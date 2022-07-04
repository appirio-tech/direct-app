/*
 * Copyright (C) 2006-2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable.persistence.sql.stresstests;

import java.util.Date;

import com.topcoder.management.deliverable.Submission;
import com.topcoder.management.deliverable.SubmissionStatus;
import com.topcoder.management.deliverable.SubmissionType;
import com.topcoder.management.deliverable.Upload;
import com.topcoder.management.deliverable.UploadStatus;
import com.topcoder.management.deliverable.UploadType;
import com.topcoder.management.deliverable.persistence.sql.SqlUploadPersistence;

/**
 * Stress tests for the class: SqlUploadPersistence.
 * @author kinfkong, FireIce
 * @version 1.1
 */
public class SqlUploadPersistenceStressTest extends DbStressTest {

    /**
     * An instance of SqlUploadPersistence stress tests.
     */
    private SqlUploadPersistence persistence = null;

    /**
     * Sets up the environment.
     * @throws Exception
     *             to JUnit
     */
    public void setUp() throws Exception {
        super.setUp();
        // create the instance
        persistence = new SqlUploadPersistence(getConnectionFactory());
    }

    /**
     * Stress tests for the method: addUploadType(UploadType).
     * @throws Exception
     *             to JUnit
     */
    public void testAddUploadType() throws Exception {
        // gets the current time
        Date current = new Date();

        // stress test
        Date start = new Date();
        for (int i = 0; i < STRESS_TEST_NUM; i++) {
            // creates the UploadType
            UploadType type = new UploadType(i + 1);

            // set the values
            type.setCreationTimestamp(current);

            type.setCreationUser("STRESS_REVIEWER");

            type.setDescription("FOR TESTS");

            type.setModificationTimestamp(current);

            type.setModificationUser("STRESS_REVIEWER");

            type.setName("SOME_ONE");

            // add it
            persistence.addUploadType(type);

            // check record exists
            String sql = "SELECT * FROM upload_type_lu WHERE upload_type_id=?";
            assertTrue("The record does not exist.", existsRecord(sql,
                new Object[] {new Long(i + 1)}));
        }
        Date finish = new Date();

        // output the information
        outputStressInfo(start, finish, "addUploadType");

    }

    /**
     * Stress tests for method removeUploadType(UploadType).
     * @throws Exception
     *             to JUnit
     */
    public void testRemoveUploadType() throws Exception {
        // gets the current time
        Date current = new Date();

        // stress test
        long time = 0;
        for (int i = 0; i < STRESS_TEST_NUM; i++) {
            // creates the UploadType
            UploadType type = new UploadType(i + 1);

            // set the values
            type.setCreationTimestamp(current);

            type.setCreationUser("STRESS_REVIEWER");

            type.setDescription("FOR TESTS");

            type.setModificationTimestamp(current);

            type.setModificationUser("STRESS_REVIEWER");

            type.setName("SOME_ONE");

            // add it
            persistence.addUploadType(type);

            Date start = new Date();
            // remove it
            persistence.removeUploadType(type);
            time += new Date().getTime() - start.getTime();

            // check record exists
            String sql = "SELECT * FROM upload_type_lu WHERE upload_type_id=?";
            assertFalse("The record should be removed.", existsRecord(sql, new Object[] {new Long(
                i + 1)}));
        }
        Date start = new Date();
        Date finish = new Date(start.getTime() + time);

        // output the information
        outputStressInfo(start, finish, "removeUploadType");
    }

    /**
     * Stress tests for method updateUploadType(UploadType).
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateUploadType() throws Exception {
        // gets the current time
        Date current = new Date();

        // creates the UploadType
        UploadType type = new UploadType(1);

        // set the values
        type.setCreationTimestamp(current);

        type.setCreationUser("STRESS_REVIEWER");

        type.setDescription("FOR TESTS");

        type.setModificationTimestamp(current);

        type.setModificationUser("STRESS_REVIEWER");

        type.setName("SOME_ONE");

        // add it
        persistence.addUploadType(type);

        // stress test
        Date start = new Date();
        for (int i = 0; i < STRESS_TEST_NUM; i++) {

            type.setModificationUser("Name" + i);

            // update it
            persistence.updateUploadType(type);

            // check record exists
            String sql = "SELECT * FROM upload_type_lu WHERE upload_type_id=1 AND modify_user=?";
            assertTrue("The record should be updated.",
                existsRecord(sql, new Object[] {"Name" + i}));
        }
        Date finish = new Date();

        // output the information
        outputStressInfo(start, finish, "updateUploadType");
    }

    /**
     * Stress tests for the method loadUploadType(long).
     * @throws Exception
     *             to JUnit
     */
    public void testLoadUploadType() throws Exception {
        // gets the current time
        Date current = new Date();

        // creates the UploadType
        UploadType type = new UploadType(1);

        // set the values
        type.setCreationTimestamp(current);

        type.setCreationUser("STRESS_REVIEWER");

        type.setDescription("FOR TESTS");

        type.setModificationTimestamp(current);

        type.setModificationUser("STRESS_REVIEWER");

        type.setName("SOME_ONE");

        // add it
        persistence.addUploadType(type);

        // stress test
        Date start = new Date();
        for (int i = 0; i < STRESS_TEST_NUM; i++) {

            // update it
            UploadType newType = persistence.loadUploadType(1);

            assertEquals("The record is not correct.", "STRESS_REVIEWER", newType
                .getModificationUser());
        }
        Date finish = new Date();

        // output the information
        outputStressInfo(start, finish, "loadUploadType");
    }

