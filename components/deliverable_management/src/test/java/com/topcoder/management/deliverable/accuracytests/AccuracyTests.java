/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * <p>
 * This test case aggregates all Accuracy test cases.
 * </p>
 *
 * @author skatou
 * @version 1.0
 */
public class AccuracyTests extends TestCase {
    /**
     * Aggregates all Accuracy test cases.
     *
     * @return the test suite aggregates all Accuracy test cases.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(IdAlreadySetExceptionAccuracyTests.class);
        suite.addTestSuite(DeliverablePersistenceExceptionAccuracyTests.class);
        suite.addTestSuite(DeliverableCheckingExceptionAccuracyTests.class);
        suite.addTestSuite(UploadPersistenceExceptionAccuracyTests.class);

        suite.addTestSuite(AuditedDeliverableStructureAccuracyTests.class);
        suite.addTestSuite(NamedDeliverableStructureAccuracyTests.class);
        suite.addTestSuite(SubmissionStatusAccuracyTests.class);
        suite.addTestSuite(SubmissionTypeAccuracyTests.class);
        suite.addTestSuite(UploadTypeAccuracyTests.class);
        suite.addTestSuite(UploadStatusAccuracyTests.class);
        suite.addTestSuite(UploadAccuracyTests.class);
        suite.addTestSuite(SubmissionAccuracyTests.class);
        suite.addTestSuite(DeliverableAccuracyTests.class);

        suite.addTestSuite(UploadFilterBuilderAccuracyTests.class);
        suite.addTestSuite(SubmissionFilterBuilderAccuracyTests.class);
        suite.addTestSuite(DeliverableFilterBuilderAccuracyTests.class);

        suite.addTestSuite(PersistenceDeliverableManagerAccuracyTests.class);
        suite.addTestSuite(PersistenceUploadManagerAccuracyTests.class);
		
        return suite;
    }
}
