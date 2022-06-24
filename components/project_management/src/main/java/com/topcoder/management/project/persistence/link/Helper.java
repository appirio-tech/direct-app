/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project.persistence.link;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.topcoder.db.connectionfactory.DBConnectionException;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.management.project.ConfigurationException;
import com.topcoder.management.project.PersistenceException;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.UnknownNamespaceException;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;

/**
 * <p>
 * Helper class. This is from project persistence component internal Helper class. It is copied here for reusing. The
 * component might be refactored so that it could be used cross the board. We keep all version information here.
 * </p>
 *
 * @author urtks, fuyun
 * @author TCSDEVELOPER
 * @version 1.1
 * @since 1.0
 */
class Helper {
    /**
     * This constant provides the <code>DataType</code> instance that can be used in the query methods to specify that
     * a <code>ResultSet</code> column of a query result should be returned as value of type <code>String</code> or as
     * <code>null</code> in case the <code>ResultSet</code> value was <tt>null</tt>.
     */
    static final DataType STRING_TYPE = new StringType();

    /**
     * This constant provides the <code>DataType</code> instance that can be used in the query methods to specify that
     * a <code>ResultSet</code> column of a query result should be returned as value of type <code>Long</code> or as
     * <code>null</code> in case the <code>ResultSet</code> value was <tt>null</tt>.
     */
    static final DataType LONG_TYPE = new LongType();

    /**
     * This constant provides the <code>DataType</code> instance that can be used in the query methods to specify that
     * a <code>ResultSet</code> column of a query result should be returned as value of type <code>Date</code> or as
     * <code>null</code> in case the <code>ResultSet</code> value was <tt>null</tt>.
     */
    static final DataType DATE_TYPE = new DateType();

    /**
     * This class is a wrapper for type safe retrieval of values from a <code>ResultSet</code>. This class has been
     * introduced to consist the behaviors of different databases and JDBC drivers so that always the expected type is
     * returned (<code>getObject(int)</code> does not sufficiently do this job as the type of the value is highly
     * database-dependent (e.g. for a BLOB column the MySQL driver returns a <code>byte[]</code> and the Oracle driver
     * returns a <code>Blob</code>)).
     * <p/>
     * This class contains a private constructor to make sure all implementations of this class are declared inside
     * <code>Helper</code>. Instances are provided to users via constants declared in <code>Helper</code> - so this
     * class defines some kind of 'pseudo-enum' which cannot be instantiated externally.
     *
     * @author urtks
     * @version 1.0
     */
    abstract static class DataType {
        /**
         * Empty private constructor. By using this concept, it is assured that only {@link Helper} can contain
         * subclasses of this class and the implementation classes cannot be instantiated externally.
         */
        private DataType() {
        }

        /**
         * This method retrieves the value at the given index from the given resultSet as instance of the
         * subclass-dependent type.
         *
         * @param resultSet the result set from which to retrieve the value
         * @param index the index at which to retrieve the value
         * @return the retrieved value
         * @throws IllegalArgumentException if resultSet is <tt>null</tt>
         * @throws SQLException if error occurs while working with the given ResultSet or the index does not exist in
         *             the result set
         */
        protected abstract Object getValue(ResultSet resultSet, int index) throws SQLException;
    }

    /**
     * This class is a wrapper for type safe retrieval of values from a <code>ResultSet</code>. The values retrieved
     * by the <code>getValue(java.sql.ResultSet, int)</code> implementation of this <code>DataType</code> are assured
     * to be of type <code>String</code> or to be <tt>null</tt> in case the <code>ResultSet</code> value was
     * <tt>null</tt>.
     *
     * @author urtks
     * @version 1.0
     */
    private static class StringType extends DataType {
        /**
         * This method retrieves the value at the given index from the given resultSet as instance of the
         * subclass-dependent type.
         *
         * @param resultSet the result set from which to retrieve the value
         * @param index the index at which to retrieve the value
         * @return the retrieved value as <code>String</code> or <code>null</code> if the value in the
         *         <code>ResultSet</code> was <code>null</code>.
         * @throws IllegalArgumentException if resultSet is <code>null</code>
         * @throws SQLException if error occurs while working with the given ResultSet or the index does not exist in
         *             the result set
         */
        protected Object getValue(ResultSet resultSet, int index) throws SQLException {
            Helper.assertObjectNotNull(resultSet, "resultSet");

            return resultSet.getString(index);
        }
    }

