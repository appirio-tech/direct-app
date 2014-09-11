/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.review;

import com.topcoder.management.review.data.Comment;
import com.topcoder.management.review.data.CommentType;
import com.topcoder.management.review.data.Item;
import com.topcoder.management.review.data.Review;

import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.search.builder.filter.GreaterThanFilter;
import com.topcoder.search.builder.filter.LessThanFilter;

import com.topcoder.util.config.ConfigManager;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * <p>
 * This class tests the <code>DefaultReviewManager</code> class. It tests the
 * DefaultReviewManager(), DefaultReviewManager(String) and
 * DefaultReviewManager(ReviewPersistence) constructors; createReview(Review, String),
 * updateReview(Review, String), searchReviews(Filter, boolean), getReview(long),
 * addReviewComment(long, Comment, String), addItemComment(long, Comment, String) and
 * getAllCommentTypes() methods.
 * </p>
 *
 * @author icyriver
 * @version 1.0
 */
public class DefaultReviewManagerTest extends TestCase {
    /**
     * <p>
     * The default namespace of <code>DefaultReviewManager</code> for test.
     * </p>
     */
    private static final String DEFAULT_NAMESPACE = "com.topcoder.management.review.DefaultReviewManager";

    /**
     * <p>
     * The test namespace of <code>DefaultReviewManager</code> for test.
     * </p>
     */
    private static final String TEST_NAMESPACE = "com.topcoder.management.review.DefaultReviewManager.OnlyClass";

    /**
     * <p>
     * The invalid namespace of <code>DefaultReviewManager</code> for test.
     * </p>
     */
    private static final String MISSINGPS_NAMESPACE =
        "com.topcoder.management.review.DefaultReviewManager.Exception_MissingPersistence";

    /**
     * <p>
     * The invalid namespace of <code>DefaultReviewManager</code> for test.
     * </p>
     */
    private static final String MISSINGCLASS_NAMESPACE =
        "com.topcoder.management.review.DefaultReviewManager.Exception_MissingClass";

    /**
     * <p>
     * The invalid namespace of <code>DefaultReviewManager</code> for test.
     * </p>
     */
    private static final String WRONGTYPE_NAMESPACE =
        "com.topcoder.management.review.DefaultReviewManager.Exception_WrongType";

    /**
     * <p>
     * The invalid namespace of <code>DefaultReviewManager</code> for test.
     * </p>
     */
    private static final String EMPTYSTRING_NAMESPACE =
        "com.topcoder.management.review.DefaultReviewManager.Exception_EmptyString";

    /**
     * <p>
     * The config file path of <code>DefaultReviewManager</code> for test.
     * </p>
     */
    private static final String CONFIG_FILE = "DefaultReviewManagerConfig.xml";

    /**
     * <p>
     * The instance of <code>EqualToFilter</code> for unit test.
     * </p>
     */
    private Filter equalFilter = null;

    /**
     * <p>
     * The instance of <code>LessThanFilter</code> for unit test.
     * </p>
     */
    private Filter lessThanFilter = null;

    /**
     * <p>
     * The instance of <code>GreaterThanFilter</code> for unit test.
     * </p>
     */
    private Filter greaterThanFilter = null;

    /**
     * <p>
     * The instance of <code>ReviewPersistence</code> for unit test.
     * </p>
     */
    private ReviewPersistence persistence = null;

    /**
     * <p>
     * The instance of <code>DefaultReviewManager</code> for unit test.
     * </p>
     */
    private ReviewManager manager = null;

    /**
     * <p>
     * Returns the test suite of <code>DefaultReviewManagerTest</code>.
     * </p>
     *
     * @return the test suite of <code>DefaultReviewManagerTest</code>.
     */
    public static Test suite() {
        return new TestSuite(DefaultReviewManagerTest.class);
    }

    /**
     * <p>
     * Set up the unit test envionment here.
     * </p>
     *
     * @throws Exception if any configuration error occurred.
     */
    protected void setUp() throws Exception {
        // clear all namespaces here.
        TestHelper.clearNamespace();

        // load config xml file.
        ConfigManager cm = ConfigManager.getInstance();
        cm.add(CONFIG_FILE);

        // new the persistence instance.
        persistence = new MockReviewPersistence();

        // new a manager for test.
        manager = new DefaultReviewManager(persistence);

        // create some filters for test.
        lessThanFilter = new LessThanFilter("reviewer", new Long(10000));
        greaterThanFilter = new GreaterThanFilter("project", new Long(10001));
        equalFilter = new EqualToFilter("committed", new Integer(1));
    }

    /**
     * <p>
     * Clean the unit test environment.
     * </p>
     *
     * @throws Exception if configuration could not be clear.
     */
    protected void tearDown() throws Exception {
        // clear all namespaces.
        TestHelper.clearNamespace();
    }

    /**
     * <p>
     * Basic test of <code>DefaultReviewManager()</code> constructor.
     * </p>
     *
     * @throws Exception if any exception occurs when creating.
     */
    public void testDefaultReviewManagerCtor1_Basic() throws Exception {
        // check null here.
        assertNotNull("Create DefaultReviewManager failed.", new DefaultReviewManager());
    }

    /**
     * <p>
     * Detail test of <code>DefaultReviewManager()</code> constructor. Creates a instance and get
     * it's attributes for test.
     * </p>
     *
     * @throws Exception if any exception occurs when creating.
     */
    public void testDefaultReviewManagerCtor1_Detail()
        throws Exception {
        manager = new DefaultReviewManager();

        // check null here.
        assertNotNull("Create DefaultReviewManager failed.", manager);

        // check the persistence here.
        assertTrue("The inner persistence from config xml file is incorrect.",
            TestHelper.getFieldValue(manager, "persistence") instanceof MockReviewPersistence);

        // check the persistence's namespace here.
        assertEquals("The inner persistence from config xml file is incorrect.",
            "com.topcoder.management.review.MockReviewPersistence",
            ((MockReviewPersistence) TestHelper.getFieldValue(manager, "persistence")).getNamespace());
    }

