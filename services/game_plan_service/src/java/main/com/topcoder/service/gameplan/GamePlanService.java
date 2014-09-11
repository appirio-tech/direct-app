/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.service.gameplan;

import com.topcoder.security.TCSubject;
import com.topcoder.service.util.gameplan.TCDirectProjectGamePlanData;

import java.util.List;

/**
 * <p>This interface represents a game plan service. It provides
 * <ul>
 * <li>a method for retrieving game plan specific data for all TC Direct projects associated with the current user.</li>
 * <li>a method for retrieving game plan data for specific TC Direct project associated with the current user.</li>
 * </ul>
 * </p>
 *
 * <p><b>Thread Safety</b>: Implementations of this interface must be thread safe when entities passed to them are used
 * in thread safe manner by the caller.</p>
 *
 * @author saarixx, FireIce
 * @version 1.0
 */
public interface GamePlanService {
    /**
     * Retrieves the game plan data of TC Direct projects for the current user. If the current user is an admin, data
     * for all existing TC Direct projects are retrieved.
     *
     * @param tcSubject the TC subject
     * @return the retrieved TC Direct projects game plan data (not null, doesn't contain null; empty if there is no TC
     *         Direct projects for the current user)
     * @throws IllegalArgumentException     if tcSubject is null
     * @throws GamePlanPersistenceException if some error occurred when accessing the persistence
     * @throws GamePlanServiceException     if some error occurred when retrieving the game plan data
     */
    public List<TCDirectProjectGamePlanData> retrieveGamePlanData(TCSubject tcSubject) throws GamePlanServiceException;

    /**
     * Retrieves the game plan data of specified TC Direct project for the current user. If the current user is an
     * admin, any TC Direct project can be retrieved.
     *
     * @param tcSubject       the TC subject
     * @param directProjectId the TC Direct Project id
     * @return the retrieved TC Direct project game plan data (possibly null if there is no TC Direct projects for the
     *         current user)
     * @throws IllegalArgumentException     if tcSubject is null
     * @throws GamePlanPersistenceException if some error occurred when accessing the persistence
     * @throws GamePlanServiceException     if some error occurred when retrieving the game plan data
     */
    public TCDirectProjectGamePlanData retrieveGamePlanData(TCSubject tcSubject, long directProjectId)
            throws GamePlanServiceException;
}

