/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.service.failuretests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.cronos.onlinereview.external.ProjectRetrieval;
import com.topcoder.management.phase.PhaseManager;
import com.topcoder.management.project.ProjectManager;
import com.topcoder.management.resource.ResourceManager;
import com.topcoder.management.team.TeamManager;
import com.topcoder.project.service.ConfigurationException;
import com.topcoder.project.service.ProjectServicesException;
import com.topcoder.project.service.impl.ProjectServicesImpl;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;

/**
 * Failure test for <code>{@link com.topcoder.project.service.impl.ProjectServicesImpl}</code> class.
 * 
 * @author mittu
 * @version 1.0
 */
public class ProjectServicesImplTest extends TestCase {
    /**
     * <p>
     * Represents the PhaseManager used for testing.
     * </p>
     */
    private PhaseManager phaseManager;

    /**
     * <p>
     * Represents the ProjectManager used for testing.
     * </p>
     */
    private ProjectManager projectManager;

    /**
     * <p>
     * Represents the ProjectRetrieval used for testing.
     * </p>
     */
    private ProjectRetrieval projectRetrieval;

    /**
     * <p>
     * Represents the ResourceManager used for testing.
     * </p>
     */
    private ResourceManager resourceManager;

    /**
     * <p>
     * Represents the TeamManager used for testing.
     * </p>
     */
    private TeamManager teamManager;

    /**
     * <p>
     * Represents the ProjectServicesImpl for testing.
     * </p>
     */
    private ProjectServicesImpl impl;

    /**
     * Sets up test environment.
     * 
     * @throws Exception
     *             to junit.
     */
    protected void setUp() throws Exception {
        phaseManager = new MockPhaseManager();
        projectManager = new MockProjectManager();
        projectRetrieval = new MockProjectRetrieval();
        resourceManager = new MockResourceManager();
        teamManager = new MockTeamManager();
        impl = new ProjectServicesImpl(projectRetrieval, resourceManager, phaseManager, teamManager,
                projectManager, LogManager.getLog("ProjectServicesFailure"), 1);
    }

    /**
     * Tears down test environment.
     * 
     * @throws Exception
     *             to junit
     */
    protected void tearDown() throws Exception {
        phaseManager = null;
        projectManager = null;
        projectRetrieval = null;
        resourceManager = null;
        teamManager = null;
        impl = null;
        FailureTestHelper.releaseConfigs();
    }

