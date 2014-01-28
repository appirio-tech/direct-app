/*
 * Copyright (C) 2012 - 2014 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.groups;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.topcoder.commons.utils.ValidationUtility;
import com.topcoder.direct.services.view.action.contest.launch.DirectStrutsActionsHelper;
import com.topcoder.security.TCPrincipal;
import com.topcoder.security.TCSubject;
import com.topcoder.security.groups.model.DirectProject;
import com.topcoder.security.groups.model.Group;
import com.topcoder.security.groups.model.GroupAuditRecord;
import com.topcoder.security.groups.model.GroupInvitation;
import com.topcoder.security.groups.model.GroupMember;
import com.topcoder.security.groups.model.GroupPermissionType;
import com.topcoder.security.groups.model.InvitationStatus;
import com.topcoder.security.groups.services.AuthorizationService;
import com.topcoder.security.groups.services.GroupAuditService;
import com.topcoder.security.groups.services.GroupInvitationService;
import com.topcoder.security.groups.services.SecurityGroupException;
import com.topcoder.security.groups.services.UserService;
import com.topcoder.security.groups.services.dto.UserDTO;

/**
 * <p>
 * Helper class for this component.
 * </p>
 *
 * <p>
 * Version 1.1 (Release Assembly - TopCoder Security Groups Frontend - Invitations Approvals) change notes:
 * <ol>
 *   <li>Updated to use the same package name as other group actions.</li>
 *   <li>Added {@link #checkFieldErrors(ActionSupport)} to check field errors in the action.</li>
 * </ol>
 * </p>
 *
 * <p>
 * Version 1.2 (Release Assembly - TopCoder Security Groups Frontend - Search Delete Groups) change notes:
 * <ol>
 *   <li>Added fields {@link #DEFAULT_PAGE} and {@link #DEFAULT_PAGESIZE}.</li>
 *   <li>Added {@link #checkCriteriaPermissions(ActionSupport, List)} to check criteria permissions parameter
 *   of the given action.</li>
 *   <li>Added {@link #checkPageAndPageSize(ActionSupport, long, long)} to check page and page size parameters
 *   of the given action.</li>
 *   <li>Merged the Helper.java of previous assembly.</li>
 * </ol>
 * </p>
 *
 * <p>
 * Version 1.3 (Release Assembly - TopCoder Security Groups Frontend - Miscellaneous) change notes:
 * <ol>
 *   <li>Added {@link #checkSingleElementList(List, String)} to check the list which should contain non-null
 *   single element.</li>
 *   <li>Added {@link #checkStringLength(ActionSupport, String, String)} to check the string length.</li>
 *   <li>Added {@link #checkDateRange(ActionSupport, Date, Date, String, String)} to check the date range fields.</li>
 *   <li>Refactored {@link #sendInvitations(GroupInvitationService, String, List, boolean)} to be reusable by
 *   different action classes, also it's updated to support skipping invitation email for new member users.</li>
 *   <li>Added {@link #checkHandle(String, UserService) to check if the handle exists and updated
 *   {@link #validate(CreateUpdateGroupAction, UserService, GroupMember) to reuse it.</li>
 * </ol>
 * </p>
 *
 * <p>
 * Version 1.4 (Release Assembly - TopCoder Security Groups - Release 4) change notes:
 * <ol>
 *   <li>Fix the typos.</li>
 * </ol>
 * </p>
 * 
 * <p>
 * Version 1.5 (48hr Cockpit Group Management Improvement Release Assembly) change notes:
 * <ol>
 *   <li>Refactored {@link #fillHandle(UserService, Group)} to sort the handles.</li>
 * </ol>
 * </p>
 * 
 * <p>
 * Version 1.5.1 change notes:
 * <ol>
 *   <li>Refactored {@link #validate(CreateUpdateGroupAction, UserService, GroupMember)} only look up the
 *   user id when user id is 0.</li>
 * </ol>
 * </p>
 * 
 * @author woodjhon, hanshuai, flexme, suno1234, TCSDEVELOPER, TCSASSEMBLER
 * @version 1.5
 */
final class HelperUtility {
    /**
     * The default page size.
     * @since 1.2
     */
    static final int DEFAULT_PAGESIZE = 10;
    
