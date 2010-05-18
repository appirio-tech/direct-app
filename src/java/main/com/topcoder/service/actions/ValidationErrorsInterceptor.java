/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.actions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * <p>
 * This purpose of this interceptor is to adapt the default validation workflow of Struts 2 to the validation workflow
 * of the Struts Framework TC component. It retrieves the validation errors of fields (also called properties in Struts
 * Framework) provided by the validation annotations and fills the ValidationErrors with the errors messages. In this
 * mode the component can completely re-use all validation annotations provided by Struts 2.
 * </p>
 * <p>
 * <b>Thread Safety</b>: an Interceptor must be thread safe (from its javadoc: Interceptors <b>must</b> be stateless and
 * not assume that a new instance will be created for each request or Action.) . This class is stateless and then it's
 * thread safe.
 * </p>
 *
 * @author fabrizyo, FireIce
 * @version 1.0
 */
public class ValidationErrorsInterceptor extends AbstractInterceptor {
    /**
     * <p>
     * Represents the unique serial version id.
     * </p>
     */
    private static final long serialVersionUID = 945084568437760480L;

    /**
     * <p>
     * Creates a <code>ValidationErrorsInterceptor</code> instance.
     * </p>
     */
    public ValidationErrorsInterceptor() {
    }

    /**
     * <p>
     * Intercepts the action after its validation and add the <code>ValidationErrors</code> instanced constructed from
     * the field errors in the <code>ActionSupport</code>.
     * </p>
     *
     * @param actionInvocation
     *            the action invocation which contains the action invocation information, can't be null
     * @return the result of the action invocation
     * @throws IllegalArgumentException
     *             if the <code>actionInvocation</code> is <code>null</code>, if the action in the actionInvocation is
     *             not an {@link BaseDirectStrutsAction}
     * @throws Exception
     *             the exception thrown by the ActionInvocation.invoke() method
     */
    public String intercept(ActionInvocation actionInvocation) throws Exception {
        if (null == actionInvocation) {
            throw new IllegalArgumentException("The actionInvocation is null");
        }

        if (!(actionInvocation.getAction() instanceof BaseDirectStrutsAction)) {
            throw new IllegalArgumentException("The action in intercepted is not an BaseDirectStrutsAction");
        }

        // invoke the action first.
        String result = actionInvocation.invoke();

        BaseDirectStrutsAction action = (BaseDirectStrutsAction) actionInvocation.getAction();

        Map<String, List<String>> fieldErrors = action.getFieldErrors();

        // convert the field errors into validation error records
        List<ValidationErrorRecord> validationErrorRecords = new ArrayList<ValidationErrorRecord>();
        for (Entry<String, List<String>> entry : fieldErrors.entrySet()) {
            ValidationErrorRecord validationErrorRecord = new ValidationErrorRecord();

            validationErrorRecord.setPropertyName(entry.getKey());
            validationErrorRecord.setMessages(entry.getValue().toArray(new String[0]));

            validationErrorRecords.add(validationErrorRecord);
        }

        if (!fieldErrors.isEmpty()) {
            // create ValidationErrors to hold ValidationErrorRecords.
            ValidationErrors validationErrors = new ValidationErrors();

            validationErrors.setErrors(validationErrorRecords.toArray(new ValidationErrorRecord[0]));

            // set in action result.
            action.setResult(validationErrors);
        }

        return result;
    }
}
