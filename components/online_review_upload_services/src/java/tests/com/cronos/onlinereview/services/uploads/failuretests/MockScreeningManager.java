/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.services.uploads.failuretests;

import com.cronos.onlinereview.autoscreening.management.PersistenceException;
import com.cronos.onlinereview.autoscreening.management.ScreeningManager;
import com.cronos.onlinereview.autoscreening.management.ScreeningTask;
import com.cronos.onlinereview.autoscreening.management.ScreeningTaskAlreadyExistsException;
import com.cronos.onlinereview.autoscreening.management.ScreeningTaskDoesNotExistException;

/**
 * A mock implementation of ScreeningManager.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockScreeningManager implements ScreeningManager {
    /**
     * A static state variable for the mock.
     */
    private static int state = 0;

    /**
     * Mock implementation. Will throw exceptions based on state.
     *
     * @param arg0
     *            project id
     * @param arg1
     *            operator
     * @throws PersistenceException
     *             if the state is 1
     * @throws ScreeningTaskAlreadyExistsException
     *             if the state is 2
     */
    public void initiateScreening(long arg0, String arg1) throws PersistenceException,
            ScreeningTaskAlreadyExistsException {
        if (getState() == 1) {
            throw new PersistenceException("Mock");
        }
        if (getState() == 2) {
            throw new ScreeningTaskAlreadyExistsException(arg0);
        }

    }

    /**
     * Not implemented.
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
     * Not implemented.
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
     * Not implemented.
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
     * Sets the state.
     *
     * @param state
     *            the state to set
     */
    static void setState(int state) {
        MockScreeningManager.state = state;
    }

    /**
     * Gets the state.
     *
     * @return the state
     */
    static int getState() {
        return state;
    }

}
