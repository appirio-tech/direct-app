/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.dao.ejb3;

import java.util.List;

import junit.framework.TestCase;

import com.topcoder.clients.dao.DAOConfigurationException;
import com.topcoder.clients.dao.DAOException;
import com.topcoder.clients.model.Project;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;

/**
 * <p>
 * Test class: <code>SearchByFilterUtilityImpl</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.1
 */
public class SearchByFilterUtilityImplTest extends TestCase {
    /**
     * <p>
     * An instance of <code>SearchByFilterUtilityImpl</code> which is tested.
     * </p>
     */
    private SearchByFilterUtilityImpl<Project, Long> target = null;

    /**
     * <p>
     * The searchBundle manager namespace used in tests.
     * </p>
     */
    private String searchBundleManagerNamespace = TestHelper.NAMESPACE;

    /**
     * <p>
     * The searchBundle name used in tests.
     * </p>
     */
    private String searchBundleName = "TestSearchBundle";

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
        TestHelper.addConfig("config1.xml");

        target = new SearchByFilterUtilityImpl<Project, Long>(
                searchBundleManagerNamespace, searchBundleName);
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
     * <p>
     * Inheritance test.
     * </p>
     * <p>
     * Verifies <code>SearchByFilterUtilityImpl</code> implements
     * <code>SearchByFilterUtility</code>.
     * </p>
     */
    public void testInheritance() {
        assertTrue(
                "SearchByFilterUtilityImpl does not implements SearchByFilterUtility.",
                target instanceof SearchByFilterUtility<?, ?>);
    }

    /**
     * <p>
     * Tests the
     * <code>com.topcoder.clients.dao.ejb3.SearchByFilterUtilityImpl(String, String)</code>
     * for proper behavior. Verifies that searchBundle should be set.
     * </p>
     *
     * @throws Exception
     *                 to JUnit
     */
    public void testConstructor_String_String() throws Exception {
        assertNotNull(
                "The 'searchBundle' field should be set and not be null.",
                TestHelper.getPrivateField(SearchByFilterUtilityImpl.class,
                        target, "searchBundle"));
    }

