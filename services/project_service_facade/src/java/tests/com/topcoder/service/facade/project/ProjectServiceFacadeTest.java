/*
 * Copyright (C) 2008-2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.facade.project;

import com.topcoder.security.TCSubject;
import junit.framework.TestCase;
import junit.framework.Assert;
import com.topcoder.db.connectionfactory.producers.JDBCConnectionProducer;
import com.topcoder.service.project.ProjectData;
import com.topcoder.service.project.AuthorizationFailedFault;
import com.topcoder.service.project.ProjectNotFoundFault;
import com.topcoder.service.project.UserNotFoundFault;
import com.topcoder.service.project.IllegalArgumentFault;

import javax.xml.ws.Service;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.soap.SOAPFaultException;
import javax.xml.namespace.QName;
import java.net.URL;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Properties;
import java.util.HashMap;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileInputStream;

import org.jboss.ws.core.StubExt;

/**
 * <p>A unit test case for <code>Project Service Facade</code> web service.</p>
 *
 * <p>
 * Version 1.1 (Release Assembly - TC Cockpit Create Project Refactoring Assembly Part Two Assembly 1.0) Change notes:
 *   <ol>
 *     <li>Updated class to make it compilable.</li>
 *   </ol>
 * </p>
 *
 * @author isv
 * @version 1.1
 */
public class ProjectServiceFacadeTest extends TestCase {

    /**
     * <p>A <code>String</code> providing the handle for user granted <code>Cockpit User</code> role.</p>
     */
    public static final String USER_HANDLE = "heffan";

    /**
     * <p>A <code>String</code> providing the ID for user granted <code>Cockpit User</code> role.</p>
     */
    public static final long USER_ID = 132456L;

    /**
     * <p>A <code>String</code> providing the handle for user granted <code>Cockpit Administrator</code> role.</p>
     */
    public static final String ADMIN_HANDLE = "user";

    /**
     * <p>A <code>String</code> providing the ID for user granted <code>Cockpit Administrator</code> role.</p>
     */
    public static final long ADMIN_ID = 132458L;

    /**
     * <p>A <code>Connection</code> providing connection to target database.</p>
     */
    private Connection connection = null;

    /**
     * <p>A <code>String</code> providing the URL for <code>WSDL</code> for tested web service.</p>
     */
    private String wsdlUrl = null;

    /**
     * <p>A <code>List</code> providing the list of existing projects for user granted <code>Cockpit User</code> role.
     * </p>
     */
    private List<ProjectData> userProjects = null;

    /**
     * <p>A <code>List</code> providing the list of existing projects for user granted
     * <code>Cockpit Administrator</code> role.</p>
     */
    private List<ProjectData> adminProjects = null;

    /**
     * <p>A <code>TCSubject</code> for user account.</p>
     * 
     * @since 1.1
     */
    private TCSubject user = new TCSubject(USER_ID);

    /**
     * <p>A <code>TCSubject</code> for admin account.</p>
     *
     * @since 1.1
     */
    private TCSubject admin = new TCSubject(ADMIN_ID);

    /**
     * <p>Constructs new <code>TopCoderServiceFacadeTest</code> instance with specified test name.</p>
     *
     * @param name a <code>String</code> providing the name of the test.
     */
    public ProjectServiceFacadeTest(String name) {
        setName(name);
    }

    /**
     * <p>Sets up the fixture. This method is called before a test is executed.</p>
     *
     * @throws Exception if an unexpected error occurs.
     */
    public void setUp() throws Exception {
        super.setUp();
        Properties props = new Properties();
        props.load(new FileInputStream("test_files/test.properties"));

        this.wsdlUrl = props.getProperty("wsdl.url");

        Class.forName(props.getProperty("jdbc.driver"));
        JDBCConnectionProducer connProvider = new JDBCConnectionProducer(props.getProperty("jdbc.url"),
                                                                         props.getProperty("jdbc.username"),
                                                                         props.getProperty("jdbc.password"));
        this.connection = connProvider.createConnection();
        this.connection.setAutoCommit(false);
        runSQL("test_files/sql/clean-up.sql");
        runSQL("test_files/sql/setup.sql");

        this.userProjects = getUserProjects(USER_ID);
        this.adminProjects = getUserProjects(ADMIN_ID);
    }

    /**
     * <p>Tears down the fixture. This method is called after a test is executed.</p>
     *
     * @throws Exception if an unexpected error occurs.
     */
    public void tearDown() throws Exception {
        this.adminProjects = null;
        this.userProjects = null;

        if (this.connection != null) {
            runSQL("test_files/sql/clean-up.sql");
            this.connection.close();
            this.connection = null;
        }
        
        this.wsdlUrl = null;
        super.tearDown();
    }

