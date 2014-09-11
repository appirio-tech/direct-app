/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project.persistence.failuretests;

import java.io.File;
import java.sql.Connection;

import junit.framework.Assert;

import com.topcoder.management.project.ConfigurationException;
import com.topcoder.management.project.PersistenceException;

import com.topcoder.management.project.persistence.InformixProjectPersistence;

/**
 * A failure test for {@link InformixProjectPersistence}.
 *
 * @author isv, kshatriyan
 * @version 1.1
 * @since 1.0
 *
 */

public class InformixProjectPersistenceTest extends AbstractInformixProjectPersistenceTest {

    /**
     * Test instance used for failure test cases.
     */
    private MyManagedPersistence projectPersistence;

    /**
     * An instance used for testing.
     */
    private InformixProjectPersistence informixProjectPersistence;

    /**
     * <p>
     * Sets up the fixture. This method is called before a test is executed.
     * </p>
     *
     * @throws Exception
     *             if any error occurs.
     */
    protected void setUp() throws Exception {
        ConfigHelper.releaseNamespaces();
        ConfigHelper.loadConfiguration(new File("failure/config.xml"));
        super.setUp();
        setProjectPersistence(new InformixProjectPersistence(
                "com.topcoder.management.project.persistence.failuretests"));
    }

    /**
     * <p>
     * Tears down the fixture. This method is called after a test is executed.
     * </p>
     *
     * @throws Exception
     *             if any error occurs.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * <p>
     * Failure test. Tests the {@link InformixProjectPersistence#InformixProjectPersistence(String)} constructor for
     * proper handling the invalid input arguments.
     * </p>
     *
     * <p>
     * Passes <code>null</code> as <code>namespace</code> and expects the <code>IllegalArgumentException</code>
     * to be thrown.
     * </p>
     */
    public void testConstructor_String_namespace_null() {
        try {
            new InformixProjectPersistence(null);
            Assert.fail("IllegalArgumentException should have been thrown");
        } catch (IllegalArgumentException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("IllegalArgumentException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>
     * Failure test. Tests the {@link InformixProjectPersistence#InformixProjectPersistence(String)} constructor for
     * proper handling the invalid input arguments.
     * </p>
     *
     * <p>
     * Passes {@link TestDataFactory#ZERO_LENGTH_STRING} as <code>namespace</code> and expects the
     * <code>IllegalArgumentException</code> to be thrown.
     * </p>
     */
    public void testConstructor_String_namespace_ZERO_LENGTH_STRING() {
        try {
            new InformixProjectPersistence(TestDataFactory.ZERO_LENGTH_STRING);
            Assert.fail("IllegalArgumentException should have been thrown");
        } catch (IllegalArgumentException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("IllegalArgumentException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>
     * Failure test. Tests the {@link InformixProjectPersistence#InformixProjectPersistence(String)} constructor for
     * proper handling the invalid input arguments.
     * </p>
     *
     * <p>
     * Passes {@link TestDataFactory#WHITESPACE_ONLY_STRING} as <code>namespace</code> and expects the
     * <code>IllegalArgumentException</code> to be thrown.
     * </p>
     */
    public void testConstructor_String_namespace_WHITESPACE_ONLY_STRING() {
        try {
            new InformixProjectPersistence(TestDataFactory.WHITESPACE_ONLY_STRING);
            Assert.fail("IllegalArgumentException should have been thrown");
        } catch (IllegalArgumentException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("IllegalArgumentException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>
     * Failure test. Tests the {@link InformixProjectPersistence#InformixProjectPersistence(String)} constructor for
     * proper behavior if the configuration is invalid.
     * </p>
     *
     * <p>
     * Removes the <code>ConnectionFactoryNS</code> configuration property from the configuration namespace and
     * expects the <code>PersistenceException</code> to be thrown.
     * </p>
     */
    public void testConstructor_MissingRequiredProperty_ConnectionFactoryNS() {
        String[] values = ConfigHelper.removeProperty("com.topcoder.management.project.persistence.failuretests",
                "ConnectionFactoryNS");
        try {
            new InformixProjectPersistence(TestDataFactory.NAMESPACE);
            Assert.fail("ConfigurationException should have been thrown");
        } catch (ConfigurationException e) {
            // expected behavior
        } catch (Exception e) {
            fail("ConfigurationException was expected but the original exception is : " + e);
        } finally {
            ConfigHelper.restoreProperty("com.topcoder.management.project.persistence.failuretests",
                    "ConnectionFactoryNS", values);
        }
    }

    /**
     * <p>
     * Failure test. Tests the {@link InformixProjectPersistence#InformixProjectPersistence(String)} constructor for
     * proper behavior if the configuration is invalid.
     * </p>
     *
     * <p>
     * Replaces the <code>ConnectionFactoryNS</code> configuration property with invalid value and expects the
     * <code>ConfigurationException</code> to be thrown.
     * </p>
     */
    public void testConstructor_InvalidProperty_ConnectionFactoryNS() {
        String[] values = ConfigHelper.setProperty("com.topcoder.management.project.persistence.failuretests",
                "ConnectionFactoryNS", "UnknownNamespace");
        try {
            new InformixProjectPersistence(TestDataFactory.NAMESPACE);
            Assert.fail("ConfigurationException should have been thrown");
        } catch (ConfigurationException e) {
            // expected behavior
        } catch (Exception e) {
            fail("ConfigurationException was expected but the original exception is : " + e);
        } finally {
            ConfigHelper.restoreProperty("com.topcoder.management.project.persistence.failuretests",
                    "ConnectionFactoryNS", values);
        }
    }

    /**
     * <p>
     * Failure test. Tests the {@link InformixProjectPersistence#InformixProjectPersistence(String)} constructor for
     * proper behavior if the configuration is invalid.
     * </p>
     *
     * <p>
     * Replaces the <code>ProjectIdSequenceName</code> configuration property with invalid value and expects the
     * <code>PersistenceException</code> to be thrown.
     * </p>
     */
    public void testConstructor_InvalidProperty_ProjectIdSequenceName() {
        String[] values = ConfigHelper.setProperty("com.topcoder.management.project.persistence.failuretests",
                "ProjectIdSequenceName", "UnknownSequence");
        try {
            new InformixProjectPersistence(TestDataFactory.NAMESPACE);
            Assert.fail("PersistenceException should have been thrown");
        } catch (PersistenceException e) {
            // expected behavior
        } catch (Exception e) {
            fail("PersistenceException was expected but the original exception is : " + e);
        } finally {
            ConfigHelper.restoreProperty("com.topcoder.management.project.persistence.failuretests",
                    "ProjectIdSequenceName", values);
        }
    }

    /**
     * <p>
     * Failure test. Tests the {@link InformixProjectPersistence#InformixProjectPersistence(String)} constructor for
     * proper behavior if the configuration is invalid.
     * </p>
     *
     * <p>
     * Replaces the <code>ProjectAuditIdSequenceName</code> configuration property with invalid value and expects
     * the <code>PersistenceException</code> to be thrown.
     * </p>
     */
    public void testConstructor_InvalidProperty_ProjectAuditIdSequenceName() {
        String[] values = ConfigHelper.setProperty("com.topcoder.management.project.persistence.failuretests",
                "ProjectAuditIdSequenceName", "UnknownSequence");
        try {
            new InformixProjectPersistence(TestDataFactory.NAMESPACE);
            Assert.fail("PersistenceException should have been thrown");
        } catch (PersistenceException e) {
            // expected behavior
        } catch (Exception e) {
            fail("PersistenceException was expected but the original exception is : " + e);
        } finally {
            ConfigHelper.restoreProperty("com.topcoder.management.project.persistence.failuretests",
                    "ProjectAuditIdSequenceName", values);
        }
    }

    /**
     * <p>
     * Failure test. Tests the <code>InformixProjectPersistence#openconnection()</code> method for proper handling
     * the invalid input arguments.
     * </p>
     *
     * <p>
     * Passes Invalid_ConnectionName as <code>namespace</code> and expects the <code>PersistenceException</code>
     * to be thrown while opening the connection.
     * </p>
     */
    public void testOpenConnection() {
        try {
            projectPersistence = new MyManagedPersistence("Invalid_ConnectionName");
            projectPersistence.openConnection();
            Assert.fail("PersistenceException should have been thrown");
        } catch (PersistenceException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("PersistenceException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>
     * Failure test. Tests the <code>InformixProjectPersistence#closeConnection()</code> method for proper handling
     * the invalid input arguments.
     * </p>
     *
     * <p>
     * Passes connection as null and expects the <code>IllegalArgumentException</code> to be thrown.
     * </p>
     */
    public void testCloseConnection() {
        try {
            projectPersistence = new MyManagedPersistence("com.topcoder.management.project.persistence.failuretests");
            projectPersistence.closeConnection(null);
            Assert.fail("IllegalArgumentException should have been thrown");
        } catch (IllegalArgumentException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("IllegalArgumentException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>
     * Failure test. Tests the <code>InformixProjectPersistence#closeConnectionOnError()</code> method for proper
     * handling the invalid input arguments.
     * </p>
     *
     * <p>
     * Passes connection as null and expects the <code>IllegalArgumentException</code> to be thrown.
     * </p>
     */
    public void testCloseConnectionOnError() {
        try {
            projectPersistence = new MyManagedPersistence("com.topcoder.management.project.persistence.failuretests");
            projectPersistence.closeConnectionOnError(null);
            Assert.fail("IllegalArgumentException should have been thrown");
        } catch (IllegalArgumentException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("IllegalArgumentException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>
     * Failure test. Tests the {@link InformixProjectPersistence#getAllProjectCategories()} method for proper behavior
     * if the SQL error occurs while communicating to DB.
     * </p>
     *
     * <p>
     * Replaces the <code>ConnectionName</code> configuration property with invalid value and expects the
     * <code>PersistenceException</code> to be thrown.
     * </p>
     */
    public void testGetAllProjectCategories_DatabaseError() {
        String[] values = ConfigHelper.setProperty("com.topcoder.management.project.persistence.failuretests",
                "ConnectionName", "invalid");
        try {
            informixProjectPersistence = new InformixProjectPersistence(TestDataFactory.NAMESPACE);
            informixProjectPersistence.getAllProjectCategories();
            Assert.fail("PersistenceException should have been thrown");
        } catch (PersistenceException e) {
            // expected behavior
        } catch (Exception e) {
            fail("PersistenceException was expected but the original exception is : " + e);
        } finally {
            ConfigHelper.restoreProperty("com.topcoder.management.project.persistence.failuretests",
                    "ConnectionName", values);
        }
    }

    /**
     * <p>
     * Failure test. Tests the {@link InformixProjectPersistence#getAllProjectPropertyTypes()} method for proper
     * behavior if the SQL error occurs while communicating to DB.
     * </p>
     *
     * <p>
     * Replaces the <code>ConnectionName</code> configuration property with invalid value and expects the
     * <code>PersistenceException</code> to be thrown.
     * </p>
     */
    public void testGetAllProjectPropertyTypes_DatabaseError() {
        String[] values = ConfigHelper.setProperty("com.topcoder.management.project.persistence.failuretests",
                "ConnectionName", "invalid");
        try {
            informixProjectPersistence = new InformixProjectPersistence(TestDataFactory.NAMESPACE);
            informixProjectPersistence.getAllProjectPropertyTypes();
            Assert.fail("PersistenceException should have been thrown");
        } catch (PersistenceException e) {
            // expected behavior
        } catch (Exception e) {
            fail("PersistenceException was expected but the original exception is : " + e);
        } finally {
            ConfigHelper.restoreProperty("com.topcoder.management.project.persistence.failuretests",
                    "ConnectionName", values);
        }
    }

    /**
     * <p>
     * Failure test. Tests the {@link InformixProjectPersistence#getAllProjectStatuses()} method for proper behavior
     * if the SQL error occurs while communicating to DB.
     * </p>
     *
     * <p>
     * Replaces the <code>ConnectionName</code> configuration property with invalid value and expects the
     * <code>PersistenceException</code> to be thrown.
     * </p>
     */
    public void testGetAllProjectStatuses_DatabaseError() {
        String[] values = ConfigHelper.setProperty("com.topcoder.management.project.persistence.failuretests",
                "ConnectionName", "invalid");
        try {
            informixProjectPersistence = new InformixProjectPersistence(TestDataFactory.NAMESPACE);
            informixProjectPersistence.getAllProjectStatuses();
            Assert.fail("PersistenceException should have been thrown");
        } catch (PersistenceException e) {
            // expected behavior
        } catch (Exception e) {
            fail("PersistenceException was expected but the original exception is : " + e);
        } finally {
            ConfigHelper.restoreProperty("com.topcoder.management.project.persistence.failuretests",
                    "ConnectionName", values);
        }
    }

    /**
     * <p>
     * Failure test. Tests the {@link InformixProjectPersistence#getAllProjectTypes()} method for proper behavior if
     * the SQL error occurs while communicating to DB.
     * </p>
     *
     * <p>
     * Replaces the <code>ConnectionName</code> configuration property with invalid value and expects the
     * <code>PersistenceException</code> to be thrown.
     * </p>
     */
    public void testGetAllProjectTypes_DatabaseError() {
        String[] values = ConfigHelper.setProperty("com.topcoder.management.project.persistence.failuretests",
                "ConnectionName", "invalid");
        try {
            informixProjectPersistence = new InformixProjectPersistence(TestDataFactory.NAMESPACE);
            informixProjectPersistence.getAllProjectTypes();
            Assert.fail("PersistenceException should have been thrown");
        } catch (PersistenceException e) {
            // expected behavior
        } catch (Exception e) {
            fail("PersistenceException was expected but the original exception is : " + e);
        } finally {
            ConfigHelper.restoreProperty("com.topcoder.management.project.persistence.failuretests",
                    "ConnectionName", values);
        }
    }

    /**
     * <p>
     * Failure test. Tests the {@link InformixProjectPersistence#updateProject(Project, String, String)} method for
     * proper behavior if the SQL error occurs while communicating to DB.
     * </p>
     *
     * <p>
     * Replaces the <code>ConnectionName</code> configuration property with invalid value and expects the
     * <code>PersistenceException</code> to be thrown.
     * </p>
     */
    public void testUpdateProject_DatabaseError() {
        String[] values = ConfigHelper.setProperty("com.topcoder.management.project.persistence.failuretests",
                "ConnectionName", "invalid");
        try {
            informixProjectPersistence = new InformixProjectPersistence(TestDataFactory.NAMESPACE);
            informixProjectPersistence.updateProject(TestDataFactory.getProject(), TestDataFactory.OPERATOR,
                    TestDataFactory.REASON);
            Assert.fail("PersistenceException should have been thrown");
        } catch (PersistenceException e) {
            // expected behavior
        } catch (Exception e) {
            fail("PersistenceException was expected but the original exception is : " + e);
        } finally {
            ConfigHelper.restoreProperty("com.topcoder.management.project.persistence.failuretests",
                    "ConnectionName", values);
        }
    }

    /**
     * <p>
     * Failure test. Tests the {@link InformixProjectPersistence#createProject(Project, String)} method for proper
     * behavior if the SQL error occurs while communicating to DB.
     * </p>
     *
     * <p>
     * Replaces the <code>ConnectionName</code> configuration property with invalid value and expects the
     * <code>PersistenceException</code> to be thrown.
     * </p>
     */
    public void testCreateProject_DatabaseError() {
        String[] values = ConfigHelper.setProperty("com.topcoder.management.project.persistence.failuretests",
                "ConnectionName", "invalid");
        try {
            informixProjectPersistence = new InformixProjectPersistence(TestDataFactory.NAMESPACE);
            informixProjectPersistence.createProject(TestDataFactory.getProject(), TestDataFactory.OPERATOR);
            Assert.fail("PersistenceException should have been thrown");
        } catch (PersistenceException e) {
            // expected behavior
        } catch (Exception e) {
            fail("PersistenceException was expected but the original exception is : " + e);
        } finally {
            ConfigHelper.restoreProperty("com.topcoder.management.project.persistence.failuretests",
                    "ConnectionName", values);
        }
    }

    /**
     * Helper class used for testing the protected methods of {@link InformixProjectPersistence}.
     *
     * @author kshatriyan
     * @version 1.1
     */
    private class MyManagedPersistence extends InformixProjectPersistence {

        /**
         * <p>
         * Creates a new MyManagedPersistence.
         * </p>
         *
         *
         * @param namespace
         *            The namespace to load connection setting.
         *
         * @throws IllegalArgumentException
         *             if the namespace is null or empty string.
         * @throws ConfigurationException
         *             if error occurs while loading configuration settings, or required configuration parameter is
         *             missing.
         * @throws PersistenceException
         *             if cannot initialize the connection to the database.
         */
        public MyManagedPersistence(String namespace) throws ConfigurationException, PersistenceException {
            super(namespace);
        }

        /**
         * <p>
         * Invokes <code>closeConnection()</code> of {@link InformixProjectPersistence}.
         * </p>
         *
         *
         * @param connection
         *            the connection to close.
         *
         * @throws IllegalArgumentException
         *             if the connection is null.
         * @throws PersistenceException
         *             if any problem occurs trying to close the connection.
         */
        public void closeConnection(Connection connection) throws PersistenceException {
            super.closeConnection(connection);
        }

        /**
         * <p>
         * Invokes <code>closeConnectionOnError()</code> of {@link InformixProjectPersistence}.
         * </p>
         *
         * @param connection
         *            a connection to close
         *
         * @throws IllegalArgumentException
         *             if the connection is null
         * @throws PersistenceException
         *             if any problem occurs trying to close the connection
         */
        public void closeConnectionOnError(Connection connection) throws PersistenceException {
            super.closeConnectionOnError(connection);
        }

        /**
         * <p>
         * Invokes <code>openConnection()</code> of {@link InformixProjectPersistence}.
         * </p>
         *
         * @return an open Connection.
         *
         * @throws PersistenceException
         *             if there's a problem getting the Connection.
         */
        public Connection openConnection() throws PersistenceException {
            return super.openConnection();
        }

    }

}
