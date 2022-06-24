/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource.persistence.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.management.resource.Notification;
import com.topcoder.management.resource.NotificationType;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.resource.ResourceRole;
import com.topcoder.management.resource.persistence.ResourcePersistence;
import com.topcoder.management.resource.persistence.ResourcePersistenceException;
import com.topcoder.management.resource.persistence.logging.LogMessage;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;
import com.topcoder.util.sql.databaseabstraction.CustomResultSet;
import com.topcoder.util.sql.databaseabstraction.InvalidCursorStateException;
import com.topcoder.util.sql.databaseabstraction.NullColumnValueException;


/**
 * <p>
 * The <code>AbstractResourcePersistence</code> class implements the <code>ResourcePersistence</code>
 * interface, in order to persist to the database.
 * </p>
 * <p>
 * <i>Changes from 1.0.1 : </i> It contains most of the logic that was in the
 * <code>SqlResourcePersistence</code> class in version 1.0.1. This class does not cache a
 * <code>Connection</code> to the database. Instead, it uses the concrete implementation of the
 * <code>{@linkplain #openConnection()}</code> method of whatever subclass is in use to acquire and open the
 * Connection. After the queries are executed and the result sets processed, it uses the
 * <code>{@linkplain #closeConnection(Connection)}</code> to dispose of the connection. If the operation
 * fails, <code>{@linkplain #closeConnectionOnError(Connection)}</code> is called instead. This allows the
 * transaction handling logic to be implemented in subclasses while the Statements, queries, and ResultSets
 * are handled in the abstract class.
 * </p>
 * <p>
 * Most methods in this class will just create and execute a single <code>PreparedStatement</code>.
 * However, some of the Resource related methods need to execute several PreparedStatments in order to
 * accomplish the update/insertion/deletion of the resource.
 * </p>
 *
 * <p>
 * <i>Version 1.2 Changes:</i> Please note that all the changes in version 1.2 revolve around the changes to
 * the association multiplicity between Resource and Submission which used to be 1 to 1 and is not 1 to N
 * (i.e. a resource can be associated with multiple submissions)
 * </p>
 * <p>
 *
 * <p>
 * <i>Version 1.2.2 (Configurable Contest Terms Release Assembly v2.0) Changes:</i> Audit information was added to
 * when a resource is added, deleted or changes its user or role.
 * </p>
 * <p>
 *
 * <b>Thread Safety</b> : This class is immutable and thread-safe in the sense that multiple threads can not
 * corrupt its internal data structures. However, the results if used from multiple threads can be
 * unpredictable as the database is changed from different threads. This can equally well occur when the
 * component is used on multiple machines or multiple instances are used, so this is not a thread-safety
 * concern.
 * </p>
 *
 * @author aubergineanode
 * @author Chenhong
 * @author bendlund
 * @author mittu
 * @author AleaActaEst
 * @author George1
 * @author pulky
 * @version 1.2.2
 * @since 1.1
 */
public abstract class AbstractResourcePersistence implements ResourcePersistence {
	/** Logger instance using the class name as category */
    private static final Log LOGGER = LogManager.getLog(AbstractResourcePersistence.class.getName()); 
    
    /**
     * <p>
     * Represents the sql to get the external properties.
     * </p>
     */
    private static final String SQL_SELECT_EXT_PROPS =
        "SELECT resource_info.resource_id, " + "resource_info_type_lu.name, resource_info.value "
            + "FROM resource_info INNER JOIN resource_info_type_lu ON "
            + "(resource_info.resource_info_type_id = resource_info_type_lu.resource_info_type_id) "
            + "WHERE resource_id IN (";

    /**
     * <p>
     * Represents the sql to get the notification types.
     * </p>
     */
    private static final String SQL_SELECT_NOTIFICATION_TYPES =
        "SELECT notification_type_id, name, description,"
            + " create_user, create_date, modify_user, modify_date"
            + " FROM notification_type_lu WHERE notification_type_id IN (";

    /**
     * <p>
     * Represents the sql to get the resource roles.
     * </p>
     */
    private static final String SQL_SELECT_RES_ROLES =
        "SELECT resource_role_id, phase_type_id, name, description,"
            + " create_user, create_date, modify_user, modify_date"
            + " FROM resource_role_lu WHERE resource_role_id IN (";

    /**
     * <p>
     * Represents the sql to get the notifications.
     * </p>
     */
    private static final String SQL_SELECT_NOTIFICATIONS =
        "SELECT project_id, external_ref_id," + " notification_type_id, create_user, "
            + " create_date, modify_user, modify_date FROM notification WHERE ";

    /**
     * <p>
     * Represents the sql to get all resources.
     * </p>
     */
    private static final String SQL_SELECT_ALL_RES =
        "SELECT resource.resource_id, resource_role_id, project_id,"
            + " project_phase_id, user_id, resource.create_user, resource.create_date, resource.modify_user, "
            + " resource.modify_date" + " FROM resource WHERE resource.resource_id IN (";

    /**
     * <p>
     * Represents the sql to get all resources with role.
     * </p>
     */
    private static final String SQL_SELECT_ALL_RES_WITH_ROLE =
        "SELECT r.resource_id, r.project_id, r.project_phase_id, r.user_id, r.create_user, r.create_date, r.modify_user,"
            + " r.modify_date, rr.resource_role_id, rr.phase_type_id, rr.name AS rr_name, rr.description AS rr_description,"
            + " rr.create_user as rr_create_user, rr.create_date as rr_create_date, rr.modify_user as rr_modify_user,"
            + " rr.modify_date as rr_modify_date FROM resource r, resource_role_lu rr"
                + " WHERE r.resource_role_id = rr.resource_role_id AND r.resource_id IN (";

    /**
     * <p>
     * Represents the sql for loading resource roles.
     * </p>
     */
    private static final String SQL_SELECT_RES_ROLE =
        "SELECT resource_role_id, phase_type_id, name, description,"
            + " create_user, create_date, modify_user, modify_date FROM resource_role_lu WHERE resource_role_id = ?";

    /**
     * <p>
     * Represents the sql for updating resource role.
     * </p>
     */
    private static final String SQL_UPDATE_RES_ROLE =
        "UPDATE resource_role_lu SET phase_type_id = ?, name = ?, "
            + "description = ?, modify_user = ?, modify_date = ? WHERE resource_role_id = ?";

    /**
     * <p>
     * Represents the sql for deleting resource role.
     * </p>
     */
    private static final String SQL_DELETE_RES_ROLE =
        "DELETE FROM resource_role_lu WHERE resource_role_id = ?";

    /**
     * <p>
     * Represents the sql for updating notification type.
     * </p>
     */
    private static final String SQL_UPDATE_NOTIFICATION_TYPE =
        "UPDATE notification_type_lu SET name = ?,"
            + " description = ?, modify_user = ?, modify_date = ?  WHERE notification_type_id = ?";

    /**
     * <p>
     * Represents the sql for deleting notification type.
     * </p>
     */
    private static final String SQL_DELETE_NOTIFICATION_TYPE =
        "DELETE FROM notification_type_lu " + "WHERE notification_type_id = ?";

    /**
     * <p>
     * Represents the sql for inserting notification type.
     * </p>
     */
    private static final String SQL_INSERT_NOTIFICATION_TYPE =
        "INSERT INTO notification_type_lu (" + "notification_type_id, name, description, create_user, "
            + "create_date, modify_user, modify_date) VALUES(?, ?, ?, ?, ?, ?, ?)";

    /**
     * <p>
     * Represents the sql for inserting resource role.
     * </p>
     */
    private static final String SQL_INSERT_RES_ROLE =
        "INSERT INTO resource_role_lu(resource_role_id, name, "
            + "description, phase_type_id, create_user, create_date, modify_user, modify_date)"
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    /**
     * <p>
     * Represents the sql for loading notification type.
     * </p>
     */
    private static final String SQL_SELECT_NOTIFICATION_TYPE =
        "SELECT notification_type_id, name, description,"
            + " create_user, create_date, modify_user, modify_date"
            + " FROM notification_type_lu WHERE notification_type_id = ?";

    /**
     * <p>
     * Represents the sql for loading notification.
     * </p>
     */
    private static final String SQL_SELECT_NOTIFICATION =
        "SELECT project_id, external_ref_id, "
            + " notification_type_id, create_user, create_date, modify_user, modify_date"
            + " FROM notification WHERE project_id = ? AND external_ref_id = ? AND notification_type_id = ?";

    /**
     * <p>
     * Represents the sql for deleting notification.
     * </p>
     */
    private static final String SQL_DELETE_NOTIFICATION =
        "DELETE FROM notification WHERE project_id = ? "
            + "AND external_ref_id = ? AND notification_type_id = ?";

    /**
     * <p>
     * Represents the sql for adding notification.
     * </p>
     */
    private static final String SQL_INSERT_NOTIFICATION =
        "INSERT INTO notification " + "(project_id, external_ref_id, notification_type_id, create_user, "
            + "create_date, modify_user, modify_date) VALUES (?, ?, ?, ?, ?, ?, ?)";

    /**
     * <p>
     * Represents the sql to load resources.
     * </p>
     */
    private static final String SQL_SELECT_LOAD_RES =
        "SELECT resource.resource_id, resource_role_id, project_id,"
            + " project_phase_id, user_id, resource.create_user, resource.create_date, resource.modify_user,"
            + " resource.modify_date" + " FROM resource WHERE resource.resource_id = ?";

    /**
     * <p>
     * Represents the sql to select external properties.
     * </p>
     */
    private static final String SQL_SELECT_EXT_PROP =
        "SELECT resource_info_type_lu.name, resource_info.value"
            + " FROM resource_info, resource_info_type_lu  "
            + " WHERE resource_info.resource_info_type_id = resource_info_type_lu.resource_info_type_id "
            + " AND resource_id = ?";

    /**
     * <p>
     * Represents the sql for deleting the resource info.
     * </p>
     */
    private static final String SQL_DELETE_RES_INFO_TYPE =
        "DELETE FROM resource_info " + "WHERE resource_id = ? and resource_info_type_id = ?";

    /**
     * <p>
     * Represents the sql for updating the resource info.
     * </p>
     */
    private static final String SQL_UPDATE_RES_INFO =
        "UPDATE resource_info SET value = ? " + "WHERE resource_id = ? AND resource_info_type_id = ?";

    /**
     * <p>
     * Represents the sql for updating the resource submission.
     * </p>
     */
    private static final String SQL_UPDATE_SUBMISSION =
        "UPDATE resource_submission"
            + " SET modify_user = ?, modify_date = ? WHERE resource_id = ? AND submission_id = ?";

    /**
     * <p>
     * Represents the sql for selecting resource submission.
     * </p>
     */
    private static final String SQL_SELECT_SUBMISSION =
        "SELECT submission_id FROM resource_submission " + "WHERE resource_id = ?";

    /**
     * <p>
     * Represents the sql for selecting resource submissions.
     * </p>
     */
    private static final String SQL_SELECT_RESOURCE_SUBMISSIONS =
        "SELECT resource_id, submission_id FROM resource_submission WHERE resource_id IN (";

    /**
     * <p>
     * Represents the sql for updating resource.
     * </p>
     */
    private static final String SQL_UPDATE_RESOURCE =
        "UPDATE resource" + " SET resource_role_id = ?, project_id = ?, project_phase_id = ?,"
            + " user_id = ?, modify_user = ?, modify_date = ? WHERE resource_id = ?";

    /**
     * <p>
     * Represents the sql for deleting resource.
     * </p>
     */
    private static final String SQL_DELETE_RESOURCE = "DELETE FROM resource WHERE resource_id = ?";

    /**
     * <p>
     * Represents the sql for deleting resource submission.
     * </p>
     */
    private static final String SQL_DELETE_SUBMISSION =
        "DELETE FROM resource_submission WHERE resource_id = ?";

    /**
     * <p>
     * Represents the sql for deleting one line of resource submission.
     * </p>
     */
    private static final String SQL_DELETE_ONE_SUBMISSION =
        "DELETE FROM resource_submission WHERE resource_id = ? AND submission_id = ?";

    /**
     * <p>
     * Represents the sql for deleting resource info.
     * </p>
     */
    private static final String SQL_DELETE_RES_INFO = "DELETE FROM resource_info WHERE resource_id = ?";

    /**
     * <p>
     * Represents the sql for selecting resource info type.
     * </p>
     */
    private static final String SQL_SELECT_RES_INFO_TYPE =
        "SELECT resource_info_type_id" + " FROM resource_info_type_lu WHERE name = ?";

    /**
     * <p>
     * Represents the sql for inserting resource info.
     * </p>
     */
    private static final String SQL_INSERT_RES_INFO =
        "INSERT INTO resource_info"
            + "(resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)"
            + "VALUES (?, ?, ?, ?, ?, ?, ?)";

