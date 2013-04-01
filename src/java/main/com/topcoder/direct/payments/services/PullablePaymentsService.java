/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.payments.services;

import com.topcoder.direct.payments.entities.PaymentBalance;
import com.topcoder.direct.payments.entities.PullablePayments;

/**
 * <p>
 * This service is responsible for providing member pullable payments. It uses
 * the DataProvider(Query Tool) to perform db operations.
 * </p>
 * 
 * @author TCSASSEMBLER
 * @version 1.0 (Module Assembly - TopCoder Direct Member Payments Dashboard
 *          Backend Assembly)
 * @since 1.0
 */
public interface PullablePaymentsService {

    /**
     * <p>
     * This method is responsible for retrieving member payment balances for
     * each payment method.
     * </p>
     * 
     * @throws ServiceException
     *             if any error occurs
     * @return {@link PaymentBalance} instance.
     */
    public PaymentBalance getBalanceAmount()
        throws ServiceException;

    /**
     * <p>
     * This method is responsible for retrieving pullable payments.
     * </p>
     * 
     * @throws ServiceException
     *             if any error occurs
     * @return {@link PullablePayments} instance.
     */
    public PullablePayments getPullablePayments()
        throws ServiceException;
}
