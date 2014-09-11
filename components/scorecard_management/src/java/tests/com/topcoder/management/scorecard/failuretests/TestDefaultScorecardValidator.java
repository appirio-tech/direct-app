/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.scorecard.failuretests;

import com.topcoder.management.scorecard.data.Group;
import com.topcoder.management.scorecard.data.Question;
import com.topcoder.management.scorecard.data.Scorecard;
import com.topcoder.management.scorecard.data.ScorecardStatus;
import com.topcoder.management.scorecard.data.ScorecardType;
import com.topcoder.management.scorecard.data.Section;
import com.topcoder.management.scorecard.validation.DefaultScorecardValidator;
import com.topcoder.management.scorecard.validation.ValidationException;

import junit.framework.TestCase;


/**
 * Tests for DefaultScorecardValidator class.
 *
 * @author qiucx0161
 * @version 1.0
 */
public class TestDefaultScorecardValidator extends TestCase {
    /** A DefaultScorecardValidator used for test. */
    private DefaultScorecardValidator validator = null;

    /** A Scorecard used for test. */
    private Scorecard scorecard = null;

    /** A Section used for test. */
    private Section section = null;

    /** A Question used for test. */
    private Question question = null;

    /**
     * Setup the test environment.
     *
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();
        validator = new DefaultScorecardValidator("namespace");

        scorecard = new Scorecard(999990, "validator test");

        question = new Question(999991, "Is elegant APIs?", 100.0f);
        question.setDescription("here is the first des.");
        question.setGuideline("here is the first guide");

        section = new Section(999992, "structure design party.", 100.0f);
        section.addQuestion(question);

        Group group = new Group(999993, "Method.", 100.0f);
        group.addSection(section);

        scorecard.addGroup(group);
        scorecard.setCategory(999994);
        scorecard.setVersion("2.0");
        scorecard.setMinScore(0);
        scorecard.setMaxScore(100.0f);
        scorecard.setScorecardStatus(new ScorecardStatus(999995, "incompleted."));
        scorecard.setScorecardType(new ScorecardType(999996, "design"));
    }

    /**
     * Setup the test environment.
     *
     * @throws Exception to JUnit.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        validator = null;
    }

    /**
     * Tests validateScorecard(Scorecard scorecard) method with invalid Scorecard
     * scorecard, IllegalArgumentException should be thrown.
     */
    public void testValidateScorecard_NullScorecard() {
        try {
            validator.validateScorecard(null);
            fail("testValidateScorecard_NullScorecard is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknow exception occurs in testValidateScorecard_NullScorecard.");
        }
    }

    /**
     * Tests validateScorecard(Scorecard scorecard) method with invalid Scorecard
     * scorecard, null ScorecardStatus ValidationException should be thrown.
     */
    public void testValidateScorecard_NullScorecardStatus() {
        try {
            scorecard.resetScorecardStatus();
            validator.validateScorecard(scorecard);
            fail("testValidateScorecard_NullScorecardStatus is failure.");
        } catch (ValidationException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknow exception occurs in testValidateScorecard_NullScorecardStatus.");
        }
    }

    /**
     * Tests validateScorecard(Scorecard scorecard) method with invalid Scorecard
     * scorecard, invalid ScorecardStatus id ValidationException should be thrown.
     *
    public void testValidateScorecard_InvalidScorecardStatusId() {
        try {
            scorecard.setScorecardStatus(new ScorecardStatus());
            validator.validateScorecard(scorecard);
            fail("testValidateScorecard_InvalidScorecardStatusId is failure.");
        } catch (ValidationException iae) {
            // pass
        } catch (Exception e) {
            fail(
                "Unknow exception occurs in testValidateScorecard_InvalidScorecardStatusId.");
        }
    }

    /**
     * Tests validateScorecard(Scorecard scorecard) method with invalid Scorecard
     * scorecard, null ScorecardType ValidationException should be thrown.
     */
    public void testValidateScorecard_NullScorecardType() {
        try {
            scorecard.resetScorecardType();
            validator.validateScorecard(scorecard);
            fail("testValidateScorecard_NullScorecardType is failure.");
        } catch (ValidationException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknow exception occurs in testValidateScorecard_NullScorecardType.");
        }
    }

