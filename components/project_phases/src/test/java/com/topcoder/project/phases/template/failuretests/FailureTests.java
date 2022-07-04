/**
 *
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved
 */
package com.topcoder.project.phases.template.failuretests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Failure test cases.
 * </p>
 * 
 * @author TopCoder
 * @version 1.0
 */
public class FailureTests extends TestCase {

    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(ConverterUtilityFailureTests.class);
        suite.addTestSuite(DBPhaseTemplatePersistenceFailureTests.class);
        suite.addTestSuite(DefaultPhaseTemplateFailureTests1_1.class);
        suite.addTestSuite(XmlPhaseTemplatePersistenceFailureTests1_1.class);

        return suite;
    }
}