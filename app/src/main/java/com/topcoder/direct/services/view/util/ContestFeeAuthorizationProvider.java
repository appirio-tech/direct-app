/*
 * Copyright (C) 2010 - 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.util;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.topcoder.security.TCSubject;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;

/**
 * This class is responsible for performing user authorization.
 * 
 * Thread safety: The class is mutable and not thread safe. But it'll not caused thread safety issue if used under
 * Spring container.
 * 
 * <p>
 * Version 1.1 (Release Assembly - Project Contest Fee Management ) Change notes:
 *   <ol>
 *     <li>{@link #isUserGrantedAccessToContestFee(long)} method is changed to check the user is TC admin or TC Accounting.</li>
 *   </ol>
 * </p>
 * 
 * @author winstips, TCSDEVELOPER
 * @version 1.0
 */
public class ContestFeeAuthorizationProvider {
    /**
     * Represents the log instance which is used for logging. It is managed with a getter and setter. It may have any
     * value. It is fully mutable.
     */
    private Log logger;

    /**
     * Default Constructor.
     */
    public ContestFeeAuthorizationProvider() {
    }

    /**
     * Return true if the user is TC Admin or TC Accounting, false otherwise.
     * 
     * @param currentUserId
     *            - the given user id.
     * @return checked result.
     */
    public boolean isUserGrantedAccessToContestFee(long currentUserId) {
        TCSubject tcSubject = DirectUtils.getTCSubjectFromSession();
        return DirectUtils.isSuperAdmin(tcSubject) || DirectUtils.isTCAccounting(tcSubject);
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
}
