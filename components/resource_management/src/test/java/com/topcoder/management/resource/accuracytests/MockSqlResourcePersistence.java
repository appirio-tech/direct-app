/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource.accuracytests;

import com.topcoder.db.connectionfactory.DBConnectionException;
import com.topcoder.db.connectionfactory.DBConnectionFactory;

import com.topcoder.management.resource.Notification;
import com.topcoder.management.resource.NotificationType;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.resource.ResourceRole;
import com.topcoder.management.resource.persistence.ResourcePersistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * The <code>MockSqlResourcePersistence</code> class implements the
 * <code>ResourcePersistence</code> interface. It is used to persist the
 * database structure given in the resource_management.sql script. Here it is a
 * mock class of <code>SqlResourcePersistence</code>. It only used for
 * accuracy testing.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockSqlResourcePersistence implements ResourcePersistence {
    /**
     * <p>
     * The name of the database connection producer from the
     * DBConnectionFactory.
     * </p>
     */
    private final String connectionName;

    /**
     * <p>
     * The connection factory used to create the database connection.
     * </p>
     */
    private final DBConnectionFactory factory;

    /**
     * <p>
     * Creates a new <code>MockSqlResourcePersistence</code> instance with
     * given <code>DBConnectionFactory</code> instance.
     * </p>
     * @param connectionFactory The connection factory for getting database
     *            connection.
     */
    public MockSqlResourcePersistence(DBConnectionFactory connectionFactory) {
        this(connectionFactory, null);
    }

    /**
     * <p>
     * Creates a new <code>MockSqlResourcePersistence</code> instance with
     * given <code>DBConnectionFactory</code> instance and connection name.
     * </p>
     * @param connectionFactory The connection factory for getting database
     *            connection.
     * @param connectionName The name of database connection.
     */
    public MockSqlResourcePersistence(DBConnectionFactory connectionFactory, String connectionName) {
        this.factory = connectionFactory;
        this.connectionName = connectionName;
    }

    /**
     * <p>
     * Adds the given Resource to the persistence. The resource must not already
     * exist (by id) in the persistence store.
     * </p>
     * @param resource The resource to add to the persistence.
     */
    public void addResource(Resource resource) {
        PreparedStatement statement = null;
        Connection conn = getConnection();

        String query = "INSERT INTO resource"
                + "(resource_id, resource_role_id, project_id, project_phase_id, create_user,"
                + " create_date, modify_user, modify_date)" + " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            statement = conn.prepareStatement(query);

            // set the parameters.
            statement.setLong(1, resource.getId());
            statement.setLong(2, resource.getResourceRole().getId());

            statement.setObject(3, resource.getProject());
            statement.setObject(4, resource.getPhase());

            statement.setString(5, resource.getCreationUser());
            statement.setTimestamp(6, convertDate(resource.getCreationTimestamp()));
            statement.setString(7, resource.getModificationUser());
            statement.setTimestamp(8, convertDate(resource.getModificationTimestamp()));

            // insert into datebase here.
            statement.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            // ignore.
        } finally {
            closeStatement(statement);
            setAutocommit(conn, true);
            closeConnection(conn);
        }
    }

    /**
     * <p>
     * Deletes the given Resource in the persistence store with its id. The
     * Resource must already be present in the persistence store, otherwise
     * nothing is done.
     * </p>
     * @param resource The resource to remove.
     */
    public void deleteResource(Resource resource) {
        PreparedStatement statement = null;
        Connection conn = getConnection();
        String query = "DELETE FROM resource WHERE resource_id = ?";

        try {
            statement = conn.prepareStatement(query);

            // set the parameters.
            statement.setLong(1, resource.getId());

            // delete from datebase here.
            statement.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            // ignore
        } finally {
            closeStatement(statement);
            setAutocommit(conn, true);
            closeConnection(conn);
        }
    }

    /**
     * <p>
     * Updates the given Resource in the persistence store with its currently
     * set information. The Resource must already be present in the persistence
     * store.
     * </p>
     * @param resource The resource to update.
     */
    public void updateResource(Resource resource) {
        PreparedStatement statement = null;
        Connection conn = getConnection();
        String query = "UPDATE resource SET resource_role_id = ?, project_id = ?, project_phase_id = ?,"
                + " modify_user = ?, modify_date = ? WHERE resource_id = ?";

        try {
            statement = conn.prepareStatement(query);

            // set the parameters.
            statement.setLong(1, resource.getResourceRole().getId());
            statement.setObject(2, resource.getProject());
            statement.setObject(3, resource.getPhase());
            statement.setString(4, resource.getModificationUser());
            statement.setTimestamp(5, convertDate(resource.getModificationTimestamp()));
            statement.setLong(6, resource.getId());

            // update into datebase here.
            statement.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            // ignore
        } finally {
            closeStatement(statement);
            closeConnection(conn);
        }
    }

    /**
     * <p>
     * Loads the resource from the persistence with the given id. Here it simply
     * return a new <code>Resource</code> instance here.
     * </p>
     * @param resourceId The id of the <code>Resource</code> instance to load.
     * @return The loaded <code>Resource</code> instance from persistence.
     */
    public Resource loadResource(long resourceId) {
        PreparedStatement statement = null;
        Connection conn = getConnection();
        ResultSet rs = null;
        String query = "SELECT resource_id, resource_role_id, project_id, project_phase_id,"
                + " create_user, create_date, modify_user, modify_date"
                + " FROM resource WHERE resource_id = ?";

        Resource resource = null;

        setAutocommit(conn, true);

        try {
            statement = conn.prepareStatement(query);
            statement.setLong(1, resourceId);
            rs = statement.executeQuery();

            if (rs.next()) {
                resource = constructResource(rs);
            }
        } catch (SQLException e) {
            // ignore
        } finally {
            closeResultSet(rs);
            closeStatement(statement);
            closeConnection(conn);
        }

        return resource;
    }

    /**
     * <p>
     * Adds a notification to the persistence store. A notification type with
     * the given ID must already exist in the persistence store, as must a
     * project.
     * </p>
     * @param user The user id to add as a notification.
     * @param project The project the notification is related to.
     * @param notificationType The id of the notification type.
     * @param operator The operator making the change.
     */
    public void addNotification(long user, long project, long notificationType, String operator) {
        Connection conn = getConnection();
        PreparedStatement statement = null;
        String query = "INSERT INTO notification"
                + "(project_id, external_ref_id, notification_type_id, "
                + "create_user, create_date, modify_user, modify_date)"
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            statement = conn.prepareStatement(query);

            // set the parameters.
            statement.setLong(1, project);
            statement.setLong(2, user);
            statement.setLong(3, notificationType);
            statement.setString(4, operator);

            statement.setTimestamp(5, convertDate(null));
            statement.setString(6, operator);
            statement.setTimestamp(7, convertDate(null));

            // add into datebase here.
            statement.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            // ignore.
        } finally {
            closeStatement(statement);
            setAutocommit(conn, true);
            closeConnection(conn);
        }
    }

    /**
     * <p>
     * Removes a notification from the persistence store. The given notification
     * tuple identifier (user, project, and notificationType) should already
     * exists in the persistence store, otherwise nothing will be done.
     * </p>
     * @param user The user id of the notification to remove.
     * @param project The project id of the notification to remove.
     * @param notificationType The notification type id of the notification to
     *            remove.
     * @param operator The operator making the change.
     */
    public void removeNotification(long user, long project, long notificationType, String operator) {
        PreparedStatement statement = null;
        Connection conn = getConnection();
        String query = "DELETE FROM notification WHERE project_id = ? AND external_ref_id = ? "
                + "AND notification_type_id = ?";

        try {
            statement = conn.prepareStatement(query);

            // set the parameters.
            statement.setLong(1, project);
            statement.setLong(2, user);
            statement.setLong(3, notificationType);

            // remove from datebase here.
            statement.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            // ignore.
        } finally {
            closeStatement(statement);
            setAutocommit(conn, true);
            closeConnection(conn);
        }
    }

    /**
     * <p>
     * Load the Notification for the given "id" triple from the persistence
     * store. Returns null if no entry in the persistence has the given user,
     * project, and notificationType.
     * </p>
     * @param user The id of the user
     * @param project The id of the project
     * @param notificationType The id of the notificationType
     * @return The loaded notification
     */
    public Notification loadNotification(long user, long project, long notificationType) {
        Connection conn = getConnection();

        ResultSet rs = null;
        PreparedStatement statement = null;

        String query = "SELECT project_id, external_ref_id, notification_type_id,"
                + " create_user, create_date, modify_user, modify_date"
                + " FROM notification WHERE project_id = ? AND external_ref_id = ? AND notification_type_id = ?";

        Notification notification = null;

        try {
            statement = conn.prepareStatement(query);
            statement.setLong(1, project);
            statement.setLong(2, user);
            statement.setLong(3, notificationType);

            rs = statement.executeQuery();

            if (rs.next()) {
                notification = consructNotification(rs);
            }
        } catch (SQLException e) {
            // ignore
        } finally {
            closeResultSet(rs);
            closeStatement(statement);
            closeConnection(conn);
        }

        return notification;
    }

    /**
     * <p>
     * Adds a notification type to the persistence store. The id of the
     * notification type must already be assigned to the NotificationType object
     * passed to this method, and not already exist in the persistence source.
     * </p>
     * @param notificationType The notification type to add.
     */
    public void addNotificationType(NotificationType notificationType) {
        Connection conn = getConnection();
        PreparedStatement statement = null;
        String query = "INSERT INTO notification_type_lu(notification_type_id, name, description, create_user, "
                + "create_date, modify_user, modify_date) VALUES(?, ?, ?, ?, ?, ?, ?)";

        try {
            statement = conn.prepareStatement(query);

            // set the parameters.
            statement.setLong(1, notificationType.getId());
            statement.setString(2, notificationType.getName());
            statement.setString(3, notificationType.getDescription());
            statement.setString(4, notificationType.getCreationUser());

            statement.setTimestamp(5, convertDate(notificationType.getCreationTimestamp()));
            statement.setString(6, notificationType.getModificationUser());
            statement.setTimestamp(7, convertDate(notificationType.getModificationTimestamp()));

            // add into datebase here.
            statement.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            // ignore.
        } finally {
            closeStatement(statement);
            closeConnection(conn);
        }
    }

    /**
     * <p>
     * Removes a notification type from the persistence (by id) store. If no
     * notification type exists with the id of the notification type, nothing is
     * done.
     * </p>
     * @param notificationType The notification type to delete.
     */
    public void deleteNotificationType(NotificationType notificationType) {
        Connection conn = getConnection();
        PreparedStatement statement = null;
        String query = "DELETE FROM notification_type_lu WHERE notification_type_id = ?";

        try {
            statement = conn.prepareStatement(query);

            // set the parameters.
            statement.setLong(1, notificationType.getId());

            // delete from datebase here.
            statement.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            // ignore
        } finally {
            closeStatement(statement);
            closeConnection(conn);
        }
    }

    /**
     * <p>
     * Updates the notification type in the persistence store. The notification
     * type (by id) must exist in the persistence store.
     * </p>
     * @param notificationType The notification type to update.
     */
    public void updateNotificationType(NotificationType notificationType) {
        PreparedStatement statement = null;
        Connection conn = getConnection();
        String query = "UPDATE notification_type_lu SET name = ?, description = ?, modify_user = ?, modify_date = ? "
                + "WHERE notification_type_id = ?";

        try {
            statement = conn.prepareStatement(query);

            statement.setString(1, notificationType.getName());
            statement.setString(2, notificationType.getDescription());
            statement.setString(3, notificationType.getModificationUser());
            statement.setTimestamp(4, convertDate(notificationType.getModificationTimestamp()));
            statement.setLong(5, notificationType.getId());

            // update into datebase here.
            statement.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            // ignore
        } finally {
            closeStatement(statement);
            closeConnection(conn);
        }
    }

    /**
     * <p>
     * Loads the notification type from the persistence with the given id.
     * Returns null if there is no notification type with the given id.
     * </p>
     * @param notificationTypeId The id of the notification type to load
     * @return The loaded notification type.
     */
    public NotificationType loadNotificationType(long notificationTypeId) {
        PreparedStatement statement = null;
        Connection conn = getConnection();
        ResultSet rs = null;
        String query = "SELECT notification_type_id, name, description, create_user, "
                + "create_date, modify_user, modify_date"
                + " FROM notification_type_lu WHERE notification_type_id = ?";

        NotificationType type = null;

        try {
            statement = conn.prepareStatement(query);
            statement.setLong(1, notificationTypeId);
            rs = statement.executeQuery();

            if (rs.next()) {
                type = consructNotificationType(rs);
            }
        } catch (SQLException e) {
            // ignore
        } finally {
            closeResultSet(rs);
            closeStatement(statement);
            closeConnection(conn);
        }

        return type;
    }

    /**
     * <p>
     * Adds a resource role to the persistence store. The id of the resource
     * role must already be assigned to the notificationType object passed to
     * this method, and not already exist in the persistence source.
     * </p>
     * @param resourceRole The resource role to add.
     */
    public void addResourceRole(ResourceRole resourceRole) {
        PreparedStatement statement = null;
        Connection conn = getConnection();
        String query = "INSERT INTO resource_role_lu(resource_role_id, name, "
                + "description, phase_type_id, create_user, create_date, modify_user, modify_date)"
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            statement = conn.prepareStatement(query);
            statement.setLong(1, resourceRole.getId());
            statement.setString(2, resourceRole.getName());
            statement.setString(3, resourceRole.getDescription());
            statement.setObject(4, resourceRole.getPhaseType());

            statement.setString(5, resourceRole.getCreationUser());
            statement.setTimestamp(6, convertDate(resourceRole.getCreationTimestamp()));
            statement.setString(7, resourceRole.getModificationUser());
            statement.setTimestamp(8, convertDate(resourceRole.getModificationTimestamp()));

            // insert into datebase here.
            statement.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            // ignore
        } finally {
            closeStatement(statement);
            closeConnection(conn);
        }
    }

    /**
     * <p>
     * Removes a resource role from the persistence store. If no resource role
     * exists with the given id, nothing is done.
     * </p>
     * @param resourceRole The notification type to delete.
     */
    public void deleteResourceRole(ResourceRole resourceRole) {
        PreparedStatement statement = null;
        Connection conn = getConnection();
        String query = "DELETE FROM resource_role_lu WHERE resource_role_id = ?";

        try {
            statement = conn.prepareStatement(query);

            // set the parameters.
            statement.setLong(1, resourceRole.getId());

            // delete from datebase here.
            statement.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            // ignore
        } finally {
            closeStatement(statement);
            closeConnection(conn);
        }
    }

    /**
     * <p>
     * Updates the resource role in the persistence store. The resource role (by
     * id) must exist in the persistence store.
     * </p>
     * @param resourceRole The resource role to update.
     */
    public void updateResourceRole(ResourceRole resourceRole) {
        PreparedStatement statement = null;
        Connection conn = getConnection();
        String query = "UPDATE resource_role_lu SET phase_type_id = ?, name = ?, "
                + "description = ?, modify_user = ?, modify_date = ? WHERE resource_role_id = ?";

        try {
            statement = conn.prepareStatement(query);

            statement.setObject(1, resourceRole.getPhaseType());
            statement.setString(2, resourceRole.getName());
            statement.setString(3, resourceRole.getDescription());
            statement.setString(4, resourceRole.getModificationUser());
            statement.setTimestamp(5, convertDate(resourceRole.getModificationTimestamp()));
            statement.setLong(6, resourceRole.getId());

            // update into datebase here.
            statement.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            // ignore
        } finally {
            closeStatement(statement);
            closeConnection(conn);
        }
    }

    /**
     * <p>
     * Loads the resource role from the persistence with the given id. Returns
     * null if there is no resource role with the given id.
     * </p>
     * @param resourceId The id of the resource role to load
     * @return The loaded resource role.
     */
    public ResourceRole loadResourceRole(long resourceId) {
        PreparedStatement statement = null;
        Connection conn = getConnection();
        ResultSet rs = null;
        String query = "SELECT resource_role_id, phase_type_id, name, description, create_user,"
                + " create_date, modify_user, modify_date"
                + " FROM resource_role_lu WHERE resource_role_id = ?";

        ResourceRole role = null;

        try {
            statement = conn.prepareStatement(query);
            statement.setLong(1, resourceId);
            rs = statement.executeQuery();

            if (rs.next()) {
                role = constructResourceRole(rs);
            }
        } catch (SQLException e) {
            // ignore
        } finally {
            closeResultSet(rs);
            closeStatement(statement);
            closeConnection(conn);
        }

        return role;
    }

    /**
     * <p>
     * Loads the resources from the persistence with the given ids. May return a
     * zero-length array. This method is designed to keep the amount of SQL
     * queries to a minimum when searching resources.
     * </p>
     * @param resourceIds The ids of resources to load.
     * @return Theloaded resources.
     */
    public Resource[] loadResources(long[] resourceIds) {
        List resources = new ArrayList();

        for (int i = 0; i < resourceIds.length; i++) {
            Resource resource = loadResource(resourceIds[i]);

            if (resource != null) {
                resources.add(resource);
            }
        }

        return (Resource[]) resources.toArray(new Resource[0]);
    }

    /**
     * <p>
     * loadNotificationTypes: Loads the notification types from the persistence
     * with the given ids. May return a 0-length array.
     * </p>
     * @param notificationTypeIds The ids of notification types to load.
     * @return The loaded notification types.
     */
    public NotificationType[] loadNotificationTypes(long[] notificationTypeIds) {
        List types = new ArrayList();

        for (int i = 0; i < notificationTypeIds.length; i++) {
            NotificationType type = loadNotificationType(notificationTypeIds[i]);

            if (type != null) {
                types.add(type);
            }
        }

        return (NotificationType[]) types.toArray(new NotificationType[0]);
    }

    /**
     * <p>
     * loadResourceRoles: Loads the resource roles from the persistence with the
     * given ids. May return a 0-length array.
     * </p>
     * @param resourceRoleIds The ids of resource roles to load
     * @return The loaded resource roles
     */
    public ResourceRole[] loadResourceRoles(long[] resourceRoleIds) {
        List resourceRoles = new ArrayList();

        for (int i = 0; i < resourceRoleIds.length; i++) {
            ResourceRole role = loadResourceRole(resourceRoleIds[i]);

            if (role != null) {
                resourceRoles.add(role);
            }
        }

        return (ResourceRole[]) resourceRoles.toArray(new ResourceRole[0]);
    }

    /**
     * <p>
     * Load the Notifications for the given "id" triples from the persistence
     * store. May return a 0-length array
     * </p>
     * @param userIds The ids of the users
     * @param projectId The ids of the projects
     * @param notificationTypes The ids of the notification types
     * @return The loaded notifications
     */
    public Notification[] loadNotifications(long[] userIds, long[] projectId, long[] notificationTypes) {
        List notifications = new ArrayList();

        for (int i = 0; i < userIds.length; i++) {
            Notification notification = loadNotification(userIds[i], projectId[i], notificationTypes[i]);

            if (notification != null) {
                notifications.add(notification);
            }
        }

        return (Notification[]) notifications.toArray(new Notification[0]);
    }

    /**
     * <p>
     * Construct a Resource instance from given ResultSet instance.
     * </p>
     * @param rs the ResultSet instance.
     * @return The Resource instance.
     */
    private Resource constructResource(ResultSet rs) {
        Resource resource = new Resource();

        try {
            resource.setId(rs.getLong(1));

            resource.setResourceRole(new ResourceRole(rs.getLong(2)));

            if (rs.getObject(3) == null) {
                resource.setProject(null);
            } else {
                resource.setProject(new Long(rs.getLong(3)));
            }

            if (rs.getObject(4) == null) {
                resource.setPhase(null);
            } else {
                resource.setPhase(new Long(rs.getLong(4)));
            }

            resource.setCreationUser(rs.getString(5));
            resource.setCreationTimestamp(rs.getDate(6));
            resource.setModificationUser(rs.getString(7));
            resource.setModificationTimestamp(rs.getTimestamp(8));
        } catch (SQLException e) {
            // ignore
        }

        return resource;
    }

    /**
     * <p>
     * Construct the Notification instance from the ResultSet instance.
     * </p>
     * @param rs the ResultSet instance.
     * @return the constructed Notification instance.
     */
    private Notification consructNotification(ResultSet rs) {
        Notification notification = null;

        try {
            NotificationType type = new NotificationType(rs.getLong(3));

            notification = new Notification(rs.getLong(1), type, rs.getLong(2));

            notification.setCreationUser(rs.getString(4));
            notification.setCreationTimestamp(rs.getTimestamp(5));
            notification.setModificationUser(rs.getString(6));
            notification.setModificationTimestamp(rs.getTimestamp(7));
        } catch (SQLException e) {
            // ignore.
        }

        return notification;
    }

    /**
     * <p>
     * Construct the NotificationType instance from the ResultSet instance.
     * </p>
     * @param rs the ResultSet instance.
     * @return the constructed NotificationType instance.
     */
    private NotificationType consructNotificationType(ResultSet rs) {
        NotificationType type = null;

        try {
            type = new NotificationType();
            type.setId(rs.getInt(1));
            type.setName(rs.getString(2));
            type.setDescription(rs.getString(3));
            type.setCreationUser(rs.getString(4));
            type.setCreationTimestamp(rs.getTimestamp(5));
            type.setModificationUser(rs.getString(6));
            type.setModificationTimestamp(rs.getTimestamp(7));
        } catch (SQLException e) {
            // ignore.
        }

        return type;
    }

    /**
     * Construct the ResourceRole instance from the given ResultSet instance.
     * @param rs the ResultSet instance.
     * @return the ResourceRole instance.
     */
    private ResourceRole constructResourceRole(ResultSet rs) {
        ResourceRole role = new ResourceRole();

        try {
            role.setId(rs.getLong(1));

            if (rs.getObject(2) == null) {
                role.setPhaseType(null);
            } else {
                role.setPhaseType(new Long(rs.getLong(2)));
            }

            role.setName(rs.getString(3));
            role.setDescription(rs.getString(4));
            role.setCreationUser(rs.getString(5));
            role.setCreationTimestamp(rs.getTimestamp(6));
            role.setModificationUser(rs.getString(7));
            role.setModificationTimestamp(rs.getTimestamp(8));
        } catch (SQLException e) {
            // ignore.
        }

        return role;
    }

    /**
     * <p>
     * Create a jdbc connection to the datebase.
     * </p>
     * @return the connection of the database.
     */
    private Connection getConnection() {
        Connection connection = null;

        try {
            if (connectionName == null) {
                connection = factory.createConnection();
            } else {
                connection = factory.createConnection(connectionName);
            }

            setAutocommit(connection, false);
        } catch (DBConnectionException e) {
            // ignore
        }

        return connection;
    }

    /**
     * <p>
     * Closes the given prepared statement silently.
     * </p>
     * @param ps the prepared statement to close.
     */
    private void closeStatement(PreparedStatement ps) {
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                // ignore
            }
        }
    }

    /**
     * <p>
     * Closes the given connection silently.
     * </p>
     * @param conn the connection to close.
     */
    private void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                // ignore
            }
        }
    }

    /**
     * <p>
     * Sets the given connection's auto commit.
     * </p>
     * @param conn the connection to close.
     * @param commit the auto commit.
     */
    private void setAutocommit(Connection conn, boolean commit) {
        if (conn != null) {
            try {
                conn.setAutoCommit(commit);
            } catch (SQLException e) {
                // ignore
            }
        }
    }

    /**
     * <p>
     * Closes the given resultSet silently.
     * </p>
     * @param rs the resultSet to close.
     */
    private void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                // ignore
            }
        }
    }

    /**
     * This method to convert a <code>java.util.Date</code> instance to
     * <code>Timestamp</code> instance.
     * @param date the <code>java.util.Date</code> to convert.
     * @return the corresponding <code>java.sql.Date</code> instance.
     */
    private static Timestamp convertDate(Date date) {
        if (date == null) {
            date = new Date();
        }

        return new Timestamp(date.getTime());
    }
}
