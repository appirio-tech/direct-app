/*
 * Copyright (C) 2010 - 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.accounting;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.topcoder.direct.services.view.util.ContestFeeAuthorizationProvider;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;

/**
 * This Interceptor will be invoked whenever user invokes contestFee struct actions.
 * 
 * Thread safety: The class is mutable and not thread safe. But it'll not caused thread safety issue if used under
 * Spring container.
 * 
 * @author winstips, TCSDEVELOPER
 * @version 1.0
 */
public class ContestFeeAccessInterceptor implements Interceptor {
    /**
     * Denotes the instance of ContestFeeAuthorizationProvider. It is managed with a getter and setter. It may have any
     * value. It is fully mutable.
     */
    private ContestFeeAuthorizationProvider contestFeeAuthorizationProvider;
    /**
     * Represents the logger instance. It is managed with a getter and setter. It may have any value. It is fully
     * mutable.
     */
    private Log logger;

    /**
     * Default Constructor.
     */
    public ContestFeeAccessInterceptor() {
    }

    /**
     * Process the intercept logic.
     * 
     * @param actionInvocation
     *            - the given instance to check.
     * @return intercept result.
     * @throws Exception
     *             if there is any exception.
     */
    public String intercept(ActionInvocation actionInvocation) throws Exception {
        if (actionInvocation == null) {
            throw new IllegalArgumentException("actionInvocation should not be null.");
        }
        long currentUserId = DirectUtils.getTCSubjectFromSession().getUserId();
        if (! this.contestFeeAuthorizationProvider.isUserGrantedAccessToContestFee(currentUserId)) {
            return "permissionDenied";
        } else {
            return actionInvocation.invoke();

        }
    }

    /**
     * Returns the contestFeeAuthorizationProvider field value.
     * 
     * @return contestFeeAuthorizationProvider field value.
     */
    public ContestFeeAuthorizationProvider getContestFeeAuthorizationProvider() {
        return this.contestFeeAuthorizationProvider;
    }

    /**
     * Sets the given value to contestFeeAuthorizationProvider field.
     * 
     * @param contestFeeAuthorizationProvider
     *            - the given value to set.
     */
    public void setContestFeeAuthorizationProvider(ContestFeeAuthorizationProvider contestFeeAuthorizationProvider) {
        this.contestFeeAuthorizationProvider = contestFeeAuthorizationProvider;
    }

    /**
     * Returns the logger field value.
     * 
     * @return logger field value.
     */
    public Log getLogger() {
        return logger;
    }

    /**
     * Sets the corresponding member field
     * 
     * @param loggerName
     *            - the given name to set.
     */
    public void setLoggerName(String loggerName) {
        if (loggerName == null) {
            this.logger = null;
        } else {
            this.logger = LogManager.getLog(loggerName);
        }
    }

    /**
     * Implements the method. Doing nothing.
     */
    public void destroy() {

    }

    /**
     * Implements the method. Doing nothing.
     */
    public void init() {

    }
}
