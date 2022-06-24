/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.scorecard;

import java.util.List;
import java.util.ArrayList;

import com.topcoder.management.scorecard.data.Group;
import com.topcoder.management.scorecard.data.Question;
import com.topcoder.management.scorecard.data.QuestionType;
import com.topcoder.management.scorecard.data.Scorecard;
import com.topcoder.management.scorecard.data.ScorecardStatus;
import com.topcoder.management.scorecard.data.ScorecardType;
import com.topcoder.management.scorecard.data.Section;
import com.topcoder.management.scorecard.validation.DefaultScorecardValidator;
import com.topcoder.management.scorecard.validation.ValidationException;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.util.config.ConfigManager;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This is the Demo test.
 * </p>
 * @author zhuzeyuan
 * @version 1.0
 */
public class DemoTest extends TestCase {
    /**
     * Aggragates all tests in this class.
     * @return Test suite aggragating all tests.
     */
    public static Test suite() {
        return new TestSuite(DemoTest.class);
    }

    /**
     * The Demo for loading a config file.
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testDemoLoadConfig() throws Exception {
        ConfigManager config = ConfigManager.getInstance();
        config.add("config.xml");
    }

    /**
     * The Demo for using filters.
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testDemoFilter() throws Exception {
        // get filter for scorecard with type id equals 1
        Filter f1 = ScorecardSearchBundle.buildTypeIdEqualFilter(1);

        // get filter for scorecard with type id equals 1, 3, or 4
        List typeIds = new ArrayList();
        typeIds.add(new Long(1));
        typeIds.add(new Long(3));
        typeIds.add(new Long(4));
        Filter f2 = ScorecardSearchBundle.buildTypeIdInFilter(typeIds);

        // get filter for scorecard with type name equals 'Design'
        Filter f3 = ScorecardSearchBundle.buildTypeNameEqualFilter("Design");

        // get filter for scorecard with type name equals 'Design' or 'Development'
        List typeNames = new ArrayList();
        typeNames.add("Design");
        typeNames.add("Development");
        Filter f4 = ScorecardSearchBundle.buildTypeNameInFilter(typeNames);

        // get filter for scorecard with status id equals 1
        Filter f5 = ScorecardSearchBundle.buildStatusIdEqualFilter(1);

        // get filter for scorecard with project category id equals 1
        Filter f6 = ScorecardSearchBundle.buildProjectCategoryIdEqualFilter(1);

        // get filter for scorecard with project id equals 1
        Filter f7 = ScorecardSearchBundle.buildProjectIdEqualFilter(1);

        // get filter for scorecard with name equals 'Test'
        Filter f8 = ScorecardSearchBundle.buildNameEqualFilter("Test");

        // get filter for scorecard with version equals '1.0'
        Filter f9 = ScorecardSearchBundle.buildNameEqualFilter("1.0");
    }

    /**
     * In this helper method a correct scorecard will be generated.
     * @return the generated scorecard
     */
    private Scorecard generateScorecard() {
        // Preset a question.
        Question question = new Question(1, 100);
        question.setName("questoin_name");
        question.setDescription("this is the description");
        question.setGuideline("this is the guideline");

        // Preset a section with the above question.
        Section section = new Section(1, "section_name", 100);
        section.addQuestion(question);

        // Preset a group with the above section.
        Group group = new Group(1, "group_name", 100);
        group.addSection(section);

        // Preset a scorecard with the above group.
        Scorecard scorecard = new Scorecard(1);
        scorecard.addGroup(group);
        ScorecardType scorecardType = new ScorecardType(1, "scorecard_type_name");
        scorecard.setScorecardType(scorecardType);
        ScorecardStatus scorecardStatus = new ScorecardStatus(1, "scorecard_status_name");
        scorecard.setScorecardStatus(scorecardStatus);
        scorecard.setCategory(1);
        scorecard.setName("scorecard_name");
        scorecard.setVersion("1.1");
        scorecard.setMinScore(5.0f);
        scorecard.setMaxScore(95.0f);
        return scorecard;
    }

    /**
     * The Demo for using a validator.
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testDemoValidation() throws Exception {
        ScorecardValidator validator = new DefaultScorecardValidator("");

        Scorecard scorecard = generateScorecard();

        // No exception will be thrown, since the scorecard is correct.
        validator.validateScorecard(scorecard);

        // If we modify the scorecard a bit, with incorrect version number.
        scorecard.setVersion("v1.1");
        try {
            validator.validateScorecard(scorecard);
            fail("ValidationException must be thrown");
        } catch (ValidationException ve) {
            // expected
        }
    }

    /**
     * The general Demo for using the manager.
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testGeneralDemo() throws Exception {
        // create scorecard manager with default ns
        ScorecardManager manager = new ScorecardManagerImpl();

        // create a scorecard
        Scorecard scorecard = generateScorecard();

        // insert scorecard into the persistence, the operator is "user1"
        manager.createScorecard(scorecard, "user1");

        // makesome modification to the scorecard
        scorecard.setVersion("1.1");

        // update scorecard in the persistence, the operator is "user2"
        manager.updateScorecard(scorecard, "user2");

        // retrieve a scorecard
        Scorecard newScorecard = manager.getScorecard(scorecard.getId());

        // build a filter for searching scorecard, this case is the filter for type id equals 1
        Filter f1 = ScorecardSearchBundle.buildTypeIdEqualFilter(1);

        // light weight searching, which only retrieve scorecards without theirs sub items such as
        // groups, sections, questions
        // Scorecard[] lightResult = manager.searchScorecards(f1, false);
        // The above sentence cannot be called with empty scorecard database.

        // complete searching, which retrieve the scorecards and their sub items
        // Scorecard[] completeResult = manager.searchScorecards(f1, true);
        // The above sentence cannot be called with empty scorecard database.

        // retrieve all scorecard types
        ScorecardType[] scorecardTypes = manager.getAllScorecardTypes();

        // retrieve all scorecard statuses
        ScorecardStatus[] scorecardStatuses = manager.getAllScorecardStatuses();

        // retrieve all question types
        QuestionType[] questionTypes = manager.getAllQuestionTypes();
    }
}
