/*
 * Copyright (C) 2006-2018 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable.persistence.sql;

import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.management.deliverable.AuditedDeliverableStructure;
import com.topcoder.management.deliverable.IdentifiableDeliverableStructure;
import com.topcoder.management.deliverable.MimeType;
import com.topcoder.management.deliverable.NamedDeliverableStructure;
import com.topcoder.management.deliverable.Submission;
import com.topcoder.management.deliverable.SubmissionDeclaration;
import com.topcoder.management.deliverable.SubmissionExternalContent;
import com.topcoder.management.deliverable.SubmissionExternalContentProperty;
import com.topcoder.management.deliverable.SubmissionExternalContentType;
import com.topcoder.management.deliverable.SubmissionImage;
import com.topcoder.management.deliverable.SubmissionStatus;
import com.topcoder.management.deliverable.SubmissionType;
import com.topcoder.management.deliverable.Upload;
import com.topcoder.management.deliverable.UploadStatus;
import com.topcoder.management.deliverable.UploadType;
import com.topcoder.management.deliverable.persistence.PersistenceException;
import com.topcoder.management.deliverable.persistence.UploadPersistence;
import com.topcoder.management.deliverable.persistence.UploadPersistenceException;
import com.topcoder.management.deliverable.persistence.sql.Helper.DataType;
import com.topcoder.management.deliverable.persistence.sql.logging.LogMessage;
import com.topcoder.management.project.FileType;
import com.topcoder.management.project.Prize;
import com.topcoder.management.project.PrizeType;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;
import com.topcoder.util.sql.databaseabstraction.CustomResultSet;
import com.topcoder.util.sql.databaseabstraction.InvalidCursorStateException;
import com.topcoder.util.sql.databaseabstraction.NullColumnValueException;
/**
 * <p>
 * The SqlUploadPersistence class implements the UploadPersistence interface, in order to persist to the database
 * structure given in the deliverable_management.sql script.
 * </p>
 * <p>
 * This class does not cache a Connection to the database. Instead a new Connection is used on every method call. All
 * methods in this class will just create and execute a single PreparedStatement.
 * </p>
 * <p>
 * Sample configuration:
 *
 * <pre>
 * &lt;CMConfig&gt;
 *   &lt;Config name="com.topcoder.db.connectionfactory.DBConnectionFactoryImpl"&gt;
 *       &lt;Property name="connections"&gt;
 *           &lt;Property name="default"&gt;
 *               &lt;Value&gt;informix_connection&lt;/Value&gt;
 *           &lt;/Property&gt;
 *           &lt;Property name="informix_connection"&gt;
 *               &lt;Property name="producer"&gt;
 *                   &lt;Value&gt;com.topcoder.db.connectionfactory.producers.JDBCConnectionProducer&lt;/Value&gt;
 *               &lt;/Property&gt;
 *               &lt;Property name="parameters"&gt;
 *                   &lt;Property name="jdbc_driver"&gt;
 *                       &lt;Value&gt;com.informix.jdbc.IfxDriver&lt;/Value&gt;
 *                   &lt;/Property&gt;
 *                   &lt;Property name="jdbc_url"&gt;
 *                       &lt;Value&gt;jdbc:informix-sqli://localhost:9088/dm:informixserver=informix_db&lt;/Value&gt;
 *                   &lt;/Property&gt;
 *                   &lt;Property name="user"&gt;
 *                       &lt;Value&gt;informix&lt;/Value&gt;
 *                   &lt;/Property&gt;
 *                   &lt;Property name="password"&gt;
 *                       &lt;Value&gt;test&lt;/Value&gt;
 *                   &lt;/Property&gt;
 *               &lt;/Property&gt;
 *           &lt;/Property&gt;
 *       &lt;/Property&gt;
 *   &lt;/Config&gt;
 * &lt;/CMConfig&gt;
 * </pre>
 *
 * </p>
 * <p>
 * Sample usage:
 *
 * <pre>
 * // first a DBConnectionFactory instance is created.
 * DBConnectionFactory connectionFactory = new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName());
 * // create the instance of SqlUploadPersistence class, using the default connection name
 * UploadPersistence persistence1 = new SqlUploadPersistence(connectionFactory);
 * // or create the instance of SqlUploadPersistence class, using the given connection name
 * UploadPersistence persistence2 = new SqlUploadPersistence(connectionFactory, &quot;informix_connection&quot;);
 * // create and save submission status
 * SubmissionStatus submissionStatus = new SubmissionStatus();
 * submissionStatus.setId(10);
 * submissionStatus.setName(&quot;Active&quot;);
 * submissionStatus.setDescription(&quot;Active submission&quot;);
 * submissionStatus.setCreationUser(&quot;admin&quot;);
 * submissionStatus.setCreationTimestamp(new Date());
 * submissionStatus.setModificationUser(&quot;admin&quot;);
 * submissionStatus.setModificationTimestamp(new Date());
 * persistence.addSubmissionStatus(submissionStatus);
 * </pre>
 *
 * <pre>
 * // Create submission image
 * SubmissionImage submissionImage = new SubmissionImage();
 * submissionImage.setSubmissionId(submission.getId());
 * submissionImage.setImageId(1);
 * submissionImage.setSortOrder(1);
 * uploadPersistence.addSubmissionImage(submissionImage);
 *
 * // Update the submission image
 * submissionImage.setSortOrder(0);
 * uploadPersistence.updateSubmissionImage(submissionImage);
 *
 * // Remove the submission image
 * uploadPersistence.removeSubmissionImage(submissionImage);
 *
 * // Retrieve the MIME type with ID=1
 * MimeType mimeType = uploadPersistence.loadMimeType(1);
 *
 * // Retrieve IDs of all MIME types
 * long[] mimeTypeIds = uploadPersistence.getAllMimeTypeIds();
 *
 * // Retrieve all MIME types by their IDs
 * MimeType[] mimeTypes = uploadPersistence.loadMimeTypes(mimeTypeIds);
 *
 * // Retrieve the submissions for project with ID=1 and user with ID=1
 * Submission[] submissions = uploadPersistence.getUserSubmissionsForProject(1, 1);
 *
 * // Retrieve all submissions for project with ID=1
 * submissions = uploadPersistence.getProjectSubmissions(1);
 *
 * // Retrieve the images for submission with ID=1
 * SubmissionImage[] submissionImages = uploadPersistence.getImagesForSubmission(1);
 * </pre>
 *
 * </p>
 * <p>
 * Changes in 1.1: Additional methods where added:
 * <ul>
 * <li>addSubmissionType</li>
 * <li>removeSubmissionType</li>
 * <li>updateSubmissionType</li>
 * <li>loadSubmissionType</li>
 * <li>loadSubmissionTypes</li>
 * <li>getAllSubmissionTypeIds</li>
 * </ul>
 * </p>
 * <p>
 * Changes in 1.2:
 * <ul>
 * <li>Updated existing methods to support new Upload and Submission properties.</li>
 * <li>Updated the sql statement for new database schema.</li>
 * <li>Added methods for managing SubmissionImage entities.</li>
 * <li>Added methods for retrieving MimeType entities.</li>
 * <li>Added methods for retrieving project/user submissions.</li>
 * <li>Added method for retrieving images associated with submission.</li>
 * <li>Added logging for parameter checking.</li>
 * <li>Refactor logging sequence.</li>
 * <li>Added generic type support.</li>
 * </ul>
 * </p>
 * <p>
 * 
 * <p>
 * Changes in 1.3:
 * <ul>
 * <li>Updated existing methods and sql statement the support new Upload and Submission properties.
 * A Submission can only have one upload now.</li>
 * </ul>
 * </p>
 * 
 * <p>
 * Changes in 1.4 (TC Direct Replatforming Release 5):
 * <ul>
 * <li>Added {@link #getSubmissionDeclaration(long)} method to retrieve SubmissionDeclaration for submission.</li>
 * <li>Added {@link #loadSubmission(CustomResultSet)} method to set <code>Prize</code> to <code>Submission</code>.</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 1.5 (Release Assembly - TC Direct Issue Tracking Tab Update Assembly 2 v1.0) change notes:
 *   <ol>
 *     <li>Update {@link #loadUpload(CustomResultSet)} to load the project phase id.</li>
 *   </ol>
 * </p>
 *
 * <p>
 * Version 1.6 - Topcoder - Change Download URL in Direct Application
 *   <ol>
 *     <li>Update {@link #loadUpload(CustomResultSet)} to load url.</li>
 *   </ol>
 * </p>
 *
 * <strong>Thread Safety:</strong> This class is immutable and thread-safe in the sense that multiple threads can not
 * corrupt its internal data structures. However, the results if used from multiple threads can be unpredictable as the
 * database is changed from different threads. This can equally well occur when the component is used on multiple
 * machines or multiple instances are used, so this is not a thread-safety concern.
 * </p>
 *
 * @author aubergineanode, saarixx, urtks, George1
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.6
 */
public class SqlUploadPersistence implements UploadPersistence {

    /**
     * <p>
     * Logger instance using the class name as category.
     * </p>
     */
    private static final Log LOGGER = LogManager.getLog(SqlUploadPersistence.class.getName());

    /**
     * <p>
     * Represents a place holder for id column in the sql statement which will be replaced by the actual id column name.
     * </p>
     */
    private static final String ID_NAME_PLACEHOLDER = "@id";

    /**
     * <p>
     * Represents a place holder for table name in the sql statement which will be replaced by the actual table name.
     * </p>
     */
    private static final String TABLE_NAME_PLACEHOLDER = "@table";

    /**
     * <p>
     * Represents the sql statement to add named deliverable structure.
     * </p>
     */
    private static final String ADD_NAMED_ENTITY_SQL = "INSERT INTO @table "
            + "(@id, create_user, create_date, modify_user, modify_date, name, description) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?)";

    /**
     * <p>
     * Represents the argument types for the sql statement to add named deliverable structure.
     * </p>
     */
    private static final DataType[] ADD_NAMED_ENTITY_ARGUMENT_TYPES = new DataType[] {Helper.LONG_TYPE,
        Helper.STRING_TYPE, Helper.DATE_TYPE, Helper.STRING_TYPE, Helper.DATE_TYPE, Helper.STRING_TYPE,
        Helper.STRING_TYPE};

    /**
     * <p>
     * Represents the sql statement to remove audited deliverable structure.
     * </p>
     */
    private static final String REMOVE_ENTITY_SQL = "DELETE FROM @table WHERE @id=?";

    /**
     * <p>
     * Represents the argument types for the sql statement to remove audited deliverable structure.
     * </p>
     */
    private static final DataType[] REMOVE_ENTITY_ARGUMENT_TYPES = new DataType[] {Helper.LONG_TYPE};

    /**
     * <p>
     * Represents the sql statement to update named deliverable structure.
     * </p>
     */
    private static final String UPDATE_NAMED_ENTITY_SQL = "UPDATE @table "
            + "SET modify_user=?, modify_date=?, name=?, description=? WHERE @id=?";

    /**
     * <p>
     * Represents the argument types for the sql statement to named deliverable structure.
     * </p>
     */
    private static final DataType[] UPDATE_NAMED_ENTITY_ARGUMENT_TYPES = new DataType[] {Helper.STRING_TYPE,
        Helper.DATE_TYPE, Helper.STRING_TYPE, Helper.STRING_TYPE, Helper.LONG_TYPE};

    /**
     * <p>
     * Represents the sql statement to load named deliverable structure.
     * </p>
     */
    private static final String LOAD_NAMED_ENTITIES_SQL = "SELECT "
            + "@id, create_user, create_date, modify_user, modify_date, name, description "
            + "FROM @table WHERE @id IN ";

    /**
     * <p>
     * Represents the column types for the result set which is returned by executing the sql statement to load named
     * deliverable structure.
     * </p>
     */
    private static final DataType[] LOAD_NAMED_ENTITIES_COLUMN_TYPES = new DataType[] {Helper.LONG_TYPE,
        Helper.STRING_TYPE, Helper.DATE_TYPE, Helper.STRING_TYPE, Helper.DATE_TYPE, Helper.STRING_TYPE,
        Helper.STRING_TYPE};

    /**
     * <p>
     * Represents the sql statement to load all audited deliverable structure ids.
     * </p>
     */
    private static final String GET_ALL_ENTITY_IDS_SQL = "SELECT @id FROM @table";

    /**
     * <p>
     * Represents the column types for the result set which is returned by executing the sql statement to load all
     * audited deliverable structure ids.
     * </p>
     */
    private static final DataType[] GET_ALL_ENTITY_IDS_COLUMN_TYPES = new DataType[] {Helper.LONG_TYPE};

    /**
     * <p>
     * Represents the sql statement to add upload.
     * </p>
     * <p>
     * Changes in version 1.2: add upload_desc column.
     * </p>
     */
    private static final String ADD_UPLOAD_SQL = "INSERT INTO upload "
            + "(upload_id, create_user, create_date, modify_user, modify_date, "
            + "project_id, project_phase_id, resource_id, upload_type_id, upload_status_id, parameter, upload_desc) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    /**
     * <p>
     * Represents the argument types for the sql statement to add upload.
     * </p>
     * <p>
     * Changes in version 1.2: add data type for description field.
     * </p>
     */
    private static final DataType[] ADD_UPLOAD_ARGUMENT_TYPES = new DataType[] {Helper.LONG_TYPE, Helper.STRING_TYPE,
        Helper.DATE_TYPE, Helper.STRING_TYPE, Helper.DATE_TYPE, Helper.LONG_TYPE, Helper.LONG_TYPE, Helper.LONG_TYPE, Helper.LONG_TYPE,
        Helper.LONG_TYPE, Helper.STRING_TYPE, Helper.STRING_TYPE};

    /**
     * <p>
     * Represents the sql statement to update upload.
     * </p>
     * <p>
     * Changes in version 1.2: add upload_desc column to update.
     * </p>
     */
    private static final String UPDATE_UPLOAD_SQL = "UPDATE upload " + "SET modify_user=?, modify_date=?, "
            + "project_id=?, project_phase_id=?, resource_id=?, upload_type_id=?, upload_status_id=?, parameter=?, upload_desc=? "
            + "WHERE upload_id=?";

    /**
     * <p>
     * Represents the argument types for the sql statement to update upload.
     * </p>
     * <p>
     * Changes in version 1.2: add data type for description field.
     * </p>
     */
    private static final DataType[] UPDATE_UPLOAD_ARGUMENT_TYPES = new DataType[] {Helper.STRING_TYPE,
        Helper.DATE_TYPE, Helper.LONG_TYPE, Helper.LONG_TYPE, Helper.LONG_TYPE, Helper.LONG_TYPE, Helper.LONG_TYPE, Helper.STRING_TYPE,
        Helper.STRING_TYPE, Helper.LONG_TYPE};

