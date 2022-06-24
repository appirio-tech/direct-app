/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.catalog.service.accuracytests;

import com.topcoder.catalog.entity.Category;
import com.topcoder.catalog.entity.CompClient;
import com.topcoder.catalog.entity.CompForum;
import com.topcoder.catalog.entity.CompLink;
import com.topcoder.catalog.entity.CompUser;
import com.topcoder.catalog.entity.CompVersion;
import com.topcoder.catalog.entity.CompVersionDates;
import com.topcoder.catalog.entity.Component;
import com.topcoder.catalog.entity.Phase;
import com.topcoder.catalog.entity.Technology;
import com.topcoder.catalog.service.AssetDTO;
import com.topcoder.catalog.service.CatalogService;
import com.topcoder.catalog.service.PersistenceException;
import com.topcoder.catalog.service.SearchCriteria;
import com.topcoder.catalog.service.TestHelper;

import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 * Accuracy test the <code>CatalogServiceImpl</code> class.
 * </p>
 *
 * @author Retunsky
 * @version 1.0
 */
public class CatalogServiceImplAccuracyTest extends CatalogServiceImplBaseTest {
    /**
     * <p>
     * Accuracy test for <code>getPhases</code> method.
     * </p>
     *
     * @throws Exception
     *             to jUnit
     */
    public void testGetPhasesAccuracy() throws Exception {
        List<Phase> phases = getRemote().getPhases();
        assertEquals("There should be all created phases", 5, phases.size());
    }

    /**
     * <p>
     * Accuracy test for <code>getActiveTechnologies</code> method.
     * </p>
     *
     * @throws Exception
     *             to jUnit
     */
    public void testGetActiveTechnologiesAccuracy() throws Exception {
        List<Technology> technologies = getRemote().getActiveTechnologies();
        assertEquals("Invalid number of active technologies", 2, technologies.size());
    }

    /**
     * <p>
     * Accuracy test for <code>getActiveCategories</code> method.
     * </p>
     *
     * @throws Exception
     *             to jUnit
     */
    public void testGetActiveCategoriesAccuracy() throws Exception {
        List<Category> categories = getRemote().getActiveCategories();
        assertEquals("Invalid number of active&viewable categories", 2, categories.size());
    }

    /**
     * <p>
     * Accuracy test for <code>getAssetById</code> method with the current version.
     * </p>
     *
     * @throws Exception
     *             to jUnit
     */
    public void testGetAssetByIdCurrentVersionAccuracy() throws Exception {
        Component entity = createAsset(1);
        AssetDTO asset = getRemote().getAssetById(entity.getId(), true);
        assertNotNull("Asset should be found", asset);
        assertEquals("Asset should have current version", "1.0", asset.getVersionText());
        assertTrue("Information should be complete", asset.isInformationComplete());
    }

    /**
     * <p>
     * Accuracy test for <code>getAssetById</code> method with the latest version.
     * </p>
     *
     * @throws Exception
     *             to jUnit
     */
    public void testGetAssetByIdLatestVersionAccuracy() throws Exception {
        Component entity = createAsset(1);
        AssetDTO asset = getRemote().getAssetById(entity.getId(), false);
        assertNotNull("Asset should be found", asset);
        assertEquals("Asset should have current version", "1.1", asset.getVersionText());
        assertTrue("Information should be complete", asset.isInformationComplete());
    }

    /**
     * <p>
     * Accuracy test for <code>getAssetByVersionId</code> method.
     * </p>
     *
     * @throws Exception
     *             to jUnit
     */
    public void testGetAssetByVersionIdAccuracy() throws Exception {
        Component entity = createAsset(1);
        for (CompVersion compVersion : entity.getVersions()) {
            AssetDTO asset = getRemote().getAssetByVersionId(compVersion.getId());
            assertNotNull("Asset should be found", asset);
            assertTrue("Information should be complete", asset.isInformationComplete());
        }
    }

