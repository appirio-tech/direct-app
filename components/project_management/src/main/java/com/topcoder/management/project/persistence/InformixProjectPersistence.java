/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project.persistence;

import java.sql.Connection;
import java.sql.SQLException;

import com.topcoder.management.project.ConfigurationException;
import com.topcoder.management.project.PersistenceException;
import com.topcoder.management.project.persistence.logging.LogMessage;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;

/**
 * <p>
 * It is a subclass of <code>AbstractInformixProjectPersistence</code> manage
 * the transaction itself.
 * </p>
 * <p>
 * the implementations of <code>openConnection()</code>,
 * <code>closeConnection(Connection)</code> and
 * <code>closeConnectionOnError(Connection)</code> call the transaction
 * management related method like <code>commit()</code>,
 * <code>setAutoCommit(boolean)</code> or <code>rollback()</code> to manage
 * the transaction as in the version 1.0.1.
 * </p>
 * <p>
 * <b>Note:</b> The <code>InformixProjectPersistence</code> class in version
 * 1.1 is completely compatible with the <code>InformixProjectPersistence</code>
 * class in version 1.0.1 and has no additional functionality. However, the bulk
 * of the logic that was in this class in version 1.0.1 has been moved into an
 * abstract superclass that is the base for
 * <code>UnmanagedTransactionInformixProjectPersistence</code> as well.
 * </p>
 * <p>
 * Thread Safety: The implementation is not thread safe due to the base class is
 * not thread safe.
 * </p>
 * @author bendlund, TCSDEVELOPER
 * @version 1.1
 * @since 1.0
 */
public class InformixProjectPersistence extends
        AbstractInformixProjectPersistence {

	/** Logger instance using the class name as category */
    private static final Log LOGGER = LogManager.getLog(InformixProjectPersistence.class.getName()); 

    /**
     * this property use for unmanaged environments/test cases 
     */
    private boolean useManualCommit;
    

    
    /**
     * <p>
     * Creates a new instance of <code>InformixProjectPersistence</code> from
     * the settings in configuration.
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
     *     &lt;Config name=&quot;AbstractInformixProjectPersistence&quot;&gt;
     *     &lt;!-- The DBConnectionFactory class name used to create DB Connection Factory, required --&gt;
     *     &lt;Property name=&quot;ConnectionFactoryNS&quot;&gt;
     *     &lt;Value&gt;com.topcoder.db.connectionfactory.DBConnectionFactoryImpl&lt;/Value&gt;
     *     &lt;/Property&gt;
     *     &lt;Property name=&quot;ConnectionName&quot;&gt;
     *     &lt;Value&gt;informix_connection&lt;/Value&gt;
     *     &lt;/Property&gt;
     *     &lt;Property name=&quot;ProjectIdSequenceName&quot;&gt;
     *     &lt;Value&gt;project_id_seq&lt;/Value&gt;
     *     &lt;/Property&gt;
     *     &lt;Property name=&quot;ProjectAuditIdSequenceName&quot;&gt;
     *     &lt;Value&gt;project_audit_id_seq&lt;/Value&gt;
     *     &lt;/Property&gt;
     *     &lt;/Config&gt;
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
    public InformixProjectPersistence(String namespace)
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
     * <p>
     * Once the connection is retrieved, the auto commit property will be set
     * false to manage the transaction itself.
     * </p>
     * @return an open Connection to underlying database.
     * @throws PersistenceException if there's a problem getting the Connection
     */
    protected Connection openConnection() throws PersistenceException {

        String connectionName = getConnectionName();
        if ( connectionName == null){
        	LOGGER.log(Level.INFO, new LogMessage(null, null, "creating db connection using default connection"));
        } else {
        	LOGGER.log(Level.INFO, new LogMessage(null, null, "creating db connection using connection name: " + connectionName));
        }
        Connection conn = Helper.createConnection(getConnectionFactory(),
                connectionName);
        try {
        	if(useManualCommit)  {
        		conn.setAutoCommit(false);
        	}
            return conn;
        } catch (SQLException e) {
            throw new PersistenceException("Error occurs when setting "
                    + (connectionName == null ? "the default connection"
                            : ("the connection '" + connectionName + "'"))
                    + " to support transaction.", e);
        }

    }

    /**
     * <p>
     * Concrete implementation of the corresponding abstract method in
     * <code>AbstractInformixProjectPersistence.</code> used to commit the
     * transaction and close the connection after an operation successfully
     * completes.
     * </p>
     * @param connection a Connection to close
     * @throws PersistenceException if any problem occurs trying to close the
     *             connection
     * @throws IllegalArgumentException if the argument is null
     */
    protected void closeConnection(Connection connection)
        throws PersistenceException {
        Helper.assertObjectNotNull(connection, "connection");
        try {
        	if(useManualCommit) { 
        		LOGGER.log(Level.INFO, "committing transaction");        	
        		Helper.commitTransaction(connection);
        	}
        } finally {
            Helper.closeConnection(connection);
        }

    }

    /**
     * <p>
     * Concrete implementation of the corresponding abstract method in
     * <code>AbstractInformixProjectPersistence.</code> used to rollback the
     * transaction and close the connection after an operation fails to
     * complete.
     * </p>
     * @param connection a connection to close
     * @throws IllegalArgumentException if the argument is null
     * @throws PersistenceException if any problem occurs trying to close the
     *             connection
     */
    protected void closeConnectionOnError(Connection connection)
        throws PersistenceException {
        Helper.assertObjectNotNull(connection, "connection");
        try {
        	if(useManualCommit) {
        		LOGGER.log(Level.INFO, "rollback transaction");
        		Helper.rollBackTransaction(connection);
        	}
        } finally {
            Helper.closeConnection(connection);
        }
    }

    /**
     * <p>Return the logger.</p>
     * @return the <code>Log</code> instance used to take the log message
     */
	protected Log getLogger() {
		return LOGGER;
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
