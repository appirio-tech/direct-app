/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.payment;

import java.math.BigDecimal;
import java.util.List;

import junit.framework.JUnit4TestAdapter;

import org.junit.Test;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.management.payment.impl.ProjectPaymentAdjustmentManagerImpl;
import com.topcoder.management.payment.impl.ProjectPaymentManagerImpl;
import com.topcoder.management.payment.search.ProjectPaymentFilterBuilder;
import com.topcoder.search.builder.filter.Filter;

/**
 * <p>
 * Shows usage for the component.
 * </p>
 *
 * @author maksimilian, sparemax
 * @version 1.0.1
 */
public class Demo extends BaseUnitTests {
    /**
     * <p>
     * Adapter for earlier versions of JUnit.
     * </p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(Demo.class);
    }

    /**
     * <p>
     * Demo API usage of this component.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @SuppressWarnings("unused")
    @Test
    public void testDemo() throws Exception {
        // Create an instance of ProjectPaymentManagerImpl using configuration
        ConfigurationObject configuration = getConfig(ProjectPaymentManagerImpl.DEFAULT_CONFIG_NAMESPACE);
        ProjectPaymentManagerImpl projectPaymentManager = new ProjectPaymentManagerImpl(configuration);

        // Create an instance of ProjectPaymentManagerImpl using config file and namespace
        projectPaymentManager = new ProjectPaymentManagerImpl(ProjectPaymentManagerImpl.DEFAULT_CONFIG_FILENAME,
            ProjectPaymentManagerImpl.DEFAULT_CONFIG_NAMESPACE);

        // Create an instance of ProjectPaymentManagerImpl using default config file
        projectPaymentManager = new ProjectPaymentManagerImpl();

        // Retrieve the project payment with ID=1
        ProjectPayment projectPayment = projectPaymentManager.retrieve(1);
        // id must be 1
        // submission id must be 1011111
        // resource id must be 1001
        // PACTS payment id must be 4
        // amount must be 500.0
        // etc.

        // Update the project payment by changing amount
        projectPayment.setAmount(BigDecimal.valueOf(600));
        projectPaymentManager.update(projectPayment, "testOperator");

        // Search for all project payments with submission ID=1011111
        Filter submissionIdFilter = ProjectPaymentFilterBuilder.createSubmissionIdFilter(1011111);

        List<ProjectPayment> projectPayments = projectPaymentManager.search(submissionIdFilter);
        // projectPayments.size() must be 1
        // id must be 1
        // submission id must be 1011111
        // resource id must be 1001
        // PACTS payment id must be 4
        // amount must be 600.0
        // etc.

        // Delete the project payment by id
        projectPaymentManager.delete(2);

        // Create an instance of ProjectPaymentAdjustmentManagerImpl using configuration
        configuration = getConfig(ProjectPaymentAdjustmentManagerImpl.DEFAULT_CONFIG_NAMESPACE);
        ProjectPaymentAdjustmentManagerImpl projectPaymentAdjustmentManager = new ProjectPaymentAdjustmentManagerImpl(
            configuration);

        // Retrieve the project payment adjustments with project ID=1001
        List<ProjectPaymentAdjustment> projectPaymentAdjustments = projectPaymentAdjustmentManager
            .retrieveByProjectId(1001);
        // projectPaymentAdjustmentss.size() must be 1
        // project id must be 1001
        // resource role id must be 4
        // fixed amount must be 50
        // etc.
    }
}
