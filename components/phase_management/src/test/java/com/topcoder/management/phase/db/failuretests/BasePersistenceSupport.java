/*
 * Copyright (C) 2007 Topcoder Inc., All Rights Reserved.
 */
package com.topcoder.management.phase.db.failuretests;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

import com.topcoder.management.phase.PhasePersistenceException;
import com.topcoder.management.phase.db.AbstractInformixPhasePersistence;
import com.topcoder.management.phase.db.InformixPhasePersistence;

import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.Project;

import com.topcoder.util.idgenerator.IDGenerator;
import com.topcoder.util.idgenerator.IDGeneratorFactory;

import junit.framework.TestCase;

import java.sql.Connection;


/**
 * The Support class for all the persistence test.
 * @author waits
 * @version 1.1
 */
public abstract class BasePersistenceSupport extends TestCase {
    /** informix connection name. */
    protected static final String CONN_NAME = "informix";

    /** Datatabase conn. */
    protected Connection conn = null;
    private AbstractInformixPhasePersistence persistence = null;

    /**
     * setup environement.
     */
    protected void setUp() throws Exception {
        TestHelper.setUpConfiguration();
        this.conn = getConnection();
        TestHelper.insertData(this.conn);

        persistence = getPersistence();
    }

    /**
     * creat AbstractInformixPhasePersistence instance.
     *
     * @return AbstractInformixPhasePersistence instance.
     *
     * @throws Exception into JUnit
     */
    protected abstract AbstractInformixPhasePersistence getPersistence()
        throws Exception;

