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
 * <p>This class tests <code>CatalogServiceImpl.createAsset</code> class for illegal arguments handling.</p>
 *
 * @author Retunsky
 * @version 1.0
 */
public class CatalogServiceImplCreateAssetIllegalArgumentsTest extends CatalogServiceImplBaseTst {
    /**
     * <p>Tests <tt>createAsset</tt> method when parameter is null.</p>
     *
     * @throws Exception to jUnit
     */
    public void testCreateAssetNull() throws Exception {
        try {
            getRemote().createAsset(null);
            fail("IllegalArgumentException is expected as parameter is null.");
        } catch (EJBException e) {
            assertTrue("Invalid cause, should be IllegalArgumentException",
                e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>Tests <tt>createAsset</tt> method when <p>id</p> is set.</p>
     *
     * @throws Exception to jUnit
     */
    public void testCreateAssetIdSet() throws Exception {
        AssetDTO newAsset = populateDTO(1);
        try {
            newAsset.setId(1L);
            getRemote().createAsset(newAsset);
            fail("IllegalArgumentException is expected as id is set.");
        } catch (EJBException e) {
            assertTrue("Invalid cause, should be IllegalArgumentException",
                e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>Tests <tt>createAsset</tt> method when <p>versionId</p> is set.</p>
     *
     * @throws Exception to jUnit
     */
    public void testCreateAssetVersionIdSet() throws Exception {
        AssetDTO newAsset = populateDTO(1);
        try {
            newAsset.setVersionId(1L);
            getRemote().createAsset(newAsset);
            fail("IllegalArgumentException is expected as version id is set.");
        } catch (EJBException e) {
            assertTrue("Invalid cause, should be IllegalArgumentException",
                e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>Tests <tt>createAsset</tt> method when <p>name</p> is null.</p>
     *
     * @throws Exception to jUnit
     */
    public void testCreateAssetNameIsNull() throws Exception {
        AssetDTO newAsset = populateDTO(1);
        try {
            newAsset.setName(null);
            getRemote().createAsset(newAsset);
            fail("IllegalArgumentException is expected as name is null.");
        } catch (EJBException e) {
            assertTrue("Invalid cause, should be IllegalArgumentException",
                e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>Tests <tt>createAsset</tt> method when <p>name</p> is empty.</p>
     *
     * @throws Exception to jUnit
     */
    public void testCreateAssetNameIsEmpty() throws Exception {
        AssetDTO newAsset = populateDTO(1);
        try {
            newAsset.setName(" ");
            getRemote().createAsset(newAsset);
            fail("IllegalArgumentException is expected as name is empty.");
        } catch (EJBException e) {
            assertTrue("Invalid cause, should be IllegalArgumentException",
                e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>Tests <tt>createAsset</tt> method when <p>versionText</p> is null.</p>
     *
     * @throws Exception to jUnit
     */
    public void testCreateAssetVersionTextIsNull() throws Exception {
        AssetDTO newAsset = populateDTO(1);
        try {
            newAsset.setVersionText(null);
            getRemote().createAsset(newAsset);
            fail("IllegalArgumentException is expected as versionText is null.");
        } catch (EJBException e) {
            assertTrue("Invalid cause, should be IllegalArgumentException",
                e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>Tests <tt>createAsset</tt> method when <p>versionText</p> is empty.</p>
     *
     * @throws Exception to jUnit
     */
    public void testCreateAssetVersionTextIsEmpty() throws Exception {
        AssetDTO newAsset = populateDTO(1);
        try {
            newAsset.setVersionText(" ");
            getRemote().createAsset(newAsset);
            fail("IllegalArgumentException is expected as versionText is empty.");
        } catch (EJBException e) {
            assertTrue("Invalid cause, should be IllegalArgumentException",
                e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>Tests <tt>createAsset</tt> method when <p>shortDescription</p> is null.</p>
     *
     * @throws Exception to jUnit
     */
    public void testCreateAssetShortDescriptionIsNull() throws Exception {
        AssetDTO newAsset = populateDTO(1);
        try {
            newAsset.setShortDescription(null);
            getRemote().createAsset(newAsset);
            fail("IllegalArgumentException is expected as shortDescription is null.");
        } catch (EJBException e) {
            assertTrue("Invalid cause, should be IllegalArgumentException",
                e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>Tests <tt>createAsset</tt> method when <p>shortDescription</p> is empty.</p>
     *
     * @throws Exception to jUnit
     */
    public void testCreateAssetShortDescriptionIsEmpty() throws Exception {
        AssetDTO newAsset = populateDTO(1);
        try {
            newAsset.setShortDescription(" ");
            getRemote().createAsset(newAsset);
            fail("IllegalArgumentException is expected as shortDescription is empty.");
        } catch (EJBException e) {
            assertTrue("Invalid cause, should be IllegalArgumentException",
                e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>Tests <tt>createAsset</tt> method when <p>detailedDescription</p> is null.</p>
     *
     * @throws Exception to jUnit
     */
    public void testCreateAssetDetailedDescriptionIsNull() throws Exception {
        AssetDTO newAsset = populateDTO(1);
        try {
            newAsset.setDetailedDescription(null);
            getRemote().createAsset(newAsset);
            fail("IllegalArgumentException is expected as detailedDescription is null.");
        } catch (EJBException e) {
            assertTrue("Invalid cause, should be IllegalArgumentException",
                e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>Tests <tt>createAsset</tt> method when <p>detailedDescription</p> is empty.</p>
     *
     * @throws Exception to jUnit
     */
    public void testCreateAssetDetailedDescriptionIsEmpty() throws Exception {
        AssetDTO newAsset = populateDTO(1);
        try {
            newAsset.setDetailedDescription(" ");
            getRemote().createAsset(newAsset);
            fail("IllegalArgumentException is expected as detailedDescription is empty.");
        } catch (EJBException e) {
            assertTrue("Invalid cause, should be IllegalArgumentException",
                e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>Tests <tt>createAsset</tt> method when <p>functionalDescription</p> is null.</p>
     *
     * @throws Exception to jUnit
     */
    public void testCreateAssetFunctionalDescriptionIsNull() throws Exception {
        AssetDTO newAsset = populateDTO(1);
        try {
            newAsset.setFunctionalDescription(null);
            getRemote().createAsset(newAsset);
            fail("IllegalArgumentException is expected as functionalDescription is null.");
        } catch (EJBException e) {
            assertTrue("Invalid cause, should be IllegalArgumentException",
                e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>Tests <tt>createAsset</tt> method when <p>functionalDescription</p> is empty.</p>
     *
     * @throws Exception to jUnit
     */
    public void testCreateAssetFunctionalDescriptionIsEmpty() throws Exception {
        AssetDTO newAsset = populateDTO(1);
        try {
            newAsset.setFunctionalDescription(" ");
            getRemote().createAsset(newAsset);
            fail("IllegalArgumentException is expected as functionalDescription is empty.");
        } catch (EJBException e) {
            assertTrue("Invalid cause, should be IllegalArgumentException",
                e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>Tests <tt>createAsset</tt> method when <p>rootCategory</p> is null.</p>
     *
     * @throws Exception to jUnit
     */
    public void testCreateAssetRootCategoryIsNull() throws Exception {
        AssetDTO newAsset = populateDTO(1);
        try {
            newAsset.setRootCategory(null);
            getRemote().createAsset(newAsset);
            fail("IllegalArgumentException is expected as rootCategory is null.");
        } catch (EJBException e) {
            assertTrue("Invalid cause, should be IllegalArgumentException",
                e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>Tests <tt>createAsset</tt> method when <p>categories</p> is null.</p>
     *
     * @throws Exception to jUnit
     */
    public void testCreateAssetCategoriesIsNull() throws Exception {
        AssetDTO newAsset = populateDTO(1);
        try {
            newAsset.setCategories(null);
            getRemote().createAsset(newAsset);
            fail("IllegalArgumentException is expected as categories is null.");
        } catch (EJBException e) {
            assertTrue("Invalid cause, should be IllegalArgumentException",
                e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>Tests <tt>createAsset</tt> method when <p>categories</p> is empty.</p>
     *
     * @throws Exception to jUnit
     */
    public void testCreateAssetCategoriesIsEmpty() throws Exception {
        AssetDTO newAsset = populateDTO(1);
        try {
            newAsset.setCategories(Collections.<Category>emptyList());
            getRemote().createAsset(newAsset);
            fail("IllegalArgumentException is expected as categories is empty.");
        } catch (EJBException e) {
            assertTrue("Invalid cause, should be IllegalArgumentException",
                e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>Tests <tt>createAsset</tt> method when <p>categories</p> contains null value.</p>
     *
     * @throws Exception to jUnit
     */
    public void testCreateAssetCategoriesContainsNull() throws Exception {
        AssetDTO newAsset = populateDTO(1);
        try {
            newAsset.setCategories(Arrays.asList((Category) null));
            getRemote().createAsset(newAsset);
            fail("IllegalArgumentException is expected as categories contains null value.");
        } catch (EJBException e) {
            assertTrue("Invalid cause, should be IllegalArgumentException",
                e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>Tests <tt>createAsset</tt> method when <p>technologies</p> is null.</p>
     *
     * @throws Exception to jUnit
     */
    public void testCreateAssetTechnologiesIsNull() throws Exception {
        AssetDTO newAsset = populateDTO(1);
        try {
            newAsset.setTechnologies(null);
            getRemote().createAsset(newAsset);
            fail("IllegalArgumentException is expected as technologies is null.");
        } catch (EJBException e) {
            assertTrue("Invalid cause, should be IllegalArgumentException",
                e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>Tests <tt>createAsset</tt> method when <p>technologies</p> is empty.</p>
     *
     * @throws Exception to jUnit
     */
    public void testCreateAssetTechnologiesIsEmpty() throws Exception {
        AssetDTO newAsset = populateDTO(1);
        try {
            newAsset.setTechnologies(Collections.<Technology>emptyList());
            getRemote().createAsset(newAsset);
            fail("IllegalArgumentException is expected as technologies is empty.");
        } catch (EJBException e) {
            assertTrue("Invalid cause, should be IllegalArgumentException",
                e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>Tests <tt>createAsset</tt> method when <p>technologies</p> contains null value.</p>
     *
     * @throws Exception to jUnit
     */
    public void testCreateAssetTechnologiesContainsNull() throws Exception {
        AssetDTO newAsset = populateDTO(1);
        try {
            newAsset.setTechnologies(Arrays.asList((Technology) null));
            getRemote().createAsset(newAsset);
            fail("IllegalArgumentException is expected as technologies contains null value.");
        } catch (EJBException e) {
            assertTrue("Invalid cause, should be IllegalArgumentException",
                e.getCause() instanceof IllegalArgumentException);
        }
    }
}