/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.topcoder.direct.services.view.util.RequestData;
import com.topcoder.direct.services.view.util.SessionData;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * <p>A base class for all <code>Struts</code> actions defined in <code>TopCoder Direct</code> application.</p>
 *
 * @author isv
 * @version 1.0
 */
public abstract class AbstractAction extends ActionSupport implements TopCoderDirectAction, Preparable {

    /**
     * <p>A <code>RequestData</code> providing the access to request mapped to this action.</p>
     */
    private RequestData requestData;

    /**
     * <p>A <code>SessionData</code> providing access to current session context for this action. May be
     * <code>null</code> if currently there is no session context.</p>
     */
    private SessionData sessionData;

    /**
     * <p>An <code>int</code> referencing the status of execution of this action.</p>
     */
    private int resultCode = RC_SUCCESS;

    /**
     * <p>A <code>String</code> providing the description of error which prevented this action from successful
     * execution.</p>
     */
    private String errorMessage;

    /**
     * <p>A <code>Throwable</code> providing the cause of error which prevented this action from successful execution.
     * </p>
     */
    private Throwable error;

    /**
     * <p>Constructs new <code>AbstractAction</code> instance. This implementation does nothing.</p>
     */
    protected AbstractAction() {
    }

    /**
     * <p>Gets the current request mapped to this action.</p>
     *
     * @return a <code>RequestData</code> providing the access to request mapped to this action.
     */
    public RequestData getRequestData() {
        return this.requestData;
    }

    /**
     * <p>Sets the current request mapped to this action.</p>
     *
     * @param requestData a <code>RequestData</code> providing the access to request mapped to this action.
     */
    public void setRequestData(RequestData requestData) {
        this.requestData = requestData;
    }

    /**
     * <p>Gets the current session associated with the incoming request from client.</p>
     *
     * @return a <code>SessionData</code> providing access to current session.
     */
    public SessionData getSessionData() {
        return this.sessionData;
    }

    /**
     * <p>Sets the current session associated with the incoming request from client.</p>
     *
     * @param sessionData a <code>SessionData</code> providing access to current session.
     */
    public void setSessionData(SessionData sessionData) {
        this.sessionData = sessionData;
    }

    /**
     * <p>Sets the status for this action's execution.</p>
     *
     * @param resultCode an <code>int</code> referencing the status of execution of this action.
     */
    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    /**
     * <p>Gets the status for this action's execution.</p>
     *
     * @return an <code>int</code> referencing the status of execution of this action.
     */
    public int getResultCode() {
        return resultCode;
    }

    /**
     * <p>Sets the description of the error which prevents this action from successful execution.</p>
     *
     * @param errorMessage a <code>String</code> providing the description of error which prevented this action from
     *        successful execution.
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * <p>Gets the description of the error which prevents this action from successful execution.</p>
     *
     * @return errorMessage a <code>String</code> providing the description of error which prevented this action from
     *        successful execution.
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * <p>Gets the original cause of error.</p>
     *
     * @return a <code>Throwable</code> providing the cause of error which prevented this action from successful
     *         execution.
     */
    public Throwable getError() {
        return error;
    }

    /**
     * <p>Sets the original cause of error.</p>
     *
     * @param error a <code>Throwable</code> providing the cause of error which prevented this action from successful
     *        execution.
     */
    public void setError(Throwable error) {
        this.error = error;
    }

    /**
     * <p>Prepares this action for execution. This implementation sets the session and request contexts for this action.
     * </p>
     *
     * @throws Exception if an unexpected error occurs.
     */
    public void prepare() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();

        HttpSession session = request.getSession(false);
        if (session != null) {
            SessionData sessionData = new SessionData(session);
            setSessionData(sessionData);
        }

        RequestData requestData = new RequestData(request);
        setRequestData(requestData);
    }

    /**
     * <p>Handles the incoming request.</p>
     *
     * @return a <code>String</code> referencing the next view or action to route request to. This implementation
     *         returns {@link #SUCCESS} always.
     * @throws Exception if an unexpected error occurs while processing the request.
     */
    @Override
    public String execute() throws Exception {
        int currentResultCode = getResultCode();
        if (RC_SUCCESS == currentResultCode) {
            return SUCCESS;
        } else {
            addActionError(getErrorMessage());
            HttpServletRequest request = ServletActionContext.getRequest();
            request.setAttribute("errorMessage", getErrorMessage());
            request.setAttribute("error", getError());
            return ERROR;
        }
    }
}
