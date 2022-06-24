/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.scorecard.failuretests;

import com.topcoder.management.scorecard.ScorecardSearchBundle;

import com.topcoder.search.builder.filter.Filter;
import com.topcoder.search.builder.filter.InFilter;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;


/**
 * Tests for ScorecardSearchBundle class.
 *
 * @author qiucx0161
 * @version 1.0
 */
public class TestScorecardSearchBundle extends TestCase {
    /** A Filter used for test. */
    private Filter filter = null;

    /**
     * Setup the test environment.
     *
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();

        List values = new ArrayList();
        values.add(new Long(123));
        filter = new InFilter("filter", values);
    }

    /**
     * Setup the test environment.
     *
     * @throws Exception to JUnit.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Tests buildTypeIdEqualFilter(long typeId) method with zero long typeId,
     * IllegalArgumentException should be thrown.
     */
    public void testBuildTypeIdEqualFilter_ZeroTypeId() {
        try {
            ScorecardSearchBundle.buildTypeIdEqualFilter(0);
            fail("testBuildTypeIdEqualFilter_ZeroTypeId is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknow exception occurs in testBuildTypeIdEqualFilter_ZeroTypeId.");
        }
    }

    /**
     * Tests buildTypeIdEqualFilter(long typeId) method with negative long typeId,
     * IllegalArgumentException should be thrown.
     */
    public void testBuildTypeIdEqualFilter_NegativeTypeId() {
        try {
            ScorecardSearchBundle.buildTypeIdEqualFilter(-1);
            fail("testBuildTypeIdEqualFilter_NegativeTypeId is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknow exception occurs in testBuildTypeIdEqualFilter_NegativeTypeId.");
        }
    }

    /**
     * Tests buildTypeIdInFilter(List typeIds) method with null List typeIds,
     * IllegalArgumentException should be thrown.
     */
    public void testBuildTypeIdInFilter_NullTypeIds() {
        try {
            ScorecardSearchBundle.buildTypeIdInFilter(null);
            fail("testBuildTypeIdInFilter_NullTypeIds is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknow exception occurs in testBuildTypeIdInFilter_NullTypeIds.");
        }
    }

    /**
     * Tests buildTypeIdInFilter(List typeIds) method with empty List typeIds,
     * IllegalArgumentException should be thrown.
     */
    public void testBuildTypeIdInFilter_EmptyTypeIds() {
        try {
            ScorecardSearchBundle.buildTypeIdInFilter(new ArrayList());
            fail("testBuildTypeIdInFilter_EmptyTypeIds is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknow exception occurs in testBuildTypeIdInFilter_EmptyTypeIds.");
        }
    }

    /**
     * Tests buildTypeIdInFilter(List typeIds) method with invalid type element in List
     * typeIds, IllegalArgumentException should be thrown.
     */
    public void testBuildTypeIdInFilter_InvalidTypeElementInTypeIds() {
        List typeIds = new ArrayList();
        typeIds.add(new Object());

        try {
            ScorecardSearchBundle.buildTypeIdInFilter(typeIds);
            fail("testBuildTypeIdInFilter_EmptyTypeIds is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknow exception occurs in testBuildTypeIdInFilter_EmptyTypeIds.");
        }
    }

    /**
     * Tests buildTypeIdInFilter(List typeIds) method with zero element in List typeIds,
     * IllegalArgumentException should be thrown.
     */
    public void testBuildTypeIdInFilter_ZeroElementInTypeIds() {
        List typeIds = new ArrayList();
        typeIds.add(new Long(0));

        try {
            ScorecardSearchBundle.buildTypeIdInFilter(typeIds);
            fail("testBuildTypeIdInFilter_ZeroElementInTypeIds is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail(
                "Unknow exception occurs in testBuildTypeIdInFilter_ZeroElementInTypeIds.");
        }
    }

