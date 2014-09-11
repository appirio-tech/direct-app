/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.catalog.service.failuretests;

import java.util.ArrayList;
import java.util.Arrays;

import junit.framework.TestCase;

import com.topcoder.catalog.entity.Category;
import com.topcoder.catalog.service.SearchCriteria;

/**
 * <p>
 * Failure tests for SearchCriteria.
 * </p>
 *
 * @author kaqi072821, hfx
 * @version 1.1
 */
public class SearchCriteriaFailureTest extends TestCase {

    /**
     * <p>
     * Failure test for the constructor when all the parameters are null. IllegalArgumentException expected.
     * </p>
     */
    public void test_ctor_fail_AllParametersNull() {
        try {
            new SearchCriteria(null, null, null, null, null, null, null, null, null);
            fail("IllegalArgumentException is expected when all parameters are null");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test for the constructor when name is empty, others null. IllegalArgumentException expected.
     * </p>
     */
    public void test_ctor_fail_NameEmpty() {
        try {
            new SearchCriteria(null, null, null, " ", null, null, null, null, null);
            fail("IllegalArgumentException is expected  when name is empty, others null");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test for the constructor when description is empty, others null. IllegalArgumentException expected.
     * </p>
     */
    public void test_ctor_fail_DescriptionEmpty() {
        try {
            new SearchCriteria(null, null, null, null, " ", null, null, null, null);
            fail("IllegalArgumentException is expected as all parameters are null, and the description is empty");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test for the constructor when categories is empty. IllegalArgumentException expected.
     * </p>
     */
    public void test_ctor_fail_CategoriesEmpty() {
        try {
            new SearchCriteria(1L, null, new ArrayList<Category>(), null, null, null, null, null, null);
            fail("IllegalArgumentException is expected as categories cannot be empty");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test for the constructor when categories contains null. IllegalArgumentException expected.
     * </p>
     */
    public void test_ctor_fail_CategoriesContainsNull() {
        try {
            new SearchCriteria(1L, null, Arrays.asList((Category) null), null, null, null, null, null, null);
            fail("IllegalArgumentException expected as categories cannot contain null");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

}