    /**
     * <p>
     * Represents the sql for inserting submission.
     * </p>
     */
    private static final String SQL_INSERT_SUBMISSION =
        "INSERT INTO resource_submission"
            + "(resource_id, submission_id, create_user, create_date, modify_user, modify_date)"
            + "VALUES (?, ?, ?, ?, ?, ?)";

    /**
     * <p>
     * Represents the sql for adding resource.
     * </p>
     */
    private static final String SQL_INSERT_RESOURCE =
        "INSERT INTO resource "
            + "(resource_id, resource_role_id, project_id, project_phase_id, user_id, create_user, create_date,"
            + " modify_user, modify_date)" + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    /**
     * <p>
     * Represents the sql for updating resource.
     * </p>
     */
    private static final String SQL_RESOURCE_EXISTS =
        "SELECT 'exists' from resource r, resource_info ri "
        + "  where project_id = ? and resource_role_id = ? "
        + "  and r.resource_id = ri.resource_id and resource_info_type_id = 1 "
        + "  and value = ?";

    /**
     * <p>
     * Represents the external reference id property key for resource property map.
     * </p>
     *
     * @since 1.2.2
     */
    private static final String EXTERNAL_REFERENCE_ID_PROPERTY_KEY = "External Reference ID";

    /**
     * <p>
     * Represents the project user audit creation type
     * </p>
     *
     * @since 1.2.2
     */
    private static final int PROJECT_USER_AUDIT_CREATE_TYPE = 1;

    /**
     * <p>
     * Represents the project user audit deletion type
     * </p>
     *
     * @since 1.2.2
     */
    private static final int PROJECT_USER_AUDIT_DELETE_TYPE = 2;

    /**
     * <p>
     * Represents the SQL for inserting project user audit records
     * </p>
     *
     * @since 1.2.2
     */
    private static final String SQL_INSERT_PROJECT_USER_AUDIT =
        "INSERT INTO project_user_audit (project_user_audit_id, project_id, resource_user_id, " +
        " resource_role_id, audit_action_type_id, action_date, action_user_id) " +
        " VALUES (PROJECT_USER_AUDIT_SEQ.nextval, ?, ?, ?, ?, ?, ?)";

    /**
     * <p>
     * The name of the connection producer to use when a connection to the database is retrieved from the
     * <code>DBConnectionFactory</code>. This field is immutable and can be <code>null</code>. When
     * non-null, no restrictions are applied to the field. When this field is <code>null</code>, the
     * createConnection() method is used to get a connection. When it is non-null, the
     * createConnection(String) method is used to get a connection. This field is exposed through the
     * protected getter <code>{@linkplain #getConnectionName()}</code> and is used by subclasses in the
     * openConnection method.
     * </p>
     */
    private final String connectionName;

    /**
     * <p>
     * The connection factory to use when a connection to the database is needed. This field is immutable and
     * must be non-null. This field is exposed through the protected getter
     * <code>{@linkplain #getConnectionFactory()}</code> is used by subclasses in the
     * <code>{@linkplain #openConnection()}</code> method.
     * </p>
     */
    private final DBConnectionFactory connectionFactory;

    /**
     * <p>
     * Creates a new <code>AbstractResourcePersistence</code>. The connectionName field is set to
     * <code>null</code>, and the connectionFactory field is a set to the argument.
     * </p>
     *
     * @param connectionFactory The connection factory to use for getting connections to the database.
     * @throws IllegalArgumentException If connectionFactory is <code>null</code>.
     */
    protected AbstractResourcePersistence(DBConnectionFactory connectionFactory) {
        this(connectionFactory, null);
    }

    /**
     * <p>
     * Creates a new <code>AbstractResourcePersistence</code>. All fields are set to the given values.
     * </p>
     *
     * @param connectionFactory The connection factory to use for getting connections to the database.
     * @param connectionName The name of the connection to use. Can be <code>null</code>.
     * @throws IllegalArgumentException If connectionFactory is <code>null</code>.
     */
    protected AbstractResourcePersistence(DBConnectionFactory connectionFactory, String connectionName) {
        Util.checkNull(connectionFactory, "connectionFactory");
        LOGGER.log(Level.INFO,
        		"Instantiate AbstractResourcePersistence with connectionFactory and connectionName[" + connectionName + "].");
        this.connectionFactory = connectionFactory;
        this.connectionName = connectionName;
    }

    /**
     * <p>
     * Adds the given Resource to the persistence store. The resource must not already exist (by id) in the
     * persistence store.
     * </p>
     * <p>
     * Any <code>SQLException</code> or <code>DBConnectionException</code> should be wrapped in a
     * <code>ResourcePersistenceException</code>.
     * </p>
     *
     * <p>
     * Note: since version 1.2.2, audit information is saved.
     * </p>
     *
     * @param resource The resource to add to the persistence store
     * @throws IllegalArgumentException If resource is <code>null</code> or its id is UNSET_ID or its
     *         <code>ResourceRole</code> is <code>null</code> or its creation/modification user/date is
     *         <code>null</code>
     * @throws ResourcePersistenceException If there is a failure to persist the change or a Resource with the
     *         id is already in the persistence.
     */
    public void addResource(Resource resource) throws ResourcePersistenceException {
        Util.checkResource(resource, false);

        LOGGER.log(Level.INFO, new LogMessage(new Long(resource.getId()), null,
                "add new resource to the project [id=" + (resource.getProject() == null ?
                    "null" : resource.getProject().longValue())
                + "] with role :" + resource.getResourceRole().getName()
                + " in the [id=" + (resource.getPhase() == null ? null :
                    resource.getPhase().longValue()) + "] phase."));
        Connection connection = openConnection();

        try {
            insertResource(connection, resource);

            // if the submissions are not empty, persist the submission.
            Long[] submissions = resource.getSubmissions();
            if (submissions != null) {
                for (int i = 0; i < submissions.length; i++) {
                    insertSubmission(connection, resource, submissions[i]);
                }
            }

            // persist properties.
            Map map = resource.getAllProperties();

            if (map != null) {
                for (Iterator iter = map.entrySet().iterator(); iter.hasNext();) {
                    Map.Entry entry = (Entry) iter.next();
    
                    Integer resourceInfoTypeId = getResourceInfoTypeId(connection, entry.getKey().toString());
    
                    // if resource_info_type_id is found
                    if (resourceInfoTypeId != null) {
                        insertResourceInfo(connection, resource, resourceInfoTypeId.intValue(), entry.getValue()
                            .toString());
                    }
                }
		 auditProjectUser(connection, resource, PROJECT_USER_AUDIT_CREATE_TYPE, null, null); // create
            }
        } catch (SQLException e) {
            closeConnectionOnError(connection);
            LOGGER.log(Level.ERROR, new LogMessage(new Long(resource.getId()), null,
                    "Unable to add resource to the project [id=" + resource.getProject().longValue() + "] with role:"
                    + resource.getResourceRole().getName() + " in [id=" + resource.getPhase() + "] phase.", e));
            throw new ResourcePersistenceException("Unable to insert resource.", e);
        } finally {
            closeConnection(connection);
        }
    }

    /**
     * Inserts the <code>Resource</code> instance into the database.
     *
     * @param connection the connection to database
     * @param resource the <code>Resource</code> instance to be persist.
     * @throws SQLException if failed to persistence Resource.
     * @throws ResourcePersistenceException <code>Resource</code> with the id is already in the persistence.
     *
     */
    private void insertResource(Connection connection, Resource resource) throws SQLException,
        ResourcePersistenceException {
    	LOGGER.log(Level.INFO, "insert a record into resource with resource id : " + resource.getId());
        PreparedStatement statement = null;
        int index = 1;
        try {
            statement = connection.prepareStatement(SQL_INSERT_RESOURCE);

            statement.setLong(index++, resource.getId());
            statement.setLong(index++, resource.getResourceRole().getId());

            statement.setObject(index++, resource.getProject());
            statement.setObject(index++, resource.getPhase());

            statement.setObject(index++, resource.getUserId());

            statement.setString(index++, resource.getCreationUser());
            statement.setTimestamp(index++, Util.dateToTimestamp(resource.getCreationTimestamp()));
            statement.setString(index++, resource.getModificationUser());
            statement.setTimestamp(index, Util.dateToTimestamp(resource.getModificationTimestamp()));

            if (statement.executeUpdate() != 1) {
                throw new ResourcePersistenceException("Resource with the id is already in the persistence");
            }

        } finally {
            Util.closeStatement(statement);
        }
    }

    /**
     * Inserts the submission information into database.
     *
     * @param connection the connection to database
     * @param resource the <code>Resource</code> instance to persist
     * @param submission the submission to insert
     * @throws SQLException if failed to persist submission.
     */
    private void insertSubmission(Connection connection, Resource resource, Object submission)
        throws SQLException {
        LOGGER.log(Level.INFO, "insert a record into the resource_submission with resource_id:" + resource.getId()
    			+ " and submission_id:" + getIdString(resource.getSubmissions()));
        PreparedStatement statement = null;
        int index = 1;
        try {
            statement = connection.prepareStatement(SQL_INSERT_SUBMISSION);
            statement.setLong(index++, resource.getId());
            statement.setObject(index++, submission);

            statement.setString(index++, resource.getCreationUser());
            statement.setTimestamp(index++, Util.dateToTimestamp(resource.getCreationTimestamp()));
            statement.setString(index++, resource.getModificationUser());
            statement.setTimestamp(index, Util.dateToTimestamp(resource.getModificationTimestamp()));

            statement.executeUpdate();
        } finally {
            Util.closeStatement(statement);
        }
    }

    /**
     * Inserts the properties of <code>Resource</code> into table resource_info.
     *
     * @param connection the connection to database
     * @param resource the <code>Resource</code> instance to be persisted.
     * @param resourceinfotypeid the resource_info_type_id
     * @param value the property value to be persisted
     * @throws SQLException if failed to persist resource_info
     */
    private void insertResourceInfo(Connection connection, Resource resource, int resourceinfotypeid,
        String value) throws SQLException {

    	LOGGER.log(Level.INFO, "insert a record into the resource_info table with resource_id = " + resource.getId() + 
    			" and resource_info_type_id =" + resourceinfotypeid + " value = " + value);
        PreparedStatement statement = null;

        int index = 1;
        try {
            statement = connection.prepareStatement(SQL_INSERT_RES_INFO);
            statement.setLong(index++, resource.getId());
            statement.setInt(index++, resourceinfotypeid);
            statement.setString(index++, value);
            statement.setString(index++, resource.getCreationUser());
            statement.setTimestamp(index++, Util.dateToTimestamp(resource.getCreationTimestamp()));
            statement.setString(index++, resource.getModificationUser());
            statement.setTimestamp(index, Util.dateToTimestamp(resource.getModificationTimestamp()));

            statement.executeUpdate();
        } finally {
            Util.closeStatement(statement);
        }
    }