    /**
     * <p>
     * Tests <code>DefaultReviewManager()</code> constructor for failure.
     * </p>
     *
     * <p>
     * It try to pass a invalid namespace into the constructor, <code>ConfigurationException</code>
     * is expected. It tests with missing 'persistence' property in config xml file.
     * </p>
     *
     * @throws Exception if any other exception occurs when creating.
     */
    public void testDefaultReviewManagerCtor1ForException_MissingPersistence()
        throws Exception {
        try {
            // clear all namespaces here.
            TestHelper.clearNamespace();

            // load config xml file.
            ConfigManager cm = ConfigManager.getInstance();
            cm.add("MissingPersistence.xml");

            manager = new DefaultReviewManager();

            // should not be here
            fail("ConfigurationException is expected here.");
        } catch (ConfigurationException e) {
            // should be here
        }
    }

    /**
     * <p>
     * Tests <code>DefaultReviewManager()</code> constructor for failure.
     * </p>
     *
     * <p>
     * It try to pass a invalid namespace into the constructor, <code>ConfigurationException</code>
     * is expected. It tests with missing 'persistence' property in config xml file.
     * </p>
     *
     * @throws Exception if any other exception occurs when creating.
     */
    public void testDefaultReviewManagerCtor1ForException_EmptyPersistence()
        throws Exception {
        try {
            // clear all namespaces here.
            TestHelper.clearNamespace();

            // load config xml file.
            ConfigManager cm = ConfigManager.getInstance();
            cm.add("EmptyPersistence.xml");

            manager = new DefaultReviewManager();

            // should not be here
            fail("ConfigurationException is expected here.");
        } catch (ConfigurationException e) {
            // should be here
        }
    }

    /**
     * <p>
     * Basic test of <code>DefaultReviewManager(String)</code> constructor.
     * </p>
     *
     * @throws Exception if any exception occurs when creating.
     */
    public void testDefaultReviewManagerCtor2_Basic() throws Exception {
        // check null here.
        assertNotNull("Create DefaultReviewManager failed.",
            new DefaultReviewManager(DEFAULT_NAMESPACE));

        assertNotNull("Create DefaultReviewManager failed.",
            new DefaultReviewManager(TEST_NAMESPACE));
    }

    /**
     * <p>
     * Detail test of <code>DefaultReviewManager(String)</code> constructor. Creates a instance and
     * get it's attributes for test. It test with both persistence namespace and class are
     * existent.
     * </p>
     *
     * @throws Exception if any exception occurs when creating.
     */
    public void testDefaultReviewManagerCtor2_Detail()
        throws Exception {
        manager = new DefaultReviewManager(DEFAULT_NAMESPACE);

        // check null here.
        assertNotNull("Create DefaultReviewManager failed.", manager);

        // check the persistence here.
        assertTrue("The inner persistence from config xml file is incorrect.",
            TestHelper.getFieldValue(manager, "persistence") instanceof MockReviewPersistence);

        // check the persistence's namespace here.
        assertEquals("The inner persistence from config xml file is incorrect.",
            "com.topcoder.management.review.MockReviewPersistence",
            ((MockReviewPersistence) TestHelper.getFieldValue(manager, "persistence")).getNamespace());
    }

    /**
     * <p>
     * Detail test of <code>DefaultReviewManager(String)</code> constructor. Creates a instance and
     * get it's attributes for test. It test with only persistence class name is existent.
     * </p>
     *
     * @throws Exception if any exception occurs when creating.
     */
    public void testDefaultReviewManagerCtor2_OnlyClass_Detail()
        throws Exception {
        manager = new DefaultReviewManager(TEST_NAMESPACE);

        // check null here.
        assertNotNull("Create DefaultReviewManager failed.", manager);

        // check the persistence here.
        assertTrue("The inner persistence from config xml file is incorrect.",
            TestHelper.getFieldValue(manager, "persistence") instanceof MockReviewPersistence);

        // check the persistence's namespace here.
        assertNull("The inner persistence from config xml file is incorrect.",
            ((MockReviewPersistence) TestHelper.getFieldValue(manager, "persistence")).getNamespace());
    }

    /**
     * <p>
     * Tests <code>DefaultReviewManager(String)</code> constructor for failure.
     * </p>
     *
     * <p>
     * It try to pass a <code>null</code> namespace into the constructor,
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception if any other exception occurs when creating.
     */
    public void testDefaultReviewManagerCtor2ForException_Null()
        throws Exception {
        try {
            manager = new DefaultReviewManager((String) null);

            // should not be here
            fail("IllegalArgumentException is expected here.");
        } catch (IllegalArgumentException e) {
            // should be here
        }
    }

    /**
     * <p>
     * Tests <code>DefaultReviewManager(String)</code> constructor for failure.
     * </p>
     *
     * <p>
     * It try to pass a empty string as the namespace into the constructor,
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception if any other exception occurs when creating.
     */
    public void testDefaultReviewManagerCtor2ForException_EmptyString()
        throws Exception {
        try {
            manager = new DefaultReviewManager(" ");

            // should not be here
            fail("IllegalArgumentException is expected here.");
        } catch (IllegalArgumentException e) {
            // should be here
        }
    }

    /**
     * <p>
     * Tests <code>DefaultReviewManager(String)</code> constructor for failure.
     * </p>
     *
     * <p>
     * It try to pass a invalid namespace into the constructor, <code>ConfigurationException</code>
     * is expected. It tests with missing 'persistence' property in config xml file.
     * </p>
     *
     * @throws Exception if any other exception occurs when creating.
     */
    public void testDefaultReviewManagerCtor2ForException_MissingPersistence()
        throws Exception {
        try {
            manager = new DefaultReviewManager(MISSINGPS_NAMESPACE);

            // should not be here
            fail("ConfigurationException is expected here.");
        } catch (ConfigurationException e) {
            // should be here
        }
    }

    /**
     * <p>
     * Tests <code>DefaultReviewManager(String)</code> constructor for failure.
     * </p>
     *
     * <p>
     * It try to pass a invalid namespace into the constructor, <code>ConfigurationException</code>
     * is expected. It tests with missing 'persistence_class' property in config xml file.
     * </p>
     *
     * @throws Exception if any other exception occurs when creating.
     */
    public void testDefaultReviewManagerCtor2ForException_MissingClass()
        throws Exception {
        try {
            manager = new DefaultReviewManager(MISSINGCLASS_NAMESPACE);

            // should not be here
            fail("ConfigurationException is expected here.");
        } catch (ConfigurationException e) {
            // should be here
        }
    }

