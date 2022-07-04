/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.groups;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.struts2.ServletActionContext;

import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.commons.utils.ValidationUtility;
import com.topcoder.security.groups.model.Client;
import com.topcoder.security.groups.model.CustomerAdministrator;
import com.topcoder.security.groups.services.SecurityGroupException;
import com.topcoder.security.groups.services.UserService;
import com.topcoder.security.groups.services.dto.UserDTO;

/**
 * <p>
 * This Struts action allows to create customer administrator. This action will
 * be used by JSP corresponding to administrator-create-new-administrator.html
 * from the prototype.
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
 *  &lt;bean id="CreateCustomerAdminAction"
 *     class="com.topcoder.security.groups.actions.CreateCustomerAdminAction"&gt;
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
 *   &lt;action name="createCustomerAdminAction" class="CreateCustomerAdminAction" method="input"&gt;
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
 *   <li>Renamed field userService to {@link #groupUserService}, renamed its setter also to
 *   avoid conflict with base class. Updated its setter and all related places of this field.</li>
 *   <li>Added method {@link #validateData()} to validate the parameters.</li>
 *   <li>Added method {@link #checkPermission()} to check permission.</li>
 *   <li>Added user permission check and data validation in {@link #executeAction()}.</li>
 *   <li>Added method {@link #enterCreateCustomerAdmin()} which is used when
 *   entering the create customer administrator page.<li>
 * </ol>
 * </p>
 *
 * <p>
 * Version 1.2 (Release Assembly - TopCoder Security Groups - Release 4) change notes:
 * <ol>
 *   <li>Fix the typos.</li>
 * </ol>
 * </p>
 * 
 * @author gevak, TCSDEVELOPER, minhu
 * @version 1.2
 */
@SuppressWarnings("serial")
public class CreateCustomerAdminAction extends ClientsPrepopulatingBaseAction {
    /**
     * <p>
     * Represent the class name.
     * </p>
     */
    private static final String CLASS_NAME = CreateCustomerAdminAction.class
            .getName();
    /**
     * ID of client who will become a customer administrator. It is used in
     * execute() method. It is injected via the setter with no validation, thus
     * can be any value. When execute() is called, XML validations are applied
     * on this variable - see CS 1.3.2. Mutable via setter.
     */
    private Long clientId;

    /**
     * Handle of user who will become a customer administrator. It is used in
     * execute() method. It is injected via the setter with no validation, thus
     * can be any value. When execute() is called, XML validations are applied
     * on this variable - see CS 1.3.2. Mutable via setter.
     */
    private String handle;

    /**
     * UserService used to search user by handle. It is used in execute()
     * method. It is injected via the setter with no validation, thus can be any
     * value. However, @PostConstruct method will ensure that it's not null.
     * Mutable via setter.
     * 
     * @since 1.1
     */
    private UserService groupUserService;

    /**
     * Empty default constructor.
     */
    public CreateCustomerAdminAction() {
    }

