/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.service.gameplan;

import com.topcoder.util.errorhandling.ExceptionData;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

/**
 * <p>Unit test for <code>GamePlanServiceConfigurationException</code> class.</p>
 *
 * @author FireIce
 * @version 1.0
 */
public class GamePlanServiceConfigurationExceptionTests {

    /**
     * <p>Represents a string with a detail message.</p>
     */
    private static final String DETAIL_MESSAGE = "detail";

    /**
     * <p>Represents a throwable cause.</p>
     */
    private static final Throwable CAUSE = new Exception("UnitTest");

    /**
     * <p> Represents the exception data. </p>
     */
    private static final ExceptionData EXCEPTION_DATA = new ExceptionData();

    /**
     * <p> Represents the application code set in exception data. </p>
     */
    private static final String APPLICATION_CODE = "Accuracy";

    static {
        EXCEPTION_DATA.setApplicationCode(APPLICATION_CODE);
    }

    /**
     * <p>Tests the <code>GamePlanServiceConfigurationException(String)</code> constructor.</p>
     *
     * <p>The detail error message should be correct.</p>
     */
    @Test
    public void testGamePlanServiceConfigurationExceptionStringAccuracy() {
        // Construct GamePlanServiceConfigurationException with a detail message
        GamePlanServiceConfigurationException exception = new GamePlanServiceConfigurationException(DETAIL_MESSAGE);

        // Verify that there is a detail message
        assertNotNull("Should have message.", exception.getMessage());
        assertEquals("Detailed error message should be identical.", DETAIL_MESSAGE, exception.getMessage());
    }

    /**
     * <p>Tests the <code>GamePlanServiceConfigurationException(String, Throwable)</code> constructor.</p>
     *
     * <p>The detail error message and the original cause of error should be correct.</p>
     */
    @Test
    public void testGamePlanServiceConfigurationExceptionStringThrowableAccuracy() {
        // Construct GamePlanServiceConfigurationException with a detail message and a cause
        GamePlanServiceConfigurationException exception =
                new GamePlanServiceConfigurationException(DETAIL_MESSAGE, CAUSE);

        // Verify that there is a detail message
        assertNotNull("Should have message.", exception.getMessage());
        assertEquals("Detailed error message with cause should be correct.", DETAIL_MESSAGE, exception.getMessage());

        // Verify that there is a cause
        assertNotNull("Should have cause.", exception.getCause());
        assertSame("Cause should be identical.", CAUSE, exception.getCause());
    }

    /**
     * <p>Tests accuracy of <code>GamePlanServiceConfigurationException(String, ExceptionData)</code> constructor. The
     * detail error message and the exception data should be correct.</p>
     */
    @Test
    public void testGamePlanServiceConfigurationExceptionStringExceptionDataAccuracy() {
        // Construct GamePlanServiceConfigurationException with a detail message and a cause
        GamePlanServiceConfigurationException exception = new GamePlanServiceConfigurationException(DETAIL_MESSAGE,
                EXCEPTION_DATA);

        // Verify that there is a detail message
        assertNotNull("Should have message.", exception.getMessage());
        assertEquals("Detailed error message with cause should be correct.", DETAIL_MESSAGE, exception.getMessage());

        // Verify that the exception data is correctly set.
        assertNotNull("application code should not null", exception.getApplicationCode());
        assertEquals("exception data is not set.", APPLICATION_CODE, exception.getApplicationCode());
    }

    /**
     * <p>Tests accuracy of <code>GamePlanServiceConfigurationException(String, Throwable, ExceptionData)</code>
     * constructor. The detail error message, the cause exception and the exception data should be correct.</p>
     */
    @Test
    public void testGamePlanServiceConfigurationExceptionStringThrowableExceptionDataAccuracy() {
        // Construct GamePlanServiceConfigurationException with a detail message and a cause
        GamePlanServiceConfigurationException exception =
                new GamePlanServiceConfigurationException(DETAIL_MESSAGE, CAUSE,
                        EXCEPTION_DATA);

        // Verify that there is a detail message
        assertNotNull("Should have message.", exception.getMessage());
        assertEquals("Detailed error message with cause should be correct.", DETAIL_MESSAGE, exception.getMessage());

        // Verify that the exception data is correctly set.
        assertNotNull("application code should not null", exception.getApplicationCode());
        assertEquals("exception data is not set.", APPLICATION_CODE, exception.getApplicationCode());

        // Verify that there is a cause
        assertNotNull("Should have cause.", exception.getCause());
        assertSame("Cause should be identical.", CAUSE, exception.getCause());
    }
}