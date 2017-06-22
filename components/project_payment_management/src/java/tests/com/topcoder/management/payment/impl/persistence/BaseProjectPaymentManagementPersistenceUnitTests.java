/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.payment.impl.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import junit.framework.JUnit4TestAdapter;

import org.junit.Before;
import org.junit.Test;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.management.payment.BaseUnitTests;
import com.topcoder.management.payment.ProjectPaymentManagementConfigurationException;
import com.topcoder.management.payment.impl.ProjectPaymentManagerImpl;

/**
 * <p>
 * Unit tests for {@link BaseProjectPaymentManagementPersistence} class.
 * </p>
 *
 * @author sparemax
 * @version 1.0
 */
public class BaseProjectPaymentManagementPersistenceUnitTests extends BaseUnitTests {
    /**
     * <p>
     * Represents the <code>BaseProjectPaymentManagementPersistence</code> instance used in tests.
     * </p>
     */
    private BaseProjectPaymentManagementPersistence instance;

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
        return new JUnit4TestAdapter(BaseProjectPaymentManagementPersistenceUnitTests.class);
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

        instance = new MockBaseProjectPaymentManagementPersistence();

        configuration = getConfig(ProjectPaymentManagerImpl.DEFAULT_CONFIG_NAMESPACE).getChild(
            "projectPaymentPersistenceConfig");

        instance.configure(configuration);
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>BaseProjectPaymentManagementPersistence()</code>.<br>
     * Instance should be correctly created.
     * </p>
     */
    @Test
    public void testCtor() {
        instance = new MockBaseProjectPaymentManagementPersistence();

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
     * <code>ProjectPaymentManagementConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ProjectPaymentManagementConfigurationException.class)
    public void test_configure_loggerNameNotString() throws Exception {
        configuration.setPropertyValue("loggerName", 1);

        instance.configure(configuration);
    }

    /**
     * <p>
     * Failure test for the method <code>configure(ConfigurationObject configuration)</code> with 'loggerName' is empty.
     * <br>
     * <code>ProjectPaymentManagementConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ProjectPaymentManagementConfigurationException.class)
    public void test_configure_loggerNameEmpty() throws Exception {
        configuration.setPropertyValue("loggerName", EMPTY_STRING);

        instance.configure(configuration);
    }

    /**
     * <p>
     * Failure test for the method <code>configure(ConfigurationObject configuration)</code> with
     * 'dbConnectionFactoryConfig' is missing.<br>
     * <code>ProjectPaymentManagementConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ProjectPaymentManagementConfigurationException.class)
    public void test_configure_dbConnectionFactoryConfigMissing() throws Exception {
        configuration.removeChild("dbConnectionFactoryConfig");

        instance.configure(configuration);
    }

    /**
     * <p>
     * Failure test for the method <code>configure(ConfigurationObject configuration)</code> with
     * 'dbConnectionFactoryConfig' is invalid.<br>
     * <code>ProjectPaymentManagementConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ProjectPaymentManagementConfigurationException.class)
    public void test_configure_dbConnectionFactoryConfigInvalid1() throws Exception {
        configuration.getChild("dbConnectionFactoryConfig").removeChild(
            "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl");

        instance.configure(configuration);
    }

    /**
     * <p>
     * Failure test for the method <code>configure(ConfigurationObject configuration)</code> with
     * 'dbConnectionFactoryConfig' is invalid.<br>
     * <code>ProjectPaymentManagementConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ProjectPaymentManagementConfigurationException.class)
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
     * <code>ProjectPaymentManagementConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ProjectPaymentManagementConfigurationException.class)
    public void test_configure_connectionNameNotString() throws Exception {
        configuration.setPropertyValue("connectionName", 1);

        instance.configure(configuration);
    }

    /**
     * <p>
     * Failure test for the method <code>configure(ConfigurationObject configuration)</code> with 'connectionName' is
     * empty.<br>
     * <code>ProjectPaymentManagementConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ProjectPaymentManagementConfigurationException.class)
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
     * Accuracy test for the method <code>getDbConnectionFactory()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     * @throws Exception to JUnit
     */
    @Test
    public void test_getDbConnectionFactory() throws Exception {
        assertNotNull("'getDbConnectionFactory' should be correct.",
            instance.getDbConnectionFactory());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getConnectionName()</code>.<br>
     * The value should be properly retrieved.
     * </p>
     * @throws Exception to JUnit
     */
    @Test
    public void test_getConnectionName() throws Exception {
        assertEquals("'getConnectionName' should be correct.",
            configuration.getPropertyValue("connectionName"), instance.getConnectionName());
    }
}