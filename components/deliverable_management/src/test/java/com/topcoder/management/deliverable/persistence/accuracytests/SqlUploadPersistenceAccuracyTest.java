/*
 * Copyright (C) 2006 - 2010 TopCoder Inc., All Rights Reserved.
 */



package com.topcoder.management.deliverable.persistence.accuracytests;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.management.deliverable.NamedDeliverableStructure;
import com.topcoder.management.deliverable.Submission;
import com.topcoder.management.deliverable.SubmissionStatus;
import com.topcoder.management.deliverable.SubmissionType;
import com.topcoder.management.deliverable.Upload;
import com.topcoder.management.deliverable.UploadStatus;
import com.topcoder.management.deliverable.UploadType;
import com.topcoder.management.deliverable.persistence.UploadPersistence;
import com.topcoder.management.deliverable.persistence.sql.SqlUploadPersistence;

import junit.framework.TestCase;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

/**
 * <p>
 * Accuracy test for <code>SqlUploadPersistence</code> class.
 * </p>
 *
 * @author arylio, morehappiness
 * @version 1.1
 */
public class SqlUploadPersistenceAccuracyTest extends TestCase {
    /**
     * <p>
     * The connection name.
     * </p>
     */
    private static final String CONNECTION_NAME = "informix";

    /**
     * <p>
     * The create date.
     * </p>
     */
    private Date PREDEFINED_CREATE_DATE = null;

    /**
     * <p>
     * Descriptions for submission status.
     * </p>
     */
    private String[] SS_DESCRIPTION = new String[] { "Active", "Failed Manual Screening", "Failed Review",
            "Completed Without Win", "Deleted" };

    /**
     * <p>
     * Descriptions for submission status.
     * </p>
     */
    private String[] US_DESCRIPTION = new String[] { "Active", "Deleted" };

    /**
     * <p>
     * Descriptions for submission status.
     * </p>
     */
    private String[] UT_DESCRIPTION = new String[] { "Submission", "Test Case", "Final Fix", "Review Document" };

    /**
     * <p>
     * Predefined Submissions.
     * </p>
     */
    private Submission[] PREDEFINED_SUBMISSION;

    /**
     * <p>
     * Predefined submission status.
     * </p>
     */
    private SubmissionStatus[] PREDEFINED_SUBMISSION_STATUS;

    /**
     * <p>
     * Predefined submission type.
     * </p>
     */
    private SubmissionType[] PREDEFINED_SUBMISSION_TYPE;

    /**
     * <p>
     * Predefined uploads.
     * </p>
     */
    private Upload[] PREDEFINED_UPLOAD;

    /**
     * <p>
     * Predefined upload status.
     * </p>
     */
    private UploadStatus[] PREDEFINED_UPLOADSTATUS;

    /**
     * <p>
     * Predefined upload type.
     * </p>
     */
    private UploadType[] PREDEFINED_UPLOADTYPE;

    /**
     * <p>
     * A connection to database.
     * </p>
     */
    private Connection connection;

    /**
     * <p>
     * An instance of db connection factory.
     * </p>
     */
    private DBConnectionFactory factory;

    /**
     * <p>
     * An upload.
     * </p>
     */
    private Submission submission;

    /**
     * <p>
     * A submission status
     * </p>
     */
    private SubmissionStatus submissionStatus;

    /**
     * <p>
     * A submission type
     * </p>
     */
    private SubmissionType submissionType;

    /**
     * <p>
     * An instance of <code>SqlUploadPersistence</code> to test.
     * </p>
     */
    private SqlUploadPersistence tester;

    /**
     * <p>
     * An upload.
     * </p>
     */
    private Upload upload;

    /**
     * <p>
     * An upload status.
     * </p>
     */
    private UploadStatus uploadStatus;

    /**
     * <p>
     * An upload type.
     * </p>
     */
    private UploadType uploadType;

