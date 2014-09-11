/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project.persistence.failuretests;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import junit.framework.Assert;
import junit.framework.TestCase;

import com.topcoder.db.connectionfactory.DBConnectionException;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

import com.topcoder.management.project.PersistenceException;

import com.topcoder.management.project.ProjectPersistence;

import com.topcoder.management.project.persistence.failuretests.mock.MockConnectionProducer;

/**
 * <p>
 * A failure test for {@link AbstractInformixProjectPersistence} class. Tests the class for proper handling of invalid
 * input data.
 * </p>
 *
 * <p>
 * Most of the test cases are from {@link InformixProjectPersistenceTest} version 1.0.
 * </p>
 *
 * @author isv, kshatriyan
 * @version 1.1
 */
public abstract class AbstractInformixProjectPersistenceTest extends TestCase {

    /**
     * <p>
     * An instance of {@link AbstractInformixProjectPersistence} which is tested. This instance is initialized in {@link
     * #setUp()} method and released in {@link #tearDown()} method.
     * <p>
     */
    private ProjectPersistence testedInstance = null;

    /**
     * <p>
     * A <code>Connection</code> used by this test case for accesing the target database.
     * </p>
     */
    private Connection connection = null;

    /**
     * <p>
     * Sets up the fixture. This method is called before a test is executed.
     * </p>
     *
     * @throws Exception
     *             if any error occurs.
     */
    protected void setUp() throws Exception {
        ConfigHelper.releaseNamespaces();
        ConfigHelper.loadConfiguration(new File("failure/config.xml"));

        DBConnectionFactory connectionFactory = new DBConnectionFactoryImpl(
                "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl");
        this.connection = connectionFactory.createConnection("informix");
        clearDatabaseTables();
        insertData();

        MockConnectionProducer.releaseState();
        MockConnectionProducer.throwGlobalException(new DBConnectionException("SQL error imitated by failure test"));
    }

    /**
     * <p>
     * Tears down the fixture. This method is called after a test is executed.
     * </p>
     *
     * @throws Exception
     *             if any error occurs.
     */
    protected void tearDown() throws Exception {
        this.testedInstance = null;
        this.connection.close();
        ConfigHelper.releaseNamespaces();
        MockConnectionProducer.releaseState();
    }

    /**
     * The {@link ProjectPersistence} instance used to test.
     *
     * @param persistence
     *            the project persistence instance which is used to test.
     */
    protected void setProjectPersistence(ProjectPersistence persistence) {
        this.testedInstance = persistence;
    }




