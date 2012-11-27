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
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;

import org.apache.struts2.ServletActionContext;

import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.commons.utils.ValidationUtility;
import com.topcoder.security.groups.model.Client;
import com.topcoder.security.groups.model.GroupInvitation;
import com.topcoder.security.groups.model.InvitationStatus;
import com.topcoder.security.groups.services.ClientService;
import com.topcoder.security.groups.services.CustomerAdministratorService;
import com.topcoder.security.groups.services.SecurityGroupException;
import com.topcoder.security.groups.services.UserService;
import com.topcoder.security.groups.services.dto.InvitationSearchCriteria;
import com.topcoder.security.groups.services.dto.PagedResult;
import com.topcoder.security.groups.services.dto.UserDTO;

/**
 * <p>
 * This Struts action allows admin to view pending approval user invitations.
 * This action will be used by JSP corresponding to
 * administrator-view-pending-approval.html from the prototype.
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
 *   class="com.topcoder.security.groups.actions.GroupInvitationSearchBaseAction"&gt;
 *   &lt;property name="logger" ref="logger"/&gt;
 * &lt;/bean&gt;
 * &lt;bean id="ViewPendingApprovalUserAction" parent="baseAction"
 *   class="com.topcoder.security.groups.actions.ViewPendingApprovalUserAction"&gt;
 *   &lt;property name="logger" ref="logger"/&gt;
 *   &lt;property name="userService" ref="userService"/&gt;
 *   &lt;property name="clientService" ref="clientService"/&gt;
 *   &lt;property name="customerAdministratorService" ref="customerAdministratorService"/&gt;
 * &lt;/bean&gt;
 * </pre>
 *
 * Struts configuration:
 *
 * <pre>
 * &lt;action name="viewPendingApprovalUserAction" class="ViewPendingApprovalUserAction"
 *    method="execute"&gt;
 *    &lt;result name="success"&gt;success.jsp&lt;/result&gt;
 *    &lt;result name="input"&gt;view_pending_approval_user.jsp&lt;/result&gt;
 * &lt;/action&gt;
 * </pre>
 *
 * </p>
 *
 * <p>
 * Version 1.1 (Release Assembly - TopCoder Security Groups Frontend - Invitations Approvals) change notes:
 * <ol>
 *   <li>Updated to use the same package name as other group actions.</li>
 *   <li>Renamed field userService to {@link #groupUserService}, renamed its setter also to
 *   avoid conflict with base class.</li>
 *   <li>Updated the whole class to support ajax calling.</li>
 * </ol>
 * </p>
 *
 * <p>
 * Version 1.2 (Release Assembly - TopCoder Security Groups Frontend - Miscellaneous) change notes:
 * <ol>
 *   <li>Removed field MAX_LENGTH, method validateLength(String, String) and refactored to use
 *   {@link HelperUtility#checkStringLength(ActionSupport, String, String)} to check the string length.</li>
 *   <li>Updated {@link #validateData()} to use {@link HelperUtility#checkDateRange(ActionSupport, Date, Date,
 *   String, String)} to check date range fields.</li>
 * </ol>
 * </p>
 *
 * @author gevak, TCSDEVELOPER, TCSASSEMBLER
 * @version 1.2
 */