    /**
     * <p>Accuracy test. Tests the {@link ProjectServiceFacade#getAllProjects(TCSubject)} functionality for producing 
     * accurate results.</p>
     *
     * <p>Authenticates to web service as user granted <code>Cockpit User</code> role and verifies that the service
     * returns projects which are associated with the specified user only.</p>
     *
     * @throws Exception if an unexpected error occurs.
     */
    public void testGetAllProjects_User() throws Exception {
        ProjectServiceFacade client = createWebServiceClient(USER_HANDLE);
        List<ProjectData> expectedProjects = this.userProjects;
        List<ProjectData> actualProjects = client.getAllProjects(user);
        Assert.assertEquals("Wrong number of projects retrieved", expectedProjects.size(), actualProjects.size());
        for (ProjectData expectedProject : expectedProjects) {
            boolean found = false;
            for (ProjectData actualProject : actualProjects) {
                if (actualProject.getProjectId().equals(expectedProject.getProjectId())) {
                    found = true;
                    verifyProjects(expectedProject, actualProject);
                }
            }
            Assert.assertTrue("The expected project is not retrieved", found);
        }
    }

    /**
     * <p>Accuracy test. Tests the {@link ProjectServiceFacade#getAllProjects(TCSubject)} functionality for producing 
     * accurate results.</p>
     *
     * <p>Authenticates to web service as user granted <code>Cockpit Administrator</code> role and verifies that the
     * service returns all existing projects.</p>
     *
     * @throws Exception if an unexpected error occurs.
     */
    public void testGetAllProjects_Admin() throws Exception {
        ProjectServiceFacade client = createWebServiceClient(ADMIN_HANDLE);
        List<ProjectData> expectedProjects = getAllProjects();
        List<ProjectData> actualProjects = client.getAllProjects(admin);
        Assert.assertEquals("Wrong number of projects retrieved", expectedProjects.size(), actualProjects.size());
        for (ProjectData expectedProject : expectedProjects) {
            boolean found = false;
            for (ProjectData actualProject : actualProjects) {
                if (actualProject.getProjectId().equals(expectedProject.getProjectId())) {
                    found = true;
                    verifyProjects(expectedProject, actualProject);
                }
            }
            Assert.assertTrue("The expected project is not retrieved", found);
        }
    }

    /**
     * <p>Accuracy test. Tests the {@link ProjectServiceFacade#getProject(TCSubject, long)} functionality for producing 
     * accurate results.</p>
     *
     * <p>Authenticates to web service as user granted <code>Cockpit User</code> role, passes IDs of projects belonging
     * to user and verifies that the service returns existing project data correctly.</p>
     *
     * @throws Exception if an unexpected error occurs.
     */
    public void testGetProject_User_OwnProjects() throws Exception {
        ProjectServiceFacade client = createWebServiceClient(USER_HANDLE);
        for (ProjectData expectedProject : this.userProjects) {
            ProjectData actualproject = client.getProject(user, expectedProject.getProjectId());
            verifyProjects(expectedProject, actualproject);
        }
    }

    /**
     * <p>Failure test. Tests the {@link ProjectServiceFacade#getProject(TCSubject, long)} functionality for proper
     * handling the case when a call is made for getting details for the project which the user is not granted access 
     * permission for.</p>
     *
     * <p>Authenticates to web service as user granted <code>Cockpit User</code> role, passes IDs of projects belonging
     * to another user and expects the <code>AuthorizationFailedFault</code> to be raised by service.</p>
     *
     * @throws Exception if an unexpected error occurs.
     */
    public void testGetProject_User_AlienProjects() throws Exception {
        ProjectServiceFacade client = createWebServiceClient(USER_HANDLE);
        for (ProjectData expectedProject : this.adminProjects) {
            try {
                client.getProject(user, expectedProject.getProjectId());
                Assert.fail("AuthorizationFailedFault should have been thrown");
            } catch (AuthorizationFailedFault e) {
                // Expected behavior
            }
        }
    }

    /**
     * <p>Failure test. Tests the {@link ProjectServiceFacade#getProject(TCSubject, long)} functionality for proper 
     * handling the case when a call is made for getting details for the non-existing project.</p>
     *
     * <p>Authenticates to web service as user granted <code>Cockpit User</code> role, passes ID of non-existing project
     * and expects the <code>ProjectNotFoundFault</code> to be raised by service.</p>
     *
     * @throws Exception if an unexpected error occurs.
     */
    public void testGetProject_User_NonExistingProject() throws Exception {
        ProjectServiceFacade client = createWebServiceClient(USER_HANDLE);
        try {
            client.getProject(user, -38348348L);
            Assert.fail("ProjectNotFoundFault should have been thrown");
        } catch (ProjectNotFoundFault e) {
            // Expected behavior
        }
    }

    /**
     * <p>Accuracy test. Tests the {@link ProjectServiceFacade#getProject(TCSubject, long)} functionality for producing
     * accurate results.</p>
     *
     * <p>Authenticates to web service as user granted <code>Cockpit Administrator</code> role, passes IDs of projects
     * belonging to user and verifies that the service returns existing project data correctly.</p>
     *
     * @throws Exception if an unexpected error occurs.
     */
    public void testGetProject_Admin_OwnProjects() throws Exception {
        ProjectServiceFacade client = createWebServiceClient(ADMIN_HANDLE);
        for (ProjectData expectedProject : this.adminProjects) {
            ProjectData actualproject = client.getProject(admin, expectedProject.getProjectId());
            verifyProjects(expectedProject, actualproject);
        }
    }

