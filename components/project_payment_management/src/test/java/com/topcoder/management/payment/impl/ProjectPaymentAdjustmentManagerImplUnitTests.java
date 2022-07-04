/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.payment.impl;

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

/**
 * <p>
 * Unit tests for {@link ProjectPaymentAdjustmentManagerImpl} class.
 * </p>
 *
 * @author sparemax
 * @version 1.0
 */
public class ProjectPaymentAdjustmentManagerImplUnitTests extends BaseUnitTests {
    /**
     * <p>
     * Represents the <code>ProjectPaymentAdjustmentManagerImpl</code> instance used in tests.
     * </p>
     */
    private ProjectPaymentAdjustmentManagerImpl instance;

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
        return new JUnit4TestAdapter(ProjectPaymentAdjustmentManagerImplUnitTests.class);
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

        configuration = getConfig(ProjectPaymentAdjustmentManagerImpl.DEFAULT_CONFIG_NAMESPACE);

        instance = new ProjectPaymentAdjustmentManagerImpl(configuration);

        projectPaymentAdjustment = new ProjectPaymentAdjustment();
        projectPaymentAdjustment.setProjectId(1004L);
        projectPaymentAdjustment.setResourceRoleId(3L);
        projectPaymentAdjustment.setFixedAmount(BigDecimal.valueOf(500));
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>ProjectPaymentAdjustmentManagerImpl()</code>.<br>
     * Instance should be correctly created.
     * </p>
     */
    @Test
    public void testCtor1() {
        instance = new ProjectPaymentAdjustmentManagerImpl();

        assertNotNull("'persistence' should be correct.",
            getField(instance, "persistence"));
        assertNotNull("'log' should be correct.",
            getField(instance, "log"));
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>ProjectPaymentAdjustmentManagerImpl(String filePath,
     * String namespace)</code>.<br>
     * Instance should be correctly created.
     * </p>
     */
    @Test
    public void testCtor2() {
        instance = new ProjectPaymentAdjustmentManagerImpl(ProjectPaymentAdjustmentManagerImpl.DEFAULT_CONFIG_FILENAME,
            ProjectPaymentAdjustmentManagerImpl.DEFAULT_CONFIG_NAMESPACE);

        assertNotNull("'persistence' should be correct.",
            getField(instance, "persistence"));
        assertNotNull("'log' should be correct.",
            getField(instance, "log"));
    }

    /**
     * <p>
     * Failure test for the constructor <code>ProjectPaymentAdjustmentManagerImpl(String filePath,
     * String namespace)</code> with filePath is <code>null</code>.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCtor2_filePathNull() {
        new ProjectPaymentAdjustmentManagerImpl(null, ProjectPaymentAdjustmentManagerImpl.DEFAULT_CONFIG_NAMESPACE);
    }

    /**
     * <p>
     * Failure test for the constructor <code>ProjectPaymentAdjustmentManagerImpl(String filePath,
     * String namespace)</code> with filePath is empty.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCtor2_filePathEmpty() {
        new ProjectPaymentAdjustmentManagerImpl(EMPTY_STRING,
            ProjectPaymentAdjustmentManagerImpl.DEFAULT_CONFIG_NAMESPACE);
    }

    /**
     * <p>
     * Failure test for the constructor <code>ProjectPaymentAdjustmentManagerImpl(String filePath,
     * String namespace)</code> with namespace is <code>null</code>.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCtor2_namespaceNull() {
        new ProjectPaymentAdjustmentManagerImpl(ProjectPaymentAdjustmentManagerImpl.DEFAULT_CONFIG_FILENAME, null);
    }

    /**
     * <p>
     * Failure test for the constructor <code>ProjectPaymentAdjustmentManagerImpl(String filePath,
     * String namespace)</code> with namespace is empty.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCtor2_namespaceEmpty() {
        new ProjectPaymentAdjustmentManagerImpl(ProjectPaymentAdjustmentManagerImpl.DEFAULT_CONFIG_FILENAME,
            EMPTY_STRING);
    }

    /**
     * <p>
     * Failure test for the constructor <code>ProjectPaymentAdjustmentManagerImpl(String filePath,
     * String namespace)</code> with filePath is invalid.<br>
     * <code>ProjectPaymentManagementConfigurationException</code> is expected.
     * </p>
     */
    @Test(expected = ProjectPaymentManagementConfigurationException.class)
    public void testCtor2_filePathInvalid1() {
        new ProjectPaymentAdjustmentManagerImpl(TEST_FILES,
            ProjectPaymentAdjustmentManagerImpl.DEFAULT_CONFIG_NAMESPACE);
    }

    /**
     * <p>
     * Failure test for the constructor <code>ProjectPaymentAdjustmentManagerImpl(String filePath,
     * String namespace)</code> with filePath is invalid.<br>
     * <code>ProjectPaymentManagementConfigurationException</code> is expected.
     * </p>
     */
    @Test(expected = ProjectPaymentManagementConfigurationException.class)
    public void testCtor2_filePathInvalid2() {
        new ProjectPaymentAdjustmentManagerImpl(TEST_FILES + "SearchBundleManager.xml",
            ProjectPaymentAdjustmentManagerImpl.DEFAULT_CONFIG_NAMESPACE);
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>ProjectPaymentAdjustmentManagerImpl(ConfigurationObject
     * configuration)</code>.<br>
     * Instance should be correctly created.
     * </p>
     */
    @Test
    public void testCtor3_1() {
        instance = new ProjectPaymentAdjustmentManagerImpl(configuration);

        assertNotNull("'persistence' should be correct.",
            getField(instance, "persistence"));
        assertNotNull("'log' should be correct.",
            getField(instance, "log"));
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>ProjectPaymentAdjustmentManagerImpl(ConfigurationObject
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

        instance = new ProjectPaymentAdjustmentManagerImpl(configuration);

        assertNotNull("'persistence' should be correct.",
            getField(instance, "persistence"));
        assertNull("'log' should be correct.",
            getField(instance, "log"));
    }

    /**
     * <p>
     * Failure test for the constructor <code>ProjectPaymentAdjustmentManagerImpl(ConfigurationObject
     * configuration)</code> with configuration is <code>null</code>.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCtor3_configurationNull() {
        configuration = null;

        new ProjectPaymentAdjustmentManagerImpl(configuration);
    }

    /**
     * <p>
     * Failure test for the constructor <code>ProjectPaymentAdjustmentManagerImpl(ConfigurationObject
     * configuration)</code> with 'objectFactoryConfig' is missing.<br>
     * <code>ProjectPaymentManagementConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUni.
     */
    @Test(expected = ProjectPaymentManagementConfigurationException.class)
    public void testCtor3_objectFactoryConfigMissing() throws Exception {
        configuration.removeChild("objectFactoryConfig");

        new ProjectPaymentAdjustmentManagerImpl(configuration);
    }

    /**
     * <p>
     * Failure test for the constructor <code>ProjectPaymentAdjustmentManagerImpl(ConfigurationObject
     * configuration)</code> with 'objectFactoryConfig' is invalid.<br>
     * <code>ProjectPaymentManagementConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUni.
     */
    @Test(expected = ProjectPaymentManagementConfigurationException.class)
    public void testCtor3_objectFactoryConfigInvalid() throws Exception {
        configuration.getChild("objectFactoryConfig").getChild("DatabaseProjectPaymentAdjustmentPersistence")
            .setPropertyValue("type", "invalid_class");

        new ProjectPaymentAdjustmentManagerImpl(configuration);
    }

    /**
     * <p>
     * Failure test for the constructor <code>ProjectPaymentAdjustmentManagerImpl(ConfigurationObject
     * configuration)</code> with 'projectPaymentAdjustmentPersistenceKey' is missing.<br>
     * <code>ProjectPaymentManagementConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUni.
     */
    @Test(expected = ProjectPaymentManagementConfigurationException.class)
    public void testCtor3_projectPaymentAdjustmentPersistenceKeyMissing() throws Exception {
        configuration.removeProperty("projectPaymentAdjustmentPersistenceKey");

        new ProjectPaymentAdjustmentManagerImpl(configuration);
    }

    /**
     * <p>
     * Failure test for the constructor <code>ProjectPaymentAdjustmentManagerImpl(ConfigurationObject
     * configuration)</code> with 'projectPaymentAdjustmentPersistenceKey' is empty.<br>
     * <code>ProjectPaymentManagementConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUni.
     */
    @Test(expected = ProjectPaymentManagementConfigurationException.class)
    public void testCtor3_projectPaymentAdjustmentPersistenceKeyEmpty() throws Exception {
        configuration.setPropertyValue("projectPaymentAdjustmentPersistenceKey", EMPTY_STRING);

        new ProjectPaymentAdjustmentManagerImpl(configuration);
    }

    /**
     * <p>
     * Failure test for the constructor <code>ProjectPaymentAdjustmentManagerImpl(ConfigurationObject
     * configuration)</code> with 'projectPaymentAdjustmentPersistenceConfig' is missing.<br>
     * <code>ProjectPaymentManagementConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUni.
     */
    @Test(expected = ProjectPaymentManagementConfigurationException.class)
    public void testCtor3_projectPaymentAdjustmentPersistenceConfigMissing() throws Exception {
        configuration.removeChild("projectPaymentAdjustmentPersistenceConfig");

        new ProjectPaymentAdjustmentManagerImpl(configuration);
    }

    /**
     * <p>
     * Failure test for the constructor <code>ProjectPaymentAdjustmentManagerImpl(ConfigurationObject
     * configuration)</code> with failed to create an instance of ProjectPaymentPersistence.<br>
     * <code>ProjectPaymentManagementConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUni.
     */
    @Test(expected = ProjectPaymentManagementConfigurationException.class)
    public void testCtor3_CreateProjectPaymentPersistenceFailed1() throws Exception {
        configuration.getChild("objectFactoryConfig").getChild("DatabaseProjectPaymentAdjustmentPersistence")
            .setPropertyValue("type", Integer.class.getName());

        new ProjectPaymentAdjustmentManagerImpl(configuration);
    }

    /**
     * <p>
     * Failure test for the constructor <code>ProjectPaymentAdjustmentManagerImpl(ConfigurationObject
     * configuration)</code> with failed to create an instance of ProjectPaymentPersistence.<br>
     * <code>ProjectPaymentManagementConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception
     *             to JUni.
     */
    @Test(expected = ProjectPaymentManagementConfigurationException.class)
    public void testCtor3_CreateProjectPaymentPersistenceFailed2() throws Exception {
        configuration.getChild("objectFactoryConfig").getChild("DatabaseProjectPaymentAdjustmentPersistence")
            .setPropertyValue("type", String.class.getName());

        new ProjectPaymentAdjustmentManagerImpl(configuration);
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
        configuration.getChild("projectPaymentAdjustmentPersistenceConfig").setPropertyValue("connectionName",
            "not_exist");
        instance = new ProjectPaymentAdjustmentManagerImpl(configuration);

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

        configuration.getChild("projectPaymentAdjustmentPersistenceConfig").setPropertyValue("connectionName",
            "not_exist");
        instance = new ProjectPaymentAdjustmentManagerImpl(configuration);

        instance.retrieveByProjectId(projectPaymentAdjustment.getProjectId());
    }
}