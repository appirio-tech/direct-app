/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.payments.actions;

import com.topcoder.commons.utils.ValidationUtility;
import com.topcoder.direct.payments.services.ConfigurationException;
import com.topcoder.direct.services.view.action.AbstractAction;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;

import javax.annotation.PostConstruct;
import java.util.Calendar;
import java.util.Date;

/**
 * <p>This class is the base action of payment actions, used to hold
 * some common fields and functions.</p>
 *
 * <p>
 * <strong>Thread safety</strong>: This class is not thread-safe. However, it will be
 * used in a thread safe manner.
 * </p>
 *
 * @author TCSASSEMBLER
 * @version  1.0 (System Assembly - TopCoder Direct Member Payments Dashboard v1.0)
 */
public abstract class BaseAction extends AbstractAction {
    /**
     * The logger used to perform logging. It is not null after initialization.
     */
    private Log logger;

    /**
     * Create the instance.
     */
    protected BaseAction() {
        // Empty Constructor.
    }

    /**
     * Check configurations.
     *
     * @throws ConfigurationException if logger is null.
     */
    @PostConstruct
    public void checkConfiguration() {
        ValidationUtility.checkNotNull(getLogger(), "logger", ConfigurationException.class);
    }

    /**
     * <p>
     * Executes the action.
     * </p>
     * <p>
     * It uses <b>Template Method</b> Design Pattern to perform the logic of the concrete action. Each concrete action
     * should implement the {@link #executeAction()} method. If some error occurs then set the exception with the
     * {@link #RESULT_KEY} key to the model.
     * </p>
     *
     * @return <code>SUCCESS</code> always
     * @throws Exception if any exception occurs and the request is not a json request.
     */
    @Override
    public String execute() throws Exception {
        try {
            executeAction();
        } catch (Throwable e) {
            logger.log(Level.ERROR, "Error when executing action : " + getAction() + " : " + e.getMessage(), e);

            if (isJsonRequest()) {
                setResult(e);
            } else {
                throw new Exception(e);
            }
        }
        return SUCCESS;
    }

    /**
     * <p>
     * This is the template method where the action logic will be performed by children classes.
     * </p>
     *
     * @throws PaymentActionException if any error occurs
     */
    protected abstract void executeAction() throws PaymentActionException;

    /**
     * Get the date with specify day diff value.
     *
     * @param diffDay the day diff value
     * @return the date request
     */
    protected Date getDateWithDiffDay(int diffDay) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, diffDay);

        return calendar.getTime();
    }

    /**
     * Get the logger.
     *
     * @return the logger
     */
    protected Log getLogger() {
        return logger;
    }

    /**
     * Set the logger.
     *
     * @param logger the logger
     */
    public void setLogger(Log logger) {
        this.logger = logger;
    }
}