    /**
     * <p>
     * Tests <code>DefaultReviewManager(String)</code> constructor for failure.
     * </p>
     *
     * <p>
     * It try to pass a invalid namespace into the constructor, <code>ConfigurationException</code>
     * is expected. It tests with incorrect 'persistence_class' property in config xml file.
     * </p>
     *
     * @throws Exception if any other exception occurs when creating.
     */
    public void testDefaultReviewManagerCtor2ForException_WrongType()
        throws Exception {
        try {
            manager = new DefaultReviewManager(WRONGTYPE_NAMESPACE);

            // should not be here
            fail("ConfigurationException is expected here.");
        } catch (ConfigurationException e) {
            // should be here
        }
    }

    /**
     * <p>
     * Tests <code>DefaultReviewManager(String)</code> constructor for failure.
     * </p>
     *
     * <p>
     * It try to pass a invalid namespace into the constructor, <code>ConfigurationException</code>
     * is expected. It tests with empty value of property in config xml file.
     * </p>
     *
     * @throws Exception if any other exception occurs when creating.
     */
    public void testDefaultReviewManagerCtor2ForException_Empty()
        throws Exception {
        try {
            manager = new DefaultReviewManager(EMPTYSTRING_NAMESPACE);

            // should not be here
            fail("ConfigurationException is expected here.");
        } catch (ConfigurationException e) {
            // should be here
        }
    }

    /**
     * <p>
     * Basic test of <code>DefaultReviewManager(ReviewPersistence)</code> constructor.
     * </p>
     *
     * @throws Exception if any exception occurs when creating.
     */
    public void testDefaultReviewManagerCtor3_Basic() throws Exception {
        // check null here.
        assertNotNull("Create DefaultReviewManager failed.", new DefaultReviewManager(persistence));
    }

    /**
     * <p>
     * Detail test of <code>DefaultReviewManager(ReviewPersistence)</code> constructor. Creates a
     * instance and get it's attributes for test.
     * </p>
     *
     * @throws Exception if any exception occurs when creating.
     */
    public void testDefaultReviewManagerCtor3_Detail()
        throws Exception {
        manager = new DefaultReviewManager(persistence);

        // check null here.
        assertNotNull("Create DefaultReviewManager failed.", manager);

        // check the persistence here.
        assertEquals("The inner persistence from config xml file is incorrect.", persistence,
            TestHelper.getFieldValue(manager, "persistence"));
    }

    /**
     * <p>
     * Tests <code>DefaultReviewManager(ReviewPersistence)</code> constructor for failure.
     * </p>
     *
     * <p>
     * It try to pass a <code>null</code> persistence into the constructor,
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception if any other exception occurs when creating.
     */
    public void testDefaultReviewManagerCtor3ForException_Null()
        throws Exception {
        try {
            manager = new DefaultReviewManager((ReviewPersistence) null);

            // should not be here
            fail("IllegalArgumentException is expected here.");
        } catch (IllegalArgumentException e) {
            // should be here
        }
    }

    /**
     * <p>
     * Test of <code>createReview(Review, String)</code> method. It tests for creating a simple
     * review into persistence only once.
     * </p>
     *
     * @throws Exception if any other exception occurs when creating review entiry.
     */
    public void testCreateReview_Simple_Once() throws Exception {
        // create a very simple review entiry
        Review review = new Review(1);

        // create a review entiry into persistence.
        String operator = "createReviewer";
        manager.createReview(review, operator);

        // get and test it.
        assertEquals("The review entiry does not create into persistence correctly.", 1,
            manager.getReview(1).getId());
        assertEquals("The review entiry does not create into persistence correctly.", operator,
            manager.getReview(1).getCreationUser());
        assertEquals("The review entiry does not create into persistence correctly.", operator,
            manager.getReview(1).getModificationUser());
    }

    /**
     * <p>
     * Test of <code>createReview(Review, String)</code> method. It tests for creating some simple
     * reviews into persistence more times.
     * </p>
     *
     * @throws Exception if any other exception occurs when creating review entiry.
     */
    public void testCreateReview_Simple_MoreTimes() throws Exception {
        // create some simple review entiries into persistence.
        String operator = "createReviewer";

        for (int i = 1; i < 10; i++) {
            manager.createReview(new Review(i), operator);
        }

        // get and test it.
        for (int i = 1; i < 10; i++) {
            assertEquals("The review entiry does not create into persistence correctly.", i,
                manager.getReview(i).getId());
            assertEquals("The review entiry does not create into persistence correctly.", operator,
                manager.getReview(i).getCreationUser());
            assertEquals("The review entiry does not create into persistence correctly.", operator,
                manager.getReview(i).getModificationUser());
        }
    }

    /**
     * <p>
     * Test of <code>createReview(Review, String)</code> method. It tests for creating a complex
     * review into persistence only once.
     * </p>
     *
     * @throws Exception if any other exception occurs when creating review entiry.
     */
    public void testCreateReview_Complex_Once() throws Exception {
        // create a review entiry and add some items and comment.
        Review review = new Review(1);
        Item item2 = new Item(2);
        item2.addComment(new Comment(2, 10000, "ok"));
        review.addItem(new Item(1));
        review.addItem(item2);
        review.addComment(new Comment(1, 10000, "good"));
        review.addComment(new Comment(2, 10000, "good"));

        // create a review entiry into persistence.
        String operator = "createReviewer";
        manager.createReview(review, operator);

        // get and test it.
        Review getReview = manager.getReview(1);
        assertEquals("The review entiry does not create into persistence correctly.", 1,
            getReview.getId());
        assertEquals("The review entiry does not create into persistence correctly.", 2,
            getReview.getNumberOfComments());
        assertEquals("The review entiry does not create into persistence correctly.", 2,
            getReview.getNumberOfItems());
        assertEquals("The review entiry does not create into persistence correctly.", operator,
            getReview.getCreationUser());
        assertEquals("The review entiry does not create into persistence correctly.", operator,
            getReview.getModificationUser());
    }

