/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project.persistence.failuretests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>This test case aggregates all Failure test cases.</p>
 *
 * @author kshatriyan
 * @version 1.1
 */
public class FailureTests extends TestCase {

    /**
     * Failure test suite.
     *
     * @return aggregates all the failure test cases.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTestSuite(AbstractInformixProjectPersistenceCtorTest.class);
        suite.addTestSuite(InformixProjectPersistenceTest.class);
        suite.addTestSuite(UnManagedTransactionInformixProjectPersistenceTest.class);
        return suite;
    }

}
