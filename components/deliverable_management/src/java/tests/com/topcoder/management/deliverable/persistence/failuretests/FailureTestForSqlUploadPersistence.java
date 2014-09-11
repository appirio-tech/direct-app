/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable.persistence.failuretests;

import java.util.Date;

import junit.framework.TestCase;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.management.deliverable.Submission;
import com.topcoder.management.deliverable.SubmissionStatus;
import com.topcoder.management.deliverable.SubmissionType;
import com.topcoder.management.deliverable.Upload;
import com.topcoder.management.deliverable.UploadStatus;
import com.topcoder.management.deliverable.UploadType;
import com.topcoder.management.deliverable.persistence.UploadPersistenceException;
import com.topcoder.management.deliverable.persistence.sql.SqlUploadPersistence;
import com.topcoder.util.config.ConfigManager;


/**
 * Failure test cases for class <code>SqlUploadPersistence</code>.
 *
 * @author Chenhong, onsky
 * @version 1.1
 * @since 1.0
 */
public class FailureTestForSqlUploadPersistence extends TestCase {
    /** Represents the SqlUploadPersistence instance for test. */
    private SqlUploadPersistence persistence = null;

    /**
     * Set up the environment.
     *
     * @throws Exception to junit.
     */
    public void setUp() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        String namespace = "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl";

        if (cm.existsNamespace(namespace)) {
            cm.removeNamespace(namespace);
        }

        cm.add("failuretests/dbfactory.xml");

        DBConnectionFactory factory = new DBConnectionFactoryImpl(namespace);

