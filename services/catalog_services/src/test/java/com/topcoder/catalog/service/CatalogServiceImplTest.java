/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.catalog.service;

import static com.topcoder.catalog.service.TestHelper.getEntityTransaction;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.ejb.EJBException;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;

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

/**
 * <p>
 * This class tests <code>CatalogServiceImpl</code> class.
 * </p>
 *
 * @author Retunsky, TCSDEVELOPER
 * @version 1.1
 */
public class CatalogServiceImplTest extends CatalogServiceImplBaseTst {
    /**
     * <p>
     * Tests <tt>getPhases</tt> method.
     * </p>
     *
     * @throws Exception
     *             to jUnit
     */
    public void testGetPhases() throws Exception {
        // there should be 5 predefined phases created by test_data.sql
        final List<Phase> phases = getRemote().getPhases();
        // check the result
        assertEquals("There should be all created phases", 5, phases.size());
    }

    /**
     * <p>
     * Tests <tt>getActiveTechnologies</tt> method.
     * </p>
     *
     * @throws Exception
     *             to jUnit
     */
    public void testGetActiveTechnologies() throws Exception {
        // there should be 3 predefined technologies, 2 of them are active created by test_data.sql
        final List<Technology> technologies = getRemote().getActiveTechnologies();
        // check the result
        assertEquals("Invalid number of active technologies", 2, technologies.size());
    }

    /**
     * <p>
     * Tests <tt>getActiveCategories</tt> method.
     * </p>
     *
     * @throws Exception
     *             to jUnit
     */
    public void testGetActiveCategories() throws Exception {
        // there are four predefined categories created by test_data.sql
        // 2 of them are active and viewable
        final List<Category> categories = getRemote().getActiveCategories();
        // check the result
        assertEquals("Invalid number of active&viewable categories", 2, categories.size());
    }

    /**
     * <p>
     * Tests <tt>getAssetById</tt> method with the current version.
     * </p>
     *
     * @throws Exception
     *             to jUnit
     */
    public void testGetAssetByIdCurrentVersion() throws Exception {
        // create asset first...
        Component entity = createAsset(1);
        // retrieve asset and its current version
        AssetDTO asset = getRemote().getAssetById(entity.getId(), true);
        assertNotNull("Asset should be found", asset);
        assertEquals("Asset should have current version", "1.0", asset.getVersionText());
        assertTrue("Information should be complete", asset.isInformationComplete());
    }

    /**
     * <p>
     * Tests <tt>getAssetById</tt> method with the latest version.
     * </p>
     *
     * @throws Exception
     *             to jUnit
     */
    public void testGetAssetByIdLatestVersion() throws Exception {
        // create asset first...
        Component entity = createAsset(1);
        // retrieve asset and its latest version
        AssetDTO asset = getRemote().getAssetById(entity.getId(), false);
        assertNotNull("Asset should be found", asset);
        assertEquals("Asset should have current version", "1.1", asset.getVersionText());
        assertTrue("Information should be complete", asset.isInformationComplete());
    }

    /**
     * <p>
     * Tests <tt>getAssetByVersionId</tt> method.
     * </p>
     *
     * @throws Exception
     *             to jUnit
     */
    public void testGetAssetByVersionId() throws Exception {
        // create asset first...
        Component entity = createAsset(1);
        // retrieve asset
        final EntityTransaction tx = getEntityTransaction();
        tx.begin();
        try {
            for (CompVersion compVersion : entity.getVersions()) {
                AssetDTO asset = getRemote().getAssetByVersionId(compVersion.getId());
                assertNotNull("Asset should be found", asset);
                assertTrue("Information should be complete", asset.isInformationComplete());
            }
        } catch (RuntimeException e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw new EJBException(e);
        }
    }

