/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.review.failuretests;

import java.util.Iterator;

import com.topcoder.management.review.ConfigurationException;
import com.topcoder.management.review.DefaultReviewManager;
import com.topcoder.management.review.DuplicateReviewEntityException;
import com.topcoder.management.review.ReviewEntityNotFoundException;
import com.topcoder.management.review.ReviewManagementException;
import com.topcoder.management.review.ReviewPersistence;
import com.topcoder.management.review.ReviewPersistenceException;
import com.topcoder.management.review.data.Comment;
import com.topcoder.management.review.data.Review;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.search.builder.filter.GreaterThanFilter;
import com.topcoder.util.config.ConfigManager;

import junit.framework.TestCase;

/**
 * Failure test for class <code>DefaultReviewManager </code>.
 *
 * @author Chenhong
 * @version 1.0
 */
public class TestDefaultReviewManagerFailure extends TestCase {

    /**
     * Represents the DefaultReviewManager instance for test.
     */
    private DefaultReviewManager manager = null;

    /**
     * Set up the enviroment.
     *
     * @throws Exception
     *             to junit.
     */
    public void setUp() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        for (Iterator iter = cm.getAllNamespaces(); iter.hasNext();) {
            cm.removeNamespace((String) iter.next());
        }

        cm.add("failuretests/config.xml");
        cm.add("failuretests/invalid1.xml");
        cm.add("failuretests/invalid2.xml");
        cm.add("failuretests/invalid3.xml");
        cm.add("failuretests/invalid4.xml");
        cm.add("failuretests/invalid5.xml");

