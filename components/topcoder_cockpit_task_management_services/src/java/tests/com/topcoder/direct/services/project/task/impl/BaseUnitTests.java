/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.task.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.topcoder.service.user.User;
import com.topcoder.util.config.ConfigManager;

/**
 * <p>
 * The base class of unit tests.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 *
 */
public class BaseUnitTests {


    /**
     * <p>
     * Represents the path of the test files.
     * </p>
     */
    private static final String TEST_FILES = "test_files" + File.separator;

    /**
     * <p>
     * Represents the configuration file.
     * </p>
     */
    private static final String TEST_CONFIG = TEST_FILES + "test.properties";

    /**
     * <p>
     * Represents the spring application context for unit tests.
     * </p>
     */
    private static ApplicationContext ctx;

    /**
     * <p>
     * Represents the entity manager for unit tests.
     * </p>
     */
    private EntityManager entityManager;

    /**
     * <p>
     * Represents the configuration for unit tests.
     * </p>
     */
    private Properties properties;



    /**
     * <p>
     * Initializes the spring beans.
     * </p>
     */
    @BeforeClass
    public static void init() {
        ctx = new ClassPathXmlApplicationContext("spring.xml");
    }

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     * @throws Exception to JUnit.
     */
    @Before
    public void setUp() throws Exception {

        EntityManagerFactory emf = (EntityManagerFactory) ctx.getBean("entityManagerFactory");
        entityManager = emf.createEntityManager();

        InputStream is = new FileInputStream(new File(TEST_CONFIG));
        properties = new Properties();
        properties.load(is);
        is.close();

        // set the config manager
        ConfigManager cm = ConfigManager.getInstance();
        for (Iterator<String> it = cm.getAllNamespaces(); it.hasNext();) {
            String namespace = it.next();
            cm.removeNamespace(namespace);
        }
        clearDB();

        // insert enums
        insertEnums();
    }

    /**
     * <p>
     * Inserts the enum value to the database.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    private void insertEnums() throws Exception {
        executeSQL(TEST_FILES + "enums.sql");
    }

    /**
     * <p>
     * Tears down the test environment.
     * </p>
     * @throws Exception to JUnit.
     */
    @After
    public void tearDown() throws Exception {
        clearDB();
        if (entityManager.isOpen()) {
            entityManager.close();
        }
        entityManager = null;
    }

    /**
     * <p>
     * Gets the spring ApplicationContext for unit tests.
     * </p>
     * @return to ApplicationContext instance.
     */
    protected ApplicationContext getApplicationContext() {
        return ctx;
    }

    /**
     * <p>
     * Creates a list of users for unit tests.
     * </p>
     * @return the list of created users.
     */
    protected List<User> createUsers() {
        return this.createUsers(10);
    }

    /**
     * <p>
     * Persists an object.
     * </p>
     * @param obj the object to persist.
     * @param <T> the type of the object.
     */
    protected <T> void persist(T obj) {
        entityManager.getTransaction().begin();
        entityManager.persist(obj);
        entityManager.getTransaction().commit();
    }

    /**
     * <p>
     * Clears the database.
     * </p>
     * @throws Exception to JUnit.
     */
    private void clearDB() throws Exception {
        executeSQL(TEST_FILES + "clear.sql");
    }

    /**
     * <p>
     * Executes the SQL statements in the file.
     * </p>
     * @param file the file.
     * @throws Exception to JUnit.
     */
    private void executeSQL(String file) throws Exception {
        EntityManagerFactory emf = (EntityManagerFactory) ctx.getBean("entityManagerFactory");
        EntityManager em =  emf.createEntityManager();
        em.getTransaction().begin();

        final String[] values = readFile(file).split(";");

        for (int i = 0; i < values.length; i++) {
            final String sql = values[i].trim();
            if ((sql.length() != 0) && (!sql.startsWith("#"))) {
                em.createNativeQuery(sql).executeUpdate();
            }
        }

        em.getTransaction().commit();
        if (em.isOpen()) {
            em.close();
        }
        em = null;
    }

    /**
     * <p>
     * Reads the content of a given file.
     * </p>
     * @param fileName the name of the file to read.
     * @return a string represents the content.
     * @throws IOException if any error occurs during reading.
     */
    protected static String readFile(String fileName) throws IOException {
        final Reader reader = new FileReader(fileName);

        try {
            // Create a StringBuilder instance
            final StringBuilder sb = new StringBuilder();

            // Buffer for reading
            final char[] buffer = new char[1024];

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
            } catch (final IOException ioe) {
                // Ignore
            }
        }
    }

    /**
     * <p>
     * Gets the entity manager.
     * </p>
     * @return the entity manager.
     */
    protected EntityManager getEntityManager() {
        return entityManager;
    }

    /**
     * <p>
     * Creates the users.
     * </p>
     * @param userNum the number of the users.
     * @return the created users.
     */
    protected List<User> createUsers(int userNum) {
        List<User> result = new ArrayList<User>();
        for (int i = 0; i < userNum; i++) {
            String handle = "tcs" + (i + 1);
            String address = handle + "@126.com";
            User user = new User();
            user.setEmailAddress(address);
            user.setHandle(handle);
            persist(user);
            result.add(user);
        }
        return result;
    }

    /**
     * <p>
     * Gets value for field of given object.
     * </p>
     * @param obj the given object.
     * @param field the field name.
     * @return the field value.
     */
    protected Object getField(Object obj, String field) {
        Object value = null;
        try {
            Field declaredField = null;
            try {
                declaredField = obj.getClass().getDeclaredField(field);
            } catch (final NoSuchFieldException e) {
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
        } catch (final IllegalAccessException e) {
            // Ignore
        } catch (final NoSuchFieldException e) {
            // Ignore
        }
        if (value == null) {
            throw new IllegalStateException("Failed to get the field:" + field);
        }
        return value;
    }

}
