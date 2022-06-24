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
import com.topcoder.management.payment.impl.ProjectPaymentManagerImpl;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.UnknownNamespaceException;

/**
 * <p>
 * Failure test cases for ProjectPaymentManagerImpl.
 * </p>
 *
 * @author lqz
 * @version 1.0.1
 */
public class ProjectPaymentManagerImplFailureTests extends TestCase {

    private static final ProjectPaymentType paymenttype = new ProjectPaymentType();

    /**
     * <p>
     * The ProjectPaymentManagerImpl instance for testing.
     * </p>
     */
    private ProjectPaymentManagerImpl instance;

    /**
     * <p>
     * Setup test environment.
     * </p>
     * @throws Exception to JUnit
     *
     */
    protected void setUp() throws Exception {
    	ConfigManager config = ConfigManager.getInstance();
    	try {
    		config.removeNamespace("ProjectPaymentManagerImpl.SearchBuilderManager");
    	}catch(UnknownNamespaceException e) {
    	}
    	try {
    		config.removeNamespace("com.topcoder.db.connectionfactory.DBConnectionFactoryImpl");
    	}catch(UnknownNamespaceException e) {
    	}

    	try {
    		config.removeNamespace("com.topcoder.search.builder.validator.factory");
    	}catch(UnknownNamespaceException e) {
    	}

    	try {
    		config.removeNamespace("DBSearchStrategy");
    	}catch(UnknownNamespaceException e) {
    	}

    	try {
    		config.removeNamespace("com.topcoder.search.builder.strategy.factory");
    	}catch(UnknownNamespaceException e) {
    	}

    	try {
    		config.removeNamespace("com.topcoder.search.builder.database.factory");
    	}catch(UnknownNamespaceException e) {
    	}
		config.add("failure/SearchBundleManager.xml");
        instance = new ProjectPaymentManagerImpl("failure/ProjectPaymentManagerImpl.properties", 
        		"com.topcoder.management.payment.impl.ProjectPaymentManagerImpl");

        paymenttype.setProjectPaymentTypeId(1L);
        paymenttype.setName("review");
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     * @throws Exception to JUnit
     *
     */
    protected void tearDown() throws Exception {
    	ConfigManager config = ConfigManager.getInstance();
    	try {
    		config.removeNamespace("ProjectPaymentManagerImpl.SearchBuilderManager");
    	}catch(UnknownNamespaceException e) {
    	}
    	try {
    		config.removeNamespace("com.topcoder.db.connectionfactory.DBConnectionFactoryImpl");
    	}catch(UnknownNamespaceException e) {
    	}

    	try {
    		config.removeNamespace("com.topcoder.search.builder.validator.factory");
    	}catch(UnknownNamespaceException e) {
    	}

    	try {
    		config.removeNamespace("DBSearchStrategy");
    	}catch(UnknownNamespaceException e) {
    	}

    	try {
    		config.removeNamespace("com.topcoder.search.builder.strategy.factory");
    	}catch(UnknownNamespaceException e) {
    	}

    	try {
    		config.removeNamespace("com.topcoder.search.builder.database.factory");
    	}catch(UnknownNamespaceException e) {
    	}

    	
    }

    /**
     * <p>
     * Return all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(ProjectPaymentManagerImplFailureTests.class);
    }


    /**
     * <p>
     * Tests ProjectPaymentManagerImpl#ProjectPaymentManagerImpl() for failure.
     * It tests the case that when config is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void test_ProjectPaymentManagerImpl_1() throws Exception {
        try {
            new ProjectPaymentManagerImpl(null);
            fail("ProjectPaymentManagementConfigurationException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectPaymentManagerImpl#ProjectPaymentManagerImpl() for failure.
     * It tests the case that when objectFactoryConfig is invalid and expects ProjectPaymentManagementConfigurationException.
     * </p>
     * @throws Exception to JUnit
     */
    public void test_ProjectPaymentManagerImpl_2() throws Exception {
        ConfigurationFileManager configFileManager = new ConfigurationFileManager("failure/ProjectPaymentManagerImpl.properties");
        ConfigurationObject configObject = configFileManager.getConfiguration("com.topcoder.management.payment.impl.ProjectPaymentManagerImpl");
        ConfigurationObject config = configObject.getChild("com.topcoder.management.payment.impl.ProjectPaymentManagerImpl");
        config.removeChild("objectFactoryConfig");
        try {
            new ProjectPaymentManagerImpl(config);
            fail("ProjectPaymentManagementConfigurationException expected.");
        } catch (ProjectPaymentManagementConfigurationException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectPaymentManagerImpl#ProjectPaymentManagerImpl() for failure.
     * It tests the case that when projectPaymentAdjustmentPersistenceKey is null and expects ProjectPaymentManagementConfigurationException.
     * </p>
     * @throws Exception to JUnit
     */
    public void test_ProjectPaymentManagerImpl_3() throws Exception {
        ConfigurationFileManager configFileManager = new ConfigurationFileManager("failure/ProjectPaymentManagerImpl.properties");
        ConfigurationObject configObject = configFileManager.getConfiguration("com.topcoder.management.payment.impl.ProjectPaymentManagerImpl");
        ConfigurationObject config = configObject.getChild("com.topcoder.management.payment.impl.ProjectPaymentManagerImpl");
        config.removeProperty("projectPaymentPersistenceKey");
        try {
            new ProjectPaymentManagerImpl(config);
            fail("ProjectPaymentManagementConfigurationException expected.");
        } catch (ProjectPaymentManagementConfigurationException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectPaymentManagerImpl#ProjectPaymentManagerImpl() for failure.
     * It tests the case that when projectPaymentAdjustmentPersistenceKey is empty and expects ProjectPaymentManagementConfigurationException.
     * </p>
     * @throws Exception to JUnit
     */
    public void test_ProjectPaymentManagerImpl_5() throws Exception {
        ConfigurationFileManager configFileManager = new ConfigurationFileManager("failure/ProjectPaymentManagerImpl.properties");
        ConfigurationObject configObject = configFileManager.getConfiguration("com.topcoder.management.payment.impl.ProjectPaymentManagerImpl");
        ConfigurationObject config = configObject.getChild("com.topcoder.management.payment.impl.ProjectPaymentManagerImpl");
        config.setPropertyValue("projectPaymentPersistenceKey", " ");
        try {
            new ProjectPaymentManagerImpl(config);
            fail("ProjectPaymentManagementConfigurationException expected.");
        } catch (ProjectPaymentManagementConfigurationException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectPaymentManagerImpl#ProjectPaymentManagerImpl() for failure.
     * It tests the case that when projectPaymentPersistenceConfig is invalid and expects ProjectPaymentManagementConfigurationException.
     * </p>
     * @throws Exception to JUnit
     */
    public void test_ProjectPaymentManagerImpl_6() throws Exception {
        ConfigurationFileManager configFileManager = new ConfigurationFileManager("failure/ProjectPaymentManagerImpl.properties");
        ConfigurationObject configObject = configFileManager.getConfiguration("com.topcoder.management.payment.impl.ProjectPaymentManagerImpl");
        ConfigurationObject config = configObject.getChild("com.topcoder.management.payment.impl.ProjectPaymentManagerImpl");
        config.removeChild("projectPaymentPersistenceConfig");
        try {
            new ProjectPaymentManagerImpl(config);
            fail("ProjectPaymentManagementConfigurationException expected.");
        } catch (ProjectPaymentManagementConfigurationException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectPaymentManagerImpl#ProjectPaymentManagerImpl() for failure.
     * It tests the case that when searchBundleManagerNamespace is null and expects ProjectPaymentManagementConfigurationException.
     * </p>
     * @throws Exception to JUnit
     */
    public void test_ProjectPaymentManagerImpl_7() throws Exception {
        ConfigurationFileManager configFileManager = new ConfigurationFileManager("failure/ProjectPaymentManagerImpl.properties");
        ConfigurationObject configObject = configFileManager.getConfiguration("com.topcoder.management.payment.impl.ProjectPaymentManagerImpl");
        ConfigurationObject config = configObject.getChild("com.topcoder.management.payment.impl.ProjectPaymentManagerImpl");
        config.removeProperty("searchBundleManagerNamespace");
        try {
            new ProjectPaymentManagerImpl(config);
            fail("ProjectPaymentManagementConfigurationException expected.");
        } catch (ProjectPaymentManagementConfigurationException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectPaymentManagerImpl#ProjectPaymentManagerImpl() for failure.
     * It tests the case that when searchBundleManagerNamespace is empty and expects ProjectPaymentManagementConfigurationException.
     * </p>
     * @throws Exception to JUnit
     */
    public void test_ProjectPaymentManagerImpl_8() throws Exception {
        ConfigurationFileManager configFileManager = new ConfigurationFileManager("failure/ProjectPaymentManagerImpl.properties");
        ConfigurationObject configObject = configFileManager.getConfiguration("com.topcoder.management.payment.impl.ProjectPaymentManagerImpl");
        ConfigurationObject config = configObject.getChild("com.topcoder.management.payment.impl.ProjectPaymentManagerImpl");
        config.setPropertyValue("searchBundleManagerNamespace", " ");
        try {
            new ProjectPaymentManagerImpl(config);
            fail("ProjectPaymentManagementConfigurationException expected.");
        } catch (ProjectPaymentManagementConfigurationException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectPaymentManagerImpl#ProjectPaymentManagerImpl() for failure.
     * It tests the case that when projectPaymentSearchBundleName is empty and expects ProjectPaymentManagementConfigurationException.
     * </p>
     * @throws Exception to JUnit
     */
    public void test_ProjectPaymentManagerImpl_9() throws Exception {
        ConfigurationFileManager configFileManager = new ConfigurationFileManager("failure/ProjectPaymentManagerImpl.properties");
        ConfigurationObject configObject = configFileManager.getConfiguration("com.topcoder.management.payment.impl.ProjectPaymentManagerImpl");
        ConfigurationObject config = configObject.getChild("com.topcoder.management.payment.impl.ProjectPaymentManagerImpl");
        config.setPropertyValue("projectPaymentSearchBundleName", " ");
        try {
            new ProjectPaymentManagerImpl(config);
            fail("ProjectPaymentManagementConfigurationException expected.");
        } catch (ProjectPaymentManagementConfigurationException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectPaymentManagerImpl#ProjectPaymentManagerImpl() for failure.
     * It tests the case that when projectPaymentSearchBundleName is null and expects ProjectPaymentManagementConfigurationException.
     * </p>
     * @throws Exception to JUnit
     */
    public void test_ProjectPaymentManagerImpl_10() throws Exception {
        ConfigurationFileManager configFileManager = new ConfigurationFileManager("failure/ProjectPaymentManagerImpl.properties");
        ConfigurationObject configObject = configFileManager.getConfiguration("com.topcoder.management.payment.impl.ProjectPaymentManagerImpl");
        ConfigurationObject config = configObject.getChild("com.topcoder.management.payment.impl.ProjectPaymentManagerImpl");
        config.removeProperty("projectPaymentSearchBundleName");
        try {
            new ProjectPaymentManagerImpl(config);
            fail("ProjectPaymentManagementConfigurationException expected.");
        } catch (ProjectPaymentManagementConfigurationException iae) {
            //good
        }
    }
    /**
     * <p>
     * Tests ProjectPaymentManagerImpl#ProjectPaymentManagerImpl() for failure.
     * It tests the case that when file is not found and expects ProjectPaymentManagementConfigurationException.
     * </p>
     * @throws Exception to JUnit
     */
    public void test_ProjectPaymentManagerImpl_21() throws Exception {
        try {
            new ProjectPaymentManagerImpl("com/topcoder/management/payment/impl/notfound",
            		"com.topcoder.management.payment.impl.ProjectPaymentManagerImpl");
            fail("ProjectPaymentManagementConfigurationException expected.");
        } catch (ProjectPaymentManagementConfigurationException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectPaymentManagerImpl#ProjectPaymentManagerImpl() for failure.
     * It tests the case that when file is not supported and expects ProjectPaymentManagementConfigurationException.
     * </p>
     * @throws Exception to JUnit
     */
    public void test_ProjectPaymentManagerImpl_22() throws Exception {
        try {
            new ProjectPaymentManagerImpl("com/topcoder/management/payment/impl/ProjectPaymentManagerImpl.invalid",
            		"com.topcoder.management.payment.impl.ProjectPaymentManagerImpl");
            fail("ProjectPaymentManagementConfigurationException expected.");
        } catch (ProjectPaymentManagementConfigurationException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectPaymentManagerImpl#ProjectPaymentManagerImpl() for failure.
     * It tests the case that when config file is invalid and expects ProjectPaymentManagementConfigurationException.
     * </p>
     * @throws Exception to JUnit
     */
    public void test_ProjectPaymentManagerImpl_23() throws Exception {
        try {
            new ProjectPaymentManagerImpl("invalidconfig",
            		"com.topcoder.management.payment.impl.ProjectPaymentManagerImpl");
            fail("ProjectPaymentManagementConfigurationException expected.");
        } catch (ProjectPaymentManagementConfigurationException iae) {
            //good
        }
    }
    


    /**
     * <p>
     * Tests ProjectPaymentManagerImpl#ProjectPaymentManagerImpl() for failure.
     * It tests the case that when namespace is not found and expects ProjectPaymentManagementConfigurationException.
     * </p>
     * @throws Exception to JUnit
     */
    public void test_ProjectPaymentManagerImpl_25() throws Exception {
        try {
            new ProjectPaymentManagerImpl("com/topcoder/management/payment/impl/ProjectPaymentManagerImpl.properties",
            		"com.topcoder.management.payment.impl.notfound");
            fail("ProjectPaymentManagementConfigurationException expected.");
        } catch (ProjectPaymentManagementConfigurationException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectPaymentManagerImpl#ProjectPaymentManagerImpl() for failure.
     * It tests the case that when name space is invalid and expects ProjectPaymentManagementConfigurationException.
     * </p>
     * @throws Exception to JUnit
     */
    public void test_ProjectPaymentManagerImpl_26() throws Exception {
        try {
            new ProjectPaymentManagerImpl("com/topcoder/management/payment/impl/ProjectPaymentManagerImpl.properties",
            		"com.topcoder.db.connectionfactory.DBConnectionFactoryImpl");
            fail("ProjectPaymentManagementConfigurationException expected.");
        } catch (ProjectPaymentManagementConfigurationException iae) {
            //good
        }
    }
    
    /**
     * <p>
     * Tests ProjectPaymentManagerImpl#ProjectPaymentManagerImpl() for failure.
     * It tests the case that when file is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void test_ProjectPaymentManagerImpl_24() throws Exception {
        try {
            new ProjectPaymentManagerImpl(null,
            		"com.topcoder.management.payment.impl.ProjectPaymentManagerImpl");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectPaymentManagerImpl#ProjectPaymentManagerImpl() for failure.
     * It tests the case that when file is empty and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void test_ProjectPaymentManagerImpl_27() throws Exception {
        try {
            new ProjectPaymentManagerImpl(" ",
            		"com.topcoder.management.payment.impl.ProjectPaymentManagerImpl");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }
    /**
     * <p>
     * Tests ProjectPaymentManagerImpl#ProjectPaymentManagerImpl() for failure.
     * It tests the case that when namespace is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void test_ProjectPaymentManagerImpl_28() throws Exception {
        try {
            new ProjectPaymentManagerImpl("com/topcoder/management/payment/impl/ProjectPaymentManagerImpl.properties",
            		null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ProjectPaymentManagerImpl#ProjectPaymentManagerImpl() for failure.
     * It tests the case that when namespace is empty and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void test_ProjectPaymentManagerImpl_29() throws Exception {
        try {
            new ProjectPaymentManagerImpl("com/topcoder/management/payment/impl/ProjectPaymentManagerImpl.properties",
            		" ");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
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
     * Tests ProjectPaymentManagerImpl#search(Filter) for failure.
     * It tests the case that when persistence is invalid.
     * </p>
     * @throws Exception to JUnit
     */
    public void test_search_1() throws Exception {
        try {
            Filter filter = null;
			instance.search(filter );
            fail("ProjectPaymentManagementPersistenceException expected.");
        } catch (ProjectPaymentManagementPersistenceException iae) {
            //good
        }
    }


}