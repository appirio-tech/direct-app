/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable.accuracytests;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.topcoder.management.deliverable.DeliverableTestHelper;
import com.topcoder.management.deliverable.PersistenceUploadManager;
import com.topcoder.management.deliverable.Submission;
import com.topcoder.management.deliverable.SubmissionStatus;
import com.topcoder.management.deliverable.SubmissionType;
import com.topcoder.management.deliverable.Upload;
import com.topcoder.management.deliverable.UploadStatus;
import com.topcoder.management.deliverable.UploadType;
import com.topcoder.management.deliverable.accuracytests.persistence.MockUploadPersistence;
import com.topcoder.management.deliverable.search.SubmissionFilterBuilder;
import com.topcoder.management.deliverable.search.UploadFilterBuilder;
import com.topcoder.search.builder.SearchBundle;
import com.topcoder.search.builder.SearchBundleManager;
import com.topcoder.util.idgenerator.IDGenerator;
import com.topcoder.util.idgenerator.IDGeneratorFactory;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogFactory;

/**
 * <p>
 * Accuracy test cases for <code>PersistenceUploadManager</code>.
 * </p>
 *
 * @author skatou
 * @version 1.0
 */
public class PersistenceUploadManagerAccuracyTests extends AccuracyTestsHelper {
    /** The path of the configuration file. */
    private static final String CONFIG = "accuracytests/PersistenceUploadManagerConfig.xml";

    /** File contains sql statement to initial database for upload search. */
    private static final String INIT_DB_SQL = "test_files/accuracytests/InitDB.sql";

    /** File contains sql statement to clear database for upload search. */
    private static final String CLEAR_DB_SQL = "test_files/accuracytests/ClearDB.sql";

    /** Represents the configuration namespace for search builder. */
    private static final String SEARCH_BUILDER_NAMESPACE = "com.topcoder.searchbuilder.DeliverableManager";

    /** The <code>SearchBundleManager</code> instance used in the tests. */
    private SearchBundleManager searchBundleManager = null;

    /** The <code>PersistenceDeliverableManager</code> instance to be tested. */
    private PersistenceUploadManager manager = null;

    private static final Log logger = LogFactory.getLog(PersistenceUploadManagerAccuracyTests.class.getName());

    /**
     * Sets up the test environment. Configurations are loaded and new instances of
     * <code>PersistenceUploadManager</code> and <code>SearchBundleManager</code> are created.
     *
     * @throws Exception
     *             pass to JUnit.
     */
    protected void setUp() throws Exception {
        unloadConfig();
        loadConfig(CONFIG);
        DeliverableTestHelper.executeBatch(CLEAR_DB_SQL);
        DeliverableTestHelper.executeBatch(INIT_DB_SQL);

        searchBundleManager = new SearchBundleManager(SEARCH_BUILDER_NAMESPACE);

        manager = new PersistenceUploadManager(new MockUploadPersistence(), searchBundleManager);

    }

    /**
     * Tear down the test environment.
     *
     * @throws Exception
     *             pass to JUnit.
     */
    protected void tearDown() throws Exception {
        DeliverableTestHelper.executeBatch(CLEAR_DB_SQL);
    }

    /**
     * Verifies public fields are set correctly.
     */
    public void testPublicFields() {
        assertEquals("UPLOAD_SEARCH_BUNDLE_NAME not set correctly", "Upload Search Bundle",
                PersistenceUploadManager.UPLOAD_SEARCH_BUNDLE_NAME);
        assertEquals("SUBMISSION_SEARCH_BUNDLE_NAME not set correctly", "Submission Search Bundle",
                PersistenceUploadManager.SUBMISSION_SEARCH_BUNDLE_NAME);
        assertEquals("UPLOAD_ID_GENERATOR_NAME not set correctly", "upload_id_seq",
                PersistenceUploadManager.UPLOAD_ID_GENERATOR_NAME);
        assertEquals("UPLOAD_TYPE_ID_GENERATOR_NAME not set correctly", "upload_type_id_seq",
                PersistenceUploadManager.UPLOAD_TYPE_ID_GENERATOR_NAME);
        assertEquals("UPLOAD_STATUS_ID_GENERATOR_NAME not set correctly", "upload_status_id_seq",
                PersistenceUploadManager.UPLOAD_STATUS_ID_GENERATOR_NAME);
        assertEquals("SUBMISSION_ID_GENERATOR_NAME not set correctly", "submission_id_seq",
                PersistenceUploadManager.SUBMISSION_ID_GENERATOR_NAME);
        assertEquals("SUBMISSION_STATUS_ID_GENERATOR_NAME not set correctly", "submission_status_id_seq",
                PersistenceUploadManager.SUBMISSION_STATUS_ID_GENERATOR_NAME);
        assertEquals("SUBMISSION_TYPE_ID_GENERATOR_NAME not set correctly", "submission_type_id_seq",
                PersistenceUploadManager.SUBMISSION_TYPE_ID_GENERATOR_NAME);
    }

