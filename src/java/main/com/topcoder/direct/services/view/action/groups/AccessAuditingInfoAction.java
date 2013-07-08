/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.groups;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.struts2.ServletActionContext;

import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.commons.utils.ValidationUtility;
import com.topcoder.security.groups.model.BillingAccount;
import com.topcoder.security.groups.model.Group;
import com.topcoder.security.groups.services.DirectProjectService;
import com.topcoder.security.groups.services.GroupMemberService;
import com.topcoder.security.groups.services.GroupService;
import com.topcoder.security.groups.services.SecurityGroupException;
import com.topcoder.security.groups.services.UserService;
import com.topcoder.security.groups.services.dto.GroupMemberAccessHistoricalData;
import com.topcoder.security.groups.services.dto.GroupMemberSearchCriteria;
import com.topcoder.security.groups.services.dto.PagedResult;
import com.topcoder.security.groups.services.dto.ProjectDTO;
import com.topcoder.security.groups.services.dto.UserDTO;

/**
 * <p>
 * This Struts action allows to access auditing information. This action will be
 * used by JSP corresponding to administrator-acc-auditing-information.html from
 * the prototype.
 * </p>
 * <p>
 * <b>Thread Safety:</b> This class is technically mutable and not thread-safe,
 * however it's expected to be used as Spring bean and thus it will be immutable
 * after initialization, so, since it inherits from thread-safe (under same
 * conditions) base class and utilizes only the thread-safe tools, it's
 * thread-safe.
 * </p>
 * <p>
 * <b>Sample Configuration:</b> The spring configuration is below:
 *
 * <pre>
 *  &lt;bean id="AccessAuditingInfoAction"
 *     class="com.topcoder.security.groups.actions.AccessAuditingInfoAction"&gt;
 *     &lt;property name="logger" ref="logger"/&gt;
 *     &lt;property name="clientService" ref="clientService"/&gt;
 *     &lt;property name="groupService" ref="groupService"/&gt;
 *     &lt;property name="userService" ref="userService"/&gt;
 *     &lt;property name="customerAdministratorService" ref="customerAdministratorService"/&gt;
 *     &lt;property name="groupMemberService" ref="groupMemberService"/&gt;
 *     &lt;property name="directProjectService" ref="directProjectService"/&gt;
 *  &lt;/bean&gt;
 * </pre>
 *
 * The struts configuration is below:
 *
 * <pre>
 *   &lt;action name="accessAuditingInfoAction" class="AccessAuditingInfoAction" method="input"&gt;
 *     &lt;result name="INPUT"&gt;accessAuditingInfo.jsp&lt;/result&gt;
 *   &lt;/action&gt;
 *
 * </pre>
 *
 * </p>
 *
 * <p>
 * Version 1.1 (Release Assembly - TopCoder Security Groups Frontend - Miscellaneous) change notes:
 * <ol>
 *   <li>Updated to use the same package name as other group actions.</li>
 *   <li>Updated the whole class to support ajax calling.</li>
 *   <li>Updated to use common {@link HelperUtility#DEFAULT_PAGE} and {@link HelperUtility#DEFAULT_PAGESIZE}.</li>
 *   <li>Renamed field userService to {@link #groupUserService}, renamed its setter to
 *   avoid conflict with base class. Updated all related places of this field.</li>
 *   <li>Added permission check in updated method {@link #executeAction()}, also it calls the updated
 *   {@link #validateData()} method.</li>
 *   <li>Added {@link #getCriteria()}, {@link #getPage()},{@link #getPageSize()}.</li>
 * </ol>
 * </p>
 *
 * @author gevak, TCSDEVELOPER, minhu
 * @version 1.1
 */
@SuppressWarnings("serial")
public class AccessAuditingInfoAction extends ClientsPrepopulatingBaseAction {
    /**
     * Represent the class name.
     */
    private static final String CLASS_NAME = AccessAuditingInfoAction.class
            .getName();
    /**
     * Group member search criteria, used for searching the group member access
     * historical data. It is used in execute() method. It is injected via the
     * setter with no validation, thus can be any value. When execute() is
     * called, XML validations are applied on this variable - see CS 1.3.2. Has
     * default value (in order to allow page to load first time (before user
     * clicks search/filter button)). Mutable via public setter.
     */
    private GroupMemberSearchCriteria criteria = new GroupMemberSearchCriteria();

