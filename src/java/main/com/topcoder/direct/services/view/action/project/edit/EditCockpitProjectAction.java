/*
 * Copyright (C) 2011 - 2014 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.project.edit;


import com.topcoder.clients.model.Project;
import com.topcoder.direct.services.project.metadata.entities.dao.DirectProjectMetadata;
import com.topcoder.direct.services.project.metadata.entities.dao.DirectProjectMetadataKey;
import com.topcoder.direct.services.view.action.FormAction;
import com.topcoder.direct.services.view.action.ViewAction;
import com.topcoder.direct.services.view.action.contest.launch.BaseDirectStrutsAction;
import com.topcoder.direct.services.view.action.project.WriteProject;
import com.topcoder.direct.services.view.dto.IdNamePair;
import com.topcoder.direct.services.view.dto.project.ProjectBriefDTO;
import com.topcoder.direct.services.view.dto.project.edit.EditCockpitProjectDTO;
import com.topcoder.direct.services.view.form.ProjectIdForm;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.security.TCSubject;
import com.topcoder.security.groups.model.BillingAccount;
import com.topcoder.security.groups.model.DirectProject;
import com.topcoder.security.groups.model.Group;
import com.topcoder.security.groups.model.GroupPermissionType;
import com.topcoder.security.groups.services.GroupService;
import com.topcoder.security.groups.services.dto.GroupSearchCriteria;
import com.topcoder.service.facade.contest.notification.ContestNotification;
import com.topcoder.service.facade.contest.notification.ProjectNotification;
import com.topcoder.service.permission.Permission;
import com.topcoder.service.project.ProjectCategory;
import com.topcoder.service.project.ProjectData;
import com.topcoder.service.project.ProjectType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * Action for viewing edit project settings page.
 * </p>
 *
 * <p>
 *     Version 1.1 (Release Assembly - TC Cockpit Edit Project and Project General Info Update) changes notes:
 *     <li>
 *         Update ${@link #setCommonProjectMetadata(java.util.List)} ()} to set the new added 5 project ratings.
 *     </li>
 * </p>
 *
 * <p>
 *     Version 2.0 (Release Assembly - TopCoder Cockpit Project Dashboard Project Type and Permission Notifications Integration)
 *     <ul>
 *         <li>
 *             Add support for setting the project type & category.
 *         </li>
 *         <li>
 *             Add support for managing project permissions and project / contest notifications.
 *         </li>
 *     </ui>
 * </p>
 *
 * <p>
 *     Version 2.1 (Release Assembly - TopCoder Cockpit Billing Account Project Association)
 *    - Add method {@link #getAvailableBillingAccounts()} to get the unassociated billing accounts for project.
 *    - update method {@link #executeAction()} to set associated project billing accounts.
 * </p>
 *
 * <p>
 * Version 2.2 (Release Assembly - TopCoder Security Groups - Release 2) Change notes:
 *   <ol>
 *     <li>Added {@link #groupService} property.</li>
 *     <li>Updated {@link #executeAction()} method to set view data with lists of assigned and available security 
 *     groups.</li>
 *   </ol>
 * </p>
 *
 * <p>
 * Version 2.3 (Release Assembly - TopCoder Security Groups Release 5 v1.0) Change notes:
 *   <ol>
 *     <li>Added annotation <code>WriteProject</code> to this class so that the interceptor will check
 *     the write permission before execution this action.</li>
 *   </ol>
 * </p>
 *
 * <p>
 * Version 2.4 (Release Assembly - TopCoder Direct Cockpit Release Assembly Ten)
 * <ol>
 *     <li>Add TopCoder Account Managers project metadata handling</li>
 *     <li>Add billings accounts the user has access to through security groups to
 *     available billing accounts. Update method {@link #getAvailableBillingAccounts()}</li>
 * </ol>
 * </p>
 *
 * <p>
 * Version 2.5 (TopCoder Security Groups Release 8 - Automatically Grant Permissions) change notes:
 * <ol>
 *     <li>Updated {@link #executeAction()} method to allow automatically grant permissions.</li>
 * </ol>
 * </p>
 *
 *  <p>
 * Version 2.5.1 (BUGR-9288 TopCoder Security Groups - Edit Project bug) change notes:
 * <ol>
 *     <li>Added {@link #clientGroups}</li> to hold a separate groups without filtering with current user.</li>
 *     <li>Updated {@link #executeAction()} method to retrieve an extra groups list.</li>
 * </ol>
 * </p>
 *
 * <p>
 * Version 2.5.2 (TopCoder Direct - Add Appirio Manager)
 * <ul>
 *     <li>Updated {@link #setCommonProjectMetadata(java.util.List)} to handle metadata for Appirio Manager</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 2.5.3 (TopCoder Direct - Add Group Permission Logic and project full permission checking)
 * <ul>
 *     <li>Add group permission checking when checking whether the current user has full permission</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 2.6 (TopCoder Direct - Change Right Sidebar to pure Ajax)
 * - Removes the statements to populate the right sidebar direct projects and project contests. It's changed to
 * load these data via ajax instead after the page finishes loading.
 * </p>
 *
 * @version 2.6
 * @author GreatKevin, freegod, FireIce, Veve, Veve
 */
