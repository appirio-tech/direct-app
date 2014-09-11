/*
 * Copyright (C) 2006-2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable.persistence.sql;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.management.deliverable.MimeType;
import com.topcoder.management.deliverable.Submission;
import com.topcoder.management.deliverable.SubmissionImage;
import com.topcoder.management.deliverable.SubmissionStatus;
import com.topcoder.management.deliverable.SubmissionType;
import com.topcoder.management.deliverable.Upload;
import com.topcoder.management.deliverable.UploadStatus;
import com.topcoder.management.deliverable.UploadType;
import com.topcoder.management.deliverable.persistence.TestHelper;
import com.topcoder.management.deliverable.persistence.UploadPersistence;
import com.topcoder.management.deliverable.persistence.UploadPersistenceException;
import com.topcoder.management.deliverable.persistence.sql.Helper.DataType;
import com.topcoder.management.project.FileType;
import com.topcoder.management.project.Prize;
import com.topcoder.management.project.PrizeType;
import com.topcoder.util.sql.databaseabstraction.CustomResultSet;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.Date;

/**
 * <p>
 * Unit tests for <code>SqlUploadPersistence</code>.
 * </p>
 * <p>
 * Changes in version 1.2:
 * <ul>
 * <li>new test cases are added for new methods.</li>
 * <li>old test cases are updated for the changes.</li>
 * </ul>
 * </p>
 *
 * @author urtks, TCSDEVELOPER
 * @version 1.2
 */
public class SqlUploadPersistenceTest extends TestCase {
    /**
     * <p>
     * Query used for loading upload instance.
     * </p>
     */
    private static final String UPLOAD_QUERY = "SELECT "
            + "upload.upload_id, upload.create_user as upload_create_user, upload.create_date as upload_create_date, "
            + "upload.modify_user as upload_modify_user, upload.modify_date as upload_modify_date, "
            + "upload.project_id, upload.resource_id, upload.parameter as upload_parameter, "
            + "upload_type_lu.upload_type_id, upload_type_lu.create_user as upload_type_create_user, "
            + "upload_type_lu.create_date as upload_type_create_date, "
            + "upload_type_lu.modify_user as upload_type_modify_user, "
            + "upload_type_lu.modify_date as upload_type_modify_date, "
            + "upload_type_lu.name as upload_type_name, upload_type_lu.description as upload_type_description, "
            + "upload_status_lu.upload_status_id, upload_status_lu.create_user as upload_status_create_user, "
            + "upload_status_lu.create_date as upload_status_create_date, "
            + "upload_status_lu.modify_user as upload_status_modify_user, "
            + "upload_status_lu.modify_date as upload_status_modify_date, "
            + "upload_status_lu.name as upload_status_name, upload_status_lu.description as upload_status_description "
            + "FROM upload INNER JOIN upload_type_lu " + "ON upload.upload_type_id=upload_type_lu.upload_type_id "
            + "INNER JOIN upload_status_lu " + "ON upload.upload_status_id=upload_status_lu.upload_status_id "
            + "WHERE upload_id = ?";

    /**
     * <p>
     * Query used for loading submission instance.
     * </p>
     */
    private static final String SUBMISSION_QUERY = "SELECT "
            + "submission.submission_id, submission.create_user as submission_create_user, "
            + "submission.create_date as submission_create_date, "
            + "submission.modify_user as submission_modify_user, submission.modify_date as submission_modify_date, "
            + "submission_status_lu.submission_status_id  as submission_status_id, "
            + "submission_status_lu.create_user as submission_status_create_user, "
            + "submission_status_lu.create_date as submission_status_create_date, "
            + "submission_status_lu.modify_user as submission_status_modify_user, "
            + "submission_status_lu.modify_date as submission_status_modify_date, "
            + "submission_status_lu.name as submission_status_name, "
            + "submission_status_lu.description as submission_status_description, "
            + "submission_type_lu.submission_type_id as submission_type_id, "
            + "submission_type_lu.create_user as submission_type_create_user, "
            + "submission_type_lu.create_date as submission_type_create_date, "
            + "submission_type_lu.modify_user as submission_type_modify_user, "
            + "submission_type_lu.modify_date as submission_type_modify_date, "
            + "submission_type_lu.name as submission_type_name, "
            + "submission_type_lu.description as submission_type_description, submission.screening_score, "
            + "submission.initial_score, submission.final_score, submission.placement, "
            + "submission.feedback_thumb, submission.user_rank, submission.mark_for_purchase, "
            + "prize.prize_id, prize.place, prize.prize_amount, prize.number_of_submissions, prize.prize_type_id, "
            + "prize.create_user as prize_create_user, prize.create_date as prize_create_date, "
            + "prize.modify_user as prize_modify_user, prize.modify_date as prize_modify_date, "
            + "prize_type_lu.prize_type_desc FROM submission INNER JOIN submission_status_lu "
            + "ON submission.submission_status_id=submission_status_lu.submission_status_id "
            + "INNER JOIN submission_type_lu ON submission.submission_type_id = submission_type_lu.submission_type_id "
            + "LEFT JOIN prize ON submission.prize_id = prize.prize_id "
            + "LEFT JOIN prize_type_lu ON prize.prize_type_id = prize_type_lu.prize_type_id "
            + "WHERE submission.submission_id = ?";

    /**
     * <p>
     * Represents an instance of connection factory used in test methods.
     * </p>
     */
    private DBConnectionFactory connectionFactory = null;

