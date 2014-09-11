/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project.failuretests;

import java.util.Iterator;

import junit.framework.TestCase;

import com.topcoder.management.project.ConfigurationException;
import com.topcoder.management.project.PersistenceException;
import com.topcoder.management.project.Project;
import com.topcoder.management.project.ProjectCategory;
import com.topcoder.management.project.ProjectFilterUtility;
import com.topcoder.management.project.ProjectManagerImpl;
import com.topcoder.management.project.ProjectStatus;
import com.topcoder.management.project.ProjectType;
import com.topcoder.management.project.ValidationException;
import com.topcoder.util.config.ConfigManager;

/**
 * Failure tests for <code>ProjectManagerImpl</code> class.
 *
 * @author vividmxx
 * @version 1.0
 */
public class ProjectManagerImplFailureTests extends TestCase {

    /**
     * The config file used for test.
     */
    private static final String CONFIG = "failure/config.xml";

    /**
     * The invalid namespace which misses "PersistenceClass" property.
     */
    private static final String MISS_PERSISTENCE_CLASS = "FailureTest.MissPersistenceClass";

    /**
     * The invalid namespace with empty "PersistenceClass" property.
     */
    private static final String EMPTY_PERSISTENCE_CLASS = "FailureTest.EmptyPersistenceClass";

    /**
     * The invalid namespace with invalid "PersistenceClass" property.
     */
    private static final String INVALID_PERSISTENCE_CLASS = "FailureTest.InvalidPersistenceClass";

    /**
     * The invalid namespace with invalid "PersistenceClass" property (other type).
     */
    private static final String OTHER_PERSISTENCE_CLASS = "FailureTest.OtherPersistenceClass";

    /**
     * The invalid namespace which misses "ValidatorClass" property.
     */
    private static final String MISS_VALIDATOR_CLASS = "FailureTest.MissValidatorClass";

    /**
     * The invalid namespace with empty "ValidatorClass" property.
     */
    private static final String EMPTY_VALIDATOR_CLASS = "FailureTest.EmptyValidatorClass";

    /**
     * The invalid namespace with invalid "ValidatorClass" property.
     */
    private static final String INVALID_VALIDATOR_CLASS = "FailureTest.InvalidValidatorClass";

    /**
     * The invalid namespace with invalid "ValidatorClass" property (other type).
     */
    private static final String OTHER_VALIDATOR_CLASS = "FailureTest.OtherValidatorClass";

    /**
     * The invalid namespace which misses "SearchBuilderNamespace" property.
     */
    private static final String MISS_SEARCHBUILDERNAMESPACE = "FailureTest.MissSearchBuilderNamespace";

    /**
     * The invalid namespace with invalid "SearchBuilderNamespace" property.
     */
    private static final String EMPTY_SEARCHBUILDERNAMESPACE = "FailureTest.EmptySearchBuilderNamespace";

    /**
     * The invalid namespace with invalid "SearchBuilderNamespace" property.
     */
    private static final String INVALID_SEARCHBUILDERNAMESPACE = "FailureTest.InvalidSearchBuilderNamespace";

    /**
     * The <code>ProjectManagerImpl</code> instance used to test against.
     */
    private ProjectManagerImpl manager = null;

    /**
     * The valid <code>Project</code> instance used for test.
     */
    private Project validProject = null;

    /**
     * The invalid <code>Project</code> instance used for test.
     */
    private Project invalidProject = null;

    /**
     * Set up the test environment.
     *
     * @throws Exception
     *             throw exception to JUnit
     */
    protected void setUp() throws Exception {
        ConfigManager.getInstance().add(CONFIG);
        manager = new ProjectManagerImpl();
        validProject = new Project(
                1, new ProjectCategory(1, "cg", new ProjectType(1, "tp")), new ProjectStatus(1, "st"));
        String name = "";
        for (int i = 0; i < 65; i++) {
            name += "N";
        }
        invalidProject = new Project(
                1, new ProjectCategory(1, name, new ProjectType(1, name)), new ProjectStatus(1, name));
    }

