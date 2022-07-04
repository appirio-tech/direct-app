/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.payment.failuretests;

import java.math.BigDecimal;
import java.util.Date;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.persistence.ConfigurationFileManager;
import com.topcoder.management.payment.ProjectPayment;
import com.topcoder.management.payment.ProjectPaymentManagementConfigurationException;
import com.topcoder.management.payment.ProjectPaymentType;
import com.topcoder.management.payment.ProjectPaymentValidationException;
import com.topcoder.management.payment.impl.ProjectPaymentManagementPersistenceException;
import com.topcoder.management.payment.impl.persistence.DatabaseProjectPaymentPersistence;

/**
 * <p>
 * Failure test cases for DatabaseProjectPaymentPersistence.
 * </p>
 *
 * @author lqz
 * @version 1.0.1
 */
public class DatabaseProjectPaymentPersistenceFailureTests extends TestCase {

    private static final ProjectPaymentType paymenttype = new ProjectPaymentType();
	/**
     * <p>
     * The DatabaseProjectPaymentPersistence instance for testing.
     * </p>
     */
    private DatabaseProjectPaymentPersistence instance;

    /**
     * <p>
     * Setup test environment.
     * </p>
     * @throws Exception to JUnit
     *
     */
    protected void setUp() throws Exception {
        instance = new DatabaseProjectPaymentPersistence();
        paymenttype.setProjectPaymentTypeId(1L);
        paymenttype.setName("review");
        ConfigurationFileManager configFileManager = new ConfigurationFileManager("failure/ProjectPaymentManagerImpl.properties");
        ConfigurationObject configObject = configFileManager.getConfiguration("com.topcoder.management.payment.impl.ProjectPaymentManagerImpl");
        ConfigurationObject config = configObject.getChild("com.topcoder.management.payment.impl.ProjectPaymentManagerImpl").getChild("projectPaymentPersistenceConfig");
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
        return new TestSuite(DatabaseProjectPaymentPersistenceFailureTests.class);
    }


