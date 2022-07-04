/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.dao.failuretests;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.clients.dao.DAOConfigurationException;
import com.topcoder.clients.dao.EntityNotFoundException;
import com.topcoder.clients.dao.ejb3.ClientDAOBean;
import com.topcoder.clients.dao.ejb3.GenericEJB3DAO;
import com.topcoder.clients.model.Client;

/**
 * Failure test for GenericEJB3DAO class.
 *
 * @author AK_47
 * @version 1.0
 */
public class GenericEJB3DAOBeanFailureTest extends TestCase {

    /**
     * The searchBundleManagerNamespace used in tests.
     */
    private String searchBundleManagerNamespace = "com.topcoder.search.builder";

    /**
     * The configFileName used in tests.
     */
    private String configFileName = "test_files/failure/test.properties";

    /**
     * The configNamespace used in tests.
     */
    private String configNamespace = "DAO";

    /**
     * <p>
     * An instance of <code>GenericEJB3DAO</code> which is tested.
     * </p>
     */
    private GenericEJB3DAO<Client, Long> bean = null;

    /**
     * Aggregates all tests in this class.
     *
     * @return Test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(GenericEJB3DAOBeanFailureTest.class);
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
        super.setUp();

        bean = new ClientDAOBean();

        bean.setEntityManager(TestHelper.getEntityManager());
    }

    /**
     * Failure test of <code>delete(T entity)</code> method.
     *
     * <p>
     * entity is null.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testDelete_Null_Client() throws Exception {
        try {
            bean.delete(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>delete(T entity)</code> method.
     *
     * <p>
     * entityManager is null.
     * </p>
     *
     * <p>
     * Expect DAOConfigurationException.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testDelete_Null_EntityManager() throws Exception {
        try {
            bean = new ClientDAOBean();

            bean.delete(new Client());

            fail("Expect DAOConfigurationException.");
        } catch (DAOConfigurationException e) {
            // expect
        }
    }

    /**
     * <p>
     * Tests the <code>delete(T entity)</code> for proper
     * behavior.
     * </p>
     *
     * <p>
     * Expect EntityNotFoundException.
     * </p>
     *
     * @throws Exception
     *                 to JUnit
     */
    public void testDelete_Invalid_Client() throws Exception {
        try {
            Client client = new Client();
            // Note: this client doesn't exist in db
            client.setId(1000L);
            bean.delete(client);
            fail("EntityNotFoundException expected.");
        } catch (EntityNotFoundException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>initialize()</code> method.
     *
     * <p>
     * searchBundleManagerNamespace is null.
     * </p>
     *
     * <p>
     * Expect DAOConfigurationException.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testinitialize_1() throws Exception {
        try {
            TestHelper.setField(GenericEJB3DAO.class, bean, "configFileName", configFileName);
            TestHelper.setField(GenericEJB3DAO.class, bean, "configNamespace", configNamespace);

            Method method = GenericEJB3DAO.class.getDeclaredMethod("initialize", new Class[] {});
            method.setAccessible(true);
            method.invoke(bean, new Object[] {});

            fail("DAOConfigurationException expected if searchBundleManagerNamespace is null.");
        } catch (InvocationTargetException e) {
            assertTrue(e.getCause() instanceof DAOConfigurationException);
            // expect
        }
    }

    /**
     * Failure test of <code>initialize()</code> method.
     *
     * <p>
     * searchBundleManagerNamespace is empty.
     * </p>
     *
     * <p>
     * Expect DAOConfigurationException.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testinitialize_2() throws Exception {
        try {
            TestHelper.setField(GenericEJB3DAO.class, bean,"searchBundleManagerNamespace", "  ");
            TestHelper.setField(GenericEJB3DAO.class, bean, "configFileName", configFileName);
            TestHelper.setField(GenericEJB3DAO.class, bean, "configNamespace", configNamespace);

            Method method = GenericEJB3DAO.class.getDeclaredMethod("initialize", new Class[] {});
            method.setAccessible(true);
            method.invoke(bean, new Object[] {});

            fail("DAOConfigurationException expected if searchBundleManagerNamespace is null.");
        } catch (InvocationTargetException e) {
            assertTrue(e.getCause() instanceof DAOConfigurationException);
            // expect
        }
    }

    /**
     * Failure test of <code>initialize()</code> method.
     *
     * <p>
     * configFileName is null.
     * </p>
     *
     * <p>
     * Expect DAOConfigurationException.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testinitialize_3() throws Exception {
        try {
            TestHelper.setField(GenericEJB3DAO.class, bean, "searchBundleManagerNamespace", searchBundleManagerNamespace);
            TestHelper.setField(GenericEJB3DAO.class, bean, "configNamespace", configNamespace);

            Method method = GenericEJB3DAO.class.getDeclaredMethod("initialize", new Class[] {});
            method.setAccessible(true);
            method.invoke(bean, new Object[] {});

            fail("DAOConfigurationException expected if searchBundleManagerNamespace is null.");
        } catch (InvocationTargetException e) {
            assertTrue(e.getCause() instanceof DAOConfigurationException);
            // expect
        }
    }

    /**
     * Failure test of <code>initialize()</code> method.
     *
     * <p>
     * configFileName is empty.
     * </p>
     *
     * <p>
     * Expect DAOConfigurationException.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testinitialize_4() throws Exception {
        try {
            TestHelper.setField(GenericEJB3DAO.class, bean, "searchBundleManagerNamespace", searchBundleManagerNamespace);
            TestHelper.setField(GenericEJB3DAO.class, bean, "configFileName", "  ");
            TestHelper.setField(GenericEJB3DAO.class, bean, "configNamespace", configNamespace);

            Method method = GenericEJB3DAO.class.getDeclaredMethod("initialize", new Class[] {});
            method.setAccessible(true);
            method.invoke(bean, new Object[] {});

            fail("DAOConfigurationException expected if searchBundleManagerNamespace is null.");
        } catch (InvocationTargetException e) {
            assertTrue(e.getCause() instanceof DAOConfigurationException);
            // expect
        }
    }

    /**
     * Failure test of <code>initialize()</code> method.
     *
     * <p>
     * configNamespace is null.
     * </p>
     *
     * <p>
     * Expect DAOConfigurationException.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testinitialize_5() throws Exception {
        try {
            TestHelper.setField(GenericEJB3DAO.class, bean, "searchBundleManagerNamespace", searchBundleManagerNamespace);
            TestHelper.setField(GenericEJB3DAO.class, bean, "configFileName", configFileName);

            Method method = GenericEJB3DAO.class.getDeclaredMethod("initialize", new Class[] {});
            method.setAccessible(true);
            method.invoke(bean, new Object[] {});

            fail("DAOConfigurationException expected if searchBundleManagerNamespace is null.");
        } catch (InvocationTargetException e) {
            assertTrue(e.getCause() instanceof DAOConfigurationException);
            // expect
        }
    }

    /**
     * Failure test of <code>initialize()</code> method.
     *
     * <p>
     * configNamespace is empty.
     * </p>
     *
     * <p>
     * Expect DAOConfigurationException.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testinitialize_6() throws Exception {
        try {
            TestHelper.setField(GenericEJB3DAO.class, bean, "searchBundleManagerNamespace", searchBundleManagerNamespace);
            TestHelper.setField(GenericEJB3DAO.class, bean, "configFileName", configFileName);
            TestHelper.setField(GenericEJB3DAO.class, bean, "configNamespace", "  ");

            Method method = GenericEJB3DAO.class.getDeclaredMethod("initialize", new Class[] {});
            method.setAccessible(true);
            method.invoke(bean, new Object[] {});

            fail("DAOConfigurationException expected if searchBundleManagerNamespace is null.");
        } catch (InvocationTargetException e) {
            assertTrue(e.getCause() instanceof DAOConfigurationException);
            // expect
        }
    }

    /**
     * Failure test of <code>initialize()</code> method.
     *
     * <p>
     * searchBundleManagerNamespace is inalid.
     * </p>
     *
     * <p>
     * Expect DAOConfigurationException.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testinitialize_7() throws Exception {
        try {
            TestHelper.setField(GenericEJB3DAO.class, bean, "searchBundleManagerNamespace", "invalid");
            TestHelper.setField(GenericEJB3DAO.class, bean, "configFileName", configFileName);
            TestHelper.setField(GenericEJB3DAO.class, bean, "configNamespace", configNamespace);

            Method method = GenericEJB3DAO.class.getDeclaredMethod("initialize", new Class[] {});
            method.setAccessible(true);
            method.invoke(bean, new Object[] {});

            fail("DAOConfigurationException expected if searchBundleManagerNamespace is null.");
        } catch (InvocationTargetException e) {
            assertTrue(e.getCause() instanceof DAOConfigurationException);
            // expect
        }
    }
    /**
     * Failure test of <code>initialize()</code> method.
     *
     * <p>
     * configFileName is invalid.
     * </p>
     *
     * <p>
     * Expect DAOConfigurationException.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testinitialize_8() throws Exception {
        try {
            TestHelper.setField(GenericEJB3DAO.class, bean, "searchBundleManagerNamespace", searchBundleManagerNamespace);
            TestHelper.setField(GenericEJB3DAO.class, bean, "configFileName", "invalid");
            TestHelper.setField(GenericEJB3DAO.class, bean, "configNamespace", configNamespace);

            Method method = GenericEJB3DAO.class.getDeclaredMethod("initialize", new Class[] {});
            method.setAccessible(true);
            method.invoke(bean, new Object[] {});

            fail("DAOConfigurationException expected if searchBundleManagerNamespace is null.");
        } catch (InvocationTargetException e) {
            assertTrue(e.getCause() instanceof DAOConfigurationException);
            // expect
        }
    }

    /**
     * Failure test of <code>initialize()</code> method.
     *
     * <p>
     * configNamespace is invalid.
     * </p>
     *
     * <p>
     * Expect DAOConfigurationException.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testinitialize_9() throws Exception {
        try {
            TestHelper.setField(GenericEJB3DAO.class, bean, "searchBundleManagerNamespace", searchBundleManagerNamespace);
            TestHelper.setField(GenericEJB3DAO.class, bean, "configFileName", configFileName);
            TestHelper.setField(GenericEJB3DAO.class, bean, "configNamespace", "invalid");

            Method method = GenericEJB3DAO.class.getDeclaredMethod("initialize", new Class[] {});
            method.setAccessible(true);
            method.invoke(bean, new Object[] {});

            fail("DAOConfigurationException expected if searchBundleManagerNamespace is null.");
        } catch (InvocationTargetException e) {
            assertTrue(e.getCause() instanceof DAOConfigurationException);
            // expect
        }
    }

    /**
     * Failure test of <code>retrieveAll()</code> method.
     *
     * <p>
     * entityManager is null.
     * </p>
     *
     * <p>
     * Expect DAOConfigurationException.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testRetrieveAll_Null_EntityManager() throws Exception {
        try {
            bean = new ClientDAOBean();

            bean.retrieveAll();

            fail("Expect DAOConfigurationException.");
        } catch (DAOConfigurationException e) {
            // expect
        }
    }


    /**
     * Failure test of <code>searchByName(String)</code> method.
     *
     * <p>
     * name is null.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testSearchByName_Null_Name() throws Exception {
        try {
            bean.searchByName(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>searchByName(String)</code> method.
     *
     * <p>
     * name is empty.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testSearchByName_Empty_Name() throws Exception {
        try {
            bean.searchByName("  ");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>searchByName(String)</code> method.
     *
     * <p>
     * entityManager is null.
     * </p>
     *
     * <p>
     * Expect DAOConfigurationException.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void testSearchByName_Null_EntityManager() throws Exception {
        try {
            bean = new ClientDAOBean();

            bean.searchByName("aa");

            fail("Expect DAOConfigurationException.");
        } catch (DAOConfigurationException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>search(Filter)</code> method.
     *
     * <p>
     * filter is null.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testSearch_Null_Filter() throws Exception {
        try {
            bean.search(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>save(T entity)</code> method.
     *
     * <p>
     * entity is null.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testSave_Null_Entity() throws Exception {
        try {
            bean.search(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }
}
