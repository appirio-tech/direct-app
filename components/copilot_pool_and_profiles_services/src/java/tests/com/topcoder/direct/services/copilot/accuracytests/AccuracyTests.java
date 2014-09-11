/**
 *
 * Copyright (c) 2010, TopCoder, Inc. All rights reserved
 */
package com.topcoder.direct.services.copilot.accuracytests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.topcoder.direct.services.copilot.dao.impl.BaseDAOAccuracyTests;
import com.topcoder.direct.services.copilot.dao.impl.CopilotProfileDAOImplAccuracyTests;
import com.topcoder.direct.services.copilot.dao.impl.CopilotProjectDAOImplAccuracyTests;
import com.topcoder.direct.services.copilot.dao.impl.CopilotProjectPlanDAOImplAccuracyTests;
import com.topcoder.direct.services.copilot.dao.impl.GenericDAOImplAccuracyTests;
import com.topcoder.direct.services.copilot.dao.impl.LookupDAOImplAccuracyTests;

/**
 * <p>
 * This test case aggregates all Accuracy test cases.
 * </p>
 *
 * @author TopCoder
 * @version 1.0
 */
@SuiteClasses( { CopilotProfileAccuracyTests.class, CopilotTypeAccuracyTests.class,
        CopilotProjectStatusAccuracyTests.class, CopilotProjectPlanAccuracyTests.class,
        CopilotProjectInfoTypeAccuracyTests.class, CopilotProjectInfoAccuracyTests.class,
        CopilotProjectAccuracyTests.class, CopilotProfileStatusAccuracyTests.class,
        CopilotProfileInfoTypeAccuracyTests.class, CopilotProfileInfoAccuracyTests.class,
        IdentifiableEntityAccuracyTests.class, LookupEntityAccuracyTests.class, PlannedContestAccuracyTests.class,
        BaseDAOAccuracyTests.class, CopilotProfileDAOImplAccuracyTests.class, CopilotProjectDAOImplAccuracyTests.class,
        CopilotProjectPlanDAOImplAccuracyTests.class, GenericDAOImplAccuracyTests.class,
        LookupDAOImplAccuracyTests.class})
@RunWith(Suite.class)
public class AccuracyTests {
}