    /**
     * <p>
     * Tests the
     * <code>com.topcoder.clients.dao.ejb3.SearchByFilterUtilityImpl(String, String)</code>
     * for proper behavior. IllegalArgumentException if
     * searchBundleManagerNamespace or searchBundleName is null or empty string.
     * </p>
     *
     * @throws Exception
     *                 to JUnit
     */
    public void testConstructor_String_String_failure_1() throws Exception {
        try {
            target = new SearchByFilterUtilityImpl<Project, Long>(null,
                    searchBundleName);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Tests the
     * <code>com.topcoder.clients.dao.ejb3.SearchByFilterUtilityImpl(String, String)</code>
     * for proper behavior. IllegalArgumentException if
     * searchBundleManagerNamespace or searchBundleName is null or empty string.
     * </p>
     *
     * @throws Exception
     *                 to JUnit
     */
    public void testConstructor_String_String_failure_2() throws Exception {
        try {
            target = new SearchByFilterUtilityImpl<Project, Long>("   ",
                    searchBundleName);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Tests the
     * <code>com.topcoder.clients.dao.ejb3.SearchByFilterUtilityImpl(String, String)</code>
     * for proper behavior. IllegalArgumentException if
     * searchBundleManagerNamespace or searchBundleName is null or empty string.
     * </p>
     *
     * @throws Exception
     *                 to JUnit
     */
    public void testConstructor_String_String_failure_3() throws Exception {
        try {
            target = new SearchByFilterUtilityImpl<Project, Long>("\t",
                    searchBundleName);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Tests the
     * <code>com.topcoder.clients.dao.ejb3.SearchByFilterUtilityImpl(String, String)</code>
     * for proper behavior. IllegalArgumentException if
     * searchBundleManagerNamespace or searchBundleName is null or empty string.
     * </p>
     *
     * @throws Exception
     *                 to JUnit
     */
    public void testConstructor_String_String_failure_4() throws Exception {
        try {
            target = new SearchByFilterUtilityImpl<Project, Long>("\n",
                    searchBundleName);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Tests the
     * <code>com.topcoder.clients.dao.ejb3.SearchByFilterUtilityImpl(String, String)</code>
     * for proper behavior. IllegalArgumentException if
     * searchBundleManagerNamespace or searchBundleName is null or empty string.
     * </p>
     *
     * @throws Exception
     *                 to JUnit
     */
    public void testConstructor_String_String_failure_5() throws Exception {
        try {
            target = new SearchByFilterUtilityImpl<Project, Long>(
                    searchBundleManagerNamespace, null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Tests the
     * <code>com.topcoder.clients.dao.ejb3.SearchByFilterUtilityImpl(String, String)</code>
     * for proper behavior. IllegalArgumentException if
     * searchBundleManagerNamespace or searchBundleName is null or empty string.
     * </p>
     *
     * @throws Exception
     *                 to JUnit
     */
    public void testConstructor_String_String_failure_6() throws Exception {
        try {
            target = new SearchByFilterUtilityImpl<Project, Long>(
                    searchBundleManagerNamespace, "   ");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Tests the
     * <code>com.topcoder.clients.dao.ejb3.SearchByFilterUtilityImpl(String, String)</code>
     * for proper behavior. IllegalArgumentException if
     * searchBundleManagerNamespace or searchBundleName is null or empty string.
     * </p>
     *
     * @throws Exception
     *                 to JUnit
     */
    public void testConstructor_String_String_failure_7() throws Exception {
        try {
            target = new SearchByFilterUtilityImpl<Project, Long>(
                    searchBundleManagerNamespace, "\t");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Tests the
     * <code>com.topcoder.clients.dao.ejb3.SearchByFilterUtilityImpl(String, String)</code>
     * for proper behavior. IllegalArgumentException if
     * searchBundleManagerNamespace or searchBundleName is null or empty string.
     * </p>
     *
     * @throws Exception
     *                 to JUnit
     */
    public void testConstructor_String_String_failure_8() throws Exception {
        try {
            target = new SearchByFilterUtilityImpl<Project, Long>(
                    searchBundleManagerNamespace, "\n");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Tests the
     * <code>com.topcoder.clients.dao.ejb3.SearchByFilterUtilityImpl(String, String)</code>
     * for proper behavior. DAOConfigurationException if this utility can not be
     * configured. (if SearcBundle could not be initialized).
     * </p>
     *
     * @throws Exception
     *                 to JUnit
     */
    public void testConstructor_String_String_failure_9() throws Exception {
        try {
            target = new SearchByFilterUtilityImpl<Project, Long>(
                    searchBundleManagerNamespace + "abc", searchBundleName);
            fail("DAOConfigurationException expected.");
        } catch (DAOConfigurationException e) {
            // ok
        }
    }

    /**
     * <p>
     * Tests the <code>search(Filter)</code> for proper behavior. Verifies
     * that result is returned correctly.
     * </p>
     *
     * @throws Exception
     *                 to JUnit
     */
    public void testMethodSearch_Filter() throws Exception {
        // do search
        EqualToFilter equalToFilter = new EqualToFilter("projectStatus", 1);
        List<Project> res = target.search(equalToFilter);
        assertEquals("The number of search result", 0, res.size());
    }

    /**
     * <p>
     * Tests the <code>search(Filter)</code> for proper behavior.
     * IllegalArgumentException if filter is null.
     * </p>
     *
     * @throws Exception
     *                 to JUnit
     */
    public void testMethodSearch_Filter_failure_1() throws Exception {
        try {
            target.search(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Tests the <code>search(Filter)</code> for proper behavior. DAOException
     * if any error occurs while performing this operation.
     * </p>
     *
     * @throws Exception
     *                 to JUnit
     */
    public void testMethodSearch_Filter_failure_2() throws Exception {
        try {
            Filter filter = new EqualToFilter("invalid", 12);
            target.search(filter);
            fail("DAOException expected.");
        } catch (DAOException e) {
            // ok
        }
    }
}
