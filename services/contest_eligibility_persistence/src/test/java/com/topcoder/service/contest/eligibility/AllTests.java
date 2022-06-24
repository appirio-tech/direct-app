/**
 * Copyright (c) 2009, TopCoder, Inc. All rights reserved
 */
package com.topcoder.service.contest.eligibility;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.topcoder.service.contest.eligibility.accuracytests.AccuracyTests;
import com.topcoder.service.contest.eligibility.failuretests.FailureTests;
import com.topcoder.service.contest.eligibility.stresstests.StressTests;

/**
 * <p>
 * This test case aggregates all test cases.
 * </p>
 * 
 * @author TopCoder
 * @version 1.0
 */
@RunWith(Suite.class)
@Suite.SuiteClasses( {FailureTests.class,
    StressTests.class,
    AccuracyTests.class,
    UnitTests.class})
public class AllTests {
}