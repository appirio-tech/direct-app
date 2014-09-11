/*
 * Copyright (C) 2006 - 2010 TopCoder Inc., All Rights Reserved.
 */



package com.topcoder.management.deliverable.stresstests;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.management.deliverable.PersistenceUploadManager;
import com.topcoder.management.deliverable.Submission;
import com.topcoder.management.deliverable.SubmissionStatus;
import com.topcoder.management.deliverable.SubmissionType;
import com.topcoder.management.deliverable.Upload;
import com.topcoder.management.deliverable.UploadStatus;
import com.topcoder.management.deliverable.UploadType;
import com.topcoder.management.deliverable.persistence.UploadPersistence;
import com.topcoder.management.deliverable.persistence.sql.MyUploadPersistence;
import com.topcoder.management.deliverable.search.UploadFilterBuilder;
import com.topcoder.search.builder.SearchBundle;
import com.topcoder.search.builder.SearchBundleManager;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.datavalidator.LongValidator;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogFactory;

import junit.framework.TestCase;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Stress test cases for class <code>PersistenceUploadManager </code>.
 *
 * @author Chenhong, morehappiness
 * @version 1.1
 */
public class StressTestPersistenceUploadManager extends TestCase {
    /**
     * The run times of the tests.
     */
    private static final int RUN_TIMES = 2;
    private static final Log logger = LogFactory.getLog(StressTestPersistenceUploadManager.class.getName());

    /**
     * Represents the PersistenceUploadManager instance for test.
     */
    private PersistenceUploadManager manager = null;

    /**
     * Represents the UploadPersistence instance for test.
     */
    private UploadPersistence persistence = null;

    /**
     * Represents the SearchBundleManager instance for test.
     */
    private SearchBundleManager sm = null;

    /**
     * Set up the testing environment.
     *
     * @throws Exception
     *             to junit.
     */
    public void setUp() throws Exception {
        logger.log(Level.INFO, "StressTestPersistenceUploadManager - setUp - start");

        ConfigManager cm = ConfigManager.getInstance();

        for (Iterator iter = cm.getAllNamespaces(); iter.hasNext(); ) {
            cm.removeNamespace((String) iter.next());
        }

        cm.add("stresstests/DBConnectionFactory.xml");
        cm.add("stresstests/SearchBundleManager.xml");

        String namespace = "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl";
        DBConnectionFactory factory = new DBConnectionFactoryImpl(namespace);

        persistence = new MyUploadPersistence(factory);

        String s_n = "com.topcoder.searchbuilder.DeliverableManager";

        sm = new SearchBundleManager(s_n);

        DBTestUtil.tearDownTest();
        DBTestUtil.setUpTest();

        Map uploadFields = new HashMap();

        uploadFields.put("project_id", LongValidator.greaterThanOrEqualTo(0));

        SearchBundle uploadSearchBundle = sm.getSearchBundle("Upload Search Bundle");

        uploadSearchBundle.setSearchableFields(uploadFields);

        Map submissionFields = new HashMap();

        submissionFields.put("resource_id", LongValidator.greaterThanOrEqualTo(0));
        SearchBundle submissionSearchBundle = sm.getSearchBundle("Submission Search Bundle");

        submissionSearchBundle.setSearchableFields(submissionFields);

        manager = new PersistenceUploadManager(persistence, sm);

        logger.log(Level.INFO, "StressTestPersistenceUploadManager - setUp - end");
    }

    /**
     * Clear the tables for testing and remove all the namespaces in the config manager.
     *
     * @throws Exception
     *             to junit.
     */
    public void tearDown() throws Exception {
        logger.log(Level.INFO, "StressTestPersistenceUploadManager - tearDown - start");

        DBTestUtil.tearDownTest();
        DBTestUtil.removeAllNamespace();

        logger.log(Level.INFO, "StressTestPersistenceUploadManager - tearDown - end");
    }

