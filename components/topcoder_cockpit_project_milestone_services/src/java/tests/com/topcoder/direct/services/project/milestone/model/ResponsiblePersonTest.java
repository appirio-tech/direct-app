/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.milestone.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * <p>
 * Unit tests for class <code>ResponsiblePerson</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ResponsiblePersonTest {

    /**
     * Represents the <code>ResponsiblePerson</code> instance used to test against.
     */
    private ResponsiblePerson impl;

    /**
     * Creates a test suite for unit tests in this test case.
     *
     * @return a Test suite for this test case.
     */
    public static junit.framework.Test suite() {
        return new junit.framework.JUnit4TestAdapter(ResponsiblePersonTest.class);
    }

    /**
     * Set up the test environment.
     *
     * @throws Exception
     *             to JUnit
     */
    @Before
    public void setUp() throws Exception {
        impl = new ResponsiblePerson();
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
     * Inheritance test, verifies <code>ResponsiblePerson</code> subclasses should be correct.
     */
    @SuppressWarnings("cast")
    @Test
    public void testInheritance() {
        assertTrue("The instance's subclass is not correct.", impl instanceof NamedEntity);
    }

    /**
     * Accuracy test for the constructor <code>ResponsiblePerson()</code>.<br>
     * Instance should be created successfully.
     */
    @Test
    public void testCtor() {
        assertNotNull("Instance should be created", impl);
    }

    /**
     * Accuracy test for the method <code>getUserId()</code>.<br>
     * Expects no error occurs.
     */
    @Test
    public void testGetUserId() {
        assertEquals("The initial value should be same as", 0, impl.getUserId());
        long expect = 1L;
        impl.setUserId(expect);
        assertEquals("The result should be same as", expect, impl.getUserId());
    }

    /**
     * Accuracy test for the method <code>setUserId(long)</code>.<br>
     * Expects no error occurs.
     */
    @Test
    public void testSetUserId() {
        assertEquals("The initial value should be same as", 0, impl.getUserId());
        long expect = 1L;
        impl.setUserId(expect);
        assertEquals("The result should be same as", expect, impl.getUserId());
    }

    /**
     * Accuracy test for the method <code>toString()</code>.<br>
     * Expects no error occurs.
     */
    @Test
    public void testToString() {
        impl.setId(1);
        impl.setName("name1");
        impl.setUserId(123456);

        String expect = "{id:1, name:name1, userId:123456}";
        assertEquals("The result should be same as", expect, impl.toString());
    }

}
