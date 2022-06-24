/*
 * Copyright (C) 2006-2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable.search;

import com.topcoder.management.deliverable.DeliverableHelper;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;

/**
 * <p>
 * The SubmissionFilterBuilder class supports building filters for searching for Submissions. This class consists of 2
 * parts. The first part consists of static Strings that name the fields that are available for searching. All
 * UploadManager implementations should use SearchBundles that are configured to use these names. The second part of
 * this class consists of convenience methods to create common filters based on these field names.
 * </p>
 *
 * <p>
 * <em>Changes in 1.1: </em>
 * <ul>
 * <li>Added method createSubmissionTypeIdFilter().</li>
 * </ul>
 * </p>
 * <p>
 * Changes in version 1.2:
 * <ul>
 * <li>code is simplified by using Autoboxing feature of Java 5.0</li>
 * </ul>
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> This class has only final static fields/methods, so mutability is not an issue.
 * </p>
 *
 * @author aubergineanode, singlewood, saarixx, sparemax
 * @author TCSDEVELOPER
 * @version 1.2
 */
public class SubmissionFilterBuilder {

    /**
     * The name of the field under which SearchBuilder Filters can be built in order to filter on
     * the id of the Submission.
     */
    private static final String SUBMISSION_ID_FIELD_NAME = "submission_id";

    /**
     * The name of the field under which SearchBuilder Filters can be built in order to filter on
     * the id of the Upload that is associated with the Submission.
     */
    private static final String UPLOAD_ID_FIELD_NAME = "upload_id";

    /**
     * The name of the field under which SearchBuilder Filters can be built in order to filter on
     * the project the submission is associated with.
     */
    private static final String PROJECT_ID_FIELD_NAME = "project_id";

    /**
     * The name of the field under which SearchBuilder Filters can be built in order to filter on
     * the id of the resource the Submission is associated with.
     */
    private static final String RESOURCE_ID_FIELD_NAME = "resource_id";

    /**
     * The name of the field under which SearchBuilder Filters can be built in order to filter on
     * the submission status of the Submissions.
     */
    private static final String SUBMISSION_STATUS_ID_FIELD_NAME = "submission_status_id";

    /**
     * <P>
     * The name of the field under which SearchBuilder Filters can be built in order to filter on the submission type
     * of the Submissions.
     * </P>
     *
     * <P>
     * This field is final, static, and private, and is used in the createSubmissionTypeIdFilter method.
     * </P>
     *
     * @since 1.1
     */
    private static final String SUBMISSION_TYPE_ID_FIELD_NAME = "submission_type_id";

    /**
     * Private constructor to prevent instantiation.
     */
    private SubmissionFilterBuilder() {
        // no operation.
    }

    /**
     * Creates a filter that selects the submission with the given id.
     *
     * @return A filter for selecting submissions with the given id
     * @param submissionId The submission id to filter on
     * @throws IllegalArgumentException If submissionId is <= 0
     */
    public static Filter createSubmissionIdFilter(long submissionId) {
        DeliverableHelper.checkGreaterThanZero(submissionId, "submissionId", null);
        return new EqualToFilter(SUBMISSION_ID_FIELD_NAME, new Long(submissionId));
    }

    /**
     * Creates a filter that selects submissions related to the given upload.
     *
     * @return A filter for selecting submissions related to the given upload
     * @param uploadId The upload id to filter on
     * @throws IllegalArgumentException If uploadId is <= 0
     */
    public static Filter createUploadIdFilter(long uploadId) {
        DeliverableHelper.checkGreaterThanZero(uploadId, "uploadId", null);
        return new EqualToFilter(UPLOAD_ID_FIELD_NAME, new Long(uploadId));
    }

    /**
     * Creates a filter that selects submissions related to the project with the given id.
     *
     * @return A filter for selecting submissions related to the given project
     * @param projectId The project id to filter on
     * @throws IllegalArgumentException If projectId is <= 0
     */
    public static Filter createProjectIdFilter(long projectId) {
        DeliverableHelper.checkGreaterThanZero(projectId, "projectId", null);
        return new EqualToFilter(PROJECT_ID_FIELD_NAME, new Long(projectId));
    }

    /**
     * Creates a filter that selects submissions related to the given resource id.
     *
     * @return A filter for selecting submissions related to the given resource
     * @param resourceId The resource id to filter on
     * @throws IllegalArgumentException If resourceId is <= 0
     */
    public static Filter createResourceIdFilter(long resourceId) {
        DeliverableHelper.checkGreaterThanZero(resourceId, "resourceId", null);
        return new EqualToFilter(RESOURCE_ID_FIELD_NAME, new Long(resourceId));
    }

    /**
     * Creates a filter that selects submissions related having the given submission status id.
     *
     * @return A filter for selecting submissions with the given status
     * @param submissionStatusId The submission status id to filter on
     * @throws IllegalArgumentException If submissionStatusId is <= 0
     */
    public static Filter createSubmissionStatusIdFilter(long submissionStatusId) {
        DeliverableHelper.checkGreaterThanZero(submissionStatusId, "submissionStatusId", null);
        return new EqualToFilter(SUBMISSION_STATUS_ID_FIELD_NAME, new Long(submissionStatusId));
    }

    /**
     * <P>
     * Creates a filter that selects submissions related having the given submission type id.
     * </P>
     *
     * @param submissionTypeId
     *            the submission type id to filter on.
     *
     * @return a filter for selecting submissions with the given type.
     *
     * @throws IllegalArgumentException
     *             If submissionTypeId is not positive.
     *
     * @since 1.1
     */
    public static Filter createSubmissionTypeIdFilter(long submissionTypeId) {
        DeliverableHelper.checkGreaterThanZero(submissionTypeId, "submissionTypeId", null);

        return new EqualToFilter(SUBMISSION_TYPE_ID_FIELD_NAME, new Long(submissionTypeId));
    }
}
