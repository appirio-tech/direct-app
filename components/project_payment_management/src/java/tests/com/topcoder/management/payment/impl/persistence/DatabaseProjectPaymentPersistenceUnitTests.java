/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.payment.impl.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Date;

import junit.framework.JUnit4TestAdapter;

import org.junit.Before;
import org.junit.Test;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.management.payment.BaseUnitTests;
import com.topcoder.management.payment.ProjectPayment;
import com.topcoder.management.payment.ProjectPaymentManagementConfigurationException;
import com.topcoder.management.payment.ProjectPaymentManagementDataIntegrityException;
import com.topcoder.management.payment.ProjectPaymentType;
import com.topcoder.management.payment.ProjectPaymentValidationException;
import com.topcoder.management.payment.impl.ProjectPaymentManagementPersistenceException;
import com.topcoder.management.payment.impl.ProjectPaymentManagerImpl;

/**
 * <p>
 * Unit tests for {@link DatabaseProjectPaymentPersistence} class.
 * </p>
 *
 * @author sparemax
 * @version 1.0.1
 */
public class DatabaseProjectPaymentPersistenceUnitTests extends BaseUnitTests {
    /**
     * <p>
     * Represents the <code>DatabaseProjectPaymentPersistence</code> instance used in tests.
     * </p>
     */
    private DatabaseProjectPaymentPersistence instance;

    /**
     * <p>
     * Represents the configuration used in tests.
     * </p>
     */
    private ConfigurationObject configuration;

    /**
     * <p>
     * Represents the project payment used in tests.
     * </p>
     */
    private ProjectPayment projectPayment;

