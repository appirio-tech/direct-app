/*
 * Copyright (C) 2006,2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;

import com.topcoder.management.deliverable.persistence.UploadPersistenceException;
import com.topcoder.management.deliverable.persistence.sql.SqlUploadPersistence;
import com.topcoder.management.deliverable.search.SubmissionFilterBuilder;
import com.topcoder.management.deliverable.search.UploadFilterBuilder;
import com.topcoder.search.builder.SearchBuilderException;
import com.topcoder.search.builder.SearchBundle;
import com.topcoder.search.builder.SearchBundleManager;
import com.topcoder.util.idgenerator.IDGenerationException;
import com.topcoder.util.idgenerator.IDGenerator;

/**
 * <p>
 * Unit test for PersistenceUploadManager.
 * </p>
 * <p>
 * <em>Changes in 1.1: </em>
 * <ul>
 * <li>Added test cases for methods for managing submission types.</li>
 * <li>Updated test cases for constructors.</li>
 * </ul>
 * </p>
 * <p>
 * <em>Changes in 1.2: </em>
 * <ul>
 * <li>Generic types are used for simplification.</li>
 * <li>Fixed the test cases according to the update.</li>
 * <li>Added new test cases for new methods.</li>
 * </ul>
 * </p>
 *
 * @author singlewood, sparemax
 * @author TCSDEVELOPER
 * @version 1.2
 */
public class PersistenceUploadManagerTests extends TestCase {

    /**
     * The name under which the upload search bundle should appear in the SearchBundleManager, if the
     * SearchBundleManager constructor is used.
     */
    public static final String UPLOAD_SEARCH_BUNDLE_NAME = "Upload Search Bundle";

    /**
     * The name under which the submission search bundle should appear in the SearchBundleManager, if the
     * SearchBundleManager constructor is used.
     */
    public static final String SUBMISSION_SEARCH_BUNDLE_NAME = "Submission Search Bundle";

    /**
     * The path of the configuration file.
     */
    private static final String CONFIG = "SearchBundleManager.xml";

    /**
     * File contains sql statement to initial database for upload search.
     */
    private static final String INIT_DB_SQL = "test_files/InitDB.sql";

    /**
     * File contains sql statement to clear database for upload search.
     */
    private static final String CLEAR_DB_SQL = "test_files/ClearDB.sql";

    /**
     * Represents the configuration namespace for search builder.
     */
    private static final String SEARCH_BUILDER_NAMESPACE = "com.topcoder.searchbuilder.DeliverableManager";

    /**
     * The test PersistenceUploadManager instance.
     */
    private PersistenceUploadManager persistenceUploadManager = null;

    /**
     * The test SqlUploadPersistence instance. Here We use SqlUploadPersistence instead of UploadPersistence, that way
     * we can add test methods in the SqlUploadPersistence.
     */
    private SqlUploadPersistence persistence = null;

    /**
     * The test SearchBundle instance.
     */
    private SearchBundle uploadSearchBundle = null;

    /**
     * The test SearchBundle instance.
     */
    private SearchBundle submissionSearchBundle = null;

    /**
     * The test IDGenerator instance.
     */
    private IDGenerator uploadIdGenerator = null;

    /**
     * The test IDGenerator instance.
     */
    private IDGenerator submissionIdGenerator = null;

    /**
     * The test IDGenerator instance.
     */
    private IDGenerator uploadTypeIdGenerator = null;

    /**
     * The test IDGenerator instance.
     */
    private IDGenerator uploadStatusIdGenerator = null;

    /**
     * The test IDGenerator instance.
     */
    private IDGenerator submissionStatusIdGenerator = null;

    /**
     * <p>
     * The test IDGenerator instance.
     * </p>
     *
     * @since 1.1
     */
    private IDGenerator submissionTypeIdGenerator;

    /**
     * The test SearchBundleManager instance.
     */
    private SearchBundleManager searchBundleManager = null;

    /**
     * Create the test instance.
     *
     * @throws Exception
     *             exception to JUnit.
     */
    public void setUp() throws Exception {
        DeliverableTestHelper.unloadConfig();
        DeliverableTestHelper.loadConfig(CONFIG);

        // Set up the SearchBundleManager.
        searchBundleManager = new SearchBundleManager(SEARCH_BUILDER_NAMESPACE);

        uploadSearchBundle = searchBundleManager.getSearchBundle(UPLOAD_SEARCH_BUNDLE_NAME);
        submissionSearchBundle = searchBundleManager.getSearchBundle(SUBMISSION_SEARCH_BUNDLE_NAME);

        // Build IDGenerators for test. Here we implement a simple IDGeneratorIncrease.
        uploadIdGenerator = new IDGeneratorIncrease();
        uploadIdGenerator = new IDGeneratorIncrease();
        submissionIdGenerator = new IDGeneratorIncrease();
        uploadTypeIdGenerator = new IDGeneratorIncrease();
        uploadStatusIdGenerator = new IDGeneratorIncrease();
        submissionStatusIdGenerator = new IDGeneratorIncrease();
        submissionTypeIdGenerator = new IDGeneratorIncrease();

        // No real database is used in this test.
        persistence = new SqlUploadPersistence(null);

        // Create a PersistenceUploadManager.
        persistenceUploadManager = new PersistenceUploadManager(persistence, uploadSearchBundle,
                submissionSearchBundle, uploadIdGenerator, uploadTypeIdGenerator, uploadStatusIdGenerator,
                submissionIdGenerator, submissionStatusIdGenerator, submissionTypeIdGenerator);
    }

    /**
     * Clean the config.
     *
     * @throws Exception
     *             exception to JUnit.
     */
    public void tearDown() throws Exception {
        // no operation.
    }

    /**
     * Set the first parameter of constructor1 to null. IllegalArgumentException should be thrown.
     */
    public void testConstructor1_Null1() {
        try {
            new PersistenceUploadManager(null, uploadSearchBundle, submissionSearchBundle, uploadIdGenerator,
                    uploadTypeIdGenerator, uploadStatusIdGenerator, submissionIdGenerator, submissionStatusIdGenerator,
                    submissionTypeIdGenerator);
            fail("IllegalArgumentException should be thrown because of null parameter.");
        } catch (IllegalArgumentException e) {
            // expected.
        }
    }

    /**
     * Set the 2th parameter of constructor1 to null. IllegalArgumentException should be thrown.
     */
    public void testConstructor1_Null2() {
        try {
            new PersistenceUploadManager(persistence, null, submissionSearchBundle, uploadIdGenerator,
                    uploadTypeIdGenerator, uploadStatusIdGenerator, submissionIdGenerator, submissionStatusIdGenerator,
                    submissionTypeIdGenerator);
            fail("IllegalArgumentException should be thrown because of null parameter.");
        } catch (IllegalArgumentException e) {
            // expected.
        }
    }

    /**
     * Set the 3th parameter of constructor1 to null. IllegalArgumentException should be thrown.
     */
    public void testConstructor1_Null3() {
        try {
            new PersistenceUploadManager(persistence, uploadSearchBundle, null, uploadIdGenerator,
                    uploadTypeIdGenerator, uploadStatusIdGenerator, submissionIdGenerator, submissionStatusIdGenerator,
                    submissionTypeIdGenerator);
            fail("IllegalArgumentException should be thrown because of null parameter.");
        } catch (IllegalArgumentException e) {
            // expected.
        }
    }

    /**
     * Set the 4th parameter of constructor1 to null. IllegalArgumentException should be thrown.
     */
    public void testConstructor1_Null4() {
        try {
            new PersistenceUploadManager(persistence, uploadSearchBundle, submissionSearchBundle, null,
                    uploadTypeIdGenerator, uploadStatusIdGenerator, submissionIdGenerator, submissionStatusIdGenerator,
                    submissionTypeIdGenerator);
            fail("IllegalArgumentException should be thrown because of null parameter.");
        } catch (IllegalArgumentException e) {
            // expected.
        }
    }

    /**
     * Set the 5th parameter of constructor1 to null. IllegalArgumentException should be thrown.
     */
    public void testConstructor1_Null5() {
        try {
            new PersistenceUploadManager(persistence, uploadSearchBundle, submissionSearchBundle, uploadIdGenerator,
                    null, uploadStatusIdGenerator, submissionIdGenerator, submissionStatusIdGenerator,
                    submissionTypeIdGenerator);
            fail("IllegalArgumentException should be thrown because of null parameter.");
        } catch (IllegalArgumentException e) {
            // expected.
        }
    }

    /**
     * Set the 6th parameter of constructor1 to null. IllegalArgumentException should be thrown.
     */
    public void testConstructor1_Null6() {
        try {
            new PersistenceUploadManager(persistence, uploadSearchBundle, submissionSearchBundle, uploadIdGenerator,
                    uploadTypeIdGenerator, null, submissionIdGenerator, submissionStatusIdGenerator,
                    submissionTypeIdGenerator);
            fail("IllegalArgumentException should be thrown because of null parameter.");
        } catch (IllegalArgumentException e) {
            // expected.
        }
    }

    /**
     * Set the 7th parameter of constructor1 to null. IllegalArgumentException should be thrown.
     */
    public void testConstructor1_Null7() {
        try {
            new PersistenceUploadManager(persistence, uploadSearchBundle, submissionSearchBundle, uploadIdGenerator,
                    uploadTypeIdGenerator, uploadStatusIdGenerator, null, submissionStatusIdGenerator,
                    submissionTypeIdGenerator);
            fail("IllegalArgumentException should be thrown because of null parameter.");
        } catch (IllegalArgumentException e) {
            // expected.
        }
    }

    /**
     * <p>
     * Set the 8th parameter of constructor1 to null. IllegalArgumentException should be thrown.
     * </p>
     *
     * @since 1.1
     */
    public void testConstructor1_Null8() {
        try {
            new PersistenceUploadManager(persistence, uploadSearchBundle, submissionSearchBundle, uploadIdGenerator,
                    uploadTypeIdGenerator, uploadStatusIdGenerator, submissionStatusIdGenerator, null,
                    submissionTypeIdGenerator);
            fail("IllegalArgumentException should be thrown because of null parameter.");
        } catch (IllegalArgumentException e) {
            // expected.
        }
    }

    /**
     * Set constructor1 with valid parameter. No exception should be thrown.
     */
    public void testConstructor1_Valid() {
        try {
            new PersistenceUploadManager(persistence, uploadSearchBundle, submissionSearchBundle, uploadIdGenerator,
                    uploadTypeIdGenerator, uploadStatusIdGenerator, submissionIdGenerator, submissionStatusIdGenerator,
                    submissionTypeIdGenerator);
        } catch (IllegalArgumentException e) {
            fail("IllegalArgumentException should not be thrown because of valid parameters.");
        }
    }

    /**
     * Set the 1th parameter of constructor2 to null. IllegalArgumentException should be thrown.
     */
    public void testConstructor2_Null1() {
        try {
            new PersistenceUploadManager(null, searchBundleManager);
            fail("IllegalArgumentException should be thrown because of null parameter.");
        } catch (IllegalArgumentException e) {
            // expected.
        } catch (IDGenerationException e) {
            // IllegalArgumentException is not be thrown here because checking on persistence
            // is after the checking of IDGeneration. But it won't affect the function.
            // fail("IDGenerationException should not be thrown because of null parameter.");
        }
    }