    /**
     * <p>
     * Tests <tt>createAsset</tt> method.
     * </p>
     *
     * @throws Exception
     *             to jUnit
     */
    public void testCreateAsset() throws Exception {
        AssetDTO newAsset = populateDTO(1);
        newAsset.setUserIds(Arrays.asList(1L, 2L));
        newAsset.setClientIds(Arrays.asList(1L, 2L));
        newAsset = getRemote().createAsset(newAsset);
        getEntityManager().clear(); // otherwise not updated instance will be loaded from JPA cache
        final Component entity = (Component) getEntityManager().createQuery("from Component")
                .getSingleResult();
        final CompVersion compVersion = entity.getCurrentVersion();
        // check Asset properties
        assertNotNull("Asset should be stored", entity);
        assertNotNull("Asset version should be stored", compVersion);
        assertEquals("Invalid name populated", newAsset.getName(), entity.getName());
        assertEquals("Invalid short description populated", newAsset.getShortDescription(), entity
                .getShortDesc());
        assertEquals("Invalid detailed description populated", newAsset.getDetailedDescription(),
                entity.getDescription());
        assertEquals("Invalid functional description populated", newAsset
                .getFunctionalDescription(), entity.getFunctionalDesc());
        assertEquals("Invalid root category", newAsset.getRootCategory().getId(), entity
                .getRootCategory().getId());
        assertEquals("Invalid categories count", 2, entity.getCategories().size());

        // check CompVersion properties
        assertEquals("Invalid version text", newAsset.getVersionText(), compVersion
                .getVersionText().trim());
        assertEquals("Invalid technologies count", 2, compVersion.getTechnologies().size());
    }

    /**
     * <p>
     * Tests <tt>createAsset</tt> method with clients.
     * </p>
     *
     * @throws Exception
     *             to jUnit
     */
    public void testCreateAssetClients() throws Exception {
        AssetDTO newAsset = populateDTO(1);
        newAsset.setClientIds(Arrays.asList(1L, 2L));
        newAsset = getRemote().createAsset(newAsset);
        getEntityManager().clear(); // otherwise not updated instance will be loaded from JPA cache
        final AssetDTO assetDTO = getRemote().getAssetById(newAsset.getId(), true);
        // check Asset properties
        assertNotNull("Asset should be stored", assetDTO);
        assertEquals("Invalid clients count", 2, assetDTO.getClientIds().size());
    }

    /**
     * <p>
     * Tests <tt>createAsset</tt> method with empty clients list.
     * </p>
     *
     * @throws Exception
     *             to jUnit
     */
    public void testCreateAssetClientsEmpty() throws Exception {
        AssetDTO newAsset = populateDTO(1);
        newAsset.setClientIds(Collections.<Long> emptyList());
        newAsset = getRemote().createAsset(newAsset);
        getEntityManager().clear(); // otherwise not updated instance will be loaded from JPA cache
        final AssetDTO assetDTO = getRemote().getAssetById(newAsset.getId(), true);
        // check Asset properties
        assertNotNull("Asset should be stored", assetDTO);
        assertTrue("There should be no clients", assetDTO.getClientIds() == null
                || assetDTO.getClientIds().isEmpty());
    }

    /**
     * <p>
     * Tests <tt>createAsset</tt> method with users.
     * </p>
     *
     * @throws Exception
     *             to jUnit
     */
    public void testCreateAssetUsers() throws Exception {
        AssetDTO newAsset = populateDTO(1);
        newAsset.setUserIds(Arrays.asList(1L, 2L));
        newAsset = getRemote().createAsset(newAsset);
        getEntityManager().clear(); // otherwise not updated instance will be loaded from JPA cache
        final AssetDTO assetDTO = getRemote().getAssetById(newAsset.getId(), true);
        // check Asset properties
        assertNotNull("Asset should be stored", assetDTO);
        assertEquals("Invalid users count", 2, assetDTO.getUserIds().size());
    }

    /**
     * <p>
     * Tests <tt>createAsset</tt> method with empty users list.
     * </p>
     *
     * @throws Exception
     *             to jUnit
     */
    public void testCreateAssetUsersEmpty() throws Exception {
        AssetDTO newAsset = populateDTO(1);
        newAsset.setUserIds(Collections.<Long> emptyList());
        newAsset = getRemote().createAsset(newAsset);
        getEntityManager().clear(); // otherwise not updated instance will be loaded from JPA cache
        final AssetDTO assetDTO = getRemote().getAssetById(newAsset.getId(), true);
        // check Asset properties
        assertNotNull("Asset should be stored", assetDTO);
        assertTrue("There should be no users", assetDTO.getUserIds() == null
                || assetDTO.getUserIds().isEmpty());
    }

    /**
     * <p>
     * Tests <tt>createAsset</tt> method with forum.
     * </p>
     *
     * @throws Exception
     *             to jUnit
     */
    public void testCreateAssetForum() throws Exception {
        AssetDTO newAsset = populateDTO(1);
        newAsset.setForum(new CompForum());
        newAsset = getRemote().createAsset(newAsset);
        getEntityManager().clear(); // otherwise not updated instance will be loaded from JPA cache
        final AssetDTO assetDTO = getRemote().getAssetById(newAsset.getId(), true);
        // check Asset properties
        assertNotNull("Asset should be stored", assetDTO);
        assertNotNull("Forum should be stored", assetDTO.getForum());
    }

