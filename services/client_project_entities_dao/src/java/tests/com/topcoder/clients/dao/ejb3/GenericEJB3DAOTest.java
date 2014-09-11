/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.dao.ejb3;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import com.topcoder.clients.dao.DAOConfigurationException;
import com.topcoder.clients.dao.DAOException;
import com.topcoder.clients.dao.EntityNotFoundException;
import com.topcoder.clients.dao.GenericDAO;
import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.Project;
import com.topcoder.clients.model.ProjectStatus;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;

/**
 * <p>
 * Test class: <code>GenericEJB3DAO</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class GenericEJB3DAOTest extends TestBase {
    /**
     * An EntityManager instance used in tests.
     */
    private EntityManager em;

    /**
     * <p>
     * An instance of <code>GenericEJB3DAO</code> which is tested.
     * </p>
     */
    private GenericEJB3DAO<Project, Long> target = null;

    /**
     * The searchBundleManagerNamespace used in tests.
     */
    private String searchBundleManagerNamespace = "com.topcoder.search.builder";

    /**
     * The configFileName used in tests.
     */
    private String configFileName = "test_files/test.properties";

    /**
     * The configNamespace used in tests.
     */
    private String configNamespace = "CPE_DAO";

    /**
     * <p>
     * setUp() routine.
     * </p>
     *
     * @throws Exception
     *                 to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();

        em = getEntityManager();

        target = new ProjectDAOBean();

        // inject data
        target.setEntityManager(em);
        TestHelper.setPrivateField(GenericEJB3DAO.class, target,
                "searchBundleManagerNamespace", searchBundleManagerNamespace);
        TestHelper.setPrivateField(GenericEJB3DAO.class, target,
                "configFileName", configFileName);
        TestHelper.setPrivateField(GenericEJB3DAO.class, target,
                "configNamespace", configNamespace);

        TestHelper.clearConfig();
        TestHelper.addConfig("config.xml");
        TestHelper.addConfig("hibernateSearchStrategyConfig.xml");
    }

    /**
     * <p>
     * Inheritance test.
     * </p>
     * <p>
     * Verifies <code>GenericEJB3DAO</code> subclasses <code>GenericDAO</code>.
     * </p>
     */
    public void testInheritance1() {
        assertTrue("ClientDAOBean does not subclasses GenericDAO.", target instanceof GenericDAO<?, ?>);
    }

    /**
     * <p>
     * Tests the <code>com.topcoder.clients.dao.ejb3.GenericEJB3DAO()</code>
     * for proper behavior.
     * </p>
     *
     * @throws Exception
     *                 to JUnit
     */
    public void testConstructor() throws Exception {
        Class<Project> type = target.getEntityBeanType();
        assertEquals("The type should be set.", Project.class, type);
    }

    /**
     * <p>
     * Tests the <code>initialize()</code> for proper behavior.
     * </p>
     *
     * @throws Exception
     *                 to JUnit
     */
    public void test_initialize_1() throws Exception {
        target.initialize();
        assertNotNull("searchByFilterUtility should be initialized.",
                TestHelper.getPrivateField(GenericEJB3DAO.class, target,
                        "searchByFilterUtility"));
    }

    /**
     * <p>
     * Tests the <code>initialize()</code> for proper behavior.
     * DAOConfigurationException if the String fields are null or empty.
     * </p>
     *
     * @throws Exception
     *                 to JUnit
     */
    public void test_initialize_failure_1() throws Exception {
        try {
            target = new ProjectDAOBean();
            target.setEntityManager(em);

            TestHelper.setPrivateField(GenericEJB3DAO.class, target,
                    "configFileName", configFileName);
            TestHelper.setPrivateField(GenericEJB3DAO.class, target,
                    "configNamespace", configNamespace);

            target.initialize();
            fail("DAOConfigurationException expected if searchBundleManagerNamespace is null.");
        } catch (DAOConfigurationException e) {
            // ok
        }
    }

    /**
     * <p>
     * Tests the <code>initialize()</code> for proper behavior.
     * DAOConfigurationException if the String fields are null or empty.
     * </p>
     *
     * @throws Exception
     *                 to JUnit
     */
    public void test_initialize_failure_2() throws Exception {
        try {
            target = new ProjectDAOBean();
            target.setEntityManager(em);

            TestHelper.setPrivateField(GenericEJB3DAO.class, target,
                    "searchBundleManagerNamespace", "  ");
            TestHelper.setPrivateField(GenericEJB3DAO.class, target,
                    "configFileName", configFileName);
            TestHelper.setPrivateField(GenericEJB3DAO.class, target,
                    "configNamespace", configNamespace);

            target.initialize();
            fail("DAOConfigurationException expected if searchBundleManagerNamespace is empty.");
        } catch (DAOConfigurationException e) {
            // ok
        }
    }

    /**
     * <p>
     * Tests the <code>initialize()</code> for proper behavior.
     * DAOConfigurationException if the String fields are null or empty.
     * </p>
     *
     * @throws Exception
     *                 to JUnit
     */
    public void test_initialize_failure_3() throws Exception {
        try {
            target = new ProjectDAOBean();
            target.setEntityManager(em);

            TestHelper.setPrivateField(GenericEJB3DAO.class, target,
                    "searchBundleManagerNamespace",
                    searchBundleManagerNamespace);
            TestHelper.setPrivateField(GenericEJB3DAO.class, target,
                    "configNamespace", configNamespace);

            target.initialize();
            fail("DAOConfigurationException expected if configFileName is null.");
        } catch (DAOConfigurationException e) {
            // ok
        }
    }

    /**
     * <p>
     * Tests the <code>initialize()</code> for proper behavior.
     * DAOConfigurationException if the String fields are null or empty.
     * </p>
     *
     * @throws Exception
     *                 to JUnit
     */
    public void test_initialize_failure_4() throws Exception {
        try {
            target = new ProjectDAOBean();
            target.setEntityManager(em);

            TestHelper.setPrivateField(GenericEJB3DAO.class, target,
                    "searchBundleManagerNamespace",
                    searchBundleManagerNamespace);
            TestHelper.setPrivateField(GenericEJB3DAO.class, target,
                    "configFileName", "   ");
            TestHelper.setPrivateField(GenericEJB3DAO.class, target,
                    "configNamespace", configNamespace);

            target.initialize();
            fail("DAOConfigurationException expected if configFileName is empty.");
        } catch (DAOConfigurationException e) {
            // ok
        }
    }

    /**
     * <p>
     * Tests the <code>initialize()</code> for proper behavior.
     * DAOConfigurationException if the String fields are null or empty.
     * </p>
     *
     * @throws Exception
     *                 to JUnit
     */
    public void test_initialize_failure_5() throws Exception {
        try {
            target = new ProjectDAOBean();
            target.setEntityManager(em);

            TestHelper.setPrivateField(GenericEJB3DAO.class, target,
                    "searchBundleManagerNamespace",
                    searchBundleManagerNamespace);
            TestHelper.setPrivateField(GenericEJB3DAO.class, target,
                    "configFileName", configFileName);

            target.initialize();
            fail("DAOConfigurationException expected if configNamespace is null.");
        } catch (DAOConfigurationException e) {
            // ok
        }
    }

    /**
     * <p>
     * Tests the <code>initialize()</code> for proper behavior.
     * DAOConfigurationException if the String fields are null or empty.
     * </p>
     *
     * @throws Exception
     *                 to JUnit
     */
    public void test_initialize_failure_6() throws Exception {
        try {
            target = new ProjectDAOBean();
            target.setEntityManager(em);

            TestHelper.setPrivateField(GenericEJB3DAO.class, target,
                    "searchBundleManagerNamespace",
                    searchBundleManagerNamespace);
            TestHelper.setPrivateField(GenericEJB3DAO.class, target,
                    "configFileName", configFileName);
            TestHelper.setPrivateField(GenericEJB3DAO.class, target,
                    "configNamespace", "   ");

            target.initialize();
            fail("DAOConfigurationException expected if configNamespace is empty.");
        } catch (DAOConfigurationException e) {
            // ok
        }
    }

    /**
     * <p>
     * Tests the <code>initialize()</code> for proper behavior.
     * DAOConfigurationException if needed configurations are missing.
     * </p>
     *
     * @throws Exception
     *                 to JUnit
     */
    public void test_initialize_failure_7() throws Exception {
        try {
            target = new ProjectDAOBean();
            target.setEntityManager(em);

            TestHelper.setPrivateField(GenericEJB3DAO.class, target,
                    "searchBundleManagerNamespace",
                    searchBundleManagerNamespace + "invalid");
            TestHelper.setPrivateField(GenericEJB3DAO.class, target,
                    "configFileName", configFileName);
            TestHelper.setPrivateField(GenericEJB3DAO.class, target,
                    "configNamespace", configNamespace);

            target.initialize();
            fail("DAOConfigurationException expected.");
        } catch (DAOConfigurationException e) {
            // ok
        }
    }

    /**
     * <p>
     * Tests the <code>initialize()</code> for proper behavior.
     * DAOConfigurationException if needed configurations are missing.
     * </p>
     *
     * @throws Exception
     *                 to JUnit
     */
    public void test_initialize_failure_8() throws Exception {
        try {
            target = new ProjectDAOBean();
            target.setEntityManager(em);

            TestHelper.setPrivateField(GenericEJB3DAO.class, target,
                    "searchBundleManagerNamespace",
                    searchBundleManagerNamespace);
            TestHelper.setPrivateField(GenericEJB3DAO.class, target,
                    "configFileName", configFileName + "invalid");
            TestHelper.setPrivateField(GenericEJB3DAO.class, target,
                    "configNamespace", configNamespace);

            target.initialize();
            fail("DAOConfigurationException expected.");
        } catch (DAOConfigurationException e) {
            // ok
        }
    }

    /**
     * <p>
     * Tests the <code>initialize()</code> for proper behavior.
     * DAOConfigurationException if needed configurations are missing.
     * </p>
     *
     * @throws Exception
     *                 to JUnit
     */
    public void test_initialize_failure_9() throws Exception {
        try {
            target = new ProjectDAOBean();
            target.setEntityManager(em);

            TestHelper.setPrivateField(GenericEJB3DAO.class, target,
                    "searchBundleManagerNamespace",
                    searchBundleManagerNamespace);
            TestHelper.setPrivateField(GenericEJB3DAO.class, target,
                    "configFileName", configFileName);
            TestHelper.setPrivateField(GenericEJB3DAO.class, target,
                    "configNamespace", configNamespace + "invalid");

            target.initialize();
            fail("DAOConfigurationException expected.");
        } catch (DAOConfigurationException e) {
            // ok
        }
    }

    /**
     * <p>
     * Tests the <code>initialize()</code> for proper behavior.
     * DAOConfigurationException if entityManager is invalid (invalid means null
     * here).
     * </p>
     *
     * @throws Exception
     *                 to JUnit
     */
    public void test_initialize_failure_10() throws Exception {
        try {
            target = new ProjectDAOBean();

            TestHelper.setPrivateField(GenericEJB3DAO.class, target,
                    "searchBundleManagerNamespace",
                    searchBundleManagerNamespace);
            TestHelper.setPrivateField(GenericEJB3DAO.class, target,
                    "configFileName", configFileName);
            TestHelper.setPrivateField(GenericEJB3DAO.class, target,
                    "configNamespace", configNamespace);

            target.initialize();
            fail("DAOConfigurationException expected.");
        } catch (DAOConfigurationException e) {
            // ok
        }
    }

    /**
     * <p>
     * Tests the <code>setEntityManager(EntityManager)</code> for proper
     * behavior.
     *
     * </p>
     *
     * @throws Exception
     *                 to JUnit
     */
    public void test_setEntityManager_1() throws Exception {
        target = new ProjectDAOBean();
        target.setEntityManager(em);
        assertEquals("entityManager", em, target.getEntityManager());
    }

    /**
     * <p>
     * Tests the <code>getEntityManager()</code> for proper behavior.
     *
     * </p>
     *
     * @throws Exception
     *                 to JUnit
     */
    public void test_getEntityManager_1() throws Exception {
        target = new ProjectDAOBean();
        target.setEntityManager(em);
        assertEquals("entityManager", em, target.getEntityManager());
    }

    /**
     * <p>
     * Tests the <code>getEntityBeanType()</code> for proper behavior.
     *
     * </p>
     *
     * @throws Exception
     *                 to JUnit
     */
    public void test_getEntityBeanType_1() throws Exception {
        target.initialize();
        assertEquals("EntityBeanType", Project.class, target
                .getEntityBeanType());
    }

    /**
     * <p>
     * Tests the <code>flush()</code> for proper behavior.
     *
     * </p>
     *
     * @throws Exception
     *                 to JUnit
     */
    public void test_flush_1() throws Exception {
        target.initialize();
        target.flush();
        // no exception is ok.
    }

    /**
     * <p>
     * Tests the <code>clear()</code> for proper behavior.
     *
     * </p>
     *
     * @throws Exception
     *                 to JUnit
     */
    public void test_clear_1() throws Exception {
        target.initialize();
        target.clear();
        // no exception is ok.
    }

    /**
     * <p>
     * Tests the <code>retrieveById(Id id)</code> for proper behavior.
     *
     * </p>
     *
     * @throws Exception
     *                 to JUnit
     */
    public void test_retrieveById_1() throws Exception {
        target.initialize();

        // prepare data
        long id = 100;
        Client client = createClient(200);
        createProjectWithClient(id, client);
        Project project = (Project) target.retrieveById(id);

        // check result
        assertEquals("project id", id, project.getId());
        assertEquals("client id", client.getId(), project.getClient().getId());
    }

    /**
     * <p>
     * Tests the <code>retrieveById(Id id)</code> for proper behavior.
     * IllegalArgumentException if id <= 0 or id is null.
     *
     * </p>
     *
     * @throws Exception
     *                 to JUnit
     */
    public void test_retrieveById_failure_1() throws Exception {
        target.initialize();

        // prepare data
        long id = 100;
        Client client = createClient(200);
        createProjectWithClient(id, client);

        try {
            target.retrieveById(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Tests the <code>retrieveById(Id id)</code> for proper behavior.
     * IllegalArgumentException if id <= 0 or id is null.
     *
     * </p>
     *
     * @throws Exception
     *                 to JUnit
     */
    public void test_retrieveById_failure_2() throws Exception {
        target.initialize();

        // prepare data
        long id = 100;
        Client client = createClient(200);
        createProjectWithClient(id, client);

        try {
            target.retrieveById(0L);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Tests the <code>retrieveById(Id id)</code> for proper behavior.
     * IllegalArgumentException if id <= 0 or id is null.
     *
     * </p>
     *
     * @throws Exception
     *                 to JUnit
     */
    public void test_retrieveById_failure_3() throws Exception {
        target.initialize();

        // prepare data
        long id = 100;
        Client client = createClient(200L);
        createProjectWithClient(id, client);

        try {
            target.retrieveById(-1L);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Tests the <code>retrieveById(Id id)</code> for proper behavior.
     * EntityNotFoundException if an entity for the given id is not found in the
     * persistence.
     *
     * </p>
     *
     * @throws Exception
     *                 to JUnit
     */
    public void test_retrieveById_failure_4() throws Exception {
        target.initialize();

        // prepare data
        long id = 100;
        Client client = createClient(200L);
        createProjectWithClient(id, client);

        try {
            target.retrieveById(101L);
            fail("EntityNotFoundException expected.");
        } catch (EntityNotFoundException e) {
            // ok
        }
    }

    /**
     * <p>
     * Tests the <code>retrieveById(Id id)</code> for proper behavior.
     * DAOConfigurationException if the configured entityManager is invalid
     * (invalid means null here).
     *
     * </p>
     *
     * @throws Exception
     *                 to JUnit
     */
    public void test_retrieveById_failure_5() throws Exception {
        target = new ProjectDAOBean();

        // prepare data
        long id = 100;
        Client client = createClient(200L);
        createProjectWithClient(id, client);

        try {
            target.retrieveById(100L);
            fail("DAOConfigurationException expected.");
        } catch (DAOConfigurationException e) {
            // ok
        }
    }

    /**
     * <p>
     * Tests the <code>retrieveAll()</code> for proper behavior.
     * </p>
     *
     * @throws Exception
     *                 to JUnit
     */
    public void test_retrieveAll_1() throws Exception {
        target.initialize();

        // prepare data
        long id = 100;
        Client client = createClient(200L);
        createProjectWithClient(id++, client);
        createProjectWithClient(id++, client);
        createProjectWithClient(id++, client);
        createProjectWithClient(id, client);
        EntityManager entityManager = getEntityManager();
        entityManager.createNativeQuery("update project set is_deleted=" + 1 + " where project_id=" + id)
                .executeUpdate();

        List<Project> res = target.retrieveAll();
        assertEquals("Only not deleted project should be returned.", 3, res.size());
        // verify data
        List<Long> ids = new ArrayList<Long>();
        ids.add(res.get(0).getId());
        ids.add(res.get(1).getId());
        ids.add(res.get(1).getId());
        assertTrue("should be returned with correct id", ids.contains(100L));
        assertTrue("should be returned with correct id", ids.contains(101L));
        assertTrue("should be returned with correct id", ids.contains(101L));
    }

    /**
     * <p>
     * Tests the <code>retrieveAll()</code> for proper behavior.
     * DAOConfigurationException if the configured entityManager is invalid
     * (invalid means null here).
     * </p>
     *
     * @throws Exception
     *                 to JUnit
     */
    public void test_retrieveAll_failure_1() throws Exception {
        target = new ProjectDAOBean();

        // prepare data
        long id = 100;
        Client client = createClient(200L);
        createProjectWithClient(id++, client);
        createProjectWithClient(id++, client);
        createProjectWithClient(id++, client);
        createProjectWithClient(id, client);
        try {
            target.retrieveAll();
            fail("DAOConfigurationException expected.");
        } catch (DAOConfigurationException e) {
            // ok
        }
    }

    /**
     * <p>
     * Tests the <code>searchByName(String name)</code> for proper behavior.
     * </p>
     *
     * @throws Exception
     *                 to JUnit
     */
    public void test_searchByName_1() throws Exception {
        target.initialize();

        // prepare data
        long id = 100;
        Client client = createClient(200L);
        Project project = createProjectWithClient(id++, client);
        createProjectWithClient(id++, client);
        createProjectWithClient(id++, client);
        createProjectWithClient(id, client);
        EntityManager entityManager = getEntityManager();
        entityManager.createNativeQuery("update project set is_deleted=" + 1 + " where project_id=" + id)
                .executeUpdate();

        List<Project> res = target.searchByName(project.getName());
        assertEquals("Only not deleted project should be returned.", 3, res.size());
        // verify data
        List<Long> ids = new ArrayList<Long>();
        ids.add(res.get(0).getId());
        ids.add(res.get(1).getId());
        ids.add(res.get(1).getId());
        assertTrue("should be returned with correct id", ids.contains(100L));
        assertTrue("should be returned with correct id", ids.contains(101L));
        assertTrue("should be returned with correct id", ids.contains(101L));
    }

    /**
     * <p>
     * Tests the <code>searchByName(String name)</code> for proper behavior.
     * IllegalArgumentException if name is null or empty string.
     * </p>
     *
     * @throws Exception
     *                 to JUnit
     */
    public void test_searchByName_failure_1() throws Exception {
        target.initialize();

        try {
            target.searchByName(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Tests the <code>searchByName(String name)</code> for proper behavior.
     * IllegalArgumentException if name is null or empty string.
     * </p>
     *
     * @throws Exception
     *                 to JUnit
     */
    public void test_searchByName_failure_2() throws Exception {
        target.initialize();

        try {
            target.searchByName("  ");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Tests the <code>searchByName(String name)</code> for proper behavior.
     * IllegalArgumentException if name is null or empty string.
     * </p>
     *
     * @throws Exception
     *                 to JUnit
     */
    public void test_searchByName_failure_3() throws Exception {
        target.initialize();

        try {
            target.searchByName("\t");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Tests the <code>searchByName(String name)</code> for proper behavior.
     * IllegalArgumentException if name is null or empty string.
     * </p>
     *
     * @throws Exception
     *                 to JUnit
     */
    public void test_searchByName_failure_4() throws Exception {
        target.initialize();

        try {
            target.searchByName("\n");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Tests the <code>searchByName(String name)</code> for proper behavior.
     * DAOConfigurationException if the configured entityManager is invalid
     * (invalid means null here).
     * </p>
     *
     * @throws Exception
     *                 to JUnit
     */
    public void test_searchByName_failure_5() throws Exception {
        target = new ProjectDAOBean();

        try {
            target.searchByName("ab");
            fail("DAOConfigurationException expected.");
        } catch (DAOConfigurationException e) {
            // ok
        }
    }

    /**
     * <p>
     * Tests the <code>search(Filter filter)</code> for proper behavior.
     * </p>
     *
     * @throws Exception
     *                 to JUnit
     */
    public void test_search_1() throws Exception {
        target.initialize();

        // prepare data
        Client client = createClient(11);
        Project project = createProjectWithClient(110, client);
        createProjectWithClient(111, client);
        createProjectWithClient(112, client);
        createProjectWithClient(113, client);
        ProjectStatus status = createProjectStatus(project.getProjectStatus().getId() + 1);
        EntityManager entityManager = getEntityManager();
        entityManager.createNativeQuery(
                "update project set project_status_id=" + status.getId() + " where project_id=" + 110).executeUpdate();
        entityManager.createNativeQuery("update project set is_deleted=" + 1 + " where project_id=" + 111)
                .executeUpdate();
        entityManager.getTransaction().commit();

        // do search
        Filter filter = new EqualToFilter("projectStatus", status.getId() - 1);
        List<Project> res = target.search(filter);
        assertEquals("The number of search result", 2, res.size());

        // verify data
        List<Long> ids = new ArrayList<Long>();
        ids.add(res.get(0).getId());
        ids.add(res.get(1).getId());
        assertTrue("should be returned with correct id", ids.contains(112L));
        assertTrue("should be returned with correct id", ids.contains(113L));
    }

    /**
     * <p>
     * Tests the <code>search(Filter filter)</code> for proper behavior.
     * IllegalArgumentException if filter is null.
     * </p>
     *
     * @throws Exception
     *                 to JUnit
     */
    public void test_search_failure_1() throws Exception {
        try {
            target.search(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Tests the <code>search(Filter filter)</code> for proper behavior.
     * DAOException if any error occurs while performing this operation.
     * </p>
     *
     * @throws Exception
     *                 to JUnit
     */
    public void test_search_failure_2() throws Exception {
        target.initialize();

        em.getTransaction().commit();

        // do search
        Filter filter = new EqualToFilter("projectStatusxxx", 1);
        try {
            target.search(filter);
            fail("DAOException expected.");
        } catch (DAOException e) {
            // ok
        }
    }

    /**
     * <p>
     * Tests the <code>save(T entity)</code> for proper behavior.
     * </p>
     *
     * @throws Exception
     *                 to JUnit
     */
    public void test_save_1() throws Exception {
        target.initialize();

        // prepare data
        Client client = createClient(11);
        Project project = createProjectWithClient(110, client);
        String name = "new name";
        project.setName(name);
        EntityManager entityManager = getEntityManager();

        Project res = target.save(project);
        entityManager.getTransaction().commit();
        assertEquals("should be saved.", name, res.getName());
    }

    /**
     * <p>
     * Tests the <code>save(T entity)</code> for proper behavior.
     * IllegalArgumentException if entity is null.
     * </p>
     *
     * @throws Exception
     *                 to JUnit
     */
    public void test_save_failure_1() throws Exception {
        target.initialize();

        try {
            target.save(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Tests the <code>save(T entity)</code> for proper behavior.
     * DAOConfigurationException if the configured entityManager is invalid
     * (invalid means null here).
     * </p>
     *
     * @throws Exception
     *                 to JUnit
     */
    public void test_save_failure_2() throws Exception {
        target = new ProjectDAOBean();

        // prepare data
        Client client = createClient(11);
        Project project = createProjectWithClient(110, client);
        project.setName(project.getName() + "2");

        try {
            target.save(project);
            fail("DAOConfigurationException expected.");
        } catch (DAOConfigurationException e) {
            // ok
        }
    }

    /**
     * <p>
     * Tests the <code>delete(T entity)</code> for proper behavior.
     * </p>
     *
     * @throws Exception
     *                 to JUnit
     */
    @SuppressWarnings("unchecked")
    public void test_delete_1() throws Exception {
        target.initialize();

        // prepare data
        Client client = createClient(11);
        Project project = createProjectWithClient(110, client);

        target.delete(project);
        em.getTransaction().commit();
        List<Project> res = em.createNativeQuery(
                "select * from project where project_id = 110 and is_deleted=1")
                .getResultList();
        assertEquals("The is_deleted should be set true.", 1, res.size());
    }

    /**
     * <p>
     * Tests the <code>delete(T entity)</code> for proper behavior.
     * IllegalArgumentException if entity is null.
     * </p>
     *
     * @throws Exception
     *                 to JUnit
     */
    public void test_delete_failure_1() throws Exception {
        target.initialize();

        try {
            target.delete(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Tests the <code>delete(T entity)</code> for proper behavior.
     * DAOConfigurationException if the configured entityManager is invalid
     * (invalid means null here).
     * </p>
     *
     * @throws Exception
     *                 to JUnit
     */
    public void test_delete_failure_2() throws Exception {
        target = new ProjectDAOBean();
        // prepare data
        Client client = createClient(11);
        Project project = createProjectWithClient(110, client);
        try {
            target.delete(project);
            fail("DAOConfigurationException expected.");
        } catch (DAOConfigurationException e) {
            // ok
        }
    }

    /**
     * <p>
     * Tests the <code>delete(T entity)</code> for proper behavior.
     * EntityNotFoundException if entity is not found in the persistence.
     * </p>
     *
     * @throws Exception
     *                 to JUnit
     */
    public void test_delete_failure_3() throws Exception {
        target.initialize();
        // prepare data
        Client client = createClient(11);
        Project project = createProjectWithClient(110, client);
        project.setId(111);
        try {
            target.delete(project);
            fail("EntityNotFoundException expected.");
        } catch (EntityNotFoundException e) {
            // ok
        }
    }
}
