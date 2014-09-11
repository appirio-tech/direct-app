/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.metadata.stresstests;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * <p>
 * This class provides methods for testing this component.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class StressTestsHelper {

    /**
     * <p>
     * Represents the <code>ApplicationContext </code> for tests.
     * </p>
     */
    public static final ApplicationContext APP_CONTEXT;

    /**
     * <p>
     * Represents the path of stress files.
     * </p>
     */
    private static final String STRESS_FILES = "test_files" + File.separator + "stress" + File.separator;

    /**
     * <p>
     * Initialization.
     * </p>
     */
    static {
        APP_CONTEXT = new ClassPathXmlApplicationContext("stress" + File.separator + "applicationContext.xml");
    }

    /**
     * <p>
     * Private constructor to prevent this class being instantiated.
     * </p>
     */
    private StressTestsHelper() {
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
        return ((EntityManagerFactory) APP_CONTEXT.getBean("entityManagerFactory")).createEntityManager();
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
        executeSQL(em, STRESS_FILES + "clear.sql");
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
        executeSQL(em, STRESS_FILES + "data.sql");
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
