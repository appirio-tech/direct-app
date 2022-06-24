/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.payment.accuracytests;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.persistence.ConfigurationFileManager;

import com.topcoder.management.payment.ProjectPaymentAdjustment;
import com.topcoder.management.payment.impl.ProjectPaymentAdjustmentManagerImpl;

import junit.framework.JUnit4TestAdapter;

import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import java.sql.Connection;

import java.util.List;


/**
 * <p>Accuracy tests for <code>{@link ProjectPaymentAdjustmentManagerImpl}</code> class.</p>
 *
 * @author gjw99
 * @version 1.0
 */
public class ProjectPaymentAdjustmentManagerImplTests {
    /**
     * <p>Represents the <code>ProjectPaymentAdjustmentManagerImpl</code> instance used in tests.</p>
     */
    private ProjectPaymentAdjustmentManagerImpl instance;

    /**
     * <p>Represents the <code>Connection</code> instance used in tests.</p>
     */
    private Connection conn;

    /**
     * <p>Adapter for earlier versions of JUnit.</p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(ProjectPaymentAdjustmentManagerImplTests.class);
    }

    /**
     * <p>Sets up the unit tests.</p>
     *
     * @throws Exception to JUnit.
     */
    @Before
    public void setUp() throws Exception {
        instance = new ProjectPaymentAdjustmentManagerImpl("test_files/accuracy/PaymentAdjustment.properties",
                "paymentAdjustment");
        conn = TestHelper.getConnection();
        TestHelper.clearDB(conn);
        TestHelper.loadDB(conn);
    }

    /**
     * <p>Tears down the unit tests.</p>
     *
     * @throws Exception to JUnit.
     */
    @After
    public void tearsDown() throws Exception {
        instance = null;
        TestHelper.clearDB(conn);

        if ((conn != null) && !conn.isClosed()) {
            conn.close();
        }
    }

    /**
     * <p>Accuracy test for the constructor.</p>
     *
     * @throws Exception to JUnit.
     */
    @Test
    public void test_ctor1() throws Exception {
        instance = new ProjectPaymentAdjustmentManagerImpl("test_files/accuracy/PaymentAdjustment.properties",
                "paymentAdjustment");
        assertNotNull("instance should be initialized", instance);
    }

    /**
     * <p>Accuracy test for the constructor.</p>
     *
     * @throws Exception to JUnit.
     */
    @Test
    public void test_ctor2() throws Exception {
        ConfigurationFileManager manager = new ConfigurationFileManager(
                "test_files/accuracy/PaymentAdjustment.properties");
        ConfigurationObject config = manager.getConfiguration("paymentAdjustment");
        instance = new ProjectPaymentAdjustmentManagerImpl(config.getChild("paymentAdjustment"));
        assertNotNull("instance should be initialized", instance);
    }

    /**
     * <p>Accuracy test for the method <code>retrieveByProjectId()</code>.</p>
     *
     * @throws Exception to JUnit.
     */
    @Test
    public void test_retrieveByProjectId1() throws Exception {
        List<ProjectPaymentAdjustment> result = instance.retrieveByProjectId(1);
        assertEquals("payment adjustment must be found", 3, result.size());

        for (ProjectPaymentAdjustment adjust : result) {
            if (adjust.getResourceRoleId() == 1) {
                assertTrue("payment adjustment must be found", 200 == adjust.getFixedAmount().doubleValue());
                assertEquals("payment adjustment must be found", null, adjust.getMultiplier());
            }

            if (adjust.getResourceRoleId() == 2) {
                assertTrue("payment adjustment must be found", 50 == adjust.getFixedAmount().doubleValue());
                assertEquals("payment adjustment must be found", null, adjust.getMultiplier());
            }

            if (adjust.getResourceRoleId() == 4) {
                assertTrue("payment adjustment must be found", 300 == adjust.getMultiplier().doubleValue());
                assertEquals("payment adjustment must be found", null, adjust.getFixedAmount());
            }
        }
    }

    /**
     * <p>Accuracy test for the method <code>retrieveByProjectId()</code>.</p>
     *
     * @throws Exception to JUnit.
     */
    @Test
    public void test_retrieveByProjectId2() throws Exception {
        List<ProjectPaymentAdjustment> result = instance.retrieveByProjectId(100);
        assertEquals("payment adjustment must not be found", 0, result.size());
    }

    /**
     * <p>Accuracy test for the method <code>retrieveByProjectId()</code>. Add new.</p>
     *
     * @throws Exception to JUnit.
     */
    @Test
    public void test_save1() throws Exception {
        List<ProjectPaymentAdjustment> result = instance.retrieveByProjectId(1);
        assertEquals("payment adjustment must be found", 3, result.size());

        ProjectPaymentAdjustment adjust = new ProjectPaymentAdjustment();
        adjust.setFixedAmount(new BigDecimal(100));
        adjust.setProjectId(1L);
        adjust.setResourceRoleId(5L);
        instance.save(adjust);
        result = instance.retrieveByProjectId(1);
        assertEquals("new payment adjustment must be added", 4, result.size());

        boolean insert = false;

        for (ProjectPaymentAdjustment adj : result) {
            if (adj.getResourceRoleId() == 5) {
                assertTrue("payment adjustment must be found", 100 == adjust.getFixedAmount().doubleValue());
                assertEquals("payment adjustment must be found", null, adjust.getMultiplier());
                insert = true;

                break;
            }
        }

        assertTrue("should be inserted", insert);
    }

    /**
     * <p>Accuracy test for the method <code>retrieveByProjectId()</code>. Update exist.</p>
     *
     * @throws Exception to JUnit.
     */
    @Test
    public void test_save2() throws Exception {
        List<ProjectPaymentAdjustment> result = instance.retrieveByProjectId(1);
        assertEquals("payment adjustment must be found", 3, result.size());

        ProjectPaymentAdjustment adjust = new ProjectPaymentAdjustment();
        adjust.setFixedAmount(new BigDecimal(600));
        adjust.setProjectId(1L);
        adjust.setResourceRoleId(4L);
        instance.save(adjust);
        result = instance.retrieveByProjectId(1);
        assertEquals("new payment adjustment must be added", 3, result.size());

        boolean update = false;

        for (ProjectPaymentAdjustment adj : result) {
            if (adj.getResourceRoleId() == 4) {
                assertTrue("payment adjustment must be found", 600 == adjust.getFixedAmount().doubleValue());
                assertEquals("payment adjustment must be found", null, adjust.getMultiplier());
                update = true;

                break;
            }
        }

        assertTrue("should be inserted", update);
    }
}