    /**
     * Tests buildTypeIdInFilter(List typeIds) method with zero element in List typeIds,
     * IllegalArgumentException should be thrown.
     */
    public void testBuildTypeIdInFilter_NullElementInTypeIds() {
        List typeIds = new ArrayList();
        typeIds.add(null);

        try {
            ScorecardSearchBundle.buildTypeIdInFilter(typeIds);
            fail("testBuildTypeIdInFilter_NullElementInTypeIds is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail(
                "Unknow exception occurs in testBuildTypeIdInFilter_NullElementInTypeIds.");
        }
    }

    /**
     * Tests buildTypeIdInFilter(List typeIds) method with negative element in List
     * typeIds, IllegalArgumentException should be thrown.
     */
    public void testBuildTypeIdInFilter_NegativeElementInTypeIds() {
        List typeIds = new ArrayList();
        typeIds.add(new Long(-1));

        try {
            ScorecardSearchBundle.buildTypeIdInFilter(typeIds);
            fail("testBuildTypeIdInFilter_NegativeElementInTypeIds is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail(
                "Unknow exception occurs in testBuildTypeIdInFilter_NegativeElementInTypeIds.");
        }
    }

    /**
     * Tests buildTypeNameEqualFilter(String typeName) method with null String typeName,
     * IllegalArgumentException should be thrown.
     */
    public void testBuildTypeNameEqualFilter_NullTypeName() {
        try {
            ScorecardSearchBundle.buildTypeNameEqualFilter(null);
            fail("testBuildTypeNameEqualFilter_NullTypeName is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknow exception occurs in testBuildTypeNameEqualFilter_NullTypeName.");
        }
    }

    /**
     * Tests buildTypeNameEqualFilter(String typeName) method with empty String typeName,
     * IllegalArgumentException should be thrown.
     */
    public void testBuildTypeNameEqualFilter_EmptyTypeName() {
        try {
            ScorecardSearchBundle.buildTypeNameEqualFilter(" ");
            fail("testBuildTypeNameEqualFilter_EmptyTypeName is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknow exception occurs in testBuildTypeNameEqualFilter_EmptyTypeName.");
        }
    }

    /**
     * Tests buildTypeNameInFilter(List typeNames) method with null List typeNames,
     * IllegalArgumentException should be thrown.
     */
    public void testBuildTypeNameInFilter_NullTypeNames() {
        try {
            ScorecardSearchBundle.buildTypeNameInFilter(null);
            fail("testBuildTypeNameInFilter_NullTypeNames is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknow exception occurs in testBuildTypeNameInFilter_NullTypeNames.");
        }
    }

    /**
     * Tests buildTypeNameInFilter(List typeNames) method with null in List typeNames,
     * IllegalArgumentException should be thrown.
     */
    public void testBuildTypeNameInFilter_NullInTypeNames() {
        try {
            List typeNames = new ArrayList();
            typeNames.add(null);
            ScorecardSearchBundle.buildTypeNameInFilter(typeNames);
            fail("testBuildTypeNameInFilter_NullInTypeNames is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknow exception occurs in testBuildTypeNameInFilter_NullInTypeNames.");
        }
    }

    /**
     * Tests buildTypeNameInFilter(List typeNames) method with empty in List typeNames,
     * IllegalArgumentException should be thrown.
     */
    public void testBuildTypeNameInFilter_EmptyInTypeNames() {
        List typeNames = new ArrayList();
        typeNames.add(" ");

        try {
            ScorecardSearchBundle.buildTypeNameInFilter(typeNames);
            fail("testBuildTypeNameEqualFilter_EmptyInTypeNames is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail(
                "Unknow exception occurs in testBuildTypeNameEqualFilter_EmptyInTypeNames.");
        }
    }

    /**
     * Tests buildStatusIdEqualFilter(long statusId) method with zero long statusId,
     * IllegalArgumentException should be thrown.
     */
    public void testBuildStatusIdEqualFilter_ZeroStatusId() {
        try {
            ScorecardSearchBundle.buildStatusIdEqualFilter(0);
            fail("testBuildStatusIdEqualFilter_ZeroStatusId is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknow exception occurs in testBuildStatusIdEqualFilter_ZeroStatusId.");
        }
    }