    /**
     * Looks up table resource_info_type_lu for resource_info_type_id.
     *
     * @param connection the connection to database
     * @param name the name to look up in table.
     * @return Integer if exist, <code>null</code> if not
     * @throws SQLException if failed to look up resource_info_type_lu table for resource_info_type_id
     */
    private Integer getResourceInfoTypeId(Connection connection, String name) throws SQLException {
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            statement = connection.prepareStatement(SQL_SELECT_RES_INFO_TYPE);
            statement.setString(1, name);
            rs = statement.executeQuery();
            if (rs.next()) {
                return new Integer(rs.getInt(1));
            }
            return null;
        } finally {
            Util.closeResultSet(rs);
            Util.closeStatement(statement);
            
        }
    }

    /**
     * <p>
     * Deletes the given <code>Resource</code> (by id) in the persistence store. The <code>Resource</code>
     * must already be present in the persistence store, otherwise nothing is done.
     * </p>
     * <p>
     * Any <code>SQLException</code> or <code>DBConnectionException</code> should be wrapped in a
     * <code>ResourcePersistenceException</code>.
     * </p>
     *
     * <p>
     * Note: since version 1.2.2, audit information is saved.
     * </p>
     *
     * @param resource The resource to remove
     * @throws IllegalArgumentException If resource is <code>null</code> or its id is UNSET_ID or its
     *         ResourceRole is <code>null</code>
     * @throws ResourcePersistenceException If there is a failure to persist the change.
     */
    public void deleteResource(Resource resource) throws ResourcePersistenceException {
        Util.checkResource(resource, true);

        LOGGER.log(Level.INFO, new LogMessage(new Long(resource.getId()), null,
                "delete resource in the project [id=" + resource.getProject().longValue()
                + "] with role :" + resource.getResourceRole().getName()
                + " in the [id=" + (resource.getPhase() == null ? "null" :
                    resource.getPhase().longValue()) + "] phase."));

        Connection connection = openConnection();
        try {
        	LOGGER.log(Level.INFO, "delete resource info with resource id:" + resource.getId());
            deleteResource(connection, SQL_DELETE_RES_INFO, resource.getId());
            LOGGER.log(Level.INFO, "delete resource submission with resource id:" + resource.getId());
            deleteResource(connection, SQL_DELETE_SUBMISSION, resource.getId());
            LOGGER.log(Level.INFO, "delete resource with resource id:" + resource.getId());
            deleteResource(connection, SQL_DELETE_RESOURCE, resource.getId());

            // audit deletion
            auditProjectUser(connection, resource, PROJECT_USER_AUDIT_DELETE_TYPE, null, null); // delete
        } catch (SQLException e) {
            closeConnectionOnError(connection);
            LOGGER.log(Level.ERROR, new LogMessage(new Long(resource.getId()), null,
        			"Unable to delete resource to the project [id=" + resource.getProject() + "] with role:"
        			+ resource.getResourceRole().getName() + " in [id=" + resource.getPhase() + "] phase.", e));
            throw new ResourcePersistenceException("Fail to delete resource", e);
        } finally {
            closeConnection(connection);
        }
    }

    /**
     * Deletes the resource based on sql and id.
     *
     * @param connection the connection to database
     * @param sql the sql to be used for deletion
     * @param id the id to be deleted.
     * @throws SQLException if failed to delete the resource
     */
    private void deleteResource(Connection connection, String sql, long id) throws SQLException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
            statement.executeUpdate();
        } finally {
            Util.closeStatement(statement);
        }
    }

    /**
     * <p>
     * Updates the given Resource in the database with its currently set information. The Resource must
     * already be present in the persistence store.
     * </p>
     *
     * <p>
     * The operator information should already have been put in the modification date/modification user
     * properties of the Resource. Any SQLException or DBConnectionException should be wrapped in a
     * ResourcePersistenceException.
     * </p>
     *
     * <p>
     * Note that in version 1.2 we will be possibly updating multiple submission associations (version 1.1
     * only deals with a single association) which means that the actual SQL will be either modified slightly
     * or it will have to be executed multiple times for each relevant submission entry.
     * </p>
     *
     * <p>
     * Note: since version 1.2.2, audit information is saved.
     * </p>
     *
     * @param resource The resource to update
     * @throws IllegalArgumentException If resource is <code>null</code> or its id is UNSET_ID or its
     *         <code>ResourceRole</code> is <code>null</code> or its modification user/date is
     *         <code>null</code>
     * @throws ResourcePersistenceException If there is a failure to persist the <code>Resource</code>, or
     *         the <code>Resource</code> is not already in the persistence.
     */
    public void updateResource(Resource resource) throws ResourcePersistenceException {
        Util.checkResource(resource, false);

        LOGGER.log(Level.INFO, new LogMessage(new Long(resource.getId()), null,
                "update resource in the project [id=" + (resource.getProject() == null ?
                    "null" : resource.getProject().longValue())
                + "] with role :" + resource.getResourceRole().getName()
                + " in the [id=" + (resource.getPhase() == null ? "null" :
                    resource.getPhase().longValue()) + "] phase."));

        Connection connection = openConnection();
        try {
            // get old resource role to save audit information in case it changed.
            Long oldResourceRoleId = getResourceRoleId(connection, resource.getId());

            if (oldResourceRoleId != null && oldResourceRoleId == resource.getResourceRole().getId()) {
                // if it's the same role, don't consider it for the audit
                oldResourceRoleId = null;
            }

            // Update the resource table.
            updateResourceTable(connection, resource);

            // Update the resource_submission table accordingly.
            Long[] previousSubmissions = getSubmissionEntry(connection, resource);
            Long[] submissions = resource.getSubmissions();

            // use the Set to check existing of previous submissions
            Set previousSubmissionsSet = new HashSet();

            if (previousSubmissions.length == 0 && submissions.length != 0) {
                // For each submission associated with Resource, insert submission.
                for (int i = 0; i < submissions.length; i++) {
                    insertSubmission(connection, resource, submissions[i]);
                }
            } else if (previousSubmissions.length != 0 && submissions.length == 0) {
            	LOGGER.log(Level.INFO, "delete resource submission with resource id:" + resource.getId());
                // remove previous submission.
                for (int i = 0; i < previousSubmissions.length; i++) {
                    deleteResourceSubmission(connection, resource, previousSubmissions[i]);
                }
            } else if (previousSubmissions.length != 0 && submissions.length != 0) {
                // update submission.

                // fill the previousSubmissionsSet
                for (int i = 0; i < previousSubmissions.length; i++) {
                    if (!resource.containsSubmission(previousSubmissions[i])) {
                        // if it is not in submissions of resource, delete it
                        deleteResourceSubmission(connection, resource, previousSubmissions[i]);
                    } else {
                        // add it to Set
                        previousSubmissionsSet.add(previousSubmissions[i]);
                    }
                }

                // handle the submissions
                for (int i = 0; i < submissions.length; i++) {
                    if (previousSubmissionsSet.contains(submissions[i])) {
                        // do update
                        updateResourceSubmission(connection, resource, submissions[i]);
                    } else {
                        // insert the submission
                        insertSubmission(connection, resource, submissions[i]);
                    }
                }
            }

            // updating the extended properties
            Map previousProperties =
                selectExternalProperties(connection, new Resource(resource.getId())).getAllProperties();

            // keep old user id to save audit information in case it changed.
            Long oldResourceUserId = null;

            for (Iterator iter = resource.getAllProperties().entrySet().iterator(); iter.hasNext();) {
                Map.Entry entry = (Entry) iter.next();

                String key = entry.getKey().toString();
                String value = entry.getValue().toString();
                Object oldValue = previousProperties.get(key);
                if (value.equals(oldValue)) {
                    // there is previous entry, but same current entry, do not hit the db.
                    previousProperties.remove(key);

                } else if (oldValue == null) {
                    // no previous entry, but current entry has, insert the current entry.
                    Integer resourceInfoTypeId = getResourceInfoTypeId(connection, key);

                    // if resource_info_type_id is found
                    if (resourceInfoTypeId != null) {
                        insertResourceInfo(connection, resource, resourceInfoTypeId.intValue(), value);
                    }

                } else if (previousProperties.get(key) != null) {
                    // there is previous entry , but different from current entry, do an update.

                    // check if external reference id changed (user id) and save it to later generate audit
                    if (key.equals(EXTERNAL_REFERENCE_ID_PROPERTY_KEY)) {
                        try {
                            oldResourceUserId =
                                Long.parseLong(previousProperties.get(EXTERNAL_REFERENCE_ID_PROPERTY_KEY).toString());
                        } catch (NumberFormatException e1) {
                            throw new ResourcePersistenceException("Audit information can't be saved " +
                                "since resource's external reference id is invalid");
                        }
                    }

                    Integer resourceInfoTypeId = getResourceInfoTypeId(connection, key);
                    if (resourceInfoTypeId != null) {
                        updateResourceInfo(connection, resource.getId(), resourceInfoTypeId.intValue(), value);
                    }
                    previousProperties.remove(key);
                }
            }

            // Up to now, what are left in the previousProperties are
            // properties which do not exist in the current properties list, we need to remove them from db.
            for (Iterator iter = previousProperties.entrySet().iterator(); iter.hasNext();) {
                Map.Entry entry = (Entry) iter.next();

                Integer resourceInfoTypeId = getResourceInfoTypeId(connection, entry.getKey().toString());
                if (resourceInfoTypeId != null) {
                    removeResourceInfo(connection, resource.getId(), resourceInfoTypeId.intValue());
                }
            }

            // audit update with delete / create audit records
            if (oldResourceUserId != null || oldResourceRoleId != null) {
                auditProjectUser(connection, resource, PROJECT_USER_AUDIT_DELETE_TYPE, oldResourceUserId,
                    oldResourceRoleId); // delete
                auditProjectUser(connection, resource, PROJECT_USER_AUDIT_CREATE_TYPE, null, null); // create
            }

        } catch (SQLException e) {
            closeConnectionOnError(connection);
            LOGGER.log(Level.ERROR, new LogMessage(new Long(resource.getId()), null,
                    "Unable to update resource to the project [id=" + resource.getProject().longValue() + "] with role:"
                    + resource.getResourceRole().getName() + " in [id=" + resource.getPhase() + "] phase.", e));
            throw new ResourcePersistenceException("Fail to update resource", e);
        } finally {
            closeConnection(connection);
        }
    }

    /**
     * Deletes the submission information into database.
     *
     * @param connection the connection to database
     * @param resource the <code>Resource</code> instance to persist
     * @param submission the submission to delete
     * @throws SQLException if failed to delete submission.
     */
    private void deleteResourceSubmission(Connection connection, Resource resource, Object submission)
        throws SQLException {
        PreparedStatement statement = null;
        int index = 1;
        try {
            statement = connection.prepareStatement(SQL_DELETE_ONE_SUBMISSION);

            statement.setLong(index++, resource.getId());
            statement.setObject(index, submission);
            statement.executeUpdate();
        } finally {
            Util.closeStatement(statement);
        }

    }

    /**
     * Updates the resource table with <code>Resource</code> instance.
     *
     * @param connection the connection to database.
     * @param resource the <code>Resource</code> instance to update
     * @throws SQLException if failed to update <code>Resource</code> instance.
     * @throws ResourcePersistenceException the <code>Resource</code> is not already in the persistence.
     */
    private void updateResourceTable(Connection connection, Resource resource) throws SQLException,
        ResourcePersistenceException {

    	LOGGER.log(Level.INFO, "update the resource table with resource id : " + resource.getId());
        PreparedStatement statement = null;
        int index = 1;
        try {
            statement = connection.prepareStatement(SQL_UPDATE_RESOURCE);
            statement.setLong(index++, resource.getResourceRole().getId());
            statement.setObject(index++, resource.getProject());
            statement.setObject(index++, resource.getPhase());
            statement.setObject(index++, resource.getUserId());
            statement.setString(index++, resource.getModificationUser());
            statement.setTimestamp(index++, Util.dateToTimestamp(resource.getModificationTimestamp()));
            statement.setLong(index, resource.getId());

            if (statement.executeUpdate() != 1) {
                throw new ResourcePersistenceException("The resource id is not already in the database.");
            }
        } finally {
            Util.closeStatement(statement);
        }
    }

    /**
     * Gets the submission entry array for <code>Resource</code> instance.
     *
     * @param connection the connection to database.
     * @param resource the Resource instance
     * @return The Long array which contains the submission(s)
     * @throws SQLException if failed to get the submission entry for <code>Resource</code> instance.
     */
    private Long[] getSubmissionEntry(Connection connection, Resource resource) throws SQLException {
        PreparedStatement statement = null;
        ResultSet rs = null;
        List submissions = new ArrayList();

        try {
            statement = connection.prepareStatement(SQL_SELECT_SUBMISSION);
            statement.setLong(1, resource.getId());

            rs = statement.executeQuery();

            while (rs.next()) {
                submissions.add(new Long(rs.getLong(1)));
            }

            return (Long[]) submissions.toArray(new Long[submissions.size()]);
        } finally {
             Util.closeResultSet(rs);
            Util.closeStatement(statement);
           
        }
    }

        /**
     * Gets the submission entry array for <code>Resource</code> instance.
     *
     * @param connection the connection to database.
     * @param resourceIds the resource ids.
     * @return The map contains mapping from resource id to its submissions
     * @throws ResourcePersistenceException if failed to get the submissions for resources.
     */
    private Map getResourceSubmissions(Connection connection, long[] resourceIds) throws ResourcePersistenceException {
        PreparedStatement statement = null;
        ResultSet rs = null;
        List submissions = new ArrayList();

        Map resSubmissions = new HashMap();
        try {
            statement = connection.prepareStatement(buildQueryWithIds(SQL_SELECT_RESOURCE_SUBMISSIONS, resourceIds));

            rs = statement.executeQuery();

            while (rs.next()) {
                Long resourceId = new Long(rs.getLong(1));
                Long submissionId = new Long(rs.getLong(2));
                List subs = new ArrayList();
                if (resSubmissions.containsKey(resourceId)) {
                    subs  = (List) resSubmissions.get(resourceId);
                } else {
                    subs = new ArrayList();
                    resSubmissions.put(resourceId, subs);
                }
                subs.add(submissionId);
            }

            return resSubmissions;
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, new LogMessage(null, null, "Failed to get external properties for resource.", e));
            throw new ResourcePersistenceException("fail to retreive submission for resources.", e);
        } finally {
            Util.closeResultSet(rs);
            Util.closeStatement(statement);

        }
    }

    /**
     * Updates the submission of the <code>Resource</code> instance.
     *
     * @param connection the connection to database
     * @param resource the <code>Resource</code> instance
     * @param submission the submission to update
     * @throws SQLException if failed to update resource submission
     */
    private void updateResourceSubmission(Connection connection, Resource resource, Object submission)
        throws SQLException {

    	LOGGER.log(Level.INFO, "update the resource_submission table with the resource_id:" + resource.getId());
        PreparedStatement statement = null;
        int index = 1;
        try {
            statement = connection.prepareStatement(SQL_UPDATE_SUBMISSION);

            statement.setString(index++, resource.getModificationUser());
            statement.setTimestamp(index++, Util.dateToTimestamp(resource.getModificationTimestamp()));
            statement.setLong(index++, resource.getId());
            statement.setObject(index++, submission);
            statement.executeUpdate();
        } finally {
            Util.closeStatement(statement);
        }
    }

    /**
     * Updates the resource_info table with the given connection, resource_id and resource_info_type_id.
     *
     * @param connection the connection to the database.
     * @param resourceId the resource id.
     * @param resourceTypeInfoId the resource type info id.
     * @param value the value.
     * @throws SQLException if database operation fails for some reasons.
     */
    private void updateResourceInfo(Connection connection, long resourceId, int resourceTypeInfoId,
        String value) throws SQLException {
    	LOGGER.log(Level.INFO, "update resource_info table with resource_id = " + resourceId
    			+ " and resource_info_type_id =" + resourceTypeInfoId + " value = " + value);
        PreparedStatement statement = null;
        int index = 1;
        try {
            statement = connection.prepareStatement(SQL_UPDATE_RES_INFO);
            statement.setString(index++, value);
            statement.setLong(index++, resourceId);
            statement.setLong(index++, resourceTypeInfoId);

            statement.executeUpdate();
        } finally {
            Util.closeStatement(statement);
        }
    }

    /**
     * Removes resource info with the given connection, resource_id and resourceInfo type id.
     *
     * @param connection the connection to the database.
     * @param resourceId the resource id.
     * @param resourceInfoTypeId the resource info type id.
     * @throws SQLException if database operation fails for some reasons.
     */
    private void removeResourceInfo(Connection connection, long resourceId, int resourceInfoTypeId)
        throws SQLException {
    	LOGGER.log(Level.INFO, "delete resource_info with resource_id:" + resourceId + " and resource_info_type_id:"
    			+ resourceInfoTypeId);
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_DELETE_RES_INFO_TYPE);
            statement.setLong(1, resourceId);
            statement.setInt(2, resourceInfoTypeId);

            statement.executeUpdate();
        } finally {
            Util.closeStatement(statement);
        }
    }

    /**
     * Selects all external properties for <code>Resource</code>, and set them into resource and returned.
     *
     * @param connection the connection to database
     * @param resource the resource instance
     * @return the resource instance with all external properties set.
     * @throws ResourcePersistenceException if failed to select all external properties for this resource
     *         instance.
     */
    private Resource selectExternalProperties(Connection connection, Resource resource)
        throws ResourcePersistenceException {

        ResultSet rs = null;
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(SQL_SELECT_EXT_PROP);
            statement.setLong(1, resource.getId());

            rs = statement.executeQuery();

            while (rs.next()) {
                resource.setProperty(rs.getString(1), rs.getString(2));
            }
            return resource;
        } catch (SQLException e) {
        	LOGGER.log(Level.ERROR, new LogMessage(null, null, "Failed to get external properties for resource.", e));
            throw new ResourcePersistenceException("Failed to select external properties for resource.", e);
        } finally {
            Util.closeResultSet(rs);
            Util.closeStatement(statement);
            
        }
    }

    /**
     * <p>
     * Loads the resource from the persistence with the given id.
     * </p>
     *
     * <p>
     * Returns <code>null</code> if there is no resource for the given id.
     * </p>
     *
     * @return The loaded Resource
     * @param resourceId The id of the Resource to load
     * @throws IllegalArgumentException If resourceId is not greater than 0
     * @throws ResourcePersistenceException If there is an error loading the Resource
     */
    public Resource loadResource(long resourceId) throws ResourcePersistenceException {
        Util.checkPositiveValue(resourceId, "resourceId");

        LOGGER.log(Level.INFO, new LogMessage(new Long(resourceId), null,"load resource."));

        Connection connection = openConnection();

        ResultSet rs = null;
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(SQL_SELECT_LOAD_RES);
            statement.setLong(1, resourceId);

            rs = statement.executeQuery();

            if (rs.next()) {
                Resource resource = constructResource(rs, connection);
                // select all external properties for the resource instance.
                return selectExternalProperties(connection, resource);
            }
            return null;
        } catch (SQLException e) {
            closeConnectionOnError(connection);
            LOGGER.log(Level.ERROR, new LogMessage(new Long(resourceId), null,"Failed to load resource instance.",e));
            throw new ResourcePersistenceException("Failed to load resource instance.", e);
        } finally {
            Util.closeResultSet(rs);
            Util.closeStatement(statement);
            
            closeConnection(connection);
        }
    }

    /**
     * Constructs a <code>Resource</code> instance from given <code>ResultSet</code> instance.
     *
     * @param rs the <code>ResultSet</code> instance
     * @param connection the <code>Connection</code> instance
     * @return The Resource instance
     * @throws ResourcePersistenceException if failed to construct the <code>Resource</code> instance.
     */
    private Resource constructResource(ResultSet rs, Connection connection)
        throws ResourcePersistenceException {
        try {
            Resource resource = new Resource();

            resource.setId(rs.getLong(1));
            ResourceRole role = this.loadResourceRole(rs.getLong(2));

            resource.setResourceRole(role);

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
            
            if (rs.getObject(5) == null) {
                resource.setUserId(null);
            } else {
                resource.setUserId(rs.getLong(5));
            }



            resource.setCreationUser(rs.getString(6));
            resource.setCreationTimestamp(rs.getTimestamp(7));
            resource.setModificationUser(rs.getString(8));
            resource.setModificationTimestamp(rs.getTimestamp(9));

            // add submissions into resource
            resource.setSubmissions(getSubmissionEntry(connection, resource));

            return resource;
        } catch (SQLException e) {
        	LOGGER.log(Level.ERROR, new LogMessage(null, null, "Failed to load the Resource from ResultSet.", e));
            throw new ResourcePersistenceException("Failed to load the Resource from ResultSet.", e);
        }finally {
            Util.closeResultSet(rs);

        }
    }

        /**
     * Constructs a <code>Resource</code> instance with role from given <code>ResultSet</code> instance.
     *
     * @param rs the <code>ResultSet</code> instance
     * @param connection the <code>Connection</code> instance
     * @return The Resource instance
     * @throws ResourcePersistenceException if failed to construct the <code>Resource</code> instance.
     */
    private Resource constructResourceWithRole(ResultSet rs, Connection connection)
        throws ResourcePersistenceException {
        try {
            Resource resource = new Resource();

            int index = 1;
            resource.setId(rs.getLong(index++));

            if (rs.getObject(index) == null) {
                resource.setProject(null);
                index++;
            } else {
                resource.setProject(new Long(rs.getLong(index++)));
            }

            if (rs.getObject(index) == null) {
                resource.setPhase(null);
                index++;
            } else {
                resource.setPhase(new Long(rs.getLong(index++)));
            }

            int userIdIndex = index++;

            if(rs.getObject(userIdIndex) == null) {
                resource.setUserId(null);
            } else {
                resource.setUserId(rs.getLong(userIdIndex));
            }

            resource.setCreationUser(rs.getString(index++));
            resource.setCreationTimestamp(rs.getTimestamp(index++));
            resource.setModificationUser(rs.getString(index++));
            resource.setModificationTimestamp(rs.getTimestamp(index++));

            ResourceRole role = new ResourceRole();
            role.setId(rs.getLong(index++));

            if (rs.getObject(index) == null) {
                role.setPhaseType(null);
                index++;
            } else {
                role.setPhaseType(new Long(rs.getLong(index++)));
            }

            role.setName(rs.getString(index++));
            role.setDescription(rs.getString(index++));
            role.setCreationUser(rs.getString(index++));
            role.setCreationTimestamp(rs.getTimestamp(index++));
            role.setModificationUser(rs.getString(index++));
            role.setModificationTimestamp(rs.getTimestamp(index++));

            resource.setResourceRole(role);

            return resource;
        } catch (SQLException e) {
        	LOGGER.log(Level.ERROR, new LogMessage(null, null, "Failed to load the Resource from ResultSet.", e));
            throw new ResourcePersistenceException("Failed to load the Resource from ResultSet.", e);
        }finally {
            Util.closeResultSet(rs);

        }
    }

    /**
     * Construct a Resource instance from given CustomResultSet instance.
     *
     * @param rs
     *            the CustomResultSet instance
     * @return The Resource instance
     * @throws ResourcePersistenceException
     *             if failed to construct the Resource instance.
     */
    private Resource constructResource(CustomResultSet rs, Connection connection) throws ResourcePersistenceException {
        try {
            Resource resource = new Resource();

            resource.setId(rs.getLong("resource_id"));
            ResourceRole role = this.loadResourceRole(rs.getLong("resource_role_id"), connection);

            resource.setResourceRole(role);

            if (rs.getObject("project_id") != null) {
                resource.setProject(new Long(rs.getLong("project_id")));
            } else {
                resource.setProject(null);
            }

            if (rs.getObject("project_phase_id") != null) {
                resource.setPhase(new Long(rs.getLong("project_phase_id")));
            } else {
                resource.setPhase(null);
            }

            if(rs.getObject("user_id") == null) {
                resource.setUserId(null);
            } else {
                resource.setUserId(rs.getLong("user_id"));
            }


            resource.setCreationUser(rs.getString("create_user"));
            resource.setCreationTimestamp(rs.getTimestamp("create_date"));
            resource.setModificationUser(rs.getString("modify_user"));
            resource.setModificationTimestamp(rs.getTimestamp("modify_date"));

            // add submissions into resource
            resource.setSubmissions(getSubmissionEntry(connection, resource));

            return resource;
        } catch (InvalidCursorStateException icse) {
            throw new ResourcePersistenceException("Failed to load the Resource from ResultSet.", icse);
        } catch (NullColumnValueException ne) {
            throw new ResourcePersistenceException("Failed to load the Resource from ResultSet.", ne);
        } catch (SQLException e) {
            throw new ResourcePersistenceException("Failed to load the Resource from ResultSet.", e);
        }
    }

    /**
     * Construct a Resource instance from given CustomResultSet instance.
     *
     * @param rs
     *            the CustomResultSet instance
     * @return The Resource instance
     * @throws ResourcePersistenceException
     *             if failed to construct the Resource instance.
     */
    private Resource constructResourceWithRole(CustomResultSet rs, Connection connection) throws ResourcePersistenceException {
        try {
            Resource resource = new Resource();

            resource.setId(rs.getLong("resource_id"));

            if (rs.getObject("project_id") != null) {
                resource.setProject(new Long(rs.getLong("project_id")));
            } else {
                resource.setProject(null);
            }

            if (rs.getObject("project_phase_id") != null) {
                resource.setPhase(new Long(rs.getLong("project_phase_id")));
            } else {
                resource.setPhase(null);
            }

            if(rs.getObject("user_id") == null) {
                resource.setUserId(null);
            } else {
                resource.setUserId(rs.getLong("user_id"));
            }

            resource.setCreationUser(rs.getString("create_user"));
            resource.setCreationTimestamp(rs.getTimestamp("create_date"));
            resource.setModificationUser(rs.getString("modify_user"));
            resource.setModificationTimestamp(rs.getTimestamp("modify_date"));

            // resource role
            ResourceRole role = new ResourceRole();

            role.setId(rs.getLong("resource_role_id"));

            if (rs.getObject("phase_type_id") == null) {
                role.setPhaseType(null);
            } else {
                role.setPhaseType(new Long(rs.getLong("phase_type_id")));
            }

            role.setName(rs.getString("rr_name"));
            role.setDescription(rs.getString("rr_description"));
            role.setCreationUser(rs.getString("rr_create_user"));
            role.setCreationTimestamp(rs.getTimestamp("rr_create_date"));
            role.setModificationUser(rs.getString("rr_modify_user"));
            role.setModificationTimestamp(rs.getTimestamp("rr_modify_date"));

            resource.setResourceRole(role);

            // add submissions into resource
            resource.setSubmissions(getSubmissionEntry(connection, resource));

            return resource;
        } catch (InvalidCursorStateException icse) {
            throw new ResourcePersistenceException("Failed to load the Resource from ResultSet.", icse);
        } catch (NullColumnValueException ne) {
            throw new ResourcePersistenceException("Failed to load the Resource from ResultSet.", ne);
        } catch (SQLException e) {
            throw new ResourcePersistenceException("Failed to load the Resource from ResultSet.", e);
        }
    }


    /**
     * <p>
     * Adds a notification to the persistence store. A notification type with the given ID must already exist
     * in the persistence store, as must a project.
     * </p>
     * <p>
     * Any <code>SQLException</code> or <code>DBConnectionException</code> should be wrapped in a
     * <code>ResourcePersistenceException</code>.
     * </p>
     *
     * @param user The user id to add as a notification
     * @param project The project the notification is related to
     * @param notificationType The id of the notification type
     * @param operator The operator making the change
     * @throws IllegalArgumentException If user, project, or notificationType is &lt;= 0
     * @throws IllegalArgumentException If operator is <code>null</code>
     * @throws ResourcePersistenceException If there is an error making the change in the persistence store
     */
    public void addNotification(long user, long project, long notificationType, String operator)
        throws ResourcePersistenceException {

        Util.checkPositiveValue(user, "user");
        Util.checkPositiveValue(project, "project");
        Util.checkPositiveValue(notificationType, "notificationType");
        Util.checkNull(operator, "operator");

        if (loadNotification(user, project, notificationType) != null)
        {
            LOGGER.log(Level.INFO, new LogMessage(null, operator, "notification (type="
        		+ notificationType+ " in the project:" + project + " with external_ref user:" + user) + "already exists");
            return;
        }

        LOGGER.log(Level.INFO, new LogMessage(null, operator, "add notification(type="
        		+ notificationType+ " in the project:" + project + " with external_ref user:" + user));
        
        Connection connection = openConnection();
        PreparedStatement statement = null;
        int index = 1;
        try {
            statement = connection.prepareStatement(SQL_INSERT_NOTIFICATION);

            statement.setLong(index++, project);
            statement.setLong(index++, user);
            statement.setLong(index++, notificationType);
            statement.setString(index++, operator);

            Timestamp time = new Timestamp(System.currentTimeMillis());
            statement.setTimestamp(index++, time);
            statement.setString(index++, operator);
            statement.setTimestamp(index, time);

            statement.executeUpdate();

        } catch (SQLException e) {
            closeConnectionOnError(connection);
            LOGGER.log(Level.ERROR, new LogMessage(null, operator, "Failsed to add notification(type="
            		+ notificationType+ " in the project:" + project + " with external_ref user:" + user,e));
            throw new ResourcePersistenceException("Failed to insert notification for reason.", e);
        } finally {
            Util.closeStatement(statement);
            closeConnection(connection);
        }
    }

    /**
     * <p>
     * Removes a notification from the persistence store. The given notification tuple identifier (user,
     * project, and notificationType) should already exists in the persistence store, otherwise nothing will
     * be done. Note that in this implementation the operator is not used.
     * </p>
     * <p>
     * Any <code>SQLException</code> or <code>DBConnectionException</code> should be wrapped in a
     * ResourcePersistenceException.
     * </p>
     *
     * @param user The user id of the notification to remove
     * @param project The project id of the notification to remove
     * @param notificationType The notification type id of the notification to remove
     * @param operator The operator making the change
     * @throws IllegalArgumentException If user, project, or notificationType is &lt;= 0 or operator is null
     * @throws ResourcePersistenceException If there is an error making the change in the persistence store
     */
    public void removeNotification(long user, long project, long notificationType, String operator)
        throws ResourcePersistenceException {
        Util.checkPositiveValue(user, "user");
        Util.checkPositiveValue(project, "project");
        Util.checkPositiveValue(notificationType, "notificationType");
        Util.checkNull(operator, "operator");
        LOGGER.log(Level.INFO, new LogMessage(null, operator, "Remove notification(type="
        		+ notificationType+ " from the project:" + project + " with external_ref user:" + user));
        PreparedStatement statement = null;
        Connection connection = openConnection();
        int index = 1;
        try {

            statement = connection.prepareStatement(SQL_DELETE_NOTIFICATION);
            statement.setLong(index++, project);
            statement.setLong(index++, user);
            statement.setLong(index, notificationType);

            statement.executeUpdate();
        } catch (SQLException e) {
            closeConnectionOnError(connection);
            LOGGER.log(Level.ERROR, new LogMessage(null, operator, "Failsed to remove notification(type="
            		+ notificationType+ " from the project:" + project + " with external_ref user:" + user,e));
            throw new ResourcePersistenceException("Failed to remove notification.", e);
        } finally {
            Util.closeStatement(statement);
            closeConnection(connection);
        }
    }

    /**
     * <p>
     * Load the <code>Notification</code> for the given "id" triple from the persistence store. Returns
     * <code>null</code> if no entry in the persistence has the given user, project, and notificationType.
     * </p>
     * <p>
     * Any <code>SQLException</code> or <code>DBConnectionException</code> should be wrapped in a
     * <code>ResourcePersistenceException</code>.
     * </p>
     *
     * @return The loaded notification
     * @param user The id of the user
     * @param project The id of the project
     * @param notificationType The id of the notificationType
     * @throws IllegalArgumentException If user, project, or notificationType is <= 0
     * @throws ResourcePersistenceException If there is an error making the change in the persistence store
     */
    public Notification loadNotification(long user, long project, long notificationType)
        throws ResourcePersistenceException {
        Util.checkPositiveValue(user, "user");
        Util.checkPositiveValue(project, "project");
        Util.checkPositiveValue(notificationType, "notificationType");

        LOGGER.log(Level.INFO, new LogMessage(null, null, "Load notification(type="
        		+ notificationType+ " of the project:" + project + " with external_ref user:" + user));

        Connection connection = openConnection();

        ResultSet rs = null;
        PreparedStatement statement = null;

        int index = 1;
        try {
            statement = connection.prepareStatement(SQL_SELECT_NOTIFICATION);
            statement.setLong(index++, project);
            statement.setLong(index++, user);
            statement.setLong(index, notificationType);

            rs = statement.executeQuery();

            if (rs.next()) {
                return constructNotification(connection, rs);
            }

            return null;
        } catch (SQLException e) {
            closeConnectionOnError(connection);
            LOGGER.log(Level.INFO, new LogMessage(null, null, "Failed to Load notification(type="
            		+ notificationType+ " of the project:" + project + " with external_ref user:" + user));
            throw new ResourcePersistenceException("Failed to load the notification.", e);
        } finally {
            Util.closeResultSet(rs);
            Util.closeStatement(statement);
            closeConnection(connection);
        }
    }

    /**
     *
     * Constructs the <code>Notification</code> instance from the <code>ResultSet</code> instance.
     *
     * @param connection the connection to the db.
     * @param rs the <code>ResultSet</code> instance
     * @return the constructed <code>Notification</code> instance.
     * @throws ResourcePersistenceException if failed to get the <code>Notification</code> instance from
     *         database.
     */
    private Notification constructNotification(Connection connection, ResultSet rs)
        throws ResourcePersistenceException {
        try {
            int index = 3;
            NotificationType type = loadNotificationType(connection, rs.getLong(index++));

            Notification notification = new Notification(rs.getLong(1), type, rs.getLong(2));

            notification.setCreationUser(rs.getString(index++));
            notification.setCreationTimestamp(rs.getTimestamp(index++));
            notification.setModificationUser(rs.getString(index++));
            notification.setModificationTimestamp(rs.getTimestamp(index++));

            return notification;

        } catch (SQLException e) {
        	LOGGER.log(Level.ERROR, new LogMessage(null, null, "Failed to construct Notification instance.", e));
            throw new ResourcePersistenceException("Failed to construct Notification instance.", e);
        }
    }

    /**
     *
     * Construct the Notification instance from the ResultSet instance.
     *
     * @param connection
     *            the connection to the db.
     * @param rs
     *            the ResultSet instance
     * @return the constructed Notification instance.
     * @throws ResourcePersistenceException
     *             if failed to get the Notification instance from database.
     */
    private Notification consructNotification(Connection connection, CustomResultSet rs) throws ResourcePersistenceException {
        try {
            NotificationType type = loadNotificationType(connection, rs.getLong("notification_type_id"));
            Notification notification = new Notification(rs.getLong("project_id"), type, rs.getLong("external_ref_id"));

            notification.setCreationUser(rs.getString("create_user"));
            notification.setCreationTimestamp(rs.getTimestamp("create_date"));
            notification.setModificationUser(rs.getString("modify_user"));
            notification.setModificationTimestamp(rs.getTimestamp("modify_date"));

            return notification;
        } catch (SQLException e) {
            throw new ResourcePersistenceException("Failed to construct Notification instance.", e);
        } catch (InvalidCursorStateException icse) {
            throw new ResourcePersistenceException("Failed to construct Notification instance.", icse);
        } catch (NullColumnValueException ne) {
            throw new ResourcePersistenceException("Failed to load the Resource from ResultSet.", ne);
        }
    }

    /**
     * Loads the <code>NotificationType</code> instance with notificationTypeId in the database.
     *
     * @param connection the connection to database.
     * @param notificationTypeId the notificationTypeId to load
     * @return NotificationType instance if exists, <code>null</code> otherwise.
     * @throws SQLException if failed to load the <code>NotificationType</code> instance.
     */
    private NotificationType loadNotificationType(Connection connection, long notificationTypeId)
        throws SQLException {

        PreparedStatement statement = null;
        ResultSet rs = null;
        int index = 1;
        try {
            statement = connection.prepareStatement(SQL_SELECT_NOTIFICATION_TYPE);
            statement.setInt(1, (int) notificationTypeId);

            rs = statement.executeQuery();

            if (rs.next()) {
                NotificationType type = new NotificationType();
                type.setId(rs.getInt(index++));
                type.setName(rs.getString(index++));
                type.setDescription(rs.getString(index++));
                type.setCreationUser(rs.getString(index++));
                type.setCreationTimestamp(rs.getTimestamp(index++));
                type.setModificationUser(rs.getString(index++));
                type.setModificationTimestamp(rs.getTimestamp(index));

                return type;
            }
        } finally {
            Util.closeResultSet(rs);
            Util.closeStatement(statement);        
        }

        return null;
    }

    /**
     * <p>
     * Adds a notification type to the persistence store. The id of the notification type must already be
     * assigned to the <code>NotificationType</code> object passed to this method, and not already exist in
     * the persistence source.
     * </p>
     * <p>
     * Any <code>SQLException</code> or <code>DBConnectionException</code> should be wrapped in a
     * <code>ResourcePersistenceException</code>.
     * </p>
     *
     * @param notificationType The notification type to add.
     * @throws IllegalArgumentException If notificationType is null or its id is NotificationType.UNSET_ID or
     *         its name/description is null or its creation/modification user/date are null
     * @throws ResourcePersistenceException If there is an error updating the persistence
     */
    public void addNotificationType(NotificationType notificationType) throws ResourcePersistenceException {
        Util.checkNotificationType(notificationType, false);

        LOGGER.log(Level.INFO, "add a notification type with id:" + notificationType.getId());
        
        Connection connection = openConnection();

        PreparedStatement statement = null;
        int index = 1;
        try {
            statement = connection.prepareStatement(SQL_INSERT_NOTIFICATION_TYPE);
            statement.setLong(index++, notificationType.getId());
            statement.setString(index++, notificationType.getName());
            statement.setString(index++, notificationType.getDescription());
            statement.setString(index++, notificationType.getCreationUser());

            statement.setTimestamp(index++, Util.dateToTimestamp(notificationType.getCreationTimestamp()));
            statement.setString(index++, notificationType.getModificationUser());
            statement.setTimestamp(index, Util.dateToTimestamp(notificationType.getModificationTimestamp()));

            statement.executeUpdate();

        } catch (SQLException e) {
            closeConnectionOnError(connection);
            LOGGER.log(Level.ERROR, new LogMessage(null, null,
            		"Failed add a notification type with id:" + notificationType.getId(),e));
            throw new ResourcePersistenceException("Failed to add the notificationType instance.", e);
        } finally {
            Util.closeStatement(statement);
            closeConnection(connection);
        }
    }

    /**
     * <p>
     * Removes a notification type from the persistence (by id) store. If no notification type exists with the
     * id of the notification type, nothing is done.
     * </p>
     *
     * @param notificationType The notification type to delete.
     *
     * @throws IllegalArgumentException If notificationType is <code>null</code> or its id is UNSET_ID.
     * @throws ResourcePersistenceException If there is an error deleting the notification type in the
     *         persistence
     */
    public void deleteNotificationType(NotificationType notificationType) throws ResourcePersistenceException {
        Util.checkNotificationType(notificationType, true);
        LOGGER.log(Level.INFO, "delete a notification type with id:" + notificationType.getId());

        Connection connection = openConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_DELETE_NOTIFICATION_TYPE);

            statement.setLong(1, notificationType.getId());

            statement.executeUpdate();
        } catch (SQLException ex) {
            closeConnectionOnError(connection);
            LOGGER.log(Level.ERROR, new LogMessage(null, null,
            		"Failed delete a notification type with id:" + notificationType.getId(),ex));
            throw new ResourcePersistenceException("Failed to delete the NotificationType instance.", ex);
        } finally {
            Util.closeStatement(statement);
            closeConnection(connection);
        }

    }

    /**
     * <p>
     * Updates the notification type in the persistence store. The notification type (by id) must exist in the
     * persistence store.
     * </p>
     * <p>
     * Any <code>SQLException</code> or <code>DBConnectionException</code> should be wrapped in a
     * <code>ResourcePersistenceException</code>.
     * </p>
     *
     * @param notificationType The notification type to update
     * @throws IllegalArgumentException If notificationType is <code>null</code> or its id is UNSET_ID or
     *         its name/description is <code>null</code> or its modification user/date is <code>null</code>
     * @throws ResourcePersistenceException if there is an error updating the notification type in the
     *         persistence
     */
    public void updateNotificationType(NotificationType notificationType) throws ResourcePersistenceException {
        Util.checkNotificationType(notificationType, false);

        LOGGER.log(Level.INFO, "Update a notification type with id:" + notificationType.getId());

        Connection connection = openConnection();

        PreparedStatement statement = null;
        int index = 1;
        try {
            statement = connection.prepareStatement(SQL_UPDATE_NOTIFICATION_TYPE);
            statement.setString(index++, notificationType.getName());
            statement.setString(index++, notificationType.getDescription());
            statement.setString(index++, notificationType.getModificationUser());
            statement
                .setTimestamp(index++, Util.dateToTimestamp(notificationType.getModificationTimestamp()));
            statement.setLong(index, notificationType.getId());

            statement.executeUpdate();

        } catch (SQLException ex) {
            closeConnectionOnError(connection);
            LOGGER.log(Level.ERROR, new LogMessage(null, null,
            		"Failed update a notification type with id:" + notificationType.getId(),ex));
            throw new ResourcePersistenceException("Failed to update the notificationType instance.", ex);
        } finally {
            closeConnection(connection);
        }
    }

    /**
     * <p>
     * Loads the notification type from the persistence with the given id. Returns <code>null</code> if
     * there is no notification type with the given id.
     * </p>
     * <p>
     * Any <code>SQLException</code> or <code>DBConnectionException</code> should be wrapped in a
     * <code>ResourcePersistenceException</code>.
     * </p>
     *
     * @return The loaded notification type
     * @param notificationTypeId The id of the notification type to load
     * @throws IllegalArgumentException If notificationTypeId is <= 0
     * @throws ResourcePersistenceException If there is an error loading the notification type
     */
    public NotificationType loadNotificationType(long notificationTypeId) throws ResourcePersistenceException {
        Util.checkPositiveValue(notificationTypeId, "notificationTypeId");

        LOGGER.log(Level.INFO, "load a notification type with id:" + notificationTypeId);

        Connection connection = openConnection();
        try {
            return loadNotificationType(connection, notificationTypeId);
        } catch (SQLException e) {
            closeConnectionOnError(connection);
            LOGGER.log(Level.ERROR, new LogMessage(null, null,
            		"Failed load a notification type with id:" + notificationTypeId, e));
            throw new ResourcePersistenceException("Fail to load notification type.", e);
        } finally {
            closeConnection(connection);
        }

    }

    /**
     * <p>
     * Adds a resource role to the persistence store. The id of the resource role must already be assigned to
     * the notificationType object passed to this method, and not already exist in the persistence source.
     * </p>
     *
     * @param resourceRole The resource role to add.
     *
     * @throws IllegalArgumentException If resourceRole is <code>null</code> or its id is UNSET_ID or its
     *         name/description is <code>null</code> or its creation/modification date/user is
     *         <code>null</code>
     * @throws ResourcePersistenceException If there is an error updating the persistence
     */
    public void addResourceRole(ResourceRole resourceRole) throws ResourcePersistenceException {
        Util.checkResourceRole(resourceRole, false);

        LOGGER.log(Level.INFO, "add ResourceRole with id:" + resourceRole.getId() + " name:" + resourceRole.getName());

        Connection connection = openConnection();
        PreparedStatement statement = null;

        int index = 1;
        try {
            statement = connection.prepareStatement(SQL_INSERT_RES_ROLE);

            statement.setLong(index++, resourceRole.getId());
            statement.setString(index++, resourceRole.getName());
            statement.setString(index++, resourceRole.getDescription());
            statement.setObject(index++, resourceRole.getPhaseType());

            statement.setString(index++, resourceRole.getCreationUser());
            statement.setTimestamp(index++, Util.dateToTimestamp(resourceRole.getCreationTimestamp()));
            statement.setString(index++, resourceRole.getModificationUser());
            statement.setTimestamp(index, Util.dateToTimestamp(resourceRole.getModificationTimestamp()));

            statement.executeUpdate();
        } catch (SQLException e) {
            closeConnectionOnError(connection);
            LOGGER.log(Level.ERROR, new LogMessage(null, null,
            		"Failed add a resource role with id:" + resourceRole.getId(), e));
            throw new ResourcePersistenceException("Failed to add the ResourceRole instance.", e);
        } finally {
            Util.closeStatement(statement);
            closeConnection(connection);
        }
    }

    /**
     * <p>
     * Removes a resource role from the persistence store. If no resource role exists with the given id,
     * nothing is done.
     * </p>
     *
     * @param resourceRole The notification type to delete.
     *
     * @throws IllegalArgumentException If notificationType is <code>null</code> or its id is UNSET_ID.
     * @throws ResourcePersistenceException If there is an error updating the persistence
     */
    public void deleteResourceRole(ResourceRole resourceRole) throws ResourcePersistenceException {
        Util.checkResourceRole(resourceRole, true);

        LOGGER.log(Level.INFO, "Delete ResourceRole with id:" + resourceRole.getId());
        
        Connection connection = openConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(SQL_DELETE_RES_ROLE);
            statement.setLong(1, resourceRole.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            closeConnectionOnError(connection);
            LOGGER.log(Level.ERROR, new LogMessage(null, null,
            		"Failed Delete a resource role with id:" + resourceRole.getId(), e));
            throw new ResourcePersistenceException("Failed to delete the ResourceRole instance.", e);
        } finally {
            Util.closeStatement(statement);
            closeConnection(connection);
        }
    }

    /**
     * <p>
     * Updates the resource role in the persistence store. The resource role (by id) must exist in the
     * persistence store.
     * </p>
     *
     * @param resourceRole The resource role to update
     * @throws ResourcePersistenceException
     * @throws IllegalArgumentException If resourceRole is <code>null</code> or its id is UNSET_ID or its
     *         name/description is <code>null</code> or its modification user/date is <code>null</code>
     * @throws ResourcePersistenceException if there is an error updating the persistence
     */
    public void updateResourceRole(ResourceRole resourceRole) throws ResourcePersistenceException {
        Util.checkResourceRole(resourceRole, false);

        LOGGER.log(Level.INFO, "update ResourceRole with id:" + resourceRole.getId());
        
        Connection connection = openConnection();

        PreparedStatement statement = null;

        int index = 1;
        try {
            statement = connection.prepareStatement(SQL_UPDATE_RES_ROLE);

            statement.setObject(index++, resourceRole.getPhaseType());
            statement.setString(index++, resourceRole.getName());
            statement.setString(index++, resourceRole.getDescription());
            statement.setString(index++, resourceRole.getModificationUser());
            statement.setTimestamp(index++, Util.dateToTimestamp(resourceRole.getModificationTimestamp()));
            statement.setLong(index, resourceRole.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            closeConnectionOnError(connection);
            LOGGER.log(Level.ERROR, new LogMessage(null, null,
            		"Failed update a resource role with id:" + resourceRole.getId(), e));
            throw new ResourcePersistenceException("Failed to update the ResourceRole instance.", e);
        } finally {
            Util.closeStatement(statement);
            closeConnection(connection);
        }
    }

    /**
     * <p>
     * Loads the resource role from the persistence with the given id. Returns <code>null</code> if there is
     * no resource role with the given id.
     * </p>
     *
     * @return The loaded resource role
     * @param resourceRoleId The id of the resource role to load
     *
     * @throws IllegalArgumentException If resourceRoleId is <= 0
     * @throws ResourcePersistenceException If there is an error loading the resource role
     */
    public ResourceRole loadResourceRole(long resourceRoleId) throws ResourcePersistenceException {
        Util.checkPositiveValue(resourceRoleId, "resourceRoleId");

        LOGGER.log(Level.INFO, "load ResourceRole with id:" + resourceRoleId);
        
        Connection connection = openConnection();
        try
        {

            return loadResourceRole(resourceRoleId, connection);

        } finally {
            
            closeConnection(connection);
        }
    }


    /**
     * <p>
     * Loads the resource role from the persistence with the given id. Returns <code>null</code> if there is
     * no resource role with the given id.
     * </p>
     *
     * @return The loaded resource role
     * @param resourceRoleId The id of the resource role to load
     *
     * @throws IllegalArgumentException If resourceRoleId is <= 0
     * @throws ResourcePersistenceException If there is an error loading the resource role
     */
    private ResourceRole loadResourceRole(long resourceRoleId, Connection connection) throws ResourcePersistenceException {
        Util.checkPositiveValue(resourceRoleId, "resourceRoleId");

        LOGGER.log(Level.INFO, "load ResourceRole with id:" + resourceRoleId);
        
        ResultSet rs = null;
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(SQL_SELECT_RES_ROLE);
            statement.setLong(1, resourceRoleId);

            rs = statement.executeQuery();

            if (rs.next()) {
                return constructResourceRole(rs);
            }

        } catch (SQLException e) {
            closeConnectionOnError(connection);
            LOGGER.log(Level.ERROR, new LogMessage(null, null,
            		"Failed load a resource role with id:" + resourceRoleId, e));
            throw new ResourcePersistenceException("Failed to load ResourceRole instance.", e);
        } finally {
            Util.closeResultSet(rs);
            Util.closeStatement(statement);
            
        }

        return null;
    }

    /**
     * Constructs the <code>ResourceRole</code> instance from the given <code>ResultSet</code> instance.
     *
     * @param rs the ResultSet instance
     * @return the <code>ResourceRole</code> instance
     * @throws SQLException if failed to get the <code>ResourceRole</code> instance from the
     *         <code>ResultSet</code>.
     */
    private ResourceRole constructResourceRole(ResultSet rs) throws SQLException {
        ResourceRole role = new ResourceRole();
        int index = 3;
        role.setId(rs.getLong(1));

        if (rs.getObject(2) == null) {
            role.setPhaseType(null);
        } else {
            role.setPhaseType(new Long(rs.getLong(2)));
        }

        role.setName(rs.getString(index++));
        role.setDescription(rs.getString(index++));
        role.setCreationUser(rs.getString(index++));
        role.setCreationTimestamp(rs.getTimestamp(index++));
        role.setModificationUser(rs.getString(index++));
        role.setModificationTimestamp(rs.getTimestamp(index++));

        return role;
    }

    /**
     * Construct the ResourceRole instance from the given ResultSet instance.
     *
     * @param rs
     *            the ResultSet instance
     * @return the ResourceRole instance
     * @throws InvalidCursorStateException
     *             if failed to get the ResourceRole instance from the ResultSet.
     */
    private ResourceRole constructResourceRole(CustomResultSet rs) throws InvalidCursorStateException, NullColumnValueException {
        ResourceRole role = new ResourceRole();

        role.setId(rs.getLong("resource_role_id"));

        if (rs.getObject("phase_type_id") == null) {
            role.setPhaseType(null);
        } else {
            role.setPhaseType(new Long(rs.getLong("phase_type_id")));
        }

        role.setName(rs.getString("name"));
        role.setDescription(rs.getString("description"));
        role.setCreationUser(rs.getString("create_user"));
        role.setCreationTimestamp(rs.getTimestamp("create_date"));
        role.setModificationUser(rs.getString("modify_user"));
        role.setModificationTimestamp(rs.getTimestamp("modify_date"));

        return role;
    }

    /**
     * <p>
     * Loads the resources from the persistence with the given ids. May return a 0-length array.
     * </p>
     *
     * @param resourceIds The ids of resources to load
     * @return The loaded resources
     * @throws IllegalArgumentException If any id is <= 0 or the array is <code>null</code>
     * @throws ResourcePersistenceException If there is an error loading the Resources
     */
    public Resource[] loadResources(long[] resourceIds) throws ResourcePersistenceException {
        Util.checkLongArray(resourceIds, "resourceIds");

        if (resourceIds.length == 0) {
            return new Resource[0];
        }

        String idString = getIdString(resourceIds);
        LOGGER.log(Level.INFO, "load Resource with ids:" + idString);

        Connection connection = openConnection();

        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            statement = connection.prepareStatement(buildQueryWithIds(SQL_SELECT_ALL_RES_WITH_ROLE, resourceIds));
            rs = statement.executeQuery();

            List list = new ArrayList();

            while (rs.next()) {
                list.add(constructResourceWithRole(rs, connection));
            }

            Resource[] resources = (Resource[]) list.toArray(new Resource[list.size()]);

            // get resource submissions.
            Map resSubmissions = getResourceSubmissions(connection, resourceIds);
            for (int i = 0; i < resources.length; i++) {
                Resource resource = resources[i];

                List submissions = (List) resSubmissions.get(new Long(resource.getId()));

                if (submissions == null) {
                    resource.setSubmissions(new Long[0]);
                } else {
                    resource.setSubmissions((Long[]) submissions.toArray(new Long[submissions.size()]));
                }
            }

            // select all the external properties once and add the matching properties into resource
            // instances.
            Map map = getAllExternalProperties(connection, resourceIds);

            for (int i = 0; i < resources.length; i++) {
                Resource resource = resources[i];
                Map properties = (Map) map.get(new Long(resource.getId()));

                if (properties == null) {
                    continue;
                }

                for (Iterator iter = properties.entrySet().iterator(); iter.hasNext();) {
                    Map.Entry entry = (Entry) iter.next();

                    resource.setProperty(entry.getKey().toString(), entry.getValue().toString());
                }
            }

            return resources;

        } catch (SQLException e) {
            closeConnectionOnError(connection);
            LOGGER.log(Level.ERROR, new LogMessage(null, null, "Failed to load Resources with ids:" + idString,e));
            throw new ResourcePersistenceException("Failed to load all the resources.", e);
        } finally {
            Util.closeResultSet(rs);
            Util.closeStatement(statement);
            
            closeConnection(connection);
        }
    }

    /**
     * Return the id string seperated by comma for the given long id array.
     * 
     * @param ids the id array
     * @return string seperated by comma
     */
	private String getIdString(long[] ids) {
		if (ids == null || ids.length == 0) {
			return "";
		}
		StringBuilder idString = new StringBuilder();
        for(int i = 0; i < ids.length; i++) {
        	idString.append(',').append(ids);
        }
		return idString.substring(1);
	}

	/**
     * Return the id string seperated by comma for the given long id array.
     * 
     * @param ids the id array
     * @return string seperated by comma
     */
	private String getIdString(Object[] ids) {
		if (ids == null || ids.length == 0) {
			return "";
		}
		StringBuilder idString = new StringBuilder();
        for(int i = 0; i < ids.length; i++) {
        	idString.append(',').append(ids.toString());
        }
		return idString.substring(1);
	}
	
    /**
     * Loads the resources from the result of the SELECT operation. May return an empty array.
     *
     * @return The loaded resources
     * @param resultSet
     *            The result of the SELECT operation.
     * @throws IllegalArgumentException
     *             If any id is <= 0 or the array is null
     * @throws ResourcePersistenceException
     *             If there is an error loading the Resources
     */
    public Resource[] loadResources(CustomResultSet resultSet) throws ResourcePersistenceException {
        Util.checkNull(resultSet, "resultSet");

        if (resultSet.getRecordCount() == 0) {
            return new Resource[0];
        }

        Connection connection = openConnection();

        try {
            List list = new ArrayList();

            while (resultSet.next()) {
                list.add(constructResourceWithRole(resultSet, connection));
            }

            Resource[] resources = (Resource[]) list.toArray(new Resource[list.size()]);
            long[] resourceIds = new long[resources.length];

            for (int i = 0; i < resources.length; ++i) {
                resourceIds[i] = resources[i].getId();
            }

            // get resource submissions.
            Map resSubmissions = getResourceSubmissions(connection, resourceIds);
            for (int i = 0; i < resources.length; i++) {
                Resource resource = resources[i];

                List submissions = (List) resSubmissions.get(new Long(resource.getId()));

                if (submissions == null) {
                    resource.setSubmissions(new Long[0]);
                } else {
                    resource.setSubmissions((Long[]) submissions.toArray(new Long[submissions.size()]));
                }
            }

            // select all the external properties once and add the matching properties into resource instances
            Map map = getAllExternalProperties(connection, resourceIds);

            for (int i = 0; i < resources.length; ++i) {
                Resource resource = resources[i];
                Map properties = (Map) map.get(new Long(resource.getId()));

                if (properties == null) {
                    continue;
                }

                for (Iterator iter = properties.entrySet().iterator(); iter.hasNext();) {
                    Map.Entry entry = (Entry) iter.next();

                    resource.setProperty(entry.getKey().toString(), entry.getValue().toString());
                }
            }

            return resources;
        } catch (ResourcePersistenceException rpe) {
            closeConnectionOnError(connection);
            throw rpe;
        } finally {
            closeConnection(connection);
        }
    }

    /**
     * Builds a select sql query with an argument contains many long values. The structure of the result
     * string looks like this: ... in ( id, id, id, id...).
     *
     * @param baseQuery the sql query
     * @param ids the ids for select sql query
     * @return the result string
     */
    private String buildQueryWithIds(String baseQuery, long[] ids) {
        StringBuffer buffer = new StringBuffer(baseQuery);
        for (int i = 0; i < ids.length; i++) {
            if (i > 0) {
                buffer.append(',');
            }

            buffer.append(ids[i]);
        }

        buffer.append(')');

        return buffer.toString();
    }

    /**
     * Gets all the external properties with one select sql query. Here a <code>HashMap</code> structure is
     * used. The key is an <code>Integer</code> of resourceId and the value is another map which contains
     * the key/value of external properties.
     *
     * @param connection the connection to database
     * @param resourceIds the resourceIds for retrieving external properties
     * @return a <code>Map</code> contained all external properties.
     * @throws ResourcePersistenceException if failed to select all external properties at once.
     */
    private Map getAllExternalProperties(Connection connection, long[] resourceIds)
        throws ResourcePersistenceException {

        // Map from resource id to a Map containing the properties of the resource.
        Map resourcesProperties = new HashMap();

        PreparedStatement statement = null;
        ResultSet rs = null;

        int index;
        try {
            statement = connection.prepareStatement(buildQueryWithIds(SQL_SELECT_EXT_PROPS, resourceIds));

            rs = statement.executeQuery();

            while (rs.next()) {
                index = 1;
                Long resourceId = new Long(rs.getLong(index++));

                String key = rs.getString(index++);
                String value = rs.getString(index);

                Map properties = (Map) resourcesProperties.get(resourceId);
                if (properties == null) {
                    properties = new HashMap();
                    resourcesProperties.put(resourceId, properties);
                }
                properties.put(key, value);
            }
            return resourcesProperties;
        } catch (SQLException e) {
        	LOGGER.log(Level.ERROR, new LogMessage(null, null,
        			"Failed for getting all external properties for all resourceIds.",e));
            throw new ResourcePersistenceException(
                "Failed for getting all external properties for all resourceIds.", e);
        } finally {
            Util.closeResultSet(rs);
            Util.closeStatement(statement);
        }
    }

    /**
     * <p>
     * Loads the notification types from the persistence with the given ids. May return a 0-length array.
     * </p>
     *
     * @param notificationTypeIds The ids of notification types to load
     * @return The loaded notification types
     * @throws ResourcePersistenceException
     * @throws IllegalArgumentException If any id is <= 0 or the array is <code>null</code>
     * @throws ResourcePersistenceException If there is an error loading from persistence
     */
    public NotificationType[] loadNotificationTypes(long[] notificationTypeIds)
        throws ResourcePersistenceException {
        Util.checkLongArray(notificationTypeIds, "notificationTypeIds");

        if (notificationTypeIds.length == 0) {
            return new NotificationType[0];
        }

        String idString = this.getIdString(notificationTypeIds);
        LOGGER.log(Level.INFO, "load NotificationType with ids:" + idString);

        Connection connection = openConnection();
        ResultSet rs = null;
        PreparedStatement statement = null;

        try {
            statement =
                connection.prepareStatement(buildQueryWithIds(SQL_SELECT_NOTIFICATION_TYPES,
                    notificationTypeIds));

            rs = statement.executeQuery();

            List list = new ArrayList();

            while (rs.next()) {
                list.add(constructNotificationType(rs));
            }

            return (NotificationType[]) list.toArray(new NotificationType[list.size()]);

        } catch (SQLException e) {
            closeConnectionOnError(connection);
            LOGGER.log(Level.ERROR, new LogMessage(null, null,
        			"Failed for getting all NotificationType with ids:" + idString, e));
            throw new ResourcePersistenceException("Failed to load NotificationTypes instances.", e);
        } finally {
            Util.closeResultSet(rs);
            Util.closeStatement(statement);
            
            closeConnection(connection);
        }

    }

    /**
     * Loads the notification types from the result of the SELECT operation. This method may return a
     * zero-length array. It is designed to keep the amount of SQL queries to a minimum when
     * searching notification types.
     *
     * @param resultSet
     *            The result of the SELECT operation. This result should have the following columns:
     *            <ul>
     *            <li>notification_type_id</li>
     *            <li>name</li>
     *            <li>description</li>
     *            <li>as create_user</li>
     *            <li>as create_date</li>
     *            <li>as modify_user</li>
     *            <li>as modify_date</li>
     *            </ul>
     * @return The loaded notification types
     * @throws IllegalArgumentException If any id is &lt;= 0 or the array is null
     * @throws ResourcePersistenceException If there is an error loading from persistence
     */
    public NotificationType[] loadNotificationTypes(CustomResultSet resultSet) throws ResourcePersistenceException {
        Util.checkNull(resultSet, "resultSet");

        if (resultSet.getRecordCount() == 0) {
            return new NotificationType[0];
        }

        try {
            List list = new ArrayList();

            while (resultSet.next()) {
                list.add(constructNotificationType(resultSet));
            }

            return (NotificationType[]) list.toArray(new NotificationType[list.size()]);
        } catch (InvalidCursorStateException icse) {
            throw new ResourcePersistenceException("Failed to load NotificationType instances.", icse);
        } catch (NullColumnValueException ne) {
            throw new ResourcePersistenceException("Failed to load NotificationType instances.", ne);
        }
    }

    /**
     * Constructs the <code>NotificationType</code> instance from the given <code>ResultSet</code>
     * instance.
     *
     * @param rs the <code>ResultSet</code> instance
     * @return NotifcationType instance.
     * @throws SQLException if failed to load the notificationType instance from the database.
     */
    private NotificationType constructNotificationType(ResultSet rs) throws SQLException {
        NotificationType type = new NotificationType();
        int index = 1;
        type.setId(rs.getLong(index++));
        type.setName(rs.getString(index++));
        type.setDescription(rs.getString(index++));
        type.setCreationUser(rs.getString(index++));
        type.setCreationTimestamp(rs.getTimestamp(index++));
        type.setModificationUser(rs.getString(index++));
        type.setModificationTimestamp(rs.getTimestamp(index));
        return type;
    }

    /**
     * Constructs the <code>NotificationType</code> instance from the given <code>CustomResultSet</code>
     * instance.
     *
     * @param rs the <code>CustomResultSet</code> instance
     * @return NotifcationType instance.
     * @throws InvalidCursorStateException if failed to load the notificationType instance from the database.
     */
    private NotificationType constructNotificationType(CustomResultSet rs) throws InvalidCursorStateException,NullColumnValueException {
        NotificationType type = new NotificationType();
        type.setId(rs.getLong("notification_type_id"));
        type.setName(rs.getString("name"));
        type.setDescription(rs.getString("description"));
        type.setCreationUser(rs.getString("create_user"));
        type.setCreationTimestamp(rs.getTimestamp("create_date"));
        type.setModificationUser(rs.getString("modify_user"));
        type.setModificationTimestamp(rs.getTimestamp("modify_date"));
        return type;
    }

    /**
     * <p>
     * Loads the resource roles from the persistence with the given ids. May return a 0-length array.
     * </p>
     *
     * @param resourceRoleIds The ids of resource roles to load
     * @return The loaded resource roles
     *
     * @throws IllegalArgumentException If any id is <= 0 or the array is <code>null</code>
     * @throws ResourcePersistenceException If there is an error loading from persistence
     */
    public ResourceRole[] loadResourceRoles(long[] resourceRoleIds) throws ResourcePersistenceException {
        Util.checkLongArray(resourceRoleIds, "resourceRoleIds");

        if (resourceRoleIds.length == 0) {
            return new ResourceRole[0];
        }

        String idString = getIdString(resourceRoleIds);
        LOGGER.log(Level.INFO, "load Resource role with ids:" + idString);

        Connection connection = openConnection();

        ResultSet rs = null;
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(buildQueryWithIds(SQL_SELECT_RES_ROLES, resourceRoleIds));

            rs = statement.executeQuery();

            List roles = new ArrayList();

            while (rs.next()) {
                roles.add(constructResourceRole(rs));
            }

            return (ResourceRole[]) roles.toArray(new ResourceRole[roles.size()]);

        } catch (SQLException e) {
            closeConnectionOnError(connection);
            LOGGER.log(Level.ERROR, new LogMessage(null, null,
        			"Failed for getting ResourceRoles ids:" + idString, e));
            throw new ResourcePersistenceException("Failed to load nResourceRole instance.", e);
        } finally {
            Util.closeResultSet(rs);
            Util.closeStatement(statement);

            closeConnection(connection);
        }
    }

    /**
     * Loads the resource roles from the result of the SELECT operation. May return an empty array.
     *
     * @return The loaded resource roles
     * @param resultSet
     *            The result of the SELECT operation.
     * @throws IllegalArgumentException
     *             If any id is <= 0 or the array is null
     * @throws ResourcePersistenceException
     *             If there is an error loading from persistence
     */
    public ResourceRole[] loadResourceRoles(CustomResultSet resultSet) throws ResourcePersistenceException {
        Util.checkNull(resultSet, "resultSet");

        if (resultSet.getRecordCount() == 0) {
            return new ResourceRole[0];
        }

        try {
            List roles = new ArrayList();

            while (resultSet.next()) {
                roles.add(constructResourceRole(resultSet));
            }

            return (ResourceRole[]) roles.toArray(new ResourceRole[roles.size()]);
        } catch (InvalidCursorStateException icse) {
            throw new ResourcePersistenceException("Failed to load nResourceRole instance.", icse);
        } catch (NullColumnValueException ne) {
            throw new ResourcePersistenceException("Failed to load nResourceRole instance.", ne);
        }
    }

    /**
     * <p>
     * Loads the Notifications for the given "id" triples from the persistence store. May return a 0-length
     * array.
     * </p>
     *
     * @param userIds The ids of the users
     * @param projectIds The ids of the projects
     * @param notificationTypes The ids of the notification types
     * @return The loaded notifications
     *
     * @throws IllegalArgumentException If the three arrays don't all have the same number of elements (or any
     *         array is <code>null</code>) or all three arrays do not have the same length, any id is <= 0
     * @throws ResourcePersistenceException If there is an error loading from the persistence
     */
    public Notification[] loadNotifications(long[] userIds, long[] projectIds, long[] notificationTypes)
        throws ResourcePersistenceException {
        Util.checkLongArray(userIds, "userIds");
        Util.checkLongArray(projectIds, "projectIds");
        Util.checkLongArray(notificationTypes, "notificationTypes");

        if (userIds.length != projectIds.length || projectIds.length != notificationTypes.length) {
            throw new IllegalArgumentException("All three arrays do not have the same length.");
        }

        if (userIds.length == 0) {
            return new Notification[0];
        }

        LOGGER.log(Level.INFO, "load Notifications with array of userIds/projectIds/notificationTypes.");

        // construct the sql query.
        StringBuffer buffer = new StringBuffer(SQL_SELECT_NOTIFICATIONS);

        for (int i = 0; i < userIds.length; i++) {
            if (i > 0) {
                buffer.append(" OR ");
            }

            buffer.append('(');
            buffer.append("project_id = " + projectIds[i]);
            buffer.append(" AND external_ref_id = " + userIds[i]);
            buffer.append(" AND notification_type_id = " + notificationTypes[i]);
            buffer.append(')');
        }
        // end of constructing sql query.

        Connection connection = openConnection();

        ResultSet rs = null;
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(buffer.toString());

            rs = statement.executeQuery();

            List list = new ArrayList();

            while (rs.next()) {
                list.add(constructNotification(connection, rs));
            }

            return (Notification[]) list.toArray(new Notification[list.size()]);

        } catch (SQLException e) {
            closeConnectionOnError(connection);
            LOGGER.log(Level.INFO, new LogMessage(null, null,
            		"Failed to load Notifications with array of userIds/projectIds/notificationTypes.", e));
            throw new ResourcePersistenceException("Failed to load Notification instances.", e);
        } finally {
            Util.closeResultSet(rs);
            Util.closeStatement(statement);

            closeConnection(connection);
        }
    }

    /**
     * Load Notifications from the result of the SELECT operation. May return an empty array.
     *
     * @return The loaded notifications
     * @param resultSet
     *            The result of the SELECT operation.
     * @throws IllegalArgumentException
     *             If the three arrays don't all have the same number of elements (or any array is null) or all three
     *             arrays do not have the same length, any id is <= 0
     * @throws ResourcePersistenceException
     *             If there is an error loading from the persistence
     */
    public Notification[] loadNotifications(CustomResultSet resultSet) throws ResourcePersistenceException {
        Util.checkNull(resultSet, "resultSet");

        if (resultSet.getRecordCount() == 0) {
            return new Notification[0];
        }

        Connection connection = openConnection();

        try {
            List notifications = new ArrayList();

            while (resultSet.next()) {
                notifications.add(consructNotification(connection, resultSet));
            }

            return (Notification[]) notifications.toArray(new Notification[notifications.size()]);
        } catch (ResourcePersistenceException rpe) {
            closeConnectionOnError(connection);
            throw rpe;
        } finally {
            closeConnection(connection);
        }
    }

    /**
     * <p>
     * Gets the connectionName, which might be <code>null</code> or empty.
     * </p>
     *
     * @return connectionName - a possibly null or empty String
     */
    protected String getConnectionName() {
        return connectionName;
    }

    /**
     * <p>
     * Return connectionFactory, which will be a non-null <code>DBConnectionFactory</code>.
     * </p>
     *
     * @return connectionFactory - a non-null DBConnectionFactory
     */
    protected DBConnectionFactory getConnectionFactory() {
        return connectionFactory;
    }

    /**
     * <p>
     * Abstract method used to get an open connection to from which to get Statements. Used from all public
     * methods.
     * </p>
     * <p>
     * Subclasses will get a connection and properly prepare it for use, depending on the transaction
     * management strategy used in the subclass.
     * </p>
     *
     * @return an open Connection, ready for use
     * @throws ResourcePersistenceException if an exception is thrown while trying to get or open the
     *         connection
     */
    protected abstract Connection openConnection() throws ResourcePersistenceException;

    /**
     * <p>
     * Abstract method used to commit any transaction (if necessary in the subclass) and to close the given
     * connection after an operation completes successfully. Used from all public methods.
     * </p>
     * <p>
     * Subclasses will close the given connection. Depending on the transaction management strategy used in
     * the subclass, a transaction on the connection may be committed..
     * </p>
     *
     * @param connection Connection to close
     * @throws ResourcePersistenceException if any exception is thrown while committing any transaction or
     *         closing the connection
     * @throws IllegalArgumentException if the argument is <code>null</code>
     */
    protected abstract void closeConnection(Connection connection) throws ResourcePersistenceException;

    /**
     * <p>
     * Abstract method used to rollback any transaction (if necessary in the subclass) and to close the given
     * connection when an error occurs. Used from all public methods just before any exception is thrown.
     * </p>
     * <p>
     * Subclasses will close the given connection. Depending on the transaction management strategy used in
     * the subclass, a transaction on the connection may be rolled back.
     * </p>
     *
     *
     * @param connection Connection to close
     * @throws ResourcePersistenceException if any exception is thrown while committing any transaction or
     *         closing the connection
     * @throws IllegalArgumentException if the argument is <code>null</code>
     */
    protected abstract void closeConnectionOnError(Connection connection) throws ResourcePersistenceException;
    /**
     * This method retrieves the resource role id for a given resource.
     *
     * @param connection the connection to database
     * @param resourceId the resource id being queried
     * @return the resource role id if it exists or null otherwise
     * @throws ResourcePersistenceException if an error occurs in the underlying layer.
     *
     * @since 1.2.2
     */
    private Long getResourceRoleId(Connection connection, long resourceId) throws ResourcePersistenceException {
        ResultSet rs = null;
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(buildQueryWithIds(SQL_SELECT_ALL_RES, new long[] {resourceId}));

            rs = statement.executeQuery();
            Long resourceRoleId = null;
            if (rs.next()) {
                resourceRoleId = rs.getLong(2);
            }

            return resourceRoleId;
        } catch (SQLException e) {
            throw new ResourcePersistenceException("Failed to get resource role id for resource.", e);
        } finally {
            Util.closeResultSet(rs);
            Util.closeStatement(statement);
            
        }
    }

    /**
     * This method will audit project user information. This information is generated when a resource is added,
     * deleted or changes its user or role.
     *
     * @param connection the connection to database
     * @param resource the resource being audited
     * @param auditType the audit type. Can be PROJECT_USER_AUDIT_CREATE_TYPE or PROJECT_USER_AUDIT_DELETE_TYPE.
     * @param userId the resource user id. This value overrides the value inside resource if present.
     * @param resourceRoleId the resource role id. This value overrides the value inside resource if present.
     *
     * @throws ResourcePersistenceException if validation error occurs or any error occurs in the underlying layer
     *
     * @since 1.2.2
     */
    private void auditProjectUser(Connection connection, Resource resource, int auditType, Long userId,
        Long resourceRoleId) throws ResourcePersistenceException {

        // decide which user id to use
        if (userId == null && !resource.hasProperty(EXTERNAL_REFERENCE_ID_PROPERTY_KEY)) {
            throw new ResourcePersistenceException("Audit information was not successfully saved " +
                "since resource doesn't have external reference id");
        }
        if (resource.getProject() == null) {
            throw new ResourcePersistenceException("Audit information was not successfully saved " +
                "since resource doesn't have project id.");
        }

        long resourceUserId;
        if (userId != null) {
            resourceUserId = userId;
        } else {
            try {
                resourceUserId = Long.parseLong(resource.getProperty(EXTERNAL_REFERENCE_ID_PROPERTY_KEY).toString());
            } catch (NumberFormatException e1) {
                throw new ResourcePersistenceException("Audit information was not successfully saved " +
                    "since resource's external reference id is invalid");
            }
        }

        // save audit information
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_INSERT_PROJECT_USER_AUDIT);

            int index = 1;
            statement.setLong(index++, resource.getProject());
            statement.setLong(index++, resourceUserId);
            statement.setLong(index++, (resourceRoleId != null) ? resourceRoleId : resource.getResourceRole().getId());
            statement.setInt(index++, auditType);
            statement.setTimestamp(index++, Util.dateToTimestamp(resource.getModificationTimestamp()));
            statement.setLong(index++, Long.parseLong(resource.getModificationUser()));

            if (statement.executeUpdate() != 1) {
                throw new ResourcePersistenceException("Audit information was not successfully saved.");
            }
        } catch (SQLException e) {
            closeConnectionOnError(connection);
            throw new ResourcePersistenceException("Unable to insert project_user_audit.", e);
        } finally {
            Util.closeStatement(statement);
        }
    }



    /**
     * cehck if resource exists
     *
     * @param projectId project id
     * @param roleId role 
     * @param userId user id
     *
     * @return boolean
     *
     * @throws ResourcePersistenceException if there is an error reading the persistence store.
     */
    public boolean resourceExists(long projectId, long roleId, long userId) throws ResourcePersistenceException {

        Connection connection = openConnection();

        ResultSet rs = null;
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(SQL_RESOURCE_EXISTS);

            int index = 1;
            statement.setLong(index++, projectId);
            statement.setLong(index++, roleId);
            statement.setString(index++, String.valueOf(userId));

            rs = statement.executeQuery();

            if (rs.next()) {
                return true;
            }

            return false;

        } catch (SQLException e) {
            closeConnectionOnError(connection);
            LOGGER.log(Level.ERROR, new LogMessage(null, null,
        			"Failed in checking resource", e));
            throw new ResourcePersistenceException("Failed in resourceExists.", e);
        } finally {
            Util.closeResultSet(rs);
            Util.closeStatement(statement);

            closeConnection(connection);
        }
    }

}