    /**
     * Amount of found records to show on a single page (used for search result
     * pagination). It is used in execute() method. It is injected via the
     * setter with no validation, thus can be any value. When execute() is
     * called, XML validations are applied on this variable - see CS 1.3.2. Has
     * default value (in order to allow page to load first time (before user
     * clicks search/filter button)). Mutable via public setter.
     */
    private int pageSize = HelperUtility.DEFAULT_PAGESIZE;

    /**
     * 1-based index of page to show (used for search result pagination). 0 for
     * retrieving all records. It is used in execute() method. It is injected
     * via the setter with no validation, thus can be any value. When execute()
     * is called, XML validations are applied on this variable - see CS 1.3.2.
     * Has default value (in order to allow page to load first time (before user
     * clicks search/filter button)). Mutable via public setter.
     */
    private int page = HelperUtility.DEFAULT_PAGE;

    /**
     * Group member access historical data search result. Initially null, but
     * will be populated by execute() method with non-null value. Has public
     * getter.
     */
    private PagedResult<GroupMemberAccessHistoricalData> historicalData;

    /**
     * List, containing Group entities for each item in the search result (in
     * same order as there). This will be used by JSP to render necessary group
     * related info. Initially null, but will be populated by execute() method
     * with non-null list of non-null values. Has public getter.
     */
    private List<Group> groups;

    /**
     * Contains projects per ID (project ID is key and project DTO is value).
     * This will be used by JSP to render content of the "Projects" column of
     * the table. Initially null, but will be populated by execute() method with
     * non-null map composed of non-null keys and non-null values. Has public
     * getter.
     */
    private Map<Long, ProjectDTO> projectById;

    /**
     * Contains users per ID (user ID is key and user DTO is value). This will
     * be used by JSP to render content of the "Members" column of the table.
     * Initially null, but will be populated by execute() method with non-null
     * map composed of non-null keys and non-null values. Has public getter.
     */
    private Map<Long, UserDTO> userById;

    /**
     * GroupMemberService used to perform search. It is used in execute()
     * method. It is injected via the setter with no validation, thus can be any
     * value. However, @PostConstruct method will ensure that it's not null.
     * Mutable via setter.
     */
    private GroupMemberService groupMemberService;

    /**
     * GroupService used to retrieve group info. It is used in execute() method.
     * It is injected via the setter with no validation, thus can be any value.
     * However, @PostConstruct method will ensure that it's not null. Mutable
     * via setter.
     */
    private GroupService groupService;

    /**
     * DirectProjectService used to retrieve project info. It is used in
     * execute() method. It is injected via the setter with no validation, thus
     * can be any value. However, the post construct method will ensure that
     * it's not null. Mutable via setter.
     */
    private DirectProjectService directProjectService;

    /**
     * UserService used to manage user information. It is used in execute()
     * method. It is injected via the setter with no validation, thus can be any
     * value. However, the post construct method will ensure that it's not null.
     * Mutable via setter.
     * 
     * @since 1.1
     */
    private UserService groupUserService;

    /**
     * Empty default constructor.
     */
    public AccessAuditingInfoAction() {
    }