    /**
     * Tests constructor with search bundles and id generator factory.
     *
     * @throws Exception
     *             pass to JUnit.
     */
    public void testConstructorWithSearchBundlesAndIdGenerators() throws Exception {
        SearchBundle uploadSearchBundle = searchBundleManager
                .getSearchBundle(PersistenceUploadManager.UPLOAD_SEARCH_BUNDLE_NAME);
        SearchBundle submissionSearchBundle = searchBundleManager
                .getSearchBundle(PersistenceUploadManager.SUBMISSION_SEARCH_BUNDLE_NAME);
        IDGenerator uploadIdGenerator = IDGeneratorFactory
                .getIDGenerator(PersistenceUploadManager.UPLOAD_ID_GENERATOR_NAME);
        IDGenerator uploadTypeIdGenerator = IDGeneratorFactory
                .getIDGenerator(PersistenceUploadManager.UPLOAD_TYPE_ID_GENERATOR_NAME);
        IDGenerator uploadStatusIdGenerator = IDGeneratorFactory
                .getIDGenerator(PersistenceUploadManager.UPLOAD_STATUS_ID_GENERATOR_NAME);
        IDGenerator submissionIdGenerator = IDGeneratorFactory
                .getIDGenerator(PersistenceUploadManager.SUBMISSION_ID_GENERATOR_NAME);
        IDGenerator submissionStatusIdGenerator = IDGeneratorFactory
                .getIDGenerator(PersistenceUploadManager.SUBMISSION_STATUS_ID_GENERATOR_NAME);

        IDGenerator submissionTypeIdGenerator = IDGeneratorFactory
                .getIDGenerator(PersistenceUploadManager.SUBMISSION_TYPE_ID_GENERATOR_NAME);
        manager = new PersistenceUploadManager(new MockUploadPersistence(), uploadSearchBundle, submissionSearchBundle,
                uploadIdGenerator, uploadTypeIdGenerator, uploadStatusIdGenerator, submissionIdGenerator,
                submissionStatusIdGenerator, submissionTypeIdGenerator);
        assertNotNull("Unable to instantiate PersistenceUploadManager", manager);
    }

    /**
     * Tests constructor with search bundle manager.
     *
     * @throws Exception
     *             pass to JUnit.
     */
    public void testConstructorWithSearchBundleManagerAndIdGeneratorFactory() throws Exception {
        manager = new PersistenceUploadManager(new MockUploadPersistence(), searchBundleManager);
        assertNotNull("Unable to instantiate PersistenceUploadManager", manager);
    }

    /**
     * Tests createUploadType method.
     *
     * @throws Exception
     *             pass to JUnit.
     */
    public void testCreateUploadType() throws Exception {
        UploadType uploadType = new UploadType();
        uploadType.setName("name");
        uploadType.setDescription("uploadType");
        manager.createUploadType(uploadType, "user");
        assertNotNull("The created object is null", uploadType);
        assertTrue("Id not set", uploadType.getId() != UploadType.UNSET_ID);
        assertEquals("Name changed", "name", uploadType.getName());
        assertEquals("Description changed", "uploadType", uploadType.getDescription());
        assertEquals("CreationUser not set correctly", "user", uploadType.getCreationUser());
        assertNotNull("CreationTimestamp not set correctly", uploadType.getCreationTimestamp());
        assertEquals("ModificationUser not set correctly", "user", uploadType.getModificationUser());
        assertNotNull("ModificationTimestamp not set correctly", uploadType.getModificationTimestamp());
        assertTrue("Invalid UploadType persisted", uploadType.isValidToPersist());
        assertEquals("Persistence implementation not used correctly", "addUploadType" + uploadType,
                MockUploadPersistence.getLastCalled());
    }

    /**
     * Tests updateUploadType method.
     *
     * @throws Exception
     *             pass to JUnit.
     */
    public void testUpdateUploadType() throws Exception {
        UploadType uploadType = new UploadType();
        uploadType.setName("name");
        uploadType.setDescription("uploadType");
        manager.createUploadType(uploadType, "user");

        long id = uploadType.getId();
        Date timestamp = uploadType.getModificationTimestamp();
        manager.updateUploadType(uploadType, "administrator");
        assertEquals("Id should not change", id, uploadType.getId());
        assertEquals("ModificationUser not updated", "administrator", uploadType.getModificationUser());
        assertTrue("ModificationTimestamp not updated", uploadType.getModificationTimestamp() != timestamp);
        assertTrue("Invalid UploadType persisted", uploadType.isValidToPersist());
        assertEquals("Persistence implementation not used correctly", "updateUploadType" + uploadType,
                MockUploadPersistence.getLastCalled());
    }