    /**
     * <p>Accuracy test. Tests the {@link ProjectServiceFacade#getProject(TCSubject, long)} functionality for producing
     * accurate results.</p>
     *
     * <p>Authenticates to web service as user granted <code>Cockpit Administrator</code> role, passes IDs of projects
     * belonging to other user and verifies that the service returns existing project data correctly.</p>
     *
     * @throws Exception if an unexpected error occurs.
     */
    public void testGetProject_Admin_AlienProjects() throws Exception {
        ProjectServiceFacade client = createWebServiceClient(ADMIN_HANDLE);
        for (ProjectData expectedProject : this.userProjects) {
            ProjectData actualproject = client.getProject(admin, expectedProject.getProjectId());
            verifyProjects(expectedProject, actualproject);
        }
    }

    /**
     * <p>Failure test. Tests the {@link ProjectServiceFacade#getProject(TCSubject, long)} functionality for proper
     * handling the case when a call is made for getting details for the non-existing project.</p>
     *
     * <p>Authenticates to web service as user granted <code>Cockpit Administrator</code> role, passes ID of
     * non-existing project and expects the <code>ProjectNotFoundFault</code> to be raised by service.</p>
     *
     * @throws Exception if an unexpected error occurs.
     */
    public void testGetProject_Admin_NonExistingProject() throws Exception {
        ProjectServiceFacade client = createWebServiceClient(ADMIN_HANDLE);
        try {
            client.getProject(admin, -8365737L);
            Assert.fail("ProjectNotFoundFault should have been thrown");
        } catch (ProjectNotFoundFault e) {
            // Expected behavior
        }
    }

    /**
     * <p>Failure test. Tests the {@link ProjectServiceFacade#getProjectsForUser(TCSubject, long)} functionality for
     * proper handling the case when a call is made by the user who is not granted <code>Cockpit Administrator</code> 
     * role.</p>
     *
     * <p>Authenticates to web service as user granted <code>Cockpit User</code> role, passes own ID as user ID and
     * expects the <code>SOAPFaultException</code> to be raised by service.</p>
     *
     * @throws Exception if an unexpected error occurs.
     */
    public void testGetProjectsForUser_User_OwnId() throws Exception {
        ProjectServiceFacade client = createWebServiceClient(USER_HANDLE);
        try {
            client.getProjectsForUser(user, USER_ID);
            Assert.fail("SOAPFaultException should have been thrown");
        } catch (SOAPFaultException e) {
            // Expected behavior
        }
    }

    /**
     * <p>Failure test. Tests the {@link ProjectServiceFacade#getProjectsForUser(TCSubject, long)} functionality for
     * proper handling the case when a call is made by the user who is not granted <code>Cockpit Administrator</code>
     * role.</p>
     *
     * <p>Authenticates to web service as user granted <code>Cockpit User</code> role, passes ID of other user and
     * expects the <code>SOAPFaultException</code> to be raised by service.</p>
     *
     * @throws Exception if an unexpected error occurs.
     */
    public void testGetProjectsForUser_User_AlienId() throws Exception {
        ProjectServiceFacade client = createWebServiceClient(USER_HANDLE);
        try {
            client.getProjectsForUser(user, ADMIN_ID);
            Assert.fail("SOAPFaultException should have been thrown");
        } catch (SOAPFaultException e) {
            // Expected behavior
        }
    }

    /**
     * <p>Accuracy test. Tests the {@link ProjectServiceFacade#getProjectsForUser(TCSubject, long)} functionality for 
     * producing accurate results.</p>
     *
     * <p>Authenticates to web service as user granted <code>Cockpit Administrator</code> role, passes own ID and
     * verifies that the service returns existing projects data correctly.</p>
     *
     * @throws Exception if an unexpected error occurs.
     */
    public void testGetProjectsForUser_Admin_OwnId() throws Exception {
        ProjectServiceFacade client = createWebServiceClient(ADMIN_HANDLE);
        List<ProjectData> expectedProjects = this.adminProjects;
        List<ProjectData> actualProjects = client.getProjectsForUser(admin, ADMIN_ID);
        Assert.assertEquals("Wrong number of projects retrieved", expectedProjects.size(), actualProjects.size());
        for (ProjectData expectedProject : expectedProjects) {
            boolean found = false;
            for (ProjectData actualProject : actualProjects) {
                if (actualProject.getProjectId().equals(expectedProject.getProjectId())) {
                    found = true;
                    verifyProjects(expectedProject, actualProject);
                }
            }
            Assert.assertTrue("The expected project is not retrieved", found);
        }
    }

    /**
     * <p>Accuracy test. Tests the {@link ProjectServiceFacade#getProjectsForUser(TCSubject, long)} functionality for
     * producing accurate results.</p>
     *
     * <p>Authenticates to web service as user granted <code>Cockpit Administrator</code> role, passes ID of another
     * user and verifies that the service returns existing projects data correctly.</p>
     *
     * @throws Exception if an unexpected error occurs.
     */
    public void testGetProjectsForUser_Admin_AlienId() throws Exception {
        ProjectServiceFacade client = createWebServiceClient(ADMIN_HANDLE);
        List<ProjectData> expectedProjects = this.userProjects;
        List<ProjectData> actualProjects = client.getProjectsForUser(admin, USER_ID);
        Assert.assertEquals("Wrong number of projects retrieved", expectedProjects.size(), actualProjects.size());
        for (ProjectData expectedProject : expectedProjects) {
            boolean found = false;
            for (ProjectData actualProject : actualProjects) {
                if (actualProject.getProjectId().equals(expectedProject.getProjectId())) {
                    found = true;
                    verifyProjects(expectedProject, actualProject);
                }
            }
            Assert.assertTrue("The expected project is not retrieved", found);
        }
    }

