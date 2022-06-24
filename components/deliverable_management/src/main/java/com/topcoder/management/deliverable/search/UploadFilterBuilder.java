/*
 * Copyright (C) 2006-2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable.search;

import com.topcoder.management.deliverable.DeliverableHelper;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;

/**
 * <p>
 * The UploadFilterBuilder class supports building filters for searching for Uploads.
 * </p>
 * <p>
 * This class has only final static fields/methods, so mutability is not an issue.
 * </p>
 * <p>
 * Changes in version 1.2:
 * <ul>
 * <li>code is simplified by using Autoboxing feature of Java 5.0</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 1.2.1 (Module Assembly - TC Cockpit - Studio - Final Fixes Integration Part One Assembly) Change notes:
 *   <ol>
 *     <li>Added {@link #PROJECT_PHASE_ID_FIELD_NAME} constant.</li>
 *     <li>Added {@link #createProjectPhaseIdFilter(long)} method.</li>
 *   </ol>
 * </p>
 *
 * @author aubergineanode, singlewood
 * @author isv
 * @version 1.2.2
 */
public class UploadFilterBuilder {

    /**
     * The name of the field under which SearchBuilder Filters can be built in order to filter on
     * the id of the Upload.
     */
    private static final String UPLOAD_ID_FIELD_NAME = "upload_id";

    /**
     * The name of the field under which SearchBuilder Filters can be built in order to filter on
     * the type id of the Upload.
     */
    private static final String UPLOAD_TYPE_ID_FIELD_NAME = "upload_type_id";

    /**
     * The name of the field under which SearchBuilder Filters can be built in order to filter on
     * the status id of the Upload.
     */
    private static final String UPLOAD_STATUS_ID_FIELD_NAME = "upload_status_id";

    /**
     * The name of the field under which SearchBuilder Filters can be built in order to filter on
     * the project the Upload is associated with.
     */
    private static final String PROJECT_ID_FIELD_NAME = "project_id";

    /**
     * The name of the field under which SearchBuilder Filters can be built in order to filter on the project phase the
     * Upload is associated with.
     *
     * @since 1.2.1
     */
    private static final String PROJECT_PHASE_ID_FIELD_NAME = "project_phase_id";

    /**
     * The name of the field under which SearchBuilder Filters can be built in order to filter on
     * the resource the Upload is associated with.
     */
    private static final String RESOURCE_ID_FIELD_NAME = "resource_id";

    /**
     * Private constructor to prevent instantiation.
     */
    private UploadFilterBuilder() {
        // no operation
    }

    /**
     * Creates a filter that selects uploads with the given id.
     *
     * @return A filter for selecting uploads with the given id.
     * @param uploadId The upload id to filter on
     * @throws IllegalArgumentException If uploadId is <= 0
     */
    public static Filter createUploadIdFilter(long uploadId) {
        DeliverableHelper.checkGreaterThanZero(uploadId, "uploadId", null);
        return new EqualToFilter(UPLOAD_ID_FIELD_NAME, new Long(uploadId));
    }

    /**
     * Creates a filter that selects uploads with the given type id.
     *
     * @return A filter for selecting uploads with the given type id.
     * @param uploadTypeId The upload type id to filter on
     * @throws IllegalArgumentException If uploadTypeId is <= 0
     */
    public static Filter createUploadTypeIdFilter(long uploadTypeId) {
        DeliverableHelper.checkGreaterThanZero(uploadTypeId, "uploadTypeId", null);
        return new EqualToFilter(UPLOAD_TYPE_ID_FIELD_NAME, new Long(uploadTypeId));
    }

    /**
     * Creates a filter that selects uploads with the given status id.
     *
     * @return A filter for selecting uploads with the given status id.
     * @param uploadStatusId The upload id to filter on
     * @throws IllegalArgumentException If uploadStatusId is <= 0
     */
    public static Filter createUploadStatusIdFilter(long uploadStatusId) {
        DeliverableHelper.checkGreaterThanZero(uploadStatusId, "uploadStatusId", null);
        return new EqualToFilter(UPLOAD_STATUS_ID_FIELD_NAME, new Long(uploadStatusId));
    }

    /**
     * Creates a filter that selects uploads related to the project with the given id.
     *
     * @return A filter for selecting uploads related to the given project
     * @param projectId The project id to filter on
     * @throws IllegalArgumentException If projectId is <= 0
     */
    public static Filter createProjectIdFilter(long projectId) {
        DeliverableHelper.checkGreaterThanZero(projectId, "projectId", null);
        return new EqualToFilter(PROJECT_ID_FIELD_NAME, new Long(projectId));
    }

    /**
     * Creates a filter that selects uploads related to the project phase with the given id.
     *
     * @param projectPhaseId The project id to filter on
     * @return A filter for selecting uploads related to the given project phase
     * @throws IllegalArgumentException If projectPhaseId is <= 0
     * @since 1.2.1
     */
    public static Filter createProjectPhaseIdFilter(long projectPhaseId) {
        DeliverableHelper.checkGreaterThanZero(projectPhaseId, "projectPhaseId", null);
        return new EqualToFilter(PROJECT_PHASE_ID_FIELD_NAME, projectPhaseId);
    }

    /**
     * Creates a filter that selects uploads related to the given resource id.
     *
     * @return A filter for selecting uploads related to the given resource
     * @param resourceId The resource id to filter on
     * @throws IllegalArgumentException If resourceId is <= 0
     */
    public static Filter createResourceIdFilter(long resourceId) {
        DeliverableHelper.checkGreaterThanZero(resourceId, "resourceId", null);
        return new EqualToFilter(RESOURCE_ID_FIELD_NAME, new Long(resourceId));
    }
}
