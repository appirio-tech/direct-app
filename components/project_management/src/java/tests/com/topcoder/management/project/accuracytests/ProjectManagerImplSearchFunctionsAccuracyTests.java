/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project.accuracytests;

import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

import com.topcoder.management.project.Project;
import com.topcoder.management.project.ProjectFilterUtility;
import com.topcoder.management.project.accuracytests.persistence.MockProjectPersistence;

import com.topcoder.search.builder.filter.Filter;

import java.io.BufferedReader;
import java.io.FileReader;

import java.sql.Connection;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * <p>
 * Accuracy test cases for <code>ProjectManagerImpl</code>'s search functionalities, including searchProject and
 * getUserProjects methods. Projects are retrieved with different filters.
 * </p>
 *
 * <p>
 * Refer to test_files/accuracytest/InitDB.sql for the data used in tests.
 * </p>
 *
 * @author skatou
 * @version 1.0
 */
public class ProjectManagerImplSearchFunctionsAccuracyTests extends ProjectManagerImplAccuracyTestsHelper {
    /** File contains sql statement to initial database. */
    private static final String INIT_DB_SQL = "test_files/accuracytests/InitDB.sql";

    /** File contains sql statement to clear database. */
    private static final String CLEAR_DB_SQL = "test_files/accuracytests/ClearDB.sql";

    /**
     * Sets up the test environment. Writes data necessary for the search into database.
     *
     * @throws Exception pass to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();

        executeSQL(INIT_DB_SQL);
    }

    /**
     * Tests search projects by project type id.
     *
     * @throws Exception pass to JUnit.
     */
    public void testSearchProjectsByProjectTypeId() throws Exception {
        try {
            // search type id 1
            Project[] projects = manager.searchProjects(ProjectFilterUtility.buildTypeIdEqualFilter(1));

            Set ids = new HashSet();

            for (int i = 0; i < projects.length; ++i) {
                ids.add(new Long(projects[i].getId()));
            }

            assertTrue(ids.contains(new Long(1)));
            assertTrue(ids.contains(new Long(2)));
            assertTrue(ids.contains(new Long(3)));
            assertTrue(ids.contains(new Long(5)));

            // search type id 2
            projects = manager.searchProjects(ProjectFilterUtility.buildTypeIdEqualFilter(2));
            assertEquals(4, projects[0].getId());

            // search type id 3
            projects = manager.searchProjects(ProjectFilterUtility.buildTypeIdEqualFilter(3));
            assertEquals(0, projects.length);
        } finally {
            executeSQL(CLEAR_DB_SQL);
        }
    }

    /**
     * Tests search projects by project type name.
     *
     * @throws Exception pass to JUnit.
     */
    public void testSearchProjectsByTypeName() throws Exception {
        try {
            // search type name "More"
            Project[] projects = manager.searchProjects(ProjectFilterUtility.buildTypeNameEqualFilter("More"));
            assertEquals(4, projects.length);

            Set ids = new HashSet();

            for (int i = 0; i < projects.length; ++i) {
                ids.add(new Long(projects[i].getId()));
            }

            assertTrue(ids.contains(new Long(1)));
            assertTrue(ids.contains(new Long(2)));
            assertTrue(ids.contains(new Long(3)));
            assertTrue(ids.contains(new Long(5)));
            assertTrue(MockProjectPersistence.getLastCalled().startsWith("getProjects"));

            // search type name "Less"
            projects = manager.searchProjects(ProjectFilterUtility.buildTypeNameEqualFilter("Less"));
            assertEquals(1, projects.length);
            assertEquals(4, projects[0].getId());

            // search type name "No"
            projects = manager.searchProjects(ProjectFilterUtility.buildTypeNameEqualFilter("No"));
            assertEquals(0, projects.length);
        } finally {
            executeSQL(CLEAR_DB_SQL);
        }
    }

    /**
     * Tests search projects by project category id.
     *
     * @throws Exception pass to JUnit.
     */
    public void testSearchProjectsByCategoryId() throws Exception {
        try {
            // search category id 1
            Project[] projects = manager.searchProjects(ProjectFilterUtility.buildCategoryIdEqualFilter(1));
            assertEquals(2, projects.length);

            Set ids = new HashSet();

            for (int i = 0; i < projects.length; ++i) {
                ids.add(new Long(projects[i].getId()));
            }

            assertTrue(ids.contains(new Long(1)));
            assertTrue(ids.contains(new Long(2)));

            // search category id 2
            projects = manager.searchProjects(ProjectFilterUtility.buildCategoryIdEqualFilter(2));
            assertEquals(1, projects.length);
            assertEquals(3, projects[0].getId());

            // search category id 3
            projects = manager.searchProjects(ProjectFilterUtility.buildCategoryIdEqualFilter(3));
            assertEquals(1, projects.length);
            assertEquals(4, projects[0].getId());

            // search category id 4
            projects = manager.searchProjects(ProjectFilterUtility.buildCategoryIdEqualFilter(4));
            assertEquals(1, projects.length);
            assertEquals(5, projects[0].getId());
        } finally {
            executeSQL(CLEAR_DB_SQL);
        }
    }

