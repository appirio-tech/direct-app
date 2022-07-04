/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.service.stresstests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * <p>
 * This test case aggregates all stress test cases.
 * </p>
 *
 * @author woodatxc
 * @version 1.1
 */
public class StressTests extends TestCase {
    /**
     * <p>
     * Aggregates all stress test cases and returns the test suite for them.
     * </p>
     *
     * @return the test suite of all stress test cases.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        // Aggregates all stress test cases for package com.topcoder.project.service
        suite.addTest(ProjectServicesFactoryStressTests.suite());

        // Aggregates all stress test cases for package com.topcoder.project.service.impl
        suite.addTest(ProjectServicesImplStressTest.suite());
        suite.addTest(ProjectServicesImplNewMethodsStressTests.suite());

        // Aggregates all stress test cases for package com.topcoder.project.service.ejb
        suite.addTest(ProjectServicesBeanStressTests.suite());

        return suite;
    }
}

