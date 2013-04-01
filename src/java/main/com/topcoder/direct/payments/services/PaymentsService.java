/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.payments.services;

import java.util.Date;
import java.util.List;

import com.topcoder.direct.payments.entities.CashOutflowPotentialResult;
import com.topcoder.direct.payments.entities.PaymentHistoryCriteria;
import com.topcoder.direct.payments.entities.PaymentHistoryResult;
import com.topcoder.direct.payments.entities.PaymentsByStatus;
import com.topcoder.direct.payments.entities.PaymentsByStatusResult;

/**
 * <p>
 * This service is responsible for providing member payments by status,
 * potential member payments and payment history.
 * </p>
 * 
 * @author TCSASSEMBLER
 * @version 1.0 (Module Assembly - TopCoder Direct Member Payments Dashboard
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
     * @param startDate
     *            start date
     * @param endDate
     *            end date
     * @return list of {@link CashOutflowPotentialResult} instances.
     * @throws ServiceException
     *             if a generic error occurs
     */
    public List<CashOutflowPotentialResult> getPotentialMemberPayments(Date startDate, Date endDate)
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
