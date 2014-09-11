/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.review.specification.ejb;

import com.topcoder.service.review.specification.SpecificationReviewService;

import javax.ejb.Remote;


/**
 * <p>
 * This interface represents the remote interface for <code>{@link SpecificationReviewService}</code> session
 * bean. It extends that interface and provides no additional methods.
 * </p>
 * <p>
 * Thread Safety: Implementations of this interface must be thread safe.
 * </p>
 *
 * @author saarixx, myxgyy
 * @version 1.0
 */
@Remote
public interface SpecificationReviewServiceRemote extends SpecificationReviewService {
}
