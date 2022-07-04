/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.contest.eligibilityvalidation;


import javax.ejb.Local;


/**
 * <p>
 * An interface providing local access to {@link ContestServiceFacade}
 * implementation available within the same application instance or
 * <code>EJB</code> container.
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
@Local
public interface ContestEligibilityServiceLocal extends ContestEligibilityService {
}
