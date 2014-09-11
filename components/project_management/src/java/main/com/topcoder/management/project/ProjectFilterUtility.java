/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project;

import com.topcoder.search.builder.SearchBundle;
import com.topcoder.search.builder.filter.Filter;

import java.util.Iterator;
import java.util.List;

/**
 * <p>
 * This class contains methods to build filter instances to use in project
 * searching. It can build filter to search for project based on various
 * criteria such as:
 * </p>
 * <ul>
 * <li>Project type id</li>
 * <li>Project type name</li>
 * <li>Project category id</li>
 * <li>Project category name</li>
 * <li>Project status id</li>
 * <li>Project status name</li>
 * <li>Project property name</li>
 * <li>Project property value</li>
 * <li>Project resource property name</li>
 * <li>Project resource property value</li>
 * </ul>
 * Besides, this class also provides method to combine any of the above filter
 * to make complex filters. This class is used be the client to create search
 * filter. The created filter is used in SearchProjects() method of
 * ProjectManager.
 * <p>
 * Thread safety: This class is immutable and thread safe.
 * </p>
 * @author tuenm, iamajia
 * @version 1.0
 */
public class ProjectFilterUtility {

    /**
     * Represents the alias for project type id column. It is used to build
     * search filter for this column. This constant also defined in
     * configuration settings for search builder.
     */
    public static final String PROJECT_TYPE_ID = "ProjectTypeID";

    /**
     * Represents the alias for project type name column. It is used to build
     * search filter for this column. This constant also defined in
     * configuration settings for search builder.
     */
    public static final String PROJECT_TYPE_NAME = "ProjectTypeName";

    /**
     * Represents the alias for project category id column. It is used to build
     * search filter for this column. This constant also defined in
     * configuration settings for search builder.
     */
    public static final String PROJECT_CATEGORY_ID = "ProjectCategoryID";

    /**
     * Represents the alias for project category name column. It is used to
     * build search filter for this column. This constant also defined in
     * configuration settings for search builder.
     */
    public static final String PROJECT_CATEGORY_NAME = "ProjectCategoryName";

    /**
     * Represents the alias for project status id column. It is used to build
     * search filter for this column. This constant also defined in
     * configuration settings for search builder.
     */
    public static final String PROJECT_STATUS_ID = "ProjectStatusID";

    /**
     * Represents the alias for project status name column. It is used to build
     * search filter for this column. This constant also defined in
     * configuration settings for search builder.
     */
    public static final String PROJECT_STATUS_NAME = "ProjectStatusName";

    /**
     * Represents the alias for project property name column. It is used to
     * build search filter for this column. This constant also defined in
     * configuration settings for search builder.
     */
    public static final String PROJECT_PROPERTY_NAME = "ProjectPropertyName";

    /**
     * Represents the alias for project property value column. It is used to
     * build search filter for this column. This constant also defined in
     * configuration settings for search builder.
     */
    public static final String PROJECT_PROPERTY_VALUE = "ProjectPropertyValue";

    /**
     * Represents the alias for project resource property name column. It is
     * used to build search filter for this column. This constant also defined
     * in configuration settings for search builder.
     */
    public static final String PROJECT_RESOURCE_PROPERTY_NAME = "ProjectResourcePropertyName";

    /**
     * Represents the alias for project resource property value column. It is
     * used to build search filter for this column. This constant also defined
     * in configuration settings for search builder.
     */
    public static final String PROJECT_RESOURCE_PROPERTY_VALUE = "ProjectResourcePropertyValue";
    
    /**
     * Represents the alias for td direct project id column. It is used to build
     * search filter for this column. This constant also defined in
     * configuration settings for search builder.
     */
    public static final String TC_DIRECT_PROJECT_ID = "TcDirectProjectID";
    
    /**
     * Represents the alias for project created user. It is used to build
     * search filter for this column. This constant also defined in
     * configuration settings for search builder.
     */
    public static final String PROJECT_CREATE_USER = "ProjectCreateUser";

    /**
     * Private constructor to prevent initialization of class instance.
     */
    private ProjectFilterUtility() {
    }

