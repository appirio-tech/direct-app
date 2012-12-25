/*
 * Copyright (C) 2011 - 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.groups;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.commons.utils.ValidationUtility;
import com.topcoder.security.groups.model.BillingAccount;
import com.topcoder.security.groups.model.Client;
import com.topcoder.security.groups.model.Group;
import com.topcoder.security.groups.model.GroupMember;
import com.topcoder.security.groups.services.BillingAccountService;
import com.topcoder.security.groups.services.ClientService;
import com.topcoder.security.groups.services.CustomerAdministratorService;
import com.topcoder.security.groups.services.DirectProjectService;
import com.topcoder.security.groups.services.GroupInvitationService;
import com.topcoder.security.groups.services.GroupMemberService;
import com.topcoder.security.groups.services.GroupService;
import com.topcoder.security.groups.services.SecurityGroupException;
import com.topcoder.security.groups.services.UserService;
import com.topcoder.security.groups.services.dto.ProjectDTO;
import com.topcoder.service.project.ProjectData;

/**
 * <p>
 * CreateUpdateGroupAction is used to provide common fields for the UpdateGroupAction and CreateGroupAction, it
 * also provide common action methods such as enter group details for the update/create group action. It will log
 * events and errors. It will audit the methods.
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
 *   <li>Added field {@link #skipInvitationEmail} and its getter/setter.</li>
 * </ol>
 * </p>
 *
 * <p>
 * Version 1.4 (Release Assembly - TopCoder Security Groups - Release 4) change notes:
 * <ol>
 *   <li>Updated {@link #input()} to sort the clients.</li>
 * </ol>
 * </p>
 * 
 * @author woodjhon, hanshuai, flexme, minhu, TCSASSEMBLER
 * @version 1.4
 */
@SuppressWarnings("serial")
public abstract class CreateUpdateGroupAction extends BaseAction {

    /**
     * <p>
     * Represent the class name.
     * </p>
     */
    private static final String CLASS_NAME = CreateUpdateGroupAction.class.getName();
    
    /**
     * Whether or not skip invitation email for new member users.
     * 
     * @since 1.3
     */
    private boolean skipInvitationEmail;

    /**
     * Purpose: groupId is used to represents the group id. Usage: It's passed as the http input parameter for this
     * action. Legal Values: Positive after set
     */
    private long groupId;

    /**
     * Purpose: selectedClientId is used to represents the selected client id. Usage: It's passed as the http input
     * parameter for this action. Legal Values: Positive after set
     */
    private Long selectedClientId;

    /**
     * Purpose: groupMemberId is used to represents the group member id.Usage: It's passed as the http input
     * parameter for this action. Legal Values: Positive after set
     */
    private long groupMemberId;

    /**
     * Purpose: group is used to represents the group. Usage: It's passed as the http input parameter for this
     * action. Legal Values: Not null after set
     */
    private Group group;

    /**
     * Purpose: groupMember is used to represents the group member. Usage: It's passed as the http input parameter
     * for this action. Legal Values: Not null after set
     */
    private GroupMember groupMember;

    /**
     * Purpose: groupMembers is used to represents the group members. Usage: It's passed as the http input
     * parameter for this action. Legal Values: Not null and not empty after set. The list should not contain null
     * value.
     */
    private List<GroupMember> groupMembers;

    /**
     * Purpose: accounts is used to represents the accounts. Usage: It's set by the action methods, and consumed by
     * the front end page. Legal Values: Not null and not empty after set. The list should not contain null value.
     */
    private List<BillingAccount> accounts;

    /**
     * Purpose: clients is used to represents the clients. Usage: It's set by the action methods, and consumed by
     * the front end page. Legal Values: Not null and not empty after set. The list should not contain null value.
     */
    private List<Client> clients;

    /**
     * Purpose: projects is used to represents the projects. Usage: It's set by the action methods, and consumed by
     * the front end page. Legal Values: Not null and not empty after set. The list should not contain null value.
     */
    private List<ProjectDTO> projects;

