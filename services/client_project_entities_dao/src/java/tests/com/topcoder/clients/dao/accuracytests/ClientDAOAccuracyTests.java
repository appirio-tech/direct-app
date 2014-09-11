/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.clients.dao.accuracytests;

import java.util.List;

import com.topcoder.clients.dao.ejb3.ClientDAOBean;
import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.Project;

/**
 * <p>
 * Tests the <code>{@link ClientDAOBean}</code> for accuracy.
 * </p>
 * 
 * @author cyberjag
 * @version 1.0
 */
public class ClientDAOAccuracyTests extends BaseTest<ClientDAOBean, Client> {

    /**
     * Tests the <code>{@link ClientDAOBean#ClientDAOBean()}</code>.
     */
    public void testClientDAOBean() {
        assertNotNull("Failed to create the bean.", getTestBean());
    }

    /**
     * Tests the <code>{@link ClientDAOBean#getProjectsForClient(Client)}</code>
     * 
     * @throws Exception
     *             to junit
     */
    public void testGetProjectsForClient() throws Exception {
        createProjectWithClient(201, getEntity());
        createProjectWithClient(202, getEntity());
        List<Project> projects = ((ClientDAOBean) getTestBean()).getProjectsForClient(getEntity());
        assertEquals("Failed to get the projects for the client.", 2, projects.size());
    }

    /**
     * Creates the EJB specific to this test.
     */
    @Override
    protected void createTestBean() {
        ClientDAOBean bean = new ClientDAOBean();
        bean.setEntityManager(getEntityManager());
        setTestBean(bean);
    }

    /**
     * Creates the entity specific to this test.
     */
    @Override
    protected void createEntity() {
        Client client = createClient(101);
        setEntity(client);
    }

    
}