    /**
     * <p>
     * Represents the sql statement to add submission.
     * </p>
     * <p>
     * Changes in version 1.2: add new columns.
     * </p>
     */
    private static final String ADD_SUBMISSION_SQL = "INSERT INTO submission "
            + "(submission_id, create_user, create_date, modify_user, modify_date, "
            + "submission_status_id, submission_type_id, screening_score, "
            + "initial_score, final_score, placement, user_rank, mark_for_purchase, prize_id, upload_id)"
            + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    /**
     * <p>
     * Represents the argument types for the sql statement to add submission.
     * </p>
     * <p>
     * Changes in version 1.2: add new data types for new columns.
     * </p>
     */
    private static final DataType[] ADD_SUBMISSION_ARGUMENT_TYPES = new DataType[] {Helper.LONG_TYPE,
        Helper.STRING_TYPE, Helper.DATE_TYPE, Helper.STRING_TYPE, Helper.DATE_TYPE, Helper.LONG_TYPE, Helper.LONG_TYPE,
        Helper.DOUBLE_TYPE, Helper.DOUBLE_TYPE, Helper.DOUBLE_TYPE, Helper.LONG_TYPE,
        Helper.INTEGER_TYPE, Helper.BOOLEAN_TYPE, Helper.LONG_TYPE, Helper.LONG_TYPE};
		
    /**
     * <p>
     * Represents the sql statement to add submission image.
     * </p>
     *
     * @since 1.2
     */
    private static final String ADD_SUBMISSION_IMAGE_SQL = "INSERT INTO submission_image(submission_id, image_id,"
            + " sort_order, modify_date, create_date) VALUES (?, ?, ?, ?, ?)";

    /**
     * <p>
     * Represents the argument types for the sql statement to add submission image.
     * </p>
     *
     * @since 1.2
     */
    private static final DataType[] ADD_SUBMISSION_IMAGE_ARGUMENT_TYPES = new DataType[] {Helper.LONG_TYPE,
        Helper.INTEGER_TYPE, Helper.INTEGER_TYPE, Helper.DATE_TYPE, Helper.DATE_TYPE};

    /**
     * <p>
     * Represents the sql statement to update submission image.
     * </p>
     *
     * @since 1.2
     */
    private static final String UPDATE_SUBMISSION_IMAGE_SQL = "UPDATE submission_image SET sort_order = ?, "
            + "modify_date = ?, create_date = ? WHERE submission_id = ? AND image_id = ?";

    /**
     * <p>
     * Represents the argument types for the sql statement to update submission image.
     * </p>
     *
     * @since 1.2
     */
    private static final DataType[] UPDATE_SUBMISSION_IMAGE_ARGUMENT_TYPES = new DataType[] {Helper.INTEGER_TYPE,
        Helper.DATE_TYPE, Helper.DATE_TYPE, Helper.LONG_TYPE, Helper.INTEGER_TYPE};

    /**
     * <p>
     * Represents the sql statement to delete submission image.
     * </p>
     *
     * @since 1.2
     */
    private static final String DELETE_SUBMISSION_IMAGE_SQL = "DELETE FROM submission_image"
            + " WHERE submission_id = ? AND image_id = ?";

    /**
     * <p>
     * Represents the sql statement to update submission.
     * </p>
     * <p>
     * Changes in version 1.2: changes to support new columns.
     * </p>
     */
    private static final String UPDATE_SUBMISSION_SQL = "UPDATE submission "
            + "SET modify_user = ?, modify_date = ?, submission_status_id = ?, submission_type_id = ?, "
            + "screening_score = ?, initial_score = ?, final_score = ?, placement = ?, "
            + " user_rank = ?, mark_for_purchase = ?, prize_id = ?, upload_id = ? WHERE submission_id = ?";

    /**
     * <p>
     * Represents the argument types for the sql statement to update submission.
     * </p>
     * <p>
     * Changes in version 1.2: add new data types to support new columns.
     * </p>
     */
    private static final DataType[] UPDATE_SUBMISSION_WITH_ARGUMENT_TYPES = new DataType[] {Helper.STRING_TYPE,
        Helper.DATE_TYPE, Helper.LONG_TYPE, Helper.LONG_TYPE, Helper.DOUBLE_TYPE, Helper.DOUBLE_TYPE,
        Helper.DOUBLE_TYPE, Helper.LONG_TYPE, Helper.INTEGER_TYPE, Helper.BOOLEAN_TYPE,
        Helper.LONG_TYPE, Helper.LONG_TYPE, Helper.LONG_TYPE};

    /**
     * <p>
     * Represents the sql statement to load uploads.
     * </p>
     * <p>
     * Changes in 1.2: add upload_desc column.
     * </p>
     */
    private static final String LOAD_UPLOADS_SQL = "SELECT "
            + "upload.upload_id, upload.create_user, upload.create_date, upload.modify_user, upload.modify_date, "
            + "upload.project_id, upload.project_phase_id, upload.resource_id, upload.parameter, upload.upload_desc, "
            + "upload_type_lu.upload_type_id, upload_type_lu.create_user, upload_type_lu.create_date, "
            + "upload_type_lu.modify_user, upload_type_lu.modify_date, "
            + "upload_type_lu.name, upload_type_lu.description, "
            + "upload_status_lu.upload_status_id, upload_status_lu.create_user, upload_status_lu.create_date, "
            + "upload_status_lu.modify_user, upload_status_lu.modify_date, "
            + "upload_status_lu.name, upload_status_lu.description " + "FROM upload INNER JOIN upload_type_lu "
            + "ON upload.upload_type_id=upload_type_lu.upload_type_id " + "INNER JOIN upload_status_lu "
            + "ON upload.upload_status_id=upload_status_lu.upload_status_id " + "WHERE upload_id IN ";

    /**
     * <p>
     * Represents the column types for the result set which is returned by executing the sql statement to load uploads.
     * </p>
     * <p>
     * Changes in 1.2: add upload_desc column.
     * </p>
     */
    private static final DataType[] LOAD_UPLOADS_COLUMN_TYPES = new DataType[] {Helper.LONG_TYPE, Helper.STRING_TYPE,
        Helper.DATE_TYPE, Helper.STRING_TYPE, Helper.DATE_TYPE, Helper.LONG_TYPE, Helper.LONG_TYPE, Helper.LONG_TYPE, Helper.STRING_TYPE,
        Helper.STRING_TYPE, Helper.LONG_TYPE, Helper.STRING_TYPE, Helper.DATE_TYPE, Helper.STRING_TYPE,
        Helper.DATE_TYPE, Helper.STRING_TYPE, Helper.STRING_TYPE, Helper.LONG_TYPE, Helper.STRING_TYPE,
        Helper.DATE_TYPE, Helper.STRING_TYPE, Helper.DATE_TYPE, Helper.STRING_TYPE, Helper.STRING_TYPE};

    /**
     * <p>
     * Represents the sql statement to load mime types.
     * </p>
     * @since 1.2
     */
    private static final String LOAD_MIME_TYPES_SQL = "SELECT mime_type_id, file_type_lu.file_type_id,"
            + "file_type_lu.file_type_desc, file_type_lu.sort, file_type_lu.image_file_ind, file_type_lu.extension,"
            + "file_type_lu.bundled_file_ind, mime_type_desc FROM mime_type_lu "
            + "INNER JOIN file_type_lu ON mime_type_lu.file_type_id = file_type_lu.file_type_id "
            + "WHERE mime_type_id IN";

    /**
     * <p>
     * Represents the sql statement to load submissions.
     * </p>
     * <p>
     * Changes in version 1.2: updated according to the new database schema.
     * </p>
     */
    private static final String LOAD_SUBMISSIONS_SQL = "SELECT "
            + "submission.submission_id, submission.create_user, submission.create_date, "
            + "submission.modify_user, submission.modify_date, submission_status_lu.submission_status_id, "
            + "submission_status_lu.create_user, submission_status_lu.create_date, "
            + "submission_status_lu.modify_user, submission_status_lu.modify_date, "
            + "submission_status_lu.name, submission_status_lu.description, "
            + "submission_type_lu.submission_type_id, "
            + "submission_type_lu.create_user, submission_type_lu.create_date, "
            + "submission_type_lu.modify_user, submission_type_lu.modify_date, "
            + "submission_type_lu.name, submission_type_lu.description, "
            + "submission.screening_score, submission.initial_score, submission.final_score, submission.placement, "
            + "submission.user_rank, submission.mark_for_purchase, "
            + "prize.prize_id, prize.place, prize.prize_amount, prize.prize_type_id, prize.number_of_submissions, "
            + "prize.create_user, prize.create_date, prize.modify_user, prize.modify_date, "
            + "prize_type_lu.prize_type_desc, "
            + "upload.upload_id, upload.create_user, upload.create_date, upload.modify_user, upload.modify_date, "
            + "upload.project_id, upload.project_phase_id, upload.resource_id, upload.parameter, upload.upload_desc, "
            + "upload_type_lu.upload_type_id, upload_type_lu.create_user, upload_type_lu.create_date, "
            + "upload_type_lu.modify_user, upload_type_lu.modify_date, "
            + "upload_type_lu.name, upload_type_lu.description, "
            + "upload_status_lu.upload_status_id, upload_status_lu.create_user, upload_status_lu.create_date, "
            + "upload_status_lu.modify_user, upload_status_lu.modify_date, "
            + "upload_status_lu.name, upload_status_lu.description " + "FROM submission "
            + "INNER JOIN submission_status_lu ON submission.submission_status_id "
            + "= submission_status_lu.submission_status_id "
            + "INNER JOIN submission_type_lu ON submission.submission_type_id = submission_type_lu.submission_type_id "
            + "LEFT JOIN prize ON submission.prize_id = prize.prize_id "
            + "LEFT JOIN prize_type_lu ON prize.prize_type_id = prize_type_lu.prize_type_id "
            + "INNER JOIN upload ON submission.upload_id = upload.upload_id "
            + "INNER JOIN upload_type_lu ON upload.upload_type_id=upload_type_lu.upload_type_id "
            + "INNER JOIN upload_status_lu ON upload.upload_status_id=upload_status_lu.upload_status_id "
            + "WHERE submission.submission_id IN ";

    /**
     * <p>
     * Represents the column types for the result set which is returned by executing the sql statement to load
     * submissions.
     * </p>
     * <p>
     * Changes in version 1.2: add new data type for new sql statement.
     * </p>
     */
    private static final DataType[] LOAD_SUBMISSIONS_COLUMN_TYPES = new DataType[] {Helper.LONG_TYPE,
        Helper.STRING_TYPE, Helper.DATE_TYPE, Helper.STRING_TYPE, Helper.DATE_TYPE, Helper.LONG_TYPE,
        Helper.STRING_TYPE, Helper.DATE_TYPE, Helper.STRING_TYPE, Helper.DATE_TYPE, Helper.STRING_TYPE,
        Helper.STRING_TYPE,

        Helper.LONG_TYPE, Helper.STRING_TYPE, Helper.DATE_TYPE, Helper.STRING_TYPE, Helper.DATE_TYPE,
        Helper.STRING_TYPE, Helper.STRING_TYPE,

        Helper.DOUBLE_TYPE, Helper.DOUBLE_TYPE, Helper.DOUBLE_TYPE, Helper.LONG_TYPE,
        Helper.INTEGER_TYPE, Helper.BOOLEAN_TYPE,

        Helper.LONG_TYPE, Helper.INTEGER_TYPE, Helper.DOUBLE_TYPE, Helper.LONG_TYPE, Helper.INTEGER_TYPE,
        Helper.STRING_TYPE, Helper.DATE_TYPE, Helper.STRING_TYPE, Helper.DATE_TYPE, Helper.STRING_TYPE,
        
        Helper.LONG_TYPE, Helper.STRING_TYPE,
        Helper.DATE_TYPE, Helper.STRING_TYPE, Helper.DATE_TYPE, Helper.LONG_TYPE, Helper.LONG_TYPE, Helper.LONG_TYPE, Helper.STRING_TYPE,
        Helper.STRING_TYPE, Helper.LONG_TYPE, Helper.STRING_TYPE, Helper.DATE_TYPE, Helper.STRING_TYPE,
        Helper.DATE_TYPE, Helper.STRING_TYPE, Helper.STRING_TYPE, Helper.LONG_TYPE, Helper.STRING_TYPE,
        Helper.DATE_TYPE, Helper.STRING_TYPE, Helper.DATE_TYPE, Helper.STRING_TYPE, Helper.STRING_TYPE
    };

    /**
     * Represents the sql statement for retrieving user submission for the given project.
     *
     * @since 1.2
     */
    private static final String GET_USER_SUBMISSIONS_FOR_PROJECT_SQL = "SELECT "
            + "submission.submission_id, submission.create_user, submission.create_date, submission.modify_user,"
            + "submission.modify_date, submission_status_lu.submission_status_id, submission_status_lu.create_user,"
            + "submission_status_lu.create_date, submission_status_lu.modify_user, submission_status_lu.modify_date,"
            + "submission_status_lu.name, submission_status_lu.description, submission_type_lu.submission_type_id,"
            + "submission_type_lu.create_user, submission_type_lu.create_date, submission_type_lu.modify_user,"
            + "submission_type_lu.modify_date, submission_type_lu.name, submission_type_lu.description, "
            + "submission.screening_score, submission.initial_score, submission.final_score, submission.placement, "
            + " submission.user_rank, submission.mark_for_purchase, "
            + "prize.prize_id, prize.place, prize.prize_amount, prize.prize_type_id, prize.number_of_submissions, "
            + "prize.create_user, prize.create_date, prize.modify_user, prize.modify_date, "
            + "prize_type_lu.prize_type_desc, "
            + "upload.upload_id, upload.create_user, upload.create_date, upload.modify_user, upload.modify_date, "
            + "upload.project_id, upload.project_phase_id, upload.resource_id, upload.parameter, upload.upload_desc, "
            + "upload_type_lu.upload_type_id, upload_type_lu.create_user, upload_type_lu.create_date, "
            + "upload_type_lu.modify_user, upload_type_lu.modify_date, "
            + "upload_type_lu.name, upload_type_lu.description, "
            + "upload_status_lu.upload_status_id, upload_status_lu.create_user, upload_status_lu.create_date, "
            + "upload_status_lu.modify_user, upload_status_lu.modify_date, "
            + "upload_status_lu.name, upload_status_lu.description " + "FROM submission "
            + "INNER JOIN submission_status_lu "
            + "ON submission.submission_status_id = submission_status_lu.submission_status_id "
            + "INNER JOIN submission_type_lu ON submission.submission_type_id = submission_type_lu.submission_type_id "
            + "LEFT JOIN prize ON submission.prize_id = prize.prize_id "
            + "LEFT JOIN prize_type_lu ON prize.prize_type_id = prize_type_lu.prize_type_id "
            + "INNER JOIN upload ON submission.upload_id = upload.upload_id AND upload.project_id = ? AND upload.resource_id = ? "
            + "INNER JOIN upload_type_lu ON upload.upload_type_id=upload_type_lu.upload_type_id "
            + "INNER JOIN upload_status_lu ON upload.upload_status_id=upload_status_lu.upload_status_id";

