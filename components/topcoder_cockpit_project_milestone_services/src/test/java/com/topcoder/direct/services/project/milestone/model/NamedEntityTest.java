/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.milestone.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * <p>
 * Unit tests for class <code>NamedEntity</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class NamedEntityTest {

    /**
     * Represents the <code>NamedEntity</code> instance used to test against.
     */
    private NamedEntity impl;

    /**
     * Creates a test suite for unit tests in this test case.
     *
     * @return a Test suite for this test case.
     */
    public static junit.framework.Test suite() {
        return new junit.framework.JUnit4TestAdapter(NamedEntityTest.class);
    }

    /**
     * Set up the test environment.
     *
     * @throws Exception
     *             to JUnit
     */
    @Before
    public void setUp() throws Exception {
        impl = new MockNamedEntity();
    }

    /**
     * Tear down the test environment.
     *
     * @throws Exception
     *             to JUnit
     */
    @After
    public void tearDown() throws Exception {
        impl = null;
    }

    /**
     * Inheritance test, verifies <code>NamedEntity</code> subclasses should be correct.
     */
    @SuppressWarnings("cast")
    @Test
    public void testInheritance() {
        assertTrue("The instance's subclass is not correct.", impl instanceof IdentifiableEntity);
    }

    /**
     * Accuracy test for the constructor <code>NamedEntity()</code>.<br>
     * Instance should be created successfully.
     */
    @Test
    public void testCtor() {
        assertNotNull("Instance should be created", impl);
    }

    /**
     * Accuracy test for the method <code>getName()</code>.<br>
     * Expects no error occurs.
     */
    @Test
    public void testGetName() {
        assertNull("The initial value should be null", impl.getName());
        String expect = "test";
        impl.setName(expect);
        assertEquals("The result should be same as", expect, impl.getName());
    }

    /**
     * Accuracy test for the method <code>setName(String)</code>.<br>
     * Expects no error occurs.
     */
    @Test
    public void testSetName() {
        assertNull("The initial value should be null", impl.getName());
        String expect = "test";
        impl.setName(expect);
        assertEquals("The result should be same as", expect, impl.getName());
    }

    /**
     * <p>
     * Mock class for.NamedEntity.
     * </p>
     *
     * @author TCSDEVELOPER
     * @version 1.0
     */
    class MockNamedEntity extends NamedEntity {
        // Empty
    }
}
