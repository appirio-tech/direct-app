/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.groups;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.struts2.ServletActionContext;

import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.commons.utils.ValidationUtility;
import com.topcoder.security.groups.model.Group;
import com.topcoder.security.groups.model.GroupMember;
import com.topcoder.security.groups.services.EntityNotFoundException;
import com.topcoder.security.groups.services.GroupInvitationService;
import com.topcoder.security.groups.services.GroupService;
import com.topcoder.security.groups.services.SecurityGroupException;
import com.topcoder.security.groups.services.UserService;
import com.topcoder.security.groups.services.dto.GroupSearchCriteria;
import com.topcoder.security.groups.services.dto.UserDTO;

/**
 * <p>
 * This Struts action allows to send invitations. This action will be used by
 * JSP corresponding to administrator-send-group-invitation.html page from the
 * prototype.
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
 *  &lt;bean id="SendInvitationAction"
 *     class="com.topcoder.security.groups.actions.SendInvitationAction"&gt;
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
 *   &lt;action name="sendInvitationAction" class="SendInvitationAction" method="input"&gt;
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
 *   <li>Updated method {@link #input()} to correctly populate the groups.</li>
 *   <li>Changed field groupNames to {@link #groupIds}, updated getter/setter and other related places.</li>
 *   <li>Remove field customerAdministratorService and its setter.</li>
 *   <li>Added permission check in updated method {@link #executeAction()}, also it calls the updated
 *   {@link #validateData()} method and added method {@link #checkPermission()}.</li>
 *   <li>Renamed field userService to {@link #groupUserService}, renamed its setter to
 *   avoid conflict with base class. Updated all related places of this field.</li>
 *   <li>Added method {@link #enterSendInvitation()} to prepare the data used by the send group invitation page.</li>
 * </ol>
 * </p>
 *
 * @author gevak, TCSDEVELOPER, minhu
 * @version 1.1
 */
@SuppressWarnings("serial")
public class SendInvitationAction extends BaseAction {
    /**
     * Represent the class name.
     *
     */
    private static final String CLASS_NAME = SendInvitationAction.class
            .getName();
    
    /**
     * Ids of groups to send invitations for. It is used in execute() method.
     * It is injected via the setter with no validation, thus can be any value.
     * Mutable via setter.
     * 
     * @since 1.1
     */
    private List<Long> groupIds;

    /**
     * Handles of users to send invitations to. It is used in execute() method.
     * It is injected via the setter with no validation, thus can be any value.
     * Mutable via setter.
     */
    private List<String> handles;

    /**
     * Ids of users to send invitations to. It is used in execute() method.
     * It is injected via the setter with no validation, thus can be any value.
     * Mutable via setter.
     */
    private List<Long> userIds;

    /**
     * Groups which can be administrated by current user. It will be used by JSP
     * to render groups dropdown list. Initially null, but will be populated by
     * input() method with non-null list of non-null elements. Has public
     * getter.
     */
    private List<Group> groups;

    /**
     * GroupService used to manage group information. It is used in input() and
     * execute() methods. It is injected via the setter with no validation, thus
     * can be any value. However, @PostConstruct method will ensure that it's
     * not null. Mutable via setter.
     */
    private GroupService groupService;

    /**
     * GroupInvitationService used to create group invitations. It is used in
     * execute() method. It is injected via the setter with no validation, thus
     * can be any value. However, @PostConstruct method will ensure that it's
     * not null. Mutable via setter.
     */
    private GroupInvitationService groupInvitationService;

    /**
     * UserService used to manage user information. It is used in execute()
     * method. It is injected via the setter with no validation, thus can be any
     * value. However, @PostConstruct method will ensure that it's not null.
     * Mutable via setter.
     * 
     * @since 1.1
     */
    private UserService groupUserService;

    /**
     * Registration URL which will be added to invitations. It is used in
     * execute() method. It is injected via the setter with no validation, thus
     * can be any value. However, @PostConstruct method will ensure that it's
     * not null and not empty. Mutable via setter.
     */
    private String registrationUrl;