    /**
     * Tests removeUploadType method.
     *
     * @throws Exception
     *             pass to JUnit.
     */
    public void testRemoveUploadType() throws Exception {
        UploadType uploadType = new UploadType();
        uploadType.setName("name");
        uploadType.setDescription("uploadType");
        manager.createUploadType(uploadType, "user");

        long id = uploadType.getId();
        Date timestamp = uploadType.getModificationTimestamp();
        manager.removeUploadType(uploadType, "modification");
        assertEquals("Id should not change", id, uploadType.getId());
        assertEquals("ModificationUser not updated", "modification", uploadType.getModificationUser());
        assertTrue("ModificationTimestamp not updated", uploadType.getModificationTimestamp() != timestamp);
        assertTrue("Invalid UploadType persisted", uploadType.isValidToPersist());
        assertEquals("Persistence implementation not used correctly", "removeUploadType" + uploadType,
                MockUploadPersistence.getLastCalled());
    }

    /**
     * Tests getAllUploadTypes method.
     *
     * @throws Exception
     *             pass to JUnit.
     */
    public void testGetAllUploadTypes() throws Exception {
        manager.getAllUploadTypes();
        assertTrue("Persistence implementation not used correctly", MockUploadPersistence.getLastCalled().startsWith(
                "loadUploadTypes"));
    }

    /**
     * Tests createUploadStatus method.
     *
     * @throws Exception
     *             pass to JUnit.
     */
    public void testCreateUploadStatus() throws Exception {
        UploadStatus uploadStatus = new UploadStatus();
        uploadStatus.setName("name");
        uploadStatus.setDescription("description");
        manager.createUploadStatus(uploadStatus, "user");
        assertNotNull("The created object is null", uploadStatus);
        assertTrue("Id not set", uploadStatus.getId() != UploadStatus.UNSET_ID);
        assertEquals("Name changed", "name", uploadStatus.getName());
        assertEquals("Description changed", "description", uploadStatus.getDescription());
        assertEquals("CreationUser not set correctly", "user", uploadStatus.getCreationUser());
        assertNotNull("CreationTimestamp not set correctly", uploadStatus.getCreationTimestamp());
        assertEquals("ModificationUser not set correctly", "user", uploadStatus.getModificationUser());
        assertNotNull("ModificationTimestamp not set correctly", uploadStatus.getModificationTimestamp());
        assertTrue("Invalid UploadType persisted", uploadStatus.isValidToPersist());
        assertEquals("Persistence implementation not used correctly", "addUploadStatus" + uploadStatus,
                MockUploadPersistence.getLastCalled());
    }

    /**
     * Tests updateUploadStatus method.
     *
     * @throws Exception
     *             pass to JUnit.
     */
    public void testUpdateUploadStatus() throws Exception {
        UploadStatus uploadStatus = new UploadStatus();
        uploadStatus.setName("name");
        uploadStatus.setDescription("description");
        manager.createUploadStatus(uploadStatus, "user");

        long id = uploadStatus.getId();
        Date timestamp = uploadStatus.getModificationTimestamp();
        manager.updateUploadStatus(uploadStatus, "administrator");
        assertEquals("Id should not change", id, uploadStatus.getId());
        assertEquals("ModificationUser not updated", "administrator", uploadStatus.getModificationUser());
        assertTrue("ModificationTimestamp not updated", uploadStatus.getModificationTimestamp() != timestamp);
        assertTrue("Invalid UploadType persisted", uploadStatus.isValidToPersist());
        assertEquals("Persistence implementation not used correctly", "updateUploadStatus" + uploadStatus,
                MockUploadPersistence.getLastCalled());
    }

    /**
     * Tests removeUploadStatus method.
     *
     * @throws Exception
     *             pass to JUnit.
     */
    public void testRemoveUploadStatus() throws Exception {
        UploadStatus uploadStatus = new UploadStatus();
        uploadStatus.setName("name");
        uploadStatus.setDescription("description");
        manager.createUploadStatus(uploadStatus, "user");

        long id = uploadStatus.getId();
        Date timestamp = uploadStatus.getModificationTimestamp();
        manager.removeUploadStatus(uploadStatus, "modification");
        assertEquals("Id should not change", id, uploadStatus.getId());
        assertEquals("ModificationUser not updated", "modification", uploadStatus.getModificationUser());
        assertTrue("ModificationTimestamp not updated", uploadStatus.getModificationTimestamp() != timestamp);
        assertTrue("Invalid UploadType persisted", uploadStatus.isValidToPersist());
        assertEquals("Persistence implementation not used correctly", "removeUploadStatus" + uploadStatus,
                MockUploadPersistence.getLastCalled());
    }

    /**
     * Tests getAllUploadStatuses method.
     *
     * @throws Exception
     *             pass to JUnit.
     */
    public void testGetAllUploadStatuses() throws Exception {
        manager.getAllUploadStatuses();
        assertTrue("Persistence implementation not used correctly", MockUploadPersistence.getLastCalled().startsWith(
                "loadUploadStatuses"));
    }

