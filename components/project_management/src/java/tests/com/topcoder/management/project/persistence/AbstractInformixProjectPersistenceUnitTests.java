/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project.persistence;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.management.project.ConfigurationException;
import com.topcoder.management.project.FileType;
import com.topcoder.management.project.PersistenceException;
import com.topcoder.management.project.Prize;
import com.topcoder.management.project.PrizeType;
import com.topcoder.management.project.Project;
import com.topcoder.management.project.ProjectStudioSpecification;
import com.topcoder.management.project.persistence.Helper.DataType;

/**
 * <p>
 * Unit test for the new APIs in <code>AbstractInformixProjectPersistence</code> of version 1.2.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.2
 * @since 1.2
 */
public class AbstractInformixProjectPersistenceUnitTests extends TestCase {
    /**
     * Represents the <code>InformixProjectPersistence</code> instance which is used to test the functionality of
     * <code>AbstractInformixProjectPersistence</code>.
     *
     * @since 1.2
     */
    private AbstractInformixProjectPersistence persistence;

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     * <p>
     * Sets up the necessary configuration and prepares the test data.
     * </p>
     *
     * @throws Exception
     *             if there is any problem.
     */
    @Before
    public void setUp() throws Exception {
        TestHelper.prepareConfig();

        persistence = new InformixProjectPersistence("InformixProjectPersistence.CustomNamespace");

        TestHelper.prepareData(persistence);
    }

    /**
     * <p>
     * Cleans up the test environment.
     * </p>
     * <p>
     * Cleans all the registered namespaces.
     * </p>
     *
     * @throws Exception
     *             if there is any problem.
     */
    @After
    public void tearDown() throws Exception {
        TestHelper.clearConfig();

        TestHelper.clearData(persistence);
    }

    /**
     * <p>
     * Accuracy test of the constructor <code>InformixProjectPersistence(String namespace)</code>.
     * </p>
     * <p>
     * An instance of InformixProjectPersistence can be created. All properties are provided.
     * </p>
     *
     * @throws Exception
     *             throw any exception to JUnit
     */
    @Test
    public void test_constructor1() throws Exception {
        assertNotNull("Unable to create InformixProjectPersistence.", persistence);
    }

    /**
     * <p>
     * Accuracy test of the constructor <code>InformixProjectPersistence(String namespace)</code>.
     * </p>
     * <p>
     * The FileTypeIdGeneratorSequenceName is not provided. ConfigurationException is expected.
     * </p>
     *
     * @throws Exception
     *             throw any exception to JUnit
     */
    @Test
    public void test_constructor2() throws Exception {
        new InformixProjectPersistence("InformixProjectPersistence.CustomNamespace.NoFileTypeIdGeneratorSequenceName");
        assertNotNull("Unable to create InformixProjectPersistence.", persistence);
    }

    /**
     * <p>
     * Accuracy test of the constructor <code>InformixProjectPersistence(String namespace)</code>.
     * </p>
     * <p>
     * The PrizeIdGeneratorSequenceName is not provided. ConfigurationException is expected.
     * </p>
     *
     * @throws Exception
     *             throw any exception to JUnit
     */
    @Test
    public void test_constructor3() throws Exception {
        new InformixProjectPersistence("InformixProjectPersistence.CustomNamespace.NoPrizeIdGeneratorSequenceName");
        assertNotNull("Unable to create InformixProjectPersistence.", persistence);
    }

    /**
     * <p>
     * Accuracy test of the constructor <code>InformixProjectPersistence(String namespace)</code>.
     * </p>
     * <p>
     * The StudioSpecIdGeneratorSequenceName is not provided. ConfigurationException is expected.
     * </p>
     *
     * @throws Exception
     *             throw any exception to JUnit
     */
    @Test
    public void test_constructor4() throws Exception {
        new InformixProjectPersistence(
            "InformixProjectPersistence.CustomNamespace.NoStudioSpecIdGeneratorSequenceName");
        assertNotNull("Unable to create InformixProjectPersistence.", persistence);
    }

