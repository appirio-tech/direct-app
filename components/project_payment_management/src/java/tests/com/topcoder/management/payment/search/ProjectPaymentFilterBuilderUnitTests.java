/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.payment.search;

import static org.junit.Assert.assertEquals;
import junit.framework.JUnit4TestAdapter;

import org.junit.Test;

import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.NotFilter;
import com.topcoder.search.builder.filter.NullFilter;

/**
 * <p>
 * Unit tests for {@link ProjectPaymentFilterBuilder} class.
 * </p>
 *
 * @author sparemax
 * @version 1.0
 */
public class ProjectPaymentFilterBuilderUnitTests {
    /**
     * <p>
     * Adapter for earlier versions of JUnit.
     * </p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(ProjectPaymentFilterBuilderUnitTests.class);
    }

    /**
     * <p>
     * Accuracy test for the method <code>createProjectIdFilter(long projectId)</code>.<br>
     * The result should be correct.
     * </p>
     */
    @Test
    public void test_createProjectIdFilter() {
        long value = 1;
        EqualToFilter filter = (EqualToFilter) ProjectPaymentFilterBuilder.createProjectIdFilter(value);

        assertEquals("'createProjectIdFilter' should be correct.", "projectId", filter.getName());
        assertEquals("'createProjectIdFilter' should be correct.", value, filter.getValue());
    }

    /**
     * <p>
     * Accuracy test for the method <code>createResourceIdFilter(long resourceId)</code>.<br>
     * The result should be correct.
     * </p>
     */
    @Test
    public void test_createResourceIdFilter() {
        long value = 1;
        EqualToFilter filter = (EqualToFilter) ProjectPaymentFilterBuilder.createResourceIdFilter(value);

        assertEquals("'createResourceIdFilter' should be correct.", "resourceId", filter.getName());
        assertEquals("'createResourceIdFilter' should be correct.", value, filter.getValue());
    }

    /**
     * <p>
     * Accuracy test for the method <code>createProjectIdFilter(long projectPaymentTypeId)</code>.<br>
     * The result should be correct.
     * </p>
     */
    @Test
    public void test_createProjectPaymentTypeIdFilter() {
        long value = 1;
        EqualToFilter filter = (EqualToFilter) ProjectPaymentFilterBuilder.createProjectPaymentTypeIdFilter(value);

        assertEquals("'createProjectPaymentTypeIdFilter' should be correct.", "projectPaymentTypeId", filter
            .getName());
        assertEquals("'createProjectPaymentTypeIdFilter' should be correct.", value, filter.getValue());
    }

    /**
     * <p>
     * Accuracy test for the method <code>createSubmissionIdFilter(long submissionId)</code>.<br>
     * The result should be correct.
     * </p>
     */
    @Test
    public void test_createSubmissionIdFilter() {
        long value = 1;
        EqualToFilter filter = (EqualToFilter) ProjectPaymentFilterBuilder.createSubmissionIdFilter(value);

        assertEquals("'createSubmissionIdFilter' should be correct.", "submissionId", filter.getName());
        assertEquals("'createSubmissionIdFilter' should be correct.", value, filter.getValue());
    }

    /**
     * <p>
     * Accuracy test for the method <code>createPactsPaymentIdFilter(boolean transferred)</code>.<br>
     * The result should be correct.
     * </p>
     */
    @Test
    public void test_createPactsPaymentIdFilter_1() {
        NullFilter filter = (NullFilter) ProjectPaymentFilterBuilder.createPactsPaymentIdFilter(false);

        assertEquals("'createPactsPaymentIdFilter' should be correct.", "pactsPaymentId", filter.getName());
    }

    /**
     * <p>
     * Accuracy test for the method <code>createPactsPaymentIdFilter(boolean transferred)</code>.<br>
     * The result should be correct.
     * </p>
     */
    @Test
    public void test_createPactsPaymentIdFilter_2() {
        NotFilter filter = (NotFilter) ProjectPaymentFilterBuilder.createPactsPaymentIdFilter(true);

        NullFilter innerFiler = (NullFilter) filter.getFilter();
        assertEquals("'createPactsPaymentIdFilter' should be correct.", "pactsPaymentId", innerFiler.getName());
    }
}
