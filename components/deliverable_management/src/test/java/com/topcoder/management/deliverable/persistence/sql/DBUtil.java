/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved
 */



package com.topcoder.management.deliverable.persistence.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.List;

/**
 * <p>
 * Helper class that maintain common database methods used in this component.
 * </p>
 *
 * @author Chenhong
 * @version 1.0
 */
final class DBUtil {
    /**
     * Empty constructor.
     */
    private DBUtil() {
        // empty.
    }

    /**
     * Closes the connection.
     *
     * @param conn the connection.
     */
    public static void close(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                // ignore
            }
        }
    }

    /**
     * Closes the statement.
     *
     * @param stmt the statement.
     */
    public static void close(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                // ignore
            }
        }
    }

    /**
     * Closes the result set.
     *
     * @param rs the result set.
     */
    public static void close(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                // ignore
            }
        }
    }

    /**
     * Roll backs the transaction.
     *
     * @param conn connection.
     */
    public static void rollback(Connection conn) {
        try {
            conn.rollback();
        } catch (SQLException ex) {
            // ignore
        }
    }

    /**
     * Converts the give list of Long objects to array.
     *
     * @param ids the ids list.
     * @return the ids array.
     */
    static long[] listToArray(List ids) {
        long[] result = new long[ids.size()];

        for (int i = 0; i < result.length; i++) {
            result[i] = ((Long) ids.get(i)).longValue();
        }

        return result;
    }

    /**
     * Creates new PreparedStatement and fill it up with the ids.
     *
     * @param query the query to be use.
     * @param ids the submission id.
     * @param conn the connection to be used.
     * @return the prepared statement filled with id values.
     * @throws SQLException if error occurs during operation.
     */
    static PreparedStatement createInQueryStatement(String query, long[] ids, Connection conn) throws SQLException {
        String actualQuery = query + createQuestionMarks(ids.length);
        PreparedStatement pstmt = conn.prepareStatement(actualQuery);

        for (int i = 0; i < ids.length; i++) {
            pstmt.setLong(i + 1, ids[i]);
        }

        return pstmt;
    }

    /**
     * Creates the string in the pattern (?,+) where count is the number of question marks. It is used th build
     * prepared statements with IN condition.
     *
     * @param count number of question marks.
     * @return the string of question marks.
     */
    static String createQuestionMarks(int count) {
        StringBuffer buff = new StringBuffer();

        buff.append("(?");

        for (int i = 1; i < count; i++) {
            buff.append(", ?");
        }

        buff.append(")");

        return buff.toString();
    }
}