    /**
     * Purpose: groupService is used to represents the group service. It's required. Usage: It's injected. Legal
     * Values: Not null after set
     */
    private GroupService groupService;

    /**
     * Purpose: clientService is used to represents the client service. It's required. Usage: It's injected. Legal
     * Values: Not null after set
     */
    private ClientService clientService;

    /**
     * Purpose: customerAdministratorService is used to represents the customer administrator service. It's
     * required. Usage: It's injected. Legal Values: Not null after set
     */
    private CustomerAdministratorService customerAdministratorService;

    /**
     * Purpose:directProjectService is used to represents the direct project service. It's required. Usage: It's
     * injected. Legal Values: Not null after set
     */
    private DirectProjectService directProjectService;

    /**
     * Purpose: groupInvitationService is used to represents the group invitation service. It's required. Usage:
     * It's injected. Legal Values: Not null after set
     */
    private GroupInvitationService groupInvitationService;

    /**
     * Purpose: acceptRejectUrlBase is used to represents the accept reject url base. It's required. Usage: It's
     * injected. Legal Values: Not null and not empty after set.
     */
    private String acceptRejectUrlBase;

    /**
     * Purpose: registrationUrl is used to represents the registration url. It's required. Usage: It's injected.
     * Legal Values: Not null and not empty after set.
     */
    private String registrationUrl;

    /**
     * Purpose: billingAccountService is used to represents the billing account service. It's required. Usage: It's
     * injected. Legal Values: Not null after set
     */
    private BillingAccountService billingAccountService;

    /**
     * Purpose: groupMemberService is used to represents the group member service. Usage: It's injected. Legal
     * Values: Not null after set
     */
    private GroupMemberService groupMemberService;

    /**
     * Purpose: userService is used to represents the user service. It's required. Usage: It's injected. Legal
     * Values: Not null after set
     */
    private UserService groupUserService;
    
    /**
     * <p>
     * Create the instance.
     * </p>
     */
    protected CreateUpdateGroupAction() {
        // Empty Constructor.
    }

    /**
     * <p>
     * Check that the required fields are injected.
     * </p>
     *
     * @throws SecurityGroupsActionConfigurationException
     *             is thrown if one of the following conditions is true:<br>
     *             <li>any of these fields is null:<br>
     *             auditService, authorizationService, clientService, billingAccountService, directProjectService,
     *             groupInvitationService, groupService, customerAdministratorService, groupMemberService<br> </li>
     *             any of these fields is null or empty after trimming:<br>
     *             registrationUrl, acceptRejectUrlBase
     *
     */
    public void checkInit() {
        super.checkInit();
        ValidationUtility.checkNotNull(clientService, "clientService",
                SecurityGroupsActionConfigurationException.class);
        ValidationUtility.checkNotNull(billingAccountService, "billingAccountService",
                SecurityGroupsActionConfigurationException.class);
        ValidationUtility.checkNotNull(directProjectService, "directProjectService",
                SecurityGroupsActionConfigurationException.class);
        ValidationUtility.checkNotNull(groupInvitationService, "groupInvitationService",
                SecurityGroupsActionConfigurationException.class);
        ValidationUtility.checkNotNull(groupService, "groupService",
                SecurityGroupsActionConfigurationException.class);
        ValidationUtility.checkNotNull(customerAdministratorService, "customerAdministratorService",
                SecurityGroupsActionConfigurationException.class);
        ValidationUtility.checkNotNullNorEmptyAfterTrimming(registrationUrl, "registrationUrl",
                SecurityGroupsActionConfigurationException.class);
        ValidationUtility.checkNotNullNorEmptyAfterTrimming(acceptRejectUrlBase, "acceptRejectUrlBase",
                SecurityGroupsActionConfigurationException.class);
        ValidationUtility.checkNotNull(groupMemberService, "groupMemberService",
                SecurityGroupsActionConfigurationException.class);
        ValidationUtility.checkNotNull(groupUserService, "groupUserService",
                SecurityGroupsActionConfigurationException.class);
    }

