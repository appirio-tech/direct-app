/*
 * Copyright (C) 2006-2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.management.deliverable.persistence.DeliverablePersistence;
import com.topcoder.management.deliverable.persistence.sql.SqlDeliverablePersistence;
import com.topcoder.management.deliverable.persistence.sql.SqlUploadPersistence;
import com.topcoder.management.deliverable.search.SubmissionFilterBuilder;
import com.topcoder.management.deliverable.search.UploadFilterBuilder;
import com.topcoder.search.builder.SearchBundleManager;
import com.topcoder.search.builder.filter.AndFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.util.idgenerator.IDGenerationException;
import com.topcoder.util.idgenerator.IDGeneratorFactory;

/**
 * <p>
 * Demo for this component.
 * </p>
 * <p>
 * Changes in version 1.2:
 * <ul>
 * <li>Added new demonstration for changes and addition in version 1.2.</li>
 * </ul>
 * </p>
 * <p>
 * Unfortunately, as this component can not really be described in a full customer scenario without including all the
 * other components up for design this week, this demo will not be a customer oriented demo but a rundown of the
 * requirements in a demo form. Where it makes sense, examples of what a customer might do with the information are
 * shown.
 * </p>
 *
 * @author singlewood, sparemax
 * @author TCSDEVELOPER
 * @version 1.2
 */
@SuppressWarnings("unused")
public class Demo extends TestCase {
    /**
     * Represents namespace containing db connection factory configurations.
     */
    private static final String DB_CONNECTION_NAMESPACE = "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl";

    /**
     * Represents the configuration namespace for search builder.
     */
    private static final String SEARCH_BUILDER_NAMESPACE = "com.topcoder.searchbuilder.DeliverableManager";

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
     * Create the test instance.
     *
     * @throws Exception
     *             exception to JUnit.
     */
    public void setUp() throws Exception {
        DeliverableTestHelper.unloadConfig();
        DeliverableTestHelper.loadConfig(CONFIG);
        DeliverableTestHelper.executeBatch(CLEAR_DB_SQL);
        DeliverableTestHelper.executeBatch(INIT_DB_SQL);
    }

    /**
     * Tear down the test instance.
     *
     * @throws Exception
     *             exception to JUnit.
     */
    protected void tearDown() throws Exception {
        DeliverableTestHelper.executeBatch(CLEAR_DB_SQL);
        DeliverableTestHelper.unloadConfig();
    }

