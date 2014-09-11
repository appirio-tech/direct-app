/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.copilot;

import junit.framework.TestCase;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.topcoder.direct.services.copilot.accuracytests.AccuracyTests;
import com.topcoder.direct.services.copilot.failuretests.FailureTests;
import com.topcoder.direct.services.copilot.stresstests.StressTests;

/**
 * <p>
 * This test case aggregates all test cases.
 * </p>
 *
 * @author TopCoder
 * @version 1.0
 */
@SuiteClasses({UnitTests.class, FailureTests.class, StressTests.class, AccuracyTests.class} )
@RunWith(Suite.class)
public class AllTests extends TestCase {
}
