/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project.persistence.accuracytests;

import java.io.File;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.Map;

import junit.framework.TestCase;

import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.management.project.PersistenceException;
import com.topcoder.management.project.Project;
import com.topcoder.management.project.ProjectCategory;
import com.topcoder.management.project.ProjectPropertyType;
import com.topcoder.management.project.ProjectStatus;
import com.topcoder.management.project.ProjectType;
import com.topcoder.management.project.persistence.InformixProjectPersistence;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.idgenerator.IDGenerator;
import com.topcoder.util.idgenerator.IDGeneratorFactory;

/**
 * Accuracy tests for the class: InformixProjectPersistence.
 *
 * @author kinfkong
 * @version 1.0
 */
public class InformixProjectPersistenceAccuracyTest extends TestCase {

    /**
     * The config file for the DBConnectionFactory.
     */
    private static final String DBCONFIG =
        "test_files" + File.separator + "accuracytests" + File.separator + "dbconfig.xml";

    /**
     * The config file for the InformixProjectPersistence.
     */
    private static final String PERSISTENCECONFIG =
        "test_files" + File.separator + "accuracytests" + File.separator + "persistenceconfig.xml";

    /**
     * An instance of ConfigManager for tests.
     */
    private ConfigManager cm = ConfigManager.getInstance();

    /**
     * An instance of InformixProjectPersistence for tests.
     */
    private InformixProjectPersistence persistence;

    /**
     * Sets up the environments.
     *
     * @throws Exception to JUnit
     */
    public void setUp() throws Exception {
        String[] configFiles = new String[] {DBCONFIG, PERSISTENCECONFIG};
        // add the config files to ConfigManager
        loadXMLConfigFiles(configFiles);
        insertRecords();
        // create the instance of InformixProjectPersistence
        persistence = new InformixProjectPersistence(
            "com.topcoder.management.project.persistence.InformixProjectPersistence.default");
    }

    /**
     * Clears the environments.
     *
     * @throws Exception to JUnit
     */
    public void tearDown() throws Exception {
        clearTables();
        clearNamespaces();
    }

    /**
     * Tests constructor InformixProjectPersistence(String).
     *
     * With default configurations - ConnectionName, ProjectIdSequenceName and
     * ProjectAuditIdSequenceName are missing.
     *
     * @throws Exception to JUnit
     */
    public void testInformixProjectPersistence_WithDefaultConfiguration() throws Exception {
        InformixProjectPersistence persistence = new InformixProjectPersistence(
                "com.topcoder.management.project.persistence.InformixProjectPersistence.default");

        // test the connectionName
        String connectionName = (String) getPrivateObject(persistence, "connectionName");
        assertNull("The connectionName should be null.", connectionName);

        // test the projectIdGenerator
        IDGenerator projectIdGenerator = (IDGenerator) getPrivateObject(persistence, "projectIdGenerator");
        assertEquals("The name of projectIDGenerator is incorrect.",
                "project_id_seq", projectIdGenerator.getIDName());

        // test the projectAuditIdGenerator
        IDGenerator projectAuditIdGenerator = (IDGenerator) getPrivateObject(persistence, "projectAuditIdGenerator");
        assertEquals("The name of projectAuditIdGenerator is incorrect.",
                "project_audit_id_seq", projectAuditIdGenerator.getIDName());
    }

    /**
     * Tests constructor InformixProjectPersistence(String).
     *
     * With another configurations -
     * ConnectionName, ProjectIdSequenceName and ProjectAuditIdSequenceName are set to specific values.
     *
     * @throws Exception to JUnit
     */
    public void testInformixProjectPersistence_WithAnotherConfiguration() throws Exception {
        InformixProjectPersistence persistence = new InformixProjectPersistence(
                "com.topcoder.management.project.persistence.InformixProjectPersistence.another");

        // test the connectionName
        String connectionName = (String) getPrivateObject(persistence, "connectionName");
        assertEquals("The connectionName is incorrect.", "dbconnection", connectionName);

        // test the projectIdGenerator
        IDGenerator projectIdGenerator = (IDGenerator) getPrivateObject(persistence, "projectIdGenerator");
        assertEquals("The name of projectIDGenerator is incorrect.",
                "project_id_seq", projectIdGenerator.getIDName());

        // test the projectAuditIdGenerator
        IDGenerator projectAuditIdGenerator = (IDGenerator) getPrivateObject(persistence, "projectAuditIdGenerator");
        assertEquals("The name of projectAuditIdGenerator is incorrect.",
                "project_audit_id_seq", projectAuditIdGenerator.getIDName());
    }

    /**
     * Tests method createProject(Project, String).
     * With a project without any properties.
     *
     * @throws Exception to JUnit
     */
    public void testCreateProject_WithoutProperties() throws Exception {
        // get the project with the category id = 2, and the status id = 1.
        Project project = getProject(2, 1);
        persistence.createProject(project, "accuracy_reviewer");
        Connection con = getConnection();
        String sql = "SELECT * FROM project WHERE project_status_id = 1 AND project_category_id = 2";
        // checks if the record has been persistenced.
        assertTrue("The record does not exists.", existsRecords(con, sql, new Object[] {}));

        // get the id from the table
        sql = "SELECT * FROM project WHERE project_status_id = 1 AND project_category_id = 2";
        Long projectId = (Long) getObject(con, sql, new Object[] {}, "project_id", Long.class);
        // get the creation user from the table
        String user = (String) getObject(con, sql, new Object[] {}, "create_user", String.class);
        // get the creation timestamp from the table
        Timestamp time = (Timestamp) getObject(con, sql, new Object[] {}, "create_date", Timestamp.class);
        // checks if the id, creation user, creation timestamp, modification user, modification timestamp has
        // been set back to the project instance.
        assertEquals("The id is not properly set.", projectId.longValue(), project.getId());
        assertEquals("The create user is not properly set.", user, project.getCreationUser());
        assertEquals("The modification user is not properly set.", user, project.getModificationUser());
        assertEquals("The create date is not properly set.",
                time.getTime(), project.getCreationTimestamp().getTime());
        assertEquals("The modification date is not properly set.",
                time.getTime(), project.getModificationTimestamp().getTime());
        con.close();
    }

