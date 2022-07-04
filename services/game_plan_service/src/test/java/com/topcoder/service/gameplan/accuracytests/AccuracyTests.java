/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.gameplan.accuracytests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * <p>
 * This test case aggregates all accuracy test cases.
 * </p>
 * @author vilain
 * @version 1.0
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({GamePlanPersistenceExceptionAccuracyTests.class,
    GamePlanServiceConfigurationExceptionAccuracyTests.class,
	GamePlanServiceExceptionAccuracyTests.class,
	GamePlanServiceBeanAccuracyTests.class})
public class AccuracyTests {
}