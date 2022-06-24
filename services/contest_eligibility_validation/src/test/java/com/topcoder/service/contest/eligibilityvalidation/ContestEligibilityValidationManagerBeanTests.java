/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.contest.eligibilityvalidation;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.service.contest.eligibility.ContestEligibility;
import com.topcoder.service.contest.eligibility.GroupContestEligibility;

/**
 * <p>
 * UnitTest cases of the <code>ContestEligibilityValidationManagerBean</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ContestEligibilityValidationManagerBeanTests extends TestCase {

    /**
     * <p>
     * Represents the <code>ContestEligibilityValidationManagerBean</code> instance used for test.
     * </p>
     */
    private ContestEligibilityValidationManagerBean bean;

    /**
     * <p>
     * Creates a test suite for the tests.
     * </p>
     *
     * @return a TestSuite for this test case.
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(ContestEligibilityValidationManagerBeanTests.class);
        return suite;
    }

    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     * @throws Exception
     *             to jUnit.
     */
    protected void setUp() throws Exception {
        bean = new ContestEligibilityValidationManagerBean();
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     *
     * @throws Exception
     *             to jUnit.
     */
    protected void tearDown() throws Exception {
        bean = null;
    }

    /**
     * <p>
     * Accuracy test case for constructor ContestEligibilityValidationManagerBean().It verifies the new
     * instance is created.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConstructorAccuracy() throws Exception {
        assertNotNull("Unable to instantiate Entity", bean);
    }

    /**
     * <p>
     * Accuracy test case for Initialize.It verifies that the logger will be initialized successfully without
     * error.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @SuppressWarnings("unchecked")
    public void testInitializeAccuracy() throws Exception {
        assertTrue("validators map size should be 0.",
            ((Map) getPrivateField(bean, "validators")).size() == 0);
        bean.initialize();
        assertTrue("validators map size should be 1.",
            ((Map) getPrivateField(bean, "validators")).size() == 1);
    }

    /**
     * <p>
     * Failure test case for Initialize.IAE is expected because the injected namespace is empty.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testInitializeFailure1() throws Exception {
        setPrivateField(ContestEligibilityValidationManagerBean.class, bean, "namespace", " ");
        try {
            bean.initialize();
            fail("ContestEligibilityValidationManagerConfigurationException should be thrown.");
        } catch (ContestEligibilityValidationManagerConfigurationException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for Initialize.IAE is expected because the injected configFileName is empty.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testInitializeFailure2() throws Exception {
        setPrivateField(ContestEligibilityValidationManagerBean.class, bean, "configFileName", " ");
        try {
            bean.initialize();
            fail("ContestEligibilityValidationManagerConfigurationException should be thrown.");
        } catch (ContestEligibilityValidationManagerConfigurationException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for Initialize.ContestEligibilityValidationManagerConfigurationException is expected
     * because the there is no 'validators' element.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testInitializeFailure3() throws Exception {
        setPrivateField(ContestEligibilityValidationManagerBean.class, bean, "configFileName", "bad.xml");
        setPrivateField(ContestEligibilityValidationManagerBean.class, bean, "namespace",
            "missRequiredElements");
        try {
            bean.initialize();
            fail("ContestEligibilityValidationManagerConfigurationException should be thrown.");
        } catch (ContestEligibilityValidationManagerConfigurationException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for Initialize.ContestEligibilityValidationManagerConfigurationException is expected
     * because the there is no 'no.xml' under class path.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testInitializeFailure4() throws Exception {
        setPrivateField(ContestEligibilityValidationManagerBean.class, bean, "configFileName", "no.xml");
        try {
            bean.initialize();
            fail("ContestEligibilityValidationManagerConfigurationException should be thrown.");
        } catch (ContestEligibilityValidationManagerConfigurationException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for Initialize.ContestEligibilityValidationManagerConfigurationException is expected
     * because of the bad object factory specification element.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testInitializeFailure5() throws Exception {
        setPrivateField(ContestEligibilityValidationManagerBean.class, bean, "configFileName", "bad.xml");
        setPrivateField(ContestEligibilityValidationManagerBean.class, bean, "namespace",
            "badOFConfiguration");
        try {
            bean.initialize();
            fail("ContestEligibilityValidationManagerConfigurationException should be thrown.");
        } catch (ContestEligibilityValidationManagerConfigurationException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for Initialize.ContestEligibilityValidationManagerConfigurationException is expected
     * because of the type of validator is wrong.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testInitializeFailure6() throws Exception {
        setPrivateField(ContestEligibilityValidationManagerBean.class, bean, "configFileName", "bad.xml");
        setPrivateField(ContestEligibilityValidationManagerBean.class, bean, "namespace", "classCastError");
        try {
            bean.initialize();
            fail("ContestEligibilityValidationManagerConfigurationException should be thrown.");
        } catch (ContestEligibilityValidationManagerConfigurationException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for Initialize.ContestEligibilityValidationManagerConfigurationException is expected
     * because objectFactory#createObject throw the InvalidClassSpecificationException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testInitializeFailure7() throws Exception {
        setPrivateField(ContestEligibilityValidationManagerBean.class, bean, "configFileName", "bad.xml");
        setPrivateField(ContestEligibilityValidationManagerBean.class, bean, "namespace",
            "InvalidClassSpecificationException");
        try {
            bean.initialize();
            fail("ContestEligibilityValidationManagerConfigurationException should be thrown.");
        } catch (ContestEligibilityValidationManagerConfigurationException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for Initialize.ContestEligibilityValidationManagerConfigurationException is expected
     * because the file type is unrecognized.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testInitializeFailure8() throws Exception {
        setPrivateField(ContestEligibilityValidationManagerBean.class, bean, "configFileName",
            "UnrecognizedFileType.txt");
        setPrivateField(ContestEligibilityValidationManagerBean.class, bean, "namespace", "no");
        try {
            bean.initialize();
            fail("ContestEligibilityValidationManagerConfigurationException should be thrown.");
        } catch (ContestEligibilityValidationManagerConfigurationException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for Initialize.ContestEligibilityValidationManagerConfigurationException is expected
     * because there are two same validators.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testInitializeFailure9() throws Exception {
        setPrivateField(ContestEligibilityValidationManagerBean.class, bean, "configFileName", "bad.xml");
        setPrivateField(ContestEligibilityValidationManagerBean.class, bean, "namespace", "same_validators");
        try {
            bean.initialize();
            fail("ContestEligibilityValidationManagerConfigurationException should be thrown.");
        } catch (ContestEligibilityValidationManagerConfigurationException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for Initialize.ContestEligibilityValidationManagerConfigurationException is expected
     * because miss the required property-validator_obj_key.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testInitializeFailure10() throws Exception {
        setPrivateField(ContestEligibilityValidationManagerBean.class, bean, "configFileName", "bad.xml");
        setPrivateField(ContestEligibilityValidationManagerBean.class, bean, "namespace",
            "missRequiredProperty");
        try {
            bean.initialize();
            fail("ContestEligibilityValidationManagerConfigurationException should be thrown.");
        } catch (ContestEligibilityValidationManagerConfigurationException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for Initialize.ContestEligibilityValidationManagerConfigurationException is expected
     * because the required property-validator_obj_key is empty.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testInitializeFailure11() throws Exception {
        setPrivateField(ContestEligibilityValidationManagerBean.class, bean, "configFileName", "bad.xml");
        setPrivateField(ContestEligibilityValidationManagerBean.class, bean, "namespace", "emptyProperty");
        try {
            bean.initialize();
            fail("ContestEligibilityValidationManagerConfigurationException should be thrown.");
        } catch (ContestEligibilityValidationManagerConfigurationException e) {
            // pass
        }
    }

    /**
     * <p>
     * Accuracy test case for validate.It verifies that validate method return true because the user is
     * eligible.
     * </p>
     * <p>
     * Note that in setup.sql,the user id and group id are 5.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testValidateAccuracy1() throws Exception {
        bean.initialize();
        EntityManager entityManager =
            Persistence.createEntityManagerFactory("persistence-unit").createEntityManager();
        TestHelper.runSQL("drop.sql", entityManager);
        TestHelper.runSQL("setup.sql", entityManager);
        GroupContestEligibility contestEligibility1 = new GroupContestEligibility();
        contestEligibility1.setGroupId(5);
        GroupContestEligibility contestEligibility2 = new GroupContestEligibility();
        contestEligibility2.setGroupId(3);
        List<ContestEligibility> eligibilities = new ArrayList<ContestEligibility>();
        eligibilities.add(contestEligibility1);
        eligibilities.add(contestEligibility2);
        assertTrue("The user should be eligible,so return true.", bean.validate(5, eligibilities));
        TestHelper.runSQL("drop.sql", entityManager);
    }

    /**
     * <p>
     * Accuracy test case for validate.It verifies that validate method return false because the user is not
     * eligible.
     * </p>
     * <p>
     * Note that in setup.sql,the user id and group id are 5.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testValidateAccuracy2() throws Exception {
        bean.initialize();
        EntityManager entityManager =
            Persistence.createEntityManagerFactory("persistence-unit").createEntityManager();
        TestHelper.runSQL("drop.sql", entityManager);
        TestHelper.runSQL("setup.sql", entityManager);
        GroupContestEligibility contestEligibility = new GroupContestEligibility();
        contestEligibility.setGroupId(1);
        List<ContestEligibility> eligibilities = new ArrayList<ContestEligibility>();
        eligibilities.add(contestEligibility);
        assertFalse("The user should not be eligible,so return false.", bean.validate(1, eligibilities));
        TestHelper.runSQL("drop.sql", entityManager);
    }

    /**
     * <p>
     * Accuracy test case for validate.It verifies that validate method return true because the given list is
     * empty.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testValidateAccuracy3() throws Exception {
        bean.initialize();
        List<ContestEligibility> eligibilities = new ArrayList<ContestEligibility>();
        assertTrue("The given list is empty,so return true.", bean.validate(100, eligibilities));
    }

    /**
     * <p>
     * Failure test case for validate.IAE is expected because the list argument is null.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testValidateFailure1() throws Exception {
        bean.initialize();
        try {
            bean.validate(5, null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for validate.IAE is expected because the list argument contains null.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testValidateFailure2() throws Exception {
        bean.initialize();
        List<ContestEligibility> eligibilities = new ArrayList<ContestEligibility>();
        eligibilities.add(null);
        try {
            bean.validate(5, eligibilities);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for validate.UnsupportedContestEligibilityValidatiorException is expected because
     * there is no corresponding validator for <code>MockContestEligibility</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testValidateFailure3() throws Exception {
        bean.initialize();
        List<ContestEligibility> eligibilities = new ArrayList<ContestEligibility>();
        eligibilities.add(new MockContestEligibility());
        try {
            bean.validate(5, eligibilities);
            fail("UnsupportedContestEligibilityValidatiorException should be thrown.");
        } catch (UnsupportedContestEligibilityValidatiorException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for validate.ContestEligibilityValidationManagerException is expected because the
     * mock validator always throw ContestEligibilityValidatorException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testValidateFailure4() throws Exception {
        setPrivateField(ContestEligibilityValidationManagerBean.class, bean, "configFileName", "bad.xml");
        setPrivateField(ContestEligibilityValidationManagerBean.class, bean, "namespace", "mockValidator");
        bean.initialize();
        List<ContestEligibility> eligibilities = new ArrayList<ContestEligibility>();
        eligibilities.add(new MockContestEligibility());
        try {
            bean.validate(5, eligibilities);
            fail("ContestEligibilityValidationManagerException should be thrown.");
        } catch (ContestEligibilityValidationManagerException e) {
            // pass
        }
    }

    /**
     * <p>
     * Sets the value of a private field in the given class.
     * </p>
     *
     * @param instance
     *            the instance which the private field belongs to
     * @param name
     *            the name of the private field to be set
     * @param value
     *            the value to set
     * @param classType
     *            the class to get field
     */
    @SuppressWarnings("unchecked")
    private static void setPrivateField(Class classType, Object instance, String name, Object value) {
        try {
            Field field = null;
            // get the reflection of the field
            field = classType.getDeclaredField(name);
            // set the field accessible
            field.setAccessible(true);

            // set the value
            field.set(instance, value);
        } catch (SecurityException e) {
            // Ignore
        } catch (IllegalArgumentException e) {
            // Ignore
        } catch (NoSuchFieldException e) {
            // Ignore
        } catch (IllegalAccessException e) {
            // Ignore
        }
    }

    /**
     * <p>
     * Gets the value of a private field in the given instance by given name.
     * </p>
     *
     * @param instance
     *            the instance which the private field belongs to.
     * @param name
     *            the name of the private field to be retrieved.
     * @return the value of the private field.
     */
    private static Object getPrivateField(Object instance, String name) {
        Object obj = null;
        try {
            Field field = instance.getClass().getDeclaredField(name);

            // Set the field accessible.
            field.setAccessible(true);

            // Get the value
            obj = field.get(instance);
        } catch (NoSuchFieldException e) {
            // Ignore
        } catch (IllegalAccessException e) {
            // Ignore
        }
        return obj;
    }
}