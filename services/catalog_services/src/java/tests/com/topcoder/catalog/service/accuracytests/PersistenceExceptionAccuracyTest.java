/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.catalog.service.accuracytests;

import com.topcoder.catalog.service.PersistenceException;

import junit.framework.TestCase;

/**
 * <p>
 * Accuracy test for <code>PersistenceException</code> class.
 * </p>
 *
 * @author Retunsky
 * @version 1.0
 */
public class PersistenceExceptionAccuracyTest extends TestCase {

    /**
     * <p>
     * Accuracy test for the constructor <code>PersistenceException(String message)</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCtor1Accuracy() throws Exception {
        Exception ex = new PersistenceException("msg");
        assertNotNull("'ex' should not be null.", ex);
        assertEquals("Exception message mismatched.", "msg", ex.getMessage());
    }

    /**
     * <p>
     * Accuracy test for the constructor
     * <code>PersistenceException(String message, Throwable cause)</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCtor2Accuracy() throws Exception {
        Exception ex = new PersistenceException("message", new Exception("exception"));
        assertNotNull("'ex' should not be null.", ex);
        assertEquals("Exception message mismatched.", "message", ex.getMessage());
    }
}
