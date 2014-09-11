/*
 * Copyright (c) 2006 - 2010, TopCoder, Inc. All rights reserved.
 */



package com.topcoder.management.deliverable.persistence.sql;

import com.topcoder.db.connectionfactory.DBConnectionException;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.management.deliverable.Submission;
import com.topcoder.management.deliverable.SubmissionStatus;
import com.topcoder.management.deliverable.SubmissionType;
import com.topcoder.management.deliverable.Upload;
import com.topcoder.management.deliverable.UploadStatus;
import com.topcoder.management.deliverable.UploadType;
import com.topcoder.management.deliverable.persistence.UploadPersistence;
import com.topcoder.management.deliverable.persistence.UploadPersistenceException;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogFactory;
import com.topcoder.util.sql.databaseabstraction.CustomResultSet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * A database upload Persistence class for testing.
 *
 * @author Chenhong, morehappiness
 * @version 1.1
 */
public class MyUploadPersistence implements UploadPersistence {
    private static final Log logger = LogFactory.getLog(MyUploadPersistence.class.getName());

    /**
     * The connection factory to use when a connection to the database is needed. This field is immutable and must be
     * non-null.
     */
    private final DBConnectionFactory connectionFactory;

    /**
     * The name of the connection producer to use when a connection to the database is retrieved from the
     * DBConnectionFactory.
     *
     */
    private final String connectionName;

    /**
     * Creates a new SqlUploadPersistence. The connectionName field is set to null.
     *
     *
     * @param connectionFactory
     *            The connection factory to use for getting connections to the database.
     * @throws IllegalArgumentException
     *             If connectionFactory is null.
     */
    public MyUploadPersistence(DBConnectionFactory connectionFactory) {
        this(connectionFactory, null);
    }

    /**
     * SqlUploadPersistence constructor: Creates a new SqlUploadPersistence.
     *
     *
     * @param connectionFactory
     *            The connection factory to use for getting connections to the database.
     * @param connectionName
     *            The name of the connection to use. Can be null.
     * @throws IllegalArgumentException
     *             If connectionFactory is null or connectionName is empty.
     */
    public MyUploadPersistence(DBConnectionFactory connectionFactory, String connectionName) {
        this.connectionFactory = connectionFactory;
        this.connectionName = connectionName;
    }

    /**
     * <p>
     * Creates the database connection using the DbConnectionFactory. If the <code>connectionName</code> is
     * <code>null</code>, default connection will be created.
     * </p>
     *
     * @return the database connection.
     * @throws UploadPersistenceException
     *             if error occurs while creating database connection.
     */
    private Connection createConnection() throws UploadPersistenceException {
        try {
            Connection conn = null;

            if (connectionName == null) {
                conn = connectionFactory.createConnection();
            } else {
                conn = connectionFactory.createConnection(connectionName);
            }

            return conn;
        } catch (DBConnectionException ex) {
            throw new UploadPersistenceException("Error occurs while creating connection: " + ex);
        }
    }

    /**
     * <p>
     * addUploadType: Adds the given uploadType to the persistence. The id of the upload type must already be assigned,
     * as must all the other fields needed for persistence.
     * </p>
     *
     * @param uploadType
     *            The upload type to add
     * @throws IllegalArgumentException
     *             If uploadType is null
     * @throws IllegalArgumentException
     *             If isValidToPersist returns false
     * @throws UploadPersistenceException
     *             If there is an error making the change in the persistence
     */
    public void addUploadType(UploadType uploadType) throws UploadPersistenceException {
        String query = "INSERT INTO upload_type_lu (upload_type_id, name, description, create_user, create_date,"
                       + "modify_user, modify_date) VALUES (?, ?, ?, ?, ?, ?, ?)";

        List values = new ArrayList();

        values.add(new Long(uploadType.getId()));
        values.add(uploadType.getName());
        values.add(uploadType.getDescription());
        values.add(uploadType.getCreationUser());
        values.add(uploadType.getCreationTimestamp());
        values.add(uploadType.getModificationUser());
        values.add(uploadType.getModificationTimestamp());

        executeSingleCommand(query, values);
    }

    /**
     * Executes given query with the values. It creates the connection, executes command and closes everything.
     *
     * @param query
     *            the query to be used.
     * @param values
     *            the query values.
     * @return the number of affected rows.
     * @throws UploadPersistenceException
     *             if any error occurs during operation.
     */
    private int executeSingleCommand(String query, List values) throws UploadPersistenceException {
        Connection conn = createConnection();

        try {
            return executeUpdate(conn, query, values);
        } catch (SQLException ex) {
            throw new UploadPersistenceException("Error occurs during database operation.", ex);
        } finally {
            DBUtil.close(conn);
        }
    }

    /**
     * Executes the update operation using the given connection, query and values.
     *
     * @param conn
     *            the database connection to be used.
     * @param query
     *            the SQL query.
     * @param values
     *            the query values.
     * @return the number of affected rows.
     *
     * @throws SQLException
     *             if any database error occurs.
     */
    private int executeUpdate(Connection conn, String query, List values) throws SQLException {
        PreparedStatement pstmt = null;

        try {
            pstmt = conn.prepareStatement(query);
            setValuesToStatement(values, pstmt);

            return pstmt.executeUpdate();
        } finally {
            DBUtil.close(pstmt);
        }

    }

    /**
     * Sets the given values into statement in the order as on the list. The special case is the Date type which ne to
     * be converted to Timestamp first.
     *
     * @param values
     *            the values to be set.
     * @param pstmt
     *            the prepared statement to be set.
     * @throws SQLException
     *             if any error occurs.
     */
    private void setValuesToStatement(List values, PreparedStatement pstmt) throws SQLException {
        for (int i = 0; i < values.size(); i++) {
            if (values.get(i) instanceof Date) {
                pstmt.setTimestamp(i + 1, new Timestamp(((Date) values.get(i)).getTime()));
            } else {
                pstmt.setObject(i + 1, values.get(i));
            }
        }
    }