    /**
     * Stress test for method <code>SubmissionType[] getAllSubmissionTypees() </code>.
     *
     */
    public void testGetAllSubmissionTypees() throws Exception {
        this.testCreateSubmissionType();

        long total = 0;

        for (int i = 0; i < RUN_TIMES; i++) {
            logger.log(Level.INFO, "Getting All Submission Typees Try Number: " + i);

            long start = System.currentTimeMillis();

            SubmissionType[] s = manager.getAllSubmissionTypes();

            total += System.currentTimeMillis() - start;

            assertTrue("True is expected.", s.length > 0);
        }

        logger.log(Level.INFO, "Invoke getAllSubmissionType for 10 times cost " + total / 1000.0 + " seconds.");
    }

    /**
     * Stress test for method <code>void createSubmission(Submission submission, String operator) </code>.
     *
     * @throws Exception
     */
    public void testCreateSubmission() throws Exception {
        this.testCreateUpload();
        this.testCreateSubmissionStatus();
        this.testCreateSubmissionType();

        long total = 0;

        for (int i = 0; i < RUN_TIMES; i++) {
            Submission submission = DBTestUtil.createSubmission();

            long start = System.currentTimeMillis();

            manager.createSubmission(submission, "operator");
            total = System.currentTimeMillis() - start;
        }

        assertTrue("True is expected.", this.searchSubmissions().length > 0);

        System.out.println("Invoke createSubmission for 10 times cost " + total / 1000.0 + " seconds.");
    }

    /**
     * Stress test for method <code>void updateSubmission(Submission submission, String operator) </code>.
     *
     */
    public void testUpdateSubmission() throws Exception {
        this.testCreateUpload();
        this.testCreateSubmissionStatus();
        this.testCreateSubmissionType();
        Submission submission = DBTestUtil.createSubmission();

        manager.createSubmission(submission, "create");

        long start = System.currentTimeMillis();

        for (int i = 0; i < RUN_TIMES; i++) {
            manager.updateSubmission(submission, "stress");
        }

        long end = System.currentTimeMillis();

        System.out.println("Invoke updateSubmission for 10 times cost " + (end - start) / 1000.0 + " seconds.");
    }

    /**
     * Stress test for method <code>void removeSubmission(Submission submission, String operator) </code>.
     *
     */
    public void testRemoveSubmission() throws Exception {
        this.testCreateSubmission();

        long start = System.currentTimeMillis();

        for (int i = 1; i < RUN_TIMES; i++) {
            Submission submission = DBTestUtil.createSubmission();

            submission.setId(i);

            manager.removeSubmission(submission, "stress");
        }

        long end = System.currentTimeMillis();

        System.out.println("Invoke removeSubmission for 10 times cost " + (end - start) / 1000.0 + " seconds.");
    }

    /**
     * Stress test for method <code>Submission getSubmission(long id) </code>.
     *
     */
    public void testGetSubmission() throws Exception {
        this.testCreateSubmission();

        long start = System.currentTimeMillis();

        for (int i = 0; i < RUN_TIMES; i++) {
            assertNotNull("Should not be null.", manager.getSubmission(1));
        }

        long end = System.currentTimeMillis();

        System.out.println("Invoke getSubmission for 10 times cost " + (end - start) / 1000.0 + " seconds.");
    }

    public Submission[] searchSubmissions() throws Exception {
        Submission[] s = null;
        long start = System.currentTimeMillis();

        Filter filter = UploadFilterBuilder.createUploadIdFilter(1);

        s = manager.searchSubmissions(filter);

        assertTrue("True is expected.", s.length > 0);

        long end = System.currentTimeMillis();

        System.out.println("Invoke searchSubmission for 10 times cost " + (end - start) / 1000.0 + " seconds.");

        return s;
    }

    /**
     * Stress test for method <code>Submission[] searchSubmissions(Filter filter) </code>.
     *
     */
    public void testSearchSubmissions() throws Exception {
        this.testCreateSubmission();

        for (int i = 0; i < RUN_TIMES; i++) {
            Submission[] s = searchSubmissions();
        }
    }

    /**
     * Stress test for method <code>void createSubmissionStatus(SubmissionStatus submissionStatus,
     * String operator) </code>.
     *
     * @throws Exception
     */
    public void testCreateSubmissionStatus() throws Exception {
        long total = 0;

        for (int i = 0; i < RUN_TIMES; i++) {
            SubmissionStatus status = DBTestUtil.createSubmissionStatus();

            long start = System.currentTimeMillis();

            manager.createSubmissionStatus(status, "stress");

            total += System.currentTimeMillis() - start;
        }

        System.out.println("Invoke createSubmissionStatus 10 times cost " + total / 1000.0 + " seconds.");
    }

