/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.review.application.impl.persistence;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import junit.framework.JUnit4TestAdapter;

import org.junit.Before;
import org.junit.Test;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.db.connectionfactory.DBConnectionException;
import com.topcoder.management.review.application.BaseUnitTests;
import com.topcoder.management.review.application.ReviewApplicationManagementConfigurationException;
import com.topcoder.management.review.application.impl.ReviewApplicationManagerImpl;

/**
 * <p>
 * Unit tests for {@link BaseDatabasePersistence} class.
 * </p>
 *
 * @author sparemax
 * @version 1.0
 */
public class BaseDatabasePersistenceUnitTests extends BaseUnitTests {
    /**
     * <p>
     * Represents the <code>BaseDatabasePersistence</code> instance used in tests.
     * </p>
     */
    private BaseDatabasePersistence instance;

    /**
     * <p>
     * Represents the configuration used in tests.
     * </p>
     */
    private ConfigurationObject configuration;

    /**
     * <p>
     * Adapter for earlier versions of JUnit.
     * </p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(BaseDatabasePersistenceUnitTests.class);
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

        instance = new MockBaseDatabasePersistence();

        configuration = getConfig(ReviewApplicationManagerImpl.DEFAULT_CONFIG_NAMESPACE).getChild("persistenceConfig");

        instance.configure(configuration);
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>BaseDatabasePersistence()</code>.<br>
     * Instance should be correctly created.
     * </p>
     */
    @Test
    public void testCtor() {
        instance = new MockBaseDatabasePersistence();

        assertNull("'dbConnectionFactory' should be correct.",
            getField(instance, "dbConnectionFactory"));
        assertNull("'connectionName' should be correct.",
            getField(instance, "connectionName"));
        assertNull("'log' should be correct.",
            getField(instance, "log"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>configure(ConfigurationObject configuration)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_configure_1() throws Exception {
        instance.configure(configuration);

        assertNotNull("'configure' should be correct.", getField(instance, "dbConnectionFactory"));
        assertNotNull("'configure' should be correct.", getField(instance, "connectionName"));
        assertNotNull("'configure' should be correct.", getField(instance, "log"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>configure(ConfigurationObject configuration)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_configure_2() throws Exception {
        configuration.removeProperty("connectionName");
        configuration.removeProperty("loggerName");
        instance.configure(configuration);

        assertNotNull("'configure' should be correct.", getField(instance, "dbConnectionFactory"));
        assertNull("'configure' should be correct.", getField(instance, "connectionName"));
        assertNull("'configure' should be correct.", getField(instance, "log"));
    }

    /**
     * <p>
     * Failure test for the method <code>configure(ConfigurationObject configuration)</code>
     * with configuration is null.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_configure_configurationNull() throws Exception {
        configuration = null;

        instance.configure(configuration);
    }

    /**
     * <p>
     * Failure test for the method <code>configure(ConfigurationObject configuration)</code> with 'loggerName' is not a
     * string.<br>
     * <code>ReviewApplicationManagementConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReviewApplicationManagementConfigurationException.class)
    public void test_configure_loggerNameNotString() throws Exception {
        configuration.setPropertyValue("loggerName", 1);

        instance.configure(configuration);
    }

    /**
     * <p>
     * Failure test for the method <code>configure(ConfigurationObject configuration)</code> with 'loggerName' is empty.
     * <br>
     * <code>ReviewApplicationManagementConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReviewApplicationManagementConfigurationException.class)
    public void test_configure_loggerNameEmpty() throws Exception {
        configuration.setPropertyValue("loggerName", EMPTY_STRING);

        instance.configure(configuration);
    }

    /**
     * <p>
     * Failure test for the method <code>configure(ConfigurationObject configuration)</code> with
     * 'dbConnectionFactoryConfig' is missing.<br>
     * <code>ReviewApplicationManagementConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReviewApplicationManagementConfigurationException.class)
    public void test_configure_dbConnectionFactoryConfigMissing() throws Exception {
        configuration.removeChild("dbConnectionFactoryConfig");

        instance.configure(configuration);
    }

    /**
     * <p>
     * Failure test for the method <code>configure(ConfigurationObject configuration)</code> with
     * 'dbConnectionFactoryConfig' is invalid.<br>
     * <code>ReviewApplicationManagementConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReviewApplicationManagementConfigurationException.class)
    public void test_configure_dbConnectionFactoryConfigInvalid1() throws Exception {
        configuration.getChild("dbConnectionFactoryConfig").removeChild(
            "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl");

        instance.configure(configuration);
    }

    /**
     * <p>
     * Failure test for the method <code>configure(ConfigurationObject configuration)</code> with
     * 'dbConnectionFactoryConfig' is invalid.<br>
     * <code>ReviewApplicationManagementConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReviewApplicationManagementConfigurationException.class)
    public void test_configure_dbConnectionFactoryConfigInvalid2() throws Exception {
        configuration.getChild("dbConnectionFactoryConfig")
            .getChild("com.topcoder.db.connectionfactory.DBConnectionFactoryImpl").getChild("connections")
            .setPropertyValue("default", "invalid");

        instance.configure(configuration);
    }

    /**
     * <p>
     * Failure test for the method <code>configure(ConfigurationObject configuration)</code> with 'connectionName' is
     * not a string.<br>
     * <code>ReviewApplicationManagementConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReviewApplicationManagementConfigurationException.class)
    public void test_configure_connectionNameNotString() throws Exception {
        configuration.setPropertyValue("connectionName", 1);

        instance.configure(configuration);
    }

    /**
     * <p>
     * Failure test for the method <code>configure(ConfigurationObject configuration)</code> with 'connectionName' is
     * empty.<br>
     * <code>ReviewApplicationManagementConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ReviewApplicationManagementConfigurationException.class)
    public void test_configure_connectionNameEmpty() throws Exception {
        configuration.setPropertyValue("connectionName", EMPTY_STRING);

        instance.configure(configuration);
    }

    /**
     * <p>
     * Accuracy test for the method <code>getLog()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     */
    @Test
    public void test_getLog() {
        assertNotNull("'getLog' should be correct.",
            instance.getLog());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getConnection()</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_getConnection_1() throws Exception {
        assertTrue("'getConnection' should be correct.", instance.getConnection().getAutoCommit());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getConnection()</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_getConnection_2() throws Exception {
        configuration.removeProperty("connectionName");
        instance.configure(configuration);

        assertTrue("'getConnection' should be correct.", instance.getConnection().getAutoCommit());
    }

    /**
     * <p>
     * Failure test for the method <code>getConnection()</code>
     * with the instance is not configured.<br>
     * <code>IllegalStateException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalStateException.class)
    public void test_getConnection_NotConfigured() throws Exception {
        instance = new MockBaseDatabasePersistence();

        instance.getConnection();
    }

    /**
     * <p>
     * Failure test for the method <code>getConnection()</code>
     * with an error has occurred.<br>
     * <code>DBConnectionException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = DBConnectionException.class)
    public void test_getConnection_Error() throws Exception {
        configuration.setPropertyValue("connectionName", "not_exist");
        instance.configure(configuration);

        instance.getConnection();
    }
}