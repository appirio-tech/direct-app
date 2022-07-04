/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.review.data.stresstests;

import com.topcoder.management.review.data.Comment;
import com.topcoder.management.review.data.Item;
import com.topcoder.management.review.data.ReviewEditor;

import junit.framework.TestCase;


/**
 * Stress test for ReviewEditor.
 *
 * @author mgmg
 * @version 1.0
 */
public class ReviewEditorStressTest extends TestCase {
    /**
     * The test instance.
     */
    private ReviewEditor instance = null;

    /**
     * The test comment array.
     */
    private Comment[] comments = null;

    /**
     * The test item array.
     */
    private Item[] items = null;

    /**
     * Create the test instance.
     */
    public void setUp() {
        instance = new ReviewEditor("mgmg");

        comments = new Comment[StressTestHelper.testCount];

        for (int i = 0; i < StressTestHelper.testCount; i++) {
            comments[i] = new Comment(10 + i);
        }

        instance.addComments(comments);

        items = new Item[StressTestHelper.testCount];

        for (int i = 0; i < StressTestHelper.testCount; i++) {
            items[i] = new Item(10 + i);
        }

        instance.addItems(items);
    }

    /**
     * Stress test for addComment.
     */
    public void testAddComment() {
        StressTestHelper.startTimer();

        for (int i = 0; i < StressTestHelper.testCount; i++) {
            instance.addComment(new Comment(1000 + i));
        }

        StressTestHelper.printTime("ReviewEditor.addComment", -1);
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

        StressTestHelper.printTime("ReviewEditor.addComments", -1);
    }

    /**
     * Stress test for removeComment.
     */
    public void testRemoveComment() {
        StressTestHelper.startTimer();

        for (int i = 0; i < StressTestHelper.testCount; i++) {
            instance.removeComment(comments[i]);
        }

        StressTestHelper.printTime("ReviewEditor.removeComment", -1);
    }

    /**
     * Stress test for addItem.
     */
    public void testAddItem() {
        StressTestHelper.startTimer();

        for (int i = 0; i < StressTestHelper.testCount; i++) {
            instance.addItem(new Item(1000 + i));
        }

        StressTestHelper.printTime("ReviewEditor.addItem", -1);
    }

    /**
     * Stress test for addItems.
     */
    public void testAddItems() {
        StressTestHelper.startTimer();

        for (int i = 0; i < StressTestHelper.testCount; i++) {
            instance.addItems(new Item[] {new Item(1000 + i), new Item(100000 + i), new Item(10000 + i)});
        }

        StressTestHelper.printTime("ReviewEditor.addItems", -1);
    }

    /**
     * Stress test for removeItem.
     */
    public void testRemoveItem() {
        StressTestHelper.startTimer();

        for (int i = 0; i < StressTestHelper.testCount; i++) {
            instance.removeItem(items[i]);
        }

        StressTestHelper.printTime("ReviewEditor.removeItem", -1);
    }
}
