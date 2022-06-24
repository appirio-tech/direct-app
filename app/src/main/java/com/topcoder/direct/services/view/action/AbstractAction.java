/*
 * Copyright (C) 2010 - 2014 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.topcoder.direct.services.view.action.contest.launch.AggregateDataModel;
import com.topcoder.direct.services.view.ajax.CustomFormatAJAXResult;
import com.topcoder.direct.services.view.util.RequestData;
import com.topcoder.direct.services.view.util.SessionData;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * <p>A base class for all <code>Struts</code> actions defined in <code>TopCoder Direct</code> application.</p>
 *
 * <p>
 * Version 1.0.1 (Direct Release 6 Assembly 1.0) Change notes:
 *   <ol>
 *     <li>Updated {@link #prepare()} method to create session if it not exists yet.</li>
 *   </ol>
 * </p>
 *
 * <p>
  * Version 1.1 (Module Assembly - TopCoder Cockpit Project Dashboard Edit Project version 1.0) Change notes:
  *   <ol>
  *     <li>Add more fields to AbstractAction</li>
  *   </ol>
  * </p>
 *
 * <p>
 *  Version 1.2 (Module Assembly - TopCoder Cockpit New Enterprise Dashboard Setup and Financial part) notes:
 *  <li>
 *      Refactor the the ajax support: result key and methods getResult, setResult and isJsonRequest to
 *      this action from BaseDirectStrutsAction.
 *  </li>
 * </p>
 *
 * <p>
 *  Version 1.3 (TopCoder Direct - Change Date Time suffix label)
 *  <ul>
 *      <li>Added {@link #TIMEZONE} and its getter</li>
 *      <li>Added {@link #DEFAULT_DATE_TIME_FORMAT} and its getter</li>
 *      <li>Added {@link #DEFAULT_DATE_FORMAT} and its getter</li>
 *  </ul>
 * </p>
 *
 * @author isv, GreatKevin
 * @version 1.3
 */
public abstract class AbstractAction extends ActionSupport implements TopCoderDirectAction, Preparable {

    /**
     * The default time zone for the direct application.
     *
     * @since 1.3
     */
    public static String TIMEZONE = "America/New_York";

    /**
     * The default date time format.
     *
     * @since 1.3
     */
    public static String DEFAULT_DATE_TIME_FORMAT = "MM/dd/yyyy HH:mm z";

    /**
     * The default date format.
     *
     * @since 1.3
     */
    public static String DEFAULT_DATE_FORMAT = "MM/dd/yyyy";

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
     * <p>
     * Represents the result key to store result in the aggregate model.
     * </p>
     *
     * @since 1.2
     */
    private static final String RESULT_KEY = "result";

    /**
     * <p>
     * Represents the model attribute of the AbstractAction entity.
     * </p>
     *
     * <p>
     * It's set and accessed in the set/get methods. It can be any value.
     * The default value is null.
     * </p>
     * @since 1.1
     */
    private AggregateDataModel model;

    /**
     * <p>
     * Represents the action attribute of the AbstractAction entity.
     * </p>
     *
     * <p>
     * It's set and accessed in the set/get methods. It can be any value.
     * The default value is null.
     * </p>
     * @since 1.1
     */
    private String action = this.getClass().getName();


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

        if (this.sessionData == null) {
            HttpSession session = request.getSession();
            SessionData sessionData = new SessionData(session);
            setSessionData(sessionData);
        }

        RequestData requestData = new RequestData(request);
        setRequestData(requestData);

        setModel(new AggregateDataModel());
        getModel().setAction(getAction());
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

    /**
     * Getter for the model.
     *
     * @return the model
     * @since 1.1
     */
    public AggregateDataModel getModel() {
        if(this.model == null) {
            this.model = new AggregateDataModel();
        }

        return this.model;
    }

    /**
     * Setter for the model. This set method does not perform any check on the argument.
     *
     * @param model the model to set to the AbstractAction
     * @since 1.1
     */
    public void setModel(AggregateDataModel model) {
        this.model = model;
    }

    /**
     * Getter for the action.
     *
     * @return the action
     * @since 1.1
     */
    public String getAction() {
        return action;
    }

    /**
     * Setter for the action. This set method does not perform any check on the argument.
     *
     * @param action the action to set to the AbstractAction
     * @since 1.1
     */
    public void setAction(String action) {
        this.action = action;
    }

    /**
     * <p>
     * Set the result of the action to the aggregate model.
     * </p>
     * <p>
     * the object will be under the {@link #RESULT_KEY} key in the model map.
     * </p>
     *
     * @param result the result to set to the model
     *
     * @since 1.2
     */
    public void setResult(Object result) {
        getModel().setData(RESULT_KEY, result);
    }

    /**
     * <p>
     * Gets the result from the aggregate model.
     * </p>
     * <p>
     * The result is under the {@link #RESULT_KEY} key in the model map (it can be null if it's not present).
     * </p>
     *
     * @return the result from the model.
     * @since 1.2
     */
    public Object getResult() {
        return getModel().getData(RESULT_KEY);
    }

    /**
     * <p>
     * Determine if it is json request or not.
     * </p>
     *
     * @return true if it is json request
     * @throws Exception
     * @since 1.2
     */
    protected boolean isJsonRequest() throws Exception {
        return ActionContext.getContext().getActionInvocation().getResult() instanceof CustomFormatAJAXResult;
    }

    /**
     * Gets the default time zone.
     *
     * @return the default time zone.
     * @since 1.3
     */
    public String getDefaultTimeZone() {

        return TIMEZONE;
    }

    /**
     * Gets the default date time format.
     *
     * @return the default date time format.
     * @since 1.3
     */
    public String getDefaultDateTimeFormat() {

        return DEFAULT_DATE_TIME_FORMAT;
    }

    /**
     * Gets the default date format.
     *
     * @return the default date format.
     * @since 1.3
     */
    public String getDefaultDateFormat() {

        return DEFAULT_DATE_FORMAT;
    }
}