    /**
     * Tests search projects by project category name.
     *
     * @throws Exception pass to JUnit.
     */
    public void testSearchProjectsByCategoryName() throws Exception {
        try {
            // search category name "Java"
            Project[] projects = manager.searchProjects(ProjectFilterUtility.buildCategoryNameEqualFilter("Java"));
            assertEquals(3, projects.length);

            Set ids = new HashSet();

            for (int i = 0; i < projects.length; ++i) {
                ids.add(new Long(projects[i].getId()));
            }

            assertTrue(ids.contains(new Long(1)));
            assertTrue(ids.contains(new Long(2)));
            assertTrue(ids.contains(new Long(5)));

            // search category name "DotNet"
            projects = manager.searchProjects(ProjectFilterUtility.buildCategoryNameEqualFilter("DotNet"));
            assertEquals(1, projects.length);
            assertEquals(3, projects[0].getId());

            // search category name "C++"
            projects = manager.searchProjects(ProjectFilterUtility.buildCategoryNameEqualFilter("C++"));
            assertEquals(1, projects.length);
            assertEquals(4, projects[0].getId());
        } finally {
            executeSQL(CLEAR_DB_SQL);
        }
    }

    /**
     * Tests search projects by project status id list.
     *
     * @throws Exception pass to JUnit.
     */
    public void testSearchProjectsByStatusIds() throws Exception {
        try {
            // all completed projects
            List searchIds = new ArrayList();
            searchIds.add(new Long(7));

            Project[] projects = manager.searchProjects(ProjectFilterUtility.buildStatusIdInFilter(searchIds));
            assertEquals(2, projects.length);

            Set ids = new HashSet();

            for (int i = 0; i < projects.length; ++i) {
                ids.add(new Long(projects[i].getId()));
            }

            assertTrue(ids.contains(new Long(1)));
            assertTrue(ids.contains(new Long(3)));

            // all completed and deleted projects
            searchIds.clear();
            searchIds.add(new Long(1));
            searchIds.add(new Long(3));
            projects = manager.searchProjects(ProjectFilterUtility.buildStatusIdInFilter(searchIds));
            assertEquals(3, projects.length);

            ids.clear();

            for (int i = 0; i < projects.length; ++i) {
                ids.add(new Long(projects[i].getId()));
            }

            assertTrue(ids.contains(new Long(2)));
            assertTrue(ids.contains(new Long(4)));
            assertTrue(ids.contains(new Long(5)));
        } finally {
            executeSQL(CLEAR_DB_SQL);
        }
    }

    /**
     * Tests search projects by project property name.
     *
     * @throws Exception pass to JUnit.
     */
    public void testSearchProjectsByPropertyName() throws Exception {
        try {
            Project[] projects = manager.searchProjects(ProjectFilterUtility.buildProjectPropertyNameEqualFilter(
                        "designer"));
            assertEquals(2, projects.length);

            Set ids = new HashSet();

            for (int i = 0; i < projects.length; ++i) {
                ids.add(new Long(projects[i].getId()));
            }

            assertTrue(ids.contains(new Long(1)));
            assertTrue(ids.contains(new Long(2)));
        } finally {
            executeSQL(CLEAR_DB_SQL);
        }
    }

    /**
     * Tests search projects by project property value.
     *
     * @throws Exception pass to JUnit.
     */
    public void testSearchProjectsByPropertyValue() throws Exception {
        try {
            Project[] projects = manager.searchProjects(ProjectFilterUtility.buildProjectPropertyValueEqualFilter(
                        "iamajia"));
            assertEquals(2, projects[0].getId());
        } finally {
            executeSQL(CLEAR_DB_SQL);
        }
    }

    /**
     * Tests search projects by project property.
     *
     * @throws Exception pass to JUnit.
     */
    public void testSearchProjectsByProperty() throws Exception {
        try {
            Project[] projects = manager.searchProjects(ProjectFilterUtility.buildProjectPropertyEqualFilter(
                        "developer", "iamajia"));
            assertEquals(2, projects[0].getId());
        } finally {
            executeSQL(CLEAR_DB_SQL);
        }
    }