    /**
     * <p>
     * Accuracy test for <code>createAsset</code> method.
     * </p>
     *
     * @throws Exception
     *             to jUnit
     */
    public void testCreateAssetAccuracy() throws Exception {
        AssetDTO newAsset = populateDTO(1);
        newAsset = getRemote().createAsset(newAsset);
        getEntityManager().clear();
        Component entity = (Component) getEntityManager().createQuery("from Component").getSingleResult();
        CompVersion compVersion = entity.getCurrentVersion();

        assertNotNull("Asset should be stored", entity);
        assertNotNull("Asset version should be stored", compVersion);
        assertEquals("Invalid name populated", newAsset.getName(), entity.getName());
        assertEquals("Invalid short description populated", newAsset.getShortDescription(), entity.getShortDesc());
        assertEquals("Invalid detailed description populated", newAsset.getDetailedDescription(), entity
                .getDescription());
        assertEquals("Invalid functional description populated", newAsset.getFunctionalDescription(), entity
                .getFunctionalDesc());
        assertEquals("Invalid root category", newAsset.getRootCategory().getId(), entity.getRootCategory().getId());
        assertEquals("Invalid categories count", 2, entity.getCategories().size());

        assertEquals("Invalid version text", newAsset.getVersionText(), compVersion.getVersionText().trim());
        assertEquals("Invalid version number", new Long(1), compVersion.getVersion());
        assertEquals("Invalid technologies count", 2, compVersion.getTechnologies().size());
    }

    /**
     * <p>
     * Accuracy test for <code>createAsset</code> method with clients.
     * </p>
     *
     * @throws Exception
     *             to jUnit
     */
    public void testCreateAssetClientsAccuracy() throws Exception {
        AssetDTO newAsset = populateDTO(1);
        newAsset.setClientIds(Arrays.asList(1L, 2L));
        newAsset = getRemote().createAsset(newAsset);
        getEntityManager().clear();
        AssetDTO assetDTO = getRemote().getAssetById(newAsset.getId(), true);
        assertNotNull("Asset should be stored", assetDTO);
        assertEquals("Invalid clients count", 2, assetDTO.getClientIds().size());
    }

    /**
     * <p>
     * Accuracy test for <code>createAsset</code> method with empty clients list.
     * </p>
     *
     * @throws Exception
     *             to jUnit
     */
    public void testCreateAssetClientsEmptyAccuracy() throws Exception {
        AssetDTO newAsset = populateDTO(1);
        newAsset.setClientIds(Collections.<Long> emptyList());
        newAsset = getRemote().createAsset(newAsset);
        getEntityManager().clear();
        AssetDTO assetDTO = getRemote().getAssetById(newAsset.getId(), true);

        assertNotNull("Asset should be stored", assetDTO);
        assertTrue("There should be no clients", assetDTO.getClientIds() == null || assetDTO.getClientIds().isEmpty());
    }

    /**
     * <p>
     * Accuracy test for <code>createAsset</code> method with users.
     * </p>
     *
     * @throws Exception
     *             to jUnit
     */
    public void testCreateAssetUsersAccuracy() throws Exception {
        AssetDTO newAsset = populateDTO(1);
        newAsset.setUserIds(Arrays.asList(1L, 2L));
        newAsset = getRemote().createAsset(newAsset);
        getEntityManager().clear();
        AssetDTO assetDTO = getRemote().getAssetById(newAsset.getId(), true);

        assertNotNull("Asset should be stored", assetDTO);
        assertEquals("Invalid users count", 2, assetDTO.getUserIds().size());
    }

    /**
     * <p>
     * Accuracy test for <code>createAsset</code> method with empty users list.
     * </p>
     *
     * @throws Exception
     *             to jUnit
     */
    public void testCreateAssetUsersEmptyAccuracy() throws Exception {
        AssetDTO newAsset = populateDTO(1);
        newAsset.setUserIds(Collections.<Long> emptyList());
        newAsset = getRemote().createAsset(newAsset);
        getEntityManager().clear();
        AssetDTO assetDTO = getRemote().getAssetById(newAsset.getId(), true);

        assertNotNull("Asset should be stored", assetDTO);
        assertTrue("There should be no users", assetDTO.getUserIds() == null || assetDTO.getUserIds().isEmpty());
    }

