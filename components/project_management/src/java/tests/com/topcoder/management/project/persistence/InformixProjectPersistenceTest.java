/*
 * Copyright (C) 2007 - 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project.persistence;

import java.sql.Connection;
import java.util.Arrays;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.management.project.ConfigurationException;
import com.topcoder.management.project.FileType;
import com.topcoder.management.project.PersistenceException;
import com.topcoder.management.project.Prize;
import com.topcoder.management.project.Project;
import com.topcoder.management.project.ProjectCategory;
import com.topcoder.management.project.ProjectPersistence;
import com.topcoder.management.project.ProjectPropertyType;
import com.topcoder.management.project.ProjectStatus;
import com.topcoder.management.project.ProjectType;
import com.topcoder.management.project.persistence.Helper.DataType;

/**
 * Accuracy and failure tests for <code>InformixProjectPersistence</code>.
 *
 * @author urtks, fuyun, TCSDEVELOPER
 * @version 1.2
 * @since 1.0
 */
public class InformixProjectPersistenceTest extends TestCase {
    /**
     * Represents the <code>InformixProjectPersistence</code> instance which is used to test the functionality of
     * <code>AbstractInformixProjectPersistence</code>.
     *
     * @since 1.2
     */
    private AbstractInformixProjectPersistence persistence;

    /**
     * Aggregates all tests in this class.
     *
     * @return test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(InformixProjectPersistenceTest.class);
    }

    /**
     * Sets up the test environment. The configuration will be loaded.
     *
     * @throws Exception
     *             throw any exception to JUnit
     * @since 1.0
     */
    protected void setUp() throws Exception {
        TestHelper.prepareConfig();

        persistence = new InformixProjectPersistence("InformixProjectPersistence.CustomNamespace");

        TestHelper.prepareData(persistence);
    }

