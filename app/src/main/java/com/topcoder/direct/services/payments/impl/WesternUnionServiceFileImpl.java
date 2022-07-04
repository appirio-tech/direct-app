/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.payments.impl;

import com.topcoder.direct.services.payments.ConfigurationException;
import com.topcoder.direct.services.payments.ServiceException;
import com.topcoder.direct.services.payments.WesternUnionService;
import org.apache.commons.io.FileUtils;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;

/**
 * <p>
 * This class retrieves western union balance amount.
 * </p>
 *
 * @author TCSASSEMBLER
 * @version 1.0
 */
public class WesternUnionServiceFileImpl extends BaseService  implements WesternUnionService {

    /**
     * Represents the balance file name for western union balance.
     */
    private String westernUnionBalanceFileName;

    @PostConstruct
    protected void checkConfiguration() throws ConfigurationException {
        if (null == westernUnionBalanceFileName) {
            throw new ConfigurationException("western union configuration is not correct. The 'westernUnionBalanceFileName' is null");
        }
    }
    /**
     * Get balance amount.
     *
     * @return
     *      balance amount
     * @throws ServiceException
     *      if any error occurs
     */
    public double getBalanceAmount() throws ServiceException {
        try {
            File file = new File(westernUnionBalanceFileName);
            if (file.exists()) {
                String content = FileUtils.readFileToString(file);

                if (content.trim().length() > 0) {
                    return Double.parseDouble(content);
                }
            }
            return 0.0;
        } catch (IOException e) {
            throw new ServiceException("Fail to read western union balance amount from file " + westernUnionBalanceFileName, e);
        }
    }

    /**
     * Set balance amount.
     *
     * @param  balanceAmount balance amount
     * @throws ServiceException
     *      if any error occurs
     */
    public synchronized void setBalanceAmount(double balanceAmount) throws ServiceException {
        try {
             FileUtils.writeStringToFile(new File(westernUnionBalanceFileName), String.valueOf(balanceAmount));
        } catch (IOException e ) {
            throw new ServiceException("Fail to write western union balance amount to file " + westernUnionBalanceFileName, e);
        }
    }

    /**
     * Sets western union balance fil name.
     *
     * @param westernUnionBalanceFileName  western union balance fil name.
     */
    public void setWesternUnionBalanceFileName(String westernUnionBalanceFileName) {
        this.westernUnionBalanceFileName = westernUnionBalanceFileName;
    }
}
