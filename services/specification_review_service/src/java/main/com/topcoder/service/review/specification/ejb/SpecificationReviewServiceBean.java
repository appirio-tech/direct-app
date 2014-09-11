/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.review.specification.ejb;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Properties;
import java.sql.PreparedStatement;


import javax.naming.Context;
import javax.naming.InitialContext;

import javax.ejb.CreateException;
import javax.ejb.EJB;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.cronos.onlinereview.phases.lookup.ResourceRoleLookupUtility;
import com.cronos.onlinereview.phases.lookup.SubmissionStatusLookupUtility;
import com.cronos.onlinereview.phases.lookup.SubmissionTypeLookupUtility;
import com.cronos.onlinereview.services.uploads.UploadExternalServices;
import com.cronos.onlinereview.services.uploads.UploadServicesException;
import com.topcoder.db.connectionfactory.DBConnectionException;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.management.deliverable.PersistenceUploadManager;
import com.topcoder.management.deliverable.Submission;
import com.topcoder.management.deliverable.UploadManager;
import com.topcoder.management.deliverable.persistence.UploadPersistence;
import com.topcoder.management.deliverable.persistence.UploadPersistenceException;
import com.topcoder.management.deliverable.persistence.sql.SqlUploadPersistence;
import com.topcoder.management.deliverable.search.SubmissionFilterBuilder;
import com.topcoder.management.resource.ResourceManager;
import com.topcoder.management.resource.ResourceRole;
import com.topcoder.management.resource.persistence.PersistenceResourceManager;
import com.topcoder.management.resource.persistence.ResourcePersistence;
import com.topcoder.management.resource.persistence.ResourcePersistenceException;
import com.topcoder.management.resource.persistence.sql.SqlResourcePersistence;
import com.topcoder.management.resource.search.ResourceFilterBuilder;
import com.topcoder.management.review.ReviewManagementException;
import com.topcoder.management.review.ReviewManager;
import com.topcoder.management.review.data.Review;
import com.topcoder.management.scorecard.PersistenceException;
import com.topcoder.management.scorecard.ScorecardManager;
import com.topcoder.management.scorecard.data.Scorecard;
import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.PhaseStatus;
import com.topcoder.project.phases.PhaseType;
import com.topcoder.project.service.FullProjectData;
import com.topcoder.project.service.ProjectServices;
import com.topcoder.project.service.ProjectServicesException;
import com.topcoder.search.builder.SearchBuilderConfigurationException;
import com.topcoder.search.builder.SearchBuilderException;
import com.topcoder.search.builder.SearchBundleManager;
import com.topcoder.search.builder.filter.AndFilter;
import com.topcoder.search.builder.filter.OrFilter;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.security.TCSubject;
import com.topcoder.service.user.UserService;
import com.topcoder.service.review.specification.SpecificationReview;
import com.topcoder.service.review.specification.SpecificationReviewServiceConfigurationException;
import com.topcoder.service.review.specification.SpecificationReviewServiceException;
import com.topcoder.service.review.specification.SpecificationReviewStatus;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.ConfigManagerException;
import com.topcoder.util.config.Property;
import com.topcoder.util.idgenerator.IDGenerationException;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.web.ejb.forums.Forums;
import com.topcoder.web.ejb.forums.ForumsHome;


/**
 * <p>
 * This class is an EJB that implements <code>SpecificationReviewService</code> business
 * interface. This bean uses Logging Wrapper component to log all occurred errors. This
 * class uses pluggable implementations of <code>UploadManager</code>,
 * <code>UploadExternalServices</code>, <code>ProjectServices</code>,
 * <code>ReviewManager</code>, <code>ScorecardManager</code> and
 * <code>ResourceManager</code> interfaces to access data in persistence and perform the
 * actual specification review management.
 * </p>
 * <p>
 * Sample config in ejb-jar.xml:
 *
 * <pre>
 *  &lt;session&gt;
 *  &lt;ejb-name&gt;SpecificationReviewServiceBean&lt;/ejb-name&gt;
 *  &lt;remote&gt;com.topcoder.service.review.specification.ejb.SpecificationReviewServiceRemote&lt;/remote&gt;
 *  &lt;local&gt;com.topcoder.service.review.specification.ejb.SpecificationReviewServiceLocal&lt;/local&gt;
 *  &lt;ejb-class&gt;com.topcoder.service.review.specification.ejb.SpecificationReviewServiceBean&lt;/ejb-class&gt;
 *  &lt;session-type&gt;Stateless&lt;/session-type&gt;
 *  &lt;transaction-type&gt;Container&lt;/transaction-type&gt;
 *  &lt;env-entry&gt;
 *  &lt;env-entry-name&gt;loggerName&lt;/env-entry-name&gt;
 *  &lt;env-entry-type&gt;java.lang.String&lt;/env-entry-type&gt;
 *  &lt;env-entry-value&gt;specification_review_service_log&lt;/env-entry-value&gt;
 *  &lt;/env-entry&gt;
 *  &lt;env-entry&gt;
 *  &lt;env-entry-name&gt;mockSubmissionFilePath&lt;/env-entry-name&gt;
 *  &lt;env-entry-type&gt;java.lang.String&lt;/env-entry-type&gt;
 *  &lt;env-entry-value&gt;./mock&lt;/env-entry-value&gt;
 *  &lt;/env-entry&gt;
 *  &lt;env-entry&gt;
 *  &lt;env-entry-name&gt;mockSubmissionFileName&lt;/env-entry-name&gt;
 *  &lt;env-entry-type&gt;java.lang.String&lt;/env-entry-type&gt;
 *  &lt;env-entry-value&gt;mock_rs.rtf&lt;/env-entry-value&gt;
 *  &lt;/env-entry&gt;
 *  &lt;env-entry&gt;
 *  &lt;env-entry-name&gt;searchBundleManageNamespace&lt;/env-entry-name&gt;
 *  &lt;env-entry-type&gt;java.lang.String&lt;/env-entry-type&gt;
 *  &lt;env-entry-value&gt;
 *  com.topcoder.search.builder.Upload_Resource_Search
 *  &lt;/env-entry-value&gt;
 *  &lt;/env-entry&gt;
 *  &lt;env-entry&gt;
 *  &lt;env-entry-name&gt;reviewManagerClassName&lt;/env-entry-name&gt;
 *  &lt;env-entry-type&gt;java.lang.String&lt;/env-entry-type&gt;
 *  &lt;env-entry-value&gt;
 *  com.topcoder.management.review.DefaultReviewManager
 *  &lt;/env-entry-value&gt;
 *  &lt;/env-entry&gt;
 *  &lt;env-entry&gt;
 *  &lt;env-entry-name&gt;scorecardManagerClassName&lt;/env-entry-name&gt;
 *  &lt;env-entry-type&gt;java.lang.String&lt;/env-entry-type&gt;
 *  &lt;env-entry-value&gt;
 *  com.topcoder.management.scorecard.ScorecardManagerImpl
 *  &lt;/env-entry-value&gt;
 *  &lt;/env-entry&gt;
 *  &lt;env-entry&gt;
 *  &lt;env-entry-name&gt;uploadExternalServicesClassName&lt;/env-entry-name&gt;
 *  &lt;env-entry-type&gt;java.lang.String&lt;/env-entry-type&gt;
 *  &lt;env-entry-value&gt;
 *  com.cronos.onlinereview.services.uploads.impl.DefaultUploadExternalServices
 *  &lt;/env-entry-value&gt;
 *  &lt;/env-entry&gt;
 *  &lt;env-entry&gt;
 *  &lt;env-entry-name&gt;dbConnectionFactoryClassName&lt;/env-entry-name&gt;
 *  &lt;env-entry-type&gt;java.lang.String&lt;/env-entry-type&gt;
 *  &lt;env-entry-value&gt;com.topcoder.db.connectionfactory.DBConnectionFactoryImpl&lt;/env-entry-value&gt;
 *  &lt;/env-entry&gt;
 *  &lt;env-entry&gt;
 *  &lt;env-entry-name&gt;dbConnectionFactoryNamespace&lt;/env-entry-name&gt;
 *  &lt;env-entry-type&gt;java.lang.String&lt;/env-entry-type&gt;
 *  &lt;env-entry-value&gt;com.topcoder.db.connectionfactory.DBConnectionFactoryImpl&lt;/env-entry-value&gt;
 *  &lt;/env-entry&gt;
 *  &lt;ejb-local-ref&gt;
 *  &lt;ejb-ref-name&gt;ejb/ProjectServices&lt;/ejb-ref-name&gt;
 *  &lt;ejb-ref-type&gt;Session&lt;/ejb-ref-type&gt;
 *  &lt;local&gt;com.topcoder.project.service.ejb.ProjectServicesLocal&lt;/local&gt;
 *  &lt;ejb-link&gt;ProjectServicesBean&lt;/ejb-link&gt;
 *  &lt;/ejb-local-ref&gt;
 *  &lt;/session&gt;
 * </pre>
 *
 * </p>
 * <p>
 * Sample usage:
 *
 * <pre>
 * // Get specification review service
 * Context context = new InitialContext();
 * SpecificationReviewService specificationReviewService = (SpecificationReviewServiceRemote) context
 *     .lookup(EAR_NAME + &quot;/SpecificationReviewServiceBean/remote&quot;);
 *
 * TCSubject tcSubject = new TCSubject(null, 1001L);
 * // Schedule specification review for the project with ID=1 to start immediately
 * long projectId = 1;
 * Date reviewStartDate = new Date();
 * specificationReviewService.scheduleSpecificationReview(tcSubject, projectId, reviewStartDate);
 * // Retrieve the open specification review positions
 * List&lt;Long&gt; projectIds = specificationReviewService.getOpenSpecificationReviewPositions(tcSubject);
 * // projectIds must contain 1
 * // Assume that specification reviewer registered for this project
 *
 * // Retrieve the open specification review positions again
 * projectIds = specificationReviewService.getOpenSpecificationReviewPositions(tcSubject);
 * // projectIds must not contain 1
 * // Assume that specification was rejected here
 *
 * // Re-submit specification as file for this project
 * String filename = &quot;sample_component_1.0_requirements_specification.rtf&quot;;
 * long submissionId = specificationReviewService.submitSpecificationAsFile(tcSubject, projectId, filename);
 * // Retrieve the specification review status
 * SpecificationReviewStatus specificationReviewStatus = specificationReviewService
 *     .getSpecificationReviewStatus(tcSubject, projectId);
 * // specificationReviewStatus must be SpecificationReviewStatus.PENDING_REVIEW
 * // Assume that specification was rejected here again
 *
 * // Retrieve the specification review status
 * specificationReviewStatus = specificationReviewService
 *     .getSpecificationReviewStatus(tcSubject, projectId);
 * // specificationReviewStatus must be SpecificationReviewStatus.WAITING_FOR_FIXES
 * // Re-submit specification as content string
 * String content = &quot;This is a text for sample specification&quot;;
 * submissionId = specificationReviewService.submitSpecificationAsString(tcSubject, projectId, content);
 * // Assume that specification was accepted here
 *
 * // Retrieve the specification review for this project
 * SpecificationReview specificationReview = specificationReviewService.getSpecificationReview(tcSubject,
 *     projectId);
 * // specificationReview.getReview() should not be not
 * // specificationReview.getScorecard() should not be null
 * </pre>
 *
 * </p>
 * <p>
 * Thread Safety: This class is mutable and not thread safe. But it is always used in
 * thread safe manner in EJB container because its state doesn't change after
 * initialization. Instances of <code>ProjectServices</code>,
 * <code>UploadExternalServices</code> and <code>DBConnectionFactory</code> used by
 * this class are thread safe. Instances of <code>UploadManager</code>,
 * <code>ReviewManager</code>, <code>ScorecardManager</code> and
 * <code>ResourceManager</code> are not thread safe, thus all calls of their methods are
 * synchronized in this class. This bean assumes that transactions are managed by the
 * container.
 * </p>
 *
 * @author saarixx, myxgyy
 * @version 1.0
 */
