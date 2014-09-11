/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.direct.services.copilot;

import com.topcoder.direct.services.copilot.dao.CopilotDAOExceptionTests;
import com.topcoder.direct.services.copilot.dao.CopilotDAOInitializationExceptionTests;
import com.topcoder.direct.services.copilot.dao.EntityNotFoundExceptionTests;
import com.topcoder.direct.services.copilot.dao.impl.BaseDAOTests;
import com.topcoder.direct.services.copilot.dao.impl.CopilotProfileDAOImplTests;
import com.topcoder.direct.services.copilot.dao.impl.CopilotProjectDAOImplTests;
import com.topcoder.direct.services.copilot.dao.impl.CopilotProjectPlanDAOImplTests;
import com.topcoder.direct.services.copilot.dao.impl.GenericDAOImplTests;
import com.topcoder.direct.services.copilot.dao.impl.HelperTests;
import com.topcoder.direct.services.copilot.dao.impl.LookupDAOImplTests;
import com.topcoder.direct.services.copilot.dao.impl.UtilityDAOImplTests;
import com.topcoder.direct.services.copilot.model.CopilotProfileInfoTests;
import com.topcoder.direct.services.copilot.model.CopilotProfileInfoTypeTests;
import com.topcoder.direct.services.copilot.model.CopilotProfileStatusTests;
import com.topcoder.direct.services.copilot.model.CopilotProfileTests;
import com.topcoder.direct.services.copilot.model.CopilotProjectInfoTests;
import com.topcoder.direct.services.copilot.model.CopilotProjectInfoTypeTests;
import com.topcoder.direct.services.copilot.model.CopilotProjectPlanTests;
import com.topcoder.direct.services.copilot.model.CopilotProjectStatusTests;
import com.topcoder.direct.services.copilot.model.CopilotProjectTests;
import com.topcoder.direct.services.copilot.model.CopilotTypeTests;
import com.topcoder.direct.services.copilot.model.IdentifiableEntityTests;
import com.topcoder.direct.services.copilot.model.LookupEntityTests;
import com.topcoder.direct.services.copilot.model.PlannedContestTests;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>This test case aggregates all Unit test cases.</p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class UnitTests extends TestCase {

    /**
     * <p>Test suite which aggregates all unit tests.</p>
     *
     * @return test suite which aggregates all unit tests
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTest(Demo.suite());

        suite.addTest(CopilotProfileDAOImplTests.suite());
        suite.addTest(CopilotProjectPlanDAOImplTests.suite());
        suite.addTest(CopilotProjectDAOImplTests.suite());
        suite.addTest(LookupDAOImplTests.suite());
        suite.addTest(UtilityDAOImplTests.suite());
        suite.addTest(GenericDAOImplTests.suite());
        suite.addTest(BaseDAOTests.suite());
        suite.addTest(HelperTests.suite());

        suite.addTest(CopilotDAOExceptionTests.suite());
        suite.addTest(CopilotDAOInitializationExceptionTests.suite());
        suite.addTest(EntityNotFoundExceptionTests.suite());

        suite.addTest(CopilotProfileInfoTests.suite());
        suite.addTest(CopilotProfileInfoTypeTests.suite());
        suite.addTest(CopilotProfileStatusTests.suite());
        suite.addTest(CopilotProfileTests.suite());
        suite.addTest(CopilotProjectInfoTests.suite());
        suite.addTest(CopilotProjectInfoTypeTests.suite());
        suite.addTest(CopilotProjectPlanTests.suite());
        suite.addTest(CopilotProjectStatusTests.suite());
        suite.addTest(CopilotProjectTests.suite());
        suite.addTest(CopilotTypeTests.suite());
        suite.addTest(CopilotTypeTests.suite());
        suite.addTest(IdentifiableEntityTests.suite());
        suite.addTest(LookupEntityTests.suite());
        suite.addTest(PlannedContestTests.suite());

        return suite;
    }

}
