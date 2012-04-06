/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.analytics.view.security;

/**
 * <p>This interface is used to perform user authentication.</p>
 * 
 * @author flexme
 * @version 1.0
 */
public interface LoginProcessor {
    /**
     * Perform authentication for a user.
     * 
     * @param username the handle of the user.
     * @param password the password of the user.
     * @param rememberMe a flag indicates whether the login state of the user should be remembered. 
     * @return true if the login is successful, false otherwise. 
     */
    public boolean login(String username, String password, boolean rememberMe);
}
