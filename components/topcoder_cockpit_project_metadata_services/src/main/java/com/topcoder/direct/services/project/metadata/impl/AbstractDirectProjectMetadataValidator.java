/*
 * Copyright (C) 2011 - 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.metadata.impl;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.topcoder.commons.utils.ValidationUtility;
import com.topcoder.direct.services.project.metadata.ConfigurationException;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;

/**
 * <p>
 * This is the base class for direct project metadata validators. It provides common entityManager for application
 * database and logger. The configuration of the class is done by Spring IoC framework.
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> The only non-thread safe part of class is configuration, but it is done in thread
 * safe manner by Spring IoC framework. The class is thread safe under these conditions.
 * </p>
 *
 * <p>
 * Version 1.1 (Release Assembly - TopCoder Cockpit Asset View And Basic Upload)
 * - Correct the persistent unit name to projectMetadataPersistenceUnit
 * </p>
 *
 * @author faeton, sparemax
 * @version 1.0
 */
public abstract class AbstractDirectProjectMetadataValidator {
    /**
     * <p>
     * The entity manager used to manage entities.
     * </p>
     *
     * <p>
     * The default value is null. It can not be null after configuration. It is accessed by getter and modified by
     * setter. Its' legality is checked in checkInitialization method.
     * </p>
     */
    @PersistenceContext(unitName = "projectMetadataPersistenceUnit")
    private EntityManager entityManager;

    /**
     * <p>
     * The logger used to perform logging.
     * </p>
     *
     * <p>
     * The default value is null. It is null if logging is not required. It is accessed by getter and modified by
     * setter.
     * </p>
     */
    private Log logger;

    /**
     * Creates an instance of AbstractDirectProjectMetadataValidator.
     */
    protected AbstractDirectProjectMetadataValidator() {
        // Empty
    }

    /**
     * Gets the entity manager used to manage entities.
     *
     * @return the entity manager used to manage entities.
     */
    protected EntityManager getEntityManager() {
        return entityManager;
    }

    /**
     * Sets the entity manager used to manage entities.
     *
     * <p>
     * It is called by Spring injection framework. It is annotated with &#64;PersistenceContext to specify the entity
     * manager.
     * </p>
     *
     * @param entityManager
     *            the entity manager used to manage entities.
     */
    @PersistenceContext(unitName = "projectMetadataPersistenceUnit")
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Gets the logger used to perform logging.
     *
     * @return the logger used to perform logging.
     */
    protected Log getLogger() {
        return logger;
    }

    /**
     * Sets the logger used to perform logging.
     *
     * <p>
     * It is called by Spring injection framework.
     * </p>
     *
     * @param loggerName
     *            the logger name to set.
     */
    public void setLoggerName(String loggerName) {
        this.logger = (loggerName == null) ? null : LogManager.getLog(loggerName);
    }

    /**
     * This method is used to check, whether the dependencies were properly initialized for the class.
     *
     * @throws ConfigurationException
     *             if entityManager is <code>null</code>.
     */
    @PostConstruct
    protected void checkInitialization() {
        ValidationUtility.checkNotNull(entityManager, "entityManager", ConfigurationException.class);
    }
}
