/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource.search;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.topcoder.management.resource.Helper;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.search.builder.filter.InFilter;
import com.topcoder.search.builder.filter.NullFilter;

/**
 * <p>The ResourceFilterBuilder class supports building filters for searching for Resources.</p>
 *
 * <p>
 * This class consists of 2 parts. The first part consists of static Strings that name the fields
 * that are available for searching. All ResourceManager implementations should use SearchBundles
 * that are configured to use these names. The second part of this class consists of convenience
 * methods to create common filters based on these field names.
 * </p>
 *
 * <p>This class has only final static fields/methods, so mutability is not an issue. This class is thread-safe.</p>
 *
 * <p>Changes in version 1.1: Added a new filter factory method createAnySubmissionIdFilter(long[]) based on the fact
 * that resource has been changed to be associated with one or many submission ids. And it also updated those
 * deprecated methods in Search Builder component of latest version.</p>
 *
 * @author aubergineanode, kinfkong, Rica, Xuchen
 * @version 1.1
 * @since 1.0
 */
public class ResourceFilterBuilder {

    /**
     * <p>
     * The name of the field under which SearchBuilder Filters can be built
     * in order to filter on the id of the Resource.
     * </p>
     *
     * <p>
     * Note, according to the forum:
     *
     * The name is changed to : resource.resource_id
     * </p>
     * <p>
     * This field is final, static, and public, and is used in the createResourceIdFilter method.
     * </p>
     */
    public static final String RESOURCE_ID_FIELD_NAME = "resource.resource_id";

    /**
     * <p>
     * The name of the field under which SearchBuilder Filters can be built in order to filter
     * on the id of the project the
     * Resource belongs to.
     * </p>
     *
     * <p>
     * This field is final, static, and public, and is used in the createProjectIdFilter
     * and createNullProjectFilter method.
     * </p>
     */
    public static final String PROJECT_ID_FIELD_NAME = "project_id";

    /**
     * <p>
     * The name of the field under which SearchBuilder Filters can be built in order
     * to filter on the id of the phase the Resource belongs to.
     * </p>
     *
     * <p>
     * This field is final, static, and public, and is used in the createPhaseIdFilter method.
     * </p>
     */
    public static final String PHASE_ID_FIELD_NAME = "project_phase_id";

    /**
     * <p>
     * The name of the field under which SearchBuilder Filters can be built in order to filter on the submission ids
     * associated with the Resource.
     * </p>
     *
     * <p>
     * This field is final, static, and public, and is used in the createSubmissionIdFilter and
     * createNoSubmissionFilter methods.
     * </p>
     */
    public static final String SUBMISSION_ID_FIELD_NAME = "submission_id";

    /**
     * <p>
     * The name of the field under which SearchBuilder Filters can be built in order to filter on the resource
     * role id with which the Resource is associated.
     * </p>
     *
     * <p>
     * This field is final, static, and public, and is used in the createResourceRoleIdFilter method.
     * </p>
     */
    public static final String RESOURCE_ROLE_ID_FIELD_NAME = "resource_role_id";

    /**
     * <p>
     * The name of the field under which SearchBuilder Filters can be built in order to filter based on the name
     * of an extension property.
     * </p>
     *
     * <p>
     * This field is public, static, and final.
     * </p>
     */
    public static final String EXTENSION_PROPERTY_NAME_FIELD_NAME = "name";

    /**
     * <p>
     * The name of the field under which SearchBuilder Filters can be built in order to filter based on the value
     * of an extension property.
     * </p>
     *
     * <p>
     * This field is public, static, and final.
     * </p>
     */
    public static final String EXTENSION_PROPERTY_VALUE_FIELD_NAME = "value";

    /**
     * <p>
     * The constructor of ResourceFilterBuilder. It is set to be private to prevent instantiation.
     * </p>
     */
    private ResourceFilterBuilder() {
        // do nothing
    }

    /**
     * <p>
     * Creates a filter that selects resources with the given id.
     * </p>
     *
     * @param resourceId The resource id to filter on
     *
     * @return A filter for selecting Resources with the given id
     *
     * @throws IllegalArgumentException If resourceId is &lt;= 0
     */
    public static Filter createResourceIdFilter(long resourceId) {
        Helper.checkLongPositive(resourceId, "resourceId");
        return new EqualToFilter(RESOURCE_ID_FIELD_NAME, new Long(resourceId));
    }

    /**
     * <p>
     * Creates a filter that selects resources which have the given project id.
     * </p>
     *
     * @param projectId The project id to filter on
     *
     * @return A filter for selecting Resources which are associated with the given project
     *
     * @throws IllegalArgumentException If projectId is &lt;= 0.
     */
    public static Filter createProjectIdFilter(long projectId) {
        Helper.checkLongPositive(projectId, "projectId");
        return new EqualToFilter(PROJECT_ID_FIELD_NAME, new Long(projectId));
    }

