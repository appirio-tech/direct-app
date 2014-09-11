/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.catalog.service.stresstests;

import com.topcoder.catalog.entity.Category;
import com.topcoder.catalog.entity.CompClient;
import com.topcoder.catalog.entity.CompDocumentation;
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

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;


/**
 * Stress tests for the class CatalogServiceImpl.
 *
 * @author NeverSurrender
 * @version 1.1
 *
 */
public class CatalogServiceImplStressTest extends TestCase {
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
     * Sets up testing environment.
     * </p>
     *
     * @throws Exception
     *             to jUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();
        entityManager = TestHelper.getEntityManager();
        TestHelper.prepareTables();

        remote = TestHelper.getCatalogService(false);
    }

    /**
     * <p>
     * Tears down the test environment.
     * </p>
     *
     * @throws Exception
     *             to jUnit.
     */
    protected void tearDown() throws Exception {
        TestHelper.clearTables();
    }

    /**
     * Stress test for the method getAllAssetVersions(long).
     *
     * @throws Exception
     *             to JUnit.
     */
    @SuppressWarnings("unchecked")
    public void testGetAllAssetVersions() throws Exception {
        // version 1
        AssetDTO asset = populateDTO(1);

        // version2
        AssetDTO newAsset1 = remote.createAsset(asset);
        newAsset1.setVersionId(null);
        newAsset1.setVersionText("versionText 1.1");

        // version 3
        AssetDTO newAsset2 = remote.createVersion(newAsset1);
        newAsset2.setVersionId(null);
        newAsset2.setVersionText("versionText 1.2");
        remote.createVersion(newAsset2);

        long time = System.currentTimeMillis();

        for (int i = 0; i < 10; i++) {
            assertEquals("The value should be matched.", 3,
                remote.getAllAssetVersions(newAsset2.getId()).size());
        }

        time = System.currentTimeMillis() - time;
        System.out.println(
            "Stress test for the method getAllAssetVersions(long) took " +
            time + "ms");
    }

    /**
     * Stress test for the method findAssets(SearchCriteria, boolean).
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testFindAssets() throws Exception {
        // all these parameters points two the first asset
        Long userId = 1L;
        Long clientId = 2L;
        String name = "SErviCES1";
        String[] descriptions = { "CLuE1", "SHorT1", "FunCTIonaL1" };
        List<Category> categories = Arrays.asList(entityManager.find(
                    Category.class, 1L), entityManager.find(Category.class, 3L));

        Component first = createAsset(1);
        createAsset(2);

        CompClient compClient = new CompClient();
        compClient.setClientId(2L);
        compClient.setComponent(first);

        EntityTransaction tx = TestHelper.getEntityTransaction();
        tx.begin();
        entityManager.persist(compClient);
        tx.commit();

        long time = System.currentTimeMillis();
        Date creationStartDate = TestHelper.parseDate("2008/01/9");
        Date creationEndDate = new Date(time + 10000);
        Date versionCreationStartDate = TestHelper.parseDate("2008/01/9");
        Date versionCreationEndDate = new Date(time + 10000);

        for (int i = 1; i <= 0x1F; i++) {
            Long pUserId = ((i & 0x10) != 0) ? userId : null;
            Long pClientId = ((i & 0x08) != 0) ? clientId : null;
            List<Category> pCategories = ((i & 0x04) != 0) ? categories : null;
            String pName = ((i & 0x02) != 0) ? name
                                             : (((i & 0x01) == 0) ? " " : null);
            String pDescription = ((i & 0x01) != 0) ? descriptions[(i / 2) % 3]
                                                    : (((i & 0x02) == 0) ? " "
                                                                         : null);

            // v 1.1
            SearchCriteria criteria = new SearchCriteria(pUserId, pClientId,
                    pCategories, pName, pDescription, creationStartDate,
                    creationEndDate, versionCreationStartDate,
                    versionCreationEndDate);
            boolean current = (i % 2) == 1;
            List<AssetDTO> assets = remote.findAssets(criteria, current);
            assertEquals("Invalid number of found items", 1, assets.size());
        }

        time = System.currentTimeMillis() - time;
        System.out.println(
            "Stress test for the method findAssets(SearchCriteria, boolean) took " +
            time + "ms");
    }

    /**
     * <p>
     * Populates DTO for asset creation.
     * </p>
     *
     * @param index
     *            an index of the asset (to create different assets)
     *
     * @return created DTO asset
     */
    private AssetDTO populateDTO(int index) {
        AssetDTO newAsset = new AssetDTO();
        newAsset.setName("Catalog Services" + index);
        newAsset.setVersionText("versionText 1.0");
        newAsset.setShortDescription("short" + index);
        newAsset.setDetailedDescription("detailed description contains a clue" + index);
        newAsset.setFunctionalDescription("functional" + index);
        newAsset.setProductionDate(new Date(System.currentTimeMillis() + index * 10000));
        // set the root category (categories are in the database
        newAsset.setRootCategory(entityManager.find(Category.class, index % 2 == 1 ? 1L : 2L));

        // assign categories which this asset belongs to
        newAsset.setCategories(Arrays.asList(
            entityManager.find(Category.class, 2L),
            entityManager.find(Category.class, 3L)));

        newAsset.setTechnologies(Arrays.asList(
            entityManager.find(Technology.class, 1L),
            entityManager.find(Technology.class, 2L)
        ));
        newAsset.setDocumentation(new ArrayList<CompDocumentation>());
        return newAsset;
    }

    /**
     * <p>
     * Creates a new asset.
     * </p>
     *
     * @return created Asset
     * @param index
     *            an index of the asset (to create different assets)
     * @throws Exception
     *             to jUnit
     */
    @SuppressWarnings("unchecked")
    private Component createAsset(int index) throws Exception {
        AssetDTO newAsset = populateDTO(index);
        newAsset = remote.createAsset(newAsset);

        // create second version
        newAsset.setVersionText("1.1"); // sent new text version
        newAsset.setVersionId(null); // reset version's id
        newAsset.setProductionDate(TestHelper.parseDate("2008/01/10"));

        remote.createVersion(newAsset);

        return (Component) entityManager.createQuery(
            "from Component c where c.name=:name")
                                        .setParameter("name", newAsset.getName())
                                        .getSingleResult();
    }
}
