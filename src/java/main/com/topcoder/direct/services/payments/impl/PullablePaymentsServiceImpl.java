/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.payments.impl;

import javax.annotation.PostConstruct;

import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.direct.services.payments.entities.PaymentBalance;
import com.topcoder.direct.services.payments.entities.PullablePayments;
import com.topcoder.direct.services.payments.ConfigurationException;
import com.topcoder.direct.services.payments.PayPalService;
import com.topcoder.direct.services.payments.PayoneerService;
import com.topcoder.direct.services.payments.PersistenceException;
import com.topcoder.direct.services.payments.PullablePaymentsService;
import com.topcoder.direct.services.payments.ServiceException;
import com.topcoder.direct.services.view.util.DataProvider;

/**
 * <p>
 * This service is responsible for providing member pullable payments. It uses
 * the DataProvider(Query Tool) to perform db operations.
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
public class PullablePaymentsServiceImpl extends BaseService implements PullablePaymentsService {

    /**
     * Represents the name of this class.
     */
    private static final String CLASS_NAME = "PullablePaymentsServiceImpl";

    /**
     * Stands for the PayPal service.
     */
    private PayPalService paypalService;

    /**
     * Stands for the Payoneer service.
     */
    private PayoneerService payoneerService;

    /**
     * <p>
     * This method is responsible for checking aggregate members are initialized
     * or not. If any member is null, then it will throw ConfigugationException
     * </p>
     * 
     * @throws ConfigurationException
     *             if some error occurred during configuration
     */
    @PostConstruct
    protected void checkConfiguration() {
        if (null == payoneerService) {
            throw new ConfigurationException("Payoneer service is not set correctly.");
        }
        if (null == paypalService) {
            throw new ConfigurationException("Paypal service is not set correctly.");
        }
    }

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
        throws ServiceException {
        final String signature = CLASS_NAME + "#getBalanceAmount()";

        LoggingWrapperUtility.logEntrance(getLogger(), signature, new String[] {}, new Object[] {});
        PaymentBalance paymentBalance = new PaymentBalance();
        paymentBalance.setWesternUnionBalance(0.0);
        paymentBalance.setNotSetBalance(0.0);

        try {
            paymentBalance.setPaypalBalance(paypalService.getBalanceAmount());
        } catch (ServiceException e) {
            paymentBalance.setPaypalBalance(0);
            LoggingWrapperUtility.logException(getLogger(), signature, new ServiceException(
                "Cannot get Paypal balance amount", e));
        }
        try {
            paymentBalance.setPayoneerBalance(payoneerService.getBalanceAmount());
        } catch (ServiceException e) {
            paymentBalance.setPayoneerBalance(0);
            LoggingWrapperUtility.logException(getLogger(), signature, new ServiceException(
                "Cannot get Payoneer balance amount", e));
        }
        LoggingWrapperUtility.logExit(getLogger(), signature, new Object[] {paymentBalance});
        return paymentBalance;
    }

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
        throws ServiceException {
        final String signature = CLASS_NAME + "#getPullablePayments()";

        LoggingWrapperUtility.logEntrance(getLogger(), signature, new String[] {}, new Object[] {});

        try {
            PullablePayments payments = DataProvider.getPullablePayments();
            LoggingWrapperUtility.logExit(getLogger(), signature, new Object[] {payments});
            return payments;
        } catch (Exception e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature, new PersistenceException(
                "An error occurs while retrieving pullable payments.", e));
        }
    }

    /**
     * <p>
     * Getter of paypalService field.
     * </p>
     * 
     * @return the paypalService
     */
    public PayPalService getPaypalService() {
        return paypalService;
    }

    /**
     * <p>
     * Setter of paypalService field.
     * </p>
     * 
     * @param paypalService
     *            the paypalService to set
     */
    public void setPaypalService(PayPalService paypalService) {
        this.paypalService = paypalService;
    }

    /**
     * <p>
     * Getter of payoneerService field.
     * </p>
     * 
     * @return the payoneerService
     */
    public PayoneerService getPayoneerService() {
        return payoneerService;
    }

    /**
     * <p>
     * Setter of payoneerService field.
     * </p>
     * 
     * @param payoneerService
     *            the payoneerService to set
     */
    public void setPayoneerService(PayoneerService payoneerService) {
        this.payoneerService = payoneerService;
    }

}