@WriteProject
public class EditCockpitProjectAction extends BaseDirectStrutsAction implements FormAction<ProjectIdForm>,
        ViewAction<EditCockpitProjectDTO> {

    /**
     * Represents the "None" option name for the project type and category.
     * @since 2.0
     */
    private static final String NONE_OPTION_NAME = "None";

    /**
     * Represents the "None" option value for the project type and category.
     * @since 2.0
     */
    private static final long NONE_OPTION_ID = -1L;

    /**
     * Represents the "None" project type.
     *
     * @since 2.0
     */
    private static final ProjectType NONE_PROJECT_TYPE;

    /**
     * Represents the "None" project category.
     *
     * @since 2.0
     */
    private static final ProjectCategory NONE_PROJECT_CATEGORY;

    /**
     * Static constructor to initialize <code>NONE_PROJECT_TYPE</code> and <code>NONE_PROJECT_CATEGORY</code>.
     * @since 2.0
     */
    static {
        NONE_PROJECT_TYPE = new ProjectType();
        NONE_PROJECT_TYPE.setProjectTypeId(NONE_OPTION_ID);
        NONE_PROJECT_TYPE.setName(NONE_OPTION_NAME);

        NONE_PROJECT_CATEGORY = new ProjectCategory();
        NONE_PROJECT_CATEGORY.setProjectCategoryId(NONE_OPTION_ID);
        NONE_PROJECT_CATEGORY.setProjectTypeId(NONE_OPTION_ID);
        NONE_PROJECT_CATEGORY.setName(NONE_OPTION_NAME);
    }

    /**
     * <p>A <code>ProjectIdForm</code> providing the ID of a requested project.</p>
     */
    private ProjectIdForm formData = new ProjectIdForm();

    /**
     * View data for edit project settings page.
     */
    private EditCockpitProjectDTO viewData = new EditCockpitProjectDTO();

    /**
     * The id of the project type, used by {@link #getProjectCategories()}.
     *
     * @since 2.0
     */
    private long projectTypeId;

    /**
     * The id of the user, used by {@link #getProjectContestsNotificationsForUser()}
     *
     * @since 2.0
     */
    private long userId;

    /**
     * The security groups for client.
     *
     * @since 2.5.1
     */
    private List<Group> clientGroups;

    /**
     * The security groups the user has access to
     *
     * @since 2.4
     */
    private List<Group> userGroups;

    /**
     * <p>A <code>GroupService</code> providing the interface to security groups service.</p>
     * 
     * @since 2.1
     */
    private GroupService groupService;

    /**
     * Gets the view data.
     *
     * @return the view data.
     */
    public EditCockpitProjectDTO getViewData() {
        return viewData;
    }

    /**
     * Sets the view data.
     *
     * @param viewData the view data to set.
     */
    public void setViewData(EditCockpitProjectDTO viewData) {
        this.viewData = viewData;
    }

    /**
     * Gets the form data.
     *
     * @return the form data.
     */
    public ProjectIdForm getFormData() {
        return formData;
    }

    /**
     * Sets the form data.
     *
     * @param formData the form data to set.
     */
    public void setFormData(ProjectIdForm formData) {
        this.formData = formData;
    }

    /**
     * Gets the project type id.
     *
     * @return the project type id.
     * @since 2.0
     */
    public long getProjectTypeId() {
        return projectTypeId;
    }

    /**
     * Sets the project type id.
     *
     * @param projectTypeId the project type id.
     * @since 2.0
     */
    public void setProjectTypeId(long projectTypeId) {
        this.projectTypeId = projectTypeId;
    }

    /**
     * Gets the id of the user.
     *
     * @return the id of the user.
     * @since 2.0
     */
    public long getUserId() {
        return userId;
    }

    /**
     * Sets the id of the user.
     *
     * @param userId the id of the user.
     * @since 2.0
     */
    public void setUserId(long userId) {
        this.userId = userId;
    }

    /**
     * Main logic of action execution.
     *
     * @throws Exception if any error
     */
    @Override
    protected void executeAction() throws Exception {

        // get current user from session
        TCSubject currentUser = DirectUtils.getTCSubjectFromSession();

        // check if project service facade EJB3 service is initialized and injected
        if(getProjectServiceFacade() == null) {
            throw new IllegalStateException("The project service facade is not initialized.");
        }

        // get the general project information
        ProjectData projectData = getProjectServiceFacade().getProject(currentUser, getFormData().getProjectId());

        viewData.setProject(projectData);

        viewData.setClientId(DirectUtils.getClientIdForProject(currentUser, getFormData().getProjectId()));

        // prepare project type and category view data
        List<ProjectType> types = getProjectServiceFacade().getAllProjectTypes();
        List<ProjectCategory> categories;

        Collections.sort(
                types,
                new Comparator<ProjectType>() {
                    public int compare(ProjectType o1, ProjectType o2) {
                        return o1.getName().compareToIgnoreCase(o2.getName());
                    }
                }
        );

        types.add(NONE_PROJECT_TYPE);

        if (projectData.getProjectType() == null) {
            categories = new ArrayList<ProjectCategory>();
            categories.add(NONE_PROJECT_CATEGORY);
        } else {
            categories = getProjectServiceFacade().getProjectCategoriesByProjectType(projectData.getProjectType().getProjectTypeId());
            if (categories == null) {
                categories = new ArrayList<ProjectCategory>();
            }

            Collections.sort(
                    categories,
                    new Comparator<ProjectCategory>() {
                        public int compare(ProjectCategory o1, ProjectCategory o2) {
                            return o1.getName().compareToIgnoreCase(o2.getName());
                        }
                    }
            );

            categories.add(NONE_PROJECT_CATEGORY);
        }

        viewData.setProjectTypes(types);
        viewData.setProjectCategories(categories);

        // set project type and category
        if (projectData.getProjectType() == null) {
            projectData.setProjectType(NONE_PROJECT_TYPE);
        }

        if (projectData.getProjectCategory() == null) {
            projectData.setProjectCategory(NONE_PROJECT_CATEGORY);
        }

        // set project billing accounts
        viewData.setBillingAccounts(getProjectServiceFacade().getBillingAccountsByProject(getFormData().getProjectId()));

        ProjectBriefDTO currentProject = new ProjectBriefDTO();
        currentProject.setId(projectData.getProjectId());
        currentProject.setName(projectData.getName());
        currentProject.setProjectForumCategoryId(projectData.getForumCategoryId());
        if (viewData.getClientId() != null) {
            currentProject.setCustomerId(viewData.getClientId());
            setCustomProjectMetadata(viewData.getClientId());
        }

        final List<Permission> projectPermissions = getPermissionServiceFacade().getPermissionsByProject(currentUser, getFormData().getProjectId());
        
        this.viewData.setHasFullPermission(false);

        // Map to store the project forum notification setting of users having permission on project
        // the key is user id, the value is the notification flag
        Map<Long, Boolean> projectNotifications = new HashMap<Long, Boolean>();
        List<Long> users = new ArrayList<Long>();

        // check permission
        for(Permission p : projectPermissions) {
            if(p.getUserId() == currentUser.getUserId()) {
                if(p.getPermissionType().getPermissionTypeId() == 3L) {
                    // has full permission
                    this.viewData.setHasFullPermission(true);
                }
            }

            users.add(p.getUserId());
            // initialize the flag to false for each user
            projectNotifications.put(p.getUserId(), false);
        }

        // TC staff has full permission to manage cockpit project resources and notifications
        if(this.viewData.getHasFullPermission() == false) {
            // no full permission yet, check other role and group

            if (DirectUtils.isTcStaff(currentUser)) {
                this.viewData.setHasFullPermission(true);
            } else {
                boolean groupFull = DirectUtils.hasPermissionBySecurityGroups(currentUser, getFormData().getProjectId(),
                        getAuthorizationService(), GroupPermissionType.FULL);
                if (groupFull) {
                    this.viewData.setHasFullPermission(true);
                }
            }
        }


        final List<Long> watchedUserIds = getProjectServiceFacade().getWatchedUserIdsForProjectForum(currentUser, users, getFormData().getProjectId());

        for(Long watchedUserId : watchedUserIds) {
            // in the watched user list, set flag to true
            projectNotifications.put(watchedUserId, true);
        }

        // set project permissions
        this.viewData.setProjectPermissions(projectPermissions);

        // set project notifications
        this.viewData.setProjectForumNotifications(projectNotifications);

        // set current user permission
        this.viewData.setCanAccessPermissionNotification(DirectUtils.isCockpitAdmin(currentUser)
                || DirectUtils.isTcOperations(currentUser)
                || DirectUtils.isTcStaff(currentUser));

        List<DirectProjectMetadata> allProjectMetadata = getMetadataService().getProjectMetadataByProject(formData.getProjectId());

        setCommonProjectMetadata(allProjectMetadata);

        getSessionData().setCurrentProjectContext(currentProject);

        getSessionData().setCurrentSelectDirectProjectID(currentProject.getId());

        // Get the list of security groups of the client and split them into two lists - one with group already assigned to project and the rest.
        // with groups already assigned to project and the rest
        GroupSearchCriteria groupSearchCriteria = new GroupSearchCriteria();

        Long clientIdForProject = viewData.getClientId();
        groupSearchCriteria.setClientId(clientIdForProject == null ? -1 : clientIdForProject);

        clientGroups = getGroupService().search(groupSearchCriteria, 0, 0).getValues();

        // Get the list of security groups accessible to current user for billing accounts permissions
		groupSearchCriteria.setUserId(currentUser.getUserId());
        userGroups = getGroupService().search(groupSearchCriteria, 0, 0).getValues();

        Set<Long> userGroupIds = new HashSet<Long>();
        for(Group userGroup : userGroups) {
            userGroupIds.add(userGroup.getId());
        }
        List<Group> assignedGroups = new ArrayList<Group>();
        List<Group> unassignedGroups = new ArrayList<Group>();

        //billing accounts of this project
        Set<Long> projectBillingAccountIds = new HashSet<Long>();
        List<Project> accounts =  getProjectServiceFacade().getBillingAccountsByProject(currentProject.getId());
        if(null != accounts && accounts.size() > 0) {
            for(Project account : accounts) {
                projectBillingAccountIds.add(account.getId());
            }
        }

        for (Group group : clientGroups) {
            List<DirectProject> directProjects = group.getDirectProjects();
            boolean assigned = false;
            if (directProjects != null) {
                for (DirectProject directProject : directProjects) {
                    if (directProject.getDirectProjectId() == currentProject.getId()) {
                        assigned = true;
                        break;
                    }
                }
            }

            //check automatically granted permissions
            if(group.getAutoGrant()) {
                assigned = true;
            }

            //check groups with this billing account
            if(!assigned) {
                Set<Long> groupBillingAccountIds = new HashSet<Long>();
                List<BillingAccount> groupBillingAccounts = group.getBillingAccounts();
                if(null != groupBillingAccounts && groupBillingAccounts.size() > 0) {
                    for(BillingAccount account : groupBillingAccounts) {
                        groupBillingAccountIds.add(account.getId());
                    }
                }

                for(Long accountId : projectBillingAccountIds) {
                    if(groupBillingAccountIds.contains(accountId)) {
                        assigned = true;
                        break;
                    }
                }
            }

            if (assigned) {
                assignedGroups.add(group);
            } else {
                if(null != clientIdForProject && userGroupIds.contains(group.getId())) {
                    unassignedGroups.add(group);
                }
            }
        }

        viewData.setSecurityGroups(assignedGroups);
        viewData.setAvailableSecurityGroups(unassignedGroups);
    }

    /**
     * Gets data from project's metadata and set into the DTO.
     *
     * @param allMetadata the list of all the metadata of the project.
     */
    private void setCommonProjectMetadata(List<DirectProjectMetadata> allMetadata) {

        for (DirectProjectMetadata data : allMetadata) {
            long keyId = data.getProjectMetadataKey().getId();
            String value = data.getMetadataValue();

            if (value == null || value.trim().length() == 0) {
                // value does not exist, continue
                continue;
            }

            if (keyId == 1L) {
                // client manager user ids
                getViewData().getClientManagerIds().add(data);
            } else if (keyId == 2L) {
                // tc manager user ids
                getViewData().getTcManagerIds().add(data);
            } else if (keyId == 3L) {
                // project budget
                getViewData().setBudget(data);
            } else if (keyId == 4L) {
                // svn address
                getViewData().setSvnURL(data);
            } else if (keyId == 5L) {
                // JIRA address
                getViewData().setJiraURL(data);
            } else if (keyId == 6L) {
                // duration
                getViewData().setDuration(data);
            } else if (keyId == 9L) {
                getViewData().setPrivacy(data);
            } else if (keyId == 10L) {
                getViewData().setBusinessImpactRating(data);
            } else if (keyId == 11L) {
                getViewData().setRiskLevelRating(data);
            } else if (keyId == 12L) {
                getViewData().setCostLevelRating(data);
            } else if (keyId == 13L) {
                getViewData().setDifficultyRating(data);
            } else if (keyId == 14L) {
                // TopCoder account manager ids
                getViewData().getTcAccountManagerIds().add(data);
            } else if (keyId == 15L) {
                // Appirio Manager
                getViewData().getAppirioManagerIds().add(data);
            }
        }
    }

    /**
     * Gets the custom project metadata of project.
     *
     * @param clientId the client id.
     * @throws Exception if any error.
     */
    private void setCustomProjectMetadata(long clientId) throws Exception {

        List<DirectProjectMetadataKey> customKeys = getMetadataKeyService().getClientProjectMetadataKeys(clientId, null);

        for (DirectProjectMetadataKey ck : customKeys) {
            List<DirectProjectMetadata> values = getMetadataService().getProjectMetadataByProjectAndKey(getFormData().getProjectId(), ck.getId());
            getViewData().getCustomMetadata().put(ck, values);
        }
    }

    /**
     * Gets the project categories of the project type via ajax.
     *
     * @return the struts2 result code.
     * @since 2.0
     */
    public String getProjectCategories() {
        try {
            Map<String, String> result = new LinkedHashMap<String, String>();

            // get the project categories of the project type id via project service facade
            final List<ProjectCategory> categories = getProjectServiceFacade().getProjectCategoriesByProjectType(getProjectTypeId());

            if (categories != null) {

                // sort the category by name
                Collections.sort(
                        categories,
                        new Comparator<ProjectCategory>() {
                            public int compare(ProjectCategory o1, ProjectCategory o2) {
                                return o1.getName().compareToIgnoreCase(o2.getName());
                            }
                        }
                );

                // put into the json result
                for(ProjectCategory c : categories) {
                    result.put(String.valueOf(c.getProjectCategoryId()), c.getName());
                }
            }

            // append "None" option to the end
            result.put(String.valueOf(NONE_OPTION_ID), NONE_OPTION_NAME);

            setResult(result);
        } catch (Throwable e) {
            if (getModel() != null) {
                setResult(e);
            }
        }

        return SUCCESS;
    }


    /**
     * Gets the available billing accounts for user to select.
     *
     * @return the available billing accounts for user to select.
     * @throws Exception if there is any error.
     * @since 2.1
     */
    public Collection<IdNamePair> getAvailableBillingAccounts() throws Exception {
        final List<ProjectData> allBillingProjects = getBillingProjects();
        Set<Long> associatedBillings = new HashSet<Long>();
        for(Project bp : viewData.getBillingAccounts()) {
            associatedBillings.add(bp.getId());
        }


        Map<Long, IdNamePair> result = new HashMap<Long, IdNamePair>();

        for(ProjectData bp : allBillingProjects) {
            if(!associatedBillings.contains(bp.getProjectId())) {
                IdNamePair billing = new IdNamePair();
                billing.setId(bp.getProjectId());
                billing.setName(bp.getName());
                result.put(bp.getProjectId(), billing);  
            }
        }

        // add billing accounts in the security groups the user has access to
        for(Group securityGroup : userGroups) {
            final List<BillingAccount> securityGroupBillingAccounts
                    = securityGroup.getBillingAccounts();
            for(BillingAccount ba : securityGroupBillingAccounts) {
                if (ba.isActive() && !result.containsKey(ba.getId())) {
                    IdNamePair billing = new IdNamePair();
                    billing.setId(ba.getId());
                    billing.setName(ba.getName());
                    result.put(ba.getId(), billing);   
                }
            }
        }

        return result.values();
    }

    /**
     * Gets the contests timeline/forum notification for the specified user in the form via ajax.
     *
     * @return struts2 result code
     * @since 2.0
     */
    public String getProjectContestsNotificationsForUser() {
        try {
            List<Map<String, String>> result = new LinkedList<Map<String, String>>();
            TCSubject currentUser = DirectUtils.getTCSubjectFromSession();

            final List<ProjectNotification> notificationsForUser = getContestServiceFacade().getNotificationsForUser(currentUser, getUserId());

            ProjectNotification projectNotification = null;

            for(ProjectNotification pn : notificationsForUser) {
                // get the project notification of the specified project
                if(pn.getProjectId() == getFormData().getProjectId()) {
                    projectNotification = pn;
                    break;
                }
            }

            if (projectNotification != null) {
                for (ContestNotification cn : projectNotification.getContestNotifications()) {
                    Map<String, String> contestItem = new HashMap<String, String>();
                    contestItem.put("name", cn.getName());
                    contestItem.put("id", String.valueOf(cn.getContestId()));
                    contestItem.put("forumId", String.valueOf(cn.getForumId()));
                    contestItem.put("forumNotification", String.valueOf(cn.isForumNotification()));
                    contestItem.put("timelineNotification", String.valueOf(cn.isProjectNotification()));

                    result.add(contestItem);
                }
            }

            setResult(result);

        } catch (Throwable e) {
            if (getModel() != null) {
                setResult(e);
            }
        }

        return SUCCESS;
    }

    /**
     * <p>Gets the interface to security groups service.</p>
     *
     * @return a <code>GroupService</code> providing the interface to security groups service.
     * @since 2.2
     */
    public GroupService getGroupService() {
        return this.groupService;
    }

    /**
     * <p>Sets the interface to security groups service.</p>
     *
     * @param groupService a <code>GroupService</code> providing the interface to security groups service.
     * @since 2.2
     */
    public void setGroupService(GroupService groupService) {
        this.groupService = groupService;
    }
}
