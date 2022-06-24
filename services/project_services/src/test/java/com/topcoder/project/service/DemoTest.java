/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.service;

import java.io.File;
import java.util.Date;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;

import com.topcoder.date.workdays.DefaultWorkdaysFactory;
import com.topcoder.management.project.Project;
import com.topcoder.management.project.ProjectCategory;
import com.topcoder.management.project.ProjectFilterUtility;
import com.topcoder.management.project.ProjectStatus;
import com.topcoder.management.project.ProjectType;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.resource.ResourceRole;
import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.PhaseStatus;
import com.topcoder.project.phases.PhaseType;
import com.topcoder.project.service.ejb.ProjectServicesLocal;
import com.topcoder.project.service.ejb.ProjectServicesRemote;
import com.topcoder.project.service.impl.ProjectServicesImpl;
import com.topcoder.project.service.impl.TestHelper;
import com.topcoder.search.builder.filter.Filter;

import junit.framework.TestCase;

/**
 * <p>
 * This is a demo for demonstrating the usage of this component.
 * </p>
 * <p>
 * <code>ProjectServices</code> is the core class in this component, so this demo will concentrate
 * on usage of this class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.1
 */
public class DemoTest extends TestCase {

    /**
     * <p>
     * Represents an instance of <code>ProjectServices</code>.
     * </p>
     */
    private ProjectServices services;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        TestHelper.clearConfig();
        TestHelper.loadXMLConfig("test_files" + File.separator + "configuration.xml");
        services = new ProjectServicesImpl();
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    protected void tearDown() throws Exception {
        services = null;
        TestHelper.clearConfig();
    }

