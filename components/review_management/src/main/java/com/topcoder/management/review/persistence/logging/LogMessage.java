/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.review.persistence.logging;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;


/**
 * Encapsulates the entry log data and generates consistent log messages.
 */
public class LogMessage {
    /** project id for the log message */
    private Long reviewId;

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
     * @param reviewId the project id to log.
     * @param operator the operator to log.
     * @param message a free text message.
     * @param error an exception to append to the log message.
     */
    public LogMessage(Long reviewId, String operator, String message, Throwable error) {
        this.reviewId = reviewId;

        this.operator = operator;

        this.message = message;

        this.error = error;
    }

    /**
     * Creates a log message. Any parameter can be null
     *
     * @param reviewId the project id to log.
     * @param operator the operator to log.
     * @param message a free text message.
     */
    public LogMessage(Long reviewId, String operator, String message) {
        this(reviewId, operator, message, null);
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
     * Returns the review id, can be null.
     *
     * @return project id
     */
    public Long getReviewId() {
        return reviewId;
    }

    /**
     * Generate the message need to be logged for this class.
     *
     * @return The log message
     */
    public String getLogMessage() {
        if (logMessage == null) {
            StringBuffer buffer = new StringBuffer();

            buffer.append("operator: ").append((operator == null) ? "Unknown" : operator).append("reviewId: ")
                  .append((reviewId == null) ? "Unknown" : reviewId.toString()).append(" - ").append(message);

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
     * Return the exception stack trace string.
     *
     * @param cause the exception to be recorded
     *
     * @return stack strace
     */
    public static String getExceptionStackTrace(Throwable cause) {
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