    /**
     * Set the 2th parameter of constructor2 to null. IllegalArgumentException should be thrown.
     */
    public void testConstructor2_Null2() {
        try {
            new PersistenceUploadManager(persistence, null);
            fail("IllegalArgumentException should be thrown because of null parameter.");
        } catch (IllegalArgumentException e) {
            // expected.
        } catch (IDGenerationException e) {
            fail("IDGenerationException should not be thrown because of null parameter.");
        }
    }

    /**
     * Set the behavior of createUpload. Set the 1th parameter to null. IllegalArgumentException should be thrown.
     */
    public void testCreateUpload_Null1() {
        try {
            persistenceUploadManager.createUpload(null, "user");
            fail("IllegalArgumentException should be thrown because of null parameter.");
        } catch (IllegalArgumentException e) {
            // expected.
        } catch (UploadPersistenceException e) {
            fail("UploadPersistenceException should not be thrown because of null parameter.");
        }
    }

    /**
     * Set the behavior of createUpload. Set the 2th parameter to null. IllegalArgumentException should be thrown.
     */
    public void testCreateUpload_Null2() {
        try {
            persistenceUploadManager.createUpload(new Upload(), null);
            fail("IllegalArgumentException should be thrown because of null parameter.");
        } catch (IllegalArgumentException e) {
            // expected.
        } catch (UploadPersistenceException e) {
            fail("UploadPersistenceException should not be thrown because of null parameter.");
        }
    }

    /**
     * Set the behavior of createUpload. Set the upload's id to 10. Since upload's id should be UNSET_ID,
     * IllegalArgumentException should be thrown.
     */
    public void testCreateUpload_Invalid1() {
        try {
            persistenceUploadManager.createUpload(new Upload(10), "user");
            fail("IllegalArgumentException should be thrown because of this invalid parameter.");
        } catch (IllegalArgumentException e) {
            // expected.
        } catch (UploadPersistenceException e) {
            fail("UploadPersistenceException should not be thrown because of invalid parameter.");
        }
    }

    /**
     * Set the behavior of createUpload. Give some valid parameters. See if the method can successfully add the Upload
     * object to persistence No exception should be thrown.
     */
    public void testCreateUpload_Accuracy1() {
        try {
            // Get a Upload.
            Upload upload = DeliverableTestHelper.getValidToPersistUpload();

            // Call the method.
            persistenceUploadManager.createUpload(upload, "user");

            // Check creationUser field.
            assertEquals("createUpload doesn't work properly", "user", persistence.getUpload().getCreationUser());

            // Check modificationUser field.
            assertEquals("createUpload doesn't work properly", "user", persistence.getUpload().getModificationUser());

            // Check modificationUser field. Since time is changing all the time, we assert it
            // to be not null is OK for test.
            assertNotNull("createUpload doesn't work properly", persistence.getUpload().getCreationTimestamp());
            assertNotNull("createUpload doesn't work properly", persistence.getUpload().getModificationTimestamp());

        } catch (UploadPersistenceException e) {
            fail("UploadPersistenceException should not be thrown because of valid parameter.");
        }
    }

    /**
     * Set the behavior of updateUpload. Set the 1th parameter to null. IllegalArgumentException should be thrown.
     */
    public void testUpdateUpload_Null1() {
        try {
            persistenceUploadManager.updateUpload(null, "user");
            fail("IllegalArgumentException should be thrown because of null parameter.");
        } catch (IllegalArgumentException e) {
            // expected.
        } catch (UploadPersistenceException e) {
            fail("UploadPersistenceException should not be thrown because of null parameter.");
        }
    }

    /**
     * Set the behavior of updateUpload. Set the 2th parameter to null. IllegalArgumentException should be thrown.
     */
    public void testUpdateUpload_Null2() {
        try {
            persistenceUploadManager.updateUpload(new Upload(), null);
            fail("IllegalArgumentException should be thrown because of null parameter.");
        } catch (IllegalArgumentException e) {
            // expected.
        } catch (UploadPersistenceException e) {
            fail("UploadPersistenceException should not be thrown because of null parameter.");
        }
    }

    /**
     * Set the behavior of updateUpload. Set the upload's id to UNSET_ID. Since upload's id should not be UNSET_ID,
     * IllegalArgumentException should be thrown.
     */
    public void testUpdateUpload_Invalid1() {
        try {
            persistenceUploadManager.updateUpload(new Upload(), "user");
            fail("IllegalArgumentException should be thrown because of this invalid parameter.");
        } catch (IllegalArgumentException e) {
            // expected.
        } catch (UploadPersistenceException e) {
            fail("UploadPersistenceException should not be thrown because of invalid parameter.");
        }
    }

    /**
     * Set the behavior of updateUpload. Give some valid parameters. See if the method can successfully add the Upload
     * object to persistence No exception should be thrown.
     */
    public void testUpdateUpload_Accuracy1() {
        try {
            // Get a Upload.
            Upload upload = DeliverableTestHelper.getValidToPersistUpload();

            // Create a upload first.
            persistenceUploadManager.createUpload(upload, "CreationUser");

            // Call the method.
            persistenceUploadManager.updateUpload(upload, "ModificationUser");

            // Check creationUser field.
            assertEquals("updateUpload doesn't work properly", "CreationUser", persistence.getUpload()
                    .getCreationUser());

            // Check modificationUser field.
            assertEquals("updateUpload doesn't work properly", "ModificationUser", persistence.getUpload()
                    .getModificationUser());

            // Check modificationUser field. Since time is changing all the time, we assert it
            // to be not null is OK for test.
            assertNotNull("updateUpload doesn't work properly", persistence.getUpload().getCreationTimestamp());
            assertNotNull("updateUpload doesn't work properly", persistence.getUpload().getModificationTimestamp());

        } catch (UploadPersistenceException e) {
            fail("UploadPersistenceException should not be thrown because of valid parameter.");
        }
    }

    /**
     * Set the behavior of removeUpload. Set the 1th parameter to null. IllegalArgumentException should be thrown.
     */
    public void testRemoveUpload_Null1() {
        try {
            persistenceUploadManager.removeUpload(null, "user");
            fail("IllegalArgumentException should be thrown because of null parameter.");
        } catch (IllegalArgumentException e) {
            // expected.
        } catch (UploadPersistenceException e) {
            fail("UploadPersistenceException should not be thrown because of null parameter.");
        }
    }

    /**
     * Set the behavior of removeUpload. Set the 2th parameter to null. IllegalArgumentException should be thrown.
     */
    public void testRemoveUpload_Null2() {
        try {
            persistenceUploadManager.removeUpload(new Upload(), null);
            fail("IllegalArgumentException should be thrown because of null parameter.");
        } catch (IllegalArgumentException e) {
            // expected.
        } catch (UploadPersistenceException e) {
            fail("UploadPersistenceException should not be thrown because of null parameter.");
        }
    }

    /**
     * Set the behavior of removeUpload. Set the upload's id to UNSET_ID. Since upload's id should not be UNSET_ID,
     * IllegalArgumentException should be thrown.
     */
    public void testRemoveUpload_Invalid1() {
        try {
            persistenceUploadManager.removeUpload(new Upload(), "user");
            fail("IllegalArgumentException should be thrown because of this invalid parameter.");
        } catch (IllegalArgumentException e) {
            // expected.
        } catch (UploadPersistenceException e) {
            fail("UploadPersistenceException should not be thrown because of invalid parameter.");
        }
    }

    /**
     * Set the behavior of removeUpload. Give some valid parameters. See if the method can successfully add the Upload
     * object to persistence No exception should be thrown.
     */
    public void testRemoveUpload_Accuracy1() {
        try {
            // Get a Upload.
            Upload upload = DeliverableTestHelper.getValidToPersistUpload();

            // Create a upload first.
            persistenceUploadManager.createUpload(upload, "CreationUser");

            // Call the method.
            persistenceUploadManager.removeUpload(upload, "ModificationUser");

            // Check creationUser field.
            assertEquals("removeUpload doesn't work properly", "CreationUser", persistence.getUpload()
                    .getCreationUser());

            // Check modificationUser field.
            assertEquals("removeUpload doesn't work properly", "ModificationUser", persistence.getUpload()
                    .getModificationUser());

            // Check modificationUser field. Since time is changing all the time, we assert it
            // to be not null is OK for test.
            assertNotNull("removeUpload doesn't work properly", persistence.getUpload().getCreationTimestamp());
            assertNotNull("removeUpload doesn't work properly", persistence.getUpload().getModificationTimestamp());

        } catch (UploadPersistenceException e) {
            fail("UploadPersistenceException should not be thrown because of valid parameter.");
        }
    }

    /**
     * Set the behavior of getUpload. Set the parameter = 0. IllegalArgumentException should be thrown.
     */
    public void testGetUpload_Invalid() {
        try {
            persistenceUploadManager.getUpload(0);
            fail("IllegalArgumentException should be thrown because of invalid parameter.");
        } catch (IllegalArgumentException e) {
            // expected.
        } catch (UploadPersistenceException e) {
            fail("UploadPersistenceException should not be thrown because of invalid parameter.");
        }
    }

    /**
     * Set the behavior of getUpload. Set the parameter = 111, then the id field of return Upload will be 111 too.
     */
    public void testGetUpload_Accuracy() {
        try {
            assertEquals("getUpload", 111, persistenceUploadManager.getUpload(111).getId());
        } catch (UploadPersistenceException e) {
            fail("UploadPersistenceException should not be thrown under this config.");
        }
    }

    /**
     * Set the behavior of searchUpload. Set the parameter = null. IllegalArgumentException should be thrown.
     */
    public void testSearchUpload_Null() {
        try {
            persistenceUploadManager.searchUploads(null);
            fail("IllegalArgumentException should be thrown because of null parameter.");
        } catch (IllegalArgumentException e) {
            // expected.
        } catch (UploadPersistenceException e) {
            fail("UploadPersistenceException should not be thrown because of null parameter.");
        } catch (SearchBuilderException e) {
            fail("SearchBuilderException should not be thrown because of null parameter.");
        }
    }

    /**
     * Set the behavior of createUploadType. Set the 1th parameter to null. IllegalArgumentException should be thrown.
     */
    public void testCreateUploadType_Null1() {
        try {
            persistenceUploadManager.createUploadType(null, "user");
            fail("IllegalArgumentException should be thrown because of null parameter.");
        } catch (IllegalArgumentException e) {
            // expected.
        } catch (UploadPersistenceException e) {
            fail("UploadPersistenceException should not be thrown because of null parameter.");
        }
    }

    /**
     * Set the behavior of createUploadType. Set the 2th parameter to null. IllegalArgumentException should be thrown.
     */
    public void testCreateUploadType_Null2() {
        try {
            persistenceUploadManager.createUploadType(new UploadType(), null);
            fail("IllegalArgumentException should be thrown because of null parameter.");
        } catch (IllegalArgumentException e) {
            // expected.
        } catch (UploadPersistenceException e) {
            fail("UploadPersistenceException should not be thrown because of null parameter.");
        }
    }

