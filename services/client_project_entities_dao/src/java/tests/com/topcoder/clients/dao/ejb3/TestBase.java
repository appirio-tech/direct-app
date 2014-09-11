/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.dao.ejb3;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.hibernate.ejb.Ejb3Configuration;

import com.topcoder.clients.model.AuditableEntity;
import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.ClientStatus;
import com.topcoder.clients.model.Company;
import com.topcoder.clients.model.Project;
import com.topcoder.clients.model.ProjectStatus;

import junit.framework.TestCase;

/**
 * The super class of all persistence related class.
 *
 * @author TCSDEVELOPER
 * @version 1.1
 */
public abstract class TestBase extends TestCase {
    /**
     * An EntityManager instance used in tests.
     */
    private static EntityManager entityManager;

    /**
     * The clear table sql.
     */
    private String[] clearSQLs = new String[] {"delete from project_contest_fee", "delete from project_manager",
        "delete from user_account", "delete from project", "delete from client", "delete from client_status",
        "delete from project_status", "delete from company"};

    /**
     * <p>
     * setUp() routine.
     * </p>
     *
     * @throws Exception
     *                 to JUnit
     */
    protected void setUp() throws Exception {
        getEntityManager();
        clearDatabase();
    }

    /**
     * <p>
     * tearDown() routine.
     * </p>
     *
     * @throws Exception
     *                 to JUnit
     */
    protected void tearDown() throws Exception {
        TestHelper.clearConfig();
        clearDatabase();
    }

    /**
     * Clear database.
     */
    private void clearDatabase() {
        EntityManager myEntityManager = getEntityManager();
        for (String sql : clearSQLs) {
            myEntityManager.createNativeQuery(sql).executeUpdate();
        }
    }

    /**
     * Get EntityManager.
     * @return EntityManager
     */
    protected EntityManager getEntityManager() {
        if (entityManager == null || !entityManager.isOpen()) {
            // create entityManager
            try {
                Ejb3Configuration cfg = new Ejb3Configuration();
                cfg.configure("hibernate.cfg.xml");

                EntityManagerFactory emf = cfg.buildEntityManagerFactory();
                entityManager = emf.createEntityManager();

            } catch (Exception e) {
            }
        }

        entityManager.clear();
        if (!entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().begin();
        }
        return entityManager;
    }

