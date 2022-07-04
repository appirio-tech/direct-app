/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.management.scorecard.data;

import java.util.Arrays;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Contains the unit tests for the {@link Util} class.
 *
 * @author      UFP2161
 * @copyright   Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 * @version     1.0
 */
public class UtilTest extends TestCase {

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Static Fields

    /**
     * A list that sums to exactly 100.
     */
    private static final List LIST_GOOD = Arrays.asList(new Question[] {
        new Question(1, 30.0f), new Question(2, 30.0f), new Question(3, 40.0f)
    });

    /**
     * A list that doesn't sum to exactly 100.
     */
    private static final List LIST_BAD = Arrays.asList(new Question[] {
        new Question(1, 30.0f), new Question(2, 30.0f)
    });

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Static Methods

    /**
     * Creates a test suite for the tests in this test case.
     *
     * @return  A TestSuite for this test case.
     */
    public static Test suite() {
        return new TestSuite(UtilTest.class);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // checkArrayNotNull Tests

    /**
     * Ensures that the checkArrayNotNull method throws an IllegalArgumentException when given a null
     * array.
     */
    public void testCheckArrayNotNullThrowsOnNullArray() {
        try {
            Util.checkArrayNotNull((Object[]) null, "argument");
            fail("An IllegalArgumentException is expected when given a null array.");
        } catch (IllegalArgumentException ex) {
            // Good!
        }
    }

    /**
     * Ensures that the checkArrayNotNull method throws an IllegalArgumentException when given an
     * array with a null element.
     */
    public void testCheckArrayNotNullThrowsOnNullElement() {
        try {
            Util.checkArrayNotNull(new Object[] {null}, "argument");
            fail("An IllegalArgumentException is expected when given an array with a null element.");
        } catch (IllegalArgumentException ex) {
            // Good!
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // checkNotNonPositive Tests

    /**
     * Ensures that the checkNotNonPositive method throws an IllegalArgumentException when given a
     * negative value.
     */
    public void testCheckNotNonPositiveThrowsOnNegative() {
        try {
            Util.checkNotNonPositive(-1, "argument");
            fail("An IllegalArgumentException is expected when given a negative value.");
        } catch (IllegalArgumentException ex) {
            // Good!
        }
    }

    /**
     * Ensures that the checkNotNonPositive method throws an IllegalArgumentException when given a
     * zero value.
     */
    public void testCheckNotNonPositiveThrowsOnZero() {
        try {
            Util.checkNotNonPositive(0, "argument");
            fail("An IllegalArgumentException is expected when given a zero value.");
        } catch (IllegalArgumentException ex) {
            // Good!
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // checkNotNull Tests

    /**
     * Ensures that the checkNotNull method throws an IllegalArgumentException when given a null.
     */
    public void testCheckNotNullThrowsOnNull() {
        try {
            Util.checkNotNull(null, "argument");
            fail("An IllegalArgumentException is expected when given a null.");
        } catch (IllegalArgumentException ex) {
            // Good!
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // checkWeights Tests

    /**
     * Ensures that the checkWeights method throws an IllegalArgumentException when given a
     * null list.
     */
    public void testCheckWeightsThrowsOnNullList() {
        checkCheckWeightsThrowsIAE(null, 1e-9f, " the list is null.");
    }

    /**
     * Ensures that the checkWeights method throws an IllegalArgumentException when given a
     * negative real number.
     */
    public void testCheckWeightsThrowsOnNegative() {
        checkCheckWeightsThrowsIAE(LIST_GOOD, -0.01f, " the tolerance is negative.");
    }

    /**
     * Ensures that the checkWeights method throws an IllegalArgumentException when given
     * negative infinity (Float.NEGATIVE_INFINITY).
     */
    public void testCheckWeightsThrowsOnNegativeInfinity() {
        checkCheckWeightsThrowsIAE(LIST_GOOD, Float.NEGATIVE_INFINITY, " the tolerance is negative infinity.");
    }

    /**
     * Ensures that the checkWeights method throws an IllegalArgumentException when given
     * positive infinity (Float.POSITIVE_INFINITY).
     */
    public void testCheckWeightsThrowsOnPositiveInfinity() {
        checkCheckWeightsThrowsIAE(LIST_GOOD, Float.POSITIVE_INFINITY, " the tolerance is positive infinity.");
    }

    /**
     * Ensures that the checkWeights method throws an IllegalArgumentException when given
     * not a number (Float.NaN).
     */
    public void testCheckWeightsThrowsOnNaN() {
        checkCheckWeightsThrowsIAE(LIST_GOOD, Float.NaN, " the tolerance is not a number (NaN).");
    }

    /**
     * Ensures that the checkWeights method throws an IllegalArgumentException when given the
     * specified arguments.
     *
     * @param   list
     *          The list that should generate an IAE being thrown.
     * @param   tolerance
     *          The tolerance that should generate an IAE being thrown.
     * @param   message
     *          The message to use in the error message.
     */
    private void checkCheckWeightsThrowsIAE(List list, float tolerance, String message) {
        try {
            Util.checkWeights(list, tolerance);
            fail("An IllegalArgumentException is expected when " + message);
        } catch (IllegalArgumentException ex) {
            // Good!
        }
    }

    /**
     * Ensures that the checkWeights method returns true when the weights add up to 100.
     */
    public void testCheckWeightsReturnsTrue() {
        assertTrue(
                "The weights add up to 100, so true should be returned.",
                Util.checkWeights(LIST_GOOD, 1e-9f));
    }

    /**
     * Ensures that the checkWeights method returns true when the weights add up to 100 (plus or
     * minus a large tolerance).
     */
    public void testCheckWeightsReturnsTrueHighTolerance() {
        assertTrue(
                "The weights add up to 100 +/- 50, so true should be returned.",
                Util.checkWeights(LIST_BAD, 50f));
    }

    /**
     * Ensures that the checkWeights method returns false when the weights don't add up to 100.
     */
    public void testCheckWeightsReturnsFalse() {
        assertFalse(
                "The weights don't add up to 100, so false should be returned.",
                Util.checkWeights(LIST_BAD, 1e-9f));
    }
}