    /**
     * The default page.
     * @since 1.2
     */
    static final int DEFAULT_PAGE = 1;

    /**
     * Represent the invitation url.
     */
    private static final String INVITATION_URL = "?invitationId=";

    /**
     * Represent the accept true url.
     */
    private static final String ACCEPTED_TURE_URL = "&accepted=true";

    /**
     * Represent the accept false url.
     */
    private static final String ACCEPTED_FALSE_URL = "&accepted=false";

    /**
     * Represent the max length of string.
     */
    private static final int MAXLENGTH = 45;

    /**
     * Default empty constructor.
     */
    private HelperUtility() {

    }

    /**
     * Perform the audit.
     *
     * @param auditService the audit service.
     * @param previousValue the previous value.
     * @param newValue the new value.
     * @throws SecurityGroupException if fail to perform the audit.
     */
    static void audit(GroupAuditService auditService, String previousValue,
            String newValue) throws SecurityGroupException {

        // Create and populate audit record
        try {
            TCSubject subject = DirectStrutsActionsHelper.getTCSubjectFromSession();
            ValidationUtility.checkNotNull(subject, "subject",
                    SecurityGroupException.class);
            ValidationUtility.checkNotNull(subject.getPrincipals(),
                    "subject.principals", SecurityGroupException.class);
            ValidationUtility.checkNotEmpty(subject.getPrincipals(),
                    "subject.principals", SecurityGroupException.class);
            String handle = ((TCPrincipal) subject.getPrincipals().iterator()
                    .next()).getName();
            HttpServletRequest httpServletRequest = ServletActionContext
                    .getRequest();
            ValidationUtility.checkNotNull(httpServletRequest,
                    "httpServletRequest", SecurityGroupException.class);
            String ipAddress = httpServletRequest.getRemoteAddr();
            GroupAuditRecord auditRecord = new GroupAuditRecord();
            auditRecord.setDate(new Date());
            auditRecord.setHandle(handle);
            auditRecord.setIpAddress(ipAddress);
            auditRecord.setPreviousValue(previousValue);
            auditRecord.setNewValue(newValue);
            // Add audit record to persistence.
            auditService.add(auditRecord);
        } catch (ClassCastException e) {
            throw new SecurityGroupException("Fail to perform the audit.", e);
        }
    }

    /**
     * Checks the string length.
     *
     * @param action the related action
     * @param value the string to check length
     * @param name the name of the string
     * @since 1.3
     */
    static void checkStringLength(ActionSupport action, String value, String name) {
        if (value.trim().length() == 0 || value.length() > MAXLENGTH) {
            action.addFieldError(name, name + " can't be empty or exceed "
                    + MAXLENGTH + " character.");
        }
    }

    /**
     * Checks if the date range is valid.
     *
     * @param action the related action
     * @param from the from date field
     * @param to the to date field
     * @param nameFrom the name of the from date field
     * @param nameTo the name of the to date field
     * @since 1.3
     */
    static void checkDateRange(ActionSupport action, Date from, Date to,
        String nameFrom, String nameTo) {
        if (from != null && to != null && to.before(from)) {
            action.addFieldError(nameTo, nameTo + " must not before the " + nameFrom);
        }        
    }

    /**
     * Checks if the criteria permissions parameter of the given action is valid.
     * 
     * @param action the related action
     * @param permissions the permissions list to check
     * @since 1.2
     */
    static void checkCriteriaPermissions(ActionSupport action, List<GroupPermissionType> permissions) {
        if (permissions == null || permissions.size() == 0) {
            action.addFieldError("criteria.permissions",
                    "criteria.permissions can't be null or empty.");
            // if permissions is null or empty, return directly
            return;
        }
        for (GroupPermissionType permission : permissions) {
            if (permission == null) {
                action.addFieldError("criteria.permissions",
                        "criteria.permissions can't contain null.");
            }
        }
        Set<GroupPermissionType> set = new HashSet<GroupPermissionType>(
                permissions);
        if (set.size() != permissions.size()) {
            action.addFieldError("criteria.permissions",
                    "criteria.permissions can't contain equal element.");
        }        
    }

