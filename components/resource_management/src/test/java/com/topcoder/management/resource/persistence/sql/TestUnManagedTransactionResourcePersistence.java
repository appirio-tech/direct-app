/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource.persistence.sql;

/**
 * Unit test cases for class <code>UnmanagedTransactionResourcePersistence </code>. In this test class, the
 * functionality of this component will be tested.
 *
 * @author mittu
 * @version 1.1
 */
public class TestUnManagedTransactionResourcePersistence extends TestAbstractResourcePersistence {

    /**
     * Set up the environment. Create UnmanagedTransactionResourcePersistence instance for test.
     *
     * @throws Exception
     *             to junit.
     */
    public void setUp() throws Exception {
        super.setUp();
        persistence1 = new UnmanagedTransactionResourcePersistence(factory);
        persistence2 = new UnmanagedTransactionResourcePersistence(factory, "sysuser");
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
