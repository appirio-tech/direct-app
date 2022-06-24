/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.scorecard.persistence.accuracytests;

import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import com.topcoder.management.scorecard.persistence.InformixScorecardPersistence;
import com.topcoder.management.scorecard.data.QuestionType;
import com.topcoder.management.scorecard.data.ScorecardType;
import com.topcoder.management.scorecard.data.ScorecardStatus;
import com.topcoder.management.scorecard.data.Scorecard;
import com.topcoder.management.scorecard.data.Group;
import com.topcoder.management.scorecard.data.Section;
import com.topcoder.management.scorecard.data.Question;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

import java.io.File;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;

/**
 * <p>An accuracy test for {@link com.topcoder.management.scorecard.persistence.InformixScorecardPersistence} class.
 * Tests the class for producing the accurate results being provided with valid input data.</p>
 *
 * @author  isv
 * @version 1.0
 */
public class InformixScorecardPersistenceTest extends TestCase {

    /**
     * <p>A <code>String</code> providing the configuration namespace utilized by this test.</p>
     */
    public static final String NAMESPACE = InformixScorecardPersistenceTest.class.getName();

    /**
     * <p>An instance of {@link InformixScorecardPersistence} which is tested. This instance is initialized in
     * {@link #setUp()} method and released in {@link #tearDown()} method.<p>
     */
    private InformixScorecardPersistence testedInstance = null;

    /**
     * <p>A <code>Connection</code> used by this test case for accesing the target database.</p>
     */
    private Connection connection = null;

    /**
     * <p>A <code>String</code> providing the sample name of operator.</p>
     */
    private static final String EXISTING_OPERATOR = "ACCURACY TEST";

    /**
     * <p>Gets the test suite for {@link InformixScorecardPersistence} class.</p>
     *
     * @return a <code>TestSuite</code> providing the tests for {@link InformixScorecardPersistence} class.
     */
    public static Test suite() {
        return new TestSuite(InformixScorecardPersistenceTest.class);
    }

    /**
     * <p>Sets up the fixture. This method is called before a test is executed.</p>
     *
     * @throws Exception if any error occurs.
     */
    protected void setUp() throws Exception {
        ConfigHelper.releaseNamespaces();
        ConfigHelper.loadConfiguration(new File("accuracy/config.xml"));
        this.testedInstance = new InformixScorecardPersistence(NAMESPACE);

        // Clear the database tables and populate them with sample data
        DBConnectionFactory connectionFactory = new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName());
        this.connection = connectionFactory.createConnection();
        clearDatabaseTables();

