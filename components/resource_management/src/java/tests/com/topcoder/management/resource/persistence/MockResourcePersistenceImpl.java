/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import com.topcoder.db.connectionfactory.ConfigurationException;
import com.topcoder.db.connectionfactory.DBConnectionException;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.management.resource.Notification;
import com.topcoder.management.resource.NotificationType;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.resource.ResourceRole;

/**
 * This is a mocked-up implementation of interface ResourcePersistence.
 *
 * This class is for unit tests only.
 *
 * @author kinfkong
 * @version 1.0
 */
public class MockResourcePersistenceImpl implements ResourcePersistence {

    /**
     * <p>
     * Adds the given Resource to the persistence store. The
     * resource must not already exist (by id) in the persistence store.
     * </p>
     *
     * @param resource The resource to add to the persistence store
     *
     */
    public void addResource(Resource resource) {
        Connection con = null;
        try {
            con = getConnection();
            String sql = "INSERT INTO resource"
                + " (resource_id, resource_role_id, project_id, create_user, create_date, modify_user, modify_date)"
                + " VALUES(?, ?, ?, ?, ?, ?, ?)";
            // System.out.println(resource.getId());
            doSQLUpdate(con, sql, new Object[] {
                new Long(resource.getId()),
                new Long(resource.getResourceRole().getId()),
                resource.getProject(),
                resource.getCreationUser(),
                resource.getCreationTimestamp(),
                resource.getModificationUser(),
                resource.getModificationTimestamp()
            });
            con.commit();
            closeConnection(con);
        } catch (SQLException e) {
            // ignore
        }
    }

    /**
     * <p>
     * Deletes the given Resource (by id) in the persistence
     * store. The Resource must already be present in the persistence store,
     * otherwise nothing is done.
     * </p>
     *
     * @param resource The resource to remove
     *
     * @throws UnsupportedOperationException always
     */
    public void deleteResource(Resource resource) {
        Connection con = null;
        try {
            con = getConnection();
            String sql = "DELETE FROM resource WHERE resource_id = ?";
            doSQLUpdate(con, sql, new Object[] {
                new Long(resource.getId()),
            });
            con.commit();
            closeConnection(con);
        } catch (SQLException e) {
            // ignore
        }
    }

    /**
     * <p>
     * Updates the given Resource in the persistence store with
     * its currently set information. The Resource must already be present in
     * the persistence store. The operator information should already have been
     * put in the modification date / modification user properties of the
     * Resource.
     * </p>
     *
     * @param resource The resource to update
     *
     */
    public void updateResource(Resource resource) {
        Connection con = null;
        try {
            con = getConnection();
            String sql = "UPDATE resource SET modify_user = ? WHERE resource_id = ?";
            doSQLUpdate(con, sql, new Object[] {
                resource.getModificationUser(),
                new Long(resource.getId())
            });
            con.commit();
            closeConnection(con);
        } catch (SQLException e) {
            // ignore
        }
    }

    /**
     * <p>
     * Loads the resource from the persistence with the given id.
     * Returns null if there is no resource for the given id.
     * </p>
     *
     * @param resourceId The id of the Resource to load
     *
     * @return The loaded Resource
     *
     */
    public Resource loadResource(long resourceId) {
        // mock implementation
        return new Resource(resourceId);
    }

    /**
     * <p>
     * Adds a notification to the persistence store. A
     * notification type with the given ID must already exist in the persistence
     * store, as must a project.
     * </p>
     *
     * @param user The user id to add as a notification
     * @param project The project the notification is related to
     * @param notificationType The id of the notification type
     * @param operator The operator making the change
     *
     */
    public void addNotification(long user, long project, long notificationType, String operator) {
        Connection con = null;
        try {
            con = getConnection();
            String sql = "INSERT INTO notification (project_id, external_ref_id, "
                + " notification_type_id, create_user, create_date, modify_user, modify_date)"
                + " VALUES(?, ?, ?, ?, CURRENT, ?, CURRENT)";

            doSQLUpdate(con, sql, new Object[] {
                new Long(project),
                new Long(user),
                new Long(notificationType),
                operator,
                operator
            });
            con.commit();
            closeConnection(con);
        } catch (SQLException e) {
            // ignore
        }
    }