    /**
     * Stress test for method <code>SubmissionStatus[] getAllSubmissionStatuses() </code>.
     *
     */
    public void testGetAllSubmissionStatuses() throws Exception {
        this.testCreateSubmissionStatus();

        long total = 0;

        for (int i = 0; i < RUN_TIMES; i++) {
            logger.log(Level.INFO, "Getting All Submission Statuses Try Number: " + i);

            long start = System.currentTimeMillis();

            SubmissionStatus[] s = manager.getAllSubmissionStatuses();

            total += System.currentTimeMillis() - start;

            assertTrue("True is expected.", s.length > 0);
        }

        logger.log(Level.INFO, "Invoke getAllSubmissionStatus for 10 times cost " + total / 1000.0 + " seconds.");
    }

    /**
     * Stress test for method
     * <p>
     * <code>void updateSubmissionStatus(SubmissionStatus submissionStatus, String operator) </code>.
     * </p>
     *
     */
    public void testUpdateSubmissionStatus() throws Exception {
        SubmissionStatus status = DBTestUtil.createSubmissionStatus();

        manager.createSubmissionStatus(status, "created");

        long start = System.currentTimeMillis();

        for (int i = 0; i < RUN_TIMES; i++) {
            manager.updateSubmissionStatus(status, "stress");
        }

        long end = System.currentTimeMillis();

        System.out.println("Invoke updateSubmissionStatus 10 times cost " + (end - start) / 1000.0 + " seconds");
    }

    /**
     * Stress test for method <code>void updateUploadType(UploadType uploadType, String operator) </code>.
     *
     */
    public void testUpdateUploadType() throws Exception {
        UploadType type = DBTestUtil.createUploadType();

        manager.createUploadType(type, "create");

        long total = 0;

        for (int i = 0; i < RUN_TIMES; i++) {
            long start = System.currentTimeMillis();

            manager.updateUploadType(type, "stress");
            total += System.currentTimeMillis() - start;
        }

        assertEquals("Equal is expected.", "stress", persistence.loadUploadType(type.getId()).getModificationUser());
        System.out.println("Invoke updateUploadTye method for 10 times cost " + total / 1000.0 + " seconds.");
    }

    /**
     * Stress test for method <code>void removeUploadType(UploadType uploadType, String operator) </code>.
     *
     */
    public void testRemoveUploadType() throws Exception {
        this.testCreateUploadType();

        UploadType type = DBTestUtil.createUploadType();

        type.setId(100);

        long total = 0;

        for (int i = 0; i < RUN_TIMES; i++) {
            long start = System.currentTimeMillis();

            manager.removeUploadType(type, "stress");

            total += System.currentTimeMillis() - start;
        }

        System.out.println("Invoke removeUploadType for 10 times cost " + total / 1000.0 + " seconds.");
    }

    /**
     * Stress test for method <code>UploadType[] getAllUploadTypes() </code>.
     *
     */
    public void testGetAllUploadTypes() throws Exception {
        this.testCreateUploadType();

        long total = 0;

        for (int i = 0; i < RUN_TIMES; i++) {
            long start = System.currentTimeMillis();
            UploadType[] types = manager.getAllUploadTypes();

            total += System.currentTimeMillis() - start;
            assertTrue("True is expected.", types.length > 0);
        }

        System.out.println("Invoke getAllUploadTypes method for 10 times cost " + total / 1000.0 + " seconds.");
    }

    public void testCreateUploadStatus() throws Exception {
        long total = 0;

        for (int i = 0; i < RUN_TIMES; i++) {
            UploadStatus status = DBTestUtil.createUploadStatus();

            long start = System.currentTimeMillis();

            manager.createUploadStatus(status, "stress");

            total += System.currentTimeMillis() - start;
        }

        System.out.println("Invoke createUploadStatus method 2o times cost " + total / 1000.0 + " second.");

    }

