/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.phase.db.accuracytests;

import java.lang.reflect.Constructor;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import com.topcoder.date.workdays.DefaultWorkdays;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.management.phase.AbstractDbPhasePersistence;
import com.topcoder.management.phase.db.InformixPhasePersistence;
import com.topcoder.project.phases.Dependency;
import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.PhaseStatus;
import com.topcoder.project.phases.PhaseType;
import com.topcoder.project.phases.Project;
import com.topcoder.util.idgenerator.IDGenerator;
import com.topcoder.util.idgenerator.IDGeneratorFactory;

import junit.framework.TestCase;

/**
 * Accuracy tests for <code>AbstractDbPhasePersistence</code>.
 * <p>
 * This class tests the protected methods of <code>AbstractDbPhasePersistence</code> only. The tests for the
 * constructors are located at <code>InformixPhasePersistenceTest</code> class.
 *
 * @author Savior
 * @version 1.1
 */
public class AbstractDbPhasePersistenceTest extends TestCase {
    /**
     * Represents a <code>AbstractDbPhasePersistence</code> to test against.
     */
    private AbstractDbPhasePersistence persistence = null;

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
        TestHelper.loadConfigurations();
        TestHelper.executeDBScript(TestHelper.SCRIPT_FILL);

        generator = IDGeneratorFactory.getIDGenerator("phase_id_seq");

        persistence =
            new InformixPhasePersistence(
                new DBConnectionFactoryImpl(TestHelper.CONNECTION_FACTORY_NAMESPACE), "informix", generator);
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
    }

    /**
     * Test <code>getConnection()</code>.
     *
     * @throws Exception to JUnit.
     */
    public void testGetConnection() throws Exception {
        Connection connection =
            (Connection) TestHelper.invokeMethod(AbstractDbPhasePersistence.class, persistence,
                "getConnection", new Class[] {}, new Object[] {});

        assertEquals("getConnection() should be correct.", "demo", connection.getCatalog());
    }

    /**
     * Test <code>getConnection()</code> when connectionName does not exist in configuration. The connection
     * should be created.
     *
     * @throws Exception to JUnit.
     */
    public void testGetConnectionNoConnectionName() throws Exception {
        persistence =
            new InformixPhasePersistence(TestHelper.PHASE_PERSISTENCE_NAMESPACE + ".NoConnectionName");

        Connection connection =
            (Connection) TestHelper.invokeMethod(AbstractDbPhasePersistence.class, persistence,
                "getConnection", new Class[] {}, new Object[] {});

        assertEquals("getConnection() should be correct.", "demo", connection.getCatalog());
    }

    /**
     * Test <code>getIDGenerator()</code>.
     *
     * @throws Exception to JUnit.
     */
    public void testGetIDGenerator() throws Exception {
        assertEquals("getIDGenerator() should be correct.", generator, TestHelper.invokeMethod(
            AbstractDbPhasePersistence.class, persistence, "getIDGenerator", new Class[] {}, new Object[] {}));
    }
}
