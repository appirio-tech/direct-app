/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project.persistence.stresstests;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.topcoder.db.connectionfactory.DBConnectionException;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.management.project.ConfigurationException;
import com.topcoder.management.project.PersistenceException;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.UnknownNamespaceException;

/**
 * <p>
 * This class is the stress helper class for Project Management Persistence 1.1.
 * </p>
 *
 * @author Hacker_QC
 * @version 1.1
 */
final class StressHelper {
    /**
     * This constant provides the <code>DataType</code> instance that can be
     * used in the query methods to specify that a <code>ResultSet</code>
     * column of a query result should be returned as value of type
     * <code>String</code> or as <code>null</code> in case the
     * <code>ResultSet</code> value was <tt>null</tt>.
     */
    static final DataType STRING_TYPE = new StringType();

    /**
     * This constant provides the <code>DataType</code> instance that can be
     * used in the query methods to specify that a <code>ResultSet</code>
     * column of a query result should be returned as value of type
     * <code>Date</code> or as <code>null</code> in case the
     * <code>ResultSet</code> value was <tt>null</tt>.
     */
    static final DataType DATE_TYPE = new DateType();

    /**
     * This class is a wrapper for type safe retrieval of values from a
     * <code>ResultSet</code>. This class has been introduced to consist the
     * behaviors of different databases and JDBC drivers so that always the
     * expected type is returned (<code>getObject(int)</code> does not
     * sufficiently do this job as the type of the value is highly
     * database-dependent (e.g. for a BLOB column the MySQL driver returns a
     * <code>byte[]</code> and the Oracle driver returns a <code>Blob</code>)).
     * <p/>This class contains a private constructor to make sure all
     * implementations of this class are declared inside <code>Helper</code>.
     * Instances are provided to users via constants declared in
     * <code>Helper</code> - so this class defines some kind of 'pseudo-enum'
     * which cannot be instantiated externally.
     * @author urtks
     * @version 1.0
     */
    abstract static class DataType {
        /**
         * Empty private constructor. By using this concept, it is assured that
         * only {@link Helper} can contain subclasses of this class and the
         * implementation classes cannot be instantiated externally.
         */
        private DataType() {
        }

        /**
         * This method retrieves the value at the given index from the given
         * resultSet as instance of the subclass-dependent type.
         * @param resultSet the result set from which to retrieve the value
         * @param index the index at which to retrieve the value
         * @return the retrieved value
         * @throws IllegalArgumentException if resultSet is <tt>null</tt>
         * @throws SQLException if error occurs while working with the given
         *             ResultSet or the index does not exist in the result set
         */
        protected abstract Object getValue(ResultSet resultSet, int index)
            throws SQLException;
    }

    /**
     * This class is a wrapper for type safe retrieval of values from a
     * <code>ResultSet</code>. The values retrieved by the
     * <code>getValue(java.sql.ResultSet, int)</code> implementation of this
     * <code>DataType</code> are assured to be of type <code>String</code>
     * or to be <tt>null</tt> in case the <code>ResultSet</code> value was
     * <tt>null</tt>.
     * @author urtks
     * @version 1.0
     */
    private static class StringType extends DataType {
        /**
         * This method retrieves the value at the given index from the given
         * resultSet as instance of the subclass-dependent type.
         * @param resultSet the result set from which to retrieve the value
         * @param index the index at which to retrieve the value
         * @return the retrieved value as <code>String</code> or
         *         <code>null</code> if the value in the
         *         <code>ResultSet</code> was <code>null</code>.
         * @throws IllegalArgumentException if resultSet is <code>null</code>
         * @throws SQLException if error occurs while working with the given
         *             ResultSet or the index does not exist in the result set
         */
        protected Object getValue(ResultSet resultSet, int index)
            throws SQLException {
            StressHelper.assertObjectNotNull(resultSet, "resultSet");

            return resultSet.getString(index);
        }
    }


