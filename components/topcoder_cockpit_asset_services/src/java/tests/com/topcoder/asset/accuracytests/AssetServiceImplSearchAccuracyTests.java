/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.asset.accuracytests;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import javax.persistence.EntityManager;

import junit.framework.JUnit4TestAdapter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.asset.entities.Asset;
import com.topcoder.asset.entities.AssetSearchCriteria;
import com.topcoder.asset.entities.PagedResult;
import com.topcoder.asset.services.impl.AssetServiceImpl;
import com.topcoder.asset.services.impl.AssetVersionServiceImpl;
import com.topcoder.util.log.log4j.Log4jLogFactory;

/**
 * <p>
 * Accuracy unit test for {@link AssetServiceImpl}.
 * </p>
 *
 * @author lqz
 * @version 1.0
 */
public class AssetServiceImplSearchAccuracyTests {
    /**
     * <p>
     * Represents the AssetServiceImpl instance to test.
     * </p>
     */
    private AssetServiceImpl impl;

    /**
     * <p>
     * Adapter for earlier versions of JUnit.
     * </p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(AssetServiceImplSearchAccuracyTests.class);
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
        impl = new AssetServiceImpl();
        impl.setLog(new Log4jLogFactory().createLog("test"));
        EntityManager entityManager = AccuracyHelper.getConnection();
		impl.setEntityManager(entityManager);
		
		AssetVersionServiceImpl assetVersionService = new AssetVersionServiceImpl();
		assetVersionService.setLog(new Log4jLogFactory().createLog("test"));
        assetVersionService.setEntityManager(entityManager);
        assetVersionService.setBasePath("test_files/acc/");
        assetVersionService.setImageTypes(Arrays.asList("jpg", "png", "gif"));
        assetVersionService.setPreviewImageHeight(800);
        assetVersionService.setPreviewImageWidth(600);
		impl.setAssetVersionService(assetVersionService);
        AccuracyHelper.clearDB();
        AccuracyHelper.prepareDB();
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
    }

    /**
     * <p>
     * Accuracy test for {@link AssetServiceImpl#searchAssets()}.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    @Test
    public void test_searchAssets() throws Exception {
		AssetSearchCriteria criteria = new AssetSearchCriteria();
		criteria.setAscending(true);
		criteria.setPageSize(2);
		criteria.setPage(1);
		PagedResult<Asset> got = impl.searchAssets(criteria );
		
		//only 3,4,6,7 are public
		assertEquals(got.getRecords().size(), 2);
		assertEquals(got.getTotalPages(), 2);
		assertEquals(got.getPage(), 1);
		assertEquals(got.getPageSize(), 2);
		assertEquals(got.getRecords().get(0).getName(), "assert3");
		assertEquals(got.getRecords().get(1).getName(), "assert4");
	
		criteria.setPage(2);
		got = impl.searchAssets(criteria );
		assertEquals(got.getRecords().size(), 2);
		assertEquals(got.getTotalPages(), 2);
		assertEquals(got.getPage(), 2);
		assertEquals(got.getPageSize(), 2);
		assertEquals(got.getRecords().get(0).getName(), "assert6");
		assertEquals(got.getRecords().get(1).getName(), "assert7");
    }
    
    /**
     * <p>
     * Accuracy test for {@link AssetServiceImpl#searchAssets()}.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    @Test
    public void test_searchAssets2() throws Exception {
		AssetSearchCriteria criteria = new AssetSearchCriteria();
		criteria.setAscending(false);
		criteria.setPageSize(10);
		criteria.setPage(1);
		PagedResult<Asset> got = impl.searchAssets(criteria );
		
		//only 3,4,6,7 are public
		assertEquals(got.getRecords().size(), 4);
		assertEquals(got.getTotalPages(), 1);
		assertEquals(got.getPage(), 1);
		assertEquals(got.getPageSize(), 10);
		assertEquals(got.getRecords().get(0).getName(), "assert7");
		assertEquals(got.getRecords().get(1).getName(), "assert6");
		assertEquals(got.getRecords().get(2).getName(), "assert4");
		assertEquals(got.getRecords().get(3).getName(), "assert3");
    }
    
    /**
     * <p>
     * Accuracy test for {@link AssetServiceImpl#searchAssets()}.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    @Test
    public void test_searchAssets3() throws Exception {
		AssetSearchCriteria criteria = new AssetSearchCriteria();
		criteria.setAscending(false);
		criteria.setPageSize(10);
		criteria.setPage(1);
		criteria.setUser("user1");
		PagedResult<Asset> got = impl.searchAssets(criteria );
		
		//7643 are public, 1 and 2's uploader is user1
		assertEquals(got.getRecords().size(), 7);
		assertEquals(got.getTotalPages(), 1);
		assertEquals(got.getPage(), 1);
		assertEquals(got.getPageSize(), 10);
		assertEquals(got.getRecords().get(0).getName(), "assert7");
		assertEquals(got.getRecords().get(1).getName(), "assert6");
		assertEquals(got.getRecords().get(2).getName(), "assert5");
		assertEquals(got.getRecords().get(3).getName(), "assert4");
		assertEquals(got.getRecords().get(4).getName(), "assert3");
		assertEquals(got.getRecords().get(5).getName(), "assert2");
		assertEquals(got.getRecords().get(6).getName(), "assert1");
    }
    
    /**
     * <p>
     * Accuracy test for {@link AssetServiceImpl#searchAssets()}.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    @Test
    public void test_searchAssets10() throws Exception {
		AssetSearchCriteria criteria = new AssetSearchCriteria();
		criteria.setAscending(false);
		criteria.setPageSize(10);
		criteria.setPage(1);
		criteria.setFileName("3.jpeg");
		PagedResult<Asset> got = impl.searchAssets(criteria );
		
		//assert3's filename matched
		assertEquals(got.getRecords().size(), 1);
		assertEquals(got.getTotalPages(), 1);
		assertEquals(got.getPage(), 1);
		assertEquals(got.getPageSize(), 10);
		assertEquals(got.getRecords().get(0).getName(), "assert3");
    }

    /**
     * <p>
     * Accuracy test for {@link AssetServiceImpl#searchAssets()}.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    @Test
    public void test_searchAssets11() throws Exception {
		AssetSearchCriteria criteria = new AssetSearchCriteria();
		criteria.setAscending(false);
		criteria.setPageSize(10);
		criteria.setPage(1);
		criteria.setName("assert7");
		PagedResult<Asset> got = impl.searchAssets(criteria );
		
		//assert7's name is matched and it is public
		assertEquals(got.getRecords().size(), 1);
		assertEquals(got.getTotalPages(), 1);
		assertEquals(got.getPage(), 1);
		assertEquals(got.getPageSize(), 10);
		assertEquals(got.getRecords().get(0).getName(), "assert7");
    }
    

    /**
     * <p>
     * Accuracy test for {@link AssetServiceImpl#searchAssets()}.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    @Test
    public void test_searchAssets12() throws Exception {
		AssetSearchCriteria criteria = new AssetSearchCriteria();
		criteria.setAscending(false);
		criteria.setPageSize(10);
		criteria.setPage(1);
		criteria.setVersion("assetversion3");
		PagedResult<Asset> got = impl.searchAssets(criteria );
		
		//assert3's version matched
		assertEquals(got.getRecords().size(), 1);
		assertEquals(got.getTotalPages(), 1);
		assertEquals(got.getPage(), 1);
		assertEquals(got.getPageSize(), 10);
		assertEquals(got.getRecords().get(0).getName(), "assert3");
    }


    /**
     * <p>
     * Accuracy test for {@link AssetServiceImpl#searchAssets()}.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    @Test
    public void test_searchAssets13() throws Exception {
		AssetSearchCriteria criteria = new AssetSearchCriteria();
		criteria.setAscending(false);
		criteria.setPageSize(10);
		criteria.setPage(1);
		criteria.setContainerType("container_type3");
		criteria.setContainerId(3L);
		PagedResult<Asset> got = impl.searchAssets(criteria );
		
		//3 and 4 matched
		assertEquals(got.getRecords().size(), 2);
		assertEquals(got.getTotalPages(), 1);
		assertEquals(got.getPage(), 1);
		assertEquals(got.getPageSize(), 10);
		assertEquals(got.getRecords().get(0).getName(), "assert4");
		assertEquals(got.getRecords().get(1).getName(), "assert3");
    }


}
