/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project.persistence.stresstests;

import junit.framework.TestCase;

import com.topcoder.management.project.Project;
import com.topcoder.management.project.persistence.InformixProjectPersistence;

/**
 * <p>
 * Stress tests for the InformixProjectPersistence class.
 * </p>
 * 
 * @author mgmg
 * @version 1.0
 */
public class InformixProjectPersistenceStressTest extends TestCase {
    /** 
     * Represents the InformixProjectPersistence instance used for testing.
     */
    private InformixProjectPersistence persistence = null;

    /**
     * Set up the test fixture.
     * 
     * @throws Exception
     *             any exception to JUnit.
     */
    protected void setUp() throws Exception {
        StressHelper.unloadConfig();
        StressTestHelper.addConfig();
        StressTestHelper.clearAllTestRecords();
        StressTestHelper.insertTestRecords();
        persistence = new InformixProjectPersistence(StressTestHelper.NAMESPACE);
    }

    /**
     * Tear down the test fixture.
     * 
     * @throws Exception
     *             any exception to JUnit.
     */
    protected void tearDown() throws Exception {
        StressTestHelper.clearAllTestRecords();
        StressTestHelper.clearConfig();
    }
    
    /**
     * Stress Test on createProject(Project, String) for 100 times.
     * 
     * @throws Exception
     *             any exception to JUnit.
     */
    public void testCreateProject_100() throws Exception {
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            persistence.createProject(StressTestHelper.getSampleProject(), "reviewer");
        }
        long dure = System.currentTimeMillis() - startTime;
        System.out.println("InformixProjectPersistence#createProject(Project, String) tested for 100 times, "
                + "takes " + Long.toString(dure) + " ms.");
    }

    /**
     * Stress Test on updateProject(Project, String, String) for 100 times.
     * 
     * @throws Exception
     *             any exception to JUnit.
     */
    public void testUpdateProject_100() throws Exception {
        long startTime = System.currentTimeMillis();
        Project original = StressTestHelper.getSampleProject();
        original.setId(1);
        Project updated = StressTestHelper.getUpdatedProject();
        updated.setId(1);
        for (int i = 0; i < 100; i++) {
            persistence.updateProject(updated, "updated", "reviewer");
            persistence.updateProject(original, "original", "reviewer");
        }
        long dure = System.currentTimeMillis() - startTime;
        System.out.println("InformixProjectPersistence#updateProject(Project, String, String) tested for 100 times, "
                + "takes " + Long.toString(dure) + " ms.");
    }

    /**
     * Stress Test on getProject(long) for 100 times.
     * 
     * @throws Exception
     *             any exception to JUnit.
     */
    public void testGetProject_100() throws Exception {
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            persistence.getProject(1);
            Project project1 = StressTestHelper.getSampleProject();
            persistence.createProject(project1, "user");
            
            // get the project
            Project project2 = persistence.getProject(project1.getId());

            assertEquals("check project id", project1.getId(), project2.getId());
            assertEquals("check project creation user", project1.getCreationUser(), project2
                .getCreationUser());
            assertEquals("check creation date", project1.getCreationTimestamp(), project2
                .getCreationTimestamp());
            assertEquals("check project modification user", project1.getModificationUser(), project2
                .getModificationUser());
            assertEquals("check modification date", project1.getModificationTimestamp(), project2
                .getModificationTimestamp());

            assertEquals("check project status id", project1.getProjectStatus().getId(), project2
                .getProjectStatus().getId());
            assertEquals("check project status name", project1.getProjectStatus().getName(), project2
                .getProjectStatus().getName());

            assertEquals("check project category id", project1.getProjectCategory().getId(), project2
                .getProjectCategory().getId());
            assertEquals("check project category id", project1.getProjectCategory().getName(), project2
                .getProjectCategory().getName());

            assertEquals("check project type id", project1.getProjectCategory().getProjectType()
                .getId(), project2.getProjectCategory().getProjectType().getId());
            assertEquals("check project type id", project1.getProjectCategory().getProjectType()
                .getName(), project2.getProjectCategory().getProjectType().getName());

            assertEquals("check project properties", project1.getAllProperties(), project2
                .getAllProperties());
        }
        long dure = System.currentTimeMillis() - startTime;
        System.out.println("InformixProjectPersistence#getProject(long) tested for 100 times, "
                + "takes " + Long.toString(dure) + " ms.");
    }

    /**
     * Stress Test on getProjects(long[]) for 100 times.
     * 
     * @throws Exception
     *             any exception to JUnit.
     */
    public void testGetProjects_100() throws Exception {
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            persistence.getProject(1);
            Project project1 = StressTestHelper.getSampleProject();
            Project project2 = StressTestHelper.getUpdatedProject();
            persistence.createProject(project1, "reviewer");
            persistence.createProject(project2, "reviewer");
            
            Project [] projects = persistence.getProjects(new long[]{project1.getId(), project2.getId()});

            for (int j = 0; j < projects.length; j++) {
                if (projects[j].getId() == project1.getId()) {
                    assertEquals("check project creation user", project1.getCreationUser(), projects[j]
                        .getCreationUser());
                    assertEquals("check creation date", project1.getCreationTimestamp(), projects[j]
                        .getCreationTimestamp());
                    assertEquals("check project modification user", project1.getModificationUser(), projects[j]
                        .getModificationUser());
                    assertEquals("check modification date", project1.getModificationTimestamp(), projects[j]
                        .getModificationTimestamp());

                    assertEquals("check project status id", project1.getProjectStatus().getId(), projects[j]
                        .getProjectStatus().getId());
                    assertEquals("check project status name", project1.getProjectStatus().getName(),
                        projects[j].getProjectStatus().getName());

                    assertEquals("check project category id", project1.getProjectCategory().getId(),
                        projects[j].getProjectCategory().getId());
                    assertEquals("check project category id", project1.getProjectCategory().getName(),
                        projects[j].getProjectCategory().getName());

                    assertEquals("check project type id", project1.getProjectCategory().getProjectType()
                        .getId(), projects[j].getProjectCategory().getProjectType().getId());
                    assertEquals("check project type id", project1.getProjectCategory().getProjectType()
                        .getName(), projects[j].getProjectCategory().getProjectType().getName());

                    assertEquals("check project properties", project1.getAllProperties(), projects[j]
                        .getAllProperties());
                } else if (projects[j].getId() == project2.getId()) {
                    assertEquals("check project creation user", project2.getCreationUser(), projects[j]
                        .getCreationUser());
                    assertEquals("check creation date", project2.getCreationTimestamp(), projects[j]
                        .getCreationTimestamp());
                    assertEquals("check project modification user", project2.getModificationUser(), projects[j]
                        .getModificationUser());
                    assertEquals("check modification date", project2.getModificationTimestamp(), projects[j]
                        .getModificationTimestamp());

                    assertEquals("check project status id", project2.getProjectStatus().getId(), projects[j]
                        .getProjectStatus().getId());
                    assertEquals("check project status name", project2.getProjectStatus().getName(),
                        projects[j].getProjectStatus().getName());

                    assertEquals("check project category id", project2.getProjectCategory().getId(),
                        projects[j].getProjectCategory().getId());
                    assertEquals("check project category id", project2.getProjectCategory().getName(),
                        projects[j].getProjectCategory().getName());

                    assertEquals("check project type id", project2.getProjectCategory().getProjectType()
                        .getId(), projects[j].getProjectCategory().getProjectType().getId());
                    assertEquals("check project type id", project2.getProjectCategory().getProjectType()
                        .getName(), projects[j].getProjectCategory().getProjectType().getName());

                    assertEquals("check project properties", project2.getAllProperties(), projects[j]
                        .getAllProperties());
                } else {
                    fail("getProjects(long []) failed.");
                }
            }

        }
        long dure = System.currentTimeMillis() - startTime;
        System.out.println("InformixProjectPersistence#getProject(long) tested for 100 times, "
                + "takes " + Long.toString(dure) + " ms.");
    }

}