    /**
     * <p>Failure test. Tests the {@link ProjectServiceFacade#getProject(TCSubject, long)} functionality for proper
     * handling the case when a call is made for getting details for the non-existing project.</p>
     *
     * <p>Authenticates to web service as user granted <code>Cockpit Administrator</code> role, passes ID of
     * non-existing user and expects the <code>UserNotFoundFault</code> to be raised by service.</p>
     *
     * @throws Exception if an unexpected error occurs.
     */
    public void testGetProjectsForUser_Admin_NonExistingUser() throws Exception {
        ProjectServiceFacade client = createWebServiceClient(ADMIN_HANDLE);
        try {
            client.getProjectsForUser(admin, -235735L);
            Assert.fail("UserNotFoundFault should have been thrown");
        } catch (UserNotFoundFault e) {
            // Expected behavior
        }
    }

    /**
     * <p>Accuracy test. Tests the {@link ProjectServiceFacade#updateProject(TCSubject, ProjectData)} functionality for
     * accurate behavior.</p>
     *
     * <p>Authenticates to web service as user granted <code>Cockpit User</code> role, passes updated data for projects
     * belonging to user and verifies that the service updates the project data in database correctly.</p>
     *
     * @throws Exception if an unexpected error occurs.
     */
    public void testUpdateProject_User_OwnProjects() throws Exception {
        ProjectServiceFacade client = createWebServiceClient(USER_HANDLE);
        for (ProjectData expectedProject : this.userProjects) {
            expectedProject.setName(expectedProject.getName() + " Updated");
            expectedProject.setDescription(expectedProject.getDescription() + " Updated");

            client.updateProject(user, expectedProject);

            ProjectData actualProject = getProject(expectedProject.getProjectId());
            verifyProjects(expectedProject, actualProject);
        }
    }

    /**
     * <p>Failure test. Tests the {@link ProjectServiceFacade#updateProject(TCSubject, ProjectData)} functionality for
     * proper handling the case when a call is made for updating details for the project which the user is not granted 
     * access permission for.</p>
     *
     * <p>Authenticates to web service as user granted <code>Cockpit User</code> role, passes updated data for projects
     * belonging to another user and expects the <code>AuthorizationFailedFault</code> to be raised by service.</p>
     *
     * @throws Exception if an unexpected error occurs.
     */
    public void testUpdateProject_User_AlienProjects() throws Exception {
        ProjectServiceFacade client = createWebServiceClient(USER_HANDLE);
        for (ProjectData expectedProject : this.adminProjects) {
            expectedProject.setName(expectedProject.getName() + " Updated");
            expectedProject.setDescription(expectedProject.getDescription() + " Updated");
            try {
                client.updateProject(user, expectedProject);
                Assert.fail("AuthorizationFailedFault should have been thrown");
            } catch (AuthorizationFailedFault e) {
                // Expected behavior
            }
        }
    }

    /**
     * <p>Failure test. Tests the {@link ProjectServiceFacade#updateProject(TCSubject, ProjectData)} functionality for
     * proper handling the case when a call is made for updating details for the non-existing project.</p>
     *
     * <p>Authenticates to web service as user granted <code>Cockpit User</code> role, passes updated data for
     * non-existing and expects the <code>ProjectNotFoundFault</code> to be raised by service.</p>
     *
     * @throws Exception if an unexpected error occurs.
     */
    public void testUpdateProject_User_NonExistingProjects() throws Exception {
        ProjectServiceFacade client = createWebServiceClient(USER_HANDLE);
        ProjectData project = new ProjectData();
        project.setProjectId(-229429L);
        project.setName("Updated");
        project.setDescription("Updated");
        try {
            client.updateProject(user, project);
            Assert.fail("ProjectNotFoundFault should have been thrown");
        } catch (ProjectNotFoundFault e) {
            // Expected behavior
        }
    }

    /**
     * <p>Failure test. Tests the {@link ProjectServiceFacade#updateProject(TCSubject, ProjectData)} functionality for
     * proper handling the invalid input.</p>
     *
     * <p>Authenticates to web service as user granted <code>Cockpit User</code> role, passes <code>null</code> as
     * project and expects the <code>IllegalArgumentFault</code> to be raised by service.</p>
     *
     * @throws Exception if an unexpected error occurs.
     */
    public void testUpdateProject_User_NullProject() throws Exception {
        ProjectServiceFacade client = createWebServiceClient(USER_HANDLE);
        try {
            client.updateProject(user, null);
            Assert.fail("IllegalArgumentFault should have been thrown");
        } catch (IllegalArgumentFault e) {
            // Expected behavior
        }
    }

