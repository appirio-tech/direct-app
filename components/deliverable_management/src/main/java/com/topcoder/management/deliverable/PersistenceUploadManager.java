/*
 * Copyright (C) 2006-2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Date;

import com.topcoder.management.deliverable.logging.LogMessage;
import com.topcoder.management.deliverable.persistence.UploadPersistence;
import com.topcoder.management.deliverable.persistence.UploadPersistenceException;
import com.topcoder.search.builder.SearchBuilderConfigurationException;
import com.topcoder.search.builder.SearchBuilderException;
import com.topcoder.search.builder.SearchBundle;
import com.topcoder.search.builder.SearchBundleManager;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.util.idgenerator.IDGenerationException;
import com.topcoder.util.idgenerator.IDGenerator;
import com.topcoder.util.idgenerator.IDGeneratorFactory;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;
import com.topcoder.util.sql.databaseabstraction.CustomResultSet;

/**
 * <p>
 * The PersistenceUploadManager class implements the UploadManager interface. It ties together a persistence mechanism,
 * several Search Builder instances (for searching for various types of data), and several id generators (for generating
 * ids for the various types). This class consists of several methods styles. The first method style just calls directly
 * to a corresponding method of the persistence. The second method style first assigns values to some data fields of the
 * object before calling a persistence method. The third type of method uses a SearchBundle to execute a search and then
 * uses the persistence to load an object for each of the ids found from the search.
 * </p>
 * <p>
 * Here is the sample code for using this component.
 *
 * <pre>
 * // Set up the SearchBundleManager.
 * SearchBundleManager searchBundleManager = new SearchBundleManager(SEARCH_BUILDER_NAMESPACE);
 *
 * // 4.3.1 Create Upload Manager, according to Component Specification.
 *
 * SqlUploadPersistence uploadPersistence =
 *     new SqlUploadPersistence(new DBConnectionFactoryImpl(DB_CONNECTION_NAMESPACE));
 *
 * UploadManager manager = new PersistenceUploadManager(uploadPersistence,
 *     searchBundleManager.getSearchBundle(&quot;Upload Search Bundle&quot;),
 *     searchBundleManager.getSearchBundle(&quot;Submission Search Bundle&quot;),
 *     IDGeneratorFactory.getIDGenerator(&quot;upload_id_seq&quot;),
 *     IDGeneratorFactory.getIDGenerator(&quot;upload_type_id_seq&quot;),
 *     IDGeneratorFactory.getIDGenerator(&quot;upload_status_id_seq&quot;),
 *     IDGeneratorFactory.getIDGenerator(&quot;submission_id_seq&quot;),
 *     IDGeneratorFactory.getIDGenerator(&quot;submission_status_id_seq&quot;),
 *     IDGeneratorFactory.getIDGenerator(&quot;submission_type_id_seq&quot;));
 *
 * // 4.3.2 Create an Upload and Submission (with supporting classes).
 *
 * // Load tagging instances (also demonstrates manager interactions)
 *
 * UploadType uploadType = manager.getAllUploadTypes()[0];
 * SubmissionStatus submissionStatus = manager.getAllSubmissionStatuses()[0];
 * UploadStatus uploadStatus = manager.getAllUploadStatuses()[0];
 * SubmissionType submissionType = manager.getAllSubmissionTypes()[0];
 *
 * // Create upload
 * Upload upload = new Upload(1234);
 * upload.setProject(24);
 * upload.setUploadType(uploadType);
 * upload.setUploadStatus(uploadStatus);
 * upload.setOwner(553);
 * upload.setParameter(&quot;The upload is somewhere&quot;);
 * upload.setDescription(&quot;This is a sample upload&quot;);
 *
 * // Create Submission
 * Submission submission = new Submission(823);
 * List&lt;Upload&gt; uploads = new ArrayList&lt;Upload&gt;();
 * uploads.add(upload);
 * submission.setUploads(uploads);
 * submission.setSubmissionStatus(submissionStatus);
 * submission.setSubmissionType(submissionType);
 * submission.setUserRank(2);
 * submission.setExtra(true);
 *
 * // 4.3.4 Save the created Upload and Submission.
 *
 * // Create manager, upload and submission. (see 4.3.1, 4.3.2)
 * upload = new Upload();
 * upload.setProject(24);
 * upload.setUploadType(uploadType);
 * upload.setUploadStatus(uploadStatus);
 * upload.setOwner(553);
 * upload.setParameter(&quot;The upload is somewhere&quot;);
 * submission = new Submission();
 *
 * manager.createUpload(upload, &quot;Operator #1&quot;);
 *
 * assertEquals(&quot;upload is not created properly.&quot;, &quot;Operator #1&quot;, upload.getCreationUser());
 *
 * submission.setUploads(Arrays.asList(upload));
 * submission.setSubmissionStatus(submissionStatus);
 * submission.setSubmissionType(submissionType);
 *
 * manager.createSubmission(submission, &quot;Operator #1&quot;);
 * // New instances of the tagging classes can be created through
 * // similar methods.
 *
 * // Change a property of the Upload
 * upload.setProject(14424);
 *
 * // And update it in the persistence
 * manager.updateUpload(upload, &quot;Operator #2&quot;);
 *
 * // Remove it from the persistence
 * manager.removeUpload(upload, &quot;Operator #3&quot;);
 *
 * // Submissions can be changed and removed similarly
 *
 * // 4.3.5 Retrieve and search for uploads
 *
 * // Get an upload for a given id
 * Upload upload2 = manager.getUpload(14402);
 *
 * // The properties of the upload can then be queried and used by the client of this
 * // component. Submissions can be retrieved similarly.
 *
 * // Search for uploads Build the uploads - this example shows searching for
 * // all uploads related to a given project and having a given upload type
 * Filter projectFilter = UploadFilterBuilder.createProjectIdFilter(953);
 * Filter uploadTypeFilter = UploadFilterBuilder.createUploadTypeIdFilter(4);
 *
 * Filter fullFilter = new AndFilter(projectFilter, uploadTypeFilter);
 *
 * // Search for the Uploads
 * Upload[] matchingUploads = manager.searchUploads(fullFilter);
 *
 * // Submissions and Deliverables can be searched similarly by
 * // using the other FilterBuilder classes and the corresponding
 * // UploadManager or DeliverableManager methods.
 *
 * // Get all the lookup table values.
 * UploadType[] uploadTypes = manager.getAllUploadTypes();
 * UploadStatus[] uploadStatuses = manager.getAllUploadStatuses();
 * SubmissionStatus[] submissionStatuses = manager.getAllSubmissionStatuses();
 * SubmissionType[] submissionTypes = manager.getAllSubmissionTypes();
 *
 * // Alter a lookup table entry and update the persistence
 * uploadTypes[0].setName(&quot;Changed name&quot;);
 * manager.updateUploadType(uploadTypes[0], &quot;Operator #1&quot;);
 *
 * // Lookup table entries can be created/removed through parallel
 * // methods to those shown in section 4.3.4
 *
 * // 4.3.6 Search for submissions with specific submission type
 * long specificationSubmissionTypeId = 1;
 * Filter specificationSubmissionFilter = SubmissionFilterBuilder
 *         .createSubmissionTypeIdFilter(specificationSubmissionTypeId);
 *
 * Submission[] specificationSubmissions = manager.searchSubmissions(specificationSubmissionFilter);
 *
 * // 4.3.7 new demo for addition in version 1.2
 * // Create submission image
 * SubmissionImage submissionImage = new SubmissionImage();
 * submissionImage.setSubmissionId(submission.getId());
 * submissionImage.setImageId(1);
 * submissionImage.setSortOrder(1);
 * manager.createSubmissionImage(submissionImage, &quot;admin&quot;);
 *
 * // Update the submission image
 * submissionImage.setSortOrder(0);
 * manager.updateSubmissionImage(submissionImage, &quot;admin&quot;);
 *
 * // Remove the submission image
 * manager.removeSubmissionImage(submissionImage, &quot;admin&quot;);
 *
 * // Retrieve the MIME type with ID=1
 * MimeType mimeType = manager.getMimeType(1);
 *
 * // Retrieve all MIME types
 * MimeType[] mimeTypes = manager.getAllMimeTypes();
 *
 * // Retrieve the submissions for project with ID=1 and
 * // user with ID=1
 * Submission[] submissions = manager.getUserSubmissionsForProject(1, 1);
 *
 * // Retrieve all submissions for project with ID=1
 * submissions = manager.getProjectSubmissions(1);
 *
 * // Retrieve the images for submission with ID=1
 * SubmissionImage[] submissionImages = manager.getImagesForSubmission(1);
 * </pre>
 *
 * </p>
 * <p>
 * <em>Changes in 1.1: </em>
 * <ul>
 * <li>Added methods for managing submission types.</li>
 * <li>Changed <code>logger</code> attribute name to upper case to meet Java and TopCoder standards.</li>
 * </ul>
 * </p>
 * <p>
 * Changes in 1.2:
 * <ul>
 * <li>Added methods for managing SubmissionImage entities.</li>
 * <li>Added methods for retrieving MimeType entities.</li>
 * <li>Added methods for retrieving project/user submissions.</li>
 * <li>Added method for retrieving images associated with submission.</li>
 * <li>Retrieving id field of entity is refactored into getId method, to reduce code redundancy.</li>
 * </ul>
 * </p>
 * 
 * <p>
 * Changes in 1.3:
 * <ul>
 * <li>Update {@link #searchSubmissions(Filter)} method to remove <code>submission.setUploads</code> statement
 * because one submission can only have a upload now.</li>
 * </ul>
 * </p>
 * 
 * <p>
 * Changes in 1.4 (TC Direct Replatforming Release 5):
 * <ul>
 * <li>Update {@link #searchSubmissions(Filter)} method to load <code>SubmissionDeclaration</code> for submission.</li>
 * </ul>
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> This class is immutable and hence thread-safe.
 * </p>
 *
 * @author aubergineanode, singlewood, George1
 * @author saarixx, sparemax
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.4
 */
