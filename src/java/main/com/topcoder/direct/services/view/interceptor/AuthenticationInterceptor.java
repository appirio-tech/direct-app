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
 * @author isv
 * @version 1.0
 */
public class AuthenticationInterceptor implements Interceptor {

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
        SessionData sessionData = action.getSessionData();
        if ((sessionData == null) || sessionData.isAnonymousUser()) {
            HttpServletRequest request = ServletActionContext.getRequest();
            HttpServletResponse response = ServletActionContext.getResponse();
            BasicAuthentication auth = new BasicAuthentication(
                new SessionPersistor(request.getSession()), new SimpleRequest(request),
                new SimpleResponse(response), new SimpleResource("direct"), DBMS.JTS_OLTP_DATASOURCE_NAME);
            User user = auth.checkCookie();
            if (user != null  && user.getId() > 0) {
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
        }
        return actionInvocation.invoke();
    }
}
