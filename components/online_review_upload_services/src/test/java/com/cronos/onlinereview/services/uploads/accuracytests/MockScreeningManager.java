/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.services.uploads.accuracytests;

import com.cronos.onlinereview.autoscreening.management.PersistenceException;
import com.cronos.onlinereview.autoscreening.management.ScreeningManager;
import com.cronos.onlinereview.autoscreening.management.ScreeningTask;
import com.cronos.onlinereview.autoscreening.management.ScreeningTaskAlreadyExistsException;
import com.cronos.onlinereview.autoscreening.management.ScreeningTaskDoesNotExistException;

/**
 * A mock implementation of ScreeningManager.
 * 
 * @author kshatriyan
 * @version 1.0
 */
public class MockScreeningManager implements ScreeningManager {

    /**
     * Used for testing.
     */
    private long submissionId = -1;

    /**
     * Used for testing.
     */
    private String userId = null;

    /**
     * Mock implementation.
     * 
     * @param arg0
     *            project id
     * @param arg1
     *            operator
     * @throws PersistenceException
     *             not thrown
     * @throws ScreeningTaskAlreadyExistsException
     *             not thrown
     */
    public void initiateScreening(long arg0, String arg1) throws PersistenceException,
            ScreeningTaskAlreadyExistsException {
        submissionId = arg0;
        userId = arg1;
    }

    /**
     * Not used.
     * 
     * @param arg0
     *            id
     * @return always null
     * @throws PersistenceException
     *             not thrown
     * @throws ScreeningTaskDoesNotExistException
     *             not thrown
     */
    public ScreeningTask getScreeningDetails(long arg0) throws PersistenceException,
            ScreeningTaskDoesNotExistException {
        return null;
    }

    /**
     * Not used.
     * 
     * @param arg0
     *            id
     * @return always null
     * @throws PersistenceException
     *             not thrown
     * @throws ScreeningTaskDoesNotExistException
     *             not thrown
     */
    public ScreeningTask[] getScreeningTasks(long[] arg0) throws PersistenceException,
            ScreeningTaskDoesNotExistException {
        return null;
    }

    /**
     * Not used.
     * 
     * @param arg0
     *            id
     * @param arg1
     *            boolean
     * @return always null
     * @throws PersistenceException
     *             not thrown
     * @throws ScreeningTaskDoesNotExistException
     *             not thrown
     */
    public ScreeningTask[] getScreeningTasks(long[] arg0, boolean arg1) throws PersistenceException,
            ScreeningTaskDoesNotExistException {
        return null;
    }

    /**
     * @return the submissionId
     */
    public long getSubmissionId() {
        return submissionId;
    }

    /**
     * @return the userId
     */
    public String getUserId() {
        return userId;
    }
}
