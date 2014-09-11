/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource.failuretests.search;

import com.topcoder.management.resource.search.ResourceFilterBuilder;

import junit.framework.TestCase;

/**
 * Failure tests for <code>ResourceFilterBuilder</code>.
 *
 * @author mayi
 * @author liuliquan
 * @version 1.1
 * @since 1.0
 */
public class ResourceFilterBuilderFailureTest extends TestCase {

    /**
     * Test <code>createResourceIdFilter(long)</code> with zero id.
     * <p>IllegalArgumentException should be thrown.</p>
     */
    public void testCreateResourceIdFilter_ZeroId() {
        try {
            ResourceFilterBuilder.createResourceIdFilter(0);
            fail("Should throw IllegalArgumentException for zero id.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test <code>createResourceIdFilter(long)</code> with negative id.
     * <p>IllegalArgumentException should be thrown.</p>
     */
    public void testCreateResourceIdFilter_NegativeId() {
        try {
            ResourceFilterBuilder.createResourceIdFilter(-2L);
            fail("Should throw IllegalArgumentException for negative id.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test <code>createPhaseIdFilter(long)</code> with zero id.
     * <p>IllegalArgumentException should be thrown.</p>
     */
    public void testCreateProjectIdFilter_ZeroId() {
        try {
            ResourceFilterBuilder.createPhaseIdFilter(0);
            fail("Should throw IllegalArgumentException for zero id.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test <code>createPhaseIdFilter(long)</code> with negative id.
     * <p>IllegalArgumentException should be thrown.</p>
     */
    public void testCreateProjectIdFilter_NegativeId() {
        try {
            ResourceFilterBuilder.createPhaseIdFilter(-2L);
            fail("Should throw IllegalArgumentException for negative id.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test <code>createSubmissionIdFilter(long)</code> with zero id.
     * <p>IllegalArgumentException should be thrown.</p>
     */
    public void testCreateSubmissionIdFilter_ZeroId() {
        try {
            ResourceFilterBuilder.createSubmissionIdFilter(0);
            fail("Should throw IllegalArgumentException for zero id.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test <code>createSubmissionIdFilter(long)</code> with negative id.
     * <p>IllegalArgumentException should be thrown.</p>
     */
    public void testCreateSubmissionIdFilter_NegativeId() {
        try {
            ResourceFilterBuilder.createSubmissionIdFilter(-2L);
            fail("Should throw IllegalArgumentException for negative id.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test <code>createResourceRoleIdFilter(long)</code> with zero id.
     * <p>IllegalArgumentException should be thrown.</p>
     */
    public void testCreateResourceRoleIdFilter_ZeroId() {
        try {
            ResourceFilterBuilder.createResourceRoleIdFilter(0);
            fail("Should throw IllegalArgumentException for zero id.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test <code>createResourceRoleIdFilter(long)</code> with negative id.
     * <p>IllegalArgumentException should be thrown.</p>
     */
    public void testCreateResourceRoleIdFilter_NegativeId() {
        try {
            ResourceFilterBuilder.createResourceRoleIdFilter(-2L);
            fail("Should throw IllegalArgumentException for negative id.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test <code>createExtensionPropertyValueFilter(String)</code> with null value.
     * <p>IllegalArgumentException should be thrown.</p>
     */
    public void testCreateExtensionPropertyNameFilter_NullValue() {
        try {
            ResourceFilterBuilder.createExtensionPropertyValueFilter(null);
            fail("Should throw IllegalArgumentException for null value.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test <code>createExtensionPropertyNameFilter(String)</code> with null name.
     * <p>IllegalArgumentException should be thrown.</p>
     */
    public void testCreateExtensionPropertyNameFilter_NullName() {
        try {
            ResourceFilterBuilder.createExtensionPropertyNameFilter(null);
            fail("Should throw IllegalArgumentException for null name.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Failure test for createAnySubmissionIdFilter().
     */
    public void testCreateAnySubmissionIdFilter_Failure1() {
        try {
            ResourceFilterBuilder.createAnySubmissionIdFilter(null);
            fail("IllegalArgumentException is expected since parameter is null.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Failure test for createAnySubmissionIdFilter().
     */
    public void testCreateAnySubmissionIdFilter_Failure2() {
        long[] subs = new long[] {1, 2, 0L};
        try {
            ResourceFilterBuilder.createAnySubmissionIdFilter(subs);
            fail("IllegalArgumentException is expected since parameter contains non positive value.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Failure test for createAnySubmissionIdFilter().
     */
    public void testCreateAnySubmissionIdFilter_Failure3() {
        long[] subs = new long[] {1, 2, -1L};
        try {
            ResourceFilterBuilder.createAnySubmissionIdFilter(subs);
            fail("IllegalArgumentException is expected since parameter contains non positive value.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }
}