    /**
     * <p>Failure test. Tests the {@link ProjectServiceFacade#updateProject(TCSubject, ProjectData)} functionality for
     * proper handling the invalid input.</p>
     *
     * <p>Authenticates to web service as user granted <code>Cockpit User</code> role, passes project with
     * <code>null</code> name and expects the <code>IllegalArgumentFault</code> to be raised by service.</p>
     *
     * @throws Exception if an unexpected error occurs.
     */
    public void testUpdateProject_User_NullProjectName() throws Exception {
        ProjectServiceFacade client = createWebServiceClient(USER_HANDLE);
        ProjectData project = this.userProjects.get(0);
        project.setName(null);
        project.setDescription("Updated");
        try {
            client.updateProject(user, project);
            Assert.fail("IllegalArgumentFault should have been thrown");
        } catch (IllegalArgumentFault e) {
            // Expected behavior
        }
    }

    /**
     * <p>Failure test. Tests the {@link ProjectServiceFacade#updateProject(TCSubject, ProjectData)} functionality for
     * proper handling the invalid input.</p>
     *
     * <p>Authenticates to web service as user granted <code>Cockpit User</code> role, passes project with
     * empty name and expects the <code>IllegalArgumentFault</code> to be raised by service.</p>
     *
     * @throws Exception if an unexpected error occurs.
     */
    public void testUpdateProject_User_EmptyProjectName() throws Exception {
        ProjectServiceFacade client = createWebServiceClient(USER_HANDLE);
        ProjectData project = this.userProjects.get(0);
        project.setName(" \t \n   \t ");
        project.setDescription("Updated");
        try {
            client.updateProject(user, project);
            Assert.fail("IllegalArgumentFault should have been thrown");
        } catch (IllegalArgumentFault e) {
            // Expected behavior
        }
    }

    /**
     * <p>Accuracy test. Tests the {@link ProjectServiceFacade#updateProject(TCSubject, ProjectData)} functionality for
     * accurate behavior.</p>
     *
     * <p>Authenticates to web service as user granted <code>Cockpit Administrator</code> role, passes updated data for
     * projects belonging to user and verifies that the service updates the project data in database correctly.</p>
     *
     * @throws Exception if an unexpected error occurs.
     */
    public void testUpdateProject_Admin_OwnProjects() throws Exception {
        ProjectServiceFacade client = createWebServiceClient(ADMIN_HANDLE);
        for (ProjectData expectedProject : this.adminProjects) {
            expectedProject.setName(expectedProject.getName() + " Updated");
            expectedProject.setDescription(expectedProject.getDescription() + " Updated");

            client.updateProject(admin, expectedProject);

            ProjectData actualProject = getProject(expectedProject.getProjectId());
            verifyProjects(expectedProject, actualProject);
        }
    }

    /**
     * <p>Accuracy test. Tests the {@link ProjectServiceFacade#updateProject(TCSubject, ProjectData)} functionality for 
     * accurate behavior.</p>
     *
     * <p>Authenticates to web service as user granted <code>Cockpit Administrator</code> role, passes updated data for
     * projects belonging to another user and verifies that the service updates the project data in database correctly.
     * </p>
     *
     * @throws Exception if an unexpected error occurs.
     */
    public void testUpdateProject_Admin_AlienProjects() throws Exception {
        ProjectServiceFacade client = createWebServiceClient(ADMIN_HANDLE);
        for (ProjectData expectedProject : this.userProjects) {
            expectedProject.setName(expectedProject.getName() + " Updated");
            expectedProject.setDescription(expectedProject.getDescription() + " Updated");

            client.updateProject(admin, expectedProject);

            ProjectData actualProject = getProject(expectedProject.getProjectId());
            verifyProjects(expectedProject, actualProject);
        }
    }

    /**
     * <p>Failure test. Tests the {@link ProjectServiceFacade#updateProject(TCSubject, ProjectData)} functionality for 
     * proper handling the case when a call is made for updating details for the non-existing project.</p>
     *
     * <p>Authenticates to web service as user granted <code>Cockpit Administrator</code> role, passes updated data for
     * non-existing and expects the <code>ProjectNotFoundFault</code> to be raised by service.</p>
     *
     * @throws Exception if an unexpected error occurs.
     */
    public void testUpdateProject_Admin_NonExistingProjects() throws Exception {
        ProjectServiceFacade client = createWebServiceClient(ADMIN_HANDLE);
        ProjectData project = new ProjectData();
        project.setProjectId(-229429L);
        project.setName("Updated");
        project.setDescription("Updated");
        try {
            client.updateProject(admin, project);
            Assert.fail("ProjectNotFoundFault should have been thrown");
        } catch (ProjectNotFoundFault e) {
            // Expected behavior
        }
    }

    /**
     * <p>Failure test. Tests the {@link ProjectServiceFacade#updateProject(TCSubject, ProjectData)} functionality for 
     * proper handling the invalid input.</p>
     *
     * <p>Authenticates to web service as user granted <code>Cockpit Administrator</code> role, passes <code>null</code>
     * as project and expects the <code>IllegalArgumentFault</code> to be raised by service.</p>
     *
     * @throws Exception if an unexpected error occurs.
     */
    public void testUpdateProject_Admin_NullProject() throws Exception {
        ProjectServiceFacade client = createWebServiceClient(ADMIN_HANDLE);
        try {
            client.updateProject(admin, null);
            Assert.fail("IllegalArgumentFault should have been thrown");
        } catch (IllegalArgumentFault e) {
            // Expected behavior
        }
    }

