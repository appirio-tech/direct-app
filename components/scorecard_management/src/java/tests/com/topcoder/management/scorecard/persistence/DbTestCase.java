/**
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved
 */
package com.topcoder.management.scorecard.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Iterator;

import junit.framework.TestCase;

import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.management.scorecard.data.Group;
import com.topcoder.management.scorecard.data.Question;
import com.topcoder.management.scorecard.data.QuestionType;
import com.topcoder.management.scorecard.data.Scorecard;
import com.topcoder.management.scorecard.data.ScorecardStatus;
import com.topcoder.management.scorecard.data.ScorecardType;
import com.topcoder.management.scorecard.data.Section;
import com.topcoder.util.config.ConfigManager;

/**
 * Base class for all database tests. It contains common test helper methods.
 *
 * @author kr00tki
 * @version 1.0
 */
public class DbTestCase extends TestCase {
    /**
     * The configuration namespace.
     */
    public static final String NAMESPACE = InformixScorecardPersistenceTest.class.getName();

    /**
     * Configuration file.
     */
    public static final String CONF_FILE = "persistence_conf.xml";

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
     * The DBManager used to manipulate on database.
     */
    protected DbManager testDBManager = null;

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
        testDBManager.loadDataSet("test_files/dataset.xml");
    }

    /**
     * Clears after test.
     *
     * @throws Exception to JUnit.
     */
    protected void tearDown() throws Exception {
        testDBManager.clearTables();
        testDBManager.release();

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
     * @return query resul
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
            DBUtils.close(rs);
            DBUtils.close(statement);
        }
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
     * Assert the database content comparing it to the given Scorecard instance.
     *
     * @param scorecard the scorecard to be checked.
     * @throws Exception to JUnit.
     */
    protected void assertScorecard(Scorecard scorecard) throws Exception {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = getConnection().prepareStatement("SELECT * FROM scorecard WHERE scorecard_id = ?");
            pstmt.setLong(1, scorecard.getId());
            rs = pstmt.executeQuery();

            assertTrue("No scorecard with id=" + scorecard.getId(), rs.next());
            assertEquals("Incorrect status id", scorecard.getScorecardStatus().getId(), rs
                    .getLong("scorecard_status_id"));
            assertEquals("Incorrect type id", scorecard.getScorecardType().getId(), rs
                    .getLong("scorecard_type_id"));
            assertEquals("Incorrect category", scorecard.getCategory(), rs.getLong("project_category_id"));
            assertEquals("Incorrect name", scorecard.getName(), rs.getString("name"));
            assertEquals("Incorrect version", scorecard.getVersion(), rs.getString("version"));
            assertEquals("Incorrect min_score", scorecard.getMinScore(), rs.getFloat("min_score"), 0.0);
            assertEquals("Incorrect max_score", scorecard.getMaxScore(), rs.getFloat("max_score"), 0.0);

            assertEquals("Incorrect create_user", scorecard.getCreationUser(), rs.getString("create_user"));
            assertEquals("Incorrect modify_user", scorecard.getModificationUser(), rs.getString("modify_user"));

            assertEquals("Incorrect modify_date", scorecard.getModificationTimestamp(), rs
                    .getTimestamp("modify_date"));
            assertEquals("Incorrect creation date", scorecard.getCreationTimestamp(), rs
                    .getTimestamp("create_date"));

        } finally {
            DBUtils.close(rs);
            DBUtils.close(pstmt);
        }

        Group[] groups = scorecard.getAllGroups();
        for (int i = 0; i < groups.length; i++) {
            assertGroup(groups[i], scorecard.getId(), i);
        }
    }

    /**
     * Compares the given group with database content.
     *
     * @param group the group to check.
     * @param parentId the paren id.
     * @param sort the sort value.
     * @throws Exception to Junit.
     */
    protected void assertGroup(Group group, long parentId, int sort) throws Exception {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = getConnection().prepareStatement("SELECT * FROM scorecard_group WHERE scorecard_group_id = ?");
            pstmt.setLong(1, group.getId());
            rs = pstmt.executeQuery();

            assertTrue("No group with id=" + group.getId(), rs.next());
            assertEquals("Incorrect scorecard id", parentId, rs.getLong("scorecard_id"));
            assertEquals("Incorrect name", group.getName(), rs.getString("name"));
            assertEquals("Incorrect weight", group.getWeight(), rs.getFloat("weight"), 0.0);
            assertEquals("Incorrect sort value.", sort, rs.getInt("sort"));
        } finally {
            DBUtils.close(rs);
            DBUtils.close(pstmt);
        }

        Section[] sections = group.getAllSections();
        for (int i = 0; i < sections.length; i++) {
            assertSection(sections[i], group.getId(), i);
        }

    }

    /**
     * Checks the given section in the database.
     *
     * @param section the section to be check.
     * @param parentId the parent id of the section.
     * @param sort the order value.
     * @throws Exception to JUnit.
     */
    protected void assertSection(Section section, long parentId, int sort) throws Exception {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = getConnection().prepareStatement(
                    "SELECT * FROM scorecard_section WHERE scorecard_section_id = ?");
            pstmt.setLong(1, section.getId());
            rs = pstmt.executeQuery();

            assertTrue("No section with id=" + section.getId(), rs.next());
            assertEquals("Incorrect scorecard id", parentId, rs.getLong("scorecard_group_id"));
            assertEquals("Incorrect name", section.getName(), rs.getString("name"));
            assertEquals("Incorrect weight", section.getWeight(), rs.getFloat("weight"), 0.0);
            assertEquals("Incorrect sort value.", sort, rs.getInt("sort"));
        } finally {
            DBUtils.close(rs);
            DBUtils.close(pstmt);
        }

        Question[] questions = section.getAllQuestions();
        for (int i = 0; i < questions.length; i++) {
            assertQuestion(questions[i], section.getId(), i);
        }

    }

    /**
     * Checks the given question in the database.
     *
     * @param question the question to be check.
     * @param parentId the parent value for question.
     * @param sort the order value.
     * @throws Exception to JUnit.
     */
    protected void assertQuestion(Question question, long parentId, int sort) throws Exception {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = getConnection().prepareStatement(
                    "SELECT * FROM scorecard_question WHERE scorecard_question_id = ?");
            pstmt.setLong(1, question.getId());
            rs = pstmt.executeQuery();

            assertTrue("No question with id=" + question.getId(), rs.next());
            assertEquals("Incorrect scorecard section id", parentId, rs.getLong("scorecard_section_id"));
            assertEquals("Incorrect question type id", question.getQuestionType().getId(), rs
                    .getLong("scorecard_question_type_id"));
            assertEquals("Incorrect description", question.getDescription(), rs.getString("description"));
            assertEquals("Incorrect guideline", question.getGuideline(), rs.getString("guideline"));
            assertEquals("Incorrect weight", question.getWeight(), rs.getFloat("weight"), 0.0);
            assertEquals("Incorrect sort value.", sort, rs.getInt("sort"));

            assertEquals("Incorrect upload value.", question.isUploadDocument(), rs.getBoolean("upload_document"));
            assertEquals("Incorrect upload required value.", question.isUploadRequired(), rs
                    .getBoolean("upload_document_required"));

        } finally {
            DBUtils.close(rs);
            DBUtils.close(pstmt);
        }
    }

    /**
     * Creates the question instance for given id.
     *
     * @param id the id of the question.
     * @return the Question instance.
     */
    protected static Question createQuestion(int id) {
        Question question = new Question();
        if (id != 0) {
            question.setId(id);
        }
        question.setDescription("description_" + id);
        question.setGuideline("guideline_" + id);
        question.setQuestionType(new QuestionType(1, "test"));
        question.setUploadDocument(id % 2 == 0 ? true : false);
        question.setUploadRequired(id % 2 == 0 ? false : true);
        question.setWeight(id % 100);

        return question;
    }

    /**
     * Creates the Section instance with given id.
     *
     * @param id the id of the section.
     * @param count the number of questions in section.
     *
     * @return the Section instance.
     */
    protected static Section createSection(int id, int count) {
        Section section = new Section();
        if (id != 0) {
            section.setId(id);
        }
        section.setName("section_" + id);
        section.setWeight(id % 100);

        for (int i = 0; i < count; i++) {
            section.addQuestion(createQuestion(id * count + i));
        }

        return section;
    }

    /**
     * Creates the group with given id.
     *
     * @param id the id of the group.
     * @param count the number of sections in group.
     *
     * @return the Group instance.
     */
    protected static Group createGroup(int id, int count) {
        Group group = new Group();
        if (id != 0) {
            group.setId(id);
        }
        group.setName("group_" + id);
        group.setWeight(id % 100);

        for (int i = 0; i < count; i++) {
            group.addSection(createSection(id * count + i, count));
        }

        return group;
    }

    /**
     * Creates the scorecard with given id.
     *
     * @param id the id of the scorecard.
     * @param count the number of groups in question.
     * @return the Scorecard instance.
     */
    protected static Scorecard createScorecard(int id, int count) {
        Scorecard card = new Scorecard();
        if (id != 0) {
            card.setId(id);
        }
        card.setCategory(1);
        card.setMaxScore(100 + count);
        card.setMinScore(0 + count);
        card.setName("scorecard_" + id);
        card.setScorecardStatus(new ScorecardStatus(1, "status_1"));
        card.setScorecardType(new ScorecardType(1, "test"));
        card.setVersion("" + id);

        for (int i = 0; i < count; i++) {
            card.addGroup(createGroup(id * count + i, count));
        }

        return card;
    }

    /**
     * Checks if both Scorecards are equal.
     *
     * @param expected the expected Scorecard.
     * @param actual the actual Scorecard.
     * @param deepCheck indicates if the child groups should be also check.
     *
     * @throws Exception to JUnit.
     */
    protected void assertScorecard(Scorecard expected, Scorecard actual, boolean deepCheck) throws Exception {
        assertEquals("incorrect id", expected.getId(), actual.getId());
        assertEquals("Incorrect status id", expected.getScorecardStatus().getId(), actual.getScorecardStatus()
                .getId());
        assertEquals("Incorrect type id", expected.getScorecardType().getId(), actual.getScorecardType().getId());
        assertEquals("Incorrect category", expected.getCategory(), actual.getCategory());
        assertEquals("Incorrect name", expected.getName(), actual.getName());
        assertEquals("Incorrect version", expected.getVersion(), actual.getVersion());
        assertEquals("Incorrect min score", expected.getMinScore(), actual.getMinScore(), 0.0);
        assertEquals("Incorrect max score", expected.getMaxScore(), actual.getMaxScore(), 0.0);

        assertEquals("Incorrect create user", expected.getCreationUser(), actual.getCreationUser());
        assertEquals("Incorrect modify user", expected.getModificationUser(), actual.getModificationUser());

        assertEquals("Incorrect modify date", expected.getModificationTimestamp(), actual
                .getModificationTimestamp());
        assertEquals("Incorrect creation date", expected.getCreationTimestamp(), actual.getCreationTimestamp());

        if (deepCheck) {
            Group[] aGroups = actual.getAllGroups();
            Group[] eGroups = expected.getAllGroups();
            assertEquals("Incorrect groups counts", eGroups.length, aGroups.length);
            for (int i = 0; i < aGroups.length; i++) {
                assertGroup(aGroups[i], eGroups[i]);
            }
        }
    }

    /**
     * Checks if both Groups are equal.
     *
     * @param expected the expected Group.
     * @param actual the actual Group.
     *
     */
    protected void assertGroup(Group expected, Group actual) {
        assertEquals("Incorrect id", expected.getId(), actual.getId());
        assertEquals("Incorrect name", expected.getName(), actual.getName());
        assertEquals("Incorrect weight", expected.getWeight(), actual.getWeight(), 0.0);

        Section[] eSections = expected.getAllSections();
        Section[] aSections = actual.getAllSections();
        assertEquals("Incorrect sections counts", eSections.length, aSections.length);
        for (int i = 0; i < aSections.length; i++) {
            assertSection(eSections[i], aSections[i]);
        }

    }

    /**
     * Checks if both Sections are equal.
     *
     * @param expected the expected Section.
     * @param actual the actual Section.
     */
    protected void assertSection(Section expected, Section actual) {
        assertEquals("Incorrect id", expected.getId(), actual.getId());
        assertEquals("Incorrect name", expected.getName(), actual.getName());
        assertEquals("Incorrect weight", expected.getWeight(), actual.getWeight(), 0.0);

        Question[] expQuestions = expected.getAllQuestions();
        Question[] actQuestions = actual.getAllQuestions();
        assertEquals("Incorrect questions counts", expQuestions.length, actQuestions.length);
        for (int i = 0; i < expQuestions.length; i++) {
            assertQuestion(expQuestions[i], actQuestions[i]);
        }

    }

    /**
     * Checks if both Questions are equal.
     *
     * @param expected the expected Questions.
     * @param actual the actual Questions.
     *
     */
    protected void assertQuestion(Question expected, Question actual) {
        assertEquals("Incorrect id", expected.getId(), actual.getId());
        assertEquals("Incorrect question type id", expected.getQuestionType().getId(), actual.getQuestionType()
                .getId());
        assertEquals("Incorrect description", expected.getDescription(), actual.getDescription());
        assertEquals("Incorrect guideline", expected.getGuideline(), actual.getGuideline());
        assertEquals("Incorrect weight", expected.getWeight(), actual.getWeight(), 0.0);

        assertEquals("Incorrect upload value.", expected.isUploadDocument(), actual.isUploadDocument());
        assertEquals("Incorrect upload required value.", expected.isUploadRequired(), actual.isUploadRequired());
    }

    /**
     * Checks if the Group exists in the database.
     *
     * @param message the assertion message.
     * @param group the group to be check.
     * @throws Exception to JUnit.
     */
    protected void assertExists(String message, Group group) throws Exception {
        assertByQuery(message, "SELECT 1 FROM scorecard_group WHERE scorecard_group_id = " + group.getId(), true);
    }

    /**
     * Checks if the Group not exists in the database.
     *
     * @param message the assertion message.
     * @param group the group to be check.
     * @throws Exception to JUnit.
     */
    protected void assertNotExists(String message, Group group) throws Exception {
        assertByQuery(message, "SELECT 1 FROM scorecard_group WHERE scorecard_group_id = " + group.getId(), false);
    }

    /**
     * Checks if the Section exists in the database.
     *
     * @param message the assertion message.
     * @param section the section to be check.
     * @throws Exception to JUnit.
     */
    protected void assertExists(String message, Section section) throws Exception {
        assertByQuery(message, "SELECT 1 FROM scorecard_section WHERE scorecard_section_id = " + section.getId(),
                true);
    }

    /**
     * Checks if the Section not exists in the database.
     *
     * @param message the assertion message.
     * @param section the section to be check.
     * @throws Exception to JUnit.
     */
    protected void assertNotExists(String message, Section section) throws Exception {
        assertByQuery(message, "SELECT 1 FROM scorecard_section WHERE scorecard_section_id = " + section.getId(),
                false);
    }

    /**
     * Checks if the Question exists in the database.
     *
     * @param message the assertion message.
     * @param question the question to be check.
     * @throws Exception to JUnit.
     */
    protected void assertExists(String message, Question question) throws Exception {
        assertByQuery(message, "SELECT 1 FROM scorecard_question WHERE scorecard_question_id = "
                + question.getId(), true);
    }

    /**
     * Checks if the Question not exists in the database.
     *
     * @param message the assertion message.
     * @param question the question to be check.
     * @throws Exception to JUnit.
     */
    protected void assertNotExists(String message, Question question) throws Exception {
        assertByQuery(message, "SELECT 1 FROM scorecard_question WHERE scorecard_question_id = "
                + question.getId(), false);
    }

    /**
     * Sets the given scorecard in use.
     *
     * @param scorecard the scorecard.
     * @throws Exception to JUnit.
     */
    protected void setInUse(Scorecard scorecard) throws Exception {
        PreparedStatement pstmt = null;
        try {
            pstmt = getConnection().prepareStatement(
                    "INSERT INTO phase_criteria(project_phase_id, "
                            + "phase_criteria_type_id, parameter) VALUES (1, 1, ?)");

            pstmt.setLong(1, scorecard.getId());

            pstmt.execute();
        } finally {
            DBUtils.close(pstmt);
        }
    }
}