    /**
     * <p>
     * Test addSubmissionType(SubmissionType submissionType),
     * submissionType should be saved to persistence.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testAddSubmissionType() throws Exception {
        assertPersistence("submissionType should be not existed.", submissionType, false, "submission_type");
        tester.addSubmissionType(submissionType);
        assertPersistence("submissionType should be existed.", submissionType, true, "submission_type");
    }

    /**
     * <p>
     * Test removeSubmissionType(SubmissionType submissionType),
     * submissionType should be removed from persistence.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testRemoveSubmissionType() throws Exception {
        assertPersistence("submissionType should be existed.", PREDEFINED_SUBMISSION_TYPE[1], true, "submission_type");
        tester.removeSubmissionType(PREDEFINED_SUBMISSION_TYPE[1]);
        assertPersistence("submissionType should be NOT existed.", PREDEFINED_SUBMISSION_TYPE[1], false,
                          "submission_type");
    }

    /**
     * <p>
     * Test updateSubmissionType(SubmissionType submissionType),
     * submissionType should be updated to persistence.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testUpdateSubmissionType() throws Exception {
        assertPersistence("submissionType should be existed.", PREDEFINED_SUBMISSION_TYPE[0], true, "submission_type");
        PREDEFINED_SUBMISSION_TYPE[0].setDescription("new description");
        PREDEFINED_SUBMISSION_TYPE[0].setModificationUser("new user");
        PREDEFINED_SUBMISSION_TYPE[0].setName("new name");
        PREDEFINED_SUBMISSION_TYPE[0].setModificationTimestamp(new Date());
        tester.updateSubmissionType(PREDEFINED_SUBMISSION_TYPE[0]);
        assertPersistence("submissionType should be updated.", PREDEFINED_SUBMISSION_TYPE[0], true, "submission_type");
    }

    /**
     * <p>
     * Test loadSubmissionType(long submissionTypeId),
     * the SubmissionType with the given id should be loaded.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testLoadSubmissionType() throws Exception {
        SubmissionType fact = tester.loadSubmissionType(PREDEFINED_SUBMISSION_TYPE[0].getId());

        assertEquals("Failed to load submissionType", PREDEFINED_SUBMISSION_TYPE[0], fact);
    }

    /**
     * <p>
     * Test getAllSubmissionTypeIds(),
     * all ids of SubmissionType in persistence should be loaded.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testGetAllSubmissionTypeIds() throws Exception {
        long[] ids = tester.getAllSubmissionTypeIds();

        Arrays.sort(ids);
        assertEquals("The returned ids should have 5 length.", 5, ids.length);

        for (int i = 0; i < ids.length; i++) {
            assertEquals("The returned ids is not correct", i + 1, ids[i]);
        }
    }

    /**
     * <p>
     * Test loadSubmissionTypees(long[] submissionTypeIds),
     * the SubmissionType with the given ids should be loaded.
     * </p>
     *
     * @throws Exception Exception to JUnit
     */
    public void testLoadSubmissionTypees() throws Exception {
        SubmissionType[] returned = tester.loadSubmissionTypes(new long[] { 2, 4 });

        assertEquals("Failed to load SubmissionTypees", 2, returned.length);

        for (int i = 0; i < returned.length; i++) {
            assertEquals("Failed to load SubmissionTypees",
                         PREDEFINED_SUBMISSION_STATUS[(int) returned[i].getId() - 1], returned[i]);
        }
    }

    /**
     * <p>
     * Test loadSubmissionTypes(long[] submissionTypeIds),
     * when submissionTypeIds is empty array, empty SubmissionType array should be returned.
     * </p>
     *
     * @throws Exception Exception to JUnit
     */
    public void testLoadSubmissionTypees_Empty() throws Exception {
        assertEquals("Empty SubmissionType array should be returned.", 0,
                     tester.loadSubmissionTypes(new long[0]).length);
    }

    /**
     * <p>
     * Test ctor SqlUploadPersistence(DBConnectionFactory connectionFactory),
     * when connectionFactory is valid, an instance should be created.
     * </p>
     */
    public void testCtor1() {
        UploadPersistence persistence = new SqlUploadPersistence(factory);

        assertNotNull("Failed to create instance of SqlUploadPersistence", persistence);
    }

    /**
     * <p>
     * Test ctor SqlUploadPersistence(DBConnectionFactory connectionFactory, String connectionName)
     * when both args are valid, an instance should be created.
     * </p>
     */
    public void testCtor2() {
        UploadPersistence persistence = new SqlUploadPersistence(factory, CONNECTION_NAME);

        assertNotNull("Failed to create instance of SqlUploadPersistence", persistence);
    }

