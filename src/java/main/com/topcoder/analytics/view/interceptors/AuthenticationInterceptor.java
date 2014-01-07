/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.analytics.view.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.topcoder.analytics.view.util.Util;
import com.topcoder.security.TCSubject;
import com.topcoder.shared.security.User;
import com.topcoder.shared.util.DBMS;
import com.topcoder.web.common.SimpleRequest;
import com.topcoder.web.common.SimpleResponse;
import com.topcoder.web.common.security.BasicAuthentication;
import com.topcoder.web.common.security.SessionPersistor;

/**
 * <p>
 * This interceptor will be responsible for checking if the user info is in the session. It will look for user info in
 * the session using <code>userSessionIdentityKey</code>. If found, it allows the action to proceed. If not, it will check
 * whether the TC SSO cookie is presented, if yes, it will use the TC SSO Cookie to perform login.
 * </p>
 *
 * <p>
 * Version 1.1 (BUG TCCC-5802) Change notes:
 *  <ul>
 *   <li>Check SSO cookie and update auth related object in session each time.</li>
 *  </ul>
 * </p>
 * @author flexme, ecnu_haozi
 * @version 1.1
 */
public class AuthenticationInterceptor extends AbstractInterceptor {
    /**
     * Represents the serial version unique id.
     */
    private static final long serialVersionUID = 5218377458678401239L;

    /**
     * Represents the user session identity key attribute.
     */
    private String userSessionIdentityKey = "user";

    /**
     * Default constructor, constructs an instance of this class.
     */
    public AuthenticationInterceptor() {
        // does nothing
    }

    /**
     * This method provides the intercept logic. It will try to retrieve the user credentials from session, if not found,
     * it will try to use TC SSO cookie to perform login.
     *
     * @param invocation the action invocation to intercept
     * @return the interception result
     * @throws IllegalStateException if the user session identity key are not injected
     * @throws Exception if an error occurs when invoking the action
     */
    public String intercept(ActionInvocation invocation) throws Exception {
        if (userSessionIdentityKey == null) {
            throw new IllegalStateException("userSessionIdentityKey has not been injected.");
        }

        HttpSession session = ServletActionContext.getRequest().getSession();
        HttpServletRequest request = ServletActionContext.getRequest();

        // Support Single-Sign-On login, Because the user may log out from another
        // app without notification to current app. Thus the auth related object in session may invalidate at any time.
        // In this case we need to check SSO cookie and update auth related object in session every time.

        
        HttpServletResponse response = ServletActionContext.getResponse();
        BasicAuthentication auth = new BasicAuthentication(
            new SessionPersistor(request.getSession()), new SimpleRequest(request),
            new SimpleResponse(response), BasicAuthentication.MAIN_SITE, DBMS.JTS_OLTP_DATASOURCE_NAME);
        User user = auth.getActiveUser();
        if (user != null  && !user.isAnonymous()) {
            // get user roles for the user id
            TCSubject tcSubject = Util.getTCSubject(user.getId());
            session.setAttribute(userSessionIdentityKey, tcSubject);
            session.setAttribute("userHandle", user.getUserName());
        }        

        // process the action and return its result
        return invocation.invoke();
    }

    /**
     * Getter for the user session identity key.
     *
     * @return the user session identity key
     */
    public String getUserSessionIdentityKey() {
        return userSessionIdentityKey;
    }

    /**
     * Setter for the user session identity key.
     *
     * @param userSessionIdentityKey user session identity key
     */
    public void setUserSessionIdentityKey(String userSessionIdentityKey) {
        this.userSessionIdentityKey = userSessionIdentityKey;
    }
}
