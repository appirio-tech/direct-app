/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.copilot.stresstests;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.topcoder.direct.services.copilot.dao.impl.CopilotProjectPlanDAOImpl;
import com.topcoder.direct.services.copilot.model.CopilotProfile;
import com.topcoder.direct.services.copilot.model.CopilotProject;
import com.topcoder.direct.services.copilot.model.CopilotProjectPlan;

/**
 * <p>
 * Stress tests for class <code>CopilotProjectPlanDAOImpl</code>.
 * </p>
 *
 * @author stevenfrog
 * @version 1.0
 */
public class CopilotProjectPlanDAOImplStressTest extends StressBaseTest {
    /**
     * <p>
     * Represent the CopilotProjectPlanDAOImpl instance that used to call its method for test. It
     * will be initialized in setUp().
     * </p>
     */
    @Autowired
    @Qualifier("copilotProjectPlanDAO")
    private CopilotProjectPlanDAOImpl impl;

    /**
     * <p>
     * Stress test for the method <code>getCopilotProfile()</code>.<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testGetCopilotProjectPlan() throws Exception {
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < STRESS_TEST_TIME; i++) {
            CopilotProfile profile1 = createCopilotProfile();
            hibernateTemplate.save(profile1.getStatus());
            hibernateTemplate.save(profile1);

            CopilotProject project1 = createCopilotProject();
            project1.setCopilotProfileId(profile1.getId());
            hibernateTemplate.save(project1.getStatus());
            hibernateTemplate.save(project1.getCopilotType());
            hibernateTemplate.save(project1);

            CopilotProjectPlan projectPlan = createCopilotProjectPlan();
            projectPlan.setCopilotProjectId(project1.getId());
            hibernateTemplate.save(projectPlan);

            CopilotProjectPlan result = impl.getCopilotProjectPlan(project1.getId());

            assertNotNull("The return value should be not null", result);

        }

        long endTime = System.currentTimeMillis();
        System.out.println("The stress test of testGetCopilotProjectPlan spend : " + (endTime - startTime)
            + " ms");
    }

}