/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.asset.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import junit.framework.JUnit4TestAdapter;

import org.junit.Before;
import org.junit.Test;

import com.topcoder.asset.BaseUnitTests;

/**
 * <p>
 * Unit tests for {@link AssetPermission} class.
 * </p>
 *
 * @author sparemax
 * @version 1.0
 */
public class AssetPermissionUnitTests {
    /**
     * <p>
     * Represents the <code>AssetPermission</code> instance used in tests.
     * </p>
     */
    private AssetPermission instance;

    /**
     * <p>
     * Adapter for earlier versions of JUnit.
     * </p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(AssetPermissionUnitTests.class);
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
        instance = new AssetPermission();
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>AssetPermission()</code>.<br>
     * Instance should be correctly created.
     * </p>
     */
    @Test
    public void testCtor() {
        instance = new AssetPermission();

        assertEquals("'id' should be correct.", 0L, BaseUnitTests.getField(instance, "id"));
        assertEquals("'assetId' should be correct.", 0L, BaseUnitTests.getField(instance, "assetId"));
        assertNull("'user' should be correct.", BaseUnitTests.getField(instance, "user"));
    }


    /**
     * <p>
     * Accuracy test for the method <code>getId()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getId() {
        long value = 1L;
        instance.setId(value);

        assertEquals("'getId' should be correct.",
            value, instance.getId());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setId(long id)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setId() {
        long value = 1L;
        instance.setId(value);

        assertEquals("'setId' should be correct.",
            value, BaseUnitTests.getField(instance, "id"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getAssetId()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getAssetId() {
        long value = 1L;
        instance.setAssetId(value);

        assertEquals("'getAssetId' should be correct.",
            value, instance.getAssetId());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setAssetId(long assetId)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setAssetId() {
        long value = 1L;
        instance.setAssetId(value);

        assertEquals("'setAssetId' should be correct.",
            value, BaseUnitTests.getField(instance, "assetId"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getUser()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getUser() {
        User value = new User();
        instance.setUser(value);

        assertSame("'getUser' should be correct.",
            value, instance.getUser());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setUser(User user)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setUser() {
        User value = new User();
        instance.setUser(value);

        assertSame("'setUser' should be correct.",
            value, BaseUnitTests.getField(instance, "user"));
    }
}