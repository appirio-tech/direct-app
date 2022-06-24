
/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.service.contest.eligibilityvalidation;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import com.topcoder.service.contest.eligibility.ContestEligibility;

/**
 * <p>
 * Unit Tests for {@link ContestEligibilityValidationManagerBean}.
 * </p>
 *
 * @author fivestarwy
 * @version 1.0
 */
public class ContestEligibilityValidationManagerBeanUnitTests extends TestCase {

    /**
     * instance used for testing.
     */
    private ContestEligibilityValidationManagerBean instance;

    /**
     * Sets up the environment.
     *
     * @throws Exception to JUNIT.
     */
    protected void setUp() throws Exception {
        instance = new ContestEligibilityValidationManagerBean();
    }

    /**
     * Clears the environment.
     */
    protected void tearDown() {
        instance = null;
    }
    

    /**
     * <p>
     * Failure test case for <code>Initialize</code>.ContestEligibilityValidationManagerConfigurationException is expected
     * because the entity_name is empty.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testInitializeFailure1() throws Exception {
        setPrivateField(ContestEligibilityValidationManagerBean.class, instance, "configFileName", "failure.xml");
        setPrivateField(ContestEligibilityValidationManagerBean.class, instance, "namespace",
            "empty_entity_name");
        try {
            instance.initialize();
            fail("ContestEligibilityValidationManagerConfigurationException should be thrown.");
        } catch (ContestEligibilityValidationManagerConfigurationException e) {
            // pass
        }
    }
    /**
     * <p>
     * Failure test case for <code>Initialize</code>.ContestEligibilityValidationManagerConfigurationException is expected
     * because the "validator_obj_key" is empty.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testInitializeFailure2() throws Exception {
        setPrivateField(ContestEligibilityValidationManagerBean.class, instance, "configFileName", "failure.xml");
        setPrivateField(ContestEligibilityValidationManagerBean.class, instance, "namespace",
            "empty_key");
        try {
            instance.initialize();
            fail("ContestEligibilityValidationManagerConfigurationException should be thrown.");
        } catch (ContestEligibilityValidationManagerConfigurationException e) {
            // pass
        }
    }
    
    /**
     * <p>
     * Failure test case for <code>Initialize</code>.ContestEligibilityValidationManagerConfigurationException is expected
     * because the entity is invalid.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testInitializeFailure3() throws Exception {
        setPrivateField(ContestEligibilityValidationManagerBean.class, instance, "configFileName", "failure.xml");
        setPrivateField(ContestEligibilityValidationManagerBean.class, instance, "namespace",
            "invalid_entity");
        try {
            instance.initialize();
            fail("ContestEligibilityValidationManagerConfigurationException should be thrown.");
        } catch (ContestEligibilityValidationManagerConfigurationException e) {
            // pass
        }
    }
    
    
    /**
     * <p>
     * Failure test case for <code>Initialize</code>.ContestEligibilityValidationManagerConfigurationException is expected
     * because the persistence unit doesn't exist.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testInitializeFailure4() throws Exception {
        setPrivateField(ContestEligibilityValidationManagerBean.class, instance, "configFileName", "failure.xml");
        setPrivateField(ContestEligibilityValidationManagerBean.class, instance, "namespace",
            "invalid_persistence_unit");
        try {
            instance.initialize();
            fail("ContestEligibilityValidationManagerConfigurationException should be thrown.");
        } catch (ContestEligibilityValidationManagerConfigurationException e) {
            // pass
        }
    }
    /**
     * Failure test for <code>validate(long, List)<code> method. 
     * eligibilities is null and IAE is expected.
     * 
     * @throws Exception to JUNIT.
     */
    public void testValidate_failure_1() throws Exception {
        instance.initialize();
        try {
            instance.validate(11111, null);
            fail("IAE should be thrown");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Failure test for <code>validate(long, List)<code> method. 
     * eligibilities list contain null and IAE is expected.
     * 
     * @throws Exception to JUNIT.
     */
    public void testValidate_failure_2() throws Exception {
        instance.initialize();
        List<ContestEligibility> list=new ArrayList<ContestEligibility>();
        list.add((ContestEligibility)null);
        try {
            instance.validate(10, list);
            fail("IAE should be thrown");
        } catch (IllegalArgumentException e) {
            // ok
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

