/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.project.edit;

import com.topcoder.direct.services.view.action.groups.BaseAction;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.security.TCSubject;
import com.topcoder.security.groups.model.BillingAccount;
import com.topcoder.security.groups.model.DirectProject;
import com.topcoder.security.groups.model.Group;
import com.topcoder.security.groups.services.DirectProjectService;
import com.topcoder.security.groups.services.GroupService;
import com.topcoder.security.groups.services.dto.GroupSearchCriteria;
import com.topcoder.security.groups.services.dto.ProjectDTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * <p>A <code>Struts</code> action to be used for servicing requests for saving security groups permissions for single
 * TC Direct project.</p>
 *
 * <p>
 * Version 1.1 (Release Assembly - TopCoder Security Groups Release 8 - Automatically Grant Permissions) changes:
 * <ol>
 *     <li>Added {@link #directProjectService} field.</li>
 *     <li>Updated {@link #executeAction()} method to support auto grant permissions.</li>
 * </ol>
 * </p>
 *
 * @author TCSDEVELOPER, freegod
 * @version 1.1 (Release Assembly - TopCoder Security Groups Release 8 - Automatically Grant Permissions)
 * @since 1.0
 */
public class SaveSecurityGroupPermissionsAction extends BaseAction {

    /**
     * <p>A <code>long</code> providing the ID of TC Direct project to update security groups permissions for.</p>
     */
    private long directProjectId;

    /**
     * <p>A <code>Long[]</code> providing the IDs of security groups associated with the project.</p>
     */
    private Long[] groupIds;

    /**
     * <p>A <code>GroupService</code> providing the interface to security groups services..</p>
     */
    private GroupService groupService;

    /**
     * <p><code>DirectProjectService</code> used to retrieve projects associated with billing accounts.</p>
     *
     * @since 1.1
     */
    private DirectProjectService directProjectService;

    /**
     * <p>Constructs new <code>SaveSecurityGroupPermissionsAction</code> instance. This implementation does
     * nothing.</p>
     */
    public SaveSecurityGroupPermissionsAction() {
    }

    /**
     * <p>Gets the ID of TC Direct project to update security groups permissions for.</p>
     *
     * @return a <code>long</code> providing the ID of TC Direct project to update security groups permissions for.
     */
    public long getDirectProjectId() {
        return this.directProjectId;
    }

    /**
     * <p>Sets the ID of TC Direct project to update security groups permissions for.</p>
     *
     * @param directProjectId a <code>long</code> providing the ID of TC Direct project to update security groups
     *                        permissions for.
     */
    public void setDirectProjectId(long directProjectId) {
        this.directProjectId = directProjectId;
    }

    /**
     * <p>Gets the interface to security groups services..</p>
     *
     * @return a <code>GroupService</code> providing the interface to security groups services..
     */
    public GroupService getGroupService() {
        return this.groupService;
    }

    /**
     * <p>Sets the interface to security groups services..</p>
     *
     * @param groupService a <code>GroupService</code> providing the interface to security groups services..
     */
    public void setGroupService(GroupService groupService) {
        this.groupService = groupService;
    }

    /**
     * <p>Gets the IDs of security groups associated with the project.</p>
     *
     * @return a <code>Long[]</code> providing the IDs of security groups associated with the project.
     */
    public Long[] getGroupIds() {
        return this.groupIds;
    }

    /**
     * <p>Sets the IDs of security groups associated with the project.</p>
     *
     * @param groupIds a <code>Long[]</code> providing the IDs of security groups associated with the project.
     */
    public void setGroupIds(Long[] groupIds) {
        this.groupIds = groupIds;
    }

    /**
     * Gets direct project service.
     *
     * @return the direct project service
     *
     * @since 1.1
     */
    public DirectProjectService getDirectProjectService() {
        return directProjectService;
    }

    /**
     * Sets direct project service.
     *
     * @param directProjectService the direct project service
     *
     * @since 1.1
     */
    public void setDirectProjectService(DirectProjectService directProjectService) {
        this.directProjectService = directProjectService;
    }

    /**
     * <p>Executes an action.</p>
     * 
     * @throws Exception if an unexpected error occurs.
     */
    @Override
    protected void executeAction() throws Exception {
        // Get all security groups accessible to current user
        TCSubject currentUser = DirectUtils.getTCSubjectFromSession();
        GroupSearchCriteria groupSearchCriteria = new GroupSearchCriteria();
        groupSearchCriteria.setUserId(currentUser.getUserId());
        List<Group> groups = getGroupService().search(groupSearchCriteria, 0, 0).getValues();

        Set<Long> groupIdsSet = new HashSet<Long>();
        if (getGroupIds() != null) {
            groupIdsSet.addAll(Arrays.asList(getGroupIds()));
        }

        // Iterate over all groups and either exclude or include requested project to them
        for (Group group : groups) {
            boolean mustBeAssociated = groupIdsSet.contains(group.getId());
            List<DirectProject> groupDirectProjects = group.getDirectProjects();
            boolean isAlreadyAssigned = false;
            boolean mustBeUpdated = false;
            if (groupDirectProjects == null || groupDirectProjects.size() <= 0) {
                //check auto-grant permissions and security groups associated with billing accounts
                if(group.getAutoGrant()) {
                    isAlreadyAssigned = true;
                }
                
                if(group.getBillingAccounts() != null) {
                    List<BillingAccount> billingAccounts = group.getBillingAccounts();
                    List<ProjectDTO> projects = directProjectService.getProjectsByBillingAccounts(billingAccounts);
                    for(ProjectDTO dto : projects) {
                        if(dto.getProjectId() == directProjectId) {
                            isAlreadyAssigned = true;
                            break;
                        }
                    }
                }
            } else {
                Iterator<DirectProject> iterator = groupDirectProjects.iterator();
                while (iterator.hasNext()) {
                    DirectProject directProject = iterator.next();
                    if (directProject.getDirectProjectId() == getDirectProjectId()) {
                        if (mustBeAssociated) {
                            isAlreadyAssigned = true;
                        } else {
                            iterator.remove();
                            mustBeUpdated = true;
                        }
                    }
                }
            }

            if (mustBeAssociated && !isAlreadyAssigned) {
                DirectProject directProject = new DirectProject();
                directProject.setDirectProjectId(getDirectProjectId());
                directProject.setGroup(group);
                groupDirectProjects.add(directProject);
                mustBeUpdated = true;
            }
            if (mustBeUpdated) {
                getGroupService().update(group);
            }
        }
    }
}