    /**
     * Tests method createProject(Project, String).
     * With a project with properties.
     *
     * @throws Exception to JUnit
     */
    public void testCreateProject_WithProperties() throws Exception {
        // get the project with the category id = 2, and the status id = 1.
        Project project = getProject(2, 1);
        // set the properties to the project

        project.setProperty("property 1", "value 1");
        project.setProperty("property 2", "value 2");
        project.setProperty("property 3", "value 3");
        project.setProperty("property 4", "value 4");

        persistence.createProject(project, "accuracy_reviewer");
        Connection con = getConnection();
        String sql = "SELECT * FROM project WHERE project_status_id = 1 AND project_category_id = 2";
        // checks if the record has been persistenced.
        assertTrue("The record does not exists.", existsRecords(con, sql, new Object[] {}));

        // get the id from the table
        sql = "SELECT * FROM project WHERE project_status_id = 1 AND project_category_id = 2";
        Long projectId = (Long) getObject(con, sql, new Object[] {}, "project_id", Long.class);
        // get the creation user from the table
        String user = (String) getObject(con, sql, new Object[] {}, "create_user", String.class);
        // get the creation timestamp from the table
        Timestamp time = (Timestamp) getObject(con, sql, new Object[] {}, "create_date", Timestamp.class);
        // checks if the id, creation user, creation timestamp, modification user, modification timestamp has
        // been set back to the project instance.
        assertEquals("The id is not properly set.", projectId.longValue(), project.getId());
        assertEquals("The create user is not properly set.", user, project.getCreationUser());
        assertEquals("The modification user is not properly set.", user, project.getModificationUser());
        assertEquals("The create date is not properly set.",
                time.getTime(), project.getCreationTimestamp().getTime());
        assertEquals("The modification date is not properly set.",
                time.getTime(), project.getModificationTimestamp().getTime());

        // checks the table: project_info
        sql = "SELECT * FROM project_info WHERE project_id = ? AND project_info_type_id = ?";
        String value = (String) getObject(con, sql, new Object[] {projectId, new Long(1)}, "value", String.class);
        assertEquals("The value is not set properly", "value 1", value);
        value = (String) getObject(con, sql, new Object[] {projectId, new Long(2)}, "value", String.class);
        assertEquals("The value is not set properly", "value 2", value);
        value = (String) getObject(con, sql, new Object[] {projectId, new Long(3)}, "value", String.class);
        assertEquals("The value is not set properly", "value 3", value);
        value = (String) getObject(con, sql, new Object[] {projectId, new Long(4)}, "value", String.class);
        assertEquals("The value is not set properly", "value 4", value);
        con.close();
    }

    /**
     * Creates the same project twice.
     * Should only persist it once.
     *
     * @throws Exception to JUnit
     */
    public void testCreateProject_Duplicated() throws Exception {
        // get the project with the category id = 2, and the status id = 1.
        Project project = getProject(2, 1);
        // first time
        persistence.createProject(project, "accuracy_reviewer");
        // second time
        try {
            persistence.createProject(project, "accuracy_reviewer");
        } catch (PersistenceException e) {
            // ignore
        }
        Connection con = getConnection();
        String sql = "SELECT COUNT(*) FROM project";
        Integer rowCount = (Integer) getObject(con, sql, new Object[] {}, "(count(*))", Integer.class);
        assertEquals("It should have only one record.", 1, rowCount.intValue());
        con.close();
    }

    /**
     * Creates the project with several projects.
     *
     * @throws Exception to JUnit
     */
    public void testCreateProject_Multiple() throws Exception {
        // create two different projects
        Project project1 = getProject(1, 2);
        Project project2 = getProject(2, 1);
        // set their properties
        project1.setProperty("property 1", "project1_value_1");
        project1.setProperty("property 2", "project1_value_2");
        project2.setProperty("property 2", "project2_value_2");
        project2.setProperty("property 3", "project2_value_3");

        // set two projects
        persistence.createProject(project1, "accuracy_reviewer");
        persistence.createProject(project2, "accuracy_reviewer");


        Connection con = getConnection();
        // the project ids generated by the IDGenerator have been re-set to the project instance.
        long projectId1 = project1.getId();
        long projectId2 = project2.getId();
        // checks if the records exist in the table: project
        String sql = "SELECT * FROM project WHERE project_id = ?";
        assertTrue("The project does not exist in the database.", existsRecords(con, sql, new Object[] {
                new Long(projectId1)}));
        assertTrue("The project does not exist in the database.", existsRecords(con, sql, new Object[] {
                new Long(projectId2)}));

        // cehck if the records exist in the table: project_info
        sql = "SELECT * FROM project_info WHERE project_id = ? AND project_info_type_id = ? AND value = ?";
        assertTrue("The project does not exist in the database.", existsRecords(con, sql, new Object[] {
                new Long(projectId1), new Long(1), "project1_value_1"}));
        assertTrue("The project does not exist in the database.", existsRecords(con, sql, new Object[] {
                new Long(projectId1), new Long(2), "project1_value_2"}));
        assertTrue("The project does not exist in the database.", existsRecords(con, sql, new Object[] {
                new Long(projectId2), new Long(2), "project2_value_2"}));
        assertTrue("The project does not exist in the database.", existsRecords(con, sql, new Object[] {
                new Long(projectId2), new Long(3), "project2_value_3"}));
    }

