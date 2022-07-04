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
 * The <code>SqlResourcePersistence</code> class in version 1.1 is completely compatible with the
 * <code>SqlResourcePersistence</code> class in version 1.0.1, and has no additional functionality. However,
 * the bulk of the logic that was in this class in version 1.0.1 has been moved into an abstract superclass
 * that is the base for <code>UnmanagedTransactionResourcePersistence</code> as well.
 * </p>
 * <p>
 * The only logic remaining in this class is that of opening connections and managing transactions, and the
 * only methods implemented in this class are <code>{@linkplain #openConnection()}</code>,
 * <code>{@linkplain #closeConnection(Connection)}</code> and
 * <code>{@linkplain #closeConnectionOnError(Connection)}</code> , which are concrete implementations of the
 * corresponding protected abstract methods in <code>AbstractResourcePersistence</code> and are used in the
 * context of a Template Method pattern.
 * </p>
 * <p>
 * <i>Thread Safety</i> : This class is immutable and thread-safe in the sense that multiple threads can not
 * corrupt its internal data structures. However, the results if used from multiple threads can be
 * unpredictable as the database is changed from different threads. This can equally well occur when the
 * component is used on multiple machines or multiple instances are used, so this is not a thread-safety
 * concern.
 * </p>
 *
 * @author aubergineanode, Chenhong, bendlund, mittu
 * @version 1.1
 * @since 1.0
 */
public class SqlResourcePersistence extends AbstractResourcePersistence {

	/** Logger instance using the class name as category */
    private static final Log LOGGER = LogManager.getLog(SqlResourcePersistence.class.getName()); 
    
    /**
     * this property use for unmanaged environments/test cases 
     */
    private boolean useManualCommit;
    
	/**
     * <p>
     * Creates a new <code>SqlResourcePersistence</code> using the connectionFactory.
     * </p>
     *
     * @param connectionFactory
     *            The connection factory to use for getting connections to the database.
     * @throws IllegalArgumentException
     *             If connectionFactory is <code>null</code>.
     */
    public SqlResourcePersistence(DBConnectionFactory connectionFactory) {
        super(connectionFactory);
    }

    /**
     * <p>
     * Creates a new <code>SqlResourcePersistence</code> using the connectionFactory and connectionName.
     * </p>
     *
     * @param connectionFactory
     *            The connection factory to use for getting connections to the database.
     * @param connectionName
     *            The name of the connection to use. Can be <code>null</code>.
     * @throws IllegalArgumentException
     *             If connectionFactory is <code>null</code>.
     */
    public SqlResourcePersistence(DBConnectionFactory connectionFactory, String connectionName) {
        super(connectionFactory, connectionName);
    }

    /**
     * <p>
     * Opens the connection using the connectionFactory and connectionName. If the connectionName is
     * <code>null</code> or empty it uses the default connection.
     * </p>
     * <p>
     * The connection will be returned with autocommit disabled.
     * </p>
     *
     * @return an open Connection, ready for use
     * @throws ResourcePersistenceException
     *             if an exception is thrown while trying to get or open the connection
     */
    protected Connection openConnection() throws ResourcePersistenceException {
        Connection connection = null;
        try {
            if (getConnectionName() == null || getConnectionName().trim().length() == 0) {
            	LOGGER.log(Level.INFO, new LogMessage(null, null, "creating db connection using default connection"));
                connection = getConnectionFactory().createConnection();
            } else {
            	LOGGER.log(Level.INFO, new LogMessage(null, null,
            			"creating db connection using connection name: " + getConnectionName()));
                connection = getConnectionFactory().createConnection(getConnectionName());
            }
            if(useManualCommit) {
            	connection.setAutoCommit(false);
            }
            return connection;
        } catch (DBConnectionException e) {
            throw new ResourcePersistenceException("Failed to create the connection", e);
        } catch (SQLException e) {
            // connection occured while setting the autocommit flag
            closeConnectionOnError(connection);
            throw new ResourcePersistenceException("Failed to disable autocommit on connection", e);
        }
    }

    /**
     * <p>
     * Concrete implementation of closeConnection used to commit a transaction and close the connection after
     * an operation successfully completes.
     * </p>
     *
     * @param connection
     *            Connection to close
     * @throws ResourcePersistenceException
     *             if any exception is thrown while committing any transaction or closing the connection
     * @throws IllegalArgumentException
     *             if the argument is <code>null</code>
     */
    protected void closeConnection(Connection connection) throws ResourcePersistenceException {
        Util.checkNull(connection, "connection");
        try {
            if (!connection.isClosed()) {
            	LOGGER.log(Level.INFO, "committing transaction");
            	if(useManualCommit) {
            		connection.commit();
            	}
            }
        } catch (SQLException e) {
            throw new ResourcePersistenceException("Failed to close the connection properly", e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                // ignore
            }
        }
    }

    /**
     * <p>
     * Concrete implementation of closeConnectionError used to rollback a transaction and close the connection after
     * an operation fails to complete.
     * </p>
     *
     * @param connection
     *            Connection to close
     * @throws ResourcePersistenceException
     *             if any exception is thrown while rolling back the transaction or closing the connection
     * @throws IllegalArgumentException
     *             if the argument is <code>null</code>
     */
    protected void closeConnectionOnError(Connection connection) throws ResourcePersistenceException {
        Util.checkNull(connection, "connection");
        try {
            if (!connection.isClosed()) {
            	LOGGER.log(Level.INFO, "rollback transaction");
            	if(useManualCommit) {
            		connection.rollback();
            	}
            }
        } catch (SQLException e) {
            throw new ResourcePersistenceException("Failed to close the connection properly", e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                // ignore
            }
        }
    }
    
    /**
	 * <p>Return the useManualCommit.</p>
	 * @return the boolean 
	 */
	public boolean isUseManualCommit() {
		return useManualCommit;
	}
	
	/**
	 * set useManualCommit
	 * @param useManualCommit
	 */
	public void setUserManualCommit(boolean useManualCommit) {
		this.useManualCommit = useManualCommit;
	}
}
