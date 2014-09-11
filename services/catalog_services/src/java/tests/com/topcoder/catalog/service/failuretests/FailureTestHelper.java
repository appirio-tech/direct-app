/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.catalog.service.failuretests;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;

import com.topcoder.catalog.service.CatalogService;
import com.topcoder.catalog.service.CatalogServiceImpl;

/**
 * <p>
 * This is helper class containing certain widely used methods.
 * </p>
 *
 * @author kaqi072821
 * @version 1.0
 */
public class FailureTestHelper {
    /**
     * <p>
     * This constant indicates that tests are being executed against mockups.
     * </p>
     * <p>
     * If this constant has value <code>false</code> then {@link #getCatalogService} ()} returns looked up remote bean
     * from JNDI.
     * </p>
     */
    static final boolean EXECUTE_AGAINST_MOCKS = Boolean.valueOf(System.getProperty("test.against.mocks", "true"));

    /**
     * <p>
     * <code>CatalogService</code> JNDI name.
     * </p>
     */
    static final String SERVICE_JNDI = "CatalogService/remote";

    /**
     * <p>
     * Represents the EntityManager.
     * </p>
     */
    private static EntityManager entityManager;

    /**
     * <p>
     * Represents the EntityTransaction.
     */
    private static EntityTransaction entityTransaction;

    /**
     * <p>
     * Private constructor to prevent from direct instantiation.
     * </p>
     */
    private FailureTestHelper() {
    }

    /**
     * <p>
     * Prepares tables for tests (fills with test data).
     * </p>
     *
     * @throws Exception when it occurs deeper
     */
    public static void prepareTables() throws Exception {
        getEntityManager().clear();
        runSQL("clear.sql");
        runSQL("test_data.sql");
        getEntityManager().clear();
    }

    /**
     * <p>
     * Clears tables used in tests.
     * </p>
     *
     * @throws Exception when it occurs deeper
     */
    public static void clearTables() throws Exception {
        getEntityManager().clear();
        runSQL("clear.sql");
        getEntityManager().clear();
    }

    /**
     * <p>
     * Getter to the entityTransaction.
     * </p>
     *
     * @return instance of current entityTransaction
     */
    public static EntityTransaction getEntityTransaction() {
        if (entityTransaction == null) {
            entityTransaction = getEntityManager().getTransaction();
        }
        return entityTransaction;
    }

    /**
     * <p>
     * Getter to the entityManager.
     * </p>
     *
     * @return instance of current entityManager
     */
    public static EntityManager getEntityManager() {
        if (entityManager == null) {
            entityManager = Persistence.createEntityManagerFactory("catalog_manager").createEntityManager();
        }
        return entityManager;
    }

    /**
     * <p>
     * Gets the file content as string.
     * </p>
     *
     * @param file The file to get its content.
     * @return The content of file.
     * @throws Exception to JUnit
     */
    private static String getFileAsString(String file) throws Exception {
        StringBuffer buf = new StringBuffer();
        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        while (true) {
            String s = in.readLine();
            if (s == null) {
                break;
            }
            buf.append(s);
        }
        in.close();
        return buf.toString();
    }

    /**
     * <p>
     * Runs the sql statements in the given file.
     * </p>
     *
     * @param file sql file to be executed.
     * @throws Exception when it occurs deeper
     */
    public static void runSQL(String file) throws Exception {
        final EntityTransaction tx = getEntityTransaction();
        boolean transaction = !tx.isActive();
        file = "test_files" + File.separator + "failuretests" + File.separator + "sql" + File.separator + file;
        EntityManager em = getEntityManager();
        try {
            String content = getFileAsString(file);
            if (transaction) {
                tx.begin();
            }

            final String[] statements = content.split(";");
            for (String statement : statements) {
                if (statement.trim().startsWith("@")) {
                    runSQL(statement.trim().substring(1));
                } else if (!statement.trim().startsWith("--") && !statement.trim().equalsIgnoreCase("exit")) {
                    em.createNativeQuery(statement).executeUpdate();
                }
            }

            if (transaction) {
                tx.commit();
            }
        } catch (PersistenceException e) {
            if (transaction) {
                tx.rollback();
            }
            throw e;
        }
    }

    /**
     * <p>
     * Returns a date by string representation.
     * </p>
     *
     * @param dateAsString date in format <tt>dd/MM/yyyy</tt>
     * @return date by given string
     * @throws java.text.ParseException if it occurs during parsing
     */
    public static Date getDate(String dateAsString) throws ParseException {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return df.parse(dateAsString);
    }

    /**
     * <p>
     * Executes one sql statement.
     * </p>
     *
     * @param query String representing query to execute in db
     */
    public static void executeUpdate(String query) {
        getEntityManager().createNativeQuery(query).executeUpdate();
    }

    /**
     * <p>
     * Closes resources.
     * </p>
     */
    public static void close() {
        try {
            if (entityTransaction != null && entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
        } catch (PersistenceException e) {
            // ignore
        } finally {
            entityTransaction = null;
        }
    }

    /**
     * <p>
     * Returns <code>CatalogService</code>'s mockup, if {@link #EXECUTE_AGAINST_MOCKS} or <code>failTest</code> is
     * set to true. Does nothing otherwise.
     * </p>
     *
     * @return if {@link #EXECUTE_AGAINST_MOCKS} is true or <code>failTest</code> is true then local
     *         <code>MockupCatalogService</code> instance, else looked up CatalogService (deployed in an EAR into
     *         JBoss).
     * @param failTest set <code>true</code> to inject <code>FailTestEntityManager</code>, <code>false</code> for
     *        accuracy testing
     */
    public static CatalogService getCatalogService(boolean failTest) {
        if (EXECUTE_AGAINST_MOCKS || failTest) {
            return new CatalogServiceFailureMock(new CatalogServiceImpl(), failTest);
        } else {
            try {
                // get object from JNDI
                Context context = new InitialContext();
                return (CatalogService) context.lookup(SERVICE_JNDI);
            } catch (NamingException e) {
                throw new IllegalStateException(
                    "Cannot lookup 'CatalogService'. Check the configuration (Jboss is running, "
                        + "id_generator_ejb.jar and catalog_services_ejb.jar are deployed successfully). "
                        + "The nested exception is: " + e.getMessage(), e);
            }
        }
    }

    /**
     * <p>
     * Parses a date by its date string representation.
     * </p>
     *
     * @param dateAsString string representation of a date (in 'yyyy/MM/dd' format)
     * @return Date object parsed by SimpleDateFormat
     * @throws Exception to jUnit
     */
    public static Date parseDate(String dateAsString) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd");

        return df.parse(dateAsString);
    }
}