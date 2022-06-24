/**
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved
 */
package com.topcoder.management.phase.db;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import junit.framework.TestCase;

import com.topcoder.date.workdays.DefaultWorkdaysFactory;
import com.topcoder.date.workdays.Workdays;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.project.phases.Dependency;
import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.PhaseStatus;
import com.topcoder.project.phases.PhaseType;
import com.topcoder.project.phases.Project;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.idgenerator.IDGenerator;
import com.topcoder.util.idgenerator.IDGeneratorFactory;

/**
 * Base class for all database tests. It contains common test helper methods.
 *
 * @author kr00tki
 * @version 1.0
 */
class DbTestCase extends TestCase {
    /**
     * The configuration namespace.
     */
    public static final String NAMESPACE = InformixPhasePersistenceTest.class.getName();

    /**
     * Configuration file.
     */
    public static final String CONF_FILE = "persistence_conf.xml";

    /**
     * Predefined criteria name.
     */
    public static final String CRITERIA1 = "criteria1";

    /**
     * Predefined criteria name.
     */
    public static final String CRITERIA = "criteria2";

    /**
     * Predefined criteria name.
     */
    public static final String CRITERIA3 = "criteria3";

    /**
     * Database connection name.
     */
    protected static final String CONNECTION_NAME = "informix";

    /**
     * Db Connection Factory namespace.
     */
    private static final String DB_CONN_FACTORY_NAMESPACE = DBConnectionFactoryImpl.class.getName();

    /**
     * Db Connection Factory config file.
     */
    private static final String DB_CONN_FACTORY_CONF = "db_conf.xml";

    /**
     * The Workdays instance used in tests.
     */
    private static Workdays workdays = null;

    /**
     * The DBManager used to manipulate on database.
     */
    private DbManager testDBManager = null;

    /**
     * Db connection.
     */
    private Connection connection = null;

    /**
     * ResultSet instance.
     */
    private ResultSet resultSet = null;

    /**
     * Statement instance.
     */
    private Statement statement = null;

    /**
     * The database connection factory instance used in tests.
     */
    private DBConnectionFactory factory;

    /**
     * This cache contains the Project instances mapped via the Project id.
     */
    private Map projectsCache = null;

    /**
     * The id generator used in tests.
     */
    private IDGenerator idGenerator = null;

    /**
     * Sets up test environment.
     *
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        clearConfigManager();
        ConfigManager.getInstance().add(DB_CONN_FACTORY_CONF);
        ConfigManager.getInstance().add(CONF_FILE);
        ConfigManager.getInstance().add("db_manager_conf.xml");
        testDBManager = new DbManager();
        testDBManager.clearTables();
        TestHelper.cleanDatabase();
        //TestHelper.initIDSequences();
        testDBManager.loadDataSet("test_files/dataset.xml");
        projectsCache = new HashMap();
    }

    /**
     * Clears after test.
     *
     * @throws Exception to JUnit.
     */
    protected void tearDown() throws Exception {
        testDBManager.clearTables();
        testDBManager.release();
        TestHelper.cleanDatabase();
        if (resultSet != null) {
            resultSet.close();
            resultSet = null;
        }

        if (statement != null) {
            statement.close();
            statement = null;
        }

        if (connection != null) {
            connection.close();
            connection = null;
        }
        clearConfigManager();
    }