    /**
     * <p>Failure test. Tests the {@link ProjectServiceFacade#updateProject(TCSubject, ProjectData)} functionality for 
     * proper handling the invalid input.</p>
     *
     * <p>Authenticates to web service as user granted <code>Cockpit Administrator</code> role, passes project with
     * <code>null</code> name and expects the <code>IllegalArgumentFault</code> to be raised by service.</p>
     *
     * @throws Exception if an unexpected error occurs.
     */
    public void testUpdateProject_Admin_NullProjectName() throws Exception {
        ProjectServiceFacade client = createWebServiceClient(ADMIN_HANDLE);
        ProjectData project = this.adminProjects.get(0);
        project.setName(null);
        project.setDescription("Updated");
        try {
            client.updateProject(admin, project);
            Assert.fail("IllegalArgumentFault should have been thrown");
        } catch (IllegalArgumentFault e) {
            // Expected behavior
        }
    }

    /**
     * <p>Failure test. Tests the {@link ProjectServiceFacade#updateProject(TCSubject, ProjectData)} functionality for 
     * proper handling the invalid input.</p>
     *
     * <p>Authenticates to web service as user granted <code>Cockpit Administrator</code> role, passes project with
     * empty name and expects the <code>IllegalArgumentFault</code> to be raised by service.</p>
     *
     * @throws Exception if an unexpected error occurs.
     */
    public void testUpdateProject_Admin_EmptyProjectName() throws Exception {
        ProjectServiceFacade client = createWebServiceClient(ADMIN_HANDLE);
        ProjectData project = this.adminProjects.get(0);
        project.setName(" \t \n   \t ");
        project.setDescription("Updated");
        try {
            client.updateProject(admin, project);
            Assert.fail("IllegalArgumentFault should have been thrown");
        } catch (IllegalArgumentFault e) {
            // Expected behavior
        }
    }

    /**
     * <p>Accuracy test. Tests the {@link ProjectServiceFacade#createProject(TCSubject, ProjectData)} functionality for 
     * accurate behavior.</p>
     *
     * <p>Authenticates to web service as user granted <code>Cockpit User</code> role, passes valid data for project
     * and verifies that the service creates the project data in database correctly.</p>
     *
     * @throws Exception if an unexpected error occurs.
     */
    public void testCreateProject_User() throws Exception {
        ProjectServiceFacade client = createWebServiceClient(USER_HANDLE);

        ProjectData expectedProject = new ProjectData();
        expectedProject.setName(expectedProject.getName() + " Updated");
        expectedProject.setDescription(expectedProject.getDescription() + " Updated");
        expectedProject = client.createProject(user, expectedProject);

        ProjectData actualProject = getProject(expectedProject.getProjectId());

        verifyProjects(expectedProject, actualProject);

        List<ProjectData> userProjects = getUserProjects(USER_ID);
        boolean found = false;
        for (ProjectData project : userProjects) {
            if (project.getProjectId().equals(expectedProject.getProjectId())) {
                found = true;
                break;
            }
        }
        Assert.assertTrue("The created project is not associated with the current user", found);
    }

    /**
     * <p>Failure test. Tests the {@link ProjectServiceFacade#createProject(TCSubject, ProjectData)} functionality for 
     * proper handling the invalid input.</p>
     *
     * <p>Authenticates to web service as user granted <code>Cockpit User</code> role, passes <code>null</code> as
     * project and expects the <code>IllegalArgumentFault</code> to be raised by service.</p>
     *
     * @throws Exception if an unexpected error occurs.
     */
    public void testCreateProject_User_NullProject() throws Exception {
        ProjectServiceFacade client = createWebServiceClient(USER_HANDLE);
        try {
            client.createProject(user, null);
            Assert.fail("IllegalArgumentFault should have been thrown");
        } catch (IllegalArgumentFault e) {
            // Expected behavior
        }
    }

    /**
     * <p>Failure test. Tests the {@link ProjectServiceFacade#createProject(TCSubject, ProjectData)} functionality for 
     * proper handling the invalid input.</p>
     *
     * <p>Authenticates to web service as user granted <code>Cockpit User</code> role, passes project with
     * <code>null</code> name and expects the <code>IllegalArgumentFault</code> to be raised by service.</p>
     *
     * @throws Exception if an unexpected error occurs.
     */
    public void testCreateProject_User_NullProjectName() throws Exception {
        ProjectServiceFacade client = createWebServiceClient(USER_HANDLE);
        ProjectData project = new ProjectData();
        project.setName(null);
        project.setDescription("Updated");
        try {
            client.createProject(user, project);
            Assert.fail("IllegalArgumentFault should have been thrown");
        } catch (IllegalArgumentFault e) {
            // Expected behavior
        }
    }

    /**
     * <p>Failure test. Tests the {@link ProjectServiceFacade#createProject(TCSubject, ProjectData)} functionality for 
     * proper handling the invalid input.</p>
     *
     * <p>Authenticates to web service as user granted <code>Cockpit User</code> role, passes project with
     * empty name and expects the <code>IllegalArgumentFault</code> to be raised by service.</p>
     *
     * @throws Exception if an unexpected error occurs.
     */
    public void testCreateProject_User_EmptyProjectName() throws Exception {
        ProjectServiceFacade client = createWebServiceClient(USER_HANDLE);
        ProjectData project = new ProjectData();
        project.setName(" \t \n   \t ");
        project.setDescription("Updated");
        try {
            client.createProject(user, project);
            Assert.fail("IllegalArgumentFault should have been thrown");
        } catch (IllegalArgumentFault e) {
            // Expected behavior
        }
    }