    /**
     * Searches auditing information.
     *
     * @throws SecurityGroupsActionException if any error occurs when performing
     *             the operation.
     * @since 1.1
     */
    public void executeAction() throws SecurityGroupsActionException {
        final String signature = CLASS_NAME + ".executeAction()";
        LoggingWrapperUtility.logEntrance(getLogger(), signature, null, null);
        try {
            // check permission
            long userId = getCurrentUserId();
            if (!getAuthorizationService().isAdministrator(userId) &&
                    !getAuthorizationService().isCustomerAdministrator(userId, 0)) {
                ServletActionContext.getRequest().setAttribute("errorPageMessage",
                    "Sorry, you don't have permission to the access auditing information page.");
                throw new SecurityGroupsActionException(
                    "Has no permission to the access auditing information page.");
            }
            // validate the fields
            validateData();
            HelperUtility.checkFieldErrors(this);
            if (getClients() == null) {
                input();
            }
            
            // Populate user id into search criteria
            criteria.setUserId(userId);
            // Search historical data
            historicalData = groupMemberService.searchHistoricalData(criteria, pageSize, page);
            List<GroupMemberAccessHistoricalData> historicalDataValues = historicalData.getValues();
            groups = new ArrayList<Group>();
            projectById = new HashMap<Long, ProjectDTO>();
            userById = new HashMap<Long, UserDTO>();
            for (GroupMemberAccessHistoricalData data : historicalDataValues) {
                // Get groups of the historical data
                Group group = groupService.get(data.getGroupId());
                ValidationUtility.checkNotNull(group, "group", SecurityGroupsActionException.class);
                groups.add(group);

                // Populate project by ID map
                List<Long> directProjectIds = data.getDirectProjectIds();
                for (Long projectId : directProjectIds) {
                    if (!projectById.containsKey(projectId)) {
                        ProjectDTO project = directProjectService.get(projectId);
                        if (project == null) {
                            project = new ProjectDTO();
                            project.setName("project with id: " + projectId);
                            project.setProjectId(projectId);
                        }
                        projectById.put(projectId, project);
                    }
                }
                
                // Populate user by ID map
                long memberUserId = data.getMemberUserId();
                if (!userById.containsKey(memberUserId)) {
                    UserDTO user = groupUserService.get(memberUserId);
                    if (user == null) {
                        user = new UserDTO();
                        user.setHandle("user with id: " + memberUserId);
                        user.setUserId(memberUserId);
                    }
                    userById.put(memberUserId, user);
                }
            }
            
            // prepare the json result
            Map<String, Object> result = new HashMap<String, Object>();
            result.put("page", this.page);
            result.put("pageSize", this.pageSize);
            result.put("total", historicalData.getTotal());
            List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
            result.put("items", items);
            int idx = 0;
            for (GroupMemberAccessHistoricalData data : historicalDataValues) {
                Map<String, Object> item = new HashMap<String, Object>();
                items.add(item);
                Group group = groups.get(idx);
                item.put("groupName", group.getName());           
                item.put("customerName", group.getClient().getName());
                String permission = data.getPermission() + "";
                item.put("accessRights", permission.substring(0, 1) + permission.substring(1).toLowerCase());
                // billing accounts
                List<BillingAccount> accounts = data.getBillingAccounts();
                List<String> elements = new ArrayList<String>();
                if (accounts != null) {
                    for (BillingAccount account : accounts) {
                        elements.add(account.getName());
                    }
                }
                item.put("accounts", elements);
                
                // direct projects
                elements = new ArrayList<String>();
                for (long projectId : data.getDirectProjectIds()) {
                    String projectName = projectById.get(projectId).getName();
                    elements.add(projectName);
                }
                item.put("projects", elements);
                
                // member
                item.put("member", userById.get(data.getMemberUserId()).getHandle());
                
                // Access From / To
                elements = new ArrayList<String>();
                elements.add(formatDate(data.getFrom()));
                elements.add("to " + formatDate(data.getTo())); 
                item.put("fromTo", elements);
                
                ++idx;
            }
            setResult(result);
            LoggingWrapperUtility.logExit(getLogger(), signature, null);
        } catch (SecurityGroupException e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature,
                new SecurityGroupsActionException("error occurs while executing the action", e));
        } catch (SecurityGroupsActionException e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature, e);
        }
        LoggingWrapperUtility.logExit(getLogger(), signature, null);
    }

    /**
     * Performs complex validation that can't be easily done with declarative
     * XML validation.
     * 
     * @since 1.1
     */
    public void validateData() {
        final String signature = CLASS_NAME + ".validateData()";
        super.validate();
        LoggingWrapperUtility.logEntrance(getLogger(), signature, null, null);
        addErrorMessage(criteria.getGroupName(), "criteria.groupName");
        addErrorMessage(criteria.getClientName(), "criteria.clientName");
        addErrorMessage(criteria.getProjectName(), "criteria.projectName");
        addErrorMessage(criteria.getBillingAccountName(),
                "criteria.billingAccountName");
        addErrorMessage(criteria.getMemberHandle(), "criteria.memberHandle");
        HelperUtility.checkDateRange(this, criteria.getHadAccessFrom(),
            criteria.getHadAccessTill(), "criteria.hadAccessFrom", "criteria.hadAccessTill");

        HelperUtility.checkPageAndPageSize(this, page, pageSize);
        HelperUtility.checkCriteriaPermissions(this, criteria.getPermissions());

        LoggingWrapperUtility.logExit(getLogger(), signature, null);
    }

    /**
     * Checks the field parameter and adds error message if it's not valid.
     *
     * @param value the field value.
     * @param name the name of the field.
     */
    private void addErrorMessage(String value, String name) {
        if (value != null) {
            HelperUtility.checkStringLength(this, value, name);
        }
    }

    /**
     * Checks whether this class was configured by Spring properly.
     *
     * @throws SecurityGroupsActionConfigurationException if the class was not
     *             configured properly (e.g. when required property was not
     *             injected or property value is invalid).
     *
     */
    @PostConstruct
    public void checkInit() {
        super.checkInit();
        ValidationUtility.checkNotNull(groupMemberService,
                "groupMemberService",
                SecurityGroupsActionConfigurationException.class);
        ValidationUtility.checkNotNull(groupService, "groupService",
                SecurityGroupsActionConfigurationException.class);
        ValidationUtility.checkNotNull(directProjectService,
                "directProjectService",
                SecurityGroupsActionConfigurationException.class);
        ValidationUtility.checkNotNull(groupUserService, "groupUserService",
                SecurityGroupsActionConfigurationException.class);
    }

    /**
     * Gets group member search criteria, used for searching the group member
     * access historical data.
     *
     * @return the Group member search criteria, used for searching the
     *            group member access historical data.
     * @since 1.1
     */
    public GroupMemberSearchCriteria getCriteria() {
        return criteria;
    }
    
    /**
     * Gets the page.
     * 
     * @return the page
     * @since 1.1
     */
    public int getPage() {
        return page;
    }
    
    /**
     * Gets the page size.
     * 
     * @return the page size
     * @since 1.1
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * Sets group member search criteria, used for searching the group member
     * access historical data.
     *
     * @param criteria Group member search criteria, used for searching the
     *            group member access historical data.
     *
     */
    public void setCriteria(GroupMemberSearchCriteria criteria) {
        this.criteria = criteria;
    }

    /**
     * Sets amount of found records to show on a single page (used for search
     * result pagination).
     *
     * @param pageSize Amount of found records to show on a single page (used
     *            for search result pagination).
     *
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * Sets 1-based index of page to show (used for search result pagination). 0
     * for retrieving all records.
     *
     * @param page 1-based index of page to show (used for search result
     *            pagination). 0 for retrieving all records.
     *
     */
    public void setPage(int page) {
        this.page = page;
    }

    /**
     * Gets group member access historical data search result.
     *
     * @return Group member access historical data search result.
     *
     */
    public PagedResult<GroupMemberAccessHistoricalData> getHistoricalData() {
        return historicalData;
    }

    /**
     * Gets list, containing Group entities for each item in the search result
     * (in same order as there).
     *
     * @return List, containing Group entities for each item in the search
     *         result (in same order as there).
     *
     */
    public List<Group> getGroups() {
        return groups;
    }

    /**
     * Gets map containing projects per ID (project ID is key and project DTO is
     * value). This will be used by JSP to render content of the "Projects"
     * column of the table.
     *
     * @return Contains projects per ID (project ID is key and project DTO is
     *         value). This will be used by JSP to render content of the
     *         "Projects" column of the table.
     *
     */
    public Map<Long, ProjectDTO> getProjectById() {
        return projectById;
    }

    /**
     * Gets map containing users per ID (user ID is key and user DTO is value).
     * This will be used by JSP to render content of the "Members" column of the
     * table.
     *
     * @return Contains users per ID (user ID is key and user DTO is value).
     *         This will be used by JSP to render content of the "Members"
     *         column of the table.
     *
     */
    public Map<Long, UserDTO> getUserById() {
        return userById;
    }

    /**
     * Sets GroupMemberService used to perform search.
     *
     * @param groupMemberService GroupMemberService used to perform search.
     *
     */
    public void setGroupMemberService(GroupMemberService groupMemberService) {
        this.groupMemberService = groupMemberService;
    }

    /**
     * Sets GroupService used to retrieve group info.
     *
     * @param groupService GroupService used to retrieve group info.
     *
     */
    public void setGroupService(GroupService groupService) {
        this.groupService = groupService;
    }

    /**
     * Sets DirectProjectService used to retrieve project info.
     *
     * @param directProjectService DirectProjectService used to retrieve project
     *            info.
     *
     */
    public void setDirectProjectService(
            DirectProjectService directProjectService) {
        this.directProjectService = directProjectService;
    }

    /**
     * Sets group user service used to manage user information.
     *
     * @param groupUserService group user service used to manage user information.
     * @since 1.1
     */
    public void setGroupUserService(UserService groupUserService) {
        this.groupUserService = groupUserService;
    }
}
