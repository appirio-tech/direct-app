/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.management.project.persistence;

import java.util.Arrays;

import junit.framework.TestCase;

import com.topcoder.management.project.FileType;
import com.topcoder.management.project.PersistenceException;
import com.topcoder.management.project.Prize;
import com.topcoder.management.project.PrizeType;
import com.topcoder.management.project.Project;
import com.topcoder.management.project.ProjectStudioSpecification;

/**
 * <p>
 * This is the demo showing how to use the added functionality of this component. It demonstrates how to CRUD operations
 * for the file types, prizes and studio specification.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.2
 */
public class DemoV12Test extends TestCase {
    /**
     * Represents the <code>InformixProjectPersistence</code> instance for tests.
     */
    private AbstractInformixProjectPersistence persistence;

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     * <p>
     * The necessary data is prepared in database.
     * </p>
     *
     * @throws Exception
     *             if there is any problem.
     */
    protected void setUp() throws Exception {
        TestHelper.prepareConfig();

        persistence = new InformixProjectPersistence("InformixProjectPersistence.CustomNamespace");

        TestHelper.prepareData(persistence);
    }

    /**
     * <p>
     * Cleans up the test environment.
     * </p>
     * <p>
     * The test data in database is cleaned.
     * </p>
     *
     * @throws Exception
     *             if there is any problem.
     */
    protected void tearDown() throws Exception {
        TestHelper.clearConfig();

        TestHelper.clearData(persistence);
    }

    /**
     * <p>
     * Demonstrates how to CRUD operations for the file types, prizes and studio specification.
     * </p>
     *
     * @throws PersistenceException
     *             to JUnit
     */
    @SuppressWarnings("unused")
    public void testDemo() throws PersistenceException {

        // get the sample project object
        Project project = TestHelper.getSampleProject1();

        persistence.createProject(project, "user");

        long projectId = project.getId();

        // get projects by directProjectId
        Project[] projects = persistence.getProjectsByDirectProjectId(1);

        FileType fileType = TestHelper.createFileType("description 1", "extension 1", true, false, 1);

        // create FileType entity
        fileType = persistence.createFileType(fileType, "admin");

        // update FileType entity
        fileType.setBundledFile(true);
        fileType.setSort(2);
        fileType.setExtension("extension3");
        persistence.updateFileType(fileType, "admin");

        // remove FileType entity
        persistence.removeFileType(fileType, "admin");

        fileType = TestHelper.createFileType("description 2", "extension 2", true, false, 1);
        persistence.createFileType(fileType, "admin");

        // get all FileTypes
        FileType[] fileTypes = persistence.getAllFileTypes();

        // get all FileTypes by project id
        fileTypes = persistence.getProjectFileTypes(projectId);

        // update relationship for project and FileTypes
        persistence.updateProjectFileTypes(projectId, Arrays.asList(fileTypes), "admin");

        // get all PrizeTypes
        PrizeType[] prizeTypes = persistence.getPrizeTypes();

        Prize prize = TestHelper.createPrize(1, 600.00, 5);

        // create Prize entity
        prize = persistence.createPrize(prize, "admin");

        // update Prize entity
        prize.setPlace(2);
        prize.setNumberOfSubmissions(10);
        persistence.updatePrize(prize, "admin");

        // remove Prize entity
        persistence.removePrize(prize, "admin");

        // get all Prizes by project id
        Prize[] prizes = persistence.getProjectPrizes(projectId);

        // update relationship for project and Prizes
        persistence.updateProjectPrizes(projectId, Arrays.asList(prizes), "admin");

        ProjectStudioSpecification spec = TestHelper.createProjectStudioSpecification("goal1", "targetAudience",
            "roundOneIntroduction1");
        // create ProjectStudioSpecification entity
        spec = persistence.createProjectStudioSpecification(spec, "admin");

        // update ProjectStudioSpecification entity
        spec.setSubmittersLockedBetweenRounds(false);
        spec.setGoals("goal");
        persistence.updateProjectStudioSpecification(spec, "admin");

        // remove ProjectStudioSpecification entity
        persistence.removeProjectStudioSpecification(spec, "admin");

        // get all ProjectStudioSpecification by project id
        spec = persistence.getProjectStudioSpecification(projectId);

        // update relationship for project and ProjectStudioSpecification
        persistence.updateStudioSpecificationForProject(spec, projectId, "admin");
    }
}
