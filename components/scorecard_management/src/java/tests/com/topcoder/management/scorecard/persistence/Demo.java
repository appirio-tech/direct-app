/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.management.scorecard.persistence;

import com.topcoder.management.scorecard.data.QuestionType;
import com.topcoder.management.scorecard.data.Scorecard;
import com.topcoder.management.scorecard.data.ScorecardStatus;
import com.topcoder.management.scorecard.data.ScorecardType;

/**
 * <p>
 * This is the component demonstration. Since this component is only the persistence part,
 * the methods and classes from other packages won't be shown.
 *
 * @author kr00tki
 * @version 1.0
 */
public class Demo extends DbTestCase {

    /**
     * This demo shows how the InformixScorecardPersistence class can be used.
     * This is the only one class available to the user. All the methods are presented.
     *
     * @throws Exception to JUnit.
     */
    public void testDemo() throws Exception {
        // create the persistence
        InformixScorecardPersistence persistence = new InformixScorecardPersistence(NAMESPACE);

        // create the scorecard instance and store it in persistence
        Scorecard scorecard = createScorecard(1, 5);
        persistence.createScorecard(scorecard, "creator");

        // get the id of the scorecard
        long id = scorecard.getId();

        // get the complete scorecard from persitence using the id
        Scorecard card = persistence.getScorecard(id, true);

        // check if are same
        assertScorecard(scorecard, card, true);

        // get the only flat scorecard using the second method
        Scorecard[] cards = persistence.getScorecards(new long[] {id}, false);
        assertScorecard(scorecard, cards[0], false);

        // get all scorecard types
        ScorecardType[] scorecardTypes = persistence.getAllScorecardTypes();

        // get all scorecard statuses
        ScorecardStatus[] scorecardStatuses = persistence.getAllScorecardStatuses();

        // get all question types
        QuestionType[] questionTypes = persistence.getAllQuestionTypes();

        // print the previous version
        System.out.println("Version: " + scorecard.getVersion());

        // change the scorecard
        scorecard.setScorecardStatus(scorecardStatuses[2]);
        scorecard.setScorecardType(scorecardTypes[1]);
        scorecard.removeGroup(3);
        scorecard.getGroup(2).getSection(2).getQuestion(1).setQuestionType(questionTypes[2]);
        scorecard.getGroup(1).clearSections();
        scorecard.getGroup(0).addSection(createSection(0, 1));

        // update the scorecard in persistence
        persistence.updateScorecard(scorecard, "modifier");

        // print the new version
        System.out.println("Version: " + scorecard.getVersion());

        // check if persistence was updated
        assertScorecard(scorecard);

        // delete the scorecard
        scorecard.clearGroups();
        scorecard.setScorecardStatus(new ScorecardStatus(3, "Deleted"));

        persistence.updateScorecard(scorecard, "operator");
    }

}
