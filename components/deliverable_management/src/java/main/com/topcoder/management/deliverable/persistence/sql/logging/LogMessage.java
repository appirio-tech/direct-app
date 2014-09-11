/*
 * Copyright (C) 2007-2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable.persistence.sql.logging;

import com.topcoder.management.deliverable.persistence.sql.Helper;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * <p>
 * Encapsulates the entry log data and generates consistent log messages.
 * </p>
 * <p>
 * Changes in 1.1: All attributes except for logMessage became final.
 * </p>
 * <p>
 * Changes in 1.2:
 * <ul>
 * <li>Code reformatted</li>
 * <li>parameter check method modified according to the new change</li>
 * </ul>
 * </p>
 * <p>
 * <strong>Thread Safety:</strong> This class is immutable so it's thread safe.
 * </p>
 *
 * @author aubergineanode, saarixx, urtks, TCSDEVELOPER
 * @version 1.2
 */
public class LogMessage {

    /**
     * <p>
     * The ID for the log message. Is initialized in the constructor and never changed after that. Can be any value.
     * </p>
     * <p>
     * Changes in 1.1: Made final.
     * </p>
     */
    private final Long id;

    /**
     * <p>
     * The operator performing the action. Is initialized in the constructor and never changed after that. Can be any
     * value.
     * </p>
     * <p>
     * Changes in 1.1: Made final.
     * </p>
     */
    private final String operator;

    /**
     * <p>
     * The type of the action. Is initialized in the constructor and never changed after that. Can be any value.
     * </p>
     * <p>
     * Changes in 1.1: Made final.
     * </p>
     */
    private final String type;

    /**
     * <p>
     * The text message to be logged. Is initialized in the constructor and never changed after that. Can be any value.
     * </p>
     * <p>
     * Changes in 1.1: Made final.
     * </p>
     */
    private final String message;

    /**
     * <p>
     * The exception to be appended to the log message. Is initialized in the constructor and never changed after that.
     * Can be any value.
     * </p>
     * <p>
     * Changes in 1.1: Made final.
     * </p>
     */
    private final Throwable error;

    /**
     * <p>
     * The generated log message log message. Is initialized with not null value in getLogMessage() and never changed
     * after that.
     * </p>
     */
    private String logMessage = null;

    /**
     * <p>
     * Creates a log message. Any parameter can be null.
     * </p>
     *
     * @param type
     *            the operator to log.
     * @param id
     *            the project id to log.
     * @param operator
     *            the operator performing the action.
     * @param message
     *            a free text message.
     * @param error
     *            an exception to append to the log message.
     */
    public LogMessage(String type, Long id, String operator, String message, Throwable error) {

        this.id = id;
        this.type = type;
        this.operator = operator;
        this.message = message;
        this.error = error;
    }

    /**
     * <p>
     * Creates a log message. Any parameter can be null.
     * </p>
     *
     * @param type
     *            the operator to log.
     * @param id
     *            the project id to log.
     * @param operator
     *            the operator performing the action.
     * @param message
     *            a free text message.
     */
    public LogMessage(String type, Long id, String operator, String message) {
        this(type, id, operator, message, null);
    }

    /**
     * <p>Retrieves the ID for the log message.</p>
     *
     * @return the ID for the log message
     */
    public Long getId() {
        return id;
    }

    /**
     * <p>
     * Retrieves the type of the action.
     * </p>
     *
     * @return the type of the action
     */
    public String getType() {
        return type;
    }

    /**
     * <p>
     * Retrieves the text message to be logged.
     * </p>
     *
     * @return the text message to be logged
     */
    public String getMessage() {
        return message;
    }

    /**
     * <p>
     * Retrieves the exception to be appended to the log message.
     * </p>
     *
     * @return the exception to be appended to the log message
     */
    public Throwable getError() {
        return error;
    }

    /**
     * <p>
     * Generates the message need to be logged for this class.
     * </p>
     *
     * @return The log message
     */
    public String getLogMessage() {
        if (logMessage == null) {
            StringBuffer buffer = new StringBuffer();

            buffer.append("type: ").append((type == null) ? "Unknown" : type).append(" id: ").append(
                    (id == null) ? "Unknown" : id.toString()).append(" operator:").append(
                    (operator == null) ? "Unknown" : operator).append(" - ").append(message);

            // This should be done while the Logging Wrapper 1.2 is used.

            // When the LW 1.3 would be ready, it will be possible pass the exception directly to LW.
            if (error != null) {
                buffer.append('\n').append(getExceptionStackTrace(error));
            }

            logMessage = buffer.toString();
        }

        return logMessage;
    }

    /**
     * <p>
     * Returns the exception stack trace string.
     * </p>
     * <p>
     * Changes in 1.1: Parameter checking - IllegalArgumentException is thrown in case the cause is null.
     * </p>
     *
     * @param cause
     *            the exception to be recorded
     * @return stack the exception stack trace string
     * @throws IllegalArgumentException
     *             if cause is null
     */
    public static String getExceptionStackTrace(Throwable cause) {
        Helper.assertObjectNotNull(cause, "cause", null);

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        cause.printStackTrace(new PrintStream(out));

        return out.toString();
    }

    /**
     * <p>
     * Overrides the toString method: returns the log message.
     * </p>
     *
     * @return the log message
     */
    public String toString() {
        return getLogMessage();
    }
}