    /**
     * Aggregates all tests in this class.
     *
     * @return test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(SqlUploadPersistenceTest.class);
    }

    /**
     * Sets up the test environment. The configuration will be loaded.
     *
     * @throws Exception
     *             if any error occurs
     */
    protected void setUp() throws Exception {
        tearDown();

        TestHelper.loadConfig();

        TestHelper.executeBatch("test_files/insertUpload.sql");

        // create a connection factory
        connectionFactory = new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName());
    }

    /**
     * Clean up the test environment. The configuration will be unloaded.
     *
     * @throws Exception
     *             if any error occurs
     */
    protected void tearDown() throws Exception {
        TestHelper.clearConfig();
        TestHelper.loadConfig();

        TestHelper.executeBatch("test_files/delete.sql");

        TestHelper.clearConfig();
    }

    /**
     * <p>
     * Accuracy test of the constructor <code>SqlUploadPersistence(DBConnectionFactory connectionFactory)</code>.
     * </p>
     * <p>
     * An instance of SqlUploadPersistence can be created.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testAccuracyConstructorA1() throws Exception {
        UploadPersistence persistence = new SqlUploadPersistence(connectionFactory);

        assertNotNull("Unable to create SqlUploadPersistence.", persistence);
    }

    /**
     * <p>
     * Failure test of the constructor <code>SqlUploadPersistence(DBConnectionFactory connectionFactory)</code>.
     * </p>
     * <p>
     * connectionFactory is null. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testFailureConstructorA1() throws Exception {
        try {
            new SqlUploadPersistence(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            assertEquals("connectionFactory should not be null.", e.getMessage());
        }
    }

    /**
     * <p>
     * Accuracy test of the constructor
     * <code>SqlUploadPersistence(DBConnectionFactory connectionFactory, String connectionName)</code>.
     * </p>
     * <p>
     * An instance of SqlUploadPersistence can be created.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testAccuracyConstructorB1() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        assertNotNull("Unable to create SqlUploadPersistence.", persistence);
    }

    /**
     * <p>
     * Accuracy test of the constructor
     * <code>SqlUploadPersistence(DBConnectionFactory connectionFactory, String connectionName)</code>.
     * </p>
     * <p>
     * An instance of SqlUploadPersistence can be created. connectionName is null.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testAccuracyConstructorB2() throws Exception {
        UploadPersistence persistence = new SqlUploadPersistence(connectionFactory, null);

        assertNotNull("Unable to create SqlUploadPersistence.", persistence);
    }

    /**
     * <p>
     * Failure test of the constructor
     * <code>SqlUploadPersistence(DBConnectionFactory connectionFactory, String connectionName)</code>.
     * </p>
     * <p>
     * connectionFactory is null. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testFailureConstructorB1() throws Exception {
        try {
            new SqlUploadPersistence(null, "informix_connection");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            assertEquals("connectionFactory should not be null.", e.getMessage());
        }
    }

    /**
     * <p>
     * Accuracy test of the method <code>addUploadType(UploadType uploadType)</code>.
     * </p>
     * <p>
     * Check if the upload type is added in the database correctly.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testAccuracyAddUploadType1() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        // create the sample UploadType object
        UploadType uploadType = getSampleUploadType(5);

        persistence.addUploadType(uploadType);

        // query the table
        Object[][] rows = Helper.doQuery(connectionFactory, null, "SELECT upload_type_id, create_user, create_date, "
                + "modify_user, modify_date, name, description FROM upload_type_lu", new DataType[] {},
                new Object[] {}, new DataType[] {Helper.LONG_TYPE, Helper.STRING_TYPE, Helper.DATE_TYPE,
                    Helper.STRING_TYPE, Helper.DATE_TYPE, Helper.STRING_TYPE, Helper.STRING_TYPE}, null);

        assertEquals("5 results", 5, rows.length);
        Object[] row = rows[4];

        assertEquals("check id", new Long(uploadType.getId()), row[0]);
        assertEquals("check create_user", uploadType.getCreationUser(), row[1]);
        assertEquals("check create_date", uploadType.getCreationTimestamp(), row[2]);
        assertEquals("check modify_user", uploadType.getModificationUser(), row[3]);
        assertEquals("check modify_date", uploadType.getModificationTimestamp(), row[4]);
        assertEquals("check name", uploadType.getName(), row[5]);
        assertEquals("check description", uploadType.getDescription(), row[6]);
    }

    /**
     * <p>
     * Failure test of the method <code>addUploadType(UploadType uploadType)</code>.
     * </p>
     * <p>
     * uploadType is null. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testFailureAddUploadType1() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        try {
            persistence.addUploadType(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            assertEquals("uploadType should not be null.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method <code>addUploadType(UploadType uploadType)</code>.
     * </p>
     * <p>
     * uploadType is not valid to persist. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testFailureAddUploadType2() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        // create the sample UploadType object, do not set name
        UploadType uploadType = getSampleUploadType(5);
        uploadType.setName(null);

        try {
            persistence.addUploadType(uploadType);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            assertEquals("The entity [uploadType] is not valid to persist.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test for the <code>addUploadType(UploadType)</code> method when connection to database is invalid.
     * </p>
     * <p>
     * UploadPersistenceException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testFailureAddUploadType3() throws Exception {
        UploadPersistence persistence = new SqlUploadPersistence(connectionFactory, "invalid_connection");

        // create the sample UploadType object
        UploadType uploadType = getSampleUploadType(5);

        try {
            persistence.addUploadType(uploadType);
            fail("UploadPersistenceException should be thrown.");
        } catch (UploadPersistenceException e) {
            // test passed
        }
    }

    /**
     * <p>
     * Accuracy test of the method <code>addUploadStatus(UploadStatus uploadStatus)</code>.
     * </p>
     * <p>
     * Check if the upload status is added in the database correctly.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testAccuracyAddUploadStatus1() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        // create the sample UploadStatus object
        UploadStatus uploadStatus = getSampleUploadStatus(3);

        persistence.addUploadStatus(uploadStatus);

        // query the table
        Object[][] rows = Helper.doQuery(connectionFactory, null, "SELECT upload_status_id, create_user, create_date, "
                + "modify_user, modify_date, name, description FROM upload_status_lu", new DataType[] {},
                new Object[] {}, new DataType[] {Helper.LONG_TYPE, Helper.STRING_TYPE, Helper.DATE_TYPE,
                    Helper.STRING_TYPE, Helper.DATE_TYPE, Helper.STRING_TYPE, Helper.STRING_TYPE}, null);

        assertEquals("3 results", 3, rows.length);
        Object[] row = rows[2];

        assertEquals("check id", new Long(uploadStatus.getId()), row[0]);
        assertEquals("check create_user", uploadStatus.getCreationUser(), row[1]);
        assertEquals("check create_date", uploadStatus.getCreationTimestamp(), row[2]);
        assertEquals("check modify_user", uploadStatus.getModificationUser(), row[3]);
        assertEquals("check modify_date", uploadStatus.getModificationTimestamp(), row[4]);
        assertEquals("check name", uploadStatus.getName(), row[5]);
        assertEquals("check description", uploadStatus.getDescription(), row[6]);
    }

    /**
     * <p>
     * Failure test of the method <code>addUploadStatus(UploadStatus uploadStatus)</code>.
     * </p>
     * <p>
     * uploadStatus is null. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testFailureAddUploadStatus1() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        try {
            persistence.addUploadStatus(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            assertEquals("uploadStatus should not be null.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method <code>addUploadStatus(UploadStatus uploadStatus)</code>.
     * </p>
     * <p>
     * uploadStatus is not valid to persist. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testFailureAddUploadStatus2() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        // create the sample UploadStatus object, do not set name
        UploadStatus uploadStatus = getSampleUploadStatus(3);
        uploadStatus.setName(null);

        try {
            persistence.addUploadStatus(uploadStatus);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            assertEquals("The entity [uploadStatus] is not valid to persist.", e.getMessage());
        }
    }

    /**
     * <p>
     * Accuracy test of the method <code>addSubmissionStatus(SubmissionStatus submissionStatus)</code>.
     * </p>
     * <p>
     * Check if the submission status is added in the database correctly.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testAccuracyAddSubmissionStatus1() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        // create the sample SubmissionStatus object
        SubmissionStatus submissionStatus = getSampleSubmissionStatus(6);

        persistence.addSubmissionStatus(submissionStatus);

        // query the table
        Object[][] rows = Helper.doQuery(connectionFactory, null,
                "SELECT submission_status_id, create_user, create_date, "
                        + "modify_user, modify_date, name, description FROM submission_status_lu", new DataType[] {},
                new Object[] {}, new DataType[] {Helper.LONG_TYPE, Helper.STRING_TYPE, Helper.DATE_TYPE,
                    Helper.STRING_TYPE, Helper.DATE_TYPE, Helper.STRING_TYPE, Helper.STRING_TYPE}, null);

        assertEquals("6 results", 6, rows.length);
        Object[] row = rows[5];

        assertEquals("check id", new Long(submissionStatus.getId()), row[0]);
        assertEquals("check create_user", submissionStatus.getCreationUser(), row[1]);
        assertEquals("check create_date", submissionStatus.getCreationTimestamp(), row[2]);
        assertEquals("check modify_user", submissionStatus.getModificationUser(), row[3]);
        assertEquals("check modify_date", submissionStatus.getModificationTimestamp(), row[4]);
        assertEquals("check name", submissionStatus.getName(), row[5]);
        assertEquals("check description", submissionStatus.getDescription(), row[6]);
    }

    /**
     * <p>
     * Failure test of the method <code>addSubmissionStatus(SubmissionStatus submissionStatus)</code>.
     * </p>
     * <p>
     * submissionStatus is null. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testFailureAddSubmissionStatus1() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        try {
            persistence.addSubmissionStatus(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            assertEquals("submissionStatus should not be null.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method <code>addSubmissionStatus(SubmissionStatus submissionStatus)</code>.
     * </p>
     * <p>
     * submissionStatus is not valid to persist. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testFailureAddSubmissionStatus2() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        // create the sample SubmissionStatus object, do not set name
        SubmissionStatus submissionStatus = getSampleSubmissionStatus(6);
        submissionStatus.setName(null);

        try {
            persistence.addSubmissionStatus(submissionStatus);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            assertEquals("The entity [submissionStatus] is not valid to persist.", e.getMessage());
        }
    }

    /**
     * <p>
     * Accuracy test of the method <code>addUpload(Upload upload)</code>.
     * </p>
     * <p>
     * Check if the upload is added in the database correctly.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testAccuracyAddUpload1() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        // create the sample Upload object
        Upload upload = getSampleUpload(4);

        persistence.addUpload(upload);

        DataType[] columnTypes = new DataType[] {Helper.LONG_TYPE, Helper.STRING_TYPE, Helper.DATE_TYPE,
            Helper.STRING_TYPE, Helper.DATE_TYPE, Helper.LONG_TYPE, Helper.LONG_TYPE, Helper.STRING_TYPE,
            Helper.STRING_TYPE, Helper.LONG_TYPE, Helper.LONG_TYPE};
        // query the table
        Object[][] rows = Helper.doQuery(connectionFactory, null, "SELECT upload_id, create_user, create_date, "
                + "modify_user, modify_date, project_id, resource_id, parameter, upload_desc, "
                + " upload_type_id, upload_status_id FROM upload", new DataType[] {}, new Object[] {}, columnTypes,
                null);

        assertEquals("3 results", 3, rows.length);
        Object[] row = rows[2];

        assertEquals("check id", new Long(upload.getId()), row[0]);
        assertEquals("check create_user", upload.getCreationUser(), row[1]);
        assertEquals("check create_date", upload.getCreationTimestamp(), row[2]);
        assertEquals("check modify_user", upload.getModificationUser(), row[3]);
        assertEquals("check modify_date", upload.getModificationTimestamp(), row[4]);
        assertEquals("check project_id", new Long(upload.getProject()), row[5]);
        assertEquals("check resource_id", new Long(upload.getOwner()), row[6]);
        assertEquals("check parameter", upload.getParameter(), row[7]);
        assertEquals("check upload_desc", upload.getDescription(), row[8]);
        assertEquals("check upload_type_id", new Long(upload.getUploadType().getId()), row[9]);
        assertEquals("check upload_status_id", new Long(upload.getUploadStatus().getId()), row[10]);
    }

    /**
     * <p>
     * Failure test of the method <code>addUpload(Upload upload)</code>.
     * </p>
     * <p>
     * upload is null. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testFailureAddUpload1() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        try {
            persistence.addUpload(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            assertEquals("upload should not be null.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method <code>addUpload(Upload upload)</code>.
     * </p>
     * <p>
     * upload is not valid to persist. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testFailureAddUpload2() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        // create the sample Upload object, do not set upload type
        Upload upload = getSampleUpload(2);
        upload.setUploadType(null);

        try {
            persistence.addUpload(upload);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            assertEquals("The entity [upload] is not valid to persist.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test for the <code>addUploadType(UploadType uploadType)</code> method when connection to database is
     * invalid.
     * </p>
     * <p>
     * UploadPersistenceException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testFailureAddUpload3() throws Exception {
        UploadPersistence persistence = new SqlUploadPersistence(connectionFactory, "invalid_connection");

        Upload upload = getSampleUpload(4);

        try {
            persistence.addUpload(upload);
            fail("UploadPersistenceException should be thrown.");
        } catch (UploadPersistenceException e) {
            // test passed
        }
    }

    /**
     * <p>
     * Accuracy test of the method <code>addSubmission(Submission submission)</code>.
     * </p>
     * <p>
     * Check if the submission is added in the database correctly.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testAccuracyAddSubmission1() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        // create the sample Submission object
        Submission submission = getSampleSubmission(3);

        // we need to persist the uploads separately first.
        for (Upload upload : submission.getUploads()) {
            persistence.addUpload(upload);
        }

        persistence.addSubmission(submission);

        // query the table
        Object[][] rows = Helper.doQuery(connectionFactory, null,
                "SELECT submission_id, create_user, create_date, modify_user, modify_date, "
                        + "submission_status_id, submission_type_id, screening_score, initial_score, final_score,"
                        + " placement, feedback_thumb, user_rank, mark_for_purchase, prize_id FROM submission",
                new DataType[] {}, new Object[] {}, new DataType[] {Helper.LONG_TYPE, Helper.STRING_TYPE,
                    Helper.DATE_TYPE, Helper.STRING_TYPE, Helper.DATE_TYPE, Helper.LONG_TYPE, Helper.LONG_TYPE,
                    Helper.DOUBLE_TYPE, Helper.DOUBLE_TYPE, Helper.DOUBLE_TYPE, Helper.LONG_TYPE, Helper.BOOLEAN_TYPE,
                    Helper.INTEGER_TYPE, Helper.BOOLEAN_TYPE, Helper.LONG_TYPE}, null);

        assertEquals("3 results", 3, rows.length);
        Object[] row = rows[0];
        assertEquals("check id", new Long(1), row[0]);
        assertEquals("check create_user", "System", row[1]);
        assertEquals("check modify_user", "System", row[3]);
        assertEquals("check submission_status_id", new Long(3), row[5]);
        assertEquals("check submission_type_id", new Long(1), row[6]);

        row = rows[1];
        assertEquals("check id", new Long(2), row[0]);
        assertEquals("check create_user", "System", row[1]);
        assertEquals("check modify_user", "System", row[3]);
        assertEquals("check submission_status_id", new Long(1), row[5]);
        assertEquals("check submission_type_id", new Long(2), row[6]);

        row = rows[2];
        assertEquals("check id", new Long(submission.getId()), row[0]);
        assertEquals("check create_user", submission.getCreationUser(), row[1]);
        assertEquals("check create_date", submission.getCreationTimestamp(), row[2]);
        assertEquals("check modify_user", submission.getModificationUser(), row[3]);
        assertEquals("check modify_date", submission.getModificationTimestamp(), row[4]);
        assertEquals("check submission_status_id", submission.getSubmissionStatus().getId(), row[5]);
        assertEquals("check submission_type_id", submission.getSubmissionType().getId(), row[6]);
        assertEquals("check screening_score", submission.getScreeningScore(), row[7]);
        assertEquals("check initial_score", submission.getInitialScore(), row[8]);
        assertEquals("check final_score", submission.getFinalScore(), row[9]);
        assertEquals("check placement", submission.getPlacement(), row[10]);
        assertEquals("check feedback_thumb", submission.isThumb(), row[11]);
        assertEquals("check user_rank", submission.getUserRank(), row[12]);
        assertEquals("check mark_for_purchase", submission.isExtra(), row[13]);
        assertEquals("check prize_id", null, row[14]);
    }

    /**
     * <p>
     * Accuracy test of the method <code>addSubmission(Submission submission)</code>.
     * </p>
     * <p>
     * Check if the submission is added in the database correctly.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testAccuracyAddSubmission2() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        // create the sample Submission object
        Submission submission = getSampleSubmission(3);

        // we need to persist the uploads separately first.
        for (Upload upload : submission.getUploads()) {
            persistence.addUpload(upload);
        }

        // set prize
        Prize prize = new Prize();
        prize.setId(1);
        submission.setPrize(prize);

        persistence.addSubmission(submission);

        DataType[] columnTypes = new DataType[] {Helper.LONG_TYPE, Helper.STRING_TYPE, Helper.DATE_TYPE,
            Helper.STRING_TYPE, Helper.DATE_TYPE, Helper.LONG_TYPE, Helper.LONG_TYPE, Helper.DOUBLE_TYPE,
            Helper.DOUBLE_TYPE, Helper.DOUBLE_TYPE, Helper.LONG_TYPE, Helper.BOOLEAN_TYPE, Helper.INTEGER_TYPE,
            Helper.BOOLEAN_TYPE, Helper.LONG_TYPE};
        // query the table
        Object[][] rows = Helper.doQuery(connectionFactory, null,
                "SELECT submission_id, create_user, create_date, modify_user, modify_date, "
                        + "submission_status_id, submission_type_id, screening_score, initial_score, final_score,"
                        + " placement, feedback_thumb, user_rank, mark_for_purchase, prize_id FROM submission"
                        + " WHERE submission_id = ?", new DataType[] {Helper.LONG_TYPE}, new Object[] {submission
                        .getId()}, columnTypes, null);

        assertEquals("1 results", 1, rows.length);
        Object[] row = rows[0];
        assertEquals("check id", submission.getId(), row[0]);
        assertEquals("check create_user", submission.getCreationUser(), row[1]);
        assertEquals("check create_date", submission.getCreationTimestamp(), row[2]);
        assertEquals("check modify_user", submission.getModificationUser(), row[3]);
        assertEquals("check modify_date", submission.getModificationTimestamp(), row[4]);
        assertEquals("check submission_status_id", submission.getSubmissionStatus().getId(), row[5]);
        assertEquals("check submission_type_id", submission.getSubmissionType().getId(), row[6]);
        assertEquals("check screening_score", submission.getScreeningScore(), row[7]);
        assertEquals("check initial_score", submission.getInitialScore(), row[8]);
        assertEquals("check final_score", submission.getFinalScore(), row[9]);
        assertEquals("check placement", submission.getPlacement(), row[10]);
        assertEquals("check feedback_thumb", submission.isThumb(), row[11]);
        assertEquals("check user_rank", submission.getUserRank(), row[12]);
        assertEquals("check mark_for_purchase", submission.isExtra(), row[13]);
        assertEquals("check prize_id", submission.getPrize().getId(), row[14]);
    }

    /**
     * <p>
     * Failure test of the method <code>addSubmission(Submission submission)</code>.
     * </p>
     * <p>
     * submission is null. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testFailureAddSubmission1() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        try {
            persistence.addSubmission(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            assertEquals("submission should not be null.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method <code>addSubmission(Submission submission)</code>.
     * </p>
     * <p>
     * submission is not valid to persist. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testFailureAddSubmission2() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        // create the sample Submission object, do not set create user
        Submission submission = getSampleSubmission(2);
        submission.setCreationUser(null);

        try {
            persistence.addSubmission(submission);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            assertEquals("The entity [submission] is not valid to persist.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test for the <code>addSubmission(Submission submission)</code> method when connection to database is
     * invalid.
     * </p>
     * <p>
     * UploadPersistenceException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testFailureAddSubmission3() throws Exception {
        UploadPersistence persistence = new SqlUploadPersistence(connectionFactory, "invalid_connection");

        // create the sample Submission object
        Submission submission = getSampleSubmission(3);

        try {
            persistence.addSubmission(submission);
            fail("UploadPersistenceException should be thrown.");
        } catch (UploadPersistenceException e) {
            // test passed
        }
    }

    /**
     * <p>
     * Accuracy test of the method <code>updateUploadType(UploadType uploadType)</code>.
     * </p>
     * <p>
     * Check if the upload type is added in the database correctly.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testAccuracyUpdateUploadType1() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        // create the sample UploadType object
        UploadType uploadType = getSampleUploadType(1);

        persistence.updateUploadType(uploadType);

        // query the table
        Object[][] rows = Helper.doQuery(connectionFactory, null, "SELECT upload_type_id, create_user, create_date, "
                + "modify_user, modify_date, name, description FROM upload_type_lu", new DataType[] {},
                new Object[] {}, new DataType[] {Helper.LONG_TYPE, Helper.STRING_TYPE, Helper.DATE_TYPE,
                    Helper.STRING_TYPE, Helper.DATE_TYPE, Helper.STRING_TYPE, Helper.STRING_TYPE}, null);

        assertEquals("4 results", 4, rows.length);
        Object[] row = rows[0];

        assertEquals("check id", new Long(uploadType.getId()), row[0]);
        assertFalse("check create_user", uploadType.getCreationUser().equals(row[1]));
        assertFalse("check create_date", uploadType.getCreationTimestamp().equals(row[2]));
        assertEquals("check modify_user", uploadType.getModificationUser(), row[3]);
        assertEquals("check modify_date", uploadType.getModificationTimestamp(), row[4]);
        assertEquals("check name", uploadType.getName(), row[5]);
        assertEquals("check description", uploadType.getDescription(), row[6]);
    }

    /**
     * <p>
     * Failure test of the method <code>updateUploadType(UploadType uploadType)</code>.
     * </p>
     * <p>
     * uploadType is null. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testFailureUpdateUploadType1() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        try {
            persistence.updateUploadType(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            assertEquals("uploadType should not be null.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method <code>updateUploadType(UploadType uploadType)</code>.
     * </p>
     * <p>
     * uploadType is not valid to persist. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testFailureUpdateUploadType2() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        // create the sample UploadType object, do not set name
        UploadType uploadType = getSampleUploadType(1);
        uploadType.setName(null);

        try {
            persistence.updateUploadType(uploadType);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            assertEquals("The entity [uploadType] is not valid to persist.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test for the <code>updateUploadType(UploadType uploadType)</code> method when connection to database is
     * invalid.
     * </p>
     * <p>
     * UploadPersistenceException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testFailureUpdateUploadType3() throws Exception {
        UploadPersistence persistence = new SqlUploadPersistence(connectionFactory, "invalid_connection");

        // create the sample UploadType object
        UploadType uploadType = getSampleUploadType(5);

        try {
            persistence.updateUploadType(uploadType);
            fail("UploadPersistenceException should be thrown.");
        } catch (UploadPersistenceException e) {
            // test passed
        }
    }

    /**
     * <p>
     * Accuracy test of the method <code>updateUploadStatus(UploadStatus uploadStatus)</code>.
     * </p>
     * <p>
     * Check if the upload status is added in the database correctly.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testAccuracyUpdateUploadStatus1() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        // create the sample UploadStatus object
        UploadStatus uploadStatus = getSampleUploadStatus(1);

        persistence.updateUploadStatus(uploadStatus);

        // query the table
        Object[][] rows = Helper.doQuery(connectionFactory, null, "SELECT upload_status_id, create_user, create_date, "
                + "modify_user, modify_date, name, description FROM upload_status_lu", new DataType[] {},
                new Object[] {}, new DataType[] {Helper.LONG_TYPE, Helper.STRING_TYPE, Helper.DATE_TYPE,
                    Helper.STRING_TYPE, Helper.DATE_TYPE, Helper.STRING_TYPE, Helper.STRING_TYPE}, null);

        assertEquals("2 results", 2, rows.length);
        Object[] row = rows[0];

        assertEquals("check id", new Long(uploadStatus.getId()), row[0]);
        assertFalse("check create_user", uploadStatus.getCreationUser().equals(row[1]));
        assertFalse("check create_date", uploadStatus.getCreationTimestamp().equals(row[2]));
        assertEquals("check modify_user", uploadStatus.getModificationUser(), row[3]);
        assertEquals("check modify_date", uploadStatus.getModificationTimestamp(), row[4]);
        assertEquals("check name", uploadStatus.getName(), row[5]);
        assertEquals("check description", uploadStatus.getDescription(), row[6]);
    }

    /**
     * <p>
     * Failure test of the method <code>updateUploadStatus(UploadStatus uploadStatus)</code>.
     * </p>
     * <p>
     * uploadStatus is null. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testFailureUpdateUploadStatus1() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        try {
            persistence.updateUploadStatus(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            assertEquals("uploadStatus should not be null.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method <code>updateUploadStatus(UploadStatus uploadStatus)</code>.
     * </p>
     * <p>
     * uploadStatus is not valid to persist. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testFailureUpdateUploadStatus2() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        // create the sample UploadStatus object, do not set name
        UploadStatus uploadStatus = getSampleUploadStatus(1);
        uploadStatus.setName(null);

        try {
            persistence.updateUploadStatus(uploadStatus);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            assertEquals("The entity [uploadStatus] is not valid to persist.", e.getMessage());
        }
    }

    /**
     * <p>
     * Accuracy test of the method <code>updateSubmissionStatus(SubmissionStatus submissionStatus)</code>.
     * </p>
     * <p>
     * Check if the submission status is added in the database correctly.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testAccuracyUpdateSubmissionStatus1() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        // create the sample SubmissionStatus object
        SubmissionStatus submissionStatus = getSampleSubmissionStatus(1);

        persistence.updateSubmissionStatus(submissionStatus);

        // query the table
        Object[][] rows = Helper.doQuery(connectionFactory, null,
                "SELECT submission_status_id, create_user, create_date, "
                        + "modify_user, modify_date, name, description FROM submission_status_lu", new DataType[] {},
                new Object[] {}, new DataType[] {Helper.LONG_TYPE, Helper.STRING_TYPE, Helper.DATE_TYPE,
                    Helper.STRING_TYPE, Helper.DATE_TYPE, Helper.STRING_TYPE, Helper.STRING_TYPE}, null);

        assertEquals("5 results", 5, rows.length);
        Object[] row = rows[0];

        assertEquals("check id", new Long(submissionStatus.getId()), row[0]);
        assertFalse("check create_user", submissionStatus.getCreationUser().equals(row[1]));
        assertFalse("check create_date", submissionStatus.getCreationTimestamp().equals(row[2]));
        assertEquals("check modify_user", submissionStatus.getModificationUser(), row[3]);
        assertEquals("check modify_date", submissionStatus.getModificationTimestamp(), row[4]);
        assertEquals("check name", submissionStatus.getName(), row[5]);
        assertEquals("check description", submissionStatus.getDescription(), row[6]);
    }

    /**
     * <p>
     * Failure test of the method <code>updateSubmissionStatus(SubmissionStatus submissionStatus)</code>.
     * </p>
     * <p>
     * submissionStatus is null. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testFailureUpdateSubmissionStatus1() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        try {
            persistence.updateSubmissionStatus(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            assertEquals("submissionStatus should not be null.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method <code>updateSubmissionStatus(SubmissionStatus submissionStatus)</code>.
     * </p>
     * <p>
     * submissionStatus is not valid to persist. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testFailureUpdateSubmissionStatus2() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        // create the sample SubmissionStatus object, do not set name
        SubmissionStatus submissionStatus = getSampleSubmissionStatus(1);
        submissionStatus.setName(null);

        try {
            persistence.updateSubmissionStatus(submissionStatus);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            assertEquals("The entity [submissionStatus] is not valid to persist.", e.getMessage());
        }
    }

    /**
     * <p>
     * Accuracy test of the method <code>updateUpload(Upload upload)</code>.
     * </p>
     * <p>
     * Check if the upload is added in the database correctly.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testAccuracyUpdateUpload1() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        // create the sample Upload object
        Upload upload = getSampleUpload(1);

        persistence.updateUpload(upload);

        DataType[] columnTypes = new DataType[] {Helper.LONG_TYPE, Helper.STRING_TYPE, Helper.DATE_TYPE,
            Helper.STRING_TYPE, Helper.DATE_TYPE, Helper.LONG_TYPE, Helper.LONG_TYPE, Helper.STRING_TYPE,
            Helper.STRING_TYPE, Helper.LONG_TYPE, Helper.LONG_TYPE};
        // query the table
        Object[][] rows = Helper.doQuery(connectionFactory, null, "SELECT upload_id, create_user, create_date, "
                + "modify_user, modify_date, project_id, resource_id, parameter, upload_desc, "
                + " upload_type_id, upload_status_id FROM upload", new DataType[] {}, new Object[] {}, columnTypes,
                null);

        assertEquals("2 results", 2, rows.length);
        Object[] row = rows[0];

        assertEquals("check id", new Long(upload.getId()), row[0]);
        assertFalse("check create_user", upload.getCreationUser().equals(row[1]));
        assertFalse("check create_date", upload.getCreationTimestamp().equals(row[2]));
        assertEquals("check modify_user", upload.getModificationUser(), row[3]);
        assertEquals("check modify_date", upload.getModificationTimestamp(), row[4]);
        assertEquals("check project_id", new Long(upload.getProject()), row[5]);
        assertEquals("check resource_id", new Long(upload.getOwner()), row[6]);
        assertEquals("check parameter", upload.getParameter(), row[7]);
        assertEquals("check upload_desc", upload.getDescription(), row[8]);
        assertEquals("check upload_type_id", new Long(upload.getUploadType().getId()), row[9]);
        assertEquals("check upload_status_id", new Long(upload.getUploadStatus().getId()), row[10]);
    }

    /**
     * <p>
     * Failure test of the method <code>updateUpload(Upload upload)</code>.
     * </p>
     * <p>
     * upload is null. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testFailureUpdateUpload1() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        try {
            persistence.updateUpload(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            assertEquals("upload should not be null.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method <code>updateUpload(Upload upload)</code>.
     * </p>
     * <p>
     * upload is not valid to persist. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testFailureUpdateUpload2() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        // create the sample Upload object, do not set upload type
        Upload upload = getSampleUpload(1);
        upload.setUploadType(null);

        try {
            persistence.updateUpload(upload);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            assertEquals("The entity [upload] is not valid to persist.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test for the <code>updateUpload(Upload upload)</code> method when connection to database is invalid.
     * </p>
     * <p>
     * UploadPersistenceException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testFailureUpdateUpload3() throws Exception {
        UploadPersistence persistence = new SqlUploadPersistence(connectionFactory, "invalid_connection");

        Upload upload = getSampleUpload(1);

        try {
            persistence.updateUpload(upload);
            fail("UploadPersistenceException should be thrown.");
        } catch (UploadPersistenceException e) {
            // test passed
        }
    }

    /**
     * <p>
     * Accuracy test of the method <code>updateSubmission(Submission submission)</code>.
     * </p>
     * <p>
     * Check if the submission is added in the database correctly.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testAccuracyUpdateSubmission1() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        // create the sample Submission object
        Submission submission = getSampleSubmission(1);

        Upload upload = getSampleUpload(3);

        persistence.addUpload(upload);

        submission.setUploads(Arrays.asList(upload));

        persistence.updateSubmission(submission);

        DataType[] columnTypes = new DataType[] {Helper.LONG_TYPE, Helper.STRING_TYPE, Helper.DATE_TYPE,
            Helper.STRING_TYPE, Helper.DATE_TYPE, Helper.LONG_TYPE, Helper.LONG_TYPE, Helper.DOUBLE_TYPE,
            Helper.DOUBLE_TYPE, Helper.DOUBLE_TYPE, Helper.LONG_TYPE, Helper.BOOLEAN_TYPE, Helper.INTEGER_TYPE,
            Helper.BOOLEAN_TYPE, Helper.LONG_TYPE};
        // query the table
        Object[][] rows = Helper.doQuery(connectionFactory, null,
                "SELECT submission_id, create_user, create_date, modify_user, modify_date, "
                        + "submission_status_id, submission_type_id, screening_score, initial_score,"
                        + " final_score, placement, feedback_thumb, user_rank, mark_for_purchase, prize_id"
                        + " FROM submission", new DataType[] {}, new Object[] {}, columnTypes, null);

        assertEquals("2 results", 2, rows.length);
        Object[] row = rows[0];

        assertEquals("check id", new Long(submission.getId()), row[0]);
        assertFalse("check create_user", submission.getCreationUser().equals(row[1]));
        assertFalse("check create_date", submission.getCreationTimestamp().equals(row[2]));
        assertEquals("check modify_user", submission.getModificationUser(), row[3]);
        assertEquals("check modify_date", submission.getModificationTimestamp(), row[4]);
        assertEquals("check submission_status_id", new Long(submission.getSubmissionStatus().getId()), row[5]);
        assertEquals("check submission_type_id", new Long(submission.getSubmissionType().getId()), row[6]);
        assertEquals("check screening_score", submission.getScreeningScore(), row[7]);
        assertEquals("check initial_score", submission.getInitialScore(), row[8]);
        assertEquals("check final_score", submission.getFinalScore(), row[9]);
        assertEquals("check placement", submission.getPlacement(), row[10]);
        assertEquals("check feedback_thumb", submission.isThumb(), row[11]);
        assertEquals("check user_rank", submission.getUserRank(), row[12]);
        assertEquals("check mark_for_purchase", submission.isExtra(), row[13]);
        assertEquals("check prize_id", null, row[14]);
    }

    /**
     * <p>
     * Failure test of the method <code>updateSubmission(Submission submission)</code>.
     * </p>
     * <p>
     * submission is null. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testFailureUpdateSubmission1() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        try {
            persistence.updateSubmission(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            assertEquals("submission should not be null.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method <code>updateSubmission(Submission submission)</code>.
     * </p>
     * <p>
     * submission is not valid to persist. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testFailureUpdateSubmission2() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        // create the sample Submission object, do not set create user
        Submission submission = getSampleSubmission(1);
        submission.setCreationUser(null);

        try {
            persistence.updateSubmission(submission);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            assertEquals("The entity [submission] is not valid to persist.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test for the <code>updateSubmission(Submission submission)</code> method when connection to database is
     * invalid.
     * </p>
     * <p>
     * UploadPersistenceException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testFailureUpdateSubmission3() throws Exception {
        UploadPersistence persistence = new SqlUploadPersistence(connectionFactory, "invalid_connection");

        Submission submission = getSampleSubmission(1);

        try {
            persistence.updateSubmission(submission);
            fail("UploadPersistenceException should be thrown.");
        } catch (UploadPersistenceException e) {
            // test passed
        }
    }

    /**
     * <p>
     * Accuracy test of the method <code>removeUploadType(UploadType uploadType)</code>.
     * </p>
     * <p>
     * Check if the upload type is removed from the database correctly.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testAccuracyRemoveUploadType1() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        UploadType uploadType = new UploadType();
        uploadType.setId(1);

        persistence.removeUploadType(uploadType);

        // query the table
        Object[][] rows = Helper.doQuery(connectionFactory, null, "SELECT upload_type_id, create_user, create_date, "
                + "modify_user, modify_date, name, description FROM upload_type_lu", new DataType[] {},
                new Object[] {}, new DataType[] {Helper.LONG_TYPE, Helper.STRING_TYPE, Helper.DATE_TYPE,
                    Helper.STRING_TYPE, Helper.DATE_TYPE, Helper.STRING_TYPE, Helper.STRING_TYPE}, null);

        assertEquals("3 results", 3, rows.length);
    }

    /**
     * <p>
     * Failure test of the method <code>removeUploadType(UploadType uploadType)</code>.
     * </p>
     * <p>
     * uploadType is null. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testFailureRemoveUploadType1() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        try {
            persistence.removeUploadType(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            assertEquals("uploadType should not be null.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method <code>removeUploadType(UploadType uploadType)</code>.
     * </p>
     * <p>
     * uploadType id is -1. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testFailureRemoveUploadType2() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        // do not set id
        UploadType uploadType = new UploadType();

        try {
            persistence.removeUploadType(uploadType);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            assertEquals("uploadType id [-1] should not be UNSET_ID.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test for the <code>removeUploadType(UploadType uploadType)</code> method when connection to database is
     * invalid.
     * </p>
     * <p>
     * UploadPersistenceException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testFailureRemoveUploadType3() throws Exception {
        UploadPersistence persistence = new SqlUploadPersistence(connectionFactory, "invalid_connection");

        // create the sample UploadType object
        UploadType uploadType = getSampleUploadType(5);

        try {
            persistence.removeUploadType(uploadType);
            fail("UploadPersistenceException should be thrown.");
        } catch (UploadPersistenceException e) {
            // test passed
        }
    }

    /**
     * <p>
     * Accuracy test of the method <code>removeUploadStatus(UploadStatus uploadStatus)</code>.
     * </p>
     * <p>
     * Check if the upload status is removed from the database correctly.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testAccuracyRemoveUploadStatus1() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        UploadStatus uploadStatus = new UploadStatus();
        uploadStatus.setId(1);

        persistence.removeUploadStatus(uploadStatus);

        // query the table
        Object[][] rows = Helper.doQuery(connectionFactory, null, "SELECT upload_status_id, create_user, create_date, "
                + "modify_user, modify_date, name, description FROM upload_status_lu", new DataType[] {},
                new Object[] {}, new DataType[] {Helper.LONG_TYPE, Helper.STRING_TYPE, Helper.DATE_TYPE,
                    Helper.STRING_TYPE, Helper.DATE_TYPE, Helper.STRING_TYPE, Helper.STRING_TYPE}, null);

        assertEquals("1 result", 1, rows.length);
    }

    /**
     * <p>
     * Failure test of the method <code>removeUploadStatus(UploadStatus uploadStatus)</code>.
     * </p>
     * <p>
     * uploadStatus is null. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testFailureRemoveUploadStatus1() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        try {
            persistence.removeUploadStatus(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            assertEquals("uploadStatus should not be null.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method <code>removeUploadStatus(UploadStatus uploadStatus)</code>.
     * </p>
     * <p>
     * uploadStatus id is -1. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testFailureRemoveUploadStatus2() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        // do not set id
        UploadStatus uploadStatus = new UploadStatus();

        try {
            persistence.removeUploadStatus(uploadStatus);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            assertEquals("uploadStatus id [-1] should not be UNSET_ID.", e.getMessage());
        }
    }

    /**
     * <p>
     * Accuracy test of the method <code>removeSubmissionStatus(SubmissionStatus submissionStatus)</code>.
     * </p>
     * <p>
     * Check if the upload status is removed from the database correctly.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testAccuracyRemoveSubmissionStatus1() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        SubmissionStatus submissionStatus = new SubmissionStatus();
        submissionStatus.setId(2);

        persistence.removeSubmissionStatus(submissionStatus);

        // query the table
        Object[][] rows = Helper.doQuery(connectionFactory, null,
                "SELECT submission_status_id, create_user, create_date, "
                        + "modify_user, modify_date, name, description FROM submission_status_lu", new DataType[] {},
                new Object[] {}, new DataType[] {Helper.LONG_TYPE, Helper.STRING_TYPE, Helper.DATE_TYPE,
                    Helper.STRING_TYPE, Helper.DATE_TYPE, Helper.STRING_TYPE, Helper.STRING_TYPE}, null);

        assertEquals("4 results", 4, rows.length);
    }

    /**
     * <p>
     * Failure test of the method <code>removeSubmissionStatus(SubmissionStatus submissionStatus)</code>.
     * </p>
     * <p>
     * uploadStatus is null. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testFailureRemoveSubmissionStatus1() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        try {
            persistence.removeSubmissionStatus(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            assertEquals("submissionStatus should not be null.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method <code>removeSubmissionStatus(SubmissionStatus submissionStatus)</code>.
     * </p>
     * <p>
     * uploadStatus id is -1. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testFailureRemoveSubmissionStatus2() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        // create the sample SubmissionStatus object, do not set name
        SubmissionStatus submissionStatus = new SubmissionStatus();

        try {
            persistence.removeSubmissionStatus(submissionStatus);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            assertEquals("submissionStatus id [-1] should not be UNSET_ID.", e.getMessage());
        }
    }

    /**
     * <p>
     * Accuracy test of the method <code>removeUpload(Upload upload)</code>.
     * </p>
     * <p>
     * Check if the upload is removed from the database correctly.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testAccuracyRemoveUpload1() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        Upload upload = new Upload();
        upload.setId(3);

        persistence.removeUpload(upload);

        DataType[] columnTypes = new DataType[] {Helper.LONG_TYPE, Helper.STRING_TYPE, Helper.DATE_TYPE,
            Helper.STRING_TYPE, Helper.DATE_TYPE, Helper.LONG_TYPE, Helper.LONG_TYPE, Helper.STRING_TYPE,
            Helper.LONG_TYPE, Helper.LONG_TYPE};
        // query the table
        Object[][] rows = Helper.doQuery(connectionFactory, null, "SELECT upload_id, create_user, create_date, "
                + "modify_user, modify_date, project_id, resource_id, parameter,"
                + " upload_type_id, upload_status_id FROM upload", new DataType[] {}, new Object[] {}, columnTypes,
                null);

        assertEquals("2 results", 2, rows.length);
    }

    /**
     * <p>
     * Failure test of the method <code>removeUpload(Upload upload)</code>.
     * </p>
     * <p>
     * upload is null. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testFailureRemoveUpload1() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        try {
            persistence.removeUpload(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            assertEquals("upload should not be null.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method <code>removeUpload(Upload upload)</code>.
     * </p>
     * <p>
     * upload id is -1. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testFailureRemoveUpload2() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        // do not set upload id
        Upload upload = new Upload();

        try {
            persistence.removeUpload(upload);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            assertEquals("upload id [-1] should not be UNSET_ID.", e.getMessage());
        }
    }

    /**
     * <p>
     * Accuracy test of the method <code>removeSubmission(Submission submission)</code>.
     * </p>
     * <p>
     * Check if the submission is removed from the database correctly.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testAccuracyRemoveSubmission1() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        Submission submission = new Submission();
        submission.setId(1);

        persistence.removeSubmission(submission);

        DataType[] columnTypes = new DataType[] {Helper.LONG_TYPE, Helper.STRING_TYPE, Helper.DATE_TYPE,
            Helper.STRING_TYPE, Helper.DATE_TYPE, Helper.LONG_TYPE};
        // query the table
        Object[][] rows = Helper
                .doQuery(connectionFactory, null,
                        "SELECT submission_id, create_user, create_date, modify_user, modify_date, "
                                + "submission_status_id FROM submission", new DataType[] {}, new Object[] {},
                        columnTypes, null);

        assertEquals("1 result", 1, rows.length);
    }

    /**
     * <p>
     * Failure test of the method <code>removeSubmission(Submission submission)</code>.
     * </p>
     * <p>
     * submission is null. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testFailureRemoveSubmission1() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        try {
            persistence.removeSubmission(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            assertEquals("submission should not be null.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method <code>removeSubmission(Submission submission)</code>.
     * </p>
     * <p>
     * submission id is -1. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testFailureRemoveSubmission2() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        // do not set id
        Submission submission = new Submission();

        try {
            persistence.removeSubmission(submission);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            assertEquals("submission id [-1] should not be UNSET_ID.", e.getMessage());
        }
    }

    /**
     * <p>
     * Accuracy test of the method <code>getAllSubmissionStatusIds()</code>.
     * </p>
     * <p>
     * the returned ids should be correct.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testAccuracyGetAllSubmissionStatusIds1() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        long[] ids = persistence.getAllSubmissionStatusIds();
        long[] expected = new long[] {1, 2, 3, 4, 5};

        assertEquals("check ids length", expected.length, ids.length);
        for (int i = 0; i < expected.length; ++i) {
            assertEquals("check ids " + i, expected[i], ids[i]);
        }
    }

    /**
     * <p>
     * Accuracy test of the method <code>getAllUploadStatusIds()</code>.
     * </p>
     * <p>
     * the returned ids should be correct.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testAccuracyGetAllUploadStatusIds1() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        long[] ids = persistence.getAllUploadStatusIds();
        long[] expected = new long[] {1, 2};

        assertEquals("check ids length", expected.length, ids.length);
        for (int i = 0; i < expected.length; ++i) {
            assertEquals("check ids " + i, expected[i], ids[i]);
        }
    }

    /**
     * <p>
     * Accuracy test of the method <code>getAllUploadTypeIds()</code>.
     * </p>
     * <p>
     * the returned ids should be correct.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testAccuracyGetAllUploadTypeIds1() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        long[] ids = persistence.getAllUploadTypeIds();
        long[] expected = new long[] {1, 2, 3, 4};

        assertEquals("check ids length", expected.length, ids.length);
        for (int i = 0; i < expected.length; ++i) {
            assertEquals("check ids " + i, expected[i], ids[i]);
        }
    }

    /**
     * <p>
     * Failure test for the <code>getAllUploadTypeIds()</code> method when connection to database is invalid.
     * </p>
     * <p>
     * UploadPersistenceException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testFailureGetAllUploadTypeIds1() throws Exception {
        UploadPersistence persistence = new SqlUploadPersistence(connectionFactory, "invalid_connection");

        try {
            persistence.getAllUploadTypeIds();
            fail("UploadPersistenceException should be thrown.");
        } catch (UploadPersistenceException e) {
            // test passed
        }
    }

    /**
     * <p>
     * Accuracy test of the method <code>loadSubmissionStatus(long submissionStatusId)</code>.
     * </p>
     * <p>
     * Check if the submission status is loaded correctly.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testAccuracyLoadSubmissionStatus1() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        SubmissionStatus submissionStatus = persistence.loadSubmissionStatus(2);

        assertEquals("check id", 2, submissionStatus.getId());
        assertEquals("check create_user", "System", submissionStatus.getCreationUser());
        assertEquals("check modify_user", "System", submissionStatus.getModificationUser());
        assertEquals("check name", "Failed Screening", submissionStatus.getName());
        assertEquals("check description", "Failed Manual Screening", submissionStatus.getDescription());
    }

    /**
     * <p>
     * Accuracy test of the method <code>loadSubmissionStatus(long submissionStatusId)</code>.
     * </p>
     * <p>
     * Check if the submission status is loaded correctly. null should be returned.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testAccuracyLoadSubmissionStatus2() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        SubmissionStatus submissionStatus = persistence.loadSubmissionStatus(10);

        assertEquals("check submissionStatus", null, submissionStatus);
    }

    /**
     * <p>
     * Failure test of the method <code>loadSubmissionStatus(long submissionStatusId)</code>.
     * </p>
     * <p>
     * submissionStatusId is -1. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testFailureLoadSubmissionStatus1() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        try {
            persistence.loadSubmissionStatus(-1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            assertEquals("submissionStatusId [-1] should not be UNSET_ID.", e.getMessage());
        }
    }

    /**
     * <p>
     * Accuracy test of the method <code>loadSubmissionStatuses(long[] submissionStatusIds)</code>.
     * </p>
     * <p>
     * Check if the submission statuses are loaded correctly.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testAccuracyLoadSubmissionStatuses1() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        SubmissionStatus[] submissionStatuses = persistence.loadSubmissionStatuses(new long[] {2, 10});

        assertEquals("check # of entities get", 1, submissionStatuses.length);
        SubmissionStatus submissionStatus = submissionStatuses[0];

        assertEquals("check id", 2, submissionStatus.getId());
        assertEquals("check create_user", "System", submissionStatus.getCreationUser());
        assertEquals("check modify_user", "System", submissionStatus.getModificationUser());
        assertEquals("check name", "Failed Screening", submissionStatus.getName());
        assertEquals("check description", "Failed Manual Screening", submissionStatus.getDescription());
    }

    /**
     * <p>
     * Accuracy test of the method <code>loadSubmissionStatuses(long[] submissionStatusIds)</code>.
     * </p>
     * <p>
     * Check if the submission statuses are loaded correctly. empty array should be returned.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testAccuracyLoadSubmissionStatuses2() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        SubmissionStatus[] submissionStatuses = persistence.loadSubmissionStatuses(new long[0]);

        assertNotNull("submissionStatuses should not be null", submissionStatuses);
        assertEquals("check # of entities get", 0, submissionStatuses.length);
    }

    /**
     * <p>
     * Failure test of the method <code>loadSubmissionStatuses(long[] submissionStatusIds)</code>.
     * </p>
     * <p>
     * submissionStatusIds is null. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testFailureLoadSubmissionStatuses1() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        try {
            persistence.loadSubmissionStatuses(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            assertEquals("submissionStatusIds should not be null.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method <code>loadSubmissionStatuses(long[] submissionStatusIds)</code>.
     * </p>
     * <p>
     * submissionStatusIds contains -1. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testFailureLoadSubmissionStatuses2() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        try {
            persistence.loadSubmissionStatuses(new long[] {1, -1});
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            assertEquals("submissionStatusIds should only contain positive values.", e.getMessage());
        }
    }

    /**
     * <p>
     * Accuracy test of the method <code>loadUploadStatus(long uploadStatusId)</code>.
     * </p>
     * <p>
     * Check if the upload status is loaded correctly.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testAccuracyloadUploadStatus1() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        UploadStatus uploadStatus = persistence.loadUploadStatus(2);

        assertEquals("check id", 2, uploadStatus.getId());
        assertEquals("check create_user", "System", uploadStatus.getCreationUser());
        assertEquals("check modify_user", "System", uploadStatus.getModificationUser());
        assertEquals("check name", "Deleted", uploadStatus.getName());
        assertEquals("check description", "Deleted", uploadStatus.getDescription());
    }

    /**
     * <p>
     * Accuracy test of the method <code>loadUploadStatus(long uploadStatusId)</code>.
     * </p>
     * <p>
     * Check if the upload status is loaded correctly. null should be returned.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testAccuracyloadUploadStatus2() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        UploadStatus uploadStatus = persistence.loadUploadStatus(10);

        assertEquals("check uploadStatus", null, uploadStatus);
    }

    /**
     * <p>
     * Failure test of the method <code>loadUploadStatus(long uploadStatusId)</code>.
     * </p>
     * <p>
     * uploadStatusId is -1. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testFailureloadUploadStatus1() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        try {
            persistence.loadUploadStatus(-1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            assertEquals("uploadStatusId [-1] should not be UNSET_ID.", e.getMessage());
        }
    }

    /**
     * <p>
     * Accuracy test of the method <code>loadUploadStatuses(long[] uploadStatusIds)</code>.
     * </p>
     * <p>
     * Check if the upload statuses are loaded correctly.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testAccuracyloadUploadStatuses1() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        UploadStatus[] uploadStatuses = persistence.loadUploadStatuses(new long[] {2, 10});

        assertEquals("check # of entities get", 1, uploadStatuses.length);
        UploadStatus uploadStatus = uploadStatuses[0];

        assertEquals("check id", 2, uploadStatus.getId());
        assertEquals("check create_user", "System", uploadStatus.getCreationUser());
        assertEquals("check modify_user", "System", uploadStatus.getModificationUser());
        assertEquals("check name", "Deleted", uploadStatus.getName());
        assertEquals("check description", "Deleted", uploadStatus.getDescription());
    }

    /**
     * <p>
     * Accuracy test of the method <code>loadUploadStatuses(long[] uploadStatusIds)</code>.
     * </p>
     * <p>
     * Check if the upload statuses are loaded correctly. empty array should be returned.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testAccuracyloadUploadStatuses2() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        UploadStatus[] uploadStatuses = persistence.loadUploadStatuses(new long[0]);

        assertNotNull("uploadStatuses should not be null", uploadStatuses);
        assertEquals("check # of entities get", 0, uploadStatuses.length);
    }

    /**
     * <p>
     * Failure test of the method <code>loadUploadStatuses(long[] uploadStatusIds)</code>.
     * </p>
     * <p>
     * uploadStatusIds is null. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testFailureloadUploadStatuses1() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        try {
            persistence.loadUploadStatuses(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            assertEquals("uploadStatusIds should not be null.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method <code>loadUploadStatuses(long[] uploadStatusIds)</code>.
     * </p>
     * <p>
     * uploadStatusIds contains -1. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testFailureloadUploadStatuses2() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        try {
            persistence.loadUploadStatuses(new long[] {1, -1});
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            assertEquals("uploadStatusIds should only contain positive values.", e.getMessage());
        }
    }

    /**
     * <p>
     * Accuracy test of the method <code>loadUploadType(long uploadTypeId)</code>.
     * </p>
     * <p>
     * Check if the upload type is loaded correctly.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testAccuracyloadUploadType1() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        UploadType uploadType = persistence.loadUploadType(2);

        assertEquals("check id", 2, uploadType.getId());
        assertEquals("check create_user", "System", uploadType.getCreationUser());
        assertEquals("check modify_user", "System", uploadType.getModificationUser());
        assertEquals("check name", "Test Case", uploadType.getName());
        assertEquals("check description", "Test Case", uploadType.getDescription());
    }

    /**
     * <p>
     * Accuracy test of the method <code>loadUploadType(long uploadTypeId)</code>.
     * </p>
     * <p>
     * Check if the upload type is loaded correctly. null should be returned.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testAccuracyloadUploadType2() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        UploadType uploadType = persistence.loadUploadType(10);

        assertEquals("check uploadType", null, uploadType);
    }

    /**
     * <p>
     * Failure test of the method <code>loadUploadType(long uploadTypeId)</code>.
     * </p>
     * <p>
     * uploadTypeId is -1. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testFailureloadUploadType1() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        try {
            persistence.loadUploadType(-1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            assertEquals("uploadTypeId [-1] should not be UNSET_ID.", e.getMessage());
        }
    }

    /**
     * <p>
     * Accuracy test of the method <code>loadUploadTypes(long[] uploadTypeIds)</code>.
     * </p>
     * <p>
     * Check if the upload types are loaded correctly.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testAccuracyloadUploadTypes1() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        UploadType[] uploadTypes = persistence.loadUploadTypes(new long[] {2, 10});

        assertEquals("check # of entities get", 1, uploadTypes.length);
        UploadType uploadType = uploadTypes[0];

        assertEquals("check id", 2, uploadType.getId());
        assertEquals("check create_user", "System", uploadType.getCreationUser());
        assertEquals("check modify_user", "System", uploadType.getModificationUser());
        assertEquals("check name", "Test Case", uploadType.getName());
        assertEquals("check description", "Test Case", uploadType.getDescription());
    }

    /**
     * <p>
     * Accuracy test of the method <code>loadUploadTypes(long[] uploadTypeIds)</code>.
     * </p>
     * <p>
     * Check if the upload types are loaded correctly. empty array should be returned.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testAccuracyloadUploadTypes2() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        UploadType[] uploadTypes = persistence.loadUploadTypes(new long[0]);

        assertNotNull("uploadTypes should not be null", uploadTypes);
        assertEquals("check # of entities get", 0, uploadTypes.length);
    }

    /**
     * <p>
     * Failure test of the method <code>loadUploadTypes(long[] uploadTypeIds)</code>.
     * </p>
     * <p>
     * uploadTypeIds is null. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testFailureloadUploadTypes1() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        try {
            persistence.loadUploadTypes(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            assertEquals("uploadTypeIds should not be null.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method <code>loadUploadTypes(long[] uploadTypeIds)</code>.
     * </p>
     * <p>
     * uploadTypeIds contains -1. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testFailureloadUploadTypes2() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        try {
            persistence.loadUploadTypes(new long[] {1, -1});
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            assertEquals("uploadTypeIds should only contain positive values.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test for the <code>loadUploadTypes(long[] ids)</code> method when connection to database is invalid.
     * </p>
     * <p>
     * UploadPersistenceException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testFailureloadUploadTypes3() throws Exception {
        UploadPersistence persistence = new SqlUploadPersistence(connectionFactory, "invalid_connection");

        try {
            persistence.loadUploadTypes(new long[] {2, 10});
            fail("UploadPersistenceException should be thrown.");
        } catch (UploadPersistenceException e) {
            // test passed
        }
    }

    /**
     * <p>
     * Accuracy test of the method <code>loadUpload(long uploadId)</code>.
     * </p>
     * <p>
     * Check if the upload is loaded correctly.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testAccuracyLoadUpload1() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        Upload upload = persistence.loadUpload(2);

        assertEquals("check id", 2, upload.getId());
        assertEquals("check create_user", "System", upload.getCreationUser());
        assertEquals("check modify_user", "System", upload.getModificationUser());
        assertEquals("check project_id", 3, upload.getProject());
        assertEquals("check resource_id", 3, upload.getOwner());
        assertEquals("check parameter", "parameter 2", upload.getParameter());

        assertEquals("check type id", 2, upload.getUploadType().getId());
        assertEquals("check type create_user", "System", upload.getUploadType().getCreationUser());
        assertEquals("check type modify_user", "System", upload.getUploadType().getModificationUser());
        assertEquals("check type name", "Test Case", upload.getUploadType().getName());
        assertEquals("check type description", "Test Case", upload.getUploadType().getDescription());

        assertEquals("check status id", 2, upload.getUploadStatus().getId());
        assertEquals("check status create_user", "System", upload.getUploadStatus().getCreationUser());
        assertEquals("check status modify_user", "System", upload.getUploadStatus().getModificationUser());
        assertEquals("check status name", "Deleted", upload.getUploadStatus().getName());
        assertEquals("check status description", "Deleted", upload.getUploadStatus().getDescription());
    }

    /**
     * <p>
     * Accuracy test of the method <code>loadUpload(long uploadId)</code>.
     * </p>
     * <p>
     * Check if the upload is loaded correctly. null should be returned.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testAccuracyLoadUpload2() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        Upload upload = persistence.loadUpload(10);
        assertNull("upload should be null", upload);
    }

    /**
     * <p>
     * Failure test of the method <code>loadUpload(long uploadId)</code>.
     * </p>
     * <p>
     * uploadId is -1. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testFailureLoadUpload1() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        try {
            persistence.loadUpload(-1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            assertEquals("uploadId [-1] should not be UNSET_ID.", e.getMessage());
        }
    }

    /**
     * <p>
     * Accuracy test of the method <code>loadUploads(long[] uploadIds)</code>.
     * </p>
     * <p>
     * Check if the uploads are loaded correctly.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testAccuracyLoadUploads1() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        Upload[] uploads = persistence.loadUploads(new long[] {2, 10});

        assertEquals("check # of entities get", 1, uploads.length);
        Upload upload = uploads[0];

        assertEquals("check id", 2, upload.getId());
        assertEquals("check create_user", "System", upload.getCreationUser());
        assertEquals("check modify_user", "System", upload.getModificationUser());
        assertEquals("check project_id", 3, upload.getProject());
        assertEquals("check resource_id", 3, upload.getOwner());
        assertEquals("check parameter", "parameter 2", upload.getParameter());
        assertEquals("check upload_desc", "desc 2", upload.getDescription());

        assertEquals("check type id", 2, upload.getUploadType().getId());
        assertEquals("check type create_user", "System", upload.getUploadType().getCreationUser());
        assertEquals("check type modify_user", "System", upload.getUploadType().getModificationUser());
        assertEquals("check type name", "Test Case", upload.getUploadType().getName());
        assertEquals("check type description", "Test Case", upload.getUploadType().getDescription());

        assertEquals("check status id", 2, upload.getUploadStatus().getId());
        assertEquals("check status create_user", "System", upload.getUploadStatus().getCreationUser());
        assertEquals("check status modify_user", "System", upload.getUploadStatus().getModificationUser());
        assertEquals("check status name", "Deleted", upload.getUploadStatus().getName());
        assertEquals("check status description", "Deleted", upload.getUploadStatus().getDescription());
    }

    /**
     * <p>
     * Accuracy test of the method <code>loadUploads(long[] uploadIds)</code>.
     * </p>
     * <p>
     * Check if the uploads are loaded correctly. empty array should be returned.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testAccuracyLoadUploads2() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        Upload[] uploads = persistence.loadUploads(new long[0]);

        assertNotNull("uploads should not be null", uploads);
        assertEquals("check # of entities get", 0, uploads.length);
    }

    /**
     * <p>
     * Failure test for the <code>loadUploads(long[] ids)</code> method when connection to database is invalid.
     * </p>
     * <p>
     * UploadPersistenceException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testFailureLoadUploads() throws Exception {
        UploadPersistence persistence = new SqlUploadPersistence(connectionFactory, "invalid_connection");
        try {
            persistence.loadUploads(new long[] {2, 10});
            fail("UploadPersistenceException should be thrown.");
        } catch (UploadPersistenceException e) {
            // test passed
        }
    }

    /**
     * <p>
     * Accuracy test of the method <code>loadUploads(CustomResultSet customResultSet)</code>.
     * </p>
     * <p>
     * Check if the upload is loaded correctly.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testAccuracyLoadUploadsCustomResultSet1() throws Exception {
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        Upload[] uploads = null;

        UploadPersistence persistence = getUploadPersistence();

        try {
            // executeQuery
            conn = connectionFactory.createConnection();
            statement = conn.prepareStatement(UPLOAD_QUERY);
            statement.setInt(1, 2);
            resultSet = statement.executeQuery();

            uploads = persistence.loadUploads(new CustomResultSet(resultSet));
        } finally {
            TestHelper.close(resultSet);
            TestHelper.close(statement);
            TestHelper.close(conn);
        }

        assertEquals("check # of entities get", 1, uploads.length);
        Upload upload = uploads[0];

        assertEquals("check id", 2, upload.getId());
        assertEquals("check create_user", "System", upload.getCreationUser());
        assertEquals("check modify_user", "System", upload.getModificationUser());
        assertEquals("check project_id", 3, upload.getProject());
        assertEquals("check resource_id", 3, upload.getOwner());
        assertEquals("check parameter", "parameter 2", upload.getParameter());

        assertEquals("check type id", 2, upload.getUploadType().getId());
        assertEquals("check type create_user", "System", upload.getUploadType().getCreationUser());
        assertEquals("check type modify_user", "System", upload.getUploadType().getModificationUser());
        assertEquals("check type name", "Test Case", upload.getUploadType().getName());
        assertEquals("check type description", "Test Case", upload.getUploadType().getDescription());

        assertEquals("check status id", 2, upload.getUploadStatus().getId());
        assertEquals("check status create_user", "System", upload.getUploadStatus().getCreationUser());
        assertEquals("check status modify_user", "System", upload.getUploadStatus().getModificationUser());
        assertEquals("check status name", "Deleted", upload.getUploadStatus().getName());
        assertEquals("check status description", "Deleted", upload.getUploadStatus().getDescription());
    }

    /**
     * <p>
     * Accuracy test of the method <code>loadUploads(CustomResultSet customResultSet)</code>.
     * </p>
     * <p>
     * Check if the upload is loaded correctly.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testAccuracyLoadUploadsCustomResultSet2() throws Exception {
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        Upload[] uploads = null;

        UploadPersistence persistence = getUploadPersistence();

        try {
            // executeQuery
            conn = connectionFactory.createConnection();
            statement = conn.prepareStatement(UPLOAD_QUERY);
            statement.setInt(1, -1);
            resultSet = statement.executeQuery();

            uploads = persistence.loadUploads(new CustomResultSet(resultSet));
        } finally {
            TestHelper.close(resultSet);
            TestHelper.close(statement);
            TestHelper.close(conn);
        }

        assertEquals("check # of entities get", 0, uploads.length);
    }

    /**
     * <p>
     * Accuracy test of the method <code>loadUploads(CustomResultSet customResultSet)</code> when customResultSet is
     * null.
     * </p>
     * <p>
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testAccuracyLoadUploadsCustomResultSet_Null() throws Exception {

        UploadPersistence persistence = getUploadPersistence();

        try {
            persistence.loadUploads((CustomResultSet) null);

            fail("IllegalArgumentException was expected.");
        } catch (IllegalArgumentException exc) {
            // test fail
        }
    }

    /**
     * <p>
     * Failure test of the method <code>loadUploads(long[] uploadIds)</code>.
     * </p>
     * <p>
     * uploadIds is null. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testFailureLoadUploads1() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        try {
            persistence.loadUploads((long[]) null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            assertEquals("uploadIds should not be null.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method <code>loadUploads(long[] uploadIds)</code>.
     * </p>
     * <p>
     * uploadIds contains -1. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testFailureLoadUploads2() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        try {
            persistence.loadUploads(new long[] {1, -1});
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            assertEquals("uploadIds should only contain positive values.", e.getMessage());
        }
    }

    /**
     * <p>
     * Accuracy test of the method <code>loadSubmission(long submissionId)</code>.
     * </p>
     * <p>
     * Check if the submission is loaded correctly.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testAccuracyLoadSubmission1() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        Submission submission = persistence.loadSubmission(2);

        assertNotNull("the submission is not loaded properly", submission);
        assertEquals("check id", 2, submission.getId());
        assertEquals("check create_user", "System", submission.getCreationUser());
        assertEquals("check modify_user", "System", submission.getModificationUser());

        SubmissionStatus status = submission.getSubmissionStatus();
        assertEquals("check status id", 1, status.getId());
        assertEquals("check create_user", "System", status.getCreationUser());
        assertEquals("check modify_user", "System", status.getModificationUser());
        assertEquals("check name", "Active", status.getName());
        assertEquals("check description", "Active", status.getDescription());

        SubmissionType type = submission.getSubmissionType();
        assertEquals("check type id", 2, type.getId());
        assertEquals("check create_user", "System", type.getCreationUser());
        assertEquals("check modify_user", "System", type.getModificationUser());
        assertEquals("check name", "Specification", type.getName());
        assertEquals("check description", "Specification", type.getDescription());
    }

    /**
     * <p>
     * Accuracy test of the method <code>loadSubmission(long submissionId)</code>.
     * </p>
     * <p>
     * Check if the submission is loaded correctly. null should be returned.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testAccuracyLoadSubmission2() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        Submission submission = persistence.loadSubmission(10);
        assertNull("submission should be null", submission);
    }

    /**
     * <p>
     * Failure test of the method <code>loadSubmission(long submissionId)</code>.
     * </p>
     * <p>
     * submissionId is -1. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testFailureLoadSubmission1() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        try {
            persistence.loadSubmission(-1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            assertEquals("submissionId [-1] should not be UNSET_ID.", e.getMessage());
        }
    }

    /**
     * <p>
     * Accuracy test of the method <code>loadSubmissions(long[] submissionIds)</code>.
     * </p>
     * <p>
     * Check if the submissions are loaded correctly.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testAccuracyLoadSubmissions1() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        Submission[] submissions = persistence.loadSubmissions(new long[] {2, 10});

        assertEquals("check # of entities get", 1, submissions.length);
        Submission submission = submissions[0];

        assertEquals("check id", 2, submission.getId());
        assertEquals("check create_user", "System", submission.getCreationUser());
        assertEquals("check modify_user", "System", submission.getModificationUser());

        SubmissionStatus status = submission.getSubmissionStatus();
        assertEquals("check status id", 1, status.getId());
        assertEquals("check create_user", "System", status.getCreationUser());
        assertEquals("check modify_user", "System", status.getModificationUser());
        assertEquals("check name", "Active", status.getName());
        assertEquals("check description", "Active", status.getDescription());
        assertEquals("check screening_score", 90.0, submission.getScreeningScore());
        assertEquals("check initial_score", 90.0, submission.getInitialScore());
        assertEquals("check final_score", 90.0, submission.getFinalScore());
        assertEquals("check placement", new Long(1), submission.getPlacement());
        assertFalse("check feedback_thumb", submission.isThumb());
        assertEquals("check user_rank", 1, submission.getUserRank());
        assertFalse("check mark_for_purchase", submission.isExtra());
        Prize prize = submission.getPrize();
        assertNotNull("check prize", prize);
        assertEquals("check prize id", 1, prize.getId());
        assertEquals("check place", 1, prize.getPlace());
        assertEquals("check prize_amount", 500.0, prize.getPrizeAmount());
        assertEquals("check number of submissions", 4, prize.getNumberOfSubmissions());
        assertEquals("check create_user", "System", prize.getCreationUser());
        assertNotNull("check create_date", prize.getCreationTimestamp());
        assertNotNull("check modify_date", prize.getModificationTimestamp());
        assertEquals("check modify_user", "System", prize.getModificationUser());

        PrizeType prizeType = prize.getPrizeType();
        assertNotNull("check prize type", prizeType);
        assertEquals("check prize type id", 2, prizeType.getId());
        assertEquals("check description", "Submission Prize", prizeType.getDescription());

        SubmissionType type = submission.getSubmissionType();
        assertEquals("check type id", 2, type.getId());
        assertEquals("check create_user", "System", type.getCreationUser());
        assertEquals("check modify_user", "System", type.getModificationUser());
        assertEquals("check name", "Specification", type.getName());
        assertEquals("check description", "Specification", type.getDescription());

    }

    /**
     * <p>
     * Accuracy test of the method <code>loadSubmissions(long[] submissionIds)</code>.
     * </p>
     * <p>
     * Check if the submissions are loaded correctly. empty array should be returned.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testAccuracyLoadSubmissions2() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        Submission[] submissions = persistence.loadSubmissions(new long[0]);

        assertNotNull("submissions should not be null", submissions);
        assertEquals("check # of entities get", 0, submissions.length);
    }

    /**
     * <p>
     * Failure test of the method <code>loadSubmissions(long[] submissionIds)</code>.
     * </p>
     * <p>
     * submissionIds is null. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testFailureLoadSubmissions1() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        try {
            persistence.loadSubmissions((long[]) null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            assertEquals("submissionIds should not be null.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method <code>loadSubmissions(long[] submissionIds)</code>.
     * </p>
     * <p>
     * submissionIds contains -1. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testFailureLoadSubmissions2() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        try {
            persistence.loadSubmissions(new long[] {1, -1});
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            assertEquals("submissionIds should only contain positive values.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test for the <code>loadSubmissions(long[] ids)</code> method when connection to database is invalid.
     * </p>
     * <p>
     * UploadPersistenceException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testFailureLoadSubmissions3() throws Exception {
        UploadPersistence persistence = new SqlUploadPersistence(connectionFactory, "invalid_connection");

        try {
            persistence.loadSubmissions(new long[] {2, 10});
            fail("UploadPersistenceException should be thrown.");
        } catch (UploadPersistenceException e) {
            // test passed
        }
    }

    /**
     * <p>
     * Accuracy test of the method <code>loadSubmissions(CustomResultSet customResultSet)</code>.
     * </p>
     * <p>
     * Check if the submissions are loaded correctly.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testAccuracyLoadSubmissionsCustomResultSet1() throws Exception {
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Submission[] submissions = null;

        UploadPersistence persistence = getUploadPersistence();

        try {
            // executeQuery
            conn = connectionFactory.createConnection();
            statement = conn.prepareStatement(SUBMISSION_QUERY);
            statement.setInt(1, 2);
            resultSet = statement.executeQuery();

            submissions = persistence.loadSubmissions(new CustomResultSet(resultSet));
        } finally {
            TestHelper.close(resultSet);
            TestHelper.close(statement);
            TestHelper.close(conn);
        }

        assertEquals("check # of entities get", 1, submissions.length);
        Submission submission = submissions[0];

        assertEquals("check id", 2, submission.getId());
        assertEquals("check create_user", "System", submission.getCreationUser());
        assertEquals("check modify_user", "System", submission.getModificationUser());

        SubmissionStatus status = submission.getSubmissionStatus();
        assertEquals("check status id", 1, status.getId());
        assertEquals("check create_user", "System", status.getCreationUser());
        assertEquals("check modify_user", "System", status.getModificationUser());
        assertEquals("check name", "Active", status.getName());
        assertEquals("check description", "Active", status.getDescription());

        SubmissionType type = submission.getSubmissionType();
        assertEquals("check type id", 2, type.getId());
        assertEquals("check create_user", "System", type.getCreationUser());
        assertEquals("check modify_user", "System", type.getModificationUser());
        assertEquals("check name", "Specification", type.getName());
        assertEquals("check description", "Specification", type.getDescription());
    }

    /**
     * <p>
     * Accuracy test of the method <code>loadSubmissions(CustomResultSet customResultSet)</code>.
     * </p>
     * <p>
     * Check if the submissions are loaded correctly.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testAccuracyLoadSubmissionsCustomResultSet2() throws Exception {
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Submission[] submissions = null;

        UploadPersistence persistence = getUploadPersistence();

        try {
            // executeQuery
            conn = connectionFactory.createConnection();
            statement = conn.prepareStatement(SUBMISSION_QUERY);
            statement.setInt(1, -1);
            resultSet = statement.executeQuery();

            submissions = persistence.loadSubmissions(new CustomResultSet(resultSet));
        } finally {
            TestHelper.close(resultSet);
            TestHelper.close(statement);
            TestHelper.close(conn);
        }

        assertEquals("check # of entities get", 0, submissions.length);
    }

    /**
     * <p>
     * Accuracy test of the method <code>loadSubmissions(CustomResultSet customResultSet)</code>.
     * </p>
     * <p>
     * IllegalArgumentException was expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testAccuracyLoadSubmissionsCustomResultSetFailure() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        try {

            persistence.loadSubmissions((CustomResultSet) null);

            fail("IllegalArgumentException was expected.");
        } catch (IllegalArgumentException exc) {
            // test passed
        }
    }

    /**
     * <p>
     * Accuracy test of the method <code>addSubmissionType(SubmissionType submissionType)</code>.
     * </p>
     * <p>
     * Check if the submission type is added in the database correctly.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testAccuracyaddSubmissionType1() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        // create the sample SubmissionType object
        SubmissionType submissionType = getSampleSubmissionType(6);

        persistence.addSubmissionType(submissionType);

        // query the table
        Object[][] rows = Helper.doQuery(connectionFactory, null,
                "SELECT submission_type_id, create_user, create_date, "
                        + "modify_user, modify_date, name, description FROM submission_type_lu", new DataType[] {},
                new Object[] {}, new DataType[] {Helper.LONG_TYPE, Helper.STRING_TYPE, Helper.DATE_TYPE,
                    Helper.STRING_TYPE, Helper.DATE_TYPE, Helper.STRING_TYPE, Helper.STRING_TYPE}, null);

        assertEquals("6 results", 6, rows.length);
        Object[] row = rows[5];

        assertEquals("check id", new Long(submissionType.getId()), row[0]);
        assertEquals("check create_user", submissionType.getCreationUser(), row[1]);
        assertEquals("check create_date", submissionType.getCreationTimestamp(), row[2]);
        assertEquals("check modify_user", submissionType.getModificationUser(), row[3]);
        assertEquals("check modify_date", submissionType.getModificationTimestamp(), row[4]);
        assertEquals("check name", submissionType.getName(), row[5]);
        assertEquals("check description", submissionType.getDescription(), row[6]);
    }

    /**
     * <p>
     * Failure test of the method <code>addSubmissionType(SubmissionType submissionType)</code>.
     * </p>
     * <p>
     * submissionType is null. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testFailureaddSubmissionType1() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        try {
            persistence.addSubmissionType(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            assertEquals("submissionType should not be null.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method <code>addSubmissionType(SubmissionType submissionType)</code>.
     * </p>
     * <p>
     * submissionType is not valid to persist. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testFailureaddSubmissionType2() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        // create the sample SubmissionType object, do not set name
        SubmissionType submissionType = getSampleSubmissionType(6);
        submissionType.setName(null);

        try {
            persistence.addSubmissionType(submissionType);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            assertEquals("The entity [submissionType] is not valid to persist.", e.getMessage());
        }
    }

    /**
     * <p>
     * Accuracy test of the method <code>updateSubmissionType(SubmissionType submissionType)</code>.
     * </p>
     * <p>
     * Check if the submission status is added in the database correctly.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testAccuracyupdateSubmissionType1() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        // create the sample SubmissionType object
        SubmissionType submissionType = getSampleSubmissionType(1);

        persistence.updateSubmissionType(submissionType);

        // query the table
        Object[][] rows = Helper.doQuery(connectionFactory, null,
                "SELECT submission_type_id, create_user, create_date, "
                        + "modify_user, modify_date, name, description FROM submission_type_lu", new DataType[] {},
                new Object[] {}, new DataType[] {Helper.LONG_TYPE, Helper.STRING_TYPE, Helper.DATE_TYPE,
                    Helper.STRING_TYPE, Helper.DATE_TYPE, Helper.STRING_TYPE, Helper.STRING_TYPE}, null);

        assertEquals("5 results", 5, rows.length);
        Object[] row = rows[0];

        assertEquals("check id", new Long(submissionType.getId()), row[0]);
        assertFalse("check create_user", submissionType.getCreationUser().equals(row[1]));
        assertFalse("check create_date", submissionType.getCreationTimestamp().equals(row[2]));
        assertEquals("check modify_user", submissionType.getModificationUser(), row[3]);
        assertEquals("check modify_date", submissionType.getModificationTimestamp(), row[4]);
        assertEquals("check name", submissionType.getName(), row[5]);
        assertEquals("check description", submissionType.getDescription(), row[6]);
    }

    /**
     * <p>
     * Failure test of the method <code>updateSubmissionType(SubmissionType submissionType)</code>.
     * </p>
     * <p>
     * submissionType is null. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testFailureupdateSubmissionType1() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        try {
            persistence.updateSubmissionType(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            assertEquals("submissionType should not be null.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method <code>updateSubmissionType(SubmissionType submissionType)</code>.
     * </p>
     * <p>
     * submissionType is not valid to persist. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testFailureupdateSubmissionType2() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        // create the sample SubmissionType object, do not set name
        SubmissionType submissionType = getSampleSubmissionType(1);
        submissionType.setName(null);

        try {
            persistence.updateSubmissionType(submissionType);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            assertEquals("The entity [submissionType] is not valid to persist.", e.getMessage());
        }
    }

    /**
     * <p>
     * Accuracy test of the method <code>removeSubmissionType(SubmissionType submissionType)</code>.
     * </p>
     * <p>
     * Check if the upload status is removed from the database correctly.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testAccuracyRemoveSubmissionType1() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        SubmissionType submissionType = new SubmissionType();
        submissionType.setId(3);

        persistence.removeSubmissionType(submissionType);

        // query the table
        Object[][] rows = Helper.doQuery(connectionFactory, null,
                "SELECT submission_type_id, create_user, create_date, "
                        + "modify_user, modify_date, name, description FROM submission_type_lu", new DataType[] {},
                new Object[] {}, new DataType[] {Helper.LONG_TYPE, Helper.STRING_TYPE, Helper.DATE_TYPE,
                    Helper.STRING_TYPE, Helper.DATE_TYPE, Helper.STRING_TYPE, Helper.STRING_TYPE}, null);

        assertEquals("4 rows should remain.", 4, rows.length);
    }

    /**
     * <p>
     * Failure test of the method <code>removeSubmissionType(SubmissionType submissionType)</code>.
     * </p>
     * <p>
     * uploadStatus is null. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testFailureRemoveSubmissionType1() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        try {
            persistence.removeSubmissionType(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            assertEquals("submissionType should not be null.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method <code>removeSubmissionType(SubmissionType submissionType)</code>.
     * </p>
     * <p>
     * uploadStatus id is -1. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testFailureRemoveSubmissionType2() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        // create the sample SubmissionType object, do not set name
        SubmissionType submissionType = new SubmissionType();

        try {
            persistence.removeSubmissionType(submissionType);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            assertEquals("submissionType id [-1] should not be UNSET_ID.", e.getMessage());
        }
    }

    /**
     * <p>
     * Accuracy test of the method <code>loadSubmissionType(long submissionTypeId)</code>.
     * </p>
     * <p>
     * Check if the submission status is loaded correctly.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testAccuracyLoadSubmissionType1() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        SubmissionType submissionType = persistence.loadSubmissionType(2);

        assertEquals("check id", 2, submissionType.getId());
        assertEquals("check create_user", "System", submissionType.getCreationUser());
        assertEquals("check modify_user", "System", submissionType.getModificationUser());
        assertEquals("check name", "Specification", submissionType.getName());
        assertEquals("check description", "Specification", submissionType.getDescription());
    }

    /**
     * <p>
     * Accuracy test of the method <code>loadSubmissionType(long submissionTypeId)</code>.
     * </p>
     * <p>
     * Check if the submission status is loaded correctly. null should be returned.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testAccuracyLoadSubmissionType2() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        SubmissionType submissionType = persistence.loadSubmissionType(10);

        assertEquals("check submissionType", null, submissionType);
    }

    /**
     * <p>
     * Failure test of the method <code>loadSubmissionType(long submissionTypeId)</code>.
     * </p>
     * <p>
     * submissionTypeId is -1. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testFailureLoadSubmissionType1() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        try {
            persistence.loadSubmissionType(-1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            assertEquals("submissionTypeId [-1] should not be UNSET_ID.", e.getMessage());
        }
    }

    /**
     * <p>
     * Accuracy test of the method <code>loadSubmissionTypes(long[] submissionTypeIds)</code>.
     * </p>
     * <p>
     * Check if the submission statuses are loaded correctly.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testAccuracyLoadSubmissionTypes1() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        SubmissionType[] submissionTypes = persistence.loadSubmissionTypes(new long[] {2, 10});

        assertEquals("check # of entities get", 1, submissionTypes.length);
        SubmissionType submissionType = submissionTypes[0];

        assertEquals("check id", 2, submissionType.getId());
        assertEquals("check create_user", "System", submissionType.getCreationUser());
        assertEquals("check modify_user", "System", submissionType.getModificationUser());
        assertEquals("check name", "Specification", submissionType.getName());
        assertEquals("check description", "Specification", submissionType.getDescription());
    }

    /**
     * <p>
     * Accuracy test of the method <code>loadSubmissionTypes(long[] submissionTypeIds)</code>.
     * </p>
     * <p>
     * Check if the submission statuses are loaded correctly. empty array should be returned.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testAccuracyLoadSubmissionTypes2() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        SubmissionType[] submissionTypes = persistence.loadSubmissionTypes(new long[0]);

        assertNotNull("submissionTypes should not be null", submissionTypes);
        assertEquals("check # of entities get", 0, submissionTypes.length);
    }

    /**
     * <p>
     * Failure test of the method <code>loadSubmissionTypes(long[] submissionTypeIds)</code>.
     * </p>
     * <p>
     * submissionTypeIds is null. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testFailureLoadSubmissionTypes1() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        try {
            persistence.loadSubmissionTypes(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            assertEquals("submissionTypeIds should not be null.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method <code>loadSubmissionTypes(long[] submissionTypeIds)</code>.
     * </p>
     * <p>
     * submissionTypeIds contains -1. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testFailureLoadSubmissionTypes2() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        try {
            persistence.loadSubmissionTypes(new long[] {1, -1});
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            assertEquals("submissionTypeIds should only contain positive values.", e.getMessage());
        }
    }

    /**
     * <p>
     * Accuracy test of the method <code>getAllSubmissionTypeIds()</code>.
     * </p>
     * <p>
     * the returned ids should be correct.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testAccuracyGetAllSubmissionTypeIds1() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        long[] ids = persistence.getAllSubmissionTypeIds();
        long[] expected = new long[] {1, 2, 3, 4, 5};

        assertEquals("check ids length", expected.length, ids.length);
        for (int i = 0; i < expected.length; ++i) {
            assertEquals("check ids " + i, expected[i], ids[i]);
        }
    }

    /**
     * <p>
     * Tests the <code>addSubmissionImage(SubmissionImage)</code> method.
     * </p>
     * <p>
     * If the submissionImage is null, should throw IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     * @since 1.2
     */
    public void testAddSubmissionImage_Null() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        try {
            persistence.addSubmissionImage(null);
            fail("expect IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests the <code>addSubmissionImage(SubmissionImage)</code> method.
     * </p>
     * <p>
     * If the submissionImage is invalid to persist, should throw IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     * @since 1.2
     */
    public void testAddSubmissionImage_invalidToPersist() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        try {
            persistence.addSubmissionImage(new SubmissionImage());
            fail("expect IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests the <code>addSubmissionImage(SubmissionImage)</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     * @since 1.2
     */
    public void testAddSubmissionImageAccuracy() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        SubmissionImage submissionImage = getSampleSubmissionImage(1, 1);

        persistence.addSubmissionImage(submissionImage);

        // verify it in database
        DataType[] columnTypes = new DataType[] {Helper.LONG_TYPE, Helper.INTEGER_TYPE, Helper.INTEGER_TYPE,
            Helper.DATE_TYPE, Helper.DATE_TYPE};
        // query the table
        Object[][] rows = Helper.doQuery(connectionFactory, null, "SELECT submission_id, image_id, sort_order, "
                + "create_date, modify_date FROM submission_image WHERE submission_id = ? AND image_id = ?",
                new DataType[] {Helper.LONG_TYPE, Helper.INTEGER_TYPE}, new Object[] {
                    submissionImage.getSubmissionId(), submissionImage.getImageId()}, columnTypes, null);

        assertEquals("1 results", 1, rows.length);
        Object[] row = rows[0];

        assertEquals("check sort_order", submissionImage.getSortOrder(), row[2]);
        assertEquals("check create_date", submissionImage.getCreateDate(), row[3]);
        assertEquals("check modify_date", submissionImage.getModifyDate(), row[4]);
    }

    /**
     * <p>
     * Tests the <code>updateSubmissionImage(SubmissionImage)</code> method.
     * </p>
     * <p>
     * If the submissionImage is null, should throw IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     * @since 1.2
     */
    public void testUpdateSubmissionImage_Null() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        try {
            persistence.updateSubmissionImage(null);
            fail("expect IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests the <code>updateSubmissionImage(SubmissionImage)</code> method.
     * </p>
     * <p>
     * If the submissionImage is invalid to persist, should throw IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     * @since 1.2
     */
    public void testUpdateSubmissionImage_invalidToPersist() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        try {
            persistence.updateSubmissionImage(new SubmissionImage());
            fail("expect IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests the <code>updateSubmissionImage(SubmissionImage)</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     * @since 1.2
     */
    public void testUpdateSubmissionImageAccuracy() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        SubmissionImage submissionImage = getSampleSubmissionImage(1, 1);

        persistence.addSubmissionImage(submissionImage);

        // change some fields
        submissionImage.setSortOrder(33);
        submissionImage.setModifyDate(new Date());

        persistence.updateSubmissionImage(submissionImage);

        // verify it in database
        DataType[] columnTypes = new DataType[] {Helper.LONG_TYPE, Helper.INTEGER_TYPE, Helper.INTEGER_TYPE,
            Helper.DATE_TYPE, Helper.DATE_TYPE};
        // query the table
        Object[][] rows = Helper.doQuery(connectionFactory, null, "SELECT submission_id, image_id, sort_order, "
                + "create_date, modify_date FROM submission_image WHERE submission_id = ? AND image_id = ?",
                new DataType[] {Helper.LONG_TYPE, Helper.INTEGER_TYPE}, new Object[] {
                    submissionImage.getSubmissionId(), submissionImage.getImageId()}, columnTypes, null);

        assertEquals("1 results", 1, rows.length);
        Object[] row = rows[0];

        assertEquals("check sort_order", submissionImage.getSortOrder(), row[2]);
        assertEquals("check create_date", submissionImage.getCreateDate(), row[3]);
        assertEquals("check modify_date", submissionImage.getModifyDate(), row[4]);
    }

    /**
     * <p>
     * Tests the <code>removeSubmissionImage(SubmissionImage)</code> method.
     * </p>
     * <p>
     * If the submissionImage is null, should throw IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     * @since 1.2
     */
    public void testRemoveSubmissionImage_Null() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        try {
            persistence.removeSubmissionImage(null);
            fail("expect IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests the <code>removeSubmissionImage(SubmissionImage)</code> method.
     * </p>
     * <p>
     * If the submission id is negative, should throw IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     * @since 1.2
     */
    public void testRemoveSubmissionImage_submissionId_negative() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        SubmissionImage submissionImage = getSampleSubmissionImage(-1, 1);

        try {
            persistence.removeSubmissionImage(submissionImage);
            fail("expect IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests the <code>removeSubmissionImage(SubmissionImage)</code> method.
     * </p>
     * <p>
     * If the submission id is zero, should throw IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     * @since 1.2
     */
    public void testRemoveSubmissionImage_submissionId_zero() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        SubmissionImage submissionImage = getSampleSubmissionImage(0, 1);

        try {
            persistence.removeSubmissionImage(submissionImage);
            fail("expect IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests the <code>removeSubmissionImage(SubmissionImage)</code> method.
     * </p>
     * <p>
     * If the image id is negative, should throw IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     * @since 1.2
     */
    public void testRemoveSubmissionImage_imageId_negative() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        SubmissionImage submissionImage = getSampleSubmissionImage(1, -1);

        try {
            persistence.removeSubmissionImage(submissionImage);
            fail("expect IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests the <code>removeSubmissionImage(SubmissionImage)</code> method.
     * </p>
     * <p>
     * If the image id is zero, should throw IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     * @since 1.2
     */
    public void testRemoveSubmissionImage_imageId_zero() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        SubmissionImage submissionImage = getSampleSubmissionImage(1, 0);

        try {
            persistence.removeSubmissionImage(submissionImage);
            fail("expect IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests the <code>removeSubmissionImage(SubmissionImage)</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     * @since 1.2
     */
    public void testRemoveSubmissionImageAccuracy() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        SubmissionImage submissionImage = getSampleSubmissionImage(1, 1);

        persistence.addSubmissionImage(submissionImage);

        // remove the submission image
        persistence.removeSubmissionImage(submissionImage);

        // verify it in database
        DataType[] columnTypes = new DataType[] {Helper.LONG_TYPE, Helper.INTEGER_TYPE, Helper.INTEGER_TYPE,
            Helper.DATE_TYPE, Helper.DATE_TYPE};
        // query the table
        Object[][] rows = Helper.doQuery(connectionFactory, null, "SELECT submission_id, image_id, sort_order, "
                + "create_date, modify_date FROM submission_image WHERE submission_id = ? AND image_id = ?",
                new DataType[] {Helper.LONG_TYPE, Helper.INTEGER_TYPE}, new Object[] {
                    submissionImage.getSubmissionId(), submissionImage.getImageId()}, columnTypes, null);

        assertEquals("0 results", 0, rows.length);
    }

    /**
     * <p>
     * Tests the <code>getAllMimeTypeIds()</code> method.
     * </p>
     * <p>
     * Should returns all the mime type ids in database.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     * @since 1.2
     */
    public void testGetAllMimeTypeIdsAccuracy() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        long[] mimeTypeIds = persistence.getAllMimeTypeIds();

        assertNotNull("the array should not be null.", mimeTypeIds);
        assertEquals("the array size should be 1.", 1, mimeTypeIds.length);
        assertEquals("The mime type id should be 1.", 1, mimeTypeIds[0]);
    }

    /**
     * <p>
     * Tests the <code>loadMimeTypes(long[])</code> method.
     * </p>
     * <p>
     * If the mimeTypeIds is null, should throw IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     * @since 1.2
     */
    public void testLoadMimeTypes_null() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        try {
            persistence.loadMimeTypes(null);

            fail("expect IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests the <code>loadMimeType(long)</code> method.
     * </p>
     * <p>
     * If the mimeTypeId is negative, should throw IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     * @since 1.2
     */
    public void testLoadMimeType_containsNegative() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        try {
            persistence.loadMimeType(-1);

            fail("expect IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests the <code>loadMimeType(long)</code> method.
     * </p>
     * <p>
     * If the mimeTypeId is zero, should throw IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     * @since 1.2
     */
    public void testLoadMimeType_containsZero() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        try {
            persistence.loadMimeType(0);

            fail("expect IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests the <code>loadMimeType(long)</code> method.
     * </p>
     * <p>
     * If the mimeTypeIds does not exist in database, should return null.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     * @since 1.2
     */
    public void testLoadMimeTypeAccuracy1() throws Exception {
        UploadPersistence persistence = getUploadPersistence();
        MimeType mimeType = persistence.loadMimeType(1110);

        assertNull("Should return null.", mimeType);
    }

    /**
     * <p>
     * Tests the <code>loadMimeType(long)</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     * @since 1.2
     */
    public void testLoadMimeTypeAccuracy2() throws Exception {
        UploadPersistence persistence = getUploadPersistence();
        MimeType mimeType = persistence.loadMimeType(1);

        assertNotNull("The element should not be null", mimeType);
        assertEquals("check description", "mime1", mimeType.getDescription());
        FileType fileType = mimeType.getFileType();
        assertNotNull("check file type", fileType);
        assertEquals("check file type description", "file type", fileType.getDescription());
        assertEquals("check extension", "extension", fileType.getExtension());
        assertEquals("check sort", 1, fileType.getSort());
        assertTrue("check image_file_ind", fileType.isImageFile());
        assertTrue("check bundled_file_ind", fileType.isBundledFile());
    }

    /**
     * <p>
     * Tests the <code>loadMimeTypes(long[])</code> method.
     * </p>
     * <p>
     * If the mimeTypeIds contains negative, should throw IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     * @since 1.2
     */
    public void testLoadMimeTypes_containsNegative() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        try {
            persistence.loadMimeTypes(new long[] {-1, 1});

            fail("expect IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests the <code>loadMimeTypes(long[])</code> method.
     * </p>
     * <p>
     * If the mimeTypeIds contains zero, should throw IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     * @since 1.2
     */
    public void testLoadMimeTypes_containsZero() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        try {
            persistence.loadMimeTypes(new long[] {0, 1});

            fail("expect IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests the <code>loadMimeTypes(long[])</code> method.
     * </p>
     * <p>
     * If the mimeTypeIds does not exist in database, should return empty array.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     * @since 1.2
     */
    public void testLoadMimeTypesAccuracy1() throws Exception {
        UploadPersistence persistence = getUploadPersistence();
        MimeType[] mimeTypes = persistence.loadMimeTypes(new long[] {1110, 1111});

        assertNotNull("Should never return null.", mimeTypes);
        assertEquals("The array size is zero.", 0, mimeTypes.length);
    }

    /**
     * <p>
     * Tests the <code>loadMimeTypes(long[])</code> method.
     * </p>
     * <p>
     * If the mimeTypeIds is empty, should return empty array.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     * @since 1.2
     */
    public void testLoadMimeTypesAccuracy2() throws Exception {
        UploadPersistence persistence = getUploadPersistence();
        MimeType[] mimeTypes = persistence.loadMimeTypes(new long[0]);

        assertNotNull("Should never return null.", mimeTypes);
        assertEquals("The array size is zero.", 0, mimeTypes.length);
    }

    /**
     * <p>
     * Tests the <code>loadMimeTypes(long[])</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     * @since 1.2
     */
    public void testLoadMimeTypesAccuracy3() throws Exception {
        UploadPersistence persistence = getUploadPersistence();
        MimeType[] mimeTypes = persistence.loadMimeTypes(new long[] {1});

        assertNotNull("Should never return null.", mimeTypes);
        assertEquals("The array size is 1.", 1, mimeTypes.length);

        MimeType mimeType = mimeTypes[0];

        assertNotNull("The element should not be null", mimeType);
        assertEquals("check description", "mime1", mimeType.getDescription());
        FileType fileType = mimeType.getFileType();
        assertNotNull("check file type", fileType);
        assertEquals("check file type description", "file type", fileType.getDescription());
        assertEquals("check extension", "extension", fileType.getExtension());
        assertEquals("check sort", 1, fileType.getSort());
        assertTrue("check image_file_ind", fileType.isImageFile());
        assertTrue("check bundled_file_ind", fileType.isBundledFile());
    }

    /**
     * <p>
     * Tests the <code>getUserSubmissionsForProject(long, long)</code> method.
     * </p>
     * <p>
     * If the projectId is negative, should throw IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     * @since 1.2
     */
    public void testGetUserSubmissionsForProject_projectId_negative() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        try {
            persistence.getUserSubmissionsForProject(-1, 1);

            fail("expect IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests the <code>getUserSubmissionsForProject(long, long)</code> method.
     * </p>
     * <p>
     * If the projectId is zero, should throw IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     * @since 1.2
     */
    public void testGetUserSubmissionsForProject_projectId_zero() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        try {
            persistence.getUserSubmissionsForProject(0, 1);

            fail("expect IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests the <code>getUserSubmissionsForProject(long, long)</code> method.
     * </p>
     * <p>
     * If the ownerId is negative, should throw IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     * @since 1.2
     */
    public void testGetUserSubmissionsForProject_ownerId_negative() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        try {
            persistence.getUserSubmissionsForProject(1, -1);

            fail("expect IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests the <code>getUserSubmissionsForProject(long, long)</code> method.
     * </p>
     * <p>
     * If the ownerId is zero, should throw IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     * @since 1.2
     */
    public void testGetUserSubmissionsForProject_ownerId_zero() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        try {
            persistence.getUserSubmissionsForProject(1, 0);

            fail("expect IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests the <code>getUserSubmissionsForProject(long, long)</code> method.
     * </p>
     * <p>
     * If the project id or owner id does not exist, should return empty array.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     * @since 1.2
     */
    public void testGetUserSubmissionsForProjectAccuracy1() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        Submission[] submissions = persistence.getUserSubmissionsForProject(100, 1);

        assertNotNull("The array should not be null", submissions);
        assertEquals("The array should be empty.", 0, submissions.length);
    }

    /**
     * <p>
     * Tests the <code>getUserSubmissionsForProject(long, long)</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     * @since 1.2
     */
    public void testGetUserSubmissionsForProjectAccuracy2() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        Submission[] submissions = persistence.getUserSubmissionsForProject(2, 2);

        assertNotNull("The array should not be null", submissions);
        assertEquals("The array should have one element.", 1, submissions.length);

        Submission submission = submissions[0];
        assertNotNull("The instance should not be null", submission);
        assertEquals("The submission id is incorrect.", 2, submission.getId());
        assertEquals("check create_user", "System", submission.getCreationUser());
        assertEquals("check modify_user", "System", submission.getModificationUser());

        SubmissionStatus status = submission.getSubmissionStatus();
        assertEquals("check status id", 1, status.getId());
        assertEquals("check create_user", "System", status.getCreationUser());
        assertEquals("check modify_user", "System", status.getModificationUser());
        assertEquals("check name", "Active", status.getName());
        assertEquals("check description", "Active", status.getDescription());
        assertEquals("check screening_score", 90.0, submission.getScreeningScore());
        assertEquals("check initial_score", 90.0, submission.getInitialScore());
        assertEquals("check final_score", 90.0, submission.getFinalScore());
        assertEquals("check placement", new Long(1), submission.getPlacement());
        assertFalse("check feedback_thumb", submission.isThumb());
        assertEquals("check user_rank", 1, submission.getUserRank());
        assertFalse("check mark_for_purchase", submission.isExtra());
        Prize prize = submission.getPrize();
        assertNotNull("check prize", prize);
        assertEquals("check prize id", 1, prize.getId());
        assertEquals("check place", 1, prize.getPlace());
        assertEquals("check prize_amount", 500.0, prize.getPrizeAmount());
        assertEquals("check number of submissions", 4, prize.getNumberOfSubmissions());
        assertEquals("check create_user", "System", prize.getCreationUser());
        assertNotNull("check create_date", prize.getCreationTimestamp());
        assertNotNull("check modify_date", prize.getModificationTimestamp());
        assertEquals("check modify_user", "System", prize.getModificationUser());

        PrizeType prizeType = prize.getPrizeType();
        assertNotNull("check prize type", prizeType);
        assertEquals("check prize type id", 2, prizeType.getId());
        assertEquals("check description", "Submission Prize", prizeType.getDescription());

        SubmissionType type = submission.getSubmissionType();
        assertEquals("check type id", 2, type.getId());
        assertEquals("check create_user", "System", type.getCreationUser());
        assertEquals("check modify_user", "System", type.getModificationUser());
        assertEquals("check name", "Specification", type.getName());
        assertEquals("check description", "Specification", type.getDescription());
    }

    /**
     * <p>
     * Tests the <code>getProjectSubmissions(long)</code> method.
     * </p>
     * <p>
     * If the projectId is zero, should throw IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     * @since 1.2
     */
    public void testGetProjectSubmissions_projectId_zero() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        try {
            persistence.getProjectSubmissions(0);

            fail("expect IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests the <code>getProjectSubmissions(long)</code> method.
     * </p>
     * <p>
     * If the projectId is negative, should throw IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     * @since 1.2
     */
    public void testGetProjectSubmissions_projectId_negative() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        try {
            persistence.getProjectSubmissions(-1);

            fail("expect IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests the <code>getProjectSubmissions(long)</code> method.
     * </p>
     * <p>
     * If the project id does not exist, should return empty array.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     * @since 1.2
     */
    public void testGetProjectSubmissionsAccuracy1() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        Submission[] submissions = persistence.getProjectSubmissions(100);

        assertNotNull("The array should not be null", submissions);
        assertEquals("The array should be empty.", 0, submissions.length);
    }

    /**
     * <p>
     * Tests the <code>getProjectSubmissions(long)</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     * @since 1.2
     */
    public void testGetProjectSubmissionsAccuracy2() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        Submission[] submissions = persistence.getProjectSubmissions(2);

        assertNotNull("The array should not be null", submissions);
        assertEquals("The array should have one element.", 1, submissions.length);

        Submission submission = submissions[0];
        assertNotNull("The instance should not be null", submission);
        assertEquals("The submission id is incorrect.", 2, submission.getId());
        assertEquals("check create_user", "System", submission.getCreationUser());
        assertEquals("check modify_user", "System", submission.getModificationUser());

        SubmissionStatus status = submission.getSubmissionStatus();
        assertEquals("check status id", 1, status.getId());
        assertEquals("check create_user", "System", status.getCreationUser());
        assertEquals("check modify_user", "System", status.getModificationUser());
        assertEquals("check name", "Active", status.getName());
        assertEquals("check description", "Active", status.getDescription());
        assertEquals("check screening_score", 90.0, submission.getScreeningScore());
        assertEquals("check initial_score", 90.0, submission.getInitialScore());
        assertEquals("check final_score", 90.0, submission.getFinalScore());
        assertEquals("check placement", new Long(1), submission.getPlacement());
        assertFalse("check feedback_thumb", submission.isThumb());
        assertEquals("check user_rank", 1, submission.getUserRank());
        assertFalse("check mark_for_purchase", submission.isExtra());
        Prize prize = submission.getPrize();
        assertNotNull("check prize", prize);
        assertEquals("check prize id", 1, prize.getId());
        assertEquals("check place", 1, prize.getPlace());
        assertEquals("check prize_amount", 500.0, prize.getPrizeAmount());
        assertEquals("check number of submissions", 4, prize.getNumberOfSubmissions());
        assertEquals("check create_user", "System", prize.getCreationUser());
        assertNotNull("check create_date", prize.getCreationTimestamp());
        assertNotNull("check modify_date", prize.getModificationTimestamp());
        assertEquals("check modify_user", "System", prize.getModificationUser());

        PrizeType prizeType = prize.getPrizeType();
        assertNotNull("check prize type", prizeType);
        assertEquals("check prize type id", 2, prizeType.getId());
        assertEquals("check description", "Submission Prize", prizeType.getDescription());

        SubmissionType type = submission.getSubmissionType();
        assertEquals("check type id", 2, type.getId());
        assertEquals("check create_user", "System", type.getCreationUser());
        assertEquals("check modify_user", "System", type.getModificationUser());
        assertEquals("check name", "Specification", type.getName());
        assertEquals("check description", "Specification", type.getDescription());
    }

    /**
     * <p>
     * Tests the <code>getImagesForSubmission(long)</code> method.
     * </p>
     * <p>
     * If the submissionId is negative, should throw IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     * @since 1.2
     */
    public void testGetImagesForSubmission_submissionId_negative() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        try {
            persistence.getImagesForSubmission(-1);

            fail("expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests the <code>getImagesForSubmission(long)</code> method.
     * </p>
     * <p>
     * If the submissionId is zero, should throw IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     * @since 1.2
     */
    public void testGetImagesForSubmission_submissionId_zero() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        try {
            persistence.getImagesForSubmission(0);

            fail("expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests the <code>getImagesForSubmission(long)</code> method.
     * </p>
     * <p>
     * If the submissionId does not exist, should return empty array.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     * @since 1.2
     */
    public void testGetImagesForSubmission_submissionId_notExist() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        SubmissionImage[] submissionImages = persistence.getImagesForSubmission(10000);

        assertNotNull("The returned array should not be null", submissionImages);
        assertEquals("The returned array should be empty", 0, submissionImages.length);
    }

    /**
     * <p>
     * Tests the <code>getImagesForSubmission(long)</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     * @since 1.2
     */
    public void testGetImagesForSubmissionAccuracy() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        SubmissionImage image = getSampleSubmissionImage(1, 1);
        persistence.addSubmissionImage(image);

        SubmissionImage[] submissionImages = persistence.getImagesForSubmission(1);

        assertNotNull("The returned array should not be null", submissionImages);
        assertEquals("The returned array should have one element", 1, submissionImages.length);

        SubmissionImage submissionImage = submissionImages[0];

        assertNotNull("the submission image can not be null.", submissionImage);

        assertEquals("check image id.", image.getImageId(), submissionImage.getImageId());
        assertEquals("check sort order.", image.getSortOrder(), submissionImage.getSortOrder());
        assertEquals("check create date.", image.getCreateDate(), submissionImage.getCreateDate());
        assertEquals("check modify date.", image.getModifyDate(), submissionImage.getModifyDate());
    }

    /**
     * <p>
     * Tests the <code>getUploadsForSubmission(long)</code> method.
     * </p>
     * <p>
     * If the submissionId is negative, should throw IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     * @since 1.2
     */
    public void testGetUploadsForSubmission_submissionId_negative() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        try {
            persistence.getUploadsForSubmission(-1);

            fail("expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests the <code>getUploadsForSubmission(long)</code> method.
     * </p>
     * <p>
     * If the submissionId is zero, should throw IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     * @since 1.2
     */
    public void testGetUploadsForSubmission_submissionId_zero() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        try {
            persistence.getUploadsForSubmission(0);

            fail("expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests the <code>getUploadsForSubmission(long)</code> method.
     * </p>
     * <p>
     * If the submissionId does not exist, should return empty array.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     * @since 1.2
     */
    public void testGetUploadsForSubmission_submissionId_notExist() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        Upload[] uploads = persistence.getUploadsForSubmission(10000);

        assertNotNull("The returned array should not be null", uploads);
        assertEquals("The returned array should be empty", 0, uploads.length);
    }

    /**
     * <p>
     * Tests the <code>getUploadsForSubmission(long)</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     * @since 1.2
     */
    public void testGetUploadsForSubmissionAccuracy() throws Exception {
        UploadPersistence persistence = getUploadPersistence();

        Upload[] uploads = persistence.getUploadsForSubmission(1);

        assertNotNull("The returned array should not be null", uploads);
        assertEquals("The returned array should have one element", 1, uploads.length);

        Upload upload = uploads[0];

        assertNotNull("the submission image can not be null.", upload);
        assertEquals("check id", 2, upload.getId());
        assertEquals("check create_user", "System", upload.getCreationUser());
        assertEquals("check modify_user", "System", upload.getModificationUser());
        assertEquals("check project_id", 3, upload.getProject());
        assertEquals("check resource_id", 3, upload.getOwner());
        assertEquals("check parameter", "parameter 2", upload.getParameter());

        assertEquals("check type id", 2, upload.getUploadType().getId());
        assertEquals("check type create_user", "System", upload.getUploadType().getCreationUser());
        assertEquals("check type modify_user", "System", upload.getUploadType().getModificationUser());
        assertEquals("check type name", "Test Case", upload.getUploadType().getName());
        assertEquals("check type description", "Test Case", upload.getUploadType().getDescription());

        assertEquals("check status id", 2, upload.getUploadStatus().getId());
        assertEquals("check status create_user", "System", upload.getUploadStatus().getCreationUser());
        assertEquals("check status modify_user", "System", upload.getUploadStatus().getModificationUser());
        assertEquals("check status name", "Deleted", upload.getUploadStatus().getName());
        assertEquals("check status description", "Deleted", upload.getUploadStatus().getDescription());
    }

    /**
     * <p>
     * Retrieves the UploadPersistence instance.
     * </p>
     * <p>
     * Refactor to make a central place for retrieving the concrete UploadPersistence instance.
     * </p>
     *
     * @return the UploadPersistence instance.
     * @since 1.2
     */
    private SqlUploadPersistence getUploadPersistence() {
        return new SqlUploadPersistence(connectionFactory, "informix_connection");
    }

    /**
     * Get an sample SubmissionType instance.
     *
     * @param id
     *            the id of the entity
     * @return a SubmissionType instance
     */
    private SubmissionType getSampleSubmissionType(int id) {
        // create the sample SubmissionType object
        SubmissionType submissionType = new SubmissionType();
        submissionType.setId(id);
        submissionType.setName("New SubmissionStatus" + id);
        submissionType.setDescription("New SubmissionStatus Description" + id);

        submissionType.setCreationUser("submissionStatus create user" + id);
        submissionType.setCreationTimestamp(new Date());
        submissionType.setModificationUser("submissionStatus modify user" + id);
        submissionType.setModificationTimestamp(new Date());

        return submissionType;
    }

    /**
     * <p>
     * Gets an sample UploadType instance.
     * </p>
     *
     * @param id
     *            the id of the entity
     * @return an UploadType instance
     */
    private UploadType getSampleUploadType(int id) {
        // create the sample UploadType object
        UploadType uploadType = new UploadType();
        uploadType.setId(id);
        uploadType.setName("New UploadType" + id);
        uploadType.setDescription("New UploadType Description" + id);

        uploadType.setCreationUser("uploadType create user" + id);
        uploadType.setCreationTimestamp(new Date());
        uploadType.setModificationUser("uploadType modify user" + id);
        uploadType.setModificationTimestamp(new Date());

        return uploadType;
    }

    /**
     * Get an sample UploadStatus instance.
     *
     * @param id
     *            the id of the entity
     * @return an UploadStatus instance
     */
    private UploadStatus getSampleUploadStatus(int id) {
        // create the sample UploadStatus object
        UploadStatus uploadStatus = new UploadStatus();
        uploadStatus.setId(id);
        uploadStatus.setName("New UploadStatus" + id);
        uploadStatus.setDescription("New UploadStatus Description" + id);

        uploadStatus.setCreationUser("uploadStatus create user" + id);
        uploadStatus.setCreationTimestamp(new Date());
        uploadStatus.setModificationUser("uploadStatus modify user" + id);
        uploadStatus.setModificationTimestamp(new Date());

        return uploadStatus;
    }

    /**
     * Get an sample SubmissionStatus instance.
     *
     * @param id
     *            the id of the entity
     * @return a SubmissionStatus instance
     */
    private SubmissionStatus getSampleSubmissionStatus(int id) {
        // create the sample SubmissionStatus object
        SubmissionStatus submissionStatus = new SubmissionStatus();
        submissionStatus.setId(id);
        submissionStatus.setName("New SubmissionStatus" + id);
        submissionStatus.setDescription("New SubmissionStatus Description" + id);

        submissionStatus.setCreationUser("submissionStatus create user" + id);
        submissionStatus.setCreationTimestamp(new Date());
        submissionStatus.setModificationUser("submissionStatus modify user" + id);
        submissionStatus.setModificationTimestamp(new Date());

        return submissionStatus;
    }

    /**
     * Get an sample Upload instance.
     *
     * @param id
     *            the id of the entity
     * @return an Upload instance
     */
    private Upload getSampleUpload(int id) {
        // create the sample Upload object
        Upload upload = new Upload();
        upload.setId(id);
        upload.setProject(1);
        upload.setOwner(1);
        upload.setParameter("parameter" + id);
        upload.setDescription("description" + id);

        upload.setCreationUser("upload create user");
        upload.setCreationTimestamp(new Date());
        upload.setModificationUser("upload modify user");
        upload.setModificationTimestamp(new Date());

        upload.setUploadType(getSampleUploadType(1));
        upload.setUploadStatus(getSampleUploadStatus(1));

        return upload;
    }

    /**
     * Get a sample SubmissionImage instance.
     *
     * @param submissionId
     *            the submission id
     * @param imageId
     *            the image id
     * @return an SubmissionImage instance
     * @since 1.2
     */
    private SubmissionImage getSampleSubmissionImage(long submissionId, int imageId) {
        SubmissionImage image = new SubmissionImage();
        image.setSubmissionId(submissionId);
        image.setImageId(imageId);
        image.setSortOrder(1);
        image.setCreateDate(new Date());
        image.setModifyDate(new Date());

        return image;
    }

    /**
     * Get a sample Submission instance.
     *
     * @param id
     *            the id of the entity
     * @return a Submission instance
     */
    private Submission getSampleSubmission(int id) {
        // create the sample Submission object
        Submission submission = new Submission();
        submission.setId(id);

        submission.setCreationUser("submission create user" + id);
        submission.setCreationTimestamp(new Date());
        submission.setModificationUser("submission modify user" + id);
        submission.setModificationTimestamp(new Date());
        submission.setInitialScore(90.0);
        submission.setFinalScore(90.0);
        submission.setScreeningScore(90.0);
        submission.setPlacement(1l);
        submission.setUserRank(1);

        submission.setSubmissionStatus(getSampleSubmissionStatus(1));
        submission.setSubmissionType(getSampleSubmissionType(1));

        submission.setUploads(Arrays.asList(getSampleUpload(id)));
        submission.setImages(Arrays.asList(getSampleSubmissionImage(id, 1)));
        return submission;
    }
}
