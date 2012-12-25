/*
 * Copyright (C) 2011 - 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.groups;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.struts2.ServletActionContext;

import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.security.groups.model.BillingAccount;
import com.topcoder.security.groups.model.DirectProject;
import com.topcoder.security.groups.model.Group;
import com.topcoder.security.groups.model.GroupAuditRecord;
import com.topcoder.security.groups.model.GroupInvitation;
import com.topcoder.security.groups.model.GroupMember;
import com.topcoder.security.groups.model.ResourceType;
import com.topcoder.security.groups.services.GroupInvitationService;
import com.topcoder.security.groups.services.SecurityGroupException;

/**
 * <p>
 * UpdateGroupAction is used to perform the update group function. It will log events and errors. It will audit the
 * methods.
 * </p>
 * <b>Configuration</b>
 * <p>
 *
 * <pre>
 *   &lt;bean id=&quot;updateGroupAction&quot;
 *         class=&quot;com.topcoder.security.groups.actions.UpdateGroupAction&quot;&gt;
 *         &lt;property name=&quot;groupService&quot; ref=&quot;groupService&quot;/&gt;
 *         &lt;property name=&quot;groupMemberService&quot; ref=&quot;groupMemberService&quot;/&gt;
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
 *   <li>Updated {@link #updateGroupMembers(Group, List, Set)}, {@link #getNewAddedMembers(List, Set)} to use userId
 *   set to process new members.</li>
 * </ol>
 * </p>
 *
 * @author woodjhon, hanshuai, flexme, minhu, TCSASSEMBLER
 * @version 1.3
 */
@SuppressWarnings("serial")
public class UpdateGroupAction extends CreateUpdateGroupAction {
    /**
     * <p>
     * Represent the class name.
     * </p>
     */
    private static final String CLASS_NAME = UpdateGroupAction.class.getName();

    /**
     * Represents the mapping from the group member to the group member invitation.
     */
    private Map<Long, GroupInvitation> groupInvitations;

    /**
     * <p>
     * Create the instance.
     * </p>
     */
    public UpdateGroupAction() {
        // Empty Constructor.
    }