    /**
     * Stress for test method <code>void updateUploadStatus(UploadStatus uploadStatus, String operator)
     * </code>.
     *
     */
    public void testUpdateUploadStatus() throws Exception {
        UploadStatus status = DBTestUtil.createUploadStatus();

        manager.createUploadStatus(status, "create");

        long start = System.currentTimeMillis();

        for (int i = 0; i < RUN_TIMES; i++) {
            manager.updateUploadStatus(status, "stress");
        }

        long end = System.currentTimeMillis();

        System.out.println("Invoke updateUploadStatus for 10 times cost " + (end - start) / 1000.0 + " seconds");
    }

    /**
     * Stress test for method <code>void removeUploadStatus(UploadStatus uploadStatus, String operator) </code>.
     *
     */
    public void testRemoveUploadStatus() throws Exception {
        this.testCreateUploadStatus();

        UploadStatus status = DBTestUtil.createUploadStatus();

        status.setId(100);

        long start = System.currentTimeMillis();

        for (int i = 0; i < RUN_TIMES; i++) {
            manager.removeUploadStatus(status, "stress");
        }

        long end = System.currentTimeMillis();

        System.out.println("Invoke removeUploadStatus for 10 times cost " + (end - start) / 1000.0 + " second.");
    }

    /**
     * Stress tset for method <code>UploadStatus[] getAllUploadStatuses() </code>.
     *
     */
    public void testGetAllUploadStatuses() throws Exception {
        this.testCreateUploadStatus();

        long total = 0;

        for (int i = 0; i < RUN_TIMES; i++) {
            long start = System.currentTimeMillis();
            UploadStatus[] status = manager.getAllUploadStatuses();

            total += System.currentTimeMillis() - start;

            assertTrue("True is expected.", status.length > 0);
        }

        System.out.println("Invoke getAllUploadStatus for 10 times cost " + total / 1000.0 + " seconds");
    }

    /**
     * Stress test for method <code>void createSubmissionType(SubmissionType submissionType,
     * String operator) </code>.
     *
     * @throws Exception
     */
    public void testCreateSubmissionType() throws Exception {
        long total = 0;

        for (int i = 0; i < RUN_TIMES; i++) {
            SubmissionType type = DBTestUtil.createSubmissionType();

            long start = System.currentTimeMillis();

            manager.createSubmissionType(type, "stress");

            total += System.currentTimeMillis() - start;
        }

        System.out.println("Invoke createSubmissionType 10 times cost " + total / 1000.0 + " seconds.");
    }

    /**
     * Stress test for method
     * <p>
     * <code>void updateSubmissionType(SubmissionType submissionType, String operator) </code>.
     * </p>
     *
     */
    public void testUpdateSubmissionType() throws Exception {
        SubmissionType type = DBTestUtil.createSubmissionType();

        manager.createSubmissionType(type, "created");

        long start = System.currentTimeMillis();

        for (int i = 0; i < RUN_TIMES; i++) {
            manager.updateSubmissionType(type, "stress");
        }

        long end = System.currentTimeMillis();

        System.out.println("Invoke updateSubmissionType 10 times cost " + (end - start) / 1000.0 + " seconds");
    }

    /**
     * Stress test for method
     * <code> void removeSubmissionType(SubmissionType submissionType, String operator) </code>
     *
     */
    public void testRemoveSubmissionType() throws Exception {

        long start = System.currentTimeMillis();

        for (int i = 1; i < RUN_TIMES; i++) {
            SubmissionType type = DBTestUtil.createSubmissionType();

            manager.createSubmissionType(type, "stress");

            manager.removeSubmissionType(type, "stress");
        }

        long end = System.currentTimeMillis();

        System.out.println("Invoke removeSubmissionType for 10 times cost " + (end - start) / 1000.0 + " seconds.");
    }

    /**
     * Test method <code> void removeUpload(Upload upload, String operator) </code>.
     *
     * @throws Exception
     */
    public void testRemoveUpload() throws Exception {
        this.testCreateUpload();

        long total = 0;

        for (int i = 7; i < RUN_TIMES; i++) {
            long start = System.currentTimeMillis();

            Upload upload = DBTestUtil.createUploadWithIdNoSet(1, 1);

            upload.setId(i);

            manager.removeUpload(upload, "stress");

            total += System.currentTimeMillis() - start;
        }

        System.out.println("Invoke removeUpload method 10 times cost " + total / 1000.0 + " second.");
    }

