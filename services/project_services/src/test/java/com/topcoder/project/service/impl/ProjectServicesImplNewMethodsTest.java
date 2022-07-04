/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.service.impl;

import java.io.File;
import java.util.Date;

import com.topcoder.date.workdays.DefaultWorkdaysFactory;
import com.topcoder.management.project.ProjectCategory;
import com.topcoder.management.project.ProjectStatus;
import com.topcoder.management.project.ProjectType;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.resource.ResourceRole;
import com.topcoder.project.phases.Phase;
import com.topcoder.project.service.ProjectDoesNotExistException;
import com.topcoder.project.service.ProjectServicesException;

import junit.framework.TestCase;

/**
 * <p>
 * This is a test for the new methods added in version 1.1 of <code>ProjectServicesImpl</code>
 * class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.1
 * @since 1.1
 */
public class ProjectServicesImplNewMethodsTest extends TestCase {
    /**
     * <p>
     * Represents an instance of <code>ProjectServicesImpl</code> for test.
     * </p>
     */
    private ProjectServicesImpl services;

    /**
     * <p>
     * Represents an instance of <code>com.topcoder.management.project.Project</code> for test.
     * </p>
     */
    private com.topcoder.management.project.Project projectHeader;

    /**
     * <p>
     * Represents an instance of <code>com.topcoder.project.phases.Project</code> for test.
     * </p>
     */
    private com.topcoder.project.phases.Project projectPhases;

    /**
     * <p>
     * Represents an instance of <code>Phase</code> for test.
     * </p>
     */
    private Phase phase;

    /**
     * <p>
     * Represents an instance of <code>Resource[]</code> for test.
     * </p>
     */
    private Resource[] projectResources;

