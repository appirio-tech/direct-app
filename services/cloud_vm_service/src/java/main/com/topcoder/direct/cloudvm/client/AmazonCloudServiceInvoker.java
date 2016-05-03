/*
 * Copyright (C) 2012 - 2016 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.cloudvm.client;

import java.util.*;

import org.apache.commons.codec.binary.Base64;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.AvailabilityZone;
import com.amazonaws.services.ec2.model.CreateTagsRequest;
import com.amazonaws.services.ec2.model.DescribeInstancesRequest;
import com.amazonaws.services.ec2.model.DescribeInstancesResult;
import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.InstanceState;
import com.amazonaws.services.ec2.model.Placement;
import com.amazonaws.services.ec2.model.Reservation;
import com.amazonaws.services.ec2.model.RunInstancesRequest;
import com.amazonaws.services.ec2.model.RunInstancesResult;
import com.amazonaws.services.ec2.model.Tag;
import com.amazonaws.services.ec2.model.TerminateInstancesRequest;
import com.amazonaws.services.ec2.model.TerminateInstancesResult;
import com.topcoder.direct.cloudvm.service.CloudVMServiceException;
import com.topcoder.direct.cloudvm.service.bean.CloudVMServiceBean;
import com.topcoder.direct.cloudvm.service.helper.Helper;
import com.topcoder.direct.services.view.dto.cloudvm.VMAccount;
import com.topcoder.direct.services.view.dto.cloudvm.VMAvailabilityZone;
import com.topcoder.direct.services.view.dto.cloudvm.VMImage;
import com.topcoder.direct.services.view.dto.cloudvm.VMInstance;
import com.topcoder.direct.services.view.dto.cloudvm.VMInstanceData;
import com.topcoder.direct.services.view.dto.cloudvm.VMInstanceStatus;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;

/**
 * This class implements functionality required for managing the Amazon instance ( Create/Update/Delete instance). 
 * It will use the Amazon WebService SDK for Java to launch or terminate instances. 
 * The basic Amazon image and instance data is stored in local database, but we need to
 * get the Amazon instance status from the web service. It will also log the
 * invocation details.
 * 
 * This class is created  by extracting the Amazon EC2 specific calls from the ${@link CloudVMServiceBean}. 
 *
 * <p>
 * Version 1.1 - TCCC-5790
 * <ul>
 *     <li>Add retry when accessing aws to describe vm instances. Failed ones will be filtered and marked as deleted.</li>
 * </ul>
 * </p>
 *
 * <p>
 * Changes in version 1.2 (Topcoder Direct - VM Management Improvement - Create Tag for EC2 Instance):
 * <ul>
 * <li>When the EC2 instance is created, tag it with AMI name and challenge id.</li>
 * </ul>
 * </p>
 *
 * @author kanakarajank, jiajizhou86, TCSCODER
 * @version 1.2
 * @since 1.0
 */
public class AmazonCloudServiceInvoker {

	/**
	 * The logger to log invocation details.
	 */
	private final Log logger = LogManager.getLog(AmazonCloudServiceInvoker.class.getName());

    /**
     * Max retry count when accessing aws services.
     * @since 1.1
     */
    private static final int MAX_RETRY_COUNT = 3;

