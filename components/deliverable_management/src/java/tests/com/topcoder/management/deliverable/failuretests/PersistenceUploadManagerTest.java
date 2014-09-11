/*
 * Copyright (C) 2006-2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable.failuretests;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import junit.framework.TestCase;

import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.management.deliverable.DeliverableTestHelper;
import com.topcoder.management.deliverable.PersistenceUploadManager;
import com.topcoder.management.deliverable.Submission;
import com.topcoder.management.deliverable.SubmissionStatus;
import com.topcoder.management.deliverable.SubmissionType;
import com.topcoder.management.deliverable.Upload;
import com.topcoder.management.deliverable.UploadStatus;
import com.topcoder.management.deliverable.UploadType;
import com.topcoder.management.deliverable.persistence.UploadPersistenceException;
import com.topcoder.search.builder.SearchBuilderException;
import com.topcoder.search.builder.SearchBundle;
import com.topcoder.search.builder.SearchBundleManager;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.idgenerator.IDGeneratorFactory;

/**
 * Failure tests for class <code>PersistenceUploadManager</code>.
 *
 * @author assistant, TCSDEVELOPER
 * @since 1.0
 * @version 1.1
 */
public class PersistenceUploadManagerTest extends TestCase {

    /**
     * Represents the manager to test.
     */
    private PersistenceUploadManager manager;

    /**
     * File contains sql statement to initial database for upload search.
     */
    private static final String INIT_DB_SQL = "test_files/InitDB.sql";

    /**
     * File contains sql statement to clear database for upload search.
     */
    private static final String CLEAR_DB_SQL = "test_files/ClearDB.sql";

