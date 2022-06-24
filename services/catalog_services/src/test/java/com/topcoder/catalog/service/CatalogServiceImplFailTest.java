/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.catalog.service;

import com.topcoder.catalog.entity.Component;
import com.topcoder.catalog.entity.Technology;
import com.topcoder.catalog.entity.Category;
import static com.topcoder.catalog.service.TestHelper.getCatalogService;

import javax.ejb.EJBException;
import java.util.Arrays;

/**
 * <p>This class tests <code>CatalogServiceImpl</code> class for failure testcase.</p>
 *
 * @author Retunsky
 * @version 1.0
 */
public class CatalogServiceImplFailTest extends CatalogServiceImplBaseTst {
    /**
     * <p><code>CatalogService</code> instance which JPA entity manager throws exceptions.</p>
     */
    private CatalogService failingRemote;

    /**
     * <p>Sets up testing environment.</p>
     *
     * @throws Exception to jUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();
        failingRemote = getCatalogService(true);
    }

    /**
     * <p>Tears down the test environment.</p>
     *
     * @throws Exception to jUnit.
     */
    protected void tearDown() throws Exception {
        failingRemote = null;
        super.tearDown();
    }

    /**
     * <p>Tests handling of a situation when the <code>entityManager</code> has not been injected.</p>
     *
     * @throws Exception to jUnit
     */
    public void testEntityManagerNotInjected() throws Exception {
        try {
            final CatalogServiceImpl service = new CatalogServiceImpl(); // no entityManager injected here
            service.getPhases();
            fail("PersistenceException is expected no entity manager injected.");
        } catch (PersistenceException e) {
            // OK
        }
    }

    /**
     * <p>Tests <tt>getPhases</tt> method when JPA layer thrown an exception.</p>
     *
     * @throws Exception to jUnit
     */
    public void testGetPhasesJPAFailure() throws Exception {
        try {
            failingRemote.getPhases();
            fail("PersistenceException is expected as an exception occurred on JPA layer.");
        } catch (PersistenceException e) {
            // OK
        }
    }

    /**
     * <p>Tests <tt>getActiveTechnologies</tt> method when JPA layer thrown an exception.</p>
     *
     * @throws Exception to jUnit
     */
    public void testGetActiveTechnologiesJPAFailure() throws Exception {
        try {
            failingRemote.getActiveTechnologies();
            fail("PersistenceException is expected as an exception occurred on JPA layer.");
        } catch (PersistenceException e) {
            // OK
        }
    }

    /**
     * <p>Tests <tt>getActiveCategories</tt> method when JPA layer thrown an exception.</p>
     *
     * @throws Exception to jUnit
     */
    public void testGetActiveCategoriesJPAFailure() throws Exception {
        try {
            failingRemote.getActiveCategories();
            fail("PersistenceException is expected as an exception occurred on JPA layer.");
        } catch (PersistenceException e) {
            // OK
        }
    }

    /**
     * <p>Tests <tt>getAssetById</tt> method when no such asset.</p>
     *
     * @throws Exception to jUnit
     */
    public void testGetAssetByNonExistingId() throws Exception {
        try {
            getRemote().getAssetById(-1, true);
            fail("EntityNotFoundException is expected as there is no such an entity.");
        } catch (EntityNotFoundException e) {
            // OK
        }
    }

    /**
     * <p>Tests <tt>getAssetById</tt> method when JPA layer thrown an exception.</p>
     *
     * @throws Exception to jUnit
     */
    public void testGetAssetJPAFailure() throws Exception {
        // create asset first...
        Component entity = createAsset(1);
        try {
            failingRemote.getAssetById(entity.getId(), true);
            fail("PersistenceException is expected as an exception occurred on JPA layer.");
        } catch (PersistenceException e) {
            // OK
        }
    }

    /**
     * <p>Tests <tt>getAssetByVersionId</tt> method when no such asset.</p>
     *
     * @throws Exception to jUnit
     */
    public void testGetAssetByNonExistingVersionid() throws Exception {
        try {
            getRemote().getAssetByVersionId(-1);
            fail("EntityNotFoundException is expected as there is no such an entity.");
        } catch (EntityNotFoundException e) {
            // OK
        }
    }