    /**
     * Tear down the test environment.
     *
     * @throws Exception
     *             throw exception to JUnit
     */
    protected void tearDown() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        for (Iterator itr = cm.getAllNamespaces(); itr.hasNext();) {
            cm.removeNamespace((String) itr.next());
        }
    }

    /**
     * Test the constructor with null namespace. IllegalArgumentException should be thrown.
     */
    public void testConstructorWithNullNamespace() {
        try {
            new ProjectManagerImpl(null);
            fail("IllegalArgumentException should be thrown");
        } catch (ConfigurationException e) {
            fail(e.getMessage());
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the constructor with empty namespace. IllegalArgumentException should be thrown.
     */
    public void testConstructorWithEmptyNamespace() {
        try {
            new ProjectManagerImpl(" ");
            fail("IllegalArgumentException should be thrown");
        } catch (ConfigurationException e) {
            fail(e.getMessage());
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the constructor with inexistent namespace. ConfigurationException should be thrown.
     */
    public void testConstructorWithInexistentNamespace() {
        try {
            new ProjectManagerImpl("not exist");
            fail("ConfigurationException should be thrown");
        } catch (ConfigurationException e) {
            // success
        }
    }

    /**
     * Test the constructor with invalid config which miss "PersistenceClass" property. ConfigurationException should be
     * thrown.
     */
    public void testConstructorConfigMissPersistenceClass() {
        try {
            new ProjectManagerImpl(MISS_PERSISTENCE_CLASS);
            fail("ConfigurationException should be thrown");
        } catch (ConfigurationException e) {
            // success
        }
    }

    /**
     * Test the constructor with invalid config with empty "PersistenceClass" property. ConfigurationException should be
     * thrown.
     */
    public void testConstructorConfigEmptyPersistenceClass() {
        try {
            new ProjectManagerImpl(EMPTY_PERSISTENCE_CLASS);
            fail("ConfigurationException should be thrown");
        } catch (ConfigurationException e) {
            // success
        }
    }

    /**
     * Test the constructor with invalid config with invalid "PersistenceClass" property. ConfigurationException should
     * be thrown.
     */
    public void testConstructorConfigInvalidPersistenceClass() {
        try {
            new ProjectManagerImpl(INVALID_PERSISTENCE_CLASS);
            fail("ConfigurationException should be thrown");
        } catch (ConfigurationException e) {
            // success
        }
    }

    /**
     * Test the constructor with invalid config which invalid "PersistenceClass" property (other type).
     * ConfigurationException should be thrown.
     */
    public void testConstructorConfigOtherPersistenceClass() {
        try {
            new ProjectManagerImpl(OTHER_PERSISTENCE_CLASS);
            fail("ConfigurationException should be thrown");
        } catch (ConfigurationException e) {
            // success
        }
    }

    /**
     * Test the constructor with invalid config which miss "ValidatorClass" property. ConfigurationException should be
     * thrown.
     */
    public void testConstructorConfigMissValidatorClass() {
        try {
            new ProjectManagerImpl(MISS_VALIDATOR_CLASS);
            fail("ConfigurationException should be thrown");
        } catch (ConfigurationException e) {
            // success
        }
    }

    /**
     * Test the constructor with invalid config with empty "ValidatorClass" property. ConfigurationException should be
     * thrown.
     */
    public void testConstructorConfigEmptyValidatorClass() {
        try {
            new ProjectManagerImpl(EMPTY_VALIDATOR_CLASS);
            fail("ConfigurationException should be thrown");
        } catch (ConfigurationException e) {
            // success
        }
    }

    /**
     * Test the constructor with invalid config with invalid "ValidatorClass" property. ConfigurationException should be
     * thrown.
     */
    public void testConstructorConfigInvalidValidatorClass() {
        try {
            new ProjectManagerImpl(INVALID_VALIDATOR_CLASS);
            fail("ConfigurationException should be thrown");
        } catch (ConfigurationException e) {
            // success
        }
    }

    /**
     * Test the constructor with invalid config which invalid "ValidatorClass" property (other type).
     * ConfigurationException should be thrown.
     */
    public void testConstructorConfigOtherValidatorClass() {
        try {
            new ProjectManagerImpl(OTHER_VALIDATOR_CLASS);
            fail("ConfigurationException should be thrown");
        } catch (ConfigurationException e) {
            // success
        }
    }

    /**
     * Test the constructor with invalid config which miss "SearchBuilderNamespace" property. ConfigurationException
     * should be thrown.
     */
    public void testConstructorConfigMissSearchBuilderNamespace() {
        try {
            new ProjectManagerImpl(MISS_SEARCHBUILDERNAMESPACE);
            fail("ConfigurationException should be thrown");
        } catch (ConfigurationException e) {
            // success
        }
    }

    /**
     * Test the constructor with invalid config with empty "SearchBuilderNamespace" property. ConfigurationException
     * should be thrown.
     */
    public void testConstructorConfigEmptySearchBuilderNamespace() {
        try {
            new ProjectManagerImpl(EMPTY_SEARCHBUILDERNAMESPACE);
            fail("ConfigurationException should be thrown");
        } catch (ConfigurationException e) {
            // success
        }
    }

    /**
     * Test the constructor with invalid config with invalid "SearchBuilderNamespace" property. ConfigurationException
     * should be thrown.
     */
    public void testConstructorConfigInvalidSearchBuilderNamespace() {
        try {
            new ProjectManagerImpl(INVALID_SEARCHBUILDERNAMESPACE);
            fail("ConfigurationException should be thrown");
        } catch (ConfigurationException e) {
            // success
        }
    }

    /**
     * Test the method <code>createProject(Project, String)</code> with null project. IllegalArgumentException should
     * be thrown.
     */
    public void testCreateProjectWithNullProject() {
        MockProjectPersistence.setException(false);
        try {
            manager.createProject(null, "tc");
            fail("IllegalArgumentException should be thrown.");
        } catch (PersistenceException e) {
            fail(e.getMessage());
        } catch (ValidationException e) {
            fail(e.getMessage());
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>createProject(Project, String)</code> with invalid project. ValidationException should be
     * thrown.
     */
    public void testCreateProjectWithInvalidProject() {
        MockProjectPersistence.setException(false);
        try {
            manager.createProject(invalidProject, "tc");
            fail("ValidationException should be thrown.");
        } catch (PersistenceException e) {
            fail(e.getMessage());
        } catch (ValidationException e) {
            // success
        }
    }

    /**
     * Test the method <code>createProject(Project, String)</code> with null operator. IllegalArgumentException should
     * be thrown.
     */
    public void testCreateProjectWithNullOperator() {
        MockProjectPersistence.setException(false);
        try {
            manager.createProject(validProject, null);
            fail("IllegalArgumentException should be thrown.");
        } catch (PersistenceException e) {
            fail(e.getMessage());
        } catch (ValidationException e) {
            fail(e.getMessage());
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>createProject(Project, String)</code> with null operator. IllegalArgumentException should
     * be thrown.
     */
    public void testCreateProjectWithEmptyOperator() {
        MockProjectPersistence.setException(false);
        try {
            manager.createProject(validProject, " ");
            fail("IllegalArgumentException should be thrown.");
        } catch (PersistenceException e) {
            fail(e.getMessage());
        } catch (ValidationException e) {
            fail(e.getMessage());
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>createProject(Project, String)</code> when under layer persistence throws a
     * PersistenceException.
     */
    public void testCreateProjectWhenPersistenceException() {
        MockProjectPersistence.setException(true);
        try {
            manager.createProject(validProject, "tc");
            fail("PersistenceException should be thrown.");
        } catch (PersistenceException e) {
            // success
        } catch (ValidationException e) {
            fail(e.getMessage());
        }
    }

    /**
     * Test the method <code>updateProject(Project, String, String)</code> with null project. IllegalArgumentException
     * should be thrown.
     */
    public void testUpdateProjectWithNullProject() {
        MockProjectPersistence.setException(false);
        try {
            manager.updateProject(null, "reason", "tc");
            fail("IllegalArgumentException should be thrown.");
        } catch (PersistenceException e) {
            fail(e.getMessage());
        } catch (ValidationException e) {
            fail(e.getMessage());
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>updateProject(Project, String, String)</code> with invalid project. ValidationException
     * should be thrown.
     */
    public void testUpdateProjectWithInvalidProject() {
        MockProjectPersistence.setException(false);
        try {
            manager.updateProject(invalidProject, "reason", "tc");
            fail("ValidationException should be thrown.");
        } catch (PersistenceException e) {
            fail(e.getMessage());
        } catch (ValidationException e) {
            // success
        }
    }

    /**
     * Test the method <code>updateProject(Project, String, String)</code> with null operator.
     * IllegalArgumentException should be thrown.
     */
    public void testUpdateProjectWithNullOperator() {
        MockProjectPersistence.setException(false);
        try {
            manager.updateProject(validProject, "reason", null);
            fail("IllegalArgumentException should be thrown.");
        } catch (PersistenceException e) {
            fail(e.getMessage());
        } catch (ValidationException e) {
            fail(e.getMessage());
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>updateProject(Project, String, String)</code> with null operator.
     * IllegalArgumentException should be thrown.
     */
    public void testUpdateProjectWithEmptyOperator() {
        MockProjectPersistence.setException(false);
        try {
            manager.updateProject(validProject, "reason", " ");
            fail("IllegalArgumentException should be thrown.");
        } catch (PersistenceException e) {
            fail(e.getMessage());
        } catch (ValidationException e) {
            fail(e.getMessage());
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>updateProject(Project, String, String)</code> with null reason. IllegalArgumentException
     * should be thrown.
     */
    public void testUpdateProjectWithNullReason() {
        MockProjectPersistence.setException(false);
        try {
            manager.updateProject(validProject, null, "tc");
            fail("IllegalArgumentException should be thrown.");
        } catch (PersistenceException e) {
            fail(e.getMessage());
        } catch (ValidationException e) {
            fail(e.getMessage());
        } catch (IllegalArgumentException e) {
            // success
        }
    }


    /**
     * Test the method <code>updateProject(Project, String, String)</code> when under layer persistence throws a
     * PersistenceException.
     */
    public void testUpdateProjectWhenPersistenceException() {
        MockProjectPersistence.setException(true);
        try {
            manager.updateProject(validProject, "reason", "tc");
            fail("PersistenceException should be thrown.");
        } catch (PersistenceException e) {
            // success
        } catch (ValidationException e) {
            fail(e.getMessage());
        }
    }

    /**
     * Test the method <code>getProject(long)</code> with negative id. IllegalArgumentException should be thrown.
     */
    public void testGetProjectWithNegativeId() {
        MockProjectPersistence.setException(false);
        try {
            manager.getProject(-1);
            fail("IllegalArgumentException should be thrown.");
        } catch (PersistenceException e) {
            fail(e.getMessage());
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>getProject(long)</code> with zero id. IllegalArgumentException should be thrown.
     */
    public void testGetProjectWithZeroId() {
        MockProjectPersistence.setException(false);
        try {
            manager.getProject(0);
            fail("IllegalArgumentException should be thrown.");
        } catch (PersistenceException e) {
            fail(e.getMessage());
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>getProject(long)</code> when under layer persistence throws a PersistenceException.
     */
    public void testGetProjectWhenPersistenceException() {
        MockProjectPersistence.setException(true);
        try {
            manager.getProject(1);
            fail("PersistenceException should be thrown");
        } catch (PersistenceException e) {
            // success
        }
    }

    /**
     * Test the method <code>getProjects(long[])</code> with null ids. IllegalArgumentException should be thrown.
     */
    public void testGetProjectsWithNullIds() {
        MockProjectPersistence.setException(false);
        try {
            manager.getProjects(null);
            fail("IllegalArgumentException should be thrown");
        } catch (PersistenceException e) {
            fail(e.getMessage());
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>getProjects(long[])</code> with empty ids. IllegalArgumentException should be thrown.
     */
    public void testGetProjectsWithEmptyIds() {
        MockProjectPersistence.setException(false);
        try {
            manager.getProjects(new long[0]);
            fail("IllegalArgumentException should be thrown");
        } catch (PersistenceException e) {
            fail(e.getMessage());
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>getProjects(long[])</code> with invalid ids which contains negative id.
     * IllegalArgumentException should be thrown.
     */
    public void testGetProjectsWithNegativeId() {
        MockProjectPersistence.setException(false);
        try {
            manager.getProjects(new long[] {-1});
            fail("IllegalArgumentException should be thrown");
        } catch (PersistenceException e) {
            fail(e.getMessage());
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>getProjects(long[])</code> with invalid ids which contains zero id.
     * IllegalArgumentException should be thrown.
     */
    public void testGetProjectsWithZeroId() {
        MockProjectPersistence.setException(false);
        try {
            manager.getProjects(new long[] {0});
            fail("IllegalArgumentException should be thrown");
        } catch (PersistenceException e) {
            fail(e.getMessage());
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>getProjects(long[])</code> when under layer persistence throws a PersistenceException.
     */
    public void testGetProjectsWhenPersistenceException() {
        MockProjectPersistence.setException(true);
        try {
            manager.getProjects(new long[] {1});
            fail("PersistenceException should be thrown");
        } catch (PersistenceException e) {
            // success
        }
    }

    /**
     * Test the method <code>searchProjects(Filter)</code> with null filter. IllegalArgumentException should be
     * thrown.
     */
    public void testSearchProjectsWithNullFilter() {
        MockProjectPersistence.setException(false);
        try {
            manager.searchProjects(null);
            fail("IllegalArgumentException should be thrown");
        } catch (PersistenceException e) {
            fail(e.getMessage());
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>getAllProjectTypes()</code> when under layer persistence throws a PersistenceException.
     */
    public void testGetAllProjectTypesWhenPersistenceException() {
        MockProjectPersistence.setException(true);
        try {
            manager.getAllProjectTypes();
            fail("PersistenceException should be thrown");
        } catch (PersistenceException e) {
            // success
        }
    }

    /**
     * Test the method <code>getAllProjectCategories()</code> when under layer persistence throws a
     * PersistenceException.
     */
    public void testGetAllProjectCategoriesWhenPersistenceException() {
        MockProjectPersistence.setException(true);
        try {
            manager.getAllProjectCategories();
            fail("PersistenceException should be thrown");
        } catch (PersistenceException e) {
            // success
        }
    }

    /**
     * Test the method <code>getAllProjectStatuses()</code> when under layer persistence throws a
     * PersistenceException.
     */
    public void testGetAllProjectStatusesWhenPersistenceException() {
        MockProjectPersistence.setException(true);
        try {
            manager.getAllProjectStatuses();
            fail("PersistenceException should be thrown");
        } catch (PersistenceException e) {
            // success
        }
    }

    /**
     * Test the method <code>getAllProjectPropertyTypes()</code> when under layer persistence throws a
     * PersistenceException.
     */
    public void testGetAllProjectPropertyTypesWhenPersistenceException() {
        MockProjectPersistence.setException(true);
        try {
            manager.getAllProjectPropertyTypes();
            fail("PersistenceException should be thrown");
        } catch (PersistenceException e) {
            // success
        }
    }
}
