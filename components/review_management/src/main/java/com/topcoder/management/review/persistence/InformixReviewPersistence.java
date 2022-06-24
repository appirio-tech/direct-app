/*
 * Copyright (C) 2006-2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.review.persistence;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.topcoder.db.connectionfactory.DBConnectionException;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.management.review.ConfigurationException;
import com.topcoder.management.review.DuplicateReviewEntityException;
import com.topcoder.management.review.ReviewEntityNotFoundException;
import com.topcoder.management.review.ReviewPersistence;
import com.topcoder.management.review.ReviewPersistenceException;
import com.topcoder.management.review.data.Comment;
import com.topcoder.management.review.data.CommentType;
import com.topcoder.management.review.data.Item;
import com.topcoder.management.review.data.Review;
import com.topcoder.management.review.persistence.Helper.DataType;
import com.topcoder.management.review.persistence.logging.LogMessage;
import com.topcoder.search.builder.SearchBuilderException;
import com.topcoder.search.builder.SearchBundle;
import com.topcoder.search.builder.SearchBundleManager;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.datavalidator.NotValidator;
import com.topcoder.util.datavalidator.NullValidator;
import com.topcoder.util.datavalidator.ObjectValidator;
import com.topcoder.util.idgenerator.IDGenerationException;
import com.topcoder.util.idgenerator.IDGenerator;
import com.topcoder.util.idgenerator.IDGeneratorFactory;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;
import com.topcoder.util.sql.databaseabstraction.CustomResultSet;
import com.topcoder.util.sql.databaseabstraction.InvalidCursorStateException;
import com.topcoder.util.sql.databaseabstraction.NullColumnValueException;

/**
 * <p>
 * This class is responsible for creating, updating, searching the review
 * entities stored in the Informix database. Additionally, application users can
 * also add comment for review and item, and get all the comment types from the
 * persistence.
 * </p>
 * <p>
 * Thread Safety: This class is not thread safe.
 * </p>
 * 
 * <p>
 * Changes in v1.0.2 (Cockpit Spec Review Backend Service Update v1.0):
 * - added flag so that container transaction demarcation can be used.
 * - LogManager is used instead of LogFactory.
 * </p>
 * 
 * @author woodjhon, urtks, George1, pulky
 * @version 1.0.2
 */
public class InformixReviewPersistence implements ReviewPersistence {
    /**
     * Represents the id generator name used to get reviewIDGenerator from
     * IDGeneratorFactory.
     */
    public static final String REVIEW_ID_SEQ = "review_id_seq";

    /**
     * Represents the id generator name used to get reviewCommentIDGenerator
     * from IDGeneratorFactory.
     */
    public static final String REVIEW_COMMENT_ID_SEQ = "review_comment_id_seq";

    /**
     * Represents the id generator name used to get reviewItemIDGenerator from
     * IDGeneratorFactory.
     */
    public static final String REVIEW_ITEM_ID_SEQ = "review_item_id_seq";

    /**
     * Represents the id generator name used to get reviewItemCommentIDGenerator
     * from IDGeneratorFactory.
     */
    public static final String REVIEW_ITEM_COMMENT_ID_SEQ = "review_item_comment_id_seq";

    /** Logger instance using the class name as category */
    private static final Log LOGGER = LogManager.getLog(InformixReviewPersistence.class.getName());
    
    /**
     * Represents the name of connection name parameter in configuration.
     */
    private static final String CONNECTION_NAME_PARAMETER = "connection.name";

    /**
     * Represents the name of connection factory namespace parameter in
     * configuration.
     */
    private static final String CONNECTION_FACTORY_NAMESPACE_PARAMETER = "connection.factory_namespace";

    /**
     * Represents the name of connection factory class parameter in
     * configuration.
     */
    private static final String CONNECTION_FACTORY_CLASS_PARAMETER = "connection.factory_class";

    /**
     * Represents the name of search bundle manager namespace parameter in
     * configuration.
     */
    private static final String SEARCH_BUNDLE_MANAGER_NAMESPACE_PARAMETER = "search_bundle_manager_namespace";

    /**
     * Represents the name of search bundle name parameter in configuration.
     */
    private static final String SEARCH_BUNDLE_NAME_PARAMETER = "search_bundle_name";

    /**
     * Represents the review table.
     */
    private static final String REVIEW_TABLE = "review";

    /**
     * Represents the review comment table.
     */
    private static final String REVIEW_COMMENT_TABLE = "review_comment";

    /**
     * Represents the review item table.
     */
    private static final String REVIEW_ITEM_TABLE = "review_item";

    /**
     * Represents the review item comment table.
     */
    private static final String REVIEW_ITEM_COMMENT_TABLE = "review_item_comment";

    /**
     * Represents the comment type lookup table.
     */
    private static final String COMMENT_TYPE_LOOKUP_TABLE = "comment_type_lu";

    /**
     * Represents the placeholder string in a sql statement to be replaced by a
     * set of ids.
     */
    private static final String ID_ARRAY_PARAMETER_PLACEHOLDER = "$ID_ARRAY$";

    /**
     * Represents the regular expression string used to find the id-array
     * placeholder string in a sql statement.
     */
    private static final String ID_ARRAY_PARAMETER_REGULAR_EXP = "\\$ID_ARRAY\\$";

    /**
     * Represents the sql statement to create review.
     */
    private static final String CREATE_REVIEW_SQL = "INSERT INTO " + REVIEW_TABLE
        + " (review_id, resource_id, submission_id, project_phase_id, scorecard_id, committed, score, initial_score,"
        + " create_user, create_date, modify_user, modify_date)"
        + " values (?,?,?,?,?,?,?,?,?,CURRENT,?,CURRENT)";

    /**
     * Represents the sql statement to create review comment.
     */
    private static final String CREATE_REVIEW_COMMENT_SQL = "INSERT INTO "
        + REVIEW_COMMENT_TABLE
        + " (review_comment_id, resource_id, review_id, comment_type_id, content, extra_info, sort,"
        + " create_user, create_date, modify_user, modify_date)"
        + " values (?,?,?,?,?,?,?,?,CURRENT,?,CURRENT)";

    /**
     * Represents the sql statement to create review item.
     */
    private static final String CREATE_REVIEW_ITEM_SQL = "INSERT INTO " + REVIEW_ITEM_TABLE
        + " (review_item_id, review_id, scorecard_question_id, upload_id, answer, sort,"
        + " create_user, create_date, modify_user, modify_date)"
        + " values (?,?,?,?,?,?,?,CURRENT,?,CURRENT)";

    /**
     * Represents the sql statement to create review item comment.
     */
    private static final String CREATE_REVIEW_ITEM_COMMENT_SQL = "INSERT INTO "
        + REVIEW_ITEM_COMMENT_TABLE
        + " (review_item_comment_id, resource_id, review_item_id, comment_type_id, content, extra_info, sort,"
        + " create_user, create_date, modify_user, modify_date)"
        + " values (?,?,?,?,?,?,?,?,CURRENT,?,CURRENT)";

    /**
     * Represents the sql statement to update review.
     */
    private static final String UPDATE_REVIEW_SQL = "UPDATE " + REVIEW_TABLE
            + " SET resource_id=?, submission_id=?, project_phase_id = ?, scorecard_id=?, committed=?, score=?, initial_score=?,"
            + " modify_user=?, modify_date=CURRENT" + " WHERE review_id=?";

    /**
     * Represents the sql statement to update review comment.
     */
    private static final String UPDATE_REVIEW_COMMENT_SQL = "UPDATE " + REVIEW_COMMENT_TABLE
        + " SET resource_id=?, comment_type_id=?, content=?, extra_info=?, sort=?,"
        + " modify_user=?, modify_date=CURRENT" + " WHERE review_comment_id=?";

    /**
     * Represents the sql statement to update review item.
     */
    private static final String UPDATE_REVIEW_ITEM_SQL = "UPDATE " + REVIEW_ITEM_TABLE
        + " SET scorecard_question_id=?, upload_id=?, answer=?, sort=?,"
        + " modify_user=?, modify_date=CURRENT" + " WHERE review_item_id=?";

    /**
     * Represents the sql statement to update review item comment.
     */
    private static final String UPDATE_REVIEW_ITEM_COMMENT_SQL = "UPDATE "
        + REVIEW_ITEM_COMMENT_TABLE
        + " SET resource_id=?, comment_type_id=?, content=?, extra_info=?, sort=?,"
        + " modify_user=?, modify_date=CURRENT" + " WHERE review_item_comment_id=?";

    /**
     * Represents the sql statement to query reviews.
     */
    private static final String QUERY_REVIEWS_SQL = "SELECT "
            + "review_id, resource_id, submission_id, project_phase_id, scorecard_id, committed, score, initial_score, "
            + "create_user, create_date, modify_user, modify_date FROM " + REVIEW_TABLE + " WHERE review_id IN ("
            + ID_ARRAY_PARAMETER_PLACEHOLDER + ")";

    /**
     * Represents the sql statement to query review comments.
     */
    private static final String QUERY_REVIEW_COMMENTS_SQL = "SELECT "
        + "review_comment_id, resource_id, review_id, comment_type_id, content, extra_info "
        + "FROM " + REVIEW_COMMENT_TABLE + " WHERE review_id IN (" + ID_ARRAY_PARAMETER_PLACEHOLDER
        + ") ORDER BY review_id, sort";

    /**
     * Represents the sql statement to query review items.
     */
    private static final String QUERY_REVIEW_ITEMS_SQL = "SELECT "
        + "review_item_id, review_id, scorecard_question_id, upload_id, answer " + "FROM "
        + REVIEW_ITEM_TABLE + " WHERE review_id IN (" + ID_ARRAY_PARAMETER_PLACEHOLDER
        + ") ORDER BY review_id, sort";

    /**
     * Represents the sql statement to query review item comments.
     */
    private static final String QUERY_REVIEW_ITEM_COMMENTS_SQL = "SELECT "
        + "review_item_comment_id, resource_id, review_item_id, comment_type_id, content, extra_info "
        + "FROM " + REVIEW_ITEM_COMMENT_TABLE + " WHERE review_item_id IN ("
        + "SELECT review_item_id FROM " + REVIEW_ITEM_TABLE + " WHERE review_id IN ("
        + ID_ARRAY_PARAMETER_PLACEHOLDER + ")) ORDER BY review_item_id, sort";

