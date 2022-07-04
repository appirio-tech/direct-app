/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.management.resource.persistence.sql.stresstests;

import junit.framework.TestCase;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.management.resource.NotificationType;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.resource.ResourceRole;
import com.topcoder.management.resource.persistence.sql.DBTestUtil;
import com.topcoder.management.resource.persistence.sql.UnmanagedTransactionResourcePersistence;

/**
 * <p>
 * Stress test case for <code>SqlResourcePersistence</code>.
 * </p>
 * <p>
 * The test cases cover the business logic in
 * <code>AbstractResourcePersistence</code> for the transactional DAO methods.
 * </p>
 * @author fuyun
 * @version 1.1
 */
public class UnmanagedTransactionResourcePersistenceTest extends TestCase {

    /**
     * Represents the <code>UnmanagedTransactionResourcePersistence</code> instance for
     * testing.
     */
    private UnmanagedTransactionResourcePersistence persistence = null;

    /**
     * Represents the <code>DBConnectionFactory</code> instance for testing.
     */
    private DBConnectionFactory factory = null;

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     * <ol>
     * <li>Loads the configuration files.</li>
     * <li>Prepares the data in database.</li>
     * <li>Creates the persistence instance.</li>
     * </ol>
     */
    protected void setUp() throws Exception {
        StressTestsHelper.loadConfiguration();
        StressTestsHelper.cleanDatabase();
        StressTestsHelper.prepareDatabase();
        factory = StressTestsHelper.getDBConnectionFactory();
        persistence = new UnmanagedTransactionResourcePersistence(factory);
    }

    /**
     * <p>
     * Cleans up the test environment.
     * </p>
     * <ol>
     * <li>Cleans the data in database. </li>
     * <li>Cleans the configuration.</li>
     * </ol>
     */
    protected void tearDown() throws Exception {
        StressTestsHelper.cleanDatabase();
        StressTestsHelper.cleanConfiguration();
    }

    /**
     * Stress test for the <code>addNotification</code> and
     * <code>removeNotification</code> methods.
     * @throws Exception if there is any problem.
     */
    public void testAddRemoveNotificationSuccess() throws Exception {
        for (int i = 0; i < 10; i++) {
            persistence.addNotification(i + 1, 2007, 2007, "stress");
        }

        assertEquals(
                "Fails to add notification.",
                10,
                StressTestsHelper
                        .getRecordsNumber("SELECT count(*) FROM notification WHERE project_id = 2007;"));

        for (int i = 0; i < 10; i++) {
            persistence.removeNotification(i + 1, 2007, 2007, "stress");
        }

        assertEquals(
                "Fails to remove notification.",
                0,
                StressTestsHelper
                        .getRecordsNumber("SELECT count(*) FROM notification WHERE project_id = 2007;"));

    }

    /**
     * Stress test for the method <code>addNotificationType</code> and
     * <code>deleteNotificationType</code>.
     * @throws Exception if there is any problem.
     */
    public void testAddDeleteNotificationTypeSuccess() throws Exception {
        for (int i = 0; i < 10; i++) {
            persistence.addNotificationType(StressTestsHelper
                    .getNotificationType(i + 1));
        }
        assertEquals("Fails to add notification type.", 11, StressTestsHelper
                .getRecordsNumber("SELECT count(*) FROM notification_type_lu;"));

        for (int i = 0; i < 10; i++) {
            persistence.deleteNotificationType(StressTestsHelper
                    .getNotificationType(i + 1));
        }
        assertEquals("Fails to delete notification type.", 1, StressTestsHelper
                .getRecordsNumber("SELECT count(*) FROM notification_type_lu;"));
    }

    /**
     * Stress test for the method <code>addResource</code> and
     * <code>deleteResource</code>.
     * @throws Exception if there is any problem.
     */
    public void testAddDeleteResourceSuccess() throws Exception {

        ResourceRole role = DBTestUtil.createResourceRole(5);
        role.setPhaseType(new Long(2007));
        persistence.addResourceRole(role);
        for (int i = 0; i < 10; i++) {
            persistence.addResource(StressTestsHelper.getResource(i + 1, 2007));
        }
        assertEquals(
                "Fails to add resource.",
                10,
                StressTestsHelper
                        .getRecordsNumber("SELECT count(*) FROM resource where resource_role_id = 2007"));

        for (int i = 0; i < 10; i++) {
            persistence.deleteResource(StressTestsHelper.getResource(i + 1,
                    2007));
        }
        assertEquals(
                "Fails to delete resource.",
                0,
                StressTestsHelper
                        .getRecordsNumber("SELECT count(*) FROM resource where resource_role_id = 2007"));
    }

    /**
     * Stress test for method <code>addResourceRole</code> and
     * <code>deleteResourceRole</code>.
     * @throws Exception if there is any problem.
     */
    public void testAddDeleteResourceRoleSuccess() throws Exception {
        for (int i = 0; i < 10; i++) {
            persistence.addResourceRole(StressTestsHelper
                    .getResourceRole(i + 1));
        }

        assertEquals("Fails to add resource role.", 11, StressTestsHelper
                .getRecordsNumber("SELECT count(*) FROM resource_role_lu;"));

        for (int i = 0; i < 10; i++) {
            persistence.deleteResourceRole(StressTestsHelper
                    .getResourceRole(i + 1));
        }

        assertEquals("Fails to delete resource role.", 1, StressTestsHelper
                .getRecordsNumber("SELECT count(*) FROM resource_role_lu;"));

    }

