/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.scorecard;

import com.topcoder.management.scorecard.data.QuestionType;
import com.topcoder.management.scorecard.data.Scorecard;
import com.topcoder.management.scorecard.data.ScorecardStatus;
import com.topcoder.management.scorecard.data.ScorecardType;
import com.topcoder.management.scorecard.validation.ValidationException;

import com.topcoder.search.builder.filter.Filter;


/**
 * This interface defines the contract for scorecard manager. A scorecard manager implementation has the
 * responsibility to validate and create/update/retrieve/search scorecard instances in the persistence. The manager
 * read configuration settings to load the configured persistence and validator implementation.
 *
 * In version 1.0.2, a new method named getDefaultScorecardsIDInfo is added
 * to retrieve the scorecard type ids and scorecard ids for a specific category from default scorecards table.
 *
 * @author tuenm, zhuzeyuan
 * @author Angen
 *
 * @version 1.0.2
 */
public interface ScorecardManager {
    /**
     * Create the scorecard in the database using the given scorecard instance. The scorecard instance can include sub
     * items such as groups, sections and questions. Those sub items will be persisted as well. The operator parameter
     * is used as the creation/modification user of the scorecard and its subitems. The creation date and modification
     * date will be the current date time when the scorecard is created. The given scorecard instance will be
     * validated before persisting.
     * @param scorecard
     *            The scorecard instance to be created in the database.
     * @param operator
     *            The creation user of this scorecard.
     * @throws IllegalArgumentException
     *             if any input is null or the operator is empty string.
     * @throws PersistenceException
     *             if error occurred while accessing the database.
     * @throws ValidationException
     *             if error occurred while validating the scorecard instance.
     */
    public void createScorecard(Scorecard scorecard, String operator)
        throws PersistenceException, ValidationException;

    /**
     * Update the given scorecard instance into the database. The scorecard instance can include sub items such as
     * groups, sections and questions. Those sub items will be updated as well. If sub items are removed from the
     * scorecard, they will be deleted from the persistence. Likewise, if new sub items are added, they will be
     * created in the persistence. The operator parameter is used as the modification user of the scorecard and its
     * subitems. The modification date will be the current date time when the scorecard is updated. The given
     * scorecard instance will be validated before persisting.
     * @param scorecard
     *            The scorecard instance to be updated into the database.
     * @param operator
     *            The modification user of this scorecard.
     * @throws IllegalArgumentException
     *             if any input is null or the operator is empty string.
     * @throws PersistenceException
     *             if error occurred while accessing the database.
     * @throws ValidationException
     *             if error occurred while validating the scorecard instance.
     */
    public void updateScorecard(Scorecard scorecard, String operator)
        throws PersistenceException, ValidationException;

    /**
     * Retrieves the scorecard instance from the persistence given its id. The scorecard instance is retrieved with
     * its sub items, such as group, section and questions.
     * @return The scorecard instance.
     * @param id
     *            The id of the scorecard to be retrieved.
     * @throws IllegalArgumentException
     *             if the input id is less than or equal to zero.
     * @throws PersistenceException
     *             if error occurred while accessing the database.
     */
    public Scorecard getScorecard(long id) throws PersistenceException;

    /**
     * Search scorecard instances using the given filter parameter. The filter parameter decides the condition of
     * searching. This method uses the Search Builder component to perform searching. The search condition can be the
     * combination of any of the followings: - Scorecard name - Scorecard version - Scorecard type id - Scorecard type
     * name - Scorecard status id - Scorecard status name - Project category id that the scorecard linked to. -
     * Project id that the scorecard linked to.
     * @return An array of scorecard instance as the search result.
     * @param filter
     *            The filter to search for scorecards.
     * @param complete
     *            Indicates whether to retrieve the scorecard including its sub items.
     * @throws IllegalArgumentException
     *             if the filter is null.
     * @throws PersistenceException
     *             if error occurred while accessing the database.
     */
    public Scorecard[] searchScorecards(Filter filter, boolean complete)
        throws PersistenceException;

    /**
     * Retrieves all the scorecard types from the persistence.
     * @return An array of scorecard type instances.
     * @throws PersistenceException
     *             if error occurred while accessing the database.
     */
    public ScorecardType[] getAllScorecardTypes() throws PersistenceException;

    /**
     * Retrieves all the question types from the persistence.
     * @return An array of question type instances.
     * @throws PersistenceException
     *             if error occurred while accessing the database.
     */
    public QuestionType[] getAllQuestionTypes() throws PersistenceException;

    /**
     * Retrieves all the scorecard statuses from the persistence.
     * @return An array of scorecard status instances.
     * @throws PersistenceException
     *             if error occurred while accessing the database.
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
     * @throws PersistenceException
     *             if error occurred while accessing the persistence.
     * @throws IllegalArgumentException
     *             if the ids is less than or equal to zero. Or the input array is null or empty.
     */
    public Scorecard[] getScorecards(long[] ids, boolean complete)
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