    /**
     * Clean up the test environment. The configuration will be unloaded.
     *
     * @throws Exception
     *             throw any exception to JUnit
     * @since 1.0
     */
    protected void tearDown() throws Exception {
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
    public void testAccuracyConstructor1() throws Exception {
        ProjectPersistence persistence = new InformixProjectPersistence("InformixProjectPersistence.CustomNamespace");

        assertNotNull("Unable to create InformixProjectPersistence.", persistence);
    }

    /**
     * <p>
     * Accuracy test of the constructor <code>InformixProjectPersistence(String namespace)</code>.
     * </p>
     * <p>
     * An instance of InformixProjectPersistence can be created. ConnectionName property is not provided.
     * </p>
     *
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyConstructor2() throws Exception {
        ProjectPersistence persistence = new InformixProjectPersistence(
            "InformixProjectPersistence.CustomNamespace.NoConnectionName");

        assertNotNull("Unable to create InformixProjectPersistence.", persistence);
    }

    /**
     * <p>
     * Accuracy test of the constructor <code>InformixProjectPersistence(String namespace)</code>.
     * </p>
     * <p>
     * An instance of InformixProjectPersistence can be created. ProjectIdSequence property is not provided.
     * </p>
     *
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyConstructor3() throws Exception {
        ProjectPersistence persistence = new InformixProjectPersistence(
            "InformixProjectPersistence.CustomNamespace.NoProjectIdSequenceName");

        assertNotNull("Unable to create InformixProjectPersistence.", persistence);
    }

    /**
     * <p>
     * Accuracy test of the constructor <code>InformixProjectPersistence(String namespace)</code>.
     * </p>
     * <p>
     * An instance of InformixProjectPersistence can be created. ProjectAuditIdSequenceName property is not provided.
     * </p>
     *
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyConstructor4() throws Exception {
        ProjectPersistence persistence = new InformixProjectPersistence(
            "InformixProjectPersistence.CustomNamespace.NoProjectIdSequenceName");

        assertNotNull("Unable to create InformixProjectPersistence.", persistence);
    }

    /**
     * <p>
     * Failure test of the constructor <code>InformixProjectPersistence(String namespace)</code>.
     * </p>
     * <p>
     * namespace is null. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureConstructor1() throws Exception {
        try {
            new InformixProjectPersistence(null);
        } catch (IllegalArgumentException e) {
            assertEquals("namespace should not be null.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the constructor <code>InformixReviewPersistence(String namespace)</code>.
     * </p>
     * <p>
     * namespace is empty (trimmed). IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureConstructor2() throws Exception {
        try {
            new InformixProjectPersistence("    ");
        } catch (IllegalArgumentException e) {
            assertEquals("namespace should not be empty (trimmed).", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the constructor <code>InformixProjectPersistence(String namespace)</code>.
     * </p>
     * <p>
     * namespace doesn't exist. ConfigurationException is expected.
     * </p>
     *
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureConstructor3() throws Exception {
        try {
            new InformixProjectPersistence("do_not_exist");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Failure test of the constructor <code>InformixProjectPersistence(String namespace)</code>.
     * </p>
     * <p>
     * ConnectionFactoryNS is not provided. ConfigurationException is expected.
     * </p>
     *
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureConstructor4() throws Exception {
        try {
            new InformixProjectPersistence("InformixProjectPersistence.CustomNamespace.NoFactoryNamespace");
        } catch (ConfigurationException e) {
            assertEquals("Configuration parameter [ConnectionFactoryNS] under"
                + " namespace [InformixProjectPersistence.CustomNamespace." + "NoFactoryNamespace] is not specified.",
                e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the constructor <code>InformixProjectPersistence(String namespace)</code>.
     * </p>
     * <p>
     * ConnectionFactoryNS is empty (trimmed). ConfigurationException is expected.
     * </p>
     *
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureConstructor5() throws Exception {
        try {
            new InformixProjectPersistence("InformixProjectPersistence.CustomNamespace.EmptyFactoryNamespace");
        } catch (ConfigurationException e) {
            assertEquals("Configuration parameter [ConnectionFactoryNS] under"
                + " namespace [InformixProjectPersistence.CustomNamespace."
                + "EmptyFactoryNamespace] is empty (trimmed).", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the constructor <code>InformixProjectPersistence(String namespace)</code>.
     * </p>
     * <p>
     * the namespace specified by ConnectionFactoryNS doesn't exist. ConfigurationException is expected.
     * </p>
     *
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureConstructor6() throws Exception {
        try {
            new InformixProjectPersistence("InformixProjectPersistence.CustomNamespace.NonExistFactoryNamespace");
        } catch (ConfigurationException e) {
            // good

        }
    }

    /**
     * <p>
     * Failure test of the constructor <code>InformixProjectPersistence(String namespace)</code>.
     * </p>
     * <p>
     * ConnectionName is empty (trimmed). ConfigurationException is expected.
     * </p>
     *
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureConstructor7() throws Exception {
        try {
            new InformixProjectPersistence("InformixProjectPersistence.CustomNamespace.EmptyConnectionName");
        } catch (ConfigurationException e) {
            assertEquals("Configuration parameter [ConnectionName] under namespace "
                + "[InformixProjectPersistence.CustomNamespace.EmptyConnectionName] " + "is empty (trimmed).", e
                .getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the constructor <code>InformixProjectPersistence(String namespace)</code>.
     * </p>
     * <p>
     * ProjectIdSequenceName is empty (trimmed). ConfigurationException is expected.
     * </p>
     *
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureConstructor8() throws Exception {
        try {
            new InformixProjectPersistence("InformixProjectPersistence.CustomNamespace.EmptyProjectIdSequenceName");
        } catch (ConfigurationException e) {
            assertEquals("Configuration parameter [ProjectIdSequenceName] under namespace "
                + "[InformixProjectPersistence.CustomNamespace.EmptyProjectIdSequenceName] " + "is empty (trimmed).", e
                .getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the constructor <code>InformixProjectPersistence(String namespace)</code>.
     * </p>
     * <p>
     * the id sequence name provided by ProjectIdSequenceName does not exist. PersistenceException is expected.
     * </p>
     *
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureConstructor9() throws Exception {
        try {
            new InformixProjectPersistence("InformixProjectPersistence.CustomNamespace.NonExistProjectIdSequenceName");
        } catch (PersistenceException e) {
            // good

        }
    }

    /**
     * <p>
     * Failure test of the constructor <code>InformixProjectPersistence(String namespace)</code>.
     * </p>
     * <p>
     * ProjectAuditIdSequenceName is empty (trimmed). ConfigurationException is expected.
     * </p>
     *
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureConstructor10() throws Exception {
        try {
            new InformixProjectPersistence("InformixProjectPersistence.CustomNamespace.EmptyProjectAuditIdSequenceName");
        } catch (ConfigurationException e) {
            assertEquals("Configuration parameter [ProjectAuditIdSequenceName] under namespace "
                + "[InformixProjectPersistence.CustomNamespace.EmptyProjectAuditIdSequenceName] "
                + "is empty (trimmed).", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the constructor <code>InformixProjectPersistence(String namespace)</code>.
     * </p>
     * <p>
     * the id sequence name provided by ProjectAuditIdSequenceName does not exist. PersistenceException is expected.
     * </p>
     *
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureConstructor11() throws Exception {
        try {
            new InformixProjectPersistence(
                "InformixProjectPersistence.CustomNamespace.NonExistProjectAuditIdSequenceName");
        } catch (PersistenceException e) {
            // good

        }
    }

    /**
     * <p>
     * Accuracy test of the method <code>getAllProjectPropertyTypes()</code>.
     * </p>
     * <p>
     * Check if all the properties are get correctly.
     * </p>
     *
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyGetAllProjectPropertyTypes1() throws Exception {
        ProjectPersistence persistence = new InformixProjectPersistence("InformixProjectPersistence.CustomNamespace");

        ProjectPropertyType[] propertyTypes = persistence.getAllProjectPropertyTypes();

        assertEquals("check # of property types", 4, propertyTypes.length);
        assertEquals("check property id", 1, propertyTypes[0].getId());
        assertEquals("check property name", "property 1", propertyTypes[0].getName());

        assertEquals("check property id", 2, propertyTypes[1].getId());
        assertEquals("check property name", "property 2", propertyTypes[1].getName());

        assertEquals("check property id", 3, propertyTypes[2].getId());
        assertEquals("check property name", "property 3", propertyTypes[2].getName());

        assertEquals("check property id", 4, propertyTypes[3].getId());
        assertEquals("check property name", "property 4", propertyTypes[3].getName());
    }

    /**
     * <p>
     * Failure test of the method <code>getAllProjectPropertyTypes()</code>.
     * </p>
     * <p>
     * the connection name is not correct. PersistenceException is expected.
     * </p>
     *
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureGetAllProjectPropertyTypes1() throws Exception {
        ProjectPersistence persistence = new InformixProjectPersistence(
            "InformixProjectPersistence.CustomNamespace.NonExistConnectionName");

        try {
            persistence.getAllProjectPropertyTypes();
        } catch (PersistenceException e) {
            // good

        }
    }

    /**
     * <p>
     * Accuracy test of the method <code>getAllProjectStatuses()</code>.
     * </p>
     * <p>
     * Check if all the statuses are get correctly.
     * </p>
     *
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyGetAllProjectStatuses1() throws Exception {
        ProjectPersistence persistence = new InformixProjectPersistence("InformixProjectPersistence.CustomNamespace");

        ProjectStatus[] statuses = persistence.getAllProjectStatuses();

        assertEquals("check # of project statuses", 7, statuses.length);
        assertEquals("check status id", 1, statuses[0].getId());
        assertEquals("check status name", "Active", statuses[0].getName());

        assertEquals("check status id", 2, statuses[1].getId());
        assertEquals("check status name", "Inactive", statuses[1].getName());

        assertEquals("check status id", 3, statuses[2].getId());
        assertEquals("check status name", "Deleted", statuses[2].getName());

        assertEquals("check status id", 4, statuses[3].getId());
        assertEquals("check status name", "Cancelled - Failed Review", statuses[3].getName());

        assertEquals("check status id", 5, statuses[4].getId());
        assertEquals("check status name", "Cancelled - Failed Screening", statuses[4].getName());

        assertEquals("check status id", 6, statuses[5].getId());
        assertEquals("check status name", "Cancelled - Zero Submissions", statuses[5].getName());

        assertEquals("check status id", 7, statuses[6].getId());
        assertEquals("check status name", "Completed", statuses[6].getName());
    }

    /**
     * <p>
     * Failure test of the method <code>getAllProjectStatuses()</code>.
     * </p>
     * <p>
     * the connection name is not correct. PersistenceException is expected.
     * </p>
     *
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureGetAllProjectStatuses1() throws Exception {
        ProjectPersistence persistence = new InformixProjectPersistence(
            "InformixProjectPersistence.CustomNamespace.NonExistConnectionName");

        try {
            persistence.getAllProjectStatuses();
        } catch (PersistenceException e) {
            // good

        }
    }

    /**
     * <p>
     * Accuracy test of the method <code>getAllProjectTypes()</code>.
     * </p>
     * <p>
     * Check if all the project types are get correctly.
     * </p>
     *
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyGetAllProjectTypes1() throws Exception {
        ProjectPersistence persistence = new InformixProjectPersistence("InformixProjectPersistence.CustomNamespace");

        ProjectType[] types = persistence.getAllProjectTypes();

        assertEquals("check # of project types", 2, types.length);
        assertEquals("check type id", 1, types[0].getId());
        assertEquals("check type name", "Topcoder", types[0].getName());

        assertEquals("check type id", 2, types[1].getId());
        assertEquals("check type name", "Customer", types[1].getName());
    }

    /**
     * <p>
     * Failure test of the method <code>getAllProjectTypes()</code>.
     * </p>
     * <p>
     * the connection name is not correct. PersistenceException is expected.
     * </p>
     *
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureGetAllProjectTypes1() throws Exception {
        ProjectPersistence persistence = new InformixProjectPersistence(
            "InformixProjectPersistence.CustomNamespace.NonExistConnectionName");

        try {
            persistence.getAllProjectTypes();
        } catch (PersistenceException e) {
            // good

        }
    }

    /**
     * <p>
     * Accuracy test of the method <code>getAllProjectCategories()</code>.
     * </p>
     * <p>
     * Check if all the project categories are get correctly.
     * </p>
     *
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyGetAllProjectCategories1() throws Exception {
        ProjectPersistence persistence = new InformixProjectPersistence("InformixProjectPersistence.CustomNamespace");

        ProjectCategory[] categories = persistence.getAllProjectCategories();

        assertEquals("check # of project categories", 4, categories.length);
        assertEquals("check category id", 1, categories[0].getId());
        assertEquals("check category name", ".Net", categories[0].getName());
        assertEquals("check type id", 1, categories[0].getProjectType().getId());
        assertEquals("check type name", "Topcoder", categories[0].getProjectType().getName());

        assertEquals("check category id", 2, categories[1].getId());
        assertEquals("check category name", "Java", categories[1].getName());
        assertEquals("check type id", 1, categories[1].getProjectType().getId());
        assertEquals("check type name", "Topcoder", categories[1].getProjectType().getName());

        assertEquals("check category id", 3, categories[2].getId());
        assertEquals("check category name", "Customer .Net", categories[2].getName());
        assertEquals("check type id", 2, categories[2].getProjectType().getId());
        assertEquals("check type name", "Customer", categories[2].getProjectType().getName());

        assertEquals("check category id", 4, categories[3].getId());
        assertEquals("check category name", "Customer Java", categories[3].getName());
        assertEquals("check type id", 2, categories[3].getProjectType().getId());
        assertEquals("check type name", "Customer", categories[3].getProjectType().getName());
    }

    /**
     * <p>
     * Failure test of the method <code>getAllProjectCategories()</code>.
     * </p>
     * <p>
     * the connection name is not correct. PersistenceException is expected.
     * </p>
     *
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureGetAllProjectCategories1() throws Exception {
        ProjectPersistence persistence = new InformixProjectPersistence(
            "InformixProjectPersistence.CustomNamespace.NonExistConnectionName");

        try {
            persistence.getAllProjectCategories();
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * <p>
     * Failure test of the method <code>createProject(Project project, String operator)</code>.
     * </p>
     * <p>
     * project is null. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureCreateProject1() throws Exception {
        ProjectPersistence persistence = new InformixProjectPersistence("InformixProjectPersistence.CustomNamespace");

        try {
            persistence.createProject(null, "user");
        } catch (IllegalArgumentException e) {
            assertEquals("project should not be null.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method <code>createProject(Project project, String operator)</code>.
     * </p>
     * <p>
     * operator is null. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureCreateProject2() throws Exception {
        ProjectPersistence persistence = new InformixProjectPersistence("InformixProjectPersistence.CustomNamespace");

        Project project = TestHelper.getSampleProject1();

        try {
            persistence.createProject(project, null);
        } catch (IllegalArgumentException e) {
            assertEquals("operator should not be null.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method <code>createProject(Project project, String operator)</code>.
     * </p>
     * <p>
     * operator is empty. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureCreateProject3() throws Exception {
        ProjectPersistence persistence = new InformixProjectPersistence("InformixProjectPersistence.CustomNamespace");

        Project project = TestHelper.getSampleProject1();

        try {
            persistence.createProject(project, "    ");
        } catch (IllegalArgumentException e) {
            assertEquals("operator should not be empty (trimmed).", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method <code>createProject(Project project, String operator)</code>.
     * </p>
     * <p>
     * unable to create the connection. PersistenceException is expected.
     * </p>
     *
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureCreateProject4() throws Exception {
        ProjectPersistence persistence = new InformixProjectPersistence(
            "InformixProjectPersistence.CustomNamespace.NonExistConnectionName");

        Project project = TestHelper.getSampleProject1();
        try {
            persistence.createProject(project, "user");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * <p>
     * Failure test of the method <code>createProject(Project project, String operator)</code>.
     * </p>
     * <p>
     * project id already exists. PersistenceException is excepted.
     * </p>
     *
     * @throws Exception
     *             throw any exception to JUnit
     */
    @SuppressWarnings("deprecation")
    public void testFailureCreateProject5() throws Exception {
        ProjectPersistence persistence = new InformixProjectPersistence("InformixProjectPersistence.CustomNamespace");

        Project project = TestHelper.getSampleProject1();
        project.setId(1);

        // create the connection
        Connection conn = new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName()).createConnection();
        Helper.doDMLQuery(conn, "INSERT INTO project " + "(project_id, project_status_id, project_category_id, "
            + "create_user, create_date, modify_user, modify_date) "
            + "VALUES (1, 1, 1, 'topcoder', CURRENT, 'topcoder', CURRENT)", new Object[]{});
        conn.close();

        try {
            persistence.createProject(project, "user");
        } catch (PersistenceException e) {
            assertEquals("The project with the same id [1] already exists.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method <code>createProject(Project project, String operator)</code>.
     * </p>
     * <p>
     * duplicate project info type names. PersistenceException is expected.
     * </p>
     *
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureCreateProject7() throws Exception {
        ProjectPersistence persistence = new InformixProjectPersistence("InformixProjectPersistence.CustomNamespace");

        // create the connection
        Connection conn = new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName()).createConnection();
        Helper.doDMLQuery(conn, "INSERT INTO project_info_type_lu (project_info_type_id, "
            + "name, description, create_user, create_date, modify_user, modify_date) "
            + "VALUES (5, 'property 1', 'project property 1', 'topcoder', CURRENT," + "'topcoder', CURRENT)",
            new Object[]{});
        conn.close();

        Project project = TestHelper.getSampleProject1();

        try {
            persistence.createProject(project, "user1");

        } catch (PersistenceException e) {
            assertEquals(
                "Duplicate project property type names [property 1] found " + "in project_info_type_lu table.", e
                    .getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method <code>createProject(Project project, String operator)</code>.
     * </p>
     * <p>
     * project status id does not exist. PersistenceException is expected.
     * </p>
     *
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureCreateProject8() throws Exception {
        ProjectPersistence persistence = new InformixProjectPersistence("InformixProjectPersistence.CustomNamespace");

        Project project = TestHelper.getSampleProject1();
        project.getProjectStatus().setId(100);

        try {
            persistence.createProject(project, "user1");
        } catch (PersistenceException e) {
            // correct
        }
    }

    /**
     * <p>
     * Failure test of the method <code>createProject(Project project, String operator)</code>.
     * </p>
     * <p>
     * project category id does not exist. PersistenceException is expected.
     * </p>
     *
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureCreateProject9() throws Exception {
        ProjectPersistence persistence = new InformixProjectPersistence("InformixProjectPersistence.CustomNamespace");

        Project project = TestHelper.getSampleProject1();
        project.getProjectCategory().setId(100);

        try {
            persistence.createProject(project, "user1");
        } catch (PersistenceException e) {
            // correct
        }
    }

    /**
     * <p>
     * Failure test of the method <code>createProject(Project project, String operator)</code>.
     * </p>
     * <p>
     * project type id does not exist. PersistenceException is expected.
     * </p>
     *
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureCreateProject10() throws Exception {
        ProjectPersistence persistence = new InformixProjectPersistence("InformixProjectPersistence.CustomNamespace");

        Project project = TestHelper.getSampleProject1();
        project.getProjectCategory().getProjectType().setId(100);

        try {
            persistence.createProject(project, "user1");
        } catch (PersistenceException e) {
            // correct
        }
    }

    /**
     * <p>
     * Accuracy test of the method <code>getProject(long id)</code>.
     * </p>
     * <p>
     * Check if the project is get from the database correctly.
     * </p>
     *
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyGetProject1() throws Exception {
        ProjectPersistence persistence = new InformixProjectPersistence("InformixProjectPersistence.CustomNamespace");

        // get the sample project object
        Project project1 = TestHelper.getSampleProject1();
        Project project2 = getSampleProject2();

        // first create the project
        persistence.createProject(project1, "user");
        persistence.createProject(project2, "user");

        // get the project
        Project project3 = persistence.getProject(project2.getId());

        // first check if the project's properties are correct.
        assertEquals("check project id", project2.getId(), project3.getId());
        assertEquals("check project creation user", project2.getCreationUser(), project3.getCreationUser());
        assertEquals("check creation date", project2.getCreationTimestamp(), project3.getCreationTimestamp());
        assertEquals("check project modification user", project2.getModificationUser(), project3.getModificationUser());
        assertEquals("check modification date", project2.getModificationTimestamp(), project3
            .getModificationTimestamp());

        assertEquals("check project status id", project2.getProjectStatus().getId(), project3.getProjectStatus()
            .getId());
        assertEquals("check project status name", project2.getProjectStatus().getName(), project3.getProjectStatus()
            .getName());

        assertEquals("check project category id", project2.getProjectCategory().getId(), project3.getProjectCategory()
            .getId());
        assertEquals("check project category id", project2.getProjectCategory().getName(), project3
            .getProjectCategory().getName());

        assertEquals("check project type id", project2.getProjectCategory().getProjectType().getId(), project3
            .getProjectCategory().getProjectType().getId());
        assertEquals("check project type id", project2.getProjectCategory().getProjectType().getName(), project3
            .getProjectCategory().getProjectType().getName());

        assertEquals("check project properties", project2.getAllProperties(), project3.getAllProperties());
    }

    /**
     * <p>
     * Accuracy test of the method <code>getProject(long id)</code>.
     * </p>
     * <p>
     * Check if the project is get from the database correctly. No project is found.
     * </p>
     *
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyGetProject2() throws Exception {
        ProjectPersistence persistence = new InformixProjectPersistence("InformixProjectPersistence.CustomNamespace");

        Project project = persistence.getProject(1);

        // check if no project is found
        assertNull("check project object", project);
    }

    /**
     * <p>
     * Failure test of the method <code>getProject(long id)</code>.
     * </p>
     * <p>
     * id is negative. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureGetProject1() throws Exception {
        ProjectPersistence persistence = new InformixProjectPersistence("InformixProjectPersistence.CustomNamespace");

        try {
            persistence.getProject(-1);
        } catch (IllegalArgumentException e) {
            assertEquals("id [-1] should be positive.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method <code>getProject(long id)</code>.
     * </p>
     * <p>
     * id is zero. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureGetProject2() throws Exception {
        ProjectPersistence persistence = new InformixProjectPersistence("InformixProjectPersistence.CustomNamespace");

        try {
            persistence.getProject(0);
        } catch (IllegalArgumentException e) {
            assertEquals("id [0] should be positive.", e.getMessage());
        }
    }

    /**
     * <p>
     * Accuracy test of the method <code>getProjects(long[] ids)</code>.
     * </p>
     * <p>
     * Check if the project is get from the database correctly.
     * </p>
     *
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyGetProjects1() throws Exception {
        ProjectPersistence persistence = new InformixProjectPersistence("InformixProjectPersistence.CustomNamespace");

        // get the sample project object
        Project project1 = TestHelper.getSampleProject1();
        Project project2 = getSampleProject2();

        // first create the project
        persistence.createProject(project1, "user");
        persistence.createProject(project2, "user");

        // get the project
        Project[] projects = persistence.getProjects(new long[]{project1.getId(), project2.getId()});

        // first check if the project's properties are correct.
        assertEquals("check project id", project2.getId(), projects[1].getId());
        assertEquals("check project creation user", project2.getCreationUser(), projects[1].getCreationUser());
        assertEquals("check creation date", project2.getCreationTimestamp(), projects[1].getCreationTimestamp());
        assertEquals("check project modification user", project2.getModificationUser(), projects[1]
            .getModificationUser());
        assertEquals("check modification date", project2.getModificationTimestamp(), projects[1]
            .getModificationTimestamp());

        assertEquals("check project status id", project2.getProjectStatus().getId(), projects[1].getProjectStatus()
            .getId());
        assertEquals("check project status name", project2.getProjectStatus().getName(), projects[1].getProjectStatus()
            .getName());

        assertEquals("check project category id", project2.getProjectCategory().getId(), projects[1]
            .getProjectCategory().getId());
        assertEquals("check project category id", project2.getProjectCategory().getName(), projects[1]
            .getProjectCategory().getName());

        assertEquals("check project type id", project2.getProjectCategory().getProjectType().getId(), projects[1]
            .getProjectCategory().getProjectType().getId());
        assertEquals("check project type id", project2.getProjectCategory().getProjectType().getName(), projects[1]
            .getProjectCategory().getProjectType().getName());

        assertEquals("check project properties", project2.getAllProperties(), projects[1].getAllProperties());
    }

    /**
     * <p>
     * Accuracy test of the method <code>getProjects(long[] ids)</code>.
     * </p>
     * <p>
     * Check if the projects are get from the database correctly. ids is not empty, but no projects are found
     * </p>
     *
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyGetProjects3() throws Exception {
        ProjectPersistence persistence = new InformixProjectPersistence("InformixProjectPersistence.CustomNamespace");

        Project[] projects = persistence.getProjects(new long[]{1, 2, 3});

        // check if # of projects is zero
        assertEquals("check # of projects", 0, projects.length);
    }

    /**
     * <p>
     * Accuracy test of the method <code>getProjects(long[] ids)</code>.
     * </p>
     * <p>
     * Check if the project is get from the database correctly. Some of the ids are not found.
     * </p>
     *
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyGetProjects4() throws Exception {
        ProjectPersistence persistence = new InformixProjectPersistence("InformixProjectPersistence.CustomNamespace");

        // get the sample project object
        Project project1 = TestHelper.getSampleProject1();
        Project project2 = getSampleProject2();

        // first create the project
        persistence.createProject(project1, "user");
        persistence.createProject(project2, "user");

        // get the project
        Project[] projects = persistence.getProjects(new long[]{project1.getId(), project2.getId(), 3, 4, 5, 6, 7});

        // first check if the project's properties are correct.
        assertEquals("check project id", project2.getId(), projects[1].getId());
        assertEquals("check project creation user", project2.getCreationUser(), projects[1].getCreationUser());
        assertEquals("check creation date", project2.getCreationTimestamp(), projects[1].getCreationTimestamp());
        assertEquals("check project modification user", project2.getModificationUser(), projects[1]
            .getModificationUser());
        assertEquals("check modification date", project2.getModificationTimestamp(), projects[1]
            .getModificationTimestamp());

        assertEquals("check project status id", project2.getProjectStatus().getId(), projects[1].getProjectStatus()
            .getId());
        assertEquals("check project status name", project2.getProjectStatus().getName(), projects[1].getProjectStatus()
            .getName());

        assertEquals("check project category id", project2.getProjectCategory().getId(), projects[1]
            .getProjectCategory().getId());
        assertEquals("check project category id", project2.getProjectCategory().getName(), projects[1]
            .getProjectCategory().getName());

        assertEquals("check project type id", project2.getProjectCategory().getProjectType().getId(), projects[1]
            .getProjectCategory().getProjectType().getId());
        assertEquals("check project type id", project2.getProjectCategory().getProjectType().getName(), projects[1]
            .getProjectCategory().getProjectType().getName());

        assertEquals("check project properties", project2.getAllProperties(), projects[1].getAllProperties());
    }

    /**
     * <p>
     * Accuracy test of the method <code>getProjects(long[] ids)</code>.
     * </p>
     * <p>
     * Check if the project is get from the database correctly. Duplicate ids in ids array.
     * </p>
     *
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracyGetProjects5() throws Exception {
        ProjectPersistence persistence = new InformixProjectPersistence("InformixProjectPersistence.CustomNamespace");

        // get the sample project object
        Project project1 = TestHelper.getSampleProject1();
        Project project2 = getSampleProject2();

        // first create the project
        persistence.createProject(project1, "user");
        persistence.createProject(project2, "user");

        // get the project
        Project[] projects = persistence.getProjects(new long[]{project1.getId(), project1.getId(), project2.getId(),
            project2.getId()});

        // first check if the project's properties are correct.
        assertEquals("check project id", project2.getId(), projects[1].getId());
        assertEquals("check project creation user", project2.getCreationUser(), projects[1].getCreationUser());
        assertEquals("check creation date", project2.getCreationTimestamp(), projects[1].getCreationTimestamp());
        assertEquals("check project modification user", project2.getModificationUser(), projects[1]
            .getModificationUser());
        assertEquals("check modification date", project2.getModificationTimestamp(), projects[1]
            .getModificationTimestamp());

        assertEquals("check project status id", project2.getProjectStatus().getId(), projects[1].getProjectStatus()
            .getId());
        assertEquals("check project status name", project2.getProjectStatus().getName(), projects[1].getProjectStatus()
            .getName());

        assertEquals("check project category id", project2.getProjectCategory().getId(), projects[1]
            .getProjectCategory().getId());
        assertEquals("check project category id", project2.getProjectCategory().getName(), projects[1]
            .getProjectCategory().getName());

        assertEquals("check project type id", project2.getProjectCategory().getProjectType().getId(), projects[1]
            .getProjectCategory().getProjectType().getId());
        assertEquals("check project type id", project2.getProjectCategory().getProjectType().getName(), projects[1]
            .getProjectCategory().getProjectType().getName());

        assertEquals("check project properties", project2.getAllProperties(), projects[1].getAllProperties());
    }

    /**
     * <p>
     * Failure test of the method <code>getProjects(long[] ids)</code>.
     * </p>
     * <p>
     * ids is null. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureGetProjects1() throws Exception {
        ProjectPersistence persistence = new InformixProjectPersistence("InformixProjectPersistence.CustomNamespace");

        try {
            persistence.getProjects((long[]) null);
        } catch (IllegalArgumentException e) {
            assertEquals("ids should not be null.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method <code>getProjects(long[] ids)</code>.
     * </p>
     * <p>
     * ids contains -1. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureGetProjects2() throws Exception {
        ProjectPersistence persistence = new InformixProjectPersistence("InformixProjectPersistence.CustomNamespace");

        long[] ids = new long[]{1, 2, 3, -1};
        try {
            persistence.getProjects(ids);
        } catch (IllegalArgumentException e) {
            assertEquals("Array 'ids' contains an id that is <= 0.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method <code>getProjects(long[] ids)</code>.
     * </p>
     * <p>
     * ids contains 0. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureGetProjects3() throws Exception {
        ProjectPersistence persistence = new InformixProjectPersistence("InformixProjectPersistence.CustomNamespace");

        long[] ids = new long[]{1, 2, 3, 0};
        try {
            persistence.getProjects(ids);
        } catch (IllegalArgumentException e) {
            assertEquals("Array 'ids' contains an id that is <= 0.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method <code>getProjects(long[] ids)</code>.
     * </p>
     * <p>
     * ids is an empty array. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureGetProjects4() throws Exception {
        ProjectPersistence persistence = new InformixProjectPersistence("InformixProjectPersistence.CustomNamespace");

        try {
            persistence.getProjects(new long[]{});
        } catch (IllegalArgumentException e) {
            assertEquals("Array 'ids' should not be empty.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method <code>updateProject(Project project, String reason, String operator)</code>.
     * </p>
     * <p>
     * project is null. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureUpdateProjects1() throws Exception {
        ProjectPersistence persistence = new InformixProjectPersistence("InformixProjectPersistence.CustomNamespace");

        try {
            persistence.updateProject(null, "some reason", "user");
        } catch (IllegalArgumentException e) {
            assertEquals("project should not be null.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method <code>updateProject(Project project, String reason, String operator)</code>.
     * </p>
     * <p>
     * project id is 0. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureUpdateProjects2() throws Exception {
        ProjectPersistence persistence = new InformixProjectPersistence("InformixProjectPersistence.CustomNamespace");

        Project project = TestHelper.getSampleProject1();

        try {
            persistence.updateProject(project, "some reason", "user");
        } catch (IllegalArgumentException e) {
            assertEquals("project id [0] should be positive.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method <code>updateProject(Project project, String reason, String operator)</code>.
     * </p>
     * <p>
     * reason is null. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureUpdateProjects3() throws Exception {
        ProjectPersistence persistence = new InformixProjectPersistence("InformixProjectPersistence.CustomNamespace");

        Project project = TestHelper.getSampleProject1();
        project.setId(1);

        try {
            persistence.updateProject(project, null, "user");
        } catch (IllegalArgumentException e) {
            assertEquals("reason should not be null.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method <code>updateProject(Project project, String reason, String operator)</code>.
     * </p>
     * <p>
     * operator is null. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureUpdateProjects5() throws Exception {
        ProjectPersistence persistence = new InformixProjectPersistence("InformixProjectPersistence.CustomNamespace");

        Project project = TestHelper.getSampleProject1();
        project.setId(1);

        try {
            persistence.updateProject(project, "some reason", null);
        } catch (IllegalArgumentException e) {
            assertEquals("operator should not be null.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method <code>updateProject(Project project, String reason, String operator)</code>.
     * </p>
     * <p>
     * operator is empty. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureUpdateProjects6() throws Exception {
        ProjectPersistence persistence = new InformixProjectPersistence("InformixProjectPersistence.CustomNamespace");

        Project project = TestHelper.getSampleProject1();
        project.setId(1);

        try {
            persistence.updateProject(project, "some reason", "    ");
        } catch (IllegalArgumentException e) {
            assertEquals("operator should not be empty (trimmed).", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method <code>updateProject(Project project, String reason, String operator)</code>.
     * </p>
     * <p>
     * project id does not exist. PersistenceException is expected.
     * </p>
     *
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureUpdateProjects7() throws Exception {
        ProjectPersistence persistence = new InformixProjectPersistence("InformixProjectPersistence.CustomNamespace");

        Project project = TestHelper.getSampleProject1();
        project.setId(1);

        try {
            persistence.updateProject(project, "some reason", "user");
        } catch (PersistenceException e) {
            assertEquals("The project id [1] does not exist in the database.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method <code>updateProject(Project project, String reason, String operator)</code>.
     * </p>
     * <p>
     * duplicate project info type names. PersistenceException is expected.
     * </p>
     *
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureUpdateProjects8() throws Exception {
        ProjectPersistence persistence = new InformixProjectPersistence("InformixProjectPersistence.CustomNamespace");

        Project project = TestHelper.getSampleProject1();
        persistence.createProject(project, "user1");

        // create the connection
        Connection conn = new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName()).createConnection();
        Helper.doDMLQuery(conn, "INSERT INTO project_info_type_lu (project_info_type_id, "
            + "name, description, create_user, create_date, modify_user, modify_date) "
            + "VALUES (5, 'property 1', 'project property 1', 'topcoder', CURRENT," + "'topcoder', CURRENT)",
            new Object[]{});
        conn.close();

        try {
            persistence.updateProject(project, "some reason", "user");
        } catch (PersistenceException e) {
            assertEquals(
                "Duplicate project property type names [property 1] found " + "in project_info_type_lu table.", e
                    .getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method <code>updateProject(Project project, String reason, String operator)</code>.
     * </p>
     * <p>
     * project info name (property name) not found. PersistenceException is expected.
     * </p>
     *
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureUpdateProjects9() throws Exception {
        ProjectPersistence persistence = new InformixProjectPersistence("InformixProjectPersistence.CustomNamespace");

        Project project = TestHelper.getSampleProject1();
        persistence.createProject(project, "user1");

        project.setProperty("do_not_exist", "dummy");

        try {
            persistence.updateProject(project, "some reason", "user");
        } catch (PersistenceException e) {
            assertEquals("Unable to find ProjectPropertyType name " + "[do_not_exist] in project_info_type_lu table.",
                e.getMessage());
        }
    }

    /**
     * <p>
     * Failure test of the method <code>updateProject(Project project, String reason, String operator)</code>.
     * </p>
     * <p>
     * project status id does not exist. PersistenceException is expected.
     * </p>
     *
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureUpdateProject11() throws Exception {
        ProjectPersistence persistence = new InformixProjectPersistence("InformixProjectPersistence.CustomNamespace");

        Project project = TestHelper.getSampleProject1();
        persistence.createProject(project, "user1");

        project.getProjectStatus().setId(100);
        try {
            persistence.updateProject(project, "some reason", "user1");
        } catch (PersistenceException e) {
            // correct
        }
    }

    /**
     * <p>
     * Failure test of the method <code>updateProject(Project project, String reason, String operator)</code>.
     * </p>
     * <p>
     * project category id does not exist. PersistenceException is expected.
     * </p>
     *
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureUpdateProject12() throws Exception {
        ProjectPersistence persistence = new InformixProjectPersistence("InformixProjectPersistence.CustomNamespace");

        Project project = TestHelper.getSampleProject1();
        persistence.createProject(project, "user1");

        project.getProjectCategory().setId(100);

        try {
            persistence.updateProject(project, "some reason", "user1");
        } catch (PersistenceException e) {
            // correct
        }
    }

    /**
     * <p>
     * Failure test of the method <code>updateProject(Project project, String reason, String operator)</code>.
     * </p>
     * <p>
     * project type id does not exist. PersistenceException is expected.
     * </p>
     *
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureUpdateProject13() throws Exception {
        ProjectPersistence persistence = new InformixProjectPersistence("InformixProjectPersistence.CustomNamespace");

        Project project = TestHelper.getSampleProject1();
        persistence.createProject(project, "user1");

        project.getProjectCategory().getProjectType().setId(100);

        try {
            persistence.updateProject(project, "some reason", "user1");
        } catch (PersistenceException e) {
            // correct
        }
    }

    /**
     * <p>
     * Accuracy test for method <code>updateProject()</code>.
     * </p>
     * <p>
     * Verifies that the transaction will be rolled back if fails to update the project.
     * </p>
     *
     * @throws Exception
     *             if there is any problem.
     * @since 1.1
     */
    public void testUpdateProjectRollback() throws Exception {
        ProjectPersistence persistence = new InformixProjectPersistence("InformixProjectPersistence.CustomNamespace");

        Project project = TestHelper.getSampleProject1();

        persistence.createProject(project, "user");

        // the proerty is not in the table, which will cause the roll back.
        project.setProperty("test", "test");

        try {
            persistence.updateProject(project, "reason", "user");
            fail("PersistenceException is expected.");
        } catch (PersistenceException pe) {
            // success
        }

        Connection conn = null;
        try {
            conn = new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName()).createConnection();
            Object[][] rows = Helper.doQuery(conn, "SELECT * FROM project_info", new Object[]{}, new DataType[]{
                Helper.LONG_TYPE, Helper.LONG_TYPE, Helper.STRING_TYPE, Helper.STRING_TYPE, Helper.DATE_TYPE,
                Helper.STRING_TYPE, Helper.DATE_TYPE});
            assertEquals("Two results should be selected.", 2, rows.length);
        } finally {
            Helper.closeConnection(conn);
        }
    }

    /**
     * Get a sample Project object to test, with project id = 0, project category = Customer Java, project type =
     * Customer, project status = Inactive.
     *
     * @return a sample Project object
     * @since 1.0
     */
    private Project getSampleProject2() {
        // create a ProjectStatus object
        ProjectStatus status = new ProjectStatus(2, "Inactive");

        // create a ProjectType object
        ProjectType type = new ProjectType(2, "Customer");

        // create a ProjectCategory object
        ProjectCategory category = new ProjectCategory(4, "Customer Java", type);

        // create the sample project object
        Project project = new Project(category, status);

        // set the properties
        project.setProperty("property 2", "new value 2");
        project.setProperty("property 3", "value 3");
        project.setProperty("property 4", "value 4");

        Prize prize = TestHelper.createPrize(1, 600.00, 5);
        project.setPrizes(Arrays.asList(new Prize[]{prize}));

        FileType fileType = TestHelper.createFileType("description 1", "extension 1", true, false, 1);

        project.setProjectFileTypes(Arrays.asList(new FileType[]{fileType}));
        project.setProjectStudioSpecification(TestHelper.createProjectStudioSpecification("goal1", "targetAudience",
            "roundOneIntroduction1"));

        project.setTcDirectProjectId(1);

        return project;
    }
}