public class PersistenceUploadManager implements UploadManager {
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
     * The name under which the id generator for uploads should appear in the IDGeneratorFactory if the
     * IDGeneratorFactory constructor is used.
     */
    public static final String UPLOAD_ID_GENERATOR_NAME = "upload_id_seq";

    /**
     * The name under which the id generator for upload types should appear in the IDGeneratorFactory if the
     * IDGeneratorFactory constructor is used. This field is public, static, and final.
     */
    public static final String UPLOAD_TYPE_ID_GENERATOR_NAME = "upload_type_id_seq";

    /**
     * The name under which the id generator for upload statuses should appear in the IDGeneratorFactory if the
     * IDGeneratorFactory constructor is used. This field is public, static, and final.
     */
    public static final String UPLOAD_STATUS_ID_GENERATOR_NAME = "upload_status_id_seq";

    /**
     * The name under which the id generator for submissions should appear in the IDGeneratorFactory if the
     * IDGeneratorFactory constructor is used.
     */
    public static final String SUBMISSION_ID_GENERATOR_NAME = "submission_id_seq";

    /**
     * The name under which the id generator for submission statuses should appear in the IDGeneratorFactory if the
     * IDGeneratorFactory constructor is used.
     */
    public static final String SUBMISSION_STATUS_ID_GENERATOR_NAME = "submission_status_id_seq";

    /**
     * <p>
     * The name under which the id generator for submission types should appear in the IDGeneratorFactory if the
     * IDGeneratorFactory constructor is used.
     * </p>
     * <p>
     * This field is public, static, and final.
     * </p>
     *
     * @since 1.1
     */
    public static final String SUBMISSION_TYPE_ID_GENERATOR_NAME = "submission_type_id_seq";

    /**
     * <p>
     * Logger instance using the class name as category.
     * </p>
     * <p>
     * Is initialized during class loading and never changed after that.
     * </p>
     * <p>
     * <em>Changes in 1.1:</em>
     * <ul>
     * <li>Changed attribute name to upper case to meet Java and TopCoder standards.</li>
     * </ul>
     * </p>
     */
    private static final Log LOGGER = LogManager.getLog(PersistenceUploadManager.class.getName());
    /**
     * The flag which indicate the caller of persistenceUploadManagerHelper.
     */
    private static final int CREATE_FLAG = 0;

    /**
     * The flag which indicate the caller of persistenceUploadManagerHelper.
     */
    private static final int UPDATE_FLAG = 1;

    /**
     * The flag which indicate the caller of persistenceUploadManagerHelper.
     */
    private static final int REMOVE_FLAG = 2;

    /**
     * The persistence store for Uploads, Submission, and related objects. This field is set in the constructor, is
     * immutable, and can never be null.
     */
    private final UploadPersistence persistence;

    /**
     * The search bundle that is used when searching for uploads. This field is set in the constructor, is immutable,
     * and can never be null.
     */
    private final SearchBundle uploadSearchBundle;

    /**
     * The search bundle that is used when searching for submissions. This field is set in the constructor, is
     * immutable, and can never be null.
     */
    private final SearchBundle submissionSearchBundle;

    /**
     * The generator used to create ids for new Uploads. This field is set in the constructor, is immutable, and can
     * never be null.
     */
    private final IDGenerator uploadIdGenerator;

    /**
     * The generator used to create ids for new Submissions. This field is set in the constructor, is immutable, and can
     * never be null.
     */
    private final IDGenerator submissionIdGenerator;

    /**
     * The generator used to create ids for new UploadTypes. This field is set in the constructor, is immutable, and can
     * never be null.
     */
    private final IDGenerator uploadTypeIdGenerator;

    /**
     * The generator used to create ids for new UploadStatuses. This field is set in the constructor, is immutable, and
     * can never be null.
     */
    private final IDGenerator uploadStatusIdGenerator;

    /**
     * The generator used to create ids for new SubmissionStatuses. This field is set in the constructor, is immutable,
     * and can never be null.
     */
    private final IDGenerator submissionStatusIdGenerator;

    /**
     * <p>
     * The generator used to create ids for new SubmissionTypes.
     * </p>
     * <p>
     * This field is set in the constructor, is immutable, and can never be null. This field is used when an id is
     * needed for a new SubmissionType, which occurs in the createSubmissionType method.
     * </p>
     *
     * @since 1.1
     */
    private final IDGenerator submissionTypeIdGenerator;

