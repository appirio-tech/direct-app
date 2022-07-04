package com.topcoder.service.review.comment.specification.ejb;

import com.topcoder.service.review.comment.specification.SpecReviewCommentService;

import javax.ejb.Remote;

/**
 * <p>
 * An interface providing remote access to {@link SpecReviewCommentService}
 * implementation available within the remote application instance or outside of
 * the <code>EJB</code> container.
 * </p>
 *
 * <p>
 * <b>Thread safety:</b> The implementations of this interface must operate in a
 * thread-safe manner to be used inside the <code>EJB</code> container.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
@Remote
public interface SpecReviewCommentServiceRemote extends SpecReviewCommentService {
}