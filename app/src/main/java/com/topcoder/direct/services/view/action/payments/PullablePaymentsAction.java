/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.payments;


import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.commons.utils.ValidationUtility;
import com.topcoder.direct.services.payments.entities.PaymentBalance;
import com.topcoder.direct.services.payments.entities.PaymentDiff;
import com.topcoder.direct.services.payments.entities.PullablePayments;
import com.topcoder.direct.services.payments.ConfigurationException;
import com.topcoder.direct.services.payments.PullablePaymentsService;
import com.topcoder.direct.services.payments.ServiceException;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>This action is used to fetch pullable payments data.</p>
 *
 * <p>
 * <strong>Thread safety</strong>: This class is not thread-safe. However, it will be
 * used in a thread safe manner.
 * </p>
 *
 * @author TCSASSEMBLER
 * @version  1.0 (System Assembly - TopCoder Direct Member Payments Dashboard v1.0)
 */
public class PullablePaymentsAction extends BaseAction {
    /**
     * Represent the class name.
     */
    private static final String CLASS_NAME = PullablePaymentsAction.class.getName();

    /**
     * The pullable payments service.
     */
    private PullablePaymentsService pullablePaymentsService;

    /**
     * Check configurations.
     *
     * @throws ConfigurationException if pullable payments service is null or logger is null.
     */
    @PostConstruct
    public void checkConfiguration() {
        super.checkConfiguration();
        ValidationUtility.checkNotNull(pullablePaymentsService, "pullablePaymentsService",
                ConfigurationException.class);
    }

    /**
     * Default constructor.
     */
    public PullablePaymentsAction() {
        // Empty Constructor.
    }

    /**
     * Handle the request, fetch data and set to result.
     *
     * @throws PaymentActionException if any exception occurs while fetching data by service.
     */
    public void executeAction() throws PaymentActionException {
        final String signature = CLASS_NAME + ".executeAction()";
        LoggingWrapperUtility.logEntrance(getLogger(), signature, null, null);

        try {
            // fetch payment balance data & pullable payment data
            PaymentBalance paymentBalance = pullablePaymentsService.getBalanceAmount();
            PullablePayments pullablePayments = pullablePaymentsService.getPullablePayments();

            // calculate payment diff
            PaymentDiff paymentDiff = new PaymentDiff();
            paymentDiff.setPaypalDiffAmount(paymentBalance.getPaypalBalance()
                    - pullablePayments.getPaypalPayments());
            paymentDiff.setPayoneerDiffAmount(paymentBalance.getPayoneerBalance()
                    - pullablePayments.getPayoneerPayments());
            paymentDiff.setWesternUnionDiffAmount(paymentBalance.getWesternUnionBalance()
                    - pullablePayments.getWesternUnionPayments());
            paymentDiff.setNotSetDiffAmount(paymentBalance.getNotSetBalance()
                    - pullablePayments.getNotSetPayments());

            // set to result
            Map<String, Object> result = new HashMap<String, Object>();
            result.put("paymentBalance", paymentBalance);
            result.put("pullablePayments", pullablePayments);
            result.put("paymentDiff", paymentDiff);

            setResult(result);
            LoggingWrapperUtility.logExit(getLogger(), signature, null);
        } catch (ServiceException e) {
            throw  LoggingWrapperUtility.logException(getLogger(), signature,
                    new PaymentActionException("Fail to fetch pullable payments", e));
        }
    }

    /**
     * Set pullable payments service.
     *
     * @param pullablePaymentsService the pullable payment service.
     */
    public void setPullablePaymentsService(PullablePaymentsService pullablePaymentsService) {
        this.pullablePaymentsService = pullablePaymentsService;
    }

}

