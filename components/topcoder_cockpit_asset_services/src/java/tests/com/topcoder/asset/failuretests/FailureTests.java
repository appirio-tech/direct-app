/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.asset.failuretests;

import com.topcoder.asset.services.impl.AssetServiceImplFailureTests;
import com.topcoder.asset.services.impl.AssetVersionServiceImplFailureTests;
import com.topcoder.asset.services.impl.BaseAssetServiceFailureTests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>This test case aggregates all Failure test cases.</p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class FailureTests extends TestCase {
    /**
     * Test suite for the failure tests.
     *
     * @return the test suite
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTest(BaseAssetServiceFailureTests.suite());
        suite.addTest(AssetServiceImplFailureTests.suite());
        suite.addTest(AssetVersionServiceImplFailureTests.suite());

        return suite;
    }

}