    /**
     * <p>
     * Test of <code>createReview(Review, String)</code> method. It tests for creating some complex
     * reviews into persistence more times.
     * </p>
     *
     * @throws Exception if any other exception occurs when creating review entiry.
     */
    public void testCreateReview_Complex_MoreTimes() throws Exception {
        // create some review entiries and add some items and comment.
        Review review1 = new Review(1);
        review1.addItem(new Item(10001));

        Item item1 = new Item(10002);
        item1.addComment(new Comment(2, 1001, "This is a great item."));
        review1.addItem(item1);
        review1.addComment(new Comment(1, 1001, "ok"));
        review1.addComment(new Comment(2, 1001, "good"));

        Review review2 = new Review(2);
        review2.addItem(new Item(20001));
        review2.addItem(new Item(20002));
        review2.addItem(new Item(20003));
        review2.addComment(new Comment(1, 1001, "ok"));

        // create reviews into persistence.
        manager.createReview(review1, "createReviewer1");
        manager.createReview(review2, "createReviewer2");

        // get and test it.
        Review getReview = manager.getReview(1);
        assertEquals("The review entiry does not create into persistence correctly.", 1,
            getReview.getId());
        assertEquals("The review entiry does not create into persistence correctly.", 2,
            getReview.getNumberOfComments());
        assertEquals("The review entiry does not create into persistence correctly.", 2,
            getReview.getNumberOfItems());

        getReview = manager.getReview(2);
        assertEquals("The review entiry does not create into persistence correctly.", 2,
            getReview.getId());
        assertEquals("The review entiry does not create into persistence correctly.", 1,
            getReview.getNumberOfComments());
        assertEquals("The review entiry does not create into persistence correctly.", 3,
            getReview.getNumberOfItems());
    }

    /**
     * <p>
     * Tests <code>createReview(Review, String)</code> method for failure.
     * </p>
     *
     * <p>
     * It try to pass a <code>null</code> review into the method,
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception if any other exception occurs when creating.
     */
    public void testCreateReviewForException_NullReview()
        throws Exception {
        try {
            manager.createReview(null, "reviewer");

            // should not be here
            fail("IllegalArgumentException is expected here.");
        } catch (IllegalArgumentException e) {
            // should be here
        }
    }

    /**
     * <p>
     * Tests <code>createReview(Review, String)</code> method for failure.
     * </p>
     *
     * <p>
     * It try to pass a <code>null</code> operator into the method,
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception if any other exception occurs when creating.
     */
    public void testCreateReviewForException_NullOperator()
        throws Exception {
        try {
            manager.createReview(new Review(1), null);

            // should not be here
            fail("IllegalArgumentException is expected here.");
        } catch (IllegalArgumentException e) {
            // should be here
        }
    }

    /**
     * <p>
     * Tests <code>createReview(Review, String)</code> method for failure.
     * </p>
     *
     * <p>
     * It try to pass a empty string as operator into the method,
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception if any other exception occurs when creating.
     */
    public void testCreateReviewForException_EmptyString()
        throws Exception {
        try {
            manager.createReview(new Review(1), " ");

            // should not be here
            fail("IllegalArgumentException is expected here.");
        } catch (IllegalArgumentException e) {
            // should be here
        }
    }

    /**
     * <p>
     * Tests <code>createReview(Review, String)</code> method for failure.
     * </p>
     *
     * <p>
     * It try to create duplicated review into the persistence,
     * <code>ReviewManagementException</code> is expected.
     * </p>
     *
     * @throws Exception if any other exception occurs when creating.
     */
    public void testCreateReviewForException_DuplicateReview()
        throws Exception {
        try {
            Review review = new Review(1);
            manager.createReview(review, "reviewer");
            manager.createReview(review, "reviewer");

            // should not be here
            fail("ReviewManagementException is expected here.");
        } catch (ReviewManagementException e) {
            // should be here
        }
    }

    /**
     * <p>
     * Tests <code>createReview(Review, String)</code> method for failure.
     * </p>
     *
     * <p>
     * It try to create a review into the invalid persistence,
     * <code>ReviewManagementException</code> is expected.
     * </p>
     *
     * @throws Exception if any other exception occurs when creating.
     */
    public void testCreateReviewForException_PersistenceException()
        throws Exception {
        try {
            // invalid the persistence here.
            ((MockReviewPersistence) persistence).stopState();
            manager.createReview(new Review(1), "reviewer");

            // should not be here
            fail("ReviewManagementException is expected here.");
        } catch (ReviewManagementException e) {
            // should be here
        }
    }

    /**
     * <p>
     * Test of <code>updateReview(Review, String)</code> method. It tests for updating a simple
     * review into persistence only once.
     * </p>
     *
     * @throws Exception if any other exception occurs when updating review entiry.
     */
    public void testUpdateReview_Simple_Once() throws Exception {
        // create a very simple review entiry
        Review review = new Review(1);

        // create a review entiry into persistence.
        manager.createReview(review, "createReviewer");

        // update this review.
        Review updatedReview = new Review(1);
        manager.updateReview(updatedReview, "updateReviewer");

        // get and test it.
        assertEquals("The review entiry does not update into persistence correctly.", 1,
            manager.getReview(1).getId());
        assertEquals("The review entiry does not update into persistence correctly.",
            "createReviewer", manager.getReview(1).getCreationUser());
        assertEquals("The review entiry does not update into persistence correctly.",
            "updateReviewer", manager.getReview(1).getModificationUser());
    }

    /**
     * <p>
     * Test of <code>updateReview(Review, String)</code> method. It tests for updating some simple
     * review into persistence more times.
     * </p>
     *
     * @throws Exception if any other exception occurs when updating review entiry.
     */
    public void testUpdateReview_Simple_MoreTimes() throws Exception {
        // create a very simple review entiry
        Review review = new Review(1);

        // create a review entiry into persistence.
        manager.createReview(review, "createReviewer");

        // update this review.
        Review updatedReview = new Review(1);

        for (int i = 0; i < 10; i++) {
            manager.updateReview(updatedReview, "updateReviewer" + i);
        }

        // get and test it.
        assertEquals("The review entiry does not update into persistence correctly.", 1,
            manager.getReview(1).getId());
        assertEquals("The review entiry does not update into persistence correctly.",
            "createReviewer", manager.getReview(1).getCreationUser());
        assertEquals("The review entiry does not update into persistence correctly.",
            "updateReviewer9", manager.getReview(1).getModificationUser());
    }

