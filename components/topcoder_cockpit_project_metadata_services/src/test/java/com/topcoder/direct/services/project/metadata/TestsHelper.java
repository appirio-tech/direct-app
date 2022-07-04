/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.metadata;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Field;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * <p>
 * This class provides methods for testing this component.
 * </p>
 *
 * @author sparemax
 * @version 1.0
 */
public class TestsHelper {
    /**
     * <p>
     * Represents the empty string.
     * </p>
     */
    public static final String EMPTY_STRING = " \t ";

    /**
     * <p>
     * Represents the <code>ApplicationContext </code> for tests.
     * </p>
     */
    public static final ApplicationContext APP_CONTEXT;

    /**
     * <p>
     * Represents the path of test files.
     * </p>
     */
    private static final String TEST_FILES = "test_files" + File.separator;

    /**
     * <p>
     * Initialization.
     * </p>
     */
    static {
        APP_CONTEXT = new ClassPathXmlApplicationContext("applicationContext.xml");
    }

    /**
     * <p>
     * Private constructor to prevent this class being instantiated.
     * </p>
     */
    private TestsHelper() {
        // empty
    }

    /**
     * <p>
     * Gets an entity manager.
     * </p>
     *
     * @return the entity manager.
     *
     * @throws Exception
     *             to JUnit.
     */
    public static EntityManager getEntityManager() throws Exception {
        return ((EntityManagerFactory) TestsHelper.APP_CONTEXT.getBean("entityManagerFactory")).createEntityManager();
    }

    /**
     * <p>
     * Clears the database.
     * </p>
     *
     * @param em
     *            the entity manager.
     *
     * @throws Exception
     *             to JUnit.
     */
    public static void clearDB(EntityManager em) throws Exception {
        executeSQL(em, TEST_FILES + "clear.sql");
    }

    /**
     * <p>
     * Loads data into the database.
     * </p>
     *
     * @param em
     *            the entity manager.
     *
     * @throws Exception
     *             to JUnit.
     */
    public static void loadDB(EntityManager em) throws Exception {
        executeSQL(em, TEST_FILES + "data.sql");
    }

    /**
     * <p>
     * Gets value for field of given object.
     * </p>
     *
     * @param obj
     *            the given object.
     * @param field
     *            the field name.
     *
     * @return the field value.
     */
    public static Object getField(Object obj, String field) {
        Object value = null;
        try {
            Field declaredField = null;
            try {
                declaredField = obj.getClass().getDeclaredField(field);
            } catch (NoSuchFieldException e) {
                // Ignore
            }
            if (declaredField == null) {
                // From the superclass
                declaredField = obj.getClass().getSuperclass().getDeclaredField(field);
            }

            declaredField.setAccessible(true);

            try {
                value = declaredField.get(obj);
            } finally {
                declaredField.setAccessible(false);
            }
        } catch (IllegalAccessException e) {
            // Ignore
        } catch (NoSuchFieldException e) {
            // Ignore
        }

        return value;
    }

    /**
     * <p>
     * Executes the SQL statements in the file. Lines that are empty or starts with '#' will be ignore.
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
    private static void executeSQL(EntityManager em, String file) throws Exception {
        em.clear();
        em.getTransaction().begin();

        String[] values = readFile(file).split(";");

        for (int i = 0; i < values.length; i++) {
            String sql = values[i].trim();
            if ((sql.length() != 0) && (!sql.startsWith("#"))) {
                em.createNativeQuery(sql).executeUpdate();
            }
        }

        em.getTransaction().commit();
        em.clear();
    }

    /**
     * <p>
     * Reads the content of a given file.
     * </p>
     *
     * @param fileName
     *            the name of the file to read.
     *
     * @return a string represents the content.
     *
     * @throws IOException
     *             if any error occurs during reading.
     */
    private static String readFile(String fileName) throws IOException {
        Reader reader = new FileReader(fileName);

        try {
            // Create a StringBuilder instance
            StringBuilder sb = new StringBuilder();

            // Buffer for reading
            char[] buffer = new char[1024];

            // Number of read chars
            int k = 0;

            // Read characters and append to string builder
            while ((k = reader.read(buffer)) != -1) {
                sb.append(buffer, 0, k);
            }

            // Return read content
            return sb.toString();
        } finally {
            try {
                reader.close();
            } catch (IOException ioe) {
                // Ignore
            }
        }
    }
}
