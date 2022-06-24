/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.copilot.accuracytests;

import java.util.ArrayList;
import java.util.List;

import com.topcoder.direct.services.copilot.impl.CopilotProjectServiceImpl;

import org.junit.After;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;


/**
 * Accuracy Tests for {@link CopilotProjectServiceImpl}.
 *
 * @author onsky
 * @version 1.0
 */
public class CopilotProjectServiceImplTests {
    /** {@link CopilotProjectServiceImpl} instance for unit test. */
    private static CopilotProjectServiceImpl instance;

    /**
     * Sets up the environment.
     *
     * @throws Exception to junit
     */
    @Before
    public void setUp() throws Exception {
        instance = new CopilotProjectServiceImpl();
        instance.setCopilotProjectPlanDAO(new MockCopilotProjectPlanDAO());
        instance.setUtilityDAO(new MockutilityDAO());
        instance.setProjectManager(new MockProjectManager());
        instance.setProjectLinkManager(new MockProjectLinkManager());
        instance.setActiveProjectStatusId(2);
        List<Long> ids = new ArrayList<Long>();
        ids.add(1l);
        ids.add(2l);
        ids.add(3l);
        instance.setFailedProjectStatusIds(ids);
        instance.setGenericDAO(new MockCopilotProjectDAO());
        instance.setCopilotProfileDAO(new MockCopilotProfileDAO());
        instance.setPhaseManager(new MockPhaseManager());
    }

    /**
     * Clears the environment.
     */
    @After
    public void tearDown() {
        instance = null;
    }

    /**
     * Accuracy test for the constructor.
     *
     * @throws Exception to junit
     */
    @Test
    public void test_ctor() throws Exception {
        assertNotNull("the object is not initialized", instance);
    }

    /**
     * Accuracy test for the getCopilotProjects, no exception is allowed.
     *
     * @throws Exception to junit
     */
    @Test
    public void test_getCopilotProjects() throws Exception {
        assertTrue(instance.getCopilotProjects(1).size() == 1);
    }

    /**
     * Accuracy test for the getCopilotProjectDTO, no exception is allowed.
     *
     * @throws Exception to junit
     */
    @Test
    public void test_getCopilotProjectDTO() throws Exception {
        assertNotNull(instance.getCopilotProjectDTO(1));
    }
}
