/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.direct.services.view.action.contest.launch;

/**
 * <p>
 * Represents the ValidationErrorRecord entity. It holds the property name and messages.
 * </p>
 *
 * <p>
 * It's used by the ValidationErrors class to store information regarding errors that
 * can occur during actions.
 * </p>
 *
 * <p>
 * <b>Thread safety:</b> This class is mutable and not thread safe.
 * </p>
 *
 * @author woodjhon, TCSDEVELOPER
 * @version 1.0
 */
public class ValidationErrorRecord {

    /**
     * <p>
     * Represents the property name attribute of the ValidationErrorRecord entity.
     * </p>
     *
     * <p>
     * It's set and accessed in the set/get methods. It can be any value.
     * The default value is null.
     * </p>
     */
    private String propertyName;

    /**
     * <p>
     * Represents the messages attribute of the ValidationErrorRecord entity.
     * </p>
     *
     * <p>
     * It's set and accessed in the set/get methods. It can be any value.
     * The default value is null.
     * </p>
     */
    private String[] messages;

    /**
     * Default constructor, constructs an instance of this class.
     */
    public ValidationErrorRecord() {
        // does nothing
    }

    /**
     * Getter for the property name.
     *
     * @return the property name
     */
    public String getPropertyName() {
        return propertyName;
    }

    /**
     * Setter for the property name. This set method does not perform any check on the argument.
     *
     * @param propertyName the property name
     */
    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    /**
     * Getter for the messages.
     *
     * @return the messages
     */
    public String[] getMessages() {
        return messages;
    }

    /**
     * Setter for the messages. This set method does not perform any check on the argument.
     *
     * @param messages the messages
     */
    public void setMessages(String[] messages) {
        this.messages = messages;
    }
}
