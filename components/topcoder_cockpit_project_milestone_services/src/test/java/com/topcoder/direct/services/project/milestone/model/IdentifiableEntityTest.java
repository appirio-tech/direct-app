/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.milestone.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * <p>
 * Unit tests for class <code>IdentifiableEntity</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class IdentifiableEntityTest {

    /**
     * Represents the <code>IdentifiableEntity</code> instance used to test against.
     */
    private IdentifiableEntity impl;

    /**
     * Creates a test suite for unit tests in this test case.
     *
     * @return a Test suite for this test case.
     */
    public static junit.framework.Test suite() {
        return new junit.framework.JUnit4TestAdapter(IdentifiableEntityTest.class);
    }

    /**
     * Set up the test environment.
     *
     * @throws Exception
     *             to JUnit
     */
    @Before
    public void setUp() throws Exception {
        impl = new MockIdentifiableEntity();
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
     * Accuracy test for the constructor <code>IdentifiableEntity()</code>.<br>
     * Instance should be created successfully.
     */
    @Test
    public void testCtor() {
        assertNotNull("Instance should be created", impl);
    }

    /**
     * Accuracy test for the method <code>getId()</code>.<br>
     * Expects no error occurs.
     */
    @Test
    public void testGetId() {
        assertEquals("The initial value should be same as", 0, impl.getId());
        long expect = 1L;
        impl.setId(expect);
        assertEquals("The result should be same as", expect, impl.getId());
    }

    /**
     * Accuracy test for the method <code>setId(long)</code>.<br>
     * Expects no error occurs.
     */
    @Test
    public void testSetId() {
        assertEquals("The initial value should be same as", 0, impl.getId());
        long expect = 1L;
        impl.setId(expect);
        assertEquals("The result should be same as", expect, impl.getId());
    }

    /**
     * Accuracy test for the method <code>equals(Object)</code>.<br>
     * Expects no error occurs.
     */
    @Test
    public void testEqualsObject() {
        impl.setId(7);

        assertFalse("'equals' should be correct", impl.equals(null));

        ResponsiblePerson obj = new ResponsiblePerson();
        obj.setId(7);
        assertFalse("'equals' should be correct", impl.equals(obj));

        MockIdentifiableEntity obj2 = new MockIdentifiableEntity();
        obj2.setId(6);
        assertFalse("'equals' should be correct", impl.equals(obj2));

        obj2.setId(7);
        assertTrue("'equals' should be correct", impl.equals(obj2));
    }

    /**
     * Accuracy test for the method <code>hashCode()</code>.<br>
     * Expects no error occurs.
     */
    @Test
    public void testHashCode() {
        impl.setId(5);

        assertEquals("The result should be same as", 5, impl.hashCode());
    }

    /**
     * <p>
     * Mock class for.IdentifiableEntity.
     * </p>
     *
     * @author TCSDEVELOPER
     * @version 1.0
     */
    class MockIdentifiableEntity extends IdentifiableEntity {
        // Empty
    }

}