    /**
     * <p>
     * Removes the given upload type (by id) from the persistence. The id of the upload type can not be UNSET_ID, but
     * the other fields do not matter.
     * </p>
     *
     *
     * @param uploadType
     *            The upload type to remove
     * @throws IllegalArgumentException
     *             If uploadType is null
     * @throws IllegalArgumentException
     *             If the id is UNSET_ID
     * @throws UploadPersistenceException
     *             If there is an error making the change in the persistence
     */
    public void removeUploadType(UploadType uploadType) throws UploadPersistenceException {
        String query = "DELETE FROM upload_type_lu WHERE upload_type_id = ?";
        List values = new ArrayList();

        values.add(new Long(uploadType.getId()));
        executeSingleCommand(query, values);
    }

    /**
     * <p>
     * Updates the given upload type in the persistence. The id of the uploadType can not be UNSET_ID, and all other
     * fields needed for persistence must also be assigned.
     * </p>
     *
     *
     * @param uploadType
     *            The upload type to update
     * @throws IllegalArgumentException
     *             If uploadType is null
     * @throws IllegalArgumentException
     *             If isValidToPersist returns false
     * @throws UploadPersistenceException
     *             If there is an error making the change in the persistence
     */
    public void updateUploadType(UploadType uploadType) throws UploadPersistenceException {
        String query = "UPDATE upload_type_lu SET name = ?, description = ?, modify_user = ?, modify_date = ? "
                       + "WHERE upload_type_id = ?";
        List values = new ArrayList();

        values.add(uploadType.getName());
        values.add(uploadType.getDescription());
        values.add(uploadType.getModificationUser());
        values.add(uploadType.getModificationTimestamp());
        values.add(new Long(uploadType.getId()));

        executeSingleCommand(query, values);
    }

    /**
     * <p>
     * Loads the UploadType with the given id from persistence. Returns null if there is no UploadType with the given
     * id.
     * </p>
     *
     * @return The loaded UploadType or null
     * @param uploadTypeId
     *            The id of the item to retrieve
     * @throws IllegalArgumentException
     *             if uploadTypeId is <= 0
     * @throws UploadPersistenceException
     *             if there is an error reading the persistence data
     */
    public UploadType loadUploadType(long uploadTypeId) throws UploadPersistenceException {
        UploadType[] types = loadUploadTypes(new long[] { uploadTypeId });

        if (types.length > 0) {
            return types[0];
        }

        return null;
    }

    /**
     * Creates the UploadType instance using the values from result set.
     *
     * @param rs
     *            the source result set.
     * @return new UploadType instance.
     * @throws SQLException
     *             if any SQL error occurs.
     */
    private static UploadType populateUploadType(ResultSet rs) throws SQLException {
        UploadType type = new UploadType();

        type.setId(rs.getLong("type_id"));
        type.setName(rs.getString("type_name"));
        type.setDescription(rs.getString("type_description"));
        type.setCreationUser(rs.getString("type_create_user"));
        type.setCreationTimestamp(rs.getTimestamp("type_create_date"));
        type.setModificationUser(rs.getString("type_modify_user"));
        type.setModificationTimestamp(rs.getTimestamp("type_modify_date"));

        return type;
    }

    /**
     * <p>
     * Gets the ids of all upload types in the persistence. The individual upload types can then be loaded with the
     * loadUploadType method.
     * </p>
     *
     * @return The ids of all upload types
     * @throws UploadPersistenceException
     *             If there is an error reading the persistence store
     */
    public long[] getAllUploadTypeIds() throws UploadPersistenceException {
        String query = "SELECT upload_type_id FROM upload_type_lu";

        return selectIds(query);
    }

    /**
     * Executes the query that will have only one column in result - the id of something. The reult will be converted to
     * long array.
     *
     * @param query
     *            the query to select ids.
     * @return the ids array (may be empty).
     * @throws UploadPersistenceException
     *             if any error occurs during operation.
     */
    private long[] selectIds(String query) throws UploadPersistenceException {
        ResultSet rs = null;
        Statement stmt = null;
        Connection conn = createConnection();

        try {
            stmt = conn.createStatement();

            stmt.execute(query);
            rs = stmt.getResultSet();
            List ids = new ArrayList();

            while (rs.next()) {
                ids.add(new Long(rs.getLong(1)));
            }

            return DBUtil.listToArray(ids);
        } catch (SQLException ex) {
            throw new UploadPersistenceException("Error occurs while retrieving the ids.", ex);
        } finally {
            DBUtil.close(rs);
            DBUtil.close(stmt);
            DBUtil.close(conn);
        }
    }

    /**
     * <p>
     * Adds the given uploadStatus to the persistence. The id of the upload status must already be assigned, as must all
     * the other fields needed for persistence.
     * </p>
     *
     * @param uploadStatus
     *            The upload status to add
     * @throws IllegalArgumentException
     *             If uploadStatus is null
     * @throws IllegalArgumentException
     *             If isValidToPersist returns false
     * @throws UploadPersistenceException
     *             If there is an error making the change in the persistence
     */
    public void addUploadStatus(UploadStatus uploadStatus) throws UploadPersistenceException {
        String query = "INSERT INTO upload_status_lu (upload_status_id, name, description, "
                       + "create_user, create_date, modify_user, modify_date) VALUES (?, ?, ?, ?, ?, ?, ?)";

        List values = new ArrayList();

        values.add(new Long(uploadStatus.getId()));
        values.add(uploadStatus.getName());
        values.add(uploadStatus.getDescription());
        values.add(uploadStatus.getCreationUser());
        values.add(uploadStatus.getCreationTimestamp());
        values.add(uploadStatus.getModificationUser());
        values.add(uploadStatus.getModificationTimestamp());

        executeSingleCommand(query, values);
    }

