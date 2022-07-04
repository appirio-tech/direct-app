/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.dao;

import com.topcoder.clients.model.ProjectContestFeePercentage;

/**
 * Defines contract methods to manage contest fees percentage for billing accounts.
 * It defines CRUD operations on contest fee percentage for a billing.
 * Thread safety: The implementation should be reasonable thread safe.
 * 
 * @author minhu
 * @version 1.0 (Module Assembly - Contest Fee Based on % of Member Cost Admin Part)
 */
public interface ProjectContestFeePercentageDAO {
    /**
     * This method is responsible for creating contest fee percentage.
     * 
     * @param contestFeePercentage - a ProjectContestFeePercentage instance to save
     * @throws IllegalArgumentException
     *         if contestFeePercentage is null or its id is not zero
     * @throws ContestFeePercentagePersistenceException
     *         if there is any exception
     */
    void create(ProjectContestFeePercentage contestFeePercentage) throws ContestFeePercentagePersistenceException;

    /**
     * This method is responsible for retrieving contest fee percentage given id.
     * 
     * @param id - denotes the entity id, should be positive
     * @return instance of ProjectContestFeePercentage or null if not found
     * @throws IllegalArgumentException
     *         if id is not positive
     * @throws ContestFeePercentagePersistenceException
     *         if there is any exception
     */
    ProjectContestFeePercentage get(long id) throws ContestFeePercentagePersistenceException;

    /**
     * This method is responsible for retrieving contest fee percentage given billing account project id.
     * 
     * @param projectId - denotes billing account project id, should be positive
     * @return instance of ProjectContestFeePercentage or null if not found
     * @throws IllegalArgumentException
     *         if projectId is not positive
     * @throws ContestFeePercentagePersistenceException
     *         if there is any exception
     */
    ProjectContestFeePercentage getByProjectId(long projectId) throws ContestFeePercentagePersistenceException;

    /**
     * This method is responsible for updating contest fee percentage given
     * instance of ProjectContestFeePercentage.
     * 
     * @param contestFeePercentage - a ProjectContestFeePercentage instance to update
     * @throws IllegalArgumentException
     *         if contestFeePercentage is null or its id is not positive
     * @throws ContestFeePercentageEntityNotFoundException
     *         if the percentage to update is not found in DB
     * @throws ContestFeePercentagePersistenceException
     *         if there is any other exception
     */
    void update(ProjectContestFeePercentage contestFeePercentage)
        throws ContestFeePercentageEntityNotFoundException, ContestFeePercentagePersistenceException;

    /**
     * This method is responsible for deleting contest fee percentage given its id.
     * 
     * @param id - denotes contest fee percentage id
     * @throws IllegalArgumentException
     *         if id is not positive
     * @throws ContestFeePercentageEntityNotFoundException
     *         if the percentage to update is not found in DB
     * @throws ContestFeePercentagePersistenceException
     *         if there is any other exception
     */
    void delete(long id) throws ContestFeePercentageEntityNotFoundException,
        ContestFeePercentagePersistenceException;
}
