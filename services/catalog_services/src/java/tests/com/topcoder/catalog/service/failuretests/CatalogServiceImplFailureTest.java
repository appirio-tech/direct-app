/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.catalog.service.failuretests;

import java.util.Arrays;

import javax.ejb.EJBException;

import com.topcoder.catalog.entity.Category;
import com.topcoder.catalog.entity.Component;
import com.topcoder.catalog.entity.Technology;
import com.topcoder.catalog.service.AssetDTO;
import com.topcoder.catalog.service.CatalogService;
import com.topcoder.catalog.service.CatalogServiceImpl;
import com.topcoder.catalog.service.EntityNotFoundException;
import com.topcoder.catalog.service.PersistenceException;
import com.topcoder.catalog.service.SearchCriteria;

/**
 * <p>
 * Failure tests for CatalogServiceImpl.
 * </p>
 *
 * @author kaqi072821
 * @version 1.0
 */
public class CatalogServiceImplFailureTest extends CatalogServiceImplFailureTestBase {
    /**
     * <p>
     * CatalogService instance under test. It is initialized in the setUp method with the JPA entity manager throws
     * exceptions.
     * </p>
     */
    private CatalogService catalogService;

    /**
     * <p>
     * Sets up testing environment.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();
        catalogService = FailureTestHelper.getCatalogService(true);
        FailureTestEntityManager.setThrowIAE(false);
    }

    /**
     * <p>
     * Tears down the test environment.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    protected void tearDown() throws Exception {
        catalogService = null;
        super.tearDown();
    }

    /**
     * <p>
     * Failure test when the entityManager has not been injected, PersistenceException expected.
     * </p>
     *
     * @throws Exception to jUnit
     */
    public void test_GetActiveCategories_fail_EntityManagerNotInjected() throws Exception {
        try {
            // no entityManager injected
            CatalogServiceImpl service = new CatalogServiceImpl();
            service.getActiveCategories();
            fail("PersistenceException is expected no entity manager injected.");
        } catch (PersistenceException e) {
            // pass
        }
    }

    /**
     * <p>
     * Tests <tt>getActiveCategories</tt> method when JPA layer throws an PersistenceException.
     * </p>
     *
     * @throws Exception to jUnit
     */
    public void test_GetActiveCategories_fail_Cause_PE() throws Exception {
        try {
            catalogService.getActiveCategories();
            fail("PersistenceException is expected as an exception occurred on JPA layer.");
        } catch (PersistenceException e) {
            // pass
        }
    }

    /**
     * <p>
     * Tests <tt>getActiveCategories</tt> method when JPA layer throws an IllegalArgumentException.
     * </p>
     *
     * @throws Exception to jUnit
     */
    public void test_GetActiveCategories_fail_Cause_IAE() throws Exception {
        try {
            FailureTestEntityManager.setThrowIAE(true);
            catalogService.getActiveCategories();
            fail("IllegalArgumentException is expected as an exception occurred on JPA layer.");
        } catch (PersistenceException e) {
            // pass
        }
    }

    /**
     * <p>
     * Tests <tt>getActiveTechnologies</tt> method when JPA layer throws an PersistenceException.
     * </p>
     *
     * @throws Exception to jUnit
     */
    public void test_getActiveTechnologies_fail_Cause_PE() throws Exception {
        try {
            catalogService.getActiveTechnologies();
            fail("PersistenceException is expected as an exception occurred on JPA layer.");
        } catch (PersistenceException e) {
            // pass
        }
    }

    /**
     * <p>
     * Tests <tt>getActiveTechnologies</tt> method when JPA layer throws an IllegalArgumentException.
     * </p>
     *
     * @throws Exception to jUnit
     */
    public void test_getActiveTechnologies_fail_Cause_IAE() throws Exception {
        try {
            FailureTestEntityManager.setThrowIAE(true);
            catalogService.getActiveTechnologies();
            fail("IllegalArgumentException is expected as an exception occurred on JPA layer.");
        } catch (PersistenceException e) {
            // pass
        }
    }

