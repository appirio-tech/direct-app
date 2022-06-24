/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project.persistence.failuretests;

import java.io.File;
import java.sql.Connection;

import junit.framework.Assert;
import junit.framework.TestCase;

import com.topcoder.management.project.ConfigurationException;
import com.topcoder.management.project.PersistenceException;
import com.topcoder.management.project.persistence.AbstractInformixProjectPersistence;
import com.topcoder.util.log.Log;

/**
 * Failure test cases for {@link AbstractInformixProjectPersistence} constructor. Most of the test cases are from
 * {@link InformixProjectPersistenceTest} version 1.0.
 *
 * @author isv, kshatriyan
 * @version 1.1
 */
public class AbstractInformixProjectPersistenceCtorTest extends TestCase {

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
        ConfigHelper.releaseNamespaces();
    }

    /**
     * <p>
     * Failure test. Tests the {@link AbstractInformixProjectPersistence#AbstractInformixProjectPersistence(String)}
     * constructor for proper handling the invalid input arguments.
     * </p>
     *
     * <p>
     * Passes <code>null</code> as <code>namespace</code> and expects the <code>IllegalArgumentException</code>
     * to be thrown.
     * </p>
     */
    public void testConstructor_String_namespace_null() {
        try {
            new ProjectPersistenceForFailure(null);
            Assert.fail("IllegalArgumentException should have been thrown");
        } catch (IllegalArgumentException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("IllegalArgumentException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>
     * Failure test. Tests the {@link AbstractInformixProjectPersistence#AbstractInformixProjectPersistence(String)}
     * constructor for proper handling the invalid input arguments.
     * </p>
     *
     * <p>
     * Passes {@link TestDataFactory#ZERO_LENGTH_STRING} as <code>namespace</code> and expects the
     * <code>IllegalArgumentException</code> to be thrown.
     * </p>
     */
    public void testConstructor_String_namespace_ZERO_LENGTH_STRING() {
        try {
            new ProjectPersistenceForFailure(TestDataFactory.ZERO_LENGTH_STRING);
            Assert.fail("IllegalArgumentException should have been thrown");
        } catch (IllegalArgumentException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("IllegalArgumentException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>
     * Failure test. Tests the {@link AbstractInformixProjectPersistence#AbstractInformixProjectPersistence(String)}
     * constructor for proper handling the invalid input arguments.
     * </p>
     *
     * <p>
     * Passes {@link TestDataFactory#WHITESPACE_ONLY_STRING} as <code>namespace</code> and expects the
     * <code>IllegalArgumentException</code> to be thrown.
     * </p>
     */
    public void testConstructor_String_namespace_WHITESPACE_ONLY_STRING() {
        try {
            new ProjectPersistenceForFailure(TestDataFactory.WHITESPACE_ONLY_STRING);
            Assert.fail("IllegalArgumentException should have been thrown");
        } catch (IllegalArgumentException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("IllegalArgumentException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>
     * Failure test. Tests the {@link AbstractInformixProjectPersistence#AbstractInformixProjectPersistence(String)}
     * constructor for proper behavior if the configuration is invalid.
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
            new ProjectPersistenceForFailure(TestDataFactory.NAMESPACE);
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
     * Failure test. Tests the {@link AbstractInformixProjectPersistence#AbstractInformixProjectPersistence(String)}
     * constructor for proper behavior if the configuration is invalid.
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
            new ProjectPersistenceForFailure(TestDataFactory.NAMESPACE);
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
     * Failure test. Tests the {@link AbstractInformixProjectPersistence#AbstractInformixProjectPersistence(String)}
     * constructor for proper behavior if the configuration is invalid.
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
            new ProjectPersistenceForFailure(TestDataFactory.NAMESPACE);
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
     * Failure test. Tests the {@link AbstractInformixProjectPersistence#AbstractInformixProjectPersistence(String)}
     * constructor for proper behavior if the configuration is invalid.
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
            new ProjectPersistenceForFailure(TestDataFactory.NAMESPACE);
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
     * Mock implementation of {@link AbstractInformixProjectPersistence}. Used for testing the constructor of the
     * same.
     *
     * @author kshatriyan
     * @version 1.1
     *
     */
    private class ProjectPersistenceForFailure extends AbstractInformixProjectPersistence {

        /**
         * <p>
         * Creates a new ProjectPersistenceForFailure.
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
        public ProjectPersistenceForFailure(String namespace) throws ConfigurationException, PersistenceException {
            super(namespace);
        }

        /**
         * <p>
         * Does nothing.
         * </p>
         *
         *
         * @param connection
         *            the connection to close.
         *
         * @throws IllegalArgumentException
         *             Not thrown.
         * @throws PersistenceException
         *             Not thrown.
         */
        public void closeConnection(Connection connection) throws PersistenceException {
        }

        /**
         * <p>
         * Does nothing.
         * </p>
         *
         *
         * @param connection
         *            the connection to close.
         *
         * @throws IllegalArgumentException
         *             Not thrown.
         * @throws PersistenceException
         *             Not thrown.
         */
        public void closeConnectionOnError(Connection connection) throws PersistenceException {
        }

        /**
         * <p>
         * Returns null always.
         * </p>
         *
         * @return null always.
         *
         * @throws PersistenceException
         *             Not thrown.
         */
        public Connection openConnection() throws PersistenceException {
            return null;
        }
        /**
         * <p>
         * Returns null always.
         * </p>
         *
         * @return null always.
         */
		protected Log getLogger() {
			return null;
		}

    }
}
