/*
 * Copyright (C) 2011 - 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.groups;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.topcoder.direct.services.view.action.contest.launch.DirectStrutsActionsHelper;
import com.topcoder.security.TCPrincipal;
import com.topcoder.security.TCSubject;
import com.topcoder.security.groups.model.DirectProject;
import com.topcoder.security.groups.model.Group;
import com.topcoder.security.groups.model.GroupInvitation;
import com.topcoder.security.groups.model.GroupMember;
import com.topcoder.security.groups.model.InvitationStatus;
import com.topcoder.security.groups.services.AuthorizationService;
import com.topcoder.security.groups.services.GroupInvitationService;
import com.topcoder.security.groups.services.SecurityGroupException;
import com.topcoder.security.groups.services.UserService;
import com.topcoder.security.groups.services.dto.UserDTO;

/**
 * Helper class for this component.
 *
 * <p>
 * Version 1.1 (TopCoder Security Groups Frontend - Create Update Group) change notes:
 * <ol>
 *   <li>Update <code>validate</code> method to validate the mbmer handles.</li>
 *   <li>Added methods <code>fillRelation</code>, <code>fillHandle</code> and <code>checkPermission</code>.</li>
 * </ol>
 * </p>
 *
 * <p>
 * Version 1.2 (Release Assembly - TopCoder Security Groups Frontend - Invitations Approvals) change notes:
 * <ol>
 *   <li>Updated field {@link #INVITATION_URL} to remove the first character '/'.</li>
 * </ol>
 * </p>
 *
 * @author woodjhon, hanshuai, flexme, TCSASSEMBLER
 * @version 1.2
 */
final class Helper {

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
     * <p>
     * Private constructor to prevent this class being instantiated.
     * </p>
     */
    private Helper() {
        // Empty
    }

    /**
     * Get the current user name.
     *
     * @return the user name.
     */
    public static String getUserName() {
        TCSubject subject = DirectStrutsActionsHelper.getTCSubjectFromSession();
        TCPrincipal principal = (TCPrincipal) subject.getPrincipals().iterator().next();
        return principal.getName();
    }

    /**
     * <p>
     * send invitations to the members.
     * </p>
     *
     * @param instance
     *            the CreateUpdateGroupAction instance used to send invitations.
     * @param members
     *            the members
     * @throws SecurityGroupException
     *             send invitation fails.
     */
    public static void sendInvitations(CreateUpdateGroupAction instance, List<GroupMember> members)
        throws SecurityGroupException {
        if (members == null || members.size() == 0) {
            return;
        }
        GroupInvitationService groupInvitationService = instance.getGroupInvitationService();
        String prefix = instance.getAcceptRejectUrlBase() + INVITATION_URL;
        for (GroupMember member : members) {
            // create invitation
            GroupInvitation invitation = new GroupInvitation();
            invitation.setGroupMember(member);
            invitation.setStatus(InvitationStatus.PENDING);
            invitation.setSentOn(new Date());
            // save invitation
            groupInvitationService.addInvitation(invitation);
            // construct URLs
            String urlPrefix = prefix + invitation.getId();
            String acceptUrl = urlPrefix + ACCEPTED_TURE_URL;
            String rejectUrl = urlPrefix + ACCEPTED_FALSE_URL;
            // send invitation

            groupInvitationService.sendInvitation(invitation, instance.getRegistrationUrl(), acceptUrl, rejectUrl);
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
     * Check the list is null or empty.
     *
     * @param instance
     *            the CreateUpdateGroupAction instance used to check.
     * @param ls
     *            the list
     * @param fieldName
     *            the field name
     * @throws SecurityGroupsActionValidationException
     *             if validation fails.
     */
    private static void checkNullNorEmpty(CreateUpdateGroupAction instance, List<?> ls, String fieldName) {
        checkNull(instance, ls, fieldName);
        if (ls.size() == 0) {
            instance.addFieldError(fieldName, instance.getText(fieldName + ".missing"));
            throw new SecurityGroupsActionValidationException(fieldName + " is required");
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
    public static void checkPositive(CreateUpdateGroupAction instance, long value, String fieldName) {
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
    public static void validate(CreateUpdateGroupAction instance, Group group) {
        check(instance, group.getName(), "group.name");
        checkNull(instance, group.getDefaultPermission(), "group.defaultPermission");
        checkPositive(instance, group.getClient().getId(), "group.client.id");
        //checkNullNorEmpty(instance, group.getBillingAccounts(), "group.billingAccounts");
        //checkNullNorEmpty(instance, group.getDirectProjects(), "group.directProjects");
        //checkNullNorEmpty(instance, group.getRestrictions(), "group.restrictions");
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
        if (member.getId() == 0) {
            // new
            try {
                List<UserDTO> users = userService.search(member.getHandle());
                if (users.size() == 0) {
                    throw new SecurityGroupsActionValidationException("Handle " + member.getHandle() + " doesn't exists");
                }
                for (UserDTO user : users) {
                    if (user.getHandle().equalsIgnoreCase(member.getHandle())) {
                        member.setUserId(user.getUserId());
                        return;
                    }
                }
                throw new SecurityGroupsActionValidationException("Handle " + member.getHandle() + " doesn't exists");
            } catch (SecurityGroupException e) {
                throw new SecurityGroupsActionValidationException("Error occurs when validating members", e);
            }
        }
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
    public static void validate(CreateUpdateGroupAction instance, UserService userService, List<GroupMember> members) {
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
    public static void fillRelation(Group group) {
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
    public static void fillHandle(UserService groupUserService, Group group) throws SecurityGroupException {
        List<GroupMember> members = group.getGroupMembers();
        for (GroupMember member : members) {
            if (member.getHandle() == null || member.getHandle().trim().length() == 0) {
                UserDTO dto = groupUserService.get(member.getUserId());
                if (dto != null) {
                    member.setHandle(dto.getHandle());
                }
            }
        }
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
    public static boolean checkPermission(HttpServletRequest request, long userId, AuthorizationService authService, Group group, boolean isCustomerAdmin, boolean allowFullUser) throws SecurityGroupException {
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
}
