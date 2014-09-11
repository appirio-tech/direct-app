/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.scorecard.persistence;

import com.topcoder.management.scorecard.PersistenceException;
import com.topcoder.management.scorecard.data.Question;
import com.topcoder.management.scorecard.data.QuestionType;
import com.topcoder.management.scorecard.data.Scorecard;
import com.topcoder.management.scorecard.data.Group;

import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Iterator;

/**
 * Informix Persistence Helper class. This class includes argument validator and some other helper utilities.
 *
 * @author TCSDEVELOPER
 * @version 1.0.1
 */
class InformixPersistenceHelper {

    /** Constructor. */
    private InformixPersistenceHelper() {

    }

    /**
     * Validate the id array.
     *
     * @param ids The id array to validate.
     *
     * @throws IllegalArgumentException if the ids is less than or equal to zero. Or the input array is null or empty.
     */
    public static void validateIds(long[] ids) {
        if (ids == null || ids.length == 0) {
            // The given input is invalid
            throw new IllegalArgumentException("The given ids array is null or empty.");
        }
        for (int i = 0; i < ids.length; i++) {
            if (ids[i] <= 0) {
                // The given input is invalid
                throw new IllegalArgumentException("One of the ids is less than or equal to zero.");
            }
        }
    }

    /**
     * Validate the given id.
     *
     * @param id The id to validate.
     *
     * @throws IllegalArgumentException if the input id is less than or equal to zero.
     */
    public static void validateId(long id) {
        if (id <= 0) {
            // id must be > 0
            throw new IllegalArgumentException("The given id is less than or equal to zero.");
        }
    }

    /**
     * Validate the given order.
     *
     * @param order The id to validate.
     *
     * @throws IllegalArgumentException if the input id is less than zero.
     */
    public static void validateOrder(int order) {
        if (order < 0) {
            // order must be >= 0
            throw new IllegalArgumentException("The given id is less than zero.");
        }
    }

    /**
     * Validate the given object to make sure it's no null, otherwise throw IllegalArgumentException.
     *
     * @param object The object to validate.
     *
     * @throws IllegalArgumentException If the given object is null.
     */
    public static void validateObject(Object object) {
        if (object == null) {
            // Object can not be null.
            throw new IllegalArgumentException("The given argument is null.");
        }
    }

    /**
     * Validate the given string to make sure it's no null nor empty, otherwise throw IllegalArgumentException.
     *
     * @param str The string to validate.
     *
     * @throws IllegalArgumentException If the str is null or empty string.
     */
    public static void validateString(String str) {
        if (str == null || str.equals("")) {
            // Operator can not be null nor empty.
            throw new IllegalArgumentException("The given str is null or empty string.");
        }
    }

    /**
     * Validate the create method arguments in the group, section and question informix persistence.
     *
     * @param object   The object to validate, may be Group, Section and Question.
     * @param order    The order to validate.
     * @param operator The operator to validate.
     * @param parentId The id of the object to validate.
     *
     * @throws IllegalArgumentException if any input is null or the operator is empty string or order < 0 or parentId
     *                                  <=0.
     */
    public static void validateCreate(Object object, int order, String operator, long parentId) {
        // Validate arguments
        validateObject(object);
        validateOrder(order);
        validateString(operator);
        validateId(parentId);
    }

    /**
     * Generate the id string like "id1, id2, id3, ..." using the ids.
     *
     * @param ids The id array to generate the id string.
     *
     * @return The generated id string.
     */
    public static String generateIdString(long[] ids) {
        String strIds = "";
        for (int i = 0; i < ids.length; i++) {
            // Append id with ',' to strIds
            strIds += ids[i];
            if (i < ids.length - 1) {
                strIds += ", ";
            }
        }
        return strIds;
    }

