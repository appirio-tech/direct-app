/*
 * Copyright (C) 2007-2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable.logging;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import com.topcoder.management.deliverable.DeliverableHelper;


/**
 * <p>
 * Encapsulates the entry log data and generates consistent log messages.
 * </p>
 *
 * <p>
 * <em>Changes in 1.1: </em>
 * <ul>
 * <li>Made just some trivial fixes to meet TopCoder standards.</li>
 * </ul>
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> This class is mutable and not thread safe.
 * </p>
 *
 * @author aubergineanode, singlewood, saarixx, sparemax
 * @version 1.1
 */
public class LogMessage {
    /**
     * <p>
     * The ID for the log message. Is initialized in the constructor and never changed after that. Can be any value.
     * Has a getter.
     * </p>
     *
     * <p>
     * <em>Changes in 1.1: </em>
     * <ul>
     * <li>Made final.</li>
     * </ul>
     * </p>
     */
    private final Long id;

    /**
     * <p>
     * The operator performing the action. Is initialized in the constructor and never changed after that. Can be any
     * value. Has a getter.
     * </p>
     *
     * <p>
     * <em>Changes in 1.1: </em>
     * <ul>
     * <li>Made final.</li>
     * </ul>
     * </p>
     */
    private final String operator;

    /**
     * <p>
     * The type of the action. Is initialized in the constructor and never changed after that. Can be any value. Has a
     * getter.
     * </p>
     *
     * <p>
     * <em>Changes in 1.1: </em>
     * <ul>
     * <li>Made final.</li>
     * </ul>
     * </p>
     */
    private final String type;

    /**
     * <p>
     * The text message to be logged. Is initialized in the constructor and never changed after that. Can be any
     * value. Has a getter.
     * </p>
     *
     * <p>
     * <em>Changes in 1.1: </em>
     * <ul>
     * <li>Made final.</li>
     * </ul>
     * </p>
     */
    private final String message;

    /**
     * <p>
     * The exception to be appended to the log message. Is initialized in the constructor and never changed after
     * that. Can be any value. Has a getter.
     * </p>
     *
     * <p>
     * <em>Changes in 1.1: </em>
     * <ul>
     * <li>Made final.</li>
     * </ul>
     * </p>
     */
    private final Throwable error;

    /**
     * <p>
     * The generated log message log message. Is initialized with not null value in getLogMessage() and never changed
     * after that.
     * </p>
     */
    private String logMessage;

    /**
     * Creates a log message. Any parameter can be null.
     *
     * @param type the operator to log.
     * @param id the project id to log.
     * @param operator DOCUMENT ME!
     * @param message a free text message.
     * @param error an exception to append to the log message.
     */
    public LogMessage(String type, Long id, String operator, String message, Throwable error) {
        this.id = id;

        this.type = type;

        this.operator = operator;

        this.message = message;

        this.error = error;
    }

    /**
     * Creates a log message. Any parameter can be null
     *
     * @param type the operator to log.
     * @param id the project id to log.
     * @param operator DOCUMENT ME!
     * @param message a free text message.
     */
    public LogMessage(String type, Long id, String operator, String message) {
        this(type, id, operator, message, null);
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
     * Returns the type. Can be null.
     *
     * @return the operator
     */
    public String getType() {
        return type;
    }

    /**
     * Returns the id, can be null.
     *
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * Generate the message need to be logged for this class.
     *
     * @return The log message
     */
    public String getLogMessage() {
        if (logMessage == null) {
            StringBuffer buffer = new StringBuffer();

            buffer.append("type: ").append((type == null) ? "Unknown" : type).append(" id: ")
                  .append((id == null) ? "Unknown" : id.toString()).append(" operator:")
                  .append((operator == null) ? "Unknown" : operator).append(" - ").append(message);

            //This should be done while the Logging Wrapper 1.2 is used.

            //When the LW 1.3 would be ready, it will be possible pass the exception directly to LW.
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
     *
     * <p>
     * <em>Changes in 1.1: </em>
     * <ul>
     * <li>Argument checking must be added to avoid NullPointerException.</li>
     * </ul>
     * </p>
     *
     * @param cause
     *            the exception to be recorded.
     *
     * @return the exception stack trace string.
     *
     * @throws IllegalArgumentException
     *             if cause is <code>null</code>.
     */
    public static String getExceptionStackTrace(Throwable cause) {
        DeliverableHelper.checkObjectNotNull(cause, "cause", null);

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        cause.printStackTrace(new PrintStream(out));

        return out.toString();
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