    /**
     * <p>Accuracy test. Tests the {@link ProjectServiceFacade#createProject(TCSubject, ProjectData)} functionality for accurate
     * behavior.</p>
     *
     * <p>Authenticates to web service as user granted <code>Cockpit Administrator</code> role, passes valid data for
     * project and verifies that the service creates the project data in database correctly.</p>
     *
     * @throws Exception if an unexpected error occurs.
     */
    public void testCreateProject_Admin() throws Exception {
        ProjectServiceFacade client = createWebServiceClient(ADMIN_HANDLE);

        ProjectData expectedProject = new ProjectData();
        expectedProject.setName(expectedProject.getName() + " Updated");
        expectedProject.setDescription(expectedProject.getDescription() + " Updated");
        expectedProject = client.createProject(admin, expectedProject);

        ProjectData actualProject = getProject(expectedProject.getProjectId());

        verifyProjects(expectedProject, actualProject);

        List<ProjectData> userProjects = getUserProjects(ADMIN_ID);
        boolean found = false;
        for (ProjectData project : userProjects) {
            if (project.getProjectId().equals(expectedProject.getProjectId())) {
                found = true;
                break;
            }
        }
        Assert.assertTrue("The created project is not associated with the current user", found);
    }

    /**
     * <p>Failure test. Tests the {@link ProjectServiceFacade#createProject(TCSubject, ProjectData)} functionality for 
     * proper handling the invalid input.</p>
     *
     * <p>Authenticates to web service as user granted <code>Cockpit Administrator</code> role, passes <code>null</code>
     * as project and expects the <code>IllegalArgumentFault</code> to be raised by service.</p>
     *
     * @throws Exception if an unexpected error occurs.
     */
    public void testCreateProject_Admin_NullProject() throws Exception {
        ProjectServiceFacade client = createWebServiceClient(ADMIN_HANDLE);
        try {
            client.createProject(admin, null);
            Assert.fail("IllegalArgumentFault should have been thrown");
        } catch (IllegalArgumentFault e) {
            // Expected behavior
        }
    }

    /**
     * <p>Failure test. Tests the {@link ProjectServiceFacade#createProject(TCSubject, ProjectData)} functionality for 
     * proper handling the invalid input.</p>
     *
     * <p>Authenticates to web service as user granted <code>Cockpit Administrator</code> role, passes project with
     * <code>null</code> name and expects the <code>IllegalArgumentFault</code> to be raised by service.</p>
     *
     * @throws Exception if an unexpected error occurs.
     */
    public void testCreateProject_Admin_NullProjectName() throws Exception {
        ProjectServiceFacade client = createWebServiceClient(ADMIN_HANDLE);
        ProjectData project = new ProjectData();
        project.setName(null);
        project.setDescription("Updated");
        try {
            client.createProject(admin, project);
            Assert.fail("IllegalArgumentFault should have been thrown");
        } catch (IllegalArgumentFault e) {
            // Expected behavior
        }
    }

    /**
     * <p>Failure test. Tests the {@link ProjectServiceFacade#createProject(TCSubject, ProjectData)} functionality for 
     * proper handling the invalid input.</p>
     *
     * <p>Authenticates to web service as user granted <code>Cockpit Administrator</code> role, passes project with
     * empty name and expects the <code>IllegalArgumentFault</code> to be raised by service.</p>
     *
     * @throws Exception if an unexpected error occurs.
     */
    public void testCreateProject_Admin_EmptyProjectName() throws Exception {
        ProjectServiceFacade client = createWebServiceClient(ADMIN_HANDLE);
        ProjectData project = new ProjectData();
        project.setName(" \t \n   \t ");
        project.setDescription("Updated");
        try {
            client.createProject(admin, project);
            Assert.fail("IllegalArgumentFault should have been thrown");
        } catch (IllegalArgumentFault e) {
            // Expected behavior
        }
    }

    /**
     * <p>Gets the client for <code>Project Service Facade</code> web service authenticated as specified user.</p>
     *
     * @param username a <code>String</code> providing the username to be used for authenticating the client to web
     *        service.
     * @return a <code>TopCoderServiceFacade</code> representing the client interface to web service authenticated as
     *         specified user.
     * @throws Exception if an unexpected error occurs.
     */
    private ProjectServiceFacade createWebServiceClient(String username) throws Exception {
        URL wsdlUrl = new URL(this.wsdlUrl);
        QName serviceName = new QName("http://ejb.project.facade.service.topcoder.com/",
                                      "ProjectServiceFacadeBeanService");
        ProjectServiceFacade service = Service.create(wsdlUrl, serviceName).getPort(ProjectServiceFacade.class);

        StubExt stubExt = (StubExt) service;
        URL securityConfigURL = getClass().getClassLoader().getResource("ws/jboss-wsse-client.xml");
        stubExt.setSecurityConfig(securityConfigURL.toURI().toString());
        stubExt.setConfigName("Standard WSSecurity Client");
        Map<String,Object> context = ((BindingProvider) service).getRequestContext();
        context.put(BindingProvider.USERNAME_PROPERTY, username);
        context.put(BindingProvider.PASSWORD_PROPERTY, "password");

        return service;
    }

