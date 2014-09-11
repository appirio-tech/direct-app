/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.project;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;

import junit.framework.TestCase;

import org.jboss.ws.core.StubExt;

import com.topcoder.service.project.impl.ProjectServiceLocalBridge;

/**
 * <p>
 * This base test case provides common functionalities:
 *   <ul>
 *     <li>Lookup Remote EJB with user/admin role;</li>
 *     <li>Lookup Local EJB bridge with user/admin role;</li>
 *     <li>Lookup WS client with user/admin role;</li>
 *     <li>Create a J2SE <code>EntityManager</code>;</li>
 *     <li>Other misc functionalities.</li>
 *   </ul>
 * </p>
 *
 * @author FireIce
 * @author TCSDEVELOPER
 * @version 1.1
 * @since 1.0
 */
public abstract class BaseUnitTestCase extends TestCase {

    /**
     * <p>
     * The instance of <code>EntityManager</code> used in Unit tests.
     * </p>
     */
    private static EntityManager em;

    /**
     * <p>
     * The instance of <code>EntityTransaction</code> used in Unit tests.
     * </p>
     */
    private static EntityTransaction et;

    /**
     * <p>
     * Represents the <code>InitialContext</code> instance for looking up.
     * </p>
     */
    private InitialContext ctx;

    static {
        //Create a J2SE EntityManager. Configuration files are under /test_files/META-INF
        em = Persistence.createEntityManagerFactory("HibernateProjectPersistence").createEntityManager();
        et = em.getTransaction();
    }

    /**
     * <p>
     * Setup the testing environment.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    /**
     * <p>
     * Tear down the testing environment.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    @Override
    protected void tearDown() throws Exception {
        if (ctx != null) {
            ctx.close();
        }

        super.tearDown();
    }

    /**
     * <p>
     * Assert two instances of <code>ProjectData</code> equal.
     * </p>
     *
     * @param one <code>ProjectData</code>
     * @param two <code>ProjectData</code>
     */
    protected void assertProjectData(ProjectData one, ProjectData two) {
        assertEquals("Incorrect id", one.getProjectId().longValue(), two.getProjectId().longValue());
        assertEquals("Incorrect name", one.getName(), two.getName());
        assertEquals("Incorrect description", one.getDescription(), two.getDescription());
    }