    /**
     * <p>
     * Adapter for earlier versions of JUnit.
     * </p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(DatabaseProjectPaymentPersistenceUnitTests.class);
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

        instance = new DatabaseProjectPaymentPersistence();

        configuration = getConfig(ProjectPaymentManagerImpl.DEFAULT_CONFIG_NAMESPACE).getChild(
            "projectPaymentPersistenceConfig");

        instance.configure(configuration);

        projectPayment = new ProjectPayment();
        ProjectPaymentType projectPaymentType = new ProjectPaymentType();
        projectPaymentType.setProjectPaymentTypeId(1L);
        projectPayment.setProjectPaymentType(projectPaymentType);
        projectPayment.setResourceId(1001L);
        projectPayment.setSubmissionId(1011111L);
        projectPayment.setAmount(BigDecimal.valueOf(650L));
        projectPayment.setPactsPaymentId(4L);
        projectPayment.setCreateDate(new Date());
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>DatabaseProjectPaymentPersistence()</code>.<br>
     * Instance should be correctly created.
     * </p>
     */
    @Test
    public void testCtor() {
        instance = new DatabaseProjectPaymentPersistence();

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
     * Accuracy test for the method <code>create(ProjectPayment projectPayment)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_create_1() throws Exception {
        ProjectPayment res = instance.create(projectPayment, "testOperator");

        assertSame("'create' should be correct.", projectPayment, res);
        assertTrue("'create' should be correct.", res.getProjectPaymentId() > 0);

        ProjectPayment retrievedProjectPayment = instance.retrieve(res.getProjectPaymentId());

        assertEquals("'create' should be correct.",
            projectPayment.getProjectPaymentType().getProjectPaymentTypeId(),
            retrievedProjectPayment.getProjectPaymentType().getProjectPaymentTypeId());
        assertEquals("'create' should be correct.",
            projectPayment.getResourceId(), retrievedProjectPayment.getResourceId());
        assertEquals("'create' should be correct.",
            projectPayment.getSubmissionId(), retrievedProjectPayment.getSubmissionId());
        assertEquals("'create' should be correct.",
            projectPayment.getAmount().doubleValue(), retrievedProjectPayment.getAmount().doubleValue(), 0.001);
        assertEquals("'create' should be correct.",
            projectPayment.getPactsPaymentId(), retrievedProjectPayment.getPactsPaymentId());
        assertNotNull("'create' should be correct.",
            retrievedProjectPayment.getCreateDate());
    }

    /**
     * <p>
     * Accuracy test for the method <code>create(ProjectPayment projectPayment)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_create_2() throws Exception {
        projectPayment.setSubmissionId(null);
        projectPayment.setPactsPaymentId(null);
        ProjectPayment res = instance.create(projectPayment, "testOperator");

        assertSame("'create' should be correct.", projectPayment, res);
        assertTrue("'create' should be correct.", res.getProjectPaymentId() > 0);

        ProjectPayment retrievedProjectPayment = instance.retrieve(res.getProjectPaymentId());

        assertEquals("'create' should be correct.",
            projectPayment.getProjectPaymentType().getProjectPaymentTypeId(),
            retrievedProjectPayment.getProjectPaymentType().getProjectPaymentTypeId());
        assertEquals("'create' should be correct.",
            projectPayment.getResourceId(), retrievedProjectPayment.getResourceId());
        assertNull("'create' should be correct.",
            retrievedProjectPayment.getSubmissionId());
        assertEquals("'create' should be correct.",
            projectPayment.getAmount().doubleValue(), retrievedProjectPayment.getAmount().doubleValue(), 0.001);
        assertNull("'create' should be correct.",
            retrievedProjectPayment.getPactsPaymentId());
        assertNotNull("'create' should be correct.",
            retrievedProjectPayment.getCreateDate());
    }

    /**
     * <p>
     * Failure test for the method <code>create(ProjectPayment projectPayment)</code> with projectPayment is null.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_create_projectPaymentNull() throws Exception {
        projectPayment = null;

        instance.create(projectPayment, "testOperator");
    }

    /**
     * <p>
     * Failure test for the method <code>create(ProjectPayment projectPayment)</code> with persistence was not
     * configured properly.<br>
     * <code>IllegalStateException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalStateException.class)
    public void test_create_NotConfigured() throws Exception {
        instance = new DatabaseProjectPaymentPersistence();

        instance.create(projectPayment, "testOperator");
    }

    /**
     * <p>
     * Failure test for the method <code>create(ProjectPayment projectPayment)</code>
     * with projectPaymentId is not null.<br>
     * <code>ProjectPaymentValidationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ProjectPaymentValidationException.class)
    public void test_create_projectPaymentIdNotNull() throws Exception {
        projectPayment.setProjectPaymentId(1L);

        instance.create(projectPayment, "testOperator");
    }

    /**
     * <p>
     * Failure test for the method <code>create(ProjectPayment projectPayment)</code>
     * with projectPaymentType is null.<br>
     * <code>ProjectPaymentValidationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ProjectPaymentValidationException.class)
    public void test_create_projectPaymentTypeNull() throws Exception {
        projectPayment.setProjectPaymentType(null);

        instance.create(projectPayment, "testOperator");
    }

    /**
     * <p>
     * Failure test for the method <code>create(ProjectPayment projectPayment)</code>
     * with projectPaymentTypeId is null.<br>
     * <code>ProjectPaymentValidationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ProjectPaymentValidationException.class)
    public void test_create_projectPaymentTypeIdNull() throws Exception {
        projectPayment.getProjectPaymentType().setProjectPaymentTypeId(null);

        instance.create(projectPayment, "testOperator");
    }

    /**
     * <p>
     * Failure test for the method <code>create(ProjectPayment projectPayment)</code>
     * with resourceId is null.<br>
     * <code>ProjectPaymentValidationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ProjectPaymentValidationException.class)
    public void test_create_resourceIdNull() throws Exception {
        projectPayment.setResourceId(null);

        instance.create(projectPayment, "testOperator");
    }

    /**
     * <p>
     * Failure test for the method <code>create(ProjectPayment projectPayment)</code>
     * with amount is null.<br>
     * <code>ProjectPaymentValidationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ProjectPaymentValidationException.class)
    public void test_create_amountNull() throws Exception {
        projectPayment.setAmount(null);

        instance.create(projectPayment, "testOperator");
    }

    /**
     * <p>
     * Failure test for the method <code>create(ProjectPayment projectPayment)</code> with amount is negative.<br>
     * <code>ProjectPaymentValidationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ProjectPaymentValidationException.class)
    public void test_create_amountNegative() throws Exception {
        projectPayment.setAmount(BigDecimal.valueOf(-0.01));

        instance.create(projectPayment, "testOperator");
    }

    /**
     * <p>
     * Failure test for the method <code>create(ProjectPayment projectPayment)</code>
     * with projectPaymentTypeId is invalid.<br>
     * <code>ProjectPaymentManagementDataIntegrityException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ProjectPaymentManagementDataIntegrityException.class)
    public void test_create_projectPaymentTypeIdInvalid() throws Exception {
        projectPayment.getProjectPaymentType().setProjectPaymentTypeId(Long.MAX_VALUE);

        instance.create(projectPayment, "testOperator");
    }

    /**
     * <p>
     * Failure test for the method <code>create(ProjectPayment projectPayment)</code>
     * with resourceId is invalid.<br>
     * <code>ProjectPaymentManagementDataIntegrityException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ProjectPaymentManagementDataIntegrityException.class)
    public void test_create_resourceIdInvalid() throws Exception {
        projectPayment.setResourceId(Long.MAX_VALUE);

        instance.create(projectPayment, "testOperator");
    }

    /**
     * <p>
     * Failure test for the method <code>create(ProjectPayment projectPayment)</code>
     * with submissionId is invalid.<br>
     * <code>ProjectPaymentManagementDataIntegrityException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ProjectPaymentManagementDataIntegrityException.class)
    public void test_create_submissionIdInvalid() throws Exception {
        projectPayment.setSubmissionId(Long.MAX_VALUE);

        instance.create(projectPayment, "testOperator");
    }

    /**
     * <p>
     * Failure test for the method <code>create(ProjectPayment projectPayment)</code>
     * with an error has occurred.<br>
     * <code>ProjectPaymentManagementPersistenceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ProjectPaymentManagementPersistenceException.class)
    public void test_create_Error() throws Exception {
        configuration.setPropertyValue("connectionName", "not_exist");
        instance.configure(configuration);

        instance.create(projectPayment, "testOperator");
    }

    /**
     * <p>
     * Accuracy test for the method <code>update(ProjectPayment projectPayment)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_update() throws Exception {
        instance.create(projectPayment, "testOperator");

        projectPayment.setSubmissionId(null);
        projectPayment.setPactsPaymentId(null);
        projectPayment.setAmount(BigDecimal.valueOf(999));
        instance.update(projectPayment, "testOperator");

        ProjectPayment retrievedProjectPayment = instance.retrieve(projectPayment.getProjectPaymentId());

        assertEquals("'update' should be correct.",
            projectPayment.getProjectPaymentType().getProjectPaymentTypeId(),
            retrievedProjectPayment.getProjectPaymentType().getProjectPaymentTypeId());
        assertEquals("'update' should be correct.",
            projectPayment.getResourceId(), retrievedProjectPayment.getResourceId());
        assertNull("'update' should be correct.",
            retrievedProjectPayment.getSubmissionId());
        assertEquals("'update' should be correct.",
            projectPayment.getAmount().doubleValue(), retrievedProjectPayment.getAmount().doubleValue(), 0.001);
        assertNull("'update' should be correct.",
            retrievedProjectPayment.getPactsPaymentId());
        assertNotNull("'update' should be correct.",
            retrievedProjectPayment.getCreateDate());
    }

    /**
     * <p>
     * Failure test for the method <code>update(ProjectPayment projectPayment)</code> with projectPayment is null.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_update_projectPaymentNull() throws Exception {
        projectPayment = null;

        instance.update(projectPayment, "testOperator");
    }

    /**
     * <p>
     * Failure test for the method <code>update(ProjectPayment projectPayment)</code> with persistence was not
     * configured properly.<br>
     * <code>IllegalStateException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalStateException.class)
    public void test_update_NotConfigured() throws Exception {
        instance.create(projectPayment, "testOperator");

        instance = new DatabaseProjectPaymentPersistence();

        instance.update(projectPayment, "testOperator");
    }

    /**
     * <p>
     * Failure test for the method <code>update(ProjectPayment projectPayment)</code>
     * with projectPaymentId is null.<br>
     * <code>ProjectPaymentValidationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ProjectPaymentValidationException.class)
    public void test_update_projectPaymentIdNull() throws Exception {
        projectPayment.setProjectPaymentId(null);

        instance.update(projectPayment, "testOperator");
    }

    /**
     * <p>
     * Failure test for the method <code>update(ProjectPayment projectPayment)</code>
     * with projectPaymentId is negative.<br>
     * <code>ProjectPaymentValidationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ProjectPaymentValidationException.class)
    public void test_update_projectPaymentIdNegative() throws Exception {
        projectPayment.setProjectPaymentId(-1L);

        instance.update(projectPayment, "testOperator");
    }

    /**
     * <p>
     * Failure test for the method <code>update(ProjectPayment projectPayment)</code>
     * with projectPaymentType is null.<br>
     * <code>ProjectPaymentValidationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ProjectPaymentValidationException.class)
    public void test_update_projectPaymentTypeNull() throws Exception {
        instance.create(projectPayment, "testOperator");
        projectPayment.setProjectPaymentType(null);

        instance.update(projectPayment, "testOperator");
    }

    /**
     * <p>
     * Failure test for the method <code>update(ProjectPayment projectPayment)</code>
     * with projectPaymentTypeId is null.<br>
     * <code>ProjectPaymentValidationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ProjectPaymentValidationException.class)
    public void test_update_projectPaymentTypeIdNull() throws Exception {
        instance.create(projectPayment, "testOperator");
        projectPayment.getProjectPaymentType().setProjectPaymentTypeId(null);

        instance.update(projectPayment, "testOperator");
    }

    /**
     * <p>
     * Failure test for the method <code>update(ProjectPayment projectPayment)</code>
     * with resourceId is null.<br>
     * <code>ProjectPaymentValidationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ProjectPaymentValidationException.class)
    public void test_update_resourceIdNull() throws Exception {
        instance.create(projectPayment, "testOperator");
        projectPayment.setResourceId(null);

        instance.update(projectPayment, "testOperator");
    }

    /**
     * <p>
     * Failure test for the method <code>update(ProjectPayment projectPayment)</code>
     * with amount is null.<br>
     * <code>ProjectPaymentValidationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ProjectPaymentValidationException.class)
    public void test_update_amountNull() throws Exception {
        instance.create(projectPayment, "testOperator");
        projectPayment.setAmount(null);

        instance.update(projectPayment, "testOperator");
    }

    /**
     * <p>
     * Failure test for the method <code>update(ProjectPayment projectPayment)</code> with amount is negative.<br>
     * <code>ProjectPaymentValidationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ProjectPaymentValidationException.class)
    public void test_update_amountNegative() throws Exception {
        instance.create(projectPayment, "testOperator");
        projectPayment.setAmount(BigDecimal.valueOf(-0.01));

        instance.update(projectPayment, "testOperator");
    }

    /**
     * <p>
     * Failure test for the method <code>update(ProjectPayment projectPayment)</code>
     * with projectPaymentTypeId is invalid.<br>
     * <code>ProjectPaymentManagementDataIntegrityException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ProjectPaymentManagementDataIntegrityException.class)
    public void test_update_projectPaymentTypeIdInvalid() throws Exception {
        instance.create(projectPayment, "testOperator");
        projectPayment.getProjectPaymentType().setProjectPaymentTypeId(Long.MAX_VALUE);

        instance.update(projectPayment, "testOperator");
    }

    /**
     * <p>
     * Failure test for the method <code>update(ProjectPayment projectPayment)</code>
     * with resourceId is invalid.<br>
     * <code>ProjectPaymentManagementDataIntegrityException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ProjectPaymentManagementDataIntegrityException.class)
    public void test_update_resourceIdInvalid() throws Exception {
        instance.create(projectPayment, "testOperator");
        projectPayment.setResourceId(Long.MAX_VALUE);

        instance.update(projectPayment, "testOperator");
    }

    /**
     * <p>
     * Failure test for the method <code>update(ProjectPayment projectPayment)</code>
     * with submissionId is invalid.<br>
     * <code>ProjectPaymentManagementDataIntegrityException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ProjectPaymentManagementDataIntegrityException.class)
    public void test_update_submissionIdInvalid() throws Exception {
        instance.create(projectPayment, "testOperator");
        projectPayment.setSubmissionId(Long.MAX_VALUE);

        instance.update(projectPayment, "testOperator");
    }

    /**
     * <p>
     * Failure test for the method <code>update(ProjectPayment projectPayment)</code>
     * with an error has occurred.<br>
     * <code>ProjectPaymentManagementPersistenceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ProjectPaymentManagementPersistenceException.class)
    public void test_update_Error() throws Exception {
        instance.create(projectPayment, "testOperator");
        configuration.setPropertyValue("connectionName", "not_exist");
        instance.configure(configuration);

        instance.update(projectPayment, "testOperator");
    }

    /**
     * <p>
     * Accuracy test for the method <code>retrieve(long projectPaymentId)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_retrieve_1() throws Exception {
        ProjectPayment res = instance.retrieve(Long.MAX_VALUE);

        assertNull("'retrieve' should be correct.", res);
    }

    /**
     * <p>
     * Accuracy test for the method <code>retrieve(long projectPaymentId)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_retrieve_2() throws Exception {
        instance.create(projectPayment, "testOperator");

        ProjectPayment res = instance.retrieve(projectPayment.getProjectPaymentId());

        assertEquals("'retrieve' should be correct.",
            projectPayment.getProjectPaymentType().getProjectPaymentTypeId(),
            res.getProjectPaymentType().getProjectPaymentTypeId());
        assertEquals("'retrieve' should be correct.",
            projectPayment.getResourceId(), res.getResourceId());
        assertEquals("'retrieve' should be correct.",
            projectPayment.getSubmissionId(), res.getSubmissionId());
        assertEquals("'retrieve' should be correct.",
            projectPayment.getAmount().doubleValue(), res.getAmount().doubleValue(), 0.001);
        assertEquals("'retrieve' should be correct.",
            projectPayment.getPactsPaymentId(), res.getPactsPaymentId());
        assertNotNull("'retrieve' should be correct.",
            res.getCreateDate());
    }

    /**
     * <p>
     * Failure test for the method <code>retrieve(long projectPaymentId)</code> with persistence was not
     * configured properly.<br>
     * <code>IllegalStateException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalStateException.class)
    public void test_retrieve_NotConfigured() throws Exception {
        instance.create(projectPayment, "testOperator");

        instance = new DatabaseProjectPaymentPersistence();

        instance.retrieve(projectPayment.getProjectPaymentId());
    }

    /**
     * <p>
     * Failure test for the method <code>retrieve(long projectPaymentId)</code> with an error has occurred.<br>
     * <code>ProjectPaymentManagementPersistenceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ProjectPaymentManagementPersistenceException.class)
    public void test_retrieve_Error() throws Exception {
        instance.create(projectPayment, "testOperator");
        configuration.setPropertyValue("connectionName", "not_exist");
        instance.configure(configuration);

        instance.retrieve(projectPayment.getProjectPaymentId());
    }

    /**
     * <p>
     * Accuracy test for the method <code>delete(long projectPaymentId)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_delete_1() throws Exception {
        boolean res = instance.delete(Long.MAX_VALUE);

        assertFalse("'delete' should be correct.", res);
    }

    /**
     * <p>
     * Accuracy test for the method <code>delete(long projectPaymentId)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_delete_2() throws Exception {
        instance.create(projectPayment, "testOperator");

        boolean res = instance.delete(projectPayment.getProjectPaymentId());

        assertTrue("'delete' should be correct.", res);
    }

    /**
     * <p>
     * Failure test for the method <code>delete(long projectPaymentId)</code> with persistence was not
     * configured properly.<br>
     * <code>IllegalStateException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalStateException.class)
    public void test_delete_NotConfigured() throws Exception {
        instance.create(projectPayment, "testOperator");

        instance = new DatabaseProjectPaymentPersistence();

        instance.delete(projectPayment.getProjectPaymentId());
    }

    /**
     * <p>
     * Failure test for the method <code>delete(long projectPaymentId)</code> with an error has occurred.<br>
     * <code>ProjectPaymentManagementPersistenceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ProjectPaymentManagementPersistenceException.class)
    public void test_delete_Error() throws Exception {
        instance.create(projectPayment, "testOperator");
        configuration.setPropertyValue("connectionName", "not_exist");
        instance.configure(configuration);

        instance.delete(projectPayment.getProjectPaymentId());
    }
}