/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.scorecard;

import java.util.ArrayList;
import java.util.List;

import com.topcoder.search.builder.filter.AndFilter;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.search.builder.filter.InFilter;
import com.topcoder.search.builder.filter.NotFilter;
import com.topcoder.search.builder.filter.OrFilter;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for ScorecardSearchBundle class.
 * @author zhuzeyuan
 * @version 1.0
 */
public class ScorecardSearchBundleTest extends TestCase {

    /**
     * The value of an int to test.
     */
    private static final int INT1 = 77;

    /**
     * The value of an int to test.
     */
    private static final int INT2 = 88;

    /**
     * The value of a string to test.
     */
    private static final String STRING1 = "string1";

    /**
     * The value of a string to test.
     */
    private static final String STRING2 = "string2";

    /**
     * The List that contains <code>INT1</code>and <code>INT2</code> as Long instances for test.
     */
    private static final List LISTINT = new ArrayList();

    /**
     * The List that contains <code>STRING1</code>and <code>STRING2</code> for test.
     */
    private static final List LISTSTRING = new ArrayList();

    /**
     * Aggragates all tests in this class.
     * @return Test suite aggragating all tests.
     */
    public static Test suite() {
        return new TestSuite(ScorecardSearchBundleTest.class);
    }

    /**
     * Sets up the environment for the TestCase.
     * @throws Exception
     *             throw exception to JUnit.
     */
    protected void setUp() throws Exception {
        LISTINT.add(new Long(INT1));
        LISTINT.add(new Long(INT2));
        LISTSTRING.add(STRING1);
        LISTSTRING.add(STRING2);
    }