    /**
     * Represents the sql statement to query review comment IDs.
     */
    private static final String QUERY_REVIEW_COMMENT_IDS_SQL = "SELECT "
        + "review_comment_id FROM " + REVIEW_COMMENT_TABLE + " WHERE review_id=?";

    /**
     * Represents the sql statement to query review item IDs.
     */
    private static final String QUERY_REVIEW_ITEM_IDS_SQL = "SELECT " + "review_item_id FROM "
        + REVIEW_ITEM_TABLE + " WHERE review_id=?";

    /**
     * Represents the sql statement to query review item comment IDs.
     */
    private static final String QUERY_REVIEW_ITEM_COMMENT_IDS_SQL = "SELECT "
        + "review_item_comment_id FROM " + REVIEW_ITEM_COMMENT_TABLE + " WHERE review_item_id=?";

    /**
     * Represents the sql statement to query all comment types.
     */
    private static final String QUERY_ALL_COMMENT_TYPES_SQL = "SELECT comment_type_id, name FROM "
        + COMMENT_TYPE_LOOKUP_TABLE;

    /**
     * The DBConnectionFactory is used by all the methods that perform the
     * database queried to get the named connection. It's initialized in the
     * contractor, not-null.
     */
    private final DBConnectionFactory dbFactory;

    /**
     * The connection name to get the named connection from this.dbFactory. It's
     * set in the contractor, non-null, non-empty.
     */
    private final String connectionName;

    /**
     * <p>
     * The searchBuilder is used to execute the database search string to
     * retrieve an array of review ids that matches the specified filter. The
     * constructor will first create SearchBundleManager from manager namespace,
     * and then get searchBundle from manager by configured searchBundleName.
     * It's expected that the "alias" and "context" properties in the
     * SearchBundleManager should be configured properly.
     * </p>
     * <p>
     * The context property for SearchBundleManager is:
     * <code>select review_id from review, scorecard, resource
     * where review.scorecard_id=scorecard.scorecard_id and
     * review.resource_id=resource.resource_id and</code>.
     * </p>
     * Following is the alias mapping:
     * <ol>
     * <li>scorecardType --- scorecard.scorecard_type_id</li>
     * <li>submission --- review.submission_id</li>
     * <li>projectPhase --- review.project_phase_id</li>
     * <li>reviewer --- review.resource_id</li>
     * <li>project --- resource.project_id</li>
     * <li>committed --- review.committed</li>
     * </ol>
     */
    private final SearchBundle searchBundle;

    /**
     * The IDGenerator used to generate the review id. It's set in the
     * constructor, not-null.
     */
    private final IDGenerator reviewIDGenerator;

    /**
     * The IDGenerator used to generate the review comment id. It's set in the
     * constructor, not-null.
     */
    private final IDGenerator reviewCommentIDGenerator;

    /**
     * The IDGenerator used to generate the review item id. It's set in the
     * constructor, not-null.
     */
    private final IDGenerator reviewItemIDGenerator;

    /**
     * The IDGenerator used to generate the review item comment id. It's set in
     * the constructor, not-null.
     */
    private final IDGenerator reviewItemCommentIDGenerator;

    /**
     * <p>
     * Represents whether this component should use manual commit or not.
     * </p>
     *
     * @since 1.0.2
     */
    private final Boolean useManualCommit = false;

    /**
     * <p>
     * Initializes a new instance of InformixReviewPersistence class form the
     * default namespace, which is the full qualified class name of this class.
     * </p>
     * @exception ConfigurationException
     *                if failed to create the instance from the default
     *                namespace.
     * @exception ReviewPersistenceException
     *                if error occurs when creating IDGenerators.
     */
    public InformixReviewPersistence() throws ConfigurationException, ReviewPersistenceException {
        this(InformixReviewPersistence.class.getName());
    }

    /**
     * <p>
     * Initializes a new instance of InformixReviewPersistence class from the
     * given namespace.
     * </p>
     * @param namespace
     *            the namespace to create the instance
     * @exception IllegalArgumentException
     *                if namespace is null or empty string
     * @exception ConfigurationException
     *                if failed to create the instance from given namespace.
     * @exception ReviewPersistenceException
     *                if error occurs when creating IDGenerators.
     */
    public InformixReviewPersistence(String namespace) throws ConfigurationException,
        ReviewPersistenceException {
        Helper.assertStringNotNullNorEmpty(namespace, "namespace");

        LOGGER.log(Level.INFO, "Instantiate InformixReviewPersistence from the namespace:" + namespace);

        ConfigManager cm = ConfigManager.getInstance();

        connectionName = Helper.getConfigurationParameterValue(cm, namespace,
            CONNECTION_NAME_PARAMETER, LOGGER);
        dbFactory = createDBConnectionFactory(cm, namespace);
        searchBundle = createSearchBundle(cm, namespace);

        reviewIDGenerator = createIDGenerator(REVIEW_ID_SEQ);
        reviewCommentIDGenerator = createIDGenerator(REVIEW_COMMENT_ID_SEQ);
        reviewItemIDGenerator = createIDGenerator(REVIEW_ITEM_ID_SEQ);
        reviewItemCommentIDGenerator = createIDGenerator(REVIEW_ITEM_COMMENT_ID_SEQ);
    }

    /**
     * <p>
     * Initializes a new instance of InformixReviewPersistence class with the
     * given arguments. The searchable fields for the search bundle are set in
     * the constructor, whatever their original values.
     * </p>
     * @param dbFactory
     *            the DBConnectionFactory
     * @param connectionName
     *            the connection name
     * @param searchBundle
     *            the search bundle to set
     * @exception IllegalArgumentException
     *                if dbFactory is null, or connectionName is null or empty
     *                string, or searchBundle is null.
     * @exception ReviewPersistenceException
     *                if error occurs when creating IDGenerators.
     */
    public InformixReviewPersistence(DBConnectionFactory dbFactory, String connectionName,
        SearchBundle searchBundle) throws ReviewPersistenceException {
        Helper.assertObjectNotNull(dbFactory, "dbFactory");
        Helper.assertStringNotNullNorEmpty(connectionName, "connectionName");
        Helper.assertObjectNotNull(searchBundle, "searchBundle");

        LOGGER.log(Level.INFO,
        		"Instantiate InformixReviewPersistence with given dbFactory, connectionName and searchBundle");
        this.dbFactory = dbFactory;
        this.connectionName = connectionName;
        this.searchBundle = searchBundle;
        searchBundle.setSearchableFields(getSearchableFields());

        reviewIDGenerator = createIDGenerator(REVIEW_ID_SEQ);
        reviewCommentIDGenerator = createIDGenerator(REVIEW_COMMENT_ID_SEQ);
        reviewItemIDGenerator = createIDGenerator(REVIEW_ITEM_ID_SEQ);
        reviewItemCommentIDGenerator = createIDGenerator(REVIEW_ITEM_COMMENT_ID_SEQ);
    }

    /**
     * Create the IDGenerator with the given idName.
     * @param idName
     *            the id name
     * @return the IDGenerator object
     * @throws ReviewPersistenceException
     *             if any error occurs during the creation.
     */
    private IDGenerator createIDGenerator(String idName) throws ReviewPersistenceException {
        try {
            return IDGeneratorFactory.getIDGenerator(idName);
        } catch (IDGenerationException e) {
        	LOGGER.log(Level.FATAL,"Unable to create IDGenerator [" + idName + "]."
        			+ LogMessage.getExceptionStackTrace(e));
            throw new ReviewPersistenceException("Unable to create IDGenerator [" + idName + "].",
                e);
        }
    }

    /**
     * <p>
     * Initializes a new instance of DBConnectionFactory class from
     * configuration.
     * </p>
     * <p>
     * Note: all the parameters are assumed valid when called.
     * </p>
     * @param cm
     *            the ConfigManager instance
     * @param namespace
     *            configuration namespace
     * @return a new instance of DBConnectionFactory class
     * @throws ConfigurationException
     *             if the instance of DBConnectionFactory class can not be
     *             created.
     */
    private DBConnectionFactory createDBConnectionFactory(ConfigManager cm, String namespace)
        throws ConfigurationException {
        String connFactoryClass = Helper.getConfigurationParameterValue(cm, namespace,
            CONNECTION_FACTORY_CLASS_PARAMETER, LOGGER);
        String connFactoryNamespace = Helper.getConfigurationParameterValue(cm, namespace,
            CONNECTION_FACTORY_NAMESPACE_PARAMETER, LOGGER);

        try {
            return (DBConnectionFactory) Class.forName(connFactoryClass).getConstructor(
                new Class[] {String.class}).newInstance(new Object[] {connFactoryNamespace});
        } catch (ClassNotFoundException e) {
        	LOGGER.log(Level.FATAL, "Unable to find class [" + connFactoryClass + "].\n"
        			+ LogMessage.getExceptionStackTrace(e));
            throw new ConfigurationException("Unable to find class [" + connFactoryClass + "].", e);
        } catch (NoSuchMethodException e) {
        	LOGGER.log(Level.FATAL, "Unable to find the constructor"
                    + " with one String parameter of class [" + connFactoryClass + "].\n"
        			+ LogMessage.getExceptionStackTrace(e));
            throw new ConfigurationException("Unable to find the constructor"
                + " with one String parameter of class [" + connFactoryClass + "].", e);
        } catch (IllegalAccessException e) {
        	LOGGER.log(Level.FATAL, "Unable to access the reflection info of class ["
                    + connFactoryClass + ". \n"
        			+ LogMessage.getExceptionStackTrace(e));
            throw new ConfigurationException("Unable to access the reflection info of class ["
                + connFactoryClass + ".", e);
        } catch (InstantiationException e) {
        	LOGGER.log(Level.FATAL, "Unable to initialize an instance of class ["
                    + connFactoryClass + "], because it's an interface or abstract class \n"
        			+ LogMessage.getExceptionStackTrace(e));
            throw new ConfigurationException("Unable to initialize an instance of class ["
                + connFactoryClass + "], because it's an interface or abstract class.\n", e);
        } catch (InvocationTargetException e) {
        	LOGGER.log(Level.FATAL, "Error occurs when invoking the constructor of class [" + connFactoryClass + "].\n"
        			+ LogMessage.getExceptionStackTrace(e));
            throw new ConfigurationException(
                "Error occurs when invoking the constructor of class [" + connFactoryClass + "].\n",
                e.getTargetException());
        } catch (ClassCastException e) {
        	LOGGER.log(Level.FATAL, "class [" + connFactoryClass
                    + "] is not of DBConnectionFactory type.\n"
        			+ LogMessage.getExceptionStackTrace(e));
            throw new ConfigurationException("class [" + connFactoryClass
                + "] is not of DBConnectionFactory type.", e);
        }
    }

