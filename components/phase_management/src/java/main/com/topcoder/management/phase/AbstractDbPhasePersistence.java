/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.management.phase;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.util.Map;

import com.topcoder.db.connectionfactory.DBConnectionException;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.UnknownNamespaceException;
import com.topcoder.util.idgenerator.IDGenerationException;
import com.topcoder.util.idgenerator.IDGenerator;
import com.topcoder.util.idgenerator.IDGeneratorFactory;

/**
 * <p>
 * This is a simple abstract persistence class which specifies the basic
 * operations that all DAOs (i.e. persistence classes) would do.
 * </p>
 * <p>
 * This class basically abstracts the 'plumbing' of what DAOs will deal with.
 * Thus Some actions are abstracted out, such as reading connection
 * configuration data as well as creating overloadable API for getting a
 * connection, closing a connection as well as managing transactions through
 * connections or in a different manner (as in unmanaged transactions actually).
 * </p>
 * <p>
 * The users could extend this class and they could then simply
 * implement/overload the relevant 'plumbing' methods to deal with the DAO.
 * </p>
 * <p>
 * For example if the user wanted to create a persistence class that will not
 * manage its own transactions he would implement the rollback to
 * just throw the <code>PhasePersistenceException</code> which would then be
 * interpreted by the caller to mean that they need to throw an exception to the
 * main caller which would work perfectly.
 * </p>
 * <p>
 * <b>Thread-Safety:</b> It is expected that the implementations of this class
 * are thread-safe. Current implementations of the class are thread-safe. But
 * the operation to Database is not thread safe because two thread may overwrite
 * each other's work in database.
 * </p>
 * @author AleaActaEst, TCSDEVELOPER
 * @version 1.1
 */
public abstract class AbstractDbPhasePersistence implements PhasePersistence {

    /**
     * <p>
     * This is a convenience key to be used when we need to pass a <code>Connection</code>
     * object to the commit or rollback methods. The user/developer could simply
     * do as follows:
     * </p>
     * <p>
     * context.put(CONNECTION_CONTEXT_KEY, connection);
     * commitTransaction(context);
     * </p>
     */
    public static final String CONNECTION_CONTEXT_KEY = "connection";

    /**
     * Represents the parameters to create <code>DBConnectionFactory</code>.
     */
    private static final Class[] CTOR_PARAMS = new Class[] {String.class};

    /**
     * <p>
     * Represents the connection factory from which we will obtain a
     * pre-configured connection for our data base access. This is initialized
     * in one of the constructors and once initialized cannot be changed. Cannot
     * be null.
     * <p>
     */
    private final DBConnectionFactory connectionFactory;

    /**
     * <p>
     * Represents a connection name (an alias) that is used to fetch a
     * connection instance from the connection factory. This is initialized in
     * one of the constructors and once initialized cannot be changed. Can be
     * null or an empty string, upon which it will try to use the default
     * connection.
     * </p>
     */
    private final String connectionName;

