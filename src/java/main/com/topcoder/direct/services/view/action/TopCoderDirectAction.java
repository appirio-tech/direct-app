/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action;

import com.topcoder.direct.services.view.util.RequestData;
import com.topcoder.direct.services.view.util.SessionData;

/**
 * <p>An interfaces for <code>Struts</code> actions defined for <code>TopCoder Direct</code> application.</p>
 *
 * @author isv
 * @version 1.0
 */
public interface TopCoderDirectAction {

    /**
     * <p>An <code>int</code> providing the result code to be set by the respective processor when unexpected error
     * is encountered.</p>
     */
    public static final int RC_UNEXPECTED_ERROR = -1;

    /**
     * <p>An <code>int</code> providing the result code to be set by the respective processor when request has been
     * processed successfully.</p>
     */
    public static final int RC_SUCCESS = 0;

    /**
     * <p>Gets the current request mapped to this action.</p>
     *
     * @return a <code>RequestData</code> providing the access to request mapped to this action.
     */
    public RequestData getRequestData();

    /**
     * <p>Sets the current request mapped to this action.</p>
     *
     * @param requestData a <code>RequestData</code> providing the access to request mapped to this action.
     */
    public void setRequestData(RequestData requestData);

    /**
     * <p>Gets the current session associated with the incoming request from client.</p>
     *
     * @return a <code>SessionData</code> providing access to current session.
     */
    public SessionData getSessionData();

    /**
     * <p>Sets the current session associated with the incoming request from client.</p>
     *
     * @param sessionData a <code>SessionData</code> providing access to current session.
     */
    public void setSessionData(SessionData sessionData);

    /**
     * <p>Sets the status for this action's execution.</p>
     *
     * @param resultCode an <code>int</code> referencing the status of execution of this action.
     */
    public void setResultCode(int resultCode);

    /**
     * <p>Gets the status for this action's execution.</p>
     *
     * @return an <code>int</code> referencing the status of execution of this action.
     */
    public int getResultCode();

    /**
     * <p>Gets the description of the error which prevents this action from successful execution.</p>
     *
     * @param errorMessage a <code>String</code> providing the description of error which prevented this action from
     *        successful execution.
     */
    public void setErrorMessage(String errorMessage);

    /**
     * <p>Gets the description of the error which prevents this action from successful execution.</p>
     *
     * @return errorMessage a <code>String</code> providing the description of error which prevented this action from
     *        successful execution.
     */
    public String getErrorMessage();

    /**
     * <p>Gets the original cause of error.</p>
     *
     * @return a <code>Throwable</code> providing the cause of error which prevented this action from successful
     *         execution.
     */
    public Throwable getError();

    /**
     * <p>Sets the original cause of error.</p>
     *
     * @param error a <code>Throwable</code> providing the cause of error which prevented this action from successful
     *        execution.
     */
    public void setError(Throwable error);
}
