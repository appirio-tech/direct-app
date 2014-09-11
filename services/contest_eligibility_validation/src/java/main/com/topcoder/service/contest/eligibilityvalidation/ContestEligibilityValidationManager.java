/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.contest.eligibilityvalidation;

import java.util.List;

import com.topcoder.service.contest.eligibility.ContestEligibility;

/**
 * <p>
 * ContestEligibilityValidationManager is used to validate the user against a list of ContestEligibility
 * entities.
 * </p>
 * <p>
 * <b>Thread Safety:</b> Implementations are required to be thread-safe.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public interface ContestEligibilityValidationManager {

    /**
     * <p>
     * Validate the user against a list of ContestEligibility entities.
     * </p>
     * <p>
     * It will for each ContestEligibility, invoke the corresponding ContestEligibilityValidator to check, for
     * now if any one of the eligibility check is true (OR), then it will return true (eligible). If the given
     * list is empty,then return true because empty list means there is no eligibility assign.
     * </p>
     *
     * @param userId
     *            the id of user
     * @param eligibilities
     *            a list of ContestEligibility entities
     * @return true if any one of the eligibility check is true or the given list is empty,otherwise false
     * @throws IllegalArgumentException
     *             if eligibilities is null or eligibilities contains null item
     * @throws ContestEligibilityValidationManagerException
     *             if any errors occurred when validating
     */
    public boolean validate(long userId, List<ContestEligibility> eligibilities)
        throws ContestEligibilityValidationManagerException;
}