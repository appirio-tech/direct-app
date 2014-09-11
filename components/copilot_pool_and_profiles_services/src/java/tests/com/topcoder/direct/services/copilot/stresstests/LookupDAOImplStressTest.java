/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.copilot.stresstests;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.topcoder.direct.services.copilot.dao.impl.LookupDAOImpl;
import com.topcoder.direct.services.copilot.model.CopilotProfile;
import com.topcoder.direct.services.copilot.model.CopilotProfileStatus;
import com.topcoder.direct.services.copilot.model.CopilotProject;
import com.topcoder.direct.services.copilot.model.CopilotProjectStatus;
import com.topcoder.direct.services.copilot.model.CopilotType;

/**
 * <p>
 * Stress tests for class <code>LookupDAOImpl</code>.
 * </p>
 *
 * @author stevenfrog
 * @version 1.0
 */
public class LookupDAOImplStressTest extends StressBaseTest {
    /**
     * <p>
     * Represent the LookupDAOImpl instance that used to call its method for test. It will be
     * initialized in setUp().
     * </p>
     */
    @Autowired
    @Qualifier("lookupDAO")
    private LookupDAOImpl impl;

    /**
     * <p>
     * Stress test for the method <code>getAllCopilotProfileStatuses()</code>.<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testGetAllCopilotProfileStatuses() throws Exception {
        long startTime = System.currentTimeMillis();

        CopilotProfile copilotProfile1 = createCopilotProfile();
        hibernateTemplate.save(copilotProfile1.getStatus());
        hibernateTemplate.save(copilotProfile1);

        CopilotProfile copilotProfile2 = createCopilotProfile();
        hibernateTemplate.save(copilotProfile2.getStatus());
        hibernateTemplate.save(copilotProfile2);

        for (int i = 0; i < STRESS_TEST_TIME; i++) {

            List<CopilotProfileStatus> result = impl.getAllCopilotProfileStatuses();

            assertEquals("The return value should be same as ", 2, result.size());

            for (CopilotProfileStatus cps : result) {
                if (cps.getId() == copilotProfile1.getStatus().getId()) {
                    assertEquals("The return value should be same as ", copilotProfile1.getStatus().getId(),
                        cps.getId());
                } else {
                    assertEquals("The return value should be same as ", copilotProfile2.getStatus().getId(),
                        cps.getId());
                }
            }
        }

        long endTime = System.currentTimeMillis();
        System.out.println("The stress test of testGetAllCopilotProfileStatuses spend : "
            + (endTime - startTime) + " ms");
    }

    /**
     * <p>
     * Stress test for the method <code>getAllCopilotProjectStatuses()</code>.<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testGetAllCopilotProjectStatuses() throws Exception {
        long startTime = System.currentTimeMillis();

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

        for (int i = 0; i < STRESS_TEST_TIME; i++) {

            List<CopilotProjectStatus> result = impl.getAllCopilotProjectStatuses();

            assertEquals("The return value should be same as ", 2, result.size());

            for (CopilotProjectStatus cps : result) {
                if (cps.getId() == project1.getStatus().getId()) {
                    assertEquals("The return value should be same as ", project1.getStatus().getId(), cps
                            .getId());
                } else {
                    assertEquals("The return value should be same as ", project2.getStatus().getId(), cps
                            .getId());
                }
            }
        }

        long endTime = System.currentTimeMillis();
        System.out.println("The stress test of testGetAllCopilotProjectStatuses spend : "
            + (endTime - startTime) + " ms");
    }

    /**
     * <p>
     * Stress test for the method <code>getAllCopilotTypes()</code>.<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testGetAllCopilotTypes() throws Exception {
        long startTime = System.currentTimeMillis();

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

        for (int i = 0; i < STRESS_TEST_TIME; i++) {

            List<CopilotType> result = impl.getAllCopilotTypes();

            assertEquals("The return value should be same as ", 2, result.size());

            for (CopilotType cps : result) {
                if (cps.getId() == project1.getCopilotType().getId()) {
                    assertEquals("The return value should be same as ", project1.getCopilotType().getId(),
                        cps.getId());
                } else {
                    assertEquals("The return value should be same as ", project2.getCopilotType().getId(),
                        cps.getId());
                }
            }
        }

        long endTime = System.currentTimeMillis();
        System.out.println("The stress test of testGetAllCopilotTypes spend : " + (endTime - startTime)
            + " ms");
    }

}
