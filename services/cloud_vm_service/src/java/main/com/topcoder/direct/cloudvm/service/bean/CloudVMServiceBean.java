/*

 * Copyright (C) 2010 - 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.cloudvm.service.bean;

import java.util.*;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.topcoder.direct.cloudvm.client.AmazonCloudServiceInvoker;
import com.topcoder.direct.cloudvm.client.NotusCloudServiceInvoker;
import com.topcoder.direct.cloudvm.service.CloudVMServiceException;
import com.topcoder.direct.cloudvm.service.CloudVMServiceLocal;
import com.topcoder.direct.cloudvm.service.CloudVMServiceRemote;
import com.topcoder.direct.cloudvm.service.helper.Helper;
import com.topcoder.direct.services.view.dto.cloudvm.VMAccount;
import com.topcoder.direct.services.view.dto.cloudvm.VMAccountUser;
import com.topcoder.direct.services.view.dto.cloudvm.VMContestType;
import com.topcoder.direct.services.view.dto.cloudvm.VMImage;
import com.topcoder.direct.services.view.dto.cloudvm.VMInstance;
import com.topcoder.direct.services.view.dto.cloudvm.VMInstanceAudit;
import com.topcoder.direct.services.view.dto.cloudvm.VMInstanceData;
import com.topcoder.direct.services.view.dto.cloudvm.VMInstanceStatus;
import com.topcoder.direct.services.view.dto.cloudvm.VMUsage;
import com.topcoder.direct.services.view.dto.cloudvm.VMUserData;
import com.topcoder.security.RolePrincipal;
import com.topcoder.security.TCSubject;
import com.topcoder.service.facade.contest.ContestServiceFacade;
import com.topcoder.service.user.UserService;
import com.topcoder.service.user.UserServiceException;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;

/**
 * <p>
 * This class implements the CloudVMServiceLocal and CloudVMServiceRemote
 * interfaces. And it's implemented as a stateless session bean. It will perform the steps necessary for processing
 * the launch/terminate/get vm instances. This will delegate the request for lauch/terminate/get to the respective 
 * provider( Amazon/Notus ) and perform the action.. It will also record the action to the TC database.
 * </p>
 * <p>
 * Thread-safety: Immutable and thread-safe.
 * </p>
 * 
 * Version 1.1 - Add the ability to create VM on another provider (Notus) by extracting the Amazon specific 
 * code to another class called <code>AmazonCloudServiceInvoker</code> based on the provider of the Image.
 *
 * Version 1.2 - Add the user_id to the vm instance audit
 *
 * <p>
 * Changes in version 1.3 (TopCoder Direct Contest VM Instances Management) :
 * <ul>
 *     <li>added {@link #getVMInstancesForContest(com.topcoder.security.TCSubject, long)}  method.</li>
 *     <li>added {@link #getAllVMAccounts()}  method.</li>
 *     <li>added {@link #isVMCreator(com.topcoder.security.TCSubject, long)} method.</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 1.4 - Release Assembly - TopCoder Direct VM Instances Management
 * <ul>
 *     <li>added {@link #getVMInstancesForContests(com.topcoder.security.TCSubject, java.util.List)} method.</li>
 *     <li>refactored {@link #getVMInstancesForContest(com.topcoder.security.TCSubject, long)} and
 *     {@link #getVMInstances(com.topcoder.security.TCSubject)} to extract the common implementation of transforming
 *     VMInstances to VMInstanceData as a private function {@link #populateVMInstanceDataWithLatestStatus}.
 *     </li>
 * </ul>
 * </p>
 * 
 * @author Standlove, kanakarajank, hohosky, gentva, jiajizhou86
 * @version 1.4
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@DeclareRoles({ "Administrator", "VMManager" })
@RolesAllowed({ "Administrator", "VMManager" })
public class CloudVMServiceBean implements CloudVMServiceRemote, CloudVMServiceLocal {

	/**
     * The default configuration namespace.
     */
    private static final String DEFAULT_NAMESAPCE = "com.topcoder.direct.cloudvm.service.bean.CloudVMServiceBean";
    
    /**
	 * Represents the UserData key for storing UserEmail
	 */
	private static final String USER_EMAIL = "user_email";

	/**
	 *  Represents the UserData key for storing Handle
	 */
	private static final String HANDLE = "handle";

	/**
	 *	Represents the UserData key for storing Branch
	 */
	private static final String BRANCH = "branch";

	/**
	 *  Represents the UserData key for storing Email
	 */
	private static final String EMAIL = "email";

	/**
	 * Represents the String containing the End of Line
	 */
	private static final String EOL = "\n";

	/**
	 * Represents the String containing the "=".
	 */
	private static final String EQUALS = "=";
	
	/**
	 * Represents the sessionContext of the EJB.
	 */
	@Resource
	private SessionContext sessionContext;

	/**
	 * The default VM image id to store the default VM image configuration. Can
	 * be any value. Will be injected by the container.
	 */
	@Resource(name = "defaultVMImageId")
	private long defaultVMImageId;

	/**
	 * The from email address. Will be injected by the container. Must be
	 * non-null, non-empty string.
	 */
	@Resource(name = "fromEmailAddress")
	private String fromEmailAddress;

	/**
	 * The encryption algorithm name. Will be injected by the container. Must be
	 * non-null, non-empty string.
	 */
	@Resource(name = "encryptionAlgorithmName")
	private String encryptionAlgorithmName;

	/**
	 * Persistence unit name. Will be injected by the container. Must be
	 * non-null, non-empty string.
	 */
	@Resource(name = "unitName")
	private String unitName;

	/**
	 * The user service to retrieve the user email. Will be injected by the
	 * container. Must be non-null.
	 */
	@EJB(name = "ejb/UserService")
	private UserService userService;

	/**
	 * The user service to retrieve the user email. Will be injected by the
	 * container. Must be non-null.
	 * 
	 * @since BUGR-3932
	 */
	@EJB(name = "ejb/ContestServiceFacade")
	private ContestServiceFacade contestServiceFacade;

	/**
	 * The logger to log invocation details. Initialized in constructor and
	 * never changed afterwards. Must be non-null.
	 */
	private final Log logger;
	
	/**
	 * Provider of the implementation logic to invoke the launch/get/terminate VM Instance on Amazon.
	 * @since 1.1
	 */
	private AmazonCloudServiceInvoker amazonServiceInvoker;
	
	
	/**
	 * Provider of the implementation logic to invoke the launch/get/terminate VM Instance on Notus.
	 * @since 1.1
	 */
	private NotusCloudServiceInvoker notusServiceInvoker;

	/**
	 * Contest type id for software contests.
	 * 
	 * @since BUGR-3932
	 */
	private static final int SOFTWARE_CONTEST_TYPE_ID = 1;

	/**
	 * Contest type id for studio contests.
	 * 
	 * @since BUGR-3932
	 */
	private static final int STUDIO_CONTEST_TYPE_ID = 2;

	/**
	 * Contest type id for bug race contests.
	 * 
	 * @since BUGR-3932
	 */
	private static final int BUG_RACE_TYPE_ID = 3;

	/**
	 * Constructor.
	 */
	public CloudVMServiceBean() {
		logger = LogManager.getLog(this.getClass().getName());
	}

	/**
	 *  <p> This initializes the <code>CloudVMServiceBean</code> with the configuration required by the Service Invoker. 
	 * 
	 *  @throws IllegalStateException it throws this exception on any issues during caller services initialization.
	 */
	@PostConstruct
	public void init(){
		Helper.logEnter(logger, "CloudVMServiceBean.init()");
		ConfigManager configManager = ConfigManager.getInstance();
        try {
			String instanceUrl = configManager.getString(DEFAULT_NAMESAPCE, "instanceUrl");
			String notusServiceUrl = configManager.getString(DEFAULT_NAMESAPCE, "notusBaseUrl");
			Helper.checkNull("notusBaseUrl", notusServiceUrl);
			Helper.checkNull("instanceurl", instanceUrl);
			amazonServiceInvoker = new AmazonCloudServiceInvoker();
			notusServiceInvoker = new NotusCloudServiceInvoker();
			notusServiceInvoker.setNotusCloudUri(notusServiceUrl);
			notusServiceInvoker.setInstanceUri(instanceUrl);
			logger.log(Level.DEBUG, "Loaded NotusService Provider with "+ notusServiceUrl + " base url and instance url as  "+instanceUrl);
		} catch (Exception e) {
            throw new IllegalStateException("Failed to create the CloudVM instance.", e);
		}
        Helper.logExit(logger, "CloudVMServiceBean.init()");
	}
	/**
	 * Launch VM instance.
	 * 
	 * @param tcSubject
	 *            the currently logged-in user info.
	 * @param vmInstanceMain
	 *            the VM instance to launch.
	 * @return the launched VM instance.
	 * @throws IllegalArgumentException
	 *             if any argument is null.
	 * @throws CloudVMServiceException
	 *             if any error occurs (wrap the underlying exceptions).
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<VMInstanceData> launchVMInstance(TCSubject tcSubject, VMInstance vmInstanceMain)
			throws CloudVMServiceException {		
		Helper.logEnter(logger, "CloudVMServiceBean.launchVMInstance()");
		Helper.checkNull("tcSubject", tcSubject);
		Helper.checkNull("vmInstance", vmInstanceMain);
		List<VMInstanceData> res = new LinkedList<VMInstanceData>();
		try {
			EntityManager entityManager = getEntityManager();

			String[] userHandles = vmInstanceMain.getTcMemberHandle().split(";");
			for (String tcMemberHandle : userHandles) {
				VMInstance vmInstance = new VMInstance();
				vmInstance.setVmImageId(vmInstanceMain.getVmImageId());
				vmInstance.setContestId(vmInstanceMain.getContestId());
				vmInstance.setUsageId(vmInstanceMain.getUsageId());
				vmInstance.setContestTypeId(vmInstanceMain.getContestTypeId());
				vmInstance.setSvnBranch(vmInstanceMain.getSvnBranch());
				vmInstance.setTcMemberHandle(tcMemberHandle);
				vmInstance.setCreationTime(new Date());

				String contestName = "";
				if (vmInstanceMain.getContestTypeId() == SOFTWARE_CONTEST_TYPE_ID
						|| vmInstanceMain.getContestTypeId() == STUDIO_CONTEST_TYPE_ID) {
					contestName = contestServiceFacade
							.getSoftwareContestByProjectId(tcSubject, vmInstanceMain.getContestId())
							.getAssetDTO().getName();
				} else if (vmInstanceMain.getContestTypeId() == BUG_RACE_TYPE_ID) {
					contestName = "Bug Race";
					// no check for bug races
				}
				vmInstance.setContestName(contestName);

				tcMemberHandle = tcMemberHandle.trim();
				logger.log(Level.DEBUG, "Launching for " + tcMemberHandle);
				verifySecurityKey(entityManager, tcMemberHandle);

				VMImage vmImage = getVMImage(vmInstance.getVmImageId(), tcSubject);
				if (vmImage == null) {
					throw new CloudVMServiceException("No vm image with id "
							+ vmInstance.getVmImageId());
				}

				VMAccount vmAccount = vmImage.getVmAccount();
				VMAccountUser vmAccountUser = getVMAccountUser(tcSubject.getUserId(),
						vmAccount.getId());
				vmInstance.setVmAccountUserId(vmAccountUser.getId());

				VMImage defaultImage = getVMImage(defaultVMImageId);

				// process user data
				StringBuilder userData = new StringBuilder();
				for (VMUserData data : vmImage.getUserData()) {
					userData.append(populateUserData(data.getKey(),data.getValue()));
				}
				userData.append(populateUserData(EMAIL,userService.getEmailAddress(tcSubject.getUserId())));
				userData.append(populateUserData(BRANCH,vmInstance.getSvnBranch()));
				userData.append(populateUserData(HANDLE,tcMemberHandle));
				userData.append(populateUserData(USER_EMAIL,userService.getEmailAddress(tcMemberHandle)));
				if (vmInstanceMain.getUserData() != null ){
					userData.append(vmInstanceMain.getUserData());
				}
				vmInstance.setUserData(userData.toString());
				VMInstanceData data = null;
				switch ((int) vmImage.getVmProvider().getId()) {
					case  Helper.NOTUS_PROVIDER: // NOTUS
						logger.log(Level.DEBUG, "Invoking NotusServiceClient");
						data = notusServiceInvoker.launchInstance(vmInstance, vmImage, defaultImage);
						break;
					case  Helper.AMAZON_PROVIDER: // Amazon EC2
						logger.log(Level.DEBUG, "Invoking AmazonServiceClient");
						data = amazonServiceInvoker.launchInstance(vmInstance, vmImage, defaultImage);
						break;
					default:
						throw new CloudVMServiceException("Invalid Image Provider "
								+ vmImage.getVmProvider().toString());
				}

				data.fillData(vmInstance, vmImage, getVMUsage(vmInstance.getUsageId()));

				vmInstance = entityManager.merge(vmInstance);

				// create audit record
				VMInstanceAudit audit = new VMInstanceAudit();
				audit.setAction("launched");
				audit.setInstanceId(vmInstance.getId());
				audit.setCreateDate(new Date());
                audit.setUserId(tcSubject.getUserId());
				entityManager.merge(audit);

				res.add(data);
            }
            return new ArrayList<VMInstanceData>(res);

        } catch (UserServiceException e) {
            throw Helper.logError(logger, new CloudVMServiceException("Unable to fetch user.", e));
        } catch (Exception e) {
            throw Helper.logError(logger, new CloudVMServiceException(
                    "Unable to launch vm image ", e));
        } finally {
            Helper.logExit(logger, "CloudVMServiceBean.launchVMInstance()");
        }
	}

	/**
	 * Verifies that given user has security key in place.
	 * 
	 * @param entityManager
	 *            entity manager to access database
	 * @param tcMemberHandle
	 *            user to verify
	 * @throws CloudVMServiceException
	 *             if verification failed
	 */
	private void verifySecurityKey(EntityManager entityManager, String tcMemberHandle)
			throws CloudVMServiceException {
		Query query = entityManager
				.createNativeQuery("select count(security_key) from user_security_key where user_id=(select u.user_id from user u where handle = ?)");
		query.setParameter(1, tcMemberHandle);
		Object result = query.getSingleResult().toString();
		if (Integer.parseInt(result.toString()) == 0) {
			throw new CloudVMServiceException("User [" + tcMemberHandle
					+ "] doesn't have security key in place.");
		}
	}

	/**
	 * Terminate VM instance.
	 * 
	 * @param tcSubject
	 *            the currently logged-in user info.
	 * @param instanceId
	 *            the VM instance to terminate.
	 * @return the changed instance status
	 * @throws IllegalArgumentException
	 *             if the tcSubject argument is null.
	 * @throws CloudVMServiceException
	 *             if any error occurs.
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public VMInstanceStatus terminateVMInstance(TCSubject tcSubject, long instanceId)
			throws CloudVMServiceException {
		Helper.logEnter(logger, "CloudVMServiceBean.terminateVMInstance");
		Helper.checkNull("tcSubject", tcSubject);
		try {
			EntityManager entityManager = getEntityManager();
			VMInstance vmInstance = entityManager.find(VMInstance.class, instanceId);

			// create and populate request
			VMAccount vmAccount = getVMAccount(tcSubject, vmInstance.getVmAccountUserId(), false);

			VMImage vmImage = getVMImage(vmInstance.getVmImageId());
			VMInstanceStatus instanceStatus = null;
			switch ((int) vmImage.getVmProvider().getId()) {
				case  Helper.NOTUS_PROVIDER: // NOTUS
					logger.log(Level.DEBUG, "Invoking NotusServiceClient");
					instanceStatus = notusServiceInvoker.terminateInstance(vmInstance,
							vmAccount);
					break;
				case  Helper.AMAZON_PROVIDER: // AMAZON
					logger.log(Level.DEBUG, "Invoking AmazonServiceClient");
					instanceStatus = amazonServiceInvoker.terminateInstance(vmInstance,
							vmAccount);
					break;
				default:
					throw new CloudVMServiceException("Invalid Image Provider "
							+ vmImage.getVmProvider().toString());
			}

			vmInstance.setTerminated(true);
			entityManager.merge(vmInstance);
			// create audit record
			VMInstanceAudit audit = new VMInstanceAudit();
			audit.setAction("terminated");
			audit.setInstanceId(vmInstance.getId());
			audit.setCreateDate(new Date());
            audit.setUserId(tcSubject.getUserId());
			entityManager.merge(audit);

			return instanceStatus;

		} catch (Exception e) {
			throw Helper.logError(logger, new CloudVMServiceException(
					"Unable to terminate vm instance with id" + instanceId + ".", e));
		} finally {
			Helper.logExit(logger, "terminateVMInstance");
		}
	}

    /**
     * Terminate VM instance for contest.
     *
     * @param tcSubject
     *            the currently logged-in user info.
     * @param instanceId
     *            the VM instance to terminate.
     * @return the changed instance status
     * @throws IllegalArgumentException
     *             if the tcSubject argument is null.
     * @throws CloudVMServiceException
     *             if any error occurs.
     * @since 1.3
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public VMInstanceStatus terminateContestVMInstance(TCSubject tcSubject, long instanceId, long contestId)
            throws CloudVMServiceException {
        Helper.logEnter(logger, "CloudVMServiceBean.terminateContestVMInstance");
        Helper.checkNull("tcSubject", tcSubject);
        try {
            EntityManager entityManager = getEntityManager();
            Query q = entityManager
                    .createQuery("select a from VMInstance a where a.contestId=:contestId and a.id = :instanceId");
            q.setParameter("instanceId", instanceId);
            q.setParameter("contestId", contestId);

            VMInstance vmInstance = (VMInstance) q.getSingleResult();

            // create and populate request
            VMAccount vmAccount = getVMAccount(tcSubject, vmInstance.getVmAccountUserId(), true);

            VMImage vmImage = getVMImage(vmInstance.getVmImageId());
            VMInstanceStatus instanceStatus = null;
            switch ((int) vmImage.getVmProvider().getId()) {
                case  Helper.NOTUS_PROVIDER: // NOTUS
                    logger.log(Level.DEBUG, "Invoking NotusServiceClient");
                    instanceStatus = notusServiceInvoker.terminateInstance(vmInstance,
                            vmAccount);
                    break;
                case  Helper.AMAZON_PROVIDER: // AMAZON
                    logger.log(Level.DEBUG, "Invoking AmazonServiceClient");
                    instanceStatus = amazonServiceInvoker.terminateInstance(vmInstance,
                            vmAccount);
                    break;
                default:
                    throw new CloudVMServiceException("Invalid Image Provider "
                            + vmImage.getVmProvider().toString());
            }

            vmInstance.setTerminated(true);
            entityManager.merge(vmInstance);
            // create audit record
            VMInstanceAudit audit = new VMInstanceAudit();
            audit.setAction("terminated");
            audit.setInstanceId(vmInstance.getId());
            audit.setCreateDate(new Date());
            audit.setUserId(tcSubject.getUserId());
            entityManager.merge(audit);

            return instanceStatus;

        } catch (Exception e) {
            throw Helper.logError(logger, new CloudVMServiceException(
                    "Unable to terminate vm instance with id" + instanceId + " and contest id " + contestId, e));
        } finally {
            Helper.logExit(logger, "terminateContestVMInstance");
        }
    }


    /**
     * Checks whether the VM instance is created by the current user.
     *
     * @param tcSubject the currently logged-in user info.
     * @param instanceId  the vm instance id.
     * @return true if the vm instance is created by the current logged-in user.
     * @throws CloudVMServiceException if any error occurs.
     * @since 1.3
     */
    public boolean  isVMCreator(TCSubject tcSubject, long instanceId) throws  CloudVMServiceException {
        Helper.logEnter(logger, "CloudVMServiceBean.isVMCreator");
        Helper.checkNull("tcSubject", tcSubject);
        try {
            EntityManager entityManager = getEntityManager();
            Query q = entityManager
                    .createQuery("select a from VMInstance a, VMAccountUser b where a.vmAccountUserId = b.id and b.userId=:userId and a.id=:instanceId");
            q.setParameter("instanceId", instanceId);
            q.setParameter("userId", tcSubject.getUserId());

            return q.getResultList().size() != 0;
        } catch (Exception e) {
            throw Helper.logError(logger, new CloudVMServiceException(
                    "Unable to check vm instance creator  info for vm instance id - " + instanceId, e));
        } finally {
            Helper.logExit(logger, "CloudVMServiceBean.isVMCreator");
        }
    }


	/**
	 * Get VM instances for the given user. Retrieves all the VMInstances present in TC database for the user.. It also retrieves the instances 
	 * from the Cloud VM service providers and then returns us the instances which are present in the cloud and update the instances which are
	 * not available in the cloud to terminated status. 
	 * 
	 * @param tcSubject
	 *            the currently logged-in user info.
	 * @return the vm instances.
	 * @throws IllegalArgumentException
	 *             if the tcSubject argument is null.
	 * @throws CloudVMServiceException
	 *             if any error occurs.
	 */
	public List<VMInstanceData> getVMInstances(TCSubject tcSubject) throws CloudVMServiceException {
		Helper.logEnter(logger, "CloudVMServiceBean.getVMInstances");
		Helper.checkNull("tcSubject", tcSubject);
		try {
			EntityManager entityManager = getEntityManager();
			List<VMAccount> vmAccounts;
			try {
				vmAccounts = getVMAccounts(tcSubject);
			} catch (CloudVMServiceException e) { // return empty list
				return new ArrayList<VMInstanceData>();
			}

			List<VMInstanceData> results = new ArrayList<VMInstanceData>();

			for (VMAccount vmAccount : vmAccounts) {
				// make db query
				Query q;
				if (!inRole(tcSubject, "Administrator")) {
					q = entityManager
							.createQuery("select a from VMInstance a, VMAccountUser b where a.terminated=:terminated and a.vmAccountUserId = b.id and b.vmAccountId=:accountId and b.userId=:userId order by a.contestId desc");
					q.setParameter("accountId", vmAccount.getId());
					q.setParameter("userId", tcSubject.getUserId());
					q.setParameter("terminated", false);
				} else {
					q = entityManager
							.createQuery("select a from VMInstance a, VMImage b where a.terminated=:terminated and a.vmImageId = b.id and b.vmAccount.id=:accountId order by a.contestId desc");
					q.setParameter("accountId", vmAccount.getId());
					q.setParameter("terminated", false);
				}
				List<VMInstance> instances = q.getResultList();
				logger.log(Level.DEBUG, "Got the following instances from the database : "+instances);

                Collection<VMInstanceData> availableInstances = populateVMInstanceDataWithLatestStatus(tcSubject,
                        vmAccount, instances);

				results.addAll(availableInstances);
			}

			// for administrators, fetch vm managers also
			if (inRole(tcSubject, "Administrator")) {
				for (VMInstanceData instanceData : results) {
					VMAccountUser accountUser = entityManager.find(VMAccountUser.class,
							instanceData.getInstance().getVmAccountUserId());
					instanceData
							.setManagerHandle(userService.getUserHandle(accountUser.getUserId()));
				}
			}

			List<VMInstanceData> list = new ArrayList<VMInstanceData>(results);
			Collections.sort(list, new Comparator<VMInstanceData>() {
				public int compare(VMInstanceData o1, VMInstanceData o2) {
					long delta = o1.getInstance().getContestId() - o2.getInstance().getContestId();
					if (delta > 0) {
						return 1;
					} else if (delta < 0) {
						return -1;
					}

					return 0;
				}
			});
			return list;
		} catch (UserServiceException e) {
			throw Helper.logError(logger, new CloudVMServiceException(
					"Unable to fetch user handle.", e));
		} catch (Exception e) {
			throw Helper.logError(logger, new CloudVMServiceException(
					"Unable to fetch vm instances.", e));
		} finally {
			Helper.logExit(logger, "CloudVMServiceBean.getVMInstances");
		}
	}

    /**
     * <p>
     * Get VM instances ever created for the given contest. Retrieves all the VMInstances present in TC
     * database for the user. It also retrieves the instances from the Cloud VM service providers and then
     * returns us the instances which are present in the cloud and update the instances which are not available
     * in the cloud to terminated status.
     * </p>
     *
     * @param tcSubject
     *            the currently logged-in user info.
     * @param contestId
     *            the given contest id.
     * @return the vm instances.
     * @throws IllegalArgumentException
     *             if the tcSubject argument is null or contestId is not positive
     * @throws CloudVMServiceException
     *             if any error occurs.
     * @since 1.3
     */
    public List<VMInstanceData> getVMInstancesForContest(TCSubject tcSubject, long contestId)
            throws CloudVMServiceException {
        return getVMInstancesForContests(tcSubject, Arrays.asList(contestId));
    }

    /**
     * <p>
     * Get VM instances ever created for the given contest list. Retrieves all the VMInstances present in
     * TC database for the user. It also retrieves the instances from the Cloud VM service providers
     * and then returns us the instances which are present in the cloud and update the instances which are
     * not available in the cloud to terminated status.
     * </p>
     *
     * @param tcSubject
     *            the currently logged-in user info.
     * @param contestIds
     *            the given contest list.
     * @return the vm instances.
     * @throws IllegalArgumentException
     *             if the tcSubject argument is null or contestIds is null or empty or any of it's element
     *             is not positive
     * @throws CloudVMServiceException
     *             if any error occurs.
     * @since 1.4
     */
    public List<VMInstanceData> getVMInstancesForContests(TCSubject tcSubject, List<Long> contestIds)
            throws CloudVMServiceException {
        Helper.logEnter(logger, "CloudVMServiceBean.getVMInstancesForContests");
        Helper.checkNull("tcSubject", tcSubject);
        Helper.checkNull("contestIds", contestIds);

        if (contestIds.size() == 0){
            return new ArrayList<VMInstanceData>();
        }

        Set<Long> distinctContestIdSet = new HashSet<Long>();
        for (Long contestId : contestIds) {
            if (contestId <= 0) {
                throw new IllegalArgumentException("contestIds should be all positive numbers, but "
                        + contestId + " is passed in.");
            }
            // only add distinct contest ids
            if (!distinctContestIdSet.contains(contestId)) {
                distinctContestIdSet.add(contestId);
            }
        }
        List<Long> distinctContestIds = new ArrayList<Long>(distinctContestIdSet);

        try {
            EntityManager entityManager = getEntityManager();
            List<VMAccount> vmAccounts = getAllVMAccounts();

            List<VMInstanceData> results = new ArrayList<VMInstanceData>();

            for (VMAccount vmAccount : vmAccounts) {
                // make db query
                Query q = entityManager.createQuery("select a from VMInstance a, VMImage b" +
                        " where a.vmImageId = b.id" +
                        " and b.vmAccount.id=:accountId" +
                        " and a.contestId in (:contestIds)" +
                        " order by a.creationTime desc");
                q.setParameter("accountId", vmAccount.getId());
                q.setParameter("contestIds", distinctContestIds);
                List<VMInstance> instances = q.getResultList();
                logger.log(Level.DEBUG, "Got the following instances from the database : " + instances);

                Collection<VMInstanceData> availableInstances = populateVMInstanceDataWithLatestStatus(tcSubject,
                        vmAccount, instances);

                results.addAll(availableInstances);
            }

            // set manager handle
            for (VMInstanceData instanceData : results) {
                VMAccountUser accountUser = entityManager.find(VMAccountUser.class,
                        instanceData.getInstance().getVmAccountUserId());
                instanceData.setManagerHandle(userService.getUserHandle(accountUser.getUserId()));
            }

            List<VMInstanceData> list = new ArrayList<VMInstanceData>(results);
            // sort by creation date.
            Collections.sort(list, new Comparator<VMInstanceData>() {
                public int compare(VMInstanceData o1, VMInstanceData o2) {
                    return o1.getCreationDate().compareTo(o2.getCreationDate());
                }
            });
            return list;
        } catch (UserServiceException e) {
            throw Helper.logError(logger, new CloudVMServiceException(
                    "Unable to fetch user handle.", e));
        } catch (Exception e) {
            throw Helper.logError(logger, new CloudVMServiceException(
                    "Unable to fetch vm instances.", e));
        } finally {
            Helper.logExit(logger, "CloudVMServiceBean.getVMInstancesForContests");
        }
    }

    /**
     * <p>
     * Populate VMInstanceData with the latest vm status from the vm provider for the given vm instances.
     * If the status is {@link VMInstanceStatus.TERMINATED} while the vm instance's isTerminated property is false,
     * the vm instance will be marked as terminated.
     * </p>
     * @param tcSubject the currently logged-in user info.
     * @param vmAccount the VM account of the VMInstances.
     * @param instances the vm instances.
     * @return collection of vm instance data.
     * @throws CloudVMServiceException if any error occurs.
     * @since 1.4
     */
    private Collection<VMInstanceData> populateVMInstanceDataWithLatestStatus(TCSubject tcSubject,
                                                                              VMAccount vmAccount,
                                                                              List<VMInstance> instances)
            throws CloudVMServiceException {
        EntityManager entityManager = getEntityManager();
        // map to reference by instance id, list to preserve order
        Map<String, VMInstanceData> notusInstanceDataMap = new LinkedHashMap<String, VMInstanceData>();
        Map<String, VMInstanceData> amazonInstanceDataMap = new LinkedHashMap<String, VMInstanceData>();
        Map<String, VMInstanceData> allInstanceDataMap = new LinkedHashMap<String, VMInstanceData>();

        // create VMInstanceData objects
        for (VMInstance instance : instances) {
            VMInstanceData instanceData = new VMInstanceData();
            instanceData.setInstance(instance);
            VMImage vmImage = getVMImage(instance.getVmImageId());
            if (vmImage != null ){
                int providerId = (int)vmImage.getVmProvider().getId();
                if (providerId == Helper.NOTUS_PROVIDER){
                    notusInstanceDataMap.put(instance.getAwsInstanceId(), instanceData);
                } else if (providerId ==  Helper.AMAZON_PROVIDER){
                    amazonInstanceDataMap.put(instance.getAwsInstanceId(), instanceData);
                }
                allInstanceDataMap.put(instance.getAwsInstanceId(), instanceData);
            } else {
                logger.log(Level.ERROR, "Image Id not found for instance : "+instance.getId());
            }
            // Mark as terminated..Will reset once we get a status check back from the provider
            instanceData.setStatus(VMInstanceStatus.TERMINATED);
        }

        if (!notusInstanceDataMap.isEmpty()){
            logger.log(Level.DEBUG, "Invoking NotusServiceClient");
            notusServiceInvoker.getVMInstances(vmAccount, notusInstanceDataMap);
        }
        if (!amazonInstanceDataMap.isEmpty()){
            logger.log(Level.DEBUG, "Invoking AmazonServiceClient");
            amazonServiceInvoker.getVMInstances(vmAccount, amazonInstanceDataMap);
        }

        if (logger.isEnabled(Level.DEBUG)) {
            logger.log(Level.DEBUG, "InstanceData after processing : " + allInstanceDataMap.values());
        }

        for (String instanceId : allInstanceDataMap.keySet()) {
            VMInstanceData instData = allInstanceDataMap.get(instanceId);
            VMInstance vmInstance = instData.getInstance();

            // Mark it deleted when status is TERMINATED or not found on the VM provider side
            // but the VMInstance's is_terminated is still false.
            if (instData.getStatus() == VMInstanceStatus.TERMINATED && !vmInstance.isTerminated()) {
                markAsTerminated(instData,
                        instData.getCreationDate() == null ? new Date() : instData.getCreationDate(),
                        tcSubject);
            }
            instData.setInstance(entityManager.merge(vmInstance));
        }

        Map<Long, VMImage> vmImageMap = new HashMap<Long, VMImage>();
        Collection<VMInstanceData> availableInstances = allInstanceDataMap.values();
        for (VMInstanceData instanceData : availableInstances) {
            VMInstance instance = instanceData.getInstance();
            VMImage vmImage = vmImageMap.get(instance.getVmImageId());
            if (vmImage == null) {
                vmImage = getVMImage(instance.getVmImageId());
                vmImageMap.put(instance.getVmImageId(), vmImage);

            }
            instanceData.fillData(instance, vmImage, getVMUsage(instance.getUsageId()));

        }
        return availableInstances;
    }

    /**
	 * Set instance as terminated ( Both on TC database and also on the Cloud provider)
	 * 
	 * @param inst
	 *            instance to be marked as terminated
     * @param tcSubject the user instance who performs the action
	 * @since BUGR-3981
	 */
	private void markAsTerminated(VMInstanceData inst, Date when, TCSubject tcSubject) throws CloudVMServiceException {
		try {
			logger.log(Level.DEBUG, "setting as terminated " + inst.getInstance().getId());
			VMInstance toBeTerminated = inst.getInstance();
			toBeTerminated.setTerminated(true);
			toBeTerminated = getEntityManager().merge(toBeTerminated);

			// create audit record
			VMInstanceAudit audit = new VMInstanceAudit();
			audit.setAction("terminated");
			audit.setInstanceId(toBeTerminated.getId());
			audit.setCreateDate(when);
            audit.setUserId(tcSubject.getUserId());
			getEntityManager().merge(audit);
		} catch (Exception e) {
			throw Helper.logError(logger, new CloudVMServiceException("unable to mark terminated",
					e));
		}

	}

	/**
	 * Get VM images.
	 * 
	 * @param tcSubject
	 *            the currently logged-in user info.
	 * @return the vm images.
	 * @throws IllegalArgumentException
	 *             if the tcSubject argument is null.
	 * @throws CloudVMServiceException
	 *             if any error occurs.
	 */
	public List<VMImage> getVMImages(TCSubject tcSubject) throws CloudVMServiceException {
		Helper.logEnter(logger, "CloudVMServiceBean.getVMImages");
		Helper.checkNull("tcSubject", tcSubject);
		try {
			EntityManager entityManager = getEntityManager();
			if (inRole(tcSubject, "Administrator")) {
				return entityManager.createQuery("select i from VMImage i ").getResultList();
			} else {
				Query q = entityManager
						.createQuery("select i from VMImage i, VMAccountUser b where i.vmAccount.id = b.vmAccountId and b.userId=:userId");
				q.setParameter("userId", tcSubject.getUserId());
				return q.getResultList();
			}
		} catch (Exception e) {
			throw Helper.logError(logger, new CloudVMServiceException(
					"Unable to fetch vm images.", e));
		} finally {
			Helper.logExit(logger, "CloudVMServiceBean.getVMImages");
		}
	}

	/**
	 * Get VM contest types.
	 * 
	 * @param tcSubject
	 *            the currently logged-in user info.
	 * @return the vm contest types.
	 * @throws IllegalArgumentException
	 *             if the tcSubject argument is null.
	 * @throws CloudVMServiceException
	 *             if any error occurs.
	 */
	public List<VMContestType> getVMContestTypes(TCSubject tcSubject)
			throws CloudVMServiceException {
		Helper.logEnter(logger, "CloudVMServiceBean.getVMContestTypes");
		Helper.checkNull("tcSubject", tcSubject);
		try {
			EntityManager entityManager = getEntityManager();
			return entityManager.createQuery("select c from VMContestType c ").getResultList();
		} catch (Exception e) {
			throw Helper.logError(logger, new CloudVMServiceException(
					"Unable to fetch vm contest types.", e));
		} finally {
			Helper.logExit(logger, "CloudVMServiceBean.getVMContestTypes");
		}
	}

	/**
	 * Get VM account by user id and vm account user id.
	 * 
	 * @param tcSubject
	 *            the currently logged-in user info.
	 * @param vmAccountUserId
	 *            the vm account user id.
     * @param asAdmin
     *            whether to retrieve the VM Account as admin role.
	 * @return the vm account.
	 * @throws CloudVMServiceException
	 *             if any error occurs.
	 */
	private VMAccount getVMAccount(TCSubject tcSubject, long vmAccountUserId, boolean asAdmin)
			throws CloudVMServiceException {
		try {
			EntityManager entityManager = getEntityManager();

			Query q;
			if (!asAdmin && !inRole(tcSubject, "Administrator")) {
				q = entityManager
						.createQuery("select a from VMAccount a, VMAccountUser b where a.id = b.vmAccountId AND b.userId=:userId AND b.id =:vmAccountUserId");
				q.setParameter("userId", tcSubject.getUserId());
				q.setParameter("vmAccountUserId", vmAccountUserId);
			} else {
				q = entityManager
						.createQuery("select a from VMAccount a, VMAccountUser b where a.id = b.vmAccountId AND b.id =:vmAccountUserId");
				q.setParameter("vmAccountUserId", vmAccountUserId);
			}

			VMAccount result = (VMAccount) q.getSingleResult();
			if (result == null) {
				throw Helper.logError(logger, new CloudVMServiceException(
						"No VM account for user with id [" + tcSubject.getUserId() + "]"));
			}
			return result;
		} catch (Exception e) {
			throw Helper.logError(logger, new CloudVMServiceException(
					"You have not set up a VM Account!", e));
		}
	}

	/**
	 * Get VM usages.
	 * 
	 * @param tcSubject
	 *            the currently logged-in user info.
	 * @return the vm usages.
	 * @throws IllegalArgumentException
	 *             if the tcSubject argument is null.
	 * @throws CloudVMServiceException
	 *             if any error occurs.
	 */
	public List<VMUsage> getVMUsages(TCSubject tcSubject) throws CloudVMServiceException {
		Helper.logEnter(logger, "CloudVMServiceBean.getVMUsages");
		Helper.checkNull("tcSubject", tcSubject);
		try {
			EntityManager entityManager = getEntityManager();
			return entityManager.createQuery("select c from VMUsage c ").getResultList();
		} catch (Exception e) {
			throw Helper.logError(logger, new CloudVMServiceException("Unable to fetch vm usages.",
					e));
		} finally {
			Helper.logExit(logger, "CloudVMServiceBean.getVMUsages");
		}
	}

	/**
	 * Get VM account user by user id and account id.
	 * 
	 * @param userId
	 *            the user id.
	 * @param accountId
	 *            the account id.
	 * @return the vm account user.
	 * @throws CloudVMServiceException
	 *             if any error occurs.
	 */
	private VMAccountUser getVMAccountUser(long userId, long accountId)
			throws CloudVMServiceException {
		try {
			EntityManager entityManager = getEntityManager();
			Query q = entityManager
					.createQuery("select a from VMAccountUser a where a.userId=:userId AND a.vmAccountId =:accountId");
			q.setParameter("userId", userId);
			q.setParameter("accountId", accountId);

			VMAccountUser result = (VMAccountUser) q.getSingleResult();
			if (result == null) {
				throw Helper.logError(logger, new CloudVMServiceException(
						"No VM account user for user with id [" + userId
								+ "]  and vm account with id [" + accountId + "]"));
			}
			return result;
		} catch (Exception e) {
			throw Helper.logError(logger, new CloudVMServiceException(
					"You have not set up a VM Account User!"));
		}
	}

	/**
	 * Get VM usage by id.
	 * 
	 * @param vmUsageId
	 *            the VM usage id.
	 * @return the vm usage.
	 * @throws CloudVMServiceException
	 *             if any error occurs.
	 */
	private VMUsage getVMUsage(long vmUsageId) throws CloudVMServiceException {
		EntityManager entityManager = getEntityManager();
		return entityManager.find(VMUsage.class, vmUsageId);
	}

	/**
	 * Get VM accounts by user id.
	 * 
	 * @param userId
	 *            the user id.
	 * @return the vm accounts.
	 * @throws CloudVMServiceException
	 *             if any error occurs.
	 */
	private List<VMAccount> getVMAccounts(TCSubject tcSubject) throws CloudVMServiceException {
		try {
			EntityManager entityManager = getEntityManager();
			List<VMAccount> result = new ArrayList<VMAccount>();

			if (inRole(tcSubject, "Administrator")) {

				Query q = entityManager.createQuery("select a from VMAccount a ");
				result = (List<VMAccount>) q.getResultList();

			} else {

				Query q = entityManager
						.createQuery("select a from VMAccount a, VMAccountUser b where a.id = b.vmAccountId AND b.userId=:userId");
				q.setParameter("userId", tcSubject.getUserId());

				result = (List<VMAccount>) q.getResultList();
				if (result.isEmpty()) {
					throw Helper.logError(logger, new CloudVMServiceException(
							"No VM account for user with id [" + tcSubject.getUserId() + "]"));
				}
			}

			return result;
		} catch (Exception e) {
			throw Helper.logError(logger, new CloudVMServiceException(
					"You have not set up a VM Account!"));
		}
	}

    /**
     * Get All VM accounts.
     *
     * @return all vm accounts.
     * @since  1.3
     */
    private List<VMAccount> getAllVMAccounts() {
        try {
            EntityManager entityManager = getEntityManager();

            Query q = entityManager.createQuery("select a from VMAccount a ");
            return (List<VMAccount>) q.getResultList();
        } catch (Exception e) {
            Helper.logError(logger, new CloudVMServiceException(
                    "Fail to retrieve all VM accounts", e));

            return new ArrayList<VMAccount>();
        }
    }

	/**
	 * Get VM image by id.
	 * 
	 * @param vmImageId
	 *            the VM image id.
	 * @return the vm image.
	 * @throws CloudVMServiceException
	 *             if any error occurs.
	 */
	private VMImage getVMImage(long vmImageId) throws CloudVMServiceException {
		EntityManager entityManager = getEntityManager();
		return entityManager.find(VMImage.class, vmImageId);
	}

	/**
	 * Get VM image by id and user id.
	 * 
	 * @param vmImageId
	 *            the VM image id.
	 * @param tcSubject
	 *            TCSubject instance for user
	 * @return the vm image.
	 * @throws CloudVMServiceException
	 *             if any error occurs.
	 */
	private VMImage getVMImage(long vmImageId, TCSubject tcSubject) throws CloudVMServiceException {
		EntityManager entityManager = getEntityManager();
		if (inRole(tcSubject, "Administrator")) {
			Query q = entityManager.createQuery("select i from VMImage i where i.id=:id");
			q.setParameter("id", vmImageId);
			return (VMImage) q.getSingleResult();
		} else {
			Query q = entityManager
					.createQuery("select i from VMImage i, VMAccountUser b where i.vmAccount.id = b.vmAccountId and b.userId=:userId and i.id=:id");
			q.setParameter("id", vmImageId);
			q.setParameter("userId", tcSubject.getUserId());
			return (VMImage) q.getSingleResult();
		}
	}

	/**
	 * Checks if the user has given role.
	 * 
	 * @param tcSubject
	 *            TCSubject instance for user
	 * @param roleName
	 *            role name
	 * @return whether user has such role
	 */
	private static boolean inRole(TCSubject tcSubject, String roleName) {
		Set<RolePrincipal> roles = tcSubject.getPrincipals();
		if (roles != null) {
			for (RolePrincipal role : roles) {
				if (role.getName().equalsIgnoreCase(roleName)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * <p>
	 * Returns the <code>EntityManager</code> looked up from the session
	 * context.
	 * </p>
	 * 
	 * @return the EntityManager looked up from the session context
	 * @throws CloudVMServiceException
	 *             if fail to get the EntityManager from the sessionContext.
	 */
	private EntityManager getEntityManager() throws CloudVMServiceException {
		try {
			Object obj = sessionContext.lookup(unitName);

			if (obj == null) {
				throw Helper.logError(logger, new CloudVMServiceException("No entity manager."));
			}

			return (EntityManager) obj;
		} catch (ClassCastException e) {
			throw Helper.logError(logger, new CloudVMServiceException("No entity manager.", e));
		}
	}
	
	/**
	 * Represents the key,value to be shown in a single line as key=value. 
	 *  
	 * @param key
	 * 			key representing the userdata
	 * @param value
	 * 			value representing the userdata
	 * @return
	 * 			String representing the key,value pair in key=value format.
	 */
	private String populateUserData(String key, String value){
		return new StringBuilder(key).append(EQUALS).append(value).append(EOL).toString();
	}
}
