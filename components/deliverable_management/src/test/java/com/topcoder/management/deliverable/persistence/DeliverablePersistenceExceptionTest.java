/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable.persistence;

import junit.framework.TestCase;


/**
 * Unit tests for DeliverablePersistenceException.
 *
 * @author singlewood
 * @version 1.0
 */
public class DeliverablePersistenceExceptionTest extends TestCase {
    /**
     * Test the constructor with one message parameter.
     * The object should be created correctly.
     */
    public void testConstructor1() {
        DeliverablePersistenceException e = new DeliverablePersistenceException("message");

        assertNotNull("The object should not be null", e);
    }

    /**
     * Test the constructor with two parameters.
     * The object should be created correctly.
     */
    public void testConstructor2() {
        DeliverablePersistenceException e =
            new DeliverablePersistenceException("message", new Exception());

        assertNotNull("The object should not be null", e);
    }
}
