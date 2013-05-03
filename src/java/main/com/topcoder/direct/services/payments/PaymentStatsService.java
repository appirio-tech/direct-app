/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.payments;

import java.util.List;

import com.topcoder.direct.services.payments.entities.PaymentTrend;
import com.topcoder.direct.services.payments.entities.PaymentTrendSearchCriteria;
import com.topcoder.direct.services.payments.entities.TopMemberPayment;
import com.topcoder.direct.services.payments.entities.TopMemberPaymentCriteria;

/**
 * <p>
 * This service is responsible for providing member payments statistics.
 * </p>
 * 
 * @author TCSASSEMBLER
 * @version 1.0 (Module Assembly - TopCoder Direct Member Payments Dashboard
 *          Backend Assembly)
 * @since 1.0
 */
public interface PaymentStatsService {

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
        throws ServiceException;

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
        throws ServiceException;
}
