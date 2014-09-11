/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.review.specification;

import junit.framework.TestCase;


/**
 * <p>Unit tests for <code>{@link SpecificationReviewStatus}</code> enum.</p>
 *
 * @author myxgyy
 * @version 1.0
 */
public class SpecificationReviewStatusTests extends TestCase {
    /**
     * Tests {@link SpecificationReviewStatus#PENDING_REVIEW} enum constant.
     */
    public void test_pending_review() {
        assertEquals("should be 'PENDING_REVIEW'", "PENDING_REVIEW",
            SpecificationReviewStatus.PENDING_REVIEW.toString());
        assertTrue("should get PENDING_REVIEW status",
            SpecificationReviewStatus.PENDING_REVIEW == Enum.valueOf(
                SpecificationReviewStatus.class, "PENDING_REVIEW"));
    }

    /**
     * Tests {@link SpecificationReviewStatus#WAITING_FOR_FIXES} enum constant.
     */
    public void test_waiting_for_fixes() {
        assertEquals("should be 'WAITING_FOR_FIXES'", "WAITING_FOR_FIXES",
            SpecificationReviewStatus.WAITING_FOR_FIXES.toString());
        assertTrue("should get WAITING_FOR_FIXES sort order",
            SpecificationReviewStatus.WAITING_FOR_FIXES == Enum.valueOf(
                SpecificationReviewStatus.class, "WAITING_FOR_FIXES"));
    }
}
