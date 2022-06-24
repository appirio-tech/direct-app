/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.contest.eligibilityvalidation;

import com.topcoder.service.contest.eligibility.ContestEligibility;

/**
 * <p>
 * It is used to validate a contest eligibility.Its implementations might be not used by client user directly.
 * </p>
 * <p>
 * <b>Thread Safety:</b> Implementations are required to be thread-safe.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public interface ContestEligibilityValidator {

    /**
     * Validate the given contestEligibility.
     *
     * @param userId
     *            the user id
     * @param contestEligibility
     *            the contestEligibility to validate
     * @return true if the user is eligible to join,otherwise false
     * @throws IllegalArgumentException
     *             if contestEligibility is null
     * @throws ContestEligibilityValidatorException
     *             if any errors occurred when querying in the persistence
     */
    public boolean validate(long userId, ContestEligibility contestEligibility)
        throws ContestEligibilityValidatorException;
}