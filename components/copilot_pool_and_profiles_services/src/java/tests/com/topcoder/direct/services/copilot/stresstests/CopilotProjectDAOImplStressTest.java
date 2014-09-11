/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.copilot.stresstests;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.topcoder.direct.services.copilot.dao.impl.CopilotProjectDAOImpl;
import com.topcoder.direct.services.copilot.model.CopilotProfile;
import com.topcoder.direct.services.copilot.model.CopilotProject;

/**
 * <p>
 * Stress tests for class <code>CopilotProjectDAOImpl</code>.
 * </p>
 *
 * @author stevenfrog
 * @version 1.0
 */
public class CopilotProjectDAOImplStressTest extends StressBaseTest {
    /**
     * <p>
     * Represent the CopilotProjectDAOImpl instance that used to call its method for test. It will
     * be initialized in setUp().
     * </p>
     */
    @Autowired
    @Qualifier("copilotProjectDAO")
    private CopilotProjectDAOImpl impl;

    /**
     * <p>
     * Stress test for the method <code>getCopilotProjects()</code>.<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testGetCopilotProjects() throws Exception {
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

            CopilotProject project2 = createCopilotProject();
            project2.setCopilotProfileId(profile1.getId());
            hibernateTemplate.save(project2.getStatus());
            hibernateTemplate.save(project2.getCopilotType());
            hibernateTemplate.save(project2);

            List<CopilotProject> result = impl.getCopilotProjects(profile1.getId());

            assertEquals("The return value should be same as ", 2, result.size());

            for (CopilotProject cp : result) {
                if (cp.getId() == project1.getId()) {
                    assertEquals("The return value should be same as ", project1.getId(), cp.getId());
                } else {
                    assertEquals("The return value should be same as ", project2.getId(), cp.getId());
                }
            }

        }

        long endTime = System.currentTimeMillis();
        System.out.println("The stress test of testGetCopilotProjects spend : " + (endTime - startTime)
            + " ms");
    }

}