    /**
     * Tests validateScorecard(Scorecard scorecard) method with invalid Scorecard
     * scorecard, invalid ScorecardType id ValidationException should be thrown.
     *
    public void testValidateScorecard_InvalidScorecardTypeId() {
        try {
            scorecard.setScorecardType(new ScorecardType());
            validator.validateScorecard(scorecard);
            fail("testValidateScorecard_InvalidScorecardTypeId is failure.");
        } catch (ValidationException iae) {
            // pass
        } catch (Exception e) {
            fail(
                "Unknow exception occurs in testValidateScorecard_InvalidScorecardTypeId.");
        }
    }

    /**
     * Tests validateScorecard(Scorecard scorecard) method with invalid Scorecard
     * scorecard, invalid Category id ValidationException should be thrown.
     */
    public void testValidateScorecard_InvalidCategoryId() {
        try {
            scorecard.resetCategory();
            validator.validateScorecard(scorecard);
            fail("testValidateScorecard_InvalidCategoryId is failure.");
        } catch (ValidationException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknow exception occurs in testValidateScorecard_InvalidCategoryId.");
        }
    }

    /**
     * Tests validateScorecard(Scorecard scorecard) method with invalid Scorecard
     * scorecard, null Name ValidationException should be thrown.
     */
    public void testValidateScorecard_NullName() {
        try {
            scorecard.resetName();
            validator.validateScorecard(scorecard);
            fail("testValidateScorecard_NullName is failure.");
        } catch (ValidationException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknow exception occurs in testValidateScorecard_NullName.");
        }
    }

    /**
     * Tests validateScorecard(Scorecard scorecard) method with invalid Scorecard
     * scorecard, empty Name ValidationException should be thrown.
     */
    public void testValidateScorecard_EmptyName() {
        try {
            scorecard.setName(" ");
            validator.validateScorecard(scorecard);
            fail("testValidateScorecard_EmptyName is failure.");
        } catch (ValidationException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknow exception occurs in testValidateScorecard_EmptyName.");
        }
    }

    /**
     * Tests validateScorecard(Scorecard scorecard) method with invalid Scorecard
     * scorecard, null Version ValidationException should be thrown.
     */
    public void testValidateScorecard_NullVersion() {
        try {
            scorecard.resetVersion();
            validator.validateScorecard(scorecard);
            fail("testValidateScorecard_NullVersion is failure.");
        } catch (ValidationException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknow exception occurs in testValidateScorecard_NullVersion.");
        }
    }

    /**
     * Tests validateScorecard(Scorecard scorecard) method with invalid Scorecard
     * scorecard, empty Version ValidationException should be thrown.
     */
    public void testValidateScorecard_EmptyVersion() {
        try {
            scorecard.setVersion(" ");
            validator.validateScorecard(scorecard);
            fail("testValidateScorecard_EmptyVersion is failure.");
        } catch (ValidationException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknow exception occurs in testValidateScorecard_EmptyVersion.");
        }
    }

    /**
     * Tests validateScorecard(Scorecard scorecard) method with invalid Scorecard
     * scorecard, version starts with dot ValidationException should be thrown.
     */
    public void testValidateScorecard_InvalidVersion1() {
        try {
            scorecard.setVersion(".1");
            validator.validateScorecard(scorecard);
            fail("testValidateScorecard_InvalidVersion1 is failure.");
        } catch (ValidationException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknow exception occurs in testValidateScorecard_InvalidVersion1.");
        }
    }

    /**
     * Tests validateScorecard(Scorecard scorecard) method with invalid Scorecard
     * scorecard, version ends with dot ValidationException should be thrown.
     */
    public void testValidateScorecard_InvalidVersion2() {
        try {
            scorecard.setVersion("1.");
            validator.validateScorecard(scorecard);
            fail("testValidateScorecard_InvalidVersion2 is failure.");
        } catch (ValidationException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknow exception occurs in testValidateScorecard_InvalidVersion2.");
        }
    }

    /**
     * Tests validateScorecard(Scorecard scorecard) method with invalid Scorecard
     * scorecard, version is too long. ValidationException should be thrown.
     */
    public void testValidateScorecard_InvalidVersion3() {
        try {
            scorecard.setVersion("1.1111111111111111111");
            validator.validateScorecard(scorecard);
            fail("testValidateScorecard_InvalidVersion3 is failure.");
        } catch (ValidationException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknow exception occurs in testValidateScorecard_InvalidVersion3.");
        }
    }