    /**
     * Tests method: updateProject(Project, String, String).
     *
     * Simply update a project with the status id changed.
     *
     * @throws Exception to JUnit
     */
    public void testUpdateProject_ChangeStatusID() throws Exception {
        // create the project
        Project project = getProject(1, 1);
        persistence.createProject(project, "accuracy_reviewer");
        project.setProjectStatus(new ProjectStatus(2, "newStatus"));
        persistence.updateProject(project, "For tests", "tcs_reviewer");

        Connection con = getConnection();
        String sql = "SELECT * FROM project WHERE project_id = ? AND project_status_id = ? AND modify_user = ?";
        // the original record does not exists
        assertTrue("The original record should be changed.", !existsRecords(con, sql, new Object[] {
                new Long(project.getId()), new Long(1), "accuracy_reviewer"
            }));
        // new record exists
        assertTrue("The new record should exist", existsRecords(con, sql, new Object[] {
                new Long(project.getId()), new Long(2), "tcs_reviewer"
            }));
        con.close();
    }

    /**
     * Tests method: updateProject(Project, String, String).
     *
     * Update a project with some new properties.
     *
     * @throws Exception to JUnit
     */
    public void testUpdateProject_AddProperties() throws Exception {
        // create the project
        Project project = getProject(1, 1);
        project.setProperty("property 1", "value 1");
        // persistence
        persistence.createProject(project, "accuracy_reviewer");
        project.setProperty("property 2", "new value 2");
        project.setProperty("property 3", "new value 3");
        // update
        persistence.updateProject(project, "For tests", "tcs_reviewer");

        Connection con = getConnection();
        String sql =
            "SELECT * FROM project_info WHERE project_id = ? AND project_info_type_id = ? AND modify_user = ?";
        // the original record still exists
        assertTrue("The original record should still exist.", existsRecords(con, sql, new Object[] {
                new Long(project.getId()), new Long(1), "tcs_reviewer"
            }));
        // new record exists
        assertTrue("The new record should exist", existsRecords(con, sql, new Object[] {
                new Long(project.getId()), new Long(2), "tcs_reviewer"
            }));
        assertTrue("The new record should exist", existsRecords(con, sql, new Object[] {
                new Long(project.getId()), new Long(3), "tcs_reviewer"
            }));
        con.close();
    }

    /**
     * Tests method: updateProject(Project, String, String).
     *
     * Update a project with some properties needs removing.
     *
     * @throws Exception to JUnit
     */
    public void testUpdateProject_RemoveProperties() throws Exception {
        // create the project
        Project project = getProject(1, 1);
        project.setProperty("property 1", "value 1");
        project.setProperty("property 2", "value 2");
        project.setProperty("property 3", "value 3");
        // persistence
        persistence.createProject(project, "accuracy_reviewer");

        // the value is null means to remove it
        project.setProperty("property 2", null);

        // update
        persistence.updateProject(project, "For tests", "tcs_reviewer");

        Connection con = getConnection();
        String sql =
            "SELECT * FROM project_info WHERE project_id = ? AND project_info_type_id = ? AND modify_user = ?";
        // the original record still exists
        assertTrue("The original record should still exist.", existsRecords(con, sql, new Object[] {
                new Long(project.getId()), new Long(1), "tcs_reviewer"
            }));
        assertTrue("The original record should still exist.", existsRecords(con, sql, new Object[] {
                new Long(project.getId()), new Long(3), "tcs_reviewer"
            }));
        // the removed record does not exist
        assertTrue("The removed record should not exist", !existsRecords(con, sql, new Object[] {
                new Long(project.getId()), new Long(2), "tcs_reviewer"
            }));
        assertTrue("The new record should exist", existsRecords(con, sql, new Object[] {
                new Long(project.getId()), new Long(3), "tcs_reviewer"
            }));
        con.close();
    }

    /**
     * Tests method: updateProject(Project, String, String).
     *
     * Update a project with some properties changed.
     *
     * @throws Exception to JUnit
     */
    public void testUpdateProject_ChangeProperties() throws Exception {
        // create the project
        Project project = getProject(1, 1);
        project.setProperty("property 1", "value 1");
        project.setProperty("property 2", "value 2");
        project.setProperty("property 3", "value 3");
        // persistence
        persistence.createProject(project, "accuracy_reviewer");

        // the value is null means to change
        project.setProperty("property 2", "new value 2");

        // update
        persistence.updateProject(project, "For tests", "tcs_reviewer");

        Connection con = getConnection();
        String sql =
            "SELECT * FROM project_info WHERE project_id = ? AND project_info_type_id = ? AND modify_user = ?";
        // the original record still exists
        assertTrue("The original record should still exist.", existsRecords(con, sql, new Object[] {
                new Long(project.getId()), new Long(1), "tcs_reviewer"
            }));
        assertTrue("The original record should still exist.", existsRecords(con, sql, new Object[] {
                new Long(project.getId()), new Long(3), "tcs_reviewer"
            }));
        // the original  record does not exist
        sql = "SELECT * FROM project_info WHERE project_id = ? AND project_info_type_id = ? AND value = ?";
        assertTrue("The old record should not exist", !existsRecords(con, sql, new Object[] {
                new Long(project.getId()), new Long(2), "value 2"
            }));
        assertTrue("The new record should exist", existsRecords(con, sql, new Object[] {
                new Long(project.getId()), new Long(2), "new value 2"
            }));
        con.close();
    }

