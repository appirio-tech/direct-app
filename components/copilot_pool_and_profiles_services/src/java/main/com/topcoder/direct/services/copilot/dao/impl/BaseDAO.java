/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.copilot.dao.impl;

import com.topcoder.direct.services.copilot.dao.CopilotDAOException;
import com.topcoder.direct.services.copilot.dao.CopilotDAOInitializationException;
import com.topcoder.direct.services.copilot.model.IdentifiableEntity;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.annotation.PostConstruct;
import java.text.MessageFormat;
import java.util.List;

import org.springframework.transaction.annotation.Transactional; 

/**
 * <p>This class is base for all DAO implementations provided in this component. It holds a Logging Wrapper logger and
 * Hibernate session factory for TCS Catalog database to be used by subclasses. Also it provides a protected method for
 * retrieving entities of specific type from persistence. It's assumed that subclasses of this class will be initialized
 * using Spring IoC.</p>
 *
 * <p><strong>Thread safety:</strong> This class has mutable attributes, thus it's not thread safe. But it's assumed
 * that its subclasses will be initialized via Spring IoC before calling any business method, this way it's always used
 * in thread safe manner. This class uses thread safe SessionFactory and Session instances.</p>
 *
 * @author saarixx, TCSDEVELOPER
 * @version 1.0
 */
@Transactional
public abstract class BaseDAO {

    /**
     * <p>Represents a query used for retrieving entities from database.</p>
     */
    private static final String ENTITY_QUERY = "from {0}";

    /**
     * <p>The name of the logger to be used for logging errors and debug information. Cannot be empty after
     * initialization (validation is performed in checkInit() method). If null, the logging is not performed.
     * Initialized by Spring setter injection. Has a setter. Is used in checkInit().</p>
     */
    private String loggerName;

    /**
     * <p>The logger to be used by subclasses for logging errors and debug information. Is initialized in checkInit().
     * If null, logging is not performed. Has a protected getter to be used by subclasses.</p>
     */
    private Log log;

    /**
     * <p>The Hibernate session factory to be used when retrieving session for accessing entities stored in TCS Catalog
     * database. Cannot be null after initialization (validation is performed in checkInit() method). Initialized by
     * Spring setter injection. Has protected getter and public setter. Is used in checkInit() and getSession().</p>
     */
    private SessionFactory sessionFactory;

    /**
     * <p>Creates new instance of <code>{@link BaseDAO}</code> class.</p>
     */
    protected BaseDAO() {
        // empty constructor
    }

    /**
     * <p>Sets the name of the logger to be used by this class.</p>
     *
     * @param loggerName the name of the logger to be used by this class
     */
    public void setLoggerName(String loggerName) {
        this.loggerName = loggerName;
    }

    /**
     * <p>Retrieves the logger to be used by subclasses for logging errors and debug information.</p>
     *
     * @return the logger to be used by subclasses for logging errors and debug information
     */
    protected Log getLog() {
        return log;
    }

    /**
     * <p>Sets the Hibernate session factory to be used when retrieving session for accessing entities stored in TCS
     * Catalog database.</p>
     *
     * @param sessionFactory the Hibernate session factory to be used when retrieving session for accessing entities
     *                       stored in TCS Catalog database
     */
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * <p>Retrieves the Hibernate session factory to be used when retrieving session for accessing entities stored in
     * TCS Catalog database.</p>
     *
     * @return the Hibernate session factory to be used when retrieving session for accessing entities stored in TCS
     *         Catalog database
     */
    protected SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    /**
     * <p>Checks whether this class was initialized by Spring properly. Initializes any additional properties if
     * required.</p>
     *
     * @throws CopilotDAOInitializationException
     *          if the class was not initialized properly, if the logger name is empty string or when sessionFactory was
     *          not set
     */
    @PostConstruct
    protected void checkInit() {

        // check if logger name is not null
        if (loggerName != null) {
            // if logger name is set then check it is not empty
            if (loggerName.trim().length() == 0) {
                throw new CopilotDAOInitializationException("The 'loggerName' property must be not empty string");
            }

            // create new logger with a given name
            log = LogManager.getLog(loggerName);
        }

        // check if session factory was set properly
        Helper.checkPropertyNotNull(sessionFactory, "sessionFactory");
    }

    /**
     * <p>Retrieves the current session from the session factory.</p>
     *
     * @return the retrieved current session (not null)
     *
     * @throws CopilotDAOException if any error occurred
     */
    protected Session getSession() throws CopilotDAOException {
        try {
            // return current session from session factory
            return sessionFactory.getCurrentSession();
        } catch (HibernateException e) {
            throw new CopilotDAOException("Could not retrieve hibernate session.", e);
        }
    }

    /**
     * <p>Retrieves all entities of the specified type from persistence.</p>
     *
     * @param entityType the type of the entities to be retrieved
     * @param <T>        type of entity to retrieve
     *
     * @return the list with retrieved entities (not null, doesn't contain null)
     *
     * @throws IllegalArgumentException if entityType is null
     * @throws CopilotDAOException      if any error occurred
     */
    @SuppressWarnings("unchecked")
    protected <T extends IdentifiableEntity> List<T> getAllEntities(Class<T> entityType) throws CopilotDAOException {
        Helper.checkIsNotNull(entityType, "entityType");

        try {
            // execute query and return result as list
            return getSession()
                    .createQuery(MessageFormat.format(ENTITY_QUERY, entityType.getSimpleName()))
                    .list();
        } catch (HibernateException e) {
            throw new CopilotDAOException(MessageFormat.format("Error occurred when retrieving entities of {0}",
                    entityType.getSimpleName()), e);
        }
    }
}
