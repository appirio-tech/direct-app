/*
 * Copyright (C) 2008-2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.project.stresstests;

import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.naming.InitialContext;

import junit.framework.TestCase;

//import org.mockejb.jndi.MockContextFactory;

import com.topcoder.service.project.Project;
import com.topcoder.service.project.ProjectPersistence;
import com.topcoder.service.project.persistence.JPAProjectPersistence;

/**
 * <p>
 * Stress test for JPAProjectPersistence.
 * </p>
 *
 * <p>
 * Version 1.2 (Release Assembly - TC Cockpit Create Project Refactoring Assembly Part Two Assembly 1.0) Change notes:
 *   <ol>
 *     <li>Updated class to make it compilable.</li>
 *   </ol>
 * </p>
 * 
 * @author 80x86
 * @version 1.2
 */
public class JPAProjectPersistenceUnitTests extends TestCase {

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
     * Sets up the testing environment.
     * 
     * @throws Exception
     *             to JUnit
     */
    @Override
    protected void setUp() throws Exception {
//        MockContextFactory.setAsInitial();
        ctx = new InitialContext();
        Properties env = new Properties();
        ctx = new InitialContext(env);
        ctx.bind("java:comp/env/persistence_unit_name", "stresstests");

        projectPersistence = new JPAProjectPersistence();
    }

    /**
     * Clears up the testing environment.
     * 
     * @throws Exception
     *             to JUnit.
     */
    @Override
    protected void tearDown() throws Exception {
        ctx.close();

        super.tearDown();
    }

    /**
     * Stress test for createProject and deleteProject.
     * 
     * @throws Exception
     *             to JUnit
     */
    public void testCreateAndDeleteProject() throws Exception {
        long start = System.currentTimeMillis();
        for (int i = 0; i < StressTests.TIMES; i++) {
            Project project = projectPersistence.createProject(createProject());
            assertNotNull("Never return null.", project);
            assertNotNull("The project id should be updated.", project.getProjectId());
            assertEquals("should be the same value", "stress", project.getName());
            // delete it
            long id = project.getProjectId();
            assertTrue("should exist and be deleted", projectPersistence.deleteProject(id));
            assertFalse("should not exist now", projectPersistence.deleteProject(id));
        }
        long end = System.currentTimeMillis();
        System.out.println("Running createProject and deleteProject for " + StressTests.TIMES + " times costs "
                + (end - start) / 1000.0 + " seconds.");
    }

    /**
     * Stress test for getProject(long).
     * 
     * @throws Exception
     *             to JUnit
     */
    public void testGetProject() throws Exception {
        long start = System.currentTimeMillis();
        for (int i = 0; i < StressTests.TIMES; i++) {
            long id = i % 3 + 1;
            Project project = projectPersistence.getProject(id);

            assertNotNull("The retrieved project should not null.", project);
            assertTrue("Incorrect project name.", project.getName().indexOf("pservice") != -1);
            assertEquals("Incorrect description", "desc", project.getDescription());
            assertNotNull("Incorrect create date", project.getCreateDate());
            assertNotNull("Incorrect modify date", project.getModifyDate());
            if (id != 3) {
                assertTrue("Incorrect competition set.", project.getCompetitions() != null
                        && project.getCompetitions().size() == 2);
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("Running getProject(long) for " + StressTests.TIMES + " times costs " + (end - start)
                / 1000.0 + " seconds.");
    }

    /**
     * Stress test for getProjectsForUser(long).
     * 
     * @throws Exception
     *             to JUnit
     */
    public void testGetProjectsForUser() throws Exception {
        long start = System.currentTimeMillis();
        for (int i = 0; i < StressTests.TIMES; i++) {
            long id = i % 4;
            List<Project> projects = projectPersistence.getProjectsForUser(id);
            if (id == 3) {
                assertNotNull("should not be null", projects);
                assertTrue("but there should be no project", projects.isEmpty());
            } else {
                assertNotNull("should not be null", projects);
                assertTrue("should have projects", projects.size() != 0);
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("Running getProjectsForUser(long) for " + StressTests.TIMES + " times costs "
                + (end - start) / 1000.0 + " seconds.");
    }

    /**
     * Stress test for getAllProjects.
     * 
     * @throws Exception
     *             to JUnit
     */
    public void testGetAllProjects() throws Exception {
        long start = System.currentTimeMillis();
        for (int i = 0; i < StressTests.TIMES; i++) {
            List<Project> projects = projectPersistence.getAllProjects();
            assertNotNull("should not be null", projects);
            assertTrue("should have some projects which are pre-inserted", projects.size() != 0);
        }
        long end = System.currentTimeMillis();
        System.out.println("Running getAllProjects for " + StressTests.TIMES + " times costs " + (end - start) / 1000.0
                + " seconds.");
    }

    /**
     * Stress test for updateProject.
     * 
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateProject() throws Exception {
        // create a project for update
        Project project = projectPersistence.createProject(createProject());
        assertNotNull("Never return null.", project);
        assertNotNull("The project id should be updated.", project.getProjectId());
        assertEquals("should be the same value", "stress", project.getName());
        long id = project.getProjectId();
        long start = System.currentTimeMillis();
        for (int i = 0; i < StressTests.TIMES; i++) {
            String newname = "stress" + i;
            String newdesc = "desc" + i;
            project.setName(newname);
            project.setDescription(newdesc);
            // the bellow code has some confliction with the RS, need confirmation.
            // Competition cp = new Competition();
            // cp.setProject(project);
            // Set<Competition> scp = new HashSet<Competition>();
            // scp.add(cp);
            // project.setCompetitions(scp);
            projectPersistence.updateProject(project);
        }
        long end = System.currentTimeMillis();
        System.out.println("Running updateProject for " + StressTests.TIMES + " times costs " + (end - start) / 1000.0
                + " seconds.");
        // delete the project
        assertTrue("should exist and be deleted", projectPersistence.deleteProject(id));
        assertFalse("should not exist now", projectPersistence.deleteProject(id));
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

        project.setName("stress");
        project.setDescription("project for stress test");
        project.setCreateDate(new Date());
        project.setUserId(1L);

        return project;
    }
}