    /**
     * <p>
     * Removes a notification from the persistence store.
     * The given notification tuple identifier (user, project, and
     * notificationType) should already exists in the persistence store,
     * otherwise nothing will be done.
     * </p>
     *
     * @param user The user id of the notification to remove
     * @param project The project id of the notification to remove
     * @param notificationType The notification type id of the notification to remove
     * @param operator The operator making the change
     *
     */
    public void removeNotification(long user, long project, long notificationType, String operator) {
        Connection con = null;
        try {
            con = getConnection();
            String sql = "DELETE FROM notification WHERE project_id = ? AND"
                + " notification_type_id = ? AND external_ref_id = ?";
            doSQLUpdate(con, sql, new Object[] {
                new Long(project),
                new Long(notificationType),
                new Long(user),
            });
            con.commit();
            closeConnection(con);
        } catch (SQLException e) {
            // ignore
        }
    }

    /**
     * <p>
     * Load the Notification for the given "id" triple from
     * the persistence store. Returns null if no entry in the persistence has
     * the given user, project, and notificationType.
     * </p>
     *
     * @return The loaded notification
     * @param user The id of the user
     * @param project The id of the project
     * @param notificationType The id of the notificationType
     *
     */
    public Notification loadNotification(long user, long project, long notificationType) {
        Notification notification = new Notification(project, new NotificationType(notificationType), user);
        return notification;
    }

    /**
     * <p>
     * Adds a notification type to the persistence store.
     * The id of the notification type must already be assigned to the
     * NotificationType object passed to this method, and not already exist in
     * the persistence source.
     * </p>
     *
     * @param notificationType The notification type to add
     *
     */
    public void addNotificationType(NotificationType notificationType) {
        Connection con = null;
        try {
            con = getConnection();
            String sql = "INSERT INTO notification_type_lu"
                + " (notification_type_id, name, description, create_user, create_date, modify_user, modify_date)"
                + " VALUES(?, ?, ?, ?, ?, ?, ?)";
            doSQLUpdate(con, sql, new Object[] {
                new Long(notificationType.getId()),
                notificationType.getName(),
                notificationType.getDescription(),
                notificationType.getCreationUser(),
                notificationType.getCreationTimestamp(),
                notificationType.getModificationUser(),
                notificationType.getModificationTimestamp()
            });
            con.commit();
            closeConnection(con);
        } catch (SQLException e) {
            // ignore
        }
    }

    /**
     * <p>
     * Removes a notification type from the persistence
     * (by id) store. If no notification type exists with the id of the
     * notification type, nothing is done.
     * </p>
     *
     * @param notificationType The notification type to delete
     *
     */
    public void deleteNotificationType(NotificationType notificationType) {
        Connection con = null;
        try {
            con = getConnection();
            String sql = "DELETE FROM notification_type_lu WHERE notification_type_id = ?";
            doSQLUpdate(con, sql, new Object[] {
                new Long(notificationType.getId()),
            });
            con.commit();
            closeConnection(con);
        } catch (SQLException e) {
            // ignore
        }
    }

    /**
     * <p>
     * updateNotificationType: Updates the notification type in the persistence
     * store. The notification type (by id) must exist in the persistence store.
     * </p>
     *
     * @param notificationType The notification type to update
     *
     * @throws UnsupportedOperationException always
     */
    public void updateNotificationType(NotificationType notificationType) {
        Connection con = null;
        try {
            con = getConnection();
            String sql = "UPDATE notification_type_lu SET modify_user = ? WHERE notification_type_id = ?";
            doSQLUpdate(con, sql, new Object[] {
                notificationType.getModificationUser(),
                new Long(notificationType.getId())
            });
            con.commit();
            closeConnection(con);
        } catch (SQLException e) {
            // ignore
        }
    }

