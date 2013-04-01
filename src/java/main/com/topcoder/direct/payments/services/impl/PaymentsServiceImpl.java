/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.payments.services.impl;

import java.util.Date;
import java.util.List;

import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.direct.payments.entities.CashOutflowPotentialResult;
import com.topcoder.direct.payments.entities.PaymentHistoryCriteria;
import com.topcoder.direct.payments.entities.PaymentHistoryResult;
import com.topcoder.direct.payments.entities.PaymentsByStatus;
import com.topcoder.direct.payments.entities.PaymentsByStatusResult;
import com.topcoder.direct.payments.services.ConfigurationException;
import com.topcoder.direct.payments.services.PaymentsService;
import com.topcoder.direct.payments.services.PersistenceException;
import com.topcoder.direct.payments.services.ServiceException;
import com.topcoder.direct.services.view.util.DataProvider;

/**
 * <p>
 * This service is responsible for providing member payments by status,
 * potential member payments and payment history.
 * </p>
 * 
 * <p>
 * <strong>Thread Safety: </strong> This class is immutable and thus is thread
 * safe.
 * </p>
 * 
 * @author TCSASSEMBLER
 * @version 1.0 (Module Assembly - TopCoder Direct Member Payments Dashboard
 *          Backend Assembly)
 * @since 1.0
 */
public class PaymentsServiceImpl extends BaseService implements PaymentsService {

    /**
     * Stands for the name of this class.
     */
    private static final String CLASS_NAME = "PaymentsServiceImpl";

    /**
     * The payment method ID.
     */
    private String paymentMethodID;

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
        throws ServiceException {
        final String signature = CLASS_NAME + "#getPaymentsByStatus(startDate, endDate)";

        LoggingWrapperUtility.logEntrance(getLogger(), signature, new String[] {"startDate", "endDate"}, new Object[] {
                startDate, endDate});
        if (null == endDate) {
            endDate = new Date();
        }
        checkStartAndEndDate(getLogger(), startDate, endDate, signature);
        try {
            List<PaymentsByStatusResult> payments = DataProvider.getPaymentsByStatus(startDate, endDate);
            LoggingWrapperUtility.logExit(getLogger(), signature, new Object[] {payments});
            return payments;
        } catch (Exception e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature, new PersistenceException(
                "Error was occurred while retrieving payments by status.", e));
        }
    }

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
        throws ServiceException {
        final String signature = CLASS_NAME + "#getPotentialMemberPayments(startDate, endDate)";

        LoggingWrapperUtility.logEntrance(getLogger(), signature, new String[] {"startDate", "endDate"}, new Object[] {
                startDate, endDate});
        if (null == endDate) {
            endDate = new Date();
        }
        checkStartAndEndDate(getLogger(), startDate, endDate, signature);
        try {
            List<CashOutflowPotentialResult> result = DataProvider.getPotentialMemberPayments(startDate, endDate,
                paymentMethodID);
            LoggingWrapperUtility.logExit(getLogger(), signature, new Object[] {result});
            return result;
        } catch (Exception e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature, new PersistenceException(
                "Error was occurred while retrieving member potential payments.", e));
        }
    }

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
        throws ServiceException {
        final String signature = CLASS_NAME + "#getPaymentHistory(criteria)";

        LoggingWrapperUtility.logEntrance(getLogger(), signature, new String[] {"criteria"}, new Object[] {criteria});
        checkNotNull(getLogger(), criteria, signature, "criteria");
        if (null == criteria.getEndDate()) {
            criteria.setEndDate(new Date());
        }
        checkStartAndEndDate(getLogger(), criteria.getStartDate(), criteria.getEndDate(), signature);
        try {
            List<PaymentHistoryResult> result = DataProvider.getPaymentHistory(criteria);
            LoggingWrapperUtility.logExit(getLogger(), signature, new Object[] {result});
            return result;
        } catch (Exception e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature, new PersistenceException(
                "Error was occurred while retrieving payment history.", e));
        }
    }

    /**
     * <p>
     * This method does nothing since there is no any dependent parameters that
     * prevent this service incorrect work.
     * </p>
     */
    @Override
    protected void checkConfiguration() {
        if (null == paymentMethodID) {
            throw new ConfigurationException(
                "Payments Service is not set correctly. Missing definition for 'paymentMethodID' parameter");
        }
    }

    /**
     * Sets paymentMethodID.
     * 
     * @param paymentMethodID
     *            the paymentMethodID to set
     */
    public void setPaymentMethodID(String paymentMethodID) {
        this.paymentMethodID = paymentMethodID;
    }

    /**
     * Gets paymentMethodID.
     * 
     * @return the paymentMethodID
     */
    public String getPaymentMethodID() {
        return paymentMethodID;
    }
}