@Stateless
public class SpecificationReviewServiceBean implements SpecificationReviewServiceLocal,
    SpecificationReviewServiceRemote {

    /**
     * Represents the default namespace for this stateless bean.
     */
    public static final String DEFAULT_NAMESPACE =
        "com.topcoder.service.review.specification.ejb.SpecificationReviewServiceBean";

    /**
     * Represents the &quot;Specification Submission&quot; phase type.
     */
    private static final String SPECIFICATION_SUBMISSION = "Specification Submission";

    /**
     * Represents the &quot;Specification Review&quot; phase type.
     */
    private static final String SPECIFICATION_REVIEW = "Specification Review";

    /**
     * Represents the &quot;Specification Reviewer&quot; resource role.
     */
    private static final String SPECIFICATION_REVIEWER = "Specification Reviewer";

    /**
     * <p>
     * Represents on value for the autopilot option project property
     * </p>
     *
     */
    private static final String AUTOPILOT_OPTION_PROJECT_PROPERTY_VALUE_ON = "On";


     /**
     * <p>
     * Represents the "Autopilot option" project property key
     * </p>
     */
    private static final String AUTOPILOT_OPTION_PROJECT_PROPERTY_KEY = "Autopilot Option";

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("MM.dd.yyyy hh:mm a", Locale.US);


     /**
     * A flag indicating whether or not create the forum. It's injected, used in
     * the createSoftwareContest method. In the old version, this variable
     * misses the document, it's added in the version 1.1
     */
    private boolean createForum = false;

    /**
     * the forum bean provider url in configuration.
     */
    private String forumBeanProviderUrl;

    /**
     * <p>
     * The project services instance used by this class. Is initialized via EJB container
     * injection.
     * </p>
     * <p>
     * Cannot be null after initialization. Is used in
     * {@link #scheduleSpecificationReview(TCSubject, long, Date)},
     * {@link #getSpecificationReviewStatus(TCSubject, long)} and
     * {@link #getOpenSpecificationReviewPositions(TCSubject)}.
     * </p>
     */
    @EJB(name = "ejb/ProjectServicesBean")
    private ProjectServices projectServices;

    /**
     * <p>
     * A <code>UserService</code> providing access to available
     * <code>User Service EJB</code>.
     * </p>
     *
     * @since Cockpit Release Assembly for Receipts
     */
    @EJB(name = "ejb/UserService")
    private UserService userService = null;

    /**
     * <p>
     * The upload manager used by this class.
     * </p>
     * <p>
     * Is initialized in {@link #initialize()}. Cannot be null after initialization. Is
     * used in {@link #getSpecificationReview(TCSubject, long)}.
     * </p>
     * <p>
     * Thread safety: Implementations of UploadManager are not required to be thread safe.
     * Thus to make SpecificationReviewServiceBean thread safe, all calls to
     * uploadManager's methods must be synchronized on "this" instance.
     * </p>
     */
    private UploadManager uploadManager;

    /**
     * <p>
     * The review manager used by this class.
     * </p>
     * <p>
     * Is initialized in {@link #initialize()}. Cannot be null after initialization. Is
     * used in {@link #getSpecificationReview(TCSubject, long)}.
     * </p>
     * <p>
     * Thread safety: Implementations of ReviewManager are not required to be thread safe.
     * Thus to make SpecificationReviewServiceBean thread safe, all calls to
     * reviewManager's methods must be synchronized on "this" instance.
     * </p>
     */
    private ReviewManager reviewManager;

    /**
     * <p>
     * The scorecard manager used by this class.
     * </p>
     * <p>
     * Is initialized in {@link #initialize()}. Cannot be null after initialization. Is
     * used in {@link #getSpecificationReview(TCSubject, long)}.
     * </p>
     * <p>
     * Thread safety: Implementations of ScorecardManager are not required to be thread
     * safe. Thus to make SpecificationReviewServiceBean thread safe, all calls to
     * scorecardManager's methods must be synchronized on "this" instance.
     * </p>
     */
    private ScorecardManager scorecardManager;

    /**
     * <p>
     * The resource manager used by this class.
     * </p>
     * <p>
     * Is initialized in {@link #initialize()}. Cannot be null after initialization. Is
     * used in {@link #getOpenSpecificationReviewPositions(TCSubject)}.
     * </p>
     * <p>
     * Thread safety: Implementations of <code>ResourceManager</code> are not required
     * to be thread safe. Thus to make <code>SpecificationReviewServiceBean</code>
     * thread safe, all calls to resourceManager's methods must be synchronized on "this"
     * instance.
     * </p>
     */
    private ResourceManager resourceManager;

    /**
     * <p>
     * The upload external services used by this class.
     * </p>
     * <p>
     * Is initialized in {@link #initialize()}. Cannot be null after initialization. Is
     * used in {@link #scheduleSpecificationReview(TCSubject, long, Date)} and
     * {@link #submitSpecificationAsFile(TCSubject, long, String)}.
     * </p>
     */
    private UploadExternalServices uploadExternalServices;

    /**
     * <p>
     * The DB connection factory used by this class.
     * </p>
     * <p>
     * Is initialized in {@link #initialize()}. Cannot be null after initialization. Is
     * used in {@link #createConnection()}.
     * </p>
     */
    private DBConnectionFactory dbConnectionFactory;

    /**
     * <p>
     * The logger instance to be used by this class for logging errors.
     * </p>
     * <p>
     * Can be set in {@link #initialize()}. Is null if logging is not required. Is used
     * in all public methods.
     * </p>
     */
    private Log log;

    /**
     * <p>
     * The name of the Logging Wrapper logger to be used by this class for logging errors
     * and debug information.
     * </p>
     * <p>
     * Is null if logging is not required. Can be modified with EJB container injection.
     * Cannot be empty. Is used in {@link #initialize()}.
     * </p>
     */
    @Resource(name = "loggerName")
    private String loggerName;

    /**
     * <p>
     * The connection passed to <code>DBConnectionFactory</code> when establishing a
     * database connection.
     * </p>
     * <p>
     * If null, the default connection is used. Can be modified with EJB container
     * injection. Cannot be empty. Is used in {@link #createConnection()}.
     * </p>
     */
    @Resource(name = "connectionName")
    private String connectionName;

	 /**
     * <p>
     * The text content of the mock specification submission.
     * </p>
     */
    @Resource(name = "mockSoftwareSubmissionContent")
    private String mockSoftwareSubmissionContent;

    /**
     * <p>
     * The text content of the mock specification submission.
     * </p>
     */
    @Resource(name = "mockStudioSubmissionContent")
    private String mockStudioSubmissionContent;

    /**
     * <p>
     * The namespace to create search bundle manager.
     * </p>
     * <p>
     * Is initialized via EJB container injection. Cannot be null or empty after
     * initialization. Is used in {@link #initialize()}.
     * </p>
     */
    @Resource(name = "searchBundleManageNamespace")
    private String searchBundleManageNamespace;

    /**
     * <p>
     * The full class name of review manager to be used by this class.
     * </p>
     * <p>
     * Is initialized via EJB container injection. Cannot be null or empty after
     * initialization. Is used in {@link #initialize()}.
     * </p>
     */
    @Resource(name = "reviewManagerClassName")
    private String reviewManagerClassName;

    /**
     * <p>
     * The namespace of review manager to be used by this class.
     * </p>
     * <p>
     * Is initialized via EJB container injection. If null, the default constructor is
     * used, otherwise this namespace is passed as the only string argument to the
     * constructor. Is used in {@link #initialize()}.
     * </p>
     */
    @Resource(name = "reviewManagerNamespace")
    private String reviewManagerNamespace;

    /**
     * <p>
     * The full class name of scorecard manager to be used by this class.
     * </p>
     * <p>
     * Is initialized via EJB container injection. Cannot be null or empty after
     * initialization. Is used in {@link #initialize()}.
     * </p>
     */
    @Resource(name = "scorecardManagerClassName")
    private String scorecardManagerClassName;

    /**
     * <p>
     * The namespace of scorecard manager to be used by this class.
     * </p>
     * <p>
     * Is initialized via EJB container injection. If null, the default constructor is
     * used, otherwise this namespace is passed as the only string argument to the
     * constructor. Is used in {@link #initialize()}.
     * </p>
     */
    @Resource(name = "scorecardManagerNamespace")
    private String scorecardManagerNamespace;

    /**
     * <p>
     * The full class name of upload external services to be used by this class.
     * </p>
     * <p>
     * Is initialized via EJB container injection. Cannot be null or empty after
     * initialization. Is used in {@link #initialize()}.
     * </p>
     */
    @Resource(name = "uploadExternalServicesClassName")
    private String uploadExternalServicesClassName;

    /**
     * <p>
     * The namespace of upload external services to be used by this class.
     * </p>
     * <p>
     * Is initialized via EJB container injection. If null, the default constructor is
     * used, otherwise this namespace is passed as the only string argument to the
     * constructor. Is used in {@link #initialize()}.
     * </p>
     */
    @Resource(name = "uploadExternalServicesNamespace")
    private String uploadExternalServicesNamespace;

    /**
     * <p>
     * The full class name of DB connection factory to be used by this class.
     * </p>
     * <p>
     * Is initialized via EJB container injection. Cannot be null or empty after
     * initialization. Is used in {@link #initialize()}.
     * </p>
     */
    @Resource(name = "dbConnectionFactoryClassName")
    private String dbConnectionFactoryClassName;

    /**
     * <p>
     * The namespace of DB connection factory to be used by this class.
     * </p>
     * <p>
     * Is initialized via EJB container injection. If null, the default constructor is
     * used, otherwise this namespace is passed as the only string argument to the
     * constructor. Is used in {@link #initialize()}.
     * </p>
     */
    @Resource(name = "dbConnectionFactoryNamespace")
    private String dbConnectionFactoryNamespace;

    /**
     * Represents the mapping from the contest type id to default spec reviewer map.
     */
    private Map<Long, Long> defaultSpecReviewerMap = new HashMap<Long, Long>();

     /**
     * Represents the mapping from the contest type id to rboard phase map.
     */
    private Map<Long, Long> projectCategoryToRBoarPhaseMap = new HashMap<Long, Long>();

    /**
     * Represents the mapping from the contest type id to rboard phase map.
     */
    private Map<Long, Long> projectCategoryToReviewRespMap = new HashMap<Long, Long>();

    /**
     * Creates an instance of <code>SpecificationReviewServiceBean</code>.
     */
    public SpecificationReviewServiceBean() {
    }

    /**
     * Initializes this bean.
     *
     * @throws SpecificationReviewServiceConfigurationException
     *             if error occurred when initializing this bean.
     */
    @PostConstruct
    protected void initialize() {
        checkNull(projectServices, "projectServices");
        checkNullOrEmpty(searchBundleManageNamespace, "searchBundleManageNamespace");
        checkNullOrEmpty(reviewManagerClassName, "reviewManagerClassName");
        checkNullOrEmpty(scorecardManagerClassName, "scorecardManagerClassName");
        checkNullOrEmpty(uploadExternalServicesClassName, "uploadExternalServicesClassName");
        checkNullOrEmpty(dbConnectionFactoryClassName, "dbConnectionFactoryClassName");
        checkEmpty(loggerName, "loggerName");
        checkEmpty(connectionName, "connectionName");

        if (loggerName != null) {
            log = com.topcoder.util.log.LogManager.getLog(loggerName);
        }

        reviewManager = createObject(reviewManagerClassName, reviewManagerNamespace, ReviewManager.class);
        scorecardManager = createObject(scorecardManagerClassName, scorecardManagerNamespace,
            ScorecardManager.class);
        uploadExternalServices = createObject(uploadExternalServicesClassName,
            uploadExternalServicesNamespace, UploadExternalServices.class);
        dbConnectionFactory = createObject(dbConnectionFactoryClassName, dbConnectionFactoryNamespace,
            DBConnectionFactory.class);

        SearchBundleManager searchBundleManager;
        try {
            searchBundleManager = new SearchBundleManager(searchBundleManageNamespace);
        } catch (SearchBuilderConfigurationException e) {
            throw new SpecificationReviewServiceConfigurationException(
                "Fails to create search bundle manager with namespace: " + searchBundleManageNamespace,
                e);
        }

        try {
            UploadPersistence uploadPersistence = new SqlUploadPersistence(dbConnectionFactory,
                this.connectionName);
            this.uploadManager = new PersistenceUploadManager(uploadPersistence, searchBundleManager);
        } catch (IDGenerationException e) {
            throw new SpecificationReviewServiceConfigurationException(
                "Fails to create upload manager implementation due to id generator not found.", e);
        } catch (IllegalArgumentException e) {
            throw new SpecificationReviewServiceConfigurationException(
                "Fails to create upload manager implementation"
                    + " due to required search bundles not found.", e);
        }

        try {
            ResourcePersistence resourcePersistence = new SqlResourcePersistence(dbConnectionFactory,
                connectionName);
            this.resourceManager = new PersistenceResourceManager(resourcePersistence,
                searchBundleManager);
        } catch (IllegalArgumentException e) {
            throw new SpecificationReviewServiceConfigurationException(
                "Fails to create resource manager implementation"
                    + " due to required search bundle or id generator not found.", e);
        }

        ConfigManager configManager = ConfigManager.getInstance();

        try {
            
            
            Property  defaultSpecReviewersProp = configManager.getPropertyObject(DEFAULT_NAMESPACE, "defaultSpecReviewers");
            List defaultSpecReviewers = defaultSpecReviewersProp.list();

            if (defaultSpecReviewers != null && defaultSpecReviewers.size() > 0)
            {
                for (int i = 0; i < defaultSpecReviewers.size(); i++)
                {
                    Property p = (Property)defaultSpecReviewers.get(i);
                    String contestTypeId = (String) p.getName();
                    String defaultSpecReviewerId = (String)p.getValue();
                    defaultSpecReviewerMap.put(Long.parseLong(contestTypeId), Long.parseLong(defaultSpecReviewerId));
                }
            }


            String createForumProp = configManager.getString(DEFAULT_NAMESPACE, "createForum");

            createForum = Boolean.parseBoolean(createForumProp);

            forumBeanProviderUrl = configManager.getString(DEFAULT_NAMESPACE, "forumBeanProviderUrl");

            projectCategoryToRBoarPhaseMap.put((long)14, (long)1125);

            projectCategoryToReviewRespMap.put((long)14, (long)40);

        } catch (NumberFormatException e) {
            throw new SpecificationReviewServiceConfigurationException(
                    "Error in config " + e, e);
        } catch (ConfigManagerException e) {
            throw new SpecificationReviewServiceConfigurationException("Unable to read configuration from file.", e);
        }
    }

    /**
     * <p>
     * Validates the value of a variable. The value can not be <c>null</c>.
     * </p>
     *
     * @param value
     *            the value of the variable to be validated.
     * @param name
     *            the name of the variable to be validated.
     * @throws SpecificationReviewServiceConfigurationException
     *             if the value of the variable is <c>null</c>.
     */
    private void checkNull(Object value, String name) {
        if (value == null) {
            throw new SpecificationReviewServiceConfigurationException("The " + name
                + " should not be null.");
        }
        
    }

    /**
     * <p>
     * Validates the value of a string. The value can not be <code>null</code> or an
     * empty string.
     * </p>
     *
     * @param value
     *            the value of the variable to be validated.
     * @param name
     *            the name of the variable to be validated.
     * @throws SpecificationReviewServiceConfigurationException
     *             if the given string is <code>null</code> or an empty string.
     */
    private void checkNullOrEmpty(String value, String name) {
        checkNull(value, name);

        if (value.trim().length() == 0) {
            throw new SpecificationReviewServiceConfigurationException("The " + name
                + " should not be empty.");
        }
    }

    /**
     * Checks whether the given value is positive or not. Note the exception will be
     * logged.
     *
     * @param value
     *            the value to check.
     * @param name
     *            the name of the value.
     * @throws IllegalArgumentException
     *             if the value is not positive.
     */
    private void assertPositive(long value, String name) {
        if (value <= 0) {
            throw logException(new IllegalArgumentException("The parameter '" + name
                + "' should be positive."));
        }
    }

    /**
     * Checks whether the given value is null or not. Note the exception will be logged.
     *
     * @param value
     *            the value to check.
     * @param name
     *            the name of the value.
     * @throws IllegalArgumentException
     *             if the value is null.
     */
    private void assertNotNull(Object value, String name) {
        if (value == null) {
            throw logException(new IllegalArgumentException("The parameter '" + name
                + "' should not be null."));
        }
    }

    /**
     * Checks whether the given value is null or empty. Note the exception will be logged.
     *
     * @param value
     *            the value to check.
     * @param name
     *            the name of the value.
     * @throws IllegalArgumentException
     *             if the value is null or empty.
     */
    private void assertNotNullNotEmpty(String value, String name) {
        assertNotNull(value, name);
        if (value.trim().length() == 0) {
            throw logException(new IllegalArgumentException("The parameter '" + name
                + "' should not be empty."));
        }
    }

    /**
     * <p>
     * Validates the value of a string when it is not null. The value can not be an empty
     * string.
     * </p>
     *
     * @param value
     *            the value of the variable to be validated.
     * @param name
     *            the name of the variable to be validated.
     * @throws SpecificationReviewServiceConfigurationException
     *             if the given string is an empty string.
     */
    private void checkEmpty(String value, String name) {
        if ((value != null) && (value.trim().length() == 0)) {
            throw new SpecificationReviewServiceConfigurationException("The " + name
                + " should not be an empty string.");
        }
    }

    /**
     * Helper method to instantiate the specified <code>className</code> using
     * reflection. The parameter is passed to constructor during reflection.
     *
     * @param <T>
     *            the generic class type.
     * @param className
     *            name of class to be instantiated.
     * @param expectedType
     *            expected type of the return instance.
     * @param parameter
     *            constructor argument.
     * @return instance of type <code>className</code>.
     * @throws SpecificationReviewServiceConfigurationException
     *             if any error occurred when creating instance.
     */
    private <T> T createObject(String className, String parameter, Class<T> expectedType) {
        try {
            Class<?> clazz = Class.forName(className);

            Object ret;

            if (parameter == null) {
                ret = clazz.newInstance();
            } else {
                ret = clazz.getConstructor(String.class).newInstance(parameter);
            }
            return expectedType.cast(ret);
        } catch (ClassNotFoundException e) {
            throw new SpecificationReviewServiceConfigurationException("Could not find class:"
                + className, e);
        } catch (IllegalArgumentException e) {
            throw new SpecificationReviewServiceConfigurationException(
                "Illegal argument for constructor", e);
        } catch (SecurityException e) {
            throw new SpecificationReviewServiceConfigurationException(
                "The object could not be instantiated.", e);
        } catch (InstantiationException e) {
            throw new SpecificationReviewServiceConfigurationException("The class[" + className
                + "] can not be instantiated.", e);
        } catch (IllegalAccessException e) {
            throw new SpecificationReviewServiceConfigurationException(
                "Can not access the constructor of this class[" + className
                    + "].", e);
        } catch (InvocationTargetException e) {
            throw new SpecificationReviewServiceConfigurationException(
                "Underlying constructor throws an exception", e);
        } catch (NoSuchMethodException e) {
            throw new SpecificationReviewServiceConfigurationException(
                "No such method found for class:" + className, e);
        } catch (ClassCastException e) {
            throw new SpecificationReviewServiceConfigurationException(className + " must be of type "
                + expectedType.getName(), e);
        }
    }

    /**
     * Schedules a specification review for the given project on the given date.
     *
     * @param tcSubject
     *            the user making the request.
     * @param projectId
     *            the ID of the project.
     * @param reviewStartDate
     *            the date the review is to begin (immediately if null or in the past).
     * @throws IllegalArgumentException
     *             if tcSubject is null or projectId is not positive.
     * @throws SpecificationReviewServiceException
     *             if there are any errors during this operation.
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void scheduleSpecificationReview(TCSubject tcSubject, long projectId, boolean startnow)
        throws SpecificationReviewServiceException {
        assertNotNull(tcSubject, "tcSubject");
        assertPositive(projectId, "projectId");
        try
        {
            
      
            FullProjectData fullProjectData = getFullProjectData(projectId);
            Phase[] allPhases = fullProjectData.getAllPhases();

            
            // find specification submission phase
            Phase specificationReviewPhase = null;
            Phase specificationSubmissionPhase = null;
            for (Phase phase : allPhases) {
                PhaseType phaseType = phase.getPhaseType();
                if (SPECIFICATION_SUBMISSION.equals(phaseType.getName())) {
                    specificationSubmissionPhase = phase;
                    break;
                }
            }
            for (Phase phase : allPhases) {
                PhaseType phaseType = phase.getPhaseType();
                if (SPECIFICATION_REVIEW.equals(phaseType.getName())) {
                    specificationReviewPhase = phase;
                    break;
                }
            }

            if (specificationReviewPhase == null || specificationSubmissionPhase == null) {
                throw logException(new SpecificationReviewServiceException(
                    "Specification submission/reivew phase does not exist for the project: " + projectId));
            }

            // immediately if null or in the past
            Date now = new Date();
            if (startnow) {
                fullProjectData.setStartDate(now);
                for (Phase phase : allPhases) {
                    PhaseType phaseType = phase.getPhaseType();
                    if (SPECIFICATION_SUBMISSION.equals(phaseType.getName())) {
                        phase.setScheduledEndDate(null);
                        phase.setScheduledStartDate(fullProjectData.getStartDate());
                        phase.setFixedStartDate(fullProjectData.getStartDate());
                        phase.setScheduledEndDate(new Date(fullProjectData.getStartDate().getTime() + phase.getLength()));
                        break;
                    }
                }
            } 

            // add user as specification submitter, if already exists, added will be false
            boolean added = addSpecificationSubmitter(projectId, tcSubject.getUserId());
            String operator = Long.toString(tcSubject.getUserId());

            //if added, upload mock, 
            if (added)
            {
                // set to open
                specificationSubmissionPhase.setPhaseStatus(PhaseStatus.OPEN);
                updatePhases(fullProjectData, operator);

                long projectTypeId = fullProjectData.getProjectHeader().getProjectCategory().getProjectType().getId();

                // upload a mock submission
                if (projectTypeId == 3) {
                    submitSpecificationAsString(tcSubject, projectId, mockStudioSubmissionContent.replace("[pj]", new Long(projectId).toString()));
                } else {
                    submitSpecificationAsString(tcSubject, projectId, mockSoftwareSubmissionContent.replace("[pj]", new Long(projectId).toString()));
                }



                // set to scheduled and update
                specificationSubmissionPhase.setPhaseStatus(PhaseStatus.SCHEDULED);
                updatePhases(fullProjectData, operator);
            }
            // resource already exists, we assume submission is already done, so no sub here
            else
            {
                updatePhases(fullProjectData, operator);
            }

            // get the project category id
            long projectCategoryId = fullProjectData.getProjectHeader().getProjectCategory().getId();

            if (defaultSpecReviewerMap.containsKey(projectCategoryId)) {
                long specReviewerId = defaultSpecReviewerMap.get(projectCategoryId);

                // assign default sepc reviewer by contest type.
                addDefaultSpecificationReviewer(projectId, specReviewerId, specificationReviewPhase.getId(), 
                                                projectCategoryToRBoarPhaseMap.get(projectCategoryId), projectCategoryToReviewRespMap.get(projectCategoryId),
                                                tcSubject);
            }

            //turn on AP
            fullProjectData.getProjectHeader().setProperty(AUTOPILOT_OPTION_PROJECT_PROPERTY_KEY, AUTOPILOT_OPTION_PROJECT_PROPERTY_VALUE_ON);
            updateProject(fullProjectData.getProjectHeader(), "Turn on AP", operator);
        } catch (Exception e) {
            throw logException(new SpecificationReviewServiceException("Error in scheduleSpecificationReview" + e, e));
        }
    }

    /**
     * Gets project with all associated information.
     *
     * @param projectId
     *            the project id.
     * @return the project data with all associated information.
     * @throws SpecificationReviewServiceException
     *             if any error occurs when retrieving project data.
     */
    private FullProjectData getFullProjectData(long projectId)
        throws SpecificationReviewServiceException {
        try {
            FullProjectData project = projectServices.getFullProjectData(projectId);
            project.setId(projectId);
            return project;
        } catch (Exception e) {
            throw logException(new SpecificationReviewServiceException(
                "Fails to retrieve the project with id[" + projectId
                    + "] along with all known associated information.", e));
        }
    }

    /**
     * Uploads a specification in the form of a file, for the given project by the given
     * user.
     *
     * @param tcSubject
     *            the user making the request.
     * @param filename
     *            the name of the file with the submission to upload.
     * @param projectId
     *            the ID of the project.
     * @param dataHandler the data handler of the input file.
     * @return the submission ID.
     * @throws SpecificationReviewServiceException
     *             if there are any errors during this operation.
     */
    private long submitSpecification(TCSubject tcSubject, long projectId, String filename, DataHandler dataHandler)
        throws SpecificationReviewServiceException {
        try {
            return uploadExternalServices.uploadSpecification(projectId, tcSubject.getUserId(),
                filename, dataHandler);
        } catch (RemoteException e) {
            throw logException(new SpecificationReviewServiceException("Fails to upload specification.",
                e));
        } catch (UploadServicesException e) {
            throw logException(new SpecificationReviewServiceException("Fails to upload specification.",
                e));
        }
    }

    /**
     * Updates given project data.
     *
     * @param fullProjectData
     *            the full project data.
     * @param operator
     *            the operator.
     * @throws SpecificationReviewServiceException
     *             if any error occurs when updating phases.
     */
    private void updatePhases(FullProjectData fullProjectData, String operator)
        throws SpecificationReviewServiceException {
        try {
            projectServices.updatePhases(fullProjectData, operator);
        } catch (ProjectServicesException e) {
            throw logException(new SpecificationReviewServiceException("Fails to update phase.", e));
        }
    }


    /**
     * Updates given project data.
     *
     * @param fullProjectData
     *            the full project data.
     * @param operator
     *            the operator.
     * @throws SpecificationReviewServiceException
     *             if any error occurs when updating phases.
     */
    private void updateProject(com.topcoder.management.project.Project project, String reason, String operator)
        throws SpecificationReviewServiceException {
        try {
            projectServices.updateProject(project, reason, operator);
        } catch (ProjectServicesException e) {
            throw logException(new SpecificationReviewServiceException("Fails to update phase.", e));
        }
    }

    /**
     * Uploads a specification in the form of a file, for the given project by the given
     * user. This method can be used for an initial submission or an updated submission.
     *
     * @param tcSubject
     *            the user making the request.
     * @param filename
     *            the name of the file with the submission to upload.
     * @param projectId
     *            the ID of the project.
     * @return the submission ID.
     * @throws IllegalArgumentException
     *             if tcSubject is null, or projectId is not positive, or if filename is
     *             null or empty.
     * @throws SpecificationReviewServiceException
     *             if there are any errors during this operation.
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public long submitSpecificationAsFile(TCSubject tcSubject, long projectId, String filename)
        throws SpecificationReviewServiceException {
        assertNotNull(tcSubject, "tcSubject");
        assertPositive(projectId, "projectId");
        assertNotNullNotEmpty(filename, "filename");

        FileDataSource fileDataSource = new FileDataSource(filename);
        DataHandler dataHandler = new DataHandler(fileDataSource);
        return submitSpecification(tcSubject, projectId, new File(filename).getName(), dataHandler);
    }

    /**
     * Uploads a specification in the form of a String, for the given project by the given
     * user. This method can be used for an initial submission or an updated submission.
     *
     * @param content
     *            the content to upload.
     * @param tcSubject
     *            the user making the request.
     * @param projectId
     *            the ID of the project.
     * @return the submission ID.
     * @throws IllegalArgumentException
     *             if tcSubject is null, or projectId is not positive, or content is null
     *             or empty.
     * @throws SpecificationReviewServiceException
     *             if there are any errors during this operation.
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public long submitSpecificationAsString(TCSubject tcSubject, long projectId, String content)
        throws SpecificationReviewServiceException {
        assertNotNull(tcSubject, "tcSubject");
        assertPositive(projectId, "projectId");
        assertNotNullNotEmpty(content, "content");

        File file = null;
        FileWriter fileWriter = null;

        try {
            file = File.createTempFile("specification", ".txt");
            fileWriter = new FileWriter(file);
            fileWriter.write(content);
            // need to flush, otherwise the data handler can not get
            // the content
            fileWriter.flush();
            return submitSpecificationAsFile(tcSubject, projectId, file.getAbsolutePath());
        } catch (IOException e) {
            throw logException(new SpecificationReviewServiceException(
                "Fails to submit specification as string.", e));
        } finally {
            try {
                if (fileWriter != null) {
                    try {
                        fileWriter.close();
                    } catch (IOException e) {
                        throw logException(new SpecificationReviewServiceException(
                            "Fails to close file writer.", e));
                    }
                }
            } finally {
                if (file != null) {
                    try {
                        file.delete();
                    } catch (SecurityException e) {
                        throw logException(new SpecificationReviewServiceException(
                            "Fails to delete file.", e));
                    }
                }
            }
        }
    }

    /**
     * Gets a specification review, including the scorecard structure as well as the
     * review content. Returns null if not found.
     *
     * @param tcSubject
     *            the user making the request.
     * @param projectId
     *            the ID of the project.
     * @return the entity with scorecard and review (null if not found).
     * @throws IllegalArgumentException
     *             if tcSubject is null or projectId is not positive.
     * @throws SpecificationReviewServiceException
     *             if there are any errors during this operation.
     */
    public SpecificationReview getSpecificationReview(TCSubject tcSubject, long projectId)
        throws SpecificationReviewServiceException {
        assertNotNull(tcSubject, "tcSubject");
        assertPositive(projectId, "projectId");

        Connection connection = null;

        long activeSubmissionStatusId;
        long specificationSubmissionTypeId;

        try {
            connection = createConnection();
            // Lookup ID of submission status with "Active" name
            activeSubmissionStatusId = SubmissionStatusLookupUtility.lookUpId(connection, "Active");
            // Lookup ID of submission type with "Specification Submission" name
            specificationSubmissionTypeId = SubmissionTypeLookupUtility.lookUpId(connection,
                SPECIFICATION_SUBMISSION);
        } catch (SQLException e) {
            throw logException(new SpecificationReviewServiceException("Fails to look up id.", e));
        } finally {
            closeConnection(connection);
        }

        // create filter to search specification submission
        Filter projectIdFilter = SubmissionFilterBuilder.createProjectIdFilter(projectId);
        Filter activeSubmissionStatusFilter = SubmissionFilterBuilder
            .createSubmissionStatusIdFilter(activeSubmissionStatusId);
        Filter specificationSubmissionTypeFilter = SubmissionFilterBuilder
            .createSubmissionTypeIdFilter(specificationSubmissionTypeId);
        Filter fullSpecificationSubmissionFilter = new AndFilter(Arrays.asList(new Filter[] {
            projectIdFilter, activeSubmissionStatusFilter, specificationSubmissionTypeFilter}));

        Submission[] specSubmissions;
        try {
            synchronized (this) {
                specSubmissions = uploadManager.searchSubmissions(fullSpecificationSubmissionFilter);
            }
        } catch (UploadPersistenceException e) {
            throw logException(new SpecificationReviewServiceException(
                "Fails to search specification submission.", e));
        } catch (SearchBuilderException e) {
            throw logException(new SpecificationReviewServiceException(
                "Fails to search specification submission.", e));
        }

        if (specSubmissions.length == 0) {
            return null;
        }
     /*   if (specSubmissions.length > 1) {
            throw logException(new SpecificationReviewServiceException(
                "Multiple specification submissions exist for project: " + projectId));
        } */

        long specSubmissionId = specSubmissions[0].getId();

        // search review for the submission
        Filter reviewFilter = new EqualToFilter("submission", specSubmissionId);

        Review[] reviews;
        try {
            synchronized (this) {
                reviews = reviewManager.searchReviews(reviewFilter, true);
            }
        } catch (ReviewManagementException e) {
            throw logException(new SpecificationReviewServiceException(
                "Fails to search review for specification submission with id[" + specSubmissionId + "].",
                e));
        }

        if (reviews.length == 0) {
            return null;
        }
        if (reviews.length > 1) {
            throw logException(new SpecificationReviewServiceException(
                "Multiple reviews exist for specification submission with id[" + specSubmissionId
                    + "]."));
        }

        // get scorecard
        long scorecardId = reviews[0].getScorecard();
        Scorecard scorecard;
        try {
            synchronized (this) {
                scorecard = scorecardManager.getScorecard(scorecardId);
            }
        } catch (PersistenceException e) {
            throw logException(new SpecificationReviewServiceException("Fails to get scorecard with id:"
                + scorecardId, e));
        }

        SpecificationReview result = new SpecificationReview();
        result.setReview(reviews[0]);
        result.setScorecard(scorecard);
        try {
            if (!"".equals(reviews[0].getCreationUser())) {
                result.setCreationUserHandle(userService.getUserHandle(Long.parseLong(reviews[0].getCreationUser())));
            } else {
                result.setCreationUserHandle("");
            }
        } catch (com.topcoder.service.user.UserServiceException e) {
            result.setCreationUserHandle("");
        }
        return result;
    }

    /**
     * Closes the given database connection.
     *
     * @param connection
     *            the database connection.
     * @throws SpecificationReviewServiceException
     *             if error occurs when closing connection.
     */
    private void closeConnection(Connection connection) throws SpecificationReviewServiceException {
        if (null != connection) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw logException(new SpecificationReviewServiceException(
                    "Fails to close the connection.", e));
            }
        }
    }

    /**
     * Gets the specification review status of the given project. It can be in
     * specification submission phase, or specification review phase, or null if neither.
     *
     * @param tcSubject
     *            the user making the request.
     * @param projectId
     *            the ID of the project.
     * @return the status of the specification review (null if not (&quot;waiting for fixes&quot; or
     *         &quot;pending review&quot;)).
     * @throws IllegalArgumentException
     *             if tcSubject is null or projectId is not positive.
     * @throws SpecificationReviewServiceException
     *             if there are any errors during this operation.
     */
    public SpecificationReviewStatus getSpecificationReviewStatus(TCSubject tcSubject, long projectId)
        throws SpecificationReviewServiceException {
        assertNotNull(tcSubject, "tcSubject");
        assertPositive(projectId, "projectId");

        FullProjectData fullProjectData = getFullProjectData(projectId);

        int specSubmissionNum = 0, finishedSpecSubmissionNum = 0, scheduledSpecSubmissionNum = 0;
        int specReviewNum = 0, finishedSpecReviewNum = 0, scheduledSpecReviewNum = 0;
        
        // get current phase
        Phase[] allPhases = fullProjectData.getAllPhases();
        Phase currentPhase = null;
        for (Phase phase : allPhases) {
            String phaseStatusName = phase.getPhaseStatus().getName();
            if (PhaseStatus.OPEN.getName().equals(phaseStatusName)) {
                currentPhase = phase;
            }
            
            String phaseTypeName = phase.getPhaseType().getName();
            if (SPECIFICATION_SUBMISSION.equals(phaseTypeName)) {
                specSubmissionNum++;
                if (PhaseStatus.CLOSED.getName().equals(phaseStatusName)) {
                    finishedSpecSubmissionNum++;
                }
                if (PhaseStatus.SCHEDULED.getName().equals(phaseStatusName)) {
                    scheduledSpecSubmissionNum++;
                }
            }
            if (SPECIFICATION_REVIEW.equals(phaseTypeName)) {
                specReviewNum++;
                if (PhaseStatus.CLOSED.getName().equals(phaseStatusName)) {
                    finishedSpecReviewNum++;
                }
                if (PhaseStatus.SCHEDULED.getName().equals(phaseStatusName)) {
                    scheduledSpecReviewNum++;
                }
            }
        }
        
        String phaseTypeName = "";
        if (currentPhase != null) {
            phaseTypeName = currentPhase.getPhaseType().getName();
        }
        
        if (specSubmissionNum == 0 && specReviewNum == 0) {
            return SpecificationReviewStatus.NO_SPEC_REVIEW;
        } else if (specSubmissionNum == scheduledSpecSubmissionNum) {
            return SpecificationReviewStatus.NO_SPEC_REVIEW;
        } else if (SPECIFICATION_SUBMISSION.equals(phaseTypeName) && finishedSpecSubmissionNum == 0) {
            return SpecificationReviewStatus.WAITING_FOR_SUBMIT;
        } else if (SPECIFICATION_SUBMISSION.equals(phaseTypeName) && finishedSpecSubmissionNum > 0) {
            return SpecificationReviewStatus.WAITING_FOR_FIXES;
        } else if (SPECIFICATION_REVIEW.equals(phaseTypeName)) {
            return SpecificationReviewStatus.PENDING_REVIEW;
        } else if (specSubmissionNum == finishedSpecSubmissionNum && specReviewNum == finishedSpecReviewNum){
            return SpecificationReviewStatus.FINISHED;
        } else {
            return SpecificationReviewStatus.UNKOWN;
        }
    }

    /**
     * Gets the IDs of all projects whose specification review positions are yet not
     * filled. Will return an empty list if there are no such projects.
     *
     * @param tcSubject
     *            the user making the request.
     * @return the IDs of all projects whose specification review positions are yet not
     *         filled (not null; doesn't contain null).
     * @throws IllegalArgumentException
     *             if tcSubject is null.
     * @throws SpecificationReviewServiceException
     *             if there are any errors during this operation.
     */
    public List<Long> getOpenSpecificationReviewPositions(TCSubject tcSubject)
        throws SpecificationReviewServiceException {
        assertNotNull(tcSubject, "tcSubject");

        List<Long> result = new ArrayList<Long>();

        // Find all active projects
        com.topcoder.management.project.Project[] activeProjects;
        try {
            activeProjects = projectServices.findActiveProjectsHeaders();
        } catch (ProjectServicesException e) {
            throw logException(new SpecificationReviewServiceException(
                "Fails to finds all active projects.", e));
        }

        // Look up ID of Specification Reviewer role
        Connection connection = null;
        long specificationReviewerResourceRoleId;
        try {
            connection = createConnection();
            specificationReviewerResourceRoleId = ResourceRoleLookupUtility.lookUpId(connection,
                SPECIFICATION_REVIEWER);
        } catch (SQLException e) {
            throw logException(new SpecificationReviewServiceException(
                "Fails to lookup id for resource role[Specification Reviewer].", e));
        } finally {
            closeConnection(connection);
        }

        try {
            for (com.topcoder.management.project.Project activeProject : activeProjects) {
                long projectId = activeProject.getId();
                // Create filter for filtering resources for the current project
                Filter projectIdFilter = ResourceFilterBuilder.createProjectIdFilter(projectId);
                // Create filter for filtering resources with Specification Reviewer role
                Filter specificationReviewerResourceRoleIdFilter = ResourceFilterBuilder
                    .createResourceRoleIdFilter(specificationReviewerResourceRoleId);
                // Create filter for filtering resources with Specification Reviewer role for the current project
                Filter fullFilter = new AndFilter(projectIdFilter,
                    specificationReviewerResourceRoleIdFilter);

                // Search for specification reviewers for the current project
                com.topcoder.management.resource.Resource[] specReviewers;
                synchronized (this) {
                    specReviewers = resourceManager.searchResources(fullFilter);
                }

                // the reviewer position has not been filled
                if (specReviewers.length == 0) {
                    result.add(projectId);
                }
            }
        } catch (ResourcePersistenceException e) {
            throw logException(new SpecificationReviewServiceException("Fails to search resource.", e));
        } catch (SearchBuilderException e) {
            throw logException(new SpecificationReviewServiceException("Fails to search resource.", e));
        }
        return result;
    }

    /**
     * Creates a DB connection.
     *
     * @return the created Connection instance (not null).
     * @throws SpecificationReviewServiceException
     *             if some error occurred.
     */
    private Connection createConnection() throws SpecificationReviewServiceException {
        Connection connection;
        try {
            if (null == connectionName) {
                // create a default db connection
                connection = dbConnectionFactory.createConnection();
            } else {
                // Create a connection with the configured name
                connection = dbConnectionFactory.createConnection(connectionName);
            }
        } catch (DBConnectionException e) {
            throw logException(new SpecificationReviewServiceException(
                "Fails to create database connection", e));
        }
        return connection;
    }

    /**
     * <p>
     * Logs the given exception at <c>ERROR</c> level. Both error message and stack trace
     * will be logged.
     * </p>
     *
     * @param <T>
     *            the generic type of exception.
     * @param exception
     *            The exception to log.
     * @return the passed in exception.
     */
    private <T extends Throwable> T logException(T exception) {
        if (log == null) {
            return exception;
        }

        // 'convert' the exception stack trace into string
        // note: closing the stringWriter has no effect
        StringWriter buffer = new StringWriter();
        exception.printStackTrace(new PrintWriter(buffer));
        log.log(Level.ERROR, buffer.toString());

        return exception;
    }

    /**
     * <p>
     * Add specification submitter resource to a user on a given project.
     * </p>
     *
     * @param projectId the id of the given project
     * @param userId the id of the user
     * @return boolean if resource already exists return false, otherwise true
     * @throws SpecificationReviewServiceException if some error occurred.
     * @since 1.1
     */
    private boolean addSpecificationSubmitter(long projectId, long userId) throws SpecificationReviewServiceException{
        try {
            ResourceRole[] resourceRoles = resourceManager.getAllResourceRoles();
            ResourceRole specificationSubmitterRole = null;
            for (int i = 0; i < resourceRoles.length; i++) {
                ResourceRole role = resourceRoles[i];
                if ("Specification Submitter".equals(role.getName())) {
                    specificationSubmitterRole = role;
                    break;
                }
            }
            
            if (resourceManager.resourceExists(projectId, specificationSubmitterRole.getId(), userId)) {
                return false;
            }
            
            String handle = userService.getUserHandle(userId);
            
            com.topcoder.management.resource.Resource resource = new com.topcoder.management.resource.Resource();
            // set resource properties
            resource.setProject(new Long(projectId));
            resource.setResourceRole(specificationSubmitterRole);
            resource.setProperty("Handle", handle);
            resource.setProperty("Payment", null);
            resource.setProperty("Payment Status", "N/A");
            resource.setProperty("External Reference ID", Long.toString(userId));
            resource.setProperty("Registration Date", DATE_FORMAT.format(new Date()));
            resource.setUserId(userId);
            resourceManager.updateResource(resource, Long.toString(userId));

            return true;

        } catch (Exception e) {
            throw logException(new SpecificationReviewServiceException("Fails to add specification submitter role to user", e));
        }
    }

    /**
     * <p>
     * Add the default specification reviewer resource to a given project by contest type.
     * </p>
     *
     * @param projectId the id of the given project
     * @param specReviewerId the default specification reviewer
     * @param specReviewPhaseId the spec review phase id
     * @return boolean if resource already exists or no need to add return false, otherwise true
     * @throws SpecificationReviewServiceException if some error occurred.
     * @since 1.1
     */
    private boolean addDefaultSpecificationReviewer(long projectId, long specReviewerId, long specReviewPhaseId, long rboardPhaseId, long reviewRespId, TCSubject tcSubject) throws SpecificationReviewServiceException{

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            ResourceRole[] resourceRoles = resourceManager.getAllResourceRoles();
            ResourceRole specificationReviewerRole = null;
            ResourceRole observerRole = null;
            for (int i = 0; i < resourceRoles.length; i++) {
                ResourceRole role = resourceRoles[i];
                if ("Specification Reviewer".equals(role.getName())) {
                    specificationReviewerRole = role;
                }

                if ("Observer".equals(role.getName())) {
                    observerRole = role;
                }

                if (specificationReviewerRole != null && observerRole != null) {
                    break;
                }
            }

            String handle = userService.getUserHandle(specReviewerId);
            
            // if no spec reviewer
            if (specificationReviewerRole != null &&
                    !resourceManager.resourceExists(projectId, specificationReviewerRole.getId(), specReviewerId)) {

                com.topcoder.management.resource.Resource resource = new com.topcoder.management.resource.Resource();
                // set resource properties
                resource.setProject(projectId);
                resource.setResourceRole(specificationReviewerRole);
                resource.setPhase(specReviewPhaseId);
                resource.setProperty("Handle", handle);
                resource.setProperty("Payment", "0");
                resource.setProperty("Payment Status", "No");
                resource.setProperty("External Reference ID", Long.toString(specReviewerId));
                resource.setProperty("Registration Date", DATE_FORMAT.format(new Date()));
                resource.setUserId(specReviewerId);
                resourceManager.updateResource(resource, Long.toString(specReviewerId));

                connection = createConnection();
                preparedStatement = connection.prepareStatement("insert into rboard_application(user_id, project_id, phase_id, review_resp_id, primary_ind) values (?, ?, ?, ?, ?);");
                preparedStatement.setObject(1, specReviewerId);
                preparedStatement.setObject(2, projectId);
                preparedStatement.setObject(3, rboardPhaseId);
                preparedStatement.setObject(4, reviewRespId);
                preparedStatement.setObject(5, 0);

                preparedStatement.executeUpdate();
            }

            // if not observer
            if (specificationReviewerRole != null &&
                    !resourceManager.resourceExists(projectId, observerRole.getId(), specReviewerId)) {
                com.topcoder.management.resource.Resource resource = new com.topcoder.management.resource.Resource();
                // set resource properties
                resource.setProject(projectId);
                resource.setResourceRole(observerRole);
                resource.setProperty("Handle", handle);
                resource.setProperty("Payment", null);
                resource.setProperty("Payment Status", "N/A");
                resource.setProperty("External Reference ID", Long.toString(specReviewerId));
                resource.setProperty("Registration Date", DATE_FORMAT.format(new Date()));
                resource.setUserId(specReviewerId);
                resourceManager.updateResource(resource, Long.toString(specReviewerId));
            }

            // add notification
            projectServices.addNotifications(specReviewerId, new long[] { projectId }, String.valueOf(tcSubject.getUserId()));

            if (createForum)
            {
                // add forum watch
                long forumId = projectServices.getForumId(projectId);
                createForumWatchAndRole(forumId, specReviewerId, true);
            }
            

            return true;
        } catch (Exception e) {
            throw logException(new SpecificationReviewServiceException("Fails to add specification reviewer role to user", e));
        } finally {
            try
            {
                if (preparedStatement != null)
                {
                    preparedStatement.close();
                }
                 if (connection != null)
                {
                    connection.close();
                }
            }
            catch (Exception ee) {}

        }
    }



    /**
     * Gets all specification reviews, including the scorecard structure as well
     * as the
     * review content. Returns a empty list if not found.
     * 
     * @param tcSubject
     *            the user making the request.
     * @param projectId
     *            the ID of the project.
     * @return the list of the entity with scorecard and review (empty if not
     *         found).
     * @throws IllegalArgumentException
     *             if tcSubject is null or projectId is not positive.
     * @throws SpecificationReviewServiceException
     *             if there are any errors during this operation.
     */
    public List<SpecificationReview> getAllSpecificationReviews(
            TCSubject tcSubject, long projectId)
            throws SpecificationReviewServiceException {
        assertNotNull(tcSubject, "tcSubject");
        assertPositive(projectId, "projectId");

        Connection connection = null;

        List<SpecificationReview> results = new ArrayList<SpecificationReview>();
        
        long activeSubmissionStatusId;
        long failedSubmissionStatusId;
        long specificationSubmissionTypeId;

        try {
            connection = createConnection();
            // Lookup ID of submission status with "Active" name
            activeSubmissionStatusId = SubmissionStatusLookupUtility.lookUpId(connection, "Active");
            
            // Lookup ID of submission status with "Failed Review" name
            failedSubmissionStatusId = SubmissionStatusLookupUtility.lookUpId(connection, "Failed Review");
            
            // Lookup ID of submission type with "Specification Submission" name
            specificationSubmissionTypeId = SubmissionTypeLookupUtility.lookUpId(connection,
                SPECIFICATION_SUBMISSION);
        } catch (SQLException e) {
            throw logException(new SpecificationReviewServiceException("Fails to look up id.", e));
        } finally {
            closeConnection(connection);
        }

        // create filter to search specification submission
        Filter projectIdFilter = SubmissionFilterBuilder
                .createProjectIdFilter(projectId);
        
        Filter activeSubmissionStatusFilter = SubmissionFilterBuilder
                .createSubmissionStatusIdFilter(activeSubmissionStatusId);
        Filter failedSubmissionStatusFilter = SubmissionFilterBuilder
                .createSubmissionStatusIdFilter(failedSubmissionStatusId);
        Filter submissionStatusFilter = new OrFilter(
                Arrays.asList(new Filter[] { activeSubmissionStatusFilter,
                        failedSubmissionStatusFilter }));

        Filter specificationSubmissionTypeFilter = SubmissionFilterBuilder
                .createSubmissionTypeIdFilter(specificationSubmissionTypeId);
        
        Filter fullSpecificationSubmissionFilter = new AndFilter(
                Arrays.asList(new Filter[] { projectIdFilter,
                        submissionStatusFilter,
                        specificationSubmissionTypeFilter }));

        Submission[] specSubmissions;
        try {
            synchronized (this) {
                specSubmissions = uploadManager.searchSubmissions(fullSpecificationSubmissionFilter);
            }
        } catch (UploadPersistenceException e) {
            throw logException(new SpecificationReviewServiceException(
                "Fails to search specification submission.", e));
        } catch (SearchBuilderException e) {
            throw logException(new SpecificationReviewServiceException(
                "Fails to search specification submission.", e));
        }
        
        if (specSubmissions.length == 0)
        {
            return results; 
        }

        // search review for the submission
        List<Filter> reviewFilters = new ArrayList<Filter>();
        
        for (Submission specSubmission : specSubmissions) {
            reviewFilters.add(new EqualToFilter("submission", specSubmission.getId()));
        }
        Filter reviewFilter = new OrFilter(reviewFilters);
        
        Review[] reviews;
        try {
            synchronized (this) {
                reviews = reviewManager.searchReviews(reviewFilter, true);
            }
        } catch (ReviewManagementException e) {
            throw logException(new SpecificationReviewServiceException(
                    "Fails to search reviews.", e));
        }
        
        for (Review review : reviews) {
            // get scorecard
            long scorecardId = review.getScorecard();
            Scorecard scorecard;
            try {
                synchronized (this) {
                    scorecard = scorecardManager.getScorecard(scorecardId);
                }
            } catch (PersistenceException e) {
                throw logException(new SpecificationReviewServiceException("Fails to get scorecard with id:"
                    + scorecardId, e));
            }

            SpecificationReview result = new SpecificationReview();
            result.setReview(review);
            result.setScorecard(scorecard);
            try {
                if (!"".equals(review.getCreationUser())) {
                    result.setCreationUserHandle(userService.getUserHandle(Long.parseLong(review.getCreationUser())));
                } else {
                    result.setCreationUserHandle("");
                }
            } catch (com.topcoder.service.user.UserServiceException e) {
                result.setCreationUserHandle("");
            }     
            
            results.add(result);
        }

        return results;
    }
    
    /**
     * resubmitSpecification a mock specification, for the given project by the given
     * user. This method can be used for an initial submission or an updated submission.
     *
     * @param tcSubject
     *            the user making the request.
     * @param projectId
     *            the ID of the project.
     * @return the submission ID.
     * @throws IllegalArgumentException
     *             if tcSubject is null, or projectId is not positive, or content is null
     *             or empty.
     * @throws SpecificationReviewServiceException
     *             if there are any errors during this operation.
     */
    public long resubmitSpecification(TCSubject tcSubject, long projectId)
        throws SpecificationReviewServiceException {
        FullProjectData fullProjectData = getFullProjectData(projectId);
        // upload a mock submission
        if ( fullProjectData.getProjectHeader().getProjectCategory().getProjectType().getId() == 3 ) {
            return submitSpecificationAsString(tcSubject, projectId, mockStudioSubmissionContent.replace("[pj]", new Long(projectId).toString()));
        } else {
            return submitSpecificationAsString(tcSubject, projectId, mockSoftwareSubmissionContent.replace("[pj]", new Long(projectId).toString()));
        }
    }


        /**
     * create forum watch with given parameters. It will lookup the ForumsHome
     * interface, and create the forum by the ejb home interface.
     *
     *
     * @param forumId
     *            The forum id
     * @param userId
     *            userId The user id to use
     * @param watch
     *            whether to create category watch.
     */
    private void createForumWatchAndRole(long forumId, long userId, boolean watch) {

        try {
            Properties p = new Properties();
            p.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
            p.put(Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces");
            p.put(Context.PROVIDER_URL, forumBeanProviderUrl);

            Context c = new InitialContext(p);
            ForumsHome forumsHome = (ForumsHome) c.lookup(ForumsHome.EJB_REF_NAME);

            Forums forums = forumsHome.create();

            String roleId = "Software_Users_" + forumId;
            if (watch)
            {
                forums.createCategoryWatch(userId, forumId);
            }

            forums.assignRole(userId, roleId);
        } catch (Exception e) {
            logException(e);
        }
    }
}
