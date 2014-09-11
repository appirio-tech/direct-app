/*
 * Copyright (c) 2009, TopCoder, Inc. All rights reserved
 */
package com.topcoder.service.contest.eligibility;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.topcoder.service.contest.eligibility.dao.ContestEligibilityManagerBeanTests;
import com.topcoder.service.contest.eligibility.dao.ContestEligibilityPersistenceExceptionTests;

/**
 * <p>
 * This test case aggregates all Unit test cases.
 * </p>
 *
 * @author TopCoder
 * @version 1.0
 */
@RunWith(Suite.class)
@Suite.SuiteClasses( {DemoTest.class,
    ContestEligibilityManagerBeanTests.class,
    ContestEligibilityPersistenceExceptionTests.class,
    GroupContestEligibilityTests.class,
    ContestEligibilityTests.class})
public class UnitTests {
}