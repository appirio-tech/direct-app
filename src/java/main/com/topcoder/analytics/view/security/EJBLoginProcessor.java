/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.analytics.view.security;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.topcoder.security.TCSubject;
import com.topcoder.security.login.AuthenticationException;
import com.topcoder.security.login.LoginRemote;
import com.topcoder.shared.security.SimpleResource;
import com.topcoder.shared.security.SimpleUser;
import com.topcoder.shared.util.DBMS;
import com.topcoder.shared.util.logging.Logger;
import com.topcoder.web.common.SimpleRequest;
import com.topcoder.web.common.SimpleResponse;
import com.topcoder.web.common.security.BasicAuthentication;
import com.topcoder.web.common.security.Constants;
import com.topcoder.web.common.security.SessionPersistor;

/**
 * <p>The processor the perform user authentication. It uses security EJB to perform authentication.</p>
 *
 * @author flexme
 * @version 1.0
 */
public class EJBLoginProcessor implements LoginProcessor {

    /**
     * The expire time for main site when 'remember me' flag is not set. Is set to 1 day.
     */
    private static final int MAIN_COOKIE_TIME = 60 * 60 * 24;

    /**
     * The Direct SSO cookie.
     */
    private static final String SSO_COOKIE = "direct_sso";

    /**
     * A <code>Logger</code> to be used for logging the events encountered while processing the requests.
     */
    private static final Logger LOGGER = Logger.getLogger(EJBLoginProcessor.class);

    /**
     * Constructs new <code>EJBLoginProcessor</code> instance. This implementation does nothing.
     */
    public EJBLoginProcessor() {

    }

    /**
     * Perform authentication for a user.
     *
     * @param username the handle of the user.
     * @param password the password of the user.
     * @param rememberMe a flag indicates whether the login state of the user should be remembered.
     * @return true if the login is successful, false otherwise.
     */
    public boolean login(String username, String password, boolean rememberMe) {
        try {
            TCSubject tcSubject = login(username, password);
            HttpSession session = ServletActionContext.getRequest().getSession();
            session.setAttribute("user", tcSubject);
            session.setAttribute("userHandle", username);
            LOGGER.info("User " + username + "  has been authenticated successfully");

            // Changed to use the TC SSO cookie
            BasicAuthentication auth = new BasicAuthentication(
                    new SessionPersistor(ServletActionContext.getRequest().getSession()),
                    new SimpleRequest(ServletActionContext.getRequest()),
                    new SimpleResponse(ServletActionContext.getResponse()),
                    BasicAuthentication.MAIN_SITE,
                    DBMS.JTS_OLTP_DATASOURCE_NAME);
            //auth.setBigCookieTime(MAIN_COOKIE_TIME);
            auth.login(new SimpleUser(tcSubject.getUserId(), username, password), rememberMe);

            auth = new BasicAuthentication(
                    new SessionPersistor(ServletActionContext.getRequest().getSession()),
                    new SimpleRequest(ServletActionContext.getRequest()),
                    new SimpleResponse(ServletActionContext.getResponse()),
                    new SimpleResource(SSO_COOKIE),
                    DBMS.JTS_OLTP_DATASOURCE_NAME);
            auth.login(new SimpleUser(tcSubject.getUserId(), username, password), true);
            return true;
        } catch (AuthenticationException e) {
            LOGGER.error("User " + username + " failed to authenticate successfully due to invalid credentials", e);
            return false;
        } catch (Exception e) {
            LOGGER.error("Failed to authenticate user due to unexpected error", e);
            return false;
        }
    }

    /**
     * Authenticates the user with specified credentials to this application.
     *
     * @param username a <code>String</code> providing the username for authentication.
     * @param password a <code>String</code> providing the password for authentication.
     * @return a <code>TCSubject</code> representing the authenticated user.
     * @throws AuthenticationException if specified credentials are invalid.
     * @throws Exception if an unexpected error occurs.
     */
    private static TCSubject login(String username, String password) throws Exception {
        LoginRemote login = (LoginRemote) Constants.createEJB(LoginRemote.class);
        TCSubject tcSubject = login.login(username, password);
        return tcSubject;
    }
}