    /**
     * Tests method: updateProject(Project, String, String).
     *
     * Tests batch remove.
     *
     * @throws Exception to JUnit
     */
    public void testUpdateProject_BatchRemove() throws Exception {
        // create the projects
        Project project1 = getProject(1, 1);
        project1.setProperty("property 1", "value 1");
        project1.setProperty("property 2", "value 2");
        project1.setProperty("property 3", "value 3");
        Project project2 = getProject(1, 1);
        project2.setProperty("property 1", "value 1");
        project2.setProperty("property 2", "value 2");
        project2.setProperty("property 3", "value 3");
        // persistence
        persistence.createProject(project1, "accuracy_reviewer");
        persistence.createProject(project2, "accuracy_reviewer");

        // remove property 1, 2 of project 2
        project2.setProperty("property 1", null);
        project2.setProperty("property 2", null);

        // update
        persistence.updateProject(project1, "For tests", "tcs_reviewer");
        persistence.updateProject(project2, "For tests", "tcs_reviewer");


        Connection con = getConnection();
        String sql =
            "SELECT * FROM project_info WHERE project_id = ? AND project_info_type_id = ? AND modify_user = ?";
        // the original record still exists
        assertTrue("The original record should still exist.", existsRecords(con, sql, new Object[] {
                new Long(project1.getId()), new Long(1), "tcs_reviewer"
            }));
        assertTrue("The original record should still exist.", existsRecords(con, sql, new Object[] {
                new Long(project1.getId()), new Long(2), "tcs_reviewer"
            }));
        assertTrue("The original record should still exist.", existsRecords(con, sql, new Object[] {
                new Long(project1.getId()), new Long(3), "tcs_reviewer"
            }));
        assertTrue("The original record should still exist.", existsRecords(con, sql, new Object[] {
                new Long(project2.getId()), new Long(3), "tcs_reviewer"
            }));
        // the original  record does not exist
        sql = "SELECT * FROM project_info WHERE project_id = ? AND project_info_type_id = ? AND value = ?";
        assertTrue("The old record should not exist", !existsRecords(con, sql, new Object[] {
                new Long(project2.getId()), new Long(1), "value 1"
            }));
        assertTrue("The old record should not exist", !existsRecords(con, sql, new Object[] {
                new Long(project2.getId()), new Long(2), "value 2"
            }));
        con.close();
    }

    /**
     * Tests the method: Update(Project, String, String).
     *
     * Tests if it has been persisted in the table: project_audit after each update.
     *
     * @throws Exception to JUnit
     */
    public void testUpdateProject_Audit() throws Exception {
        Project project = getProject(1, 1);
        // persistence
        persistence.createProject(project, "accuracy_reviewer");
        // update
        persistence.updateProject(project, "for tests", "tcs_reviewer");
        String sql = "SELECT * FROM project_audit WHERE project_id = ?";
        Connection con = getConnection();
        assertTrue("Should update the table: project_audit.", existsRecords(con, sql, new Object[] {
                new Long(project.getId())
        }));
        // the modification user has been set to the project itself
        assertEquals("the modification user should be set to the project itself.",
                "tcs_reviewer", project.getModificationUser());
    }


    /**
     * Tests method: getProject(long).
     *
     * Inserts one project and choose that project.
     *
     * @throws Exception to JUnit
     */
    public void testGetProject() throws Exception {
        // create three different projects
        Project project = getProject(1, 1);
        // set the properties
        project.setProperty("property 1", "1_1");
        project.setProperty("property 2", "1_2");
        // do the persistence
        persistence.createProject(project, "accuracy_reviewer");

        Project projectRes = persistence.getProject(project.getId());

        // tests not null
        assertNotNull("It should not be null", projectRes);
        // tests the accuracy of the project

        // test the category id
        assertEquals("The category id is set incorrectly.", 1, projectRes.getProjectCategory().getId());
        // test the status id
        assertEquals("The status id is set incorrectly.", 1, projectRes.getProjectStatus().getId());
        // test the property
        assertEquals("The property is not set properly.", "1_1", projectRes.getProperty("property 1"));
        assertEquals("The property is not set properly.", "1_2", projectRes.getProperty("property 2"));
    }

    /**
     * Tests method: getProject(long).
     *
     * Inserts several project and choose one project of them.
     *
     * @throws Exception to JUnit
     */
    public void testGetProject_Multiple() throws Exception {
        // create three different projects
        Project project = getProject(1, 1);
        Project project1 = getProject(1, 1);
        Project project2 = getProject(2, 1);
        Project project3 = getProject(1, 2);
        Project project4 = getProject(2, 2);
        // set the properties
        project.setProperty("property 1", "1_1");
        project.setProperty("property 2", "1_2");
        // do the persistence
        persistence.createProject(project, "accuracy_reviewer");
        persistence.createProject(project1, "accuracy_reviewer");
        persistence.createProject(project2, "accuracy_reviewer");
        persistence.createProject(project3, "accuracy_reviewer");
        persistence.createProject(project4, "accuracy_reviewer");

        Project projectRes = persistence.getProject(project.getId());

        // tests not null
        assertNotNull("It should not be null", projectRes);
        // tests the accuracy of the project

        // test the category id
        assertEquals("The category id is set incorrectly.", 1, projectRes.getProjectCategory().getId());
        // test the status id
        assertEquals("The status id is set incorrectly.", 1, projectRes.getProjectStatus().getId());
        // test the property
        assertEquals("The property is not set properly.", "1_1", projectRes.getProperty("property 1"));
        assertEquals("The property is not set properly.", "1_2", projectRes.getProperty("property 2"));
    }