    /**
     * Set the behavior of createUploadType. Set the uploadType's id to 10. Since uploadType's id should be UNSET_ID,
     * IllegalArgumentException should be thrown.
     */
    public void testCreateUploadType_Invalid1() {
        try {
            persistenceUploadManager.createUploadType(new UploadType(10), "user");
            fail("IllegalArgumentException should be thrown because of this invalid parameter.");
        } catch (IllegalArgumentException e) {
            // expected.
        } catch (UploadPersistenceException e) {
            fail("UploadPersistenceException should not be thrown because of invalid parameter.");
        }
    }

    /**
     * Set the behavior of createUploadType. Give some valid parameters. See if the method can successfully add the
     * UploadType object to persistence No exception should be thrown.
     */
    public void testCreateUploadType_Accuracy1() {
        try {
            // Get a UploadType.
            UploadType uploadType = DeliverableTestHelper.getValidToPersistUploadType();

            // Call the method.
            persistenceUploadManager.createUploadType(uploadType, "user");

            // Check creationUser field.
            assertEquals("createUploadType doesn't work properly", "user", persistence.getUploadType()
                    .getCreationUser());

            // Check modificationUser field.
            assertEquals("createUploadType doesn't work properly", "user", persistence.getUploadType()
                    .getModificationUser());

            // Check modificationUser field. Since time is changing all the time, we assert it
            // to be not null is OK for test.
            assertNotNull("createUploadType doesn't work properly", persistence.getUploadType().getCreationTimestamp());
            assertNotNull("createUploadType doesn't work properly", persistence.getUploadType()
                    .getModificationTimestamp());

        } catch (UploadPersistenceException e) {
            fail("UploadPersistenceException should not be thrown because of valid parameter.");
        }
    }

    /**
     * Set the behavior of updateUploadType. Set the 1th parameter to null. IllegalArgumentException should be thrown.
     */
    public void testUpdateUploadType_Null1() {
        try {
            persistenceUploadManager.updateUploadType(null, "user");
            fail("IllegalArgumentException should be thrown because of null parameter.");
        } catch (IllegalArgumentException e) {
            // expected.
        } catch (UploadPersistenceException e) {
            fail("UploadPersistenceException should not be thrown because of null parameter.");
        }
    }

    /**
     * Set the behavior of updateUploadType. Set the 2th parameter to null. IllegalArgumentException should be thrown.
     */
    public void testUpdateUploadType_Null2() {
        try {
            persistenceUploadManager.updateUploadType(new UploadType(), null);
            fail("IllegalArgumentException should be thrown because of null parameter.");
        } catch (IllegalArgumentException e) {
            // expected.
        } catch (UploadPersistenceException e) {
            fail("UploadPersistenceException should not be thrown because of null parameter.");
        }
    }

    /**
     * Set the behavior of updateUploadType. Set the uploadType's id to UNSET_ID. Since uploadType's id should not be
     * UNSET_ID, IllegalArgumentException should be thrown.
     */
    public void testUpdateUploadType_Invalid1() {
        try {
            persistenceUploadManager.updateUploadType(new UploadType(), "user");
            fail("IllegalArgumentException should be thrown because of this invalid parameter.");
        } catch (IllegalArgumentException e) {
            // expected.
        } catch (UploadPersistenceException e) {
            fail("UploadPersistenceException should not be thrown because of invalid parameter.");
        }
    }

    /**
     * Set the behavior of updateUploadType. Give some valid parameters. See if the method can successfully add the
     * UploadType object to persistence No exception should be thrown.
     */
    public void testUpdateUploadType_Accuracy1() {
        try {
            // Get a UploadType.
            UploadType uploadType = DeliverableTestHelper.getValidToPersistUploadType();

            // Create a uploadType first.
            persistenceUploadManager.createUploadType(uploadType, "CreationUser");

            // Call the method.
            persistenceUploadManager.updateUploadType(uploadType, "ModificationUser");

            // Check creationUser field.
            assertEquals("updateUploadType doesn't work properly", "CreationUser", persistence.getUploadType()
                    .getCreationUser());

            // Check modificationUser field.
            assertEquals("updateUploadType doesn't work properly", "ModificationUser", persistence.getUploadType()
                    .getModificationUser());

            // Check modificationUser field. Since time is changing all the time, we assert it
            // to be not null is OK for test.
            assertNotNull("updateUploadType doesn't work properly", persistence.getUploadType().getCreationTimestamp());
            assertNotNull("updateUploadType doesn't work properly", persistence.getUploadType()
                    .getModificationTimestamp());

        } catch (UploadPersistenceException e) {
            fail("UploadPersistenceException should not be thrown because of valid parameter.");
        }
    }

    /**
     * Set the behavior of removeUploadType. Set the 1th parameter to null. IllegalArgumentException should be thrown.
     */
    public void testRemoveUploadType_Null1() {
        try {
            persistenceUploadManager.removeUploadType(null, "user");
            fail("IllegalArgumentException should be thrown because of null parameter.");
        } catch (IllegalArgumentException e) {
            // expected.
        } catch (UploadPersistenceException e) {
            fail("UploadPersistenceException should not be thrown because of null parameter.");
        }
    }

    /**
     * Set the behavior of removeUploadType. Set the 2th parameter to null. IllegalArgumentException should be thrown.
     */
    public void testRemoveUploadType_Null2() {
        try {
            persistenceUploadManager.removeUploadType(new UploadType(), null);
            fail("IllegalArgumentException should be thrown because of null parameter.");
        } catch (IllegalArgumentException e) {
            // expected.
        } catch (UploadPersistenceException e) {
            fail("UploadPersistenceException should not be thrown because of null parameter.");
        }
    }

    /**
     * Set the behavior of removeUploadType. Set the uploadType's id to UNSET_ID. Since uploadType's id should not be
     * UNSET_ID, IllegalArgumentException should be thrown.
     */
    public void testRemoveUploadType_Invalid1() {
        try {
            persistenceUploadManager.removeUploadType(new UploadType(), "user");
            fail("IllegalArgumentException should be thrown because of this invalid parameter.");
        } catch (IllegalArgumentException e) {
            // expected.
        } catch (UploadPersistenceException e) {
            fail("UploadPersistenceException should not be thrown because of invalid parameter.");
        }
    }

    /**
     * Set the behavior of removeUploadType. Give some valid parameters. See if the method can successfully add the
     * UploadType object to persistence No exception should be thrown.
     */
    public void testRemoveUploadType_Accuracy1() {
        try {
            // Get a UploadType.
            UploadType uploadType = DeliverableTestHelper.getValidToPersistUploadType();

            // Create a uploadType first.
            persistenceUploadManager.createUploadType(uploadType, "CreationUser");

            // Call the method.
            persistenceUploadManager.removeUploadType(uploadType, "ModificationUser");

            // Check creationUser field.
            assertEquals("removeUploadType doesn't work properly", "CreationUser", persistence.getUploadType()
                    .getCreationUser());

            // Check modificationUser field.
            assertEquals("removeUploadType doesn't work properly", "ModificationUser", persistence.getUploadType()
                    .getModificationUser());

            // Check modificationUser field. Since time is changing all the time, we assert it
            // to be not null is OK for test.
            assertNotNull("removeUploadType doesn't work properly", persistence.getUploadType().getCreationTimestamp());
            assertNotNull("removeUploadType doesn't work properly", persistence.getUploadType()
                    .getModificationTimestamp());

        } catch (UploadPersistenceException e) {
            fail("UploadPersistenceException should not be thrown because of valid parameter.");
        }
    }

    /**
     * Since the getAllUploadTypes totally invoke method in persistence, We just use MOCK persistence class to prove it
     * is invoked properly.
     */
    public void testGetAllUploadTypes_Accuracy() {
        UploadType uploadType = DeliverableTestHelper.getValidToPersistUploadType();
        uploadType.setId(2);
        try {
            assertEquals("getAllUploadStatus doesn't work.", uploadType.getId(), (persistenceUploadManager
                    .getAllUploadTypes())[0].getId());
        } catch (IllegalArgumentException e) {
            fail("IllegalArgumentException should not be thrown.");
        } catch (UploadPersistenceException e) {
            fail("UploadPersistenceException should not be thrown.");
        }
    }

    /**
     * Set the behavior of createUploadStatus. Set the 1th parameter to null. IllegalArgumentException should be thrown.
     */
    public void testCreateUploadStatus_Null1() {
        try {
            persistenceUploadManager.createUploadStatus(null, "user");
            fail("IllegalArgumentException should be thrown because of null parameter.");
        } catch (IllegalArgumentException e) {
            // expected.
        } catch (UploadPersistenceException e) {
            fail("UploadPersistenceException should not be thrown because of null parameter.");
        }
    }

    /**
     * Set the behavior of createUploadStatus. Set the 2th parameter to null. IllegalArgumentException should be thrown.
     */
    public void testCreateUploadStatus_Null2() {
        try {
            persistenceUploadManager.createUploadStatus(new UploadStatus(), null);
            fail("IllegalArgumentException should be thrown because of null parameter.");
        } catch (IllegalArgumentException e) {
            // expected.
        } catch (UploadPersistenceException e) {
            fail("UploadPersistenceException should not be thrown because of null parameter.");
        }
    }

    /**
     * Set the behavior of createUploadStatus. Set the uploadStatus's id to 10. Since uploadStatus's id should be
     * UNSET_ID, IllegalArgumentException should be thrown.
     */
    public void testCreateUploadStatus_Invalid1() {
        try {
            persistenceUploadManager.createUploadStatus(new UploadStatus(10), "user");
            fail("IllegalArgumentException should be thrown because of this invalid parameter.");
        } catch (IllegalArgumentException e) {
            // expected.
        } catch (UploadPersistenceException e) {
            fail("UploadPersistenceException should not be thrown because of invalid parameter.");
        }
    }

    /**
     * Set the behavior of createUploadStatus. Give some valid parameters. See if the method can successfully add the
     * UploadStatus object to persistence No exception should be thrown.
     */
    public void testCreateUploadStatus_Accuracy1() {
        try {
            // Get a UploadStatus.
            UploadStatus uploadStatus = DeliverableTestHelper.getValidToPersistUploadStatus();

            // Call the method.
            persistenceUploadManager.createUploadStatus(uploadStatus, "user");

            // Check creationUser field.
            assertEquals("createUploadStatus doesn't work properly", "user", persistence.getUploadStatus()
                    .getCreationUser());

            // Check modificationUser field.
            assertEquals("createUploadStatus doesn't work properly", "user", persistence.getUploadStatus()
                    .getModificationUser());

            // Check modificationUser field. Since time is changing all the time, we assert it
            // to be not null is OK for test.
            assertNotNull("createUploadStatus doesn't work properly", persistence.getUploadStatus()
                    .getCreationTimestamp());
            assertNotNull("createUploadStatus doesn't work properly", persistence.getUploadStatus()
                    .getModificationTimestamp());

        } catch (UploadPersistenceException e) {
            fail("UploadPersistenceException should not be thrown because of valid parameter.");
        }
    }