    /**
     * <p>
     * Failure test of the constructor <code>InformixProjectPersistence(String namespace)</code>.
     * </p>
     * <p>
     * FileTypeIdGeneratorSequenceName is empty (trimmed). ConfigurationException is expected.
     * </p>
     *
     * @throws Exception
     *             throw any exception to JUnit
     */
    @Test
    public void test_constructor_failure1() throws Exception {
        try {
            new InformixProjectPersistence(
                "InformixProjectPersistence.CustomNamespace.EmptyFileTypeIdGeneratorSequenceName");
            fail("ConfigurationException is expected");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Failure test of the constructor <code>InformixProjectPersistence(String namespace)</code>.
     * </p>
     * <p>
     * the id sequence name provided by FileTypeIdGeneratorSequenceName does not exist. PersistenceException is
     * expected.
     * </p>
     *
     * @throws Exception
     *             throw any exception to JUnit
     */
    @Test
    public void test_constructor_failure2() throws Exception {
        try {
            new InformixProjectPersistence(
                "InformixProjectPersistence.CustomNamespace.NonExistFileTypeIdGeneratorSequenceName");
            fail("ConfigurationException is expected");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * <p>
     * Failure test of the constructor <code>InformixProjectPersistence(String namespace)</code>.
     * </p>
     * <p>
     * PrizeIdGeneratorSequenceName is empty (trimmed). ConfigurationException is expected.
     * </p>
     *
     * @throws Exception
     *             throw any exception to JUnit
     */
    @Test
    public void test_constructor_failure3() throws Exception {
        try {
            new InformixProjectPersistence(
                "InformixProjectPersistence.CustomNamespace.EmptyPrizeIdGeneratorSequenceName");
            fail("ConfigurationException is expected");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Failure test of the constructor <code>InformixProjectPersistence(String namespace)</code>.
     * </p>
     * <p>
     * the id sequence name provided by PrizeIdGeneratorSequenceName does not exist. PersistenceException is expected.
     * </p>
     *
     * @throws Exception
     *             throw any exception to JUnit
     */
    @Test
    public void test_constructor_failure4() throws Exception {
        try {
            new InformixProjectPersistence(
                "InformixProjectPersistence.CustomNamespace.NonExistPrizeIdGeneratorSequenceName");
            fail("ConfigurationException is expected");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * <p>
     * Failure test of the constructor <code>InformixProjectPersistence(String namespace)</code>.
     * </p>
     * <p>
     * StudioSpecIdGeneratorSequenceName is empty (trimmed). ConfigurationException is expected.
     * </p>
     *
     * @throws Exception
     *             throw any exception to JUnit
     */
    @Test
    public void test_constructor_failure5() throws Exception {
        try {
            new InformixProjectPersistence(
                "InformixProjectPersistence.CustomNamespace.EmptyStudioSpecIdGeneratorSequenceName");
            fail("ConfigurationException is expected");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Failure test of the constructor <code>InformixProjectPersistence(String namespace)</code>.
     * </p>
     * <p>
     * the id sequence name provided by StudioSpecIdGeneratorSequenceName does not exist. PersistenceException is
     * expected.
     * </p>
     *
     * @throws Exception
     *             throw any exception to JUnit
     */
    @Test
    public void test_constructor_failure6() throws Exception {
        try {
            new InformixProjectPersistence(
                "InformixProjectPersistence.CustomNamespace.NonExistStudioSpecIdGeneratorSequenceName");
            fail("ConfigurationException is expected");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * <p>
     * Accuracy test for the method <code>createProject(Project project, String operator)</code>.
     * </p>
     * <p>
     * Check if the project is created in the database correctly.
     * </p>
     *
     * @throws Exception
     *             throw any exception to JUnit
     */
    @Test
    public void test_createProject() throws Exception {
        // get the sample project object
        Project project = TestHelper.getSampleProject1();

        persistence.createProject(project, "user");

        // first check if the project's properties are updated.
        assertEquals("check project creation user", "user", project.getCreationUser());
        assertTrue("check creation date is near current time", (new Date().getTime() - project.getCreationTimestamp()
            .getTime()) <= 10 * 60 * 1000);
        assertEquals("check project modification user", "user", project.getModificationUser());
        assertTrue("check modification date is near current time", (new Date().getTime() - project
            .getCreationTimestamp().getTime()) <= 10 * 60 * 1000);

        // then check if the project is stored in the database.
        assertProject(project);
    }

    /**
     * <p>
     * Accuracy test for the method <code>updateProject(Project project, String operator)</code>.
     * </p>
     * <p>
     * Check if the project is updated in the database correctly.
     * </p>
     *
     * @throws Exception
     *             throw any exception to JUnit
     */
    @Test
    public void test_updateProject() throws Exception {
        // get the sample project object
        Project project = TestHelper.getSampleProject1();

        persistence.createProject(project, "user");

        project.setPrizes(Arrays.asList(new Prize[]{TestHelper.createPrize(2, 600.00, 10)}));

        project.setProjectFileTypes(Arrays.asList(new FileType[]{TestHelper.createFileType("description 2",
            "extension 2", true, true, 1)}));

        persistence.updateProject(project, "No reason", "tc");

        // then check if the project is updated in the database.
        assertProject(project);
    }

    /**
     * <p>
     * Accuracy test for the method <code>getProject(long)</code>.
     * </p>
     * <p>
     * Check if the project is gotten from the database correctly.
     * </p>
     *
     * @throws Exception
     *             throw any exception to JUnit
     */
    @Test
    public void test_getProject() throws Exception {
        // get the sample project object
        Project project = TestHelper.getSampleProject1();

        persistence.createProject(project, "user");

        Project projectRetrieved = persistence.getProject(project.getId());

        assertEquals("Should be the same", project.getId(), projectRetrieved.getId());
        assertEquals("Should be the same", project.getProjectStudioSpecification().getId(), projectRetrieved
            .getProjectStudioSpecification().getId());
        assertEquals("Should be the same", project.getPrizes().size(), projectRetrieved.getPrizes().size());
        assertEquals("Should be the same", project.getProjectFileTypes().size(), projectRetrieved.getProjectFileTypes()
            .size());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getProjects(long[])</code>.
     * </p>
     * <p>
     * Check if the projects are gotten from the database correctly.
     * </p>
     *
     * @throws Exception
     *             throw any exception to JUnit
     */
    @Test
    public void test_getProjects() throws Exception {
        // get the sample project object
        Project project1 = TestHelper.getSampleProject1();

        persistence.createProject(project1, "user");

        Project project2 = TestHelper.getSampleProject1();

        persistence.createProject(project2, "tc");

        Project[] projectsRetrieved = persistence.getProjects(new long[]{project1.getId(), project2.getId()});

        for (Project project : projectsRetrieved) {
            if (project.getId() == project1.getId()) {
                assertEquals("Should be the same", project1.getProjectStudioSpecification().getId(), project
                    .getProjectStudioSpecification().getId());
                assertEquals("Should be the same", project1.getPrizes().size(), project.getPrizes().size());
                assertEquals("Should be the same", project1.getProjectFileTypes().size(), project.getProjectFileTypes()
                    .size());
            } else {
                assertEquals("Should be the same", project2.getId(), project.getId());
                assertEquals("Should be the same", project2.getProjectStudioSpecification().getId(), project
                    .getProjectStudioSpecification().getId());
                assertEquals("Should be the same", project2.getPrizes().size(), project.getPrizes().size());
                assertEquals("Should be the same", project2.getProjectFileTypes().size(), project.getProjectFileTypes()
                    .size());
            }
        }
    }

    /**
     * <p>
     * Accuracy test for {@link AbstractInformixProjectPersistence#getProjectsByDirectProjectId(long)} method.
     * </p>
     * <p>
     * The projects should be gotten from the database correctly.
     * </p>
     *
     * @throws Exception
     *             throw any exception to JUnit
     */
    @Test
    public void test_getProjectsByDirectProjectId() throws Exception {
        Project project1 = TestHelper.getSampleProject1();

        persistence.createProject(project1, "user");

        Project project2 = TestHelper.getSampleProject1();

        persistence.createProject(project2, "tc");

        Project[] projectsRetrieved = persistence.getProjectsByDirectProjectId(1);

        assertEquals("Should be the same", 2, projectsRetrieved.length);

        for (Project project : projectsRetrieved) {
            if (project.getId() == project1.getId()) {
                assertEquals("Should be the same", project1.getProjectStudioSpecification().getId(), project
                    .getProjectStudioSpecification().getId());
                assertEquals("Should be the same", project1.getPrizes().size(), project.getPrizes().size());
                assertEquals("Should be the same", project1.getProjectFileTypes().size(), project.getProjectFileTypes()
                    .size());
            } else {
                assertEquals("Should be the same", project2.getId(), project.getId());
                assertEquals("Should be the same", project2.getProjectStudioSpecification().getId(), project
                    .getProjectStudioSpecification().getId());
                assertEquals("Should be the same", project2.getPrizes().size(), project.getPrizes().size());
                assertEquals("Should be the same", project2.getProjectFileTypes().size(), project.getProjectFileTypes()
                    .size());
            }
        }

        projectsRetrieved = persistence.getProjectsByDirectProjectId(100);

        assertEquals("Should be the same", 0, projectsRetrieved.length);
    }

    /**
     * Failure test for {@link AbstractInformixProjectPersistence#getProjectsByDirectProjectId(long)} method. If the
     * argument is non-positive, IllegalArgumentException is expected.
     *
     * @throws PersistenceException
     *             to JUnit
     */
    @Test
    public void test_getProjectsByDirectProjectId_failure1() throws PersistenceException {
        try {
            persistence.getProjectsByDirectProjectId(-1);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // good
        }

        try {
            persistence.getProjectsByDirectProjectId(0);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Accuracy test for {@link AbstractInformixProjectPersistence#getProjectFileTypes(long)} method. The file type can
     * be gotten correctly for the specified project.
     *
     * @throws PersistenceException
     *             to JUnit
     */
    @Test
    public void test_getProjectFileTypes() throws PersistenceException {
        Project project = TestHelper.getSampleProject1();

        persistence.createProject(project, "user");

        FileType[] fileTypes = persistence.getProjectFileTypes(project.getId());

        assertEquals("Should be the same", 1, fileTypes.length);
        assertEquals("Should be the same", "extension 1", fileTypes[0].getExtension());
        assertEquals("Should be the same", "description 1", fileTypes[0].getDescription());
        assertEquals("Should be the same", 1, fileTypes[0].getSort());
    }

    /**
     * Failure test for {@link AbstractInformixProjectPersistence#getProjectFileTypes(long)} method. If the argument is
     * non-positive, IllegalArgumentException is expected.
     *
     * @throws PersistenceException
     *             to JUnit
     */
    @Test
    public void test_getProjectFileTypes_failure1() throws PersistenceException {
        try {
            persistence.getProjectFileTypes(-1);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // good
        }

        try {
            persistence.getProjectFileTypes(0);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Failure test for {@link AbstractInformixProjectPersistence#getProjectFileTypes(long)} method. If the project does
     * not exist, PersistenceException is expected.
     */
    @Test
    public void test_getProjectFileTypes_failure2() {
        try {
            persistence.getProjectFileTypes(1);
            fail("PersistenceException is expected");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * Accuracy test for {@link AbstractInformixProjectPersistence#updateProjectFileTypes(long, List)} method. The file
     * type can be updated correctly for the specified project.
     *
     * @throws PersistenceException
     *             to JUnit
     */
    @Test
    public void test_updateProjectFileTypes() throws PersistenceException {
        Project project = TestHelper.getSampleProject1();

        persistence.createProject(project, "user");

        List<FileType> fileTypes = new ArrayList<FileType>();
        fileTypes.add(TestHelper.createFileType("d1", "e1", true, false, 2));
        FileType fileType = TestHelper.createFileType("d2", "e2", true, true, 3);
        persistence.createFileType(fileType, "tc");
        fileTypes.add(fileType);

        persistence.updateProjectFileTypes(project.getId(), fileTypes, "tc");
        FileType[] fileTypesRetrieved = persistence.getProjectFileTypes(project.getId());

        assertEquals("Should be the same", 2, fileTypesRetrieved.length);
        for (int i = 0; i < fileTypesRetrieved.length; i++) {
            if ("e1".equals(fileTypesRetrieved[i].getExtension())) {
                assertEquals("Should be the same", "d1", fileTypesRetrieved[i].getDescription());
                assertEquals("Should be the same", 2, fileTypesRetrieved[i].getSort());
            } else {
                assertEquals("Should be the same", "e2", fileTypesRetrieved[i].getExtension());
                assertEquals("Should be the same", "d2", fileTypesRetrieved[i].getDescription());
                assertEquals("Should be the same", 3, fileTypesRetrieved[i].getSort());
            }
        }
    }

    /**
     * Failure test for {@link AbstractInformixProjectPersistence#updateProjectFileTypes(long, List, String)} method. If
     * the projectId is non-positive, IllegalArgumentException is expected.
     *
     * @throws PersistenceException
     *             to JUnit
     */
    @Test
    public void test_updateProjectFileTypes_failure1() throws PersistenceException {
        try {
            persistence.updateProjectFileTypes(-1, new ArrayList<FileType>(), "tc");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // good
        }

        try {
            persistence.updateProjectFileTypes(0, new ArrayList<FileType>(), "tc");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Failure test for {@link AbstractInformixProjectPersistence#updateProjectFileTypes(long, List, String)} method. If
     * the fileTypes contains null, IllegalArgumentException is expected.
     *
     * @throws PersistenceException
     *             to JUnit
     */
    @Test
    public void test_updateProjectFileTypes_failure2() throws PersistenceException {
        List<FileType> fileTypes = new ArrayList<FileType>();
        fileTypes.add(null);
        try {
            persistence.updateProjectFileTypes(1, fileTypes, "tc");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Failure test for {@link AbstractInformixProjectPersistence#updateProjectFileTypes(long, List, String)} method. If
     * the operator is null, IllegalArgumentException is expected.
     *
     * @throws PersistenceException
     *             to JUnit
     */
    @Test
    public void test_updateProjectFileTypes_failure3() throws PersistenceException {
        try {
            persistence.updateProjectFileTypes(1, new ArrayList<FileType>(), null);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Failure test for {@link AbstractInformixProjectPersistence#updateProjectFileTypes(long, List, String)} method. If
     * the operator is empty, IllegalArgumentException is expected.
     *
     * @throws PersistenceException
     *             to JUnit
     */
    @Test
    public void test_updateProjectFileTypes_failure4() throws PersistenceException {
        try {
            persistence.updateProjectFileTypes(1, new ArrayList<FileType>(), " \t\t ");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Failure test for {@link AbstractInformixProjectPersistence#updateProjectFileTypes(long, List, String)} method. If
     * the project does not exist, PersistenceException is expected.
     */
    @Test
    public void test_updateProjectFileTypes_failure5() {
        List<FileType> fileTypes = new ArrayList<FileType>();
        fileTypes.add(TestHelper.createFileType("d1", "e1", true, false, 2));
        try {
            persistence.updateProjectFileTypes(1, fileTypes, "tc");
            fail("PersistenceException is expected");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * Accuracy test for {@link AbstractInformixProjectPersistence#getProjectPrizes(long)} method. The prizes can be
     * gotten correctly for the specified project.
     *
     * @throws PersistenceException
     *             to JUnit
     */
    @Test
    public void test_getProjectPrizes() throws PersistenceException {
        Project project = TestHelper.getSampleProject1();

        persistence.createProject(project, "user");

        Prize[] prizes = persistence.getProjectPrizes(project.getId());

        assertEquals("Should be the same", 1, prizes.length);
        assertEquals("Should be the same", 1, prizes[0].getPlace());
        assertEquals("Should be the same", 600.00, prizes[0].getPrizeAmount(), 0.0001);
        assertEquals("Should be the same", 5, prizes[0].getNumberOfSubmissions());
    }

    /**
     * Failure test for {@link AbstractInformixProjectPersistence#getProjectPrizes(long)} method. If the argument is
     * non-positive, IllegalArgumentException is expected.
     *
     * @throws PersistenceException
     *             to JUnit
     */
    @Test
    public void test_getProjectPrizes_failure1() throws PersistenceException {
        try {
            persistence.getProjectPrizes(-1);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // good
        }

        try {
            persistence.getProjectPrizes(0);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Failure test for {@link AbstractInformixProjectPersistence#getProjectPrizes(long)} method. If the project does
     * not exist, PersistenceException is expected.
     */
    @Test
    public void test_getProjectPrizes_failure2() {
        try {
            persistence.getProjectPrizes(1);
            fail("PersistenceException is expected");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * Accuracy test for {@link AbstractInformixProjectPersistence#updateProjectPrizes(long, List)} method. The prizes
     * can be updated correctly for the specified project.
     *
     * @throws PersistenceException
     *             to JUnit
     */
    @Test
    public void test_updateProjectPrizes() throws PersistenceException {
        Project project = TestHelper.getSampleProject1();
        persistence.createProject(project, "user");

        List<Prize> prizes = new ArrayList<Prize>();
        prizes.add(TestHelper.createPrize(1, 100, 2));
        Prize prize = TestHelper.createPrize(2, 200, 4);
        persistence.createPrize(prize, "tc");
        prizes.add(prize);

        persistence.updateProjectPrizes(project.getId(), prizes, "tc");
        Prize[] prizesRetrieved = persistence.getProjectPrizes(project.getId());

        assertEquals("Should be the same", 2, prizesRetrieved.length);
        for (int i = 0; i < prizesRetrieved.length; i++) {
            if (2 == prizesRetrieved[i].getPlace()) {
                assertEquals("Should be the same", 4, prizesRetrieved[i].getNumberOfSubmissions());
                assertEquals("Should be the same", 200, prizesRetrieved[i].getPrizeAmount(), 0.001);
            } else {
                assertEquals("Should be the same", 1, prizesRetrieved[i].getPlace());
                assertEquals("Should be the same", 2, prizesRetrieved[i].getNumberOfSubmissions());
                assertEquals("Should be the same", 100, prizesRetrieved[i].getPrizeAmount(), 0.001);
            }
        }
    }

    /**
     * Failure test for {@link AbstractInformixProjectPersistence#updateProjectPrizes(long, List, String)} method. If
     * the projectId is non-positive, IllegalArgumentException is expected.
     *
     * @throws PersistenceException
     *             to JUnit
     */
    @Test
    public void test_updateProjectPrizes_failure1() throws PersistenceException {
        try {
            persistence.updateProjectPrizes(-1, new ArrayList<Prize>(), "tc");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // good
        }

        try {
            persistence.updateProjectPrizes(0, new ArrayList<Prize>(), "tc");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Failure test for {@link AbstractInformixProjectPersistence#updateProjectPrizes(long, List, String)} method. If
     * the prizes contains null, IllegalArgumentException is expected.
     *
     * @throws PersistenceException
     *             to JUnit
     */
    @Test
    public void test_updateProjectPrizes_failure2() throws PersistenceException {
        List<Prize> prizes = new ArrayList<Prize>();
        prizes.add(null);
        try {
            persistence.updateProjectPrizes(1, prizes, "tc");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Failure test for {@link AbstractInformixProjectPersistence#updateProjectPrizes(long, List, String)} method. If
     * the operator is null, IllegalArgumentException is expected.
     *
     * @throws PersistenceException
     *             to JUnit
     */
    @Test
    public void test_updateProjectPrizes_failure3() throws PersistenceException {
        try {
            persistence.updateProjectPrizes(1, new ArrayList<Prize>(), null);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Failure test for {@link AbstractInformixProjectPersistence#updateProjectPrizes(long, List, String)} method. If
     * the operator is empty, IllegalArgumentException is expected.
     *
     * @throws PersistenceException
     *             to JUnit
     */
    @Test
    public void test_updateProjectPrizes_failure4() throws PersistenceException {
        try {
            persistence.updateProjectPrizes(1, new ArrayList<Prize>(), " \t\t ");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Failure test for {@link AbstractInformixProjectPersistence#updateProjectPrizes(long, List, String)} method. If
     * the project does not exist, PersistenceException is expected.
     */
    @Test
    public void test_updateProjectPrizes_failure5() {
        List<Prize> prizes = new ArrayList<Prize>();
        prizes.add(TestHelper.createPrize(1, 100, 2));
        try {
            persistence.updateProjectPrizes(1, prizes, "tc");
            fail("PersistenceException is expected");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * Accuracy test for {@link AbstractInformixProjectPersistence#createFileType(FileType, String)} method. The file
     * type can be created correctly.
     *
     * @throws PersistenceException
     *             to JUnit
     */
    @Test
    public void test_createFileType() throws PersistenceException {
        FileType fileType = TestHelper.createFileType("description 1", "extension 1", true, false, 1);
        fileType = persistence.createFileType(fileType, "tc");

        assertFileType(fileType);
    }

    /**
     * Failure test for {@link AbstractInformixProjectPersistence#createFileType(FileType, String)} method. If the
     * fileType is null, IllegalArgumentException is expected.
     *
     * @throws PersistenceException
     *             to JUnit
     */
    @Test
    public void test_createFileType_failure1() throws PersistenceException {
        try {
            persistence.createFileType(null, "tc");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Failure test for {@link AbstractInformixProjectPersistence#createFileType(FileType, String)} method. If the
     * operator is null, IllegalArgumentException is expected.
     *
     * @throws PersistenceException
     *             to JUnit
     */
    @Test
    public void test_createFileType_failure2() throws PersistenceException {
        try {
            persistence.createFileType(new FileType(), null);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Failure test for {@link AbstractInformixProjectPersistence#createFileType(FileType, String)} method. If the
     * operator is empty, IllegalArgumentException is expected.
     *
     * @throws PersistenceException
     *             to JUnit
     */
    @Test
    public void test_createFileType_failure3() throws PersistenceException {
        try {
            persistence.createFileType(new FileType(), "  ");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Failure test for {@link AbstractInformixProjectPersistence#createFileType(FileType, String)} method. If the file
     * type is already in the database, PersistenceException is expected.
     */
    @Test
    public void test_createFileType_failure4() throws PersistenceException {
        FileType fileType = TestHelper.createFileType("description 1", "extension 1", true, false, 1);
        fileType = persistence.createFileType(fileType, "tc");
        try {
            persistence.createFileType(fileType, "tc");
            fail("PersistenceException is expected");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * Accuracy test for {@link AbstractInformixProjectPersistence#updateFileType(FileType, String)} method. The file
     * type can be updated correctly.
     *
     * @throws PersistenceException
     *             to JUnit
     */
    @Test
    public void test_updateFileType() throws PersistenceException {
        FileType fileType = TestHelper.createFileType("description 2", "extension 2", true, false, 1);
        fileType = persistence.createFileType(fileType, "tc1");

        fileType.setBundledFile(true);
        fileType.setSort(2);
        fileType.setExtension("extension3");
        persistence.updateFileType(fileType, "tc2");

        assertFileType(fileType);
    }

    /**
     * Failure test for {@link AbstractInformixProjectPersistence#updateFileType(FileType, String)} method. If the
     * fileType is null, IllegalArgumentException is expected.
     *
     * @throws PersistenceException
     *             to JUnit
     */
    @Test
    public void test_updateFileType_failure1() throws PersistenceException {
        try {
            persistence.updateFileType(null, "tc");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Failure test for {@link AbstractInformixProjectPersistence#updateFileType(FileType, String)} method. If the
     * operator is null, IllegalArgumentException is expected.
     *
     * @throws PersistenceException
     *             to JUnit
     */
    @Test
    public void test_updateFileType_failure2() throws PersistenceException {
        try {
            persistence.updateFileType(new FileType(), null);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Failure test for {@link AbstractInformixProjectPersistence#updateFileType(FileType, String)} method. If the
     * operator is empty, IllegalArgumentException is expected.
     *
     * @throws PersistenceException
     *             to JUnit
     */
    @Test
    public void test_updateFileType_failure3() throws PersistenceException {
        try {
            persistence.updateFileType(new FileType(), "  ");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Failure test for {@link AbstractInformixProjectPersistence#updateFileType(FileType, String)} method. If the file
     * type is not in the database, PersistenceException is expected.
     */
    @Test
    public void test_updateFileType_failure4() throws PersistenceException {
        FileType fileType = TestHelper.createFileType("description 1", "extension 1", true, false, 1);
        fileType.setId(1);
        try {
            persistence.updateFileType(fileType, "tc");
            fail("PersistenceException is expected");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * Accuracy test for {@link AbstractInformixProjectPersistence#removeFileType(FileType, String)} method. The file
     * type can be removed correctly.
     *
     * @throws PersistenceException
     *             to JUnit
     */
    @Test
    public void test_removeFileType() throws PersistenceException {
        Project project = TestHelper.getSampleProject1();
        persistence.createProject(project, "user");

        FileType fileType = project.getProjectFileTypes().get(0);

        persistence.removeFileType(fileType, "tc2");

        Connection conn = persistence.openConnection();
        try {
            assertFalse("Should not contain the entitry", Helper.checkEntityExists("file_type_lu", "file_type_id",
                fileType.getId(), conn));

            assertFalse("Should not contain the entitry", Helper.checkEntityExists("project_file_type_xref",
                "file_type_id", fileType.getId(), conn));
        } catch (PersistenceException e) {
            persistence.closeConnectionOnError(conn);
        }
        persistence.closeConnection(conn);
    }

    /**
     * Failure test for {@link AbstractInformixProjectPersistence#removeFileType(FileType, String)} method. If the
     * fileType is null, IllegalArgumentException is expected.
     *
     * @throws PersistenceException
     *             to JUnit
     */
    @Test
    public void test_removeFileType_failure1() throws PersistenceException {
        try {
            persistence.removeFileType(null, "tc");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Failure test for {@link AbstractInformixProjectPersistence#removeFileType(FileType, String)} method. If the
     * operator is null, IllegalArgumentException is expected.
     *
     * @throws PersistenceException
     *             to JUnit
     */
    @Test
    public void test_removeFileType_failure2() throws PersistenceException {
        try {
            persistence.removeFileType(new FileType(), null);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Failure test for {@link AbstractInformixProjectPersistence#removeFileType(FileType, String)} method. If the
     * operator is empty, IllegalArgumentException is expected.
     *
     * @throws PersistenceException
     *             to JUnit
     */
    @Test
    public void test_removeFileType_failure3() throws PersistenceException {
        try {
            persistence.removeFileType(new FileType(), "  ");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Failure test for {@link AbstractInformixProjectPersistence#removeFileType(FileType, String)} method. If the file
     * type is not in the database, PersistenceException is expected.
     */
    @Test
    public void test_removeFileType_failure4() throws PersistenceException {
        FileType fileType = TestHelper.createFileType("description 1", "extension 1", true, false, 1);
        fileType.setId(1);
        try {
            persistence.removeFileType(fileType, "tc");
            fail("PersistenceException is expected");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * Accuracy test for {@link AbstractInformixProjectPersistence#getAllFileTypes()} method. All the file type can be
     * gotten correctly.
     *
     * @throws PersistenceException
     *             to JUnit
     */
    @Test
    public void test_getAllFileTypes() throws PersistenceException {
        FileType fileType1 = TestHelper.createFileType("description 1", "extension 1", true, false, 1);
        fileType1 = persistence.createFileType(fileType1, "tc1");
        FileType fileType2 = TestHelper.createFileType("description 2", "extension 2", true, false, 1);
        fileType2 = persistence.createFileType(fileType2, "tc1");
        FileType fileType3 = TestHelper.createFileType("description 3", "extension 3", true, true, 1);
        fileType3 = persistence.createFileType(fileType3, "tc1");

        FileType fileTypes[] = persistence.getAllFileTypes();
        assertEquals("Should be the same", 3, fileTypes.length);
        assertFileType(fileTypes[0]);
        assertFileType(fileTypes[1]);
        assertFileType(fileTypes[2]);
    }

    /**
     * Accuracy test for {@link AbstractInformixProjectPersistence#getPrizeTypes()} method. All the file type can be
     * gotten correctly.
     *
     * @throws PersistenceException
     *             to JUnit
     */
    @Test
    public void test_getPrizeTypes() throws PersistenceException {
        PrizeType[] prizeTypes = persistence.getPrizeTypes();

        assertEquals("Should be the same", 3, prizeTypes.length);

        for (PrizeType type : prizeTypes) {
            if (type.getId() == 1) {
                assertEquals("Should be the same", "Component Design Prize", type.getDescription());
            } else if (type.getId() == 2) {
                assertEquals("Should be the same", "Component Development Prize", type.getDescription());
            } else {
                assertEquals("Should be the same", "Assembly Prize", type.getDescription());
            }
        }
    }

    /**
     * Accuracy test for {@link AbstractInformixProjectPersistence#createPrize(Prize, String)} method. The prize can be
     * created correctly.
     *
     * @throws PersistenceException
     *             to JUnit
     */
    @Test
    public void test_createPrize() throws PersistenceException {
        Prize prize = TestHelper.createPrize(1, 600.00, 5);
        prize = persistence.createPrize(prize, "tc");

        assertPrize(prize);
    }

    /**
     * Failure test for {@link AbstractInformixProjectPersistence#createPrize(Prize, String)} method. If the fileType is
     * null, IllegalArgumentException is expected.
     *
     * @throws PersistenceException
     *             to JUnit
     */
    @Test
    public void test_createPrize_failure1() throws PersistenceException {
        try {
            persistence.createPrize(null, "tc");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Failure test for {@link AbstractInformixProjectPersistence#createPrize(Prize, String)} method. If the operator is
     * null, IllegalArgumentException is expected.
     *
     * @throws PersistenceException
     *             to JUnit
     */
    @Test
    public void test_createPrize_failure2() throws PersistenceException {
        try {
            persistence.createPrize(new Prize(), null);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Failure test for {@link AbstractInformixProjectPersistence#createPrize(Prize, String)} method. If the operator is
     * empty, IllegalArgumentException is expected.
     *
     * @throws PersistenceException
     *             to JUnit
     */
    @Test
    public void test_createPrize_failure3() throws PersistenceException {
        try {
            persistence.createPrize(new Prize(), "  ");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Failure test for {@link AbstractInformixProjectPersistence#createPrize(Prize, String)} method. If the file type
     * is already in the database, PersistenceException is expected.
     */
    @Test
    public void test_createPrize_failure4() throws PersistenceException {
        Prize prize = TestHelper.createPrize(1, 100, 5);
        prize = persistence.createPrize(prize, "tc");
        try {
            persistence.createPrize(prize, "tc");
            fail("PersistenceException is expected");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * Accuracy test for {@link AbstractInformixProjectPersistence#updatePrize(Prize, String)} method. The prize can be
     * updated correctly.
     *
     * @throws PersistenceException
     *             to JUnit
     */
    @Test
    public void test_updatePrize() throws PersistenceException {
        Prize prize = TestHelper.createPrize(1, 600.00, 5);
        prize = persistence.createPrize(prize, "tc");

        prize.setPlace(2);
        prize.setNumberOfSubmissions(10);
        persistence.updatePrize(prize, "tc1");

        assertPrize(prize);
    }

    /**
     * Failure test for {@link AbstractInformixProjectPersistence#updatePrize(Prize, String)} method. If the fileType is
     * null, IllegalArgumentException is expected.
     *
     * @throws PersistenceException
     *             to JUnit
     */
    @Test
    public void test_updatePrize_failure1() throws PersistenceException {
        try {
            persistence.updatePrize(null, "tc");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Failure test for {@link AbstractInformixProjectPersistence#updatePrize(Prize, String)} method. If the operator is
     * null, IllegalArgumentException is expected.
     *
     * @throws PersistenceException
     *             to JUnit
     */
    @Test
    public void test_updatePrize_failure2() throws PersistenceException {
        try {
            persistence.updatePrize(new Prize(), null);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Failure test for {@link AbstractInformixProjectPersistence#updatePrize(Prize, String)} method. If the operator is
     * empty, IllegalArgumentException is expected.
     *
     * @throws PersistenceException
     *             to JUnit
     */
    @Test
    public void test_updatePrize_failure3() throws PersistenceException {
        try {
            persistence.updatePrize(new Prize(), "  ");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Failure test for {@link AbstractInformixProjectPersistence#updatePrize(Prize, String)} method. If the file type
     * is not in the database, PersistenceException is expected.
     */
    @Test
    public void test_updatePrize_failure4() throws PersistenceException {
        Prize prize = TestHelper.createPrize(1, 100, 5);
        prize.setId(1);
        try {
            persistence.updatePrize(prize, "tc");
            fail("PersistenceException is expected");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * Accuracy test for {@link AbstractInformixProjectPersistence#removePrize(Prize, String)} method. The prize can be
     * removed correctly.
     *
     * @throws PersistenceException
     *             to JUnit
     */
    @Test
    public void test_removePrize() throws PersistenceException {
        Project project = TestHelper.getSampleProject1();
        persistence.createProject(project, "user");

        Prize prize = project.getPrizes().get(0);

        persistence.removePrize(prize, "tc2");

        Connection conn = persistence.openConnection();
        try {
            assertFalse("Should not contain the entitry", Helper.checkEntityExists("prize", "prize_id", prize.getId(),
                conn));

            assertFalse("Should not contain the entitry", Helper.checkEntityExists("project_prize_xref", "prize_id",
                prize.getId(), conn));
        } catch (PersistenceException e) {
            persistence.closeConnectionOnError(conn);
        }
        persistence.closeConnection(conn);
    }

    /**
     * Failure test for {@link AbstractInformixProjectPersistence#removePrize(Prize, String)} method. If the fileType is
     * null, IllegalArgumentException is expected.
     *
     * @throws PersistenceException
     *             to JUnit
     */
    @Test
    public void test_removePrize_failure1() throws PersistenceException {
        try {
            persistence.removePrize(null, "tc");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Failure test for {@link AbstractInformixProjectPersistence#removePrize(Prize, String)} method. If the operator is
     * null, IllegalArgumentException is expected.
     *
     * @throws PersistenceException
     *             to JUnit
     */
    @Test
    public void test_removePrize_failure2() throws PersistenceException {
        try {
            persistence.removePrize(new Prize(), null);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Failure test for {@link AbstractInformixProjectPersistence#removePrize(Prize, String)} method. If the operator is
     * empty, IllegalArgumentException is expected.
     *
     * @throws PersistenceException
     *             to JUnit
     */
    @Test
    public void test_removePrize_failure3() throws PersistenceException {
        try {
            persistence.removePrize(new Prize(), "  ");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Failure test for {@link AbstractInformixProjectPersistence#removePrize(Prize, String)} method. If the file type
     * is not in the database, PersistenceException is expected.
     */
    @Test
    public void test_removePrizePrize_failure4() throws PersistenceException {
        Prize prize = TestHelper.createPrize(1, 100, 5);
        prize.setId(1);
        try {
            persistence.removePrize(prize, "tc");
            fail("PersistenceException is expected");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * Accuracy test for
     * {@link AbstractInformixProjectPersistence#createProjectStudioSpecification(ProjectStudioSpecification, String)}
     * method. The prize can be created correctly.
     *
     * @throws PersistenceException
     *             to JUnit
     */
    @Test
    public void test_createProjectStudioSpecification() throws PersistenceException {
        ProjectStudioSpecification spec = TestHelper.createProjectStudioSpecification("goal1", "targetAudience",
            "roundOneIntroduction1");
        spec = persistence.createProjectStudioSpecification(spec, "tc");

        assertProjectStudioSpecification(spec);
    }

    /**
     * Failure test for
     * {@link AbstractInformixProjectPersistence#createProjectStudioSpecification(ProjectStudioSpecification, String)}
     * method. If the fileType is null, IllegalArgumentException is expected.
     *
     * @throws PersistenceException
     *             to JUnit
     */
    @Test
    public void test_createProjectStudioSpecification_failure1() throws PersistenceException {
        try {
            persistence.createProjectStudioSpecification(null, "tc");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Failure test for
     * {@link AbstractInformixProjectPersistence#createProjectStudioSpecification(ProjectStudioSpecification, String)}
     * method. If the operator is null, IllegalArgumentException is expected.
     *
     * @throws PersistenceException
     *             to JUnit
     */
    @Test
    public void test_createProjectStudioSpecification_failure2() throws PersistenceException {
        try {
            persistence.createProjectStudioSpecification(new ProjectStudioSpecification(), null);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Failure test for
     * {@link AbstractInformixProjectPersistence#createProjectStudioSpecification(ProjectStudioSpecification, String)}
     * method. If the operator is empty, IllegalArgumentException is expected.
     *
     * @throws PersistenceException
     *             to JUnit
     */
    @Test
    public void test_createProjectStudioSpecification_failure3() throws PersistenceException {
        try {
            persistence.createProjectStudioSpecification(new ProjectStudioSpecification(), "  ");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Failure test for
     * {@link AbstractInformixProjectPersistence#createProjectStudioSpecification(ProjectStudioSpecification, String)}
     * method. If the file type is already in the database, PersistenceException is expected.
     */
    @Test
    public void test_createProjectStudioSpecification_failure4() throws PersistenceException {
        ProjectStudioSpecification spec = TestHelper.createProjectStudioSpecification("goal1", "targetAudience",
            "roundOneIntroduction1");
        spec = persistence.createProjectStudioSpecification(spec, "tc");
        try {
            persistence.createProjectStudioSpecification(spec, "tc");
            fail("PersistenceException is expected");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * Accuracy test for
     * {@link AbstractInformixProjectPersistence#updateProjectStudioSpecification(ProjectStudioSpecification, String)}
     * method. The prize can be updated correctly.
     *
     * @throws PersistenceException
     *             to JUnit
     */
    @Test
    public void test_updateProjectStudioSpecification() throws PersistenceException {
        ProjectStudioSpecification spec = TestHelper.createProjectStudioSpecification("goal1", "targetAudience",
            "roundOneIntroduction1");
        spec = persistence.createProjectStudioSpecification(spec, "tc");

        spec.setSubmittersLockedBetweenRounds(false);
        spec.setGoals("goal");

        persistence.updateProjectStudioSpecification(spec, "tc1");

        assertProjectStudioSpecification(spec);
    }

    /**
     * Failure test for
     * {@link AbstractInformixProjectPersistence#updateProjectStudioSpecification(ProjectStudioSpecification, String)}
     * method. If the fileType is null, IllegalArgumentException is expected.
     *
     * @throws PersistenceException
     *             to JUnit
     */
    @Test
    public void test_updateProjectStudioSpecification_failure1() throws PersistenceException {
        try {
            persistence.updateProjectStudioSpecification(null, "tc");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Failure test for
     * {@link AbstractInformixProjectPersistence#updateProjectStudioSpecification(ProjectStudioSpecification, String)}
     * method. If the operator is null, IllegalArgumentException is expected.
     *
     * @throws PersistenceException
     *             to JUnit
     */
    @Test
    public void test_updateProjectStudioSpecification_failure2() throws PersistenceException {
        try {
            persistence.updateProjectStudioSpecification(new ProjectStudioSpecification(), null);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Failure test for
     * {@link AbstractInformixProjectPersistence#updateProjectStudioSpecification(ProjectStudioSpecification, String)}
     * method. If the operator is empty, IllegalArgumentException is expected.
     *
     * @throws PersistenceException
     *             to JUnit
     */
    @Test
    public void test_updateProjectStudioSpecification_failure3() throws PersistenceException {
        try {
            persistence.updateProjectStudioSpecification(new ProjectStudioSpecification(), "  ");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Failure test for
     * {@link AbstractInformixProjectPersistence#updateProjectStudioSpecification(ProjectStudioSpecification, String)}
     * method. If the file type is already in the database, PersistenceException is expected.
     */
    @Test
    public void test_updateProjectStudioSpecification_failure4() throws PersistenceException {
        ProjectStudioSpecification spec = TestHelper.createProjectStudioSpecification("goal1", "targetAudience",
            "roundOneIntroduction1");
        spec.setId(1);
        try {
            persistence.updateProjectStudioSpecification(spec, "tc");
            fail("PersistenceException is expected");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * Accuracy test for
     * {@link AbstractInformixProjectPersistence#removeProjectStudioSpecification(ProjectStudioSpecification, String)}
     * method. The prize can be removed correctly.
     *
     * @throws PersistenceException
     *             to JUnit
     */
    @Test
    public void test_removeProjectStudioSpecification() throws PersistenceException {
        Project project = TestHelper.getSampleProject1();
        persistence.createProject(project, "user");

        ProjectStudioSpecification spec = project.getProjectStudioSpecification();

        persistence.removeProjectStudioSpecification(spec, "tc2");

        Connection conn = persistence.openConnection();
        try {
            assertFalse("Should not contain the entitry", Helper.checkEntityExists("project_studio_specification",
                "project_studio_spec_id", spec.getId(), conn));
        } catch (PersistenceException e) {
            persistence.closeConnectionOnError(conn);
        }
        persistence.closeConnection(conn);
    }

    /**
     * Failure test for
     * {@link AbstractInformixProjectPersistence#removeProjectStudioSpecification(ProjectStudioSpecification, String)}
     * method. If the fileType is null, IllegalArgumentException is expected.
     *
     * @throws PersistenceException
     *             to JUnit
     */
    @Test
    public void test_removeProjectStudioSpecification_failure1() throws PersistenceException {
        try {
            persistence.removeProjectStudioSpecification(null, "tc");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Failure test for
     * {@link AbstractInformixProjectPersistence#removeProjectStudioSpecification(ProjectStudioSpecification, String)}
     * method. If the operator is null, IllegalArgumentException is expected.
     *
     * @throws PersistenceException
     *             to JUnit
     */
    @Test
    public void test_removeProjectStudioSpecification_failure2() throws PersistenceException {
        try {
            persistence.removeProjectStudioSpecification(new ProjectStudioSpecification(), null);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Failure test for
     * {@link AbstractInformixProjectPersistence#removeProjectStudioSpecification(ProjectStudioSpecification, String)}
     * method. If the operator is empty, IllegalArgumentException is expected.
     *
     * @throws PersistenceException
     *             to JUnit
     */
    @Test
    public void test_removeProjectStudioSpecification_failure3() throws PersistenceException {
        try {
            persistence.removeProjectStudioSpecification(new ProjectStudioSpecification(), "  ");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Failure test for
     * {@link AbstractInformixProjectPersistence#removeProjectStudioSpecification(ProjectStudioSpecification, String)}
     * method. If the file type is already in the database, PersistenceException is expected.
     */
    @Test
    public void test_removeProjectStudioSpecification_failure4() throws PersistenceException {
        ProjectStudioSpecification spec = TestHelper.createProjectStudioSpecification("goal1", "targetAudience",
            "roundOneIntroduction1");
        spec.setId(1);
        try {
            persistence.removeProjectStudioSpecification(spec, "tc");
            fail("PersistenceException is expected");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * Accuracy test for {@link AbstractInformixProjectPersistence#getProjectStudioSpecification(long)} method. The
     * studio specification can be gotten correctly for the specified project.
     *
     * @throws PersistenceException
     *             to JUnit
     */
    @Test
    public void test_getProjectStudioSpecification() throws PersistenceException {
        Project project = TestHelper.getSampleProject1();

        persistence.createProject(project, "user");

        ProjectStudioSpecification spec = persistence.getProjectStudioSpecification(project.getId());

        assertNotNull("Should not be null", spec);
        assertEquals("Should be the same", "goal1", spec.getGoals());
        assertEquals("Should be the same", "targetAudience", spec.getTargetAudience());
        assertEquals("Should be the same", "roundOneIntroduction1", spec.getRoundOneIntroduction());
    }

    /**
     * Failure test for {@link AbstractInformixProjectPersistence#getProjectStudioSpecification(long)} method. If the
     * argument is non-positive, IllegalArgumentException is expected.
     *
     * @throws PersistenceException
     *             to JUnit
     */
    @Test
    public void test_getProjectStudioSpecification_failure1() throws PersistenceException {
        try {
            persistence.getProjectStudioSpecification(-1);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // good
        }

        try {
            persistence.getProjectStudioSpecification(0);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Failure test for {@link AbstractInformixProjectPersistence#getProjectStudioSpecification(long)} method. If the
     * project does not exist, PersistenceException is expected.
     */
    @Test
    public void test_getProjectStudioSpecification_failure2() {
        try {
            persistence.getProjectStudioSpecification(1);
            fail("PersistenceException is expected");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * Accuracy test for
     * {@link AbstractInformixProjectPersistence#updateStudioSpecificationForProject(
     * ProjectStudioSpecification, long, String)}
     * method. The prizes can be updated correctly for the specified project.
     *
     * @throws PersistenceException
     *             to JUnit
     */
    @Test
    public void test_updateStudioSpecificationForProject() throws PersistenceException {
        Project project = TestHelper.getSampleProject1();

        persistence.createProject(project, "user");

        ProjectStudioSpecification spec = TestHelper.createProjectStudioSpecification("g1", "t1", "r1");
        persistence.updateStudioSpecificationForProject(spec, project.getId(), "tc");
        spec = persistence.getProjectStudioSpecification(project.getId());

        assertEquals("Should be the same", "g1", spec.getGoals());
        assertEquals("Should be the same", "t1", spec.getTargetAudience());
        assertEquals("Should be the same", "r1", spec.getRoundOneIntroduction());

        spec = TestHelper.createProjectStudioSpecification("g2", "t2", "r2");
        persistence.createProjectStudioSpecification(spec, "user");
        persistence.updateStudioSpecificationForProject(spec, project.getId(), "tc");
        spec = persistence.getProjectStudioSpecification(project.getId());

        assertEquals("Should be the same", "g2", spec.getGoals());
        assertEquals("Should be the same", "t2", spec.getTargetAudience());
        assertEquals("Should be the same", "r2", spec.getRoundOneIntroduction());
    }

    /**
     * Failure test for
     * {@link AbstractInformixProjectPersistence#updateStudioSpecificationForProject(
     * ProjectStudioSpecification, long, String)}
     * method. If the projectId is non-positive, IllegalArgumentException is expected.
     *
     * @throws PersistenceException
     *             to JUnit
     */
    @Test
    public void test_updateStudioSpecificationForProject_failure1() throws PersistenceException {
        try {
            persistence.updateStudioSpecificationForProject(new ProjectStudioSpecification(), -1, "tc");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // good
        }

        try {
            persistence.updateStudioSpecificationForProject(new ProjectStudioSpecification(), 0, "tc");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Failure test for
     * {@link AbstractInformixProjectPersistence#updateStudioSpecificationForProject(
     * ProjectStudioSpecification, long, String)}
     * method. If the spec is null, IllegalArgumentException is expected.
     *
     * @throws PersistenceException
     *             to JUnit
     */
    @Test
    public void test_updateStudioSpecificationForProject_failure2() throws PersistenceException {
        try {
            persistence.updateStudioSpecificationForProject(null, 1, "tc");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Failure test for
     * {@link AbstractInformixProjectPersistence#updateStudioSpecificationForProject(
     * ProjectStudioSpecification, long, String)}
     * method. If the operator is null, IllegalArgumentException is expected.
     *
     * @throws PersistenceException
     *             to JUnit
     */
    @Test
    public void test_updateStudioSpecificationForProject_failure3() throws PersistenceException {
        try {
            persistence.updateStudioSpecificationForProject(new ProjectStudioSpecification(), 1, null);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Failure test for
     * {@link AbstractInformixProjectPersistence#updateStudioSpecificationForProject(
     * ProjectStudioSpecification, long, String)}
     * method. If the operator is empty, IllegalArgumentException is expected.
     *
     * @throws PersistenceException
     *             to JUnit
     */
    @Test
    public void test_updateStudioSpecificationForProject_failure4() throws PersistenceException {
        try {
            persistence.updateStudioSpecificationForProject(new ProjectStudioSpecification(), 1, "  \t\n  ");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Failure test for
     * {@link AbstractInformixProjectPersistence#updateStudioSpecificationForProject(
     * ProjectStudioSpecification, long, String)}
     * method. If the project does not exist, PersistenceException is expected.
     */
    @Test
    public void test_updateStudioSpecificationForProject_failure5() {
        ProjectStudioSpecification spec = TestHelper.createProjectStudioSpecification("goal1", "targetAudience",
            "roundOneIntroduction1");
        try {
            persistence.updateStudioSpecificationForProject(spec, 1, "tc");
            fail("PersistenceException is expected");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * Asserts the retrieved prize instance according to the given parameter instance.
     *
     * @param prize
     *            the expected instance
     * @throws PersistenceException
     *             to JUnit
     */
    private void assertPrize(Prize prize) throws PersistenceException {
        Connection conn = persistence.openConnection();
        Object[][] rows = null;
        try {
            String prizeQuery = "SELECT " + "prize.place, prize.prize_amount, prize.number_of_submissions, "
                + "prize_type.prize_type_id, prize_type.description " + "FROM prize AS prize "
                + "JOIN prize_type_lu AS prize_type ON prize.prize_type_id=prize_type.prize_type_id "
                + "WHERE prize.prize_id=";
            DataType[] columnTypes = {Helper.LONG_TYPE, Helper.Double_TYPE, Helper.LONG_TYPE, Helper.LONG_TYPE,
                Helper.STRING_TYPE};
            rows = Helper.doQuery(conn, prizeQuery + prize.getId(), new Object[]{}, columnTypes);
        } catch (PersistenceException e) {
            persistence.closeConnectionOnError(conn);
        }
        persistence.closeConnection(conn);

        assertEquals("Should be the same", 1, rows.length);
        assertEquals("Should be the same", prize.getPlace(), ((Long) rows[0][0]).intValue());
        assertEquals("Should be the same", prize.getPrizeAmount(), ((Double) rows[0][1]).doubleValue(), 0.0001);
        assertEquals("Should be the same", prize.getNumberOfSubmissions(), ((Long) rows[0][2]).intValue());
        assertEquals("Should be the same", prize.getPrizeType().getId(), ((Long) rows[0][3]).longValue());
        assertEquals("Should be the same", prize.getPrizeType().getDescription(), (String) rows[0][4]);
    }

    /**
     * Asserts the retrieved ProjectStudioSpecification instance according to the given parameter instance.
     *
     * @param spec
     *            the expected instance
     * @throws PersistenceException
     *             to JUnit
     */
    private void assertProjectStudioSpecification(ProjectStudioSpecification spec) throws PersistenceException {
        Connection conn = persistence.openConnection();
        Object[][] rows = null;
        try {
            String specQuery = "SELECT " + "spec.goals, spec.target_audience, "
                + "spec.submitters_locked_between_rounds, " + "spec.round_one_introduction "
                + "FROM project_studio_specification AS spec WHERE spec.project_studio_spec_id=";
            DataType[] columnTypes = new DataType[]{Helper.STRING_TYPE, Helper.STRING_TYPE, Helper.BOOLEAN_TYPE,
                Helper.STRING_TYPE};
            rows = Helper.doQuery(conn, specQuery + spec.getId(), new Object[]{}, columnTypes);
        } catch (PersistenceException e) {
            persistence.closeConnectionOnError(conn);
        }
        persistence.closeConnection(conn);

        assertEquals("Should be the same", 1, rows.length);
        assertEquals("Should be the same", spec.getGoals(), (String) rows[0][0]);
        assertEquals("Should be the same", spec.getTargetAudience(), (String) rows[0][1]);
        assertEquals("Should be the same", spec.isSubmittersLockedBetweenRounds(), ((Boolean) rows[0][2])
            .booleanValue());
        assertEquals("Should be the same", spec.getRoundOneIntroduction(), (String) rows[0][3]);
    }

    /**
     * Asserts the retrieved project instance according to the given parameter instance.
     *
     * @param project
     *            the expected instance
     * @throws PersistenceException
     *             to JUnit
     */
    private void assertProject(Project project) throws PersistenceException {
        Connection conn = persistence.openConnection();

        // query the project table
        try {
            String projectQuery = "SELECT "
                + "spec.goals, spec.target_audience, "
                + "file_type_xref.file_type_id, prize_xref.prize_id "
                + "FROM project "
                + "JOIN project_studio_specification AS spec "
                + "ON spec.project_studio_spec_id=project.project_studio_spec_id "
                + "JOIN project_file_type_xref AS file_type_xref ON file_type_xref.project_id=project.project_id "
                + "JOIN project_prize_xref AS prize_xref ON prize_xref.project_id=project.project_id "
                + "WHERE project.project_id=";
            DataType[] columnTypes = new DataType[]{Helper.STRING_TYPE, Helper.STRING_TYPE, Helper.LONG_TYPE,
                Helper.LONG_TYPE};
            Object[][] projectRows = Helper.doQuery(conn, projectQuery + project.getId(), new Object[]{}, columnTypes);

            assertEquals("only one result", 1, projectRows.length);
            Object[] row = projectRows[0];

            assertEquals("Should be the same", project.getProjectStudioSpecification().getGoals(), (String) row[0]);
            assertEquals("Should be the same", project.getProjectStudioSpecification().getTargetAudience(),
                (String) row[1]);

            project.getProjectFileTypes().get(0).setId((Long) row[2]);
            project.getPrizes().get(0).setId((Long) row[3]);
            assertFileType(project.getProjectFileTypes().get(0));
            assertPrize(project.getPrizes().get(0));
        } catch (PersistenceException e) {
            persistence.closeConnectionOnError(conn);
        }
        persistence.closeConnection(conn);

    }

    /**
     * Asserts the retrieved fileType instance according to the given parameter instance.
     *
     * @param fileType
     *            the expected instance
     * @throws PersistenceException
     *             to JUnit
     */
    private void assertFileType(FileType fileType) throws PersistenceException {
        FileType[] fileTypes = persistence.getAllFileTypes();

        for (FileType type : fileTypes) {
            if (type.getId() == fileType.getId()) {
                // assertEquals("Should be the same", fileType.getCreationUser(), type.getCreationUser());
                // assertEquals("Should be the same", fileType.getCreationUser(), type.getModificationUser());
                assertEquals("Should be the same", fileType.isBundledFile(), type.isBundledFile());
                assertEquals("Should be the same", fileType.getSort(), type.getSort());
                assertEquals("Should be the same", fileType.getExtension(), type.getExtension());
                assertEquals("Should be the same", fileType.getDescription(), type.getDescription());
            }
        }
    }
}
