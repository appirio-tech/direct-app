/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.asset.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import java.util.ArrayList;
import java.util.List;

import junit.framework.JUnit4TestAdapter;

import org.junit.Before;
import org.junit.Test;

import com.topcoder.asset.BaseUnitTests;

/**
 * <p>
 * Unit tests for <code>PagedResult&lt;T&gt;</code> class.
 * </p>
 *
 * @author sparemax
 * @version 1.0
 */
public class PagedResultUnitTests {
    /**
     * <p>
     * Represents the <code>PagedResult&lt;T&gt;</code> instance used in tests.
     * </p>
     */
    private PagedResult<Object> instance;

    /**
     * <p>
     * Adapter for earlier versions of JUnit.
     * </p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(PagedResultUnitTests.class);
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
        instance = new PagedResult<Object>();
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>PagedResult()</code>.<br>
     * Instance should be correctly created.
     * </p>
     */
    @Test
    public void testCtor() {
        instance = new PagedResult<Object>();

        assertEquals("'page' should be correct.", 0, BaseUnitTests.getField(instance, "page"));
        assertEquals("'pageSize' should be correct.", 0, BaseUnitTests.getField(instance, "pageSize"));
        assertEquals("'totalPages' should be correct.", 0, BaseUnitTests.getField(instance, "totalPages"));
        assertNull("'records' should be correct.", BaseUnitTests.getField(instance, "records"));
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
     * Accuracy test for the method <code>getTotalPages()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getTotalPages() {
        int value = 1;
        instance.setTotalPages(value);

        assertEquals("'getTotalPages' should be correct.",
            value, instance.getTotalPages());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setTotalPages(int totalPages)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setTotalPages() {
        int value = 1;
        instance.setTotalPages(value);

        assertEquals("'setTotalPages' should be correct.",
            value, BaseUnitTests.getField(instance, "totalPages"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getRecords()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getRecords() {
        List<Object> value = new ArrayList<Object>();
        instance.setRecords(value);

        assertSame("'getRecords' should be correct.",
            value, instance.getRecords());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setRecords(List&lt;T&gt; records)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setRecords() {
        List<Object> value = new ArrayList<Object>();
        instance.setRecords(value);

        assertSame("'setRecords' should be correct.",
            value, BaseUnitTests.getField(instance, "records"));
    }
}