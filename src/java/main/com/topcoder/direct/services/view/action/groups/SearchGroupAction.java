/*
 * Copyright (C) 2011-2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.groups;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.commons.utils.ValidationUtility;
import com.topcoder.security.groups.model.BillingAccount;
import com.topcoder.security.groups.model.DirectProject;
import com.topcoder.security.groups.model.Group;
import com.topcoder.security.groups.model.GroupMember;
import com.topcoder.security.groups.model.ResourceType;
import com.topcoder.security.groups.services.DirectProjectService;
import com.topcoder.security.groups.services.GroupService;
import com.topcoder.security.groups.services.SecurityGroupException;
import com.topcoder.security.groups.services.UserService;
import com.topcoder.security.groups.services.dto.GroupSearchCriteria;
import com.topcoder.security.groups.services.dto.PagedResult;
import com.topcoder.security.groups.services.dto.ProjectDTO;
import com.topcoder.security.groups.services.dto.UserDTO;

/**
 * <p>
 * SearchGroupAction is used to perform the search group function. It will log events and errors. It will audit the
 * methods.
 * </p>
 * <b>Configuration</b>
 * <p>
 *
 * <pre>
 *   &lt;bean id=&quot;searchGroupAction&quot;
 *         class=&quot;com.topcoder.security.groups.actions.SearchGroupAction&quot;&gt;
 *         &lt;property name=&quot;groupService&quot; ref=&quot;groupService&quot;/&gt;
 *         &lt;property name=&quot;logger&quot; ref=&quot;logger&quot;/&gt;
 *         &lt;property name=&quot;auditService&quot; ref=&quot;auditService&quot;/&gt;
 *         &lt;property name=&quot;authorizationService&quot; ref=&quot;authorizationService&quot;/&gt;
 *         &lt;!-- other properties here --&gt;
 *   &lt;/bean&gt;
 * </pre>
 *
 * </p>
 * <p>
 * <strong>Thread Safety: </strong> It's mutable and not thread safe. However the struts framework will guarantee
 * that it will be used in the thread safe model.
 * </p>
 *
 * <p>
 * Version 1.1 (Release Assembly - TopCoder Security Groups Frontend - Search Delete Groups) change notes:
 * <ol>
 *   <li>Updated to use the same package name as other group actions.</li>
 *   <li>Updated to follow other security groups pages to set default pageSize = 10 and default page = 1.</li>
 *   <li>Updated the whole class to support ajax calling.</li>
 *   <li>Added field {@link #groupId} and its setter; added method {@link #checkUpdateRemovePermission()}
 *   to check if the user has permission to update/remove the group.</li>
 *   <li>Added fields {@link #groupUserService}, {@link #directProjectService} and their getters/setters;
 *   updated {@link #checkInit()} to check these new fields.</li>
 *   <li>Added user permission check in {@link #executeAction()}.</li>
 *   <li>Added validation check for pageSize, page; updated validation for criteria.permissions in
 *   {@link #validateData()}.</li>
 *   <li>Updated to return list of error messages when throwing validation exception.</li>
 * </ol>
 * </p>
 *
 * @author woodjhon, hanshuai, TCSASSEMBLER
 * @version 1.1
 */
@SuppressWarnings("serial")
public class SearchGroupAction extends BaseAction {    
    /**
     * <p>
     * Represent the class name.
     * </p>
     */
    private static final String CLASS_NAME = SearchGroupAction.class.getName();

    /**
     * Represent the max length of string.
     */
    private static final int MAXLENGTH = 45;

    /**
     * Purpose: criteria is used to represents the criteria. Usage: It's passed as the http input parameter for
     * this action. Legal Values: Not null after set
     */
    private GroupSearchCriteria criteria;

    /**
     * Purpose: pageSize is used to represents the page size. Usage: It's passed as the http input parameter for
     * this action. Legal Values: Positive after set
     */
    private int pageSize = HelperUtility.DEFAULT_PAGESIZE;

    /**
     * Purpose: page is used to represents the page. Usage: It's passed as the http input parameter for this
     * action. Legal Values: Positive after set
     */
    private int page = HelperUtility.DEFAULT_PAGE;

    /**
     * Purpose: groups is used to represents the groups. Usage: It's set by the action methods, and consumed by the
     * front end page. Legal Values: Not null after set
     */
    private PagedResult<Group> groups;

    /**
     * Purpose: groupService is used to represents the group service. It's required. Usage: It's injected. Legal
     * Values: Not null after set
     */
    private GroupService groupService;

    /**
     * Purpose: userService is used to represents the user service. It's required. Usage: It's injected. Legal
     * Values: Not null after set.
     * @since 1.1
     */
    private UserService groupUserService;

    /**
     * Purpose:directProjectService is used to represents the direct project service. It's required. Usage: It's
     * injected. Legal Values: Not null after set.
     * @since 1.1
     */
    private DirectProjectService directProjectService;
    
    /**
     * The group id to check update/remove permission.
     * 
     * @since 1.1
     */
    private long groupId;

    /**
     * <p>
     * Create the instance.
     * </p>
     */
    public SearchGroupAction() {
        // Empty Constructor.
    }