    /**
     * <p>
     * Removes the given upload status (by id) from the persistence. The id of the upload status can not be UNSET_ID,
     * but the other fields do not matter.
     * </p>
     *
     * @param uploadStatus
     *            The upload status to remove
     * @throws IllegalArgumentException
     *             If uploadStatus is null
     * @throws IllegalArgumentException
     *             If the id is UNSET_ID
     * @throws UploadPersistenceException
     *             If there is an error making the change in the persistence
     */
    public void removeUploadStatus(UploadStatus uploadStatus) throws UploadPersistenceException {
        String query = "DELETE FROM upload_status_lu WHERE upload_status_id = ?";
        List values = Collections.singletonList(new Long(uploadStatus.getId()));

        executeSingleCommand(query, values);
    }

    /**
     * <p>
     * Updates the given upload status in the persistence. The id of the uploadStatus can not be UNSET_ID, and all other
     * fields needed for persistence must also be assigned.
     * </p>
     *
     * @param uploadStatus
     *            The upload status to update
     * @throws IllegalArgumentException
     *             If uploadStatus is null
     * @throws IllegalArgumentException
     *             If isValidToPersist returns false
     * @throws UploadPersistenceException
     *             If there is an error making the change in the persistence
     */
    public void updateUploadStatus(UploadStatus uploadStatus) throws UploadPersistenceException {
        String query = "UPDATE upload_status_lu SET name = ?, description = ?, modify_user = ?, "
                       + "modify_date = ? WHERE upload_status_id = ?";

        List values = new ArrayList();

        values.add(uploadStatus.getName());
        values.add(uploadStatus.getDescription());
        values.add(uploadStatus.getModificationUser());
        values.add(uploadStatus.getModificationTimestamp());
        values.add(new Long(uploadStatus.getId()));

        executeSingleCommand(query, values);
    }

    /**
     * <p>
     * Loads the UploadStatus with the given id from persistence. Returns null if there is no UploadStatus with the
     * given id.
     * </p>
     *
     * @return The loaded UploadStatus or null
     * @param uploadStatusId
     *            The id of the item to retrieve
     * @throws IllegalArgumentException
     *             if uploadStatusId is <= 0
     * @throws UploadPersistenceException
     *             if there is an error reading the persistence data
     */
    public UploadStatus loadUploadStatus(long uploadStatusId) throws UploadPersistenceException {
        UploadStatus[] statuses = loadUploadStatuses(new long[] { uploadStatusId });

        if (statuses.length > 0) {
            return statuses[0];
        }

        return null;
    }

    /**
     * Creates the UploadStatus instance using the values from result set.
     *
     * @param rs
     *            the source result set.
     * @return new UploadStatus instance.
     * @throws SQLException
     *             if any SQL error occurs.
     */
    private static UploadStatus populateUploadStatus(ResultSet rs) throws SQLException {
        UploadStatus status = new UploadStatus();

        status.setId(rs.getLong("status_id"));
        status.setName(rs.getString("status_name"));
        status.setDescription(rs.getString("status_description"));
        status.setCreationUser(rs.getString("status_create_user"));
        status.setCreationTimestamp(rs.getTimestamp("status_create_date"));
        status.setModificationUser(rs.getString("status_modify_user"));
        status.setModificationTimestamp(rs.getTimestamp("status_modify_date"));

        return status;
    }

    /**
     * <p>
     * Gets the ids of all upload statuses in the persistence. The individual upload statuses can then be loaded with
     * the loadUploadStatus method.
     * </p>
     *
     * @return The ids of all upload statuses
     * @throws UploadPersistenceException
     *             If there is an error reading the persistence store
     */
    public long[] getAllUploadStatusIds() throws UploadPersistenceException {
        String query = "SELECT upload_status_id FROM upload_status_lu";

        return selectIds(query);
    }

    /**
     * <p>
     * Adds the given submission status to the persistence. The id of the submission status must already be assigned, as
     * must all the other fields needed for persistence.
     * </p>
     *
     *
     * @param submissionStatus
     *            The submission status to add
     * @throws IllegalArgumentException
     *             If submissionStatus is null
     * @throws IllegalArgumentException
     *             If isValidToPersist returns false
     * @throws UploadPersistenceException
     *             If there is an error making the change in the persistence
     */
    public void addSubmissionStatus(SubmissionStatus submissionStatus) throws UploadPersistenceException {
        String query =
            "INSERT INTO submission_status_lu (submission_status_id, name, "
            + "description, create_user, create_date, modify_user, modify_date) VALUES (?, ?, ?, ?, ?, ?, ?)";

        List values = new ArrayList();

        values.add(new Long(submissionStatus.getId()));
        values.add(submissionStatus.getName());
        values.add(submissionStatus.getDescription());
        values.add(submissionStatus.getCreationUser());
        values.add(submissionStatus.getCreationTimestamp());
        values.add(submissionStatus.getModificationUser());
        values.add(submissionStatus.getModificationTimestamp());

        executeSingleCommand(query, values);
    }

    /**
     * <p>
     * Removes the given submission status (by id) from the persistence. The id of the submission status can not be
     * UNSET_ID, but the other fields do not matter.
     * </p>
     *
     * @param submissionStatus
     *            The submission status to remove
     * @throws IllegalArgumentException
     *             If submissionStatus is null
     * @throws IllegalArgumentException
     *             If the id is UNSET_ID
     * @throws UploadPersistenceException
     *             If there is an error making the change in the persistence
     */
    public void removeSubmissionStatus(SubmissionStatus submissionStatus) throws UploadPersistenceException {
        String query = "DELETE FROM submission_status_lu WHERE submission_status_id = ?";
        List values = Collections.singletonList(new Long(submissionStatus.getId()));

        executeSingleCommand(query, values);
    }

