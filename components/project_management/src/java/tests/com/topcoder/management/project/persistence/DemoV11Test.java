/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.management.project.persistence;

import java.sql.Connection;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;

import junit.framework.TestCase;

import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.management.project.Project;
import com.topcoder.management.project.ProjectCategory;
import com.topcoder.management.project.ProjectStatus;
import com.topcoder.management.project.ProjectType;
import com.topcoder.util.config.ConfigManager;

/**
 * <p>
 * This is the demo showing how to use the added functionality of this component. It demonstrates how to use the
 * unmanaged persistence class with EJB, where the transaction is managed by the container.
 * </p>
 * <p>
 * Please refer to the <code>ProjectBean</code>, <code>ProjectHome</code>, <code>ProjectObject</code> for more
 * information about the EJB classes.
 * </p>
 * <p>
 * Please deploy the EJB to the EBJ Container before running the test.
 * </p>
 *
 * @author fuyun
 * @version 1.1
 */
public class DemoV11Test extends TestCase {

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     * <p>
     * The necessary data is prepared in database.
     * </p>
     *
     * @throws Exception
     *             if there is any problem.
     */
    protected void setUp() throws Exception {

        tearDown();

        ConfigManager cm = ConfigManager.getInstance();

        cm.add("dbfactory.xml");
        // create the connection
        Connection conn = null;
        try {
            conn = new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName()).createConnection();
            conn.setAutoCommit(false);

            // insert data into project_type_lu table
            Helper.doDMLQuery(conn, "INSERT INTO project_type_lu " + "(project_type_id, name, description, "
                + "create_user, create_date, modify_user, modify_date) "
                + "VALUES (1, 'Topcoder', 'Topcoder Component', " + "'topcoder', CURRENT, 'topcoder', CURRENT)",
                new Object[]{});

            // insert data into project_category_lu table
            Helper.doDMLQuery(conn, "INSERT INTO project_category_lu "
                + "(project_category_id, project_type_id, name, description, "
                + "create_user, create_date, modify_user, modify_date) " + "VALUES (1, 1, '.Net', '.NET Component', "
                + "'topcoder', CURRENT, 'topcoder', CURRENT)", new Object[]{});

            conn.commit();

            cm.removeNamespace("com.topcoder.db.connectionfactory.DBConnectionFactoryImpl");

        } finally {
            if (conn != null) {
                conn.close();
            }

        }
    }

    /**
     * <p>
     * Cleans up the test environment.
     * </p>
     * <p>
     * The test data in database is cleaned.
     * </p>
     *
     * @throws Exception
     *             if there is any problem.
     */
    protected void tearDown() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        cm.add("dbfactory.xml");
        // create the connection
        Connection conn = null;
        try {
            conn = new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName()).createConnection();
            conn.setAutoCommit(false);

            Helper.doDMLQuery(conn, "DELETE FROM project", new Object[]{});
            Helper.doDMLQuery(conn, "DELETE FROM project_category_lu", new Object[]{});
            Helper.doDMLQuery(conn, "DELETE FROM project_type_lu", new Object[]{});

            conn.commit();

            cm.removeNamespace("com.topcoder.db.connectionfactory.DBConnectionFactoryImpl");

        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    /**
     * <p>
     * Demonstrates how to use the <code>UnmanagedTransactionInformixProjectPersistence</code> with EJB, where the
     * transaction is managed by the EJB container.
     * </p>
     */
    public void testEJBDemo() {
        try {

            // create the necessary properties.
            java.util.Properties p = new java.util.Properties();
            p.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
            p.put(Context.URL_PKG_PREFIXES, "jboss.naming:org.jnp.interfaces");
            p.put(Context.PROVIDER_URL, "localhost:1099");

            // looks up the home
            Context jndiContext = new InitialContext(p);
            Object ref = jndiContext.lookup("ProjectHome");

            // create the home and object
            ProjectHome home = (ProjectHome) PortableRemoteObject.narrow(ref, ProjectHome.class);
            ProjectObject pb = home.create();

            // create the Project by EJB
            pb.createProject(getProject(), "topcoder");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Get a sample Project object to test, with project id = 1, project category = .Net, project type = Topcoder,
     * project status = Active.
     *
     * @return a sample Project object
     */
    private Project getProject() {
        // create a ProjectStatus object
        ProjectStatus status = new ProjectStatus(1, "Active");

        // create a ProjectType object
        ProjectType type = new ProjectType(1, "Topcoder");

        // create a ProjectCategory object
        ProjectCategory category = new ProjectCategory(1, ".Net", type);

        // create the sample project object
        Project project = new Project(category, status);

        project.setId(1);

        return project;
    }

}