    /**
     * Tests buildStatusIdEqualFilter(long statusId) method with negative long statusId,
     * IllegalArgumentException should be thrown.
     */
    public void testBuildStatusIdEqualFilter_NegativeStatusId() {
        try {
            ScorecardSearchBundle.buildStatusIdEqualFilter(-1);
            fail("testBuildStatusIdEqualFilter_NegativeStatusId is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail(
                "Unknow exception occurs in testBuildStatusIdEqualFilter_NegativeStatusId.");
        }
    }

    /**
     * Tests buildStatusIdInFilter(List statusIds) method with null List statusIds,
     * IllegalArgumentException should be thrown.
     */
    public void testBuildStatusIdInFilter_NullStatusIds() {
        try {
            ScorecardSearchBundle.buildStatusIdInFilter(null);
            fail("testBuildStatusIdInFilter_NullStatusIds is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknow exception occurs in testBuildStatusIdInFilter_NullStatusIds.");
        }
    }

    /**
     * Tests buildStatusIdInFilter(List statusIds) method with empty List statusIds,
     * IllegalArgumentException should be thrown.
     */
    public void testBuildStatusIdInFilter_EmptyStatusIds() {
        List statusIds = new ArrayList();

        try {
            ScorecardSearchBundle.buildStatusIdInFilter(statusIds);
            fail("testBuildStatusIdInFilter_EmptyStatusIds is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknow exception occurs in testBuildStatusIdInFilter_EmptyStatusIds.");
        }
    }

    /**
     * Tests buildStatusIdInFilter(List statusIds) method with zero in List statusIds,
     * IllegalArgumentException should be thrown.
     */
    public void testBuildStatusIdInFilter_ZeroInStatusIds() {
        List statusIds = new ArrayList();
        statusIds.add(new Long(0));

        try {
            ScorecardSearchBundle.buildStatusIdInFilter(statusIds);
            fail("testBuildStatusIdInFilter_ZeroInStatusIds is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknow exception occurs in testBuildStatusIdInFilter_ZeroInStatusIds.");
        }
    }

    /**
     * Tests buildStatusIdInFilter(List statusIds) method with negative in List
     * statusIds, IllegalArgumentException should be thrown.
     */
    public void testBuildStatusIdInFilter_NegativeInStatusIds() {
        List statusIds = new ArrayList();
        statusIds.add(new Long(-1));

        try {
            ScorecardSearchBundle.buildStatusIdInFilter(statusIds);
            fail("testBuildStatusIdInFilter_NegativeInStatusIds is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail(
                "Unknow exception occurs in testBuildStatusIdInFilter_NegativeInStatusIds.");
        }
    }

    /**
     * Tests buildStatusNameEqualFilter(String statusName) method with null String
     * statusName, IllegalArgumentException should be thrown.
     */
    public void testBuildStatusNameEqualFilter_NullStatusName() {
        try {
            ScorecardSearchBundle.buildStatusNameEqualFilter(null);
            fail("testBuildStatusNameEqualFilter_NullStatusName is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail(
                "Unknow exception occurs in testBuildStatusNameEqualFilter_NullStatusName.");
        }
    }

    /**
     * Tests buildStatusNameEqualFilter(String statusName) method with empty String
     * statusName, IllegalArgumentException should be thrown.
     */
    public void testBuildStatusNameEqualFilter_EmptyStatusName() {
        try {
            ScorecardSearchBundle.buildStatusNameEqualFilter(" ");
            fail("testBuildStatusNameEqualFilter_EmptyStatusName is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail(
                "Unknow exception occurs in testBuildStatusNameEqualFilter_EmptyStatusName.");
        }
    }

    /**
     * Tests buildStatusNameInFilter(List statusNames) method with null List statusNames,
     * IllegalArgumentException should be thrown.
     */
    public void testBuildStatusNameInFilter_NullStatusNames() {
        try {
            ScorecardSearchBundle.buildStatusNameInFilter(null);
            fail("testBuildStatusNameInFilter_NullStatusNames is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail(
                "Unknow exception occurs in testBuildStatusNameInFilter_NullStatusNames.");
        }
    }

