/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.payment.stresstests;

import java.math.BigDecimal;
import java.util.Date;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.management.payment.ProjectPayment;
import com.topcoder.management.payment.ProjectPaymentType;
import com.topcoder.management.payment.impl.persistence.DatabaseProjectPaymentPersistence;

import junit.framework.TestCase;

/**
 * Stress tests for class {@link DatabaseProjectPaymentPersistence}.
 *
 * @author itkankan
 * @version 1.0.1
 */
public class DatabaseProjectPaymentPersistenceStressTests extends TestCase {

    /**
     * Represents the number of execute times.
     */
    private static final int NUM = 10;

    /**
     * Represents the instance of DatabaseProjectPaymentPersistence whose methods will be tested.
     */
    private DatabaseProjectPaymentPersistence instance = null;

    /**
     * Represents the ProjectPayment entity.
     */
    private ProjectPayment projectPayment = null;

    /**
     * Setup stress test environment.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void setUp() throws Exception {
        projectPayment = new ProjectPayment();
        ProjectPaymentType projectPaymentType = new ProjectPaymentType();
        projectPaymentType.setProjectPaymentTypeId(1L);
        projectPayment.setProjectPaymentType(projectPaymentType);
        projectPayment.setResourceId(1L);
        projectPayment.setSubmissionId(1L);
        projectPayment.setAmount(BigDecimal.valueOf(650L));
        projectPayment.setPactsPaymentId(4L);
        projectPayment.setCreateDate(new Date());

        StressHelper.clearDB();
        instance = new DatabaseProjectPaymentPersistence();
        ConfigurationObject configuration = StressHelper.getConfigObject("DBConfig", "test_files/stress/db.xml");
        instance.configure(configuration.getChild("projectPaymentPersistenceConfig"));
    }

    /**
     * Tear down stress test environment.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void tearDown() throws Exception {
        StressHelper.clearDB();
    }

    /**
     * Stress test for method {@link DatabaseProjectPaymentPersistence#create(ProjectPayment)}.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testcreateStress() throws Exception {
        ProjectPayment[] projectPayment = generateProjectPayments();

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < NUM; i++) {
            StressHelper.prepareDB(i + 1);
            instance.create(projectPayment[i], "testOperator");
        }
        int firstIndex = StressHelper
                .getFirstIndex("select project_payment_id from project_payment order by project_payment_id asc");
        int lastIndex = StressHelper
                .getFirstIndex("select project_payment_id from project_payment order by project_payment_id desc");
        assertEquals("The result should be correct.", NUM - 1, lastIndex - firstIndex);
        System.out.println(String.format("testcreateStress took %s ms", System.currentTimeMillis() - startTime));
    }

    /**
     * Stress test for method {@link DatabaseProjectPaymentPersistence#update(ProjectPayment)}.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testupdateStress() throws Exception {
        StressHelper.prepareDB(1);

        instance.create(projectPayment, "testOperator");
        int firstIndex = StressHelper
                .getFirstIndex("select project_payment_id from project_payment order by project_payment_id asc");

        projectPayment.setProjectPaymentId(new Long(firstIndex));

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < NUM; i++) {
            projectPayment.setAmount(BigDecimal.valueOf(i));
            instance.update(projectPayment, "testOperator");

            ProjectPayment result = instance.retrieve(firstIndex);
            assertEquals("The result should be correct.", i, result.getAmount().intValue());
        }
        System.out.println(String.format("testupdateStress took %s ms", System.currentTimeMillis() - startTime));
    }

    /**
     * Stress test for method {@link DatabaseProjectPaymentPersistence#retrieve(long)}.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testretrieveStress() throws Exception {
        StressHelper.prepareDB(1);
        instance.create(projectPayment, "testOperator");
        int firstIndex = StressHelper
                .getFirstIndex("select project_payment_id from project_payment order by project_payment_id asc");

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < NUM; i++) {
            ProjectPayment result = instance.retrieve(firstIndex);
            assertEquals("The result should be correct.", 1, result.getResourceId().intValue());
            assertEquals("The result should be correct.", 1, result.getSubmissionId().intValue());
            assertEquals("The result should be correct.", 1, result.getProjectPaymentType().getProjectPaymentTypeId()
                    .intValue());
        }
        System.out.println(String.format("testretrieveStress took %s ms", System.currentTimeMillis() - startTime));
    }

    /**
     * Stress test for method {@link DatabaseProjectPaymentPersistence#delete(long)}.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testdeleteStress() throws Exception {
        ProjectPayment[] projectPayment = generateProjectPayments();
        for (int i = 0; i < NUM; i++) {
            StressHelper.prepareDB(i + 1);
            instance.create(projectPayment[i], "testOperator");

        }

        int firstIndex = StressHelper
                .getFirstIndex("select project_payment_id from project_payment order by project_payment_id asc");

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < NUM; i++) {
            instance.delete(firstIndex + i);
        }

        int index = StressHelper.getFirstIndex("select count(*) from project_payment");
        assertEquals("The result should be correct.", 0, index);
        System.out.println(String.format("testdeleteStress took %s ms", System.currentTimeMillis() - startTime));
    }

    /**
     * Generate an array of ProjectPayment.
     *
     * @return an array of ProjectPayment.
     */
    private static ProjectPayment[] generateProjectPayments() {
        ProjectPayment[] projectPayment = new ProjectPayment[NUM];
        for (int i = 0; i < NUM; i++) {
            projectPayment[i] = new ProjectPayment();
            ProjectPaymentType projectPaymentType = new ProjectPaymentType();
            projectPaymentType.setProjectPaymentTypeId(new Long(i + 1));
            projectPayment[i].setProjectPaymentType(projectPaymentType);
            projectPayment[i].setResourceId(new Long(i + 1));
            projectPayment[i].setSubmissionId(new Long(i + 1));
            projectPayment[i].setAmount(BigDecimal.valueOf(3L));
            projectPayment[i].setPactsPaymentId(4L);
            projectPayment[i].setCreateDate(new Date());
        }
        return projectPayment;
    }

}