@SuppressWarnings("serial")
public class ViewPendingApprovalUserAction extends
        GroupInvitationSearchBaseAction {
    /**
     * Represent the class name.
     */
    private static final String CLASS_NAME = ViewPendingApprovalUserAction.class
            .getName();

    /**
     * The email pattern used to validate email.
     */
    private static final Pattern EMAIL_PATTERN = Pattern
            .compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");

    /**
     * ID of the client, used for searching. It's used in execute() method. It
     * is injected via the setter with no validation, thus can be any value.
     * When execute() is called, XML validations are applied on this variable.
     * Mutable via public setter.
     */
    private Long clientId;

    /**
     * User data for users assigned to the found invitations. Initially null,
     * but populated from execute() method with non-null list containing no null
     * elements. Has public getter.
     */
    private List<UserDTO> users;

    /**
     * Clients which can be administrated by current user. It will be used by
     * JSP to render clients dropdown list. Initially null, but will be
     * populated by input() method with non-null list of non-null elements. Has
     * public getter.
     */
    private List<Client> clients;

    /**
     * UserService used to get user data for users assigned with found
     * invitations. That user data will be needed by JSP to render all the
     * necessary information. It is used in execute() method. It is injected via
     * the setter with no validation, thus can be any value. However,
     * <code>checkInit</code> method will ensure that it's not null. Mutable via
     * setter.
     * 
     * @since 1.1
     */
    private UserService groupUserService;

    /**
     * ClientService used to manage clients. It is used in input() method. It is
     * injected via the setter with no validation, thus can be any value.
     * However, <code>checkInit</code> method will ensure that it's not null.
     * Mutable via setter.
     */
    private ClientService clientService;

    /**
     * CustomerAdministratorService used to manager customer administrators. It
     * is used in input() method. It is injected via the setter with no
     * validation, thus can be any value. However, <code>checkInit</code> method
     * will ensure that it's not null. Mutable via setter.
     */
    private CustomerAdministratorService customerAdministratorService;

    /**
     * Empty default constructor.
     */
    public ViewPendingApprovalUserAction() {
    }

    /**
     * Populates "clients" parameter, which will be used by JSP to render
     * clients dropdown list.
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
            // Search all clients using the clientService, result is put to the
            // clients output field
            List<Client> allClients = clientService.getAllClients();
            // Get current user id
            long userId = getCurrentUserId();
            // For non-admin, filter by user role
            if (!getAuthorizationService().isAdministrator(userId)) {
                List<Client> possibleClients = customerAdministratorService
                        .getCustomersForAdministrator(userId);
                ValidationUtility.checkNotNull(possibleClients,
                        "possibleClients", SecurityGroupsActionException.class);
                ValidationUtility.checkNotNullElements(possibleClients,
                        "possibleClients", SecurityGroupsActionException.class);
                Set<Long> possibleClientIds = new HashSet<Long>();
                for (Client client : possibleClients) {
                    possibleClientIds.add(client.getId());
                }
                clients = new ArrayList<Client>();
                for (Client client : allClients) {
                    if (possibleClientIds.contains(client.getId())) {
                        clients.add(client);
                    }
                }
            } else {
                clients = allClients;
            }
        } catch (SecurityGroupException e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature,
                    new SecurityGroupsActionException(
                            "error occurs while executing the action", e));
        } catch (SecurityGroupsActionException e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature, e);
        }
        LoggingWrapperUtility.logExit(getLogger(), signature,
                new Object[] {INPUT });
        return INPUT;
    }

    /**
     * Searches for pending approval user invitations, supporting pagination.
     *
     * @return SUCCESS to indicate that the operation was successful.
     * @throws SecurityGroupsActionException if any error occurs when performing
     *             the operation.
     * @since 1.1
     */
    @Override
    protected void executeAction() throws Exception {
        final String signature = CLASS_NAME + ".executeAction()";
        LoggingWrapperUtility.logEntrance(getLogger(), signature,
                new String[] {"clientId" }, new Object[] {clientId });
        try {
            // check permission
            if (!getAuthorizationService().checkApprovalPermission(getCurrentUserId())) {
                ServletActionContext.getRequest().setAttribute("errorPageMessage",
                    "Sorry, you don't have permission to access the pending approval page.");
                throw new SecurityGroupsActionException(
                    "Has no permission to access the pending approval page.");
            }
            // validate the fields
            validateData();
            HelperUtility.checkFieldErrors(this);
            
            if (clients == null) {
                input();
            }

            // Add "pending approval" condition to the criteria
            getCriteria().setStatus(InvitationStatus.APPROVAL_PENDING);            
            
            // Get current user id
            long userId = getCurrentUserId();
            // Populate master user ID to criteria
            getCriteria().setMasterUserId(userId);
            // Search invitations
            PagedResult<GroupInvitation> pagedResult = getGroupInvitationService()
                    .search(getCriteria(), clientId == null ? 0 : clientId, getPageSize(), getPage());
            setInvitations(pagedResult);
            // Get user for each invitation, since JSP page needs to render user
            // data
            users = new ArrayList<UserDTO>();
            for (GroupInvitation invitation : pagedResult.getValues()) {
                ValidationUtility.checkNotNull(invitation.getGroupMember(),
                        "invitation.groupMember",
                        SecurityGroupsActionException.class);
                UserDTO userDTO = groupUserService.get(invitation.getGroupMember()
                        .getUserId());
                ValidationUtility.checkNotNull(userDTO, "userDTO",
                        SecurityGroupsActionException.class);
                users.add(userDTO);
            }
            
            Map<String, Object> result = new HashMap<String, Object>();
            result.put("page", getPage());
            result.put("pageSize", getPageSize());
            result.put("total", pagedResult.getTotal());
            List<Map<String, String>> items = new ArrayList<Map<String, String>>();
            result.put("items", items);
            int idx = 0;
            for (GroupInvitation invitation : pagedResult.getValues()) {
                Map<String, String> item = new HashMap<String, String>();
                items.add(item);
                item.put("invitationId", invitation.getId() + "");
                item.put("handle", users.get(idx).getHandle());
                item.put("email", users.get(idx).getEmailAddress());
                item.put("requestDate", formatDate(invitation.getSentOn()));
                item.put("customerName", invitation.getGroupMember().getGroup().getClient().getName());
                ++idx;
            }            
            setResult(result);
        } catch (SecurityGroupException e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature,
                    new SecurityGroupsActionException(
                            "error occurs while executing the action", e));
        } catch (SecurityGroupsActionException e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature, e);
        }
        LoggingWrapperUtility.logExit(getLogger(), signature,
            getOutputParametersMessage(new String[] {"users", "clients" },
                new Object[] {users, clients}));
    }

    /**
     * <p>
     * Retrieves the log message for the given output parameters. It's assumed
     * that paramNames and paramValues contain the same number of elements.
     * </p>
     *
     * @param paramValues the values of output parameters (not null).
     * @param paramNames the names of output parameters (not null).
     *
     * @return the constructed log message.
     */
    private Object[] getOutputParametersMessage(String[] paramNames,
            Object[] paramValues) {
        StringBuilder sb = new StringBuilder("Output parameters [");
        int paramNamesLen = paramNames.length;
        for (int i = 0; i < paramNamesLen; i++) {
            if (i != 0) {
                // Append a comma
                sb.append(", ");
            }
            sb.append(paramNames[i]).append(":").append(paramValues[i]);
        }
        sb.append("]");
        return new Object[] {sb.toString() };
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
        // validate handle.
        String handle = criteria.getInviteeHandle();
        if (handle != null) {
            HelperUtility.checkStringLength(this, handle, "criteria.inviteeHandle");
        }
        // validate email.
        String email = criteria.getInviteeEmail();
        if (email != null) {
            HelperUtility.checkStringLength(this, email, "criteria.inviteeEmail");
            if (!EMAIL_PATTERN.matcher(email).matches()) {
                addFieldError("criteria.inviteeEmail",
                        "criteria.inviteeEmail is invalid.");
            }

        }
        HelperUtility.checkDateRange(this, criteria.getSentDateFrom(),
            criteria.getSentDateTo(), "criteria.sentDateFrom", "criteria.sentDateTo");
        
        LoggingWrapperUtility.logExit(getLogger(), signature, null);
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
        ValidationUtility.checkNotNull(groupUserService, "groupUserService",
                SecurityGroupsActionConfigurationException.class);
        ValidationUtility.checkNotNull(clientService, "clientService",
                SecurityGroupsActionConfigurationException.class);
        ValidationUtility.checkNotNull(customerAdministratorService,
                "customerAdministratorService",
                SecurityGroupsActionConfigurationException.class);
    }

    /**
     * Sets ID of the client, used for searching.
     *
     * @param clientId ID of the client, used for searching.
     *
     */
    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    /**
     * Gets user data for users assigned to the found invitations.
     *
     * @return User data for users assigned to the found invitations.
     *
     */
    public List<UserDTO> getUsers() {
        return users;
    }

    /**
     * Gets clients which can be administrated by current user. It will be used
     * by JSP to render clients dropdown list.
     *
     * @return Clients which can be administrated by current user. It will be
     *         used by JSP to render clients dropdown list.
     *
     */
    public List<Client> getClients() {
        return clients;
    }

    /**
     * Sets UserService used to get user data for users assigned with found
     * invitations.
     *
     * @param groupUserService UserService used to get user data for users assigned
     *            with found invitations.
     * @since 1.1
     *
     */
    public void setGroupUserService(UserService groupUserService) {
        this.groupUserService = groupUserService;
    }

    /**
     * Sets ClientService used to manage clients.
     *
     * @param clientService ClientService used to manage clients.
     *
     */
    public void setClientService(ClientService clientService) {
        this.clientService = clientService;
    }

    /**
     * Sets CustomerAdministratorService used to manager customer
     * administrators.
     *
     * @param customerAdministratorService CustomerAdministratorService used to
     *            manager customer administrators.
     *
     */
    public void setCustomerAdministratorService(
            CustomerAdministratorService customerAdministratorService) {
        this.customerAdministratorService = customerAdministratorService;
    }
}
