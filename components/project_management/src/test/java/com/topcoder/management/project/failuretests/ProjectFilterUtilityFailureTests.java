/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project.failuretests;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.topcoder.management.project.ProjectFilterUtility;
import com.topcoder.search.builder.filter.EqualToFilter;

import junit.framework.TestCase;

/**
 * Failure tests for <code>ProjectFilterUtility</code> class.
 *
 * @author vividmxx
 * @version 1.0
 */
public class ProjectFilterUtilityFailureTests extends TestCase {

    /**
     * Test the method <code>buildTypeIdEqualFilter(long)</code> with negative typeId. IllegalArgumentException should
     * be thrown.
     */
    public void testBuildTypeIdEqualFilterWithNegativeTypeId() {
        try {
            ProjectFilterUtility.buildTypeIdEqualFilter(-1);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>buildTypeIdEqualFilter(long)</code> with zero typeId. IllegalArgumentException should be
     * thrown.
     */
    public void testBuildTypeIdEqualFilterWithZeroTypeId() {
        try {
            ProjectFilterUtility.buildTypeIdEqualFilter(0);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>buildTypeIdInFilter(List)</code> with null typeIds. IllegalArgumentException should be
     * thrown.
     */
    public void testBuildTypeIdInFilterWithNullTypeIds() {
        try {
            ProjectFilterUtility.buildTypeIdInFilter(null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>buildTypeIdInFilter(List)</code> with empty typeIds. IllegalArgumentException should be
     * thrown.
     */
    public void testBuildTypeIdInFilterWithEmptyTypeIds() {
        try {
            ProjectFilterUtility.buildTypeIdInFilter(new ArrayList());
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>buildTypeIdInFilter(List)</code> with invalid typeIds which contains invalid element with
     * non-Long type. IllegalArgumentException should be thrown.
     */
    public void testBuildTypeIdInFilterWithNonLongTypeId() {
        try {
            List list = new ArrayList();
            list.add(new Long(1));
            list.add(new Date());
            ProjectFilterUtility.buildTypeIdInFilter(list);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>buildTypeIdInFilter(List)</code> with invalid typeIds which contains negative value
     * element. IllegalArgumentException should be thrown.
     */
    public void testBuildTypeIdInFilterWithNagetiveTypeId() {
        try {
            List list = new ArrayList();
            list.add(new Long(-1));
            ProjectFilterUtility.buildTypeIdInFilter(list);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>buildTypeIdInFilter(List)</code> with invalid typeIds which contains zero value element.
     * IllegalArgumentException should be thrown.
     */
    public void testBuildTypeIdInFilterWithZeroTypeId() {
        try {
            List list = new ArrayList();
            list.add(new Long(0));
            ProjectFilterUtility.buildTypeIdInFilter(list);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>buildTypeNameEqualFilter(String)</code> with null name. IllegalArgumentException should
     * be thrown.
     */
    public void testBuildTypeNameEqualFilterWithNullName() {
        try {
            ProjectFilterUtility.buildTypeNameEqualFilter(null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>buildTypeNameEqualFilter(String)</code> with empty name. IllegalArgumentException should
     * be thrown.
     */
    public void testBuildTypeNameEqualFilterWithEmptyName() {
        try {
            ProjectFilterUtility.buildTypeNameEqualFilter(" ");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>buildTypeNameInFilter(List)</code> with null names. IllegalArgumentException should be
     * thrown.
     */
    public void testBuildTypeNameInFilterWithNullNames() {
        try {
            ProjectFilterUtility.buildTypeNameInFilter(null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>buildTypeNameInFilter(List)</code> with empty names. IllegalArgumentException should be
     * thrown.
     */
    public void testBuildTypeNameInFilterWithEmptyNames() {
        try {
            ProjectFilterUtility.buildTypeNameInFilter(new ArrayList());
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>buildTypeNameInFilter(List)</code> with invalid names which contains element with
     * non-String type. IllegalArgumentException should be thrown.
     */
    public void testBuildTypeNameInFilterWithNonStringName() {
        try {
            List list = new ArrayList();
            list.add(new String());
            list.add(new Date());
            ProjectFilterUtility.buildTypeNameInFilter(list);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>buildTypeNameInFilter(List)</code> with invalid names which contains null element.
     * IllegalArgumentException should be thrown.
     */
    public void testBuildTypeNameInFilterWithNullName() {
        try {
            List list = new ArrayList();
            list.add(null);
            ProjectFilterUtility.buildTypeNameInFilter(list);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>buildTypeNameInFilter(List)</code> with invalid names which contains empty element.
     * IllegalArgumentException should be thrown.
     */
    public void testBuildTypeNameInFilterWithEmptyName() {
        try {
            List list = new ArrayList();
            list.add(" ");
            ProjectFilterUtility.buildTypeNameInFilter(list);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>buildCategoryIdEqualFilter(long)</code> with negative categoryId.
     * IllegalArgumentException should be thrown.
     */
    public void testBuildCategoryIdEqualFilterWithNegativeCategoryId() {
        try {
            ProjectFilterUtility.buildCategoryIdEqualFilter(-1);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>buildCategoryIdEqualFilter(long)</code> with zero categoryId. IllegalArgumentException
     * should be thrown.
     */
    public void testBuildCategoryIdEqualFilterWithZeroCategoryId() {
        try {
            ProjectFilterUtility.buildCategoryIdEqualFilter(0);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>buildCategoryIdInFilter(List)</code> with null categoryIds. IllegalArgumentException
     * should be thrown.
     */
    public void testBuildCategoryIdInFilterWithNullCategoryIds() {
        try {
            ProjectFilterUtility.buildCategoryIdInFilter(null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>buildCategoryIdInFilter(List)</code> with empty categoryIds. IllegalArgumentException
     * should be thrown.
     */
    public void testBuildCategoryIdInFilterWithEmptyCategoryIds() {
        try {
            ProjectFilterUtility.buildCategoryIdInFilter(new ArrayList());
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>buildCategoryIdInFilter(List)</code> with invalid categoryIds which contains invalid
     * element with non-Long type. IllegalArgumentException should be thrown.
     */
    public void testBuildCategoryIdInFilterWithNonLongCategoryId() {
        try {
            List list = new ArrayList();
            list.add(new Long(1));
            list.add(new Date());
            ProjectFilterUtility.buildCategoryIdInFilter(list);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>buildCategoryIdInFilter(List)</code> with invalid categoryIds which contains negative
     * value element. IllegalArgumentException should be thrown.
     */
    public void testBuildCategoryIdInFilterWithNagetiveCategoryId() {
        try {
            List list = new ArrayList();
            list.add(new Long(-1));
            ProjectFilterUtility.buildCategoryIdInFilter(list);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>buildCategoryIdInFilter(List)</code> with invalid categoryIds which contains zero value
     * element. IllegalArgumentException should be thrown.
     */
    public void testBuildCategoryIdInFilterWithZeroCategoryId() {
        try {
            List list = new ArrayList();
            list.add(new Long(0));
            ProjectFilterUtility.buildCategoryIdInFilter(list);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>buildCategoryNameEqualFilter(String)</code> with null name. IllegalArgumentException
     * should be thrown.
     */
    public void testBuildCategoryNameEqualFilterWithNullName() {
        try {
            ProjectFilterUtility.buildCategoryNameEqualFilter(null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>buildCategoryNameEqualFilter(String)</code> with empty name. IllegalArgumentException
     * should be thrown.
     */
    public void testBuildCategoryNameEqualFilterWithEmptyName() {
        try {
            ProjectFilterUtility.buildCategoryNameEqualFilter(" ");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>buildCategoryNameInFilter(List)</code> with null names. IllegalArgumentException should
     * be thrown.
     */
    public void testBuildCategoryNameInFilterWithNullNames() {
        try {
            ProjectFilterUtility.buildCategoryNameInFilter(null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>buildCategoryNameInFilter(List)</code> with empty names. IllegalArgumentException should
     * be thrown.
     */
    public void testBuildCategoryNameInFilterWithEmptyNames() {
        try {
            ProjectFilterUtility.buildCategoryNameInFilter(new ArrayList());
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>buildCategoryNameInFilter(List)</code> with invalid names which contains element with
     * non-String type. IllegalArgumentException should be thrown.
     */
    public void testBuildCategoryNameInFilterWithNonStringName() {
        try {
            List list = new ArrayList();
            list.add(new String());
            list.add(new Date());
            ProjectFilterUtility.buildCategoryNameInFilter(list);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>buildCategoryNameInFilter(List)</code> with invalid names which contains null element.
     * IllegalArgumentException should be thrown.
     */
    public void testBuildCategoryNameInFilterWithNullName() {
        try {
            List list = new ArrayList();
            list.add(null);
            ProjectFilterUtility.buildCategoryNameInFilter(list);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>buildCategoryNameInFilter(List)</code> with invalid names which contains empty element.
     * IllegalArgumentException should be thrown.
     */
    public void testBuildCategoryNameInFilterWithEmptyName() {
        try {
            List list = new ArrayList();
            list.add(" ");
            ProjectFilterUtility.buildCategoryNameInFilter(list);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>buildStatusIdEqualFilter(long)</code> with negative statusId. IllegalArgumentException
     * should be thrown.
     */
    public void testBuildStatusIdEqualFilterWithNegativeStatusId() {
        try {
            ProjectFilterUtility.buildStatusIdEqualFilter(-1);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>buildStatusIdEqualFilter(long)</code> with zero statusId. IllegalArgumentException should
     * be thrown.
     */
    public void testBuildStatusIdEqualFilterWithZeroStatusId() {
        try {
            ProjectFilterUtility.buildStatusIdEqualFilter(0);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>buildStatusIdInFilter(List)</code> with null statusIds. IllegalArgumentException should
     * be thrown.
     */
    public void testBuildStatusIdInFilterWithNullStatusIds() {
        try {
            ProjectFilterUtility.buildStatusIdInFilter(null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>buildStatusIdInFilter(List)</code> with empty statusIds. IllegalArgumentException should
     * be thrown.
     */
    public void testBuildStatusIdInFilterWithEmptyStatusIds() {
        try {
            ProjectFilterUtility.buildStatusIdInFilter(new ArrayList());
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>buildStatusIdInFilter(List)</code> with invalid statusIds which contains invalid element
     * with non-Long type. IllegalArgumentException should be thrown.
     */
    public void testBuildStatusIdInFilterWithNonLongStatusId() {
        try {
            List list = new ArrayList();
            list.add(new Long(1));
            list.add(new Date());
            ProjectFilterUtility.buildStatusIdInFilter(list);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>buildStatusIdInFilter(List)</code> with invalid statusIds which contains negative value
     * element. IllegalArgumentException should be thrown.
     */
    public void testBuildStatusIdInFilterWithNagetiveStatusId() {
        try {
            List list = new ArrayList();
            list.add(new Long(-1));
            ProjectFilterUtility.buildStatusIdInFilter(list);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>buildStatusIdInFilter(List)</code> with invalid statusIds which contains zero value
     * element. IllegalArgumentException should be thrown.
     */
    public void testBuildStatusIdInFilterWithZeroStatusId() {
        try {
            List list = new ArrayList();
            list.add(new Long(0));
            ProjectFilterUtility.buildStatusIdInFilter(list);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>buildStatusNameEqualFilter(String)</code> with null name. IllegalArgumentException should
     * be thrown.
     */
    public void testBuildStatusNameEqualFilterWithNullName() {
        try {
            ProjectFilterUtility.buildStatusNameEqualFilter(null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>buildStatusNameEqualFilter(String)</code> with empty name. IllegalArgumentException
     * should be thrown.
     */
    public void testBuildStatusNameEqualFilterWithEmptyName() {
        try {
            ProjectFilterUtility.buildStatusNameEqualFilter(" ");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>buildStatusNameInFilter(List)</code> with null names. IllegalArgumentException should be
     * thrown.
     */
    public void testBuildStatusNameInFilterWithNullNames() {
        try {
            ProjectFilterUtility.buildStatusNameInFilter(null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>buildStatusNameInFilter(List)</code> with empty names. IllegalArgumentException should be
     * thrown.
     */
    public void testBuildStatusNameInFilterWithEmptyNames() {
        try {
            ProjectFilterUtility.buildStatusNameInFilter(new ArrayList());
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>buildStatusNameInFilter(List)</code> with invalid names which contains element with
     * non-String type. IllegalArgumentException should be thrown.
     */
    public void testBuildStatusNameInFilterWithNonStringName() {
        try {
            List list = new ArrayList();
            list.add(new String());
            list.add(new Date());
            ProjectFilterUtility.buildStatusNameInFilter(list);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>buildStatusNameInFilter(List)</code> with invalid names which contains null element.
     * IllegalArgumentException should be thrown.
     */
    public void testBuildStatusNameInFilterWithNullName() {
        try {
            List list = new ArrayList();
            list.add(null);
            ProjectFilterUtility.buildStatusNameInFilter(list);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>buildStatusNameInFilter(List)</code> with invalid names which contains empty element.
     * IllegalArgumentException should be thrown.
     */
    public void testBuildStatusNameInFilterWithEmptyName() {
        try {
            List list = new ArrayList();
            list.add(" ");
            ProjectFilterUtility.buildStatusNameInFilter(list);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>buildProjectPropertyNameEqualFilter(String)</code> with null name.
     * IllegalArgumentException should be thrown.
     */
    public void testBuildProjectPropertyNameEqualFilterWithNullName() {
        try {
            ProjectFilterUtility.buildProjectPropertyNameEqualFilter(null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>buildProjectPropertyNameEqualFilter(String)</code> with empty name.
     * IllegalArgumentException should be thrown.
     */
    public void testBuildProjectPropertyNameEqualFilterWithEmptyName() {
        try {
            ProjectFilterUtility.buildProjectPropertyNameEqualFilter(" ");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>buildProjectPropertyNameInFilter(List)</code> with null names. IllegalArgumentException
     * should be thrown.
     */
    public void testBuildProjectPropertyNameInFilterWithNullNames() {
        try {
            ProjectFilterUtility.buildProjectPropertyNameInFilter(null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>buildProjectPropertyNameInFilter(List)</code> with empty names. IllegalArgumentException
     * should be thrown.
     */
    public void testBuildProjectPropertyNameInFilterWithEmptyNames() {
        try {
            ProjectFilterUtility.buildProjectPropertyNameInFilter(new ArrayList());
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>buildProjectPropertyNameInFilter(List)</code> with invalid names which contains element
     * with non-String type. IllegalArgumentException should be thrown.
     */
    public void testBuildProjectPropertyNameInFilterWithNonStringName() {
        try {
            List list = new ArrayList();
            list.add(new String());
            list.add(new Date());
            ProjectFilterUtility.buildProjectPropertyNameInFilter(list);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>buildProjectPropertyNameInFilter(List)</code> with invalid names which contains null
     * element. IllegalArgumentException should be thrown.
     */
    public void testBuildProjectPropertyNameInFilterWithNullName() {
        try {
            List list = new ArrayList();
            list.add(null);
            ProjectFilterUtility.buildProjectPropertyNameInFilter(list);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>buildProjectPropertyNameInFilter(List)</code> with invalid names which contains empty
     * element. IllegalArgumentException should be thrown.
     */
    public void testBuildProjectPropertyNameInFilterWithEmptyName() {
        try {
            List list = new ArrayList();
            list.add(" ");
            ProjectFilterUtility.buildProjectPropertyNameInFilter(list);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>buildProjectPropertyValueEqualFilter(String)</code> with null value.
     * IllegalArgumentException should be thrown.
     */
    public void testBuildProjectPropertyValueEqualFilterWithNullValue() {
        try {
            ProjectFilterUtility.buildProjectPropertyValueEqualFilter(null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>buildProjectPropertyValueEqualFilter(String)</code> with empty value.
     * IllegalArgumentException should be thrown.
     */
    public void testBuildProjectPropertyValueEqualFilterWithEmptyValue() {
        try {
            ProjectFilterUtility.buildProjectPropertyValueEqualFilter(" ");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>buildProjectPropertyValueInFilter(List)</code> with null values. IllegalArgumentException
     * should be thrown.
     */
    public void testBuildProjectPropertyValueInFilterWithNullValues() {
        try {
            ProjectFilterUtility.buildProjectPropertyValueInFilter(null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>buildProjectPropertyValueInFilter(List)</code> with empty values.
     * IllegalArgumentException should be thrown.
     */
    public void testBuildProjectPropertyValueInFilterWithEmptyValues() {
        try {
            ProjectFilterUtility.buildProjectPropertyValueInFilter(new ArrayList());
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>buildProjectPropertyValueInFilter(List)</code> with invalid values which contains element
     * with non-String type. IllegalArgumentException should be thrown.
     */
    public void testBuildProjectPropertyValueInFilterWithNonStringValue() {
        try {
            List list = new ArrayList();
            list.add(new String());
            list.add(new Date());
            ProjectFilterUtility.buildProjectPropertyValueInFilter(list);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>buildProjectPropertyValueInFilter(List)</code> with invalid values which contains null
     * element. IllegalArgumentException should be thrown.
     */
    public void testBuildProjectPropertyValueInFilterWithNullValue() {
        try {
            List list = new ArrayList();
            list.add(null);
            ProjectFilterUtility.buildProjectPropertyValueInFilter(list);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>buildProjectPropertyValueInFilter(List)</code> with invalid values which contains empty
     * element. IllegalArgumentException should be thrown.
     */
    public void testBuildProjectPropertyValueInFilterWithEmptyValue() {
        try {
            List list = new ArrayList();
            list.add(" ");
            ProjectFilterUtility.buildProjectPropertyValueInFilter(list);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>buildProjectPropertyEqualFilter(String, String)</code> with null name.
     * IllegalArgumentException should be thrown.
     */
    public void testBuildProjectPropertyEqualFilterWithNullName() {
        try {
            ProjectFilterUtility.buildProjectPropertyEqualFilter(null, "value");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>buildProjectPropertyEqualFilter(String, String)</code> with empty name.
     * IllegalArgumentException should be thrown.
     */
    public void testBuildProjectPropertyEqualFilterWithEmptyName() {
        try {
            ProjectFilterUtility.buildProjectPropertyEqualFilter(" ", "value");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>buildProjectPropertyEqualFilter(String, String)</code> with null value.
     * IllegalArgumentException should be thrown.
     */
    public void testBuildProjectPropertyEqualFilterWithNullValue() {
        try {
            ProjectFilterUtility.buildProjectPropertyEqualFilter("name", null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>buildProjectPropertyEqualFilter(String, String)</code> with empty value.
     * IllegalArgumentException should be thrown.
     */
    public void testBuildProjectPropertyEqualFilterWithEmptyValue() {
        try {
            ProjectFilterUtility.buildProjectPropertyEqualFilter("name", " ");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>buildProjectResourcePropertyNameEqualFilter(String)</code> with null name.
     * IllegalArgumentException should be thrown.
     */
    public void testBuildProjectResourcePropertyNameEqualFilterWithNullName() {
        try {
            ProjectFilterUtility.buildProjectResourcePropertyNameEqualFilter(null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>buildProjectResourcePropertyNameEqualFilter(String)</code> with empty name.
     * IllegalArgumentException should be thrown.
     */
    public void testBuildProjectResourcePropertyNameEqualFilterWithEmptyName() {
        try {
            ProjectFilterUtility.buildProjectResourcePropertyNameEqualFilter(" ");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>buildProjectResourcePropertyNameInFilter(List)</code> with null names.
     * IllegalArgumentException should be thrown.
     */
    public void testBuildProjectResourcePropertyNameInFilterWithNullNames() {
        try {
            ProjectFilterUtility.buildProjectResourcePropertyNameInFilter(null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>buildProjectResourcePropertyNameInFilter(List)</code> with empty names.
     * IllegalArgumentException should be thrown.
     */
    public void testBuildProjectResourcePropertyNameInFilterWithEmptyNames() {
        try {
            ProjectFilterUtility.buildProjectResourcePropertyNameInFilter(new ArrayList());
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>buildProjectResourcePropertyNameInFilter(List)</code> with invalid names which contains
     * element with non-String type. IllegalArgumentException should be thrown.
     */
    public void testBuildProjectResourcePropertyNameInFilterWithNonStringName() {
        try {
            List list = new ArrayList();
            list.add(new String());
            list.add(new Date());
            ProjectFilterUtility.buildProjectResourcePropertyNameInFilter(list);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>buildProjectResourcePropertyNameInFilter(List)</code> with invalid names which contains
     * null element. IllegalArgumentException should be thrown.
     */
    public void testBuildProjectResourcePropertyNameInFilterWithNullName() {
        try {
            List list = new ArrayList();
            list.add(null);
            ProjectFilterUtility.buildProjectResourcePropertyNameInFilter(list);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>buildProjectResourcePropertyNameInFilter(List)</code> with invalid names which contains
     * empty element. IllegalArgumentException should be thrown.
     */
    public void testBuildProjectResourcePropertyNameInFilterWithEmptyName() {
        try {
            List list = new ArrayList();
            list.add(" ");
            ProjectFilterUtility.buildProjectResourcePropertyNameInFilter(list);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>buildProjectResourcePropertyValueEqualFilter(String)</code> with null value.
     * IllegalArgumentException should be thrown.
     */
    public void testBuildProjectResourcePropertyValueEqualFilterWithNullValue() {
        try {
            ProjectFilterUtility.buildProjectResourcePropertyValueEqualFilter(null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>buildProjectResourcePropertyValueEqualFilter(String)</code> with empty value.
     * IllegalArgumentException should be thrown.
     */
    public void testBuildProjectResourcePropertyValueEqualFilterWithEmptyValue() {
        try {
            ProjectFilterUtility.buildProjectResourcePropertyValueEqualFilter(" ");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>buildProjectResourcePropertyValueInFilter(List)</code> with null values.
     * IllegalArgumentException should be thrown.
     */
    public void testBuildProjectResourcePropertyValueInFilterWithNullValues() {
        try {
            ProjectFilterUtility.buildProjectResourcePropertyValueInFilter(null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>buildProjectResourcePropertyValueInFilter(List)</code> with empty values.
     * IllegalArgumentException should be thrown.
     */
    public void testBuildProjectResourcePropertyValueInFilterWithEmptyValues() {
        try {
            ProjectFilterUtility.buildProjectResourcePropertyValueInFilter(new ArrayList());
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>buildProjectResourcePropertyValueInFilter(List)</code> with invalid values which contains
     * element with non-String type. IllegalArgumentException should be thrown.
     */
    public void testBuildProjectResourcePropertyValueInFilterWithNonStringValue() {
        try {
            List list = new ArrayList();
            list.add(new String());
            list.add(new Date());
            ProjectFilterUtility.buildProjectResourcePropertyValueInFilter(list);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>buildProjectResourcePropertyValueInFilter(List)</code> with invalid values which contains
     * null element. IllegalArgumentException should be thrown.
     */
    public void testBuildProjectResourcePropertyValueInFilterWithNullValue() {
        try {
            List list = new ArrayList();
            list.add(null);
            ProjectFilterUtility.buildProjectResourcePropertyValueInFilter(list);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>buildProjectResourcePropertyValueInFilter(List)</code> with invalid values which contains
     * empty element. IllegalArgumentException should be thrown.
     */
    public void testBuildProjectResourcePropertyValueInFilterWithEmptyValue() {
        try {
            List list = new ArrayList();
            list.add(" ");
            ProjectFilterUtility.buildProjectResourcePropertyValueInFilter(list);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>buildProjectPropertyResourceEqualFilter(String, String)</code> with null name.
     * IllegalArgumentException should be thrown.
     */
    public void testBuildProjectPropertyResourceEqualFilterWithNullName() {
        try {
            ProjectFilterUtility.buildProjectPropertyResourceEqualFilter(null, "value");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>buildProjectPropertyResourceEqualFilter(String, String)</code> with empty name.
     * IllegalArgumentException should be thrown.
     */
    public void testBuildProjectPropertyResourceEqualFilterWithEmptyName() {
        try {
            ProjectFilterUtility.buildProjectPropertyResourceEqualFilter(" ", "value");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>buildProjectPropertyResourceEqualFilter(String, String)</code> with null value.
     * IllegalArgumentException should be thrown.
     */
    public void testBuildProjectPropertyResourceEqualFilterWithNullValue() {
        try {
            ProjectFilterUtility.buildProjectPropertyResourceEqualFilter("name", null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>buildProjectPropertyResourceEqualFilter(String, String)</code> with empty value.
     * IllegalArgumentException should be thrown.
     */
    public void testBuildProjectPropertyResourceEqualFilterWithEmptyValue() {
        try {
            ProjectFilterUtility.buildProjectPropertyResourceEqualFilter("name", " ");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>buildAndFilter(Filter, Filter)</code> with null f1, IllegalArgumentException should be
     * thrown.
     */
    public void testBuildAndFilterWithNullF1() {
        try {
            ProjectFilterUtility.buildAndFilter(null, new EqualToFilter("committed", new Integer(1)));
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>buildAndFilter(Filter, Filter)</code> with null f2, IllegalArgumentException should be
     * thrown.
     */
    public void testBuildAndFilterWithNullF2() {
        try {
            ProjectFilterUtility.buildAndFilter(new EqualToFilter("committed", new Integer(1)), null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>buildOrFilter(Filter, Filter)</code> with null f1, IllegalArgumentException should be
     * thrown.
     */
    public void testBuildOrFilterWithNullF1() {
        try {
            ProjectFilterUtility.buildOrFilter(null, new EqualToFilter("committed", new Integer(1)));
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>buildOrFilter(Filter, Filter)</code> with null f2, IllegalArgumentException should be
     * thrown.
     */
    public void testBuildOrFilterWithNullF2() {
        try {
            ProjectFilterUtility.buildOrFilter(new EqualToFilter("committed", new Integer(1)), null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>buildNotFilter(Filter)</code> with null f1, IllegalArgumentException should be thrown.
     */
    public void testBuildNotFilterWithNullF1() {
        try {
            ProjectFilterUtility.buildNotFilter(null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }
}