    /**
     * This ID Generator will be used to generate the new ids in the create and
     * update methods. It is initialized in the constructor and never changed
     * after that.
     */
    private final IDGenerator idGenenerator;

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
    protected AbstractDbPhasePersistence(String namespace)
        throws ConfigurationException {
        if (namespace == null) {
            throw new IllegalArgumentException("namespace cannot be null.");
        }

        if (namespace.trim().length() == 0) {
            throw new IllegalArgumentException(
                    "namespace cannot be empty String.");
        }

        String className = getProperty(namespace,
                "ConnectionFactory.className", true);
        String ns = getProperty(namespace, "ConnectionFactory.namespace", true);
        connectionName = getProperty(namespace, "connectionName", false);
        connectionFactory = createConnectionFactory(className, ns);
        // create the idgenerator
        className = getProperty(namespace, "Idgenerator.className", false);
        String sequence = getProperty(namespace, "Idgenerator.sequenceName",
                true);
        idGenenerator = createIdGenerator(sequence, className);
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
    protected AbstractDbPhasePersistence(DBConnectionFactory connectionFactory,
            String connectionName, IDGenerator idGen) {
        if (connectionFactory == null) {
            throw new IllegalArgumentException(
                    "connectionFactory cannot be null.");
        }

        if ((connectionName != null) && (connectionName.trim().length() == 0)) {
            throw new IllegalArgumentException(
                    "The connectionName cannot be empty.");
        }

        if (idGen == null) {
            throw new IllegalArgumentException("idGen cannot be null.");
        }
        this.connectionFactory = connectionFactory;
        this.connectionName = connectionName;
        this.idGenenerator = idGen;
    }

    /**
     * <p>
     * Fetches a new connection from connection factory based on the
     * connectionName variable. If connectionName is not provided the default
     * connection from connection factory will be retrieved.
     * </p>
     * @return a new connection from the connection factory based on the given
     *         connection name.
     * @throws PhasePersistenceException if there is any issue when getting the
     *             connection.
     */
    protected Connection getConnection() throws PhasePersistenceException {
        try {

            return connectionName == null ? connectionFactory
                    .createConnection() : connectionFactory
                    .createConnection(connectionName);

        } catch (DBConnectionException e) {
            throw new PhasePersistenceException("Error occurs when getting "
                    + (connectionName == null ? "the default connection."
                            : ("the connection '" + connectionName + "'.")), e);
        }
    }

    /**
     * <p>
     * This is a clean up contract method to be fulfilled by the extending
     * classes which is used to dispose connection properly(such as closing for
     * example).
     * </p>
     * @param connection the connection to dispose.
     * @throws IllegalArgumentException if the argument is null.
     * @throws PhasePersistenceException if there is any issue to dispose the
     *             connection.
     */
    protected abstract void disposeConnection(Connection connection)
        throws PhasePersistenceException;

    /**
     * <p>
     * This is a transaction commit contract method to be fulfilled by the
     * extending classes which is used to commit the changes properly based on
     * some context input data.
     * </p>
     * <p>
     * The implementation of this method should examine the input map to fetch
     * whatever data it needed, which will most of the time be a
     * <code>Connection</code> with the key "connection" in the map.
     * </p>
     * @param context the transactional context map.
     * @throws IllegalArgumentException if the map is not null but contains null
     *             key or value.
     * @throws PhasePersistenceException if there is any issue when committing
     *             the transaction.
     */
    protected abstract void commitTransaction(Map context)
        throws PhasePersistenceException;

    /**
     * <p>
     * This is a transaction rollback contract method to be fulfilled by the
     * extending classes which is used to rollback changes properly based on
     * some context input data.
     * </p>
     * <p>
     * The implementation of this method should examine the input map to fetch
     * whatever data it needed, which will most of the time be a
     * <code>Connection</code> with the key "connection" in the map.
     * </p>
     * @param context the transactional context map.
     * @throws IllegalArgumentException if the map is not null but contains null
     *             key or value.
     * @throws PhasePersistenceException if there is any issue when rolling back
     *             the transaction.
     */
    protected abstract void rollbackTransaction(Map context)
        throws PhasePersistenceException;

    /**
     * <p>
     * This is a transaction start contract method to be fulfilled by the
     * extending classes which is used to delineate transactions based on some
     * context input data.
     * </p>
     * <p>
     * The implementation of this method would examine the input map to fetch
     * whatever data it needed, which will most of the time be a
     * <code>Connection</code> with the key "connection" in the map.
     * </p>
     * @param context the transactional context map.
     * @throws IllegalArgumentException if the map is not null but contains null
     *             key or value.
     * @throws PhasePersistenceException if there is any issue when committing
     *             the transaction.
     */
    protected abstract void startTransaction(Map context)
        throws PhasePersistenceException;

    /**
     * Gets the ID Generator instance.
     * @return the ID Generator instance.
     */
    protected IDGenerator getIDGenerator() {
        return idGenenerator;
    }

    /**
     * Creates the IDGenerator instance using the sequence name and the
     * generator class.
     * @param sequence the name of the id sequence.
     * @param className the name of the generator class.
     * @return the created IDGenerator.
     * @throws ConfigurationException if error occurs while creating the
     *             IdGenerator.
     */
    private static IDGenerator createIdGenerator(String sequence,
            String className) throws ConfigurationException {
        try {
            if (className == null) {
                return IDGeneratorFactory.getIDGenerator(sequence);
            }
            return IDGeneratorFactory.getIDGenerator(sequence, className);
        } catch (IDGenerationException ex) {
            throw new ConfigurationException(
                    "Error occurs while creation IdGenerator.", ex);
        } catch (ClassNotFoundException ex) {
            throw new ConfigurationException("No generator class.", ex);
        } catch (NoSuchMethodException ex) {
            throw new ConfigurationException("Missing public constructor.", ex);
        } catch (InstantiationException ex) {
            throw new ConfigurationException(
                    "Error occurs while creation IdGenerator.", ex);
        } catch (IllegalAccessException ex) {
            throw new ConfigurationException(
                    "Error occurs while creation IdGenerator.", ex);
        } catch (InvocationTargetException ex) {
            throw new ConfigurationException(
                    "Error occurs while creation IdGenerator.", ex);
        }
    }

    /**
     * Returns the configuration property from ConfigManager.
     * @param namespace the configuration namespace.
     * @param name the name of the property.
     * @param required flag indicating if the property is required.
     * @return the configuration property.
     * @throws ConfigurationException if any error was thrown from
     *             ConfigManager, or there is no require property, or the
     *             property has empty value.
     */
    private static String getProperty(String namespace, String name,
            boolean required) throws ConfigurationException {

        ConfigManager cm = ConfigManager.getInstance();
        try {
            String value = cm.getString(namespace, name);
            if (required && (value == null)) {
                throw new ConfigurationException("Missing required property: "
                        + name + " in namespace: " + namespace);
            }

            // check if the value is empty
            if ((value != null) && (value.trim().length() == 0)) {
                throw new ConfigurationException("Empty value for property: "
                        + name + " in namespace: " + namespace);
            }

            return value;
        } catch (UnknownNamespaceException ex) {
            throw new ConfigurationException("Miising config namespace: "
                    + namespace, ex);
        }
    }

    /**
     * Creates the database connection factory instance.
     * @param className the class name of the factory.
     * @param ns the factory namespace.
     * @return the DbConnectionFactory instance.
     * @throws ConfigurationException if any error occurs.
     */
    private static DBConnectionFactory createConnectionFactory(
            String className, String ns) throws ConfigurationException {

        try {
            // get the class.
            Class cl = Class.forName(className);
            if (!DBConnectionFactory.class.isAssignableFrom(cl)) {
                throw new ConfigurationException(
                        "The class is not DbConnectionFactory.");
            }

            // get the constructor and then create the instance.
            return (DBConnectionFactory) cl.getConstructor(CTOR_PARAMS)
                    .newInstance(new Object[] {ns});
        } catch (ConfigurationException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ConfigurationException(
                    "Error occurs while creating the factory.", ex);
        }
    }

}
