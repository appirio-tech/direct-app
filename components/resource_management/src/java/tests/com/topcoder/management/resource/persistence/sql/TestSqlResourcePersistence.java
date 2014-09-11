/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource.persistence.sql;

/**
 * Unit test cases for class <code>SqlResourcePersistence </code>. In this test class, the
 * functionality of this component will be tested.
 *
 * @author mittu
 * @version 1.1
 */
public class TestSqlResourcePersistence extends TestAbstractResourcePersistence {

    /**
     * Set up the environment. Create SqlResourcePersistence instance for test.
     *
     * @throws Exception
     *             to junit.
     */
    public void setUp() throws Exception {
        super.setUp();
        persistence1 = new SqlResourcePersistence(factory);
        persistence2 = new SqlResourcePersistence(factory, "sysuser");
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
