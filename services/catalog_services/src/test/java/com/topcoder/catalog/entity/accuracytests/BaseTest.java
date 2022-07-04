/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.catalog.entity.accuracytests;

import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.UnknownNamespaceException;
import com.topcoder.util.idgenerator.ejb.IDGenerator;
import com.topcoder.util.idgenerator.ejb.IDGeneratorBean;
import com.topcoder.util.idgenerator.ejb.IDGeneratorHome;
import com.topcoder.catalog.entity.TestHelper;

import junit.framework.TestCase;

import org.mockejb.MockContainer;
import org.mockejb.SessionBeanDescriptor;

import org.mockejb.jndi.MockContextFactory;

import java.io.File;

import java.util.Iterator;

import javax.naming.Context;
import javax.naming.InitialContext;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;


/**
 * A base class for accuracy tests.
 * 
 * <p>
 * It will set up the hibernate environment for testing.
 * </p>
 *
 * @author Chenhong
 * @version 1.0
 */
public class BaseTest extends TestCase {
    /** The context for testing. */
    private Context context = null;

    /** The sessionBean used in testing. */
    private IDGeneratorBean sessionBean = null;

    /**
     * Get an EntityManager instance
     *
     * @return EntityManager instance.
     */
    public EntityManager getEntityManager() {
        return TestHelper.getEntityManager();
    }

    /**
     * Get an EntityTransaction.
     *
     * @return EntityTransaction
     */
    public EntityTransaction getEntityTransaction() {
        return TestHelper.getEntityTransaction();
    }

    /**
     * Set up the environment for hibernate testing.
     *
     * @throws Exception to junit.
     */
    protected void setUp() throws Exception {
        super.setUp();

        // Prepare configuration and clear data for testing
        loadConfiguration();

        // Setup mock ejb
        MockContextFactory.setAsInitial();
        context = new InitialContext();

        MockContainer container = new MockContainer(context);

        // Prepare the SessionBean for testing
        sessionBean = new IDGeneratorBean();

        SessionBeanDescriptor descriptor = new SessionBeanDescriptor("java:ejb/IDGeneratorHome",
                IDGeneratorHome.class, IDGenerator.class, sessionBean);
        container.deploy(descriptor);

        IDGeneratorHome home = (IDGeneratorHome) context.lookup("java:ejb/IDGeneratorHome");
        home.create();
        // clear all the table for testing.
        clearData();
    }

    /**
     * Clear all test data.
     *
     * @throws Exception if error occurs
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        
        clearData();
        MockContextFactory.revertSetAsInitial();
        clearNamespaces();
        if (getEntityTransaction() != null && getEntityTransaction().isActive()) {
            getEntityTransaction().rollback();
        }
    }

    /**
     * Clear the data in the database tables.
     *
     * @throws Exception to junit.
     */
    private void clearData() throws Exception {
        TestHelper.clearTables();
    }

    /**
     * Load the configuration.
     *
     * @throws Exception to junit.
     */
    private void loadConfiguration() throws Exception {
        clearNamespaces();

        ConfigManager cm = ConfigManager.getInstance();
        cm.add(new File("test_files/accuracytests/jndi_config.xml").getCanonicalPath());
    }

    /**
     * Clear all the namespaces in the Config Manager.
     *
     * @throws Exception to junit.
     */
    private void clearNamespaces() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        try {
            cm.removeNamespace("com.topcoder.naming.jndiutility");
        } catch (UnknownNamespaceException e) {
            // ignore
        }
    }
    
    /**
     * Insert a record into the category table.
     * @param query the query for insert a record.
     * @throws Exception to junit.
     */
    public void insertRecordIntoCategory(String query) throws Exception {
        if (getEntityTransaction().isActive() == false) {
            getEntityTransaction().begin();
        }
        
        getEntityManager().createNativeQuery(query).executeUpdate();
        
        getEntityTransaction().commit();
    }
}
