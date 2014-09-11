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
 * <p>This class tests <code>CatalogServiceImpl.updateAsset</code> class for illegal arguments handling.</p>
 *
 * @author Retunsky
 * @version 1.0
 */
public class CatalogServiceImplUpdateComponentIllegalArgumentsTest extends CatalogServiceImplBaseTst {
    /**
     * <p>Tests <tt>updateAsset</tt> method when parameter is null.</p>
     *
     * @throws Exception to jUnit
     */
    public void testUpdateComponentNull() throws Exception {
        try {
            getRemote().updateAsset(null);
            fail("IllegalArgumentException is expected as parameter is null.");
        } catch (EJBException e) {
            assertTrue("Invalid cause, should be IllegalArgumentException",
                e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>Tests <tt>updateAsset</tt> method when <p>id</p> is null.</p>
     *
     * @throws Exception to jUnit
     */
    public void testUpdateComponentIdNull() throws Exception {
        AssetDTO newAsset = createAssetDTO(1);
        try {
            newAsset.setId(null);
            getRemote().updateAsset(newAsset);
            fail("IllegalArgumentException is expected as id is null.");
        } catch (EJBException e) {
            assertTrue("Invalid cause, should be IllegalArgumentException",
                e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>Tests <tt>updateAsset</tt> method when <p>versionId</p> is set.</p>
     *
     * @throws Exception to jUnit
     */
    public void testUpdateComponentVersionIdSet() throws Exception {
        AssetDTO newAsset = createAssetDTO(1);
        try {
            newAsset.setVersionId(-1L);
            getRemote().updateAsset(newAsset);
            fail("EntityNotFoundException is expected as version with id doesn't exist.");
        } catch (EntityNotFoundException e) {
            // OK
        }
    }

    /**
     * <p>Tests <tt>updateAsset</tt> method when <p>name</p> is null.</p>
     *
     * @throws Exception to jUnit
     */
    public void testUpdateComponentNameIsNull() throws Exception {
        AssetDTO newAsset = createAssetDTO(1);
        try {
            newAsset.setName(null);
            getRemote().updateAsset(newAsset);
            fail("IllegalArgumentException is expected as name is null.");
        } catch (EJBException e) {
            assertTrue("Invalid cause, should be IllegalArgumentException",
                e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>Tests <tt>updateAsset</tt> method when <p>name</p> is empty.</p>
     *
     * @throws Exception to jUnit
     */
    public void testUpdateComponentNameIsEmpty() throws Exception {
        AssetDTO newAsset = createAssetDTO(1);
        try {
            newAsset.setName(" ");
            getRemote().updateAsset(newAsset);
            fail("IllegalArgumentException is expected as name is empty.");
        } catch (EJBException e) {
            assertTrue("Invalid cause, should be IllegalArgumentException",
                e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>Tests <tt>updateAsset</tt> method when <p>versionText</p> is null.</p>
     *
     * @throws Exception to jUnit
     */
    public void testUpdateComponentVersionTextIsNull() throws Exception {
        AssetDTO newAsset = createAssetDTO(1);
        try {
            newAsset.setVersionText(null);
            getRemote().updateAsset(newAsset);
            fail("IllegalArgumentException is expected as versionText is null.");
        } catch (EJBException e) {
            assertTrue("Invalid cause, should be IllegalArgumentException",
                e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>Tests <tt>updateAsset</tt> method when <p>versionText</p> is empty.</p>
     *
     * @throws Exception to jUnit
     */
    public void testUpdateComponentVersionTextIsEmpty() throws Exception {
        AssetDTO newAsset = createAssetDTO(1);
        try {
            newAsset.setVersionText(" ");
            getRemote().updateAsset(newAsset);
            fail("IllegalArgumentException is expected as versionText is empty.");
        } catch (EJBException e) {
            assertTrue("Invalid cause, should be IllegalArgumentException",
                e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>Tests <tt>updateAsset</tt> method when <p>versionText</p> is null, but versionId is null too.</p>
     *
     * @throws Exception to jUnit
     */
    public void testUpdateComponentVersionTextIsNullVersionIdToo() throws Exception {
        AssetDTO newAsset = createAssetDTO(1);
        try {
            newAsset.setVersionId(null);
            newAsset.setVersionText(null);
            getRemote().updateAsset(newAsset);
        } catch (EJBException e) {
            fail("IllegalArgumentException is not expected as version attributes should be ignored.");
        }
    }

    /**
     * <p>Tests <tt>updateAsset</tt> method when <p>versionText</p> is empty, but versionId is null too.</p>
     *
     * @throws Exception to jUnit
     */
    public void testUpdateComponentVersionTextIsEmptyVersionIdToo() throws Exception {
        AssetDTO newAsset = createAssetDTO(1);
        try {
            newAsset.setVersionText(" ");
            newAsset.setVersionId(null);
            getRemote().updateAsset(newAsset);
        } catch (EJBException e) {
            fail("IllegalArgumentException is not expected as version attributes should be ignored.");
        }
    }

    /**
     * <p>Tests <tt>updateAsset</tt> method when <p>shortDescription</p> is null.</p>
     *
     * @throws Exception to jUnit
     */
    public void testUpdateComponentShortDescriptionIsNull() throws Exception {
        AssetDTO newAsset = createAssetDTO(1);
        try {
            newAsset.setShortDescription(null);
            getRemote().updateAsset(newAsset);
            fail("IllegalArgumentException is expected as shortDescription is null.");
        } catch (EJBException e) {
            assertTrue("Invalid cause, should be IllegalArgumentException",
                e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>Tests <tt>updateAsset</tt> method when <p>shortDescription</p> is empty.</p>
     *
     * @throws Exception to jUnit
     */
    public void testUpdateComponentShortDescriptionIsEmpty() throws Exception {
        AssetDTO newAsset = createAssetDTO(1);
        try {
            newAsset.setShortDescription(" ");
            getRemote().updateAsset(newAsset);
            fail("IllegalArgumentException is expected as shortDescription is empty.");
        } catch (EJBException e) {
            assertTrue("Invalid cause, should be IllegalArgumentException",
                e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>Tests <tt>updateAsset</tt> method when <p>detailedDescription</p> is null.</p>
     *
     * @throws Exception to jUnit
     */
    public void testUpdateComponentDetailedDescriptionIsNull() throws Exception {
        AssetDTO newAsset = createAssetDTO(1);
        try {
            newAsset.setDetailedDescription(null);
            getRemote().updateAsset(newAsset);
            fail("IllegalArgumentException is expected as detailedDescription is null.");
        } catch (EJBException e) {
            assertTrue("Invalid cause, should be IllegalArgumentException",
                e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>Tests <tt>updateAsset</tt> method when <p>detailedDescription</p> is empty.</p>
     *
     * @throws Exception to jUnit
     */
    public void testUpdateComponentDetailedDescriptionIsEmpty() throws Exception {
        AssetDTO newAsset = createAssetDTO(1);
        try {
            newAsset.setDetailedDescription(" ");
            getRemote().updateAsset(newAsset);
            fail("IllegalArgumentException is expected as detailedDescription is empty.");
        } catch (EJBException e) {
            assertTrue("Invalid cause, should be IllegalArgumentException",
                e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>Tests <tt>updateAsset</tt> method when <p>functionalDescription</p> is null.</p>
     *
     * @throws Exception to jUnit
     */
    public void testUpdateComponentFunctionalDescriptionIsNull() throws Exception {
        AssetDTO newAsset = createAssetDTO(1);
        try {
            newAsset.setFunctionalDescription(null);
            getRemote().updateAsset(newAsset);
            fail("IllegalArgumentException is expected as functionalDescription is null.");
        } catch (EJBException e) {
            assertTrue("Invalid cause, should be IllegalArgumentException",
                e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>Tests <tt>updateAsset</tt> method when <p>functionalDescription</p> is empty.</p>
     *
     * @throws Exception to jUnit
     */
    public void testUpdateComponentFunctionalDescriptionIsEmpty() throws Exception {
        AssetDTO newAsset = createAssetDTO(1);
        try {
            newAsset.setFunctionalDescription(" ");
            getRemote().updateAsset(newAsset);
            fail("IllegalArgumentException is expected as functionalDescription is empty.");
        } catch (EJBException e) {
            assertTrue("Invalid cause, should be IllegalArgumentException",
                e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>Tests <tt>updateAsset</tt> method when <p>rootCategory</p> is null.</p>
     *
     * @throws Exception to jUnit
     */
    public void testUpdateComponentRootCategoryIsNull() throws Exception {
        AssetDTO newAsset = createAssetDTO(1);
        try {
            newAsset.setRootCategory(null);
            getRemote().updateAsset(newAsset);
            fail("IllegalArgumentException is expected as rootCategory is null.");
        } catch (EJBException e) {
            assertTrue("Invalid cause, should be IllegalArgumentException",
                e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>Tests <tt>updateAsset</tt> method when <p>categories</p> is null.</p>
     *
     * @throws Exception to jUnit
     */
    public void testUpdateComponentCategoriesIsNull() throws Exception {
        AssetDTO newAsset = createAssetDTO(1);
        try {
            newAsset.setCategories(null);
            getRemote().updateAsset(newAsset);
            fail("IllegalArgumentException is expected as categories is null.");
        } catch (EJBException e) {
            assertTrue("Invalid cause, should be IllegalArgumentException",
                e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>Tests <tt>updateAsset</tt> method when <p>categories</p> is empty.</p>
     *
     * @throws Exception to jUnit
     */
    public void testUpdateComponentCategoriesIsEmpty() throws Exception {
        AssetDTO newAsset = createAssetDTO(1);
        try {
            newAsset.setCategories(Collections.<Category>emptyList());
            getRemote().updateAsset(newAsset);
            fail("IllegalArgumentException is expected as categories is empty.");
        } catch (EJBException e) {
            assertTrue("Invalid cause, should be IllegalArgumentException",
                e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>Tests <tt>updateAsset</tt> method when <p>categories</p> contains null value.</p>
     *
     * @throws Exception to jUnit
     */
    public void testUpdateComponentCategoriesContainsNull() throws Exception {
        AssetDTO newAsset = createAssetDTO(1);
        try {
            newAsset.setCategories(Arrays.asList((Category) null));
            getRemote().updateAsset(newAsset);
            fail("IllegalArgumentException is expected as categories contains null value.");
        } catch (EJBException e) {
            assertTrue("Invalid cause, should be IllegalArgumentException",
                e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>Tests <tt>updateAsset</tt> method when <p>technologies</p> is null.</p>
     *
     * @throws Exception to jUnit
     */
    public void testUpdateComponentTechnologiesIsNull() throws Exception {
        AssetDTO newAsset = createAssetDTO(1);
        try {
            newAsset.setTechnologies(null);
            getRemote().updateAsset(newAsset);
            fail("IllegalArgumentException is expected as technologies is null.");
        } catch (EJBException e) {
            assertTrue("Invalid cause, should be IllegalArgumentException",
                e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>Tests <tt>updateAsset</tt> method when <p>technologies</p> is empty.</p>
     *
     * @throws Exception to jUnit
     */
    public void testUpdateComponentTechnologiesIsEmpty() throws Exception {
        AssetDTO newAsset = createAssetDTO(1);
        try {
            newAsset.setTechnologies(Collections.<Technology>emptyList());
            getRemote().updateAsset(newAsset);
            fail("IllegalArgumentException is expected as technologies contains are empty.");
        } catch (EJBException e) {
            assertTrue("Invalid cause, should be IllegalArgumentException",
                e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>Tests <tt>updateAsset</tt> method when <p>technologies</p> contains null value.</p>
     *
     * @throws Exception to jUnit
     */
    public void testUpdateComponentTechnologiesContainsNull() throws Exception {
        AssetDTO newAsset = createAssetDTO(1);
        try {
            newAsset.setTechnologies(Arrays.asList((Technology) null));
            getRemote().updateAsset(newAsset);
            fail("IllegalArgumentException is expected as technologies contains null value.");
        } catch (EJBException e) {
            assertTrue("Invalid cause, should be IllegalArgumentException",
                e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>Tests <tt>updateAsset</tt> method when <p>technologies</p> is null, but versionId is null too.</p>
     *
     * @throws Exception to jUnit
     */
    public void testUpdateComponentTechnologiesIsNullVersionIdIsNullToo() throws Exception {
        AssetDTO newAsset = createAssetDTO(1);
        try {
            newAsset.setTechnologies(null);
            newAsset.setVersionId(null);
            getRemote().updateAsset(newAsset);
        } catch (EJBException e) {
            fail("Exception is not expected as version attributes should be ignored.");
        }
    }

    /**
     * <p>Tests <tt>updateAsset</tt> method when <p>technologies</p> is empty, but versionId is null too.</p>
     *
     * @throws Exception to jUnit
     */
    public void testUpdateComponentTechnologiesIsEmptyVersionIdIsNullToo() throws Exception {
        AssetDTO newAsset = createAssetDTO(1);
        try {
            newAsset.setTechnologies(Collections.<Technology>emptyList());
            newAsset.setVersionId(null);
            getRemote().updateAsset(newAsset);
        } catch (EJBException e) {
            fail("Exception is not expected as version attributes should be ignored.");
        }
    }

    /**
     * <p>Tests <tt>updateAsset</tt> method when <p>technologies</p> contains null value, but versionId is null too.</p>
     *
     * @throws Exception to jUnit
     */
    public void testUpdateComponentTechnologiesContainsNullVersionIdIsNullToo() throws Exception {
        AssetDTO newAsset = createAssetDTO(1);
        try {
            newAsset.setTechnologies(Arrays.asList((Technology) null));
            newAsset.setVersionId(null);
            getRemote().updateAsset(newAsset);
        } catch (EJBException e) {
            fail("Exception is not expected as version attributes should be ignored.");
        }
    }
}