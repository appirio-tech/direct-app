/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.payments;


/**
 * <p>
 * This class retrieves western union balance amount.
 * </p>
 *
 * @author TCSASSEMBLER
 * @version 1.0
 */
public interface WesternUnionService {

    /**
     * Get balance amount.
     *
     * @return
     *      balance amount
     * @throws ServiceException
     *      if any error occurs
     */
    public double getBalanceAmount() throws ServiceException;

    /**
     * Set balance amount.
     *
     * @param  balanceAmount balance amount
     * @throws ServiceException
     *      if any error occurs
     */
    public void setBalanceAmount(double balanceAmount) throws ServiceException;
}
