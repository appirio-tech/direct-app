/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.dao.stresstests;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.hibernate.ejb.Ejb3Configuration;

import com.topcoder.clients.dao.ejb3.ClientDAOBean;
import com.topcoder.clients.dao.ejb3.ClientStatusDAOBean;
import com.topcoder.clients.dao.ejb3.CompanyDAOBean;
import com.topcoder.clients.dao.ejb3.ProjectDAOBean;
import com.topcoder.clients.dao.ejb3.ProjectStatusDAOBean;
import com.topcoder.clients.model.AuditableEntity;
import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.ClientStatus;
import com.topcoder.clients.model.Company;
import com.topcoder.clients.model.Project;
import com.topcoder.clients.model.ProjectStatus;
import com.topcoder.util.config.ConfigManager;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

/**
 * <p>
 * Stress test cases for Client Project Entities DAO component.
 * </p>
 *
 * @author biotrail
 * @version 1.0
 */
public class ClientDAOStressTests extends TestCase {
    /**
     * <p>
     * This constant represents the test count used for testing.
     * </p>
     */
    private static final long NUMBER = 10;

    /**
     * <p>
     * This constant represents the current time used for testing.
     * </p>
     */
    private long current = -1;

    /**
     * An EntityManager instance used in tests.
     */
    private static EntityManager entityManager;

    /**
     * The clear table sql.
     */
    private String[] clearSQLs = new String[] {"delete from project", "delete from client",
        "delete from client_status", "delete from project_status", "delete from company"};

    /**
     * <p>Return all tests.</p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(ClientDAOStressTests.class);
    }

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
     * <P>
     * tearDown() routine.
     * </P>
     *
     * @throws Exception
     *                 to JUnit
     */
    protected void tearDown() throws Exception {
        clearConfig();
        clearDatabase();
    }

    /**
     * <p>Tests ClientDAOBean#getProjectsForClient(Client) for stress purpose.</p>
     * @throws Exception to JUnit
     */
    public void testGetProjectsForClient() throws Exception {
        ClientDAOBean instance = new ClientDAOBean();
        instance.setEntityManager(entityManager);
        Client client = createClient(10);
        createProjectWithClient(100, client);

        start();

        for (int i = 0; i < NUMBER; i++) {
            List<Project> projects = instance.getProjectsForClient(client);
            assertEquals("Failed to get projects for client.", 1, projects.size());
            assertEquals("Failed to get projects for client.", 100, projects.get(0).getId());
            assertEquals("Failed to get projects for client.", "createStressUser", projects.get(0).getCreateUsername());
            assertEquals("Failed to get projects for client.", "modifyStressUser", projects.get(0).getModifyUsername());
        }

        printResult("ClientDAOBean#getProjectsForClient(Client)", NUMBER);
    }

    /**
     * <p>Tests ClientStatusDAOBean#getClientsWithStatus(ClientStatus) for stress purpose.</p>
     * @throws Exception to JUnit
     */
    public void testGetClientsWithStatus() throws Exception {
        ClientStatusDAOBean instance = new ClientStatusDAOBean();
        instance.setEntityManager(entityManager);
        Client client = createClient(10);

        start();

        for (int i = 0; i < NUMBER; i++) {

            List<Client> clients = instance.getClientsWithStatus(client.getClientStatus());
            assertEquals("Failed to get clients with status.", 1, clients.size());
            assertEquals("Failed to get clients with status.", 10, clients.get(0).getId());
        }

        printResult("ClientStatusDAOBean#getClientsWithStatus(ClientStatus)", NUMBER);
    }

    /**
     * <p>Tests CompanyDAOBean#getClientsForCompany(Company) for stress purpose.</p>
     * @throws Exception to JUnit
     */
    public void testGetClientsForCompany() throws Exception {
        CompanyDAOBean instance = new CompanyDAOBean();
        instance.setEntityManager(entityManager);
        Client client = createClient(10);

        start();

        for (int i = 0; i < NUMBER; i++) {
            List<Client> clients = instance.getClientsForCompany(client.getCompany());
            assertEquals("Failed to get clients for company.", 1, clients.size());
            assertEquals("Failed to get clients for company.", 10, clients.get(0).getId());
        }

        printResult("CompanyDAOBean#getClientsForCompany(Company)", NUMBER);
    }

    /**
     * <p>Tests ProjectDAOBean#retrieveById(Long, boolean) for stress purpose.</p>
     * @throws Exception to JUnit
     */
    public void testRetrieveById() throws Exception {
        ProjectDAOBean instance = new ProjectDAOBean();
        instance.setEntityManager(entityManager);
        Client client = createClient(10);
        createProjectWithClient(100, client);

        start();

        for (int i = 0; i < NUMBER; i++) {
            Project project = instance.retrieveById(100L, true);
            assertNotNull("Failed to retrieve by id.", project);
            assertEquals("Failed to retrieve by id.", "createStressUser", project.getCreateUsername());
            assertEquals("Failed to retrieve by id.", "modifyStressUser", project.getModifyUsername());
        }

        printResult("ProjectDAOBean#retrieveById(Long, boolean)", NUMBER);
    }