    /**
     * Test createPhase(Phase phase, String operator) with null phase, iae expected.
     *
     * @throws Exception into Junit
     */
    public void testCreatePhase_nullPhase() throws Exception {
        try {
            this.persistence.createPhase(null, "ivern");
            fail("The phase instance is null.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test createPhase(Phase phase, String operator) with null operator, iae expected.
     *
     * @throws Exception into Junit
     */
    public void testCreatePhase_nullOperator() throws Exception {
        try {
            this.persistence.createPhase(TestHelper.getPhase(1L)[0], null);
            fail("The phase operator is null.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test createPhase(Phase phase, String operator) with empty operator, iae expected.
     *
     * @throws Exception into Junit
     */
    public void testCreatePhase_emptyOperator() throws Exception {
        try {
            this.persistence.createPhase(TestHelper.getPhase(1L)[0], " ");
            fail("The phase operator is empty.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test createPhases(Phase []phase, String operator) with null phases, iae expected.
     *
     * @throws Exception into Junit
     */
    public void testCreatePhases_nullPhases() throws Exception {
        try {
            this.persistence.createPhases(null, "ivern");
            fail("The phase instances is null.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test createPhases(Phase []phase, String operator) with null phase, iae expected.
     *
     * @throws Exception into Junit
     */
    public void testCreatePhases_nullPhase() throws Exception {
        try {
            this.persistence.createPhases(new Phase[] { null }, "ivern");
            fail("The phase instance is null.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test createPhases(Phase [] phase, String operator) with null operator, iae expected.
     *
     * @throws Exception into Junit
     */
    public void testCreatePhases_nullOperator() throws Exception {
        try {
            this.persistence.createPhases(TestHelper.getPhase(1L), null);
            fail("The phase operator is null.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test createPhases(Phase [] phase, String operator) with empty operator, iae expected.
     *
     * @throws Exception into Junit
     */
    public void testCreatePhases_emptyOperator() throws Exception {
        try {
            this.persistence.createPhases(TestHelper.getPhase(1L), " ");
            fail("The phase operator is empty.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test createPhase with invalid connection name, PhasePersistenceException expected.
     *
     * @throws Exception into JUnit
     */
    public void testCreatePhases_invalidConnection() throws Exception {
        this.persistence = new InformixPhasePersistence("failure6");

        try {
            this.persistence.createPhase(TestHelper.getPhase(1L)[0], "ivern");
            fail("The connection name is invalid.");
        } catch (PhasePersistenceException e) {
            //good
        }
    }

    /**
     * Test getPhases(long[] phaseIds) with null phaseIds, iae expected.
     *
     * @throws Exception into jUnit
     */
    public void testGetPhases_nullArray() throws Exception {
        try {
            this.persistence.getPhases(null);
            fail("The phase ids is null.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test createPhase.
     *
     * @throws Exception into jUnit
     */
    public void testCreatePhases() throws Exception {
        //create phases
        Phase[] phases = TestHelper.getPhase(1L);
        this.persistence.createPhases(phases, "ivern");
    }

    /**
     * Test getProjectPhases(long[] projectIds) with null arrays, iae expected.
     *
     * @throws Exception into jUnit
     */
    public void testGetProjectPhases_nullArray() throws Exception {
        try {
            this.persistence.getProjectPhases(null);
            fail("The project ids is null.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test getProjectPhases method.
     *
     * @throws Exception into Junit
     */
    public void testGetProjectPhases() throws Exception {
        Project project = this.persistence.getProjectPhases(1);

        assertEquals("There should be no phase defined.", 0, project.getAllPhases().length);

        //create phases
        Phase[] phases = TestHelper.getPhase(1L);
        this.persistence.createPhases(phases, "ivern");

        project = this.persistence.getProjectPhases(1);
        assertNotNull("There should be one project before any persist.", project);
        assertEquals("The size of phases should be 2", 2, project.getAllPhases().length);
    }

    /**
     * Test getProjectPhases method.
     *
     * @throws Exception into Junit
     */
    public void testGetPhases() throws Exception {
        //create phases
        Phase[] phases = TestHelper.getPhase(1L);
        this.persistence.createPhases(phases, "ivern");

        Project project = this.persistence.getProjectPhases(1);

        assertNotNull("the phase should exist.", this.persistence.getPhase(project.getAllPhases()[0].getId()));
    }

    /**
     * Test isNewPhase(Phase phase) with null phase, iae expected.
     */
    public void testIsNewPhase_nullPhase() {
        try {
            this.persistence.isNewPhase(null);
            fail("The phase is null.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test isNewDependency(Dependency dependency) with null Dependency, iae expected.
     */
    public void testIsNewDependency_nullPhase() {
        try {
            this.persistence.isNewPhase(null);
            fail("The Dependency is null.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test updatePhase(Phase phase, String operator) with null phase, iae expected.
     *
     * @throws Exception into Junit
     */
    public void testUpdatePhase_nullPhase() throws Exception {
        try {
            this.persistence.updatePhase(null, getName());
            fail("The phase instance is null.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test updatePhase(Phase phase, String operator) with null operator, iae expected.
     *
     * @throws Exception into Junit
     */
    public void testUpdatePhase_nullOperator() throws Exception {
        try {
            this.persistence.updatePhase(TestHelper.getPhase(1L)[0], null);
            fail("The phase operator is null.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test updatePhase(Phase phase, String operator) with empty operator, iae expected.
     *
     * @throws Exception into Junit
     */
    public void testUpdatePhase_emptyOperator() throws Exception {
        try {
            this.persistence.updatePhase(TestHelper.getPhase(1L)[0], " ");
            fail("The phase operator is empty.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test updatePhases(Phase phase, String operator) with null phase, iae expected.
     *
     * @throws Exception into Junit
     */
    public void testupdatePhasess_nullPhase() throws Exception {
        try {
            this.persistence.updatePhases(null, getName());
            fail("The phase instance is null.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test updatePhases(Phase phase, String operator) with null operator, iae expected.
     *
     * @throws Exception into Junit
     */
    public void testupdatePhasess_nullOperator() throws Exception {
        try {
            this.persistence.updatePhases(TestHelper.getPhase(1L), null);
            fail("The phase operator is null.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test updatePhases(Phase phase, String operator) with empty operator, iae expected.
     *
     * @throws Exception into Junit
     */
    public void testupdatePhasess_emptyOperator() throws Exception {
        try {
            this.persistence.updatePhases(TestHelper.getPhase(1L), " ");
            fail("The phase operator is empty.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test deletePhase(Phase phase) with null phase, iae expected.
     *
     * @throws Exception into Junit
     */
    public void testDeletePhase_nullPhase() throws Exception {
        try {
            this.persistence.deletePhase(null);
            fail("The phase is null.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test deletePhases(Phase  [] phase) with null phases, iae expected.
     *
     * @throws Exception into Junit
     */
    public void testDeletePhases_nullPhases() throws Exception {
        try {
            this.persistence.deletePhases(null);
            fail("The phases is null.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test deletePhases(Phase  [] phase) with null phases, iae expected.
     *
     * @throws Exception into Junit
     */
    public void testDeletePhases_nullPhase() throws Exception {
        try {
            this.persistence.deletePhases(new Phase[] { null });
            fail("The phase is null.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * Test deletePhases.
     *
     * @throws Exception into Junit
     */
    public void testDeletePhase() throws Exception {
        //create phases
        Phase[] phases = TestHelper.getPhase(1L);
        this.persistence.createPhases(phases, "ivern");

        Project project = this.persistence.getProjectPhases(1);
        this.persistence.deletePhases(project.getAllPhases());

        project = this.persistence.getProjectPhases(1);
        assertEquals("There should be no phase defined.", 0, project.getAllPhases().length);
    }

    /**
     * Create DBConnectionFactory instance for testing.
     *
     * @return DBConnectionFactory instance ,not null
     *
     * @throws Exception fail to create required object
     */
    protected DBConnectionFactory createDBConnectionFactory()
        throws Exception {
        //create dbConnectionFactory for testing
        return new DBConnectionFactoryImpl(TestHelper.DB_CONNECTION_NAMESPACE);
    }

    /**
     * Create IDGenerator for testing.
     *
     * @return IDGenerator instance
     *
     * @throws Exception fail to create idGenerator
     */
    protected IDGenerator createIDGenerator() throws Exception {
        return IDGeneratorFactory.getIDGenerator("phase");
    }

    /**
     * Create connection.
     *
     * @return Connection instance
     *
     * @throws Exception fails to create instance
     */
    protected Connection getConnection() throws Exception {
        return this.createDBConnectionFactory().createConnection();
    }

    /**
     * Clear environment.
     */
    protected void tearDown() throws Exception {
        TestHelper.clearConfiguration();
        TestHelper.clearTables(conn);

        if (conn != null) {
            conn.close();
        }
    }
}