    /**
     * <p>
     * Tests DatabaseProjectPaymentPersistence#create(ProjectPayment) for failure.
     * It tests the case that when ProjectPayment is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void test_create_1() throws Exception {
        try {
            instance.create(null, "testOperator");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }
    /**
     * <p>
     * Tests DatabaseProjectPaymentPersistence#create(ProjectPayment) for failure.
     * It tests the case that when amount is null and expects ProjectPaymentValidationException.
     * </p>
     * @throws Exception to JUnit
     */
    public void test_create_2() throws Exception {
    	ProjectPayment obj = new ProjectPayment();
    	obj.setAmount(null);
    	obj.setCreateDate(new Date());
    	obj.setPactsPaymentId(1L);
    	
    	obj.setProjectPaymentType(paymenttype);
    	obj.setResourceId(1L);
    	obj.setSubmissionId(2L);
        try {
            instance.create(obj, "testOperator");
            fail("ProjectPaymentValidationException expected.");
        } catch (ProjectPaymentValidationException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DatabaseProjectPaymentPersistence#create(ProjectPayment) for failure.
     * It tests the case that when amount is negative and expects ProjectPaymentValidationException.
     * </p>
     * @throws Exception to JUnit
     */
    public void test_create_3() throws Exception {
    	ProjectPayment obj = new ProjectPayment();
    	obj.setAmount(new BigDecimal(-1));
    	obj.setCreateDate(new Date());
    	obj.setPactsPaymentId(1L);
    	
    	obj.setProjectPaymentType(paymenttype);
    	obj.setResourceId(1L);
    	obj.setSubmissionId(2L);
        try {
            instance.create(obj, "testOperator");
            fail("ProjectPaymentValidationException expected.");
        } catch (ProjectPaymentValidationException iae) {
            //good
        }
    }
    


    /**
     * <p>
     * Tests DatabaseProjectPaymentPersistence#create(ProjectPayment) for failure.
     * It tests the case that when project payment id is not null and expects ProjectPaymentValidationException.
     * </p>
     * @throws Exception to JUnit
     */
    public void test_create_5() throws Exception {
    	ProjectPayment obj = new ProjectPayment();
    	obj.setAmount(new BigDecimal(1));
    	obj.setCreateDate(new Date());
    	obj.setPactsPaymentId(1L);
    	obj.setProjectPaymentId(1L);
    	obj.setProjectPaymentType(paymenttype);
    	obj.setResourceId(1L);
    	obj.setSubmissionId(2L);
        try {
            instance.create(obj, "testOperator");
            fail("ProjectPaymentValidationException expected.");
        } catch (ProjectPaymentValidationException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DatabaseProjectPaymentPersistence#create(ProjectPayment) for failure.
     * It tests the case that when payment type id is null and expects ProjectPaymentValidationException.
     * </p>
     * @throws Exception to JUnit
     */
    public void test_create_0() throws Exception {
    	ProjectPayment obj = new ProjectPayment();
    	obj.setAmount(new BigDecimal(1));
    	obj.setCreateDate(new Date());
    	obj.setPactsPaymentId(1L);
    	obj.setProjectPaymentType(new ProjectPaymentType());
    	obj.setResourceId(1L);
    	obj.setSubmissionId(2L);
        try {
            instance.create(obj, "testOperator");
            fail("ProjectPaymentValidationException expected.");
        } catch (ProjectPaymentValidationException iae) {
            //good
        }
    }
    /**
     * <p>
     * Tests DatabaseProjectPaymentPersistence#create(ProjectPayment) for failure.
     * It tests the case that when payment type is null and expects ProjectPaymentValidationException.
     * </p>
     * @throws Exception to JUnit
     */
    public void test_create_7() throws Exception {
    	ProjectPayment obj = new ProjectPayment();
    	obj.setAmount(new BigDecimal(1));
    	obj.setCreateDate(new Date());
    	obj.setPactsPaymentId(1L);
    	obj.setResourceId(1L);
    	obj.setSubmissionId(2L);
        try {
            instance.create(obj, "testOperator");
            fail("ProjectPaymentValidationException expected.");
        } catch (ProjectPaymentValidationException iae) {
            //good
        }
    }
    

    /**
     * <p>
     * Tests DatabaseProjectPaymentPersistence#create(ProjectPayment) for failure.
     * It tests the case that when resource id is null and expects ProjectPaymentValidationException.
     * </p>
     * @throws Exception to JUnit
     */
    public void test_create_8() throws Exception {
    	ProjectPayment obj = new ProjectPayment();
    	obj.setAmount(new BigDecimal(1));
    	obj.setCreateDate(new Date());
    	obj.setPactsPaymentId(1L);
    	
    	obj.setProjectPaymentType(paymenttype);
    	obj.setSubmissionId(2L);
        try {
            instance.create(obj, "testOperator");
            fail("ProjectPaymentValidationException expected.");
        } catch (ProjectPaymentValidationException iae) {
            //good
        }
    }
    

    /**
     * <p>
     * Tests DatabaseProjectPaymentPersistence#create(ProjectPayment) for failure.
     * It tests the case that when persistence error occurs and expects ProjectPaymentManagementPersistenceException.
     * </p>
     * @throws Exception to JUnit
     */
    public void test_create_10() throws Exception {
    	ProjectPayment obj = new ProjectPayment();
    	obj.setAmount(new BigDecimal(1));
    	obj.setCreateDate(new Date());
    	obj.setPactsPaymentId(1L);
    	
    	obj.setProjectPaymentType(paymenttype);
    	obj.setResourceId(1L);
    	obj.setSubmissionId(2L);
        try {
            instance.create(obj, "testOperator");
            fail("ProjectPaymentManagementPersistenceException expected.");
        } catch (ProjectPaymentManagementPersistenceException iae) {
            //good
        }
    }
    

    /**
     * <p>
     * Tests DatabaseProjectPaymentPersistence#create(ProjectPayment) for failure.
     * It tests the case that when dbConnectionFactory is null and expects IllegalStateException.
     * </p>
     * @throws Exception to JUnit
     */
    public void test_create_11() throws Exception {
    	instance = new DatabaseProjectPaymentPersistence();
    	ProjectPayment obj = new ProjectPayment();
    	obj.setAmount(new BigDecimal(1));
    	obj.setCreateDate(new Date());
    	obj.setPactsPaymentId(1L);
    	
    	obj.setProjectPaymentType(paymenttype);
    	obj.setResourceId(1L);
    	obj.setSubmissionId(2L);
        try {
            instance.create(obj, "testOperator");
            fail("IllegalStateException expected.");
        } catch (IllegalStateException iae) {
            //good
        }
    }
    
    
    
    
    /**
     * <p>
     * Tests DatabaseProjectPaymentPersistence#update(ProjectPayment) for failure.
     * It tests the case that when ProjectPayment is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void test_update_1() throws Exception {
        try {
            instance.update(null, "testOperator");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }
    /**
     * <p>
     * Tests DatabaseProjectPaymentPersistence#update(ProjectPayment) for failure.
     * It tests the case that when amount is null and expects ProjectPaymentValidationException.
     * </p>
     * @throws Exception to JUnit
     */
    public void test_update_2() throws Exception {
    	ProjectPayment obj = new ProjectPayment();
    	obj.setCreateDate(new Date());
    	obj.setPactsPaymentId(1L);
    	obj.setProjectPaymentId(2L);
    	obj.setProjectPaymentType(paymenttype);
    	obj.setResourceId(1L);
    	obj.setSubmissionId(2L);
        try {
            instance.update(obj, "testOperator");
            fail("ProjectPaymentValidationException expected.");
        } catch (ProjectPaymentValidationException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DatabaseProjectPaymentPersistence#update(ProjectPayment) for failure.
     * It tests the case that when amount is negative and expects ProjectPaymentValidationException.
     * </p>
     * @throws Exception to JUnit
     */
    public void test_update_3() throws Exception {
    	ProjectPayment obj = new ProjectPayment();
    	obj.setAmount(new BigDecimal(-1));
    	obj.setCreateDate(new Date());
    	obj.setPactsPaymentId(1L);
    	obj.setProjectPaymentId(2L);
    	obj.setProjectPaymentType(paymenttype);
    	obj.setResourceId(1L);
    	obj.setSubmissionId(2L);
        try {
            instance.update(obj, "testOperator");
            fail("ProjectPaymentValidationException expected.");
        } catch (ProjectPaymentValidationException iae) {
            //good
        }
    }
    

    /**
     * <p>
     * Tests DatabaseProjectPaymentPersistence#update(ProjectPayment) for failure.
     * It tests the case that when project payment id is null and expects ProjectPaymentValidationException.
     * </p>
     * @throws Exception to JUnit
     */
    public void test_update_5() throws Exception {
    	ProjectPayment obj = new ProjectPayment();
    	obj.setAmount(new BigDecimal(1));
    	obj.setCreateDate(new Date());
    	obj.setPactsPaymentId(1L);
    	obj.setProjectPaymentType(paymenttype);
    	obj.setResourceId(1L);
    	obj.setSubmissionId(2L);
        try {
            instance.update(obj, "testOperator");
            fail("ProjectPaymentValidationException expected.");
        } catch (ProjectPaymentValidationException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DatabaseProjectPaymentPersistence#update(ProjectPayment) for failure.
     * It tests the case that when payment type is null and expects ProjectPaymentValidationException.
     * </p>
     * @throws Exception to JUnit
     */
    public void test_update_7() throws Exception {
    	ProjectPayment obj = new ProjectPayment();
    	obj.setAmount(new BigDecimal(1));
    	obj.setCreateDate(new Date());
    	obj.setPactsPaymentId(1L);
    	obj.setProjectPaymentId(2L);
    	obj.setResourceId(1L);
    	obj.setSubmissionId(2L);
        try {
            instance.update(obj, "testOperator");
            fail("ProjectPaymentValidationException expected.");
        } catch (ProjectPaymentValidationException iae) {
            //good
        }
    }
    

    /**
     * <p>
     * Tests DatabaseProjectPaymentPersistence#update(ProjectPayment) for failure.
     * It tests the case that when resource id is null and expects ProjectPaymentValidationException.
     * </p>
     * @throws Exception to JUnit
     */
    public void test_update_8() throws Exception {
    	ProjectPayment obj = new ProjectPayment();
    	obj.setAmount(new BigDecimal(1));
    	obj.setCreateDate(new Date());
    	obj.setPactsPaymentId(1L);
    	obj.setProjectPaymentId(2L);
    	obj.setProjectPaymentType(paymenttype);
    	obj.setSubmissionId(2L);
        try {
            instance.update(obj, "testOperator");
            fail("ProjectPaymentValidationException expected.");
        } catch (ProjectPaymentValidationException iae) {
            //good
        }
    }
    

    /**
     * <p>
     * Tests DatabaseProjectPaymentPersistence#update(ProjectPayment) for failure.
     * It tests the case that when persistence error occurs and expects ProjectPaymentManagementPersistenceException.
     * </p>
     * @throws Exception to JUnit
     */
    public void test_update_10() throws Exception {
    	ProjectPayment obj = new ProjectPayment();
    	obj.setAmount(new BigDecimal(1));
    	obj.setCreateDate(new Date());
    	obj.setPactsPaymentId(1L);
    	obj.setProjectPaymentId(2L);
    	obj.setProjectPaymentType(paymenttype);
    	obj.setResourceId(1L);
    	obj.setSubmissionId(2L);
        try {
            instance.update(obj, "testOperator");
            fail("ProjectPaymentManagementPersistenceException expected.");
        } catch (ProjectPaymentManagementPersistenceException iae) {
            //good
        }
    }
    

    /**
     * <p>
     * Tests DatabaseProjectPaymentPersistence#update(ProjectPayment) for failure.
     * It tests the case that when dbConnectionFactory is null and expects IllegalStateException.
     * </p>
     * @throws Exception to JUnit
     */
    public void test_update_11() throws Exception {
    	instance = new DatabaseProjectPaymentPersistence();
    	ProjectPayment obj = new ProjectPayment();
    	obj.setAmount(new BigDecimal(1));
    	obj.setCreateDate(new Date());
    	obj.setPactsPaymentId(1L);
    	obj.setProjectPaymentId(2L);
    	obj.setProjectPaymentType(paymenttype);
    	obj.setResourceId(1L);
    	obj.setSubmissionId(2L);
        try {
            instance.update(obj, "testOperator");
            fail("IllegalStateException expected.");
        } catch (IllegalStateException iae) {
            //good
        }
    }
    
    


    /**
     * <p>
     * Tests DatabaseProjectPaymentPersistence#update(ProjectPayment) for failure.
     * It tests the case that when date is not null and expects ProjectPaymentValidationException.
     * </p>
     * @throws Exception to JUnit
     */
    public void test_update_6() throws Exception {
    	ProjectPayment obj = new ProjectPayment();
    	obj.setAmount(new BigDecimal(1));
    	obj.setPactsPaymentId(1L);
    	obj.setProjectPaymentType(paymenttype);
    	obj.setResourceId(1L);
    	obj.setSubmissionId(2L);
        try {
            instance.update(obj, "testOperator");
            fail("ProjectPaymentValidationException expected.");
        } catch (ProjectPaymentValidationException iae) {
            //good
        }
    }
    /**
     * <p>
     * Tests DatabaseProjectPaymentPersistence#update(ProjectPayment) for failure.
     * It tests the case that when payment type id is null and expects ProjectPaymentValidationException.
     * </p>
     * @throws Exception to JUnit
     */
    public void test_update_0() throws Exception {
    	ProjectPayment obj = new ProjectPayment();
    	obj.setAmount(new BigDecimal(1));
    	obj.setCreateDate(new Date());
    	obj.setPactsPaymentId(1L);
    	obj.setProjectPaymentType(new ProjectPaymentType());
    	obj.setResourceId(1L);
    	obj.setSubmissionId(2L);
        try {
            instance.update(obj, "testOperator");
            fail("ProjectPaymentValidationException expected.");
        } catch (ProjectPaymentValidationException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests DatabaseProjectPaymentAdjustmentPersistence#retrieve(long) for failure.
     * It tests the case that when persistence is bad and expects ProjectPaymentManagementPersistenceException.
     * </p>
     * @throws Exception to JUnit
     */
    public void test_retrieve_1() throws Exception {
        try {
            instance.retrieve(1);
            fail("ProjectPaymentManagementPersistenceException expected.");
        } catch (ProjectPaymentManagementPersistenceException iae) {
            //good
        }
    }
    /**
     * <p>
     * Tests DatabaseProjectPaymentAdjustmentPersistence#retrieve(long) for failure.
     * It tests the case that when dbConnectionFactory is null and expects IllegalStateException.
     * </p>
     * @throws Exception to JUnit
     */
    public void test_retrieve_2() throws Exception {
    	instance = new DatabaseProjectPaymentPersistence();
        try {
            instance.retrieve(1);
            fail("IllegalStateException expected.");
        } catch (IllegalStateException iae) {
            //good
        }
    }
    
    

    /**
     * <p>
     * Tests DatabaseProjectPaymentAdjustmentPersistence#delete(long) for failure.
     * It tests the case that when persistence is bad and expects ProjectPaymentManagementPersistenceException.
     * </p>
     * @throws Exception to JUnit
     */
    public void test_delete_1() throws Exception {
        try {
            instance.delete(1);
            fail("ProjectPaymentManagementPersistenceException expected.");
        } catch (ProjectPaymentManagementPersistenceException iae) {
            //good
        }
    }
    /**
     * <p>
     * Tests DatabaseProjectPaymentAdjustmentPersistence#delete(long) for failure.
     * It tests the case that when dbConnectionFactory is null and expects IllegalStateException.
     * </p>
     * @throws Exception to JUnit
     */
    public void test_delete_2() throws Exception {
    	instance = new DatabaseProjectPaymentPersistence();
        try {
            instance.delete(1);
            fail("IllegalStateException expected.");
        } catch (IllegalStateException iae) {
            //good
        }
    }
    /**
     * <p>
     * Tests DatabaseProjectPaymentPersistence#configure(ConfigurationObject) for failure.
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
     * Tests DatabaseProjectPaymentPersistence#configure(ConfigurationObject) for failure.
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
     * Tests DatabaseProjectPaymentPersistence#configure(ConfigurationObject) for failure.
     * It tests the case that when dbConnectionFactoryConfig is incomplete and expects ProjectPaymentManagementConfigurationException.
     * </p>
     * @throws Exception to JUnit
     */
    public void test_configure_3() throws Exception {
        ConfigurationFileManager configFileManager = new ConfigurationFileManager("failure/ProjectPaymentManagerImpl.properties");
        ConfigurationObject configObject = configFileManager.getConfiguration("com.topcoder.management.payment.impl.ProjectPaymentManagerImpl");
        ConfigurationObject config = configObject.getChild("com.topcoder.management.payment.impl.ProjectPaymentManagerImpl").getChild("incompletePersistenceConfig");

        try {
            instance.configure(config);
            fail("ProjectPaymentManagementConfigurationException expected.");
        } catch (ProjectPaymentManagementConfigurationException iae) {
            //good
        }
    }


}