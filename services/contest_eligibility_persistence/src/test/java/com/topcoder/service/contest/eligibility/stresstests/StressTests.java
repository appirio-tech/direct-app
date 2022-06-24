/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.contest.eligibility.stresstests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * <p>
 * This test case aggregates all Stress test cases.
 * </p>
 * 
 * @author KLW
 * @version 1.0
 */
@RunWith(Suite.class)
@Suite.SuiteClasses( {
		ContestEligibilityManagerBeanStressTest.class })
public class StressTests {
	public StressTests() {
	}
}
