/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.groups;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.struts2.ServletActionContext;

import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.commons.utils.ValidationUtility;
import com.topcoder.security.groups.model.GroupInvitation;
import com.topcoder.security.groups.model.GroupMember;
import com.topcoder.security.groups.model.InvitationStatus;
import com.topcoder.security.groups.services.EntityNotFoundException;
import com.topcoder.security.groups.services.GroupMemberService;
import com.topcoder.security.groups.services.SecurityGroupException;

/**
 * <p>
 * This Struts action allows admin to approve or reject users to a group. This
 * action will be used when either "Approve" or "Reject" button will be clicked
 * on a page corresponding to administrator-view-pending-approval.html from the
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
 * <b>Sample Usage:</b>
 *
 * Spring configuration:
 *
 * <pre>
 * &lt;bean id="baseAction" abstract="true"
 *    class="com.topcoder.security.groups.actions.GroupInvitationAwareBaseAction"&gt;
 *   &lt;property name="logger" ref="logger"/&gt;
 *   &lt;property name="groupInvitationService" ref="groupInvitationService"/&gt;
 * &lt;/bean&gt;
 * &lt;bean id="ApproveRejectPendingUserAction" parent="baseAction"
 *   class="com.topcoder.security.groups.actions.ApproveRejectPendingUserAction"&gt;
 *   &lt;property name="groupMemberService" ref="groupMemberService"/&gt;
 * &lt;/bean&gt;
 * </pre>
 *
 * Struts configuration:
 *
 * <pre>
 * &lt;action name="approveRejectPendingUserAction" class="ApproveRejectPendingUserAction"
 *    method="execute"&gt;
 *    &lt;result name="success"&gt;success.jsp&lt;/result&gt;
 *    &lt;result name="input"&gt;accept_reject_pending_user.jsp&lt;/result&gt;
 * &lt;/action&gt;
 * </pre>
 *
 * </p>
 *
 * <p>
 * Version 1.1 (Release Assembly - TopCoder Security Groups Frontend - Invitations Approvals) change notes:
 * <ol>
 *   <li>Updated to use the same package name as other group actions.</li>
 *   <li>Updated the whole class to support ajax calling.</li>
 *   <li>Updated to add permission checking before approving/rejecting pending approvals, also
 *   updated to check the status is APPROVAL_PENDING.</li>
 * </ol>
 * </p>
 *
 * @author gevak, TCSDEVELOPER, TCSASSEMBLER
 * @version 1.1
 */