    /**
     * <p>
     * Creates a new PersistenceUploadManager.
     * </p>
     * <p>
     * <em>Changes in 1.1: </em>
     * <ul>
     * <li>Added submissionTypeIdGenerator parameter.</li>
     * </ul>
     * </p>
     *
     * @param persistence
     *            The persistence for Uploads, Submissions, and related objects.
     * @param uploadSearchBundle
     *            The search bundle for searching uploads.
     * @param submissionSearchBundle
     *            The search bundle for searching submissions.
     * @param uploadIdGenerator
     *            The generator for Upload ids.
     * @param uploadTypeIdGenerator
     *            The generator for UploadType ids.
     * @param uploadStatusIdGenerator
     *            The generator for UploadStatus ids.
     * @param submissionIDGenerator
     *            The generator for Submission ids.
     * @param submissionStatusIdGenerator
     *            The generator for SubmissionStatus ids.
     * @param submissionTypeIdGenerator
     *            The generator for SubmissionType ids
     * @throws IllegalArgumentException
     *             If any argument is <code>null</code>.
     */
    public PersistenceUploadManager(UploadPersistence persistence, SearchBundle uploadSearchBundle,
        SearchBundle submissionSearchBundle, IDGenerator uploadIdGenerator, IDGenerator uploadTypeIdGenerator,
        IDGenerator uploadStatusIdGenerator, IDGenerator submissionIDGenerator,
        IDGenerator submissionStatusIdGenerator, IDGenerator submissionTypeIdGenerator) {
        // Check parameters.
        DeliverableHelper.checkObjectNotNull(persistence, "persistence", LOGGER);
        DeliverableHelper.checkObjectNotNull(uploadIdGenerator, "uploadIdGenerator", LOGGER);
        DeliverableHelper.checkObjectNotNull(uploadTypeIdGenerator, "uploadTypeIdGenerator", LOGGER);
        DeliverableHelper.checkObjectNotNull(uploadStatusIdGenerator, "uploadStatusIdGenerator", LOGGER);
        DeliverableHelper.checkObjectNotNull(submissionIDGenerator, "submissionIDGenerator", LOGGER);
        DeliverableHelper.checkObjectNotNull(submissionStatusIdGenerator, "submissionStatusIdGenerator", LOGGER);
        DeliverableHelper.checkObjectNotNull(submissionTypeIdGenerator, "submissionTypeIdGenerator", LOGGER);
        DeliverableHelper.checkObjectNotNullFullDesp(uploadSearchBundle,
                "uploadSearchBundle is null, or searchBundleManager doesn't contain"
                        + " SearchBundle of required names UPLOAD_SEARCH_BUNDLE_NAME", LOGGER);
        DeliverableHelper.checkObjectNotNullFullDesp(submissionSearchBundle,
                "submissionSearchBundle is null, or searchBundleManager doesn't contain"
                        + " SearchBundle of required names SUBMISSION_SEARCH_BUNDLE_NAME", LOGGER);

        // Set the searchable fields of search bundles.
        DeliverableHelper.setSearchableFields(uploadSearchBundle, DeliverableHelper.UPLOAD_SEARCH_BUNDLE);
        DeliverableHelper.setSearchableFields(submissionSearchBundle, DeliverableHelper.SUBMISSION_SEARCH_BUNDLE);

        LOGGER.log(Level.INFO, "Instantiate PersistenceUploadManager with UploadPersistence, uploadSearchBundle,"
                + " submissionSearchBundle, uploadIdGenerator, uploadTypeIdGenerator, uploadStatusIdGenerator,"
                + " submissionIDGenerator and submissionStatusIdGenerator.");

        // Initializing all fields to the given values.
        this.persistence = persistence;
        this.uploadSearchBundle = uploadSearchBundle;
        this.submissionSearchBundle = submissionSearchBundle;
        this.uploadIdGenerator = uploadIdGenerator;
        this.uploadTypeIdGenerator = uploadTypeIdGenerator;
        this.uploadStatusIdGenerator = uploadStatusIdGenerator;
        this.submissionIdGenerator = submissionIDGenerator;
        this.submissionStatusIdGenerator = submissionStatusIdGenerator;
        this.submissionTypeIdGenerator = submissionTypeIdGenerator;
    }

    /**
     * <p>
     * Creates a new PersistenceUploadManager.
     * </p>
     * <p>
     * <em>Changes in 1.1: </em>
     * <ul>
     * <li>Additionally creating submissionTypeIdGenerator.</li>
     * </ul>
     * </p>
     *
     * @param persistence
     *            The persistence for Uploads and related objects.
     * @param searchBundleManager
     *            The manager containing the various SearchBundles needed.
     * @throws IllegalArgumentException
     *             If any argument is <code>null</code>.
     * @throws IllegalArgumentException
     *             If any search bundle or id generator is not available under the required names.
     * @throws IDGenerationException
     *             If any error occur when generating IDGenerators.
     */
    public PersistenceUploadManager(UploadPersistence persistence, SearchBundleManager searchBundleManager)
        throws IDGenerationException {
        // Check if searchBundleManager is null, if not, get the SearchBundles.
        // Get IDGenerator from IDGeneratorFactory. If any id generator is not available under
        // the required names, IDGenerationException will be thrown.
        // Finally, delegate to the first constructor.
        this(
                persistence,
                (DeliverableHelper.checkObjectNotNull(searchBundleManager, "searchBundleManager", LOGGER)) ?
                            searchBundleManager.getSearchBundle(UPLOAD_SEARCH_BUNDLE_NAME)
                        : null, searchBundleManager.getSearchBundle(SUBMISSION_SEARCH_BUNDLE_NAME), IDGeneratorFactory
                        .getIDGenerator(UPLOAD_ID_GENERATOR_NAME), IDGeneratorFactory
                        .getIDGenerator(UPLOAD_TYPE_ID_GENERATOR_NAME), IDGeneratorFactory
                        .getIDGenerator(UPLOAD_STATUS_ID_GENERATOR_NAME), IDGeneratorFactory
                        .getIDGenerator(SUBMISSION_ID_GENERATOR_NAME), IDGeneratorFactory
                        .getIDGenerator(SUBMISSION_STATUS_ID_GENERATOR_NAME), IDGeneratorFactory
                        .getIDGenerator(SUBMISSION_TYPE_ID_GENERATOR_NAME));
    }

    /**
     * <p>
     * Creates a new upload in the persistence store. The id of the upload must be UNSET_ID. The manager will assign an
     * id to the upload.
     * </p>
     *
     * @param upload
     *            The upload to add
     * @param operator
     *            The name of the operator making the change to the persistence store
     * @throws IllegalArgumentException
     *             If upload or operator is null
     * @throws IllegalArgumentException
     *             If the id is not UNSET_ID
     * @throws IllegalArgumentException
     *             If the upload (once an id and creation/modification user/date are assigned) is not in a state to be
     *             persisted (i.e. if isValidToPersist returns false)
     * @throws UploadPersistenceException
     *             If there is an error persisting the upload
     */
    public void createUpload(Upload upload, String operator) throws UploadPersistenceException {
        LOGGER.log(Level.INFO, new LogMessage("Upload", null, operator, "Create new Upload."));
        persistenceUploadManagerHelper(upload, "upload", operator, CREATE_FLAG, uploadIdGenerator);
    }

    /**
     * <p>
     * Updates the upload in the persistence store. The id of the upload can not be UNSET_ID and all necessary fields
     * must have already been assigned.
     * </p>
     *
     * @param upload
     *            The upload to update
     * @param operator
     *            The name of the operator making the change to the persistence store
     * @throws IllegalArgumentException
     *             If upload or operator is null
     * @throws IllegalArgumentException
     *             The id of the upload is UNSET_ID
     * @throws IllegalArgumentException
     *             If the upload (once the modification user/date is set) is not in a state to be persisted (i.e. if
     *             isValidToPersist returns false)
     * @throws UploadPersistenceException
     *             If there is an error persisting the upload changes
     */
    public void updateUpload(Upload upload, String operator) throws UploadPersistenceException {
        LOGGER.log(Level.INFO, new LogMessage("Upload", getId(upload), operator, "Update Upload."));

        persistenceUploadManagerHelper(upload, "upload", operator, UPDATE_FLAG, null);
    }

