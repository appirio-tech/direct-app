/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.groups;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.commons.utils.ValidationUtility;
import com.topcoder.security.groups.model.Client;
import com.topcoder.security.groups.services.ClientService;
import com.topcoder.security.groups.services.CustomerAdministratorService;
import com.topcoder.security.groups.services.SecurityGroupException;

/**
 * <p>
 * This is a base class for Struts actions, which need to expose list of clients
 * to JSP before execution (JSP will need this list to populate dropdown list in
 * the input form). This class inherits from BaseAction and additionally
 * contains fields (with proper setters and getters) and input() method,
 * required to address clients list prepopulating.
 * </p>
 * <p>
 * <b>Thread Safety:</b>This class is technically mutable and not thread-safe,
 * however it's expected to be used as Spring bean and thus it will be immutable
 * after initialization (as well as base class), so it's thread-safe.
 * </p>
 * <p>
 * <b>Sample Configuration:</b> The spring configuration is below:
 *
 * <pre>
 *  &lt;bean id="ClientsPrepopulatingBaseAction"
 *     class="com.topcoder.security.groups.actions.ClientsPrepopulatingBaseAction"&gt;
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
 *   &lt;action name="cientsPrepopulatingBaseAction" class="ClientsPrepopulatingBaseAction" method="input"&gt;
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
 * </ol>
 * </p>
 *
 * @author gevak, TCSDEVELOPER, TCSASSEMBLER
 * @version 1.1
 */
@SuppressWarnings("serial")
public abstract class ClientsPrepopulatingBaseAction extends BaseAction {
    /**
     * Represent the class name.
     *
     */
    private static final String CLASS_NAME = ClientsPrepopulatingBaseAction.class
            .getName();
    /**
     * Clients which can be administrated by current user. It's expected to be
     * used by JSP to render clients dropdown list. Initially null, but will be
     * populated by input() method with non-null list of non-null elements. Has
     * public getter and protected setter.
     */
    private List<Client> clients;

    /**
     * ClientService used to manage clients. It is used in input() method. It is
     * injected via the setter with no validation, thus can be any value.
     * However, @PostConstruct method will ensure that it's not null. Mutable
     * via setter, exposed to subclasses via protected getter.
     */
    private ClientService clientService;

    /**
     * CustomerAdministratorService used to manager customer administrators. It
     * is used in input() method. It is injected via the setter with no
     * validation, thus can be any value. However, @PostConstruct method will
     * ensure that it's not null. Mutable via setter, exposed to subclasses via
     * protected getter.
     */
    private CustomerAdministratorService customerAdministratorService;

    /**
     * Empty default constructor.
     */
    protected ClientsPrepopulatingBaseAction() {
    }

    /**
     * Populates "clients" parameter, which will be used by JSP to render
     * clients dropdown list.
     *
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
            ValidationUtility.checkNotNull(allClients, "allClients",
                    SecurityGroupsActionException.class);
            ValidationUtility.checkNotNullElements(allClients, "allClients",
                    SecurityGroupsActionException.class);
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
                            "error occurs while prepopulating clients", e));
        } catch (SecurityGroupsActionException e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature, e);
        }
        LoggingWrapperUtility.logExit(getLogger(), signature,
                new Object[] { INPUT });
        return INPUT;
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
        ValidationUtility.checkNotNull(clientService, "clientService",
                SecurityGroupsActionConfigurationException.class);
        ValidationUtility.checkNotNull(customerAdministratorService,
                "customerAdministratorService",
                SecurityGroupsActionConfigurationException.class);
    }

    /**
     * Gets clients which can be administrated by current user. It's expected to
     * be used by JSP to render clients dropdown list.
     *
     * @return Clients which can be administrated by current user. It's expected
     *         to be used by JSP to render clients dropdown list.
     *
     */
    public List<Client> getClients() {
        return clients;
    }

    /**
     * Sets clients which can be administrated by current user. It's expected to
     * be used by JSP to render clients dropdown list.
     *
     * @param clients Clients which can be administrated by current user. It's
     *            expected to be used by JSP to render clients dropdown list.
     *
     */
    protected void setClients(List<Client> clients) {
        this.clients = clients;
    }

    /**
     * Gets ClientService used to manage clients.
     *
     * @return ClientService used to manage clients.
     *
     */
    protected ClientService getClientService() {
        return clientService;
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
     * Gets CustomerAdministratorService used to manager customer
     * administrators.
     *
     * @return CustomerAdministratorService used to manager customer
     *         administrators.
     *
     */
    protected CustomerAdministratorService getCustomerAdministratorService() {
        return customerAdministratorService;
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