    /**
     * <p>
     * Creates a filter that selects resources which are related to the given phase.
     * </p>
     *
     * @param phaseId The phase id to create the filter with
     *
     * @return A filter for selecting Resources associated with the given phase
     *
     * @throws IllegalArgumentException If phaseId is &lt;= 0
     */
    public static Filter createPhaseIdFilter(long phaseId) {
        Helper.checkLongPositive(phaseId, "phaseId");
        return new EqualToFilter(PHASE_ID_FIELD_NAME, new Long(phaseId));
    }

    /**
     * <p>
     * Creates a filter that selects resources which have associated the given submission id with their submission Set.
     * </p>
     *
     * @param submissionId The submission id to create the filter with
     *
     * @return A filter to selecting Resources which have associated the given submission id with their submission Set.
     *
     * @throws IllegalArgumentException If submissionId is &lt;= 0
     */
    public static Filter createSubmissionIdFilter(long submissionId) {
        Helper.checkLongPositive(submissionId, "submissionId");
        return new EqualToFilter(SUBMISSION_ID_FIELD_NAME, new Long(submissionId));
    }

    /**
     * <p>Creates a filter that selects resources which have associated any one in the given submission ids with their
     * submission Set.</p>
     *
     * @param submissionsId the array of submission ids to build the filter with
     * @return A filter for selecting Resources associated with the any of the given submissionId;
     * or NullFilter if given array is empty
     * @throws IllegalArgumentException if given argument is null or any of the array elements is &lt;=0
     * @since 1.1
     */
    public static Filter createAnySubmissionIdFilter(long[] submissionsId) {
        Helper.checkNull(submissionsId, "submissionsId");

        if (submissionsId.length == 0) {
            return new NullFilter(SUBMISSION_ID_FIELD_NAME);
        }

        // Validate each id value, and remove duplicated ids by putting them into set which will improve efficiency
        // of InFilter created later.
        Set submissionIdSet = new HashSet();
        for (int i = 0; i < submissionsId.length; i++) {
            Helper.checkLongPositive(submissionsId[i], "element of submissionsId array");

            submissionIdSet.add(new Long(submissionsId[i]));
        }

        return new InFilter(SUBMISSION_ID_FIELD_NAME, new ArrayList(submissionIdSet));
    }

    /**
     * <p>
     * Creates a filter that selects resources which are associated with the given resource role.
     * </p>
     *
     * @param resourceRoleId The resource role id to create the filter with
     *
     * @return A filter to selecting Resources with the given role
     *
     * @throws IllegalArgumentException If resourceRoleId is &lt;= 0
     */
    public static Filter createResourceRoleIdFilter(long resourceRoleId) {
        Helper.checkLongPositive(resourceRoleId, "resourceRoleId");
        return new EqualToFilter(RESOURCE_ROLE_ID_FIELD_NAME, new Long(resourceRoleId));
    }

    /**
     * <p>
     * Creates a filter that selects resources which do not have an associated project.
     * </p>
     *
     * @return A filter for selecting Resources with no associated project
     */
    public static Filter createNoProjectFilter() {
        return new NullFilter(PROJECT_ID_FIELD_NAME);
    }

    /**
     * <p>
     * Creates a filter that selects resources which do not have an associated submission.
     * </p>
     *
     * @return A filter for selecting Resources with no associated submission
     */
    public static Filter createNoSubmissionFilter() {
        return new NullFilter(SUBMISSION_ID_FIELD_NAME);
    }

    /**
     * <p>
     * Creates a filter that selects resources which do not have an associated phase.
     * </p>
     *
     * @return A filter for selecting Resources that are not associated with a phase
     */
    public static Filter createNoPhaseFilter() {
        return new NullFilter(PHASE_ID_FIELD_NAME);
    }

    /**
     * <p>
     * Creates a filter that selects resources which have an extension property with the given value.
     * </p>
     *
     * @param value The value to search for
     *
     * @return A filter for selecting Resources that have an extension property with the given value
     *
     * @throws IllegalArgumentException If value is null
     */
    public static Filter createExtensionPropertyValueFilter(String value) {
        Helper.checkNull(value, "value");
        return new EqualToFilter(EXTENSION_PROPERTY_VALUE_FIELD_NAME, value);
    }

    /**
     * <p>
     * Creates a filter that selects resources which have an extension property with the given name.
     * </p>
     *
     * @param name The extension property name to search for
     *
     * @return A filter for selecting Resources that have an extension property with the given name
     * @throws IllegalArgumentException If name is null
     */
    public static Filter createExtensionPropertyNameFilter(String name) {
        Helper.checkNull(name, "name");
        return new EqualToFilter(EXTENSION_PROPERTY_NAME_FIELD_NAME, name);
    }
}