    /**
     * <p>
     * Update group method. It will call the back end service to update the group.
     * </p>
     *
     * @throws SecurityGroupsActionException
     *             if any error is caught during the operation.
     * @return The execution result
     */
    public void executeAction() throws SecurityGroupsActionException {
        final String signature = CLASS_NAME + ".executeAction()";
        LoggingWrapperUtility.logEntrance(getLogger(), signature, null, null);
        
        try {
            if (!HelperUtility.checkPermission(ServletActionContext.getRequest(), getCurrentUserId(),
                getAuthorizationService(), getGroup(), false, false)) {
                throw new SecurityGroupsActionException("Has no permission to update group");
            }
            validateData();
            
            // Updates group
            Group group = getGroupService().get(getGroupId());
            Group newGroup = getGroup();
            group.setName(newGroup.getName());
            group.setClient(newGroup.getClient());
            group.setDefaultPermission(newGroup.getDefaultPermission());
            group.setRestrictions(newGroup.getRestrictions() != null ? 
                newGroup.getRestrictions() : new ArrayList<ResourceType>());
            
            // audit
            GroupAuditRecord record = new GroupAuditRecord();
            record.setHandle(HelperUtility.getUserName());
            record.setPreviousValue(group.getId() + group.getName());
            record.setNewValue(getGroupId() + getGroup().getName());
            record.setDate(new Date());
            record.setIpAddress(ServletActionContext.getRequest().getRemoteAddr());
            getAuditService().add(record);
            
            updateProjectsAndBillings(group, newGroup);
            Set<Long> newMemberUserIds = new HashSet<Long>();
            updateGroupMembers(group, newGroup.getGroupMembers(), newMemberUserIds);
            HelperUtility.fillRelation(group);
            List<GroupMember> newMembers = getNewAddedMembers(group.getGroupMembers(), newMemberUserIds);
            if (isSkipInvitationEmail()) {
                Date now = new Date();
                for (GroupMember newMember : newMembers) {
                    newMember.setActive(true);
                    newMember.setActivatedOn(now);
                }
            }
            group = getGroupService().update(group);

            // get the new members with the member ids set
            newMembers = new ArrayList<GroupMember>();
            for (GroupMember member : group.getGroupMembers()) {
                if (newMemberUserIds.contains(member.getUserId())) {
                    newMembers.add(member);
                }
            }
            
            HelperUtility.fillHandle(getGroupUserService(), group);

            // Send invitations to all new group members
            HelperUtility.sendInvitations(this.getAuditService(), getGroupInvitationService(), getAcceptRejectUrlBase(),
                getRegistrationUrl(), newMembers,
                isSkipInvitationEmail());
            
            Map<String, Object> result = new HashMap<String, Object>();
            result.put("groupId", group.getId());
            setResult(result);
            LoggingWrapperUtility.logExit(getLogger(), signature, new Object[] {SUCCESS });
        } catch (SecurityGroupException e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature, new SecurityGroupsActionException(
                    "error occurs when sending invitation.", e));
        } catch (RuntimeException e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature, new SecurityGroupsActionException(
                    e.getMessage(), e));
        }
    }

    /**
     * <p>
     * Performs complex validation that can't be easily done with declarative XML validation. throws:
     * </p>
     *
     * @throws SecurityGroupsActionValidationException
     *             if validation fails.
     */
    public synchronized void validateData() {
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

    /**
     * <p>
     * Enter group details method. It will put the group in the session.
     * </p>
     *
     * @throws SecurityGroupsActionException
     *             if any error is caught during the operation.
     * @return The execution result
     */
    @Override
    public synchronized String enterGroupDetails() throws SecurityGroupsActionException {
        final String signature = CLASS_NAME + ".enterGroupDetails()";
        LoggingWrapperUtility.logEntrance(getLogger(), signature, null, null);
        try {
            Group group = getGroupService().get(getGroupId());
            
            if (!HelperUtility.checkPermission(ServletActionContext.getRequest(), getCurrentUserId(), getAuthorizationService(), group, false, false)) {
                throw new SecurityGroupsActionException("Has no permission to update group");
            }
            
            HelperUtility.fillHandle(getGroupUserService(), group);
            setGroup(group);
            setSelectedClientId(group.getClient().getId());
            this.groupInvitations = HelperUtility.getGroupMemberInvitations(getGroupInvitationService(), group);
            input();
            LoggingWrapperUtility.logExit(getLogger(), signature, new Object[] {SUCCESS });
            return SUCCESS;
        } catch (SecurityGroupException e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature, new SecurityGroupsActionException(
                    "error occurs when updating group", e));
        } catch (RuntimeException e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature, new SecurityGroupsActionException(
                    "RuntimeException occurs while updating the group", e));
        }
    }
    
    /**
     * <p>
     * Update the group members with new group members. The new added members will be returned in the third 
     * parameters.
     * </p>
     * 
     * @param group the group to update
     * @param newEntities the new group members
     * @param newMembers the new added members will be returned by this method
     */
    private static void updateGroupMembers(Group group, List<GroupMember> newEntities, Set<Long> newMembers) {
        List<GroupMember> entities = new ArrayList<GroupMember>();
        Map<Long, GroupMember> oldEntitiesMap = new HashMap<Long, GroupMember>();
        Set<Long> allMembers = new HashSet<Long>();
        for (GroupMember entity : group.getGroupMembers()) {
            if (entity.getUnassignedOn() == null) {
                oldEntitiesMap.put(entity.getUserId(), entity);
            } else {
                entities.add(entity);
            }
        }
        if (newEntities != null) {
            for (GroupMember entity : newEntities) {
                allMembers.add(entity.getUserId());
                if (oldEntitiesMap.containsKey(entity.getUserId())) {
                    // update
                    GroupMember entity2 = oldEntitiesMap.get(entity.getUserId());
                    entity2.setUseGroupDefault(entity.isUseGroupDefault());
                    entity2.setSpecificPermission(entity.getSpecificPermission());
                    entities.add(entity2);
                } else {
                    // add
                    entities.add(entity);
                    newMembers.add(entity.getUserId());
                }
            }
        }
        for (GroupMember entity : group.getGroupMembers()) {
            if (entity.getUnassignedOn() == null && !allMembers.contains(entity.getUserId())) {
                // remove
                entity.setActive(false);
                entity.setUnassignedOn(new Date());
                entities.add(entity);
            }
        }
        group.getGroupMembers().clear();
        for (GroupMember entity : entities) {
            group.getGroupMembers().add(entity);
        }
    }
    
    /**
     * <p>
     * Gets the new added <code>GroupMember</code> instances.
     * </p>
     * 
     * @param members the all group members
     * @param newMembers the userIds of new added group members
     * @return the new added <code>GroupMember</code> instances
     */
    private static List<GroupMember> getNewAddedMembers(List<GroupMember> members, Set<Long> newMembers) {
        List<GroupMember> newAddedMembers = new ArrayList<GroupMember>();
        Map<Long, GroupMember> membersMap = new HashMap<Long, GroupMember>();
        for (GroupMember entity : members) {
            if (entity.getUnassignedOn() == null) {
                membersMap.put(entity.getUserId(), entity);
            }
        }
        for (Long userId : newMembers) {
            newAddedMembers.add(membersMap.get(userId));
        }
        return newAddedMembers;
    }
    
    /**
     * <p>
     * Update the group projects and billings with new group.
     * </p>
     * 
     * @param oldGroup the old group to update
     * @param newGroup the new group
     */
    private static void updateProjectsAndBillings(Group oldGroup, Group newGroup) {
        if (newGroup.getBillingAccounts() == null) {
            oldGroup.setBillingAccounts(new ArrayList<BillingAccount>());
        } else {
            oldGroup.setBillingAccounts(newGroup.getBillingAccounts());
        }
        Map<Long, DirectProject> existsProjects = new HashMap<Long, DirectProject>();
        for (DirectProject dp : oldGroup.getDirectProjects()) {
            existsProjects.put(dp.getDirectProjectId(), dp);
        }
        List<DirectProject> newProjects = new ArrayList<DirectProject>();
        if (newGroup.getDirectProjects() != null) {
            for (DirectProject dp : newGroup.getDirectProjects()) {
                if (existsProjects.containsKey(dp.getDirectProjectId())) {
                    newProjects.add(existsProjects.get(dp.getDirectProjectId()));
                } else {
                    DirectProject proj = new DirectProject();
                    proj.setDirectProjectId(dp.getDirectProjectId());
                    proj.setGroup(oldGroup);
                    newProjects.add(proj);
                }
            }
        }
        
        oldGroup.getDirectProjects().clear();
        for (DirectProject dp : newProjects) {
            oldGroup.getDirectProjects().add(dp);
        }
    }
    
    /**
     * Sets the group invitations.
     * 
     * @return the groupInvitations
     */
    public Map<Long, GroupInvitation> getGroupInvitations() {
        return groupInvitations;
    }
}