    /**
     * Tests buildStatusNameInFilter(List statusNames) method with empty List
     * statusNames, IllegalArgumentException should be thrown.
     */
    public void testBuildStatusNameInFilter_EmptyStatusNames() {
        List statusNames = new ArrayList();

        try {
            ScorecardSearchBundle.buildStatusNameInFilter(statusNames);
            fail("testBuildStatusNameInFilter_EmptyStatusNames is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail(
                "Unknow exception occurs in testBuildStatusNameInFilter_EmptyStatusNames.");
        }
    }

    /**
     * Tests buildStatusNameInFilter(List statusNames) method with empty in List
     * statusNames, IllegalArgumentException should be thrown.
     */
    public void testBuildStatusNameInFilter_EmptyInStatusNames() {
        List statusNames = new ArrayList();
        statusNames.add(" ");

        try {
            ScorecardSearchBundle.buildStatusNameInFilter(statusNames);
            fail("testBuildStatusNameInFilter_EmptyInStatusNames is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail(
                "Unknow exception occurs in testBuildStatusNameInFilter_EmptyInStatusNames.");
        }
    }

    /**
     * Tests buildStatusNameInFilter(List statusNames) method with null in List
     * statusNames, IllegalArgumentException should be thrown.
     */
    public void testBuildStatusNameInFilter_NullInStatusNames() {
        List statusNames = new ArrayList();
        statusNames.add(null);

        try {
            ScorecardSearchBundle.buildStatusNameInFilter(statusNames);
            fail("testBuildStatusNameInFilter_NullInStatusNames is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail(
                "Unknow exception occurs in testBuildStatusNameInFilter_NullInStatusNames.");
        }
    }

    /**
     * Tests buildProjectCategoryIdEqualFilter(long projectCategoryId) method with zero
     * long projectCategoryId, IllegalArgumentException should be thrown.
     */
    public void testBuildProjectCategoryIdEqualFilter_ZeroProjectCategoryId() {
        try {
            ScorecardSearchBundle.buildProjectCategoryIdEqualFilter(0);
            fail(
                "testBuildProjectCategoryIdEqualFilter_ZeroProjectCategoryId is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail(
                "Unknow exception occurs in testBuildProjectCategoryIdEqualFilter_ZeroProjectCategoryId.");
        }
    }

    /**
     * Tests buildProjectCategoryIdEqualFilter(long projectCategoryId) method with
     * negative long projectCategoryId, IllegalArgumentException should be thrown.
     */
    public void testBuildProjectCategoryIdEqualFilter_NegativeProjectCategoryId() {
        try {
            ScorecardSearchBundle.buildProjectCategoryIdEqualFilter(-1);
            fail(
                "testBuildProjectCategoryIdEqualFilter_NegativeProjectCategoryId is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail(
                "Unknow exception occurs in testBuildProjectCategoryIdEqualFilter_NegativeProjectCategoryId.");
        }
    }

    /**
     * Tests buildProjectCategoryIdInFilter(List projectCategoryIds) method with null
     * List projectCategoryIds, IllegalArgumentException should be thrown.
     */
    public void testBuildProjectCategoryIdInFilter_NullProjectCategoryIds() {
        try {
            ScorecardSearchBundle.buildProjectCategoryIdInFilter(null);
            fail("testBuildProjectCategoryIdInFilter_NullProjectCategoryIds is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail(
                "Unknow exception occurs in testBuildProjectCategoryIdInFilter_NullProjectCategoryIds.");
        }
    }

    /**
     * Tests buildProjectCategoryIdInFilter(List projectCategoryIds) method with empty
     * List projectCategoryIds, IllegalArgumentException should be thrown.
     */
    public void testBuildProjectCategoryIdInFilter_EmptyProjectCategoryIds() {
        try {
            ScorecardSearchBundle.buildProjectCategoryIdInFilter(new ArrayList());
            fail("testBuildProjectCategoryIdInFilter_NullProjectCategoryIds is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail(
                "Unknow exception occurs in testBuildProjectCategoryIdInFilter_NullProjectCategoryIds.");
        }
    }

