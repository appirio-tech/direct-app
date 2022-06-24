/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.asset.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import java.util.Date;

import junit.framework.JUnit4TestAdapter;

import org.junit.Before;
import org.junit.Test;

import com.topcoder.asset.BaseUnitTests;

/**
 * <p>
 * Unit tests for {@link AssetVersion} class.
 * </p>
 *
 * @author sparemax
 * @version 1.0
 */
public class AssetVersionUnitTests {
    /**
     * <p>
     * Represents the <code>AssetVersion</code> instance used in tests.
     * </p>
     */
    private AssetVersion instance;

    /**
     * <p>
     * Adapter for earlier versions of JUnit.
     * </p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(AssetVersionUnitTests.class);
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
        instance = new AssetVersion();
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>AssetVersion()</code>.<br>
     * Instance should be correctly created.
     * </p>
     */
    @Test
    public void testCtor() {
        instance = new AssetVersion();

        assertEquals("'id' should be correct.", 0L, BaseUnitTests.getField(instance, "id"));
        assertNull("'version' should be correct.", BaseUnitTests.getField(instance, "version"));
        assertNull("'fileName' should be correct.", BaseUnitTests.getField(instance, "fileName"));
        assertNull("'fileType' should be correct.", BaseUnitTests.getField(instance, "fileType"));
        assertEquals("'fileSizeBytes' should be correct.", 0L, BaseUnitTests.getField(instance, "fileSizeBytes"));
        assertNull("'uploader' should be correct.", BaseUnitTests.getField(instance, "uploader"));
        assertNull("'uploadTime' should be correct.", BaseUnitTests.getField(instance, "uploadTime"));
        assertNull("'description' should be correct.", BaseUnitTests.getField(instance, "description"));
        assertEquals("'assetId' should be correct.", 0L, BaseUnitTests.getField(instance, "assetId"));
        assertNull("'filePath' should be correct.", BaseUnitTests.getField(instance, "filePath"));
        assertNull("'previewImagePath' should be correct.", BaseUnitTests.getField(instance, "previewImagePath"));
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
     * Accuracy test for the method <code>getFileSizeBytes()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getFileSizeBytes() {
        long value = 1L;
        instance.setFileSizeBytes(value);

        assertEquals("'getFileSizeBytes' should be correct.",
            value, instance.getFileSizeBytes());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setFileSizeBytes(long fileSizeBytes)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setFileSizeBytes() {
        long value = 1L;
        instance.setFileSizeBytes(value);

        assertEquals("'setFileSizeBytes' should be correct.",
            value, BaseUnitTests.getField(instance, "fileSizeBytes"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getUploader()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getUploader() {
        User value = new User();
        instance.setUploader(value);

        assertSame("'getUploader' should be correct.",
            value, instance.getUploader());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setUploader(User uploader)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setUploader() {
        User value = new User();
        instance.setUploader(value);

        assertSame("'setUploader' should be correct.",
            value, BaseUnitTests.getField(instance, "uploader"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getUploadTime()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getUploadTime() {
        Date value = new Date();
        instance.setUploadTime(value);

        assertSame("'getUploadTime' should be correct.",
            value, instance.getUploadTime());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setUploadTime(Date uploadTime)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setUploadTime() {
        Date value = new Date();
        instance.setUploadTime(value);

        assertSame("'setUploadTime' should be correct.",
            value, BaseUnitTests.getField(instance, "uploadTime"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getDescription()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getDescription() {
        String value = "new_value";
        instance.setDescription(value);

        assertEquals("'getDescription' should be correct.",
            value, instance.getDescription());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setDescription(String description)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setDescription() {
        String value = "new_value";
        instance.setDescription(value);

        assertEquals("'setDescription' should be correct.",
            value, BaseUnitTests.getField(instance, "description"));
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
     * Accuracy test for the method <code>getFilePath()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getFilePath() {
        String value = "new_value";
        instance.setFilePath(value);

        assertEquals("'getFilePath' should be correct.",
            value, instance.getFilePath());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setFilePath(String filePath)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setFilePath() {
        String value = "new_value";
        instance.setFilePath(value);

        assertEquals("'setFilePath' should be correct.",
            value, BaseUnitTests.getField(instance, "filePath"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getPreviewImagePath()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getPreviewImagePath() {
        String value = "new_value";
        instance.setPreviewImagePath(value);

        assertEquals("'getPreviewImagePath' should be correct.",
            value, instance.getPreviewImagePath());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setPreviewImagePath(String previewImagePath)</code>.<br>
     * The value should be properly set.
     * </p>
     */
    @Test
    public void test_setPreviewImagePath() {
        String value = "new_value";
        instance.setPreviewImagePath(value);

        assertEquals("'setPreviewImagePath' should be correct.",
            value, BaseUnitTests.getField(instance, "previewImagePath"));
    }
}