    /**
     * Create project with client.
     *
     * @param id
     *                the id of project
     * @param client
     *                the client to set
     * @return created project
     */
    protected Project createProjectWithClient(long id, Client client) {
        Project project = new Project();
        setAuditableEntity(project);
        project.setActive(true);
        project.setClient(client);
        ProjectStatus projectStatus = createProjectStatus(100000);
        project.setProjectStatus(projectStatus);
        project.setId(id);
        project.setCompany(client.getCompany());

        // persist object
        Query query = entityManager
                .createNativeQuery("insert into project (project_id, project_status_id, client_id, "
                        + "company_id,name,active,sales_tax,po_box_number,payment_terms_id,"
                        + "description,creation_date,creation_user,modification_date,"
                        + "modification_user,is_deleted,is_manual_prize_setting)"
                        + " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        int idx = 1;
        query.setParameter(idx++, project.getId());
        query.setParameter(idx++, project.getProjectStatus().getId());
        query.setParameter(idx++, project.getClient().getId());
        query.setParameter(idx++, project.getCompany().getId());
        query.setParameter(idx++, project.getName());
        query.setParameter(idx++, project.isActive());
        query.setParameter(idx++, project.getSalesTax());
        query.setParameter(idx++, project.getPOBoxNumber());
        query.setParameter(idx++, project.getPaymentTermsId());
        query.setParameter(idx++, project.getDescription());
        query.setParameter(idx++, project.getCreateDate());
        query.setParameter(idx++, project.getCreateUsername());
        query.setParameter(idx++, project.getModifyDate());
        query.setParameter(idx++, project.getModifyUsername());
        query.setParameter(idx++, 0);
        query.setParameter(idx++, 0);
        query.executeUpdate();

        query = entityManager
                .createNativeQuery("insert into client_project (project_id, client_id) values (?,?)");
        idx = 1;
        query.setParameter(idx++, project.getId());
        query.setParameter(idx++, project.getClient().getId());
        query.executeUpdate();
        
        return project;
    }

    /**
     * Set child project.
     * @param parent the parent id
     * @param child the child id
     */
    protected void setChildProject(long parent, long child) {
        Query query = entityManager
                .createNativeQuery("update project set parent_project_id=? where project_id=?");
        query.setParameter(1, parent);
        query.setParameter(2, child);
        query.executeUpdate();
        entityManager.flush();
    }

    /**
     * Create ProjectStatus.
     * @param id the ProjectStatus id
     * @return ProjectStatus created
     */
    protected ProjectStatus createProjectStatus(long id) {
        ProjectStatus projectStatus = entityManager.find(ProjectStatus.class,
                id);
        if (projectStatus != null) {
            return projectStatus;
        }
        projectStatus = new ProjectStatus();
        setAuditableEntity(projectStatus);
        projectStatus.setDescription("des");
        projectStatus.setId(id);

        // persist object
        entityManager.persist(projectStatus);
        entityManager.flush();
        projectStatus.setId(id);
        return projectStatus;
    }

    /**
     * Create client.
     * @param id client id
     * @return client created
     */
    protected Client createClient(long id) {
        Client client = new Client();
        setAuditableEntity(client);
        ClientStatus clientStatus = createClientStatus(10);
        client.setClientStatus(clientStatus);
        client.setCodeName("codename");
        Company company = createCompany(100);
        client.setCompany(company);
        client.setEndDate(new Date());
        client.setPaymentTermsId(10);
        client.setSalesTax(1.1);
        client.setStartDate(new Date());
        client.setId(id);
        client.setDeleted(false);

        // persist object
        Query query = entityManager
                .createNativeQuery("insert into client "
                        + "(client_id, client_status_id, is_deleted, payment_term_id,company_id"
                        + ",salestax,start_date,end_date,creation_date,creation_user,modification_date,"
                        + "modification_user,code_name) "
                        + "values (?,?,?,?,?,?,?,?,?,?,?,?,?)");
        int idx = 1;
        query.setParameter(idx++, client.getId());
        query.setParameter(idx++, client.getClientStatus().getId());
        query.setParameter(idx++, 0);
        query.setParameter(idx++, 10);
        query.setParameter(idx++, company.getId());
        query.setParameter(idx++, client.getSalesTax());
        query.setParameter(idx++, client.getStartDate());
        query.setParameter(idx++, client.getEndDate());
        query.setParameter(idx++, client.getCreateDate());
        query.setParameter(idx++, client.getCreateUsername());
        query.setParameter(idx++, client.getModifyDate());
        query.setParameter(idx++, client.getModifyUsername());
        query.setParameter(idx, client.getCodeName());
        query.executeUpdate();

        return client;
    }

    /**
     * Create client.
     * @param name the client name
     * @param id client id
     * @return client created
     */
    protected Client createClient(String name, long id) {
        Client client = new Client();
        setAuditableEntity(client);
        ClientStatus clientStatus = createClientStatus(10);
        client.setClientStatus(clientStatus);
        client.setCodeName("codename");
        Company company = createCompany(100);
        client.setCompany(company);
        client.setEndDate(new Date());
        client.setPaymentTermsId(10);
        client.setSalesTax(1.1);
        client.setStartDate(new Date());
        client.setId(id);
        client.setDeleted(false);
        client.setName(name);

        // persist object
        Query query = entityManager
                .createNativeQuery("insert into client "
                        + "(client_id, client_status_id, is_deleted, payment_term_id,company_id"
                        + ",salestax,start_date,end_date,creation_date,creation_user,modification_date,"
                        + "modification_user,code_name, name) "
                        + "values (?,?,?,?,?,?,?,?,?,?,?,?,?, ?)");
        int idx = 1;
        query.setParameter(idx++, client.getId());
        query.setParameter(idx++, client.getClientStatus().getId());
        query.setParameter(idx++, 0);
        query.setParameter(idx++, 10);
        query.setParameter(idx++, company.getId());
        query.setParameter(idx++, client.getSalesTax());
        query.setParameter(idx++, client.getStartDate());
        query.setParameter(idx++, client.getEndDate());
        query.setParameter(idx++, client.getCreateDate());
        query.setParameter(idx++, client.getCreateUsername());
        query.setParameter(idx++, client.getModifyDate());
        query.setParameter(idx++, client.getModifyUsername());
        query.setParameter(idx++, client.getCodeName());
        query.setParameter(idx, client.getName());
        query.executeUpdate();

        return client;
    }

    /**
     * Create company.
     * @param id the company id
     * @return company created
     */
    protected Company createCompany(long id) {
        Company company = entityManager.find(Company.class, id);
        if (company != null) {
            return company;
        }
        company = new Company();

        setAuditableEntity(company);
        company.setId(id);
        company.setPasscode("passcode");

        // persist object
        entityManager.persist(company);
        entityManager.flush();
        company.setId(id);
        return company;
    }

    /**
     * Set fields of auditableEntity.
     * @param auditableEntity the auditableEntity to set
     */
    protected void setAuditableEntity(AuditableEntity auditableEntity) {
        auditableEntity.setCreateUsername("createUser");
        auditableEntity.setModifyUsername("modifyUser");
        auditableEntity.setCreateDate(new Date());
        auditableEntity.setModifyDate(new Date());
        auditableEntity.setName("name");
    }

    /**
     * Create ClientStatus.
     * @param id the ClientStatus id
     * @return ClientStatus created
     */
    protected ClientStatus createClientStatus(long id) {
        ClientStatus clientStatus = entityManager.find(ClientStatus.class, id);
        if (clientStatus != null) {
            return clientStatus;
        }
        clientStatus = new ClientStatus();

        setAuditableEntity(clientStatus);
        clientStatus.setDescription("description");
        clientStatus.setId(id);
        // persist object
        entityManager.persist(clientStatus);
        entityManager.flush();
        clientStatus.setId(id);
        return clientStatus;
    }
}
