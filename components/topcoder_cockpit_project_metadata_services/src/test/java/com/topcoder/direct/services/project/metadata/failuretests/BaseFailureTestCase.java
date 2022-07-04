/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.metadata.failuretests;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;

import junit.framework.TestCase;

/**
 * This is base unit {@link TestCase}.
 *
 * @author mumujava
 * @version 1.0
 */
public class BaseFailureTestCase extends TestCase {
    /** Represents empty string, used for test. */
    protected static final String EMPTY = "   \t \n";
    /** Represents entity manager, used for tests. */
    protected static final EntityManager ENTITY_MANAGER = loadManager("failurePersistence");
    
    protected static final String LONG_TEXT;
    static {
    	StringBuilder sb = new StringBuilder();
    	for (int i = 0; i < 1000; i++) {
    		sb.append("this is a large text\n");
    	}
    	LONG_TEXT = sb.toString();
    }
    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    protected void setUp() throws Exception {
    	super.setUp();
    	executeSqlFile("test_files/failure/clear.sql");
    	executeSqlFile("test_files/failure/init.sql");
    }
    /**
     * <p>
     * Tears down test environment.
     * </p>
     *
     * @throws Exception to jUnit.
     */
    protected void tearDown() throws Exception {
    	super.tearDown();
    	executeSqlFile("test_files/failure/clear.sql");
    }
    

    /**
     * Executes the sql file.
     *
     * @param filepath
     *            the sql file path.
     * @throws PersistenceException
     *             to JUnit
     * @throws IOException
     *             to JUnit
     */
    protected static void executeSqlFile(String filepath) throws IOException {
        EntityTransaction transaction = ENTITY_MANAGER.getTransaction();
        transaction.begin();
        ENTITY_MANAGER.clear();
        String[] sqls = getFileAsString(filepath).split(";");
        for (String sql : sqls) {
            sql = sql.trim();
            if (sql.length() != 0) {
                ENTITY_MANAGER.createNativeQuery(sql).executeUpdate();
            }
        }
        ENTITY_MANAGER.clear();
        transaction.commit();
    }

    /**
     * Gets the file content as string.
     *
     * @param filePath the file path
     * @return The content of file
     * @throws IOException to JUnit
     */
    public static String getFileAsString(String filePath) throws IOException {
        StringBuilder buf = new StringBuilder();
        BufferedReader in = new BufferedReader(new FileReader(filePath));
        try {
            String s;
            while ((s = in.readLine()) != null) {
                buf.append(s);
            }
            return buf.toString();
        } finally {
            in.close();
        }
    }

    /**
     * Gets private field value from object.
     *
     * @param fieldName name of the field
     * @param object object that contains field
     * @return fetched value
     * @throws Exception to junit
     */
    protected static Object getField(Object object, String fieldName) throws SecurityException, Exception {
        // fetch field and set its accessibility
        Field field = object.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);

        // return value of the field
        return field.get(object);
    }

    /**
     * Creates entity manager.
     *
     * @param name the name of entity manager
     * @return the loaded entity manager
     */
    private static EntityManager loadManager(String name) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory(name);
        return factory.createEntityManager();
    }

}
