/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.management;

import java.sql.Connection;

import com.cronos.onlinereview.autoscreening.management.db.DbScreeningManager;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

/**
 * This is a dummy class of DbScreeningManager used for test.
 *
 * @author haozhangr
 * @version 1.0
 */
class MockDbScreeningManager extends DbScreeningManager {

    /**
     * A wrap constructor of super(DBConnectionFactory).
     *
     * @param connectionFactory
     *            the connection factory to use
     */
    protected MockDbScreeningManager(DBConnectionFactoryImpl connectionFactory) {
        super(connectionFactory);
    }

    /**
     * A wrap constructor of super(DBConnectionFactory, String).
     *
     * @param connectionFactory
     *            the connection factory to use
     * @param connectionName
     *            the connection producer name
     */
    protected MockDbScreeningManager(DBConnectionFactoryImpl connectionFactory,
            String connectionName) {
        super(connectionFactory, connectionName);
    }

    /**
     * A wrap method of super.createConnection().
     *
     * @return a new database connection
     * @throws PersistenceException
     *             if any error occurs in persistence
     */
    protected Connection createConnection() throws PersistenceException {
        return super.createConnection();
    }

    /**
     * This class will not be implemented to test purpose.
     *
     * @param upload
     *            the upload identifier
     * @return the screening task with details
     * @throws IllegalArgumentException
     *             if upload is non-positive
     * @throws PersistenceException
     *             if any error occurs in persistence
     * @throws ScreeningTaskDoesNotExistException
     *             if the upload was not initiated a screening task
     */
    public ScreeningTask getScreeningDetails(long upload) throws PersistenceException,
            ScreeningTaskDoesNotExistException {
        return null;
    }

    /**
     * This class will not be implemented to test purpose.
     *
     * @param uploads
     *            the upload identifiers
     * @return the screening tasks without details
     * @throws IllegalArgumentException
     *             if uploads is null, or any of the uploads is non-positive, or uploads contains
     *             duplicated elements
     * @throws PersistenceException
     *             if any error occurs in persistence
     * @throws ScreeningTaskDoesNotExistException
     *             if any of the uploads was not initiated a screening task
     */
    public ScreeningTask[] getScreeningTasks(long[] uploads) throws PersistenceException,
            ScreeningTaskDoesNotExistException {

        return null;
    }

    /**
     * This class will not be implemented to test purpose.
     *
     * @param uploads
     *            the upload identifiers
     * @param allowNonExist
     *            whether it allows upload with no screening task initiated
     * @return the screening tasks without details
     * @throws IllegalArgumentException
     *             if uploads is null, or any of the uploads is non-positive, or uploads contains
     *             duplicated elements
     * @throws PersistenceException
     *             if any error occurs in persistence
     * @throws ScreeningTaskDoesNotExistException
     *             if any of the uploads was not initiated a screening task, and allowNotExist is
     *             false
     */
    public ScreeningTask[] getScreeningTasks(long[] uploads, boolean allowNonExist)
            throws PersistenceException, ScreeningTaskDoesNotExistException {

        return null;
    }

    /**
     * This class will not be implemented to test purpose.
     *
     * @param upload
     *            the upload identifier
     * @param operator
     *            the creation user of the screening task
     * @throws IllegalArgumentException
     *             if upload is non-positive, or operator is null or empty String
     * @throws PersistenceException
     *             if any error occurs in persistence
     * @throws ScreeningTaskAlreadyExistsException
     *             if the upload has been used to initiate a screening task
     */
    public void initiateScreening(long upload, String operator) throws PersistenceException,
            ScreeningTaskAlreadyExistsException {

    }

}