    /**
     * Accuracy test of <code>buildTypeIdEqualFilter(int typeId)</code> method.
     * <p>
     * Create a simple filter by buildTypeIdEqualFilter, and test if it is built correctly.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildTypeIdEqualFilterAccuracy() throws Exception {
        EqualToFilter filter = (EqualToFilter) ScorecardSearchBundle.buildTypeIdEqualFilter(INT1);
        assertEquals("filter name mismatch", ScorecardSearchBundle.SCORECARD_TYPE_ID, filter.getName());
        assertEquals("filter value mismatch", new Long(INT1), filter.getValue());
    }

    /**
     * Failure test of <code>buildTypeIdEqualFilter(int typeId)</code> method.
     * <p>
     * Call buildTypeIdEqualFilter with non-positive id.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildTypeIdEqualFilterFailure() throws Exception {
        try {
            ScorecardSearchBundle.buildTypeIdEqualFilter(0);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>buildTypeIdInFilter(List typeIds)</code> method.
     * <p>
     * Create a simple filter by buildTypeIdInFilter, and test if it is built correctly.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildTypeIdInFilterAccuracy() throws Exception {
        InFilter filter = (InFilter) ScorecardSearchBundle.buildTypeIdInFilter(LISTINT);
        assertEquals("filter name mismatch", ScorecardSearchBundle.SCORECARD_TYPE_ID, filter.getName());
        assertEquals("filter value list mismatch", LISTINT, filter.getList());
    }

    /**
     * Failure test of <code>buildTypeIdInFilter(List typeIds)</code> method.
     * <p>
     * Call buildTypeIdInFilter with null parameter.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildTypeIdInFilterFailure1() throws Exception {
        try {
            ScorecardSearchBundle.buildTypeIdInFilter(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>buildTypeIdInFilter(List typeIds)</code> method.
     * <p>
     * Call buildTypeIdInFilter with empty list.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildTypeIdInFilterFailure2() throws Exception {
        try {
            ScorecardSearchBundle.buildTypeIdInFilter(new ArrayList());
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>buildTypeIdInFilter(List typeIds)</code> method.
     * <p>
     * Call buildTypeIdInFilter with non-positive id.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildTypeIdInFilterFailure3() throws Exception {
        try {
            List list = new ArrayList();
            list.add(new Long(1));
            list.add(new Long(0));
            ScorecardSearchBundle.buildTypeIdInFilter(list);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>buildTypeNameEqualFilter(String typeName)</code> method.
     * <p>
     * Create a simple filter by buildTypeNameEqualFilter, and test if it is built correctly.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildTypeNameEqualFilterAccuracy() throws Exception {
        EqualToFilter filter = (EqualToFilter) ScorecardSearchBundle.buildTypeNameEqualFilter(STRING1);
        assertEquals("filter name mismatch", ScorecardSearchBundle.SCORECARD_TYPE_NAME, filter.getName());
        assertEquals("filter value mismatch", STRING1, filter.getValue());
    }

    /**
     * Failure test of <code>buildTypeNameEqualFilter(String typeName)</code> method.
     * <p>
     * Call buildTypeNameEqualFilter with null parameter.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildTypeNameEqualFilterFailure1() throws Exception {
        try {
            ScorecardSearchBundle.buildTypeNameEqualFilter(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>buildTypeNameEqualFilter(String typeName)</code> method.
     * <p>
     * Call buildTypeNameEqualFilter with empty string.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildTypeNameEqualFilterFailure2() throws Exception {
        try {
            ScorecardSearchBundle.buildTypeNameEqualFilter(" ");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>buildTypeNameInFilter(List typeNames)</code> method.
     * <p>
     * Create a simple filter by buildTypeNameInFilter, and test if it is built correctly.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildTypeNameInFilterAccuracy() throws Exception {
        InFilter filter = (InFilter) ScorecardSearchBundle.buildTypeNameInFilter(LISTSTRING);
        assertEquals("filter name mismatch", ScorecardSearchBundle.SCORECARD_TYPE_NAME, filter.getName());
        assertEquals("filter value list mismatch", LISTSTRING, filter.getList());
    }

    /**
     * Failure test of <code>buildTypeNameInFilter(List typeNames)</code> method.
     * <p>
     * Call buildTypeNameInFilter with null parameter.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildTypeNameInFilterFailure1() throws Exception {
        try {
            ScorecardSearchBundle.buildTypeNameInFilter(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>buildTypeNameInFilter(List typeNames)</code> method.
     * <p>
     * Call buildTypeNameInFilter with empty list.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildTypeNameInFilterFailure2() throws Exception {
        try {
            ScorecardSearchBundle.buildTypeNameInFilter(new ArrayList());
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>buildTypeNameInFilter(List typeNames)</code> method.
     * <p>
     * Call buildTypeNameInFilter with null element.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildTypeNameInFilterFailure3() throws Exception {
        try {
            List list = new ArrayList();
            list.add("hello");
            list.add(null);
            ScorecardSearchBundle.buildTypeNameInFilter(list);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>buildTypeNameInFilter(List typeNames)</code> method.
     * <p>
     * Call buildTypeNameInFilter with empty element.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildTypeNameInFilterFailure4() throws Exception {
        try {
            List list = new ArrayList();
            list.add("hello");
            list.add(" ");
            ScorecardSearchBundle.buildTypeNameInFilter(list);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>buildStatusIdEqualFilter(int statusId)</code> method.
     * <p>
     * Create a simple filter by buildStatusIdEqualFilter, and test if it is built correctly.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildStatusIdEqualFilterAccuracy() throws Exception {
        EqualToFilter filter = (EqualToFilter) ScorecardSearchBundle.buildStatusIdEqualFilter(INT2);
        assertEquals("filter name mismatch", ScorecardSearchBundle.SCORECARD_STATUS_ID, filter.getName());
        assertEquals("filter value mismatch", new Long(INT2), filter.getValue());
    }

    /**
     * Failure test of <code>buildStatusIdEqualFilter(int statusId)</code> method.
     * <p>
     * Call buildStatusIdEqualFilter with non-positive parameter.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildStatusIdEqualFilterFailure() throws Exception {
        try {
            ScorecardSearchBundle.buildStatusIdEqualFilter(0);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>buildStatusIdInFilter(List statusIds)</code> method.
     * <p>
     * Create a simple filter by buildStatusIdInFilter, and test if it is built correctly.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildStatusIdInFilterAccuracy() throws Exception {
        InFilter filter = (InFilter) ScorecardSearchBundle.buildStatusIdInFilter(LISTINT);
        assertEquals("filter name mismatch", ScorecardSearchBundle.SCORECARD_STATUS_ID, filter.getName());
        assertEquals("filter value list mismatch", LISTINT, filter.getList());
    }

    /**
     * Failure test of <code>buildStatusIdInFilter(List statusIds)</code> method.
     * <p>
     * Call buildStatusIdInFilter with null parameter.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildStatusIdInFilterFailure1() throws Exception {
        try {
            ScorecardSearchBundle.buildStatusIdInFilter(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>buildStatusIdInFilter(List statusIds)</code> method.
     * <p>
     * Call buildStatusIdInFilter with empty list.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildStatusIdInFilterFailure2() throws Exception {
        try {
            ScorecardSearchBundle.buildStatusIdInFilter(new ArrayList());
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>buildStatusIdInFilter(List statusIds)</code> method.
     * <p>
     * Call buildStatusIdInFilter with non-positive element.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildStatusIdInFilterFailure3() throws Exception {
        try {
            List list = new ArrayList();
            list.add(new Long(1));
            list.add(new Long(0));
            ScorecardSearchBundle.buildStatusIdInFilter(list);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>buildStatusNameEqualFilter(String statusName)</code> method.
     * <p>
     * Create a simple filter by buildStatusNameEqualFilter, and test if it is built correctly.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildStatusNameEqualFilterAccuracy() throws Exception {
        EqualToFilter filter = (EqualToFilter) ScorecardSearchBundle.buildStatusNameEqualFilter(STRING2);
        assertEquals("filter name mismatch", ScorecardSearchBundle.SCORECARD_STATUS_NAME, filter.getName());
        assertEquals("filter value mismatch", STRING2, filter.getValue());
    }

    /**
     * Failure test of <code>buildStatusNameEqualFilter(String statusName)</code> method.
     * <p>
     * Call buildStatusNameEqualFilter with null parameter.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildStatusNameEqualFilterFailure1() throws Exception {
        try {
            ScorecardSearchBundle.buildStatusNameEqualFilter(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>buildStatusNameEqualFilter(String statusName)</code> method.
     * <p>
     * Call buildStatusNameEqualFilter with empty parameter.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildStatusNameEqualFilterFailure2() throws Exception {
        try {
            ScorecardSearchBundle.buildStatusNameEqualFilter("  ");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>buildStatusNameInFilter(List statusNames)</code> method.
     * <p>
     * Create a simple filter by buildStatusNameInFilter, and test if it is built correctly.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildStatusNameInFilterAccuracy() throws Exception {
        InFilter filter = (InFilter) ScorecardSearchBundle.buildStatusNameInFilter(LISTSTRING);
        assertEquals("filter name mismatch", ScorecardSearchBundle.SCORECARD_STATUS_NAME, filter.getName());
        assertEquals("filter value list mismatch", LISTSTRING, filter.getList());
    }

    /**
     * Failure test of <code>buildStatusNameInFilter(List statusNames)</code> method.
     * <p>
     * Call buildStatusNameInFilter with null parameter.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildStatusNameInFilterFailure1() throws Exception {
        try {
            ScorecardSearchBundle.buildStatusNameInFilter(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>buildStatusNameInFilter(List statusNames)</code> method.
     * <p>
     * Call buildStatusNameInFilter with empty list.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildStatusNameInFilterFailure2() throws Exception {
        try {
            ScorecardSearchBundle.buildStatusNameInFilter(new ArrayList());
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>buildStatusNameInFilter(List statusNames)</code> method.
     * <p>
     * Call buildStatusNameInFilter with null element.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildStatusNameInFilterFailure3() throws Exception {
        try {
            List list = new ArrayList();
            list.add(STRING1);
            list.add(null);
            ScorecardSearchBundle.buildStatusNameInFilter(list);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>buildStatusNameInFilter(List statusNames)</code> method.
     * <p>
     * Call buildStatusNameInFilter with empty string element.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildStatusNameInFilterFailure4() throws Exception {
        try {
            List list = new ArrayList();
            list.add(STRING1);
            list.add("  ");
            ScorecardSearchBundle.buildStatusNameInFilter(list);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>buildProjectCategoryIdEqualFilter(int ProjectCategoryId)</code> method.
     * <p>
     * Create a simple filter by buildProjectCategoryIdEqualFilter, and test if it is built correctly.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildProjectCategoryIdEqualFilterAccuracy() throws Exception {
        EqualToFilter filter = (EqualToFilter) ScorecardSearchBundle.buildProjectCategoryIdEqualFilter(INT2);
        assertEquals("filter name mismatch", ScorecardSearchBundle.PROJECT_CATEGORY_ID, filter.getName());
        assertEquals("filter value mismatch", new Long(INT2), filter.getValue());
    }

    /**
     * Failure test of <code>buildProjectCategoryIdEqualFilter(int ProjectCategoryId)</code> method.
     * <p>
     * Call buildProjectCategoryIdEqualFilter with non-positive parameter.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildProjectCategoryIdEqualFilterFailure() throws Exception {
        try {
            ScorecardSearchBundle.buildProjectCategoryIdEqualFilter(0);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>buildProjectCategoryIdInFilter(List ProjectCategoryIds)</code> method.
     * <p>
     * Create a simple filter by buildProjectCategoryIdInFilter, and test if it is built correctly.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildProjectCategoryIdInFilterAccuracy() throws Exception {
        InFilter filter = (InFilter) ScorecardSearchBundle.buildProjectCategoryIdInFilter(LISTINT);
        assertEquals("filter name mismatch", ScorecardSearchBundle.PROJECT_CATEGORY_ID, filter.getName());
        assertEquals("filter value list mismatch", LISTINT, filter.getList());
    }

    /**
     * Failure test of <code>buildProjectCategoryIdInFilter(List ProjectCategoryIds)</code> method.
     * <p>
     * Call buildProjectCategoryIdInFilter with null parameter.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildProjectCategoryIdInFilterFailure1() throws Exception {
        try {
            ScorecardSearchBundle.buildProjectCategoryIdInFilter(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>buildProjectCategoryIdInFilter(List ProjectCategoryIds)</code> method.
     * <p>
     * Call buildProjectCategoryIdInFilter with empty list.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildProjectCategoryIdInFilterFailure2() throws Exception {
        try {
            ScorecardSearchBundle.buildProjectCategoryIdInFilter(new ArrayList());
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>buildProjectCategoryIdInFilter(List ProjectCategoryIds)</code> method.
     * <p>
     * Call buildProjectCategoryIdInFilter with non-positive element.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildProjectCategoryIdInFilterFailure3() throws Exception {
        try {
            List list = new ArrayList();
            list.add(new Long(1));
            list.add(new Long(0));
            ScorecardSearchBundle.buildProjectCategoryIdInFilter(list);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>buildProjectIdEqualFilter(int ProjectId)</code> method.
     * <p>
     * Create a simple filter by buildProjectIdEqualFilter, and test if it is built correctly.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildProjectIdEqualFilterAccuracy() throws Exception {
        EqualToFilter filter = (EqualToFilter) ScorecardSearchBundle.buildProjectIdEqualFilter(INT2);
        assertEquals("filter name mismatch", ScorecardSearchBundle.PROJECT_ID, filter.getName());
        assertEquals("filter value mismatch", new Long(INT2), filter.getValue());
    }

    /**
     * Failure test of <code>buildProjectIdEqualFilter(int ProjectId)</code> method.
     * <p>
     * Call buildProjectIdEqualFilter with non-positive parameter.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildProjectIdEqualFilterFailure() throws Exception {
        try {
            ScorecardSearchBundle.buildProjectIdEqualFilter(0);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>buildProjectIdInFilter(List ProjectIds)</code> method.
     * <p>
     * Create a simple filter by buildProjectIdInFilter, and test if it is built correctly.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildProjectIdInFilterAccuracy() throws Exception {
        InFilter filter = (InFilter) ScorecardSearchBundle.buildProjectIdInFilter(LISTINT);
        assertEquals("filter name mismatch", ScorecardSearchBundle.PROJECT_ID, filter.getName());
        assertEquals("filter value list mismatch", LISTINT, filter.getList());
    }

    /**
     * Failure test of <code>buildProjectIdInFilter(List ProjectIds)</code> method.
     * <p>
     * Call buildProjectIdInFilter with null parameter.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildProjectIdInFilterFailure1() throws Exception {
        try {
            ScorecardSearchBundle.buildProjectIdInFilter(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>buildProjectIdInFilter(List ProjectIds)</code> method.
     * <p>
     * Call buildProjectIdInFilter with empty list.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildProjectIdInFilterFailure2() throws Exception {
        try {
            ScorecardSearchBundle.buildProjectIdInFilter(new ArrayList());
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>buildProjectIdInFilter(List ProjectIds)</code> method.
     * <p>
     * Call buildProjectIdInFilter with non-positive element.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildProjectIdInFilterFailure3() throws Exception {
        try {
            List list = new ArrayList();
            list.add(new Long(1));
            list.add(new Long(0));
            ScorecardSearchBundle.buildProjectIdInFilter(list);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>buildNameEqualFilter(String Name)</code> method.
     * <p>
     * Create a simple filter by buildNameEqualFilter, and test if it is built correctly.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildNameEqualFilterAccuracy() throws Exception {
        EqualToFilter filter = (EqualToFilter) ScorecardSearchBundle.buildNameEqualFilter(STRING2);
        assertEquals("filter name mismatch", ScorecardSearchBundle.SCORECARD_NAME, filter.getName());
        assertEquals("filter value mismatch", STRING2, filter.getValue());
    }

    /**
     * Failure test of <code>buildNameEqualFilter(String Name)</code> method.
     * <p>
     * Call buildNameEqualFilter with null parameter.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildNameEqualFilterFailure1() throws Exception {
        try {
            ScorecardSearchBundle.buildNameEqualFilter(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>buildNameEqualFilter(String Name)</code> method.
     * <p>
     * Call buildNameEqualFilter with empty parameter.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildNameEqualFilterFailure2() throws Exception {
        try {
            ScorecardSearchBundle.buildNameEqualFilter("  ");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>buildNameInFilter(List Names)</code> method.
     * <p>
     * Create a simple filter by buildNameInFilter, and test if it is built correctly.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildNameInFilterAccuracy() throws Exception {
        InFilter filter = (InFilter) ScorecardSearchBundle.buildNameInFilter(LISTSTRING);
        assertEquals("filter name mismatch", ScorecardSearchBundle.SCORECARD_NAME, filter.getName());
        assertEquals("filter value list mismatch", LISTSTRING, filter.getList());
    }

    /**
     * Failure test of <code>buildNameInFilter(List Names)</code> method.
     * <p>
     * Call buildNameInFilter with null parameter.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildNameInFilterFailure1() throws Exception {
        try {
            ScorecardSearchBundle.buildNameInFilter(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>buildNameInFilter(List Names)</code> method.
     * <p>
     * Call buildNameInFilter with empty list.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildNameInFilterFailure2() throws Exception {
        try {
            ScorecardSearchBundle.buildNameInFilter(new ArrayList());
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>buildNameInFilter(List Names)</code> method.
     * <p>
     * Call buildNameInFilter with null element.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildNameInFilterFailure3() throws Exception {
        try {
            List list = new ArrayList();
            list.add(STRING1);
            list.add(null);
            ScorecardSearchBundle.buildNameInFilter(list);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>buildNameInFilter(List Names)</code> method.
     * <p>
     * Call buildNameInFilter with empty string element.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildNameInFilterFailure4() throws Exception {
        try {
            List list = new ArrayList();
            list.add(STRING1);
            list.add("  ");
            ScorecardSearchBundle.buildNameInFilter(list);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>buildVersionEqualFilter(String Version)</code> method.
     * <p>
     * Create a simple filter by buildVersionEqualFilter, and test if it is built correctly.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildVersionEqualFilterAccuracy() throws Exception {
        EqualToFilter filter = (EqualToFilter) ScorecardSearchBundle.buildVersionEqualFilter(STRING2);
        assertEquals("filter name mismatch", ScorecardSearchBundle.SCORECARD_VERSION, filter.getName());
        assertEquals("filter value mismatch", STRING2, filter.getValue());
    }

    /**
     * Failure test of <code>buildVersionEqualFilter(String Version)</code> method.
     * <p>
     * Call buildVersionEqualFilter with null parameter.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildVersionEqualFilterFailure1() throws Exception {
        try {
            ScorecardSearchBundle.buildVersionEqualFilter(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>buildVersionEqualFilter(String Version)</code> method.
     * <p>
     * Call buildVersionEqualFilter with empty parameter.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildVersionEqualFilterFailure2() throws Exception {
        try {
            ScorecardSearchBundle.buildVersionEqualFilter("  ");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>buildVersionInFilter(List Versions)</code> method.
     * <p>
     * Create a simple filter by buildVersionInFilter, and test if it is built correctly.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildVersionInFilterAccuracy() throws Exception {
        InFilter filter = (InFilter) ScorecardSearchBundle.buildVersionInFilter(LISTSTRING);
        assertEquals("filter name mismatch", ScorecardSearchBundle.SCORECARD_VERSION, filter.getName());
        assertEquals("filter value list mismatch", LISTSTRING, filter.getList());
    }

    /**
     * Failure test of <code>buildVersionInFilter(List Versions)</code> method.
     * <p>
     * Call buildVersionInFilter with null parameter.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildVersionInFilterFailure1() throws Exception {
        try {
            ScorecardSearchBundle.buildVersionInFilter(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>buildVersionInFilter(List Versions)</code> method.
     * <p>
     * Call buildVersionInFilter with empty list.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildVersionInFilterFailure2() throws Exception {
        try {
            ScorecardSearchBundle.buildVersionInFilter(new ArrayList());
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>buildVersionInFilter(List Versions)</code> method.
     * <p>
     * Call buildVersionInFilter with null element.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildVersionInFilterFailure3() throws Exception {
        try {
            List list = new ArrayList();
            list.add(STRING1);
            list.add(null);
            ScorecardSearchBundle.buildVersionInFilter(list);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>buildVersionInFilter(List Versions)</code> method.
     * <p>
     * Call buildVersionInFilter with empty string element.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildVersionInFilterFailure4() throws Exception {
        try {
            List list = new ArrayList();
            list.add(STRING1);
            list.add("  ");
            ScorecardSearchBundle.buildVersionInFilter(list);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>buildAndFilter(Filter f1, Filter f2)</code> method.
     * <p>
     * Create an AndFilter with two simple EqualToFilters, and test if it is built correctly.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildAndFilterAccuracy() throws Exception {
        // Create an AndFilter with two simple EqualToFilters.
        EqualToFilter f1 = new EqualToFilter(STRING1, new Long(INT1));
        EqualToFilter f2 = new EqualToFilter(STRING2, new Long(INT2));
        AndFilter filter = (AndFilter) ScorecardSearchBundle.buildAndFilter(f1, f2);

        // Fetch the just built filter.
        assertEquals("filter type mismatch", Filter.AND_FILTER, filter.getFilterType());
        List filters = filter.getFilters();
        EqualToFilter ff1 = (EqualToFilter) filters.get(0);
        EqualToFilter ff2 = (EqualToFilter) filters.get(1);
        assertEquals("filther f1's name mismatch", STRING1, ff1.getName());
        assertEquals("filther f1's value mismatch", new Long(INT1), ff1.getValue());
        assertEquals("filther f2's name mismatch", STRING2, ff2.getName());
        assertEquals("filther f2's value mismatch", new Long(INT2), ff2.getValue());
    }

    /**
     * Accuracy test of <code>buildOrFilter(Filter f1, Filter f2)</code> method.
     * <p>
     * Create an OrFilter with two simple EqualToFilters, and test if it is built correctly.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildOrFilterAccuracy() throws Exception {
        // Create an OrFilter with two simple EqualToFilters.
        EqualToFilter f1 = new EqualToFilter(STRING1, new Long(INT1));
        EqualToFilter f2 = new EqualToFilter(STRING2, new Long(INT2));
        OrFilter filter = (OrFilter) ScorecardSearchBundle.buildOrFilter(f1, f2);

        // Fetch the just built filter.
        assertEquals("filter type mismatch", Filter.OR_FILTER, filter.getFilterType());
        List filters = filter.getFilters();
        EqualToFilter ff1 = (EqualToFilter) filters.get(0);
        EqualToFilter ff2 = (EqualToFilter) filters.get(1);
        assertEquals("filther f1's name mismatch", STRING1, ff1.getName());
        assertEquals("filther f1's value mismatch", new Long(INT1), ff1.getValue());
        assertEquals("filther f2's name mismatch", STRING2, ff2.getName());
        assertEquals("filther f2's value mismatch", new Long(INT2), ff2.getValue());
    }

    /**
     * Accuracy test of <code>buildNotFilter(Filter f1)</code> method.
     * <p>
     * Create a NotFilter with a simple EqualToFilters, and test if it is built correctly.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildNotFilterAccuracy() throws Exception {
        // Create a NotFilter with a simple EqualToFilters.
        EqualToFilter f1 = new EqualToFilter(STRING1, new Long(INT1));
        NotFilter filter = (NotFilter) ScorecardSearchBundle.buildNotFilter(f1);

        // Fetch the just built filter.
        assertEquals("filter type mismatch", Filter.NOT_FILTER, filter.getFilterType());
        EqualToFilter ff1 = (EqualToFilter) filter.getFilter();
        assertEquals("filther f1's name mismatch", STRING1, ff1.getName());
        assertEquals("filther f1's value mismatch", new Long(INT1), ff1.getValue());
    }
}
