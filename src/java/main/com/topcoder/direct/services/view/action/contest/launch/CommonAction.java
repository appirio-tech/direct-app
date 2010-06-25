/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.contest.launch;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.topcoder.clients.model.ProjectContestFee;
import com.topcoder.direct.services.configs.ConfigUtils;
import com.topcoder.security.TCSubject;
import com.topcoder.service.facade.admin.AdminServiceFacadeException;

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
     */
    public String getStudioConfigs() {
        Map<String, Object> configs = new HashMap<String, Object>();
        configs.put("overview", ConfigUtils.getStudioOverviews());
        configs.put("fees", ConfigUtils.getStudioContestFees());
        configs.put("fileTypes", ConfigUtils.getFileTypes().getFileTypes());
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
}
