/*
 * Copyright (C) 2011-2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.groups;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.commons.utils.ValidationUtility;
import com.topcoder.security.groups.model.Group;
import com.topcoder.security.groups.services.GroupService;
import com.topcoder.security.groups.services.SecurityGroupException;

/**
 * <p>
 * DeleteGroupAction is used to perform the delete group function. It will log events and errors. It will audit the
 * methods.
 * </p>
 *
 * <b>Configuration</b>
 * <p>
 *
 * <pre>
 *   &lt;bean id="deleteGroupAction" class="com.topcoder.security.groups.actions.DeleteGroupAction">
 *         &lt;property name="groupService" ref="groupService"/>
 *         &lt;property name="logger" ref="logger"/>
 *         &lt;property name="auditService" ref="auditService"/>
 *         &lt;property name="authorizationService" ref="authorizationService"/>
 *
 *         &lt;!-- other properties here -->
 *   &lt;/bean>
 *
 * </pre>
 *
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> It's mutable and not thread safe. However the struts framework will guarantee
 * that it will be used in the thread safe model.
 * </p>
 *
 * <p>
 * Version 1.1 (Release Assembly - TopCoder Security Groups Frontend - Search Delete Groups) change notes:
 * <ol>
 *   <li>Updated to use the same package name as other group actions.</li>
 *   <li>Updated the whole class to support ajax calling.</li>
 *   <li>Added method {@link #validateData()} to validate the parameter(s).</li>
 *   <li>Added validation and permission check in {@link #executeAction()}.</li>
 * </ol>
 * </p>
 *
 * @author woodjhon, hanshuai, TCSASSEMBLER
 * @version 1.2
 */
@SuppressWarnings("serial")
public class DeleteGroupAction extends BaseAction {
    /**
     * <p>
     * Represent the class name.
     * </p>
     */
    private static final String CLASS_NAME = DeleteGroupAction.class.getName();

    /**
     * Purpose: groupId is used to represents the group id. Usage: It's passed as the http input parameter for this
     * action. Legal Values: Positive after set
     */
    private long groupId;

    /**
     * Purpose: groupService is used to represents the group service. Usage: It's injected. Legal Values: Not null
     * after set
     */
    private GroupService groupService;

    /**
     * <p>
     * Create the instance.
     * </p>
     */
    public DeleteGroupAction() {
        // Empty Constructor.
    }

    /**
     * <p>
     * Check that the required(with config stereotype) fields are injected.
     * </p>
     *
     * @throws SecurityGroupsActionConfigurationException
     *             is thrown if any of these fields is null:<br>
     *             auditService, authorizationService, groupService
     */
    public void checkInit() {
        super.checkInit();
        ValidationUtility.checkNotNull(groupService, "groupService",
                SecurityGroupsActionConfigurationException.class);
    }

    /**
     * <p>
     * Deletes the group by id.
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
            // validation 
            validateData();
            HelperUtility.checkFieldErrors(this);

            Group group = groupService.get(groupId);
            if (group == null) {
                throw new SecurityGroupException("The group to delete does not exist.");
            }
            
            // permission check
            if (!HelperUtility.checkPermission(ServletActionContext.getRequest(), getCurrentUserId(),
                getAuthorizationService(), group, false, false)) {
                throw new SecurityGroupsActionException("Has no permission to delete group");
            }            
            
            // audit
            HelperUtility.audit(getAuditService(), getGroupId() + group.getName(), "");

            // delete
            groupService.delete(groupId);
            
            Map<String, Object> result = new HashMap<String, Object>();
            result.put("operation", "delete");
            result.put("groupId", new Long(groupId));
            result.put("result", "success");
            setResult(result);
            
            LoggingWrapperUtility.logExit(getLogger(), signature, null);
        } catch (SecurityGroupException e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature, new SecurityGroupsActionException(
                    "error occurs while deleting the group", e));
        } catch (RuntimeException e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature, new SecurityGroupsActionException(
                    "RuntimeException occurs while deleting the group", e));
        }
    }

    /**
     * Validates the parameter(s).
     *
     * @since 1.1
     */
    public void validateData() {
        final String signature = CLASS_NAME + ".validateData()";
        LoggingWrapperUtility.logEntrance(getLogger(), signature, null, null);
        if (groupId <= 0) {
            addFieldError("groupId", "The groupId should be positive.");
        }
        LoggingWrapperUtility.logExit(getLogger(), signature, null);
    }

    /**
     * <p>
     * Get group id.
     * </p>
     *
     * @return the group id
     */
    public long getGroupId() {
        return groupId;
    }

    /**
     * <p>
     * Set group id.
     * </p>
     *
     * @param groupId
     *            the group id to set.
     */
    public void setGroupId(long groupId) {
        this.groupId = groupId;
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
}
