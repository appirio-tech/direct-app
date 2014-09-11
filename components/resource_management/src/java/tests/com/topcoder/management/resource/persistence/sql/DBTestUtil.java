/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource.persistence.sql;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.management.resource.NotificationType;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.resource.ResourceRole;
import com.topcoder.management.resource.persistence.ResourcePersistenceException;
import com.topcoder.util.config.ConfigManager;

/**
 * This is the utility class for assisting the process of testing.
 *
 * It helps to clear the database tables, create certain records etc.
 *
 * @author Chenhong, TCSDEVELOPER
 * @version 1.2
 * @since 1.1
 */
public final class DBTestUtil {
    /**
     * <p>
     * Represents the namespace for DB Connection Factory component.
     * </p>
     */
    public static final String DB_FACTORY_NAMESPACE =
        "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl";

    /**
     * <p>
     * The DBConnectionFactory instance for testing.
     * </p>
     */
    private static DBConnectionFactory factory;

    /**
     * The table names in this component.
     */
    private static String[] tableNames =
        {"notification", "notification_type_lu", "resource_submission", "resource_info",
            "resource_info_type_lu", "resource", "project_user_audit", "resource_role_lu",
            "submission", "project_phase", "phase_type_lu", "project"};

    /**
     * Private constructor.
     */
    private DBTestUtil() {
        // empty.
    }

