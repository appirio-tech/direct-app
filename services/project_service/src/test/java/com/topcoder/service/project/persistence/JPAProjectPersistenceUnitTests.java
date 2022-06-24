/*
 * Copyright (C) 2008-2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.project.persistence;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;

import junit.framework.Test;
import junit.framework.TestSuite;

import com.topcoder.service.project.BaseTestCase;
import com.topcoder.service.project.Competition;
import com.topcoder.service.project.ConfigurationException;
import com.topcoder.service.project.Project;
import com.topcoder.service.project.ProjectNotFoundException;
import com.topcoder.service.project.ProjectPersistence;
import com.topcoder.service.project.StudioCompetition;

/**
 * <p>
 * Unit test for <code>{@link JPAProjectPersistence}</code> class.
 * </p>
 *
 * <p>
 * Version 1.2 (Release Assembly - TC Cockpit Create Project Refactoring Assembly Part Two Assembly 1.0) Change notes:
 *   <ol>
 *     <li>Updated class to make it compilable.</li>
 *   </ol>
 * </p>
 *
 * @author TCSDEVELOPER, isv
 * @version 1.2
 */
public class JPAProjectPersistenceUnitTests extends BaseTestCase {

    /**
     * <p>
     * Represents the <code>ProjectPersistence</code> instance for testing.
     * </p>
     */
    private ProjectPersistence projectPersistence;

    /**
     * <p>
     * Represents the <code>InitialContext</code> instance for looking up.
     * </p>
     */
    private InitialContext ctx;