        insertQuestionTypes();
        insertScorecardTypes();
        insertScorecardStatuses();
        insertProjectCategories();
        insertPhases();
        insertPhaseCriteriaTypes();
        insertScorecards();
    }

    /**
     * <p>Tears down the fixture. This method is called after a test is executed.</p>
     *
     * @throws Exception if any error occurs.
     */
    protected void tearDown() throws Exception {
        this.testedInstance = null;
        ConfigHelper.releaseNamespaces();
        clearDatabaseTables();
    }

    /**
     * <p>Accuracy test. Tests the {@link InformixScorecardPersistence#getAllQuestionTypes()} method for proper
     * behavior.</p>
     *
     * <p>Verifies that the method returns the list of all existing question types correctly.</p>
     *
     * @throws Exception if an unexpected error occurs. Such an error must be interpreted as test failure.
     */
    public void testGetAllQuestionTypes() throws Exception {
        Object[][] existingTypes = getExistingQuestionTypes();
        QuestionType[] actualTypes = this.testedInstance.getAllQuestionTypes();
        Assert.assertEquals("The method does not return the exact number of question types",
                            existingTypes.length, actualTypes.length);
        for (int i = 0; i < actualTypes.length; i++) {
            QuestionType actualType = actualTypes[i];
            boolean found = false;
            for (int j = 0; j < existingTypes.length; j++) {
                if (((Long) existingTypes[j][0]).longValue() == actualType.getId()) {
                    found = true;
                    Assert.assertEquals("The question type name is not retrieved correctly",
                                        existingTypes[j][1], actualType.getName());
                }
            }
            Assert.assertTrue("The unknown question type is retrieved from DB", found);
        }
        for (int i = 0; i < existingTypes.length; i++) {
            boolean found = false;
            for (int j = 0; j < actualTypes.length; j++) {
                QuestionType actualType = actualTypes[j];
                if (((Long) existingTypes[i][0]).longValue() == actualType.getId()) {
                    found = true;
                    Assert.assertEquals("The question type name is not retrieved correctly",
                                        existingTypes[i][1], actualType.getName());
                }
            }
            Assert.assertTrue("The question type is not retrieved from DB", found);
        }
    }

    /**
     * <p>Accuracy test. Tests the {@link InformixScorecardPersistence#getAllScorecardStatuses()} method for proper
     * behavior.</p>
     *
     * <p>Verifies that the method returns the list of all existing question types correctly.</p>
     *
     * @throws Exception if an unexpected error occurs. Such an error must be interpreted as test failure.
     */
    public void testGetAllScorecardStatuses() throws Exception {
        Object[][] existingStatuses = getExistingScorecardStatuses();
        ScorecardStatus[] actualStatuses = this.testedInstance.getAllScorecardStatuses();
        Assert.assertEquals("The method does not return the exact number of scorecard statuses",
                            existingStatuses.length, actualStatuses.length);
        for (int i = 0; i < actualStatuses.length; i++) {
            ScorecardStatus actualStatus = actualStatuses[i];
            boolean found = false;
            for (int j = 0; j < existingStatuses.length; j++) {
                if (((Long) existingStatuses[j][0]).longValue() == actualStatus.getId()) {
                    found = true;
                    Assert.assertEquals("The scorecard status name is not retrieved correctly",
                                        existingStatuses[j][1], actualStatus.getName());
                }
            }
            Assert.assertTrue("The unknown scorecard status is retrieved from DB", found);
        }
        for (int i = 0; i < existingStatuses.length; i++) {
            boolean found = false;
            for (int j = 0; j < actualStatuses.length; j++) {
                ScorecardStatus actualType = actualStatuses[j];
                if (((Long) existingStatuses[i][0]).longValue() == actualType.getId()) {
                    found = true;
                    Assert.assertEquals("The scorecard status name is not retrieved correctly",
                                        existingStatuses[i][1], actualType.getName());
                }
            }
            Assert.assertTrue("The scorecard status is not retrieved from DB", found);
        }
    }

    /**
     * <p>Accuracy test. Tests the {@link InformixScorecardPersistence#getAllScorecardTypes()} method for proper
     * behavior.</p>
     *
     * <p>Verifies that the method returns the list of all existing scorecard types correctly.</p>
     *
     * @throws Exception if an unexpected error occurs. Such an error must be interpreted as test failure.
     */
    public void testGetAllScorecardTypes() throws Exception {
        Object[][] existingTypes = getExistingScorecardTypes();
        ScorecardType[] actualTypes = this.testedInstance.getAllScorecardTypes();
        Assert.assertEquals("The method does not return the exact number of scorecard types",
                            existingTypes.length, actualTypes.length);
        for (int i = 0; i < actualTypes.length; i++) {
            ScorecardType actualType = actualTypes[i];
            boolean found = false;
            for (int j = 0; j < existingTypes.length; j++) {
                if (((Long) existingTypes[j][0]).longValue() == actualType.getId()) {
                    found = true;
                    Assert.assertEquals("The scorecard type name is not retrieved correctly",
                                        existingTypes[j][1], actualType.getName());
                }
            }
            Assert.assertTrue("The unknown scorecard type is retrieved from DB", found);
        }
        for (int i = 0; i < existingTypes.length; i++) {
            boolean found = false;
            for (int j = 0; j < actualTypes.length; j++) {
                ScorecardType actualType = actualTypes[j];
                if (((Long) existingTypes[i][0]).longValue() == actualType.getId()) {
                    found = true;
                    Assert.assertEquals("The scorecard type name is not retrieved correctly",
                                        existingTypes[i][1], actualType.getName());
                }
            }
            Assert.assertTrue("The scorecard type is not retrieved from DB", found);
        }
    }

    /**
     * <p>Accuracy test. Tests the {@link InformixScorecardPersistence#getScorecard(long, boolean)} method for proper
     * behavior.</p>
     *
     * <p>Verifies that the method returns <code>null</code> if the requested scorecard is not found.</p>
     *
     * @throws Exception if an unexpected error occurs. Such an error must be interpreted as test failure.
     */
    public void testGetScorecard_long_boolean_UnknownId() throws Exception {
        assertNull("Does not return NULL in case the requested scorecard is not found",
                   this.testedInstance.getScorecard(11111, false));
        assertNull("Does not return NULL in case the requested scorecard is not found",
                   this.testedInstance.getScorecard(11111, true));
    }

    /**
     * <p>Accuracy test. Tests the {@link InformixScorecardPersistence#getScorecard(long, boolean)} method for proper
     * behavior.</p>
     *
     * <p>Verifies that the method returns the requested scorecard if it exists in database.</p>
     *
     * @throws Exception if an unexpected error occurs. Such an error must be interpreted as test failure.
     */
    public void testGetScorecard_long_boolean() throws Exception {
        Object[][] scorecards = getExistingScorecards();
        for (int i = 0; i < scorecards.length; i++) {
            Scorecard scorecard = this.testedInstance.getScorecard(((Long) scorecards[i][0]).longValue(), false);
            Assert.assertNotNull("The existing scorecard is not found", scorecard);
            assertScorecard(scorecards[i], scorecard, false);
        }
        for (int i = 0; i < scorecards.length; i++) {
            Scorecard scorecard = this.testedInstance.getScorecard(((Long) scorecards[i][0]).longValue(), true);
            Assert.assertNotNull("The existing scorecard is not found", scorecard);
            assertScorecard(scorecards[i], scorecard, true);
        }
    }

    /**
     * <p>Accuracy test. Tests the {@link InformixScorecardPersistence#getScorecards(long[], boolean)} method for proper
     * behavior.</p>
     *
     * <p>Verifies that the method returns empty array if none of the requested scorecards is found.</p>
     *
     * @throws Exception if an unexpected error occurs. Such an error must be interpreted as test failure.
     */
    public void testGetScorecards_longArray_boolean_UnknownIds() throws Exception {
        long[] ids = new long[] {11111, 11111112, 11111113};
        Assert.assertEquals("No scorecards should have been found", 0,
                            this.testedInstance.getScorecards(ids, false).length);
        Assert.assertEquals("No scorecards should have been found", 0,
                            this.testedInstance.getScorecards(ids, true).length);
    }

    /**
     * <p>Accuracy test. Tests the {@link InformixScorecardPersistence#getScorecards(long[], boolean)} method for proper
     * behavior.</p>
     *
     * <p>Verifies that the method returns the requested scorecards if they exist in DB.</p>
     *
     * @throws Exception if an unexpected error occurs. Such an error must be interpreted as test failure.
     */
    public void testGetScorecards_longArray_boolean() throws Exception {
        Object[][] scorecards = getExistingScorecards();
        long[] ids = new long[scorecards.length];
        for (int i = 0; i < scorecards.length; i++) {
            ids[i] = ((Long) scorecards[i][0]).longValue();
        }

        Scorecard[] foundScorecards = this.testedInstance.getScorecards(ids, false);
        Assert.assertEquals("The method does not return the exact number of scorecards",
                            scorecards.length, foundScorecards.length);
        for (int i = 0; i < scorecards.length; i++) {
            assertScorecard(scorecards[i], foundScorecards[i], false);
        }

        foundScorecards = this.testedInstance.getScorecards(ids, true);
        Assert.assertEquals("The method does not return the exact number of scorecards",
                            scorecards.length, foundScorecards.length);
        for (int i = 0; i < scorecards.length; i++) {
            assertScorecard(scorecards[i], foundScorecards[i], true);
        }
    }

    /**
     * <p>Accuracy test. Tests the {@link InformixScorecardPersistence#createScorecard(Scorecard, String)} method for
     * proper behavior.</p>
     *
     * <p>Verifies that the method creates the scorecard successfully.</p>
     *
     * @throws Exception if an unexpected error occurs. Such an error must be interpreted as test failure.
     */
    public void testCreateScorecard_Scorecard_String() throws Exception {
        Scorecard newScorecard = getNewScoreCard();
        this.testedInstance.createScorecard(newScorecard, EXISTING_OPERATOR);
        Assert.assertTrue("The ID is not assigned to created scorecard correctly", newScorecard.getId() > 0);

        Scorecard actualScorecard = this.testedInstance.getScorecard(newScorecard.getId(), true);
        assertScorecard(newScorecard, actualScorecard);
    }

    /**
     * <p>Accuracy test. Tests the {@link InformixScorecardPersistence#updateScorecard(Scorecard, String)} method for
     * proper behavior.</p>
     *
     * <p>Verifies that the method updates the scorecard successfully.</p>
     *
     * @throws Exception if an unexpected error occurs. Such an error must be interpreted as test failure.
     */
    public void testUpdateScorecard_Scorecard_String() throws Exception {
        Object[][] existingScorecards = getExistingScorecards();
        for (int i = 0; i < existingScorecards.length; i++) {
            Scorecard existingScorecard
                = this.testedInstance.getScorecard(((Long) existingScorecards[i][0]).longValue(), true);
            modifyScorecard(existingScorecard);
            this.testedInstance.updateScorecard(existingScorecard, "NEW OPERATOR");
            Scorecard actualScorecard = this.testedInstance.getScorecard(existingScorecard.getId(), true);
            assertScorecard(existingScorecard, actualScorecard);
        }

    }

    /**
     * <p>Removes all data from the database tables which are affected by this test case.</p>
     *
     * @throws SQLException if an SQL error occurs.
     */
    private void clearDatabaseTables() throws SQLException {
        String[] tables = new String[] {"scorecard_question", "scorecard_section", "scorecard_group", "scorecard",
            "scorecard_question_type_lu", "scorecard_type_lu", "scorecard_status_lu", "project_category_lu", "project_phase",
            "phase_criteria_type_lu"};

        PreparedStatement stmt = null;
        try {
            for (int i = 0; i < tables.length; i++) {
                stmt = this.connection.prepareStatement("DELETE FROM " + tables[i]);
                stmt.executeUpdate();
                stmt.close();
            }
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    /**
     * <p>Inserts the data for existing question types into database tables.</p>
     *
     * @throws SQLException if an SQL error occurs.
     */
    private void insertQuestionTypes() throws SQLException {
        PreparedStatement stmt = null;
        Object[][] types = getExistingQuestionTypes();
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        try {
            stmt = this.connection.prepareStatement("INSERT INTO scorecard_question_type_lu "
                                                    + "(scorecard_question_type_id, name, description, create_user,"
                                                    + "create_date,modify_user,modify_date) "
                                                    + "VALUES (?,?,?,?,?,?,?)");
            stmt.setString(3, "Description");
            stmt.setString(4, EXISTING_OPERATOR);
            stmt.setTimestamp(5, currentTime);
            stmt.setString(6, EXISTING_OPERATOR);
            stmt.setTimestamp(7, currentTime);
            for (int i = 0; i < types.length; i++) {
                stmt.setLong(1, ((Long) types[i][0]).longValue());
                stmt.setString(2, (String) types[i][1]);
                stmt.executeUpdate();
            }
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    /**
     * <p>Inserts the data for existing scorecards into database tables.</p>
     *
     * @throws SQLException if an SQL error occurs.
     */
    private void insertScorecards() throws SQLException {
        PreparedStatement stmt = null;
        PreparedStatement stmt2 = null;
        PreparedStatement stmt3 = null;
        PreparedStatement stmt4 = null;
        Object[][] scorecards = getExistingScorecards();
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        try {
            stmt = this.connection.prepareStatement("INSERT INTO scorecard "
                                                    + "(scorecard_id, scorecard_status_id, scorecard_type_id,"
                                                    + "project_category_id, name, version, min_score, max_score,"
                                                    + "create_user, create_date, modify_user, modify_date) "
                                                    + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");
            stmt2 = this.connection.prepareStatement("INSERT INTO scorecard_group "
                                                    + "(scorecard_group_id, scorecard_id,"
                                                    + "name, weight, sort,"
                                                    + "create_user, create_date, modify_user, modify_date) "
                                                    + "VALUES (?,?,?,?,?,?,?,?,?)");
            stmt3 = this.connection.prepareStatement("INSERT INTO scorecard_section "
                                                    + "(scorecard_section_id, scorecard_group_id,"
                                                    + "name, weight, sort,"
                                                    + "create_user, create_date, modify_user, modify_date) "
                                                    + "VALUES (?,?,?,?,?,?,?,?,?)");
            stmt4 = this.connection.prepareStatement("INSERT INTO scorecard_question "
                                                    + "(scorecard_question_id, scorecard_question_type_id, "
                                                    + "scorecard_section_id, description, guideline,"
                                                    + "weight, sort, upload_document, upload_document_required,"
                                                    + "create_user, create_date, modify_user, modify_date) "
                                                    + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)");

            stmt.setString(9, EXISTING_OPERATOR);
            stmt.setTimestamp(10, currentTime);
            stmt.setString(11, EXISTING_OPERATOR);
            stmt.setTimestamp(12, currentTime);

            stmt2.setString(6, EXISTING_OPERATOR);
            stmt2.setTimestamp(7, currentTime);
            stmt2.setString(8, EXISTING_OPERATOR);
            stmt2.setTimestamp(9, currentTime);

            stmt3.setString(6, EXISTING_OPERATOR);
            stmt3.setTimestamp(7, currentTime);
            stmt3.setString(8, EXISTING_OPERATOR);
            stmt3.setTimestamp(9, currentTime);

            stmt4.setString(10, EXISTING_OPERATOR);
            stmt4.setTimestamp(11, currentTime);
            stmt4.setString(12, EXISTING_OPERATOR);
            stmt4.setTimestamp(13, currentTime);

            for (int i = 0; i < scorecards.length; i++) {
                // Add scorecard
                stmt.setLong(1, ((Long) scorecards[i][0]).longValue());
                stmt.setLong(2, ((Long) scorecards[i][1]).longValue());
                stmt.setLong(3, ((Long) scorecards[i][2]).longValue());
                stmt.setLong(4, ((Long) scorecards[i][3]).longValue());
                stmt.setString(5, (String) scorecards[i][4]);
                stmt.setString(6, (String) scorecards[i][5]);
                stmt.setFloat(7, ((Float) scorecards[i][6]).floatValue());
                stmt.setFloat(8, ((Float) scorecards[i][7]).floatValue());
                stmt.executeUpdate();

                // Add scorecard group
                Object[][] groups = (Object[][]) scorecards[i][8];
                for (int j = 0; j < groups.length; j++) {
                    stmt2.setLong(1, ((Long) groups[j][0]).longValue());
                    stmt2.setLong(2, ((Long) groups[j][1]).longValue());
                    stmt2.setString(3, (String) groups[j][2]);
                    stmt2.setFloat(4, ((Float) groups[j][3]).floatValue());
                    stmt2.setLong(5, ((Long) groups[j][4]).longValue());
                    stmt2.executeUpdate();

                    // Add group sections
                    Object[][] sections = (Object[][]) groups[j][5];
                    for (int k = 0; k < sections.length; k++) {
                        stmt3.setLong(1, ((Long) sections[k][0]).longValue());
                        stmt3.setLong(2, ((Long) sections[k][1]).longValue());
                        stmt3.setString(3, (String) sections[k][2]);
                        stmt3.setFloat(4, ((Float) sections[k][3]).floatValue());
                        stmt3.setLong(5, ((Long) sections[k][4]).longValue());
                        stmt3.executeUpdate();

                        // Add section questions
                        Object[][] questions = (Object[][]) sections[k][5];
                        for (int m = 0; m < questions.length; m++) {
                            stmt4.setLong(1, ((Long) questions[m][0]).longValue());
                            stmt4.setLong(2, ((Long) questions[m][1]).longValue());
                            stmt4.setLong(3, ((Long) questions[m][2]).longValue());
                            stmt4.setString(4, (String) questions[m][3]);
                            stmt4.setString(5, (String) questions[m][4]);
                            stmt4.setFloat(6, ((Float) questions[m][5]).floatValue());
                            stmt4.setLong(7, ((Long) questions[m][6]).longValue());
                            stmt4.setBoolean(8, ((Boolean) questions[m][7]).booleanValue());
                            stmt4.setBoolean(9, ((Boolean) questions[m][8]).booleanValue());
                            stmt4.executeUpdate();
                        }
                    }
                }
            }
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (stmt2 != null) {
                stmt2.close();
            }
            if (stmt3 != null) {
                stmt3.close();
            }
            if (stmt4 != null) {
                stmt4.close();
            }
        }
    }

    /**
     * <p>Inserts the data for existing scorecard types into database tables.</p>
     *
     * @throws SQLException if an SQL error occurs.
     */
    private void insertScorecardTypes() throws SQLException {
        PreparedStatement stmt = null;
        Object[][] types = getExistingScorecardTypes();
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        try {
            stmt = this.connection.prepareStatement("INSERT INTO scorecard_type_lu "
                                                    + "(scorecard_type_id, name, description, create_user,"
                                                    + "create_date,modify_user,modify_date) "
                                                    + "VALUES (?,?,?,?,?,?,?)");
            stmt.setString(3, "Description");
            stmt.setString(4, EXISTING_OPERATOR);
            stmt.setTimestamp(5, currentTime);
            stmt.setString(6, EXISTING_OPERATOR);
            stmt.setTimestamp(7, currentTime);
            for (int i = 0; i < types.length; i++) {
                stmt.setLong(1, ((Long) types[i][0]).longValue());
                stmt.setString(2, (String) types[i][1]);
                stmt.executeUpdate();
            }
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    /**
     * <p>Inserts the data for existing scorecard statuses into database tables.</p>
     *
     * @throws SQLException if an SQL error occurs.
     */
    private void insertScorecardStatuses() throws SQLException {
        PreparedStatement stmt = null;
        Object[][] statuses = getExistingScorecardStatuses();
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        try {
            stmt = this.connection.prepareStatement("INSERT INTO scorecard_status_lu "
                                                    + "(scorecard_status_id, name, description, create_user,"
                                                    + "create_date, modify_user, modify_date) "
                                                    + "VALUES (?,?,?,?,?,?,?)");
            stmt.setString(3, "Description");
            stmt.setString(4, EXISTING_OPERATOR);
            stmt.setTimestamp(5, currentTime);
            stmt.setString(6, EXISTING_OPERATOR);
            stmt.setTimestamp(7, currentTime);
            for (int i = 0; i < statuses.length; i++) {
                stmt.setLong(1, ((Long) statuses[i][0]).longValue());
                stmt.setString(2, (String) statuses[i][1]);
                stmt.executeUpdate();
            }
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    /**
     * <p>Inserts the data for existing project categories into database tables.</p>
     *
     * @throws SQLException if an SQL error occurs.
     */
    private void insertProjectCategories() throws SQLException {
        PreparedStatement stmt = null;
        long[] categories = getExistingProjectCategories();
        try {
            stmt = this.connection.prepareStatement("INSERT INTO project_category_lu "
                                                    + "(project_category_id) "
                                                    + "VALUES (?)");
            for (int i = 0; i < categories.length; i++) {
                stmt.setLong(1, categories[i]);
                stmt.executeUpdate();
            }
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    /**
     * <p>Inserts the data for existing phases into database tables.</p>
     *
     * @throws SQLException if an SQL error occurs.
     */
    private void insertPhases() throws SQLException {
        PreparedStatement stmt = null;
        long[] phases = getExistingPhases();
        try {
            stmt = this.connection.prepareStatement("INSERT INTO project_phase "
                                                    + "(project_phase_id) "
                                                    + "VALUES (?)");
            for (int i = 0; i < phases.length; i++) {
                stmt.setLong(1, phases[i]);
                stmt.executeUpdate();
            }
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    /**
     * <p>Inserts the data for existing phase criteria types into database tables.</p>
     *
     * @throws SQLException if an SQL error occurs.
     */
    private void insertPhaseCriteriaTypes() throws SQLException {
        PreparedStatement stmt = null;
        Object[][] types = getExistingPhaseCriteriaTypes();
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        try {
            stmt = this.connection.prepareStatement("INSERT INTO phase_criteria_type_lu "
                                                    + "(phase_criteria_type_id, name, description, create_user,"
                                                    + "create_date,modify_user,modify_date) "
                                                    + "VALUES (?,?,?,?,?,?,?)");
            stmt.setString(3, "Description");
            stmt.setString(4, EXISTING_OPERATOR);
            stmt.setTimestamp(5, currentTime);
            stmt.setString(6, EXISTING_OPERATOR);
            stmt.setTimestamp(7, currentTime);
            for (int i = 0; i < types.length; i++) {
                stmt.setLong(1, ((Long) types[i][0]).longValue());
                stmt.setString(2, (String) types[i][1]);
                stmt.executeUpdate();
            }
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    /**
     * <p>Gets the details for existing phase criteria types.</p>
     *
     * @return an <code>Object</code> array providing details for existing phase criteria types.
     */
    private Object[][] getExistingPhaseCriteriaTypes() {
        Object[][] types = new Object[5][2];
        for (int i = 0; i < types.length; i++) {
            types[i][0] = new Long(i + 1);
            types[i][1] = "PhaseCriteriaType" + i;
        }
        return types;
    }

    /**
     * <p>Gets the details for existing question types.</p>
     *
     * @return an <code>Object</code> array providing details for existing question types.
     */
    private Object[][] getExistingQuestionTypes() {
        Object[][] types = new Object[5][2];
        for (int i = 0; i < types.length; i++) {
            types[i][0] = new Long(i + 1);
            types[i][1] = "QuestionType" + i;
        }
        return types;
    }

    /**
     * <p>Gets the details for existing scorecard types.</p>
     *
     * @return an <code>Object</code> array providing details for existing scorecard types.
     */
    private Object[][] getExistingScorecardTypes() {
        Object[][] types = new Object[7][2];
        for (int i = 0; i < types.length; i++) {
            types[i][0] = new Long(i + 1);
            types[i][1] = "ScorecardType" + i;
        }
        return types;
    }

    /**
     * <p>Gets the details for existing scorecard statuses.</p>
     *
     * @return an <code>Object</code> array providing details for existing scorecard statuses.
     */
    private Object[][] getExistingScorecardStatuses() {
        Object[][] types = new Object[10][2];
        for (int i = 0; i < types.length; i++) {
            types[i][0] = new Long(i + 1);
            types[i][1] = "ScorecardStatus" + i;
        }
        return types;
    }

    /**
     * <p>Gets the IDs for existing project categories.</p>
     *
     * @return a <code>long</code> array providing the IDs for existing project categories.
     */
    private long[] getExistingProjectCategories() {
        long[] categories = new long[6];

        for (int i = 0; i < categories.length; i++) {
            categories[i] = i + 1;
        }
        return categories;
    }

    /**
     * <p>Gets the IDs for existing phase.</p>
     *
     * @return a <code>long</code> array providing the IDs for existing phases.
     */
    private long[] getExistingPhases() {
        long[] phases = new long[8];

        for (int i = 0; i < phases.length; i++) {
            phases[i] = i + 1;
        }
        return phases;
    }

    /**
     * <p>Gets the details for existing scorecards.</p>
     *
     * @return an <code>Object</code> array providing details for existing scorecards.
     */
    private Object[][] getExistingScorecards() {
        Object[][] scorecards = new Object[10][9];

        Object[][] statuses = getExistingScorecardStatuses();
        Object[][] types = getExistingScorecardTypes();
        Object[][] questionTypes = getExistingQuestionTypes();
        long[] categories = getExistingProjectCategories();

        long groupId = 1;
        long sectionId = 1;
        long questionId = 1;

        for (int i = 0; i < scorecards.length; i++) {
            scorecards[i][0] = new Long(i + 1);
            scorecards[i][1] = statuses[i % statuses.length][0];
            scorecards[i][2] = types[i % types.length][0];
            scorecards[i][3] = new Long(categories[i % categories.length]);
            scorecards[i][4] = "Scorecard" + i;
            scorecards[i][5] = "Ver" + i;
            scorecards[i][6] = new Float(10 + i) ;
            scorecards[i][7] = new Float(100 + i);

            Object[][] groups = new Object[i][6];
            for (int j = 0; j < i; j++) {
                groups[j][0] = new Long(groupId);
                groups[j][1] = scorecards[i][0];
                groups[j][2] = "Group" + groupId;
                groups[j][3] = new Float(16.66666667);
                groups[j][4] = new Long(groupId++);

                Object[][] sections = new Object[j][6];
                for (int k = 0; k < j; k++) {
                    sections[k][0] = new Long(sectionId);
                    sections[k][1] = groups[j][0];
                    sections[k][2] = "Section" + sectionId;
                    sections[k][3] = new Float(16.66666667);
                    sections[k][4] = new Long(sectionId++);

                    Object[][] questions = new Object[k][9];
                    for (int m = 0; m < k; m++) {
                        questions[m][0] = new Long(questionId);
                        questions[m][1] = questionTypes[m % questionTypes.length][0];
                        questions[m][2] = sections[k][0];
                        questions[m][3] = "QuestionDesc" + questionId;
                        questions[m][4] = "QuestionGuideLine" + questionId;
                        questions[m][5] = new Float(11.11111111);
                        questions[m][6] = new Long(questionId);
                        questions[m][7] = new Boolean(questionId % 2 == 0);
                        questions[m][8] = new Boolean(questionId++ % 2 == 0);
                    }
                    sections[k][5] = questions;
                }
                groups[j][5] = sections;

            }
            scorecards[i][8] = groups;
        }

        return scorecards;
    }

    /**
     * <p>Verifies that specified scorecards are equal.</p>
     *
     * @param expected an <code>Object</code> array providing the expected details for scorecard.
     * @param actual a <code>Scorecard</code> providing the actual details for scorecard.
     */
    private void assertScorecard(Object[] expected, Scorecard actual, boolean full) {
        Assert.assertEquals("Wrong scorecard ID", ((Long) expected[0]).longValue(), actual.getId());
        Assert.assertEquals("Wrong scorecard status ID",
                            ((Long) expected[1]).longValue(), actual.getScorecardStatus().getId());
        Assert.assertEquals("Wrong scorecard type ID",
                            ((Long) expected[2]).longValue(), actual.getScorecardType().getId());
        Assert.assertEquals("Wrong scorecard project category ID",
                            ((Long) expected[3]).longValue(), actual.getCategory());
        Assert.assertEquals("Wrong scorecard name", (String) expected[4], actual.getName());
        Assert.assertEquals("Wrong scorecard version", (String) expected[5], actual.getVersion());
        Assert.assertEquals("Wrong scorecard min score", ((Float) expected[6]).floatValue(), actual.getMinScore(),
                            0.001);
        Assert.assertEquals("Wrong scorecard max score", ((Float) expected[7]).floatValue(), actual.getMaxScore(),
                            0.001);
        Assert.assertEquals("Wrong scorecard creation user", EXISTING_OPERATOR, actual.getCreationUser());
        Assert.assertEquals("Wrong scorecard modification user", EXISTING_OPERATOR, actual.getModificationUser());

        if (full) {
            Object[][] expectedGroups = (Object[][]) expected[8];
            Group[] actualGroups = actual.getAllGroups();
            Assert.assertEquals("The method does not return the exact number of groups",
                                expectedGroups.length, actualGroups.length);

            for (int i = 0; i < expectedGroups.length; i++) {
                assertGroup(expectedGroups[i], actualGroups[i]);
            }
        }
    }

    /**
     * <p>Verifies that specified scorecards are equal.</p>
     *
     * @param expected a <code>Scorecard</code> providing the expected details for scorecard.
     * @param actual a <code>Scorecard</code> providing the actual details for scorecard.
     */
    private void assertScorecard(Scorecard expected, Scorecard actual) {
        Assert.assertEquals("Wrong scorecard ID", expected.getId(), actual.getId());
        Assert.assertEquals("Wrong scorecard status ID",
                            expected.getScorecardStatus().getId(), actual.getScorecardStatus().getId());
        Assert.assertEquals("Wrong scorecard type ID",
                            expected.getScorecardType().getId(), actual.getScorecardType().getId());
        Assert.assertEquals("Wrong scorecard project category ID",
                            expected.getCategory(), actual.getCategory());
        Assert.assertEquals("Wrong scorecard name", expected.getName(), actual.getName());
        Assert.assertEquals("Wrong scorecard version", expected.getVersion(), actual.getVersion());
        Assert.assertEquals("Wrong scorecard min score", expected.getMinScore(), actual.getMinScore(),
                            0.001);
        Assert.assertEquals("Wrong scorecard max score", expected.getMaxScore(), actual.getMaxScore(),
                            0.001);
        Assert.assertEquals("Wrong scorecard creation user", expected.getCreationUser(), actual.getCreationUser());
        Assert.assertEquals("Wrong scorecard modification user", expected.getModificationUser(),
                            actual.getModificationUser());

        Group[] expectedGroups = expected.getAllGroups();
        Group[] actualGroups = actual.getAllGroups();
        Assert.assertEquals("The method does not return the exact number of groups",
                            expectedGroups.length, actualGroups.length);

        for (int i = 0; i < expectedGroups.length; i++) {
            assertGroup(expectedGroups[i], actualGroups[i]);
        }
    }

    /**
     * <p>Verifies that specified scorecard groups are equal.</p>
     *
     * @param expected a <code>Group</code> providing the expected details for group.
     * @param actual a <code>Group</code> providing the actual details for group.
     */
    private void assertGroup(Group expected, Group actual) {
        Assert.assertEquals("Wrong group ID", expected.getId(), actual.getId());
        Assert.assertEquals("Wrong group name", expected.getName(), actual.getName());
        Assert.assertEquals("Wrong group weight", expected.getWeight(), actual.getWeight(), 0.001);

        Section[] expectedSections = expected.getAllSections();
        Section[] actualSections = actual.getAllSections();
        Assert.assertEquals("The method does not return the exact number of sections",
                            expectedSections.length, actualSections.length);

        for (int i = 0; i < expectedSections.length; i++) {
            assertSection(expectedSections[i], actualSections[i]);
        }
    }

    /**
     * <p>Verifies that specified scorecard sections are equal.</p>
     *
     * @param expected a <code>Section</code> providing the expected details for section.
     * @param actual a <code>Section</code> providing the actual details for section.
     */
    private void assertSection(Section expected, Section actual) {
        Assert.assertEquals("Wrong section ID", expected.getId(), actual.getId());
        Assert.assertEquals("Wrong section name", expected.getName(), actual.getName());
        Assert.assertEquals("Wrong section weight", expected.getWeight(), actual.getWeight(), 0.001);

        Question[] expectedQuestions = expected.getAllQuestions();
        Question[] actualQuestions = actual.getAllQuestions();
        Assert.assertEquals("The method does not return the exact number of questions",
                            expectedQuestions.length, actualQuestions.length);

        for (int i = 0; i < expectedQuestions.length; i++) {
            assertQuestion(expectedQuestions[i], actualQuestions[i]);
        }
    }

    /**
     * <p>Verifies that specified scorecard sections are equal.</p>
     *
     * @param expected a <code>Section</code> providing the expected details for section.
     * @param actual a <code>Section</code> providing the actual details for section.
     */
    private void assertQuestion(Question expected, Question actual) {
        Assert.assertEquals("Wrong question ID", expected.getId(), actual.getId());
        Assert.assertEquals("Wrong question description", expected.getDescription(), actual.getDescription());
        Assert.assertEquals("Wrong question guideline", expected.getGuideline(), actual.getGuideline());
        Assert.assertEquals("Wrong question weight", expected.getWeight(), actual.getWeight(), 0.001);
        Assert.assertEquals("Wrong question type",
                            expected.getQuestionType().getId(), actual.getQuestionType().getId());
        Assert.assertEquals("Wrong question type",
                            expected.getQuestionType().getName(), actual.getQuestionType().getName());
        Assert.assertEquals("Wrong upload flag", expected.isUploadDocument(), actual.isUploadDocument());
        Assert.assertEquals("Wrong upload required flag", expected.isUploadRequired(), actual.isUploadRequired());
    }

    /**
     * <p>Gets the new scorecard which does not exist in persistent data store.</p>
     *
     * @return a <code>Scorecard</code> providing the new scorecard details.
     * @throws Exception if an unexpected error occurs.
     */
    private Scorecard getNewScoreCard() throws Exception {
        Object[][] existingScorecards = getExistingScorecards();
        Scorecard scorecard = this.testedInstance.getScorecard(((Long) existingScorecards[0][0]).longValue(), true);
        deleteScorecard(scorecard);
        return scorecard;
    }

    /**
     * <p>Deletes the specified scorecard from the persistent data store.</p>
     *
     * @param scorecard a <code>Scorecard</code> to be deleted.
     * @throws Exception if an unexpected error occurs.
     */
    private void deleteScorecard(Scorecard scorecard) throws Exception {
        PreparedStatement stmt = null;
        try {
            stmt = this.connection.prepareStatement("DELETE FROM scorecard_question WHERE EXISTS " +
                                                    "(SELECT s.scorecard_section_id FROM scorecard_section s "
                                                    + "WHERE s.scorecard_section_id = scorecard_question.scorecard_section_id "
                                                    + "AND EXISTS "
                                                    + "(SELECT g.scorecard_group_id FROM scorecard_group g "
                                                    +  "WHERE g.scorecard_group_id = s.scorecard_group_id "
                                                    +  "AND g.scorecard_id = ?)) ");
            stmt.setLong(1, scorecard.getId());
            stmt.executeUpdate();
            stmt.close();

            stmt = this.connection.prepareStatement("DELETE FROM scorecard_section "
                                                    + "WHERE EXISTS "
                                                    + "(SELECT g.scorecard_group_id FROM scorecard_group g "
                                                    +  "WHERE g.scorecard_group_id = scorecard_section.scorecard_group_id "
                                                    +  "AND g.scorecard_id = ?) ");
            stmt.setLong(1, scorecard.getId());
            stmt.executeUpdate();
            stmt.close();

            stmt = this.connection.prepareStatement("DELETE FROM scorecard_group WHERE scorecard_id = ?");
            stmt.setLong(1, scorecard.getId());
            stmt.executeUpdate();
            stmt.close();
            stmt = this.connection.prepareStatement("DELETE FROM scorecard WHERE scorecard_id = ?");
            stmt.setLong(1, scorecard.getId());
            stmt.executeUpdate();
            stmt.close();
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    /**
     * <p>Alters the specified scorecard with random data.</p>
     *
     * @param existingScorecard a <code>Scorecard</code> to be modified.
     */
    private void modifyScorecard(Scorecard existingScorecard) {
        existingScorecard.setName("New Name " + existingScorecard.getId());
        existingScorecard.setVersion("NewV" + existingScorecard.getId());
        existingScorecard.setMaxScore(existingScorecard.getMaxScore() + 1000);
        existingScorecard.setMinScore(existingScorecard.getMinScore() + 100);
        existingScorecard.setModificationUser("NEW OPERATOR");
        Group[] groups = existingScorecard.getAllGroups();
        for (int i = 0; i < groups.length - 1; i++) {
            existingScorecard.removeGroup(groups[i]);
        }
        groups = existingScorecard.getAllGroups();
        for (int i = 0; i < groups.length; i++) {
            modifyGroup(groups[i]);
        }
    }

    /**
     * <p>Alters the specified group with random data.</p>
     *
     * @param existingGroup a <code>Group</code> to be modified.
     */
    private void modifyGroup(Group existingGroup) {
        existingGroup.setName("New Name " + existingGroup.getId());
        Section[] sections = existingGroup.getAllSections();
        for (int i = 0; i < sections.length - 1; i++) {
            existingGroup.removeSection(sections[i]);
        }
        sections = existingGroup.getAllSections();
        for (int i = 0; i < sections.length; i++) {
            modifySection(sections[i]);
        }
    }

    /**
     * <p>Alters the specified section with random data.</p>
     *
     * @param existingSection a <code>Section</code> to be modified.
     */
    private void modifySection(Section existingSection) {
        existingSection.setName("New Name " + existingSection.getId());
        Question[] questions = existingSection.getAllQuestions();
        for (int i = 0; i < questions.length - 1; i++) {
            existingSection.removeQuestion(questions[i]);
        }
        questions = existingSection.getAllQuestions();
        for (int i = 0; i < questions.length; i++) {
            modifyQuestion(questions[i]);
        }
    }

    /**
     * <p>Alters the specified question with random data.</p>
     *
     * @param existingQuestion a <code>Question</code> to be modified.
     */
    private void modifyQuestion(Question existingQuestion) {
        existingQuestion.setDescription("New Desc " + existingQuestion.getId());
        existingQuestion.setGuideline("New Guideline " + existingQuestion.getId());
        existingQuestion.setUploadRequired(!existingQuestion.isUploadRequired());
        existingQuestion.setUploadDocument(existingQuestion.isUploadRequired());
    }

    /**
     * <p>Verifies that specified scorecard groups are equal.</p>
     *
     * @param expected a <code>Group</code> providing the expected details for group.
     * @param actual a <code>Group</code> providing the actual details for group.
     */
    private void assertGroup(Object[] expected, Group actual) {
        Assert.assertEquals("Wrong group ID", ((Long) expected[0]).longValue(), actual.getId());
        Assert.assertEquals("Wrong group name", (String) expected[2], actual.getName());
        Assert.assertEquals("Wrong group weight", ((Float) expected[3]).floatValue(), actual.getWeight(), 0.001);

        Object[][] expectedSections = (Object[][]) expected[5];
        Section[] actualSections = actual.getAllSections();
        Assert.assertEquals("The method does not return the exact number of sections",
                            expectedSections.length, actualSections.length);

        for (int i = 0; i < expectedSections.length; i++) {
            assertSection(expectedSections[i], actualSections[i]);
        }
    }

    /**
     * <p>Verifies that specified scorecard sections are equal.</p>
     *
     * @param expected a <code>Section</code> providing the expected details for section.
     * @param actual a <code>Section</code> providing the actual details for section.
     */
    private void assertSection(Object[] expected, Section actual) {
        Assert.assertEquals("Wrong section ID", ((Long) expected[0]).longValue(), actual.getId());
        Assert.assertEquals("Wrong section name", (String) expected[2], actual.getName());
        Assert.assertEquals("Wrong section weight", ((Float) expected[3]).floatValue(), actual.getWeight(), 0.001);

        Object[][] expectedQuestions = (Object[][]) expected[5];
        Question[] actualQuestions = actual.getAllQuestions();
        Assert.assertEquals("The method does not return the exact number of questions",
                            expectedQuestions.length, actualQuestions.length);

        for (int i = 0; i < expectedQuestions.length; i++) {
            assertQuestion(expectedQuestions[i], actualQuestions[i]);
        }
    }

    /**
     * <p>Verifies that specified scorecard sections are equal.</p>
     *
     * @param expected a <code>Section</code> providing the expected details for section.
     * @param actual a <code>Section</code> providing the actual details for section.
     */
    private void assertQuestion(Object[] expected, Question actual) {
        Assert.assertEquals("Wrong question ID", ((Long) expected[0]).longValue(), actual.getId());
        Assert.assertEquals("Wrong question description", (String) expected[3], actual.getDescription());
        Assert.assertEquals("Wrong question guideline", (String) expected[4], actual.getGuideline());
        Assert.assertEquals("Wrong question weight", ((Float) expected[5]).floatValue(), actual.getWeight(), 0.001);
        Assert.assertEquals("Wrong question type",
                            ((Long) expected[1]).longValue(), actual.getQuestionType().getId());
        Assert.assertEquals("Wrong upload flag", ((Boolean) expected[7]).booleanValue(), actual.isUploadDocument());
        Assert.assertEquals("Wrong upload flag", ((Boolean) expected[8]).booleanValue(), actual.isUploadRequired());
    }
}
