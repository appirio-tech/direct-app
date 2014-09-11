/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.scorecard;

import com.topcoder.management.scorecard.data.Group;
import com.topcoder.management.scorecard.data.Question;
import com.topcoder.management.scorecard.data.Scorecard;
import com.topcoder.management.scorecard.data.ScorecardStatus;
import com.topcoder.management.scorecard.data.ScorecardType;
import com.topcoder.management.scorecard.data.Section;
import com.topcoder.management.scorecard.validation.DefaultScorecardValidator;
import com.topcoder.management.scorecard.validation.ValidationException;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for DefaultScorecardValidator class.
 * @author zhuzeyuan
 * @version 1.0
 */
public class DefaultScorecardValidatorTest extends TestCase {

    /**
     * Provide a correct scorecard in setUp method for testing, and it will contain two same groups.
     */
    private Scorecard scorecard = null;

    /**
     * Provide a correct group in setUp method for testing, and it will contain two same sections.
     */
    private Group group = null;

    /**
     * Provide a correct section in setUp method for testing, and it will contain two same questions.
     */
    private Section section = null;

    /**
     * Provide a correct question in setUp method for testing.
     */
    private Question question = null;

    /**
     * Provide a default scorecard validator for testing.
     */
    private ScorecardValidator validator = null;

    /**
     * Aggragates all tests in this class.
     * @return Test suite aggragating all tests.
     */
    public static Test suite() {
        return new TestSuite(DefaultScorecardValidatorTest.class);
    }

    /**
     * Sets up a new correct question.
     * @throws Exception
     *             throw exception to JUnit.
     * @return the new question just made.
     */
    private Question setQuestion() throws Exception {
        Question newQuestion = new Question(1, 100);
        newQuestion.setName("question_name");
        newQuestion.setDescription("this is the description");
        newQuestion.setGuideline("this is the guideline");
        return newQuestion;
    }

    /**
     * Sets up a new correct section with the given question.
     * @param question
     *            The question to be added to this section.
     * @throws Exception
     *             throw exception to JUnit.
     * @return the new section.
     */
    private Section setSection(Question question) throws Exception {
        Section newSection = new Section(1, "section_name", 100);
        newSection.addQuestion(question);
        return newSection;
    }

    /**
     * Sets up a new correct group with the given section.
     * @param section
     *            The section to be added to this group.
     * @throws Exception
     *             throw exception to JUnit.
     * @return the new group just made.
     */
    private Group setGroup(Section section) throws Exception {
        Group newGroup = new Group(1, "group_name", 100);
        newGroup.addSection(section);
        return newGroup;
    }

    /**
     * Sets up a new correct scorecard with the given group.
     * @param group
     *            The group to be added to this scorecard.
     * @throws Exception
     *             throw exception to JUnit.
     * @return the new scorecard just made.
     */
    private Scorecard setScorecard(Group group) throws Exception {
        Scorecard newScorecard = new Scorecard(1);
        newScorecard.addGroup(group);
        ScorecardType scorecardType = new ScorecardType(1, "scorecard_type_name");
        newScorecard.setScorecardType(scorecardType);
        ScorecardStatus scorecardStatus = new ScorecardStatus(1, "scorecard_status_name");
        newScorecard.setScorecardStatus(scorecardStatus);
        newScorecard.setCategory(1);
        newScorecard.setName("scorecard_name");
        newScorecard.setVersion("1.1");
        newScorecard.setMinScore(5.0f);
        newScorecard.setMaxScore(95.0f);
        return newScorecard;
    }

    /**
     * Sets up the environment for the TestCase.
     * @throws Exception
     *             throw exception to JUnit.
     */
    protected void setUp() throws Exception {
        // Set up the instances to be used in testing.
        question = setQuestion();
        section = setSection(question);
        group = setGroup(section);
        scorecard = setScorecard(group);
        validator = new DefaultScorecardValidator("");
    }

