/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.catalog.service;

import com.topcoder.catalog.entity.Category;
import com.topcoder.catalog.entity.Technology;

import javax.ejb.EJBException;
import java.util.Arrays;
import java.util.Collections;

/**
 * <p>This class tests <code>CatalogServiceImpl.createVersion</code> class for illegal arguments handling.</p>
 *
 * @author Retunsky
 * @version 1.0
 */
public class CatalogServiceImplCreateVersionIllegalArgumentsTest extends CatalogServiceImplBaseTst {
    /**
     * <p>Tests <tt>createVersion</tt> method when parameter is null.</p>
     *
     * @throws Exception to jUnit
     */
    public void testCreateVersionNull() throws Exception {
        try {
            getRemote().createVersion(null);
            fail("IllegalArgumentException is expected as parameter is null.");
        } catch (EJBException e) {
            assertTrue("Invalid cause, should be IllegalArgumentException",
                e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>Tests <tt>createVersion</tt> method when <p>id</p> is not set.</p>
     *
     * @throws Exception to jUnit
     */
    public void testCreateVersionIdSet() throws Exception {
        AssetDTO newAsset = populateDTO(1);
        try {
            newAsset.setId(null);
            getRemote().createVersion(newAsset);
            fail("IllegalArgumentException is expected as id is not set.");
        } catch (EJBException e) {
            assertTrue("Invalid cause, should be IllegalArgumentException",
                e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>Tests <tt>createVersion</tt> method when <p>versionId</p> is set.</p>
     *
     * @throws Exception to jUnit
     */
    public void testCreateVersionVersionIdSet() throws Exception {
        AssetDTO newAsset = populateDTO(1);
        try {
            newAsset.setVersionId(1L);
            getRemote().createVersion(newAsset);
            fail("IllegalArgumentException is expected as version id is set.");
        } catch (EJBException e) {
            assertTrue("Invalid cause, should be IllegalArgumentException",
                e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>Tests <tt>createVersion</tt> method when <p>name</p> is null.</p>
     *
     * @throws Exception to jUnit
     */
    public void testCreateVersionNameIsNull() throws Exception {
        AssetDTO newAsset = populateDTO(1);
        try {
            newAsset.setName(null);
            getRemote().createVersion(newAsset);
            fail("IllegalArgumentException is expected as name is null.");
        } catch (EJBException e) {
            assertTrue("Invalid cause, should be IllegalArgumentException",
                e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>Tests <tt>createVersion</tt> method when <p>name</p> is empty.</p>
     *
     * @throws Exception to jUnit
     */
    public void testCreateVersionNameIsEmpty() throws Exception {
        AssetDTO newAsset = populateDTO(1);
        try {
            newAsset.setName(" ");
            getRemote().createVersion(newAsset);
            fail("IllegalArgumentException is expected as name is empty.");
        } catch (EJBException e) {
            assertTrue("Invalid cause, should be IllegalArgumentException",
                e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>Tests <tt>createVersion</tt> method when <p>versionText</p> is null.</p>
     *
     * @throws Exception to jUnit
     */
    public void testCreateVersionVersionTextIsNull() throws Exception {
        AssetDTO newAsset = populateDTO(1);
        try {
            newAsset.setVersionText(null);
            getRemote().createVersion(newAsset);
            fail("IllegalArgumentException is expected as versionText is null.");
        } catch (EJBException e) {
            assertTrue("Invalid cause, should be IllegalArgumentException",
                e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>Tests <tt>createVersion</tt> method when <p>versionText</p> is empty.</p>
     *
     * @throws Exception to jUnit
     */
    public void testCreateVersionVersionTextIsEmpty() throws Exception {
        AssetDTO newAsset = populateDTO(1);
        try {
            newAsset.setVersionText(" ");
            getRemote().createVersion(newAsset);
            fail("IllegalArgumentException is expected as versionText is empty.");
        } catch (EJBException e) {
            assertTrue("Invalid cause, should be IllegalArgumentException",
                e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>Tests <tt>createVersion</tt> method when <p>shortDescription</p> is null.</p>
     *
     * @throws Exception to jUnit
     */
    public void testCreateVersionShortDescriptionIsNull() throws Exception {
        AssetDTO newAsset = populateDTO(1);
        try {
            newAsset.setShortDescription(null);
            getRemote().createVersion(newAsset);
            fail("IllegalArgumentException is expected as shortDescription is null.");
        } catch (EJBException e) {
            assertTrue("Invalid cause, should be IllegalArgumentException",
                e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>Tests <tt>createVersion</tt> method when <p>shortDescription</p> is empty.</p>
     *
     * @throws Exception to jUnit
     */
    public void testCreateVersionShortDescriptionIsEmpty() throws Exception {
        AssetDTO newAsset = populateDTO(1);
        try {
            newAsset.setShortDescription(" ");
            getRemote().createVersion(newAsset);
            fail("IllegalArgumentException is expected as shortDescription is empty.");
        } catch (EJBException e) {
            assertTrue("Invalid cause, should be IllegalArgumentException",
                e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>Tests <tt>createVersion</tt> method when <p>detailedDescription</p> is null.</p>
     *
     * @throws Exception to jUnit
     */
    public void testCreateVersionDetailedDescriptionIsNull() throws Exception {
        AssetDTO newAsset = populateDTO(1);
        try {
            newAsset.setDetailedDescription(null);
            getRemote().createVersion(newAsset);
            fail("IllegalArgumentException is expected as detailedDescription is null.");
        } catch (EJBException e) {
            assertTrue("Invalid cause, should be IllegalArgumentException",
                e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>Tests <tt>createVersion</tt> method when <p>detailedDescription</p> is empty.</p>
     *
     * @throws Exception to jUnit
     */
    public void testCreateVersionDetailedDescriptionIsEmpty() throws Exception {
        AssetDTO newAsset = populateDTO(1);
        try {
            newAsset.setDetailedDescription(" ");
            getRemote().createVersion(newAsset);
            fail("IllegalArgumentException is expected as detailedDescription is empty.");
        } catch (EJBException e) {
            assertTrue("Invalid cause, should be IllegalArgumentException",
                e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>Tests <tt>createVersion</tt> method when <p>functionalDescription</p> is null.</p>
     *
     * @throws Exception to jUnit
     */
    public void testCreateVersionFunctionalDescriptionIsNull() throws Exception {
        AssetDTO newAsset = populateDTO(1);
        try {
            newAsset.setFunctionalDescription(null);
            getRemote().createVersion(newAsset);
            fail("IllegalArgumentException is expected as functionalDescription is null.");
        } catch (EJBException e) {
            assertTrue("Invalid cause, should be IllegalArgumentException",
                e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>Tests <tt>createVersion</tt> method when <p>functionalDescription</p> is empty.</p>
     *
     * @throws Exception to jUnit
     */
    public void testCreateVersionFunctionalDescriptionIsEmpty() throws Exception {
        AssetDTO newAsset = populateDTO(1);
        try {
            newAsset.setFunctionalDescription(" ");
            getRemote().createVersion(newAsset);
            fail("IllegalArgumentException is expected as functionalDescription is empty.");
        } catch (EJBException e) {
            assertTrue("Invalid cause, should be IllegalArgumentException",
                e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>Tests <tt>createVersion</tt> method when <p>rootCategory</p> is null.</p>
     *
     * @throws Exception to jUnit
     */
    public void testCreateVersionRootCategoryIsNull() throws Exception {
        AssetDTO newAsset = populateDTO(1);
        try {
            newAsset.setRootCategory(null);
            getRemote().createVersion(newAsset);
            fail("IllegalArgumentException is expected as rootCategory is null.");
        } catch (EJBException e) {
            assertTrue("Invalid cause, should be IllegalArgumentException",
                e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>Tests <tt>createVersion</tt> method when <p>categories</p> is null.</p>
     *
     * @throws Exception to jUnit
     */
    public void testCreateVersionCategoriesIsNull() throws Exception {
        AssetDTO newAsset = populateDTO(1);
        try {
            newAsset.setCategories(null);
            getRemote().createVersion(newAsset);
            fail("IllegalArgumentException is expected as categories is null.");
        } catch (EJBException e) {
            assertTrue("Invalid cause, should be IllegalArgumentException",
                e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>Tests <tt>createVersion</tt> method when <p>categories</p> is empty.</p>
     *
     * @throws Exception to jUnit
     */
    public void testCreateVersionCategoriesIsEmpty() throws Exception {
        AssetDTO newAsset = populateDTO(1);
        try {
            newAsset.setCategories(Collections.<Category>emptyList());
            getRemote().createVersion(newAsset);
            fail("IllegalArgumentException is expected as categories is empty.");
        } catch (EJBException e) {
            assertTrue("Invalid cause, should be IllegalArgumentException",
                e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>Tests <tt>createVersion</tt> method when <p>categories</p> contains null value.</p>
     *
     * @throws Exception to jUnit
     */
    public void testCreateVersionCategoriesContainsNull() throws Exception {
        AssetDTO newAsset = populateDTO(1);
        try {
            newAsset.setCategories(Arrays.asList((Category) null));
            getRemote().createVersion(newAsset);
            fail("IllegalArgumentException is expected as categories contains null value.");
        } catch (EJBException e) {
            assertTrue("Invalid cause, should be IllegalArgumentException",
                e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>Tests <tt>createVersion</tt> method when <p>technologies</p> is null.</p>
     *
     * @throws Exception to jUnit
     */
    public void testCreateVersionTechnologiesIsNull() throws Exception {
        AssetDTO newAsset = populateDTO(1);
        try {
            newAsset.setTechnologies(null);
            getRemote().createVersion(newAsset);
            fail("IllegalArgumentException is expected as technologies is null.");
        } catch (EJBException e) {
            assertTrue("Invalid cause, should be IllegalArgumentException",
                e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>Tests <tt>createVersion</tt> method when <p>technologies</p> is empty.</p>
     *
     * @throws Exception to jUnit
     */
    public void testCreateVersionTechnologiesIsEmpty() throws Exception {
        AssetDTO newAsset = populateDTO(1);
        try {
            newAsset.setTechnologies(Collections.<Technology>emptyList());
            getRemote().createVersion(newAsset);
            fail("IllegalArgumentException is expected as technologies is empty.");
        } catch (EJBException e) {
            assertTrue("Invalid cause, should be IllegalArgumentException",
                e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>Tests <tt>createVersion</tt> method when <p>technologies</p> contains null value.</p>
     *
     * @throws Exception to jUnit
     */
    public void testCreateVersionTechnologiesContainsNull() throws Exception {
        AssetDTO newAsset = populateDTO(1);
        try {
            newAsset.setTechnologies(Arrays.asList((Technology) null));
            getRemote().createVersion(newAsset);
            fail("IllegalArgumentException is expected as technologies contains null value.");
        } catch (EJBException e) {
            assertTrue("Invalid cause, should be IllegalArgumentException",
                e.getCause() instanceof IllegalArgumentException);
        }
    }
}