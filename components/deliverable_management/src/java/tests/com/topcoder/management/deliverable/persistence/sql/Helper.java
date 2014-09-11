/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */



package com.topcoder.management.deliverable.persistence.sql;

import com.topcoder.db.connectionfactory.DBConnectionException;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.management.deliverable.AuditedDeliverableStructure;
import com.topcoder.management.deliverable.NamedDeliverableStructure;
import com.topcoder.management.deliverable.persistence.PersistenceException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Helper class for the package
 * com.topcoder.management.deliverable.persistence.sql., including the methods
 * to validate arguments and the methods to access database.
 * @author urtks
 * @version 1.0
 */
class Helper {
    /**
     * This constant provides the DataType instance that can be used in the
     * query methods to specify that a ResultSet column of a query result should
     * be returned as value of type String or as null in case the ResultSet
     * value was null, and to specify that PreparedStatement#setString() should
     * be used for a parameter.
     */
    static final DataType STRING_TYPE = new StringType();

    /**
     * This constant provides the DataType instance that can be used in the
     * query methods to specify that a ResultSet column of a query result should
     * be returned as value of type Long or as null in case the ResultSet value
     * was null, and to specify that PreparedStatement#setLong() should be used
     * for a parameter.
     */
    static final DataType LONG_TYPE = new LongType();

    /**
     * This constant provides the DataType instance that can be used in the
     * query methods to specify that a ResultSet column of a query result should
     * be returned as value of type Date or as null in case the ResultSet value
     * was null, and to specify that PreparedStatement#setTimestamp() should be
     * used for a parameter.
     */
    static final DataType DATE_TYPE = new DateType();

    /**
     * This constant provides the DataType instance that can be used in the
     * query methods to specify that a ResultSet column of a query result should
     * be returned as value of type Boolean or as null in case the ResultSet
     * value was null, and to specify that PreparedStatement#setBoolean() should
     * be used for a parameter.
     */
    static final DataType BOOLEAN_TYPE = new BooleanType();

    /**
     * Private constructor to prevent this class be instantiated.
     */
    private Helper() {
    }

