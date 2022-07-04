/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.groups;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionSupport;
import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.security.groups.model.GroupInvitation;
import com.topcoder.security.groups.model.GroupPermissionType;
import com.topcoder.security.groups.model.InvitationStatus;
import com.topcoder.security.groups.services.SecurityGroupException;
import com.topcoder.security.groups.services.dto.InvitationSearchCriteria;
import com.topcoder.security.groups.services.dto.PagedResult;

/**
 * <p>
 * This Struts action allows user to view his invitations. This action will be
 * used by JSP corresponding to customer-view-invitation-status.html from the
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
 * <pre>
 * &lt;bean id="baseAction" abstract="true"
 *   class="com.topcoder.security.groups.actions.GroupInvitationSearchBaseAction"&gt;
 *   &lt;property name="logger" ref="logger"/&gt;
 * &lt;/bean&gt;
 * &lt;bean id="ViewInvitationStatusAction" parent="baseAction"
 *  class="com.topcoder.security.groups.actions.ViewInvitationStatusAction"&gt;
 * &lt;/bean&gt;
 * </pre>
 *
 * Struts configuration:
 *
 * <pre>
 * &lt;action name="viewInvitationStatusAction" class="ViewInvitationStatusAction"
 *    method="execute"&gt;
 *    &lt;result name="success"&gt;success.jsp&lt;/result&gt;
 *    &lt;result name="input"&gt;view_invitation_status.jsp&lt;/result&gt;
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
 *   <li>Added field {@link #acceptRejectSuccessResult} and its getter.</li>
 *   <li>Added field {@link #acceptRejectErrorMessage} and its getter.</li>
 * </ol>
 * </p>
 *
 * <p>
 * Version 1.2 (Release Assembly - TopCoder Security Groups Frontend - Search Delete Groups) change notes:
 * <ol>
 *   <li>Refactored to use {@link HelperUtility#checkCriteriaPermissions(ActionSupport, List)}
 *   to check criteria permissions parameter.</li>
 * </ol>
 * </p>
 *
 * <p>
 * Version 1.3 (Release Assembly - TopCoder Security Groups Frontend - Miscellaneous) change notes:
 * <ol>
 *   <li>Removed field MAX_LENGTH and refactored to use {@link HelperUtility#checkStringLength(ActionSupport,
 *   String, String)} to check the string length.</li>
 *   <li>Updated {@link #validateData()} to use {@link HelperUtility#checkDateRange(ActionSupport, Date, Date,
 *   String, String)} to check date range fields.</li>
 * </ol>
 * </p>
 *
 * Version 1.4 (https://apps.topcoder.com/bugs/browse/TCCC-4704) change notes:
 * <ol>
 *   <li>Add the 'Report' permission</li>
 * </ol>
 * </p>
 *
 * @author gevak, xjtufreeman, TCSDEVELOPER, TCSASSEMBLER
 * @version 1.4
 */
@SuppressWarnings("serial")
public class ViewInvitationStatusAction extends GroupInvitationSearchBaseAction {
    /**
     * Represent the class name.
     */
    private static final String CLASS_NAME = ViewInvitationStatusAction.class
            .getName();
    
    /**
     * The chained-in accept/reject group invitation action result error message.
     * 
     * @since 1.1
     */
    private String acceptRejectErrorMessage;
    
    /**
     * The chained-in accept/reject group invitation action success result.
     * 
     * @since 1.1
     */
    private Map<String, String> acceptRejectSuccessResult;

    /**
     * Empty default constructor.
     */
    public ViewInvitationStatusAction() {
    }