    /**
     * Build the filter to search for project with the given type id. Type id
     * must be greater than zero.
     *
     * @return the filter to search for project.
     * @param typeId
     *            The type id to build filter.
     * @throws IllegalArgumentException
     *             if input is less than or equals to zero.
     */
    public static Filter buildTypeIdEqualFilter(long typeId) {
        Helper.checkNumberPositive(typeId, "typeId");
        return SearchBundle.buildEqualToFilter(PROJECT_TYPE_ID, new Long(typeId));
    }

    /**
     * Build the filter to search for project with type id in the given type id
     * list. Type ids must be greater than zero. The content of the input list
     * must be of Long type.
     *
     * @return the filter to search for project.
     * @param typeIds
     *            The type id list to build filter.
     * @throws IllegalArgumentException
     *             if one id in the list is less than or equals to zero or the
     *             list is null or empty or the content of the list is not of
     *             Long type.
     */
    public static Filter buildTypeIdInFilter(List typeIds) {
        assertLongListValid(typeIds, "typeIds");
        return SearchBundle.buildInFilter(PROJECT_TYPE_ID, typeIds);
    }

    /**
     * Build the filter to search for project with the given type name. Type
     * name must not be null or empty string.
     *
     * @return the filter to search for project.
     * @param name
     *            The type name to build filter.
     * @throws IllegalArgumentException
     *             if input is null or empty.
     */
    public static Filter buildTypeNameEqualFilter(String name) {
        Helper.checkStringNotNullOrEmpty(name, "name");
        return SearchBundle.buildEqualToFilter(PROJECT_TYPE_NAME, name);
    }

    /**
     * Build the filter to search for project with type name in the given type
     * name list. Type names must not be null or empty. The content of the input
     * list must be of String type.
     *
     * @return the filter to search for project.
     * @param names
     *            The type names list to build filter.
     * @throws IllegalArgumentException
     *             if one name in the list is empty string or the list is null
     *             or empty or the content of the list is not of String type.
     */
    public static Filter buildTypeNameInFilter(List names) {
        assertStringListValid(names, "names");
        return SearchBundle.buildInFilter(PROJECT_TYPE_NAME, names);
    }

    /**
     * Build the filter to search for project with the given category id.
     * Category id must be greater than zero.
     *
     * @return the filter to search for project.
     * @param categoryId
     *            The category id to build filter.
     * @throws IllegalArgumentException
     *             if input is less than or equals to zero.
     */
    public static Filter buildCategoryIdEqualFilter(long categoryId) {
        Helper.checkNumberPositive(categoryId, "categoryId");
        return SearchBundle.buildEqualToFilter(PROJECT_CATEGORY_ID, new Long(categoryId));
    }

    /**
     * Build the filter to search for project with category id in the given
     * category id list. Category ids must be greater than zero. The content of
     * the input list must be of Long type.
     *
     * @return the filter to search for project.
     * @param categoryIds
     *            The category id list to build filter.
     * @throws IllegalArgumentException
     *             if one id in the list is less than or equals to zero or the
     *             list is null or empty or the content of the list is not of
     *             Long type.
     */
    public static Filter buildCategoryIdInFilter(List categoryIds) {
        assertLongListValid(categoryIds, "categoryIds");
        return SearchBundle.buildInFilter(PROJECT_CATEGORY_ID, categoryIds);
    }

    /**
     * Build the filter to search for project with the given category name.
     * Category name must not be null or empty string.
     *
     * @return the filter to search for project.
     * @param name
     *            The category name to build filter.
     * @throws IllegalArgumentException
     *             if input is null or empty.
     */
    public static Filter buildCategoryNameEqualFilter(String name) {
        Helper.checkStringNotNullOrEmpty(name, "name");
        return SearchBundle.buildEqualToFilter(PROJECT_CATEGORY_NAME, name);
    }