    /**
     * Tests search projects by project resource property name.
     *
     * @throws Exception pass to JUnit.
     */
    public void testSearchProjectsByResourcePropertyName()
        throws Exception {
        try {
            Project[] projects = manager.searchProjects(
                ProjectFilterUtility.buildProjectResourcePropertyNameEqualFilter("External Reference ID"));
            assertEquals(2, projects.length);

            Set ids = new HashSet();

            for (int i = 0; i < projects.length; ++i) {
                ids.add(new Long(projects[i].getId()));
            }

            assertTrue(ids.contains(new Long(2)));
            assertTrue(ids.contains(new Long(4)));
        } finally {
            executeSQL(CLEAR_DB_SQL);
        }
    }

    /**
     * Tests search projects by project resource property value.
     *
     * @throws Exception pass to JUnit.
     */
    public void testSearchProjectsByResourcePropertyValue()
        throws Exception {
        try {
            Project[] projects = manager.searchProjects(
                ProjectFilterUtility.buildProjectResourcePropertyValueEqualFilter("administrator"));
            assertEquals(2, projects[0].getId());
        } finally {
            executeSQL(CLEAR_DB_SQL);
        }
    }

    /**
     * Tests search projects by project resource property value list.
     *
     * @throws Exception pass to JUnit.
     */
    public void testSearchProjectsByResourcePropertyValues()
        throws Exception {
        try {
            List values = new ArrayList();
            values.add("999");

            Project[] projects = manager.searchProjects(ProjectFilterUtility.buildProjectResourcePropertyValueInFilter(
                        values));
            assertEquals(4, projects[0].getId());
        } finally {
            executeSQL(CLEAR_DB_SQL);
        }
    }

    /**
     * Tests search projects by project resource property.
     *
     * @throws Exception pass to JUnit.
     */
    public void testSearchProjectsByResourceProperty()
        throws Exception {
        try {
            Project[] projects = manager.searchProjects(ProjectFilterUtility.buildProjectPropertyResourceEqualFilter(
                        "External Reference ID", "999"));
            assertEquals(4, projects[0].getId());
        } finally {
            executeSQL(CLEAR_DB_SQL);
        }
    }

    /**
     * Tests search projects by project status name AND project category name.
     *
     * @throws Exception pass to JUnit.
     */
    public void testSearchProjectsByStatusNameAndCategoryName()
        throws Exception {
        try {
            Filter statusFilter = ProjectFilterUtility.buildStatusNameEqualFilter("Deleted");
            Filter categoryFilter = ProjectFilterUtility.buildCategoryNameEqualFilter("Java");
            Project[] projects = manager.searchProjects(
                ProjectFilterUtility.buildAndFilter(statusFilter, categoryFilter));
            assertEquals(5, projects[0].getId());
        } finally {
            executeSQL(CLEAR_DB_SQL);
        }
    }

    /**
     * Tests search projects by project status name OR project category name.
     *
     * @throws Exception pass to JUnit.
     */
    public void testSearchProjectsByStatusNameOrCategoryName()
        throws Exception {
        try {
            Filter statusFilter = ProjectFilterUtility.buildStatusNameEqualFilter("Deleted");
            Filter categoryFilter = ProjectFilterUtility.buildCategoryNameEqualFilter("DotNet");
            Project[] projects = manager.searchProjects(
                ProjectFilterUtility.buildOrFilter(statusFilter, categoryFilter));
            assertEquals(2, projects.length);

            Set ids = new HashSet();

            for (int i = 0; i < projects.length; ++i) {
                ids.add(new Long(projects[i].getId()));
            }

            assertTrue(ids.contains(new Long(3)));
            assertTrue(ids.contains(new Long(5)));
        } finally {
            executeSQL(CLEAR_DB_SQL);
        }
    }

    /**
     * Tests search projects NOT of Java category.
     *
     * @throws Exception pass to JUnit.
     */
    public void testSearchProjectsNotJava() throws Exception {
        try {
            Filter javaFilter = ProjectFilterUtility.buildCategoryNameEqualFilter("Java");
            Project[] projects = manager.searchProjects(ProjectFilterUtility.buildNotFilter(javaFilter));
            assertEquals(2, projects.length);

            Set ids = new HashSet();

            for (int i = 0; i < projects.length; ++i) {
                ids.add(new Long(projects[i].getId()));
            }

            assertTrue(ids.contains(new Long(3)));
            assertTrue(ids.contains(new Long(4)));
        } finally {
            executeSQL(CLEAR_DB_SQL);
        }
    }

    /**
     * Tests getUserProjects method.
     *
     * @throws Exception pass to JUnit.
     */
    public void testGetUserProjects() throws Exception {
        try {
            Project[] projects = manager.getUserProjects(999);
            assertEquals(4, projects[0].getId());
        } finally {
            executeSQL(CLEAR_DB_SQL);
        }
    }

    /**
     * Executes a sql batch contains in a file.
     *
     * @param file the file contains the sql statements.
     *
     * @throws Exception pass to JUnit.
     */
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
}
