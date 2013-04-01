/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.payments.services.impl;

import java.util.Date;
import java.util.List;

import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.direct.payments.entities.PaymentTrend;
import com.topcoder.direct.payments.entities.PaymentTrendSearchCriteria;
import com.topcoder.direct.payments.entities.TopMemberPayment;
import com.topcoder.direct.payments.entities.TopMemberPaymentCriteria;
import com.topcoder.direct.payments.services.PaymentStatsService;
import com.topcoder.direct.payments.services.PersistenceException;
import com.topcoder.direct.payments.services.ServiceException;
import com.topcoder.direct.services.view.util.DataProvider;

/**
 * <p>
 * This service is responsible for providing member payments statistics.
 * </p>
 * 
 * <p>
 * <strong>Thread Safety: </strong> This class is mutable and thus is not thread
 * safe.
 * </p>
 * 
 * @author TCSASSEMBLER
 * @version 1.0 (Module Assembly - TopCoder Direct Member Payments Dashboard
 *          Backend Assembly)
 * @since 1.0
 */
public class PaymentStatsServiceImpl extends BaseService implements PaymentStatsService {

    /**
     * Stands for the name of this class.
     */
    private static final String CLASS_NAME = "PaymentStatsServiceImpl";

    /**
     * Default result limit while retrieving top member payments.
     */
    private int defaultResultLimit;

    /**
     * <p>
     * This method is responsible for retrieving payments by days given
     * {@link PaymentsTrendsByDaysCriteria} instance.
     * </p>
     * 
     * @param criteria
     *            search criteria
     * @return list of {@link PaymentTrend} instances.
     * @throws ServiceException
     *             if a generic error occurs
     */
    public List<PaymentTrend> getPaymentsTrends(PaymentTrendSearchCriteria criteria)
        throws ServiceException {
        final String signature = CLASS_NAME + "#getPaymentsTrends(criteria)";

        LoggingWrapperUtility.logEntrance(getLogger(), signature, new String[] {"criteria"}, new Object[] {criteria});

        checkNotNull(getLogger(), criteria, signature, "criteria");
        if (null == criteria.getEndDate()) {
            criteria.setEndDate(new Date());
        }
        checkStartAndEndDate(getLogger(), criteria.getStartDate(), criteria.getEndDate(), signature);
        try {
            List<PaymentTrend> trends = DataProvider.getPaymentsTrends(criteria);
            LoggingWrapperUtility.logExit(getLogger(), signature, new Object[] {trends.size()});
            return trends;
        } catch (Exception e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature, new PersistenceException(
                "Error occurs while retrieving payment trend data.", e));
        }
    }

    /**
     * <p>
     * This method is responsible for retrieving payments by days given
     * {@link PaymentsTrendsByWeeksCriteria} instance.
     * </p>
     * 
     * @param criteria
     *            search criteria
     * @return list of {@link TopMemberPayment} instances.
     * @throws ServiceException
     *             if a generic error occurs
     */
    public List<TopMemberPayment> getTopMemberPayments(TopMemberPaymentCriteria criteria)
        throws ServiceException {
        final String signature = CLASS_NAME + "#getTopMemberPayments(criteria)";

        LoggingWrapperUtility.logEntrance(getLogger(), signature, new String[] {"criteria"}, new Object[] {criteria});

        if (null == criteria) {
            criteria = new TopMemberPaymentCriteria();
        }
        if (criteria.getResultLimit() <= 0) {
            criteria.setResultLimit(defaultResultLimit);
        }

        try {
            List<TopMemberPayment> memberPayments = DataProvider.getTopMemberPayments(criteria);
            LoggingWrapperUtility.logExit(getLogger(), signature, new Object[] {memberPayments.size()});
            return memberPayments;
        } catch (Exception e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature, new PersistenceException(
                "Error was occurred while retrieving member payment data.", e));
        }
    }

    /**
     * <p>
     * Getter of defaultResultLimit field.
     * </p>
     * 
     * @return the defaultResultLimit
     */
    public int getDefaultResultLimit() {
        return defaultResultLimit;
    }

    /**
     * <p>
     * Setter of defaultResultLimit field.
     * </p>
     * 
     * @param defaultResultLimit
     *            the defaultResultLimit to set
     */
    public void setDefaultResultLimit(int defaultResultLimit) {
        this.defaultResultLimit = defaultResultLimit;
    }

    /**
     * <p>
     * This method does nothing since there is no any dependent parameters that
     * prevent this service incorrect work.
     * </p>
     */
    @Override
    protected void checkConfiguration() {
       // does nothing
    }

}
