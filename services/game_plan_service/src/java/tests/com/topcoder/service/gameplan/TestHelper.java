/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.service.gameplan;

import org.hibernate.ejb.Ejb3Configuration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.lang.reflect.Field;

/**
 * Helper class for testing purpose.
 *
 * @author FireIce
 * @version 1.0
 */
public class TestHelper {
    /**
     * <p>Represents the entity manager for tcs_catalog database.</p>
     */
    private static EntityManager softwareEntityManager;
    /**
     * <p>Represents the entity manager for studio_oltp database.</p>
     */
    private static EntityManager studioEntityManager;

    /**
     * Private constructor.
     */
    private TestHelper() {
    }

    /**
     * <p>Gets EntityManager for tcs_catalog database.</p>
     *
     * @return EntityManager
     */
    public static EntityManager getSoftwareEntityManager() {
        if (softwareEntityManager == null || !softwareEntityManager.isOpen()) {
            // create entityManager
            Ejb3Configuration cfg = new Ejb3Configuration();
            cfg.configure("hibernate_software.cfg.xml");

            EntityManagerFactory emf = cfg.buildEntityManagerFactory();
            softwareEntityManager = emf.createEntityManager();
        }

        softwareEntityManager.clear();
        if (!softwareEntityManager.getTransaction().isActive()) {
            softwareEntityManager.getTransaction().begin();
        }
        return softwareEntityManager;
    }

    /**
     * <p>Gets EntityManager for studio_oltp database.</p>
     *
     * @return EntityManager
     */
    public static EntityManager getStudioEntityManager() {
        if (studioEntityManager == null || !studioEntityManager.isOpen()) {
            // create entityManager
            Ejb3Configuration cfg = new Ejb3Configuration();
            cfg.configure("hibernate_studio.cfg.xml");

            EntityManagerFactory emf = cfg.buildEntityManagerFactory();
            studioEntityManager = emf.createEntityManager();
        }

        studioEntityManager.clear();
        if (!studioEntityManager.getTransaction().isActive()) {
            studioEntityManager.getTransaction().begin();
        }
        return studioEntityManager;
    }

    /**
     * <p>Retrieves the specified field of the given object.</p>
     *
     * @param object    the object that holds the field
     * @param fieldName the name of the field
     * @return the value of the field
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public static Object getField(Object object, String fieldName) throws Exception {
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
        return field.get(object);
    }

    /**
     * <p>Set the specified field of the given object.</p>
     *
     * @param object    the object that holds the field
     * @param fieldName the name of the field
     * @param value     the value to set
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public static void setField(Object object, String fieldName, Object value) throws Exception {
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