/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.util;

import com.topcoder.service.util.gameplan.SoftwareProjectDataTests;
import com.topcoder.service.util.gameplan.StudioProjectDataTests;
import com.topcoder.service.util.gameplan.TCDirectProjectGamePlanDataTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * <p>This test case aggregates all Unit test cases.</p>
 *
 * @author FireIce
 * @version 1.0
 */
@RunWith(Suite.class)
@Suite.SuiteClasses(
        {SoftwareProjectDataTests.class, StudioProjectDataTests.class, TCDirectProjectGamePlanDataTests.class })
public class UnitTests {
}