/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.catalog.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.TestCase;

import com.topcoder.catalog.entity.Category;
import com.topcoder.catalog.entity.CompDocumentation;
import com.topcoder.catalog.entity.CompForum;
import com.topcoder.catalog.entity.CompLink;
import com.topcoder.catalog.entity.Technology;

/**
 * <p>Unit test case for {@link AssetDTO}.</p>
 *
 * @author Retunsky, TCSDEVELOPER
 * @version 1.1
 */
public class AssetDTOTest extends TestCase {
    /**
     * <p>Represents AssetDTO instance for testing.</p>
     */
    private AssetDTO assetDTO;

    /**
     * <p>Creates a new instance of AssetDTO.</p>
     *
     * @throws Exception to jUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();
        assetDTO = new AssetDTO();
    }

    /**
     * <p>Tears down the test environment.</p>
     *
     * @throws Exception to jUnit.
     */
    protected void tearDown() throws Exception {
        assetDTO = null;
        super.tearDown();
    }

    /**
     * <p>Tests <code>AssetDTO()</code> constructor.</p>
     */
    public void testAssetDTO() {
        assertNotNull("Unable to instantiate AssetDTO", assetDTO);
    }

    /**
     * <p>Tests <code>getId()</code> and
     * <code>setId()</code> methods for accuracy.</p>
     */
    public void testGetSetId() {
        assertNull("Incorrect default id value", assetDTO.getId());
        Long value = 1L;
        // set a id
        assetDTO.setId(value);
        assertEquals("Incorrect id value after setting a new one",
            value, assetDTO.getId());
    }

    /**
     * <p>Tests <code>setId(null)</code>.</p>
     */
    public void testIdAllowsNull() {
        // set a id
        // set null
        try {
            assetDTO.setId(null);
            assertNull("Field 'id' should contain 'null' value",
                assetDTO.getId());
        } catch (IllegalArgumentException e) {
            fail("Field 'id' should allow null values");
        }
    }

    /**
     * <p>Tests <code>getCategories()</code> and
     * <code>setCategories()</code> methods for accuracy.</p>
     */
    public void testGetSetCategories() {
        assertNull("Incorrect default categories value", assetDTO.getCategories());
        List<Category> value = new ArrayList<Category>();
        // set a categories
        assetDTO.setCategories(value);
        assertEquals("Incorrect categories value after setting a new one",
            value, assetDTO.getCategories());
    }

    /**
     * <p>Tests <code>setCategories(null)</code>.</p>
     */
    public void testCategoriesAllowsNull() {
        // set a categories
        // set null
        try {
            assetDTO.setCategories(null);
            assertNull("Field 'categories' should contain 'null' value",
                assetDTO.getCategories());
        } catch (IllegalArgumentException e) {
            fail("Field 'categories' should allow null values");
        }
    }

    /**
     * <p>Tests <code>getClientIds()</code> and
     * <code>setClientIds()</code> methods for accuracy.</p>
     */
    public void testGetSetClientIds() {
        assertNull("Incorrect default clientIds value", assetDTO.getClientIds());
        List<Long> value = new ArrayList<Long>();
        // set a clientIds
        assetDTO.setClientIds(value);
        assertEquals("Incorrect clientIds value after setting a new one",
            value, assetDTO.getClientIds());
    }

    /**
     * <p>Tests <code>setClientIds(null)</code>.</p>
     */
    public void testClientIdsAllowsNull() {
        // set a clientIds
        // set null
        try {
            assetDTO.setClientIds(null);
            assertNull("Field 'clientIds' should contain 'null' value",
                assetDTO.getClientIds());
        } catch (IllegalArgumentException e) {
            fail("Field 'clientIds' should allow null values");
        }
    }

    /**
     * <p>Tests <code>isCurrentVersionAlsoLatestVersion()</code> and
     * <code>setCurrentVersionAlsoLatestVersion()</code> methods for accuracy.</p>
     */
    public void testGetSetCurrentVersionAlsoLatestVersion() {
        assertFalse("Incorrect default currentVersionAlsoLatestVersion value",
            assetDTO.isCurrentVersionAlsoLatestVersion());
        boolean value = true;
        // set a currentVersionAlsoLatestVersion
        assetDTO.setCurrentVersionAlsoLatestVersion(value);
        assertEquals("Incorrect currentVersionAlsoLatestVersion value after setting a new one",
            value, assetDTO.isCurrentVersionAlsoLatestVersion());
    }

