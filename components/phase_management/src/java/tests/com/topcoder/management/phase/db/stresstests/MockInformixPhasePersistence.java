/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.phase.db.stresstests;

import com.topcoder.db.connectionfactory.DBConnectionFactory;

import com.topcoder.management.phase.ConfigurationException;
import com.topcoder.management.phase.PhasePersistenceException;
import com.topcoder.management.phase.db.InformixPhasePersistence;

import com.topcoder.util.idgenerator.IDGenerator;

import java.sql.Connection;

import java.util.Map;


/**
 * mock class InformixPhasePersistence for stress testing.
 *
 * @author KLW
 * @version 1.1
 */
public class MockInformixPhasePersistence extends InformixPhasePersistence {
    /**
     * constructor
     * @param connectionFactory
     * @param connectionName
     * @param idGen
     */
    public MockInformixPhasePersistence(DBConnectionFactory connectionFactory,
        String connectionName, IDGenerator idGen) {
        super(connectionFactory, connectionName, idGen);
    }
    /**
     * <p>An simple constructor which will populate the connectionFactory and connectionName information
     * from configuration.</p>
     *
     *
     * @param namespace config namespace
     * @throws IllegalArgumentException if namespace is an empty string or a null.
     * @throws ConfigurationException if any of the required configuration parameters are missing or are incorrect.
     */
        public MockInformixPhasePersistence(String namespace) throws ConfigurationException {
            super(namespace);
        }
    /**
     * mock the method disposeConnection()
     * @throws PhasePersistenceException 
     */
    protected void disposeConnection(Connection connection) {
        super.disposeConnection(connection);
    }

    /**
     * mock the method commitTransaction()
     * @throws PhasePersistenceException 
     */
    protected void commitTransaction(Map context) throws PhasePersistenceException {
        super.commitTransaction(context);
    }

    /**
     * mock the method rollbackTransaction();
     * @throws PhasePersistenceException
     */
    protected void rollbackTransaction(Map context)
        throws PhasePersistenceException {
        super.rollbackTransaction(context);
    }

    /**
     * mock the method startTransaction();
     * @throws PhasePersistenceException 
     */
    protected void startTransaction(Map context) {
        super.startTransaction(context);
    }
}
