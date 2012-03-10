/*
 * Copyright (C) 2011 - 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.accounting;

import com.opensymphony.xwork2.Preparable;
import com.topcoder.clients.model.BillingAccount;
import com.topcoder.clients.model.ProjectContestFee;
import com.topcoder.clients.model.ProjectContestFeePercentage;
import com.topcoder.clients.model.SearchResult;
import com.topcoder.direct.services.view.action.contest.launch.DirectStrutsActionsHelper;
import com.topcoder.security.TCSubject;
import com.topcoder.util.log.Level;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * An action to be used for handling the requests for creating new contest fees.
 * </p>
 * <p>
 * Versions 1.1 (Module Assembly - Contest Fee Based on % of Member Cost Admin Part)
 * <ol>
 * <li>Updated methods validate and executeAction to support percentage based contest fee.</li>
 * <li>Moved method body of validate to super class BaseContestFeeAction#validateFormData.</li>
 * <ol>
 * </p>
 * 
 * @author isv, minhu
 * @version 1.1
 */
public class CreateContestFeesAction extends CreateFeesHomeAction implements Preparable {
    /**
     * <p>
     * Constructs new <code>CreateContestFeesAction</code> instance. This implementation does nothing.
     * </p>
     */
    public CreateContestFeesAction() {
    }

    /**
     * <p>
     * Handles the incoming request.
     * </p>
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
     * <p>
     * Implements the logic for preparing the action for creating contest fees.
     * </p>
     * 
     * @throws Exception if an unexpected error occurs.
     * @since 1.1
     */
    public void prepare() throws Exception {
        SearchResult<BillingAccount> accountSearchResult =
            getContestFeeService().search(false, Integer.MAX_VALUE, 1, "start_date", "DESC");
        Map<Long, String> accounts = new LinkedHashMap<Long, String>();
        for (BillingAccount account : accountSearchResult.getItems()) {
            accounts.put(account.getProjectId(), account.getName());
        }
        setBillingAccountsWithNoFees(accounts);

        if (getFormData() == null) {
            setFormData(getBillingAccount(getProjectId()));
        }
    }

    /**
     * <p>
     * Handles the incoming request. Creates new contest fees and contest fee percentage.
     * </p>
     * 
     * @throws Exception if an unexpected error occurs.
     */
    @Override
    protected void executeAction() throws Exception {
        // create new contest fees
        List<ProjectContestFee> fees = getFormData().getContestFees();
        getContestFeeService().save(fees);

        // create the percentage
        ProjectContestFeePercentage percentage = new ProjectContestFeePercentage();
        percentage.setActive(!getFormData().isContestFeeFixed());
        if (percentage.isActive()) {
            percentage.setContestFeePercentage(getFormData().getContestFeePercentage());
        }
        percentage.setProjectId(getProjectId());
        
        TCSubject tcSubject = DirectStrutsActionsHelper.getTCSubjectFromSession();
        String userId = String.valueOf(tcSubject.getUserId());
        percentage.setCreateUser(userId);
        percentage.setModifyUser(userId);
        Date date = new Date();
        percentage.setCreateDate(date);
        percentage.setModifyDate(date);
        
        getContestFeePercentageService().create(percentage);
    }
}