    /**
     * Stress tests for the method loadUploadType(long).
     * @throws Exception
     *             to JUnit
     */
    public void testLoadUploadType_NotFound() throws Exception {
        // gets the current time
        Date current = new Date();

        // creates the UploadType
        UploadType status = new UploadType(1);

        // set the values
        status.setCreationTimestamp(current);

        status.setCreationUser("STRESS_REVIEWER");

        status.setDescription("FOR TESTS");

        status.setModificationTimestamp(current);

        status.setModificationUser("STRESS_REVIEWER");

        status.setName("SOME_ONE");

        // add it
        persistence.addUploadType(status);

        // stress test
        Date start = new Date();
        for (int i = 0; i < STRESS_TEST_NUM; i++) {

            // update it
            UploadType newType = persistence.loadUploadType(2);

            assertNull("The record is not correct.", newType);
        }
        Date finish = new Date();

        // output the information
        outputStressInfo(start, finish, "loadUploadType (returns null)");
    }

    /**
     * Stress tests for the method: getAllUploadTypeIds().
     * @throws Exception
     *             to JUnit
     */
    public void testGetAllUploadTypeIds() throws Exception {
        // gets the current time
        Date current = new Date();

        for (int i = 0; i < 10; i++) {
            // creates the UploadType
            UploadType status = new UploadType(i + 1);

            // set the values
            status.setCreationTimestamp(current);

            status.setCreationUser("STRESS_REVIEWER");

            status.setDescription("FOR TESTS");

            status.setModificationTimestamp(current);

            status.setModificationUser("STRESS_REVIEWER");

            status.setName("SOME_ONE");

            // add it
            persistence.addUploadType(status);
        }

        // stress test

        long[] ids = null;

        Date start = new Date();
        for (int i = 0; i < STRESS_TEST_NUM; i++) {
            // get it
            ids = persistence.getAllUploadTypeIds();
        }
        Date finish = new Date();

        // check correctness

        for (int i = 0; i < 10; i++) {
            assertEquals("The method getAllUploadstatusIds does not work correctly.", i + 1, ids[i]);
        }

        // output the information
        outputStressInfo(start, finish, "getAllUploadTypes");
    }

    /**
     * Stress tests for the method: addUploadStatus(UploadStatus).
     * @throws Exception
     *             to JUnit
     */
    public void testAddUploadStatus() throws Exception {
        // gets the current time
        Date current = new Date();

        // stress test
        Date start = new Date();
        for (int i = 0; i < STRESS_TEST_NUM; i++) {
            // creates the UploadStatus
            UploadStatus status = new UploadStatus(i + 1);

            // set the values
            status.setCreationTimestamp(current);

            status.setCreationUser("STRESS_REVIEWER");

            status.setDescription("FOR TESTS");

            status.setModificationTimestamp(current);

            status.setModificationUser("STRESS_REVIEWER");

            status.setName("SOME_ONE");

            // add it
            persistence.addUploadStatus(status);

            // check record exists
            String sql = "SELECT * FROM upload_status_lu WHERE upload_status_id=?";
            assertTrue("The record does not exist.", existsRecord(sql,
                new Object[] {new Long(i + 1)}));
        }
        Date finish = new Date();

        // output the information
        outputStressInfo(start, finish, "addUploadStatus");
    }

    /**
     * Stress tests for method removeUploadStatus(UploadStatus).
     * @throws Exception
     *             to JUnit
     */
    public void testRemoveUploadStatus() throws Exception {
        // gets the current time
        Date current = new Date();

        // stress test
        long time = 0;
        for (int i = 0; i < STRESS_TEST_NUM; i++) {
            // creates the UploadStatus
            UploadStatus status = new UploadStatus(i + 1);

            // set the values
            status.setCreationTimestamp(current);

            status.setCreationUser("STRESS_REVIEWER");

            status.setDescription("FOR TESTS");

            status.setModificationTimestamp(current);

            status.setModificationUser("STRESS_REVIEWER");

            status.setName("SOME_ONE");

            // add it
            persistence.addUploadStatus(status);

            Date start = new Date();
            // remove it
            persistence.removeUploadStatus(status);
            time += new Date().getTime() - start.getTime();

            // check record exists
            String sql = "SELECT * FROM upload_status_lu WHERE upload_status_id=?";
            assertFalse("The record should be removed.", existsRecord(sql, new Object[] {new Long(
                i + 1)}));
        }
        Date start = new Date();
        Date finish = new Date(start.getTime() + time);

        // output the information
        outputStressInfo(start, finish, "removeUploadStatus");
    }

    /**
     * Stress tests for method updateUploadStatus(UploadStatus).
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateUploadStatus() throws Exception {
        // gets the current time
        Date current = new Date();

        // creates the UploadStatus
        UploadStatus status = new UploadStatus(1);

        // set the values
        status.setCreationTimestamp(current);

        status.setCreationUser("STRESS_REVIEWER");

        status.setDescription("FOR TESTS");

        status.setModificationTimestamp(current);

        status.setModificationUser("STRESS_REVIEWER");

        status.setName("SOME_ONE");

        // add it
        persistence.addUploadStatus(status);

        // stress test
        Date start = new Date();
        for (int i = 0; i < STRESS_TEST_NUM; i++) {

            status.setModificationUser("Name" + i);

            // update it
            persistence.updateUploadStatus(status);

            // check record exists
            String sql = "SELECT * FROM upload_status_lu WHERE upload_status_id=1 AND modify_user=?";
            assertTrue("The record should be updated.",
                existsRecord(sql, new Object[] {"Name" + i}));
        }
        Date finish = new Date();

        // output the information
        outputStressInfo(start, finish, "updateUploadStatus");
    }

    /**
     * Stress tests for the method loadUploadStatus(long).
     * @throws Exception
     *             to JUnit
     */
    public void testLoadUploadStatus() throws Exception {
        // gets the current time
        Date current = new Date();

        // creates the UploadStatus
        UploadStatus status = new UploadStatus(1);

        // set the values
        status.setCreationTimestamp(current);

        status.setCreationUser("STRESS_REVIEWER");

        status.setDescription("FOR TESTS");

        status.setModificationTimestamp(current);

        status.setModificationUser("STRESS_REVIEWER");

        status.setName("SOME_ONE");

        // add it
        persistence.addUploadStatus(status);

        // stress test
        Date start = new Date();
        for (int i = 0; i < STRESS_TEST_NUM; i++) {

            // update it
            UploadStatus newStatus = persistence.loadUploadStatus(1);

            assertEquals("The record is not correct.", "STRESS_REVIEWER", newStatus
                .getModificationUser());
        }
        Date finish = new Date();

        // output the information
        outputStressInfo(start, finish, "loadUploadStatus");
    }