    /**
     * <p>
     * Enters create customer administrator. It will prepare the data used by the create customer administrator page.
     * </p>
     *
     * @return SUCCESS if no error occurred
     * @throws SecurityGroupsActionException if any error occurred
     * @since 1.1
     */
    public String enterCreateCustomerAdmin() throws SecurityGroupsActionException {
        final String signature = CLASS_NAME + ".enterCreateCustomerAdministrator()";
        LoggingWrapperUtility.logEntrance(getLogger(), signature, null, null);
        try {
            checkPermission();

            input();
            LoggingWrapperUtility.logExit(getLogger(), signature, new Object[] { SUCCESS });
            return SUCCESS;
        } catch (Throwable e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature, new SecurityGroupsActionException(
                    "Error occurs while preparing the create customer administrator page.", e));
        }
    }

    /**
     * Checks the permission for creating administrator.
     *
     * @throws SecurityGroupException if any error occurred in authorization service
     * @throws SecurityGroupsActionException if the permission is denied
     * @since 1.1
     */
    private void checkPermission() throws SecurityGroupException, SecurityGroupsActionException {
        // check permission
        long loginId = getCurrentUserId();
        if (!getAuthorizationService().isAdministrator(loginId) &&
              !getAuthorizationService().isCustomerAdministrator(loginId, 0)) {
            ServletActionContext.getRequest().setAttribute("errorPageMessage",
                "Sorry, you don't have permission to create administrator.");
            throw new SecurityGroupsActionException(
                "Has no permission to create administrator.");
        }
    }

    /**
     * Create customer administrator.
     *
     * @throws SecurityGroupsActionException if any error occurs when performing
     *             the operation.
     *
     */
    public void executeAction() throws SecurityGroupsActionException {
        final String signature = CLASS_NAME + ".execute()";
        LoggingWrapperUtility.logEntrance(getLogger(), signature, null, null);
        try {
            checkPermission();
            
            // validate the fields
            validateData();
            HelperUtility.checkFieldErrors(this);
            
            // check the handle
            UserDTO user = HelperUtility.checkHandle(handle, groupUserService);     
            long userId = user.getUserId();
            handle = user.getHandle();
            
            // Get client
            Client client = getClientService().get(clientId);
            ValidationUtility.checkNotNull(client, "client",
                    SecurityGroupsActionException.class);
            
            // check if the user already added as administrator
            for(CustomerAdministrator admin : 
                getCustomerAdministratorService().getAdministratorsForCustomer(client.getId())) {
                if (admin.getUserId() == userId) {
                    throw new SecurityGroupsActionException("The member " + handle
                        + " was already a customer administrator for " + client.getName() + ".");
                }
            }
            
            // Create and populate customer admin
            CustomerAdministrator ca = new CustomerAdministrator();
            ca.setUserId(userId);
            ca.setClient(client);
            // Persist it
            long customerAdministratorId = getCustomerAdministratorService().add(ca);
            // do the audit.
            HelperUtility.audit(getAuditService(), "", "client ID = "
                    + clientId + " name = " + client.getName()
                    + "; user handle = " + handle);
            
            // prepare the json result
            Map<String, String> result = new HashMap<String, String>();
            result.put("customerAdministratorId", customerAdministratorId + "");
            result.put("handle", handle);
            result.put("result", "success");
            setResult(result);
        } catch (SecurityGroupException e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature,
                    new SecurityGroupsActionException(
                            "error occurs while creating customer admin", e));
        } catch (SecurityGroupsActionException e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature, e);
        }
        LoggingWrapperUtility.logExit(getLogger(), signature, null);
    }

    /**
     * <p>
     * Validates the request field parameters.
     * </p>
     * 
     * @since 1.1
     */
    public void validateData() {
        final String signature = CLASS_NAME + ".validateData()";
        LoggingWrapperUtility.logEntrance(getLogger(), signature, null, null);
        super.validate();
        if (clientId <= 0) {
            addFieldError("clientId", "The clientId should be positive.");
        }
        if (handle == null) {
            addFieldError("handle", "The handle should not be null.");
        }
        HelperUtility.checkStringLength(this, handle, "handle");
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
        ValidationUtility.checkNotNull(groupUserService, "groupUserService",
                SecurityGroupsActionConfigurationException.class);
    }

    /**
     * Sets ID of client who will become a customer administrator.
     *
     * @param clientId ID of client who will become a customer administrator.
     *
     */
    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    /**
     * Sets handle of user who will become a customer administrator.
     *
     * @param handle Handle of user who will become a customer administrator.
     *
     */
    public void setHandle(String handle) {
        this.handle = handle;
    }

    /**
     * Sets UserService used to search user by handle.
     *
     * @param groupUserService UserService used to search user by handle.
     * @since 1.1
     */
    public void setGroupUserService(UserService groupUserService) {
        this.groupUserService = groupUserService;
    }
}
