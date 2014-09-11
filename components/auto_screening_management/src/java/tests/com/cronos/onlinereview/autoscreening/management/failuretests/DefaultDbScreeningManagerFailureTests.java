/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.management.failuretests;

import java.util.Random;

import junit.framework.TestCase;

import com.cronos.onlinereview.autoscreening.management.PersistenceException;
import com.cronos.onlinereview.autoscreening.management.ScreeningTaskAlreadyExistsException;
import com.cronos.onlinereview.autoscreening.management.ScreeningTaskDoesNotExistException;
import com.cronos.onlinereview.autoscreening.management.db.DefaultDbScreeningManager;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.util.idgenerator.IDGeneratorFactory;
import com.topcoder.util.idgenerator.IDGeneratorImpl;


/**
 * <p>
 * Failure test for DefaultDbScreeningManager.
 * </p>
 *
 * @author GavinWang
 * @version 1.0
 */
public class DefaultDbScreeningManagerFailureTests extends TestCase {
    /** an instance of DefaultDbScreeningManager for testing. */
    private DefaultDbScreeningManager manager;

    /**
     * <p>
     * Set up each test.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        TestHelper.loadConfig();

        TestHelper.executeSQLFile("failure/clean.sql");
        TestHelper.executeSQLFile("failure/data.sql");
        manager = new DefaultDbScreeningManager(new DBConnectionFactoryImpl(
                    "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl"),
                (IDGeneratorImpl) IDGeneratorFactory.getIDGenerator("screening_task_id_seq"));
    }

    /**
     * <p>
     * Clean up each test.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        TestHelper.executeSQLFile("failure/clean.sql");
        TestHelper.cleanConfig();
    }

    /**
     * Failure test for DefaultDbScreeningManager(DBConnectionFactory, IDGenerator). Inputs: null DBConnectionFactory.
     * Expectation: IllegalArgumentException should be thrown.
     *
     * @throws Exception to JUnit
     */
    public void testDefaultDbScreeningManager2NullDBConnectionFactory()
        throws Exception {
        try {
            new DefaultDbScreeningManager(null,
                (IDGeneratorImpl) IDGeneratorFactory.getIDGenerator("screening_task_id_seq"));
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for DefaultDbScreeningManager(DBConnectionFactory, IDGenerator). Inputs: null DBConnectionFactory.
     * Expectation: IllegalArgumentException should be thrown.
     *
     * @throws Exception to JUnit
     */
    public void testDefaultDbScreeningManager2NullIDGenerator()
        throws Exception {
        try {
            new DefaultDbScreeningManager(new DBConnectionFactoryImpl(), null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for DefaultDbScreeningManager(DBConnectionFactory, String, IDGenerator). Inputs: null
     * DBConnectionFactory. Expectation: IllegalArgumentException should be thrown.
     *
     * @throws Exception to JUnit
     */
    public void testDefaultDbScreeningManager3NullDBConnectionFactory()
        throws Exception {
        try {
            new DefaultDbScreeningManager(null, "auto_screening",
                (IDGeneratorImpl) IDGeneratorFactory.getIDGenerator("screening_task_id_seq"));
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for DefaultDbScreeningManager(DBConnectionFactory, String, IDGenerator). Inputs: null IDGenerator.
     * Expectation: IllegalArgumentException should be thrown.
     *
     * @throws Exception to JUnit
     */
    public void testDefaultDbScreeningManager3NullIDGenerator()
        throws Exception {
        try {
            new DefaultDbScreeningManager(new DBConnectionFactoryImpl(), "auto_screening", null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for DefaultDbScreeningManager(DBConnectionFactory, String, IDGenerator). Inputs: null
     * connectionName. Expectation: IllegalArgumentException should be thrown.
     *
     * @throws Exception to JUnit
     */
    public void testDefaultDbScreeningManager3NullConnectionName()
        throws Exception {
        try {
            new DefaultDbScreeningManager(new DBConnectionFactoryImpl(), null,
                (IDGeneratorImpl) IDGeneratorFactory.getIDGenerator("screening_task_id_seq"));
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for DefaultDbScreeningManager(DBConnectionFactory, String, IDGenerator). Inputs: empty
     * connectionName. Expectation: IllegalArgumentException should be thrown.
     *
     * @throws Exception to JUnit
     */
    public void testDefaultDbScreeningManager3EmptyConnectionName()
        throws Exception {
        try {
            new DefaultDbScreeningManager(new DBConnectionFactoryImpl(), "   ",
                (IDGeneratorImpl) IDGeneratorFactory.getIDGenerator("screening_task_id_seq"));
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for initiateScreening(long, String). Inputs: zero upload. Expectation: IllegalArgumentException
     * should be thrown.
     *
     * @throws Exception to JUnit
     */
    public void testInitiateScreeningZeroUpload() throws Exception {
        try {
            this.manager.initiateScreening(0L, "topcoder");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for initiateScreening(long, String). Inputs: negative upload. Expectation: IllegalArgumentException
     * should be thrown.
     *
     * @throws Exception to JUnit
     */
    public void testInitiateScreeningNegativeUpload() throws Exception {
        int i = new Random().nextInt(Integer.MAX_VALUE) + 1;

        try {
            this.manager.initiateScreening(-1L * i, "topcoder");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for initiateScreening(long, String). Inputs: null operator. Expectation: IllegalArgumentException
     * should be thrown.
     *
     * @throws Exception to JUnit
     */
    public void testInitiateScreeningNullOperator() throws Exception {
        try {
            this.manager.initiateScreening(1L, null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for initiateScreening(long, String). Inputs: empty operator. Expectation: IllegalArgumentException
     * should be thrown.
     *
     * @throws Exception to JUnit
     */
    public void testInitiateScreeningEmptyOperator() throws Exception {
        try {
            this.manager.initiateScreening(2L, "    ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for initiateScreening(long, String). Inputs: legal inputs. Expectation: should be ok.
     *
     * @throws Exception to JUnit
     */
    public void testInitiateScreeningLegal() throws Exception {
        this.manager.initiateScreening(2L, "topcoder");
    }

    /**
     * Failure test for initiateScreening(long, String). Inputs: an already initiated upload. Expectation:
     * ScreeningTaskAlreadyExistsException should be thrown.
     *
     * @throws Exception to JUnit
     */
    public void testInitiateScreeningMultipleTimes() throws Exception {
        this.manager.initiateScreening(2L, "topcoder");

        try {
            this.manager.initiateScreening(2L, "topcoder2");
            fail("ScreeningTaskAlreadyExistsException should be thrown.");
        } catch (ScreeningTaskAlreadyExistsException e) {
            // expected
        }
    }

    /**
     * Failure test for initiateScreening(long, String). Inputs: an unrecognized upload. Expectation:
     * PersistenceException should be thrown.
     *
     * @throws Exception to JUnit
     */
    public void testInitiateScreeningPersistenceException()
        throws Exception {
        try {
            this.manager.initiateScreening(4L, "topcoder2");
            fail("PersistenceException should be thrown.");
        } catch (PersistenceException e) {
            // expected
        }
    }

    /**
     * Failure test for getScreeningDetails(long). Inputs: zero upload id. Expectation: IllegalArgumentException should
     * be thrown.
     *
     * @throws Exception to JUnit
     */
    public void testGetScreeningDetailsZeroUpload() throws Exception {
        try {
            this.manager.getScreeningDetails(0L);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for getScreeningDetails(long). Inputs: negative upload id. Expectation: IllegalArgumentException
     * should be thrown.
     *
     * @throws Exception to JUnit
     */
    public void testGetScreeningDetailsNegativeUpload()
        throws Exception {
        int i = new Random().nextInt(Integer.MAX_VALUE) + 1;

        try {
            this.manager.getScreeningDetails(-1L * i);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for getScreeningDetails(long). Inputs: unknown upload. Expectation:
     * ScreeningTaskDoesNotExistException should be thrown.
     *
     * @throws Exception to JUnit
     */
    public void testGetScreeningDetailsUnknownUpload()
        throws Exception {
        try {
            this.manager.getScreeningDetails(2L);
            fail("ScreeningTaskDoesNotExistException should be thrown.");
        } catch (ScreeningTaskDoesNotExistException e) {
            // expected
        }
    }

    /**
     * Failure test for getScreeningDetails(long). Inputs: known upload. Expectation: should be ok.
     *
     * @throws Exception to JUnit
     */
    public void testGetScreeningDetailsLegalUpload() throws Exception {
        this.manager.initiateScreening(2L, "topcoder");
        this.manager.getScreeningDetails(2L);
    }

    /**
     * Failure test for getScreeningTasks(long[]). Inputs: null array. Expectation: IllegalArgumentException should be
     * thrown.
     *
     * @throws Exception to JUnit
     */
    public void testGetScreeningTasksNull() throws Exception {
        try {
            this.manager.getScreeningTasks(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for getScreeningTasks(long[]). Inputs: array conaining zero uploads. Expectation:
     * IllegalArgumentException should be thrown.
     *
     * @throws Exception to JUnit
     */
    public void testGetScreeningTasksZeroUploads() throws Exception {
        try {
            this.manager.getScreeningTasks(new long[] {0L, 1L, 3L, 0L});
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for getScreeningTasks(long[]). Inputs: array conaining negative uploads. Expectation:
     * IllegalArgumentException should be thrown.
     *
     * @throws Exception to JUnit
     */
    public void testGetScreeningTasksNegativeUploads()
        throws Exception {
        try {
            this.manager.getScreeningTasks(new long[] {-1L, 1L, -3L, 5L});
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for getScreeningTasks(long[]). Inputs: array conaining duplicate uploads. Expectation:
     * IllegalArgumentException should be thrown.
     *
     * @throws Exception to JUnit
     */
    public void testGetScreeningTasksDuplicateUploads()
        throws Exception {
        try {
            this.manager.getScreeningTasks(new long[] {1L, 2L, 2L});
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for getScreeningTasks(long[]). Inputs: array conaining unknown uploads. Expectation:
     * ScreeningTaskDoesNotExistException should be thrown.
     *
     * @throws Exception to JUnit
     */
    public void testGetScreeningTasksUnknownUploads() throws Exception {
        this.manager.initiateScreening(2L, "topcoder1");

        try {
            this.manager.getScreeningTasks(new long[] {2L, 3L});
            fail("ScreeningTaskDoesNotExistException should be thrown.");
        } catch (ScreeningTaskDoesNotExistException e) {
            // expected
        }
    }

    /**
     * Failure test for getScreeningTasks(long[]). Inputs: each is known upload. Expectation: should be ok.
     *
     * @throws Exception to JUnit
     */
    public void testGetScreeningTasksAllKnownUploads()
        throws Exception {
        this.manager.initiateScreening(2L, "topcoder1");
        this.manager.initiateScreening(3L, "topcoder2");

        this.manager.getScreeningTasks(new long[] {2L, 3L});
    }

    /**
     * Failure test for getScreeningTasks(long[], boolean). Inputs: null array. Expectation: IllegalArgumentException
     * should be thrown.
     *
     * @throws Exception to JUnit
     */
    public void testGetScreeningTasks2NullUploads() throws Exception {
        try {
            this.manager.getScreeningTasks(null, false);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for getScreeningTasks(long[], boolean). Inputs: array conaining zero uploads. Expectation:
     * IllegalArgumentException should be thrown.
     *
     * @throws Exception to JUnit
     */
    public void testGetScreeningTasks2ZeroUploads() throws Exception {
        try {
            this.manager.getScreeningTasks(new long[] {0L, 1L, 0L, 5L}, true);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for getScreeningTasks(long[], boolean). Inputs: array conaining negative uploads. Expectation:
     * IllegalArgumentException should be thrown.
     *
     * @throws Exception to JUnit
     */
    public void testGetScreeningTasks2NegativeUploads()
        throws Exception {
        try {
            this.manager.getScreeningTasks(new long[] {-1L, 1L, -2L, 5L}, false);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for getScreeningTasks(long[]). Inputs: array conaining duplicate uploads. Expectation:
     * IllegalArgumentException should be thrown.
     *
     * @throws Exception to JUnit
     */
    public void testGetScreeningTasks2DuplicateUploads()
        throws Exception {
        try {
            this.manager.getScreeningTasks(new long[] {1L, 2L, 2L}, false);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for getScreeningTasks(long[], boolean). Inputs: array conaining unknown uploads and it is set not
     * allowed. Expectation: ScreeningTaskDoesNotExistException should be thrown.
     *
     * @throws Exception to JUnit
     */
    public void testGetScreeningTasks2UnknownUploads()
        throws Exception {
        this.manager.initiateScreening(2L, "topcoder2");

        try {
            this.manager.getScreeningTasks(new long[] {2L, 3L}, false);
            fail("ScreeningTaskDoesNotExistException should be thrown.");
        } catch (ScreeningTaskDoesNotExistException e) {
            // expected
        }
    }

    /**
     * Failure test for getScreeningTasks(long[], boolean). Inputs: array conaining unknown uploads but it is set
     * allowed. Expectation: should be ok.
     *
     * @throws Exception to JUnit
     */
    public void testGetScreeningTasks2UnknownUploadsAllowed()
        throws Exception {
        this.manager.initiateScreening(2L, "topcoder2");
        this.manager.getScreeningTasks(new long[] {2L, 3L}, true);
    }
}
