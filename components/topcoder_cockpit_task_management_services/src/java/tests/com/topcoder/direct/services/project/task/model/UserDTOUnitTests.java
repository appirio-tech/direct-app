/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.task.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import junit.framework.JUnit4TestAdapter;

import org.junit.Before;
import org.junit.Test;

import com.topcoder.direct.services.project.task.TestsHelper;

/**
 * <p>
 * Unit tests for {@link UserDTO} class.
 * </p>
 *
 * @author sparemax
 * @version 1.0
 */
public class UserDTOUnitTests {
    /**
     * <p>
     * Represents the <code>UserDTO</code> instance used in tests.
     * </p>
     */
    private UserDTO instance;

    /**
     * <p>
     * Adapter for earlier versions of JUnit.
     * </p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(UserDTOUnitTests.class);
    }

    /**
     * <p>
     * Sets up the unit tests.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Before
    public void setUp() throws Exception {
        instance = new UserDTO();
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>UserDTO()</code>.<br>
     * Instance should be correctly created.
     * </p>
     */
    @Test
    public void testCtor() {
        instance = new UserDTO();

        assertEquals("'userId' should be correct.", 0L, TestsHelper.getField(instance, "userId"));
        assertNull("'handle' should be correct.", TestsHelper.getField(instance, "handle"));
    }


    /**
     * <p>
     * Accuracy test for the method <code>getUserId()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getUserId() {
        long value = 1L;
        instance.setUserId(value);

        assertEquals("'getUserId' should be correct.",
            value, instance.getUserId());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setUserId(long userId)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setUserId() {
        long value = 1L;
        instance.setUserId(value);

        assertEquals("'setUserId' should be correct.",
            value, TestsHelper.getField(instance, "userId"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getHandle()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getHandle() {
        String value = "new_value";
        instance.setHandle(value);

        assertEquals("'getHandle' should be correct.",
            value, instance.getHandle());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setHandle(String handle)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setHandle() {
        String value = "new_value";
        instance.setHandle(value);

        assertEquals("'setHandle' should be correct.",
            value, TestsHelper.getField(instance, "handle"));
    }
}