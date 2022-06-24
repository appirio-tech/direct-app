/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.management.stresstests;

import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.cronos.onlinereview.autoscreening.management.ScreeningStatus;
import com.cronos.onlinereview.autoscreening.management.ScreeningTask;
import com.cronos.onlinereview.autoscreening.management.db.DefaultDbScreeningManager;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.ConfigManagerException;
import com.topcoder.util.idgenerator.IDGeneratorFactory;
import com.topcoder.util.idgenerator.IDGeneratorImpl;

import junit.framework.TestCase;

/**
 * Stress test for the DefaultDbScreeningManager class.
 *
 * @author waits
 * @version 1.0
 */
public class DefaultDbScreeningManagerTests extends TestCase {
    /**
     * Represents the load of stress test.
     */
    private static final int LOAD2 = 200;

    /**
     * Represents the load of stress test.
     */
    private static final int LOAD1 = 50;

    /**
     * Represents the upload base used int the tests.
     */
    private static final long UPLOAD = 100;

    /**
     * Represent the file name of insert data sql scripts.
     */
    private static final String INSERT_SQL_FILE = "test_files/stress_tests/insert.sql";

    /**
     * Represent the file name of delete data sql scripts.
     */
    private static final String DELETE_SQL_FILE = "test_files/stress_tests/delete.sql";

    /**
     * The constant of test config file name.
     */
    private static final String CONFIG_FILE = "config.xml";

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
    private DefaultDbScreeningManager defaultDbScreeningManager = null;

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
        loadNamespaces();
        // prepare test data
        dBConnectionFactory = new DBConnectionFactoryImpl(
                "com.cronos.onlinereview.autoscreening.management.db");
        executeSqlFile(DELETE_SQL_FILE);
        executeSqlFile(INSERT_SQL_FILE);
        Connection conn = dBConnectionFactory.createConnection();
        PreparedStatement stmt = conn.prepareStatement("insert into upload("
                + "upload_id, project_id, resource_id) values(?, ?, ?)");
        try {
            for (int i = 0; i < LOAD2 * 2; i++) {
                // upload table
                stmt.setLong(1, i + 100);
                stmt.setLong(2, 11);
                stmt.setLong(3, 41);
                stmt.executeUpdate();
            }
        } finally {
            stmt.close();
            conn.close();
        }

