/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.contest.eligibilityvalidation;

import com.topcoder.service.contest.eligibility.ContestEligibility;

/**
 * <p>
 * Mock a ContestEligibilityValidator used to test.
 * </p>
 * <p>
 * <b>Thread Safety:</b> it is thread safe.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockEligibilityValidator implements ContestEligibilityValidator {

    /**
     * Just throw the ContestEligibilityValidatorException.
     *
     * @param userId
     *            the user id
     * @param contestEligibility
     *            the contestEligibility to validate
     * @return false
     * @throws ContestEligibilityValidatorException
     *             always throw it
     */
    public boolean validate(long userId, ContestEligibility contestEligibility)
        throws ContestEligibilityValidatorException {
        throw new ContestEligibilityValidatorException(
            "ContestEligibilityValidatorException thrown from mock contest validator.");
    }
}