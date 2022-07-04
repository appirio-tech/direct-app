/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.management.phase.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.management.phase.ConfigurationException;
import com.topcoder.management.phase.PhasePersistenceException;
import com.topcoder.util.idgenerator.IDGenerator;

/**
 * <p>
 * This is a specific implementation of transactional control on top of database
 * persistence actions geared towards an Informix Database platform.
 * </p>
 * <p>
 * The main purpose of this class is to fully control transactional actions and
 * basically deal with them per persistence action on the very connection that
 * is being used.
 * </p>
 * <p>
 * Using this class, the transaction will be committed if the operation competes
 * successfully and will be rolled back if any issue occurs during the DB
 * operation.
 * </p>
 * <p>
 * <b>Version 1.1 changes:</b> There are no behavioral changes in this class.
 * The actual architecture of the component has changed and now this is based on
 * an abstract class but from a user/caller perspective (i.e. client code) there
 * is no change whatsoever, it is fully back compatible with version 1.0
 * </p>
 * <p>
 * <b>Thread-Safety:</b> This implementation doesn't change the aspect of the
 * base class' thread safety and is thus thread-safe.
 * </p>
 * @author AleaActaEst, TCSDEVELOPER
 * @version 1.1
 * @since 1.0
 */
public class InformixPhasePersistence extends AbstractInformixPhasePersistence {

	
	/**
     * this property use for unmanaged environments/test cases 
     */
    private boolean useManualCommit;
    
    /**
     * <p>
     * An simple constructor which will populate the connectionFactory and
     * connectionName information from configuration. It will also read the
     * IDGenerator sequence name and implementation class (optional).
     * </p>
     * <p>
     * The configurations are:
     * <ol>
     * <li>connectionName - The connection name used to get connection from
     * connection factory, if it is not configured, the default connection will
     * be used, optional.</li>
     * <li>connectionFactoryClassName - The class name of connection factory,
     * required.</li>
     * <li>connectionFactoryNamespace - The namespace used to create the
     * connection factory, required.</li>
     * <li>Idgenerator.sequenceName - Used to retrieve an IDGenerator that can
     * service it, required. </li>
     * <li>Idgenerator.className - Name of the IDGenerator class that services
     * the named sequence, optional.</li>
     * </ol>
     * </p>
     * @param namespace the namespace to get the configuration.
     * @throws IllegalArgumentException if namespace is an empty string or a
     *             null.
     * @throws ConfigurationException if any of the required configuration
     *             parameters are missing or are incorrect.
     */
    public InformixPhasePersistence(String namespace)
        throws ConfigurationException {
        super(namespace);
    }

    /**
     * <p>
     * A simple constructor which will populate the id generator, connection
     * factory and connection name information from input parameters.
     * </p>
     * @param connectionFactory connection factory instance
     * @param connectionName connection name.
     * @param idGen the IdGenerator that will be used to create the new ids for
     *            entities.
     * @throws IllegalArgumentException if the connectionFactory is
     *             <code>null</code>, idGen is <code>null</code> or the connectionName
     *             is empty String.
     */
    public InformixPhasePersistence(DBConnectionFactory connectionFactory,
            String connectionName, IDGenerator idGen) {
        super(connectionFactory, connectionName, idGen);
    }

    /**
     * <p>
     * Fetches a new connection from connection factory based on the
     * connectionName variable. If connectionName is null/empty the default
     * connection from connection factory will be retrieved.
     * </p>
     * <p>
     * The auto commit property is set false to support the transaction
     * handling.
     * </p>
     * @return a new connection from the connection factory based on the given
     *         connection name.
     * @throws PhasePersistenceException if there is any issue when getting the
     *             connection or set the auto commit property.
     */
    protected Connection getConnection() throws PhasePersistenceException {
        // get the connection.
        Connection conn = super.getConnection();
        try {
            // set the auto commit property.
        	if(useManualCommit) {
        		conn.setAutoCommit(false);
        	}
            return conn;
        } catch (SQLException e) {
            throw new PhasePersistenceException(
                    "Error occurs when setting the connection"
                            + " to support transaction.", e);
        }
    }

    /**
     * <p>
     * Disposes the connection by closing the connection.
     * </p>
     * <p>
     * If exception is thrown by the close method, it will be ignored.
     * </p>
     * @param connection the connection to dispose.
     * @throws IllegalArgumentException if the argument is null.
     */
    protected void disposeConnection(Connection connection) {

        Helper.checkNull(connection, "connection");

        try {
            connection.close();
        } catch (SQLException e) {
            // ignore the exception
        }
    }

    /**
     * <p>
     * Commits the transaction using the connection in the context.
     * </p>
     * <p>
     * The connection should be in the context map with key 'connection'.
     * </p>
     * @param context transaction context information.
     * @throws IllegalArgumentException if the context argument is null,
     *             contains null entry (key or value) or there is no connection
     *             in the context with key 'connection'.
     * @throws PhasePersistenceException if fails to commit the changes
     *             associated with the connection in the context.
     */
    protected void commitTransaction(Map context)
        throws PhasePersistenceException {

        Helper.checkMap(context, "context");

        Connection conn = checkConnectionExists(context, "context");
        try {
        	if(useManualCommit) {
        		conn.commit();
        	}
        } catch (SQLException e) {
            throw new PhasePersistenceException(
                    "Error occurs when doing commit.", e);
        }
    }

    /**
     * <p>
     * Rolls back the transaction using the connection in the context.
     * </p>
     * <p>
     * The connection should be in the context map with key 'connection'.
     * </p>
     * @param context transaction context information.
     * @throws IllegalArgumentException if the context argument is null,
     *             contains null entry (key or value) or there is no connection
     *             in the context with key 'connection'.
     * @throws PhasePersistenceException if fails to rollback the changes
     *             associated with the connection in the context.
     */
    protected void rollbackTransaction(Map context)
        throws PhasePersistenceException {
        Helper.checkMap(context, "context");

        Connection conn = checkConnectionExists(context, "context");
        try {
        	if(useManualCommit) {
        		conn.rollback();
        	}
        } catch (SQLException e) {
            throw new PhasePersistenceException(
                    "Error occurs when doing rollback.", e);
        }
    }

    /**
     * <p>
     * Starts the transaction. Nothing is done in this method.
     * </p>
     * <p>
     * The null context is allowed.
     * </p>
     * @param context transaction context information.
     * @throws IllegalArgumentException if the context contains null entry (key
     *             or value).
     */
    protected void startTransaction(Map context) {
        Helper.checkMap(context, "context");
    }

    /**
     * Checks if the connection with key 'connection' exists in the context.
     * @param context the context map used to find the connection.
     * @param name the name of the context map.
     * @return the connection if it can be found in the context map.
     */
    private Connection checkConnectionExists(Map context, String name) {
        if (context == null) {
            throw new IllegalArgumentException("The argument " + name
                    + " could not be null.");
        }
        Connection conn = (Connection) context.get(CONNECTION_CONTEXT_KEY);
        if (conn == null) {
            throw new IllegalArgumentException("The argument map " + name
                    + " does not contain the connection with key "
                    + CONNECTION_CONTEXT_KEY);
        }
        return conn;
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