    /**
     * Tests createSubmissionStatus method.
     *
     * @throws Exception
     *             pass to JUnit.
     */
    public void testCreateSubmissionStatus() throws Exception {
        SubmissionStatus status = new SubmissionStatus();
        status.setName("name");
        status.setDescription("description");
        manager.createSubmissionStatus(status, "user");
        assertNotNull("The created object is null", status);
        assertTrue("Id not set", status.getId() != SubmissionStatus.UNSET_ID);
        assertEquals("Name changed", "name", status.getName());
        assertEquals("Description changed", "description", status.getDescription());
        assertEquals("CreationUser not set correctly", "user", status.getCreationUser());
        assertNotNull("CreationTimestamp not set correctly", status.getCreationTimestamp());
        assertEquals("ModificationUser not set correctly", "user", status.getModificationUser());
        assertNotNull("ModificationTimestamp not set correctly", status.getModificationTimestamp());
        assertTrue("Invalid UploadType persisted", status.isValidToPersist());
        assertEquals("Persistence implementation not used correctly", "addSubmissionStatus" + status,
                MockUploadPersistence.getLastCalled());
    }

    /**
     * Tests updateSubmissionStatus method.
     *
     * @throws Exception
     *             pass to JUnit.
     */
    public void testUpdateSubmissionStatus() throws Exception {
        SubmissionStatus status = new SubmissionStatus();
        status.setName("name");
        status.setDescription("description");
        manager.createSubmissionStatus(status, "user");

        long id = status.getId();
        Date timestamp = status.getModificationTimestamp();
        manager.updateSubmissionStatus(status, "administrator");
        assertEquals("Id should not change", id, status.getId());
        assertEquals("ModificationUser not updated", "administrator", status.getModificationUser());
        assertTrue("ModificationTimestamp not updated", status.getModificationTimestamp() != timestamp);
        assertTrue("Invalid UploadType persisted", status.isValidToPersist());
        assertEquals("Persistence implementation not used correctly", "updateSubmissionStatus" + status,
                MockUploadPersistence.getLastCalled());
    }

    /**
     * Tests removeSubmissionStatus method.
     *
     * @throws Exception
     *             pass to JUnit.
     */
    public void testRemoveSubmissionStatus() throws Exception {
        SubmissionStatus status = new SubmissionStatus();
        status.setName("name");
        status.setDescription("description");
        manager.createSubmissionStatus(status, "user");

        long id = status.getId();
        Date timestamp = status.getModificationTimestamp();
        manager.removeSubmissionStatus(status, "modification");
        assertEquals("Id should not change", id, status.getId());
        assertEquals("ModificationUser not updated", "modification", status.getModificationUser());
        assertTrue("ModificationTimestamp not updated", status.getModificationTimestamp() != timestamp);
        assertTrue("Invalid UploadType persisted", status.isValidToPersist());
        assertEquals("Persistence implementation not used correctly", "removeSubmissionStatus" + status,
                MockUploadPersistence.getLastCalled());
    }

    /**
     * Tests getAllSubmissionStatuses method.
     *
     * @throws Exception
     *             pass to JUnit.
     */
    public void testGetAllSubmissionStatuses() throws Exception {
        manager.getAllSubmissionStatuses();
        assertTrue("Persistence implementation not used correctly", MockUploadPersistence.getLastCalled().startsWith(
                "loadSubmissionStatuses"));
    }

    /**
     * Tests createSubmissionType method.
     *
     * @throws Exception
     *             pass to JUnit.
     * @since 1.1
     */
    public void testCreateSubmissionType() throws Exception {
        SubmissionType type = new SubmissionType();
        type.setName("name");
        type.setDescription("description");
        manager.createSubmissionType(type, "user");
        assertNotNull("The created object is null", type);
        assertTrue("Id not set", type.getId() != SubmissionStatus.UNSET_ID);
        assertEquals("Name changed", "name", type.getName());
        assertEquals("Description changed", "description", type.getDescription());
        assertEquals("CreationUser not set correctly", "user", type.getCreationUser());
        assertNotNull("CreationTimestamp not set correctly", type.getCreationTimestamp());
        assertEquals("ModificationUser not set correctly", "user", type.getModificationUser());
        assertNotNull("ModificationTimestamp not set correctly", type.getModificationTimestamp());
        assertTrue("Invalid UploadType persisted", type.isValidToPersist());
        assertEquals("Persistence implementation not used correctly", "addSubmissionType" + type,
                MockUploadPersistence.getLastCalled());
    }

    /**
     * Tests updateSubmissionType method.
     *
     * @throws Exception
     *             pass to JUnit.
     * @since 1.1
     */
    public void testUpdateSubmissionType() throws Exception {
        SubmissionType type = new SubmissionType();
        type.setName("name");
        type.setDescription("description");
        manager.createSubmissionType(type, "user");

        long id = type.getId();
        Date timestamp = type.getModificationTimestamp();
        manager.updateSubmissionType(type, "administrator");
        assertEquals("Id should not change", id, type.getId());
        assertEquals("ModificationUser not updated", "administrator", type.getModificationUser());
        assertTrue("ModificationTimestamp not updated", type.getModificationTimestamp() != timestamp);
        assertTrue("Invalid UploadType persisted", type.isValidToPersist());
        assertEquals("Persistence implementation not used correctly", "updateSubmissionType" + type,
                MockUploadPersistence.getLastCalled());
    }

