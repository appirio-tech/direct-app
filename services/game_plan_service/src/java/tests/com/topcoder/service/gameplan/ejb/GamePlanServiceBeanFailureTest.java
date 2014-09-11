/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.gameplan.ejb;

import com.topcoder.security.RolePrincipal;
import com.topcoder.security.TCSubject;
import com.topcoder.service.gameplan.GamePlanServiceConfigurationException;
import com.topcoder.service.gameplan.GamePlanPersistenceException;

import com.topcoder.service.gameplan.ejb.GamePlanServiceBean;
import com.topcoder.service.util.gameplan.SoftwareProjectData;
import com.topcoder.service.util.gameplan.StudioProjectData;
import com.topcoder.service.util.gameplan.TCDirectProjectGamePlanData;

import org.hibernate.ejb.Ejb3Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.lang.reflect.Field;
import java.util.List;


/**
 * Failure test for GamePlanServiceBean class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class GamePlanServiceBeanFailureTest {

    /**
     * The GamePlanServiceBean instance used in test.
     */
    private GamePlanServiceBean instance;

    /**
     * The entity manager for tcs_catalog database
     */
    private EntityManager softwareEntityManager;

    /**
     * The entity manager for studio_oltp database.
     */
    private EntityManager studioEntityManager;

    /**
     * Set up for each test.
     */
    @Before
    public void setUp() {
        instance = new GamePlanServiceBean();
    }

    /**
     * Tear down for each test.
     */
    @After
    public void tearDown() {
        instance = null;
    }

    /**
     * Test initialize method. When the logName is empty string.
     *
     * @throws Exception to jUnit
     */
    @Test(expected = GamePlanServiceConfigurationException.class)
    public void testInitialize_LogNameIsEmpty() throws Exception {
        setField(instance, "logName", "  ");

        instance.initialize();
    }

    /**
     * Test retrieveGamePlanData method. When tcSubject is null.
     *
     * @throws Exception to jUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testRetrieveGamePlanData_TCSubjectIsNull() throws Exception {
        instance.retrieveGamePlanData(null);
    }

    /**
     * Test retrieveGamePlanData method. When softwareEntityManager is not set.
     *
     * @throws Exception to jUnit
     */
    @Test(expected = IllegalStateException.class)
    public void testRetrieveGamePlanData_SoftwareEntityManagerIsNotSet() throws Exception {
        setField(instance, "studioEntityManager", getStudioEntityManager("hibernate_studio.cfg.xml"));

        instance.retrieveGamePlanData(new TCSubject(1));
    }

    /**
     * Test retrieveGamePlanData method. When studioEntityManager is not set.
     *
     * @throws Exception to jUnit
     */
    @Test(expected = IllegalStateException.class)
    public void testRetrieveGamePlanData_StudioEntityManagerIsNotSet() throws Exception {
        setField(instance, "softwareEntityManager", getSoftwareEntityManager("hibernate_software.cfg.xml"));

        instance.retrieveGamePlanData(new TCSubject(1));
    }

    /**
     * Test retrieveGamePlanData method. When error occurred connecting the database.
     *
     * @throws Exception to jUnit
     */
    @Test(expected = GamePlanPersistenceException.class)
    public void testRetrieveGamePlanData_InvalidConnectionIsInvalid1() throws Exception {
        setField(instance, "studioEntityManager", getStudioEntityManager("failure/hibernate_studio.cfg.xml"));
        setField(instance, "softwareEntityManager", getSoftwareEntityManager("hibernate_software.cfg.xml"));

        instance.retrieveGamePlanData(new TCSubject(1));
    }

    /**
     * Test retrieveGamePlanData method. When error occurred connecting the database.
     *
     * @throws Exception to jUnit
     */
    @Test(expected = GamePlanPersistenceException.class)
    public void testRetrieveGamePlanData_InvalidConnectionIsInvalid2() throws Exception {
        setField(instance, "studioEntityManager", getStudioEntityManager("hibernate_studio.cfg.xml"));
        setField(instance, "softwareEntityManager", getSoftwareEntityManager("failure/hibernate_software.cfg.xml"));

        instance.retrieveGamePlanData(new TCSubject(1));
    }

    /**
     * Gets EntityManager for tcs_catalog database.
     *
     * @return the EntityManager for software
     */
    private EntityManager getSoftwareEntityManager(String file) {
        if (softwareEntityManager == null || !softwareEntityManager.isOpen()) {
            // create entityManager
            Ejb3Configuration cfg = new Ejb3Configuration();
            cfg.configure(file);

            EntityManagerFactory emf = cfg.buildEntityManagerFactory();
            softwareEntityManager = emf.createEntityManager();
        }

        return softwareEntityManager;
    }

    /**
     * Gets EntityManager for studio_oltp database.
     *
     * @return the EntityManager for studio
     */
    private EntityManager getStudioEntityManager(String file) {
        if (studioEntityManager == null || !studioEntityManager.isOpen()) {
            // create entityManager
            Ejb3Configuration cfg = new Ejb3Configuration();
            cfg.configure(file);

            EntityManagerFactory emf = cfg.buildEntityManagerFactory();
            studioEntityManager = emf.createEntityManager();
        }
        return studioEntityManager;
    }

    /**
     * Set the field value of the given object.
     *
     * @param object    the object to use
     * @param fieldName the name of the field
     * @param value     the value to set
     * @throws Exception to jUnit
     */
    private static void setField(Object object, String fieldName, Object value) throws Exception {
        Field field = null;
        Class<?> instanceClass = object.getClass();
        while (null == field) {
            try {
                field = instanceClass.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                instanceClass = instanceClass.getSuperclass();
                if (null == instanceClass) {
                    throw e;
                }
            }
        }

        field.setAccessible(true);
        field.set(object, value);
    }
}