    /**
     * This class is a wrapper for type safe retrieval of values from a <code>ResultSet</code>. The values retrieved
     * by the <code>getValue(java.sql.ResultSet, int)</code> implementation of this <code>DataType</code> are assured
     * to be of type <code>Long</code> or to be <tt>null</tt> in case the <code>ResultSet</code> value was
     * <tt>null</tt>.
     *
     * @author urtks
     * @version 1.0
     */
    private static class LongType extends DataType {
        /**
         * This method retrieves the value at the given index from the given resultSet as instance of the
         * subclass-dependent type.
         *
         * @param resultSet the result set from which to retrieve the value
         * @param index the index at which to retrieve the value
         * @return the retrieved value as <code>Long</code> or <code>null</code> if the value in the
         *         <code>ResultSet</code> was <code>null</code>.
         * @throws IllegalArgumentException if resultSet is <code>null</code>
         * @throws SQLException if error occurs while working with the given ResultSet or the index does not exist in
         *             the result set
         */
        protected Object getValue(ResultSet resultSet, int index) throws SQLException {
            Helper.assertObjectNotNull(resultSet, "resultSet");

            long aLong = resultSet.getLong(index);
            return resultSet.wasNull() ? null : new Long(aLong);
        }
    }

    /**
     * This class is a wrapper for type safe retrieval of values from a <code>ResultSet</code>. The values retrieved
     * by the <code>getValue(java.sql.ResultSet, int)</code> implementation of this <code>DataType</code> are assured
     * to be of type <code>Date</code> or to be <tt>null</tt> in case the <code>ResultSet</code> value was
     * <tt>null</tt>.
     *
     * @author urtks
     * @version 1.0
     */
    private static class DateType extends DataType {
        /**
         * This method retrieves the value at the given index from the given resultSet as instance of the
         * subclass-dependent type.
         *
         * @param resultSet the result set from which to retrieve the value
         * @param index the index at which to retrieve the value
         * @return the retrieved value as <code>Date</code> or <code>null</code> if the value in the
         *         <code>ResultSet</code> was <code>null</code>.
         * @throws IllegalArgumentException if resultSet is <code>null</code>
         * @throws SQLException if error occurs while working with the given ResultSet or the index does not exist in
         *             the result set
         */
        protected Object getValue(ResultSet resultSet, int index) throws SQLException {
            Helper.assertObjectNotNull(resultSet, "resultSet");

            return resultSet.getTimestamp(index);
        }
    }

    /**
     * Private constructor to prevent this class be instantiated.
     */
    private Helper() {
    }