    /**
     * <p>Tests <code>getDetailedDescription()</code> and
     * <code>setDetailedDescription()</code> methods for accuracy.</p>
     */
    public void testGetSetDetailedDescription() {
        assertNull("Incorrect default detailedDescription value", assetDTO.getDetailedDescription());
        String value = "1";
        // set a detailedDescription
        assetDTO.setDetailedDescription(value);
        assertEquals("Incorrect detailedDescription value after setting a new one",
            value, assetDTO.getDetailedDescription());
    }

    /**
     * <p>Tests <code>setDetailedDescription(null)</code>.</p>
     */
    public void testDetailedDescriptionAllowsNull() {
        // set a detailedDescription
        // set null
        try {
            assetDTO.setDetailedDescription(null);
            assertNull("Field 'detailedDescription' should contain 'null' value",
                assetDTO.getDetailedDescription());
        } catch (IllegalArgumentException e) {
            fail("Field 'detailedDescription' should allow null values");
        }
    }

    /**
     * <p>Tests <code>getForum()</code> and
     * <code>setForum()</code> methods for accuracy.</p>
     */
    public void testGetSetForum() {
        assertNull("Incorrect default forum value", assetDTO.getForum());
        CompForum value = new CompForum();
        // set a forum
        assetDTO.setForum(value);
        assertEquals("Incorrect forum value after setting a new one",
            value, assetDTO.getForum());
    }

    /**
     * <p>Tests <code>setForum(null)</code>.</p>
     */
    public void testForumAllowsNull() {
        // set a forum
        // set null
        try {
            assetDTO.setForum(null);
            assertNull("Field 'forum' should contain 'null' value",
                assetDTO.getForum());
        } catch (IllegalArgumentException e) {
            fail("Field 'forum' should allow null values");
        }
    }

    /**
     * <p>Tests <code>getFunctionalDescription()</code> and
     * <code>setFunctionalDescription()</code> methods for accuracy.</p>
     */
    public void testGetSetFunctionalDescription() {
        assertNull("Incorrect default functionalDescription value", assetDTO.getFunctionalDescription());
        String value = "2";
        // set a functionalDescription
        assetDTO.setFunctionalDescription(value);
        assertEquals("Incorrect functionalDescription value after setting a new one",
            value, assetDTO.getFunctionalDescription());
    }

    /**
     * <p>Tests <code>setFunctionalDescription(null)</code>.</p>
     */
    public void testFunctionalDescriptionAllowsNull() {
        // set a functionalDescription
        // set null
        try {
            assetDTO.setFunctionalDescription(null);
            assertNull("Field 'functionalDescription' should contain 'null' value",
                assetDTO.getFunctionalDescription());
        } catch (IllegalArgumentException e) {
            fail("Field 'functionalDescription' should allow null values");
        }
    }

    /**
     * <p>Tests <code>isInformationComplete()</code> and
     * <code>setInformationComplete()</code> methods for accuracy.</p>
     */
    public void testGetSetInformationComplete() {
        assertFalse("Incorrect default informationComplete value", assetDTO.isInformationComplete());
        boolean value = true;
        // set a informationComplete
        assetDTO.setInformationComplete(value);
        assertEquals("Incorrect informationComplete value after setting a new one",
            value, assetDTO.isInformationComplete());
    }

    /**
     * <p>Tests <code>getLink()</code> and
     * <code>setLink()</code> methods for accuracy.</p>
     */
    public void testGetSetLink() {
        assertNull("Incorrect default link value", assetDTO.getLink());
        CompLink value = new CompLink();
        // set a link
        assetDTO.setLink(value);
        assertEquals("Incorrect link value after setting a new one",
            value, assetDTO.getLink());
    }

    /**
     * <p>Tests <code>setLink(null)</code>.</p>
     */
    public void testLinkAllowsNull() {
        // set a link
        // set null
        try {
            assetDTO.setLink(null);
            assertNull("Field 'link' should contain 'null' value",
                assetDTO.getLink());
        } catch (IllegalArgumentException e) {
            fail("Field 'link' should allow null values");
        }
    }

    /**
     * <p>Tests <code>getName()</code> and
     * <code>setName()</code> methods for accuracy.</p>
     */
    public void testGetSetName() {
        assertNull("Incorrect default name value", assetDTO.getName());
        String value = "3";
        // set a name
        assetDTO.setName(value);
        assertEquals("Incorrect name value after setting a new one",
            value, assetDTO.getName());
    }