	/**
	 * Launch the Amazon EC2 instance specified by the parameters. 
	 * 
	 * @param vmInstance
	 * 			the VM instance to launch.
	 * @param vmImage
	 * 			instance representing the vmImage to be launched.
	 * @return 
	 * 		 the launched VM instance.
	 * @throws CloudVMServiceException
	 */
	public VMInstanceData launchInstance(VMInstance vmInstance, VMImage vmImage,
			VMImage defaultImage) throws CloudVMServiceException {
		Helper.logEnter(logger, "AmazonCloudServiceInvoker.launchInstance()");
		AmazonEC2Client client = new AmazonEC2Client(new BasicAWSCredentials(vmImage.getVmAccount()
				.getAwsAccessKeyId(), vmImage.getVmAccount().getAwsSecurityAccessKey()));

		// create and populate request
		RunInstancesRequest request = new RunInstancesRequest();
		if (vmImage.getSecurityGroup() == null) {
			request.setSecurityGroups(Arrays.asList(defaultImage.getSecurityGroup().getName()));
		} else {
			request.setSecurityGroups(Arrays.asList(vmImage.getSecurityGroup().getName()));
		}

		// process time zones
		VMAvailabilityZone requestZone = vmImage.getAvailabilityZone() == null ? defaultImage
				.getAvailabilityZone() : vmImage.getAvailabilityZone();
		Set<String> availableZones = new HashSet<String>();
		for (AvailabilityZone zone : client.describeAvailabilityZones().getAvailabilityZones()) {
			availableZones.add(zone.getZoneName());
		}
		if (availableZones.contains(requestZone.getName())) {
			request.setPlacement(new Placement().withAvailabilityZone(requestZone.getName()));
		} else {
			request.setPlacement(new Placement().withAvailabilityZone(availableZones.iterator()
					.next()));
		}

		request.setInstanceType(vmImage.getInstanceType() == null ? defaultImage.getInstanceType()
				.getName() : vmImage.getInstanceType().getName());
		request.setKeyName(vmImage.getKeyPair() == null ? defaultImage.getKeyPair().getName()
				: vmImage.getKeyPair().getName());

		// BUGR-3931
		logger.log(Level.DEBUG, "user data is: " + vmInstance.getUserData());

		request.setUserData(new String(Base64.encodeBase64(vmInstance.getUserData().toString()
				.getBytes())));

		request.setImageId(vmImage.getAwsImageId());
		request.setMinCount(1);
		request.setMaxCount(1);

		// send request
		RunInstancesResult result = client.runInstances(request);
		Instance instance = result.getReservation().getInstances().get(0);

        // create tag
        CreateTagsRequest createTagsRequest = new CreateTagsRequest();
        createTagsRequest.withResources(instance.getInstanceId()).withTags(new Tag("Name", vmImage.getTcName() + " - "
            + vmInstance.getContestId()));
        client.createTags(createTagsRequest);

		vmInstance.setAwsInstanceId(instance.getInstanceId());
		logger.log(Level.DEBUG,
				"state=" + instance.getState() + ", public ip= " + instance.getPublicIpAddress());
		vmInstance.setPublicIP(instance.getPublicIpAddress()); // BUGR-3932

		// create and return DTO object
		VMInstanceData data = new VMInstanceData();
		data.setInstance(vmInstance);
		data.setStatus(convertState(instance.getState()));
		Helper.logExit(logger, "AmazonCloudServiceInvoker.launchInstance()");
		return data;
	}
	
