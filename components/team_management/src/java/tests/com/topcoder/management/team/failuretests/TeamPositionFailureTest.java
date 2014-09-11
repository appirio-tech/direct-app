/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.team.failuretests;

import java.util.Arrays;

import com.topcoder.management.team.TeamPosition;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Failure test for TeamPosition class.
 *
 * @author King_Bette
 * @version 1.0
 */
public class TeamPositionFailureTest extends TestCase {
    /**
     * Represents the TeamPosition instance that is used in the test.
     */
    private TeamPosition teamPosition = new TeamPosition();

    /**
     * Aggregates all tests in this class.
     *
     * @return Test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(TeamPositionFailureTest.class);
    }

    /**
     * Failure test of
     * <code>TeamPosition(String description, boolean filled, long memberResourceId,
     * int paymentPercentage, String name, long positionId, boolean published)</code>
     * constructor.
     * <p>
     * memberResourceId is less than -1.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testTeamPosition_failure_less_than_minus1_memberResourceId() throws Exception {
        try {
            new TeamPosition(null, false, -2, 0, null, 0, false);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of
     * <code>TeamPosition(String description, boolean filled, long memberResourceId,
     * int paymentPercentage, String name, long positionId, boolean published)</code>
     * constructor.
     * <p>
     * paymentPercentage is negative.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testTeamPosition_failure_negative_paymentPercentage() throws Exception {
        try {
            new TeamPosition(null, false, -1, -1, null, 0, false);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of
     * <code>TeamPosition(String description, boolean filled, long memberResourceId,
     * int paymentPercentage, String name, long positionId, boolean published)</code>
     * constructor.
     * <p>
     * paymentPercentage is greater than 100.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testTeamPosition_failure_greater_than_100_paymentPercentage() throws Exception {
        try {
            new TeamPosition(null, false, -1, 101, null, 0, false);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of
     * <code>TeamPosition(String description, boolean filled, long memberResourceId,
     * int paymentPercentage, String name, long positionId, boolean published)</code>
     * constructor.
     * <p>
     * positionId is negative.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testTeamPosition_failure_negative_positionId() throws Exception {
        try {
            new TeamPosition(null, false, -1, 0, null, -1, false);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>setMemberResourceId(long memberResourceId)</code>
     * method.
     * <p>
     * memberResourceId is less than -1.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testSetMemberResourceId_failure_less_than_minus1_memberResourceId() throws Exception {
        try {
            teamPosition.setMemberResourceId(-2);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>setPaymentPercentage(int paymentPercentage)</code>
     * method.
     * <p>
     * paymentPercentage is negative.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testSetPaymentPercentage_failure_negative_paymentPercentage() throws Exception {
        try {
            teamPosition.setPaymentPercentage(-1);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>setPaymentPercentage(int paymentPercentage)</code>
     * method.
     * <p>
     * paymentPercentage is greater than 100.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testSetPaymentPercentage_failure_greater_than_100_paymentPercentage() throws Exception {
        try {
            teamPosition.setPaymentPercentage(101);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>setPositionId(long positionId)</code> method.
     * <p>
     * positionId is negative.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testSetPositionId_failure_negative_positionId() throws Exception {
        try {
            teamPosition.setPositionId(-1);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>setProperty(String key, Serializable value)</code>
     * method.
     * <p>
     * key is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testSetProperty_failure_null_key() throws Exception {
        try {
            teamPosition.setProperty(null, "test");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>setProperty(String key, Serializable value)</code>
     * method.
     * <p>
     * key is empty.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testSetProperty_failure_empty_key() throws Exception {
        try {
            teamPosition.setProperty("  ", null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>setProperty(String key, Serializable value)</code>
     * method.
     * <p>
     * key is longer than 255.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testSetProperty_failure_longer_than_255_key() throws Exception {
        char[] s = new char[256];
        Arrays.fill(s, 'a');
        try {
            teamPosition.setProperty(new String(s), null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>getProperty(String key)</code> method.
     * <p>
     * key is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testGetProperty_failure_null_key() throws Exception {
        try {
            teamPosition.getProperty(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>getProperty(String key)</code> method.
     * <p>
     * key is empty.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testGetProperty_failure_empty_key() throws Exception {
        try {
            teamPosition.getProperty("  ");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>getProperty(String key, Serializable value)</code>
     * method.
     * <p>
     * key is longer than 255.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testGetProperty_failure_longer_than_255_key() throws Exception {
        char[] s = new char[256];
        Arrays.fill(s, 'a');
        try {
            teamPosition.getProperty(new String(s));
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }
}