    /**
     * <p>Tests <code>setName(null)</code>.</p>
     */
    public void testNameAllowsNull() {
        // set a name
        // set null
        try {
            assetDTO.setName(null);
            assertNull("Field 'name' should contain 'null' value",
                assetDTO.getName());
        } catch (IllegalArgumentException e) {
            fail("Field 'name' should allow null values");
        }
    }

    /**
     * <p>Tests <code>getProductionDate()</code> and
     * <code>setProductionDate()</code> methods for accuracy.</p>
     */
    public void testGetSetProductionDate() {
        assertNull("Incorrect default productionDate value", assetDTO.getProductionDate());
        Date value = new Date(1167944400000L) /*2008-01-05*/;
        // set a productionDate
        assetDTO.setProductionDate(value);
        assertEquals("Incorrect productionDate value after setting a new one",
            value, assetDTO.getProductionDate());
    }

    /**
     * <p>Tests <code>setProductionDate(null)</code>.</p>
     */
    public void testProductionDateAllowsNull() {
        // set a productionDate
        // set null
        try {
            assetDTO.setProductionDate(null);
            assertNull("Field 'productionDate' should contain 'null' value",
                assetDTO.getProductionDate());
        } catch (IllegalArgumentException e) {
            fail("Field 'productionDate' should allow null values");
        }
    }

    /**
     * <p>Tests <code>getRootCategory()</code> and
     * <code>setRootCategory()</code> methods for accuracy.</p>
     */
    public void testGetSetRootCategory() {
        assertNull("Incorrect default rootCategory value", assetDTO.getRootCategory());
        Category value = new Category();
        // set a rootCategory
        assetDTO.setRootCategory(value);
        assertEquals("Incorrect rootCategory value after setting a new one",
            value, assetDTO.getRootCategory());
    }

    /**
     * <p>Tests <code>setRootCategory(null)</code>.</p>
     */
    public void testRootCategoryAllowsNull() {
        // set a rootCategory
        // set null
        try {
            assetDTO.setRootCategory(null);
            assertNull("Field 'rootCategory' should contain 'null' value",
                assetDTO.getRootCategory());
        } catch (IllegalArgumentException e) {
            fail("Field 'rootCategory' should allow null values");
        }
    }

    /**
     * <p>Tests <code>getShortDescription()</code> and
     * <code>setShortDescription()</code> methods for accuracy.</p>
     */
    public void testGetSetShortDescription() {
        assertNull("Incorrect default shortDescription value", assetDTO.getShortDescription());
        String value = "5";
        // set a shortDescription
        assetDTO.setShortDescription(value);
        assertEquals("Incorrect shortDescription value after setting a new one",
            value, assetDTO.getShortDescription());
    }

    /**
     * <p>Tests <code>setShortDescription(null)</code>.</p>
     */
    public void testShortDescriptionAllowsNull() {
        // set a shortDescription
        // set null
        try {
            assetDTO.setShortDescription(null);
            assertNull("Field 'shortDescription' should contain 'null' value",
                assetDTO.getShortDescription());
        } catch (IllegalArgumentException e) {
            fail("Field 'shortDescription' should allow null values");
        }
    }

    /**
     * <p>Tests <code>getTechnologies()</code> and
     * <code>setTechnologies()</code> methods for accuracy.</p>
     */
    public void testGetSetTechnologies() {
        assertNull("Incorrect default technologies value", assetDTO.getTechnologies());
        List<Technology> value = new ArrayList<Technology>();
        // set a technologies
        assetDTO.setTechnologies(value);
        assertEquals("Incorrect technologies value after setting a new one",
            value, assetDTO.getTechnologies());
    }

    /**
     * <p>Tests <code>setTechnologies(null)</code>.</p>
     */
    public void testTechnologiesAllowsNull() {
        // set a technologies
        // set null
        try {
            assetDTO.setTechnologies(null);
            assertNull("Field 'technologies' should contain 'null' value",
                assetDTO.getTechnologies());
        } catch (IllegalArgumentException e) {
            fail("Field 'technologies' should allow null values");
        }
    }

    /**
     * <p>Tests <code>getUserIds()</code> and
     * <code>setUserIds()</code> methods for accuracy.</p>
     */
    public void testGetSetUserIds() {
        assertNull("Incorrect default userIds value", assetDTO.getUserIds());
        List<Long> value = new ArrayList<Long>();
        // set a userIds
        assetDTO.setUserIds(value);
        assertEquals("Incorrect userIds value after setting a new one",
            value, assetDTO.getUserIds());
    }