    /**
     * Build the filter to search for project with category name in the given
     * category name list. Category names must not be null or empty. The content
     * of the input list must be of String type.
     *
     * @return the filter to search for project.
     * @param names
     *            The category names list to build filter.
     * @throws IllegalArgumentException
     *             if one name in the list is empty string or the list is null
     *             or empty or the content of the list is not of String type.
     */
    public static Filter buildCategoryNameInFilter(List names) {
        assertStringListValid(names, "names");
        return SearchBundle.buildInFilter(PROJECT_CATEGORY_NAME, names);
    }

    /**
     * Build the filter to search for project with the given status id. Status
     * id must be greater than zero.
     *
     * @return the filter to search for project.
     * @param statusId
     *            The status id to build filter.
     * @throws IllegalArgumentException
     *             if input is less than or equals to zero.
     */
    public static Filter buildStatusIdEqualFilter(long statusId) {
        Helper.checkNumberPositive(statusId, "statusId");
        return SearchBundle.buildEqualToFilter(PROJECT_STATUS_ID, new Long(statusId));
    }

    /**
     * Build the filter to search for project with status id in the given status
     * id list. Status ids must be greater than zero. The content of the input
     * list must be of Long type.
     *
     * @return the filter to search for project.
     * @param statusIds
     *            The status id list to build filter.
     * @throws IllegalArgumentException
     *             if one id in the list is less than or equals to zero or the
     *             list is null or empty or the content of the list is not of
     *             Long type.
     */
    public static Filter buildStatusIdInFilter(List statusIds) {
        assertLongListValid(statusIds, "statusIds");
        return SearchBundle.buildInFilter(PROJECT_STATUS_ID, statusIds);
    }

    /**
     * Build the filter to search for project with the given status name. Status
     * name must not be null or empty string.
     *
     * @return the filter to search for project.
     * @param name
     *            The status name to build filter.
     * @throws IllegalArgumentException
     *             if input is null or empty.
     */
    public static Filter buildStatusNameEqualFilter(String name) {
        Helper.checkStringNotNullOrEmpty(name, "name");
        return SearchBundle.buildEqualToFilter(PROJECT_STATUS_NAME, name);
    }

    /**
     * Build the filter to search for project with status name in the given
     * status name list. Status names must not be null or empty. The content of
     * the input list must be of String type.
     *
     * @return the filter to search for project.
     * @param names
     *            The status names list to build filter.
     * @throws IllegalArgumentException
     *             if one name in the list is empty string or the list is null
     *             or empty or the content of the list is not of String type.
     */
    public static Filter buildStatusNameInFilter(List names) {
        assertStringListValid(names, "names");
        return SearchBundle.buildInFilter(PROJECT_STATUS_NAME, names);
    }

    /**
     * Build the filter to search for project with the given property name. Name
     * must not be null or empty string.
     *
     * @return the filter to search for project.
     * @param name
     *            The property name to build filter.
     * @throws IllegalArgumentException
     *             if input is null or empty string.
     */
    public static Filter buildProjectPropertyNameEqualFilter(String name) {
        Helper.checkStringNotNullOrEmpty(name, "name");
        return SearchBundle.buildEqualToFilter(PROJECT_PROPERTY_NAME, name);
    }

    /**
     * Build the filter to search for project with the given list of property
     * names. Names must not be null or empty string. The content of the input
     * list must be of String type.
     *
     * @return the filter to search for project.
     * @param names
     *            The property name list to build filter.
     * @throws IllegalArgumentException
     *             if one name in the list is empty string or the list is null
     *             or empty or the content of the list is not of String type.
     */
    public static Filter buildProjectPropertyNameInFilter(List names) {
        assertStringListValid(names, "names");
        return SearchBundle.buildInFilter(PROJECT_PROPERTY_NAME, names);
    }

    /**
     * Build the filter to search for project with the given property value.
     * Value must not be null or empty string.
     *
     * @return the filter to search for project.
     * @param value
     *            The property value to build filter.
     * @throws IllegalArgumentException
     *             if input is null or empty string.
     */
    public static Filter buildProjectPropertyValueEqualFilter(String value) {
        Helper.checkStringNotNullOrEmpty(value, "value");
        return SearchBundle.buildEqualToFilter(PROJECT_PROPERTY_VALUE, value);
    }