    /**
     * <p>
     * Failure test. Tests the {@link AbstractInformixProjectPersistence#createProject(Project,String)} method for
     * proper handling the invalid input arguments.
     * </p>
     *
     * <p>
     * Passes <code>null</code> as <code>project</code> and expects the <code>IllegalArgumentException</code> to
     * be thrown.
     * </p>
     */
    public void testCreateProject_Project_String_project_null() {
        try {
            this.testedInstance.createProject(null, TestDataFactory.OPERATOR);
            Assert.fail("IllegalArgumentException should have been thrown");
        } catch (IllegalArgumentException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("IllegalArgumentException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>
     * Failure test. Tests the {@link AbstractInformixProjectPersistence#createProject(Project,String)} method for
     * proper handling the invalid input arguments.
     * </p>
     *
     * <p>
     * Passes {@link TestDataFactory#getProjectWithInvalidCategory()} as <code>project</code> and expects the
     * <code>PersistenceException</code> to be thrown.
     * </p>
     */
    public void testCreateProject_Project_String_project_PROJECT_WITH_INVALID_CATEGORY() {
        try {
            this.testedInstance.createProject(TestDataFactory.getProjectWithInvalidCategory(),
                    TestDataFactory.OPERATOR);
            Assert.fail("PersistenceException should have been thrown");
        } catch (PersistenceException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("PersistenceException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>
     * Failure test. Tests the {@link AbstractInformixProjectPersistence#createProject(Project,String)} method for
     * proper handling the invalid input arguments.
     * </p>
     *
     * <p>
     * Passes {@link TestDataFactory#getProjectWithInvalidStatus} as <code>project</code> and expects the
     * <code>PersistenceException</code> to be thrown.
     * </p>
     */
    public void testCreateProject_Project_String_project_PROJECT_WITH_INVALID_STATUS() {
        try {
            this.testedInstance
                    .createProject(TestDataFactory.getProjectWithInvalidStatus(), TestDataFactory.OPERATOR);
            Assert.fail("PersistenceException should have been thrown");
        } catch (PersistenceException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("PersistenceException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>
     * Failure test. Tests the {@link AbstractInformixProjectPersistence#createProject(Project,String)} method for
     * proper handling the invalid input arguments.
     * </p>
     *
     * <p>
     * Passes <code>null</code> as <code>operator</code> and expects the <code>IllegalArgumentException</code>
     * to be thrown.
     * </p>
     */
    public void testCreateProject_Project_String_operator_null() {
        try {
            this.testedInstance.createProject(TestDataFactory.getProject(), null);
            Assert.fail("IllegalArgumentException should have been thrown");
        } catch (IllegalArgumentException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("IllegalArgumentException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>
     * Failure test. Tests the {@link AbstractInformixProjectPersistence#createProject(Project,String)} method for
     * proper handling the invalid input arguments.
     * </p>
     *
     * <p>
     * Passes {@link TestDataFactory#ZERO_LENGTH_STRING} as <code>operator</code> and expects the
     * <code>IllegalArgumentException</code> to be thrown.
     * </p>
     */
    public void testCreateProject_Project_String_operator_ZERO_LENGTH_STRING() {
        try {
            this.testedInstance.createProject(TestDataFactory.getProject(), TestDataFactory.ZERO_LENGTH_STRING);
            Assert.fail("IllegalArgumentException should have been thrown");
        } catch (IllegalArgumentException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("IllegalArgumentException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>
     * Failure test. Tests the {@link AbstractInformixProjectPersistence#createProject(Project,String)} method for
     * proper handling the invalid input arguments.
     * </p>
     *
     * <p>
     * Passes {@link TestDataFactory#WHITESPACE_ONLY_STRING} as <code>operator</code> and expects the
     * <code>IllegalArgumentException</code> to be thrown.
     * </p>
     */
    public void testCreateProject_Project_String_operator_WHITESPACE_ONLY_STRING() {
        try {
            this.testedInstance.createProject(TestDataFactory.getProject(), TestDataFactory.WHITESPACE_ONLY_STRING);
            Assert.fail("IllegalArgumentException should have been thrown");
        } catch (IllegalArgumentException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("IllegalArgumentException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>
     * Failure test. Tests the {@link AbstractInformixProjectPersistence#updateProject(Project,String,String)} method
     * for proper handling the invalid input arguments.
     * </p>
     *
     * <p>
     * Passes <code>null</code> as <code>project</code> and expects the <code>IllegalArgumentException</code> to
     * be thrown.
     * </p>
     */
    public void testUpdateProject_Project_String_String_project_null() {
        try {
            this.testedInstance.updateProject(null, TestDataFactory.REASON, TestDataFactory.OPERATOR);
            Assert.fail("IllegalArgumentException should have been thrown");
        } catch (IllegalArgumentException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("IllegalArgumentException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>
     * Failure test. Tests the {@link AbstractInformixProjectPersistence#updateProject(Project,String,String)} method
     * for proper handling the invalid input arguments.
     * </p>
     *
     * <p>
     * Passes {@link TestDataFactory#getProjectWithZeroId} as <code>project</code> and expects the
     * <code>IllegalArgumentException</code> to be thrown.
     * </p>
     */
    public void testUpdateProject_Project_String_String_project_PROJECT_WITH_ZERO_ID() {
        try {
            this.testedInstance.updateProject(TestDataFactory.getProjectWithZeroId(), TestDataFactory.REASON,
                    TestDataFactory.OPERATOR);
            Assert.fail("IllegalArgumentException should have been thrown");
        } catch (IllegalArgumentException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("IllegalArgumentException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>
     * Failure test. Tests the {@link AbstractInformixProjectPersistence#updateProject(Project,String,String)} method
     * for proper handling the invalid input arguments.
     * </p>
     *
     * <p>
     * Passes {@link TestDataFactory#getProjectWithInvalidCategory()} as <code>project</code> and expects the
     * <code>PersistenceException</code> to be thrown.
     * </p>
     */
    public void testUpdateProject_Project_String_String_project_PROJECT_WITH_INVALID_CATEGORY() {
        try {
            this.testedInstance.updateProject(TestDataFactory.getProjectWithInvalidCategory(),
                    TestDataFactory.REASON, TestDataFactory.OPERATOR);
            Assert.fail("PersistenceException should have been thrown");
        } catch (PersistenceException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("PersistenceException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>
     * Failure test. Tests the {@link AbstractInformixProjectPersistence#updateProject(Project,String,String)} method
     * for proper handling the invalid input arguments.
     * </p>
     *
     * <p>
     * Passes {@link TestDataFactory#getProjectWithInvalidStatus()} as <code>project</code> and expects the
     * <code>PersistenceException</code> to be thrown.
     * </p>
     */
    public void testUpdateProject_Project_String_String_project_PROJECT_WITH_INVALID_STATUS() {
        try {
            this.testedInstance.updateProject(TestDataFactory.getProjectWithInvalidStatus(), TestDataFactory.REASON,
                    TestDataFactory.OPERATOR);
            Assert.fail("PersistenceException should have been thrown");
        } catch (PersistenceException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("PersistenceException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>
     * Failure test. Tests the {@link AbstractInformixProjectPersistence#updateProject(Project,String,String)} method
     * for proper handling the invalid input arguments.
     * </p>
     *
     * <p>
     * Passes <code>null</code> as <code>reason</code> and expects the <code>IllegalArgumentException</code> to
     * be thrown.
     * </p>
     */
    public void testUpdateProject_Project_String_String_reason_null() {
        try {
            this.testedInstance.updateProject(TestDataFactory.getProject(), null, TestDataFactory.OPERATOR);
            Assert.fail("IllegalArgumentException should have been thrown");
        } catch (IllegalArgumentException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("IllegalArgumentException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>
     * Failure test. Tests the {@link AbstractInformixProjectPersistence#updateProject(Project,String,String)} method
     * for proper handling the invalid input arguments.
     * </p>
     *
     * <p>
     * Passes <code>null</code> as <code>operator</code> and expects the <code>IllegalArgumentException</code>
     * to be thrown.
     * </p>
     */
    public void testUpdateProject_Project_String_String_operator_null() {
        try {
            this.testedInstance.updateProject(TestDataFactory.getProject(), TestDataFactory.REASON, null);
            Assert.fail("IllegalArgumentException should have been thrown");
        } catch (IllegalArgumentException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("IllegalArgumentException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>
     * Failure test. Tests the {@link AbstractInformixProjectPersistence#updateProject(Project,String,String)} method
     * for proper handling the invalid input arguments.
     * </p>
     *
     * <p>
     * Passes {@link TestDataFactory#ZERO_LENGTH_STRING} as <code>operator</code> and expects the
     * <code>IllegalArgumentException</code> to be thrown.
     * </p>
     */
    public void testUpdateProject_Project_String_String_operator_ZERO_LENGTH_STRING() {
        try {
            this.testedInstance.updateProject(TestDataFactory.getProject(), TestDataFactory.REASON,
                    TestDataFactory.ZERO_LENGTH_STRING);
            Assert.fail("IllegalArgumentException should have been thrown");
        } catch (IllegalArgumentException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("IllegalArgumentException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>
     * Failure test. Tests the {@link AbstractInformixProjectPersistence#updateProject(Project,String,String)} method
     * for proper handling the invalid input arguments.
     * </p>
     *
     * <p>
     * Passes {@link TestDataFactory#WHITESPACE_ONLY_STRING} as <code>operator</code> and expects the
     * <code>IllegalArgumentException</code> to be thrown.
     * </p>
     */
    public void testUpdateProject_Project_String_String_operator_WHITESPACE_ONLY_STRING() {
        try {
            this.testedInstance.updateProject(TestDataFactory.getProject(), TestDataFactory.REASON,
                    TestDataFactory.WHITESPACE_ONLY_STRING);
            Assert.fail("IllegalArgumentException should have been thrown");
        } catch (IllegalArgumentException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("IllegalArgumentException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>
     * Failure test. Tests the {@link AbstractInformixProjectPersistence#getProject(long)} method for proper handling
     * the invalid input arguments.
     * </p>
     *
     * <p>
     * Passes {@link 0} as <code>id</code> and expects the <code>IllegalArgumentException</code> to be thrown.
     * </p>
     */
    public void testGetProject_long_id_0() {
        try {
            this.testedInstance.getProject(0);
            Assert.fail("IllegalArgumentException should have been thrown");
        } catch (IllegalArgumentException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("IllegalArgumentException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>
     * Failure test. Tests the {@link AbstractInformixProjectPersistence#getProject(long)} method for proper handling
     * the invalid input arguments.
     * </p>
     *
     * <p>
     * Passes {@link -10} as <code>id</code> and expects the <code>IllegalArgumentException</code> to be thrown.
     * </p>
     */
    public void testGetProject_long_id_NegativeId() {
        try {
            this.testedInstance.getProject(-10);
            Assert.fail("IllegalArgumentException should have been thrown");
        } catch (IllegalArgumentException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("IllegalArgumentException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>
     * Failure test. Tests the {@link AbstractInformixProjectPersistence#getProjects(long[])} method for proper handling
     * the invalid input arguments.
     * </p>
     *
     * <p>
     * Passes <code>null</code> as <code>ids</code> and expects the <code>IllegalArgumentException</code> to be
     * thrown.
     * </p>
     */
    public void testGetProjects_long_ids_null() {
        try {
            this.testedInstance.getProjects((long[]) null);
            Assert.fail("IllegalArgumentException should have been thrown");
        } catch (IllegalArgumentException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("IllegalArgumentException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>
     * Failure test. Tests the {@link AbstractInformixProjectPersistence#getProjects(long[])} method for proper handling
     * the invalid input arguments.
     * </p>
     *
     * <p>
     * Passes {@link TestDataFactory#EMPTY_ID_ARRAY} as <code>ids</code> and expects the
     * <code>IllegalArgumentException</code> to be thrown.
     * </p>
     */
    public void testGetProjects_long_ids_EMPTY_ID_ARRAY() {
        try {
            this.testedInstance.getProjects(TestDataFactory.EMPTY_ID_ARRAY);
            Assert.fail("IllegalArgumentException should have been thrown");
        } catch (IllegalArgumentException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("IllegalArgumentException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>
     * Failure test. Tests the {@link AbstractInformixProjectPersistence#getProjects(long[])} method for proper handling
     * the invalid input arguments.
     * </p>
     *
     * <p>
     * Passes {@link TestDataFactory#NEGATIVE_ID_ARRAY} as <code>ids</code> and expects the
     * <code>IllegalArgumentException</code> to be thrown.
     * </p>
     */
    public void testGetProjects_long_ids_NEGATIVE_ID_ARRAY() {
        try {
            this.testedInstance.getProjects(TestDataFactory.NEGATIVE_ID_ARRAY);
            Assert.fail("IllegalArgumentException should have been thrown");
        } catch (IllegalArgumentException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("IllegalArgumentException was expected but the original exception is : " + e);
        }
    }

    /**
     * <p>
     * Failure test. Tests the {@link AbstractInformixProjectPersistence#getProjects(long[])} method for proper handling
     * the invalid input arguments.
     * </p>
     *
     * <p>
     * Passes {@link TestDataFactory#ZERO_ID_ARRAY} as <code>ids</code> and expects the
     * <code>IllegalArgumentException</code> to be thrown.
     * </p>
     */
    public void testGetProjects_long_ids_ZERO_ID_ARRAY() {
        try {
            this.testedInstance.getProjects(TestDataFactory.ZERO_ID_ARRAY);
            Assert.fail("IllegalArgumentException should have been thrown");
        } catch (IllegalArgumentException e) {
            // expected behavior
        } catch (Exception e) {
            Assert.fail("IllegalArgumentException was expected but the original exception is : " + e);
        }
    }





    /**
     * <p>
     * Removes all data from the database tables which are affected by this test case.
     * </p>
     *
     * @throws SQLException
     *             if an SQL error occurs.
     */
    private void clearDatabaseTables() throws SQLException {
        String[] tables = new String[] {"project_audit", "project_info", "project", "project_category_lu",
            "project_type_lu", "project_info_type_lu", "project_status_lu" };

        PreparedStatement stmt = null;
        try {
            for (int i = 0; i < tables.length; i++) {
                stmt = this.connection.prepareStatement("DELETE FROM " + tables[i]);
                stmt.executeUpdate();
                stmt.close();
            }
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    /**
     * <p>
     * Populates the database with sample data.
     * </p>
     *
     * @throws SQLException
     *             if an SQL error occurs.
     */
    private void insertData() throws SQLException {
        Statement stmt = null;
        try {
            stmt = this.connection.createStatement();
            stmt.executeUpdate("INSERT INTO project_info_type_lu (project_info_type_id, name, description, "
                    + "create_user, create_date, modify_user, modify_date) "
                    + "VALUES (1, 'property_type', 'property_description', 'operator', CURRENT, "
                    + "'operator', CURRENT)");
            stmt.executeUpdate("INSERT INTO project_status_lu (project_status_id, name, description, create_user, "
                    + "create_date, modify_user, modify_date) VALUES (1, 'project_status', "
                    + "'status_description', 'operator', CURRENT, 'operator', CURRENT)");
            stmt.executeUpdate("INSERT INTO project_type_lu (project_type_id, name, description, create_user, "
                    + "create_date, modify_user, modify_date) VALUES (1, 'project_type', "
                    + "'type_description', 'operator', CURRENT, 'operator', CURRENT)");
            stmt.executeUpdate("INSERT INTO project_category_lu(project_category_id, project_type_id, name, "
                    + "description, create_user, create_date, modify_user, modify_date) "
                    + "VALUES (1, 1, 'category', 'description', 'operator', CURRENT, 'operator'," + "CURRENT)");
            stmt
                    .executeUpdate("INSERT INTO project (project_id, project_status_id, project_category_id,"
                            + " create_user, create_date, modify_user, modify_date) VALUES (1, 1, 1, 'operator',"
                            + " CURRENT, 'operator', CURRENT)");
            stmt.close();
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }
}
