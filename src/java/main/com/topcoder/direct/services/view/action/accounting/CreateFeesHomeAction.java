/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.accounting;

import com.topcoder.clients.model.BillingAccount;
import com.topcoder.clients.model.SearchResult;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <p>An action to be used for servicing the requests for viewing the page for contest fees creation.</p>
 * 
 * @author isv
 * @version 1.0 (Release Assembly - Project Contest Fees Management Update 1 Assembly)
 */
public class CreateFeesHomeAction extends BaseContestFeeAction {

    /**
     * <p>A <code>SearchResult</code> providing the billing accounts which do not have contest fees set yet.</p>
     */
    private Map<Long, String> billingAccountsWithNoFees;

    /**
     * <p>Constructs new <code>CreateFeesHomeAction</code> instance. This implementation does nothing.</p>
     */
    public CreateFeesHomeAction() {
    }

    /**
     * <p>Handles the incoming request.</p>
     * 
     * @throws Exception if an unexpected error occurs.
     */
    @Override
    protected void executeAction() throws Exception {
        SearchResult<BillingAccount> accountSearchResult =
            getContestFeeService().search(false, Integer.MAX_VALUE, 1, "start_date", "DESC");
        Map<Long, String> accounts = new LinkedHashMap<Long, String>();
        for (BillingAccount account : accountSearchResult.getItems()) {
            accounts.put(account.getProjectId(), account.getName());
        }
        setBillingAccountsWithNoFees(accounts);

        if (getFormData() == null) {
            setFormData(getBillingAccount(-1));
        }
    }

    /**
     * <p>Gets the billing accounts which do not have contest fees set yet.</p>
     *
     * @return a <code>Map</code> providing the billing accounts which do not have contest fees set yet.
     */
    public Map<Long, String> getBillingAccountsWithNoFees() {
        return this.billingAccountsWithNoFees;
    }

    /**
     * <p>Sets the billing accounts which do not have contest fees set yet.</p>
     *
     * @param billingAccountsWithNoFees a <code>Map</code> providing the billing accounts which do not have
     *                                  contest fees set yet.
     */
    public void setBillingAccountsWithNoFees(Map<Long, String> billingAccountsWithNoFees) {
        this.billingAccountsWithNoFees = billingAccountsWithNoFees;
    }
}
