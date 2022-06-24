/*
 * Copyright (C) 2012 - 2014 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.facade.permission.ejb;

import com.topcoder.management.resource.ResourceRole;
import com.topcoder.project.service.ProjectServices;
import com.topcoder.security.RolePrincipal;
import com.topcoder.security.TCSubject;
import com.topcoder.security.groups.model.GroupPermissionType;
import com.topcoder.security.groups.model.ResourceType;
import com.topcoder.security.groups.services.AuthorizationService;
import com.topcoder.service.contest.eligibility.ContestEligibility;
import com.topcoder.service.contest.eligibility.dao.ContestEligibilityManager;
import com.topcoder.service.contest.eligibility.dao.ContestEligibilityPersistenceException;
import com.topcoder.service.contest.eligibilityvalidation.ContestEligibilityValidationManager;
import com.topcoder.service.contest.eligibilityvalidation.ContestEligibilityValidationManagerException;
import com.topcoder.service.facade.permission.CommonProjectPermissionData;
import com.topcoder.service.permission.Permission;
import com.topcoder.service.permission.PermissionService;
import com.topcoder.service.permission.PermissionServiceException;
import com.topcoder.service.permission.PermissionType;
import com.topcoder.service.permission.ProjectPermission;
import com.topcoder.service.project.ProjectData;
import com.topcoder.service.project.ProjectService;
import com.topcoder.service.user.UserService;
import com.topcoder.service.user.UserServiceException;
import com.topcoder.service.util.SpringApplicationContext;
import com.topcoder.shared.util.DBMS;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.ConfigManagerException;
import com.topcoder.web.common.RowNotFoundException;
import com.topcoder.web.ejb.forums.Forums;
import com.topcoder.web.ejb.forums.ForumsHome;
import com.topcoder.web.ejb.project.ProjectRoleTermsOfUse;
import com.topcoder.web.ejb.project.ProjectRoleTermsOfUseHome;
import com.topcoder.web.ejb.user.UserPreference;
import com.topcoder.web.ejb.user.UserPreferenceHome;
import com.topcoder.web.ejb.user.UserTermsOfUse;
import com.topcoder.web.ejb.user.UserTermsOfUseHome;
import org.jboss.logging.Logger;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.CreateException;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * <p>
 * This class implements <code>PermissionServiceFacade</code> and provide all permission related operations.
 * </p>
 *
 * <p>
 * Version 1.1 (Release Assembly - TopCoder Direct Project Audit v1.0) change notes:
 *   <ol>
 *      <li>Updated methods {@link #updatePermissions(TCSubject, Permission[])} and
 *        {@link #updateProjectPermissions(TCSubject, List, long)}
 *        to pass <code>TCSubject</code> instance to the updated methods.</li>
 *   </ol>
 * </p>
 *
 * <p>
 * Version 1.2 (TopCoder Direct - Add Group Permission Logic and project full permission checking)
 * <ul>
 *     <li>Added {@link #groupAuthorizationService} to check group related permission</li>
 *     <li>Added method {@link #hasFullProjectPermissionViaGroup(long, com.topcoder.security.TCSubject)}</li>
 *     <li>Updated method {@link #updateProjectPermissions(com.topcoder.security.TCSubject, java.util.List, long)}</li>
 * </ul>
 * </p>
 *
 *
 * 
 * @author GreatKevin
 * @version 1.2
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class PermissionServiceFacadeBean implements PermissionServiceFacadeLocal, PermissionServiceFacadeRemote {

    /**
     * The default configuration namespace.
     */
    private static final String DEFAULT_NAMESAPCE = "com.topcoder.service.facade.permission.ejb.PermissionServiceFacadeBean";

    /**
     * Private constant specifying administrator role.
     */
    private static final String ADMIN_ROLE = "Cockpit Administrator";

    /**
     * Private constant specifying liquid administrator role.
     */
    private static final String LIQUID_ADMIN_ROLE = "Liquid Administrator";

    /**
     * Private constant specifying TC staff role.
     */
    private static final String TC_STAFF_ROLE = "TC Staff";

    /**
     * The user preference id of global timeline notification.
     */
    private final static int GLOBAL_TIMELINE_NOTIFICATION = 29;

    /**
     * The user preference id of glocal forum watch.
     */
    private final static int GLOBAL_FORUM_WATCH = 30;

    /**
     * The user preference id of global project forum watch.
     */
    private final static int GLOBAL_PROJECT_FORUM_WATCH = 31;

    /**
     * Private constant specifying resource ext ref id
     */
    private static final String RESOURCE_INFO_EXTERNAL_REFERENCE_ID = "External Reference ID";

    /**
     * Private constant specifying resource pay
     */
    private static final String RESOURCE_INFO_PAYMENT_STATUS = "Payment Status";

    /**
     * Private constant specifying resource pay
     */
    private static final String RESOURCE_INFO_PAYMENT_STATUS_NA = "N/A";

    /**
     * Private constant specifying resource handle
     */
    private static final String RESOURCE_INFO_HANDLE = "Handle";

    /**
     * Private constant specifying registration date
     */
    private static final String RESOURCE_INFO_REGISTRATION_DATE = "Registration Date";

    /**
     * The date format used for setting the registration time of the resource
     */
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("MM.dd.yyyy hh:mm a", Locale.US);

    /**
     * A flag indicating whether or not create the forum.
     */
    private boolean createForum = false;

    /**
     * userBeanProviderUrl is used in the jndi context to get the user bean.
     * It's injected, non-null and non-empty after set.
     */
    private String userBeanProviderUrl;


    /**
     * forumBeanProviderUrl is used in the jndi context to get the forum bean in
     * the createForum method.
     */
    private String softwareForumBeanProviderUrl;

    /**
     * userBeanProviderUrl is used in the jndi context to get the user bean.
     * It's injected, non-null and non-empty after set.
     */
    @Resource(name = "projectBeanProviderUrl")
    private String projectBeanProviderUrl;

    /**
     * <p>
     * Represents the sessionContext of the ejb.
     * </p>
     */
    @Resource
    private SessionContext sessionContext;

    /**
     * The logger instance for logging the information in PermissionServiceFacade.
     */
    private Logger logger = Logger.getLogger(this.getClass());

    /**
     * <p>
     * A <code>PermissionService</code> providing access to available
     * <code>Permission Service EJB</code>. This bean is delegated to process
     * the calls for CRUD on permissions.
     * </p>
     */
    @EJB(name = "ejb/PermissionService")
    private PermissionService permissionService = null;

    /**
     * <p>
     * A <code>ProjectServices</code> providing access to available
     * <code>Project Services EJB</code>. This bean is delegated to process the
     * calls to the methods inherited from <code>Project Services</code>
     * component.
     * </p>
     */
    @EJB(name = "ejb/ProjectServicesBean")
    private ProjectServices projectServices = null;

    /**
     * <p>
     * A <code>ProjectService</code> providing access to available
     * <code>Project Service EJB</code>. This bean is delegated to process the
     * calls to the methods inherited from <code>Project Service</code>
     * component.
     * </p>
     */
    @EJB(name = "ejb/ProjectService")
    private ProjectService projectService = null;

    /**
     * <p>
     * A <code>UserService</code> providing access to available <code>User Service EJB</code>.
     * </p>
     */
    @EJB(name = "ejb/UserService")
    private UserService userService = null;

    /**
     * <p>
     * A <code>ContestEligibilityManager</code> providing access to available
     * <code>Contest Eligibility Persistence EJB</code>.
     * </p>
     */
    @EJB(name = "ejb/ContestEligibilityPersistence")
    private ContestEligibilityManager contestEligibilityManager = null;

    /**
     * <p>
     * A <code>ContestEligibilityValidationManager</code> providing access to available
     * <code>Contest Eligibility Validation EJB</code>.
     * </p>
     */
    @EJB(name = "ejb/ContestEligibilityValidation")
    private ContestEligibilityValidationManager contestEligibilityValidationManager = null;


    /**
     * The group authorization service.
     *
     * @since 1.2
     */
    private AuthorizationService groupAuthorizationService;


    /**
     * Gets the group authorization service. If the field is null, get
     * the authorization service from shared spring application context.
     *
     * @return the group authorization service.
     * @since 1.2
     */
    public AuthorizationService getGroupAuthorizationService() {
        if (groupAuthorizationService == null) {
            groupAuthorizationService = (AuthorizationService) SpringApplicationContext.getBean(
                    "groupAuthorizationService");
        }
        return groupAuthorizationService;
    }

    /**
     * <p>
     * Gets the EJB bean provide URL and createForum flag from the configuration file.
     * </p>
     */
    @PostConstruct
    public void init() {
        if (logger == null) {
            logger = Logger.getLogger(this.getClass());
        }

        logger.debug("Initializing PermissionServiceFacadeBean");

        ConfigManager configManager = ConfigManager.getInstance();

        try {
            String createForumProp = configManager.getString(DEFAULT_NAMESAPCE, "createForum");

            createForum = Boolean.parseBoolean(createForumProp);

            softwareForumBeanProviderUrl = configManager.getString(DEFAULT_NAMESAPCE, "forumBeanProviderUrl");

            userBeanProviderUrl = configManager.getString(DEFAULT_NAMESAPCE, "userBeanProviderUrl");

            projectBeanProviderUrl = configManager.getString(DEFAULT_NAMESAPCE, "projectBeanProviderUrl");

        } catch (ConfigManagerException e) {
            throw new IllegalStateException("Unable to read property from config file", e);
        }
    }

    /**
     * <p>
     * This method retrieve all the permissions that the user owned for any
     * projects. Returns empty list if no permission found.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param userid    user id to look for
     * @return all the permissions that the user owned for any projects.
     * @throws PermissionServiceException if any error occurs when getting permissions.
     */
    public List<Permission> getPermissionsByUser(TCSubject tcSubject, long userid) throws PermissionServiceException {
        logger.debug("getPermissionsByUser");

        List<Permission> permissionsByUser = this.permissionService.getPermissionsByUser(userid);

        logger.debug("Exit getPermissionsByUser");

        return permissionsByUser;
    }

    /**
     * <p>
     * This method retrieve all the permissions that various users own for a
     * given project. Returns empty list if no permission found.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param projectid project id to look for
     * @return all the permissions that various users own for a given project.
     * @throws PermissionServiceException if any error occurs when getting permissions.
     */
    public List<Permission> getPermissionsByProject(TCSubject tcSubject, long projectid) throws PermissionServiceException {
        logger.debug("getPermissionsByProject");

        List<Permission> permissionsByProject = this.permissionService.getPermissionsByProject(projectid);

        logger.debug("Exit getPermissionsByProject");

        return permissionsByProject;
    }

    /**
     * <p>
     * This method retrieve all the permissions that the user own for a given
     * project. Returns empty list if no permission found.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param userid    user id to look for
     * @param projectid project id to look for
     * @return all the permissions that the user own for a given project.
     * @throws PermissionServiceException if any error occurs when getting permissions.
     */
    public List<Permission> getPermissions(TCSubject tcSubject, long userid, long projectid) throws PermissionServiceException {
        logger.debug("getPermissions");

        List<Permission> permissions = this.permissionService.getPermissions(userid, projectid);

        logger.debug("Exit getPermissions");

        return permissions;
    }

    /**
     * <p>
     * This method retrieve all the permission types.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @return all the permission types.
     * @throws PermissionServiceException if any error occurs when getting permission types.
     */
    public List<PermissionType> getAllPermissionType(TCSubject tcSubject) throws PermissionServiceException {
        logger.debug("getAllPermissionType");

        List<PermissionType> allPermissionType = this.permissionService.getAllPermissionType();

        logger.debug("Exit getAllPermissionType");
        return allPermissionType;
    }

    /**
     * <p>
     * This method will add a permission type, and return the added type entity.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param type      the permission type to add.
     * @return the added permission type entity
     * @throws PermissionServiceException if any error occurs when adding the permission type.
     */
    public PermissionType addPermissionType(TCSubject tcSubject, PermissionType type) throws PermissionServiceException {
        logger.debug("addPermissionType");
        PermissionType permissionType = this.permissionService.addPermissionType(type);
        logger.debug("Exit addPermissionType");
        return permissionType;
    }

    /**
     * <p>
     * This method will update permission type data.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param type      the permission type to update.
     * @throws PermissionServiceException if any error occurs when updating the permission type.
     */
    public void updatePermissionType(TCSubject tcSubject, PermissionType type) throws PermissionServiceException {
        logger.debug("updatePermissionType");
        this.permissionService.updatePermissionType(type);
        logger.debug("Exit updatePermissionType");
    }

    /**
     * <p>
     * This method updates array of permissions to the persistence.
     * </p>
     *
     * @param tcSubject   TCSubject instance contains the login security info for the current user
     * @param permissions the permissions to update.
     * @throws PermissionServiceException if any error occurs when updating the permission.
     */
    public void updatePermissions(TCSubject tcSubject, Permission[] permissions) throws PermissionServiceException {
        logger.debug("updatePermissions");

        try {

            if (!isRole(tcSubject, ADMIN_ROLE) && !isRole(tcSubject, LIQUID_ADMIN_ROLE) &&
                    !isRole(tcSubject, TC_STAFF_ROLE)) {
                long userId = tcSubject.getUserId();

                List<CommonProjectPermissionData> userPermissions =
                        getCommonProjectPermissionDataForUser(tcSubject, userId);

                for (Permission p : permissions) {
                    boolean hasFullPermission = false;

                    for (CommonProjectPermissionData data : userPermissions) {
                        if (p.getResourceId().longValue() == data.getProjectId()) {
                            if (data.getPfull() > 0) {
                                hasFullPermission = true;
                                break;
                            }
                        } else {
                            if (p.getResourceId().longValue() == data.getContestId() &&
                                    (p.isStudio() == data.isStudio())) {
                                if (data.getPfull() > 0 || data.getCfull() > 0) {
                                    hasFullPermission = true;
                                    break;
                                }
                            }
                        }
                    }

                    if (!hasFullPermission) {
                        throw new PermissionServiceException("No full permission on resource " + p.getResourceId());
                    }
                }
            }

            // when add/remove permission, we need to add/remvoe observer
            for (Permission per : permissions) {
                // if add permission
                if ((per.getPermissionId() == null || per.getPermissionId() <= 0)
                        && per.getPermissionType() != null && per.getPermissionType().getName() != null
                        && !per.getPermissionType().getName().equals("")) {

                    List<Long> projectIds = new ArrayList<Long>();

                    // if permission is project, get its OR projects
                    if (per.getPermissionType().getPermissionTypeId() >= PermissionType.PERMISSION_TYPE_PROJECT_READ
                            && per.getPermissionType().getPermissionTypeId() <=
                            PermissionType.PERMISSION_TYPE_PROJECT_FULL) {
                        projectIds = projectServices.getProjectIdByTcDirectProject(per.getResourceId());
                    } else if (!per.isStudio()) {
                        projectIds.add(per.getResourceId());
                    }

                    if (projectIds != null && projectIds.size() > 0) {
                        // for each OR project, find all observers
                        for (Long pid : projectIds) {
                            // delegate to new method added in BUGR-3731
                            this.assignRole(tcSubject, pid.longValue(), ResourceRole.RESOURCE_ROLE_OBSERVER_ID,
                                    per.getUserId().longValue());

                        }

                    }


                }
                // if remove permission, we need to remove observer
                else if (per.getPermissionType() == null || per.getPermissionType().getName() == null
                        || per.getPermissionType().getName().equals("")) {

                    List<Permission> ps = this.getPermissions(tcSubject, per.getUserId(), per.getResourceId());
                    Permission toDelete = null;
                    if (ps != null && ps.size() > 0) {
                        toDelete = ps.get(0);
                    }

                    if (toDelete != null) {
                        List<Long> projectIds = new ArrayList<Long>();
                        boolean isTCProject = false;

                        // if permission is project, get its OR projects
                        if (toDelete.getPermissionType().getPermissionTypeId() >=
                                PermissionType.PERMISSION_TYPE_PROJECT_READ
                                && toDelete.getPermissionType().getPermissionTypeId() <=
                                PermissionType.PERMISSION_TYPE_PROJECT_FULL) {
                            projectIds = projectServices.getProjectIdByTcDirectProject(per.getResourceId());
                            isTCProject = true;
                        } else if (!toDelete.isStudio()) {
                            projectIds.add(per.getResourceId());
                        }

                        if (projectIds != null && projectIds.size() > 0) {
                            for (Long pid : projectIds) {
                                // if we are removing project permission but user still has contest permission,
                                // or if we are removing contest permission but user still has project permission
                                // we will not remove observer
                                if ((!projectServices.hasContestPermission(pid, toDelete.getUserId()) && isTCProject)
                                        || (!projectServices.checkProjectPermission(
                                        projectServices.getTcDirectProject(pid), true, toDelete.getUserId()) &&
                                        !isTCProject)) {
                                    com.topcoder.management.resource.Resource[] resources = projectServices.searchResources(
                                            pid, ResourceRole.RESOURCE_ROLE_OBSERVER_ID);

                                    com.topcoder.management.resource.Resource delRes = null;

                                    // check if user is already a observer
                                    if (resources != null && resources.length > 0) {
                                        for (com.topcoder.management.resource.Resource resource : resources) {
                                            if (resource.hasProperty(RESOURCE_INFO_EXTERNAL_REFERENCE_ID)
                                                    && resource.getProperty(RESOURCE_INFO_EXTERNAL_REFERENCE_ID).equals(
                                                    String.valueOf(toDelete.getUserId()))) {
                                                delRes = resource;
                                                break;
                                            }
                                        }
                                    }

                                    if (delRes != null) {
                                        projectServices.removeResource(delRes, String.valueOf(tcSubject.getUserId()));
                                        projectServices.removeNotifications(delRes.getId(), new long[]{pid.longValue()},
                                                String.valueOf(delRes.getId()));
                                    }

                                    // delete forum watch
                                    long forumId = projectServices.getForumId(pid);
                                    if (forumId > 0 && createForum && !per.isStudio()) {
                                        deleteSoftwareForumWatchAndRole(forumId, per.getUserId());
                                    }
                                }
                            }
                        }
                    }

                }
            }

            this.permissionService.updatePermissions(tcSubject, permissions);
        } catch (Exception e) {
            sessionContext.setRollbackOnly();
            throw new PermissionServiceException(e.getMessage(), e);
        }

        logger.debug("Exit updatePermissions");
    }

    /**
     * <p>Gets the permissions set for projects which specified user has <code>Full Access</code> permission set for.
     * </p>
     *
     * @param tcSubject a <code>TCSubject</code> instance contains the login security info for the current user.
     * @return a <code>List</code> listing the project permissions set for projects which specified user has <code>Full
     *         Access</code> permission set for.
     * @throws PermissionServiceException if an unexpected error occurs.
     */
    public List<ProjectPermission> getProjectPermissions(TCSubject tcSubject) throws PermissionServiceException {
        logger.debug("getProjectPermissions(" + tcSubject + ")");
        return this.permissionService.getProjectPermissions(tcSubject.getUserId());
    }

    /**
     * <p>Updates the permissions for specified user for accessing the projects.</p>
     *
     * @param tcSubject          a <code>TCSubject</code> instance contains the login security info for the current user.
     * @param projectPermissions a <code>List</code> listing the permissions to be set for specified user for accessing
     *                           projects.
     * @param role               the role id to add
     * @throws PermissionServiceException if an unexpected error occurs.
     */
    public void updateProjectPermissions(TCSubject tcSubject, List<ProjectPermission> projectPermissions, long role) throws PermissionServiceException {
        logger.debug("permission service facade bean #updateProjectPermissions("
                + tcSubject + ", " + projectPermissions + ", " + role + ")");




        try {
            if (!isRole(tcSubject, ADMIN_ROLE)
                    && !isRole(tcSubject, LIQUID_ADMIN_ROLE) && !isRole(tcSubject, TC_STAFF_ROLE)) {
                // NOT the admin role, need to check if the specified operation user has full permission
                // on these project ids
                Set<Long> directProjectIDsSetNeedToCheck = new HashSet<Long>();

                // check all the direct project ID in passed-in project permissions
                for (ProjectPermission permission : projectPermissions) {
                    directProjectIDsSetNeedToCheck.add(permission.getProjectId());
                }

                // 1) Check permissions in user_permission_grant
                // retrieve direct project ID set what the operation user has FULL permission on
                Set<Long> fullAccessProjectIds = new HashSet<Long>();

                List<ProjectPermission> allPermissions = getProjectPermissions(tcSubject);

                for (ProjectPermission permission : allPermissions) {
                    if (permission.getUserId() == tcSubject.getUserId()
                            && "full".equals(permission.getPermission())) {
                        fullAccessProjectIds.add(permission.getProjectId());
                    }
                }

                Iterator<Long> itr = directProjectIDsSetNeedToCheck.iterator();

                while (itr.hasNext()) {
                    if (fullAccessProjectIds.contains(itr.next())) {
                        // if has full permission, remove from check list
                        itr.remove();
                    }
                }

                // 2) check group permission if there are direct project IDs not pass the direct full permission check
                if (directProjectIDsSetNeedToCheck.size() > 0) {
                    // still has direct project id not pass check, check the rest via group permission checking

                    Iterator<Long> itr2 = directProjectIDsSetNeedToCheck.iterator();

                    while (itr2.hasNext()) {
                        if (hasFullProjectPermissionViaGroup(itr2.next(), tcSubject)) {
                            // if has full permission, remove from check list
                            itr2.remove();
                        }
                    }

                }

                // 3) Check if there is still id not pass check
                if (directProjectIDsSetNeedToCheck.size() > 0) {
                    throw new PermissionServiceException("User "
                            + tcSubject.getUserId()
                            + " is not granted FULL permission for these "
                            + "project(s) : " + StringUtils.collectionToCommaDelimitedString(directProjectIDsSetNeedToCheck));
                }


            }

			//for now we will always add as observer to OR contests, 
			boolean isCopilot = (role == ResourceRole.RESOURCE_ROLE_COPILOT_ID);
			
			role = ResourceRole.RESOURCE_ROLE_OBSERVER_ID;
			
            // when add/remove permission, we need to add/remove observer
            for (ProjectPermission permission : projectPermissions) {
                // add permission
                if (permission.getUserPermissionId() < 0
                        && permission.getPermission() != null
                        && permission.getPermission().length() > 0) {

                    boolean addNotification;
                    boolean addForumWatch;
                    boolean addGlobalForumWatch;

                    List<Integer> preferenceIds = new ArrayList<Integer>();
                    // notification preference
                    preferenceIds.add(GLOBAL_TIMELINE_NOTIFICATION);
                    // forum preference
                    preferenceIds.add(GLOBAL_FORUM_WATCH);
                    // project forum preference
                    preferenceIds.add(GLOBAL_PROJECT_FORUM_WATCH);

                    Map<Integer, String> preferences = getUserPreferenceMaps(permission.getUserId(), preferenceIds);

                    addNotification = Boolean.parseBoolean(preferences.get(GLOBAL_TIMELINE_NOTIFICATION));
                    addForumWatch = Boolean.parseBoolean(preferences.get(GLOBAL_FORUM_WATCH));
                    addGlobalForumWatch = Boolean.parseBoolean(preferences.get(GLOBAL_PROJECT_FORUM_WATCH));
					
					// copilot always watch project forum
					if (isCopilot)
					{
						addGlobalForumWatch = true;
					}

                    // grant user to project level forum, always watch project forum
                    ProjectData cockpitProj = projectService.getProject(tcSubject, permission.getProjectId());
                    if (cockpitProj.getForumCategoryId() != null && !cockpitProj.getForumCategoryId().equals("")) {
                        Long projForumId = Long.parseLong(cockpitProj.getForumCategoryId());
                        createSoftwareForumWatchAndRole(projForumId, permission.getUserId(), addGlobalForumWatch);

                    }
                    List<Long> projectIds = projectServices
                            .getProjectIdByTcDirectProject(permission
                                    .getProjectId());

                    // for each OR project, find all observers
                    for (Long pid : projectIds) {
                        this.assignRole(tcSubject, pid.longValue(), role,
                                permission.getUserId(), null, addNotification,
                                addForumWatch, permission.getStudio(), true);
                    }
                } else if (permission.getPermission() == null
                        || "".equals(permission.getPermission())) {
                    List<Permission> ps = getPermissions(tcSubject, permission
                            .getUserId(), permission.getProjectId());
                    Permission toDelete = null;
                    if (ps != null && ps.size() > 0) {
                        toDelete = ps.get(0);
                    }

                    if (toDelete != null) {

                        // remove user to project level forum
                        ProjectData cockpitProj = projectService.getProject(tcSubject, permission.getProjectId());
                        if (cockpitProj.getForumCategoryId() != null && !cockpitProj.getForumCategoryId().equals("")) {
                            Long projForumId = Long.parseLong(cockpitProj.getForumCategoryId());
                            deleteSoftwareForumWatchAndRole(projForumId, permission.getUserId());

                        }

                        List<Long> projectIds = projectServices
                                .getProjectIdByTcDirectProject(permission
                                        .getProjectId());

                        for (Long pid : projectIds) {
                            // if we are removing project permission but user
                            // still has contest permission,
                            // we will not remove observer
                            if ((!projectServices.hasContestPermission(pid,
                                    toDelete.getUserId()))) {
                                com.topcoder.management.resource.Resource[] resources = projectServices
                                        .searchResources(pid, role);

                                com.topcoder.management.resource.Resource delRes = null;

                                // check if user is already a observer
                                if (resources != null && resources.length > 0) {
                                    for (com.topcoder.management.resource.Resource resource : resources) {
                                        if (resource.hasProperty(RESOURCE_INFO_EXTERNAL_REFERENCE_ID)
                                                && resource.getProperty(RESOURCE_INFO_EXTERNAL_REFERENCE_ID)
                                                .equals(String.valueOf(toDelete.getUserId()))) {
                                            delRes = resource;
                                            break;
                                        }
                                    }
                                }

                                if (delRes != null) {
                                    projectServices.removeResource(delRes,
                                            String.valueOf(tcSubject.getUserId()));
                                    projectServices.removeNotifications(toDelete.getUserId(), new long[]{pid
                                            .longValue()}, String.valueOf(tcSubject.getUserId()));
                                }

                                // delete forum watch
                                long forumId = projectServices.getForumId(pid);
                                if (forumId > 0 && createForum && !permission.getStudio()) {
                                    deleteSoftwareForumWatchAndRole(forumId, permission
                                            .getUserId());
                                }
                            }
                        }
                    }
                }
            }

            // update project permissions
            this.permissionService.updateProjectPermissions(tcSubject, projectPermissions,
                    tcSubject.getUserId());
        } catch (Exception e) {
            sessionContext.setRollbackOnly();
            throw new PermissionServiceException(e.getMessage(), e);
        }

        logger.debug("Exit updateProjectPermissions");
    }


    /**
     * Check if the given user has full permission on the given project by checking the group permission.
     *
     * @param directProjectId the direct project id
     * @param user the user
     * @return true if has full permission, false otherwise
     * @throws Exception if any error
     * @since 1.2
     */
    private boolean hasFullProjectPermissionViaGroup(long directProjectId, TCSubject user) throws Exception {

        if (this.getGroupAuthorizationService() == null) {
            System.out.println("Authorization Service not injected");
            throw new IllegalStateException(
                    "Group Authorization Service is not injected for PermissionServiceFacadeBean");
        }

        // Check if user is administrator for client account
        com.topcoder.clients.model.Client clientByProject = projectService.getClientByProject(
                directProjectId);


        long userId = user.getUserId();
        boolean isCustomerAdministrator = false;
        if (clientByProject != null) {
            isCustomerAdministrator = getGroupAuthorizationService().isCustomerAdministrator(userId,
                    clientByProject.getId());
        }
        if (isCustomerAdministrator) {
            return true;
        } else {
            // If not then check if user is granted desired permission to access the project based on
            // security groups which user is member of
            GroupPermissionType groupPermissionType =
                    getGroupAuthorizationService().checkAuthorization(userId, directProjectId, ResourceType.PROJECT);

            if (groupPermissionType == null || groupPermissionType != GroupPermissionType.FULL) {
                return false;
            } else {
                return true;
            }
        }
    }


    /**
     * <p>
     * Checks if the login user is of given role
     * </p>
     *
     * @param tcSubject TCSubject instance for login user
     * @return true if it is given role
     */
    private static boolean isRole(TCSubject tcSubject, String roleName) {
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
     * Gets the list of project and their permissions (including permissions for the parent tc project)
     * </p>
     *
     * @param tcSubject   TCSubject instance contains the login security info for the current user
     * @param createdUser user for which to get the permissions
     * @return list of project and their permissions.
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<CommonProjectPermissionData> getCommonProjectPermissionDataForUser(TCSubject tcSubject, long createdUser) {
        logger.debug("getCommonProjectPermissionDataForUser (tcSubject = " + tcSubject.getUserId() + ", " + createdUser + ")");

        List<com.topcoder.management.project.SimpleProjectPermissionData> softwarePermissions =
                projectServices.getSimpleProjectPermissionDataForUser(tcSubject, createdUser);

        List<CommonProjectPermissionData> ret = new ArrayList<CommonProjectPermissionData>();

        for (com.topcoder.management.project.SimpleProjectPermissionData data : softwarePermissions) {
            CommonProjectPermissionData newdata = new CommonProjectPermissionData();
            newdata.setContestId(data.getContestId());
            newdata.setProjectId(data.getProjectId());
            newdata.setCfull(data.getCfull());
            newdata.setCname(data.getCname());
            newdata.setCread(data.getCread());
            newdata.setCwrite(data.getCwrite());
            newdata.setPfull(data.getPfull());
            newdata.setPname(data.getPname());
            newdata.setPread(data.getPread());
            newdata.setPwrite(data.getPwrite());
            newdata.setStudio(data.isStudio());
            ret.add(newdata);
        }

        logger.debug("Exit getCommonProjectPermissionDataForUser (" +
                createdUser + ")");

        return ret;
    }

    /**
     * Gets the user preference data as a map.
     *
     * @param userId        the id of the user.
     * @param preferenceIds the ids of the user preferences.
     * @return the map of user preferences.
     * @throws Exception if any error.
     */
    private Map<Integer, String> getUserPreferenceMaps(long userId,
                                                       List<Integer> preferenceIds) throws Exception {
        Properties p = new Properties();
        p.put(Context.INITIAL_CONTEXT_FACTORY,
                "org.jnp.interfaces.NamingContextFactory");
        p.put(Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces");
        p.put(Context.PROVIDER_URL, userBeanProviderUrl);

        Context c = new InitialContext(p);

        UserPreferenceHome userPreferenceHome = (UserPreferenceHome) c
                .lookup("com.topcoder.web.ejb.user.UserPreferenceHome");
        UserPreference userPreference = userPreferenceHome.create();
        Map<Integer, String> ret = new HashMap<Integer, String>();

        for (int preferenceId : preferenceIds) {
            String value;

            try {
                value = userPreference.getValue(userId, preferenceId,
                        DBMS.COMMON_OLTP_DATASOURCE_NAME);

            } catch (RowNotFoundException e) {
                value = "false";
            }

            ret.put(preferenceId, value);
        }

        return ret;
    }

    /**
     * Adds the forum watch and forum role for the specified userId and forumId.
     *
     * @param forumId the id of the forum.
     * @param userId  the id of the user.
     * @param watch   the forum watch flag.
     */
    private void createSoftwareForumWatchAndRole(long forumId, long userId, boolean watch) {
        logger.debug("createSoftwareForumWatchAndRole (" + forumId + ", " + userId + ")");

        try {
            Forums forums = getSoftwareForums();

            String roleId = "Software_Moderators_" + forumId;
			forums.assignRole(userId, roleId);
			
            if (watch) {
                forums.createCategoryWatch(userId, forumId);
            }

            logger.debug("Exit createSoftwareForumWatchAndRole (" + forumId + ", " + userId + ")");

        } catch (Exception e) {
            logger.error("*** Could not create a software forum watch for " + forumId + ", " + userId);
            logger.error(e);
        }
    }

    /**
     * Assign the given roleId to the specified userId in the given project.
     *
     * @param tcSubject the TCSubject instance.
     * @param projectId the id of the project.
     * @param roleId    the id of the role.
     * @param userId    the id of the user.
     * @param phase     the <code>Phase</code> associated with the resource.
     * @param isStudio  whether assign to studio contest.
     */
    private void assignRole(TCSubject tcSubject, long projectId, long roleId, long userId, com.topcoder.project.phases.Phase phase,
                            boolean addNotification, boolean addForumWatch, boolean isStudio, boolean checkTerm)
            throws PermissionServiceException {
        logger.debug("enter method assignRole");

        try {
            //  com.topcoder.management.resource.Resource[] resources = projectServices.searchResources(projectId, roleId);

            boolean found = false;

            found = projectServices.resourceExists(projectId, roleId, userId);

            boolean termChecking = !checkTerm || checkTerms(projectId, userId, new int[]{(int) roleId});


            // if not found && user agreed terms (if any) && is eligible, add resource
            if (!found && termChecking
                    && isEligible(userId, projectId, false)) {

                com.topcoder.management.resource.Resource newRes = new com.topcoder.management.resource.Resource();
                newRes.setId(com.topcoder.management.resource.Resource.UNSET_ID);
                newRes.setProject(projectId);

                ResourceRole[] allroles = projectServices.getAllResourceRoles();
                ResourceRole roleToSet = null;
                if (allroles != null && allroles.length > 0) {
                    for (ResourceRole role : allroles) {
                        if (role.getId() == roleId) {
                            roleToSet = role;
                        }
                    }
                }

                if (roleToSet == null) {
                    throw new PermissionServiceException("Invalid role id " + roleId);
                }

                newRes.setResourceRole(roleToSet);
                if (phase != null) {
                    newRes.setPhase(phase.getId());
                }

                newRes.setProperty(RESOURCE_INFO_EXTERNAL_REFERENCE_ID, String.valueOf(userId));
                newRes.setProperty(RESOURCE_INFO_HANDLE, String.valueOf(userService.getUserHandle(userId)));
                newRes.setProperty(RESOURCE_INFO_PAYMENT_STATUS, RESOURCE_INFO_PAYMENT_STATUS_NA);
                newRes.setProperty(RESOURCE_INFO_REGISTRATION_DATE, DATE_FORMAT.format(new Date()));
                newRes.setUserId(userId);
                projectServices.updateResource(newRes, String.valueOf(tcSubject.getUserId()));

                // only check notification setting for observer, else always addd
                if (roleId != ResourceRole.RESOURCE_ROLE_OBSERVER_ID || addNotification) {
                    projectServices.addNotifications(userId,
                            new long[]{projectId},
                            String.valueOf(tcSubject.getUserId()));
                }


                // create forum watch
                long forumId = projectServices.getForumId(projectId);

                // only check notification for observer
                if (roleId != ResourceRole.RESOURCE_ROLE_OBSERVER_ID) {
                    addForumWatch = true;
                }

                if (forumId > 0 && createForum && !isStudio) {
                    createSoftwareForumWatchAndRole(forumId, userId, addForumWatch);
                }

            }

        } catch (UserServiceException use) {
            sessionContext.setRollbackOnly();
            throw new PermissionServiceException(use.getMessage(), use);
        } catch (PermissionServiceException cse) {
            sessionContext.setRollbackOnly();
            throw new PermissionServiceException(cse.getMessage(), cse);
        } finally {
            logger.debug("exist method assignRole");
        }
    }

    /**
     * Assign the given roleId to the specified userId in the given project.
     *
     * @param tcSubject the TCSubject instance.
     * @param projectId the id of the project.
     * @param roleId    the id of the role.
     * @param userId    the id of the user.
     * @throws PermissionServiceException if any error.
     */
    private void assignRole(TCSubject tcSubject, long projectId, long roleId, long userId)
            throws PermissionServiceException {
        assignRole(tcSubject, projectId, roleId, userId, null, true, true, false, false);
    }

    /**
     * Delete software forum watch with given parameters. It will lookup the ForumsHome
     * interface, and ceate the forum by the ejb home interface. In the old
     * version, this method misses the document, it's added in the version 1.1
     *
     * @param forumId The forum id to delete watch.
     * @param userId  userId The user id to use
     */
    private void deleteSoftwareForumWatchAndRole(long forumId, long userId) {
        logger.info("deleteForumWatch (" + forumId + ", " + userId + ")");

        try {
            Forums forums = getSoftwareForums();

            String roleId = "Software_Moderators_" + forumId;
            forums.deleteCategoryWatch(userId, forumId);
            forums.removeRole(userId, roleId);
            logger.debug("Exit deleteForumWatch (" + forumId + ", " + userId + ")");

        } catch (Exception e) {
            logger.error("*** Could not delete forum watch for " + forumId + ", " + userId);
            logger.error(e);
        }
    }

    /**
     * Get the Software Forum EJB service for Software competitions.
     *
     * @return the forums EJB service handler.
     * @throws javax.naming.NamingException if a naming exception is encountered.
     * @throws java.rmi.RemoteException     if remote error occurs.
     * @throws javax.ejb.CreateException    if error occurs when creating EJB handler
     */
    private Forums getSoftwareForums() throws RemoteException, NamingException, CreateException {
        return getForumsEJBFromJNDI(softwareForumBeanProviderUrl);
    }

    /**
     * Get the EJB handler for Forum EJB service.
     *
     * @param url the EJB bean url
     * @return the forum EJB service handler.
     * @throws NamingException if a naming exception is encountered.
     * @throws RemoteException if remote error occurs.
     * @throws CreateException if error occurs when creating EJB handler
     */
    private Forums getForumsEJBFromJNDI(String url) throws NamingException, CreateException, RemoteException {
        Properties p = new Properties();
        p.put(Context.INITIAL_CONTEXT_FACTORY,
                "org.jnp.interfaces.NamingContextFactory");
        p.put(Context.URL_PKG_PREFIXES,
                "org.jboss.naming:org.jnp.interfaces");

        p.put(Context.PROVIDER_URL, url);

        Context c = new InitialContext(p);
        ForumsHome forumsHome = (ForumsHome) c.lookup(ForumsHome.EJB_REF_NAME);

        return forumsHome.create();
    }

    /**
     * Checks whether the specified user has the required terms of the specified project.
     *
     * @param projectId the id of the project.
     * @param userId    the id of the user.
     * @param roleIds   ids of the user roles.
     * @return whether the user meets the term requirements.
     */
    private boolean checkTerms(long projectId, long userId, int[] roleIds) {
        logger.info("checkTerms (" + projectId + ", " + userId + ", " + roleIds + ")");

        try {
            Properties p = new Properties();
            p.put(Context.INITIAL_CONTEXT_FACTORY,
                    "org.jnp.interfaces.NamingContextFactory");
            p.put(Context.URL_PKG_PREFIXES,
                    "org.jboss.naming:org.jnp.interfaces");
            p.put(Context.PROVIDER_URL, userBeanProviderUrl);

            Context c = new InitialContext(p);
            UserTermsOfUseHome userTermsOfUseHome = (UserTermsOfUseHome) c.lookup(UserTermsOfUseHome.EJB_REF_NAME);

            UserTermsOfUse userTerm = userTermsOfUseHome.create();

            Properties p2 = new Properties();
            p2.put(Context.INITIAL_CONTEXT_FACTORY,
                    "org.jnp.interfaces.NamingContextFactory");
            p2.put(Context.URL_PKG_PREFIXES,
                    "org.jboss.naming:org.jnp.interfaces");
            p2.put(Context.PROVIDER_URL, projectBeanProviderUrl);

            Context c2 = new InitialContext(p2);
            ProjectRoleTermsOfUseHome projectRoleTermsOfUseHome = (ProjectRoleTermsOfUseHome) c2.lookup(ProjectRoleTermsOfUseHome.EJB_REF_NAME);

            ProjectRoleTermsOfUse projectTerm = projectRoleTermsOfUseHome.create();

            List<Long>[] necessaryTerms = projectTerm.getTermsOfUse((int) projectId, roleIds, "java:/DS");

            // if project does not have term
            if (necessaryTerms == null || necessaryTerms.length == 0) {
                return true;
            }

            for (int i = 0; i < necessaryTerms.length; i++) {
                if (necessaryTerms[i] != null) {
                    for (int j = 0; j < necessaryTerms[i].size(); j++) {
                        Long termId = necessaryTerms[i].get(j);
                        // if user has not agreed
                        if (!userTerm.hasTermsOfUse(userId, termId, "java:/DS")) {
                            return false;
                        }
                    }
                }
            }

            logger.debug("checkTerms (" + projectId + ", " + userId + ", " + roleIds + ")");
            return true;

        } catch (Exception e) {
            logger.error("*** error in checkTerms (" + projectId + ", " + userId + ", " + roleIds + ")");

            logger.error(e);
            return false;
        }
    }

    /**
     * <p>
     * Checks whether the user is eligible to the contest.
     * </p>
     *
     * @param userId    the id of the user.
     * @param contestId the id of the contest.
     * @param isStudio  whether the contest is a studio contest.
     * @return true if user is eligible, otherwise false.
     * @throws PermissionServiceException if any error.
     */
    private boolean isEligible(long userId, long contestId, boolean isStudio)
            throws PermissionServiceException {
        String methodName = "isEligible";
        logger.info("Enter: " + methodName);

        boolean eligible = false;

        try {
            List<ContestEligibility> eligibilities = contestEligibilityManager.getContestEligibility(contestId,
                    isStudio);
            eligible = contestEligibilityValidationManager.validate(userId, eligibilities);
        } catch (ContestEligibilityPersistenceException e) {
            logger.error(e.getMessage(), e);
            throw new PermissionServiceException(e.getMessage(), e);
        } catch (ContestEligibilityValidationManagerException e) {
            logger.error(e.getMessage(), e);
            throw new PermissionServiceException(e.getMessage(), e);
        }

        logger.info("Exit: " + methodName);
        return eligible;
    }
}