    /**
     * <p>
     * Removes the upload (by id) from the persistence store. The id of the upload can not be UNSET_ID.
     * </p>
     *
     * @param upload
     *            The upload to remove
     * @param operator
     *            The name of the operator making the change to the persistence store
     * @throws IllegalArgumentException
     *             If upload or operator is null
     * @throws IllegalArgumentException
     *             If the id of the upload is UNSET_ID
     * @throws IllegalArgumentException
     *             If the upload (once the modification user/date is set) is not in a state to be persisted (i.e. if
     *             isValidToPersist returns false)
     * @throws UploadPersistenceException
     *             If there is an error making the change in the persistence
     */
    public void removeUpload(Upload upload, String operator) throws UploadPersistenceException {
        LOGGER.log(Level.INFO, new LogMessage("Upload", getId(upload), operator, "Remove Upload."));

        persistenceUploadManagerHelper(upload, "upload", operator, REMOVE_FLAG, null);
    }

    /**
     * <p>
     * Gets the upload in the persistence store for the given id. Returns null if there is no upload for the given id.
     * </p>
     *
     * @return The retrieved Upload, or null
     * @param id
     *            The id of the upload to retrieve
     * @throws IllegalArgumentException
     *             If id is <= 0
     * @throws UploadPersistenceException
     *             If there is an error retrieving the Upload from persistence
     */
    public Upload getUpload(long id) throws UploadPersistenceException {
        LOGGER.log(Level.INFO,
                new LogMessage("Upload", new Long(id), null, "Retrieve Upload, delegate to persistence."));
        // Simply call the corresponding persistence method.
        return persistence.loadUpload(id);
    }

    /**
     * <p>
     * Searches the persistence for uploads meeting the filter. The filter can be easily built using the
     * UploadFilterBuilder.
     * </p>
     *
     * @return The Uploads meeting the filter
     * @param filter
     *            The filter to use
     * @throws IllegalArgumentException
     *             If filter is null
     * @throws UploadPersistenceException
     *             If there is an error reading the upload from persistence
     * @throws SearchBuilderException
     *             If there is an error executing the filter
     * @throws SearchBuilderConfigurationException
     *             If the SearchBundle used by the manager is not configured for proper searching.
     */
    public Upload[] searchUploads(Filter filter) throws UploadPersistenceException, SearchBuilderException {
        DeliverableHelper.checkObjectNotNull(filter, "filter", LOGGER);
        CustomResultSet resultSet = (CustomResultSet) uploadSearchBundle.search(filter);
        LOGGER.log(Level.INFO, new LogMessage("Upload", null, null, "search uploads with filter.") + " found: "
                + resultSet.getRecordCount());
        // Check if the object is a CustomResultSet with a single column consisting of long ids.
        // The parameter 1 indicate that there should be a single column in the CustomResultSet.
        // The return type is long[][], what we need is the first array.
        return persistence.loadUploads(resultSet);
    }

    /**
     * <p>
     * Creates a new UploadType in the persistence store. The id of the upload type must be UNSET_ID. The manager will
     * assign an id before persisting the change.
     * </p>
     *
     * @param uploadType
     *            The upload type to add to the persistence store
     * @param operator
     *            The name of the operator making the change to the persistence store
     * @throws IllegalArgumentException
     *             If uploadType or operator is null
     * @throws IllegalArgumentException
     *             If the id is not UNSET_ID
     * @throws IllegalArgumentException
     *             If the upload type (once an id and creation/modification user/date are assigned) is not in a state to
     *             be persisted (i.e. if isValidToPersist returns false)
     * @throws UploadPersistenceException
     *             If there is an error making the change in the persistence
     */
    public void createUploadType(UploadType uploadType, String operator) throws UploadPersistenceException {
        LOGGER.log(Level.INFO, new LogMessage("UploadType", null, operator, "Create new UploadType."));
        persistenceUploadManagerHelper(uploadType, "uploadType", operator, CREATE_FLAG, uploadTypeIdGenerator);
    }

    /**
     * <p>
     * Updates the info for an UploadType in the persistence store. All fields of the upload type must have values
     * assigned when this method is called.
     * </p>
     *
     * @param uploadType
     *            The upload type to update
     * @param operator
     *            The name of the operator making the change to the persistence store
     * @throws IllegalArgumentException
     *             If uploadType or operator is null
     * @throws IllegalArgumentException
     *             The id is UNSET_ID
     * @throws IllegalArgumentException
     *             If the upload type (once the modification user/date is set) is not in a state to be persisted (i.e.
     *             if isValidToPersist returns false)
     * @throws UploadPersistenceException
     *             If there is an error making the change in the persistence
     */
    public void updateUploadType(UploadType uploadType, String operator) throws UploadPersistenceException {
        LOGGER.log(Level.INFO, new LogMessage("UploadType", getId(uploadType), operator, "update UploadType."));

        persistenceUploadManagerHelper(uploadType, "uploadType", operator, UPDATE_FLAG, null);
    }

    /**
     * <p>
     * Removes the given upload type (by id) from the persistence. The id of the uploadType can not be UNSET_ID.
     * </p>
     *
     * @param uploadType
     *            The upload type to remove
     * @param operator
     *            The name of the operator making the change to the persistence store
     * @throws IllegalArgumentException
     *             If uploadType or operator is null
     * @throws IllegalArgumentException
     *             If the id of the upload type is UNSET_ID
     * @throws IllegalArgumentException
     *             If the uploadType (once the modification user/date is set) is not in a state to be persisted (i.e. if
     *             isValidToPersist returns false)
     * @throws UploadPersistenceException
     *             If there is an error making the change in the persistence
     */
    public void removeUploadType(UploadType uploadType, String operator) throws UploadPersistenceException {
        LOGGER.log(Level.INFO, new LogMessage("UploadType", getId(uploadType), operator, "remove UploadType."));

        persistenceUploadManagerHelper(uploadType, "uploadType", operator, REMOVE_FLAG, null);
    }

    /**
     * <p>
     * Gets all the upload types in the persistence store.
     * </p>
     *
     * @return All UploadTypes in the persistence store.
     * @throws UploadPersistenceException
     *             If there is an error accessing the persistence store
     */
    public UploadType[] getAllUploadTypes() throws UploadPersistenceException {
        LOGGER.log(Level.INFO, "get All upload types, delegate to persistence.");
        return persistence.loadUploadTypes(persistence.getAllUploadTypeIds());
    }

    /**
     * <p>
     * Creates a new UploadStatus in the persistence store. The id of the upload status must be UNSET_ID. The manager
     * will assign an id before persisting the change.
     * </p>
     *
     * @param uploadStatus
     *            The upload status to add to the persistence store
     * @param operator
     *            The name of the operator making the change to the persistence store
     * @throws IllegalArgumentException
     *             If uploadStatus or operator is null
     * @throws IllegalArgumentException
     *             If the id is not UNSET_ID
     * @throws IllegalArgumentException
     *             If the upload status (once an id and creation/modification user/date are assigned) is not in a state
     *             to be persisted (i.e. if isValidToPersist returns false)
     * @throws UploadPersistenceException
     *             If there is an error making the change in the persistence
     */
    public void createUploadStatus(UploadStatus uploadStatus, String operator) throws UploadPersistenceException {
        LOGGER.log(Level.INFO, new LogMessage("UploadStatus", null, operator, "create new UploadStatus."));
        persistenceUploadManagerHelper(uploadStatus, "uploadStatus", operator, CREATE_FLAG, uploadStatusIdGenerator);
    }

