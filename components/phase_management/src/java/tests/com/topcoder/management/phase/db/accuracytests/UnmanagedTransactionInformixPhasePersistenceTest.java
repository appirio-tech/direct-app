/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.phase.db.accuracytests;

import java.sql.Connection;

import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.management.phase.AbstractDbPhasePersistence;
import com.topcoder.management.phase.db.UnmanagedTransactionInformixPhasePersistence;
import com.topcoder.util.idgenerator.IDGenerator;
import com.topcoder.util.idgenerator.IDGeneratorFactory;

import junit.framework.TestCase;

/**
 * Accuracy tests for <code>UnmanagedTransactionInformixPhasePersistence</code>.
 *
 * @author Savior
 * @version 1.1
 */
public class UnmanagedTransactionInformixPhasePersistenceTest extends TestCase {
    /**
     * Represents a <code>InformixPhasePersistence</code> to test against.
     */
    private UnmanagedTransactionInformixPhasePersistence persistence = null;

    /**
     * <p>
     * The id generator.
     * </p>
     */
    private IDGenerator generator = null;

    /**
     * Set up.
     * <p>
     * Load configuration, prepare database, and create the testing instances.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();

        TestHelper.loadConfigurations();
        TestHelper.executeDBScript(TestHelper.SCRIPT_FILL);

        generator = IDGeneratorFactory.getIDGenerator("phase_id_seq");

        persistence =
            new UnmanagedTransactionInformixPhasePersistence(new DBConnectionFactoryImpl(
                TestHelper.CONNECTION_FACTORY_NAMESPACE), "informix", generator);
    }

    /**
     * Tear down.
     * <p>
     * Clear database, close all connections, clear configurations.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    protected void tearDown() throws Exception {
        TestHelper.executeDBScript(TestHelper.SCRIPT_CLEAR);
        TestHelper.closeAllConnections();
        TestHelper.clearAllConfigurations();

        super.tearDown();
    }

    /**
     * Test <code>disposeConnection(Connection)</code>.
     *
     * @throws Exception to JUnit.
     */
    public void testDisposeConnection() throws Exception {
        Connection connection =
            (Connection) TestHelper.invokeMethod(AbstractDbPhasePersistence.class, persistence,
                "getConnection", new Class[] {}, new Object[] {});
        assertFalse("The connection has not been closed.", connection.isClosed());

        TestHelper.invokeMethod(persistence, "disposeConnection", new Class[] {Connection.class},
            new Object[] {connection});

        assertTrue("The connection should be closed.", connection.isClosed());
    }
}