    /**
     * Stress tests for the method loadUploadStatus(long).
     * @throws Exception
     *             to JUnit
     */
    public void testLoadUploadStatus_NotFound() throws Exception {
        // gets the current time
        Date current = new Date();

        // creates the UploadStatus
        UploadStatus status = new UploadStatus(1);

        // set the values
        status.setCreationTimestamp(current);

        status.setCreationUser("STRESS_REVIEWER");

        status.setDescription("FOR TESTS");

        status.setModificationTimestamp(current);

        status.setModificationUser("STRESS_REVIEWER");

        status.setName("SOME_ONE");

        // add it
        persistence.addUploadStatus(status);

        // stress test
        Date start = new Date();
        for (int i = 0; i < STRESS_TEST_NUM; i++) {

            // update it
            UploadStatus newStatus = persistence.loadUploadStatus(2);

            assertNull("The record is not correct.", newStatus);
        }
        Date finish = new Date();

        // output the information
        outputStressInfo(start, finish, "loadUploadStatus (returns null)");
    }

    /**
     * Stress tests for the method: getAllUploadStatusIds().
     * @throws Exception
     *             to JUnit
     */
    public void testGetAllUploadStatusIds() throws Exception {
        // gets the current time
        Date current = new Date();

        for (int i = 0; i < 10; i++) {
            // creates the UploadStatus
            UploadStatus status = new UploadStatus(i + 1);

            // set the values
            status.setCreationTimestamp(current);

            status.setCreationUser("STRESS_REVIEWER");

            status.setDescription("FOR TESTS");

            status.setModificationTimestamp(current);

            status.setModificationUser("STRESS_REVIEWER");

            status.setName("SOME_ONE");

            // add it
            persistence.addUploadStatus(status);
        }

        // stress test

        Date start = new Date();
        long[] ids = null;

        for (int i = 0; i < STRESS_TEST_NUM; i++) {
            // get it
            ids = persistence.getAllUploadStatusIds();
        }
        Date finish = new Date();

        // check correctness

        for (int i = 0; i < 10; i++) {
            assertEquals("The method getAllUploadstatusIds does not work correctly.", i + 1, ids[i]);
        }

        // output the information
        outputStressInfo(start, finish, "getAllUploadStatuseIds");
    }

    /**
     * Stress tests for the method: addSubmissionStatus(SubmissionStatus).
     * @throws Exception
     *             to JUnit
     */
    public void testAddSubmissionStatus() throws Exception {
        // gets the current time
        Date current = new Date();

        // stress test
        Date start = new Date();
        for (int i = 0; i < STRESS_TEST_NUM; i++) {
            // creates the SubmissionStatus
            SubmissionStatus status = new SubmissionStatus(i + 1);

            // set the values
            status.setCreationTimestamp(current);

            status.setCreationUser("STRESS_REVIEWER");

            status.setDescription("FOR TESTS");

            status.setModificationTimestamp(current);

            status.setModificationUser("STRESS_REVIEWER");

            status.setName("SOME_ONE");

            // add it
            persistence.addSubmissionStatus(status);

            // check record exists
            String sql = "SELECT * FROM submission_status_lu WHERE submission_status_id=?";
            assertTrue("The record does not exist.", existsRecord(sql,
                new Object[] {new Long(i + 1)}));
        }
        Date finish = new Date();

        // output the information
        outputStressInfo(start, finish, "addSubmissionStatus");

    }

    /**
     * Stress tests for method removeSubmissionStatus(SubmissionStatus).
     * @throws Exception
     *             to JUnit
     */
    public void testRemoveSubmissionStatus() throws Exception {
        // gets the current time
        Date current = new Date();

        // stress test
        long time = 0;
        for (int i = 0; i < STRESS_TEST_NUM; i++) {
            // creates the SubmissionStatus
            SubmissionStatus status = new SubmissionStatus(i + 1);

            // set the values
            status.setCreationTimestamp(current);

            status.setCreationUser("STRESS_REVIEWER");

            status.setDescription("FOR TESTS");

            status.setModificationTimestamp(current);

            status.setModificationUser("STRESS_REVIEWER");

            status.setName("SOME_ONE");

            // add it
            persistence.addSubmissionStatus(status);

            Date start = new Date();
            // remove it
            persistence.removeSubmissionStatus(status);
            time += new Date().getTime() - start.getTime();

            // check record exists
            String sql = "SELECT * FROM submission_status_lu WHERE submission_status_id=?";
            assertFalse("The record should be removed.", existsRecord(sql, new Object[] {new Long(
                i + 1)}));
        }
        Date start = new Date();
        Date finish = new Date(start.getTime() + time);

        // output the infomation
        outputStressInfo(start, finish, "removeSubmissionStatus");
    }

