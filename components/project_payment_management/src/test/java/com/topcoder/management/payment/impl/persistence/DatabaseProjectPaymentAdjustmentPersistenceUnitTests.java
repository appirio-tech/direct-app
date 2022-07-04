/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.payment.impl.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.math.BigDecimal;
import java.util.List;

import junit.framework.JUnit4TestAdapter;

import org.junit.Before;
import org.junit.Test;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.management.payment.BaseUnitTests;
import com.topcoder.management.payment.ProjectPaymentAdjustment;
import com.topcoder.management.payment.ProjectPaymentAdjustmentValidationException;
import com.topcoder.management.payment.ProjectPaymentManagementConfigurationException;
import com.topcoder.management.payment.impl.ProjectPaymentManagementPersistenceException;
import com.topcoder.management.payment.impl.ProjectPaymentManagerImpl;

/**
 * <p>
 * Unit tests for {@link DatabaseProjectPaymentAdjustmentPersistence} class.
 * </p>
 *
 * @author sparemax
 * @version 1.0
 */
public class DatabaseProjectPaymentAdjustmentPersistenceUnitTests extends BaseUnitTests {
    /**
     * <p>
     * Represents the <code>DatabaseProjectPaymentAdjustmentPersistence</code> instance used in tests.
     * </p>
     */
    private DatabaseProjectPaymentAdjustmentPersistence instance;

    /**
     * <p>
     * Represents the configuration used in tests.
     * </p>
     */
    private ConfigurationObject configuration;

    /**
     * <p>
     * Represents the project payment adjustment used in tests.
     * </p>
     */
    private ProjectPaymentAdjustment projectPaymentAdjustment;

