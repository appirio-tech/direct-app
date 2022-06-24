/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.direct.services.view.action.contest.launch;

import com.topcoder.service.payment.PaymentResult;

/**
 * <p>
 * This is the base class for the payment actions. It holds the main logic. It extends
 * <code>StudioOrSoftwareContestAction</code>.
 * </p>
 *
 * <p>
 * <b>Thread safety:</b> The class is not thread safe because it's mutable and the values of this class
 * will change based on the request parameters. It's not required to be thread safe because in Struts 2 the
 * actions (different from Struts 1) are created for every request.
 * </p>
 *
 * @author fabrizyo, TCSDEVELOPER
 * @version 1.0
 */
public abstract class PayContestAction extends StudioOrSoftwareContestAction {

    /**
     * Default constructor, creates new instance.
     */
    protected PayContestAction() {
    }

    /**
     * Processes the payment using the <code>processPayment</code> method.
     *
     * @throws IllegalStateException if <code>contestServiceFacade</code> hasn't been injected
     * @throws Exception if some error occurs during method execution
     */
    @Override
    public void executeAction() throws Exception {
        ActionHelper.checkInjectedFieldNull(getContestServiceFacade(), "contestServiceFacade");
        sendEmail(processPayment());
    }

    /**
     * Processes the payment.
     *
     * @throws Exception if some error occurs during method execution
     * @return the payment result
     */
    protected abstract PaymentResult processPayment() throws Exception;

    /**
     * Sends the email when the payment is finished.
     *
     * @param paymentResult the payment result to use when sending the email
     */
    protected abstract void sendEmail(PaymentResult paymentResult);
}
