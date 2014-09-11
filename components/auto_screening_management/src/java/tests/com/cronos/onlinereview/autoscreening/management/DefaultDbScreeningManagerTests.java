/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.management;

import com.cronos.onlinereview.autoscreening.management.db.DbScreeningManager;
import com.cronos.onlinereview.autoscreening.management.db.DefaultDbScreeningManager;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.util.idgenerator.IDGeneratorFactory;
import com.topcoder.util.idgenerator.IDGeneratorImpl;

import junit.framework.TestCase;

/**
 * <p>
 * Test cases of DefaultDbScreeningManager. Tests the class for proper behavior.
 * </p>
 *
 * @author haozhangr
 * @version 1.0
 */
public class DefaultDbScreeningManagerTests extends TestCase {
    /**
     * Represents the operator used in tests.
     */
    public static final String OPERATOR = "admin";

    /**
     * Represents the upload used in tests.
     */
    public static final long UPLOAD_NOTEXIST = 56;

    /**
     * Represents the upload used in tests.
     */
    public static final long UPLOAD_EXIST = 51;

    /**
     * <p>
     * The connectionName used for testing.
     * </p>
     */
    private static final String NAME = "Informix_Connection_Producer";

    /**
     * <p>
     * The IDGenerator used for testing.
     * </p>
     */
    private IDGeneratorImpl iDGenerator = null;

    /**
     * <p>
     * The DBConnectionFactory used for testing.
     * </p>
     */
    private DBConnectionFactoryImpl dBConnectionFactory = null;

    /**
     * <p>
     * An instance of <code>DefaultDbScreeningManager</code> which is tested.
     * </p>
     */
    private DefaultDbScreeningManager target = null;

    /**
     * <p>
     * setUp() routine. Creates test DefaultDbScreeningManager instance, DBConnectionFactory and
     * IDGenerator used for test.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        TestHelper.loadNamespaces();
        TestHelper.prepareData();
        dBConnectionFactory = new DBConnectionFactoryImpl(
                "com.cronos.onlinereview.autoscreening.management.db");
        iDGenerator = (IDGeneratorImpl) IDGeneratorFactory.getIDGenerator("screening_task_id_seq");
        target = new DefaultDbScreeningManager(dBConnectionFactory, iDGenerator);
    }

    /**
     * tearDown() routine. clear tables and namespaces loaded.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void tearDown() throws Exception {
        TestHelper.clearData();
        TestHelper.releaseNamespaces();
    }

    /**
     * <p>
     * Inheritance test.
     * </p>
     *
     * <p>
     * Verifies DefaultDbScreeningManager implements DbScreeningManager.
     * </p>
     */
    public void testInheritance() {
        assertTrue("DefaultDbScreeningManager does not implement DbScreeningManager.",
                target instanceof DbScreeningManager);
    }

    /**
     * <p>
     * Accuracy test. Tests the
     * <code>DefaultDbScreeningManager(DBConnectionFactoryImpl, IDGeneratorImpl)</code> Create for
     * proper behavior.
     * </p>
     */
    public void testCreateDBConnectionFactoryIDGenerator_accuracy() {
        assertNotNull("Failed to initialize DefaultDbScreeningManager object.", target);
    }

