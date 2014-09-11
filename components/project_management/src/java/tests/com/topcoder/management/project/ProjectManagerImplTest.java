/*
 * Copyright (C) 2006 - 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.util.config.ConfigManager;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for ProjectManagerImpl class.
 *
 * @author iamajia, TCSDEVELOPER
 * @version 1.2
 * @since 1.0
 */
public class ProjectManagerImplTest extends TestCase {
    /** File contains sql statement to initial database. */
    private static final String INIT_DB_SQL = "test_files/InitDB.sql";

    /** File contains sql statement to clear database. */
    private static final String CLEAR_DB_SQL = "test_files/ClearDB.sql";

    /**
     * This project is used in the test.
     */
    private Project project;
    /**
     * this projectManager is used in the test.
     */
    private ProjectManagerImpl projectManager;

    /**
     * Aggregates all tests in this class.
     *
     * @return Test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(ProjectManagerImplTest.class);
    }

    /**
     * Sets up the environment for the TestCase.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    protected void setUp() throws Exception {
        // load config.xml
        ConfigManager.getInstance().add("config.xml");

        // initialize the instance that is used in the test.
        ProjectType projectType = new ProjectType(1, "type1");
        ProjectCategory projectCategory = new ProjectCategory(1, "category1", projectType);
        ProjectStatus projectStatus = new ProjectStatus(1, "status1");
        project = new Project(projectCategory, projectStatus);
        projectManager = new ProjectManagerImpl();
    }

    /**
     * Tears down the environment for the TestCase.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    @SuppressWarnings("unchecked")
    protected void tearDown() throws Exception {
        // remove all namespace.
        ConfigManager cm = ConfigManager.getInstance();
        for (Iterator iter = cm.getAllNamespaces(); iter.hasNext();) {
            cm.removeNamespace((String) iter.next());
        }
    }

    /**
     * Accuracy test of <code>ProjectManagerImpl()</code> constructor.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testProjectManagerImplAccuracy1() throws Exception {
        new ProjectManagerImpl();
    }

    /**
     * Accuracy test of <code>ProjectManagerImpl(String ns)</code> constructor.
     * <p>
     * use 'custom.ProjectManagerImpl' namespace.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testProjectManagerImplAccuracy2() throws Exception {
        new ProjectManagerImpl(ProjectManagerImpl.NAMESPACE);
    }

    /**
     * Failure test of <code>ProjectManagerImpl(String ns)</code> constructor.
     * <p>
     * ns is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testProjectManagerImplFailure1() throws Exception {
        try {
            new ProjectManagerImpl(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>ProjectManagerImpl(String ns)</code> constructor.
     * <p>
     * ns is empty.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testProjectManagerImplFailure2() throws Exception {
        try {
            new ProjectManagerImpl("  ");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>ProjectManagerImpl(String ns)</code> constructor.
     * <p>
     *'PersistenceClass' property is missed.
     * </p>
     * <p>
     * Expect ConfigurationException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testProjectManagerImplFailure3() throws Exception {
        try {
            new ProjectManagerImpl("invalid1.ProjectManagerImpl");
            fail("Expect ConfigurationException.");
        } catch (ConfigurationException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>ProjectManagerImpl(String ns)</code> constructor.
     * <p>
     *'ValidatorClass' property is missed.
     * </p>
     * <p>
     * Expect ConfigurationException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testProjectManagerImplFailure4() throws Exception {
        try {
            new ProjectManagerImpl("invalid2.ProjectManagerImpl");
            fail("Expect ConfigurationException.");
        } catch (ConfigurationException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>ProjectManagerImpl(String ns)</code> constructor.
     * <p>
     *'SearchBuilderNamespace' property is missed.
     * </p>
     * <p>
     * Expect ConfigurationException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testProjectManagerImplFailure5() throws Exception {
        try {
            new ProjectManagerImpl("invalid3.ProjectManagerImpl");
            fail("Expect ConfigurationException.");
        } catch (ConfigurationException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>ProjectManagerImpl(String ns)</code> constructor.
     * <p>
     *'PersistenceClass' is String.
     * </p>
     * <p>
     * Expect ConfigurationException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testProjectManagerImplFailure6() throws Exception {
        try {
            new ProjectManagerImpl("invalid4.ProjectManagerImpl");
            fail("Expect ConfigurationException.");
        } catch (ConfigurationException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>ProjectManagerImpl(String ns)</code> constructor.
     * <p>
     * PersistenceClass is empty.
     * </p>
     * <p>
     * Expect ConfigurationException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testProjectManagerImplFailure7() throws Exception {
        try {
            new ProjectManagerImpl("invalid5.ProjectManagerImpl");
            fail("Expect ConfigurationException.");
        } catch (ConfigurationException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>ProjectManagerImpl(String ns)</code> constructor.
     * <p>
     * PersistenceClass is invalid.
     * </p>
     * <p>
     * Expect ConfigurationException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testProjectManagerImplFailure8() throws Exception {
        try {
            new ProjectManagerImpl("invalid6.ProjectManagerImpl");
            fail("Expect ConfigurationException.");
        } catch (ConfigurationException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>createProject(Project project, String operator)</code> method.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testCreateProjectAccuracy() throws Exception {
        projectManager.createProject(project, "test");
    }

    /**
     * Failure test of <code>createProject(Project project, String operator)</code> method.
     * <p>
     * project is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testCreateProjectFailure1() throws Exception {
        try {
            projectManager.createProject(null, "test");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>createProject(Project project, String operator)</code> method.
     * <p>
     * operator is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testCreateProjectFailure2() throws Exception {
        try {
            projectManager.createProject(project, null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>createProject(Project project, String operator)</code> method.
     * <p>
     * operator is empty.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testCreateProjectFailure3() throws Exception {
        try {
            projectManager.createProject(project, "   ");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>updateProject(Project project, String reason, String operator)</code> method.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testUpdateProjectAccuracy() throws Exception {
        projectManager.updateProject(project, "reason", "test");
    }

    /**
     * Failure test of <code>updateProject(Project project, String reason, String operator)</code> method.
     * <p>
     * project is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testUpdateProjectFailure1() throws Exception {
        try {
            projectManager.updateProject(null, "reason", "test");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>updateProject(Project project, String reason, String operator)</code> method.
     * <p>
     * operator is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testUpdateProjectFailure2() throws Exception {
        try {
            projectManager.updateProject(project, "reason", null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>updateProject(Project project, String reason, String operator)</code> method.
     * <p>
     * operator is empty.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testUpdateProjectFailure3() throws Exception {
        try {
            projectManager.updateProject(project, "reason", "  ");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>updateProject(Project project, String reason, String operator)</code> method.
     * <p>
     * reason is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testUpdateProjectFailure4() throws Exception {
        try {
            projectManager.updateProject(project, null, "test");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>getProject(long id)</code> method.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testGetProjectAccuracy() throws Exception {
        projectManager.getProject(1);
    }

    /**
     * Failure test of <code>getProject(long id)</code> method.
     * <p>
     * id <= 0
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testGetProjectFailure() throws Exception {
        try {
            projectManager.getProject(0);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>getProjects(long[] ids)</code> method.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testGetProjectsAccuracy() throws Exception {
        long[] ids = {1, 2, 3};
        projectManager.getProjects(ids);
    }

    /**
     * Failure test of <code>getProjects(long[] ids)</code> method.
     * <p>
     * ids is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testGetProjectsFailure1() throws Exception {
        try {
            projectManager.getProjects(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>getProjects(long[] ids)</code> method.
     * <p>
     * ids is empty.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testGetProjectsFailure2() throws Exception {
        try {
            projectManager.getProjects(new long[0]);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>getProjects(long[] ids)</code> method.
     * <p>
     * ids contain an id <= 0
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testGetProjectsFailure3() throws Exception {
        try {
            projectManager.getProjects(new long[]{1, 0});
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>searchProjects(Filter filter)</code> method.
     *
     * @throws Exception
     *             throw exception to JUnit.
     * @since 1.0
     */
    @SuppressWarnings("unchecked")
    public void testSearchProjectsAccuracy() throws Exception {
        try {
            executeSQL(INIT_DB_SQL);
            Project[] projects = projectManager.searchProjects(ProjectFilterUtility.buildTypeIdEqualFilter(1));

            Set ids = new HashSet();

            for (int i = 0; i < projects.length; ++i) {
                ids.add(new Long(projects[i].getId()));
            }

            Set expectIds = new HashSet();
            expectIds.add(new Long(1));
            expectIds.add(new Long(2));
            expectIds.add(new Long(3));
            expectIds.add(new Long(4));

            assertEquals("ids is incorrect..", expectIds, ids);
        } finally {
            executeSQL(CLEAR_DB_SQL);
        }
    }