    /**
     * <p>
     * Updates the given submission status in the persistence. The id of the submissionStats can not be UNSET_ID, and
     * all other fields needed for persistence must also be assigned.
     * </p>
     *
     * @param submissionStatus
     *            The submissionStatus to update
     * @throws IllegalArgumentException
     *             If submissionStatus is null
     * @throws IllegalArgumentException
     *             If isValidToPersist returns false
     * @throws UploadPersistenceException
     *             If there is an error making the change in the persistence
     */
    public void updateSubmissionStatus(SubmissionStatus submissionStatus) throws UploadPersistenceException {
        String query = "UPDATE submission_status_lu SET name = ?, description = ?, "
                       + "modify_user = ?, modify_date = ? WHERE submission_status_id = ?";

        List values = new ArrayList();

        values.add(submissionStatus.getName());
        values.add(submissionStatus.getDescription());
        values.add(submissionStatus.getModificationUser());
        values.add(submissionStatus.getModificationTimestamp());
        values.add(new Long(submissionStatus.getId()));

        executeSingleCommand(query, values);
    }

    /**
     * <p>
     * Loads the SubmissionStatus with the given id from persistence. Returns null if there is no SubmissionStatus with
     * the given id.
     * </p>
     *
     * @return The loaded SubmissionStatus or null
     * @param submissionStatusId
     *            The id of the item to retrieve
     * @throws IllegalArgumentException
     *             if submissionStatusId is <= 0
     * @throws UploadPersistenceException
     *             if there is an error reading the persistence data
     */
    public SubmissionStatus loadSubmissionStatus(long submissionStatusId) throws UploadPersistenceException {
        SubmissionStatus[] statuses = loadSubmissionStatuses(new long[] { submissionStatusId });

        if (statuses.length > 0) {
            return statuses[0];
        }

        return null;
    }

    /**
     * Creates the SubmissionStatus instance using the values from result set.
     *
     * @param rs
     *            the source result set.
     * @return new SubmissionStatus instance.
     * @throws SQLException
     *             if any SQL error occurs.
     */
    private SubmissionStatus populateSubmissionStatus(ResultSet rs) throws SQLException {
        SubmissionStatus status = new SubmissionStatus();

        status.setId(rs.getLong("status_id"));
        status.setName(rs.getString("status_name"));
        status.setDescription(rs.getString("status_description"));
        status.setCreationUser(rs.getString("status_create_user"));
        status.setCreationTimestamp(rs.getTimestamp("status_create_date"));
        status.setModificationUser(rs.getString("status_modify_user"));
        status.setModificationTimestamp(rs.getTimestamp("status_modify_date"));

        return status;
    }

    /**
     * <p>
     * Gets the ids of all submission statuses in the persistence. The individual submission statuses can then be loaded
     * with the loadSubmissionStatus method.
     * </p>
     *
     * @return The ids of all submission statuses
     * @throws UploadPersistenceException
     *             If there is an error reading the persistence store
     */
    public long[] getAllSubmissionStatusIds() throws UploadPersistenceException {
        String query = "SELECT submission_status_id FROM submission_status_lu;";

        return selectIds(query);
    }

    /**
     * <p>
     * Adds the given upload to the persistence. The id of the upload must already be assigned, as must all the other
     * fields needed for persistence.
     * </p>
     *
     * @param upload
     *            The upload to add
     * @throws IllegalArgumentException
     *             If upload is null
     * @throws IllegalArgumentException
     *             If isValidToPersist returns false
     * @throws UploadPersistenceException
     *             If there is an error making the change in the persistence
     */
    public void addUpload(Upload upload) throws UploadPersistenceException {
        String query = "INSERT INTO upload (upload_id, project_id, resource_id, upload_type_id, "
                       + "upload_status_id, parameter, create_user, create_date, modify_user, modify_date) "
                       + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        List values = new ArrayList();

        values.add(new Long(upload.getId()));
        values.add(new Long(upload.getProject()));
        values.add(new Long(upload.getOwner()));
        values.add(new Long(upload.getUploadType().getId()));
        values.add(new Long(upload.getUploadStatus().getId()));

        values.add(upload.getParameter());
        values.add(upload.getCreationUser());
        values.add(upload.getCreationTimestamp());
        values.add(upload.getModificationUser());
        values.add(upload.getModificationTimestamp());

        executeSingleCommand(query, values);
    }

    /**
     * <p>
     * Removes the given upload (by id) from the persistence. The id of the upload can not be UNSET_ID, but the other
     * fields do not matter.
     * </p>
     *
     * @param upload
     *            The upload to remove
     * @throws IllegalArgumentException
     *             If upload is null
     * @throws IllegalArgumentException
     *             If the id is UNSET_ID
     * @throws UploadPersistenceException
     *             If there is an error making the change in the persistence
     */
    public void removeUpload(Upload upload) throws UploadPersistenceException {
        String query = "DELETE FROM upload WHERE upload_id = ?";
        List values = Collections.singletonList(new Long(upload.getId()));

        executeSingleCommand(query, values);
    }

    /**
     * <p>
     * Updates the given upload in the persistence. The id of the upload can not be UNSET_ID, and all other fields
     * needed for persistence must also be assigned.
     * </p>
     *
     * @param upload
     *            The upload to update
     * @throws IllegalArgumentException
     *             If upload is null
     * @throws IllegalArgumentException
     *             If isValidToPersist returns false
     * @throws UploadPersistenceException
     *             If there is an error making the change in the persistence
     */
    public void updateUpload(Upload upload) throws UploadPersistenceException {
        String query = "UPDATE upload SET project_id = ?, resource_id = ?, upload_type_id = ?, "
                       + "upload_status_id = ?, parameter = ?, modify_user = ?, modify_date = ? WHERE upload_id = ?";

        List values = new ArrayList();

        values.add(new Long(upload.getProject()));
        values.add(new Long(upload.getOwner()));
        values.add(new Long(upload.getUploadType().getId()));
        values.add(new Long(upload.getUploadStatus().getId()));

        values.add(upload.getParameter());
        values.add(upload.getModificationUser());
        values.add(upload.getModificationTimestamp());
        values.add(new Long(upload.getId()));

        executeSingleCommand(query, values);
    }

