/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project;

import java.io.Serializable;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * <p>
 * Unit tests for {@link FileType}.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.2
 */
public class FileTypeUnitTests extends TestCase {
    /**
     * Represents {@link FileType} instance for tests.
     */
    private FileType instance;

    /**
     * Sets up the test environment.
     *
     * @throws Exception
     *             to JUnit
     */
    @Before
    public void setUp() throws Exception {
        instance = new FileType();
    }

    /**
     * Tears down the test environment.
     *
     * @throws Exception
     *             to JUnit
     */
    @After
    public void tearDown() throws Exception {
        instance = null;
    }

    /**
     * Accuracy test for {@link FileType#FileType()}.
     */
    @Test
    public void test_ctor() {
        assertNotNull("Unable to instantiate this object", instance);
        assertTrue("Should be true", instance instanceof Serializable);
    }

    /**
     * Accuracy test for {@link FileType#getId()} method. The method should get the field value correctly.
     */
    @Test
    public void test_getId() {
        assertEquals("Should be 0", 0, instance.getId());
    }

    /**
     * Accuracy test for {@link FileType#setId(String)} method. The method should set the field value correctly.
     */
    @Test
    public void test_setId() {
        int value = 2;
        instance.setId(value);
        assertEquals("Should be the same", value, instance.getId());
    }

    /**
     * Failure test for {@link FileType#setId(String)} method. If the argument is non positive, IllegalArgumentException
     * is expected.
     */
    @Test
    public void test_setId_failure1() {
        try {
            instance.setId(-1);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // good
        }
        try {
            instance.setId(0);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Accuracy test for {@link FileType#getDescription()} method. The method should get the field value correctly.
     */
    @Test
    public void test_getDescription() {
        assertEquals("Should be null", null, instance.getDescription());
    }

    /**
     * Accuracy test for {@link FileType#setDescription(String)} method. The method should set the field value
     * correctly.
     */
    @Test
    public void test_setDescription() {
        String value = " newValue";
        instance.setDescription(value);
        assertEquals("Should be the same", value, instance.getDescription());
    }

    /**
     * Failure test for {@link FileType#setDescription(String)} method. If the argument is null,
     * IllegalArgumentException is expected.
     */
    @Test
    public void test_setDescription_failure1() {
        try {
            instance.setDescription(null);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Failure test for {@link FileType#setDescription(String)} method. If the argument is empty,
     * IllegalArgumentException is expected.
     */
    @Test
    public void test_setDescription_failure2() {
        try {
            instance.setDescription(" \t\n  ");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Accuracy test for {@link FileType#getExtension()} method. The method should get the field value correctly.
     */
    @Test
    public void test_getExtension() {
        assertEquals("Should be null", null, instance.getExtension());
    }

    /**
     * Accuracy test for {@link FileType#setExtension(String)} method. The method should set the field value correctly.
     */
    @Test
    public void test_setExtension() {
        String value = " newValue";
        instance.setExtension(value);
        assertEquals("Should be the same", value, instance.getExtension());
    }

    /**
     * Failure test for {@link FileType#setExtension(String)} method. If the argument is null, IllegalArgumentException
     * is expected.
     */
    @Test
    public void test_setExtension_failure1() {
        try {
            instance.setExtension(null);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Failure test for {@link FileType#setExtension(String)} method. If the argument is empty, IllegalArgumentException
     * is expected.
     */
    @Test
    public void test_setExtension_failure2() {
        try {
            instance.setExtension(" \t\n  ");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Accuracy test for {@link FileType#getSort()} method. The method should get the field value correctly.
     */
    @Test
    public void test_getSort() {
        assertEquals("Should be 0", 0, instance.getSort());
    }

    /**
     * Accuracy test for {@link FileType#setSort(String)} method. The method should set the field value correctly.
     */
    @Test
    public void test_setSort() {
        int value = 2;
        instance.setSort(value);
        assertEquals("Should be the same", value, instance.getSort());
    }

    /**
     * Accuracy test for {@link FileType#getImageFile()} method. The method should get the field value correctly.
     */
    @Test
    public void test_getImageFile() {
        assertEquals("Should be false", false, instance.isImageFile());
    }

    /**
     * Accuracy test for {@link FileType#setImageFile(boolean)} method. The method should set the field value correctly.
     */
    @Test
    public void test_setImageFile() {
        boolean value = true;
        instance.setImageFile(value);
        assertEquals("Should be the same", value, instance.isImageFile());
    }

    /**
     * Accuracy test for {@link FileType#getBundledFile()} method. The method should get the field value correctly.
     */
    @Test
    public void test_getBundledFile() {
        assertEquals("Should be false", false, instance.isBundledFile());
    }

    /**
     * Accuracy test for {@link FileType#setBundledFile(boolean)} method. The method should set the field value
     * correctly.
     */
    @Test
    public void test_setBundledFile() {
        boolean value = true;
        instance.setBundledFile(value);
        assertEquals("Should be the same", value, instance.isBundledFile());
    }

}
