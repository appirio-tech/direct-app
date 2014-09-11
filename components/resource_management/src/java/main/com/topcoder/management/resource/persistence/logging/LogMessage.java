/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource.persistence.logging;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;


/**
 * Encapsulates the entry log data and generates consistent log messages.
 */
public class LogMessage {
    /** resource id for the log message */
    private Long resourceId;

    /** Operator doing the action */
    private String operator;

    /** free text message to log */
    private String message;

    /** exception to append to the log message */
    private Throwable error;

    /** generated log message */
    private String logMessage = null;

    /**
     * Creates a log message. Any parameter can be null.
     *
     * @param resourceId the resource id to log.
     * @param operator the operator to log.
     * @param message a free text message.
     * @param error an exception to append to the log message.
     */
    public LogMessage(Long resourceId, String operator, String message, Throwable error) {
        this.resourceId = resourceId;

        this.operator = operator;

        this.message = message;

        this.error = error;
    }

    /**
     * Creates a log message. Any parameter can be null.
     *
     * @param resourceId the resource id to log.
     * @param operator the operator to log.
     * @param message a free text message.
     */
    public LogMessage(Long resourceId, String operator, String message) {
        this(resourceId, operator, message, null);
    }

    /**
     * Return the error cause.Can be null.
     *
     * @return the error cause.
     */
    public Throwable getError() {
        return error;
    }

    /**
     * Returns the message.
     *
     * @return message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Returns the operator. Can be null.
     *
     * @return the operator
     */
    public String getOperator() {
        return operator;
    }

    /**
     * Returns the resource id, can be null.
     *
     * @return resource id
     */
    public Long getResourceId() {
        return resourceId;
    }

    /**
     * Generate the message need to be logged for this class.
     *
     * @return The log message
     */
    public String getLogMessage() {
        if (logMessage == null) {
            StringBuffer buffer = new StringBuffer();

            buffer.append("operator: ").append((operator == null) ? "Unknown" : operator).append("resourceId: ")
                  .append((resourceId == null) ? "Unknown" : resourceId.toString()).append(" - ").append(message);

            //This should be done while the Logging Wrapper 1.2 is used.

            //When the LW 1.3 would be ready, it will be possible pass the exception directly to LW.   
            if (error != null) {
                ByteArrayOutputStream out = new ByteArrayOutputStream();

                error.printStackTrace(new PrintStream(out));

                buffer.append('\n').append(out.toString());
            }

            logMessage = buffer.toString();
        }

        return logMessage;
    }

    /**
     * Override the toString method returns the log message.
     *
     * @return logged message
     */
    public String toString() {
        return getLogMessage();
    }
}
