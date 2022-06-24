/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.contest.launch;

import java.util.Date;

import com.opensymphony.xwork2.validator.ValidationException;
import com.opensymphony.xwork2.validator.annotations.CustomValidator;
import com.opensymphony.xwork2.validator.validators.FieldValidatorSupport;

/**
 * <p>
 * This validator validates the date which must be after current date. For example, it's used to validate the start date
 * of contest. It's used together with the {@link CustomValidator} with the 'dateAfterCurrentDate' type. See
 * http://java-x.blogspot.com/2008/04/struts-2-custom-validators.html for an example of configuration annotation.
 * </p>
 * <p>
 * It should be configured in the <b>validations.xml</b> file in order to be used.
 *
 * <pre>
 * &lt;?xml version=&quot;1.0&quot; encoding=&quot;UTF-8&quot;?&gt;
 * &lt;!DOCTYPE validators PUBLIC
 *     &quot;-//OpenSymphony Group//XWork Validator Config 1.0//EN&quot;
 *     &quot;http://www.opensymphony.com/xwork/xwork-validator-config-1.0.dtd&quot;&gt;
 * &lt;!-- after definition, we can use it with the CustomValidator annotation--&gt;
 * &lt;validators&gt;
 * &lt;validator name=&quot;dateAfterCurrentDate&quot;
 *     class=&quot;com.topcoder.service.actions.DateAfterCurrentDateValidator&quot;/&gt;
 * &lt;/validators&gt;
 * </pre>
 *
 * </p>
 * <p>
 * <b>Thread Safety</b>: the super class is not thread safe (it has getter and setters and it's not required to be
 * because the validator instances are created on the fly when it's necessary) and then this class is not thread safe.
 * </p>
 *
 * @author fabrizyo, FireIce
 * @version 1.0
 */
public class DateAfterCurrentDateValidator extends FieldValidatorSupport {
    /**
     * <p>
     * Creates a <code>DateAfterCurrentDateValidator</code> instance.
     * </p>
     */
    public DateAfterCurrentDateValidator() {
    }

    /**
     * <p>
     * Validates the date which must be strictly after current date.
     * </p>
     *
     * @param object
     *            the object
     * @throws ValidationException
     *             if any error occurs.
     */
    public void validate(Object object) throws ValidationException {
        String fieldName = getFieldName();

        Object value = getFieldValue(fieldName, object);

        if (value instanceof Date) {
            if (((Date) value).getTime() <= System.currentTimeMillis()) {
                // before the current date
                addFieldError(fieldName, object);
            }
        }
    }
}
