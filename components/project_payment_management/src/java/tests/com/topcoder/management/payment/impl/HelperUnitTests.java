/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.payment.impl;

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
import com.topcoder.management.payment.BaseUnitTests;
import com.topcoder.management.payment.ProjectPaymentType;
import com.topcoder.management.payment.impl.persistence.DatabaseProjectPaymentPersistence;
import com.topcoder.management.payment.impl.persistence.MockBaseProjectPaymentManagementPersistence;
import com.topcoder.util.log.Log;

/**
 * <p>
 * Unit tests for {@link Helper} class.
 * </p>
 *
 * @author sparemax
 * @version 1.0.1
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

        config = BaseUnitTests.getConfig(ProjectPaymentManagerImpl.DEFAULT_CONFIG_NAMESPACE);

        log = Helper.getLog(config);
    }

    /**
     * <p>
     * Tests accuracy of <code>createPersistence(Class&lt;?&gt; targetType, ConfigurationObject config,
     * String persistenceKey, String configKey)</code>
     * method.<br>
     * Result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void test_createPersistence() throws Exception {
        assertNotNull("'createPersistence' should be correct.", Helper.createPersistence(
            DatabaseProjectPaymentPersistence.class, config, "projectPaymentPersistenceKey",
            "projectPaymentPersistenceConfig"));
    }

    /**
     * <p>
     * Tests accuracy of <code>getConfiguration(String filePath, String namespace)</code> method.<br>
     * Result should be correct.
     * </p>
     */
    @Test
    public void test_getConfiguration() {
        assertNotNull("'getConfiguration' should be correct.", Helper.getConfiguration(
            ProjectPaymentAdjustmentManagerImpl.DEFAULT_CONFIG_FILENAME,
            ProjectPaymentAdjustmentManagerImpl.DEFAULT_CONFIG_NAMESPACE));
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
        Helper.getConfiguration(null, ProjectPaymentAdjustmentManagerImpl.DEFAULT_CONFIG_NAMESPACE);
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
        Helper.getConfiguration(BaseUnitTests.EMPTY_STRING,
            ProjectPaymentAdjustmentManagerImpl.DEFAULT_CONFIG_NAMESPACE);
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
        Helper.getConfiguration(ProjectPaymentAdjustmentManagerImpl.DEFAULT_CONFIG_FILENAME, null);
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
        Helper.getConfiguration(ProjectPaymentAdjustmentManagerImpl.DEFAULT_CONFIG_FILENAME,
            BaseUnitTests.EMPTY_STRING);
    }

    /**
     * <p>
     * Tests accuracy of <code>executeUpdate(Log log, String signature, Connection connection,
     * boolean returnsId, String sql, Object... params)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_executeUpdate_1() throws Exception {
        executeUpdate("DELETE FROM project_payment");

        Connection connection = getConnection();
        long id = Helper.executeUpdate(log, "signature", connection, true,
            "INSERT INTO project_payment (project_payment_id, project_payment_type_id,"
                + " resource_id, submission_id, amount, pacts_payment_id,"
                + " create_user, create_date, modify_user, modify_date)"
                + " VALUES(1, 1, 1001, 1011111, 500.0, 4, 'testOperator', CURRENT, 'testOperator', CURRENT)");

        assertTrue("'executeUpdate' should be correct.", id > 0);
    }

    /**
     * <p>
     * Tests accuracy of <code>executeUpdate(Log log, String signature, Connection connection,
     * boolean returnsId, String sql, Object... params)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_executeUpdate_2() throws Exception {
        executeUpdate("DELETE FROM project_payment_adjustment");

        Connection connection = getConnection();
        long count = Helper.executeUpdate(log, "signature", connection, false,
            "INSERT INTO project_payment_adjustment (project_id, resource_role_id, fixed_amount)"
                + " VALUES(?, ?, ?)", 1001, 4, 50.0);

        assertEquals("'executeUpdate' should be correct.", 1, count);
    }

    /**
     * <p>
     * Tests accuracy of <code>getConnection(Log log, String signature, DBConnectionFactory dbConnectionFactory,
     * String connectionName, boolean autoCommit)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_getConnection() throws Exception {
        MockBaseProjectPaymentManagementPersistence persistence = new MockBaseProjectPaymentManagementPersistence();
        persistence.configure(config.getChild("projectPaymentPersistenceConfig"));

        assertNotNull("'getConnection' should be correct.", Helper.getConnection(log, "signature", persistence
            .getDbConnectionFactory(), persistence.getConnectionName(), false));
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

        PreparedStatement preparedStatement = getConnection().prepareStatement(
            "select * from project_payment_adjustment");
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
     * Tests accuracy of <code>toString(Object obj)</code> method.<br>
     * Result should be correct.
     * </p>
     */
    @Test
    public void test_toString_1() {
        assertEquals("'toString' should be correct.", "null", Helper.toString(null));
    }

    /**
     * <p>
     * Tests accuracy of <code>toString(Object obj)</code> method.<br>
     * Result should be correct.
     * </p>
     */
    @Test
    public void test_toString_2() {
        assertEquals("'toString' should be correct.", "1", Helper.toString(1));
    }

    /**
     * <p>
     * Tests accuracy of <code>toString(Object obj)</code> method.<br>
     * Result should be correct.
     * </p>
     */
    @Test
    public void test_toString_3() {
        assertNotNull("'toString' should be correct.", Helper.toString(new ProjectPaymentType()));
    }
}