    /**
     * Test constructor <code> PersistenceUploadManager(UploadPersistence, SearchBundleManager) </code>.
     */
    public void testConstructor() {
        assertNotNull("Should not be null.", manager);
    }

    public void testCreateUploadType() throws Exception {
        long total = 0;
        long id = 0;

        for (int i = 0; i < RUN_TIMES; i++) {
            UploadType type = DBTestUtil.createUploadType();
            long start = System.currentTimeMillis();

            manager.createUploadType(type, "stress");

            total += System.currentTimeMillis() - start;
            id = type.getId();
        }

        // check if the UploadType is created.
        UploadType ret = persistence.loadUploadType(id);

        assertNotNull("Should not be null.", ret);

        System.out.println("Invoke createUploadType method 10 times cost " + total / 1000.0 + " second.");
    }

    /**
     * Stress test for method <code>void createUpload(Upload upload, String operator) </code>.
     *
     */
    public void testCreateUpload() throws Exception {
        long total = 0;
        long id = 0;

        this.testCreateUploadType();
        this.testCreateUploadStatus();

        for (int i = 0; i < RUN_TIMES; i++) {
            Upload upload = DBTestUtil.createUploadWithIdNoSet(1, 1);
            long start = System.currentTimeMillis();

            manager.createUpload(upload, "stress");

            total += System.currentTimeMillis() - start;
            id = upload.getId();
            System.out.println("Created Upload with ID: " + id);
        }

        // check if the create Upload is correct.
        Upload ret = persistence.loadUpload(id);

        assertNotNull("Should not be null.", ret);

        System.out.println("Invoke createUpload method 10 times cost " + total / 1000.0 + " second.");
    }

    /**
     * Stress test for method <code>void updateUpload(Upload upload, String operator) </code>.
     *
     */
    public void testUpdateUpload() throws Exception {
        this.testCreateUploadType();
        this.testCreateUploadStatus();

        Upload upload = DBTestUtil.createUploadWithIdNoSet(1, 1);

        upload.setId(10);

        persistence.addUpload(upload);

        long total = 0;

        for (int i = 0; i < RUN_TIMES; i++) {
            long start = System.currentTimeMillis();

            manager.updateUpload(upload, "stress");
            total += System.currentTimeMillis() - start;
        }

        // check if the update is correct.
        Upload ret = persistence.loadUpload(upload.getId());

        assertEquals("Equal is expected.", "stress", ret.getModificationUser());

        System.out.println("Invoke updateUpload method 10 times cost " + total / 1000.0 + " second.");
    }

    /**
     * Stress test for method <code>Upload getUpload(long id) </code>.
     *
     */
    public void testGetUpload() throws Exception {
        this.testCreateUpload();

        long total = 0;
        Upload upload = null;

        for (int i = 0; i < RUN_TIMES; i++) {
            long start = System.currentTimeMillis();

            upload = manager.getUpload(1);

            total += System.currentTimeMillis() - start;
        }

        // check if the project id is 2.
        assertEquals("The project id should be 1.", 1, upload.getProject());

        System.out.println("Invoke getUpload method 10 times cost " + total / 1000.0 + " second.");
    }

    /**
     * Stress test for method <code>Upload[] searchUploads(Filter filter) </code>.
     *
     * <p>
     * It will search all upload instance for project id = 1;
     * </p>
     *
     * @throws Exception
     */
    public void testSearchUploads() throws Exception {
        this.testCreateUpload();

        Filter filter = UploadFilterBuilder.createProjectIdFilter(1);

        long total = 0;

        for (int i = 0; i < RUN_TIMES; i++) {
            long start = System.currentTimeMillis();

            manager.searchUploads(filter);

            total += System.currentTimeMillis() - start;
        }

        System.out.println("Invoke SearchUploads method 10 times cost " + total / 1000.0 + " second.");
    }
}
