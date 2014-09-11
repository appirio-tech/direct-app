/*
 * Copyright (C) 2006-2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import com.topcoder.management.deliverable.failuretests.FailureTests;
import com.topcoder.management.deliverable.accuracytests.AccuracyTests;
import com.topcoder.management.deliverable.stresstests.StressTests;


/**
 * <p>This test case aggregates all Unit test cases.</p>
 *
 * @author TopCoder
 * @version 1.1
 */
public class AllTests extends TestCase {

    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTest(UnitTests.suite());
        suite.addTest(FailureTests.suite());
        suite.addTest(AccuracyTests.suite());
        suite.addTest(StressTests.suite());
        return suite;
    }

}