    /**
     * Cleart the config manager.
     *
     * @throws Exception to junit.
     */
    public static void clearConfigManager() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        for (Iterator iter = cm.getAllNamespaces(); iter.hasNext();) {
            cm.removeNamespace((String) iter.next());
        }
    }

    /**
     * Create a notification type instance for testing, it will not hit the db.
     *
     * @param id the id of the notification type.
     *
     * @return the NotificationType instance.
     */
    public static NotificationType createNotificationType(long id) {
        NotificationType type = new NotificationType(id);
        type.setName("topcoder");
        type.setDescription("what is a tree");
        type.setCreationUser("developer");
        type.setCreationTimestamp(new Timestamp(System.currentTimeMillis()));
        type.setModificationUser("developer");
        type.setModificationTimestamp(new Timestamp(System.currentTimeMillis()));

        return type;
    }

    /**
     * Create a resource role instance for testing, it will not hit the db.
     *
     * @param id the id of the resource role.
     *
     * @return the ResourceRole instance.
     */
    public static ResourceRole createResourceRole(long id) {
        ResourceRole role = new ResourceRole(id);
        role.setDescription("resource role");
        role.setName("tc");

        role.setPhaseType(new Long(1));

        Date now = new Date();

        role.setCreationUser("developer");
        role.setCreationTimestamp(now);
        role.setModificationUser("developer");
        role.setModificationTimestamp(now);

        return role;
    }

    /**
     * Create a Resource instance for test. This Resource instance has not submission property and no external
     * properties.
     *
     * @param resourceId the id for resource
     * @param projectId the project id
     * @param phaseId the phase id
     * @return the Resource instance
     */
    public static Resource createResource(long resourceId, long projectId, long phaseId) {
        Resource resource = new Resource(resourceId);

        Date now = new Date();
        resource.setCreationUser("tc");
        resource.setCreationTimestamp(now);
        resource.setModificationUser("1");
        resource.setModificationTimestamp(now);

        resource.setProject(new Long(projectId));
        resource.setPhase(new Long(phaseId));

        ResourceRole role = createResourceRole(5);

        resource.setResourceRole(role);

        resource.setProperty("External Reference ID", 1);

        return resource;
    }

    /**
     * <p>
     * Deletes data from the table used by this component.
     * </p>
     *
     * @throws Exception to junit.
     */
    public static void clearTables() throws Exception {

        Connection connection = getConnection();
        Statement statement = null;

        try {
            statement = connection.createStatement();
            for (int i = 0; i < tableNames.length; i++) {
                statement.executeUpdate("DELETE FROM " + tableNames[i]);
            }
        } finally {
            closeStatement(statement);
            closeConnection(connection);
        }
    }

    /**
     * Insert some records for testing.
     *
     * @throws Exception to junit.
     */
    public static void setupDatbase() throws Exception {
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        statement.addBatch("insert into project (project_id) values (1)");
        statement.addBatch("insert into project (project_id) values (2)");
        statement.addBatch("insert into project (project_id) values (3)");
        statement.addBatch("insert into project (project_id) values (4)");
        statement.addBatch("insert into project (project_id) values (5)");

        statement.addBatch("insert into phase_type_lu (phase_type_id) values(1)");
        statement.addBatch("insert into phase_type_lu (phase_type_id) values(2)");
        statement.addBatch("insert into phase_type_lu (phase_type_id) values(3)");
        statement.addBatch("insert into phase_type_lu (phase_type_id) values(4)");

        statement
            .addBatch("insert into project_phase (project_phase_id, project_id, phase_type_id) values(1,1,1)");
        statement
            .addBatch("insert into project_phase (project_phase_id, project_id, phase_type_id) values(2,2,2)");
        statement
            .addBatch("insert into project_phase (project_phase_id, project_id, phase_type_id) values(3,3,3)");
        statement
            .addBatch("insert into project_phase (project_phase_id, project_id, phase_type_id) values(4,4,4)");

        statement.addBatch("insert into submission (submission_id) values(121);");
        statement.addBatch("insert into submission (submission_id) values(122);");
        statement.addBatch("insert into submission (submission_id) values(123);");
        statement.addBatch("insert into submission (submission_id) values(1200);");
        statement.addBatch("insert into submission (submission_id) values(1201);");
        statement.addBatch("insert into submission (submission_id) values(1202);");
        statement.addBatch("insert into submission (submission_id) values(1203);");
        statement.addBatch("insert into submission (submission_id) values(1204);");
        statement.addBatch("insert into submission (submission_id) values(1205);");
        statement.addBatch("insert into submission (submission_id) values(1206);");

        statement.executeBatch();
        statement.close();
        connection.close();

    }

    /**
     * Insert records into resource_info_type_lu table, so that resource can set properties.
     *
     * @param id the id of the resource_info_type.
     *
     * @param name the name of the resource_info_type
     *
     * @throws Exception to junit.
     */
    public static void insertIntoResource_info_type_lu(int id, String name) throws Exception {
        Connection connection = getConnection();

        PreparedStatement statement = null;

        String query =
            "INSERT INTO resource_info_type_lu"
                + " (resource_info_type_id, name,description, create_user, create_date, modify_user, modify_date)"
                + " VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            statement = connection.prepareStatement(query);

            statement.setInt(1, id);
            statement.setString(2, name);
            statement.setString(3, "resource_info_type_lu");
            statement.setString(4, "tc");
            statement.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
            statement.setString(6, "topcoder");
            statement.setTimestamp(7, new Timestamp(System.currentTimeMillis()));

            statement.executeUpdate();
        } finally {
            closeStatement(statement);
            closeConnection(connection);
        }
    }

    /**
     * Insert a submission.
     *
     * @param id the id of the submission
     *
     * @throws Exception to junit.
     */
    public static void insertIntoSubmission(int id) throws Exception {
        String query = "insert into submission (submission_id)  values (?)";

        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(query);

            statement.setInt(1, id);

            statement.executeUpdate();

        } finally {
            closeStatement(statement);
            closeConnection(connection);
        }
    }

    /**
     * get the submission entry for Resource instance.
     *
     * @param resource the Resource instance
     * @return Integer if exists, otherwise null
     * @throws Exception to junit.
     */
    public static Integer getSubmissionEntry(Resource resource) throws Exception {

        String query = "SELECT submission_id FROM resource_submission WHERE resource_id = ?";

        Connection connection = getConnection();

        PreparedStatement statement = null;

        ResultSet rs = null;

        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, (int) resource.getId());

            rs = statement.executeQuery();

            if (rs.next()) {
                return new Integer(rs.getInt(1));
            }

            return null;
        } catch (SQLException e) {
            throw new ResourcePersistenceException("Failed to get the submission.", e);
        } finally {
            closeStatement(statement);
            closeResultSet(rs);
            closeConnection(connection);
        }
    }

    /**
     * <p>
     * Returns a new connection to be used for persistence.
     * </p>
     *
     * @return the connection instance for database operation
     *
     * @throws Exception If unable to obtain a Connection
     */
    private static Connection getConnection() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        for (Iterator iter = cm.getAllNamespaces(); iter.hasNext();) {
            cm.removeNamespace((String) iter.next());
        }

        cm.add(new File("test_files/DBConnectionFactory.xml").getAbsolutePath());

        factory = new DBConnectionFactoryImpl(DB_FACTORY_NAMESPACE);
        return factory.createConnection();
    }

    /**
     * <p>
     * Closes the given Connection instance.
     * </p>
     *
     * @param connection the given Connection instance to close.
     */
    private static void closeConnection(Connection connection) {
        try {
            if ((connection != null) && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            // Ignore
        }
    }

    /**
     * <p>
     * Closes the given PreparedStatement instance.
     * </p>
     *
     * @param statement the given Statement instance to close.
     */
    private static void closeStatement(Statement statement) {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            // Ignore
        }
    }

    /**
     * <p>
     * Closes the given ResultSet.
     * </p>
     *
     * @param rs the given ResultSet instance to close.
     */
    private static void closeResultSet(ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            // ignore.
        }
    }
}
