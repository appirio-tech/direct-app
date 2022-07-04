/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.team;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * UnitTest for TeamHeader class. Getters will be tested throughout this test class, so no special test methods are
 * designed for those getters.
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class TeamHeaderUnitTest extends TestCase {
    /** Provide a header for testing. */
    private TeamHeader header = new TeamHeader();

    /**
     * <p>
     * Aggregates all tests in this class.
     * </p>
     * @return Test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(TeamHeaderUnitTest.class);
    }

    /**
     * Accuracy test of <code>TeamHeader()</code> constructor.
     * <p>
     * Call this constructor and then check the inner fields.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testTeamHeader1_Accuracy() throws Exception {
        header = new TeamHeader();
        TestHelper.assertTeamHeader(header, null, false, -1, -1, -1, -1, null, new HashMap());
    }

    /**
     * Accuracy test of <code>TeamHeader(String, boolean, long, long, long, int, String)</code> constructor.
     * <p>
     * Call this constructor and then check the inner fields.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testTeamHeader2_Accuracy1() throws Exception {
        header = new TeamHeader("a", true, 1, 2, 3, 4, "b");
        TestHelper.assertTeamHeader(header, "a", true, 1, 2, 3, 4, "b", new HashMap());
    }

    /**
     * Accuracy test of <code>TeamHeader(String, boolean, long, long, long, int, String)</code> constructor.
     * <p>
     * Call this constructor with null name and description.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testTeamHeader2_Accuracy2() throws Exception {
        header = new TeamHeader(null, true, 1, 2, 3, 4, null);
        TestHelper.assertTeamHeader(header, null, true, 1, 2, 3, 4, null, new HashMap());
    }

    /**
     * Accuracy test of <code>TeamHeader(String, boolean, long, long, long, int, String)</code> constructor.
     * <p>
     * Call this constructor with 0s.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testTeamHeader2_Accuracy3() throws Exception {
        header = new TeamHeader("a", true, 0, 0, 0, 0, "b");
        TestHelper.assertTeamHeader(header, "a", true, 0, 0, 0, 0, "b", new HashMap());
    }

    /**
     * Accuracy test of <code>TeamHeader(String, boolean, long, long, long, int, String)</code> constructor.
     * <p>
     * Call this constructor with 100 as percentage.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testTeamHeader2_Accuracy4() throws Exception {
        header = new TeamHeader("a", true, 0, 0, 0, 100, "b");
        TestHelper.assertTeamHeader(header, "a", true, 0, 0, 0, 100, "b", new HashMap());
    }

    /**
     * Failure test of <code>TeamHeader(String, boolean, long, long, long, int, String)</code> constructor.
     * <p>
     * Call this constructor with negative projectId.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testTeamHeader2_Failure1() throws Exception {
        try {
            new TeamHeader("a", true, -1, 2, 3, 4, "b");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>TeamHeader(String, boolean, long, long, long, int, String)</code> constructor.
     * <p>
     * Call this constructor with negative teamId.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testTeamHeader2_Failure2() throws Exception {
        try {
            new TeamHeader("a", true, 1, -2, 3, 4, "b");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>TeamHeader(String, boolean, long, long, long, int, String)</code> constructor.
     * <p>
     * Call this constructor with negative captainResourceId.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testTeamHeader2_Failure3() throws Exception {
        try {
            new TeamHeader("a", true, 1, 2, -3, 4, "b");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>TeamHeader(String, boolean, long, long, long, int, String)</code> constructor.
     * <p>
     * Call this constructor with negative captainPaymentPercentage.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testTeamHeader2_Failure4() throws Exception {
        try {
            new TeamHeader("a", true, 1, 2, 3, -1, "b");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>TeamHeader(String, boolean, long, long, long, int, String)</code> constructor.
     * <p>
     * Call this constructor with captainPaymentPercentage not between 0 and 100.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testTeamHeader2_Failure5() throws Exception {
        try {
            new TeamHeader("a", true, 1, 2, 3, 101, "b");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>setName(String name)</code> method.
     * <p>
     * Call this method with null.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSetNameAccuracy1() throws Exception {
        header.setName(null);
        TestHelper.assertTeamHeader(header, null, false, -1, -1, -1, -1, null, new HashMap());
    }

    /**
     * Accuracy test of <code>setName(String name)</code> method.
     * <p>
     * Call this method with empty string.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSetNameAccuracy2() throws Exception {
        header.setName("");
        TestHelper.assertTeamHeader(header, "", false, -1, -1, -1, -1, null, new HashMap());
    }

    /**
     * Accuracy test of <code>setName(String name)</code> method.
     * <p>
     * Call this method with a simple string.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSetNameAccuracy3() throws Exception {
        header.setName("string");
        TestHelper.assertTeamHeader(header, "string", false, -1, -1, -1, -1, null, new HashMap());
    }

    /**
     * Accuracy test of <code>setFinalized(boolean finalized)</code> method.
     * <p>
     * Set true finalized.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSetFinalizedAccuracy1() throws Exception {
        header.setFinalized(true);
        TestHelper.assertTeamHeader(header, null, true, -1, -1, -1, -1, null, new HashMap());
    }

    /**
     * Accuracy test of <code>setFinalized(boolean finalized)</code> method.
     * <p>
     * Set false finalized.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSetFinalizedAccuracy2() throws Exception {
        header.setFinalized(true);
        header.setFinalized(false);
        TestHelper.assertTeamHeader(header, null, false, -1, -1, -1, -1, null, new HashMap());
    }

    /**
     * Accuracy test of <code>setProjectId(long projectId)</code> method.
     * <p>
     * Set a sample project id.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSetProjectIdAccuracy1() throws Exception {
        header.setProjectId(7);
        TestHelper.assertTeamHeader(header, null, false, 7, -1, -1, -1, null, new HashMap());
    }

    /**
     * Accuracy test of <code>setProjectId(long projectId)</code> method.
     * <p>
     * Set a zero project id.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSetProjectIdAccuracy2() throws Exception {
        header.setProjectId(0);
        TestHelper.assertTeamHeader(header, null, false, 0, -1, -1, -1, null, new HashMap());
    }

    /**
     * Failure test of <code>setProjectId(long projectId)</code> method.
     * <p>
     * Call this method with negative.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSetProjectIdFailure() throws Exception {
        try {
            header.setProjectId(-1);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>setTeamId(long teamId)</code> method.
     * <p>
     * Set a sample team id.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSetTeamIdAccuracy1() throws Exception {
        header.setTeamId(7);
        TestHelper.assertTeamHeader(header, null, false, -1, 7, -1, -1, null, new HashMap());
    }

    /**
     * Accuracy test of <code>setTeamId(long teamId)</code> method.
     * <p>
     * Set a zero team id.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSetTeamIdAccuracy2() throws Exception {
        header.setTeamId(0);
        TestHelper.assertTeamHeader(header, null, false, -1, 0, -1, -1, null, new HashMap());
    }

    /**
     * Failure test of <code>setTeamId(long teamId)</code> method.
     * <p>
     * Set negative teamId.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSetTeamIdFailure() throws Exception {
        try {
            header.setTeamId(-1);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>setCaptainResourceId(long captainResourceId)</code> method.
     * <p>
     * Set a sample captainResourceId.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSetCaptainResourceIdAccuracy1() throws Exception {
        header.setCaptainResourceId(7);
        TestHelper.assertTeamHeader(header, null, false, -1, -1, 7, -1, null, new HashMap());
    }

    /**
     * Accuracy test of <code>setCaptainResourceId(long captainResourceId)</code> method.
     * <p>
     * Set a zero captainResourceId.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSetCaptainResourceIdAccuracy2() throws Exception {
        header.setCaptainResourceId(0);
        TestHelper.assertTeamHeader(header, null, false, -1, -1, 0, -1, null, new HashMap());
    }

    /**
     * Failure test of <code>setCaptainResourceId(long captainResourceId)</code> method.
     * <p>
     * Set a negative captainResourceId.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSetCaptainResourceIdFailure() throws Exception {
        try {
            header.setCaptainResourceId(-1);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>setCaptainPaymentPercentage(int captainPaymentPercentage)</code> method.
     * <p>
     * Set zero percentage.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSetCaptainPaymentPercentageAccuracy1() throws Exception {
        header.setCaptainPaymentPercentage(0);
        TestHelper.assertTeamHeader(header, null, false, -1, -1, -1, 0, null, new HashMap());
    }

    /**
     * Accuracy test of <code>setCaptainPaymentPercentage(int captainPaymentPercentage)</code> method.
     * <p>
     * Set 40 percentage.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSetCaptainPaymentPercentageAccuracy2() throws Exception {
        header.setCaptainPaymentPercentage(40);
        TestHelper.assertTeamHeader(header, null, false, -1, -1, -1, 40, null, new HashMap());
    }

    /**
     * Accuracy test of <code>setCaptainPaymentPercentage(int captainPaymentPercentage)</code> method.
     * <p>
     * Set 100 percentage.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSetCaptainPaymentPercentageAccuracy3() throws Exception {
        header.setCaptainPaymentPercentage(100);
        TestHelper.assertTeamHeader(header, null, false, -1, -1, -1, 100, null, new HashMap());
    }

    /**
     * Failure test of <code>setCaptainPaymentPercentage(int captainPaymentPercentage)</code> method.
     * <p>
     * Set negative percentage.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSetCaptainPaymentPercentageFailure1() throws Exception {
        try {
            header.setCaptainPaymentPercentage(-1);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>setCaptainPaymentPercentage(int captainPaymentPercentage)</code> method.
     * <p>
     * Set 101 percentage.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSetCaptainPaymentPercentageFailure2() throws Exception {
        try {
            header.setCaptainPaymentPercentage(101);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>setProperty(String key, Serializable value)</code> method.
     * <p>
     * Set several properties and check the correctness.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSetPropertyAccuracy1() throws Exception {
        Map map = new HashMap();
        header.setProperty("key1", "value1");
        header.setProperty("key1", "value2");
        header.setProperty("key2", "value3");
        map.put("key1", "value2");
        map.put("key2", "value3");
        TestHelper.assertTeamHeader(header, null, false, -1, -1, -1, -1, null, map);
    }

    /**
     * Accuracy test of <code>setProperty(String key, Serializable value)</code> method.
     * <p>
     * Use set null to remove property.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSetPropertyAccuracy2() throws Exception {
        Map map = new HashMap();
        header.setProperty("key1", "value1");
        header.setProperty("key1", "value2");
        header.setProperty("key2", "value3");
        header.setProperty("key2", null);
        map.put("key1", "value2");
        TestHelper.assertTeamHeader(header, null, false, -1, -1, -1, -1, null, map);
    }

    /**
     * Failure test of <code>setProperty(String key, Serializable value)</code> method.
     * <p>
     * Call this method with null key.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSetPropertyFailure1() throws Exception {
        try {
            header.setProperty(null, "value");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>setProperty(String key, Serializable value)</code> method.
     * <p>
     * Call this method with trimmed empty key.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSetPropertyFailure2() throws Exception {
        try {
            header.setProperty(" ", "value");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>setProperty(String key, Serializable value)</code> method.
     * <p>
     * Call this method with lengthy key.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSetPropertyFailure3() throws Exception {
        try {
            // Generate a string with 256 characters.
            header.setProperty(TestHelper.generateString(256), "value");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>getProperty(String key)</code> method.
     * <p>
     * Simply set a property and get it.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testGetPropertyAccuracy1() throws Exception {
        header.setProperty("key1", "value1");
        header.setProperty("key1", "value2");
        assertEquals("property mismatch", "value2", header.getProperty("key1"));
    }

    /**
     * Accuracy test of <code>getProperty(String key)</code> method.
     * <p>
     * Get a non-existed property.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testGetPropertyAccuracy2() throws Exception {
        assertEquals("property mismatch", null, header.getProperty("key1"));
    }

    /**
     * Failure test of <code>getProperty(String key)</code> method.
     * <p>
     * Call this method with null key.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testGetPropertyFailure1() throws Exception {
        try {
            header.getProperty(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>getProperty(String key)</code> method.
     * <p>
     * Call this method with trimmed empty key.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testGetPropertyFailure2() throws Exception {
        try {
            header.getProperty(" ");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>getProperty(String key)</code> method.
     * <p>
     * Call this method with lengthy key.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testGetPropertyFailure3() throws Exception {
        try {
            // Generate a string with 256 characters.
            header.getProperty(TestHelper.generateString(256));
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>setDescription(String name)</code> method.
     * <p>
     * Call this method with null.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSetDescriptionAccuracy1() throws Exception {
        header.setDescription(null);
        TestHelper.assertTeamHeader(header, null, false, -1, -1, -1, -1, null, new HashMap());
    }

    /**
     * Accuracy test of <code>setDescription(String name)</code> method.
     * <p>
     * Call this method with empty string.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSetDescriptionAccuracy2() throws Exception {
        header.setDescription("");
        TestHelper.assertTeamHeader(header, null, false, -1, -1, -1, -1, "", new HashMap());
    }

    /**
     * Accuracy test of <code>setDescription(String name)</code> method.
     * <p>
     * Call this method with a simple string.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSetDescriptionAccuracy3() throws Exception {
        header.setDescription("string");
        TestHelper.assertTeamHeader(header, null, false, -1, -1, -1, -1, "string", new HashMap());
    }
}
