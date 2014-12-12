/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.project;

import com.topcoder.direct.cloudvm.service.CloudVMService;
import com.topcoder.direct.cloudvm.service.CloudVMServiceException;
import com.topcoder.direct.services.configs.ConfigUtils;
import com.topcoder.direct.services.view.action.FormAction;
import com.topcoder.direct.services.view.action.cloudvm.AbstractVMAction;
import com.topcoder.direct.services.view.action.BaseDirectStrutsAction;
import com.topcoder.direct.services.view.dto.cloudvm.*;
import com.topcoder.direct.services.view.dto.contest.TypedContestBriefDTO;
import com.topcoder.direct.services.view.form.ProjectIdForm;
import com.topcoder.direct.services.view.util.DataProvider;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.security.TCSubject;
import com.topcoder.service.facade.permission.PermissionServiceFacade;
import com.topcoder.service.permission.PermissionServiceException;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Action supporting project level vm management page.
 *
 * @author jiajizhou86
 * @version 1.0
 */
public class ProjectVMManagementAction extends BaseDirectStrutsAction
        implements FormAction<ProjectIdForm> {

    /**
     * Logger for this class.
     */
    private static final Logger logger = Logger.getLogger(ProjectVMManagementAction.class);

    /**
     * <p>A <code>ProjectIdForm</code> providing the ID of a requested project.</p>
     */
    private ProjectIdForm formData = new ProjectIdForm();

    /**
     * Represents the CloudVMService to manage the VM. It has getter & setter. Can be any value.
     */
    private CloudVMService cloudVMService;

    /**
     * The permission service facade.
     */
    private PermissionServiceFacade permissionServiceFacade;

    /**
     * List of vm usages.
     */
    private List<VMUsage> vmUsages;

    /**
     * List of vm images.
     */
    private List<VMImage> vmImages;

    /**
     * List of contest types.
     */
    private List<VMContestType> vmContestTypes;

    /**
     * List of current vm instances.
     */
    private List<VMInstanceData> vmInstances;

    /**
     * List of contests of the current project
     */
    private List<TypedContestBriefDTO> contests;

    /**
     * Represents the number of active VMs for this contest.
     */
    private int activeVMCount;

    /**
     * Represents the number of terminated VMs for this contest.
     */
    private int terminatedVMCount;

    /**
     * Represents the number of total VMs created for this contest.
     */
    private int totalVMCount;

    /**
     * Whether current user has Write or Full permission
     */
    private boolean allowVMTermination;

    /**
     * Current login user info
     */
    private TCSubject user;

    /**
     * Represents if there is some error when accessing VM services.
     */
    private boolean errorLoadingVM;

    /**
     * Represents the error message should shown to user if there is error accessing VM services.
     */
    private String vmErrorMessage;

    /**
     * Fetch VM data related to the current project
     *
     * @throws Exception if any error occurs
     */
    protected void executeAction() throws Exception {
        populateUserData();
        populateContestData();

        // initialize empty data
        vmUsages = new ArrayList<VMUsage>();
        vmInstances = new ArrayList<VMInstanceData>();
        totalVMCount = 0;
        activeVMCount = 0;
        terminatedVMCount = 0;
        errorLoadingVM = false;

        try {
            populateVMData();
        } catch (CloudVMServiceException ex) {
            logger.error("Exception caught when accessing CloudVMService: " + ex);
            errorLoadingVM = true;
            vmErrorMessage = ConfigUtils.getCloudVMServiceAccessErrorConfig().getGeneralErrorMessage();
        }
    }

    /**
     * Get current user, check if the user has permission to terminate VMs
     *
     * @throws PermissionServiceException if error occurs getting project's permission
     */
    private void populateUserData() throws PermissionServiceException {
        user = AbstractVMAction.getUser();

        allowVMTermination = DirectUtils.isCockpitAdmin(user)
                || DirectUtils.isVMManager(user)
                || DirectUtils.hasProjectWritePermission(this, user, this.getFormData().getProjectId());
    }

    /**
     * Populate the contests of the current project
     *
     * @throws Exception if any error occurs
     */
    private void populateContestData() throws Exception {
        contests = DataProvider.getProjectTypedContests(user.getUserId(), formData.getProjectId());
    }

    /**
     * Get VM Data associated to the contests
     *
     * @throws CloudVMServiceException if error occurs getting data from CloudVMService
     */
    private void populateVMData() throws CloudVMServiceException {
        List<Long> contestIds = new ArrayList<Long>();
        for (TypedContestBriefDTO contest : contests) {
            contestIds.add(contest.getId());
        }

        vmImages = getCloudVMService().getVMImages(user);
        vmContestTypes = getCloudVMService().getVMContestTypes(user);
        vmUsages = getCloudVMService().getVMUsages(user);
        vmInstances = getCloudVMService().getVMInstancesForContests(user, contestIds);

        totalVMCount = vmInstances.size();
        for(VMInstanceData vmInstance : vmInstances) {
            if (vmInstance.getStatus().equals(VMInstanceStatus.RUNNING)) {
                activeVMCount++;
            }

            if (vmInstance.getStatus().equals(VMInstanceStatus.TERMINATED)) {
                terminatedVMCount++;
            }
        }
    }

    /**
     * Get the cloud vm service.
     *
     * @return the cloud vm service.
     */
    public CloudVMService getCloudVMService() {
        return cloudVMService;
    }

    /**
     * Set the cloud vm service.
     *
     * @param cloudVMService the cloud vm service.
     */
    public void setCloudVMService(CloudVMService cloudVMService) {
        this.cloudVMService = cloudVMService;
    }

    /**
     * Gets the permission service facade.
     *
     * @return the permission service facade.
     */
    public PermissionServiceFacade getPermissionServiceFacade() {
        return permissionServiceFacade;
    }

    /**
     * Sets the permission service facade.
     *
     * @param permissionServiceFacade the permission service facade instance.
     */
    public void setPermissionServiceFacade(PermissionServiceFacade permissionServiceFacade) {
        this.permissionServiceFacade = permissionServiceFacade;
    }

    /**
     * <p>Gets the form data.</p>
     *
     * @return an <code>Object</code> providing the data for form submitted by user.
     */
    public ProjectIdForm getFormData() {
        return formData;
    }


    /**
     * Returns list of vm images.
     *
     * @return list of vm images
     */
    public List<VMImage> getVmImages() {
        return vmImages;
    }

    /**
     * Returns list of contest types.
     *
     * @return list of contest types
     */
    public List<VMContestType> getVmContestTypes() {
        return vmContestTypes;
    }

    /**
     * Returns list of usages.
     *
     * @return list of usages
     */
    public List<VMUsage> getVmUsages() {
        return vmUsages;
    }

    /**
     * Returns list of vm instances.
     *
     * @return list of vm instances
     */
    public List<VMInstanceData> getVmInstances() {
        return vmInstances;
    }

    /**
     * Returns the number of active VMs for this contest.
     *
     * @return the number of active VMs for this contest.
     */
    public int getActiveVMCount() {
        return activeVMCount;
    }

    /**
     * Returns the number of total VMs for this contest.
     *
     * @return the number of total VMs for this contest.
     */
    public int getTotalVMCount() {
        return totalVMCount;
    }

    /**
     * Returns the number of terminated VMs for this contest.
     *
     * @return the number of terminated VMs for this contest.
     */
    public int getTerminatedVMCount() {
        return terminatedVMCount;
    }

    /**
     * Tells whether user has write or full permission.
     *
     * @return true if user has write or full permission .
     */
    public boolean getAllowVMTermination() {
        return allowVMTermination;
    }

    /**
     * Returns if there is any error accessing VM services.
     *
     * @return if there is any error accessing VM services.
     */
    public boolean isErrorLoadingVM() {
        return errorLoadingVM;
    }

    /**
     * Returns the error message should be shown to user if there is error accessing VM services.
     * @return the error message
     */
    public String getVmErrorMessage() {
        return vmErrorMessage;
    }

    /**
     * Set the error message should be shown to user if there is error accessing VM services.
     * @param vmErrorMessage the error message
     */
    public void setVmErrorMessage(String vmErrorMessage) {
        this.vmErrorMessage = vmErrorMessage;
    }
}