    /**
     * <p>
     * Accuracy test for <code>createAsset</code> method with forum.
     * </p>
     *
     * @throws Exception
     *             to jUnit
     */
    public void testCreateAssetForumAccuracy() throws Exception {
        AssetDTO newAsset = populateDTO(1);
        newAsset.setForum(new CompForum());
        newAsset = getRemote().createAsset(newAsset);
        getEntityManager().clear();
        AssetDTO assetDTO = getRemote().getAssetById(newAsset.getId(), true);

        assertNotNull("Asset should be stored", assetDTO);
        assertNotNull("Forum should be stored", assetDTO.getForum());
    }

    /**
     * <p>
     * Accuracy test for <code>createAsset</code> method with link.
     * </p>
     *
     * @throws Exception
     *             to jUnit
     */
    public void testCreateAssetLinkAccuracy() throws Exception {
        AssetDTO newAsset = populateDTO(1);
        CompLink link = new CompLink();
        link.setLink("svn-link");
        newAsset.setLink(link);
        newAsset = getRemote().createAsset(newAsset);
        getEntityManager().clear();
        AssetDTO assetDTO = getRemote().getAssetById(newAsset.getId(), true);

        assertNotNull("Asset should be stored", assetDTO);
        assertNotNull("Link should be stored", assetDTO.getLink());
    }

    /**
     * <p>
     * Accuracy test for <code>createVersion</code> method.
     * </p>
     *
     * @throws Exception
     *             to jUnit
     */
    public void testCreateVersionAccuracy() throws Exception {
        AssetDTO newAsset = populateDTO(1);
        newAsset = getRemote().createAsset(newAsset);
        // create second version
        String newVersion = "1.1";
        newAsset.setVersionText(newVersion);
        newAsset.setVersionId(null);
        getRemote().createVersion(newAsset);

        getEntityManager().clear();
        Component entity = (Component) getEntityManager().createQuery("from Component").getSingleResult();
        CompVersion latest = getLatestVersion(entity);

        assertEquals("Invalid version text", newVersion, latest.getVersionText().trim());
        assertEquals("Invalid version number", new Long(2), latest.getVersion());
        CompVersionDates compVersionDates = latest.getVersionDates().get(111L);
        assertNotNull("Version dates not found", compVersionDates);
        assertNull("Production date should be null", compVersionDates.getProductionDate());
    }

    /**
     * <p>
     * Accuracy test for <code>updateAsset</code> method.
     * </p>
     *
     * @throws Exception
     *             to jUnit
     */
    public void testUpdateAssetAccuracy() throws Exception {
        AssetDTO updated = createAssetDTO(1);
        updated.setName("UPDATED");
        updated.setShortDescription("UPDATED");
        updated.setDetailedDescription("UPDATED");
        updated.setFunctionalDescription("UPDATED");
        updated.setCategories(Arrays.asList(updated.getCategories().get(0)));

        getRemote().updateAsset(updated);
        getEntityManager().clear();
        Component entity = (Component) getEntityManager().createQuery("from Component").getSingleResult();

        assertEquals("Invalid name populated", updated.getName(), entity.getName());
        assertEquals("Invalid short description populated", updated.getShortDescription(), entity.getShortDesc());
        assertEquals("Invalid detailed description populated", updated.getDetailedDescription(), entity
                .getDescription());
        assertEquals("Invalid functional description populated", updated.getFunctionalDescription(), entity
                .getFunctionalDesc());
        assertEquals("Invalid root category", updated.getRootCategory().getId(), entity.getRootCategory().getId());
        assertEquals("Invalid categories count", 1, entity.getCategories().size());
    }

    /**
     * <p>
     * Accuracy test for <code>assignUserToAsset</code> method.
     * </p>
     *
     * @throws Exception
     *             to jUnit
     */
    public void testAssignUserToComponentAccuracy() throws Exception {
        Component asset = createAsset(1);
        CatalogService remote = getRemote();
        remote.assignUserToAsset(1L, asset.getId());

        CompUser compUser;
        compUser = getUserAssetAssociation(1L, asset);
        assertNotNull("Not found compUser", compUser);
        remote.assignUserToAsset(2L, asset.getId());
        compUser = getUserAssetAssociation(2L, asset);
        assertNotNull("Not found compUser", compUser);
    }