    /**
     * Set up the environment.
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();

        ConfigManager cm = ConfigManager.getInstance();
        if (!cm.existsNamespace("com.topcoder.db.connectionfactory.DBConnectionFactoryImpl")) {
            cm.add("failure/db.xml");
        }

        DeliverableTestHelper.executeBatch(CLEAR_DB_SQL);
        DeliverableTestHelper.executeBatch(INIT_DB_SQL);

        Map fields = new HashMap();
        fields.put("11", null);
        Map alias = new HashMap();
        alias.put("11", "22");

        manager = new PersistenceUploadManager(
                new SqlUploadPersistence(
                        new DBConnectionFactoryImpl("com.topcoder.db.connectionfactory.DBConnectionFactoryImpl")),
                new SearchBundle("name", fields, alias, "dd"),
                new SearchBundle("name", fields, alias, "dd"),
                IDGeneratorFactory.getIDGenerator("upload_id_seq"),
                IDGeneratorFactory.getIDGenerator("upload_type_id_seq"),
                IDGeneratorFactory.getIDGenerator("upload_status_id_seq"),
                IDGeneratorFactory.getIDGenerator("submission_id_seq"),
                IDGeneratorFactory.getIDGenerator("submission_status_id_seq"),
                IDGeneratorFactory.getIDGenerator("submission_type_id_seq"));

    }

    /**
     * Clean up the environment.
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        super.tearDown();

        DeliverableTestHelper.executeBatch(CLEAR_DB_SQL);

        ConfigManager cm = ConfigManager.getInstance();

        Iterator it = cm.getAllNamespaces();
        while (it.hasNext()) {
            cm.removeNamespace(it.next().toString());
        }
    }

    /**
     * Test method for PersistenceUploadManager(
     * com.topcoder.management.deliverable.persistence.UploadPersistence,
     * com.topcoder.search.builder.SearchBundle,
     * com.topcoder.search.builder.SearchBundle,
     * com.topcoder.util.idgenerator.IDGenerator,
     * com.topcoder.util.idgenerator.IDGenerator,
     * com.topcoder.util.idgenerator.IDGenerator,
     * com.topcoder.util.idgenerator.IDGenerator,
     * com.topcoder.util.idgenerator.IDGenerator,
     * com.topcoder.util.idgenerator.IDGenerator).
     * @throws Exception to JUnit
     */
    public void testPersistenceUploadManager1NullPersistence() throws Exception {
        try {
            new PersistenceUploadManager(
                    null,
                    new SearchBundle("name", new HashMap(), new HashMap(), "dd"),
                    new SearchBundle("name", new HashMap(), new HashMap(), "dd"),
                    IDGeneratorFactory.getIDGenerator("Upload Id Generator"),
                    IDGeneratorFactory.getIDGenerator("Upload Type Id Generator"),
                    IDGeneratorFactory.getIDGenerator("Upload Status Id Generator"),
                    IDGeneratorFactory.getIDGenerator("Submission Id Generator"),
                    IDGeneratorFactory.getIDGenerator("Submission Status Id Generator"),
                    IDGeneratorFactory.getIDGenerator("submission_type_id_seq"));
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for PersistenceUploadManager(
     * com.topcoder.management.deliverable.persistence.UploadPersistence,
     * com.topcoder.search.builder.SearchBundle,
     * com.topcoder.search.builder.SearchBundle,
     * com.topcoder.util.idgenerator.IDGenerator,
     * com.topcoder.util.idgenerator.IDGenerator,
     * com.topcoder.util.idgenerator.IDGenerator,
     * com.topcoder.util.idgenerator.IDGenerator,
     * com.topcoder.util.idgenerator.IDGenerator,
     * com.topcoder.util.idgenerator.IDGenerator).
     * @throws Exception to JUnit
     */
    public void testPersistenceUploadManager1NullUploadBundle() throws Exception {
        try {
            new PersistenceUploadManager(
                    new SqlUploadPersistence(
                            new DBConnectionFactoryImpl("com.topcoder.db.connectionfactory.DBConnectionFactoryImpl")),
                    null,
                    new SearchBundle("name", new HashMap(), new HashMap(), "dd"),
                    IDGeneratorFactory.getIDGenerator("Upload Id Generator"),
                    IDGeneratorFactory.getIDGenerator("Upload Type Id Generator"),
                    IDGeneratorFactory.getIDGenerator("Upload Status Id Generator"),
                    IDGeneratorFactory.getIDGenerator("Submission Id Generator"),
                    IDGeneratorFactory.getIDGenerator("Submission Status Id Generator"),
                    IDGeneratorFactory.getIDGenerator("submission_type_id_seq"));
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for PersistenceUploadManager(
     * com.topcoder.management.deliverable.persistence.UploadPersistence,
     * com.topcoder.search.builder.SearchBundle,
     * com.topcoder.search.builder.SearchBundle,
     * com.topcoder.util.idgenerator.IDGenerator,
     * com.topcoder.util.idgenerator.IDGenerator,
     * com.topcoder.util.idgenerator.IDGenerator,
     * com.topcoder.util.idgenerator.IDGenerator,
     * com.topcoder.util.idgenerator.IDGenerator,
     * com.topcoder.util.idgenerator.IDGenerator).
     * @throws Exception to JUnit
     */
    public void testPersistenceUploadManager1NullSubmissionBundle() throws Exception {
        try {
            new PersistenceUploadManager(
                    new SqlUploadPersistence(
                            new DBConnectionFactoryImpl("com.topcoder.db.connectionfactory.DBConnectionFactoryImpl")),
                    new SearchBundle("name", new HashMap(), new HashMap(), "dd"),
                    null,
                    IDGeneratorFactory.getIDGenerator("Upload Id Generator"),
                    IDGeneratorFactory.getIDGenerator("Upload Type Id Generator"),
                    IDGeneratorFactory.getIDGenerator("Upload Status Id Generator"),
                    IDGeneratorFactory.getIDGenerator("Submission Id Generator"),
                    IDGeneratorFactory.getIDGenerator("Submission Status Id Generator"),
                    IDGeneratorFactory.getIDGenerator("submission_type_id_seq"));
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for PersistenceUploadManager(
     * com.topcoder.management.deliverable.persistence.UploadPersistence,
     * com.topcoder.search.builder.SearchBundle,
     * com.topcoder.search.builder.SearchBundle,
     * com.topcoder.util.idgenerator.IDGenerator,
     * com.topcoder.util.idgenerator.IDGenerator,
     * com.topcoder.util.idgenerator.IDGenerator,
     * com.topcoder.util.idgenerator.IDGenerator,
     * com.topcoder.util.idgenerator.IDGenerator,
     * com.topcoder.util.idgenerator.IDGenerator).
     * @throws Exception to JUnit
     */
    public void testPersistenceUploadManager1NullUploadIdGen() throws Exception {
        try {
            new PersistenceUploadManager(
                    new SqlUploadPersistence(
                            new DBConnectionFactoryImpl("com.topcoder.db.connectionfactory.DBConnectionFactoryImpl")),
                    new SearchBundle("name", new HashMap(), new HashMap(), "dd"),
                    new SearchBundle("name", new HashMap(), new HashMap(), "dd"),
                    null, //IDGeneratorFactory.getIDGenerator("upload"),
                    IDGeneratorFactory.getIDGenerator("Upload Type Id Generator"),
                    IDGeneratorFactory.getIDGenerator("Upload Status Id Generator"),
                    IDGeneratorFactory.getIDGenerator("Submission Id Generator"),
                    IDGeneratorFactory.getIDGenerator("Submission Status Id Generator"),
                    IDGeneratorFactory.getIDGenerator("submission_type_id_seq"));
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for PersistenceUploadManager(
     * com.topcoder.management.deliverable.persistence.UploadPersistence,
     * com.topcoder.search.builder.SearchBundle,
     * com.topcoder.search.builder.SearchBundle,
     * com.topcoder.util.idgenerator.IDGenerator,
     * com.topcoder.util.idgenerator.IDGenerator,
     * com.topcoder.util.idgenerator.IDGenerator,
     * com.topcoder.util.idgenerator.IDGenerator,
     * com.topcoder.util.idgenerator.IDGenerator,
     * com.topcoder.util.idgenerator.IDGenerator).
     * @throws Exception to JUnit
     */
    public void testPersistenceUploadManager1NullUploadTypeIdGen() throws Exception {
        try {
            new PersistenceUploadManager(
                    new SqlUploadPersistence(
                            new DBConnectionFactoryImpl("com.topcoder.db.connectionfactory.DBConnectionFactoryImpl")),
                    new SearchBundle("name", new HashMap(), new HashMap(), "dd"),
                    new SearchBundle("name", new HashMap(), new HashMap(), "dd"),
                    IDGeneratorFactory.getIDGenerator("Upload Id Generator"),
                    null, //IDGeneratorFactory.getIDGenerator("uploadType"),
                    IDGeneratorFactory.getIDGenerator("Upload Status Id Generator"),
                    IDGeneratorFactory.getIDGenerator("Submission Id Generator"),
                    IDGeneratorFactory.getIDGenerator("Submission Status Id Generator"),
                    IDGeneratorFactory.getIDGenerator("submission_type_id_seq"));
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for PersistenceUploadManager(
     * com.topcoder.management.deliverable.persistence.UploadPersistence,
     * com.topcoder.search.builder.SearchBundle,
     * com.topcoder.search.builder.SearchBundle,
     * com.topcoder.util.idgenerator.IDGenerator,
     * com.topcoder.util.idgenerator.IDGenerator,
     * com.topcoder.util.idgenerator.IDGenerator,
     * com.topcoder.util.idgenerator.IDGenerator,
     * com.topcoder.util.idgenerator.IDGenerator,
     * com.topcoder.util.idgenerator.IDGenerator).
     * @throws Exception to JUnit
     */
    public void testPersistenceUploadManager1NullUploadStatusIdGen() throws Exception {
        try {
            new PersistenceUploadManager(
                    new SqlUploadPersistence(
                            new DBConnectionFactoryImpl("com.topcoder.db.connectionfactory.DBConnectionFactoryImpl")),
                    new SearchBundle("name", new HashMap(), new HashMap(), "dd"),
                    new SearchBundle("name", new HashMap(), new HashMap(), "dd"),
                    IDGeneratorFactory.getIDGenerator("Upload Id Generator"),
                    IDGeneratorFactory.getIDGenerator("Upload Type Id Generator"),
                    null, //IDGeneratorFactory.getIDGenerator("uploadStatus"),
                    IDGeneratorFactory.getIDGenerator("Submission Id Generator"),
                    IDGeneratorFactory.getIDGenerator("Submission Status Id Generator"),
                    IDGeneratorFactory.getIDGenerator("submission_type_id_seq"));
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for PersistenceUploadManager(
     * com.topcoder.management.deliverable.persistence.UploadPersistence,
     * com.topcoder.search.builder.SearchBundle,
     * com.topcoder.search.builder.SearchBundle,
     * com.topcoder.util.idgenerator.IDGenerator,
     * com.topcoder.util.idgenerator.IDGenerator,
     * com.topcoder.util.idgenerator.IDGenerator,
     * com.topcoder.util.idgenerator.IDGenerator,
     * com.topcoder.util.idgenerator.IDGenerator,
     * com.topcoder.util.idgenerator.IDGenerator).
     * @throws Exception to JUnit
     */
    public void testPersistenceUploadManager1NullSubmissionIdGen() throws Exception {
        try {
            new PersistenceUploadManager(
                    new SqlUploadPersistence(
                            new DBConnectionFactoryImpl("com.topcoder.db.connectionfactory.DBConnectionFactoryImpl")),
                    new SearchBundle("name", new HashMap(), new HashMap(), "dd"),
                    new SearchBundle("name", new HashMap(), new HashMap(), "dd"),
                    IDGeneratorFactory.getIDGenerator("Upload Id Generator"),
                    IDGeneratorFactory.getIDGenerator("Upload Type Id Generator"),
                    IDGeneratorFactory.getIDGenerator("Upload Status Id Generator"),
                    null, //IDGeneratorFactory.getIDGenerator("submission"),
                    IDGeneratorFactory.getIDGenerator("Submission Status Id Generator"),
                    IDGeneratorFactory.getIDGenerator("submission_type_id_seq"));
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for PersistenceUploadManager(
     * com.topcoder.management.deliverable.persistence.UploadPersistence,
     * com.topcoder.search.builder.SearchBundle,
     * com.topcoder.search.builder.SearchBundle,
     * com.topcoder.util.idgenerator.IDGenerator,
     * com.topcoder.util.idgenerator.IDGenerator,
     * com.topcoder.util.idgenerator.IDGenerator,
     * com.topcoder.util.idgenerator.IDGenerator,
     * com.topcoder.util.idgenerator.IDGenerator,
     * com.topcoder.util.idgenerator.IDGenerator).
     * @throws Exception to JUnit
     * @since 1.1
     */
    public void testPersistenceUploadManager1NullSubmissionStatusIdGen() throws Exception {
        try {
            new PersistenceUploadManager(
                    new SqlUploadPersistence(
                            new DBConnectionFactoryImpl("com.topcoder.db.connectionfactory.DBConnectionFactoryImpl")),
                    new SearchBundle("name", new HashMap(), new HashMap(), "dd"),
                    new SearchBundle("name", new HashMap(), new HashMap(), "dd"),
                    IDGeneratorFactory.getIDGenerator("Upload Id Generator"),
                    IDGeneratorFactory.getIDGenerator("Upload Type Id Generator"),
                    IDGeneratorFactory.getIDGenerator("Upload Status Id Generator"),
                    null, //IDGeneratorFactory.getIDGenerator("submission"),
                    IDGeneratorFactory.getIDGenerator("Submission Status Id Generator"),
                    IDGeneratorFactory.getIDGenerator("submission_type_id_seq"));
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for PersistenceUploadManager(
     * com.topcoder.management.deliverable.persistence.UploadPersistence,
     * com.topcoder.search.builder.SearchBundle,
     * com.topcoder.search.builder.SearchBundle,
     * com.topcoder.util.idgenerator.IDGenerator,
     * com.topcoder.util.idgenerator.IDGenerator,
     * com.topcoder.util.idgenerator.IDGenerator,
     * com.topcoder.util.idgenerator.IDGenerator,
     * com.topcoder.util.idgenerator.IDGenerator,
     * com.topcoder.util.idgenerator.IDGenerator).
     * @throws Exception to JUnit
     */
    public void testPersistenceUploadManager1NullSubmissionTypeIdGenerator() throws Exception {
        try {
            new PersistenceUploadManager(
                    new SqlUploadPersistence(
                            new DBConnectionFactoryImpl("com.topcoder.db.connectionfactory.DBConnectionFactoryImpl")),
                    new SearchBundle("name", new HashMap(), new HashMap(), "dd"),
                    new SearchBundle("name", new HashMap(), new HashMap(), "dd"),
                    IDGeneratorFactory.getIDGenerator("Upload Id Generator"),
                    IDGeneratorFactory.getIDGenerator("Upload Type Id Generator"),
                    IDGeneratorFactory.getIDGenerator("Upload Status Id Generator"),
                    IDGeneratorFactory.getIDGenerator("submission"),
                    IDGeneratorFactory.getIDGenerator("Submission Status Id Generator"),
                    null);
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for PersistenceUploadManager(UploadPersistence, SearchBundleManager).
     * @throws Exception to JUnit
     */
    public void testPersistenceUploadManager2NullPersistence() throws Exception {
        try {
            new PersistenceUploadManager(null, new SearchBundleManager());
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for PersistenceUploadManager(UploadPersistence, SearchBundleManager).
     * @throws Exception to JUnit
     */
    public void testPersistenceUploadManager2NullBundleManager() throws Exception {
        try {
            new PersistenceUploadManager(
                    new SqlUploadPersistence(
                            new DBConnectionFactoryImpl("com.topcoder.db.connectionfactory.DBConnectionFactoryImpl")),
                    new SearchBundleManager());
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for createSubmission(com.topcoder.management.deliverable.Submission, java.lang.String).
     * @throws UploadPersistenceException to JUnit
     */
    public void testCreateSubmissionNullSubmission() throws UploadPersistenceException {
        try {
            manager.createSubmission(null, "a");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for createSubmission(com.topcoder.management.deliverable.Submission, java.lang.String).
     * @throws UploadPersistenceException to JUnit
     */
    public void testCreateSubmissionNotUnset() throws UploadPersistenceException {
        try {
            manager.createSubmission(new Submission(1), "a");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for createSubmission(com.topcoder.management.deliverable.Submission, java.lang.String).
     * @throws UploadPersistenceException to JUnit
     */
    public void testCreateSubmissionBadStatus() throws UploadPersistenceException {
        try {
            manager.createSubmission(new Submission(), "a");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for createSubmissionStatus(com.topcoder.management.deliverable.SubmissionStatus, java.lang.String).
     * @throws UploadPersistenceException to JUnit
     */
    public void testCreateSubmissionStatusNullSubmissionStatus() throws UploadPersistenceException {
        try {
            manager.createSubmissionStatus(null, "a");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for createSubmissionStatus(com.topcoder.management.deliverable.SubmissionStatus, java.lang.String).
     * @throws UploadPersistenceException to JUnit
     */
    public void testCreateSubmissionStatusNullSubmissionNotUnset() throws UploadPersistenceException {
        try {
            manager.createSubmissionStatus(new SubmissionStatus(1), "a");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for createSubmission(com.topcoder.management.deliverable.Submission, java.lang.String).
     * @throws UploadPersistenceException to JUnit
     */
    public void testCreateSubmissionStatusNullSubmissionStatusBadStatus() throws UploadPersistenceException {
        try {
            manager.createSubmissionStatus(new SubmissionStatus(), "a");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for createUpload(com.topcoder.management.deliverable.Upload, java.lang.String).
     * @throws UploadPersistenceException to JUnit
     */
    public void testCreateUploadNullUpload() throws UploadPersistenceException {
        try {
            manager.createUpload(null, "a");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for createUpload(com.topcoder.management.deliverable.Upload, java.lang.String).
     * @throws UploadPersistenceException to JUnit
     */
    public void testCreateUploadNotUnset() throws UploadPersistenceException {
        try {
            manager.createUpload(new Upload(1), "a");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for createUpload(com.topcoder.management.deliverable.Upload, java.lang.String).
     * @throws UploadPersistenceException to JUnit
     */
    public void testCreateUploadBadStatus() throws UploadPersistenceException {
        try {
            manager.createUpload(new Upload(), "a");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for createUploadStatus(com.topcoder.management.deliverable.UploadStatus, java.lang.String).
     * @throws UploadPersistenceException to JUnit
     */
    public void testCreateUploadStatusNullUploadStatus() throws UploadPersistenceException {
        try {
            manager.createUploadStatus(null, "a");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for createUploadStatus(com.topcoder.management.deliverable.UploadStatus, java.lang.String).
     * @throws UploadPersistenceException to JUnit
     */
    public void testCreateUploadStatusNotUnset() throws UploadPersistenceException {
        try {
            manager.createUploadStatus(new UploadStatus(1), "a");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for createUploadStatus(com.topcoder.management.deliverable.UploadStatus, java.lang.String).
     * @throws UploadPersistenceException to JUnit
     */
    public void testCreateUploadStatusBadStatus() throws UploadPersistenceException {
        try {
            manager.createUploadStatus(new UploadStatus(), "a");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for createUploadType(com.topcoder.management.deliverable.UploadType, java.lang.String).
     * @throws UploadPersistenceException to JUnit
     */
    public void testCreateUploadNullUploadType() throws UploadPersistenceException {
        try {
            manager.createUploadType(null, "a");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for createUploadType(com.topcoder.management.deliverable.UploadType, java.lang.String).
     * @throws UploadPersistenceException to JUnit
     */
    public void testCreateUploadTypeNotUnset() throws UploadPersistenceException {
        try {
            manager.createUploadType(new UploadType(1), "a");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for createUploadType(com.topcoder.management.deliverable.UploadType, java.lang.String).
     * @throws UploadPersistenceException to JUnit
     */
    public void testCreateUploadTypeBadStatus() throws UploadPersistenceException {
        try {
            manager.createUploadType(new UploadType(), "a");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for {@link com.topcoder.management.deliverable.PersistenceUploadManager#getSubmission(long)}.
     * @throws UploadPersistenceException to JUnit
     */
    public void testGetSubmissionZeroId() throws UploadPersistenceException {
        try {
            manager.getSubmission(0);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for {@link com.topcoder.management.deliverable.PersistenceUploadManager#getSubmission(long)}.
     * @throws UploadPersistenceException to JUnit
     */
    public void testGetSubmissionNegativeId() throws UploadPersistenceException {
        try {
            manager.getSubmission(-1);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for removeSubmission(com.topcoder.management.deliverable.Submission, java.lang.String).
     * @throws UploadPersistenceException to JUnit
     */
    public void testRemoveSubmissionNullSubmission() throws UploadPersistenceException {
        try {
            manager.removeSubmission(null, "a");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for removeSubmission(com.topcoder.management.deliverable.Submission, java.lang.String).
     * @throws UploadPersistenceException to JUnit
     */
    public void testRemoveSubmissionNotSet() throws UploadPersistenceException {
        try {
            manager.removeSubmission(new Submission(), "a");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for removeSubmissionStatus(com.topcoder.management.deliverable.SubmissionStatus, java.lang.String).
     * @throws UploadPersistenceException to JUnit
     */
    public void testRemoveSubmissionStatusNullSubmissionStatus() throws UploadPersistenceException {
        try {
            manager.removeSubmissionStatus(null, "a");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for removeSubmissionStatus(com.topcoder.management.deliverable.SubmissionStatus, java.lang.String).
     * @throws UploadPersistenceException to JUnit
     */
    public void testRemoveSubmissionStatusNotSet() throws UploadPersistenceException {
        try {
            manager.removeSubmissionStatus(new SubmissionStatus(), "a");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for removeUpload(com.topcoder.management.deliverable.Upload, java.lang.String).
     * @throws UploadPersistenceException to JUnit
     */
    public void testRemoveUploadNullUpload() throws UploadPersistenceException {
        try {
            manager.removeUpload(null, "a");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for removeUpload(com.topcoder.management.deliverable.Upload, java.lang.String).
     * @throws UploadPersistenceException to JUnit
     */
    public void testRemoveUploadNotSet() throws UploadPersistenceException {
        try {
            manager.removeUpload(new Upload(), "a");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for removeUploadStatus(com.topcoder.management.deliverable.UploadStatus, java.lang.String).
     * @throws UploadPersistenceException to JUnit
     */
    public void testRemoveUploadNullUploadStatus() throws UploadPersistenceException {
        try {
            manager.removeUploadStatus(null, "a");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for removeUploadStatus(com.topcoder.management.deliverable.UploadStatus, java.lang.String).
     * @throws UploadPersistenceException to JUnit
     */
    public void testRemoveUploadStatusNotSet() throws UploadPersistenceException {
        try {
            manager.removeUploadStatus(new UploadStatus(), "a");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }


    /**
     * Test method for searchSubmissions(com.topcoder.search.builder.filter.Filter).
     * @throws SearchBuilderException to JUnit
     * @throws UploadPersistenceException to JUnit
     */
    public void testSearchSubmissionsNullFilter() throws UploadPersistenceException, SearchBuilderException {
        try {
            manager.searchSubmissions(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for searchUploads(com.topcoder.search.builder.filter.Filter).
     * @throws SearchBuilderException to JUnit
     * @throws UploadPersistenceException to JUnit
     */
    public void testSearchUploadsNullFilter() throws UploadPersistenceException, SearchBuilderException {
        try {
            manager.searchUploads(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }


    /**
     * Test method for updateSubmission(com.topcoder.management.deliverable.Submission, java.lang.String).
     * @throws UploadPersistenceException to JUnit
     */
    public void testUpdateSubmissionNullSubmission() throws UploadPersistenceException {
        try {
            manager.updateSubmission(null, "a");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for updateSubmission(com.topcoder.management.deliverable.Submission, java.lang.String).
     * @throws UploadPersistenceException to JUnit
     */
    public void testupdateSubmissionNotSet() throws UploadPersistenceException {
        try {
            manager.updateSubmission(new Submission(), "a");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for updateSubmissionStatus(com.topcoder.management.deliverable.Submission, java.lang.String).
     * @throws UploadPersistenceException to JUnit
     */
    public void testUpdateSubmissionStatusNullSubmissionStatus() throws UploadPersistenceException {
        try {
            manager.updateSubmissionStatus(null, "a");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for updateSubmissionStatus(com.topcoder.management.deliverable.Submission, java.lang.String).
     * @throws UploadPersistenceException to JUnit
     */
    public void testupdateSubmissionStatusNotSet() throws UploadPersistenceException {
        try {
            manager.updateSubmissionStatus(new SubmissionStatus(), "a");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for updateUpload(com.topcoder.management.deliverable.Upload, java.lang.String).
     * @throws UploadPersistenceException to JUnit
     */
    public void testUpdateUploadNullSubmission() throws UploadPersistenceException {
        try {
            manager.updateUpload(null, "a");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for updateUpload(com.topcoder.management.deliverable.Upload, java.lang.String).
     * @throws UploadPersistenceException to JUnit
     */
    public void testupdateUploadNotSet() throws UploadPersistenceException {
        try {
            manager.updateUpload(new Upload(), "a");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for updateUploadStatus(com.topcoder.management.deliverable.UploadStatus, java.lang.String).
     * @throws UploadPersistenceException to JUnit
     */
    public void testUpdateUploadStatusNullSubmissionStatus() throws UploadPersistenceException {
        try {
            manager.updateUploadStatus(null, "a");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for updateUploadStatus(com.topcoder.management.deliverable.UploadStatus, java.lang.String).
     * @throws UploadPersistenceException to JUnit
     */
    public void testupdateUploadStatusNotSet() throws UploadPersistenceException {
        try {
            manager.updateUploadStatus(new UploadStatus(), "a");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for updateUploadType(com.topcoder.management.deliverable.UploadType, java.lang.String).
     * @throws UploadPersistenceException to JUnit
     */
    public void testUpdateUploadTypeNullSubmission() throws UploadPersistenceException {
        try {
            manager.updateUploadType(null, "a");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for updateUploadType(com.topcoder.management.deliverable.UploadType, java.lang.String).
     * @throws UploadPersistenceException to JUnit
     */
    public void testupdateUploadTypeNotSet() throws UploadPersistenceException {
        try {
            manager.updateUploadType(new UploadType(), "a");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * <p>
     * Failure test for the method <code>createSubmissionType(SubmissionType submissionType, String operator)</code>
     * with submissionType is null, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     *
     * @since 1.1
     */
    public void testCreateSubmissionType_SubmissionTypeIdNull() throws Exception {
        try {
            manager.createSubmissionType(null, "user");

            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // Good
        }
    }

    /**
     * <p>
     * Failure test for the method <code>createSubmissionType(SubmissionType submissionType, String operator)</code>
     * with submissionType is invalid, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     *
     * @since 1.1
     */
    public void testCreateSubmissionType_SubmissionTypeIdInvalid() throws Exception {
        try {
            manager.createSubmissionType(new SubmissionType(1), "user");

            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // Good
        }
    }

    /**
     * <p>
     * Failure test for the method <code>createSubmissionType(SubmissionType submissionType, String operator)</code>
     * with operator is null, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     *
     * @since 1.1
     */
    public void testCreateSubmissionType_OperatorIsNull() throws Exception {
        try {
            manager.createSubmissionType(new SubmissionType(), null);

            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // Good
        }
    }

    /**
     * <p>
     * Failure test for the method <code>updateSubmissionType(SubmissionType submissionType, String operator)</code>
     * with submissionType is null, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     *
     * @since 1.1
     */
    public void testUpdateSubmissionType_SubmissionTypeIsNull() throws Exception {
        try {
            manager.updateSubmissionType(null, "user");

            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // Good
        }
    }

    /**
     * <p>
     * Failure test for the method <code>updateSubmissionType(SubmissionType submissionType, String operator)</code>
     * with submissionType is invalid, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     *
     * @since 1.1
     */
    public void testUpdateSubmissionType_SubmissionTypeIsInvalid() throws Exception {
        try {
            manager.updateSubmissionType(new SubmissionType(-1), "user");

            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // Good
        }
    }

    /**
     * <p>
     * Failure test for the method <code>updateSubmissionType(SubmissionType submissionType, String operator)</code>
     * with operator is null, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     *
     * @since 1.1
     */
    public void testUpdateSubmissionType_OperatorIsNull() throws Exception {
        try {
            manager.updateSubmissionType(new SubmissionType(1), null);

            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // Good
        }
    }

    /**
     * <p>
     * Failure test for the method <code>updateSubmissionType(SubmissionType submissionType, String operator)</code>
     * with submissionType is null, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     *
     * @since 1.1
     */
    public void testRemoveSubmissionType_SubmissionTypeIsNull() throws Exception {
        try {
            manager.removeSubmissionType(null, "user");

            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // Good
        }
    }

    /**
     * <p>
     * Failure test for the method <code>updateSubmissionType(SubmissionType submissionType, String operator)</code>
     * with submissionType is invalid, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     *
     * @since 1.1
     */
    public void testRemoveSubmissionType_SubmissionTypeIsInvalid() throws Exception {
        try {
            manager.removeSubmissionType(new SubmissionType(-1), "user");

            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // Good
        }
    }

    /**
     * <p>
     * Failure test for the method <code>updateSubmissionType(SubmissionType submissionType, String operator)</code>
     * with operator is null, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     *
     * @since 1.1
     */
    public void testRemoveSubmissionType_OperatorNull() throws Exception {
        try {
            manager.removeSubmissionType(new SubmissionType(1), null);

            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // Good
        }
    }
}