        manager = new DefaultReviewManager();
    }

    /**
     * Tear down the enviroment. Remove all the namespaces in the config manager instance.
     *
     * @throws Exception
     *             to junit.
     */
    public void tearDown() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        for (Iterator iter = cm.getAllNamespaces(); iter.hasNext();) {
            cm.removeNamespace((String) iter.next());
        }
    }

    /**
     * Test constructor <code>DefaultReviewManager(String namespace) </code>. The namespace is null, and
     * IllegalArgumentException should be thrown.
     *
     * @throws Exception
     *             to junit.
     */
    public void testDefaultReviewManagerString_1() throws Exception {
        try {
            new DefaultReviewManager((String) null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // Ok.
        }
    }

    /**
     * Test constructor <code>DefaultReviewManager(String namespace) </code>. The namespace is empty, and
     * IllegalArgumentException should be thrown.
     *
     * @throws Exception
     *             to junit.
     */
    public void testDefaultReviewManagerString_2() throws Exception {
        try {
            new DefaultReviewManager("");
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // Ok.
        }
    }

    /**
     * Test constructor <code>DefaultReviewManager(String namespace) </code>. The namespace is empty, and
     * IllegalArgumentException should be thrown.
     *
     * @throws Exception
     *             to junit.
     */
    public void testDefaultReviewManagerString_3() throws Exception {
        try {
            new DefaultReviewManager("                        ");
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // Ok.
        }
    }

    /**
     * Test constructor <code>DefaultReviewManager(String namespace) </code>. If the config file is not valid,
     * ConfigurationException should be thrown.
     *
     * @throws Exception
     *             to junit.
     */
    public void testDefaultReviewManagerString_4() throws Exception {
        try {
            new DefaultReviewManager("invalid1");
            fail("ConfigurationException is expected.");
        } catch (ConfigurationException e) {
            // Ok.
        }
    }

    /**
     * Test constructor <code>DefaultReviewManager(String namespace) </code>. If the config file is not valid,
     * ConfigurationException should be thrown.
     *
     * @throws Exception
     *             to junit.
     */
    public void testDefaultReviewManagerString_5() throws Exception {
        try {
            new DefaultReviewManager("invalid2");
            fail("ConfigurationException is expected.");
        } catch (ConfigurationException e) {
            // Ok.
        }
    }

    /**
     * Test constructor <code>DefaultReviewManager(String namespace) </code>. If the config file is not valid,
     * ConfigurationException should be thrown.
     *
     * @throws Exception
     *             to junit.
     */
    public void testDefaultReviewManagerString_6() throws Exception {
        try {
            new DefaultReviewManager("invalid3");
            fail("ConfigurationException is expected.");
        } catch (ConfigurationException e) {
            // Ok.
        }
    }

    /**
     * Test constructor <code>DefaultReviewManager(String namespace) </code>. If the config file is not valid,
     * ConfigurationException should be thrown.
     *
     * @throws Exception
     *             to junit.
     */
    public void testDefaultReviewManagerString_7() throws Exception {
        try {
            new DefaultReviewManager("invalid4");
            fail("ConfigurationException is expected.");
        } catch (ConfigurationException e) {
            // Ok.
        }
    }

    /**
     * Test constructor <code>DefaultReviewManager(String namespace) </code>. If the config file is not valid,
     * ConfigurationException should be thrown.
     *
     * @throws Exception
     *             to junit.
     */
    public void testDefaultReviewManagerString_8() throws Exception {
        try {
            new DefaultReviewManager("invalid5");
            fail("ConfigurationException is expected.");
        } catch (ConfigurationException e) {
            // Ok.
        }
    }

    /**
     * Test constructor <code>DefaultReviewManager(ReviewPersistence persistence) </code>. If the parameter
     * persistence is null, IllegalArgumentException should be thrown.
     *
     */
    public void testDefaultReviewManagerReviewPersistence() {
        try {
            new DefaultReviewManager((ReviewPersistence) null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // Ok.
        }
    }

    /**
     * Test method <code>void createReview(Review review, String operator) </code>.
     *
     * @throws Exception
     *             to junit.
     */
    public void testCreateReview_1() throws Exception {
        Review r = new Review();
        r.setId(10);

        manager.createReview(r, "ok");

        try {
            manager.createReview(r, "secondTime");
            fail("ReviewManagementException is expected.");
        } catch (ReviewManagementException e) {
            // Ok.
        }
    }

    /**
     * Test method <code>void createReview(Review review, String operator) </code>.
     *
     * @throws Exception
     *             to junit.
     */
    public void testCreateReview_2() throws Exception {
        Review r = new Review();
        r.setId(10000);

        try {
            manager.createReview(r, "ok");
            fail("ReviewManagementException is expected.");
        } catch (ReviewManagementException e) {
            // Ok.
        }
    }

    /**
     * Test method <code>void updateReview(Review review, String operator) </code>.
     *
     */
    public void testUpdateReview_1() {
        Review r = new Review();
        r.setId(999);

        try {
            manager.updateReview(r, "ok");
            fail("ReviewManagementException is expected.");
        } catch (ReviewManagementException e) {
            // Ok.
        }
    }

    /**
     * Test method <code>void updateReview(Review review, String operator) </code>.
     *
     */
    public void testUpdateReview_2() {
        Review r = new Review();
        r.setId(999);

        try {
            manager.updateReview(r, "ok");
            fail("ReviewManagementException is expected.");
        } catch (ReviewManagementException e) {
            // Ok.
        }
    }

    /**
     * Test method <code>Review getReview(long id) </code>.
     */
    public void testGetReview_1() {
        try {
            manager.getReview(199);
            fail("ReviewManagementException is expected.");
        } catch (ReviewManagementException e) {
            // Ok.
        }
    }

    /**
     * Test method <code>Review getReview(long id) </code>.
     */
    public void testGetReview_2() throws Exception {
        // first create an review with id 1000 in the persistence.
        Review r = new Review();
        r.setId(1000);

        manager.createReview(r, "ok");

        try {
            manager.getReview(1000);
            fail("ReviewManagementException is expected.");
        } catch (ReviewManagementException e) {
            // Ok.
        }
    }

    /**
     * Test method <code>Review[] searchReviews(Filter filter, boolean complete) </code>.
     */
    public void testSearchReviews() {
        Filter filter = new GreaterThanFilter("Age", new Long(50));

        try {
            manager.searchReviews(filter, true);
            fail("ReviewManagementException is expected.");
        } catch (ReviewManagementException e) {
            // Ok.
        }
    }

    /**
     * Test method <code>void addReviewComment(long reviewId, Comment comment, String operator) </code>.
     *
     */
    public void testAddReviewComment_1() {
        try {
            manager.addReviewComment(100, new Comment(), "ok");
            fail("ReviewManagementException is expected.");
        } catch (ReviewManagementException e) {
            // Ok.
        }
    }

    /**
     * Test method <code>void addReviewComment(long reviewId, Comment comment, String operator) </code>.
     *
     * @throws Exception
     *             to junit.
     */
    public void testAddReviewComment_2() throws Exception {
        Review r = new Review();
        r.setId(1000);
        manager.createReview(r, "ok");

        try {
            manager.addReviewComment(1000, new Comment(), "ok");
            fail("ReviewManagementException is expected.");
        } catch (ReviewManagementException e) {
            // Ok.
        }
    }

    /**
     * Test method <code>void addItemComment(long itemId, Comment comment, String operator) </code>.
     */
    public void testAddItemComment_1() {
        try {
            manager.addItemComment(100, new Comment(), "ok");
            fail("ReviewManagementException is expected.");
        } catch (ReviewManagementException e) {
            // Ok.
        }
    }

    /**
     * Test method <code>void addItemComment(long itemId, Comment comment, String operator) </code>.
     */
    public void testAddItemComment_2() {
        try {
            manager.addItemComment(1000, new Comment(), "ok");
            fail("ReviewManagementException is expected.");
        } catch (ReviewManagementException e) {
            // Ok.
        }
    }

    /**
     * Test method <code>CommentType[] getAllCommentTypes() </code>.
     */
    public void testGetAllCommentTypes() {
        try {
            manager.getAllCommentTypes();
            fail("ReviewManagementException is expected.");
        } catch (ReviewManagementException e) {
            // Ok.
        }
    }
}