    /**
     * Checks if the page and page size parameters of the given action are valid.
     * 
     * @param action the related action
     * @param page the page parameter to check
     * @param pageSize the page size parameter to check
     * @since 1.2
     */
    static void checkPageAndPageSize(ActionSupport action, long page, long pageSize) {
        if (page != 0 && pageSize <= 0) {
            action.addFieldError("pageSize", "PageSize must be positive when page != 0");
        }
        if (page < 0) {
            action.addFieldError("page", "Page must be non-negative");
        }
    }
    
    /**
     * Checks if any field errors exist in the action.
     * 
     * @param action the action to check
     * @throws SecurityGroupsActionValidationException if any field errors exist
     * @since 1.1
     */
    static void checkFieldErrors(ActionSupport action) {
        if (action.getFieldErrors().size() > 0) {
            StringBuilder builder = new StringBuilder();
            for (Map.Entry<String, List<String>> pair : action.getFieldErrors().entrySet()) {
                for (String err : pair.getValue()) {
                    builder.append(err).append("\r\n");
                }
            }
            throw new SecurityGroupsActionValidationException(builder.toString());
        }
    }

    /**
     * Get the current user name.
     *
     * @return the user name.
     */
    static String getUserName() {
        TCSubject subject = DirectStrutsActionsHelper.getTCSubjectFromSession();
        TCPrincipal principal = (TCPrincipal) subject.getPrincipals().iterator().next();
        return principal.getName();
    }

    /**
     * <p>
     * send invitations to the members.
     * </p>
     *
     * @param groupAuditService
     *            the GroupAuditService instance used to perform auditing
     * @param groupInvitationService
     *            the GroupInvitationService instance used to send invitations
     * @param acceptRejectUrlBase
     *            the accept/reject URL base
     * @param members
     *            the members
     * @param skipInvitationEmail
     *            whether or not skipping invitation email for new member users
     * @throws SecurityGroupException
     *             send invitation fails.
     */
    static void sendInvitations(GroupAuditService groupAuditService,
            GroupInvitationService groupInvitationService, String acceptRejectUrlBase, String registrationUrl,
            List<GroupMember> members, boolean skipInvitationEmail)
        throws SecurityGroupException {
        if (members == null || members.size() == 0) {
            return;
        }
        String prefix = acceptRejectUrlBase + INVITATION_URL;
        List<GroupInvitation> invitations = new ArrayList<GroupInvitation>();
        Date now = new Date();
        for (GroupMember member : members) {
            // Perform audit
            Group group = member.getGroup();
            HelperUtility.audit(groupAuditService, "",
                "group ID = " + group.getId() + " name = " + group.getName()
                        + "; new group member user ID = " + member.getUserId()
                        + " handle = " + member.getHandle());
            
            // create invitation
            GroupInvitation invitation = new GroupInvitation();
            invitations.add(invitation);
            invitation.setGroupMember(member);
            invitation.setSentOn(now);
            if (skipInvitationEmail) {
                // set invitation status as APPROVAL_ACCEPTED
                // accepted_or_rejected_on and approval_processed_on will be set to current date
                // the newly added members will be set to active and activated_on set to current date
                invitation.setStatus(InvitationStatus.APPROVAL_ACCEPTED);
                invitation.setAcceptedOrRejectedOn(now);
                invitation.setApprovalProcessedOn(now);
            } else {
                invitation.setStatus(InvitationStatus.PENDING);
            }
            
            // save invitation
            groupInvitationService.addInvitation(invitation);
        }

        if (!skipInvitationEmail) {
            for (GroupInvitation invitation : invitations) {
                // construct URLs
                String urlPrefix = prefix + invitation.getId();
                String acceptUrl = urlPrefix + ACCEPTED_TURE_URL;
                String rejectUrl = urlPrefix + ACCEPTED_FALSE_URL;
                // send invitation    
                groupInvitationService.sendInvitation(invitation, registrationUrl,
                    acceptUrl, rejectUrl, Helper.getUserHandle());
            }
        }
    }

    /**
     * Check the value is positive.
     *
     * @param instance
     *            the CreateUpdateGroupAction instance used to check.
     * @param value
     *            the value
     * @param fieldName
     *            the field name
     * @throws SecurityGroupsActionValidationException
     *             if validation fails.
     */
    static void checkPositive(CreateUpdateGroupAction instance, long value, String fieldName) {
        if (value <= 0) {
            instance.addFieldError(fieldName, instance.getText(fieldName + ".negative"));
            throw new SecurityGroupsActionValidationException(fieldName + " must be positive");
        }
    }
    