    /**
     * <p>
     * Initializes a new instance of SearchBundle class from configuration.
     * </p>
     * <p>
     * Note: all the parameters are assumed valid when called.
     * </p>
     * @param cm
     *            the ConfigManager instance
     * @param namespace
     *            configuration namespace
     * @return a new instance of SearchBundle class
     * @throws ConfigurationException
     *             if the instance of SearchBundle class can not be created.
     */
    private SearchBundle createSearchBundle(ConfigManager cm, String namespace)
        throws ConfigurationException {
        String sbmNamespace = Helper.getConfigurationParameterValue(cm, namespace,
            SEARCH_BUNDLE_MANAGER_NAMESPACE_PARAMETER, LOGGER);
        String sbName = Helper.getConfigurationParameterValue(cm, namespace,
            SEARCH_BUNDLE_NAME_PARAMETER, LOGGER);

        SearchBundleManager sbManager;
        try {
            sbManager = new SearchBundleManager(sbmNamespace);
        } catch (Exception e) {
        	LOGGER.log(Level.FATAL, "Unable to create a new instance of SearchBundleManager class"
                    + " from namespace [" + sbmNamespace + "] \n"
        			+ LogMessage.getExceptionStackTrace(e));
            throw new ConfigurationException(
                "Unable to create a new instance of SearchBundleManager class"
                    + " from namespace [" + sbmNamespace + "]", e);
        }

        SearchBundle sb = sbManager.getSearchBundle(sbName);
        if (sb == null) {
        	LOGGER.log(Level.FATAL, "Unable to find the specified SearchBundle [" + sbName
                    + "] from the SearchBundleManager created from configuration namespace ["
                    + sbmNamespace + "].");
            throw new ConfigurationException("Unable to find the specified SearchBundle [" + sbName
                + "] from the SearchBundleManager created from configuration namespace ["
                + sbmNamespace + "].");
        }

/*        // create the searchable fields map.
        Map fieldsMap = new HashMap();
        ObjectValidator notNullValidator = new NotValidator(new NullValidator());
        fieldsMap.put("scorecardType", notNullValidator);
        fieldsMap.put("submission", notNullValidator);
        fieldsMap.put("reviewer", notNullValidator);
        fieldsMap.put("project", notNullValidator);
        fieldsMap.put("committed", notNullValidator);

        // set the fields map to search bundle.
        sb.setSearchableFields(getSearchableFields());*/
        return sb;
    }

    /**
     * Get the searchable fields map for search bundle.
     * @return the searchable fields map
     */
    private Map getSearchableFields() {

        // create the searchable fields map.
        Map fieldsMap = new HashMap();
        ObjectValidator notNullValidator = new NotValidator(new NullValidator());
        fieldsMap.put("scorecardType", notNullValidator);
        fieldsMap.put("submission", notNullValidator);
        fieldsMap.put("reviewer", notNullValidator);
        fieldsMap.put("project", notNullValidator);
        fieldsMap.put("committed", notNullValidator);

        return fieldsMap;
    }

    /**
     * Create a connection with transaction support.
     * @return the connection created
     * @throws ReviewPersistenceException
     *             if error happens when creating the connection
     */
    private Connection createTransactionalConnection() throws ReviewPersistenceException {
        try {
        	LOGGER.log(Level.INFO, new LogMessage(null, null,
        			"creating db connection using connection name: " + connectionName));
            Connection conn = dbFactory.createConnection(connectionName);
            LOGGER.log(Level.INFO, new LogMessage(null, null, "start transaction."));
            if(useManualCommit) {
                conn.setAutoCommit(false);
            }
            //conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            return conn;
        } catch (DBConnectionException e) {
            throw new ReviewPersistenceException("Error occurs when getting the connection.", e);
        } catch (SQLException e) {
            throw new ReviewPersistenceException(
                "Error occurs when setting the connection to support transaction.", e);
        }
    }

    /**
     * Check if the given review is valid. A Review object is considered valid
     * if:
     * <ol>
     * <li>author is positive;</li>
     * <li>submission is positive;</li>
     * <li>scorecard is positive;</li>
     * <li>all the review comments it contains are valid;</li>
     * <li>all the review items it contains are valid; </li>
     * <li>there are no duplicate entities(review comments, review items,
     * review item comments) by reference in it.</li>
     * </ol>
     * @param review
     *            the review to check
     * @throws IllegalArgumentException
     *             if the given review is invalid
     */
    private void assertReviewValid(Review review) {
        Helper.assertLongPositive(review.getAuthor(), "author of review");
        Helper.assertLongPositive(review.getSubmission(), "submission of review");
        Helper.assertLongPositive(review.getScorecard(), "scorecard of review");

        Comment[] comments = review.getAllComments();
        for (int i = 0; i < comments.length; ++i) {
            // comments[i] is never null.
            assertCommentValid(comments[i]);
        }

        Item[] items = review.getAllItems();
        for (int i = 0; i < items.length; ++i) {
            // items[i] is never null.
            assertItemValid(items[i]);
        }

        // Note:
        // 1. We do not need to check duplicate Item references, since the
        // implementation of Review class guarantees that there are no duplicate
        // Item references in a Review object.
        // 2. However we do need to check duplicate Comment references, although
        // implementation of Review class and Item class guarantees that there
        // are no duplicate Comment references in one Review or Item object.
        // There are two reasons: 1) review comment and review item comment are
        // both of Comment type, they can reference the same object. 2) review
        // item comments in different item can reference the same object.

        // check if there are duplicate references for Comment objects.
        Set commentSet = new HashSet();
        for (int i = 0; i < comments.length; ++i) {
            if (commentSet.contains(comments[i])) {
                throw new IllegalArgumentException("Duplicate Comment references found.");
            }
            commentSet.add(comments[i]);
        }
        for (int i = 0; i < items.length; ++i) {
            Comment itemComments[] = items[i].getAllComments();
            for (int j = 0; j < itemComments.length; ++j) {
                if (commentSet.contains(itemComments[j])) {
                    throw new IllegalArgumentException("Duplicate Comment references found.");
                }
                commentSet.add(itemComments[j]);
            }
        }
    }

    /**
     * Check if the given item is valid. An Item object is considered valid if:
     * <ol>
     * <li>question is positive;</li>
     * <li>answer is not null and of String type;</li>
     * <li>all the review item comments it contains are valid.</li>
     * </ol>
     * @param item
     *            the item to check
     * @throws IllegalArgumentException
     *             if the given item is invalid
     */
    private void assertItemValid(Item item) {
        Helper.assertLongPositive(item.getQuestion(), "question of item");

        // check if the answer is null or not of String type.
        if (!(item.getAnswer() instanceof String)) {
            throw new IllegalArgumentException("answer of item is null or not of String type.");
        }

        Comment[] comments = item.getAllComments();
        for (int i = 0; i < comments.length; ++i) {
            // comments[i] is never null.
            assertCommentValid(comments[i]);
        }
    }

    /**
     * Check if the given comment(review comment or review item comment) is
     * valid. An Comment object is considered valid if:
     * <ol>
     * <li>author is positive;</li>
     * <li>comment type is not null and valid;</li>
     * <li>content is not null;</li>
     * <li>extra info is of String type.</li>
     * </ol>
     * @param comment
     *            the comment to check
     * @throws IllegalArgumentException
     *             if the given comment is invalid
     */
    private void assertCommentValid(Comment comment) {
        Helper.assertLongPositive(comment.getAuthor(), "author of comment");

        Helper.assertObjectNotNull(comment.getCommentType(), "type of comment");
        assertCommentTypeValid(comment.getCommentType());

        Helper.assertObjectNotNull(comment.getComment(), "content of comment");

        if (comment.getExtraInfo() != null && !(comment.getExtraInfo() instanceof String)) {
            throw new IllegalArgumentException("extra info of comment is not of String type.");
        }
    }

    /**
     * Check if the given comment type is valid. An CommentType object is
     * considered valid if:
     * <ol>
     * <li>id is positive;</li>
     * <li>name is not null.</li>
     * </ol>
     * @param commentType
     *            the commentType to check
     * @throws IllegalArgumentException
     *             if the given comment type is invalid
     */
    private void assertCommentTypeValid(CommentType commentType) {
        Helper.assertLongPositive(commentType.getId(), "id of comment type");
        Helper.assertObjectNotNull(commentType.getName(), "name of comment type");
    }

    /**
     * Set the new entity ids finally according to the change table when no
     * exception occurred.
     * @param changeTable
     *            the change table
     */
    private void setEntityIds(Map changeTable) {
        // enumerate each pair in the change table.
        for (Iterator iter = changeTable.entrySet().iterator(); iter.hasNext();) {
            Entry entry = (Entry) iter.next();

            // get the new id.
            long newId = ((Long) entry.getValue()).longValue();

            // set the new Id according to the type of the object
            if (entry.getKey() instanceof Review) {
                ((Review) entry.getKey()).setId(newId);
            } else if (entry.getKey() instanceof Comment) {
                ((Comment) entry.getKey()).setId(newId);
            } else if (entry.getKey() instanceof Item) {
                ((Item) entry.getKey()).setId(newId);
            }
        }
    }

