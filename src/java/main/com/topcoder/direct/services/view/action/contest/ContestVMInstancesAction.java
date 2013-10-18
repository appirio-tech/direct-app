/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.contest;

import com.topcoder.direct.cloudvm.service.CloudVMService;
import com.topcoder.direct.cloudvm.service.CloudVMServiceException;
import com.topcoder.direct.services.configs.ConfigUtils;
import com.topcoder.direct.services.view.action.contest.launch.DirectStrutsActionsHelper;
import com.topcoder.direct.services.view.action.contest.launch.StudioOrSoftwareContestAction;
import com.topcoder.direct.services.view.dto.UserProjectsDTO;
import com.topcoder.direct.services.view.dto.cloudvm.VMInstanceData;
import com.topcoder.direct.services.view.dto.cloudvm.VMUsage;
import com.topcoder.direct.services.view.dto.cloudvm.VMInstanceStatus;
import com.topcoder.direct.services.view.dto.contest.BaseContestCommonDTO;
import com.topcoder.direct.services.view.dto.contest.ContestStatsDTO;
import com.topcoder.direct.services.view.dto.contest.TypedContestBriefDTO;
import com.topcoder.direct.services.view.dto.project.ProjectBriefDTO;
import com.topcoder.direct.services.view.util.DataProvider;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.direct.services.view.util.SessionData;
import com.topcoder.security.TCSubject;
import com.topcoder.service.facade.contest.ContestServiceFacade;
import com.topcoder.service.project.SoftwareCompetition;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>Action class which handles retrieving all VM instances for the contest.</p>
 *
 * <p>
 * Version 1.1 (Release Assembly - TopCoder Direct VM Instances Management)
 * - added error message handling when there is error accessing VM services.
 * </p>
 *
 * @author gentva, jiajizhou86
 * @version 1.1
 */
public class ContestVMInstancesAction extends StudioOrSoftwareContestAction {

    /**
     * Logger for this class
     * @since 1.1
     */
    private Logger logger = Logger.getLogger(ContestVMInstancesAction.class);

    /**
     * <p>A <code>BaseContestCommonDTO</code> providing the view data for displaying by
     * <code>Contest VM Instances</code> view. </p>
     */
    private BaseContestCommonDTO viewData;
    /**
     * <p>A <code>SessionData</code> providing interface to current session.</p>
     */
    private SessionData sessionData;

    /**
     * Represents the CloudVMService to manage the VM. It has getter & setter. Can be any value.
     */
    private CloudVMService cloudVMService;

    /**
     * List of vm usages.
     */
    private List<VMUsage> vmUsages;

    /**
     * List of current vm instances.
     */
    private List<VMInstanceData> vmInstances;

    /**
     * Whether current user has Write or Full permission
     */
    private boolean hasWriteOrFullPermission;

    /**
     * Whether the current contest is studio.
     */
    private boolean isStudio;

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
     * Represents if there is some error when accessing VM services.
     *
     * @since 1.1
     */
    private boolean errorLoadingVM;

    /**
     * Represents the error message should shown to user if there is error accessing VM services.
     *
     * @since 1.1
     */
    private String vmErrorMessage;

    /**
     * Initialize the action. The constructor will initialize an empty view data.
     */
    public ContestVMInstancesAction() {
        viewData = new BaseContestCommonDTO();
    }

