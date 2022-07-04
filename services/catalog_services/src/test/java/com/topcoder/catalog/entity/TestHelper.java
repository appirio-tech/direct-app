/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.catalog.entity;

import org.mockejb.jndi.MockContextFactory;
import org.mockejb.MockContainer;
import org.mockejb.SessionBeanDescriptor;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.topcoder.util.idgenerator.ejb.IDGeneratorHome;
import com.topcoder.util.idgenerator.ejb.IDGenerator;
import com.topcoder.util.idgenerator.ejb.IDGeneratorBean;

/**
 * <p>This is helper class containing certain widely used methods.</p>
 *
 * @author Retunsky
 * @version 1.0
 */
public class TestHelper {
    /**
     * <p>Represents the EntityManager.</p>
     */
    private static EntityManager entityManager;
    /**
     * <p>Represents the EntityTransaction.
     */
    private static EntityTransaction entityTransaction;

    /**
     * <p>Private constructor to prevent from direct instantiation.</p>
     */
    private TestHelper() {
    }

    /**
     * <p>Prepares tables for tests (fills with test data).</p>
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
     * <p>Clears tables used in tests.</p>
     *
     * @throws Exception when it occurs deeper
     */
    public static void clearTables() throws Exception {
        getEntityManager().clear();
        runSQL("clear.sql");
        getEntityManager().clear();
    }

    /**
     * <p>Getter to the entityTransaction.</p>
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
     * <p>Getter to the entityManager.</p>
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
     * <p>Gets the file content as string.</p>
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
     * <p>Runs the sql statements in the given file.</p>
     *
     * @param file sql file to be executed.
     * @throws Exception when it occurs deeper
     */
    public static void runSQL(String file) throws Exception {
        final EntityTransaction tx = getEntityTransaction();
        boolean transaction = !tx.isActive();
        file = "test_files" + File.separator + "sql" + File.separator + file;
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
     * <p>Returns a date by string representation.</p>
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
     * <p>Executes one sql statement.</p>
     *
     * @param query String representing query to execute in db
     */
    public static void executeUpdate(String query) {
        getEntityManager().createNativeQuery(query).executeUpdate();
    }

    /**
     * <p>Closes resources.</p>
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
     * <p>Binds mockup <code>IDGeneratorHome</code>'s home.</p>
     */
    public static void bindIdGenerator() {



        try {
            MockContextFactory.setAsInitial();
            Context context = new InitialContext();

            MockContainer container = new MockContainer(context);

            SessionBeanDescriptor descriptor = new SessionBeanDescriptor("IDGeneratorBean/home",
                IDGeneratorHome.class, IDGenerator.class, new IDGeneratorBean());
            container.deploy(descriptor);
//            // put object to JNDI
//            Context ctx = new InitialContext();
//            unbindIdGenerator();
//            String path1 = "java:comp";
//            String path2 = "ejb";
//            String path3 = "IDGeneratorHome";
//            ctx = ctx.createSubcontext(path1);
//            ctx = ctx.createSubcontext(path2);
//            ctx.bind(path3, new MockRemoteHome());
        } catch (NamingException e) {
            throw new IllegalStateException(
                "Cannot bind 'IDGeneratorHome'. Check the configuration. "
                    + "The nested exception is: " + e.getMessage(), e);
        }
    }

    /**
     * <p>Unbinds <code>IDGeneratorHome</code> bean from JNDI.</p>
     */
    public static void unbindIdGenerator() {
        MockContextFactory.revertSetAsInitial();
//        try {
//            final InitialContext ctx = new InitialContext();
//            ctx.unbind("java:comp/ejb/IDGeneratorHome");
//            ctx.unbind("java:comp/ejb");
//            ctx.unbind("java:comp");
//        } catch (NamingException e) {
//            // ignore, as it was not bound
//        }
    }
}
