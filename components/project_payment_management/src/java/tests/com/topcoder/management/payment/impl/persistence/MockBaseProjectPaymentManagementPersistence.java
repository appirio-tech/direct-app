/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.payment.impl.persistence;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.management.payment.ProjectPaymentManagementConfigurationException;

/**
 * <p>
 * A mockup class of BaseProjectPaymentManagementPersistence. Used for testing.
 * </p>
 *
 * @author sparemax
 * @version 1.0
 */
public class MockBaseProjectPaymentManagementPersistence extends BaseProjectPaymentManagementPersistence {
    /**
     * Creates an instance of MockBaseProjectPaymentManagementPersistence.
     */
    public MockBaseProjectPaymentManagementPersistence() {
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
    public void configure(ConfigurationObject configuration) {
        super.configure(configuration);
    }

    /**
     * Gets the database connection factory to be used.
     *
     * @return the database connection factory to be used.
     */
    public DBConnectionFactory getDbConnectionFactory() {
        return super.getDbConnectionFactory();
    }

    /**
     * Gets the connection name to be used.
     *
     * @return the connection name to be used.
     */
    public String getConnectionName() {
        return super.getConnectionName();
    }
}