    /**
     * Tests method: getProject(long).
     *
     * Inserts several project and choose one project id that does not exists.
     *
     * @throws Exception to JUnit
     */
    public void testGetProject_NotExists() throws Exception {
        // create three different projects
        Project project = getProject(1, 1);
        Project project1 = getProject(1, 1);
        Project project2 = getProject(2, 1);
        Project project3 = getProject(1, 2);
        Project project4 = getProject(2, 2);
        // set the properties
        project.setProperty("property 1", "1_1");
        project.setProperty("property 2", "1_2");
        // do the persistence
        persistence.createProject(project, "accuracy_reviewer");
        persistence.createProject(project1, "accuracy_reviewer");
        persistence.createProject(project2, "accuracy_reviewer");
        persistence.createProject(project3, "accuracy_reviewer");
        persistence.createProject(project4, "accuracy_reviewer");

        // use the IDGenerator to generate a new id
        IDGenerator generator = IDGeneratorFactory.getIDGenerator("project_id_seq");
        long newId = generator.getNextID();
        Project projectRes = persistence.getProject(newId);

        // assert null
        assertNull("It should be null", projectRes);
    }

    /**
     * Tests method: getProjects(long[]).
     *
     * Tests with mutiple ids.
     *
     * @throws Exception to JUnit
     */
    public void testGetProjects() throws Exception {
        // create three different projects
        Project project1 = getProject(1, 1);
        Project project2 = getProject(2, 2);
        Project project3 = getProject(1, 2);
        // set the properties
        project1.setProperty("property 1", "1_1");
        project1.setProperty("property 2", "1_2");
        project2.setProperty("property 2", "2_2");
        // do the persistence
        persistence.createProject(project1, "accuracy_reviewer");
        persistence.createProject(project2, "accuracy_reviewer");
        persistence.createProject(project3, "accuracy_reviewer");

        Project[] projects = persistence.getProjects(new long[] {
                project1.getId(), project2.getId(), project3.getId()});

        // tests the number
        assertEquals("It should contains 3 elements.", 3, projects.length);
        // tests the accuracy of the project 1
        int i;
        for (i = 0; i < projects.length; i++) {
            if (projects[i].getId() == project1.getId()) {
                break;
            }
        }
        if (i == projects.length) {
            fail("The project is not found by the getProjects method.");
        }
        // test the category id
        assertEquals("The category id is set incorrectly.", 1, projects[i].getProjectCategory().getId());
        // test the status id
        assertEquals("The status id is set incorrectly.", 1, projects[i].getProjectStatus().getId());
        // test the property
        assertEquals("The property is not set properly.", "1_1", projects[i].getProperty("property 1"));
        assertEquals("The property is not set properly.", "1_2", projects[i].getProperty("property 2"));

        // tests the accuracy of the project 2
        for (i = 0; i < projects.length; i++) {
            if (projects[i].getId() == project2.getId()) {
                break;
            }
        }
        if (i == projects.length) {
            fail("The project is not found by the getProjects method.");
        }
        // test the category id
        assertEquals("The category id is set incorrectly.", 2, projects[i].getProjectCategory().getId());
        // test the status id
        assertEquals("The status id is set incorrectly.", 2, projects[i].getProjectStatus().getId());
        // test the property
        assertEquals("The property is not set properly.", "2_2", projects[i].getProperty("property 2"));

        // tests the accuracy of the project 3
        for (i = 0; i < projects.length; i++) {
            if (projects[i].getId() == project3.getId()) {
                break;
            }
        }
        if (i == projects.length) {
            fail("The project is not found by the getProjects method.");
        }
        // test the category id
        assertEquals("The category id is set incorrectly.", 1, projects[i].getProjectCategory().getId());
        // test the status id
        assertEquals("The status id is set incorrectly.", 2, projects[i].getProjectStatus().getId());
        // test the property
        Map properties = projects[i].getAllProperties();
        assertTrue("The properties should be empty.", properties.isEmpty());
    }

    /**
     * Tests method: getProjects(long[]).
     *
     * Tests with mutiple ids and one of which does not exists in the database.
     *
     * @throws Exception to JUnit
     */
    public void testGetProjects_WithNotExistsId() throws Exception {
        // create three different projects
        Project project1 = getProject(1, 1);
        Project project2 = getProject(2, 2);
        Project project3 = getProject(1, 2);
        // set the properties
        project1.setProperty("property 1", "1_1");
        project1.setProperty("property 2", "1_2");
        project2.setProperty("property 2", "2_2");
        // do the persistence
        persistence.createProject(project1, "accuracy_reviewer");
        persistence.createProject(project2, "accuracy_reviewer");
        persistence.createProject(project3, "accuracy_reviewer");

        // use the IDGenerator to generate an id that does not exists in the database
        IDGenerator generator = IDGeneratorFactory.getIDGenerator("project_id_seq");
        long newId = generator.getNextID();
        Project[] projects = persistence.getProjects(new long[] {
                project1.getId(), project2.getId(), project3.getId(), newId});

        // tests the number
        assertEquals("It should contains 3 elements.", 3, projects.length);
        // tests the accuracy of the project 1
        int i;
        for (i = 0; i < projects.length; i++) {
            if (projects[i].getId() == project1.getId()) {
                break;
            }
        }
        if (i == projects.length) {
            fail("The project is not found by the getProjects method.");
        }
        // test the category id
        assertEquals("The category id is set incorrectly.", 1, projects[i].getProjectCategory().getId());
        // test the status id
        assertEquals("The status id is set incorrectly.", 1, projects[i].getProjectStatus().getId());
        // test the property
        assertEquals("The property is not set properly.", "1_1", projects[i].getProperty("property 1"));
        assertEquals("The property is not set properly.", "1_2", projects[i].getProperty("property 2"));

        // tests the accuracy of the project 2
        for (i = 0; i < projects.length; i++) {
            if (projects[i].getId() == project2.getId()) {
                break;
            }
        }
        if (i == projects.length) {
            fail("The project is not found by the getProjects method.");
        }
        // test the category id
        assertEquals("The category id is set incorrectly.", 2, projects[i].getProjectCategory().getId());
        // test the status id
        assertEquals("The status id is set incorrectly.", 2, projects[i].getProjectStatus().getId());
        // test the property
        assertEquals("The property is not set properly.", "2_2", projects[i].getProperty("property 2"));

        // tests the accuracy of the project 3
        for (i = 0; i < projects.length; i++) {
            if (projects[i].getId() == project3.getId()) {
                break;
            }
        }
        if (i == projects.length) {
            fail("The project is not found by the getProjects method.");
        }
        // test the category id
        assertEquals("The category id is set incorrectly.", 1, projects[i].getProjectCategory().getId());
        // test the status id
        assertEquals("The status id is set incorrectly.", 2, projects[i].getProjectStatus().getId());
        // test the property
        Map properties = projects[i].getAllProperties();
        assertTrue("The properties should be empty.", properties.isEmpty());
    }