    /**
     * <p>
     * Adapter for earlier versions of JUnit.
     * </p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(DatabaseProjectPaymentAdjustmentPersistenceUnitTests.class);
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

        instance = new DatabaseProjectPaymentAdjustmentPersistence();

        configuration = getConfig(ProjectPaymentManagerImpl.DEFAULT_CONFIG_NAMESPACE).getChild(
            "projectPaymentPersistenceConfig");

        instance.configure(configuration);

        projectPaymentAdjustment = new ProjectPaymentAdjustment();
        projectPaymentAdjustment.setProjectId(1004L);
        projectPaymentAdjustment.setResourceRoleId(3L);
        projectPaymentAdjustment.setFixedAmount(BigDecimal.valueOf(500));
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>DatabaseProjectPaymentAdjustmentPersistence()</code>.<br>
     * Instance should be correctly created.
     * </p>
     */
    @Test
    public void testCtor() {
        instance = new DatabaseProjectPaymentAdjustmentPersistence();

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
     * Accuracy test for the method <code>save(ProjectPaymentAdjustment projectPaymentAdjustment)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_save_1() throws Exception {
        instance.save(projectPaymentAdjustment);

        List<ProjectPaymentAdjustment> retrievedProjectPaymentAdjustments =
            instance.retrieveByProjectId(projectPaymentAdjustment.getProjectId());

        assertEquals("'save' should be correct.", 1, retrievedProjectPaymentAdjustments.size());
        ProjectPaymentAdjustment retrievedProjectPaymentAdjustment = retrievedProjectPaymentAdjustments.get(0);
        assertEquals("'save' should be correct.",
            projectPaymentAdjustment.getProjectId(), retrievedProjectPaymentAdjustment.getProjectId());
        assertEquals("'save' should be correct.",
            projectPaymentAdjustment.getResourceRoleId(), retrievedProjectPaymentAdjustment.getResourceRoleId());
        assertEquals("'save' should be correct.",
            projectPaymentAdjustment.getFixedAmount().doubleValue(),
            retrievedProjectPaymentAdjustment.getFixedAmount().doubleValue(), 0.001);
        assertNull("'save' should be correct.",
            retrievedProjectPaymentAdjustment.getMultiplier());
    }

    /**
     * <p>
     * Accuracy test for the method <code>save(ProjectPaymentAdjustment projectPaymentAdjustment)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_save_2() throws Exception {
        instance.save(projectPaymentAdjustment);

        // Update
        projectPaymentAdjustment.setFixedAmount(null);
        projectPaymentAdjustment.setMultiplier(0.01);
        instance.save(projectPaymentAdjustment);

        List<ProjectPaymentAdjustment> retrievedProjectPaymentAdjustments =
            instance.retrieveByProjectId(projectPaymentAdjustment.getProjectId());

        assertEquals("'save' should be correct.", 1, retrievedProjectPaymentAdjustments.size());
        ProjectPaymentAdjustment retrievedProjectPaymentAdjustment = retrievedProjectPaymentAdjustments.get(0);
        assertEquals("'save' should be correct.",
            projectPaymentAdjustment.getProjectId(), retrievedProjectPaymentAdjustment.getProjectId());
        assertEquals("'save' should be correct.",
            projectPaymentAdjustment.getResourceRoleId(), retrievedProjectPaymentAdjustment.getResourceRoleId());
        assertNull("'save' should be correct.",
            retrievedProjectPaymentAdjustment.getFixedAmount());
        assertEquals("'save' should be correct.",
            projectPaymentAdjustment.getMultiplier(), retrievedProjectPaymentAdjustment.getMultiplier());
    }

    /**
     * <p>
     * Accuracy test for the method <code>save(ProjectPaymentAdjustment projectPaymentAdjustment)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_save_3() throws Exception {
        instance.save(projectPaymentAdjustment);

        // Update
        projectPaymentAdjustment.setFixedAmount(null);
        projectPaymentAdjustment.setMultiplier(null);
        instance.save(projectPaymentAdjustment);

        List<ProjectPaymentAdjustment> retrievedProjectPaymentAdjustments =
            instance.retrieveByProjectId(projectPaymentAdjustment.getProjectId());

        assertEquals("'save' should be correct.", 1, retrievedProjectPaymentAdjustments.size());
        ProjectPaymentAdjustment retrievedProjectPaymentAdjustment = retrievedProjectPaymentAdjustments.get(0);
        assertEquals("'save' should be correct.",
            projectPaymentAdjustment.getProjectId(), retrievedProjectPaymentAdjustment.getProjectId());
        assertEquals("'save' should be correct.",
            projectPaymentAdjustment.getResourceRoleId(), retrievedProjectPaymentAdjustment.getResourceRoleId());
        assertNull("'save' should be correct.",
            retrievedProjectPaymentAdjustment.getFixedAmount());
        assertNull("'save' should be correct.",
            retrievedProjectPaymentAdjustment.getMultiplier());
    }

    /**
     * <p>
     * Failure test for the method <code>save(ProjectPaymentAdjustment projectPaymentAdjustment)</code>
     * with projectPaymentAdjustment is null.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_save_projectPaymentAdjustmentNull() throws Exception {
        projectPaymentAdjustment = null;

        instance.save(projectPaymentAdjustment);
    }

    /**
     * <p>
     * Failure test for the method <code>save(ProjectPaymentAdjustment projectPaymentAdjustment)</code>
     * with persistence was not configured properly.<br>
     * <code>IllegalStateException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalStateException.class)
    public void test_save_NotConfigured() throws Exception {
        instance = new DatabaseProjectPaymentAdjustmentPersistence();

        instance.save(projectPaymentAdjustment);
    }

    /**
     * <p>
     * Failure test for the method <code>save(ProjectPaymentAdjustment projectPaymentAdjustment)</code>
     * with projectId is null.<br>
     * <code>ProjectPaymentAdjustmentValidationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ProjectPaymentAdjustmentValidationException.class)
    public void test_save_projectIdNull() throws Exception {
        projectPaymentAdjustment.setProjectId(null);

        instance.save(projectPaymentAdjustment);
    }

    /**
     * <p>
     * Failure test for the method <code>save(ProjectPaymentAdjustment projectPaymentAdjustment)</code>
     * with resourceRoleId is null.<br>
     * <code>ProjectPaymentAdjustmentValidationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ProjectPaymentAdjustmentValidationException.class)
    public void test_save_resourceRoleIdNull() throws Exception {
        projectPaymentAdjustment.setResourceRoleId(null);

        instance.save(projectPaymentAdjustment);
    }

    /**
     * <p>
     * Failure test for the method <code>save(ProjectPaymentAdjustment projectPaymentAdjustment)</code>
     * with fixedAmount is negative.<br>
     * <code>ProjectPaymentAdjustmentValidationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ProjectPaymentAdjustmentValidationException.class)
    public void test_save_fixedAmountNegative() throws Exception {
        projectPaymentAdjustment.setFixedAmount(BigDecimal.valueOf(-1));

        instance.save(projectPaymentAdjustment);
    }

    /**
     * <p>
     * Failure test for the method <code>save(ProjectPaymentAdjustment projectPaymentAdjustment)</code>
     * with multiplier is negative.<br>
     * <code>ProjectPaymentAdjustmentValidationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ProjectPaymentAdjustmentValidationException.class)
    public void test_save_multiplierNegative() throws Exception {
        projectPaymentAdjustment.setMultiplier(-0.001);

        instance.save(projectPaymentAdjustment);
    }

    /**
     * <p>
     * Failure test for the method <code>save(ProjectPaymentAdjustment projectPaymentAdjustment)</code>
     * with both fixedAmount and multiplier are not null.<br>
     * <code>ProjectPaymentAdjustmentValidationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ProjectPaymentAdjustmentValidationException.class)
    public void test_save_fixedAmountAndMultiplierNotNull() throws Exception {
        projectPaymentAdjustment.setMultiplier(1D);

        instance.save(projectPaymentAdjustment);
    }

    /**
     * <p>
     * Failure test for the method <code>save(ProjectPaymentAdjustment projectPaymentAdjustment)</code>
     * with an error has occurred.<br>
     * <code>ProjectPaymentManagementPersistenceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ProjectPaymentManagementPersistenceException.class)
    public void test_save_Error() throws Exception {
        configuration.setPropertyValue("connectionName", "not_exist");
        instance.configure(configuration);

        instance.save(projectPaymentAdjustment);
    }

    /**
     * <p>
     * Accuracy test for the method <code>retrieveByProjectId(long projectId)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_retrieveByProjectId_1() throws Exception {
        instance.save(projectPaymentAdjustment);

        List<ProjectPaymentAdjustment> res = instance.retrieveByProjectId(Long.MAX_VALUE);

        assertEquals("'retrieveByProjectId' should be correct.", 0, res.size());
    }

    /**
     * <p>
     * Accuracy test for the method <code>retrieveByProjectId(long projectId)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_retrieveByProjectId_2() throws Exception {
        instance.save(projectPaymentAdjustment);

        List<ProjectPaymentAdjustment> res =
            instance.retrieveByProjectId(projectPaymentAdjustment.getProjectId());

        assertEquals("'retrieveByProjectId' should be correct.", 1, res.size());
        ProjectPaymentAdjustment retrievedProjectPaymentAdjustment = res.get(0);
        assertEquals("'retrieveByProjectId' should be correct.",
            projectPaymentAdjustment.getProjectId(), retrievedProjectPaymentAdjustment.getProjectId());
        assertEquals("'retrieveByProjectId' should be correct.",
            projectPaymentAdjustment.getResourceRoleId(), retrievedProjectPaymentAdjustment.getResourceRoleId());
        assertEquals("'retrieveByProjectId' should be correct.",
            projectPaymentAdjustment.getFixedAmount().doubleValue(),
            retrievedProjectPaymentAdjustment.getFixedAmount().doubleValue(), 0.001);
        assertNull("'retrieveByProjectId' should be correct.",
            retrievedProjectPaymentAdjustment.getMultiplier());
    }

    /**
     * <p>
     * Accuracy test for the method <code>retrieveByProjectId(long projectId)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_retrieveByProjectId_3() throws Exception {
        instance.save(projectPaymentAdjustment);

        projectPaymentAdjustment = new ProjectPaymentAdjustment();
        projectPaymentAdjustment.setProjectId(1004L);
        projectPaymentAdjustment.setResourceRoleId(4L);
        projectPaymentAdjustment.setFixedAmount(BigDecimal.valueOf(500));
        instance.save(projectPaymentAdjustment);

        List<ProjectPaymentAdjustment> res =
            instance.retrieveByProjectId(projectPaymentAdjustment.getProjectId());

        assertEquals("'retrieveByProjectId' should be correct.", 2, res.size());
    }

    /**
     * <p>
     * Failure test for the method <code>retrieveByProjectId(long projectId)</code>
     * with persistence was not configured properly.<br>
     * <code>IllegalStateException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalStateException.class)
    public void test_retrieveByProjectId_NotConfigured() throws Exception {
        instance.save(projectPaymentAdjustment);

        instance = new DatabaseProjectPaymentAdjustmentPersistence();

        instance.retrieveByProjectId(projectPaymentAdjustment.getProjectId());
    }

    /**
     * <p>
     * Failure test for the method <code>retrieveByProjectId(long projectId)</code>
     * with an error has occurred.<br>
     * <code>ProjectPaymentManagementPersistenceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ProjectPaymentManagementPersistenceException.class)
    public void test_retrieveByProjectId_Error() throws Exception {
        instance.save(projectPaymentAdjustment);

        configuration.setPropertyValue("connectionName", "not_exist");
        instance.configure(configuration);

        instance.retrieveByProjectId(projectPaymentAdjustment.getProjectId());
    }
}