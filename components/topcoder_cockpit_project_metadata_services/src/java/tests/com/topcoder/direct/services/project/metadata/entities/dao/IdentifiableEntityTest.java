/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.metadata.entities.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * <p>
 * Unit tests for the {@link IdentifiableEntity}.
 * </p>
 *
 * @author  CaDenza
 * @version 1.0
 */
public class IdentifiableEntityTest {
    /** Represents the id used to test again. */
    private final long idValue = 1;

    /** Represents the instance used to test again. */
    private IdentifiableEntity testInstance;

    /**
     * Sets up test environment.
     *
     * @throws Exception to JUnit
     */
    @Before
    public void setUp() throws Exception {
        testInstance = new MockIdentifiableEntity();
    }

    /**
     * Tears down test environment.
     *
     * @throws Exception to JUnit
     */
    @After
    public void tearDown() throws Exception {
        testInstance = null;
    }

    /**
     * <p>
     * Accuracy test for {@link IdentifiableEntity#IdentifiableEntity()}.
     * </p>
     * <p>
     * Instance should be correctly created.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testIdentifiableEntity() throws Exception {
        new MockIdentifiableEntity();

        // Good
    }

    /**
     * <p>
     * Accuracy test for {@link IdentifiableEntity#getId()}
     * </p>
     * <p>
     * The value of <code>id</code> should be properly retrieved.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getId() throws Exception {
        assertEquals("Initial value of id must be 0.", 0, testInstance.getId());
    }

    /**
     * <p>
     * Accuracy test {@link IdentifiableEntity#setId(long)}.
     * </p>
     * <p>
     * The value of <code>id</code> should be properly set.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setId() throws Exception {
        testInstance.setId(idValue);
        assertEquals("Property id isn't set properly.", idValue, testInstance.getId());
    }

    /**
     * <p>
     * Accuracy test for {@link IdentifiableEntity#toJSONObject()}.
     * </p>
     * <p>
     *
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_toJSONObject() throws Exception {
        assertNotNull("Fail construct JSON", testInstance.toJSONObject(true));
    }

    /**
     * <p>
     * Accuracy test for {@link IdentifiableEntity#toJSONString()}.
     * </p>
     * <p>
     *
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_toJSONString() throws Exception {
        assertNotNull("Fail construct JSON", testInstance.toJSONString());
    }
}