    /**
     * <p>
     * Create the review in the persistence. This method is also responsible for
     * creating the associated Comment, and Item, but not for CommentType.
     * </p>
     * <p>
     * This method will check if the given review is valid before accessing the
     * database.
     * </p>
     * <p>
     * A Review object is considered valid if:
     * <ol>
     * <li>author is positive;</li>
     * <li>submission is positive;</li>
     * <li>scorecard is positive;</li>
     * <li>all the review comments it contains are valid;</li>
     * <li>all the review items it contains are valid; </li>
     * <li>there are no duplicate entity references (review comments, review
     * items, review item comments) in it.</li>
     * </ol>
     * </p>
     * <p>
     * An Item object is considered valid if:
     * <ol>
     * <li>question is positive;</li>
     * <li>answer is not null and of String type;</li>
     * <li>all the review item comments it contains are valid.</li>
     * </ol>
     * </p>
     * <p>
     * An Comment object is considered valid if:
     * <ol>
     * <li>author is positive;</li>
     * <li>comment type is not null and valid;</li>
     * <li>content is not null;</li>
     * <li>extra info is null or of String type.</li>
     * </ol>
     * </p>
     * <p>
     * An CommentType object is considered valid if:
     * <ol>
     * <li>id is positive;</li>
     * <li>name is not null.</li>
     * </ol>
     * </p>
     * @param review
     *            the Review instance to create
     * @param operator
     *            the operator who creates the review instance
     * @exception IllegalArgumentException
     *                if either of arguments is null, or operator is empty
     *                string or review fails to pass the validation.
     * @exception DuplicateReviewEntityException
     *                if review id already exists.
     * @exception ReviewPersistenceException
     *                if any other error occurred.
     */
    public void createReview(Review review, String operator) throws DuplicateReviewEntityException,
        ReviewPersistenceException {
        Helper.assertObjectNotNull(review, "review");
        Helper.assertStringNotNullNorEmpty(operator, "operator");

        assertReviewValid(review);

        Connection conn = null;

        // create a change table to record the new Ids for Review, Item or
        // Comment.
        Map changeTable = new HashMap();

        // createDate will contain the create_date retrieved from database.
        Date createDate;

        LOGGER.log(Level.INFO, new LogMessage(null, operator, "creating new Review."));
        try {
            // create the connection
            conn = createTransactionalConnection();

            // check whether the review id is already in the database
            if (review.getId() > 0) {
                if (Helper.countEntities(REVIEW_TABLE, "review_id", review.getId(), conn) != 0) {
                	LOGGER.log(Level.ERROR, new LogMessage(new Long(review.getId()), operator,
                			"The review with same id [" + review.getId() + "] already exists."));
                    throw new DuplicateReviewEntityException("The review with same id ["
                        + review.getId() + "] already exists.", review.getId());
                }
            }

            // create the review.
            createReview(review, operator, conn, changeTable);

            // get the creation date.
            createDate = (Date) Helper.doSingleValueQuery(conn, "SELECT create_date FROM "
                + REVIEW_TABLE + " WHERE review_id=?", new Object[] {changeTable.get(review)},
                Helper.DATE_TYPE);

            Helper.commitTransaction(conn, LOGGER, useManualCommit);
        } catch (ReviewPersistenceException e) {
            LOGGER.log(Level.ERROR, new LogMessage(null, operator, "Error occurs during create new Review.", e));
            Helper.rollBackTransaction(conn, LOGGER, useManualCommit);
            throw e;
        } finally {
            Helper.closeConnection(conn , LOGGER);
        }

        // set the creation/modification user and date when no exception
        // occured.
        review.setCreationUser(operator);
        review.setCreationTimestamp(createDate);
        review.setModificationUser(operator);
        review.setModificationTimestamp(createDate);

        // set the new ids finally when no exception occured.
        setEntityIds(changeTable);
    }

    /**
     * Create the review in the database.
     * @param review
     *            the Review instance to create
     * @param operator
     *            the operator who creates the review instance
     * @param conn
     *            the transactional connection
     * @param changeTable
     *            the change table
     * @throws ReviewPersistenceException
     *             if any error occurs during the creation
     */
    private void createReview(Review review, String operator, Connection conn, Map changeTable)
        throws ReviewPersistenceException {

        Long newId;

        try {
            // generate id for the review
            newId = new Long(reviewIDGenerator.getNextID());
            LOGGER.log(Level.INFO, "Get new review id :" + newId);
        } catch (IDGenerationException e) {
            throw new ReviewPersistenceException("Unable to generate id for review.", e);
        }

        // add the review and newId pair to the change table.
        changeTable.put(review, newId);

        // insert the review into database
        Object[] queryArgs = new Object[] {newId, new Long(review.getAuthor()),
            new Long(review.getSubmission()), new Long(review.getProjectPhase()), new Long(review.getScorecard()),
            new Long(review.isCommitted() ? 1 : 0), review.getScore(), review.getInitialScore(), operator, operator};
        Helper.doDMLQuery(conn, CREATE_REVIEW_SQL, queryArgs);

        LOGGER.log(Level.INFO, "insert record into " + REVIEW_TABLE + " with new id:" + newId);

        // create review comments
        createReviewComments(review.getAllComments(), makeAscendingLongArray(review
            .getNumberOfComments()), newId, operator, conn, changeTable);

        // create review items
        createReviewItems(review.getAllItems(), makeAscendingLongArray(review.getNumberOfItems()),
            newId, operator, conn, changeTable);
    }

    /**
     * Create review comments in database.
     * @param comments
     *            the comments to create
     * @param indices
     *            the positions of each comment in their corresponding reviews
     * @param reviewId
     *            the review id containing these comments
     * @param operator
     *            the operator who creates the review instance
     * @param conn
     *            the transactional connection
     * @param changeTable
     *            the change table
     * @throws ReviewPersistenceException
     *             if any error occurs during the creation
     */
    private void createReviewComments(Comment[] comments, Long[] indices, Long reviewId,
        String operator, Connection conn, Map changeTable) throws ReviewPersistenceException {

        try {
            // enumerate each review comment
            for (int i = 0; i < comments.length; ++i) {
                Comment comment = comments[i];

                // generate id for the review comment
                Long newId = new Long(reviewCommentIDGenerator.getNextID());

                LOGGER.log(Level.INFO, "generate new review comment id :" + newId);
                // add the comment and newId pair to the change table.
                changeTable.put(comment, newId);

                // insert the review comment into database
                Object[] queryArgs = new Object[] {newId, new Long(comment.getAuthor()), reviewId,
                    new Long(comment.getCommentType().getId()), comment.getComment(),
                    comment.getExtraInfo(), indices[i], operator, operator};
                LOGGER.log(Level.INFO, "insert record into " + REVIEW_COMMENT_TABLE + " with new id:" + newId);
                Helper.doDMLQuery(conn, CREATE_REVIEW_COMMENT_SQL, queryArgs);
            }
        } catch (IDGenerationException e) {
            throw new ReviewPersistenceException("Unable to generate id for review comment.", e);
        }
    }

    /**
     * Create review items in database.
     * @param items
     *            the review items to create
     * @param indices
     *            the positions of each item in their corresponding reviews
     * @param reviewId
     *            the review id containing these items
     * @param operator
     *            the operator who creates the review instance
     * @param conn
     *            the transactional connection
     * @param changeTable
     *            the change table
     * @throws ReviewPersistenceException
     *             if any error occurs during the creation
     */
    private void createReviewItems(Item[] items, Long[] indices, Long reviewId, String operator,
        Connection conn, Map changeTable) throws ReviewPersistenceException {

        try {
            // enumerate each review item
            for (int i = 0; i < items.length; ++i) {
                Item item = items[i];

                // generate id for the review item
                Long newId = new Long(reviewItemIDGenerator.getNextID());

                LOGGER.log(Level.INFO, "generate new review Item id :" + newId);

                // add the item and newId pair into the change table.
                changeTable.put(item, newId);

                // insert the review item into database
                Object[] queryArgs = new Object[] {newId, reviewId, new Long(item.getQuestion()),
                    item.getDocument(), item.getAnswer(), indices[i], operator, operator};

                LOGGER.log(Level.INFO, "insert record into " + REVIEW_ITEM_TABLE + " with new id:" + newId);

                Helper.doDMLQuery(conn, CREATE_REVIEW_ITEM_SQL, queryArgs);

                // create review item comments
                createReviewItemComments(item.getAllComments(), makeAscendingLongArray(item
                    .getNumberOfComments()), newId, operator, conn, changeTable);
            }
        } catch (IDGenerationException e) {
            throw new ReviewPersistenceException("Unable to generate id for review item.", e);
        }
    }

    /**
     * Create review item comments in database.
     * @param comments
     *            the review item comments to create
     * @param indices
     *            the positions of each comment in their corresponding items
     * @param itemId
     *            the review item id containing these comments
     * @param operator
     *            the operator who creates the review instance
     * @param conn
     *            the transactional connection
     * @param changeTable
     *            the change table
     * @throws ReviewPersistenceException
     *             if any error occurs during the creation
     */
    private void createReviewItemComments(Comment[] comments, Long[] indices, Long itemId,
        String operator, Connection conn, Map changeTable) throws ReviewPersistenceException {

        try {
            // enumerate each review item comment
            for (int i = 0; i < comments.length; ++i) {
                Comment comment = comments[i];

                // generate id for the review item comment
                Long newId = new Long(reviewItemCommentIDGenerator.getNextID());

                LOGGER.log(Level.INFO, "generate new review Item comment id :" + newId);

                // add the comment and newId pair to the change table.
                changeTable.put(comment, newId);

                // insert the review item comment into database
                Object[] queryArgs = new Object[] {newId, new Long(comment.getAuthor()), itemId,
                    new Long(comment.getCommentType().getId()), comment.getComment(),
                    comment.getExtraInfo(), indices[i], operator, operator};

                LOGGER.log(Level.INFO, "insert record into " + REVIEW_ITEM_COMMENT_TABLE + " with new id:" + newId);

                Helper.doDMLQuery(conn, CREATE_REVIEW_ITEM_COMMENT_SQL, queryArgs);
            }
        } catch (IDGenerationException e) {
            throw new ReviewPersistenceException("Unable to generate id for review item comment.",
                e);
        }
    }

