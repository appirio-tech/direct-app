/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.team;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import com.topcoder.management.team.impl.TeamManagerImpl;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.idgenerator.IDGeneratorFactory;
import com.topcoder.util.log.LogManager;
import com.topcoder.util.log.basic.BasicLogFactory;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * UnitTest for TeamManagerImpl class.
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class TeamManagerImplUnitTest1Constructors extends TestCase {
    /** The ByteArrayOutputStream for testing of logs. */
    private ByteArrayOutputStream out;

    /** Provide a mock TeamPersistence for testing. */
    private MockTeamPersistence persistence;

    /** Provide a TeamManagerImpl for testing. */
    private TeamManagerImpl manager;

    /** Provide a TeamHeader for testing. */
    private TeamHeader header = new TeamHeader("header_name", false, 1, 1, 1, 30, "header_des");

    /**
     * <p>
     * Aggregates all tests in this class.
     * </p>
     * @return Test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(TeamManagerImplUnitTest1Constructors.class);
    }

    /**
     * <p>
     * Sets up the environment for the TestCase.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    protected void setUp() throws Exception {
        TestHelper.addConfig();

        // Set up an ByteArrayOutputStream for logs to write
        out = new ByteArrayOutputStream();
        LogManager.setLogFactory(new BasicLogFactory(new PrintStream(out)));

        // Prepare the manager with logger
        persistence = new MockTeamPersistence();
        manager = new TeamManagerImpl(persistence, IDGeneratorFactory.getIDGenerator(TestHelper.IDGENERATOR_NAME),
            LogManager.getLog());
    }

    /**
     * <p>
     * Tears down the environment for the TestCase.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    protected void tearDown() throws Exception {
        TestHelper.clearConfig();
    }

    /**
     * Accuracy test of <code>TeamManagerImpl()</code> constructor.
     * <p>
     * Call this constructor and perform some simple operations on it.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testTeamManagerImpl1_Accuracy1() throws Exception {
        manager = new TeamManagerImpl();
        manager.getTeam(1);
        assertTrue("logger must be used", out.toString().length() > 0);
        manager.createTeam(header, 3);
    }

    /**
     * Accuracy test of <code>TeamManagerImpl()</code> constructor.
     * <p>
     * Call this constructor with a default log.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testTeamManagerImpl1_Accuracy2() throws Exception {
        TestHelper.clearConfig();
        ConfigManager.getInstance().add("config_defaultlog.xml");
        ConfigManager.getInstance().add(TestHelper.CONFIG_DBCONNECTION);
        manager = new TeamManagerImpl();
        manager.getTeam(1);
        assertTrue("logger must be used", out.toString().length() > 0);
    }

    /**
     * Accuracy test of <code>TeamManagerImpl()</code> constructor.
     * <p>
     * Call this constructor without log - config_nolog(1).xml.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testTeamManagerImpl1_Accuracy3() throws Exception {
        TestHelper.clearConfig();
        ConfigManager.getInstance().add("config_nolog(1).xml");
        ConfigManager.getInstance().add(TestHelper.CONFIG_DBCONNECTION);
        manager = new TeamManagerImpl();
        manager.getTeam(1);
        assertTrue("no log should be used", out.toString().length() == 0);
    }

    /**
     * Accuracy test of <code>TeamManagerImpl()</code> constructor.
     * <p>
     * Call this constructor without log - config_nolog(2).xml.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testTeamManagerImpl1_Accuracy4() throws Exception {
        TestHelper.clearConfig();
        ConfigManager.getInstance().add("config_nolog(2).xml");
        ConfigManager.getInstance().add(TestHelper.CONFIG_DBCONNECTION);
        manager = new TeamManagerImpl();
        manager.getTeam(1);
        assertTrue("no log should be used", out.toString().length() == 0);
    }

    /**
     * Accuracy test of <code>TeamManagerImpl()</code> constructor.
     * <p>
     * Call this constructor without log - config_nolog(3).xml.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testTeamManagerImpl1_Accuracy5() throws Exception {
        TestHelper.clearConfig();
        ConfigManager.getInstance().add("config_nolog(3).xml");
        ConfigManager.getInstance().add(TestHelper.CONFIG_DBCONNECTION);
        manager = new TeamManagerImpl();
        manager.getTeam(1);
        assertTrue("no log should be used", out.toString().length() == 0);
    }

    /**
     * Failure test of <code>TeamManagerImpl()</code> constructor.
     * <p>
     * Call this constructor with a bad config - no specNamespace.
     * </p>
     * <p>
     * Expect TeamConfigurationException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testTeamManagerImpl1_Failure1() throws Exception {
        try {
            TestHelper.clearConfig();
            ConfigManager.getInstance().add("config_bad(1).xml");
            ConfigManager.getInstance().add(TestHelper.CONFIG_DBCONNECTION);
            new TeamManagerImpl();
            fail("Expect TeamConfigurationException.");
        } catch (TeamConfigurationException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>TeamManagerImpl()</code> constructor.
     * <p>
     * Call this constructor with a bad config - invalid specNamespace.
     * </p>
     * <p>
     * Expect TeamConfigurationException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testTeamManagerImpl1_Failure2() throws Exception {
        try {
            TestHelper.clearConfig();
            ConfigManager.getInstance().add("config_bad(2).xml");
            ConfigManager.getInstance().add(TestHelper.CONFIG_DBCONNECTION);
            new TeamManagerImpl();
            fail("Expect TeamConfigurationException.");
        } catch (TeamConfigurationException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>TeamManagerImpl()</code> constructor.
     * <p>
     * Call this constructor with a bad config - no persistenceKey.
     * </p>
     * <p>
     * Expect TeamConfigurationException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testTeamManagerImpl1_Failure3() throws Exception {
        try {
            TestHelper.clearConfig();
            ConfigManager.getInstance().add("config_bad(3).xml");
            ConfigManager.getInstance().add(TestHelper.CONFIG_DBCONNECTION);
            new TeamManagerImpl();
            fail("Expect TeamConfigurationException.");
        } catch (TeamConfigurationException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>TeamManagerImpl()</code> constructor.
     * <p>
     * Call this constructor with a bad config - invalid persistenceKey.
     * </p>
     * <p>
     * Expect TeamConfigurationException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testTeamManagerImpl1_Failure4() throws Exception {
        try {
            TestHelper.clearConfig();
            ConfigManager.getInstance().add("config_bad(4).xml");
            ConfigManager.getInstance().add(TestHelper.CONFIG_DBCONNECTION);
            new TeamManagerImpl();
            fail("Expect TeamConfigurationException.");
        } catch (TeamConfigurationException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>TeamManagerImpl()</code> constructor.
     * <p>
     * Call this constructor with a bad config - no idGenerator.
     * </p>
     * <p>
     * Expect TeamConfigurationException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testTeamManagerImpl1_Failure5() throws Exception {
        try {
            TestHelper.clearConfig();
            ConfigManager.getInstance().add("config_bad(5).xml");
            ConfigManager.getInstance().add(TestHelper.CONFIG_DBCONNECTION);
            new TeamManagerImpl();
            fail("Expect TeamConfigurationException.");
        } catch (TeamConfigurationException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>TeamManagerImpl()</code> constructor.
     * <p>
     * Call this constructor with a bad config - invalid idGenerator.
     * </p>
     * <p>
     * Expect TeamConfigurationException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testTeamManagerImpl1_Failure6() throws Exception {
        try {
            TestHelper.clearConfig();
            ConfigManager.getInstance().add("config_bad(6).xml");
            ConfigManager.getInstance().add(TestHelper.CONFIG_DBCONNECTION);
            new TeamManagerImpl();
            fail("Expect TeamConfigurationException.");
        } catch (TeamConfigurationException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>TeamManagerImpl()</code> constructor.
     * <p>
     * Call this constructor with a bad config - invalid ObjectFactory setting.
     * </p>
     * <p>
     * Expect TeamConfigurationException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testTeamManagerImpl1_Failure7() throws Exception {
        try {
            TestHelper.clearConfig();
            ConfigManager.getInstance().add("config_bad(7).xml");
            ConfigManager.getInstance().add(TestHelper.CONFIG_DBCONNECTION);
            new TeamManagerImpl();
            fail("Expect TeamConfigurationException.");
        } catch (TeamConfigurationException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>TeamManagerImpl()</code> constructor.
     * <p>
     * Call this constructor with a bad config - wrong ObjectFactory type.
     * </p>
     * <p>
     * Expect TeamConfigurationException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testTeamManagerImpl1_Failure8() throws Exception {
        try {
            TestHelper.clearConfig();
            ConfigManager.getInstance().add("config_bad(8).xml");
            ConfigManager.getInstance().add(TestHelper.CONFIG_DBCONNECTION);
            new TeamManagerImpl();
            fail("Expect TeamConfigurationException.");
        } catch (TeamConfigurationException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>TeamManagerImpl(String namespace)</code> constructor.
     * <p>
     * Call this constructor and perform some simple operations on it.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testTeamManagerImpl2_Accuracy1() throws Exception {
        manager = new TeamManagerImpl(TeamManagerImpl.DEFAULT_NAMESPACE);
        manager.getTeam(1);
        assertTrue("logger must be used", out.toString().length() > 0);
        manager.createTeam(header, 3);
    }

    /**
     * Accuracy test of <code>TeamManagerImpl(String namespace)</code> constructor.
     * <p>
     * Call this constructor with a default log.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testTeamManagerImpl2_Accuracy2() throws Exception {
        TestHelper.clearConfig();
        ConfigManager.getInstance().add("config_defaultlog.xml");
        ConfigManager.getInstance().add(TestHelper.CONFIG_DBCONNECTION);
        manager = new TeamManagerImpl(TeamManagerImpl.DEFAULT_NAMESPACE);
        manager.getTeam(1);
        assertTrue("logger must be used", out.toString().length() > 0);
    }

    /**
     * Accuracy test of <code>TeamManagerImpl(String namespace)</code> constructor.
     * <p>
     * Call this constructor without log - config_nolog(1).xml.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testTeamManagerImpl2_Accuracy3() throws Exception {
        TestHelper.clearConfig();
        ConfigManager.getInstance().add("config_nolog(1).xml");
        ConfigManager.getInstance().add(TestHelper.CONFIG_DBCONNECTION);
        manager = new TeamManagerImpl(TeamManagerImpl.DEFAULT_NAMESPACE);
        manager.getTeam(1);
        assertTrue("no log should be used", out.toString().length() == 0);
    }

    /**
     * Accuracy test of <code>TeamManagerImpl(String namespace)</code> constructor.
     * <p>
     * Call this constructor without log - config_nolog(2).xml.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testTeamManagerImpl2_Accuracy4() throws Exception {
        TestHelper.clearConfig();
        ConfigManager.getInstance().add("config_nolog(2).xml");
        ConfigManager.getInstance().add(TestHelper.CONFIG_DBCONNECTION);
        manager = new TeamManagerImpl(TeamManagerImpl.DEFAULT_NAMESPACE);
        manager.getTeam(1);
        assertTrue("no log should be used", out.toString().length() == 0);
    }

    /**
     * Accuracy test of <code>TeamManagerImpl(String namespace)</code> constructor.
     * <p>
     * Call this constructor without log - config_nolog(3).xml.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testTeamManagerImpl2_Accuracy5() throws Exception {
        TestHelper.clearConfig();
        ConfigManager.getInstance().add("config_nolog(3).xml");
        ConfigManager.getInstance().add(TestHelper.CONFIG_DBCONNECTION);
        manager = new TeamManagerImpl(TeamManagerImpl.DEFAULT_NAMESPACE);
        manager.getTeam(1);
        assertTrue("no log should be used", out.toString().length() == 0);
    }

    /**
     * Failure test of <code>TeamManagerImpl(String namespace)</code> constructor.
     * <p>
     * Call this constructor with a bad config - no specNamespace.
     * </p>
     * <p>
     * Expect TeamConfigurationException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testTeamManagerImpl2_Failure1() throws Exception {
        try {
            TestHelper.clearConfig();
            ConfigManager.getInstance().add("config_bad(1).xml");
            ConfigManager.getInstance().add(TestHelper.CONFIG_DBCONNECTION);
            new TeamManagerImpl(TeamManagerImpl.DEFAULT_NAMESPACE);
            fail("Expect TeamConfigurationException.");
        } catch (TeamConfigurationException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>TeamManagerImpl(String namespace)</code> constructor.
     * <p>
     * Call this constructor with a bad config - invalid specNamespace.
     * </p>
     * <p>
     * Expect TeamConfigurationException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testTeamManagerImpl2_Failure2() throws Exception {
        try {
            TestHelper.clearConfig();
            ConfigManager.getInstance().add("config_bad(2).xml");
            ConfigManager.getInstance().add(TestHelper.CONFIG_DBCONNECTION);
            new TeamManagerImpl(TeamManagerImpl.DEFAULT_NAMESPACE);
            fail("Expect TeamConfigurationException.");
        } catch (TeamConfigurationException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>TeamManagerImpl(String namespace)</code> constructor.
     * <p>
     * Call this constructor with a bad config - no persistenceKey.
     * </p>
     * <p>
     * Expect TeamConfigurationException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testTeamManagerImpl2_Failure3() throws Exception {
        try {
            TestHelper.clearConfig();
            ConfigManager.getInstance().add("config_bad(3).xml");
            ConfigManager.getInstance().add(TestHelper.CONFIG_DBCONNECTION);
            new TeamManagerImpl(TeamManagerImpl.DEFAULT_NAMESPACE);
            fail("Expect TeamConfigurationException.");
        } catch (TeamConfigurationException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>TeamManagerImpl(String namespace)</code> constructor.
     * <p>
     * Call this constructor with a bad config - invalid persistenceKey.
     * </p>
     * <p>
     * Expect TeamConfigurationException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testTeamManagerImpl2_Failure4() throws Exception {
        try {
            TestHelper.clearConfig();
            ConfigManager.getInstance().add("config_bad(4).xml");
            ConfigManager.getInstance().add(TestHelper.CONFIG_DBCONNECTION);
            new TeamManagerImpl(TeamManagerImpl.DEFAULT_NAMESPACE);
            fail("Expect TeamConfigurationException.");
        } catch (TeamConfigurationException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>TeamManagerImpl(String namespace)</code> constructor.
     * <p>
     * Call this constructor with a bad config - no idGenerator.
     * </p>
     * <p>
     * Expect TeamConfigurationException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testTeamManagerImpl2_Failure5() throws Exception {
        try {
            TestHelper.clearConfig();
            ConfigManager.getInstance().add("config_bad(5).xml");
            ConfigManager.getInstance().add(TestHelper.CONFIG_DBCONNECTION);
            new TeamManagerImpl(TeamManagerImpl.DEFAULT_NAMESPACE);
            fail("Expect TeamConfigurationException.");
        } catch (TeamConfigurationException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>TeamManagerImpl(String namespace)</code> constructor.
     * <p>
     * Call this constructor with a bad config - invalid idGenerator.
     * </p>
     * <p>
     * Expect TeamConfigurationException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testTeamManagerImpl2_Failure6() throws Exception {
        try {
            TestHelper.clearConfig();
            ConfigManager.getInstance().add("config_bad(6).xml");
            ConfigManager.getInstance().add(TestHelper.CONFIG_DBCONNECTION);
            new TeamManagerImpl(TeamManagerImpl.DEFAULT_NAMESPACE);
            fail("Expect TeamConfigurationException.");
        } catch (TeamConfigurationException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>TeamManagerImpl(String namespace)</code> constructor.
     * <p>
     * Call this constructor with a bad config - invalid ObjectFactory setting.
     * </p>
     * <p>
     * Expect TeamConfigurationException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testTeamManagerImpl2_Failure7() throws Exception {
        try {
            TestHelper.clearConfig();
            ConfigManager.getInstance().add("config_bad(7).xml");
            ConfigManager.getInstance().add(TestHelper.CONFIG_DBCONNECTION);
            new TeamManagerImpl(TeamManagerImpl.DEFAULT_NAMESPACE);
            fail("Expect TeamConfigurationException.");
        } catch (TeamConfigurationException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>TeamManagerImpl(String namespace)</code> constructor.
     * <p>
     * Call this constructor with a bad config - wrong ObjectFactory type.
     * </p>
     * <p>
     * Expect TeamConfigurationException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testTeamManagerImpl2_Failure8() throws Exception {
        try {
            TestHelper.clearConfig();
            ConfigManager.getInstance().add("config_bad(8).xml");
            ConfigManager.getInstance().add(TestHelper.CONFIG_DBCONNECTION);
            new TeamManagerImpl(TeamManagerImpl.DEFAULT_NAMESPACE);
            fail("Expect TeamConfigurationException.");
        } catch (TeamConfigurationException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>TeamManagerImpl(String namespace)</code> constructor.
     * <p>
     * Call this constructor with invalid namespace.
     * </p>
     * <p>
     * Expect TeamConfigurationException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testTeamManagerImpl2_Failure9() throws Exception {
        try {
            new TeamManagerImpl("invalid");
            fail("Expect TeamConfigurationException.");
        } catch (TeamConfigurationException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>TeamManagerImpl(String namespace)</code> constructor.
     * <p>
     * Call this method with null namespace.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testTeamManagerImpl2_Failure10() throws Exception {
        try {
            new TeamManagerImpl(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>TeamManagerImpl(String namespace)</code> constructor.
     * <p>
     * Call this method with trimmed empty namespace.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testTeamManagerImpl2_Failure11() throws Exception {
        try {
            new TeamManagerImpl("  ");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>TeamManagerImpl(TeamPersistence persistence, IDGenerator idGenerator, Log logger)</code>
     * constructor.
     * <p>
     * The correctness for this constructor will be tested throughout this class, so here we will simply invoke this
     * constructor without log.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testTeamManagerImpl3_Accuracy() throws Exception {
        manager = new TeamManagerImpl(persistence, IDGeneratorFactory.getIDGenerator(TestHelper.IDGENERATOR_NAME),
            null);
        manager.getTeam(1);
        assertTrue("no log should be used", out.toString().length() == 0);
    }

    /**
     * Failure test of <code>TeamManagerImpl(TeamPersistence persistence, IDGenerator idGenerator, Log logger)</code>
     * constructor.
     * <p>
     * Call this constructor with null persistence.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testTeamManagerImpl3_Failure1() throws Exception {
        try {
            new TeamManagerImpl(null, IDGeneratorFactory.getIDGenerator(TestHelper.IDGENERATOR_NAME), LogManager
                .getLog());
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>TeamManagerImpl(TeamPersistence persistence, IDGenerator idGenerator, Log logger)</code>
     * constructor.
     * <p>
     * Call this constructor with null IDGenerator.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testTeamManagerImpl3_Failure2() throws Exception {
        try {
            new TeamManagerImpl(persistence, null, LogManager.getLog());
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }
}
