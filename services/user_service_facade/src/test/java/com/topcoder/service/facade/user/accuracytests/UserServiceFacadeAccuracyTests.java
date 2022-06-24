/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.facade.user.accuracytests;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Iterator;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.service.user.ejb.UserServiceBean;
import com.topcoder.service.user.UserService;
import com.topcoder.service.user.ejb.UserServiceBean;
import com.topcoder.util.config.ConfigManager;

/**
 * <p>
 * Tests the <code>{@link UserServiceBean}</code> for accuracy.
 * </p>
 * 
 * @author snow01
 * 
 * @since Jira & Confluence User  Service
 * @version 1.0
 */
public class UserServiceFacadeAccuracyTests extends TestCase {

    /**
     * The configNamespace used in tests.
     */
    private static final String configNamespace = "com.topcoder.project.service.user.UserServiceBean";

    /**
     * The config file name used in tests.
     */
    private static final String configFileName = "config.xml";

    /**
     * Represents the bean to test.
     */
    private UserServiceFacade testBean;

    /**
     * <p>
     * Aggregates all tests.
     * </p>
     * 
     * @return A test suite will be returned
     */
    public static Test suite() {
        return new TestSuite(UserServiceAccuracyTests.class);
    }

    /**
     * Sets up the environment.
     * 
     * @throws Exception
     *             if any error is encountered during run, exception is thrown to JUnit
     */
    @Override
    protected void setUp() throws Exception {
        createTestBean();
        setPrivateField(UserServiceFacadeBase.class, testBean, "logName", "UserServiceFacadeAccuracyTests");
        setPrivateField(UserServiceFacadeBase.class, testBean, "configNamespace", configNamespace);
        setPrivateField(UserServiceFacadeBase.class, testBean, "userService", new UserServiceBean());

        clearConfig();
        addConfig(configFileName);

        invoke(UserServiceFacadeBase.class, testBean, "init");
    }

    /**
     * Tears down the environment.
     * 
     * @throws Exception
     *             if any error is encountered during run, exception is thrown to JUnit
     */
    @Override
    protected void tearDown() throws Exception {
        clearConfig();
    }

    /**
     * Tests the <code>{@link  UserServiceBeanFacade#UserServiceFacadeBean()}</code>.
     */
    public void testUserServiceFacadeBean() {
        assertNotNull("Failed to create the bean.", getTestBean());
    }

    /**
     * Tests the <code>{@link UserServiceFacadeBean#UserServiceBean()}</code>.
     * 
     * @throws Exception
     *             if any error is encountered during run, exception is thrown to JUnit
     */
    public void testGetJiraUser() throws Exception {
        String email = testBean.getJiraUser("user");
        assertNotNull("Failed to get Jira User", email);
    }

    /**
     * Tests the <code>{@link UserServiceFacadeBean#UserServiceFacadeBean()}</code>.
     * 
     * @throws Exception
     *             if any error is encountered during run, exception is thrown to JUnit
     */
    public void testGetConfluenceUser() throws Exception {
        String email = testBean.getConfluenceUser("user");
        assertNotNull("Failed to get Confluence User", email);
    }

    /**
     * Creates the EJB specific to this test.
     */
    protected void createTestBean() {
        UserService bean = new UserServiceFacadeBean();
        setTestBean(bean);
    }

    /**
     * Gets the test bean for this accuracy test.
     * 
     * @return the test bean for this accuracy test.
     */
    public UserServiceFacade getTestBean() {
        return testBean;
    }

    /**
     * Sets the test bean for this accuracy test.
     * 
     * @param testBean
     *            the test bean for this accuracy test.
     */
    public void setTestBean(UserService testBean) {
        this.testBean = testBean;
    }

    /**
     * Remove all the namespace.
     * 
     * @throws Exception
     *             if any error is encountered during run, exception is thrown to JUnit
     */
    @SuppressWarnings("unchecked")
    private static void clearConfig() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        Iterator it = cm.getAllNamespaces();

        while (it.hasNext()) {
            cm.removeNamespace((String) it.next());
        }
    }

    /**
     * Add the namespace.
     * 
     * @param filename
     *            the config filename
     * @throws Exception
     *             if any error is encountered during run, exception is thrown to JUnit
     */
    public static void addConfig(String filename) throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        cm.add(filename);
    }

    /**
     * Sets the value of a private field in the given class.
     * 
     * @param type
     *            the class which the private field belongs to
     * @param instance
     *            the instance which the private field belongs to
     * @param name
     *            the name of the private field to be set
     * @param value
     *            the value to set
     */
    @SuppressWarnings("unchecked")
    private static void setPrivateField(Class type, Object instance, String name, Object value) {
        Field field = null;

        try {
            // get the reflection of the field
            field = type.getDeclaredField(name);

            // set the field accessible
            field.setAccessible(true);

            // set the value
            field.set(instance, value);
        } catch (NoSuchFieldException e) {
            // ignore
        } catch (IllegalAccessException e) {
            // ignore
        } finally {
            if (field != null) {
                // reset the accessibility
                field.setAccessible(false);
            }
        }
    }

    /**
     * Invokes the function.
     * 
     * @param type
     *            the class which the method belongs to
     * @param instance
     *            the instance which the method belongs to
     * @param name
     *            the name of the method
     */
    @SuppressWarnings("unchecked")
    private static void invoke(Class type, Object instance, String name) {
        Method method = null;

        try {
            // get the reflection of the field
            method = type.getDeclaredMethod(name);
            method.setAccessible(true);
            method.invoke(instance);
        } catch (Exception e) {
            // ignore
            e.printStackTrace();
        } finally {
            if (method != null) {
                method.setAccessible(false);
            }
        }
    }
}