    /**
     * Stress tests for method updateSubmissionStatus(SubmissionStatus).
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateSubmissionStatus() throws Exception {
        // gets the current time
        Date current = new Date();

        // creates the SubmissionStatus
        SubmissionStatus status = new SubmissionStatus(1);

        // set the values
        status.setCreationTimestamp(current);

        status.setCreationUser("STRESS_REVIEWER");

        status.setDescription("FOR TESTS");

        status.setModificationTimestamp(current);

        status.setModificationUser("STRESS_REVIEWER");

        status.setName("SOME_ONE");

        // add it
        persistence.addSubmissionStatus(status);

        // stress test
        Date start = new Date();
        for (int i = 0; i < STRESS_TEST_NUM; i++) {

            status.setModificationUser("Name" + i);

            // update it
            persistence.updateSubmissionStatus(status);

            // check record exists
            String sql = "SELECT * FROM submission_status_lu WHERE submission_status_id=1 AND modify_user=?";
            assertTrue("The record should be updated.",
                existsRecord(sql, new Object[] {"Name" + i}));
        }
        Date finish = new Date();

        // output the information
        outputStressInfo(start, finish, "updateSubmissionStatus");
    }

    /**
     * Stress tests for the method loadSubmissionStatus(long).
     * @throws Exception
     *             to JUnit
     */
    public void testLoadSubmissionStatus() throws Exception {
        // gets the current time
        Date current = new Date();

        // creates the SubmissionStatus
        SubmissionStatus status = new SubmissionStatus(1);

        // set the values
        status.setCreationTimestamp(current);

        status.setCreationUser("STRESS_REVIEWER");

        status.setDescription("FOR TESTS");

        status.setModificationTimestamp(current);

        status.setModificationUser("STRESS_REVIEWER");

        status.setName("SOME_ONE");

        // add it
        persistence.addSubmissionStatus(status);

        // stress test
        Date start = new Date();
        for (int i = 0; i < STRESS_TEST_NUM; i++) {

            // update it
            SubmissionStatus newType = persistence.loadSubmissionStatus(1);

            assertEquals("The record is not correct.", "STRESS_REVIEWER", newType
                .getModificationUser());
        }
        Date finish = new Date();

        // output the information
        outputStressInfo(start, finish, "loadSubmissionStatus");
    }

    /**
     * Stress tests for the method loadSubmissionStatus(long).
     * @throws Exception
     *             to JUnit
     */
    public void testLoadSubmissionStatus_NotFound() throws Exception {
        // gets the current time
        Date current = new Date();

        // creates the SubmissionStatus
        SubmissionStatus status = new SubmissionStatus(1);

        // set the values
        status.setCreationTimestamp(current);

        status.setCreationUser("STRESS_REVIEWER");

        status.setDescription("FOR TESTS");

        status.setModificationTimestamp(current);

        status.setModificationUser("STRESS_REVIEWER");

        status.setName("SOME_ONE");

        // add it
        persistence.addSubmissionStatus(status);

        // stress test
        Date start = new Date();
        for (int i = 0; i < STRESS_TEST_NUM; i++) {

            // update it
            SubmissionStatus newType = persistence.loadSubmissionStatus(2);

            assertNull("The record is not correct.", newType);
        }
        Date finish = new Date();

        // output the information
        outputStressInfo(start, finish, "loadSubmissionStatus (returns null)");
    }

    /**
     * Stress tests for the method: getAllSubmissionStatusIds().
     * @throws Exception
     *             to JUnit
     */
    public void testGetAllSubmissionStatusIds() throws Exception {
        // gets the current time
        Date current = new Date();

        for (int i = 0; i < 10; i++) {
            // creates the SubmissionStatus
            SubmissionStatus status = new SubmissionStatus(i + 1);

            // set the values
            status.setCreationTimestamp(current);

            status.setCreationUser("STRESS_REVIEWER");

            status.setDescription("FOR TESTS");

            status.setModificationTimestamp(current);

            status.setModificationUser("STRESS_REVIEWER");

            status.setName("SOME_ONE");

            // add it
            persistence.addSubmissionStatus(status);
        }

        // stress test
        long[] ids = null;

        Date start = new Date();
        for (int i = 0; i < STRESS_TEST_NUM; i++) {
            // get it
            ids = persistence.getAllSubmissionStatusIds();
        }
        Date finish = new Date();

        // check correctness

        for (int i = 0; i < 10; i++) {
            assertEquals("The method getAllSubmissionStatusIds does not work correctly.", i + 1,
                ids[i]);
        }

        // output the information
        outputStressInfo(start, finish, "getAllSubmissionStatuseIds");
    }

    /**
     * Stress tests for the method: addSubmissionType(SubmissionType).
     * @throws Exception
     *             to JUnit
     * @since 1.1
     */
    public void testAddSubmissionType() throws Exception {
        // gets the current time
        Date current = new Date();

        // stress test
        Date start = new Date();
        for (int i = 0; i < STRESS_TEST_NUM; i++) {
            // creates the SubmissionType
            SubmissionType type = new SubmissionType(i + 1);

            // set the values
            type.setCreationTimestamp(current);

            type.setCreationUser("STRESS_REVIEWER");

            type.setDescription("FOR TESTS");

            type.setModificationTimestamp(current);

            type.setModificationUser("STRESS_REVIEWER");

            type.setName("SOME_ONE");

            // add it
            persistence.addSubmissionType(type);

            // check record exists
            String sql = "SELECT * FROM submission_type_lu WHERE submission_type_id=?";
            assertTrue("The record does not exist.", existsRecord(sql,
                new Object[] {new Long(i + 1)}));
        }
        Date finish = new Date();

        // output the information
        outputStressInfo(start, finish, "addSubmissionType");

    }

    /**
     * Stress tests for method removeSubmissionType(SubmissionType).
     * @throws Exception
     *             to JUnit
     * @since 1.1
     */
    public void testRemoveSubmissionType() throws Exception {
        // gets the current time
        Date current = new Date();

        // stress test
        long time = 0;
        for (int i = 0; i < STRESS_TEST_NUM; i++) {
            // creates the SubmissionType
            SubmissionType type = new SubmissionType(i + 1);

            // set the values
            type.setCreationTimestamp(current);

            type.setCreationUser("STRESS_REVIEWER");

            type.setDescription("FOR TESTS");

            type.setModificationTimestamp(current);

            type.setModificationUser("STRESS_REVIEWER");

            type.setName("SOME_ONE");

            // add it
            persistence.addSubmissionType(type);

            Date start = new Date();
            // remove it
            persistence.removeSubmissionType(type);
            time += new Date().getTime() - start.getTime();

            // check record exists
            String sql = "SELECT * FROM submission_type_lu WHERE submission_type_id=?";
            assertFalse("The record should be removed.", existsRecord(sql, new Object[] {new Long(
                i + 1)}));
        }
        Date start = new Date();
        Date finish = new Date(start.getTime() + time);

        // output the information
        outputStressInfo(start, finish, "removeSubmissionType");
    }