    /**
     * <p>Tests ProjectStatusDAOBean#getProjectsWithStatus(ProjectStatus) for stress purpose.</p>
     * @throws Exception to JUnit
     */
    public void testGetProjectsWithStatus() throws Exception {
        ProjectStatusDAOBean instance = new ProjectStatusDAOBean();
        instance.setEntityManager(entityManager);
        Client client = createClient(10);
        Project porject = createProjectWithClient(100, client);

        start();

        for (int i = 0; i < NUMBER; i++) {
            List<Project> projects = instance.getProjectsWithStatus(porject.getProjectStatus());
            assertEquals("Failed to get projects with status.", 1, projects.size());
        }

        printResult("ProjectStatusDAOBean#getProjectsWithStatus(ProjectStatus)", NUMBER);
    }

    /**
     * <p>
     * Starts to count time.
     * </p>
     */
    private void start() {
        current = System.currentTimeMillis();
    }

    /**
     * <p>
     * Prints test result.
     * </p>
     *
     * @param name the test name
     * @param count the run count
     */
    private void printResult(String name, long count) {
        System.out.println("The test [" + name + "] run " + count + " times, took time: "
            + (System.currentTimeMillis() - current) + " ms");
    }

    /**
     * Get EntityManager.
     * @return EntityManager
     */
    private EntityManager getEntityManager() {
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

        return entityManager;
    }

    /**
     * Remove all the namespace.
     *
     * @throws Exception
     *                 to JUnit
     */
    @SuppressWarnings("unchecked")
    private static void clearConfig() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        Iterator it = cm.getAllNamespaces();

        while (it.hasNext()) {
            cm.removeNamespace((String) it.next());
        }
    }

    /**
     * Clear database.
     */
    private void clearDatabase() {
        EntityManager myEntityManager = getEntityManager();
        for (String sql : clearSQLs) {
            entityManager.getTransaction().begin();
            myEntityManager.createNativeQuery(sql).executeUpdate();
            entityManager.getTransaction().commit();
        }
    }

    /**
     * Create ProjectStatus.
     * @param id the ProjectStatus id
     * @return ProjectStatus created
     */
    private ProjectStatus createProjectStatus(long id) {
        ProjectStatus projectStatus = entityManager.find(ProjectStatus.class, id);
        if (projectStatus != null) {
            return projectStatus;
        }
        projectStatus = new ProjectStatus();
        setAuditableEntity(projectStatus);
        projectStatus.setDescription("des");
        projectStatus.setId(id);

        // persist object
        entityManager.getTransaction().begin();
        entityManager.persist(projectStatus);
        entityManager.getTransaction().commit();
        projectStatus.setId(id);
        return projectStatus;
    }

    /**
     * Create ClientStatus.
     * @param id the ClientStatus id
     * @return ClientStatus created
     */
    private ClientStatus createClientStatus(long id) {
        ClientStatus clientStatus = entityManager.find(ClientStatus.class, id);
        if (clientStatus != null) {
            return clientStatus;
        }
        clientStatus = new ClientStatus();

        setAuditableEntity(clientStatus);
        clientStatus.setDescription("description");
        clientStatus.setId(id);
        // persist object
        entityManager.getTransaction().begin();
        entityManager.persist(clientStatus);
        entityManager.getTransaction().commit();
        clientStatus.setId(id);
        return clientStatus;
    }

    /**
     * Create company.
     * @param id the company id
     * @return company created
     */
    private Company createCompany(long id) {
        Company company = entityManager.find(Company.class, id);
        if (company != null) {
            return company;
        }
        company = new Company();

        setAuditableEntity(company);
        company.setId(id);
        company.setPasscode("passcode");
        // persist object
        entityManager.getTransaction().begin();
        entityManager.persist(company);
        entityManager.getTransaction().commit();
        company.setId(id);
        return company;
    }

    /**
     * Create client.
     * @param id client id
     * @return client created
     */
    private Client createClient(long id) {
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
        entityManager.getTransaction().begin();
        entityManager.persist(client);
        entityManager.getTransaction().commit();
        return client;
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
    private Project createProjectWithClient(long id, Client client) {
        Project project = new Project();
        setAuditableEntity(project);
        project.setActive(true);
        project.setClient(client);
        ProjectStatus projectStatus = createProjectStatus(100000);
        project.setProjectStatus(projectStatus);
        project.setId(id);
        project.setCompany(client.getCompany());

        // persist object
        entityManager.getTransaction().begin();
        entityManager.persist(project);
        entityManager.getTransaction().commit();

        return project;
    }

    /**
     * Set fields of auditableEntity.
     * @param auditableEntity the auditableEntity to set
     */
    private void setAuditableEntity(AuditableEntity auditableEntity) {
        auditableEntity.setCreateUsername("createStressUser");
        auditableEntity.setModifyUsername("modifyStressUser");
        auditableEntity.setCreateDate(new Date());
        auditableEntity.setModifyDate(new Date());
        auditableEntity.setName("name");
    }
}