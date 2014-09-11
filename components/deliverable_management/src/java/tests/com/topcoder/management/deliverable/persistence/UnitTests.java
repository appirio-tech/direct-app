/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable.persistence;

import com.topcoder.management.deliverable.persistence.sql.HelperTest;
import com.topcoder.management.deliverable.persistence.sql.SqlDeliverablePersistenceTest;
import com.topcoder.management.deliverable.persistence.sql.SqlUploadPersistenceTest;
import com.topcoder.management.deliverable.persistence.sql.logging.LogMessageTest;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Unit test cases.
 * </p>
 *
 * @author urtks
 * @version 1.0
 */
public class UnitTests extends TestCase {
    /**
     * Aggregates all unit tests in this component.
     *
     * @return test suite aggregating all unit tests.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTest(LogMessageTest.suite());

        suite.addTest(HelperTest.suite());
        suite.addTest(SqlUploadPersistenceTest.suite());
        suite.addTest(SqlDeliverablePersistenceTest.suite());

        // add demo test
        suite.addTest(DemoTest.suite());

        return suite;
    }

}
