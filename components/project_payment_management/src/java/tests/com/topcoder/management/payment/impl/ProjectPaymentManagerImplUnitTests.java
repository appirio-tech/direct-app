/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.payment.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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
import com.topcoder.management.payment.search.ProjectPaymentFilterBuilder;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.search.builder.filter.NullFilter;

/**
 * <p>
 * Unit tests for {@link ProjectPaymentManagerImpl} class.
 * </p>
 *
 * @author sparemax
 * @version 1.0.1
 */
public class ProjectPaymentManagerImplUnitTests extends BaseUnitTests {
    /**
     * <p>
     * Represents the <code>ProjectPaymentManagerImpl</code> instance used in tests.
     * </p>
     */
    private ProjectPaymentManagerImpl instance;

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
     * Represents the filter used in tests.
     * </p>
     */
    private Filter filter;

    /**
     * <p>
     * Adapter for earlier versions of JUnit.
     * </p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(ProjectPaymentManagerImplUnitTests.class);
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

        configuration = getConfig(ProjectPaymentManagerImpl.DEFAULT_CONFIG_NAMESPACE);

        instance = new ProjectPaymentManagerImpl(configuration);

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
     * Accuracy test for the constructor <code>ProjectPaymentManagerImpl()</code>.<br>
     * Instance should be correctly created.
     * </p>
     */
    @Test
    public void testCtor1() {
        instance = new ProjectPaymentManagerImpl();

        assertNotNull("'persistence' should be correct.",
            getField(instance, "persistence"));
        assertNotNull("'log' should be correct.",
            getField(instance, "log"));
        assertNotNull("'searchBundle' should be correct.",
            getField(instance, "searchBundle"));
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>ProjectPaymentManagerImpl(String filePath,
     * String namespace)</code>.<br>
     * Instance should be correctly created.
     * </p>
     */
    @Test
    public void testCtor2() {
        instance = new ProjectPaymentManagerImpl(ProjectPaymentManagerImpl.DEFAULT_CONFIG_FILENAME,
            ProjectPaymentManagerImpl.DEFAULT_CONFIG_NAMESPACE);

        assertNotNull("'persistence' should be correct.",
            getField(instance, "persistence"));
        assertNotNull("'log' should be correct.",
            getField(instance, "log"));
        assertNotNull("'searchBundle' should be correct.",
            getField(instance, "searchBundle"));
    }

    /**
     * <p>
     * Failure test for the constructor <code>ProjectPaymentManagerImpl(String filePath, String namespace)</code>
     * with filePath is <code>null</code>.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCtor2_filePathNull() {
        new ProjectPaymentManagerImpl(null, ProjectPaymentManagerImpl.DEFAULT_CONFIG_NAMESPACE);
    }

    /**
     * <p>
     * Failure test for the constructor <code>ProjectPaymentManagerImpl(String filePath, String namespace)</code>
     * with filePath is empty.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCtor2_filePathEmpty() {
        new ProjectPaymentManagerImpl(EMPTY_STRING, ProjectPaymentManagerImpl.DEFAULT_CONFIG_NAMESPACE);
    }

    /**
     * <p>
     * Failure test for the constructor <code>ProjectPaymentManagerImpl(String filePath, String namespace)</code>
     * with namespace is <code>null</code>.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCtor2_namespaceNull() {
        new ProjectPaymentManagerImpl(ProjectPaymentManagerImpl.DEFAULT_CONFIG_FILENAME, null);
    }

    /**
     * <p>
     * Failure test for the constructor <code>ProjectPaymentManagerImpl(String filePath, String namespace)</code>
     * with namespace is empty.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCtor2_namespaceEmpty() {
        new ProjectPaymentManagerImpl(ProjectPaymentManagerImpl.DEFAULT_CONFIG_FILENAME, EMPTY_STRING);
    }

    /**
     * <p>
     * Failure test for the constructor <code>ProjectPaymentManagerImpl(String filePath, String namespace)</code>
     * with filePath is invalid.<br>
     * <code>ProjectPaymentManagementConfigurationException</code> is expected.
     * </p>
     */
    @Test(expected = ProjectPaymentManagementConfigurationException.class)
    public void testCtor2_filePathInvalid1() {
        new ProjectPaymentManagerImpl(TEST_FILES, ProjectPaymentManagerImpl.DEFAULT_CONFIG_NAMESPACE);
    }

    /**
     * <p>
     * Failure test for the constructor <code>ProjectPaymentManagerImpl(String filePath, String namespace)</code>
     * with filePath is invalid.<br>
     * <code>ProjectPaymentManagementConfigurationException</code> is expected.
     * </p>
     */
    @Test(expected = ProjectPaymentManagementConfigurationException.class)
    public void testCtor2_filePathInvalid2() {
        new ProjectPaymentManagerImpl(TEST_FILES + "SearchBundleManager.xml",
            ProjectPaymentManagerImpl.DEFAULT_CONFIG_NAMESPACE);
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>ProjectPaymentManagerImpl(ConfigurationObject
     * configuration)</code>.<br>
     * Instance should be correctly created.
     * </p>
     */
    @Test
    public void testCtor3_1() {
        instance = new ProjectPaymentManagerImpl(configuration);

        assertNotNull("'persistence' should be correct.",
            getField(instance, "persistence"));
        assertNotNull("'log' should be correct.",
            getField(instance, "log"));
        assertNotNull("'searchBundle' should be correct.",
            getField(instance, "searchBundle"));
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>ProjectPaymentManagerImpl(ConfigurationObject
     * configuration)</code>.<br>
     * Instance should be correctly created.
     * </p>
     *
     * @throws Exception
     *             to JUni.
     */
    @Test
    public void testCtor3_2() throws Exception {
        configuration.removeProperty("loggerName");

        instance = new ProjectPaymentManagerImpl(configuration);

        assertNotNull("'persistence' should be correct.",
            getField(instance, "persistence"));
        assertNull("'log' should be correct.",
            getField(instance, "log"));
        assertNotNull("'searchBundle' should be correct.",
            getField(instance, "searchBundle"));
    }

    /**
     * <p>
     * Failure test for the constructor <code>ProjectPaymentManagerImpl(ConfigurationObject configuration)</code>
     * with configuration is <code>null</code>.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCtor3_configurationNull() {
        configuration = null;

        new ProjectPaymentManagerImpl(configuration);
    }

    /**
     * <p>
     * Failure test for the constructor <code>ProjectPaymentManagerImpl(ConfigurationObject configuration)</code>
     * with 'searchBundleManagerNamespace' is missing.<br>
     * <code>ProjectPaymentManagementConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUni.
     */
    @Test(expected = ProjectPaymentManagementConfigurationException.class)
    public void testCtor3_searchBundleManagerNamespaceMissing() throws Exception {
        configuration.removeProperty("searchBundleManagerNamespace");

        new ProjectPaymentManagerImpl(configuration);
    }

    /**
     * <p>
     * Failure test for the constructor <code>ProjectPaymentManagerImpl(ConfigurationObject configuration)</code>
     * with 'searchBundleManagerNamespace' is empty.<br>
     * <code>ProjectPaymentManagementConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUni.
     */
    @Test(expected = ProjectPaymentManagementConfigurationException.class)
    public void testCtor3_searchBundleManagerNamespaceEmpty() throws Exception {
        configuration.setPropertyValue("searchBundleManagerNamespace", EMPTY_STRING);

        new ProjectPaymentManagerImpl(configuration);
    }

    /**
     * <p>
     * Failure test for the constructor <code>ProjectPaymentManagerImpl(ConfigurationObject configuration)</code>
     * with 'searchBundleManagerNamespace' is invalid.<br>
     * <code>ProjectPaymentManagementConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUni.
     */
    @Test(expected = ProjectPaymentManagementConfigurationException.class)
    public void testCtor3_searchBundleManagerNamespaceInvalid() throws Exception {
        configuration.setPropertyValue("searchBundleManagerNamespace", "invalid.searchBundleManagerNamespace");

        new ProjectPaymentManagerImpl(configuration);
    }

    /**
     * <p>
     * Failure test for the constructor <code>ProjectPaymentManagerImpl(ConfigurationObject configuration)</code>
     * with 'projectPaymentSearchBundleName' is missing.<br>
     * <code>ProjectPaymentManagementConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUni.
     */
    @Test(expected = ProjectPaymentManagementConfigurationException.class)
    public void testCtor3_projectPaymentSearchBundleNameMissing() throws Exception {
        configuration.removeProperty("projectPaymentSearchBundleName");

        new ProjectPaymentManagerImpl(configuration);
    }

    /**
     * <p>
     * Failure test for the constructor <code>ProjectPaymentManagerImpl(ConfigurationObject configuration)</code>
     * with 'projectPaymentSearchBundleName' is empty.<br>
     * <code>ProjectPaymentManagementConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUni.
     */
    @Test(expected = ProjectPaymentManagementConfigurationException.class)
    public void testCtor3_projectPaymentSearchBundleNameEmpty() throws Exception {
        configuration.setPropertyValue("projectPaymentSearchBundleName", EMPTY_STRING);

        new ProjectPaymentManagerImpl(configuration);
    }

    /**
     * <p>
     * Failure test for the constructor <code>ProjectPaymentManagerImpl(ConfigurationObject configuration)</code>
     * with 'projectPaymentSearchBundleName' is invalid.<br>
     * <code>ProjectPaymentManagementConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUni.
     */
    @Test(expected = ProjectPaymentManagementConfigurationException.class)
    public void testCtor3_projectPaymentSearchBundleNameInvalid() throws Exception {
        configuration.setPropertyValue("projectPaymentSearchBundleName", "projectPaymentSearchBundleNameInvalid");

        new ProjectPaymentManagerImpl(configuration);
    }

    /**
     * <p>
     * Failure test for the constructor <code>ProjectPaymentManagerImpl(ConfigurationObject configuration)</code>
     * with 'objectFactoryConfig' is missing.<br>
     * <code>ProjectPaymentManagementConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUni.
     */
    @Test(expected = ProjectPaymentManagementConfigurationException.class)
    public void testCtor3_objectFactoryConfigMissing() throws Exception {
        configuration.removeChild("objectFactoryConfig");

        new ProjectPaymentManagerImpl(configuration);
    }

    /**
     * <p>
     * Failure test for the constructor <code>ProjectPaymentManagerImpl(ConfigurationObject configuration)</code>
     * with 'objectFactoryConfig' is invalid.<br>
     * <code>ProjectPaymentManagementConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUni.
     */
    @Test(expected = ProjectPaymentManagementConfigurationException.class)
    public void testCtor3_objectFactoryConfigInvalid() throws Exception {
        configuration.getChild("objectFactoryConfig").getChild("DatabaseProjectPaymentPersistence")
            .setPropertyValue("type", "invalid_class");

        new ProjectPaymentManagerImpl(configuration);
    }

    /**
     * <p>
     * Failure test for the constructor <code>ProjectPaymentManagerImpl(ConfigurationObject configuration)</code>
     * with 'projectPaymentPersistenceKey' is missing.<br>
     * <code>ProjectPaymentManagementConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUni.
     */
    @Test(expected = ProjectPaymentManagementConfigurationException.class)
    public void testCtor3_projectPaymentPersistenceKeyMissing() throws Exception {
        configuration.removeProperty("projectPaymentPersistenceKey");

        new ProjectPaymentManagerImpl(configuration);
    }

    /**
     * <p>
     * Failure test for the constructor <code>ProjectPaymentManagerImpl(ConfigurationObject configuration)</code>
     * with 'projectPaymentPersistenceKey' is empty.<br>
     * <code>ProjectPaymentManagementConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUni.
     */
    @Test(expected = ProjectPaymentManagementConfigurationException.class)
    public void testCtor3_projectPaymentPersistenceKeyEmpty() throws Exception {
        configuration.setPropertyValue("projectPaymentPersistenceKey", EMPTY_STRING);

        new ProjectPaymentManagerImpl(configuration);
    }

    /**
     * <p>
     * Failure test for the constructor <code>ProjectPaymentManagerImpl(ConfigurationObject configuration)</code>
     * with 'projectPaymentPersistenceConfig' is missing.<br>
     * <code>ProjectPaymentManagementConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUni.
     */
    @Test(expected = ProjectPaymentManagementConfigurationException.class)
    public void testCtor3_projectPaymentPersistenceConfigMissing() throws Exception {
        configuration.removeChild("projectPaymentPersistenceConfig");

        new ProjectPaymentManagerImpl(configuration);
    }

    /**
     * <p>
     * Failure test for the constructor <code>ProjectPaymentManagerImpl(ConfigurationObject configuration)</code>
     * with failed to create an instance of ProjectPaymentPersistence.<br>
     * <code>ProjectPaymentManagementConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUni.
     */
    @Test(expected = ProjectPaymentManagementConfigurationException.class)
    public void testCtor3_CreateProjectPaymentPersistenceFailed1() throws Exception {
        configuration.getChild("objectFactoryConfig").getChild("DatabaseProjectPaymentPersistence")
            .setPropertyValue("type", Integer.class.getName());

        new ProjectPaymentManagerImpl(configuration);
    }

    /**
     * <p>
     * Failure test for the constructor <code>ProjectPaymentManagerImpl(ConfigurationObject configuration)</code>
     * with failed to create an instance of ProjectPaymentPersistence.<br>
     * <code>ProjectPaymentManagementConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUni.
     */
    @Test(expected = ProjectPaymentManagementConfigurationException.class)
    public void testCtor3_CreateProjectPaymentPersistenceFailed2() throws Exception {
        configuration.getChild("objectFactoryConfig").getChild("DatabaseProjectPaymentPersistence")
            .setPropertyValue("type", String.class.getName());

        new ProjectPaymentManagerImpl(configuration);
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
        configuration.getChild("projectPaymentPersistenceConfig").setPropertyValue("connectionName", "not_exist");
        instance = new ProjectPaymentManagerImpl(configuration);

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
        configuration.getChild("projectPaymentPersistenceConfig").setPropertyValue("connectionName", "not_exist");
        instance = new ProjectPaymentManagerImpl(configuration);

        instance.update(projectPayment, "testOperator");
    }

    /**
     * <p>
     * Accuracy test for the method <code>search(Filter filter)</code>.<br>
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
     * Accuracy test for the method <code>search(Filter filter)</code>.<br>
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
     * Failure test for the method <code>search(Filter filter)</code> with an error has occurred.<br>
     * <code>ProjectPaymentManagementPersistenceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ProjectPaymentManagementPersistenceException.class)
    public void test_retrieve_Error() throws Exception {
        instance.create(projectPayment, "testOperator");
        configuration.getChild("projectPaymentPersistenceConfig").setPropertyValue("connectionName", "not_exist");
        instance = new ProjectPaymentManagerImpl(configuration);

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
        configuration.getChild("projectPaymentPersistenceConfig").setPropertyValue("connectionName", "not_exist");
        instance = new ProjectPaymentManagerImpl(configuration);

        instance.delete(projectPayment.getProjectPaymentId());
    }

    /**
     * <p>
     * Accuracy test for the method <code>search(Filter filter)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_search_1() throws Exception {
        clearDB();
        List<ProjectPayment> res = instance.search(filter);

        assertEquals("'search' should be correct.", 0, res.size());
    }

    /**
     * <p>
     * Accuracy test for the method <code>search(Filter filter)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_search_2() throws Exception {
        executeUpdate("DELETE FROM project_payment");

        projectPayment.setSubmissionId(null);
        instance.create(projectPayment, "testOperator");

        List<ProjectPayment> res = instance.search(filter);

        assertEquals("'search' should be correct.", 1, res.size());
        ProjectPayment retrievedProjectPayment = res.get(0);
        assertEquals("'search' should be correct.",
            projectPayment.getProjectPaymentType().getProjectPaymentTypeId(),
            retrievedProjectPayment.getProjectPaymentType().getProjectPaymentTypeId());
        assertEquals("'search' should be correct.",
            projectPayment.getResourceId(), retrievedProjectPayment.getResourceId());
        assertNull("'search' should be correct.",
            retrievedProjectPayment.getSubmissionId());
        assertEquals("'search' should be correct.",
            projectPayment.getAmount().doubleValue(), retrievedProjectPayment.getAmount().doubleValue(), 0.001);
        assertEquals("'search' should be correct.",
            projectPayment.getPactsPaymentId(), retrievedProjectPayment.getPactsPaymentId());
        assertNotNull("'search' should be correct.",
            retrievedProjectPayment.getCreateDate());
    }

    /**
     * <p>
     * Accuracy test for the method <code>search(Filter filter)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_search_3() throws Exception {
        instance.create(projectPayment, "testOperator");

        List<ProjectPayment> res = instance.search(filter);

        assertEquals("'search' should be correct.", 3, res.size());
    }

    /**
     * <p>
     * Accuracy test for the method <code>search(Filter filter)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_search_4() throws Exception {
        executeUpdate("DELETE FROM project_payment");

        instance.create(projectPayment, "testOperator");

        filter = ProjectPaymentFilterBuilder.createResourceIdFilter(projectPayment.getResourceId());
        List<ProjectPayment> res = instance.search(filter);

        assertEquals("'search' should be correct.", 1, res.size());
        ProjectPayment retrievedProjectPayment = res.get(0);
        assertEquals("'search' should be correct.",
            projectPayment.getProjectPaymentType().getProjectPaymentTypeId(),
            retrievedProjectPayment.getProjectPaymentType().getProjectPaymentTypeId());
        assertEquals("'search' should be correct.",
            projectPayment.getResourceId(), retrievedProjectPayment.getResourceId());
        assertEquals("'search' should be correct.",
            projectPayment.getSubmissionId(), retrievedProjectPayment.getSubmissionId());
        assertEquals("'search' should be correct.",
            projectPayment.getAmount().doubleValue(), retrievedProjectPayment.getAmount().doubleValue(), 0.001);
        assertEquals("'search' should be correct.",
            projectPayment.getPactsPaymentId(), retrievedProjectPayment.getPactsPaymentId());
        assertNotNull("'search' should be correct.",
            retrievedProjectPayment.getCreateDate());
    }

    /**
     * <p>
     * Accuracy test for the method <code>search(Filter filter)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_search_5() throws Exception {
        executeUpdate("DELETE FROM project_payment");

        instance.create(projectPayment, "testOperator");

        filter = ProjectPaymentFilterBuilder.createProjectIdFilter(100001);
        List<ProjectPayment> res = instance.search(filter);

        assertEquals("'search' should be correct.", 1, res.size());
        ProjectPayment retrievedProjectPayment = res.get(0);
        assertEquals("'search' should be correct.",
            projectPayment.getProjectPaymentType().getProjectPaymentTypeId(),
            retrievedProjectPayment.getProjectPaymentType().getProjectPaymentTypeId());
        assertEquals("'search' should be correct.",
            projectPayment.getResourceId(), retrievedProjectPayment.getResourceId());
        assertEquals("'search' should be correct.",
            projectPayment.getSubmissionId(), retrievedProjectPayment.getSubmissionId());
        assertEquals("'search' should be correct.",
            projectPayment.getAmount().doubleValue(), retrievedProjectPayment.getAmount().doubleValue(), 0.001);
        assertEquals("'search' should be correct.",
            projectPayment.getPactsPaymentId(), retrievedProjectPayment.getPactsPaymentId());
        assertNotNull("'search' should be correct.",
            retrievedProjectPayment.getCreateDate());
    }

    /**
     * <p>
     * Accuracy test for the method <code>search(Filter filter)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_search_6() throws Exception {
        executeUpdate("DELETE FROM project_payment");

        instance.create(projectPayment, "testOperator");

        filter = ProjectPaymentFilterBuilder.createProjectPaymentTypeIdFilter(projectPayment.getProjectPaymentType()
            .getProjectPaymentTypeId());
        List<ProjectPayment> res = instance.search(filter);

        assertEquals("'search' should be correct.", 1, res.size());
        ProjectPayment retrievedProjectPayment = res.get(0);
        assertEquals("'search' should be correct.", projectPayment.getProjectPaymentType().getProjectPaymentTypeId(),
            retrievedProjectPayment.getProjectPaymentType().getProjectPaymentTypeId());
        assertEquals("'search' should be correct.",
            projectPayment.getResourceId(), retrievedProjectPayment.getResourceId());
        assertEquals("'search' should be correct.",
            projectPayment.getSubmissionId(), retrievedProjectPayment.getSubmissionId());
        assertEquals("'search' should be correct.",
            projectPayment.getAmount().doubleValue(), retrievedProjectPayment.getAmount().doubleValue(), 0.001);
        assertEquals("'search' should be correct.",
            projectPayment.getPactsPaymentId(), retrievedProjectPayment.getPactsPaymentId());
        assertNotNull("'search' should be correct.",
            retrievedProjectPayment.getCreateDate());
    }

    /**
     * <p>
     * Accuracy test for the method <code>search(Filter filter)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_search_7() throws Exception {
        executeUpdate("DELETE FROM project_payment");

        instance.create(projectPayment, "testOperator");

        filter = ProjectPaymentFilterBuilder.createSubmissionIdFilter(projectPayment.getSubmissionId());
        List<ProjectPayment> res = instance.search(filter);

        assertEquals("'search' should be correct.", 1, res.size());
        ProjectPayment retrievedProjectPayment = res.get(0);
        assertEquals("'search' should be correct.", projectPayment.getProjectPaymentType().getProjectPaymentTypeId(),
            retrievedProjectPayment.getProjectPaymentType().getProjectPaymentTypeId());
        assertEquals("'search' should be correct.",
            projectPayment.getResourceId(), retrievedProjectPayment.getResourceId());
        assertEquals("'search' should be correct.",
            projectPayment.getSubmissionId(), retrievedProjectPayment.getSubmissionId());
        assertEquals("'search' should be correct.",
            projectPayment.getAmount().doubleValue(), retrievedProjectPayment.getAmount().doubleValue(), 0.001);
        assertEquals("'search' should be correct.",
            projectPayment.getPactsPaymentId(), retrievedProjectPayment.getPactsPaymentId());
        assertNotNull("'search' should be correct.",
            retrievedProjectPayment.getCreateDate());
    }

    /**
     * <p>
     * Accuracy test for the method <code>search(Filter filter)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_search_8() throws Exception {
        executeUpdate("DELETE FROM project_payment");

        projectPayment.setPactsPaymentId(null);
        instance.create(projectPayment, "testOperator");

        filter = ProjectPaymentFilterBuilder.createPactsPaymentIdFilter(false);
        List<ProjectPayment> res = instance.search(filter);

        assertEquals("'search' should be correct.", 1, res.size());
        ProjectPayment retrievedProjectPayment = res.get(0);
        assertEquals("'search' should be correct.", projectPayment.getProjectPaymentType().getProjectPaymentTypeId(),
            retrievedProjectPayment.getProjectPaymentType().getProjectPaymentTypeId());
        assertEquals("'search' should be correct.",
            projectPayment.getResourceId(), retrievedProjectPayment.getResourceId());
        assertEquals("'search' should be correct.",
            projectPayment.getSubmissionId(), retrievedProjectPayment.getSubmissionId());
        assertEquals("'search' should be correct.",
            projectPayment.getAmount().doubleValue(), retrievedProjectPayment.getAmount().doubleValue(), 0.001);
        assertEquals("'search' should be correct.",
            projectPayment.getPactsPaymentId(), retrievedProjectPayment.getPactsPaymentId());
        assertNotNull("'search' should be correct.",
            retrievedProjectPayment.getCreateDate());
    }

    /**
     * <p>
     * Accuracy test for the method <code>search(Filter filter)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_search_9() throws Exception {
        executeUpdate("DELETE FROM project_payment");

        projectPayment.setPactsPaymentId(1L);
        instance.create(projectPayment, "testOperator");

        filter = ProjectPaymentFilterBuilder.createPactsPaymentIdFilter(true);
        List<ProjectPayment> res = instance.search(filter);

        assertEquals("'search' should be correct.", 1, res.size());
        ProjectPayment retrievedProjectPayment = res.get(0);
        assertEquals("'search' should be correct.", projectPayment.getProjectPaymentType().getProjectPaymentTypeId(),
            retrievedProjectPayment.getProjectPaymentType().getProjectPaymentTypeId());
        assertEquals("'search' should be correct.",
            projectPayment.getResourceId(), retrievedProjectPayment.getResourceId());
        assertEquals("'search' should be correct.",
            projectPayment.getSubmissionId(), retrievedProjectPayment.getSubmissionId());
        assertEquals("'search' should be correct.",
            projectPayment.getAmount().doubleValue(), retrievedProjectPayment.getAmount().doubleValue(), 0.001);
        assertEquals("'search' should be correct.",
            projectPayment.getPactsPaymentId(), retrievedProjectPayment.getPactsPaymentId());
        assertNotNull("'search' should be correct.",
            retrievedProjectPayment.getCreateDate());
    }

    /**
     * <p>
     * Failure test for the method <code>search(Filter filter)</code> with an error has occurred.<br>
     * <code>ProjectPaymentManagementPersistenceException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ProjectPaymentManagementPersistenceException.class)
    public void test_search_Error() throws Exception {
        instance.search(new NullFilter("invalid"));
    }
}