    /**
     * Stress tests for method updateSubmissionType(SubmissionType).
     * @throws Exception
     *             to JUnit
     * @since 1.1
     */
    public void testUpdateSubmissionType() throws Exception {
        // gets the current time
        Date current = new Date();

        // creates the SubmissionType
        SubmissionType type = new SubmissionType(1);

        // set the values
        type.setCreationTimestamp(current);

        type.setCreationUser("STRESS_REVIEWER");

        type.setDescription("FOR TESTS");

        type.setModificationTimestamp(current);

        type.setModificationUser("STRESS_REVIEWER");

        type.setName("SOME_ONE");

        // add it
        persistence.addSubmissionType(type);

        // stress test
        Date start = new Date();
        for (int i = 0; i < STRESS_TEST_NUM; i++) {

            type.setModificationUser("Name" + i);

            // update it
            persistence.updateSubmissionType(type);

            // check record exists
            String sql = "SELECT * FROM submission_type_lu WHERE submission_type_id=1 AND modify_user=?";
            assertTrue("The record should be updated.",
                existsRecord(sql, new Object[] {"Name" + i}));
        }
        Date finish = new Date();

        // output the information
        outputStressInfo(start, finish, "updateSubmissionType");
    }

    /**
     * Stress tests for the method loadSubmissionType(long).
     * @throws Exception
     *             to JUnit
     * @since 1.1
     */
    public void testLoadSubmissionType() throws Exception {
        // gets the current time
        Date current = new Date();

        // creates the SubmissionType
        SubmissionType type = new SubmissionType(1);

        // set the values
        type.setCreationTimestamp(current);

        type.setCreationUser("STRESS_REVIEWER");

        type.setDescription("FOR TESTS");

        type.setModificationTimestamp(current);

        type.setModificationUser("STRESS_REVIEWER");

        type.setName("SOME_ONE");

        // add it
        persistence.addSubmissionType(type);

        // stress test
        Date start = new Date();
        for (int i = 0; i < STRESS_TEST_NUM; i++) {

            // update it
            SubmissionType newType = persistence.loadSubmissionType(1);

            assertEquals("The record is not correct.", "STRESS_REVIEWER", newType
                .getModificationUser());
        }
        Date finish = new Date();

        // output the information
        outputStressInfo(start, finish, "loadSubmissionType");
    }

    /**
     * Stress tests for the method loadSubmissionType(long).
     * @throws Exception
     *             to JUnit
     * @since 1.1
     */
    public void testLoadSubmissionType_NotFound() throws Exception {
        // gets the current time
        Date current = new Date();

        // creates the SubmissionType
        SubmissionType type = new SubmissionType(1);

        // set the values
        type.setCreationTimestamp(current);

        type.setCreationUser("STRESS_REVIEWER");

        type.setDescription("FOR TESTS");

        type.setModificationTimestamp(current);

        type.setModificationUser("STRESS_REVIEWER");

        type.setName("SOME_ONE");

        // add it
        persistence.addSubmissionType(type);

        // stress test
        Date start = new Date();
        for (int i = 0; i < STRESS_TEST_NUM; i++) {

            // update it
            SubmissionType newType = persistence.loadSubmissionType(2);

            assertNull("The record is not correct.", newType);
        }
        Date finish = new Date();

        // output the information
        outputStressInfo(start, finish, "loadSubmissionType (returns null)");
    }

    /**
     * Stress tests for the method: getAllSubmissionTypeIds().
     * @throws Exception
     *             to JUnit
     * @since 1.1
     */
    public void testGetAllSubmissionTypeIds() throws Exception {
        // gets the current time
        Date current = new Date();

        for (int i = 0; i < 10; i++) {
            // creates the SubmissionType
            SubmissionType type = new SubmissionType(i + 1);

            // set the values
            type.setCreationTimestamp(current);

            type.setCreationUser("STRESS_REVIEWER");

            type.setDescription("FOR TESTS");

            type.setModificationTimestamp(current);

            type.setModificationUser("STRESS_REVIEWER");

            type.setName("SOME_ONE");

            // add it
            persistence.addSubmissionType(type);
        }

        // stress test
        long[] ids = null;

        Date start = new Date();
        for (int i = 0; i < STRESS_TEST_NUM; i++) {
            // get it
            ids = persistence.getAllSubmissionTypeIds();
        }
        Date finish = new Date();

        // check correctness

        for (int i = 0; i < 10; i++) {
            assertEquals("The method getAllSubmissionTypeIds does not work correctly.", i + 1,
                ids[i]);
        }

        // output the information
        outputStressInfo(start, finish, "getAllSubmissionTypeIds");
    }


    /**
     * Stress tests for the method: addUpload(Upload).
     * @throws Exception
     *             to JUnit
     */
    public void testAddUpload() throws Exception {
        // gets the current time
        Date current = new Date();

        setLuTableRecords();

        // stress test
        Date start = new Date();
        for (int i = 0; i < STRESS_TEST_NUM; i++) {
            // creates the Upload
            Upload upload = new Upload(i + 1);

            // set the values
            upload.setCreationTimestamp(current);

            upload.setCreationUser("STRESS_REVIEWER");

            upload.setModificationTimestamp(current);

            upload.setModificationUser("STRESS_REVIEWER");

            upload.setUploadType(getUploadType(1));

            upload.setProject(1);

            upload.setOwner(1);

            upload.setUploadStatus(getUploadStatus(1));

            upload.setParameter("some_parameter");

            // add it
            persistence.addUpload(upload);

            // check record exists
            String sql = "SELECT * FROM upload WHERE upload_id=?";
            assertTrue("The record does not exist.", existsRecord(sql,
                new Object[] {new Long(i + 1)}));
        }
        Date finish = new Date();

        // output the information
        outputStressInfo(start, finish, "addUpload");
    }

