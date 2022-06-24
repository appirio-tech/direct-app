/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.metadata.accuracytests.impl;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.lang.reflect.Field;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * <p>
 * Helper class for accuracy test.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AccuracyTestHelper {
    /**
     * <p>
     * Represents the ApplicationContext used to load configuration.
     * </p>
     */
    public static final ApplicationContext CONTEXT;

    /**
     * <p>
     * Represents the directory used in accuracy tests.
     * </p>
     */
    private static final String ACC_FOLDER = "test_files" + File.separator + "acc" + File.separator;

    /**
     * <p>
     * Loads the definitions from the given XML file.
     * </p>
     */
    static {
        CONTEXT = new FileSystemXmlApplicationContext(ACC_FOLDER + "applicationContext.xml");
    }

    /**
     * <p>
     * Private constructor.
     * </p>
     */
    private AccuracyTestHelper() {
        // empty
    }

    /**
     * <p>
     * Creates an instance of EntityManager used in test.
     * </p>
     *
     * @return the created instance of EntityManager.
     *
     * @throws Exception
     *             to JUnit.
     */
    public static EntityManager createEntityManager() throws Exception {
        return ((EntityManagerFactory) CONTEXT.getBean("entityManagerFactory")).createEntityManager();
    }

    /**
     * <p>
     * Clear test data.
     * </p>
     *
     * @param em
     *            the entity manager.
     *
     * @throws Exception
     *             to JUnit.
     */
    public static void clearDB(EntityManager em) throws Exception {
        execute(em, ACC_FOLDER + "clear.sql");
    }

    /**
     * <p>
     * Add test data.
     * </p>
     *
     * @param em
     *            the entity manager.
     *
     * @throws Exception
     *             to JUnit.
     */
    public static void loadDB(EntityManager em) throws Exception {
        execute(em, ACC_FOLDER + "clear.sql");
        execute(em, ACC_FOLDER + "data.sql");
    }

    /**
     * <p>
     * Executes the SQL statements in the file.
     * </p>
     *
     * @param em
     *            the entity manager.
     * @param file
     *            the file.
     *
     * @throws Exception
     *             to JUnit.
     */
    private static void execute(EntityManager em, String file) throws Exception {
        em.clear();
        em.getTransaction().begin();

        String[] values = readFile(file).split(";");

        for (int i = 0; i < values.length; i++) {
            if (values[i].trim().length() > 0) {
                em.createNativeQuery(values[i]).executeUpdate();
            }
        }
        em.getTransaction().commit();
        em.clear();
    }

    /**
     * <p>
     * Reads the content from the file.
     * </p>
     *
     * @param fileName
     *            the name of the file to read.
     *
     * @return the file content.
     *
     * @throws Exception
     *             to jUnit.
     */
    private static String readFile(String fileName) throws Exception {
        Reader reader = new FileReader(fileName);

        try {
            StringBuilder sb = new StringBuilder();
            char[] buffer = new char[1024];
            int i = 0;
            while ((i = reader.read(buffer)) != -1) {
                sb.append(buffer, 0, i);
            }
            return sb.toString();
        } finally {
            reader.close();
        }
    }

    /**
     * <p>
     * Gets value for field of given object.
     * </p>
     *
     * @param clazz
     *            the type of object.
     * @param obj
     *            the given object.
     * @param field
     *            the field name.
     * @return the field value.
     *
     * @throws Exception
     *             to jUnit.
     */
    public static Object getField(Class<?> clazz, Object obj, String name) throws Exception {
        Field field = null;

        if (clazz == null) {
            clazz = obj.getClass();
        }
        field = clazz.getDeclaredField(name);
        field.setAccessible(true);
        try {
            return field.get(obj);
        } finally {
            field.setAccessible(false);
        }
    }
}