    /**
     * Set the behavior of updateUploadStatus. Set the 1th parameter to null. IllegalArgumentException should be thrown.
     */
    public void testUpdateUploadStatus_Null1() {
        try {
            persistenceUploadManager.updateUploadStatus(null, "user");
            fail("IllegalArgumentException should be thrown because of null parameter.");
        } catch (IllegalArgumentException e) {
            // expected.
        } catch (UploadPersistenceException e) {
            fail("UploadPersistenceException should not be thrown because of null parameter.");
        }
    }

    /**
     * Set the behavior of updateUploadStatus. Set the 2th parameter to null. IllegalArgumentException should be thrown.
     */
    public void testUpdateUploadStatus_Null2() {
        try {
            persistenceUploadManager.updateUploadStatus(new UploadStatus(), null);
            fail("IllegalArgumentException should be thrown because of null parameter.");
        } catch (IllegalArgumentException e) {
            // expected.
        } catch (UploadPersistenceException e) {
            fail("UploadPersistenceException should not be thrown because of null parameter.");
        }
    }

    /**
     * Set the behavior of updateUploadStatus. Set the uploadStatus's id to UNSET_ID. Since uploadStatus's id should not
     * be UNSET_ID, IllegalArgumentException should be thrown.
     */
    public void testUpdateUploadStatus_Invalid1() {
        try {
            persistenceUploadManager.updateUploadStatus(new UploadStatus(), "user");
            fail("IllegalArgumentException should be thrown because of this invalid parameter.");
        } catch (IllegalArgumentException e) {
            // expected.
        } catch (UploadPersistenceException e) {
            fail("UploadPersistenceException should not be thrown because of invalid parameter.");
        }
    }

    /**
     * Set the behavior of updateUploadStatus. Give some valid parameters. See if the method can successfully add the
     * UploadStatus object to persistence No exception should be thrown.
     */
    public void testUpdateUploadStatus_Accuracy1() {
        try {
            // Get a UploadStatus.
            UploadStatus uploadStatus = DeliverableTestHelper.getValidToPersistUploadStatus();

            // Create a uploadStatus first.
            persistenceUploadManager.createUploadStatus(uploadStatus, "CreationUser");

            // Call the method.
            persistenceUploadManager.updateUploadStatus(uploadStatus, "ModificationUser");

            // Check creationUser field.
            assertEquals("updateUploadStatus doesn't work properly", "CreationUser", persistence.getUploadStatus()
                    .getCreationUser());

            // Check modificationUser field.
            assertEquals("updateUploadStatus doesn't work properly", "ModificationUser", persistence.getUploadStatus()
                    .getModificationUser());

            // Check modificationUser field. Since time is changing all the time, we assert it
            // to be not null is OK for test.
            assertNotNull("updateUploadStatus doesn't work properly", persistence.getUploadStatus()
                    .getCreationTimestamp());
            assertNotNull("updateUploadStatus doesn't work properly", persistence.getUploadStatus()
                    .getModificationTimestamp());

        } catch (UploadPersistenceException e) {
            fail("UploadPersistenceException should not be thrown because of valid parameter.");
        }
    }

    /**
     * Set the behavior of removeUploadStatus. Set the 1th parameter to null. IllegalArgumentException should be thrown.
     */
    public void testRemoveUploadStatus_Null1() {
        try {
            persistenceUploadManager.removeUploadStatus(null, "user");
            fail("IllegalArgumentException should be thrown because of null parameter.");
        } catch (IllegalArgumentException e) {
            // expected.
        } catch (UploadPersistenceException e) {
            fail("UploadPersistenceException should not be thrown because of null parameter.");
        }
    }

    /**
     * Set the behavior of removeUploadStatus. Set the 2th parameter to null. IllegalArgumentException should be thrown.
     */
    public void testRemoveUploadStatus_Null2() {
        try {
            persistenceUploadManager.removeUploadStatus(new UploadStatus(), null);
            fail("IllegalArgumentException should be thrown because of null parameter.");
        } catch (IllegalArgumentException e) {
            // expected.
        } catch (UploadPersistenceException e) {
            fail("UploadPersistenceException should not be thrown because of null parameter.");
        }
    }

    /**
     * Set the behavior of removeUploadStatus. Set the uploadStatus's id to UNSET_ID. Since uploadStatus's id should not
     * be UNSET_ID, IllegalArgumentException should be thrown.
     */
    public void testRemoveUploadStatus_Invalid1() {
        try {
            persistenceUploadManager.removeUploadStatus(new UploadStatus(), "user");
            fail("IllegalArgumentException should be thrown because of this invalid parameter.");
        } catch (IllegalArgumentException e) {
            // expected.
        } catch (UploadPersistenceException e) {
            fail("UploadPersistenceException should not be thrown because of invalid parameter.");
        }
    }

    /**
     * Set the behavior of removeUploadStatus. Give some valid parameters. See if the method can successfully add the
     * UploadStatus object to persistence No exception should be thrown.
     */
    public void testRemoveUploadStatus_Accuracy1() {
        try {
            // Get a UploadStatus.
            UploadStatus uploadStatus = DeliverableTestHelper.getValidToPersistUploadStatus();

            // Create a uploadStatus first.
            persistenceUploadManager.createUploadStatus(uploadStatus, "CreationUser");

            // Call the method.
            persistenceUploadManager.removeUploadStatus(uploadStatus, "ModificationUser");

            // Check creationUser field.
            assertEquals("removeUploadStatus doesn't work properly", "CreationUser", persistence.getUploadStatus()
                    .getCreationUser());

            // Check modificationUser field.
            assertEquals("removeUploadStatus doesn't work properly", "ModificationUser", persistence.getUploadStatus()
                    .getModificationUser());

            // Check modificationUser field. Since time is changing all the time, we assert it
            // to be not null is OK for test.
            assertNotNull("removeUploadStatus doesn't work properly", persistence.getUploadStatus()
                    .getCreationTimestamp());
            assertNotNull("removeUploadStatus doesn't work properly", persistence.getUploadStatus()
                    .getModificationTimestamp());

        } catch (UploadPersistenceException e) {
            fail("UploadPersistenceException should not be thrown because of valid parameter.");
        }
    }

    /**
     * Since the getAllUploadStatuses totally invoke method in persistence, We just use MOCK persistence class to prove
     * it is invoked properly.
     */
    public void testGetAllUploadStatuses_Accuracy() {
        UploadStatus uploadStatus1 = DeliverableTestHelper.getValidToPersistUploadStatus();
        uploadStatus1.setId(2);
        try {
            assertEquals("getAllUploadStatus doesn't work.", uploadStatus1.getId(), (persistenceUploadManager
                    .getAllUploadStatuses())[0].getId());
        } catch (IllegalArgumentException e) {
            fail("IllegalArgumentException should not be thrown.");
        } catch (UploadPersistenceException e) {
            fail("UploadPersistenceException should not be thrown.");
        }
    }

    /**
     * Set the behavior of createSubmission. Set the 1th parameter to null. IllegalArgumentException should be thrown.
     */
    public void testCreateSubmission_Null1() {
        try {
            persistenceUploadManager.createSubmission(null, "user");
            fail("IllegalArgumentException should be thrown because of null parameter.");
        } catch (IllegalArgumentException e) {
            // expected.
        } catch (UploadPersistenceException e) {
            fail("UploadPersistenceException should not be thrown because of null parameter.");
        }
    }

    /**
     * Set the behavior of createSubmission. Set the 2th parameter to null. IllegalArgumentException should be thrown.
     */
    public void testCreateSubmission_Null2() {
        try {
            persistenceUploadManager.createSubmission(new Submission(), null);
            fail("IllegalArgumentException should be thrown because of null parameter.");
        } catch (IllegalArgumentException e) {
            // expected.
        } catch (UploadPersistenceException e) {
            fail("UploadPersistenceException should not be thrown because of null parameter.");
        }
    }

    /**
     * Set the behavior of createSubmission. Set the submission's id to 10. Since submission's id should be UNSET_ID,
     * IllegalArgumentException should be thrown.
     */
    public void testCreateSubmission_Invalid1() {
        try {
            persistenceUploadManager.createSubmission(new Submission(10), "user");
            fail("IllegalArgumentException should be thrown because of this invalid parameter.");
        } catch (IllegalArgumentException e) {
            // expected.
        } catch (UploadPersistenceException e) {
            fail("UploadPersistenceException should not be thrown because of invalid parameter.");
        }
    }

    /**
     * Set the behavior of createSubmission. Give some valid parameters. See if the method can successfully add the
     * Submission object to persistence No exception should be thrown.
     */
    public void testCreateSubmission_Accuracy1() {
        try {
            // Get a Submission.
            Submission submission = DeliverableTestHelper.getValidToPersistSubmission();

            // Call the method.
            persistenceUploadManager.createSubmission(submission, "user");

            // Check creationUser field.
            assertEquals("createSubmission doesn't work properly", "user", persistence.getSubmission()
                    .getCreationUser());

            // Check modificationUser field.
            assertEquals("createSubmission doesn't work properly", "user", persistence.getSubmission()
                    .getModificationUser());

            // Check modificationUser field. Since time is changing all the time, we assert it
            // to be not null is OK for test.
            assertNotNull("createSubmission doesn't work properly", persistence.getSubmission().getCreationTimestamp());
            assertNotNull("createSubmission doesn't work properly", persistence.getSubmission()
                    .getModificationTimestamp());

        } catch (UploadPersistenceException e) {
            fail("UploadPersistenceException should not be thrown because of valid parameter.");
        }
    }

    /**
     * Set the behavior of updateSubmission. Set the 1th parameter to null. IllegalArgumentException should be thrown.
     */
    public void testUpdateSubmission_Null1() {
        try {
            persistenceUploadManager.updateSubmission(null, "user");
            fail("IllegalArgumentException should be thrown because of null parameter.");
        } catch (IllegalArgumentException e) {
            // expected.
        } catch (UploadPersistenceException e) {
            fail("UploadPersistenceException should not be thrown because of null parameter.");
        }
    }

    /**
     * Set the behavior of updateSubmission. Set the 2th parameter to null. IllegalArgumentException should be thrown.
     */
    public void testUpdateSubmission_Null2() {
        try {
            persistenceUploadManager.updateSubmission(new Submission(), null);
            fail("IllegalArgumentException should be thrown because of null parameter.");
        } catch (IllegalArgumentException e) {
            // expected.
        } catch (UploadPersistenceException e) {
            fail("UploadPersistenceException should not be thrown because of null parameter.");
        }
    }

    /**
     * Set the behavior of updateSubmission. Set the submission's id to UNSET_ID. Since submission's id should not be
     * UNSET_ID, IllegalArgumentException should be thrown.
     */
    public void testUpdateSubmission_Invalid1() {
        try {
            persistenceUploadManager.updateSubmission(new Submission(), "user");
            fail("IllegalArgumentException should be thrown because of this invalid parameter.");
        } catch (IllegalArgumentException e) {
            // expected.
        } catch (UploadPersistenceException e) {
            fail("UploadPersistenceException should not be thrown because of invalid parameter.");
        }
    }

