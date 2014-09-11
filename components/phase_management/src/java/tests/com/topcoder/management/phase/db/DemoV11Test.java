/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.management.phase.db;

import java.util.Date;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;

import junit.framework.TestCase;

import com.topcoder.date.workdays.DefaultWorkdaysFactory;
import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.PhaseStatus;
import com.topcoder.project.phases.PhaseType;
import com.topcoder.project.phases.Project;
import com.topcoder.util.config.ConfigManager;

/**
 * <p>
 * This is the demo showing how to use the added functionality of this
 * component. It demonstrates how to use the unmanaged persistence class with
 * EJB, where the transaction is managed by the container.
 * </p>
 * <p>
 * Please refer to the <code>PhaseBean</code>, <code>PhaseHome</code>,
 * <code>PhaseObject</code> for more information about the EJB classes.
 * </p>
 * <p>
 * Please deploy the EJB to the EBJ Container before running the test.
 * </p>
 * @author TCSDEVELOPER
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
     * @throws Exception if there is any problem.
     */
    protected void setUp() throws Exception {

        ConfigManager cm = ConfigManager.getInstance();

        cm.add("DBConfig.xml");

        TestHelper.initDatabase();

        TestHelper.executeSQL("insert into project (project_id) values (1);");
        TestHelper
                .executeSQL("insert into phase_type_lu (phase_type_id, name, description, "
                        + "create_user, create_date, modify_user, modify_date) values (1, 'java', "
                        + "'java component', 'tc', current, 'tc', current)");
        TestHelper
                .executeSQL("INSERT INTO phase_status_lu(phase_status_id, name, description, "
                        + "create_user, create_date, modify_user, modify_date) VALUES(2, 'Open', "
                        + "'Open', 'System', CURRENT, 'System', CURRENT);");

    }

    /**
     * <p>
     * Cleans up the test environment.
     * </p>
     * <p>
     * The test data in database is cleaned.
     * </p>
     * @throws Exception if there is any problem.
     */
    protected void tearDown() throws Exception {

        TestHelper.executeSQL("delete from project_phase;delete from project;");
        TestHelper
                .executeSQL("delete from phase_type_lu;delete from phase_status_lu");
        TestHelper.cleanDatabase();
        ConfigManager.getInstance().removeNamespace(
                "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl");

    }

    /**
     * <p>
     * Demonstrates how to use the
     * <code>UnmanagedTransactionInformixPhasePersistence</code> with EJB,
     * where the transaction is managed by the EJB container.
     * </p>
     */
    public void testEJBDemo() {
        try {

            // create the necessary properties.
            java.util.Properties p = new java.util.Properties();
            p.put(Context.INITIAL_CONTEXT_FACTORY,
                    "org.jnp.interfaces.NamingContextFactory");
            p.put(Context.URL_PKG_PREFIXES, "jboss.naming:org.jnp.interfaces");

            // looks up the home
            Context jndiContext = new InitialContext(p);
            Object ref = jndiContext.lookup("PhaseHome");

            // create the home and object
            PhaseRemoteHome home = (PhaseRemoteHome) PortableRemoteObject
                    .narrow(ref, PhaseRemoteHome.class);
            PhaseRemoteObject pb = home.create();

            // PhaseObject object = home.create();
            // create the phase by EJB
            pb.createPhase(createPhase(1, 1), "topcoder");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Creates the Phase object. Set the dates to current value.
     * @param id the Phase id.
     * @param projectId the project id to which this phase belongs to.
     * @return the Phase instance.
     */
    protected Phase createPhase(long id, long projectId) {
        Project project = new Project(new Date(), new DefaultWorkdaysFactory()
                .createWorkdaysInstance());
        project.setId(1);
        Phase phase = new Phase(project, id * 1000);
        phase.setId(id);
        phase.setFixedStartDate(new Date());
        phase.setActualStartDate(new Date());
        phase.setActualEndDate(new Date());

        phase.setScheduledEndDate(new Date(System.currentTimeMillis() + id
                * 1000));
        phase.setScheduledStartDate(new Date(System.currentTimeMillis() + id
                * 100));

        phase.setPhaseStatus(PhaseStatus.OPEN);
        phase.setPhaseType(new PhaseType(1, "c"));

        return phase;
    }

}
