/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.payments;


/**
 * <p>
 * This class retrieves paypal balance amount.
 * </p>
 * 
 * @author TCSASSEMBLER
 * @version 1.0 (Module Assembly - TopCoder Direct Member Payments Dashboard Backend Assembly)
 * @since 1.0
 */
public interface PayPalService {

	/**
	 * Get balance amount.
	 * 
	 * @return
	 * 			balance amount
	 * @throws ServiceException
	 * 			if any error occurs
	 */
	public double getBalanceAmount() throws ServiceException;
}