    /**
     * Stress test for the <code>addNotification</code> and
     * <code>loadNotification</code> methods.
     * @throws Exception if there is any problem.
     */
    public void testAddLoadNotificationSuccess() throws Exception {
        for (int i = 0; i < 10; i++) {
            persistence.addNotification(i + 1, 2007, 2007, "stress");
        }
        for (int i = 0; i < 10; i++) {
            assertNotNull("Fails to load notification.", persistence
                    .loadNotification(i + 1, 2007, 2007));
        }
    }

    /**
     * Stress test for the method <code>addNotificationType</code> and
     * <code>loadNotificationType</code>.
     * @throws Exception if there is any problem.
     */
    public void testAddLoadNotificationTypeSuccess() throws Exception {
        for (int i = 0; i < 10; i++) {
            persistence.addNotificationType(StressTestsHelper
                    .getNotificationType(i + 1));
        }
        for (int i = 0; i < 10; i++) {
            assertNotNull("Fails to load notification type.", persistence
                    .loadNotificationType(i + 1));
        }
    }

    /**
     * Stress test for the method <code>addResource</code> and
     * <code>loadResource</code>.
     * @throws Exception if there is any problem.
     */
    public void testAddLoadResourceSuccess() throws Exception {

        ResourceRole role = DBTestUtil.createResourceRole(5);
        role.setPhaseType(new Long(2007));
        persistence.addResourceRole(role);
        for (int i = 0; i < 10; i++) {
            persistence.addResource(StressTestsHelper.getResource(i + 1, 2007));
        }
        for (int i = 0; i < 10; i++) {
            assertNotNull("Fails to load resource.", persistence
                    .loadResource(i + 1));
        }
    }

    /**
     * Stress test for method <code>addResourceRole</code> and
     * <code>loadResourceRole</code>.
     * @throws Exception if there is any problem.
     */
    public void testAddloadResourceRoleSuccess() throws Exception {
        for (int i = 0; i < 10; i++) {
            persistence.addResourceRole(StressTestsHelper
                    .getResourceRole(i + 1));
        }

        for (int i = 0; i < 10; i++) {
            assertNotNull("Fails to load resource role.", persistence
                    .loadResourceRole(i + 1));
        }
    }

    /**
     * Stress test for the method <code>addNotificationType</code> and
     * <code>updateNotificationType</code>.
     * @throws Exception if there is any problem.
     */
    public void testAddUpdateNotificationTypeSuccess() throws Exception {
        for (int i = 0; i < 10; i++) {
            persistence.addNotificationType(StressTestsHelper
                    .getNotificationType(i + 1));
        }
        for (int i = 0; i < 10; i++) {
            NotificationType type = StressTestsHelper
                    .getNotificationType(i + 1);
            type.setModificationUser("reviewer");
            persistence.updateNotificationType(type);
        }
        assertEquals(
                "Fails to update notification type.",
                10,
                StressTestsHelper
                        .getRecordsNumber("SELECT count(*) FROM notification_type_lu WHERE modify_user = 'reviewer';"));
    }

    /**
     * Stress test for the method <code>addResource</code> and
     * <code>updateResource</code>.
     * @throws Exception if there is any problem.
     */
    public void testAddUpdateResourceSuccess() throws Exception {

        ResourceRole role = DBTestUtil.createResourceRole(5);
        role.setPhaseType(new Long(2007));
        persistence.addResourceRole(role);
        for (int i = 0; i < 10; i++) {
            persistence.addResource(StressTestsHelper.getResource(i + 1, 2007));
        }
        for (int i = 0; i < 10; i++) {
            Resource resource = StressTestsHelper.getResource(i + 1, 2007);
            resource.setModificationUser("reviewer");
            persistence.updateResource(resource);
        }
        assertEquals(
                "Fails to update resource.",
                10,
                StressTestsHelper
                        .getRecordsNumber("SELECT count(*) FROM resource where modify_user = 'reviewer';"));
    }

    /**
     * Stress test for method <code>addResourceRole</code> and
     * <code>updateResourceRole</code>.
     * @throws Exception if there is any problem.
     */
    public void testAddUpdateResourceRoleSuccess() throws Exception {
        for (int i = 0; i < 10; i++) {
            persistence.addResourceRole(StressTestsHelper
                    .getResourceRole(i + 1));
        }

        for (int i = 0; i < 10; i++) {
            ResourceRole role = StressTestsHelper.getResourceRole(i + 1);
            role.setModificationUser("reviewer");
            persistence.updateResourceRole(role);
        }

        assertEquals("Fails to update resource role.", 10, StressTestsHelper
                .getRecordsNumber("SELECT count(*) FROM resource_role_lu WHERE modify_user = 'reviewer';"));

    }

}
