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
import com.topcoder.management.payment.impl.ProjectPaymentAdjustmentManagerImpl;
import com.topcoder.management.payment.impl.ProjectPaymentManagementPersistenceException;

/**
 * <p>
 * Failure test cases for ProjectPaymentAdjustmentManagerImpl.
 * </p>
 *
 * @author lqz
 * @version 1.0
 */
public class ProjectPaymentAdjustmentManagerImplFailureTests extends TestCase {

    /**
     * <p>
     * The ProjectPaymentAdjustmentManagerImpl instance for testing.
     * </p>
     */
    private ProjectPaymentAdjustmentManagerImpl instance;

    /**
     * <p>
     * Setup test environment.
     * </p>
     * @throws Exception to JUnit
     *
     */
    protected void setUp() throws Exception {
        instance = new ProjectPaymentAdjustmentManagerImpl("failure/ProjectPaymentAdjustmentManagerImpl.properties", 
        		"com.topcoder.management.payment.impl.ProjectPaymentAdjustmentManagerImpl");
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
        return new TestSuite(ProjectPaymentAdjustmentManagerImplFailureTests.class);
    }

    /**
     * <p>
     * Tests ProjectPaymentAdjustmentManagerImpl#ProjectPaymentAdjustmentManagerImpl() for failure.
     * It tests the case that when config is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void test_ProjectPaymentAdjustmentManagerImpl_1() throws Exception {
        try {
            new ProjectPaymentAdjustmentManagerImpl(null);
            fail("ProjectPaymentManagementConfigurationException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectPaymentAdjustmentManagerImpl#ProjectPaymentAdjustmentManagerImpl() for failure.
     * It tests the case that when objectFactoryConfig is invalid and expects ProjectPaymentManagementConfigurationException.
     * </p>
     * @throws Exception to JUnit
     */
    public void test_ProjectPaymentAdjustmentManagerImpl_2() throws Exception {
        ConfigurationFileManager configFileManager = new ConfigurationFileManager("failure/ProjectPaymentAdjustmentManagerImpl.properties");
        ConfigurationObject configObject = configFileManager.getConfiguration("com.topcoder.management.payment.impl.ProjectPaymentAdjustmentManagerImpl");
        ConfigurationObject config = configObject.getChild("com.topcoder.management.payment.impl.ProjectPaymentAdjustmentManagerImpl");
        config.removeChild("objectFactoryConfig");
        try {
            new ProjectPaymentAdjustmentManagerImpl(config);
            fail("ProjectPaymentManagementConfigurationException expected.");
        } catch (ProjectPaymentManagementConfigurationException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectPaymentAdjustmentManagerImpl#ProjectPaymentAdjustmentManagerImpl() for failure.
     * It tests the case that when projectPaymentAdjustmentPersistenceKey is null and expects ProjectPaymentManagementConfigurationException.
     * </p>
     * @throws Exception to JUnit
     */
    public void test_ProjectPaymentAdjustmentManagerImpl_3() throws Exception {
        ConfigurationFileManager configFileManager = new ConfigurationFileManager("failure/ProjectPaymentAdjustmentManagerImpl.properties");
        ConfigurationObject configObject = configFileManager.getConfiguration("com.topcoder.management.payment.impl.ProjectPaymentAdjustmentManagerImpl");
        ConfigurationObject config = configObject.getChild("com.topcoder.management.payment.impl.ProjectPaymentAdjustmentManagerImpl");
        config.removeProperty("projectPaymentAdjustmentPersistenceKey");
        try {
            new ProjectPaymentAdjustmentManagerImpl(config);
            fail("ProjectPaymentManagementConfigurationException expected.");
        } catch (ProjectPaymentManagementConfigurationException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectPaymentAdjustmentManagerImpl#ProjectPaymentAdjustmentManagerImpl() for failure.
     * It tests the case that when projectPaymentAdjustmentPersistenceKey is empty and expects ProjectPaymentManagementConfigurationException.
     * </p>
     * @throws Exception to JUnit
     */
    public void test_ProjectPaymentAdjustmentManagerImpl_5() throws Exception {
        ConfigurationFileManager configFileManager = new ConfigurationFileManager("failure/ProjectPaymentAdjustmentManagerImpl.properties");
        ConfigurationObject configObject = configFileManager.getConfiguration("com.topcoder.management.payment.impl.ProjectPaymentAdjustmentManagerImpl");
        ConfigurationObject config = configObject.getChild("com.topcoder.management.payment.impl.ProjectPaymentAdjustmentManagerImpl");
        config.setPropertyValue("projectPaymentAdjustmentPersistenceKey", " ");
        try {
            new ProjectPaymentAdjustmentManagerImpl(config);
            fail("ProjectPaymentManagementConfigurationException expected.");
        } catch (ProjectPaymentManagementConfigurationException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectPaymentAdjustmentManagerImpl#ProjectPaymentAdjustmentManagerImpl() for failure.
     * It tests the case that when projectPaymentAdjustmentPersistenceConfig is invalid and expects ProjectPaymentManagementConfigurationException.
     * </p>
     * @throws Exception to JUnit
     */
    public void test_ProjectPaymentAdjustmentManagerImpl_6() throws Exception {
        ConfigurationFileManager configFileManager = new ConfigurationFileManager("failure/ProjectPaymentAdjustmentManagerImpl.properties");
        ConfigurationObject configObject = configFileManager.getConfiguration("com.topcoder.management.payment.impl.ProjectPaymentAdjustmentManagerImpl");
        ConfigurationObject config = configObject.getChild("com.topcoder.management.payment.impl.ProjectPaymentAdjustmentManagerImpl");
        config.removeChild("projectPaymentAdjustmentPersistenceConfig");
        try {
            new ProjectPaymentAdjustmentManagerImpl(config);
            fail("ProjectPaymentManagementConfigurationException expected.");
        } catch (ProjectPaymentManagementConfigurationException iae) {
            //good
        }
    }
    

    /**
     * <p>
     * Tests ProjectPaymentAdjustmentManagerImpl#ProjectPaymentAdjustmentManagerImpl() for failure.
     * It tests the case that when file is not found and expects ProjectPaymentManagementConfigurationException.
     * </p>
     * @throws Exception to JUnit
     */
    public void test_ProjectPaymentAdjustmentManagerImpl_21() throws Exception {
        try {
            new ProjectPaymentAdjustmentManagerImpl("com/topcoder/management/payment/impl/notfound",
            		"com.topcoder.management.payment.impl.ProjectPaymentAdjustmentManagerImpl");
            fail("ProjectPaymentManagementConfigurationException expected.");
        } catch (ProjectPaymentManagementConfigurationException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectPaymentAdjustmentManagerImpl#ProjectPaymentAdjustmentManagerImpl() for failure.
     * It tests the case that when file is not supported and expects ProjectPaymentManagementConfigurationException.
     * </p>
     * @throws Exception to JUnit
     */
    public void test_ProjectPaymentAdjustmentManagerImpl_22() throws Exception {
        try {
            new ProjectPaymentAdjustmentManagerImpl("com/topcoder/management/payment/impl/ProjectPaymentAdjustmentManagerImpl.invalid",
            		"com.topcoder.management.payment.impl.ProjectPaymentAdjustmentManagerImpl");
            fail("ProjectPaymentManagementConfigurationException expected.");
        } catch (ProjectPaymentManagementConfigurationException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectPaymentAdjustmentManagerImpl#ProjectPaymentAdjustmentManagerImpl() for failure.
     * It tests the case that when config file is invalid and expects ProjectPaymentManagementConfigurationException.
     * </p>
     * @throws Exception to JUnit
     */
    public void test_ProjectPaymentAdjustmentManagerImpl_23() throws Exception {
        try {
            new ProjectPaymentAdjustmentManagerImpl("invalidconfig",
            		"com.topcoder.management.payment.impl.ProjectPaymentAdjustmentManagerImpl");
            fail("ProjectPaymentManagementConfigurationException expected.");
        } catch (ProjectPaymentManagementConfigurationException iae) {
            //good
        }
    }
    


    /**
     * <p>
     * Tests ProjectPaymentAdjustmentManagerImpl#ProjectPaymentAdjustmentManagerImpl() for failure.
     * It tests the case that when namespace is not found and expects ProjectPaymentManagementConfigurationException.
     * </p>
     * @throws Exception to JUnit
     */
    public void test_ProjectPaymentAdjustmentManagerImpl_25() throws Exception {
        try {
            new ProjectPaymentAdjustmentManagerImpl("com/topcoder/management/payment/impl/ProjectPaymentAdjustmentManagerImpl.properties",
            		"com.topcoder.management.payment.impl.notfound");
            fail("ProjectPaymentManagementConfigurationException expected.");
        } catch (ProjectPaymentManagementConfigurationException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectPaymentAdjustmentManagerImpl#ProjectPaymentAdjustmentManagerImpl() for failure.
     * It tests the case that when name space is invalid and expects ProjectPaymentManagementConfigurationException.
     * </p>
     * @throws Exception to JUnit
     */
    public void test_ProjectPaymentAdjustmentManagerImpl_26() throws Exception {
        try {
            new ProjectPaymentAdjustmentManagerImpl("com/topcoder/management/payment/impl/ProjectPaymentAdjustmentManagerImpl.properties",
            		"com.topcoder.management.payment.impl.ProjectPaymentManagerImpl");
            fail("ProjectPaymentManagementConfigurationException expected.");
        } catch (ProjectPaymentManagementConfigurationException iae) {
            //good
        }
    }
    
    /**
     * <p>
     * Tests ProjectPaymentAdjustmentManagerImpl#ProjectPaymentAdjustmentManagerImpl() for failure.
     * It tests the case that when file is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void test_ProjectPaymentAdjustmentManagerImpl_24() throws Exception {
        try {
            new ProjectPaymentAdjustmentManagerImpl(null,
            		"com.topcoder.management.payment.impl.ProjectPaymentAdjustmentManagerImpl");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectPaymentAdjustmentManagerImpl#ProjectPaymentAdjustmentManagerImpl() for failure.
     * It tests the case that when file is empty and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void test_ProjectPaymentAdjustmentManagerImpl_27() throws Exception {
        try {
            new ProjectPaymentAdjustmentManagerImpl(" ",
            		"com.topcoder.management.payment.impl.ProjectPaymentAdjustmentManagerImpl");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }
    /**
     * <p>
     * Tests ProjectPaymentAdjustmentManagerImpl#ProjectPaymentAdjustmentManagerImpl() for failure.
     * It tests the case that when namespace is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void test_ProjectPaymentAdjustmentManagerImpl_28() throws Exception {
        try {
            new ProjectPaymentAdjustmentManagerImpl("com/topcoder/management/payment/impl/ProjectPaymentAdjustmentManagerImpl.properties",
            		null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectPaymentAdjustmentManagerImpl#ProjectPaymentAdjustmentManagerImpl() for failure.
     * It tests the case that when namespace is empty and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void test_ProjectPaymentAdjustmentManagerImpl_29() throws Exception {
        try {
            new ProjectPaymentAdjustmentManagerImpl("com/topcoder/management/payment/impl/ProjectPaymentAdjustmentManagerImpl.properties",
            		" ");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
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
     * It tests the case that when dbConnectionFactory is invalid and expects ProjectPaymentManagementPersistenceException.
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
     * Tests ProjectPaymentAdjustmentManagerImpl#ProjectPaymentManagerImpl() for failure.
     * It tests the case that when simulation is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void test_configure_1() throws Exception {
        try {
            new ProjectPaymentAdjustmentManagerImpl("", "");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }


    /**
     * <p>
     * Tests ProjectPaymentAdjustmentManagerImpl#ProjectPaymentManagerImpl() for failure.
     * It tests the case that when simulation is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void test_configure_21() throws Exception {
        try {
            new ProjectPaymentAdjustmentManagerImpl(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }
}