    /**
     * This class is a wrapper for type safe retrieval of values from a
     * <code>ResultSet</code>. The values retrieved by the
     * <code>getValue(java.sql.ResultSet, int)</code> implementation of this
     * <code>DataType</code> are assured to be of type <code>Date</code> or
     * to be <tt>null</tt> in case the <code>ResultSet</code> value was
     * <tt>null</tt>.
     * @author urtks
     * @version 1.0
     */
    private static class DateType extends DataType {
        /**
         * This method retrieves the value at the given index from the given
         * resultSet as instance of the subclass-dependent type.
         * @param resultSet the result set from which to retrieve the value
         * @param index the index at which to retrieve the value
         * @return the retrieved value as <code>Date</code> or
         *         <code>null</code> if the value in the
         *         <code>ResultSet</code> was <code>null</code>.
         * @throws IllegalArgumentException if resultSet is <code>null</code>
         * @throws SQLException if error occurs while working with the given
         *             ResultSet or the index does not exist in the result set
         */
        protected Object getValue(ResultSet resultSet, int index)
            throws SQLException {
            StressHelper.assertObjectNotNull(resultSet, "resultSet");

            return resultSet.getTimestamp(index);
        }
    }

    /**
     * Private constructor to prevent this class be instantiated.
     */
    private StressHelper() {
    }

