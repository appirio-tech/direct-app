/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.review.specification.accuracytests;

import java.io.Serializable;

import junit.framework.TestCase;

import com.topcoder.management.review.data.Review;
import com.topcoder.management.scorecard.data.Scorecard;
import com.topcoder.service.review.specification.SpecificationReview;

/**
 * <p>
 * Accuracy tests for class <code>SpecificationReview</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class SpecificationReviewAccuracyTest extends TestCase {

    /**
     * The <code>SpecificationReview</code> instance for accuracy test.
     */
    private SpecificationReview review;

    /**
     * <p>
     * Set the test environment.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        review = new SpecificationReview();
    }

    /**
     * <p>
     * Clear the test environment.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        review = null;
    }

    /**
     * <p>
     * Accuracy test for the constructor.<br>
     * Instance should be created successfully.
     * </p>
     */
    public void testConstructor() {
        assertNotNull("The instance should be created successfully", review);
        assertTrue(review instanceof Serializable);
    }

    /**
     * <p>
     * Accuracy test for the method <code>getReview()</code>.
     * </p>
     *
     *@throws Exception to JUnit
     */
    public void testGetReview() throws Exception {
        assertNull(review.getReview());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setReview(Review review)</code>.
     * </p>
     *
     *@throws Exception to JUnit
     */
    public void testSetReview() throws Exception {
        Review local = new Review();
        review.setReview(local);
        assertEquals(local, review.getReview());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getScorecard()</code>.
     * </p>
     *
     *@throws Exception to JUnit
     */
    public void testGetScorecard() throws Exception {
        assertNull(review.getScorecard());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setScorecard(Scorecard scorecard)</code>.
     * </p>
     *
     *@throws Exception to JUnit
     */
    public void testSetScorecard() throws Exception {
        Scorecard card = new Scorecard();
        review.setScorecard(card);
        assertEquals(card, review.getScorecard());
    }
}
