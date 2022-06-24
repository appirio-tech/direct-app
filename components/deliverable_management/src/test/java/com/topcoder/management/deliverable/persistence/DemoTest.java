/*
 * Copyright (C) 2006,2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable.persistence;

import java.sql.Connection;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Date;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.management.deliverable.Deliverable;
import com.topcoder.management.deliverable.MimeType;
import com.topcoder.management.deliverable.Submission;
import com.topcoder.management.deliverable.SubmissionImage;
import com.topcoder.management.deliverable.SubmissionStatus;
import com.topcoder.management.deliverable.SubmissionType;
import com.topcoder.management.deliverable.Upload;
import com.topcoder.management.deliverable.UploadStatus;
import com.topcoder.management.deliverable.UploadType;
import com.topcoder.management.deliverable.persistence.sql.SqlDeliverablePersistence;
import com.topcoder.management.deliverable.persistence.sql.SqlUploadPersistence;

/**
 * This TestCase demonstrates the usage of this component.
 *
 * @author urtks, TCSDEVELOPER
 * @version 1.2
 */
@SuppressWarnings("unused")
public class DemoTest extends TestCase {

    /**
     * Aggregates all tests in this class.
     *
     * @return test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(DemoTest.class);
    }

    /**
     * Sets up the test environment. The configuration will be loaded.
     *
     * @throws Exception
     *             throw any exception to JUnit
     */
    protected void setUp() throws Exception {
        tearDown();

        TestHelper.loadConfig();

        TestHelper.executeBatch("test_files/insertDemo.sql");
    }

    /**
     * Clean up the test environment. The configuration will be unloaded.
     *
     * @throws Exception
     *             throw any exception to JUnit
     */
    protected void tearDown() throws Exception {
        TestHelper.clearConfig();
        TestHelper.loadConfig();

        TestHelper.executeBatch("test_files/delete.sql");

        TestHelper.clearConfig();
    }