    /**
     * Searches for user's invitations, supporting pagination.
     *
     * @return SUCCESS to indicate that the operation was successful.
     * @throws SecurityGroupsActionException if any error occurs when performing
     *             the operation.
     * 
     * @since 1.1
     */
    @SuppressWarnings("unchecked")
    public void executeAction() throws Exception {
        final String signature = CLASS_NAME + ".executeAction()";
        LoggingWrapperUtility.logEntrance(getLogger(), signature, null, null);
        try {
            // save the chained-in result
            Object acceptRejectResult = getResult();
            if (acceptRejectResult != null) {
                if (acceptRejectResult instanceof Throwable) {
                    acceptRejectErrorMessage = ((Throwable)acceptRejectResult).getMessage();
                } else {
                    acceptRejectSuccessResult = (Map<String, String>) acceptRejectResult;
                }
                // initialize default parameters
                InvitationSearchCriteria criteria = getCriteria();
                if (criteria == null) {
                    criteria = new InvitationSearchCriteria();
                    setCriteria(criteria);
                }
                criteria.setPermissions(Arrays.asList(new GroupPermissionType[] {
                    GroupPermissionType.REPORT,    
                    GroupPermissionType.READ,
                    GroupPermissionType.WRITE,
                    GroupPermissionType.FULL
                    }));
            }
            
            // validate the fields
            validateData();
            HelperUtility.checkFieldErrors(this);
            
            // Get current user id
            long userId = getCurrentUserId();
            // Filter invitations of just the current user
            getCriteria().setOwnedUserId(userId);
            // Search invitations using the input fields and the
            // groupInvitationService, put result into the "invitations" output
            // field
            PagedResult<GroupInvitation> pagedResult = getGroupInvitationService()
                    .search(getCriteria(), 0, getPageSize(), getPage());

            // set the result
            setInvitations(pagedResult);
            Map<String, Object> result = new HashMap<String, Object>();
            result.put("page", getPage());
            result.put("pageSize", getPageSize());
            result.put("total", pagedResult.getTotal());
            List<Map<String, String>> items = new ArrayList<Map<String, String>>();
            result.put("items", items);
            for (GroupInvitation invitation : pagedResult.getValues()) {
                Map<String, String> item = new HashMap<String, String>();
                items.add(item);
                item.put("sentOn", formatDate(invitation.getSentOn()));
                item.put("acceptedOrRejectedOn", invitation.getStatus() == InvitationStatus.REJECTED
                    ? "Rejected" : formatDate(invitation.getAcceptedOrRejectedOn()));
                item.put("approvalProcessedOn", invitation.getStatus() == InvitationStatus.APPROVAL_REJECTED
                    ? "Rejected" : formatDate(invitation.getApprovalProcessedOn()));
                item.put("groupName", invitation.getGroupMember().getGroup().getName());
                item.put("accessRight", (!invitation.getGroupMember().isUseGroupDefault() ? 
                        invitation.getGroupMember().getSpecificPermission() :
                        invitation.getGroupMember().getGroup().getDefaultPermission()) + "");
            }            
            setResult(result);
        } catch (SecurityGroupException e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature,
                    new SecurityGroupsActionException(
                            "error occurs while executing the action", e));
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
        LoggingWrapperUtility.logEntrance(getLogger(), signature, null, null);
        super.validate();
        InvitationSearchCriteria criteria = getCriteria();
        if (criteria.getGroupName() != null) {
            HelperUtility.checkStringLength(this, criteria.getGroupName(), "criteria.groupName");
        }
        HelperUtility.checkDateRange(this, criteria.getAcceptedDateFrom(),
            criteria.getAcceptedDateTo(), "criteria.acceptedDateFrom", "criteria.acceptedDateTo");
        HelperUtility.checkDateRange(this, criteria.getSentDateFrom(),
            criteria.getSentDateTo(), "criteria.sentDateFrom", "criteria.sentDateTo");
        HelperUtility.checkCriteriaPermissions(this, criteria.getPermissions());
        
        LoggingWrapperUtility.logExit(getLogger(), signature, null);
    }

    /**
     * Gets the chained-in accept/reject group invitation action success result.
     * 
     * @return chained-in accept/reject group invitation action success result
     * @since 1.1
     */
    public Map<String, String> getAcceptRejectSuccessResult() {
        return acceptRejectSuccessResult;
    }

    /**
     * Gets the chained-in accept/reject group invitation action result error message.
     * 
     * @return the chained-in accept/reject group invitation action result error message
     * @since 1.1
     */
    public String getAcceptRejectErrorMessage() {
        return acceptRejectErrorMessage;
    }
}