    /**
     * Set the behavior of updateSubmission. Give some valid parameters. See if the method can successfully add the
     * Submission object to persistence No exception should be thrown.
     */
    public void testUpdateSubmission_Accuracy1() {
        try {
            // Get a Submission.
            Submission submission = DeliverableTestHelper.getValidToPersistSubmission();

            // Create a submission first.
            persistenceUploadManager.createSubmission(submission, "CreationUser");

            // Call the method.
            persistenceUploadManager.updateSubmission(submission, "ModificationUser");

            // Check creationUser field.
            assertEquals("updateSubmission doesn't work properly", "CreationUser", persistence.getSubmission()
                    .getCreationUser());

            // Check modificationUser field.
            assertEquals("updateSubmission doesn't work properly", "ModificationUser", persistence.getSubmission()
                    .getModificationUser());

            // Check modificationUser field. Since time is changing all the time, we assert it
            // to be not null is OK for test.
            assertNotNull("updateSubmission doesn't work properly", persistence.getSubmission().getCreationTimestamp());
            assertNotNull("updateSubmission doesn't work properly", persistence.getSubmission()
                    .getModificationTimestamp());

        } catch (UploadPersistenceException e) {
            fail("UploadPersistenceException should not be thrown because of valid parameter.");
        }
    }

    /**
     * Set the behavior of removeSubmission. Set the 1th parameter to null. IllegalArgumentException should be thrown.
     */
    public void testRemoveSubmission_Null1() {
        try {
            persistenceUploadManager.removeSubmission(null, "user");
            fail("IllegalArgumentException should be thrown because of null parameter.");
        } catch (IllegalArgumentException e) {
            // expected.
        } catch (UploadPersistenceException e) {
            fail("UploadPersistenceException should not be thrown because of null parameter.");
        }
    }

    /**
     * Set the behavior of removeSubmission. Set the 2th parameter to null. IllegalArgumentException should be thrown.
     */
    public void testRemoveSubmission_Null2() {
        try {
            persistenceUploadManager.removeSubmission(new Submission(), null);
            fail("IllegalArgumentException should be thrown because of null parameter.");
        } catch (IllegalArgumentException e) {
            // expected.
        } catch (UploadPersistenceException e) {
            fail("UploadPersistenceException should not be thrown because of null parameter.");
        }
    }

    /**
     * Set the behavior of removeSubmission. Set the submission's id to UNSET_ID. Since submission's id should not be
     * UNSET_ID, IllegalArgumentException should be thrown.
     */
    public void testRemoveSubmission_Invalid1() {
        try {
            persistenceUploadManager.removeSubmission(new Submission(), "user");
            fail("IllegalArgumentException should be thrown because of this invalid parameter.");
        } catch (IllegalArgumentException e) {
            // expected.
        } catch (UploadPersistenceException e) {
            fail("UploadPersistenceException should not be thrown because of invalid parameter.");
        }
    }

    /**
     * Set the behavior of removeSubmission. Give some valid parameters. See if the method can successfully add the
     * Submission object to persistence No exception should be thrown.
     */
    public void testRemoveSubmission_Accuracy1() {
        try {
            // Get a Submission.
            Submission submission = DeliverableTestHelper.getValidToPersistSubmission();

            // Create a submission first.
            persistenceUploadManager.createSubmission(submission, "CreationUser");

            // Call the method.
            persistenceUploadManager.removeSubmission(submission, "ModificationUser");

            // Check creationUser field.
            assertEquals("removeSubmission doesn't work properly", "CreationUser", persistence.getSubmission()
                    .getCreationUser());

            // Check modificationUser field.
            assertEquals("removeSubmission doesn't work properly", "ModificationUser", persistence.getSubmission()
                    .getModificationUser());

            // Check modificationUser field. Since time is changing all the time, we assert it
            // to be not null is OK for test.
            assertNotNull("removeSubmission doesn't work properly", persistence.getSubmission().getCreationTimestamp());
            assertNotNull("removeSubmission doesn't work properly", persistence.getSubmission()
                    .getModificationTimestamp());

        } catch (UploadPersistenceException e) {
            fail("UploadPersistenceException should not be thrown because of valid parameter.");
        }
    }

    /**
     * Set the behavior of getSubmission. Set the parameter = 0. IllegalArgumentException should be thrown.
     */
    public void testGetSubmission_Invalid() {
        try {
            persistenceUploadManager.getUpload(0);
            fail("IllegalArgumentException should be thrown because of invalid parameter.");
        } catch (IllegalArgumentException e) {
            // expected.
        } catch (UploadPersistenceException e) {
            fail("UploadPersistenceException should not be thrown because of invalid parameter.");
        }
    }

    /**
     * Set the behavior of getSubmission. Set the parameter = 111, then the id field of return Submission will be 111
     * too.
     */
    public void testGetSubmission_Accuracy() {
        try {
            assertEquals("getSubmission doesn't work.", 111, persistenceUploadManager.getSubmission(111).getId());
        } catch (UploadPersistenceException e) {
            fail("UploadPersistenceException should not be thrown under this config.");
        }
    }

    /**
     * Set the behavior of searchSubmission. Set the parameter = null. IllegalArgumentException should be thrown.
     */
    public void testSearchSubmission_Null() {
        try {
            persistenceUploadManager.searchSubmissions(null);
            fail("IllegalArgumentException should be thrown because of null parameter.");
        } catch (IllegalArgumentException e) {
            // expected.
        } catch (SearchBuilderException e) {
            fail("SearchBuilderException should not be thrown because of null parameter.");
        } catch (UploadPersistenceException e) {
            fail("UploadPersistenceException should not be thrown because of null parameter.");
        }
    }

    /**
     * Set the behavior of createSubmissionStatus. Set the 1th parameter to null. IllegalArgumentException should be
     * thrown.
     */
    public void testCreateSubmissionStatus_Null1() {
        try {
            persistenceUploadManager.createSubmissionStatus(null, "user");
            fail("IllegalArgumentException should be thrown because of null parameter.");
        } catch (IllegalArgumentException e) {
            // expected.
        } catch (UploadPersistenceException e) {
            fail("UploadPersistenceException should not be thrown because of null parameter.");
        }
    }

    /**
     * Set the behavior of createSubmissionStatus. Set the 2th parameter to null. IllegalArgumentException should be
     * thrown.
     */
    public void testCreateSubmissionStatus_Null2() {
        try {
            persistenceUploadManager.createSubmissionStatus(new SubmissionStatus(), null);
            fail("IllegalArgumentException should be thrown because of null parameter.");
        } catch (IllegalArgumentException e) {
            // expected.
        } catch (UploadPersistenceException e) {
            fail("UploadPersistenceException should not be thrown because of null parameter.");
        }
    }

    /**
     * Set the behavior of createSubmissionStatus. Set the submissionStatus's id to 10. Since submissionStatus's id
     * should be UNSET_ID, IllegalArgumentException should be thrown.
     */
    public void testCreateSubmissionStatus_Invalid1() {
        try {
            persistenceUploadManager.createSubmissionStatus(new SubmissionStatus(10), "user");
            fail("IllegalArgumentException should be thrown because of this invalid parameter.");
        } catch (IllegalArgumentException e) {
            // expected.
        } catch (UploadPersistenceException e) {
            fail("UploadPersistenceException should not be thrown because of invalid parameter.");
        }
    }

    /**
     * Set the behavior of createSubmissionStatus. Give some valid parameters. See if the method can successfully add
     * the SubmissionStatus object to persistence No exception should be thrown.
     */
    public void testCreateSubmissionStatus_Accuracy1() {
        try {
            // Get a SubmissionStatus.
            SubmissionStatus submissionStatus = DeliverableTestHelper.getValidToPersistSubmissionStatus();

            // Call the method.
            persistenceUploadManager.createSubmissionStatus(submissionStatus, "user");

            // Check creationUser field.
            assertEquals("createSubmissionStatus doesn't work properly", "user", persistence.getSubmissionStatus()
                    .getCreationUser());

            // Check modificationUser field.
            assertEquals("createSubmissionStatus doesn't work properly", "user", persistence.getSubmissionStatus()
                    .getModificationUser());

            // Check modificationUser field. Since time is changing all the time, we assert it
            // to be not null is OK for test.
            assertNotNull("createSubmissionStatus doesn't work properly", persistence.getSubmissionStatus()
                    .getCreationTimestamp());
            assertNotNull("createSubmissionStatus doesn't work properly", persistence.getSubmissionStatus()
                    .getModificationTimestamp());

        } catch (UploadPersistenceException e) {
            fail("UploadPersistenceException should not be thrown because of valid parameter.");
        }
    }

    /**
     * Set the behavior of updateSubmissionStatus. Set the 1th parameter to null. IllegalArgumentException should be
     * thrown.
     */
    public void testUpdateSubmissionStatus_Null1() {
        try {
            persistenceUploadManager.updateSubmissionStatus(null, "user");
            fail("IllegalArgumentException should be thrown because of null parameter.");
        } catch (IllegalArgumentException e) {
            // expected.
        } catch (UploadPersistenceException e) {
            fail("UploadPersistenceException should not be thrown because of null parameter.");
        }
    }

    /**
     * Set the behavior of updateSubmissionStatus. Set the 2th parameter to null. IllegalArgumentException should be
     * thrown.
     */
    public void testUpdateSubmissionStatus_Null2() {
        try {
            persistenceUploadManager.updateSubmissionStatus(new SubmissionStatus(), null);
            fail("IllegalArgumentException should be thrown because of null parameter.");
        } catch (IllegalArgumentException e) {
            // expected.
        } catch (UploadPersistenceException e) {
            fail("UploadPersistenceException should not be thrown because of null parameter.");
        }
    }

    /**
     * Set the behavior of updateSubmissionStatus. Set the submissionStatus's id to UNSET_ID. Since submissionStatus's
     * id should not be UNSET_ID, IllegalArgumentException should be thrown.
     */
    public void testUpdateSubmissionStatus_Invalid1() {
        try {
            persistenceUploadManager.updateSubmissionStatus(new SubmissionStatus(), "user");
            fail("IllegalArgumentException should be thrown because of this invalid parameter.");
        } catch (IllegalArgumentException e) {
            // expected.
        } catch (UploadPersistenceException e) {
            fail("UploadPersistenceException should not be thrown because of invalid parameter.");
        }
    }

    /**
     * Set the behavior of updateSubmissionStatus. Give some valid parameters. See if the method can successfully add
     * the SubmissionStatus object to persistence No exception should be thrown.
     */
    public void testUpdateSubmissionStatus_Accuracy1() {
        try {
            // Get a SubmissionStatus.
            SubmissionStatus submissionStatus = DeliverableTestHelper.getValidToPersistSubmissionStatus();

            // Create a submissionStatus first.
            persistenceUploadManager.createSubmissionStatus(submissionStatus, "CreationUser");

            // Call the method.
            persistenceUploadManager.updateSubmissionStatus(submissionStatus, "ModificationUser");

            // Check creationUser field.
            assertEquals("updateSubmissionStatus doesn't work properly", "CreationUser", persistence
                    .getSubmissionStatus().getCreationUser());

            // Check modificationUser field.
            assertEquals("updateSubmissionStatus doesn't work properly", "ModificationUser", persistence
                    .getSubmissionStatus().getModificationUser());

            // Check modificationUser field. Since time is changing all the time, we assert it
            // to be not null is OK for test.
            assertNotNull("updateSubmissionStatus doesn't work properly", persistence.getSubmissionStatus()
                    .getCreationTimestamp());
            assertNotNull("updateSubmissionStatus doesn't work properly", persistence.getSubmissionStatus()
                    .getModificationTimestamp());

        } catch (UploadPersistenceException e) {
            fail("UploadPersistenceException should not be thrown because of valid parameter.");
        }
    }