    /**
     * Tests validateScorecard(Scorecard scorecard) method with invalid Scorecard
     * scorecard, letter is in version. ValidationException should be thrown.
     */
    public void testValidateScorecard_InvalidVersion4() {
        try {
            scorecard.setVersion("1.b");
            validator.validateScorecard(scorecard);
            fail("testValidateScorecard_InvalidVersion4 is failure.");
        } catch (ValidationException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknow exception occurs in testValidateScorecard_InvalidVersion4.");
        }
    }

    /**
     * Tests validateScorecard(Scorecard scorecard) method with invalid Scorecard
     * scorecard, negative MinScore ValidationException should be thrown.
     */
    public void testValidateScorecard_NegativeMinScore() {
        try {
            scorecard.setMinScore(-0.1f);
            validator.validateScorecard(scorecard);
            fail("testValidateScorecard_NegativeMinScore is failure.");
        } catch (ValidationException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknow exception occurs in testValidateScorecard_NegativeMinScore.");
        }
    }

    /**
     * Tests validateScorecard(Scorecard scorecard) method with invalid Scorecard
     * scorecard, zero MinScore.
     *
     * @throws ValidationException to Junit
     */
    public void testValidateScorecard_MinScoreCorrect()
        throws ValidationException {
        scorecard.setMinScore(0);
        validator.validateScorecard(scorecard);
    }

    /**
     * Tests validateScorecard(Scorecard scorecard) method with invalid Scorecard
     * scorecard, group is missing. ValidationException should be thrown.
     */
    public void testValidateScorecard_MissingGroup() {
        try {
            scorecard.clearGroups();
            validator.validateScorecard(scorecard);
            fail("testValidateScorecard_MissingGroup is failure.");
        } catch (ValidationException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknow exception occurs in testValidateScorecard_MissingGroup.");
        }
    }

    /**
     * Tests validateScorecard(Scorecard scorecard) method with invalid Scorecard
     * scorecard, weight of group is invalid. ValidationException should be thrown.
     */
    public void testValidateScorecard_InvalidWeightInGroup() {
        try {
            scorecard.clearGroups();

            Group g = new Group(999993, "Method.", 90.0f);
            g.addSection(section);
            scorecard.addGroup(g);

            validator.validateScorecard(scorecard);
            fail("testValidateScorecard_InvalidWeightInGroup is failure.");
        } catch (ValidationException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknow exception occurs in testValidateScorecard_InvalidWeightInGroup.");
        }
    }

    /**
     * Tests validateScorecard(Scorecard scorecard) method with invalid Scorecard
     * scorecard, id of group is invalid. ValidationException should be thrown.
     *
    public void testValidateScorecard_InvalidIdOfGroup() {
        try {
            scorecard.clearGroups();

            Group g = new Group();
            g.setName("group");
            g.setWeight(100.0f);
            g.addSection(section);
            scorecard.addGroup(g);

            validator.validateScorecard(scorecard);
            fail("testValidateScorecard_InvalidIdOfGroup is failure.");
        } catch (ValidationException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknow exception occurs in testValidateScorecard_InvalidIdOfGroup.");
        }
    }

    /**
     * Tests validateScorecard(Scorecard scorecard) method with invalid Scorecard
     * scorecard, null Name of group ValidationException should be thrown.
     */
    public void testValidateScorecard_NullNameOfGroup() {
        try {
            scorecard.getGroup(0).resetName();
            validator.validateScorecard(scorecard);
            fail("testValidateScorecard_NullNameOfGroup is failure.");
        } catch (ValidationException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknow exception occurs in testValidateScorecard_NullNameOfGroup.");
        }
    }

    /**
     * Tests validateScorecard(Scorecard scorecard) method with invalid Scorecard
     * scorecard, empty Name of group ValidationException should be thrown.
     */
    public void testValidateScorecard_EmptyNameOfGroup() {
        try {
            scorecard.getGroup(0).setName(" ");
            validator.validateScorecard(scorecard);
            fail("testValidateScorecard_EmptyNameOfGroup is failure.");
        } catch (ValidationException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknow exception occurs in testValidateScorecard_EmptyNameOfGroup.");
        }
    }

