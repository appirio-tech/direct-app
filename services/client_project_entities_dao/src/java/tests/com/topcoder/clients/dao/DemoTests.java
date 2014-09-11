/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.dao;

import java.util.List;

import javax.naming.InitialContext;

import com.topcoder.clients.dao.ejb3.ProjectDAORemote;
import com.topcoder.clients.dao.ejb3.TestBase;
import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.Project;
import com.topcoder.clients.model.ProjectContestFee;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;

/**
 * The demo of this component.
 *
 * @author Mafy, TCSDEVELOPER
 * @version 1.1
 */
public class DemoTests extends TestBase {
    /**
     * This is demo for this component. For other entities and entity dao are almost the same as Project, the usage of
     * other entities is not showed.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testDemo() throws Exception {
        // prepare data
        Client client = createClient(200);
        Project project = createProjectWithClient(100, client);
        createProjectWithClient(101, client);
        createProjectWithClient(102, client);
        createProjectWithClient(103, client);
        getEntityManager().getTransaction().commit();

        // retrieve bean
        InitialContext ctx = new InitialContext();
        ProjectDAORemote bean = (ProjectDAORemote) ctx.lookup("client_project_entities_dao/ProjectDAOBean/remote");

        Filter filter = new EqualToFilter("projectStatus", project.getProjectStatus().getId());

        List<Project> projects;

        // get project for corresponding id
        Project tempProject = bean.retrieveById(100L);

        // get all projects
        projects = bean.retrieveAll();

        // get all projects with the name "name"
        projects = bean.searchByName("name");

        // get all that match the given filter
        projects = bean.search(filter);

        // save or update a project
        bean.save(project);

        // delete the project
        bean.delete(project);

        // get project for corresponding id without projectChildren
        tempProject = bean.retrieveById(100L, false);

        // get project for corresponding id with projectChildren
        tempProject = bean.retrieveById(100L, true);

        // get all projects without projectChildrens
        projects = bean.retrieveAll(false);

        // get all projects with projectChildrens
        projects = bean.retrieveAll(true);

        // get projects by user
        projects = bean.getProjectsByUser("username");

        // get all projects only
        projects = bean.retrieveAllProjectsOnly();

        // search projects by project name
        projects = bean.searchProjectsByProjectName("projectname");

        // search projects by client name
        projects = bean.searchProjectsByClientName("clientname");

        // get contest fees by project
        List<ProjectContestFee> fees = bean.getContestFeesByProject(100L);

        // save contest fees
        bean.saveContestFees(fees, 100L);

        // check client project permission
        boolean clientProjectPermission = bean.checkClientProjectPermission("username", 100L);

        // check po number permission
        boolean poNumberPermission = bean.checkPoNumberPermission("username", "123456A");

        // add user to billing projects.
        bean.addUserToBillingProjects("username", new long[] {100, 101, 102});

        // remove user from billing projects.
        bean.removeUserFromBillingProjects("ivern", new long[] {100, 201});

        // get the projects by the given client id.
        projects = bean.getProjectsByClientId(200);
    }

}
