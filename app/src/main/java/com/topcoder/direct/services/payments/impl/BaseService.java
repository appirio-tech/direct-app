/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.payments.impl;

import java.util.Date;
import javax.annotation.PostConstruct;

import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.direct.services.payments.ConfigurationException;
import com.topcoder.direct.services.payments.ServiceException;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;

/**
 * <p>
 * This service is responsible for holding common data like logger. It
 * implements checkConfiguration method which will be check member logger
 * instance. All subclasses of this service should override this method to check
 * additional configuration.
 * </p>
 * 
 * @author TCSASSEMBLER
 * @version 1.0 (Module Assembly - TopCoder Direct Member Payments Dashboard
 *          Backend Assembly)
 * @since 1.0
 */
public abstract class BaseService {

    /**
     * Log to be used.
     */
    private final Log logger = LogManager.getLog(getClass().getName());

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
    protected abstract void checkConfiguration();

    /**
     * Check parameter not null.
     * 
     * @param logger
     *            the logger for logging the case when <code>obj</code> is
     *            <code>null</code>
     * @param obj
     *            object to be checked
     * @param signature
     *            log signature
     * @param paramName
     *            parameter name
     * @throws ServiceException
     *             if this object is null
     */
    protected void checkNotNull(Log logger, Object obj, String signature, String paramName)
        throws ServiceException {
        if (null == obj) {
            throw LoggingWrapperUtility.logException(logger, signature, new ServiceException(
                "Cannot retrieve data due to illegal argument.", new IllegalArgumentException(paramName
                    + " must be set.")));
        }
    }

    /**
     * Check start date and end date.
     * 
     * @param logger
     *            the logger for logging the case when parameters were incorrect
     * @param startDate
     *            start date
     * @param endDate
     *            end date
     * @param signature
     *            log signature
     * @throws ServiceException
     *             if parameters are not correct
     */
    protected void checkStartAndEndDate(Log logger, Date startDate, Date endDate, String signature)
        throws ServiceException {
        if (null == startDate) {
            throw LoggingWrapperUtility.logException(logger, signature,
                new ServiceException("Cannot retrieve data due to illegal argument.", new IllegalArgumentException(
                    "Start date is not set.")));
        }
        if (startDate.after(endDate)) {
            throw LoggingWrapperUtility.logException(logger, signature, new ServiceException(
                "Cannot retrieve data due to illegal argument.", new IllegalArgumentException(
                    "Start date must be before end date.")));
        }
    }

    /**
     * Gets logger.
     * 
     * @return the logger
     */
    protected Log getLogger() {
        return logger;
    }

}
