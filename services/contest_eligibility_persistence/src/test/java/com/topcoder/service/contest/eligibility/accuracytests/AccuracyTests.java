/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.contest.eligibility.accuracytests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * <p>
 * This test case aggregates all accuracy test cases.
 * </p>
 *
 * @author fivestarwy
 * @version 1.0
 */
@RunWith(Suite.class)
@Suite.SuiteClasses( {ContestEligibilityTest.class,
    GroupContestEligibilityTest.class,
    ContestEligibilityManagerBeanTest.class,
    ContestEligibilityPersistenceExceptionTest.class})
public class AccuracyTests {

}
