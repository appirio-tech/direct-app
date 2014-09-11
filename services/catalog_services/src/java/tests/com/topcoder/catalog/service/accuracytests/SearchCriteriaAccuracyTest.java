/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.catalog.service.accuracytests;

import com.topcoder.catalog.entity.Category;
import com.topcoder.catalog.service.SearchCriteria;

import junit.framework.TestCase;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * Accuracy test case for <code>SearchCriteria</code> class.
 * </p>
 *
 * @author Retunsky
 * @version 1.0
 */
public class SearchCriteriaAccuracyTest extends TestCase {
    /**
     * <p>
     * Represents user id for test.
     * </p>
     */
    private static final Long USER_ID = 1L;

    /**
     * <p>
     * Represents client id for test.
     * </p>
     */
    private static final Long CLIENT_ID = 2L;

    /**
     * <p>
     * Represents categories for test.
     * </p>
     */
    private static final List<Category> CATEGORIES = Arrays.asList(new Category());

    /**
     * <p>
     * Represents name for test.
     * </p>
     */
    private static final String NAME = "NAME";

    /**
     * <p>
     * Represents description for test.
     * </p>
     */
    private static final String DESCRIPTION = "DESCRIPTION";

    /**
     * <p>
     * Represents <code>SearchCriteria</code> instance for test.
     * </p>
     */
    private SearchCriteria searchCriteria;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     * @throws Exception
     *             to jUnit.
     */
    protected void setUp() throws Exception {
        searchCriteria = new SearchCriteria(USER_ID, CLIENT_ID, CATEGORIES, NAME, DESCRIPTION, null, null, null, null);
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>SearchCriteria()</code>.
     * </p>
     */
    public void testCtorAccuracy() {
        assertNotNull("Failed to instantiate SearchCriteria.", searchCriteria);
    }

    /**
     * <p>
     * Accuracy test for the method <code>getUserId()</code>.
     * </p>
     */
    public void testGetUserIdAccuracy() {
        assertEquals("The userId should be got correctly.", USER_ID, searchCriteria.getUserId());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getClientId()</code>.
     * </p>
     */
    public void testGetClientIdAccuracy() {
        assertEquals("The clientId should be got correctly.", CLIENT_ID, searchCriteria.getClientId());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getCategories()</code>.
     * </p>
     */
    public void testGetCategoriesAccuracy() {
        assertEquals("The categories should be got correctly.", CATEGORIES, searchCriteria.getCategories());
        assertNotSame("There should be shallow copy of the list.", CATEGORIES, searchCriteria.getCategories());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getName()</code>.
     * </p>
     */
    public void testGetNameAccuracy() {
        assertEquals("The name should be got correctly.", NAME, searchCriteria.getName());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getDescription()</code>.
     * </p>
     */
    public void testGetDescriptionAccuracy() {
        assertEquals("The description should be got correctly.", DESCRIPTION, searchCriteria.getDescription());
    }
}
