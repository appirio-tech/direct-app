/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.review.specification;

import com.topcoder.management.review.data.Review;
import com.topcoder.management.scorecard.data.Scorecard;

import junit.framework.TestCase;


/**
 * <p>Unit tests for <code>{@link SpecificationReview}</code> class.</p>
 *
 * @author myxgyy
 * @version 1.0
 */
public class SpecificationReviewTests extends TestCase {
    /**
     * The <code>{@link SpecificationReview}</code> instance used for testing.
     */
    private SpecificationReview review;

    /**
     * <p>Sets up the test environment.</p>
     *
     * @throws Exception throws exception if any.
     */
    protected void setUp() throws Exception {
        review = new SpecificationReview();
    }

    /**
     * Accuracy test case for constructor of <code>{@link SpecificationReview}</code>
     * class.
     */
    public void testConstructor() {
        assertNull("review should be null", review.getReview());
        assertNull("scorecard should be null", review.getScorecard());
    }

    /**
     * Accuracy test case for <code>{@link SpecificationReview#getReview()}</code> method.
     */
    public void test_getReview() {
        assertNull("review should be retrieved", review.getReview());
    }

    /**
     * Accuracy test case for <code>{@link SpecificationReview#setReview(Review)}</code>
     * method.
     */
    public void test_setReview() {
        Review r = new Review();
        review.setReview(r);
        assertSame("review should be set", r, review.getReview());
    }

    /**
     * Accuracy test case for <code>{@link SpecificationReview#getScorecard()}</code>
     * method.
     */
    public void test_getScorecard() {
        assertNull("scorecard should be retrieved", review.getScorecard());
    }

    /**
     * Accuracy test case for <code>{@link
     * SpecificationReview#setScorecard(Scorecard)}</code> method.
     */
    public void test_setScorecard() {
        Scorecard scorecard = new Scorecard();
        review.setScorecard(scorecard);
        assertSame("scorecard should be set", scorecard, review.getScorecard());
    }
}
