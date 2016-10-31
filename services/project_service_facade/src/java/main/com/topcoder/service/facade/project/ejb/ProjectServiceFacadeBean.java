/*
 * Copyright (C) 2008 - 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.facade.project.ejb;

import com.topcoder.clientcockpit.phases.EmailMessageGenerationException;
import com.topcoder.clientcockpit.phases.EmailMessageGenerator;
import com.topcoder.clientcockpit.phases.EmailSendingException;
import com.topcoder.clientcockpit.phases.messagegenerators.DefaultEmailMessageGenerator;
import com.topcoder.clients.dao.ClientDAO;
import com.topcoder.clients.dao.ClientStatusDAO;
import com.topcoder.clients.dao.CompanyDAO;
import com.topcoder.clients.dao.DAOException;
import com.topcoder.clients.dao.EntityNotFoundException;
import com.topcoder.clients.dao.ProjectDAO;
import com.topcoder.clients.dao.ProjectStatusDAO;
import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.Project;
import com.topcoder.clients.model.ProjectStatus;
import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.persistence.ConfigurationFileManager;
import com.topcoder.message.email.EmailEngine;
import com.topcoder.message.email.TCSEmailMessage;
import com.topcoder.service.project.entities.ProjectQuestion;
import com.topcoder.service.facade.project.DAOFault;
import com.topcoder.service.facade.project.EntityNotFoundFault;
import com.topcoder.service.facade.project.notification.DirectProjectNotification;
import com.topcoder.service.permission.ProjectPermission;
import com.topcoder.service.project.IllegalArgumentFault;
import com.topcoder.service.project.InvalidBillingAccountForProjectFault;
import com.topcoder.service.project.PersistenceFault;
import com.topcoder.service.project.ProjectCategory;
import com.topcoder.service.project.ProjectService;
import com.topcoder.service.project.ProjectData;
import com.topcoder.service.project.ProjectNotFoundFault;
import com.topcoder.service.project.AuthorizationFailedFault;
import com.topcoder.service.project.ProjectServiceFault;
import com.topcoder.service.project.ProjectType;
import com.topcoder.service.project.UserNotFoundFault;
import com.topcoder.security.RolePrincipal;
import com.topcoder.security.TCSubject;
import com.topcoder.service.permission.Permission;
import com.topcoder.service.permission.PermissionService;
import com.topcoder.service.permission.PermissionServiceException;
import com.topcoder.service.permission.PermissionType;
import com.topcoder.service.user.UserService;
import com.topcoder.shared.util.DBMS;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.ConfigManagerException;
import com.topcoder.util.errorhandling.BaseException;
import com.topcoder.util.errorhandling.ExceptionUtils;
import com.topcoder.util.file.DocumentGenerator;
import com.topcoder.util.file.DocumentGeneratorFactory;
import com.topcoder.util.file.Template;
import com.topcoder.web.common.RowNotFoundException;
import com.topcoder.web.ejb.forums.Forums;
import com.topcoder.web.ejb.forums.ForumsHome;
import com.topcoder.web.ejb.user.UserPreference;
import com.topcoder.web.ejb.user.UserPreferenceHome;

import javax.annotation.PostConstruct;
import javax.ejb.CreateException;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.EJB;
import javax.annotation.Resource;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.jboss.logging.Logger;

/**
 * <p>This is an implementation of <code>Project Service Facade</code> web service in form of stateless session EJB. It
 * holds a reference to {@link ProjectService} which is delegated the fulfillment of requests.</p>
 * </p>
 * <p>
 *  Changes in v1.0.1 Moved insert permission when creating project.
 * </p>
 * <p>
 * Changes in v1.0.2(Cockpit Security Facade V1.0):
 *  - It is not a web-service facade any more.
 *  - All the methods accepts a parameter TCSubject which contains all the security info for current user.
 *    The implementation EJB should use TCSubject and now get these info from the sessionContext.
 *  - Please use the new ProjectServiceFacadeWebService as the facade now. That interface will delegates all the methods
 *    to this interface.
 * </p>
 *
 * <p>
 * Version 1.0.3 (Release Assembly - TC Cockpit Create Project Refactoring Assembly Part Two) change notes:
 *   <ol>
 *       <li>Added {@link #createTopCoderDirectProjectForum(TCSubject, long, Long)} method along with private helper
 *       methods.</li>
 *       <li>Updated {@link #createProject(TCSubject, ProjectData)} method to create project forum.</li>
 *       <li>Added {@link #logger} property.</li>
 *       <li>Added {@link #createForum} property.</li>
 *       <li>Added {@link #forumBeanProviderUrl} property.</li>
 *       <li>Added {@link #DEFAULT_NAMESPACE} constant.</li>
 *       <li>Added {@link #RESOURCE_ROLE_OBSERVER_ID} constant.</li>
 *       <li>Added {@link #createTCDirectProject(TCSubject, ProjectData, List)} method.</li>
 *   </ol>
 * </p>
 *
 *  * <p>
 *     Version 2.0 (Module Assembly - TC Cockpit Direct Project Related Services Update and Integration)
 *     <ul>
 *         <li>
 *             Add method {@link #getAllProjectTypes()}
 *         </li>
 *         <li>
 *             Add method {@link #getProjectCategoriesByProjectType(long)}
 *         </li>
 *         <li>
 *             Add method {@link #getBillingAccountsByProject(long)}
 *         </li>
 *         <li>
 *             Add method {@link #getBillingAccountsByClient(long)}
 *         </li>
 *         <li>
 *             Add method {@link #getProjectsByBillingAccount(long)}
 *         </li>
 *         <li>
 *             Add method {@link #addBillingAccountToProject(long, long)}
 *         </li>
 *         <li>
 *             Add method {@link #removeBillingAccountFromProject(long, long)}
 *         </li>
 *         <li>
 *             Add method {@link #getProjectsByClient(long)}
 *         </li>
 *     </ul>
 * </p>
 *
 * <p>
 *     Version 2.1 (Release Assembly - TC Cockpit Project Forum Settings) changes:
 *     <ul>
 *         <li>Add method {@link #getProjectNotifications(com.topcoder.security.TCSubject, long)}</li>
 *         <li>Add method {@link #updateProjectNotifications(com.topcoder.security.TCSubject, long, java.util.List)}</li>
 *     </ul>
 * </p>
 *
 * <p>
 *     Version 2.2 (Release Assembly - TopCoder Cockpit Project Dashboard Project Type and Permission Notifications Integration) changes:
 *     <ul>
 *         <li>Add method {@link #getWatchedUserIdsForProjectForum(com.topcoder.security.TCSubject, java.util.List, long)}</li>
 *     </ul>
 * </p>
 * 
 * <p>
 *     Version 2.3(TC Cockpit - Create New Project BackEnd 1.0) change log:
 *     <ul>
 *       <li>
 *       A new method <code>getProjectQuestions</code> is added to be used to get all project questions available 
 *       in the persistence. This method uses the aggregated ProjectService implementation instance to get the 
 *       project questions from the persistence.
 *       </li>
 *     </ul>
 * </p>
 *
 * <p>
 *     Version 2.4 (Release Assembly - TC Direct Project Forum Configuration Assembly) changes:
 *     <ul>
 *         <li>Changed {@link #createTopCoderDirectProjectForum(TCSubject, long, Long, Map)} and add a new argument to
 *         support project forum configuration.</li>
 *         <li>Changed {@link #createTCDirectProject(TCSubject, ProjectData, List, Map)} to add a new argument.</li>
 *         <li>Added {@link #createProject(TCSubject, ProjectData, Map)} to add a new argument.</li>
 *     </ul>
 * </p>
 * 
 * <p>
 *     Version 2.5 (Release Assembly - TC Direct Project Forum Configuration Assembly 2) changes:
 *     <ul>
 *         <li>Added {@link #addTopCoderDirectProjectForum(long, String, String)} to add a direct project forum.</li>
 *         <li>Added {@link #deleteTopCoderDirectProjectForum(long, long)} to delete a direct project forum.</li>
 *     </ul>
 * </p>
 *
 * <p>
 * Version 2.6 (Release Assembly - TopCoder Direct Project Audit v1.0) change notes:
 *   <ol>
 *      <li>Updated {@link #createProject(TCSubject, ProjectData, Map)}, 
 *      {@link #createTCDirectProject(TCSubject, ProjectData, List, Map)}
 *      methods to pass <code>TCSubject</code> instance to the updated methods.</li>
 *   </ol> 
 * </p>
 *
 * <P>
 * Version 2.7 (Release Assembly - TopCoder Direct Cockpit Release Assembly Ten)
 *
 * </P>
 *
 * <p>
 * Version 2.8 (Release Assembly - TC Cockpit Start Project Flow Billing Account Integration)
 * - Set project billing account when creating new project is the billing account is set.
 * </p>
 * <p> 
 * Version 2.9 (TOPCODER DIRECT - MAKE FORUM CREATION OPTIONAL) changes:
 *     <ul>
 *         <li>Added {@link #createProject(TCSubject, ProjectData, boolean)}  to enable or disable forum creation.</li>
 *         <li>Refactored the other createProject to use a common createProjectInternal method.</li>
 *         <li>Updated createTopCoderDirectProjectForum method to log the method entry at INFO level</li>
 *     </ul>
 * </p>
 *
 * @author isv, waits, GreatKevin, duxiaoyang, GreatKevin, TCSCODER
 * @version 2.9
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ProjectServiceFacadeBean implements ProjectServiceFacadeLocal, ProjectServiceFacadeRemote {

    /**
     * <p>The default configuration namespace.</p>
     *
     * @since 1.0.3
     */
    private static final String DEFAULT_NAMESPACE = ProjectServiceFacadeBean.class.getName();

    /**
     * <p>Private constant specifying resource role observer id</p>
     *
     * @since 1.0.3
     */
    private static final long RESOURCE_ROLE_OBSERVER_ID = 12;

    /**
     * The user preference id of global project forum watch.
     */
    private final static int GLOBAL_PROJECT_FORUM_WATCH = 31;

    /**
     * <p>The id of the active project status</p>
     * @since 2.1
     */
    private final static long DIRECT_PROJECT_ACTIVE_STATUS_ID = 1L;

    /**
     * <p>A <code>ProjectService</code> providing access to available <code>Project Service EJB</code>. This bean is
     * delegated to process the calls to the methods inherited from <code>Project Service</code> component.</p>
     */
    @EJB(name = "ejb/ProjectService")
    private ProjectService projectService = null;
    /**
     * <p>A <code>ClientDAO</code> providing access to available <code>Client DAO EJB</code>. This bean is
     * delegated to process the calls to the methods inherited from <code>Client Project Entities and DAO</code>
     * component.</p>
     */
    @EJB(name = "ejb/ClientDAOBean")
    private ClientDAO clientDAO = null;
    /**
     * <p>A <code>ClientStatusDAO</code> providing access to available <code>ClientStatusDAOBean EJB</code>. It is
     * delegated to process the calls to the methods inherited from <code>Client Project Entities and DAO</code>
     * component.</p>
     */
    @EJB(name = "ejb/ClientStatusDAOBean")
    private ClientStatusDAO clientStatusDAO = null;
    /**
     * <p>A <code>CompanyDAO</code> providing access to available <code>ClientStatusDAOBean EJB</code>. This bean is
     * delegated to process the calls to the methods inherited from <code>Client Project Entities and DAO</code>
     * component.</p>
     */
    @EJB(name = "ejb/CompanyDAOBean")
    private CompanyDAO companyDAO = null;
    /**
     * <p>A <code>ProjectDAO</code> providing access to available <code>ClientStatusDAOBean EJB</code>. This bean is
     * delegated to process the calls to the methods inherited from <code>Client Project Entities and DAO</code>
     * component.</p>
     */
    @EJB(name = "ejb/ProjectDAOBean")
    private ProjectDAO projectDAO = null;
    /**
     * <p>A <code>ProjectStatusDAO</code> providing access to available <code>ClientStatusDAOBean EJB</code>. It is
     * delegated to process the calls to the methods inherited from <code>Client Project Entities and DAO</code>
     * component.</p>
     */
    @EJB(name = "ejb/ProjectStatusDAOBean")
    private ProjectStatusDAO projectStatusDAO = null;
    /**
     * <p>
     * A <code>UserService</code> providing access to available <code>User Service EJB</code>.
     * </p>
     * @since v1.0.2 adding TCSubject
     */
    @EJB(name = "ejb/UserService")
    private UserService userService = null;

    /**
     * <p>
     * A <code>PermissionService</code> providing access to available
     * <code>Permission Service EJB</code>. This bean is delegated to process
     * the calls for CRUD on permissions.
     * </p>
     *
     * @since TopCoder Service Layer Integration 3 Assembly
     */
    @EJB(name = "ejb/PermissionService")
    private PermissionService permissionService = null;

    /**
     * The FROM email address of the direct project creation notification email.
     *
     * @since 2.7
     */
    private String projectCreationEmailFromAddress;

    /**
     * The TO email addresses of the direct project creation notification email.
     *
     * @since 2.7
     */
    private String projectCreationEmailToAddressesConfig;

    /**
     * The CC email addresses of the direct project creation notification email.
     *
     * @since 2.7
     */
    private String projectCreationEmailCCAddressesConfig;

    /**
     * The subject of the direct project creation notification email.
     *
     * @since 2.7
     */
    @Resource(name = "projectCreationEmailSubject")
    private String projectCreationEmailSubject;

    /**
     * The path of the email template for the direct project creation notification email.
     *
     * @since 2.7
     */
    private String projectCreationEmailTemplatePath;

    /**
     * <p>
     * The instance of SessionContext that was injected by the EJB container.
     * </p>
     * <p>
     * Valid Values: sessionContext objects (possibly null).
     * </p>
     */
    @Resource
    private SessionContext sessionContext;


    /**
     * The configuration file for the document manager.
     *
     * @since 2.7
     */
    @Resource(name = "documentManagerConfigFile")
    private String documentManagerConfigFile;

    /**
     * Private constant specifying administrator role.
     */
    private static final String ADMIN_ROLE = "Cockpit Administrator";
    /**
     * Private constant specifying user role.
     */
    //private static final String USER_ROLE = "Cockpit User";

    /**
     * Private constant specifying 'project_full' permission type name
     * @since 1.0.1
     *
     */
    private static final String PERMISSION_TYPE_PROJECT_FULL_NAME = "project_full";

    /**
     * Email file template source key that is used by email generator.
     *
     * @since 2.7
     */
    private static final String EMAIL_FILE_TEMPLATE_SOURCE_KEY = "fileTemplateSource";

    /**
     * Private constant specifying 'project_full' permission type id
     * @since 1.0.1
     *
     */
    private static final long ERMISSION_TYPE_PROJECT_FULL_ID = 3;

    /**
     * userBeanProviderUrl is used in the jndi context to get the user bean.
     * It's injected, non-null and non-empty after set.
     */
    private String userBeanProviderUrl;

    /**
     * <p>forumBeanProviderUrl is used in the jndi context to get the forum bean in the createForum method. It's
     * injected, non-null and non-empty after set.</p>
     *
     * @since 1.0.3
     */
    private String forumBeanProviderUrl;

    /**
     * Document generator that stores email templates.
     *
     * @since Cockpit Release Assembly for Receipts
     * @since 2.7
     */
    private DocumentGenerator documentGenerator;

    /**
     * Email generator that generates email message from given template.
     *
     * @since 2.7
     */
    private EmailMessageGenerator emailMessageGenerator;

    /**
     * <p>A flag indicating whether or not create the forum. It's injected, used in the createSoftwareContest method.
     * </p>
     *
     * @since 1.0.3
     */
    private boolean createForum = false;

    /**
     * <p>The logger instance for logging the information in ProjectServiceFacadeBean.</p>
     *
     * @since 1.0.3
     */
    private Logger logger = Logger.getLogger(this.getClass());

    /**
     * <p> This initializes the API Profile to the <code>CallerServices</code>. The API profile are the merchant's (in
     * this case TopCoder) PayPal API details. </p>
     *
     * <p> TopCoder Service Layer Integration 3 Assembly change: new instance of the DefaultUploadServices for exposing
     * its methods. </p>
     *
     * <p> Updated for Cockpit Release Assembly for Receipts - documentGenerator and emailMessageGenerator instance
     * created. </p>
     *
     * @throws IllegalStateException it throws this exception on any issues during caller services initialization.
     * Issues can be: wrong authentication information, invalid information etc.
     */
    @PostConstruct
    public void init() {
        if (logger == null) {
            logger = Logger.getLogger(this.getClass());
        }
        logger.debug("Initializing ProjectServiceFacadeBean");

        ConfigManager configManager = ConfigManager.getInstance();
        try {
            String createForumProp = configManager.getString(DEFAULT_NAMESPACE, "createForum");
            createForum = Boolean.parseBoolean(createForumProp);
            forumBeanProviderUrl = configManager.getString(DEFAULT_NAMESPACE, "forumBeanProviderUrl");
            userBeanProviderUrl = configManager.getString(DEFAULT_NAMESPACE, "userBeanProviderUrl");
            projectCreationEmailToAddressesConfig = configManager.getString(DEFAULT_NAMESPACE, "projectCreationEmailToAddresses");
            projectCreationEmailCCAddressesConfig = configManager.getString(DEFAULT_NAMESPACE, "projectCreationEmailCCAddresses");
            projectCreationEmailTemplatePath = configManager.getString(DEFAULT_NAMESPACE, "projectCreationEmailTemplatePath");
            projectCreationEmailFromAddress = configManager.getString(DEFAULT_NAMESPACE, "projectCreationEmailFromAddress");
        } catch (ConfigManagerException e) {
            throw new IllegalStateException("Unable to read property from config file", e);
        }

        try {
            documentGenerator = getDocumentGenerator();
        } catch (ConfigManagerException e) {
            throw new IllegalStateException("Failed to create the documentGenerator instance.",
                                            e);
        }

        // the default email message generator.
        emailMessageGenerator = new DefaultEmailMessageGenerator();
    }

    /**
     * <p>Creates a project with the given project data.</p>
     *
     * <p>Note, any user can create project and the project will associate with him/her.</p>
     * <p>
     * Update in v1.0.2: add parameter TCSubject which contains the security info for current user.
     * </p>
     * <p>
     * Update in version 2.4: invokes {@link #createProject(TCSubject, ProjectData, Map)} method.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param projectData The project data to be created. Must not be null. The <code>ProjectData.name</code> must not be
     * null/empty. The <code>ProjectData.projectId</code>, if any, is ignored.
     * @return The project as it was created, with the <code>ProjectData.projectId</code> and <code>ProjectData.userId
     *         </code> set. Will never be null.
     * @throws IllegalArgumentFault if the given <code>ProjectData</code> is illegal.
     * @throws PersistenceFault if a generic persistence error occurs.
     * @see ProjectService#createProject(TCSubject, ProjectData)
     */
    public ProjectData createProject(TCSubject tcSubject, ProjectData projectData)
        throws PersistenceFault, IllegalArgumentFault
    {
        return createProjectInternal(tcSubject, projectData, true, null);
    }

    /**
     * <p>Creates a project with the given project data.</p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param projectData
     *            The project data to be created. Must not be null.
     *            The <code>ProjectData.name</code> must not be null/empty.
     *            The <code>ProjectData.projectId</code>, if any, is ignored.
     * @param withForum indicates the forum should be created or not
     * @return The project as it was created, with the <code>ProjectData.projectId</code> and <code>ProjectData.userId
     *         </code> set. Will never be null.
     * @throws IllegalArgumentFault if the given <code>ProjectData</code> is illegal.
     * @throws PersistenceFault if a generic persistence error occurs.
     * @see ProjectService#createProject(ProjectData)
     * @since 2.9
     */
    public ProjectData createProject(TCSubject tcSubject, ProjectData projectData, boolean withForum) 
        throws PersistenceFault, IllegalArgumentFault {

        return createProjectInternal(tcSubject, projectData, withForum, null);
    }


    /**
     * <p>Creates a project with the given project data and forum templates.</p>
     *
     *
     * <p>
     * Update in version 2.7 (Release Assembly - TopCoder Direct Cockpit Release Assembly Ten)
     * - Send an notification email when the project is successfully created.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param projectData The project data to be created. Must not be null. The <code>ProjectData.name</code> must not be
     * null/empty. The <code>ProjectData.projectId</code>, if any, is ignored.
     * @param forums a list of project forum templates configuration.
     * @return The project as it was created, with the <code>ProjectData.projectId</code> and <code>ProjectData.userId
     *         </code> set. Will never be null.
     * @throws IllegalArgumentFault if the given <code>ProjectData</code> is illegal, or <code>forums</code> list
     *         contains <code>null</code> key/value.
     * @throws PersistenceFault if a generic persistence error occurs.
     * @see ProjectService#createProject(TCSubject, ProjectData)
     */
    public ProjectData createProject(TCSubject tcSubject, ProjectData projectData, Map<String, String> forums)
        throws PersistenceFault, IllegalArgumentFault
    {
		return createProjectInternal(tcSubject, projectData, true, forums);
    }


    /**
     * <p>Creates a project with the given project data and forum templates.</p>
     *
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param projectData The project data to be created. Must not be null. The <code>ProjectData.name</code> must not be
     * null/empty. The <code>ProjectData.projectId</code>, if any, is ignored.
     * @param withForum indicates the forum should be created or not
     * @param forums a list of project forum templates configuration.
     * @return The project as it was created, with the <code>ProjectData.projectId</code> and <code>ProjectData.userId
     *         </code> set. Will never be null.
     * @throws IllegalArgumentFault if the given <code>ProjectData</code> is illegal, or <code>forums</code> list
     *         contains <code>null</code> key/value.
     * @throws PersistenceFault if a generic persistence error occurs.
     * @see ProjectService#createProject(TCSubject, ProjectData)
     */
    private ProjectData createProjectInternal(TCSubject tcSubject, ProjectData projectData, boolean withForum, Map<String, String> forums)
        throws PersistenceFault, IllegalArgumentFault
    {
        if (forums != null) {
            for (String key : forums.keySet()) {
                if (key == null || forums.get(key) == null) {
                    throw new IllegalArgumentFault("Forums map contains NULL key/value");
                }
            }
        }

        try
        {
            ProjectData result = this.projectService.createProject(tcSubject, projectData);

            // add permission for project creator with project_full
            long userId = tcSubject.getUserId();
            Permission perm = new Permission();
            perm.setUserId(userId);
            perm.setResourceId(result.getProjectId());
            PermissionType permType = new PermissionType();
            permType.setName(PERMISSION_TYPE_PROJECT_FULL_NAME);
            permType.setPermissionTypeId(ERMISSION_TYPE_PROJECT_FULL_ID);
            perm.setPermissionType(permType);
            perm.setStudio(false);
            Permission[] permissions = new Permission[1];
            permissions[0] = perm;
            permissionService.updatePermissions(tcSubject, permissions);

            if (withForum) {
                long forumID = createTopCoderDirectProjectForum(tcSubject, result.getProjectId(), null, forums);

                projectData.setForumCategoryId(String.valueOf(forumID));
                result.setForumCategoryId(String.valueOf(forumID));
            }

            // send project creation notification email
            com.topcoder.project.phases.Phase phase = new com.topcoder.project.phases.Phase();

            phase.setAttribute("PROJECT_NAME", result.getName());
            phase.setAttribute("PROJECT_ID", result.getProjectId());
            phase.setAttribute("PROJECT_CREATION_TIME", new Date());
            phase.setAttribute("CREATION_USER", getUserName(tcSubject));

            // set to default template
            String templateToUse = projectCreationEmailTemplatePath;

            String file = Thread.currentThread().getContextClassLoader().getResource(
                    templateToUse).getFile();
            Logger.getLogger(this.getClass()).debug("File name for template: " + file);

            final String[] toAddrs = projectCreationEmailToAddressesConfig.split(";");
            final String[] ccAddrs = projectCreationEmailCCAddressesConfig.split(";");

            return result;
        } catch (PermissionServiceException e) {
            sessionContext.setRollbackOnly();
            e.printStackTrace(System.err);
            throw new PersistenceFault(e.getMessage());
        } catch (PersistenceFault e) {
            sessionContext.setRollbackOnly();
            e.printStackTrace(System.err);
            throw e;
        } catch (Exception e) {
            sessionContext.setRollbackOnly();
            e.printStackTrace(System.err);
            throw new PersistenceFault(e.getMessage());
        }


    }

    /**
     * <p>Gets the project data for the project with the given Id.</p>
     *
     * <p>Notes, only associated user can retrieve the specified project, administrator can retrieve any projects.</p>
     *<p>
     * Update in v1.0.2: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param projectId the ID of the project to be retrieved.
     * @return The project data for the project with the given Id. Will never be null.
     * @throws PersistenceFault if a generic persistence error.
     * @throws ProjectNotFoundFault if no project with the given ID exists.
     * @throws AuthorizationFailedFault if the calling principal is not authorized to retrieve the project.
     * @see ProjectService#getProject(TCSubject, long)
     */
    public ProjectData getProject(TCSubject tcSubject, long projectId) throws PersistenceFault, ProjectNotFoundFault,
                                                                              AuthorizationFailedFault {
        return this.projectService.getProject(tcSubject,projectId);
    }

    /**
     * <p>Gets the project data for all projects of the given user.</p>
     *
     * <p>Notes, only administrator can do this.</p>
     * <p>
     * Update in v1.0.2: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @param userId the ID of the user whose projects are to be retrieved.
     * @return The project data for all projects of the given user. The returned collection will not be null or contain
     *         nulls. Will never be empty.
     * @throws UserNotFoundFault if there are no projects linked to the given user.
     * @throws PersistenceFault if a generic persistence error occurs.
     * @throws AuthorizationFailedFault should not be thrown from version 1.1. It is here only to leave the web interface
     * unchanged.
     * @see ProjectService#getProjectsForUser(long)
     */
    public List<ProjectData> getProjectsForUser(TCSubject tcSubject, long userId) throws PersistenceFault,
                                                                                         UserNotFoundFault,
                                                                                         AuthorizationFailedFault {
        return this.projectService.getProjectsForUser(userId);
    }

    /**
     * <p>Gets the project data for all projects viewable from the calling principal.</p>
     *
     * <p>Notes, for user, it will retrieve only the projects associated with him; for administrators, it will retrieve all
     * the existing projects.</p>
     *
     * <p>
     * Updated for Cockpit Project Admin Release Assembly v1.0
     *      - Added check for admin user, if admin user then all projects are loaded else only for the user.
     * </p>
     * <p>
     * Update in v1.0.2: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @return The project data for all projects viewable from the calling principal. The returned collection will not be
     *         null or contain nulls. Possibly empty.
     * @throws PersistenceFault if a generic persistence error occurs.
     * @see ProjectService#getAllProjects()
     */

    public List<ProjectData> getAllProjects(TCSubject tcSubject) throws PersistenceFault, AuthorizationFailedFault, UserNotFoundFault {
        if (isRole(tcSubject, ADMIN_ROLE)) {
            return this.projectService.getAllProjects();
        } else {
            return this.projectService.getProjectsForUser(tcSubject.getUserId());
        }
    }

    /**
     * <p>
     * Checks if the login user is admin or not.
     * </p>
     *
     * @param tcSubject TCSubject instance for login user
     * @return true if it is admin
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
     * <p>Updates the project data for the given project.</p>
     *
     * <p>Notes, only project-associated user can update that project, but administrator can update any project.</p>
     *
     * @param projectData The project data to be updated. Must not be null. The <code>ProjectData.name</code> must not be
     * null/empty. The <code>ProjectData.projectId</code> must be non-null.
     * @throws IllegalArgumentFault if the given <code>ProjectData</code> is illegal.
     * @throws ProjectNotFoundFault if no project with the given ID exists.
     * @throws AuthorizationFailedFault if the calling principal is not authorized to update the project.
     * @throws PersistenceFault if a generic persistence error.
     * @see ProjectService#updateProject(TCSubject, ProjectData)
     */
    public void updateProject(TCSubject tcSubject, ProjectData projectData) throws PersistenceFault, ProjectNotFoundFault,
                                                                        AuthorizationFailedFault, IllegalArgumentFault {
        this.projectService.updateProject(tcSubject, projectData);
    }


    /**
     * <p>
     * Defines the operation that performs the retrieval of the list with
     * projects with the given client from the persistence. If nothing is found,
     * return an empty list.
     * </p>
     *
     * @param client
     *                the given clients to retrieve it's projects. Should not be
     *                null.
     * @return the list of Projects for the given client found in the
     *         persistence. If nothing is found, return an empty list.
     * @throws IllegalArgumentException
     *                 if client is null.
     * @throws EntityNotFoundFault
     *                 if client is not found in the persistence.
     * @throws DAOFault
     *                 if any error occurs while performing this operation.
     */
    public List<Project> getClientProjectsForClient(Client client) throws DAOFault {
        try {
            return this.clientDAO.getProjectsForClient(client);
        } catch(EntityNotFoundException e) {
            throw new EntityNotFoundFault(e.getMessage(), e.getCause());
        } catch (DAOException e) {
            throw new DAOFault(e.getMessage(), e.getCause());
        }
    }



    /**
     * <p>
     * Defines the operation that performs the retrieval of a project using the
     * given id from the persistence. If include children is true return the
     * Project.childProjects list too; otherwise the list should not be
     * returned.
     * </p>
     *
     * @param id
     *                the identifier of the Project that should be retrieved.
     *                Should be positive and not null.
     * @param includeChildren
     *                the flag that mention if the Project.childrenProjects list
     *                should be returned or not.
     * @return the Project with the given id retrieved from the persistence.
     * @throws IllegalArgumentException
     *                 if id <= 0 or id is null.
     * @throws EntityNotFoundFault
     *                 if id is not found in the persistence.
     * @throws DAOFault
     *                 if any error occurs while performing this operation.
     */
    //@WebMethod
    public Project retrieveClientProjectById(Long id, boolean includeChildren) throws EntityNotFoundFault, DAOFault {
        try {
            return this.projectDAO.retrieveById(id, includeChildren);
        } catch(EntityNotFoundException e) {
            throw new EntityNotFoundFault(e.getMessage(), e.getCause());
        } catch (DAOException e) {
            throw new DAOFault(e.getMessage(), e.getCause());
        }
    }

    /**
     * <p>
     * Defines the operation that performs the retrieval of all projects from
     * the persistence. If include children is true return the
     * Project.childProjects list too; otherwise the list should not be
     * returned. If nothing is found, return an empty list.
     * </p>
     *
     * @param includeChildren
     *                the flag that mention if the Project.childrenProjects list
     *                should be returned or not.
     * @return the list of Projects found in the persistence. If nothing is
     *         found, return an empty list.
     * @throws DAOFault
     *                 if any error occurs while performing this operation.
     */
 //   @WebMethod
    public List<Project> retrieveClientProjects(boolean includeChildren) throws DAOFault {
        try {
            return this.projectDAO.retrieveAll(includeChildren);
        } catch (DAOException e) {
            throw new DAOFault(e.getMessage(), e.getCause());
        }
    }
    /**
     * <p>
     * Defines the operation that performs the retrieval of an entity using the
     * given id from the persistence.
     * </p>
     *
     * @param id
     *                the identifier of the entity that should be retrieved.
     *                Should be positive and not null.
     * @return the entity with the given id retrieved from the persistence.
     * @throws IllegalArgumentException
     *                 if id <= 0 or id is null.
     * @throws EntityNotFoundFault
     *                 if an entity for the given id is not found in the
     *                 persistence.
     * @throws DAOFault
     *                 if any error occurs while performing this operation.
     */
   // @WebMethod
    public Project retrieveClientProjectByProjectId(Long id) throws EntityNotFoundFault, DAOFault {
        try {
            return this.projectDAO.retrieveById(id);
        } catch(EntityNotFoundException e) {
            throw new EntityNotFoundFault(e.getMessage(), e.getCause());
        } catch (DAOException e) {
            throw new DAOFault(e.getMessage(), e.getCause());
        }
    }

    /**
     * <p>
     * Defines the operation that performs the retrieval of all entities from
     * the persistence. If nothing is found, return an empty list.
     * </p>
     *
     * @return the list of entities found in the persistence. If nothing is
     *         found, return an empty list.
     * @throws DAOFault
     *                 if any error occurs while performing this operation.
     */
    //@WebMethod
    public List<Project> retrieveAllClientProjects() throws DAOFault {
        try {
            return this.projectDAO.retrieveAll();
        } catch (DAOException e) {
            throw new DAOFault(e.getMessage(), e.getCause());
        }
    }



    /**
     * <p>
     * Defines the operation that performs the retrieval of the list with
     * projects with the given status from the persistence. If nothing is found,
     * return an empty list.
     * </p>
     *
     * @param status
     *                the given project status to retrieve it's projects. Should
     *                not be null.
     * @return the list of Projects for the given project status found in the
     *         persistence. If nothing is found, return an empty list.
     * @throws IllegalArgumentException
     *                 if status is null.
     * @throws EntityNotFoundFault
     *                 if status is not found in the persistence.
     * @throws DAOFault
     *                 if any error occurs while performing this operation.
     */
    //@WebMethod
    public List<Project> getClientProjectsWithStatus(ProjectStatus status) throws DAOFault {
        try {
            return this.projectStatusDAO.getProjectsWithStatus(status);
        } catch(EntityNotFoundException e) {
            throw new EntityNotFoundFault(e.getMessage(), e.getCause());
        } catch (DAOException e) {
            throw new DAOFault(e.getMessage(), e.getCause());
        }
    }
    /**
     * <p>
     * Defines the operation that performs the retrieval of an entity using the
     * given id from the persistence.
     * </p>
     *
     * @param id
     *                the identifier of the entity that should be retrieved.
     *                Should be positive and not null.
     * @return the entity with the given id retrieved from the persistence.
     * @throws IllegalArgumentException
     *                 if id <= 0 or id is null.
     * @throws EntityNotFoundFault
     *                 if an entity for the given id is not found in the
     *                 persistence.
     * @throws DAOFault
     *                 if any error occurs while performing this operation.
     */
    //@WebMethod
    public ProjectStatus retrieveProjectStatusById(Long id) throws EntityNotFoundFault, DAOFault {
        try {
            return this.projectStatusDAO.retrieveById(id);
        } catch(EntityNotFoundException e) {
            throw new EntityNotFoundFault(e.getMessage(), e.getCause());
        } catch (DAOException e) {
            throw new DAOFault(e.getMessage(), e.getCause());
        }
    }

    /**
     * <p>
     * Get the user-name for the login user represented by TCSubject.
     * </p>
     * @param tcSubject for login user info
     * @return user name
     * @throws DAOException if any error occurs
     */
    private String getUserName(TCSubject tcSubject) throws DAOException {
        try {
            return this.userService.getUserHandle(tcSubject.getUserId());
        } catch (Exception e) {
            throw new DAOException("Fail to get the user-name by user-id" + tcSubject.getUserId(), e);
        }
    }

    /**
     * <p>
     * Defines the operation that performs the retrieval of the list with
     * projects with the given user id. If nothing is found, return an empty list.
     * <p>
     * <p>
     * Update in v1.0.2: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @return List of Project, if nothing is found, return an empty string
     * @throws DAOException if any error occurs while performing this operation.
     */
    public List<Project> getClientProjectsByUser(TCSubject tcSubject) throws DAOFault {

        try {
            if (isRole(tcSubject, ADMIN_ROLE)) {
                return this.projectDAO.retrieveAllProjectsOnly();
            } else {
                return this.projectDAO.getProjectsByUser(getUserName(tcSubject));
            }


        } catch(EntityNotFoundException e) {
            throw new EntityNotFoundFault(e.getMessage(), e.getCause());
        } catch (DAOException e) {
            throw new DAOFault(e.getMessage(), e.getCause());
        }
    }

    /**
     * <p>Creates the forum for the specified <code>TC Direct</code> project.</p>
     *
     * @param currentUser           a <code>TCSubject</code> representing the current user.
     * @param projectId             a <code>long</code> providing the ID of <code>TC Direct</code> project to create
     *                              forum for.
     * @param tcDirectProjectTypeId a <code>Long</code> referencing the type of <code>TC Direct</code> project. May be
     *                              <code>null</code>.
	 * @param forumConfig           a list of project forum templates configuration.
     * @return a <code>long</code> providing the ID of created forum.
     * @throws IllegalArgumentException if project cannot be found or it already has forum created, or given
     *         <code>forumConfig</code> contains null key/value.
     * @throws ProjectServiceFault if an unexpected error occurs.
     * @since 1.0.3
     */
    public long createTopCoderDirectProjectForum(TCSubject currentUser, long projectId, Long tcDirectProjectTypeId,
            Map<String, String> forumConfig) throws ProjectServiceFault {
        long userId = currentUser.getUserId();
        logger.info("createTopCoderDirectProjectForum (projectId = " + projectId + ", userId = " + userId
                     + ", createProjectForum = )" + createForum);

        if (!createForum) {
            return System.currentTimeMillis();
        }

        try {
            // Get project data
            ProjectData projectData = getProject(currentUser, projectId);

            if (projectData == null) {
                throw new IllegalArgumentException("The direct project id to create forum does not exist");
            }

            if (projectData.getForumCategoryId() != null && projectData.getForumCategoryId().trim().length() != 0) {
                // not going to happen, but should always check
                throw new IllegalArgumentException("The direct project already has a project forum created.");
            }

			if (forumConfig != null) {
                for (String key : forumConfig.keySet()) {
                    if (key == null || forumConfig.get(key) == null) {
                        throw new IllegalArgumentException("the forum list contains null key/value.");
                    }
                }
            }

            String projectName = projectData.getName();
            Forums forums = getSoftwareForums();
            long forumId = forums.createTopCoderDirectProjectForums(projectName, tcDirectProjectTypeId, forumConfig);
            if (forumId < 0) {
                throw new ProjectServiceFault(
                    "createTopCoderDirectProjectForum returned negative forum ID: " + forumId);
            }

            // Always add create user. Note: it is ok to add the same user multiple times and Forum EJB service
            // will take care of it.
            assignToForumCategoryWithWatch(userId, forumId);

            // All project users must have permission for accessing the project's forum
            List<Permission> projectPermissions = permissionService.getPermissionsByProject(projectId);

            for (Permission permission : projectPermissions) {
                assignToForumCategoryWithWatch(permission.getUserId(), forumId);
            }

            // Update project
            projectData.setForumCategoryId(String.valueOf(forumId));
            updateProject(currentUser, projectData);

            return forumId;
        } catch (Exception e) {
            String msg = "Error when creating a TC Direct project forum for project " + projectId;
            logger.error(msg, e);
            throw (e instanceof ProjectServiceFault) ? (ProjectServiceFault) e : new ProjectServiceFault(msg, e);
        }
    }

    /**
     * <p>
     * Adds a forum to the existing TopCoder Direct project forum category.
     * </p>
     * @param forumCategoryId the TopCoder Direct project forum category id.
     * @param forumName the name of the forum to be added.
     * @param forumDescription the description of the forum to be added.
     * @return the id of the added forum.
     * @throws ProjectServiceFault if an unexpected error occurs.
     * @since 2.4
     */
    public long addTopCoderDirectProjectForum(long forumCategoryId, String forumName, String forumDescription)
            throws ProjectServiceFault {
        try {
            if (createForum) {
                Forums forums = getSoftwareForums();
                return forums.addTopCoderDirectProjectForum(forumCategoryId, forumName, forumDescription);
            } else {
                return System.currentTimeMillis();
            }
        } catch (Exception e) {
            String msg = "Error when adding TC Direct project forum into category " + forumCategoryId;
            logger.error(msg, e);
            throw (e instanceof ProjectServiceFault) ? (ProjectServiceFault) e : new ProjectServiceFault(msg, e);
        }
    }

    /**
     * <p>
     * Deletes an existing TopCoder Direct project forum.
     * </p>
     * @param forumCategoryId the id of the forum category.
     * @param forumId the id of the forum to be deleted.
     * @throws ProjectServiceFault if an unexpected error occurs.
     * @since 2.4
     */
    public void deleteTopCoderDirectProjectForum(long forumCategoryId, long forumId) throws ProjectServiceFault {
        if (createForum) {
            try {
                Forums forums = getSoftwareForums();
                forums.deleteTopCoderDirectProjectForum(forumCategoryId, forumId);
            } catch (Exception e) {
                String msg = "Error when deleting TC Direct project forum " + forumId;
                logger.error(msg, e);
                throw (e instanceof ProjectServiceFault) ? (ProjectServiceFault) e : new ProjectServiceFault(msg, e);
            }
        }
    }

    /**
     * Gets all the project types.
     *
     * @return all the project types.
     * @throws PersistenceFault if a generic persistence error occurs.
     * @since 2.0
     */
    public List<ProjectType> getAllProjectTypes() throws PersistenceFault {
        return this.projectService.getAllProjectTypes();
    }

    /**
     * Gets the project categories by the given project type id.
     *
     * @param projectTypeId the id of the project type.
     * @return a list pf project categories
     * @throws PersistenceFault if a generic persistence error occurs.
     * @since 2.0
     */
    public List<ProjectCategory> getProjectCategoriesByProjectType(long projectTypeId) throws PersistenceFault {
        return this.projectService.getProjectCategoriesByProjectType(projectTypeId);
    }

    /**
     * Gets the billing accounts of the given direct project id
     *
     * @param projectId the id of the direct project
     * @return a list of billing accounts of the direct project.
     * @throws PersistenceFault if a generic persistence error occurs.
     * @since 2.0
     */
    public List<com.topcoder.clients.model.Project> getBillingAccountsByProject(long projectId) throws PersistenceFault {
        return this.projectService.getBillingAccountsByProject(projectId);
    }

    /**
     * Gets the projects by the billing account id.
     *
     * @param billingAccountId the id of the billing account.
     * @return a list of <code>ProjectData</code>
     * @throws PersistenceFault if a generic persistence error occurs.
     * @since 2.0
     */
    public List<ProjectData> getProjectsByBillingAccount(long billingAccountId) throws PersistenceFault {
        return this.projectService.getProjectsByBillingAccount(billingAccountId);
    }

    /**
     * Adds the billing account to the project.
     *
     * @param projectId        the id of the direct project
     * @param billingAccountId the id of the billing account
     * @throws InvalidBillingAccountForProjectFault
     *                          if the billing account does not have same client as project's existing billing accounts
     * @throws PersistenceFault if a generic persistence error occurs.
     * @since 2.0
     */
    public void addBillingAccountToProject(long projectId, long billingAccountId) throws InvalidBillingAccountForProjectFault, PersistenceFault {
        this.projectService.addBillingAccountToProject(projectId, billingAccountId);
    }

    /**
     * Removes the billing account from the project.
     *
     * @param projectId        the id of the direct project
     * @param billingAccountId the id of the billing account
     * @throws PersistenceFault if a generic persistence error occurs.
     * @since 2.0
     */
    public void removeBillingAccountFromProject(long projectId, long billingAccountId) throws PersistenceFault {
        this.projectService.removeBillingAccountFromProject(projectId, billingAccountId);
    }

    /**
     * Gets all the billing accounts by the id of the client
     *
     * @param clientId the id of the client
     * @return a list of billing accounts
     * @throws PersistenceFault if a generic persistence error occurs.
     * @since 2.0
     */
    public List<com.topcoder.clients.model.Project> getBillingAccountsByClient(long clientId) throws PersistenceFault {
        return this.projectService.getBillingAccountsByClient(clientId);
    }

    /**
     * Gets the projects of the given client.
     *
     * @param clientId the id of the client.
     * @return a list of projects belong to the client.
     * @throws PersistenceFault if a generic persistence error occurs.
     * @since 2.0
     */
    public List<ProjectData> getProjectsByClient(long clientId) throws PersistenceFault {
        return this.projectService.getProjectsByClient(clientId);
    }

    /**
     * Checks to see if forum watch is preferred.
     *
     * @param userId the user id which the preference is queried
     * @return true if it is preferred otherwise false
     * @throws Exception if any error occurs
     */
    private boolean isForumWatchPreferred(long userId) throws Exception {
        // Get preference Map
        List<Integer> preferenceIds = new ArrayList<Integer>();
        // notification preference
        preferenceIds.add(GLOBAL_PROJECT_FORUM_WATCH);
        Map<Integer, String> preferences = getUserPreferenceMaps(userId, preferenceIds);
        return Boolean.parseBoolean(preferences.get(GLOBAL_PROJECT_FORUM_WATCH));
    }

    /**
     * Gets the user preference data as a map.
     * This is copied from PermissionServiceFacadeBean#getUserPreferenceMaps
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
     * <p>Creates the specified <code>TC Direct</code> project with optional permissions granted to users.</p>
     *
     * @param currentUser   a <code>TCSubject</code> representing the current user.
     * @param projectData a <code>ProjectData</code> providing the details for project to be created.
     * @param permissions a <code>List</code> providing the permissions for users to be set. May be <code>null</code>.
     * @return a <code>ProjectData</code> providing details for created project.
     * @throws ProjectServiceFault if an unexpected error occurs.
     * @throws IllegalArgumentFault if specified <code>currentUser</code> or <code>projectData</code> is
     *         <code>null</code> or specified <code>permissions</code> list contains <code>null</code> element.
     * @since 1.0.4
     */
    public ProjectData createTCDirectProject(TCSubject currentUser, ProjectData projectData,
                                             List<ProjectPermission> permissions) throws ProjectServiceFault {
        return createTCDirectProject(currentUser, projectData, permissions, null);
    }

    /**
     * <p>Creates the specified <code>TC Direct</code> project with optional permissions granted to users and forum
     * templates.</p>
     * @param currentUser a <code>TCSubject</code> representing the current user.
     * @param projectData a <code>ProjectData</code> providing the details for project to be created.
     * @param permissions a <code>List</code> providing the permissions for users to be set. May be <code>null</code>.
     * @param forums a list of project forum templates configuration.
     * @return a <code>ProjectData</code> providing details for created project.
     * @throws ProjectServiceFault if an unexpected error occurs.
     * @throws IllegalArgumentFault if specified <code>currentUs 0er</code> or <code>projectData</code> is
     *         <code>null</code> or specified <code>permissions</code> list contains <code>null</code> element or
     *         specified <code>forums</code> map contains <code>null</code> key/value.
     * @since 2.4
     */
    public ProjectData createTCDirectProject(TCSubject currentUser, ProjectData projectData,
            List<ProjectPermission> permissions, Map<String, String> forums) throws ProjectServiceFault {
        // Validate arguments
        if (currentUser == null) {
            throw new IllegalArgumentFault("The parameter [currentUser] is NULL");
        }
        if (projectData == null) {
            throw new IllegalArgumentFault("The parameter [projectData] is NULL");
        }
        if (permissions != null) {
            for (ProjectPermission permission : permissions) {
                if (permission == null) {
                    throw new IllegalArgumentFault("Permissions list contains NULL element");
                }
            }
        }

		if (forums != null) {
            for (String key : forums.keySet()) {
                if (key == null || forums.get(key) == null) {
                    throw new IllegalArgumentFault("Forums list contains NULL key/value");
                }
            }
        }

        try {
            // Create project
            ProjectData result = createProject(currentUser, projectData, forums);

            // associated project billing account
            if (projectData.getProjectBillingAccountId() > 0) {
                addBillingAccountToProject(result.getProjectId(), projectData.getProjectBillingAccountId());
            }

			long forumid = Long.valueOf(result.getForumCategoryId());
            if ((permissions != null) && !permissions.isEmpty()) {
                List<ProjectPermission> permissionsToAdd = new ArrayList<ProjectPermission>();
                for (ProjectPermission p : permissions) {
                    p.setProjectId(result.getProjectId());
                    p.setProjectName(projectData.getName());
                    p.setStudio(false);
                    p.setUserPermissionId(-1); // always new permission

                    // do not add permission for current user who creates the new project
                    if (p.getUserId() != currentUser.getUserId()) {
                        permissionsToAdd.add(p);

						if (createForum)
						{
							assignToForumCategoryWithWatch(p.getUserId(), forumid, false);
						}
                    }
                }

                if (permissionsToAdd.size() > 0) {
                    permissionService.updateProjectPermissions(currentUser, permissionsToAdd, 
                        RESOURCE_ROLE_OBSERVER_ID);
                }
            }

            return result;
        } catch (Exception e) {
            sessionContext.setRollbackOnly();
            throw (e instanceof ProjectServiceFault)
                  ? (ProjectServiceFault) e : new ProjectServiceFault("Failed to create TC Direct project", e);
        }
    }

    /**
     * <p>Updates the project forum watch.</p>
     *
     * @param currentUser a <code>TCSubject</code> representing the current user.
     * @param tcDirectProjectId the project id.
     * @param watching to indicate if you want to set watch or unwatch.
     * @throws ProjectServiceFault if an unexpected error occurs.
     */
    public void updateProjectForumWatch(TCSubject currentUser, long tcDirectProjectId, boolean watching) throws ProjectServiceFault {
        long userId = currentUser.getUserId();
        if (logger.isDebugEnabled()) {
            logger.debug("updateProjectForumWatch (tcDirectProjectId = " + tcDirectProjectId + ", userId = " + userId
                + ", watching = " + watching + ")");
        }

        if (!createForum) {
            logger.info("Mock operation for updateProjectForumWatch since createForum flag is set to false now :");
            logger.info("updateProjectForumWatch (tcDirectProjectId = " + tcDirectProjectId + ", userId = " + userId
                + ", watching = " + watching+")");
            return;
        }

        try {
            // Get project data
            ProjectData projectData = getProject(currentUser, tcDirectProjectId);

            if (projectData == null) {
                throw new IllegalArgumentException("The direct project id to update forum watch does not exist.");
            }

            if (projectData.getForumCategoryId() == null && projectData.getForumCategoryId().trim().length() == 0) {
                // not going to happen, but should always check
                throw new IllegalArgumentException("The direct project doesn't have forum created yet.");
            }

            long forumCategoryId = Long.parseLong(projectData.getForumCategoryId());

            if (watching) {
                assignToForumCategoryWithWatch(userId, forumCategoryId, watching);
            } else {
                unwatchForumCategory(userId, forumCategoryId);
            }
        } catch (Exception e) {
            String msg = "Error when creating a TC Direct project forum for project " + tcDirectProjectId;
            logger.error(msg, e);
            throw (e instanceof ProjectServiceFault) ? (ProjectServiceFault) e : new ProjectServiceFault(msg, e);
        }
    }

    /**
     * Gets the ids of the users who watch the project forum of the specified project id.
     *
     * @param tcSubject the current user performs the operation.
     * @param userIds the list of user ids to check.
     * @param projectId the id of the project.
     * @return the list of user ids who watch the project forum
     * @throws ProjectServiceFault if there is any error.
     * @since 2.2
     */
    public List<Long> getWatchedUserIdsForProjectForum(TCSubject tcSubject,
                                                       List<Long> userIds, long projectId) throws ProjectServiceFault {
        // validate arguments
        if (tcSubject == null) {
            throw new IllegalArgumentFault("The parameter [tcSubject] is NULL");
        }

        if (logger.isDebugEnabled()) {
            logger.debug("getWatchedUserIdsForProjectForum (tcSubject = " + tcSubject.getUserId()
                    + ", userIds = " + userIds.toString() + ", projectId = " + projectId + ")");
        }

        if(!createForum) {
            // if createForum is set to false, log the operation and return an empty list
            logger.info("Mock operation for getWatchedUserIdsForProjectForum since createForum flag is set to false now :");
            logger.info("getWatchedUserIdsForProjectForum (tcSubject = " + tcSubject.getUserId()
                    + ", userIds = " + userIds.toString() + ", projectId = " + projectId + ")");
            return new ArrayList<Long>();
        }


        try {
            final ProjectData project = getProject(tcSubject, projectId);

            List<Long> watchedUsers = new ArrayList<Long>();

            if(project.getForumCategoryId() == null || project.getForumCategoryId().trim().length() == 0) {
                // no forum, return empty
                return watchedUsers;
            }

            long[] forumId = new long[] {Long.parseLong(project.getForumCategoryId())};

            for(Long userId : userIds) {
                // check if each user watches the project forum
                final long[] watchedForumIds = getSoftwareForums().areCategoriesWatched(userId, forumId);

                if(watchedForumIds != null && watchedForumIds.length > 0) {
                    watchedUsers.add(userId);
                }

            }

            return watchedUsers;

        } catch (Exception e) {
            String msg = "Error when getting projects forum notification settings for project ID:" + projectId;
            logger.error(msg, e);
            throw (e instanceof ProjectServiceFault) ? (ProjectServiceFault) e : new ProjectServiceFault(msg, e);
        }
    }

    /**
     * Gets the projects notification settings
     *
     * @param tcSubject the TCSubject instance.
     * @param userId the user id.
     * @return the projects notification settings of the user.
     * @throws ProjectServiceFault if there is any error.
     * @since 2.1
     */
    public List<DirectProjectNotification> getProjectNotifications(TCSubject tcSubject, long userId) throws ProjectServiceFault {

        // validate arguments
        if (tcSubject == null) {
            throw new IllegalArgumentFault("The parameter [tcSubject] is NULL");
        }

        if (logger.isDebugEnabled()) {
            logger.debug("getProjectNotifications (tcSubject = " + tcSubject.getUserId() + ", userId = " + userId + ")");
        }

        if(!createForum) {
            // if createForum is set to false, log the operation and return an empty list
            logger.info("Mock operation for getProjectNotifications since createForum flag is set to false now :");
            logger.info("getProjectNotifications (tcSubject = " + tcSubject.getUserId() + ", userId = " + userId + ")");
            return new ArrayList<DirectProjectNotification>();
        }


        try {
            final List<ProjectData> projectsForUser = getProjectsForUser(tcSubject, userId);
            List<DirectProjectNotification> notifications = new ArrayList<DirectProjectNotification>();

            // filter out active direct projects with forum created and add to notifications list
            for(ProjectData p : projectsForUser) {
                if(p.getProjectStatusId() == DIRECT_PROJECT_ACTIVE_STATUS_ID
                        && p.getForumCategoryId() != null
                        && p.getForumCategoryId().trim().length() != 0) {
                    long forumId = Long.parseLong(p.getForumCategoryId());

                    DirectProjectNotification notification = new DirectProjectNotification();

                    notification.setName(p.getName());
                    notification.setForumId(forumId);
                    notification.setProjectId(p.getProjectId());

                    notifications.add(notification);
                }
            }

            long[] forumIds = new long[notifications.size()];
            int count = 0;
            for(DirectProjectNotification notification : notifications) {
                forumIds[count++] = notification.getForumId();
            }

            final long[] watchedForumIds = getSoftwareForums().areCategoriesWatched(userId, forumIds);
            // create a hash set to store the watched forum ids for fast checking
            Set<Long> watchedForumIdsSet = new HashSet<Long>();
            for(long id : watchedForumIds) {
                watchedForumIdsSet.add(id);
            }

            for(DirectProjectNotification notification : notifications) {
                notification.setForumNotification(watchedForumIdsSet.contains(notification.getForumId()));
            }

            return notifications;

        } catch (Exception e) {
            String msg = "Error when getting projects forum notification settings for user ID:" + userId;
            logger.error(msg, e);
            throw (e instanceof ProjectServiceFault) ? (ProjectServiceFault) e : new ProjectServiceFault(msg, e);
        }
    }

    /**
     * Update the project notification settings of the user.
     *
     * @param tcSubject the TCSubject instance.
     * @param userId the user id.
     * @param notifications a list of <code>DirectProjectNotification</code> instances representing the notifications
     *                      which need to update.
     * @throws ProjectServiceFault if there is any error.
     * @since 2.1
     */
    public void updateProjectNotifications(TCSubject tcSubject, long userId,
                                           List<DirectProjectNotification> notifications) throws ProjectServiceFault {

        // validate arguments
        if (tcSubject == null) {
            throw new IllegalArgumentFault("The parameter [tcSubject] is NULL");
        }

        if (logger.isDebugEnabled()) {
            logger.debug("updateProjectNotifications (tcSubject = " + tcSubject.getUserId() +
                    ", userId = " + userId + ", notifications :" + notifications.size() +" notifications)");
        }

        if(!createForum) {
            // if createForum is set to false, log the operation and return an empty list
            logger.info("Mock operation for updateProjectNotifications since createForum flag is set to false now :");
            logger.debug("updateProjectNotifications (tcSubject = " + tcSubject.getUserId() +
                    ", userId = " + userId + ", notifications :" + notifications.size() +" notifications)");
            return;
        }

        List<Long> watchedForumIdList = new ArrayList<Long>();
        List<Long> unwatchedForumIdList = new ArrayList<Long>();

        // get updated notification setting
        for (DirectProjectNotification notification : notifications) {
            // do not consider invalid forum id
            if (notification.getForumId() > 0) {
                if (notification.isForumNotification()) {
                    watchedForumIdList.add(notification.getForumId());
                } else {
                    unwatchedForumIdList.add(notification.getForumId());
                }
            }
        }

        // update the watch settings
        try {
            final Forums softwareForums = getSoftwareForums();
            softwareForums.deleteCategoryWatches(userId, toPrimitiveLongArray(unwatchedForumIdList));
            softwareForums.createCategoryWatches(userId, toPrimitiveLongArray(watchedForumIdList));
        } catch (Exception e) {
            String msg = "Error when updating project forum notifications settings for user ID:" + userId;
            logger.error(msg, e);
            throw (e instanceof ProjectServiceFault) ? (ProjectServiceFault) e : new ProjectServiceFault(msg, e);
        }
    }

    /**
     * Converts a List of <code>Long</code> to primitive long array.
     *
     * @param list a list of <code>Long</code> objects
     * @return the primitive array long[]
     * @since 2.1
     */
    private static long[] toPrimitiveLongArray(List<Long> list) {
        long[] result = new long[list.size()];
        for(int i = 0, n = list.size(); i < n; ++i) {
            result[i] = list.get(i);
        }
        return result;
    }

    /**
     * <p> Assigns the user to the forum and also adds the user to watch list automatically. </p>
     *
     * @param resourceUserId resource user id or user id.
     * @param forumCategoryId        the forum id.
     * @throws ProjectServiceFault if any error occurs
     * @since 1.0.3
     */
    private void assignToForumCategoryWithWatch(long resourceUserId, long forumCategoryId) throws ProjectServiceFault {
        assignToForumCategoryWithWatch(resourceUserId, forumCategoryId, false);
    }

    /**
     * <p> Assigns the user to the forum and also adds the user to watch list automatically. </p>
     *
     * @param resourceUserId resource user id or user id.
     * @param forumCategoryId        the forum id.
     * @throws ProjectServiceFault if any error occurs
     * @since 1.0.3
     */
    private void assignToForumCategoryWithWatch(long resourceUserId, long forumCategoryId, boolean watching) throws ProjectServiceFault {
        try {
		
			Forums forums = getSoftwareForums();
            forums.assignRole(resourceUserId, "Software_Moderators_" + forumCategoryId);
			
            if(watching || isForumWatchPreferred(resourceUserId)) {
                forums.createCategoryWatch(resourceUserId, forumCategoryId);;
            }
            
            
        } catch (Exception e) {
            throw new ProjectServiceFault("Error when assign " + resourceUserId + " to the forum category of " + forumCategoryId, e);
        }
    }

    /**
     * <p>Unwatch forum. </p>
     *
     * @param resourceUserId resource user id or user id.
     * @param forumCategoryId        the forum id or category id.
     * @throws ProjectServiceFault if any error occurs
     */
    private void unwatchForumCategory(long resourceUserId, long forumCategoryId) throws ProjectServiceFault {
        try {
            Forums forums = getSoftwareForums();
            forums.deleteCategoryWatch(resourceUserId, forumCategoryId);
        } catch (Exception e) {
            throw new ProjectServiceFault("Error when unwatch " + resourceUserId + " to the forum category of " + forumCategoryId, e);
        }
    }

    /**
     * Get the Software Forum EJB service for Software competitions.
     *
     * @return the forums EJB service handler.
     * @throws NamingException if a naming exception is encountered.
     * @throws RemoteException if remote error occurs.
     * @throws CreateException if error occurs when creating EJB handler
     * @since 1.0.3
     */
    private Forums getSoftwareForums() throws RemoteException, NamingException, CreateException {
        return getForumsEJBFromJNDI(forumBeanProviderUrl);
    }

    /**
     * <p>Gets the EJB handler for Forum EJB service.</p>
     *
     * @param url the EJB bean url.
     * @return the forum EJB service handler.
     * @throws NamingException if a naming exception is encountered.
     * @throws RemoteException if remote error occurs.
     * @throws CreateException if error occurs when creating EJB handler.
     * @since 1.0.3
     */
    private Forums getForumsEJBFromJNDI(String url) throws NamingException, CreateException, RemoteException {
        Properties p = new Properties();
        p.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
        p.put(Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces");
        p.put(Context.PROVIDER_URL, url);

        Context c = new InitialContext(p);
        ForumsHome forumsHome = (ForumsHome) c.lookup(ForumsHome.EJB_REF_NAME);

        return forumsHome.create();
    }

    /**
     * This method gets all ProjectQuestion instances available in the persistence.
     * It uses the aggregated ProjectService implementation instance to get the project questions from the persistence.
     * @return A list of all the available ProjectQuestion instances in the persistence.
     * @throws PersistenceFault : When some error occurred when accessing the persistence.
     * @since 2.3(TC Cockpit- Create New Project BackEnd 1.0)
     */
    public List<ProjectQuestion> getProjectQuestions() throws PersistenceFault {
        return projectService.getProjectQuestions();
    }

    /**
     * Gets the document generator instance.
     *
     * @return the document generator instance
     * @throws ConfigManagerException if there is any error.
     * @since 2.7
     */
    private DocumentGenerator getDocumentGenerator()
            throws ConfigManagerException {
        try {
            ConfigurationFileManager cfManager = new ConfigurationFileManager(documentManagerConfigFile);

            String docGenNamespace = DocumentGenerator.class.getPackage()
                    .getName();
            ConfigurationObject confObj = cfManager.getConfiguration(docGenNamespace)
                    .getChild(docGenNamespace);

            return DocumentGeneratorFactory.getDocumentGenerator(confObj);
        } catch (Exception e) {
            throw new ConfigManagerException("Error in creating document generator instance");
        }
    }

    /**
     * Sends the email generated from email template.
     *
     * @param templateSource the source of the email template
     * @param templateName the name of the email template
     * @param subject the subject of the email
     * @param toAddrs an array of email addresses set to email-TO
     * @param ccAddrs an array of email addresses set to email-CC
     * @param bccAddr the email BCC address
     * @param fromAddr the email FROM address
     * @param phase the phase
     * @param operation the operation this email is sent for
     * @throws EmailMessageGenerationException if there is any error with email generation
     * @throws EmailSendingException if there is any error with email sending.
     *
     * @since 2.7
     */
    private void sendEmail(String templateSource, String templateName, String subject,
                           String[] toAddrs, String[] ccAddrs, String bccAddr, String fromAddr,
                           com.topcoder.project.phases.Phase phase, String operation)
            throws EmailMessageGenerationException, EmailSendingException {
        boolean messageGenerated = false;

        try {
            // Generate the message body first
            Template template = (templateSource == null)
                    ? documentGenerator.getTemplate(templateName)
                    : documentGenerator.getTemplate(templateSource, templateName);
            String messageBody = this.emailMessageGenerator.generateMessage(documentGenerator,
                                                                            template, phase);

            logger.debug("Generated following email message of subject [" +
                                 subject + "] to be sent to [" + fromAddr + "] \n" +
                                 messageBody);

            // Create a TCSEmailMessage to be sent
            TCSEmailMessage email = new TCSEmailMessage();

            // Set subject, from address and message body.
            email.setSubject(subject);
            email.setFromAddress(fromAddr);
            email.setBody(messageBody);
            email.setContentType("text/html");

            ExceptionUtils.checkNull(toAddrs, null, null, "To address must be non-null.");
            for (String toAddr : toAddrs) {
                email.addToAddress(toAddr, TCSEmailMessage.TO);
            }

            if (ccAddrs != null && ccAddrs.length > 0) {
                for (String ccAddr : ccAddrs) {
                    email.addToAddress(ccAddr, TCSEmailMessage.CC);
                }
            }

            if (bccAddr != null) {
                email.addToAddress(bccAddr, TCSEmailMessage.BCC);
            }

            // Now the email message is generated successfully
            messageGenerated = true;

            // Send email
            EmailEngine.send(email);
            logger.debug("Sent email message of subject [" + subject +
                                 "] to [" + fromAddr + "]");
        } catch (BaseException e) {
            rethrowEmailError(e, messageGenerated, operation);
        } catch (ConfigManagerException e) {
            rethrowEmailError(e, messageGenerated, operation);
        } catch (IllegalArgumentException e) {
            rethrowEmailError(e, messageGenerated, operation);
        }
    }

    /**
     * Wraps the error content of sending an mail.
     *
     * @param e the exception to wrap.
     * @param messageGenerated the generated message.
     * @param operation the operation the email is sent for
     * @throws EmailMessageGenerationException if there is any error with email generation
     * @throws EmailSendingException if there is any error with email sending.
     *
     * @since 2.7
     */
    private void rethrowEmailError(Throwable e, boolean messageGenerated, String operation)
            throws EmailMessageGenerationException, EmailSendingException {
        try {
            if (messageGenerated) {
                throw (e instanceof EmailSendingException)
                        ? (EmailSendingException) e
                        : new EmailSendingException("Error while sending email.", e);
            } else {
                throw (e instanceof EmailMessageGenerationException)
                        ? (EmailMessageGenerationException) e
                        : new EmailMessageGenerationException("Error while generating email to be sent.",
                                                              e);
            }
        } catch (Exception e1) {
            logger.error("*** Could not generate or send an email for operation:" + operation,
                         e1);
        }
    }
}
