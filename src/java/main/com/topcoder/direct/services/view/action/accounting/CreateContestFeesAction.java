/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.accounting;

import com.opensymphony.xwork2.Preparable;
import com.topcoder.clients.model.BillingAccount;
import com.topcoder.clients.model.ProjectContestFee;
import com.topcoder.clients.model.SearchResult;
import com.topcoder.util.log.Level;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>An action to be used for handling the requests for creating new contest fees.</p>
 * 
 * @author isv
 * @version 1.0 (Release Assembly - Project Contest Fees Management Update 1 Assembly)
 */
public class CreateContestFeesAction extends CreateFeesHomeAction implements Preparable {

    /**
     * <p>Constructs new <code>CreateContestFeesAction</code> instance. This implementation does nothing.</p>
     */
    public CreateContestFeesAction() {
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
        Map<String, List<String>> map = getFieldErrors();
        if (getFieldErrors() != null && !getFieldErrors().isEmpty()) {
            for (String str : map.keySet()) {
                if (str != null && str.startsWith("formData.contestFees[")) {
                    try {
                        String str2 = str.replace("formData.contestFees[", "");
                        int pos = str2.indexOf("]");
                        int i = Integer.parseInt(str2.substring(0, pos));
                        String description = getFormData().getContestFees().get(i).getContestTypeDescription();
                        map.get(str).clear();
                        map.get(str)
                            .add("Invalid value for " + description + ". It should use non-negative Double type.");
                    } catch (Exception e) {
                        getLogger().log(Level.ERROR, e);
                    }
                }
            }
        }
        // Validate against negative fees
        List<ProjectContestFee> contestFees = getFormData().getContestFees();
        for (int i = 0; i < contestFees.size(); i++) {
            ProjectContestFee fee = contestFees.get(i);
            if (fee.getContestFee() < 0) {
                addFieldError("formData.contestFees[" + i + "].fee", "Value for " + fee.getContestTypeDescription()
                                                                     + " is negative");
            }
        }
    }

    /**
     * <p>Implements the logic for preparing the action for creating contest fees.</p>
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
     * <p>Handles the incoming request. Creates new contest fees.</p>
     * 
     * @throws Exception if an unexpected error occurs.
     */
    @Override
    protected void executeAction() throws Exception {
        // create new ones
        getContestFeeService().save(getFormData().getContestFees());
    }
}