    /**
     * Tests removeSubmissionType method.
     *
     * @throws Exception
     *             pass to JUnit.
     * @since 1.1
     */
    public void testRemoveSubmissionType() throws Exception {
        SubmissionType type = new SubmissionType();
        type.setName("name");
        type.setDescription("description");
        manager.createSubmissionType(type, "user");

        long id = type.getId();
        Date timestamp = type.getModificationTimestamp();
        manager.removeSubmissionType(type, "modification");
        assertEquals("Id should not change", id, type.getId());
        assertEquals("ModificationUser not updated", "modification", type.getModificationUser());
        assertTrue("ModificationTimestamp not updated", type.getModificationTimestamp() != timestamp);
        assertTrue("Invalid UploadType persisted", type.isValidToPersist());
        assertEquals("Persistence implementation not used correctly", "removeSubmissionType" + type,
                MockUploadPersistence.getLastCalled());
    }

    /**
     * Tests getAllSubmissionTypes method.
     *
     * @throws Exception
     *             pass to JUnit.
     * @since 1.1
     */
    public void testGetAllSubmissionTypes() throws Exception {
        manager.getAllSubmissionTypes();
        assertTrue("Persistence implementation not used correctly", MockUploadPersistence.getLastCalled().startsWith(
                "loadSubmissionTypes"));
    }

    /**
     * Tests createUpload method.
     *
     * @throws Exception
     *             pass to JUnit.
     */
    public void testCreateUpload() throws Exception {
        Upload upload = createUpload(16, 32, "parameter");
        manager.createUpload(upload, "user");

        assertNotNull("The created object is null", upload);
        assertTrue("Id not set", upload.getId() != Upload.UNSET_ID);
        assertEquals("Owner changed", 16, upload.getOwner());
        assertEquals("Project changed", 32, upload.getProject());
        assertEquals("Parameter changed", "parameter", upload.getParameter());
        assertEquals("CreationUser not set correctly", "user", upload.getCreationUser());
        assertNotNull("CreationTimestamp not set correctly", upload.getCreationTimestamp());
        assertEquals("ModificationUser not set correctly", "user", upload.getModificationUser());
        assertNotNull("ModificationTimestamp not set correctly", upload.getModificationTimestamp());
        assertTrue("Invalid UploadType persisted", upload.isValidToPersist());
        assertEquals("Persistence implementation not used correctly", "addUpload" + upload, MockUploadPersistence
                .getLastCalled());
    }

    /**
     * Tests updateUpload method.
     *
     * @throws Exception
     *             pass to JUnit.
     */
    public void testUpdateUpload() throws Exception {
        Upload upload = createUpload(16, 32, "parameter");
        manager.createUpload(upload, "user");

        long id = upload.getId();
        Date timestamp = upload.getModificationTimestamp();
        manager.updateUpload(upload, "administrator");
        assertEquals("Id should not change", id, upload.getId());
        assertEquals("ModificationUser not updated", "administrator", upload.getModificationUser());
        assertTrue("ModificationTimestamp not updated", upload.getModificationTimestamp() != timestamp);
        assertTrue("Invalid UploadType persisted", upload.isValidToPersist());
        assertEquals("Persistence implementation not used correctly", "updateUpload" + upload, MockUploadPersistence
                .getLastCalled());
    }

    /**
     * Tests removeUpload method.
     *
     * @throws Exception
     *             pass to JUnit.
     */
    public void testRemoveUpload() throws Exception {
        Upload upload = createUpload(16, 32, "parameter");
        manager.createUpload(upload, "user");

        long id = upload.getId();
        Date timestamp = upload.getModificationTimestamp();
        manager.removeUpload(upload, "modification");
        assertEquals("Id should not change", id, upload.getId());
        assertEquals("ModificationUser not updated", "modification", upload.getModificationUser());
        assertTrue("ModificationTimestamp not updated", upload.getModificationTimestamp() != timestamp);
        assertTrue("Invalid UploadType persisted", upload.isValidToPersist());
        assertEquals("Persistence implementation not used correctly", "removeUpload" + upload, MockUploadPersistence
                .getLastCalled());
    }

    /**
     * Tests getUpload method.
     *
     * @throws Exception
     *             pass to JUnit.
     */
    public void testGetUpload() throws Exception {
        long id = 1024;
        manager.getUpload(id);
        assertEquals("Persistence implementation not used correctly", "loadUpload" + id, MockUploadPersistence
                .getLastCalled());
    }

