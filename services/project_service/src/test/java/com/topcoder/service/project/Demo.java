/*
 * Copyright (C) 2008-2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.project;

import java.net.URL;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.ejb.EJBAccessException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.persistence.Query;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;
import javax.xml.ws.soap.SOAPFaultException;

import com.topcoder.security.TCSubject;
import junit.framework.Test;
import junit.framework.TestSuite;

import org.jboss.ws.core.StubExt;

import com.topcoder.service.project.impl.ProjectServiceLocalBridge;

/**
 * <p>
 * This is the demo for this component:
 *   <ul>
 *     <li>Remote EJB;</li>
 *     <li>Local EJB;</li>
 *     <li>WebService client;</li>
 *     <li>Security roles overridden by deployment descriptor;</li>
 *     <li>Manage entity with a J2SE <code>EntityManager</code>.</li>
 *   </ul>
 * </p>
 *
 * <p>
 * Version 1.2 (Release Assembly - TC Cockpit Create Project Refactoring Assembly Part Two Assembly 1.0) Change notes:
 *   <ol>
 *     <li>Updated class to make it compilable.</li>
 *   </ol>
 * </p>
 *
 * @author humblefool, FireIce
 * @author ThinMan, isv
 * @version 1.2
 * @since 1.0
 */
public class Demo extends BaseUnitTestCase {

    /**
     * <p>A <code>String</code> providing the ID for user granted <code>Cockpit User</code> role.</p>
     *
     * @since 1.2
     */
    public static final long USER_ID = 132456L;

    /**
     * <p>A <code>String</code> providing the ID for user granted <code>Cockpit Administrator</code> role.</p>
     *
     * @since 1.2
     */
    public static final long ADMIN_ID = 132458L;

    /**
     * <p>A <code>TCSubject</code> for user account.</p>
     *
     * @since 1.2
     */
    private TCSubject user = new TCSubject(USER_ID);

    /**
     * <p>A <code>TCSubject</code> for admin account.</p>
     *
     * @since 1.2
     */
    private TCSubject admin = new TCSubject(ADMIN_ID);

    /**
     * <p>
     * Represents the <code>InitialContext</code> instance for looking up.
     * </p>
     */
    private InitialContext ctx;

    /**
     * <p>
     * Aggregates all tests in this class.
     * </p>
     *
     * @return test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(Demo.class);
    }

    /**
     * <p>
     * Setup the testing environment.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();

        executeScript("/clean.sql");

    }

    /**
     * <p>
     * Tear down the testing environment.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    @Override
    protected void tearDown() throws Exception {
        if (ctx != null) {
            ctx.close();
        }

        executeScript("/clean.sql");

        super.tearDown();
    }

    /**
     * <p>
     * Demonstrates the usage of manage <code>Project</code> with the
     * JPA <code>EntityManager</code> in J2SE environment.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    @SuppressWarnings("unchecked")
    public void testDemoJPAEntityManager() throws Exception {
        Project project = new Project();
        project.setUserId(1L);
        project.setName("Arena");
        project.setDescription("A new competition arena");
        project.setCreateDate(new Date());
        /* Similarly set the other properties */

        /* We assume DesignCompetition and StudioCompetition are subclasses of Competition */
        Set < Competition > competitions = new HashSet();
        competitions.add(new StudioCompetition("c"));
        competitions.add(new StudioCompetition("c"));
        competitions.add(new SoftwareCompetition());
        for (Competition c : competitions) {
            c.setProject(project);
        }

        project.setCompetitions(competitions);

        /*
         * Create everything. Note that to fully persist the Competition subclasses, custom Hibernate configuration will
         * be required. E.g., set the "cascade" to "all"(See /test_files/META-INF/mapping.xml).
         */
        getEntityTransaction().begin();
        getEntityManager().persist(project);
        getEntityTransaction().commit();

        /* This will create 4 records in the database - 1 in the project table and 3 in the competition table. */
        getEntityManager().clear();
        getEntityTransaction().begin();
        Project created = getEntityManager().find(Project.class, project.getProjectId());

        System.out.println("Size of competitions: " + created.getCompetitions().size());

        /* Similarly we may update, delete, and retrieve by different criteria as shown in the demo above. */

        // Update project
        created.setName("new name");
        created.setModifyDate(new Date());

        getEntityManager().flush();

        // Delete project
        getEntityManager().remove(created);

        getEntityManager().flush();

        // Retrieve by name
        Query query = getEntityManager().createQuery("select p from Project p where p.name=:name");
        query.setParameter("name", "new name");
        List < Project > list = query.getResultList();

        System.out.println("Size of projects: " + list.size());

