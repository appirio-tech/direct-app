/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.scorecard.accuracytests;

import com.topcoder.management.scorecard.ScorecardSearchBundle;

import com.topcoder.search.builder.filter.AndFilter;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.search.builder.filter.InFilter;
import com.topcoder.search.builder.filter.NotFilter;
import com.topcoder.search.builder.filter.OrFilter;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.ArrayList;
import java.util.List;


/**
 * Accuracy test cases for class ScorecardSearchBundle.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AccuracyTestScorecardSearchBundle extends TestCase {
    /** The value of id used for test. */
    private static Long ID1 = new Long(10);

    /** The value of id used for test. */
    private static Long ID2 = new Long(20);

    /** The value of string used for test. */
    private static String STRING1 = "String 1";

    /** The value of string used for test. */
    private static String STRING2 = "String 2";

    /**
     * Returns the suit to run the test.
     *
     * @return suite to run the test
     */
    public static Test suite() {
        return new TestSuite(AccuracyTestScorecardSearchBundle.class);
    }

    /**
     * Tests buildTypeIdEqualFilter(long typeId).
     */
    public void testBuildTypeIdEqualFilterAccuracy() {
        EqualToFilter filter = (EqualToFilter) ScorecardSearchBundle.buildTypeIdEqualFilter(ID1.longValue());

        assertEquals("invalid filter name", filter.getName(), ScorecardSearchBundle.SCORECARD_TYPE_ID);
        assertEquals("invalid filter value", filter.getValue(), ID1);
    }

    /**
     * Tests buildTypeIdInFilter(List typeIds).
     */
    public void testBuildTypeIdInFilterAccuracy() {
        List ids = new ArrayList();
        ids.add(ID1);
        ids.add(ID2);

        InFilter filter = (InFilter) ScorecardSearchBundle.buildTypeIdInFilter(ids);

        assertEquals("invalid filter name", filter.getName(), ScorecardSearchBundle.SCORECARD_TYPE_ID);
        assertEquals("invalid filter value", filter.getList(), ids);
    }

    /**
     * Tests BuildTypeNameEqualFilter(String typeName).
     */
    public void testBuildTypeNameEqualFilterAccuracy() {
        EqualToFilter filter = (EqualToFilter) ScorecardSearchBundle.buildTypeNameEqualFilter(STRING1);

        assertEquals("invalid filter name", filter.getName(), ScorecardSearchBundle.SCORECARD_TYPE_NAME);
        assertEquals("invalid filter value", filter.getValue(), STRING1);
    }

    /**
     * Tests BuildTypeNameInFilter(List typeNames).
     */
    public void testBuildTypeNameInFilterAccuracy() {
        List names = new ArrayList();
        names.add(STRING1);
        names.add(STRING2);

        InFilter filter = (InFilter) ScorecardSearchBundle.buildTypeNameInFilter(names);

        assertEquals("invalid filter name", filter.getName(), ScorecardSearchBundle.SCORECARD_TYPE_NAME);
        assertEquals("invalid filter value", filter.getList(), names);
    }

    /**
     * Tests BuildStatusIdEqualFilter(long statusId).
     */
    public void testBuildStatusIdEqualFilterAccuracy() {
        EqualToFilter filter = (EqualToFilter) ScorecardSearchBundle.buildStatusIdEqualFilter(ID1.longValue());

        assertEquals("invalid filter name", filter.getName(), ScorecardSearchBundle.SCORECARD_STATUS_ID);
        assertEquals("invalid filter value", filter.getValue(), ID1);
    }

    /**
     * Tests BuildStatusIdInFilter(List statusIds).
     */
    public void testBuildStatusIdInFilterAccuracy() {
        List ids = new ArrayList();
        ids.add(ID1);
        ids.add(ID2);

        InFilter filter = (InFilter) ScorecardSearchBundle.buildStatusIdInFilter(ids);

        assertEquals("invalid filter name", filter.getName(), ScorecardSearchBundle.SCORECARD_STATUS_ID);
        assertEquals("invalid filter value", filter.getList(), ids);
    }

    /**
     * Tests BuildStatusNameEqualFilter(String name).
     */
    public void testBuildStatusNameEqualFilterAccuracy() {
        EqualToFilter filter = (EqualToFilter) ScorecardSearchBundle.buildStatusNameEqualFilter(STRING1);

        assertEquals("invalid filter name", filter.getName(), ScorecardSearchBundle.SCORECARD_STATUS_NAME);
        assertEquals("invalid filter value", filter.getValue(), STRING1);
    }

    /**
     * Tests BuildStatusNameInFilter(List statusNames).
     */
    public void testBuildStatusNameInFilterAccuracy() {
        List names = new ArrayList();
        names.add(STRING1);
        names.add(STRING2);

        InFilter filter = (InFilter) ScorecardSearchBundle.buildStatusNameInFilter(names);

        assertEquals("invalid filter name", filter.getName(), ScorecardSearchBundle.SCORECARD_STATUS_NAME);
        assertEquals("invalid filter value", filter.getList(), names);
    }

    /**
     * Tests BuildProjectCategoryIdEqualFilter(long projectCategoryId).
     */
    public void testBuildProjectCategoryIdEqualFilterAccuracy() {
        EqualToFilter filter = (EqualToFilter) ScorecardSearchBundle.buildProjectCategoryIdEqualFilter(ID1.longValue());

        assertEquals("invalid filter name", filter.getName(), ScorecardSearchBundle.PROJECT_CATEGORY_ID);
        assertEquals("invalid filter value", filter.getValue(), ID1);
    }

    /**
     * Tests BuildProjectCategoryIdInFilter(List projectCategoryIds).
     */
    public void testBuildProjectCategoryIdInFilterAccuracy() {
        List ids = new ArrayList();
        ids.add(ID1);
        ids.add(ID2);

        InFilter filter = (InFilter) ScorecardSearchBundle.buildProjectCategoryIdInFilter(ids);

        assertEquals("invalid filter name", filter.getName(), ScorecardSearchBundle.PROJECT_CATEGORY_ID);
        assertEquals("invalid filter value", filter.getList(), ids);
    }

    /**
     * Tests BuildProjectIdEqualFilter(long projectId).
     */
    public void testBuildProjectIdEqualFilterAccuracy() {
        EqualToFilter filter = (EqualToFilter) ScorecardSearchBundle.buildProjectIdEqualFilter(ID1.longValue());

        assertEquals("invalid filter name", filter.getName(), ScorecardSearchBundle.PROJECT_ID);
        assertEquals("invalid filter value", filter.getValue(), ID1);
    }

    /**
     * Tests BuildProjectIdInFilter(List projectIds).
     */
    public void testBuildProjectIdInFilterAccuracy() {
        List ids = new ArrayList();
        ids.add(ID1);
        ids.add(ID2);

        InFilter filter = (InFilter) ScorecardSearchBundle.buildProjectIdInFilter(ids);

        assertEquals("invalid filter name", filter.getName(), ScorecardSearchBundle.PROJECT_ID);
        assertEquals("invalid filter value", filter.getList(), ids);
    }

    /**
     * Tests BuildNameEqualFilter(String name).
     */
    public void testBuildNameEqualFilterAccuracy() {
        EqualToFilter filter = (EqualToFilter) ScorecardSearchBundle.buildNameEqualFilter(STRING1);

        assertEquals("invalid filter name", filter.getName(), ScorecardSearchBundle.SCORECARD_NAME);
        assertEquals("invalid filter value", filter.getValue(), STRING1);
    }

    /**
     * Tests BuildNameInFilter(List names).
     */
    public void testBuildNameInFilterAccuracy() {
        List names = new ArrayList();
        names.add(STRING1);
        names.add(STRING2);

        InFilter filter = (InFilter) ScorecardSearchBundle.buildNameInFilter(names);

        assertEquals("invalid filter name", filter.getName(), ScorecardSearchBundle.SCORECARD_NAME);
        assertEquals("invalid filter value", filter.getList(), names);
    }

    /**
     * Tests BuildVersionEqualFilter(String version).
     */
    public void testBuildVersionEqualFilterAccuracy() {
        EqualToFilter filter = (EqualToFilter) ScorecardSearchBundle.buildVersionEqualFilter(STRING1);

        assertEquals("invalid filter name", filter.getName(), ScorecardSearchBundle.SCORECARD_VERSION);
        assertEquals("invalid filter value", filter.getValue(), STRING1);
    }

    /**
     * Tests BuildVersionInFilter(List versions).
     */
    public void testBuildVersionInFilterAccuracy() {
        List names = new ArrayList();
        names.add(STRING1);
        names.add(STRING2);

        InFilter filter = (InFilter) ScorecardSearchBundle.buildVersionInFilter(names);

        assertEquals("invalid filter name", filter.getName(), ScorecardSearchBundle.SCORECARD_VERSION);
        assertEquals("invalid filter value", filter.getList(), names);
    }

    /**
     * Tests buildAndFilter(Filter f1, Filter f2).
     */
    public void testBuildAndFilterAccuracy() {
        EqualToFilter f1 = (EqualToFilter) ScorecardSearchBundle.buildTypeIdEqualFilter(ID1.longValue());

        EqualToFilter f2 = (EqualToFilter) ScorecardSearchBundle.buildTypeIdEqualFilter(ID2.longValue());

        AndFilter filter = (AndFilter) ScorecardSearchBundle.buildAndFilter(f1, f2);

        List filters = filter.getFilters();

        assertEquals("invalid filter values", filters.size(), 2);
        assertEquals("invalid filter values", ((EqualToFilter) filters.get(0)).getValue(), ID1);
        assertEquals("invalid filter values", ((EqualToFilter) filters.get(1)).getValue(), ID2);
    }

    /**
     * Tests buildOrFilter(Filter f1, Filter f2).
     */
    public void testBuildOrFilterAccuracy() {
        Filter f1 = ScorecardSearchBundle.buildTypeIdEqualFilter(ID1.longValue());

        Filter f2 = ScorecardSearchBundle.buildTypeIdEqualFilter(ID2.longValue());

        OrFilter filter = (OrFilter) ScorecardSearchBundle.buildOrFilter(f1, f2);

        List filters = filter.getFilters();

        assertEquals("invalid filter values", filters.size(), 2);
        assertEquals("invalid filter values", ((EqualToFilter) filters.get(0)).getValue(), ID1);
        assertEquals("invalid filter values", ((EqualToFilter) filters.get(1)).getValue(), ID2);
    }

    /**
     * Tests buildNotFilter(Filter f1).
     */
    public void testBuildNotFilterAccuracy() {
        Filter equalFilter = ScorecardSearchBundle.buildTypeIdEqualFilter(ID1.longValue());

        NotFilter filter = (NotFilter) ScorecardSearchBundle.buildNotFilter(equalFilter);

        assertEquals("invalid filter value", ((EqualToFilter) filter.getFilter()).getValue(), ID1);
    }
}