    /**
     * <p>
     * Test addUploadType(UploadType uploadType),
     * an uploadType should be saved to persistence.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testAddUploadType() throws Exception {
        assertPersistence("uploadType should be not existed.", uploadType, false, "upload_type");
        tester.addUploadType(uploadType);
        assertPersistence("Failed to add upload type.", uploadType, true, "upload_type");
    }

    /**
     * <p>
     * Test removeUploadType(UploadType uploadType),
     * the uploadType in persistence should be removed.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testRemoveUploadType() throws Exception {
        assertPersistence("uploadType should be existed.", PREDEFINED_UPLOADTYPE[3], true, "upload_type");
        tester.removeUploadType(PREDEFINED_UPLOADTYPE[3]);
        assertPersistence("Failed to remove upload type.", PREDEFINED_UPLOADTYPE[3], false, "upload_type");
    }

    /**
     * <p>
     * Test updateUploadType(UploadType uploadType),
     * the uploadType should be updated to persistence.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testUpdateUploadType() throws Exception {
        assertPersistence("uploadType should be existed.", PREDEFINED_UPLOADTYPE[0], true, "upload_type");
        PREDEFINED_UPLOADTYPE[0].setDescription("new description");
        PREDEFINED_UPLOADTYPE[0].setModificationUser("new user");
        PREDEFINED_UPLOADTYPE[0].setName("new name");
        PREDEFINED_UPLOADTYPE[0].setModificationTimestamp(new Date());
        tester.updateUploadType(PREDEFINED_UPLOADTYPE[0]);
        assertPersistence("uploadType should be updated.", PREDEFINED_UPLOADTYPE[0], true, "upload_type");
    }

    /**
     * <p>
     * Test loadUploadType(long uploadTypeId),
     * the uploadType with the given id should be loaded.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testLoadUploadType() throws Exception {
        UploadType fact = tester.loadUploadType(PREDEFINED_UPLOADTYPE[1].getId());

        assertEquals("failed to load upload type.", PREDEFINED_UPLOADTYPE[1], fact);
    }

    /**
     * <p>
     * Test getAllUploadTypeIds(),
     * it should return all ids of upload type in persistence.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testGetAllUploadTypeIds() throws Exception {
        long[] ids = tester.getAllUploadTypeIds();

        Arrays.sort(ids);
        assertEquals("The returned ids should have 4 length.", 4, ids.length);

        for (int i = 0; i < ids.length; i++) {
            assertEquals("The returned ids is not correct", i + 1, ids[i]);
        }
    }

    /**
     * <p>
     * Test addUploadStatus(UploadStatus uploadStatus),
     * an record for uploadStatus should be saved to persistence.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testAddUploadStatus() throws Exception {
        assertPersistence("uploadStatus should be not existed.", uploadStatus, false, "upload_status");
        tester.addUploadStatus(uploadStatus);
        assertPersistence("uploadStatus should be existed.", uploadStatus, true, "upload_status");
    }

    /**
     * <p>
     * Test removeUploadStatus(UploadStatus uploadStatus),
     * the uploadStatus should be removed from persistence.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testRemoveUploadStatus() throws Exception {
        assertPersistence("uploadStatus should be existed.", PREDEFINED_UPLOADSTATUS[1], true, "upload_status");
        tester.removeUploadStatus(PREDEFINED_UPLOADSTATUS[1]);
        assertPersistence("uploadStatus should be NOT existed.", PREDEFINED_UPLOADSTATUS[1], false, "upload_status");
    }

    /**
     * <p>
     * Test updateUploadStatus(UploadStatus uploadStatus),
     * the uploadStatus should be updated to persistence.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testUpdateUploadStatus() throws Exception {
        assertPersistence("uploadStatus should be existed.", PREDEFINED_UPLOADSTATUS[0], true, "upload_status");
        PREDEFINED_UPLOADSTATUS[0].setDescription("new description");
        PREDEFINED_UPLOADSTATUS[0].setModificationUser("new user");
        PREDEFINED_UPLOADSTATUS[0].setName("new name");
        PREDEFINED_UPLOADSTATUS[0].setModificationTimestamp(new Date());
        tester.updateUploadStatus(PREDEFINED_UPLOADSTATUS[0]);
        assertPersistence("uploadStatus should be updated.", PREDEFINED_UPLOADSTATUS[0], true, "upload_status");
    }

    /**
     * <p>
     * Test loadUploadStatus(long uploadStatusId),
     * the UploadStatus with the given id should be loaded.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testLoadUploadStatus() throws Exception {
        UploadStatus fact = tester.loadUploadStatus(PREDEFINED_UPLOADSTATUS[0].getId());

        assertEquals("Failed to load uploadStatus", PREDEFINED_UPLOADSTATUS[0], fact);
    }

    /**
     * <p>
     * Test getAllUploadStatusIds(),
     * all ids of UploadStatus in persistence should be loaded.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testGetAllUploadStatusIds() throws Exception {
        long[] ids = tester.getAllUploadStatusIds();

        Arrays.sort(ids);
        assertEquals("The returned ids should have 2 length.", 2, ids.length);

        for (int i = 0; i < ids.length; i++) {
            assertEquals("The returned ids is not correct", i + 1, ids[i]);
        }
    }

    /**
     * <p>
     * Test addSubmissionStatus(SubmissionStatus submissionStatus),
     * submissionStatus should be saved to persistence.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testAddSubmissionStatus() throws Exception {
        assertPersistence("submissionStatus should be not existed.", submissionStatus, false, "submission_status");
        tester.addSubmissionStatus(submissionStatus);
        assertPersistence("submissionStatus should be existed.", submissionStatus, true, "submission_status");
    }

    /**
     * <p>
     * Test removeSubmissionStatus(SubmissionStatus submissionStatus),
     * submissionStatus should be removed from persistence.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testRemoveSubmissionStatus() throws Exception {
        assertPersistence("submissionStatus should be existed.", PREDEFINED_SUBMISSION_STATUS[1], true,
                          "submission_status");
        tester.removeSubmissionStatus(PREDEFINED_SUBMISSION_STATUS[1]);
        assertPersistence("submissionStatus should be NOT existed.", PREDEFINED_SUBMISSION_STATUS[1], false,
                          "submission_status");
    }

    /**
     * <p>
     * Test updateSubmissionStatus(SubmissionStatus submissionStatus),
     * submissionStatus should be updated to persistence.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testUpdateSubmissionStatus() throws Exception {
        assertPersistence("submissionStatus should be existed.", PREDEFINED_SUBMISSION_STATUS[0], true,
                          "submission_status");
        PREDEFINED_SUBMISSION_STATUS[0].setDescription("new description");
        PREDEFINED_SUBMISSION_STATUS[0].setModificationUser("new user");
        PREDEFINED_SUBMISSION_STATUS[0].setName("new name");
        PREDEFINED_SUBMISSION_STATUS[0].setModificationTimestamp(new Date());
        tester.updateSubmissionStatus(PREDEFINED_SUBMISSION_STATUS[0]);
        assertPersistence("submissionStatus should be updated.", PREDEFINED_SUBMISSION_STATUS[0], true,
                          "submission_status");
    }

    /**
     * <p>
     * Test loadSubmissionStatus(long submissionStatusId),
     * the SubmissionStatus with the given id should be loaded.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testLoadSubmissionStatus() throws Exception {
        UploadStatus fact = tester.loadUploadStatus(PREDEFINED_SUBMISSION_STATUS[0].getId());

        assertEquals("Failed to load submissionStatus", PREDEFINED_SUBMISSION_STATUS[0], fact);
    }

    /**
     * <p>
     * Test getAllSubmissionStatusIds(),
     * all ids of SubmissionStatus in persistence should be loaded.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testGetAllSubmissionStatusIds() throws Exception {
        long[] ids = tester.getAllSubmissionStatusIds();

        Arrays.sort(ids);
        assertEquals("The returned ids should have 5 length.", 5, ids.length);

        for (int i = 0; i < ids.length; i++) {
            assertEquals("The returned ids is not correct", i + 1, ids[i]);
        }
    }

    /**
     * <p>
     * Test addUpload(Upload upload),
     * an instance with upload should be saved to persistence.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testAddUpload() throws Exception {
        tester.addUpload(upload);
        assertPersistence("Failed to add upload", upload, true);
    }

    /**
     * <p>
     * Test removeUpload(Upload upload),
     * the upload should be removed from persistence.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testRemoveUpload() throws Exception {
        assertPersistence("upload should be existed", PREDEFINED_UPLOAD[1], true);
        tester.removeUpload(PREDEFINED_UPLOAD[1]);
        assertPersistence("upload should be NOT existed", PREDEFINED_UPLOAD[1], false);
    }

    /**
     * <p>
     * Test updateUpload(Upload upload),
     * the upload should be updated to persistence.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testUpdateUpload() throws Exception {
        assertPersistence("upload should be existed", PREDEFINED_UPLOAD[0], true);
        PREDEFINED_UPLOAD[0].setModificationTimestamp(new Date());
        PREDEFINED_UPLOAD[0].setModificationUser("tester");
        PREDEFINED_UPLOAD[0].setOwner(2);
        PREDEFINED_UPLOAD[0].setProject(2);
        PREDEFINED_UPLOAD[0].setParameter("new parameter");
        PREDEFINED_UPLOAD[0].setUploadStatus(PREDEFINED_UPLOADSTATUS[1]);
        PREDEFINED_UPLOAD[0].setUploadType(PREDEFINED_UPLOADTYPE[1]);

        tester.updateUpload(PREDEFINED_UPLOAD[0]);
        assertPersistence("failed to update upload.", PREDEFINED_UPLOAD[0], true);
    }

    /**
     * <p>
     * Test loadUpload(long uploadId),
     * the upload with the given id should be loaded.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testLoadUpload() throws Exception {
        Upload fact = tester.loadUpload(PREDEFINED_UPLOAD[0].getId());

        assertEquals("Failed to load upload.", PREDEFINED_UPLOAD[0], fact);
    }

    /**
     * <p>
     * Test addSubmission(Submission submission),
     * the submission should be saved to persistence.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testAddSubmission() throws Exception {
        tester.addSubmission(submission);
        assertPersistence("Failed to add submission", submission, true);
    }

    /**
     * <p>
     * Test removeSubmission(Submission submission),
     * the submission should be removed from persistence.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testRemoveSubmission() throws Exception {
        assertPersistence("submission should be existed.", PREDEFINED_SUBMISSION[0], true);
        tester.removeSubmission(PREDEFINED_SUBMISSION[0]);
        assertPersistence("Failed to remove submission", PREDEFINED_SUBMISSION[0], false);
    }

    /**
     * <p>
     * Test updateSubmission(Submission submission),
     * the submission should be updated to persistence.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testUpdateSubmission() throws Exception {
        assertPersistence("submission should be existed.", PREDEFINED_SUBMISSION[0], true);
        PREDEFINED_SUBMISSION[0].setUpload(PREDEFINED_UPLOAD[1]);
        PREDEFINED_SUBMISSION[0].setModificationTimestamp(new Date());
        PREDEFINED_SUBMISSION[0].setModificationUser("Modifier");
        PREDEFINED_SUBMISSION[0].setSubmissionStatus(PREDEFINED_SUBMISSION_STATUS[0]);
        tester.updateSubmission(PREDEFINED_SUBMISSION[0]);
        assertPersistence("Failed to update submission", PREDEFINED_SUBMISSION[0], true);
    }

    /**
     * <p>
     * Test loadSubmission(long submissionId),
     * the submission should be loaded.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testLoadSubmission() throws Exception {
        Submission fact = tester.loadSubmission(PREDEFINED_SUBMISSION[0].getId());

        assertEquals("Failed to load submission", PREDEFINED_SUBMISSION[0], fact);
    }

    /**
     * <p>
     * Test loadSubmissions(long[] submissionIds),
     * the submission with the given ids should be loaded.
     * </p>
     *
     * @throws Exception Exception to JUnit
     */
    public void testLoadSubmissions() throws Exception {
        Submission[] returned = tester.loadSubmissions(new long[] { 1, 2 });

        assertEquals("Failed to load Submission", 2, returned.length);

        for (int i = 0; i < returned.length; i++) {
            assertEquals("Failed to load Upload", PREDEFINED_SUBMISSION[(int) returned[i].getId() - 1], returned[i]);
        }
    }