        getEntityTransaction().commit();
        getEntityManager().clear();
    }

    /**
     * <p>
     * Demonstrates the usage of the Web Service.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDemoWebService() throws Exception {
        ProjectData projectData = new ProjectData();
        projectData.setName("Arena");
        projectData.setDescription("A new competition arena");

        ProjectService service = Service.create(
            new URL("http://127.0.0.1:8080/project_service-project_service/ProjectServiceBean?wsdl"),
            new QName("http://impl.project.service.topcoder.com/", "ProjectServiceBeanService"))
            .getPort(ProjectService.class);

        // Provide the username/password
        URL securityURL = Demo.class.getResource("/jboss-wsse-client.xml");
        ((StubExt) service).setSecurityConfig(securityURL.toURI().toString());
        ((StubExt) service).setConfigName("Standard WSSecurity Client");
        ((BindingProvider) service).getRequestContext().put(BindingProvider.USERNAME_PROPERTY, "username");
        ((BindingProvider) service).getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, "password");

        // Create project
        projectData = service.createProject(user, projectData);

        System.out.println("Project created with id - " + projectData.getProjectId());

        // Get project by id
        service.getProject(user, projectData.getProjectId());

        // Get all projects own by user
        service.getAllProjects();

        projectData.setName("new name");
        projectData.setDescription("new description");

        // Update project
        service.updateProject(user, projectData);

        // Only administrator can get projects for user
        try {
            service.getProjectsForUser(1L);
        } catch (SOAPFaultException e) {
            System.err.println("Only administrator can get projects for user");
        }

        // Delete project
        service.deleteProject(user, projectData.getProjectId());
    }

    /**
     * <p>
     * Demonstrates the usage of the remote EJB.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDemoRemoteEJB() throws Exception {
        ProjectData projectData = new ProjectData();
        projectData.setName("Arena");
        projectData.setDescription("A new competition arena");

        Properties env = new Properties();

        // Specify principal
        env.setProperty(Context.SECURITY_PRINCIPAL, "username");

        // Specify credential
        env.setProperty(Context.SECURITY_CREDENTIALS, "password");

        // The initial factory and provider url are specified in /test_files/jndi.properties

        ctx = new InitialContext(env);

        // Get the remote EJB
        ProjectServiceRemote service = (ProjectServiceRemote) ctx.lookup("remote/ProjectServiceBean");

        // Create project
        projectData = service.createProject(admin, projectData);

        System.out.println("Project created with id - " + projectData.getProjectId());

        // Get project by id
        service.getProject(admin, projectData.getProjectId());

        // Get all projects own by user
        service.getAllProjects();

        projectData.setName("new name");
        projectData.setDescription("new description");

        // Update project
        service.updateProject(admin, projectData);

        // Only administrator can get projects for user
        try {
            service.getProjectsForUser(1L);
        } catch (EJBAccessException e) {
            System.err.println("Only administrator can get projects for user");
        }

        // Delete project
        service.deleteProject(admin, projectData.getProjectId());
    }

    /**
     * <p>
     * Demonstrates the usage of the local EJB.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDemoLocalEJB() throws Exception {
        ProjectData projectData = new ProjectData();
        projectData.setName("Arena");
        projectData.setDescription("A new competition arena");

        Properties env = new Properties();

        // Specify principal
        env.setProperty(Context.SECURITY_PRINCIPAL, "username");

        // Specify credential
        env.setProperty(Context.SECURITY_CREDENTIALS, "password");

        // The initial factory and provider url are specified in /test_files/jndi.properties

        ctx = new InitialContext(env);

        // Get the bridge for local EJB
        ProjectServiceLocalBridge localBridge =
            (ProjectServiceLocalBridge) ctx.lookup("project_service/ProjectServiceLocalBridgeBean/remote");

        // Create project
        projectData = localBridge.createProject(user, projectData);

        System.out.println("Project created with id - " + projectData.getProjectId());

        // Get project by id
        localBridge.getProject(user, projectData.getProjectId());

        // Get all projects own by user
        localBridge.getAllProjects();

        projectData.setName("new name");
        projectData.setDescription("new description");

        // Update project
        localBridge.updateProject(user, projectData);

        // Only administrator can get projects for user
        try {
            localBridge.getProjectsForUser(1L);
        } catch (EJBAccessException e) {
            System.err.println("Only administrator can get projects for user");
        }

        // Delete project
        localBridge.deleteProject(user, projectData.getProjectId());
    }

    /**
     * <p>
     * Demonstrates the usage of the overridden security roles.
     * </p>
     *
     * <p>
     * See the "ProjectServiceBeanRoleOverridden" ejb in /test_files/ejb/ejb-jar.xml.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDemoRolesOverridden() throws Exception {
        ProjectData projectData = new ProjectData();
        projectData.setName("Arena");
        projectData.setDescription("A new competition arena");

        Properties env = new Properties();

        // Specify principal.
        // See /test_files/lib/mock.jar/MockUserGroupManager
        env.setProperty(Context.SECURITY_PRINCIPAL, "user");

        // Specify credential
        env.setProperty(Context.SECURITY_CREDENTIALS, "password");

        // The initial factory and provider url are specified in /test_files/jndi.properties

        ctx = new InitialContext(env);

        // Get the remote EJB
        ProjectServiceRemote service = (ProjectServiceRemote) ctx.lookup(
            "project_service/ProjectServiceBeanRoleOverridden/remote");

        // Create project
        projectData = service.createProject(admin, projectData);

        System.out.println("Project created with id - " + projectData.getProjectId());

        // Get project by id
        service.getProject(admin, projectData.getProjectId());

        // Get all projects own by user
        service.getAllProjects();

        projectData.setName("new name");
        projectData.setDescription("new description");

        // Update project
        service.updateProject(admin, projectData);

        // Only administrator can get projects for user
        try {
            service.getProjectsForUser(1L);
        } catch (EJBAccessException e) {
            System.err.println("Only administrator can get projects for user");
        }

        // Delete project
        service.deleteProject(admin, projectData.getProjectId());
    }
}
