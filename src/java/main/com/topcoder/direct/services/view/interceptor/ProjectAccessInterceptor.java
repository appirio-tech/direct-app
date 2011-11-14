/*
 * Copyright (C) 2010 - 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.interceptor;

import javax.servlet.http.HttpServletRequest;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.topcoder.direct.services.view.action.FormAction;
import com.topcoder.direct.services.view.action.TopCoderDirectAction;
import com.topcoder.direct.services.view.form.ProjectIdForm;
import com.topcoder.direct.services.view.util.AuthorizationProvider;
import com.topcoder.direct.services.view.util.DirectUtils;

/**
 * <p>An interceptor for requests to secured area. Verifies that current user is granted a permission to access the
 * requested project. If not then redirects user to <code>Permission Denied</code> page.</p>
 *
 * <p>This interceptor must be mapped to the only actions which implement {@link FormAction} interface and have their
 * form data of {@link ProjectIdForm} type.</p>
 *
 * <p>
 * Version 1.1 (TC Cockpit Permission and Report Update One) change log:
 * <ol>
 *   <li>Updated {@link #intercept(ActionInvocation)} method to pass <code>TCSubject</code> instance to
 *   <code>AuthorizationProvider.isUserGrantedAccessToProject</code>.</li>
 *   <li>Updated {@link #intercept(ActionInvocation)} to set the error page message when user has no permission.</li>
 * </ol>
 * </p>
 * 
 * @author isv
 * @version 1.1
 */
public class ProjectAccessInterceptor implements Interceptor {

    /**
     * <p>Constructs new <code>ProjectAccessInterceptor</code> instance. This implementation does nothing.</p>
     */
    public ProjectAccessInterceptor() {
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
        HttpServletRequest request = DirectUtils.getServletRequest();
        TopCoderDirectAction action = (TopCoderDirectAction) actionInvocation.getAction();
        FormAction formAction = (FormAction) action;
        Object formData = formAction.getFormData();
        ProjectIdForm projectIdForm = (ProjectIdForm) formData;
        long projectId = projectIdForm.getProjectId();
        boolean granted = AuthorizationProvider.isUserGrantedAccessToProject(DirectUtils.getTCSubjectFromSession(), projectId);
        if (!granted) {
            request.setAttribute("errorPageMessage", "Sorry, you don't have permission to access this project.");
            return "permissionDenied";
        } else {
            return actionInvocation.invoke();
        }
    }
}