    /**
     * Tests buildProjectCategoryIdInFilter(List projectCategoryIds) method with zero in
     * List projectCategoryIds, IllegalArgumentException should be thrown.
     */
    public void testBuildProjectCategoryIdInFilter_ZeroInProjectCategoryIds() {
        List projectCategoryIds = new ArrayList();
        projectCategoryIds.add(new Long(0));

        try {
            ScorecardSearchBundle.buildProjectCategoryIdInFilter(projectCategoryIds);
            fail(
                "testBuildProjectCategoryIdInFilter_ZeroInProjectCategoryIds is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail(
                "Unknow exception occurs in testBuildProjectCategoryIdInFilter_ZeroInProjectCategoryIds.");
        }
    }

    /**
     * Tests buildProjectCategoryIdInFilter(List projectCategoryIds) method with negative
     * in List projectCategoryIds, IllegalArgumentException should be thrown.
     */
    public void testBuildProjectCategoryIdInFilter_NegativeInProjectCategoryIds() {
        List projectCategoryIds = new ArrayList();
        projectCategoryIds.add(new Long(-1));

        try {
            ScorecardSearchBundle.buildProjectCategoryIdInFilter(projectCategoryIds);
            fail(
                "testBuildProjectCategoryIdInFilter_NegativeInProjectCategoryIds is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail(
                "Unknow exception occurs in testBuildProjectCategoryIdInFilter_NegativeInProjectCategoryIds.");
        }
    }

    /**
     * Tests buildProjectIdEqualFilter(long projectId) method with zero long projectId,
     * IllegalArgumentException should be thrown.
     */
    public void testBuildProjectIdEqualFilter_ZeroProjectId() {
        try {
            ScorecardSearchBundle.buildProjectIdEqualFilter(0);
            fail("testBuildProjectIdEqualFilter_ZeroProjectId is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail(
                "Unknow exception occurs in testBuildProjectIdEqualFilter_ZeroProjectId.");
        }
    }

    /**
     * Tests buildProjectIdEqualFilter(long projectId) method with negative long
     * projectId, IllegalArgumentException should be thrown.
     */
    public void testBuildProjectIdEqualFilter_NegativeProjectId() {
        try {
            ScorecardSearchBundle.buildProjectIdEqualFilter(-1);
            fail("testBuildProjectIdEqualFilter_NegativeProjectId is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail(
                "Unknow exception occurs in testBuildProjectIdEqualFilter_NegativeProjectId.");
        }
    }

    /**
     * Tests buildProjectIdInFilter(List projectIds) method with null List projectIds,
     * IllegalArgumentException should be thrown.
     */
    public void testBuildProjectIdInFilter_NullProjectIds() {
        try {
            ScorecardSearchBundle.buildProjectIdInFilter(null);
            fail("testBuildProjectIdInFilter_NullProjectIds is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknow exception occurs in testBuildProjectIdInFilter_NullProjectIds.");
        }
    }

    /**
     * Tests buildProjectIdInFilter(List projectIds) method with empty List projectIds,
     * IllegalArgumentException should be thrown.
     */
    public void testBuildProjectIdInFilter_EmptyProjectIds() {
        try {
            ScorecardSearchBundle.buildProjectIdInFilter(new ArrayList());
            fail("testBuildProjectIdInFilter_EmptyProjectIds is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknow exception occurs in testBuildProjectIdInFilter_EmptyProjectIds.");
        }
    }

    /**
     * Tests buildProjectIdInFilter(List projectIds) method with zero in List projectIds,
     * IllegalArgumentException should be thrown.
     */
    public void testBuildProjectIdInFilter_ZeroInProjectIds() {
        List projectIds = new ArrayList();
        projectIds.add(new Long(0));

        try {
            ScorecardSearchBundle.buildProjectIdInFilter(projectIds);
            fail("testBuildProjectIdInFilter_ZeroInProjectIds is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail(
                "Unknow exception occurs in testBuildProjectIdInFilter_ZeroInProjectIds.");
        }
    }

