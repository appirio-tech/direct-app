/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.scorecard.accuracytests;

import com.topcoder.management.scorecard.data.Scorecard;
import com.topcoder.management.scorecard.data.ScorecardStatus;
import com.topcoder.management.scorecard.data.ScorecardType;
import com.topcoder.management.scorecard.validation.DefaultScorecardValidator;
import com.topcoder.management.scorecard.validation.ValidationException;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * Accuracy test cases for class DefaultScorecardValidator.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AccuracyTestDefaultScorecardValidator extends TestCase {
    /** The string with the length 65 used for test. */
    private static final String INVALID_STRING;

    static {
        StringBuffer buffer = new StringBuffer(65);

        for (int i = 0; i < 13; i++) {
            buffer.append("abcde");
        }

        INVALID_STRING = buffer.toString();
    }

    /** The instance of DefaultScorecardValidator used for test. */
    private DefaultScorecardValidator validator = null;

    /** The instance of scorecard used for test. */
    private Scorecard scorecard = null;

    /**
     * Returns the suit to run the test.
     *
     * @return suite to run the test
     */
    public static Test suite() {
        return new TestSuite(AccuracyTestDefaultScorecardValidator.class);
    }

    /**
     * Set up the test environment.
     *
     * @throws Exception exception to JUnit.
     *
     */
    public void setUp() throws Exception {
        validator = new DefaultScorecardValidator("test");

        scorecard = AccuracyTestHelper.createScorecard(new ScorecardStatus(111), new ScorecardType(222), "444", "5.5",
                333, 0.0f, 100.0f);
    }

    /**
     * Tests the constructor.
     */
    public void testConstructor() {
        assertNotNull("constructor error.", validator);
    }

    /**
     * Tests the method validateScorecard(Scorecard scorecard) with legal scorecard.
     *
     * @throws Exception to JUnit.
     */
    public void testValidateScorecardAccuracy() throws Exception {
        try {
            validator.validateScorecard(scorecard);
        } catch (ValidationException e) {
            fail("ValidationException expected.");
        }
    }

    /**
     * Tests the method validateScorecard(Scorecard scorecard) with invalid status id.
     *
     * @throws Exception to JUnit.
     *
    public void testValidateScorecardInvalidStatusID()
        throws Exception {
        scorecard.setScorecardStatus(new ScorecardStatus());

        try {
            validator.validateScorecard(scorecard);
            fail("ValidationException expected.");
        } catch (ValidationException e) {
            // expected
        }
    }*/

    /**
     * Tests the method validateScorecard(Scorecard scorecard) with invalid scorecard type id.
     *
     * @throws Exception to JUnit.
     *
    public void testValidateScorecardInvalidTypeID() throws Exception {
        scorecard.setScorecardType(new ScorecardType());

        try {
            validator.validateScorecard(scorecard);
            fail("ValidationException expected.");
        } catch (ValidationException e) {
            // expected
        }
    }

    /**
     * Tests the method validateScorecard(Scorecard scorecard) with invalid category.
     *
     * @throws Exception to JUnit.
     */
    public void testValidateScorecardInvalidCategory()
        throws Exception {
        scorecard.resetCategory();

        try {
            validator.validateScorecard(scorecard);
            fail("ValidationException expected.");
        } catch (ValidationException e) {
            // expected
        }
    }

    /**
     * Tests the method validateScorecard(Scorecard scorecard) with empty name.
     *
     * @throws Exception to JUnit.
     */
    public void testValidateScorecardEmptyName() throws Exception {
        scorecard.setName("");

        try {
            validator.validateScorecard(scorecard);
            fail("ValidationException expected.");
        } catch (ValidationException e) {
            // expected
        }
    }

    /**
     * Tests the method validateScorecard(Scorecard scorecard) with name of the length 65.
     *
     * @throws Exception to JUnit.
     */
    public void testValidateScorecardLongName() throws Exception {
        scorecard.setName(INVALID_STRING);

        try {
            validator.validateScorecard(scorecard);
            fail("ValidationException expected.");
        } catch (ValidationException e) {
            // expected
        }
    }

    /**
     * Tests the method validateScorecard(Scorecard scorecard) with version of the length 17.
     *
     * @throws Exception to JUnit.
     */
    public void testValidateScorecardLongVersion() throws Exception {
        scorecard.setVersion("12345678901234567");

        try {
            validator.validateScorecard(scorecard);
            fail("ValidationException expected.");
        } catch (ValidationException e) {
            // expected
        }
    }

    /**
     * Tests the method validateScorecard(Scorecard scorecard) with version empty.
     *
     * @throws Exception to JUnit.
     */
    public void testValidateScorecardEmptyVersion() throws Exception {
        scorecard.setVersion("");

        try {
            validator.validateScorecard(scorecard);
            fail("ValidationException expected.");
        } catch (ValidationException e) {
            // expected
        }
    }

    /**
     * Tests the method validateScorecard(Scorecard scorecard) with invalid version.
     *
     * @throws Exception to JUnit.
     */
    public void testValidateScorecardInvalidVersion1()
        throws Exception {
        scorecard.setVersion(".33");

        try {
            validator.validateScorecard(scorecard);
            fail("ValidationException expected.");
        } catch (ValidationException e) {
            // expected
        }
    }

    /**
     * Tests the method validateScorecard(Scorecard scorecard) with invalid version.
     *
     * @throws Exception to JUnit.
     */
    public void testValidateScorecardInvalidVersion2()
        throws Exception {
        scorecard.setVersion("33.");

        try {
            validator.validateScorecard(scorecard);
            fail("ValidationException expected.");
        } catch (ValidationException e) {
            // expected
        }
    }

    /**
     * Tests the method validateScorecard(Scorecard scorecard) with invalid version.
     *
     * @throws Exception to JUnit.
     */
    public void testValidateScorecardInvalidVersion3()
        throws Exception {
        scorecard.setVersion("a.3");

        try {
            validator.validateScorecard(scorecard);
            fail("ValidationException expected.");
        } catch (ValidationException e) {
            // expected
        }
    }

    /**
     * Tests the method validateScorecard(Scorecard scorecard) with invlid minscore.
     *
     * @throws Exception to JUnit.
     */
    public void testValidateScorecardInvlidMinScore() throws Exception {
        scorecard.setMinScore(-1);

        try {
            validator.validateScorecard(scorecard);
            fail("ValidationException expected.");
        } catch (ValidationException e) {
            // expected
        }
    }

    /**
     * Tests the method validateScorecard(Scorecard scorecard) with invlid maxscore.
     *
     * @throws Exception to JUnit.
     */
    public void testValidateScorecardInvlidMaxScore() throws Exception {
        scorecard.setMaxScore(scorecard.getMinScore());

        try {
            validator.validateScorecard(scorecard);
            fail("ValidationException expected.");
        } catch (ValidationException e) {
            // expected
        }
    }

    /**
     * Tests the method validateScorecard(Scorecard scorecard) with long group name of the length 65.
     *
     * @throws Exception to JUnit.
     */
    public void testValidateScorecardLongGroupName() throws Exception {
        scorecard.getGroup(2).setName(INVALID_STRING);

        try {
            validator.validateScorecard(scorecard);
            fail("ValidationException expected.");
        } catch (ValidationException e) {
            // expected
        }
    }

    /**
     * Tests the method validateScorecard(Scorecard scorecard) with invalid group weights.
     *
     * @throws Exception to JUnit.
     */
    public void testValidateScorecardInvalidGroupWeight1()
        throws Exception {
        scorecard.getGroup(1).setWeight(100);

        try {
            validator.validateScorecard(scorecard);
            fail("ValidationException expected.");
        } catch (ValidationException e) {
            // expected
        }
    }

    /**
     * Tests the method validateScorecard(Scorecard scorecard) with invalid group weights.
     *
     * @throws Exception to JUnit.
     */
    public void testValidateScorecardInvalidGroupWeight2()
        throws Exception {
        scorecard.getGroup(1).setWeight(0);

        try {
            validator.validateScorecard(scorecard);
            fail("ValidationException expected.");
        } catch (ValidationException e) {
            // expected
        }
    }

    /**
     * Tests the method validateScorecard(Scorecard scorecard) with invalid sum of group weights.
     *
     * @throws Exception to JUnit.
     */
    public void testValidateScorecardInvalidGroupWeightSum()
        throws Exception {
        scorecard.clearGroups();

        scorecard.addGroups(AccuracyTestHelper.createGroupArray(20.0f, 20.0f, 61.0f));

        try {
            validator.validateScorecard(scorecard);
            fail("ValidationException expected.");
        } catch (ValidationException e) {
            // expected
        }
    }

    /**
     * Tests the method validateScorecard(Scorecard scorecard) with section name of the length 65.
     *
     * @throws Exception to JUnit.
     */
    public void testValidateScorecardLongSectionName()
        throws Exception {
        scorecard.getGroup(2).getSection(2).setName(INVALID_STRING);

        try {
            validator.validateScorecard(scorecard);
            fail("ValidationException expected.");
        } catch (ValidationException e) {
            // expected
        }
    }

    /**
     * Tests the method validateScorecard(Scorecard scorecard) with invalid section weight.
     *
     * @throws Exception to JUnit.
     */
    public void testValidateScorecardInvalidSectionWeight1()
        throws Exception {
        scorecard.getGroup(2).getSection(2).setWeight(100.0f);

        try {
            validator.validateScorecard(scorecard);
            fail("ValidationException expected.");
        } catch (ValidationException e) {
            // expected
        }
    }

    /**
     * Tests the method validateScorecard(Scorecard scorecard) with invalid section weight.
     *
     * @throws Exception to JUnit.
     */
    public void testValidateScorecardInvalidSectionWeight2()
        throws Exception {
        scorecard.getGroup(1).getSection(0).setWeight(0.0f);

        try {
            validator.validateScorecard(scorecard);
            fail("ValidationException expected.");
        } catch (ValidationException e) {
            // expected
        }
    }

    /**
     * Tests the method validateScorecard(Scorecard scorecard) with invalid sum of section weights.
     *
     * @throws Exception to JUnit.
     */
    public void testValidateScorecardInvalidSectionWeightSum()
        throws Exception {
        scorecard.getGroup(2).clearSections();
        scorecard.getGroup(2).addSections(AccuracyTestHelper.createSectionArray(10.0f, 30.0f, 60.1f));

        try {
            validator.validateScorecard(scorecard);
            fail("ValidationException expected.");
        } catch (ValidationException e) {
            // expected
        }
    }

    /**
     * Tests the method validateScorecard(Scorecard scorecard) with empty question description.
     *
     * @throws Exception to JUnit.
     */
    public void testValidateScorecardEmptyQuestionDescription()
        throws Exception {
        scorecard.getGroup(2).getSection(1).getQuestion(0).setDescription("");

        try {
            validator.validateScorecard(scorecard);
            fail("ValidationException expected.");
        } catch (ValidationException e) {
            // expected
        }
    }

    /**
     * Tests the method validateScorecard(Scorecard scorecard) with invalid question weight.
     *
     * @throws Exception to JUnit.
     */
    public void testValidateScorecardInvalidQuestionWeight1()
        throws Exception {
        scorecard.getGroup(2).getSection(1).getQuestion(0).setWeight(100);

        try {
            validator.validateScorecard(scorecard);
            fail("ValidationException expected.");
        } catch (ValidationException e) {
            // expected
        }
    }

    /**
     * Tests the method validateScorecard(Scorecard scorecard) with invalid question weight.
     *
     * @throws Exception to JUnit.
     */
    public void testValidateScorecardInvalidQuestionWeight2()
        throws Exception {
        scorecard.getGroup(2).getSection(1).getQuestion(0).setWeight(0);

        try {
            validator.validateScorecard(scorecard);
            fail("ValidationException expected.");
        } catch (ValidationException e) {
            // expected
        }
    }

    /**
     * Tests the method validateScorecard(Scorecard scorecard) with invalid sum of question weights.
     *
     * @throws Exception to JUnit.
     */
    public void testValidateScorecardInvalidQuestionWeightSum()
        throws Exception {
        scorecard.getGroup(2).getSection(1).clearQuestions();
        scorecard.getGroup(2).getSection(1).addQuestions(AccuracyTestHelper.createQuestionArray(30.0f, 60.0f, 9.9f));

        try {
            validator.validateScorecard(scorecard);
            fail("ValidationException expected.");
        } catch (ValidationException e) {
            // expected
        }
    }
}
