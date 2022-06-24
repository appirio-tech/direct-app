/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.review.specification.ejb;

import com.topcoder.date.workdays.DefaultWorkdays;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

import com.topcoder.management.deliverable.Submission;
import com.topcoder.management.deliverable.SubmissionStatus;
import com.topcoder.management.deliverable.SubmissionType;
import com.topcoder.management.deliverable.Upload;
import com.topcoder.management.deliverable.UploadStatus;
import com.topcoder.management.deliverable.UploadType;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.resource.ResourceRole;
import com.topcoder.management.review.data.Review;
import com.topcoder.management.scorecard.data.Scorecard;
import com.topcoder.management.scorecard.data.ScorecardStatus;
import com.topcoder.management.scorecard.data.ScorecardType;

import com.topcoder.project.phases.Dependency;
import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.PhaseStatus;
import com.topcoder.project.phases.PhaseType;
import com.topcoder.project.phases.Project;

import com.topcoder.util.config.ConfigManager;

import junit.framework.TestCase;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.lang.reflect.Field;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import java.util.Date;
import java.util.Iterator;
import java.util.StringTokenizer;


/**
 * <p>The base test case for Unit tests.</p>
 *
 * @author myxgyy
 * @version 1.0
 */
public abstract class BaseTest extends TestCase {
    /**
     * Represents the directory used to store uploaded files.
     */
    public static final String UPLOAD_DIRECTORY = "test_files" + File.separatorChar + "upload"
        + File.separatorChar;

    /**
     * Constant for one day time.
     */
    private static final long DAY = 24 * 60 * 60 * 1000;

    /**
     * Represents the file contains statement to insert look up data.
     */
    private static final String INSERT_SQL = "/insert.sql";

    /**
     * Constant for database connection factory configuration file.
     */
    private static final String DB_FACTORY_CONFIG_FILE = "config/DB_Factory.xml";

    /**
     * Constant for logging wrapper configuration file.
     */
    private static final String LOGGING_WRAPPER_CONFIG_FILE = "config/Logging_Wrapper.xml";

    /**
     * Array of all the config file names for various dependency components.
     */
    private static final String[] COMPONENT_FILE_NAMES = new String[] {"config/Project_Management.xml",
        "config/Phase_Management.xml", "config/Review_Management.xml", "config/Scorecard_Management.xml",
        "config/Screening_Management.xml", "config/Upload_Resource_Search.xml", "config/User_Project_Data_Store.xml",
        "config/SearchBuilderCommon.xml", "config/Project_Services.xml", "config/UploadExternal_Services.xml",
        "config/Failure_Config.xml"};

    /**
     * An array of table names to be cleaned.
     */
    private static final String[] ALL_TABLE_NAMES = new String[] {"project_user_audit", "screening_result",
        "screening_task", "project_phase_audit", "project_info_audit", "notification", "project_audit", "review",
        "resource_submission", "submission", "upload", "resource_info", "resource", "phase_criteria",
        "phase_dependency", "project_phase", "project_scorecard", "project_info", "project", "scorecard",
        "comp_forum_xref", "comp_versions", "categories", "comp_catalog", "user_reliability", "user_rating",
        "user", "email", "linked_project_xref", "id_sequences", "audit_action_type_lu",
        "project_category_lu", "project_type_lu", "scorecard_type_lu", "scorecard_status_lu",
        "scorecard_question_type_lu", "project_status_lu", "project_info_type_lu", "deliverable_lu",
        "phase_status_lu", "phase_criteria_type_lu", "resource_role_lu", "resource_info_type_lu",
        "upload_type_lu", "upload_status_lu", "submission_status_lu", "comment_type_lu", "phase_type_lu",
        "notification_type_lu", "screening_status_lu", "screening_response_lu", "response_severity_lu",
        "submission_type_lu", "link_type_lu"};

    /**
     * Represents the configuration manager instance used in tests.
     */
    private ConfigManager configManager;

