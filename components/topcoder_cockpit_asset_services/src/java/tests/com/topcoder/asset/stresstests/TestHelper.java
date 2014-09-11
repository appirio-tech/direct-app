/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.asset.stresstests;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import javax.persistence.EntityManager;

/**
 * <p>
 * The helper class for testing.
 * </p>
 * 
 * @author progloco
 * @version 1.0
 */
public class TestHelper {

    /**
     * <p>
     * Represents the path of test files.
     * </p>
     */
    public static final String TEST_FILES = "test_files" + File.separator
            + "stress" + File.separator;

    /**
     * <p>
     * Clears the database.
     * </p>
     * 
     * @throws Exception to JUnit.
     */
    static void clearDB(EntityManager em) throws Exception {
        executeSQL(em, TEST_FILES + "clear.sql");
    }

    /**
     * <p>
     * Loads data into the database.
     * </p>
     * 
     * @throws Exception to JUnit.
     */
    static void loadDB(EntityManager em) throws Exception {
        executeSQL(em, TEST_FILES + "data.sql");
    }

    /**
     * <p>
     * Executes the SQL statements in the file. Lines that are empty or starts
     * with '#' will be ignore.
     * </p>
     * 
     * @param file the file.
     * 
     * @throws Exception to JUnit.
     */
    static void executeSQL(EntityManager em, String file) throws Exception {
        // EntityManager em = factory.createEntityManager();
        if (!em.getTransaction().isActive()) {
            em.getTransaction().begin();
        }

        String[] values = readFile(file).split(";");

        for (int i = 0; i < values.length; i++) {
            String sql = values[i].trim();
            if ((sql.length() != 0) && (!sql.startsWith("#"))) {
                em.createNativeQuery(sql).executeUpdate();
            }
        }

        em.getTransaction().commit();
    }

    /**
     * <p>
     * Deletes the file.
     * </p>
     * 
     * @param file the file
     */
    private static void deleteFile(File file) {
        if (file.isDirectory()) {
            for (File subFile : file.listFiles()) {
                deleteFile(subFile);
            }
        }

        file.delete();
    }

    /**
     * Clears the output.
     */
    static void clearOutput() {
        File file = new File(TEST_FILES + "dir");
        deleteFile(file);
        file.mkdir();
    }

    /**
     * <p>
     * Reads the content of a given file.
     * </p>
     * 
     * @param fileName the name of the file to read.
     * 
     * @return a string represents the content.
     * 
     * @throws IOException if any error occurs during reading.
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