    /**
     * <p>Tests <tt>getAssetByVersionId</tt> method when JPA layer thrown an exception.</p>
     *
     * @throws Exception to jUnit
     */
    public void testGetAssetByVersionJPAFailure() throws Exception {
        // create asset first...
        Component entity = createAsset(1);
        try {
            failingRemote.getAssetByVersionId(entity.getCurrentVersion().getId());
            fail("PersistenceException is expected as an exception occurred on JPA layer.");
        } catch (PersistenceException e) {
            // OK
        }
    }

    /**
     * <p>Tests <tt>createAsset</tt> method when JPA layer thrown an exception.</p>
     *
     * @throws Exception to jUnit
     */
    public void testCreateAssetJPAFailure() throws Exception {
        AssetDTO newAsset = populateDTO(1);
        try {
            failingRemote.createAsset(newAsset);
            fail("PersistenceException is expected as an exception occurred on JPA layer.");
        } catch (PersistenceException e) {
            // OK
        }
    }

    /**
     * <p>Tests <tt>createAsset</tt> method for non-existing technology.</p>
     *
     * @throws Exception to jUnit
     */
    public void testCreateAssetNonExistingTechnology() throws Exception {
        AssetDTO newAsset = populateDTO(1);
        final Technology invalidTechnology = new Technology();
        invalidTechnology.setId(-1L);
        newAsset.setTechnologies(Arrays.asList(invalidTechnology));
        try {
            getRemote().createAsset(newAsset);
            fail("PersistenceException is expected as there is no such a technology.");
        } catch (PersistenceException e) {
            // OK
        }
    }
    /**
     * <p>Tests <tt>createAsset</tt> method for non-existing category.</p>
     *
     * @throws Exception to jUnit
     */
    public void testCreateAssetNonExistingCategory() throws Exception {
        AssetDTO newAsset = populateDTO(1);
        final Category invalidCategory = new Category();
        invalidCategory.setId(-1L);
        newAsset.setCategories(Arrays.asList(invalidCategory));
        try {
            getRemote().createAsset(newAsset);
            fail("PersistenceException is expected as there is no such a category.");
        } catch (PersistenceException e) {
            // OK
        }
    }


    /**
     * <p>Tests <tt>createVersion</tt> method when JPA layer thrown an exception.</p>
     *
     * @throws Exception to jUnit
     */
    public void testCreateVersionJPAFailure() throws Exception {
        AssetDTO newAsset = populateDTO(1);
        newAsset = getRemote().createAsset(newAsset);
        // create second version
        final String newVersion = "1.1";
        newAsset.setVersionText(newVersion); // sent new text version
        newAsset.setVersionId(null); // reset version’s ID
        try {
            failingRemote.createVersion(newAsset);
            fail("PersistenceException is expected as an exception occurred on JPA layer.");
        } catch (PersistenceException e) {
            // OK
        }
    }

    /**
     * <p>Tests <tt>createVersion</tt> method for non-existing asset.</p>
     *
     * @throws Exception to jUnit
     */
    public void testCreateVersionNonExistingAsset() throws Exception {
        AssetDTO newAsset = populateDTO(1);
        newAsset.setId(-1L);
        // create second version
        final String newVersion = "1.1";
        newAsset.setVersionText(newVersion); // sent new text version
        newAsset.setVersionId(null); // reset version’s ID
        try {
            getRemote().createVersion(newAsset);
            fail("EntityNotFoundException is expected as there is no such an entity.");
        } catch (EntityNotFoundException e) {
            // OK
        }
    }


    /**
     * <p>Tests <tt>updateAsset</tt> method when JPA layer thrown an exception.</p>
     *
     * @throws Exception to jUnit
     */
    public void testUpdateAssetJPAFailure() throws Exception {
        AssetDTO newAsset = createAssetDTO(1);
        try {
            failingRemote.updateAsset(newAsset);
            fail("PersistenceException is expected as an exception occurred on JPA layer.");
        } catch (PersistenceException e) {
            // OK
        }
    }

    /**
     * <p>Tests <tt>updateAsset</tt> method for non-existing asset.</p>
     *
     * @throws Exception to jUnit
     */
    public void testUpdateAssetNonExistingAsset() throws Exception {
        AssetDTO newAsset = createAssetDTO(1);
        newAsset.setId(-1L);
        try {
            getRemote().updateAsset(newAsset);
            fail("EntityNotFoundException is expected as there is no such an entity.");
        } catch (EntityNotFoundException e) {
            // OK
        }
    }