    /**
     * <p>
     * Loads the Upload with the given id from persistence. Returns null if there is no Upload with the given id.
     * </p>
     *
     * @return The loaded Upload or null
     * @param uploadId
     *            The id of the item to retrieve
     * @throws IllegalArgumentException
     *             if uploadId is <= 0
     * @throws UploadPersistenceException
     *             if there is an error reading the persistence data
     */
    public Upload loadUpload(long uploadId) throws UploadPersistenceException {
        Connection conn = createConnection();

        try {
            return loadUpload(conn, uploadId);
        } finally {
            DBUtil.close(conn);
        }
    }

    /**
     * <p>
     * Loads the Upload with the given id from persistence. Returns null if there is no Upload with the given id.
     * </p>
     *
     * @return The loaded Upload or null
     * @param conn
     *            the connection to be used.
     * @param uploadId
     *            The id of the item to retrieve
     * @throws IllegalArgumentException
     *             if uploadId is <= 0
     * @throws UploadPersistenceException
     *             if there is an error reading the persistence data
     */
    private static Upload loadUpload(Connection conn, long uploadId) throws UploadPersistenceException {
        String query =
            "SELECT upload_id, project_id, resource_id, "
            + "parameter, upload.create_user upload_create_user, upload.create_date upload_create_date, "
            + "upload.modify_user upload_modify_user, " + "upload.modify_date upload_modify_date, "
            + "upload_type_lu.name type_name, upload_type_lu.upload_type_id type_id, "
            + "upload_status_lu.name status_name, "
            + "upload_status_lu.upload_status_id status_id, upload_type_lu.description type_description, "
            + "upload_status_lu.description status_description, "
            + "upload_type_lu.create_user type_create_user, upload_type_lu.create_date type_create_date, "
            + "upload_type_lu.modify_user type_modify_user, "
            + "upload_type_lu.modify_date type_modify_date, upload_status_lu.create_user status_create_user, "
            + "upload_status_lu.create_date status_create_date, "
            + "upload_status_lu.modify_user status_modify_user, upload_status_lu.modify_date status_modify_date "
            + "FROM upload INNER JOIN upload_type_lu ON upload_type_lu.upload_type_id = upload.upload_type_id "
            + "INNER JOIN upload_status_lu ON upload_status_lu.upload_status_id = upload.upload_status_id "
            + "WHERE upload_id = ?";

        ResultSet rs = null;
        PreparedStatement pstmt = null;

        try {
            pstmt = conn.prepareStatement(query);
            pstmt.setLong(1, uploadId);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                return populateUpload(rs);
            }

        } catch (SQLException ex) {
            throw new UploadPersistenceException("Error occurs while retrieving the upload.", ex);
        } finally {
            DBUtil.close(rs);
            DBUtil.close(pstmt);
        }