    /**
     * <p>
     * Test of <code>updateReview(Review, String)</code> method. It tests for updating a complex
     * review into persistence only once.
     * </p>
     *
     * @throws Exception if any other exception occurs when updating review entiry.
     */
    public void testUpdateReview_Complex_Once() throws Exception {
        // create a very simple review entiry
        Review review = new Review(1);
        review.addItem(new Item(1));

        // create a review entiry into persistence.
        manager.createReview(review, "createReviewer");

        // update this review.
        Review updatedReview = new Review(1);
        updatedReview.addItem(new Item(1));
        updatedReview.addItem(new Item(2));
        updatedReview.addComment(new Comment(1, 10000, "good"));
        manager.updateReview(updatedReview, "updateReviewer");

        // get and test it.
        assertEquals("The review entiry does not update into persistence correctly.", 1,
            manager.getReview(1).getId());
        assertEquals("The review entiry does not update into persistence correctly.",
            "createReviewer", manager.getReview(1).getCreationUser());
        assertEquals("The review entiry does not update into persistence correctly.",
            "updateReviewer", manager.getReview(1).getModificationUser());
        assertEquals("The review entiry does not update into persistence correctly.", 1,
            manager.getReview(1).getNumberOfComments());
        assertEquals("The review entiry does not update into persistence correctly.", 2,
            manager.getReview(1).getNumberOfItems());
    }

    /**
     * <p>
     * Test of <code>updateReview(Review, String)</code> method. It tests for updating some complex
     * reviews into persistence more times.
     * </p>
     *
     * @throws Exception if any other exception occurs when updating review entiry.
     */
    public void testUpdateReview_Complex_MoreTimes() throws Exception {
        // create a very simple review entiry
        Review review = new Review(1);
        review.addItem(new Item(1));

        // create a review entiry into persistence.
        manager.createReview(review, "createReviewer");

        // update this review.
        Review updatedReview = new Review(1);

        for (int i = 1; i < 10; i++) {
            updatedReview.addItem(new Item(i));
            manager.updateReview(updatedReview, "updateReviewer" + i);
        }

        // get and test it.
        assertEquals("The review entiry does not update into persistence correctly.", 1,
            manager.getReview(1).getId());
        assertEquals("The review entiry does not update into persistence correctly.",
            "createReviewer", manager.getReview(1).getCreationUser());
        assertEquals("The review entiry does not update into persistence correctly.",
            "updateReviewer9", manager.getReview(1).getModificationUser());
        assertEquals("The review entiry does not update into persistence correctly.", 9,
            manager.getReview(1).getNumberOfItems());
    }

    /**
     * <p>
     * Tests <code>updateReview(Review, String)</code> method for failure.
     * </p>
     *
     * <p>
     * It try to pass a <code>null</code> review into the method,
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception if any other exception occurs when updating.
     */
    public void testUpdateReviewForException_NullReview()
        throws Exception {
        try {
            manager.updateReview(null, "reviewer");

            // should not be here
            fail("IllegalArgumentException is expected here.");
        } catch (IllegalArgumentException e) {
            // should be here
        }
    }

    /**
     * <p>
     * Tests <code>updateReview(Review, String)</code> method for failure.
     * </p>
     *
     * <p>
     * It try to pass a <code>null</code> operator into the method,
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception if any other exception occurs when updating.
     */
    public void testUpdateReviewForException_NullOperator()
        throws Exception {
        try {
            manager.createReview(new Review(1), "reviewer");
            manager.updateReview(new Review(1), null);

            // should not be here
            fail("IllegalArgumentException is expected here.");
        } catch (IllegalArgumentException e) {
            // should be here
        }
    }

    /**
     * <p>
     * Tests <code>updateReview(Review, String)</code> method for failure.
     * </p>
     *
     * <p>
     * It try to update a inexistent review into the persistence,
     * <code>ReviewManagementException</code> is expected.
     * </p>
     *
     * @throws Exception if any other exception occurs when updating.
     */
    public void testUpdateReviewForException_ReviewEntityNotFound()
        throws Exception {
        try {
            manager.updateReview(new Review(1), "reviewer");

            // should not be here
            fail("ReviewManagementException is expected here.");
        } catch (ReviewManagementException e) {
            // should be here
        }
    }

    /**
     * <p>
     * Tests <code>updateReview(Review, String)</code> method for failure.
     * </p>
     *
     * <p>
     * It try to update a inexistent review into the persistence,
     * <code>ReviewManagementException</code> is expected.
     * </p>
     *
     * @throws Exception if any other exception occurs when updating.
     */
    public void testUpdateReviewForException_PersistenceException()
        throws Exception {
        try {
            // invalid the persistence here.
            ((MockReviewPersistence) persistence).stopState();
            manager.updateReview(new Review(1), "reviewer");

            // should not be here
            fail("ReviewManagementException is expected here.");
        } catch (ReviewManagementException e) {
            // should be here
        }
    }

    /**
     * <p>
     * Test of <code>getReview(long)</code> method. It tests for getting a review into persistence
     * only once.
     * </p>
     *
     * @throws Exception if any other exception occurs when getting review entiry.
     */
    public void testGetReview_Once() throws Exception {
        // create a very simple review entiry
        Review review = new Review(1);

        // create a review entiry into persistence.
        String operator = "createReviewer";
        manager.createReview(review, operator);

        // get and test it.
        assertEquals("The review entiry getting from persistence is incorrect.", 1,
            manager.getReview(1).getId());
        assertEquals("The review entiry getting from persistence is incorrect.", operator,
            manager.getReview(1).getCreationUser());
        assertEquals("The review entiry getting from persistence is incorrect.", operator,
            manager.getReview(1).getModificationUser());
    }

    /**
     * <p>
     * Test of <code>getReview(long)</code> method. It tests for getting a review into persistence
     * only once.
     * </p>
     *
     * @throws Exception if any other exception occurs when getting review entiry.
     */
    public void testGetReview_MoreTimes() throws Exception {
        // create some simple review entiries into persistence.
        String operator = "createReviewer";

        for (int i = 1; i < 10; i++) {
            manager.createReview(new Review(i), operator + i);
        }

        // get and test it.
        for (int i = 1; i < 10; i++) {
            assertEquals("The review entiry getting from persistence is incorrect.", i,
                manager.getReview(i).getId());
            assertEquals("The review entiry getting from persistence is incorrect.", operator + i,
                manager.getReview(i).getCreationUser());
            assertEquals("The review entiry getting from persistence is incorrect.", operator + i,
                manager.getReview(i).getModificationUser());
        }
    }

