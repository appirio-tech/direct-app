/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.cloudvm.client;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import com.topcoder.direct.cloudvm.client.dto.NotusInstance;
import com.topcoder.direct.cloudvm.service.CloudVMServiceException;
import com.topcoder.direct.cloudvm.service.helper.Helper;
import com.topcoder.direct.services.view.dto.cloudvm.VMAccount;
import com.topcoder.direct.services.view.dto.cloudvm.VMImage;
import com.topcoder.direct.services.view.dto.cloudvm.VMInstance;
import com.topcoder.direct.services.view.dto.cloudvm.VMInstanceData;
import com.topcoder.direct.services.view.dto.cloudvm.VMInstanceStatus;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;

/**
 * This class implements functionality required for managing the Notus Cloud instance ( Create/Update/Delete instance). 
 * It will use the Notus REST service to launch or terminate instances. 
 * The basic image and instance data is stored in local database, but we need to
 * get the Amazon instance status from the web service. It will also log the
 * invocation details.
 * 
 * @author kanakarajank
 * 
 * @version 1.0
 */
public class NotusCloudServiceInvoker {


	/**
	 * Represents the Base URL for the Notus REST API.  
	 */
	private String notusCloudUri;
	
	/**
	 * Represents the URL relative to the notusCloudUri for the instance.
	 */
	private String instanceUri;
	
	
	/**
	 * The logger to log invocation details.
	 */
	private static final Log logger = LogManager.getLog(NotusCloudServiceInvoker.class.getName());

	/**
	 * Launch the Notus Cloud instance specified by the parameters. 
	 *  
	 * @param vmInstance
	 * 				the vmInstance to be terminated.
	 * @param vmImage
	 * 				the vmImage representing the image to be used for launching.
	 * @throws CloudVMServiceException
	 * 				thrown if there are any errors.
	 */
	public VMInstanceData launchInstance(VMInstance vmInstance, VMImage vmImage,
			VMImage defaultImage) throws CloudVMServiceException {
		Helper.logEnter(logger, "NotusCloudServiceInvoker.launchInstance()");
		URI uri = null;
		HttpClient httpclient = null;
		PostMethod postMethod = null;
		List<NotusInstance> instances = null;
		int status = HttpStatus.SC_NOT_FOUND;
		String responseMessage = null;
		try {
			uri = new URI(notusCloudUri + instanceUri);
			postMethod = new PostMethod(uri.toString());
			postMethod.addRequestHeader("accept", "application/json");
			httpclient = getHttpClient(uri,vmImage.getVmAccount());
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new NameValuePair("name", "VMInstance -"+vmInstance.getTcMemberHandle()+"-"+vmInstance.getContestId()));
			nameValuePairs.add(new NameValuePair("imageID", vmImage.getAwsImageId()));
			nameValuePairs.add(new NameValuePair("instanceType", vmImage.getInstanceType()
					.getName()));
			nameValuePairs.add(new NameValuePair("location", vmImage.getAvailabilityZone()
					.getName()));
			nameValuePairs.add(new NameValuePair("publicKey", vmImage.getKeyPair().getName()));
			if (vmInstance.getUserData() != null){				
				Properties prop = new Properties();
				prop.load(new ByteArrayInputStream(vmInstance.getUserData().getBytes()));
				Enumeration<Object> keys = prop.keys();
				while(keys.hasMoreElements()){
					String key = (String)keys.nextElement();
					nameValuePairs.add(new NameValuePair(key, prop.getProperty(key)));
				}
			}
			NameValuePair[] requestBody = nameValuePairs.toArray(new NameValuePair[] {});
			postMethod.addParameters(requestBody);
			logger.log(Level.DEBUG, "Request Data for Launch VM Instance "+ Arrays.toString(requestBody));
			status = httpclient.executeMethod(postMethod);
			logger.log(Level.DEBUG, "Response Code for Launch VM Instance:" + status);
			responseMessage = postMethod.getResponseBodyAsString();
			logger.log(Level.DEBUG, "Response Data for Launch VM Instance:" + responseMessage);
		} catch (URISyntaxException e) {
			e.printStackTrace();
			throw Helper.logError(logger,new CloudVMServiceException("Improper URL "+uri.toString(),e));
		} catch (HttpException e) {
			e.printStackTrace();
			throw Helper.logError(logger,new CloudVMServiceException("Error Invoking launchInstance ",e));
		} catch (Exception e) {
			e.printStackTrace();
			throw Helper.logError(logger,new CloudVMServiceException("Error Invoking launchInstance ",e));
		} finally{
			if (postMethod != null){				
				postMethod.releaseConnection();
			}
		}
		if (status != HttpStatus.SC_OK){
			throw Helper.logError(logger,new CloudVMServiceException("Error Invoking launchInstance.Got an invalid returncode["+status +"] from the server ["+ uri.toString() +"]"));
		}
		
