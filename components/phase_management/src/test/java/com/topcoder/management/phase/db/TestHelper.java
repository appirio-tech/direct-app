/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.management.phase.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.management.phase.AbstractDbPhasePersistence;

/**
 * Utility class to facilitate the unit test.
 * @author TCSDEVELOEPR
 * @version 1.1
 */
final class TestHelper {

    /**
     * private constructor used to prevent the instantiation.
     */
    private TestHelper() {
    }

    /**
     * Closes the resource if they are available.
     * @param conn the <code>Connection</code> to close.
     * @param stmt the <Code>Statement</code> to close.
     * @param rs the <code>ResultSet</code> to close.
     */
    static final void closeResource(Connection conn, Statement stmt,
            ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                // ignore
            }
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                // ignore
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                // ignore
            }
        }
    }

    /**
     * Executes the SQL with the given connection.
     * @param conn the connection used to execute the SQL.
     * @param sql the SQL statement to execute.
     * @throws Exception if there is any problem.
     */
    static final void executeSQL(Connection conn, String sql) throws Exception {
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            stmt.execute(sql);
        } finally {
            closeResource(null, stmt, null);
        }
    }

    /**
     * Executes the SQL.
     * @param sql the SQL statement to execute.
     * @throws Exception if there is any problem.
     */
    static final void executeSQL(String sql) throws Exception {
        Connection conn = null;
        try {
            conn = getConnection();
            executeSQL(conn, sql);
        } finally {
            closeResource(conn, null, null);
        }
    }

    /**
     * Checks if the given SQL can select data from database.
     * @param sql the SQL to select data.
     * @return true if the SQL can get data, false otherwise.
     * @throws Exception if there is any problem.
     */
    static final boolean assertRecordExists(String sql) throws Exception {
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            connection = getConnection();
            stmt = connection.createStatement();
            rs = stmt.executeQuery(sql);
            return rs.next();
        } finally {
            closeResource(connection, stmt, rs);
        }
    }

    /**
     * Gets the connection from DB Connection Factory.
     * @return the connection retrieved.
     * @throws Exception if fails to get the connection.
     */
    static final Connection getConnection() throws Exception {
        return new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class
                .getName()).createConnection();
    }

    /**
     * Creates a context map with the connection filled with key "connection".
     * @param conn the connection which should be in the context map.
     * @return a context map containing the connection with key "connection".
     */
    static final Map createContextMap(Connection conn) {
        Map context = new HashMap();
        context.put(AbstractDbPhasePersistence.CONNECTION_CONTEXT_KEY, conn);
        return context;
    }

    /**
     * Creates the records in database for testing.
     * @throws Exception if there is any problem.
     */
    static final void initDatabase() throws Exception {
        Connection conn = null;
        try {
            conn = getConnection();
//            executeSQL(conn, "INSERT INTO id_sequences(name, next_block_start, "
//                    + "block_size, exhausted) VALUES('phase_id_seq', 1, 20, 0);");
            executeSQL(conn, "INSERT INTO phase_status_lu(phase_status_id, name, "
                    + "description, create_user, create_date, modify_user, modify_date) "
                    + "VALUES(1, 'Scheduled', 'Scheduled', 'System', CURRENT, 'System', CURRENT);");
            executeSQL(conn, "INSERT INTO phase_status_lu(phase_status_id, name, "
                    + "description, create_user, create_date, modify_user, modify_date) "
                    + "VALUES(2, 'Open', 'Open', 'System', CURRENT, 'System', CURRENT);");
            executeSQL(conn, "INSERT INTO phase_status_lu(phase_status_id, name, "
                    + "description, create_user, create_date, modify_user, modify_date) "
                    + "VALUES(3, 'Closed', 'Closed', 'System', CURRENT, 'System', CURRENT);");
        } finally {
            closeResource(conn, null, null);
        }
    }


    /**
     * Cleans the data in database.
     * @throws Exception if there is any problem.
     */
    static void cleanDatabase() throws Exception {
        Connection conn = null;
        try {
            conn = getConnection();
            executeSQL(conn, "DELETE FROM phase_status_lu;");
            //executeSQL(conn, "DELETE FROM id_sequences;");
        } finally {
            closeResource(conn, null, null);
        }
    }
}