    /**
     * <p>Handles the incoming request. If action is executed successfully then changes the current project context to
     * project for contest requested for this action.</p>
     *
     * @throws Exception if an unexpected error occurs while processing the request.
     */
    @Override
    public void executeAction() throws Exception {
        // Get current session
        HttpServletRequest request = DirectUtils.getServletRequest();

        HttpSession session = request.getSession(false);

        if (session != null) {
            sessionData = new SessionData(session);
        }

        ContestServiceFacade contestServiceFacade = getContestServiceFacade();
        TCSubject currentUser = DirectStrutsActionsHelper.getTCSubjectFromSession();

        // Set registrants data
        long contestId = getProjectId();

        SoftwareCompetition competition = contestServiceFacade.getSoftwareContestByProjectId(currentUser, contestId);
        isStudio = DirectUtils.isStudio(competition);

        hasWriteOrFullPermission = DirectUtils.hasWritePermission(this, currentUser, contestId, isStudio);

        // Set contest stats
        ContestStatsDTO contestStats = DirectUtils.getContestStats(currentUser, contestId, competition);
        getViewData().setContestStats(contestStats);

        // Set projects data
        List<ProjectBriefDTO> projects = DataProvider.getUserProjects(currentUser.getUserId());
        UserProjectsDTO userProjectsDTO = new UserProjectsDTO();
        userProjectsDTO.setProjects(projects);
        getViewData().setUserProjects(userProjectsDTO);

        // Set current project contests
        List<TypedContestBriefDTO> contests = DataProvider.getProjectTypedContests(
                currentUser.getUserId(), contestStats.getContest().getProject().getId());
        this.sessionData.setCurrentProjectContests(contests);

        // Set current project context based on selected contest
        this.sessionData.setCurrentProjectContext(contestStats.getContest().getProject());
        this.sessionData.setCurrentSelectDirectProjectID(contestStats.getContest().getProject().getId());
        
         DirectUtils.setDashboardData(currentUser, contestId, viewData,
                getContestServiceFacade(), !isStudio);

        // initialize empty data for vm instances
        vmUsages = new ArrayList<VMUsage>();
        vmInstances = new ArrayList<VMInstanceData>();
        totalVMCount = 0;
        activeVMCount = 0;
        terminatedVMCount = 0;
        errorLoadingVM = false;

        try {
            vmUsages = getCloudVMService().getVMUsages(currentUser);

            // get all VM instances including all statuses.
            vmInstances = getCloudVMService().getVMInstancesForContest(currentUser, contestId);

            totalVMCount = vmInstances.size();
            for(VMInstanceData vmInstance : vmInstances) {
                if (vmInstance.getStatus().equals(VMInstanceStatus.RUNNING)) {
                    activeVMCount++;
                }

                if (vmInstance.getStatus().equals(VMInstanceStatus.TERMINATED)) {
                    terminatedVMCount++;
                }
            }
        } catch (CloudVMServiceException ex) {
            logger.error("Exception caught when accessing CloudVMService: " + ex);
            errorLoadingVM = true;
            vmErrorMessage = ConfigUtils.getCloudVMServiceAccessErrorConfig().getGeneralErrorMessage();
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
     * @param cloudVMService  the cloud vm service.
     */
    public void setCloudVMService(CloudVMService cloudVMService) {
        this.cloudVMService = cloudVMService;
    }

    /**
     * Tells whether the current contest is studio
     *
     * @return true if the current contest is studio
     */
    public boolean isStudio() {
        return isStudio;
    }

    /**
     * Tells whether user has write or full permission.
     *
     * @return true if user has write or full permission .
     */
    public boolean getHasWriteOrFullPermission() {
        return hasWriteOrFullPermission;
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
     * Returns the list of VM instances for this contest.
     *
     * @return the list of VM instances for this contest.
     */
    public List<VMInstanceData> getVmInstances() {
        return vmInstances;
    }

    /**
     * Returns the list of VM usages.
     *
     * @return the list of VM usages.
     */
    public List<VMUsage> getVmUsages() {
        return vmUsages;
    }

    /**
     * Returns if there is any error accessing VM services.
     *
     * @return if there is any error accessing VM services.
     * @since 1.1
     */
    public boolean isErrorLoadingVM() {
        return errorLoadingVM;
    }

    /**
     * Returns the error message should be shown to user if there is error accessing VM services.
     * @return the error message
     * @since 1.1
     */
    public String getVmErrorMessage() {
        return vmErrorMessage;
    }

    /**
     * Set the error message should be shown to user if there is error accessing VM services.
     * @param vmErrorMessage the error message
     * @since 1.1
     */
    public void setVmErrorMessage(String vmErrorMessage) {
        this.vmErrorMessage = vmErrorMessage;
    }

    /**
     * <p>Gets the current session associated with the incoming request from client.</p>
     *
     * @return a <code>SessionData</code> providing access to current session.
     */
    public SessionData getSessionData() {
        return this.sessionData;
    }

    /**
     * Gets the view data for this action.
     *
     * @return the view data for this action.
     */
    public BaseContestCommonDTO getViewData() {
        return viewData;
    }
}