    /**
     * Represents the sql statement for retrieving project submissions.
     *
     * @since 1.2
     */
    private static final String GET_PROJECT_SUBMISSIONS_SQL = "SELECT "
            + "submission.submission_id, submission.create_user, submission.create_date, submission.modify_user, "
            + "submission.modify_date, submission_status_lu.submission_status_id, submission_status_lu.create_user, "
            + "submission_status_lu.create_date, submission_status_lu.modify_user, submission_status_lu.modify_date, "
            + "submission_status_lu.name, submission_status_lu.description, submission_type_lu.submission_type_id, "
            + "submission_type_lu.create_user, submission_type_lu.create_date, submission_type_lu.modify_user, "
            + "submission_type_lu.modify_date, submission_type_lu.name, submission_type_lu.description, "
            + "submission.screening_score, submission.initial_score, submission.final_score, submission.placement, "
            + "submission.user_rank, submission.mark_for_purchase, "
            + "prize.prize_id, prize.place, prize.prize_amount, prize.prize_type_id, prize.number_of_submissions, "
            + "prize.create_user, prize.create_date, prize.modify_user, prize.modify_date, "
            + "prize_type_lu.prize_type_desc, "
            + "upload.upload_id, upload.create_user, upload.create_date, upload.modify_user, upload.modify_date, "
            + "upload.project_id, upload.project_phase_id, upload.resource_id, upload.parameter, upload.upload_desc, "
            + "upload_type_lu.upload_type_id, upload_type_lu.create_user, upload_type_lu.create_date, "
            + "upload_type_lu.modify_user, upload_type_lu.modify_date, "
            + "upload_type_lu.name, upload_type_lu.description, "
            + "upload_status_lu.upload_status_id, upload_status_lu.create_user, upload_status_lu.create_date, "
            + "upload_status_lu.modify_user, upload_status_lu.modify_date, "
            + "upload_status_lu.name, upload_status_lu.description " + "FROM submission "
            + "INNER JOIN submission_status_lu ON submission.submission_status_id "
            + "= submission_status_lu.submission_status_id "
            + "INNER JOIN submission_type_lu ON submission.submission_type_id = submission_type_lu.submission_type_id "
            + "LEFT JOIN prize ON submission.prize_id = prize.prize_id "
            + "LEFT JOIN prize_type_lu ON prize.prize_type_id = prize_type_lu.prize_type_id "
            + "INNER JOIN upload ON submission.upload_id = upload.upload_id AND upload.project_id = ? "
            + "INNER JOIN upload_type_lu ON upload.upload_type_id=upload_type_lu.upload_type_id "
            + "INNER JOIN upload_status_lu ON upload.upload_status_id=upload_status_lu.upload_status_id";

    /**
     * Represents the sql statement for retrieving the submission images for the given submission.
     *
     * @since 1.2
     */
    private static final String GET_SUBMISSION_IMAGES_FOR_SUBMISSION_SQL = "SELECT"
            + " image_id, sort_order, modify_date, create_date FROM submission_image WHERE submission_id = ?";

    /**
     * Represents the column types for submission images.
     *
     * @since 1.2
     */
    private static final DataType[] GET_SUBMISSION_IMAGES_FOR_SUBMISSION_COLUMN_TYPES = new DataType[] {
        Helper.INTEGER_TYPE, Helper.INTEGER_TYPE, Helper.DATE_TYPE, Helper.DATE_TYPE};

    /**
     * Represents the sql statement for retrieving the submission declaration data for the given submission.
     *
     * @since 1.4
     */
    private static final String GET_SUBMISSION_DECLARATION_SQL = "SELECT "
            + "sd.submission_declaration_id, sd.comment, sd.has_external_content, "
            + "sec.external_content_id, sec.display_position, "
            + "ect.external_content_type_id, ect.name, "
            + "ecp.external_content_property_id, ecp.name, ecp.value "
            + "FROM submission_declaration sd, "
            + " submission_external_content sec, "
            + " external_content_property ecp, "
            + " external_content_type ect "
            + "WHERE sd.submission_id = ? "
            + " AND sd.submission_declaration_id = sec.submission_declaration_id "
            + " AND ecp.external_content_id = sec.external_content_id "
            + " AND ect.external_content_type_id = sec.external_content_type_id "
            + "ORDER BY sd.submission_declaration_id, sec.display_position, sec.external_content_id";
    
    /**
     * Represents the column types for submission declaration data.
     *
     * @since 1.4
     */
    private static final DataType[] GET_SUBMISSION_DECLARATION_COLUMN_TYPES = new DataType[] {
        Helper.LONG_TYPE, Helper.STRING_TYPE, Helper.BOOLEAN_TYPE,
        Helper.LONG_TYPE, Helper.INTEGER_TYPE,
        Helper.LONG_TYPE, Helper.STRING_TYPE,
        Helper.LONG_TYPE, Helper.STRING_TYPE, Helper.STRING_TYPE
    };
    
    /**
     * <p>
     * The name of the connection producer to use when a connection to the database is retrieved from the
     * DBConnectionFactory. This field is immutable and can be null or non-null. When non-null, no restrictions are
     * applied to the field. When this field is null, the createConnection() method is used to get a connection. When it
     * is non-null, the createConnection(String) method is used to get a connection. This field is not exposed by this
     * class, and is used whenever a connection to the database is needed (i.e. in every method).
     * </p>
     */
    private final String connectionName;

    /**
     * <p>
     * The connection factory to use when a connection to the database is needed. This field is immutable and must be
     * non-null. This field is not exposed by this class and is used whenever a connection to the database is needed
     * (i.e. in every method).
     * </p>
     */
    private final DBConnectionFactory connectionFactory;

    /**
     * <p>
     * Creates a new instance of the SqlUploadPersistence class. Default connection name of the connectionFactory will
     * be used.
     * </p>
     *
     * @param connectionFactory
     *            The connection factory to use for getting connections to the database.
     * @throws IllegalArgumentException
     *             If connectionFactory is null.
     */
    public SqlUploadPersistence(DBConnectionFactory connectionFactory) {
        this(connectionFactory, null);
    }

    /**
     * <p>
     * Creates a new instance of the SqlUploadPersistence class, with the given connectionFactory and connectionName.
     * </p>
     *
     * @param connectionFactory
     *            The connection factory to use for getting connections to the database.
     * @param connectionName
     *            The name of the connection to use. Can be null.
     * @throws IllegalArgumentException
     *             If connectionFactory is null, or connectionName is empty (trimmed).
     */
    public SqlUploadPersistence(DBConnectionFactory connectionFactory, String connectionName) {
        LOGGER.log(Level.INFO, "Instantiate SqlUploadPersistence with connectionFactory and connectionName:"
                + connectionName);

        Helper.assertObjectNotNull(connectionFactory, "connectionFactory", LOGGER);
        Helper.assertStringNotEmpty(connectionName, "connectionName", LOGGER);

        this.connectionFactory = connectionFactory;
        this.connectionName = connectionName;
    }

    /**
     * <p>
     * Adds the given uploadType to the persistence. The id of the upload type must already be assigned, as must all the
     * other fields needed for persistence.
     * </p>
     *
     * @param uploadType
     *            The upload type to add
     * @throws IllegalArgumentException
     *             If uploadType is null
     * @throws IllegalArgumentException
     *             If isValidToPersist returns false
     * @throws UploadPersistenceException
     *             If there is an error when making the change in the persistence
     */
    public void addUploadType(UploadType uploadType) throws UploadPersistenceException {
        LOGGER.log(Level.INFO, new LogMessage("UploadType", getIdFromEntity(uploadType), null, "add new UploadType"));

        assertEntityNotNullAndValidToPersist(uploadType, "uploadType", LOGGER);

        addNameEntity(uploadType, "upload_type_lu", "upload_type_id");
    }

    /**
     * <p>
     * Adds the given uploadStatus to the persistence. The id of the upload status must already be assigned, as must all
     * the other fields needed for persistence.
     * </p>
     *
     * @param uploadStatus
     *            The upload status to add
     * @throws IllegalArgumentException
     *             If uploadStatus is null
     * @throws IllegalArgumentException
     *             If isValidToPersist returns false
     * @throws UploadPersistenceException
     *             If there is an error when making the change in the persistence
     */
    public void addUploadStatus(UploadStatus uploadStatus) throws UploadPersistenceException {
        LOGGER.log(Level.INFO, new LogMessage("UploadStatus", getIdFromEntity(uploadStatus), null,
                "add new uploadStatus"));

        assertEntityNotNullAndValidToPersist(uploadStatus, "uploadStatus", LOGGER);

        addNameEntity(uploadStatus, "upload_status_lu", "upload_status_id");
    }

    /**
     * <p>
     * Adds the given submission status to the persistence. The id of the submission status must already be assigned, as
     * must all the other fields needed for persistence.
     * </p>
     *
     * @param submissionStatus
     *            The submission status to add
     * @throws IllegalArgumentException
     *             If submissionStatus is null
     * @throws IllegalArgumentException
     *             If isValidToPersist returns false
     * @throws UploadPersistenceException
     *             If there is an error when making the change in the persistence
     */
    public void addSubmissionStatus(SubmissionStatus submissionStatus) throws UploadPersistenceException {
        LOGGER.log(Level.INFO, new LogMessage("SubmissionStatus", getIdFromEntity(submissionStatus), null,
                "add new SubmissionStatus."));

        assertEntityNotNullAndValidToPersist(submissionStatus, "submissionStatus", LOGGER);

        addNameEntity(submissionStatus, "submission_status_lu", "submission_status_id");
    }

    /**
     * <p>
     * Removes the given upload type (by id) from the persistence. The id of the upload type can not be UNSET_ID, but
     * the other fields do not matter.
     * </p>
     *
     * @param uploadType
     *            The upload type to remove
     * @throws IllegalArgumentException
     *             If uploadType is null
     * @throws IllegalArgumentException
     *             If the id is UNSET_ID
     * @throws UploadPersistenceException
     *             If there is an error when making the change in the persistence
     */
    public void removeUploadType(UploadType uploadType) throws UploadPersistenceException {
        LOGGER.log(Level.INFO, new LogMessage("UploadType", getIdFromEntity(uploadType), null, "Remove UploadType."));

        Helper.assertObjectNotNull(uploadType, "uploadType", LOGGER);
        Helper.assertIdNotUnset(uploadType.getId(), "uploadType id", LOGGER);

        removeEntity(uploadType, "upload_type_lu", "upload_type_id");
    }

    /**
     * <p>
     * Removes the given upload status (by id) from the persistence. The id of the upload status can not be UNSET_ID,
     * but the other fields do not matter.
     * </p>
     *
     * @param uploadStatus
     *            The upload status to remove
     * @throws IllegalArgumentException
     *             If uploadStatus is null
     * @throws IllegalArgumentException
     *             If the id is UNSET_ID
     * @throws UploadPersistenceException
     *             If there is an error when making the change in the persistence
     */
    public void removeUploadStatus(UploadStatus uploadStatus) throws UploadPersistenceException {
        LOGGER.log(Level.INFO, new LogMessage("UploadStatus", getIdFromEntity(uploadStatus), null,
                "Remove UploadStatus."));

        Helper.assertObjectNotNull(uploadStatus, "uploadStatus", LOGGER);
        Helper.assertIdNotUnset(uploadStatus.getId(), "uploadStatus id", LOGGER);

        removeEntity(uploadStatus, "upload_status_lu", "upload_status_id");
    }

    /**
     * <p>
     * Removes the given submission status (by id) from the persistence. The id of the submission status can not be
     * UNSET_ID, but the other fields do not matter.
     * </p>
     *
     * @param submissionStatus
     *            The submission status to remove
     * @throws IllegalArgumentException
     *             If submissionStatus is null
     * @throws IllegalArgumentException
     *             If the id is UNSET_ID
     * @throws UploadPersistenceException
     *             If there is an error when making the change in the persistence
     */
    public void removeSubmissionStatus(SubmissionStatus submissionStatus) throws UploadPersistenceException {
        LOGGER.log(Level.INFO, new LogMessage("SubmissionStatus", getIdFromEntity(submissionStatus), null,
                "Remove SubmissionStatus."));

        Helper.assertObjectNotNull(submissionStatus, "submissionStatus", LOGGER);
        Helper.assertIdNotUnset(submissionStatus.getId(), "submissionStatus id", LOGGER);

        removeEntity(submissionStatus, "submission_status_lu", "submission_status_id");
    }

    /**
     * <p>
     * Removes the given upload (by id) from the persistence. The id of the upload can not be UNSET_ID, but the other
     * fields do not matter.
     * </p>
     *
     * @param upload
     *            The upload to remove
     * @throws IllegalArgumentException
     *             If upload is null
     * @throws IllegalArgumentException
     *             If the id is UNSET_ID
     * @throws UploadPersistenceException
     *             If there is an error when making the change in the persistence
     */
    public void removeUpload(Upload upload) throws UploadPersistenceException {
        LOGGER.log(Level.INFO, new LogMessage("Upload", getIdFromEntity(upload), null, "Remove Upload."));

        Helper.assertObjectNotNull(upload, "upload", LOGGER);
        Helper.assertIdNotUnset(upload.getId(), "upload id", LOGGER);

        removeEntity(upload, "upload", "upload_id");
    }

    /**
     * <p>
     * Removes the given submission (by id) from the persistence. The id of the submission can not be UNSET_ID, but the
     * other fields do not matter.
     * </p>
     *
     * @param submission
     *            The submission to remove
     * @throws IllegalArgumentException
     *             If submission is null
     * @throws IllegalArgumentException
     *             If the id is UNSET_ID
     * @throws UploadPersistenceException
     *             If there is an error when making the change in the persistence
     */
    public void removeSubmission(Submission submission) throws UploadPersistenceException {
        LOGGER.log(Level.INFO, new LogMessage("Submission", getIdFromEntity(submission), null, "Remove Submission."));

        Helper.assertObjectNotNull(submission, "submission", LOGGER);
        Helper.assertIdNotUnset(submission.getId(), "submission id", LOGGER);

        removeEntity(submission, "submission", "submission_id");
    }

    /**
     * <p>
     * Updates the given upload type in the persistence. The id of the uploadType can not be UNSET_ID, and all other
     * fields needed for persistence must also be assigned.
     * </p>
     *
     * @param uploadType
     *            The upload type to update
     * @throws IllegalArgumentException
     *             If uploadType is null
     * @throws IllegalArgumentException
     *             If isValidToPersist returns false
     * @throws UploadPersistenceException
     *             If there is an error when making the change in the persistence
     */
    public void updateUploadType(UploadType uploadType) throws UploadPersistenceException {
        LOGGER.log(Level.INFO, new LogMessage("UploadType", getIdFromEntity(uploadType), null, "Update UploadType."));

        assertEntityNotNullAndValidToPersist(uploadType, "uploadType", LOGGER);

        updateNamedEntity(uploadType, "upload_type_lu", "upload_type_id");
    }

