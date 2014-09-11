/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.contest.eligibility.dao;

import javax.ejb.Local;

/**
 * <p>
 * This is EJB <code>Local</code> interface extending the <code>ContestEligibilityManager</code>.
 * </p>
 * <p>
 * Used for referencing to <code>ContestEligibilityManager</code> inside the same EJB application.
 * </p>
 * <p>
 * <strong>Thread-safety:</strong>implementations of this interface must be thread-safe to be used inside EJB
 * container.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
@Local
public interface ContestEligibilityManagerLocal extends ContestEligibilityManager {
}