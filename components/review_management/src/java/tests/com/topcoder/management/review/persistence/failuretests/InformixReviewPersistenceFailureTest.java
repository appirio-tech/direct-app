/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.review.persistence.failuretests;

import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.management.review.ConfigurationException;
import com.topcoder.management.review.DuplicateReviewEntityException;
import com.topcoder.management.review.ReviewEntityNotFoundException;
import com.topcoder.management.review.ReviewPersistence;
import com.topcoder.management.review.ReviewPersistenceException;
import com.topcoder.management.review.data.Comment;
import com.topcoder.management.review.data.CommentType;
import com.topcoder.management.review.data.Item;
import com.topcoder.management.review.data.Review;
import com.topcoder.management.review.persistence.InformixReviewPersistence;
import com.topcoder.search.builder.SearchBundle;
import com.topcoder.search.builder.SearchBundleManager;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.datavalidator.NotValidator;
import com.topcoder.util.datavalidator.NullValidator;
import com.topcoder.util.datavalidator.ObjectValidator;

import junit.framework.TestCase;

/**
 * <p>
 * Failure test for InformixReviewPersistence class.
 * </p>
 *
 * @author arylio
 * @version 1.0
 */
public class InformixReviewPersistenceFailureTest extends TestCase {
    /**
     * <p>
     * A config file for informix review persistence.
     * </p>
     */
    private static String DEFAULT_CONFIG_FILE = "failuretests/ReviewManagerPersistence_Informix.xml";

    /**
     * <p>
     * An invalid config file for informix review persistence.
     * </p>
     */
    private static String INVALID_CONFIG_FILE = "failuretests/ReviewManagerPersistence_Informix_Invalid.xml";

    /**
     * <p>
     * An config file for search bundle manager.
     * </p>
     */
    private static String SEARCH_BUNDLE_CONFIG_FILE = "failuretests/search_bundle_manager.xml";

    /**
     * <p>
     * A config file for db factory.
     * </p>
     */
    private static String DB_FACTORY_CONFIG_FILE = "failuretests/dbfactory.xml";

    /**
     * <p>
     * An instance of InformixReviewPersistence to test.
     * </p>
     */
    private InformixReviewPersistence persistence = null;

    /**
     * <p>
     * An instance of InformixReviewPersistence with invalid connection name to test.
     * </p>
     */
    private InformixReviewPersistence persistenceInvalid = null;


    /**
     * The SearchBundle instance used in constructing persistence.
     */
    private SearchBundle searchBundle = null;

    /**
     * The DBConnectionFactory instance used in constructing persistence.
     */
    private DBConnectionFactory dbFactory = null;

    /**
     * <p>
     * The setUp method of the unit test, add the config, prepare the test data,
     * and create the testing instance.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        loadConfig();
        dbFactory = new DBConnectionFactoryImpl("com.topcoder.db.connectionfactory.DBConnectionFactoryImpl");
        Connection conn = dbFactory.createConnection();
        executeSqlFile("./test_files/failuretests/clear_tables.sql", conn);
        executeSqlFile("./test_files/failuretests/prepare.sql", conn);
        conn.close();

        persistence = new InformixReviewPersistence();

        SearchBundleManager manager = new SearchBundleManager("com.topcoder.search.builder.SearchBundleManager");
        searchBundle = manager.getSearchBundle("ReviewSearchBundle");

        Map fields = new HashMap();
        ObjectValidator notNullValidate = new NotValidator(new NullValidator());
        fields.put("scorecardType", notNullValidate);
        fields.put("submission", notNullValidate);
        fields.put("reviewer", notNullValidate);
        fields.put("project", notNullValidate);
        fields.put("commited", notNullValidate);
        searchBundle.setSearchableFields(fields);
        persistenceInvalid = new InformixReviewPersistence(dbFactory, "invalid", searchBundle);

    }

    /**
     * The tearDown of the unit test.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void tearDown() throws Exception {
        Connection conn = dbFactory.createConnection();
        executeSqlFile("test_files/failuretests/clear_tables.sql", conn);
        conn.close();
        clearConfig();
    }

    /**
     * Add the namespace.
     *
     * @throws Exception to JUnit
     */
    private static void loadConfig() throws Exception {
        clearConfig();
        ConfigManager cm = ConfigManager.getInstance();
        cm.add(DEFAULT_CONFIG_FILE);
        cm.add(INVALID_CONFIG_FILE);
        cm.add(SEARCH_BUNDLE_CONFIG_FILE);
        cm.add(DB_FACTORY_CONFIG_FILE);
    }