    /**
     * This method demonstrates the method to create a SqlUploadPersistence.
     *
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testCreateSqlUploadPersistence() throws Exception {
        // first a DBConnectionFactory instance is created.
        DBConnectionFactory connectionFactory = new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName());

        // create the instance of SqlUploadPersistence class, using the default
        // connection name
        UploadPersistence persistence1 = new SqlUploadPersistence(connectionFactory);

        // or create the instance of SqlUploadPersistence class, using the given
        // connection name
        UploadPersistence persistence2 = new SqlUploadPersistence(connectionFactory, "informix_connection");
    }

    /**
     * This method demonstrates the method to create a SqlDeliverablePersistence.
     *
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testCreateSqlDeliverablePersistence() throws Exception {
        // first a DBConnectionFactory instance is created.
        DBConnectionFactory connectionFactory = new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName());

        // create the instance of SqlDeliverablePersistence class, using the
        // default connection name
        DeliverablePersistence persistence1 = new SqlDeliverablePersistence(connectionFactory);

        // or create the instance of SqlDeliverablePersistence class, using the
        // given connection name
        DeliverablePersistence persistence2 = new SqlDeliverablePersistence(connectionFactory, "informix_connection");
    }

    /**
     * This method demonstrates the usage of the UploadPersistence to manage all the AuditedDeliverableStructure
     * entities except Deliverable in this component.
     *
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testManageEntity() throws Exception {
        // first a DBConnectionFactory instance is created.
        DBConnectionFactory connectionFactory = new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName());

        // then create the instance of SqlUploadPersistence class
        UploadPersistence persistence = new SqlUploadPersistence(connectionFactory);

        // ////////
        // load the Upload object from the persistence
        Upload upload = persistence.loadUpload(1);
        // the above loading can be batched.
        Upload[] uploads = persistence.loadUploads(new long[] {1, 2, 3});

        // add a new Upload object to the persistence
        Upload upload2 = new Upload();
        upload2.setId(4);
        upload2.setProject(upload.getProject());
        upload2.setOwner(upload.getOwner());
        upload2.setUploadType(upload.getUploadType());
        upload2.setUploadStatus(upload.getUploadStatus());
        upload2.setParameter(upload.getParameter());
        upload2.setCreationUser(upload.getCreationUser());
        upload2.setCreationTimestamp(upload.getCreationTimestamp());
        upload2.setModificationUser(upload.getModificationUser());
        upload2.setModificationTimestamp(upload.getModificationTimestamp());
        persistence.addUpload(upload2);

        // update the Upload object to the persistence
        upload2.setParameter("new param");
        upload2.setDescription("new desc");
        persistence.updateUpload(upload2);

        // finally the Upload object can be removed from the persistence
        persistence.removeUpload(upload2);

        // ////////
        // load the UploadType object from the persistence
        UploadType uploadType = persistence.loadUploadType(1);
        // the above loading can be batched.
        UploadType[] uploadTypes = persistence.loadUploadTypes(new long[] {1, 2, 3});

        // add a new UploadType object to the persistence
        UploadType uploadType2 = new UploadType();
        uploadType2.setId(10);
        uploadType2.setName(uploadType.getName());
        uploadType2.setDescription(uploadType.getDescription());
        uploadType2.setCreationUser(uploadType.getCreationUser());
        uploadType2.setCreationTimestamp(uploadType.getCreationTimestamp());
        uploadType2.setModificationUser(uploadType.getModificationUser());
        uploadType2.setModificationTimestamp(uploadType.getModificationTimestamp());
        persistence.addUploadType(uploadType2);

        // update the UploadType object to the persistence
        uploadType2.setDescription("new description");
        persistence.updateUploadType(uploadType2);

        // finally the UploadType object can be removed from the persistence
        persistence.removeUploadType(uploadType2);

        // ////////
        // load the UploadStatus object from the persistence
        UploadStatus uploadStatus = persistence.loadUploadStatus(1);
        // the above loading can be batched.
        UploadStatus[] uploadStatuses = persistence.loadUploadStatuses(new long[] {1, 2, 3});

        // add a new UploadStatus object to the persistence
        UploadStatus uploadStatus2 = new UploadStatus();
        uploadStatus2.setId(10);
        uploadStatus2.setName(uploadStatus.getName());
        uploadStatus2.setDescription(uploadStatus.getDescription());
        uploadStatus2.setCreationUser(uploadStatus.getCreationUser());
        uploadStatus2.setCreationTimestamp(uploadStatus.getCreationTimestamp());
        uploadStatus2.setModificationUser(uploadStatus.getModificationUser());
        uploadStatus2.setModificationTimestamp(uploadStatus.getModificationTimestamp());
        persistence.addUploadStatus(uploadStatus2);

        // update the UploadStatus object to the persistence
        uploadStatus2.setDescription("new description");
        persistence.updateUploadStatus(uploadStatus2);

        // finally the UploadStatus object can be removed from the persistence
        persistence.removeUploadStatus(uploadStatus2);

        // ////////
        // load the SubmissionStatus object from the persistence
        SubmissionStatus submissionStatus = persistence.loadSubmissionStatus(1);
        // the above loading can be batched.
        SubmissionStatus[] submissionStatuses = persistence.loadSubmissionStatuses(new long[] {1, 2, 3});

        // load submission type
        SubmissionType submissionType = persistence.loadSubmissionType(1L);

        // add a new SubmissionStatus object to the persistence
        SubmissionStatus submissionStatus2 = new SubmissionStatus();
        submissionStatus2.setId(10);
        submissionStatus2.setName(submissionStatus.getName());
        submissionStatus2.setDescription(submissionStatus.getDescription());
        submissionStatus2.setCreationUser(submissionStatus.getCreationUser());
        submissionStatus2.setCreationTimestamp(submissionStatus.getCreationTimestamp());
        submissionStatus2.setModificationUser(submissionStatus.getModificationUser());
        submissionStatus2.setModificationTimestamp(submissionStatus.getModificationTimestamp());
        persistence.addSubmissionStatus(submissionStatus2);

        // save submission
        Submission submission = new Submission(823);
        submission.setCreationUser("admin");
        submission.setCreationTimestamp(new Date());
        submission.setModificationUser("admin");
        submission.setModificationTimestamp(new Date());
        submission.setSubmissionStatus(submissionStatus);
        submission.setSubmissionType(submissionType);
        submission.setThumb(true);
        submission.setUserRank(2);
        submission.setExtra(true);
        submission.setUploads(Arrays.asList(persistence.loadUpload(1)));

        persistence.addSubmission(submission);

        // remove the created submission
        persistence.removeSubmission(submission);

        // update the SubmissionStatus object to the persistence
        submissionStatus2.setDescription("new description");
        persistence.updateSubmissionStatus(submissionStatus2);

        // finally the SubmissionStatus object can be removed from the
        // persistence
        persistence.removeSubmissionStatus(submissionStatus2);

        // for UploadType, UploadStatus SubmissionStatus, SubmissionType, we can get all their
        // ids in the database
        long[] ids1 = persistence.getAllUploadTypeIds();
        long[] ids2 = persistence.getAllUploadStatusIds();
        long[] ids3 = persistence.getAllSubmissionStatusIds();
        long[] ids4 = persistence.getAllSubmissionTypeIds();
    }

    /**
     * This method demonstrates the usage of DeliverablePersistence to manage Deliverable in this component.
     *
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testManageDeliverable() throws Exception {
        // first a DBConnectionFactory instance is created.
        DBConnectionFactory connectionFactory = new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName());

        // then create the instance of SqlDeliverablePersistence class
        DeliverablePersistence persistence = new SqlDeliverablePersistence(connectionFactory);

        // load a "per submission" deliverable from the persistence
        Deliverable deliverable = persistence.loadDeliverable(2, 1, 1, 1);
        // the above loading can be batched.
        Deliverable[] deliverables1 = persistence.loadDeliverables(new long[] {2, 1}, new long[] {1, 2}, new long[] {1,
            1}, new long[] {1, 1});

        // load a general deliverable from the persistence
        Deliverable[] deliverables2 = persistence.loadDeliverables(2, 2, 1);
        // the above loading can be batched.
        Deliverable[] deliverables3 = persistence.loadDeliverables(new long[] {2, 1}, new long[] {1, 2}, new long[] {1,
            1});
    }

    /**
     * <p>
     * Demonstrates creation submission type.
     * </p>
     *
     * @throws Exception
     *             if any error occurs
     */
    public void testCreateSubmissionType() throws Exception {

        Connection connection = null;

        // first a DBConnectionFactory instance is created.
        DBConnectionFactory connectionFactory = new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName());

