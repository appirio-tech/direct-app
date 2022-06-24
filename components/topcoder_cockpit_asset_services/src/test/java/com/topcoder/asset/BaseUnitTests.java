/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.asset;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Field;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.topcoder.asset.entities.AuditRecord;

/**
 * <p>
 * The base test case for Unit tests.
 * </p>
 *
 * @author sparemax
 * @version 1.0
 */
public abstract class BaseUnitTests {
    /**
     * <p>
     * Represents the empty string.
     * </p>
     */
    public static final String EMPTY_STRING = " \t ";

    /**
     * <p>
     * Represents the path of test files.
     * </p>
     */
    public static final String TEST_FILES = "test_files" + File.separator;

    /**
     * <p>
     * Represents the <code>ApplicationContext </code> for tests.
     * </p>
     */
    public static final ApplicationContext APP_CONTEXT;

    /**
     * <p>
     * Represents the <code>EntityManagerFactory</code> for tests.
     * </p>
     */
    private static EntityManagerFactory factory;

    /**
     * <p>
     * Represents the entity manager used in tests.
     * </p>
     */
    private EntityManager entityManager;

    /**
     * <p>
     * Initialization.
     * </p>
     */
    static {
        APP_CONTEXT = new ClassPathXmlApplicationContext("beans.xml");
    }

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @BeforeClass
    public static void setUpClass() throws Exception {
        factory = Persistence.createEntityManagerFactory("persistenceUnit");
    }

    /**
     * <p>
     * Cleans up the test environment.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @AfterClass
    public static void tearDownClass() throws Exception {
        factory.close();
        factory = null;
    }

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Before
    public void setUp() throws Exception {
        entityManager = factory.createEntityManager();

        clearDB();
    }

    /**
     * <p>
     * Cleans up the test environment.
     * </p>
     *
     * @throws Exception
     *             to JUnit
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
     * Checks the audit record.
     * </p>
     *
     * @param method
     *            the method.
     * @param values
     *            the expected values.
     */
    @SuppressWarnings("unchecked")
    protected void checkAuditRecord(String method, Object... values) {
        List<AuditRecord> list = entityManager.createQuery("SELECT e FROM AuditRecord e ORDER BY e.id DESC")
            .getResultList();

        AuditRecord auditRecord = list.get(0);

        int index = 0;

        assertNotNull("'" + method + "' should be correct.", auditRecord.getTimestamp());

        assertEquals("'" + method + "' should be correct.", values[index++], auditRecord.getUserId());
        assertEquals("'" + method + "' should be correct.", values[index++], auditRecord.getAction());
        assertEquals("'" + method + "' should be correct.", values[index++], auditRecord.getEntityType());
        assertEquals("'" + method + "' should be correct.", values[index++], auditRecord.getEntityId());

        boolean value = (Boolean) values[index++];
        if (value) {
            assertNull("'" + method + "' should be correct.", auditRecord.getOldValue());
        } else {
            assertNotNull("'" + method + "' should be correct.", auditRecord.getOldValue());
        }

        value = (Boolean) values[index];
        if (value) {
            assertNull("'" + method + "' should be correct.", auditRecord.getNewValue());
        } else {
            assertNotNull("'" + method + "' should be correct.", auditRecord.getNewValue());
        }
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
                try {
                    // From the superclass
                    declaredField = obj.getClass().getSuperclass().getDeclaredField(field);
                } catch (NoSuchFieldException e) {
                    // Ignore
                }
            }

            if (declaredField == null) {
                // From the superclass
                declaredField = obj.getClass().getSuperclass().getSuperclass().getDeclaredField(field);
            }

            declaredField.setAccessible(true);

            value = declaredField.get(obj);

            declaredField.setAccessible(false);
        } catch (IllegalArgumentException e) {
            // Ignore
        } catch (SecurityException e) {
            // Ignore
        } catch (IllegalAccessException e) {
            // Ignore
        } catch (NoSuchFieldException e) {
            // Ignore
        }

        return value;
    }

    /**
     * <p>
     * Gets the entity manager.
     * </p>
     *
     * @return the entity manager.
     *
     * @throws Exception
     *             to JUnit.
     */
    protected EntityManager getEntityManager() throws Exception {
        return entityManager;
    }

    /**
     * <p>
     * Clears the database.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    protected void clearDB() throws Exception {
        executeSQL(TEST_FILES + "clear.sql");
    }

    /**
     * <p>
     * Loads data into the database.
     * </p>
     *
     * @param misc
     *            true for Misc Services
     *
     * @throws Exception
     *             to JUnit.
     */
    protected void loadDB(boolean misc) throws Exception {
        if (misc) {
            executeSQL(TEST_FILES + "data-misc.sql");
        } else {
            executeSQL(TEST_FILES + "data.sql");
        }
    }

    /**
     * <p>
     * Executes the SQL statements in the file. Lines that are empty or starts with '#' will be ignore.
     * </p>
     *
     * @param file
     *            the file.
     *
     * @throws Exception
     *             to JUnit.
     */
    private void executeSQL(String file) throws Exception {
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();

        String[] values = readFile(file).split(";");

        for (int i = 0; i < values.length; i++) {
            String sql = values[i].trim();
            if ((sql.length() != 0) && (!sql.startsWith("#"))) {
                em.createNativeQuery(sql).executeUpdate();
            }
        }

        em.getTransaction().commit();
        em.close();
        em = null;
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