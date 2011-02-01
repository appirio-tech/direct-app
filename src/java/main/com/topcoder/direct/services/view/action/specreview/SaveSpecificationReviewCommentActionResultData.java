/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.direct.services.view.action.specreview;

import com.topcoder.service.review.comment.specification.UserComment;
import com.topcoder.util.errorhandling.ExceptionUtils;

/**
 * <p>
 * This class is a simple DTO that holds the result data of <code>SaveSpecificationReviewCommentAction</code>.
 * It holds the saved user comment.
 * </p>
 *
 * <p>
 * <b>Thread safety:</b>  This class is mutable and not thread safe. This is not an issue because each action
 * instance will be used by one thread only, and will create/use only one instance of this class at a time.
 * Also, this is not an issue at the client side because it is single-threaded.
 * </p>
 *
 * @author caru, TCSDEVELOPER
 * @version 1.0
 */
public class SaveSpecificationReviewCommentActionResultData {

    /**
     * <p>
     * Represents the saved specification review user comment.
     * </p>
     *
     * <p>
     * Initially set to null, once set cannot be null. It set by setter and accessed by getter.
     * </p>
     */
    private UserComment userComment;

    /**
     * Default constructor, creates new instance.
     */
    public SaveSpecificationReviewCommentActionResultData() {
    }

    /**
     * Getter for user comment.
     *
     * @return the user comment
     */
    public UserComment getUserComment() {
        return userComment;
    }

    /**
     * Setter for user comment.
     *
     * @param userComment the user comment
     * @throws IllegalArgumentException if argument is null
     */
    public void setUserComment(UserComment userComment) {
        ExceptionUtils.checkNull(userComment, null, null, "userComment cannot be null.");
        this.userComment = userComment;
    }
}
