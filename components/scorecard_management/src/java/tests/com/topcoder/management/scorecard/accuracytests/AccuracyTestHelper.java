/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.scorecard.accuracytests;

import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

import com.topcoder.management.scorecard.data.Group;
import com.topcoder.management.scorecard.data.Question;
import com.topcoder.management.scorecard.data.Scorecard;
import com.topcoder.management.scorecard.data.ScorecardStatus;
import com.topcoder.management.scorecard.data.ScorecardType;
import com.topcoder.management.scorecard.data.Section;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;

import java.util.Calendar;


/**
 * The helper class for accuracy test cases.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AccuracyTestHelper {
    /** The value of the description used for test. */
    private static final String VALID_DESCRIPTION = "description";

    /** The namespace of the database used for test. */
    private static final String DB_NAMESPACE =
        "com.topcoder.management.scorecard.accuracytests.AccuracyTestScorecardPersistence";

    /** The SQL statement to insert a record into scorecard table. */
    private static final String INSERT_SCORECARD_SQL = "INSERT INTO scorecard "
        + "(scorecard_id, scorecard_status_id, scorecard_type_id, project_category_id, name, "
        + "version, min_score, max_score, create_user, create_date, modify_user, modify_date) "
        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";

    /** The SQL statement to insert a record into project_category_lu table. */
    private static final String INSERT_PROJECT_SQL = "INSERT INTO project_category_lu "
        + "(project_category_id) VALUES (?) ";

    /** The SQL statement to insert a record into project_phase table. */
    private static final String INSERT_PROJECT_PHASE_SQL = "INSERT INTO project_phase "
        + "(project_phase_id) VALUES (?) ";
    
    /** The SQL statement to insert a record into phase_criteria table. */
    private static final String INSERT_PHASE_CRITERIA_SQL = "INSERT INTO phase_criteria "
        + "(project_phase_id, phase_criteria_type_id, parameter) VALUES (?, 1, ?) ";

    /** The SQL statement to insert a record into scorecard_status_lu table. */
    private static final String INSERT_STATUS_SQL = "INSERT INTO scorecard_status_lu "
        + "(scorecard_status_id, name, description, create_user, create_date, modify_user, modify_date) "
        + "VALUES (?, ?, ?, ?, ?, ?, ?)";

    /** The SQL statement to insert a record into scorecard_type_lu table. */
    private static final String INSERT_TYPE_SQL = "INSERT INTO scorecard_type_lu "
        + "(scorecard_type_id, name, description, create_user, create_date, modify_user, modify_date) "
        + "VALUES (?, ?, ?, ?, ?, ?, ?)";

    /** The SQL statement to delete all record from scorecard table. */
    private static final String DELETE_SCORECARD_SQL = "DELETE FROM scorecard";

    /** The SQL statement to delete all record from project_category_lu table. */
    private static final String DELETE_PROJECT_SQL = "DELETE FROM project_category_lu";

    /** The SQL statement to delete all record from scorecard_status_lu table. */
    private static final String DELETE_STATUS_SQL = "DELETE FROM scorecard_status_lu";

    /** The SQL statement to delete all record from scorecard_type_lu table. */
    private static final String DELETE_TYPE_SQL = "DELETE FROM scorecard_type_lu";

    /** The SQL statement to delete all record from project_phase table. */
    private static final String DELETE_PROJECT_PHASE_SQL = "DELETE FROM project_phase";
    
    /** The SQL statement to delete all record from phase_criteria table. */
    private static final String DELETE_PHASE_CRITERIA_SQL = "DELETE FROM phase_criteria";

    /**
     * The constructor of AccuracyTestHelper.
     *
     */
    private AccuracyTestHelper() {
    }

    /**
     * Creates a new valid Scorecard instance.
     *
     * @param status the status of scorecard.
     * @param type the type of scorecard.
     * @param name the name of scorecard.
     * @param version the version of scorecard.
     * @param category the category id of scorecard.
     * @param min the min scope of scorecard.
     * @param max the max scope of scorecard.
     *
     * @return the instance of scorecard.
     */
    public static Scorecard createScorecard(ScorecardStatus status, ScorecardType type, String name, String version,
        long category, float min, float max) {
        Scorecard scorecard = new Scorecard();

        scorecard.setScorecardStatus(status);
        scorecard.setScorecardType(type);
        scorecard.setCategory(category);
        scorecard.setName(name);
        scorecard.setVersion(version);
        scorecard.setMinScore(min);
        scorecard.setMaxScore(max);

        scorecard.addGroups(createGroupArray(10.0f, 50.0f, 40.0f));

        return scorecard;
    }

    /**
     * Creates a valid three-member array of group instances, whose weights are w1, w2, w3.
     *
     * @param w1 the weight of the first member.
     * @param w2 the weight of the second member.
     * @param w3 the weight of the third member.
     *
     * @return the group array.
     */
    public static Group[] createGroupArray(float w1, float w2, float w3) {
        Group group1 = new Group(1, "group1", w1);
        Group group2 = new Group(2, "group2", w2);
        Group group3 = new Group(2, "group3", w3);

        group1.addSections(createSectionArray(1.0f, 1.1f, 97.9f));
        group2.addSections(createSectionArray(99.0f, 0.5f, 0.5f));
        group3.addSections(createSectionArray(50.0f, 49.9f, 0.1f));

        return new Group[] {group1, group2, group3};
    }

    /**
     * Creates a valid three-member array of section instances, whose weights are w1, w2, w3.
     *
     * @param w1 the weight of the first member.
     * @param w2 the weight of the second member.
     * @param w3 the weight of the third member.
     *
     * @return the section array.
     */
    public static Section[] createSectionArray(float w1, float w2, float w3) {
        Section section1 = new Section(1, "section1", w1);
        Section section2 = new Section(2, "section2", w2);
        Section section3 = new Section(3, "section3", w3);

        section1.addQuestions(createQuestionArray(10.0f, 40.0f, 50.0f));
        section2.addQuestions(createQuestionArray(20.0f, 50.0f, 30.0f));
        section3.addQuestions(createQuestionArray(30.1f, 60.2f, 9.7f));

        return new Section[] {section1, section2, section3};
    }

    /**
     * Creates a three-member array of question instances, whose weights are w1, w2, w3.
     *
     * @param w1 the weight of the first member.
     * @param w2 the weight of the second member.
     * @param w3 the weight of the third member.
     *
     * @return the question array.
     */
    public static Question[] createQuestionArray(float w1, float w2, float w3) {
        Question question1 = new Question(1, w1);
        Question question2 = new Question(2, w2);
        Question question3 = new Question(3, w3);

        question1.setName("question1_name");
        question1.setDescription(VALID_DESCRIPTION);
        question2.setName("question1_name");
        question2.setDescription(VALID_DESCRIPTION);
        question3.setName("question1_name");
        question3.setDescription(VALID_DESCRIPTION);

        return new Question[] {question1, question2, question3};
    }

    /**
     * <p>
     * Validates the value of a variable. The value cannot be <code>null</code>.
     * </p>
     *
     * @param value the argument to validate.
     * @param name the name of the argument.
     *
     * @throws IllegalArgumentException if the argument is null.
     */
    public static void checkNotNull(Object value, String name) {
        if (value == null) {
            throw new IllegalArgumentException(name + " should not be null.");
        }
    }

    /**
     * <p>
     * Validates the value of a variable. The value cannot be <code>null</code> or empty string.
     * </p>
     *
     * @param value the argument to validate.
     * @param name the name of the argument.
     *
     * @throws IllegalArgumentException if the argument is null or empty string.
     */
    public static void checkNotNullOrEmpty(String value, String name) {
        checkNotNull(value, name);

        if (value.trim().length() == 0) {
            throw new IllegalArgumentException(name + " should not be empty string.");
        }
    }

    /**
     * <p>
     * Validates the value of a variable. The value should be positive.
     * </p>
     *
     * @param value the argument to validate.
     * @param name the name of the argument.
     *
     * @throws IllegalArgumentException if the argument is not positive.
     */
    public static void checkPositive(long value, String name) {
        if (value <= 0) {
            throw new IllegalArgumentException(name + " should be positive.");
        }
    }

    /**
     * <p>
     * Validates an object array. The array cannot be <code>null</code> or empty (if allowEmpty is false), any element
     * of it cannot be null.
     * </p>
     *
     * @param array the object array to validate
     * @param name the name of the object array
     * @param allowEmpty the flag indicating whether empty array is allowed
     *
     * @throws IllegalArgumentException if the argument is null or empty (if allowEmpty is false), or any element of it
     *         is null
     */
    public static void checkLongArray(long[] array, String name, boolean allowEmpty) {
        checkNotNull(array, name);

        if (!allowEmpty && (array.length == 0)) {
            throw new IllegalArgumentException(name + " should not be empty.");
        }

        for (int i = 0; i < array.length; i++) {
            checkPositive(array[i], name + "[" + i + "]");
        }
    }

    /**
     * Returns the connection to the database.
     *
     * @return the connection to the database.
     *
     * @throws Exception if it fails to get the connection.
     */
    public static Connection getConnection() throws Exception {
        return new DBConnectionFactoryImpl(DB_NAMESPACE).createConnection();
    }

    /**
     * Deletes all records in all tables.
     *
     * @throws Exception if any error occurs.
     */
    public static void clearAllTables() throws Exception {
        Connection conn = getConnection();
        Statement stmt = conn.createStatement();

        stmt.executeUpdate(DELETE_SCORECARD_SQL);
        stmt.executeUpdate(DELETE_PROJECT_SQL);
        stmt.executeUpdate(DELETE_STATUS_SQL);
        stmt.executeUpdate(DELETE_TYPE_SQL);
        stmt.executeUpdate(DELETE_PHASE_CRITERIA_SQL);
        stmt.executeUpdate(DELETE_PROJECT_PHASE_SQL);

        stmt.close();
        conn.close();
    }

    /**
     * Inserts a scorecard record into scorecard table.
     *
     * @param scorecard the scorecard to insert.
     * @param operator the name of the operator.
     *
     * @throws Exception if any error occurs.
     */
    public static void insertScorecard(Scorecard scorecard, String operator)
        throws Exception {
        insertProjectCategory(scorecard.getCategory());
        insertProjectScorecard(scorecard.getId());

        insertStatus(scorecard.getScorecardStatus(), operator);
        insertType(scorecard.getScorecardType(), operator);

        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = getConnection();
            ps = conn.prepareStatement(INSERT_SCORECARD_SQL);

            ps.setLong(1, scorecard.getId());
            ps.setLong(2, scorecard.getScorecardStatus().getId());
            ps.setLong(3, scorecard.getScorecardType().getId());
            ps.setLong(4, scorecard.getCategory());
            ps.setString(5, scorecard.getName());
            ps.setString(6, scorecard.getVersion());
            ps.setFloat(7, scorecard.getMinScore());
            ps.setFloat(8, scorecard.getMaxScore());

            long time = Calendar.getInstance().getTimeInMillis();
            ps.setString(9, operator);
            ps.setDate(10, new Date(time));
            ps.setString(11, operator);
            ps.setDate(12, new Date(time));
            ps.executeUpdate();
        } finally {
            if (ps != null) {
                ps.close();
            }

            if (conn != null) {
                conn.close();
            }
        }
    }

    /**
     * Inserts a project category record into project_category_lu table.
     *
     * @param id the project category id to insert.
     *
     * @throws Exception if any error occurs.
     */
    public static void insertProjectCategory(long id) throws Exception {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = getConnection();
            ps = conn.prepareStatement(INSERT_PROJECT_SQL);

            ps.setLong(1, id);

            ps.executeUpdate();
        } finally {
            if (ps != null) {
                ps.close();
            }

            if (conn != null) {
                conn.close();
            }
        }
    }

    /**
     * Inserts a project scorecard record into project_scorecard table.
     *
     * @param id the project scorecard id to insert.
     *
     * @throws Exception if any error occurs.
     */
    public static void insertProjectScorecard(long id)
        throws Exception {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = getConnection();
            ps = conn.prepareStatement(INSERT_PROJECT_PHASE_SQL);

            ps.setLong(1, id);

            ps.executeUpdate();
            ps.close();
            
            ps = conn.prepareStatement(INSERT_PHASE_CRITERIA_SQL);

            ps.setLong(1, id);
            ps.setLong(2, id);

            ps.executeUpdate();
        } finally {
            if (ps != null) {
                ps.close();
            }

            if (conn != null) {
                conn.close();
            }
        }
    }

    /**
     * Inserts a scorecard status record into scorecard_status_lu table.
     *
     * @param status the status to insert.
     * @param operator the name of the operator.
     *
     * @throws Exception if any error occurs.
     */
    public static void insertStatus(ScorecardStatus status, String operator)
        throws Exception {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = getConnection();
            ps = conn.prepareStatement(INSERT_STATUS_SQL);

            ps.setLong(1, status.getId());
            ps.setString(2, status.getName());
            ps.setString(3, VALID_DESCRIPTION);

            long time = Calendar.getInstance().getTimeInMillis();
            ps.setString(4, operator);
            ps.setDate(5, new Date(time));
            ps.setString(6, operator);
            ps.setDate(7, new Date(time));

            ps.executeUpdate();
        } finally {
            if (ps != null) {
                ps.close();
            }

            if (conn != null) {
                conn.close();
            }
        }
    }

    /**
     * Inserts a scorecard type record into scorecard_type_lu table.
     *
     * @param type the type to insert.
     * @param operator the name of the operator.
     *
     * @throws Exception if any error occurs.
     */
    public static void insertType(ScorecardType type, String operator)
        throws Exception {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = getConnection();
            ps = conn.prepareStatement(INSERT_TYPE_SQL);

            ps.setLong(1, type.getId());
            ps.setString(2, type.getName());
            ps.setString(3, VALID_DESCRIPTION);

            long time = Calendar.getInstance().getTimeInMillis();
            ps.setString(4, operator);
            ps.setDate(5, new Date(time));
            ps.setString(6, operator);
            ps.setDate(7, new Date(time));

            ps.executeUpdate();
        } finally {
            if (ps != null) {
                ps.close();
            }

            if (conn != null) {
                conn.close();
            }
        }
    }
}
