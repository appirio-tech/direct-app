/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.scorecard.data.failuretests;

import com.topcoder.management.scorecard.data.ScorecardType;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

/**
 * <p>
 * Failure test cases for ScorecardType.
 * </p>
 *
 * @author biotrail
 * @version 1.0
 */
public class ScorecardTypeFailureTests extends TestCase {
    /**
     * <p>
     * Return all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(ScorecardTypeFailureTests.class);
    }

    /**
     * <p>
     * Tests ctor ScorecardType#ScorecardType(long) for failure.
     * It tests the case that when id is negative and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor1_NegativeId() {
        try {
            new ScorecardType(-98);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor ScorecardType#ScorecardType(long) for failure.
     * It tests the case that when id is zero and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor1_ZeroId() {
        try {
            new ScorecardType(0);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor ScorecardType#ScorecardType(long,String) for failure.
     * It tests the case that when id is negative and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor2_NegativeId() {
        try {
            new ScorecardType(-98, "GroupType");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor ScorecardType#ScorecardType(long,String) for failure.
     * It tests the case that when id is zero and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor2_ZeroId() {
        try {
            new ScorecardType(0, "ScorecardType");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor ScorecardType#ScorecardType(long,String) for failure.
     * It tests the case that when name is null and expects IllegalArgumentException.
     * </p>
     */
    public void testCtor2_NullName() {
        try {
            new ScorecardType(100, null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }
}