    /**
     * <p>Tests <code>setUserIds(null)</code>.</p>
     */
    public void testUserIdsAllowsNull() {
        // set a userIds
        // set null
        try {
            assetDTO.setUserIds(null);
            assertNull("Field 'userIds' should contain 'null' value",
                assetDTO.getUserIds());
        } catch (IllegalArgumentException e) {
            fail("Field 'userIds' should allow null values");
        }
    }

    /**
     * <p>Tests <code>getVersionId()</code> and
     * <code>setVersionId()</code> methods for accuracy.</p>
     */
    public void testGetSetVersionId() {
        assertNull("Incorrect default versionId value", assetDTO.getVersionId());
        Long value = 25769803783L;
        // set a versionId
        assetDTO.setVersionId(value);
        assertEquals("Incorrect versionId value after setting a new one",
            value, assetDTO.getVersionId());
    }

    /**
     * <p>Tests <code>setVersionId(null)</code>.</p>
     */
    public void testVersionIdAllowsNull() {
        // set a versionId
        // set null
        try {
            assetDTO.setVersionId(null);
            assertNull("Field 'versionId' should contain 'null' value",
                assetDTO.getVersionId());
        } catch (IllegalArgumentException e) {
            fail("Field 'versionId' should allow null values");
        }
    }

    /**
     * <p>Tests <code>getVersionText()</code> and
     * <code>setVersionText()</code> methods for accuracy.</p>
     */
    public void testGetSetVersionText() {
        assertNull("Incorrect default versionText value", assetDTO.getVersionText());
        String value = "8";
        // set a versionText
        assetDTO.setVersionText(value);
        assertEquals("Incorrect versionText value after setting a new one",
            value, assetDTO.getVersionText());
    }

    /**
     * <p>Tests <code>setVersionText(null)</code>.</p>
     */
    public void testVersionTextAllowsNull() {
        // set a versionText
        // set null
        try {
            assetDTO.setVersionText(null);
            assertNull("Field 'versionText' should contain 'null' value",
                assetDTO.getVersionText());
        } catch (IllegalArgumentException e) {
            fail("Field 'versionText' should allow null values");
        }
    }

    /**
     * <p>Tests <code>getVersionNumber()</code> and
     * <code>setVersionNumber()</code> methods for accuracy.</p>
     */
    public void testGetSetVersionNumber() {
        assertNull("Incorrect default versionNumber value", assetDTO.getVersionNumber());
        Long value = 8L;
        // set a versionNumber
        assetDTO.setVersionNumber(value);
        assertEquals("Incorrect versionNumber value after setting a new one",
            value, assetDTO.getVersionNumber());
    }

    /**
     * <p>Tests <code>setVersionNumber(null)</code>.</p>
     */
    public void testVersionNumberAllowsNull() {
        // set a versionNumber
        // set null
        try {
            assetDTO.setVersionNumber(null);
            assertNull("Field 'versionNumber' should contain 'null' value",
                assetDTO.getVersionNumber());
        } catch (IllegalArgumentException e) {
            fail("Field 'versionNumber' should allow null values");
        }
    }

    /**
     * <p>Tests <code>getDocumentation()</code> and
     * <code>setDocumentation()</code> methods for accuracy.</p>
     * @since 1.1
     */
    public void testGetSetDocumentation() {
        assertNull("Incorrect default documentation value", assetDTO.getDocumentation());
        List<CompDocumentation> value = new ArrayList<CompDocumentation>();
        value.add(new CompDocumentation());
        // set a Documentation
        assetDTO.setDocumentation(value);
        assertEquals("Incorrect documentation value after setting a new one",
            value, assetDTO.getDocumentation());
        // empty list is valid
        value.clear();
        try {
            assetDTO.setDocumentation(value);
            // ok
        } catch (IllegalArgumentException e) {
            fail("setDocumentation should accept empty list.");
        }
    }

    /**
     * <p>Tests <code>setDocumentation(null)</code>.</p>
     * @since 1.1
     */
    public void testDocumentationAllowsNull() {
        // set a Documentation
        // set null
        try {
            assetDTO.setDocumentation(null);
            assertNull("Field 'documentation' should contain 'null' value",
                    assetDTO.getDocumentation());
        } catch (IllegalArgumentException e) {
            fail("Field 'documentation' should allow null values");
        }
    }
}
