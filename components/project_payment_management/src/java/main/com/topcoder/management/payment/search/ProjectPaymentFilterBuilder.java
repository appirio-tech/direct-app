/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.payment.search;

import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.search.builder.filter.NotFilter;
import com.topcoder.search.builder.filter.NullFilter;

/**
 * <p>
 * This is a static helper class that provides factory methods for creating filters that can be used when searching
 * for project payments
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> This class is immutable and thread safe.
 * </p>
 *
 * @author maksimilian, sparemax
 * @version 1.0
 */
public final class ProjectPaymentFilterBuilder {
    /**
     * Empty private constructor.
     */
    private ProjectPaymentFilterBuilder() {
        // Empty
    }

    /**
     * Creates a filter that selects the project payments matching by resource id.
     *
     * @param resourceId
     *            the resource id
     *
     * @return the filter, non null
     */
    public static Filter createResourceIdFilter(long resourceId) {
        return new EqualToFilter("resourceId", resourceId);
    }

    /**
     * Creates a filter that selects the project payments matching by project id.
     *
     * @param projectId
     *            the project id
     *
     * @return the filter, non null
     */
    public static Filter createProjectIdFilter(long projectId) {
        return new EqualToFilter("projectId", projectId);
    }

    /**
     * Creates a filter that selects the project payments matching by project payment type id.
     *
     * @param projectPaymentTypeId
     *            the project payment type id
     *
     * @return the filter, non null
     */
    public static Filter createProjectPaymentTypeIdFilter(long projectPaymentTypeId) {
        return new EqualToFilter("projectPaymentTypeId", projectPaymentTypeId);
    }

    /**
     * Creates a filter that selects the project payments matching by submission id.
     *
     * @param submissionId
     *            the submission id
     *
     * @return the filter, non null
     */
    public static Filter createSubmissionIdFilter(long submissionId) {
        return new EqualToFilter("submissionId", submissionId);
    }

    /**
     * Creates a filter that selects the project payments matching by PACTs payment id.
     *
     * @param transferred
     *            the PACTs payment id
     *
     * @return the filter, non null
     */
    public static Filter createPactsPaymentIdFilter(boolean transferred) {
        Filter result = new NullFilter("pactsPaymentId");
        if (transferred) {
            result = new NotFilter(result);
        }
        return result;
    }
}
