/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.review.data.stresstests;

import com.topcoder.management.review.data.Comment;
import com.topcoder.management.review.data.Item;

import junit.framework.TestCase;


/**
 * Stress test for Item class.
 *
 * @author mgmg
 * @version 1.0
 */
public class ItemStressTest extends TestCase {
    /**
     * The test instance.
     */
    private Item instance = null;

    /**
     * The test comment array.
     */
    private Comment[] comments = null;

    /**
     * Create the test instance.
     */
    public void setUp() {
        instance = new Item(123);
        comments = new Comment[StressTestHelper.testCount];

        for (int i = 0; i < StressTestHelper.testCount; i++) {
            comments[i] = new Comment(10 + i);
        }

        instance.addComments(comments);
    }

    /**
     * Stress test for addComment.
     */
    public void testAddComment() {
        StressTestHelper.startTimer();

        for (int i = 0; i < StressTestHelper.testCount; i++) {
            instance.addComment(new Comment(1000 + i));
        }

        StressTestHelper.printTime("Item.addComment", -1);
    }

    /**
     * Stress test for addComments.
     */
    public void testAddComments() {
        StressTestHelper.startTimer();

        for (int i = 0; i < StressTestHelper.testCount; i++) {
            instance.addComments(new Comment[] {
                    new Comment(1000 + i), new Comment(100000 + i), new Comment(10000 + i)
                });
        }

        StressTestHelper.printTime("Item.addComments", -1);
    }

    /**
     * Stress test for removeComment.
     */
    public void testRemoveComment() {
        StressTestHelper.startTimer();

        for (int i = 0; i < StressTestHelper.testCount; i++) {
            instance.removeComment(comments[i]);
        }

        StressTestHelper.printTime("Item.removeComment", -1);
    }

    /**
     * Stress test for removeComment.
     */
    public void testRemoveCommentById() {
        StressTestHelper.startTimer();

        for (int i = 0; i < StressTestHelper.testCount; i++) {
            instance.removeComment(comments[i].getId());
        }

        StressTestHelper.printTime("Item.removeComment by id", -1);
    }

    /**
     * Stress test for getAllComments.
     */
    public void testGetAllComments() {
        StressTestHelper.startTimer();

        for (int i = 0; i < StressTestHelper.testCount; i++) {
            instance.getAllComments();
        }

        StressTestHelper.printTime("Item.getAllComments", -1);
    }

    /**
     * Stress test for getComment.
     */
    public void testGetComment() {
        StressTestHelper.startTimer();

        for (int i = 0; i < StressTestHelper.testCount; i++) {
            instance.getComment(i);
        }

        StressTestHelper.printTime("Item.getComment", -1);
    }
}
