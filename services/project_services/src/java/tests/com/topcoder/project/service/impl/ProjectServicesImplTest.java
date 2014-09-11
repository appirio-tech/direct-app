/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.service.impl;

import java.io.File;

import com.topcoder.management.project.Project;
import com.topcoder.management.project.ProjectFilterUtility;
import com.topcoder.project.service.ConfigurationException;
import com.topcoder.project.service.FullProjectData;
import com.topcoder.project.service.ProjectServicesException;

import junit.framework.TestCase;

/**
 * <p>
 * This is a test for <code>ProjectServicesImpl</code> class.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ProjectServicesImplTest extends TestCase {

    /**
     * <p>
     * Represents the namespace.
     * </p>
     */
    private static final String NAMESPACE = "com.topcoder.project.service.impl.ProjectServicesImpl";

    /**
     * <p>
     * Represents an instance of <code>ProjectServicesImpl</code>.
     * </p>
     */
    private ProjectServicesImpl services;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        TestHelper.clearConfig();
        TestHelper.loadXMLConfig("test_files" + File.separator + "configuration.xml");
        services = new ProjectServicesImpl();
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    protected void tearDown() throws Exception {
        TestHelper.clearConfig();
        services = null;
    }

    /**
     * <p>
     * Test for default constructor.
     * </p>
     * <p>
     * Tests it for accuracy, non-null instance should be created.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testDefaultConstructor() throws Exception {
        assertNotNull("'services' should not be null.", services);
    }

    /**
     * <p>
     * Test for constructor with namespace parameter.
     * </p>
     * <p>
     * Tests it against null argument. expects <code>IllegalArgumentException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCtor1WithNullArg() throws Exception {
        try {
            new ProjectServicesImpl(null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for constructor with namespace parameter.
     * </p>
     * <p>
     * Tests it against empty argument. expects <code>IllegalArgumentException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCtor1WithEmptyArg() throws Exception {
        try {
            new ProjectServicesImpl("   ");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for constructor with namespace parameter.
     * </p>
     * <p>
     * Tests it against unknown namespace. expects <code>ConfigurationException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCtor1WithUnknownNamespace() throws Exception {
        TestHelper.clearConfig();
        TestHelper.loadXMLConfig("test_files" + File.separator + "invalid_config" + File.separator
            + "unknown_namespace.xml");
        try {
            new ProjectServicesImpl(NAMESPACE);
            fail("ConfigurationException should be thrown");
        } catch (ConfigurationException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for constructor with namespace parameter.
     * </p>
     * <p>
     * Tests it with invalid configuration with activeProjectStatusId having invalid value type .
     * expects <code>ConfigurationException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCtor1WithActiveProjectStatusIdInvalidType() throws Exception {
        TestHelper.clearConfig();
        TestHelper.loadXMLConfig("test_files" + File.separator + "invalid_config" + File.separator
            + "active_project_status_id_invalid_type.xml");
        try {
            new ProjectServicesImpl(NAMESPACE);
            fail("ConfigurationException should be thrown");
        } catch (ConfigurationException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for constructor with namespace parameter.
     * </p>
     * <p>
     * Tests it with invalid configuration with activeProjectStatusId missing . expects
     * <code>ConfigurationException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCtor1WithActiveProjectStatusIdMissing() throws Exception {
        TestHelper.clearConfig();
        TestHelper.loadXMLConfig("test_files" + File.separator + "invalid_config" + File.separator
            + "active_project_status_id_missing.xml");
        try {
            new ProjectServicesImpl(NAMESPACE);
            fail("ConfigurationException should be thrown");
        } catch (ConfigurationException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for constructor with namespace parameter.
     * </p>
     * <p>
     * Tests it with invalid configuration with activeProjectStatusId negative . expects
     * <code>ConfigurationException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCtor1WithActiveProjectStatusIdNegative() throws Exception {
        TestHelper.clearConfig();
        TestHelper.loadXMLConfig("test_files" + File.separator + "invalid_config" + File.separator
            + "active_project_status_id_negative.xml");
        try {
            new ProjectServicesImpl(NAMESPACE);
            fail("ConfigurationException should be thrown");
        } catch (ConfigurationException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for constructor with namespace parameter.
     * </p>
     * <p>
     * Tests it with invalid configuration with phaseManagerKey missing. expects
     * <code>ConfigurationException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCtor1WithActivePhaseManagerKeyMissing() throws Exception {
        TestHelper.clearConfig();
        TestHelper.loadXMLConfig("test_files" + File.separator + "invalid_config" + File.separator
            + "phase_manager_key_missing.xml");
        try {
            new ProjectServicesImpl(NAMESPACE);
            fail("ConfigurationException should be thrown");
        } catch (ConfigurationException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for constructor with namespace parameter.
     * </p>
     * <p>
     * Tests it with invalid configuration with projectManagerKey missing. expects
     * <code>ConfigurationException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCtor1WithActiveProjectManagerKeyMissing() throws Exception {
        TestHelper.clearConfig();
        TestHelper.loadXMLConfig("test_files" + File.separator + "invalid_config" + File.separator
            + "project_manager_key_missing.xml");
        try {
            new ProjectServicesImpl(NAMESPACE);
            fail("ConfigurationException should be thrown");
        } catch (ConfigurationException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for constructor with namespace parameter.
     * </p>
     * <p>
     * Tests it with invalid configuration with resourceManagerKey missing. expects
     * <code>ConfigurationException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCtor1WithActiveResourceManagerKeyMissing() throws Exception {
        TestHelper.clearConfig();
        TestHelper.loadXMLConfig("test_files" + File.separator + "invalid_config" + File.separator
            + "resource_manager_key_missing.xml");
        try {
            new ProjectServicesImpl(NAMESPACE);
            fail("ConfigurationException should be thrown");
        } catch (ConfigurationException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for constructor with namespace parameter.
     * </p>
     * <p>
     * Tests it with invalid configuration with specNamespace invalid. expects
     * <code>ConfigurationException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCtor1WithSpecNamespaceInvalid() throws Exception {
        TestHelper.clearConfig();
        TestHelper.loadXMLConfig("test_files" + File.separator + "invalid_config" + File.separator
            + "spec_namespace_invalid.xml");
        try {
            new ProjectServicesImpl(NAMESPACE);
            fail("ConfigurationException should be thrown");
        } catch (ConfigurationException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for constructor with namespace parameter.
     * </p>
     * <p>
     * Tests it with invalid configuration with specNamespace missing. expects
     * <code>ConfigurationException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCtor1WithSpecNamespaceMissing() throws Exception {
        TestHelper.clearConfig();
        TestHelper.loadXMLConfig("test_files" + File.separator + "invalid_config" + File.separator
            + "spec_namespace_missing.xml");
        try {
            new ProjectServicesImpl(NAMESPACE);
            fail("ConfigurationException should be thrown");
        } catch (ConfigurationException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for constructor with namespace parameter.
     * </p>
     * <p>
     * Tests it with invalid configuration with teamManager missing. expects
     * <code>ConfigurationException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCtor1WithTeamManagerMissing() throws Exception {
        TestHelper.clearConfig();
        TestHelper.loadXMLConfig("test_files" + File.separator + "invalid_config" + File.separator
            + "team_manager_missing.xml");
        try {
            new ProjectServicesImpl(NAMESPACE);
            fail("ConfigurationException should be thrown");
        } catch (ConfigurationException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for constructor with namespace parameter.
     * </p>
     * <p>
     * Tests it with invalid configuration with userRetrievalKey having invalid type. expects
     * <code>ConfigurationException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCtor1WithUserRetrievalInvalidType() throws Exception {
        TestHelper.clearConfig();
        TestHelper.loadXMLConfig("test_files" + File.separator + "invalid_config" + File.separator
            + "user_retrieval_invalid_type.xml");
        try {
            new ProjectServicesImpl(NAMESPACE);
            fail("ConfigurationException should be thrown");
        } catch (ConfigurationException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for constructor with namespace parameter.
     * </p>
     * <p>
     * Tests it with invalid configuration with userRetrievalKey having invalid key. expects
     * <code>ConfigurationException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCtor1WithUserRetrievalInvalidKey() throws Exception {
        TestHelper.clearConfig();
        TestHelper.loadXMLConfig("test_files" + File.separator + "invalid_config" + File.separator
            + "user_retrieval_key_invalid.xml");
        try {
            new ProjectServicesImpl(NAMESPACE);
            fail("ConfigurationException should be thrown");
        } catch (ConfigurationException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for constructor with namespace parameter.
     * </p>
     * <p>
     * Tests it with invalid configuration with userRetrievalKey key missing. expects
     * <code>ConfigurationException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCtor1WithUserRetrievalKeyMissing() throws Exception {
        TestHelper.clearConfig();
        TestHelper.loadXMLConfig("test_files" + File.separator + "invalid_config" + File.separator
            + "user_retrieval_key_missing.xml");
        try {
            new ProjectServicesImpl(NAMESPACE);
            fail("ConfigurationException should be thrown");
        } catch (ConfigurationException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for constructor with namespace parameter.
     * </p>
     * <p>
     * Tests it with invalid configuration with userRetrievalKey having unknown type. expects
     * <code>ConfigurationException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCtor1WithUserRetrievalKeyUnknownType() throws Exception {
        TestHelper.clearConfig();
        TestHelper.loadXMLConfig("test_files" + File.separator + "invalid_config" + File.separator
            + "user_retrieval_unknown_type.xml");
        try {
            new ProjectServicesImpl(NAMESPACE);
            fail("ConfigurationException should be thrown");
        } catch (ConfigurationException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for constructor with namespace parameter.
     * </p>
     * <p>
     * Tests it with valid configuration, non-null instance should be created.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCtor1Accuracy() throws Exception {
        services = new ProjectServicesImpl(NAMESPACE);
        assertNotNull("'services' should not be null.", services);
    }

    /**
     * <p>
     * Test for constructor will full parameters.
     * </p>
     * <p>
     * Tests it against null projectRetrieval.expects <code>IllegalArgumentException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCtor2WithNullProjectRetrieval() throws Exception {
        try {
            new ProjectServicesImpl(null, new MockResourceManager(), new MockPhaseManager(),
                new MockTeamManager(), new MockProjectManager(), null, 1);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for constructor will full parameters.
     * </p>
     * <p>
     * Tests it against null resourceManager.expects <code>IllegalArgumentException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCtor2WithNullResourceManager() throws Exception {
        try {
            new ProjectServicesImpl(new MockProjectRetrieval(), null, new MockPhaseManager(),
                new MockTeamManager(), new MockProjectManager(), null, 1);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for constructor will full parameters.
     * </p>
     * <p>
     * Tests it against null phaseManager.expects <code>IllegalArgumentException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCtor2WithNullPhaseManager() throws Exception {
        try {
            new ProjectServicesImpl(new MockProjectRetrieval(), new MockResourceManager(), null,
                new MockTeamManager(), new MockProjectManager(), null, 1);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for constructor will full parameters.
     * </p>
     * <p>
     * Tests it against null teamManager.expects <code>IllegalArgumentException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCtor2WithNullTeamManager() throws Exception {
        try {
            new ProjectServicesImpl(new MockProjectRetrieval(), new MockResourceManager(),
                new MockPhaseManager(), null, new MockProjectManager(), null, 1);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for constructor will full parameters.
     * </p>
     * <p>
     * Tests it against null projectManager.expects <code>IllegalArgumentException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCtor2WithNullProjectManager() throws Exception {
        try {
            new ProjectServicesImpl(new MockProjectRetrieval(), new MockResourceManager(),
                new MockPhaseManager(), new MockTeamManager(), null, null, 1);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for constructor will full parameters.
     * </p>
     * <p>
     * Tests it against negative activeProjectStatusId.expects <code>IllegalArgumentException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCtor2WithNegaitveActiveProjectStatusId() throws Exception {
        try {
            new ProjectServicesImpl(new MockProjectRetrieval(), new MockResourceManager(),
                new MockPhaseManager(), new MockTeamManager(), new MockProjectManager(), null, -1);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for constructor will full parameters.
     * </p>
     * <p>
     * Tests it with valid arguments, non-null instance should be created.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testCtor2Accuracy() throws Exception {
        services = new ProjectServicesImpl(new MockProjectRetrieval(), new MockResourceManager(),
            new MockPhaseManager(), new MockTeamManager(), new MockProjectManager(), null, 1);
        assertNotNull("'services' should not  be null.", services);
    }

    /**
     * <p>
     * Test for <code>findActiveProjectsHeaders()</code> method.
     * </p>
     * <p>
     * Tests it against error occurred in persistence, expects <code>ProjectServicesException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testFindActiveProjectsHeadersWithPersistenceErrorOccuring() throws Exception {
        services = new ProjectServicesImpl(new MockProjectRetrieval(), new MockResourceManager(),
            new MockPhaseManager(), new MockTeamManager(), new ErrorProjectManager(), null, 1);
        try {
            services.findActiveProjectsHeaders();
            fail("ProjectServicesException should be thrown.");
        } catch (ProjectServicesException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for <code>findActiveProjectsHeaders()</code> method.
     * </p>
     * <p>
     * Tests it for accuracy, an array containing two elements will be returned, one has id=1, the
     * other has id=2.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testFindActiveProjectsHeadersAccuracy() throws Exception {
        Project[] projects = services.findActiveProjectsHeaders();
        assertEquals("There should be three projects returned.", 3, projects.length);
        boolean f1 = false, f2 = false, f3 = false;
        for (int i = 0; i < projects.length; i++) {
            if (projects[i].getId() == 1) {
                f1 = true;
            }
            if (projects[i].getId() == 3) {
                f2 = true;
            }
            if (projects[i].getId() == 2) {
                f3 = true;
            }
        }
        assertTrue("'f1' should be true.", f1);
        assertTrue("'f2' should be true.", f2);
        assertTrue("'f3' should be true.", f3);
    }

	/**
	 * <p>
	 * Test for <code>findAllTcDirectProjects()</code> method.
	 * </p>
	 * <p>
	 * Tests it for accuracy, an array containing three elements will be returned,
	 * two has tc direct project id=1 and other has id=2.
	 * </p>
	 *
	 * @throws Exception
	 *             to JUnit
	 */
	public void testFindAllTcDirectProjectsAccuracy()throws Exception {
		Project[] projects = services.findAllTcDirectProjects();
		assertEquals("There should be three tc direct projects returned.", 3, projects.length);
		boolean f1 = false, f2 = false, f3 = false;
		if (projects[0].getTcDirectProjectId() == 1) {
			f1 = true;
		}
		if (projects[1].getTcDirectProjectId() == 2) {
			f2 = true;
		}
		if (projects[2].getTcDirectProjectId() == 1) {
			f3 = true;
		}
		assertTrue("'f1' should be true.", f1);
		assertTrue("'f2' should be true.", f2);
		assertTrue("'f3' should be true.", f3);
	}

	/**
	 * <p>
	 * Test for <code>testFindAllTcDirectProjectsForUserAccuracy()</code> method.
	 * </p>
	 * <p>
	 * Tests it for accuracy, an array containing two elements will be returned for user,
	 * and one for user2.
	 * </p>
	 *
	 * @throws Exception
	 *             to JUnit
	 */
	public void testFindAllTcDirectProjectsForUserAccuracy()throws Exception {
		Project[] projects = services.findAllTcDirectProjectsForUser("user");
		assertEquals("There should be two tc direct projects returned.", 2, projects.length);
		boolean f1 = false, f2 = false, f3 = false;
		if (projects[0].getTcDirectProjectId() == 1) {
			f1 = true;
		}
		if (projects[1].getTcDirectProjectId() == 1) {
			f3 = true;
		}
		projects = services.findAllTcDirectProjectsForUser("user2");
		assertEquals("There should be one tc direct projects returned.", 1, projects.length);
		if (projects[0].getTcDirectProjectId() == 2) {
			f2 = true;
		}
		projects = services.findAllTcDirectProjectsForUser("user3");
		assertEquals("There should be no tc direct projects returned.", 0, projects.length);
		assertTrue("'f1' should be true.", f1);
		assertTrue("'f2' should be true.", f2);
		assertTrue("'f3' should be true.", f3);
	}



    /**
     * <p>
     * Test for <code>findProjectsHeaders(filter)</code> method.
     * </p>
     * <p>
     * Tests it against null filter. expects <code>IllegalArgumentException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testFindProjectsHeadersWithNullFilter() throws Exception {
        try {
            services.findProjectsHeaders(null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for <code>findProjectsHeaders(filter)</code> method.
     * </p>
     * <p>
     * Tests it for accuracy, an array containing three elements will be returned, projects ids are:
     * id=1,id=2,id=3.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testFindProjectsHeadersAccuracy() throws Exception {
        Project[] projects = services.findProjectsHeaders(ProjectFilterUtility
            .buildCategoryIdEqualFilter(1));
        assertEquals("There should be 3 elements returned.", 3, projects.length);
        boolean f1 = false, f2 = false, f3 = false;
        for (int i = 0; i < projects.length; i++) {
            if (projects[i].getId() == 1) {
                f1 = true;
            }
            if (projects[i].getId() == 2) {
                f2 = true;
            }
            if (projects[i].getId() == 3) {
                f3 = true;
            }
        }
        assertTrue("'f1' should be true.", f1);
        assertTrue("'f2' should be true.", f2);
        assertTrue("'f3' should be true.", f3);
    }

    /**
     * <p>
     * Test for <code>findProjectsHeaders(filter)</code> method.
     * </p>
     * <p>
     * Tests it against error occurred in persistence, expects <code>ProjectServicesException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testFindProjectsHeadersWithPersistenceErrorOccuring() throws Exception {
        services = new ProjectServicesImpl(new MockProjectRetrieval(), new MockResourceManager(),
            new MockPhaseManager(), new MockTeamManager(), new ErrorProjectManager(), null, 1);
        try {
            services.findProjectsHeaders(ProjectFilterUtility.buildCategoryIdEqualFilter(1));
            fail("ProjectServicesException should be thrown.");
        } catch (ProjectServicesException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for <code>getFullProjectData(projectId)</code> method.
     * </p>
     * <p>
     * Tests it against negative project id. expects <code>IllegalArgumentException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testGetFullProjectDataWithNegativeProjectId() throws Exception {
        try {
            services.getFullProjectData(-1);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for <code>getFullProjectData(projectId)</code> method.
     * </p>
     * <p>
     * Tests it with error occurred when operating PhaseManager, expects
     * <code>ProjectServicesException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testGetFullProjectDataWithErrorInPhaseManager() throws Exception {
        try {
            services.getFullProjectData(10);
            fail("ProjectServicesException should be thrown.");
        } catch (ProjectServicesException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for <code>getFullProjectData(projectId)</code> method.
     * </p>
     * <p>
     * Tests it with error occurred when operating ProjectManager, expects
     * <code>ProjectServicesException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testGetFullProjectDataWithErrorInProjectManager() throws Exception {
        try {
            services.getFullProjectData(11);
            fail("ProjectServicesException should be thrown.");
        } catch (ProjectServicesException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for <code>getFullProjectData(projectId)</code> method.
     * </p>
     * <p>
     * Tests it with error occurred when operating ResourceManager, expects
     * <code>ProjectServicesException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testGetFullProjectDataWithErrorInResourceManager() throws Exception {
        services = new ProjectServicesImpl(new MockProjectRetrieval(), new ErrorResourceManager(),
            new MockPhaseManager(), new MockTeamManager(), new MockProjectManager(), null, 1);
        try {
            services.getFullProjectData(1);
            fail("ProjectServicesException should be thrown.");
        } catch (ProjectServicesException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for <code>getFullProjectData(projectId)</code> method.
     * </p>
     * <p>
     * Tests it with error occurred when operating ProjectRetrieval, expects
     * <code>ProjectServicesException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testGetFullProjectDataWithErrorInProjectRetrieval() throws Exception {
        try {
            services.getFullProjectData(12);
            fail("ProjectServicesException should be thrown.");
        } catch (ProjectServicesException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for <code>getFullProjectData(projectId)</code> method.
     * </p>
     * <p>
     * Tests it for accuracy, a non-null instance of FullProjectData should be returned.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testGetFullProjectDataAccuracy() throws Exception {
        FullProjectData fullData = services.getFullProjectData(1);
        assertEquals("Project id mismatched.", 1, fullData.getProjectHeader().getId());
        assertEquals("Project category mismatched.", 1, fullData.getProjectHeader()
            .getProjectCategory().getId());
        assertEquals("Project status mismatched.", 1, fullData.getProjectHeader()
            .getProjectStatus().getId());
        assertEquals("There should be two resources.", 2, fullData.getResources().length);
        assertEquals("There should be only one team.", 1, fullData.getTeams().length);
        assertEquals("There should be two technologies.", 2, fullData.getTechnologies().length);
    }

    /**
     * <p>
     * Test for <code>findFullProjects(filter)</code> method.
     * </p>
     * <p>
     * Tests it against null filter, expects <code>IllegalArgumentException</code>.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testFindFullProjectsWithNullFilter() throws Exception {
        try {
            services.findFullProjects(null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * <p>
     * Test for <code>findFullProjects(filter)</code> method.
     * </p>
     * <p>
     * Tests it for accuracy with filter(categoryId==1), there should be three projects returned.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testFindFullProjectsAccuracy() throws Exception {
        FullProjectData[] fullProjects = services.findFullProjects(ProjectFilterUtility
            .buildCategoryIdEqualFilter(1));
        assertEquals("There should be three projects returned.", 3, fullProjects.length);
    }

    /**
     * <p>
     * Test for <code>findActiveProjects()</code> method.
     * </p>
     * <p>
     * Tests it for accuracy, there should be two projects(active) returned.
     * </p>
     * @throws Exception
     *             to JUnit
     */
    public void testFindActiveProjectsAccuracy() throws Exception {
        FullProjectData[] fullProjects = services.findActiveProjects();
        assertEquals("There should be three projects returned.", 3, fullProjects.length);
    }
}