    /**
     * <p>
     * Updates the info for an UploadStatus in the persistence store. All fields of the upload status must have values
     * assigned when this method is called.
     * </p>
     *
     * @param uploadStatus
     *            The upload status to update
     * @param operator
     *            The name of the operator making the change to the persistence store
     * @throws IllegalArgumentException
     *             If uploadStatus or operator is null
     * @throws IllegalArgumentException
     *             The id is UNSET_ID
     * @throws IllegalArgumentException
     *             If the upload status (once the modification user/date is set) is not in a state to be persisted (i.e.
     *             if isValidToPersist returns false)
     * @throws UploadPersistenceException
     *             If there is an error making the change in the persistence
     */
    public void updateUploadStatus(UploadStatus uploadStatus, String operator) throws UploadPersistenceException {
        LOGGER.log(Level.INFO,
                new LogMessage("UploadStatus", getId(uploadStatus), operator, "create new UploadStatus."));

        persistenceUploadManagerHelper(uploadStatus, "uploadStatus", operator, UPDATE_FLAG, null);
    }

    /**
     * <p>
     * Removes the given upload status (by id) from the persistence. The id of the uploadStatus can not be UNSET_ID.
     * </p>
     *
     * @param uploadStatus
     *            The upload status to remove
     * @param operator
     *            The name of the operator making the change to the persistence store
     * @throws IllegalArgumentException
     *             If uploadStatus or operator is null
     * @throws IllegalArgumentException
     *             If the id of the upload status is UNSET_ID
     * @throws IllegalArgumentException
     *             If the uploadStatus (once the modification user/date is set) is not in a state to be persisted (i.e.
     *             if isValidToPersist returns false)
     * @throws UploadPersistenceException
     *             If there is an error making the change in the persistence
     */
    public void removeUploadStatus(UploadStatus uploadStatus, String operator) throws UploadPersistenceException {
        LOGGER.log(Level.INFO, new LogMessage("UploadStatus", getId(uploadStatus), operator, "remove UploadStatus."));

        persistenceUploadManagerHelper(uploadStatus, "uploadStatus", operator, REMOVE_FLAG, null);
    }

    /**
     * <p>
     * Gets all the upload statuses in the persistence store.
     * </p>
     *
     * @return All UploadStatuses in the persistence store.
     * @throws UploadPersistenceException
     *             If there is an error accessing the persistence store
     */
    public UploadStatus[] getAllUploadStatuses() throws UploadPersistenceException {
        LOGGER.log(Level.INFO, "get All upload status, delegate to persistence.");
        return persistence.loadUploadStatuses(persistence.getAllUploadStatusIds());
    }

    /**
     * <p>
     * Creates a new Submission in the persistence store. The id of the submission must be UNSET_ID. The manager will
     * assign an id before persisting the change.
     * </p>
     *
     * @param submission
     *            The submission to add to the persistence store
     * @param operator
     *            The name of the operator making the change to the persistence store
     * @throws IllegalArgumentException
     *             If submission or operator is null
     * @throws IllegalArgumentException
     *             If the id is not UNSET_ID
     * @throws IllegalArgumentException
     *             If the submission (once an id and creation/modification user/date are assigned) is not in a state to
     *             be persisted (i.e. if isValidToPersist returns false)
     * @throws UploadPersistenceException
     *             If there is an error making the change in the persistence
     */
    public void createSubmission(Submission submission, String operator) throws UploadPersistenceException {
        LOGGER.log(Level.INFO, new LogMessage("Submission", null, operator, "create new Submission."));
        persistenceUploadManagerHelper(submission, "submission", operator, CREATE_FLAG, submissionIdGenerator);
    }

    /**
     * <p>
     * Updates the info for a Submission in the persistence store. All fields of the submission must have values
     * assigned when this method is called.
     * </p>
     *
     * @param submission
     *            The submission to update
     * @param operator
     *            The name of the operator making the change to the persistence store
     * @throws IllegalArgumentException
     *             If submission or operator is null
     * @throws IllegalArgumentException
     *             The id is UNSET_ID
     * @throws IllegalArgumentException
     *             If the submission (once the modification user/date is set) is not in a state to be persisted (i.e. if
     *             isValidToPersist returns false)
     * @throws UploadPersistenceException
     *             If there is an error making the change in the persistence
     */
    public void updateSubmission(Submission submission, String operator) throws UploadPersistenceException {
        LOGGER.log(Level.INFO, new LogMessage("Submission", getId(submission), operator, "update Submission."));

        persistenceUploadManagerHelper(submission, "submission", operator, UPDATE_FLAG, null);
    }

    /**
     * <p>
     * Removes the given submission (by id) from the persistence. The id of the submission can not be UNSET_ID.
     * </p>
     *
     * @param submission
     *            The submission to remove
     * @param operator
     *            The name of the operator making the change to the persistence store
     * @throws IllegalArgumentException
     *             If submission or operator is null
     * @throws IllegalArgumentException
     *             If the id of the submission is UNSET_ID
     * @throws IllegalArgumentException
     *             If the submission (once the modification user/date is set) is not in a state to be persisted (i.e. if
     *             isValidToPersist returns false)
     * @throws UploadPersistenceException
     *             If there is an error making the change in the persistence
     */
    public void removeSubmission(Submission submission, String operator) throws UploadPersistenceException {
        LOGGER.log(Level.INFO, new LogMessage("Submission", getId(submission), operator, "remove Submission."));

        persistenceUploadManagerHelper(submission, "submission", operator, REMOVE_FLAG, null);
    }

    /**
     * <p>
     * Gets the Submission in the persistence store for the given id. Returns null if there is no submission for the
     * given id.
     * </p>
     *
     * @return The retrieved Submission, or null
     * @param id
     *            The id of the submission to retrieve
     * @throws IllegalArgumentException
     *             If id is <= 0
     * @throws UploadPersistenceException
     *             If there is an error retrieving the Submission from persistence
     */
    public Submission getSubmission(long id) throws UploadPersistenceException {
        LOGGER.log(Level.INFO, new LogMessage("Submission", id, null, "Retrieve Submission, delegate to persistence"));
        return persistence.loadSubmission(id);
    }