    /**
     * <p>
     * Update the review in the persistence. The update method is also
     * responsible for creating, deleting, updating the associated Items,
     * Comments, and Item Comments.
     * </p>
     * <p>
     * This method will check if the given review is valid before accessing the
     * database.
     * </p>
     * <p>
     * Please refer to the documentation for createReview method to see when a
     * review is considered to be valid.
     * </p>
     * @param review
     *            the Review instance to update
     * @param operator
     *            the operator who updates the Review instance
     * @exception IllegalArgumentException
     *                if either of arguments is null, operator is empty string,
     *                or review id is negative, or review fails to pass the
     *                validation.
     * @exception ReviewEntityNotFoundException
     *                if given review id does not exist
     * @exception ReviewPersistenceException
     *                if any other error occurred.
     */
    public void updateReview(Review review, String operator) throws ReviewEntityNotFoundException,
        ReviewPersistenceException {
        Helper.assertObjectNotNull(review, "review");
        Helper.assertStringNotNullNorEmpty(operator, "operator");

        Helper.assertLongPositive(review.getId(), "review id");
        assertReviewValid(review);

        Connection conn = null;

        // create a change table to record the new Ids for Review, Item or
        // Comment.
        Map changeTable = new HashMap();

        // modifyDate will contain the modify_date retrieved from database.
        Date modifyDate;

        LOGGER.log(Level.INFO, new LogMessage(new Long(review.getId()), operator, "Update Review."));

        try {
            // create the connection
            conn = createTransactionalConnection();

            // check whether the review id is already in the database
            if (Helper.countEntities(REVIEW_TABLE, "review_id", review.getId(), conn) == 0) {
            	LOGGER.log(Level.ERROR, new LogMessage(new Long(review.getId()), operator,
            			"The review id [" + review.getId() + "] does not exist in the database."));
            	throw new ReviewEntityNotFoundException("The review id [" + review.getId()
                    + "] does not exist in the database.", review.getId());
            }

            // update the review.
            updateReview(review, operator, conn, changeTable);

            // get the modification date.
            modifyDate = (Date) Helper.doSingleValueQuery(conn, "SELECT modify_date FROM "
                + REVIEW_TABLE + " WHERE review_id=?", new Object[] {new Long(review.getId())},
                Helper.DATE_TYPE);

            Helper.commitTransaction(conn, LOGGER, useManualCommit);
        } catch (ReviewPersistenceException e) {
            LOGGER.log(Level.ERROR, new LogMessage(new Long(review.getId()), operator, "Error occurs during update Review.", e));
            Helper.rollBackTransaction(conn, LOGGER, useManualCommit);
            throw e;
        } finally {
            Helper.closeConnection(conn, LOGGER);
        }

        // set the modification user and date when no exception
        // occurred.
        review.setModificationUser(operator);
        review.setModificationTimestamp(modifyDate);

        // set the new ids finally when no exception occurred.
        setEntityIds(changeTable);
    }

    /**
     * Update the review in the database.
     * @param review
     *            the review to update
     * @param operator
     *            the operator who creates the review instance
     * @param conn
     *            the transactional connection
     * @param changeTable
     *            the change table
     * @throws ReviewPersistenceException
     *             if any error occurs during the update
     */
    private void updateReview(Review review, String operator, Connection conn, Map changeTable)
        throws ReviewPersistenceException {

        Long reviewId = new Long(review.getId());

        // update the review item in database
        Object[] queryArgs = new Object[] {new Long(review.getAuthor()),
            new Long(review.getSubmission()), new Long(review.getProjectPhase()), new Long(review.getScorecard()),
            new Long(review.isCommitted() ? 1 : 0), review.getScore(), review.getInitialScore(), operator, reviewId};

        LOGGER.log(Level.INFO, "update record in the  " + REVIEW_TABLE + " table with id:" + reviewId.longValue());

        Helper.doDMLQuery(conn, UPDATE_REVIEW_SQL, queryArgs);

        // update review comments
        updateReviewComments(review.getAllComments(), makeAscendingLongArray(review
            .getNumberOfComments()), reviewId, operator, conn, changeTable);

        // update review items
        updateReviewItems(review.getAllItems(), makeAscendingLongArray(review.getNumberOfItems()),
            reviewId, operator, conn, changeTable);
    }

    /**
     * Update review comments in the database.
     * @param comments
     *            the review comments to update
     * @param indices
     *            the positions of each comment in their corresponding reviews
     * @param reviewId
     *            the review id containing these comments
     * @param operator
     *            the operator who creates the review instance
     * @param conn
     *            the transactional connection
     * @param changeTable
     *            the change table
     * @throws ReviewPersistenceException
     *             if any error occurs during the update
     */
    private void updateReviewComments(Comment[] comments, Long[] indices, Long reviewId,
        String operator, Connection conn, Map changeTable) throws ReviewPersistenceException {

        // get a set containing all the review comment ids
        // associated with this review id
        Set reviewCommentIDs = getReviewCommentIDs(reviewId, conn);

        // create a list containing all the review comments that should be
        // created and another list containing the indices of them.
        List createReviewList = new ArrayList();
        List createIndexList = new ArrayList();

        // enumerate each comment
        for (int i = 0; i < comments.length; ++i) {
            Comment comment = comments[i];

            Long commentId = new Long(comment.getId());

            // check if the comment id already exists in the database
            if (reviewCommentIDs.contains(commentId)) {
                reviewCommentIDs.remove(commentId);

                // update the review comment
                updateReviewComment(comment, indices[i], operator, conn);
            } else {
                // add to create list
                createReviewList.add(comment);
                createIndexList.add(indices[i]);
            }
        }

        // create the review comments in the createList
        createReviewComments((Comment[]) createReviewList.toArray(new Comment[] {}),
            (Long[]) createIndexList.toArray(new Long[] {}), reviewId, operator, conn, changeTable);

        // delete the reviews comments left in the reviewCommentIDs set
        deleteReviewComments(reviewCommentIDs, conn);
    }

    /**
     * Update the review comment in the database.
     * @param comment
     *            the review comment to update
     * @param index
     *            the position of the comment in its review
     * @param operator
     *            the operator who creates the review instance
     * @param conn
     *            the transactional connection
     * @throws ReviewPersistenceException
     *             if any error occurs during the update
     */
    private void updateReviewComment(Comment comment, Long index, String operator, Connection conn)
        throws ReviewPersistenceException {

        // update the review comment in database
        Object[] queryArgs = new Object[] {new Long(comment.getAuthor()),
            new Long(comment.getCommentType().getId()), comment.getComment(),
            comment.getExtraInfo(), index, operator, new Long(comment.getId())};

        LOGGER.log(Level.INFO, "update record in the " + REVIEW_COMMENT_TABLE + " table with id:" + comment.getId());

        Helper.doDMLQuery(conn, UPDATE_REVIEW_COMMENT_SQL, queryArgs);
    }

    /**
     * Update review items in the database.
     * @param items
     *            the review items to update
     * @param indices
     *            the positions of each item in their corresponding reviews
     * @param reviewId
     *            the review id containing these items
     * @param operator
     *            the operator who creates the review instance
     * @param conn
     *            the transactional connection
     * @param changeTable
     *            the change table
     * @throws ReviewPersistenceException
     *             if any error occurs during the update
     */
    private void updateReviewItems(Item[] items, Long[] indices, Long reviewId, String operator,
        Connection conn, Map changeTable) throws ReviewPersistenceException {

        // get a set containing all the review item ids
        // associated with this review id
        Set reviewItemIDs = getReviewItemIDs(reviewId, conn);

        // create a list containing all the review items that should be
        // created and another list containing the indices of them.
        List createItemList = new ArrayList();
        List createIndexList = new ArrayList();

        // enumerate each item
        for (int i = 0; i < items.length; ++i) {
            Item item = items[i];

            Long itemId = new Long(item.getId());

            // check if the item id already exists in the database
            if (reviewItemIDs.contains(itemId)) {
                reviewItemIDs.remove(itemId);

                // update the review item
                updateReviewItem(item, indices[i], operator, conn, changeTable);
            } else {
                // add to create list
                createItemList.add(item);
                createIndexList.add(indices[i]);
            }
        }

        // create the review items in the create list
        createReviewItems((Item[]) createItemList.toArray(new Item[] {}), (Long[]) createIndexList
            .toArray(new Long[] {}), reviewId, operator, conn, changeTable);

        // delete the review items left in the reviewItemIDs set
        deleteReviewItems(reviewItemIDs, conn);
    }

    /**
     * Update review item in the database.
     * @param item
     *            the review item to update
     * @param index
     *            the position of the item in its review
     * @param operator
     *            the operator who creates the review instance
     * @param conn
     *            the transactional connection
     * @param changeTable
     *            the change table
     * @throws ReviewPersistenceException
     *             if any error occurs during the update
     */
    private void updateReviewItem(Item item, Long index, String operator, Connection conn,
        Map changeTable) throws ReviewPersistenceException {

    	LOGGER.log(Level.INFO, "execute sql:" + UPDATE_REVIEW_ITEM_SQL);

        Long itemId = new Long(item.getId());

        // update the review item in database
        Object[] queryArgs = new Object[] {new Long(item.getQuestion()), item.getDocument(),
            item.getAnswer(), index, operator, itemId};
        Helper.doDMLQuery(conn, UPDATE_REVIEW_ITEM_SQL, queryArgs);

        // update the review item comments
        updateReviewItemComments(item.getAllComments(), makeAscendingLongArray(item
            .getNumberOfComments()), itemId, operator, conn, changeTable);
    }

