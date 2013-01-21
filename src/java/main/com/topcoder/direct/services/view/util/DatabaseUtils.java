/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.util;

import com.topcoder.shared.util.DBMS;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * <p>
 *  Util class for the direct database actions.
 * </p>
 *
 * @author TCSASSEMBLER
 * @version 1.0 (Module Assembly - TopCoder Cockpit Instant Search)
 */
public class DatabaseUtils {

    /**
     * Private constructor.
     */
    private DatabaseUtils() {

    }

    /**
     * Gets the database connection from the data source.
     *
     * @param dataSourceName the name of the data source.
     * @return the connection
     * @throws Exception if there is any error when creating the connection.
     */
    public static Connection getDatabaseConnection(String dataSourceName) throws Exception {
        return DBMS.getConnection(dataSourceName);
    }

    /**
     * Close the result set.
     *
     * @param resultSet the result set.
     */
    public static void close(ResultSet resultSet) {
        try {
            if (resultSet != null) {
                resultSet.close();
                resultSet = null;
            }
        } catch (SQLException ex) {
            // ignore
        }
    }

    /**
     * Closes the sql statement.
     *
     * @param sqlStatement the sql statement.
     */
    public static void close(Statement sqlStatement) {
        try {
            if (sqlStatement != null) {
                sqlStatement.close();
                sqlStatement = null;
            }
        } catch (SQLException ex) {
            // ignore
        }
    }

    /**
     * Closes the connection
     *
     * @param connection the database connection.
     */
    public static void close(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
                connection = null;
            }
        } catch (SQLException ex) {
            // ignore
        }
    }
}
