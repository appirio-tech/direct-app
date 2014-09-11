/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project.persistence;

import java.sql.Connection;

import com.topcoder.management.project.ConfigurationException;
import com.topcoder.management.project.PersistenceException;
import com.topcoder.management.project.persistence.logging.LogMessage;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogFactory;
import com.topcoder.util.log.LogManager;

/**
 * <p>
 * It is a subclass of <code>AbstractInformixProjectPersistence</code>
 * providing the unmanaged-transaction version of DB connection management.
 * </p>
 * <p>
 * It is supposed to be used with externally managed transactions so the
 * implementations of <code>openConnection()</code>,
 * <code>closeConnection(Connection)</code> and
 * <code>closeConnectionOnError(Connection)</code> does not call the
 * transaction management related method like <code>commit()</code>,
 * <code>setAutoCommit(boolean)</code> or <code>rollback()</code>. The
 * transaction is expected to be handled externally to the component such as
 * EJB.
 * </p>
 * <p>
 * Thread Safety: This class is thread safe because it is immutable.
 * </p>
 * @author bendlund, fuyun
 * @version 1.1
 */
public class UnmanagedTransactionInformixProjectPersistence extends
        AbstractInformixProjectPersistence {
	/** Logger instance using the class name as category */
    private static final Log LOGGER = LogManager.getLog(UnmanagedTransactionInformixProjectPersistence.class.getName()); 
    /**
     * <p>
     * Creates a new instance of
     * <code>UnmanagedTransactionInformixProjectPersistence</code> from the
     * settings in configuration.
     * </p>
     * <p>
     * The following parameters are configured.
     * <ul>
     * <li>ConnectionFactoryNS: The namespace that contains settings for DB
     * Connection Factory. It is required.</li>
     * <li>ConnectionName: The name of the connection that will be used by
     * DBConnectionFactory to create connection. If missing, default connection
     * will be created. It is optional.</li>
     * <li>ProjectIdSequenceName: The sequence name used to create Id generator
     * for project Id. If missing default value (project_id_seq) is used. It is
     * optional.</li>
     * <li>ProjectAuditIdSequenceName: The sequence name used to create Id
     * generator for project audit Id. If missing, default value
     * (project_audit_id_seq) is used. It is optional.</li>
     * </ul>
     * </p>
     * <p>
     * Configuration sample:
     *
     * <pre>
     *    &lt;Config name=&quot;AbstractInformixProjectPersistence&quot;&gt;
     *    &lt;!-- The DBConnectionFactory class name used to create DB Connection Factory, required --&gt;
     *    &lt;Property name=&quot;ConnectionFactoryNS&quot;&gt;
     *    &lt;Value&gt;com.topcoder.db.connectionfactory.DBConnectionFactoryImpl&lt;/Value&gt;
     *    &lt;/Property&gt;
     *    &lt;Property name=&quot;ConnectionName&quot;&gt;
     *    &lt;Value&gt;informix_connection&lt;/Value&gt;
     *    &lt;/Property&gt;
     *    &lt;Property name=&quot;ProjectIdSequenceName&quot;&gt;
     *    &lt;Value&gt;project_id_seq&lt;/Value&gt;
     *    &lt;/Property&gt;
     *    &lt;Property name=&quot;ProjectAuditIdSequenceName&quot;&gt;
     *    &lt;Value&gt;project_audit_id_seq&lt;/Value&gt;
     *    &lt;/Property&gt;
     *    &lt;/Config&gt;
     * </pre>
     *
     * </p>
     * @param namespace The namespace to load connection setting.
     * @throws IllegalArgumentException if the input is null or empty string.
     * @throws ConfigurationException if error occurs while loading
     *             configuration settings, or required configuration parameter
     *             is missing.
     * @throws PersistenceException if cannot initialize the connection to the
     *             database.
     */
    public UnmanagedTransactionInformixProjectPersistence(String namespace)
        throws ConfigurationException, PersistenceException {
        super(namespace);
    }

    /**
     * <p>
     * Concrete implementation of the corresponding abstract method in
     * <code>AbstractInformixProjectPersistence.</code>.
     * </p>
     * <p>
     * It uses the DB connection factory to create the connection to underlying
     * database. If the connection is not configured, the default connection
     * from DB connection factory will be created, otherwise, the connection
     * with the specified name in DB connection factory will be created.
     * </p>
     * @return an open connection to underlying database.
     * @throws PersistenceException if there's a problem getting the Connection
     */
    protected Connection openConnection() throws PersistenceException {
    	 if ( getConnectionName() == null){
         	LOGGER.log(Level.INFO, new LogMessage(null, null, "creating db connection using default connection"));
         } else {
         	LOGGER.log(Level.INFO, new LogMessage(null, null, "creating db connection using connection name: " + getConnectionName()));
         }
        return Helper.createConnection(getConnectionFactory(),
                getConnectionName());
    }

    /**
     * <p>
     * Concrete implementation of the corresponding abstract method in
     * <code>AbstractInformixProjectPersistence.</code> used to close the
     * connection after an operation successfully completes.
     * </p>
     * @param connection a connection to close
     * @throws IllegalArgumentException if the argument is null
     * @throws PersistenceException if any problem occurs trying to close the
     *             connection
     */
    protected void closeConnection(Connection connection)
        throws PersistenceException {
        Helper.assertObjectNotNull(connection, "connection");
        LOGGER.log(Level.INFO, "close the connection.");
        Helper.closeConnection(connection);
    }

    /**
     * <p>
     * Concrete implementation of the corresponding abstract method in
     * <code>AbstractInformixProjectPersistence.</code> used to close the
     * connection after an operation fails to complete.
     * </p>
     * @param connection a Connection to close
     * @throws IllegalArgumentException if the argument is null
     * @throws PersistenceException if any problem occurs trying to close the
     *             connection
     */
    protected void closeConnectionOnError(Connection connection)
        throws PersistenceException {
        closeConnection(connection);
    }

    /**
     * <p>Return the logger.</p>
     * @return the <code>Log</code> instance used to take the log message
     */
	protected Log getLogger() {
		return LOGGER;
	}
}