    /**
     * <p>
     * Get <code>InitialContext</code> with given user name.
     * </p>
     *
     * @param username The user name set as <code>Context.SECURITY_PRINCIPAL</code>.
     *
     * @return The <code>InitialContext</code>.
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    protected InitialContext getInitialContext(String username) throws Exception {
        Properties env = new Properties();

        // Specify principal
        env.setProperty(Context.SECURITY_PRINCIPAL, username);

        // Specify credential
        env.setProperty(Context.SECURITY_CREDENTIALS, "password");

        // The initial factory and provider url are specified in /test_files/jndi.properties

        ctx = new InitialContext(env);

        return ctx;
    }

    /**
     * <p>
     * Lookup the <code>ProjectServiceRemote</code> with administrator role.
     * </p>
     *
     * @return The <code>ProjectServiceRemote</code> with administrator role.
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    protected ProjectServiceRemote lookupProjectServiceRemoteWithAdminRole() throws Exception {

        //See /test_files/lib/mock.jar/MockUserGroupManager
        return (ProjectServiceRemote) getInitialContext("admin").lookup("remote/ProjectServiceBean");
    }

    /**
     * <p>
     * Lookup the <code>ProjectServiceRemote</code> with user role.
     * </p>
     *
     * @return The <code>ProjectServiceRemote</code> with user role.
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    protected ProjectServiceRemote lookupProjectServiceRemoteWithUserRole() throws Exception {

        //See /test_files/lib/mock.jar/MockUserGroupManager
        return (ProjectServiceRemote) getInitialContext("username").lookup("remote/ProjectServiceBean");
    }

    /**
     * <p>
     * Lookup the <code>ProjectServiceLocalBridge</code> with administrator role.
     * </p>
     *
     * @return The <code>ProjectServiceLocalBridge</code> with administrator role.
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    protected ProjectServiceLocalBridge lookupProjectServiceLocalWithAdminRole() throws Exception {

        //See /test_files/lib/mock.jar/MockUserGroupManager
        return (ProjectServiceLocalBridge) getInitialContext("admin").lookup(
            "project_service/ProjectServiceLocalBridgeBean/remote");
    }

    /**
     * <p>
     * Lookup the <code>ProjectServiceLocalBridge</code> with user role.
     * </p>
     *
     * @return The <code>ProjectServiceLocalBridge</code> with user role.
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    protected ProjectServiceLocalBridge lookupProjectServiceLocalWithUserRole() throws Exception {

        //See /test_files/lib/mock.jar/MockUserGroupManager
        return (ProjectServiceLocalBridge) getInitialContext("username").lookup(
            "project_service/ProjectServiceLocalBridgeBean/remote");
    }

    /**
     * <p>
     * Get <code>ProjectService</code> WebService client with given user name.
     * </p>
     *
     * @param username The user name set as <code>BindingProvider.USERNAME_PROPERTY</code>.
     *
     * @return The <code>ProjectService</code> WebService client with given user name.
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    protected ProjectService getWSClient(String username) throws Exception {
        ProjectService service = Service.create(
                new URL("http://127.0.0.1:8080/project_service-project_service/ProjectServiceBean?wsdl"),
                new QName("http://impl.project.service.topcoder.com/", "ProjectServiceBeanService"))
                .getPort(ProjectService.class);

        // Provide the username/password
        URL securityURL = Demo.class.getResource("/jboss-wsse-client.xml");
        ((StubExt) service).setSecurityConfig(securityURL.toURI().toString());
        ((StubExt) service).setConfigName("Standard WSSecurity Client");
        ((BindingProvider) service).getRequestContext().put(BindingProvider.USERNAME_PROPERTY, username);
        ((BindingProvider) service).getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, "password");

        return service;
    }

    /**
     * <p>
     * Lookup the <code>ProjectService</code> WebService client with administrator role.
     * </p>
     *
     * @return The <code>ProjectService</code> WebService client with administrator role.
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    protected ProjectService lookupProjectServiceWSClientWithAdminRole() throws Exception {

        //See /test_files/lib/mock.jar/MockUserGroupManager
        return getWSClient("admin");
    }

    /**
     * <p>
     * Lookup the <code>ProjectService</code> WebService client with user role.
     * </p>
     *
     * @return The <code>ProjectService</code> WebService client with user role.
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    protected ProjectService lookupProjectServiceWSClientWithUserRole() throws Exception {

        //See /test_files/lib/mock.jar/MockUserGroupManager
        return getWSClient("username");
    }

    /**
     * <p>
     * Get the instance of <code>EntityManager</code> used in Unit tests.
     * </p>
     *
     * @return The instance of <code>EntityManager</code> used in Unit tests.
     */
    public static EntityManager getEntityManager() {
        return em;
    }

    /**
     * <p>
     * Get the instance of <code>EntityTransaction</code> used in Unit tests.
     * </p>
     *
     * @return The instance of <code>EntityTransaction</code> used in Unit tests.
     */
    public static EntityTransaction getEntityTransaction() {
        return et;
    }

    /**
     * <p>
     * Persist entity.
     * </p>
     *
     * @param entity to be persisted
     */
    public static void persist(Object entity) {

        try {
            em.clear();

            if (!et.isActive()) {
                et.begin();
            }

            em.persist(entity);

            et.commit();

            em.clear();
        } catch (PersistenceException e) {
            // If a PersistenceException is thrown then the transaction is already rolled back
            throw e;
        }
    }

    /**
     * <p>
     * Executes the sql script.
     * </p>
     *
     * @param fileName The file name of sql script to be executed.
     *
     * @throws Exception to JUnit
     */
    public static void executeScript(String fileName) throws Exception {
        InputStream input = UnitTests.class.getResourceAsStream(fileName);
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));

        try {
            em.clear();

            if (!et.isActive()) {
                et.begin();
            }

            String line = null;

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (line.length() > 0 && line.endsWith(";")) {
                    line = line.substring(0, line.length() - 1);

                    em.createNativeQuery(line).executeUpdate();
                }
            }

            et.commit();

            em.clear();
        } catch (PersistenceException ex) {
            // If a PersistenceException is thrown then the transaction is already rolled back
            throw ex;
        } finally {
            reader.close();
            input.close();
        }
    }

}
