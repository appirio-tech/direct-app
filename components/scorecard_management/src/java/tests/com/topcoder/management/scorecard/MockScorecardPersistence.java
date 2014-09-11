/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.management.scorecard;

import com.topcoder.util.sql.databaseabstraction.CustomResultSet;

import com.topcoder.management.scorecard.data.QuestionType;
import com.topcoder.management.scorecard.data.Scorecard;
import com.topcoder.management.scorecard.data.ScorecardStatus;
import com.topcoder.management.scorecard.data.ScorecardType;

/**
 * A simple mock instance of ScorecardPersistence. Nothing will be done in this class, except some parameters will be
 * recorded while calling those methods.
 * @author zhuzeyuan
 * @version 1.0
 */
public class MockScorecardPersistence implements ScorecardPersistence {

    /**
     * Default constructor, and reset all the backup fields.
     */
    public MockScorecardPersistence() {
    }

    /**
     * Constructor with namespace.
     * @param ns
     *            the given namespace
     */
    public MockScorecardPersistence(String ns) {
    }

    /**
     * Should be used to create a scorecard.
     * @param scorecard
     *            The scorecard instance to be created in the persistence.
     * @param operator
     *            The creation user of this scorecard.
     */
    public void createScorecard(Scorecard scorecard, String operator) {
        Helper.assertObjectNotNull(scorecard, "scorecard");
        Helper.assertStringNotEmpty(operator, "operator");
    }

    /**
     * Should be used to update a scorecard.
     * @param scorecard
     *            The scorecard instance to be updated into the persistence.
     * @param operator
     *            The modification user of this scorecard.
     */
    public void updateScorecard(Scorecard scorecard, String operator) {
        Helper.assertObjectNotNull(scorecard, "scorecard");
        Helper.assertStringNotEmpty(operator, "operator");
    }

    /**
     * Should be used to get a scorecard. The parameters will be recorded.
     * @return The scorecard instance.
     * @param id
     *            The id of the scorecard to be retrieved.
     * @param complete
     *            Indicates whether to retrieve the scorecard including its sub items.
     */
    public Scorecard getScorecard(long id, boolean complete) {
        ScorecardManagerImplTest.setId(id);
        ScorecardManagerImplTest.setComplete(complete);
        return null;
    }

    /**
     * Should be used to get all the scorecard types. A sample return value will be fecthed from
     * ScorecardManagerImplTest class, and returned.
     * @return An array of scorecard type instances.
     */
    public ScorecardType[] getAllScorecardTypes() {
        return ScorecardManagerImplTest.getReturnScorecardTypes();
    }

    /**
     * Should be used to get all the question types. A sample return value will be fecthed from
     * ScorecardManagerImplTest class, and returned.
     * @return An array of question type instances.
     */
    public QuestionType[] getAllQuestionTypes() {
        return ScorecardManagerImplTest.getReturnQuestionTypes();
    }

    /**
     * Should be used to get all the scorecard statuses. A sample return value will be fecthed from
     * ScorecardManagerImplTest class, and returned.
     * @return An array of scorecard status instances.
     */
    public ScorecardStatus[] getAllScorecardStatuses() {
        return ScorecardManagerImplTest.getReturnScorecardStatuses();
    }

    /**
     * Should be used to retrieve a bunch of scorecards. The parameters will be recorded.
     * @param ids
     *            The array of ids of the scorecards to be retrieved.
     * @param complete
     *            Indicates whether to retrieve the scorecard including its sub items.
     * @return An array of scorecard instances.
     */
    public Scorecard[] getScorecards(long[] ids, boolean complete) {
        ScorecardManagerImplTest.setIds(ids);
        ScorecardManagerImplTest.setComplete(complete);
        Helper.assertObjectNotNull(ids, "ids");
        if (ids.length == 0) {
            throw new IllegalArgumentException("ids must contain at least one element");
        }
        for (int i = 0; i < ids.length; i++) {
            Helper.assertIntegerGreaterThanZero(ids[i], "Element in ids");
        }
        return null;
    }


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
    public Scorecard[] getScorecards(CustomResultSet resultSet, boolean complete) throws PersistenceException {
        return new Scorecard[0];
    }

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
    public ScorecardIDInfo[] getDefaultScorecardsIDInfo(long projectCategoryId) throws PersistenceException {
        return new ScorecardIDInfo[0];
    }

}
