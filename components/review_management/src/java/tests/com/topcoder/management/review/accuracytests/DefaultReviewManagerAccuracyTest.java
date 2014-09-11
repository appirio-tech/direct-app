/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * DefaultReviewManagerAccuracyTest.java
 */
package com.topcoder.management.review.accuracytests;

import junit.framework.TestCase;
import com.topcoder.management.review.DefaultReviewManager;
import com.topcoder.management.review.data.Review;
import com.topcoder.management.review.data.Comment;
import com.topcoder.search.builder.filter.EqualToFilter;

/**
 * <p>
 * Accuracy tests for <code>DefaultReviewManager</code> class.
 * </p>
 *
 * @author wiedzmin
 * @version 1.0
 */
public class DefaultReviewManagerAccuracyTest extends TestCase {

    /**Namespace.*/
    private static final String NAMESPACE = "com.topcoder.management.review.accuracy";

    /**ReviewPersistence instance that is used for testing.*/
    private ReviewPersistenceImpl persistence;

    /**DefaultReviewManager instance that will be tested.*/
    private DefaultReviewManager reviewManager;

    /**
     * <p>
     * Set up environment.
     * </p>
     *
     * @throws Exception exception
     */
    public void setUp() throws Exception {
        Helper.initNamespace();

        persistence = new ReviewPersistenceImpl();
        reviewManager = new DefaultReviewManager(persistence);
    }

    /**
     * <p>
     * Tear down environment.
     * </p>
     *
     * @throws Exception exception
     */
    public void tearDown() throws Exception {
        Helper.clearNamespace();
    }

    /**
     * <p>
     * Test constructor.
     * In this test we will use constructor without arguments - default namespace should be used.
     * </p>
     *
     * @throws Exception exception
     */
    public void testCtorWithDefaultNamespace() throws Exception {
        assertNotNull("DefaultReviewManager is not created correctly using default namespace.",
                new DefaultReviewManager());
    }

    /**
     * <p>
     * Test constructor.
     * In this test we will use constructor with namespace argument. Configuration under this
     * namespace contains both persistence_class and persistence_namespace properties.
     * </p>
     *
     * @throws Exception exception
     */
    public void testCtorWithNamespace1() throws Exception {
        assertNotNull("DefaultReviewManager is not created correctly using valid configuration.",
                new DefaultReviewManager(NAMESPACE + 1));
    }

    /**
     * <p>
     * Test constructor.
     * In this test we will use constructor with namespace argument. Configuration under this
     * namespace contains only persistence_class property.
     * </p>
     *
     * @throws Exception exception
     */
    public void testCtorWithNamespace2() throws Exception {
        assertNotNull("DefaultReviewManager is not created correctly using only persistence_class property.",
                new DefaultReviewManager(NAMESPACE + 2));
    }

    /**
     * <p>
     * Test constructor.
     * In this test we will use constructor with ReviewPersistence argument.
     * </p>
     *
     * @throws Exception exception
     */
    public void testCtorWithPersistence() throws Exception {
        assertNotNull("DefaultReviewManager is not created correctly using valid ReviewPersistence.",
                new DefaultReviewManager(persistence));
    }

    /**
     * <p>
     * Test method createReview().
     * Since DefaultReviewManager.createReview should simply call persistence.createReview,
     * we only check that method persistence.createReview really was called.
     * </p>
     *
     * @throws Exception exception
     */
    public void testCreateReview() throws Exception {
        //be sure that persistence.createReview was not called yet
        assertEquals(0, persistence.createReviewCallCount());

        //now call three times reviewManager.createReview
        reviewManager.createReview(new Review(2), "operator");
        reviewManager.createReview(new Review(3), "operator");
        reviewManager.createReview(new Review(4), "operator");

        //now check that method call was delegated to persistence.createReview
        assertEquals("ReviewPersistence.createReview method was not called.",
                3, persistence.createReviewCallCount());
    }