    /**
     * <p>
     * Updates the given upload status in the persistence. The id of the uploadStatus can not be UNSET_ID, and all other
     * fields needed for persistence must also be assigned.
     * </p>
     *
     * @param uploadStatus
     *            The upload status to update
     * @throws IllegalArgumentException
     *             If uploadStatus is null
     * @throws IllegalArgumentException
     *             If isValidToPersist returns false
     * @throws UploadPersistenceException
     *             If there is an error when making the change in the persistence
     */
    public void updateUploadStatus(UploadStatus uploadStatus) throws UploadPersistenceException {
        LOGGER.log(Level.INFO, new LogMessage("UploadStatus", getIdFromEntity(uploadStatus), null,
                "Update UploadStatus."));

        assertEntityNotNullAndValidToPersist(uploadStatus, "uploadStatus", LOGGER);

        updateNamedEntity(uploadStatus, "upload_status_lu", "upload_status_id");
    }

    /**
     * <p>
     * Updates the given submission status in the persistence. The id of the submissionStats can not be UNSET_ID, and
     * all other fields needed for persistence must also be assigned.
     * </p>
     *
     * @param submissionStatus
     *            The submissionStatus to update
     * @throws IllegalArgumentException
     *             If submissionStatus is null
     * @throws IllegalArgumentException
     *             If isValidToPersist returns false
     * @throws UploadPersistenceException
     *             If there is an error when making the change in the persistence
     */
    public void updateSubmissionStatus(SubmissionStatus submissionStatus) throws UploadPersistenceException {
        LOGGER.log(Level.INFO, new LogMessage("SubmissionStatus", getIdFromEntity(submissionStatus), null,
                "Update SubmissionStatus."));

        assertEntityNotNullAndValidToPersist(submissionStatus, "submissionStatus", LOGGER);

        updateNamedEntity(submissionStatus, "submission_status_lu", "submission_status_id");
    }

    /**
     * <p>
     * Gets the ids of all upload types in the persistence. The individual upload types can then be loaded with the
     * loadUploadType method.
     * </p>
     *
     * @return The ids of all upload types
     * @throws UploadPersistenceException
     *             If there is an error when reading the persistence store
     */
    public long[] getAllUploadTypeIds() throws UploadPersistenceException {
        return getAllEntityIds("upload_type_lu", "upload_type_id");
    }

    /**
     * <p>
     * Gets the ids of all upload statuses in the persistence. The individual upload statuses can then be loaded with
     * the loadUploadStatus method.
     * </p>
     *
     * @return The ids of all upload statuses
     * @throws UploadPersistenceException
     *             If there is an error when reading the persistence store
     */
    public long[] getAllUploadStatusIds() throws UploadPersistenceException {
        return getAllEntityIds("upload_status_lu", "upload_status_id");
    }

    /**
     * <p>
     * Gets the ids of all submission statuses in the persistence. The individual submission statuses can then be loaded
     * with the loadSubmissionStatus method.
     * </p>
     *
     * @return The ids of all submission statuses
     * @throws UploadPersistenceException
     *             If there is an error when reading the persistence store
     */
    public long[] getAllSubmissionStatusIds() throws UploadPersistenceException {
        return getAllEntityIds("submission_status_lu", "submission_status_id");
    }

    /**
     * <p>
     * Adds the given submission type to the persistence. The id of the submission type must be already assigned, as
     * must all the other fields needed for persistence.
     * </p>
     *
     * @param submissionType
     *            The submission type to add
     * @throws IllegalArgumentException
     *             If submissionType is null or isValidToPersist returns false
     * @throws UploadPersistenceException
     *             If there is an error making the change in the persistence
     * @since 1.1
     */
    public void addSubmissionType(SubmissionType submissionType) throws UploadPersistenceException {
        LOGGER.log(Level.INFO, new LogMessage("SubmissionType", getIdFromEntity(submissionType), null,
                "add new SubmissionType."));

        assertEntityNotNullAndValidToPersist(submissionType, "submissionType", LOGGER);

        addNameEntity(submissionType, "submission_type_lu", "submission_type_id");
    }

    /**
     * <p>
     * Removes the given submission type (by id) from the persistence. The id of the submission type can not be
     * UNSET_ID, but the other fields do not matter.
     * </p>
     *
     * @param submissionType
     *            The submission type to remove
     * @throws IllegalArgumentException
     *             If submissionType is null or the id is equal to UNSET_ID
     * @throws UploadPersistenceException
     *             If there is an error making the change in the persistence
     * @since 1.1
     */
    public void removeSubmissionType(SubmissionType submissionType) throws UploadPersistenceException {
        LOGGER.log(Level.INFO, new LogMessage("SubmissionType", getIdFromEntity(submissionType), null,
                "Remove SubmissionType."));

        Helper.assertObjectNotNull(submissionType, "submissionType", LOGGER);
        Helper.assertIdNotUnset(submissionType.getId(), "submissionType id", LOGGER);

        removeEntity(submissionType, "submission_type_lu", "submission_type_id");
    }

    /**
     * <p>
     * Updates the given submission type in the persistence. The id of the submission type can not be UNSET_ID, and all
     * other fields needed for persistence must also be assigned.
     * </p>
     *
     * @param submissionType
     *            The submissionType to update
     * @throws IllegalArgumentException
     *             If submissionType is null or isValidToPersist returns false
     * @throws UploadPersistenceException
     *             If there is an error making the change in the persistence
     * @since 1.1
     */
    public void updateSubmissionType(SubmissionType submissionType) throws UploadPersistenceException {
        LOGGER.log(Level.INFO, new LogMessage("SubmissionType", getIdFromEntity(submissionType), null,
                "Update submissionType."));

        assertEntityNotNullAndValidToPersist(submissionType, "submissionType", LOGGER);

        updateNamedEntity(submissionType, "submission_type_lu", "submission_type_id");
    }

    /**
     * <p>
     * Loads the SubmissionType with the given id from persistence. Returns null if there is no SubmissionType with the
     * given id.
     * </p>
     *
     * @param submissionTypeId
     *            The id of the item to retrieve
     * @return The loaded SubmissionType or null
     * @throws IllegalArgumentException
     *             if submissionTypeId is <= 0
     * @throws UploadPersistenceException
     *             if there is an error reading the persistence data
     * @since 1.1
     */
    public SubmissionType loadSubmissionType(long submissionTypeId) throws UploadPersistenceException {
        LOGGER.log(Level.INFO, new LogMessage("SubmissionType", submissionTypeId, null,
                "Load SubmissionType. Delegate to loadSubmissionTypes(long[])."));

        Helper.assertIdNotUnset(submissionTypeId, "submissionTypeId", LOGGER);

        SubmissionType[] submissionTypes = loadSubmissionTypes(new long[] {submissionTypeId});
        return submissionTypes.length == 0 ? null : submissionTypes[0];
    }

    /**
     * <p>
     * Loads all SubmissionTypes with the given ids from persistence. May return a 0-length array.
     * </p>
     *
     * @param submissionTypeIds
     *            The ids of the objects to load
     * @return The loaded SubmissionTypes
     * @throws IllegalArgumentException
     *             if any id is <= 0
     * @throws UploadPersistenceException
     *             if there is an error reading the persistence data
     * @since 1.1
     */
    public SubmissionType[] loadSubmissionTypes(long[] submissionTypeIds) throws UploadPersistenceException {
        LOGGER.log(Level.INFO, new LogMessage("SubmissionType", null, null, "Load SubmissionTypes with ids:"
                + Helper.getIdString(submissionTypeIds)));

        Helper.assertLongArrayNotNullAndOnlyHasPositive(submissionTypeIds, "submissionTypeIds", LOGGER);

        return (SubmissionType[]) loadNamedEntities(submissionTypeIds, SubmissionType.class, "submission_type_lu",
                "submission_type_id");
    }

    /**
     * <p>
     * Gets the ids of all submission types in the persistence. The individual submission types can then be loaded with
     * the loadSubmissionType method.
     * </p>
     *
     * @return The ids of all submission types
     * @throws UploadPersistenceException
     *             If there is an error reading the persistence store
     * @since 1.1
     */
    public long[] getAllSubmissionTypeIds() throws UploadPersistenceException {
        return getAllEntityIds("submission_type_lu", "submission_type_id");
    }

    /**
     * <p>
     * Loads the UploadType with the given id from persistence. Returns null if there is no UploadType with the given
     * id.
     * </p>
     *
     * @param uploadTypeId
     *            The id of the item to retrieve
     * @return The loaded UploadType or null
     * @throws IllegalArgumentException
     *             if uploadTypeId is <= 0
     * @throws UploadPersistenceException
     *             if there is an error when reading the persistence data
     */
    public UploadType loadUploadType(long uploadTypeId) throws UploadPersistenceException {
        LOGGER.log(Level.INFO, new LogMessage("UploadType", uploadTypeId, null,
                "Load UploadType. Delegate to loadUploadTypes(long[])."));

        Helper.assertIdNotUnset(uploadTypeId, "uploadTypeId", LOGGER);

        UploadType[] uploadTypes = loadUploadTypes(new long[] {uploadTypeId});
        return uploadTypes.length == 0 ? null : uploadTypes[0];
    }

    /**
     * <p>
     * Loads the UploadStatus with the given id from persistence. Returns null if there is no UploadStatus with the
     * given id.
     * </p>
     *
     * @param uploadStatusId
     *            The id of the item to retrieve
     * @return The loaded UploadStatus or null
     * @throws IllegalArgumentException
     *             if uploadStatusId is <= 0
     * @throws UploadPersistenceException
     *             if there is an error when reading the persistence data
     */
    public UploadStatus loadUploadStatus(long uploadStatusId) throws UploadPersistenceException {
        LOGGER.log(Level.INFO, new LogMessage("UploadStatus", uploadStatusId, null,
                "Load UploadStatus. Delegate to loadUploadStatuses(long[])."));

        Helper.assertIdNotUnset(uploadStatusId, "uploadStatusId", LOGGER);

        UploadStatus[] uploadStatuses = loadUploadStatuses(new long[] {uploadStatusId});
        return uploadStatuses.length == 0 ? null : uploadStatuses[0];
    }

    /**
     * <p>
     * Loads the SubmissionStatus with the given id from persistence. Returns null if there is no SubmissionStatus with
     * the given id.
     * </p>
     *
     * @param submissionStatusId
     *            The id of the item to retrieve
     * @return The loaded SubmissionStatus or null
     * @throws IllegalArgumentException
     *             if submissionStatusId is <= 0
     * @throws UploadPersistenceException
     *             if there is an error when reading the persistence data
     */
    public SubmissionStatus loadSubmissionStatus(long submissionStatusId) throws UploadPersistenceException {
        LOGGER.log(Level.INFO, new LogMessage("SubmissionStatus", submissionStatusId, null,
                "Load SubmissionStatus. Delegate to loadSubmissionStatuses(long[])."));
        Helper.assertIdNotUnset(submissionStatusId, "submissionStatusId", LOGGER);

        SubmissionStatus[] submissionStatuses = loadSubmissionStatuses(new long[] {submissionStatusId});
        return submissionStatuses.length == 0 ? null : submissionStatuses[0];
    }

    /**
     * <p>
     * Loads all UploadTypes with the given ids from persistence. May return a 0-length array.
     * </p>
     *
     * @param uploadTypeIds
     *            The ids of the objects to load
     * @return the loaded UploadTypes
     * @throws IllegalArgumentException
     *             if uploadTypeIds is null, or any id is <= 0
     * @throws UploadPersistenceException
     *             if there is an error when reading the persistence data
     */
    public UploadType[] loadUploadTypes(long[] uploadTypeIds) throws UploadPersistenceException {
        LOGGER.log(Level.INFO, new LogMessage("UploadType", null, null, "Load UploadTypes with ids:"
                + Helper.getIdString(uploadTypeIds)));

        Helper.assertLongArrayNotNullAndOnlyHasPositive(uploadTypeIds, "uploadTypeIds", LOGGER);

        return (UploadType[]) loadNamedEntities(uploadTypeIds, UploadType.class, "upload_type_lu", "upload_type_id");
    }

    /**
     * <p>
     * Loads all UploadStatuses with the given ids from persistence. May return a 0-length array.
     * </p>
     *
     * @param uploadStatusIds
     *            The ids of the objects to load
     * @return the loaded UploadStatuses
     * @throws IllegalArgumentException
     *             if uploadStatusIds is null, or any id is <= 0
     * @throws UploadPersistenceException
     *             if there is an error when reading the persistence data
     */
    public UploadStatus[] loadUploadStatuses(long[] uploadStatusIds) throws UploadPersistenceException {
        LOGGER.log(Level.INFO, new LogMessage("UploadStatus", null, null, "Load UploadStatuses with ids:"
                + Helper.getIdString(uploadStatusIds)));

        Helper.assertLongArrayNotNullAndOnlyHasPositive(uploadStatusIds, "uploadStatusIds", LOGGER);

        return (UploadStatus[]) loadNamedEntities(uploadStatusIds, UploadStatus.class, "upload_status_lu",
                "upload_status_id");
    }

    /**
     * <p>
     * Loads all SubmissionStatuses with the given ids from persistence. May return a 0-length array.
     * </p>
     *
     * @param submissionStatusIds
     *            The ids of the objects to load
     * @return the loaded SubmissionStatuses
     * @throws IllegalArgumentException
     *             if submissionStatusIds is null, or any id is <= 0
     * @throws UploadPersistenceException
     *             if there is an error when reading the persistence data
     */
    public SubmissionStatus[] loadSubmissionStatuses(long[] submissionStatusIds) throws UploadPersistenceException {
        LOGGER.log(Level.INFO, new LogMessage("SubmissionStatus", null, null, "Load SubmissionStatuses with ids:"
                + Helper.getIdString(submissionStatusIds)));

        Helper.assertLongArrayNotNullAndOnlyHasPositive(submissionStatusIds, "submissionStatusIds", LOGGER);

        return (SubmissionStatus[]) loadNamedEntities(submissionStatusIds, SubmissionStatus.class,
                "submission_status_lu", "submission_status_id");

    }