    /**
     * validate the group.
     *
     * @param instance
     *            the CreateUpdateGroupAction instance used to validate.
     * @param group
     *            the group
     * @throws SecurityGroupsActionValidationException
     *             if validation fails.
     */
    static void validate(CreateUpdateGroupAction instance, Group group) {
        check(instance, group.getName(), "group.name");
        checkNull(instance, group.getDefaultPermission(), "group.defaultPermission");
        checkPositive(instance, group.getClient().getId(), "group.client.id");
        //checkNullNorEmpty(instance, group.getBillingAccounts(), "group.billingAccounts");
        //checkNullNorEmpty(instance, group.getDirectProjects(), "group.directProjects");
        //checkNullNorEmpty(instance, group.getRestrictions(), "group.restrictions");
    }

    /**
     * validate the group members.
     *
     * @param instance
     *            the CreateUpdateGroupAction instance used to validate.
     * @param userService
     *            the UserService instance used to validate.
     * @param members
     *            the group members
     * @throws SecurityGroupsActionValidationException
     *             if validation fails.
     */
    static void validate(CreateUpdateGroupAction instance, UserService userService, List<GroupMember> members) {
        if (members == null) {
            return;
        }
        for (GroupMember member : members) {
            validate(instance, userService, member);
        }
    }
 
    /**
     * Fill the relationship of the group.
     * 
     * @param group the group
     */
    static void fillRelation(Group group) {
        if (group.getDirectProjects() != null) {
            for (DirectProject dp : group.getDirectProjects()) {
                dp.setGroup(group);
            }
        }
        if (group.getGroupMembers() != null) {
            for (GroupMember gm : group.getGroupMembers()) {
                if (gm.getGroup() == null) {
                    gm.setGroup(group);
                }
            }
        }
    }
    
    /**
     * Set the handle field in GroupMember entity.
     * 
     * @param groupUserService
     *          the UserService instance
     * @param group
     *            the GroupMember's group
     * @throws SecurityGroupException
     *             if any error is caught during the operation
     */
    static void fillHandle(UserService groupUserService, Group group) throws SecurityGroupException {
        List<GroupMember> members = group.getGroupMembers();
        for (GroupMember member : members) {
            if (member.getHandle() == null || member.getHandle().trim().length() == 0) {
                UserDTO dto = groupUserService.get(member.getUserId());
                if (dto != null) {
                    member.setHandle(dto.getHandle());
                }
            }
        }
        
        Collections.sort( group.getGroupMembers(), new Comparator<GroupMember>() {
            public int compare(GroupMember o1, GroupMember o2) {
                return o1.getHandle().compareToIgnoreCase(o2.getHandle());
            }
        });
    }
    
    /**
     * Check the user's permission while viewing the group detail.Admins, customer admins and full permission members of
     * the specific group can view the detail.
     * 
     * @param HttpServletRequest
     *           the http servlet request instance
     * @param userId
     *           the ID of the user to check
     * @param authService
     *           the AuthorizationService instance
     * @param group
     *            the group to check
     * @param isCustomerAdmin
     *            whether the user is customer administrator
     * @param allowFullUser
     *            whether allow full permission user
     * @return the user has permission or not
     * @throws SecurityGroupException
     *             if any error is caught during the operation
     * 
     */
    static boolean checkPermission(HttpServletRequest request, long userId, AuthorizationService authService, Group group, boolean isCustomerAdmin, boolean allowFullUser) throws SecurityGroupException {
        if (group != null) {
            if (group.getArchived()) {
                // can't operate on the archived group
                request.setAttribute("errorPageMessage", "Sorry, the group is archived.");
                return false;
            }
        }
        if (authService.isAdministrator(userId)) {
            return true;
        }
        if (group != null && group.getClient() != null) {
            if (authService.isCustomerAdministrator(userId, group.getClient().getId())) {
                return true;
            }
        }
        if (group != null && allowFullUser) {
            List<Long> ids = authService.getGroupIdsOfFullPermissionsUser(userId);
            for (Long id : ids) {
                if (id == group.getId()) {
                    request.setAttribute("groupFullPermission", Boolean.TRUE);
                    return true;
                }
            }
        }
        if (group == null && isCustomerAdmin) {
            // customer administrator can create group
            return true;
        }

        request.setAttribute("errorPageMessage", "Sorry, you have no permission to create/view/update the group.");
        return false;
    }
    
