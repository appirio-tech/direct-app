/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.payment;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.management.payment.impl.HelperUnitTests;
import com.topcoder.management.payment.impl.ProjectPaymentAdjustmentManagerImplUnitTests;
import com.topcoder.management.payment.impl.ProjectPaymentManagementPersistenceExceptionUnitTests;
import com.topcoder.management.payment.impl.ProjectPaymentManagerImplUnitTests;
import com.topcoder.management.payment.impl.persistence.BaseProjectPaymentManagementPersistenceUnitTests;
import com.topcoder.management.payment.impl.persistence.DatabaseProjectPaymentAdjustmentPersistenceUnitTests;
import com.topcoder.management.payment.impl.persistence.DatabaseProjectPaymentPersistenceUnitTests;
import com.topcoder.management.payment.search.ProjectPaymentFilterBuilderUnitTests;

/**
 * <p>
 * This test case aggregates all Unit test cases.
 * </p>
 *
 * @author sparemax
 * @version 1.0
 */
public class UnitTests extends TestCase {
    /**
     * <p>
     * All unit test cases.
     * </p>
     *
     * @return The test suite.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTest(Demo.suite());
        suite.addTest(HelperUnitTests.suite());

        suite.addTest(ProjectPaymentAdjustmentUnitTests.suite());
        suite.addTest(ProjectPaymentTypeUnitTests.suite());
        suite.addTest(ProjectPaymentUnitTests.suite());

        suite.addTest(ProjectPaymentManagerImplUnitTests.suite());
        suite.addTest(ProjectPaymentAdjustmentManagerImplUnitTests.suite());

        suite.addTest(BaseProjectPaymentManagementPersistenceUnitTests.suite());
        suite.addTest(DatabaseProjectPaymentAdjustmentPersistenceUnitTests.suite());
        suite.addTest(DatabaseProjectPaymentPersistenceUnitTests.suite());

        suite.addTest(ProjectPaymentFilterBuilderUnitTests.suite());

        // Exceptions
        suite.addTest(ProjectPaymentManagementConfigurationExceptionUnitTests.suite());
        suite.addTest(ProjectPaymentManagementExceptionUnitTests.suite());
        suite.addTest(ProjectPaymentAdjustmentValidationExceptionUnitTests.suite());
        suite.addTest(ProjectPaymentManagementDataIntegrityExceptionUnitTests.suite());
        suite.addTest(ProjectPaymentNotFoundExceptionUnitTests.suite());
        suite.addTest(ProjectPaymentValidationExceptionUnitTests.suite());
        suite.addTest(ProjectPaymentManagementPersistenceExceptionUnitTests.suite());

        return suite;
    }

}
