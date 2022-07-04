/*
 * Copyright (C) 2010-2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.project.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Accuracy test cases.
 * </p>
 *
 * <p>
 * Version 1.2 (Release Assembly - TC Cockpit Create Project Refactoring Assembly Part Two Assembly 1.0) Change notes:
 *   <ol>
 *     <li>Updated class to make it compilable.</li>
 *   </ol>
 * </p>
 * @author myxgyy
 * @version 1.2
 */
public class AccuracyTests extends TestCase {

    /**
     * Return all accuracy test cases.
     * @return all accuracy test cases.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTest(ProjectServiceBeanAccTests.suite());
        return suite;
    }

}
