/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.review.stresstests;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import com.topcoder.management.review.ReviewPersistence;
import com.topcoder.management.review.ReviewPersistenceException;
import com.topcoder.management.review.data.Comment;
import com.topcoder.management.review.data.CommentType;
import com.topcoder.management.review.data.Review;
import com.topcoder.search.builder.filter.Filter;




/**
 * <p>
 * Mock StressMockReviewPersistence class implements ReviewPersistence.
 * </p>
 *
 * @author still
 * @version 1.0
 */
final public class StressMockReviewPersistence extends Assert implements ReviewPersistence {
	/**
	 * This private map keeps the pair: method name and the number of the method call count. 
	 */	
	private Map methodCountMap = new HashMap();


	/**
	 * Mock createReview method.
	 * @see com.topcoder.management.review.ReviewPersistence#createReview(com.topcoder.management.review.data.Review, java.lang.String)
	 */
	public void createReview(Review review, String operator) throws ReviewPersistenceException {
		addMethodCount("createReview");
	}

	/**
	 * Mock updateReview method.
	 * @see com.topcoder.management.review.ReviewPersistence#updateReview(com.topcoder.management.review.data.Review, java.lang.String)
	 */
	public void updateReview(Review review, String operator) throws ReviewPersistenceException {
		addMethodCount("updateReview");
	}

	/**
	 * Mock getReview method.
	 * @see com.topcoder.management.review.ReviewPersistence#getReview(long)
	 */
	public Review getReview(long id) throws ReviewPersistenceException {
		addMethodCount("getReview");
		return null;
	}

	/**
	 * Mock searchReviews method.
	 * @see com.topcoder.management.review.ReviewPersistence#searchReviews(com.topcoder.search.builder.filter.Filter, boolean)
	 */
	public Review[] searchReviews(Filter filter, boolean complete) throws ReviewPersistenceException {
		addMethodCount("searchReviews");
		return null;
	}

	/**
	 * Mock addReviewComment method.
	 * @see com.topcoder.management.review.ReviewPersistence#addReviewComment(long, com.topcoder.management.review.data.Comment, java.lang.String)
	 */
	public void addReviewComment(long reviewId, Comment comment, String operator) throws ReviewPersistenceException {
		addMethodCount("addReviewComment");		
	}

	/**
	 * Mock addItemComment method.
	 * @see com.topcoder.management.review.ReviewPersistence#addItemComment(long, com.topcoder.management.review.data.Comment, java.lang.String)
	 */
	public void addItemComment(long itemId, Comment comment, String operator) throws ReviewPersistenceException {
		addMethodCount("addItemComment");
	}

	/**
	 * Mock getAllCommentTypes method.
	 * @see com.topcoder.management.review.ReviewPersistence#getAllCommentTypes()
	 */
	public CommentType[] getAllCommentTypes() throws ReviewPersistenceException {
		addMethodCount("getAllCommentTypes");
		return null;
	}

	/**
	 * This method add the Integer corresponding to methodName by 1.
	 * @param methodName the method name
	 */
    private void addMethodCount(String methodName) {
    	Object count = methodCountMap.get(methodName);
    	if (count != null) {
    		methodCountMap.put(methodName, new Integer(((Integer) count).intValue() + 1));
    	} else {
    		methodCountMap.put(methodName, new Integer(1));
    	}    	
    }
    /**
     * This method asserts that the method with 'methodName' be called exactly 'calledTimes' times.
     * @param methodName
     * @param calledTimes
     */
    public void assertMethodCalled(String methodName, int calledTimes) {
    	Object count = methodCountMap.get(methodName);
    	assertNotNull("Should call method '" + methodName + "'.", count);
    	assertEquals("Should call method '" + methodName + "' only once.", count, new Integer(calledTimes));
    	assertTrue("Should not call method other than '" + methodName + "'.", methodCountMap.size() == 1);
    }
}