    /**
     * <p>
     * Tests <code>getReview(long)</code> method for failure.
     * </p>
     *
     * <p>
     * It try to pass a <code>-1</code> as review id into the method,
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception if any other exception occurs when getting.
     */
    public void testGetReviewForException_Negative() throws Exception {
        try {
            manager.getReview(-1);

            // should not be here
            fail("IllegalArgumentException is expected here.");
        } catch (IllegalArgumentException e) {
            // should be here
        }
    }

    /**
     * <p>
     * Tests <code>getReview(long)</code> method for failure.
     * </p>
     *
     * <p>
     * It try to find a inexistent review from the persistence,
     * <code>ReviewManagementException</code> is expected.
     * </p>
     *
     * @throws Exception if any other exception occurs when getting.
     */
    public void testGetReviewForException_ReviewEntityNotFound()
        throws Exception {
        try {
            manager.createReview(new Review(1), "reviewer");
            manager.getReview(2);

            // should not be here
            fail("ReviewManagementException is expected here.");
        } catch (ReviewManagementException e) {
            // should be here
        }
    }

    /**
     * <p>
     * Tests <code>getReview(long)</code> method for failure.
     * </p>
     *
     * <p>
     * It try to find a review from the invalid persistence, <code>ReviewManagementException</code>
     * is expected.
     * </p>
     *
     * @throws Exception if any other exception occurs when getting.
     */
    public void testGetReviewForException_PersistenceException()
        throws Exception {
        try {
            // invalid the persistence here.
            ((MockReviewPersistence) persistence).stopState();
            manager.getReview(2);

            // should not be here
            fail("ReviewManagementException is expected here.");
        } catch (ReviewManagementException e) {
            // should be here
        }
    }

    /**
     * <p>
     * Test of <code>searchReviews(Filter, boolean)</code> method. It tests for searching review
     * from persistence with simple filter.
     * </p>
     *
     * @throws Exception if any other exception occurs when searching review entiry.
     */
    public void testSearchReviews_SimpleFilter() throws Exception {
        Review[] reviews = manager.searchReviews(equalFilter, true);

        // check the result here.
        assertEquals("The searching result is incorrect.", 0, reviews.length);
    }

    /**
     * <p>
     * Test of <code>searchReviews(Filter, boolean)</code> method. It tests for searching review
     * from persistence with chain filter.
     * </p>
     *
     * @throws Exception if any other exception occurs when searching review entiry.
     */
    public void testSearchReviews_ChainFilter() throws Exception {
        // create a complex chain filter here.
        ChainFilter filter = new ChainFilter(equalFilter);
        filter = filter.and(lessThanFilter).or(greaterThanFilter).not();

        // check the result here.
        Review[] reviews = manager.searchReviews(filter.getFilter(), true);
        assertEquals("The searching result is incorrect.", 0, reviews.length);
    }

    /**
     * <p>
     * Tests <code>searchReviews(Filter, boolean)</code> method for failure.
     * </p>
     *
     * <p>
     * It try to pass a <code>null</code> filter into the method,
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception if any other exception occurs when creating.
     */
    public void testSearchReviewsForException_NullFilter()
        throws Exception {
        try {
            manager.searchReviews(null, false);

            // should not be here
            fail("IllegalArgumentException is expected here.");
        } catch (IllegalArgumentException e) {
            // should be here
        }
    }

    /**
     * <p>
     * Tests <code>searchReviews(Filter, boolean)</code> method for failure.
     * </p>
     *
     * <p>
     * It try to search a review from the invalid persistence,
     * <code>ReviewManagementException</code> is expected.
     * </p>
     *
     * @throws Exception if any other exception occurs when creating.
     */
    public void testSearchReviewsForException_PersistenceException()
        throws Exception {
        try {
            // invalid the persistence here.
            ((MockReviewPersistence) persistence).stopState();
            manager.searchReviews(equalFilter, false);

            // should not be here
            fail("ReviewManagementException is expected here.");
        } catch (ReviewManagementException e) {
            // should be here
        }
    }

    /**
     * <p>
     * Test of <code>addReviewComment(long, Comment, String)</code> method. It tests for adding
     * comment for review in persistence only once.
     * </p>
     *
     * @throws Exception if any other exception occurs when adding comment for review entiry.
     */
    public void testAddReviewComment_Once() throws Exception {
        // create some review entiries into persistence.
        String operator = "createReviewer";

        for (int i = 1; i < 10; i++) {
            manager.createReview(new Review(i), operator);
        }

        // add comment for review.
        manager.addReviewComment(1, new Comment(1, 10000, "good"), "operator");

        // get and test it.
        Review getReview = manager.getReview(1);
        assertEquals("The comment for review entiry does not add into persistence correctly.", 1,
            getReview.getNumberOfComments());
    }

    /**
     * <p>
     * Test of <code>addReviewComment(long, Comment, String)</code> method. It tests for adding
     * some comments for review in persistence more times.
     * </p>
     *
     * @throws Exception if any other exception occurs when adding comment for review entiry.
     */
    public void testAddReviewComment_MoreTimes() throws Exception {
        // create some review entiries into persistence.
        String operator = "createReviewer";

        for (int i = 1; i < 10; i++) {
            manager.createReview(new Review(i), operator);
        }

        // add comment for review.
        manager.addReviewComment(1, new Comment(1, 10000, "good"), "operator");
        manager.addReviewComment(2, new Comment(2, 10000, "good"), "operator");
        manager.addReviewComment(1, new Comment(2, 10000, "ok"), "operator");

        // get and test it.
        Review getReview = manager.getReview(1);
        assertEquals("The comment for review entiry does not add into persistence correctly.", 2,
            getReview.getNumberOfComments());
        getReview = manager.getReview(2);
        assertEquals("The comment for review entiry does not add into persistence correctly.", 1,
            getReview.getNumberOfComments());
    }

