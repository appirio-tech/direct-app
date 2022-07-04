/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.catalog.service.failuretests;

import java.util.Arrays;
import java.util.Collections;

import javax.ejb.EJBException;

import com.topcoder.catalog.entity.Category;
import com.topcoder.catalog.entity.CompDocumentation;
import com.topcoder.catalog.entity.Technology;
import com.topcoder.catalog.service.AssetDTO;

/**
 * <p>
 * This class tests <code>CatalogServiceImpl.createAsset</code> class for illegal arguments handling.
 * </p>
 *
 * @author kaqi072821, hfx
 * @version 1.1
 * @since 1.0
 */
public class CatalogServiceImplArgumentFailureTest extends CatalogServiceImplFailureTestBase {
    /**
     * <p>
     * Failure tests for <tt>createAsset</tt> method when parameter is null.
     * </p>
     *
     * @throws Exception to jUnit
     */
    public void test_CreateAsset_fail_Null() throws Exception {
        try {
            getRemote().createAsset(null);
            fail("IllegalArgumentException is expected as parameter is null.");
        } catch (EJBException e) {
            assertTrue("Invalid cause, should be IllegalArgumentException",
                e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>
     * Failure tests for <tt>createAsset</tt> method when id is set.
     * </p>
     *
     * @throws Exception to jUnit
     */
    public void test_CreateAsset_fail_IdSet() throws Exception {
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
     * <p>
     * Failure tests for <tt>createAsset</tt> method when versionId is set.
     * </p>
     *
     * @throws Exception to jUnit
     */
    public void test_CreateAsset_fail_VersionIdSet() throws Exception {
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
     * <p>
     * Failure tests for <tt>createAsset</tt> method when name is null.
     * </p>
     *
     * @throws Exception to jUnit
     */
    public void test_CreateAsset_fail_NameNull() throws Exception {
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
     * <p>
     * Failure tests for <tt>createAsset</tt> method when name is empty.
     * </p>
     *
     * @throws Exception to jUnit
     */
    public void test_CreateAsset_fail_NameIsEmpty() throws Exception {
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
     * <p>
     * Failure tests for <tt>createAsset</tt> method when versionText is null.
     * </p>
     *
     * @throws Exception to jUnit
     */
    public void test_CreateAsset_fail_VersionTextIsNull() throws Exception {
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
     * <p>
     * Failure tests for <tt>createAsset</tt> method when versionText is empty.
     * </p>
     *
     * @throws Exception to jUnit
     */
    public void test_CreateAsset_fail_VersionTextIsEmpty() throws Exception {
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
     * <p>
     * Failure tests for <tt>createAsset</tt> method when shortDescription is null.
     * </p>
     *
     * @throws Exception to jUnit
     */
    public void test_CreateAsset_fail_ShortDescriptionIsNull() throws Exception {
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
     * <p>
     * Failure tests for <tt>createAsset</tt> method when shortDescription is empty.
     * </p>
     *
     * @throws Exception to jUnit
     */
    public void test_CreateAsset_fail_ShortDescriptionIsEmpty() throws Exception {
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
     * <p>
     * Failure tests for <tt>createAsset</tt> method when detailedDescription is null.
     * </p>
     *
     * @throws Exception to jUnit
     */
    public void test_CreateAsset_fail_DetailedDescriptionIsNull() throws Exception {
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
     * <p>
     * Failure tests for <tt>createAsset</tt> method when detailedDescription is empty.
     * </p>
     *
     * @throws Exception to jUnit
     */
    public void test_CreateAsset_fail_DetailedDescriptionIsEmpty() throws Exception {
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
     * <p>
     * Failure tests for <tt>createAsset</tt> method when functionalDescription is null.
     * </p>
     *
     * @throws Exception to jUnit
     */
    public void test_CreateAsset_fail_FunctionalDescriptionIsNull() throws Exception {
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
     * <p>
     * Failure tests for <tt>createAsset</tt> method when functionalDescription is empty.
     * </p>
     *
     * @throws Exception to jUnit
     */
    public void test_CreateAsset_fail_FunctionalDescriptionIsEmpty() throws Exception {
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
     * <p>
     * Failure tests for <tt>createAsset</tt> method when rootCategory is null.
     * </p>
     *
     * @throws Exception to jUnit
     */
    public void test_CreateAsset_fail_RootCategoryIsNull() throws Exception {
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
     * <p>
     * Failure tests for <tt>createAsset</tt> method when categories is null.
     * </p>
     *
     * @throws Exception to jUnit
     */
    public void test_CreateAsset_fail_CategoriesIsNull() throws Exception {
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
     * <p>
     * Failure tests for <tt>createAsset</tt> method when categories is empty.
     * </p>
     *
     * @throws Exception to jUnit
     */
    public void test_CreateAsset_fail_CategoriesIsEmpty() throws Exception {
        AssetDTO newAsset = populateDTO(1);
        try {
            newAsset.setCategories(Collections.<Category> emptyList());
            getRemote().createAsset(newAsset);
            fail("IllegalArgumentException is expected as categories is empty.");
        } catch (EJBException e) {
            assertTrue("Invalid cause, should be IllegalArgumentException",
                e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>
     * Failure tests for <tt>createAsset</tt> method when categories contains null value.
     * </p>
     *
     * @throws Exception to jUnit
     */
    public void test_CreateAsset_fail_CategoriesContainsNull() throws Exception {
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
     * <p>
     * Failure tests for <tt>createAsset</tt> method when technologies is null.
     * </p>
     *
     * @throws Exception to jUnit
     */
    public void test_CreateAsset_fail_TechnologiesIsNull() throws Exception {
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
     * <p>
     * Failure tests for <tt>createAsset</tt> method when technologies is empty.
     * </p>
     *
     * @throws Exception to jUnit
     */
    public void test_CreateAsset_fail_TechnologiesIsEmpty() throws Exception {
        AssetDTO newAsset = populateDTO(1);
        try {
            newAsset.setTechnologies(Collections.<Technology> emptyList());
            getRemote().createAsset(newAsset);
            fail("IllegalArgumentException is expected as technologies is empty.");
        } catch (EJBException e) {
            assertTrue("Invalid cause, should be IllegalArgumentException",
                e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>
     * Failure tests for <tt>createAsset</tt> method when technologies contains null value.
     * </p>
     *
     * @throws Exception to jUnit
     */
    public void test_CreateAsset_fail_TechnologiesContainsNull() throws Exception {
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

    /**
     * <p>
     * Failure tests for <tt>createVersion</tt> method when parameter is null.
     * </p>
     *
     * @throws Exception to jUnit
     */
    public void test_CreateVersion_fail_Null() throws Exception {
        try {
            getRemote().createVersion(null);
            fail("IllegalArgumentException is expected as parameter is null.");
        } catch (EJBException e) {
            assertTrue("Invalid cause, should be IllegalArgumentException",
                e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>
     * Failure tests for <tt>createVersion</tt> method when id is not set.
     * </p>
     *
     * @throws Exception to jUnit
     */
    public void test_CreateVersion_fail_IdSet() throws Exception {
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
     * <p>
     * Failure tests for <tt>createVersion</tt> method when versionId is set.
     * </p>
     *
     * @throws Exception to jUnit
     */
    public void test_CreateVersion_fail_VersionIdSet() throws Exception {
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
     * <p>
     * Failure tests for <tt>createVersion</tt> method when name is null.
     * </p>
     *
     * @throws Exception to jUnit
     */
    public void test_CreateVersion_fail_NameIsNull() throws Exception {
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
     * <p>
     * Failure tests for <tt>createVersion</tt> method when name is empty.
     * </p>
     *
     * @throws Exception to jUnit
     */
    public void test_CreateVersion_fail_NameIsEmpty() throws Exception {
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
     * <p>
     * Failure tests for <tt>createVersion</tt> method when versionText is null.
     * </p>
     *
     * @throws Exception to jUnit
     */
    public void test_CreateVersion_fail_VersionTextIsNull() throws Exception {
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
     * <p>
     * Failure tests for <tt>createVersion</tt> method when versionText is empty.
     * </p>
     *
     * @throws Exception to jUnit
     */
    public void test_CreateVersion_fail_VersionTextIsEmpty() throws Exception {
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
     * <p>
     * Failure tests for <tt>createVersion</tt> method when shortDescription is null.
     * </p>
     *
     * @throws Exception to jUnit
     */
    public void test_CreateVersion_fail_ShortDescriptionIsNull() throws Exception {
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
     * <p>
     * Failure tests for <tt>createVersion</tt> method when shortDescription is empty.
     * </p>
     *
     * @throws Exception to jUnit
     */
    public void test_CreateVersion_fail_ShortDescriptionIsEmpty() throws Exception {
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
     * <p>
     * Failure tests for <tt>createVersion</tt> method when detailedDescription is null.
     * </p>
     *
     * @throws Exception to jUnit
     */
    public void test_CreateVersion_fail_DetailedDescriptionIsNull() throws Exception {
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
     * <p>
     * Failure tests for <tt>createVersion</tt> method when detailedDescription is empty.
     * </p>
     *
     * @throws Exception to jUnit
     */
    public void test_CreateVersion_fail_DetailedDescriptionIsEmpty() throws Exception {
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
     * <p>
     * Failure tests for <tt>createVersion</tt> method when functionalDescription is null.
     * </p>
     *
     * @throws Exception to jUnit
     */
    public void test_CreateVersion_fail_FunctionalDescriptionIsNull() throws Exception {
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
     * <p>
     * Failure tests for <tt>createVersion</tt> method when functionalDescription is empty.
     * </p>
     *
     * @throws Exception to jUnit
     */
    public void test_CreateVersion_fail_FunctionalDescriptionIsEmpty() throws Exception {
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
     * <p>
     * Failure tests for <tt>createVersion</tt> method when rootCategory is null.
     * </p>
     *
     * @throws Exception to jUnit
     */
    public void test_CreateVersion_fail_RootCategoryIsNull() throws Exception {
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
     * <p>
     * Failure tests for <tt>createVersion</tt> method when categories is null.
     * </p>
     *
     * @throws Exception to jUnit
     */
    public void test_CreateVersion_fail_CategoriesIsNull() throws Exception {
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
     * <p>
     * Failure tests for <tt>createVersion</tt> method when categories is empty.
     * </p>
     *
     * @throws Exception to jUnit
     */
    public void test_CreateVersion_fail_CategoriesIsEmpty() throws Exception {
        AssetDTO newAsset = populateDTO(1);
        try {
            newAsset.setCategories(Collections.<Category> emptyList());
            getRemote().createVersion(newAsset);
            fail("IllegalArgumentException is expected as categories is empty.");
        } catch (EJBException e) {
            assertTrue("Invalid cause, should be IllegalArgumentException",
                e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>
     * Failure tests for <tt>createVersion</tt> method when categories contains null value.
     * </p>
     *
     * @throws Exception to jUnit
     */
    public void test_CreateVersion_fail_CategoriesContainsNull() throws Exception {
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
     * <p>
     * Failure tests for <tt>createVersion</tt> method when technologies is null.
     * </p>
     *
     * @throws Exception to jUnit
     */
    public void test_CreateVersion_fail_TechnologiesIsNull() throws Exception {
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
     * <p>
     * Failure tests for <tt>createVersion</tt> method when technologies is empty.
     * </p>
     *
     * @throws Exception to jUnit
     */
    public void test_CreateVersion_fail_TechnologiesIsEmpty() throws Exception {
        AssetDTO newAsset = populateDTO(1);
        try {
            newAsset.setTechnologies(Collections.<Technology> emptyList());
            getRemote().createVersion(newAsset);
            fail("IllegalArgumentException is expected as technologies is empty.");
        } catch (EJBException e) {
            assertTrue("Invalid cause, should be IllegalArgumentException",
                e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>
     * Failure tests for <tt>createVersion</tt> method when technologies contains null value.
     * </p>
     *
     * @throws Exception to jUnit
     */
    public void test_CreateVersion_fail_TechnologiesContainsNull() throws Exception {
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

    /**
     * <p>
     * Failure tests for <tt>updateAsset</tt> method when parameter is null.
     * </p>
     *
     * @throws Exception to jUnit
     */
    public void test_updateAsset_fail_Null() throws Exception {
        try {
            getRemote().updateAsset(null);
            fail("IllegalArgumentException is expected as parameter is null.");
        } catch (EJBException e) {
            assertTrue("Invalid cause, should be IllegalArgumentException",
                e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>
     * Failure tests for <tt>updateAsset</tt> method when id is null.
     * </p>
     *
     * @throws Exception to jUnit
     */
    public void test_updateAsset_fail_IdNull() throws Exception {
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
     * <p>
     * Failure tests for <tt>updateAsset</tt> method when name is null.
     * </p>
     *
     * @throws Exception to jUnit
     */
    public void test_updateAsset_fail_NameIsNull() throws Exception {
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
     * <p>
     * Failure tests for <tt>updateAsset</tt> method when name is empty.
     * </p>
     *
     * @throws Exception to jUnit
     */
    public void test_updateAsset_fail_NameIsEmpty() throws Exception {
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
     * <p>
     * Failure tests for <tt>updateAsset</tt> method when shortDescription is null.
     * </p>
     *
     * @throws Exception to jUnit
     */
    public void test_updateAsset_fail_ShortDescriptionIsNull() throws Exception {
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
     * <p>
     * Failure tests for <tt>updateAsset</tt> method when shortDescription is empty.
     * </p>
     *
     * @throws Exception to jUnit
     */
    public void test_updateAsset_fail_ShortDescriptionIsEmpty() throws Exception {
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
     * <p>
     * Failure tests for <tt>updateAsset</tt> method when detailedDescription is null.
     * </p>
     *
     * @throws Exception to jUnit
     */
    public void test_updateAsset_fail_DetailedDescriptionIsNull() throws Exception {
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
     * <p>
     * Failure tests for <tt>updateAsset</tt> method when detailedDescription is empty.
     * </p>
     *
     * @throws Exception to jUnit
     */
    public void test_updateAsset_fail_DetailedDescriptionIsEmpty() throws Exception {
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
     * <p>
     * Failure tests for <tt>updateAsset</tt> method when functionalDescription is null.
     * </p>
     *
     * @throws Exception to jUnit
     */
    public void test_updateAsset_fail_FunctionalDescriptionIsNull() throws Exception {
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
     * <p>
     * Failure tests for <tt>updateAsset</tt> method when functionalDescription is empty.
     * </p>
     *
     * @throws Exception to jUnit
     */
    public void test_updateAsset_fail_FunctionalDescriptionIsEmpty() throws Exception {
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
     * <p>
     * Failure tests for <tt>updateAsset</tt> method when rootCategory is null.
     * </p>
     *
     * @throws Exception to jUnit
     */
    public void test_updateAsset_fail_RootCategoryIsNull() throws Exception {
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
     * <p>
     * Failure tests for <tt>updateAsset</tt> method when categories is null.
     * </p>
     *
     * @throws Exception to jUnit
     */
    public void test_updateAsset_fail_CategoriesIsNull() throws Exception {
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
     * <p>
     * Failure tests for <tt>updateAsset</tt> method when categories is empty.
     * </p>
     *
     * @throws Exception to jUnit
     */
    public void test_updateAsset_fail_CategoriesIsEmpty() throws Exception {
        AssetDTO newAsset = createAssetDTO(1);
        try {
            newAsset.setCategories(Collections.<Category> emptyList());
            getRemote().updateAsset(newAsset);
            fail("IllegalArgumentException is expected as categories is empty.");
        } catch (EJBException e) {
            assertTrue("Invalid cause, should be IllegalArgumentException",
                e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>
     * Failure tests for <tt>updateAsset</tt> method when categories contains null value.
     * </p>
     *
     * @throws Exception to jUnit
     */
    public void test_updateAsset_fail_CategoriesContainsNull() throws Exception {
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
     * <p>
     * Failure tests for <tt>updateAsset</tt> method when technologies is null.
     * </p>
     *
     * @throws Exception to jUnit
     */
    public void test_updateAsset_fail_TechnologiesIsNull() throws Exception {
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
     * <p>
     * Failure tests for <tt>updateAsset</tt> method when technologies is empty.
     * </p>
     *
     * @throws Exception to jUnit
     */
    public void test_updateAsset_fail_TechnologiesIsEmpty() throws Exception {
        AssetDTO newAsset = createAssetDTO(1);
        try {
            newAsset.setTechnologies(Collections.<Technology> emptyList());
            newAsset.setVersionId(null);
            getRemote().updateAsset(newAsset);
        } catch (EJBException e) {
            fail("Exception is not expected as version attributes should be ignored.");
        }
    }

    /**
     * <p>
     * Failure tests for <tt>updateAsset</tt> method when technologies contains null value.
     * </p>
     *
     * @throws Exception to jUnit
     */
    public void test_updateAsset_fail_TechnologiesContainsNull() throws Exception {
        AssetDTO newAsset = createAssetDTO(1);
        try {
            newAsset.setTechnologies(Arrays.asList((Technology) null));
            newAsset.setVersionId(null);
            getRemote().updateAsset(newAsset);
        } catch (EJBException e) {
            fail("Exception is not expected as version attributes should be ignored.");
        }
    }
    
    /**
     * <p>
     * Failure tests for <tt>createAsset</tt> method when CompDocumentations is null.
     * </p>
     *
     * @throws Exception to jUnit
     * @since 1.1
     */
    public void test_CreateAsset_fail_DocumentationIsNull() throws Exception {
        AssetDTO newAsset = populateDTO(1);
        try {
            newAsset.setDocumentation(null);
            getRemote().createAsset(newAsset);
            fail("IllegalArgumentException is expected as CompDocumentations is null.");
        } catch (EJBException e) {
            assertTrue("Invalid cause, should be IllegalArgumentException",
                e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>
     * Failure tests for <tt>createAsset</tt> method when CompDocumentations contains null value.
     * </p>
     *
     * @throws Exception to jUnit
     * @since 1.1
     */
    public void test_CreateAsset_fail_DocumentationContainsNull() throws Exception {
        AssetDTO newAsset = populateDTO(1);
        try {
            newAsset.setDocumentation(Arrays.asList((CompDocumentation) null));
            getRemote().createAsset(newAsset);
            fail("IllegalArgumentException is expected as CompDocumentations contains null value.");
        } catch (EJBException e) {
            assertTrue("Invalid cause, should be IllegalArgumentException",
                e.getCause() instanceof IllegalArgumentException);
        }
    }
    
    /**
     * <p>
     * Failure tests for <tt>createVersion</tt> method when CompDocumentations is null.
     * </p>
     *
     * @throws Exception to jUnit
     * @since 1.1
     */
    public void test_CreateVersion_fail_DocumentationIsNull() throws Exception {
        AssetDTO newAsset = populateDTO(1);
        try {
            newAsset.setDocumentation(null);
            getRemote().createVersion(newAsset);
            fail("IllegalArgumentException is expected as CompDocumentations is null.");
        } catch (EJBException e) {
            assertTrue("Invalid cause, should be IllegalArgumentException",
                e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>
     * Failure tests for <tt>createVersion</tt> method when CompDocumentations contains null value.
     * </p>
     *
     * @throws Exception to jUnit
     * @since 1.1
     */
    public void test_CreateVersion_fail_DocumentationContainsNull() throws Exception {
        AssetDTO newAsset = populateDTO(1);
        try {
            newAsset.setDocumentation(Arrays.asList((CompDocumentation) null));
            getRemote().createVersion(newAsset);
            fail("IllegalArgumentException is expected as CompDocumentations contains null value.");
        } catch (EJBException e) {
            assertTrue("Invalid cause, should be IllegalArgumentException",
                e.getCause() instanceof IllegalArgumentException);
        }
    }
    
    /**
     * <p>
     * Failure tests for <tt>updateAsset(AssetDTO)</tt> method when CompDocumentations is null.
     * </p>
     *
     * @throws Exception to jUnit
     * @since 1.1
     */
    public void test_UpdateAsset_fail_DocumentationIsNull() throws Exception {
        AssetDTO newAsset = populateDTO(1);
        try {
            newAsset.setDocumentation(null);
            getRemote().updateAsset(newAsset);
            fail("IllegalArgumentException is expected as CompDocumentations is null.");
        } catch (EJBException e) {
            assertTrue("Invalid cause, should be IllegalArgumentException",
                e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>
     * Failure tests for <tt>updateAsset(AssetDTO)</tt> method when CompDocumentations contains null value.
     * </p>
     *
     * @throws Exception to jUnit
     * @since 1.1
     */
    public void test_UpdateAsset_fail_DocumentationContainsNull() throws Exception {
        AssetDTO newAsset = populateDTO(1);
        try {
            newAsset.setDocumentation(Arrays.asList((CompDocumentation) null));
            getRemote().updateAsset(newAsset);
            fail("IllegalArgumentException is expected as CompDocumentations contains null value.");
        } catch (EJBException e) {
            assertTrue("Invalid cause, should be IllegalArgumentException",
                e.getCause() instanceof IllegalArgumentException);
        }
    }
}