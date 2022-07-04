/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.scorecard.data.failuretests;

import com.topcoder.management.scorecard.data.Question;
import com.topcoder.management.scorecard.data.Section;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

/**
 * <p>
 * Failure test cases for Section.
 * </p>
 *
 * @author biotrail
 * @version 1.0
 */
public class SectionFailureTests extends TestCase {
    /**
     * <p>
     * This constant represents the section id for testing.
     * </p>
     */
    private static final long ID = 100;

    /**
     * <p>
     * This constant represents the section name for testing.
     * </p>
     */
    private static final String NAME = "WeightedScorecardStructure";

    /**
     * <p>
     * This constant represents the section weight for testing.
     * </p>
     */
    private static final float WEIGHT = 100f;

    /**
     * <p>
     * Section instance for testing.
     * </p>
     */
    private Section section;

    /**
     * <p>
     * Setup test environment.
     * </p>
     *
     */
    protected void setUp() {
        section = new Section();
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     *
     */
    protected void tearDown() {
        section = null;
    }

    /**
     * <p>
     * Return all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(SectionFailureTests.class);
    }

    /**
     * <p>
     * Tests ctor Section#Section(long) for failure.
     * It tests the case that when id is negative and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor1_NegativeId() {
        try {
            new Section(-45);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor Section#Section(long) for failure.
     * It tests the case that when id is zero and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor1_ZeroId() {
        try {
            new Section(0);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor Section#Section(long,String) for failure.
     * It tests the case that when id is negative and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor2_NegativeId() {
        try {
            new Section(-45, NAME);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor Section#Section(long,String) for failure.
     * It tests the case that when id is zero and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor2_ZeroId() {
        try {
            new Section(0, NAME);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor Section#Section(long,String) for failure.
     * It tests the case that when name is null and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor2_NullName() {
        try {
            new Section(ID, null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor Section#Section(long,float) for failure.
     * It tests the case that when id is negative and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor3_NegativeId() {
        try {
            new Section(-45, WEIGHT);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor Section#Section(long,float) for failure.
     * It tests the case that when id is zero and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor3_ZeroId() {
        try {
            new Section(0, WEIGHT);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor Section#Section(long,float) for failure.
     * It tests the case that when weight is negative and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor3_NegativeWeight() {
        try {
            new Section(ID, -65);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor Section#Section(long,float) for failure.
     * It tests the case that when weight is over 100 and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor3_TooLargeWeight() {
        try {
            new Section(ID, 154);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor Section#Section(long,float) for failure.
     * It tests the case that when weight is NaN and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor3_NaNWeight() {
        try {
            new Section(ID, Float.NaN);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor Section#Section(long,String,float) for failure.
     * It tests the case that when id is negative and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor4_NegativeId() {
        try {
            new Section(-45, NAME, WEIGHT);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor Section#Section(long,String,float) for failure.
     * It tests the case that when id is zero and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor4_ZeroId() {
        try {
            new Section(0, NAME, WEIGHT);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor Section#Section(long,String,float) for failure.
     * It tests the case that when name is null and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor4_NullName() {
        try {
            new Section(ID, null, WEIGHT);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor Section#Section(long,String,float) for failure.
     * It tests the case that when weight is negative and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor4_NegativeWeight() {
        try {
            new Section(ID, NAME, -65);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor Section#Section(long,String,float) for failure.
     * It tests the case that when weight is over 100 and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor4_TooLargeWeight() {
        try {
            new Section(ID, NAME, 154);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor Section#Section(long,String,float) for failure.
     * It tests the case that when weight is NaN and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor4_NaNWeight() {
        try {
            new Section(ID, NAME, Float.NaN);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests Question#addQuestion(Question) for failure.
     * It tests the case that when question is null and expects IllegalArgumentException.
     * </p>
     */
    public void testAddQuestion_NullQuestion() {
        try {
            section.addQuestion(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests Question#addQuestions(Question[]) for failure.
     * It tests the case that when questions is null and expects IllegalArgumentException.
     * </p>
     */
    public void testAddQuestions_NullQuestions() {
        try {
            section.addQuestions(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests Question#addQuestions(Question[]) for failure.
     * It tests the case that when questions contains null question and expects IllegalArgumentException.
     * </p>
     */
    public void testAddQuestions_NullQuestionInQuestions() {
        try {
            section.addQuestions(new Question[] {new Question(ID), null});
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests Question#removeQuestion(Question) for failure.
     * It tests the case that when question is null and expects IllegalArgumentException.
     * </p>
     */
    public void testRemoveQuestion_NullQuestion() {
        try {
            section.removeQuestion(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests Question#removeQuestion(int) for failure.
     * It tests the case that when index is negative and expects IndexOutOfBoundsException.
     * </p>
     */
    public void testRemoveQuestion_NegativeIndex() {
        try {
            section.removeQuestion(-98);
            fail("IndexOutOfBoundsException expected.");
        } catch (IndexOutOfBoundsException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests Question#removeQuestion(int) for failure.
     * It tests the case that when index is too large and expects IndexOutOfBoundsException.
     * </p>
     */
    public void testRemoveQuestion_TooLargeIndex() {
        try {
            section.removeQuestion(8);
            fail("IndexOutOfBoundsException expected.");
        } catch (IndexOutOfBoundsException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests Question#getQuestion(int) for failure.
     * It tests the case that when index is negative and expects IndexOutOfBoundsException.
     * </p>
     */
    public void testGetQuestion_NegativeIndex() {
        try {
            section.getQuestion(-98);
            fail("IndexOutOfBoundsException expected.");
        } catch (IndexOutOfBoundsException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests Question#getQuestion(int) for failure.
     * It tests the case that when index is too large and expects IndexOutOfBoundsException.
     * </p>
     */
    public void testGetQuestion_TooLargeIndex() {
        try {
            section.getQuestion(8);
            fail("IndexOutOfBoundsException expected.");
        } catch (IndexOutOfBoundsException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests Question#removeQuestions(Question[]) for failure.
     * It tests the case that when questions is null and expects IllegalArgumentException.
     * </p>
     */
    public void testRemoveQuestions_NullQuestions() {
        try {
            section.removeQuestions(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests Question#removeQuestions(Question[]) for failure.
     * It tests the case that when questions contains null question and expects IllegalArgumentException.
     * </p>
     */
    public void testRemoveQuestions_NullQuestionInQuestions() {
        try {
            section.removeQuestions(new Question[] {new Question(ID), null});
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests Question#insertQuestion(Question,int) for failure.
     * It tests the case that when question is null and expects IllegalArgumentException.
     * </p>
     */
    public void testInsertQuestion_NullQuestion() {
        try {
            section.insertQuestion(null, 0);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests Question#insertQuestion(Question,int) for failure.
     * It tests the case that when index is negative and expects IndexOutOfBoundsException.
     * </p>
     */
    public void testInsertQuestion_NegativeIndex() {
        try {
            section.insertQuestion(new Question(ID), -98);
            fail("IndexOutOfBoundsException expected.");
        } catch (IndexOutOfBoundsException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests Question#insertQuestion(Question,int) for failure.
     * It tests the case that when index is too large and expects IndexOutOfBoundsException.
     * </p>
     */
    public void testInsertQuestion_TooLargeIndex() {
        try {
            section.insertQuestion(new Question(ID), 98);
            fail("IndexOutOfBoundsException expected.");
        } catch (IndexOutOfBoundsException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests Section#checkWeights(float) for failure.
     * It tests the case that when tolerance is negative and expects IllegalArgumentException.
     * </p>
     */
    public void testCheckWeights_NegativeTolerance() {
        try {
            section.checkWeights(-9f);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests Section#checkWeights(float) for failure.
     * It tests the case that when tolerance is positive infinite and expects IllegalArgumentException.
     * </p>
     */
    public void testCheckWeights_PositiveInfiniteTolerance() {
        try {
            section.checkWeights(Float.POSITIVE_INFINITY);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests Section#checkWeights(float) for failure.
     * It tests the case that when tolerance is NaN and expects IllegalArgumentException.
     * </p>
     */
    public void testCheckWeights_NaNTolerance() {
        try {
            section.checkWeights(Float.NaN);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }
}