    /**
     * This method performs the given retrieval (i.e. non-DML) query on the given connection using the given query
     * arguments. The <code>ResultSet</code> returned from the query is expected to contain ONE row containing ONE
     * value, which is then returned. This approach assured that all resources (<code>PreparedStatement</code> and the
     * <code>ResultSet</code>) allocated in this method are also de-allocated in this method. <b>Note:</b> The given
     * connection is not closed or committed in this method.
     *
     * @param connection the connection to perform the query on
     * @param queryString the query to be performed
     * @param queryArgs the arguments to be used in the query
     * @param columnType the type as which to return the value, use one of the values STRING_TYPE, DATE_TYPE or
     *            LONG_TYPE or FLOAT_TYPE here
     * @return the value returned by the query as value of the type represented by the given columnType or
     *         <code>null</code> if the <code>ResultSet</code> value was <code>null</code>
     * @throws IllegalArgumentException if any parameter is <code>null</code>, or queryString is empty (trimmed), or
     *             the given query did return multiple (or zero) rows or columns
     * @throws PersistenceException if the query fails
     */
    static Object doSingleValueQuery(Connection connection, String queryString, Object[] queryArgs,
        DataType columnType) throws PersistenceException {
        Helper.assertObjectNotNull(connection, "connection");
        Helper.assertStringNotNullNorEmpty(queryString, "queryString");
        Helper.assertObjectNotNull(columnType, "columnType");

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            // build the statement
            preparedStatement = connection.prepareStatement(queryString);
            // build the statement
            for (int i = 0; i < queryArgs.length; i++) {
                preparedStatement.setObject(i + 1, queryArgs[i]);
            }

            // do the query
            resultSet = preparedStatement.executeQuery();

            int columnCount = resultSet.getMetaData().getColumnCount();
            if (columnCount == 1 && resultSet.next()) {
                return columnType.getValue(resultSet, 1);
            } else {
                throw new IllegalArgumentException("The given query [" + queryString
                    + "] did not return ONE result row containing " + "ONE value using the query arguments ["
                    + Arrays.asList(queryArgs).toString() + "].");
            }
        } catch (SQLException e) {
            throw new PersistenceException("Error occurs while executing query [" + queryString
                + "] using the query arguments " + Arrays.asList(queryArgs).toString() + ".", e);
        } finally {
            try {
                closeResultSet(resultSet);
            } finally {
                closeStatement(preparedStatement);
            }
        }
    }

    /**
     * This method performs the given retrieval (i.e. non-DML) query on the given connection using the given query
     * arguments. The <code>ResultSet</code> returned from the query is fetched into a List of <code>Object[]</code>s
     * and then returned. This approach assured that all resources (the <code>PreparedStatement</code> and the
     * <code>ResultSet</code>) allocated in this method are also de-allocated in this method.
     * <p/>
     * <b>Note:</b> The given connection is not closed or committed in this method.
     *
     * @param connection the connection to perform the query on
     * @param queryString the query to be performed
     * @param queryArgs the arguments to be used in the query
     * @param columnTypes the types as which to return the result set columns
     * @return the result of the query as List containing an <code>Object[]</code> for each <code>ResultSet</code> row
     *         The elements of the array are of the type represented by the <code>DataType</code> specified at the
     *         corresponding index in the given columnTypes array (or <code>null</code> in case the resultSet value
     *         was <code>null</code>)
     * @throws IllegalArgumentException if any parameter is <code>null</code>, or queryString is empty (trimmed), or
     *             columnTypes contains null, or the number of columns returned is different from that of columnTypes
     * @throws PersistenceException if any error happens
     */
    @SuppressWarnings("unchecked")
    static Object[][] doQuery(Connection connection, String queryString, Object[] queryArgs, DataType[] columnTypes)
        throws PersistenceException {
        Helper.assertObjectNotNull(connection, "connection");
        Helper.assertStringNotNullNorEmpty(queryString, "queryString");
        Helper.assertArrayNotNullNorHasNull(columnTypes, "columnTypes");

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
                throw new IllegalArgumentException("The column types length [" + columnTypes.length
                    + "] does not match the result set column count[" + columnCount + "].");
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
            throw new PersistenceException("Error occurs while executing query [" + queryString
                + "] using the query arguments " + Arrays.asList(queryArgs).toString() + ".", e);
        } finally {
            try {
                closeResultSet(resultSet);
            } finally {
                closeStatement(preparedStatement);
            }
        }
    }

    /**
     * This method performs the given DML (query on the given connection using the given query arguments. The update
     * count returned from the query is then returned. <b>Note:</b> The given connection is not closed or committed in
     * this method.
     *
     * @param connection the connection to perform the query on
     * @param queryString the query to be performed
     * @param queryArgs the arguments to be used in the query
     * @return the number of database rows affected by the query
     * @throws IllegalArgumentException if any parameter is null or queryString is empty (trimmed)
     * @throws PersistenceException if the query fails
     */
    static int doDMLQuery(Connection connection, String queryString, Object[] queryArgs) throws PersistenceException {
        Helper.assertObjectNotNull(connection, "connection");
        Helper.assertStringNotNullNorEmpty(queryString, "queryString");
        Helper.assertObjectNotNull(queryArgs, "queryArgs");

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
            throw new PersistenceException("Error occurs while executing query [" + queryString
                + "] using the query arguments " + Arrays.asList(queryArgs).toString() + ".", e);
        } finally {
            closeStatement(preparedStatement);
        }
    }

    /**
     * This method performs the given DML (query on the given prepared statement using the given query arguments. The
     * update count returned from the query is then returned. <b>Note:</b> The given statement is not closed in this
     * method.
     *
     * @param statement the prepared statement to perform the query on
     * @param queryArgs the arguments to be used in the query
     * @return the number of database rows affected by the query
     * @throws IllegalArgumentException if any parameter is null
     * @throws PersistenceException if the query fails
     */
    static int doDMLQuery(PreparedStatement statement, Object[] queryArgs) throws PersistenceException {
        Helper.assertObjectNotNull(statement, "statement");
        Helper.assertObjectNotNull(queryArgs, "queryArgs");

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
            throw new PersistenceException("Error occurs while executing prepared statement "
                + "using the query arguments " + Arrays.asList(queryArgs).toString() + ".", e);
        }
    }

    /**
     * Close the connection.
     *
     * @param conn the connection to close
     * @throws PersistenceException if error occurs when closing the connection
     */
    static void closeConnection(Connection conn) throws PersistenceException {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new PersistenceException("Error occurs when closing the connection.", e);
            }
        }
    }

    /**
     * Close the prepared statement.
     *
     * @param ps the prepared statement to close
     * @throws PersistenceException error occurs when closing the prepared statement
     */
    static void closeStatement(PreparedStatement ps) throws PersistenceException {
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                throw new PersistenceException("Error occurs when closing the prepared statement.", e);
            }
        }
    }

    /**
     * Close the result set.
     *
     * @param rs the result set to close
     * @throws PersistenceException error occurs when closing the result set.
     */
    static void closeResultSet(ResultSet rs) throws PersistenceException {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                throw new PersistenceException("Error occurs when closing the result set.", e);
            }
        }
    }

    /**
     * Do commit for transaction.
     *
     * @param conn the connection
     * @throws PersistenceException error occurs when doing commit
     */
    static void commitTransaction(Connection conn) throws PersistenceException {
        if (conn != null) {
            try {
                conn.commit();
            } catch (SQLException e) {
                throw new PersistenceException("Error occurs when doing commit.", e);
            }
        }
    }

    /**
     * Do rollback for transaction.
     *
     * @param conn the connection
     * @throws PersistenceException error occurs when doing rollback
     */
    static void rollBackTransaction(Connection conn) throws PersistenceException {
        if (conn != null) {
            try {
                conn.rollback();
            } catch (SQLException e) {
                throw new PersistenceException("Error occurs when doing rollback.", e);
            }
        }
    }

    /**
     * Check whether an entity exists or not in the specified table.
     *
     * @param tableName the table name
     * @param columnName the column name
     * @param id the id to check
     * @param conn the db connection
     * @return true if it exists, otherwise false
     * @throws IllegalArgumentException if tableName or columnName is null or empty, or id is not positive, or conn is
     *             null
     * @throws PersistenceException if error happens during querying
     */
    static boolean checkEntityExists(String tableName, String columnName, long id, Connection conn)
        throws PersistenceException {
        Helper.assertStringNotNullNorEmpty(tableName, "tableName");
        Helper.assertStringNotNullNorEmpty(columnName, "columnName");
        Helper.assertLongPositive(id, "id");
        Helper.assertObjectNotNull(conn, "conn");

        Long entryCount = (Long) Helper.doSingleValueQuery(conn, "SELECT COUNT(*) FROM " + tableName + " WHERE "
            + columnName + "=?", new Object[] {new Long(id)}, Helper.LONG_TYPE);
        return entryCount.longValue() > 0;
    }

    /**
     * Check if the given object is null.
     *
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
     *
     * @param array the given array to check
     * @param name the name to identify the array.
     * @throws IllegalArgumentException if the given array is null or contains null.
     */
    static void assertArrayNotNullNorHasNull(Object[] array, String name) {
        assertObjectNotNull(array, name);

        for (int i = 0; i < array.length; ++i) {
            if (array[i] == null) {
                throw new IllegalArgumentException(name + " should not contain null.");
            }
        }
    }

    /**
     * Check if the given string is null or empty (trimmed).
     *
     * @param str the given string to check
     * @param name the name to identify the string.
     * @throws IllegalArgumentException if the given string is null or empty (trimmed).
     */
    static void assertStringNotNullNorEmpty(String str, String name) {
        assertObjectNotNull(str, name);

        if (str.trim().length() == 0) {
            throw new IllegalArgumentException(name + " should not be empty (trimmed).");
        }
    }

    /**
     * Check if the given long value is positive.
     *
     * @param val the given long value to check.
     * @param name the name to identify the long value.
     * @throws IllegalArgumentException if the given long value is negative or zero.
     */
    static void assertLongPositive(long val, String name) {
        if (val <= 0) {
            throw new IllegalArgumentException(name + " [" + val + "] should be positive.");
        }
    }

    /**
     * <p>
     * Gets the parameter value from configuration.
     * </p>
     *
     * @param cm the ConfigManager instance
     * @param namespace configuration namespace
     * @param name the parameter name
     * @param required true, if the parameter is require; false, if the parameter is optional
     * @param logger the logger
     * @return A String that represents the parameter value
     * @throws IllegalArgumentException if any parameter is null, or namespace or name is empty (trimmed)
     * @throws ConfigurationException if the namespace does not exist, or the value is not specified when required is
     *             true, or the value is empty (trimmed) when not null.
     */
    static String getConfigurationParameterValue(ConfigManager cm, String namespace, String name, boolean required,
        Log logger) throws ConfigurationException {
        Helper.assertObjectNotNull(cm, "cm");
        Helper.assertStringNotNullNorEmpty(namespace, "namespace");
        Helper.assertStringNotNullNorEmpty(name, "name");

        String value;

        try {
            value = cm.getString(namespace, name);
        } catch (UnknownNamespaceException e) {
            logger.log(Level.FATAL, "Configuration namespace [" + namespace + "] does not exist.");
            throw new ConfigurationException("Configuration namespace [" + namespace + "] does not exist.", e);
        }

        if (value == null) {
            if (required) {
                logger.log(Level.FATAL, "Configuration parameter [" + name + "] under namespace [" + namespace
                    + "] is not specified.");
                throw new ConfigurationException("Configuration parameter [" + name + "] under namespace ["
                    + namespace + "] is not specified.");
            }
        } else if (value.trim().length() == 0) {
            logger.log(Level.FATAL, "Configuration parameter [" + name + "] under namespace [" + namespace
                + "] is empty (trimmed).");
            throw new ConfigurationException("Configuration parameter [" + name + "] under namespace [" + namespace
                + "] is empty (trimmed).");
        }

        logger.log(Level.INFO, "Read propery[" + name + "] which is " + (required ? " required " : " optional ")
            + " with value[" + value + "] from namespace [" + namespace + "].");

        return value;
    }

    /**
     * <p>
     * Creates the <code>Connection</code> instance by the given <code>DBConnectionFactory</code> and possible
     * connection name.
     * </p>
     * <p>
     * If the connection name is null, the default connection configuration is used, otherwise, the specified
     * connection configuration is used.
     * </p>
     *
     * @param factory the <code>DBConnectionFactory</code> instance used to create the connection.
     * @param connectionName the <code>null</code> possible connection name used to specify the connection
     *            configuration in connection factory.
     * @return the <code>Connection</code> instance created by the factory and name.
     * @throws PersistenceException if fails to create the connection.
     * @since 1.1
     */
    static Connection createConnection(DBConnectionFactory factory, String connectionName)
        throws PersistenceException {
        try {

            return connectionName == null ? factory.createConnection() : factory.createConnection(connectionName);

        } catch (DBConnectionException e) {
            throw new PersistenceException(
                "Error occurs when getting "
                    + (connectionName == null ? "the default connection."
                        : ("the connection '" + connectionName + "'.")), e);
        }
    }
}
