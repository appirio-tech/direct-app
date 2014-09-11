/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable.search;

import com.topcoder.management.deliverable.DeliverableHelper;
import com.topcoder.search.builder.filter.AndFilter;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.search.builder.filter.NotFilter;

/**
 * <p>
 * The DeliverableFilterBuilder class supports building filters for searching for Deliverables.
 * </p>
 * <p>
 * Changes in version 1.2:
 * <ul>
 * <li>code is simplified by using Autoboxing feature of Java 5.0</li>
 * </ul>
 * </p>
 * <p>
 * This class has only final static fields/methods, so mutability is not an issue.
 * </p>
 *
 * @author aubergineanode, singlewood
 * @author TCSDEVELOPER
 * @version 1.2
 */
public class DeliverableFilterBuilder {

    /**
     * The name of the field under which SearchBuilder Filters can be built in order to filter on
     * the id of the Deliverable.
     */
    private static final String DELIVERABLE_ID_FIELD_NAME = "deliverable_id";

    /**
     * The name of the field under which SearchBuilder Filters can be built in order to filter on
     * the name of the Deliverable.
     */
    private static final String NAME_FIELD_NAME = "name";

    /**
     * The name of the field under which SearchBuilder Filters can be built in order to filter on
     * the id of the project of the Deliverable.
     */
    private static final String PROJECT_ID_FIELD_NAME = "project_id";

    /**
     * The name of the field under which SearchBuilder Filters can be built in order to filter on
     * the phase the Deliverable falls in.
     */
    private static final String PHASE_ID_FIELD_NAME = "phase_id";

    /**
     * The name of the field under which SearchBuilder Filters can be built in order to filter on
     * the submission the Deliverable is associated with.
     */
    private static final String SUBMISSION_ID_FIELD_NAME = "submission_id";

    /**
     * The name of the field under which SearchBuilder Filters can be built in order to filter on
     * the resource id of the Deliverable.
     */
    private static final String RESOURCE_ID_FIELD_NAME = "resource_id";

    /**
     * The name of the field under which SearchBuilder Filters can be built in order to filter on
     * whether the Deliverable is required or not.
     */
    private static final String REQUIRED_FIELD_NAME = "required";

    /**
     * Private constructor to prevent instantiation.
     */
    private DeliverableFilterBuilder() {
        // no operation.
    }

    /**
     * Creates a filter that selects deliverables with the given id.
     *
     * @return A filter for selecting deliverables by id
     * @param deliverableId The id of the deliverable
     * @throws IllegalArgumentException If deliverableId is <= 0
     */
    public static Filter createDeliverableIdFilter(long deliverableId) {
        DeliverableHelper.checkGreaterThanZero(deliverableId, "deliverableId", null);
        return new EqualToFilter(DELIVERABLE_ID_FIELD_NAME, new Long(deliverableId));
    }

    /**
     * Creates a filter that selects deliverables that fall have the given name.
     *
     * @return A filter for selecting deliverables with the given name
     * @param name The name to filter on
     * @throws IllegalArgumentException If name is null
     */
    public static Filter createNameFilter(String name) {
        DeliverableHelper.checkObjectNotNull(name, "name", null);
        return new EqualToFilter(NAME_FIELD_NAME, name);
    }

    /**
     * Creates a filter that selects deliverables related to the project with the given id.
     *
     * @return A filter for selecting deliverables related to the given project
     * @param projectId The project id to filter on
     * @throws IllegalArgumentException If projectId is <= 0
     */
    public static Filter createProjectIdFilter(long projectId) {
        DeliverableHelper.checkGreaterThanZero(projectId, "projectId", null);
        return new EqualToFilter(PROJECT_ID_FIELD_NAME, new Long(projectId));
    }

    /**
     * Creates a filter that selects deliverables that fall in the phase with the given id.
     *
     * @return A filter for selecting deliverables for the given phase
     * @param phaseId The phase id to filter on
     * @throws IllegalArgumentException If phaseId is <= 0
     */
    public static Filter createPhaseIdFilter(long phaseId) {
        DeliverableHelper.checkGreaterThanZero(phaseId, "phaseId", null);
        return new EqualToFilter(PHASE_ID_FIELD_NAME, new Long(phaseId));
    }

    /**
     * Creates a filter that selects deliverables that are related to the given submission.
     *
     * @return A filter for selecting deliverables for the given submission
     * @param submissionId The submission id to filter on
     * @throws IllegalArgumentException If submissionId is <= 0
     */
    public static Filter createSubmissionIdFilter(long submissionId) {
        DeliverableHelper.checkGreaterThanZero(submissionId, "submissionId", null);
        return new EqualToFilter(SUBMISSION_ID_FIELD_NAME, new Long(submissionId));
    }

    /**
     * Creates a filter that selects deliverables that are related to the given resource.
     *
     * @return A filter for selecting deliverables for the given resource
     * @param resourceId The resource id to filter on
     * @throws IllegalArgumentException If resourceId is <= 0
     */
    public static Filter createResourceIdFilter(long resourceId) {
        DeliverableHelper.checkGreaterThanZero(resourceId, "resourceId", null);
        return new EqualToFilter(RESOURCE_ID_FIELD_NAME, new Long(resourceId));
    }

    /**
     * Creates a filter that selects deliverables that fall on whether or not the deliverable is
     * required.
     *
     * @return A filter for selecting deliverables that are (or are not) required
     * @param isRequired Whether or not deliverable is required
     */
    public static Filter createRequiredFilter(boolean isRequired) {
        // Build the filter according to the fact that SQL Boolean false = 0 and SQL Boolean true != 0
        if (isRequired) {
            return new NotFilter(new EqualToFilter(REQUIRED_FIELD_NAME, new Integer(0)));
        }

        return new EqualToFilter(REQUIRED_FIELD_NAME, new Integer(0));
    }

    /**
     * Creates a filter that selects deliverables that are related to the given id and resource.
     *
     * @return A filter for selecting deliverables for the given id and resource
     * @param deliverableId
     *            The id of the deliverable
     * @param resourceId
     *            The resource id to filter on
     * @throws IllegalArgumentException
     *             If resourceId or deliverableId is <= 0
     */
    public static Filter createDeliverableIdResourceIdFilter(long deliverableId, long resourceId) {
        DeliverableHelper.checkGreaterThanZero(deliverableId, "deliverableId", null);
        DeliverableHelper.checkGreaterThanZero(resourceId, "resourceId", null);
        return new AndFilter(createDeliverableIdFilter(deliverableId),
                createResourceIdFilter(resourceId));
    }
}
