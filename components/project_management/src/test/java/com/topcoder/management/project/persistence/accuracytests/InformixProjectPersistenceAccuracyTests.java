/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project.persistence.accuracytests;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;
import com.topcoder.management.project.persistence.InformixProjectPersistence;

/**
 * <p>
 * Accuracy Unit test cases for InformixProjectPersistence.
 * </p>
 *
 * @author victorsam
 * @version 1.1
 */
public class InformixProjectPersistenceAccuracyTests extends TestCase {
    /**
     * <p>
     * InformixProjectPersistence instance for testing.
     * </p>
     */
    private InformixProjectPersistence instance;

    /**
     * <p>
     * Setup test environment.
     * </p>
     * @throws Exception to JUnit
     *
     */
    protected void setUp() throws Exception {
        AccuracyTestHelper.loadXMLConfig(AccuracyTestHelper.CONFIG_FILE);
        AccuracyTestHelper.tearDownDataBase();
        AccuracyTestHelper.setUpDataBase();

        instance = new InformixProjectPersistence("InformixProjectPersistence.CustomNamespace");
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     * @throws Exception to JUnit
     *
     */
    protected void tearDown() throws Exception {
        instance = null;

        AccuracyTestHelper.tearDownDataBase();
        AccuracyTestHelper.clearConfig();
    }

    /**
     * <p>
     * Return all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(InformixProjectPersistenceAccuracyTests.class);
    }

    /**
     * <p>
     * Tests ctor InformixProjectPersistence#InformixProjectPersistence(String) for accuracy.
     * </p>
     */
    public void testCtor() {
        assertNotNull("Failed to create InformixProjectPersistence instance.", instance);
    }

}