/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.asset.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import junit.framework.JUnit4TestAdapter;

import org.junit.Before;
import org.junit.Test;

import com.topcoder.asset.BaseUnitTests;

/**
 * <p>
 * Unit tests for {@link FileTypeIcon} class.
 * </p>
 *
 * @author sparemax
 * @version 1.0
 */
public class FileTypeIconUnitTests {
    /**
     * <p>
     * Represents the <code>FileTypeIcon</code> instance used in tests.
     * </p>
     */
    private FileTypeIcon instance;

    /**
     * <p>
     * Adapter for earlier versions of JUnit.
     * </p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(FileTypeIconUnitTests.class);
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
        instance = new FileTypeIcon();
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>FileTypeIcon()</code>.<br>
     * Instance should be correctly created.
     * </p>
     */
    @Test
    public void testCtor() {
        instance = new FileTypeIcon();

        assertEquals("'id' should be correct.", 0L, BaseUnitTests.getField(instance, "id"));
        assertNull("'fileType' should be correct.", BaseUnitTests.getField(instance, "fileType"));
        assertNull("'fileTypeCategory' should be correct.", BaseUnitTests.getField(instance, "fileTypeCategory"));
        assertNull("'iconPath' should be correct.", BaseUnitTests.getField(instance, "iconPath"));
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
     * Accuracy test for the method <code>getFileType()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getFileType() {
        String value = "new_value";
        instance.setFileType(value);

        assertEquals("'getFileType' should be correct.",
            value, instance.getFileType());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setFileType(String fileType)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setFileType() {
        String value = "new_value";
        instance.setFileType(value);

        assertEquals("'setFileType' should be correct.",
            value, BaseUnitTests.getField(instance, "fileType"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getFileTypeCategory()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getFileTypeCategory() {
        String value = "new_value";
        instance.setFileTypeCategory(value);

        assertEquals("'getFileTypeCategory' should be correct.",
            value, instance.getFileTypeCategory());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setFileTypeCategory(String fileTypeCategory)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setFileTypeCategory() {
        String value = "new_value";
        instance.setFileTypeCategory(value);

        assertEquals("'setFileTypeCategory' should be correct.",
            value, BaseUnitTests.getField(instance, "fileTypeCategory"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getIconPath()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getIconPath() {
        String value = "new_value";
        instance.setIconPath(value);

        assertEquals("'getIconPath' should be correct.",
            value, instance.getIconPath());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setIconPath(String iconPath)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setIconPath() {
        String value = "new_value";
        instance.setIconPath(value);

        assertEquals("'setIconPath' should be correct.",
            value, BaseUnitTests.getField(instance, "iconPath"));
    }
}