    /**
     * <p>
     * Represents an instance of <code>Resource</code> for test.
     * </p>
     */
    private Resource resource;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    protected void setUp() throws Exception {
        TestHelper.clearConfig();
        TestHelper.loadXMLConfig("test_files" + File.separator + "configuration.xml");
        services = new ProjectServicesImpl();

        projectHeader = new com.topcoder.management.project.Project(new ProjectCategory(123, "projectCategory",
                new ProjectType(1, "projectType")), new ProjectStatus(2, "projectStatus"));

        projectPhases = new com.topcoder.project.phases.Project(new Date(), new DefaultWorkdaysFactory()
                .createWorkdaysInstance());
        phase = new Phase(projectPhases, 1);
        projectPhases.addPhase(phase);

        resource = new Resource();
        resource.setResourceRole(new ResourceRole());
        projectResources = new Resource[] {resource};
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    protected void tearDown() throws Exception {
        TestHelper.clearConfig();
        services = null;
        projectHeader = null;
        projectPhases = null;
        phase = null;
        projectResources = null;
        resource = null;
    }

    /**
     * <p>
     * Accuracy test for the method <code>createProject</code>.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testCreateProjectAccuracy() throws Exception {
        services.createProject(projectHeader, projectPhases, projectResources, "operator");

        long projectHeaderId = projectHeader.getId();
        // verify the projectHeader.id is set properly.
        assertTrue("The project should be created properly.", projectHeaderId > 0);
        // verify the projectPhases is set to projectHeader.id
        assertEquals("The project should be created properly.", projectHeaderId, projectPhases.getId());

        // verify the projectPhases.phases.ids are set to 0 (id not set).
        assertEquals("The project should be created properly.", 0, phase.getId());
        // verify the projectPhases.phases.projects are set to projectHeader.id
        assertEquals("The project should be created properly.", projectHeaderId, phase.getProject().getId());

        // verify the projectResources.resources.projects are set to projectHeader.id
        assertTrue("The project should be created properly.", projectHeaderId == resource.getProject());
    }

    /**
     * <p>
     * Failure test for the method <code>createProject</code> with ProjectHeader is null,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testCreateProjectWithProjectHeaderNull() throws Exception {
        try {
            services.createProject(null, projectPhases, projectResources, "operator");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Failure test for the method <code>createProject</code> with ProjectPhases is null,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testCreateProjectWithProjectPhasesNull() throws Exception {
        try {
            services.createProject(projectHeader, null, projectResources, "operator");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Failure test for the method <code>createProject</code> with ProjectPhases is empty,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testCreateProjectWithProjectPhasesEmpty() throws Exception {
        projectPhases.clearPhases();

        try {
            services.createProject(projectHeader, projectPhases, projectResources, "operator");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Failure test for the method <code>createProject</code> with the projectPhases.phase.project
     * is not equal to projectPhases, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testCreateProjectWithProjectPhasesInvalid() throws Exception {
        // set the phase.project to a new Project
        TestHelper.setPrivateField(Phase.class, phase, "project", new com.topcoder.project.phases.Project(new Date(),
                new DefaultWorkdaysFactory().createWorkdaysInstance()));

        try {
            services.createProject(projectHeader, projectPhases, projectResources, "operator");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Failure test for the method <code>createProject</code> with projectResources contains null
     * entries, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testCreateProjectWithProjectResourcesContainsNull() throws Exception {
        projectResources = new Resource[] {resource, null};

        try {
            services.createProject(projectHeader, projectPhases, projectResources, "operator");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Failure test for the method <code>createProject</code> with projectResources contains
     * resource.id != Resource.UNSET_ID, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testCreateProjectWithProjectResourcesContainsInvalid1() throws Exception {
        resource.setId(123);

        try {
            services.createProject(projectHeader, projectPhases, projectResources, "operator");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Failure test for the method <code>createProject</code> with projectResources contains
     * resource.getResourceRole() is null, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testCreateProjectWithProjectResourcesInvalid2() throws Exception {
        resource.setResourceRole(null);

        try {
            services.createProject(projectHeader, projectPhases, projectResources, "operator");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Failure test for the method <code>createProject</code> with operator is null,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testCreateProjectWithOperatorNull() throws Exception {
        resource.setResourceRole(null);

        try {
            services.createProject(projectHeader, projectPhases, projectResources, null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Failure test for the method <code>createProject</code> with operator is empty,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testCreateProjectWithOperatorEmpty() throws Exception {
        resource.setResourceRole(null);

        try {
            services.createProject(projectHeader, projectPhases, projectResources, " ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Failure test for the method <code>createProject</code> with PersistenceException occurs
     * while performing the create operation, ProjectServicesException is expected.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testCreateProjectWithPE() throws Exception {
        services = new ProjectServicesImpl(new MockProjectRetrieval(), new MockResourceManager(),
                new MockPhaseManager(), new MockTeamManager(), new ErrorProjectManager(), null, 1);

        try {
            services.createProject(projectHeader, projectPhases, projectResources, "operator");
            fail("ProjectServicesException should be thrown.");
        } catch (ProjectServicesException e) {
            // good
        }
    }

    /**
     * <p>
     * Failure test for the method <code>createProject</code> with ValidationException occurs
     * while performing the create operation, ProjectServicesException is expected.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testCreateProjectWithVE() throws Exception {
        ErrorProjectManager projectManager = new ErrorProjectManager();
        projectManager.setThrowValidationException(true);

        services = new ProjectServicesImpl(new MockProjectRetrieval(), new MockResourceManager(),
                new MockPhaseManager(), new MockTeamManager(), projectManager, null, 1);

        try {
            services.createProject(projectHeader, projectPhases, projectResources, "operator");
            fail("ProjectServicesException should be thrown.");
        } catch (ProjectServicesException e) {
            // good
        }
    }

    /**
     * <p>
     * Failure test for the method <code>createProject</code> with PhaseManagementException occurs
     * while performing the create operation, ProjectServicesException is expected.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testCreateProjectWithPME() throws Exception {
        services = new ProjectServicesImpl(new MockProjectRetrieval(), new MockResourceManager(),
                new ErrorPhaseManager(), new MockTeamManager(), new MockProjectManager(), null, 1);

        try {
            services.createProject(projectHeader, projectPhases, projectResources, "operator");
            fail("ProjectServicesException should be thrown.");
        } catch (ProjectServicesException e) {
            // good
        }
    }

    /**
     * <p>
     * Failure test for the method <code>createProject</code> with ResourcePersistenceException
     * occurs while performing the create operation, ProjectServicesException is expected.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testCreateProjectWithRPE() throws Exception {
        services = new ProjectServicesImpl(new MockProjectRetrieval(), new ErrorResourceManager(),
                new MockPhaseManager(), new MockTeamManager(), new MockProjectManager(), null, 1);

        try {
            services.createProject(projectHeader, projectPhases, projectResources, "operator");
            fail("ProjectServicesException should be thrown.");
        } catch (ProjectServicesException e) {
            // good
        }
    }

    /**
     * <p>
     * Accuracy test for the method <code>updateProject</code>. Verifies the project is updated
     * properly.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateProjectAccuracy() throws Exception {
        initUpdateParameters();
        services.updateProject(projectHeader, "The project is changed", projectPhases, projectResources, "admin");
    }

    /**
     * <p>
     * Failure test for the method <code>updateProject</code> with projectHeader is null,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateProjectWithProjectHeaderNull() throws Exception {
        initUpdateParameters();

        try {
            services.updateProject(null, "The project is changed", projectPhases, projectResources, "admin");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Failure test for the method <code>updateProject</code> with projectHeader.id is
     * nonpositive, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateProjectWithProjectHeaderIdNonpositive() throws Exception {
        initUpdateParameters();
        projectHeader = new com.topcoder.management.project.Project(new ProjectCategory(123, "projectCategory",
                new ProjectType(1, "projectType")), new ProjectStatus(2, "projectStatus"));

        try {
            services.updateProject(projectHeader, "The project is changed", projectPhases, projectResources, "admin");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Failure test for the method <code>updateProject</code> with projectHeaderReason is null,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateProjectWithProjectHeaderReasonNull() throws Exception {
        initUpdateParameters();

        try {
            services.updateProject(projectHeader, null, projectPhases, projectResources, "admin");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Failure test for the method <code>updateProject</code> with projectHeaderReason is empty,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateProjectWithProjectHeaderReasonEmpty() throws Exception {
        initUpdateParameters();

        try {
            services.updateProject(projectHeader, " ", projectPhases, projectResources, "admin");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Failure test for the method <code>updateProject</code> with projectPhases is null,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateProjectWithProjectPhasesNull() throws Exception {
        initUpdateParameters();

        try {
            services.updateProject(projectHeader, "The project is changed", null, projectResources, "admin");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Failure test for the method <code>updateProject</code> with the phases of projectPhases are
     * empty, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateProjectWithProjectPhasesEmpty() throws Exception {
        initUpdateParameters();
        projectPhases.clearPhases();

        try {
            services.updateProject(projectHeader, "The project is changed", projectPhases, projectResources, "admin");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Failure test for the method <code>updateProject</code> with the projectPhases.id is not
     * equal to projectHeader.id, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateProjectWithProjectPhasesInvalidId() throws Exception {
        initUpdateParameters();
        projectPhases.setId(123);

        try {
            services.updateProject(projectHeader, "The project is changed", projectPhases, projectResources, "admin");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Failure test for the method <code>updateProject</code> with the phase.object is not equal
     * to projectPhases, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateProjectWithProjectPhasesInvalidPhase1() throws Exception {
        initUpdateParameters();
        // set the phase.project to a new Project
        TestHelper.setPrivateField(Phase.class, phase, "project", new com.topcoder.project.phases.Project(new Date(),
                new DefaultWorkdaysFactory().createWorkdaysInstance()));

        try {
            services.updateProject(projectHeader, "The project is changed", projectPhases, projectResources, "admin");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Failure test for the method <code>updateProject</code> with the
     * projectPhases.phases.project.id is not equal to projectHeader.id, IllegalArgumentException is
     * expected.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateProjectWithProjectPhasesInvalidPhase2() throws Exception {
        initUpdateParameters();
        phase.getProject().setId(123);

        try {
            services.updateProject(projectHeader, "The project is changed", projectPhases, projectResources, "admin");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Failure test for the method <code>updateProject</code> with the projectResources contains
     * null entries, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateProjectWithProjectResourcesContainsNull() throws Exception {
        initUpdateParameters();
        projectResources = new Resource[] {resource, null};

        try {
            services.updateProject(projectHeader, "The project is changed", projectPhases, projectResources, "admin");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Failure test for the method <code>updateProject</code> with the resource.getResourceRole()
     * is null, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateProjectWithProjectResourcesContainsInvalid1() throws Exception {
        initUpdateParameters();
        resource.setResourceRole(null);

        try {
            services.updateProject(projectHeader, "The project is changed", projectPhases, projectResources, "admin");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Failure test for the method <code>updateProject</code> with the resource role is associated
     * with a phase type but the resource is not associated, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateProjectWithProjectResourcesContainsInvalid2() throws Exception {
        initUpdateParameters();
        resource.setPhase(null);

        try {
            services.updateProject(projectHeader, "The project is changed", projectPhases, projectResources, "admin");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Failure test for the method <code>updateProject</code> with the resource.phase (id of
     * phase) is set but it's not in projectPhases.phases' ids, IllegalArgumentException is
     * expected.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateProjectWithProjectResourcesContainsInvalid4() throws Exception {
        initUpdateParameters();
        // projectPhases.phases' ids = {123L}
        resource.setPhase(321L);
        resource.getResourceRole().setPhaseType(321L);

        try {
            services.updateProject(projectHeader, "The project is changed", projectPhases, projectResources, "admin");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Failure test for the method <code>updateProject</code> with the resource.project (project's
     * id) is not set, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateProjectWithProjectResourcesContainsInvalid5() throws Exception {
        initUpdateParameters();
        resource.setProject(null);

        try {
            services.updateProject(projectHeader, "The project is changed", projectPhases, projectResources, "admin");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Failure test for the method <code>updateProject</code> with the resource.project (project's
     * id) is not equal to projectHeader's id, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateProjectWithProjectResourcesContainsInvalid6() throws Exception {
        initUpdateParameters();
        resource.setProject(2L);

        try {
            services.updateProject(projectHeader, "The project is changed", projectPhases, projectResources, "admin");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Failure test for the method <code>updateProject</code> with the operator is null,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateProjectWithOperatorNull() throws Exception {
        initUpdateParameters();

        try {
            services.updateProject(projectHeader, "The project is changed", projectPhases, projectResources, null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Failure test for the method <code>updateProject</code> with the operator is empty,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateProjectWithOperatorEmpty() throws Exception {
        initUpdateParameters();

        try {
            services.updateProject(projectHeader, "The project is changed", projectPhases, projectResources, "");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Failure test for the method <code>updateProject</code> with ProjectDoesNotExistException
     * occurs while performing the create operation, ProjectDoesNotExistException is expected.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateProjectWithPDE() throws Exception {
        ErrorProjectManager projectManager = new ErrorProjectManager();
        projectManager.setGetProjectReturnNull(true);

        services = new ProjectServicesImpl(new MockProjectRetrieval(), new MockResourceManager(),
                new MockPhaseManager(), new MockTeamManager(), projectManager, null, 1);

        initUpdateParameters();

        try {
            services.updateProject(projectHeader, "The project is changed", projectPhases, projectResources, "admin");
            fail("ProjectDoesNotExistException should be thrown.");
        } catch (ProjectDoesNotExistException e) {
            // good
        }
    }

    /**
     * <p>
     * Failure test for the method <code>updateProject</code> with PersistenceException occurs
     * while performing the create operation, ProjectServicesException is expected.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateProjectWithPE() throws Exception {
        services = new ProjectServicesImpl(new MockProjectRetrieval(), new MockResourceManager(),
                new MockPhaseManager(), new MockTeamManager(), new ErrorProjectManager(), null, 1);

        initUpdateParameters();

        try {
            services.updateProject(projectHeader, "The project is changed", projectPhases, projectResources, "admin");
            fail("ProjectServicesException should be thrown.");
        } catch (ProjectServicesException e) {
            // good
        }
    }

    /**
     * <p>
     * Failure test for the method <code>updateProject</code> with ValidationException occurs
     * while performing the create operation, ProjectServicesException is expected.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateProjectWithVE() throws Exception {
        ErrorProjectManager projectManager = new ErrorProjectManager();
        projectManager.setThrowValidationException(true);

        services = new ProjectServicesImpl(new MockProjectRetrieval(), new MockResourceManager(),
                new MockPhaseManager(), new MockTeamManager(), projectManager, null, 1);

        initUpdateParameters();

        try {
            services.updateProject(projectHeader, "The project is changed", projectPhases, projectResources, "admin");
            fail("ProjectServicesException should be thrown.");
        } catch (ProjectServicesException e) {
            // good
        }
    }

    /**
     * <p>
     * Failure test for the method <code>updateProject</code> with PhaseManagementException occurs
     * while performing the create operation, ProjectServicesException is expected.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateProjectWithPME() throws Exception {
        services = new ProjectServicesImpl(new MockProjectRetrieval(), new MockResourceManager(),
                new ErrorPhaseManager(), new MockTeamManager(), new MockProjectManager(), null, 1);

        initUpdateParameters();

        try {
            services.updateProject(projectHeader, "The project is changed", projectPhases, projectResources, "admin");
            fail("ProjectServicesException should be thrown.");
        } catch (ProjectServicesException e) {
            // good
        }
    }

    /**
     * <p>
     * Failure test for the method <code>updateProject</code> with ResourcePersistenceException
     * occurs while performing the create operation, ProjectServicesException is expected.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateProjectWithRPE() throws Exception {
        services = new ProjectServicesImpl(new MockProjectRetrieval(), new ErrorResourceManager(),
                new MockPhaseManager(), new MockTeamManager(), new MockProjectManager(), null, 1);

        initUpdateParameters();

        try {
            services.updateProject(projectHeader, "The project is changed", projectPhases, projectResources, "admin");
            fail("ProjectServicesException should be thrown.");
        } catch (ProjectServicesException e) {
            // good
        }
    }

    /**
     * <p>
     * Initializes the parameters to call the <code>updateProject</code> method.
     * </p>
     */
    private void initUpdateParameters() {
        long projectHeaderId = 1;
        projectHeader.setId(projectHeaderId);
        projectPhases.setId(projectHeaderId);
        resource.setProject(projectHeaderId);

        phase.setId(123);
        resource.setPhase(123L);
        resource.getResourceRole().setPhaseType(123L);
    }
}