    /**
     * Tests validateScorecard(Scorecard scorecard) method with invalid Scorecard
     * scorecard, missing section in group ValidationException should be thrown.
     */
    public void testValidateScorecard_MissingSectionGroup() {
        try {
            scorecard.getGroup(0).clearSections();
            validator.validateScorecard(scorecard);
            fail("testValidateScorecard_MissingSectionGroup is failure.");
        } catch (ValidationException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknow exception occurs in testValidateScorecard_MissingSectionGroup.");
        }
    }

    /**
     * Tests validateScorecard(Scorecard scorecard) method with invalid Scorecard
     * scorecard, id of section is invalid. ValidationException should be thrown.
     *
    public void testValidateScorecard_InvalidIdOfSection() {
        try {
            scorecard.getGroup(0).clearSections();

            Section s = new Section();
            s.setName("section");
            s.setWeight(100.0f);
            s.addQuestion(question);

            scorecard.getGroup(0).addSection(s);

            validator.validateScorecard(scorecard);
            fail("testValidateScorecard_InvalidIdOfSection is failure.");
        } catch (ValidationException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknow exception occurs in testValidateScorecard_InvalidIdOfSection.");
        }
    }

    /**
     * Tests validateScorecard(Scorecard scorecard) method with invalid Scorecard
     * scorecard, null Name of section ValidationException should be thrown.
     */
    public void testValidateScorecard_NullNameOfSection() {
        try {
            scorecard.getGroup(0).getSection(0).resetName();
            validator.validateScorecard(scorecard);
            fail("testValidateScorecard_NullNameOfSection is failure.");
        } catch (ValidationException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknow exception occurs in testValidateScorecard_NullNameOfSection.");
        }
    }

    /**
     * Tests validateScorecard(Scorecard scorecard) method with invalid Scorecard
     * scorecard, empty Name of section ValidationException should be thrown.
     */
    public void testValidateScorecard_EmptyNameOfSection() {
        try {
            scorecard.getGroup(0).getSection(0).setName(" ");
            validator.validateScorecard(scorecard);
            fail("testValidateScorecard_EmptyNameOfSection is failure.");
        } catch (ValidationException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknow exception occurs in testValidateScorecard_EmptyNameOfSection.");
        }
    }

    /**
     * Tests validateScorecard(Scorecard scorecard) method with invalid Scorecard
     * scorecard, missing question in section ValidationException should be thrown.
     */
    public void testValidateScorecard_MissingQuestionInSection() {
        try {
            scorecard.getGroup(0).getSection(0).clearQuestions();
            validator.validateScorecard(scorecard);
            fail("testValidateScorecard_MissingQuestionInSection is failure.");
        } catch (ValidationException iae) {
            // pass
        } catch (Exception e) {
            fail(
                "Unknow exception occurs in testValidateScorecard_MissingQuestionInSection.");
        }
    }

    /**
     * Tests validateScorecard(Scorecard scorecard) method with invalid Scorecard
     * scorecard, null description in question ValidationException should be thrown.
     */
    public void testValidateScorecard_NullDescriptionInQuestion() {
        try {
            scorecard.getGroup(0).getSection(0).getQuestion(0).resetDescription();
            validator.validateScorecard(scorecard);
            fail("testValidateScorecard_NullDescriptionInQuestion is failure.");
        } catch (ValidationException iae) {
            // pass
        } catch (Exception e) {
            fail(
                "Unknow exception occurs in testValidateScorecard_NullDescriptionInQuestion.");
        }
    }

    /**
     * Tests validateScorecard(Scorecard scorecard) method with invalid Scorecard
     * scorecard, empty description in question ValidationException should be thrown.
     */
    public void testValidateScorecard_EmptyDescriptionInQuestion() {
        try {
            scorecard.getGroup(0).getSection(0).getQuestion(0).setDescription(" ");
            validator.validateScorecard(scorecard);
            fail("testValidateScorecard_EmptyDescriptionInQuestion is failure.");
        } catch (ValidationException iae) {
            // pass
        } catch (Exception e) {
            fail(
                "Unknow exception occurs in testValidateScorecard_EmptyDescriptionInQuestion.");
        }
    }
}