    /**
     * Set the behavior of removeSubmissionStatus. Set the 1th parameter to null. IllegalArgumentException should be
     * thrown.
     */
    public void testRemoveSubmissionStatus_Null1() {
        try {
            persistenceUploadManager.removeSubmissionStatus(null, "user");
            fail("IllegalArgumentException should be thrown because of null parameter.");
        } catch (IllegalArgumentException e) {
            // expected.
        } catch (UploadPersistenceException e) {
            fail("UploadPersistenceException should not be thrown because of null parameter.");
        }
    }

    /**
     * Set the behavior of removeSubmissionStatus. Set the 2th parameter to null. IllegalArgumentException should be
     * thrown.
     */
    public void testRemoveSubmissionStatus_Null2() {
        try {
            persistenceUploadManager.removeSubmissionStatus(new SubmissionStatus(), null);
            fail("IllegalArgumentException should be thrown because of null parameter.");
        } catch (IllegalArgumentException e) {
            // expected.
        } catch (UploadPersistenceException e) {
            fail("UploadPersistenceException should not be thrown because of null parameter.");
        }
    }

    /**
     * Set the behavior of removeSubmissionStatus. Set the submissionStatus's id to UNSET_ID. Since submissionStatus's
     * id should not be UNSET_ID, IllegalArgumentException should be thrown.
     */
    public void testRemoveSubmissionStatus_Invalid1() {
        try {
            persistenceUploadManager.removeSubmissionStatus(new SubmissionStatus(), "user");
            fail("IllegalArgumentException should be thrown because of this invalid parameter.");
        } catch (IllegalArgumentException e) {
            // expected.
        } catch (UploadPersistenceException e) {
            fail("UploadPersistenceException should not be thrown because of invalid parameter.");
        }
    }

    /**
     * Set the behavior of removeSubmissionStatus. Give some valid parameters. See if the method can successfully add
     * the SubmissionStatus object to persistence No exception should be thrown.
     */
    public void testRemoveSubmissionStatus_Accuracy1() {
        try {
            // Get a SubmissionStatus.
            SubmissionStatus submissionStatus = DeliverableTestHelper.getValidToPersistSubmissionStatus();

            // Create a submissionStatus first.
            persistenceUploadManager.createSubmissionStatus(submissionStatus, "CreationUser");

            // Call the method.
            persistenceUploadManager.removeSubmissionStatus(submissionStatus, "ModificationUser");

            // Check creationUser field.
            assertEquals("removeSubmissionStatus doesn't work properly", "CreationUser", persistence
                    .getSubmissionStatus().getCreationUser());

            // Check modificationUser field.
            assertEquals("removeSubmissionStatus doesn't work properly", "ModificationUser", persistence
                    .getSubmissionStatus().getModificationUser());

            // Check modificationUser field. Since time is changing all the time, we assert it
            // to be not null is OK for test.
            assertNotNull("removeSubmissionStatus doesn't work properly", persistence.getSubmissionStatus()
                    .getCreationTimestamp());
            assertNotNull("removeSubmissionStatus doesn't work properly", persistence.getSubmissionStatus()
                    .getModificationTimestamp());

        } catch (UploadPersistenceException e) {
            fail("UploadPersistenceException should not be thrown because of valid parameter.");
        }
    }

    /**
     * Since the getAllSubmissionStatuses totally invoke method in persistence, We just use MOCK persistence class to
     * prove it is invoked properly.
     */
    public void testGetAllSubmissionStatuses_Accuracy() {
        SubmissionStatus submissionStatus = DeliverableTestHelper.getValidToPersistSubmissionStatus();
        submissionStatus.setId(2);
        try {
            assertEquals("getAllUploadStatus doesn't work.", submissionStatus.getId(), (persistenceUploadManager
                    .getAllSubmissionStatuses())[0].getId());
        } catch (IllegalArgumentException e) {
            fail("IllegalArgumentException should not be thrown.");
        } catch (UploadPersistenceException e) {
            fail("UploadPersistenceException should not be thrown.");
        }
    }

    /**
     * Tests searchUploads method with upload id filter.
     *
     * @throws Exception
     *             pass to JUnit.
     */
    public void testSearchUploadsByUploadId() throws Exception {
        try {
            DeliverableTestHelper.executeBatch(CLEAR_DB_SQL);
            DeliverableTestHelper.executeBatch(INIT_DB_SQL);

            for (int i = 1; i <= 5; ++i) {
                Upload[] uploads = persistenceUploadManager.searchUploads(UploadFilterBuilder.createUploadIdFilter(i));
                assertEquals("Wrong number of uploads retrieved", 1, uploads.length);
                assertEquals("Wrong upload retrieved", i, uploads[0].getId());
            }

            assertTrue("Persistence method not called correctly", SqlUploadPersistence.getLastCalled().startsWith(
                    "loadUploads"));
        } finally {
            DeliverableTestHelper.executeBatch(CLEAR_DB_SQL);
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
            DeliverableTestHelper.executeBatch(CLEAR_DB_SQL);
            DeliverableTestHelper.executeBatch(INIT_DB_SQL);

            Upload[] uploads = persistenceUploadManager.searchUploads(UploadFilterBuilder.createUploadTypeIdFilter(3));
            assertEquals("Wrong number of uploads retrieved", 2, uploads.length);

            Set<Long> ids = new HashSet<Long>();

            for (int i = 0; i < uploads.length; ++i) {
                ids.add(new Long(uploads[i].getId()));
            }

            assertTrue("Wrong upload retrieved", ids.contains(new Long(2)));
            assertTrue("Wrong upload retrieved", ids.contains(new Long(3)));

            assertTrue("Persistence method not called correctly", SqlUploadPersistence.getLastCalled().startsWith(
                    "loadUploads"));
        } finally {
            DeliverableTestHelper.executeBatch(CLEAR_DB_SQL);
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
            DeliverableTestHelper.executeBatch(CLEAR_DB_SQL);
            DeliverableTestHelper.executeBatch(INIT_DB_SQL);

            Upload[] uploads = persistenceUploadManager
                    .searchUploads(UploadFilterBuilder.createUploadStatusIdFilter(1));
            assertEquals("Wrong number of uploads retrieved", 3, uploads.length);

            Set<Long> ids = new HashSet<Long>();

            for (int i = 0; i < uploads.length; ++i) {
                ids.add(new Long(uploads[i].getId()));
            }

            assertTrue("Wrong upload retrieved", ids.contains(new Long(1)));
            assertTrue("Wrong upload retrieved", ids.contains(new Long(3)));
            assertTrue("Wrong upload retrieved", ids.contains(new Long(4)));

            assertTrue("Persistence method not called correctly", SqlUploadPersistence.getLastCalled().startsWith(
                    "loadUploads"));
        } finally {
            DeliverableTestHelper.executeBatch(CLEAR_DB_SQL);
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
            DeliverableTestHelper.executeBatch(CLEAR_DB_SQL);
            DeliverableTestHelper.executeBatch(INIT_DB_SQL);

            Upload[] uploads = persistenceUploadManager.searchUploads(UploadFilterBuilder.createProjectIdFilter(1));
            assertEquals("Wrong number of uploads retrieved", 2, uploads.length);

            Set<Long> ids = new HashSet<Long>();

            for (int i = 0; i < uploads.length; ++i) {
                ids.add(new Long(uploads[i].getId()));
            }

            assertTrue("Wrong upload retrieved", ids.contains(new Long(1)));
            assertTrue("Wrong upload retrieved", ids.contains(new Long(4)));

            assertTrue("Persistence method not called correctly", SqlUploadPersistence.getLastCalled().startsWith(
                    "loadUploads"));
        } finally {
            DeliverableTestHelper.executeBatch(CLEAR_DB_SQL);
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
            DeliverableTestHelper.executeBatch(CLEAR_DB_SQL);
            DeliverableTestHelper.executeBatch(INIT_DB_SQL);

            Upload[] uploads = persistenceUploadManager.searchUploads(UploadFilterBuilder.createResourceIdFilter(2));
            assertEquals("Wrong number of uploads retrieved", 2, uploads.length);

            Set<Long> ids = new HashSet<Long>();

            for (int i = 0; i < uploads.length; ++i) {
                ids.add(new Long(uploads[i].getId()));
            }

            assertTrue("Wrong upload retrieved", ids.contains(new Long(2)));
            assertTrue("Wrong upload retrieved", ids.contains(new Long(5)));

            assertTrue("Persistence method not called correctly", SqlUploadPersistence.getLastCalled().startsWith(
                    "loadUploads"));
        } finally {
            DeliverableTestHelper.executeBatch(CLEAR_DB_SQL);
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
            DeliverableTestHelper.executeBatch(CLEAR_DB_SQL);
            DeliverableTestHelper.executeBatch(INIT_DB_SQL);

            for (int i = 1; i <= 5; ++i) {
                Submission[] submissions = persistenceUploadManager.searchSubmissions(SubmissionFilterBuilder
                        .createSubmissionIdFilter(i));
                assertEquals("Wrong number of submissions retrieved", 1, submissions.length);
                assertEquals("Wrong submission retrieved", i, submissions[0].getId());
            }

            assertTrue("Persistence method not called correctly", SqlUploadPersistence.getLastCalled().startsWith(
                    "loadSubmissions"));
        } finally {
            DeliverableTestHelper.executeBatch(CLEAR_DB_SQL);
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
            DeliverableTestHelper.executeBatch(CLEAR_DB_SQL);
            DeliverableTestHelper.executeBatch(INIT_DB_SQL);

            Submission[] submissions = persistenceUploadManager.searchSubmissions(SubmissionFilterBuilder
                    .createUploadIdFilter(1));
            assertEquals("Wrong number of submissions retrieved", 1, submissions.length);

            Set<Long> ids = new HashSet<Long>();

            for (int i = 0; i < submissions.length; ++i) {
                ids.add(new Long(submissions[i].getId()));
            }

            assertTrue("Wrong submission retrieved", ids.contains(new Long(1)));

            assertTrue("Persistence method not called correctly", SqlUploadPersistence.getLastCalled().startsWith(
                    "loadSubmissions"));
        } finally {
            DeliverableTestHelper.executeBatch(CLEAR_DB_SQL);
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
            DeliverableTestHelper.executeBatch(CLEAR_DB_SQL);
            DeliverableTestHelper.executeBatch(INIT_DB_SQL);

            Submission[] submissions = persistenceUploadManager.searchSubmissions(SubmissionFilterBuilder
                    .createSubmissionStatusIdFilter(4));
            assertEquals("Wrong number of submissions retrieved", 3, submissions.length);

            Set<Long> ids = new HashSet<Long>();

            for (int i = 0; i < submissions.length; ++i) {
                ids.add(new Long(submissions[i].getId()));
            }

            assertTrue("Wrong submission retrieved", ids.contains(new Long(1)));
            assertTrue("Wrong submission retrieved", ids.contains(new Long(4)));
            assertTrue("Wrong submission retrieved", ids.contains(new Long(5)));

            assertTrue("Persistence method not called correctly", SqlUploadPersistence.getLastCalled().startsWith(
                    "loadSubmissions"));
        } finally {
            DeliverableTestHelper.executeBatch(CLEAR_DB_SQL);
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
            DeliverableTestHelper.executeBatch(CLEAR_DB_SQL);
            DeliverableTestHelper.executeBatch(INIT_DB_SQL);

            Submission[] submissions = persistenceUploadManager.searchSubmissions(SubmissionFilterBuilder
                    .createProjectIdFilter(1));
            assertEquals("Wrong number of submissions retrieved", 2, submissions.length);

            Set<Long> ids = new HashSet<Long>();

            for (int i = 0; i < submissions.length; ++i) {
                ids.add(new Long(submissions[i].getId()));
            }

            assertTrue("Wrong submission retrieved", ids.contains(new Long(1)));
            assertTrue("Wrong submission retrieved", ids.contains(new Long(4)));

            assertTrue("Persistence method not called correctly", SqlUploadPersistence.getLastCalled().startsWith(
                    "loadSubmissions"));
        } finally {
            DeliverableTestHelper.executeBatch(CLEAR_DB_SQL);
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
            DeliverableTestHelper.executeBatch(CLEAR_DB_SQL);
            DeliverableTestHelper.executeBatch(INIT_DB_SQL);

            Submission[] submissions = persistenceUploadManager.searchSubmissions(SubmissionFilterBuilder
                    .createResourceIdFilter(2));
            assertEquals("Wrong number of submissions retrieved", 2, submissions.length);

            Set<Long> ids = new HashSet<Long>();

            for (int i = 0; i < submissions.length; ++i) {
                ids.add(new Long(submissions[i].getId()));
            }

            assertTrue("Wrong submission retrieved", ids.contains(new Long(2)));
            assertTrue("Wrong submission retrieved", ids.contains(new Long(3)));

            assertTrue("Persistence method not called correctly", SqlUploadPersistence.getLastCalled().startsWith(
                    "loadSubmissions"));
        } finally {
            DeliverableTestHelper.executeBatch(CLEAR_DB_SQL);
        }
    }