    /**
     * Tests createSubmission method.
     *
     * @throws Exception
     *             pass to JUnit.
     */
    public void testCreateSubmission() throws Exception {
        Submission submission = createSubmission();
        manager.createSubmission(submission, "user");

        assertNotNull("The created object is null", submission);
        assertTrue("Id not set", submission.getId() != Upload.UNSET_ID);
        assertEquals("CreationUser not set correctly", "user", submission.getCreationUser());
        assertNotNull("CreationTimestamp not set correctly", submission.getCreationTimestamp());
        assertEquals("ModificationUser not set correctly", "user", submission.getModificationUser());
        assertNotNull("ModificationTimestamp not set correctly", submission.getModificationTimestamp());
        assertTrue("Invalid UploadType persisted", submission.isValidToPersist());
        assertEquals("Persistence implementation not used correctly", "addSubmission" + submission,
                MockUploadPersistence.getLastCalled());
    }

    /**
     * Tests updateSubmission method.
     *
     * @throws Exception
     *             pass to JUnit.
     */
    public void testUpdateSubmission() throws Exception {
        Submission submission = createSubmission();
        manager.createSubmission(submission, "user");

        long id = submission.getId();
        Date timestamp = submission.getModificationTimestamp();
        manager.updateSubmission(submission, "administrator");
        assertEquals("Id should not change", id, submission.getId());
        assertEquals("ModificationUser not updated", "administrator", submission.getModificationUser());
        assertTrue("ModificationTimestamp not updated", submission.getModificationTimestamp() != timestamp);
        assertTrue("Invalid UploadType persisted", submission.isValidToPersist());
        assertEquals("Persistence implementation not used correctly", "updateSubmission" + submission,
                MockUploadPersistence.getLastCalled());
    }

    /**
     * Tests removeSubmission method.
     *
     * @throws Exception
     *             pass to JUnit.
     */
    public void testRemoveSubmission() throws Exception {
        Submission submission = createSubmission();
        manager.createSubmission(submission, "user");

        long id = submission.getId();
        Date timestamp = submission.getModificationTimestamp();
        manager.removeSubmission(submission, "modification");
        assertEquals("Id should not change", id, submission.getId());
        assertEquals("ModificationUser not updated", "modification", submission.getModificationUser());
        assertTrue("ModificationTimestamp not updated", submission.getModificationTimestamp() != timestamp);
        assertTrue("Invalid UploadType persisted", submission.isValidToPersist());
        assertEquals("Persistence implementation not used correctly", "removeSubmission" + submission,
                MockUploadPersistence.getLastCalled());
    }

    /**
     * Tests getSubmission method.
     *
     * @throws Exception
     *             pass to JUnit.
     */
    public void testGetSubmission() throws Exception {
        long id = 64;
        manager.getSubmission(id);
        assertEquals("Persistence implementation not used correctly", "loadSubmission" + id, MockUploadPersistence
                .getLastCalled());
    }

    /**
     * Tests searchUploads method with upload id filter.
     *
     * @throws Exception
     *             pass to JUnit.
     */
    public void testSearchUploadsByUploadId() throws Exception {
        try {
            executeBatch(CLEAR_DB_SQL);
            executeBatch(INIT_DB_SQL);

            for (int i = 1; i <= 5; ++i) {
                Upload[] uploads = manager.searchUploads(UploadFilterBuilder.createUploadIdFilter(i));

                logger.log(Level.INFO, "Number Of Uploads Retrieved: " + uploads.length);
                assertEquals("Wrong number of uploads retrieved", 1, uploads.length);
                assertEquals("Wrong upload retrieved", i, uploads[0].getId());
            }

            assertTrue("Persistence method not called correctly", MockUploadPersistence.getLastCalled().startsWith(
                    "loadUploads"));
        } finally {
            executeBatch(CLEAR_DB_SQL);
        }
    }

    /**
     * Tests searchUploads method with upload type id filter.
     *
     * @throws Exception
     *             pass to JUnit.
     */
    public void testSearchUploadsByUploadTypeId() throws Exception {
        try {
            executeBatch(CLEAR_DB_SQL);
            executeBatch(INIT_DB_SQL);

            Upload[] uploads = manager.searchUploads(UploadFilterBuilder.createUploadTypeIdFilter(3));
            assertEquals("Wrong number of uploads retrieved", 2, uploads.length);

            Set ids = new HashSet();

            for (int i = 0; i < uploads.length; ++i) {
                ids.add(new Long(uploads[i].getId()));
            }

            assertTrue("Wrong upload retrieved", ids.contains(new Long(2)));
            assertTrue("Wrong upload retrieved", ids.contains(new Long(3)));

            assertTrue("Persistence method not called correctly", MockUploadPersistence.getLastCalled().startsWith(
                    "loadUploads"));
        } finally {
            executeBatch(CLEAR_DB_SQL);
        }
    }