    /**
     * Holds db connection factory instance.
     */
    private DBConnectionFactory dbFactory;

    /**
     * Holds connection.
     */
    private Connection connection;

    /**
     * <p>Sets up the test environment.</p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    @SuppressWarnings("deprecation")
    protected void setUp() throws Exception {
        configManager = ConfigManager.getInstance();

        // Remove all namespaces
        Iterator<?> iter = configManager.getAllNamespaces();

        while (iter.hasNext()) {
            configManager.removeNamespace((String) iter.next());
        }

        // init db factory
        configManager.add(DB_FACTORY_CONFIG_FILE);

        // load logging wrapper configuration
        configManager.add(LOGGING_WRAPPER_CONFIG_FILE);

        // add all dependencies config
        for (String config : COMPONENT_FILE_NAMES) {
            configManager.add(config);
        }

        dbFactory = new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName());
        cleanTables();
        executeSqlFile(INSERT_SQL);
    }

    /**
     * <p>Cleans up the test environment.</p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    protected void tearDown() throws Exception {
        // Remove all namespaces
        Iterator<?> iter = configManager.getAllNamespaces();

        while (iter.hasNext()) {
            configManager.removeNamespace((String) iter.next());
        }
        // cleanTables();
        closeConnection();
        clearFiles();
    }

    /**
     * <p>
     * Executes the sql file.
     * </p>
     *
     * @param file
     *            The sql file to be executed.
     * @throws Exception
     *             to JUnit
     */
    private void executeSqlFile(String file) throws Exception {
        String sql = getSql(file);

        Connection conn = null;
        Statement stmt = null;

        StringTokenizer st = new StringTokenizer(sql, ";");
        try {
            conn = getConnection();
            stmt = conn.createStatement();

            for (int count = 1; st.hasMoreTokens(); count++) {
                String statement = st.nextToken().trim();

                if (statement.length() > 0) {
                    stmt.addBatch(statement);
                }
            }

            stmt.executeBatch();
        } finally {
            closeStatement(stmt);
            closeConnection();
        }
    }

    /**
     * <p>
     * Gets the sql file content.
     * </p>
     *
     * @param file
     *            The sql file to get its content.
     * @return The content of sql file.
     * @throws Exception
     *             to JUnit
     */
    private static String getSql(String file) throws Exception {
        StringBuilder sql = new StringBuilder();
        InputStream is = BaseTest.class.getResourceAsStream(file);

        if (is == null) {
            throw new FileNotFoundException("Not found: " + file);
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(
                    is));

        try {
            for (String s = in.readLine(); s != null;
                    s = in.readLine()) {
                int commentIndex = s.indexOf("--");

                if (commentIndex >= 0) { // Remove any SQL comment
                    s = s.substring(0, commentIndex);
                }

                sql.append(s).append(' '); // The BufferedReader drops newlines so insert
                                           // whitespace
            }
        } finally {
            in.close();
        }

        return sql.toString();
    }

    /**
     * <p>Sets the field value to bean instance.</p>
     *
     * @param <T> The generic type.
     * @param clazz the class type.
     * @param instance the instance to be set.
     * @param fieldName The field name.
     * @param value The field value to set.
     *
     * @throws Exception to JUnit.
     */
    protected static <T> void setField(Class<T> clazz, T instance, String fieldName, Object value)
        throws Exception {
        Field field = clazz.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(instance, value);
        field.setAccessible(false);
    }

    /**
     * <p>Gets value for field of given object.</p>
     *
     * @param obj the given object.
     * @param field the field name.
     *
     * @return the field value.
     *
     * @throws Exception to JUnit.
     */
    protected static Object getField(Object obj, String field)
        throws Exception {
        Object value = null;
        Field declaredField = obj.getClass().getDeclaredField(field);
        declaredField.setAccessible(true);

        value = declaredField.get(obj);
        declaredField.setAccessible(false);

        return value;
    }