    /**
     * <p>
     * Accuracy test for <code>assignUserToAsset</code> method when the association already
     * exists.
     * </p>
     *
     * @throws Exception
     *             to jUnit
     */
    public void testTwiceAssignUserToAssetAccuracy() throws Exception {
        Component asset = createAsset(1);

        getRemote().assignUserToAsset(1L, asset.getId());

        try {
            getRemote().assignUserToAsset(1L, asset.getId());
        } catch (PersistenceException e) {
            fail("No exception expected on association user&asset which have been already associated");
        }
    }

    /**
     * <p>
     * Accuracy test for <code>removeUserFromAsset</code> method.
     * </p>
     *
     * @throws Exception
     *             to jUnit
     */
    public void testRemoveUserFromAssetAccuracy() throws Exception {
        Component asset = createAsset(1);
        getRemote().assignUserToAsset(1L, asset.getId());
        CompUser compUser = getUserAssetAssociation(1L, asset);

        assertNotNull("Not found compUser", compUser);
        getRemote().removeUserFromAsset(1L, asset.getId());
        compUser = getUserAssetAssociation(1L, asset);
        assertNull("Found compUser, while it should be removed", compUser);
    }

    /**
     * <p>
     * Accuracy test for <code>findAsset</code> method.
     * </p>
     *
     * @throws Exception
     *             to jUnit
     */
    public void testFindAssetAccuracy() throws Exception {
        // all these parameters points two the first asset
        final Long userId = 1L;
        final Long clientId = 2L;
        final String name = "SErviCES1"; // partial and in another case
        final String[] descriptions = {"CLuE1", "SHorT1", "FunCTIonaL1"}; // all descriptions
        final List<Category> categories = Arrays.asList(
                getEntityManager().find(Category.class, 1L), getEntityManager().find(
                        Category.class, 3L));

        final Component first = createAsset(1);
        createAsset(2); // create two assets

        // create client-asset assignment
        CompClient compClient = new CompClient();
        compClient.setClientId(2L);
        compClient.setComponent(first);
        final EntityTransaction tx = TestHelper.getEntityTransaction();
        tx.begin();
        getEntityManager().persist(compClient);
        tx.commit();

        // start searching with different parameters (fully enumerated combinations)
        for (int i = 1; i <= 0x1F; i++) {
            final Long pUserId = (i & 0x10) != 0 ? userId : null;
            final Long pClientId = (i & 0x08) != 0 ? clientId : null;
            final List<Category> pCategories = (i & 0x04) != 0 ? categories : null;
            final String pName = (i & 0x02) != 0 ? name : (i & 0x01) == 0 ? " " : null;
            final String pDescription = (i & 0x01) != 0 ? descriptions[(i / 2) % 3]
                    : (i & 0x02) == 0 ? " " : null;
            final SearchCriteria criteria = new SearchCriteria(pUserId, pClientId, pCategories,
                    pName, pDescription, null, null, null, null);
            final boolean current = i % 2 == 1;
            final List<AssetDTO> assets = getRemote().findAssets(criteria, current);
            assertEquals("Invalid number of found items", 1, assets.size());
            final AssetDTO assetDTO = assets.get(0);
            assertFalse("It should be incomplete information", assetDTO.isInformationComplete());
            assertEquals("Invalid item's id", first.getId(), assetDTO.getId());
            assertEquals("Invalid version", current ? "1.0" : "1.1", assetDTO.getVersionText());
        }
    }

    /**
     * <p>
     * Retrieves user-to-component association.
     * </p>
     *
     * @param userId
     *            user's id to associate with a component
     * @param component
     *            component to associate a user with
     * @return CompUser association or null, if there is no such an association
     *
     * @throws Exception
     *             to jUnit
     */
    private CompUser getUserAssetAssociation(long userId, Component component) throws Exception {
        try {
            return (CompUser) getEntityManager().createQuery(
                    "SELECT u FROM CompUser u where u.userId=:userId and u.component.id=:componentId").setParameter(
                    "userId", userId).setParameter("componentId", component.getId()).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