    /**
     * <p>
     * Accuracy test for the method <code>createSubmissionType(SubmissionType submissionType,
     * String operator)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     * @since 1.1
     */
    public void test_createSubmissionType() throws Exception {
        // Get a SubmissionType
        SubmissionType submissionType = DeliverableTestHelper.getValidToPersistSubmissionType();

        // Call the method.
        persistenceUploadManager.createSubmissionType(submissionType, "user");

        // Check creationUser field.
        assertEquals("'createSubmissionType' doesn't work properly.", "user", persistence.getSubmissionType()
                .getCreationUser());

        // Check modificationUser field.
        assertEquals("'createSubmissionType' doesn't work properly.", "user", persistence.getSubmissionType()
                .getModificationUser());

        // Check modificationUser field. Since time is changing all the time, we assert it
        // to be not null is OK for test.
        assertNotNull("'createSubmissionType' doesn't work properly.", persistence.getSubmissionType()
                .getCreationTimestamp());
        assertNotNull("'createSubmissionType' doesn't work properly.", persistence.getSubmissionType()
                .getModificationTimestamp());

        // Check the persistence method is called correctly
        assertEquals("'createSubmissionType' doesn't work properly.", "add submission type", persistence
                .getSubmissionType().getDescription());
    }

    /**
     * <p>
     * Failure test for the method <code>createSubmissionType(SubmissionType submissionType, String operator)</code>
     * with submissionType is <code>null</code>.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     * @since 1.1
     */
    public void test_createSubmissionType_submissionTypeIdNull() throws Exception {
        try {
            persistenceUploadManager.createSubmissionType(null, "user");

            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // Good
        }
    }

    /**
     * <p>
     * Failure test for the method <code>createSubmissionType(SubmissionType submissionType, String operator)</code>
     * with submissionType id is not UNSET_ID.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     * @since 1.1
     */
    public void test_createSubmissionType_submissionTypeIdInvalid() throws Exception {
        try {
            persistenceUploadManager.createSubmissionType(new SubmissionType(10), "user");

            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // Good
        }
    }

    /**
     * <p>
     * Failure test for the method <code>createSubmissionType(SubmissionType submissionType, String operator)</code>
     * with operator is <code>null</code>.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     * @since 1.1
     */
    public void test_createSubmissionType_operatorNull() throws Exception {
        try {
            persistenceUploadManager.createSubmissionType(new SubmissionType(), null);

            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // Good
        }
    }

    /**
     * <p>
     * Accuracy test for the method <code>updateSubmissionType(SubmissionType submissionType,
     * String operator)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     * @since 1.1
     */
    public void test_updateSubmissionType() throws Exception {
        // Get a SubmissionType
        SubmissionType submissionType = DeliverableTestHelper.getValidToPersistSubmissionType();

        // Create a submissionType first.
        persistenceUploadManager.createSubmissionType(submissionType, "CreationUser");

        // Call the method.
        persistenceUploadManager.updateSubmissionType(submissionType, "ModificationUser");

        // Check creationUser field.
        assertEquals("'updateSubmissionType' doesn't work properly.", "CreationUser", persistence.getSubmissionType()
                .getCreationUser());

        // Check modificationUser field.
        assertEquals("'updateSubmissionType' doesn't work properly.", "ModificationUser", persistence
                .getSubmissionType().getModificationUser());

        // Check modificationUser field. Since time is changing all the time, we assert it
        // to be not null is OK for test.
        assertNotNull("'updateSubmissionType' doesn't work properly.", persistence.getSubmissionType()
                .getCreationTimestamp());
        assertNotNull("'updateSubmissionType' doesn't work properly.", persistence.getSubmissionType()
                .getModificationTimestamp());

        // Check the persistence method is called correctly
        assertEquals("'updateSubmissionType' doesn't work properly.", "update submission type", persistence
                .getSubmissionType().getDescription());
    }

    /**
     * <p>
     * Failure test for the method <code>updateSubmissionType(SubmissionType submissionType, String operator)</code>
     * with submissionType is <code>null</code>.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     * @since 1.1
     */
    public void test_updateSubmissionType_submissionTypeIdNull() throws Exception {
        try {
            persistenceUploadManager.updateSubmissionType(null, "user");

            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // Good
        }
    }

    /**
     * <p>
     * Failure test for the method <code>updateSubmissionType(SubmissionType submissionType, String operator)</code>
     * with submissionType id is not UNSET_ID.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     * @since 1.1
     */
    public void test_updateSubmissionType_submissionTypeIdInvalid() throws Exception {
        try {
            persistenceUploadManager.updateSubmissionType(new SubmissionType(10), "user");

            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // Good
        }
    }

    /**
     * <p>
     * Failure test for the method <code>updateSubmissionType(SubmissionType submissionType, String operator)</code>
     * with operator is <code>null</code>.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     * @since 1.1
     */
    public void test_updateSubmissionType_operatorNull() throws Exception {
        try {
            persistenceUploadManager.updateSubmissionType(new SubmissionType(), null);

            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // Good
        }
    }

    /**
     * <p>
     * Accuracy test for the method <code>removeSubmissionType(SubmissionType submissionType,
     * String operator)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     * @since 1.1
     */
    public void test_removeSubmissionType() throws Exception {
        // Get a SubmissionType
        SubmissionType submissionType = DeliverableTestHelper.getValidToPersistSubmissionType();

        // Create a submissionType first.
        persistenceUploadManager.createSubmissionType(submissionType, "CreationUser");

        // Call the method.
        persistenceUploadManager.removeSubmissionType(submissionType, "ModificationUser");

        // Check creationUser field.
        assertEquals("'removeSubmissionType' doesn't work properly.", "CreationUser", persistence.getSubmissionType()
                .getCreationUser());

        // Check modificationUser field. Since time is changing all the time, we assert it
        // to be not null is OK for test.
        assertNotNull("'removeSubmissionType' doesn't work properly.", persistence.getSubmissionType()
                .getCreationTimestamp());
        assertNotNull("'removeSubmissionType' doesn't work properly.", persistence.getSubmissionType()
                .getModificationTimestamp());

        // Check the persistence method is called correctly
        assertEquals("'removeSubmissionType' doesn't work properly.", "remove submission type", persistence
                .getSubmissionType().getDescription());
    }

    /**
     * <p>
     * Failure test for the method <code>removeSubmissionType(SubmissionType submissionType, String operator)</code>
     * with submissionType is <code>null</code>.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     * @since 1.1
     */
    public void test_removeSubmissionType_submissionTypeIdNull() throws Exception {
        try {
            persistenceUploadManager.removeSubmissionType(null, "user");

            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // Good
        }
    }

    /**
     * <p>
     * Failure test for the method <code>removeSubmissionType(SubmissionType submissionType, String operator)</code>
     * with submissionType id is not UNSET_ID.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     * @since 1.1
     */
    public void test_removeSubmissionType_submissionTypeIdInvalid() throws Exception {
        try {
            persistenceUploadManager.removeSubmissionType(new SubmissionType(10), "user");

            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // Good
        }
    }

    /**
     * <p>
     * Failure test for the method <code>removeSubmissionType(SubmissionType submissionType, String operator)</code>
     * with operator is <code>null</code>.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     * @since 1.1
     */
    public void test_removeSubmissionType_operatorNull() throws Exception {
        try {
            persistenceUploadManager.removeSubmissionType(new SubmissionType(), null);

            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // Good
        }
    }

    /**
     * <p>
     * Accuracy test for the method <code>getAllSubmissionTypes</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     * @since 1.1
     */
    public void test_getAllSubmissionTypes() throws Exception {
        assertEquals("getAllSubmissionTypes doesn't work.", 2, (persistenceUploadManager.getAllSubmissionTypes())[0]
                .getId());
    }

