/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.direct.services.copilot.dao;

import com.topcoder.util.errorhandling.BaseRuntimeException;
import com.topcoder.util.errorhandling.ExceptionData;
import junit.framework.JUnit4TestAdapter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * <p>Tests <code>{@link CopilotDAOInitializationException}</code> class.</p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class CopilotDAOInitializationExceptionTests {

    /**
     * <p>Represents error message used for testing exception constructor.</p>
     */
    private static final String MESSAGE = "MESSAGE";

    /**
     * <p>Represents error cause used for testing exception constructor.</p>
     */
    private static final Exception CAUSE = new Exception("UnitTest");

    /**
     * <p>Represents application code used for testing exception constructor.</p>
     */
    private static final String APPLICATION_CODE = "applicationCode";

    /**
     * <p>Represents error code used for testing exception constructor.</p>
     */
    private static final String ERROR_CODE = "errorCode";

    /**
     * <p>Represents module code used for testing exception constructor.</p>
     */
    private static final String MODULE_CODE = "moduleCode";

    /**
     * <p>Represents exception data used for testing exception constructor.</p>
     */
    private ExceptionData exceptionData;

    /**
     * <p>Returns test suite for this class.</p>
     *
     * @return test suite for this class
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(CopilotDAOInitializationExceptionTests.class);
    }

    /**
     * <p>Sets up test environment.</p>
     *
     * @throws Exception if any error occurs
     */
    @Before
    public void setUp() throws Exception {

        exceptionData = new ExceptionData();
        exceptionData.setApplicationCode(APPLICATION_CODE);
        exceptionData.setErrorCode(ERROR_CODE);
        exceptionData.setModuleCode(MODULE_CODE);
    }

    /**
     * <p>Tests <code>{@link CopilotDAOInitializationException}</code> base class.</p>
     */
    @Test
    public void testInheritance() {
        Class clazz = CopilotDAOInitializationException.class;

        Assert.assertEquals("CopilotDAOInitializationException has invalid base class.",
                BaseRuntimeException.class, clazz.getSuperclass());
    }

    /**
     * <p>Tests <code>{@link CopilotDAOInitializationException#CopilotDAOInitializationException(String)} </code>
     * constructor taking only message as parameter.</p>
     */
    @Test
    public void testCtorWithMessage() {
        CopilotDAOInitializationException exc = new CopilotDAOInitializationException(MESSAGE);

        Assert.assertEquals("Exception has invalid message.", MESSAGE, exc.getMessage());
    }

    /**
     * <p>Tests <code>{@link CopilotDAOInitializationException#CopilotDAOInitializationException(String, ExceptionData)}
     * </code> constructor taking message and exception data as parameters.</p>
     */
    @Test
    public void testCtorWithMessageAndErrorData() {

        CopilotDAOInitializationException exc = new CopilotDAOInitializationException(MESSAGE, exceptionData);

        Assert.assertEquals("Exception has invalid message.", MESSAGE, exc.getMessage());
        Assert.assertEquals("Exception has invalid application code.", APPLICATION_CODE, exc.getApplicationCode());
        Assert.assertEquals("Exception has invalid error code.", ERROR_CODE, exc.getErrorCode());
        Assert.assertEquals("Exception has invalid module code.", MODULE_CODE, exc.getModuleCode());
    }

    /**
     * <p>Tests <code>{@link CopilotDAOInitializationException#CopilotDAOInitializationException(String, Throwable)}
     * </code> constructor taking both message and cause as parameters.</p>
     */
    @Test
    public void testCtorWithMessageAndCause() {
        CopilotDAOInitializationException exc = new CopilotDAOInitializationException(MESSAGE, CAUSE);

        Assert.assertEquals("Exception has invalid message.", MESSAGE, exc.getMessage());
        Assert.assertSame("Exception has invalid cause", CAUSE, exc.getCause());
    }

    /**
     * <p>Tests <code>{@link CopilotDAOInitializationException#CopilotDAOInitializationException(String, Throwable,
     * ExceptionData)} </code> constructor taking message, cause and exception data as parameters.</p>
     */
    @Test
    public void testCtorWithMessageAndCauseAndExceptionData() {
        CopilotDAOInitializationException exc = new CopilotDAOInitializationException(MESSAGE, CAUSE, exceptionData);

        Assert.assertEquals("Exception has invalid message.", MESSAGE, exc.getMessage());
        Assert.assertSame("Exception has invalid cause", CAUSE, exc.getCause());
        Assert.assertEquals("Exception has invalid application code.", APPLICATION_CODE, exc.getApplicationCode());
        Assert.assertEquals("Exception has invalid error code.", ERROR_CODE, exc.getErrorCode());
        Assert.assertEquals("Exception has invalid module code.", MODULE_CODE, exc.getModuleCode());
    }
}
