/*
 * Copyright (C) 2010 - 2011 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.direct.services.view.action.specreview;

import com.topcoder.direct.services.view.action.contest.launch.AbstractAction;
import com.topcoder.security.TCSubject;
import com.topcoder.direct.services.view.action.contest.launch.AggregateDataModel;
import com.topcoder.direct.services.view.action.contest.launch.ValidationErrorRecord;
import com.topcoder.direct.services.view.action.contest.launch.ValidationErrors;
import com.topcoder.direct.services.view.action.contest.launch.DirectStrutsActionsHelper;
import com.topcoder.util.errorhandling.ExceptionUtils;

/**
 * <p>
 * This abstract action provides common request parameters (contestId and studio) to the implementing classes.
 * It also provides convenience methods to get the TCSubject from session and to store result data, exceptions
 * and validation errors to the <code>AggregateDataModel</code> data map, which is part of the
 * <code>AbstractAction</code> class.
 * </p>
 *
 * <p>
 * The session key used to retrieve the TCSubject and the keys used to store result data, exceptions and
 * validation errors in the <code>AggregateDataModel</code> data map are all configurable via injection
 * setter, though default values are already defined for them.
 * </p>
 *
 * <p>
 * <strong>
 * Demo which shows how the <code>SaveSpecificationReviewCommentAction</code> and
 * <code>ViewSpecificationReviewAction</code> (these actions inherit from this class) could be used in a JSP:
 * </strong>
 * <pre>
 * &lt;%@ page language="java" contentType="text/html; charset=ISO-8859-1"
 *  pageEncoding="ISO-8859-1"%&gt;
 * &lt;!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"&gt;
 * &lt;%@taglib uri="/struts-tags" prefix="s"%&gt;
 * &lt;html&gt;
 * &lt;head&gt;
 * &lt;meta http-equiv="Content-Type" content="text/html; charset=UTF-8"&gt;
 * &lt;title&gt;Demo&lt;/title&gt;
 *
 * &lt;link href="&lt;s:url value="/css/main.css"/&gt;" rel="stylesheet"
 *  type="text/css" /&gt;
 *
 * &lt;/head&gt;
 * &lt;body&gt;
 *
 * &lt;!-- display any action errors --&gt;
 * &lt;div style="color: #ff0000;"&gt;&lt;s:iterator value="actionErrors"&gt;
 *  &lt;span&gt;&lt;s:property escape="false" /&gt;&lt;/span&gt;
 * &lt;/s:iterator&gt;&lt;/div&gt;
 *
 * &lt;h3&gt;SaveSpecificationReviewCommentAction&lt;/h3&gt;
 * &lt;s:form action="saveSpecificationReviewCommentAction"&gt;
 *  &lt;table&gt;
 *      &lt;tr&gt;
 *          &lt;td&gt;Enter Comment&lt;/td&gt;
 *          &lt;td&gt;&lt;s:textfield name="comment" /&gt;&lt;/td&gt;
 *      &lt;/tr&gt;
 *      &lt;tr&gt;
 *          &lt;td&gt;&lt;s:submit value="Save Comment" /&gt;&lt;/td&gt;
 *          &lt;td&gt;&lt;/td&gt;
 *      &lt;/tr&gt;
 *  &lt;/table&gt;
 *  &lt;s:hidden name="contestId" value="1" /&gt;
 *  &lt;s:hidden name="studio" value="false" /&gt;
 *  &lt;s:hidden name="questionId" value="2" /&gt;
 *  &lt;s:hidden name="action" value="add" /&gt;
 * &lt;/s:form&gt;
 *
 * &lt;h3&gt;ViewSpecificationReviewAction&lt;/h3&gt;
 * &lt;s:form action="viewSpecificationReviewAction"&gt;
 *  &lt;table&gt;
 *      &lt;tr&gt;
 *          &lt;td&gt;&lt;s:submit value="View Specification Review Comments" /&gt;&lt;/td&gt;
 *      &lt;/tr&gt;
 *  &lt;/table&gt;
 *  &lt;s:hidden name="contestId" value="1" /&gt;
 * &lt;/s:form&gt;
 *
 * &lt;/body&gt;
 * &lt;/html&gt;
 * </pre>
 * </p>
 * <p>Version 1.1 change notes:
 * <ul>
 *  <li>Update {@link #execute()} to use the projectId instead of contestId</li>
 * </ul>
 * </p>

 * <p>
 * <b>Thread safety:</b> This class is mutable and not thread safe and the implementations do not need be
 * thread safe. This should be fine since different instances of this class should be created to serve
 * different user requests and will not be shared across user threads (this will be taken care of by Struts 2
 * framework). So it can be considered as being used in a thread safe manner.
 * </p>
 *
 * @author caru, TCSDEVELOPER, morehappiness
 * @version 1.1
 * @since 1.0
 */
public abstract class SpecificationReviewAction extends SessionAwareAction {