    /**
     * <p>
     * This method performs the given retrieval (i.e. non-DML) query on the
     * given connection using the given query arguments. The ResultSet returned
     * from the query is fetched into a Object[][] of Object[]s and then
     * returned. This approach assured that all resources (the PreparedStatement
     * and the ResultSet) allocated in this method are also de-allocated in this
     * method.
     * </p>
     * <p>
     * Note: The given connection is not closed or committed in this method.
     * </p>
     * @param connection
     *            the connection to perform the query on
     * @param queryString
     *            the query to be performed
     * @param argumentTypes
     *            the types of each object in queryArgs, use one of the values
     *            STRING_TYPE, LONG_TYPE or BOOLEAN_TYPE here
     * @param queryArgs
     *            the arguments to be used in the query
     * @param columnTypes
     *            the types as which to return the result set columns
     * @return the result of the query as Object[][] containing an Object[] for
     *         each ResultSet row The elements of the array are of the type
     *         represented by the DataType specified at the corresponding index
     *         in the given columnTypes array (or null in case the resultSet
     *         value was null)
     * @throws IllegalArgumentException
     *             if connection is null
     * @throws IllegalArgumentException
     *             if queryString is null or empty (trimmed)
     * @throws IllegalArgumentException
     *             if argumentTypes is null or contains null
     * @throws IllegalArgumentException
     *             if queryArgs is null or the length of it is different from
     *             that of argumentTypes
     * @throws IllegalArgumentException
     *             if columnTypes is null or contains null or the the number of
     *             columns returned is different from that of columnTypes
     * @throws PersistenceException
     *             if any error happens
     */
    static Object[][] doQuery(Connection connection, String queryString, DataType[] argumentTypes, Object[] queryArgs,
                              DataType[] columnTypes)
            throws PersistenceException {
        Helper.assertObjectNotNull(connection, "connection");
        Helper.assertStringNotNullNorEmpty(queryString, "queryString");
        Helper.assertArrayNotNullNorHasNull(argumentTypes, "argumentTypes");
        Helper.assertObjectNotNull(queryArgs, "queryArgs");
        Helper.assertArrayLengthEqual(queryArgs, "queryArgs", argumentTypes, "argumentTypes");
        Helper.assertArrayNotNullNorHasNull(columnTypes, "columnTypes");

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            // prepare the statement
            preparedStatement = connection.prepareStatement(queryString);

            // build the statement
            for (int i = 0; i < queryArgs.length; i++) {
                argumentTypes[i].setValue(preparedStatement, i + 1, queryArgs[i]);
            }

            // execute the query and build the result into a list
            resultSet = preparedStatement.executeQuery();

            // get result list.
            List<Object[]> ret = new ArrayList<Object[]>();

            // check if the number of column is correct.
            int columnCount = resultSet.getMetaData().getColumnCount();

            if (columnTypes.length != columnCount) {
                throw new IllegalArgumentException("The column types length [" + columnTypes.length
                                                   + "] does not match the result set column count[" + columnCount
                                                   + "].");
            }

            while (resultSet.next()) {
                Object[] rowData = new Object[columnCount];

                for (int i = 0; i < rowData.length; i++) {
                    rowData[i] = columnTypes[i].getValue(resultSet, i + 1);
                }

                ret.add(rowData);
            }

            return ret.toArray(new Object[][] {});
        } catch (SQLException e) {
            throw new PersistenceException("Error occurs while executing query [" + queryString
                                           + "] using the query arguments " + Arrays.asList(queryArgs).toString()
                                           + ".", e);
        } finally {
            try {
                closeResultSet(resultSet);
            } finally {
                closeStatement(preparedStatement);
            }
        }
    }

    /**
     * <p>
     * This method performs the given retrieval (i.e. non-DML) query on the
     * given connection using the given query arguments. The ResultSet returned
     * from the query is fetched into a Object[][] of Object[]s and then
     * returned. This approach assured that all resources (the PreparedStatement
     * and the ResultSet) allocated in this method are also de-allocated in this
     * method.
     * </p>
     * <p>
     * Note: The given connection is not closed or committed in this method.
     * </p>
     * @param connectionFactory
     *            the connection factory
     * @param connectionName
     *            the connection name
     * @param queryString
     *            the query to be performed
     * @param argumentTypes
     *            the types of each object in queryArgs, use one of the values
     *            STRING_TYPE, LONG_TYPE or BOOLEAN_TYPE here
     * @param queryArgs
     *            the arguments to be used in the query
     * @param columnTypes
     *            the types as which to return the result set columns
     * @return the result of the query as Object[][] containing an Object[] for
     *         each ResultSet row The elements of the array are of the type
     *         represented by the DataType specified at the corresponding index
     *         in the given columnTypes array (or null in case the resultSet
     *         value was null)
     * @throws IllegalArgumentException
     *             if connectionFactory is null
     * @throws IllegalArgumentException
     *             if queryString is null or empty (trimmed)
     * @throws IllegalArgumentException
     *             if argumentTypes is null or contains null
     * @throws IllegalArgumentException
     *             if queryArgs is null or the length of it is different from
     *             that of argumentTypes
     * @throws IllegalArgumentException
     *             if columnTypes is null or contains null or the the number of
     *             columns returned is different from that of columnTypes
     * @throws PersistenceException
     *             if any error happens
     */
    static Object[][] doQuery(DBConnectionFactory connectionFactory, String connectionName, String queryString,
                              DataType[] argumentTypes, Object[] queryArgs, DataType[] columnTypes)
            throws PersistenceException {
        Connection conn = null;

        try {
            // create the connection with auto-commit mode enabled
            conn = createConnection(connectionFactory, connectionName, true, true);

            return doQuery(conn, queryString, argumentTypes, queryArgs, columnTypes);
        } finally {
            Helper.closeConnection(conn);
        }
    }

    /**
     * <p>
     * This method performs the given DML (query on the given connection using
     * the given query arguments and their types. The update count returned from
     * the query is then returned.
     * </p>
     * <p>
     * Note: The given connection is not closed or committed in this method.
     * </p>
     * @param connection
     *            the connection to perform the query on
     * @param queryString
     *            the query to be performed
     * @param argumentTypes
     *            the types of each object in queryArgs, use one of the values
     *            STRING_TYPE, LONG_TYPE or BOOLEAN_TYPE here
     * @param queryArgs
     *            the arguments to be used in the query
     * @return the number of database rows affected by the query
     * @throws IllegalArgumentException
     *             if connection is null
     * @throws IllegalArgumentException
     *             if queryString is null or empty (trimmed)
     * @throws IllegalArgumentException
     *             if argumentTypes is null or contains null
     * @throws IllegalArgumentException
     *             if queryArgs is null or length of it is different from that
     *             of argumentTypes
     * @throws PersistenceException
     *             if the query fails
     */
    static int doDMLQuery(Connection connection, String queryString, DataType[] argumentTypes, Object[] queryArgs)
            throws PersistenceException {
        Helper.assertObjectNotNull(connection, "connection");
        Helper.assertStringNotNullNorEmpty(queryString, "queryString");
        Helper.assertArrayNotNullNorHasNull(argumentTypes, "argumentTypes");
        Helper.assertArrayLengthEqual(queryArgs, "queryArgs", argumentTypes, "argumentTypes");
        Helper.assertObjectNotNull(queryArgs, "queryArgs");

        PreparedStatement preparedStatement = null;

        try {
            // prepare the statement.
            preparedStatement = connection.prepareStatement(queryString);

            // build the statement.
            for (int i = 0; i < queryArgs.length; i++) {
                argumentTypes[i].setValue(preparedStatement, i + 1, queryArgs[i]);
            }

            // execute the statement.
            preparedStatement.execute();

            return preparedStatement.getUpdateCount();
        } catch (SQLException e) {
            throw new PersistenceException("Error occurs while executing query [" + queryString
                                           + "] using the query arguments " + Arrays.asList(queryArgs).toString()
                                           + ".", e);
        } finally {
            closeStatement(preparedStatement);
        }
    }

    /**
     * <p>
     * This method performs the given DML (query on the given connection using
     * the given query arguments and their types. The update count returned from
     * the query is then returned.
     * </p>
     * @param connectionFactory
     *            the connection factory
     * @param connectionName
     *            the connection name
     * @param queryString
     *            the query to be performed
     * @param argumentTypes
     *            the types of each object in queryArgs, use one of the values
     *            STRING_TYPE, LONG_TYPE or BOOLEAN_TYPE here
     * @param queryArgs
     *            the arguments to be used in the query
     * @return the number of database rows affected by the query
     * @throws IllegalArgumentException
     *             if connectionFactory is null
     * @throws IllegalArgumentException
     *             if queryString is null or empty (trimmed)
     * @throws IllegalArgumentException
     *             if argumentTypes is null or contains null
     * @throws IllegalArgumentException
     *             if queryArgs is null or length of it is different from that
     *             of argumentTypes
     * @throws PersistenceException
     *             if the query fails
     */
    static int doDMLQuery(DBConnectionFactory connectionFactory, String connectionName, String queryString,
                          DataType[] argumentTypes, Object[] queryArgs)
            throws PersistenceException {
        Connection conn = null;

        try {
            // create the connection with auto-commit mode enabled
            conn = createConnection(connectionFactory, connectionName, true, false);

            return doDMLQuery(conn, queryString, argumentTypes, queryArgs);
        } finally {
            Helper.closeConnection(conn);
        }
    }

    /**
     * Close the connection if conn is not null.
     * @param conn
     *            the connection to close
     * @throws PersistenceException
     *             if error occurs when closing the connection
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
     * Close the prepared statement if ps is not null.
     * @param ps
     *            the prepared statement to close
     * @throws PersistenceException
     *             error occurs when closing the prepared statement
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
     * Close the result set if rs is not null.
     * @param rs
     *            the result set to close
     * @throws PersistenceException
     *             error occurs when closing the result set.
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
     * Do commit for transaction if conn is not null.
     * @param conn
     *            the connection
     * @throws PersistenceException
     *             error occurs when doing commit
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
     * Do rollback for transaction if conn is not null.
     * @param conn
     *            the connection
     * @throws PersistenceException
     *             error occurs when doing rollback
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
     * Create a connection from the connectionFactory and connectionName, with
     * options to enable or disable the auto-commit mode and to enable or
     * disable the read-only mode.
     * @param connectionFactory
     *            the connection factory
     * @param connectionName
     *            the connection name
     * @param autoCommit
     *            true to enable auto-commit mode; false to disable it
     * @param readOnly
     *            true to enable read-only mode; false to disable it
     * @return the connection created
     * @throws PersistenceException
     *             if error happens when creating the connection
     */
    static Connection createConnection(DBConnectionFactory connectionFactory, String connectionName,
                                       boolean autoCommit, boolean readOnly)
            throws PersistenceException {
        Helper.assertObjectNotNull(connectionFactory, "connectionFactory");

        try {
            // create the connection.
            Connection conn = (connectionName == null)
                              ? connectionFactory.createConnection()
                              : connectionFactory.createConnection(connectionName);

            conn.setAutoCommit(autoCommit);
            conn.setReadOnly(readOnly);

            // if auto-commit mode is disabled, try to set transaction isolation
            // level to Connection.TRANSACTION_SERIALIZABLE.
            if (!autoCommit) {
                conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            }

            return conn;
        } catch (DBConnectionException e) {
            throw new PersistenceException("Error occurs when getting the connection using "
                                           + ((connectionName == null)
                                              ? "default name" : ("name [" + connectionName + "].")), e);
        } catch (SQLException e) {
            throw new PersistenceException("Error occurs when enable/disable the connection's auto-commit mode, "
                                           + "or when enable/disable the connection's read-only mode.", e);
        }
    }

    /**
     * Create a String object from the ids array in the form of "(id0, id1, id2,
     * ...)", used in a sql statement.
     * @param ids
     *            the ids array
     * @return A String that represents the ids array in the form of "(id0, id1,
     *         id2, ...)"
     * @throws IllegalArgumentException
     *             if ids is null or empty
     */
    static String makeIdListString(long[] ids) {
        Helper.assertObjectNotNull(ids, "ids");

        if (ids.length == 0) {
            throw new IllegalArgumentException("ids should not be empty.");
        }

        StringBuffer sb = new StringBuffer();

        sb.append('(');

        // enumerate each id in the array
        for (int i = 0; i < ids.length; ++i) {
            // if it's not the first one, append a comma
            if (i != 0) {
                sb.append(',');
            }

            sb.append(ids[i]);
        }

        sb.append(')');

        return sb.toString();
    }

    /**
     * Load data items from the data row and fill the fields of an
     * AuditedDeliverableStructure instance.
     * @param entity
     *            the AuditedDeliverableStructure instance whose fields will be
     *            filled
     * @param row
     *            the data row
     * @param startIndex
     *            the start index to read from
     * @return the start index of the left data items that haven't been read
     */
    static int loadEntityFieldsSequentially(AuditedDeliverableStructure entity, Object[] row, int startIndex) {
        entity.setId(((Long) row[startIndex++]).longValue());
        entity.setCreationUser((String) row[startIndex++]);
        entity.setCreationTimestamp((Date) row[startIndex++]);
        entity.setModificationUser((String) row[startIndex++]);
        entity.setModificationTimestamp((Date) row[startIndex++]);

        return startIndex;
    }

    /**
     * Load data items from the data row and fill the fields of an
     * NamedDeliverableStructure instance.
     * @param namedEntity
     *            the NamedDeliverableStructure instance whose fields will be
     *            filled
     * @param row
     *            the data row
     * @param startIndex
     *            the start index to read from
     * @return the start index of the left data items that haven't been read
     */
    static int loadNamedEntityFieldsSequentially(NamedDeliverableStructure namedEntity, Object[] row, int startIndex) {
        startIndex = loadEntityFieldsSequentially(namedEntity, row, startIndex);

        namedEntity.setName((String) row[startIndex++]);
        namedEntity.setDescription((String) row[startIndex++]);

        return startIndex;
    }

    /**
     * Check if the given object is null.
     * @param obj
     *            the given object to check
     * @param name
     *            the name to identify the object.
     * @throws IllegalArgumentException
     *             if the given object is null
     */
    static void assertObjectNotNull(Object obj, String name) {
        if (obj == null) {
            throw new IllegalArgumentException(name + " should not be null.");
        }
    }

    /**
     * Check if the given array is null or contains null.
     * @param array
     *            the given array to check
     * @param name
     *            the name to identify the array.
     * @throws IllegalArgumentException
     *             if the given array is null or contains null.
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
     * Check if the lengths of the two arrays are equal.
     * @param array
     *            the given array to check
     * @param array1
     *            the given array to check
     * @param name
     *            the name to identify array.
     * @param name1
     *            the name to identify array1.
     * @throws IllegalArgumentException
     *             if length of array is different from that of array1.
     */
    static void assertArrayLengthEqual(Object[] array, String name, Object[] array1, String name1) {
        if (array.length != array1.length) {
            throw new IllegalArgumentException("The length of " + name + " should be the same as that of " + name1);
        }
    }

    /**
     * Check if the given string is empty (trimmed).
     * @param str
     *            the given string to check
     * @param name
     *            the name to identify the string.
     * @throws IllegalArgumentException
     *             if the given string is not null and empty (trimmed).
     */
    static void assertStringNotEmpty(String str, String name) {
        if ((str != null) && (str.trim().length() == 0)) {
            throw new IllegalArgumentException(name + " should not be empty (trimmed).");
        }
    }

    /**
     * Check if the given string is null or empty (trimmed).
     * @param str
     *            the given string to check
     * @param name
     *            the name to identify the string.
     * @throws IllegalArgumentException
     *             if the given string is null or empty (trimmed).
     */
    static void assertStringNotNullNorEmpty(String str, String name) {
        assertObjectNotNull(str, name);
        assertStringNotEmpty(str, name);
    }

    /**
     * Check if the given long value is UNSET_ID.
     * @param value
     *            the given long value to check.
     * @param name
     *            the name to identify the long value.
     * @throws IllegalArgumentException
     *             if the given long value is UNSET_ID.
     */
    static void assertIdNotUnset(long value, String name) {
        if (value == AuditedDeliverableStructure.UNSET_ID) {
            throw new IllegalArgumentException(name + " [" + value + "] should not be UNSET_ID.");
        }
    }

    /**
     * Check if the given long array is null or contains non positive values.
     * @param values
     *            the long array to check
     * @param name
     *            the name to identify the long array.
     * @throws IllegalArgumentException
     *             if the given long array is null or contains negative or zero
     *             values.
     */
    static void assertLongArrayNotNullAndOnlyHasPositive(long[] values, String name) {
        Helper.assertObjectNotNull(values, name);

        for (int i = 0; i < values.length; ++i) {
            if (values[i] <= 0) {
                throw new IllegalArgumentException(name + " should only contain positive values.");
            }
        }
    }

    /**
     * Check if the given object is an instance of the expected class.
     * @param obj
     *            the given object to check.
     * @param expectedType
     *            the expected class.
     * @param name
     *            the name to identify the object.
     * @throws IllegalArgumentException
     *             if the given object is null or is not an instance of the
     *             expected class.
     */
    static void assertObjectNullOrIsInstance(Object obj, Class<?> expectedType, String name) {
        if ((obj != null) &&!expectedType.isInstance(obj)) {
            throw new IllegalArgumentException(name + " of type [" + obj.getClass().getName()
                                               + "] should be an instance of " + expectedType.getName());
        }
    }

    /**
     * Check if the given AuditedDeliverableStructure instance is valid to
     * persist.
     * @param entity
     *            the given AuditedDeliverableStructure instance to check.
     * @param name
     *            the name to identify the instance.
     * @throws IllegalArgumentException
     *             if the given instance is null or isValidToPersist returns
     *             false.
     */
    static void assertEntityNotNullAndValidToPersist(AuditedDeliverableStructure entity, String name) {
        Helper.assertObjectNotNull(entity, name);

        if (!entity.isValidToPersist()) {
            throw new IllegalArgumentException("The entity [" + name + "] is not valid to persist.");
        }
    }

    /**
     * This class is a wrapper for type safe getting of values from a ResultSet
     * and setting of values to a PreparedStatement. The values retrieved by the
     * getValue(java.sql.ResultSet, int) implementation of this DataType are
     * assured to be of type Boolean or to be null in case the ResultSet value
     * was null. PreparedStatement#setBoolean() will be used to set the value,
     * which should be of Boolean type.
     * @author urtks
     * @version 1.0
     */
    private static class BooleanType extends DataType {
        /**
         * This method retrieves the value at the given index from the given
         * resultSet as instance of Boolean type.
         * @param resultSet
         *            the result set from which to retrieve the value
         * @param index
         *            the index at which to retrieve the value
         * @return the retrieved value as Boolean or null if the value in the
         *         ResultSet was null.
         * @throws IllegalArgumentException
         *             if resultSet is null
         * @throws SQLException
         *             if error occurs while working with the given ResultSet or
         *             the index does not exist in the result set
         */
        protected Object getValue(ResultSet resultSet, int index) throws SQLException {
            Helper.assertObjectNotNull(resultSet, "resultSet");

            return resultSet.wasNull() ? null : new Boolean(resultSet.getBoolean(index));
        }

        /**
         * This method sets the value at the given index from the given
         * preparedStatement as instance of Boolean type.
         * @param preparedStatement
         *            the prepared statement from which to set the value
         * @param index
         *            the index at which to set the value
         * @param value
         *            the value to set
         * @throws IllegalArgumentException
         *             if preparedStatement or value is null, or value is not an
         *             instance of Boolean type
         * @throws SQLException
         *             if error occurs while working with the given
         *             preparedStatement or the index does not exist in the
         *             prepared statement
         */
        protected void setValue(PreparedStatement preparedStatement, int index, Object value) throws SQLException {
            Helper.assertObjectNotNull(preparedStatement, "preparedStatement");
            Helper.assertObjectNullOrIsInstance(value, Boolean.class, "value " + index);

            preparedStatement.setBoolean(index, ((Boolean) value).booleanValue());
        }
    }


    /**
     * <p>
     * This class is a wrapper for type safe getting of values from a ResultSet
     * and setting of values to a PreparedStatement. This class has been
     * introduced to consist the behaviors of different databases and JDBC
     * drivers so that always the expected type is returned (getObject(int) does
     * not sufficiently do this job as the type of the value is highly
     * database-dependent (e.g. for a BLOB column the MySQL driver returns a
     * byte[] and the Oracle driver returns a Blob)). This class has also been
     * introduced to make sure that the input parameter objects are all of
     * expected types and the specified setXXX methods are used (setObject() is
     * kind of dangerous in some cases).
     * </p>
     * <p>
     * This class contains a private constructor to make sure all
     * implementations of this class are declared inside Helper. Instances are
     * provided to users via constants declared in Helper - so this class
     * defines some kind of 'pseudo-enum' which cannot be instantiated
     * externally.
     * </p>
     * @author urtks
     * @version 1.0
     */
    abstract static class DataType {
        /**
         * Empty private constructor. By using this concept, it is assured that
         * only Helper class can contain subclasses of this class and the
         * implementation classes cannot be instantiated externally.
         */
        private DataType() {}

        /**
         * This method gets the value at the given index from the given
         * resultSet as instance of the subclass-dependent type.
         * @param resultSet
         *            the result set from which to get the value
         * @param index
         *            the index at which to get the value
         * @return the retrieved value
         * @throws IllegalArgumentException
         *             if resultSet is null
         * @throws SQLException
         *             if error occurs while working with the given ResultSet or
         *             the index does not exist in the result set
         */
        protected abstract Object getValue(ResultSet resultSet, int index) throws SQLException;

        /**
         * This method sets the value at the given index from the given
         * preparedStatement as instance of the subclass-dependent type.
         * @param preparedStatement
         *            the prepared statement from which to set the value
         * @param index
         *            the index at which to set the value
         * @param value
         *            the value to set
         * @throws IllegalArgumentException
         *             if preparedStatement or value is null, or value is not an
         *             instance of the subclass-dependent type
         * @throws SQLException
         *             if error occurs while working with the given
         *             preparedStatement or the index does not exist in the
         *             prepared statement
         */
        protected abstract void setValue(PreparedStatement preparedStatement, int index, Object value)
                throws SQLException;
    }


    /**
     * This class is a wrapper for type safe getting of values from a ResultSet
     * and setting of values to a PreparedStatement. The values retrieved by the
     * getValue(java.sql.ResultSet, int) implementation of this DataType are
     * assured to be of type java.util.Date or to be null in case the ResultSet
     * value was null. PreparedStatement#setTimestamp() will be used to set the
     * value, which should be of java.util.Date type.
     * @author urtks
     * @version 1.0
     */
    private static class DateType extends DataType {
        /**
         * This method retrieves the value at the given index from the given
         * resultSet as instance of java.util.Date type.
         * @param resultSet
         *            the result set from which to retrieve the value
         * @param index
         *            the index at which to retrieve the value
         * @return the retrieved value as java.util.Date or null if the value in
         *         the ResultSet was null.
         * @throws IllegalArgumentException
         *             if resultSet is null
         * @throws SQLException
         *             if error occurs while working with the given ResultSet or
         *             the index does not exist in the result set
         */
        protected Object getValue(ResultSet resultSet, int index) throws SQLException {
            Helper.assertObjectNotNull(resultSet, "resultSet");

            Timestamp timestamp = resultSet.getTimestamp(index);

            return (timestamp == null) ? null : new Date(timestamp.getTime());
        }

        /**
         * This method sets the value at the given index from the given
         * preparedStatement as instance of java.util.Date type. <p/> Note:
         * UnsupportedOperationException is always thrown currently to ensure
         * that this method won't be called.
         * @param preparedStatement
         *            the prepared statement from which to set the value
         * @param index
         *            the index at which to set the value
         * @param value
         *            the value to set
         * @throws IllegalArgumentException
         *             if preparedStatement or value is null, or value is not an
         *             instance of java.util.Date type
         * @throws SQLException
         *             if error occurs while working with the given
         *             preparedStatement or the index does not exist in the
         *             prepared statement
         */
        protected void setValue(PreparedStatement preparedStatement, int index, Object value) throws SQLException {
            Helper.assertObjectNotNull(preparedStatement, "statement");
            Helper.assertObjectNullOrIsInstance(value, Date.class, "value" + index);

            preparedStatement.setTimestamp(index, (value == null) ? null : new Timestamp(((Date) value).getTime()));
        }
    }


    /**
     * This class is a wrapper for type safe getting of values from a ResultSet
     * and setting of values to a PreparedStatement. The values retrieved by the
     * getValue(java.sql.ResultSet, int) implementation of this DataType are
     * assured to be of type Long or to be null in case the ResultSet value was
     * null. PreparedStatement#setLong() will be used to set the value, which
     * should be of Long type.
     * @author urtks
     * @version 1.0
     */
    private static class LongType extends DataType {
        /**
         * This method retrieves the value at the given index from the given
         * resultSet as instance of Long type.
         * @param resultSet
         *            the result set from which to retrieve the value
         * @param index
         *            the index at which to retrieve the value
         * @return the retrieved value as Long or null if the value in the
         *         ResultSet was null.
         * @throws IllegalArgumentException
         *             if resultSet is null
         * @throws SQLException
         *             if error occurs while working with the given ResultSet or
         *             the index does not exist in the result set
         */
        protected Object getValue(ResultSet resultSet, int index) throws SQLException {
            Helper.assertObjectNotNull(resultSet, "resultSet");

            return resultSet.wasNull() ? null : new Long(resultSet.getLong(index));
        }

        /**
         * This method sets the value at the given index from the given
         * preparedStatement as instance of Long type.
         * @param preparedStatement
         *            the prepared statement from which to set the value
         * @param index
         *            the index at which to set the value
         * @param value
         *            the value to set
         * @throws IllegalArgumentException
         *             if preparedStatement or value is null, or value is not an
         *             instance of Long type
         * @throws SQLException
         *             if error occurs while working with the given
         *             preparedStatement or the index does not exist in the
         *             prepared statement
         */
        protected void setValue(PreparedStatement preparedStatement, int index, Object value) throws SQLException {
            Helper.assertObjectNotNull(preparedStatement, "statement");
            Helper.assertObjectNullOrIsInstance(value, Long.class, "value " + index);

            preparedStatement.setLong(index, ((Long) value).longValue());
        }
    }


    /**
     * This class is a wrapper for type safe getting of values from a ResultSet
     * and setting of values to a PreparedStatement. The values retrieved by the
     * getValue(java.sql.ResultSet, int) implementation of this DataType are
     * assured to be of type String or to be null in case the ResultSet value
     * was null. PreparedStatement#setString() will be used to set the value,
     * which should be of String type.
     * @author urtks
     * @version 1.0
     */
    private static class StringType extends DataType {
        /**
         * This method retrieves the value at the given index from the given
         * resultSet as instance of String type.
         * @param resultSet
         *            the result set from which to retrieve the value
         * @param index
         *            the index at which to retrieve the value
         * @return the retrieved value as String or null if the value in the
         *         ResultSet was null.
         * @throws IllegalArgumentException
         *             if resultSet is null
         * @throws SQLException
         *             if error occurs while working with the given ResultSet or
         *             the index does not exist in the result set
         */
        protected Object getValue(ResultSet resultSet, int index) throws SQLException {
            Helper.assertObjectNotNull(resultSet, "resultSet");

            return resultSet.getString(index);
        }

        /**
         * This method sets the value at the given index from the given
         * preparedStatement as instance of String type.
         * @param preparedStatement
         *            the prepared statement from which to set the value
         * @param index
         *            the index at which to set the value
         * @param value
         *            the value to set
         * @throws IllegalArgumentException
         *             if preparedStatement or value is null, or value is not an
         *             instance of String type
         * @throws SQLException
         *             if error occurs while working with the given
         *             preparedStatement or the index does not exist in the
         *             prepared statement
         */
        protected void setValue(PreparedStatement preparedStatement, int index, Object value) throws SQLException {
            Helper.assertObjectNotNull(preparedStatement, "statement");
            Helper.assertObjectNullOrIsInstance(value, String.class, "value " + index);

            preparedStatement.setString(index, (String) value);
        }
    }
}