    /**
     * Helper method to create a phase instance.
     *
     * @param phaseId phase id.
     * @param phaseStatusId phase Status Id.
     * @param phaseStatusName phase Status Name.
     * @param phaseTypeId phase Type Id.
     * @param phaseTypeName phase Type Name.
     *
     * @return phase instance.
     */
    protected Phase createPhase(long phaseId, long phaseStatusId, String phaseStatusName,
        long phaseTypeId, String phaseTypeName) {
        Project project = new Project(new Date(), new DefaultWorkdays());
        project.setId(1);

        Phase phase = new Phase(project, 2000);
        phase.setId(phaseId);
        phase.setPhaseStatus(new PhaseStatus(phaseStatusId, phaseStatusName));
        phase.setPhaseType(new PhaseType(phaseTypeId, phaseTypeName));

        return phase;
    }

    /**
     * Helper method to create Resource instance.
     *
     * @param resourceId resource Id.
     * @param phaseId phase Id.
     * @param projectId project Id.
     * @param resourceRoleId resource Role Id.
     *
     * @return Resource instance.
     */
    protected Resource createResource(long resourceId, Long phaseId, long projectId,
        long resourceRoleId) {
        Resource resource = new Resource();
        resource.setId(resourceId);
        resource.setPhase(phaseId);
        resource.setProject(new Long(projectId));
        resource.setResourceRole(new ResourceRole(resourceRoleId));

        return resource;
    }

    /**
     * Helper method to create Upload instance.
     *
     * @param uploadId upload id.
     * @param projectId project id.
     * @param resourceId resource id.
     * @param uploadTypeId upload type id.
     * @param uploadStatusId upload status id.
     * @param parameter parameter.
     *
     * @return Upload instance.
     */
    protected Upload createUpload(long uploadId, long projectId, long resourceId,
        long uploadTypeId, long uploadStatusId, String parameter) {
        Upload upload = new Upload();
        upload.setId(uploadId);
        upload.setProject(projectId);
        upload.setOwner(resourceId);
        upload.setUploadType(new UploadType(uploadTypeId));
        upload.setUploadStatus(new UploadStatus(uploadStatusId));
        upload.setParameter(parameter);
        upload.setCreationTimestamp(new Date());

        return upload;
    }

    /**
     * Helper method to create Submission instance.
     *
     * @param submissionId submission id.
     * @param uploadId upload id.
     * @param submissionStatusId submission status id.
     * @param submissionTypeId submission type id.
     *
     * @return Submission instance.
     */
    protected Submission createSubmission(long submissionId, long uploadId,
        long submissionStatusId, long submissionTypeId) {
        Submission submission = new Submission(submissionId);
        submission.setUpload(new Upload(uploadId));
        submission.setSubmissionStatus(new SubmissionStatus(submissionStatusId));
        submission.setSubmissionType(new SubmissionType(submissionTypeId));

        return submission;
    }

    /**
     * Helper method to create Scorecard instance.
     *
     * @param scorecardId scorecard id.
     * @param scorecardStatusId scorecard status id.
     * @param scorecardTypeId scorecard type id.
     * @param projectCategoryId project category id.
     * @param name name.
     * @param version version.
     * @param minScore min score.
     * @param maxScore max score.
     *
     * @return Scorecard instance.
     */
    protected Scorecard createScorecard(long scorecardId, long scorecardStatusId,
        long scorecardTypeId, long projectCategoryId, String name, String version, float minScore,
        float maxScore) {
        Scorecard scorecard = new Scorecard(scorecardId);
        scorecard.setScorecardStatus(new ScorecardStatus(scorecardStatusId));
        scorecard.setScorecardType(new ScorecardType(scorecardTypeId));
        scorecard.setCategory(projectCategoryId);
        scorecard.setName(name);
        scorecard.setVersion(version);
        scorecard.setMinScore(minScore);
        scorecard.setMaxScore(maxScore);

        return scorecard;
    }

