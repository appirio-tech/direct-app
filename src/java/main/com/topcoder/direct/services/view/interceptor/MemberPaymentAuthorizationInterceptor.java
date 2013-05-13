/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.view.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.topcoder.direct.services.view.action.contest.launch.DirectStrutsActionsHelper;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.security.TCSubject;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>An interceptor for requests to member payments functions. Verifies that current user has tc accounting role.
 * If not then redirects user to <code>Permission Denied</code> page.</p>
 *
 * <p>
 * <strong>Thread safety</strong>: This class is not thread-safe. However, it will be
 * used in a thread safe manner.
 * </p>
 *
 * @author TCSASSEMBLER
 * @version  1.0 (System Assembly - TopCoder Direct Member Payments Dashboard v1.0)
 */
public class MemberPaymentAuthorizationInterceptor implements Interceptor {
    /**
     * <p>Destroys this interceptor. This implementation does nothing.</p>
     */
    public void destroy() {
        // do nothing
    }

    /**
     * <p>Initializes this interceptor. This implementation does nothing.</p>
     */
    public void init() {
        // do nothing
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
        TCSubject currentUser = DirectStrutsActionsHelper.getTCSubjectFromSession();
        if (currentUser == null ||  (!DirectUtils.isTCAccounting(currentUser) && !DirectUtils.isTcOperations(currentUser))) {
            HttpServletRequest request = DirectUtils.getServletRequest();
            request.setAttribute("errorPageMessage",
                    "Sorry, you don't have permission to access member payment page.");
            return "permissionDenied";
        }
        return actionInvocation.invoke();
    }
}

