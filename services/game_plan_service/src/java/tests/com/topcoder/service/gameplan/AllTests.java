/**
 *
 * Copyright (c) 2010, TopCoder, Inc. All rights reserved
 */
package com.topcoder.service.gameplan;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import com.topcoder.service.gameplan.accuracytests.AccuracyTests;
import com.topcoder.service.gameplan.failuretests.FailureTests;
import com.topcoder.service.gameplan.stresstests.StressTests;

/**
 * <p>This test case aggregates all test cases.</p>
 *
 * @author TopCoder
 * @version 1.0
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({UnitTests.class, FailureTests.class, StressTests.class, AccuracyTests.class})
public class AllTests {
}