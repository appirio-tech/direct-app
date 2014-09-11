/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.review.application;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import junit.framework.JUnit4TestAdapter;

import org.junit.Before;
import org.junit.Test;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.management.review.application.impl.ReviewApplicationManagerImpl;
import com.topcoder.management.review.application.impl.ReviewApplicationPersistence;
import com.topcoder.search.builder.SearchBundleManager;
import com.topcoder.util.log.Log;

/**
 * <p>
 * Unit tests for {@link Helper} class.
 * </p>
 *
 * @author sparemax
 * @version 1.0
 */
public class HelperUnitTests extends BaseUnitTests {
    /**
     * <p>
     * Represents the configuration object used in tests.
     * </p>
     */
    private ConfigurationObject config;

    /**
     * <p>
     * Represents the configuration object used in tests.
     * </p>
     */
    private Log log;

    /**
     * <p>
     * Adapter for earlier versions of JUnit.
     * </p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(HelperUnitTests.class);
    }

    /**
     * <p>
     * Sets up the unit tests.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();

        config = BaseUnitTests.getConfig(ReviewApplicationManagerImpl.DEFAULT_CONFIG_NAMESPACE);

        log = Helper.getLog(config);
    }

    /**
     * <p>
     * Tests accuracy of <code>executeUpdate(Log log, String signature, Class&lt;T&gt; eClass,
     * Connection connection, boolean returnsId, String sql, Object... params)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_executeUpdate_1() throws Exception {
        clearDB();

        Connection connection = getConnection();
        long id = Helper.executeUpdate(log, "signature", ReviewAuctionManagerException.class, connection, true,
            "INSERT INTO review_auction_category_lu(name) VALUES(?)", new Object[] {"Contest Review"});

        assertTrue("'executeUpdate' should be correct.", id > 0);
    }

    /**
     * <p>
     * Tests accuracy of <code>executeUpdate(Log log, String signature, Class&lt;T&gt; eClass,
     * Connection connection, boolean returnsId, String sql, Object... params)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_executeUpdate_2() throws Exception {
        Connection connection = getConnection();
        long id = Helper.executeUpdate(log, "signature", ReviewAuctionManagerException.class, connection, false,
            "UPDATE review_auction_category_lu SET name=? WHERE review_auction_category_id = 1",
            new Object[] {"New Review"});

        assertEquals("'executeUpdate' should be correct.", 0, id);
    }

    /**
     * <p>
     * Tests accuracy of <code>closeStatement(Log log, String signature, Statement statement)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_closeStatement() throws Exception {
        clearDB();

        PreparedStatement preparedStatement = getConnection().prepareStatement("select * from review_application");
        Helper.closeStatement(log, "signature", preparedStatement);

        try {
            preparedStatement.executeQuery();

            fail("SQLException is expected.");
        } catch (SQLException e) {
            // Good
        }
    }

    /**
     * <p>
     * Tests accuracy of <code>closeConnection(Log log, String signature, Connection connection)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_closeConnection() throws Exception {
        clearDB();

        Connection connection = getConnection();
        Helper.closeConnection(log, "signature", connection);

        assertTrue("'closeConnection' should be correct.", connection.isClosed());
    }

    /**
     * <p>
     * Tests accuracy of <code>createPersistence(Class&lt;?&gt; targetType, ConfigurationObject config)</code>
     * method.<br>
     * Result should be correct.
     * </p>
     */
    @Test
    public void test_createPersistence() {
        assertNotNull("'createPersistence' should be correct.",
            Helper.createPersistence(ReviewApplicationPersistence.class, config));
    }

    /**
     * <p>
     * Tests accuracy of <code>getLog(ConfigurationObject config)</code> method.<br>
     * Result should be correct.
     * </p>
     */
    @Test
    public void test_getLog() {
        assertNotNull("'getLog' should be correct.", Helper.getLog(config));
    }

    /**
     * <p>
     * Tests accuracy of <code>getConfiguration(String filePath, String namespace)</code> method.<br>
     * Result should be correct.
     * </p>
     */
    @Test
    public void test_getConfiguration() {
        assertNotNull("'getConfiguration' should be correct.",
            Helper.getConfiguration(ReviewApplicationManagerImpl.DEFAULT_CONFIG_FILENAME,
                ReviewApplicationManagerImpl.DEFAULT_CONFIG_NAMESPACE));
    }

    /**
     * <p>
     * Tests failure of <code>getConfiguration(String filePath, String namespace)</code> method with filePath is
     * null.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_getConfiguration_filePathNull() {
        Helper.getConfiguration(null, ReviewApplicationManagerImpl.DEFAULT_CONFIG_NAMESPACE);
    }

    /**
     * <p>
     * Tests failure of <code>getConfiguration(String filePath, String namespace)</code> method with filePath is
     * empty.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_getConfiguration_filePathEmpty() {
        Helper.getConfiguration(BaseUnitTests.EMPTY_STRING, ReviewApplicationManagerImpl.DEFAULT_CONFIG_NAMESPACE);
    }

    /**
     * <p>
     * Tests failure of <code>getConfiguration(String filePath, String namespace)</code> method with namespace is
     * null.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_getConfiguration_namespaceNull() {
        Helper.getConfiguration(ReviewApplicationManagerImpl.DEFAULT_CONFIG_FILENAME, null);
    }

    /**
     * <p>
     * Tests failure of <code>getConfiguration(String filePath, String namespace)</code> method with namespace is
     * empty.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_getConfiguration_namespaceEmpty() {
        Helper.getConfiguration(ReviewApplicationManagerImpl.DEFAULT_CONFIG_FILENAME, BaseUnitTests.EMPTY_STRING);
    }

    /**
     * <p>
     * Tests accuracy of <code>getSearchBundleManager(ConfigurationObject config)</code> method.<br>
     * Result should be correct.
     * </p>
     */
    @Test
    public void test_getSearchBundleManager() {
        assertNotNull("'getSearchBundleManager' should be correct.", Helper.getSearchBundleManager(config));
    }

    /**
     * <p>
     * Tests accuracy of <code>getSearchBundle(SearchBundleManager searchBundleManager, ConfigurationObject config,
     * String key)</code> method.<br>
     * Result should be correct.
     * </p>
     */
    @Test
    public void test_getSearchBundle() {
        SearchBundleManager searchBundleManager = Helper.getSearchBundleManager(config);

        assertNotNull("'getSearchBundle' should be correct.",
            Helper.getSearchBundle(searchBundleManager, config, "reviewApplicationSearchBundleName"));
    }

    /**
     * <p>
     * Tests accuracy of <code>getChildConfig(ConfigurationObject config, String key)</code> method.<br>
     * Result should be correct.
     * </p>
     */
    @Test
    public void test_getChildConfig() {
        assertNotNull("'getChildConfig' should be correct.", Helper.getChildConfig(config, "objectFactoryConfig"));
    }

    /**
     * <p>
     * Tests accuracy of <code>getProperty(ConfigurationObject config, String key,
     * boolean required)</code> method.<br>
     * Result should be correct.
     * </p>
     */
    @Test
    public void test_getProperty() {
        assertEquals("'getProperty' should be correct.", "myLogger", Helper.getProperty(config, "loggerName", false));
    }

    /**
     * <p>
     * Tests accuracy of <code>concat(Object... values)</code> method.<br>
     * Result should be correct.
     * </p>
     */
    @Test
    public void test_concat() {
        String res = Helper.concat("abc", 1, null);

        assertEquals("'concat' should be correct.", "abc1null", res);
    }
}
