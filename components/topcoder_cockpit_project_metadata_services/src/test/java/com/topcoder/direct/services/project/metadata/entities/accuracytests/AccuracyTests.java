/**
 * Copyright (c) 2011, TopCoder, Inc. All rights reserved
 */
package com.topcoder.direct.services.project.metadata.entities.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>This test case aggregates all accuracy test cases.</p>
 *
 * @author TopCoder
 * @version 1.0
 */
public class AccuracyTests extends TestCase {

    /**
     * Aggregates all accuracy test cases.
     *
     * @return all aggregates test cases
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTest(AuditEntityAccuracyTests.suite());
        suite.addTest(CompositeFilterAccuracyTests.suite());
        suite.addTest(CompositeOperatorAccuracyTests.suite());
        suite.addTest(DirectProjectMetadataAccuracyTests.suite());
        suite.addTest(DirectProjectMetadataAuditAccuracyTests.suite());
        suite.addTest(DirectProjectMetadataDTOAccuracyTests.suite());
        suite.addTest(DirectProjectMetadataKeyAccuracyTests.suite());
        suite.addTest(DirectProjectMetadataKeyAuditAccuracyTests.suite());
        suite.addTest(DirectProjectMetadataPredefinedValueAccuracyTests.suite());
        suite.addTest(DirectProjectMetadataPredefinedValueAuditAccuracyTests.suite());
        suite.addTest(MetadataKeyIdValueFilterAccuracyTests.suite());
        suite.addTest(MetadataKeyNameValueFilterAccuracyTests.suite());
        suite.addTest(MetadataValueOperatorAccuracyTests.suite());
        suite.addTest(TcDirectProjectAccuracyTests.suite());

        return suite;
    }

}