    /**
     * Testd method: getAllProjectTypes().
     *
     * @throws Exception to JUnit
     */
    public void testGetAllProjectTypes() throws Exception {
        ProjectType[] projectTypes = persistence.getAllProjectTypes();
        // test the number
        assertEquals("The total number of projectTypes is incorrect.", 2, projectTypes.length);
        // test the accuracy
        for (int i = 0; i < projectTypes.length; i++) {
            boolean flag = false;
            if (projectTypes[i].getId() == 1
                    && projectTypes[i].getName().equals("topoder")
                    && projectTypes[i].getDescription().equals("topcoder component")) {
                flag = true;
            } else if (projectTypes[i].getId() == 2
                    && projectTypes[i].getName().equals("custom")
                    && projectTypes[i].getDescription().equals("custom component")) {
                flag = true;
            }
            assertTrue("The type is not set properly.", flag);
        }
    }

    /**
     * Tests method: getAllProjectCategories().
     *
     * @throws Exception to JUnit
     */
    public void testGetAllProjectCategories() throws Exception {
        ProjectCategory[] projectCategories = persistence.getAllProjectCategories();
        // test the number
        assertEquals("The total number of projectCategories is incorrect.", 4, projectCategories.length);
        // test the accuracy
        for (int i = 0; i < projectCategories.length; i++) {
            boolean flag = false;
            if (projectCategories[i].getId() == 1
                    && projectCategories[i].getProjectType().getId() == 1
                    && projectCategories[i].getName().equals(".net")
                    && projectCategories[i].getDescription().equals(".net component")) {
                flag = true;
            } else if (projectCategories[i].getId() == 2
                    && projectCategories[i].getProjectType().getId() == 1
                    && projectCategories[i].getName().equals("java")
                    && projectCategories[i].getDescription().equals("java component")) {
                flag = true;
            } else if (projectCategories[i].getId() == 3
                    && projectCategories[i].getProjectType().getId() == 2
                    && projectCategories[i].getName().equals("custom .net")
                    && projectCategories[i].getDescription().equals("custom .net component")) {
                flag = true;
            } else if (projectCategories[i].getId() == 4
                    && projectCategories[i].getProjectType().getId() == 2
                    && projectCategories[i].getName().equals("custom java")
                    && projectCategories[i].getDescription().equals("custom java component")) {
                flag = true;
            }
            assertTrue("The type is not set properly.", flag);
        }
    }

    /**
     * Tests method: getAllProjectStatuses().
     *
     * @throws Exception to JUnit
     */
    public void testGetAllProjectStatuses() throws Exception {
        ProjectStatus[] projectStatuses = persistence.getAllProjectStatuses();
        // test the number
        assertEquals("The total number of projectStatuses is incorrect.", 7, projectStatuses.length);
        // test the accuracy
        for (int i = 0; i < projectStatuses.length; i++) {
            boolean flag = false;
            if (projectStatuses[i].getId() == 1
                    && projectStatuses[i].getName().equals("Active")
                    && projectStatuses[i].getDescription().equals("Active")) {
                flag = true;
            } else if (projectStatuses[i].getId() == 2
                    && projectStatuses[i].getName().equals("Inactive")
                    && projectStatuses[i].getDescription().equals("Inactive")) {
                flag = true;
            } else if (projectStatuses[i].getId() == 3
                    && projectStatuses[i].getName().equals("Deleted")
                    && projectStatuses[i].getDescription().equals("Deleted")) {
                flag = true;
            } else if (projectStatuses[i].getId() == 4
                    && projectStatuses[i].getName().equals("Cancelled - Failed Review")
                    && projectStatuses[i].getDescription().equals("Cancelled - Failed Review")) {
                flag = true;
            } else if (projectStatuses[i].getId() == 5
                    && projectStatuses[i].getName().equals("Cancelled - Failed Screening")
                    && projectStatuses[i].getDescription().equals("Cancelled - Failed Screening")) {
                flag = true;
            } else if (projectStatuses[i].getId() == 6
                    && projectStatuses[i].getName().equals("Cancelled - Zero Submissions")
                    && projectStatuses[i].getDescription().equals("Cancelled - Zero Submissions")) {
                flag = true;
            } else if (projectStatuses[i].getId() == 7
                    && projectStatuses[i].getName().equals("Completed")
                    && projectStatuses[i].getDescription().equals("Completed")) {
                flag = true;
            }
            assertTrue("The type is not set properly.", flag);
        }
    }