    /**
     * <p>
     * Input method. Process the user input.
     * </p>
     *
     * @throws SecurityGroupsActionException
     *             if any error is caught during the operation.
     * @return The input result
     */
    public String input() throws SecurityGroupsActionException {
        final String signature = CLASS_NAME + ".input()";
        LoggingWrapperUtility.logEntrance(getLogger(), signature, null, null);
        try {
            // get current user id
            long userId = getCurrentUserId();
            // fill clients for "Customer Name" field
            if (getAuthorizationService().isAdministrator(userId)) {
                clients = clientService.getAllClients();
            } else {
                clients = customerAdministratorService.getCustomersForAdministrator(userId);
            }
            Collections.sort(clients, new Comparator<Client>() {
                public int compare(Client client1, Client client2) {
                    return client1.getName().compareTo(client2.getName());
                }
            });
            
            if (!HelperUtility.checkPermission(ServletActionContext.getRequest(), getCurrentUserId(), getAuthorizationService(), getGroup(), clients.size() > 0, false)) {
                throw new SecurityGroupsActionException("Can't view this group");
            }
            
            // fill projects for "Projects" field, fill accounts for Billing Accounts field.
            if ((selectedClientId == null || selectedClientId == 0) && clients.size() > 0) {
                selectedClientId = clients.get(0).getId();
            }
            if (selectedClientId != null) {
                projects = directProjectService.getProjectsByClientId(selectedClientId);
                accounts = billingAccountService.getBillingAccountsForClient(selectedClientId);
            }

            LoggingWrapperUtility.logExit(getLogger(), signature, new Object[] {INPUT });
            return INPUT;
        } catch (SecurityGroupException e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature, new SecurityGroupsActionException(
                    "error occurs while processing the user input", e));
        } catch (RuntimeException e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature, new SecurityGroupsActionException(
                    "RuntimeException occurs while processing the user input", e));
        }
    }
    
    /**
     * <p>
     * Gets the projects and billing accounts of the selected client.
     * </p>
     *
     * @throws SecurityGroupsActionException
     *             if any error is caught during the operation.
     * @return The result
     */
    public String getProjectsAndAccounts() throws SecurityGroupsActionException {
        final String signature = CLASS_NAME + ".getProjectsAndAccounts()";
        LoggingWrapperUtility.logEntrance(getLogger(), signature, null, null);
        try {
            projects = directProjectService.getProjectsByClientId(selectedClientId);
            accounts = billingAccountService.getBillingAccountsForClient(selectedClientId);
            
            Map<String, Object> result = new HashMap<String, Object>();
            List<ProjectData> dtoAccounts = new ArrayList<ProjectData>();
            for (BillingAccount ba : accounts) {
                ProjectData data = new ProjectData();
                data.setProjectId(ba.getId());
                data.setName(ba.getName());
                dtoAccounts.add(data);
            }
            result.put("projects", projects);
            result.put("accounts", dtoAccounts);
            setResult(result);
            
            LoggingWrapperUtility.logExit(getLogger(), signature, new Object[] {SUCCESS });;
            return SUCCESS;
        } catch (SecurityGroupException e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature, new SecurityGroupsActionException(
                    "error occurs while getting the projects and accounts for a client", e));
        } catch (RuntimeException e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature, new SecurityGroupsActionException(
                    "RuntimeException occurs while getting the projects and accounts for a client", e));
        }
    }

    /**
     * <p>
     * Enter group details method. It will put the group in the session.
     * </p>
     *
     * @throws SecurityGroupsActionException
     *             if any error is caught during the operation.
     * @return The execution result
     */
    public String enterGroupDetails() throws SecurityGroupsActionException {
        final String signature = CLASS_NAME + ".enterGroupDetails()";
        LoggingWrapperUtility.logEntrance(getLogger(), signature, null, null);
        try {
            input();
            LoggingWrapperUtility.logExit(getLogger(), signature, new Object[] {SUCCESS });
            return SUCCESS;
        } catch (RuntimeException e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature, new SecurityGroupsActionException(
                    "RuntimeException occurs while adding member to the group", e));
        }
    }

    /**
     * <p>
     * Get group.
     * </p>
     *
     * @return the group
     */
    public Group getGroup() {
        return group;
    }

    /**
     * <p>
     * Set group.
     * </p>
     *
     * @param group
     *            the group to set.
     */
    public void setGroup(Group group) {
        this.group = group;
    }

    /**
     * <p>
     * Get group member id.
     * </p>
     *
     * @return the group member id
     */
    public long getGroupMemberId() {
        return groupMemberId;
    }

    /**
     * <p>
     * Set group member id.
     * </p>
     *
     * @param groupMemberId
     *            the group member id to set.
     */
    public void setGroupMemberId(long groupMemberId) {
        this.groupMemberId = groupMemberId;
    }

    /**
     * <p>
     * Get group member.
     * </p>
     *
     * @return the group member
     */
    public GroupMember getGroupMember() {
        return groupMember;
    }

    /**
     * <p>
     * Set group member.
     * </p>
     *
     * @param groupMember
     *            the group member to set.
     */
    public void setGroupMember(GroupMember groupMember) {
        this.groupMember = groupMember;
    }

    /**
     * <p>
     * Get group members.
     * </p>
     *
     * @return the group members
     */
    public List<GroupMember> getGroupMembers() {
        return groupMembers;
    }

    /**
     * <p>
     * Set group members.
     * </p>
     *
     * @param groupMembers
     *            the group members to set.
     */
    public void setGroupMembers(List<GroupMember> groupMembers) {
        this.groupMembers = groupMembers;
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

    /**
     * <p>
     * Get clients.
     * </p>
     *
     * @return the clients
     */
    public List<Client> getClients() {
        return clients;
    }

    /**
     * <p>
     * Set clients.
     * </p>
     *
     * @param clients
     *            the clients to set.
     */
    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    /**
     * <p>
     * Get selected client id.
     * </p>
     *
     * @return the selected client id
     */
    public Long getSelectedClientId() {
        return selectedClientId;
    }

    /**
     * <p>
     * Set selected client id.
     * </p>
     *
     * @param selectedClientId
     *            the selected client id to set.
     */
    public void setSelectedClientId(Long selectedClientId) {
        this.selectedClientId = selectedClientId;
    }

    /**
     * <p>
     * Get projects.
     * </p>
     *
     * @return the projects
     */
    public List<ProjectDTO> getDirectProjects() {
        return projects;
    }

    /**
     * <p>
     * Set projects.
     * </p>
     *
     * @param projects
     *            the projects to set.
     */
    public void setDirectProjects(List<ProjectDTO> projects) {
        this.projects = projects;
    }

    /**
     * <p>
     * Get client service.
     * </p>
     *
     * @return the client service
     */
    public ClientService getClientService() {
        return clientService;
    }

    /**
     * <p>
     * Set client service.
     * </p>
     *
     * @param clientService
     *            the client service to set.
     */
    public void setClientService(ClientService clientService) {
        this.clientService = clientService;
    }

    /**
     * <p>
     * Get customer administrator service.
     * </p>
     *
     * @return the customer administrator service
     */
    public CustomerAdministratorService getCustomerAdministratorService() {
        return customerAdministratorService;
    }

    /**
     * <p>
     * Set customer administrator service.
     * </p>
     *
     * @param customerAdministratorService
     *            the customer administrator service to set.
     */
    public void setCustomerAdministratorService(CustomerAdministratorService customerAdministratorService) {
        this.customerAdministratorService = customerAdministratorService;
    }

    /**
     * <p>
     * Get direct project service.
     * </p>
     *
     * @return the direct project service
     */
    public DirectProjectService getDirectProjectService() {
        return directProjectService;
    }

    /**
     * <p>
     * Set direct project service.
     * </p>
     *
     * @param directProjectService
     *            the direct project service to set.
     */
    public void setDirectProjectService(DirectProjectService directProjectService) {
        this.directProjectService = directProjectService;
    }

    /**
     * <p>
     * Get group invitation service.
     * </p>
     *
     * @return the group invitation service
     */
    public GroupInvitationService getGroupInvitationService() {
        return groupInvitationService;
    }

    /**
     * <p>
     * Set group invitation service.
     * </p>
     *
     * @param groupInvitationService
     *            the group invitation service to set.
     */
    public void setGroupInvitationService(GroupInvitationService groupInvitationService) {
        this.groupInvitationService = groupInvitationService;
    }

    /**
     * <p>
     * Get accept reject url base.
     * </p>
     *
     * @return the accept reject url base
     */
    public String getAcceptRejectUrlBase() {
        return acceptRejectUrlBase;
    }

    /**
     * <p>
     * Set accept reject url base.
     * </p>
     *
     * @param acceptRejectUrlBase
     *            the accept reject url base to set.
     */
    public void setAcceptRejectUrlBase(String acceptRejectUrlBase) {
        this.acceptRejectUrlBase = acceptRejectUrlBase;
    }

    /**
     * <p>
     * Get registration url.
     * </p>
     *
     * @return the registration url
     */
    public String getRegistrationUrl() {
        return registrationUrl;
    }

    /**
     * <p>
     * Set registration url.
     * </p>
     *
     * @param registrationUrl
     *            the registration url to set.
     */
    public void setRegistrationUrl(String registrationUrl) {
        this.registrationUrl = registrationUrl;
    }

    /**
     * <p>
     * Get accounts.
     * </p>
     *
     * @return the accounts
     */
    public List<BillingAccount> getAccounts() {
        return accounts;
    }

    /**
     * <p>
     * Set accounts.
     * </p>
     *
     * @param accounts
     *            the accounts to set.
     */
    public void setAccounts(List<BillingAccount> accounts) {
        this.accounts = accounts;
    }

    /**
     * <p>
     * Get billing account service.
     * </p>
     *
     * @return the billing account service
     */
    public BillingAccountService getBillingAccountService() {
        return billingAccountService;
    }

    /**
     * <p>
     * Set billing account service.
     * </p>
     *
     * @param billingAccountService
     *            the billing account service to set.
     */
    public void setBillingAccountService(BillingAccountService billingAccountService) {
        this.billingAccountService = billingAccountService;
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
     * Get group Member service.
     * </p>
     *
     * @return the group member service
     */
    public GroupMemberService getGroupMemberService() {
        return groupMemberService;
    }

    /**
     * <p>
     * Set group Member service.
     * </p>
     *
     * @param groupMemberService
     *            the group member service to set.
     */
    public void setGroupMemberService(GroupMemberService groupMemberService) {
        this.groupMemberService = groupMemberService;
    }
    
    /**
     * <p>
     * Get user service.
     * </p>
     *
     * @return the user service
     */
    public UserService getGroupUserService() {
        return groupUserService;
    }

    /**
     * <p>
     * Set user service.
     * </p>
     *
     * @param groupUserService
     *            the user service to set.
     */
    public void setGroupUserService(UserService groupUserService) {
        this.groupUserService = groupUserService;
    }

    /**
     * Sets the flag that indicates whether or not skip invitation email for new member users.
     *
     * @param skipInvitationEmail the flag that indicates whether or not skip invitation email for new member users
     * @since 1.3
     */
    public void setSkipInvitationEmail(boolean skipInvitationEmail) {
        this.skipInvitationEmail = skipInvitationEmail;
    }

    /**
     * Gets the flag that indicates whether or not skip invitation email for new member users.
     *
     * @return the flag that indicates whether or not skip invitation email for new member users
     * @since 1.3
     */
    public boolean isSkipInvitationEmail() {
        return skipInvitationEmail;
    }
}
