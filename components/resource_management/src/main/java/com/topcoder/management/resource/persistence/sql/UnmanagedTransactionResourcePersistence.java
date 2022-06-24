/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource.persistence.sql;

import java.sql.Connection;
import java.sql.SQLException;

import com.topcoder.db.connectionfactory.DBConnectionException;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.management.resource.persistence.ResourcePersistenceException;
import com.topcoder.management.resource.persistence.logging.LogMessage;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;

/**
 * <p>
 * The <code>UnmanagedTransactionResourcePersistence</code> class is a new class in version 1.1. It performs
 * exactly the same tasks as <code>SqlResourcePersistence</code>, but is designed to be used with
 * externally managed transactions.
 * </p>
 * <p>
 * The implementations of <code>{@linkplain #openConnection()}</code>,
 * <code>{@linkplain #closeConnection(Connection)}</code> and
 * <code>{@linkplain #closeConnectionOnError(Connection)}</code> this class do not call
 * <code>commit()</code>, <code>setAutoCommit()</code>, or <code>rollback()</code>, as the
 * transaction is expected to be handled externally to the component.
 * </p>
 * <p>
 * <i>Thread Safety</i> : This class is immutable and thread-safe in the sense that multiple threads can not
 * corrupt its internal data structures. However, the results if used from multiple threads can be
 * unpredictable as the database is changed from different threads. This can equally well occur when the
 * component is used on multiple machines or multiple instances are used, so this is not a thread-safety
 * concern.
 * </p>
 *
 * @author bendlund, mittu
 * @version 1.1
 */
public class UnmanagedTransactionResourcePersistence extends AbstractResourcePersistence {

	/** Logger instance using the class name as category */
    private static final Log LOGGER = LogManager.getLog(UnmanagedTransactionResourcePersistence.class.getName()); 
    
    /**
     * <p>
     * Creates a new <code>UnmanagedTransactionResourcePersistence</code> using connectionFactory.
     * </p>
     *
     * @param connectionFactory
     *            The connection factory to use for getting connections to the database.
     * @throws IllegalArgumentException
     *             If connectionFactory is <code>null</code>.
     */
    public UnmanagedTransactionResourcePersistence(DBConnectionFactory connectionFactory) {
        super(connectionFactory);
    }

    /**
     * <p>
     * Creates a new <code>UnmanagedTransactionResourcePersistence</code> using connectionFactory and
     * connectionName.
     * </p>
     *
     *
     * @param connectionFactory
     *            The connection factory to use for getting connections to the database.
     * @param connectionName
     *            The name of the connection to use. Can be <code>null</code>.
     * @throws IllegalArgumentException
     *             If connectionFactory is <code>null</code>.
     */
    public UnmanagedTransactionResourcePersistence(DBConnectionFactory connectionFactory,
        String connectionName) {
        super(connectionFactory, connectionName);
    }

    /**
     * <p>
     * Opens the connection using the connectionFactory and connectionName. If the connectionName is
     * <code>null</code> or empty it uses the default connection.
     * </p>
     *
     * @return an open Connection, ready for use
     * @throws ResourcePersistenceException
     *             if an exception is thrown while trying to get or open the connection
     */
    protected Connection openConnection() throws ResourcePersistenceException {
        try {
        	if (getConnectionName() == null || getConnectionName().trim().length() == 0) {
        		LOGGER.log(Level.INFO, new LogMessage(null, null, "creating db connection using default connection"));
                return getConnectionFactory().createConnection();
            }
        	LOGGER.log(Level.INFO, new LogMessage(null, null,
        			"creating db connection using connection name: " + getConnectionName()));
            return getConnectionFactory().createConnection(getConnectionName());
        } catch (DBConnectionException e) {
            throw new ResourcePersistenceException("Failed to create the connection", e);
        }
    }

    /**
     * <p>
     * Concrete implementation of closeConnection used to commit a transaction and close the connection after
     * an operation successfully completes.
     * </p>
     * <p>
     * Exceptions resulting from <code>connection.close()</code> is ignored.
     * </p>
     *
     * @param connection
     *            Connection to close
     * @throws IllegalArgumentException
     *             if the argument is <code>null</code>
     */
    protected void closeConnection(Connection connection) {
        Util.checkNull(connection, "connection");
        try {
            if (!connection.isClosed()) {
            	LOGGER.log(Level.INFO, "close the connection.");
            	connection.close();
            }
        } catch (SQLException e) {
            // ignore
        }
    }

    /**
     * <p>
     * Concrete implementation of closeConnectionError used to rollback a transaction and close the connection after
     * an operation fails to complete.
     * </p>
     * <p>
     * Exceptions resulting from <code>connection.close()</code> is ignored.
     * </p>
     *
     * @param connection
     *            Connection to close
     * @throws IllegalArgumentException
     *             if the argument is <code>null</code>
     */
    protected void closeConnectionOnError(Connection connection) {
        closeConnection(connection);
    }
}
