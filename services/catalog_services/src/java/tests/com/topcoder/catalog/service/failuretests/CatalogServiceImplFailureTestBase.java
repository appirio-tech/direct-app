/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.catalog.service.failuretests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;

import junit.framework.TestCase;

import com.topcoder.catalog.entity.Category;
import com.topcoder.catalog.entity.CompDocumentation;
import com.topcoder.catalog.entity.CompVersion;
import com.topcoder.catalog.entity.Component;
import com.topcoder.catalog.entity.Technology;
import com.topcoder.catalog.service.AssetDTO;
import com.topcoder.catalog.service.CatalogService;

/**
 * <p>
 * The base test case for the failure tests. It provides the shared methods for the inheritances.
 * </p>
 *
 * @author kaqi072821, hfx
 * @version 1.1
 */
public class CatalogServiceImplFailureTestBase extends TestCase {
    /**
     * <p>
     * The
     * <p>
     * EntityManager
     * </p>
     * instance for testing.
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
     * Sets up testing environment.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();
        FailureTestHelper.prepareTables();
        entityManager = FailureTestHelper.getEntityManager();
        remote = FailureTestHelper.getCatalogService(false);
    }

    /**
     * <p>
     * Tears down the test environment.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    protected void tearDown() throws Exception {
        FailureTestHelper.close();
        FailureTestHelper.clearTables();
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
        // create second version
        newAsset.setVersionText("1.1"); // sent new text version
        newAsset.setVersionId(null); // reset version ID
        newAsset.setProductionDate(FailureTestHelper.parseDate("2008/01/10")); // set new
        // production
        // date
        remote.createVersion(newAsset);

        return (Component) entityManager.createQuery("from Component c where c.name=:name").setParameter("name",
            newAsset.getName()).getSingleResult();
    }
    
    /**
     * <p>
     * Creates a new asset.
     * </p>
     *
     * @return created Asset
     * @throws Exception to jUnit
     * @param index an index of the asset (to create different assets)
     * @since 1.1
     */
    protected AssetDTO createAssetWithDoc(int index) throws Exception {
		AssetDTO asset = populateDTO(1);
        // create the first version
        asset = remote.createAsset(asset);
        // create document
        CompDocumentation doc = new CompDocumentation();
        doc.setDocumentName("Requirement doc");
        doc.setDocumentTypeId(1L);
        doc.setUrl("http://tc.com/doc/111");
        // set document
		asset.setDocumentation(Arrays.asList(doc));
		// create asset with documentation
		asset = remote.createAsset(asset);
		return asset;
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
        // set the root category (categories are in the database
        newAsset.setRootCategory(entityManager.find(Category.class, index % 2 == 1 ? 1L : 2L));
        newAsset.setDocumentation(new ArrayList<CompDocumentation>());

        // assign categories which this asset belongs to
        newAsset.setCategories(Arrays.asList(entityManager.find(Category.class, 2L), entityManager.find(Category.class,
            3L)));

        newAsset.setTechnologies(Arrays.asList(entityManager.find(Technology.class, 1L), entityManager.find(
            Technology.class, 2L)));
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