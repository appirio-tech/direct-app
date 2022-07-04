/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.contest.eligibilityvalidation.accuracytests;

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
import com.topcoder.service.contest.eligibilityvalidation.ContestEligibilityValidationManagerBean;
import com.topcoder.service.contest.eligibilityvalidation.ContestEligibilityValidator;
import com.topcoder.service.contest.eligibilityvalidation.GroupEligibilityValidator;
import com.topcoder.util.log.LogManager;

/**
 * <p>
 * UnitTest cases of the <code>ContestEligibilityValidationManagerBean</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ContestEligibilityValidationManagerBeanAccTests extends TestCase {

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
        TestSuite suite = new TestSuite(ContestEligibilityValidationManagerBeanAccTests.class);
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
    @Override
    protected void setUp() throws Exception {
        bean = new ContestEligibilityValidationManagerBean();
        Map<String, ContestEligibilityValidator> validators = (Map<String, ContestEligibilityValidator>) getPrivateField(bean, "validators");
        validators.put("com.topcoder.service.contest.eligibility.GroupContestEligibility", new GroupEligibilityValidator("persistence-unit"));
        setPrivateField(ContestEligibilityValidationManagerBean.class, bean, "logger", LogManager.getLog());
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     *
     * @throws Exception
     *             to jUnit.
     */
    @Override
    protected void tearDown() throws Exception {
        bean = null;
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