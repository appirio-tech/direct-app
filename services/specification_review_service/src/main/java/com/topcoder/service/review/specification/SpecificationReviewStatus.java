/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.review.specification;

/**
 * <p>
 * This is an enumeration for specification review statuses.
 * </p>
 * <p>
 * Thread Safety: This class is immutable and thread safe.
 * </p>
 *
 * @author saarixx, myxgyy
 * @version 1.0
 */
public enum SpecificationReviewStatus {
    /**
     * Represents the &quot;waiting for fixes&quot; specification review status.
     */
    WAITING_FOR_FIXES,
    
    /**
     * Represents the &quot;pending review&quot; specification review status.
     */
    PENDING_REVIEW,
    
    /**
     * Represents the &quot;no spec review&quot; specification review status.
     */
    NO_SPEC_REVIEW,
    
    /**
     * Represents the &quot;waiting for submit&quot; specification review status.
     */
    WAITING_FOR_SUBMIT,
    
    /**
     * Represents the &quot;finished&quot; specification review status.
     */
    FINISHED,
    
    /**
     * Represents the &quot;unkown&quot; specification review status.
     */
    UNKOWN;    
}
