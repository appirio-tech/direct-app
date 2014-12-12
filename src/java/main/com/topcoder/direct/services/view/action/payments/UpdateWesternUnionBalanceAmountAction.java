/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.payments;

import com.topcoder.direct.services.payments.WesternUnionService;
import com.topcoder.direct.services.view.action.BaseDirectStrutsAction;

/**
 * <p>A <code>Struts</code> action to be used for handling the requests for updating the western union balance amount.</p>
 *
 * @author TCSASSEMBER
 * @version 1.0
 */
public class UpdateWesternUnionBalanceAmountAction extends BaseDirectStrutsAction {

    /**
     * <p>A <code>double</code> providing the balance amount to be updated.</p>
     */
    private double balanceAmount;

    /**
     * Represents the western union service to set the balance amount
     */
    private WesternUnionService westernUnionService;

    /**
     * <p>Handles the incoming request. It will update the invoice.</p>
     *
     * @throws Exception if an unexpected error occurs.
     */
    @Override
    protected void executeAction() throws Exception {
        westernUnionService.setBalanceAmount(balanceAmount);
    }

    /**
     * <p>Sets the balance amount to be updated.</p>
     *
     * @param balanceAmount the the balance amount to be updated
     */
    public void setBalanceAmount(double balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    /**
     * <p>Sets the instance of <code>WesternUnionService</code>.</p>
     *
     * @param westernUnionService the instance of <code>WesternUnionService</code>
     */
    public void setWesternUnionService(WesternUnionService westernUnionService) {
        this.westernUnionService = westernUnionService;
    }

}
