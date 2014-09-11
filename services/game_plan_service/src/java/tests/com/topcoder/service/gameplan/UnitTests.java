/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.gameplan;

import com.topcoder.service.gameplan.ejb.GamePlanServiceBeanTests;
import com.topcoder.service.gameplan.ejb.GamePlanServiceBeanWithJBossTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;


/**
 * <p>This test case aggregates all Unit test cases.</p>
 *
 * @author FireIce
 * @version 1.0
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({GamePlanServiceExceptionTests.class, GamePlanServiceConfigurationExceptionTests.class,
        GamePlanPersistenceExceptionTests.class, GamePlanServiceBeanTests.class, DemoTests.class,
        GamePlanServiceBeanWithJBossTests.class })
public class UnitTests {
}
