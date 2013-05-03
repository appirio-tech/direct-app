/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.payments;

import com.topcoder.direct.services.payments.entities.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * This service is responsible for providing member payments by status,
 * potential member payments and payment history.
 * </p>
 *
 * <p>
 * Version 1.1 (System Assembly - TopCoder Direct Member Payments Dashboard v1.0)
 * - update method{@link #getPotentialMemberPayments(java.util.Date, java.util.Date, int[])}
 *      to accept multiple payment method ids.
 * </p>
 *
 * @author TCSASSEMBLER, tangzx
 * @version 1.1 (Module Assembly - TopCoder Direct Member Payments Dashboard
 *          Backend Assembly)
 * @since 1.0
 */
public interface PaymentsService {

    /**
     * <p>
     * This method is responsible for retrieving payments by status for given
     * days.
     * </p>
     * 
     * @param startDate
     *            start date
     * @param endDate
     *            end date
     * @return {@link PaymentsByStatus} items.
     * @throws ServiceException
     *             if a generic error occurs
     */
    public List<PaymentsByStatusResult> getPaymentsByStatus(Date startDate, Date endDate)
        throws ServiceException;

    /**
     * <p>
     * This method is responsible for retrieving member potential payments for
     * given start and end date.
     * </p>
     *
     * @param endDate
     *            end date
     * @param paymentMethods
     *            payment methods
     * @return Map of {@link Integer} to {@link CashOutflowPotentialResult} instances.
     * @throws ServiceException
     *             if a generic error occurs
     */
    public Map<Integer, CashOutflowPotentialResult> getPotentialMemberPayments(Date endDate,
                                                                               int[] paymentMethods)
        throws ServiceException;

    /**
     * <p>
     * This method is responsible for retrieving payment history given
     * PaymentHistoryCriteria instance.
     * </p>
     * 
     * @param criteria
     *            search criteria
     * @return list of {@link PaymentHistoryResult} instances.
     * @throws ServiceException
     *             if a generic error occurs
     */
    public List<PaymentHistoryResult> getPaymentHistory(PaymentHistoryCriteria criteria)
        throws ServiceException;
}