    /**
     * Checks if the handle exists.
     *
     * @param handle the handle to check
     * @param userService user service
     * @return the UserDTO of the handle
     * @throws SecurityGroupsActionValidationException if any error occurred
     * @since 1.3
     */
    static UserDTO checkHandle(String handle, UserService userService) {
        try {
            List<UserDTO> users = userService.search(handle);
            if (users.size() == 0) {
                throw new SecurityGroupsActionValidationException("Handle " + handle + " doesn't exist");
            }
            for (UserDTO user : users) {
                // compare the handle text and return the user id
                if (user.getHandle().equalsIgnoreCase(handle)) {
                    return user;
                }
            }
            throw new SecurityGroupsActionValidationException("Handle " + handle + " doesn't exist");
        } catch (SecurityGroupException e) {
            throw new SecurityGroupsActionValidationException("Error occurs when validating members", e);
        }
    }
    
    /**
     * Gets the mapping from the group member to the group member invitation.
     * 
     * @param groupInvitationService the group invitation service.
     * @param group the group.
     * @return the mapping from the group member to the group member invitation.
     * @throws SecurityGroupException if any error occurs.
     */
    static Map<Long, GroupInvitation> getGroupMemberInvitations(GroupInvitationService groupInvitationService, Group group) throws SecurityGroupException {
        Map<Long, GroupInvitation> invitations = new HashMap<Long, GroupInvitation>();
        if (group.getGroupMembers() != null) {
            for (GroupMember member : group.getGroupMembers()) {
                GroupInvitation invitation = groupInvitationService.getInvitation(group.getId(), member.getUserId());
                if (invitation != null) {
                    invitations.put(member.getUserId(), invitation);
                }
            }
        }
        return invitations;
    }

    /**
     * Check the object is null.
     *
     * @param instance
     *            the CreateUpdateGroupAction instance used to check.
     * @param object
     *            the object
     * @param fieldName
     *            the field name
     * @throws SecurityGroupsActionValidationException
     *             if validation fails.
     */
    private static void checkNull(CreateUpdateGroupAction instance, Object object, String fieldName) {
        if (object == null) {
            instance.addFieldError(fieldName, instance.getText(fieldName + ".missing"));
            throw new SecurityGroupsActionValidationException(fieldName + " is required");
        }
    }

    /**
     * Check the string has max 45 chars and not empty.
     *
     * @param instance
     *            the CreateUpdateGroupAction instance used to check.
     * @param string
     *            the string
     * @param fieldName
     *            the field name
     * @throws SecurityGroupsActionValidationException
     *             if validation fails.
     */
    private static void check(CreateUpdateGroupAction instance, String string, String fieldName) {
        if (string != null) {
            string = string.trim();
        }
        if (string == null || string.length() == 0) {
            instance.addFieldError(fieldName, instance.getText(fieldName + ".missing"));
            throw new SecurityGroupsActionValidationException(fieldName + " is required");
        }
        if (string.length() > MAXLENGTH) {
            instance.addFieldError(fieldName, instance.getText(fieldName + ".length"));
            throw new SecurityGroupsActionValidationException(fieldName + "'s length is bigger than 45");
        }
    }

    /**
     * validate the group member.
     *
     * @param instance
     *            the CreateUpdateGroupAction instance used to validate.
     * @param userService
     *            the UserService instance used to validate.
     * @param member
     *            the group member
     * @throws SecurityGroupsActionValidationException
     *             if validation fails.
     */
    private static void validate(CreateUpdateGroupAction instance, UserService userService, GroupMember member) {
        check(instance, member.getHandle(), "member.handle");
        if (!member.isUseGroupDefault()) {
            checkNull(instance, member.getSpecificPermission(), "member.specificPermission");
        }
        if (member.getUserId() == 0) {
            member.setUserId(checkHandle(member.getHandle(), userService).getUserId());
        }
    }
}
