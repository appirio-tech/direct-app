/*
 * Copyright (C) 2006-2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.scorecard.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.topcoder.management.scorecard.PersistenceException;
import com.topcoder.management.scorecard.data.Question;
import com.topcoder.management.scorecard.data.QuestionType;
import com.topcoder.management.scorecard.persistence.logging.LogMessage;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;

/**
 * This class contains operations to create and update question instances into the Informix database. It is package
 * level because it is used only in InformixSectionPersistence class to persist question information. Connection to
 * the database is passed to this class during initialization. Thread Safety: The implementation is not thread safe
 * in that two threads running the same method will use the same statement and could overwrite each other's work.
 * 
 * <p>
 * Changes in v1.0.2 (Cockpit Spec Review Backend Service Update v1.0):
 * - LogManager is used instead of LogFactory.
 * </p>
 *
 * @author tuenm, kr00tki, pulky
 * @version 1.0.2
 */
class InformixQuestionPersistence {
    
    /** Logger instance using the class name as category */
    private static final Log logger = LogManager.getLog(InformixQuestionPersistence.class.getName());
    

    /**
     * Selects all questions by parent id.
     */
    private static final String SELECT_SCORECARD_QUESTION_BY_PARENT_ID = "SELECT sq.scorecard_question_id, "
            + "sq.description, sq.guideline, sq.weight, sq.upload_document, sq.upload_document_required, "
            + "type.scorecard_question_type_id, type.name AS TypeName FROM scorecard_question AS sq JOIN "
            + "scorecard_question_type_lu AS type ON sq.scorecard_question_type_id = "
            + "type.scorecard_question_type_id WHERE sq.scorecard_section_id = ? ORDER BY sort";

    /**
     * Select the question by its id.
     */
    private static final String SELECT_SCORECARD_QUESTION_BY_ID = "SELECT sq.scorecard_question_id, "
            + "sq.description, sq.guideline, sq.weight, sq.upload_document, sq.upload_document_required, "
            + "type.scorecard_question_type_id, type.name AS TypeName FROM scorecard_question AS sq JOIN "
            + "scorecard_question_type_lu AS type ON sq.scorecard_question_type_id = type.scorecard_question_type_id "
            + "WHERE sq.scorecard_question_id = ?";

    /**
     * Deletes all questions with ids.
     */
    private static final String DELETE_SCORECARD_QUESTIONS = "DELETE FROM scorecard_question "
            + "WHERE scorecard_question_id IN ";

    /**
     * Updates the question.
     */
    private static final String UPDATE_SCORECARD_QUESTION = "UPDATE scorecard_question SET "
            + "scorecard_question_type_id = ?, scorecard_section_id = ?, description = ?, guideline = ?, "
            + "weight = ?, sort = ?, upload_document = ?, upload_document_required = ?, modify_user = ?, "
            + "modify_date = ? WHERE scorecard_question_id = ?";

    /**
     * Inserts the question to database.
     */
    private static final String INSERT_SCORECARD_QUESTION = "INSERT INTO scorecard_question "
            + "(scorecard_question_id, scorecard_question_type_id, scorecard_section_id, description, "
            + "guideline, weight, sort, upload_document, upload_document_required, create_user, create_date, "
            + "modify_user, modify_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    /**
     * The connection used to connection to the database. It is initialized in the constructor from the passing
     * parameter.
     *
     */
    private final Connection connection;

    /**
     * Create a new instance of InformixQuestionPersistence. The passing connection parameter will be used to
     * connect to the database. Null value is not allowed. Implementation notes: - Set the member connection
     * variable.
     *
     *
     * @param connection The connection to the database.
     * @throws IllegalArgumentException if the input is null.
     */
    public InformixQuestionPersistence(Connection connection) {
        if (connection == null) {
            throw new IllegalArgumentException("connection cannot be null.");
        }

        this.connection = connection;
    }

