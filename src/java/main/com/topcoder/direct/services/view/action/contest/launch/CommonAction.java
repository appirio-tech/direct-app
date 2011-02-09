/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.contest.launch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.topcoder.clients.model.Project;
import com.topcoder.clients.model.ProjectContestFee;
import com.topcoder.direct.services.configs.ConfigUtils;
import com.topcoder.direct.services.view.dto.contest.ContestCopilotDTO;
import com.topcoder.direct.services.view.util.DataProvider;
import com.topcoder.management.project.DesignComponents;
import com.topcoder.security.TCSubject;
import com.topcoder.service.facade.contest.ContestServiceException;
import com.topcoder.service.facade.project.DAOFault;

/**
 * <p>
 * Common action to handle some common requests.
 * </p>
 *
 * <p> Version 1.1 Changes:
 * - New property directProjectId is added.
 * - New method getDirectProjectCopilots to return copilots of direct project as JSON.
 * </p>
 *
 * @author BeBetter, TCSDEVELOPER
 * @version 1.1
 */
public class CommonAction extends BaseDirectStrutsAction {
    /**
     * <p>
     * Billing project id field.
     * </p>
     */
    private long billingProjectId;

    /**
     * <p>
     * The direct project id field.
     * </p>
     *
     * @since 1.1
     */
    private long directProjectId;

    /**
     * <p>
     * Software catalog id.
     * </p>
     */
    private long catalogId = -1;

    /**
     * <p>
     * Executes the action.
     * </p>
     */
    @Override
    protected void executeAction() throws Exception {
        // do nothing
    }

    /**
     * <p>
     * Gets the copilot user ids / copilot handle for the given direct project id.
     * </p>
     *
     * @return the success result
     * @throws Exception if any error occurs
     * @since 1.1
     */
    public String getDirectProjectCopilots() throws Exception {
        // get all the copilots for the given direct project
        List<ContestCopilotDTO> copilots = DataProvider.getCopilotsForDirectProject(this.directProjectId);
        long currentUserId = getCurrentUser().getUserId();
        boolean isCurrentUser = false;

        // use map to store the copilots result, key is copilot user id, value is copilot handle
        Map<String, String> value = new HashMap<String, String>();
        for (ContestCopilotDTO copilot : copilots) {
            value.put(String.valueOf(copilot.getUserId()), copilot.getHandle());
            
            // current user is one of the copilots
            if(currentUserId == copilot.getUserId()) {
                isCurrentUser = true;
            }
        }

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("copilots", value);
        result.put("selected", (isCurrentUser ? String.valueOf(currentUserId) : "0"));
        setResult(result);
        return SUCCESS;
    }

    /**
     * <p>
     * Gets the studio configurations.
     * </p>
     *
     * @return the success page
     * @throws Exception if any error occurs
     */
    public String getStudioConfigs() throws Exception {
        Map<String, Object> configs = new HashMap<String, Object>();
        configs.put("overview", ConfigUtils.getStudioOverviews());
        configs.put("fees", ConfigUtils.getStudioContestFees());
        configs.put("fileTypes", ConfigUtils.getFileTypes().getFileTypes());
        configs.put("softwareContestFees", ConfigUtils.getSoftwareContestFees());
        configs.put("billingInfos", getBillingProjectInfos());
        configs.put("copilotFees", ConfigUtils.getCopilotFees());
        setResult(configs);
        return SUCCESS;
    }

    /**
     * <p>
     * Gets project studio contest fee.
     * </p>
     *
     * @return the success page
     * @throws Exception if any error occurs
     */
    public String getBillingProjectContestFees() throws Exception {
        TCSubject tcSubject = DirectStrutsActionsHelper.getTCSubjectFromSession();
        List<ProjectContestFee> contestFees = getAdminServiceFacade().getContestFeesByProject(tcSubject,
            billingProjectId);

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("fees", contestFees);
        setResult(result);
        return SUCCESS;
    }

    /**
     * <p>
     * Gets the billing project information.
     * </p>
     *
     * @return the billing project information. each project is represented in a map object.
     * @throws DAOFault if a DAO error occurs
     */
    private List<Map<String, Object>> getBillingProjectInfos() throws DAOFault {
        List<Map<String, Object>> billings = new ArrayList<Map<String, Object>>();

        for (Project project : getProjectServiceFacade().getClientProjectsByUser(
            DirectStrutsActionsHelper.getTCSubjectFromSession())) {
            Map<String, Object> projectInfo = new HashMap<String, Object>();
            projectInfo.put("id", project.getId());
            projectInfo.put("manualPrizeSetting", project.isManualPrizeSetting());
            billings.add(projectInfo);
        }

        return billings;
    }


    /**
     * <p>
     * Gets a list of design components for the given user.
     * </p>
     *
     * @return a list of design components.
     * @throws ContestServiceException if contest service exception occurs
     */
    public String getDesignComponents() throws ContestServiceException {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("designs", getContestServiceFacade().getDesignComponents(getCurrentUser()));
        setResult(result);
        return SUCCESS;
    }

    /**
     * <p>
     * Gets the categories.
     * </p>
     *
     * @return the categories
     */
    public String getCategories() {
        setResult(getReferenceDataBean().getCatalogToCategoriesMap().get(catalogId));
        return SUCCESS;
    }

    /**
     * @return the billingProjectId
     */
    public long getBillingProjectId() {
        return billingProjectId;
    }

    /**
     * @param billingProjectId the billingProjectId to set
     */
    public void setBillingProjectId(long billingProjectId) {
        this.billingProjectId = billingProjectId;
    }

    /**
     * Gets the catalog id.
     *
     * @return the catalog id
     */
    public long getCatalogId() {
        return catalogId;
    }

    /**
     * Sets the catalog id.
     *
     * @param catalogId the catalog id
     */
    public void setCatalogId(long catalogId) {
        this.catalogId = catalogId;
    }

    /**
     * Gets the direct project id.
     *
     * @return the direct project id.
     * @since 1.1
     */
    public long getDirectProjectId() {
        return directProjectId;
    }

    /**
     * Sets the direct project id.
     *
     * @param directProjectId the direct project id to set.
     * @since 1.1
     */
    public void setDirectProjectId(long directProjectId) {
        this.directProjectId = directProjectId;
    }
}