    /**
     * <p>
     * Tests <tt>createAsset</tt> method with link.
     * </p>
     *
     * @throws Exception
     *             to jUnit
     */
    public void testCreateAssetLink() throws Exception {
        AssetDTO newAsset = populateDTO(1);
        final CompLink link = new CompLink();
        link.setLink("svn-link");
        newAsset.setLink(link);
        newAsset = getRemote().createAsset(newAsset);
        getEntityManager().clear(); // otherwise not updated instance will be loaded from JPA cache
        final AssetDTO assetDTO = getRemote().getAssetById(newAsset.getId(), true);
        // check Asset properties
        assertNotNull("Asset should be stored", assetDTO);
        assertNotNull("Link should be stored", assetDTO.getLink());
    }

    /**
     * <p>
     * Tests <tt>createVersion</tt> method.
     * </p>
     *
     * @throws Exception
     *             to jUnit
     */
    public void testCreateVersion() throws Exception {
        AssetDTO newAsset = populateDTO(1);
        newAsset = getRemote().createAsset(newAsset);
        // create second version
        final String newVersion = "1.1";
        newAsset.setVersionText(newVersion); // sent new text version
        newAsset.setVersionId(null); // reset version’s ID
        getRemote().createVersion(newAsset);

        // we need to query entity, as remote call doesn't return anything, and doesn't update
        // parameters
        getEntityManager().clear(); // otherwise not updated instance will be loaded from JPA cache
        final Component entity = (Component) getEntityManager().createQuery("from Component")
                .getSingleResult();
        final CompVersion latest = getLatestVersion(entity);
        // check CompVersion properties
        assertEquals("Invalid version text", newVersion, latest.getVersionText().trim());
        final CompVersionDates compVersionDates = latest.getVersionDates().get(111L);
        assertNotNull("Version dates not found", compVersionDates);
    }

    /**
     * <p>
     * Tests <tt>updateAsset</tt> method.
     * </p>
     *
     * @throws Exception
     *             to jUnit
     */
    public void testUpdateAsset() throws Exception {
        // final AssetDTO updated = createAssetDTO(1);
        // updated.setName("UPDATED");
        // updated.setShortDescription("UPDATED");
        // updated.setDetailedDescription("UPDATED");
        // updated.setFunctionalDescription("UPDATED");
        // updated.setCategories(Arrays.asList(updated.getCategories().get(0))); // leave only one
        // element
        // updated.setVersionText("UPDATED");
        //
        // getRemote().updateAsset(updated);
        // // we need to query entity, as remote call doesn't return anything, and doesn't update
        // parameters
        // getEntityManager().clear(); // otherwise not updated instance will be loaded from JPA
        // cache
        // final Component entity = (Component) getEntityManager().createQuery("from
        // Component").getSingleResult();
        // // check Asset properties
        // assertEquals("Invalid name populated", updated.getName(), entity.getName());
        // assertEquals("Invalid short description populated", updated.getShortDescription(),
        // entity.getShortDesc());
        // assertEquals("Invalid detailed description populated",
        // updated.getDetailedDescription(), entity.getDescription());
        // assertEquals("Invalid functional description populated",
        // updated.getFunctionalDescription(), entity.getFunctionalDesc());
        // assertEquals("Invalid root category",
        // updated.getRootCategory().getId(), entity.getRootCategory().getId());
        // assertEquals("Invalid categories count", 1, entity.getCategories().size());
        // assertEquals("Invalid version text", updated.getVersionText(),
        // entity.getCurrentVersion().getVersionText().trim());
    }

