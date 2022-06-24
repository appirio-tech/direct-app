/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.review.data.stresstests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * <p>This test case aggregates all Stress test cases.</p>
 *
 * @author mgmg
 * @version 1.0
 */
public class StressTests extends TestCase {
    /**
     * Aggregate all stress tests.
     *
     * @return
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(ItemStressTest.class);
        suite.addTestSuite(ReviewEditorStressTest.class);

        return suite;
    }
}
