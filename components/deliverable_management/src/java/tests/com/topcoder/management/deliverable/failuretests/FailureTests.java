/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable.failuretests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * This test case aggregates all Unit test cases.
 *
 * @author  assistant
 * @version  1.0
 */
public class FailureTests extends TestCase {

    /**
     * Creates a unit test suite.
     *
     * @return  a test suite
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTestSuite(AuditedDeliverableStructureTest.class);
        suite.addTestSuite(DeliverableFilterBuilderTest.class);
        suite.addTestSuite(DeliverableTest.class);
        suite.addTestSuite(NamedDeliverableStructureTest.class);
        suite.addTestSuite(PersistenceDeliverableManagerTest.class);
        suite.addTestSuite(PersistenceUploadManagerTest.class);
        suite.addTestSuite(SubmissionFilterBuilderTest.class);
        suite.addTestSuite(SubmissionStatusTest.class);
        suite.addTestSuite(SubmissionTest.class);
        suite.addTestSuite(UploadFilterBuilderTest.class);
        suite.addTestSuite(UploadStatusTest.class);
        suite.addTestSuite(UploadTest.class);
        suite.addTestSuite(UploadTypeTest.class);
        suite.addTestSuite(SubmissionTypeTests.class);
        return suite;
    }
}