    /**
     * <p>
     * Failure test. Tests the
     * <code>DefaultDbScreeningManager(DBConnectionFactoryImpl, IDGeneratorImpl)</code> for proper
     * behavior. Verify that IllegalArgumentException is thrown for invalid parameter.
     * </p>
     */
    public void testCreateDBConnectionFactoryIDGenerator_1_failure() {
        try {
            new DefaultDbScreeningManager(null, iDGenerator);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test. Tests the
     * <code>DefaultDbScreeningManager(DBConnectionFactoryImpl, IDGeneratorImpl)</code> for proper
     * behavior. Verify that IllegalArgumentException is thrown for invalid parameter.
     * </p>
     */
    public void testCreateDBConnectionFactoryIDGenerator_2_failure() {
        try {
            new DefaultDbScreeningManager(dBConnectionFactory, null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Accuracy test. Tests the
     * <code>DefaultDbScreeningManager(DBConnectionFactoryImpl, String, IDGeneratorImpl)</code>
     * Create for proper behavior.
     * </p>
     */
    public void testCreateDBConnectionFactoryStringIDGenerator_accuracy() {
        target = new DefaultDbScreeningManager(dBConnectionFactory, NAME, iDGenerator);
        assertNotNull("Failed to initialize DefaultDbScreeningManager object.", target);
    }

    /**
     * <p>
     * Failure test. Tests the
     * <code>DefaultDbScreeningManager(DBConnectionFactoryImpl, String, IDGeneratorImpl)</code>
     * for proper behavior. Verify that IllegalArgumentException is thrown for invalid parameter.
     * </p>
     */
    public void testCreateDBConnectionFactoryStringIDGenerator_1_failure() {
        try {
            new DefaultDbScreeningManager(null, NAME, iDGenerator);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test. Tests the
     * <code>DefaultDbScreeningManager(DBConnectionFactoryImpl, String, IDGeneratorImpl)</code>
     * for proper behavior. Verify that IllegalArgumentException is thrown for invalid parameter.
     * </p>
     */
    public void testCreateDBConnectionFactoryStringIDGenerator_2_failure() {
        try {
            new DefaultDbScreeningManager(dBConnectionFactory, null, iDGenerator);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test. Tests the
     * <code>DefaultDbScreeningManager(DBConnectionFactoryImpl, String, IDGeneratorImpl)</code>
     * for proper behavior. Verify that IllegalArgumentException is thrown for invalid parameter.
     * </p>
     */
    public void testCreateDBConnectionFactoryStringIDGenerator_3_failure() {
        try {
            new DefaultDbScreeningManager(dBConnectionFactory, "  ", iDGenerator);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test. Tests the
     * <code>DefaultDbScreeningManager(DBConnectionFactoryImpl, String, IDGeneratorImpl)</code>
     * for proper behavior. Verify that IllegalArgumentException is thrown for invalid parameter.
     * </p>
     */
    public void testCreateDBConnectionFactoryStringIDGenerator_4_failure() {
        try {
            new DefaultDbScreeningManager(dBConnectionFactory, NAME, null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>initiateScreening(long, String)</code> for proper behavior.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testInitiateScreening_accuracy() throws Exception {
        // initiate screening
        target.initiateScreening(UPLOAD_NOTEXIST, OPERATOR);
        ScreeningTask task = target.getScreeningTasks(new long[] {UPLOAD_NOTEXIST})[0];

        assertEquals("CreationUser", OPERATOR, task.getCreationUser());
        assertEquals("ModificationUser", OPERATOR, task.getModificationUser());
        assertEquals("ScreeningStatus", ScreeningStatus.PENDING, task.getScreeningStatus()
                .getName());
        assertEquals("ScreeningStatus", 61, task.getScreeningStatus().getId());
        assertTrue("id", task.getId() != -1);
        assertEquals("Screener", -1, task.getScreener());
        assertEquals("Upload", UPLOAD_NOTEXIST, task.getUpload());
        assertEquals("StartTimestamp", null, task.getStartTimestamp());
    }

    /**
     * <p>
     * Failure test. Tests the <code>initiateScreening(long, String)</code> for proper behavior.
     * Verify that IllegalArgumentException is thrown for invalid parameter.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testInitiateScreening_1_failure() throws Exception {
        try {
            target.initiateScreening(0, OPERATOR);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test. Tests the <code>initiateScreening(long, String)</code> for proper behavior.
     * Verify that IllegalArgumentException is thrown for invalid parameter.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testInitiateScreening_2_failure() throws Exception {
        try {
            target.initiateScreening(-1, OPERATOR);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test. Tests the <code>initiateScreening(long, String)</code> for proper behavior.
     * Verify that IllegalArgumentException is thrown for invalid parameter.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testInitiateScreening_3_failure() throws Exception {
        try {
            target.initiateScreening(UPLOAD_NOTEXIST, null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test. Tests the <code>initiateScreening(long, String)</code> for proper behavior.
     * Verify that IllegalArgumentException is thrown for invalid parameter.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testInitiateScreening_4_failure() throws Exception {
        try {
            target.initiateScreening(UPLOAD_NOTEXIST, "  ");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test. Tests the <code>initiateScreening(long, String)</code> for proper behavior.
     * Verify that ScreeningTaskAlreadyExistsException is thrown if the upload has been used to
     * initiate a screening task.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testInitiateScreening_5_failure() throws Exception {
        try {
            target.initiateScreening(UPLOAD_EXIST, OPERATOR);
            fail("ScreeningTaskAlreadyExistsException expected.");
        } catch (ScreeningTaskAlreadyExistsException e) {
            // ok
        }
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>getScreeningDetails(long)</code> for proper behavior.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetScreeningDetails_1_accuracy() throws Exception {
        ScreeningTask task = target.getScreeningDetails(UPLOAD_EXIST);

        ScreeningResult[] screeningResults = task.getAllScreeningResults();
        assertEquals("AllScreeningResults's length", 5, screeningResults.length);
        // verify that screening result is got correctly
        ScreeningResult screeningResult = screeningResults[2];
        assertEquals("screeningResult is incorrect.", 113, screeningResult.getId());
        assertEquals("screeningResult is incorrect.", "dynamic_response_text3", screeningResult
                .getDynamicText());
        ScreeningResponse screeningResponse = screeningResult.getScreeningResponse();
        // verify that screeningResponse result is got correctly
        assertEquals("screeningResponse is incorrect.", 103, screeningResponse.getId());
        assertEquals("screeningResponse is incorrect.", "response_code_3", screeningResponse
                .getResponseCode());
        assertEquals("screeningResponse is incorrect.", "response_text3", screeningResponse
                .getResponseText());
        ResponseSeverity responseSeverity = screeningResponse.getResponseSeverity();
        assertEquals("responseSeverity is incorrect.", 93, responseSeverity.getId());
        assertEquals("responseSeverity is incorrect.", "response_severity_lu_name_3",
                responseSeverity.getName());

        assertEquals("CreationUser", "screening_task_create_user_1", task.getCreationUser());
        assertEquals("ModificationUser", "screening_task_modify_user_1", task.getModificationUser());
        assertEquals("ScreeningStatus", ScreeningStatus.SCREENING, task.getScreeningStatus()
                .getName());
        assertEquals("ScreeningStatus", 62, task.getScreeningStatus().getId());
        assertEquals("id", 71, task.getId());
        assertEquals("Screener", 81, task.getScreener());
        assertEquals("Upload", UPLOAD_EXIST, task.getUpload());
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>getScreeningDetails(long)</code> for proper behavior.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetScreeningDetails_2_accuracy() throws Exception {
        ScreeningTask task = target.getScreeningDetails(53);

        assertEquals("Screener should not be set.", -1, task.getScreener());
        assertEquals("StartTimestamp should not be set.", null, task.getStartTimestamp());
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>getScreeningDetails(long)</code> for proper behavior.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetScreeningDetails_3_accuracy() throws Exception {
        ScreeningTask task = target.getScreeningDetails(54);

        assertEquals("Screener should not be set.", -1, task.getScreener());
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>getScreeningDetails(long)</code> for proper behavior.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetScreeningDetails_4_accuracy() throws Exception {
        ScreeningTask task = target.getScreeningDetails(55);

        assertEquals("StartTimestamp should not be set.", null, task.getStartTimestamp());
    }

    /**
     * <p>
     * Failure test. Tests the <code>getScreeningDetails(long)</code> for proper behavior. Verify
     * that IllegalArgumentException is thrown for invalid parameter.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetScreeningDetails_1_failure() throws Exception {
        try {
            target.getScreeningDetails(0);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test. Tests the <code>getScreeningDetails(long)</code> for proper behavior. Verify
     * that IllegalArgumentException is thrown for invalid parameter.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetScreeningDetails_2_failure() throws Exception {
        try {
            target.getScreeningDetails(-1);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test. Tests the <code>getScreeningDetails(long)</code> for proper behavior. Verify
     * that ScreeningTaskDoesNotExistException is thrown if the upload was not initiated a screening
     * task.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetScreeningDetails_3_failure() throws Exception {
        try {
            target.getScreeningDetails(UPLOAD_NOTEXIST);
            fail("ScreeningTaskDoesNotExistException expected.");
        } catch (ScreeningTaskDoesNotExistException e) {
            // ok
        }
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>getScreeningTasks(long[])</code> for proper behavior.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testgetScreeningTasksLongArray_accuracy() throws Exception {
        ScreeningTask[] tasks = target
                .getScreeningTasks(new long[] {UPLOAD_EXIST, UPLOAD_EXIST + 1});
        assertEquals("The number of tasks.", 2, tasks.length);
        // the accuracy of tasks will not been test for
        // this has been done in testGetScreeningDetails_accuracy.
    }

    /**
     * <p>
     * Failure test. Tests the <code>getScreeningTasks(long[])</code> for proper behavior. Verify
     * that IllegalArgumentException is thrown for invalid parameter.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testgetScreeningTasksLongArray_1_failure() throws Exception {
        try {
            target.getScreeningTasks(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test. Tests the <code>getScreeningTasks(long[])</code> for proper behavior. Verify
     * that IllegalArgumentException is thrown for invalid parameter.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testgetScreeningTasksLongArray_2_failure() throws Exception {
        try {
            target.getScreeningTasks(new long[] {1, -1});
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test. Tests the <code>getScreeningTasks(long[])</code> for proper behavior. Verify
     * that IllegalArgumentException is thrown for invalid parameter.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testgetScreeningTasksLongArray_3_failure() throws Exception {
        try {
            target.getScreeningTasks(new long[] {1, 0});
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test. Tests the <code>getScreeningTasks(long[])</code> for proper behavior. Verify
     * that ScreeningTaskDoesNotExistException will be thrown if any of the uploads was not
     * initiated a screening task
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testgetScreeningTasksLongArray_4_failure() throws Exception {
        try {
            target.getScreeningTasks(new long[] {UPLOAD_EXIST, UPLOAD_NOTEXIST});
            fail("ScreeningTaskDoesNotExistException expected.");
        } catch (ScreeningTaskDoesNotExistException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test. Tests the <code>getScreeningTasks(long[])</code> for proper behavior. Verify
     * that IllegalArgumentException is thrown for invalid parameter.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testgetScreeningTasksLongArray_5_failure() throws Exception {
        try {
            target.getScreeningTasks(new long[] {1, 2, 3, 3, 4});
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test. Tests the <code>getScreeningTasks(long[])</code> for proper behavior. Verify
     * that IllegalArgumentException is thrown for invalid parameter.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testgetScreeningTasksLongArray_6_failure() throws Exception {
        try {
            target.getScreeningTasks(new long[] {1, 2, 3, 4, 5, 3});
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test. Tests the <code>getScreeningTasks(long[])</code> for proper behavior. Verify
     * that IllegalArgumentException is thrown for invalid parameter.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testgetScreeningTasksLongArray_7_failure() throws Exception {
        try {
            target.getScreeningTasks(new long[] {1, 1});
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>getScreeningTasks(long[], boolean)</code> for proper
     * behavior.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testgetScreeningTasksLongArrayBoolean_1_accuracy() throws Exception {
        ScreeningTask[] tasks = target.getScreeningTasks(
                new long[] {UPLOAD_EXIST, UPLOAD_EXIST + 1}, false);
        assertEquals("The number of tasks.", 2, tasks.length);
        for (int i = 0; i < tasks.length; i++) {
            assertEquals("Tasks should be sorted.", UPLOAD_EXIST + i, tasks[i].getUpload());
        }
        // the accuracy of tasks will not been test for
        // this has been done in testGetScreeningDetails_accuracy.
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>getScreeningTasks(long[], boolean)</code> for proper
     * behavior. Verify that non-exist task is ok if flag is set to true.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testgetScreeningTasksLongArrayBoolean_2_accuracy() throws Exception {

        ScreeningTask[] tasks = target.getScreeningTasks(new long[] {UPLOAD_EXIST, UPLOAD_NOTEXIST,
                UPLOAD_EXIST + 1}, true);
        assertEquals("The number of tasks.", 3, tasks.length);
        assertEquals("Tasks should be sorted.", UPLOAD_EXIST, tasks[0].getUpload());
        assertEquals("Tasks should be sorted.", null, tasks[1]);
        assertEquals("Tasks should be sorted.", UPLOAD_EXIST + 1, tasks[2].getUpload());
        // the accuracy of tasks will not been test for
        // this has been done in testGetScreeningDetails_accuracy.
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>getScreeningTasks(long[], boolean)</code> for proper
     * behavior. Verify that the screener_id and start_timestamp are null will not cause exception.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testgetScreeningTasksLongArrayBoolean_3_accuracy() throws Exception {
        ScreeningTask task = target.getScreeningTasks(new long[] {53}, true)[0];
        assertEquals("Screener", -1, task.getScreener());
        assertEquals("StartTimestamp", null, task.getStartTimestamp());
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>getScreeningTasks(long[], boolean)</code> for proper
     * behavior. Verify that the screener_id is null will not cause exception.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testgetScreeningTasksLongArrayBoolean_4_accuracy() throws Exception {
        ScreeningTask task = target.getScreeningTasks(new long[] {54}, true)[0];
        assertEquals("Screener", -1, task.getScreener());
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>getScreeningTasks(long[], boolean)</code> for proper
     * behavior. Verify that the start_timestamp is null will not cause exception.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testgetScreeningTasksLongArrayBoolean_5_accuracy() throws Exception {
        ScreeningTask task = target.getScreeningTasks(new long[] {55}, true)[0];
        assertEquals("StartTimestamp", null, task.getStartTimestamp());
    }

    /**
     * <p>
     * Failure test. Tests the <code>getScreeningTasks(long[], boolean)</code> for proper
     * behavior. Verify that IllegalArgumentException is thrown for invalid parameter.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testgetScreeningTasksLongArrayBoolean_1_failure() throws Exception {
        try {
            target.getScreeningTasks(null, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test. Tests the <code>getScreeningTasks(long[], boolean)</code> for proper
     * behavior. Verify that IllegalArgumentException is thrown for invalid parameter.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testgetScreeningTasksLongArrayBoolean_2_failure() throws Exception {
        try {
            target.getScreeningTasks(new long[] {1, 0}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test. Tests the <code>getScreeningTasks(long[], boolean)</code> for proper
     * behavior. Verify that IllegalArgumentException is thrown for invalid parameter.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testgetScreeningTasksLongArrayBoolean_3_failure() throws Exception {
        try {
            target.getScreeningTasks(new long[] {1, -1}, true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test. Tests the <code>getScreeningTasks(long[], boolean)</code> for proper
     * behavior. Verify that ScreeningTaskDoesNotExistException will be thrown if any of the uploads
     * was not initiated a screening task, and allowNotExist is false.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testgetScreeningTasksLongArrayBoolean_4_failure() throws Exception {
        try {
            target.getScreeningTasks(new long[] {UPLOAD_NOTEXIST}, false);
            fail("ScreeningTaskDoesNotExistException expected.");
        } catch (ScreeningTaskDoesNotExistException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test. Tests the <code>getScreeningTasks(long[], boolean)</code> for proper
     * behavior. Verify that IllegalArgumentException is thrown for invalid parameter.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testgetScreeningTasksLongArrayBoolean_5_failure() throws Exception {
        try {
            target.getScreeningTasks(new long[] {1, 2, 3, 3, 4}, false);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test. Tests the <code>getScreeningTasks(long[], boolean)</code> for proper
     * behavior. Verify that IllegalArgumentException is thrown for invalid parameter.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testgetScreeningTasksLongArrayBoolean_6_failure() throws Exception {
        try {
            target.getScreeningTasks(new long[] {1, 2, 3, 4, 5, 3}, false);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test. Tests the <code>getScreeningTasks(long[], boolean)</code> for proper
     * behavior. Verify that IllegalArgumentException is thrown for invalid parameter.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testgetScreeningTasksLongArrayBoolean_7_failure() throws Exception {
        try {
            target.getScreeningTasks(new long[] {1, 1}, false);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }
}