@SuppressWarnings("serial")
public class ApproveRejectPendingUserAction extends
        GroupInvitationAwareBaseAction {
    /**
     * Represent the class name.
     */
    private static final String CLASS_NAME = ApproveRejectPendingUserAction.class
            .getName();
    /**
     * Contains IDs of invitations, which should be approved/rejected. It is
     * used in execute() method. It is injected via the setter with no
     * validation, thus can be any value. When execute() is called,
     * <code>validate</code> method will validate it. Mutable via setter.
     */
    private List<Long> invitationIds;

    /**
     * Indicates whether specified invitations should be approved (if true) or
     * rejected (if false). It is used in execute() method. It is injected via
     * the setter with no validation, thus can be any value. When execute() is
     * called, XML validations are applied on this variable. Mutable via setter.
     */
    private Boolean approved;

    /**
     * Reject reason. Only makes sense if "approved" input parameter will be
     * false. It is used in execute() method. It is injected via the setter with
     * no validation, thus can be any value. When execute() is called, XML
     * validations are applied on this variable. Mutable via setter.
     */
    private String rejectReason;

    /**
     * GroupMemberService used to activate group member in case of invitation
     * approval. It is used in execute() method. It is injected via the setter
     * with no validation, thus can be any value. However,
     * <code>checkInit</code> method will ensure that it's not null. Mutable via
     * setter.
     */
    private GroupMemberService groupMemberService;

    /**
     * Empty default constructor.
     */
    public ApproveRejectPendingUserAction() {
    }
    
    /**
     * Calls method executeAction and wraps all exception.
     * 
     * @return always SUCCESS
     */
    public String execute() {
        try {
            executeAction();
        } catch (Throwable e) {
            setResult(e);
        }
        return SUCCESS;
    }

    /**
     * Approves or rejects users to a group.
     *
     *
     * @throws SecurityGroupsActionException if any error occurs when performing
     *             the operation.
     * @since 1.1
     */
    public void executeAction() throws SecurityGroupsActionException {
        final String signature = CLASS_NAME + ".executeAction()";
        LoggingWrapperUtility.logEntrance(getLogger(), signature, new String[] {
            "approved", "rejectReason" }, new Object[] {approved,
                rejectReason });
        try {
            // check permission
            if (!getAuthorizationService().checkApprovalPermission(getCurrentUserId())) {
                ServletActionContext.getRequest().setAttribute("errorPageMessage",
                    "Sorry, you don't have permission to approve/reject pending approvals.");
                throw new SecurityGroupsActionException(
                    "Has no permission to approve/reject pending approvals.");
            }
            // validate the fields
            validateData();
            HelperUtility.checkFieldErrors(this);

            if (approved == null) {
                approved = false;
            }
            for (long invitationId : invitationIds) {
                // Get invitation:
                GroupInvitation invitation = getGroupInvitationService()
                        .getInvitation(invitationId);
                ValidationUtility.checkNotNull(invitation, "invitation",
                        SecurityGroupsActionException.class);
                if (invitation.getStatus() != InvitationStatus.APPROVAL_PENDING) {
                    throw new SecurityGroupsActionValidationException(
                        "You can only approve/reject group invitations with status APPROVAL_PENDING.");
                }
                
                // Save old status (for auditing) and set invitation status
                InvitationStatus oldStatus = invitation.getStatus();
                ValidationUtility.checkNotNull(oldStatus, "oldStatus",
                        SecurityGroupsActionException.class);
                invitation
                        .setStatus(approved ? InvitationStatus.APPROVAL_ACCEPTED
                                : InvitationStatus.APPROVAL_REJECTED);
                // If rejected, populate reject reason
                if (!approved) {
                    invitation.setRejectReason(rejectReason);
                }
                // Set invitation.acceptedOrRejectedOn with current time:
                invitation.setApprovalProcessedOn(new Date());
                // Update invitation using the groupInvitationService:
                getGroupInvitationService().updateInvitation(invitation);
                // Perform audit
                HelperUtility.audit(getAuditService(), "invitation ID "
                        + invitationId + " status = "
                        + String.valueOf(oldStatus), "invitation ID "
                        + invitationId + " status = "
                        + invitation.getStatus().toString());

                // If approved, activate group member
                if (approved) {
                    // update group member
                    GroupMember groupMember = invitation.getGroupMember();
                    ValidationUtility.checkNotNull(groupMember, "groupMember",
                            SecurityGroupsActionException.class);
                    groupMember.setActive(true);
                    groupMember.setActivatedOn(new Date());
                    groupMemberService.update(groupMember);
                    // Perform audit
                    HelperUtility.audit(getAuditService(), "group member ID = "
                            + groupMember.getId() + " active = false",
                            "group member ID = " + groupMember.getId()
                                    + " active = true");

                }
            }
            
            // set result
            Map<String, Object> result = new HashMap<String, Object>();
            result.put("operation", approved ? "approve" : "reject");
            result.put("result", "success");
            setResult(result);
        } catch (EntityNotFoundException e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature,
                    new SecurityGroupsActionException(
                            "error occurs while executing the action", e));
        } catch (SecurityGroupException e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature,
                    new SecurityGroupsActionException(
                            "error occurs while executing the action", e));
        } catch (SecurityGroupsActionException e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature, e);
        }
        LoggingWrapperUtility.logExit(getLogger(), signature, null);
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
        ValidationUtility.checkNotNull(groupMemberService,
                "groupMemberService",
                SecurityGroupsActionConfigurationException.class);
    }

    /**
     * Validate the fields.
     * 
     * @since 1.1
     */
    public void validateData() {
        final String signature = CLASS_NAME + ".validateData()";
        LoggingWrapperUtility.logEntrance(getLogger(), signature, null, null);
        // Can't be null.
        if (invitationIds == null) {
            addFieldError("invitationIds", "invitationIds can't be null.");
            LoggingWrapperUtility.logExit(getLogger(), signature, null);
            return;
        }
        // Can't be empty.
        if (invitationIds.isEmpty()) {
            addFieldError("invitationIds", "invitationIds can't be empty.");
            LoggingWrapperUtility.logExit(getLogger(), signature, null);
            return;
        }
        Set<Long> invitationIdsSet = new HashSet<Long>(invitationIds);
        // Can't contain null or equal element.
        if (invitationIdsSet.contains(null)) {
            addFieldError("invitationIds", "invitationIds can't contain null.");
        }
        if (invitationIdsSet.size() != invitationIds.size()) {
            addFieldError("invitationIds",
                    "invitationIds can't contain equal elements.");
        }
        
        // reject reason length
        if (rejectReason != null && rejectReason.trim().length() > 500) {
            addFieldError("rejectReason",
                "The length of reject reason should not be more than 500 characters.");
        }
        LoggingWrapperUtility.logExit(getLogger(), signature, null);
    }

    /**
     * Sets IDs of invitations, which should be approved/rejected.
     *
     * @param invitationIds IDs of invitations, which should be
     *            approved/rejected.
     *
     */
    public void setInvitationIds(List<Long> invitationIds) {
        this.invitationIds = invitationIds;
    }

    /**
     * Sets a flag indicating whether specified invitations should be approved
     * (if true) or rejected (if false).
     *
     *
     * @param approved Flag indicating whether specified invitations should be
     *            approved (if true) or rejected (if false).
     *
     */
    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    /**
     * Sets reject reason (which only makes sense if "approved" input parameter
     * will be false).
     *
     *
     * @param rejectReason Reject reason (which only makes sense if "approved"
     *            input parameter will be false).
     *
     */
    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    /**
     * Sets GroupMemberService used to activate group member in case of
     * invitation approval.
     *
     *
     * @param groupMemberService GroupMemberService used to activate group
     *            member in case of invitation approval.
     *
     */
    public void setGroupMemberService(GroupMemberService groupMemberService) {
        this.groupMemberService = groupMemberService;
    }
}
