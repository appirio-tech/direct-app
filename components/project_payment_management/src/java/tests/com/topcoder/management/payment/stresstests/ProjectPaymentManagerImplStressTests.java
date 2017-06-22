/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.payment.stresstests;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.management.payment.ProjectPayment;
import com.topcoder.management.payment.ProjectPaymentType;
import com.topcoder.management.payment.impl.ProjectPaymentManagerImpl;
import com.topcoder.management.payment.search.ProjectPaymentFilterBuilder;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.util.config.ConfigManager;

import junit.framework.TestCase;

/**
 * Stress tests for class {@link ProjectPaymentManagerImpl}.
 *
 * @author itkankan
 * @version 1.0.1
 */
public class ProjectPaymentManagerImplStressTests extends TestCase {

    /**
     * Instance of ProjectPaymentManagerImpl whose methods will be tested.
     */
    private ProjectPaymentManagerImpl instance = null;

    /**
     * Setup stress test environment.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void setUp() throws Exception {
        StressHelper.clearDB();

        ConfigManager.getInstance().add("SearchBundleManager.xml");
        ConfigurationObject configuration = StressHelper.getConfigObject("DBConfig", "test_files/stress/db.xml");
        instance = new ProjectPaymentManagerImpl(configuration);
    }

    /**
     * Tear down stress test environment.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void tearDown() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();

        for (Iterator<String> it = cm.getAllNamespaces(); it.hasNext();) {
            String nameSpace = it.next().toString();
            cm.removeNamespace(nameSpace);
        }

        StressHelper.clearDB();
    }

    /**
     * Stress tests for methods {@link ProjectPaymentManagerImpl#search(Filter)}.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testsearchStress() throws Exception {
        ProjectPayment projectPayment = new ProjectPayment();
        ProjectPaymentType projectPaymentType = new ProjectPaymentType();
        projectPaymentType.setProjectPaymentTypeId(1L);
        projectPayment.setProjectPaymentType(projectPaymentType);
        projectPayment.setResourceId(1L);
        projectPayment.setSubmissionId(1L);
        projectPayment.setAmount(BigDecimal.valueOf(2L));
        projectPayment.setPactsPaymentId(3L);
        projectPayment.setCreateDate(new Date());

        StressHelper.prepareDB(1);

        instance.create(projectPayment, "testOperator");
        Filter filter = ProjectPaymentFilterBuilder.createSubmissionIdFilter(1L);

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 20; i++) {
            List<ProjectPayment> res = instance.search(filter);
            assertEquals("The result should be correct.", 1, res.size());
        }
        System.out.println(String.format("testsearchStress took %s ms", System.currentTimeMillis() - startTime));
    }
}
