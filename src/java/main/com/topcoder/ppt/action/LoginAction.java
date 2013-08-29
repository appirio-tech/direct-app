/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.ppt.action;

import java.util.HashMap;
import java.util.Map;

import com.topcoder.ppt.security.LoginProcessor;
import com.topcoder.ppt.util.Util;
import com.topcoder.security.TCSubject;

/**
 * <p>A <code>Struts</code> action to be used for handling requests for user login.</p>
 *
 * <p>
 * Changes in version 1.1 (Add User ID to JSON Result - BUGR-9450):
 * <ol>
 * 		<li>Update {@link #executeAction()} method.</li>
 * </ol>
 * </p>
 * @author flexme
 * @version 1.1
 */
public class LoginAction extends BaseAjaxAction {
    /**
     * Represents the serial version unique id.
     */
    private static final long serialVersionUID = -1858199098223662626L;

    /**
     * The <code>LoginProcessor</code> instance used to perform authentication. It will be
     * injected by Spring.
     */
    private LoginProcessor loginProcessor;
    
    /**
     * A <code>String</code> providing the user name submitted by user.
     */
    private String username;
    
    /**
     * A <code>String</code> providing the password submitted by user.
     */
    private String password;
    
    /**
     * A <code>boolean</code> flag indicating whether user selected <code>Remember Me</code> option or not.
     */
    private boolean rememberMe;

    /**
     * Sets the user name submitted by user.
     * 
     * @param username the user name submitted by user.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Sets the password submitted by user.
     * 
     * @param password the password submitted by user.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Sets the flag indicating whether user selected <code>Remember Me</code> option or not.
     * 
     * @param rememberMe flag indicating whether user selected <code>Remember Me</code> option or not.
     */
    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    /**
     * Sets the <code>LoginProcessor</code> used to perform authentication.
     * 
     * @param loginProcessor the <code>LoginProcessor</code> used to perform authentication.
     */
    public void setLoginProcessor(LoginProcessor loginProcessor) {
        this.loginProcessor = loginProcessor;
    }

    /**
     * Constructs new <code>LoginAction</code> instance. This implementation does nothing.
     */
    public LoginAction() {
        super();
    }
    
    /**
     * Handles the incoming request. Perform authentication for the user.
     */
    public void executeAction() {
        Map<String, Object> loginResult = new HashMap<String, Object>();
        loginResult.put("success", loginProcessor.login(username, password, rememberMe));
        final TCSubject currentUser = Util.getTCSubjectFromSession();
        if (currentUser != null) {
        	loginResult.put("userId", currentUser.getUserId());
        }
        setResult(loginResult);
    }
}