    /**
     * <p>
     * Tests <tt>getPhases</tt> method when JPA layer throws an PersistenceException.
     * </p>
     *
     * @throws Exception to jUnit
     */
    public void test_getPhases_fail_Cause_PE() throws Exception {
        try {
            catalogService.getPhases();
            fail("PersistenceException is expected as an exception occurred on JPA layer.");
        } catch (PersistenceException e) {
            // pass
        }
    }

    /**
     * <p>
     * Tests <tt>getPhases</tt> method when JPA layer throws an IllegalArgumentException.
     * </p>
     *
     * @throws Exception to jUnit
     */
    public void test_getPhases_fail_Cause_IAE() throws Exception {
        try {
            FailureTestEntityManager.setThrowIAE(true);
            catalogService.getPhases();
            fail("IllegalArgumentException is expected as an exception occurred on JPA layer.");
        } catch (PersistenceException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test for <tt>getAssetById</tt> method when no such asset.
     * </p>
     *
     * @throws Exception to jUnit
     */
    public void test_getAssetById_fail_NonExistingId() throws Exception {
        try {
            getRemote().getAssetById(-1, true);
            fail("EntityNotFoundException is expected as there is no such an entity.");
        } catch (EntityNotFoundException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test for <tt>getAssetById</tt> method when JPA layer thrown a PersistenceException.
     * </p>
     *
     * @throws Exception to jUnit
     */
    public void test_getAssetById_fail_cause_PE() throws Exception {
        Component entity = createAsset(1);
        try {
            catalogService.getAssetById(entity.getId(), true);
            fail("PersistenceException is expected as an exception occurred on JPA layer.");
        } catch (PersistenceException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test for <tt>getAssetById</tt> method when JPA layer thrown an IllegalArgumentException.
     * </p>
     *
     * @throws Exception to jUnit
     */
    public void test_getAssetById_fail_cause_IAE() throws Exception {
        Component entity = createAsset(1);
        try {
            FailureTestEntityManager.setThrowIAE(true);
            catalogService.getAssetById(entity.getId(), true);
            fail("PersistenceException is expected as an exception occurred on JPA layer.");
        } catch (PersistenceException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure tests for <tt>getAssetByVersionId</tt> method when no such asset.
     * </p>
     *
     * @throws Exception to jUnit
     */
    public void test_getAssetByVersionId_fail_NonExistingVersionid() throws Exception {
        try {
            getRemote().getAssetByVersionId(-1);
            fail("EntityNotFoundException is expected as there is no such an entity.");
        } catch (EntityNotFoundException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure tests for <tt>getAssetByVersionId</tt> method when JPA layer thrown an PersistenceException.
     * </p>
     *
     * @throws Exception to jUnit
     */
    public void test_getAssetByVersionId_fail_cause_PE() throws Exception {
        // create asset first...
        Component entity = createAsset(1);
        try {
            catalogService.getAssetByVersionId(entity.getCurrentVersion().getId());
            fail("PersistenceException is expected as an exception occurred on JPA layer.");
        } catch (PersistenceException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure tests for <tt>getAssetByVersionId</tt> method when JPA layer thrown an IllegalArgumentException.
     * </p>
     *
     * @throws Exception to jUnit
     */
    public void test_getAssetByVersionId_fail_cause_IAE() throws Exception {
        // create asset first...
        Component entity = createAsset(1);
        try {
            FailureTestEntityManager.setThrowIAE(true);
            catalogService.getAssetByVersionId(entity.getCurrentVersion().getId());
            fail("PersistenceException is expected as an IllegalArgumentException occurred on JPA layer.");
        } catch (PersistenceException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure tests for <tt>createAsset</tt> method when JPA layer thrown an PersistenceException.
     * </p>
     *
     * @throws Exception to jUnit
     */
    public void test_createAsset_fail_cause_PE() throws Exception {
        AssetDTO newAsset = populateDTO(1);
        try {
            catalogService.createAsset(newAsset);
            fail("PersistenceException is expected as an exception occurred on JPA layer.");
        } catch (PersistenceException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure tests for <tt>createAsset</tt> method when JPA layer thrown an IllegalArgumentException.
     * </p>
     *
     * @throws Exception to jUnit
     */
    public void test_createAsset_fail_cause_IAE() throws Exception {
        AssetDTO newAsset = populateDTO(1);
        try {
            FailureTestEntityManager.setThrowIAE(true);
            catalogService.createAsset(newAsset);
            fail("PersistenceException is expected as an IllegalArgumentException occurred on JPA layer.");
        } catch (PersistenceException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure tests for <tt>createAsset</tt> method for non-existing technology.
     * </p>
     *
     * @throws Exception to jUnit
     */
    public void test_CreateAsset_NonExistingTechnology() throws Exception {
        AssetDTO newAsset = populateDTO(1);
        Technology invalidTechnology = new Technology();
        invalidTechnology.setId(-1L);
        newAsset.setTechnologies(Arrays.asList(invalidTechnology));
        try {
            getRemote().createAsset(newAsset);
            fail("PersistenceException is expected as there is no such a technology.");
        } catch (PersistenceException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure tests for <tt>createAsset</tt> method for non-existing category.
     * </p>
     *
     * @throws Exception to jUnit
     */
    public void test_CreateAsset_NonExistingCategory() throws Exception {
        AssetDTO newAsset = populateDTO(1);
        Category invalidCategory = new Category();
        invalidCategory.setId(-1L);
        newAsset.setCategories(Arrays.asList(invalidCategory));
        try {
            getRemote().createAsset(newAsset);
            fail("PersistenceException is expected as there is no such a category.");
        } catch (PersistenceException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure tests for <tt>createVersion</tt> method when JPA layer thrown an PersistenceException.
     * </p>
     *
     * @throws Exception to jUnit
     */
    public void test_CreateVersion_fail_cause_PE() throws Exception {
        AssetDTO newAsset = populateDTO(1);
        newAsset = getRemote().createAsset(newAsset);
        // create second version
        String newVersion = "1.1";
        newAsset.setVersionText(newVersion);
        newAsset.setVersionId(null);
        try {
            catalogService.createVersion(newAsset);
            fail("PersistenceException is expected as an PersistenceException occurred on JPA layer.");
        } catch (PersistenceException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure tests for <tt>createVersion</tt> method when JPA layer thrown an IllegalArgumentException.
     * </p>
     *
     * @throws Exception to jUnit
     */
    public void test_CreateVersion_fail_cause_IAE() throws Exception {
        AssetDTO newAsset = populateDTO(1);
        newAsset = getRemote().createAsset(newAsset);
        // create second version
        String newVersion = "1.1";
        newAsset.setVersionText(newVersion);
        newAsset.setVersionId(null);
        try {
            FailureTestEntityManager.setThrowIAE(true);
            catalogService.createVersion(newAsset);
            fail("PersistenceException is expected as an IllegalArgumentException occurred on JPA layer.");
        } catch (PersistenceException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure Tests for <tt>createVersion</tt> method for non-existing asset.
     * </p>
     *
     * @throws Exception to jUnit
     */
    public void test_CreateVersion_fail_NonExistingAsset() throws Exception {
        AssetDTO newAsset = populateDTO(1);
        newAsset.setId(-1L);
        // create second version
        String newVersion = "1.1";
        newAsset.setVersionText(newVersion);
        newAsset.setVersionId(null);
        try {
            getRemote().createVersion(newAsset);
            fail("EntityNotFoundException is expected as there is no such an entity.");
        } catch (EntityNotFoundException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure Tests for <tt>updateAsset</tt> method when JPA layer thrown a PersistenceException.
     * </p>
     *
     * @throws Exception to jUnit
     */
    public void test_UpdateAsset_fail_cause_PE() throws Exception {
        AssetDTO newAsset = createAssetDTO(1);
        try {
            catalogService.updateAsset(newAsset);
            fail("PersistenceException is expected as an PersistenceException occurred on JPA layer.");
        } catch (PersistenceException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure Tests for <tt>updateAsset</tt> method when JPA layer throws an IllegalArgumentException.
     * </p>
     *
     * @throws Exception to jUnit
     */
    public void test_UpdateAsset_fail_cause_IAE() throws Exception {
        AssetDTO newAsset = createAssetDTO(1);
        try {
            FailureTestEntityManager.setThrowIAE(true);
            catalogService.updateAsset(newAsset);
            fail("PersistenceException is expected as an IllegalArgumentException occurred on JPA layer.");
        } catch (PersistenceException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure tests for <tt>updateAsset</tt> method for non-existing asset.
     * </p>
     *
     * @throws Exception to jUnit
     */
    public void test_UpdateAsset_fail_NonExistingAsset() throws Exception {
        AssetDTO newAsset = createAssetDTO(1);
        newAsset.setId(-1L);
        try {
            getRemote().updateAsset(newAsset);
            fail("EntityNotFoundException is expected as there is no such an entity.");
        } catch (EntityNotFoundException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure tests for <tt>findAssets</tt> method when the criteria is null.
     * </p>
     *
     * @throws Exception to jUnit
     */
    public void test_FindAssets_fail_NullParameter() throws Exception {
        try {
            catalogService.findAssets(null, true);
            fail("IllegalArgumentException is expected as no criteria provided.");
        } catch (EJBException e) {
            assertTrue("Invalid cause, should be IllegalArgumentException",
                e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>
     * Failure tests for <tt>findAssets</tt> method when JPA layer thrown an PersistenceException.
     * </p>
     *
     * @throws Exception to jUnit
     */
    public void test_FindAssets_fail_cause_PE() throws Exception {
        try {
            catalogService.findAssets(new SearchCriteria(1L, null, null, null, null, null, null, null, null), false);
            fail("PersistenceException is expected as an PersistenceException occurred on JPA layer.");
        } catch (PersistenceException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure tests for <tt>findAssets</tt> method when JPA layer thrown an IllegalArgumentException.
     * </p>
     *
     * @throws Exception to jUnit
     */
    public void test_FindAssets_fail_cause_IAE() throws Exception {
        try {
            FailureTestEntityManager.setThrowIAE(true);
            catalogService.findAssets(new SearchCriteria(1L, null, null, null, null, null, null, null, null), false);
            fail("PersistenceException is expected as an IllegalArgumentException occurred on JPA layer.");
        } catch (PersistenceException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure tests for <tt>assignUserToAsset</tt> method for non-existing asset.
     * </p>
     *
     * @throws Exception to jUnit
     */
    public void test_assignUserToAsset_fail_NonExistingAsset() throws Exception {
        try {
            getRemote().assignUserToAsset(1L, -1);
            fail("EntityNotFoundException is expected as there is no such an entity.");
        } catch (EntityNotFoundException e) {
            // pass
        }
    }

    /**
     * <p>
     * Tests <tt>assignUserToAsset</tt> method when JPA layer thrown an PersistenceException.
     * </p>
     *
     * @throws Exception to jUnit
     */
    public void test_assignUserToAsset_fail_cause_PE() throws Exception {
        AssetDTO newAsset = createAssetDTO(1);
        try {
            catalogService.assignUserToAsset(1L, newAsset.getId());
            fail("PersistenceException is expected as an PersistenceException occurred on JPA layer.");
        } catch (PersistenceException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure tests for <tt>assignUserToAsset</tt> method when JPA layer thrown an IllegalArgumentException.
     * </p>
     *
     * @throws Exception to jUnit
     */
    public void test_assignUserToAsset_fail_cause_IAE() throws Exception {
        AssetDTO newAsset = createAssetDTO(1);
        try {
            FailureTestEntityManager.setThrowIAE(true);
            catalogService.assignUserToAsset(1L, newAsset.getId());
            fail("PersistenceException is expected as an IllegalArgumentException occurred on JPA layer.");
        } catch (PersistenceException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure tests for <tt>removeUserFromAsset</tt> method for non-existing asset.
     * </p>
     *
     * @throws Exception to jUnit
     */
    public void test_removeUserFromAsset_fail_NonExistingComponent() throws Exception {
        try {
            getRemote().removeUserFromAsset(1L, -1);
            fail("EntityNotFoundException is expected as there is no such an entity.");
        } catch (EntityNotFoundException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure tests for <tt>removeUserFromAsset</tt> method for non-existing user-component association.
     * </p>
     *
     * @throws Exception to jUnit
     */
    public void test_removeUserFromAsset_NonExistingAssociation() throws Exception {
        Component asset = createAsset(1);
        CatalogService remote = getRemote();
        // try to remove when there are no associations at all
        try {
            remote.removeUserFromAsset(1L, asset.getId());
            fail("EntityNotFoundException is expected as there is no such an entity.");
        } catch (EntityNotFoundException e) {
            // pass
        }
        // try to remove when there is an association, but another one
        remote.assignUserToAsset(2L, asset.getId());
        try {
            remote.removeUserFromAsset(1L, asset.getId());
            fail("EntityNotFoundException is expected as there is no such an entity.");
        } catch (EntityNotFoundException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure tests for <tt>removeUserFromAsset</tt> method when JPA layer thrown an PersistenceException.
     * </p>
     *
     * @throws Exception to jUnit
     */
    public void test_removeUserFromAsset_fail_cause_PE() throws Exception {
        AssetDTO newAsset = createAssetDTO(1);
        try {
            catalogService.removeUserFromAsset(1L, newAsset.getId());
            fail("PersistenceException is expected as a PersistenceException occurred on JPA layer.");
        } catch (PersistenceException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure tests for <tt>removeUserFromAsset</tt> method when JPA layer thrown an IllegalArgumentException.
     * </p>
     *
     * @throws Exception to jUnit
     */
    public void test_removeUserFromAsset_fail_cause_IAE() throws Exception {
        AssetDTO newAsset = createAssetDTO(1);
        try {
            FailureTestEntityManager.setThrowIAE(true);
            catalogService.removeUserFromAsset(1L, newAsset.getId());
            fail("PersistenceException is expected as a IllegalArgumentException occurred on JPA layer.");
        } catch (PersistenceException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure tests for <tt>getAllAssetVersions(long)</tt> method.
     * </p>
     * <p>
     * If the assetDTO is not found in persistence. 
     * </p>
     * <p>
     * EntityNotFoundException expected.
     * </p>
     *
     * @throws Exception to jUnit
     * @since 1.1
     */
    public void test_getAllAssetVersions_fail_cause_EntityNotFoundException() throws Exception {
        try {
            catalogService.getAllAssetVersions(38291L);
            fail("EntityNotFoundException should be thrown if assetDTO is not found in persistence.");
        } catch (EntityNotFoundException e) {
            // pass
        } catch (PersistenceException e) {
            // PersistenceException is also OK for different hibernate version
            // may thrown PersistenceException
        }
    }

    /**
     * <p>
     * Failure tests for <tt>getAllAssetVersions(long)</tt> method.
     * </p>
     * <p>
     * if an error occurs when interacting with the persistence store.
     * </p>
     * <p>
     * PersistenceException expected.
     * </p>
     *
     * @throws Exception to jUnit
     * @since 1.1
     */
    public void test_getAllAssetVersions_fail_cause_PersistenceException() throws Exception {
    	createAssetDTO(1);
        try {
            FailureTestEntityManager.setThrowIAE(true);
            catalogService.getAllAssetVersions(1L);
            fail("PersistenceException is expected as an IllegalArgumentException occurred on JPA layer.");
        } catch (PersistenceException e) {
            // pass
        }
    }
}