    /**
     * Tests method: getAllProjectPropertyTypes().
     *
     * @throws Exception to JUnit
     */
    public void testGetAllProjectPropertyTypes() throws Exception {
        ProjectPropertyType[] projectPropertyTypes = persistence.getAllProjectPropertyTypes();
        // test the number
        assertEquals("The total number of projectPropertyTypes is incorrect.", 4, projectPropertyTypes.length);
        // test the accuracy
        for (int i = 0; i < projectPropertyTypes.length; i++) {
            boolean flag = false;
            if (projectPropertyTypes[i].getId() == 1
                    && projectPropertyTypes[i].getName().equals("property 1")
                    && projectPropertyTypes[i].getDescription().equals("project property 1")) {
                flag = true;
            } else if (projectPropertyTypes[i].getId() == 2
                    && projectPropertyTypes[i].getName().equals("property 2")
                    && projectPropertyTypes[i].getDescription().equals("project property 2")) {
                flag = true;
            } else if (projectPropertyTypes[i].getId() == 3
                    && projectPropertyTypes[i].getName().equals("property 3")
                    && projectPropertyTypes[i].getDescription().equals("project property 3")) {
                flag = true;
            } else if (projectPropertyTypes[i].getId() == 4
                    && projectPropertyTypes[i].getName().equals("property 4")
                    && projectPropertyTypes[i].getDescription().equals("project property 4")) {
                flag = true;
            }
            assertTrue("The type is not set properly.", flag);
        }
    }

    /**
     * Adds the config files to the ConfigManager.
     *
     * @param fileNames an array of files to be added
     *
     * @throws Exception to JUnit
     */
    private void loadXMLConfigFiles(String[] fileNames) throws Exception {
        for (int i = 0; i < fileNames.length; i++) {
            File file = new File(fileNames[i]);
            cm.add(file.getCanonicalPath());
        }
    }

    /**
     * Clears all the namespaces in the ConfigManager.
     *
     * @throws Exception to JUnit
     */
    private void clearNamespaces() throws Exception {
        for (Iterator it = cm.getAllNamespaces(); it.hasNext();) {
            String ns = (String) it.next();
            if (cm.existsNamespace(ns)) {
                cm.removeNamespace(ns);
            }
        }
    }

    /**
     * Gets a private field from a  specific object via reflection.
     *
     * For tests only.
     *
     * @param instance the object to extract the field
     * @param variableName the name of the field
     *
     * @return the object of the variable
     *
     * @throws Exception to JUnit
     */
    private Object getPrivateObject(Object instance, String variableName) throws Exception {
        Field field = instance.getClass().getDeclaredField(variableName);
        field.setAccessible(true);
        return field.get(instance);
    }

    /**
     * Inserts the records to the tables: project_type_lu, project_category_lu,
     * project_status_lu, id_sequences.
     *
     * @throws Exception to JUnit
     */
    private void insertRecords() throws Exception {
        // get the connection
        Connection con = getConnection();

        // insert to project_type_lu
        String sql = "INSERT INTO project_type_lu (project_type_id, name, description, "
            + "create_user, create_date, modify_user, modify_date) "
            + "VALUES (?, ?, ?, 'topcoder', CURRENT, 'topcoder', CURRENT)";
        doUpdate(con, sql, new Object[] {new Integer(1), "topoder", "topcoder component"});
        doUpdate(con, sql, new Object[] {new Integer(2), "custom", "custom component"});

        // insert to project_category_lu
        sql = "INSERT INTO project_category_lu (project_category_id, project_type_id, name, description, "
            + "create_user, create_date, modify_user, modify_date) "
            + "VALUES (?, ?, ?, ?, 'topcoder', CURRENT, 'topcoder', CURRENT)";
        doUpdate(con, sql, new Object[] {new Integer(1), new Integer(1), ".net", ".net component"});
        doUpdate(con, sql, new Object[] {new Integer(2), new Integer(1), "java", "java component"});
        doUpdate(con, sql, new Object[] {new Integer(3), new Integer(2), "custom .net", "custom .net component"});
        doUpdate(con, sql, new Object[] {new Integer(4), new Integer(2), "custom java", "custom java component"});

        // insert to project_status_lu
        sql = "INSERT INTO project_status_lu (project_status_id, name, description, "
            + "create_user, create_date, modify_user, modify_date) "
            + "VALUES (?, ?, ?, 'System', CURRENT, 'System', CURRENT)";
        doUpdate(con, sql, new Object[] {new Integer(1), "Active", "Active"});
        doUpdate(con, sql, new Object[] {new Integer(2), "Inactive", "Inactive"});
        doUpdate(con, sql, new Object[] {new Integer(3), "Deleted", "Deleted"});
        doUpdate(con, sql, new Object[] {new Integer(4), "Cancelled - Failed Review", "Cancelled - Failed Review"});
        doUpdate(con, sql, new Object[] {new Integer(5),
                "Cancelled - Failed Screening", "Cancelled - Failed Screening"});
        doUpdate(con, sql, new Object[] {new Integer(6),
                "Cancelled - Zero Submissions", "Cancelled - Zero Submissions"});
        doUpdate(con, sql, new Object[] {new Integer(7), "Completed", "Completed"});

        // insert to project_info_type_lu
        sql = "INSERT INTO project_info_type_lu "
            + "(project_info_type_id, name, description, "
            + "create_user, create_date, modify_user, modify_date) "
            + "VALUES (?, ?, ?, 'topcoder', CURRENT, 'topcoder', CURRENT)";

        doUpdate(con, sql, new Object[] {new Integer(1), "property 1", "project property 1"});
        doUpdate(con, sql, new Object[] {new Integer(2), "property 2", "project property 2"});
        doUpdate(con, sql, new Object[] {new Integer(3), "property 3", "project property 3"});
        doUpdate(con, sql, new Object[] {new Integer(4), "property 4", "project property 4"});

        con.commit();
        con.close();
    }

