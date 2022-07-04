/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.phases.template.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.TestResult;

/**
 * <p>
 * This test case aggregates all Accuracy test cases.
 * </p>
 * @author FireIce
 * @version 1.0
 */
public class AccuracyTests extends TestCase {

    /**
     * <p>
     * Aggregates all Accuracy test cases.
     * </p>
     * @return test suite aggregating all tests.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        
        suite.addTest(ConfigurationExceptionAccuracyTests.suite());
        suite.addTest(PersistenceExceptionAccuracyTests.suite());
        suite.addTest(PhaseTemplateExceptionAccuracyTests.suite());
        suite.addTest(PhaseGenerationExceptionAccuracyTests.suite());
        suite.addTest(StartDateGenerationExceptionAccuracyTests.suite());
        suite.addTest(RelativeWeekTimeStartDateGeneratorAccuracyTests.suite());
        suite.addTest(DefaultPhaseTemplateAccuracyTests.suite());
        suite.addTest(XmlPhaseTemplatePersistenceAccuracyTests.suite());
        return suite;
    }

}