    /**
     * Create the question in the database using the given question instance. The operator parameter is used as the
     * creation/modification user of the question. The creation date and modification date will be the current date
     * time when the question is created.
     *
     *
     * @param question The question instance to be created in the database.
     * @param order the order of the question in persistence.
     * @param operator The creation user of this question.
     * @param parentId The id of the section that contains this.
     * @throws IllegalArgumentException if any input is null or the operator is empty string.
     * @throws PersistenceException if error occurred while accessing the database.
     */
    public void createQuestion(Question question, int order, String operator, long parentId)
            throws PersistenceException {
        if (question == null) {
            throw new IllegalArgumentException("question cannot be null.");
        }

        if (operator == null) {
            throw new IllegalArgumentException("operator cannot be null.");
        }

        if (operator.trim().length() == 0) {
            throw new IllegalArgumentException("operator cannot be empty String.");
        }
        
        logger.log(Level.INFO, new LogMessage("Question", null, operator,
        		"Create new Question with order:" + order + " and parentId:" + parentId));

        // get the id
        long id = DBUtils.nextId(IdGeneratorUtility.getQuestionIdGenerator());

        PreparedStatement pstmt = null;
        try {
        	logger.log(Level.INFO, "insert record into scorecard_question with question id:" + id);
            // create the statement and set the values
            pstmt = connection.prepareStatement(INSERT_SCORECARD_QUESTION);

            Timestamp time = new Timestamp(System.currentTimeMillis());
            pstmt.setLong(1, id);
            pstmt.setLong(2, question.getQuestionType().getId());
            pstmt.setLong(3, parentId); // scorecard_section_id
            pstmt.setString(4, question.getDescription());
            if (question.getGuideline() == null) {
                pstmt.setNull(5, Types.VARCHAR);
            } else {
                pstmt.setString(5, question.getGuideline());
            }

            pstmt.setFloat(6, question.getWeight());
            pstmt.setInt(7, order);
            pstmt.setBoolean(8, question.isUploadDocument());
            pstmt.setBoolean(9, question.isUploadRequired());

            // user and dates
            pstmt.setString(10, operator);
            pstmt.setTimestamp(11, time);
            pstmt.setString(12, operator);
            pstmt.setTimestamp(13, time);

            pstmt.executeUpdate();

        } catch (SQLException ex) {
        	logger.log(Level.ERROR, new LogMessage("Question", new Long(id), operator,
            		"Failed to Create new Question with order:" + order + " and parentId:" + parentId, ex));
            throw new PersistenceException("Error occurs while creating questions.", ex);

        } finally {
            DBUtils.close(pstmt);
        }

        // set the id
        question.setId(id);
    }