    /**
     * <p>
     * Adds the given upload to the persistence. The id of the upload must already be assigned, as must all the other
     * fields needed for persistence.
     * </p>
     * <p>
     * Change in 1.2:
     * <ul>
     * <li>The method is updated to support description property of Upload.</li>
     * </ul>
     * </p>
     *
     * @param upload
     *            The upload to add
     * @throws IllegalArgumentException
     *             If upload is null
     * @throws IllegalArgumentException
     *             If isValidToPersist returns false
     * @throws UploadPersistenceException
     *             If there is an error when making the change in the persistence
     */
    public void addUpload(Upload upload) throws UploadPersistenceException {
        LOGGER.log(Level.INFO, new LogMessage("Upload", getIdFromEntity(upload), null,
                "Add new Upload, it will insert record into upload table."));

        assertEntityNotNullAndValidToPersist(upload, "upload", LOGGER);

        // build arguments
        Object[] queryArgs = new Object[] {upload.getId(), upload.getCreationUser(), upload.getCreationTimestamp(),
            upload.getModificationUser(), upload.getModificationTimestamp(), upload.getProject(), upload.getProjectPhase(), upload.getOwner(),
            upload.getUploadType().getId(), upload.getUploadStatus().getId(), upload.getParameter(),
            upload.getDescription()};

        // add upload to database
        doDMLQuery(connectionFactory, connectionName, ADD_UPLOAD_SQL, ADD_UPLOAD_ARGUMENT_TYPES, queryArgs,
                new LogMessage("Upload", upload.getId(), null, "Failed to add new Upload"));
    }

    /**
     * <p>
     * Updates the given upload in the persistence. The id of the upload can not be UNSET_ID, and all other fields
     * needed for persistence must also be assigned.
     * </p>
     * <p>
     * Change in 1.2: The method is updated to support description property of Upload.
     * </p>
     *
     * @param upload
     *            The upload to update
     * @throws IllegalArgumentException
     *             If upload is null
     * @throws IllegalArgumentException
     *             If isValidToPersist returns false
     * @throws UploadPersistenceException
     *             If there is an error when making the change in the persistence
     */
    public void updateUpload(Upload upload) throws UploadPersistenceException {
        LOGGER.log(Level.INFO, new LogMessage("Upload", getIdFromEntity(upload), null,
                "Update Upload, it will update record in upload table."));

        assertEntityNotNullAndValidToPersist(upload, "upload", LOGGER);

        // build arguments
        Object[] queryArgs = new Object[] {upload.getModificationUser(), upload.getModificationTimestamp(),
            upload.getProject(), upload.getProjectPhase(), upload.getOwner(), upload.getUploadType().getId(), upload.getUploadStatus().getId(),
            upload.getParameter(), upload.getDescription(), upload.getId()};

        // update upload to database
        doDMLQuery(connectionFactory, connectionName, UPDATE_UPLOAD_SQL, UPDATE_UPLOAD_ARGUMENT_TYPES, queryArgs,
                new LogMessage("Upload", upload.getId(), null, "Failed to update Upload"));
    }

    /**
     * <p>
     * Adds the given submission to the persistence. The id of the submission must already be assigned, as must all the
     * other fields needed for persistence.
     * </p>
     * <p>
     * Change in 1.2: The method is updated to support userRank and extra properties of Submission.
     * Additionally removal of upload_id field is handled. Association between submission and the list of uploads
     * is created properly. The foreign key relation to prize table is added if the prize property set.
     * </p>
     *
     * @param submission
     *            The submission to add
     * @throws IllegalArgumentException
     *             If submission is null
     * @throws IllegalArgumentException
     *             If isValidToPersist returns false
     * @throws UploadPersistenceException
     *             If there is an error when making the change in the persistence
     */
    public void addSubmission(Submission submission) throws UploadPersistenceException {
        LOGGER.log(Level.INFO, new LogMessage("Submission", getIdFromEntity(submission), null,
                "Add new Submission, it will insert record into submission table."));

        assertEntityNotNullAndValidToPersist(submission, "submission", LOGGER);

        // build arguments
        Object[] queryArgs = new Object[] {submission.getId(), submission.getCreationUser(),
            submission.getCreationTimestamp(), submission.getModificationUser(), submission.getModificationTimestamp(),
            submission.getSubmissionStatus().getId(), submission.getSubmissionType().getId(),
            submission.getScreeningScore(), submission.getInitialScore(), submission.getFinalScore(),
            submission.getPlacement(), submission.getUserRank(), submission.isExtra(),
            submission.getPrize() != null ? submission.getPrize().getId() : null,
            submission.getUpload() != null ? submission.getUpload().getId() : null};

        // add submission to database
        doDMLQuery(connectionFactory, connectionName, ADD_SUBMISSION_SQL, ADD_SUBMISSION_ARGUMENT_TYPES, queryArgs,
                new LogMessage("Submission", submission.getId(), null, "Failed to add new submission"));
    }

    /**
     * <p>
     * Updates the given submission in the persistence. The id of the submission can not be UNSET_ID, and all other
     * fields needed for persistence must also be assigned.
     * </p>
     * <p>
     * Change in 1.2: The method is updated to support userRank and extra properties of Submission.
     * Additionally removal of upload_id field is handled. Association between submission and the list of uploads
     * is updated properly. The foreign key relation to prize table is updated if the prize property set.
     * </p>
     *
     * @param submission
     *            The submission to add
     * @throws IllegalArgumentException
     *             If submission is null
     * @throws IllegalArgumentException
     *             If isValidToPersist returns false
     * @throws UploadPersistenceException
     *             If there is an error when making the change in the persistence
     */
    public void updateSubmission(Submission submission) throws UploadPersistenceException {
        LOGGER.log(Level.INFO, new LogMessage("Submission", getIdFromEntity(submission), null,
                "Update Submission, it will update record in submission table."));

        assertEntityNotNullAndValidToPersist(submission, "submission", LOGGER);

        // build arguments
        Object[] queryArgs = new Object[] {submission.getModificationUser(), submission.getModificationTimestamp(),
            submission.getSubmissionStatus().getId(), submission.getSubmissionType().getId(),
            submission.getScreeningScore(), submission.getInitialScore(), submission.getFinalScore(),
            submission.getPlacement(), submission.getUserRank(), submission.isExtra(),
            submission.getPrize() != null ? submission.getPrize().getId() : null,
            submission.getUpload() != null ? submission.getUpload().getId() : null, submission.getId()};

        // update submission to database
        doDMLQuery(connectionFactory, connectionName, UPDATE_SUBMISSION_SQL, UPDATE_SUBMISSION_WITH_ARGUMENT_TYPES,
                queryArgs, new LogMessage("Submission", submission.getId(), null, "Failed to update submission"));
    }

    /**
     * <p>
     * Loads the Upload with the given id from persistence. Returns null if there is no Upload with the given id.
     * </p>
     *
     * @param uploadId
     *            The id of the item to retrieve
     * @return The loaded Upload or null
     * @throws IllegalArgumentException
     *             if uploadId is <= 0
     * @throws UploadPersistenceException
     *             if there is an error when reading the persistence data
     */
    public Upload loadUpload(long uploadId) throws UploadPersistenceException {
        LOGGER.log(Level.INFO,
                new LogMessage("Upload", uploadId, null, "Load Upload. Delegate to loadUploads(long[])."));

        Helper.assertIdNotUnset(uploadId, "uploadId", LOGGER);

        Upload[] uploads = loadUploads(new long[] {uploadId});
        return uploads.length == 0 ? null : uploads[0];
    }

    /**
     * <p>
     * Loads all Uploads with the given ids from persistence. May return a 0-length array.
     * </p>
     * <p>
     * Change in 1.2: The method is updated to support description property of Upload.
     * </p>
     *
     * @param uploadIds
     *            The ids of uploads to load
     * @return The loaded uploads
     * @throws IllegalArgumentException
     *             if uploadIds is null or any id is <= 0
     * @throws UploadPersistenceException
     *             if there is an error when reading the persistence data
     */
    public Upload[] loadUploads(long[] uploadIds) throws UploadPersistenceException {
        LOGGER.log(Level.INFO, new LogMessage("Upload", null, null, "Load Uploads with ids: "
                + Helper.getIdString(uploadIds)));

        Helper.assertLongArrayNotNullAndOnlyHasPositive(uploadIds, "uploadIds", LOGGER);

        // simply return an empty Upload array if uploadIds is empty
        if (uploadIds.length == 0) {
            return new Upload[0];
        }

        Object[][] rows;
        try {
            // load upload
            rows = Helper.doQuery(connectionFactory, connectionName, LOAD_UPLOADS_SQL + makeIdListString(uploadIds),
                    new DataType[0], new Object[0], LOAD_UPLOADS_COLUMN_TYPES, LOGGER);
        } catch (PersistenceException e) {
            String errorMessage = "Unable to load uploads to the database.";
            LOGGER.log(Level.ERROR, errorMessage + ". \n" + LogMessage.getExceptionStackTrace(e));
            throw new UploadPersistenceException(errorMessage, e);
        }

        // create a new Upload array
        Upload[] uploads = new Upload[rows.length];

        // enumerate each data row
        for (int i = 0; i < rows.length; ++i) {
            // reference the current data row
            Object[] row = rows[i];

            // create a new Upload object
            Upload upload = new Upload();
            loadUploadFieldsSequentially(upload, row, 0);

            // assign it to the array
            uploads[i] = upload;
        }
        return uploads;
    }

    /**
     * <p>
     * Loads uploads from the result of the SELECT operation.
     * </p>
     * <p>
     * Change in 1.2: New description property of Upload is additionally loaded.
     * </p>
     *
     * @param resultSet
     *            The result of the SELECT operation.
     * @return an array of loaded uploads.
     * @throws UploadPersistenceException
     *             if any error occurs while loading uploads.
     */
    public Upload[] loadUploads(CustomResultSet resultSet) throws UploadPersistenceException {
        LOGGER.log(Level.INFO, new LogMessage("Upload", null, null, "Load Uploads from he custom result set."));

        Helper.assertObjectNotNull(resultSet, "resultSet", LOGGER);

        if (resultSet.getRecordCount() == 0) {
            return new Upload[0];
        }

            List uploads = new ArrayList();

            while (resultSet.next()) {
                uploads.add(loadUpload(resultSet));
            }

            return (Upload[]) uploads.toArray(new Upload[uploads.size()]);
    }

    /**
     * <p>
     * Loads the Submission with the given id from persistence. Returns null if there is no Submission with the given
     * id.
     * </p>
     *
     * @param submissionId
     *            The id of the item to retrieve
     * @return The loaded Submission or null
     * @throws IllegalArgumentException
     *             if submissionId is <= 0
     * @throws UploadPersistenceException
     *             if there is an error when reading the persistence data
     */
    public Submission loadSubmission(long submissionId) throws UploadPersistenceException {
        LOGGER.log(Level.INFO, new LogMessage("Submission", submissionId, null,
                "Load Submission. Delegate to loadSubmissions(long[])."));

        Helper.assertIdNotUnset(submissionId, "submissionId", LOGGER);

        Submission[] submissions = loadSubmissions(new long[] {submissionId});
        return submissions.length == 0 ? null : submissions[0];
    }

    /**
     * <p>
     * Loads all Submissions with the given ids from persistence. May return a 0-length array.
     * </p>
     * <p>
     * Changes in 1.2: The method is updated to support userRank, extra, and prize properties of Submission. A
     * list of images for each submission is loaded by using getImagesForSubmission() method. A list of uploads for each
     * submission is loaded by using getUploadsForSubmission() method.
     * </p>
     *
     * @param submissionIds
     *            The ids of submissions to load
     * @return The loaded submissions
     * @throws IllegalArgumentException
     *             if submissionIds is null, or any id is <= 0
     * @throws UploadPersistenceException
     *             if there is an error when reading the persistence data
     */
    public Submission[] loadSubmissions(long[] submissionIds) throws UploadPersistenceException {
        LOGGER.log(Level.INFO, new LogMessage("Submission", null, null, "Load Submissions with ids:"
                + Helper.getIdString(submissionIds)));

        Helper.assertLongArrayNotNullAndOnlyHasPositive(submissionIds, "submissionIds", LOGGER);

        // simply return an empty Submission array if submissionIds is empty
        if (submissionIds.length == 0) {
            return new Submission[0];
        }

        Object[][] rows;
        try {
            // load submission
            rows = Helper.doQuery(connectionFactory, connectionName, LOAD_SUBMISSIONS_SQL
                    + makeIdListString(submissionIds), new DataType[0], new Object[0], LOAD_SUBMISSIONS_COLUMN_TYPES,
                    LOGGER);
        } catch (PersistenceException e) {
            String errorMessage = "Unable to load submissions to the database.";
            LOGGER.log(Level.ERROR, errorMessage + "\n" + LogMessage.getExceptionStackTrace(e));
            throw new UploadPersistenceException(errorMessage, e);
        }

        // create a new Submission array
        Submission[] submissions = new Submission[rows.length];

        // enumerate each data row
        for (int i = 0; i < rows.length; ++i) {
            // reference the current data row
            Object[] row = rows[i];

            // create a new Submission object
            Submission submission = new Submission();

            loadSubmissionFieldsSequentially(submission, row, 0);

            // assign it to the array
            submissions[i] = submission;
        }
        return submissions;
    }

    /**
     * <p>
     * Loads submissions from the result of the SELECT operation.
     * </p>
     * <p>
     * Changes in 1.2: Step for loading of Upload from the result set is removed (refer to private
     * loadSubmission(CustomResultSet) method). Additionally userRank, extra and prize properties of Submission
     * are loaded. Notes, images and uploads properties are not loaded, please use the corresponding methods.
     * </p>
     *
     * @param resultSet
     *            The result of the SELECT operation.
     * @return an array of loaded submissions.
     * @throws UploadPersistenceException
     *             if any error occurs while loading submissions.
     */
    public Submission[] loadSubmissions(CustomResultSet resultSet) throws UploadPersistenceException {
        LOGGER
                .log(Level.INFO, new LogMessage("Submission", null, null,
                        "Load Submissions from the custom result set."));

        Helper.assertObjectNotNull(resultSet, "resultSet", LOGGER);

        if (resultSet.getRecordCount() == 0) {
            return new Submission[0];
        }

        List<Submission> submissions = new ArrayList<Submission>();

        while (resultSet.next()) {
            submissions.add(loadSubmission(resultSet));
        }

        return submissions.toArray(new Submission[submissions.size()]);
    }

    /**
     * Adds the given submission image to persistence.
     *
     * @param submissionImage
     *            the submission image to be created in persistence
     * @throws IllegalArgumentException
     *             If submissionImage is null, or submissionImage.isValidToPersist() returns false
     * @throws UploadPersistenceException
     *             If some error occurred when accessing the persistence
     * @since 1.2
     */
    public void addSubmissionImage(SubmissionImage submissionImage) throws UploadPersistenceException {
        LOGGER.log(Level.INFO, new LogMessage("SubmissionImage", null, null, "add new SubmissionImage"));

        Helper.assertObjectNotNull(submissionImage, "submissionImage", LOGGER);

        if (!submissionImage.isValidToPersist()) {
            throw Helper.logException(LOGGER, new IllegalArgumentException(
                    "The entity [SubmissionImage] is not valid to persist."));
        }

        LOGGER.log(Level.INFO, "add record into table[submission_image] with submission id: "
                + submissionImage.getSubmissionId() + ", and image id:" + submissionImage.getImageId());

        // build arguments
        Object[] queryArgs = new Object[] {submissionImage.getSubmissionId(), submissionImage.getImageId(),
            submissionImage.getSortOrder(), submissionImage.getModifyDate(), submissionImage.getCreateDate()};

        String errorMessage = "Failed to add record into table[submission_image] with submission id: "
                + submissionImage.getSubmissionId() + ", and image id:" + submissionImage.getImageId();
        doDMLQuery(connectionFactory, connectionName, ADD_SUBMISSION_IMAGE_SQL, ADD_SUBMISSION_IMAGE_ARGUMENT_TYPES,
                queryArgs, new LogMessage("SubmissionImage", null, null, errorMessage));
    }