    /**
     * Accuracy test of <code>DefaultScorecardValidator(String namespace)</code> constructor.
     * <p>
     * Test the default constructor with an empty namespace. No namespace is required for DefaultScorecardValidator in
     * fact.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testDefaultScorecardValidatorAccuracy() throws Exception {
        new DefaultScorecardValidator("");
    }

    /**
     * Accuracy test of <code>validateScorecard(Scorecard scorecard)</code> method.
     * <p>
     * Test this method with a preset scorecard in setUp.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testValidateScorecardAccuracy1() throws Exception {
        validator.validateScorecard(scorecard);
    }

    /**
     * Accuracy test of <code>validateScorecard(Scorecard scorecard)</code> method.
     * <p>
     * Test with a scorecard_name just not exceed the string maxlength.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testValidateScorecardAccuracy2() throws Exception {
        scorecard.setName("0123456789012345678901234567890123456789012345678901234567890123");
        validator.validateScorecard(scorecard);
    }

    /**
     * Accuracy test of <code>validateScorecard(Scorecard scorecard)</code> method.
     * <p>
     * Test a scorecard with three groups.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testValidateScorecardAccuracy3() throws Exception {
        group = setGroup(setSection(setQuestion()));
        group.setWeight(100.0f / 3);
        scorecard = setScorecard(group);
        group = setGroup(setSection(setQuestion()));
        group.setWeight(100.0f / 3);
        scorecard.addGroup(group);
        group = setGroup(setSection(setQuestion()));
        group.setWeight(100.0f / 3);
        scorecard.addGroup(group);
        validator.validateScorecard(scorecard);
    }

    /**
     * Failure test of <code>validateScorecard(Scorecard scorecard)</code> method.
     * <p>
     * The scorecard is in wrong - null scorecard.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testValidateScorecardFailure_ScorecardInWrong1() throws Exception {
        try {
            validator.validateScorecard(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>validateScorecard(Scorecard scorecard)</code> method.
     * <p>
     * The scorecard is in wrong - null scorecard_status.
     * </p>
     * <p>
     * Expect ValidationException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testValidateScorecardFailure_ScorecardInWrong2() throws Exception {
        try {
            scorecard.setScorecardStatus(null);
            validator.validateScorecard(scorecard);
            fail("Expect ValidationException.");
        } catch (ValidationException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>validateScorecard(Scorecard scorecard)</code> method.
     * <p>
     * The scorecard is in wrong - non-positive scorecard_status_id.
     * </p>
     * <p>
     * Expect ValidationException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     *
    public void testValidateScorecardFailure_ScorecardInWrong3() throws Exception {
        try {
            scorecard.setScorecardStatus(new ScorecardStatus());
            validator.validateScorecard(scorecard);
            fail("Expect ValidationException.");
        } catch (ValidationException e) {
            // expect
        }
    }*/

