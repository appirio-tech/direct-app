/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.payment.failuretests;

import java.math.BigDecimal;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.persistence.ConfigurationFileManager;
import com.topcoder.management.payment.ProjectPaymentAdjustment;
import com.topcoder.management.payment.ProjectPaymentAdjustmentValidationException;
import com.topcoder.management.payment.ProjectPaymentManagementConfigurationException;
import com.topcoder.management.payment.impl.ProjectPaymentManagementPersistenceException;
import com.topcoder.management.payment.impl.persistence.DatabaseProjectPaymentAdjustmentPersistence;

/**
 * <p>
 * Failure test cases for DatabaseProjectPaymentAdjustmentPersistence.
 * </p>
 *
 * @author lqz
 * @version 1.0
 */
public class DatabaseProjectPaymentAdjustmentPersistenceFailureTests extends TestCase {

    /**
     * <p>
     * The DatabaseProjectPaymentAdjustmentPersistence instance for testing.
     * </p>
     */
    private DatabaseProjectPaymentAdjustmentPersistence instance;

    /**
     * <p>
     * Setup test environment.
     * </p>
     * @throws Exception to JUnit
     *
     */
    protected void setUp() throws Exception {
        instance = new DatabaseProjectPaymentAdjustmentPersistence();

        ConfigurationFileManager configFileManager = new ConfigurationFileManager("failure/ProjectPaymentAdjustmentManagerImpl.properties");
        ConfigurationObject configObject = configFileManager.getConfiguration("com.topcoder.management.payment.impl.ProjectPaymentAdjustmentManagerImpl");
        ConfigurationObject config = configObject.getChild("com.topcoder.management.payment.impl.ProjectPaymentAdjustmentManagerImpl").getChild("projectPaymentAdjustmentPersistenceConfig");
        instance.configure(config);
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     * @throws Exception to JUnit
     *
     */
    protected void tearDown() throws Exception {
    }

    /**
     * <p>
     * Return all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(DatabaseProjectPaymentAdjustmentPersistenceFailureTests.class);
    }


    /**
     * <p>
     * Tests DatabaseProjectPaymentAdjustmentPersistence#save(ProjectPaymentAdjustment) for failure.
     * It tests the case that when ProjectPaymentAdjustment is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void test_save_1() throws Exception {
        try {
            instance.save(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }
    
    /**
     * <p>
     * Tests DatabaseProjectPaymentAdjustmentPersistence#save(ProjectPaymentAdjustment) for failure.
     * It tests the case that when dbConnectionFactory is null and expects IllegalStateException.
     * </p>
     * @throws Exception to JUnit
     */
    public void test_save_2() throws Exception {
    	ProjectPaymentAdjustment obj = new ProjectPaymentAdjustment();
    	instance = new DatabaseProjectPaymentAdjustmentPersistence();
    	obj.setFixedAmount(new BigDecimal(1000));
    	obj.setProjectId(1L);
    	obj.setResourceRoleId(2L);
        try {
            instance.save(obj);
            fail("IllegalStateException expected.");
        } catch (IllegalStateException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DatabaseProjectPaymentAdjustmentPersistence#save(ProjectPaymentAdjustment) for failure.
     * It tests the case that when dbConnectionFactory is null and expects ProjectPaymentManagementPersistenceException.
     * </p>
     * @throws Exception to JUnit
     */
    public void test_save_3() throws Exception {
    	ProjectPaymentAdjustment obj = new ProjectPaymentAdjustment();
    	obj.setFixedAmount(new BigDecimal(1000));
    	obj.setProjectId(1L);
    	obj.setResourceRoleId(2L);
        try {
            instance.save(obj);
            fail("ProjectPaymentManagementPersistenceException expected.");
        } catch (ProjectPaymentManagementPersistenceException iae) {
            //good
        }
    }

    
    /**
     * <p>
     * Tests DatabaseProjectPaymentAdjustmentPersistence#save(ProjectPaymentAdjustment) for failure.
     * It tests the case that when ProjectId is null and expects ProjectPaymentAdjustmentValidationException.
     * </p>
     * @throws Exception to JUnit
     */
    public void test_save_4() throws Exception {
    	ProjectPaymentAdjustment obj = new ProjectPaymentAdjustment();
    	obj.setFixedAmount(new BigDecimal(1000));
    	obj.setResourceRoleId(2L);
        try {
            instance.save(obj);
            fail("ProjectPaymentAdjustmentValidationException expected.");
        } catch (ProjectPaymentAdjustmentValidationException iae) {
            //good
        }
    }
    
    /**
     * <p>
     * Tests DatabaseProjectPaymentAdjustmentPersistence#save(ProjectPaymentAdjustment) for failure.
     * It tests the case that when resource role id is null and expects ProjectPaymentAdjustmentValidationException.
     * </p>
     * @throws Exception to JUnit
     */
    public void test_save_5() throws Exception {
    	ProjectPaymentAdjustment obj = new ProjectPaymentAdjustment();
    	obj.setFixedAmount(new BigDecimal(1000));
    	obj.setProjectId(1L);
        try {
            instance.save(obj);
            fail("ProjectPaymentAdjustmentValidationException expected.");
        } catch (ProjectPaymentAdjustmentValidationException iae) {
            //good
        }
    }
    
    /**
     * <p>
     * Tests DatabaseProjectPaymentAdjustmentPersistence#save(ProjectPaymentAdjustment) for failure.
     * It tests the case that when fixed amount is negative and expects ProjectPaymentAdjustmentValidationException.
     * </p>
     * @throws Exception to JUnit
     */
    public void test_save_8() throws Exception {
    	ProjectPaymentAdjustment obj = new ProjectPaymentAdjustment();
    	obj.setFixedAmount(new BigDecimal(-1));
    	obj.setProjectId(1L);
    	obj.setResourceRoleId(2L);
        try {
            instance.save(obj);
            fail("ProjectPaymentAdjustmentValidationException expected.");
        } catch (ProjectPaymentAdjustmentValidationException iae) {
            //good
        }
    }
    
    /**
     * <p>
     * Tests DatabaseProjectPaymentAdjustmentPersistence#save(ProjectPaymentAdjustment) for failure.
     * It tests the case that when multiplier is negative and expects ProjectPaymentAdjustmentValidationException.
     * </p>
     * @throws Exception to JUnit
     */
    public void test_save_6() throws Exception {
    	ProjectPaymentAdjustment obj = new ProjectPaymentAdjustment();
    	obj.setProjectId(1L);
    	obj.setMultiplier(-3.0);
    	obj.setResourceRoleId(2L);
        try {
            instance.save(obj);
            fail("ProjectPaymentAdjustmentValidationException expected.");
        } catch (ProjectPaymentAdjustmentValidationException iae) {
            //good
        }
    }
    
    /**
     * <p>
     * Tests DatabaseProjectPaymentAdjustmentPersistence#save(ProjectPaymentAdjustment) for failure.
     * It tests the case that when multiplier and amount are both non-null and expects ProjectPaymentAdjustmentValidationException.
     * </p>
     * @throws Exception to JUnit
     */
    public void test_save_7() throws Exception {
    	ProjectPaymentAdjustment obj = new ProjectPaymentAdjustment();
    	obj.setFixedAmount(new BigDecimal(1000));
    	obj.setMultiplier(3.0);
    	obj.setProjectId(1L);
    	obj.setResourceRoleId(2L);
        try {
            instance.save(obj);
            fail("ProjectPaymentAdjustmentValidationException expected.");
        } catch (ProjectPaymentAdjustmentValidationException iae) {
            //good
        }
    }
    
    /**
     * <p>
     * Tests DatabaseProjectPaymentAdjustmentPersistence#retrieveByProjectId(long) for failure.
     * It tests the case that when persistence is bad and expects ProjectPaymentManagementPersistenceException.
     * </p>
     * @throws Exception to JUnit
     */
    public void test_retrieveByProjectId_1() throws Exception {
        try {
            instance.retrieveByProjectId(1);
            fail("ProjectPaymentManagementPersistenceException expected.");
        } catch (ProjectPaymentManagementPersistenceException iae) {
            //good
        }
    }
    /**
     * <p>
     * Tests DatabaseProjectPaymentAdjustmentPersistence#retrieveByProjectId(long) for failure.
     * It tests the case that when dbConnectionFactory is null and expects IllegalStateException.
     * </p>
     * @throws Exception to JUnit
     */
    public void test_retrieveByProjectId_2() throws Exception {
    	instance = new DatabaseProjectPaymentAdjustmentPersistence();
        try {
            instance.retrieveByProjectId(1);
            fail("IllegalStateException expected.");
        } catch (IllegalStateException iae) {
            //good
        }
    }
    /**
     * <p>
     * Tests DatabaseProjectPaymentAdjustmentPersistence#configure(ConfigurationObject) for failure.
     * It tests the case that when ConfigurationObject is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void test_configure_1() throws Exception {
    	ConfigurationObject obj = null;
        try {
            instance.configure(obj);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }
    
    /**
     * <p>
     * Tests DatabaseProjectPaymentAdjustmentPersistence#configure(ConfigurationObject) for failure.
     * It tests the case that when dbConnectionFactoryConfig is null and expects ProjectPaymentManagementConfigurationException.
     * </p>
     * @throws Exception to JUnit
     */
    public void test_configure_2() throws Exception {
        ConfigurationFileManager configFileManager = new ConfigurationFileManager("failure/ProjectPaymentAdjustmentManagerImpl.properties");
        ConfigurationObject configObject = configFileManager.getConfiguration("com.topcoder.management.payment.impl.ProjectPaymentAdjustmentManagerImpl");
        ConfigurationObject config = configObject.getChild("com.topcoder.management.payment.impl.ProjectPaymentAdjustmentManagerImpl");

        try {
            instance.configure(config);
            fail("ProjectPaymentManagementConfigurationException expected.");
        } catch (ProjectPaymentManagementConfigurationException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DatabaseProjectPaymentAdjustmentPersistence#configure(ConfigurationObject) for failure.
     * It tests the case that when dbConnectionFactoryConfig is incomplete and expects ProjectPaymentManagementConfigurationException.
     * </p>
     * @throws Exception to JUnit
     */
    public void test_configure_3() throws Exception {
        ConfigurationFileManager configFileManager = new ConfigurationFileManager("failure/ProjectPaymentAdjustmentManagerImpl.properties");
        ConfigurationObject configObject = configFileManager.getConfiguration("com.topcoder.management.payment.impl.ProjectPaymentAdjustmentManagerImpl");
        ConfigurationObject config = configObject.getChild("com.topcoder.management.payment.impl.ProjectPaymentAdjustmentManagerImpl").getChild("incompletepersistenceconfig");

        try {
            instance.configure(config);
            fail("ProjectPaymentManagementConfigurationException expected.");
        } catch (ProjectPaymentManagementConfigurationException iae) {
            //good
        }
    }


}