    /**
     * <p>
     * Loads the notification type from the persistence
     * with the given id. Returns null if there is no notification type with the
     * given id.
     * </p>
     * @param notificationTypeId The id of the notification type to load
     *
     * @return The loaded notification type
     *
     * @throws UnsupportedOperationException always
     */
    public NotificationType loadNotificationType(long notificationTypeId) {
        throw new UnsupportedOperationException("This method is not supported yet.");
    }

    /**
     * <p>
     * Adds a resource role to the persistence store. The id of
     * the resource role must already be assigned to the notificationType object
     * passed to this method, and not already exist in the persistence source.
     * </p>
     *
     * @param resourceRole The resource role to add
     *
     * @throws UnsupportedOperationException always
     */
    public void addResourceRole(ResourceRole resourceRole) {
        Connection con = null;
        try {
            con = getConnection();
            String sql = "INSERT INTO resource_role_lu"
                + " (resource_role_id, phase_type_id, name, description,"
                + " create_user, create_date, modify_user, modify_date)"
                + " VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
            doSQLUpdate(con, sql, new Object[] {
                new Long(resourceRole.getId()),
                resourceRole.getPhaseType(),
                resourceRole.getName(),
                resourceRole.getDescription(),
                resourceRole.getCreationUser(),
                resourceRole.getCreationTimestamp(),
                resourceRole.getModificationUser(),
                resourceRole.getModificationTimestamp()
            });
            con.commit();
            closeConnection(con);
        } catch (SQLException e) {
            // ignore
        }
    }

    /**
     * <p>
     * Removes a resource role from the persistence store.
     * If no resource role exists with the given id, nothing is done.
     * </p>
     *
     * @param resourceRole The notification type to delete.
     *
     */
    public void deleteResourceRole(ResourceRole resourceRole) {
        Connection con = null;
        try {
            con = getConnection();
            String sql = "DELETE FROM resource_role_lu WHERE resource_role_id = ?";
            doSQLUpdate(con, sql, new Object[] {
                new Long(resourceRole.getId()),
            });
            con.commit();
            closeConnection(con);
        } catch (SQLException e) {
            // ignore
        }
    }

    /**
     * <p>
     * Updates the resource role in the persistence store.
     * The resource role (by id) must exist in the persistence store.
     * </p>
     *
     * @param resourceRole The resource role to update
     *
     */
    public void updateResourceRole(ResourceRole resourceRole) {
        Connection con = null;
        try {
            con = getConnection();
            String sql = "UPDATE resource_role_lu SET modify_user = ? WHERE resource_role_id = ?";
            doSQLUpdate(con, sql, new Object[] {
                resourceRole.getModificationUser(),
                new Long(resourceRole.getId())
            });
            con.commit();
            closeConnection(con);
        } catch (SQLException e) {
            // ignore
        }
    }

    /**
     * <p>
     * Loads the resource role from the persistence with the
     * given id. Returns null if there is no resource role with the given id.
     * </p>
     *
     * @param resourceId The id of the resource role to load
     * @return The loaded resource role
     *
     * @throws UnsupportedOperationException always
     */
    public ResourceRole loadResourceRole(long resourceId) {
        throw new UnsupportedOperationException("This method is not supported yet.");
    }

    /**
     * <p>
     * Loads the resources from the persistence with the given
     * ids. May return a zero-length array. This method is designed to keep the
     * amount of SQL queries to a minimum when searching resources.
     * </p>
     *
     * @param resourceIds The ids of resources to load
     * @return Theloaded resources
     *
     */
    public Resource[] loadResources(long[] resourceIds) {
        Resource[] resources = new Resource[resourceIds.length];
        for (int i = 0; i < resources.length; i++) {
            resources[i] = new Resource(resourceIds[i]);
        }
        return resources;
    }