        connection = connectionFactory.createConnection();
        Statement statement = connection.createStatement();

        statement.addBatch("delete from submission");
        statement.addBatch("delete from deliverable_lu");
        statement.addBatch("delete from submission_type_lu");
        statement.addBatch("INSERT INTO submission_type_lu(submission_type_id, name, description, create_user, "
                + " create_date, modify_user, modify_date) "
                + " VALUES(1, 'Submission', 'The contest submission', 'admin', CURRENT, 'admin', CURRENT)");
        statement.executeBatch();
        statement.close();
        connection.close();

        // then create the instance of SqlUploadPersistence class
        UploadPersistence persistence = new SqlUploadPersistence(connectionFactory);

        /**
         * Demo 4.3.7 from CS
         */

        // Load submission type with ID=1
        SubmissionType submissionType = persistence.loadSubmissionType(1);

        // Update name of submission type together with audit data
        submissionType.setName("Contest Submission");
        submissionType.setModificationTimestamp(new Date());
        submissionType.setModificationUser("user1");

        // Update submission type in persistence
        persistence.updateSubmissionType(submissionType);

        // Create SubmissionType instance for "Specification Submission" type
        submissionType = new SubmissionType();
        submissionType.setId(2);
        submissionType.setName("Specification Submission");
        submissionType.setDescription("The specification submission");
        submissionType.setCreationTimestamp(new Date());
        submissionType.setModificationTimestamp(new Date());
        submissionType.setCreationUser("user1");
        submissionType.setModificationUser("user1");

        // Add this new submission type to persistence
        persistence.addSubmissionType(submissionType);

        // Retrieve all existing submission type IDs
        long[] submissionTypeIds = persistence.getAllSubmissionTypeIds();

        // check the ids
        long[] expected = new long[] {1, 2};

        assertEquals("wrong submissionTypeIds length", expected.length, submissionTypeIds.length);
        for (int i = 0; i < expected.length; ++i) {
            assertEquals("invalid submissionTypeId " + i, expected[i], submissionTypeIds[i]);
        }
    }

    /**
     * Demonstrates management of SubmissionImage.
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     * @since 1.2
     */
    public void testManageSubmissionImage() throws Exception {
        // first a DBConnectionFactory instance is created.
        DBConnectionFactory connectionFactory = new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName());

        // then create the instance of SqlUploadPersistence class
        UploadPersistence uploadPersistence = new SqlUploadPersistence(connectionFactory);

        // Create submission image
        SubmissionImage submissionImage = new SubmissionImage();

        // submission with id 1 is already in database.
        submissionImage.setSubmissionId(1);
        submissionImage.setImageId(1);
        submissionImage.setSortOrder(1);
        uploadPersistence.addSubmissionImage(submissionImage);

        // Update the submission image
        submissionImage.setSortOrder(0);
        uploadPersistence.updateSubmissionImage(submissionImage);

        // Remove the submission image
        uploadPersistence.removeSubmissionImage(submissionImage);
    }

    /**
     * Demonstrates management of MimeType.
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     * @since 1.2
     */
    public void testManageMimeType() throws Exception {
        // first a DBConnectionFactory instance is created.
        DBConnectionFactory connectionFactory = new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName());

        // then create the instance of SqlUploadPersistence class
        UploadPersistence uploadPersistence = new SqlUploadPersistence(connectionFactory);

        // Retrieve the MIME type with ID=1
        MimeType mimeType = uploadPersistence.loadMimeType(1);

        // Retrieve IDs of all MIME types
        long[] mimeTypeIds = uploadPersistence.getAllMimeTypeIds();

        // Retrieve all MIME types by their IDs
        MimeType[] mimeTypes = uploadPersistence.loadMimeTypes(mimeTypeIds);
    }

    /**
     * Demonstrates retrieval of Submissions.
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     * @since 1.2
     */
    public void testSubmissionRetrieval() throws Exception {
        // first a DBConnectionFactory instance is created.
        DBConnectionFactory connectionFactory = new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName());

        // then create the instance of SqlUploadPersistence class
        UploadPersistence uploadPersistence = new SqlUploadPersistence(connectionFactory);

        // Retrieve the submissions for project with ID=1 and user with ID=1
        Submission[] submissions = uploadPersistence.getUserSubmissionsForProject(1, 1);

        // Retrieve all submissions for project with ID=1
        submissions = uploadPersistence.getProjectSubmissions(1);

        // Retrieve the images for submission with ID=1
        SubmissionImage[] submissionImages = uploadPersistence.getImagesForSubmission(1);

    }
}