    /**
     * Returns a valid upload type of a specific id.
     * @param id
     *            the id
     * @return the upload type
     */
    private UploadType getUploadType(long id) {
        UploadType type = new UploadType(id);
        Date current = new Date();
        type.setCreationTimestamp(current);
        type.setModificationTimestamp(current);
        type.setCreationUser("USER");
        type.setModificationUser("USER");
        type.setDescription("SOMETHING");
        type.setName("name");
        return type;
    }

    /**
     * Returns a valid upload status of a specific id.
     * @param id
     *            the id
     * @return the upload status
     */
    private UploadStatus getUploadStatus(long id) {
        UploadStatus status = new UploadStatus(id);
        Date current = new Date();
        status.setCreationTimestamp(current);
        status.setModificationTimestamp(current);
        status.setCreationUser("USER");
        status.setModificationUser("USER");
        status.setDescription("SOMETHING");
        status.setName("name");
        return status;
    }

    /**
     * Returns a valid Submission status of a specific id.
     * @param id
     *            the id
     * @return the Submission status
     */
    private SubmissionStatus getSubmissionStatus(long id) {
        SubmissionStatus status = new SubmissionStatus(id);
        Date current = new Date();
        status.setCreationTimestamp(current);
        status.setModificationTimestamp(current);
        status.setCreationUser("USER");
        status.setModificationUser("USER");
        status.setDescription("SOMETHING");
        status.setName("name");
        return status;
    }

    /**
     * Returns a valid Submission type of a specific id.
     * @param id
     *            the id
     * @return the Submission type
     */
    private SubmissionType getSubmissionType(long id) {
        SubmissionType type = new SubmissionType(id);
        Date current = new Date();
        type.setCreationTimestamp(current);
        type.setModificationTimestamp(current);
        type.setCreationUser("USER");
        type.setModificationUser("USER");
        type.setDescription("SOMETHING");
        type.setName("name");
        return type;
    }

    /**
     * Stress tests for method removeUpload(Upload).
     * @throws Exception
     *             to JUnit
     */
    public void testRemoveUpload() throws Exception {
        // gets the current time
        Date current = new Date();

        // insert records
        setLuTableRecords();

        // stress test
        long time = 0;
        for (int i = 0; i < STRESS_TEST_NUM; i++) {
            // creates the Upload
            Upload upload = new Upload(i + 1);

            // set the values
            upload.setCreationTimestamp(current);

            upload.setCreationUser("STRESS_REVIEWER");

            upload.setModificationTimestamp(current);

            upload.setModificationUser("STRESS_REVIEWER");

            upload.setUploadType(getUploadType(1));

            upload.setProject(1);

            upload.setOwner(1);

            upload.setUploadStatus(getUploadStatus(1));

            upload.setParameter("some_parameter");

            // add it
            persistence.addUpload(upload);

            Date start = new Date();
            // remove it
            persistence.removeUpload(upload);
            time += new Date().getTime() - start.getTime();

            // check record exists
            String sql = "SELECT * FROM upload WHERE upload_id=?";
            assertFalse("The record should be removed.", existsRecord(sql, new Object[] {new Long(
                i + 1)}));
        }
        Date start = new Date();
        Date finish = new Date(start.getTime() + time);

        // output the information
        outputStressInfo(start, finish, "removeUpload");
    }

    /**
     * Stress tests for the method: updateUpload(Upload).
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateUpload() throws Exception {
        setLuTableRecords();
        String sql = "INSERT INTO upload ("
            + " upload_id, project_id, resource_id, upload_type_id, upload_status_id, "
            + " parameter, create_user, create_date, modify_user, modify_date)"
            + " VALUES(1, 1, 1, 1, 1, 'parameter', 'REVIEWER', CURRENT, 'REVIEWER', CURRENT)";
        doSQLUpdate(sql, new Object[] {});

        Date current = new Date();
        // stress test
        Date start = new Date();
        for (int i = 0; i < STRESS_TEST_NUM; i++) {
            // creates the Upload
            Upload upload = new Upload(1);

            // set the values
            upload.setCreationTimestamp(current);

            upload.setCreationUser("STRESS_REVIEWER");

            upload.setModificationTimestamp(current);

            upload.setModificationUser("STRESS_REVIEWER");

            upload.setUploadType(getUploadType(1));

            upload.setProject(i + 1);

            upload.setOwner(1);

            upload.setUploadStatus(getUploadStatus(1));

            upload.setParameter("some_parameter");

            // add it
            persistence.updateUpload(upload);

            // check record exists
            sql = "SELECT * FROM upload WHERE upload_id=1 AND project_id=?";
            assertTrue("The record should be removed.", existsRecord(sql, new Object[] {new Long(
                i + 1)}));
        }
        Date finish = new Date();

        // output the information
        outputStressInfo(start, finish, "updateUpload");
    }

    /**
     * Stress tests for the method: loadUpload(long).
     * @throws Exception
     *             to JUnit
     */
    public void testLoadUpload() throws Exception {
        setLuTableRecords();
        String sql = "INSERT INTO upload ("
            + " upload_id, project_id, resource_id, upload_type_id, upload_status_id, "
            + " parameter, create_user, create_date, modify_user, modify_date)"
            + " VALUES(1, 1, 1, 1, 1, 'parameter', 'REVIEWER', CURRENT, 'REVIEWER', CURRENT)";
        doSQLUpdate(sql, new Object[] {});

        Upload res = null;

        Date start = new Date();
        for (int i = 0; i < STRESS_TEST_NUM; i++) {
            // load it
            res = persistence.loadUpload(1);
        }
        Date finish = new Date();

        // check accuracy
        assertEquals("The loadUpload does not work correctly.", 1, res.getProject());
        assertEquals("The loadUpload does not work correctly.", "parameter", res.getParameter());

        // output the information
        outputStressInfo(start, finish, "loadUpload");
    }

