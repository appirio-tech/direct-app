/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.contest.eligibilityvalidation;

import java.util.Set;

/**
 * <p>
 * An interface for the contest eligibility
 * </p>
 *
 */
public interface ContestEligibilityService {
    
    /**
     * Returns whether a user is eligible for a particular contest.
     *
     * @param userId
     *            The user id
     * @param contestId
     *            The contest id
     * @param isStudio
     *            true if the contest is a studio contest, false otherwise.
     * @return true if the user is eligible for the specified contest, false otherwise.
     * 
     * @throws ContestServiceException
     *             if any other error occurs
     * @since 1.2
     */
    public boolean isEligible(long userId, long contestId, boolean isStudio) throws ContestEligibilityValidatorException;

    /**
     * Returns whether a contest has any eligibility
     *
     * @param contestId
     *            The contest id
     * @param isStudio
     *            true if the contest is a studio contest, false otherwise.
     * @return true if the user is eligible for the specified contest, false otherwise.
     * 
     * @throws ContestServiceException
     *             if any other error occurs
     * @since 1.2
     */
    public boolean hasEligibility(long contestId, boolean isStudio) throws ContestEligibilityValidatorException;

    /**
     * Return a list of contest ids that has eligibility.
     *
     * @param contestIds
     *            the contest id list
     * @param isStudio
     *            the flag used to indicate whether it is studio
     * @return a list of contst ids
     * @throws IllegalArgumentException
     *             if contestId is not positive
     */
    public Set<Long> haveEligibility(Long[] contestids, boolean isStudio) throws ContestEligibilityValidatorException;

}