    /**
     * <p>
     * Aggregates all tests in this class.
     * </p>
     *
     * @return test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(JPAProjectPersistenceUnitTests.class);
    }

    /**
     * <p>
     * Setup the testing environment.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();

        executeScriptFile("/clean.sql");

        Properties env = new Properties();
        env.setProperty(Context.SECURITY_PRINCIPAL, "username");
        env.setProperty(Context.SECURITY_CREDENTIALS, "password");
        env.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.security.jndi.JndiLoginInitialContextFactory");
        ctx = new InitialContext(env);

        projectPersistence = (ProjectPersistence) ctx.lookup("project_service/ProjectPersistenceBean/remote");
    }

    /**
     * <p>
     * Tear down the testing environment.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    @Override
    protected void tearDown() throws Exception {
        ctx.close();

        executeScriptFile("/clean.sql");

        super.tearDown();
    }

    /**
     * <p>
     * Unit test for <code>{@link JPAProjectPersistence#JPAProjectPersistence()}</code> constructor.
     * </p>
     * <p>
     * If the required 'persistence_unit_name' property is missing, should throw ConfigurationException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testJPAProjectPersistence_Property_Missing() throws Exception {
        try {
            projectPersistence = (ProjectPersistence) ctx
                    .lookup("project_service/ProjectPersistenceBeanMissingConfig/remote");

            // need to invoke one business method to make the ejb initialized.
            projectPersistence.deleteProject(1);
            fail("Expected ConfigurationException.");
        } catch (RuntimeException e) {
            assertTrue("Fail to construct.", e.getCause() instanceof InvocationTargetException);
            InvocationTargetException exception = (InvocationTargetException) e.getCause();
            assertTrue("Expect ConfigurationException cause.", exception.getCause() instanceof ConfigurationException);
        }
    }

    /**
     * <p>
     * Unit test for <code>{@link JPAProjectPersistence#JPAProjectPersistence()}</code> constructor.
     * </p>
     * <p>
     * If the required 'persistence_unit_name' property is empty, should throw ConfigurationException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testJPAProjectPersistence_Property_Empty() throws Exception {
        try {
            projectPersistence = (ProjectPersistence) ctx
                    .lookup("project_service/ProjectPersistenceBeanEmptyConfig/remote");
            // need to invoke one business method to make the ejb initialized.
            projectPersistence.deleteProject(1);
            fail("Expected ConfigurationException.");
        } catch (RuntimeException e) {
            assertTrue("Fail to construct.", e.getCause() instanceof InvocationTargetException);
            InvocationTargetException exception = (InvocationTargetException) e.getCause();
            assertTrue("Expect ConfigurationException cause.", exception.getCause() instanceof ConfigurationException);
        }
    }

    /**
     * <p>
     * Unit test for <code>{@link JPAProjectPersistence#JPAProjectPersistence()}</code> constructor.
     * </p>
     * <p>
     * If the required 'persistence_unit_name' property is trimmed empty, should throw ConfigurationException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testJPAProjectPersistence_Property_TrimmedEmpty() throws Exception {
        try {
            projectPersistence = (ProjectPersistence) ctx
                    .lookup("project_service/ProjectPersistenceBeanTrimmedEmptyConfig/remote");
            // need to invoke one business method to make the ejb initialized.
            projectPersistence.deleteProject(1);
            fail("Expected ConfigurationException.");
        } catch (RuntimeException e) {
            assertTrue("Fail to construct.", e.getCause() instanceof InvocationTargetException);
            InvocationTargetException exception = (InvocationTargetException) e.getCause();
            assertTrue("Expect ConfigurationException cause.", exception.getCause() instanceof ConfigurationException);
        }
    }

    /**
     * <p>
     * Unit test for <code>{@link JPAProjectPersistence#JPAProjectPersistence()}</code> constructor.
     * </p>
     * <p>
     * If the required 'persistence_unit_name' property is invalid type, should throw ConfigurationException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testJPAProjectPersistence_Property_InvalidType() throws Exception {
        try {
            projectPersistence = (ProjectPersistence) ctx
                    .lookup("project_service/ProjectPersistenceBeanInvalidConfig/remote");
            // need to invoke one business method to make the ejb initialized.
            projectPersistence.deleteProject(1);
            fail("Expected ConfigurationException.");
        } catch (RuntimeException e) {
            assertTrue("Fail to construct.", e.getCause() instanceof InvocationTargetException);
            InvocationTargetException exception = (InvocationTargetException) e.getCause();
            assertTrue("Expect ConfigurationException cause.", exception.getCause() instanceof ConfigurationException);
        }
    }

    /**
     * <p>
     * Unit test for <code>{@link JPAProjectPersistence#JPAProjectPersistence()}</code> constructor.
     * </p>
     * <p>
     * If the 'persistence_unit_name' is not found, should throw ConfigurationException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testJPAProjectPersistence_Persistence_NotFound() throws Exception {
        try {
            projectPersistence = (ProjectPersistence) ctx
                    .lookup("project_service/ProjectPersistenceBeanPersistenceNotFound/remote");
            // need to invoke one business method to make the ejb initialized.
            projectPersistence.deleteProject(1);
            fail("Expected ConfigurationException.");
        } catch (RuntimeException e) {
            assertTrue("Fail to construct.", e.getCause() instanceof InvocationTargetException);
            InvocationTargetException exception = (InvocationTargetException) e.getCause();
            assertTrue("Expect ConfigurationException cause.", exception.getCause() instanceof ConfigurationException);
        }
    }

    /**
     * <p>
     * Unit test for <code>{@link JPAProjectPersistence#JPAProjectPersistence()}</code> constructor.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testJPAProjectPersistence_accuracy() throws Exception {
        try {
            projectPersistence.deleteProject(1);
        } catch (Exception e) {
            fail("No exception should throw.");
        }
    }

    /**
     * <p>
     * Unit test for <code>{@link JPAProjectPersistence#createProject(Project)}</code> method.
     * </p>
     * <p>
     * If the passed project is null, should throw IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testCreateProject_project_null() throws Exception {
        try {
            projectPersistence.createProject(null);
            fail("Expected IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for <code>{@link JPAProjectPersistence#createProject(Project)}</code> method.
     * </p>
     * <p>
     * If the project name is null, should throw IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testCreateProject_project_nullName() throws Exception {
        Project project = createProject();
        project.setName(null);
        try {
            projectPersistence.createProject(project);
            fail("Expected IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for <code>{@link JPAProjectPersistence#createProject(Project)}</code> method.
     * </p>
     * <p>
     * If the project name is empty, should throw IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testCreateProject_project_emptyName() throws Exception {
        Project project = createProject();
        project.setName("");
        try {
            projectPersistence.createProject(project);
            fail("Expected IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for <code>{@link JPAProjectPersistence#createProject(Project)}</code> method.
     * </p>
     * <p>
     * If the project name is trimmed empty, should throw IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testCreateProject_project_trimmedEmptyName() throws Exception {
        Project project = createProject();
        project.setName(" ");
        try {
            projectPersistence.createProject(project);
            fail("Expected IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for <code>{@link JPAProjectPersistence#createProject(Project)}</code> method.
     * </p>
     * <p>
     * If the project create date is null, should throw IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testCreateProject_project_nullCreateDate() throws Exception {
        Project project = createProject();
        project.setCreateDate(null);
        try {
            projectPersistence.createProject(project);
            fail("Expected IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for <code>{@link JPAProjectPersistence#createProject(Project)}</code> method.
     * </p>
     * <p>
     * If the project competition set contains null, should throw IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testCreateProject_project_setContainsNull() throws Exception {
        Project project = createProject();
        Set<Competition> competitions = new HashSet<Competition>();
        competitions.add(null);
        project.setCompetitions(competitions);
        try {
            projectPersistence.createProject(project);
            fail("Expected IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for <code>{@link JPAProjectPersistence#createProject(Project)}</code> method.
     * </p>
     * <p>
     * If the project competition set contains competition not belong to this project, should throw
     * IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testCreateProject_project_IncorrectCompetition() throws Exception {
        Project project = createProject();
        Set<Competition> competitions = new HashSet<Competition>();
        competitions.add(new StudioCompetition("C"));
        project.setCompetitions(competitions);
        try {
            projectPersistence.createProject(project);
            fail("Expected IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for <code>{@link JPAProjectPersistence#createProject(Project)}</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testCreateProject_accuracy1() throws Exception {
        Project project = projectPersistence.createProject(createProject());

        assertNotNull("Never return null.", project);
        assertNotNull("The project id should be updated.", project.getProjectId());

        // verify the result.
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement("SELECT * FROM tc_direct_project WHERE project_id=?");
            pstmt.setLong(1, project.getProjectId());

            rs = pstmt.executeQuery();

            assertTrue("No record found.", rs.next());

            assertEquals("Incorrect name", "Project Service", rs.getString("name"));
            assertEquals("Incorrect description", project.getDescription(), rs.getString("description"));
            assertEquals("Incorrect userId", project.getUserId(), rs.getLong("user_id"));
            assertEquals("incorrect create date.", project.getCreateDate().getTime(), rs.getTimestamp("create_date")
                    .getTime(), 1000);
        } finally {
            closeResultSet(rs);
            closeStatement(pstmt);
            closeConnection(conn);
        }
    }

    /**
     * <p>
     * Unit test for <code>{@link JPAProjectPersistence#getProject(long)}</code> method.
     * </p>
     * <p>
     * If the entity is null, should throw ProjectNotFoundException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetProject_EntityNotFound() throws Exception {
        try {
            projectPersistence.getProject(1);
            fail("Expect ProjectNotFoundException.");
        } catch (ProjectNotFoundException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for <code>{@link JPAProjectPersistence#getProject(long)}</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetProject_accuracy() throws Exception {
        executeSQL(new String[] {"INSERT INTO tc_direct_project (project_id, name, user_id, create_date)"
                + " VALUES (1, 'Project Service', 2, '2008-02-20 12:53:45')"});
        Project project = projectPersistence.getProject(1);

        assertNotNull("The retrieved project should not null.", project);
        assertEquals("Incorrect project name.", "Project Service", project.getName());
        assertNull("Incorrect description", project.getDescription());
        assertNotNull("Incorrect create date", project.getCreateDate());
        assertNull("Incorrect modify date", project.getModifyDate());
        assertEquals("Incorrect user id.", 2, project.getUserId());
        assertTrue("Incorrect competition set.", project.getCompetitions() == null
                || project.getCompetitions().isEmpty());
    }

    /**
     * <p>
     * Unit test for <code>{@link JPAProjectPersistence#getProjectsForUser(long)}</code> method.
     * </p>
     * <p>
     * If no projects related to the passed user id, should return empty list.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetProjectsForUser_accuracy1() throws Exception {
        List<Project> projects = projectPersistence.getProjectsForUser(1);

        assertNotNull("The retrieved project should not null.", projects);
        assertTrue("Incorrect competition set.", projects.isEmpty());
    }

    /**
     * <p>
     * Unit test for <code>{@link JPAProjectPersistence#getProjectsForUser(long)}</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetProjectsForUser_accuracy2() throws Exception {
        executeSQL(new String[] {
            "INSERT INTO tc_direct_project (project_id, name, user_id, create_date)"
                    + " values (1, 'Project Service 1', 2, '2008-02-20 12:53:45')",
            "INSERT INTO tc_direct_project (project_id, name, user_id, create_date)"
                    + " values (2, 'Project Service 2', 2, '2008-02-20 12:53:45')",
            "INSERT INTO tc_direct_project (project_id, name, user_id, create_date)"
                    + " values (3, 'Project Service 3', 3, '2008-02-20 12:53:45')"});
        List<Project> projects = projectPersistence.getProjectsForUser(2);

        assertNotNull("The retrieved project should not null.", projects);
        assertEquals("should return two elements.", 2, projects.size());
    }

    /**
     * <p>
     * Unit test for <code>{@link JPAProjectPersistence#getAllProjects()}</code> method.
     * </p>
     * <p>
     * If no projects exists in persistence, should return empty list.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetAllProjects_accuracy1() throws Exception {
        List<Project> projects = projectPersistence.getAllProjects();

        assertNotNull("The retrieved project should not null.", projects);
        assertTrue("Incorrect competition set.", projects.isEmpty());
    }

    /**
     * <p>
     * Unit test for <code>{@link JPAProjectPersistence#getAllProjects()}</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetAllProjects_accuracy2() throws Exception {
        executeSQL(new String[] {
            "INSERT INTO tc_direct_project (project_id, name, user_id, create_date)"
                    + " values (1, 'Project Service 1', 2, '2008-02-20 12:53:45')",
            "INSERT INTO tc_direct_project (project_id, name, user_id, create_date)"
                    + " values (2, 'Project Service 2', 2, '2008-02-20 12:53:45')",
            "INSERT INTO tc_direct_project (project_id, name, user_id, create_date)"
                    + " values (3, 'Project Service 3', 3, '2008-02-20 12:53:45')"});
        List<Project> projects = projectPersistence.getAllProjects();

        assertNotNull("The retrieved project should not null.", projects);
        assertEquals("should return three elements.", 3, projects.size());
    }

    /**
     * <p>
     * Unit test for <code>{@link JPAProjectPersistence#updateProject(Project)}</code> method.
     * </p>
     * <p>
     * If the passed project is null, should throw IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateProject_project_null() throws Exception {
        try {
            projectPersistence.updateProject(null);
            fail("Expected IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for <code>{@link JPAProjectPersistence#updateProject(Project)}</code> method.
     * </p>
     * <p>
     * If the project id is null, should throw IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateProject_project_nullId() throws Exception {
        Project project = createProject();
        try {
            projectPersistence.updateProject(project);
            fail("Expected IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for <code>{@link JPAProjectPersistence#updateProject(Project)}</code> method.
     * </p>
     * <p>
     * If the project name is empty, should throw IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateProject_project_emptyName() throws Exception {
        Project project = createProject();
        project.setProjectId(1l);
        project.setName("");
        try {
            projectPersistence.updateProject(project);
            fail("Expected IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for <code>{@link JPAProjectPersistence#updateProject(Project)}</code> method.
     * </p>
     * <p>
     * If the project name is trimmed empty, should throw IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateProject_project_trimmedEmptyName() throws Exception {
        Project project = createProject();
        project.setProjectId(1l);
        project.setName(" ");
        try {
            projectPersistence.updateProject(project);
            fail("Expected IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for <code>{@link JPAProjectPersistence#updateProject(Project)}</code> method.
     * </p>
     * <p>
     * If the project competition set contains null, should throw IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateProject_project_setContainsNull() throws Exception {
        Project project = createProject();
        project.setProjectId(1l);
        Set<Competition> competitions = new HashSet<Competition>();
        competitions.add(null);
        project.setCompetitions(competitions);
        try {
            projectPersistence.updateProject(project);
            fail("Expected IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for <code>{@link JPAProjectPersistence#updateProject(Project)}</code> method.
     * </p>
     * <p>
     * If the project competition set contains competition not belong to this project, should throw
     * IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateProject_project_IncorrectCompetition() throws Exception {
        Project project = createProject();
        project.setProjectId(1l);
        Set<Competition> competitions = new HashSet<Competition>();
        competitions.add(new StudioCompetition("C"));
        project.setCompetitions(competitions);
        try {
            projectPersistence.updateProject(project);
            fail("Expected IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for <code>{@link JPAProjectPersistence#updateProject(Project)}</code> method.
     * </p>
     * <p>
     * If no project exists in persistence to update, throw ProjectNotFoundException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateProject_project_NotFound() throws Exception {
        Project project = createProject();
        project.setProjectId(1l);

        try {
            projectPersistence.updateProject(project);
            fail("Expected ProjectNotFoundException.");
        } catch (ProjectNotFoundException e) {
            // expected
        }
    }

    /**
     * <p>
     * Unit test for <code>{@link JPAProjectPersistence#updateProject(Project)}</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testUpdateProject_accuracy() throws Exception {
        executeSQL(new String[] {"INSERT INTO tc_direct_project (project_id, name, user_id, create_date)"
                + " values (1, 'Project Service', 2, '2008-02-20 12:53:45')"});
        Project project = createProject();
        project.setProjectId(1l);
        projectPersistence.updateProject(project);

        // verify the result.
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement("SELECT * FROM tc_direct_project WHERE project_id=?");
            pstmt.setLong(1, project.getProjectId());

            rs = pstmt.executeQuery();

            assertTrue("No record found.", rs.next());

            assertEquals("Incorrect name", "Project Service", rs.getString("name"));
            assertEquals("Incorrect description", project.getDescription(), rs.getString("description"));
            assertEquals("Incorrect userId", project.getUserId(), rs.getLong("user_id"));
            assertEquals("incorrect create date.", project.getCreateDate().getTime(), rs.getTimestamp("create_date")
                    .getTime(), 1000);
        } finally {
            closeResultSet(rs);
            closeStatement(pstmt);
            closeConnection(conn);
        }
    }

    /**
     * <p>
     * Unit test for <code>{@link JPAProjectPersistence#deleteProject(long)}</code> method.
     * </p>
     * <p>
     * If the specified project id does not exist, should return false.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDeleteProject_accuracy1() throws Exception {
        assertFalse("No project is deleted.", projectPersistence.deleteProject(1L));
    }

    /**
     * <p>
     * Unit test for <code>{@link JPAProjectPersistence#deleteProject(long)}</code> method.
     * </p>
     * <p>
     * If the specified project id does exist, it should be deleted.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDeleteProject_accuracy2() throws Exception {
        executeSQL(new String[] {"INSERT INTO tc_direct_project (project_id, name, user_id, create_date)"
                + " values (1, 'Project Service', 2, '2008-02-20 12:53:45')"});

        projectPersistence.deleteProject(1L);

        // verify the result.
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement("SELECT * FROM tc_direct_project WHERE project_id=?");
            pstmt.setLong(1, 1L);

            rs = pstmt.executeQuery();

            assertFalse("No record found.", rs.next());
        } finally {
            closeResultSet(rs);
            closeStatement(pstmt);
            closeConnection(conn);
        }
    }

    /**
     * <p>
     * Creates a new Project instance.
     * </p>
     *
     * @return the updated project.
     */
    private static Project createProject() {
        Project project = new Project();

        project.setName("Project Service");
        project.setDescription("A Web Serice project");
        project.setCreateDate(new Date());
        project.setUserId(1L);

        return project;
    }
}