    /**
     * <p>
     * Test loadSubmissions(long[] submissionIds),
     * when submissionIds is emtpy, an empty array should be returned.
     * </p>
     *
     * @throws Exception Exception to JUnit
     */
    public void testLoadSubmissions_Empty() throws Exception {
        assertEquals("Empty array should be returned.", 0, tester.loadSubmissions(new long[0]).length);
    }

    /**
     * <p>
     * Test loadUploads(long[] uploadIds),
     * the Upload with the given ids should be loaded.
     * </p>
     *
     * @throws Exception Exception to JUnit
     */
    public void testLoadUploads() throws Exception {
        Upload[] returned = tester.loadUploads(new long[] { 1, 2, 3 });

        assertEquals("Failed to load Upload", 2, returned.length);

        for (int i = 0; i < returned.length; i++) {
            assertEquals("Failed to load Upload", PREDEFINED_UPLOAD[(int) returned[i].getId() - 1], returned[i]);
        }
    }

    /**
     * <p>
     * Test loadUploads(long[] uploadIds),
     * when uploadIds is empty, an empty array should be returned.
     * </p>
     *
     * @throws Exception Exception to JUnit
     */
    public void testLoadUploads_Empty() throws Exception {
        assertEquals("Empty array should be returned.", 0, tester.loadUploads(new long[0]).length);
    }