    /**
     * <p>
     * Demonstrates the usage of this component.
     * </p>
     * <p>
     * In this demo, the active project status id is 1, here is the current state of the system,
     * there are three available projects:
     *
     * <pre>
     *       Project 1: Active, Type=Design
     *                --Resources: 1, 2
     *                --Teams: 1
     *                --Technologies: C#, SQL
     *
     *       Project 2: Active, Type=Development
     *                --Resources: 3, 4, 5, 6
     *                --Teams: 2, 3
     *                --Technologies: C#, XML
     *
     *       Project 3: InActive, Type=Design
     *                --Resources: 1, 5
     *                --Teams: 4
     *                --Technologies: Java, EJB
     * </pre>
     *
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testDemo() throws Exception {
        // Find active project headers
        Project[] activeProjectHeaders = services.findActiveProjectsHeaders();
        // This would return 2 projects: 1, 2

        // Find active projects
        FullProjectData[] activeProjects = services.findActiveProjects();
        // This would return 2 projects: 1, 2.

        // Project 1 would have resources 1 and 2, teams 1, and technologies C# and SQL.
        // Project 2 would have resources 3, 4, 5, and 6, teams 2 and 3, and technologies C# and
        // XML.
        // Find project headers. This would involve using Filters. We will perform a search for all
        // Design projects.
        Filter typeFilter = ProjectFilterUtility.buildTypeNameEqualFilter("Design");
        Project[] projectHeaders = services.findProjectsHeaders(typeFilter);
        // This would return 2 projects: 1, 3

        // The findFullProjects method would work in the same manner, but retrieve the full project
        // data
        // Finally, one can retrieve data for a single project, by project ID
        FullProjectData project = services.getFullProjectData(2);
        // This would return Project 2, and would have resources 3, 4, 5, and 6, teams 2 and 3, and
        // technologies C# and XML.
        // This demo has provided a typical scenario for the usage of the service.

        // =========================since version 1.1========================/
        // create the project using a project category and a project status. project
        // types, categories and statuses are pre-defined in the persistence
        // this component don't add/remove them. The project id
        // will be zero, which mean it isn't persisted
        ProjectType projectType = new ProjectType(1, "projectType");
        ProjectCategory projectCategory = new ProjectCategory(123, "projectCategory", projectType);
        ProjectStatus projectStatus = new ProjectStatus(2, "projectStatus");
        com.topcoder.management.project.Project projectHeader = new com.topcoder.management.project.Project(
                projectCategory, projectStatus);

        // set up a simple project phases with a single phase
        com.topcoder.project.phases.Project projectPhases = new com.topcoder.project.phases.Project(new Date(),
                new DefaultWorkdaysFactory().createWorkdaysInstance());
        PhaseType phaseTypeOne = new PhaseType(1, "one");
        Phase phaseOne = new Phase(projectPhases, 1);
        phaseOne.setPhaseType(phaseTypeOne);
        phaseOne.setFixedStartDate(new Date());
        phaseOne.setPhaseStatus(PhaseStatus.SCHEDULED);

        // Create a resource
        // Note that it is not necessary to set any other field of the
        // resource because they are all optional
        Resource resource = new Resource();
        ResourceRole resourceRole = new ResourceRole();
        resource.setResourceRole(resourceRole);
        Resource[] projectResources = new Resource[] {resource};

        // Persist the project header the project phases and the resource with the "admin"
        // operator
        services.createProject(projectHeader, projectPhases, projectResources, "admin");
        // after creation, new values will be set to the project instance such as
        // project id, creation user, creation date, modification user, modification date.
        // projectHeader will have the id, projectPhases will have the projectHader id, the
        // resources will have the projectHeader id

        // Modify the project header
        ProjectCategory newProjectCategory = new ProjectCategory(456, "newProjectCategory", projectType);
        projectHeader.setProjectCategory(newProjectCategory);

        // Modify the project phases
        PhaseType phaseTypeTwo = new PhaseType(2, "two");
        phaseOne.setPhaseType(phaseTypeTwo);

        // Modify the resource in resources
        ResourceRole newResourceRole = new ResourceRole();
        resource.setResourceRole(newResourceRole);

        // Persist the project's changes
        services.updateProject(projectHeader, "The project is changed", projectPhases, projectResources, "admin");
        // Now the project's header, the project's phases and the resources are changed

        // It's possible to get the ProjectServices instance from factory, the factory is
        // used inside the EJB
        // Get the project services
        ProjectServices projectServ = ProjectServicesFactory.getProjectServices();

        // Get the project services using namespace
        projectServ = ProjectServicesFactory.getProjectServices("MyNamespace");
        // use projectServ
        projectServ.getFullProjectData(1);
    }

    /**
     * <p>
     * Demonstrates the <b>EJB</b> usage of this component.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testEJBUsageDemo() throws Exception {
        // If you are using a standard Java EE
        // component (Servlet, JSP, EJB, or Java EE
        // Application Client), you will not need to set the properties explicitly when
        // creating a JNDI InitialContext , no matter which EJB vendor you are using. That's
        // because the JNDI properties can be configured at deployment time and are applied
        // automatically. A Java EE component would obtain its InitialContext as follows:
        Context jndiContext = new InitialContext();

        // All Java EE components use the same JNDI naming system that enterprise beans use
        // to look up any service. Specifically, they require that EJB references be bound
        // to the "java:comp/env/ejb/" namespace. For example, for a different Java EE
        // component like a servlet, here's all we need to look up the ProjectServices EJB:
        Object ref = jndiContext.lookup("java:/comp/env/ejb/ProjectServicesBean");

        // narrow the Object ref to a ProjectServicesRemote reference (get the Remote
        // interface):
        ProjectServicesRemote remote = (ProjectServicesRemote) PortableRemoteObject.narrow(ref,
                ProjectServicesRemote.class);

        // Now, you can use all methods of the ProjectServices instance
        FullProjectData[] activeProjects = this.services.findActiveProjects();

        // finds active project headers
        com.topcoder.management.project.Project[] projectHeaders = this.services.findActiveProjectsHeaders();

        // gets the full project with specified project id
        FullProjectData fullProjectData = this.services.getFullProjectData(17);

        // creates a project
        Project projectHeader = new Project(1, new ProjectCategory(1, "dev", new ProjectType(1, "Java")),
                new ProjectStatus(1, "submission"));
        com.topcoder.project.phases.Project projectPhases = new com.topcoder.project.phases.Project(new Date(),
                new DefaultWorkdaysFactory().createWorkdaysInstance());
        PhaseType phaseTypeOne = new PhaseType(1, "one");
        Phase phaseOne = new Phase(projectPhases, 1);
        phaseOne.setPhaseType(phaseTypeOne);
        phaseOne.setFixedStartDate(new Date());
        phaseOne.setPhaseStatus(PhaseStatus.SCHEDULED);
        Resource resource = new Resource();
        resource.setResourceRole(new ResourceRole());
        Resource[] projectResources = new Resource[] {resource};
        services.createProject(projectHeader, projectPhases, projectResources, "admin");

        // updates a project
        projectHeader.setProjectCategory(new ProjectCategory(1, "dev", new ProjectType(1, "C#")));
        PhaseType phaseTypeTwo = new PhaseType(2, "two");
        phaseOne.setPhaseType(phaseTypeTwo);
        resource.setResourceRole(new ResourceRole());
        services.updateProject(projectHeader, "The project is changed", projectPhases, projectResources, "admin");

        // If the local or remote ejb method throws an exception then the ejb does not rollback
        // automatically. You must manage this mechanism in the application context

        // In the same mode you can get the local interface
        ref = jndiContext.lookup("java:comp/env/ejb/ProjectServicesLocal");

        // narrow the Object ref to a ProjectServicesLocal reference (get the Local interface):
        ProjectServicesLocal local = (ProjectServicesLocal) PortableRemoteObject.narrow(ref,
                ProjectServicesLocal.class);
        assertNotNull("The local should not be null.", local);
    }
}
