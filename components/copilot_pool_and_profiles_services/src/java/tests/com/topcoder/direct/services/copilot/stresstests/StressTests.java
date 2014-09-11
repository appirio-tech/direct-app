/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.copilot.stresstests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * <p>This test case aggregates all stress test cases.</p>
 *
 * @author stevenfrog
 * @version 1.0
 */
@RunWith(Suite.class)
@Suite.SuiteClasses( {CopilotProfileDAOImplStressTest.class,
    CopilotProjectDAOImplStressTest.class,
    CopilotProjectPlanDAOImplStressTest.class,
    GenericDAOImplStreeTest.class,
    LookupDAOImplStressTest.class,
    UtilityDAOImplStressTest.class
})
public class StressTests {
    // empty
}