    /**
     * <p>
     * Searches the persistence for submissions meeting the filter The filter can be easily built using the
     * SubmissionFilterBuilder.
     * </p>
     * <p>
     * Changes in 1.2:
     * <ul>
     * <li>Now, uploads and submission images related to the given submission, are retrieved separately by using
     * getUploadsForSubmission() and getImagesForSubmission().</li>
     * </ul>
     * </p>
     *
     * @return The submissions meeting the filter
     * @param filter
     *            The filter to use
     * @throws IllegalArgumentException
     *             If filter is null
     * @throws UploadPersistenceException
     *             If there is an error reading the submissions from persistence
     * @throws SearchBuilderException
     *             If there is an error executing the filter
     * @throws SearchBuilderConfigurationException
     *             If the SearchBundle used by the manager is not configured for proper searching.
     */
    public Submission[] searchSubmissions(Filter filter) throws UploadPersistenceException, SearchBuilderException {
        DeliverableHelper.checkObjectNotNull(filter, "filter", LOGGER);
        CustomResultSet customResult = (CustomResultSet) submissionSearchBundle.search(filter);

        LOGGER.log(Level.INFO, new LogMessage("Upload", null, null, "search submissions with filter.") + " found: "
                + customResult.getRecordCount());
        Submission[] submissions = persistence.loadSubmissions(customResult);

        // retrieve submission declaration and submission images separately for each submission
        for (Submission submission : submissions) {
            submission.setImages(Arrays.asList(persistence.getImagesForSubmission(submission.getId())));
            submission.setSubmissionDeclaration(persistence.getSubmissionDeclaration(submission.getId()));
        }

        return submissions;
    }

    /**
     * <p>
     * Creates a new SubmissionStatus in the persistence store. The id of the submission status must be UNSET_ID. The
     * manager will assign an id before persisting the change.
     * </p>
     *
     * @param submissionStatus
     *            The submission status to add to the persistence store
     * @param operator
     *            The name of the operator making the change to the persistence store
     * @throws IllegalArgumentException
     *             If submissionStatus or operator is null
     * @throws IllegalArgumentException
     *             If the id is not UNSET_ID
     * @throws IllegalArgumentException
     *             If the submission status (once an id and creation/modification user/date are assigned) is not in a
     *             state to be persisted (i.e. if isValidToPersist returns false)
     * @throws UploadPersistenceException
     *             If there is an error making the change in the persistence
     */
    public void createSubmissionStatus(SubmissionStatus submissionStatus, String operator)
        throws UploadPersistenceException {
        LOGGER.log(Level.INFO, new LogMessage("SubmissionStatus", null, operator, "create new SubmissionStatus."));
        persistenceUploadManagerHelper(submissionStatus, "submissionStatus", operator, CREATE_FLAG,
                submissionStatusIdGenerator);
    }

    /**
     * <p>
     * Updates the info for submission status in the persistence store. All fields of the submission status must have
     * values assigned when this method is called.
     * </p>
     *
     * @param submissionStatus
     *            The submission status to update
     * @param operator
     *            The name of the operator making the change to the persistence store
     * @throws IllegalArgumentException
     *             If submissionStatus or operator is null
     * @throws IllegalArgumentException
     *             The id is UNSET_ID
     * @throws IllegalArgumentException
     *             If the submission status (once the modification user/date is set) is not in a state to be persisted
     *             (i.e. if isValidToPersist returns false)
     * @throws UploadPersistenceException
     *             If there is an error making the change in the persistence
     */
    public void updateSubmissionStatus(SubmissionStatus submissionStatus, String operator)
        throws UploadPersistenceException {
        LOGGER.log(Level.INFO, new LogMessage("SubmissionStatus", getId(submissionStatus), operator,
                "update SubmissionStatus."));

        persistenceUploadManagerHelper(submissionStatus, "submissionStatus", operator, UPDATE_FLAG, null);
    }

    /**
     * <p>
     * Removes the given submission status (by id) from the persistence. The id of the submissionStatus can not be
     * UNSET_ID.
     * </p>
     *
     * @param submissionStatus
     *            The submission status to remove
     * @param operator
     *            The name of the operator making the change to the persistence store
     * @throws IllegalArgumentException
     *             If submissionStatus or operator is null
     * @throws IllegalArgumentException
     *             If the id of the submission status is UNSET_ID
     * @throws IllegalArgumentException
     *             If the submissionStatus (once the modification user/date is set) is not in a state to be persisted
     *             (i.e. if isValidToPersist returns false)
     * @throws UploadPersistenceException
     *             If there is an error making the change in the persistence
     */
    public void removeSubmissionStatus(SubmissionStatus submissionStatus, String operator)
        throws UploadPersistenceException {
        LOGGER.log(Level.INFO, new LogMessage("SubmissionStatus", getId(submissionStatus), operator,
                "remove SubmissionStatus."));

        persistenceUploadManagerHelper(submissionStatus, "submissionStatus", operator, REMOVE_FLAG, null);
    }

    /**
     * <p>
     * Gets all the submission statuses in the persistence store.
     * </p>
     *
     * @return All SubmissionStatuses in the persistence store.
     * @throws UploadPersistenceException
     *             If there is an error accessing the persistence store
     */
    public SubmissionStatus[] getAllSubmissionStatuses() throws UploadPersistenceException {
        LOGGER.log(Level.INFO, "get All submission statuses, delegate to persistence.");

        return persistence.loadSubmissionStatuses(persistence.getAllSubmissionStatusIds());
    }

    /**
     * <p>
     * Creates a new SubmissionType in the persistence store. The id of the submission type must be UNSET_ID. The
     * manager will assign an id before persisting the change.
     * </p>
     *
     * @param submissionType
     *            the submission type to add to the persistence store.
     * @param operator
     *            the name of the operator making the change to the persistence store.
     * @throws IllegalArgumentException
     *             if submissionType or operator is <code>null</code>, id is not UNSET_ID, or the submission type (once
     *             an id and creation/modification user/date are assigned) is not in a state to be persisted (i.e. if
     *             isValidToPersist returns false).
     * @throws UploadPersistenceException
     *             if there is an error making the change in the persistence.
     * @since 1.1
     */
    public void createSubmissionType(SubmissionType submissionType, String operator) throws UploadPersistenceException {
        LOGGER.log(Level.INFO, new LogMessage("SubmissionType", null, operator, "Create new SubmissionType."));

        persistenceUploadManagerHelper(submissionType, "submissionType", operator, CREATE_FLAG,
                submissionTypeIdGenerator);
    }

    /**
     * <p>
     * Updates the info for submission type in the persistence store. All fields of the submission type must have values
     * assigned when this method is called.
     * </p>
     *
     * @param submissionType
     *            the submission type to update
     * @param operator
     *            the name of the operator making the change to the persistence store
     * @throws IllegalArgumentException
     *             if submissionType or operator is <code>null</code>, or the submission type (once the modification
     *             user/date is set) is not in a state to be persisted (i.e. if isValidToPersist returns false)
     * @throws UploadPersistenceException
     *             if there is an error making the change in the persistence.
     * @since 1.1
     */
    public void updateSubmissionType(SubmissionType submissionType, String operator) throws UploadPersistenceException {
        LOGGER.log(Level.INFO, new LogMessage("SubmissionType", getId(submissionType), operator,
                "Update SubmissionType."));

        persistenceUploadManagerHelper(submissionType, "submissionType", operator, UPDATE_FLAG, null);
    }

    /**
     * <p>
     * Removes the given submission type (by id) from the persistence. The id of the submissionType can not be UNSET_ID.
     * </p>
     *
     * @param submissionType
     *            the submission type to remove.
     * @param operator
     *            the name of the operator making the change to the persistence store.
     * @throws IllegalArgumentException
     *             if submissionType or operator is <code>null</code> or the id of the submission type is UNSET_ID.
     * @throws UploadPersistenceException
     *             if there is an error making the change in the persistence.
     * @since 1.1
     */
    public void removeSubmissionType(SubmissionType submissionType, String operator) throws UploadPersistenceException {
        LOGGER.log(Level.INFO, new LogMessage("SubmissionType", getId(submissionType), operator,
                "Remove SubmissionType."));

        persistenceUploadManagerHelper(submissionType, "submissionType", operator, REMOVE_FLAG, null);
    }