    /**
     * <p>
     * Test of <code>addReviewComment(long, Comment, String)</code> method. It tests for adding
     * some comments for complex review in persistence.
     * </p>
     *
     * @throws Exception if any other exception occurs when adding comment for review entiry.
     */
    public void testAddReviewComment_Complex() throws Exception {
        // create a review entiry and add some items and comment.
        Review review = new Review(1);
        Item item2 = new Item(2);
        item2.addComment(new Comment(2, 10000, "ok"));
        review.addItem(new Item(1));
        review.addItem(item2);
        review.addComment(new Comment(1, 10000, "good"));
        review.addComment(new Comment(2, 10000, "good"));

        // create a review entiry into persistence.
        String operator = "createReviewer";
        manager.createReview(review, operator);

        // add a new comment for review entiry.
        manager.addReviewComment(1, new Comment(3, 10000, "good"), "operator");

        // get and test it.
        Review getReview = manager.getReview(1);
        assertEquals("The comment for review entiry does not add into persistence correctly.", 3,
            getReview.getNumberOfComments());
    }

    /**
     * <p>
     * Tests <code>addReviewComment(long, Comment, String)</code> method for failure.
     * </p>
     *
     * <p>
     * It try to pass a <code>-1</code> as review id into the method,
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception if any other exception occurs when adding.
     */
    public void testAddReviewCommentForException_Negative()
        throws Exception {
        try {
            manager.addReviewComment(-1, new Comment(), "operator");

            // should not be here
            fail("IllegalArgumentException is expected here.");
        } catch (IllegalArgumentException e) {
            // should be here
        }
    }

    /**
     * <p>
     * Tests <code>addReviewComment(long, Comment, String)</code> method for failure.
     * </p>
     *
     * <p>
     * It try to pass a <code>0</code> as review id into the method,
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception if any other exception occurs when getting.
     */
    public void testAddReviewCommentForException_Zero()
        throws Exception {
        try {
            manager.addReviewComment(0, new Comment(), "operator");

            // should not be here
            fail("IllegalArgumentException is expected here.");
        } catch (IllegalArgumentException e) {
            // should be here
        }
    }

    /**
     * <p>
     * Tests <code>addReviewComment(long, Comment, String)</code> method for failure.
     * </p>
     *
     * <p>
     * It try to pass a <code>null</code> operator into the method,
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception if any other exception occurs when adding.
     */
    public void testAddReviewCommentForException_NullString()
        throws Exception {
        try {
            manager.addReviewComment(1, new Comment(), null);

            // should not be here
            fail("IllegalArgumentException is expected here.");
        } catch (IllegalArgumentException e) {
            // should be here
        }
    }

    /**
     * <p>
     * Tests <code>addReviewComment(long, Comment, String)</code> method for failure.
     * </p>
     *
     * <p>
     * It try to pass a empty string as the operator into the method,
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception if any other exception occurs when adding.
     */
    public void testAddReviewCommentForException_EmptyString()
        throws Exception {
        try {
            manager.addReviewComment(1, new Comment(), "  ");

            // should not be here
            fail("IllegalArgumentException is expected here.");
        } catch (IllegalArgumentException e) {
            // should be here
        }
    }

    /**
     * <p>
     * Tests <code>addReviewComment(long, Comment, String)</code> method for failure.
     * </p>
     *
     * <p>
     * It try to pass a <code>null</code> operator into the method,
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception if any other exception occurs when adding.
     */
    public void testAddReviewCommentForException_NullComment()
        throws Exception {
        try {
            manager.addReviewComment(1, null, "operator");

            // should not be here
            fail("IllegalArgumentException is expected here.");
        } catch (IllegalArgumentException e) {
            // should be here
        }
    }

    /**
     * <p>
     * Tests <code>addReviewComment(long, Comment, String)</code> method for failure.
     * </p>
     *
     * <p>
     * It try to add comment for a inexistent review in the persistence,
     * <code>ReviewManagementException</code> is expected.
     * </p>
     *
     * @throws Exception if any other exception occurs when adding.
     */
    public void testAddReviewCommentForException_ReviewEntityNotFound()
        throws Exception {
        try {
            manager.addReviewComment(1, new Comment(), "operator");

            // should not be here
            fail("ReviewManagementException is expected here.");
        } catch (ReviewManagementException e) {
            // should be here
        }
    }

    /**
     * <p>
     * Tests <code>addReviewComment(long, Comment, String)</code> method for failure.
     * </p>
     *
     * <p>
     * It try to add comment for review in the invalid persistence,
     * <code>ReviewManagementException</code> is expected.
     * </p>
     *
     * @throws Exception if any other exception occurs when adding.
     */
    public void testAddReviewCommentForException_PersistenceException()
        throws Exception {
        try {
            manager.createReview(new Review(1), "reviewer");

            // invalid the persistence here.
            ((MockReviewPersistence) persistence).stopState();
            manager.addReviewComment(1, new Comment(), "operator");

            // should not be here
            fail("ReviewManagementException is expected here.");
        } catch (ReviewManagementException e) {
            // should be here
        }
    }

    /**
     * <p>
     * Test of <code>addItemComment(long, Comment, String)</code> method. It tests for adding
     * comment for item in persistence only once.
     * </p>
     *
     * @throws Exception if any other exception occurs when adding comment for review entiry.
     */
    public void testAddItemComment_Once() throws Exception {
        // create a review into persistence.
        String operator = "createReviewer";
        Review review = new Review(1);

        // add some items.
        for (int i = 1; i < 10; i++) {
            review.addItem(new Item(i));
        }

        manager.createReview(review, operator);

        // add a new comment for item.
        manager.addItemComment(1, new Comment(), "operator");

        // get and test it.
        Review getReview = manager.getReview(1);
        assertEquals("The comment for item does not add into persistence correctly.", 1,
            getReview.getItem(0).getNumberOfComments());
    }

    /**
     * <p>
     * Test of <code>addItemComment(long, Comment, String)</code> method. It tests for adding
     * comment for item in persistence more times.
     * </p>
     *
     * @throws Exception if any other exception occurs when adding comment for review entiry.
     */
    public void testAddItemComment_MoreTimes() throws Exception {
        // create a review into persistence.
        String operator = "createReviewer";
        Review review = new Review(1);

        // add some items.
        for (int i = 1; i < 10; i++) {
            review.addItem(new Item(i));
        }

        manager.createReview(review, operator);

        // add a new comment for item.
        manager.addItemComment(1, new Comment(), "operator");
        manager.addItemComment(2, new Comment(), "operator");
        manager.addItemComment(1, new Comment(), "operator");

        // get and test it.
        Review getReview = manager.getReview(1);
        assertEquals("The comment for item does not add into persistence correctly.", 2,
            getReview.getItem(0).getNumberOfComments());
        assertEquals("The comment for item does not add into persistence correctly.", 1,
            getReview.getItem(1).getNumberOfComments());
    }