    /**
     * Build the filter to search for project with the given list of property
     * values. Values must not be null or empty string. The content of the input
     * list must be of String type.
     *
     * @return the filter to search for project.
     * @param values
     *            The property name list to build filter.
     * @throws IllegalArgumentException
     *             if one value in the list is empty string or the list is null
     *             or empty or the content of the list is not of String type.
     */
    public static Filter buildProjectPropertyValueInFilter(List values) {
        assertStringListValid(values, "values");
        return SearchBundle.buildInFilter(PROJECT_PROPERTY_VALUE, values);
    }

    /**
     * Build the filter to search for project with the tc direct project id.
     * Tc direct project id must be greater than zero.
     *
     * @return the filter to search for project.
     * @param tcDirectProjectId
     *            The status id to build filter.
     * @throws IllegalArgumentException
     *             if input is less than or equals to zero.
     */
    public static Filter buildTcDirectProjectIdEqualFilter(long tcDirectProjectId) {
        Helper.checkNumberPositive(tcDirectProjectId, "tcDirectProjectId");        
        return SearchBundle.buildEqualToFilter(TC_DIRECT_PROJECT_ID, new Long(tcDirectProjectId));
    }
    
    /**
     * Build the filter to search for project with the given operator. 
     * Operator must not be null or empty string.
     *
     * @return the filter to search for project.
     * @param opertator
     *            The create user to build filter.
     * @throws IllegalArgumentException
     *             if input is null or empty.
     */
    public static Filter buildCreateUserEqualFilter(String opertator) {
        Helper.checkStringNotNullOrEmpty(opertator, "operator");
        return SearchBundle.buildEqualToFilter(PROJECT_CREATE_USER, opertator);
    }
    
    /**
     * <p>
     * Build the filter to search for project with the given property name/value
     * pair. Name and value must not be null or empty string.
     * </p>
     *
     * @param name
     *            he property name to build filter.
     * @param value
     *            he property value to build filter.
     * @return he filter to search for project.
     * @throws IllegalArgumentException
     *             if any input is null or empty string.
     */
    public static Filter buildProjectPropertyEqualFilter(String name, String value) {
        return buildAndFilter(buildProjectPropertyNameEqualFilter(name),
            buildProjectPropertyValueEqualFilter(value));
    }

    /**
     * Build the filter to search for project with the given resource property
     * name. Name must not be null or empty string.
     *
     * @return the filter to search for project.
     * @param name
     *            The resource property name to build filter.
     * @throws IllegalArgumentException
     *             if input is null or empty string.
     */
    public static Filter buildProjectResourcePropertyNameEqualFilter(String name) {
        Helper.checkStringNotNullOrEmpty(name, "name");
        return SearchBundle.buildEqualToFilter(PROJECT_RESOURCE_PROPERTY_NAME, name);
    }

    /**
     * Build the filter to search for project with the given list of resource
     * property names. Names must not be null or empty string. The content of
     * the input list must be of String type.
     *
     * @return the filter to search for project.
     * @param names
     *            The resource property name list to build filter.
     * @throws IllegalArgumentException
     *             if one name in the list is empty string or the list is null
     *             or empty or the content of the list is not of String type.
     */
    public static Filter buildProjectResourcePropertyNameInFilter(List names) {
        assertStringListValid(names, "names");
        return SearchBundle.buildInFilter(PROJECT_RESOURCE_PROPERTY_NAME, names);
    }

    /**
     * Build the filter to search for project with the given resource property
     * value. Value must not be null or empty string.
     *
     * @return the filter to search for project.
     * @param value
     *            The property value to build filter.
     * @throws IllegalArgumentException
     *             if input is null or empty string.
     */
    public static Filter buildProjectResourcePropertyValueEqualFilter(String value) {
        Helper.checkStringNotNullOrEmpty(value, "value");
        return SearchBundle.buildEqualToFilter(PROJECT_RESOURCE_PROPERTY_VALUE, value);
    }