    /**
     * Failure test of <code>validateScorecard(Scorecard scorecard)</code> method.
     * <p>
     * The scorecard is in wrong - null scorecard_type.
     * </p>
     * <p>
     * Expect ValidationException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testValidateScorecardFailure_ScorecardInWrong4() throws Exception {
        try {
            scorecard.setScorecardType(null);
            validator.validateScorecard(scorecard);
            fail("Expect ValidationException.");
        } catch (ValidationException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>validateScorecard(Scorecard scorecard)</code> method.
     * <p>
     * The scorecard is in wrong - non-positive scorecard_type_id.
     * </p>
     * <p>
     * Expect ValidationException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     *
    public void testValidateScorecardFailure_ScorecardInWrong5() throws Exception {
        try {
            scorecard.setScorecardType(new ScorecardType());
            validator.validateScorecard(scorecard);
            fail("Expect ValidationException.");
        } catch (ValidationException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>validateScorecard(Scorecard scorecard)</code> method.
     * <p>
     * The scorecard is in wrong - scorecard_name too long.
     * </p>
     * <p>
     * Expect ValidationException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testValidateScorecardFailure_ScorecardInWrong6() throws Exception {
        try {
            scorecard.setName("012345678901234567890123456789012345678901234567890123456789012345");
            validator.validateScorecard(scorecard);
            fail("Expect ValidationException.");
        } catch (ValidationException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>validateScorecard(Scorecard scorecard)</code> method.
     * <p>
     * The scorecard is in wrong - scorecard_name empty.
     * </p>
     * <p>
     * Expect ValidationException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testValidateScorecardFailure_ScorecardInWrong7() throws Exception {
        try {
            scorecard.setName(" ");
            validator.validateScorecard(scorecard);
            fail("Expect ValidationException.");
        } catch (ValidationException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>validateScorecard(Scorecard scorecard)</code> method.
     * <p>
     * The scorecard is in wrong - scorecard_version illegal.
     * </p>
     * <p>
     * Expect ValidationException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testValidateScorecardFailure_ScorecardInWrong8() throws Exception {
        try {
            scorecard.setVersion("v1.1");
            validator.validateScorecard(scorecard);
            fail("Expect ValidationException.");
        } catch (ValidationException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>validateScorecard(Scorecard scorecard)</code> method.
     * <p>
     * The scorecard is in wrong - scorecard_version illegal.
     * </p>
     * <p>
     * Expect ValidationException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testValidateScorecardFailure_ScorecardInWrong9() throws Exception {
        try {
            scorecard.setVersion(".1.1");
            validator.validateScorecard(scorecard);
            fail("Expect ValidationException.");
        } catch (ValidationException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>validateScorecard(Scorecard scorecard)</code> method.
     * <p>
     * The scorecard is in wrong - total score exceed 100.
     * </p>
     * <p>
     * Expect ValidationException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testValidateScorecardFailure_ScorecardInWrong10() throws Exception {
        try {
            // Add a new group with 100points weight, that will make the total score exceed 100.
            scorecard.addGroup(setGroup(setSection(setQuestion())));
            validator.validateScorecard(scorecard);
            fail("Expect ValidationException.");
        } catch (ValidationException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>validateScorecard(Scorecard scorecard)</code> method.
     * <p>
     * The group is in wrong - group_name too long.
     * </p>
     * <p>
     * Expect ValidationException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testValidateScorecardFailure_GroupInWrong1() throws Exception {
        try {
            group.setName("01234567890123456789012345678901234567890123456789012345678901234");
            validator.validateScorecard(setScorecard(group));
            fail("Expect ValidationException.");
        } catch (ValidationException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>validateScorecard(Scorecard scorecard)</code> method.
     * <p>
     * The group is in wrong - group_name cannot be empty.
     * </p>
     * <p>
     * Expect ValidationException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testValidateScorecardFailure_GroupInWrong2() throws Exception {
        try {
            group.setName(" ");
            validator.validateScorecard(setScorecard(group));
            fail("Expect ValidationException.");
        } catch (ValidationException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>validateScorecard(Scorecard scorecard)</code> method.
     * <p>
     * The group is in wrong - an empty bad group.
     * </p>
     * <p>
     * Expect ValidationException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testValidateScorecardFailure_GroupInWrong3() throws Exception {
        try {
            Group badGroup = new Group();
            validator.validateScorecard(setScorecard(badGroup));
            fail("Expect ValidationException.");
        } catch (ValidationException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>validateScorecard(Scorecard scorecard)</code> method.
     * <p>
     * The group is in wrong - total points of sections exceed 100.
     * </p>
     * <p>
     * Expect ValidationException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testValidateScorecardFailure_GroupInWrong4() throws Exception {
        try {
            group.addSection(setSection(setQuestion()));
            validator.validateScorecard(setScorecard(group));
            fail("Expect ValidationException.");
        } catch (ValidationException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>validateScorecard(Scorecard scorecard)</code> method.
     * <p>
     * The section is in wrong - section_name too long.
     * </p>
     * <p>
     * Expect ValidationException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testValidateScorecardFailure_SectionInWrong1() throws Exception {
        try {
            section.setName("01234567890123456789012345678901234567890123456789012345678901234");
            validator.validateScorecard(setScorecard(setGroup(section)));
            fail("Expect ValidationException.");
        } catch (ValidationException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>validateScorecard(Scorecard scorecard)</code> method.
     * <p>
     * The section is in wrong - section_name cannot be empty.
     * </p>
     * <p>
     * Expect ValidationException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testValidateScorecardFailure_SectionInWrong2() throws Exception {
        try {
            section.setName(" ");
            validator.validateScorecard(setScorecard(setGroup(section)));
            fail("Expect ValidationException.");
        } catch (ValidationException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>validateScorecard(Scorecard scorecard)</code> method.
     * <p>
     * The section is in wrong - a fresh new section.
     * </p>
     * <p>
     * Expect ValidationException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testValidateScorecardFailure_SectionInWrong3() throws Exception {
        try {
            Section badSection = new Section();
            validator.validateScorecard(setScorecard(setGroup(badSection)));
            fail("Expect ValidationException.");
        } catch (ValidationException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>validateScorecard(Scorecard scorecard)</code> method.
     * <p>
     * The section is in wrong - total points for questions exceed 100.
     * </p>
     * <p>
     * Expect ValidationException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testValidateScorecardFailure_SectionInWrong4() throws Exception {
        try {
            section.addQuestion(new Question());
            validator.validateScorecard(setScorecard(setGroup(section)));
            fail("Expect ValidationException.");
        } catch (ValidationException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>validateScorecard(Scorecard scorecard)</code> method.
     * <p>
     * The question is in wrong - description cannot be empty.
     * </p>
     * <p>
     * Expect ValidationException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testValidateScorecardFailure_QuestionInWrong() throws Exception {
        try {
            question.setDescription(" ");
            validator.validateScorecard(setScorecard(setGroup(setSection(question))));
            fail("Expect ValidationException.");
        } catch (ValidationException e) {
            // expect
        }
    }

}
