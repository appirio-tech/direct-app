/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project.persistence.stresstests;

import java.sql.Connection;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.management.project.ConfigurationException;
import com.topcoder.management.project.PersistenceException;
import com.topcoder.management.project.persistence.InformixProjectPersistence;

/**
 * <p>
 * This class is the mock class of <code>InformixProjectPersistence</code> for stress test.
 * </p>
 *
 * <p>
 * <b>Thread Safety:</b> This class is thread-safe.
 * </p>
 *
 * @author Hacker_QC
 * @version 1.1
 */
class MockPersistence extends InformixProjectPersistence {

    public MockPersistence(String namespace) throws ConfigurationException, PersistenceException {
        super(namespace);
    }

    public Connection openConnection() throws PersistenceException {
        return super.openConnection();
    }

    public void closeConnection(Connection connection) throws PersistenceException {
        super.closeConnection(connection);
    }

    public void closeConnectionOnError(Connection connection) throws PersistenceException {
        super.closeConnectionOnError(connection);
    }

    public String getConnectionName() {
        return super.getConnectionName();
    }

    public DBConnectionFactory getConnectionFactory() {
        return super.getConnectionFactory();
    }
}