    /**
     * Removes all namespaces from Config Manager.
     *
     * @throws Exception to JUnit.
     */
    protected void clearConfigManager() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        for (Iterator it = cm.getAllNamespaces(); it.hasNext();) {
            String ns = (String) it.next();
            cm.removeNamespace(ns);
        }
    }

    /**
     * Returns the DbConnectionFactory.
     *
     * @return the database connection factory.
     * @throws Exception to JUnit.
     */
    protected DBConnectionFactory getConnectionFactory() throws Exception {
        if (factory == null) {
            factory = new DBConnectionFactoryImpl(DB_CONN_FACTORY_NAMESPACE);
        }

        return factory;
    }

    /**
     * Returns the IdGenerator.
     *
     * @return the IDGenerator.
     * @throws Exception to JUnit.
     */
    protected IDGenerator getIDGenerator() throws Exception {
        if (idGenerator == null) {
            idGenerator = IDGeneratorFactory.getIDGenerator("phase_id_seq");
        }

        return idGenerator;
    }

    /**
     * Creates db connection.
     *
     * @return db connection.
     * @throws Exception to JUnit.
     */
    protected Connection getConnection() throws Exception {
        if (connection == null) {
            connection = new DBConnectionFactoryImpl(DB_CONN_FACTORY_NAMESPACE).createConnection(CONNECTION_NAME);
        }

        return connection;
    }

    /**
     * Returns ResultSet for given query.
     *
     * @param query db query.
     * @return query result
     * @throws Exception to JUnit.
     */
    protected ResultSet getResultSet(String query) throws Exception {
        Connection conn = getConnection();
        statement = conn.createStatement();
        resultSet = statement.executeQuery(query);
        return resultSet;
    }

    /**
     * Asserts test by query.
     *
     * @param assertionMessage assertion message.
     * @param binarySQLQuery SQL query that should return something or not.
     * @param expected expected true or false.
     * @throws Exception to JUnit.
     */
    protected void assertByQuery(String assertionMessage, String binarySQLQuery, boolean expected)
        throws Exception {
        ResultSet rs = null;
        try {
            rs = getResultSet(binarySQLQuery);
            assertEquals(assertionMessage, expected, rs.next());
        } finally {
            if (rs != null) {
                rs.close();
            }
        }
    }

    /**
     * Returns the private fields from object instance.
     *
     * @param source the source object.
     * @param fieldName the private field name.
     * @return the field value.
     * @throws Exception to JUnit.
     */
    protected static Object getField(Object source, String fieldName) throws Exception {
        Class cl = source.getClass();
        Field field = cl.getDeclaredField(fieldName);
        field.setAccessible(true);

        return field.get(source);
    }

    /**
     * Helper method to execute SQL command.
     *
     * @param sqlQuery query.
     * @throws Exception to JUnit.
     */
    protected void execute(String sqlQuery) throws Exception {
        statement = getConnection().createStatement();
        statement.execute(sqlQuery);
    }

    /**
     * Creates a project with given id.
     *
     * @param id the project id.
     * @return the Project instance.
     */
    private Project createProject(long id) {
        Project project = (Project) projectsCache.get(new Long(id));
        if (project == null) {
            project = new Project(new Date(), getWorkdays());
            project.setId(id);
            projectsCache.put(new Long(id), project);
        }

        return project;
    }

    /**
     * Returns the Workdays instance.
     *
     * @return the Workdays instance.
     */
    private static Workdays getWorkdays() {
        if (workdays == null) {
            workdays = new DefaultWorkdaysFactory().createWorkdaysInstance();
        }
        return workdays;
    }

    /**
     * Creates the Phase object. Set the dates to current value.
     *
     * @param id the Phase id.
     * @param projectId the project id to which this phase belongs to.
     * @param createFull if false - only required fields will be set.
     *
     * @return the Phase instance.
     */
    protected Phase createPhase(long id, long projectId, boolean createFull) {
        Phase phase = new Phase(createProject(projectId), id * 1000);
        phase.setId(id);
        if (createFull) {
            phase.setFixedStartDate(new Date());
            phase.setActualStartDate(new Date());
            phase.setActualEndDate(new Date());
        }
        phase.setScheduledEndDate(new Date(System.currentTimeMillis() + id * 1000));
        phase.setScheduledStartDate(new Date(System.currentTimeMillis() + id * 100));

        phase.setPhaseStatus(PhaseStatus.OPEN);
        phase.setPhaseType(new PhaseType(1, "c"));

        return phase;
    }

    /**
     * Checks if the database contains given Phase.
     *
     * @param expected the expected database content.
     * @param operator the modification operator.
     *
     * @throws Exception to JUnit.
     */
    protected void assertDatabase(Phase expected, String operator) throws Exception {
        final String query = "SELECT * from project_phase WHERE project_phase_id = ?";
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = getConnection().prepareStatement(query);
            pstmt.setLong(1, expected.getId());

            rs = pstmt.executeQuery();
            assertTrue("No phase with id: " + expected.getId(), rs.next());
            assertEquals("Incorrect project id.", expected.getProject().getId(), rs.getLong("project_id"));
            assertEquals("Incorrect phase type", expected.getPhaseType().getId(), rs.getLong("phase_type_id"));
            assertEquals("Incorrect phase status", expected.getPhaseStatus().getId(), rs
                    .getLong("phase_status_id"));
            assertEquals("Incorrect fixed start", expected.getFixedStartDate(), rs
                    .getTimestamp("fixed_start_time"));
            assertEquals("Incorrect scheduled start", expected.getScheduledStartDate(), rs
                    .getTimestamp("scheduled_start_time"));
            assertEquals("Incorrect scheduled end", expected.getScheduledEndDate(), rs
                    .getTimestamp("scheduled_end_time"));

            assertEquals("Incorrect actual start", expected.getActualStartDate(), rs
                    .getTimestamp("actual_start_time"));
            assertEquals("Incorrect actual end", expected.getActualEndDate(), rs.getTimestamp("actual_end_time"));
            assertEquals("Incorrect duration", expected.getLength(), rs.getLong("duration"));

            assertEquals("Incorrect update_user", operator, rs.getString("modify_user"));

            rs.close();
            pstmt.close();

            if (expected.getAllDependencies().length > 0) {

                statement = getConnection().createStatement();
                rs = statement.executeQuery("SELECT count(*) FROM phase_dependency WHERE dependent_phase_id = "
                        + expected.getId());

                assertTrue("Should have dependencies.", rs.next());
                assertEquals("Incorrect dependencies count.", expected.getAllDependencies().length, rs.getInt(1));
            }

        } finally {
            rs.close();
            pstmt.close();
        }

        Dependency[] deps = expected.getAllDependencies();
        for (int i = 0; i < deps.length; i++) {
            assertDatabase(deps[i]);
        }

        assertCriteria(expected);

    }

    /**
     * Checks if the expected criteria matches the ones from database.
     *
     * @param expected the phase with expected criteria.
     * @throws Exception to JUnit.
     */
    private void assertCriteria(Phase expected) throws Exception {
        final String query = "SELECT name, parameter from phase_criteria JOIN phase_criteria_type_lu "
                + "ON phase_criteria_type_lu.phase_criteria_type_id = phase_criteria.phase_criteria_type_id  "
                + "WHERE project_phase_id = ?";
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = getConnection().prepareStatement(query);
            pstmt.setLong(1, expected.getId());

            rs = pstmt.executeQuery();
            int count = 0;
            while (rs.next()) {
                count++;
                String value = (String) expected.getAttribute(rs.getString("name"));
                assertNotNull("Missing criteria in phase. Name: " + rs.getString("name"), value);
                assertEquals("Incorrect criteria value. ", value, rs.getString("parameter"));
            }

            assertEquals("Incorrect criteria count.", expected.getAttributes().size(), count);

        } finally {
            rs.close();
            pstmt.close();
        }

    }

    /**
     * Checks if the database contains given Dependency.
     *
     * @param expected the expected database content.
     *
     * @throws Exception to JUnit.
     */
    protected void assertDatabase(Dependency expected) throws Exception {
        final String query = "SELECT * FROM phase_dependency WHERE dependency_phase_id = ? AND dependent_phase_id = ?";
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = getConnection().prepareStatement(query);
            pstmt.setLong(1, expected.getDependency().getId());
            pstmt.setLong(2, expected.getDependent().getId());

            rs = pstmt.executeQuery();
            assertTrue("No such dependency", rs.next());
            assertEquals("Incorrect dependency start value.", expected.isDependencyStart(), rs
                    .getBoolean("dependency_start"));
            assertEquals("Incorrect dependent start value", expected.isDependentStart(), rs
                    .getBoolean("dependent_start"));
            assertEquals("Incorrect lag time", expected.getLagTime(), rs.getLong("lag_time"));
        } finally {
            rs.close();
            pstmt.close();
        }

    }

    /**
     * Compares two Phase objects.
     *
     * @param expected the expected Phase.
     * @param actual the actual Phase.
     *
     * @throws Exception to JUnit.
     */
    protected void assertEquals(Phase expected, Phase actual) throws Exception {
        assertEquals("Incorrect id: ", expected.getId(), actual.getId());
        assertEquals("Incorrect project id.", expected.getProject().getId(), actual.getProject().getId());
        assertEquals("Incorrect phase type", expected.getPhaseType().getId(), actual.getPhaseType().getId());
        assertEquals("Incorrect phase status", expected.getPhaseStatus().getId(), actual.getPhaseStatus().getId());
        assertEquals("Incorrect fixed start", expected.getFixedStartDate(), actual.getFixedStartDate());
        assertEquals("Incorrect scheduled start", expected.getScheduledStartDate(), actual.getScheduledStartDate());
        assertEquals("Incorrect scheduled end", expected.getScheduledEndDate(), actual.getScheduledEndDate());

        assertEquals("Incorrect actual start", expected.getActualStartDate(), actual.getActualStartDate());
        assertEquals("Incorrect actual end", expected.getActualEndDate(), actual.getActualEndDate());
        assertEquals("Incorrect length", expected.getLength(), actual.getLength());

        Dependency[] expectedDeps = expected.getAllDependencies();
        Dependency[] actualDeps = actual.getAllDependencies();
        assertEquals("Different dependencies size", expectedDeps.length, actualDeps.length);
        assertEquals("Incorrect criteria", expected.getAttributes(), actual.getAttributes());
    }

    /**
     * Compares the two Dependencies.
     *
     * @param expected the expected value.
     * @param actual the actual value.
     *
     * @throws Exception to JUnit.
     */
    protected void assertEquals(Dependency expected, Dependency actual) throws Exception {
        assertEquals(expected.getDependency(), actual.getDependency());
        assertEquals("Incorrect dependent id", expected.getDependent().getId(), actual.getDependent().getId());
        assertEquals("Incorrect dependency start.", expected.isDependencyStart(), actual.isDependencyStart());
        assertEquals("Incorrect dependent start", expected.isDependentStart(), actual.isDependentStart());
        assertEquals("Incorrect lag time", expected.getLagTime(), actual.getLagTime());
    }

    /**
     * Checks if Phase and its dependencies were removed from database.
     *
     * @param phase the database to check.
     *
     * @throws Exception to JUnit.
     */
    protected void assertNotExist(Phase phase) throws Exception {
        final String q1 = "SELECT project_phase.project_phase_id FROM project_phase, phase_dependency, "
                + "phase_criteria WHERE " + "(project_phase.project_phase_id = dependency_phase_id "
                + "OR dependent_phase_id = project_phase.project_phase_id "
                + "OR phase_criteria.project_phase_id = project_phase.project_phase_id) "
                + "AND project_phase.project_phase_id = " + phase.getId();
        assertByQuery("Phase should be removed.", q1, false);
    }
}