    /**
     * Failure test of <code>searchProjects(Filter filter)</code> method.
     * <p>
     * filter is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSearchProjectsFailure() throws Exception {
        try {
            projectManager.searchProjects(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>getUserProjects(long user)</code> method.
     *
     * @throws Exception
     *             throw exception to JUnit.
     * @since 1.0
     */
    @SuppressWarnings("unchecked")
    public void testGetUserProjectsAccuracy() throws Exception {
        try {
            executeSQL(INIT_DB_SQL);
            Project[] projects = projectManager.getUserProjects(999);

            Set ids = new HashSet();

            for (int i = 0; i < projects.length; ++i) {
                ids.add(new Long(projects[i].getId()));
            }

            Set expectIds = new HashSet();
            expectIds.add(new Long(1));
            expectIds.add(new Long(2));
            expectIds.add(new Long(3));
            expectIds.add(new Long(4));

            assertEquals("ids is incorrect.", expectIds, ids);
        } finally {
            executeSQL(CLEAR_DB_SQL);
        }
    }

    /**
     * Accuracy test of <code>getAllProjectTypes()</code> method.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testGetAllProjectTypesAccuracy() throws Exception {
        projectManager.getAllProjectTypes();
    }

    /**
     * Accuracy test of <code>getAllProjectCategories()</code> method.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testGetAllProjectCategoriesAccuracy() throws Exception {
        projectManager.getAllProjectCategories();
    }

    /**
     * Accuracy test of <code>getAllProjectStatuses()</code> method.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testGetAllProjectStatusesAccuracy() throws Exception {
        projectManager.getAllProjectStatuses();
    }

    /**
     * Accuracy test of <code>getAllProjectPropertyTypes()</code> method.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testGetAllProjectPropertyTypesAccuracy() throws Exception {
        projectManager.getAllProjectPropertyTypes();
    }

    /**
     * Accuracy test for {@link AbstractInformixProjectPersistence#getProjectsByDirectProjectId(long)} method. It should
     * get the project correctly.
     *
     * @throws PersistenceException
     *             to JUnit
     * @since 1.2
     */
    public void test_getProjectsByDirectProjectId() throws PersistenceException {
        long directProjectId = 1;
        assertEquals("Should be the same", 0, projectManager.getProjectsByDirectProjectId(directProjectId).length);
    }

