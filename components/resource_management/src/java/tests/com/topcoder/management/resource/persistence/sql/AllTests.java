/**
 *
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved
 */
package com.topcoder.management.resource.persistence.sql;

import com.topcoder.management.resource.persistence.sql.accuracytests.AccuracyTests;
import com.topcoder.management.resource.persistence.sql.failuretests.FailureTests;
import com.topcoder.management.resource.persistence.sql.stresstests.StressTests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>This test case aggregates all test cases.</p>
 *
 * @author TopCoder
 * @version 1.0
 */
public class AllTests extends TestCase {

    public static Test suite() {
        final TestSuite suite = new TestSuite();
        
        suite.addTest(FailureTests.suite());
        
        suite.addTest(StressTests.suite());
        
        suite.addTest(AccuracyTests.suite());
        
        suite.addTest(UnitTests.suite());

        
        return suite;
    }

}