    /**
     * Tests searchUploads method with upload status id filter.
     *
     * @throws Exception
     *             pass to JUnit.
     */
    public void testSearchUploadsByUploadStatusId() throws Exception {
        try {
            executeBatch(CLEAR_DB_SQL);
            executeBatch(INIT_DB_SQL);

            Upload[] uploads = manager.searchUploads(UploadFilterBuilder.createUploadStatusIdFilter(1));
            assertEquals("Wrong number of uploads retrieved", 3, uploads.length);

            Set ids = new HashSet();

            for (int i = 0; i < uploads.length; ++i) {
                ids.add(new Long(uploads[i].getId()));
            }

            assertTrue("Wrong upload retrieved", ids.contains(new Long(1)));
            assertTrue("Wrong upload retrieved", ids.contains(new Long(3)));
            assertTrue("Wrong upload retrieved", ids.contains(new Long(4)));

            assertTrue("Persistence method not called correctly", MockUploadPersistence.getLastCalled().startsWith(
                    "loadUploads"));
        } finally {
            executeBatch(CLEAR_DB_SQL);
        }
    }

    /**
     * Tests searchUploads method with project id filter.
     *
     * @throws Exception
     *             pass to JUnit.
     */
    public void testSearchUploadsByProjectId() throws Exception {
        try {
            executeBatch(CLEAR_DB_SQL);
            executeBatch(INIT_DB_SQL);

            Upload[] uploads = manager.searchUploads(UploadFilterBuilder.createProjectIdFilter(1));
            assertEquals("Wrong number of uploads retrieved", 2, uploads.length);

            Set ids = new HashSet();

            for (int i = 0; i < uploads.length; ++i) {
                ids.add(new Long(uploads[i].getId()));
            }

            assertTrue("Wrong upload retrieved", ids.contains(new Long(1)));
            assertTrue("Wrong upload retrieved", ids.contains(new Long(4)));

            assertTrue("Persistence method not called correctly", MockUploadPersistence.getLastCalled().startsWith(
                    "loadUploads"));
        } finally {
            executeBatch(CLEAR_DB_SQL);
        }
    }

    /**
     * Tests searchUploads method with resource id filter.
     *
     * @throws Exception
     *             pass to JUnit.
     */
    public void testSearchUploadsByResourceId() throws Exception {
        try {
            executeBatch(CLEAR_DB_SQL);
            executeBatch(INIT_DB_SQL);

            Upload[] uploads = manager.searchUploads(UploadFilterBuilder.createResourceIdFilter(2));
            assertEquals("Wrong number of uploads retrieved", 2, uploads.length);

            Set ids = new HashSet();

            for (int i = 0; i < uploads.length; ++i) {
                ids.add(new Long(uploads[i].getId()));
            }

            assertTrue("Wrong upload retrieved", ids.contains(new Long(2)));
            assertTrue("Wrong upload retrieved", ids.contains(new Long(5)));

            assertTrue("Persistence method not called correctly", MockUploadPersistence.getLastCalled().startsWith(
                    "loadUploads"));
        } finally {
            executeBatch(CLEAR_DB_SQL);
        }
    }

    /**
     * Tests searchSubmissions method with submission id filter.
     *
     * @throws Exception
     *             pass to JUnit.
     */
    public void testSearchSubmissionsBySubmissionId() throws Exception {
        try {
            executeBatch(CLEAR_DB_SQL);
            executeBatch(INIT_DB_SQL);

            for (int i = 1; i <= 5; ++i) {
                Submission[] submissions = manager.searchSubmissions(SubmissionFilterBuilder
                        .createSubmissionIdFilter(i));
                assertEquals("Wrong number of submissions retrieved", 1, submissions.length);
                assertEquals("Wrong submission retrieved", i, submissions[0].getId());
            }

            assertTrue("Persistence method not called correctly", MockUploadPersistence.getLastCalled().startsWith(
                    "loadSubmissions"));
        } finally {
            executeBatch(CLEAR_DB_SQL);
        }
    }

    /**
     * Tests searchSubmissions method with upload id filter.
     *
     * @throws Exception
     *             pass to JUnit.
     */
    public void testSearchSubmissionsByUploadId() throws Exception {
        try {
            executeBatch(CLEAR_DB_SQL);
            executeBatch(INIT_DB_SQL);

            Submission[] submissions = manager.searchSubmissions(SubmissionFilterBuilder.createUploadIdFilter(1));
            assertEquals("Wrong number of submissions retrieved", 2, submissions.length);

            Set ids = new HashSet();

            for (int i = 0; i < submissions.length; ++i) {
                ids.add(new Long(submissions[i].getId()));
            }

            assertTrue("Wrong submission retrieved", ids.contains(new Long(1)));
            assertTrue("Wrong submission retrieved", ids.contains(new Long(4)));

            assertTrue("Persistence method not called correctly", MockUploadPersistence.getLastCalled().startsWith(
                    "loadSubmissions"));
        } finally {
            executeBatch(CLEAR_DB_SQL);
        }
    }