    /**
     * Updates the submission image to persistence.
     *
     * @param submissionImage
     *            the submission image with updated data
     * @throws IllegalArgumentException
     *             If submissionImage is null, or submissionImage.isValidToPersist() returns false
     * @throws UploadPersistenceException
     *             If some error occurred when accessing the persistence
     * @since 1.2
     */
    public void updateSubmissionImage(SubmissionImage submissionImage) throws UploadPersistenceException {
        LOGGER.log(Level.INFO, new LogMessage("SubmissionImage", null, null, "update SubmissionImage"));

        Helper.assertObjectNotNull(submissionImage, "submissionImage", LOGGER);

        if (!submissionImage.isValidToPersist()) {
            throw Helper.logException(LOGGER, new IllegalArgumentException(
                    "The entity [SubmissionImage] is not valid to persist."));
        }

        LOGGER.log(Level.INFO, "update record into table[submission_image] with submission id: "
                + submissionImage.getSubmissionId() + ", and image id:" + submissionImage.getImageId());

        // build arguments
        Object[] queryArgs = new Object[] {submissionImage.getSortOrder(), submissionImage.getModifyDate(),
            submissionImage.getCreateDate(), submissionImage.getSubmissionId(), submissionImage.getImageId()};

        String errorMessage = "Failed to update record into table[submission_image] with submission id: "
                + submissionImage.getSubmissionId() + ", and image id:" + submissionImage.getImageId();
        doDMLQuery(connectionFactory, connectionName, UPDATE_SUBMISSION_IMAGE_SQL,
                UPDATE_SUBMISSION_IMAGE_ARGUMENT_TYPES, queryArgs, new LogMessage("SubmissionImage", null, null,
                        errorMessage));
    }

    /**
     * Removes the submission image to persistence.
     *
     * @param submissionImage
     *            the submission image to be removed from persistence
     * @throws IllegalArgumentException
     *             If submissionImage is null, submissionImage.getSubmissionId() <= 0 or submissionImage.getImageId() <=
     *             0
     * @throws UploadPersistenceException
     *             If some error occurred when accessing the persistence
     * @since 1.2
     */
    public void removeSubmissionImage(SubmissionImage submissionImage) throws UploadPersistenceException {
        LOGGER.log(Level.INFO, new LogMessage("SubmissionImage", null, null, "remove SubmissionImage."));

        Helper.assertObjectNotNull(submissionImage, "submissionImage", LOGGER);
        assertLongBePositive(submissionImage.getSubmissionId(), "submission id of the given submission image", LOGGER);
        assertLongBePositive(submissionImage.getImageId(), "image id of the given submission image", LOGGER);

        LOGGER.log(Level.INFO, "remove record into table[submission_image] with submission id: "
                + submissionImage.getSubmissionId() + ", and image id:" + submissionImage.getImageId());

        // build arguments
        Object[] queryArgs = new Object[] {submissionImage.getSubmissionId(), submissionImage.getImageId()};

        String errorMessage = "Failed to remove record into table[submission_image] with submission id: "
                + submissionImage.getSubmissionId() + ", and image id:" + submissionImage.getImageId();
        doDMLQuery(connectionFactory, connectionName, DELETE_SUBMISSION_IMAGE_SQL, new DataType[] {Helper.LONG_TYPE,
            Helper.INTEGER_TYPE}, queryArgs, new LogMessage("SubmissionImage", null, null, errorMessage));
    }

    /**
     * Loads the MimeType with the given id from persistence. Returns null if there is no MimeType with the given id.
     *
     * @param mimeTypeId
     *            The id of the item to retrieve
     * @return the loaded MimeType or null
     * @throws IllegalArgumentException
     *             if mimeTypeId <= 0
     * @throws UploadPersistenceException
     *             if there is an error reading the persistence data
     * @since 1.2
     */
    public MimeType loadMimeType(long mimeTypeId) throws UploadPersistenceException {
        LOGGER.log(Level.INFO, new LogMessage("MimeType", mimeTypeId, null,
                "Load MimeType. Delegate to loadMimeTypes(long[])."));

        assertLongBePositive(mimeTypeId, "mimeTypeId", LOGGER);

        MimeType[] mimeTypes = loadMimeTypes(new long[] {mimeTypeId});
        return mimeTypes.length == 0 ? null : mimeTypes[0];
    }

    /**
     * Gets the ids of all MIME types in the persistence. The individual MIME types can then be loaded with the
     * loadMimeType method.
     *
     * @return The ids of all MIME types
     * @throws UploadPersistenceException
     *             if there is an error reading the persistence store
     * @since 1.2
     */
    public long[] getAllMimeTypeIds() throws UploadPersistenceException {
        LOGGER.log(Level.INFO, new LogMessage("MimeType", null, null, "Load all MimeType ids in persistence."));

        return getAllEntityIds("mime_type_lu", "mime_type_id");
    }

