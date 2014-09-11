/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.catalog.entity.unittests.persistence;

import javax.persistence.EntityTransaction;

import com.topcoder.catalog.entity.CompVersion;
import com.topcoder.catalog.entity.Component;
import com.topcoder.catalog.entity.TestHelper;

import junit.framework.TestCase;

/**
 * <p>
 * Accuracy test the correct mapping of
 * </p>
 *
 * @author FireIce
 */
public class ComponentUnitTest extends TestCase {

    /**
     * Clears persistence context.
     *
     * @throws Exception
     *             if something goes wrong
     */
    protected void setUp() throws Exception {
        super.setUp();
        TestHelper.bindIdGenerator();
        final EntityTransaction tx = TestHelper.getEntityTransaction();
        if (tx != null && tx.isActive()) {
            tx.rollback();
        }
        TestHelper.clearTables();
        prepareTables();
    }

    /**
     * <p>
     * Prepares tables for tests (fills with test data).
     * </p>
     *
     * @throws Exception
     *             when it occurs deeper
     */
    public static void prepareTables() throws Exception {
        TestHelper.getEntityManager().clear();
        TestHelper.runSQL("test_data.sql");
        TestHelper.runSQL("new_data.sql");
        TestHelper.getEntityManager().clear();
    }

    /**
     * finally removes the entity.
     *
     * @throws Exception
     *             if something goes wrong
     */
    protected void tearDown() throws Exception {
        final EntityTransaction tx = TestHelper.getEntityTransaction();
        if (tx != null && tx.isActive()) {
            tx.rollback();
        }
        TestHelper.unbindIdGenerator();
        TestHelper.clearTables();
        super.tearDown();
    }

    /**
     * <p>
     * Verify the current version of a component is correctly mapped.
     * </p>
     */
    public void testMapping() {
        Component component = TestHelper.getEntityManager().find(Component.class, 100L);
        assertNotNull("Unable to retrieve componnet.", component);
        CompVersion compVersion = component.getCurrentVersion();
        assertNotNull("Unable to retrieve current version.", compVersion);
        assertEquals("Incorrect version", 1, compVersion.getVersion().longValue());
    }
}
