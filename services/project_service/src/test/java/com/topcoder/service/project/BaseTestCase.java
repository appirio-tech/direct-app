/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.project;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import junit.framework.TestCase;


/**
 * <p>This base test case provides common functionality for configuration and database.</p>
 *
 * @author TCSDEVELOPER
 * @version 1.1
 * @since 1.0
 */
public abstract class BaseTestCase extends TestCase {

    /**
     * <p>Setup the testing environment.</p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    /**
     * <p>Executes the sql script against the database.</p>
     *
     * @param filename the file name.
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void executeScriptFile(String filename) throws Exception {
        Connection conn = null;
        Statement stmt = null;
        BufferedReader bufferedReader = null;

        try {
            conn = getConnection();
            conn.setAutoCommit(false);

            stmt = conn.createStatement();

            String sql = null;
            String path = this.getClass().getResource(filename).toURI().getPath();
            bufferedReader = new BufferedReader(new FileReader(path));

            while (null != (sql = bufferedReader.readLine())) {
                stmt.executeUpdate(sql);
            }

            conn.commit();
        } catch (SQLException e) {
            conn.rollback();

            throw e;
        } finally {
            closeStatement(stmt);
            closeConnection(conn);

            if (null != bufferedReader) {
                bufferedReader.close();
            }
        }
    }

    /**
     * <p>Executes the sql statements against the database.</p>
     *
     * @param sqls the array of sql statements.
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void executeSQL(String[] sqls) throws Exception {
        Connection conn = null;
        Statement stmt = null;

        try {
            conn = getConnection();
            conn.setAutoCommit(false);

            stmt = conn.createStatement();

            for (int i = 0; i < sqls.length; i++) {
                stmt.executeUpdate(sqls[i]);
            }

            conn.commit();
        } catch (SQLException e) {
            conn.rollback();

            throw e;
        } finally {
            closeStatement(stmt);
            closeConnection(conn);
        }
    }

    /**
     * <p>Closes the connection. It will be used in finally block.</p>
     *
     * @param conn the database connection.
     */
    public static void closeConnection(Connection conn) {
        if (null != conn) {
            try {
                conn.close();
            } catch (SQLException e) {
                // ignore
            }
        }
    }

    /**
     * <p>Close the result set. It will be used in finally block.</p>
     *
     * @param rs the result set.
     */
    public static void closeResultSet(ResultSet rs) {
        if (null != rs) {
            try {
                rs.close();
            } catch (SQLException e) {
                // ignore
            }
        }
    }

    /**
     * <p>Close the statement. It will be used in finally block.</p>
     *
     * @param stmt the statement.
     */
    public static void closeStatement(Statement stmt) {
        if (null != stmt) {
            try {
                stmt.close();
            } catch (SQLException e) {
                // ignore
            }
        }
    }

    /**
     * <p>Gets the field value of an object.</p>
     *
     * @param obj the object where to get the field value.
     * @param fieldName the name of the field.
     *
     * @return the field value
     *
     * @throws Exception any exception occurs.
     */
    public static Object getFieldValue(Object obj, String fieldName)
        throws Exception {
        Field field = obj.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);

        return field.get(obj);
    }

    /**
     * <p>Create a DB connection from the configuration file.</p>
     *
     * @return DB connection
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    protected Connection getConnection() throws Exception {
        // load the properties from configuration file
        Properties prop = new Properties();
        InputStream fs = this.getClass().getResourceAsStream("/db.properties");
        BufferedInputStream bs = new BufferedInputStream(fs);
        prop.load(bs);

        fs.close();
        bs.close();

        String driver = prop.getProperty("dbdriver");
        String url = prop.getProperty("dburl");
        String user = prop.getProperty("dbuser");
        String password = prop.getProperty("dbpassword");
        Class.forName(driver);

        return DriverManager.getConnection(url, user, password);
    }
}