    /**
     * Demo for this component.
     *
     * @throws IDGenerationException
     *             If any error occur when generating IDGenerators.
     * @throws Exception
     *             to JUnit.
     */
    public void testDemo() throws Exception {
        // Set up the SearchBundleManager.
        SearchBundleManager searchBundleManager = new SearchBundleManager(SEARCH_BUILDER_NAMESPACE);

        /*
         * 4.3.1 Create Upload Manager, according to Component Specification.
         */

        SqlUploadPersistence uploadPersistence = new SqlUploadPersistence(new DBConnectionFactoryImpl(
                DB_CONNECTION_NAMESPACE));

        UploadManager manager = new PersistenceUploadManager(uploadPersistence, searchBundleManager
                .getSearchBundle("Upload Search Bundle"), searchBundleManager
                .getSearchBundle("Submission Search Bundle"), IDGeneratorFactory.getIDGenerator("upload_id_seq"),
                IDGeneratorFactory.getIDGenerator("upload_type_id_seq"), IDGeneratorFactory
                        .getIDGenerator("upload_status_id_seq"),
                IDGeneratorFactory.getIDGenerator("submission_id_seq"), IDGeneratorFactory
                        .getIDGenerator("submission_status_id_seq"), IDGeneratorFactory
                        .getIDGenerator("submission_type_id_seq"));

        /*
         * 4.3.2 Create an Upload and Submission (with supporting classes).
         */

        // Load tagging instances (also demonstrates manager interactions)

        UploadType uploadType = manager.getAllUploadTypes()[0];
        SubmissionStatus submissionStatus = manager.getAllSubmissionStatuses()[0];
        UploadStatus uploadStatus = manager.getAllUploadStatuses()[0];
        SubmissionType submissionType = manager.getAllSubmissionTypes()[0];

        // Create upload
        Upload upload = new Upload(1234);
        upload.setProject(24);
        upload.setUploadType(uploadType);
        upload.setUploadStatus(uploadStatus);
        upload.setOwner(553);
        upload.setParameter("The upload is somewhere");
        upload.setDescription("This is a sample upload");

        assertEquals("upload's id is not correct.", 1234, upload.getId());
        assertEquals("upload's project is not correct.", 24, upload.getProject());
        assertEquals("upload's UploadStatus is not correct.", uploadStatus, upload.getUploadStatus());
        assertEquals("upload's owner is not correct.", 553, upload.getOwner());
        assertEquals("upload's parameter is not correct.", "The upload is somewhere", upload.getParameter());

        // Create Submission
        Submission submission = new Submission(823);
        List<Upload> uploads = new ArrayList<Upload>();
        uploads.add(upload);
        submission.setUploads(uploads);
        submission.setSubmissionStatus(submissionStatus);
        submission.setSubmissionType(submissionType);
        submission.setThumb(true);
        submission.setUserRank(2);
        submission.setExtra(true);

        assertEquals("submission's parameter is not correct.", 823, submission.getId());
        assertEquals("submission's Upload is not correct.", upload, submission.getUploads().get(0));

        /*
         * 4.3.3 Create deliverable persistence and manager.
         */

        DeliverablePersistence deliverablePersistence = new SqlDeliverablePersistence(new DBConnectionFactoryImpl(
                DB_CONNECTION_NAMESPACE));

        // The checker is used when deliverable instances
        // are retrieved
        Map<String, DeliverableChecker> checker = new HashMap<String, DeliverableChecker>();
        checker.put("name1", new MockDeliverableChecker());
        checker.put("name2", new MockDeliverableChecker());

        DeliverableManager deliverableManager = new PersistenceDeliverableManager(deliverablePersistence, checker,
                searchBundleManager.getSearchBundle("Deliverable Search Bundle"), searchBundleManager
                        .getSearchBundle("Deliverable With Submission Search Bundle"));

        // Search for deliverables (see 4.3.5)

        /*
         * 4.3.4 Save the created Upload and Submission.
         */

        // Create manager, upload and submission. (see 4.3.1, 4.3.2)
        upload = new Upload();
        upload.setProject(24);
        upload.setUploadType(uploadType);
        upload.setUploadStatus(uploadStatus);
        upload.setOwner(553);
        upload.setParameter("The upload is somewhere");
        submission = new Submission();

        manager.createUpload(upload, "Operator #1");

        assertEquals("upload is not created properly.", "Operator #1", upload.getCreationUser());

        submission.setUploads(Arrays.asList(upload));
        submission.setSubmissionStatus(submissionStatus);
        submission.setSubmissionType(submissionType);

        manager.createSubmission(submission, "Operator #1");
        // New instances of the tagging classes can be created through
        // similar methods.

        assertEquals("submission is not created properly.", "Operator #1", submission.getCreationUser());

        // Change a property of the Upload
        upload.setProject(14424);

        // And update it in the persistence
        manager.updateUpload(upload, "Operator #2");

        assertEquals("upload is not updated properly.", "Operator #2", upload.getModificationUser());

        // Remove it from the persistence
        manager.removeUpload(upload, "Operator #3");

        assertEquals("upload is not removed properly.", "Operator #3", upload.getModificationUser());

        // Submissions can be changed and removed similarly

        /*
         * 4.3.5 Retrieve and search for uploads
         */

        // Get an upload for a given id
        Upload upload2 = manager.getUpload(14402);

        // The properties of the upload can then be queried and used by the client of this
        // component. Submissions can be retrieved similarly.

        // Search for uploads Build the uploads - this example shows searching for
        // all uploads related to a given project and having a given upload type
        Filter projectFilter = UploadFilterBuilder.createProjectIdFilter(953);
        Filter uploadTypeFilter = UploadFilterBuilder.createUploadTypeIdFilter(4);

        Filter fullFilter = new AndFilter(projectFilter, uploadTypeFilter);

        // Search for the Uploads
        Upload[] matchingUploads = manager.searchUploads(fullFilter);

        // Submissions and Deliverables can be searched similarly by
        // using the other FilterBuilder classes and the corresponding
        // UploadManager or DeliverableManager methods.

        // Get all the lookup table values.
        UploadType[] uploadTypes = manager.getAllUploadTypes();
        UploadStatus[] uploadStatuses = manager.getAllUploadStatuses();
        SubmissionStatus[] submissionStatuses = manager.getAllSubmissionStatuses();
        SubmissionType[] submissionTypes = manager.getAllSubmissionTypes();

        // Alter a lookup table entry and update the persistence
        uploadTypes[0].setName("Changed name");
        manager.updateUploadType(uploadTypes[0], "Operator #1");

        // Lookup table entries can be created/removed through parallel
        // methods to those shown in section 4.3.4

        /**
         * 4.3.6 Search for submissions with specific submission type
         */
        long specificationSubmissionTypeId = 1;
        Filter specificationSubmissionFilter = SubmissionFilterBuilder
                .createSubmissionTypeIdFilter(specificationSubmissionTypeId);

        Submission[] specificationSubmissions = manager.searchSubmissions(specificationSubmissionFilter);

        /*
         * new demo for addition in version 1.2
         */
        // Create submission image
        SubmissionImage submissionImage = new SubmissionImage();
        submissionImage.setSubmissionId(submission.getId());
        submissionImage.setImageId(1);
        submissionImage.setSortOrder(1);
        manager.createSubmissionImage(submissionImage, "admin");

        // Update the submission image
        submissionImage.setSortOrder(0);
        manager.updateSubmissionImage(submissionImage, "admin");

        // Remove the submission image
        manager.removeSubmissionImage(submissionImage, "admin");

        // Retrieve the MIME type with ID=1
        MimeType mimeType = manager.getMimeType(1);

        // Retrieve all MIME types
        MimeType[] mimeTypes = manager.getAllMimeTypes();

        // Retrieve the submissions for project with ID=1 and
        // user with ID=1
        Submission[] submissions = manager.getUserSubmissionsForProject(1, 1);

        // Retrieve all submissions for project with ID=1
        submissions = manager.getProjectSubmissions(1);

        // Retrieve the images for submission with ID=1
        SubmissionImage[] submissionImages = manager.getImagesForSubmission(1);

    }
}
