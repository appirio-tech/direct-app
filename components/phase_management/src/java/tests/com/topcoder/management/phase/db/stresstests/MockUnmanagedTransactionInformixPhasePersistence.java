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

/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.phase.db.stresstests;

import com.topcoder.db.connectionfactory.DBConnectionFactory;

import com.topcoder.management.phase.PhasePersistenceException;
import com.topcoder.management.phase.db.UnmanagedTransactionInformixPhasePersistence;

import com.topcoder.util.idgenerator.IDGenerator;

import java.sql.Connection;

import java.util.Map;


/**
 * mock class AbstractInformixPhasePersistence
 *
 * @author TCSDEVELOPER
 * @version 1.1
 */
public class MockUnmanagedTransactionInformixPhasePersistence
    extends UnmanagedTransactionInformixPhasePersistence {
	/**
	 * constructor
	 * @param connectionFactory
	 * @param connectionName
	 * @param idGen
	 */
    public MockUnmanagedTransactionInformixPhasePersistence(
        DBConnectionFactory connectionFactory, String connectionName,
        IDGenerator idGen) {
        super(connectionFactory, connectionName, idGen);
    }

    /**
     * mock the method disposeConnection()
     */
    protected void disposeConnection(Connection connection) {
        super.disposeConnection(connection);
    }

    /**
     * mock the method commitTransaction()
     */
    protected void commitTransaction(Map context) {
        super.commitTransaction(context);
    }

    /**
     * mock the method rollbackTransaction();
     * @throws PhasePersistenceException 
     */
    protected void rollbackTransaction(Map context) throws PhasePersistenceException {
        super.rollbackTransaction(context);
    }

    /**
     * mock the method startTransaction();
     */
    protected void startTransaction(Map context) {
        super.startTransaction(context);
    }
}
