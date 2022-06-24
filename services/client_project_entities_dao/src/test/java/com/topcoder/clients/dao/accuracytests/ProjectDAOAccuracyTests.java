/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.clients.dao.accuracytests;

import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.topcoder.clients.dao.ejb3.ProjectDAOBean;
import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.Project;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.idgenerator.IDGenerator;
import com.topcoder.util.idgenerator.IDGeneratorFactory;

/**
 * <p>
 * Tests the <code>{@link ProjectDAOBean}</code> for accuracy.
 * </p>
 *
 * @author cyberjag
 * @version 1.0
 */
public class ProjectDAOAccuracyTests extends BaseTest<ProjectDAOBean, Project> {


    /**
     * Tests the <code>{@link ProjectDAOBean#ProjectDAOBean()}</code> for accuracy.
     */
    public void testProjectDAOBean() {
        assertNotNull("Failed to create the bean.", getTestBean());
    }

    /**
     * Tests the <code>{@link ProjectDAOBean#retrieveById(Long, boolean)}</code> for accuracy.
     *
     * @throws Exception
     *             to junit
     */
    public void testRetrieveByIdLongBoolean1() throws Exception {
        Client client = createClient(100);
        createProjectWithClient(10, client);
        createProjectWithClient(12, client);
        createProjectWithClient(13, client);

        setChildProject(10, 12);
        setChildProject(10, 13);

        Project res = getTestBean().retrieveById(10L, false);
        assertNotNull("Failed to RetrieveByIdLongBoolean.", res);
        assertNull("Failed to RetrieveByIdLongBoolean. Null child projects expected.", res.getChildProjects());
    }

    /**
     * Tests the <code>{@link ProjectDAOBean#retrieveById(Long, boolean)}</code> for accuracy.
     *
     * @throws Exception
     *             to junit
     */
    public void testRetrieveByIdLongBoolean2() throws Exception {
        Client client = createClient(100);
        createProjectWithClient(10, client);
        createProjectWithClient(12, client);
        createProjectWithClient(13, client);

        setChildProject(10, 12);
        setChildProject(10, 13);

        Project res = getTestBean().retrieveById(10L, true);
        assertEquals("Failed to RetrieveByIdLongBoolean. Child projects expected.", res.getChildProjects()
                .size(), 2);
    }

    /**
     * Set child project.
     *
     * @param parent
     *            the parent id
     * @param child
     *            the child id
     */
    protected void setChildProject(long parent, long child) {
        EntityManager em = getEntityManager();
        if (!em.getTransaction().isActive()) {
            em.getTransaction().begin();
        }
        Query query = em.createNativeQuery("update project set parent_project_id=? where project_id=?");

        query.setParameter(1, parent);
        query.setParameter(2, child);
        query.executeUpdate();

        em.getTransaction().commit();
    }

    /**
     * Tests the <code>{@link ProjectDAOBean#retrieveAll(boolean)}</code> for accuracy.
     *
     * @throws Exception
     *             to junit
     */
    public void testRetrieveAllBoolean1() throws Exception {
        Client client = createClient(100);
        createProjectWithClient(10, client);
        createProjectWithClient(12, client);
        createProjectWithClient(13, client);

        setChildProject(10, 12);
        setChildProject(10, 13);

        List<Project> list = getTestBean().retrieveAll(false);
        // including the one which is created during setup
        assertEquals("Failed to retrieveAll.", list.size(), 4);
        assertNull("Failed to retrieveAll. Null child projects expected.", list.get(0).getChildProjects());
        assertNull("Failed to retrieveAll. Null child projects expected.", list.get(1).getChildProjects());
        assertNull("Failed to retrieveAll. Null child projects expected.", list.get(2).getChildProjects());
    }

    /**
     * Tests the <code>{@link ProjectDAOBean#retrieveAll(boolean)}</code> for accuracy.
     *
     * @throws Exception
     *             to junit
     */
    public void testRetrieveAllBoolean2() throws Exception {
        Client client = createClient(100);
        createProjectWithClient(10, client);
        createProjectWithClient(12, client);
        createProjectWithClient(13, client);

        setChildProject(10, 12);
        setChildProject(10, 13);

        List<Project> list = getTestBean().retrieveAll(true);
        assertEquals("Failed to retrieveAll.", list.size(), 4);
        assertNotNull("Failed to retrieveAll.", list.get(0).getChildProjects());
    }

    /**
     * Creates the entity specific to this test.
     */
    @Override
    protected void createEntity() {
        Client client = createClient(51);
        Project project = createProjectWithClient(1001, client);
        setEntity(project);
    }

    /**
     * Creates the EJB specific to this test.
     */
    @Override
    protected void createTestBean() {
        ProjectDAOBean bean = new ProjectDAOBean();
        bean.setEntityManager(getEntityManager());
        setTestBean(bean);
    }
}
