/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.metadata.stresstests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>This test case aggregates all Stress test cases.</p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class StressTests extends TestCase {
    /**
     * Returns stress tests together.
     *
     * @return all stress tests.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTest(DirectProjectMetadataServiceImplStressTests.suite());
        suite.addTest(DirectProjectMetadataKeyServiceImplStressTests.suite());

        return suite;
    }
}