    /**
     * Loads all MimeTypes with the given ids from persistence. May return an empty array.
     *
     * @param mimeTypeIds
     *            The ids of the objects to load
     * @return the loaded MimeTypes
     * @throws IllegalArgumentException
     *             if the mimeTypeIds is null or any id <= 0
     * @throws UploadPersistenceException
     *             if there is an error reading the persistence data
     * @since 1.2
     */
    public MimeType[] loadMimeTypes(long[] mimeTypeIds) throws UploadPersistenceException {
        LOGGER.log(Level.INFO, new LogMessage("MimeType", null, null, "Load MimeType with ids:"
                + Helper.getIdString(mimeTypeIds)));

        Helper.assertLongArrayNotNullAndOnlyHasPositive(mimeTypeIds, "mimeTypeIds", LOGGER);

        // simply return an empty array if mimeTypeIds is empty
        if (mimeTypeIds.length == 0) {
            return new MimeType[0];
        }

        Object[][] rows;
        try {
            rows = Helper.doQuery(connectionFactory, connectionName, LOAD_MIME_TYPES_SQL
                    + makeIdListString(mimeTypeIds), new DataType[0], new Object[0], new DataType[] {Helper.LONG_TYPE,
                Helper.LONG_TYPE, Helper.STRING_TYPE, Helper.INTEGER_TYPE, Helper.BOOLEAN_TYPE, Helper.STRING_TYPE,
                Helper.BOOLEAN_TYPE, Helper.STRING_TYPE}, LOGGER);
        } catch (PersistenceException e) {
            String errorMessage = "Unable to load MimeType from the database.";
            LOGGER.log(Level.ERROR, errorMessage + "\n" + LogMessage.getExceptionStackTrace(e));
            throw new UploadPersistenceException(errorMessage, e);
        }

        MimeType[] mimeTypes = new MimeType[rows.length];

        for (int i = 0; i < rows.length; i++) {
            MimeType mimeType = new MimeType();

            loadMimeTypeFieldsSequentially(mimeType, rows[i], 0);

            mimeTypes[i] = mimeType;
        }

        return mimeTypes;
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
     *             If projectId <= 0 or ownerId <= 0
     * @throws UploadPersistenceException
     *             If some error occurred when accessing the persistence
     * @since 1.2
     */
    public Submission[] getUserSubmissionsForProject(long projectId, long ownerId) throws UploadPersistenceException {
        LOGGER.log(Level.INFO, new LogMessage("Submission", null, null, MessageFormat.format(
                "Load Submissions with project id [{0}] and owner id [{1}].", projectId, ownerId)));

        assertLongBePositive(projectId, "projectId", LOGGER);
        assertLongBePositive(ownerId, "ownerId", LOGGER);

        Object[][] rows;
        try {
            // load submission
            rows = Helper.doQuery(connectionFactory, connectionName, GET_USER_SUBMISSIONS_FOR_PROJECT_SQL,
                    new DataType[] {Helper.LONG_TYPE, Helper.LONG_TYPE}, new Object[] {projectId, ownerId},
                    LOAD_SUBMISSIONS_COLUMN_TYPES, LOGGER);
        } catch (PersistenceException e) {
            String errorMessage = "Unable to load submissions from the database.";

            LOGGER.log(Level.ERROR, errorMessage + "\n" + LogMessage.getExceptionStackTrace(e));
            throw new UploadPersistenceException(errorMessage, e);
        }

        // create a new Submission array
        Submission[] submissions = new Submission[rows.length];

        // enumerate each data row
        for (int i = 0; i < rows.length; ++i) {
            // reference the current data row
            Object[] row = rows[i];

            // create a new Submission object
            Submission submission = new Submission();

            loadSubmissionFieldsSequentially(submission, row, 0);

            // assign it to the array
            submissions[i] = submission;
        }
        return submissions;
    }

    /**
     * Retrieves the project submissions. If projectId is unknown, this method returns an empty array.
     *
     * @param projectId
     *            the ID of the project
     * @return the retrieved project submissions (not null, doesn't contain null)
     * @throws IllegalArgumentException
     *             If projectId <= 0
     * @throws UploadPersistenceException
     *             If some error occurred when accessing the persistence
     * @since 1.2
     */
    public Submission[] getProjectSubmissions(long projectId) throws UploadPersistenceException {
        LOGGER.log(Level.INFO, new LogMessage("Submission", null, null, MessageFormat.format(
                "Load Submissions with project id [{0}].", projectId)));

        assertLongBePositive(projectId, "projectId", LOGGER);

        Object[][] rows;
        try {
            // load submission
            rows = Helper.doQuery(connectionFactory, connectionName, GET_PROJECT_SUBMISSIONS_SQL,
                    new DataType[] {Helper.LONG_TYPE}, new Object[] {projectId}, LOAD_SUBMISSIONS_COLUMN_TYPES, LOGGER);
        } catch (PersistenceException e) {
            String errorMessage = "Unable to load submissions to the database.";
            LOGGER.log(Level.ERROR, errorMessage + "\n" + LogMessage.getExceptionStackTrace(e));
            throw new UploadPersistenceException(errorMessage, e);
        }

        // create a new Submission array
        Submission[] submissions = new Submission[rows.length];

        // enumerate each data row
        for (int i = 0; i < rows.length; ++i) {
            // reference the current data row
            Object[] row = rows[i];

            // create a new Submission object
            Submission submission = new Submission();

            loadSubmissionFieldsSequentially(submission, row, 0);

            // assign it to the array
            submissions[i] = submission;
        }
        return submissions;
    }

    /**
     * Retrieves the images for submission with the given ID. If submissionId is unknown, this method returns an empty
     * array.
     *
     * @param submissionId
     *            the ID of the submission
     * @return the retrieved images for submission (not null, doesn't contain null)
     * @throws IllegalArgumentException
     *             If submissionId <= 0
     * @throws UploadPersistenceException
     *             If some error occurred when accessing the persistence
     * @since 1.2
     */
    public SubmissionImage[] getImagesForSubmission(long submissionId) throws UploadPersistenceException {
        LOGGER.log(Level.INFO, new LogMessage("SubmissionImage", null, null, MessageFormat.format(
                "Load SubmissionImages for submission id [{0}].", submissionId)));

        assertLongBePositive(submissionId, "submissionId", LOGGER);

        Object[][] rows;
        try {
            // load submission
            rows = Helper.doQuery(connectionFactory, connectionName, GET_SUBMISSION_IMAGES_FOR_SUBMISSION_SQL,
                    new DataType[] {Helper.LONG_TYPE}, new Object[] {submissionId},
                    GET_SUBMISSION_IMAGES_FOR_SUBMISSION_COLUMN_TYPES, LOGGER);
        } catch (PersistenceException e) {
            LOGGER.log(Level.ERROR, "Unable to load submissions to the database. \n"
                    + LogMessage.getExceptionStackTrace(e));
            throw new UploadPersistenceException("Unable to load submissions to the database.", e);
        }

        // create a new SubmissionImage array
        SubmissionImage[] submissionImages = new SubmissionImage[rows.length];

        // enumerate each data row
        for (int i = 0; i < rows.length; ++i) {
            // reference the current data row
            Object[] row = rows[i];

            // create a new SubmissionImage object
            SubmissionImage submissionImage = new SubmissionImage();

            // update the submission id.
            submissionImage.setSubmissionId(submissionId);

            loadSubmissionImageFieldsSequentially(submissionImage, row, 0);

            // assign it to the array
            submissionImages[i] = submissionImage;
        }

        return submissionImages;
    }

    /**
     * Retrieves the <code>SubmissionDeclaration</code> for submission with the given ID. If submissionId is unknown, this method returns null.
     * 
     * @param submissionId the ID of the submission
     * @return the retrieved <code>SubmissionDeclaration</code>, null if not found
     * @throws IllegalArgumentException
     *             If submissionId <= 0
     * @throws UploadPersistenceException
     *             If some error occurred when accessing the persistence
     * @since 1.4
     */
    public SubmissionDeclaration getSubmissionDeclaration(long submissionId) throws UploadPersistenceException {
        LOGGER.log(Level.INFO, new LogMessage("SubmissionDeclaration", null, null, MessageFormat.format(
                "Load SubmissionDeclaration for submission id [{0}].", submissionId)));
        
        assertLongBePositive(submissionId, "submissionId", LOGGER);
        
        Object[][] rows;
        try {
            // load submission declaration data
            rows = Helper.doQuery(connectionFactory, connectionName, GET_SUBMISSION_DECLARATION_SQL,
                    new DataType[] {Helper.LONG_TYPE}, new Object[] {submissionId}, GET_SUBMISSION_DECLARATION_COLUMN_TYPES, LOGGER);
            
        } catch (PersistenceException e) {
            LOGGER.log(Level.ERROR, "Unable to load submission_declaration to the database. \n"
                    + LogMessage.getExceptionStackTrace(e));
            throw new UploadPersistenceException("Unable to load submission_declaration to the database.", e);
        }
        
        if (rows != null && rows.length > 0) {
            SubmissionDeclaration submissionDeclaration = new SubmissionDeclaration();
            submissionDeclaration.setId((Long) rows[0][0]);
            submissionDeclaration.setComment((String) rows[0][1]);
            submissionDeclaration.setHasExternalContent((Boolean) rows[0][2]);
            if (submissionDeclaration.hasExternalContent()) {
                // load submission external content and properties
                List<SubmissionExternalContent> externalContents = new ArrayList<SubmissionExternalContent>();
                submissionDeclaration.setExternalContents(externalContents);
                SubmissionExternalContent currentExternalContent = null;
                for (int i = 0; i < rows.length; i++) {
                    if (currentExternalContent == null || (Long) rows[i][3] != currentExternalContent.getId()) {
                        // new submission external content
                        currentExternalContent = new SubmissionExternalContent();
                        currentExternalContent.setId((Long) rows[i][3]);
                        currentExternalContent.setDisplayPosition((Integer) rows[i][4]);
                        SubmissionExternalContentType externalContentType = new SubmissionExternalContentType();
                        externalContentType.setId((Long) rows[i][5]);
                        externalContentType.setName((String) rows[i][6]);
                        currentExternalContent.setExternalContentType(externalContentType);
                        currentExternalContent.setExternalContentProperties(new ArrayList<SubmissionExternalContentProperty>());
                        externalContents.add(currentExternalContent);
                    }
                    SubmissionExternalContentProperty property = new SubmissionExternalContentProperty();
                    property.setId((Long) rows[i][7]);
                    property.setName((String) rows[i][8]);
                    property.setValue((String) rows[i][9]);
                    currentExternalContent.getExternalContentProperties().add(property);
                }
            }
            return submissionDeclaration;
        }
        return null;
    }

    /**
     * <p>
     * Removes the given AuditedDeliverableStructure instance (by id) from the persistence.
     * </p>
     *
     * @param entity
     *            the given AuditedDeliverableStructure instance to remove
     * @param tableName
     *            the table name to delete the instance from
     * @param idName
     *            the id column name of the table that corresponds to the id field of the instance
     * @throws UploadPersistenceException
     *             if there is an error when during the persistence process
     */
    private void removeEntity(AuditedDeliverableStructure entity, String tableName, String idName)
        throws UploadPersistenceException {
        LOGGER.log(Level.INFO, "delete record from table: " + tableName + " with id:" + entity.getId());

        // build arguments
        Object[] queryArgs = new Object[] {entity.getId()};

        // remove entity from the database
        doDMLQuery(connectionFactory, connectionName, REMOVE_ENTITY_SQL.replaceAll(TABLE_NAME_PLACEHOLDER, tableName)
                .replaceAll(ID_NAME_PLACEHOLDER, idName), REMOVE_ENTITY_ARGUMENT_TYPES, queryArgs, new LogMessage(
                entity.getClass().getName(), entity.getId(), null, "Failed to delete record from table:" + tableName
                        + " with id:" + +entity.getId()));
    }

    /**
     * <p>
     * Adds the given NamedDeliverableStructure instance to the persistence.
     * </p>
     *
     * @param namedEntity
     *            the NamedDeliverableStructure instance to add
     * @param tableName
     *            the table name to persist the instance into
     * @param idName
     *            the id column name of the table that corresponds to the id field of the instance
     * @throws UploadPersistenceException
     *             if there is an error during the persistence process
     */
    private void addNameEntity(NamedDeliverableStructure namedEntity, String tableName, String idName)
        throws UploadPersistenceException {
        LOGGER.log(Level.INFO, "add record into table:" + tableName + " with id:" + namedEntity.getId());

        // build arguments
        Object[] queryArgs = new Object[] {namedEntity.getId(), namedEntity.getCreationUser(),
            namedEntity.getCreationTimestamp(), namedEntity.getModificationUser(),
            namedEntity.getModificationTimestamp(), namedEntity.getName(), namedEntity.getDescription()};

        // add named entity to database
        doDMLQuery(connectionFactory, connectionName, ADD_NAMED_ENTITY_SQL
                .replaceAll(TABLE_NAME_PLACEHOLDER, tableName).replaceAll(ID_NAME_PLACEHOLDER, idName),
                ADD_NAMED_ENTITY_ARGUMENT_TYPES, queryArgs, new LogMessage(namedEntity.getClass().getName(),
                        namedEntity.getId(), null, "Failed to add record into table:" + tableName + " with id:"
                                + +namedEntity.getId()));
    }

    /**
     * <p>
     * Updates the given NamedDeliverableStructure instance in the persistence.
     * </p>
     *
     * @param namedEntity
     *            the given NamedDeliverableStructure instance to update
     * @param tableName
     *            the table name to update the instance to
     * @param idName
     *            the id column name of the table that corresponds to the id field of the instance
     * @throws UploadPersistenceException
     *             if there is an error during the persistence process
     */
    private void updateNamedEntity(NamedDeliverableStructure namedEntity, String tableName, String idName)
        throws UploadPersistenceException {

        LOGGER.log(Level.INFO, "update record in table: " + tableName + " with id:" + namedEntity.getId());

        // build arguments
        Object[] queryArgs = new Object[] {namedEntity.getModificationUser(), namedEntity.getModificationTimestamp(),
            namedEntity.getName(), namedEntity.getDescription(), namedEntity.getId()};

        // update named entity
        doDMLQuery(connectionFactory, connectionName, UPDATE_NAMED_ENTITY_SQL.replaceAll(TABLE_NAME_PLACEHOLDER,
                tableName).replaceAll(ID_NAME_PLACEHOLDER, idName), UPDATE_NAMED_ENTITY_ARGUMENT_TYPES, queryArgs,
                new LogMessage(namedEntity.getClass().getName(), namedEntity.getId(), null,
                        "Failed to update record in table:" + tableName + " with id:" + +namedEntity.getId()));
    }

    /**
     * <p>
     * Load data items from the data row and fill the fields of an Upload instance.
     * </p>
     *
     * @param upload
     *            the Upload instance whose fields will be filled
     * @param row
     *            the data row
     * @param startIndex
     *            the start index to read from
     * @return the start index of the left data items that haven't been read
     */
    private int loadUploadFieldsSequentially(Upload upload, Object[] row, int startIndex) {

        startIndex = Helper.loadEntityFieldsSequentially(upload, row, startIndex);

        upload.setProject((Long) row[startIndex++]);
        upload.setProjectPhase((Long) row[startIndex++]);
        upload.setOwner((Long) row[startIndex++]);
        upload.setParameter((String) row[startIndex++]);
        upload.setDescription((String) row[startIndex++]);

        // create a new UploadType object
        UploadType uploadType = new UploadType();
        startIndex = Helper.loadNamedEntityFieldsSequentially(uploadType, row, startIndex);
        upload.setUploadType(uploadType);

        // create a new UploadStatus object
        UploadStatus uploadStatus = new UploadStatus();
        startIndex = Helper.loadNamedEntityFieldsSequentially(uploadStatus, row, startIndex);
        upload.setUploadStatus(uploadStatus);

        return startIndex;
    }

    /**
     * <p>
     * Load data items from the data row and fill the fields of an MimeType instance.
     * </p>
     *
     * @param upload
     *            the MimeType instance whose fields will be filled
     * @param row
     *            the data row
     * @param startIndex
     *            the start index to read from
     * @return the start index of the left data items that haven't been read
     * @since 1.2
     */
    private int loadMimeTypeFieldsSequentially(MimeType mimeType, Object[] row, int startIndex) {
        mimeType.setId((Long) row[startIndex++]);

        FileType fileType = new FileType();
        fileType.setId((Long) row[startIndex++]);
        fileType.setDescription((String) row[startIndex++]);
        fileType.setSort((Integer) row[startIndex++]);
        fileType.setImageFile((Boolean) row[startIndex++]);
        fileType.setExtension((String) row[startIndex++]);
        fileType.setBundledFile((Boolean) row[startIndex++]);
        mimeType.setFileType(fileType);

        mimeType.setDescription((String) row[startIndex++]);

        return startIndex;
    }

    /**
     * <p>
     * Load data items from the data row and fill the fields of an SubmissionImage instance.
     * </p>
     *
     * @param upload
     *            the SubmissionImage instance whose fields will be filled
     * @param row
     *            the data row
     * @param startIndex
     *            the start index to read from
     * @return the start index of the left data items that haven't been read
     * @since 1.2
     */
    private int loadSubmissionImageFieldsSequentially(SubmissionImage submissionImage, Object[] row, int startIndex) {
        submissionImage.setImageId((Integer) row[startIndex++]);
        submissionImage.setSortOrder((Integer) row[startIndex++]);
        submissionImage.setModifyDate((Date) row[startIndex++]);
        submissionImage.setCreateDate((Date) row[startIndex++]);
        return startIndex;
    }

    /**
     * Loads the prize entity from the data row and fill the fields of an Prize instance.
     *
     * @param prize
     *            the Prize instance whose fields will be filled
     * @param row
     *            the data row
     * @param startIndex
     *            the start index to read from
     * @return the start index of the left data items that haven't been read
     * @since 1.2
     */
    private int loadPrizeEntityFieldsSequentially(Prize prize, Object[] row, int startIndex) {
        Long id = (Long) row[startIndex++];
        if (id != null) {
            prize.setId(id);
            prize.setPlace((Integer) row[startIndex++]);
            prize.setPrizeAmount((Double) row[startIndex++]);
            PrizeType prizeType = new PrizeType();
            prizeType.setId((Long) row[startIndex++]);
            prize.setPrizeType(prizeType);
            prize.setNumberOfSubmissions((Integer) row[startIndex++]);
            prize.setCreationUser((String) row[startIndex++]);
            prize.setCreationTimestamp((Date) row[startIndex++]);
            prize.setModificationUser((String) row[startIndex++]);
            prize.setModificationTimestamp((Date) row[startIndex++]);
            prizeType.setDescription((String) row[startIndex++]);
        } else {
            // increase the index, in case there are some additional fields.
            startIndex += 9;
        }

        return startIndex;
    }

    /**
     * <p>
     * Load data items from the data row and fill the fields of an Submission instance.
     * </p>
     *
     * @param submission
     *            the Submission instance whose fields will be filled
     * @param row
     *            the data row
     * @param startIndex
     *            the start index to read from
     * @return the start index of the left data items that haven't been read
     * @throws UploadPersistenceException if any problem to load the submission fields.
     */
    private int loadSubmissionFieldsSequentially(Submission submission, Object[] row, int startIndex)
        throws UploadPersistenceException {

        startIndex = Helper.loadEntityFieldsSequentially(submission, row, startIndex);

        // create a new SubmissionStatus object
        SubmissionStatus submissionStatus = new SubmissionStatus();
        startIndex = Helper.loadNamedEntityFieldsSequentially(submissionStatus, row, startIndex);
        submission.setSubmissionStatus(submissionStatus);

        SubmissionType submissionType = new SubmissionType();
        startIndex = Helper.loadNamedEntityFieldsSequentially(submissionType, row, startIndex);
        submission.setSubmissionType(submissionType);

        Object screeningScore = row[startIndex++];
        if (screeningScore != null) {
            submission.setScreeningScore(((Double) screeningScore));
        }
        Object initialScore = row[startIndex++];
        if (initialScore != null) {
            submission.setInitialScore(((Double) initialScore));
        }
        Object finalScore = row[startIndex++];
        if (finalScore != null) {
            submission.setFinalScore(((Double) finalScore));
        }
        Object placement = row[startIndex++];
        if (placement != null) {
            submission.setPlacement(((Long) placement));
        }

		Object userRank = row[startIndex++];
		if(userRank != null) {
		    submission.setUserRank((Integer) userRank);
		}
		
		Object extra = row[startIndex++];
		if (extra != null) {
            submission.setExtra((Boolean) extra);
		}
        Prize prize = new Prize();
        startIndex = loadPrizeEntityFieldsSequentially(prize, row, startIndex);

        if (prize.getId() > 0) {
            submission.setPrize(prize);
        }

        // retrieve the submission images.
        submission.setImages(Arrays.asList(getImagesForSubmission(submission.getId())));
        
        // retrieve the submission declaration
        submission.setSubmissionDeclaration(getSubmissionDeclaration(submission.getId()));

        // retrieve the submission upload
        Upload upload = new Upload();
        startIndex = loadUploadFieldsSequentially(upload, row, startIndex);
        submission.setUpload(upload);

        return startIndex;
    }

    /**
     * <p>
     * Loads submission from the result of the SELECT operation.
     * </p>
     *
     * @param resultSet
     *            Result of the SELECT operation.
     * @return loaded submission.
     * @throws UploadPersistenceException
     *             if any error occurs while reading the result.
     */
    private Submission loadSubmission(CustomResultSet resultSet) throws UploadPersistenceException {
        Submission submission = new Submission();

        try {
            if (resultSet.getObject("screening_score") != null) {
                submission.setScreeningScore(resultSet.getDouble("screening_score"));
            }
            if (resultSet.getObject("initial_score") != null) {
                submission.setInitialScore(resultSet.getDouble("initial_score"));
            }
            if (resultSet.getObject("final_score") != null) {
                submission.setFinalScore(resultSet.getDouble("final_score"));
            }
            if (resultSet.getObject("placement") != null) {
                submission.setPlacement(resultSet.getLong("placement"));
            }

			if (resultSet.getObject("mark_for_purchase") != null) {
                submission.setExtra(resultSet.getBoolean("mark_for_purchase"));
            }
			
            submission.setId(resultSet.getLong("submission_id"));
			if(resultSet.getObject("user_rank")!=null) {
			    submission.setUserRank(resultSet.getInt("user_rank"));
			}
            
            submission.setCreationUser(resultSet.getString("submission_create_user"));
            submission.setCreationTimestamp(resultSet.getDate("submission_create_date"));
            submission.setModificationUser(resultSet.getString("submission_modify_user"));
            submission.setModificationTimestamp(resultSet.getDate("submission_modify_date"));

            // create a new SubmissionStatus object
            SubmissionStatus submissionStatus = new SubmissionStatus();

            submissionStatus.setId(resultSet.getLong("submission_status_id"));
            submissionStatus.setCreationUser(resultSet.getString("submission_status_create_user"));
            submissionStatus.setCreationTimestamp(resultSet.getDate("submission_status_create_date"));
            submissionStatus.setModificationUser(resultSet.getString("submission_status_modify_user"));
            submissionStatus.setModificationTimestamp(resultSet.getDate("submission_status_modify_date"));
            submissionStatus.setName(resultSet.getString("submission_status_name"));
            submissionStatus.setDescription(resultSet.getString("submission_status_description"));

            submission.setSubmissionStatus(submissionStatus);

            // create a new SubmissionStatus object
            SubmissionType submissionType = new SubmissionType();

            submissionType.setId(resultSet.getLong("submission_type_id"));
            submissionType.setCreationUser(resultSet.getString("submission_type_create_user"));
            submissionType.setCreationTimestamp(resultSet.getDate("submission_type_create_date"));
            submissionType.setModificationUser(resultSet.getString("submission_type_modify_user"));
            submissionType.setModificationTimestamp(resultSet.getDate("submission_type_modify_date"));
            submissionType.setName(resultSet.getString("submission_type_name"));
            submissionType.setDescription(resultSet.getString("submission_type_description"));

            submission.setSubmissionType(submissionType);

            // load prize if exist
            if (resultSet.getObject("prize_id") != null) {
                Prize prize = new Prize();

                prize.setId(resultSet.getLong("prize_id"));
                prize.setPlace(resultSet.getInt("place"));
                prize.setPrizeAmount(resultSet.getDouble("prize_amount"));
                prize.setNumberOfSubmissions(resultSet.getInt("number_of_submissions"));
                prize.setCreationUser(resultSet.getString("prize_create_user"));
                prize.setCreationTimestamp(resultSet.getDate("prize_create_date"));
                prize.setModificationUser(resultSet.getString("prize_modify_user"));
                prize.setModificationTimestamp(resultSet.getDate("prize_modify_date"));

                PrizeType prizeType = new PrizeType();
                prizeType.setId(resultSet.getLong("prize_type_id"));
                prizeType.setDescription(resultSet.getString("prize_type_desc"));
                prize.setPrizeType(prizeType);
                submission.setPrize(prize);
            }
            
            submission.setUpload(loadUpload(resultSet));

            return submission;
        } catch (InvalidCursorStateException e) {
            throw new UploadPersistenceException("Error loading submission.", e);
        } catch (NullColumnValueException ne) {
            throw new UploadPersistenceException("Error loading submission.", ne);
        }
    }

    /**
     * <p>
     * Loads upload from the result of the SELECT operation.
     * </p>
     *
     * @param resultSet
     *            Result of the SELECT operation.
     * @return loaded upload.
     * @throws UploadPersistenceException
     *             if any error occurs while reading the result.
     */
    private Upload loadUpload(CustomResultSet resultSet) throws UploadPersistenceException {
        try {
            Upload upload = new Upload();

            upload.setId(resultSet.getLong("upload_id"));
            upload.setCreationUser(resultSet.getString("upload_create_user"));
            upload.setCreationTimestamp(resultSet.getDate("upload_create_date"));
            upload.setModificationUser(resultSet.getString("upload_modify_user"));
            upload.setModificationTimestamp(resultSet.getDate("upload_modify_date"));
            upload.setUrl(resultSet.getString("upload_url"));

            upload.setProject(resultSet.getLong("project_id"));
            if (resultSet.getObject("project_phase_id") != null) {
                upload.setProjectPhase(resultSet.getLong("project_phase_id"));
            }
            upload.setOwner(resultSet.getLong("resource_id"));
            upload.setParameter(resultSet.getString("upload_parameter"));
            upload.setDescription(resultSet.getString("upload_desc"));

            // create a new UploadType object
            UploadType uploadType = new UploadType();

            uploadType.setId(resultSet.getLong("upload_type_id"));
            uploadType.setCreationUser(resultSet.getString("upload_type_create_user"));
            uploadType.setCreationTimestamp(resultSet.getDate("upload_type_create_date"));
            uploadType.setModificationUser(resultSet.getString("upload_type_modify_user"));
            uploadType.setModificationTimestamp(resultSet.getDate("upload_type_modify_date"));
            uploadType.setName(resultSet.getString("upload_type_name"));
            uploadType.setDescription(resultSet.getString("upload_type_description"));

            upload.setUploadType(uploadType);

            // create a new UploadStatus object
            UploadStatus uploadStatus = new UploadStatus();

            uploadStatus.setId(resultSet.getLong("upload_status_id"));
            uploadStatus.setCreationUser(resultSet.getString("upload_status_create_user"));
            uploadStatus.setCreationTimestamp(resultSet.getDate("upload_status_create_date"));
            uploadStatus.setModificationUser(resultSet.getString("upload_status_modify_user"));
            uploadStatus.setModificationTimestamp(resultSet.getDate("upload_status_modify_date"));
            uploadStatus.setName(resultSet.getString("upload_status_name"));
            uploadStatus.setDescription(resultSet.getString("upload_status_description"));

            upload.setUploadStatus(uploadStatus);

            return upload;
        } catch (InvalidCursorStateException icse) {
            throw new UploadPersistenceException("Error loading upload.", icse);
        }catch (NullColumnValueException ne) {
            throw new UploadPersistenceException("Error loading submission.", ne);
        }
    }

    /**
     * <p>
     * Loads all NamedDeliverableStructure with the given ids from persistence. May return a 0-length array.
     * </p>
     *
     * @param namedEntityIds
     *            The ids of the objects to load
     * @param type
     *            the concrete class type of the NamedDeliverableStructure
     * @param tableName
     *            the name of the table to load from
     * @param idName
     *            the id column name of the table that corresponds to the id field of the instance
     * @return an array of all the instances of 'type' type
     * @throws UploadPersistenceException
     *             if there is an error during the persistence process
     */
    private NamedDeliverableStructure[] loadNamedEntities(long[] namedEntityIds, Class<?> type, String tableName,
            String idName) throws UploadPersistenceException {

        // check if the given type is of NamedDeliverableStructure kind.
        if (!NamedDeliverableStructure.class.isAssignableFrom(type)) {
            throw new IllegalArgumentException("type [" + type.getName()
                    + "] should be a NamedDeliverableStructure type or its sub-type.");
        }

        // simply return an empty 'type'[] array if
        // namedEntityIds is empty
        if (namedEntityIds.length == 0) {
            return (NamedDeliverableStructure[]) Array.newInstance(type, 0);
        }

        Object[][] rows;
        try {
            // load named entities
            rows = Helper.doQuery(connectionFactory, connectionName, LOAD_NAMED_ENTITIES_SQL.replaceAll(
                    TABLE_NAME_PLACEHOLDER, tableName).replaceAll(ID_NAME_PLACEHOLDER, idName)
                    + makeIdListString(namedEntityIds), new DataType[0], new Object[0],
                    LOAD_NAMED_ENTITIES_COLUMN_TYPES, LOGGER);
        } catch (PersistenceException e) {
            LOGGER.log(Level.ERROR, "Unable to load " + type.getName() + " from the database."
                    + LogMessage.getExceptionStackTrace(e));
            throw new UploadPersistenceException("Unable to load " + type.getName() + " from the database.", e);
        }

        try {
            // create a new array of 'type'[] type
            NamedDeliverableStructure[] namedEntities = (NamedDeliverableStructure[]) Array.newInstance(type,
                    rows.length);

            // enumerate each data row
            for (int i = 0; i < rows.length; ++i) {
                // reference the current data row
                Object[] row = rows[i];

                // create a new 'type' object
                NamedDeliverableStructure namedEntity = (NamedDeliverableStructure) type.newInstance();
                Helper.loadNamedEntityFieldsSequentially(namedEntity, row, 0);

                // assign it to the array
                namedEntities[i] = namedEntity;
            }
            return namedEntities;
        } catch (InstantiationException e) {
            LOGGER.log(Level.ERROR, "Unable to create an instance of " + type.getName() + ". \n"
                    + LogMessage.getExceptionStackTrace(e));
            throw new UploadPersistenceException("Unable to create an instance of " + type.getName() + ".", e);
        } catch (IllegalAccessException e) {
            LOGGER.log(Level.ERROR, "Unable to create an instance of " + type.getName() + ". \n"
                    + LogMessage.getExceptionStackTrace(e));
            throw new UploadPersistenceException("Unable to create an instance of " + type.getName() + ".", e);
        }
    }

    /**
     * <p>
     * Gets the ids from the given table in the persistence.
     * </p>
     *
     * @param tableName
     *            the table name to load the ids from
     * @param idName
     *            the id column name of the table
     * @return all the ids in the table
     * @throws UploadPersistenceException
     *             if there is an error during the persistence process
     */
    private long[] getAllEntityIds(String tableName, String idName) throws UploadPersistenceException {

        Object[][] rows;
        try {
            // load all upload type ids
            rows = Helper.doQuery(connectionFactory, connectionName, GET_ALL_ENTITY_IDS_SQL.replaceAll(
                    TABLE_NAME_PLACEHOLDER, tableName).replaceAll(ID_NAME_PLACEHOLDER, idName), new DataType[0],
                    new Object[0], GET_ALL_ENTITY_IDS_COLUMN_TYPES, LOGGER);
        } catch (PersistenceException e) {
            String errMessage = "Unable to get all ids from the table [" + tableName + "].";
            LOGGER.log(Level.ERROR, errMessage + LogMessage.getExceptionStackTrace(e));
            throw new UploadPersistenceException(errMessage, e);
        }

        // create a long array and set values
        long[] ids = new long[rows.length];

        // enumerate each data row
        for (int i = 0; i < rows.length; ++i) {
            ids[i] = ((Long) rows[i][0]).longValue();
        }
        return ids;
    }

    /**
     * <p>
     * This method performs the given DML (query on the given connection using the given query arguments and their
     * types. The update count returned from the query is then returned.
     * </p>
     *
     * @param connectionFactory
     *            the connection factory
     * @param connectionName
     *            the connection name
     * @param queryString
     *            the query to be performed
     * @param argumentTypes
     *            the types of each object in queryArgs, use one of the values STRING_TYPE, LONG_TYPE or BOOLEAN_TYPE
     *            here
     * @param queryArgs
     *            the arguments to be used in the query
     * @param logMessage
     *            the LogMessage instance.
     * @return the number of database rows affected by the query
     * @throws IllegalArgumentException
     *             if connectionFactory is null
     * @throws IllegalArgumentException
     *             if queryString is null or empty (trimmed)
     * @throws IllegalArgumentException
     *             if argumentTypes is null or contains null
     * @throws IllegalArgumentException
     *             if queryArgs is null or length of it is different from that of argumentTypes
     * @throws UploadPersistenceException
     *             if the query fails
     */
    private static int doDMLQuery(DBConnectionFactory connectionFactory, String connectionName, String queryString,
            DataType[] argumentTypes, Object[] queryArgs, LogMessage logMessage) throws UploadPersistenceException {

        Connection conn = null;
        try {
            // create the connection with auto-commit mode enabled
            conn = Helper.createConnection(connectionFactory, connectionName, true, false, LOGGER);

            return doDMLQuery(conn, queryString, argumentTypes, queryArgs);
        } catch (PersistenceException e) {
            LOGGER.log(Level.ERROR, logMessage.getLogMessage() + "\n" + LogMessage.getExceptionStackTrace(e));

            throw new UploadPersistenceException(logMessage.getMessage(), e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    throw new UploadPersistenceException("Error occurs when closing the connection.", e);
                }
            }
        }
    }

