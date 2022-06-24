/*
 * Copyright (C) 2006-2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.scorecard.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.topcoder.management.scorecard.PersistenceException;
import com.topcoder.management.scorecard.data.Group;
import com.topcoder.management.scorecard.data.NamedScorecardStructure;
import com.topcoder.management.scorecard.data.Question;
import com.topcoder.management.scorecard.data.Scorecard;
import com.topcoder.management.scorecard.data.Section;
import com.topcoder.management.scorecard.persistence.logging.LogMessage;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;

/**
 * This class contains operations to create and update section instances into the Informix database. It is package
 * level because it is used only in InformixGroupPersistence class to persist section information. Connection to
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
class InformixSectionPersistence {

    /** Logger instance using the class name as category */
    private static final Log logger = LogManager.getLog(InformixSectionPersistence.class.getName());
    
    /**
     * Select sections by parent group id.
     */
    private static final String SELECT_SECTIONS_BY_PARENT_ID = "SELECT scorecard_section_id, name, "
            + "weight FROM scorecard_section WHERE scorecard_group_id = ? ORDER BY sort";

    /**
     * Select the section by its id.
     */
    private static final String SELECT_SCORECARD_SECTION_BY_ID = "SELECT scorecard_section_id, name, "
            + "weight FROM scorecard_section WHERE scorecard_section_id = ?";

    /**
     * Deletes the sections with ids.
     */
    private static final String DELETE_SCORECARD_SECTIONS = "DELETE FROM scorecard_section WHERE "
            + "scorecard_section_id IN ";

    /**
     * Selects questions ids for sections.
     */
    private static final String SELECT_QUESTION_IDS = "SELECT scorecard_question_id FROM scorecard_question "
            + "WHERE scorecard_section_id IN ";

    /**
     * Updates the section in database.
     */
    private static final String UPDATE_SCORECARD_SECTION = "UPDATE scorecard_section SET scorecard_group_id = ?, "
            + "name = ?, weight = ?, sort = ?, modify_user = ?, modify_date = ? WHERE scorecard_section_id = ?";

    /**
     * Inserts the section informations.
     */
    private static final String INSERT_SCORECARD_SECTION = "INSERT INTO scorecard_section (scorecard_section_id, "
            + "scorecard_group_id, name, weight, sort, create_user, create_date, modify_user, modify_date) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    /**
     * The connection used to connection to the database. It is initialized in the constructor from the passing
     * parameter.
     *
     */
    private final Connection connection;

    /**
     * Create a new instance of InformixSectionPersistence. The passing connection parameter will be used to
     * connect to the database. Null value is not allowed.
     *
     * @param connection The connection to the database.
     * @throws IllegalArgumentException if the input is null.
     */
    public InformixSectionPersistence(Connection connection) {
        if (connection == null) {
            throw new IllegalArgumentException("connection cannot be null.");
        }

        this.connection = connection;
    }

    /**
     * Create the section in the database using the given section instance. The section instance can include sub
     * items such as questions. Those sub items will be persisted as well. The operator parameter is used as the
     * creation/modification user of the section and its subitems. The creation date and modification date will be
     * the current date time when the section is created.
     *
     * @param section The section instance to be created in the database.
     * @param order the position of the section.
     * @param operator The creation user of this section.
     * @param parentId The id of the group that contains this.
     * @throws IllegalArgumentException if any input is null or the operator is empty string.
     * @throws PersistenceException if error occurred while accessing the database.
     */
    public void createSection(Section section, int order, String operator, long parentId)
        throws PersistenceException {
        if (section == null) {
            throw new IllegalArgumentException("section cannot be null.");
        }

        if (operator == null) {
            throw new IllegalArgumentException("operator cannot be null.");
        }

        if (operator.trim().length() == 0) {
            throw new IllegalArgumentException("operator cannot be empty String.");
        }

        logger.log(Level.INFO, new LogMessage("Section", null, operator,
        		"create new Section with order:" + order + " and parentId:" + parentId));
        
        PreparedStatement pstmt = null;
        long id = DBUtils.nextId(IdGeneratorUtility.getSectionIdGenerator());
        Timestamp time = new Timestamp(System.currentTimeMillis());
        try {
        	logger.log(Level.INFO, "insert record into scorecard_section with group_id:" + id);
        	
            pstmt = connection.prepareStatement(INSERT_SCORECARD_SECTION);
            // set the scorecard section data
            pstmt.setLong(1, id);
            pstmt.setLong(2, parentId); // group_id
            pstmt.setString(3, section.getName());
            pstmt.setFloat(4, section.getWeight());
            pstmt.setInt(5, order);

            // set the user and dates
            pstmt.setString(6, operator);
            pstmt.setTimestamp(7, time);
            pstmt.setString(8, operator);
            pstmt.setTimestamp(9, time);

            pstmt.executeUpdate();
            InformixQuestionPersistence questionPersistence = new InformixQuestionPersistence(connection);
            questionPersistence.createQuestions(section.getAllQuestions(), operator, id);
        } catch (SQLException ex) {
        	logger.log(Level.ERROR, new LogMessage("Section", new Long(id), operator, "Fail to create Section.", ex));
            throw new PersistenceException("Error occurs while creating sections.", ex);
        } finally {
            DBUtils.close(pstmt);
        }

        section.setId(id);
    }

    /**
     * Creates the sections in the database.
     *
     * @param sections the sections to be created.
     * @param operator the creation operator.
     * @param parentId the parent group id.
     * @throws PersistenceException if database error occurs.
     */
    void createSections(Section[] sections, String operator, long parentId) throws PersistenceException {
    	logger.log(Level.INFO,
    			new LogMessage("Section", null, operator, "create new Sections with parentId:" + parentId));
    	
        PreparedStatement pstmt = null;
        long[] ids = DBUtils.generateIdsArray(sections.length, IdGeneratorUtility.getSectionIdGenerator());
        Timestamp time = new Timestamp(System.currentTimeMillis());
        try {
            pstmt = connection.prepareStatement(INSERT_SCORECARD_SECTION);

            pstmt.setLong(2, parentId); // group_id
            InformixQuestionPersistence questionPersistence = new InformixQuestionPersistence(connection);
            for (int i = 0; i < sections.length; i++) {
                pstmt.setLong(1, ids[i]);
                pstmt.setString(3, sections[i].getName());
                pstmt.setFloat(4, sections[i].getWeight());
                pstmt.setInt(5, i);

                // set the user and dates
                pstmt.setString(6, operator);
                pstmt.setTimestamp(7, time);
                pstmt.setString(8, operator);
                pstmt.setTimestamp(9, time);

                pstmt.executeUpdate();

                logger.log(Level.INFO, "insert record into scorecard_section table with groupId:" + ids[i]);
                
                questionPersistence.createQuestions(sections[i].getAllQuestions(), operator, ids[i]);
            }
        } catch (SQLException ex) {
        	logger.log(Level.ERROR,
        			new LogMessage("Section", null, operator, "create new Sections with parentId:" + parentId, ex));
            throw new PersistenceException("Error occurs while creating sections.", ex);
        } finally {
            DBUtils.close(pstmt);
        }

        // assign the ids
        for (int i = 0; i < sections.length; i++) {
            sections[i].setId(ids[i]);
        }
    }

    /**
     * Update the given section instance into the database. The section instance can include sub items such as
     * questions. Those sub items will be updated as well. If sub items are removed from the section, they will be
     * deleted from the persistence. Likewise, if new sub items are added, they will be created in the persistence.
     * The operator parameter is used as the modification user of the section and its subitems. The modification
     * date will be the current date time when the section is updated.
     *
     * @param section The section instance to be updated into the database.
     * @param order the position of the section in persistence.
     * @param operator The modification user of this section.
     * @param parentId The id of the group that contains this.
     * @param oldScorecard The scorecard instance before update. It is used to find out remeved items.
     * @param deletedQuestionIds This is an output parameter. An empty array is expected to be passed in. Deleted
     *        question ids will be saved into this list.
     * @throws IllegalArgumentException if any input is null or the operator is empty string.
     * @throws PersistenceException if error occurred while accessing the database.
     */
    public void updateSection(Section section, int order, String operator, long parentId, Scorecard oldScorecard,
            List deletedQuestionIds) throws PersistenceException {
        if (section == null) {
            throw new IllegalArgumentException("section cannot be null.");
        }

        if (operator == null) {
            throw new IllegalArgumentException("operator cannot be null.");
        }

        if (operator.trim().length() == 0) {
            throw new IllegalArgumentException("operator cannot be empty String.");
        }

        if (oldScorecard == null) {
            throw new IllegalArgumentException("oldScorecard cannot be null.");
        }

        if (deletedQuestionIds == null) {
            throw new IllegalArgumentException("deletedQuestionIds cannot be null.");
        }

        logger.log(Level.INFO, new LogMessage("Section", new Long(section.getId()), operator,
        		"create new section with order:" + order + " ,parentId:" + parentId
        		+ ", oldScorecard:" + oldScorecard.getId()));
        
        Set oldSectionIds = getQuestionsIds(section, oldScorecard, parentId);
        // add all old questions to be deleted
        deletedQuestionIds.addAll(oldSectionIds);
        Question[] questions = section.getAllQuestions();
        InformixQuestionPersistence questionPersistence = new InformixQuestionPersistence(connection);

        for (int i = 0; i < questions.length; i++) {
            Long longId = new Long(questions[i].getId());
            if (questions[i].getId() == NamedScorecardStructure.SENTINEL_ID) {
                questionPersistence.createQuestion(questions[i], i, operator, section.getId());
            } else if (oldSectionIds.contains(longId)) {
                questionPersistence.updateQuestion(questions[i], i, operator, section.getId());
                deletedQuestionIds.remove(longId);
            }
        }

        updateSection(connection, section, operator, parentId, order);
    }

    /**
     * Updates the section in the persistence.
     *
     * @param conn the connection to use.
     * @param section the section to update.
     * @param operator the update opertor name.
     * @param parentId the id of the paren group.
     * @param order the position of the section in persistence.
     * @throws PersistenceException if any database error occurs.
     */
    private static void updateSection(Connection conn, Section section, String operator, long parentId, int order)
            throws PersistenceException {

        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(UPDATE_SCORECARD_SECTION);

            pstmt.setLong(1, parentId);
            pstmt.setString(2, section.getName());
            pstmt.setFloat(3, section.getWeight());
            pstmt.setInt(4, order);

            Timestamp time = new Timestamp(System.currentTimeMillis());
            pstmt.setString(5, operator);
            pstmt.setTimestamp(6, time);
            pstmt.setLong(7, section.getId());

            if (pstmt.executeUpdate() != 1) {
            	logger.log(Level.ERROR, "No section with id = " + section.getId());
                throw new PersistenceException("No section with id = " + section.getId());
            }
        } catch (SQLException ex) {
        	logger.log(Level.ERROR, new LogMessage("Section", new Long(section.getId()), operator,
        			"Error occurs while updating the Section.", ex));
            throw new PersistenceException("Error occurs while updating the section.", ex);
        } finally {
            DBUtils.close(pstmt);
        }

    }

    /**
     * Returns the ids of the questions for the section in the old scorecard.
     *
     * @param section the current section.
     * @param scorecard the previous scorecard.
     * @param groupId the id of the parent group.
     * @return the set of ids of the questions.
     */
    private static Set getQuestionsIds(Section section, Scorecard scorecard, long groupId) {
        Set ids = new HashSet();
        Group[] oldGroups = scorecard.getAllGroups();
        for (int i = 0; i < oldGroups.length; i++) {
            // find group first
            if (oldGroups[i].getId() == groupId) {
                Section[] sections = oldGroups[i].getAllSections();
                for (int j = 0; j < sections.length; j++) {
                    // find the section
                    if (sections[j].getId() == section.getId()) {
                        Question[] questions = sections[j].getAllQuestions();
                        // get all question ids.
                        for (int k = 0; k < questions.length; k++) {
                            ids.add(new Long(questions[k].getId()));
                        }
                        break;
                    }
                }
            }
        }

        return ids;
    }

    /**
     * Remove the sections with the given array of ids from the persistence. Section deletion is required when user
     * udpate a scorecard and remove a section from its section list.
     *
     * @param ids The id of the section to remove.
     * @throws IllegalArgumentException if the ids is less than or equal to zero. Or the input array is null or
     *         empty.
     * @throws PersistenceException if error occurred while accessing the database.
     */
    public void deleteSections(long[] ids) throws PersistenceException {
        DBUtils.checkIdsArray(ids, "ids");

        logger.log(Level.INFO, new LogMessage("Section", null, null,
        		"Delete Section with ids:" + InformixPersistenceHelper.generateIdString(ids)));
        
        PreparedStatement pstmt = null;
        PreparedStatement pstmt2 = null;
        ResultSet rs = null;
        try {
            // create the statement to select the questions ids first
            pstmt = connection.prepareStatement(SELECT_QUESTION_IDS + DBUtils.createQuestionMarks(ids.length));
            logger.log(Level.INFO, "delete record from scorecard_sections with ids:"
            		+ InformixPersistenceHelper.generateIdString(ids));
            
            // create the staement to delete the sections
            pstmt2 = connection.prepareStatement(DELETE_SCORECARD_SECTIONS
                    + DBUtils.createQuestionMarks(ids.length));

            // set the ids for both queries
            for (int i = 0; i < ids.length; i++) {
                pstmt.setLong(i + 1, ids[i]);
                pstmt2.setLong(i + 1, ids[i]);
            }

            // execute the select query
            rs = pstmt.executeQuery();
            List idsToDelete = new ArrayList();
            // collect all the question ids
            while (rs.next()) {
                idsToDelete.add(new Long(rs.getLong(1)));
            }

            // if there is anything to delete - create the persistence and drop questions
            if (idsToDelete.size() > 0) {
                InformixQuestionPersistence questionPersistence = new InformixQuestionPersistence(connection);
                questionPersistence.deleteQuestions(DBUtils.listToArray(idsToDelete));
            }

            pstmt2.executeUpdate();
        } catch (SQLException ex) {
        	logger.log(Level.ERROR, new LogMessage("Section", null, null,
            		"Failed to Delete Sections with ids:" + InformixPersistenceHelper.generateIdString(ids), ex));
            throw new PersistenceException("Error occurs while deleting the sections.", ex);
        } finally {
            DBUtils.close(rs);
            DBUtils.close(pstmt);
            DBUtils.close(pstmt2);
        }
    }

    /**
     * Retrieves the section instance from the persistence given its id. The section instance is always retrieved
     * with its sub items.
     *
     * @return The section instance.
     * @param id The id of the section to be retrieved.
     * @throws IllegalArgumentException if the input id is less than or equal to zero.
     * @throws PersistenceException if error occurred while accessing the database.
     */
    public Section getSection(long id) throws PersistenceException {
        if (id <= 0) {
            throw new IllegalArgumentException("The section id must be positive. Id = " + id);
        }
        logger.log(Level.INFO, new LogMessage("Section", new Long(id), null, "retrieve Section"));
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = connection.prepareStatement(SELECT_SCORECARD_SECTION_BY_ID);

            pstmt.setLong(1, id);
            rs = pstmt.executeQuery();

            InformixQuestionPersistence questionPersistence = new InformixQuestionPersistence(connection);
            if (rs.next()) {
                Section section = prepareSection(rs);
                section.addQuestions(questionPersistence.getQuestions(section.getId()));
                return section;
            }
        } catch (SQLException ex) {
        	logger.log(Level.ERROR, new LogMessage("Section", new Long(id), null, "Failed to retrieve Section", ex));
            throw new PersistenceException("Error occurs while retrieving the section.", ex);
        } finally {
            DBUtils.close(rs);
            DBUtils.close(pstmt);
        }

        return null;
    }

    /**
     * Returns the sections for the given parent group.
     *
     * @param parentId the parent group id.
     * @return the array of Sections for group id.
     * @throws PersistenceException id any database errors occurs.
     */
    Section[] getSections(long parentId) throws PersistenceException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = connection.prepareStatement(SELECT_SECTIONS_BY_PARENT_ID);
            pstmt.setLong(1, parentId);
            rs = pstmt.executeQuery();

            List result = new ArrayList();
            InformixQuestionPersistence questionPersistence = new InformixQuestionPersistence(connection);
            while (rs.next()) {
                Section section = prepareSection(rs);
                section.addQuestions(questionPersistence.getQuestions(section.getId()));
                result.add(section);
            }

            return (Section[]) result.toArray(new Section[result.size()]);
        } catch (SQLException ex) {
            logger.log(Level.ERROR, new LogMessage("Section", null, null,
                    "Failed to retrieve Section with parentId:" + parentId , ex));
            throw new PersistenceException("Error occurs while retrieving the section.", ex);
        } finally {
            DBUtils.close(rs);
            DBUtils.close(pstmt);
        }
    }

    /**
     * Creates the Section instance from the given ResultSet.
     *
     * @param rs the source result set.
     * @return the Section instance.
     * @throws SQLException if error occurs.
     */
    private Section prepareSection(ResultSet rs) throws SQLException {
        Section section = new Section();
        section.setId(rs.getLong("scorecard_section_id"));
        section.setName(rs.getString("name"));
        section.setWeight(rs.getFloat("weight"));

        return section;
    }
}