    /**
     * Helper method to create Review instance.
     *
     * @param reviewId review id.
     * @param resourceId resource id.
     * @param submissionId submission id.
     * @param scorecardId scorecard id.
     * @param committed committed.
     * @param score score.
     *
     * @return Review instance.
     */
    protected Review createReview(long reviewId, long resourceId, long submissionId,
        long scorecardId, boolean committed, float score) {
        Review review = new Review(reviewId);
        review.setAuthor(resourceId);
        review.setSubmission(submissionId);
        review.setScorecard(scorecardId);
        review.setCommitted(committed);
        review.setScore(new Float(score));

        return review;
    }

    /**
     * Returns a connection instance.
     *
     * @return a connection instance.
     *
     * @throws Exception not for this test case.
     */
    protected Connection getConnection() throws Exception {
        if (connection == null) {
            connection = dbFactory.createConnection();
        }

        return connection;
    }

    /**
     * Closes the connection.
     */
    protected void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException ex) {
                // do nothing.
            }
        }

        connection = null;
    }

    /**
     * helper method to close a statement.
     *
     * @param stmt statement to close.
     */
    protected void closeStatement(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException ex) {
                // do nothing
            }
        }
    }

    /**
     * inserts a project into the database. Inserts records into the project, comp_catalog
     * and comp_versions tables.
     *
     * @param conn connection to use.
     *
     * @throws Exception not under test.
     */
    protected void insertProject(Connection conn) throws Exception {
        PreparedStatement preparedStmt = null;

        try {
            // insert a project
            String insertProject = "insert into project(project_id, project_status_id, project_category_id,"
                + "create_user, create_date, modify_user, modify_date) values "
                + "(1, 1, 1, 'user', ?, 'user', ?)";
            preparedStmt = conn.prepareStatement(insertProject);
            preparedStmt.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
            preparedStmt.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            preparedStmt.executeUpdate();
            closeStatement(preparedStmt);
            preparedStmt = null;

            // insert into comp_catalog
            String insertCatalog = "insert into comp_catalog(component_id, current_version, component_name,"
                + "description, create_time, status_id) values "
                + "(1, 1, 'Online Review Phases', 'Online Review Phases', ?, 1)";
            preparedStmt = conn.prepareStatement(insertCatalog);
            preparedStmt.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
            preparedStmt.executeUpdate();
            closeStatement(preparedStmt);
            preparedStmt = null;

            // insert into comp_catalog
            String insertVersion = "insert into comp_versions(comp_vers_id, component_id, version,version_text,"
                + "create_time, phase_id, phase_time, price, comments) values "
                + "(1, 1, 1, '1.0', ?, 112, ?, 500, 'Comments')";
            preparedStmt = conn.prepareStatement(insertVersion);
            preparedStmt.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
            preparedStmt.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            preparedStmt.executeUpdate();
            closeStatement(preparedStmt);
            preparedStmt = null;
        } finally {
            closeStatement(preparedStmt);
        }
    }

    /**
     * inserts project properties into the database. Inserts records into the project_info
     * table.
     *
     * @param conn connection to use.
     * @param projectId project id.
     * @param infoTypes array of project info type ids.
     * @param infoValues array of corresponding project info values.
     *
     * @throws Exception not under test.
     */
    protected void insertProjectInfo(Connection conn, long projectId, long[] infoTypes,
        String[] infoValues) throws Exception {
        PreparedStatement preparedStmt = null;

        try {
            // insert a project info
            String insertProjectInfo = "insert into project_info(project_id, project_info_type_id, value,"
                + "create_user, create_date, modify_user, modify_date) values "
                + "(?, ?, ?, 'user', ?, 'user', ?)";
            preparedStmt = conn.prepareStatement(insertProjectInfo);

            for (int i = 0; i < infoTypes.length; i++) {
                preparedStmt.setLong(1, projectId);
                preparedStmt.setLong(2, infoTypes[i]);
                preparedStmt.setString(3, infoValues[i]);
                preparedStmt.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
                preparedStmt.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
                preparedStmt.executeUpdate();
            }

            closeStatement(preparedStmt);
            preparedStmt = null;
        } finally {
            closeStatement(preparedStmt);
        }
    }

    /**
     * Creates specification submission for the project at review phase.
     *
     * @param con connection to use.
     *
     * @throws Exception to JUnit.
     */
    protected void setupSubmissionForReview(Connection con)
        throws Exception {
        // Prepare submitter and insert into DB
        Resource submitter = new Resource();
        submitter.setId(1);
        submitter.setResourceRole(new ResourceRole(17));
        submitter.setProject(new Long(1));
        submitter.setPhase(new Long(101));

        insertResources(con, new Resource[] {submitter});

        // Prepare upload and insert into DB
        Upload upload = new Upload();
        upload.setId(1);
        upload.setProject(1);
        upload.setOwner(1);
        upload.setUploadType(new UploadType(1));
        upload.setUploadStatus(new UploadStatus(1));
        upload.setParameter("param");

        insertUploads(con, new Upload[] {upload});

        // Prepare submission and insert into DB
        Submission submission = new Submission();
        submission.setId(1);
        submission.setUpload(upload);
        submission.setSubmissionStatus(new SubmissionStatus(1));
        submission.setSubmissionType(new SubmissionType(2));

        insertSubmissions(con, new Submission[] {submission});
    }

    /**
     * inserts resources required by test cases into the db.
     *
     * @param conn connection to use.
     * @param resources resources to insert.
     *
     * @throws Exception not under test.
     */
    protected void insertResources(Connection conn, Resource[] resources)
        throws Exception {
        PreparedStatement preparedStmt = null;

        try {
            String insertResource = "INSERT INTO resource "
                + "(resource_id, resource_role_id, project_id, project_phase_id,"
                + "create_user, create_date, modify_user, modify_date) "
                + "VALUES (?, ?, ?, ?, 'user', ?, 'user', ?)";
            preparedStmt = conn.prepareStatement(insertResource);

            Timestamp now = new Timestamp(System.currentTimeMillis());

            for (int i = 0; i < resources.length; i++) {
                preparedStmt.setLong(1, resources[i].getId());
                preparedStmt.setLong(2, resources[i].getResourceRole().getId());
                preparedStmt.setLong(3, resources[i].getProject().longValue());

                if (resources[i].getPhase() != null) {
                    preparedStmt.setLong(4, resources[i].getPhase());
                } else {
                    preparedStmt.setNull(4, java.sql.Types.INTEGER);
                }

                preparedStmt.setTimestamp(5, now);
                preparedStmt.setTimestamp(6, now);
                preparedStmt.executeUpdate();
            }

            closeStatement(preparedStmt);
            preparedStmt = null;
        } finally {
            closeStatement(preparedStmt);
        }
    }

    /**
     * inserts uploads required by test cases into the db.
     *
     * @param conn connection to use.
     * @param uploads uploads to insert.
     *
     * @throws Exception not under test.
     */
    protected void insertUploads(Connection conn, Upload[] uploads)
        throws Exception {
        PreparedStatement preparedStmt = null;

        try {
            String insertUpload = "INSERT INTO upload "
                + "(upload_id, project_id, resource_id, upload_type_id, upload_status_id, parameter,"
                + "create_user, create_date, modify_user, modify_date) "
                + "VALUES (?, ?, ?, ?, ?, ?, 'user', ?, 'user', ?)";
            preparedStmt = conn.prepareStatement(insertUpload);

            Timestamp now = new Timestamp(System.currentTimeMillis());

            for (int i = 0; i < uploads.length; i++) {
                preparedStmt.setLong(1, uploads[i].getId());
                preparedStmt.setLong(2, uploads[i].getProject());
                preparedStmt.setLong(3, uploads[i].getOwner());
                preparedStmt.setLong(4, uploads[i].getUploadType().getId());
                preparedStmt.setLong(5, uploads[i].getUploadStatus().getId());
                preparedStmt.setString(6, uploads[i].getParameter());
                preparedStmt.setTimestamp(7, now);
                preparedStmt.setTimestamp(8, now);
                preparedStmt.executeUpdate();
            }

            closeStatement(preparedStmt);
            preparedStmt = null;
        } finally {
            closeStatement(preparedStmt);
        }
    }

    /**
     * inserts uploads required by test cases into the db.
     *
     * @param conn connection to use.
     * @param submissions submissions to insert.
     *
     * @throws Exception not under test.
     */
    protected void insertSubmissions(Connection conn, Submission[] submissions)
        throws Exception {
        PreparedStatement preparedStmt = null;

        try {
            String insertSubmission = "INSERT INTO submission "
                + "(submission_id, upload_id, submission_status_id, "
                + "submission_type_id, create_user, create_date, modify_user, modify_date, placement) "
                + "VALUES (?, ?, ?, ?, 'user', ?, 'user', ?, ?)";
            preparedStmt = conn.prepareStatement(insertSubmission);

            Timestamp now = new Timestamp(System.currentTimeMillis());

            for (int i = 0; i < submissions.length; i++) {
                preparedStmt.setLong(1, submissions[i].getId());
                preparedStmt.setLong(2, submissions[i].getUpload().getId());
                preparedStmt.setLong(3, submissions[i].getSubmissionStatus().getId());
                preparedStmt.setLong(4, submissions[i].getSubmissionType().getId());
                preparedStmt.setTimestamp(5, now);
                preparedStmt.setTimestamp(6, now);

                preparedStmt.setLong(7,
                    (submissions[i].getPlacement() == null) ? new Long(0)
                                                            : submissions[i].getPlacement());
                preparedStmt.executeUpdate();
            }

            closeStatement(preparedStmt);
            preparedStmt = null;
        } finally {
            closeStatement(preparedStmt);
        }
    }

    /**
     * inserts scorecards required by test cases into the db.
     *
     * @param conn connection to use.
     * @param scorecards scorecards to insert.
     *
     * @throws Exception not under test.
     */
    protected void insertScorecards(Connection conn, Scorecard[] scorecards)
        throws Exception {
        PreparedStatement preparedStmt = null;

        try {
            String insertScorecard = "INSERT INTO scorecard "
                + "(scorecard_id, scorecard_status_id, scorecard_type_id, project_category_id,"
                + "name, version, min_score, max_score,"
                + "create_user, create_date, modify_user, modify_date) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, 'user', ?, 'user', ?)";

            preparedStmt = conn.prepareStatement(insertScorecard);

            Timestamp now = new Timestamp(System.currentTimeMillis());

            for (int i = 0; i < scorecards.length; i++) {
                preparedStmt.setLong(1, scorecards[i].getId());
                preparedStmt.setLong(2, scorecards[i].getScorecardStatus().getId());
                preparedStmt.setLong(3, scorecards[i].getScorecardType().getId());
                preparedStmt.setLong(4, scorecards[i].getCategory());
                preparedStmt.setString(5, scorecards[i].getName());
                preparedStmt.setString(6, scorecards[i].getVersion());
                preparedStmt.setFloat(7, scorecards[i].getMinScore());
                preparedStmt.setFloat(8, scorecards[i].getMaxScore());
                preparedStmt.setTimestamp(9, now);
                preparedStmt.setTimestamp(10, now);
                preparedStmt.executeUpdate();
            }

            closeStatement(preparedStmt);
            preparedStmt = null;
        } finally {
            closeStatement(preparedStmt);
        }
    }

    /**
     * inserts reviews required by test cases into the db.
     *
     * @param conn connection to use.
     * @param reviews reviews to insert.
     *
     * @throws Exception not under test.
     */
    protected void insertReviews(Connection conn, Review[] reviews)
        throws Exception {
        PreparedStatement preparedStmt = null;

        try {
            String insertReview = "INSERT INTO review"
                + "(review_id, resource_id, submission_id, scorecard_id, committed, score,"
                + "create_user, create_date, modify_user, modify_date) "
                + "VALUES (?, ?, ?, ?, ?, ?, 'user', ?, 'user', ?)";
            preparedStmt = conn.prepareStatement(insertReview);

            Timestamp now = new Timestamp(System.currentTimeMillis());

            for (int i = 0; i < reviews.length; i++) {
                preparedStmt.setLong(1, reviews[i].getId());
                preparedStmt.setLong(2, reviews[i].getAuthor());
                preparedStmt.setLong(3, reviews[i].getSubmission());
                preparedStmt.setLong(4, reviews[i].getScorecard());
                preparedStmt.setBoolean(5, reviews[i].isCommitted());
                preparedStmt.setFloat(6, reviews[i].getScore().floatValue());
                preparedStmt.setTimestamp(7, now);
                preparedStmt.setTimestamp(8, now);
                preparedStmt.executeUpdate();
            }

            closeStatement(preparedStmt);
            preparedStmt = null;
        } finally {
            closeStatement(preparedStmt);
        }
    }

    /**
     * A helper method to insert a winning submitter for the given project id with given
     * resource id.
     *
     * @param conn connection to use.
     * @param resourceId resource id.
     * @param resourceInfoTypeId resource info type id.
     * @param resourceInfo resource info value.
     *
     * @throws Exception not under test.
     */
    protected void insertResourceInfo(Connection conn, long resourceId, long resourceInfoTypeId,
        String resourceInfo) throws Exception {
        PreparedStatement preparedStmt = null;

        try {
            String insertInfo = "insert into resource_info"
                + "(resource_id, resource_info_type_id, value,"
                + "create_user, create_date, modify_user, modify_date) "
                + "VALUES (?, ?, ?, 'user', ?, 'user', ?)";
            preparedStmt = conn.prepareStatement(insertInfo);

            Timestamp now = new Timestamp(System.currentTimeMillis());
            preparedStmt.setLong(1, resourceId);
            preparedStmt.setLong(2, resourceInfoTypeId);
            preparedStmt.setString(3, resourceInfo);
            preparedStmt.setTimestamp(4, now);
            preparedStmt.setTimestamp(5, now);
            preparedStmt.executeUpdate();

            closeStatement(preparedStmt);
            preparedStmt = null;
        } finally {
            closeStatement(preparedStmt);
        }
    }

    /**
     * Cleans up records in the given table names.
     *
     * @throws Exception not under test.
     */
    protected void cleanTables() throws Exception {
        Connection conn = null;
        Statement stmt = null;

        try {
            conn = getConnection();
            stmt = conn.createStatement();

            for (int i = 0; i < ALL_TABLE_NAMES.length; i++) {
                String sql = "delete from " + ALL_TABLE_NAMES[i];
                stmt.addBatch(sql);
            }

            stmt.executeBatch();
        } finally {
            closeStatement(stmt);
            closeConnection();
        }
    }

    /**
     * Inserts a project and the standard phases into the database.
     *
     * @param conn the database connection.
     *
     * @return project instance with phases populated.
     *
     * @throws Exception to JUnit.
     */
    protected Project setupPhasesForSpec(Connection conn)
        throws Exception {
        PreparedStatement preparedStmt = null;
        Project project = null;

        try {
            project = new Project(new Date(), new DefaultWorkdays());
            project.setId(1);

            // insert project first
            insertProject(conn);

            String insertPhase = "insert into project_phase(project_phase_id, project_id,"
                + " phase_type_id, phase_status_id,"
                + "scheduled_start_time, scheduled_end_time, duration,"
                + " create_user, create_date, modify_user, modify_date)"
                + "values (?, 1, ?, 1, ?, ?, ?, 'user', ?, 'user', ?)";

            preparedStmt = conn.prepareStatement(insertPhase);

            // insert all standard phases
            long[] phaseIds = new long[] {101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113};
            long[] phaseTypeIds = new long[] {13, 14, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
            String[] phaseTypeNames = new String[] {"Specification Submission", "Specification Review",
                "Registration", "Submission", "Screening", "Review", "Appeals", "Appeals Response",
                "Aggregation", "Aggregation Review", "Final Fix", "Final Review", "Approval"};

            long now = System.currentTimeMillis();
            Timestamp scheduledStart = new Timestamp(now - (DAY * 2));
            Timestamp scheduledEnd = new Timestamp(scheduledStart.getTime() + DAY);

            for (int i = 0; i < phaseIds.length; i++) {
                // insert into db
                preparedStmt.setLong(1, phaseIds[i]);
                preparedStmt.setLong(2, phaseTypeIds[i]);
                preparedStmt.setTimestamp(3, scheduledStart);
                preparedStmt.setTimestamp(4, scheduledEnd);
                preparedStmt.setLong(5, DAY);
                preparedStmt.setTimestamp(6, new Timestamp(now));
                preparedStmt.setTimestamp(7, new Timestamp(now));
                preparedStmt.executeUpdate();

                // create phase instance
                Phase phase = new Phase(project, DAY);
                phase.setId(phaseIds[i]);
                phase.setPhaseType(new PhaseType(phaseTypeIds[i], phaseTypeNames[i]));
                phase.setPhaseStatus(PhaseStatus.SCHEDULED);
                phase.setActualStartDate(scheduledStart);
                phase.setActualEndDate(scheduledEnd);
                phase.setScheduledStartDate(scheduledStart);
                phase.setScheduledEndDate(scheduledEnd);

                project.addPhase(phase);

                // re-calculate scheduled start and end.
                scheduledStart = new Timestamp(scheduledEnd.getTime());
                scheduledEnd = new Timestamp(scheduledStart.getTime() + DAY);
            }

            closeStatement(preparedStmt);
            preparedStmt = null;

            // insert dependencies
            String insertDependency = "INSERT INTO phase_dependency "
                + "(dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, lag_time,"
                + " create_user, create_date, modify_user, modify_date)"
                + "VALUES (?, ?, ?, ?, ?, 'user', ?, 'user', ?)";
            preparedStmt = conn.prepareStatement(insertDependency);

            long[] dependencyPhaseIds = new long[] {101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112};
            long[] dependentPhaseIds = new long[] {102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113};
            Phase[] phases = project.getAllPhases();

            for (int i = 0; i < (phaseIds.length - 1); i++) {
                preparedStmt.setLong(1, dependencyPhaseIds[i]);
                preparedStmt.setLong(2, dependentPhaseIds[i]);
                preparedStmt.setBoolean(3, false);
                preparedStmt.setBoolean(4, true);
                preparedStmt.setLong(5, 0);
                preparedStmt.setTimestamp(6, new Timestamp(now));
                preparedStmt.setTimestamp(7, new Timestamp(now));
                preparedStmt.executeUpdate();

                Dependency dependency = new Dependency(phases[i], phases[i + 1], false, true, 0);
                phases[i + 1].addDependency(dependency);
            }
        } finally {
            closeStatement(preparedStmt);
        }

        return project;
    }

    /**
     * Clear all uploaded files created by test case.
     */
    protected static void clearFiles() {
        File file = new File(UPLOAD_DIRECTORY);
        File[] files = file.listFiles();

        for (File delFile : files) {
            delFile.delete();
        }
    }
}
