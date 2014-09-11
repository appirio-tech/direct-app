/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.management.scorecard.data;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Locale;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Contains a demonstration of the Scorecard Data Structure component.
 *
 * @author      UFP2161
 * @copyright   Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 * @version     1.0
 */
public class Demo extends TestCase {

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Static Fields

    /**
     * A DateForamt that is specific to the United States, using the medium format.
     */
    private static final DateFormat US_MEDIUM_DATE_FORMAT =
            DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.US);

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Static Methods

    /**
     * Creates a test suite for the tests in this test case.
     *
     * @return  A TestSuite for this test case.
     */
    public static Test suite() {
        return new TestSuite(Demo.class);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Demonstrations

    /**
     * Creates a mock Scorecard and returns it.
     *
     * @return  A mock scorecard.
     *
     * @throws  ParseException
     *          A data format could not be parsed.
     */
    private Scorecard createMockScorecard() throws ParseException {
        // Create the scorecard.
        Scorecard scorecard = new Scorecard();

        // Create scorecard status and type objects.
        // In normal use, these would probably come from some persistence source.
        ScorecardType scorecardType = new ScorecardType(7, "Review Scorecard");
        ScorecardStatus scorecardStatus = new ScorecardStatus(12, "Active");

        // Set scorecard data values.
        scorecard.setId(1);

        // Note that although this demo will show the ids being set on scorecard structures, the
        // component in no way requires this to be done. Ids can be assigned later.
        scorecard.setScorecardStatus(scorecardStatus);
        scorecard.setScorecardType(scorecardType);
        scorecard.setName("Design Scorecard");
        scorecard.setVersion("1.0 (1/1/2006)");
        scorecard.setMinScore(0);
        scorecard.setMaxScore(4);
        scorecard.setInUse(true);
        scorecard.setCreationUser("Tooth Fairy");
        scorecard.setCreationTimestamp(US_MEDIUM_DATE_FORMAT.parse("Jan 1, 1996"));
        scorecard.setModificationUser("Santa");
        scorecard.setModificationTimestamp(US_MEDIUM_DATE_FORMAT.parse("Jan 1, 2000"));

        // Set the category and then demonstrate how to reset it to the unassigned value.
        scorecard.setCategory(17);
        scorecard.resetCategory();

        // Create groups.
        Group group1 = new Group(445, "Group 1", 65);
        Group group2 = new Group(423, "Group 2", 35);

        // Add groups to scorecard.
        scorecard.addGroups(new Group[] {group1, group2});

        // Create sections.
        Section section1 = new Section(345, "Overall", 25);
        Section section2 = new Section(523, "Documentation", 75);

        // Add sections to both groups.
        group1.addSections(new Section[]{section1, section2});
        group2.addSections(new Section[]{section1, section2});

        // Create questions.
        Question question = createQuestion();

        // Note that this call is not really necessary, as the  setUploadRequired call would
        // automatically set it to true.
        question.setUploadDocument(true);
        question.setUploadRequired(true);

        // Add the question to the section
        section1.addQuestion(question);
        section2.addQuestion(question);

        // Return the mock scorecard.
        return scorecard;
    }

    /**
     * Creates a Question and returns it.
     *
     * @return  A new Question.
     */
    private Question createQuestion() {
        // Create question type. As for scorecard type/status, this would almost certainly be
        // loaded from some persistence.
        QuestionType questionType = new QuestionType(12, "Normal Question");

        // Create a question.
        Question question = new Question();

        question.setId(12);
        question.setQuestionType(questionType);
        question.setDescription("How is the design overall?");
        question.setGuideline("Determine how good the design is overall.");
        question.setWeight(100);

        // Return the question.
        return question;
    }

    /**
     * Demonstrates how someone can programmatically create a scorecard.
     *
     * @throws  ParseException
     *          A data format could not be parsed.
     */
    public void testDemoScorecardCreation() throws ParseException {
        // Refer to the private method as its shared between the various demos.
        createMockScorecard();
    }

    /**
     * Demonstrates how someone can programmatically edit a scorecard using the ScorecardEditor.
     *
     * @throws  ParseException
     *          A data format could not be parsed.
     */
    public void testDemoEditingScorecards() throws ParseException {
        // Create the scorecard.
        Scorecard scorecard = createMockScorecard();

        // Create a ScorecardEditor for editing the properties of the scorecard while automatically
        // updating modification user and date/time.
        ScorecardEditor editor = new ScorecardEditor(scorecard, "The Grinch");

        // Update several properties of the scorecard, automatically updating the modification user
        // and date each time.
        editor.setInUse(false);
        editor.addGroup(new Group());
        editor.clearGroups();

        // Retrieve the modification date and user. These will now be the "The Grinch" and the current
        // date/time.
        editor.getScorecard().getModificationUser();
        editor.getScorecard().getModificationTimestamp();
    }

    /**
     * Demonstrates how someone can programmatically fetch data from a scorecard.
     *
     * @throws  ParseException
     *          A data format could not be parsed.
     */
    public void testGettingScorecardData() throws ParseException {
        // Typically, to inspect the data in a scorecard, the scorecard would be loaded from some
        // persistence source, probably by using the Scorecard Management component. This demo will
        // demonstrate the retrieval functionality using the scorecard created above.

        // In reality, this sort of a line would be used to get a scorecard for inspection.
        // scorecard = scorecardManager.getScorecard(12345);

        // For this demo, just create a mock scorecard.
        Scorecard scorecard = createMockScorecard();

        // Retrieve data from the scorecard.
        scorecard.getId();         // will be 1
        scorecard.getVersion();    // will be 1.0 (1/1/2006)

        // Other data fields can be fetched in a similar manner.

        // Retrieve groups.
        Group[] groups = scorecard.getAllGroups();

        // And iterate over groups.
        for (int i = 0; i < groups.length; i++) {
            Group group = groups[i];
            group.getId();

            // Other data fields can be fetched in a similar manner.

            // Retrieve sections.
            Section[] sections = group.getAllSections();

            // And iterate over sections.
            for (int j = 0; j < sections.length; j++) {
                Section section = sections[j];
                section.getId();

                // Other data fields can be fetched in a similar manner.

                // Retrieve questions.
                Question[] questions = section.getAllQuestions();

                // And iterate over questions.
                for (int k = 0; k < questions.length; k++) {
                    Question question = questions[k];
                    question.getId();

                    // Other data fields can be fetched in a similar manner.
                }
            }
        }
    }
}
