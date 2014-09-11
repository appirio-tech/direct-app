/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.gameplan.accuracytests;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.topcoder.service.gameplan.GamePlanServiceException;
import com.topcoder.util.errorhandling.ExceptionData;

/**
 * Set of tests for testing GamePlanServiceException class.
 * @author vilain
 * @version 1.0
 */
public class GamePlanServiceExceptionAccuracyTests {

    /**
     * Exception message for testing.
     */
    private String message = "message";

    /**
     * Exception for testing.
     */
    private Exception e = new Exception();

    /**
     * Instance of ExceptionData for testing.
     */
    private ExceptionData exceptionData = new ExceptionData();

    /**
     * Constructor under test for GamePlanServiceException(String). Accuracy
     * testing of correctly assigned exception message and data.
     */
	@Test
    public final void testGamePlanServiceExceptionString() {
        GamePlanServiceException exception = new GamePlanServiceException(message);
        // testing for equal messages
        assertEquals("messages must be equal", message, exception.getMessage());
    }

    /**
     * Constructor under test for GamePlanServiceException(String, Throwable).
     * Accuracy testing of correctly assigned exception message and cause.
     */
	@Test
    public final void testGamePlanServiceExceptionStringThrowable() {
        GamePlanServiceException exception = new GamePlanServiceException(message, e);
        // testing for cause and message
        assertTrue("message must be included", exception.getMessage().indexOf(message) != -1);
        assertEquals("causes must be equal", e, exception.getCause());
    }

    /**
     * Constructor under test for GamePlanServiceException(String,
     * ExceptionData). Accuracy testing of correctly assigned exception message
     * and data.
     */
	@Test
    public final void testGamePlanServiceExceptionStringExceptionData() {
        GamePlanServiceException exception = new GamePlanServiceException(message, exceptionData);
        // testing for cause and message
        assertTrue("message must be included", exception.getMessage().indexOf(message) != -1);
        assertEquals("exception datas must be equal", exceptionData.getCreationDate(), exception
            .getCreationDate());
    }

    /**
     * Constructor under test for GamePlanServiceException(String, Throwable,
     * ExceptionData). Accuracy testing of correctly assigned exception message,
     * cause, and data.
     */
	@Test
    public final void testGamePlanServiceExceptionStringThrowableExceptionData() {
        GamePlanServiceException exception = new GamePlanServiceException(message, e, exceptionData);
        // testing for cause and message
        assertTrue("message must be included", exception.getMessage().indexOf(message) != -1);
        assertEquals("causes must be equal", e, exception.getCause());
        assertEquals("exception datas must be equal", exceptionData.getCreationDate(), exception
            .getCreationDate());
    }
}