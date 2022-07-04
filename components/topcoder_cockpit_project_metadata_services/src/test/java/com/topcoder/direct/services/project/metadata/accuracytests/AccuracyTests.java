/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.metadata.accuracytests;

import com.topcoder.direct.services.project.metadata.accuracytests.impl.DirectProjectMetadataKeyValidatorImplAccuracyTests;
import com.topcoder.direct.services.project.metadata.impl.DirectProjectMetadataKeyServiceImplAccuracyTests;
import com.topcoder.direct.services.project.metadata.impl.DirectProjectMetadataValidatorImplAccuracyTests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all accuracy test cases.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AccuracyTests extends TestCase {
    /**
     * <p>
     * All accuracy test cases.
     * </p>
     *
     * @return The test suite.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTest(DirectProjectMetadataKeyValidatorImplAccuracyTests.suite());
        suite.addTest(DirectProjectMetadataValidatorImplAccuracyTests.suite());

        suite.addTest(DirectProjectMetadataKeyServiceImplAccuracyTests.suite());
        suite.addTest(DirectProjectMetadataValidatorImplAccuracyTests.suite());

        return suite;
    }

}
