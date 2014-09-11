/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project;

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
 * Unit test for ProjectFilterUtility class.
 *
 * @author iamajia
 * @version 1.0
 */
public class ProjectFilterUtilityAccuracyTest extends TestCase {
    /**
     * this filter is used in the test.
     */
    private Filter f1 = ProjectFilterUtility.buildCategoryIdEqualFilter(1);

    /**
     * this filter is used in the test.
     */
    private Filter f2 = ProjectFilterUtility.buildTypeIdEqualFilter(2);

    /**
     * this list only contains Long instance, it is used in the test.
     */
    private List longList = new ArrayList();

    /**
     * this list only contains String instance, it is used in the test.
     */
    private List stringList = new ArrayList();

    /**
     * Aggregates all tests in this class.
     *
     * @return Test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(ProjectFilterUtilityAccuracyTest.class);
    }

    /**
     * Sets up the environment for the TestCase.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    protected void setUp() throws Exception {
        // create a Long list that is used in the test.
        longList.add(new Long(1));
        longList.add(new Long(2));
        longList.add(new Long(3));
        // create a String list that is used in the test.
        stringList.add("string1");
        stringList.add("string2");
        stringList.add("string3");
    }

    /**
     * Accuracy test of <code>buildTypeIdEqualFilter(long typeId)</code> method.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildTypeIdEqualFilterAccuracy() throws Exception {
        EqualToFilter filter = (EqualToFilter) ProjectFilterUtility.buildTypeIdEqualFilter(1);
        assertEquals("name is incorrect.", ProjectFilterUtility.PROJECT_TYPE_ID, filter.getName());
        assertEquals("value is incorrect.", new Long(1), filter.getValue());
    }

    /**
     * Accuracy test of <code>buildTypeIdInFilter(List typeIds)</code> method.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildTypeIdInFilterAccuracy() throws Exception {
        InFilter filter = (InFilter) ProjectFilterUtility.buildTypeIdInFilter(longList);
        assertEquals("name is incorrect.", ProjectFilterUtility.PROJECT_TYPE_ID, filter.getName());
        assertEquals("value is incorrect.", longList, filter.getList());
    }

    /**
     * Accuracy test of <code>buildTypeNameEqualFilter(String name)</code> method.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildTypeNameEqualFilterAccuracy() throws Exception {
        EqualToFilter filter = (EqualToFilter) ProjectFilterUtility.buildTypeNameEqualFilter("type1");
        assertEquals("name is incorrect.", ProjectFilterUtility.PROJECT_TYPE_NAME, filter.getName());
        assertEquals("value is incorrect.", "type1", filter.getValue());
    }

    /**
     * Accuracy test of <code>buildTypeNameInFilter(List names)</code> method.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildTypeNameInFilterAccuracy() throws Exception {
        InFilter filter = (InFilter) ProjectFilterUtility.buildTypeNameInFilter(stringList);
        assertEquals("name is incorrect.", ProjectFilterUtility.PROJECT_TYPE_NAME, filter.getName());
        assertEquals("value is incorrect.", stringList, filter.getList());
    }

    /**
     * Accuracy test of <code>buildCategoryIdEqualFilter(long categoryId)</code> method.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildCategoryIdEqualFilterAccuracy() throws Exception {
        EqualToFilter filter = (EqualToFilter) ProjectFilterUtility.buildCategoryIdEqualFilter(1);
        assertEquals("name is incorrect.", ProjectFilterUtility.PROJECT_CATEGORY_ID, filter.getName());
        assertEquals("value is incorrect.", new Long(1), filter.getValue());
    }

    /**
     * Accuracy test of <code>buildCategoryIdInFilter(List categoryIds)</code> method.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildCategoryIdInFilterAccuracy() throws Exception {
        InFilter filter = (InFilter) ProjectFilterUtility.buildCategoryIdInFilter(longList);
        assertEquals("name is incorrect.", ProjectFilterUtility.PROJECT_CATEGORY_ID, filter.getName());
        assertEquals("value is incorrect.", longList, filter.getList());
    }

    /**
     * Accuracy test of <code>buildCategoryNameEqualFilter(String name)</code> method.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildCategoryNameEqualFilterAccuracy() throws Exception {
        EqualToFilter filter = (EqualToFilter) ProjectFilterUtility.buildCategoryNameEqualFilter("category1");
        assertEquals("name is incorrect.", ProjectFilterUtility.PROJECT_CATEGORY_NAME, filter.getName());
        assertEquals("value is incorrect.", "category1", filter.getValue());
    }

    /**
     * Accuracy test of <code>buildCategoryNameInFilter(List names)</code> method.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildCategoryNameInFilterAccuracy() throws Exception {
        InFilter filter = (InFilter) ProjectFilterUtility.buildCategoryNameInFilter(stringList);
        assertEquals("name is incorrect.", ProjectFilterUtility.PROJECT_CATEGORY_NAME, filter.getName());
        assertEquals("value is incorrect.", stringList, filter.getList());
    }

    /**
     * Accuracy test of <code>buildStatusIdEqualFilter(long statusId)</code> method.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildStatusIdEqualFilterAccuracy() throws Exception {
        EqualToFilter filter = (EqualToFilter) ProjectFilterUtility.buildStatusIdEqualFilter(1);
        assertEquals("name is incorrect.", ProjectFilterUtility.PROJECT_STATUS_ID, filter.getName());
        assertEquals("value is incorrect.", new Long(1), filter.getValue());
    }

    /**
     * Accuracy test of <code>buildStatusIdInFilter(List statusIds)</code> method.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildStatusIdInFilterAccuracy() throws Exception {
        InFilter filter = (InFilter) ProjectFilterUtility.buildStatusIdInFilter(longList);
        assertEquals("name is incorrect.", ProjectFilterUtility.PROJECT_STATUS_ID, filter.getName());
        assertEquals("value is incorrect.", longList, filter.getList());
    }

    /**
     * Accuracy test of <code>buildStatusNameEqualFilter(String name)</code> method.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildStatusNameEqualFilterAccuracy() throws Exception {
        EqualToFilter filter = (EqualToFilter) ProjectFilterUtility.buildStatusNameEqualFilter("status1");
        assertEquals("name is incorrect.", ProjectFilterUtility.PROJECT_STATUS_NAME, filter.getName());
        assertEquals("value is incorrect.", "status1", filter.getValue());
    }

    /**
     * Accuracy test of <code>buildStatusNameInFilter(List names)</code> method.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildStatusNameInFilterAccuracy() throws Exception {
        InFilter filter = (InFilter) ProjectFilterUtility.buildStatusNameInFilter(stringList);
        assertEquals("name is incorrect.", ProjectFilterUtility.PROJECT_STATUS_NAME, filter.getName());
        assertEquals("value is incorrect.", stringList, filter.getList());
    }

    /**
     * Accuracy test of <code>buildProjectPropertyNameEqualFilter(String name)</code> method.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildProjectPropertyNameEqualFilterAccuracy() throws Exception {
        EqualToFilter filter = (EqualToFilter) ProjectFilterUtility
            .buildProjectPropertyNameEqualFilter("projectProperty1");
        assertEquals("name is incorrect.", ProjectFilterUtility.PROJECT_PROPERTY_NAME, filter.getName());
        assertEquals("value is incorrect.", "projectProperty1", filter.getValue());
    }

    /**
     * Accuracy test of <code>buildProjectPropertyNameInFilter(List names)</code> method.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildProjectPropertyNameInFilterAccuracy() throws Exception {
        InFilter filter = (InFilter) ProjectFilterUtility.buildProjectPropertyNameInFilter(stringList);
        assertEquals("name is incorrect.", ProjectFilterUtility.PROJECT_PROPERTY_NAME, filter.getName());
        assertEquals("value is incorrect.", stringList, filter.getList());
    }

    /**
     * Accuracy test of <code>buildProjectPropertyValueEqualFilter(String value)</code> method.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildProjectPropertyValueEqualFilterAccuracy() throws Exception {
        EqualToFilter filter = (EqualToFilter) ProjectFilterUtility
            .buildProjectPropertyValueEqualFilter("projectProperty1");
        assertEquals("name is incorrect.", ProjectFilterUtility.PROJECT_PROPERTY_VALUE, filter.getName());
        assertEquals("value is incorrect.", "projectProperty1", filter.getValue());
    }

    /**
     * Accuracy test of <code>buildProjectPropertyValueInFilter(List values)</code> method.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildProjectPropertyValueInFilterAccuracy() throws Exception {
        InFilter filter = (InFilter) ProjectFilterUtility.buildProjectPropertyValueInFilter(stringList);
        assertEquals("name is incorrect.", ProjectFilterUtility.PROJECT_PROPERTY_VALUE, filter.getName());
        assertEquals("value is incorrect.", stringList, filter.getList());
    }

    /**
     * Accuracy test of <code>buildProjectPropertyEqualFilter(String name, String value)</code> method.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildProjectPropertyEqualFilterAccuracy() throws Exception {
        AndFilter filter = (AndFilter) ProjectFilterUtility.buildProjectPropertyEqualFilter("name", "value");
        List filterList = filter.getFilters();
        assertEquals("length is incorrect.", 2, filterList.size());
        EqualToFilter f1 = (EqualToFilter) filterList.get(0);
        EqualToFilter f2 = (EqualToFilter) filterList.get(1);
        assertEquals("f1's name is incorrect.", ProjectFilterUtility.PROJECT_PROPERTY_NAME, f1.getName());
        assertEquals("f1's value is incorrect.", "name", f1.getValue());
        assertEquals("f2's name is incorrect.", ProjectFilterUtility.PROJECT_PROPERTY_VALUE, f2.getName());
        assertEquals("f2's value is incorrect.", "value", f2.getValue());
    }

    /**
     * Accuracy test of <code>buildProjectResourcePropertyNameEqualFilter(String name)</code> method.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildProjectResourcePropertyNameEqualFilterAccuracy() throws Exception {
        EqualToFilter filter = (EqualToFilter) ProjectFilterUtility
            .buildProjectResourcePropertyNameEqualFilter("projectResourceProperty1");
        assertEquals("name is incorrect.", ProjectFilterUtility.PROJECT_RESOURCE_PROPERTY_NAME, filter.getName());
        assertEquals("value is incorrect.", "projectResourceProperty1", filter.getValue());
    }

    /**
     * Accuracy test of <code>buildProjectResourcePropertyNameInFilter(List names)</code> method.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildProjectResourcePropertyNameInFilterAccuracy() throws Exception {
        InFilter filter = (InFilter) ProjectFilterUtility.buildProjectResourcePropertyNameInFilter(stringList);
        assertEquals("name is incorrect.", ProjectFilterUtility.PROJECT_RESOURCE_PROPERTY_NAME, filter.getName());
        assertEquals("value is incorrect.", stringList, filter.getList());
    }

    /**
     * Accuracy test of <code>buildProjectResourcePropertyValueEqualFilter(String value)</code> method.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildProjectResourcePropertyValueEqualFilterAccuracy() throws Exception {
        EqualToFilter filter = (EqualToFilter) ProjectFilterUtility
            .buildProjectResourcePropertyValueEqualFilter("projectResourceProperty1");
        assertEquals("name is incorrect.", ProjectFilterUtility.PROJECT_RESOURCE_PROPERTY_VALUE, filter.getName());
        assertEquals("value is incorrect.", "projectResourceProperty1", filter.getValue());
    }

    /**
     * Accuracy test of <code>buildProjectPropertyResourceEqualFilter(String name, String value)</code> method.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildProjectPropertyResourceEqualFilterAccuracy() throws Exception {
        AndFilter filter = (AndFilter) ProjectFilterUtility.buildProjectPropertyResourceEqualFilter("name", "value");
        List filterList = filter.getFilters();
        assertEquals("length is incorrect.", 2, filterList.size());
        EqualToFilter f1 = (EqualToFilter) filterList.get(0);
        EqualToFilter f2 = (EqualToFilter) filterList.get(1);
        assertEquals("f1's name is incorrect.", ProjectFilterUtility.PROJECT_RESOURCE_PROPERTY_NAME, f1.getName());
        assertEquals("f1's value is incorrect.", "name", f1.getValue());
        assertEquals("f2's name is incorrect.", ProjectFilterUtility.PROJECT_RESOURCE_PROPERTY_VALUE, f2.getName());
        assertEquals("f2's value is incorrect.", "value", f2.getValue());
    }

    /**
     * Accuracy test of <code>buildAndFilter(Filter f1, Filter f2)</code> method.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildAndFilterAccuracy() throws Exception {
        AndFilter filter = (AndFilter) ProjectFilterUtility.buildAndFilter(f1, f2);
        List filterList = filter.getFilters();
        assertEquals("length is incorrect.", 2, filterList.size());
        assertEquals("f1 is incorrect.", f1, filterList.get(0));
        assertEquals("f2 is incorrect.", f2, filterList.get(1));
    }

    /**
     * Accuracy test of <code>buildOrFilter(Filter f1, Filter f2)</code> method.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildOrFilterAccuracy() throws Exception {
        OrFilter filter = (OrFilter) ProjectFilterUtility.buildOrFilter(f1, f2);
        List filterList = filter.getFilters();
        assertEquals("length is incorrect.", 2, filterList.size());
        assertEquals("f1 is incorrect.", f1, filterList.get(0));
        assertEquals("f2 is incorrect.", f2, filterList.get(1));
    }

    /**
     * Accuracy test of <code>buildNotFilter(Filter f1)</code> method.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildNotFilterAccuracy() throws Exception {
        NotFilter filter = (NotFilter) ProjectFilterUtility.buildNotFilter(f1);
        assertEquals("name is incorrect.", ProjectFilterUtility.PROJECT_CATEGORY_ID, ((EqualToFilter) filter
            .getFilter()).getName());
        assertEquals("value is incorrect.", new Long(1), ((EqualToFilter) filter.getFilter()).getValue());
    }
}
