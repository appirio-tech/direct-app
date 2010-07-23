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
import com.topcoder.management.project.DesignComponents;
import com.topcoder.security.TCSubject;
import com.topcoder.service.facade.contest.ContestServiceException;
import com.topcoder.service.facade.project.DAOFault;

/**
 * <p>
 * Common action to handle some common requests.
 * </p>
 *
 * @author BeBetter
 * @version 1.0
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
        setResult(configs);
        return SUCCESS;
    }

    /**
     * <p>
     * Gets project studio contest fee.
     * </p>
     *
     * @param billingProjectId the billing project id
     * @param studioSubTypeId studio sub type id
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
}
