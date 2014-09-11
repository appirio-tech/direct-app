/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.project;

import com.topcoder.security.facade.LogOutException;
import com.topcoder.security.facade.LogoutHandler;

/**
 * <p>
 * MockLogoutHandler for test use.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockLogoutHandler implements LogoutHandler {

    /**
     * <p>
     * Represents if the MockLogoutHandler is bad. If true, LogOutException will be thrown in logOut.
     * </p>
     */
    public static final String LOGOUT_EXCEPTION = "logout_exception";

    /**
     * Represents if the MockLogoutHandler login success. If false logOut will return false.
     */
    public static final String LOGOUT_FALSE_USER = "logout_false_user";

    /**
     * <p>
     * Default constructor, do nothing.
     * </p>
     */
    public MockLogoutHandler() {

    }

    /**
     * Log out an user.Do nothing in this class.
     * @param user
     *            the user to log out
     * @return true if the log out is successful and false otherwise
     * @throws LogOutException
     *             if the log out is bad is true
     * @throws IllegalArgumentException
     *             if user is null or trim to empty
     */
    public boolean logOut(String user) throws LogOutException {
        if (user.equals(LOGOUT_EXCEPTION)) {
            throw new LogOutException("LogOutException in MockLogoutHandler");
        }
        return (LOGOUT_FALSE_USER.equals(user)) ? false : true;
    }

}
