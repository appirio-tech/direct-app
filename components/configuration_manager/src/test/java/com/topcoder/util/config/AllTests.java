package com.topcoder.util.config;

import junit.framework.*;
import junit.extensions.*;
//import com.topcoder.util.config.functionaltests.FunctionalTests;
import com.topcoder.util.config.accuracytests.AccuracyTests;
import com.topcoder.util.config.failuretests.FailureTests;
import com.topcoder.util.config.stresstests.StressTests;

/**
 * Organizes all unit tests into a single testing suite
 */
public class AllTests extends TestCase {

    public AllTests(String name) {
	super(name);
    }

    public static Test suite() {
        final TestSuite suite = new TestSuite();

        //unit tests
        suite.addTest(UnitTests.suite());

        //functional tests
//        suite.addTest(FunctionalTests.suite());

        //accuracy tests
        suite.addTest(AccuracyTests.suite());

        //failure tests
        suite.addTest(FailureTests.suite());

        //stress tests
        suite.addTest(StressTests.suite());

        return suite;

    }

}
