/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.review;

import com.topcoder.management.review.data.Comment;
import com.topcoder.management.review.data.CommentType;
import com.topcoder.management.review.data.Item;
import com.topcoder.management.review.data.Review;

import com.topcoder.search.builder.filter.Filter;

import java.util.HashMap;
import java.util.Map;


/**
 * <p>
 * This <code>MockReviewPersistence</code> class is the implementation of
 * <code>ReviewPersistence</code> interface. It provides the persistence functionalities to
 * create, update and search review entities in cache. It can also be used to add comment for
 * review and item, and get all the comment types from the persistence.
 * </p>
 *
 * <p>
 * Here it is only a mock of the <code>InformixReviewPersistence</code> class for test.
 * </p>
 *
 * <p>
 * Thread safety: This class is not required to be thread-safe.
 * </p>
 *
 * @author icyriver
 * @version 1.0
 */
class MockReviewPersistence implements ReviewPersistence {
    /**
     * <p>
     * This variable represents the comment type of this <code>MockReviewPersistence</code> class.
     * </p>
     */
    private static CommentType commentType = new CommentType(1, "Comment");

    /**
     * <p>
     * This variable represents the comment type of this <code>MockReviewPersistence</code> class.
     * </p>
     */
    private static CommentType recommendType = new CommentType(2, "Recommend");

    /**
     * <p>
     * This variable represents the comment type of this <code>MockReviewPersistence</code> class.
     * </p>
     */
    private static CommentType requiredType = new CommentType(3, "Required");

    /**
     * <p>
     * This variable represents the namespace of this <code>MockReviewPersistence</code> class.
     * </p>
     */
    private String namespace = null;

    /**
     * <p>
     * This variable represents if the <code>MockReviewPersistence</code> is stopped. It is used to
     * make the methods throw <code>ReviewPersistenceException</code>.
     * </p>
     */
    private boolean isStopped = false;

    /**
     * <p>
     * This variable represents the map containing review entities of this
     * <code>MockReviewPersistence</code> class. The key of this map is the id of review entity,
     * the value is the corresponding review entity.
     * </p>
     */
    private Map reviewsMap = new HashMap();

    /**
     * <p>
     * This variable represents the map containing items of this <code>MockReviewPersistence</code>
     * class. The key of this map is the id of items, the value is the corresponding items.
     * </p>
     */
    private Map itemsMap = new HashMap();

    /**
     * <p>
     * Create the <code>MockReviewPersistence</code> instance from the default namespace. It will
     * do nothing here.
     * </p>
     */
    public MockReviewPersistence() {
    }

    /**
     * <p>
     * Create the <code>MockReviewPersistence</code> instance from the given namespace.
     * </p>
     *
     * @param namespace the given namespace for this <code>MockReviewPersistence</code> class.
     *
     * @throws IllegalArgumentException if the namespace is null or empty string.
     */
    public MockReviewPersistence(String namespace) {
        TestHelper.checkString(namespace, "namespace");
        this.namespace = namespace;
    }

    /**
     * <p>
     * This method creates the review entity in the persistence. This method will also create the
     * associated <code>Comment</code> and <code>Item</code> instances into persistence.
     * </p>
     *
     * @param review the review entity to be created in the persistence.
     * @param operator the operator who creates the review entity.
     *
     * @throws IllegalArgumentException if any argument is null, or operator is empty string,
     *                                  or review fails to pass the validation.
     * @throws DuplicateReviewEntityException if review entity id already exists in the
     *         persistence.
     * @throws ReviewPersistenceException if any other error occurred.
     */
    public void createReview(Review review, String operator)
        throws ReviewPersistenceException {
        // check the persistence state here.
        validState();

        // chekc null and empty string here.
        TestHelper.checkNull(review, "review");
        TestHelper.checkString(operator, "operator");

        // check for duplicated review entity.
        Object key = new Long(review.getId());

        if (reviewsMap.containsKey(key)) {
            throw new DuplicateReviewEntityException("The review entity already exists in the persistence.",
                review.getId());
        }

        // add the review entity into persistence.
        review.setCreationUser(operator);
        review.setModificationUser(operator);
        reviewsMap.put(key, review);

        // add all items in review entity into persistence.
        Item[] itemsArray = review.getAllItems();

        for (int i = 0, m = itemsArray.length; i < m; i++) {
            itemsMap.put(new Long(itemsArray[i].getId()), itemsArray[i]);
        }
    }

    /**
     * <p>
     * This method updates the review entity in the persistence. This method will also be update
     * the associated Items, Comments, and Item Comments.
     * </p>
     *
     * @param review the review entity to update into the persistence.
     * @param operator the operator who updates the review entity.
     *
     * @throws IllegalArgumentException if arguments is null, or operator is empty string,
     *                                  or review fails to pass the validation.
     * @throws ReviewEntityNotFoundException if given review entity does not exist in the
     *         persistence.
     * @throws ReviewPersistenceException if any other error occurred.
     */
    public void updateReview(Review review, String operator)
        throws ReviewPersistenceException {
        // check the persistence state here.
        validState();

        // chekc null and empty string here.
        TestHelper.checkNull(review, "review");
        TestHelper.checkString(operator, "operator");

        // check for review entity.
        Object key = new Long(review.getId());

        if (!reviewsMap.containsKey(key)) {
            throw new ReviewEntityNotFoundException("The review entity does not exist in the persistence.",
                review.getId());
        }

        // remove all items in the old review entity from persistence.
        Item[] itemsArray = ((Review) reviewsMap.get(key)).getAllItems();

        for (int i = 0, m = itemsArray.length; i < m; i++) {
            itemsMap.remove(new Long(itemsArray[i].getId()));
        }

        // updated the review entity.
        review.setCreationUser(((Review) reviewsMap.get(key)).getCreationUser());
        review.setModificationUser(operator);
        reviewsMap.put(key, review);

        // all items in the new review entity from persistence.
        itemsArray = review.getAllItems();

        for (int i = 0, m = itemsArray.length; i < m; i++) {
            itemsMap.put(new Long(itemsArray[i].getId()), itemsArray[i]);
        }
    }

