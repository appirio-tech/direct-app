/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.review.data;

import java.text.SimpleDateFormat;
import java.util.Date;

import junit.framework.TestCase;

/**
 * <p>
 * Reviews are produced based on scorecards. A review holds a collection of items which address each of the questions on
 * the scorecard. It also consists of the author that produced the review, the submission it addresses and the scorecard
 * template it is based on. Various types of comments can be attached to the review or to each review item. A committed
 * review must address all questions on the corresponding scorecard, and will have its overall score available. This
 * component defines the object model, hierarchy, and data structures for a review.
 * </p>
 * <p>
 * All of the classes that make up this model (<code>Review</code>, <code>Item</code>, <code>Comment</code>,
 * and <code>CommentType</code>) possess a Java Bean-like interface. That is, they all have a no argument
 * constructor, and all properties have both getter and setter methods. Convenience constructors for setting some fields
 * are also provided.
 * </p>
 * <p>
 * This class provides the demo usage of this component.
 * </p>
 *
 * @author vividmxx
 * @version 1.0
 */
public class DemoTests extends TestCase {

    /**
     * Demonstrates how to create a review hierarchy, and how to retrieve data from a review hierarchy. Because both
     * creation and retrieve are the operations on the review hierarchy, so there is no need to write them as two
     * separated test cases.
     *
     * @throws Exception
     *             throws Exception to JUnit
     */
    public void testCreatingAndUsingReviewHierarchy() throws Exception {
        // Create the review
        Review review = new Review();

        // Set review data values
        review.setId(1);
        review.setAuthor(45);
        review.setSubmission(12);
        review.setCommitted(true);
        review.setScore(new Float(75.0));
        review.setModificationUser("Santa");
        review.setModificationTimestamp(new SimpleDateFormat("yyyy-MM-dd").parse("2000-1-1"));

        // Set the scorecard and then demonstrate how to reset it to the unassigned value
        review.setScorecard(1);
        review.resetScorecard();

        // Create comment type (in any real system, this would definitely be loaded from some persistence of standard
        // comment types)
        CommentType commentType = new CommentType(1, "Standard Comment");

        // Create comment for review
        Comment reviewComment = new Comment(1);

        // Set comment data values
        reviewComment.setCommentType(commentType);
        reviewComment.setAuthor(472);
        // Note how for non-primitive values, null is allowed in the setter as a way to set the value as "unassigned"
        reviewComment.setExtraInfo(null);

        // Add comment to review
        review.addComment(reviewComment);

        // Create items
        Item item1 = new Item(445);
        item1.setAnswer("Great way to solve this problem!");
        item1.setQuestion(46);
        item1.setDocument(new Long(14566));

        Item item2 = new Item();
        item2.setId(5);
        item2.setAnswer("See comment");
        item2.setQuestion(8);
        // Note no need to set document, as no attached document is the default state.

        // Create item comment
        Comment itemComment = new Comment();
        itemComment.setCommentType(commentType);
        itemComment.setAuthor(472);
        itemComment.setExtraInfo(new Double(12.2));

        // Add comment to item
        item2.addComment(itemComment);

        // Add items to review
        review.addItems(new Item[] {item1, item2});

        // Retrieve data from the review
        long id = review.getId();
        assertTrue("id should be 1", id == 1);
        long author = review.getAuthor();
        assertTrue("author should be 45", author == 45);
        // Continue in like manner for other data fields...

        // Retrieve comments
        Comment[] comments = review.getAllComments();
        // And iterate over comments
        for (int i = 0; i < comments.length; i++) {
            Comment comment = comments[i];
            long commentId = comment.getId();
            // Continue in like manner for other data fields...
            commentType = comment.getCommentType();
            String commentTypeName = commentType.getName();
        }

        // Retrieve items
        Item[] items = review.getAllItems();

        // Iterate over the items
        for (int i = 0; i < items.length; i++) {
            Item item = items[i];
            long itemId = review.getId();
            // Continue in like manner for other data fields...
            Comment[] itemComments = item.getAllComments();
            // Iterate over comments as above...
        }
    }

    /**
     * Demonstrates how to use the <code>ReviewEditor</code>.
     */
    public void testUsingReviewEditor() {
        Review review = new Review();

        // Create a ReviewEditor for editing the properties of the review while automatically updating modification
        // user and datetime
        ReviewEditor editor = new ReviewEditor(review, "The Grinch");

        // Update a property of the review, automatically updating the modification user and datetime
        editor.setScorecard(7);
        editor.resetScorecard();

        // Retrieve the modification date and user. These will now be the "The Grinch" and the current datetime
        String modificationUser = editor.getReview().getModificationUser();
        Date modificationDate = editor.getReview().getModificationTimestamp();

        assertTrue("modificationUser should be 'The Grinch'", modificationUser.equals("The Grinch"));
        assertTrue("modificationDate should be now", modificationDate.equals(new Date()));
    }
}
