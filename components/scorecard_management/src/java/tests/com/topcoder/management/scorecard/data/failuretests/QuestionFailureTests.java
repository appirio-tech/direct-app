/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.scorecard.data.failuretests;

import com.topcoder.management.scorecard.data.Question;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

/**
 * <p>
 * Failure test cases for Question.
 * </p>
 *
 * @author biotrail
 * @version 1.0
 */
public class QuestionFailureTests extends TestCase {
    /**
     * <p>
     * This constant represents the question id for testing.
     * </p>
     */
    private static final long ID = 100;

    /**
     * <p>
     * This constant represents the question name for testing.
     * </p>
     */
    private static final String NAME = "Question";

    /**
     * <p>
     * This constant represents the question weight for testing.
     * </p>
     */
    private static final float WEIGHT = 100f;

    /**
     * <p>
     * Return all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(QuestionFailureTests.class);
    }

    /**
     * <p>
     * Tests ctor Question#Question(long) for failure.
     * It tests the case that when id is negative and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor1_NegativeId() {
        try {
            new Question(-45);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor Question#Question(long) for failure.
     * It tests the case that when id is zero and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor1_ZeroId() {
        try {
            new Question(0);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor Question#Question(long,String) for failure.
     * It tests the case that when id is negative and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor2_NegativeId() {
        try {
            new Question(-45, NAME);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor Question#Question(long,String) for failure.
     * It tests the case that when id is zero and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor2_ZeroId() {
        try {
            new Question(0, NAME);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor Question#Question(long,String) for failure.
     * It tests the case that when name is null and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor2_NullName() {
        try {
            new Question(ID, null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor Question#Question(long,float) for failure.
     * It tests the case that when id is negative and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor3_NegativeId() {
        try {
            new Question(-45, WEIGHT);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor Question#Question(long,float) for failure.
     * It tests the case that when id is zero and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor3_ZeroId() {
        try {
            new Question(0, WEIGHT);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor Question#Question(long,float) for failure.
     * It tests the case that when weight is negative and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor3_NegativeWeight() {
        try {
            new Question(ID, -65);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor Question#Question(long,float) for failure.
     * It tests the case that when weight is over 100 and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor3_TooLargeWeight() {
        try {
            new Question(ID, 154);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor Question#Question(long,float) for failure.
     * It tests the case that when weight is NaN and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor3_NaNWeight() {
        try {
            new Question(ID, Float.NaN);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor Question#Question(long,String,float) for failure.
     * It tests the case that when id is negative and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor4_NegativeId() {
        try {
            new Question(-45, NAME, WEIGHT);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor Question#Question(long,String,float) for failure.
     * It tests the case that when id is zero and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor4_ZeroId() {
        try {
            new Question(0, NAME, WEIGHT);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor Question#Question(long,String,float) for failure.
     * It tests the case that when name is null and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor4_NullName() {
        try {
            new Question(ID, null, WEIGHT);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor Question#Question(long,String,float) for failure.
     * It tests the case that when weight is negative and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor4_NegativeWeight() {
        try {
            new Question(ID, NAME, -65);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor Question#Question(long,String,float) for failure.
     * It tests the case that when weight is over 100 and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor4_TooLargeWeight() {
        try {
            new Question(ID, NAME, 154);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor Question#Question(long,String,float) for failure.
     * It tests the case that when weight is NaN and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor4_NaNWeight() {
        try {
            new Question(ID, NAME, Float.NaN);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor Question#setUploadDocument(boolean) for failure.
     * It tests the case that when uploadDocument is false and uploadRequired is true and expects IllegalStateException.
     * </p>
     */
    public void testSetUploadDocumentForISE() {
        Question question = new Question(125);
        question.setUploadRequired(true);

        try {
            question.setUploadDocument(false);
            fail("IllegalStateException expected.");
        } catch (IllegalStateException e) {
            // good.
        }
    }

    /**
     * <p>
     * Tests ctor Question#resetUploadDocument() for failure.
     * It tests the case that when uploadDocument is false and uploadRequired is true and expects IllegalStateException.
     * </p>
     */
    public void testResetUploadDocumentForISE() {
        Question question = new Question(125);
        question.setUploadRequired(true);

        try {
            question.resetUploadDocument();
            fail("IllegalStateException expected.");
        } catch (IllegalStateException e) {
            // good.
        }
    }
}