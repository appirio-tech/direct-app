/*
 * Copyright (C) 2006-2009 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.management.db;

import java.sql.Connection;
import java.sql.SQLException;

import com.cronos.onlinereview.autoscreening.management.Helper;
import com.cronos.onlinereview.autoscreening.management.PersistenceException;
import com.cronos.onlinereview.autoscreening.management.ScreeningManager;
import com.topcoder.db.connectionfactory.DBConnectionException;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

/**
 * <p>
 * This class abstracts the screening manager that uses database as the persistence for the
 * screening tasks. The database connection is made configurable by using the DB Connection Factory
 * component. The connection-related parameters should be provided in the constructor in order to be
 * used by the Object Factory.
 * </p>
 * <p>
 * Under this abstraction, subclasses don't need to care about how the database connection is
 * specified. Rather, they can just focus on the persistence logic for the operations.
 * </p>
 * <p>
 * This class is thread-safe by being immutable.
 * </p>
 *
 * <p>
 * Changes in v1.1 (Cockpit Spec Review Backend Service Update v1.0):
 * - added flag so that container transaction demarcation can be used.
 * </p>
 *
 * @author colau, haozhangr, pulky
 * @version 1.1
 */
public abstract class DbScreeningManager implements ScreeningManager {

    /**
     * <p>
     * Represents the database connection factory to produce connections (based on the connection
     * producer name).
     * </p>
     * <p>
     * It is initialized in the constructor and not changed afterwards. It cannot be null. It is
     * accessed in the createConnection() method.
     * </p>
     *
     *
     */
    private final DBConnectionFactory connectionFactory;

    /**
     * <p>
     * Represents the connection producer name which is used to obtain connection from the
     * connection factory.
     * </p>
     * <p>
     * It is initialized in the constructor and not changed afterwards. It can be null (if default
     * connection producer name is used) or non-empty. It is accessed in the createConnection()
     * method.
     * </p>
     *
     *
     */
    private final String connectionName;

    /**
     * <p>
     * Represents whether this component should use manual commit or not.
     * </p>
     *
     * @since 1.1
     */
    protected final Boolean useManualCommit = false;

    /**
     * <p>
     * Constructor that accepts the given argument. Default connection producer name will be used to
     * obtain connections.
     * </p>
     *
     *
     *
     * @param connectionFactory
     *            the connection factory to use
     * @throws IllegalArgumentException
     *             if connectionFactory is null
     */
    protected DbScreeningManager(DBConnectionFactoryImpl connectionFactory) {
        Helper.checkNull(connectionFactory, "connectionFactory");
        this.connectionFactory = connectionFactory;
        this.connectionName = null;
    }

    /**
     * <p>
     * Constructor that accepts the given arguments.
     * </p>
     *
     *
     *
     * @param connectionFactory
     *            the connection factory to use
     * @param connectionName
     *            the connection producer name
     * @throws IllegalArgumentException
     *             if connectionFactory is null, or connectionName is null or empty String
     */
    protected DbScreeningManager(DBConnectionFactoryImpl connectionFactory, String connectionName) {
        Helper.checkNull(connectionFactory, "connectionFactory");
        Helper.checkString(connectionName, "connectionName");
        this.connectionFactory = connectionFactory;
        this.connectionName = connectionName;
    }

    /**
     * <p>
     * Creates a new database connection. The returned connection has auto-commit flag set to false
     * to support transaction.
     * </p>
     *
     *
     * @return a new database connection
     * @throws PersistenceException
     *             if the database connection cannot be created
     */
    protected Connection createConnection() throws PersistenceException {
        try {
            Connection conn = null;
            if (connectionName == null) {
                conn = connectionFactory.createConnection();
            } else {
                conn = connectionFactory.createConnection(connectionName);
            }
            if (useManualCommit) {
                conn.setAutoCommit(false);
            }
            return conn;
        } catch (DBConnectionException e) {
            throw new PersistenceException("Failed to create database connection.", e);
        } catch (SQLException e) {
            throw new PersistenceException(
                    "Failed to set database connection auto commit to false.", e);
        }
    }
}
