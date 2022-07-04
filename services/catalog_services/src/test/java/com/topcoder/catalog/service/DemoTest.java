/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.catalog.service;

import com.topcoder.catalog.entity.Category;
import com.topcoder.catalog.entity.CompDocumentation;
import com.topcoder.catalog.entity.Component;
import com.topcoder.catalog.entity.Technology;
import static com.topcoder.catalog.service.TestHelper.parseDate;
import junit.framework.TestCase;

import javax.persistence.EntityManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>Tests the Demo provided in the CS 4.3.</p>
 *
 * @author caru, Retunsky, TCSDEVELOPER
 * @version 1.1
 */
public class DemoTest extends TestCase {
    /**
     * <p>The <p>EntityManager</p> instance for testing.</p>
     */
    private EntityManager entityManager;

    /**
     * <p><code>CatalogService</code> instance for testing.</p>
     */
    private CatalogService remote;

    /**
     * <p>Sets up testing environment.</p>
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
     * <p>Tears down the test environment.</p>
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
     * <p>Tests "Create a new asset" paragraph.</p>
     *
     * @throws Exception to jUnit
     */
    public void testCreateNewComponent() throws Exception {
        AssetDTO newAsset = createAsset();
        // now verify that result is what was expected
        Component entity = (Component) entityManager.createQuery("from Component").getSingleResult();
        assertNotNull("Asset should be stored", entity);
        assertNotNull("Asset's version should be stored", entity.getCurrentVersion());
        assertEquals("Invalid name populated", newAsset.getName(), entity.getName());
        assertEquals("Invalid technologies count", 2, entity.getCurrentVersion().getTechnologies().size());
    }

    /**
     * <p>Tests "Create a new version" paragraph.</p>
     *
     * @throws Exception to jUnit
     */
    public void testCreateNewVersion() throws Exception {
        // create asset first...
        createAsset();
        // we need to query entity, as remote call doesn't return anything, and doesn't update parameters
        Component entity = (Component) entityManager.createQuery("from Component").getSingleResult();
        final Long assetId = entity.getId();
        // retrieve asset and its current version
        AssetDTO asset = remote.getAssetById(assetId, true);
        asset.setName("Catalog Service"); // update asset name
        asset.setVersionText("1.1"); // sent new text version
        asset.setVersionId(null); // reset version's ID
        asset.setProductionDate(parseDate("2008/01/10")); // set new production date
        remote.createVersion(asset);
        // verify result
        entityManager.clear(); // to reload from database, not from cache
        entity = (Component) entityManager.createQuery("from Component").getSingleResult();
        assertNotNull("Asset should be stored", entity);
        assertEquals("Invalid versions count", 2, entity.getVersions().size());
    }

    /**
     * <p>Tests "Find assets" paragraph.</p>
     *
     * @throws Exception to jUnit
     */
    public void testFindAssets() throws Exception {
        // create a component first
        createAsset();
        createAsset2();
        List<AssetDTO> assets = remote.findAssets(new SearchCriteria(null, null, null, "catalog",
                null, null, null, null, null), true);

        // verify result
        assertFalse("Assets should be retrieved", assets == null || assets.isEmpty());
        assertEquals("Invalid count of assets", 2, assets.size());
    }

    /**
     * <p>Creates a new asset. Corresponds to code in paragraph "Create a new asset" in the CS 4.3.</p>
     *
     * @return original AssetDTO
     * @throws Exception to jUnit
     */
    private AssetDTO createAsset() throws Exception {
        final Category javaCategory = entityManager.find(Category.class, 2L);
        final Category ejb3Category = entityManager.find(Category.class, 3L);
        final Technology java15Technology = entityManager.find(Technology.class, 1L);
        final Technology informixTechnology = entityManager.find(Technology.class, 2L);

        AssetDTO newAsset = new AssetDTO();
        newAsset.setName("Catalog Services");
        newAsset.setVersionText("1.0");
        newAsset.setShortDescription("short");
        newAsset.setDetailedDescription("detailed");
        newAsset.setFunctionalDescription("functional");
        // set the root category
        newAsset.setRootCategory(javaCategory);

        // assign categories which this asset belongs to
        newAsset.setCategories(Arrays.asList(ejb3Category));

        newAsset.setTechnologies(Arrays.asList(
            java15Technology,
            informixTechnology
        ));
        newAsset.setDocumentation(new ArrayList<CompDocumentation>());
        remote.createAsset(newAsset);
        return newAsset;
    }

    /**
     * <p>Creates second component for "Find assets" demo.</p>
     *
     * @return original AssetDTO
     * @throws Exception to jUnit
     */
    private AssetDTO createAsset2() throws Exception {
        AssetDTO newAsset = new AssetDTO();
        newAsset.setName("Catalog Entities");
        newAsset.setVersionText("1.0");
        newAsset.setShortDescription("short");
        newAsset.setDetailedDescription("detailed");
        newAsset.setFunctionalDescription("functional");
        // set the root category (categories are in the database
        newAsset.setRootCategory(entityManager.find(Category.class, 2L));

        // assign categories which this component belongs to
        newAsset.setCategories(Arrays.asList(
            entityManager.find(Category.class, 2L),
            entityManager.find(Category.class, 3L)));

        newAsset.setTechnologies(Arrays.asList(
            entityManager.find(Technology.class, 1L),
            entityManager.find(Technology.class, 2L)
        ));
        newAsset.setDocumentation(new ArrayList<CompDocumentation>());
        remote.createAsset(newAsset);
        return newAsset;
    }
}
