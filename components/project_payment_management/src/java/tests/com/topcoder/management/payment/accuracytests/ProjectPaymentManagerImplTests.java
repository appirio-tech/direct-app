/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.payment.accuracytests;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.persistence.ConfigurationFileManager;

import com.topcoder.management.payment.ProjectPayment;
import com.topcoder.management.payment.ProjectPaymentType;
import com.topcoder.management.payment.impl.ProjectPaymentManagerImpl;
import com.topcoder.management.payment.search.ProjectPaymentFilterBuilder;

import com.topcoder.util.config.ConfigManager;

import junit.framework.JUnit4TestAdapter;

import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import java.sql.Connection;

import java.util.Date;
import java.util.Iterator;
import java.util.List;


/**
 * <p>Accuracy tests for <code>{@link ProjectPaymentManagerImpl}</code> class.</p>
 *
 * @author gjw99
 * @version 1.0.1
 */
public class ProjectPaymentManagerImplTests {
    /**
     * <p>Represents the <code>ProjectPaymentManagerImpl</code> instance used in tests.</p>
     */
    private ProjectPaymentManagerImpl instance;

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
        return new JUnit4TestAdapter(ProjectPaymentManagerImplTests.class);
    }

    /**
     * <p>Sets up the unit tests.</p>
     *
     * @throws Exception to JUnit.
     */
    @Before
    public void setUp() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();

        for (Iterator<?> it = cm.getAllNamespaces(); it.hasNext();) {
            String ns = it.next().toString();
            cm.removeNamespace(ns);
        }

        ConfigManager.getInstance().add("accuracy/SearchBundleManager.xml");
        instance = new ProjectPaymentManagerImpl("test_files/accuracy/Payment.properties", "payment");
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
        instance = new ProjectPaymentManagerImpl("test_files/accuracy/Payment.properties", "payment");
        assertNotNull("instance should be initialized", instance);
    }

    /**
     * <p>Accuracy test for the constructor.</p>
     *
     * @throws Exception to JUnit.
     */
    @Test
    public void test_ctor2() throws Exception {
        ConfigurationFileManager manager = new ConfigurationFileManager("test_files/accuracy/Payment.properties");
        ConfigurationObject config = manager.getConfiguration("payment");
        instance = new ProjectPaymentManagerImpl(config.getChild("payment"));
        assertNotNull("instance should be initialized", instance);
    }

    /**
     * <p>Accuracy test for the method <code>create()</code>.</p>
     *
     * @throws Exception to JUnit.
     */
    @Test
    public void test_create() throws Exception {
        ProjectPayment payment = new ProjectPayment();
        payment.setAmount(new BigDecimal(200));
        payment.setCreateDate(new Date());

        ProjectPaymentType type = new ProjectPaymentType();
        type.setProjectPaymentTypeId(1L);
        payment.setProjectPaymentType(type);
        payment.setResourceId(2L);
        payment.setSubmissionId(1L);
        payment.setPactsPaymentId(2L);

        ProjectPayment created = instance.create(payment, "testOperator");
        assertTrue("payment must be created", created.getProjectPaymentId() > 0);
    }

    /**
     * <p>Accuracy test for the method <code>retrieve()</code>.</p>
     *
     * @throws Exception to JUnit.
     */
    @Test
    public void retrieve() throws Exception {
        ProjectPayment payment = new ProjectPayment();
        payment.setAmount(new BigDecimal(200));
        payment.setCreateDate(new Date());

        ProjectPaymentType type = new ProjectPaymentType();
        type.setProjectPaymentTypeId(1L);
        payment.setProjectPaymentType(type);
        payment.setResourceId(2L);
        payment.setSubmissionId(1L);
        payment.setPactsPaymentId(2L);

        ProjectPayment created = instance.create(payment, "testOperator");
        assertTrue("payment must be created", created.getProjectPaymentId() > 0);

        ProjectPayment persisted = instance.retrieve(created.getProjectPaymentId());
        assertEquals("record should be found", 0, created.getAmount().compareTo(persisted.getAmount()));
        assertEquals("record should be found", created.getPactsPaymentId().longValue(),
            persisted.getPactsPaymentId().longValue());
        assertEquals("record should be found", created.getResourceId().longValue(),
            persisted.getResourceId().longValue());
    }

    /**
     * <p>Accuracy test for the method <code>delete()</code>.</p>
     *
     * @throws Exception to JUnit.
     */
    @Test
    public void delete() throws Exception {
        ProjectPayment payment = new ProjectPayment();
        payment.setAmount(new BigDecimal(200));
        payment.setCreateDate(new Date());

        ProjectPaymentType type = new ProjectPaymentType();
        type.setProjectPaymentTypeId(1L);
        payment.setProjectPaymentType(type);
        payment.setResourceId(2L);
        payment.setSubmissionId(1L);
        payment.setPactsPaymentId(2L);

        ProjectPayment created = instance.create(payment, "testOperator");
        assertTrue("payment must be created", created.getProjectPaymentId() > 0);

        long id = created.getProjectPaymentId();
        ProjectPayment persisted = instance.retrieve(id);
        assertNotNull("should exist", persisted);

        boolean result = instance.delete(id);
        assertTrue("should be deleted", result);
        persisted = instance.retrieve(id);
        assertNull("should not exist", persisted);
        // delete an non-exist one
        result = instance.delete(100);
        assertFalse("should return false", result);
    }

    /**
     * <p>Accuracy test for the method <code>update()</code>.</p>
     *
     * @throws Exception to JUnit.
     */
    @Test
    public void update() throws Exception {
        ProjectPayment payment = new ProjectPayment();
        payment.setAmount(new BigDecimal(200));
        payment.setCreateDate(new Date());

        ProjectPaymentType type = new ProjectPaymentType();
        type.setProjectPaymentTypeId(1L);
        payment.setProjectPaymentType(type);
        payment.setResourceId(2L);
        payment.setSubmissionId(1L);
        payment.setPactsPaymentId(2L);

        ProjectPayment created = instance.create(payment, "testOperator");
        assertTrue("payment must be created", created.getProjectPaymentId() > 0);

        ProjectPayment persisted = instance.retrieve(created.getProjectPaymentId());
        assertEquals("record should be found", 0, created.getAmount().compareTo(persisted.getAmount()));
        assertEquals("record should be found", created.getPactsPaymentId().longValue(),
            persisted.getPactsPaymentId().longValue());
        assertEquals("record should be found", created.getResourceId().longValue(),
            persisted.getResourceId().longValue());
        persisted.setAmount(new BigDecimal(100));
        persisted.setResourceId(3L);
        persisted.setSubmissionId(2L);
        instance.update(persisted, "testOperator");
        persisted = instance.retrieve(persisted.getProjectPaymentId());
        assertEquals("record should be updated", 0, new BigDecimal(100).compareTo(persisted.getAmount()));
        assertEquals("record should be found", 2L, persisted.getSubmissionId().longValue());
        assertEquals("record should be found", 3L, persisted.getResourceId().longValue());
    }

    /**
     * <p>Accuracy test for the method <code>search()</code>.</p>
     *
     * @throws Exception to JUnit.
     */
    @Test
    public void search() throws Exception {
        List<ProjectPayment> result = instance.search(null);
        assertEquals("no project payment found", 0, result.size());
        // create 4 payments
        instance.create(getProjectPayment(), "testOperator");
        instance.create(getProjectPayment(), "testOperator");
        instance.create(getProjectPayment(), "testOperator");
        instance.create(getProjectPayment(), "testOperator");
        result = instance.search(null);
        assertEquals("4 project payments found", 4, result.size());
        assertEquals("record should be updated", 0, new BigDecimal(200).compareTo(result.get(0).getAmount()));
        assertEquals("record should be found", 1L, result.get(0).getSubmissionId().longValue());
        assertEquals("record should be found", 2L, result.get(0).getResourceId().longValue());
        result = instance.search(ProjectPaymentFilterBuilder.createPactsPaymentIdFilter(true));
        assertEquals("4 project payments found", 4, result.size());
        result = instance.search(ProjectPaymentFilterBuilder.createProjectIdFilter(100));
        assertEquals("no project payments found", 0, result.size());
        result = instance.search(ProjectPaymentFilterBuilder.createProjectPaymentTypeIdFilter(1L));
        assertEquals("4 project payments found", 4, result.size());
        result = instance.search(ProjectPaymentFilterBuilder.createProjectPaymentTypeIdFilter(10));
        assertEquals("no project payments found", 0, result.size());
        result = instance.search(ProjectPaymentFilterBuilder.createResourceIdFilter(2L));
        assertEquals("4 project payments found", 4, result.size());
        result = instance.search(ProjectPaymentFilterBuilder.createResourceIdFilter(10));
        assertEquals("no project payments found", 0, result.size());
        result = instance.search(ProjectPaymentFilterBuilder.createSubmissionIdFilter(1L));
        assertEquals("4 project payments found", 4, result.size());
        result = instance.search(ProjectPaymentFilterBuilder.createSubmissionIdFilter(10));
        assertEquals("no project payments found", 0, result.size());
    }

    /**
     * Get project payment for tests.
     *
     * @return the project payment
     */
    private ProjectPayment getProjectPayment() {
        ProjectPayment payment = new ProjectPayment();
        payment.setAmount(new BigDecimal(200));
        payment.setCreateDate(new Date());

        ProjectPaymentType type = new ProjectPaymentType();
        type.setProjectPaymentTypeId(1L);
        payment.setProjectPaymentType(type);
        payment.setResourceId(2L);
        payment.setSubmissionId(1L);
        payment.setPactsPaymentId(2L);

        return payment;
    }
}
