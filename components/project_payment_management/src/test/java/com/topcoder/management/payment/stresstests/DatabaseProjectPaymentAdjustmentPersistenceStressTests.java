/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.payment.stresstests;

import java.util.List;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.management.payment.ProjectPaymentAdjustment;
import com.topcoder.management.payment.impl.persistence.DatabaseProjectPaymentAdjustmentPersistence;

import junit.framework.TestCase;

/**
 * Stress tests for class {@link DatabaseProjectPaymentAdjustmentPersistence}.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DatabaseProjectPaymentAdjustmentPersistenceStressTests extends TestCase {
    /**
     * Represents the number of execute times.
     */
    private static final int NUM = 10;

    /**
     * Instance of DatabaseProjectPaymentAdjustmentPersistence whose methods will be tested.
     */
    private DatabaseProjectPaymentAdjustmentPersistence instance = null;

    /**
     * Setup stress test environment.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void setUp() throws Exception {
        StressHelper.clearDB();
        instance = new DatabaseProjectPaymentAdjustmentPersistence();
        ConfigurationObject configuration = StressHelper.getConfigObject("DBConfig", "test_files/stress/db.xml");
        instance.configure(configuration.getChild("projectPaymentPersistenceConfig"));
    }

    /**
     * Tear down stress test environment.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void tearDown() throws Exception {
        StressHelper.clearDB();
    }

    /**
     * Stress test for method {@link DatabaseProjectPaymentAdjustmentPersistence#save(ProjectPaymentAdjustment)}.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testsaveStress() throws Exception {
        long startTime = System.currentTimeMillis();

        ProjectPaymentAdjustment[] projectPaymentAdjustment = new ProjectPaymentAdjustment[NUM];
        for (int i = 0; i < NUM; i++) {
            projectPaymentAdjustment[i] = new ProjectPaymentAdjustment();
            projectPaymentAdjustment[i].setFixedAmount(null);
            projectPaymentAdjustment[i].setMultiplier(new Double(i));
            projectPaymentAdjustment[i].setProjectId(new Long(i));
            projectPaymentAdjustment[i].setResourceRoleId(new Long(i));

            StressHelper.executeSql("INSERT INTO 'informix'.resource_role_lu(resource_role_id,phase_type_id,name,"
                    + "description,create_user,create_date,modify_user,modify_date)" + " VALUES (" + i
                    + ", NULL, 'Submitter', 'Submitter', 'System', '2006-11-02 20:14:24.000',"
                    + " 'System', '2006-11-02 20:14:24.000');");
            StressHelper.executeSql("INSERT INTO 'informix'.project(project_id, project_status_id,"
                    + " project_category_id, create_user, create_date, modify_user, modify_date, "
                    + "tc_direct_project_id) VALUES(" + i + ", 1, 1, 'admin', CURRENT, 'admin', CURRENT, 1);");
            instance.save(projectPaymentAdjustment[i]);
        }

        List<ProjectPaymentAdjustment> result = instance.retrieveByProjectId(NUM - 1);
        assertEquals("The result should be correct.", 1, result.size());
        List<ProjectPaymentAdjustment> result2 = instance.retrieveByProjectId(NUM);
        assertEquals("The result should be correct.", 0, result2.size());
        System.out.println(String.format("testsaveStress took %s ms", System.currentTimeMillis() - startTime));
    }

    /**
     * Stress test for method {@link DatabaseProjectPaymentAdjustmentPersistence#retrieveByProjectId(long)}.
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testretrieveByProjectIdStress() throws Exception {
        ProjectPaymentAdjustment projectPaymentAdjustment = new ProjectPaymentAdjustment();
        projectPaymentAdjustment.setProjectId(1L);
        projectPaymentAdjustment.setResourceRoleId(1L);
        projectPaymentAdjustment.setMultiplier(1D);

        StressHelper.executeSql("INSERT INTO 'informix'.resource_role_lu(resource_role_id,phase_type_id,name,"
                + "description,create_user,create_date,modify_user,modify_date) VALUES (1, NULL,"
                + " 'Submitter', 'Submitter', 'System', '2006-11-02 20:14:24.000', 'System',"
                + " '2006-11-02 20:14:24.000');");
        StressHelper.executeSql("INSERT INTO 'informix'.project(project_id, project_status_id,"
                + " project_category_id, create_user, create_date, modify_user, modify_date,"
                + " tc_direct_project_id) VALUES(1, 1, 1, 'admin', CURRENT, 'admin', CURRENT, 1);");
        instance.save(projectPaymentAdjustment);

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < NUM; i++) {
            List<ProjectPaymentAdjustment> result = instance.retrieveByProjectId(1L);
            assertEquals("The result should be correct.", 1, result.size());
        }
        System.out.println(String.format("testretrieveByProjectIdStress took %s ms", System.currentTimeMillis()
                - startTime));
    }
}