        iDGenerator = (IDGeneratorImpl) IDGeneratorFactory.getIDGenerator("screening_task_id_seq");
        defaultDbScreeningManager = new DefaultDbScreeningManager(dBConnectionFactory, iDGenerator);
    }

    /**
     * tearDown() routine. clear tables and namespaces loaded.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void tearDown() throws Exception {
        // clear data
        executeSqlFile(DELETE_SQL_FILE);
        releaseNamespaces();
    }

    /**
     * <p>
     * Stress test of <code>initiateScreening(long, String)</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testInitiateScreening_1_Stress() throws Exception {
        String operator = "stress test";

        long start = System.currentTimeMillis();

        System.out
                .println("----------- Start strss test of initiateScreening(long, String) ----------");
        for (int i = 0; i < LOAD1; i++) {
            defaultDbScreeningManager.initiateScreening(UPLOAD + i, operator);
        }

        System.out.println("---------- Running initiateScreening(long, String) " + LOAD1 + " times"
                + " costs:" + (System.currentTimeMillis() - start) + "ms -----------");

        // the uploads used to retrieve the initialized screen tasks
        long[] uploads = new long[LOAD1];
        for (int i = 0; i < uploads.length; i++) {
            uploads[i] = UPLOAD + i;
        }
        ScreeningTask[] tasks = defaultDbScreeningManager.getScreeningTasks(uploads);
        // verify the inserted data
        for (int i = 0; i < LOAD1; i++) {
            assertEquals("CreationUser", operator, tasks[i].getCreationUser());
            assertEquals("ModificationUser", operator, tasks[i].getModificationUser());
            assertEquals("ScreeningStatus", ScreeningStatus.PENDING, tasks[i].getScreeningStatus()
                    .getName());
            assertEquals("ScreeningStatus", 61, tasks[i].getScreeningStatus().getId());
            assertTrue("id", tasks[i].getId() != -1);
            assertEquals("Screener", -1, tasks[i].getScreener());
            assertEquals("Upload", UPLOAD + i, tasks[i].getUpload());
            assertEquals("StartTimestamp", null, tasks[i].getStartTimestamp());
        }
    }

    /**
     * <p>
     * Stress test of <code>initiateScreening(long, String)</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testInitiateScreening_2_Stress() throws Exception {
        String operator = "stress test";

        long start = System.currentTimeMillis();

        System.out
                .println("----------- Start strss test of initiateScreening(long, String) ----------");
        for (int i = 0; i < LOAD2; i++) {
            defaultDbScreeningManager.initiateScreening(UPLOAD + i, operator);
        }

        System.out.println("---------- Running initiateScreening(long, String) " + LOAD2 + " times"
                + " costs:" + (System.currentTimeMillis() - start) + "ms -----------");

        // the uploads used to retrieve the initialized screen tasks
        long[] uploads = new long[LOAD2];
        for (int i = 0; i < uploads.length; i++) {
            uploads[i] = UPLOAD + i;
        }
        ScreeningTask[] tasks = defaultDbScreeningManager.getScreeningTasks(uploads);
        // verify the inserted data
        for (int i = 0; i < LOAD2; i++) {
            assertEquals("CreationUser", operator, tasks[i].getCreationUser());
            assertEquals("ModificationUser", operator, tasks[i].getModificationUser());
            assertEquals("ScreeningStatus", ScreeningStatus.PENDING, tasks[i].getScreeningStatus()
                    .getName());
            assertEquals("ScreeningStatus", 61, tasks[i].getScreeningStatus().getId());
            assertTrue("id", tasks[i].getId() != -1);
            assertEquals("Screener", -1, tasks[i].getScreener());
            assertEquals("Upload", UPLOAD + i, tasks[i].getUpload());
            assertEquals("StartTimestamp", null, tasks[i].getStartTimestamp());
        }
    }

    /**
     * <p>
     * Stress test of <code>getScreeningDetails(long)</code> for proper behavior.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetScreeningDetails_1_Stress() throws Exception {
        // initiate screening tasks
        String operator = "stress test";
        for (int i = 0; i < LOAD1; i++) {
            defaultDbScreeningManager.initiateScreening(UPLOAD + i, operator);
        }

        long start = System.currentTimeMillis();
        System.out.println("----------- Start strss test of getScreeningDetails(long) ----------");
        for (int i = 0; i < LOAD1; i++) {
            defaultDbScreeningManager.getScreeningDetails(UPLOAD + i);
        }

        System.out.println("---------- Running getScreeningDetails(long) " + LOAD1 + " times"
                + " costs:" + (System.currentTimeMillis() - start) + "ms -----------");
    }

    /**
     * <p>
     * Stress test of <code>getScreeningDetails(long)</code> for proper behavior.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetScreeningDetails_2_Stress() throws Exception {
        // initiate screening tasks
        String operator = "stress test";
        for (int i = 0; i < LOAD2; i++) {
            defaultDbScreeningManager.initiateScreening(UPLOAD + i, operator);
        }

        long start = System.currentTimeMillis();
        System.out.println("----------- Start strss test of getScreeningDetails(long) ----------");
        for (int i = 0; i < LOAD2; i++) {
            defaultDbScreeningManager.getScreeningDetails(UPLOAD + i);
        }

        System.out.println("---------- Running getScreeningDetails(long) " + LOAD2 + " times"
                + " costs:" + (System.currentTimeMillis() - start) + "ms -----------");
    }

    /**
     * <p>
     * Stress test of <code>getScreeningTasks(long[])</code> for proper behavior.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testgetScreeningTasksLongArray_1_Stress() throws Exception {
        // initiate screening tasks
        String operator = "stress test";
        for (int i = 0; i < LOAD1; i++) {
            defaultDbScreeningManager.initiateScreening(UPLOAD + i, operator);
        }
        // the uploads added currently
        long[] uploads = new long[LOAD1];
        for (int i = 0; i < uploads.length; i++) {
            uploads[i] = UPLOAD + i;
        }

        long start = System.currentTimeMillis();
        System.out.println("----------- Start strss test of getScreeningTasks(long[]) ----------");
        for (int i = 0; i < LOAD1; i++) {
            defaultDbScreeningManager.getScreeningTasks(uploads);
        }
        System.out.println("---------- Running getScreeningTasks(long[]) " + LOAD1 + " times"
                + " costs:" + (System.currentTimeMillis() - start) + "ms -----------");
    }

    /**
     * <p>
     * Stress test of <code>getScreeningTasks(long[])</code> for proper behavior.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testgetScreeningTasksLongArray_2_Stress() throws Exception {
        // initiate screening tasks
        String operator = "stress test";
        for (int i = 0; i < LOAD2; i++) {
            defaultDbScreeningManager.initiateScreening(UPLOAD + i, operator);
        }
        // the uploads added currently
        long[] uploads = new long[LOAD2];
        for (int i = 0; i < uploads.length; i++) {
            uploads[i] = UPLOAD + i;
        }

        long start = System.currentTimeMillis();
        System.out.println("----------- Start strss test of getScreeningTasks(long[]) ----------");
        for (int i = 0; i < LOAD2; i++) {
            defaultDbScreeningManager.getScreeningTasks(uploads);
        }
        System.out.println("---------- Running getScreeningTasks(long[]) " + LOAD2 + " times"
                + " costs:" + (System.currentTimeMillis() - start) + "ms -----------");
    }

    /**
     * <p>
     * Stress test of <code>getScreeningTasks(long[], boolean)</code> for proper behavior.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testgetScreeningTasksLongArrayBoolean_1_Stress() throws Exception {
        // initiate screening tasks
        String operator = "stress test";
        for (int i = 0; i < LOAD1; i++) {
            defaultDbScreeningManager.initiateScreening(UPLOAD + i, operator);
        }
        // the uploads added currently
        long[] uploads = new long[LOAD1];
        for (int i = 0; i < uploads.length; i++) {
            uploads[i] = UPLOAD + i;
        }

        long start = System.currentTimeMillis();
        System.out
                .println("----------- Start strss test of getScreeningTasks(long[], boolean) ----------");
        for (int i = 0; i < LOAD1; i++) {
            defaultDbScreeningManager.getScreeningTasks(uploads, false);
        }
        System.out.println("---------- Running getScreeningTasks(long[], boolean) " + LOAD1
                + " times" + " costs:" + (System.currentTimeMillis() - start) + "ms -----------");
    }

    /**
     * <p>
     * Stress test of <code>getScreeningTasks(long[], boolean)</code> for proper behavior.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testgetScreeningTasksLongArrayBoolean_2_Stress() throws Exception {
        // initiate screening tasks
        String operator = "stress test";
        for (int i = 0; i < LOAD2; i++) {
            defaultDbScreeningManager.initiateScreening(UPLOAD + i, operator);
        }
        // the uploads added currently
        long[] uploads = new long[LOAD2];
        for (int i = 0; i < uploads.length; i++) {
            uploads[i] = UPLOAD + i;
        }

        long start = System.currentTimeMillis();
        System.out
                .println("----------- Start strss test of getScreeningTasks(long[], boolean) ----------");
        for (int i = 0; i < LOAD2; i++) {
            defaultDbScreeningManager.getScreeningTasks(uploads, false);
        }
        System.out.println("---------- Running getScreeningTasks(long[], boolean) " + LOAD2
                + " times" + " costs:" + (System.currentTimeMillis() - start) + "ms -----------");
    }

    /**
     * <p>
     * Load all namespaces for testing.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    private void loadNamespaces() throws Exception {
        ConfigManager configManager = ConfigManager.getInstance();

        try {
            releaseNamespaces();
            configManager.add(CONFIG_FILE);
        } catch (ConfigManagerException e) {
            e.printStackTrace();
        }
    }

    /**
     * <p>
     * Release all test namespaces from ConfigManager.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    private void releaseNamespaces() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();

        for (Iterator it = cm.getAllNamespaces(); it.hasNext();) {
            cm.removeNamespace((String) it.next());
        }
    }

    /**
     * Execute the sql statements in a file.
     *
     * @param filename
     *            the sql file.
     * @throws Exception
     *             to JUnit
     */
    private void executeSqlFile(String filename) throws Exception {
        Reader file = new FileReader(filename);
        char[] buffer = new char[1024];
        int retLength = 0;
        StringBuffer content = new StringBuffer();

        while ((retLength = file.read(buffer)) >= 0) {
            content.append(buffer, 0, retLength);
        }

        file.close();

        List sqls = new ArrayList();
        int lastIndex = 0;

        // parse the sqls
        for (int i = 0; i < content.length(); i++) {
            if (content.charAt(i) == ';') {
                sqls.add(content.substring(lastIndex, i).trim());
                lastIndex = i + 1;
            }
        }

        // get the connection
        Connection conn = dBConnectionFactory.createConnection();
        Statement stmt = conn.createStatement();

        try {
            // excute the sql in the file
            for (int i = 0; i < sqls.size(); i++) {
                stmt.executeUpdate((String) sqls.get(i));
            }
        } finally {
            stmt.close();
            conn.close();
        }
    }
}
