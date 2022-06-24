/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.service.contest.eligibility.failuretests;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Field;

import javax.persistence.EntityManager;

/**
 * <p>
 * The helper class for unit test.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class TestHelper {

    /**
     * <p>
     * Represent the file name of delete data sql scripts.
     * </p>
     */
    private static final String DELETE_SQL_FILE = "drop.sql";

    /**
     * <p>
     * Represent the file name of insert data sql scripts.
     * </p>
     */
    private static final String INSERT_SQL_FILE = "test_files/insert.sql";

    /**
     * <p>Private constructor.</p>
     */
    private TestHelper() {
        // nothing
    }

    /**
     * <p>Outjects the value of the given instance.</p>
     *
     * @param clazz clazz of where the field is declared
     * @param instance the instance whose value to outject
     * @param fieldName the field whose value to outject
     * @return the value
     * @throws Exception if anything wrong
     */
    @SuppressWarnings("unchecked")
    public static Object outject(Class clazz, Object instance, String fieldName) throws Exception {
        Field field = clazz.getDeclaredField(fieldName);
        try {
            field.setAccessible(true);
            return field.get(instance);
        } finally {
            if (field != null) {
                field.setAccessible(false);
            }
        }
    }

    /**
     * <p>Injects the value.</p>
     * @param clazz the class of the object
     * @param name the name of the field
     * @param obj the object to inject to
     * @param value the value of the field
     * @throws Exception if anything is wrong
     */
    @SuppressWarnings("unchecked")
    public static void inject(Class clazz, String name, Object obj, Object value) throws Exception {
        Field field = null;
        try {
            field = clazz.getDeclaredField(name);
            field.setAccessible(true);
            field.set(obj, value);
        } finally {
            if (field != null) {
                field.setAccessible(false);
            }
        }
    }
    /**
     * <p>
     * Inserts data for test.
     * </p>
     * @param em The entity manager instance
     *
     * @throws Exception
     *             to JUnit.
     */
    public static void insertDB(EntityManager em) throws Exception {
        runSQL(DELETE_SQL_FILE, em);
        runSQL(INSERT_SQL_FILE, em);
    }

    /**
     * <p>
     * Clears data for test.
     * </p>
     * @param em The entity manager instance
     *
     * @throws Exception
     *             to JUnit.
     */
    public static void clearDB(EntityManager em) throws Exception {
        runSQL(DELETE_SQL_FILE, em);
    }
    /**
     * <p>
     * Runs the given file.
     * </p>
     *
     * @param file The sql file to be executed.
     * @param em The entity manager instance
     *
     * @throws Exception to JUnit
     */
    private static void runSQL(String file, EntityManager em) throws Exception {
        String content = getFileAsString(file);
        if (!em.getTransaction().isActive()) {
            em.getTransaction().begin();
        }

        for (String st : content.split(";")) {
            em.createNativeQuery(st).executeUpdate();
        }

        em.getTransaction().commit();
    }
    /**
     * <p>
     * Gets the file content as string.
     * </p>
     *
     * @param file The file to get its content.
     *
     * @return The content of file.
     *
     * @throws Exception to JUnit
     */
    static String getFileAsString(String file) throws Exception {
        StringBuilder buf = new StringBuilder();
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(ClassLoader.getSystemResourceAsStream(file)));
            String s;

            while ((s = in.readLine()) != null) {
                buf.append(s);
            }
        } finally {
            if (in != null) {

                in.close();
            }
        }
        return buf.toString();
    }
}