	/**
	 * Terminate Amazon VM instance.
	 * 
	 * @param vmInstance
	 * 			the VMInstance to be terminated. 
	 * @param vmAccount
	 * 			the VMAccount containing the username and password to connect to the service.
	 * @return
	 * 			the instanceStatus.
	 * @throws CloudVMServiceException
	 * 				if any error occurs.
	 */
	public  VMInstanceStatus terminateInstance(VMInstance vmInstance, VMAccount vmAccount) throws CloudVMServiceException{
		Helper.logEnter(logger, "AmazonCloudServiceInvoker.terminateInstance()");
		TerminateInstancesRequest request =
                new TerminateInstancesRequest().withInstanceIds(vmInstance.getAwsInstanceId());
		
        AmazonEC2Client client = new AmazonEC2Client(
            new BasicAWSCredentials(vmAccount.getAwsAccessKeyId(), vmAccount.getAwsSecurityAccessKey()));

        // send request
        TerminateInstancesResult result = client.terminateInstances(request);
        
        // return new vm state
		Helper.logExit(logger, "AmazonCloudServiceInvoker.terminateInstance()");
        return convertState(result.getTerminatingInstances().get(0).getCurrentState());
	}

	
	/**
	 * This method returns the list of VMInstances for the specified user. It accepts the list of VMInstances from TC database and returns the status for those.
	 * 
	 * @param vmAccount
	 * 			the VMAccount containing the username and password to connect to the service.
	 * @param dataMap
	 * 			<code>Map</code> containing the VMInstanceData ( with VMInstance ) from the TC database for the user. 
	 * @return
	 * 			processed <code>Map</code> of the VMInstanceData with the status as in the cloud 
	 * @throws CloudVMServiceException
	 */
	public  Map<String,VMInstanceData> getVMInstances(VMAccount vmAccount, Map<String,VMInstanceData> dataMap) throws CloudVMServiceException{
		Helper.logEnter(logger, "AmazonCloudServiceInvoker.getVMInstances()");
		 // populate result with data from db
        AmazonEC2Client client = new AmazonEC2Client(
            new BasicAWSCredentials(vmAccount.getAwsAccessKeyId(), vmAccount.getAwsSecurityAccessKey()));
        // make remote call and fetch information about vm statuses
        DescribeInstancesResult result = null;
        Set<String> querySet = new HashSet<String>();
        // only add those not terminated instances to query
        for (String instanceId : dataMap.keySet()) {
            if (!dataMap.get(instanceId).getInstance().isTerminated()) {
                querySet.add(instanceId);
            }
        }
        int retryCount = 0;
        boolean success = false;
        Exception lastError = null;
        while (retryCount <= MAX_RETRY_COUNT && !success) {
            try {
                result = client.describeInstances(new DescribeInstancesRequest().withInstanceIds(querySet));
                success = true;
            } catch(AmazonServiceException aws) {
                lastError = aws;
                retryCount++;
                logger.log(Level.DEBUG,  aws.getErrorCode());
                if("InvalidInstanceID.NotFound".equals(aws.getErrorCode())) {
                    logger.log(Level.DEBUG, "AWS error: Instance not found.");
                    String invalidIds = aws.getMessage().replaceAll("The instance ID.*'(.*)' .* not exist", "$1");
                    String[] invalidIdArray = invalidIds.split(", ");
                    for (String invalidId : invalidIdArray) {
                        logger.log(Level.WARN, "Get invalid instance id: " + invalidId + ".");
                        querySet.remove(invalidId);
                    }
                } else if ("InvalidInstanceID.Malformed".equals(aws.getErrorCode())) {
                    logger.log(Level.DEBUG, "AWS error: Malformed instance id.");
                    String invalidId = aws.getMessage().replaceAll("Invalid id: \"(.*)\"", "$1");
                    logger.log(Level.WARN, "Get invalid instance id: " + invalidId + ".");
                    querySet.remove(invalidId);
                } else {
                    throw Helper.logError(logger,new CloudVMServiceException("Unable to describe instances", aws));
                }
            } catch (Exception e) {
                throw Helper.logError(logger,new CloudVMServiceException("Unable to describe instances", e));
            }
        }

        if (!success) {
            throw Helper.logError(logger, new CloudVMServiceException("Retry accessing aws exhausted.", lastError));
        }

        if (result != null) {
            for (Reservation reservation : result.getReservations()) {
                for (Instance instance : reservation.getInstances()) {
                    VMInstanceData instanceData = dataMap.get(instance.getInstanceId());
                    if (instanceData != null) {
                        instanceData.setStatus(convertState(instance.getState()));
                        instanceData.setVmCreationTime(instance.getLaunchTime());
                        if(instanceData.getInstance().getPublicIP() == null || instance.getPublicIpAddress() != null) {
                            VMInstance toBeSaved = instanceData.getInstance();
                            toBeSaved.setPublicIP(instance.getPublicIpAddress());
                        }
                    }
                }
            }
        }
        Helper.logExit(logger, "AmazonCloudServiceInvoker.getVMInstances()");
        return dataMap;
	}
	/**
	 * Converts amazon vm InstanceState to topcoder VMInstanceStatus.
	 * 
	 * @param state
	 *            value to convert
	 * @return conversion result
	 * @throws CloudVMServiceException
	 *             if conversion fails
	 */
	private  VMInstanceStatus convertState(InstanceState state)
			throws CloudVMServiceException {
		if (state.getName().equalsIgnoreCase("pending")) {
			return VMInstanceStatus.PENDING;
		} else if (state.getName().equalsIgnoreCase("running")) {
			return VMInstanceStatus.RUNNING;
		} else if (state.getName().equalsIgnoreCase("shutting-down")) {
			return VMInstanceStatus.SHUTTING_DOWN;
		} else if (state.getName().equalsIgnoreCase("terminated")) {
			return VMInstanceStatus.TERMINATED;
		} else if (state.getName().equalsIgnoreCase("stopped")) {
			return VMInstanceStatus.STOPPED;
		} else {
			return VMInstanceStatus.UNKNOWN;
		}
	}
}
