/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.payments;

import com.topcoder.commons.utils.LoggingWrapperUtility;

/**
 * <p>This class is the action used for payment overview page.</p>
 *
 * <p>
 * <strong>Thread safety</strong>: This class is not thread-safe. However, it will be
 * used in a thread safe manner.
 * </p>
 *
 * @author TCSASSEMBLER
 * @version  1.0 (System Assembly - TopCoder Direct Member Payments Dashboard v1.0)
 */
public class PaymentOverviewAction extends BaseAction {
    /**
     * Represent the class name.
     */
    private static final String CLASS_NAME = PaymentOverviewAction.class.getName();

    /**
     * Handle the request, fetch data and set to result.
     *
     * @throws PaymentActionException don't throw for now
     */
    @Override
    protected void executeAction() throws PaymentActionException {
        final String signature = CLASS_NAME + ".executeAction()";
        LoggingWrapperUtility.logEntrance(getLogger(), signature, null, null);

        // do nothing here, but we still add it, will be used in future
        // to fetch data used by filter, etc.

        LoggingWrapperUtility.logExit(getLogger(), signature, null);
    }
}
