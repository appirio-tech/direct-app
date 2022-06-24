/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.contest.eligibilityvalidation;

import javax.ejb.Remote;

/**
 * <p>
 * This is EJB <code>Remote</code> interface extending the <code>ContestEligibilityValidationManager</code>.
 * </p>
 * <p>
 * Used for referencing to <code>ContestEligibilityValidationManager</code> outside the same application.
 * </p>
 * <p>
 * <strong>Thread-safety:</strong>implementations of this interface must be thread-safe to be used inside EJB
 * container.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
@Remote
public interface ContestEligibilityValidationManagerRemote extends ContestEligibilityValidationManager {
}