        return null;
    }

    /**
     * Creates the Upload instance using the values from result set.
     *
     * @param rs
     *            the source result set.
     * @return new Upload instance.
     * @throws SQLException
     *             if any SQL error occurs.
     */
    private static Upload populateUpload(ResultSet rs) throws SQLException {
        Upload upload = new Upload();

        upload.setId(rs.getLong("upload_id"));
        upload.setParameter(rs.getString("parameter"));
        upload.setProject(rs.getLong("project_id"));
        upload.setOwner(rs.getLong("resource_id"));
        upload.setCreationUser(rs.getString("upload_create_user"));
        upload.setModificationUser(rs.getString("upload_modify_user"));
        upload.setCreationTimestamp(rs.getTimestamp("upload_create_date"));
        upload.setModificationTimestamp(rs.getTimestamp("upload_modify_date"));

        upload.setUploadStatus(populateUploadStatus(rs));
        upload.setUploadType(populateUploadType(rs));

        return upload;
    }

    /**
     * <p>
     * Adds the given submission to the persistence. The id of the submission must already be assigned, as must all the
     * other fields needed for persistence.
     * </p>
     *
     * @param submission
     *            The submission to add
     * @throws IllegalArgumentException
     *             If submission is null
     * @throws IllegalArgumentException
     *             If isValidToPersist returns false
     * @throws UploadPersistenceException
     *             If there is an error making the change in the persistence
     */
    public void addSubmission(Submission submission) throws UploadPersistenceException {
        String query = "INSERT INTO submission (submission_id, upload_id, submission_status_id, submission_type_id, "
                       + "create_user, create_date, modify_user, modify_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        List values = new ArrayList();

        values.add(new Long(submission.getId()));
        values.add(new Long(submission.getUpload().getId()));
        values.add(new Long(submission.getSubmissionStatus().getId()));
        values.add(new Long(submission.getSubmissionType().getId()));
        values.add(submission.getCreationUser());
        values.add(submission.getCreationTimestamp());
        values.add(submission.getModificationUser());
        values.add(submission.getModificationTimestamp());

        executeSingleCommand(query, values);
    }

    /**
     * <p>
     * Removes the given submission (by id) from the persistence. The id of the submission can not be UNSET_ID, but the
     * other fields do not matter.
     * </p>
     *
     * @param submission
     *            The submission to remove
     * @throws IllegalArgumentException
     *             If submission is null
     * @throws IllegalArgumentException
     *             If the id is UNSET_ID
     * @throws UploadPersistenceException
     *             If there is an error making the change in the persistence
     */
    public void removeSubmission(Submission submission) throws UploadPersistenceException {
        String query = "DELETE FROM submission WHERE submission_id = ?";
        List values = Collections.singletonList(new Long(submission.getId()));

        executeSingleCommand(query, values);
    }

    /**
     * <p>
     * Updates the given submission in the persistence. The id of the submission can not be UNSET_ID, and all other
     * fields needed for persistence must also be assigned.
     * </p>
     *
     * @param submission
     *            The submission to add
     * @throws IllegalArgumentException
     *             If submission is null
     * @throws IllegalArgumentException
     *             If isValidToPersist returns false
     * @throws UploadPersistenceException
     *             If there is an error making the change in the persistence
     */
    public void updateSubmission(Submission submission) throws UploadPersistenceException {
        String query = "UPDATE submission SET upload_id = ?, submission_status_id = ?, submission_type_id = ?, "
                       + "modify_user = ?, modify_date = ? WHERE submission_id = ?";

        List values = new ArrayList();

        values.add(new Long(submission.getUpload().getId()));
        values.add(new Long(submission.getSubmissionStatus().getId()));
        values.add(new Long(submission.getSubmissionType().getId()));
        values.add(submission.getModificationUser());
        values.add(submission.getModificationTimestamp());
        values.add(new Long(submission.getId()));

        executeSingleCommand(query, values);
    }

    /**
     * <p>
     * Loads the Submission with the given id from persistence. Returns null if there is no Submission with the given
     * id.
     * </p>
     *
     * @return The loaded Submission or null
     * @param submissionId
     *            The id of the item to retrieve
     * @throws IllegalArgumentException
     *             if submissionId is <= 0
     * @throws UploadPersistenceException
     *             if there is an error reading the persistence data
     */
    public Submission loadSubmission(long submissionId) throws UploadPersistenceException {
        Submission[] submissions = loadSubmissions(new long[] { submissionId });

        if (submissions.length > 0) {
            return submissions[0];
        }

        return null;
    }

    /**
     * Creates the Submission instance using the values from result set.
     *
     * @param rs
     *            the source result set.
     * @param conn
     *            used the get the Upload instance.
     * @return new Submission instance.
     * @throws SQLException
     *             if any SQL error occurs.
     * @throws UploadPersistenceException
     *             if error occurs when loading Upload instance.
     */
    private Submission populateSubmission(ResultSet rs, Connection conn)
            throws SQLException, UploadPersistenceException {
        Submission submission = new Submission();

        submission.setId(rs.getLong("submission_id"));
        submission.setSubmissionStatus(populateSubmissionStatus(rs));

        submission.setCreationUser(rs.getString("submission_create_user"));
        submission.setModificationUser(rs.getString("submission_modify_user"));
        submission.setCreationTimestamp(rs.getTimestamp("submission_create_date"));
        submission.setModificationTimestamp(rs.getTimestamp("submission_modify_date"));

        submission.setUpload(loadUpload(conn, rs.getLong("upload_id")));

        return submission;
    }

    /**
     * <p>
     * Loads all Submissions with the given ids from persistence. May return a 0-length array.
     * </p>
     *
     * @param submissionIds
     *            The ids of submissions to load
     * @return The loaded submissions
     * @throws IllegalArgumentException
     *             if any id is <= 0
     * @throws UploadPersistenceException
     *             if there is an error reading the persistence data
     */
    public Submission[] loadSubmissions(long[] submissionIds) throws UploadPersistenceException {
        Connection conn = createConnection();
        String query =
            "SELECT submission_id, upload_id, "
            + "submission.create_user submission_create_user, submission.create_date submission_create_date, "
            + "submission.modify_user submission_modify_user, submission.modify_date submission_modify_date, "
            + "submission_status_lu.submission_status_id status_id, submission_status_lu.name status_name, "
            + "submission_status_lu.description status_description, "
            + "submission_status_lu.create_user status_create_user, "
            + "submission_status_lu.create_date status_create_date, "
            + "submission_status_lu.modify_user status_modify_user, "
            + "submission_status_lu.modify_date status_modify_date "
            + "FROM submission INNER JOIN submission_status_lu "
            + "ON submission_status_lu.submission_status_id = submission.submission_status_id WHERE "
            + "submission_id IN ";

        ResultSet rs = null;
        PreparedStatement pstmt = null;

        try {
            pstmt = DBUtil.createInQueryStatement(query, submissionIds, conn);
            rs = pstmt.executeQuery();

            int index = 0;
            List result = new ArrayList();

            while (rs.next()) {
                logger.log(Level.INFO, this.getClass().getName() + " loadSubmissions - index: " + index++);
                result.add(populateSubmission(rs, conn));
            }

            return (Submission[]) result.toArray(new Submission[result.size()]);
        } catch (SQLException ex) {
            throw new UploadPersistenceException("Error occurs while retrieving the submission status.", ex);
        } finally {
            DBUtil.close(rs);
            DBUtil.close(pstmt);
            DBUtil.close(conn);
        }
    }

    /**
     * <p>
     * Loads all Uploads with the given ids from persistence. May return a 0-length array.
     * </p>
     *
     * @param uploadIds
     *            The ids of uploads to load
     * @return The loaded uploads
     * @throws IllegalArgumentException
     *             if any id is <= 0
     * @throws UploadPersistenceException
     *             if there is an error reading the persistence data
     */
    public Upload[] loadUploads(long[] uploadIds) throws UploadPersistenceException {
        Connection conn = createConnection();
        String query =
            "SELECT upload_id, project_id, resource_id, "
            + "parameter, upload.create_user upload_create_user, upload.create_date upload_create_date, "
            + "upload.modify_user upload_modify_user, " + "upload.modify_date upload_modify_date, "
            + "upload_type_lu.name type_name, upload_type_lu.upload_type_id type_id, "
            + "upload_status_lu.name status_name, "
            + "upload_status_lu.upload_status_id status_id, upload_type_lu.description type_description, "
            + "upload_status_lu.description status_description, "
            + "upload_type_lu.create_user type_create_user, upload_type_lu.create_date type_create_date, "
            + "upload_type_lu.modify_user type_modify_user, "
            + "upload_type_lu.modify_date type_modify_date, upload_status_lu.create_user status_create_user, "
            + "upload_status_lu.create_date status_create_date, "
            + "upload_status_lu.modify_user status_modify_user, upload_status_lu.modify_date status_modify_date "
            + "FROM upload INNER JOIN upload_type_lu ON upload_type_lu.upload_type_id = upload.upload_type_id "
            + "INNER JOIN upload_status_lu ON upload_status_lu.upload_status_id = upload.upload_status_id "
            + "WHERE upload_id IN ";

        ResultSet rs = null;
        PreparedStatement pstmt = null;

        try {
            pstmt = DBUtil.createInQueryStatement(query, uploadIds, conn);
            rs = pstmt.executeQuery();
            List result = new ArrayList();

            while (rs.next()) {
                result.add(populateUpload(rs));
            }

            return (Upload[]) result.toArray(new Upload[result.size()]);
        } catch (SQLException ex) {
            throw new UploadPersistenceException("Error occurs while retrieving the uploads.", ex);
        } finally {
            DBUtil.close(rs);
            DBUtil.close(pstmt);
            DBUtil.close(conn);
        }
    }

    /**
     * <p>
     * Loads all UploadTypes with the given ids from persistence. May return a 0-length array.
     * </p>
     *
     * @param uploadTypeIds
     *            The ids of the objects to load
     * @return the loaded UploadTypes
     * @throws IllegalArgumentException
     *             if any id is <= 0
     * @throws UploadPersistenceException
     *             if there is an error reading the persistence data
     */
    public UploadType[] loadUploadTypes(long[] uploadTypeIds) throws UploadPersistenceException {
        Connection conn = createConnection();
        String query = "SELECT upload_type_id type_id, name type_name, description type_description, "
                       + "create_user type_create_user, create_date type_create_date, modify_user type_modify_user, "
                       + "modify_date type_modify_date FROM upload_type_lu WHERE upload_type_id IN ";

        ResultSet rs = null;
        PreparedStatement pstmt = null;

        try {
            pstmt = DBUtil.createInQueryStatement(query, uploadTypeIds, conn);
            rs = pstmt.executeQuery();
            List result = new ArrayList();

            while (rs.next()) {
                result.add(populateUploadType(rs));
            }

            return (UploadType[]) result.toArray(new UploadType[result.size()]);
        } catch (SQLException ex) {
            throw new UploadPersistenceException("Error occurs while retrieving the uploads.", ex);
        } finally {
            DBUtil.close(rs);
            DBUtil.close(pstmt);
            DBUtil.close(conn);
        }
    }

    /**
     * <p>
     * Loads all UploadStatuses with the given ids from persistence. May return a 0-length array.
     * </p>
     *
     * @param uploadStatusIds
     *            The ids of the objects to load
     * @return the loaded UploadStatuses
     * @throws IllegalArgumentException
     *             if any id is <= 0
     * @throws UploadPersistenceException
     *             if there is an error reading the persistence data
     */
    public UploadStatus[] loadUploadStatuses(long[] uploadStatusIds) throws UploadPersistenceException {
        Connection conn = createConnection();
        String query = "SELECT upload_status_id status_id, name status_name, description status_description, "
                       + "create_user status_create_user, create_date status_create_date, "
                       + "modify_user status_modify_user, modify_date status_modify_date FROM upload_status_lu "
                       + "WHERE upload_status_id IN ";

        ResultSet rs = null;
        PreparedStatement pstmt = null;

        try {
            pstmt = DBUtil.createInQueryStatement(query, uploadStatusIds, conn);
            rs = pstmt.executeQuery();
            List result = new ArrayList();

            while (rs.next()) {
                result.add(populateUploadStatus(rs));
            }

            return (UploadStatus[]) result.toArray(new UploadStatus[result.size()]);
        } catch (SQLException ex) {
            throw new UploadPersistenceException("Error occurs while retrieving the uploads.", ex);
        } finally {
            DBUtil.close(rs);
            DBUtil.close(pstmt);
            DBUtil.close(conn);
        }
    }

    /**
     * <p>
     * Loads all SubmissionStatuses with the given ids from persistence. May return a empty array.
     * </p>
     *
     * @param submissionStatusIds
     *            The ids of the objects to load
     * @return the loaded SubmissionStatuses
     * @throws IllegalArgumentException
     *             if any id is <= 0
     * @throws UploadPersistenceException
     *             if there is an error reading the persistence data
     */
    public SubmissionStatus[] loadSubmissionStatuses(long[] submissionStatusIds) throws UploadPersistenceException {
        Connection conn = createConnection();
        String query = "SELECT submission_status_id status_id, name status_name, description status_description, "
                       + "create_user status_create_user, create_date status_create_date, "
                       + "modify_user status_modify_user, modify_date status_modify_date FROM submission_status_lu "
                       + "WHERE submission_status_id IN ";

        ResultSet rs = null;
        PreparedStatement pstmt = null;

        try {
            logger.log(Level.INFO, "loadSubmissionStatuses - Creating Query: " + submissionStatusIds.length);

            pstmt = DBUtil.createInQueryStatement(query, submissionStatusIds, conn);

            logger.log(Level.INFO, "loadSubmissionStatuses - Processing Query");

            rs = pstmt.executeQuery();

            logger.log(Level.INFO, "loadSubmissionStatuses - Query Processed");

            List result = new ArrayList();

            while (rs.next()) {
                result.add(populateSubmissionStatus(rs));
            }

            return (SubmissionStatus[]) result.toArray(new SubmissionStatus[result.size()]);
        } catch (SQLException ex) {
            throw new UploadPersistenceException("Error occurs while retrieving the submission status.", ex);
        } finally {
            DBUtil.close(rs);
            DBUtil.close(pstmt);
            DBUtil.close(conn);
        }
    }

    public Submission[] loadSubmissions(CustomResultSet resultSet) throws UploadPersistenceException {
        try {
            Submission[] submissionArray = new Submission[resultSet.getRecordCount()];

            logger.log(Level.INFO, " Loading: " + submissionArray.length);

            int index = 0;

            while (resultSet.next()) {
                submissionArray[index] = new Submission();
                Submission submission = submissionArray[index++];

                submission.setId(resultSet.getInt("submission_id"));
            }

            return submissionArray;

        } catch (Exception ex) {
            throw new UploadPersistenceException("Error occurs while retrieving submission.", ex);
        }
    }

    public Upload[] loadUploads(CustomResultSet resultSet) throws UploadPersistenceException {
        try {
            Upload[] uploadArray = new Upload[resultSet.getRecordCount()];

            int index = 0;

            while (resultSet.next()) {
                uploadArray[index] = new Upload();
                Upload upload = uploadArray[index++];

                upload.setId(resultSet.getInt("upload_id"));
            }

            return uploadArray;

        } catch (Exception ex) {
            throw new UploadPersistenceException("Error occurs while retrieving Uploads.", ex);
        }
    }

    public void addSubmissionType(SubmissionType submissionType) throws UploadPersistenceException {
        String query =
            "INSERT INTO submission_type_lu (submission_type_id, name, "
            + "description, create_user, create_date, modify_user, modify_date) VALUES (?, ?, ?, ?, ?, ?, ?)";

        List values = new ArrayList();

        values.add(new Long(submissionType.getId()));
        values.add(submissionType.getName());
        values.add(submissionType.getDescription());
        values.add(submissionType.getCreationUser());
        values.add(submissionType.getCreationTimestamp());
        values.add(submissionType.getModificationUser());
        values.add(submissionType.getModificationTimestamp());

        executeSingleCommand(query, values);
    }

    public long[] getAllSubmissionTypeIds() throws UploadPersistenceException {
        String query = "SELECT submission_type_id FROM submission_type_lu;";

        return selectIds(query);
    }

    public SubmissionType loadSubmissionType(long submissionTypeId) throws UploadPersistenceException {
        SubmissionType[] types = loadSubmissionTypes(new long[] { submissionTypeId });

        if (types.length > 0) {
            return types[0];
        }

        return null;
    }

    public SubmissionType[] loadSubmissionTypes(long[] submissionTypeIds) throws UploadPersistenceException {
        Connection conn = createConnection();
        String query = "SELECT submission_type_id type_id, name type_name, description type_description, "
                       + "create_user type_create_user, create_date type_create_date, "
                       + "modify_user type_modify_user, modify_date type_modify_date FROM submission_type_lu "
                       + "WHERE submission_type_id IN ";

        ResultSet rs = null;
        PreparedStatement pstmt = null;

        try {
            logger.log(Level.INFO, "loadSubmissionTypes - Creating Query: " + submissionTypeIds.length);

            pstmt = DBUtil.createInQueryStatement(query, submissionTypeIds, conn);

            logger.log(Level.INFO, "loadSubmissionStatuses - Processing Query");

            rs = pstmt.executeQuery();

            logger.log(Level.INFO, "loadSubmissionStatuses - Query Processed");

            List result = new ArrayList();

            while (rs.next()) {
                result.add(populateSubmissionType(rs));
            }

            return (SubmissionType[]) result.toArray(new SubmissionType[result.size()]);
        } catch (SQLException ex) {
            throw new UploadPersistenceException("Error occurs while retrieving the submission type.", ex);
        } finally {
            DBUtil.close(rs);
            DBUtil.close(pstmt);
            DBUtil.close(conn);
        }
    }

    public void removeSubmissionType(SubmissionType submissionType) throws UploadPersistenceException {
        String query = "DELETE FROM submission_type_lu WHERE submission_type_id = ?";
        List values = Collections.singletonList(new Long(submissionType.getId()));

        executeSingleCommand(query, values);
    }

    public void updateSubmissionType(SubmissionType submissionType) throws UploadPersistenceException {
        String query = "UPDATE submission_type_lu SET name = ?, description = ?, "
                       + "modify_user = ?, modify_date = ? WHERE submission_type_id = ?";

        List values = new ArrayList();

        values.add(submissionType.getName());
        values.add(submissionType.getDescription());
        values.add(submissionType.getModificationUser());
        values.add(submissionType.getModificationTimestamp());
        values.add(new Long(submissionType.getId()));

        executeSingleCommand(query, values);
    }

    /**
     * Creates the SubmissionType instance using the values from result set.
     *
     * @param rs
     *            the source result set.
     * @return new SubmissionType instance.
     * @throws SQLException
     *             if any SQL error occurs.
     */
    private SubmissionType populateSubmissionType(ResultSet rs) throws SQLException {
        SubmissionType type = new SubmissionType();

        type.setId(rs.getLong("type_id"));
        type.setName(rs.getString("type_name"));
        type.setDescription(rs.getString("type_description"));
        type.setCreationUser(rs.getString("type_create_user"));
        type.setCreationTimestamp(rs.getTimestamp("type_create_date"));
        type.setModificationUser(rs.getString("type_modify_user"));
        type.setModificationTimestamp(rs.getTimestamp("type_modify_date"));

        return type;
    }
}