    /**
     * Tests buildProjectIdInFilter(List projectIds) method with negative in List
     * projectIds, IllegalArgumentException should be thrown.
     */
    public void testBuildProjectIdInFilter_NegativeInProjectIds() {
        List projectIds = new ArrayList();
        projectIds.add(new Long(-1));

        try {
            ScorecardSearchBundle.buildProjectIdInFilter(projectIds);
            fail("testBuildProjectIdInFilter_NegativeInProjectIds is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail(
                "Unknow exception occurs in testBuildProjectIdInFilter_NegativeInProjectIds.");
        }
    }

    /**
     * Tests buildNameEqualFilter(String name) method with null String name,
     * IllegalArgumentException should be thrown.
     */
    public void testBuildNameEqualFilter_NullName() {
        try {
            ScorecardSearchBundle.buildNameEqualFilter(null);
            fail("testBuildNameEqualFilter_NullName is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknow exception occurs in testBuildNameEqualFilter_NullName.");
        }
    }

    /**
     * Tests buildNameEqualFilter(String name) method with empty String name,
     * IllegalArgumentException should be thrown.
     */
    public void testBuildNameEqualFilter_EmptyName() {
        try {
            ScorecardSearchBundle.buildNameEqualFilter(" ");
            fail("testBuildNameEqualFilter_EmptyName is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknow exception occurs in testBuildNameEqualFilter_EmptyName.");
        }
    }

    /**
     * Tests buildNameInFilter(List names) method with null List names,
     * IllegalArgumentException should be thrown.
     */
    public void testBuildNameInFilter_NullNames() {
        try {
            ScorecardSearchBundle.buildNameInFilter(null);
            fail("testBuildNameInFilter_NullNames is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknow exception occurs in testBuildNameInFilter_NullNames.");
        }
    }

    /**
     * Tests buildNameInFilter(List names) method with empty List names,
     * IllegalArgumentException should be thrown.
     */
    public void testBuildNameInFilter_EmptyNames() {
        try {
            ScorecardSearchBundle.buildNameInFilter(new ArrayList());
            fail("testBuildNameInFilter_EmptyNames is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknow exception occurs in testBuildNameInFilter_EmptyNames.");
        }
    }

    /**
     * Tests buildNameInFilter(List names) method with empty in List names,
     * IllegalArgumentException should be thrown.
     */
    public void testBuildNameInFilter_EmptyInNames() {
        List names = new ArrayList();
        names.add(" ");

        try {
            ScorecardSearchBundle.buildNameInFilter(names);
            fail("testBuildNameInFilter_EmptyInNames is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknow exception occurs in testBuildNameInFilter_EmptyInNames.");
        }
    }

    /**
     * Tests buildNameInFilter(List names) method with null in List names,
     * IllegalArgumentException should be thrown.
     */
    public void testBuildNameInFilter_NullInNames() {
        List names = new ArrayList();
        names.add(null);

        try {
            ScorecardSearchBundle.buildNameInFilter(names);
            fail("testBuildNameInFilter_NullInNames is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknow exception occurs in testBuildNameInFilter_NullInNames.");
        }
    }

    /**
     * Tests buildVersionEqualFilter(String version) method with null String version,
     * IllegalArgumentException should be thrown.
     */
    public void testBuildVersionEqualFilter_NullVersion() {
        try {
            ScorecardSearchBundle.buildVersionEqualFilter(null);
            fail("testBuildVersionEqualFilter_NullVersion is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknow exception occurs in testBuildVersionEqualFilter_NullVersion.");
        }
    }

    /**
     * Tests buildVersionEqualFilter(String version) method with empty String version,
     * IllegalArgumentException should be thrown.
     */
    public void testBuildVersionEqualFilter_EmptyVersion() {
        try {
            ScorecardSearchBundle.buildVersionEqualFilter(" ");
            fail("testBuildVersionEqualFilter_EmptyVersion is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknow exception occurs in testBuildVersionEqualFilter_EmptyVersion.");
        }
    }