    /**
     * The default key used to retrieve the TCSubject from session. Set to 'tcSubject'.
     */
    public static final String DEFAULT_TC_SUBJECT_SESSION_KEY = "tcSubject";

    /**
     * The default key used to store result data in <code>AggregateDataModel</code> data map. Set to
     * 'result'.
     */
    public static final String DEFAULT_RESULT_DATA_KEY = "result";

    /**
     * The default key used to store exceptions in <code>AggregateDataModel</code> data map. Set to
     * 'result'.
     */
    public static final String DEFAULT_EXCEPTION_KEY = "result";

    /**
     * The default key used to store validation errors in <code>AggregateDataModel</code> data map. Set to
     * 'validationErrors'.
     */
    public static final String DEFAULT_VALIDATION_ERRORS_KEY = "validationErrors";

    /**
     * <p>
     * Represents the project id.
     * </p>
     *
     * <p>
     * Initially set to 0 (default), can be any value. It set by setter and accessed by getter.
     * </p>
     */
    private long projectId;

    /**
     * <p>
     * Flag indicating whether the contest is a studio (true) or software contest (false).
     * </p>
     *
     * <p>
     * Initially set to false (default), can be any value. It set by setter and accessed by getter.
     * </p>
     */
    private boolean studio;

    /**
     * <p>
     * Represents the key used to retrieve the TCSubject from session.
     * </p>
     *
     * <p>
     * Initially set to <code>DEFAULT_TC_SUBJECT_SESSION_KEY</code>, cannot be null or empty. It set by setter
     * and accessed by getter. Used in <code>getTCSubject</code>.
     */
    private String tcSubjectSessionKey = DEFAULT_TC_SUBJECT_SESSION_KEY;

    /**
     * <p>
     * Represents the key used to store result data in <code>AggregateDataModel</code> data map.
     * </p>
     *
     * <p>
     * Initially set to <code>DEFAULT_RESULT_DATA_KEY</code>, cannot be null or empty. It set by setter and
     * accessed by getter. Used in <code>setResultData</code>.
     * </p>
     */
    private String resultDataKey = DEFAULT_RESULT_DATA_KEY;

    /**
     * <p>
     * Represents the key used to store exceptions in <code>AggregateDataModel</code> data map.
     * </p>
     *
     * <p>
     * Initially set to <code>DEFAULT_EXCEPTION_KEY</code>, cannot be null or empty. It set by setter and
     * accessed by getter. Used in <code>setException</code>.
     * </p>
     */
    private String exceptionKey = DEFAULT_EXCEPTION_KEY;

    /**
     * <p>
     * Represents the key used to store validation errors in <code>AggregateDataModel</code> data map.
     * </p>
     *
     * <p>
     * Initially set to <code>DEFAULT_VALIDATION_ERRORS_KEY</code>, cannot be null or empty. It set by setter
     * and accessed by getter. Used in <code>addValidationError</code>.
     * </p>
     */
    private String validationErrorsKey = DEFAULT_VALIDATION_ERRORS_KEY;

    /**
     * Default constructor, it's protected to prevent external initialization.
     */
    protected SpecificationReviewAction() {
    }

    /**
     * Getter for TCSubject stored in session.
     *
     * @return the TCSubject stored in session
     */
    protected TCSubject getTCSubject() {
        return DirectStrutsActionsHelper.getTCSubjectFromSession();
        //return (TCSubject) getSession().get(tcSubjectSessionKey);
    }

    /**
     * Stores result data in <code>AggregateDataModel</code> data map.
     *
     * @param resultData the result data, cannot be null
     * @throws IllegalArgumentException if argument is null
     */
    protected void setResultData(Object resultData) {
        ExceptionUtils.checkNull(resultData, null, null, "resultData cannot be null.");
        setModelData(resultDataKey, resultData);
    }

    /**
     * Stores exception in <code>AggregateDataModel</code> data map.
     *
     * @param exception the exception to store in the <code>AggregateDataModel</code> data map, cannot be null
     * @throws IllegalArgumentException if the argument is null
     */
    protected void setException(Throwable exception) {
        ExceptionUtils.checkNull(exception, null, null, "exception cannot be null.");
        setModelData(exceptionKey, exception);
    }