		NotusInstance notusInstance = null;
		try {
			instances = processInstanceResponse(responseMessage);
			notusInstance = instances.get(0);
		} catch (Exception e) {
			throw Helper.logError(logger,new CloudVMServiceException("Instance Created Successfully.. But Error Processing the Response Message.",e));
		} 
		

		vmInstance.setAwsInstanceId(notusInstance.getId());
		logger.log(Level.DEBUG, "state=" + notusInstance.getStatus() + ", public ip= "
				+ notusInstance.getIp());
		vmInstance.setPublicIP(notusInstance.getIp());

		// create and return DTO object
		VMInstanceData data = new VMInstanceData();
		data.setInstance(vmInstance);
		data.setStatus(convertState(notusInstance.getStatus()));
		Helper.logExit(logger, "NotusCloudServiceInvoker.launchInstance()");
		return data;

	}

	/**
	 * Terminate Notus VM instance.
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
	public VMInstanceStatus terminateInstance(VMInstance vmInstance, VMAccount vmAccount)
			throws CloudVMServiceException {
		Helper.logEnter(logger, "NotusCloudServiceInvoker.terminateInstance()");
		Helper.checkNull("vmInstance", vmInstance);
		Helper.checkNull("vmInstance", vmInstance.getAwsInstanceId());
		URI uri = null;
		DeleteMethod deleteMethod = null;
		int status = HttpStatus.SC_NOT_FOUND;
		String responseMessage = null;
		try {
			uri = new URI(notusCloudUri + instanceUri + "/" + vmInstance.getAwsInstanceId());
			deleteMethod = new DeleteMethod(uri.toString());
			deleteMethod.addRequestHeader("accept", "application/json");
			HttpClient httpclient = getHttpClient(uri,vmAccount);
			logger.log(Level.DEBUG, "Requesting Get VM Instances ");
			status = httpclient.executeMethod(deleteMethod);
			logger.log(Level.DEBUG, "Response Code for Get VM Instance: " + status);
			responseMessage = deleteMethod.getResponseBodyAsString();
			logger.log(Level.DEBUG, "Response Data for Launch VM Instance:" + responseMessage);			
			
		} catch (URISyntaxException e) {
			e.printStackTrace();
			throw Helper.logError(logger,new CloudVMServiceException("Improper URL "+uri.toString(),e));
		} catch (HttpException e) {
			e.printStackTrace();
			throw Helper.logError(logger,new CloudVMServiceException("Error Invoking getVMInstances ",e));
		} catch (Exception e) {
			e.printStackTrace();
			throw Helper.logError(logger,new CloudVMServiceException("Error Invoking getVMInstances ",e));
		} finally {
			if (deleteMethod != null){				
				deleteMethod.releaseConnection();
			}
		}
		if (status != HttpStatus.SC_OK){
			throw Helper.logError(logger,new CloudVMServiceException("Error Invoking launchInstance.Got an invalid returncode["+status +"] from the server"));
		}
		
		try {
			Boolean responseStatus = processTerminateResponse(responseMessage);
			if (!responseStatus){
				logger.log(Level.ERROR,"Response status for the Terminate Instance is not true");
			}
		} catch (Exception e) {
			throw Helper
					.logError(
							logger,
							new CloudVMServiceException(
									"Instance Deleted Successfully.. But Error Processing the Response Message.",
									e));
		}
		Helper.logExit(logger, "NotusCloudServiceInvoker.terminateInstance()");
		return VMInstanceStatus.TERMINATED;
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
	public Map<String, VMInstanceData> getVMInstances(VMAccount vmAccount,
			Map<String, VMInstanceData> dataMap) throws CloudVMServiceException {
		Helper.logEnter(logger, "NotusCloudServiceInvoker.getVMInstances()");
		URI uri = null;
		List<NotusInstance> instances = null;
		GetMethod getMethod = null;
		int status = HttpStatus.SC_NOT_FOUND;
		String responseMessage = null;
		try {
			uri = new URI(notusCloudUri + instanceUri);
			getMethod = new GetMethod(uri.toString());
			getMethod.addRequestHeader("accept", "application/json");
			HttpClient httpclient = getHttpClient(uri,vmAccount);
			logger.log(Level.DEBUG, "Requesting Get VM Instances ");
			status = httpclient.executeMethod(getMethod);
			logger.log(Level.DEBUG, "Response Code for Get VM Instance: " + status);
			responseMessage = getMethod.getResponseBodyAsString();
			logger.log(Level.DEBUG, "Response Data for Launch VM Instance:" + responseMessage);			
			
		} catch (URISyntaxException e) {
			e.printStackTrace();
			throw Helper.logError(logger,new CloudVMServiceException("Improper URL "+uri.toString(),e));
		} catch (HttpException e) {
			e.printStackTrace();
			throw Helper.logError(logger,new CloudVMServiceException("Error Invoking getVMInstances ",e));
		} catch (Exception e) {
			e.printStackTrace();
			throw Helper.logError(logger,new CloudVMServiceException("Error Invoking getVMInstances ",e));
		} finally {
			if (getMethod != null){				
				getMethod.releaseConnection();
			}
		}
		if (status != HttpStatus.SC_OK){
			throw Helper.logError(logger,new CloudVMServiceException("Error Invoking launchInstance.Got an invalid returncode["+status +"] from the server"));
		}
		try {
			instances = processInstanceResponse(responseMessage);
		} catch (Exception e) {
			e.printStackTrace();
			throw Helper.logError(logger,new CloudVMServiceException("Error Processing the Response Message.Check log for details.",e));
		}

		
		for (NotusInstance notusInstance : instances) {
			VMInstanceData instanceData = dataMap.get(notusInstance.getId());
			if (instanceData != null) {
				instanceData.setStatus(convertState(notusInstance.getStatus()));
				instanceData.setVmCreationTime(new Date(notusInstance.getLaunchTime()));
				if ( instanceData.getInstance().getPublicIP() == null
						|| notusInstance.getIp() != null) {
					VMInstance toBeSaved = instanceData.getInstance();
					toBeSaved.setPublicIP(notusInstance.getIp());
				}
			}
		}
		Helper.logEnter(logger, "NotusCloudServiceInvoker.getVMInstances()");
		return dataMap;
	}

	/**
	 * Converts notus vm InstanceState to topcoder VMInstanceStatus.
	 * 
	 * @param state
	 *            value to convert
	 * @return conversion result
	 * @throws CloudVMServiceException
	 *             if conversion fails
	 */
	private VMInstanceStatus convertState(int state) throws CloudVMServiceException {

		switch (state) {
		case 0:
		case 1:
			return VMInstanceStatus.PENDING;
		case 5:
			return VMInstanceStatus.RUNNING;
		case 10:
			return VMInstanceStatus.SHUTTING_DOWN;
		case 3:
			return VMInstanceStatus.TERMINATED;
		case 11:
			return VMInstanceStatus.STOPPED;
		default:
			return VMInstanceStatus.UNKNOWN;
		}
	}

	/**
	 * Method to Create the HTTPClient using for invoking the Notus REST APIs
	 * 
	 * @param uri
	 * 			URI for Http Connection
	 * @param account
	 * 			Account having the userid and password for authentication.
	 * @return
	 * 			the HTTPClient instance
	 */
	private HttpClient getHttpClient(URI uri,VMAccount account) {
		HttpClient httpClient = new HttpClient();
		Credentials credentials = new UsernamePasswordCredentials(account.getAwsAccessKeyId(), account.getAwsSecurityAccessKey());
		httpClient.getState().setCredentials(new AuthScope(uri.getHost(), uri.getPort()), credentials);
		return httpClient;

	}

	/**
	 * Method to form JSON object from WebService Response content for the  launchVMInstance/getVMInstances.
	 * 
	 * @param response
	 * 			JSON String response obtained by the REST invocation
	 * @return
	 * 			The List<NotusInstance> from the JSON input.
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 * @throws IOException
	 * 			any error while mapping the JSON object to the <code>NotusInstance</code> object
	 */
	private List<NotusInstance> processInstanceResponse(String response) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		Map<String,List<NotusInstance>> mapValues =  mapper.readValue(response, new  TypeReference<Map<String,List<NotusInstance>>>(){});
		return mapValues.get("instances");
	}
	
	/**
	 * Method to form JSON object from WebService Response content for terminateVMInstance.
	 * 
	 * @param response
	 * 			JSON String response obtained by the REST invocation
	 * @return
	 * 			Boolean flag representing the status from the invocation
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 * @throws IOException
	 * 			any error while mapping the JSON object to the <code>Boolean</code> object
	 */
	private Boolean processTerminateResponse(String response) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		Map<String,Boolean> mapValues =  mapper.readValue(response, new  TypeReference<Map<String,Boolean>>(){});
		return mapValues.get("success");
	}

	/**
	 * Setter for setting the notusCloudUri.
	 * @param notusCloudUri the notusCloudUri to set
	 */
	public void setNotusCloudUri(String notusCloudUri) {
		this.notusCloudUri = notusCloudUri;
	}

	/**
	 * Setter for setting the instanceUri.
	 * @param instanceUri the instanceUri to set
	 */
	public void setInstanceUri(String instanceUri) {
		this.instanceUri = instanceUri;
	}
}