    /**
     * <p>
     * This method performs the given DML (query on the given connection using the given query arguments and their
     * types. The update count returned from the query is then returned.
     * </p>
     * <p>
     * Note: The given connection is not closed or committed in this method.
     * </p>
     *
     * @param connection
     *            the connection to perform the query on
     * @param queryString
     *            the query to be performed
     * @param argumentTypes
     *            the types of each object in queryArgs, use one of the values STRING_TYPE, LONG_TYPE or BOOLEAN_TYPE
     *            here
     * @param queryArgs
     *            the arguments to be used in the query
     * @return the number of database rows affected by the query
     * @throws IllegalArgumentException
     *             if connection is null
     * @throws IllegalArgumentException
     *             if queryString is null or empty (trimmed)
     * @throws IllegalArgumentException
     *             if argumentTypes is null or contains null
     * @throws IllegalArgumentException
     *             if queryArgs is null or length of it is different from that of argumentTypes
     * @throws UploadPersistenceException
     *             if the query fails
     */
    private static int doDMLQuery(Connection connection, String queryString, DataType[] argumentTypes,
            Object[] queryArgs) throws UploadPersistenceException {
        Helper.assertObjectNotNull(connection, "connection", LOGGER);
        Helper.assertStringNotNullNorEmpty(queryString, "queryString", LOGGER);
        Helper.assertArrayNotNullNorHasNull(argumentTypes, "argumentTypes", LOGGER);
        Helper.assertArrayLengthEqual(queryArgs, "queryArgs", argumentTypes, "argumentTypes", LOGGER);
        Helper.assertObjectNotNull(queryArgs, "queryArgs", LOGGER);

        PreparedStatement preparedStatement = null;

        try {
            // prepare the statement.
            preparedStatement = connection.prepareStatement(queryString);

            // build the statement.
            for (int i = 0; i < queryArgs.length; i++) {
                argumentTypes[i].setValue(preparedStatement, i + 1, queryArgs[i]);
            }

            // execute the statement.
            preparedStatement.execute();
            return preparedStatement.getUpdateCount();
        } catch (SQLException e) {
            throw new UploadPersistenceException("Error occurs while executing query [" + queryString
                    + "] using the query arguments " + Arrays.asList(queryArgs).toString() + ".", e);
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    // simply ignore it, as close connection will retry to clean the resource.
                }
            }
        }
    }

    /**
     * <p>
     * Check if the given AuditedDeliverableStructure instance is valid to persist.
     * </p>
     *
     * @param entity
     *            the given AuditedDeliverableStructure instance to check.
     * @param name
     *            the name to identify the instance.
     * @param logger
     *            the Log instance
     * @throws IllegalArgumentException
     *             if the given instance is null or isValidToPersist returns false.
     */
    private static void assertEntityNotNullAndValidToPersist(AuditedDeliverableStructure entity, String name,
            Log logger) {
        Helper.assertObjectNotNull(entity, name, logger);

        if (!entity.isValidToPersist()) {
            throw Helper.logException(logger, new IllegalArgumentException("The entity [" + name
                    + "] is not valid to persist."));
        }
    }

    /**
     * <p>
     * Check if the given long value is positive.
     * </p>
     *
     * @param value
     *            the long value to check
     * @param name
     *            the name to identify the long value.
     * @param logger
     *            the Log instance
     * @throws IllegalArgumentException
     *             if the given long value is negative or zero
     * @since 1.2
     */
    private static void assertLongBePositive(long value, String name, Log logger) {
        if (value <= 0) {
            throw Helper.logException(logger, new IllegalArgumentException(name + " should be positive."));
        }
    }

    /**
     * <p>
     * Create a String object from the ids array in the form of "(id0, id1, id2, ...)", used in a sql statement.
     * </p>
     *
     * @param ids
     *            the ids array
     * @return A String that represents the ids array in the form of "(id0, id1, id2, ...)"
     * @throws IllegalArgumentException
     *             if ids is null or empty
     */
    private static String makeIdListString(long[] ids) {
        Helper.assertObjectNotNull(ids, "ids", LOGGER);
        if (ids.length == 0) {
            throw new IllegalArgumentException("ids should not be empty.");
        }

        StringBuffer sb = new StringBuffer();
        sb.append('(');

        // enumerate each id in the array
        for (int i = 0; i < ids.length; ++i) {
            // if it's not the first one, append a comma
            if (i != 0) {
                sb.append(',');
            }
            sb.append(ids[i]);
        }
        sb.append(')');

        return sb.toString();
    }

    /**
     * <p>
     * Gets the id field of the given entity, or null, if the entity is null.
     * </p>
     *
     * @param entity
     *            the entity
     * @return the value of id field, or null
     * @since 1.2
     */
    private static Long getIdFromEntity(IdentifiableDeliverableStructure entity) {
        return entity == null ? null : entity.getId();
    }
}
