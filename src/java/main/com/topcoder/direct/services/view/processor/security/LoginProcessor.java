/*
 * Copyright (C) 2010 - 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.processor.security;

import com.topcoder.direct.services.view.action.LoginAction;
import com.topcoder.direct.services.view.form.LoginForm;
import com.topcoder.direct.services.view.processor.RequestProcessor;
import com.topcoder.security.TCSubject;
import com.topcoder.security.login.AuthenticationException;
import com.topcoder.security.login.LoginRemote;
import com.topcoder.shared.util.logging.Logger;
import com.topcoder.web.common.security.Constants;

import com.topcoder.shared.security.SimpleResource;
import com.topcoder.shared.security.SimpleUser;
import com.topcoder.shared.util.DBMS;
import com.topcoder.web.common.SimpleRequest;
import com.topcoder.web.common.SimpleResponse;
import com.topcoder.web.common.security.BasicAuthentication;
import com.topcoder.web.common.security.SessionPersistor;
import org.apache.struts2.ServletActionContext;

/**
 * <p>A processor to be used for handling requests for user authentication to application.</p>
 *
 * <p>This processor expects the actions of {@link LoginAction} type to be passed to it. It gets the submitted user
 * credentials from action's form and uses them to authenticate user using <code>Security Manager EJB</code>.</p>
 *
 * <p>
 * Version 1.1 (System Assembly - Direct Topcoder Scorecard Tool Integration) changes notes: 
 *    <ul>
 *       <li>Set cookie to be used by scorecard application.</li>
 *       <li>Use MAIN_SITE cookie for direct application.</li>
 *       <li>Use 1 day as expiration time for MAIN_SITE cookie if remember me flag is not set.</li>
 *    </ul> 
 * </p>
 *
 * @author isv, pvmagacho
 * @version 1.1
 */
public class LoginProcessor implements RequestProcessor<LoginAction> {

    /**
     * The expire time for main site when 'remember me' flag is not set. Is set to 1 day.
     *
     * @since 1.1
     */
    private static final int MAIN_COOKIE_TIME = 60 * 60 * 24;
    
    /**
     * The SSO cookie to be for use by the Scorecard Tool.
     */
    private static final String SSO_COOKIE = "direct_sso";

    /**
     * <p>A <code>Logger</code> to be used for logging the events encountered while processing the requests.</p>
     */
    private static final Logger log = Logger.getLogger(LoginProcessor.class);

    /**
     * <p>Constructs new <code>LoginProcessor</code> instance. This implementation does nothing.</p>
     */
    public LoginProcessor() {
    }

    /**
     * <p>Processes the incoming request which has been mapped to specified action.</p>
     *
     * @param action an <code>Object</code> representing the current action mapped to incoming request.
     */
    public void processRequest(LoginAction action) {
        LoginForm form = action.getFormData();
        String username = form.getUsername();
        String password = form.getPassword();

        try {
            TCSubject tcSubject = login(username, password);
            action.getSessionData().setCurrentUser(tcSubject);
            action.getSessionData().setCurrentUserHandle(username);
            action.setResultCode(LoginAction.RC_SUCCESS);
            log.info("User " + username + "  has been authenticated successfully");

	    // Changed to use the TC SSO cookie
	    BasicAuthentication auth = new BasicAuthentication(
                    new SessionPersistor(ServletActionContext.getRequest().getSession()),
                    new SimpleRequest(ServletActionContext.getRequest()),
                    new SimpleResponse(ServletActionContext.getResponse()),
					BasicAuthentication.MAIN_SITE,
                    DBMS.JTS_OLTP_DATASOURCE_NAME);
					//auth.setBigCookieTime(MAIN_COOKIE_TIME);
            auth.login(new SimpleUser(tcSubject.getUserId(), username, password), action.getFormData().isRemember());

	    // added by System Assembly - Direct Topcoder Scorecard Tool Integration
	    auth = new BasicAuthentication(
                    new SessionPersistor(ServletActionContext.getRequest().getSession()),
                    new SimpleRequest(ServletActionContext.getRequest()),
                    new SimpleResponse(ServletActionContext.getResponse()),
		    new SimpleResource(SSO_COOKIE),
                    DBMS.JTS_OLTP_DATASOURCE_NAME);
	    auth.login(new SimpleUser(tcSubject.getUserId(), username, password), true);
        } catch (AuthenticationException e) {
            log.error("User " + username + " failed to authenticate successfully due to invalid credentials", e);
            action.setResultCode(LoginAction.RC_INVALID_CREDENTIALS);
        } catch (Exception e) {
            log.error("Failed to authenticate user due to unexpected error", e);
            action.setResultCode(LoginAction.RC_INVALID_CREDENTIALS);
        }
    }

    /**
     * <p>Authenticates the user with specified credentials to this application.</p>
     *
     * @param username a <code>String</code> providing the username for authentication.
     * @param password a <code>String</code> providing the password for authentication.
     * @return a <code>TCSubject</code> representing the authenticated user.
     * @throws AuthenticationException if specified credentials are invalid.
     * @throws Exception if an unexpected error occurs.
     */
    public static TCSubject login(String username, String password) throws Exception {
        LoginRemote login = (LoginRemote) Constants.createEJB(LoginRemote.class);
        TCSubject tcSubject = login.login(username, password);
        return tcSubject;
    }
}