    /**
     * Update review item comments in the database.
     * @param comments
     *            the review item comments to update
     * @param indices
     *            the positions of each comment in their corresponding items
     * @param itemId
     *            the review item id containing these comments
     * @param operator
     *            the operator who creates the review instance
     * @param conn
     *            the transactional connection
     * @param changeTable
     *            the change table
     * @throws ReviewPersistenceException
     *             if any error occurs during the update
     */
    private void updateReviewItemComments(Comment[] comments, Long[] indices, Long itemId,
        String operator, Connection conn, Map changeTable) throws ReviewPersistenceException {

        // get a set containing all the review item comment ids
        // associated with this review id
        Set reviewItemCommentIDs = getReviewItemCommentIDs(itemId, conn);

        // create a list containing all the review item comments that should be
        // created and another list containing the indices of them.
        List createCommentList = new ArrayList();
        List createIndexList = new ArrayList();

        // enumerate each comment
        for (int i = 0; i < comments.length; ++i) {
            Comment comment = comments[i];

            Long commentId = new Long(comment.getId());

            // check if the comment id already exists in the database
            if (reviewItemCommentIDs.contains(commentId)) {
                reviewItemCommentIDs.remove(commentId);

                // update the review item comment
                updateReviewItemComment(comment, indices[i], operator, conn);
            } else {
                // add to create list
                createCommentList.add(comment);
                createIndexList.add(indices[i]);
            }
        }

        // create the review item comments in the create list
        createReviewItemComments((Comment[]) createCommentList.toArray(new Comment[] {}),
            (Long[]) createIndexList.toArray(new Long[] {}), itemId, operator, conn, changeTable);

        // delete the reviews item comments left in the reviewCommentIDs set
        deleteReviewItemComments(reviewItemCommentIDs, conn);
    }

    /**
     * Update the review item comment in the database.
     * @param comment
     *            the review item comment to update
     * @param index
     *            the position of the comment in its item
     * @param operator
     *            the operator who creates the review instance
     * @param conn
     *            the transactional connection
     * @throws ReviewPersistenceException
     *             if any error occurs during the update
     */
    private void updateReviewItemComment(Comment comment, Long index, String operator,
	        Connection conn) throws ReviewPersistenceException {
    	LOGGER.log(Level.INFO, "execute sql:" + UPDATE_REVIEW_ITEM_COMMENT_SQL);

        // update the review item comment in database
        Object[] queryArgs = new Object[] {new Long(comment.getAuthor()),
            new Long(comment.getCommentType().getId()), comment.getComment(),
            comment.getExtraInfo(), index, operator, new Long(comment.getId())};
        Helper.doDMLQuery(conn, UPDATE_REVIEW_ITEM_COMMENT_SQL, queryArgs);
    }

    /**
     * get all the review comment ids associated with the review id.
     * @param reviewId
     *            the given review id
     * @param conn
     *            the transactional connection
     * @return A set that contains the review comment ids
     * @throws ReviewPersistenceException
     *             if any error occurs during the query
     */
    private Set getReviewCommentIDs(Long reviewId, Connection conn)
        throws ReviewPersistenceException {

        Object[][] rows = Helper.doQuery(conn, QUERY_REVIEW_COMMENT_IDS_SQL,
            new Object[] {reviewId}, new DataType[] {Helper.LONG_TYPE});

        // build the set from result list
        Set reviewCommentIDs = new HashSet();
        for (int i = 0; i < rows.length; ++i) {
            reviewCommentIDs.add(rows[i][0]);
        }

        return reviewCommentIDs;
    }

    /**
     * get all the review item ids associated with the review id.
     * @param reviewId
     *            the given review id
     * @param conn
     *            the transactional connection
     * @return A set that contains the review item ids
     * @throws ReviewPersistenceException
     *             if any error occurs during the query
     */
    private Set getReviewItemIDs(Long reviewId, Connection conn) throws ReviewPersistenceException {

        Object[][] rows = Helper.doQuery(conn, QUERY_REVIEW_ITEM_IDS_SQL, new Object[] {reviewId},
            new DataType[] {Helper.LONG_TYPE});

        // build the set from result list
        Set reviewItemIDs = new HashSet();
        for (int i = 0; i < rows.length; ++i) {
            reviewItemIDs.add(rows[i][0]);
        }

        return reviewItemIDs;
    }

    /**
     * get all the review item comment ids associated with the item id.
     * @param itemId
     *            the given item id
     * @param conn
     *            the transactional connection
     * @return A set that contains the review item comment ids
     * @throws ReviewPersistenceException
     *             if any error occurs during the query
     */
    private Set getReviewItemCommentIDs(Long itemId, Connection conn)
        throws ReviewPersistenceException {

    	LOGGER.log(Level.INFO, "execute sql:" + QUERY_REVIEW_ITEM_COMMENT_IDS_SQL);

        Object[][] rows = Helper.doQuery(conn, QUERY_REVIEW_ITEM_COMMENT_IDS_SQL,
            new Object[] {itemId}, new DataType[] {Helper.LONG_TYPE});

        // build the set from result list
        Set reviewItemCommentIDs = new HashSet();
        for (int i = 0; i < rows.length; ++i) {
            reviewItemCommentIDs.add(rows[i][0]);
        }

        return reviewItemCommentIDs;
    }

    /**
     * Make an ascending Long array starting from 0 with step 1.
     * @param length
     *            the length of the generated array
     * @return an ascending Long array
     */
    private Long[] makeAscendingLongArray(int length) {
        Long[] array = new Long[length];

        for (int i = 0; i < array.length; ++i) {
            array[i] = new Long(i);
        }

        return array;
    }

    /**
     * Delete review comment ids in the database.
     * @param reviewCommentIDs
     *            A set that contains review comment ids to delete
     * @param conn
     *            the transactional connection
     * @throws ReviewPersistenceException
     *             if any error occurs during the deletion
     */
    private void deleteReviewComments(Set reviewCommentIDs, Connection conn)
        throws ReviewPersistenceException {

        // enumerate each id
        for (Iterator it = reviewCommentIDs.iterator(); it.hasNext();) {
        	Long id = (Long) it.next();

        	LOGGER.log(Level.INFO, "delete entry from "
        			+ REVIEW_COMMENT_TABLE + " with the review comment id:" + id.longValue());

        	Helper.deleteEntities(REVIEW_COMMENT_TABLE, "review_comment_id", id.longValue(), conn);
        }
    }

    /**
     * Delete review item ids in the database.
     * @param reviewItemIDs
     *            A set that contains review item ids to delete
     * @param conn
     *            the transactional connection
     * @throws ReviewPersistenceException
     *             if any error occurs during the deletion
     */
    private void deleteReviewItems(Set reviewItemIDs, Connection conn)
        throws ReviewPersistenceException {

        // enumerate each id
        for (Iterator it = reviewItemIDs.iterator(); it.hasNext();) {
            long itemId = ((Long) it.next()).longValue();

            LOGGER.log(Level.INFO, "delete entry from "
        			+ REVIEW_ITEM_COMMENT_TABLE + " with the review item id:" + itemId);

            Helper.deleteEntities(REVIEW_ITEM_COMMENT_TABLE, "review_item_id", itemId, conn);

            LOGGER.log(Level.INFO, "delete entry from "
        			+ REVIEW_ITEM_TABLE + " with the review item id:" + itemId);

            Helper.deleteEntities(REVIEW_ITEM_TABLE, "review_item_id", itemId, conn);
        }
    }

    /**
     * Delete review item comment ids in the database.
     * @param reviewItemCommentIDs
     *            A set that contains review item comment ids to delete
     * @param conn
     *            the transactional connection
     * @throws ReviewPersistenceException
     *             if any error occurs during the deletion
     */
    private void deleteReviewItemComments(Set reviewItemCommentIDs, Connection conn)
        throws ReviewPersistenceException {

        // enumerate each id
        for (Iterator it = reviewItemCommentIDs.iterator(); it.hasNext();) {

        	long id = ((Long) it.next()).longValue();

        	LOGGER.log(Level.INFO, "delete entry from "
        			+ REVIEW_ITEM_COMMENT_TABLE + " with the review_item_comment_id:" + id);

            Helper.deleteEntities(REVIEW_ITEM_COMMENT_TABLE, "review_item_comment_id", id, conn);
        }
    }

    /**
     * <p>
     * Get the Review instance from the persistence with given id.
     * </p>
     * @param id
     *            the review id
     * @return the Review instance matching the given id
     * @exception IllegalArgumentException
     *                if id is not positive
     * @exception ReviewEntityNotFoundException
     *                if given id does not exist in the database
     * @exception ReviewPersistenceException
     *                if any other error occurred.
     */
    public Review getReview(long id) throws ReviewEntityNotFoundException,
        ReviewPersistenceException {
        Helper.assertLongPositive(id, "id");

        Connection conn = null;

        LOGGER.log(Level.INFO, new LogMessage(new Long(id), null, "Retrieve Review."));

        try {
            // create the connection
            conn = createTransactionalConnection();

            // check whether the review id is already in the database
            if (Helper.countEntities(REVIEW_TABLE, "review_id", id, conn) == 0) {
            	LOGGER.log(Level.ERROR, new LogMessage(new Long(id), null,
            			"The review id [" + id + "] does not exist in the database."));

                throw new ReviewEntityNotFoundException("The review id [" + id
                    + "] does not exist in the database.", id);
            }

            // get the review in the database
            Review review = getReviewsComplete(new Long(id).toString(), conn)[0];

            Helper.commitTransaction(conn, LOGGER, useManualCommit);
            return review;
        } catch (ReviewPersistenceException e) {
            LOGGER.log(Level.ERROR, new LogMessage(new Long(id), null, "Error occurs during retreive Review.", e));
            Helper.rollBackTransaction(conn, LOGGER, useManualCommit);
            throw e;
        } finally {
            Helper.closeConnection(conn, LOGGER);
        }
    }

