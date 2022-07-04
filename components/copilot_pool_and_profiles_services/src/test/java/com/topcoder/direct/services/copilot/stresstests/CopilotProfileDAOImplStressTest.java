/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.copilot.stresstests;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.topcoder.direct.services.copilot.dao.impl.CopilotProfileDAOImpl;
import com.topcoder.direct.services.copilot.model.CopilotProfile;

/**
 * <p>
 * Stress tests for class <code>CopilotProfileDAOImpl</code>.
 * </p>
 *
 * @author stevenfrog
 * @version 1.0
 */
public class CopilotProfileDAOImplStressTest extends StressBaseTest {
    /**
     * <p>
     * Represent the CopilotProfileDAOImpl instance that used to call its method for test. It will
     * be initialized in setUp().
     * </p>
     */
    @Autowired
    @Qualifier("copilotProfileDAO")
    private CopilotProfileDAOImpl impl;

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
    public void testGetCopilotProfile() throws Exception {
        long startTime = System.currentTimeMillis();

        CopilotProfile copilotProfile = createCopilotProfile();
        hibernateTemplate.save(copilotProfile.getStatus());
        hibernateTemplate.save(copilotProfile);

        for (int i = 0; i < STRESS_TEST_TIME; i++) {

            CopilotProfile result = impl.getCopilotProfile(copilotProfile.getUserId());

            assertNotNull("The return value should be not null", result);

        }

        long endTime = System.currentTimeMillis();
        System.out.println("The stress test of testGetCopilotProfile spend : " + (endTime - startTime) + " ms");
    }

}