    /**
     * Failure test for <code>{@link ProjectServicesImpl#ProjectServicesImpl(ProjectRetrieval,ResourceManager,
     * PhaseManager,TeamManager,ProjectManager,Log,long)}</code>.
     */
    public void testConstructor_ProjectRetrieval_ResourceManager_PhaseManager_TeamManager_ProjectManager_Log_long_1() {
        try {
            new ProjectServicesImpl(null, resourceManager, phaseManager, teamManager, projectManager,
                    LogManager.getLog("ProjectServicesFailure"), 1);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for <code>{@link ProjectServicesImpl#ProjectServicesImpl(ProjectRetrieval,ResourceManager,
     * PhaseManager,TeamManager,ProjectManager,Log,long)}</code>.
     */
    public void testConstructor_ProjectRetrieval_ResourceManager_PhaseManager_TeamManager_ProjectManager_Log_long_2() {
        try {
            new ProjectServicesImpl(projectRetrieval, null, phaseManager, teamManager, projectManager,
                    LogManager.getLog("ProjectServicesFailure"), 1);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for <code>{@link ProjectServicesImpl#ProjectServicesImpl(ProjectRetrieval,ResourceManager,
     * PhaseManager,TeamManager,ProjectManager,Log,long)}</code>.
     */
    public void testConstructor_ProjectRetrieval_ResourceManager_PhaseManager_TeamManager_ProjectManager_Log_long_3() {
        try {
            new ProjectServicesImpl(projectRetrieval, resourceManager, null, teamManager, projectManager,
                    LogManager.getLog("ProjectServicesFailure"), 1);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for <code>{@link ProjectServicesImpl#ProjectServicesImpl(ProjectRetrieval,ResourceManager,
     * PhaseManager,TeamManager,ProjectManager,Log,long)}</code>.
     */
    public void testConstructor_ProjectRetrieval_ResourceManager_PhaseManager_TeamManager_ProjectManager_Log_long_4() {
        try {
            new ProjectServicesImpl(projectRetrieval, resourceManager, phaseManager, null, projectManager,
                    LogManager.getLog("ProjectServicesFailure"), 1);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for <code>{@link ProjectServicesImpl#ProjectServicesImpl(ProjectRetrieval,ResourceManager,
     * PhaseManager,TeamManager,ProjectManager,Log,long)}</code>.
     */
    public void testConstructor_ProjectRetrieval_ResourceManager_PhaseManager_TeamManager_ProjectManager_Log_long_5() {
        try {
            new ProjectServicesImpl(projectRetrieval, resourceManager, phaseManager, teamManager, null,
                    LogManager.getLog("ProjectServicesFailure"), 1);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for <code>{@link ProjectServicesImpl#ProjectServicesImpl(ProjectRetrieval,ResourceManager,
     * PhaseManager,TeamManager,ProjectManager,Log,long)}</code>.
     */
    public void testConstructor_ProjectRetrieval_ResourceManager_PhaseManager_TeamManager_ProjectManager_Log_long_6() {
        try {
            new ProjectServicesImpl(projectRetrieval, resourceManager, phaseManager, teamManager,
                    projectManager, LogManager.getLog("ProjectServicesFailure"), -1);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for <code>{@link ProjectServicesImpl#ProjectServicesImpl(String)}</code>.
     */
    public void testConstructor_String_1() {
        try {
            new ProjectServicesImpl(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for <code>{@link ProjectServicesImpl#ProjectServicesImpl(String)}</code>.
     */
    public void testConstructor_String_2() {
        try {
            new ProjectServicesImpl("  ");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for <code>{@link ProjectServicesImpl#ProjectServicesImpl(String)}</code>.
     */
    public void testConstructor_String_3() {
        try {
            new ProjectServicesImpl("UnknownNamespace");
            fail("ConfigurationException expected");
        } catch (ConfigurationException e) {
            // expected
        }
    }

    /**
     * Failure test for <code>{@link ProjectServicesImpl#ProjectServicesImpl(String)}</code>.
     * 
     * @throws Exception
     *             if any error occurs
     */
    public void testConstructor_String_4() throws Exception {
        FailureTestHelper.loadConfigs("spec_missing.xml");
        try {
            new ProjectServicesImpl(ProjectServicesImpl.DEFAULT_NAMESPACE);
            fail("ConfigurationException expected");
        } catch (ConfigurationException e) {
            // expected
        }
    }

    /**
     * Failure test for <code>{@link ProjectServicesImpl#ProjectServicesImpl(String)}</code>.
     * 
     * @throws Exception
     *             if any error occurs
     */
    public void testConstructor_String_5() throws Exception {
        FailureTestHelper.loadConfigs("project_retrival_missing.xml");
        try {
            new ProjectServicesImpl(ProjectServicesImpl.DEFAULT_NAMESPACE);
            fail("ConfigurationException expected");
        } catch (ConfigurationException e) {
            // expected
        }
    }

    /**
     * Failure test for <code>{@link ProjectServicesImpl#ProjectServicesImpl(String)}</code>.
     * 
     * @throws Exception
     *             if any error occurs
     */
    public void testConstructor_String_6() throws Exception {
        FailureTestHelper.loadConfigs("resource_manager_missing.xml");
        try {
            new ProjectServicesImpl(ProjectServicesImpl.DEFAULT_NAMESPACE);
            fail("ConfigurationException expected");
        } catch (ConfigurationException e) {
            // expected
        }
    }

    /**
     * Failure test for <code>{@link ProjectServicesImpl#ProjectServicesImpl(String)}</code>.
     * 
     * @throws Exception
     *             if any error occurs
     */
    public void testConstructor_String_7() throws Exception {
        FailureTestHelper.loadConfigs("phase_manager_missing.xml");
        try {
            new ProjectServicesImpl(ProjectServicesImpl.DEFAULT_NAMESPACE);
            fail("ConfigurationException expected");
        } catch (ConfigurationException e) {
            // expected
        }
    }

    /**
     * Failure test for <code>{@link ProjectServicesImpl#ProjectServicesImpl(String)}</code>.
     * 
     * @throws Exception
     *             if any error occurs
     */
    public void testConstructor_String_8() throws Exception {
        FailureTestHelper.loadConfigs("project_manager_missing.xml");
        try {
            new ProjectServicesImpl(ProjectServicesImpl.DEFAULT_NAMESPACE);
            fail("ConfigurationException expected");
        } catch (ConfigurationException e) {
            // expected
        }
    }

    /**
     * Failure test for <code>{@link ProjectServicesImpl#ProjectServicesImpl(String)}</code>.
     * 
     * @throws Exception
     *             if any error occurs
     */
    public void testConstructor_String_9() throws Exception {
        FailureTestHelper.loadConfigs("team_manager_missing.xml");
        try {
            new ProjectServicesImpl(ProjectServicesImpl.DEFAULT_NAMESPACE);
            fail("ConfigurationException expected");
        } catch (ConfigurationException e) {
            // expected
        }
    }

    /**
     * Failure test for <code>{@link ProjectServicesImpl#ProjectServicesImpl(String)}</code>.
     * 
     * @throws Exception
     *             if any error occurs
     */
    public void testConstructor_String_10() throws Exception {
        FailureTestHelper.loadConfigs("active_project_stat_negative.xml");
        try {
            new ProjectServicesImpl(ProjectServicesImpl.DEFAULT_NAMESPACE);
            fail("ConfigurationException expected");
        } catch (ConfigurationException e) {
            // expected
        }
    }

    /**
     * Failure test for <code>{@link ProjectServicesImpl#ProjectServicesImpl(String)}</code>.
     * 
     * @throws Exception
     *             if any error occurs
     */
    public void testConstructor_String_11() throws Exception {
        FailureTestHelper.loadConfigs("project_retrieval_invalid.xml");
        try {
            new ProjectServicesImpl(ProjectServicesImpl.DEFAULT_NAMESPACE);
            fail("ConfigurationException expected");
        } catch (ConfigurationException e) {
            // expected
        }
    }

    /**
     * Failure test for <code>{@link ProjectServicesImpl#ProjectServicesImpl(String)}</code>.
     * 
     * @throws Exception
     *             if any error occurs
     */
    public void testConstructor_String_12() throws Exception {
        FailureTestHelper.loadConfigs("resource_manager_invalid.xml");
        try {
            new ProjectServicesImpl(ProjectServicesImpl.DEFAULT_NAMESPACE);
            fail("ConfigurationException expected");
        } catch (ConfigurationException e) {
            // expected
        }
    }

    /**
     * Failure test for <code>{@link ProjectServicesImpl#ProjectServicesImpl(String)}</code>.
     * 
     * @throws Exception
     *             if any error occurs
     */
    public void testConstructor_String_13() throws Exception {
        FailureTestHelper.loadConfigs("phase_manager_invalid.xml");
        try {
            new ProjectServicesImpl(ProjectServicesImpl.DEFAULT_NAMESPACE);
            fail("ConfigurationException expected");
        } catch (ConfigurationException e) {
            // expected
        }
    }

    /**
     * Failure test for <code>{@link ProjectServicesImpl#ProjectServicesImpl(String)}</code>.
     * 
     * @throws Exception
     *             if any error occurs
     */
    public void testConstructor_String_14() throws Exception {
        FailureTestHelper.loadConfigs("project_manager_invalid.xml");
        try {
            new ProjectServicesImpl(ProjectServicesImpl.DEFAULT_NAMESPACE);
            fail("ConfigurationException expected");
        } catch (ConfigurationException e) {
            // expected
        }
    }

    /**
     * Failure test for <code>{@link ProjectServicesImpl#ProjectServicesImpl(String)}</code>.
     * 
     * @throws Exception
     *             if any error occurs
     */
    public void testConstructor_String_15() throws Exception {
        FailureTestHelper.loadConfigs("team_manager_invalid.xml");
        try {
            new ProjectServicesImpl(ProjectServicesImpl.DEFAULT_NAMESPACE);
            fail("ConfigurationException expected");
        } catch (ConfigurationException e) {
            // expected
        }
    }

    /**
     * Failure test for <code>{@link ProjectServicesImpl#getFullProjectData(long)}</code>.
     */
    public void testMethodGetFullProjectData_long_1() {
        try {
            impl.getFullProjectData(-1);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for <code>{@link ProjectServicesImpl#getFullProjectData(long)}</code>.
     */
    public void testMethodGetFullProjectData_long_2() {
        ((MockPhaseManager) phaseManager).setState((byte) 0x1);
        try {
            impl.getFullProjectData(1);
            fail("ProjectServicesException expected");
        } catch (ProjectServicesException e) {
            // expected
        }
    }

    /**
     * Failure test for <code>{@link ProjectServicesImpl#getFullProjectData(long)}</code>.
     */
    public void testMethodGetFullProjectData_long_3() {
        ((MockProjectManager) projectManager).setState((byte) 0x3);
        try {
            impl.getFullProjectData(1);
            fail("ProjectServicesException expected");
        } catch (ProjectServicesException e) {
            // expected
        }
    }

    /**
     * Failure test for <code>{@link ProjectServicesImpl#getFullProjectData(long)}</code>.
     */
    public void testMethodGetFullProjectData_long_5() {
        ((MockResourceManager) resourceManager).setState((byte) 0x1);
        try {
            impl.getFullProjectData(1);
            fail("ProjectServicesException expected");
        } catch (ProjectServicesException e) {
            // expected
        }
    }

    /**
     * Failure test for <code>{@link ProjectServicesImpl#getFullProjectData(long)}</code>.
     */
    public void testMethodGetFullProjectData_long_6() {
        ((MockResourceManager) resourceManager).setState((byte) 0x2);
        try {
            impl.getFullProjectData(1);
            fail("ProjectServicesException expected");
        } catch (ProjectServicesException e) {
            // expected
        }
    }

    /**
     * Failure test for <code>{@link ProjectServicesImpl#getFullProjectData(long)}</code>.
     */
    public void testMethodGetFullProjectData_long_7() {
        ((MockProjectRetrieval) projectRetrieval).setState((byte) 0x1);
        try {
            impl.getFullProjectData(1);
            fail("ProjectServicesException expected");
        } catch (ProjectServicesException e) {
            // expected
        }
    }

    /**
     * Failure test for <code>{@link ProjectServicesImpl#findFullProjects(Filter)}</code>.
     */
    public void testMethodFindFullProjects_Filter() {
        try {
            impl.findFullProjects(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for <code>{@link ProjectServicesImpl#findActiveProjectsHeaders()}</code>.
     */
    public void testMethodFindActiveProjectsHeaders_1() {
        ((MockProjectManager) projectManager).setState((byte) 0x2);
        try {
            impl.findActiveProjectsHeaders();
            fail("ProjectServicesException expected");
        } catch (ProjectServicesException e) {
            // expected
        }
    }

    /**
     * Failure test for <code>{@link ProjectServicesImpl#findProjectsHeaders(Filter)}</code>.
     */
    public void testMethodFindProjectsHeaders_Filter_1() {
        try {
            impl.findProjectsHeaders(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for <code>{@link ProjectServicesImpl#findProjectsHeaders(Filter)}</code>.
     */
    public void testMethodFindProjectsHeaders_Filter_2() {
        ((MockProjectManager) projectManager).setState((byte) 0x2);
        try {
            impl.findProjectsHeaders(new EqualToFilter("dummy", "dummy"));
            fail("ProjectServicesException expected");
        } catch (ProjectServicesException e) {
            // expected
        }
    }

    /**
     * Returns all tests.
     * 
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(ProjectServicesImplTest.class);
    }
}
