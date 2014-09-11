/*
 * Copyright (C) 2006-2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.scorecard;

import com.topcoder.management.scorecard.data.QuestionType;
import com.topcoder.management.scorecard.data.Scorecard;
import com.topcoder.management.scorecard.data.ScorecardStatus;
import com.topcoder.management.scorecard.data.ScorecardType;

import com.topcoder.util.sql.databaseabstraction.CustomResultSet;


/**
 * This interface defines the contract that any scorecard persistence must implement. The implementation classes will
 * be used by ScorecardManagerImpl to performs scorecard persistence operations. The implementation classes should
 * have a constructor that receive a namespace string parameter so that they're exchangeable with each other by
 * changing configuration settings in the manager.
 *
 * In version 1.0.2, a new method named getDefaultScorecardsIDInfo is added
 * to retrieve the scorecard type ids and scorecard ids for a specific category from default scorecards table.
 *
 * @author tuenm
 * @author zhuzeyuan
 * @author George1
 * @author Angen
 *
 * @version 1.0.2
 */
public interface ScorecardPersistence {
    /**
     * Create the scorecard in the persistence using the given scorecard instance. The scorecard instance can include
     * sub items such as groups, sections and questions. Those sub items will be persisted as well. The operator
     * parameter is used as the creation/modification user of the scorecard and its subitems. The creation date and
     * modification date will be the current date time when the scorecard is created.
     * @param scorecard
     *            The scorecard instance to be created in the persistence.
     * @param operator
     *            The creation user of this scorecard.
     * @throws IllegalArgumentException
     *             if any input is null or the operator is empty string.
     * @throws PersistenceException
     *             if error occurred while accessing the persistence.
     */
    public void createScorecard(Scorecard scorecard, String operator)
        throws PersistenceException;

    /**
     * Update the given scorecard instance into the persistence. The scorecard instance can include sub items such as
     * groups, sections and questions. Those sub items will be updated as well. If sub items are removed from the
     * scorecard, they will be deleted from the persistence. Likewise, if new sub items are added, they will be
     * created in the persistence. The operator parameter is used as the modification user of the scorecard and its
     * subitems. The modification date will be the current date time when the scorecard is updated.
     * @param scorecard
     *            The scorecard instance to be updated into the persistence.
     * @param operator
     *            The modification user of this scorecard.
     * @throws IllegalArgumentException
     *             if any input is null or the operator is empty string.
     * @throws PersistenceException
     *             if error occurred while accessing the persistence.
     */
    public void updateScorecard(Scorecard scorecard, String operator)
        throws PersistenceException;

    /**
     * Retrieves the scorecard instance from the persistence given its id. The scorecard instance can be retrieved
     * with or without its sub items, depends on the 'complete' parameter.
     * @return The scorecard instance.
     * @param id
     *            The id of the scorecard to be retrieved.
     * @param complete
     *            Indicates whether to retrieve the scorecard including its sub items.
     * @throws IllegalArgumentException
     *             if the input id is less than or equal to zero.
     * @throws PersistenceException
     *             if error occurred while accessing the persistence.
     */
    public Scorecard getScorecard(long id, boolean complete)
        throws PersistenceException;

    /**
     * Retrieves all the scorecard types from the persistence.
     * @return An array of scorecard type instances.
     * @throws PersistenceException
     *             if error occurred while accessing the persistence.
     */
    public ScorecardType[] getAllScorecardTypes() throws PersistenceException;

    /**
     * Retrieves all the question types from the persistence.
     * @return An array of question type instances.
     * @throws PersistenceException
     *             if error occurred while accessing the persistence.
     */
    public QuestionType[] getAllQuestionTypes() throws PersistenceException;

    /**
     * Retrieves all the scorecard statuses from the persistence.
     * @return An array of scorecard status instances.
     * @throws PersistenceException
     *             if error occurred while accessing the persistence.
     */
    public ScorecardStatus[] getAllScorecardStatuses()
        throws PersistenceException;

    /**
     * <p>
     * Retrieves an array of the scorecard instances from the persistence given their ids. The scorecard instances can
     * be retrieved with or without its sub items, depends on the 'complete' parameter.
     * </p>
     * @param ids
     *            The array of ids of the scorecards to be retrieved.
     * @param complete
     *            Indicates whether to retrieve the scorecard including its sub items.
     * @return An array of scorecard instances.
     * @throws IllegalArgumentException
     *             if the ids is less than or equal to zero. Or the input array is null or empty.
     * @throws PersistenceException
     *             if error occurred while accessing the persistence.
     */
    public Scorecard[] getScorecards(long[] ids, boolean complete)
        throws PersistenceException;

    /**
     * Retrieves an array of the scorecard instances from the persistence given the provided result
     * of the select operation. The scorecard instances can be retrieved with or without its sub
     * items, depends on the 'complete' parameter.
     *
     * @return An array of scorecard instances.
     * @param resultSet
     *            The result of the SELECT operation. The result should include the following
     *            columns:
     *            <ul>
     *            <li>scorecard.scorecard_id</li>
     *            <li>scorecard.scorecard_status_id (as status_id)</li>
     *            <li>scorecard.scorecard_type_id (as type_id)</li>
     *            <li>scorecard.project_category_id (as project_category_id)</li>
     *            <li>scorecard.name (as scorecard_name)</li>
     *            <li>scorecard.version</li>
     *            <li>scorecard.min_score</li>
     *            <li>scorecard.max_score</li>
     *            <li>scorecard.create_user (as create_user)</li>
     *            <li>scorecard.create_date (as create_date)</li>
     *            <li>scorecard.modify_user (as modify_user)</li>
     *            <li>scorecard.modify_date (as modify_date)</li>
     *            <li>scorecard_status_lu.name (as status_name)</li>
     *            <li>scorecard_type_lu.name (as type_name)</li>
     *            </ul>
     * @param complete
     *            Indicates whether to retrieve the scorecard including its sub items.
     * @throws IllegalArgumentException
     *             if the ids is less than or equal to zero. Or the input array is null or empty.
     * @throws PersistenceException
     *             if error occurred while accessing the persistence.
     */
    public Scorecard[] getScorecards(CustomResultSet resultSet, boolean complete)
        throws PersistenceException;

    /**
     * Retrieves the scorecard type ids and scorecard ids for a specific category
     * from default scorecards table.
     * This method takes as a parameter projectCategoryId, gets the
     * id information of scorecards for it, and returns an array of ScorecardIDInfo instances, each one
     * containing the scorecard type id and the scorecard id.
     *
     * @param projectCategoryId the id of the project category.
     *
     * @return the ScorecardIDInfo instances array, each one containing the scorecard type id and the scorecard id.
     *
     * @throws PersistenceException if error occurred while accessing the persistence.
     * @throws IllegalArgumentException if the projectCategoryId is less than or equal to zero.
     */
    public ScorecardIDInfo[] getDefaultScorecardsIDInfo(long projectCategoryId)
        throws PersistenceException;
}
