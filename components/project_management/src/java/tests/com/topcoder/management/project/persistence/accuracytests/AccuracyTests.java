/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project.persistence.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>This test case aggregates all Accuracy test cases.</p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AccuracyTests extends TestCase {
    /**
     * <p>
     * This test case aggregates all Accuracy test cases.
     * </p>
     *
     * @return all Accuracy test cases.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTest(AbstractInformixProjectPersistenceAccuracyTests.suite());
        suite.addTest(InformixProjectPersistenceAccuracyTests.suite());
        suite.addTest(UnmanagedTransactionInformixProjectPersistenceAccuracyTests.suite());

        return suite;
    }

}
