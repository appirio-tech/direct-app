/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.asset.services.impl;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.topcoder.asset.exceptions.AssetConfigurationException;
import com.topcoder.commons.utils.ValidationUtility;
import com.topcoder.util.log.Log;

/**
 * <p>
 * This class is a base for all JPA-based service implementations provided in this component. It simply holds injected
 * JPA EntityManager to be used by subclasses. It also holds a Logging Wrapper logger to perform logging. It's assumed
 * that subclasses will be initialized with Spring setter dependency injection only.
 * </p>
 *
 * <p>
 * <strong>Thread safety:</strong> This class is mutable and not thread safe since it provides public setters for its
 * properties.
 * </p>
 *
 * <p>
 * Version 1.1 (Release Assembly - TopCoder Cockpit Asset View And Basic Upload)
 * - Sets the persistence unit name to assetPersistenceUnit
 * </p>
 *
 * @author LOY, sparemax, GreatKevin
 * @version 1.1
 */
public abstract class BaseAssetService {
    /**
     * The EntityManager instance to be used by subclasses for accessing JPA entities in persistence. Is initialized
     * with Spring setter dependency injection. Cannot be null after initialization, assuming that property is
     * initialized via Spring setter-based dependency injection and is never changed after that (note that check is
     * performed in checkInit() method instead of the setter). Has a setter and a protected getter.
     */
    @PersistenceContext(unitName = "assetPersistenceUnit")
    private EntityManager entityManager;

    /**
     * The logger to be used for logging errors and debug information. Can be initialized in the setLogger() method.
     * If is null, logging is not performed. Has a setter and a protected getter.
     */
    private Log log;

    /**
     * Creates an instance of BaseAssetService.
     */
    protected BaseAssetService() {
        // Empty
    }

    /**
     * Checks whether this class was initialized by Spring properly.
     *
     * @throws AssetConfigurationException
     *             if the class was not initialized properly (entityManager is null)
     */
    @PostConstruct
    protected void checkInit() {
        ValidationUtility.checkNotNull(entityManager, "entityManager", AssetConfigurationException.class);
    }

    /**
     * Retrieves the EntityManager instance to be used by subclasses.
     *
     * @return the EntityManager instance to be used by subclasses.
     */
    protected EntityManager getEntityManager() {
        return entityManager;
    }

    /**
     * Sets the EntityManager instance to be used by subclasses.
     *
     * @param entityManager
     *            the EntityManager instance to be used by subclasses.
     */
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Retrieves the logger to be used for logging errors and debug information.
     *
     * @return the logger to be used for logging errors and debug information.
     */
    protected Log getLog() {
        return log;
    }

    /**
     * Sets the logger to be used for logging errors and debug information.
     *
     * @param log
     *            the logger to be used for logging errors and debug information.
     */
    public void setLog(Log log) {
        this.log = log;
    }
}
