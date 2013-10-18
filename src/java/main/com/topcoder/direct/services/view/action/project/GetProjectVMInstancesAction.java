/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.project;

import com.topcoder.direct.cloudvm.service.CloudVMService;
import com.topcoder.direct.cloudvm.service.CloudVMServiceException;
import com.topcoder.direct.services.view.action.AbstractAction;
import com.topcoder.direct.services.view.action.cloudvm.AbstractVMAction;
import com.topcoder.direct.services.view.dto.cloudvm.VMInstanceData;
import com.topcoder.direct.services.view.dto.contest.TypedContestBriefDTO;
import com.topcoder.direct.services.view.util.DataProvider;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.security.TCSubject;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Get all VMs for the project.
 *
 * @author jiajizhou86
 * @version 1.0
 */
public class GetProjectVMInstancesAction extends AbstractAction {

    /**
     * Logger for this class.
     */
    private static final Logger logger = Logger.getLogger(GetProjectVMInstancesAction.class);

    /**
     * Represents the CloudVMService to manage the VM. It has getter & setter. Can be any value.
     */
    private CloudVMService cloudVMService;

    /**
     * Current login user info
     */
    private TCSubject user;

    /**
     * The given project id
     */
    private long projectId;

    /**
     * List of current vm instances.
     */
    private List<VMInstanceData> vmInstances;

    /**
     * List of contests of the current project
     */
    private List<TypedContestBriefDTO> contests;

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
     * Returns list of vm instances.
     *
     * @return list of vm instances
     */
    public List<VMInstanceData> getVmInstances() {
        return vmInstances;
    }

    /**
     * Fetch VM data related to the current project
     *
     * @throws Exception if any error occurs
     */
    public String execute() throws Exception {
        HttpServletRequest request = DirectUtils.getServletRequest();
        projectId = Long.parseLong(request.getParameter("projectId"));

        String result = super.execute();
        if (result.equals(SUCCESS)) {
            populateUserData();
            populateContestData();
            try {
                populateVMData();
                setResult(vmInstances);
            } catch (Exception ex) {
                logger.error("Exception caught when accessing CloudVMService: " + ex);
                setError(ex);
                return ERROR;
            }
        }
        return result;
    }

    /**
     * Get the current user.
     */
    private void populateUserData() {
        user = AbstractVMAction.getUser();
    }

    /**
     * Get contests of current project
     *
     * @throws Exception if any error occurs
     */
    private void populateContestData() throws Exception {
        contests = DataProvider.getProjectTypedContests(user.getUserId(), projectId);
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

        vmInstances = getCloudVMService().getVMInstancesForContests(user, contestIds);
    }
}
