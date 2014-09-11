/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.team;

import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.NullFilter;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * UnitTest for TeamFilterFactory class.
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class TeamFilterFactoryUnitTest extends TestCase {
    /**
     * <p>
     * Aggregates all tests in this class.
     * </p>
     * @return Test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(TeamFilterFactoryUnitTest.class);
    }

    /**
     * Accuracy test of <code>createNameFilter(String name)</code> method.
     * <p>
     * Create a sample name filter and check the correctness.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testCreateNameFilterAccuracy() throws Exception {
        EqualToFilter filter = (EqualToFilter) TeamFilterFactory.createNameFilter("my_value");
        assertEquals("filter name mismatch", TeamFilterFactory.NAME, filter.getName());
        assertEquals("filter value mismatch", "my_value", filter.getValue());
    }

    /**
     * Failure test of <code>createNameFilter(String name)</code> method.
     * <p>
     * Call this method with null name.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testCreateNameFilterFailure1() throws Exception {
        try {
            TeamFilterFactory.createNameFilter(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>createNameFilter(String name)</code> method.
     * <p>
     * Call this method with trimmed empty name.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testCreateNameFilterFailure2() throws Exception {
        try {
            TeamFilterFactory.createNameFilter("  ");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>createDescriptionFilter(String description)</code> method.
     * <p>
     * Call this method with a simple description.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testCreateDescriptionFilterAccuracy1() throws Exception {
        EqualToFilter filter = (EqualToFilter) TeamFilterFactory.createDescriptionFilter("my_value");
        assertEquals("filter name mismatch", TeamFilterFactory.DESCRIPTION, filter.getName());
        assertEquals("filter value mismatch", "my_value", filter.getValue());
    }

    /**
     * Accuracy test of <code>createDescriptionFilter(String description)</code> method.
     * <p>
     * Call this method with an empty description.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testCreateDescriptionFilterAccuracy2() throws Exception {
        EqualToFilter filter = (EqualToFilter) TeamFilterFactory.createDescriptionFilter("");
        assertEquals("filter name mismatch", TeamFilterFactory.DESCRIPTION, filter.getName());
        assertEquals("filter value mismatch", "", filter.getValue());
    }

    /**
     * Accuracy test of <code>createDescriptionFilter(String description)</code> method.
     * <p>
     * Call this method with a null description. NullFiter is expected.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testCreateDescriptionFilterAccuracy3() throws Exception {
        NullFilter filter = (NullFilter) TeamFilterFactory.createDescriptionFilter(null);
        assertEquals("filter name mismatch", TeamFilterFactory.DESCRIPTION, filter.getName());
    }

    /**
     * Accuracy test of <code>createProjectIdFilter(long projectId)</code> method.
     * <p>
     * Create this filter with a given projectId.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testCreateProjectIdFilterAccuracy1() throws Exception {
        EqualToFilter filter = (EqualToFilter) TeamFilterFactory.createProjectIdFilter(7);
        assertEquals("filter name mismatch", TeamFilterFactory.PROJECT_ID, filter.getName());
        assertEquals("filter value mismatch", new Long(7), filter.getValue());
    }

    /**
     * Accuracy test of <code>createProjectIdFilter(long projectId)</code> method.
     * <p>
     * Create this filter with a given zero projectId.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testCreateProjectIdFilterAccuracy2() throws Exception {
        EqualToFilter filter = (EqualToFilter) TeamFilterFactory.createProjectIdFilter(0);
        assertEquals("filter name mismatch", TeamFilterFactory.PROJECT_ID, filter.getName());
        assertEquals("filter value mismatch", new Long(0), filter.getValue());
    }

    /**
     * Failure test of <code>createProjectIdFilter(long projectId)</code> method.
     * <p>
     * Call this method with negative projectId.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testCreateProjectIdFilterFailure() throws Exception {
        try {
            TeamFilterFactory.createProjectIdFilter(-1);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>createFinalizedFilter(boolean finalized)</code> method.
     * <p>
     * Call this method with true.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testCreateFinalizedFilterAccuracy1() throws Exception {
        EqualToFilter filter = (EqualToFilter) TeamFilterFactory.createFinalizedFilter(true);
        assertEquals("filter name mismatch", TeamFilterFactory.FINALIZED, filter.getName());
        assertEquals("filter value mismatch", UtilityFilterFactory.BOOLEAN_TRUE, filter.getValue());
    }

    /**
     * Accuracy test of <code>createFinalizedFilter(boolean finalized)</code> method.
     * <p>
     * Call this method with true.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testCreateFinalizedFilterAccuracy2() throws Exception {
        EqualToFilter filter = (EqualToFilter) TeamFilterFactory.createFinalizedFilter(false);
        assertEquals("filter name mismatch", TeamFilterFactory.FINALIZED, filter.getName());
        assertEquals("filter value mismatch", UtilityFilterFactory.BOOLEAN_FALSE, filter.getValue());
    }

    /**
     * Accuracy test of <code>createCaptainResourceIdFilter(long captainResourceId)</code> method.
     * <p>
     * Create this filter with a given captainResourceId.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testCreateCaptainResourceIdFilterAccuracy1() throws Exception {
        EqualToFilter filter = (EqualToFilter) TeamFilterFactory.createCaptainResourceIdFilter(7);
        assertEquals("filter name mismatch", TeamFilterFactory.CAPTAIN_RESOURCE_ID, filter.getName());
        assertEquals("filter value mismatch", new Long(7), filter.getValue());
    }

    /**
     * Accuracy test of <code>createCaptainResourceIdFilter(long captainResourceId)</code> method.
     * <p>
     * Create this filter with a given zero captainResourceId.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testCreateCaptainResourceIdFilterAccuracy2() throws Exception {
        EqualToFilter filter = (EqualToFilter) TeamFilterFactory.createCaptainResourceIdFilter(0);
        assertEquals("filter name mismatch", TeamFilterFactory.CAPTAIN_RESOURCE_ID, filter.getName());
        assertEquals("filter value mismatch", new Long(0), filter.getValue());
    }

    /**
     * Failure test of <code>createCaptainResourceIdFilter(long captainResourceId)</code> method.
     * <p>
     * Call this method with negative captainResourceId.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testCreateCaptainResourceIdFilterFailure() throws Exception {
        try {
            TeamFilterFactory.createCaptainResourceIdFilter(-1);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>createCustomPropertyNameFilter(String name)</code> method.
     * <p>
     * Create a filter with a given name.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testCreateCustomPropertyNameFilterAccuracy() throws Exception {
        EqualToFilter filter = (EqualToFilter) TeamFilterFactory.createCustomPropertyNameFilter("my_name");
        assertEquals("filter name mismatch", TeamFilterFactory.CUSTOM_PROPERTY_NAME, filter.getName());
        assertEquals("filter value mismatch", "my_name", filter.getValue());
    }

    /**
     * Failure test of <code>createCustomPropertyNameFilter(String name)</code> method.
     * <p>
     * Call this method with null name.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testCreateCustomPropertyNameFilterFailure1() throws Exception {
        try {
            TeamFilterFactory.createCustomPropertyNameFilter(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>createCustomPropertyNameFilter(String name)</code> method.
     * <p>
     * Call this method with trimmed empty name.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testCreateCustomPropertyNameFilterFailure2() throws Exception {
        try {
            TeamFilterFactory.createCustomPropertyNameFilter("  ");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }
}