    /**
     * <p>
     * loadNotificationTypes: Loads the notification types from the persistence
     * with the given ids. May return a 0-length array.
     * </p>
     *
     * @param notificationTypeIds The ids of notification types to load
     * @return The loaded notification types
     * @throws UnsupportedOperationException always
     */
    public NotificationType[] loadNotificationTypes(long[] notificationTypeIds) {
        NotificationType[] notificationTypes = new NotificationType[notificationTypeIds.length];
        for (int i = 0; i < notificationTypeIds.length; i++) {
            notificationTypes[i] = new NotificationType(notificationTypeIds[i]);
        }
        return notificationTypes;
    }

    /**
     * <p>
     * loadResourceRoles: Loads the resource roles from the persistence with the
     * given ids. May return a 0-length array.
     * </p>
     *
     * @param resourceRoleIds The ids of resource roles to load
     *
     * @return The loaded resource roles
     *
     */
    public ResourceRole[] loadResourceRoles(long[] resourceRoleIds) {
        ResourceRole[] resourceRoles = new ResourceRole[resourceRoleIds.length];
        for (int i = 0; i < resourceRoles.length; i++) {
            resourceRoles[i] = new ResourceRole(resourceRoleIds[i]);
        }
        return resourceRoles;
    }

    /**
     * <p>
     * Load the Notifications for the given "id" triples from
     * the persistence store. May return a 0-length array
     * </p>
     *
     * @param userIds The ids of the users
     * @param projectId The ids of the projects
     * @param notificationTypes The ids of the notification types
     *
     * @return The loaded notifications
     *
     */
    public Notification[] loadNotifications(long[] userIds, long[] projectId, long[] notificationTypes) {
        int n = userIds.length;
        Notification[] notifications = new Notification[n];
        for (int i = 0; i < n; i++) {
            notifications[i] = loadNotification(userIds[i], projectId[i], notificationTypes[i]);
        }
        return notifications;
    }

    /**
     * Does the update operation to the database.
     *
     * @param con the connection
     * @param sql the sql statement to execute
     * @param args the arguments to be set into the database
     */
    private void doSQLUpdate(Connection con, String sql, Object[] args) {
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                setElement(ps, i + 1, args[i]);
            }
            ps.executeUpdate();
        } catch (SQLException e) {
            // ignore
            // e.printStackTrace();
        } finally {
            closeStatement(ps);
        }
    }

    /**
     * Sets the element to the prepared statement with given index.
     *
     * @param ps the prepared statement
     * @param index the index to set
     * @param obj the value of the argument
     *
     * @throws SQLException if any error occurs
     */
    private void setElement(PreparedStatement ps, int index, Object obj) throws SQLException {
        if (obj instanceof String) {
            ps.setString(index, (String) obj);
        } else if (obj instanceof Integer) {
            ps.setInt(index, ((Integer) obj).intValue());
        } else if (obj instanceof Long) {
            ps.setLong(index, ((Long) obj).longValue());
        } else if (obj instanceof Timestamp) {
            ps.setTimestamp(index, (Timestamp) obj);
        } else if (obj instanceof Date) {
            ps.setDate(index, new java.sql.Date(((Date) obj).getTime()));
        } else if (obj == null) {
            ps.setNull(index, java.sql.Types.INTEGER);
        }  else {
            throw new IllegalArgumentException("The element type is not supported yet.");
        }
    }

    /**
     * Closes a prepared statement silently.
     *
     * @param ps the prepared statement to close
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
     * Closes a connection silently.
     *
     * @param con the connection to close
     */
    private void closeConnection(Connection con) {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                // ignore
            }
        }
    }

    /**
     * Returns a connection.
     *
     * @return the connection
     *
     */
    private Connection getConnection() {
        Connection con = null;
        try {
            String dbNamespace = "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl";
            DBConnectionFactory factory = new DBConnectionFactoryImpl(dbNamespace);
            con = factory.createConnection();
            con.setAutoCommit(false);
        } catch (DBConnectionException e) {
            // ignore
        } catch (SQLException e) {
            // ignore
        } catch (ConfigurationException e) {
            // ignore
        }
        return con;
    }

}
