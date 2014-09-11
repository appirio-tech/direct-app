/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource.persistence.sql.failuretests;

import java.sql.Connection;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.management.resource.persistence.ResourcePersistenceException;
import com.topcoder.management.resource.persistence.sql.SqlResourcePersistence;

/**
 * This mock class is used to test protected method of SqlResourcePersistence.
 *
 * @author King_Bette
 * @version 1.1
 */
public class MockSqlResourcePersistence extends SqlResourcePersistence {
    /**
     * <p>
     * simply delegate to super constructor.
     * </p>
     *
     * @param connectionFactory The connection factory to use for getting connections to the database.
     *
     * @throws IllegalArgumentException If connectionFactory is null.
     */
    public MockSqlResourcePersistence(DBConnectionFactory connectionFactory) {
        super(connectionFactory);
    }

    /**
     * <p>
     * simply delegate to super constructor.
     * </p>
     *
     * @param connectionFactory The connection factory to use for getting connections to the database.
     * @param connectionName The name of the connection to use. Can be null.
     *
     * @throws IllegalArgumentException If connectionFactory is null.
     */
    public MockSqlResourcePersistence(DBConnectionFactory connectionFactory, String connectionName) {
        super(connectionFactory, connectionName);
    }
    /**
     * <p>
     * simply delegate to super method.
     * </p>
     *
     * @return an open Connection, ready for use
     *
     * @throws ResourcePersistenceException if an exception is thrown while trying to get the connection, or set the
     *         connection into transaction state.
     */
    public Connection openConnection() throws ResourcePersistenceException {
        return super.openConnection();
    }

    /**
     * <p>
     * simply delegate to super method.
     * </p>
     *
     * @param connection Connection to close
     *
     * @throws ResourcePersistenceException if any exception is thrown while committing any transaction
     * @throws IllegalArgumentException if the argument is null
     */
    public void closeConnection(Connection connection)
        throws ResourcePersistenceException {
        super.closeConnection(connection);
    }

    /**
     * <p>
     * simply delegate to super method.
     * </p>
     *
     * @param connection Connection to close
     *
     * @throws ResourcePersistenceException if any exception is thrown while committing any transaction
     * @throws IllegalArgumentException if the argument is null
     */
    public void closeConnectionOnError(Connection connection)
        throws ResourcePersistenceException {
        super.closeConnectionOnError(connection);
    }
}