    /**
     * <p>
     * Test loadUploadTypes(long[] uploadTypeIds),
     * the UploadType with the given ids should be loaded.
     * </p>
     *
     * @throws Exception Exception to JUnit
     */
    public void testLoadUploadTypes() throws Exception {
        UploadType[] returned = tester.loadUploadTypes(new long[] { 1, 2 });

        assertEquals("Failed to load UploadTypes", 2, returned.length);

        for (int i = 0; i < returned.length; i++) {
            assertEquals("Failed to load UploadTypes", PREDEFINED_UPLOADTYPE[(int) returned[i].getId() - 1],
                         returned[i]);
        }
    }

    /**
     * <p>
     * Test loadUploadTypes(long[] uploadTypeIds),
     * when uploadTypeIds is empty, an empty array should be returned.
     * </p>
     *
     * @throws Exception Exception to JUnit
     */
    public void testLoadUploadTypes_Empty() throws Exception {
        assertEquals("Empty array should be returned.", 0, tester.loadUploadTypes(new long[0]).length);
    }

    /**
     * <p>
     * Test loadUploadStatuses(long[] uploadStatusIds),
     * the UploadStatuse with the given ids should be loaded.
     * </p>
     *
     * @throws Exception Exception to JUnit
     */
    public void testLoadUploadStatuses() throws Exception {
        UploadStatus[] returned = tester.loadUploadStatuses(new long[] { 1, 2 });

        assertEquals("Failed to load UploadStatuses", 2, returned.length);

        for (int i = 0; i < returned.length; i++) {
            assertEquals("Failed to load UploadStatuses", PREDEFINED_UPLOADSTATUS[(int) returned[i].getId() - 1],
                         returned[i]);
        }
    }

    /**
     * <p>
     * Test loadUploadStatuses(long[] uploadStatusIds),
     * when uploadStatusIds is empty, an empty array should be returned.
     * </p>
     *
     * @throws Exception Exception to JUnit
     */
    public void testLoadUploadStatuses_Empty() throws Exception {
        assertEquals("Empty array should be returned.", 0, tester.loadUploadStatuses(new long[0]).length);
    }

    /**
     * <p>
     * Test loadSubmissionStatuses(long[] submissionStatusIds),
     * the SubmissionStatus with the given ids should be loaded.
     * </p>
     *
     * @throws Exception Exception to JUnit
     */
    public void testLoadSubmissionStatuses() throws Exception {
        SubmissionStatus[] returned = tester.loadSubmissionStatuses(new long[] { 2, 4 });

        assertEquals("Failed to load SubmissionStatuses", 2, returned.length);

        for (int i = 0; i < returned.length; i++) {
            assertEquals("Failed to load SubmissionStatuses",
                         PREDEFINED_SUBMISSION_STATUS[(int) returned[i].getId() - 1], returned[i]);
        }
    }

    /**
     * <p>
     * Test loadSubmissionStatuses(long[] submissionStatusIds),
     * when submissionStatusIds is empty array, empty SubmissionStatus array should be returned.
     * </p>
     *
     * @throws Exception Exception to JUnit
     */
    public void testLoadSubmissionStatuses_Empty() throws Exception {
        assertEquals("Empty SubmissionStatus array should be returned.", 0,
                     tester.loadSubmissionStatuses(new long[0]).length);
    }

