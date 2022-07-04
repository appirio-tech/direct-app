/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project;

import java.util.ArrayList;
import java.util.List;

import com.topcoder.search.builder.filter.Filter;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for ProjectFilterUtility class.
 *
 * @author iamajia
 * @version 1.0
 */
public class ProjectFilterUtilityFailureTest extends TestCase {
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
        return new TestSuite(ProjectFilterUtilityFailureTest.class);
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
     * Failure test of <code>buildTypeIdEqualFilter(long typeId)</code> method.
     * <p>
     * typeId <= 0
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildTypeIdEqualFilterFailure1() throws Exception {
        try {
            ProjectFilterUtility.buildTypeIdEqualFilter(0);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>buildTypeIdEqualFilter(long typeId)</code> method.
     * <p>
     * typeId = -1
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildTypeIdEqualFilterFailure2() throws Exception {
        try {
            ProjectFilterUtility.buildTypeIdEqualFilter(-1);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>buildTypeIdInFilter(List typeIds)</code> method.
     * <p>
     * typeIds is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildTypeIdInFilterFailure1() throws Exception {
        try {
            ProjectFilterUtility.buildTypeIdInFilter(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>buildTypeIdInFilter(List typeIds)</code> method.
     * <p>
     * typeIds is empty.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildTypeIdInFilterFailure2() throws Exception {
        try {
            ProjectFilterUtility.buildTypeIdInFilter(new ArrayList());
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>buildTypeIdInFilter(List typeIds)</code> method.
     * <p>
     * typeIds contain a instance that is not Long.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildTypeIdInFilterFailure3() throws Exception {
        longList.add("string");
        try {
            ProjectFilterUtility.buildTypeIdInFilter(longList);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>buildTypeIdInFilter(List typeIds)</code> method.
     * <p>
     * typeIds contain a null element.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildTypeIdInFilterFailure4() throws Exception {
        longList.add(null);
        try {
            ProjectFilterUtility.buildTypeIdInFilter(longList);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>buildTypeIdInFilter(List typeIds)</code> method.
     * <p>
     * typeIds contain a id <= 0
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildTypeIdInFilterFailure5() throws Exception {
        longList.add(new Long(0));
        try {
            ProjectFilterUtility.buildTypeIdInFilter(longList);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>buildTypeNameEqualFilter(String name)</code> method.
     * <p>
     * name is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildTypeNameEqualFilterFailure1() throws Exception {
        try {
            ProjectFilterUtility.buildTypeNameEqualFilter(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>buildTypeNameEqualFilter(String name)</code> method.
     * <p>
     * name is empty.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildTypeNameEqualFilterFailure2() throws Exception {
        try {
            ProjectFilterUtility.buildTypeNameEqualFilter("  ");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>buildTypeNameInFilter(List names)</code> method.
     * <p>
     * names is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildTypeNameInFilterFailure1() throws Exception {
        try {
            ProjectFilterUtility.buildTypeNameInFilter(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>buildTypeNameInFilter(List names)</code> method.
     * <p>
     * names is empty.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildTypeNameInFilterFailure2() throws Exception {
        try {
            ProjectFilterUtility.buildTypeNameInFilter(new ArrayList());
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>buildTypeNameInFilter(List names)</code> method.
     * <p>
     * names contain a instance that is not String.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildTypeNameInFilterFailure3() throws Exception {
        stringList.add(new Long(2));
        try {
            ProjectFilterUtility.buildTypeNameInFilter(stringList);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>buildTypeNameInFilter(List names)</code> method.
     * <p>
     * names contain a String that is empty.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildTypeNameInFilterFailure4() throws Exception {
        stringList.add("   ");
        try {
            ProjectFilterUtility.buildTypeNameInFilter(stringList);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>buildTypeNameInFilter(List names)</code> method.
     * <p>
     * names contain a null element.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildTypeNameInFilterFailure5() throws Exception {
        stringList.add(null);
        try {
            ProjectFilterUtility.buildTypeNameInFilter(stringList);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>buildCategoryIdEqualFilter(long categoryId)</code> method.
     * <p>
     * categoryId = 0
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildCategoryIdEqualFilterFailure1() throws Exception {
        try {
            ProjectFilterUtility.buildCategoryIdEqualFilter(0);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>buildCategoryIdEqualFilter(long categoryId)</code> method.
     * <p>
     * categoryId = -1
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildCategoryIdEqualFilterFailure2() throws Exception {
        try {
            ProjectFilterUtility.buildCategoryIdEqualFilter(-1);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>buildCategoryIdInFilter(List categoryIds)</code> method.
     * <p>
     * categoryIds is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildCategoryIdInFilterFailure1() throws Exception {
        try {
            ProjectFilterUtility.buildCategoryIdInFilter(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>buildCategoryIdInFilter(List categoryIds)</code> method.
     * <p>
     * categoryIds is empty.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildCategoryIdInFilterFailure2() throws Exception {
        try {
            ProjectFilterUtility.buildCategoryIdInFilter(new ArrayList());
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>buildCategoryIdInFilter(List categoryIds)</code> method.
     * <p>
     * categoryIds contain a instance that is not Long.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildCategoryIdInFilterFailure3() throws Exception {
        longList.add("string");
        try {
            ProjectFilterUtility.buildCategoryIdInFilter(longList);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>buildCategoryIdInFilter(List categoryIds)</code> method.
     * <p>
     * categoryIds contain a null element.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildCategoryIdInFilterFailure4() throws Exception {
        longList.add(null);
        try {
            ProjectFilterUtility.buildCategoryIdInFilter(longList);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>buildCategoryIdInFilter(List categoryIds)</code> method.
     * <p>
     * categoryIds contain a id <= 0
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildCategoryIdInFilterFailure5() throws Exception {
        longList.add(new Long(0));
        try {
            ProjectFilterUtility.buildCategoryIdInFilter(longList);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>buildCategoryNameEqualFilter(String name)</code> method.
     * <p>
     * name is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildCategoryNameEqualFilterFailure1() throws Exception {
        try {
            ProjectFilterUtility.buildCategoryNameEqualFilter(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>buildCategoryNameEqualFilter(String name)</code> method.
     * <p>
     * name is empty.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildCategoryNameEqualFilterFailure2() throws Exception {
        try {
            ProjectFilterUtility.buildCategoryNameEqualFilter("  ");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>buildCategoryNameInFilter(List names)</code> method.
     * <p>
     * names is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildCategoryNameInFilterFailure1() throws Exception {
        try {
            ProjectFilterUtility.buildCategoryNameInFilter(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>buildCategoryNameInFilter(List names)</code> method.
     * <p>
     * names is empty.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildCategoryNameInFilterFailure2() throws Exception {
        try {
            ProjectFilterUtility.buildCategoryNameInFilter(new ArrayList());
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>buildCategoryNameInFilter(List names)</code> method.
     * <p>
     * names contain a instance that is not String.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildCategoryNameInFilterFailure3() throws Exception {
        stringList.add(new Long(2));
        try {
            ProjectFilterUtility.buildCategoryNameInFilter(stringList);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>buildCategoryNameInFilter(List names)</code> method.
     * <p>
     * names contain a String that is empty.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildCategoryNameInFilterFailure4() throws Exception {
        stringList.add("   ");
        try {
            ProjectFilterUtility.buildCategoryNameInFilter(stringList);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>buildCategoryNameInFilter(List names)</code> method.
     * <p>
     * names contain a null element.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildCategoryNameInFilterFailure5() throws Exception {
        stringList.add(null);
        try {
            ProjectFilterUtility.buildCategoryNameInFilter(stringList);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>buildStatusIdEqualFilter(long statusId)</code> method.
     * <p>
     * statusId = 0
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildStatusIdEqualFilterFailure1() throws Exception {
        try {
            ProjectFilterUtility.buildStatusIdEqualFilter(0);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>buildStatusIdEqualFilter(long statusId)</code> method.
     * <p>
     * statusId = -1
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildStatusIdEqualFilterFailure2() throws Exception {
        try {
            ProjectFilterUtility.buildStatusIdEqualFilter(-1);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>buildStatusIdInFilter(List statusIds)</code> method.
     * <p>
     * statusIds is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildStatusIdInFilterFailure1() throws Exception {
        try {
            ProjectFilterUtility.buildStatusIdInFilter(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>buildStatusIdInFilter(List statusIds)</code> method.
     * <p>
     * statusIds is empty.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildStatusIdInFilterFailure2() throws Exception {
        try {
            ProjectFilterUtility.buildStatusIdInFilter(new ArrayList());
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>buildStatusIdInFilter(List statusIds)</code> method.
     * <p>
     * statusIds contain a instance that is not Long.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildStatusIdInFilterFailure3() throws Exception {
        longList.add("string");
        try {
            ProjectFilterUtility.buildStatusIdInFilter(longList);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>buildStatusIdInFilter(List statusIds)</code> method.
     * <p>
     * statusIds contain a null element.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildStatusIdInFilterFailure4() throws Exception {
        longList.add(null);
        try {
            ProjectFilterUtility.buildStatusIdInFilter(longList);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>buildStatusIdInFilter(List statusIds)</code> method.
     * <p>
     * statusIds contain a id <= 0
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildStatusIdInFilterFailure5() throws Exception {
        longList.add(new Long(0));
        try {
            ProjectFilterUtility.buildStatusIdInFilter(longList);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>buildStatusNameEqualFilter(String name)</code> method.
     * <p>
     * name is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildStatusNameEqualFilterFailure1() throws Exception {
        try {
            ProjectFilterUtility.buildStatusNameEqualFilter(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>buildStatusNameEqualFilter(String name)</code> method.
     * <p>
     * name is empty.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildStatusNameEqualFilterFailure2() throws Exception {
        try {
            ProjectFilterUtility.buildStatusNameEqualFilter("  ");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>buildStatusNameInFilter(List names)</code> method.
     * <p>
     * names is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildStatusNameInFilterFailure1() throws Exception {
        try {
            ProjectFilterUtility.buildStatusNameInFilter(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>buildStatusNameInFilter(List names)</code> method.
     * <p>
     * names is empty.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildStatusNameInFilterFailure2() throws Exception {
        try {
            ProjectFilterUtility.buildStatusNameInFilter(new ArrayList());
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>buildStatusNameInFilter(List names)</code> method.
     * <p>
     * names contain a instance that is not String.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildStatusNameInFilterFailure3() throws Exception {
        stringList.add(new Long(2));
        try {
            ProjectFilterUtility.buildStatusNameInFilter(stringList);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>buildStatusNameInFilter(List names)</code> method.
     * <p>
     * names contain a String that is empty.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildStatusNameInFilterFailure4() throws Exception {
        stringList.add("   ");
        try {
            ProjectFilterUtility.buildStatusNameInFilter(stringList);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>buildStatusNameInFilter(List names)</code> method.
     * <p>
     * names contain a null element.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildStatusNameInFilterFailure5() throws Exception {
        stringList.add(null);
        try {
            ProjectFilterUtility.buildStatusNameInFilter(stringList);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>buildProjectPropertyNameEqualFilter(String name)</code> method.
     * <p>
     * name is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildProjectPropertyNameEqualFilterFailure1() throws Exception {
        try {
            ProjectFilterUtility.buildProjectPropertyNameEqualFilter(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>buildProjectPropertyNameEqualFilter(String name)</code> method.
     * <p>
     * name is empty.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildProjectPropertyNameEqualFilterFailure2() throws Exception {
        try {
            ProjectFilterUtility.buildProjectPropertyNameEqualFilter("  ");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>buildProjectPropertyNameInFilter(List names)</code> method.
     * <p>
     * names is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildProjectPropertyNameInFilterFailure1() throws Exception {
        try {
            ProjectFilterUtility.buildProjectPropertyNameInFilter(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>buildProjectPropertyNameInFilter(List names)</code> method.
     * <p>
     * names is empty.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildProjectPropertyNameInFilterFailure2() throws Exception {
        try {
            ProjectFilterUtility.buildProjectPropertyNameInFilter(new ArrayList());
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>buildProjectPropertyNameInFilter(List names)</code> method.
     * <p>
     * names contain a instance that is not String.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildProjectPropertyNameInFilterFailure3() throws Exception {
        stringList.add(new Long(2));
        try {
            ProjectFilterUtility.buildProjectPropertyNameInFilter(stringList);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>buildProjectPropertyNameInFilter(List names)</code> method.
     * <p>
     * names contain a String that is empty.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildProjectPropertyNameInFilterFailure4() throws Exception {
        stringList.add("   ");
        try {
            ProjectFilterUtility.buildProjectPropertyNameInFilter(stringList);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>buildProjectPropertyNameInFilter(List names)</code> method.
     * <p>
     * names contain a null element.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildProjectPropertyNameInFilterFailure5() throws Exception {
        stringList.add(null);
        try {
            ProjectFilterUtility.buildProjectPropertyNameInFilter(stringList);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>buildProjectPropertyValueEqualFilter(String value)</code> method.
     * <p>
     * value is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildProjectPropertyValueEqualFilterFailure1() throws Exception {
        try {
            ProjectFilterUtility.buildProjectPropertyValueEqualFilter(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>buildProjectPropertyValueEqualFilter(String value)</code> method.
     * <p>
     * value is empty.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildProjectPropertyValueEqualFilterFailure2() throws Exception {
        try {
            ProjectFilterUtility.buildProjectPropertyValueEqualFilter("  ");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>buildProjectPropertyValueInFilter(List values)</code> method.
     * <p>
     * values is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildProjectPropertyValueInFilterFailure1() throws Exception {
        try {
            ProjectFilterUtility.buildProjectPropertyValueInFilter(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>buildProjectPropertyValueInFilter(List values)</code> method.
     * <p>
     * values is empty.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildProjectPropertyValueInFilterFailure2() throws Exception {
        try {
            ProjectFilterUtility.buildProjectPropertyValueInFilter(new ArrayList());
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>buildProjectPropertyValueInFilter(List values)</code> method.
     * <p>
     * values contain a instance that is not String.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildProjectPropertyValueInFilterFailure3() throws Exception {
        stringList.add(new Long(2));
        try {
            ProjectFilterUtility.buildProjectPropertyValueInFilter(stringList);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>buildProjectPropertyValueInFilter(List values)</code> method.
     * <p>
     * values contain a String that is empty.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildProjectPropertyValueInFilterFailure4() throws Exception {
        stringList.add("   ");
        try {
            ProjectFilterUtility.buildProjectPropertyValueInFilter(stringList);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>buildProjectPropertyValueInFilter(List values)</code> method.
     * <p>
     * values contain a null element.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildProjectPropertyValueInFilterFailure5() throws Exception {
        stringList.add(null);
        try {
            ProjectFilterUtility.buildProjectPropertyValueInFilter(stringList);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>buildProjectPropertyEqualFilter(String name, String value)</code> method.
     * <p>
     * name is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildProjectPropertyEqualFilterFailure1() throws Exception {
        try {
            ProjectFilterUtility.buildProjectPropertyEqualFilter(null, "value");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>buildProjectPropertyEqualFilter(String name, String value)</code> method.
     * <p>
     * name is empty.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildProjectPropertyEqualFilterFailure2() throws Exception {
        try {
            ProjectFilterUtility.buildProjectPropertyEqualFilter("  ", "value");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>buildProjectPropertyEqualFilter(String name, String value)</code> method.
     * <p>
     * value is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildProjectPropertyEqualFilterFailure3() throws Exception {
        try {
            ProjectFilterUtility.buildProjectPropertyEqualFilter("name", null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>buildProjectPropertyEqualFilter(String name, String value)</code> method.
     * <p>
     * value is empty.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildProjectPropertyEqualFilterFailure4() throws Exception {
        try {
            ProjectFilterUtility.buildProjectPropertyEqualFilter("name", "  ");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>buildProjectResourcePropertyNameEqualFilter(String name)</code> method.
     * <p>
     * name is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildProjectResourcePropertyNameEqualFilterFailure1() throws Exception {
        try {
            ProjectFilterUtility.buildProjectResourcePropertyNameEqualFilter(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>buildProjectResourcePropertyNameEqualFilter(String name)</code> method.
     * <p>
     * name is empty.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildProjectResourcePropertyNameEqualFilterFailure2() throws Exception {
        try {
            ProjectFilterUtility.buildProjectResourcePropertyNameEqualFilter("  ");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>buildProjectResourcePropertyNameInFilter(List names)</code> method.
     * <p>
     * names is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildProjectResourcePropertyNameInFilterFailure1() throws Exception {
        try {
            ProjectFilterUtility.buildProjectResourcePropertyNameInFilter(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>buildProjectResourcePropertyNameInFilter(List names)</code> method.
     * <p>
     * names is empty.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildProjectResourcePropertyNameInFilterFailure2() throws Exception {
        try {
            ProjectFilterUtility.buildProjectResourcePropertyNameInFilter(new ArrayList());
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>buildProjectResourcePropertyNameInFilter(List names)</code> method.
     * <p>
     * names contain a instance that is not String.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildProjectResourcePropertyNameInFilterFailure3() throws Exception {
        stringList.add(new Long(2));
        try {
            ProjectFilterUtility.buildProjectResourcePropertyNameInFilter(stringList);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>buildProjectResourcePropertyNameInFilter(List names)</code> method.
     * <p>
     * names contain a String that is empty.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildProjectResourcePropertyNameInFilterFailure4() throws Exception {
        stringList.add("   ");
        try {
            ProjectFilterUtility.buildProjectResourcePropertyNameInFilter(stringList);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>buildProjectResourcePropertyNameInFilter(List names)</code> method.
     * <p>
     * names contain a null element.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildProjectResourcePropertyNameInFilterFailure5() throws Exception {
        stringList.add(null);
        try {
            ProjectFilterUtility.buildProjectResourcePropertyNameInFilter(stringList);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>buildProjectResourcePropertyValueEqualFilter(String value)</code> method.
     * <p>
     * value is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildProjectResourcePropertyValueEqualFilterFailure1() throws Exception {
        try {
            ProjectFilterUtility.buildProjectResourcePropertyValueEqualFilter(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>buildProjectResourcePropertyValueEqualFilter(String value)</code> method.
     * <p>
     * value is empty.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildProjectResourcePropertyValueEqualFilterFailure2() throws Exception {
        try {
            ProjectFilterUtility.buildProjectResourcePropertyValueEqualFilter("  ");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>buildProjectResourcePropertyValueInFilter(List values)</code> method.
     * <p>
     * values is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildProjectResourcePropertyValueInFilterFailure1() throws Exception {
        try {
            ProjectFilterUtility.buildProjectResourcePropertyValueInFilter(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>buildProjectResourcePropertyValueInFilter(List values)</code> method.
     * <p>
     * values is empty.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildProjectResourcePropertyValueInFilterFailure2() throws Exception {
        try {
            ProjectFilterUtility.buildProjectResourcePropertyValueInFilter(new ArrayList());
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>buildProjectResourcePropertyValueInFilter(List values)</code> method.
     * <p>
     * values contain a instance that is not String.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildProjectResourcePropertyValueInFilterFailure3() throws Exception {
        stringList.add(new Long(2));
        try {
            ProjectFilterUtility.buildProjectResourcePropertyValueInFilter(stringList);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>buildProjectResourcePropertyValueInFilter(List values)</code> method.
     * <p>
     * values contain a String that is empty.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildProjectResourcePropertyValueInFilterFailure4() throws Exception {
        stringList.add("   ");
        try {
            ProjectFilterUtility.buildProjectResourcePropertyValueInFilter(stringList);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>buildProjectResourcePropertyValueInFilter(List values)</code> method.
     * <p>
     * values contain a null element.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildProjectResourcePropertyValueInFilterFailure5() throws Exception {
        stringList.add(null);
        try {
            ProjectFilterUtility.buildProjectResourcePropertyValueInFilter(stringList);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>buildProjectPropertyResourceEqualFilter(String name, String value)</code> method.
     * <p>
     * name is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildProjectPropertyResourceEqualFilterFailure1() throws Exception {
        try {
            ProjectFilterUtility.buildProjectPropertyResourceEqualFilter(null, "value");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>buildProjectPropertyResourceEqualFilter(String name, String value)</code> method.
     * <p>
     * name is empty.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildProjectPropertyResourceEqualFilterFailure2() throws Exception {
        try {
            ProjectFilterUtility.buildProjectPropertyResourceEqualFilter("  ", "value");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>buildProjectPropertyResourceEqualFilter(String name, String value)</code> method.
     * <p>
     * value is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildProjectPropertyResourceEqualFilterFailure3() throws Exception {
        try {
            ProjectFilterUtility.buildProjectPropertyResourceEqualFilter("name", null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>buildProjectPropertyResourceEqualFilter(String name, String value)</code> method.
     * <p>
     * value is empty.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildProjectPropertyResourceEqualFilterFailure4() throws Exception {
        try {
            ProjectFilterUtility.buildProjectPropertyResourceEqualFilter("name", "  ");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>buildAndFilter(Filter f1, Filter f2)</code> method.
     * <p>
     * f1 is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildAndFilterFailure1() throws Exception {
        try {
            ProjectFilterUtility.buildAndFilter(null, f2);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>buildAndFilter(Filter f1, Filter f2)</code> method.
     * <p>
     * f2 is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildAndFilterFailure2() throws Exception {
        try {
            ProjectFilterUtility.buildAndFilter(f1, null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>buildOrFilter(Filter f1, Filter f2)</code> method.
     * <p>
     * f1 is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildOrFilterFailure1() throws Exception {
        try {
            ProjectFilterUtility.buildOrFilter(null, f2);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>buildOrFilter(Filter f1, Filter f2)</code> method.
     * <p>
     * f2 is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildOrFilterFailure2() throws Exception {
        try {
            ProjectFilterUtility.buildOrFilter(f1, null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>buildNotFilter(Filter f1)</code> method.
     * <p>
     * f1 is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testBuildNotFilterFailure() throws Exception {
        try {
            ProjectFilterUtility.buildNotFilter(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }
}