    /**
     * <p>
     * Check that the required(with config stereotype) fields are injected.
     * </p>
     *
     * @throws SecurityGroupsActionConfigurationException
     *             is thrown if any of these fields is null:<br>
     *             auditService, authorizationService, groupService, groupUserService, directProjectService
     */
    public void checkInit() {
        super.checkInit();
        ValidationUtility.checkNotNull(groupService, "groupService",
            SecurityGroupsActionConfigurationException.class);
        ValidationUtility.checkNotNull(groupUserService, "groupUserService",
            SecurityGroupsActionConfigurationException.class);
        ValidationUtility.checkNotNull(directProjectService, "directProjectService",
            SecurityGroupsActionConfigurationException.class);
    }

    /**
     * <p>
     * Checks if the login user has the permission to update/remove the currently selected group.
     * </p>
     * @return SUCCESS if no occurred, ERROR otherwise
     * @since 1.1
     */
    public String checkUpdateRemovePermission() {
        final String signature = CLASS_NAME + ".checkUpdateRemovePermission()";
        LoggingWrapperUtility.logEntrance(getLogger(), signature, null, null);

        try {
            // check parameter
            if (groupId <= 0) {
                throw new SecurityGroupsActionException("The groupId parameter should be positive.");
            }

            Group group = groupService.get(groupId);
            if (group == null) {
                throw new SecurityGroupException("The group to update/remove does not exist.");
            }
            
            // permission check
            boolean ok = HelperUtility.checkPermission(ServletActionContext.getRequest(), getCurrentUserId(),
                getAuthorizationService(), group, false, false);
            
            // prepare the json result
            Map<String, String> result = new HashMap<String, String>();
            result.put("groupId", groupId + "");
            result.put("access", ok ? "true" : "false");
            setResult(result);

            LoggingWrapperUtility.logExit(getLogger(), signature, new String[] { SUCCESS });
            return SUCCESS;
        } catch (Throwable e) {
            setResult(e);
            LoggingWrapperUtility.logExit(getLogger(), signature, new String[] { ERROR });
            return ERROR;
        }
    }