    /**
     * Stress tests for the method: addSubmission(Submission).
     * @throws Exception
     *             to JUnit
     */
    public void testAddSubmission() throws Exception {
        // gets the current time
        Date current = new Date();

        setLuTableRecords();

        // add an upload
        Upload upload = new Upload(1);

        // set the values
        upload.setCreationTimestamp(current);

        upload.setCreationUser("STRESS_REVIEWER");

        upload.setModificationTimestamp(current);

        upload.setModificationUser("STRESS_REVIEWER");

        upload.setUploadType(getUploadType(1));

        upload.setProject(1);

        upload.setOwner(1);

        upload.setUploadStatus(getUploadStatus(1));

        upload.setParameter("some_parameter");

        // add it
        persistence.addUpload(upload);

        // stress test
        Date start = new Date();
        for (int i = 0; i < STRESS_TEST_NUM; i++) {

            // creates the Submission
            Submission submission = new Submission(i + 1);

            // set the values
            submission.setCreationTimestamp(current);

            submission.setCreationUser("STRESS_REVIEWER");

            submission.setModificationTimestamp(current);

            submission.setModificationUser("STRESS_REVIEWER");

            submission.setUpload(getUpload(1));

            submission.setSubmissionStatus(getSubmissionStatus(1));

            submission.setSubmissionType(getSubmissionType(1));

            // add it
            persistence.addSubmission(submission);

            // check record exists
            String sql = "SELECT * FROM submission WHERE submission_id=?";
            assertTrue("The record does not exist.", existsRecord(sql,
                new Object[] {new Long(i + 1)}));
        }
        Date finish = new Date();

        // output the information
        outputStressInfo(start, finish, "addSubmission");

    }

    /**
     * Gets the upload with a specific id.
     * @param id
     *            the id
     * @return the upload
     */
    private Upload getUpload(long id) {
        // add an upload
        Upload upload = new Upload(id);

        Date current = new Date();

        // set the values
        upload.setCreationTimestamp(current);

        upload.setCreationUser("STRESS_REVIEWER");

        upload.setModificationTimestamp(current);

        upload.setModificationUser("STRESS_REVIEWER");

        upload.setUploadType(getUploadType(1));

        upload.setProject(1);

        upload.setOwner(1);

        upload.setUploadStatus(getUploadStatus(1));

        upload.setParameter("some_parameter");

        return upload;
    }

    /**
     * Stress tests for the method: removeSubmission(Submission).
     * @throws Exception
     *             to JUnit
     */
    public void testRemoveSubmission() throws Exception {
        setLuTableRecords();
        String sql = "INSERT INTO upload ("
            + " upload_id, project_id, resource_id, upload_type_id, upload_status_id, "
            + " parameter, create_user, create_date, modify_user, modify_date)"
            + " VALUES(1, 1, 1, 1, 1, 'parameter', 'REVIEWER', CURRENT, 'REVIEWER', CURRENT)";
        doSQLUpdate(sql, new Object[] {});

        String sql1 = "INSERT INTO submission ("
            + " submission_id, upload_id, submission_status_id, submission_type_id, create_user, create_date, modify_user, modify_date)"
            + " VALUES (1, 1, 1, 1, 'REVIEWER', CURRENT, 'REVIEWER', CURRENT)";

        long time = 0;
        for (int i = 0; i < STRESS_TEST_NUM; i++) {
            // add it
            doSQLUpdate(sql1, new Object[] {});

            Date start = new Date();

            // remove it
            persistence.removeSubmission(new Submission(1));

            time += new Date().getTime() - start.getTime();

            // check exists
            sql = "SELECT * FROM submission WHERE submission_id=1";
            assertFalse("Should be removed.", existsRecord(sql, new Object[] {}));
        }
        Date start = new Date();
        Date finish = new Date(start.getTime() + time);

        // output the information
        outputStressInfo(start, finish, "removeSubmission");
    }

    /**
     * Stress tests updateSubmission(Submission).
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateSubmission() throws Exception {
        setLuTableRecords();
        String sql = "INSERT INTO upload ("
            + " upload_id, project_id, resource_id, upload_type_id, upload_status_id, "
            + " parameter, create_user, create_date, modify_user, modify_date)"
            + " VALUES(1, 1, 1, 1, 1, 'parameter', 'REVIEWER', CURRENT, 'REVIEWER', CURRENT)";
        doSQLUpdate(sql, new Object[] {});

        String sql1 = "INSERT INTO submission ("
            + " submission_id, upload_id, submission_status_id, submission_type_id, create_user, create_date, modify_user, modify_date)"
            + " VALUES (1, 1, 1, 1, 'REVIEWER', CURRENT, 'REVIEWER', CURRENT)";

        doSQLUpdate(sql1, new Object[] {});

        Date current = new Date();
        Date start = new Date();
        for (int i = 0; i < STRESS_TEST_NUM; i++) {
            // create a new submission
            Submission submission = new Submission(1);
            submission.setCreationTimestamp(current);
            submission.setCreationUser("REVIEWER");

            submission.setSubmissionStatus(getSubmissionStatus(2));
            submission.setSubmissionType(getSubmissionType(1));
            submission.setModificationUser("NEW_REVIEWER");
            submission.setModificationTimestamp(current);

            submission.setUpload(getUpload(1));

            persistence.updateSubmission(submission);

            // assert it
            assertTrue("The record should be updated.", existsRecord(
                "SELECT * FROM submission WHERE modify_user='NEW_REVIEWER'", new Object[] {}));
        }
        Date finish = new Date();

        // output the information
        outputStressInfo(start, finish, "updateSubmission");
    }

    /**
     * Stress tests for the method: loadSubmission(long).
     * @throws Exception
     *             to JUnit
     */
    public void testLoadSubmission() throws Exception {
        setLuTableRecords();
        String sql = "INSERT INTO upload ("
            + " upload_id, project_id, resource_id, upload_type_id, upload_status_id, "
            + " parameter, create_user, create_date, modify_user, modify_date)"
            + " VALUES(1, 1, 1, 1, 1, 'parameter', 'REVIEWER', CURRENT, 'REVIEWER', CURRENT)";
        doSQLUpdate(sql, new Object[] {});

        String sql1 = "INSERT INTO submission ("
            + " submission_id, upload_id, submission_status_id, submission_type_id, create_user, create_date, modify_user, modify_date)"
            + " VALUES (1, 1, 1, 1, 'REVIEWER', CURRENT, 'REVIEWER', CURRENT)";

        doSQLUpdate(sql1, new Object[] {});

        // stress tests
        Date start = new Date();
        for (int i = 0; i < STRESS_TEST_NUM; i++) {
            Submission submission = persistence.loadSubmission(1);

            // assert accuracy
            assertEquals("The load method does not work properly.", 1, submission.getId());
        }
        Date finish = new Date();

        // output the information
        outputStressInfo(start, finish, "loadSubmission");
    }

