/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.review.stresstests;

import junit.framework.TestCase;

import com.topcoder.management.review.DefaultReviewManager;
import com.topcoder.management.review.ReviewManager;

/**
 * <p>
 * Stress test of DefaultReviewManager.
 * </p>
 * @author still
 * @version 1.0
 */
public class DefaultReviewManagerStressTest extends TestCase {
    /** The number of times each method will be run. */
    public static final int RUN_TIMES = 10000;

    /**
     * Set up testing environment.
     * @throws Exception to junit.
     */
    public void setUp() throws Exception {
        StressTestHelper.loadConfig();
    }

    /**
     * Tear down testing environment.
     * @throws Exception to junit.
     */
    public void tearDown() throws Exception {
        StressTestHelper.unloadConfig();
    }

    /**
     * Stress test of the default constructor of DefaultReviewManager.
     * @throws Exception to junit.
     */
    public void testCtor() throws Exception {
        long start = System.currentTimeMillis();
        for (int i = 0; i < RUN_TIMES; i++) {
            assertNotNull("Failed to create DefaultReviewManager.",
                    new DefaultReviewManager());
        }
        long end = System.currentTimeMillis();
        System.out.println("Testing DefaultReviewManager() for " + RUN_TIMES + " times costs "
                + (end - start) + "ms");
    }

    /**
     * Stress test of the namespace constructor of DefaultReviewManager.
     * @throws Exception to junit.
     */
    public void testCtor_namespace() throws Exception {
        long start = System.currentTimeMillis();
        for (int i = 0; i < RUN_TIMES; i++) {
            assertNotNull("Failed to create DefaultReviewManager.",
                    new DefaultReviewManager("test1"));
        }
        long end = System.currentTimeMillis();
        System.out.println("Testing DefaultReviewManager(String) for " + RUN_TIMES + " times costs "
                + (end - start) + "ms");
    }


    /**
     * Stress test of the persistence constructor of DefaultReviewManager.
     * @throws Exception to junit.
     */
    public void testCtor_persistence() throws Exception {
        long start = System.currentTimeMillis();
        for (int i = 0; i < RUN_TIMES; i++) {
            assertNotNull("Failed to create DefaultReviewManager.",
                    new DefaultReviewManager(new StressMockReviewPersistence()));
        }
        long end = System.currentTimeMillis();
        System.out.println("Testing DefaultReviewManager(ReviewPersistence) for " + RUN_TIMES + " times costs "
                + (end - start) + "ms");
    }

    /**
     * Stress test of DefaultReviewManager.createReview(Review, String).
     * @throws Exception to junit
     */
    public void testCreateReview() throws Exception {
        long start = System.currentTimeMillis();

        for (int i = 0; i < RUN_TIMES; i++) {
            StressMockReviewPersistence persistence = new StressMockReviewPersistence();
            ReviewManager manager = new DefaultReviewManager(persistence);
            manager.createReview(null, null);
            persistence.assertMethodCalled("createReview", 1);
        }

        long end = System.currentTimeMillis();
        System.out.println("Testing createReview(Review, String) for " + RUN_TIMES + " times costs "
                + (end - start) + "ms");
    }

    /**
     * Stress test of DefaultReviewManager.updateReview(Review, String).
     * @throws Exception to junit
     */
    public void testUpdateReview() throws Exception {
        long start = System.currentTimeMillis();

        for (int i = 0; i < RUN_TIMES; i++) {
            StressMockReviewPersistence persistence = new StressMockReviewPersistence();
            ReviewManager manager = new DefaultReviewManager(persistence);
            manager.updateReview(null, null);
            persistence.assertMethodCalled("updateReview", 1);
        }

        long end = System.currentTimeMillis();
        System.out.println("Testing updateReview(Review, String) for " + RUN_TIMES + " times costs "
                + (end - start) + "ms");
    }

    /**
     * Stress test of DefaultReviewManager.getReview(long).
     * @throws Exception to junit
     */
    public void testGetReview() throws Exception {
        long start = System.currentTimeMillis();

        for (int i = 0; i < RUN_TIMES; i++) {
            StressMockReviewPersistence persistence = new StressMockReviewPersistence();
            ReviewManager manager = new DefaultReviewManager(persistence);
            manager.getReview(0);
            persistence.assertMethodCalled("getReview", 1);
        }

        long end = System.currentTimeMillis();
        System.out.println("Testing getReview(long) for " + RUN_TIMES + " times costs "
                + (end - start) + "ms");
    }

    /**
     * Stress test of DefaultReviewManager.searchReviews(Filter, boolean).
     * @throws Exception to junit
     */
    public void testSearchReviews() throws Exception {
        long start = System.currentTimeMillis();

        for (int i = 0; i < RUN_TIMES; i++) {
            StressMockReviewPersistence persistence = new StressMockReviewPersistence();
            ReviewManager manager = new DefaultReviewManager(persistence);
            manager.searchReviews(null, true);
            persistence.assertMethodCalled("searchReviews", 1);
        }

        long end = System.currentTimeMillis();
        System.out.println("Testing searchReviews(Filter, boolean) for " + RUN_TIMES + " times costs "
                + (end - start) + "ms");
    }

    /**
     * Stress test of DefaultReviewManager.addReviewComment(long, Comment, String).
     * @throws Exception to junit
     */
    public void testAddReviewComment() throws Exception {
        long start = System.currentTimeMillis();

        for (int i = 0; i < RUN_TIMES; i++) {
            StressMockReviewPersistence persistence = new StressMockReviewPersistence();
            ReviewManager manager = new DefaultReviewManager(persistence);
            manager.addReviewComment(0, null, null);
            persistence.assertMethodCalled("addReviewComment", 1);
        }

        long end = System.currentTimeMillis();
        System.out.println("Testing addReviewComment(long, Comment, String) for " + RUN_TIMES + " times costs "
                + (end - start) + "ms");
    }

    /**
     * Stress test of DefaultReviewManager.addItemComment(long, Comment, String).
     * @throws Exception to junit
     */
    public void testAddItemComment() throws Exception {
        long start = System.currentTimeMillis();

        for (int i = 0; i < RUN_TIMES; i++) {
            StressMockReviewPersistence persistence = new StressMockReviewPersistence();
            ReviewManager manager = new DefaultReviewManager(persistence);
            manager.addItemComment(0, null, null);
            persistence.assertMethodCalled("addItemComment", 1);
        }

        long end = System.currentTimeMillis();
        System.out.println("Testing addItemComment(long, Comment, String) for " + RUN_TIMES + " times costs "
                + (end - start) + "ms");
    }

    /**
     * Stress test of DefaultReviewManager.getAllCommentTypes()().
     * @throws Exception to junit
     */
    public void testGetAllCommentTypes() throws Exception {
        long start = System.currentTimeMillis();

        for (int i = 0; i < RUN_TIMES; i++) {
            StressMockReviewPersistence persistence = new StressMockReviewPersistence();
            ReviewManager manager = new DefaultReviewManager(persistence);
            manager.getAllCommentTypes();
            persistence.assertMethodCalled("getAllCommentTypes", 1);
        }

        long end = System.currentTimeMillis();
        System.out.println("Testing getAllCommentTypes() for " + RUN_TIMES + " times costs "
                + (end - start) + "ms");
    }
}