        persistence = new SqlUploadPersistence(factory);
    }

    /**
     * Tear down the environment.
     *
     * @throws Exception to junit.
     */
    public void tearDown() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        String namespace = "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl";

        if (cm.existsNamespace(namespace)) {
            cm.removeNamespace(namespace);
        }
    }

    /**
     * Test SqlUploadPersistence(DBConnectionFactory). If parameter connectionFactory is null,
     * IllegalArgumentException should be thrown.
     */
    public void testSqlUploadPersistenceDBConnectionFactory() {
        try {
            new SqlUploadPersistence(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test SqlUploadPersistence(DBConnectionFactory, String). If parameter connectionFactory is null,
     * IllegalArgumentException should be thrown.
     */
    public void testSqlUploadPersistenceDBConnectionFactoryString() {
        try {
            new SqlUploadPersistence(null, "name");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>void addUploadType(UploadType uploadType) </code>.
     *
     * @throws Exception to junit.
     */
    public void testAddUploadType_1() throws Exception {
        for (int i = 0; i < 7; i++) {
            UploadType type = new UploadType();

            // create invalid UploadType instance with level.
            type = (UploadType) Util.getNamedDeliverableStructure(type, i);

            try {
                persistence.addUploadType(type);
                fail("IllegalArgumentException should be thrown.");
            } catch (IllegalArgumentException e) {
                // ok.
            }
        }
    }

    /**
     * Test method <code>void addUploadType(UploadType uploadType) </code>.
     *
     * @throws Exception to junit.
     */
    public void testAddUploadType_2() throws Exception {
        try {
            persistence.addUploadType(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code> void addUploadStatus(UploadStatus uploadStatus) </code>.
     *
     * @throws Exception to junit.
     */
    public void testAddUploadStatus_1() throws Exception {
        try {
            persistence.addUploadStatus(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code> void addUploadStatus(UploadStatus uploadStatus) </code>.
     *
     * @throws Exception to junit.
     */
    public void testAddUploadStatus_2() throws Exception {
        for (int i = 0; i < 7; i++) {
            UploadStatus status = new UploadStatus();
            status = (UploadStatus) Util.getNamedDeliverableStructure(status, i);

            try {
                persistence.addUploadStatus(status);
                fail("IllegalArgumentException should be thrown.");
            } catch (IllegalArgumentException e) {
                // ok.
            }
        }
    }

    /**
     * Test method <code>void addSubmissionStatus(SubmissionStatus submissionStatus) </code>.
     *
     * @throws Exception to junit.
     */
    public void testAddSubmissionStatus() throws Exception {
        try {
            persistence.addSubmissionStatus(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>void addSubmissionStatus(SubmissionStatus submissionStatus) </code>.
     *
     * @throws Exception to junit.
     */
    public void testAddSubmissionStatus_2() throws Exception {
        for (int i = 0; i < 7; i++) {
            SubmissionStatus status = new SubmissionStatus();

            status = (SubmissionStatus) Util.getNamedDeliverableStructure(status, i);

            try {
                persistence.addSubmissionStatus(status);
                fail("IllegalArgumentException should be thrown.");
            } catch (IllegalArgumentException e) {
                // ok.
            }
        }
    }

    /**
     * Test method <code>void removeUploadType(UploadType uploadType) </code>.
     *
     * @throws Exception to junit.
     */
    public void testRemoveUploadType() throws Exception {
        for (int i = -1; i < 1; i++) {
            UploadType type = new UploadType();

            type = (UploadType) Util.getNamedDeliverableStructure(type, i);

            try {
                persistence.removeUploadType(type);
                fail("IllegalArgumentException should be thrown.");
            } catch (IllegalArgumentException e) {
                // ok.
            }
        }
    }

    /**
     * Test method <code>void removeUploadStatus(UploadStatus uploadStatus) </code>.
     *
     * @throws Exception to junit.
     */
    public void testRemoveUploadStatus() throws Exception {
        for (int i = -1; i < 1; i++) {
            UploadStatus status = new UploadStatus();

            status = (UploadStatus) Util.getNamedDeliverableStructure(status, i);

            try {
                persistence.removeUploadStatus(status);
                fail("IllegalArgumentException should be thrown.");
            } catch (IllegalArgumentException e) {
                // ok.
            }
        }
    }

    /**
     * Test method <code>void removeSubmissionStatus(SubmissionStatus submissionStatus) </code>.
     *
     * @throws Exception to junit.
     */
    public void testRemoveSubmissionStatus() throws Exception {
        for (int i = -1; i < 1; i++) {
            SubmissionStatus status = new SubmissionStatus();
            status = (SubmissionStatus) Util.getNamedDeliverableStructure(status, i);

            try {
                persistence.removeSubmissionStatus(status);
                fail("IllegalArgumentException should be thrown.");
            } catch (IllegalArgumentException e) {
                // ok.
            }
        }
    }

    /**
     * Test method <code>void removeUpload(Upload upload) </code>.
     *
     * @throws Exception to junit.
     */
    public void testRemoveUpload_1() throws Exception {
        try {
            persistence.removeUpload(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>void removeUpload(Upload upload) </code>.
     *
     * @throws Exception to junit.
     */
    public void testRemoveUpload_2() throws Exception {
        try {
            persistence.removeUpload(new Upload());
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>void removeSubmission(Submission submission) </code>.
     *
     * @throws Exception to junit.
     */
    public void testRemoveSubmission_1() throws Exception {
        try {
            persistence.removeSubmission(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>void removeSubmission(Submission submission) </code>.
     *
     * @throws Exception to junit.
     */
    public void testRemoveSubmission_2() throws Exception {
        try {
            persistence.removeSubmission(new Submission());
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>void updateUploadType(UploadType uploadType)  </code>.
     *
     * @throws Exception to junit.
     */
    public void testUpdateUploadType_1() throws Exception {
        for (int i = -1; i < 7; i++) {
            UploadType type = new UploadType();
            type = (UploadType) Util.getNamedDeliverableStructure(type, i);

            try {
                persistence.updateUploadType(type);
                fail("IllegalArgumentException should be thrown.");
            } catch (IllegalArgumentException e) {
                // ok.
            }
        }
    }

    /**
     * Test method <code>void updateUploadStatus(UploadStatus uploadStatus) </code>.
     *
     * @throws Exception to junit.
     */
    public void testUpdateUploadStatus_1() throws Exception {
        for (int i = -1; i < 7; i++) {
            UploadStatus status = new UploadStatus();
            status = (UploadStatus) Util.getNamedDeliverableStructure(status, i);

            try {
                persistence.updateUploadStatus(status);
                fail("IllegalArgumentException should be thrown.");
            } catch (IllegalArgumentException e) {
                // ok.
            }
        }
    }

    /**
     * Test method <code>void updateSubmissionStatus(SubmissionStatus submissionStatus) </code>.
     *
     * @throws Exception to junit.
     */
    public void testUpdateSubmissionStatus() throws Exception {
        for (int i = -1; i < 7; i++) {
            SubmissionStatus status = new SubmissionStatus();
            status = (SubmissionStatus) Util.getNamedDeliverableStructure(status, i);

            try {
                persistence.updateSubmissionStatus(status);
                fail("IllegalArgumentException should be thrown.");
            } catch (IllegalArgumentException e) {
                // ok.
            }
        }
    }

    /**
     * Test method <code>UploadType loadUploadType(long uploadTypeId) </code>.
     *
     * @throws Exception to junit.
     */
    public void testLoadUploadType_1() throws Exception {
        try {
            persistence.loadUploadType(0);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>UploadType loadUploadType(long uploadTypeId) </code>.
     *
     * @throws Exception to junit.
     */
    public void testLoadUploadType_2() throws Exception {
        try {
            persistence.loadUploadType(-1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>loadUploadStatus(long uploadStatusId) </code>.
     *
     * @throws Exception to junit.
     */
    public void testLoadUploadStatus_1() throws Exception {
        try {
            persistence.loadUploadStatus(0);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>loadUploadStatus(long uploadStatusId) </code>.
     *
     * @throws Exception to junit.
     */
    public void testLoadUploadStatus_2() throws Exception {
        try {
            persistence.loadUploadStatus(-1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>loadSubmissionStatus(long submissionStatusId) </code>.
     *
     * @throws Exception to junit.
     */
    public void testLoadSubmissionStatus() throws Exception {
        for (int i = -1; i <= 0; i++) {
            try {
                persistence.loadSubmission(i);
                fail("IllegalArgumentException should be thrown.");
            } catch (IllegalArgumentException e) {
                // ok.
            }
        }
    }

    /**
     * Test method <code>UploadType[] loadUploadTypes(long[] uploadTypeIds) </code>.
     *
     * @throws Exception to junit.
     */
    public void testLoadUploadTypes_1() throws Exception {
        try {
            persistence.loadUploadTypes(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>UploadType[] loadUploadTypes(long[] uploadTypeIds) </code>.
     *
     * @throws Exception to junit.
     */
    public void testLoadUploadTypes_2() throws Exception {
        try {
            persistence.loadUploadTypes(new long[] { 1, -2, 0 });
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>UploadStatus[] loadUploadStatuses(long[] uploadStatusIds) </code>.
     *
     * @throws Exception to junit.
     */
    public void testLoadUploadStatuses_1() throws Exception {
        try {
            persistence.loadUploadStatuses(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>UploadStatus[] loadUploadStatuses(long[] uploadStatusIds) </code>.
     *
     * @throws Exception to junit.
     */
    public void testLoadUploadStatuses_2() throws Exception {
        try {
            persistence.loadUploadStatuses(new long[] { 1, 2, -1, 0, -5 });
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>SubmissionStatus[] loadSubmissionStatuses(long[] submissionStatusIds) </code>.
     *
     * @throws Exception to junit.
     */
    public void testLoadSubmissionStatuses_1() throws Exception {
        try {
            persistence.loadSubmissionStatuses(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>SubmissionStatus[] loadSubmissionStatuses(long[] submissionStatusIds) </code>.
     *
     * @throws Exception to junit.
     */
    public void testLoadSubmissionStatuses_2() throws Exception {
        try {
            persistence.loadSubmissionStatuses(new long[] { 0, -1, 1, 2, 3 });
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>void addUpload(Upload upload) </code>.
     *
     * @throws Exception to junit.
     */
    public void testAddUpload_1() throws Exception {
        try {
            persistence.addUpload(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>void addUpload(Upload upload) </code>.
     *
     * @throws Exception to junit.
     */
    public void testAddUpload_2() throws Exception {
        Upload u = new Upload();

        try {
            persistence.addUpload(u);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>void addUpload(Upload upload) </code>.
     *
     * @throws Exception to junit.
     */
    public void testAddUpload_3() throws Exception {
        Upload u = new Upload();
        u.setId(1);

        try {
            persistence.addUpload(u);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>void addUpload(Upload upload) </code>.
     *
     * @throws Exception to junit.
     */
    public void testAddUpload_4() throws Exception {
        Upload u = new Upload();
        u.setId(1);
        u.setOwner(1);

        try {
            persistence.addUpload(u);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>void addUpload(Upload upload) </code>.
     *
     * @throws Exception to junit.
     */
    public void testAddUpload_5() throws Exception {
        Upload u = new Upload();
        u.setId(1);
        u.setOwner(1);
        u.setProject(10);

        try {
            persistence.addUpload(u);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>void addUpload(Upload upload) </code>.
     *
     * @throws Exception to junit.
     */
    public void testAddUpload_6() throws Exception {
        Upload u = new Upload();
        u.setId(1);
        u.setOwner(1);
        u.setProject(10);
        u.setUploadStatus(new UploadStatus());

        try {
            persistence.addUpload(u);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>void addUpload(Upload upload) </code>.
     *
     * @throws Exception to junit.
     */
    public void testAddUpload_7() throws Exception {
        Upload u = new Upload();
        u.setId(1);
        u.setOwner(1);
        u.setProject(10);
        u.setUploadStatus(new UploadStatus());
        u.setUploadType(new UploadType());

        try {
            persistence.addUpload(u);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>void addUpload(Upload upload) </code>.
     *
     * @throws Exception to junit.
     */
    public void testAddUpload_8() throws Exception {
        Upload u = new Upload();
        u.setId(1);
        u.setOwner(1);
        u.setProject(10);
        u.setUploadStatus(new UploadStatus());
        u.setUploadType(new UploadType());
        u.setCreationTimestamp(new Date());
        u.setCreationUser("user");
        u.setModificationTimestamp(new Date());
        u.setModificationUser("m");

        try {
            persistence.addUpload(u);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>void addUpload(Upload upload) </code>.
     *
     * @throws Exception to junit.
     */
    public void testAddUpload_9() throws Exception {
        Upload u = new Upload();
        u.setId(1);
        u.setOwner(1);
        u.setProject(10);
        u.setUploadStatus((UploadStatus) Util.getNamedDeliverableStructure(new UploadStatus(), 10));
        u.setUploadType(new UploadType());
        u.setCreationTimestamp(new Date());
        u.setCreationUser("user");
        u.setModificationTimestamp(new Date());
        u.setModificationUser("m");

        try {
            persistence.addUpload(u);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>void addUpload(Upload upload) </code>.
     *
     * @throws Exception to junit.
     */
    public void testAddUpload_10() throws Exception {
        Upload u = new Upload();
        u.setId(1);
        u.setOwner(1);
        u.setProject(10);
        u.setUploadStatus((UploadStatus) Util.getNamedDeliverableStructure(new UploadStatus(), 10));
        u.setUploadType((UploadType) Util.getNamedDeliverableStructure(new UploadType(), 10));
        u.setCreationTimestamp(new Date());
        u.setCreationUser("user");
        u.setModificationTimestamp(new Date());
        u.setModificationUser("m");

        try {
            persistence.addUpload(u);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>void addUpload(Upload upload) </code>.
     *
     * @throws Exception to junit.
     */
    public void testAddUpload_11() throws Exception {
        Upload u = new Upload();
        u.setId(1);
        u.setOwner(1);
        u.setProject(10);
        u.setUploadStatus((UploadStatus) Util.getNamedDeliverableStructure(new UploadStatus(), 10));
        u.setUploadType((UploadType) Util.getNamedDeliverableStructure(new UploadType(), 10));
        u.setCreationTimestamp(new Date());
        u.setCreationUser("user");
        u.setModificationTimestamp(new Date());
        u.setModificationUser("m");
        u.setParameter("parameter");

        // all upload properties are set, but missing reference key in the database.
        try {
            persistence.addUpload(u);
            fail("UploadPersistenceException should be thrown.");
        } catch (UploadPersistenceException e) {
            // ok.
        }
    }

    /**
     * Test method <code>void updateUpload(Upload upload) </code>.
     *
     * @throws Exception to junit.
     */
    public void testUpdateUpload_1() throws Exception {
        try {
            persistence.updateUpload(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code> void updateSubmission(Submission submission) </code>.
     *
     * @throws Exception to junit.
     */
    public void testAddSubmission_1() throws Exception {
        try {
            persistence.addSubmission(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code> void updateSubmission(Submission submission) </code>.
     *
     * @throws Exception to junit.
     */
    public void testAddSubmission_2() throws Exception {
        Submission s = new Submission();

        try {
            persistence.addSubmission(s);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code> void updateSubmission(Submission submission) </code>.
     *
     * @throws Exception to junit.
     */
    public void testAddSubmission_3() throws Exception {
        Submission s = new Submission();
        s.setId(10);

        try {
            persistence.addSubmission(s);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code> void updateSubmission(Submission submission) </code>.
     *
     * @throws Exception to junit.
     */
    public void testAddSubmission_4() throws Exception {
        Submission s = new Submission();
        s.setId(10);
        s.setSubmissionStatus((SubmissionStatus) Util.getNamedDeliverableStructure(new SubmissionStatus(), 10));

        try {
            persistence.addSubmission(s);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code> void updateSubmission(Submission submission) </code>.
     *
     * @throws Exception to junit.
     */
    public void testAddSubmission_5() throws Exception {
        Submission s = new Submission();
        s.setId(10);
        s.setSubmissionStatus((SubmissionStatus) Util.getNamedDeliverableStructure(new SubmissionStatus(), 10));

        s.setUpload(new Upload());

        try {
            persistence.addSubmission(s);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code> void updateSubmission(Submission submission) </code>.
     *
     * @throws Exception to junit.
     */
    public void testAddSubmission_6() throws Exception {
        Submission s = new Submission();
        s.setId(10);
        s.setSubmissionStatus((SubmissionStatus) Util.getNamedDeliverableStructure(new SubmissionStatus(), 10));

        s.setCreationTimestamp(new Date());
        s.setCreationUser("user");
        s.setModificationTimestamp(new Date());
        s.setModificationUser("m");
        s.setUpload(new Upload());

        // The upload is not valid for persist since the upload_id is not set.
        try {
            persistence.addSubmission(s);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Failure test <code> void updateSubmission(Submission submission) </code>.
     *
     * @throws Exception to junit.
     */
    public void testAddSubmission_7() throws Exception {
        Upload u = new Upload();
        u.setId(1);
        u.setOwner(1);
        u.setProject(10);
        u.setUploadStatus((UploadStatus) Util.getNamedDeliverableStructure(new UploadStatus(), 10));
        u.setUploadType((UploadType) Util.getNamedDeliverableStructure(new UploadType(), 10));
        u.setCreationTimestamp(new Date());
        u.setCreationUser("user");
        u.setModificationTimestamp(new Date());
        u.setModificationUser("m");
        u.setParameter("parameter");

        Submission s = new Submission();
        s.setId(10);
        s.setSubmissionStatus((SubmissionStatus) Util.getNamedDeliverableStructure(new SubmissionStatus(), 10));

        s.setCreationTimestamp(new Date());
        s.setCreationUser("user");
        s.setModificationTimestamp(new Date());
        s.setModificationUser("m");
        s.setUpload(u);

        try {
            persistence.addSubmission(s);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code> void updateSubmission(Submission submission) </code>.
     *
     * @throws Exception to junit.
     */
    public void testUpdateSubmission_1() throws Exception {
        try {
            persistence.updateSubmission(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code> void updateSubmission(Submission submission) </code>.
     *
     * @throws Exception to junit.
     */
    public void testUpdateSubmission_2() throws Exception {
        Upload u = new Upload();
        u.setId(1);
        u.setOwner(1);
        u.setProject(10);
        u.setUploadStatus((UploadStatus) Util.getNamedDeliverableStructure(new UploadStatus(), 10));
        u.setUploadType((UploadType) Util.getNamedDeliverableStructure(new UploadType(), 10));
        u.setCreationTimestamp(new Date());
        u.setCreationUser("user");
        u.setModificationTimestamp(new Date());
        u.setModificationUser("m");
        u.setParameter("parameter");

        Submission s = new Submission();
        s.setId(10);
        s.setSubmissionStatus((SubmissionStatus) Util.getNamedDeliverableStructure(new SubmissionStatus(), 10));

        s.setCreationTimestamp(new Date());
        s.setCreationUser("user");
        s.setModificationTimestamp(new Date());
        s.setModificationUser("m");
        s.setUpload(u);

        try {
            persistence.updateSubmission(s);
            fail("IAE expected");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>Upload loadUpload(long uploadId) </code>.
     *
     * @throws Exception to junit.
     */
    public void testLoadUpload() throws Exception {
        for (int i = -1; i <= 0; i++) {
            try {
                persistence.loadUpload(i);
                fail("IllegalArgumentException should be thrown.");
            } catch (IllegalArgumentException e) {
                // ok.
            }
        }
    }

    /**
     * Test method <code>Upload[] loadUploads(long[] uploadIds) </code>.
     *
     * @throws Exception to junit.
     */
    public void testLoadUploads_1() throws Exception {
        try {
            persistence.loadUploads((long[]) null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>Upload[] loadUploads(long[] uploadIds) </code>.
     *
     * @throws Exception to junit.
     */
    public void testLoadUploads_2() throws Exception {
        try {
            persistence.loadUploads(new long[] { -1, 1 });
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>Submission loadSubmission(long submissionId) </code>.
     *
     * @throws Exception to junit.
     */
    public void testLoadSubmission() throws Exception {
        for (int i = -2; i <= 0; i++) {
            try {
                persistence.loadSubmission(i);
                fail("IllegalArgumentException should be thrown.");
            } catch (IllegalArgumentException e) {
                // ok.
            }
        }
    }

    /**
     * Test method <code>Submission[] loadSubmissions(long[] submissionIds) </code>.
     *
     * @throws Exception to junit.
     */
    public void testLoadSubmissions_1() throws Exception {
        try {
            persistence.loadSubmissions((long[]) null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>Submission[] loadSubmissions(long[] submissionIds) </code>.
     *
     * @throws Exception to junit.
     */
    public void testLoadSubmissions_2() throws Exception {
        try {
            persistence.loadSubmissions(new long[] { 1, -1, 0 });
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>addSubmissionType(SubmissionType) </code>.
     *
     * @throws Exception to junit.
     */
    public void testaddSubmissionType_1() throws Exception {
        try {
            persistence.addSubmissionType(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>addSubmissionType(SubmissionType) </code>.
     *
     * @throws Exception to junit.
     */
    public void testaddSubmissionType_2() throws Exception {
        try {
            persistence.addSubmissionType(new SubmissionType());
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>removeSubmissionType(SubmissionType) </code>.
     *
     * @throws Exception to junit.
     */
    public void testremoveSubmissionType_1() throws Exception {
        try {
            persistence.removeSubmissionType(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>removeSubmissionType(SubmissionType) </code>.
     *
     * @throws Exception to junit.
     */
    public void testremoveSubmissionType_2() throws Exception {
        try {
            persistence.removeSubmissionType(new SubmissionType());
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>updateSubmissionType(SubmissionType) </code>.
     *
     * @throws Exception to junit.
     */
    public void testupdateSubmissionType_1() throws Exception {
        try {
            persistence.updateSubmissionType(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>updateSubmissionType(SubmissionType) </code>.
     *
     * @throws Exception to junit.
     */
    public void testupdateSubmissionType_2() throws Exception {
        try {
            persistence.updateSubmissionType(new SubmissionType());
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>loadSubmissionType(long) </code>.
     *
     * @throws Exception to junit.
     */
    public void testloadSubmissionType_1() throws Exception {
        try {
            persistence.loadSubmissionType(-1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>loadSubmissionType(long) </code>.
     *
     * @throws Exception to junit.
     */
    public void testloadSubmissionType_2() throws Exception {
        try {
            persistence.loadSubmissionType(0);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>loadSubmissionTypes(long[]) </code>.
     *
     * @throws Exception to junit.
     */
    public void testloadSubmissionTypes_1() throws Exception {
        try {
            persistence.loadSubmissionTypes(new long[]{-1});
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>loadSubmissionTypes(long[]) </code>.
     *
     * @throws Exception to junit.
     */
    public void testloadSubmissionTypes_2() throws Exception {
        try {
            persistence.loadSubmissionTypes(new long[]{0});
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }

    /**
     * Test method <code>loadSubmissionTypes(long[]) </code>.
     *
     * @throws Exception to junit.
     */
    public void testloadSubmissionTypes_3() throws Exception {
        try {
            persistence.loadSubmissionTypes(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok.
        }
    }
}