    /**
     * <p>
     * This method gets the review entity from the persistence with given review entity id.
     * </p>
     *
     * @param id the id of the review entity to be retrieved.
     *
     * @return the retrieved review entity with its id.
     *
     * @throws IllegalArgumentException if the given id is not positive.
     * @throws ReviewEntityNotFoundException if the given id does not exist in the persistence.
     * @throws ReviewPersistenceException if any other error occurred.
     */
    public Review getReview(long id) throws ReviewPersistenceException {
        // check the persistence state here.
        validState();

        // chekc for positive here.
        TestHelper.checkPositive(id, "id");

        // get the review entity with its id.
        Object review = reviewsMap.get(new Long(id));

        if (review == null) {
            throw new ReviewEntityNotFoundException("The review entity does not exist in the persistence.",
                id);
        }

        return (Review) review;
    }

    /**
     * <p>
     * This method will search the review entities with the given search filters. If the complete
     * argument is false, the assoicated items and comments of the matching review entity will not
     * been retrieved. Here it just return a empty array.
     * </p>
     *
     * @param filter the filter that specifies the search conditions.
     * @param complete a boolean variable indicating if the complete review data is retrieved.
     *
     * @return the array of matching review entities, or empty array if no review entity matches
     *         the filter.
     *
     * @throws IllegalArgumentException if the filter is null.
     * @throws ReviewPersistenceException if failed to search the review entities.
     */
    public Review[] searchReviews(Filter filter, boolean complete)
        throws ReviewPersistenceException {
        // check the persistence state here.
        validState();

        // check for null here.
        TestHelper.checkNull(filter, "filter");

        return new Review[0];
    }

    /**
     * <p>
     * This method will add comment to review with given review id.
     * </p>
     *
     * @param reviewId the id of review entity.
     * @param comment the Comment instance to be added.
     * @param operator the operator who adds the comment.
     *
     * @throws IllegalArgumentException if any argument is null, or reviewId is not positive,
     *                                  or operator is empty string.
     * @throws ReviewEntityNotFoundException if reviewId does not exists in the persistence.
     * @throws ReviewPersistenceException if any other error occurred.
     */
    public void addReviewComment(long reviewId, Comment comment, String operator)
        throws ReviewPersistenceException {
        // check the persistence state here.
        validState();

        // chekc the arguments here.
        TestHelper.checkPositive(reviewId, "reviewId");
        TestHelper.checkNull(comment, "comment");
        TestHelper.checkString(operator, "operator");

        // get the review entity with its id.
        Review review = getReview(reviewId);

        // add the comment for the review entity.
        review.setModificationUser(operator);
        review.addComment(comment);
    }

    /**
     * <p>
     * This method will add the comment to the item with given item id. This method is responsible
     * for creating the Comment, but not for the CommentType of the given comment.
     * </p>
     *
     * @param itemId the item id.
     * @param comment the Comment instance to be added.
     * @param operator the operator who adds the comment.
     *
     * @throws IllegalArgumentException if any argument is null, or itemId is not positive,
     *                                  or operator is empty string.
     * @throws ReviewEntityNotFoundException if itemId does not exists in the persistence.
     * @throws ReviewPersistenceException if any other error occurred.
     */
    public void addItemComment(long itemId, Comment comment, String operator)
        throws ReviewPersistenceException {
        // check the persistence state here.
        validState();

        // chekc the arguments here.
        TestHelper.checkPositive(itemId, "itemId");
        TestHelper.checkNull(comment, "comment");
        TestHelper.checkString(operator, "operator");

        // get the item with its id.
        Object item = itemsMap.get(new Long(itemId));

        if (item == null) {
            throw new ReviewEntityNotFoundException("The item does not exist in the persistence.",
                itemId);
        }

        ((Item) item).addComment(comment);
    }

    /**
     * <p>
     * This method will get all the <code>CommentType</code> instance from the persistence.
     * </p>
     *
     * @return the array of all <code>CommentType</code> instance from the persistence, or empty
     *         array if no comment type can be found.
     *
     * @throws ReviewPersistenceException if failed to get the comment types.
     */
    public CommentType[] getAllCommentTypes() throws ReviewPersistenceException {
        // check the persistence state here.
        validState();

        return new CommentType[] {commentType, recommendType, requiredType};
    }

    /**
     * <p>
     * This method will stop the state of this persistence. It is used to make the methods throw
     * <code>ReviewPersistenceException</code>.
     * </p>
     */
    public void stopState() {
        this.isStopped = true;
    }

    /**
     * <p>
     * This method will return the namespace of this persistence.
     * </p>
     *
     * @return the namespace of this persistence.
     */
    public String getNamespace() {
        return this.namespace;
    }

    /**
     * <p>
     * This method will valid the state of this persistence. It will throw
     * <code>ReviewPersistenceException</code> when the persistence is stopped.
     * </p>
     *
     * @throws ReviewPersistenceException if the persistence is stopped.
     */
    private void validState() throws ReviewPersistenceException {
        if (isStopped) {
            throw new ReviewPersistenceException("Error occurred in this persistence.");
        }
    }
}