    /**
     * Stores a validation error in <code>AggregateDataModel</code> data map.
     *
     * @param propertyName the property name, cannot be null or empty
     * @param messages the validation error messages, cannot be null or empty, elements cannot be null or empty
     *
     * @throws IllegalArgumentException if propertyName is null or empty or if messages is null or empty or
     *         contains null or empty elements
     */
    protected void addValidationError(String propertyName, String[] messages) {
        ExceptionUtils.checkNullOrEmpty(propertyName, null, null, "propertyName cannot be null or empty.");
        ExceptionUtils.checkNull(messages, null, null, "messages cannot be null.");

        // make sure messages array is valid
        if (messages.length == 0) {
            throw new IllegalArgumentException("messages cannot be empty.");
        }
        for (String msg : messages) {
            if (msg == null || msg.trim().length() == 0) {
                throw new IllegalArgumentException("messages cannot contain null or empty elements.");
            }
        }

        // get the model for the action (create it if necessary)
        AggregateDataModel model = getOrCreateModel();

        // get the validation errors and create a new object if necessary
        ValidationErrors errors = (ValidationErrors) model.getData(getValidationErrorsKey());
        if (errors == null) {
            errors = new ValidationErrors();
        }

        // get the validation error record array and resize it to hold the new element, or create
        // a new array if it's null
        ValidationErrorRecord[] records = errors.getErrors();
        if (records != null) {
            ValidationErrorRecord[] newRecords = new ValidationErrorRecord[records.length + 1];
            System.arraycopy(records, 0, newRecords, 0, records.length);
            records = newRecords;
        } else {
            records = new ValidationErrorRecord[1];
        }

        // prepare the validation error record and load the error records array to it
        ValidationErrorRecord record = new ValidationErrorRecord();
        record.setPropertyName(propertyName);
        record.setMessages(messages);
        records[records.length - 1] = record;
        errors.setErrors(records);

        // update the model
        model.setData(validationErrorsKey, errors);
        setModel(model);
    }

    /**
     * Getter for project id.
     *
     * @return the project id
     */
    public long getProjectId() {
        return projectId;
    }

    /**
     * Setter for project id.
     *
     * @param projectId the project id
     */
    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    /**
     * Getter for the flag indicating whether this is a studio competition.
     *
     * @return the flag indicating whether this is a studio competition
     */
    public boolean isStudio() {
        return studio;
    }

    /**
     * Setter for the flag indicating whether this is a studio competition.
     *
     * @param studio the flag indicating whether this is a studio competition
     */
    public void setStudio(boolean studio) {
        this.studio = studio;
    }

    /**
     * Getter for TCSubject session key.
     *
     * @return the TCSubject session key
     */
    public String getTCSubjectSessionKey() {
        return tcSubjectSessionKey;
    }

    /**
     * Setter for TCSubject session key.
     *
     * @param tcSubjectSessionKey the TCSubject session key
     * @throws IllegalArgumentException if the argument is null or empty
     */
    public void setTCSubjectSessionKey(String tcSubjectSessionKey) {
        ExceptionUtils.checkNullOrEmpty(tcSubjectSessionKey, null, null,
            "tcSubjectSessionKey cannot be null or empty.");
        this.tcSubjectSessionKey = tcSubjectSessionKey;
    }

    /**
     * Getter for result data key.
     *
     * @return the result data key
     */
    public String getResultDataKey() {
        return resultDataKey;
    }

    /**
     * Setter for result data key.
     *
     * @param resultDataKey the result data key
     * @throws IllegalArgumentException if the argument is null or empty
     */
    public void setResultDataKey(String resultDataKey) {
        ExceptionUtils.checkNullOrEmpty(resultDataKey, null, null, "resultDataKey cannot be null or empty.");
        this.resultDataKey = resultDataKey;
    }

    /**
     * Getter for exception key.
     *
     * @return the exception key
     */
    public String getExceptionKey() {
        return exceptionKey;
    }

    /**
     * Setter for exception key.
     *
     * @param exceptionKey the exception key
     * @throws IllegalArgumentException if the argument is null or empty
     */
    public void setExceptionKey(String exceptionKey) {
        ExceptionUtils.checkNullOrEmpty(exceptionKey, null, null, "exceptionKey cannot be null or empty.");
        this.exceptionKey = exceptionKey;
    }

    /**
     * Getter for validation errors key.
     *
     * @return the validation errors key
     */
    public String getValidationErrorsKey() {
        return validationErrorsKey;
    }

    /**
     * Setter for validation errors key.
     *
     * @param validationErrorsKey the validation errors key
     * @throws IllegalArgumentException if the argument is null or empty
     */
    public void setValidationErrorsKey(String validationErrorsKey) {
        ExceptionUtils.checkNullOrEmpty(validationErrorsKey, null, null,
            "validationErrorsKey cannot be null or empty.");
        this.validationErrorsKey = validationErrorsKey;
    }

    /**
     * Stores the given data in the model using the given key.
     *
     * @param key the key to use when storing data in model
     * @param data the data to store in model
     */
    private void setModelData(String key, Object data) {
        // get the model for the action (create it if necessary)
        AggregateDataModel model = getOrCreateModel();

        // update the model with the data
        model.setData(key, data);
        setModel(model);
    }

    /**
     * Gets the existing model, or creates a new model if model is null.
     *
     * @return the model
     */
    private AggregateDataModel getOrCreateModel() {
        // return existing model or create a new one if necessary
        AggregateDataModel model = getModel();
        return model == null ? new AggregateDataModel() : model;
    }
}
