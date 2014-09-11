/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.catalog.service.accuracytests;

import com.topcoder.catalog.entity.Category;
import com.topcoder.catalog.entity.CompForum;
import com.topcoder.catalog.entity.CompLink;
import com.topcoder.catalog.entity.Technology;
import com.topcoder.catalog.service.AssetDTO;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * Accuracy test case for <code>AssetDTO</code>.
 * </p>
 * 
 * @author Retunsky
 * @version 1.0
 */
public class AssetDTOAccuracyTest extends TestCase {
    /**
     * <p>
     * Represents <code>AssetDTO</code> instance for test.
     * </p>
     */
    private AssetDTO assetDTO;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     * 
     * @throws Exception
     *             to jUnit.
     */
    protected void setUp() throws Exception {
        assetDTO = new AssetDTO();
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>AssetDTO()</code>.
     * </p>
     */
    public void testCtorAccuracy() {
        assertNotNull("Failed to instantiate AssetDTO", assetDTO);
    }

    /**
     * <p>
     * Accuracy test for the method <code>getId()</code>.
     * </p>
     */
    public void testGetIdAccuracy() {
        assertNull("The id should be got correctly.", assetDTO.getId());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setId()</code>.
     * </p>
     */
    public void testSetIdAccuracy() {
        Long id = 1L;
        assetDTO.setId(id);
        assertEquals("The id should be set correctly.", id, assetDTO.getId());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getCategories()</code>.
     * </p>
     */
    public void testGetCategoriesAccuracy() {
        assertNull("The categories should be got correctly.", assetDTO.getCategories());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setCategories()</code>.
     * </p>
     */
    public void testSetCategoriesAccuracy() {
        List<Category> value = new ArrayList<Category>();
        assetDTO.setCategories(value);
        assertEquals("The categories should be set correctly.", value, assetDTO.getCategories());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getClientIds()</code>.
     * </p>
     */
    public void testGetClientIdsAccuracy() {
        assertNull("The clientIds should be got correctly.", assetDTO.getClientIds());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setClientIds()</code>.
     * </p>
     */
    public void testSetClientIdsAccuracy() {
        List<Long> value = new ArrayList<Long>();
        assetDTO.setClientIds(value);
        assertEquals("The clientIds should be set correctly.", value, assetDTO.getClientIds());
    }

    /**
     * <p>
     * Accuracy test for the method <code>isCurrentVersionAlsoLatestVersion()</code>.
     * </p>
     */
    public void testIsCurrentVersionAlsoLatestVersionAccuracy() {
        assertFalse("The isCurrentVersionAlsoLatestVersion should be got correctly.", assetDTO
                .isCurrentVersionAlsoLatestVersion());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setCurrentVersionAlsoLatestVersion()</code>.
     * </p>
     */
    public void testSetCurrentVersionAlsoLatestVersionAccuracy() {
        assetDTO.setCurrentVersionAlsoLatestVersion(true);
        assertTrue("The isCurrentVersionAlsoLatestVersion should be set correctly.", assetDTO
                .isCurrentVersionAlsoLatestVersion());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getDetailedDescription()</code>.
     * </p>
     */
    public void testGetDetailedDescriptionAccuracy() {
        assertNull("The detailedDescription should be got correctly.", assetDTO.getDetailedDescription());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setDetailedDescription()</code>.
     * </p>
     */
    public void testSetDetailedDescriptionAccuracy() {
        assetDTO.setDetailedDescription("desc");
        assertEquals("The detailedDescription should be set correctly.", "desc", assetDTO.getDetailedDescription());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getForum()</code>.
     * </p>
     */
    public void testGetForumAccuracy() {
        assertNull("The forum should be got correctly.", assetDTO.getForum());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setForum()</code>.
     * </p>
     */
    public void testSetForumAccuracy() {
        CompForum value = new CompForum();
        assetDTO.setForum(value);
        assertEquals("The forum should be set correctly.", value, assetDTO.getForum());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getFunctionalDescription()</code>.
     * </p>
     */
    public void testGetFunctionalDescriptionAccuracy() {
        assertNull("The functionalDescription should be got correctly.", assetDTO.getFunctionalDescription());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setFunctionalDescription()</code>.
     * </p>
     */
    public void testSetFunctionalDescriptionAccuracy() {
        assetDTO.setFunctionalDescription("functionalDescription");
        assertEquals("The functionalDescription should be set correctly.", "functionalDescription", assetDTO
                .getFunctionalDescription());
    }

    /**
     * <p>
     * Accuracy test for the method <code>isInformationComplete()</code>.
     * </p>
     */
    public void testGetInformationCompleteAccuracy() {
        assertFalse("The informationComplete should be got correctly.", assetDTO.isInformationComplete());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setInformationComplete()</code>.
     * </p>
     */
    public void testSetInformationCompleteAccuracy() {
        assetDTO.setInformationComplete(true);
        assertTrue("The informationComplete should be set correctly.", assetDTO.isInformationComplete());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getLink()</code>.
     * </p>
     */
    public void testGetLinkAccuracy() {
        assertNull("The link should be got correctly.", assetDTO.getLink());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setLink()</code>.
     * </p>
     */
    public void testSetLinkAccuracy() {
        CompLink value = new CompLink();
        assetDTO.setLink(value);
        assertEquals("The link should be set correctly.", value, assetDTO.getLink());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getName()</code>.
     * </p>
     */
    public void testGetNameAccuracy() {
        assertNull("The name should be got correctly.", assetDTO.getName());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setName()</code>.
     * </p>
     */
    public void testGetSetNameAccuracy() {
        assetDTO.setName("name");
        assertEquals("The name should be set correctly.", "name", assetDTO.getName());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getProductionDate()</code>.
     * </p>
     */
    public void testGetProductionDateAccuracy() {
        assertNull("The productionDate should be got correctly.", assetDTO.getProductionDate());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setProductionDate()</code>.
     * </p>
     */
    public void testSetProductionDateAccuracy() {
        Date value = new Date();
        assetDTO.setProductionDate(value);
        assertEquals("The productionDate should be got correctly.", value, assetDTO.getProductionDate());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getRootCategory()</code>.
     * </p>
     */
    public void testGetRootCategoryAccuracy() {
        assertNull("The rootCategory should be got correctly.", assetDTO.getRootCategory());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setRootCategory()</code>.
     * </p>
     */
    public void testSetRootCategoryAccuracy() {
        Category value = new Category();
        assetDTO.setRootCategory(value);
        assertEquals("The rootCategory should be got correctly.", value, assetDTO.getRootCategory());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getShortDescription()</code>.
     * </p>
     */
    public void testGetShortDescriptionAccuracy() {
        assertNull("The shortDescription should be got correctly.", assetDTO.getShortDescription());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setShortDescription()</code>.
     * </p>
     */
    public void testSetShortDescriptionAccuracy() {
        assetDTO.setShortDescription("shortDescription");
        assertEquals("The shortDescription should be got correctly.", "shortDescription", assetDTO
                .getShortDescription());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getTechnologies()</code>.
     * </p>
     */
    public void testGetTechnologiesAccuracy() {
        assertNull("The technologies should be got correctly.", assetDTO.getTechnologies());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setTechnologies()</code>.
     * </p>
     */
    public void testSetTechnologiesAccuracy() {
        List<Technology> value = new ArrayList<Technology>();
        assetDTO.setTechnologies(value);
        assertEquals("The technologies should be got correctly.", value, assetDTO.getTechnologies());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getUserIds()</code>.
     * </p>
     */
    public void testGetUserIdsAccuracy() {
        assertNull("The userIds should be got correctly.", assetDTO.getUserIds());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setUserIds()</code>.
     * </p>
     */
    public void testSetUserIdsAccuracy() {
        List<Long> value = new ArrayList<Long>();
        assetDTO.setUserIds(value);
        assertEquals("The userIds should be got correctly.", value, assetDTO.getUserIds());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getVersionId()</code>.
     * </p>
     */
    public void testGetVersionIdAccuracy() {
        assertNull("The versionId should be got correctly.", assetDTO.getVersionId());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setVersionId()</code>.
     * </p>
     */
    public void testSetVersionIdAccuracy() {
        Long value = 25769803783L;
        assetDTO.setVersionId(value);
        assertEquals("The versionId should be got correctly.", value, assetDTO.getVersionId());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getVersionText()</code>.
     * </p>
     */
    public void testGetVersionTextAccuracy() {
        assertNull("The versionText should be got correctly.", assetDTO.getVersionText());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setVersionText()</code>.
     * </p>
     */
    public void testSetVersionTextAccuracy() {
        assetDTO.setVersionText("versionText");
        assertEquals("The versionText should be got correctly.", "versionText", assetDTO.getVersionText());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getVersionNumber()</code>.
     * </p>
     */
    public void testGetVersionNumberAccuracy() {
        assertNull("The versionNumber should be got correctly.", assetDTO.getVersionNumber());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setVersionNumber()</code>.
     * </p>
     */
    public void testSetVersionNumberAccuracy() {
        Long value = 8L;
        assetDTO.setVersionNumber(value);
        assertEquals("The versionNumber should be got correctly.", value, assetDTO.getVersionNumber());
    }
}