    /**
     * <p>
     * Search for the matching review in the persistence with given filter. If
     * complete is false, the associated items and comments of the matching
     * review will not be retrieved.
     * </p>
     * <p>
     * In the version 1.0, the filter supports at most five fields:
     * <ol>
     * <li>scorecardType --- the score card type, must be java.Long type</li>
     * <li>submission --- the review submission id, must be java.Long type</li>
     * <li>reviewer --- the author of the review, must be java.Long type</li>
     * <li>project --- the project id of the review, must be java.Long type</li>
     * <li>committed --- indicate if the review has been committed, must be
     * java.lang.Integer type. Either new Integer(1) representing committed, or
     * new Integer(0), represent not committed</li>
     * </ol>
     * </p>
     * @param filter
     *            the filter that specified the filter condition
     * @param complete
     *            a boolean indicate if the complete review data will be
     *            retrieved
     * @return an array of review matching the given filter
     * @exception IllegalArgumentException
     *                if filter is null
     * @exception ReviewPersistenceException
     *                if failed to search the reviews.
     */
    public Review[] searchReviews(Filter filter, boolean complete) throws ReviewPersistenceException {
        Helper.assertObjectNotNull(filter, "filter");

        List reviewsList = new ArrayList();

		LOGGER.log(Level.INFO, new LogMessage(null, null,
        		"Search " +( complete ? " complete " : " not completed" )+ " Reviews "));
        try {
            // do the search using search bundle
            CustomResultSet resultSet = (CustomResultSet) searchBundle.search(filter);

            if (resultSet.getRecordCount() == 0) {
                return new Review[0];
            }

            while (resultSet.next()) {
                reviewsList.add(getReview(resultSet));
            }
        } catch (InvalidCursorStateException icse) {
            throw new ReviewPersistenceException("Error retrieving review from the result set.", icse);
        } catch (NullColumnValueException ne) {
            throw new ReviewPersistenceException("Error retrieving review from the result set.", ne);
        } catch (SearchBuilderException sbe) {
            throw new ReviewPersistenceException("Error searching the reviews.", sbe);
        }

        Review[] reviews = (Review[]) reviewsList.toArray(new Review[reviewsList.size()]);

        if (!complete) {
            return reviews;
        }

        StringBuffer idList = new StringBuffer();
        Connection conn = null;

        for (int i = 0; i < reviews.length; ++i) {
            if (i != 0) {
                idList.append(",");
            }
            idList.append(reviews[i].getId());
        }

        try {
            // create the connection
            conn = createTransactionalConnection();

            // get the Id-CommentType map
            Map commentTypeMap = makeIdCommentTypeMap(getAllCommentTypes(conn));
            // get the Id-Review Map
            Map reviewMap = makeIdReviewMap(reviews);
            String idsList = idList.toString();

            // get the review comments
            getReviewComments(idsList, conn, reviewMap, commentTypeMap);

            // get the review items
            Item[] items = getReviewItems(idsList, conn, reviewMap);

            // get the review item comments
            getReviewItemComments(idsList, conn, makeIdItemMap(items), commentTypeMap);

            return reviews;
        } catch (ReviewPersistenceException e) {
            LOGGER.log(Level.ERROR, new LogMessage(null, null, "Error occurs during search/retrieve Review.", e));
            Helper.rollBackTransaction(conn, LOGGER, useManualCommit);
            throw e;
        } finally {
            Helper.closeConnection(conn, LOGGER);
        }
    }

    /**
     * Get the reviews in the id list from database.
     * @param idList
     *            the id list string containing the review ids
     * @param conn
     *            the transactional connection
     * @return A Review array that contains the reviews get
     * @throws ReviewPersistenceException
     *             if any error occurs during the query
     */
    private Review[] getReviews(String idList, Connection conn) throws ReviewPersistenceException {


        // find the reviews with review id in idList in the table
        DataType[] columnTypes = new DataType[] {Helper.LONG_TYPE, Helper.LONG_TYPE, Helper.LONG_TYPE,
            Helper.LONG_TYPE, Helper.LONG_TYPE, Helper.LONG_TYPE, Helper.FLOAT_TYPE, Helper.FLOAT_TYPE,
            Helper.STRING_TYPE, Helper.DATE_TYPE, Helper.STRING_TYPE, Helper.DATE_TYPE};
        Object[][] rows = Helper.doQuery(conn, QUERY_REVIEWS_SQL.replaceFirst(
            ID_ARRAY_PARAMETER_REGULAR_EXP, idList), new Object[] {}, columnTypes);

        // create the Review array.
        Review[] reviews = new Review[rows.length];
        for (int i = 0; i < rows.length; ++i) {
            Object[] row = rows[i];

            // create a new review object.
            Review review = new Review();

            review.setId(((Long) row[0]).longValue());
            review.setAuthor(((Long) row[1]).longValue());
            review.setSubmission(((Long) row[2]).longValue());
			review.setProjectPhase(((Long) row[3]).longValue());
            review.setScorecard(((Long) row[4]).longValue());
            review.setCommitted(((Long) row[5]).longValue() != 0);
            review.setScore((Float) row[6]);
            review.setInitialScore((Float) row[7]);
            review.setCreationUser((String) row[8]);
            review.setCreationTimestamp((Date) row[9]);
            review.setModificationUser((String) row[10]);
            review.setModificationTimestamp((Date) row[11]);

            // assign the current review to the array.
            reviews[i] = review;
        }

        return reviews;
    }

    /**
     * Get the reviews in the id list from database, including all the review
     * entities associated to it.
     * @param idList
     *            the id list string containing the review ids
     * @param conn
     *            the transactional connection
     * @return A Review array that contains the reviews get
     * @throws ReviewPersistenceException
     *             if any error occurs during the query
     */
    private Review[] getReviewsComplete(String idList, Connection conn)
        throws ReviewPersistenceException {

        // get the reviews from the id list string
        Review[] reviews = getReviews(idList, conn);

        // get the Id-CommentType map
        Map commentTypeMap = makeIdCommentTypeMap(getAllCommentTypes(conn));
        // get the Id-Review Map
        Map reviewMap = makeIdReviewMap(reviews);

        // get the review comments
        getReviewComments(idList, conn, reviewMap, commentTypeMap);

        // get the review items
        Item[] items = getReviewItems(idList, conn, reviewMap);

        // get the review item comments
        getReviewItemComments(idList, conn, makeIdItemMap(items), commentTypeMap);

        return reviews;
    }

    /**
     * Creates a review from result set's row.
     *
     * @return created review.
     * @param resultSet
     *            a result set pointing to the row describing new review to create
     * @throws InvalidCursorStateException
     *             if any error occurs reading the result set.
     */
    private Review getReview(CustomResultSet resultSet) throws InvalidCursorStateException, NullColumnValueException {
        Review review = new Review();

        review.setId(resultSet.getLong("review_id"));
        review.setAuthor(resultSet.getLong("resource_id"));
        review.setSubmission(resultSet.getLong("submission_id"));
		review.setProjectPhase(resultSet.getLong("project_phase_id"));
        review.setScorecard(resultSet.getLong("scorecard_id"));
        review.setCommitted(resultSet.getLong("committed") != 0);
        if (resultSet.getObject("score") != null) {
            review.setScore(new Float(resultSet.getFloat("score")));
        } else {
            review.setScore(null);
        }
        if (resultSet.getObject("initial_score") != null) {
            review.setInitialScore(new Float(resultSet.getFloat("initial_score")));
        } else {
            review.setInitialScore(null);
        }
        review.setCreationUser(resultSet.getString("create_user"));
        review.setCreationTimestamp(resultSet.getDate("create_date"));
        review.setModificationUser(resultSet.getString("modify_user"));
        review.setModificationTimestamp(resultSet.getDate("modify_date"));

        return review;
    }

    /**
     * Get the review comments whose review id is in the id list from database,
     * and add them to the corrsponding review objects.
     * @param idList
     *            the id list string containing the review ids
     * @param conn
     *            the transactional connection
     * @param reviewMap
     *            the Id-Review map
     * @param commentTypeMap
     *            the Id-CommentType map
     * @throws ReviewPersistenceException
     *             if any error occurs during the query
     */
    private void getReviewComments(String idList, Connection conn, Map reviewMap, Map commentTypeMap)
        throws ReviewPersistenceException {

        // find the review comments with review id in idList in the table
        DataType[] columnTypes = new DataType[] {Helper.LONG_TYPE, Helper.LONG_TYPE,
            Helper.LONG_TYPE, Helper.LONG_TYPE, Helper.STRING_TYPE, Helper.STRING_TYPE};
        Object[][] rows = Helper.doQuery(conn, QUERY_REVIEW_COMMENTS_SQL.replaceFirst(
            ID_ARRAY_PARAMETER_REGULAR_EXP, idList), new Object[] {}, columnTypes);

        // enumerate each comment
        for (int i = 0; i < rows.length; ++i) {
            Object[] row = rows[i];

            Comment comment = new Comment();
            comment.setId(((Long) row[0]).longValue());
            comment.setAuthor(((Long) row[1]).longValue());
            // add to the corresponding review object.
            ((Review) reviewMap.get(row[2])).addComment(comment);
            comment.setCommentType((CommentType) commentTypeMap.get(row[3]));
            comment.setComment((String) row[4]);
            comment.setExtraInfo((String) row[5]);
        }
    }

    /**
     * Get the review items whose review id is in the id list from database, and
     * add them to the corresponding review objects.
     * @param idList
     *            the id list string containing the review ids
     * @param conn
     *            the transactional connection
     * @param reviewMap
     *            the Id-Review map
     * @return A Comment array that contains the review items get
     * @throws ReviewPersistenceException
     *             if any error occurs during the query
     */
    private Item[] getReviewItems(String idList, Connection conn, Map reviewMap)
        throws ReviewPersistenceException {

        // find the review items with review id in idList in the table
        DataType[] columnTypes = new DataType[] {Helper.LONG_TYPE, Helper.LONG_TYPE,
            Helper.LONG_TYPE, Helper.LONG_TYPE, Helper.STRING_TYPE};
        Object[][] rows = Helper.doQuery(conn, QUERY_REVIEW_ITEMS_SQL.replaceFirst(
            ID_ARRAY_PARAMETER_REGULAR_EXP, idList), new Object[] {}, columnTypes);

        // create the Item array.
        Item[] items = new Item[rows.length];
        for (int i = 0; i < rows.length; ++i) {
            Object[] row = rows[i];

            // create a new Item object.
            Item item = new Item();

            item.setId(((Long) row[0]).longValue());
            // add to the corresponding review object.
            ((Review) reviewMap.get(row[1])).addItem(item);
            item.setQuestion(((Long) row[2]).longValue());
            item.setDocument((Long) row[3]);
            item.setAnswer((String) row[4]);

            // assign the current Item object to the array.
            items[i] = item;
        }

        return items;
    }