    /**
     * <p>
     * Gets all the submission types in the persistence store.
     * </p>
     *
     * @return All SubmissionTypes in the persistence store.
     * @throws UploadPersistenceException
     *             if there is an error accessing the persistence store.
     * @since 1.1
     */
    public SubmissionType[] getAllSubmissionTypes() throws UploadPersistenceException {
        LOGGER.log(Level.INFO, "Get all submission types, delegate to persistence.");

        return persistence.loadSubmissionTypes(persistence.getAllSubmissionTypeIds());
    }

    /**
     * Creates the given submission image in persistence.
     *
     * @param submissionImage
     *            the submission image to be created in persistence
     * @param operator
     *            the user that performs the operation
     * @throws IllegalArgumentException
     *             if submissionImage or operator is null, or submissionImage.isValidToPersist() returns false
     * @throws UploadPersistenceException
     *             if some error occurred when accessing the persistence
     * @since 1.2
     */
    public void createSubmissionImage(SubmissionImage submissionImage, String operator)
        throws UploadPersistenceException {
        LOGGER.log(Level.INFO, new LogMessage("SubmissionImage", null, operator, "Create new SubmissionImage."));

        DeliverableHelper.checkObjectNotNull(submissionImage, "submissionImage", LOGGER);
        DeliverableHelper.checkObjectNotNull(operator, "operator", LOGGER);
        if (!submissionImage.isValidToPersist()) {
            throw DeliverableHelper.logException(LOGGER, new IllegalArgumentException(
                    "The submission image is invalid to persist."));
        }

        // set create date and modify date
        Date now = new Date();
        submissionImage.setCreateDate(now);
        submissionImage.setModifyDate(now);

        persistence.addSubmissionImage(submissionImage);
    }

    /**
     * Updates the given submission image in persistence.
     *
     * @param submissionImage
     *            the submission image to be updated in persistence
     * @param operator
     *            the user that performs the operation
     * @throws IllegalArgumentException
     *             if submissionImage or operator is null, or submissionImage.isValidToPersist() returns false
     * @throws UploadPersistenceException
     *             if some error occurred when accessing the persistence
     * @since 1.2
     */
    public void updateSubmissionImage(SubmissionImage submissionImage, String operator)
        throws UploadPersistenceException {
        LOGGER.log(Level.INFO, new LogMessage("SubmissionImage", null, operator, "Update SubmissionImage."));
        DeliverableHelper.checkObjectNotNull(submissionImage, "submissionImage", LOGGER);
        DeliverableHelper.checkObjectNotNull(operator, "operator", LOGGER);
        if (!submissionImage.isValidToPersist()) {
            throw DeliverableHelper.logException(LOGGER, new IllegalArgumentException(
                    "The submission image is invalid to persist."));
        }

        // set modify date
        submissionImage.setModifyDate(new Date());

        persistence.updateSubmissionImage(submissionImage);
    }

    /**
     * Removes the submission image in persistence.
     *
     * @param submissionImage
     *            the submission image to be removed in persistence
     * @param operator
     *            the user that performs the operation
     * @throws IllegalArgumentException
     *             if submissionImage/operator is null, submissionImage.getSubmissionId() <= 0 or
     *             submissionImage.getImageId() <= 0
     * @throws UploadPersistenceException
     *             if some error occurred when accessing the persistence
     * @since 1.2
     */
    public void removeSubmissionImage(SubmissionImage submissionImage, String operator)
        throws UploadPersistenceException {
        LOGGER.log(Level.INFO, new LogMessage("SubmissionImage", null, operator, "Remove SubmissionImage."));
        DeliverableHelper.checkObjectNotNull(submissionImage, "submissionImage", LOGGER);
        DeliverableHelper.checkObjectNotNull(operator, "operator", LOGGER);
        DeliverableHelper.checkGreaterThanZero(submissionImage.getSubmissionId(),
                "The submission id of the submission image", LOGGER);
        DeliverableHelper.checkGreaterThanZero(submissionImage.getImageId(), "The image id of the submission image",
                LOGGER);

        persistence.removeSubmissionImage(submissionImage);
    }

    /**
     * Retrieves the MIME type with the given ID from persistence.
     *
     * @param id
     *            the ID of the MIME type to be retrieved
     * @return the retrieved MIME type or null if MIME type with the given ID doesn't exist
     * @throws IllegalArgumentException
     *             if id <= 0
     * @throws UploadPersistenceException
     *             if some error occurred when retrieving the MimeType from persistence
     * @since 1.2
     */
    public MimeType getMimeType(long id) throws UploadPersistenceException {
        LOGGER.log(Level.INFO, new LogMessage("MimeType", id, null, "Retrieve MimeType, delegate to persistence."));

        DeliverableHelper.checkGreaterThanZero(id, "id", LOGGER);
        return persistence.loadMimeType(id);
    }

    /**
     * Retrieves all MIME types from persistence. This method returns an empty array if no MIME types were found.
     *
     * @return the retrieved MIME types (not null, doesn't contain null)
     * @throws UploadPersistenceException
     *             if some error occurred when accessing the persistence
     * @since 1.2
     */
    public MimeType[] getAllMimeTypes() throws UploadPersistenceException {
        LOGGER.log(Level.INFO, new LogMessage("MimeType", null, null,
                "Retrieve All MimeTypes, delegate to persistence."));
        return persistence.loadMimeTypes(persistence.getAllMimeTypeIds());
    }

    /**
     * Retrieves the user submissions for project with the given ID. If projectId or ownerId is unknown, this method
     * returns an empty array.
     *
     * @param projectId
     *            the ID of the project
     * @param ownerId
     *            the ID of the submission owner
     * @return the retrieved user submissions for project (not null, doesn't contain null)
     * @throws IllegalArgumentException
     *             if projectId <= 0 or ownerId <= 0
     * @throws UploadPersistenceException
     *             if some error occurred when accessing the persistence
     * @since 1.2
     */
    public Submission[] getUserSubmissionsForProject(long projectId, long ownerId) throws UploadPersistenceException {
        LOGGER.log(Level.INFO, new LogMessage("Submission", null, null, MessageFormat.format(
                "Retrieve Submissions for project id [{0}] and owner id [{1}].", projectId, ownerId)));
        DeliverableHelper.checkGreaterThanZero(projectId, "projectId", LOGGER);
        DeliverableHelper.checkGreaterThanZero(ownerId, "ownerId", LOGGER);

        return persistence.getUserSubmissionsForProject(projectId, ownerId);
    }

    /**
     * Retrieves the project submissions. If projectId is unknown, this method returns an empty array.
     *
     * @param projectId
     *            the ID of the project
     * @return the retrieved project submissions (not null, doesn't contain null)
     * @throws IllegalArgumentException
     *             if projectId <= 0
     * @throws UploadPersistenceException
     *             if some error occurred when accessing the persistence
     * @since 1.2
     */
    public Submission[] getProjectSubmissions(long projectId) throws UploadPersistenceException {
        LOGGER.log(Level.INFO, new LogMessage("Submission", null, null, MessageFormat.format(
                "Retrieve Submissions for project id [{0}].", projectId)));

        DeliverableHelper.checkGreaterThanZero(projectId, "projectId", LOGGER);

        return persistence.getProjectSubmissions(projectId);
    }

