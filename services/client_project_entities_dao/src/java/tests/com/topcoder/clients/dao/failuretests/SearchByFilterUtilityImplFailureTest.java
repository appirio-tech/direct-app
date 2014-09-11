/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.dao.failuretests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.clients.dao.DAOConfigurationException;
import com.topcoder.clients.dao.DAOException;
import com.topcoder.clients.dao.ejb3.SearchByFilterUtilityImpl;
import com.topcoder.clients.model.Project;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;

/**
 * Failure test for SearchByFilterUtilityImpl class.
 *
 * @author AK_47
 * @version 1.0
 */
public class SearchByFilterUtilityImplFailureTest extends TestCase {
    /**
     * <p>
     * An instance of <code>SearchByFilterUtilityImpl</code> which is tested.
     * </p>
     */
    private SearchByFilterUtilityImpl filter = null;

    /**
     * <p>
     * The searchBundle manager namespace used in tests.
     * </p>
     */
    private String searchBundleManagerNamespace = "com.topcoder.search.builder";

    /**
     * <p>
     * The searchBundle name used in tests.
     * </p>
     */
    private String searchBundleName = "HibernateSearchBundle_Project";

    /**
     * Aggregates all tests in this class.
     *
     * @return Test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(SearchByFilterUtilityImplFailureTest.class);
    }
    /**
     * <p>
     * setUp() routine.
     * </p>
     *
     * @throws Exception
     *                 to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();
        TestHelper.clearConfig();
        TestHelper.addConfig("config.xml");
        TestHelper.addConfig("hibernateSearchStrategyConfig.xml");
    }

    /**
     * <p>
     * tearDown() routine.
     * </p>
     *
     * @throws Exception
     *                 to JUnit
     */
    protected void tearDown() throws Exception {
        TestHelper.clearConfig();
        super.tearDown();
    }

    /**
     * Failure test of <code>SearchByFilterUtilityImpl(String, String)</code> method.
     *
     * <p>
     * searchBundleManagerNamespace is null.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testConstructor_1() throws Exception {
        try {
            filter = new SearchByFilterUtilityImpl<Project, Long>(null, searchBundleName);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>SearchByFilterUtilityImpl(String, String)</code> method.
     *
     * <p>
     * searchBundleManagerNamespace is empty.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testConstructor_2() throws Exception {
        try {
            filter = new SearchByFilterUtilityImpl<Project, Long>("   ", searchBundleName);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>SearchByFilterUtilityImpl(String, String)</code> method.
     *
     * <p>
     * searchBundleName is null.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testConstructor_3() throws Exception {
        try {
            filter = new SearchByFilterUtilityImpl<Project, Long>(searchBundleManagerNamespace, null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>SearchByFilterUtilityImpl(String, String)</code> method.
     *
     * <p>
     * searchBundleName is empty.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testConstructor_4() throws Exception {
        try {
            filter = new SearchByFilterUtilityImpl<Project, Long>(searchBundleManagerNamespace, "  ");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>SearchByFilterUtilityImpl(String, String)</code> method.
     *
     * <p>
     * searchBundleManagerNamespace is invalid.
     * </p>
     *
     * <p>
     * Expect DAOConfigurationException.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testConstructor_5() throws Exception {
        try {
            filter = new SearchByFilterUtilityImpl<Project, Long>("invalid", searchBundleName);
            fail("Expect DAOConfigurationException.");
        } catch (DAOConfigurationException e) {
            // expect
        }
    }
    /**
     * Failure test of <code>search(Filter)</code> method.
     *
     * <p>
     * Filter is null.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testSearch_Null_Filter() throws Exception {
        try {
            filter = new SearchByFilterUtilityImpl<Project, Long>(
                searchBundleManagerNamespace, searchBundleName);
            filter.search(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>search(Filter)</code> method.
     *
     * <p>
     * Filter is invalid.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testSearch_InvalidFilter() throws Exception {
        try {
            filter = new SearchByFilterUtilityImpl<Project, Long>(
                searchBundleManagerNamespace, searchBundleName);

            Filter filter1 = new EqualToFilter("1111", 1);
            filter.search(filter1);
            fail("DAOException expected.");
        } catch (DAOException e) {
            // expect
        }
    }
}