    /**
     * Base part of URL for accepting/rejecting invitations. Hyperlinks to such
     * URL's will be added to invitations. It is used in execute() method. It is
     * injected via the setter with no validation, thus can be any value.
     * However, @PostConstruct method will ensure that it's not null and not
     * empty. Mutable via setter.
     */
    private String acceptRejectUrlBase;

    /**
     * Empty default constructor.
     */
    public SendInvitationAction() {
    }

    /**
     * Populates "groups" parameter, which will be used by JSP to render groups
     * dropdown list.
     *
     * @return INPUT to indicate that the operation was successful.
     * @throws SecurityGroupsActionException if any error occurs when performing
     *             the operation.
     *
     */
    public String input() throws SecurityGroupsActionException {
        final String signature = CLASS_NAME + ".input()";
        LoggingWrapperUtility.logEntrance(getLogger(), signature, null, null);
        try {
            // Get all related groups
            GroupSearchCriteria c = new GroupSearchCriteria();
            // Get current user id
            long userId = getCurrentUserId();
            c.setUserId(userId);
            groups = groupService.search(c, 0, 0).getValues();
        } catch (SecurityGroupException e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature,
                    new SecurityGroupsActionException(
                            "error occurs while doing the input", e));
        }
        LoggingWrapperUtility.logExit(getLogger(), signature,
                new Object[] { INPUT });
        return INPUT;
    }

    /**
     * <p>
     * Enters send group invitation page. It will prepare the data used by the send group invitation page.
     * </p>
     *
     * @throws SecurityGroupsActionException
     *             if any error is caught during the operation.
     * @return SUCCESS if no error occurred
     * @throws SecurityGroupsActionException if any error occurred
     * @since 1.1
     */
    public String enterSendInvitation() throws SecurityGroupsActionException {
        final String signature = CLASS_NAME + ".enterSendInvitation()";
        LoggingWrapperUtility.logEntrance(getLogger(), signature, null, null);
        try {
            checkPermission();
            
            input();
            LoggingWrapperUtility.logExit(getLogger(), signature, new Object[] { SUCCESS });
            return SUCCESS;
        } catch (Throwable e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature, new SecurityGroupsActionException(
                "Error occurs while preparing the send invitation page.", e));
        }
    }


    /**
     * validate the user handle.
     *
     * @throws SecurityGroupsActionValidationException if any error occurs when validation the inputs
     * @throws SecurityGroupsActionException if any error occurs when performing the operation
     * @since 1.1
     */
    public String validateUserHandle() throws SecurityGroupsActionException {
        final String signature = CLASS_NAME + ".validateUserHandle()";
        LoggingWrapperUtility.logEntrance(getLogger(), signature, null, null);
        try {
            checkPermission();

            // validate the fields
            validateData();
            HelperUtility.checkFieldErrors(this);

            // first get all user ids to avoid duplicate searching to improve performance
            List<Long> userIds = new ArrayList<Long>();
            for (String handle : handles) {
                // Find User by handle, there must be exactly one
                UserDTO user = HelperUtility.checkHandle(handle, groupUserService);
                long userId = user.getUserId();
                if (userIds.contains(userId)) {
                    throw new SecurityGroupsActionException("There should be no duplicate handle exists: " + handle);
                }
                userIds.add(userId);
            }

            // prepare the json result
            Map<String, List> result = new HashMap<String, List>();
            result.put("result", userIds);
            setResult(result);
        } catch (Throwable e) {
            setResult(e);
        }
        LoggingWrapperUtility.logExit(getLogger(), signature, null);
        return SUCCESS;
    }
    
    /**
     * Sends invitations to the specified handles for specified groups.
     * 
     * @throws SecurityGroupsActionValidationException if any error occurs when validation the inputs
     * @throws SecurityGroupsActionException if any error occurs when performing the operation
     * @since 1.1
     */
    public void executeAction() throws SecurityGroupsActionException {
        final String signature = CLASS_NAME + ".executeAction()";
        LoggingWrapperUtility.logEntrance(getLogger(), signature, null, null);
        try {
            Map<Long, String> userIdToHandles = new HashMap<Long, String>();
            for(int i=0; i<handles.size(); i++) {
                userIdToHandles.put(userIds.get(i), handles.get(i));
            }
            
            // send invitations for each group
            boolean exist = false;
            for (long groupId : groupIds) {
                // check if the group exists
                Group group = groupService.get(groupId);
                if (group == null) {
                    throw new SecurityGroupsActionException("The group with the id " + groupId
                        + " doesn't exist.");
                }
                // get all existing group member user ids
                List<GroupMember> groupMembers = group.getGroupMembers();
                Set<Long> oldUserIds = new HashSet<Long>();
                for (GroupMember gm : groupMembers) {
                    oldUserIds.add(gm.getUserId());
                }                
                
                List<GroupMember> newGroupMembers = new ArrayList<GroupMember>();
                for (Map.Entry<Long, String> userIdHandlePair : userIdToHandles.entrySet()) {
                    if (oldUserIds.contains(userIdHandlePair.getKey())) {
                        continue;
                    }

                    // create a GroupMember for the new user
                    GroupMember groupMember = new GroupMember();
                    groupMember.setUserId(userIdHandlePair.getKey());
                    groupMember.setUseGroupDefault(true);
                    groupMember.setActive(false);
                    groupMember.setGroup(group);
                    groupMember.setHandle(userIdHandlePair.getValue());
                    newGroupMembers.add(groupMember);
                    groupMembers.add(groupMember);
                    exist = true;
                }
                
                // update the group
                group.setGroupMembers(groupMembers);
                groupService.update(group);

                // send invitations
                HelperUtility.sendInvitations(getAuditService(), groupInvitationService, acceptRejectUrlBase,
                    registrationUrl, newGroupMembers, false);
            }
            
            // prepare the json result
            Map<String, String> result = new HashMap<String, String>();
            result.put("result", exist ? "success" : "nope");
            setResult(result);
        } catch (EntityNotFoundException e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature,
                    new SecurityGroupsActionException(
                            "error occurs while sending invitation", e));
        } catch (SecurityGroupException e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature,
                    new SecurityGroupsActionException(
                            "error occurs while sending invitation", e));
        } catch (SecurityGroupsActionException e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature, e);
        }
        LoggingWrapperUtility.logExit(getLogger(), signature, null);
    }

    /**
     * Checks the permission for sending invitation.
     *
     * @throws SecurityGroupException if any error occurred in authorization service
     * @throws SecurityGroupsActionException if the permission is denied
     * @since 1.1
     */
    private void checkPermission() throws SecurityGroupException, SecurityGroupsActionException {
        // check permission
        if (!getAuthorizationService().checkApprovalPermission(getCurrentUserId())) {
            ServletActionContext.getRequest().setAttribute("errorPageMessage",
                "Sorry, you don't have permission to send invitation.");
            throw new SecurityGroupsActionException(
                "Has no permission to send invitation.");
        }
    }

    /**
     * Performs complex validation that can't be easily done with declarative
     * XML validation.
     * 
     * @since 1.1
     */
    public void validateData() {
        final String signature = CLASS_NAME + ".validateData()";
        LoggingWrapperUtility.logEntrance(getLogger(), signature, null, null);

        // validate field.
        validateListField(groupIds, "groupIds");
        validateListField(handles, "handles");

        LoggingWrapperUtility.logExit(getLogger(), signature, null);
    }

    /**
     * Validate the list field.
     *
     * @param value list to check.
     * @param name of the list.
     */
    @SuppressWarnings({
        "rawtypes", "unchecked" })
    private void validateListField(List value, String name) {
        super.validate();
        try {
            ValidationUtility.checkNotNull(value, name,
                    SecurityGroupsActionException.class);
            ValidationUtility.checkNotEmpty(value, name,
                    SecurityGroupsActionException.class);
            ValidationUtility.checkNotNullElements(value, name,
                    SecurityGroupsActionException.class);
        } catch (SecurityGroupsActionException e) {
            addFieldError(name, name + " can't be null or empty or contain null element.");
            return;
        }

        Set set = new HashSet(value);
        if (set.size() != value.size()) {
            addFieldError(name, name + " can't contain duplicate elements.");
        }
        if (value.get(0) instanceof String) {
            for (Object str : value) {
                HelperUtility.checkStringLength(this, (String) str, name + " element");
            }
        }
    }

    /**
     * Checks whether this class was configured by Spring properly.
     *
     * @throws SecurityGroupsActionConfigurationException - if the class was not
     *             configured properly (e.g. when required property was not
     *             injected or property value is invalid).
     *
     */
    @PostConstruct
    public void checkInit() {
        super.checkInit();
        ValidationUtility.checkNotNull(groupService, "groupService",
                SecurityGroupsActionConfigurationException.class);
        ValidationUtility.checkNotNull(groupInvitationService,
                "groupInvitationService",
                SecurityGroupsActionConfigurationException.class);
        ValidationUtility.checkNotNull(groupUserService, "groupUserService",
                SecurityGroupsActionConfigurationException.class);

        // not null nor empty.
        ValidationUtility.checkNotNullNorEmpty(registrationUrl,
                "registrationUrl",
                SecurityGroupsActionConfigurationException.class);
        ValidationUtility.checkNotNullNorEmpty(acceptRejectUrlBase,
                "acceptRejectUrlBase",
                SecurityGroupsActionConfigurationException.class);
    }

    /**
     * Sets Handles of users to send invitations to.
     *
     * @param handles Handles of users to send invitations to.
     *
     */
    public void setHandles(List<String> handles) {
        this.handles = handles;
    }

    /**
     * Sets Ids of users to send invitations to.
     *
     * @param userIds Ids of users to send invitations to.
     *
     */
    public void setUserIds(List<Long> userIds) {
        this.userIds = userIds;
    }

    /**
     * Gets Groups which can be administrated by current user. It will be used
     * by JSP to render groups dropdown list.
     *
     * @return Groups which can be administrated by current user. It will be
     *         used by JSP to render groups dropdown list.
     *
     */
    public List<Group> getGroups() {
        return groups;
    }

    /**
     * Sets GroupService used to manage group information.
     *
     * @param groupService GroupService used to manage group information.
     *
     */
    public void setGroupService(GroupService groupService) {
        this.groupService = groupService;
    }

    /**
     * Sets GroupInvitationService used to create group invitations.
     *
     * @param groupInvitationService GroupInvitationService used to create group
     *            invitations.
     *
     */
    public void setGroupInvitationService(
            GroupInvitationService groupInvitationService) {
        this.groupInvitationService = groupInvitationService;
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

    /**
     * Sets Registration URL which will be added to invitations.
     *
     * @param registrationUrl Registration URL which will be added to
     *            invitations.
     *
     */
    public void setRegistrationUrl(String registrationUrl) {
        this.registrationUrl = registrationUrl;
    }

    /**
     * Sets Base part of URL for accepting/rejecting invitations. Hyperlinks to
     * such URL's will be added to invitations.
     *
     * @param acceptRejectUrlBase Base part of URL for accepting/rejecting
     *            invitations. Hyperlinks to such URL's will be added to
     *            invitations.
     *
     */
    public void setAcceptRejectUrlBase(String acceptRejectUrlBase) {
        this.acceptRejectUrlBase = acceptRejectUrlBase;
    }

    /**
     * Sets the group ids.
     * 
     * @param groupIds the group ids to set
     * @since 1.1
     */
    public void setGroupIds(List<Long> groupIds) {
        this.groupIds = groupIds;
    }
}
