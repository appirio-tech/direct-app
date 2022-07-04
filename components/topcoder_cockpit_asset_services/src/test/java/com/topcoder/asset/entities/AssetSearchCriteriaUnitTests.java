/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.asset.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.JUnit4TestAdapter;

import org.junit.Before;
import org.junit.Test;

import com.topcoder.asset.BaseUnitTests;

/**
 * <p>
 * Unit tests for {@link AssetSearchCriteria} class.
 * </p>
 *
 * @author sparemax
 * @version 1.0
 */
public class AssetSearchCriteriaUnitTests {
    /**
     * <p>
     * Represents the <code>AssetSearchCriteria</code> instance used in tests.
     * </p>
     */
    private AssetSearchCriteria instance;

    /**
     * <p>
     * Adapter for earlier versions of JUnit.
     * </p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(AssetSearchCriteriaUnitTests.class);
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
        instance = new AssetSearchCriteria();
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>AssetSearchCriteria()</code>.<br>
     * Instance should be correctly created.
     * </p>
     */
    @Test
    public void testCtor() {
        instance = new AssetSearchCriteria();

        assertNull("'name' should be correct.", BaseUnitTests.getField(instance, "name"));
        assertNull("'version' should be correct.", BaseUnitTests.getField(instance, "version"));
        assertNull("'fileName' should be correct.", BaseUnitTests.getField(instance, "fileName"));
        assertNull("'categories' should be correct.", BaseUnitTests.getField(instance, "categories"));
        assertNull("'uploaders' should be correct.", BaseUnitTests.getField(instance, "uploaders"));
        assertNull("'user' should be correct.", BaseUnitTests.getField(instance, "user"));
        assertNull("'containerType' should be correct.", BaseUnitTests.getField(instance, "containerType"));
        assertNull("'containerId' should be correct.", BaseUnitTests.getField(instance, "containerId"));
        assertNull("'startTime' should be correct.", BaseUnitTests.getField(instance, "startTime"));
        assertNull("'endTime' should be correct.", BaseUnitTests.getField(instance, "endTime"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getName()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getName() {
        String value = "new_value";
        instance.setName(value);

        assertEquals("'getName' should be correct.",
            value, instance.getName());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setName(String name)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setName() {
        String value = "new_value";
        instance.setName(value);

        assertEquals("'setName' should be correct.",
            value, BaseUnitTests.getField(instance, "name"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getVersion()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getVersion() {
        String value = "new_value";
        instance.setVersion(value);

        assertEquals("'getVersion' should be correct.",
            value, instance.getVersion());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setVersion(String version)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setVersion() {
        String value = "new_value";
        instance.setVersion(value);

        assertEquals("'setVersion' should be correct.",
            value, BaseUnitTests.getField(instance, "version"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getFileName()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getFileName() {
        String value = "new_value";
        instance.setFileName(value);

        assertEquals("'getFileName' should be correct.",
            value, instance.getFileName());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setFileName(String fileName)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setFileName() {
        String value = "new_value";
        instance.setFileName(value);

        assertEquals("'setFileName' should be correct.",
            value, BaseUnitTests.getField(instance, "fileName"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getCategories()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getCategories() {
        List<String> value = new ArrayList<String>();
        instance.setCategories(value);

        assertSame("'getCategories' should be correct.",
            value, instance.getCategories());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setCategories(List&lt;String&gt; categories)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setCategories() {
        List<String> value = new ArrayList<String>();
        instance.setCategories(value);

        assertSame("'setCategories' should be correct.",
            value, BaseUnitTests.getField(instance, "categories"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getUploaders()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getUploaders() {
        List<String> value = new ArrayList<String>();
        instance.setUploaders(value);

        assertSame("'getUploaders' should be correct.",
            value, instance.getUploaders());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setUploaders(List&lt;String&gt; uploaders)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setUploaders() {
        List<String> value = new ArrayList<String>();
        instance.setUploaders(value);

        assertSame("'setUploaders' should be correct.",
            value, BaseUnitTests.getField(instance, "uploaders"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getUser()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getUser() {
        String value = "new_value";
        instance.setUser(value);

        assertEquals("'getUser' should be correct.",
            value, instance.getUser());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setUser(String user)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setUser() {
        String value = "new_value";
        instance.setUser(value);

        assertEquals("'setUser' should be correct.",
            value, BaseUnitTests.getField(instance, "user"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getContainerType()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getContainerType() {
        String value = "new_value";
        instance.setContainerType(value);

        assertEquals("'getContainerType' should be correct.",
            value, instance.getContainerType());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setContainerType(String containerType)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setContainerType() {
        String value = "new_value";
        instance.setContainerType(value);

        assertEquals("'setContainerType' should be correct.",
            value, BaseUnitTests.getField(instance, "containerType"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getContainerId()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getContainerId() {
        Long value = 1L;
        instance.setContainerId(value);

        assertEquals("'getContainerId' should be correct.",
            value, instance.getContainerId());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setContainerId(Long containerId)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setContainerId() {
        Long value = 1L;
        instance.setContainerId(value);

        assertEquals("'setContainerId' should be correct.",
            value, BaseUnitTests.getField(instance, "containerId"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getStartTime()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getStartTime() {
        Date value = new Date();
        instance.setStartTime(value);

        assertSame("'getStartTime' should be correct.",
            value, instance.getStartTime());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setStartTime(Date startTime)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setStartTime() {
        Date value = new Date();
        instance.setStartTime(value);

        assertSame("'setStartTime' should be correct.",
            value, BaseUnitTests.getField(instance, "startTime"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getEndTime()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getEndTime() {
        Date value = new Date();
        instance.setEndTime(value);

        assertSame("'getEndTime' should be correct.",
            value, instance.getEndTime());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setEndTime(Date endTime)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setEndTime() {
        Date value = new Date();
        instance.setEndTime(value);

        assertSame("'setEndTime' should be correct.",
            value, BaseUnitTests.getField(instance, "endTime"));
    }
}