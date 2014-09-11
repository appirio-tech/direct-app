/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.asset.services.impl;

import static org.junit.Assert.assertEquals;

import javax.persistence.EntityManager;

import junit.framework.JUnit4TestAdapter;

import org.junit.Before;
import org.junit.Test;

import com.topcoder.asset.BaseUnitTests;
import com.topcoder.asset.entities.Asset;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;

/**
 * <p>
 * Unit tests for {@link Helper} class.
 * </p>
 *
 * @author sparemax
 * @version 1.0
 */
public class HelperUnitTests extends BaseUnitTests {
    /**
     * The log.
     */
    private Log log = LogManager.getLog(getClass().getName());

    /**
     * <p>
     * Adapter for earlier versions of JUnit.
     * </p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(HelperUnitTests.class);
    }

    /**
     * <p>
     * Sets up the unit tests.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        loadDB(false);
    }

    /**
     * <p>
     * Tests accuracy of <code>getEntity(Log log, String signature, String name, EntityManager entityManager,
     * Class&lt;T&gt; type, long id)</code>
     * method.<br>
     * Result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_getEntity() throws Exception {
        Asset res = Helper.getEntity(log, "signature", "Asset", getEntityManager(), Asset.class, 1);

        assertEquals("'getEntity' should be correct.", 1, res.getId());
    }

    /**
     * <p>
     * Tests accuracy of <code>performAudit(EntityManager entityManager, long userId, String action, String entityType,
     * long entityId, String oldValue, String newValue)</code>
     * method.<br>
     * Result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_performAudit() throws Exception {
        EntityManager entityManager = getEntityManager();
        entityManager.getTransaction().begin();
        Helper.performAudit(getEntityManager(), 1, "action", "entityType", 1, "oldValue", "newValue");
        entityManager.getTransaction().commit();
        entityManager.clear();

        checkAuditRecord("performAudit", 1L, "action", "entityType", 1L, false, false);
    }

    /**
     * <p>
     * Tests accuracy of <code>toString(Log log, String signature, Object obj)</code> method.<br>
     * Result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_toString1() throws Exception {
        String res = Helper.toString(log, "signature", 1);

        assertEquals("'toString' should be correct.", "1", res);
    }

    /**
     * <p>
     * Tests accuracy of <code>toString(Object obj)</code> method.<br>
     * Result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void test_toString2() throws Exception {
        String res = Helper.toString(1);

        assertEquals("'toString' should be correct.", "1", res);
    }
}
