/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.dao.impl;

import com.topcoder.clients.Utils;
import com.topcoder.clients.dao.ContestFeeConfigurationException;
import com.topcoder.clients.dao.ContestFeePercentageEntityNotFoundException;
import com.topcoder.clients.dao.ContestFeePercentagePersistenceException;
import com.topcoder.clients.dao.ProjectContestFeePercentageDAO;
import com.topcoder.clients.dao.ProjectContestFeePercentageService;
import com.topcoder.clients.model.ProjectContestFeePercentage;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;

/**
 * This class implements ProjectContestFeePercentageService, it contains methods to
 * manage contest fees percentage for billing accounts.
 * Thread safety: The class is mutable and not thread safe. But it'll not caused thread safety issue if used under
 * Spring container.
 * 
 * @author minhu
 * @version 1.0 (Module Assembly - Contest Fee Based on % of Member Cost Admin Part)
 */
public class ProjectContestFeePercentageServiceImpl implements ProjectContestFeePercentageService {
    /**
     * Instance of Logger used to perform logging operations. It is managed with a getter and setter. It may have any
     * value. It is fully mutable.
     */
    private Log logger;

    /**
     * Instance of DataAccess used to perform db operations. It is managed with a getter and setter. It may have any
     * value. It is fully mutable.
     */
    private ProjectContestFeePercentageDAO persistence;

    /**
     * This method is responsible for creating contest fee percentage.
     * 
     * @param contestFeePercentage - a ProjectContestFeePercentage instance to save
     * @throws IllegalArgumentException
     *         if contestFeePercentage is null or its id is not zero
     * @throws ContestFeePercentagePersistenceException
     *         if there is any exception
     */
    public void create(ProjectContestFeePercentage contestFeePercentage)
        throws ContestFeePercentagePersistenceException {
        logger.log(
            Level.DEBUG,
            "Enter ProjectContestFeePercentageServiceImpl#create(ProjectContestFeePercentage) with "
                + Utils.toString(contestFeePercentage));
        try {
            persistence.create(contestFeePercentage);
        } catch (IllegalArgumentException e) {
            logger.log(Level.ERROR, e);
            throw e;
        } catch (ContestFeePercentagePersistenceException e) {
            logger.log(Level.ERROR, e);
            throw e;
        }
        logger
            .log(Level.DEBUG, "Exit ProjectContestFeePercentageServiceImpl#create(ProjectContestFeePercentage).");
    }

    /**
     * This method is responsible for retrieving contest fee percentage given entity id.
     * 
     * @param id - denotes the entity id, should be positive
     * @return instance of ProjectContestFeePercentage or null if not found
     * @throws IllegalArgumentException
     *         if id is not positive
     * @throws ContestFeePercentagePersistenceException
     *         if there is any exception
     */
    public ProjectContestFeePercentage get(long id) throws ContestFeePercentagePersistenceException {
        logger.log(Level.DEBUG,
            "Enter ProjectContestFeePercentageServiceImpl#get(long) with id:" + id);
        ProjectContestFeePercentage contestFeePercentage;
        try {
            contestFeePercentage = persistence.get(id);
        } catch (IllegalArgumentException e) {
            logger.log(Level.ERROR, e);
            throw e;
        } catch (ContestFeePercentagePersistenceException e) {
            logger.log(Level.ERROR, e);
            throw e;
        }
        logger.log(
            Level.DEBUG,
            "Exit ProjectContestFeePercentageServiceImpl#getByProjectId(long) with "
                + Utils.toString(contestFeePercentage));
        return contestFeePercentage;
    }

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
    public ProjectContestFeePercentage getByProjectId(long projectId)
        throws ContestFeePercentagePersistenceException {
        logger.log(Level.DEBUG,
            "Enter ProjectContestFeePercentageServiceImpl#getByProjectId(long) with projectId:" + projectId);
        ProjectContestFeePercentage contestFeePercentage;
        try {
            contestFeePercentage = persistence.getByProjectId(projectId);
        } catch (IllegalArgumentException e) {
            logger.log(Level.ERROR, e);
            throw e;
        } catch (ContestFeePercentagePersistenceException e) {
            logger.log(Level.ERROR, e);
            throw e;
        }
        logger.log(
            Level.DEBUG,
            "Exit ProjectContestFeePercentageServiceImpl#getByProjectId(long) with "
                + Utils.toString(contestFeePercentage));
        return contestFeePercentage;
    }

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
    public void update(ProjectContestFeePercentage contestFeePercentage)
        throws ContestFeePercentageEntityNotFoundException, ContestFeePercentagePersistenceException {
        logger.log(
            Level.DEBUG,
            "Enter ProjectContestFeePercentageServiceImpl#update(ProjectContestFeePercentage) with "
                + Utils.toString(contestFeePercentage));
        try {
            persistence.update(contestFeePercentage);
        } catch (IllegalArgumentException e) {
            logger.log(Level.ERROR, e);
            throw e;
        } catch (ContestFeePercentageEntityNotFoundException e) {
            logger.log(Level.ERROR, e);
            throw e;
        } catch (ContestFeePercentagePersistenceException e) {
            logger.log(Level.ERROR, e);
            throw e;
        }
        logger
            .log(Level.DEBUG, "Exit ProjectContestFeePercentageServiceImpl#update(ProjectContestFeePercentage).");
    }

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
    public void delete(long id) throws ContestFeePercentageEntityNotFoundException,
        ContestFeePercentagePersistenceException {
        logger.log(Level.DEBUG, "Enter ProjectContestFeePercentageServiceImpl#delete(long) with id:" + id);
        try {
            persistence.delete(id);
        } catch (IllegalArgumentException e) {
            logger.log(Level.ERROR, e);
            throw e;
        } catch (ContestFeePercentageEntityNotFoundException e) {
            logger.log(Level.ERROR, e);
            throw e;
        } catch (ContestFeePercentagePersistenceException e) {
            logger.log(Level.ERROR, e);
            throw e;
        }
        logger.log(Level.DEBUG, "Exit ProjectContestFeePercentageServiceImpl#delete(id).");
    }

    /**
     * Returns the logger field value.
     * 
     * @return logger field value.
     */
    public Log getLogger() {
        return logger;
    }

    /**
     * Sets the corresponding member field
     * 
     * @param loggerName
     *        - the given name to set.
     */
    public void setLoggerName(String loggerName) {
        if (loggerName == null) {
            this.logger = null;
        } else {
            this.logger = LogManager.getLog(loggerName);
        }
    }

    /**
     * Returns the persistence field value.
     * 
     * @return persistence field value.
     */
    public ProjectContestFeePercentageDAO getPersistence() {
        return persistence;
    }

    /**
     * Sets the given value to persistence field.
     * 
     * @param persistence
     *        - the given value to set.
     */
    public void setPersistence(ProjectContestFeePercentageDAO persistence) {
        this.persistence = persistence;
    }

    /**
     * Check parameters.
     * 
     * @throw ContestFeeConfigurationException if this.logger or this.persistence is null
     */
    public void postConstruct() {
        if (logger == null || persistence == null) {
            throw new ContestFeeConfigurationException(
                "The class requires logger instance and persistence instance.");
        }
    }
}