    /**
     * Convert the list with Long as element to long[].
     *
     * @param longList The Long list to be converted.
     *
     * @return Converted long array.
     */
    public static long[] convertLongList(List longList) {
        long[] longArray = new long[longList.size()];
        int i = 0;
        for (Iterator iterator = longList.iterator(); iterator.hasNext();) {
            Long aLong = (Long)iterator.next();
            longArray[i++] = aLong.longValue();
        }
        return longArray;
    }

    /**
     * Generate the id string like "id1, id2, id3, ..." using the id list.
     *
     * @param idList The id list to generate the id string, the element type of the list is Long.
     *
     * @return The generated id string.
     */
    public static String generateIdString(List idList) {
        long[] ids = convertLongList(idList);
        return generateIdString(ids);
    }

    /**
     * Close the given PreparedStatement to release resource.
     *
     * @param ps The PreparedStatement to close.
     *
     * @throws PersistenceException If error occurs while close the statement.
     */
    public static void closePreparedStatement(PreparedStatement ps) throws PersistenceException {
        try {
            if (ps != null) {
                // close the ps and release resource
                ps.close();
            }
        } catch (SQLException e) {
            throw new PersistenceException("Error occurs while deleting groups", e);
        }
    }

    /**
     * Extract the question properties from ResultSet and create a new Question to set these properties. This method is
     * used in constructing the question when using sql to query the question table.
     *
     * @param rs The sql query result set that contains the information of the qustion.
     *
     * @return A created question that contains the returned result.
     *
     * @throws PersistenceException If error occurs while accessing database
     */
    public static Question extractQuestion(ResultSet rs) throws PersistenceException {
        try {
            Question question = new Question();
            question.setId(rs.getInt("scorecard_question_id"));
            // Set the question type
            QuestionType questionType = new QuestionType();
            questionType.setId(rs.getInt("scorecard_question_type_id"));
            questionType.setName(rs.getString("name"));
            question.setQuestionType(questionType);
            // Set other properties
            question.setDescription(rs.getString("description"));
            question.setGuideline(rs.getString("guideline"));
            question.setUploadDocument(rs.getBoolean("upload_document"));
            question.setUploadRequired(rs.getBoolean("upload_document_required"));
            question.setWeight(rs.getFloat("weight"));
            return question;
        } catch (SQLException e) {
            throw new PersistenceException("Error occurs while extract question.", e);
        }
    }

    /**
     * Check whether the section with the given id existed in the database.
     *
     * @param ps The PreparedStatement that checks the existence of the id.
     * @param id The id of the section to be checked.
     *
     * @throws PersistenceException If the id doesn't exist or error occurs while accessing database.
     */
    public static void checkIdExistence(PreparedStatement ps, long id) throws PersistenceException {
        try {
            // Check if the section exists
            ps.setInt(1, (int) id);
            ResultSet rs = ps.executeQuery();
            int count = 0;
            if (rs.next()) {
                // only one result
                count = rs.getInt(1);
            }
            // The section doesn't exist, throw exception
            if (count == 0) {
                throw new PersistenceException("The given id doesn't exist.");
            }
        } catch (SQLException e) {
            throw new PersistenceException("Error occur while checking id existence.", e);
        } finally {
            InformixPersistenceHelper.closePreparedStatement(ps);
        }
    }

    /**
     * Get the group from the old scorecard with the given groupId. This method is used in the update operations to help
     * to find the specific group from the scorecard.
     *
     * @param oldScorecard The scorecard instance before update. It is used to find out the group.
     * @param groupId      The identifier of the target group.
     *
     * @return The group instance equal to the id.
     */
    public static Group getGroup(Scorecard oldScorecard, long groupId) {
        // Find the old group through oldScorecard
        Group[] oldGroups = oldScorecard.getAllGroups();
        Group group = null;
        for (int i = 0; i < oldGroups.length; i++) {
            Group aGroup = oldGroups[i];
            if (aGroup.getId() == groupId) {
                // Find it.
                group = aGroup;
                break;
            }
        }
        return group;
    }
}