    /**
     * <p>
     * Test method updateReview().
     * Since DefaultReviewManager.updateReview should simply call persistence.updateReview,
     * we only check that method persistence.updateReview really was called.
     * </p>
     *
     * @throws Exception exception
     */
    public void testUpdateReview() throws Exception {
        //be sure that persistence.updateReview was not called yet
        assertEquals(0, persistence.updateReviewCallCount());

        //now call three times reviewManager.updateReview
        reviewManager.updateReview(new Review(2), "operator");
        reviewManager.updateReview(new Review(3), "operator");
        reviewManager.updateReview(new Review(4), "operator");

        //now check that method call was delegated to persistence.updateReview
        assertEquals("ReviewPersistence.updateReview method was not called.",
                3, persistence.updateReviewCallCount());
    }

    /**
     * <p>
     * Test method getReview().
     * Since DefaultReviewManager.getReview should simply call persistence.getReview,
     * we only check that method persistence.getReview really was called.
     * </p>
     *
     * @throws Exception exception
     */
    public void testGetReview() throws Exception {
        //be sure that persistence.getReview was not called yet
        assertEquals(0, persistence.getReviewCallCount());

        //now call two times reviewManager.getReview
        reviewManager.getReview(3);
        reviewManager.getReview(4);

        //now check that method call was delegated to persistence.getReview
        assertEquals("ReviewPersistence.getReview method was not called.",
                2, persistence.getReviewCallCount());
    }

    /**
     * <p>
     * Test method searchReviews().
     * Since DefaultReviewManager.searchReviews should simply call persistence.searchReviews,
     * we only check that method persistence.searchReviews really was called.
     * </p>
     *
     * @throws Exception exception
     */
    public void testSearchReviews() throws Exception {
        //be sure that persistence.searchReviews was not called yet
        assertEquals(0, persistence.searchReviewsCallCount());

        //now call two times reviewManager.searchReviews
        reviewManager.searchReviews(new EqualToFilter("age", "john"), true);
        reviewManager.searchReviews(new EqualToFilter("age", "mark"), false);

        //now check that method call was delegated to persistence.searchReviews
        assertEquals("ReviewPersistence.searchReviews method was not called.",
                2, persistence.searchReviewsCallCount());
    }

    /**
     * <p>
     * Test method addReviewComment().
     * Since DefaultReviewManager.addReviewComment should simply call persistence.addReviewComment,
     * we only check that method persistence.addReviewComment really was called.
     * </p>
     *
     * @throws Exception exception
     */
    public void testAddReviewComment() throws Exception {
        //be sure that persistence.addReviewComment was not called yet
        assertEquals(0, persistence.addReviewCommentCallCount());

        //now call two times reviewManager.addReviewComment
        reviewManager.addReviewComment(1, new Comment(1), "operator");
        reviewManager.addReviewComment(1, new Comment(2), "operator");

        //now check that method call was delegated to persistence.addReviewComment
        assertEquals("ReviewPersistence.addReviewComment method was not called.",
                2, persistence.addReviewCommentCallCount());
    }

    /**
     * <p>
     * Test method addItemComment().
     * Since DefaultReviewManager.addItemComment should simply call persistence.addItemComment,
     * we only check that method persistence.addItemComment really was called.
     * </p>
     *
     * @throws Exception exception
     */
    public void testAddItemComment() throws Exception {
        //be sure that persistence.addItemComment was not called yet
        assertEquals(0, persistence.addItemCommentCallCount());

        //now call two times reviewManager.addItemComment
        reviewManager.addItemComment(1, new Comment(1), "operator");
        reviewManager.addItemComment(1, new Comment(2), "operator");

        //now check that method call was delegated to persistence.addItemComment
        assertEquals("ReviewPersistence.addItemComment method was not called.",
                2, persistence.addItemCommentCallCount());
    }

    /**
     * <p>
     * Test method getAllCommentTypes().
     * Since DefaultReviewManager.getAllCommentTypes should simply call persistence.getAllCommentTypes,
     * we only check that method persistence.getAllCommentTypes really was called.
     * </p>
     *
     * @throws Exception exception
     */
    public void testGetAllCommentTypes() throws Exception {
        //be sure that persistence.getAllCommentTypes was not called yet
        assertEquals(0, persistence.getAllCommentTypesCallCount());

        //now call two times reviewManager.getAllCommentTypes
        reviewManager.getAllCommentTypes();
        reviewManager.getAllCommentTypes();

        //now check that method call was delegated to persistence.getAllCommentTypes
        assertEquals("ReviewPersistence.getAllCommentTypes method was not called.",
                2, persistence.getAllCommentTypesCallCount());
    }
}