    /**
     * Clears all the tables.
     *
     * @throws Exception to JUnit
     */
    private void clearTables() throws Exception {
        // get the connection
        Connection con = getConnection();
        doUpdate(con, "DELETE FROM project_audit", new Object[] {});
        doUpdate(con, "DELETE FROM project_info", new Object[] {});
        doUpdate(con, "DELETE FROM project_info_type_lu", new Object[] {});
        doUpdate(con, "DELETE FROM project", new Object[] {});
        doUpdate(con, "DELETE FROM project_status_lu", new Object[] {});
        doUpdate(con, "DELETE FROM project_category_lu", new Object[] {});
        doUpdate(con, "DELETE FROM project_type_lu", new Object[] {});
        con.commit();
        con.close();
    }

    /**
     * Does the update operation with a specific sql statement and values.
     *
     * @param con the connection
     * @param sql the sql statement
     * @param values the values to set in the sql statement
     */
    private void doUpdate(Connection con, String sql, Object[] values) {
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(sql);
            for (int i = 0; i < values.length; i++) {
                    setElement(ps, i + 1, values[i]);
            }
            ps.executeUpdate();
        } catch (SQLException e) {
            // ignore
        } finally {
            try {
                ps.close();
            } catch (SQLException e) {
                // ignore
            }
        }
    }

    /**
     * Does the query operation with a specific sql statement and values.
     *
     * @param con the connection
     * @param sql the sql statement
     * @param values the values to set in the sql statement
     *
     * @return the result in the form of ResultSet
     */
    private ResultSet doQuery(Connection con, String sql, Object[] values, PreparedStatement ps) {
        ps = null;
        try {
            ps = con.prepareStatement(sql);
            for (int i = 0; i < values.length; i++) {
                    setElement(ps, i + 1, values[i]);
            }
            return ps.executeQuery();
        } catch (SQLException e) {
            // ignore
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Checks if there are records with the given sql statement.
     *
     * @param con the connection
     * @param sql the sql statement
     * @param values the values to set in the dql statement
     *
     * @return true if the statement exists, otherwise false
     */
    private boolean existsRecords(Connection con, String sql, Object[] values) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = con.prepareStatement(sql);
            rs = doQuery(con, sql, values, ps);
            return rs.next();
        } catch (SQLException e) {
            // ignore
            e.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (SQLException e) {
                // ignore
            }
            try {
                ps.close();
            } catch (SQLException e) {
                // ignore
            }
        }
        return false;
    }
    /**
     * Sets the values to the prepared statement.
     *
     * @param ps the object of PreparedStatement
     * @param index the index of the value
     * @param obj the value
     *
     * @throws SQLException if any error occurs
     */
    private void setElement(PreparedStatement ps, int index, Object obj) throws SQLException {
        if (obj instanceof Integer) {
            ps.setInt(index, ((Integer) obj).intValue());
        } else if (obj instanceof Long) {
            ps.setLong(index, ((Long) obj).longValue());
        } else if (obj instanceof Timestamp) {
            ps.setTimestamp(index, (Timestamp) obj);
        } else if (obj instanceof String) {
            ps.setString(index, (String) obj);
        } else {
            throw new IllegalArgumentException("The type of the obj is not supported.");
        }
    }

    /**
     * Gets a specific project with the given category id and status id.
     *
     * No properties are set in the returned project.
     *
     * @param categoryId the category id
     * @param statusId the status id
     *
     * @return the project with given infomation
     */
    private Project getProject(int categoryId, int statusId) {
        // create the project type, the id is mocked
        ProjectType type = new ProjectType(1, "something");
        // create the project category
        ProjectCategory category = new ProjectCategory(categoryId, "something", type);
        // create the project status
        ProjectStatus status = new ProjectStatus(statusId, "something");
        // create the project without id
        Project project = new Project(category, status);
        return project;
    }

    /**
     * Returns a connection to database via DBConnectionFactory.
     *
     * @return the connection to database
     *
     * @throws Exception to JUnit
     */
    private Connection getConnection() throws Exception {
        Connection con =
            new DBConnectionFactoryImpl(
                    "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl").createConnection();
        con.setAutoCommit(false);
        return con;
    }

    /**
     * Gets the object from the result set with a specific sql statement.
     *
     * @param con the connection
     * @param sql the sql statement
     * @param values the values to be set in the statement
     * @param columnName the column name
     * @param type the type of the column
     *
     * @return the object that statifies the sql query
     */
    private Object getObject(Connection con, String sql, Object[] values, String columnName, Class type) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = con.prepareStatement(sql);
            rs = doQuery(con, sql, values, ps);
            return getElement(rs, columnName, type);
        } catch (SQLException e) {
            // ignore
        } finally {
            try {
                rs.close();
            } catch (SQLException e) {
                // ignore
            }
            try {
                ps.close();
            } catch (SQLException e) {
                // ignore
            }
        }
        return null;
    }

    /**
     * Returns the object.
     *
     * @param rs the instance of ResultSet
     * @param columnName the columnName
     * @param type the type of the column
     * @return an object from the result set
     *
     * @throws SQLException if any error occurs
     */
    private Object getElement(ResultSet rs, String columnName, Class type) throws SQLException {
        if (rs.next() == false) {
            return null;
        }
        if (type.getName().equals(String.class.getName())) {
            return rs.getString(columnName);
        } else if (type.getName().equals(Integer.class.getName())) {
            return new Integer(rs.getInt(columnName));
        } else if (type.getName().equals(Timestamp.class.getName())) {
            return rs.getTimestamp(columnName);
        } else if (type.getName().equals(Long.class.getName())) {
            return new Long(rs.getLong(columnName));
        } else {
            throw new IllegalArgumentException("The type is not supported.");
        }
    }
}
