/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project.persistence.logging;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;


/**
 * Encapsulates the entry log data and generates consistent log messages.
 */
public class LogMessage {
    /** project id for the log message */
    private Long projectId;

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
     * @param projectId the project id to log.
     * @param operator the operator to log.
     * @param message a free text message.
     * @param error an exception to append to the log message.
     */
    public LogMessage(Long projectId, String operator, String message, Throwable error) {
        this.projectId = projectId;

        this.operator = operator;

        this.message = message;

        this.error = error;
    }

    /**
     * Creates a log message. Any parameter can be null
     *
     * @param projectId the project id to log.
     * @param operator the operator to log.
     * @param message a free text message.
     */
    public LogMessage(Long projectId, String operator, String message) {
        this(projectId, operator, message, null);
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
     * Returns the project id, can be null.
     *
     * @return project id
     */
    public Long getProjectId() {
        return projectId;
    }

    /**
     * Generate the message need to be logged for this class.
     *
     * @return The log message
     */
    public String getLogMessage() {
        if (logMessage == null) {
            StringBuffer buffer = new StringBuffer();

            buffer.append("operator: ").append((operator == null) ? "Unknown" : operator).append("projectId: ")
                  .append((projectId == null) ? "Unknown" : projectId.toString()).append(" - ").append(message);

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
