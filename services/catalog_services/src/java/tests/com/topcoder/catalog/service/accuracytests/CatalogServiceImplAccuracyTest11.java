/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.catalog.service.accuracytests;

import com.topcoder.catalog.entity.Category;
import com.topcoder.catalog.entity.CompDocumentation;
import com.topcoder.catalog.entity.CompVersion;
import com.topcoder.catalog.entity.Component;
import com.topcoder.catalog.entity.Technology;
import com.topcoder.catalog.service.AssetDTO;
import com.topcoder.catalog.service.CatalogService;
import com.topcoder.catalog.service.SearchCriteria;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.ejb.EJBException;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 * Test cases for the CatalogServiceImpl version 1.1.
 * 
 * @author iRabbit
 * @version 1.1
 * @since 1.1
 */
public class CatalogServiceImplAccuracyTest11 extends TestCase {

    /**
     * <p>
     * The <code>EntityManager</code> instance for testing.
     * </p>
     */
    private EntityManager entityManager;
    /**
     * <p>
     * <code>CatalogService</code> instance for testing.
     * </p>
     */
    private CatalogService remote;

    /**
     * <p>
     * Tests <tt>findAsset</tt> method for component create date criteria.
     * </p>
     * 
     * @throws Exception to jUnit
     * @since 1.1
     */
    public void testFindAsset_createDate_accuracy1() throws Exception {
        final AssetDTO[] assetDTOs = new AssetDTO[10];
        for (int i = 0; i < assetDTOs.length; i++) {
            assetDTOs[i] = createAssetDTO(i);
        }

        final SearchCriteria criteria1 = new SearchCriteria(null, null, null, null, null, null, null, new Date(),
                assetDTOs[7].getProductionDate());
        List<AssetDTO> assets = getRemote().findAssets(criteria1, true);
        assertEquals("Wrong asset number returned.", 0, assets.size());
    }

    /**
     * <p>
     * Tests <tt>findAsset</tt> method for component version create date criteria.
     * </p>
     * 
     * @throws Exception to jUnit
     * @since 1.1
     */
    public void testFindAsset_createDate_accuracy2() throws Exception {
        final AssetDTO[] assetDTOs = new AssetDTO[10];
        for (int i = 0; i < assetDTOs.length; i++) {
            assetDTOs[i] = createAssetDTO(i);
        }

        final SearchCriteria criteria1 = new SearchCriteria(null, null, null, null, null, assetDTOs[2]
                .getProductionDate(), assetDTOs[7].getProductionDate(), null, null);
        List<AssetDTO> assets = getRemote().findAssets(criteria1, true);
        // verify result
        assertEquals("Wrong asset number returned.", 0, assets.size());
    }

    /**
     * <p>
     * Tests <tt>getAllAssetVersions</tt> method for versions are get correctly.
     * </p>
     * 
     * @throws Exception to jUnit
     * @since 1.1
     */
    public void testGetAllAssetVersions() throws Exception {
        // prepare data
        final AssetDTO[] assetDTOs = new AssetDTO[2];
        for (int i = 0; i < assetDTOs.length; i++) {
            assetDTOs[i] = populateDTO(i);
            getRemote().createAsset(assetDTOs[i]);
        }
        Long id = assetDTOs[1].getId();
        AssetDTO assetDTO = populateDTO((int) id.longValue());
        assetDTO.setId(id);
        assetDTOs[1].setVersionId(null);
        getRemote().createVersion(assetDTOs[1]);

        List<AssetDTO> res = getRemote().getAllAssetVersions(assetDTOs[0].getId());
        assertEquals("Wrong asset number returned.", 1, res.size());
        res = getRemote().getAllAssetVersions(id);
        assertEquals("Wrong asset number returned.", 2, res.size());
        assertTrue("Wrong order", res.get(0).getVersionId() < res.get(1).getVersionId());
    }

    /**
     * <p>
     * Sets up testing environment.
     * </p>
     * 
     * @throws Exception to jUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();
        TestHelper.prepareTables(); // clear data before a test
        entityManager = TestHelper.getEntityManager();
        remote = TestHelper.getCatalogService(false);
    }

    /**
     * <p>
     * Tears down the test environment.
     * </p>
     * 
     * @throws Exception to jUnit.
     */
    protected void tearDown() throws Exception {
        TestHelper.close(); // close entity manager and transaction
        TestHelper.clearTables(); // clear data
        entityManager = null;
        remote = null;
        super.tearDown();
    }

