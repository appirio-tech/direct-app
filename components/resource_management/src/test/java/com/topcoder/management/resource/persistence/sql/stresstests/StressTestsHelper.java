/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.management.resource.persistence.sql.stresstests;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.management.resource.NotificationType;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.resource.ResourceRole;
import com.topcoder.util.config.ConfigManager;

/**
 * This is the utility class to help the stress testing.
 * 
 * @author fuyun, littleken
 * @version 1.2
 * @since 1.1
 */
final class StressTestsHelper {

    /**
     * <p>
     * Loads the configuration file.
     * </p>
     * 
     * @throws Exception if there is any problem.
     */
    static final void loadConfiguration() throws Exception {
        ConfigManager.getInstance().add("stresstests/dbconfig.xml");
    }

    /**
     * <p>
     * Cleans up the test environment.
     * </p>
     * 
     * @throws Exception if there is any problem.
     */
    static final void cleanConfiguration() throws Exception {
        ConfigManager.getInstance().removeNamespace(
            "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl");
    }

    /**
     * Prepares the data in database for testing.
     * 
     * @throws Exception if there is any problem.
     */
    static final void prepareDatabase() throws Exception {
        Connection conn = null;
        try {
            conn = getConnection();
            executeSQL(conn, "INSERT INTO notification_type_lu VALUES (2007, 'notification_type_1', "
                + "'used for test', 'stress', current, 'stress', current);");
            executeSQL(conn, "INSERT INTO project VALUES (2007);");
            executeSQL(conn, "INSERT INTO phase_type_lu VALUES (2007);");
            executeSQL(conn, "INSERT INTO resource_role_lu VALUES (2007, 2007, 'resource_role_1', "
                + "'userd for test', 'stress', current, 'stress', current);");
            for (int i = 1; i <= 10; i++) {
                executeSQL(conn, "INSERT INTO submission VALUES (" + i + ");");
            }

        } finally {
            closeResource(conn, null, null);
        }

    }

    /**
     * Cleans the test data in database.
     * 
     * @throws Exception if there is any problem.
     */
    static final void cleanDatabase() throws Exception {
        Connection conn = null;
        try {
            conn = getConnection();
            executeSQL(conn, "DELETE FROM notification;");
            executeSQL(conn, "DELETE FROM notification_type_lu;");
            executeSQL(conn, "DELETE FROM resource_submission;");
            executeSQL(conn, "DELETE FROM resource_info;");
            executeSQL(conn, "DELETE FROM resource_info_type_lu;");
            executeSQL(conn, "DELETE FROM resource;");
            executeSQL(conn, "DELETE FROM project_user_audit;");
            executeSQL(conn, "DELETE FROM resource_role_lu;");
            executeSQL(conn, "DELETE FROM submission;");
            executeSQL(conn, "DELETE FROM project_phase;");
            executeSQL(conn, "DELETE FROM phase_type_lu;");
            executeSQL(conn, "DELETE FROM project;");

        } finally {
            closeResource(conn, null, null);
        }
    }

    /**
     * Closes the resource if they are available.
     * 
     * @param conn the <code>Connection</code> to close.
     * @param stmt the <Code>Statement</code> to close.
     * @param rs the <code>ResultSet</code> to close.
     */
    static final void closeResource(Connection conn, Statement stmt, ResultSet rs) {
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
     * 
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
     * 
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
     * Gets the connection from DB Connection Factory.
     * 
     * @return the connection retrieved.
     * @throws Exception if fails to get the connection.
     */
    static final Connection getConnection() throws Exception {
        return getDBConnectionFactory().createConnection();
    }

    /**
     * Gets the <code>DBConnectionFactory</code> instance.
     * 
     * @return the <code>DBConnectionFactory</code> instance.
     * @throws Exception if there is any problem.
     */
    static final DBConnectionFactory getDBConnectionFactory() throws Exception {
        return new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName());
    }

    /**
     * Gets the number of records returned by the given SQL.
     * 
     * @param sql the SQL statement to retrieve the records.
     * @return the number of records returned by the given SQL.
     * @throws Exception if there is any problem.
     */
    static final int getRecordsNumber(String sql) throws Exception {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            rs.next();
            return rs.getInt(1);
        } finally {
            closeResource(conn, stmt, rs);
        }
    }

    /**
     * Returns the notification type with the specific id.
     * 
     * @param id the id
     * @return the notification type with the specific id
     */
    static final NotificationType getNotificationType(long id) {
        NotificationType notificationType = new NotificationType(id);
        Date current = new Date();
        notificationType.setCreationTimestamp(current);
        notificationType.setCreationUser("stress");
        notificationType.setModificationTimestamp(current);
        notificationType.setModificationUser("stress");
        notificationType.setName("name");
        notificationType.setDescription("For test");
        return notificationType;
    }

    /**
     * Returns the resource with the specific id.
     * 
     * @param id the id
     * @return the resource with the specific id
     */
    static final Resource getResource(long id, long roleId) {
        Resource resource = new Resource(id);
        resource.setResourceRole(getResourceRole(roleId));
        Date current = new Date();
        resource.setCreationTimestamp(current);
        resource.setCreationUser("stress");
        resource.setModificationTimestamp(current);
        resource.setModificationUser("1");
        resource.setProperty("External Reference ID", 1);
        resource.setProject(2007L);
        for (int i = 1; i <= 10; i++) {
            resource.addSubmission(new Long(i));
        }
        return resource;
    }

    /**
     * Returns the resource role with the specific id.
     * 
     * @param id the id
     * @return the resource role with the specific id
     */
    static final ResourceRole getResourceRole(long id) {
        ResourceRole resourceRole = new ResourceRole(id);
        Date current = new Date();
        resourceRole.setCreationTimestamp(current);
        resourceRole.setCreationUser("stress");
        resourceRole.setModificationTimestamp(current);
        resourceRole.setModificationUser("stress");
        resourceRole.setName("name");
        resourceRole.setDescription("For test");
        return resourceRole;
    }
}
