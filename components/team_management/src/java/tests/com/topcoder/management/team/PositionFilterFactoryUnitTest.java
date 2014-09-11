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
 * UnitTest for PositionFilterFactory class.
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class PositionFilterFactoryUnitTest extends TestCase {
    /**
     * <p>
     * Aggregates all tests in this class.
     * </p>
     * @return Test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(PositionFilterFactoryUnitTest.class);
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
        EqualToFilter filter = (EqualToFilter) PositionFilterFactory.createNameFilter("my_value");
        assertEquals("filter name mismatch", PositionFilterFactory.NAME, filter.getName());
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
            PositionFilterFactory.createNameFilter(null);
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
            PositionFilterFactory.createNameFilter("  ");
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
        EqualToFilter filter = (EqualToFilter) PositionFilterFactory.createDescriptionFilter("my_value");
        assertEquals("filter name mismatch", PositionFilterFactory.DESCRIPTION, filter.getName());
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
        EqualToFilter filter = (EqualToFilter) PositionFilterFactory.createDescriptionFilter("");
        assertEquals("filter name mismatch", PositionFilterFactory.DESCRIPTION, filter.getName());
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
        NullFilter filter = (NullFilter) PositionFilterFactory.createDescriptionFilter(null);
        assertEquals("filter name mismatch", PositionFilterFactory.DESCRIPTION, filter.getName());
    }

    /**
     * Accuracy test of <code>createResourceIdFilter(long resourceId)</code> method.
     * <p>
     * Create this filter with a given resourceId.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testCreateResourceIdFilterAccuracy1() throws Exception {
        EqualToFilter filter = (EqualToFilter) PositionFilterFactory.createResourceIdFilter(7);
        assertEquals("filter name mismatch", PositionFilterFactory.RESOURCE_ID, filter.getName());
        assertEquals("filter value mismatch", new Long(7), filter.getValue());
    }

    /**
     * Accuracy test of <code>createResourceIdFilter(long resourceId)</code> method.
     * <p>
     * Create this filter with a given zero resourceId.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testCreateResourceIdFilterAccuracy2() throws Exception {
        EqualToFilter filter = (EqualToFilter) PositionFilterFactory.createResourceIdFilter(0);
        assertEquals("filter name mismatch", PositionFilterFactory.RESOURCE_ID, filter.getName());
        assertEquals("filter value mismatch", new Long(0), filter.getValue());
    }

    /**
     * Failure test of <code>createResourceIdFilter(long resourceId)</code> method.
     * <p>
     * Call this method with negative resourceId.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testCreateResourceIdFilterFailure() throws Exception {
        try {
            PositionFilterFactory.createResourceIdFilter(-1);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>createTeamIdFilter(long teamId)</code> method.
     * <p>
     * Create this filter with a given teamId.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testCreateTeamIdFilterAccuracy1() throws Exception {
        EqualToFilter filter = (EqualToFilter) PositionFilterFactory.createTeamIdFilter(7);
        assertEquals("filter name mismatch", PositionFilterFactory.TEAM_ID, filter.getName());
        assertEquals("filter value mismatch", new Long(7), filter.getValue());
    }

    /**
     * Accuracy test of <code>createTeamIdFilter(long teamId)</code> method.
     * <p>
     * Create this filter with a zero teamId.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testCreateTeamIdFilterAccuracy2() throws Exception {
        EqualToFilter filter = (EqualToFilter) PositionFilterFactory.createTeamIdFilter(0);
        assertEquals("filter name mismatch", PositionFilterFactory.TEAM_ID, filter.getName());
        assertEquals("filter value mismatch", new Long(0), filter.getValue());
    }

    /**
     * Failure test of <code>createTeamIdFilter(long teamId)</code> method.
     * <p>
     * Call this method with negative teamId.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testCreateTeamIdFilterFailure() throws Exception {
        try {
            PositionFilterFactory.createTeamIdFilter(-1);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
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
        EqualToFilter filter = (EqualToFilter) PositionFilterFactory.createProjectIdFilter(7);
        assertEquals("filter name mismatch", PositionFilterFactory.PROJECT_ID, filter.getName());
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
        EqualToFilter filter = (EqualToFilter) PositionFilterFactory.createProjectIdFilter(0);
        assertEquals("filter name mismatch", PositionFilterFactory.PROJECT_ID, filter.getName());
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
            PositionFilterFactory.createProjectIdFilter(-1);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>createPublishedFilter(boolean published)</code> method.
     * <p>
     * Call this method with true.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testCreatePublishedFilterAccuracy1() throws Exception {
        EqualToFilter filter = (EqualToFilter) PositionFilterFactory.createPublishedFilter(true);
        assertEquals("filter name mismatch", PositionFilterFactory.PUBLISHED, filter.getName());
        assertEquals("filter value mismatch", UtilityFilterFactory.BOOLEAN_TRUE, filter.getValue());
    }

    /**
     * Accuracy test of <code>createPublishedFilter(boolean published)</code> method.
     * <p>
     * Call this method with true.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testCreatePublishedFilterAccuracy2() throws Exception {
        EqualToFilter filter = (EqualToFilter) PositionFilterFactory.createPublishedFilter(false);
        assertEquals("filter name mismatch", PositionFilterFactory.PUBLISHED, filter.getName());
        assertEquals("filter value mismatch", UtilityFilterFactory.BOOLEAN_FALSE, filter.getValue());
    }

    /**
     * Accuracy test of <code>createFilledFilter(boolean published)</code> method.
     * <p>
     * Call this method with true.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testCreateFilledFilterAccuracy1() throws Exception {
        EqualToFilter filter = (EqualToFilter) PositionFilterFactory.createFilledFilter(true);
        assertEquals("filter name mismatch", PositionFilterFactory.FILLED, filter.getName());
        assertEquals("filter value mismatch", UtilityFilterFactory.BOOLEAN_TRUE, filter.getValue());
    }

    /**
     * Accuracy test of <code>createFilledFilter(boolean published)</code> method.
     * <p>
     * Call this method with true.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testCreateFilledFilterAccuracy2() throws Exception {
        EqualToFilter filter = (EqualToFilter) PositionFilterFactory.createFilledFilter(false);
        assertEquals("filter name mismatch", PositionFilterFactory.FILLED, filter.getName());
        assertEquals("filter value mismatch", UtilityFilterFactory.BOOLEAN_FALSE, filter.getValue());
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
        EqualToFilter filter = (EqualToFilter) PositionFilterFactory.createCustomPropertyNameFilter("my_name");
        assertEquals("filter name mismatch", PositionFilterFactory.CUSTOM_PROPERTY_NAME, filter.getName());
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
            PositionFilterFactory.createCustomPropertyNameFilter(null);
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
            PositionFilterFactory.createCustomPropertyNameFilter("  ");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }
}
