/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.team.failuretests;

import java.util.Arrays;

import com.topcoder.management.team.TeamHeader;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Failure test for TeamHeader class.
 *
 * @author King_Bette
 * @version 1.0
 */
public class TeamHeaderFailureTest extends TestCase {
    /**
     * Represents an TeamHeader instance that is used in the test.
     */
    private TeamHeader teamHeader = new TeamHeader();

    /**
     * Aggregates all tests in this class.
     *
     * @return Test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(TeamHeaderFailureTest.class);
    }

    /**
     * Failure test of
     * <code>TeamHeader(String name, boolean finalized, long projectId, long teamId,
     * long captainResourceId, int captainPaymentPercentage, String description)</code>
     * constructor.
     * <p>
     * projectId is negative.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testTeamHeader_failure_negative_projectId() throws Exception {
        try {
            new TeamHeader(null, false, -1, 0, 0, 0, null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of
     * <code>TeamHeader(String name, boolean finalized, long projectId, long teamId,
     * long captainResourceId, int captainPaymentPercentage, String description)</code>
     * constructor.
     * <p>
     * teamId is negative.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testTeamHeader_failure_negative_teamId() throws Exception {
        try {
            new TeamHeader(null, false, 0, -1, 0, 0, null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of
     * <code>TeamHeader(String name, boolean finalized, long projectId, long teamId,
     * long captainResourceId, int captainPaymentPercentage, String description)</code>
     * constructor.
     * <p>
     * captainResourceId is negative.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testTeamHeader_failure_negative_captainResourceId() throws Exception {
        try {
            new TeamHeader(null, false, 0, 0, -1, 0, null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of
     * <code>TeamHeader(String name, boolean finalized, long projectId, long teamId,
     * long captainResourceId, int captainPaymentPercentage, String description)</code>
     * constructor.
     * <p>
     * captainPaymentPercentage is negative.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testTeamHeader_failure_negative_captainPaymentPercentage() throws Exception {
        try {
            new TeamHeader(null, false, 0, 0, 0, -1, null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of
     * <code>TeamHeader(String name, boolean finalized, long projectId, long teamId,
     * long captainResourceId, int captainPaymentPercentage, String description)</code>
     * constructor.
     * <p>
     * captainPaymentPercentage is greater than 100.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testTeamHeader_failure_greater_than_100_captainPaymentPercentage() throws Exception {
        try {
            new TeamHeader(null, false, 0, 0, 0, 101, null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>setProjectId(long projectId)</code> method.
     * <p>
     * projectId is negative.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testSetProjectId_failure_negative_projectId() throws Exception {
        try {
            teamHeader.setProjectId(-1);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>setTeamId(long teamId)</code> method.
     * <p>
     * teamId is negative.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testSetTeamId_failure_negative_teamId() throws Exception {
        try {
            teamHeader.setTeamId(-1);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>setCaptainResourceId(long captainResourceId)</code>
     * method.
     * <p>
     * captainResourceId is negative.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testSetCaptainResourceId_failure_negative_captainResourceId() throws Exception {
        try {
            teamHeader.setCaptainResourceId(-1);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of
     * <code>setCaptainPaymentPercentage(int captainPaymentPercentage)</code>
     * method.
     * <p>
     * captainPaymentPercentage is negative.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testSetCaptainPaymentPercentage_failure_negative_captainPaymentPercentage() throws Exception {
        try {
            teamHeader.setCaptainPaymentPercentage(-1);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of
     * <code>setCaptainPaymentPercentage(int captainPaymentPercentage)</code>
     * method.
     * <p>
     * captainPaymentPercentage is greater than 100.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testSetCaptainPaymentPercentage_failure_greater_than_100_captainPaymentPercentage()
        throws Exception {
        try {
            teamHeader.setCaptainPaymentPercentage(101);
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
            teamHeader.setProperty(null, "test");
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
            teamHeader.setProperty("  ", null);
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
            teamHeader.setProperty(new String(s), null);
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
            teamHeader.getProperty(null);
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
            teamHeader.getProperty("  ");
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
            teamHeader.getProperty(new String(s));
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }
}