    /**
     * Build the filter to search for project with the given list of resource
     * property values. Values must not be null or empty string. The content of
     * the input list must be of String type.
     *
     * @return the filter to search for project.
     * @param values
     *            The resource property value list to build filter.
     * @throws IllegalArgumentException
     *             if one value in the list is empty string or the list is null
     *             or empty or the content of the list is not of String type.
     */
    public static Filter buildProjectResourcePropertyValueInFilter(List values) {
        assertStringListValid(values, "values");
        return SearchBundle.buildInFilter(PROJECT_RESOURCE_PROPERTY_VALUE, values);
    }

    /**
     * <p>
     * Build the filter to search for project with the given resource property
     * name/value pair. Name and value must not be null or empty string.
     * </p>
     *
     * @param name
     *            The property name to build filter.
     * @param value
     *            The property value to build filter.
     * @return the filter to search for project.
     * @throws IllegalArgumentException
     *             if any input is null or empty string.
     */
    public static Filter buildProjectPropertyResourceEqualFilter(String name, String value) {
        return buildAndFilter(buildProjectResourcePropertyNameEqualFilter(name),
            buildProjectResourcePropertyValueEqualFilter(value));
    }

    /**
     * Build the AND filter that combine the two input filters.
     *
     * @return the combined filter.
     * @param f1
     *            the first filter.
     * @param f2
     *            the second filter.
     * @throws IllegalArgumentException
     *             if any input is null.
     */
    public static Filter buildAndFilter(Filter f1, Filter f2) {
        Helper.checkObjectNotNull(f1, "f1");
        Helper.checkObjectNotNull(f2, "f2");
        return SearchBundle.buildAndFilter(f1, f2);
    }

    /**
     * Build the OR filter that combine the two input filters.
     *
     * @return the combined filter.
     * @param f1
     *            the first filter.
     * @param f2
     *            the second filter.
     * @throws IllegalArgumentException
     *             if any input is null.
     */
    public static Filter buildOrFilter(Filter f1, Filter f2) {
        Helper.checkObjectNotNull(f1, "f1");
        Helper.checkObjectNotNull(f2, "f2");
        return SearchBundle.buildOrFilter(f1, f2);
    }

    /**
     * Build the NOT filter that negate input filter.
     *
     * @return the negated filter.
     * @param f1
     *            the filter.
     * @throws IllegalArgumentException
     *             if any input is null.
     */
    public static Filter buildNotFilter(Filter f1) {
        Helper.checkObjectNotNull(f1, "f1");
        return SearchBundle.buildNotFilter(f1);
    }

    /**
     * this private method is used to check if long list valid.
     *
     * @param list
     *            the list to validate
     * @param name
     *            the name of list
     * @throws IllegalArgumentException
     *             if one element in the list is less than or equals to zero or the
     *             list is null or empty or the content of the list is not of
     *             Long type.
     */
    private static void assertLongListValid(List list, String name) {
        Helper.checkObjectNotNull(list, name);
        Helper.checkNumberPositive(list.size(), name + "'s size");
        // validate each element
        for (Iterator iter = list.iterator(); iter.hasNext();) {
            Object element = iter.next();
            if (!(element instanceof Long)) {
                throw new IllegalArgumentException(name + "has a element that is not Long");
            }
            Helper.checkNumberPositive(((Long) element).longValue(), name + "'s element");
        }
    }

    /**
     * this private method is used to check if given string list valid.
     *
     * @param list
     *            the list to validate
     * @param name
     *            the name of list
     * @throws IllegalArgumentException
     *             if one element in the list is empty string or the list is null
     *             or empty or the content of the list is not of String type.
     */
    private static void assertStringListValid(List list, String name) {
        Helper.checkObjectNotNull(list, name);
        Helper.checkNumberPositive(list.size(), name + "'s size");
        // validate each element
        for (Iterator iter = list.iterator(); iter.hasNext();) {
            Object element = iter.next();
            if (!(element instanceof String)) {
                throw new IllegalArgumentException(name + "has a element that is not String.");
            }
            Helper.checkStringNotNullOrEmpty((String) element, name + "'s element");
        }
    }
}
