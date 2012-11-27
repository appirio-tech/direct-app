/*
 * Copyright (C) 2011 - 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.groups;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.security.groups.model.GroupAuditRecord;
import com.topcoder.security.groups.model.GroupMember;
import com.topcoder.security.groups.services.SecurityGroupException;

/**
 * <p>
 * CreateGroupAction is used to perform the create group function. It will log events and errors. It will audit the
 * methods.
 * </p>
 * <b>Configuration</b>
 * <p>
 *
 * <pre>
 *   &lt;bean id=&quot;createGroupAction&quot;
 *   class=&quot;com.topcoder.security.groups.actions.CreateGroupAction&quot;&gt;
 *         &lt;property name=&quot;groupService&quot; ref=&quot;groupService&quot;/&gt;
 *         &lt;property name=&quot;clientService&quot; ref=&quot;clientService&quot;/&gt;
 *         &lt;property name=&quot;customerAdministratorService&quot; ref=&quot;customerAdministratorService&quot;/&gt;
 *         &lt;property name=&quot;directProjectService&quot; ref=&quot;directProjectService&quot;/&gt;
 *         &lt;property name=&quot;groupInvitationService&quot; ref=&quot;groupInvitationService&quot;/&gt;
 *         &lt;property name=&quot;acceptRejectUrlBase&quot; value=&quot;acceptRejectUrlBase&quot;/&gt;
 *         &lt;property name=&quot;registrationUrl&quot; value=&quot;registrationUrl&quot;/&gt;
 *         &lt;property name=&quot;billingAccountService&quot; ref=&quot;billingAccountService&quot;/&gt;
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
 * Version 1.1 (TopCoder Security Groups Frontend - Create Update Group) change notes:
 * <ol>
 *   <li>This class was updated to support ajax calls. Also permission checking was added.</li>
 * </ol>
 * </p>
 *
 * <p>
 * Version 1.2 (Release Assembly - TopCoder Security Groups Frontend - Search Delete Groups) change notes:
 * <ol>
 *   <li>Updated to use helper methods in {@link HelperUtility}.</li>
 * </ol>
 * </p>
 *
 * <p>
 * Version 1.3 (Release Assembly - TopCoder Security Groups Frontend - Miscellaneous) change notes:
 * <ol>
 *   <li>Updated {@link #executeAction()} to add support for skipping invitation email for new member users.</li>
 * </ol>
 * </p>
 *
 * @author woodjhon, hanshuai, flexme, minhu, TCSASSEMBLER
 * @version 1.3
 */
@SuppressWarnings("serial")
public class CreateGroupAction extends CreateUpdateGroupAction {

    /**
     * <p>
     * Represent the class name.
     * </p>
     */
    private static final String CLASS_NAME = CreateGroupAction.class.getName();

    /**
     * <p>
     * Create the instance.
     * </p>
     */
    public CreateGroupAction() {
        // Empty Constructor.
    }

    /**
     * <p>
     * Create group method. It will back the back end service to create the group.
     * </p>
     *
     * @throws SecurityGroupsActionException
     *             if any error is caught during the operation.
     */
    public void executeAction() throws SecurityGroupsActionException {
        final String signature = CLASS_NAME + ".executeAction()";
        LoggingWrapperUtility.logEntrance(getLogger(), signature, null, null);
        
        try {
            if (!HelperUtility.checkPermission(ServletActionContext.getRequest(), getCurrentUserId(), getAuthorizationService(), getGroup(), false, false)) {
                throw new SecurityGroupsActionException("Has no permission to create group");
            }
            validateData();
            
            // audit
            GroupAuditRecord record = new GroupAuditRecord();
            record.setHandle(HelperUtility.getUserName());
            record.setDate(new Date());
            record.setIpAddress(ServletActionContext.getRequest().getRemoteAddr());
            record.setPreviousValue("");
            record.setNewValue(getGroupId() + getGroup().getName());
            getAuditService().add(record);
            
            HelperUtility.fillRelation(getGroup());
            if (isSkipInvitationEmail()) {
                Date now = new Date();
                for (GroupMember member : getGroup().getGroupMembers()) {
                    member.setActive(true);
                    member.setActivatedOn(now);
                }
            }            
            long groupId = getGroupService().add(getGroup());
            HelperUtility.sendInvitations(this.getAuditService(), getGroupInvitationService(), getAcceptRejectUrlBase(),
                getRegistrationUrl(), getGroup().getGroupMembers(), isSkipInvitationEmail());
            
            Map<String, Object> result = new HashMap<String, Object>();
            result.put("groupId", new Long(groupId));
            setResult(result);
        } catch (SecurityGroupException e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature, new SecurityGroupsActionException(
                    e.getMessage(), e));
        } catch (RuntimeException e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature, new SecurityGroupsActionException(
                    e.getMessage(), e));
        }
    }

    /**
     * <p>
     * Performs complex validation that can't be easily done with declarative XML validation.
     * </p>
     *
     * @throws SecurityGroupsActionValidationException
     *             if validation fails.
     */
    public void validateData() {
        final String signature = CLASS_NAME + ".validateData()";
        LoggingWrapperUtility.logEntrance(getLogger(), signature, null, null);
        try {
            HelperUtility.validate(this, getGroup());
            HelperUtility.validate(this, getGroupUserService(), getGroup().getGroupMembers());
            LoggingWrapperUtility.logExit(getLogger(), signature, null);
        } catch (SecurityGroupsActionValidationException e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature, e);
        }
    }
}