    /**
     * <p>
     * Remove all the namespace.
     * </p>
     *
     * @throws Exception to JUnit
     */
    private static void clearConfig() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        Iterator it = cm.getAllNamespaces();
        while (it.hasNext()) {
            cm.removeNamespace((String) it.next());
        }
    }

    /**
     * Execute the sql statements in a file.
     *
     * @param filename the sql file.
     * @throws Exception to JUnit
     */
    private static void executeSqlFile(String filename, Connection connection) throws Exception {
        Reader file = new FileReader(filename);
        char[] buffer = new char[1024];
        int retLength = 0;
        StringBuffer content = new StringBuffer();

        while ((retLength = file.read(buffer)) >= 0) {
            content.append(buffer, 0, retLength);
        }

        file.close();

        List sqls = new ArrayList();
        int lastIndex = 0;

        //parse the sqls
        for (int i = 0; i < content.length(); i++) {
            if (content.charAt(i) == ';') {
                sqls.add(content.substring(lastIndex, i).trim());
                lastIndex = i + 1;
            }
        }
        Statement stmt = null;

        try {
            stmt = connection.createStatement();;
            for (int i = 0; i < sqls.size(); i++) {
                stmt.executeUpdate((String) sqls.get(i));
            }
        } finally {
            stmt.close();
        }
    }

    /**
     * <p>
     * Test ctor InformixReviewPersistence(), when the default namespace not loaded,
     * ConfigurationException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testCtor1_NamespaceNotExisted() throws Exception {
        ConfigManager.getInstance().removeNamespace(InformixReviewPersistence.class.getName());
        try {
            new InformixReviewPersistence();
            fail("ConfigurationException is expected when the default namespace not existed.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Test ctor InformixReviewPersistence(String namespace), when namespace is null,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testCtor2_NamespaceIsNull() throws Exception {
        try {
            new InformixReviewPersistence(null);
            fail("IllegalArgumentException is expected when namespace is null.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test ctor InformixReviewPersistence(String namespace), when namespace is empty,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testCtor2_NamespaceIsEmpty() throws Exception {
        try {
            new InformixReviewPersistence("  \t ");
            fail("IllegalArgumentException is expected, when namespace is empty.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test ctor InformixReviewPersistence(String namespace), when connection name is not existed,
     * ConfigurationException is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor2_ConnectionNameNotExisted() throws Exception {
        innerTestCtor2_Invalid("com.topcoder.management.review.persistence.InformixReviewPersistence.invalid1");
    }

    /**
     * <p>
     * Test ctor InformixReviewPersistence(String namespace), when connection name is invalid,
     * ConfigurationException is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor2_ConnectionNameInvalid() throws Exception {
        innerTestCtor2_Invalid("com.topcoder.management.review.persistence.InformixReviewPersistence.invalid2");
    }

    /**
     * <p>
     * Test ctor InformixReviewPersistence(String namespace), when factory_namespace is not existed ,
     * ConfigurationException is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor2_FactoryNamespaceNotExisted() throws Exception {
        innerTestCtor2_Invalid("com.topcoder.management.review.persistence.InformixReviewPersistence.invalid3");
    }

    /**
     * <p>
     * Test ctor InformixReviewPersistence(String namespace), when factory_namespace is invalid,
     * ConfigurationException is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor2_FactoryNamespaceInvalid() throws Exception {
        innerTestCtor2_Invalid("com.topcoder.management.review.persistence.InformixReviewPersistence.invalid4");
    }

    /**
     * <p>
     * Test ctor InformixReviewPersistence(String namespace), when factory_class is not existed,
     * ConfigurationException is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor2_FactoryClassNotExisted() throws Exception {
        innerTestCtor2_Invalid("com.topcoder.management.review.persistence.InformixReviewPersistence.invalid5");
    }

    /**
     * <p>
     * Test ctor InformixReviewPersistence(String namespace), when factory_class is invalid,
     * ConfigurationException is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor2_FactoryClassInvalid() throws Exception {
        innerTestCtor2_Invalid("com.topcoder.management.review.persistence.InformixReviewPersistence.invalid6");
    }

    /**
     * <p>
     * Test ctor InformixReviewPersistence(String namespace), when search_bundle_manager_namespace is not existed,
     * ConfigurationException is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor2_SearchBundleManagerNamespaceNotExisted() throws Exception {
        innerTestCtor2_Invalid("com.topcoder.management.review.persistence.InformixReviewPersistence.invalid7");
    }

    /**
     * <p>
     * Test ctor InformixReviewPersistence(String namespace), when search_bundle_manager_namespace is invalid,
     * ConfigurationException is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor2_SearchBundleManagerNamespaceInvalid() throws Exception {
        innerTestCtor2_Invalid("com.topcoder.management.review.persistence.InformixReviewPersistence.invalid8");
    }

    /**
     * <p>
     * Test ctor InformixReviewPersistence(String namespace), when search_bundle_name is not existed,
     * ConfigurationException is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor2_SearchBundleNameNotExisted() throws Exception {
        innerTestCtor2_Invalid("com.topcoder.management.review.persistence.InformixReviewPersistence.invalid9");
    }

    /**
     * <p>
     * Test ctor InformixReviewPersistence(String namespace), when search_bundle_name is invalid,
     * ConfigurationException is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor2_SearchBundleNameInvalid() throws Exception {
        innerTestCtor2_Invalid("com.topcoder.management.review.persistence.InformixReviewPersistence.invalid10");
    }

    /**
     * <p>
     * Test for ctor InformixReviewPersistence(String namespace), when namespace is invalid,
     * ConfigurationException is expected.
     * </p>
     *
     * @param namespace the namesapce to test.
     *
     * @throws Exception to JUnit.
     */
    private void innerTestCtor2_Invalid(String namespace) throws Exception {
        try {
            new InformixReviewPersistence(namespace);
            fail("ConfigurationException is expected when namespace is invalid.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Test ctor InformixReviewPersistence(DBConnectionFactory dbFactory, String connectionName,
     * SearchBundle searchBundle), when dbFactory is null, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor3_DbFactoryIsNull() throws Exception {
        try {
            new InformixReviewPersistence(null, "failureTest", searchBundle);
            fail("When dbFactory is null, IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test ctor InformixReviewPersistence(DBConnectionFactory dbFactory, String connectionName,
     * SearchBundle searchBundle), when searchBundle is null, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor3_SearchBundleIsNull() throws Exception {
        try {
            new InformixReviewPersistence(dbFactory, "failureTest", null);
            fail("When searchBundle is null, IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test ctor InformixReviewPersistence(DBConnectionFactory dbFactory, String connectionName,
     * SearchBundle searchBundle), when connectionName is null, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor3_ConnectionNameIsNull() throws Exception {
        try {
            new InformixReviewPersistence(dbFactory, null, searchBundle);
            fail("When connectionName is null, IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test ctor InformixReviewPersistence(DBConnectionFactory dbFactory, String connectionName,
     * SearchBundle searchBundle), when connectionName is empty, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testCtor3_ConnectionNameIsEmpty() throws Exception {
        try {
            new InformixReviewPersistence(dbFactory, "  ", searchBundle);
            fail("When connectionName is empty, IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test createReview(Review review, String operator), when review is null,
     * IllegalArgumentException is expected.
     */
    public void testCreateReview_ReviewIsNull() throws Exception {
        try {
            persistence.createReview(null, "operator");
            fail("IllegalArgumentException is expected, when review is null.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test createReview(Review review, String operator), when operator is null,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testCreateReview_OperatorIsNull() throws Exception {
        try {
            persistence.createReview(createReview(), null);
            fail("IllegalArgumentException is expected, when operator is null.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test createReview(Review review, String operator), when operator is emtpy,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testCreateReview_OperatorIsEmpty() throws Exception {
        try {
            persistence.createReview(createReview(), "    ");
            fail("IllegalArgumentException is expected, when operator is emtpy.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test createReview(Review review, String operator), when review already existed,
     * DuplicateReviewEntityException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testCreateReview_ReviewExisted() throws Exception {
        try {
            persistence.createReview(createReview(), "operator");
            fail("DuplicateReviewEntityException is expected, when review already existed.");
        } catch (DuplicateReviewEntityException e) {
            // good
        }
    }

    /**
     * <p>
     * Test createReview(Review review, String operator), when review is invalid,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testCreateReview_ReviewInvalid1() throws Exception {
        Review review = new Review();
        // review.setAuthor(1);
        review.setScorecard(1);
        review.setSubmission(1);
        review.setCommitted(true);
        review.setScore(new Float(89.1f));

        review.addComment(createComment(100));
        review.addItem(createReviewItem(300));
        try {
            persistence.createReview(review, "operator");
            fail("IllegalArgumentException is expected, when review is invalid.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test createReview(Review review, String operator), when review is invalid,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testCreateReview_ReviewInvalid2() throws Exception {
        Review review = new Review();
        review.setAuthor(1);
        // review.setScorecard(1);
        review.setSubmission(1);
        review.setCommitted(true);
        review.setScore(new Float(89.1f));

        review.addComment(createComment(100));
        review.addItem(createReviewItem(300));
        try {
            persistence.createReview(review, "operator");
            fail("IllegalArgumentException is expected, when review is invalid.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test createReview(Review review, String operator), when review is invalid,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testCreateReview_ReviewInvalid3() throws Exception {
        Review review = new Review();
        review.setAuthor(1);
        review.setScorecard(1);
        // review.setSubmission(1);
        review.setCommitted(true);
        review.setScore(new Float(89.1f));

        review.addComment(createComment(100));
        review.addItem(createReviewItem(300));
        try {
            persistence.createReview(review, "operator");
            fail("IllegalArgumentException is expected, when review is invalid.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test createReview(Review review, String operator), when review is invalid,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testCreateReview_ReviewInvalid4() throws Exception {
        Review review = createReview(1000);
        Comment comment = new Comment();
        //comment.setAuthor(1);
        comment.setExtraInfo("extraInfo");
        comment.setCommentType(new CommentType(1, "CommentType"));
        comment.setComment("comment");
        review.addComment(comment);
        try {
            persistence.createReview(review, "operator");
            fail("IllegalArgumentException is expected, when review is invalid.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test createReview(Review review, String operator), when review is invalid,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testCreateReview_ReviewInvalid5() throws Exception {
        Review review = createReview(1000);
        Comment comment = new Comment();
        comment.setAuthor(1);
        comment.setExtraInfo(new Comment());
        comment.setCommentType(new CommentType(1, "CommentType"));
        comment.setComment("comment");
        review.addComment(comment);
        try {
            persistence.createReview(review, "operator");
            fail("IllegalArgumentException is expected, when review is invalid.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test createReview(Review review, String operator), when review is invalid,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testCreateReview_ReviewInvalid6() throws Exception {
        Review review = createReview(1000);
        Comment comment = new Comment();
        comment.setAuthor(1);
        comment.setExtraInfo("extraInfo");
        //comment.setCommentType(new CommentType(1, "CommentType"));
        comment.setComment("comment");
        review.addComment(comment);
        try {
            persistence.createReview(review, "operator");
            fail("IllegalArgumentException is expected, when review is invalid.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test createReview(Review review, String operator), when review is invalid,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testCreateReview_ReviewInvalid7() throws Exception {
        Review review = createReview(1000);
        Comment comment = new Comment();
        comment.setAuthor(1);
        comment.setExtraInfo("extraInfo");
        comment.setCommentType(new CommentType(1, "CommentType"));
        //comment.setComment("comment");
        review.addComment(comment);
        try {
            persistence.createReview(review, "operator");
            fail("IllegalArgumentException is expected, when review is invalid.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test createReview(Review review, String operator), when review is invalid,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testCreateReview_ReviewInvalid8() throws Exception {
        Review review = createReview(1000);
        Item item = new Item(2000);
        //item.setQuestion(1);
        item.setAnswer("answer");
        item.addComment(createComment(2001));
        review.addItem(item);
        try {
            persistence.createReview(review, "operator");
            fail("IllegalArgumentException is expected, when review is invalid.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test createReview(Review review, String operator), when review is invalid,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testCreateReview_ReviewInvalid9() throws Exception {
        Review review = createReview(1000);
        Item item = new Item(2000);
        item.setQuestion(1);
        //item.setAnswer("answer");
        item.addComment(createComment(2001));
        review.addItem(item);
        try {
            persistence.createReview(review, "operator");
            fail("IllegalArgumentException is expected, when review is invalid.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test createReview(Review review, String operator), when review is invalid,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testCreateReview_ReviewInvalid10() throws Exception {
        Review review = createReview(1000);
        Item item = new Item(2000);
        item.setQuestion(1);
        item.setAnswer(createComment(3000));
        item.addComment(createComment(2001));
        review.addItem(item);
        try {
            persistence.createReview(review, "operator");
            fail("IllegalArgumentException is expected, when review is invalid.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test createReview(Review review, String operator), when review is invalid,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testCreateReview_ReviewInvalid11() throws Exception {
        Review review = createReview(1000);
        Item item = new Item(2000);
        item.setQuestion(1);
        item.setAnswer("answer");

        Comment comment = new Comment(2100);
        //comment.setAuthor(1);
        comment.setExtraInfo("extraInfo");
        comment.setCommentType(new CommentType(1, "CommentType"));
        comment.setComment("content");
        item.addComment(comment);
        review.addItem(item);
        try {
            persistence.createReview(review, "operator");
            fail("IllegalArgumentException is expected, when review is invalid.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test createReview(Review review, String operator), when review is invalid,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testCreateReview_ReviewInvalid12() throws Exception {
        Review review = createReview(1000);
        Item item = new Item(2000);
        item.setQuestion(1);
        item.setAnswer("answer");

        Comment comment = new Comment(2100);
        comment.setAuthor(1);
        comment.setExtraInfo(createComment(1));
        comment.setCommentType(new CommentType(1, "CommentType"));
        comment.setComment("content");
        item.addComment(comment);
        review.addItem(item);
        try {
            persistence.createReview(review, "operator");
            fail("IllegalArgumentException is expected, when review is invalid.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test createReview(Review review, String operator), when review is invalid,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testCreateReview_ReviewInvalid13() throws Exception {
        Review review = createReview(1000);
        Item item = new Item(2000);
        item.setQuestion(1);
        item.setAnswer("answer");

        Comment comment = new Comment(2100);
        comment.setAuthor(1);
        comment.setExtraInfo("ExtraInfo");
        //comment.setCommentType(new CommentType(1, "CommentType"));
        comment.setComment("content");
        item.addComment(comment);
        review.addItem(item);
        try {
            persistence.createReview(review, "operator");
            fail("IllegalArgumentException is expected, when review is invalid.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test createReview(Review review, String operator), when review is invalid,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testCreateReview_ReviewInvalid14() throws Exception {
        Review review = createReview(1000);
        Item item = new Item(2000);
        item.setQuestion(1);
        item.setAnswer("answer");

        Comment comment = new Comment(2100);
        comment.setAuthor(1);
        comment.setExtraInfo("ExtraInfo");
        comment.setCommentType(new CommentType(1, "CommentType"));
        //comment.setComment("content");
        item.addComment(comment);
        review.addItem(item);
        try {
            persistence.createReview(review, "operator");
            fail("IllegalArgumentException is expected, when review is invalid.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test createReview(Review review, String operator), when failed to get connection,
     * ReviewPersistenceException is expected.
     * </p>
     */
    public void tesCreateReview_Failed() {
        try {
            persistenceInvalid.createReview(createReview(), "operator");
            fail("IllegalArgumentException is expected, when failed to get connection.");
        } catch (ReviewPersistenceException e) {
            // good
        }
    }

    /**
     * <p>
     * Test updateReview(Review review, String operator), when review is null,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testUpdateReview_ReviewIsNull() throws Exception {
        try {
            persistence.updateReview(null, "operator");
            fail("IllegalArgumentException is expected, when review is null.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test updateReview(Review review, String operator), when review is null,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testUpdateReview_OperatorIsNull() throws Exception {
        try {
            persistence.updateReview(createReview(), null);
            fail("IllegalArgumentException is expected, when operator is null.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test updateReview(Review review, String operator), when operator is empty,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testUpdateReview_OperatorIsEmpty() throws Exception {
        try {
            persistence.updateReview(createReview(), "  ");
            fail("IllegalArgumentException is expected, when operator is empty.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test updateReview(Review review, String operator), when review is not existed,
     * ReviewEntityNotFoundException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testUpdateReview_ReviewNotExisted() throws Exception {
        try {
            persistence.updateReview(createReview(99999), "operator");
            fail("ReviewEntityNotFoundException is expected, when review not existed.");
        } catch (ReviewEntityNotFoundException e) {
            // good
        }
    }

    /**
     * <p>
     * Test updateReview(Review review, String operator), when review is invalid,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testUpdateReview_ReviewInvalid1() throws Exception {
        Review review = new Review(1);
        // review.setAuthor(1);
        review.setScorecard(1);
        review.setSubmission(1);
        review.setCommitted(true);
        review.setScore(new Float(89.1f));

        review.addComment(createComment(100));
        review.addItem(createReviewItem(300));
        try {
            persistence.updateReview(review, "operator");
            fail("IllegalArgumentException is expected, when review is invalid.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test updateReview(Review review, String operator), when review is invalid,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testUpdateReview_ReviewInvalid2() throws Exception {
        Review review = new Review(1);
        review.setAuthor(1);
        // review.setScorecard(1);
        review.setSubmission(1);
        review.setCommitted(true);
        review.setScore(new Float(89.1f));

        review.addComment(createComment(100));
        review.addItem(createReviewItem(300));
        try {
            persistence.updateReview(review, "operator");
            fail("IllegalArgumentException is expected, when review is invalid.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test updateReview(Review review, String operator), when review is invalid,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testUpdateReview_ReviewInvalid3() throws Exception {
        Review review = new Review(1);
        review.setAuthor(1);
        review.setScorecard(1);
        // review.setSubmission(1);
        review.setCommitted(true);
        review.setScore(new Float(89.1f));

        review.addComment(createComment(100));
        review.addItem(createReviewItem(300));
        try {
            persistence.updateReview(review, "operator");
            fail("IllegalArgumentException is expected, when review is invalid.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test updateReview(Review review, String operator), when review is invalid,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testUpdateReview_ReviewInvalid4() throws Exception {
        Review review = createReview();
        Comment comment = new Comment();
        //comment.setAuthor(1);
        comment.setExtraInfo("extraInfo");
        comment.setCommentType(new CommentType(1, "CommentType"));
        comment.setComment("comment");
        review.addComment(comment);
        try {
            persistence.updateReview(review, "operator");
            fail("IllegalArgumentException is expected, when review is invalid.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test updateReview(Review review, String operator), when review is invalid,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testUpdateReview_ReviewInvalid5() throws Exception {
        Review review = createReview();
        Comment comment = new Comment();
        comment.setAuthor(1);
        comment.setExtraInfo(new Comment());
        comment.setCommentType(new CommentType(1, "CommentType"));
        comment.setComment("comment");
        review.addComment(comment);
        try {
            persistence.updateReview(review, "operator");
            fail("IllegalArgumentException is expected, when review is invalid.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test updateReview(Review review, String operator), when review is invalid,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testUpdateReview_ReviewInvalid6() throws Exception {
        Review review = createReview();
        Comment comment = new Comment();
        comment.setAuthor(1);
        comment.setExtraInfo("extraInfo");
        //comment.setCommentType(new CommentType(1, "CommentType"));
        comment.setComment("comment");
        review.addComment(comment);
        try {
            persistence.updateReview(review, "operator");
            fail("IllegalArgumentException is expected, when review is invalid.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test updateReview(Review review, String operator), when review is invalid,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testUpdateReview_ReviewInvalid7() throws Exception {
        Review review = createReview();
        Comment comment = new Comment();
        comment.setAuthor(1);
        comment.setExtraInfo("extraInfo");
        comment.setCommentType(new CommentType(1, "CommentType"));
        //comment.setComment("comment");
        review.addComment(comment);
        try {
            persistence.updateReview(review, "operator");
            fail("IllegalArgumentException is expected, when review is invalid.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test updateReview(Review review, String operator), when review is invalid,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testUpdateReview_ReviewInvalid8() throws Exception {
        Review review = createReview();
        Item item = new Item(2000);
        //item.setQuestion(1);
        item.setAnswer("answer");
        item.addComment(createComment(2001));
        review.addItem(item);
        try {
            persistence.updateReview(review, "operator");
            fail("IllegalArgumentException is expected, when review is invalid.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test updateReview(Review review, String operator), when review is invalid,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testUpdateReview_ReviewInvalid9() throws Exception {
        Review review = createReview();
        Item item = new Item(2000);
        item.setQuestion(1);
        //item.setAnswer("answer");
        item.addComment(createComment(2001));
        review.addItem(item);
        try {
            persistence.updateReview(review, "operator");
            fail("IllegalArgumentException is expected, when review is invalid.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test updateReview(Review review, String operator), when review is invalid,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testUpdateReview_ReviewInvalid10() throws Exception {
        Review review = createReview();
        Item item = new Item(2000);
        item.setQuestion(1);
        item.setAnswer(createComment(3000));
        item.addComment(createComment(2001));
        review.addItem(item);
        try {
            persistence.updateReview(review, "operator");
            fail("IllegalArgumentException is expected, when review is invalid.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test updateReview(Review review, String operator), when review is invalid,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testUpdateReview_ReviewInvalid11() throws Exception {
        Review review = createReview();
        Item item = new Item(2000);
        item.setQuestion(1);
        item.setAnswer("answer");

        Comment comment = new Comment(2100);
        //comment.setAuthor(1);
        comment.setExtraInfo("extraInfo");
        comment.setCommentType(new CommentType(1, "CommentType"));
        comment.setComment("content");
        item.addComment(comment);
        review.addItem(item);
        try {
            persistence.updateReview(review, "operator");
            fail("IllegalArgumentException is expected, when review is invalid.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test updateReview(Review review, String operator), when review is invalid,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testUpdateReview_ReviewInvalid12() throws Exception {
        Review review = createReview();
        Item item = new Item(2000);
        item.setQuestion(1);
        item.setAnswer("answer");

        Comment comment = new Comment(2100);
        comment.setAuthor(1);
        comment.setExtraInfo(createComment(1));
        comment.setCommentType(new CommentType(1, "CommentType"));
        comment.setComment("content");
        item.addComment(comment);
        review.addItem(item);
        try {
            persistence.updateReview(review, "operator");
            fail("IllegalArgumentException is expected, when review is invalid.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test updateReview(Review review, String operator), when review is invalid,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testUpdateReview_ReviewInvalid13() throws Exception {
        Review review = createReview();
        Item item = new Item(2000);
        item.setQuestion(1);
        item.setAnswer("answer");

        Comment comment = new Comment(2100);
        comment.setAuthor(1);
        comment.setExtraInfo("ExtraInfo");
        //comment.setCommentType(new CommentType(1, "CommentType"));
        comment.setComment("content");
        item.addComment(comment);
        review.addItem(item);
        try {
            persistence.updateReview(review, "operator");
            fail("IllegalArgumentException is expected, when review is invalid.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test updateReview(Review review, String operator), when review is invalid,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testUpdateReview_ReviewInvalid14() throws Exception {
        Review review = createReview();
        Item item = new Item(2000);
        item.setQuestion(1);
        item.setAnswer("answer");

        Comment comment = new Comment(2100);
        comment.setAuthor(1);
        comment.setExtraInfo("ExtraInfo");
        comment.setCommentType(new CommentType(1, "CommentType"));
        //comment.setComment("content");
        item.addComment(comment);
        review.addItem(item);
        try {
            persistence.updateReview(review, "operator");
            fail("IllegalArgumentException is expected, when review is invalid.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test updateReview(Review review, String operator), when failed to get connection,
     * ReviewPersistenceException is expected.
     * </p>
     */
    public void testUpdateReview_Failed() {
        try {
            persistenceInvalid.updateReview(createReview(), "operator");
            fail("IllegalArgumentException is expected, when failed to get connection.");
        } catch (ReviewPersistenceException e) {
            // good
        }
    }

    /**
     * <p>
     * Test getReview(long id), when id is zero, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testGetReview_IdIsZero() throws Exception {
        try {
            persistence.getReview(0);
            fail("IllegalArgumentException is expected, when id is zero.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test getReview(long id), when id is negative, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testGetReview_IdIsNegative() throws Exception {
        try {
            persistence.getReview(-1);
            fail("IllegalArgumentException is expected, when id is negative.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test getReview(long id), when id is not existed, ReviewEntityNotFoundException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testGetReview_NotExisted() throws Exception {
        try {
            persistence.getReview(999999);
            fail("ReviewEntityNotFoundException is expected, when id is not existed.");
        } catch (ReviewEntityNotFoundException e) {
            // good
        }
    }

    /**
     * <p>
     * Test getReview(long id), when failed to create connection, ReviewPersistenceException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testGetReview_Failed() throws Exception {
        try {
            persistenceInvalid.getReview(1);
            fail("ReviewEntityNotFoundException is expected, when id is not existed.");
        } catch (ReviewPersistenceException e) {
            // good
        }
    }


    /**
     * <p>
     * Test searchReviews(Filter filter, boolean complete),
     * when filter is null, IllegalArgumentException is expected.
     * </p>
     */
    public void testSearchReviews_FilterIsNull() throws Exception {
        try {
            persistence.searchReviews(null, false);
            fail("IllegalArgumentException is expected, when filter is null.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test searchReviews(Filter filter, boolean complete),
     * when filter is invalid, ReviewPersistenceException is expected.
     * </p>
     */
    public void testSearchReviews_FilterIsInvalid() {
        Filter filter = new EqualToFilter("invalid column", "invalid");
        try {
            persistence.searchReviews(filter, false);
            fail("ReviewPersistenceException is expected, when filter is invalid.");
        } catch (ReviewPersistenceException e) {
            // good
        }
    }

    /**
     * <p>
     * Test addReviewComment(long reviewId, Comment comment, String operator),
     * when reviewId is zero, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit
     */
    public void testAddReviewComment_ReviewIdIsZero() throws Exception {
        try {
            persistence.addReviewComment(0, createComment(1), "operator");
            fail("IllegalArgumentException is expected, when reviewId is zero.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test addReviewComment(long reviewId, Comment comment, String operator),
     * when reviewId is negative, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit
     */
    public void testAddReviewComment_ReviewIdIsNegative() throws Exception {
        try {
            persistence.addReviewComment(-1, createComment(1), "operator");
            fail("IllegalArgumentException is expected, when reviewId is negative.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test addReviewComment(long reviewId, Comment comment, String operator),
     * when comment is null, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit
     */
    public void testAddReviewComment_CommentIsNull() throws Exception {
        try {
            persistence.addReviewComment(-1, null, "operator");
            fail("IllegalArgumentException is expected, when comment is null.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test addReviewComment(long reviewId, Comment comment, String operator),
     * when operator is null, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit
     */
    public void testAddReviewComment_OperatorIsNull() throws Exception {
        try {
            persistence.addReviewComment(-1, createComment(1), null);
            fail("IllegalArgumentException is expected, when operator is null.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test addReviewComment(long reviewId, Comment comment, String operator),
     * when operator is empty string, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit
     */
    public void testAddReviewComment_OperatorIsEmpty() throws Exception {
        try {
            persistence.addReviewComment(-1, createComment(1), "  \t ");
            fail("IllegalArgumentException is expected, when operator is empty.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test addReviewComment(long reviewId, Comment comment, String operator),
     * when comment is invalid, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit
     */
    public void testAddReviewComment_CommentInvalid1() throws Exception {
        Comment comment = new Comment(1);
        //comment.setAuthor(1);
        comment.setExtraInfo("extraInfo");
        comment.setCommentType(new CommentType(1, "CommentType"));
        comment.setComment("content");
        try {
            persistence.addReviewComment(-1, comment, "operator");
            fail("IllegalArgumentException is expected, when comment is invalid.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test addReviewComment(long reviewId, Comment comment, String operator),
     * when comment is invalid, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit
     */
    public void testAddReviewComment_CommentInvalid2() throws Exception {
        Comment comment = new Comment(1);
        comment.setAuthor(1);
        comment.setExtraInfo(createComment(1));
        comment.setCommentType(new CommentType(1, "CommentType"));
        comment.setComment("content");
        try {
            persistence.addReviewComment(-1, comment, "operator");
            fail("IllegalArgumentException is expected, when comment is invalid.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test addReviewComment(long reviewId, Comment comment, String operator),
     * when comment is invalid, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit
     */
    public void testAddReviewComment_CommentInvalid3() throws Exception {
        Comment comment = new Comment(1);
        comment.setAuthor(1);
        comment.setExtraInfo(createComment(1));
        comment.setCommentType(new CommentType(1, "CommentType"));
        comment.setComment("content");
        try {
            persistence.addReviewComment(-1, comment, "operator");
            fail("IllegalArgumentException is expected, when comment is invalid.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test addReviewComment(long reviewId, Comment comment, String operator),
     * when comment is invalid, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit
     */
    public void testAddReviewComment_CommentInvalid4() throws Exception {
        Comment comment = new Comment(1);
        comment.setAuthor(1);
        //comment.setCommentType(new CommentType(1, "CommentType"));
        comment.setComment("content");
        try {
            persistence.addReviewComment(-1, comment, "operator");
            fail("IllegalArgumentException is expected, when comment is invalid.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test addReviewComment(long reviewId, Comment comment, String operator),
     * when comment is invalid, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit
     */
    public void testAddReviewComment_CommentInvalid5() throws Exception {
        Comment comment = new Comment(1);
        comment.setAuthor(1);
        comment.setCommentType(new CommentType(1, "CommentType"));
        //comment.setComment("content");
        try {
            persistence.addReviewComment(-1, comment, "operator");
            fail("IllegalArgumentException is expected, when comment is invalid.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test addReviewComment(long reviewId, Comment comment, String operator),
     * when reviewId is not existed, ReviewEntityNotFoundException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit
     */
    public void testAddReviewComment_ReviewIdNotExisted() throws Exception {
        try {
            persistence.addReviewComment(9999, createComment(1), "operator");
            fail("ReviewPersistenceException is expected, when failed to create connection.");
        } catch (ReviewEntityNotFoundException e) {
            // good
        }
    }

    /**
     * <p>
     * Test addReviewComment(long reviewId, Comment comment, String operator),
     * when failed to create connection, ReviewPersistenceException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit
     */
    public void testAddReviewComment_Failed() throws Exception {
        try {
            persistenceInvalid.addReviewComment(1, createComment(100), "operator");
            fail("ReviewPersistenceException is expected, when failed to create connection.");
        } catch (ReviewPersistenceException e) {
            // good
        }
    }

    /**
     * <p>
     * Test addItemComment(long itemId, Comment comment, String operator), when itemId is zero,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testAddItemComment_ItemIdIsZero() throws Exception {
        try {
            persistence.addItemComment(0, createComment(1), "operator");
            fail("IllegalArgumentException is expected, when itemId is zero.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test addItemComment(long itemId, Comment comment, String operator), when itemId is negative,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testAddItemComment_ItemIdIsNegative() throws Exception {
        try {
            persistence.addItemComment(-1, createComment(1), "operator");
            fail("IllegalArgumentException is expected, when itemId is negative.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test addItemComment(long itemId, Comment comment, String operator), when comment is null,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testAddItemComment_CommentIsNull() throws Exception {
        try {
            persistence.addItemComment(1, null, "operator");
            fail("IllegalArgumentException is expected, when comment is null.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test addItemComment(long itemId, Comment comment, String operator), when ioperator is nul,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testAddItemComment_OperatorIsNull() throws Exception {
        try {
            persistence.addItemComment(1, createComment(1), null);
            fail("IllegalArgumentException is expected, when operator is null.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test addItemComment(long itemId, Comment comment, String operator),
     * when operator is empty string, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit
     */
    public void testAddItemComment_CommentInvalid1() throws Exception {
        Comment comment = new Comment(1);
        //comment.setAuthor(1);
        comment.setExtraInfo("extraInfo");
        comment.setCommentType(new CommentType(1, "CommentType"));
        comment.setComment("content");
        try {
            persistence.addItemComment(1, comment, "operator");
            fail("IllegalArgumentException is expected, when comment is invalid.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test addItemComment(long itemId, Comment comment, String operator),
     * when operator is empty string, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit
     */
    public void testAddItemComment_CommentInvalid2() throws Exception {
        Comment comment = new Comment(1);
        comment.setAuthor(1);
        comment.setExtraInfo(createComment(2));
        comment.setCommentType(new CommentType(1, "CommentType"));
        comment.setComment("content");
        try {
            persistence.addItemComment(1, comment, "operator");
            fail("IllegalArgumentException is expected, when comment is invalid.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test addItemComment(long itemId, Comment comment, String operator),
     * when operator is empty string, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit
     */
    public void testAddItemComment_CommentInvalid3() throws Exception {
        Comment comment = new Comment(1);
        comment.setAuthor(1);
        //comment.setCommentType(new CommentType(1, "CommentType"));
        comment.setComment("content");
        try {
            persistence.addItemComment(1, comment, "operator");
            fail("IllegalArgumentException is expected, when comment is invalid.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test addItemComment(long itemId, Comment comment, String operator),
     * when operator is empty string, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit
     */
    public void testAddItemComment_CommentInvalid4() throws Exception {
        Comment comment = new Comment(1);
        comment.setAuthor(1);
        comment.setCommentType(new CommentType(1, "CommentType"));
        //comment.setComment("content");
        try {
            persistence.addItemComment(1, comment, "operator");
            fail("IllegalArgumentException is expected, when comment is invalid.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test addItemComment(long itemId, Comment comment, String operator), when operator is empty string,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testAddItemComment_OperatorIsEmpty() throws Exception {
        try {
            persistence.addItemComment(1, createComment(1), " \t ");
            fail("IllegalArgumentException is expected, when operator is empty string.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test addItemComment(long itemId, Comment comment, String operator), when itemId does not exists,
     * ReviewEntityNotFoundException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testAddItemComment_ItemIdNotExisted() throws Exception {
        try {
            persistence.addItemComment(9999999, createComment(1), "operator");
            fail("ReviewEntityNotFoundException is expected, when itemId does not exists.");
        } catch (ReviewEntityNotFoundException e) {
            // good
        }
    }

    /**
     * <p>
     * Test addItemComment(long itemId, Comment comment, String operator), when  failed to create connection,
     * ReviewPersistenceException is expected.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testAddItemComment_Failed() throws Exception {
        try {
            persistenceInvalid.addItemComment(1,createComment(1), "operator");
            fail("ReviewPersistenceException is expected, when failed to create connection.");
        } catch (ReviewPersistenceException e) {
            // good
        }
    }

    /**
     * <p>
     * Test getAllCommentTypes(), when the connection name is invalid,
     * failed to create connection, ReviewPersistenceException is expected.
     * </p>
     */
    public void testGetAllCommentTypes_Failed() {
        try {
            persistenceInvalid.getAllCommentTypes();
            fail("ReviewPersistenceException is expected, when failed to create connection.");
        } catch (ReviewPersistenceException e) {
            // good
        }
    }

    /**
     * <p>
     * Create a Review with id is 1.
     * </p>
     *
     * @return a Review instance
     */
    private Review createReview() {
        return createReview(1);
    }

    /**
     * <p>
     * Create a Review with the given id.
     * </p>
     *
     * @return a Review instance
     *
     * @param reviewId the review id
     */
    private Review createReview(int reviewId) {
        Review review = new Review(reviewId);
        review.setAuthor(1);
        review.setScorecard(1);
        review.setSubmission(1);
        review.setCommitted(true);
        review.setScore(new Float(89.1f));

        review.addComment(createComment(100));
        review.addComment(createComment(200));

        review.addItem(createReviewItem(300));

        return review;
    }

    /**
     * <p>
     * Create a Item with the given id.
     * </p>
     *
     * @return a Item instance
     *
     * @param itemId the item id
     */
    private Item createReviewItem(int itemId) {
        Item item = new Item(itemId);
        item.setQuestion(1);
        item.setAnswer("answer " + itemId);
        item.addComment(createComment(itemId + 1));
        return item;
    }

    /**
     * <p>
     * Create a comment with the given id.
     * </p>
     *
     * @return a comment instance
     *
     * @param commentId the comment id
     */
    private Comment createComment(int commentId) {
        Comment comment = new Comment(commentId);
        comment.setAuthor(1);
        comment.setExtraInfo("extraInfo " + commentId);
        comment.setCommentType(new CommentType(1, "CommentType"));
        comment.setComment("content" + commentId);
        return comment;
    }
}