    /**
     * Tests searchSubmissions method with submission status id filter.
     *
     * @throws Exception
     *             pass to JUnit.
     */
    public void testSearchSubmissionsBySubmissionStatusId() throws Exception {
        try {
            executeBatch(CLEAR_DB_SQL);
            executeBatch(INIT_DB_SQL);

            Submission[] submissions = manager.searchSubmissions(SubmissionFilterBuilder
                    .createSubmissionStatusIdFilter(4));
            assertEquals("Wrong number of submissions retrieved", 3, submissions.length);

            Set ids = new HashSet();

            for (int i = 0; i < submissions.length; ++i) {
                ids.add(new Long(submissions[i].getId()));
            }

            assertTrue("Wrong submission retrieved", ids.contains(new Long(1)));
            assertTrue("Wrong submission retrieved", ids.contains(new Long(4)));
            assertTrue("Wrong submission retrieved", ids.contains(new Long(5)));

            assertTrue("Persistence method not called correctly", MockUploadPersistence.getLastCalled().startsWith(
                    "loadSubmissions"));
        } finally {
            executeBatch(CLEAR_DB_SQL);
        }
    }

    /**
     * Tests searchSubmissions method with project id filter.
     *
     * @throws Exception
     *             pass to JUnit.
     */
    public void testSearchSubmissionsByProjectId() throws Exception {
        try {
            executeBatch(CLEAR_DB_SQL);
            executeBatch(INIT_DB_SQL);

            Submission[] submissions = manager.searchSubmissions(SubmissionFilterBuilder.createProjectIdFilter(1));
            assertEquals("Wrong number of submissions retrieved", 2, submissions.length);

            Set ids = new HashSet();

            for (int i = 0; i < submissions.length; ++i) {
                ids.add(new Long(submissions[i].getId()));
            }

            assertTrue("Wrong submission retrieved", ids.contains(new Long(1)));
            assertTrue("Wrong submission retrieved", ids.contains(new Long(4)));

            assertTrue("Persistence method not called correctly", MockUploadPersistence.getLastCalled().startsWith(
                    "loadSubmissions"));
        } finally {
            executeBatch(CLEAR_DB_SQL);
        }
    }

    /**
     * Tests searchSubmissions method with resource id filter.
     *
     * @throws Exception
     *             pass to JUnit.
     */
    public void testSearchSubmissionsByResourceId() throws Exception {
        try {
            executeBatch(CLEAR_DB_SQL);
            executeBatch(INIT_DB_SQL);

            Submission[] submissions = manager.searchSubmissions(SubmissionFilterBuilder.createResourceIdFilter(2));
            assertEquals("Wrong number of submissions retrieved", 2, submissions.length);

            Set ids = new HashSet();

            for (int i = 0; i < submissions.length; ++i) {
                ids.add(new Long(submissions[i].getId()));
            }

            assertTrue("Wrong submission retrieved", ids.contains(new Long(2)));
            assertTrue("Wrong submission retrieved", ids.contains(new Long(3)));

            assertTrue("Persistence method not called correctly", MockUploadPersistence.getLastCalled().startsWith(
                    "loadSubmissions"));
        } finally {
            executeBatch(CLEAR_DB_SQL);
        }
    }

    /**
     * Creates a new instance of Upload using the given parameters. The instance is ready to be persisted.
     *
     * @param owner
     *            the owner of the upload.
     * @param project
     *            the project of the upload.
     * @param parameter
     *            the parameter of the upload.
     * @return the upload created.
     * @throws Exception
     *             pass to JUnit.
     */
    private Upload createUpload(int owner, int project, String parameter) throws Exception {
        UploadType uploadType = new UploadType();
        uploadType.setName("upload type name");
        uploadType.setDescription("upload type description");
        manager.createUploadType(uploadType, "someone");

        UploadStatus uploadStatus = new UploadStatus();
        uploadStatus.setName("upload status name");
        uploadStatus.setDescription("upload status description");
        manager.createUploadStatus(uploadStatus, "someone");

        Upload upload = new Upload();
        upload.setUploadType(uploadType);
        upload.setUploadStatus(uploadStatus);
        upload.setOwner(owner);
        upload.setProject(project);
        upload.setParameter(parameter);

        return upload;
    }

    /**
     * Creates a new instance of Submission using the given parameters. The instance is ready to be persisted.
     *
     * @return the submission created.
     * @throws Exception
     *             pass to JUnit.
     */
    private Submission createSubmission() throws Exception {
        Upload upload = createUpload(16, 32, "parameter");
        manager.createUpload(upload, "someone");

        SubmissionStatus status = new SubmissionStatus();
        status.setName("submission status name");
        status.setDescription("submission status description");
        manager.createSubmissionStatus(status, "someone");

        SubmissionType type = new SubmissionType();
        type.setName("submission type name");
        type.setDescription("submission type description");
        manager.createSubmissionType(type, "someone");

        Submission submission = new Submission();
        submission.setUpload(upload);
        submission.setSubmissionStatus(status);
        submission.setSubmissionType(type);

        return submission;
    }
}
