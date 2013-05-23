/*
 * Copyright (C) 2010 - 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.contest.launch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.topcoder.clients.model.Project;
import com.topcoder.clients.model.ProjectContestFee;
import com.topcoder.clients.model.ProjectContestFeePercentage;
import com.topcoder.direct.services.configs.ConfigUtils;
import com.topcoder.direct.services.view.action.accounting.BaseContestFeeAction;
import com.topcoder.direct.services.view.dto.contest.ContestCopilotDTO;
import com.topcoder.direct.services.view.dto.contest.ProblemDTO;
import com.topcoder.direct.services.view.util.DataProvider;
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
 * <p> Version 1.2 Changes (Module Assembly - Contest Fee Based on % of Member Cost User Part):
 * - Modified method getBillingProjectContestFees. Added support for fee percentage.
 * - If the billing is configured by percentage of member cost, the contest fee will be calculated 
 * - as a percentage of the member cost.
 * </p>
 *
 * <p>
 * Version 1.3 Changes (Release Assembly - TopCoder Cockpit Billing Account Project Association)
 * - Add method {@link #getBillingAccountsForProject()} to handle ajax request to get billing accounts for project.
 * </p>
 *
 * <p>
 * Version 1.4 (Release Assembly - TopCoder Direct Cockpit Release Assembly Ten)
 * - Updates method {@link #getBillingAccountsForProject()} to return all the billing accounts of the project without
 * checking the billing account permission of the users.
 * </p>
 * 
 * <p>
 * Version 1.5 (Release Assembly - TopCoder Cockpit - Launch Contest Update for Marathon Match)
 * - New method {@link #getActiveProblemSet()} to return active problems as JSON.
 * </p>
 *
 * @author BeBetter, pvmagacho, GreatKevin, bugbuka
 * @version 1.5
 */
public class CommonAction extends BaseContestFeeAction {
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
     * Gets the active problems.
     * </p>
     * 
     * @return the active problems.
     * @since 1.5
     */
    public String getActiveProblemSet() throws Exception {

        // gets the billing accounts associated to the project
        List<ProblemDTO> problems = DataProvider.getActiveProblemSet();

        Map<String, String> result = new HashMap<String, String>();

        // filter out the billing accounts user has access to
        for(ProblemDTO p : problems) {
            result.put(String.valueOf(p.getId()), p.getName());
        }

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
    public String getContestConfigs() throws Exception {
        Map<String, Object> configs = new HashMap<String, Object>();
        configs.put("overview", ConfigUtils.getStudioOverviews());
        configs.put("fileTypes", ConfigUtils.getFileTypes().getFileTypes());
        configs.put("studioContestFees", ConfigUtils.getStudioContestFees());
        configs.put("softwareContestFees", ConfigUtils.getSoftwareContestFees());
        configs.put("algorithmContestFees", ConfigUtils.getAlgorithmSubtypeContestFees());
        configs.put("copilotFees", ConfigUtils.getCopilotFees());
        configs.put("billingInfos", getBillingProjectInfos());
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
        Map<String, Object> result = new HashMap<String, Object>();
        
        // set the percentage info
        ProjectContestFeePercentage percentage = getContestFeePercentageService().getByProjectId(billingProjectId);
        if (percentage != null && percentage.isActive()) {
            result.put("percentage", percentage);
        } else {
            // Only return fee if percentage is not set
            TCSubject tcSubject = DirectStrutsActionsHelper.getTCSubjectFromSession();
            List<ProjectContestFee> contestFees = getAdminServiceFacade().getContestFeesByProject(tcSubject,
                billingProjectId);
            result.put("fees", contestFees);
        }
        
        setResult(result);
        return SUCCESS;
    }

    /**
     * Gets the billing accounts for the project.
     *
     * @return the result code
     * @since 1.3
     */
    public String getBillingAccountsForProject() {
        try {
            // gets the billing accounts associated to the project
            List<Project> billingAccountsByProject = getProjectServiceFacade().getBillingAccountsByProject(getDirectProjectId());

            List<Map<String, String>> result = new ArrayList<Map<String, String>>();
            
            long[] billingAccountIds = new long[billingAccountsByProject.size()];
            
            for (int i = 0; i < billingAccountIds.length; i++){
                billingAccountIds[i] = billingAccountsByProject.get(i).getId();
            }
            
            boolean[] requireCCAs = getContestServiceFacade().requireBillingProjectsCCA(billingAccountIds);
            
            for (int i = 0; i < billingAccountIds.length; i++){
                Map<String, String> billingAccount = new HashMap<String, String>();
                billingAccount.put("id", String.valueOf(billingAccountsByProject.get(i).getId()));
                billingAccount.put("name", billingAccountsByProject.get(i).getName());
                billingAccount.put("cca", String.valueOf(requireCCAs[i]));
                result.add(billingAccount);
            }
            setResult(result);
        } catch (Throwable e) {
            if (getModel() != null) {
                setResult(e);
            }
        }

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