    /**
     * Stress tests for the method: loadSubmissions(long[]).
     * @throws Exception
     *             to JUnit
     */
    public void testLoadSubmissions() throws Exception {
        setLuTableRecords();
        String sql = "INSERT INTO upload ("
            + " upload_id, project_id, resource_id, upload_type_id, upload_status_id, "
            + " parameter, create_user, create_date, modify_user, modify_date)"
            + " VALUES(1, 1, 1, 1, 1, 'parameter', 'REVIEWER', CURRENT, 'REVIEWER', CURRENT)";
        doSQLUpdate(sql, new Object[] {});

        String sql1 = "INSERT INTO submission ("
            + " submission_id, upload_id, submission_status_id, submission_type_id, create_user, create_date, modify_user, modify_date)"
            + " VALUES (?, 1, 1, 1, 'REVIEWER', CURRENT, 'REVIEWER', CURRENT)";

        // insert 10 submissions
        for (int i = 0; i < 10; i++) {
            doSQLUpdate(sql1, new Object[] {new Long(i + 1)});
        }

        long[] ids = new long[6];

        // create the query ids
        for (int i = 0; i < 5; i++) {
            ids[i] = i + 1;
        }
        // this one does not found
        ids[5] = 100;

        // stress tests
        Date start = new Date();
        for (int i = 0; i < STRESS_TEST_NUM; i++) {
            Submission[] submissions = persistence.loadSubmissions(ids);

            // assert accuracy
            assertEquals("The load method does not work properly.", 5, submissions.length);
        }
        Date finish = new Date();

        // output the information
        outputStressInfo(start, finish, "loadSubmission");
    }

    /**
     * Stress tests for the method: loadUploads(long[]).
     * @throws Exception
     *             to JUnit
     */
    public void testLoadUploads() throws Exception {
        setLuTableRecords();
        String sql = "INSERT INTO upload ("
            + " upload_id, project_id, resource_id, upload_type_id, upload_status_id, "
            + " parameter, create_user, create_date, modify_user, modify_date)"
            + " VALUES(?, 1, 1, 1, 1, 'parameter', 'REVIEWER', CURRENT, 'REVIEWER', CURRENT)";

        // insert 10 records to the upload
        for (int i = 0; i < 10; i++) {
            doSQLUpdate(sql, new Object[] {new Long(i + 1)});
        }
        long[] ids = new long[6];

        // create the query ids
        for (int i = 0; i < 5; i++) {
            ids[i] = i + 1;
        }
        // this one does not found
        ids[5] = 100;

        Date start = new Date();
        for (int i = 0; i < STRESS_TEST_NUM; i++) {
            Upload[] uploads = persistence.loadUploads(ids);

            // assert accuracy
            assertEquals("The load method does not work properly.", 5, uploads.length);

        }
        Date finish = new Date();

        // output the information
        outputStressInfo(start, finish, "loadUpload");
    }

    /**
     * Stress tests for the method: loadUploadTypes(long[]).
     * @throws Exception
     *             to JUnit
     */
    public void testLoadUploadTypes() throws Exception {

        setLuTableRecords();

        long[] ids = new long[6];

        // create the query ids
        for (int i = 0; i < 5; i++) {
            ids[i] = i + 1;
        }
        // this one does not found
        ids[5] = 100;

        Date start = new Date();
        for (int i = 0; i < STRESS_TEST_NUM; i++) {
            UploadType[] uploadTypes = persistence.loadUploadTypes(ids);

            // assert accuracy
            assertEquals("The load method does not work properly.", 5, uploadTypes.length);

        }
        Date finish = new Date();

        // output the information
        outputStressInfo(start, finish, "loadUploadTypes");
    }

    /**
     * Stress tests for the method: loadUploadStatus(long[]).
     * @throws Exception
     *             to JUnit
     */
    public void testLoadUploadStatuses() throws Exception {

        setLuTableRecords();

        long[] ids = new long[6];

        // create the query ids
        for (int i = 0; i < 5; i++) {
            ids[i] = i + 1;
        }
        // this one does not found
        ids[5] = 100;

        Date start = new Date();
        for (int i = 0; i < STRESS_TEST_NUM; i++) {
            UploadStatus[] uploadStatus = persistence.loadUploadStatuses(ids);

            // assert accuracy
            assertEquals("The load method does not work properly.", 5, uploadStatus.length);

        }
        Date finish = new Date();

        // output the information
        outputStressInfo(start, finish, "loadUploadStatuses");
    }

    /**
     * Stress tests for the method: loadSubmissionStatus(long[]).
     * @throws Exception
     *             to JUnit
     */
    public void testLoadSubmissionStatuses() throws Exception {

        setLuTableRecords();

        long[] ids = new long[6];

        // create the query ids
        for (int i = 0; i < 5; i++) {
            ids[i] = i + 1;
        }
        // this one does not found
        ids[5] = 100;

        Date start = new Date();
        for (int i = 0; i < STRESS_TEST_NUM; i++) {
            SubmissionStatus[] submissionStatus = persistence.loadSubmissionStatuses(ids);

            // assert accuracy
            assertEquals("The load method does not work properly.", 5, submissionStatus.length);

        }
        Date finish = new Date();

        // output the information
        outputStressInfo(start, finish, "loadSubmissionStatuses");
    }
}