    /**
     * <p>Verifies that the specified projects are equal.</p>
     *
     * @param expected a <code>ProjectData</code> providing the expected project data.
     * @param actual a <code>ProjectData</code> providng the actual project data.
     */
    private void verifyProjects(ProjectData expected, ProjectData actual) {
        assertEquals("Project id is invalid", expected.getProjectId(), actual.getProjectId());
        assertEquals("Project name is invalid", expected.getName(), actual.getName());
        assertEquals("Project description is invalid", expected.getDescription(), actual.getDescription());
    }

    /**
     * <p>Checks whether the specified project exists in database or not.</p>
     *
     * @param projectId a <code>long</code> providing the ID of a project.
     * @return <code>true</code> if respective record is found in database table; <code>false</code> otherwise.
     * @throws SQLException if an SQL error occurs.
     */
    private boolean projectExists(long projectId) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = this.connection.prepareStatement("SELECT * FROM tc_direct_project WHERE project_id = ?");
            ps.setLong(1, projectId);
            rs = ps.executeQuery();
            return rs.next();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
        }
    }

    /**
     * <p>Gets the list of projects created by the specified user.</p>
     *
     * @param userId a <code>long</code> providing the ID of a user to get the list of projects for.
     * @return a <code>List</code> of <code>ProjectData</code> providing the details for located projects.
     * @throws SQLException if an SQL error occurs.
     */
    private List<ProjectData> getUserProjects(long userId) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = this.connection.prepareStatement("SELECT * FROM tc_direct_project WHERE user_id = ?");
            ps.setLong(1, userId);
            rs = ps.executeQuery();

            List<ProjectData> result = new ArrayList<ProjectData>();
            while (rs.next()) {
                ProjectData project = new ProjectData();
                project.setName(rs.getString("name"));
                project.setDescription(rs.getString("description"));
                project.setProjectId(rs.getLong("project_id"));
                result.add(project);
            }
            return result;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
        }
    }

    /**
     * <p>Gets the list of all existing projects.</p>
     *
     * @return a <code>List</code> of <code>ProjectData</code> providing the details for all existing projects.
     * @throws SQLException if an SQL error occurs.
     */
    private List<ProjectData> getAllProjects() throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = this.connection.prepareStatement("SELECT * FROM tc_direct_project");
            rs = ps.executeQuery();

            List<ProjectData> result = new ArrayList<ProjectData>();
            while (rs.next()) {
                ProjectData project = new ProjectData();
                project.setName(rs.getString("name"));
                project.setDescription(rs.getString("description"));
                project.setProjectId(rs.getLong("project_id"));
                result.add(project);
            }
            return result;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
        }
    }

    /**
     * <p>Gets the list of projects created by the specified user.</p>
     *
     * @param projectId a <code>long</code> providing the ID of a user to get the list of projects for.
     * @return a <code>List</code> of <code>ProjectData</code> providing the details for located projects.
     * @throws SQLException if an SQL error occurs.
     */
    private ProjectData getProject(long projectId) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = this.connection.prepareStatement("SELECT * FROM tc_direct_project WHERE project_id = ?");
            ps.setLong(1, projectId);
            rs = ps.executeQuery();

            if (rs.next()) {
                ProjectData project = new ProjectData();
                project.setName(rs.getString("name"));
                project.setDescription(rs.getString("description"));
                project.setProjectId(rs.getLong("project_id"));
                return project;
            } else {
                return null;
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
        }
    }

    /**
     * <p>Gets the list of competitions created by the specified user.</p>
     *
     * @param userId a <code>long</code> providing the ID of a user to get the list of competitons for.
     * @return a <code>Map</code> mapping the project IDs to competition IDs. 
     * @throws SQLException if an SQL error occurs.
     */
    private Map<Long, Long> getCompetitions(long userId) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = this.connection.prepareStatement("SELECT c.* FROM competition c, tc_direct_project p "
                                                  + "WHERE c.project_id = p.project_id AND p.user_id = ?");
            ps.setLong(1, userId);
            rs = ps.executeQuery();

            Map<Long, Long> result = new HashMap<Long, Long>();
            while (rs.next()) {
                result.put(rs.getLong("project_id"), rs.getLong("competition_id"));
            }
            return result;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
        }
    }

    /**
     * <p>Executes the SQL statements contained in specicied file.</p>
     *
     * @param fileName a <code>String</code> providing the name of file with SQL statements to be executed.
     * @throws Exception if an unexpected error occurs.
     */
    private void runSQL(String fileName) throws Exception {
        InputStream input = new FileInputStream(fileName);
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));

        String line = null;
        Statement stmt = null;
        try {
            stmt = this.connection.createStatement();
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.startsWith("--") && line.length() > 0 && line.endsWith(";")) {
                    line = line.substring(0, line.length() - 1);
                    stmt.execute(line);
                }
            }
            this.connection.commit();
        } catch (SQLException e) {
            if (line != null) {
                System.err.println("Failed to execute SQL statement: " + line);
            }
            this.connection.rollback();
            throw e;
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            reader.close();
            input.close();
        }
    }
}
