/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.direct.services.view.action.contest.launch;

/**
 * <p>
 * This class holds the validation errors that can occur during actions. It holds the errors array, which
 * is an array of ValidationErrorRecord objects.
 * </p>
 *
 * <p>
 * <b>Thread safety:</b> This class is mutable and not thread safe.
 * </p>
 *
 * @author woodjhon, TCSDEVELOPER
 * @version 1.0
 */
public class ValidationErrors {

    /**
     * <p>
     * Represents the errors attribute of the ValidationErrors entity.
     * </p>
     *
     * <p>
     * It's set and accessed in the set/get methods. It can be any value.
     * The default value is null.
     * </p>
     */
    private ValidationErrorRecord[] errors;

    /**
     * Default constructor, constructs an instance of this class.
     */
    public ValidationErrors() {
        // does nothing
    }

    /**
     * Getter for the errors.
     *
     * @return the errors
     */
    public ValidationErrorRecord[] getErrors() {
        return errors;
    }

    /**
     * Setter for the errors. This set method does not perform any check on the argument.
     *
     * @param errors the errors
     */
    public void setErrors(ValidationErrorRecord[] errors) {
        this.errors = errors;
    }
}