    /**
     * Tests buildVersionInFilter(List versions) method with null List versions,
     * IllegalArgumentException should be thrown.
     */
    public void testBuildVersionInFilter_NullVersions() {
        try {
            ScorecardSearchBundle.buildVersionInFilter(null);
            fail("testBuildVersionInFilter_NullVersions is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknow exception occurs in testBuildVersionInFilter_NullVersions.");
        }
    }

    /**
     * Tests buildVersionInFilter(List versions) method with empty List versions,
     * IllegalArgumentException should be thrown.
     */
    public void testBuildVersionInFilter_EmptyVersions() {
        try {
            ScorecardSearchBundle.buildVersionInFilter(new ArrayList());
            fail("testBuildVersionInFilter_EmptyVersions is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknow exception occurs in testBuildVersionInFilter_EmptyVersions.");
        }
    }

    /**
     * Tests buildVersionInFilter(List versions) method with empty in List versions,
     * IllegalArgumentException should be thrown.
     */
    public void testBuildVersionInFilter_EmptyInVersions() {
        List versions = new ArrayList();
        versions.add(" ");

        try {
            ScorecardSearchBundle.buildVersionInFilter(versions);
            fail("testBuildVersionInFilter_EmptyVersions is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknow exception occurs in testBuildVersionInFilter_EmptyVersions.");
        }
    }

    /**
     * Tests buildVersionInFilter(List versions) method with Null in List versions,
     * IllegalArgumentException should be thrown.
     */
    public void testBuildVersionInFilter_NullInVersions() {
        List versions = new ArrayList();
        versions.add(null);

        try {
            ScorecardSearchBundle.buildVersionInFilter(new ArrayList());
            fail("testBuildVersionInFilter_NullInVersions is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknow exception occurs in testBuildVersionInFilter_NullInVersions.");
        }
    }

    /**
     * Tests buildAndFilter(Filter f1, Filter f2) method with null Filter f1,
     * IllegalArgumentException should be thrown.
     */
    public void testBuildAndFilter_NullF1() {
        try {
            ScorecardSearchBundle.buildAndFilter(null, filter);
            fail("testBuildAndFilter_NullF1 is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknow exception occurs in testBuildAndFilter_NullF1.");
        }
    }

    /**
     * Tests buildAndFilter(Filter f1, Filter f2) method with null Filter f2,
     * IllegalArgumentException should be thrown.
     */
    public void testBuildAndFilter_NullF2() {
        try {
            ScorecardSearchBundle.buildAndFilter(filter, null);
            fail("testBuildAndFilter_NullF2 is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknow exception occurs in testBuildAndFilter_NullF2.");
        }
    }

    /**
     * Tests buildOrFilter(Filter f1, Filter f2) method with null Filter f1,
     * IllegalArgumentException should be thrown.
     */
    public void testBuildOrFilter_NullF1() {
        try {
            ScorecardSearchBundle.buildOrFilter(null, filter);
            fail("testBuildOrFilter_NullF1 is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknow exception occurs in testBuildOrFilter_NullF1.");
        }
    }

    /**
     * Tests buildOrFilter(Filter f1, Filter f2) method with null Filter f2,
     * IllegalArgumentException should be thrown.
     */
    public void testBuildOrFilter_NullF2() {
        try {
            ScorecardSearchBundle.buildOrFilter(filter, null);
            fail("testBuildOrFilter_NullF2 is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknow exception occurs in testBuildOrFilter_NullF2.");
        }
    }

    /**
     * Tests buildNotFilter(Filter f1) method with null Filter f1,
     * IllegalArgumentException should be thrown.
     */
    public void testBuildNotFilter_NullF1() {
        try {
            ScorecardSearchBundle.buildNotFilter(null);
            fail("testBuildNotFilter_NullF1 is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknow exception occurs in testBuildNotFilter_NullF1.");
        }
    }
}
