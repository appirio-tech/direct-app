/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.asset.accuracytests;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import junit.framework.TestCase;

/**
 * <p>
 * This test case aggregates all accuracy unit test cases.
 * </p>
 *
 * @author lqz
 * @version 1.0
 */
public class AccuracyHelper extends TestCase {
    private static EntityManager EM;

    /**
     * Clear the database.
     * 
     * @param connection
     *            the connection.
     * 
     * @throws Exception
     *             to JUnit.
     */
    protected static void clearDB( ) throws Exception {
        executeSQL(getConnection(), "test_files/acc/clear.sql");
    }

    /**
     * Clear the database.
     * 
     * @param connection
     *            the connection.
     * 
     * @throws Exception
     *             to JUnit.
     */
    protected static void prepareDB( ) throws Exception {
        executeSQL(getConnection(), "test_files/acc/prepare.sql");
    }
    
    /**
     * gets the connection.
     * 
     * @param connection
     *            the connection.
     * 
     * @throws Exception
     *             to JUnit.
     */
    protected static EntityManager getConnection() throws Exception {
    	if (EM == null) {
    		 EM = Persistence.createEntityManagerFactory("persistenceUnit").createEntityManager();
    	}
    	return EM;
    }
    /**
     * Executes the SQL statements from file.
     * 
     * @param connection
     *            the connection.
     * @param file
     *            the file.
     * 
     * @throws Exception
     *             to JUnit.
     */
    private static void executeSQL(EntityManager em, String file) throws Exception {
        String[] values = readFile(file).split(";");
        if (!em.getTransaction().isActive()) {
        	em.getTransaction().begin();
        }
            for (int i = 0; i < values.length; i++) {
                String sql = values[i].trim();
                if ((sql.length() != 0) && (!sql.startsWith("#"))) {
                    em.createNativeQuery(sql).executeUpdate();
                }
            }
        em.getTransaction().commit();
    }

    /**
     * Reads file to a string.
     * 
     * @param fileName
     *            the name of the file to read.
     * 
     * @return a string represents the content.
     * 
     * @throws IOException
     *             if any IO error occurs.
     */
    private static String readFile(String fileName) throws IOException {
        Reader reader = new FileReader(fileName);

        try {
            StringBuilder sb = new StringBuilder();
            char[] buffer = new char[1024];
            int k = 0;
            while ((k = reader.read(buffer)) != -1) {
                sb.append(buffer, 0, k);
            }
            return sb.toString().replace("\r\n", "\n");
        } finally {
            try {
                reader.close();
            } catch (IOException ioe) {
                // Ignore
            }
        }
    }
}