    /**
     * This method performs the given retrieval (i.e. non-DML) query on the
     * given connection using the given query arguments. The
     * <code>ResultSet</code> returned from the query is fetched into a List
     * of <code>Object[]</code>s and then returned. This approach assured
     * that all resources (the <code>PreparedStatement</code> and the
     * <code>ResultSet</code>) allocated in this method are also de-allocated
     * in this method. <p/> <b>Note:</b> The given connection is not closed or
     * committed in this method.
     * @param connection the connection to perform the query on
     * @param queryString the query to be performed
     * @param queryArgs the arguments to be used in the query
     * @param columnTypes the types as which to return the result set columns
     * @return the result of the query as List containing an
     *         <code>Object[]</code> for each <code>ResultSet</code> row The
     *         elements of the array are of the type represented by the
     *         <code>DataType</code> specified at the corresponding index in
     *         the given columnTypes array (or <code>null</code> in case the
     *         resultSet value was <code>null</code>)
     * @throws IllegalArgumentException if any parameter is <code>null</code>,
     *             or queryString is empty (trimmed), or columnTypes contains
     *             null, or the number of columns returned is different from
     *             that of columnTypes
     * @throws PersistenceException if any error happens
     */
    static Object[][] doQuery(Connection connection, String queryString,
            Object[] queryArgs, DataType[] columnTypes)
        throws PersistenceException {
        StressHelper.assertObjectNotNull(connection, "connection");
        StressHelper.assertStringNotNullNorEmpty(queryString, "queryString");
        StressHelper.assertArrayNotNullNorHasNull(columnTypes, "columnTypes");

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            // prepare the statement
            preparedStatement = connection.prepareStatement(queryString);
            // build the statement
            for (int i = 0; i < queryArgs.length; i++) {
                preparedStatement.setObject(i + 1, queryArgs[i]);
            }

            // execute the query and build the result into a list
            resultSet = preparedStatement.executeQuery();

            // get result list.
            List ret = new ArrayList();

            // check if the number of column is correct.
            int columnCount = resultSet.getMetaData().getColumnCount();
            if (columnTypes.length != columnCount) {
                throw new IllegalArgumentException("The column types length ["
                        + columnTypes.length
                        + "] does not match the result set column count["
                        + columnCount + "].");
            }

            while (resultSet.next()) {
                Object[] rowData = new Object[columnCount];
                for (int i = 0; i < rowData.length; i++) {
                    rowData[i] = columnTypes[i].getValue(resultSet, i + 1);
                }
                ret.add(rowData);
            }
            return (Object[][]) ret.toArray(new Object[][] {});
        } catch (SQLException e) {
            throw new PersistenceException(
                    "Error occurs while executing query [" + queryString
                            + "] using the query arguments "
                            + Arrays.asList(queryArgs).toString() + ".", e);
        } finally {
            try {
                closeResultSet(resultSet);
            } finally {
                closeStatement(preparedStatement);
            }
        }
    }

    /**
     * This method performs the given DML (query on the given connection using
     * the given query arguments. The update count returned from the query is
     * then returned. <b>Note:</b> The given connection is not closed or
     * committed in this method.
     * @param connection the connection to perform the query on
     * @param queryString the query to be performed
     * @param queryArgs the arguments to be used in the query
     * @return the number of database rows affected by the query
     * @throws IllegalArgumentException if any parameter is null or queryString
     *             is empty (trimmed)
     * @throws PersistenceException if the query fails
     */
    static int doDMLQuery(Connection connection, String queryString,
            Object[] queryArgs) throws PersistenceException {
        StressHelper.assertObjectNotNull(connection, "connection");
        StressHelper.assertStringNotNullNorEmpty(queryString, "queryString");
        StressHelper.assertObjectNotNull(queryArgs, "queryArgs");

        PreparedStatement preparedStatement = null;

        try {
            // prepare the statement.
            preparedStatement = connection.prepareStatement(queryString);

            // build the statement.
            for (int i = 0; i < queryArgs.length; i++) {
                preparedStatement.setObject(i + 1, queryArgs[i]);
            }

            // execute the statement.
            preparedStatement.execute();
            return preparedStatement.getUpdateCount();
        } catch (SQLException e) {
            throw new PersistenceException(
                    "Error occurs while executing query [" + queryString
                            + "] using the query arguments "
                            + Arrays.asList(queryArgs).toString() + ".", e);
        } finally {
            closeStatement(preparedStatement);
        }
    }

    /**
     * This method performs the given DML (query on the given prepared statement
     * using the given query arguments. The update count returned from the query
     * is then returned. <b>Note:</b> The given statement is not closed in this
     * method.
     * @param statement the prepared statement to perform the query on
     * @param queryArgs the arguments to be used in the query
     * @return the number of database rows affected by the query
     * @throws IllegalArgumentException if any parameter is null
     * @throws PersistenceException if the query fails
     */
    static int doDMLQuery(PreparedStatement statement, Object[] queryArgs)
        throws PersistenceException {
        StressHelper.assertObjectNotNull(statement, "statement");
        StressHelper.assertObjectNotNull(queryArgs, "queryArgs");

        try {
            statement.clearParameters();

            // build the statement.
            for (int i = 0; i < queryArgs.length; i++) {
                statement.setObject(i + 1, queryArgs[i]);
            }

            // execute the statement.
            statement.execute();
            return statement.getUpdateCount();
        } catch (SQLException e) {
            throw new PersistenceException(
                    "Error occurs while executing prepared statement "
                            + "using the query arguments "
                            + Arrays.asList(queryArgs).toString() + ".", e);
        }
    }

    /**
     * Close the prepared statement.
     * @param ps the prepared statement to close
     * @throws PersistenceException error occurs when closing the prepared
     *             statement
     */
    static void closeStatement(PreparedStatement ps)
        throws PersistenceException {
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                throw new PersistenceException(
                        "Error occurs when closing the prepared statement.", e);
            }
        }
    }

    /**
     * Close the result set.
     * @param rs the result set to close
     * @throws PersistenceException error occurs when closing the result set.
     */
    static void closeResultSet(ResultSet rs) throws PersistenceException {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                throw new PersistenceException(
                        "Error occurs when closing the result set.", e);
            }
        }
    }

    /**
     * Check if the given object is null.
     * @param obj the given object to check
     * @param name the name to identify the object.
     * @throws IllegalArgumentException if the given object is null
     */
    static void assertObjectNotNull(Object obj, String name) {
        if (obj == null) {
            throw new IllegalArgumentException(name + " should not be null.");
        }
    }

    /**
     * Check if the given array is null or contains null.
     * @param array the given array to check
     * @param name the name to identify the array.
     * @throws IllegalArgumentException if the given array is null or contains
     *             null.
     */
    static void assertArrayNotNullNorHasNull(Object[] array, String name) {
        assertObjectNotNull(array, name);

        for (int i = 0; i < array.length; ++i) {
            if (array[i] == null) {
                throw new IllegalArgumentException(name
                        + " should not contain null.");
            }
        }
    }

    /**
     * Check if the given string is null or empty (trimmed).
     * @param str the given string to check
     * @param name the name to identify the string.
     * @throws IllegalArgumentException if the given string is null or empty
     *             (trimmed).
     */
    static void assertStringNotNullNorEmpty(String str, String name) {
        assertObjectNotNull(str, name);

        if (str.trim().length() == 0) {
            throw new IllegalArgumentException(name
                    + " should not be empty (trimmed).");
        }
    }


    /**
     * <p>Gets the parameter value from configuration.</p>
     * @param cm the ConfigManager instance
     * @param namespace configuration namespace
     * @param name the parameter name
     * @param required true, if the parameter is require; false, if the
     *            parameter is optional
     * @return A String that represents the parameter value
     * @throws IllegalArgumentException if any parameter is null, or namespace
     *             or name is empty (trimmed)
     * @throws ConfigurationException if the namespace does not exist, or the
     *             value is not specified when required is true, or the value is
     *             empty (trimmed) when not null.
     */
    static String getConfigurationParameterValue(ConfigManager cm,
            String namespace, String name, boolean required)
        throws ConfigurationException {
        StressHelper.assertObjectNotNull(cm, "cm");
        StressHelper.assertStringNotNullNorEmpty(namespace, "namespace");
        StressHelper.assertStringNotNullNorEmpty(name, "name");

        String value;

        try {
            value = cm.getString(namespace, name);
        } catch (UnknownNamespaceException e) {
            throw new ConfigurationException("Configuration namespace ["
                    + namespace + "] does not exist.", e);
        }

        if (value == null) {
            if (required) {
                throw new ConfigurationException("Configuration parameter ["
                        + name + "] under namespace [" + namespace
                        + "] is not specified.");
            }
        } else if (value.trim().length() == 0) {
            throw new ConfigurationException("Configuration parameter [" + name
                    + "] under namespace [" + namespace
                    + "] is empty (trimmed).");
        }

        return value;
    }

    /**
     * <p>
     * Creates the <code>Connection</code> instance by the given
     * <code>DBConnectionFactory</code> and possible connection name.
     * </p>
     * <p>
     * If the connection name is null, the default connection configuration is
     * used, otherwise, the specified connection configuration is used.
     * </p>
     * @param factory the <code>DBConnectionFactory</code> instance used to
     *            create the connection.
     * @param connectionName the <code>null</code> possible connection name
     *            used to specify the connection configuration in connection
     *            factory.
     * @return the <code>Connection</code> instance created by the factory and
     *         name.
     * @throws PersistenceException if fails to create the connection.
     * @since 1.1
     */
    static Connection createConnection(DBConnectionFactory factory,
            String connectionName) throws PersistenceException {
        try {

            return connectionName == null ? factory
                    .createConnection() : factory
                    .createConnection(connectionName);

        } catch (DBConnectionException e) {
            throw new PersistenceException("Error occurs when getting "
                    + (connectionName == null ? "the default connection."
                            : ("the connection '" + connectionName + "'.")), e);
        }
    }
    
    /**
     * Loads all configurations from files with given file name.
     *
     * @param file the file to loaded from.
     * @throws Exception when error occurs
     */
    public static void loadConfig(String file) throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        cm.add(file);
    }

    /**
     * Unloads all configurations from files.
     *
     * @throws Exception when error occurs
     */
    public static void unloadConfig() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();

        for (Iterator it = cm.getAllNamespaces(); it.hasNext();) {
            cm.removeNamespace((String) it.next());
        }
    }
}