    /**
     * <p>
     * Tests the <code>createSubmissionImage(SubmissionImage, String)</code> method.
     * </p>
     * <p>
     * If the submissionImage is null, should throw IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     * @since 1.2
     */
    public void testCreateSubmissionImage_submissionImage_null() throws Exception {
        try {
            persistenceUploadManager.createSubmissionImage(null, "name");

            fail("expect IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests the <code>createSubmissionImage(SubmissionImage, String)</code> method.
     * </p>
     * <p>
     * If the submissionImage is invalid to persist, should throw IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     * @since 1.2
     */
    public void testCreateSubmissionImage_submissionImage_invalidToPersist() throws Exception {
        try {
            persistenceUploadManager.createSubmissionImage(new SubmissionImage(), "name");

            fail("expect IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests the <code>createSubmissionImage(SubmissionImage, String)</code> method.
     * </p>
     * <p>
     * If the operator is null, should throw IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     * @since 1.2
     */
    public void testCreateSubmissionImage_null() throws Exception {
        SubmissionImage submissionImage = new SubmissionImage();
        submissionImage.setSubmissionId(1);
        submissionImage.setImageId(1);
        try {
            persistenceUploadManager.createSubmissionImage(submissionImage, null);

            fail("expect IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests the <code>createSubmissionImage(SubmissionImage, String)</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     * @since 1.2
     */
    public void testCreateSubmissionImage_accuracy() throws Exception {
        SubmissionImage submissionImage = new SubmissionImage();
        submissionImage.setSubmissionId(1);
        submissionImage.setImageId(1);

        persistenceUploadManager.createSubmissionImage(submissionImage, "operator");

        assertSame("'createSubmissionImage' doesn't work properly.", submissionImage, persistence.getSubmissionImage());
        assertNotNull("The create date should be set.", persistence.getSubmissionImage().getCreateDate());
        assertNotNull("The modify date should be set", persistence.getSubmissionImage().getModifyDate());
    }

    /**
     * <p>
     * Tests the <code>updateSubmissionImage(SubmissionImage, String)</code> method.
     * </p>
     * <p>
     * If the submissionImage is null, should throw IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     * @since 1.2
     */
    public void testUpdateSubmissionImage_submissionImage_null() throws Exception {
        try {
            persistenceUploadManager.updateSubmissionImage(null, "name");

            fail("expect IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests the <code>updateSubmissionImage(SubmissionImage, String)</code> method.
     * </p>
     * <p>
     * If the submissionImage is invalid to persist, should throw IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     * @since 1.2
     */
    public void testUpdateSubmissionImage_submissionImage_invalidToPersist() throws Exception {
        try {
            persistenceUploadManager.updateSubmissionImage(new SubmissionImage(), "name");

            fail("expect IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests the <code>updateSubmissionImage(SubmissionImage, String)</code> method.
     * </p>
     * <p>
     * If the operator is null, should throw IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     * @since 1.2
     */
    public void testUpdateSubmissionImage_null() throws Exception {
        SubmissionImage submissionImage = new SubmissionImage();
        submissionImage.setSubmissionId(1);
        submissionImage.setImageId(1);
        try {
            persistenceUploadManager.updateSubmissionImage(submissionImage, null);

            fail("expect IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests the <code>updateSubmissionImage(SubmissionImage, String)</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     * @since 1.2
     */
    public void testUpdateSubmissionImage_accuracy() throws Exception {
        SubmissionImage submissionImage = new SubmissionImage();
        submissionImage.setSubmissionId(1);
        submissionImage.setImageId(1);
        Date now = new Date();
        submissionImage.setCreateDate(now);
        submissionImage.setModifyDate(now);

        persistenceUploadManager.updateSubmissionImage(submissionImage, "operator");

        assertSame("'updateSubmissionImage' doesn't work properly.", submissionImage, persistence.getSubmissionImage());
        assertSame("The create date should be set.", now, persistence.getSubmissionImage().getCreateDate());
        assertNotSame("The modify date should be updated", now, persistence.getSubmissionImage().getModifyDate());
    }

    /**
     * <p>
     * Tests the <code>removeSubmissionImage(SubmissionImage, String)</code> method.
     * </p>
     * <p>
     * If the submissionImage is null, should throw IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     * @since 1.2
     */
    public void testRemoveSubmissionImage_submissionImage_null() throws Exception {
        try {
            persistenceUploadManager.removeSubmissionImage(null, "name");

            fail("expect IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests the <code>removeSubmissionImage(SubmissionImage, String)</code> method.
     * </p>
     * <p>
     * If the submissionImage's submission id is negative, should throw IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     * @since 1.2
     */
    public void testRemoveSubmissionImage_submissionImage_submissionId_negative() throws Exception {
        SubmissionImage submissionImage = new SubmissionImage();
        submissionImage.setImageId(1);
        submissionImage.setSubmissionId(-1);

        try {
            persistenceUploadManager.removeSubmissionImage(submissionImage, "name");

            fail("expect IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests the <code>removeSubmissionImage(SubmissionImage, String)</code> method.
     * </p>
     * <p>
     * If the submissionImage's submission id is zero, should throw IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     * @since 1.2
     */
    public void testRemoveSubmissionImage_submissionImage_submissionId_zero() throws Exception {
        SubmissionImage submissionImage = new SubmissionImage();
        submissionImage.setImageId(1);
        submissionImage.setSubmissionId(0);

        try {
            persistenceUploadManager.removeSubmissionImage(submissionImage, "name");

            fail("expect IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests the <code>removeSubmissionImage(SubmissionImage, String)</code> method.
     * </p>
     * <p>
     * If the submissionImage's image id is negative, should throw IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     * @since 1.2
     */
    public void testRemoveSubmissionImage_submissionImage_imageId_negative() throws Exception {
        SubmissionImage submissionImage = new SubmissionImage();
        submissionImage.setImageId(-1);
        submissionImage.setSubmissionId(1);

        try {
            persistenceUploadManager.removeSubmissionImage(submissionImage, "name");

            fail("expect IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests the <code>removeSubmissionImage(SubmissionImage, String)</code> method.
     * </p>
     * <p>
     * If the submissionImage's image id is zero, should throw IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     * @since 1.2
     */
    public void testRemoveSubmissionImage_submissionImage_imageId_zero() throws Exception {
        SubmissionImage submissionImage = new SubmissionImage();
        submissionImage.setImageId(0);
        submissionImage.setSubmissionId(1);

        try {
            persistenceUploadManager.removeSubmissionImage(submissionImage, "name");

            fail("expect IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests the <code>removeSubmissionImage(SubmissionImage, String)</code> method.
     * </p>
     * <p>
     * If the operator is null, should throw IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     * @since 1.2
     */
    public void testRemoveSubmissionImage_operator_null() throws Exception {
        SubmissionImage submissionImage = new SubmissionImage();
        submissionImage.setSubmissionId(1);
        submissionImage.setImageId(1);
        try {
            persistenceUploadManager.removeSubmissionImage(submissionImage, null);

            fail("expect IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests the <code>removeSubmissionImage(SubmissionImage, String)</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     * @since 1.2
     */
    public void testRemoveSubmissionImage_accuracy() throws Exception {
        SubmissionImage submissionImage = new SubmissionImage();
        submissionImage.setSubmissionId(1);
        submissionImage.setImageId(1);
        Date now = new Date();
        submissionImage.setCreateDate(now);
        submissionImage.setModifyDate(now);

        persistenceUploadManager.removeSubmissionImage(submissionImage, "operator");

        assertSame("'removeSubmissionImage' doesn't work properly.", submissionImage, persistence.getSubmissionImage());
    }

    /**
     * <p>
     * Tests the <code>getMimeType(long)</code> method.
     * </p>
     * <p>
     * If the id is negative, should throw IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     * @since 1.2
     */
    public void testGetMimeType_negative() throws Exception {
        try {
            persistenceUploadManager.getMimeType(-1);

            fail("expect IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests the <code>getMimeType(long)</code> method.
     * </p>
     * <p>
     * If the id is zero, should throw IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     * @since 1.2
     */
    public void testGetMimeType_zero() throws Exception {
        try {
            persistenceUploadManager.getMimeType(0);

            fail("expect IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests the <code>getMimeType(long)</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     * @since 1.2
     */
    public void testGetMimeType_accuracy() throws Exception {
        long id = 1;
        MimeType mimeType = persistenceUploadManager.getMimeType(1);

        assertNotNull("getMimeType does not work", mimeType);
        assertEquals("incorrect data", id, mimeType.getId());
    }

    /**
     * <p>
     * Tests the <code>getAllMimeTypes()</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     * @since 1.2
     */
    public void testGetAllMimeTypes_accuracy() throws Exception {
        assertEquals("getAllMimeTypes doesn't work.", 2, (persistenceUploadManager.getAllMimeTypes())[0].getId());
    }


    /**
     * <p>
     * Tests the <code>getUserSubmissionsForProject(long, long)</code> method.
     * </p>
     * <p>
     * If the project id is negative, should throw IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     * @since 1.2
     */
    public void testGetUserSubmissionsForProject_projectId_negative() throws Exception {
        try {
            persistenceUploadManager.getUserSubmissionsForProject(-1, 1);

            fail("expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests the <code>getUserSubmissionsForProject(long, long)</code> method.
     * </p>
     * <p>
     * If the project id is zero, should throw IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     * @since 1.2
     */
    public void testGetUserSubmissionsForProject_projectId_zero() throws Exception {
        try {
            persistenceUploadManager.getUserSubmissionsForProject(0, 1);

            fail("expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests the <code>getUserSubmissionsForProject(long, long)</code> method.
     * </p>
     * <p>
     * If the owner id is negative, should throw IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     * @since 1.2
     */
    public void testGetUserSubmissionsForProject_ownerId_negative() throws Exception {
        try {
            persistenceUploadManager.getUserSubmissionsForProject(1, -1);

            fail("expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests the <code>getUserSubmissionsForProject(long, long)</code> method.
     * </p>
     * <p>
     * If the owner id is zero, should throw IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     * @since 1.2
     */
    public void testGetUserSubmissionsForProject_ownerId_zero() throws Exception {
        try {
            persistenceUploadManager.getUserSubmissionsForProject(1, 0);

            fail("expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
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
    public void testGetUserSubmissionsForProject_accuracy() throws Exception {
        Submission[] submissions = persistenceUploadManager.getUserSubmissionsForProject(1, 1);

        assertNotNull("the return should not be null.", submissions);
        assertEquals("The array should be empty.", 0, submissions.length);
    }

    /**
     * <p>
     * Tests the <code>getProjectSubmissions(long)</code> method.
     * </p>
     * <p>
     * If the project id is negative, should throw IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     * @since 1.2
     */
    public void testGetProjectSubmissions_projectId_negative() throws Exception {
        try {
            persistenceUploadManager.getProjectSubmissions(-1);

            fail("expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests the <code>getProjectSubmissions(long)</code> method.
     * </p>
     * <p>
     * If the project id is zero, should throw IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     * @since 1.2
     */
    public void testGetProjectSubmissions_projectId_zero() throws Exception {
        try {
            persistenceUploadManager.getProjectSubmissions(0);

            fail("expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
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
    public void testGetProjectSubmissions_accuracy() throws Exception {
        Submission[] submissions = persistenceUploadManager.getProjectSubmissions(1);

        assertNotNull("the return should not be null.", submissions);
        assertEquals("The array should be empty.", 0, submissions.length);
    }

    /**
     * <p>
     * Tests the <code>getImagesForSubmission(long)</code> method.
     * </p>
     * <p>
     * If the submission id is negative, should throw IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     * @since 1.2
     */
    public void testGetImagesForSubmission_submissionId_negative() throws Exception {
        try {
            persistenceUploadManager.getImagesForSubmission(-1);

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
     * If the submission id is zero, should throw IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     * @since 1.2
     */
    public void testGetImagesForSubmission_submissionId_zero() throws Exception {
        try {
            persistenceUploadManager.getImagesForSubmission(0);

            fail("expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
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
    public void testGetImagesForSubmission_accuracy() throws Exception {
        SubmissionImage[] submissionImages = persistenceUploadManager.getImagesForSubmission(1);

        assertNotNull("the return should not be null.", submissionImages);
        assertEquals("The array should be empty.", 0, submissionImages.length);
    }
}
