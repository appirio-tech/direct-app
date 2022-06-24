/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.phases.accuracytests;

import junit.framework.TestCase;

import com.topcoder.project.phases.CyclicDependencyException;
import com.topcoder.util.errorhandling.BaseRuntimeException;

/**
 * Accuracy test for CyclicDependencyException class.
 *
 * @author XuChuan, mayi
 * @version 2.0
 * @since 1.0
 */
public class CyclicDependencyExceptionAccuracyTest extends TestCase {

    /**
     * Test the constructor with non-null message.
     */
    public void testConstructor_Message() {
        CyclicDependencyException e = new CyclicDependencyException("test");
        assertEquals("test1", "test", e.getMessage());
    }

    /**
     * Test the construcotr.
     * <p>This case will be used to validate the inheriting relationship.</p>
     */
    public void testConstructor_Inherit() {
        Object e = new CyclicDependencyException("test");
        assertTrue("It should inherit from BaseRuntimeException.",
                e instanceof BaseRuntimeException);
    }
}