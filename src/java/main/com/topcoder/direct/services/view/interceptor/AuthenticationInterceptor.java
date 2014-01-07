/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.topcoder.direct.services.view.action.AbstractAction;
import com.topcoder.direct.services.view.action.TopCoderDirectAction;
import com.topcoder.direct.services.view.processor.security.LoginProcessor;
import com.topcoder.direct.services.view.util.SessionData;
import com.topcoder.security.TCSubject;
import com.topcoder.security.Util;
import com.topcoder.security.login.AuthenticationException;
import com.topcoder.shared.security.SimpleResource;
import com.topcoder.shared.security.User;
import com.topcoder.shared.util.DBMS;
import com.topcoder.shared.util.logging.Logger;
import com.topcoder.web.common.SimpleRequest;
import com.topcoder.web.common.SimpleResponse;
import com.topcoder.web.common.security.BasicAuthentication;
import com.topcoder.web.common.security.SessionPersistor;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>An interceptor for requests to secured area. Verifies that current user is authenticated to application. If not
 * then redirects user to landing page.</p>
 * 
 * <p>
 * Version 1.1 (BUG TCCC-5802) Change notes:
 *  <ul>
 *   <li>Check SSO cookie and update auth related object in session each time.</li>
 *  </ul>
 * </p>
 *
 * @author isv, ecnu_haozi
 * @version 1.1
 */
public class AuthenticationInterceptor implements Interceptor {

    private static final Logger logger = Logger.getLogger(AuthenticationInterceptor.class);

    /**
     * <p>Constructs new <code>AuthenticationInterceptor</code> instance. This implementation does nothing.</p>
     */
    public AuthenticationInterceptor() {
    }

    /**
     * <p>Initializes this interceptor. This implementation does nothing.</p>
     */
    public void init() {
    }

    /**
     * <p>Destroys this interceptor. This implementation does nothing.</p>
     */
    public void destroy() {
    }

    /**
     * <p>Intercepts the action invocation chain.</p>
     *
     * @param actionInvocation an <code>ActionInvocation</code> providing the current context for action invocation.
     * @return a <code>String</code> referencing the next view or action to route request to or <code>null</code> if
     *         request has been already handled and response committed.
     * @throws Exception if an unexpected error occurs while running the interception chain.
     */
    public String intercept(ActionInvocation actionInvocation) throws Exception {
        // Check if currently there is an active session and if user is authenticated. If not then attempt
        // to authenticate the user based on persistent cookie
        TopCoderDirectAction action = (TopCoderDirectAction) actionInvocation.getAction();
        HttpServletRequest request = ServletActionContext.getRequest();

        SessionData sessionData = action.getSessionData();

        // Support Single-Sign-On login, Because the user may log out from another
        // app without notification to current app. Thus the auth related object in session may invalidate at any time.
        // In this case we need to check SSO cookie and update auth related object in session every time.

            
        HttpServletResponse response = ServletActionContext.getResponse();
        BasicAuthentication auth = new BasicAuthentication(
            new SessionPersistor(request.getSession()), new SimpleRequest(request),
            new SimpleResponse(response), BasicAuthentication.MAIN_SITE, DBMS.JTS_OLTP_DATASOURCE_NAME);
        User user = auth.getActiveUser();
        if (user != null  && !user.isAnonymous()) {
            TCSubject tcSubject = new TCSubject(user.getId());
            if (sessionData == null) {
                sessionData = new SessionData(request.getSession());
                action.setSessionData(sessionData);
            }
            sessionData.setCurrentUser(tcSubject);
            sessionData.setCurrentUserHandle(user.getUserName());
        } else {
            return "anonymous";
        }        

        String servletPath = request.getContextPath() + request.getServletPath();
        String query = request.getQueryString();
        String queryString = (query == null) ? ("") : ("?" + query);

        StringBuffer buf = new StringBuffer(200);
        buf.append("https://");        
        buf.append(request.getServerName());
        buf.append(servletPath);
        buf.append(queryString);
        String requestString = buf.toString();
        String handle = "";
        if (sessionData != null && sessionData.getCurrentUserHandle() != null && !sessionData.getCurrentUserHandle().equals(""))
        {
            handle = sessionData.getCurrentUserHandle();
        }

        StringBuffer loginfo = new StringBuffer(100);
        loginfo.append("[* ");
        loginfo.append(handle);
        loginfo.append(" * ");
        loginfo.append(request.getRemoteAddr());
        loginfo.append(" * ");
        loginfo.append(request.getMethod());
        loginfo.append(" ");
        loginfo.append(requestString);
        loginfo.append(" *]");
        logger.info(loginfo.toString());

        return actionInvocation.invoke();
    }
}
