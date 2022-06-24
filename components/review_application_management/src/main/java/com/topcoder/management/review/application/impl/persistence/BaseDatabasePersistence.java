/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.review.application.impl.persistence;

import java.sql.Connection;
import java.sql.SQLException;

import com.topcoder.commons.utils.ParameterCheckUtility;
import com.topcoder.commons.utils.ValidationUtility;
import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.db.connectionfactory.ConfigurationException;
import com.topcoder.db.connectionfactory.DBConnectionException;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.db.connectionfactory.UnknownConnectionException;
import com.topcoder.management.review.application.Helper;
import com.topcoder.management.review.application.ReviewApplicationManagementConfigurationException;
import com.topcoder.util.log.Log;

/**
 * <p>
 * This class is base class for persistence implementations that access data in database persistence using JDBC and DB
 * Connection Factory component.
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> This class is mutable, but thread safe when configure() method is called just once
 * right after instantiation and entities passed to it are used by the caller in thread safe manner. It uses thread safe
 * DBConnectionFactory and Log instances.
 * </p>
 *
 * @author albertwang, sparemax
 * @version 1.0
 */
public abstract class BaseDatabasePersistence {
    /**
     * <p>
     * Represents the class name.
     * </p>
     */
    private static final String CLASS_NAME = BaseDatabasePersistence.class.getName();

    /**
     * <p>
     * Represents the child key 'dbConnectionFactoryConfig'.
     * </p>
     */
    private static final String KEY_DBCF_CONFIG = "dbConnectionFactoryConfig";

    /**
     * <p>
     * Represents the property key 'connectionName'.
     * </p>
     */
    private static final String KEY_CONN_NAME = "connectionName";

    /**
     * Represents the database connection factory to be used. Can be non-null DBConnectionFactory. It will be
     * initialized in the configure() method and never changed after that. Cannot be null after initialization. It will
     * be used in getConnection method.
     */
    private DBConnectionFactory dbConnectionFactory;

    /**
     * Represents the connection name. Can be non-empty string. Could be null, if null the default connection will be
     * used. It will be initialized in the configure() method and never changed after that. Cannot be empty after
     * initialization.
     */
    private String connectionName;

    /**
     * Represents the logger used to perform logging. Can be Log instance, could be null, null means no logging is
     * required. It will be initialized in the configure() method and never changed after that. It will be accessed by
     * subclasses via protected getter.
     */
    private Log log;

    /**
     * Creates an instance of BaseDatabasePersistence.
     */
    protected BaseDatabasePersistence() {
        // Empty
    }

    /**
     * Configures this instance with use of the given configuration object.
     *
     * @param configuration
     *            the configuration object
     *
     * @throws IllegalArgumentException
     *             if configuration is null
     * @throws ReviewApplicationManagementConfigurationException
     *             if some error occurred when initializing an instance using the given configuration
     */
    public void configure(ConfigurationObject configuration) {
        try {
            ParameterCheckUtility.checkNotNull(configuration, "configuration");

            // Get Log instance to be used
            log = Helper.getLog(configuration);

            // Get configuration of DB Connection Factory
            ConfigurationObject dbConnectionFactoryConfig = Helper.getChildConfig(configuration, KEY_DBCF_CONFIG);
            // Create database connection factory using the extracted configuration
            dbConnectionFactory = new DBConnectionFactoryImpl(dbConnectionFactoryConfig);

            // Get connection name
            connectionName = Helper.getProperty(configuration, KEY_CONN_NAME, false);
        } catch (UnknownConnectionException e) {
            throw new ReviewApplicationManagementConfigurationException(
                "Failed to create a database connection factory.", e);
        } catch (ConfigurationException e) {
            throw new ReviewApplicationManagementConfigurationException(
                "Failed to create a database connection factory.", e);
        }
    }

    /**
     * Gets the logger used to perform logging.
     *
     * @return the logger used to perform logging.
     */
    protected Log getLog() {
        return log;
    }

    /**
     * Create and return a database connection.
     *
     * @return the database connection
     *
     * @throws IllegalStateException
     *             if the instance is not configured yet (dbConnectionFactory is null).
     * @throws DBConnectionException
     *             if any error occurs while creating the connection.
     */
    protected Connection getConnection() throws DBConnectionException {
        ValidationUtility.checkNotNull(dbConnectionFactory, "dbConnectionFactory", IllegalStateException.class);

        Connection connection;
        if (connectionName == null) {
            connection = dbConnectionFactory.createConnection();
        } else {
            connection = dbConnectionFactory.createConnection(connectionName);
        }
       
        return connection;
       
    }
}
