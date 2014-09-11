/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.dao.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.topcoder.clients.Utils;
import com.topcoder.clients.dao.ContestFeeConfigurationException;
import com.topcoder.clients.dao.ContestFeePercentageEntityNotFoundException;
import com.topcoder.clients.dao.ContestFeePercentagePersistenceException;
import com.topcoder.clients.dao.ProjectContestFeePercentageDAO;
import com.topcoder.clients.model.ProjectContestFeePercentage;
import com.topcoder.commons.utils.ParameterCheckUtility;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;

/**
 * This class implements ProjectContestFeePercentageDAO, it contains methods to
 * manage contest fees percentage for billing accounts.
 * Thread safety: The class is mutable and not thread safe. But it'll not caused thread safety issue if used under
 * Spring container.
 * 
 * @author minhu
 * @version 1.0 (Module Assembly - Contest Fee Based on % of Member Cost Admin Part)
 */
public class ProjectContestFeePercentageDAOImpl implements ProjectContestFeePercentageDAO {
    /**
     * Instance of Logger used to perform logging operations. It is managed with a getter and setter. It may have any
     * value. It is fully mutable.
     */
    private Log logger;

    /**
     * Represents the session factory instance. It is managed with a getter and setter. It may have any value. It is
     * fully mutable.
     */
    private SessionFactory sessionFactory;

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
            "Enter ProjectContestFeePercentageDAOImpl#create(ProjectContestFeePercentage) with "
                + Utils.toString(contestFeePercentage));

        Session session = null;
        try {
            ParameterCheckUtility.checkNotNull(contestFeePercentage, "contestFeePercentage");
            if (contestFeePercentage.getId() != 0) {
                throw new IllegalArgumentException("contestFeePercentage.id should be zero.");
            }

            session = getSession();
            session.save(contestFeePercentage);
            session.flush();
        } catch (IllegalArgumentException e) {
            logger.log(Level.ERROR, e);
            throw e;
        } catch (HibernateException e) {
            ContestFeePercentagePersistenceException ex =
                new ContestFeePercentagePersistenceException("Fail to create ProjectContestFeePercentage entity.",
                    e);
            logger.log(Level.ERROR, ex);
            throw ex;
        } finally {
            if (session != null) {
                session.close();
            }
        }

        logger.log(Level.DEBUG, "Exit ProjectContestFeePercentageDAOImpl#create(ProjectContestFeePercentage).");
    }

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
    public ProjectContestFeePercentage get(long id) throws ContestFeePercentagePersistenceException {
        logger.log(Level.DEBUG, "Enter ProjectContestFeePercentageServiceImpl#get(long) with id:" + id);
        ProjectContestFeePercentage contestFeePercentage;

        String hql = "from ProjectContestFeePercentage p where p.id = :id";
        Session session = null;
        try {
            ParameterCheckUtility.checkPositive(id, "id");

            // create query to fetch the entity
            session = getSession();
            List res = session.createQuery(hql).setLong("id", id).list();
            contestFeePercentage = (res.isEmpty() ? null : (ProjectContestFeePercentage) res.get(0));
        } catch (IllegalArgumentException e) {
            logger.log(Level.ERROR, e);
            throw e;
        } catch (HibernateException e) {
            ContestFeePercentagePersistenceException ex =
                new ContestFeePercentagePersistenceException(
                    "Fail to get ProjectContestFeePercentage entity by projectId.", e);
            logger.log(Level.ERROR, ex);
            throw ex;
        } finally {
            if (session != null) {
                session.close();
            }
        }
        logger.log(Level.DEBUG,
            "Exit ProjectContestFeePercentageDAOImpl#get(long) with " + Utils.toString(contestFeePercentage));
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
        logger.log(Level.DEBUG, "Enter ProjectContestFeePercentageDAOImpl#getByProjectId(long) with projectId:"
            + projectId);
        ProjectContestFeePercentage contestFeePercentage;

        String hql = "from ProjectContestFeePercentage p where p.projectId = :projectId";
        Session session = null;
        try {
            ParameterCheckUtility.checkPositive(projectId, "projectId");

            // create query to fetch the entity
            session = getSession();
            List res = session.createQuery(hql).setLong("projectId", projectId).list();
            contestFeePercentage = (res.isEmpty() ? null : (ProjectContestFeePercentage) res.get(0));
        } catch (IllegalArgumentException e) {
            logger.log(Level.ERROR, e);
            throw e;
        } catch (HibernateException e) {
            ContestFeePercentagePersistenceException ex =
                new ContestFeePercentagePersistenceException(
                    "Fail to get ProjectContestFeePercentage entity by projectId.", e);
            logger.log(Level.ERROR, ex);
            throw ex;
        } finally {
            if (session != null) {
                session.close();
            }
        }
        logger.log(
            Level.DEBUG,
            "Exit ProjectContestFeePercentageDAOImpl#getByProjectId(long) with "
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
            "Enter ProjectContestFeePercentageDAOImpl#update(ProjectContestFeePercentage) with "
                + Utils.toString(contestFeePercentage));

        Session session = null;
        try {
            ParameterCheckUtility.checkNotNull(contestFeePercentage, "contestFeePercentage");
            ParameterCheckUtility.checkPositive(contestFeePercentage.getId(), "contestFeePercentage.id");

            // check if entity exists
            getAndCheckExist(contestFeePercentage.getId());

            session = getSession();
            session.update(contestFeePercentage);
            session.flush();
        } catch (IllegalArgumentException e) {
            logger.log(Level.ERROR, e);
            throw e;
        } catch (ContestFeePercentageEntityNotFoundException e) {
            logger.log(Level.ERROR, e);
            throw e;
        } catch (HibernateException e) {
            ContestFeePercentagePersistenceException ex =
                new ContestFeePercentagePersistenceException("Fail to update ProjectContestFeePercentage entity.",
                    e);
            logger.log(Level.ERROR, ex);
            throw ex;
        } finally {
            if (session != null) {
                session.close();
            }
        }

        logger.log(Level.DEBUG, "Exit ProjectContestFeePercentageDAOImpl#update(ProjectContestFeePercentage).");
    }

    /**
     * This method is responsible for deleting contest fee percentage given its id.
     * 
     * @param id - denotes contest fee percentage id
     * @throws IllegalArgumentException
     *         if id is not positive
     * @throws ContestFeePercentageEntityNotFoundException
     *         if the percentage to delete is not found in DB
     * @throws ContestFeePercentagePersistenceException
     *         if there is any other exception
     */
    public void delete(long id) throws ContestFeePercentageEntityNotFoundException,
        ContestFeePercentagePersistenceException {
        logger.log(Level.DEBUG, "Enter ProjectContestFeePercentageDAOImpl#delete(long) with id:" + id);

        Session session = null;
        try {
            ParameterCheckUtility.checkPositive(id, "id");
            ProjectContestFeePercentage contestFeePercentage = getAndCheckExist(id);

            session = getSession();
            session.delete(contestFeePercentage);
            session.flush();
        } catch (IllegalArgumentException e) {
            logger.log(Level.ERROR, e);
            throw e;
        } catch (ContestFeePercentageEntityNotFoundException e) {
            logger.log(Level.ERROR, e);
            throw e;
        } catch (HibernateException e) {
            ContestFeePercentagePersistenceException ex =
                new ContestFeePercentagePersistenceException("Fail to delete ProjectContestFeePercentage entity.",
                    e);
            logger.log(Level.ERROR, ex);
            throw ex;
        } finally {
            if (session != null) {
                session.close();
            }
        }

        logger.log(Level.DEBUG, "Exit ProjectContestFeePercentageDAOImpl#delete(id).");
    }

    /**
     * gets the logger field
     * 
     * @return the logger field value.
     */
    public Log getLogger() {
        return logger;
    }

    /**
     * Sets the corresponding member field
     * 
     * @param loggerName
     *        - the given logger name to set.
     */
    public void setLoggerName(String loggerName) {
        if (loggerName == null) {
            this.logger = null;
        } else {
            this.logger = LogManager.getLog(loggerName);
        }
    }

    /**
     * Check the parameters.
     * 
     * @throw ContestFeeConfigurationException if this.logger or this.session is null.
     */
    public void postConstruct() {
        if (logger == null || sessionFactory == null) {
            throw new ContestFeeConfigurationException("This class requires logger instance and session instance.");
        }
    }

    /**
     * Returns the Session field value.
     * 
     * @return created Session field value.
     */
    private Session getSession() {
        return sessionFactory.openSession();
    }

    /**
     * Returns the sessionFactory field value.
     * 
     * @return sessionFactory field value.
     */
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    /**
     * Sets the given value to sessionFactory field.
     * 
     * @param sessionFactory
     *        - the given value to set.
     */
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Gets the ProjectContestFeePercentage entity and checks if it's not null.
     * 
     * @throws ContestFeePercentageEntityNotFoundException
     *         if the percentage is not found in DB
     * @throws ContestFeePercentagePersistenceException
     *         if any error occurred
     * @return the entity got
     */
    private ProjectContestFeePercentage getAndCheckExist(long id)
        throws ContestFeePercentageEntityNotFoundException, ContestFeePercentagePersistenceException {
        ProjectContestFeePercentage contestFeePercentage = get(id);
        if (contestFeePercentage == null) {
            throw new ContestFeePercentageEntityNotFoundException(
                "The ProjectContestFeePercentage is not found with the given id: " + id);
        }
        return contestFeePercentage;
    }
}
