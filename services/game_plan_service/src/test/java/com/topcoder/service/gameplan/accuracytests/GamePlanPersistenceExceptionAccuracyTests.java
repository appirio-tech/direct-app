/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.gameplan.accuracytests;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.topcoder.service.gameplan.GamePlanPersistenceException;
import com.topcoder.util.errorhandling.ExceptionData;

/**
 * Set of tests for testing GamePlanPersistenceException class.
 * @author vilain
 * @version 1.0
 */
public class GamePlanPersistenceExceptionAccuracyTests {

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
     * Constructor under test for GamePlanPersistenceException(String). Accuracy
     * testing of correctly assigned exception message and data.
     */
	@Test
    public final void testGamePlanPersistenceExceptionString() {
        GamePlanPersistenceException exception = new GamePlanPersistenceException(message);
        // testing for equal messages
        assertEquals("messages must be equal", message, exception.getMessage());
    }

    /**
     * Constructor under test for GamePlanPersistenceException(String,
     * Throwable). Accuracy testing of correctly assigned exception message and
     * cause.
     */
	@Test
    public final void testGamePlanPersistenceExceptionStringThrowable() {
        GamePlanPersistenceException exception = new GamePlanPersistenceException(message, e);
        // testing for cause and message
        assertTrue("message must be included", exception.getMessage().indexOf(message) != -1);
        assertEquals("causes must be equal", e, exception.getCause());
    }

    /**
     * Constructor under test for GamePlanPersistenceException(String,
     * ExceptionData). Accuracy testing of correctly assigned exception message
     * and data.
     */
	@Test
    public final void testGamePlanPersistenceExceptionStringExceptionData() {
        GamePlanPersistenceException exception = new GamePlanPersistenceException(message, exceptionData);
        // testing for cause and message
        assertTrue("message must be included", exception.getMessage().indexOf(message) != -1);
        assertEquals("exception datas must be equal", exceptionData.getCreationDate(), exception
            .getCreationDate());
    }

    /**
     * Constructor under test for GamePlanPersistenceException(String,
     * Throwable, ExceptionData). Accuracy testing of correctly assigned
     * exception message, cause, and data.
     */
	@Test
    public final void testGamePlanPersistenceExceptionStringThrowableExceptionData() {
        GamePlanPersistenceException exception = new GamePlanPersistenceException(message, e, exceptionData);
        // testing for cause and message
        assertTrue("message must be included", exception.getMessage().indexOf(message) != -1);
        assertEquals("causes must be equal", e, exception.getCause());
        assertEquals("exception datas must be equal", exceptionData.getCreationDate(), exception
            .getCreationDate());
    }
}