    /**
     * <p>
     * Test of <code>addItemComment(long, Comment, String)</code> method. It tests for adding
     * comment for a complex item in persistence.
     * </p>
     *
     * @throws Exception if any other exception occurs when adding comment for review entiry.
     */
    public void testAddItemComment_Complex() throws Exception {
        // create a review entiry and add some items and comment.
        String operator = "createReviewer";
        Review review = new Review(1);
        Item item = new Item(2);
        item.addComment(new Comment(2, 10000, "ok"));
        review.addItem(new Item(1));
        review.addItem(item);

        manager.createReview(review, operator);

        // add a new comment for item.
        manager.addItemComment(2, new Comment(), "operator");

        // get and test it.
        Review getReview = manager.getReview(1);
        assertEquals("The comment for item does not add into persistence correctly.", 2,
            getReview.getItem(1).getNumberOfComments());
    }

    /**
     * <p>
     * Tests <code>addItemComment(long, Comment, String)</code> method for failure.
     * </p>
     *
     * <p>
     * It try to pass a <code>-1</code> as review id into the method,
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception if any other exception occurs when adding.
     */
    public void testAddItemCommentForException_Negative()
        throws Exception {
        try {
            manager.addItemComment(-1, new Comment(), "operator");

            // should not be here
            fail("IllegalArgumentException is expected here.");
        } catch (IllegalArgumentException e) {
            // should be here
        }
    }

    /**
     * <p>
     * Tests <code>addItemComment(long, Comment, String)</code> method for failure.
     * </p>
     *
     * <p>
     * It try to pass a <code>0</code> as review id into the method,
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception if any other exception occurs when adding.
     */
    public void testAddItemCommentForException_Zero() throws Exception {
        try {
            manager.addItemComment(0, new Comment(), "operator");

            // should not be here
            fail("IllegalArgumentException is expected here.");
        } catch (IllegalArgumentException e) {
            // should be here
        }
    }

    /**
     * <p>
     * Tests <code>addItemComment(long, Comment, String)</code> method for failure.
     * </p>
     *
     * <p>
     * It try to pass a <code>null</code> operator into the method,
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception if any other exception occurs when adding.
     */
    public void testAddItemCommentForException_NullString()
        throws Exception {
        try {
            manager.addItemComment(1, new Comment(), null);

            // should not be here
            fail("IllegalArgumentException is expected here.");
        } catch (IllegalArgumentException e) {
            // should be here
        }
    }

    /**
     * <p>
     * Tests <code>addItemComment(long, Comment, String)</code> method for failure.
     * </p>
     *
     * <p>
     * It try to pass a empty string as operator into the method,
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception if any other exception occurs when adding.
     */
    public void testAddItemCommentForException_EmptyString()
        throws Exception {
        try {
            manager.addItemComment(1, new Comment(), " ");

            // should not be here
            fail("IllegalArgumentException is expected here.");
        } catch (IllegalArgumentException e) {
            // should be here
        }
    }

    /**
     * <p>
     * Tests <code>addItemComment(long, Comment, String)</code> method for failure.
     * </p>
     *
     * <p>
     * It try to pass a <code>null</code> comment into the method,
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception if any other exception occurs when adding.
     */
    public void testAddItemCommentForException_NullComment()
        throws Exception {
        try {
            manager.addItemComment(1, null, "operator");

            // should not be here
            fail("IllegalArgumentException is expected here.");
        } catch (IllegalArgumentException e) {
            // should be here
        }
    }

    /**
     * <p>
     * Tests <code>addItemComment(long, Comment, String)</code> method for failure.
     * </p>
     *
     * <p>
     * It try to add comment for a inexistent item in the persistence,
     * <code>ReviewManagementException</code> is expected.
     * </p>
     *
     * @throws Exception if any other exception occurs when adding.
     */
    public void testAddItemCommentForException_ReviewEntityNotFound()
        throws Exception {
        try {
            manager.addItemComment(1, new Comment(), "operator");

            // should not be here
            fail("ReviewManagementException is expected here.");
        } catch (ReviewManagementException e) {
            // should be here
        }
    }

    /**
     * <p>
     * Tests <code>addItemComment(long, Comment, String)</code> method for failure.
     * </p>
     *
     * <p>
     * It try to add comment to the invalid persistence, <code>ReviewManagementException</code> is
     * expected.
     * </p>
     *
     * @throws Exception if any other exception occurs when adding.
     */
    public void testAddItemCommentForException_PersistenceException()
        throws Exception {
        try {
            Review review = new Review(1);

            // add some items.
            for (int i = 1; i < 10; i++) {
                review.addItem(new Item(i));
            }

            manager.createReview(review, "operator");

            // invalid the persistence here.
            ((MockReviewPersistence) persistence).stopState();
            manager.addItemComment(1, new Comment(), "operator");

            // should not be here
            fail("ReviewManagementException is expected here.");
        } catch (ReviewManagementException e) {
            // should be here
        }
    }

    /**
     * <p>
     * Test of <code>getAllCommentTypes()</code> method.
     * </p>
     *
     * @throws Exception if any other exception occurs when adding comment for review entiry.
     */
    public void testGetAllCommentTypes() throws Exception {
        CommentType[] types = manager.getAllCommentTypes();

        // check the types.
        assertEquals("The comment type from manager is incorrect.", 3, types.length);
        assertEquals("The comment type from manager is incorrect.", "Comment", types[0].getName());
        assertEquals("The comment type from manager is incorrect.", "Recommend", types[1].getName());
        assertEquals("The comment type from manager is incorrect.", "Required", types[2].getName());
    }

    /**
     * <p>
     * Tests <code>getAllCommentTypes()</code> method for failure.
     * </p>
     *
     * <p>
     * It try to get comment type from the invalid persistence,
     * <code>ReviewManagementException</code> is expected.
     * </p>
     *
     * @throws Exception if any other exception occurs when adding.
     */
    public void testGetAllCommentTypesForException_PersistenceException()
        throws Exception {
        try {
            // invalid the persistence here.
            ((MockReviewPersistence) persistence).stopState();
            manager.getAllCommentTypes();

            // should not be here
            fail("ReviewManagementException is expected here.");
        } catch (ReviewManagementException e) {
            // should be here
        }
    }
}
