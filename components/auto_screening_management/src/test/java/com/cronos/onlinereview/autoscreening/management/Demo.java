/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.management;

import java.util.Date;

import junit.framework.TestCase;

/**
 * <p>
 * Test cases to demonstrate the usage of this component.
 * </p>
 *
 * @author haozhangr
 * @version 1.0
 */
public class Demo extends TestCase {
    /**
     * <p>
     * setUp() routine. Load namespace.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        TestHelper.loadNamespaces();
        TestHelper.prepareData();
    }

    /**
     * tearDown() routine. Clear namespaces loaded.
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        TestHelper.clearData();
        TestHelper.releaseNamespaces();
    }

    /**
     * <p>
     * This method will demonstrate how to initiating screening task.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testInitiatingScreeningTask() throws Exception {
        // create the ScreeningManager instance with concrete namespace
        ScreeningManager manager = ScreeningManagerFactory
                .createScreeningManager("com.cronos.onlinereview.autoscreening.management");

        // or you can create the ScreeningManager instance with default namespace
        manager = ScreeningManagerFactory.createScreeningManager();

        // initiate screening task with upload
        manager.initiateScreening(56, "Operator");
    }

    /**
     * <p>
     * This method will demonstrate how to querying screening tasks without details.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testQueryingWithoutDetails() throws Exception {
        // create the ScreeningManager instance with default namespace
        ScreeningManager manager = ScreeningManagerFactory.createScreeningManager();
        // query screening tasks without details
        ScreeningTask[] screeningTasks = manager.getScreeningTasks(new long[] {51});
        // the screening task id should be 71
        long taskid = screeningTasks[0].getId();
        // the upload id should be 51
        long upload = screeningTasks[0].getUpload();
        // the screener id should be 81
        long screener = screeningTasks[0].getScreener();
        // the start timestamp should be "2006-07-11 13:27:39"
        Date startTimestamp = screeningTasks[0].getStartTimestamp();
        // the creation user should be "screening_task_create_user_1"
        String creationUser = screeningTasks[0].getCreationUser();
        // the creation timestamp should be "2006-07-11 13:45:59"
        Date creationTimestamp = screeningTasks[0].getCreationTimestamp();
        // the modification user should be "screening_task_modify_user_1"
        String modificationUser = screeningTasks[0].getModificationUser();
        // the modification timestamp should be "2006-07-11 14:04:19"
        Date modificationTimestamp = screeningTasks[0].getModificationTimestamp();
        // get the screening status of the screening task
        ScreeningStatus screeningStatus = screeningTasks[0].getScreeningStatus();
        // the screening status id should be 62
        long statusid = screeningStatus.getId();
        // the screening status name should be "Pending"
        String name = screeningStatus.getName();
        // no results should be returned in this mode, should be an empty array
        ScreeningResult[] screeningResults = screeningTasks[0].getAllScreeningResults();
        // query non-existent screening tasks without details,
        // the returned array should contain null element
        screeningTasks = manager.getScreeningTasks(new long[] {1000}, true);
    }

    /**
     * <p>
     * This method will demonstrate how to Querying Screening Task With Details.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testQueryingWithDetails() throws Exception {
        // create the ScreeningManager instance with default namespace
        ScreeningManager manager = ScreeningManagerFactory.createScreeningManager();
        // query screening task with details
        ScreeningTask screeningTask = manager.getScreeningDetails(51);
        // the screening results should be of length 5
        ScreeningResult[] screeningResults = screeningTask.getAllScreeningResults();
        // the screening result id should be 111
        long id = screeningResults[0].getId();
        // the dynamic text should be "dynamic_response_text1"
        String text = screeningResults[0].getDynamicText();
        // get the screening response of screening result
        ScreeningResponse screeningResponse = screeningResults[0].getScreeningResponse();
        // the screening response id should be 101
        id = screeningResponse.getId();
        // the response code should be "response_code_1"
        String code = screeningResponse.getResponseCode();
        // the response text should be "response_text1"
        text = screeningResponse.getResponseText();
        // get the response severity of screening response
        ResponseSeverity responseSeverity = screeningResponse.getResponseSeverity();
        // the response severity id should be 91
        id = responseSeverity.getId();
        // the response severity name should be "response_severity_lu_name_1"
        String name = responseSeverity.getName();
    }
}
