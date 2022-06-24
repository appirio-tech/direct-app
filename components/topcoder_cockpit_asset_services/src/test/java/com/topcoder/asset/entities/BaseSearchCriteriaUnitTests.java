/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.asset.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import junit.framework.JUnit4TestAdapter;

import org.junit.Before;
import org.junit.Test;

import com.topcoder.asset.BaseUnitTests;

/**
 * <p>
 * Unit tests for {@link BaseSearchCriteria} class.
 * </p>
 *
 * @author sparemax
 * @version 1.0
 */
public class BaseSearchCriteriaUnitTests {
    /**
     * <p>
     * Represents the <code>BaseSearchCriteria</code> instance used in tests.
     * </p>
     */
    private BaseSearchCriteria instance;

    /**
     * <p>
     * Adapter for earlier versions of JUnit.
     * </p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(BaseSearchCriteriaUnitTests.class);
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
        instance = new MockBaseSearchCriteria();
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>BaseSearchCriteria()</code>.<br>
     * Instance should be correctly created.
     * </p>
     */
    @Test
    public void testCtor() {
        instance = new MockBaseSearchCriteria();

        assertEquals("'page' should be correct.", 0, BaseUnitTests.getField(instance, "page"));
        assertEquals("'pageSize' should be correct.", 0, BaseUnitTests.getField(instance, "pageSize"));
        assertNull("'sortingColumn' should be correct.", BaseUnitTests.getField(instance, "sortingColumn"));
        assertFalse("'ascending' should be correct.", (Boolean) BaseUnitTests.getField(instance, "ascending"));
    }


    /**
     * <p>
     * Accuracy test for the method <code>getPage()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getPage() {
        int value = 1;
        instance.setPage(value);

        assertEquals("'getPage' should be correct.",
            value, instance.getPage());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setPage(int page)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setPage() {
        int value = 1;
        instance.setPage(value);

        assertEquals("'setPage' should be correct.",
            value, BaseUnitTests.getField(instance, "page"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getPageSize()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getPageSize() {
        int value = 1;
        instance.setPageSize(value);

        assertEquals("'getPageSize' should be correct.",
            value, instance.getPageSize());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setPageSize(int pageSize)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setPageSize() {
        int value = 1;
        instance.setPageSize(value);

        assertEquals("'setPageSize' should be correct.",
            value, BaseUnitTests.getField(instance, "pageSize"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getSortingColumn()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getSortingColumn() {
        String value = "new_value";
        instance.setSortingColumn(value);

        assertEquals("'getSortingColumn' should be correct.",
            value, instance.getSortingColumn());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setSortingColumn(String sortingColumn)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setSortingColumn() {
        String value = "new_value";
        instance.setSortingColumn(value);

        assertEquals("'setSortingColumn' should be correct.",
            value, BaseUnitTests.getField(instance, "sortingColumn"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getAscending()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getAscending() {
        boolean value = true;
        instance.setAscending(value);

        assertTrue("'getAscending' should be correct.", instance.getAscending());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setAscending(boolean ascending)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setAscending() {
        boolean value = true;
        instance.setAscending(value);

        assertTrue("'setAscending' should be correct.",
            (Boolean) BaseUnitTests.getField(instance, "ascending"));
    }
}