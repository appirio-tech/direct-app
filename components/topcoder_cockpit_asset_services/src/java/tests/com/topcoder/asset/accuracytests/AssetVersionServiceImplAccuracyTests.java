/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.asset.accuracytests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.persistence.EntityManager;

import junit.framework.JUnit4TestAdapter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.asset.entities.Asset;
import com.topcoder.asset.entities.AssetVersion;
import com.topcoder.asset.entities.User;
import com.topcoder.asset.services.impl.AssetServiceImpl;
import com.topcoder.asset.services.impl.AssetVersionServiceImpl;
import com.topcoder.util.log.log4j.Log4jLogFactory;

/**
 * <p>
 * Accuracy unit test for {@link AssetVersionServiceImpl}.
 * </p>
 *
 * @author lqz
 * @version 1.0
 */
public class AssetVersionServiceImplAccuracyTests {
    /**
     * <p>
     * Represents the AssetVersionServiceImpl instance to test.
     * </p>
     */
    private AssetVersionServiceImpl impl;
	private AssetServiceImpl assetService;

    /**
     * <p>
     * Adapter for earlier versions of JUnit.
     * </p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(AssetVersionServiceImplAccuracyTests.class);
    }

    /**
     * <p>
     * Sets up the unit tests.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    @Before
    public void setUp() throws Exception {
        impl = new AssetVersionServiceImpl();
        impl.setLog(new Log4jLogFactory().createLog("test"));
        EntityManager entityManager = AccuracyHelper.getConnection();
		impl.setEntityManager(entityManager);
        impl.setBasePath("test_files/acc");
        impl.setImageTypes(Arrays.asList("jpeg", "jpg", "png", "gif"));
        impl.setPreviewImageHeight(30);
        impl.setPreviewImageWidth(20);
        AccuracyHelper.clearDB();
        AccuracyHelper.prepareDB();
        
        assetService = new AssetServiceImpl();
        assetService.setLog(new Log4jLogFactory().createLog("test"));
        assetService.setEntityManager(entityManager);
        assetService.setAssetVersionService(impl);
    }

    /**
     * <p>
     * Cleans up the unit tests.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    @After
    public void tearDown() throws Exception {
        impl = null;
        AccuracyHelper.clearDB();

		
        File[] files = new File("test_files/acc/container_type1/1/1/1.0/").listFiles();
        if (files != null) {
        	for (File f : files) {
        		f.delete();
        	}
        }
    }

    /**
     * <p>
     * Accuracy test for {@link AssetVersionServiceImpl#AssetVersionServiceImpl()}.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    @Test
    public void test_AssetVersionServiceImpl() throws Exception {
        impl = new AssetVersionServiceImpl();
    }
    

    /**
     * <p>
     * Accuracy test for {@link AssetVersionServiceImpl#createAssetVersion()}.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    @Test
    public void test_createAssetVersion_not_currentversion() throws Exception {
		AssetVersion assetVersion = new AssetVersion();
		assetVersion.setAssetId(1);
		assetVersion.setDescription("desc");
		assetVersion.setFileName("1.jpeg");
		assetVersion.setFileSizeBytes(20201);
		assetVersion.setFileType("jpeg");
		User uploader = new User();
		uploader.setId(1);
		assetVersion.setUploader(uploader);
		assetVersion.setUploadTime(new Date());
		assetVersion.setVersion("1.0");
		
		AccuracyHelper.getConnection().getTransaction().begin();
		impl.createAssetVersion(1, assetVersion, new File("test_files/acc/images/1.jpeg"), false);
		AccuracyHelper.getConnection().getTransaction().commit();
		
		assertEquals(20201, new File("test_files/acc/container_type1/1/1/1.0/1.jpeg").length());
		//the preview image should be compressed to 20*30
		long previewLen = new File("test_files/acc/container_type1/1/1/1.0/preview_1.jpeg").length();
		assertTrue("the length is "+previewLen,10000 > previewLen);
		new File("test_files/acc/container_type1/1/1/1.0/1.jpeg").delete();
		new File("test_files/acc/container_type1/1/1/1.0/preview_1.jpeg").delete();
		
		AssetVersion got = impl.getAssetVersion(assetVersion.getId());
		assertEquals("desc", got.getDescription());
		assertEquals("1.jpeg", got.getFileName());
		assertEquals(20201, got.getFileSizeBytes());
		assertEquals("jpeg", got.getFileType());
		assertEquals(1, got.getAssetId());
		assertTrue(new File("test_files/acc/container_type1/1/1/1.0/1.jpeg").equals(new File(got.getFilePath())));
		assertTrue(new File("test_files/acc/container_type1/1/1/1.0/preview_1.jpeg").equals(new File(got.getPreviewImagePath())));
		assertEquals(1, got.getUploader().getId());
		assertEquals("1.0", got.getVersion());
		
		Asset asset = assetService.getAsset(1);
		//the current version is unchanged
		assertEquals(1, asset.getCurrentVersion().getId());
    }
    

    /**
     * <p>
     * Accuracy test for {@link AssetVersionServiceImpl#createAssetVersion()}.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    @Test
    public void test_createAssetVersion_currentversion() throws Exception {
		AssetVersion assetVersion = new AssetVersion();
		assetVersion.setAssetId(1);
		assetVersion.setDescription("desc");
		assetVersion.setFileName("1.jpeg");
		assetVersion.setFileSizeBytes(20201);
		assetVersion.setFileType("jpeg");
		User uploader = new User();
		uploader.setId(1);
		assetVersion.setUploader(uploader);
		assetVersion.setUploadTime(new Date());
		assetVersion.setVersion("1.0");
		
		AccuracyHelper.getConnection().getTransaction().begin();
		impl.createAssetVersion(1, assetVersion, new File("test_files/acc/images/1.jpeg"), true);
		AccuracyHelper.getConnection().getTransaction().commit();
		
		assertEquals(20201, new File("test_files/acc/container_type1/1/1/1.0/1.jpeg").length());
		//the preview image should be compressed to 20*30
		long previewLen = new File("test_files/acc/container_type1/1/1/1.0/preview_1.jpeg").length();
		assertTrue("the length is "+previewLen,10000 > previewLen);
		new File("test_files/acc/container_type1/1/1/1.0/1.jpeg").delete();
		new File("test_files/acc/container_type1/1/1/1.0/preview_1.jpeg").delete();
		
		AssetVersion got = impl.getAssetVersion(assetVersion.getId());
		assertEquals("desc", got.getDescription());
		assertEquals("1.jpeg", got.getFileName());
		assertEquals(20201, got.getFileSizeBytes());
		assertEquals("jpeg", got.getFileType());
		assertEquals(1, got.getAssetId());

		assertTrue(new File("test_files/acc/container_type1/1/1/1.0/1.jpeg").equals(new File(got.getFilePath())));
		assertTrue(new File("test_files/acc/container_type1/1/1/1.0/preview_1.jpeg").equals(new File(got.getPreviewImagePath())));
		assertEquals(1, got.getUploader().getId());
		assertEquals("1.0", got.getVersion());
		

		Asset asset = assetService.getAsset(1);
		//the current version is changed
		assertEquals(assetVersion.getId(), asset.getCurrentVersion().getId());
    }

    
    
    

    /**
     * <p>
     * Accuracy test for {@link AssetVersionServiceImpl#deleteAssetVersion()}.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    @Test
    public void test_deleteAssetVersion() throws Exception {
		AssetVersion assetVersion = new AssetVersion();
		assetVersion.setAssetId(1);
		assetVersion.setDescription("desc");
		assetVersion.setFileName("1.jpeg");
		assetVersion.setFileSizeBytes(20201);
		assetVersion.setFileType("jpeg");
		User uploader = new User();
		uploader.setId(1);
		assetVersion.setUploader(uploader);
		assetVersion.setUploadTime(new Date());
		assetVersion.setVersion("1.0");
		
		AccuracyHelper.getConnection().getTransaction().begin();
		impl.createAssetVersion(1, assetVersion, new File("test_files/acc/images/1.jpeg"), true);
		AccuracyHelper.getConnection().getTransaction().commit();
		
		AccuracyHelper.getConnection().getTransaction().begin();
		impl.deleteAssetVersion(1, assetVersion.getId());
		AccuracyHelper.getConnection().getTransaction().commit();
		
		AssetVersion ret = impl.getAssetVersion(assetVersion.getId());
		assertNull("ret should be null", ret);
		
		assertFalse("should be deleted", new File("test_files/acc/container_type1/1/1/1.0/1.jpeg").exists());
		assertFalse("should be deleted", new File("test_files/acc/container_type1/1/1/1.0/preview_1.jpeg").exists());
		
		Asset asset = assetService.getAsset(1);
		//the current version should be removed
		assertNull("the current version should be removed", asset.getCurrentVersion());
    }
    /**
     * <p>
     * Accuracy test for {@link AssetVersionServiceImpl#getAssetVersion()}.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    @Test
    public void test_getAssetVersion() throws Exception {
		AssetVersion assetVersion = new AssetVersion();
		assetVersion.setAssetId(1);
		assetVersion.setDescription("desc");
		assetVersion.setFileName("1.jpeg");
		assetVersion.setFileSizeBytes(20201);
		assetVersion.setFileType("jpeg");
		User uploader = new User();
		uploader.setId(1);
		assetVersion.setUploader(uploader);
		assetVersion.setUploadTime(new Date());
		assetVersion.setVersion("1.0");
		
		AccuracyHelper.getConnection().getTransaction().begin();
		impl.createAssetVersion(1, assetVersion, new File("test_files/acc/images/1.jpeg"), false);
		AccuracyHelper.getConnection().getTransaction().commit();
		
		AssetVersion got = impl.getAssetVersion(assetVersion.getId());
		assertEquals("desc", got.getDescription());
		assertEquals("1.jpeg", got.getFileName());
		assertEquals(20201, got.getFileSizeBytes());
		assertEquals("jpeg", got.getFileType());
		assertEquals(1, got.getAssetId());

		assertTrue(new File("test_files/acc/container_type1/1/1/1.0/1.jpeg").equals(new File(got.getFilePath())));
		assertTrue(new File("test_files/acc/container_type1/1/1/1.0/preview_1.jpeg").equals(new File(got.getPreviewImagePath())));
		assertEquals(1, got.getUploader().getId());
		assertEquals("1.0", got.getVersion());
    }
    

    /**
     * <p>
     * Accuracy test for {@link AssetVersionServiceImpl#getAssetVersionsOfAsset()}.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    @Test
    public void test_getAssetVersionsOfAsset() throws Exception {
		List<AssetVersion> got = impl.getAssetVersionsOfAsset(1);
		assertEquals(1, got.size());
		assertEquals(1, got.get(0).getId());

		assertEquals("1.jpeg", got.get(0).getFileName());
		assertEquals("assetversion1", got.get(0).getVersion());
		assertEquals(1, got.get(0).getAssetId());
    }
    
    /**
     * <p>
     * Accuracy test for {@link AssetVersionServiceImpl#batchGetAssetVersionContents()}.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    @Test
    public void test_batchGetAssetVersionContents() throws Exception {
		AssetVersion assetVersion1 = new AssetVersion();
		assetVersion1.setAssetId(1);
		assetVersion1.setDescription("desc");
		assetVersion1.setFileName("1.jpeg");
		assetVersion1.setFileSizeBytes(20201);
		assetVersion1.setFileType("jpeg");
		User uploader = new User();
		uploader.setId(1);
		assetVersion1.setUploader(uploader);
		assetVersion1.setUploadTime(new Date());
		assetVersion1.setVersion("1.0");
		
		AccuracyHelper.getConnection().getTransaction().begin();
		impl.createAssetVersion(1, assetVersion1, new File("test_files/acc/images/1.jpeg"), false);
		AccuracyHelper.getConnection().getTransaction().commit();
		
		AssetVersion assetVersion2 = new AssetVersion();
		assetVersion2.setAssetId(1);
		assetVersion2.setDescription("desc");
		assetVersion2.setFileName("2.gif");
		assetVersion2.setFileSizeBytes(1575);
		assetVersion2.setFileType("gif");
		assetVersion2.setUploader(uploader);
		assetVersion2.setUploadTime(new Date());
		assetVersion2.setVersion("1.0");
		AccuracyHelper.getConnection().getTransaction().begin();
		impl.createAssetVersion(1, assetVersion2, new File("test_files/acc/images/2.gif"), false);
		AccuracyHelper.getConnection().getTransaction().commit();
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		impl.batchGetAssetVersionContents(Arrays.asList(assetVersion1.getId(), assetVersion2.getId()), out );
		assertEquals(22011, out.size());
		ZipInputStream in = new ZipInputStream(new ByteArrayInputStream(out.toByteArray()));
		ZipEntry e1 = in.getNextEntry();
		
		ZipEntry e2 = in.getNextEntry();
		assertNull("only have 2 entries", in.getNextEntry());
		System.out.println(e2.getSize());
		assertEquals(1575, e2.getSize());
		assertEquals(20201, e1.getSize());
    }
    

    /**
     * <p>
     * Accuracy test for {@link AssetVersionServiceImpl#updateAssetVersion()}.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    @Test
    public void test_updateAssetVersion() throws Exception {
		AssetVersion assetVersion = new AssetVersion();
		assetVersion.setAssetId(1);
		assetVersion.setDescription("desc");
		assetVersion.setFileName("1.jpeg");
		assetVersion.setFileSizeBytes(20201);
		assetVersion.setFileType("jpeg");
		User uploader = new User();
		uploader.setId(1);
		assetVersion.setUploader(uploader);
		assetVersion.setUploadTime(new Date());
		assetVersion.setVersion("1.0");
		
		
		AccuracyHelper.getConnection().getTransaction().begin();
		impl.createAssetVersion(1, assetVersion, new File("test_files/acc/images/1.jpeg"), true);
		AccuracyHelper.getConnection().getTransaction().commit();
		
		long id = assetVersion.getId();
		AssetVersion newassetVersion = new AssetVersion();
		newassetVersion.setAssetId(1);
		newassetVersion.setId(id);
		newassetVersion.setDescription("desc 2");
		newassetVersion.setFileName("100.jpeg");
		newassetVersion.setFileType("jpeg");
		newassetVersion.setFileSizeBytes(assetVersion.getFileSizeBytes());
		newassetVersion.setFilePath("test_files/acc/container_type1/1/1/1.0/100.jpeg");
		newassetVersion.setPreviewImagePath("test_files/acc/container_type1/1/1/1.0/preview_100.jpeg");
		newassetVersion.setVersion("2.0");
		newassetVersion.setUploader(assetVersion.getUploader());
		newassetVersion.setUploadTime(assetVersion.getUploadTime());
		AccuracyHelper.getConnection().getTransaction().begin();
		impl.updateAssetVersion(1, newassetVersion);
		AccuracyHelper.getConnection().getTransaction().commit();
		
		assertEquals(20201, new File("test_files/acc/container_type1/1/1/1.0/100.jpeg").length());
		//the preview image should be compressed to 20*30
		long previewLen = new File("test_files/acc/container_type1/1/1/1.0/preview_100.jpeg").length();
		assertTrue("the length is "+previewLen,10000 > previewLen);
		//old files should be deleted
		assertFalse("should be deleted", new File("test_files/acc/container_type1/1/1/1.0/1.jpeg").exists());
		assertFalse("should be deleted", new File("test_files/acc/container_type1/1/1/1.0/preview_1.jpeg").exists());
		
		AssetVersion got = impl.getAssetVersion(newassetVersion.getId());
		assertEquals("desc 2", got.getDescription());
		assertEquals("100.jpeg", got.getFileName());
		assertEquals(20201, got.getFileSizeBytes());
		assertEquals("jpeg", got.getFileType());
		assertEquals(1, got.getAssetId());

		assertTrue(new File("test_files/acc/container_type1/1/1/1.0/100.jpeg").equals(new File(got.getFilePath())));
		assertTrue(new File("test_files/acc/container_type1/1/1/1.0/preview_100.jpeg").equals(new File(got.getPreviewImagePath())));
		assertEquals(1, got.getUploader().getId());
		assertEquals("2.0", got.getVersion());
    }



    /**
     * <p>
     * Accuracy test for {@link AssetVersionServiceImpl#updateAssetVersion()}.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    @Test
    public void test_updateAssetVersions() throws Exception {
		AssetVersion assetVersion = new AssetVersion();
		assetVersion.setAssetId(1);
		assetVersion.setDescription("desc");
		assetVersion.setFileName("1.jpeg");
		assetVersion.setFileSizeBytes(20201);
		assetVersion.setFileType("jpeg");
		User uploader = new User();
		uploader.setId(1);
		assetVersion.setUploader(uploader);
		assetVersion.setUploadTime(new Date());
		assetVersion.setVersion("1.0");
		
		
		AccuracyHelper.getConnection().getTransaction().begin();
		impl.createAssetVersion(1, assetVersion, new File("test_files/acc/images/1.jpeg"), true);
		AccuracyHelper.getConnection().getTransaction().commit();
		
		long id = assetVersion.getId();
		AssetVersion newassetVersion = new AssetVersion();
		newassetVersion.setAssetId(1);
		newassetVersion.setId(id);
		newassetVersion.setDescription("desc 2");
		newassetVersion.setFileName("100.jpeg");
		newassetVersion.setFileType("jpeg");
		newassetVersion.setFileSizeBytes(assetVersion.getFileSizeBytes());
		newassetVersion.setFilePath("test_files/acc/container_type1/1/1/1.0/100.jpeg");
		newassetVersion.setPreviewImagePath("test_files/acc/container_type1/1/1/1.0/preview_100.jpeg");
		newassetVersion.setVersion("2.0");
		newassetVersion.setUploader(assetVersion.getUploader());
		newassetVersion.setUploadTime(assetVersion.getUploadTime());
		AccuracyHelper.getConnection().getTransaction().begin();
		impl.updateAssetVersions(1, Arrays.asList(newassetVersion));
		AccuracyHelper.getConnection().getTransaction().commit();
		
		assertEquals(20201, new File("test_files/acc/container_type1/1/1/1.0/100.jpeg").length());
		//the preview image should be compressed to 20*30
		long previewLen = new File("test_files/acc/container_type1/1/1/1.0/preview_100.jpeg").length();
		assertTrue("the length is "+previewLen,10000 > previewLen);
		
		//old files should be deleted
		assertFalse("should be deleted", new File("test_files/acc/container_type1/1/1/1.0/1.jpeg").exists());
		assertFalse("should be deleted", new File("test_files/acc/container_type1/1/1/1.0/preview_1.jpeg").exists());
		
		AssetVersion got = impl.getAssetVersion(newassetVersion.getId());
		assertEquals("desc 2", got.getDescription());
		assertEquals("100.jpeg", got.getFileName());
		assertEquals(20201, got.getFileSizeBytes());
		assertEquals("jpeg", got.getFileType());
		assertEquals(1, got.getAssetId());

		assertTrue(new File("test_files/acc/container_type1/1/1/1.0/100.jpeg").equals(new File(got.getFilePath())));
		assertTrue(new File("test_files/acc/container_type1/1/1/1.0/preview_100.jpeg").equals(new File(got.getPreviewImagePath())));
		assertEquals(1, got.getUploader().getId());
		assertEquals("2.0", got.getVersion());
    }

    
    
    
    
    
    
    
}
