/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.payment.impl.persistence;

import com.topcoder.commons.utils.ParameterCheckUtility;
import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.db.connectionfactory.ConfigurationException;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.db.connectionfactory.UnknownConnectionException;
import com.topcoder.management.payment.ProjectPaymentManagementConfigurationException;
import com.topcoder.management.payment.impl.Helper;
import com.topcoder.util.log.Log;

/**
 * <p>
 * This class is base class for persistence classes of this component that uses JDBC and DB Connection Factory
 * component. This class aggregates logger from Logging Wrapper component to be used by subclasses to log errors and
 * debug information.
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> This class is mutable, but thread safe when configure() method is called just once
 * right after instantiation and entities passed to it are used by the caller in thread safe manner. It uses thread
 * safe DBConnectionFactory and Log instances.
 * </p>
 *
 * @author maksimilian, sparemax
 * @version 1.0
 */
public abstract class BaseProjectPaymentManagementPersistence {
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
     * The logger used by this class for logging errors and debug information. It is null if logging is not
     * requireUsed Initialized in the configure() method and never changed after that. Used in getter. Please see
     * section 1.3.1 of CS for logging details.
     */
    private Log log;

    /**
     * The database connection factory to be used. Initialized in the configure() method and never changed after that.
     * Cannot be null after initialization. Used in getter.
     */
    private DBConnectionFactory dbConnectionFactory;

    /**
     * The connection name to be used. Initialized in the configure() method and never changed after that. Cannot be
     * empty after initialization. If null, the default connection is used. Used in getter.
     */
    private String connectionName;

    /**
     * Creates an instance of BaseProjectPaymentManagementPersistence.
     */
    protected BaseProjectPaymentManagementPersistence() {
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
     * @throws ProjectPaymentManagementConfigurationException
     *             if some error occurred when initializing an instance using the given configuration
     */
    protected void configure(ConfigurationObject configuration) {
        ParameterCheckUtility.checkNotNull(configuration, "configuration");

        // Get Log instance to be used
        log = Helper.getLog(configuration);

        try {
            // Get configuration of DB Connection Factory
            ConfigurationObject dbConnectionFactoryConfig = Helper.getChildConfig(configuration, KEY_DBCF_CONFIG);
            // Create DB connection factory
            dbConnectionFactory = new DBConnectionFactoryImpl(dbConnectionFactoryConfig);

            // Get connection name
            connectionName = Helper.getProperty(configuration, KEY_CONN_NAME, false);
        } catch (UnknownConnectionException e) {
            throw new ProjectPaymentManagementConfigurationException(
                "The connection is invalid.", e);
        } catch (ConfigurationException e) {
            throw new ProjectPaymentManagementConfigurationException(
                "Failed to create a database connection factory.", e);
        }
    }

    /**
     * Gets the logger used by this class for logging errors and debug information.
     *
     * @return the logger used by this class for logging errors and debug information.
     */
    protected Log getLog() {
        return log;
    }

    /**
     * Gets the database connection factory to be used.
     *
     * @return the database connection factory to be used.
     */
    protected DBConnectionFactory getDbConnectionFactory() {
        return dbConnectionFactory;
    }

    /**
     * Gets the connection name to be used.
     *
     * @return the connection name to be used.
     */
    protected String getConnectionName() {
        return connectionName;
    }
}