    /**
     * <p>
     * Verify the NamedDeliverableStructure is in database.
     * </p>
     *
     * @param object the structrue to check.
     * @param isExisted the objected should be existed or not.
     */
    private void assertPersistence(String msg, NamedDeliverableStructure object, boolean isExisted, String type)
            throws Exception {
        String sql = "Select name, description, create_user, create_date, modify_user, modify_date " + "from " + type
                     + "_lu where " + type + "_id = " + object.getId();

        Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = connection.createStatement();
            rs = stmt.executeQuery(sql);

            if (!isExisted) {
                assertFalse(msg, rs.next());
            } else {
                rs.next();
                assertEquals(msg, object.getName(), rs.getString("name"));
                assertEquals(msg, object.getDescription(), rs.getString("description"));
                assertEquals(msg, object.getCreationUser(), rs.getString("create_user"));
                assertEquals(msg, object.getModificationUser(), rs.getString("modify_user"));
                assertEquals(msg, object.getCreationTimestamp(), new Date(rs.getTimestamp("create_date").getTime()));
                assertEquals(msg, object.getModificationTimestamp(),
                             new Date(rs.getTimestamp("modify_date").getTime()));
            }
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {}

            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {}
        }
    }

    /**
     * <p>
     * Verify the expected is same as fact.
     * </p>
     *
     * @param msg the message
     * @param expected the expected instance.
     * @param fact the fact instance.
     */
    private void assertEquals(String msg, NamedDeliverableStructure expected, NamedDeliverableStructure fact) {
        assertEquals(msg, expected.getId(), fact.getId());
        assertEquals(msg, expected.getName(), fact.getName());
        assertEquals(msg, expected.getDescription(), fact.getDescription());
        assertEquals(msg, expected.getModificationUser(), fact.getModificationUser());
        assertEquals(msg, expected.getCreationUser(), fact.getCreationUser());

        assertEquals(msg, expected.getModificationTimestamp(), fact.getModificationTimestamp());
        assertEquals(msg, expected.getCreationTimestamp(), fact.getCreationTimestamp());
    }

    /**
     * <p>
     * Verify the upload is in database.
     * </p>
     *
     * @param upload the upload to check.
     * @param isExisted the upload should be existed or not.
     */
    private void assertPersistence(String msg, Upload upload, boolean isExisted) throws Exception {
        String sql = "select project_id, resource_id, upload_type_id, upload_status_id, parameter,"
                     + " create_user, create_date, modify_user, modify_date from upload where upload_id="
                     + upload.getId();

        Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = connection.createStatement();
            rs = stmt.executeQuery(sql);

            if (!isExisted) {
                assertFalse(msg, rs.next());
            } else {
                rs.next();
                assertEquals(msg, upload.getProject(), rs.getLong("project_id"));
                assertEquals(msg, upload.getOwner(), rs.getLong("resource_id"));
                assertEquals(msg, upload.getUploadType().getId(), rs.getLong("upload_type_id"));
                assertEquals(msg, upload.getUploadStatus().getId(), rs.getLong("upload_status_id"));
                assertEquals(msg, upload.getParameter(), rs.getString("parameter"));
                assertEquals(msg, upload.getCreationUser(), rs.getString("create_user"));
                assertEquals(msg, upload.getModificationUser(), rs.getString("modify_user"));
                assertEquals(msg, upload.getCreationTimestamp(), new Date(rs.getTimestamp("create_date").getTime()));
                assertEquals(msg, upload.getModificationTimestamp(),
                             new Date(rs.getTimestamp("modify_date").getTime()));
            }
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {}

            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {}
        }
    }

    /**
     * <p>
     * Verify the Submission is in database.
     * </p>
     *
     * @param submission the submission to check.
     * @param isExisted the submission should be existed or not.
     */
    private void assertPersistence(String msg, Submission submission, boolean isExisted) throws Exception {
        String sql = "select submission_id, upload_id, submission_status_id, create_user, create_date, "
                     + "modify_user, modify_date from submission where submission_id=" + submission.getId();

        Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = connection.createStatement();
            rs = stmt.executeQuery(sql);

            if (!isExisted) {
                assertFalse(msg, rs.next());
            } else {
                rs.next();
                assertEquals(msg, submission.getUpload().getId(), rs.getLong("upload_id"));
                assertEquals(msg, submission.getSubmissionStatus().getId(), rs.getLong("submission_status_id"));
                assertEquals(msg, submission.getCreationUser(), rs.getString("create_user"));
                assertEquals(msg, submission.getModificationUser(), rs.getString("modify_user"));
                assertEquals(msg, submission.getCreationTimestamp(),
                             new Date(rs.getTimestamp("create_date").getTime()));
                assertEquals(msg, submission.getModificationTimestamp(),
                             new Date(rs.getTimestamp("modify_date").getTime()));
            }
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {}

            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {}
        }
    }

    /**
     * <p>
     * Verify the expected is same as fact.
     * </p>
     *
     * @param expected the expected instance.
     * @param fact the fact instance.
     */
    private void assertEquals(String msg, Upload expected, Upload fact) {
        assertEquals(msg, expected.getId(), fact.getId());
        assertEquals(msg, expected.getProject(), fact.getProject());
        assertEquals(msg, expected.getOwner(), fact.getOwner());
        assertEquals(msg, expected.getUploadType().getId(), fact.getUploadType().getId());
        assertEquals(msg, expected.getUploadStatus().getId(), fact.getUploadStatus().getId());
        assertEquals(msg, expected.getParameter(), fact.getParameter());
        assertEquals(msg, expected.getCreationUser(), fact.getCreationUser());
        assertEquals(msg, expected.getModificationUser(), fact.getModificationUser());
        assertEquals(msg, expected.getCreationTimestamp(), fact.getCreationTimestamp());
        assertEquals(msg, expected.getModificationTimestamp(), fact.getModificationTimestamp());
    }

    /**
     * <p>
     * Verify the expected is same as fact.
     * </p>
     *
     * @param expected the expected instance.
     * @param fact the fact instance.
     */
    private void assertEquals(String msg, Submission expected, Submission fact) {
        assertEquals(msg, expected.getUpload().getId(), fact.getUpload().getId());
        assertEquals(msg, expected.getSubmissionStatus().getId(), fact.getSubmissionStatus().getId());
        assertEquals(msg, expected.getCreationUser(), fact.getCreationUser());
        assertEquals(msg, expected.getModificationUser(), fact.getModificationUser());
        assertEquals(msg, expected.getCreationTimestamp(), fact.getCreationTimestamp());
        assertEquals(msg, expected.getModificationTimestamp(), fact.getModificationTimestamp());
    }

    /**
     * <p>
     * Assert Date.
     * </p>
     *
     * @param msg the message.
     * @param expected the expected date.
     * @param fact the fact.
     */
    private void assertEquals(String msg, Date expected, Date fact) {
        assertEquals(msg, expected.getTime(), fact.getTime());
    }

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     *
     * @throws Exception Exception to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();

        AccuracyHelper.clearNamespace();
        AccuracyHelper.loadConfig();
        factory = new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName());
        connection = factory.createConnection();
        AccuracyHelper.clearData(connection);
        AccuracyHelper.prepareData(connection);
        prepareDefined();
        tester = new SqlUploadPersistence(factory);
    }

    /**
     * <p>
     * Clean up the test environment.
     * </p>
     *
     * @throws Exception Exception to JUnit
     */
    protected void tearDown() throws Exception {
        try {
            AccuracyHelper.clearData(connection);
        } catch (SQLException e) {
            // ignore
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                // ignore.
            }
        }

        AccuracyHelper.clearNamespace();
    }

    /**
     * <p>
     * Create predefined objectes.
     * </p>
     */
    private void prepareDefined() {
        {
            Calendar calendar = Calendar.getInstance();

            calendar.set(2006, 06, 18, 0, 0, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            PREDEFINED_CREATE_DATE = calendar.getTime();
            PREDEFINED_SUBMISSION_STATUS = new SubmissionStatus[] { new SubmissionStatus(1, "Active"),
                    new SubmissionStatus(2, "Failed Screening"), new SubmissionStatus(3, "Failed Review"),
                    new SubmissionStatus(4, "Completed Without Win"), new SubmissionStatus(5, "Deleted"), };

            for (int i = 0; i < PREDEFINED_SUBMISSION_STATUS.length; i++) {
                PREDEFINED_SUBMISSION_STATUS[i].setCreationTimestamp(PREDEFINED_CREATE_DATE);
                PREDEFINED_SUBMISSION_STATUS[i].setModificationTimestamp(PREDEFINED_CREATE_DATE);
                PREDEFINED_SUBMISSION_STATUS[i].setCreationUser("System");
                PREDEFINED_SUBMISSION_STATUS[i].setModificationUser("System");
                PREDEFINED_SUBMISSION_STATUS[i].setDescription(SS_DESCRIPTION[i]);
            }

            submissionStatus = new SubmissionStatus(100, "Test");
            submissionStatus.setCreationTimestamp(PREDEFINED_CREATE_DATE);
            submissionStatus.setModificationTimestamp(PREDEFINED_CREATE_DATE);
            submissionStatus.setCreationUser("Tester");
            submissionStatus.setModificationUser("Tester");
            submissionStatus.setDescription("Description");
        }

        {
            Calendar calendar = Calendar.getInstance();

            calendar.set(2006, 06, 18, 0, 0, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            PREDEFINED_CREATE_DATE = calendar.getTime();
            PREDEFINED_SUBMISSION_TYPE = new SubmissionType[] { new SubmissionType(1, "Active"),
                    new SubmissionType(2, "Failed Screening"), new SubmissionType(3, "Failed Review"),
                    new SubmissionType(4, "Completed Without Win"), new SubmissionType(5, "Deleted"), };

            for (int i = 0; i < PREDEFINED_SUBMISSION_TYPE.length; i++) {
                PREDEFINED_SUBMISSION_TYPE[i].setCreationTimestamp(PREDEFINED_CREATE_DATE);
                PREDEFINED_SUBMISSION_TYPE[i].setModificationTimestamp(PREDEFINED_CREATE_DATE);
                PREDEFINED_SUBMISSION_TYPE[i].setCreationUser("System");
                PREDEFINED_SUBMISSION_TYPE[i].setModificationUser("System");
                PREDEFINED_SUBMISSION_TYPE[i].setDescription(SS_DESCRIPTION[i]);
            }

            submissionType = new SubmissionType(100, "Test");
            submissionType.setCreationTimestamp(PREDEFINED_CREATE_DATE);
            submissionType.setModificationTimestamp(PREDEFINED_CREATE_DATE);
            submissionType.setCreationUser("Tester");
            submissionType.setModificationUser("Tester");
            submissionType.setDescription("Description");
        }

        {
            PREDEFINED_UPLOADTYPE = new UploadType[] { new UploadType(1, "Submission"), new UploadType(2, "Test Case"),
                    new UploadType(3, "Final Fix"), new UploadType(4, "Review Document") };

            for (int i = 0; i < PREDEFINED_UPLOADTYPE.length; i++) {
                PREDEFINED_UPLOADTYPE[i].setCreationTimestamp(PREDEFINED_CREATE_DATE);
                PREDEFINED_UPLOADTYPE[i].setModificationTimestamp(PREDEFINED_CREATE_DATE);
                PREDEFINED_UPLOADTYPE[i].setCreationUser("System");
                PREDEFINED_UPLOADTYPE[i].setModificationUser("System");
                PREDEFINED_UPLOADTYPE[i].setDescription(UT_DESCRIPTION[i]);
            }

            uploadType = new UploadType(100, "Test");
            uploadType.setCreationTimestamp(PREDEFINED_CREATE_DATE);
            uploadType.setModificationTimestamp(PREDEFINED_CREATE_DATE);
            uploadType.setCreationUser("Tester");
            uploadType.setModificationUser("Tester");
            uploadType.setDescription("Description");
        }

        {
            PREDEFINED_UPLOADSTATUS = new UploadStatus[] { new UploadStatus(1, "Active"),
                    new UploadStatus(2, "Deleted") };

            for (int i = 0; i < PREDEFINED_UPLOADSTATUS.length; i++) {
                PREDEFINED_UPLOADSTATUS[i].setCreationTimestamp(PREDEFINED_CREATE_DATE);
                PREDEFINED_UPLOADSTATUS[i].setModificationTimestamp(PREDEFINED_CREATE_DATE);
                PREDEFINED_UPLOADSTATUS[i].setCreationUser("System");
                PREDEFINED_UPLOADSTATUS[i].setModificationUser("System");
                PREDEFINED_UPLOADSTATUS[i].setDescription(US_DESCRIPTION[i]);
            }

            uploadStatus = new UploadStatus(100, "Test");
            uploadStatus.setCreationTimestamp(PREDEFINED_CREATE_DATE);
            uploadStatus.setModificationTimestamp(PREDEFINED_CREATE_DATE);
            uploadStatus.setCreationUser("Tester");
            uploadStatus.setModificationUser("Tester");
            uploadStatus.setDescription("Description");
        }

        {
            PREDEFINED_UPLOAD = new Upload[] { new Upload(1), new Upload(2), };

            for (int i = 0; i < PREDEFINED_UPLOAD.length; i++) {
                PREDEFINED_UPLOAD[i].setProject(i + 1);
                PREDEFINED_UPLOAD[i].setOwner(i + 1);
                PREDEFINED_UPLOAD[i].setParameter("parameter");
                PREDEFINED_UPLOAD[i].setCreationTimestamp(PREDEFINED_CREATE_DATE);
                PREDEFINED_UPLOAD[i].setModificationTimestamp(PREDEFINED_CREATE_DATE);
                PREDEFINED_UPLOAD[i].setCreationUser("System");
                PREDEFINED_UPLOAD[i].setModificationUser("System");
                PREDEFINED_UPLOAD[i].setUploadStatus(PREDEFINED_UPLOADSTATUS[0]);
                PREDEFINED_UPLOAD[i].setUploadType(PREDEFINED_UPLOADTYPE[0]);
            }

            upload = new Upload(100);
            upload.setProject(1);
            upload.setOwner(1);
            upload.setParameter("Tester");
            upload.setCreationTimestamp(PREDEFINED_CREATE_DATE);
            upload.setModificationTimestamp(PREDEFINED_CREATE_DATE);
            upload.setCreationUser("Tester");
            upload.setModificationUser("Tester");
            upload.setUploadStatus(PREDEFINED_UPLOADSTATUS[0]);
            upload.setUploadType(PREDEFINED_UPLOADTYPE[0]);
        }

        {
            PREDEFINED_SUBMISSION = new Submission[] { new Submission(1), new Submission(2), };

            for (int i = 0; i < PREDEFINED_SUBMISSION.length; i++) {
                PREDEFINED_SUBMISSION[i].setSubmissionStatus(PREDEFINED_SUBMISSION_STATUS[0]);
                PREDEFINED_SUBMISSION[i].setSubmissionType(PREDEFINED_SUBMISSION_TYPE[0]);
                PREDEFINED_SUBMISSION[i].setUpload(PREDEFINED_UPLOAD[0]);
                PREDEFINED_SUBMISSION[i].setCreationTimestamp(PREDEFINED_CREATE_DATE);
                PREDEFINED_SUBMISSION[i].setModificationTimestamp(PREDEFINED_CREATE_DATE);
                PREDEFINED_SUBMISSION[i].setCreationUser("System");
                PREDEFINED_SUBMISSION[i].setModificationUser("System");
            }

            submission = new Submission(100);
            submission.setSubmissionStatus(PREDEFINED_SUBMISSION_STATUS[0]);
            submission.setSubmissionType(PREDEFINED_SUBMISSION_TYPE[0]);
            submission.setUpload(PREDEFINED_UPLOAD[0]);
            submission.setCreationTimestamp(PREDEFINED_CREATE_DATE);
            submission.setModificationTimestamp(PREDEFINED_CREATE_DATE);
            submission.setCreationUser("Tester");
            submission.setModificationUser("Tester");
        }
    }
}