    /**
     * <p>
     * Creates a new asset.
     * </p>
     * 
     * @return created Asset
     * @throws Exception to jUnit
     * @param index an index of the asset (to create different assets)
     */
    protected Component createAsset(int index) throws Exception {
        AssetDTO newAsset = populateDTO(index);
        newAsset = remote.createAsset(newAsset);
        CompDocumentation[] docs = new CompDocumentation[2];
        final EntityTransaction tx = com.topcoder.catalog.service.accuracytests.TestHelper.getEntityTransaction();
        tx.begin();
        try {
            for (int i = 0; i < docs.length; i++) {
                docs[i] = new CompDocumentation();
                docs[i].setCompVersion(entityManager.find(CompVersion.class, newAsset.getId()));
                docs[i].setDocumentName("xxx");
                docs[i].setDocumentType("xxxx");
                docs[i].setDocumentTypeId(300L);
                docs[i].setUrl("xxxx");
                entityManager.persist(docs[i]);
            }
            tx.commit();
        } catch (RuntimeException e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw new EJBException(e);
        }
        entityManager.clear();
        newAsset = remote.getAssetById(newAsset.getId(), true);

        // create second version
        newAsset.setVersionText("1.1"); // sent new text version
        newAsset.setVersionId(null); // reset version’s ID
        newAsset.setProductionDate(com.topcoder.catalog.service.TestHelper.parseDate("2008/01/10")); // set new
                                                                                                        // production
                                                                                                        // date
        remote.createVersion(newAsset);

        return (Component) entityManager.createQuery("from Component c where c.name=:name").setParameter("name",
                newAsset.getName()).getSingleResult();
    }

    /**
     * <p>
     * Populates DTO for asset creation.
     * </p>
     * 
     * @return created DTO asset
     * @param index an index of the asset (to create different assets)
     */
    protected AssetDTO populateDTO(int index) {
        AssetDTO newAsset = new AssetDTO();
        newAsset.setName("Catalog Services" + index);
        newAsset.setVersionText("1.0");
        newAsset.setShortDescription("short" + index);
        newAsset.setDetailedDescription("detailed description contains a clue" + index);
        newAsset.setFunctionalDescription("functional" + index);
        newAsset.setProductionDate(new Date(System.currentTimeMillis() + index * 10000));
        // set the root category (categories are in the database
        newAsset.setRootCategory(entityManager.find(Category.class, index % 2 == 1 ? 1L : 2L));

        // assign categories which this asset belongs to
        newAsset.setCategories(Arrays.asList(entityManager.find(Category.class, 2L), entityManager.find(
                Category.class, 3L)));

        newAsset.setTechnologies(Arrays.asList(entityManager.find(Technology.class, 1L), entityManager.find(
                Technology.class, 2L)));
        newAsset.setDocumentation(new ArrayList<CompDocumentation>());
        return newAsset;
    }

    /**
     * <p>
     * Retrieves the latest version of a given asset.
     * </p>
     * 
     * @param asset asset whose latest version is being obtained
     * @return the latest version of the asset
     */
    protected CompVersion getLatestVersion(Component asset) {
        final List<CompVersion> versions = asset.getVersions();
        assertFalse("Asset doesn't have any versions", versions.isEmpty());
        // there is always at least one version
        CompVersion latestVersion = versions.get(0);
        for (CompVersion version : versions) {
            if (version.getVersion() > latestVersion.getVersion()) {
                latestVersion = version;
            }
        }
        return latestVersion;
    }

    /**
     * <p>
     * Creates assetDTO and returns copy obtained from the persistence (for updating).
     * </p>
     * 
     * @param index an index of the asset (to create different assets)
     * @return just created and persisted assetDTO
     * @throws Exception to jUnit
     */
    protected AssetDTO createAssetDTO(int index) throws Exception {
        final Component asset = createAsset(index);
        return getRemote().getAssetById(asset.getId(), true);
    }

    /**
     * <p>
     * Retrieves <code>entityManager</code>.
     * </p>
     * 
     * @return the entity manager
     */
    protected EntityManager getEntityManager() {
        return entityManager;
    }

    /**
     * <p>
     * Returns an instance of <code>CatalogService</code>.
     * </p>
     * 
     * @return CatalogService instance
     */
    protected CatalogService getRemote() {
        return remote;
    }
}