    /**
     * Failure test for {@link AbstractInformixProjectPersistence#getProjectsByDirectProjectId(long)} method. If the
     * argument is non-positive, IllegalArgumentException is expected.
     *
     * @throws PersistenceException
     *             to JUnit
     * @since 1.2
     */
    public void test_getProjectsByDirectProjectId_failure1() throws PersistenceException {
        try {
            projectManager.getProjectsByDirectProjectId(-1);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // good
        }

        try {
            projectManager.getProjectsByDirectProjectId(0);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Failure test for {@link AbstractInformixProjectPersistence#getProjectsByDirectProjectId(long)} method. If the
     * argument is 2, PersistenceException is expected.
     *
     * @since 1.2
     */
    public void test_getProjectsByDirectProjectId_failure2() {
        try {
            projectManager.getProjectsByDirectProjectId(2);
            fail("PersistenceException is expected");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * Accuracy test for {@link AbstractInformixProjectPersistence#getProjectFileTypes(long)} method. The file type can
     * be gotten correctly for the specified project.
     *
     * @throws Exception
     *             to JUnit
     * @since 1.2
     */
    public void test_getProjectFileTypes() throws Exception {
        FileType[] fileTypes = projectManager.getProjectFileTypes(1);

        assertEquals("Should be the same", 0, fileTypes.length);
    }

    /**
     * Failure test for {@link AbstractInformixProjectPersistence#getProjectFileTypes(long)} method. If the argument is
     * non-positive, IllegalArgumentException is expected.
     *
     * @throws PersistenceException
     *             to JUnit
     * @since 1.2
     */
    public void test_getProjectFileTypes_failure1() throws PersistenceException {
        try {
            projectManager.getProjectFileTypes(-1);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // good
        }

        try {
            projectManager.getProjectFileTypes(0);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Failure test for {@link AbstractInformixProjectPersistence#getProjectFileTypes(long)} method. If the project does
     * not exist, PersistenceException is expected.
     *
     * @since 1.2
     */
    public void test_getProjectFileTypes_failure2() {
        try {
            projectManager.getProjectFileTypes(2);
            fail("PersistenceException is expected");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * Accuracy test for {@link AbstractInformixProjectPersistence#updateProjectFileTypes(long, List)} method. The file
     * type can be updated correctly for the specified project.
     *
     * @throws Exception
     *             to JUnit
     * @since 1.2
     */
    public void test_updateProjectFileTypes() throws Exception {
        Project project = getSampleProject1();

        projectManager.createProject(project, "user");

        List<FileType> fileTypes = new ArrayList<FileType>();
        fileTypes.add(createFileType("d1", "e1", true, false, 2));
        FileType fileType = createFileType("d2", "e2", true, true, 3);
        projectManager.createFileType(fileType, "tc");
        fileTypes.add(fileType);

        projectManager.updateProjectFileTypes(1, fileTypes, "tc");

        assertEquals("Should be the same", "tc", fileTypes.get(0).getModificationUser());
    }

    /**
     * Failure test for {@link AbstractInformixProjectPersistence#updateProjectFileTypes(long, List, String)} method. If
     * the projectId is non-positive, IllegalArgumentException is expected.
     *
     * @throws PersistenceException
     *             to JUnit
     * @since 1.2
     */
    public void test_updateProjectFileTypes_failure1() throws PersistenceException {
        try {
            projectManager.updateProjectFileTypes(-1, new ArrayList<FileType>(), "tc");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // good
        }

        try {
            projectManager.updateProjectFileTypes(0, new ArrayList<FileType>(), "tc");
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
     * @since 1.2
     */
    public void test_updateProjectFileTypes_failure2() throws PersistenceException {
        List<FileType> fileTypes = new ArrayList<FileType>();
        fileTypes.add(null);
        try {
            projectManager.updateProjectFileTypes(1, fileTypes, "tc");
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
     * @since 1.2
     */
    public void test_updateProjectFileTypes_failure3() throws PersistenceException {
        try {
            projectManager.updateProjectFileTypes(1, new ArrayList<FileType>(), null);
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
     * @since 1.2
     */
    public void test_updateProjectFileTypes_failure4() throws PersistenceException {
        try {
            projectManager.updateProjectFileTypes(1, new ArrayList<FileType>(), " \t\t ");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Failure test for {@link AbstractInformixProjectPersistence#updateProjectFileTypes(long, List, String)} method. If
     * the project does not exist, PersistenceException is expected.
     *
     * @since 1.2
     */
    public void test_updateProjectFileTypes_failure5() {
        List<FileType> fileTypes = new ArrayList<FileType>();
        fileTypes.add(createFileType("d1", "e1", true, false, 2));
        try {
            projectManager.updateProjectFileTypes(2, fileTypes, "tc");
            fail("PersistenceException is expected");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * Accuracy test for {@link AbstractInformixProjectPersistence#getProjectPrizes(long)} method. The prizes can be
     * gotten correctly for the specified project.
     *
     * @throws Exception
     *             to JUnit
     * @since 1.2
     */
    public void test_getProjectPrizes() throws Exception {
        Project project = getSampleProject1();

        projectManager.createProject(project, "user");

        Prize[] prizes = projectManager.getProjectPrizes(1);

        assertEquals("Should be the same", 0, prizes.length);
    }

    /**
     * Failure test for {@link AbstractInformixProjectPersistence#getProjectPrizes(long)} method. If the argument is
     * non-positive, IllegalArgumentException is expected.
     *
     * @throws PersistenceException
     *             to JUnit
     * @since 1.2
     */
    public void test_getProjectPrizes_failure1() throws PersistenceException {
        try {
            projectManager.getProjectPrizes(-1);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // good
        }

        try {
            projectManager.getProjectPrizes(0);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Failure test for {@link AbstractInformixProjectPersistence#getProjectPrizes(long)} method. If the project does
     * not exist, PersistenceException is expected.
     *
     * @since 1.2
     */
    public void test_getProjectPrizes_failure2() {
        try {
            projectManager.getProjectPrizes(2);
            fail("PersistenceException is expected");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * Accuracy test for {@link AbstractInformixProjectPersistence#updateProjectPrizes(long, List)} method. The prizes
     * can be updated correctly for the specified project.
     *
     * @throws Exception
     *             to JUnit
     * @since 1.2
     */
    public void test_updateProjectPrizes() throws Exception {
        Project project = getSampleProject1();

        projectManager.createProject(project, "user");

        List<Prize> prizes = new ArrayList<Prize>();
        prizes.add(createPrize(1, 100, 2));
        Prize prize = createPrize(2, 200, 4);
        projectManager.createPrize(prize, "tc");
        prizes.add(prize);

        projectManager.updateProjectPrizes(1, prizes, "tc");

        assertEquals("Should be the same", "tc", prizes.get(0).getModificationUser());
    }

    /**
     * Failure test for {@link AbstractInformixProjectPersistence#updateProjectPrizes(long, List, String)} method. If
     * the projectId is non-positive, IllegalArgumentException is expected.
     *
     * @throws PersistenceException
     *             to JUnit
     * @since 1.2
     */
    public void test_updateProjectPrizes_failure1() throws PersistenceException {
        try {
            projectManager.updateProjectPrizes(-1, new ArrayList<Prize>(), "tc");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // good
        }

        try {
            projectManager.updateProjectPrizes(0, new ArrayList<Prize>(), "tc");
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
     * @since 1.2
     */
    public void test_updateProjectPrizes_failure2() throws PersistenceException {
        List<Prize> prizes = new ArrayList<Prize>();
        prizes.add(null);
        try {
            projectManager.updateProjectPrizes(1, prizes, "tc");
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
     * @since 1.2
     */
    public void test_updateProjectPrizes_failure3() throws PersistenceException {
        try {
            projectManager.updateProjectPrizes(1, new ArrayList<Prize>(), null);
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
     * @since 1.2
     */
    public void test_updateProjectPrizes_failure4() throws PersistenceException {
        try {
            projectManager.updateProjectPrizes(1, new ArrayList<Prize>(), " \t\t ");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Failure test for {@link AbstractInformixProjectPersistence#updateProjectPrizes(long, List, String)} method. If
     * the project does not exist, PersistenceException is expected.
     *
     * @since 1.2
     */
    public void test_updateProjectPrizes_failure5() {
        List<Prize> prizes = new ArrayList<Prize>();
        prizes.add(createPrize(1, 100, 2));
        try {
            projectManager.updateProjectPrizes(2, prizes, "tc");
            fail("PersistenceException is expected");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * Accuracy test for {@link AbstractInformixProjectPersistence#createFileType(FileType, String)} method. The file
     * type can be created correctly.
     *
     * @throws Exception
     *             to JUnit
     * @since 1.2
     */
    public void test_createFileType() throws Exception {
        FileType fileType = createFileType("description 1", "extension 1", true, false, 1);
        fileType = projectManager.createFileType(fileType, "tc");
        assertEquals("Should be the same", "tc", fileType.getCreationUser());
    }

    /**
     * Failure test for {@link AbstractInformixProjectPersistence#createFileType(FileType, String)} method. If the
     * fileType is null, IllegalArgumentException is expected.
     *
     * @throws PersistenceException
     *             to JUnit
     * @since 1.2
     */
    public void test_createFileType_failure1() throws PersistenceException {
        try {
            projectManager.createFileType(null, "tc");
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
     * @since 1.2
     */
    public void test_createFileType_failure2() throws PersistenceException {
        try {
            projectManager.createFileType(new FileType(), null);
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
     * @since 1.2
     */
    public void test_createFileType_failure3() throws PersistenceException {
        try {
            projectManager.createFileType(new FileType(), "  ");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Failure test for {@link AbstractInformixProjectPersistence#createFileType(FileType, String)} method. If the file
     * type is already in the database, PersistenceException is expected.
     *
     * @since 1.2
     */
    public void test_createFileType_failure4() throws PersistenceException {
        FileType fileType = createFileType("description 1", "extension 1", true, false, 1);
        fileType = projectManager.createFileType(fileType, "tc");
        try {
            projectManager.createFileType(fileType, "t");
            fail("PersistenceException is expected");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * Accuracy test for {@link AbstractInformixProjectPersistence#updateFileType(FileType, String)} method. The file
     * type can be updated correctly.
     *
     * @throws Exception
     *             to JUnit
     * @since 1.2
     */
    public void test_updateFileType() throws Exception {
        FileType fileType = createFileType("description 2", "extension 2", true, false, 1);
        fileType = projectManager.createFileType(fileType, "tc1");

        fileType.setBundledFile(true);
        fileType.setSort(2);
        fileType.setExtension("extension3");
        projectManager.updateFileType(fileType, "tc2");
        assertEquals("Should be the same", "tc2", fileType.getModificationUser());
    }

    /**
     * Failure test for {@link AbstractInformixProjectPersistence#updateFileType(FileType, String)} method. If the
     * fileType is null, IllegalArgumentException is expected.
     *
     * @throws PersistenceException
     *             to JUnit
     * @since 1.2
     */
    public void test_updateFileType_failure1() throws PersistenceException {
        try {
            projectManager.updateFileType(null, "tc");
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
     * @since 1.2
     */
    public void test_updateFileType_failure2() throws PersistenceException {
        try {
            projectManager.updateFileType(new FileType(), null);
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
     * @since 1.2
     */
    public void test_updateFileType_failure3() throws PersistenceException {
        try {
            projectManager.updateFileType(new FileType(), "  ");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Failure test for {@link AbstractInformixProjectPersistence#updateFileType(FileType, String)} method. If the file
     * type is not in the database, PersistenceException is expected.
     *
     * @since 1.2
     */
    public void test_updateFileType_failure4() throws PersistenceException {
        FileType fileType = createFileType("description 1", "extension 1", true, false, 1);
        fileType.setId(1);
        try {
            projectManager.updateFileType(fileType, "t");
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
     * @since 1.2
     */
    public void test_removeFileType() throws PersistenceException {
        FileType fileType = createFileType("description 2", "extension 2", true, false, 1);
        fileType = projectManager.createFileType(fileType, "tc1");

        projectManager.removeFileType(fileType, "tc2");
    }

    /**
     * Failure test for {@link AbstractInformixProjectPersistence#removeFileType(FileType, String)} method. If the
     * fileType is null, IllegalArgumentException is expected.
     *
     * @throws PersistenceException
     *             to JUnit
     * @since 1.2
     */
    public void test_removeFileType_failure1() throws PersistenceException {
        try {
            projectManager.removeFileType(null, "tc");
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
     * @since 1.2
     */
    public void test_removeFileType_failure2() throws PersistenceException {
        try {
            projectManager.removeFileType(new FileType(), null);
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
     * @since 1.2
     */
    public void test_removeFileType_failure3() throws PersistenceException {
        try {
            projectManager.removeFileType(new FileType(), "  ");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Failure test for {@link AbstractInformixProjectPersistence#removeFileType(FileType, String)} method. If the file
     * type is not in the database, PersistenceException is expected.
     *
     * @since 1.2
     */
    public void test_removeFileType_failure4() throws PersistenceException {
        FileType fileType = createFileType("description 1", "extension 1", true, false, 1);
        fileType.setId(1);
        try {
            projectManager.removeFileType(fileType, "t");
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
     * @since 1.2
     */
    public void test_getAllFileTypes() throws PersistenceException {
        FileType fileType1 = createFileType("description 1", "extension 1", true, false, 1);
        fileType1 = projectManager.createFileType(fileType1, "tc1");
        FileType fileType2 = createFileType("description 2", "extension 2", true, false, 1);
        fileType2 = projectManager.createFileType(fileType2, "tc1");
        FileType fileType3 = createFileType("description 3", "extension 3", true, true, 1);
        fileType3 = projectManager.createFileType(fileType3, "tc1");

        FileType fileTypes[] = projectManager.getAllFileTypes();
        assertEquals("Should be the same", 0, fileTypes.length);
    }

    /**
     * Accuracy test for {@link AbstractInformixProjectPersistence#getPrizeTypes()} method. All the file type can be
     * gotten correctly.
     *
     * @throws PersistenceException
     *             to JUnit
     * @since 1.2
     */
    public void test_getPrizeTypes() throws PersistenceException {
        PrizeType[] prizeTypes = projectManager.getPrizeTypes();

        assertEquals("Should be the same", 0, prizeTypes.length);
    }

    /**
     * Accuracy test for {@link AbstractInformixProjectPersistence#createPrize(Prize, String)} method. The prize can be
     * created correctly.
     *
     * @throws PersistenceException
     *             to JUnit
     * @since 1.2
     */
    public void test_createPrize() throws PersistenceException {
        Prize prize = createPrize(1, 600.00, 5);
        prize = projectManager.createPrize(prize, "tc");
        assertEquals("Should be the same", "tc", prize.getCreationUser());
    }

    /**
     * Failure test for {@link AbstractInformixProjectPersistence#createPrize(Prize, String)} method. If the fileType is
     * null, IllegalArgumentException is expected.
     *
     * @throws PersistenceException
     *             to JUnit
     * @since 1.2
     */
    public void test_createPrize_failure1() throws PersistenceException {
        try {
            projectManager.createPrize(null, "tc");
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
     * @since 1.2
     */
    public void test_createPrize_failure2() throws PersistenceException {
        try {
            projectManager.createPrize(new Prize(), null);
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
     * @since 1.2
     */
    public void test_createPrize_failure3() throws PersistenceException {
        try {
            projectManager.createPrize(new Prize(), "  ");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Failure test for {@link AbstractInformixProjectPersistence#createPrize(Prize, String)} method. If the file type
     * is already in the database, PersistenceException is expected.
     *
     * @since 1.2
     */
    public void test_createPrize_failure4() throws PersistenceException {
        Prize prize = createPrize(1, 100, 5);
        prize = projectManager.createPrize(prize, "tc");
        try {
            projectManager.createPrize(prize, "t");
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
     * @since 1.2
     */
    public void test_updatePrize() throws PersistenceException {
        Prize prize = createPrize(1, 600.00, 5);
        prize = projectManager.createPrize(prize, "tc");

        prize.setPlace(2);
        prize.setNumberOfSubmissions(10);
        projectManager.updatePrize(prize, "tc1");
        assertEquals("Should be the same", "tc1", prize.getModificationUser());
    }

    /**
     * Failure test for {@link AbstractInformixProjectPersistence#updatePrize(Prize, String)} method. If the fileType is
     * null, IllegalArgumentException is expected.
     *
     * @throws PersistenceException
     *             to JUnit
     * @since 1.2
     */
    public void test_updatePrize_failure1() throws PersistenceException {
        try {
            projectManager.updatePrize(null, "tc");
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
     * @since 1.2
     */
    public void test_updatePrize_failure2() throws PersistenceException {
        try {
            projectManager.updatePrize(new Prize(), null);
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
     * @since 1.2
     */
    public void test_updatePrize_failure3() throws PersistenceException {
        try {
            projectManager.updatePrize(new Prize(), "  ");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Failure test for {@link AbstractInformixProjectPersistence#updatePrize(Prize, String)} method. If the file type
     * is not in the database, PersistenceException is expected.
     *
     * @since 1.2
     */
    public void test_updatePrize_failure4() throws PersistenceException {
        Prize prize = createPrize(1, 100, 5);
        prize.setId(1);
        try {
            projectManager.updatePrize(prize, "t");
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
     * @since 1.2
     */
    public void test_removePrize() throws PersistenceException {
        Prize prize = createPrize(1, 600.00, 5);
        prize = projectManager.createPrize(prize, "tc");

        projectManager.removePrize(prize, "tc2");

    }

    /**
     * Failure test for {@link AbstractInformixProjectPersistence#removePrize(Prize, String)} method. If the fileType is
     * null, IllegalArgumentException is expected.
     *
     * @throws PersistenceException
     *             to JUnit
     * @since 1.2
     */
    public void test_removePrize_failure1() throws PersistenceException {
        try {
            projectManager.removePrize(null, "tc");
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
     * @since 1.2
     */
    public void test_removePrize_failure2() throws PersistenceException {
        try {
            projectManager.removePrize(new Prize(), null);
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
     * @since 1.2
     */
    public void test_removePrize_failure3() throws PersistenceException {
        try {
            projectManager.removePrize(new Prize(), "  ");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Failure test for {@link AbstractInformixProjectPersistence#removePrize(Prize, String)} method. If the file type
     * is not in the database, PersistenceException is expected.
     *
     * @since 1.2
     */
    public void test_removePrizePrize_failure4() throws PersistenceException {
        Prize prize = createPrize(1, 100, 5);
        prize.setId(1);
        try {
            projectManager.removePrize(prize, "t");
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
     * @since 1.2
     */
    public void test_createProjectStudioSpecification() throws PersistenceException {
        ProjectStudioSpecification spec = createProjectStudioSpecification("goal1", "targetAudience",
            "roundOneIntroduction1");
        spec = projectManager.createProjectStudioSpecification(spec, "tc");
        assertEquals("Should be the same", "tc", spec.getCreationUser());

    }

    /**
     * Failure test for
     * {@link AbstractInformixProjectPersistence#createProjectStudioSpecification(ProjectStudioSpecification, String)}
     * method. If the fileType is null, IllegalArgumentException is expected.
     *
     * @throws PersistenceException
     *             to JUnit
     * @since 1.2
     */
    public void test_createProjectStudioSpecification_failure1() throws PersistenceException {
        try {
            projectManager.createProjectStudioSpecification(null, "tc");
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
     * @since 1.2
     */
    public void test_createProjectStudioSpecification_failure2() throws PersistenceException {
        try {
            projectManager.createProjectStudioSpecification(new ProjectStudioSpecification(), null);
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
     * @since 1.2
     */
    public void test_createProjectStudioSpecification_failure3() throws PersistenceException {
        try {
            projectManager.createProjectStudioSpecification(new ProjectStudioSpecification(), "  ");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Failure test for
     * {@link AbstractInformixProjectPersistence#createProjectStudioSpecification(ProjectStudioSpecification, String)}
     * method. If the file type is already in the database, PersistenceException is expected.
     *
     * @since 1.2
     */
    public void test_createProjectStudioSpecification_failure4() throws PersistenceException {
        ProjectStudioSpecification spec = createProjectStudioSpecification("goal1", "targetAudience",
            "roundOneIntroduction1");
        spec = projectManager.createProjectStudioSpecification(spec, "tc");
        try {
            projectManager.createProjectStudioSpecification(spec, "t");
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
     * @since 1.2
     */
    public void test_updateProjectStudioSpecification() throws PersistenceException {
        ProjectStudioSpecification spec = createProjectStudioSpecification("goal1", "targetAudience",
            "roundOneIntroduction1");
        spec = projectManager.createProjectStudioSpecification(spec, "tc");

        spec.setSubmittersLockedBetweenRounds(false);
        spec.setGoals("goal");

        projectManager.updateProjectStudioSpecification(spec, "tc1");
        assertEquals("Should be the same", "tc1", spec.getModificationUser());

    }

    /**
     * Failure test for
     * {@link AbstractInformixProjectPersistence#updateProjectStudioSpecification(ProjectStudioSpecification, String)}
     * method. If the fileType is null, IllegalArgumentException is expected.
     *
     * @throws PersistenceException
     *             to JUnit
     * @since 1.2
     */
    public void test_updateProjectStudioSpecification_failure1() throws PersistenceException {
        try {
            projectManager.updateProjectStudioSpecification(null, "tc");
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
     * @since 1.2
     */
    public void test_updateProjectStudioSpecification_failure2() throws PersistenceException {
        try {
            projectManager.updateProjectStudioSpecification(new ProjectStudioSpecification(), null);
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
     * @since 1.2
     */
    public void test_updateProjectStudioSpecification_failure3() throws PersistenceException {
        try {
            projectManager.updateProjectStudioSpecification(new ProjectStudioSpecification(), "  ");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Failure test for
     * {@link AbstractInformixProjectPersistence#updateProjectStudioSpecification(ProjectStudioSpecification, String)}
     * method. If the file type is already in the database, PersistenceException is expected.
     *
     * @since 1.2
     */
    public void test_updateProjectStudioSpecification_failure4() throws PersistenceException {
        ProjectStudioSpecification spec = createProjectStudioSpecification("goal1", "targetAudience",
            "roundOneIntroduction1");
        spec.setId(1);
        try {
            projectManager.updateProjectStudioSpecification(spec, "t");
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
     * @since 1.2
     */
    public void test_removeProjectStudioSpecification() throws PersistenceException {
        ProjectStudioSpecification spec = createProjectStudioSpecification("goal1", "targetAudience",
            "roundOneIntroduction1");
        spec = projectManager.createProjectStudioSpecification(spec, "tc");

        projectManager.removeProjectStudioSpecification(spec, "tc2");

    }

    /**
     * Failure test for
     * {@link AbstractInformixProjectPersistence#removeProjectStudioSpecification(ProjectStudioSpecification, String)}
     * method. If the fileType is null, IllegalArgumentException is expected.
     *
     * @throws PersistenceException
     *             to JUnit
     * @since 1.2
     */
    public void test_removeProjectStudioSpecification_failure1() throws PersistenceException {
        try {
            projectManager.removeProjectStudioSpecification(null, "tc");
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
     * @since 1.2
     */
    public void test_removeProjectStudioSpecification_failure2() throws PersistenceException {
        try {
            projectManager.removeProjectStudioSpecification(new ProjectStudioSpecification(), null);
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
     * @since 1.2
     */
    public void test_removeProjectStudioSpecification_failure3() throws PersistenceException {
        try {
            projectManager.removeProjectStudioSpecification(new ProjectStudioSpecification(), "  ");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Failure test for
     * {@link AbstractInformixProjectPersistence#removeProjectStudioSpecification(ProjectStudioSpecification, String)}
     * method. If the file type is already in the database, PersistenceException is expected.
     *
     * @since 1.2
     */
    public void test_removeProjectStudioSpecification_failure4() throws PersistenceException {
        ProjectStudioSpecification spec = createProjectStudioSpecification("goal1", "targetAudience",
            "roundOneIntroduction1");
        spec.setId(1);
        try {
            projectManager.removeProjectStudioSpecification(spec, "t");
            fail("PersistenceException is expected");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * Accuracy test for {@link AbstractInformixProjectPersistence#getProjectStudioSpecification(long)} method. The
     * studio specification can be gotten correctly for the specified project.
     *
     * @throws Exception
     *             to JUnit
     * @since 1.2
     */
    public void test_getProjectStudioSpecification() throws Exception {
        ProjectStudioSpecification spec = projectManager.getProjectStudioSpecification(1);

        assertNull("Should be null", spec);
    }

    /**
     * Failure test for {@link AbstractInformixProjectPersistence#getProjectStudioSpecification(long)} method. If the
     * argument is non-positive, IllegalArgumentException is expected.
     *
     * @throws PersistenceException
     *             to JUnit
     * @since 1.2
     */
    public void test_getProjectStudioSpecification_failure1() throws PersistenceException {
        try {
            projectManager.getProjectStudioSpecification(-1);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // good
        }

        try {
            projectManager.getProjectStudioSpecification(0);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Failure test for {@link AbstractInformixProjectPersistence#getProjectStudioSpecification(long)} method. If the
     * project does not exist, PersistenceException is expected.
     *
     * @since 1.2
     */
    public void test_getProjectStudioSpecification_failure2() {
        try {
            projectManager.getProjectStudioSpecification(2);
            fail("PersistenceException is expected");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * Accuracy test for
     * {@link AbstractInformixProjectPersistence#updateStudioSpecificationForProject(ProjectStudioSpecification, long, String)}
     * method. The prizes can be updated correctly for the specified project.
     *
     * @throws Exception
     *             to JUnit
     * @since 1.2
     */
    public void test_updateStudioSpecificationForProject() throws Exception {
        Project project = getSampleProject1();

        projectManager.createProject(project, "user");

        ProjectStudioSpecification spec = createProjectStudioSpecification("g1", "t1", "r1");
        projectManager.updateStudioSpecificationForProject(spec, 1, "tc");
    }

    /**
     * Failure test for
     * {@link AbstractInformixProjectPersistence#updateStudioSpecificationForProject(ProjectStudioSpecification, long, String)}
     * method. If the projectId is non-positive, IllegalArgumentException is expected.
     *
     * @throws PersistenceException
     *             to JUnit
     * @since 1.2
     */
    public void test_updateStudioSpecificationForProject_failure1() throws PersistenceException {
        try {
            projectManager.updateStudioSpecificationForProject(new ProjectStudioSpecification(), -1, "tc");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // good
        }

        try {
            projectManager.updateStudioSpecificationForProject(new ProjectStudioSpecification(), 0, "tc");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Failure test for
     * {@link AbstractInformixProjectPersistence#updateStudioSpecificationForProject(ProjectStudioSpecification, long, String)}
     * method. If the spec is null, IllegalArgumentException is expected.
     *
     * @throws PersistenceException
     *             to JUnit
     * @since 1.2
     */
    public void test_updateStudioSpecificationForProject_failure2() throws PersistenceException {
        try {
            projectManager.updateStudioSpecificationForProject(null, 1, "tc");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Failure test for
     * {@link AbstractInformixProjectPersistence#updateStudioSpecificationForProject(ProjectStudioSpecification, long, String)}
     * method. If the operator is null, IllegalArgumentException is expected.
     *
     * @throws PersistenceException
     *             to JUnit
     * @since 1.2
     */
    public void test_updateStudioSpecificationForProject_failure3() throws PersistenceException {
        try {
            projectManager.updateStudioSpecificationForProject(new ProjectStudioSpecification(), 1, null);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Failure test for
     * {@link AbstractInformixProjectPersistence#updateStudioSpecificationForProject(ProjectStudioSpecification, long, String)}
     * method. If the operator is empty, IllegalArgumentException is expected.
     *
     * @throws PersistenceException
     *             to JUnit
     * @since 1.2
     */
    public void test_updateStudioSpecificationForProject_failure4() throws PersistenceException {
        try {
            projectManager.updateStudioSpecificationForProject(new ProjectStudioSpecification(), 1, "  \t\n  ");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Failure test for
     * {@link AbstractInformixProjectPersistence#updateStudioSpecificationForProject(ProjectStudioSpecification, long, String)}
     * method. If the project does not exist, PersistenceException is expected.
     *
     * @since 1.2
     */
    public void test_updateStudioSpecificationForProject_failure5() {
        ProjectStudioSpecification spec = createProjectStudioSpecification("goal1", "targetAudience",
            "roundOneIntroduction1");
        try {
            projectManager.updateStudioSpecificationForProject(spec, 2, "tc");
            fail("PersistenceException is expected");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * Executes a sql batch contains in a file.
     *
     * @param file
     *            the file contains the sql statements.
     * @throws Exception
     *             pass to JUnit.
     */
    @SuppressWarnings("deprecation")
    private void executeSQL(String file) throws Exception {
        // get db connection
        Connection connection = new DBConnectionFactoryImpl("Dbconnection.factory").createConnection();
        Statement statement = connection.createStatement();

        // get sql statements and add to statement
        BufferedReader in = new BufferedReader(new FileReader(file));
        String line = null;

        while ((line = in.readLine()) != null) {
            if (line.trim().length() != 0) {
                statement.addBatch(line);
            }
        }

        statement.executeBatch();
    }

    /**
     * Creates file type instance according to the given fields values.
     *
     * @param description
     *            the description field value to set
     * @param extension
     *            the extension field value to set
     * @param imageFile
     *            the imageFile field value to set
     * @param bundledFile
     *            the bundledFile field value to set
     * @param sort
     *            the sort field value to set
     * @return the created instance
     * @since 1.2
     */
    private FileType createFileType(String description, String extension, boolean imageFile, boolean bundledFile,
        int sort) {
        FileType fileType = new FileType();

        fileType.setBundledFile(true);
        fileType.setCreationTimestamp(new Date());
        fileType.setCreationUser("user");
        fileType.setDescription(description);
        fileType.setExtension(extension);
        fileType.setImageFile(true);
        fileType.setModificationTimestamp(new Date());
        fileType.setModificationUser("root");
        fileType.setSort(sort);

        return fileType;
    }

    /**
     * Creates prize instance according to the given fields values.
     *
     * @param place
     *            the place field value to set
     * @param prizeAmount
     *            the prizeAmount field value to set
     * @param numberOfSubmissions
     *            the numberOfSubmissions field value to set
     * @return the created instance
     * @since 1.2
     */
    private Prize createPrize(int place, double prizeAmount, int numberOfSubmissions) {
        Prize prize = new Prize();
        prize.setPlace(place);
        prize.setPrizeAmount(prizeAmount);
        prize.setNumberOfSubmissions(numberOfSubmissions);
        PrizeType prizeType = new PrizeType();
        prizeType.setId(1);
        prizeType.setDescription("Component Design Prize");
        prize.setPrizeType(prizeType);
        return prize;
    }

    /**
     * Creates project studio specification instance according to the given fields values.
     *
     * @param goals
     *            the goals field value to set
     * @param targetAudience
     *            the targetAudience field value to set
     * @param roundOneIntroduction
     *            the roundOneIntroduction field value to set
     * @return the created instance
     * @since 1.2
     */
    private ProjectStudioSpecification createProjectStudioSpecification(String goals, String targetAudience,
        String roundOneIntroduction) {
        ProjectStudioSpecification spec = new ProjectStudioSpecification();
        spec.setBrandingGuidelines("brandingGuidelines");
        spec.setColors("colors");
        spec.setDislikedDesignWebSites("dislikedDesignWebSites");
        spec.setFonts("fonts");
        spec.setLayoutAndSize("layoutAndSize");
        spec.setOtherInstructions("otherInstructions");
        spec.setRoundOneIntroduction(roundOneIntroduction);
        spec.setRoundTwoIntroduction("roundTwoIntroduction");
        spec.setSubmittersLockedBetweenRounds(true);
        spec.setTargetAudience(targetAudience);
        spec.setWinningCriteria("winningCriteria");
        spec.setGoals(goals);
        return spec;
    }

    /**
     * Get a sample Project object to test, with project id = 0, project category = .Net, project type = Topcoder,
     * project status = Active.
     *
     * @return a sample Project object
     * @since 1.2
     */
    private Project getSampleProject1() {
        // create a ProjectStatus object
        ProjectStatus status = new ProjectStatus(1, "Active");

        // create a ProjectType object
        ProjectType type = new ProjectType(1, "Topcoder");

        // create a ProjectCategory object
        ProjectCategory category = new ProjectCategory(1, ".Net", type);

        // create the sample project object
        Project project = new Project(category, status);

        // set the properties
        project.setProperty("property 1", "value 1");
        project.setProperty("property 2", "value 2");

        Prize prize = createPrize(1, 600.00, 5);
        project.setPrizes(Arrays.asList(new Prize[]{prize}));

        FileType fileType = createFileType("description 1", "extension 1", true, false, 1);

        project.setProjectFileTypes(Arrays.asList(new FileType[]{fileType}));
        project.setProjectStudioSpecification(createProjectStudioSpecification("goal1", "targetAudience",
            "roundOneIntroduction1"));

        return project;
    }
}