    /**
     * Retrieves the images for submission with the given ID. If submissionId is unknown, this method returns an empty
     * array.
     *
     * @param submissionId
     *            the ID of the submission
     * @return the retrieved images for submission (not null, doesn't contain null)
     * @throws IllegalArgumentException
     *             if submissionId <= 0
     * @throws UploadPersistenceException
     *             if some error occurred when accessing the persistence
     * @since 1.2
     */
    public SubmissionImage[] getImagesForSubmission(long submissionId) throws UploadPersistenceException {
        LOGGER.log(Level.INFO, new LogMessage("SubmissionImage", null, null, MessageFormat.format(
                "Retrieve SubmissionImages for submission with id [{0}].", submissionId)));

        DeliverableHelper.checkGreaterThanZero(submissionId, "submissionId", LOGGER);

        return persistence.getImagesForSubmission(submissionId);
    }

    /**
     * Helper method for PersistenceUploadManager. All the create, update, remove methods delegate to this method. It
     * create (update, remove) AuditedDeliverableStructure object from the persistence.
     *
     * @param obj
     *            object to manipulate
     * @param name
     *            object name which in manipulate in this method
     * @param operator
     *            the operator name
     * @param operation
     *            identifier for the caller
     * @param idGenerator
     *            the IDGenerator to use
     * @throws IllegalArgumentException
     *             if obj or operator is <code>null</code>, id is not UNSET_ID, or the obj (once an id and
     *             creation/modification user/date are assigned) is not in a state to be persisted (i.e. if
     *             isValidToPersist returns false).
     * @throws UploadPersistenceException
     *             If there is an error making the change in the persistence
     */
    private void persistenceUploadManagerHelper(AuditedDeliverableStructure obj, String name, String operator,
            int operation, IDGenerator idGenerator) throws UploadPersistenceException {
        DeliverableHelper.checkObjectNotNull(obj, name, LOGGER);
        DeliverableHelper.checkObjectNotNull(operator, "operator", LOGGER);

        // Get current time.
        Date now = new Date();

        if (operation == CREATE_FLAG) {
            // Branch for CREATE_FLAG operation.

            // id should not been set yet.
            if (obj.getId() != AuditedDeliverableStructure.UNSET_ID) {
                LOGGER.log(Level.ERROR, "The id of the " + name + " must be UNSET_ID, " + obj.getId() + " received");
                throw new IllegalArgumentException("The id of the " + name + " must be UNSET_ID, " + obj.getId()
                        + " received");
            }

            // Set the creation user and creation date.
            obj.setCreationUser(operator);
            obj.setCreationTimestamp(now);

            // Create an id using idGenerator.
            try {
                obj.setId(idGenerator.getNextID());
                LOGGER.log(Level.INFO, "generate next id from the idGenerator:" + idGenerator.getIDName());
            } catch (IDGenerationException ide) {
                LOGGER.log(Level.ERROR, "failed to generate an " + name + " id. \n"
                        + LogMessage.getExceptionStackTrace(ide));
                throw new UploadPersistenceException("Failed to generate an " + name + " id.", ide);
            }
        } else {
            // Branch for UPDATE_FLAG, REMOVE_FLAG operation

            // id field can not equal to UNSET_ID.
            if (obj.getId() == AuditedDeliverableStructure.UNSET_ID) {
                LOGGER.log(Level.ERROR, "The id of the " + name + " can't be UNSET_ID.");
                throw new IllegalArgumentException("The id of the " + name + " can't be UNSET_ID.");
            }
        }

        // Set the modification user and modification date.
        obj.setModificationUser(operator);
        obj.setModificationTimestamp(now);

        // Check if the object is ready to persist.
        if (!obj.isValidToPersist()) {
            LOGGER.log(Level.ERROR, "The " + name + " is not in a state to be persisted.");
            throw new IllegalArgumentException("The " + name + " is not in a state to be persisted.");
        }

        // Save change to persistence.
        persistApply(obj, operation);
    }

    /**
     * This is a helper method for applying operations on persistence layer.
     *
     * @param obj
     *            the object to manipulate
     * @param operation
     *            operation identifier for the caller
     * @throws UploadPersistenceException
     *             If there is an error making the change in the persistence
     */
    private void persistApply(AuditedDeliverableStructure obj, int operation) throws UploadPersistenceException {
        if (obj instanceof Upload) {
            switch (operation) {
            case CREATE_FLAG:
                // Add to persistence
                persistence.addUpload((Upload) obj);
                break;

            case UPDATE_FLAG:
                // Update the persistence
                persistence.updateUpload((Upload) obj);
                break;

            case REMOVE_FLAG:
                // Remove update from persistence
                persistence.removeUpload((Upload) obj);
                break;

            default:
                break;
            }

        } else if (obj instanceof UploadType) {
            switch (operation) {
            case CREATE_FLAG:
                // Add to persistence
                persistence.addUploadType((UploadType) obj);
                break;

            case UPDATE_FLAG:
                // Update the persistence
                persistence.updateUploadType((UploadType) obj);
                break;

            case REMOVE_FLAG:
                // Remove update type from persistence
                persistence.removeUploadType((UploadType) obj);
                break;

            default:
                break;
            }
        } else if (obj instanceof UploadStatus) {
            switch (operation) {
            case CREATE_FLAG:
                // Add to persistence
                persistence.addUploadStatus((UploadStatus) obj);
                break;

            case UPDATE_FLAG:
                // Update the persistence
                persistence.updateUploadStatus((UploadStatus) obj);
                break;

            case REMOVE_FLAG:
                // Remove upload status from persistence
                persistence.removeUploadStatus((UploadStatus) obj);
                break;

            default:
                break;
            }
        } else if (obj instanceof Submission) {
            switch (operation) {
            case CREATE_FLAG:
                // Add to persistence
                persistence.addSubmission((Submission) obj);
                break;

            case UPDATE_FLAG:
                // Update the persistence
                persistence.updateSubmission((Submission) obj);
                break;

            case REMOVE_FLAG:
                // Remove submission from persistence
                persistence.removeSubmission((Submission) obj);
                break;

            default:
                break;
            }
        } else if (obj instanceof SubmissionStatus) {
            switch (operation) {
            case CREATE_FLAG:
                // Add to persistence
                persistence.addSubmissionStatus((SubmissionStatus) obj);
                break;

            case UPDATE_FLAG:
                // Update the persistence
                persistence.updateSubmissionStatus((SubmissionStatus) obj);
                break;

            case REMOVE_FLAG:
                // Remove submission from persistence
                persistence.removeSubmissionStatus((SubmissionStatus) obj);
                break;

            default:
                break;
            }
        } else if (obj instanceof SubmissionType) {
            switch (operation) {
            case CREATE_FLAG:
                // Add to persistence
                persistence.addSubmissionType((SubmissionType) obj);
                break;

            case UPDATE_FLAG:
                // Update the persistence
                persistence.updateSubmissionType((SubmissionType) obj);
                break;

            case REMOVE_FLAG:
                // Remove from persistence
                persistence.removeSubmissionType((SubmissionType) obj);
                break;

            default:
                break;
            }
        }
    }

    /**
     * Gets the id field from the given entity. if the entity is null, should return -1.
     *
     * @param entity
     *            the entity to extract the id field.
     * @return the value of id.
     * @since 1.2
     */
    private static long getId(IdentifiableDeliverableStructure entity) {
        if (entity != null) {
            return entity.getId();
        }

        return -1;
    }
}
