/*
 * Copyright (C) 2010 - 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.topcoder.direct.services.project.metadata.DirectProjectMetadataService;
import com.topcoder.direct.services.project.metadata.entities.dao.DirectProjectAccess;
import com.topcoder.direct.services.view.action.FormAction;
import com.topcoder.direct.services.view.action.TopCoderDirectAction;
import com.topcoder.direct.services.view.action.project.WriteProject;
import com.topcoder.direct.services.view.form.ProjectIdForm;
import com.topcoder.direct.services.view.util.AuthorizationProvider;
import com.topcoder.direct.services.view.util.DirectUtils;

import javax.servlet.http.HttpServletRequest;

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
 * <p>
 * Version 1.2 (Release Assembly - TopCoder Security Groups Release 5 v1.0) Change notes:
 *   <ol>
 *     <li>Updated {@link #intercept(ActionInvocation)} to check the write permission instead of read permission on the
 *     project if the corresponding struts action class/method is annotated with <code>WriteProject</code>.</li>
 *   </ol>
 * </p>
 *
 * <p>
 * Version 1.3 (Release Assembly - TopCoder Cockpit Navigation Update)
 * <ul>
 *     <li>Added property {@link #metadataService} and its setter and getter for injection</li>
 *     <li>Updated method {@link #intercept(com.opensymphony.xwork2.ActionInvocation)} to record the user direct project access</li>
 * </ul>
 * </p>
 *
 * @author isv, GreatKevin
 * @version 1.3
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
     * The direct project metadata service to record the direct project user access.
     *
     * @since 1.3
     */
    private DirectProjectMetadataService metadataService;

    /**
     * Gets the direct project metadata service.
     *
     * @return the direct project metadata service.
     * @since 1.3
     */
    public DirectProjectMetadataService getMetadataService() {
        return metadataService;
    }

    /**
     * Sets the direct project metadata service.
     *
     * @param metadataService the direct project metadata service.
     * @since 1.3
     */
    public void setMetadataService(DirectProjectMetadataService metadataService) {
        this.metadataService = metadataService;
    }

    /**
     * <p>Intercepts the action invocation chain.</p>
     *
     * <p>
     * Version 1.3 (Release Assembly - TopCoder Cockpit Navigation Update) changes:
     * - Record the project current user accessed and the access time.
     * </p>
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
        boolean readonly = true;
        boolean granted;
        String method = actionInvocation.getProxy().getMethod();
        if (method != null && !method.equals("execute")) {
            if (actionInvocation.getAction().getClass().getMethod(method).isAnnotationPresent(WriteProject.class)) {
                readonly = false;
            }
        } else {
            if (actionInvocation.getAction().getClass().isAnnotationPresent(WriteProject.class)) {
                readonly = false;
            }
        }
        if (!readonly) {
            granted =  AuthorizationProvider.isUserGrantedWriteAccessToProject(DirectUtils.getTCSubjectFromSession(), projectId);
        } else {
            granted = AuthorizationProvider.isUserGrantedAccessToProject(DirectUtils.getTCSubjectFromSession(), projectId);
        }
        if (!granted) {
            request.setAttribute("errorPageMessage", "Sorry, you don't have permission to access this project.");
            return "permissionDenied";
        } else {
            // record access time and accessed project id for
            // current user (Release Assembly - TopCoder Cockpit Navigation Update)
            DirectProjectAccess projectAccess = new DirectProjectAccess();
            projectAccess.setUserId(DirectUtils.getTCSubjectFromSession().getUserId());
            projectAccess.setAccessItemId(projectId);
            projectAccess.setAccessTypeId(1L);
            getMetadataService().recordDirectProjectAccess(projectAccess,
                                                           DirectUtils.getTCSubjectFromSession().getUserId());
            return actionInvocation.invoke();
        }
    }
}
