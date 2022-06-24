/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.clients.dao.accuracytests;

import java.util.List;

import com.topcoder.clients.dao.ejb3.ProjectStatusDAOBean;
import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.Project;
import com.topcoder.clients.model.ProjectStatus;

/**
 * <p>
 * Tests the <code>{@link ProjectStatusDAOBean}</code> for accuracy.
 * </p>
 * 
 * @author cyberjag
 * @version 1.0
 */
public class ProjectStatusDAOAccuracyTests extends BaseTest<ProjectStatusDAOBean, ProjectStatus> {
    /**
     * Tests the <code>{@link ProjectStatusDAOBean#ProjectStatusDAOBean()}</code> for accuracy.
     */
    public void testProjectStatusDAOBean() {
        assertNotNull("Failed to create the bean.", getTestBean());
    }

    /**
     * Tests the <code>{@link ProjectStatusDAOBean#getProjectsWithStatus(ProjectStatus)}</code> for
     * accuracy.
     * 
     * @throws Exception
     *             to junit
     */
    public void testGetProjectsWithStatus() throws Exception {
        Client client = createClient(100);
        createProjectWithClient(1000, client);
        Project project = createProjectWithClient(1001, client);
        List<Project> list = getTestBean().getProjectsWithStatus(project.getProjectStatus());

        assertEquals("Failed to getProjectsWithStatus", list.size(), 2);
    }

    /**
     * Creates the entity specific to this test.
     */
    @Override
    protected void createEntity() {
        ProjectStatus status = createProjectStatus(5);
        setEntity(status);
    }

    /**
     * Creates the EJB specific to this test.
     */
    @Override
    protected void createTestBean() {
        ProjectStatusDAOBean bean = new ProjectStatusDAOBean();
        bean.setEntityManager(getEntityManager());
        setTestBean(bean);
    }

}
