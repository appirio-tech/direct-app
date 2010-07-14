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
import com.topcoder.security.TCSubject;
import com.topcoder.service.facade.admin.AdminServiceFacadeException;
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
    private long billingProjectId;

    private long studioSubTypeId;

    private long catalogId = -1;

    public long getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(long catalogId) {
        this.catalogId = catalogId;
    }

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
    public String getProjectStudioContestFee() throws Exception {
        Map<String, Double> result = new HashMap<String, Double>();
        result.put("fee", getProjectStudioContestFeeInternal(billingProjectId, studioSubTypeId));
        setResult(result);
        return SUCCESS;
    }

    /**
     * <p>
     * Gets project studio contest fee. It is the internal handling function.
     * </p>
     *
     * @param billingProjectId the billing project id
     * @param studioSubTypeId studio sub type id
     * @return contest fee &gt;0 if it exists
     * @throws AdminServiceFacadeException if any error occurs
     */
    private double getProjectStudioContestFeeInternal(long billingProjectId, long studioSubTypeId)
        throws AdminServiceFacadeException {
        if (billingProjectId <= 0) {
            return -1;
        }

        TCSubject tcSubject = DirectStrutsActionsHelper.getTCSubjectFromSession();

        List<ProjectContestFee> contestFees = getAdminServiceFacade().getContestFeesByProject(tcSubject,
            billingProjectId);
        for (ProjectContestFee contestFee : contestFees) {
            if (contestFee.isStudio() && contestFee.getContestTypeId() == studioSubTypeId) {
                return contestFee.getContestFee();
            }
        }

        return -1;
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
     * @return the studioSubTypeId
     */
    public long getStudioSubTypeId() {
        return studioSubTypeId;
    }

    /**
     * @param studioSubTypeId the studioSubTypeId to set
     */
    public void setStudioSubTypeId(long studioSubTypeId) {
        this.studioSubTypeId = studioSubTypeId;
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
     * Gets the categories.
     * </p>
     *
     * @return the categories
     */
    public String getCategories() {
        setResult(getReferenceDataBean().getCatalogToCategoriesMap().get(catalogId));
        return SUCCESS;
    }
}
