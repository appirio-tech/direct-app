/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.clients.dao.accuracytests;

import java.util.List;

import com.topcoder.clients.dao.ejb3.CompanyDAOBean;
import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.Company;
import com.topcoder.clients.model.Project;

/**
 * <p>
 * Tests the <code>{@link CompanyDAOBean}</code> for accuracy.
 * </p>
 * 
 * @author cyberjag
 * @version 1.0
 */
public class CompanyDAOAccuracyTests extends BaseTest<CompanyDAOBean, Company> {

    /**
     * Tests the <code>{@link CompanyDAOBean#CompanyDAOBean()}</code> for accuracy.
     */
    public void testCompanyDAOBean() {
        assertNotNull("Failed to create the bean.", getTestBean());
    }

    /**
     * Tests the <code>{@link CompanyDAOBean#getClientsForCompany(Company)}</code> for accuracy.
     * 
     * @throws Exception
     *             to junit
     */
    public void testGetClientsForCompany() throws Exception {
        Client client = createClient(101);
        List<Client> list = getTestBean().getClientsForCompany(client.getCompany());
        assertEquals("Failed to get the clients for the company", 1, list.size());
        assertEntity(list.get(0), client);
    }

    /**
     * Tests the <code>{@link CompanyDAOBean#getProjectsForCompany(Company)}</code> for accuracy.
     * 
     * @throws Exception
     *             to junit
     */
    public void testGetProjectsForCompany() throws Exception {
        Client client = createClient(101);
        Project project = createProjectWithClient(201, client);
        List<Project> list = getTestBean().getProjectsForCompany(client.getCompany());
        assertEquals("Failed to get the projects for the company.", 1, list.size());
        assertEntity(list.get(0), project);
    }

    /**
     * Creates the entity specific to this test.
     */
    @Override
    protected void createEntity() {
        Company company = createCompany(1001);
        setEntity(company);
    }

    /**
     * Creates the EJB specific to this test.
     */
    @Override
    protected void createTestBean() {
        CompanyDAOBean bean = new CompanyDAOBean();
        bean.setEntityManager(getEntityManager());
        setTestBean(bean);
    }

}
