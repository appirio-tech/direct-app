/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.direct.services.copilot.dao;

import com.topcoder.util.errorhandling.ExceptionData;
import junit.framework.JUnit4TestAdapter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * <p>Tests <code>{@link EntityNotFoundException}</code> class.</p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class EntityNotFoundExceptionTests {

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
     * <p>Represents entity type name used for testing exception constructor.</p>
     */
    private static final String ENTITY_TYPE_NAME = "entityTypeName";

    /**
     * <p>Represents entity id used for testing exception constructor.</p>
     */
    private static final long ENTITY_ID = 10L;

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
        return new JUnit4TestAdapter(EntityNotFoundExceptionTests.class);
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
     * <p>Tests <code>{@link EntityNotFoundException}</code> base class.</p>
     */
    @Test
    public void testInheritance() {
        Class clazz = EntityNotFoundException.class;

        Assert.assertEquals("EntityNotFoundException has invalid base class.",
                CopilotDAOException.class, clazz.getSuperclass());
    }

    /**
     * <p>Tests <code>{@link EntityNotFoundException#EntityNotFoundException(String, String, long)} </code> constructor
     * taking only message as parameter.</p>
     */
    @Test
    public void testCtorWithMessage() {
        EntityNotFoundException exc = new EntityNotFoundException(MESSAGE, ENTITY_TYPE_NAME, ENTITY_ID);

        Assert.assertEquals("Exception has invalid message.", MESSAGE, exc.getMessage());
        Assert.assertEquals("Exception has invalid entity type name.", ENTITY_TYPE_NAME, exc.getEntityTypeName());
        Assert.assertEquals("Exception has invalid entity id.", ENTITY_ID, exc.getEntityId());
    }

    /**
     * <p>Tests <code>{@link EntityNotFoundException#EntityNotFoundException(String, ExceptionData, String, long)}
     * </code> constructor taking message and exception data as parameters.</p>
     */
    @Test
    public void testCtorWithMessageAndErrorData() {

        EntityNotFoundException exc = new EntityNotFoundException(MESSAGE, exceptionData, ENTITY_TYPE_NAME, ENTITY_ID);

        Assert.assertEquals("Exception has invalid message.", MESSAGE, exc.getMessage());
        Assert.assertEquals("Exception has invalid application code.", APPLICATION_CODE, exc.getApplicationCode());
        Assert.assertEquals("Exception has invalid error code.", ERROR_CODE, exc.getErrorCode());
        Assert.assertEquals("Exception has invalid module code.", MODULE_CODE, exc.getModuleCode());

        Assert.assertEquals("Exception has invalid entity type name.", ENTITY_TYPE_NAME, exc.getEntityTypeName());
        Assert.assertEquals("Exception has invalid entity id.", ENTITY_ID, exc.getEntityId());
    }

    /**
     * <p>Tests <code>{@link EntityNotFoundException#EntityNotFoundException(String, Throwable, String, long)} </code>
     * constructor taking both message and cause as parameters.</p>
     */
    @Test
    public void testCtorWithMessageAndCause() {
        EntityNotFoundException exc = new EntityNotFoundException(MESSAGE, CAUSE, ENTITY_TYPE_NAME, ENTITY_ID);

        Assert.assertEquals("Exception has invalid message.", MESSAGE, exc.getMessage());
        Assert.assertSame("Exception has invalid cause", CAUSE, exc.getCause());

        Assert.assertEquals("Exception has invalid entity type name.", ENTITY_TYPE_NAME, exc.getEntityTypeName());
        Assert.assertEquals("Exception has invalid entity id.", ENTITY_ID, exc.getEntityId());
    }

    /**
     * <p>Tests <code>{@link EntityNotFoundException#EntityNotFoundException(String, Throwable, ExceptionData, String,
     * long)} </code> constructor taking message, cause and exception data as parameters.</p>
     */
    @Test
    public void testCtorWithMessageAndCauseAndExceptionData() {
        EntityNotFoundException exc = new EntityNotFoundException(MESSAGE, CAUSE, exceptionData,
                ENTITY_TYPE_NAME, ENTITY_ID);

        Assert.assertEquals("Exception has invalid message.", MESSAGE, exc.getMessage());
        Assert.assertSame("Exception has invalid cause", CAUSE, exc.getCause());
        Assert.assertEquals("Exception has invalid application code.", APPLICATION_CODE, exc.getApplicationCode());
        Assert.assertEquals("Exception has invalid error code.", ERROR_CODE, exc.getErrorCode());
        Assert.assertEquals("Exception has invalid module code.", MODULE_CODE, exc.getModuleCode());

        Assert.assertEquals("Exception has invalid entity type name.", ENTITY_TYPE_NAME, exc.getEntityTypeName());
        Assert.assertEquals("Exception has invalid entity id.", ENTITY_ID, exc.getEntityId());
    }

    /**
     * <p>Tests <code>{@link EntityNotFoundException#getEntityTypeName()}</code> method.</p>
     */
    @Test
    public void testGetEntityTypeName() {
        EntityNotFoundException exc = new EntityNotFoundException(MESSAGE, ENTITY_TYPE_NAME, ENTITY_ID);

        Assert.assertEquals("Exception has invalid entity type name.", ENTITY_TYPE_NAME, exc.getEntityTypeName());
    }

    /**
     * <p>Tests <code>{@link EntityNotFoundException#getEntityId()}</code> method.</p>
     */
    @Test
    public void testGetEntityId() {
        EntityNotFoundException exc = new EntityNotFoundException(MESSAGE, ENTITY_TYPE_NAME, ENTITY_ID);

        Assert.assertEquals("Exception has invalid entity id.", ENTITY_ID, exc.getEntityId());
    }
}