    /**
     * <p>
     * Tests <tt>updateAsset</tt> method without version.
     * </p>
     *
     * @throws Exception
     *             to jUnit
     */
    public void testUpdateAssetWithoutVersion() throws Exception {
        final AssetDTO updated = createAssetDTO(1);
        updated.setName("UPDATED");
        updated.setShortDescription("UPDATED");
        updated.setDetailedDescription("UPDATED");
        updated.setFunctionalDescription("UPDATED");
        updated.setCategories(Arrays.asList(updated.getCategories().get(0))); // leave only one
                                                                                // element
        updated.setVersionId(null);

        getRemote().updateAsset(updated);
        // we need to query entity, as remote call doesn't return anything, and doesn't update
        // parameters
        getEntityManager().clear(); // otherwise not updated instance will be loaded from JPA cache
        final Component entity = (Component) getEntityManager().createQuery("from Component")
                .getSingleResult();
        // check Asset properties
        assertEquals("Invalid name populated", updated.getName(), entity.getName());
        assertEquals("Invalid short description populated", updated.getShortDescription(), entity
                .getShortDesc());
        assertEquals("Invalid detailed description populated", updated.getDetailedDescription(),
                entity.getDescription());
        assertEquals("Invalid functional description populated",
                updated.getFunctionalDescription(), entity.getFunctionalDesc());
        assertEquals("Invalid root category", updated.getRootCategory().getId(), entity
                .getRootCategory().getId());
        assertEquals("Invalid categories count", 1, entity.getCategories().size());
    }

    /**
     * <p>
     * Tests <tt>assignUserToAsset</tt> method.
     * </p>
     *
     * @throws Exception
     *             to jUnit
     */
    public void testAssignUserToComponent() throws Exception {
        final Component asset = createAsset(1);

        final CatalogService remote = getRemote();

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
     * Tests <tt>assignUserToAsset</tt> method when the association already exists.
     * </p>
     *
     * @throws Exception
     *             to jUnit
     */
    public void testTwiceAssignUserToAsset() throws Exception {
        final Component asset = createAsset(1);

        getRemote().assignUserToAsset(1L, asset.getId());

        try {
            getRemote().assignUserToAsset(1L, asset.getId());
        } catch (PersistenceException e) {
            fail("No exception expected on association user&asset which have been already associated");
        }
    }

    /**
     * <p>
     * Tests <tt>removeUserFromAsset</tt> method.
     * </p>
     *
     * @throws Exception
     *             to jUnit
     */
    public void testRemoveUserFromAsset() throws Exception {
        final Component asset = createAsset(1);

        getRemote().assignUserToAsset(1L, asset.getId());

        CompUser compUser = getUserAssetAssociation(1L, asset);

        assertNotNull("Not found compUser", compUser);

        getRemote().removeUserFromAsset(1L, asset.getId());

        compUser = getUserAssetAssociation(1L, asset);

        assertNull("Found compUser, while it should be removed", compUser);

    }

    /**
     * <p>
     * Tests <tt>findAsset</tt> method.
     * </p>
     *
     * @throws Exception
     *             to jUnit
     */
    public void testFindAsset() throws Exception {
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
     * Tests <tt>findAsset</tt> method for component create date criteria.
     * </p>
     *
     * @throws Exception
     *             to jUnit
     * @since 1.1
     */
    public void testFindAsset_createDate_accuracy1() throws Exception {
        final AssetDTO[] assetDTOs = new AssetDTO[10];
        for (int i = 0; i < assetDTOs.length; i++) {
            assetDTOs[i] = createAssetDTO(i);
        }

        final SearchCriteria criteria1 = new SearchCriteria(null, null, null, null, null, null,
                null, new Date(), assetDTOs[7].getProductionDate());
        List<AssetDTO> assets = getRemote().findAssets(criteria1, true);
        assertEquals("Wrong asset number returned.", 0, assets.size());
    }

    /**
     * <p>
     * Tests <tt>findAsset</tt> method for component version create date criteria.
     * </p>
     *
     * @throws Exception
     *             to jUnit
     * @since 1.1
     */
    public void testFindAsset_createDate_accuracy2() throws Exception {
        final AssetDTO[] assetDTOs = new AssetDTO[10];
        for (int i = 0; i < assetDTOs.length; i++) {
            assetDTOs[i] = createAssetDTO(i);
        }

        final SearchCriteria criteria1 = new SearchCriteria(null, null, null, null, null,
                assetDTOs[2].getProductionDate(), assetDTOs[7].getProductionDate(), null, null);
        List<AssetDTO> assets = getRemote().findAssets(criteria1, true);
        // verify result
        assertEquals("Wrong asset number returned.", 0, assets.size());
    }

    /**
     * <p>
     * Tests <tt>getAllAssetVersions</tt> method for versions are get correctly.
     * </p>
     *
     * @throws Exception
     *             to jUnit
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
            return (CompUser) getEntityManager()
                    .createQuery(
                            "SELECT u FROM CompUser u where u.userId=:userId and u.component.id=:componentId")
                    .setParameter("userId", userId).setParameter("componentId", component.getId())
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