    /**
     * <p>Tests <tt>updateAsset</tt> method for non-existing version.</p>
     *
     * @throws Exception to jUnit
     */
    public void testUpdateVersionNonExistingVersion() throws Exception {
        AssetDTO newAsset = createAssetDTO(1);
        newAsset.setVersionId(-1L); // invalid version’s ID
        try {
            getRemote().updateAsset(newAsset);
            fail("EntityNotFoundException is expected as there is no such a version.");
        } catch (EntityNotFoundException e) {
            // OK
        }
    }


    /**
     * <p>Tests <tt>assignUserToAsset</tt> method for non-existing asset.</p>
     *
     * @throws Exception to jUnit
     */
    public void testAssignUserToNonExistingAsset() throws Exception {
        try {
            getRemote().assignUserToAsset(1L, -1);
            fail("EntityNotFoundException is expected as there is no such an entity.");
        } catch (EntityNotFoundException e) {
            // OK
        }
    }

    /**
     * <p>Tests <tt>assignUserToAsset</tt> method when JPA layer thrown an exception.</p>
     *
     * @throws Exception to jUnit
     */
    public void testAssignUserToComponentJPAFailure() throws Exception {
        AssetDTO newAsset = createAssetDTO(1);
        try {
            failingRemote.assignUserToAsset(1L, newAsset.getId());
            fail("PersistenceException is expected as an exception occurred on JPA layer.");
        } catch (PersistenceException e) {
            // OK
        }
    }

    /**
     * <p>Tests <tt>removeUserFromAsset</tt> method for non-existing component.</p>
     *
     * @throws Exception to jUnit
     */
    public void testRemoveUserFromNonExistingComponent() throws Exception {
        try {
            getRemote().removeUserFromAsset(1L, -1);
            fail("EntityNotFoundException is expected as there is no such an entity.");
        } catch (EntityNotFoundException e) {
            // OK
        }
    }

    /**
     * <p>Tests <tt>removeUserFromAsset</tt> method for non-existing user-component association.</p>
     *
     * @throws Exception to jUnit
     */
    public void testRemoveUserFromNonExistingAssociation() throws Exception {
        final Component asset = createAsset(1);
        final CatalogService remote = getRemote();
        // try to remove when there are no associations at all
        try {
            remote.removeUserFromAsset(1L, asset.getId());
            fail("EntityNotFoundException is expected as there is no such an entity.");
        } catch (EntityNotFoundException e) {
            // OK
        }
        // try to remove when there is an association, but another one
        remote.assignUserToAsset(2L, asset.getId());
        try {
            remote.removeUserFromAsset(1L, asset.getId());
            fail("EntityNotFoundException is expected as there is no such an entity.");
        } catch (EntityNotFoundException e) {
            // OK
        }
    }

    /**
     * <p>Tests <tt>removeUserFromAsset</tt> method when JPA layer thrown an exception.</p>
     *
     * @throws Exception to jUnit
     */
    public void testRemoveUserFromComponentJPAFailure() throws Exception {
        AssetDTO newAsset = createAssetDTO(1);
        try {
            failingRemote.removeUserFromAsset(1L, newAsset.getId());
            fail("PersistenceException is expected as an exception occurred on JPA layer.");
        } catch (PersistenceException e) {
            // OK
        }
    }

    /**
     * <p>Tests <tt>findAssets</tt> method when the criteria is null.</p>
     *
     * @throws Exception to jUnit
     */
    public void testFindAssetsNullParameter() throws Exception {
        try {
            failingRemote.findAssets(null, true);
            fail("IllegalArgumentException is expected as no criteria provided.");
        } catch (EJBException e) {
            assertTrue("Invalid cause, should be IllegalArgumentException",
                e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>Tests <tt>findAssets</tt> method when JPA layer thrown an exception.</p>
     *
     * @throws Exception to jUnit
     */
    public void testFindAssetsJPAFailure() throws Exception {
        try {
            failingRemote.findAssets(new SearchCriteria(1L, null, null, null, null, null, null,
                    null, null), false);
            fail("PersistenceException is expected as an exception occurred on JPA layer.");
        } catch (PersistenceException e) {
            // OK
        }
    }
}