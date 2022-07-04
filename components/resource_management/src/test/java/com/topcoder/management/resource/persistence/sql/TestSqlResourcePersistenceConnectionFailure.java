/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource.persistence.sql;

/**
 * Unit test cases for class <code>SqlResourcePersistence </code>. In this test class, the connection
 * failure cases are tested.
 *
 * @author mittu
 * @version 1.1
 */
public class TestSqlResourcePersistenceConnectionFailure extends TestAbstractlResourcePersistenceConnectionFailure {

    /**
     * Set up the environment. Create SqlResourcePersistence instance for test.
     *
     * @throws Exception
     *             to junit.
     */
    public void setUp() throws Exception {
        super.setUp();
        persistence1 = new SqlResourcePersistence(factory, "wronguser");
        persistence2 = new SqlResourcePersistence(factory, "nonexistinguser");
    }

    /**
     * Tear down the environment. Clear all namespaces in the config manager.
     *
     * @throws Exception
     *             to junit.
     */
    public void tearDown() throws Exception {
        super.tearDown();
    }
}