    /**
     * <p>
     * Searches groups by the input criteria.
     * </p>
     *
     * @throws SecurityGroupsActionException
     *             if any error is caught during the operation.
     * @since 1.1
     */
    public void executeAction() throws SecurityGroupsActionException {
        final String signature = CLASS_NAME + ".executeAction()";
        LoggingWrapperUtility.logEntrance(getLogger(), signature, null, null);
        try {
            long userId = getCurrentUserId();
            // validate the fields
            validateData();
            HelperUtility.checkFieldErrors(this);
            
            criteria.setUserId(userId);
            groups = groupService.search(criteria, pageSize, page);
            
            // prepare the json result
            Map<String, Object> result = new HashMap<String, Object>();
            result.put("page", getPage());
            result.put("pageSize", getPageSize());
            result.put("total", groups.getTotal());
            List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
            result.put("items", items);
            // use map the reduce duplicate searching
            Map<Long, String> projectIdToName = new HashMap<Long, String>();
            Map<Long, String> userIdToHandle = new HashMap<Long, String>();
            for (Group group : groups.getValues()) {
                Map<String, Object> item = new HashMap<String, Object>();
                items.add(item);
                item.put("groupId", group.getId() + "");
                item.put("groupName", group.getName());           
                item.put("customerName", group.getClient().getName());
                // billing accounts
                List<BillingAccount> accounts = group.getBillingAccounts();
                List<String> elements = new ArrayList<String>();
                if (accounts != null) {
                    for (BillingAccount account : accounts) {
                        elements.add(account.getName());
                    }
                }
                item.put("accounts", elements);
                
                // direct projects
                elements = new ArrayList<String>();
                List<DirectProject> projects = group.getDirectProjects();
                if (projects != null) {
                    for (DirectProject project : projects) {
                        String projectName;
                        if (projectIdToName.containsKey(project.getDirectProjectId())) {
                            projectName = projectIdToName.get(project.getDirectProjectId());
                        } else {
                            ProjectDTO dto = getDirectProjectService().get(project.getDirectProjectId());
                            if (dto == null) {
                                projectName = "project with id: " + project.getDirectProjectId();
                            } else {
                                projectName = dto.getName();
                            }
                            projectIdToName.put(project.getDirectProjectId(), projectName);
                        }
                        project.setName(projectName);
                        elements.add(projectName);
                    }
                }
                item.put("projects", elements);
                
                // restrictions
                elements = new ArrayList<String>();
                List<ResourceType> resources = group.getRestrictions();
                if (resources != null) {
                    for (ResourceType resource : resources) {
                        if (resource == ResourceType.BILLING_ACCOUNT) {
                            elements.add("Billing Account");                            
                        } else if (resource == ResourceType.PROJECT) {
                            elements.add("Project");             
                        }
                    }
                }
                item.put("resources", elements);
                
                // members
                elements = new ArrayList<String>();
                List<GroupMember> members = group.getGroupMembers();
                if (members != null) {
                    for (GroupMember member : members) {
                        if (!member.getActive())continue;
                        String handle;
                        if (userIdToHandle.containsKey(member.getUserId())) {
                            handle = userIdToHandle.get(member.getUserId());
                        } else {
                            UserDTO dto = getGroupUserService().get(member.getUserId());
                            if (dto == null) {
                                handle = "user with id: " + member.getUserId();
                            } else {
                                handle = dto.getHandle();
                            }
                            userIdToHandle.put(member.getUserId(), handle);
                        }
                        member.setHandle(handle);
                        elements.add(handle);
                    }
                }
                item.put("members", elements);
                String permission = group.getDefaultPermission() + "";
                item.put("accessRights", permission.substring(0, 1) + permission.substring(1).toLowerCase());     
            }
            setResult(result);
            LoggingWrapperUtility.logExit(getLogger(), signature, null);
        } catch (SecurityGroupException e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature, new SecurityGroupsActionException(
                    "error occurs while searching the group", e));
        } catch (RuntimeException e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature, new SecurityGroupsActionException(
                    "RuntimeException occurs while searching the group", e));
        }
    }

    /**
     * Check the string has max 45 chars. the string can be empty.
     *
     * @param string
     *            the string
     * @param fieldName
     *            the field name
     */
    private void check(String string, String fieldName) {
        if (string == null) {
            return;
        }
        string = string.trim();
        if (string.length() > MAXLENGTH) {
            this.addFieldError(fieldName, fieldName + "'s length is bigger than 45");
        }
    }

    /**
     * <p>
     * Performs complex validation that can't be easily done with declarative XML validation.
     * </p>
     *
     * @throws SecurityGroupsActionValidationException
     *             if validation fails.
     * @since 1.1
     */
    public void validateData() {
        final String signature = CLASS_NAME + ".validateData()";
        LoggingWrapperUtility.logEntrance(getLogger(), signature, null, null);
        try {
            HelperUtility.checkPageAndPageSize(this, page, pageSize);
            
            GroupSearchCriteria criteria2 = getCriteria();
            if (criteria2 != null) {
                check(criteria2.getGroupName(), "criteria.groupName");
                check(criteria2.getClientName(), "criteria.clientName");
                check(criteria2.getProjectName(), "criteria.projectName");
                check(criteria2.getBillingAccountName(), "criteria.billingAccountName");
                check(criteria2.getGroupMemberHandle(), "criteria.groupMemberHandle");
                HelperUtility.checkCriteriaPermissions(this, criteria2.getPermissions());
            }

            LoggingWrapperUtility.logExit(getLogger(), signature, null);
        } catch (SecurityGroupsActionValidationException e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature, e);
        }
    }

    /**
     * <p>
     * Get criteria.
     * </p>
     *
     * @return the criteria
     */
    public GroupSearchCriteria getCriteria() {
        return criteria;
    }

    /**
     * <p>
     * Set criteria.
     * </p>
     *
     * @param criteria
     *            the criteria to set.
     */
    public void setCriteria(GroupSearchCriteria criteria) {
        this.criteria = criteria;
    }

    /**
     * <p>
     * Get page size.
     * </p>
     *
     * @return the page size
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * <p>
     * Set page size.
     * </p>
     *
     * @param pageSize
     *            the page size to set.
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * <p>
     * Get page.
     * </p>
     *
     * @return the page
     */
    public int getPage() {
        return page;
    }

    /**
     * <p>
     * Set page.
     * </p>
     *
     * @param page
     *            the page to set.
     */
    public void setPage(int page) {
        this.page = page;
    }

    /**
     * <p>
     * Get groups.
     * </p>
     *
     * @return the groups
     */
    public PagedResult<Group> getGroups() {
        return groups;
    }

    /**
     * <p>
     * Set groups.
     * </p>
     *
     * @param groups
     *            the groups to set.
     */
    public void setGroups(PagedResult<Group> groups) {
        this.groups = groups;
    }

    /**
     * <p>
     * Get group service.
     * </p>
     *
     * @return the group service
     */
    public GroupService getGroupService() {
        return groupService;
    }

    /**
     * <p>
     * Set group service.
     * </p>
     *
     * @param groupService
     *            the group service to set.
     */
    public void setGroupService(GroupService groupService) {
        this.groupService = groupService;
    }

    /**
     * Sets the group id to check update/remove permission.
     * 
     * @param groupId the group id to check
     * @since 1.1
     */
    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    /**
     * Sets group user service.
     * 
     * @param groupUserService the group user service to set
     * @since 1.1
     */
    public void setGroupUserService(UserService groupUserService) {
        this.groupUserService = groupUserService;
    }

    /**
     * Gets group user service.
     * 
     * @return the group user service
     * @since 1.1
     */
    public UserService getGroupUserService() {
        return groupUserService;
    }

    /**
     * Gets the direct project service.
     * 
     * @return the direct project service
     * @since 1.1
     */
    public DirectProjectService getDirectProjectService() {
        return directProjectService;
    }

    /**
     * Sets the direct project service.
     * 
     * @param directProjectService the direct project service to set
     * @since 1.1
     */
    public void setDirectProjectService(DirectProjectService directProjectService) {
        this.directProjectService = directProjectService;
    }
}
