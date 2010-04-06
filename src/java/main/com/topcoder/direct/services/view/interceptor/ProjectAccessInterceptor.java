/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.topcoder.direct.services.view.action.FormAction;
import com.topcoder.direct.services.view.action.TopCoderDirectAction;
import com.topcoder.direct.services.view.form.ProjectIdForm;
import com.topcoder.direct.services.view.util.AuthorizationProvider;
import com.topcoder.direct.services.view.util.SessionData;

/**
 * <p>An interceptor for requests to secured area. Verifies that current user is granted a permission to access the
 * requested project. If not then redirects user to <code>Permission Denied</code> page.</p>
 *
 * <p>This interceptor must be mapped to the only actions which implement {@link FormAction} interface and have their
 * form data of {@link ProjectIdForm} type.</p>
 *
 * @author isv
 * @version 1.0
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
        TopCoderDirectAction action = (TopCoderDirectAction) actionInvocation.getAction();
        SessionData sessionData = action.getSessionData();
        FormAction formAction = (FormAction) action;
        Object formData = formAction.getFormData();
        ProjectIdForm projectIdForm = (ProjectIdForm) formData;
        long currentUserId = sessionData.getCurrentUserId();
        long projectId = projectIdForm.getProjectId();
        boolean granted = AuthorizationProvider.isUserGrantedAccessToProject(currentUserId, projectId);
        if (!granted) {
            return "permissionDenied";
        } else {
            return actionInvocation.invoke();
        }
    }
}
