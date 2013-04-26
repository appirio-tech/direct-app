/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.payments.actions;

import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.commons.utils.ValidationUtility;
import com.topcoder.direct.payments.entities.TopMemberPayment;
import com.topcoder.direct.payments.entities.TopMemberPaymentCriteria;
import com.topcoder.direct.payments.services.ConfigurationException;
import com.topcoder.direct.payments.services.PaymentStatsService;
import com.topcoder.direct.payments.services.ServiceException;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * <p>This action is used to fetch top member payments data.</p>
 *
 * <p>
 * <strong>Thread safety</strong>: This class is not thread-safe. However, it will be
 * used in a thread safe manner.
 * </p>
 *
 * @author TCSASSEMBLER
 * @version  1.0 (System Assembly - TopCoder Direct Member Payments Dashboard v1.0)
 */
public class TopMemberPaymentsStatsAction extends BaseAction {
    /**
     * Represent the class name.
     */
    private static final String CLASS_NAME = TopMemberPaymentsStatsAction.class.getName();

    /**
     * The default member payment criteria.
     */
    private static final TopMemberPaymentCriteria DEFAULT_TOP_MEMBER_PAYMENT_CRITERIA;

    /**
     * Initiate DEFAULT_TOP_MEMBER_PAYMENT_CRITERIA.
     */
    static {
        DEFAULT_TOP_MEMBER_PAYMENT_CRITERIA = new TopMemberPaymentCriteria();
        DEFAULT_TOP_MEMBER_PAYMENT_CRITERIA.setResultLimit(10);
        DEFAULT_TOP_MEMBER_PAYMENT_CRITERIA.setSortColumn("0");
        DEFAULT_TOP_MEMBER_PAYMENT_CRITERIA.setSortOrder("desc");
    }

    /**
     * The top member payment criteria.
     */
    private TopMemberPaymentCriteria topMemberPaymentCriteria;

    /**
     * The payment stats service.
     */
    private PaymentStatsService paymentStatsService;

    /**
     * Default constructor.
     */
    public TopMemberPaymentsStatsAction() {
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
            if (topMemberPaymentCriteria == null) {
                topMemberPaymentCriteria = DEFAULT_TOP_MEMBER_PAYMENT_CRITERIA;
            }

            // fetch top member payments data
            List<TopMemberPayment> topMemberPayments = paymentStatsService.
                    getTopMemberPayments(topMemberPaymentCriteria);
            setResult(topMemberPayments);

            LoggingWrapperUtility.logExit(getLogger(), signature, null);
        } catch (ServiceException e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature,
                    new PaymentActionException("Fail to get top member payment details", e));
        }
    }

    /**
     * Check configurations.
     *
     * @throws ConfigurationException if paymentStatsService is null or logger is null.
     */
    @PostConstruct
    public void checkConfiguration() {
        super.checkConfiguration();
        ValidationUtility.checkNotNull(paymentStatsService, "paymentStatsService",
                ConfigurationException.class);
    }

    /**
     * Set payment stats service.
     *
     * @param paymentStatsService the service to set.
     */
    public void setPaymentStatsService(PaymentStatsService paymentStatsService) {
        this.paymentStatsService = paymentStatsService;
    }

    /**
     * Set top member payment criteria.
     *
     * @param topMemberPaymentCriteria the criteria to set.
     */
    public void setTopMemberPaymentCriteria(TopMemberPaymentCriteria topMemberPaymentCriteria) {
        this.topMemberPaymentCriteria = topMemberPaymentCriteria;
    }
}