    /**
     * Get the review item comments which are associated with any review id in
     * the id list from database and add them to the corresponding item objects.
     * @param idList
     *            the id list string containing the review ids
     * @param conn
     *            the transactional connection
     * @param itemMap
     *            the Id-Item map
     * @param commentTypeMap
     *            the Id-CommentType map
     * @throws ReviewPersistenceException
     *             if any error occurs during the query
     */
    private void getReviewItemComments(String idList, Connection conn, Map itemMap,
        Map commentTypeMap) throws ReviewPersistenceException {

        // find the review comments with review id in idList in the table
        DataType[] columnTypes = new DataType[] {Helper.LONG_TYPE, Helper.LONG_TYPE,
            Helper.LONG_TYPE, Helper.LONG_TYPE, Helper.STRING_TYPE, Helper.STRING_TYPE};
        Object[][] rows = Helper.doQuery(conn, QUERY_REVIEW_ITEM_COMMENTS_SQL.replaceFirst(
            ID_ARRAY_PARAMETER_REGULAR_EXP, idList), new Object[] {}, columnTypes);

        // enumerate each comment
        for (int i = 0; i < rows.length; ++i) {
            Object[] row = rows[i];

            Comment comment = new Comment();
            comment.setId(((Long) row[0]).longValue());
            comment.setAuthor(((Long) row[1]).longValue());
            // add to the corresponding item object.
            ((Item) itemMap.get(row[2])).addComment(comment);
            comment.setCommentType((CommentType) commentTypeMap.get(row[3]));
            comment.setComment((String) row[4]);
            comment.setExtraInfo((String) row[5]);
        }
    }

    /**
     * Make an Id-CommentType map from CommentType[].
     * @param commentTypes
     *            the CommentType array
     * @return an Id-CommentType map
     */
    private Map makeIdCommentTypeMap(CommentType[] commentTypes) {
        Map map = new HashMap();

        for (int i = 0; i < commentTypes.length; ++i) {
            map.put(new Long(commentTypes[i].getId()), commentTypes[i]);
        }
        return map;
    }

    /**
     * Make an Id-Item map from Item[].
     * @param items
     *            the Item array
     * @return an Id-Item map
     */
    private Map makeIdItemMap(Item[] items) {
        Map map = new HashMap();

        for (int i = 0; i < items.length; ++i) {
            map.put(new Long(items[i].getId()), items[i]);
        }
        return map;
    }

    /**
     * Make an Id-Review map from Review[].
     * @param reviews
     *            the Review array
     * @return an Id-Review map
     */
    private Map makeIdReviewMap(Review[] reviews) {
        Map map = new HashMap();

        for (int i = 0; i < reviews.length; ++i) {
            map.put(new Long(reviews[i].getId()), reviews[i]);
        }
        return map;
    }

    /**
     * <p>
     * Get all the CommentType instance from the persistence. Return empty array
     * if no type is found.
     * </p>
     * @return all the CommentType instance from the persistence
     * @exception ReviewPersistenceException
     *                if failed to get the types
     */
    public CommentType[] getAllCommentTypes() throws ReviewPersistenceException {
        Connection conn = null;

        LOGGER.log(Level.INFO, new LogMessage(null, null, " retrieve all comment types."));

        try {
            // create the connection
            conn = createTransactionalConnection();

            CommentType[] commentTypes = getAllCommentTypes(conn);

            Helper.commitTransaction(conn, LOGGER, useManualCommit);
            return commentTypes;
        } catch (ReviewPersistenceException e) {
            LOGGER.log(Level.ERROR, new LogMessage(null, null, "Error occurs retrieve all comment types .", e));
            Helper.rollBackTransaction(conn, LOGGER, useManualCommit);
            throw e;
        } finally {
            Helper.closeConnection(conn, LOGGER);
        }
    }

    /**
     * Get all the comment types in the database.
     * @param conn
     *            the transactional connection
     * @return A CommentType array that contains all the comment types
     * @throws ReviewPersistenceException
     *             if any error occurs during the query
     */
    private CommentType[] getAllCommentTypes(Connection conn) throws ReviewPersistenceException {

        // find all comment types in the table
        Object[][] rows = Helper.doQuery(conn, QUERY_ALL_COMMENT_TYPES_SQL, new Object[] {},
            new DataType[] {Helper.LONG_TYPE, Helper.STRING_TYPE});

        // create the CommentType array.
        CommentType[] commentTypes = new CommentType[rows.length];
        for (int i = 0; i < rows.length; ++i) {
            Object[] row = rows[i];

            commentTypes[i] = new CommentType(((Long) row[0]).longValue(), (String) row[1]);
        }

        return commentTypes;
    }

    /**
     * <p>
     * Add the comment review with given review id.
     * </p>
     * <p>
     * Please refer to the documentation for createReview method to see when a
     * comment is considered to be valid.
     * </p>
     * @param reviewId
     *            representing the review id
     * @param comment
     *            representing the comment
     * @param operator
     *            representing the operator
     * @exception IllegalArgumentException
     *                if comment or operator is null, reviewId is not positive,
     *                operator is empty string, or comment is invalid.
     * @exception ReviewEntityNotFoundException
     *                if reviewId does not exists
     * @exception ReviewPersistenceException
     *                if any other error occurred.
     */
    public void addReviewComment(long reviewId, Comment comment, String operator)
        throws ReviewEntityNotFoundException, ReviewPersistenceException {
        Helper.assertLongPositive(reviewId, "reviewId");
        Helper.assertObjectNotNull(comment, "comment");
        Helper.assertStringNotNullNorEmpty(operator, "operator");

        assertCommentValid(comment);

        // create a change table to record the new Ids for Comment.
        Map changeTable = new HashMap();

        Connection conn = null;

        LOGGER.log(Level.INFO, new LogMessage(new Long(reviewId), operator, "Add comment to the review."));

        try {
            // create the connection
            conn = createTransactionalConnection();

            // check if the review id exists
            if (Helper.countEntities(REVIEW_TABLE, "review_id", reviewId, conn) == 0) {
            	LOGGER.log(Level.ERROR, new LogMessage(new Long(reviewId), operator,
            			"The review id [" + reviewId + "] does not exist in the database."));
                throw new ReviewEntityNotFoundException("The review id [" + reviewId
                    + "] does not exist in table [" + REVIEW_TABLE + "].", reviewId);
            }

            // get # of comments for this review in the database, which equals
            // to the position of the current comment in its review.
            Long index = new Long(Helper.countEntities(REVIEW_COMMENT_TABLE, "review_id", reviewId,
                conn));

            // create the comment
            createReviewComments(new Comment[] {comment}, new Long[] {index}, new Long(reviewId),
                operator, conn, changeTable);

            Helper.commitTransaction(conn, LOGGER, useManualCommit);
        } catch (ReviewPersistenceException e) {
            LOGGER.log(Level.ERROR, new LogMessage(new Long(reviewId), operator, 
                "Error occurs adding comment to review .", e));
            Helper.rollBackTransaction(conn, LOGGER, useManualCommit);
            throw e;
        } finally {
            Helper.closeConnection(conn, LOGGER);
        }

        // set the comment id when no exception occurred.
        setEntityIds(changeTable);
    }

    /**
     * <p>
     * Add the comment to the item with given item id.
     * </p>
     * <p>
     * Please refer to the documentation for createReview method to see when a
     * comment is considered to be valid.
     * </p>
     * @param itemId
     *            the id of the item
     * @param comment
     *            the Comment instance
     * @param operator
     *            the operator
     * @exception IllegalArgumentException
     *                if comment or operator is null, or itemId is not positive,
     *                or operator is empty string, or comment is invalid.
     * @exception ReviewEntityNotFoundException
     *                if itemId does not exists
     * @exception ReviewPersistenceException
     *                if any other error occurred.
     */
    public void addItemComment(long itemId, Comment comment, String operator)
        throws ReviewEntityNotFoundException, ReviewPersistenceException {
        Helper.assertLongPositive(itemId, "itemId");
        Helper.assertObjectNotNull(comment, "comment");
        Helper.assertStringNotNullNorEmpty(operator, "operator");

        assertCommentValid(comment);

        // create a change table to record the new Ids for Comment.
        Map changeTable = new HashMap();

        Connection conn = null;

        LOGGER.log(Level.INFO, new LogMessage(null, operator, "Add comment to the Item with the itemid:" + itemId));

        try {
            // create the connection
            conn = createTransactionalConnection();

            // check if the review item id exists
            if (Helper.countEntities(REVIEW_ITEM_TABLE, "review_item_id", itemId, conn) == 0) {
                LOGGER.log(Level.ERROR, new LogMessage(new Long(itemId), operator,
                        "The item id [" + itemId + "] does not exist in the database."));
                throw new ReviewEntityNotFoundException("The item id [" + itemId
                    + "] does not exist in table [" + REVIEW_ITEM_TABLE + "].", itemId);
            }

            // get # of comments for this item in the database, which equals
            // to the position of the current comment in its item.
            Long index = new Long(Helper.countEntities(REVIEW_ITEM_COMMENT_TABLE, "review_item_id",
                itemId, conn));

            // create the comment
            createReviewItemComments(new Comment[] {comment}, new Long[] {index}, new Long(itemId),
                operator, conn, changeTable);

            Helper.commitTransaction(conn, LOGGER, useManualCommit);
        } catch (ReviewPersistenceException e) {
            LOGGER.log(Level.ERROR, new LogMessage(null, operator,
                    "Error occurs add item comment with the item id[" + itemId + "].", e));
            Helper.rollBackTransaction(conn, LOGGER, useManualCommit);
            throw e;
        } finally {
            Helper.closeConnection(conn, LOGGER);
        }

        // set the comment id when no exception occurred.
        setEntityIds(changeTable);
    }
}
