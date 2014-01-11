/*
 * Copyright (C) 2011 - 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.accounting;

import java.util.Date;
import java.util.List;

import com.opensymphony.xwork2.Preparable;
import com.topcoder.clients.model.ProjectContestFee;
import com.topcoder.clients.model.ProjectContestFeePercentage;
import com.topcoder.direct.services.view.action.contest.launch.DirectStrutsActionsHelper;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.security.TCSubject;
import com.topcoder.util.log.Level;

/**
 * <p>An action to be used for servicing the requests for updating the existing contest fees for selected billing
 * account.</p>
 * <p>
 * Versions 1.1 (Module Assembly - Contest Fee Based on % of Member Cost Admin Part)
 * <ol>
 * <li>Updated methods validate and executeAction to support percentage based contest fee.</li>
 * <li>Moved method body of validate to super class BaseContestFeeAction#validateFormData.</li>
 * <ol>
 * </p>
 *
 * <p>
 *     Version 1.2 (Release Assembly - TC Cockpit Bug Race Cost and Fees Part 1) change notes:
 *     <ol>
 *         <li>Updated {@link #executeAction()} to update the fixed bug race contest fee and percentage
 *         bug race contest fee for tc direct project when updating contest fees for a billing account.</li>
 *     </ol>
 * </p>
 *
 * <p>
 * Changes in version 1.3 (BUGR-10395 Cockpit Fixed Contest Fee Not Saved):
 * <ol>
 * 		<li>Update {@link #executeAction()} method to populate contest fee with project id.</li>
 * </ol>
 * </p>
 * @author isv, minhu, TCSASSEMBLER
 * @version 1.3
 */
public class SaveContestFeesAction extends BaseContestFeeAction implements Preparable {
    /**
     * <p>Constructs new <code>SaveContestFeesAction</code> instance. This implementation does nothing.</p>
     */
    public SaveContestFeesAction() {
    }

    /**
     * <p>Handles the incoming request.</p>
     * 
     * @return a <code>String</code> referencing the next view to be displayed to user.
     * @throws Exception if an unexpected error occurs.
     */
    @Override
    public String execute() throws Exception {
        if (getFieldErrors() != null && !getFieldErrors().isEmpty()) {
            return INPUT;
        } else {
            return super.execute();
        }
    }

    /**
     * Implements validate method.
     */
    public void validate() {
        validateFormData();
    }

    /**
     * Implements prepare save method.
     */
    public void prepare() {
        try {
            if (getFormData() == null) {
                setFormData(getBillingAccount(getProjectId()));
            }
        } catch (Exception e) {
            getLogger().log(Level.ERROR, e);
        }
    }

    /**
     * <p>Handles the incoming request. Saves/Updates contest fees and contest fee percentage.</p>
     * 
     * @throws Exception if an unexpected error occurs.
     */
    @Override
    protected void executeAction() throws Exception {
        // clean up the old contest fees first
        getContestFeeService().cleanUpContestFees(getProjectId());
        //populate with projectId
        List<ProjectContestFee> fees = getFormData().getContestFees();
        populateContestFeeData(fees, getProjectId());
        // create new contest fees
        getContestFeeService().save(fees);
        
        // get the old percentage
        ProjectContestFeePercentage percentage = getContestFeePercentageService().getByProjectId(getProjectId());        
        boolean exist = (percentage != null); 
        if (!exist) {        
            // create the percentage
            percentage = new ProjectContestFeePercentage();
            percentage.setProjectId(getProjectId());        
        }
        percentage.setActive(!getFormData().isContestFeeFixed());
        if (percentage.isActive()) {
            percentage.setContestFeePercentage(getFormData().getContestFeePercentage());
        } else {
            percentage.setContestFeePercentage(0);
        }
        
        TCSubject tcSubject = DirectStrutsActionsHelper.getTCSubjectFromSession();
        String userId = String.valueOf(tcSubject.getUserId());
        percentage.setModifyUser(userId);
        Date date = new Date();
        percentage.setModifyDate(date);
        
        
        // create or update the percentage
        if (exist) {
            getContestFeePercentageService().update(percentage);
        } else {
            percentage.setCreateUser(userId);
            percentage.setCreateDate(date);
            getContestFeePercentageService().create(percentage);
        }

        DirectUtils.updateBillingAccountDirectProjectsBugContestFees(tcSubject, getProjectId(),
                getProjectServiceFacade(), getContestFeeService(), getContestFeePercentageService(),
                getDirectProjectService());
    }
}
