/*
 * Copyright (C) 2006 -2010 TopCoder Inc., All Rights Reserved.
 */



package com.topcoder.management.deliverable.stresstests;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.management.deliverable.DeliverableTestHelper;
import com.topcoder.management.deliverable.Submission;
import com.topcoder.management.deliverable.SubmissionStatus;
import com.topcoder.management.deliverable.SubmissionType;
import com.topcoder.management.deliverable.Upload;
import com.topcoder.management.deliverable.UploadStatus;
import com.topcoder.management.deliverable.UploadType;
import com.topcoder.util.config.ConfigManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.Date;
import java.util.Iterator;

/**
 * Helper for database testing.
 *
 * @author Chenhong, morehappiness
 * @version 1.1
 */
public final class DBTestUtil {
    /**
     * File contains sql statement to clear database for upload search.
     */
    private static final String CLEAR_DB_SQL = "test_files/stresstests/ClearDB.sql";

    /**
     * File contains sql statement to initial database for upload search.
     */
    private static final String INIT_DB_SQL = "test_files/stresstests/InitDB.sql";

    /**
     * Private constructor.
     *
     */
    private DBTestUtil() {
        // empty.
    }

    /**
     * Set up the database. Some records should be first inserted for testing.
     *
     * @throws Exception
     *             to junit.
     */
    static void setUpTest() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();

        for (Iterator iter = cm.getAllNamespaces(); iter.hasNext(); ) {
            cm.removeNamespace((String) iter.next());
        }

        cm.add("stresstests/DBConnectionFactory.xml");

        DeliverableTestHelper.executeBatch(CLEAR_DB_SQL);
        DeliverableTestHelper.executeBatch(INIT_DB_SQL);
    }

    /**
     * Clear the tables in use for testing.
     *
     * @throws Exception
     *             to junit.
     */
    static void tearDownTest() throws Exception {
        DeliverableTestHelper.executeBatch(CLEAR_DB_SQL);
    }

    /**
     * Remove all the namespaces in the config manager instance.
     *
     * @throws Exception
     *             to junit.
     */
    static void removeAllNamespace() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();

        for (Iterator iter = cm.getAllNamespaces(); iter.hasNext(); ) {
            cm.removeNamespace((String) iter.next());
        }
    }

    /**
     * Insert one Deliverable into the database.
     *
     * @throws Exception
     *             to junit.
     */
    static void insertDeliverable() throws Exception {
        Connection connection = null;
        Statement statement = null;

        try {
            connection = getConnection();
            statement = connection.createStatement();

            statement.addBatch("INSERT INTO resource_role_lu (resource_role_id) VALUES (1)");
            statement.addBatch("INSERT INTO phase_type_lu (phase_type_id) VALUES (1)");

            statement.addBatch("INSERT INTO deliverable_lu"
                               + "(deliverable_id, phase_type_id, resource_role_id, per_submission, required, "
                               + "name, description, create_user, create_date, modify_user, modify_date) "
                               + "VALUES (1, 1, 1, 1, 1, 'stress', 'submission deliverable', "
                               + "'System', CURRENT, 'System', CURRENT)");

            statement.executeBatch();

            statement.clearBatch();
        } catch (SQLException e) {
            // ingore.
        } finally {
            if (statement != null) {
                statement.close();
            }

            if (connection != null) {
                connection.close();
            }
        }
    }

    /**
     * Create Connection.
     *
     * @return a Connection
     * @throws Exception
     *             to junit.
     */
    private static Connection getConnection() throws Exception {
        DBConnectionFactory factory = new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName());

        return factory.createConnection();
    }

    /**
     * Create a upload with no id setting.
     *
     * @param project_id
     *            the project id
     * @param resource_id
     *            the resource id
     * @return Upload instance
     */
    static Upload createUploadWithIdNoSet(long project_id, long resource_id) {
        Upload upload = new Upload();

        upload.setProject(project_id);

        UploadStatus uploadStatus = createUploadStatus();

        uploadStatus.setId(1);

        UploadType uploadType = createUploadType();

        uploadType.setId(1);

        upload.setUploadStatus(uploadStatus);
        upload.setUploadType(uploadType);
        upload.setOwner(1);
        upload.setParameter("parameter");

        upload.setCreationTimestamp(new Date());
        upload.setCreationUser("stress reviewer");

        upload.setModificationTimestamp(new Date());
        upload.setModificationUser("stress");

        return upload;
    }

    /**
     * Creates a Submission without id.
     *
     * @return a Submission
     */
    static Submission createSubmission() {
        Submission submission = new Submission();

        submission.setCreationTimestamp(new Date());
        submission.setCreationUser("stress reviewer");
        submission.setModificationTimestamp(new Date());
        submission.setModificationUser("stress");

        SubmissionStatus submissionStatus = createSubmissionStatus();

        submissionStatus.setId(1);

        Upload upload = createUploadWithIdNoSet(1, 1);

        upload.setId(1);

        submission.setSubmissionStatus(submissionStatus);
        submission.setUpload(upload);

        SubmissionType submissionType = createSubmissionType();

        submissionType.setId(1);
        submission.setSubmissionType(submissionType);

        return submission;
    }

    /**
     * Creates a valid SubmissionStatus without an id.
     *
     * @return a SubmissionStatus
     */
    static SubmissionStatus createSubmissionStatus() {
        SubmissionStatus status = new SubmissionStatus();

        status.setName("upload status");

        status.setDescription("This is upload.");

        status.setCreationTimestamp(new Date());
        status.setCreationUser("stress reviewer");

        status.setModificationTimestamp(new Date());
        status.setModificationUser("stress reviewer");

        return status;
    }

    /**
     * Creates an UploadStatus without id
     *
     * @return a UploadStatus
     */
    static UploadStatus createUploadStatus() {
        UploadStatus status = new UploadStatus();

        status.setName("upload status");

        status.setDescription("This is upload.");

        status.setCreationTimestamp(new Date());
        status.setCreationUser("stress reviewer");

        status.setModificationTimestamp(new Date());
        status.setModificationUser("stress reviewer");

        return status;
    }

    /**
     * Creates a UploadType without id.
     *
     * @return a UploadType
     */
    static UploadType createUploadType() {
        UploadType type = new UploadType();

        type.setName("uploadType");

        type.setDescription("This is upload type");

        type.setCreationTimestamp(new Date());
        type.setCreationUser("stress reviewer");
        type.setModificationTimestamp(new Date());

        type.setModificationUser("stress reviewer");

        return type;
    }

    /**
     * Creates a valid SubmissionType without an id.
     *
     * @return a SubmissionType
     */
    static SubmissionType createSubmissionType() {
        SubmissionType type = new SubmissionType();

        type.setName("submission type");

        type.setDescription("This is type.");

        type.setCreationTimestamp(new Date());
        type.setCreationUser("stress reviewer");

        type.setModificationTimestamp(new Date());
        type.setModificationUser("stress reviewer");

        return type;
    }
}
