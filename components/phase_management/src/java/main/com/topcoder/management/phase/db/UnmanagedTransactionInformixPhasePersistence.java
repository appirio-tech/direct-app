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
 * The main purpose of this class is to intercept any transactional actions and
 * basically disregard them so that some other means of controlling the
 * transactions could be used (i.e. Stateless Session EJB for example -
 * container manager transaction)
 * </p>
 * <p>
 * The most important aspect is that the <code>rollbackTransaction(Map)</code>
 * method simply throws the <code>PhasePersistenceException</code> which would
 * then be caught by any of the C[R]UD (excluding any reads which are
 * non-transactional) methods and which would force a re-throw of the exception
 * to the caller. This allows the caller to properly rollback on their own.
 * </p>
 * <p>
 * <b>Thread-Safety:</b> This implementation doesn't change the aspect of the
 * base class' thread safety and is thus thread-safe.
 * </p>
 * @author AleaActaEst, TCSDEVELOPER
 * @version 1.1
 */
public class UnmanagedTransactionInformixPhasePersistence extends
        AbstractInformixPhasePersistence {

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
    public UnmanagedTransactionInformixPhasePersistence(String namespace)
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
    public UnmanagedTransactionInformixPhasePersistence(
            DBConnectionFactory connectionFactory, String connectionName,
            IDGenerator idGen) {
        super(connectionFactory, connectionName, idGen);
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
     * Commits the transaction. Nothing is done in this method.
     * </p>
     * <p>
     * The null context is allowed.
     * </p>
     * @param context transaction context information.
     * @throws IllegalArgumentException if the context argument contains null
     *             entry (key or value).
     */
    protected void commitTransaction(Map context) {
        Helper.checkMap(context, "context");
    }

    /**
     * <p>
     * Rolls back the transaction. This method always throw
     * <code>PhasePersistenceException</code>.
     * </p>
     * <p>
     * The null context is allowed.
     * </p>
     * @param context transaction context information.
     * @throws IllegalArgumentException if the context argument contains null
     *             entry (key or value).
     * @throws PhasePersistenceException always
     */
    protected void rollbackTransaction(Map context)
        throws PhasePersistenceException {
        Helper.checkMap(context, "context");
        throw new PhasePersistenceException("The roll back is not supported.");
    }

    /**
     * <p>
     * Starts the transaction. Nothing is done in this method.
     * </p>
     * <p>
     * The null context is allowed.
     * </p>
     * @param context transaction context information.
     * @throws IllegalArgumentException if the context argument contains null
     *             entry (key or value).
     */
    protected void startTransaction(Map context) {
        Helper.checkMap(context, "context");
    }

}
