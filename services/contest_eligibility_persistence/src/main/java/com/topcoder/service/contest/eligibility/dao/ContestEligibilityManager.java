/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.service.contest.eligibility.dao;

import java.util.List;
import java.util.Set;
import java.util.HashSet;

import com.topcoder.service.contest.eligibility.ContestEligibility;

/**
 * <p>
 * ContestEligibilityManager is used to manage the persistence of eligibility entities.
 * </p>
 * <p>
 * <b>Thread Safety:</b> Implementations are required to be thread-safe.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public interface ContestEligibilityManager {

    /**
     * Return a list of eligibilities for a contest.
     *
     * @param contestId
     *            the contest id
     * @param isStudio
     *            the flag used to indicate whether it is studio
     * @return a list of eligibilities
     * @throws IllegalArgumentException
     *             if contestId is not positive
     * @throws ContestEligibilityPersistenceException
     *             if any errors occurred when retrieving the eligibilities
     */
    public List<ContestEligibility> getContestEligibility(long contestId, boolean isStudio)
        throws ContestEligibilityPersistenceException;

    /**
     * Add a contest eligibility.
     *
     * @param contestEligibility
     *            contest eligibility
     * @return the added contest eligibility
     * @throws IllegalArgumentException
     *             if contestEligibility is null
     * @throws ContestEligibilityPersistenceException
     *             if any errors occurred when persisting the given contest eligibility
     */
    public ContestEligibility create(ContestEligibility contestEligibility)
        throws ContestEligibilityPersistenceException;

    /**
     * Remove a contest eligibility.
     *
     * @param contestEligibility
     *            the contest eligibility to remove
     * @throws IllegalArgumentException
     *             if contestEligibility is null
     * @throws ContestEligibilityPersistenceException
     *             if any errors occurred when removing the given contest eligibility
     */
    public void remove(ContestEligibility contestEligibility) throws ContestEligibilityPersistenceException;

    /**
     * <p>
     * Save a list of eligibilities, if can be create/update/delete.
     * </p>
     * <ol>
     * You will get a list of eligibilities as input parameter, for each eligibility in the list, you do one
     * of these,
     * <li>create/insert, if id is 0 then insert.</li>
     * <li>update, if id is not 0, do an update.</li>
     * <li>delete, if 'delete' flag is true, you remove the eligibility.</li>
     * </ol>
     *
     * @param list
     *            a list of eligibilities
     * @return a list of eligibilities
     * @throws IllegalArgumentException
     *             if list is null or it contains null
     * @throws ContestEligibilityPersistenceException
     *             if any errors occurred when saving eligibilities
     */
    public List<ContestEligibility> save(List<ContestEligibility> list)
        throws ContestEligibilityPersistenceException;

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
    public Set<Long> haveEligibility(Long[] contestids, boolean isStudio)
         throws ContestEligibilityPersistenceException;
}