    /**
     * Create the given Questions in the persistence.
     *
     * @param questions the questions to store.
     * @param operator the creation operator.
     * @param parentId the id of the parent section.
     * @throws PersistenceException if any database error occurs.
     */
    void createQuestions(Question[] questions, String operator, long parentId) throws PersistenceException {
    	logger.log(Level.INFO,
    			new LogMessage("Question", null, operator, "Create new Questions with parentId:" + parentId));
    	
        // generate the ids
        long[] ids = DBUtils.generateIdsArray(questions.length, IdGeneratorUtility.getQuestionIdGenerator());
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(INSERT_SCORECARD_QUESTION);
            // set the parent
            pstmt.setLong(3, parentId); // scorecard_section_id
            Timestamp time = new Timestamp(System.currentTimeMillis());
            // set the creaton values for all questions.
            for (int i = 0; i < questions.length; i++) {
                pstmt.setLong(1, ids[i]);
                pstmt.setLong(2, questions[i].getQuestionType().getId());
                pstmt.setString(4, questions[i].getDescription());
                if (questions[i].getGuideline() == null) {
                    pstmt.setNull(5, Types.VARCHAR);
                } else {
                    pstmt.setString(5, questions[i].getGuideline());
                }
                pstmt.setFloat(6, questions[i].getWeight());
                pstmt.setInt(7, i);
                pstmt.setBoolean(8, questions[i].isUploadDocument());
                pstmt.setBoolean(9, questions[i].isUploadRequired());

                // user and dates
                pstmt.setString(10, operator);
                pstmt.setTimestamp(11, time);
                pstmt.setString(12, operator);
                pstmt.setTimestamp(13, time);

                logger.log(Level.INFO, "insert record into scorecard_question with question id:" + ids[i]);
                pstmt.executeUpdate();
            }

        } catch (SQLException ex) {
        	logger.log(Level.ERROR, new LogMessage("Question", null, operator,
            		"Failed to Create new Questions with parentId:" + parentId, ex));
            throw new PersistenceException("Error occurs while creating questions.", ex);
        } finally {
            DBUtils.close(pstmt);
        }
        // set the ids to questions
        for (int i = 0; i < questions.length; i++) {
            questions[i].setId(ids[i]);
        }
    }

    /**
     * Update the given question instance into the database. The operator parameter is used as the modification
     * user of the question. The modification date will be the current date time when the question is updated.
     *
     * @param question The question instance to be updated into the database.
     * @param order the position of the question in the database.
     * @param operator The modification user of this question.
     * @param parentId The id of the section that contains this.
     * @throws IllegalArgumentException if any input is null or the operator is empty string.
     * @throws PersistenceException if error occurred while accessing the database.
     */
    public void updateQuestion(Question question, int order, String operator, long parentId)
            throws PersistenceException {
        if (question == null) {
            throw new IllegalArgumentException("question cannot be null.");
        }

        if (operator == null) {
            throw new IllegalArgumentException("operator cannot be null.");
        }

        if (operator.trim().length() == 0) {
            throw new IllegalArgumentException("operator cannot be empty String.");
        }

        logger.log(Level.INFO, new LogMessage("Question", new Long(question.getId()), operator,
        		"Update Question with order:" + order + " and parentId:" + parentId));
        
        PreparedStatement pstmt = null;
        try {
        	logger.log(Level.INFO, "update record in scorecard_question with question id:" + question.getId());
        	
            // prepare the statement
            pstmt = connection.prepareStatement(UPDATE_SCORECARD_QUESTION);

            // set update values
            pstmt.setLong(1, question.getQuestionType().getId());
            pstmt.setLong(2, parentId);
            pstmt.setString(3, question.getDescription());
            if (question.getGuideline() == null) {
                pstmt.setNull(4, Types.VARCHAR);
            } else {
                pstmt.setString(4, question.getGuideline());
            }

            pstmt.setFloat(5, question.getWeight());
            pstmt.setInt(6, order);
            pstmt.setBoolean(7, question.isUploadDocument());
            pstmt.setBoolean(8, question.isUploadRequired());

            Timestamp time = new Timestamp(System.currentTimeMillis());
            pstmt.setString(9, operator);
            pstmt.setTimestamp(10, time);

            pstmt.setLong(11, question.getId());

            if (pstmt.executeUpdate() != 1) {
            	logger.log(Level.ERROR, "The question not exists in the database. Id: " + question.getId());
                throw new PersistenceException("The question not exists in the database. Id: " + question.getId());
            }
        } catch (SQLException ex) {
        	logger.log(Level.ERROR, new LogMessage("Question", new Long(question.getId()), operator,
            		"Failed to Update Question with order:" + order + " and parentId:" + parentId, ex));
            throw new PersistenceException("Error occurs while updating the question.", ex);
        } finally {
            DBUtils.close(pstmt);
        }
    }

    /**
     * Remove the questions with the given array of ids from the persistence. Question deletion is required when
     * user udpate a scorecard and remove a question from its question list.
     *
     *
     * @param ids The array of ids of the questions to remove.
     * @throws IllegalArgumentException if the ids is less than or equal to zero. Or the input array is null or
     *         empty.
     * @throws PersistenceException if error occurred while accessing the database.
     */
    public void deleteQuestions(long[] ids) throws PersistenceException {
        DBUtils.checkIdsArray(ids, "ids");
        logger.log(Level.INFO, new LogMessage("Question", null, null,
        		"Delete Questions with ids:" + InformixPersistenceHelper.generateIdString(ids)));
        
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(DELETE_SCORECARD_QUESTIONS
                    + DBUtils.createQuestionMarks(ids.length));
            for (int i = 0; i < ids.length; i++) {
                pstmt.setLong(i + 1, ids[i]);
            }

            pstmt.executeUpdate();
        } catch (SQLException ex) {
        	logger.log(Level.ERROR, new LogMessage("Question", null, null,
            		"Failed to delete Questions with ids:" + InformixPersistenceHelper.generateIdString(ids)));
            throw new PersistenceException("Error occurs while deleting the questions.", ex);
        } finally {
            DBUtils.close(pstmt);
        }
    }

    /**
     * Retrieves the question instance from the persistence given its id.
     *
     * @return The question instance.
     * @param id The id of the question to be retrieved.
     * @throws IllegalArgumentException if the input id is less than or equal to zero.
     * @throws PersistenceException if error occurred while accessing the database.
     */
    public Question getQuestion(long id) throws PersistenceException {
        if (id <= 0) {
            throw new IllegalArgumentException("The id must be positive.");
        }

        logger.log(Level.INFO, new LogMessage("Question", new Long(id), null, "retrieve Question"));
        
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = connection.prepareStatement(SELECT_SCORECARD_QUESTION_BY_ID);
            pstmt.setLong(1, id);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                return populateQuestion(rs);
            }
        } catch (SQLException ex) {
        	logger.log(Level.ERROR,
        			new LogMessage("Question", new Long(id), null, "Failed to retrieve Question", ex));
            throw new PersistenceException("Error occurs while retrieving question.", ex);
        } finally {
            DBUtils.close(rs);
            DBUtils.close(pstmt);
        }

        return null;
    }

    /**
     * Returns the Questions array for the given parent section id.
     *
     * @param parentId the section id.
     * @return the Questions for the section.
     * @throws PersistenceException if ant database error occurs.
     */
    Question[] getQuestions(long parentId) throws PersistenceException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        logger.log(Level.INFO, new LogMessage("Question", null, null,
    			"retrieve Question with parent id:" + parentId));
        
        try {
            pstmt = connection.prepareStatement(SELECT_SCORECARD_QUESTION_BY_PARENT_ID);
            pstmt.setLong(1, parentId);
            rs = pstmt.executeQuery();

            List result = new ArrayList();
            while (rs.next()) {
                result.add(populateQuestion(rs));
            }

            return (Question[]) result.toArray(new Question[result.size()]);
        } catch (SQLException ex) {
        	logger.log(Level.ERROR, new LogMessage("Question", null, null,
        			"Failed to retrieve Question with parent id:" + parentId, ex));
            throw new PersistenceException("Error occurs while retrieving question.", ex);
        } finally {
            DBUtils.close(rs);
            DBUtils.close(pstmt);
        }
    }

    /**
     * Creates the question instance using the ResultSet as source.
     *
     * @param rs the source result set.
     * @return the Questio instance.
     * @throws SQLException if any error occurs.
     */
    private Question populateQuestion(ResultSet rs) throws SQLException {
        Question question = new Question();
        question.setId(rs.getLong("scorecard_question_id"));
        question.setDescription(rs.getString("description"));
        question.setGuideline(rs.getString("guideline"));
        question.setUploadDocument(rs.getBoolean("upload_document"));
        question.setUploadRequired(rs.getBoolean("upload_document_required"));
        question.setWeight(rs.getFloat("weight"));

        QuestionType type = new QuestionType();
        type.setId(rs.getLong("scorecard_question_type_id"));
        type.setName(rs.getString("TypeName"));

        question.setQuestionType(type);
        return question;
    }
}
