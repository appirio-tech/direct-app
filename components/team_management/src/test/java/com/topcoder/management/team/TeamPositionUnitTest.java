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
 * UnitTest for TeamPosition class. Getters will be tested throughout this test class, so no special test methods are
 * designed for those getters.
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class TeamPositionUnitTest extends TestCase {
    /** Provide a position for testing. */
    private TeamPosition position = new TeamPosition();

    /**
     * <p>
     * Aggregates all tests in this class.
     * </p>
     * @return Test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(TeamPositionUnitTest.class);
    }

    /**
     * Accuracy test of <code>TeamPosition()</code> constructor.
     * <p>
     * Call this constructor and check the inner fields.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testTeamPosition1_Accuracy() throws Exception {
        position = new TeamPosition();
        TestHelper.assertTeamPosition(position, null, false, -1, -1, null, -1, false, new HashMap());
    }

    /**
     * Accuracy test of <code>TeamPosition(TeamPosition, String, boolean, long, int, String, long, boolean)</code>
     * constructor.
     * <p>
     * Call this constructor and check the inner fields.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testTeamPosition2_Accuracy1() throws Exception {
        position = new TeamPosition("a", true, 1, 2, "b", 3, true);
        TestHelper.assertTeamPosition(position, "a", true, 1, 2, "b", 3, true, new HashMap());
    }

    /**
     * Accuracy test of <code>TeamPosition(TeamPosition, String, boolean, long, int, String, long, boolean)</code>
     * constructor.
     * <p>
     * Call this constructor with null strings.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testTeamPosition2_Accuracy2() throws Exception {
        position = new TeamPosition(null, false, 1, 2, null, 3, true);
        TestHelper.assertTeamPosition(position, null, false, 1, 2, null, 3, true, new HashMap());
    }

    /**
     * Accuracy test of <code>TeamPosition(TeamPosition, String, boolean, long, int, String, long, boolean)</code>
     * constructor.
     * <p>
     * Call this constructor with zeros.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testTeamPosition2_Accuracy3() throws Exception {
        position = new TeamPosition("a", true, 0, 0, "b", 0, false);
        TestHelper.assertTeamPosition(position, "a", true, 0, 0, "b", 0, false, new HashMap());
    }

    /**
     * Accuracy test of <code>TeamPosition(TeamPosition, String, boolean, long, int, String, long, boolean)</code>
     * constructor.
     * <p>
     * Call this constructor with -1 as memberResourceId, and 100 as percentage.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testTeamPosition2_Accuracy4() throws Exception {
        position = new TeamPosition("a", true, -1, 100, "b", 0, false);
        TestHelper.assertTeamPosition(position, "a", true, -1, 100, "b", 0, false, new HashMap());
    }

    /**
     * Failure test of <code>TeamPosition(TeamPosition, String, boolean, long, int, String, long, boolean)</code>
     * constructor.
     * <p>
     * Call this constructor with memberResourceId < -1.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testTeamPosition2_Failure1() throws Exception {
        try {
            new TeamPosition("a", true, -2, 0, "b", 0, false);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>TeamPosition(TeamPosition, String, boolean, long, int, String, long, boolean)</code>
     * constructor.
     * <p>
     * Call this constructor with positionId < 0.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testTeamPosition2_Failure2() throws Exception {
        try {
            new TeamPosition("a", true, 0, 0, "b", -1, false);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>TeamPosition(TeamPosition, String, boolean, long, int, String, long, boolean)</code>
     * constructor.
     * <p>
     * Call this constructor with paymentPercentage < 0.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testTeamPosition2_Failure3() throws Exception {
        try {
            new TeamPosition("a", true, 0, -1, "b", 0, false);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>TeamPosition(TeamPosition, String, boolean, long, int, String, long, boolean)</code>
     * constructor.
     * <p>
     * Call this constructor with paymentPercentage > 100.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testTeamPosition2_Failure4() throws Exception {
        try {
            new TeamPosition("a", true, 0, 101, "b", 0, false);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>setDescription(String description)</code> method.
     * <p>
     * Set a sample description.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSetDescriptionAccuracy1() throws Exception {
        position.setDescription("sample");
        TestHelper.assertTeamPosition(position, "sample", false, -1, -1, null, -1, false, new HashMap());

    }

    /**
     * Accuracy test of <code>setDescription(String description)</code> method.
     * <p>
     * Set an empty description.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSetDescriptionAccuracy2() throws Exception {
        position.setDescription("");
        TestHelper.assertTeamPosition(position, "", false, -1, -1, null, -1, false, new HashMap());
    }

    /**
     * Accuracy test of <code>setDescription(String description)</code> method.
     * <p>
     * Set a null description.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSetDescriptionAccuracy3() throws Exception {
        position.setDescription("sample");
        position.setDescription(null);
        TestHelper.assertTeamPosition(position, null, false, -1, -1, null, -1, false, new HashMap());
    }

    /**
     * Accuracy test of <code>setFilled(boolean filled)</code> method.
     * <p>
     * Set true.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSetFilledAccuracy1() throws Exception {
        position.setFilled(true);
        TestHelper.assertTeamPosition(position, null, true, -1, -1, null, -1, false, new HashMap());
    }

    /**
     * Accuracy test of <code>setFilled(boolean filled)</code> method.
     * <p>
     * Set false.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSetFilledAccuracy2() throws Exception {
        position.setFilled(true);
        position.setFilled(false);
        TestHelper.assertTeamPosition(position, null, false, -1, -1, null, -1, false, new HashMap());
    }

    /**
     * Accuracy test of <code>setMemberResourceId(long memberResourceId)</code> method.
     * <p>
     * Set a simple value.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSetMemberResourceIdAccuracy1() throws Exception {
        position.setMemberResourceId(7);
        TestHelper.assertTeamPosition(position, null, false, 7, -1, null, -1, false, new HashMap());
    }

    /**
     * Accuracy test of <code>setMemberResourceId(long memberResourceId)</code> method.
     * <p>
     * Set memeberResourceId to -1.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSetMemberResourceIdAccuracy2() throws Exception {
        position.setMemberResourceId(-1);
        TestHelper.assertTeamPosition(position, null, false, -1, -1, null, -1, false, new HashMap());
    }

    /**
     * Accuracy test of <code>setMemberResourceId(long memberResourceId)</code> method.
     * <p>
     * Set memeberResourceId to 0.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSetMemberResourceIdAccuracy3() throws Exception {
        position.setMemberResourceId(0);
        TestHelper.assertTeamPosition(position, null, false, 0, -1, null, -1, false, new HashMap());
    }

    /**
     * Failure test of <code>setMemberResourceId(long memberResourceId)</code> method.
     * <p>
     * Set with memeberResourceId < -1.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSetMemberResourceIdFailure() throws Exception {
        try {
            position.setMemberResourceId(-2);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>setPaymentPercentage(int paymentPercentage)</code> method.
     * <p>
     * Set 0 as percentage.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSetPaymentPercentageAccuracy1() throws Exception {
        position.setPaymentPercentage(0);
        TestHelper.assertTeamPosition(position, null, false, -1, 0, null, -1, false, new HashMap());
    }

    /**
     * Accuracy test of <code>setPaymentPercentage(int paymentPercentage)</code> method.
     * <p>
     * Set 70 as percentage.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSetPaymentPercentageAccuracy2() throws Exception {
        position.setPaymentPercentage(70);
        TestHelper.assertTeamPosition(position, null, false, -1, 70, null, -1, false, new HashMap());
    }

    /**
     * Accuracy test of <code>setPaymentPercentage(int paymentPercentage)</code> method.
     * <p>
     * Set 100 as percentage.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSetPaymentPercentageAccuracy3() throws Exception {
        position.setPaymentPercentage(100);
        TestHelper.assertTeamPosition(position, null, false, -1, 100, null, -1, false, new HashMap());
    }

    /**
     * Failure test of <code>setPaymentPercentage(int paymentPercentage)</code> method.
     * <p>
     * Set -1 as percentage.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSetPaymentPercentageFailure1() throws Exception {
        try {
            position.setPaymentPercentage(-1);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>setPaymentPercentage(int paymentPercentage)</code> method.
     * <p>
     * Set 101 as percentage.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSetPaymentPercentageFailure2() throws Exception {
        try {
            position.setPaymentPercentage(101);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>setName(String name)</code> method.
     * <p>
     * Set a sample name.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSetNameAccuracy1() throws Exception {
        position.setName("sample");
        TestHelper.assertTeamPosition(position, null, false, -1, -1, "sample", -1, false, new HashMap());
    }

    /**
     * Accuracy test of <code>setName(String name)</code> method.
     * <p>
     * Set an empty name.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSetNameAccuracy2() throws Exception {
        position.setName("");
        TestHelper.assertTeamPosition(position, null, false, -1, -1, "", -1, false, new HashMap());
    }

    /**
     * Accuracy test of <code>setName(String name)</code> method.
     * <p>
     * Set a null name.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSetNameAccuracy3() throws Exception {
        position.setName(null);
        TestHelper.assertTeamPosition(position, null, false, -1, -1, null, -1, false, new HashMap());
    }

    /**
     * Accuracy test of <code>setPositionId(long positionId)</code> method.
     * <p>
     * Set with a sample positionId.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSetPositionIdAccuracy1() throws Exception {
        position.setPositionId(7);
        TestHelper.assertTeamPosition(position, null, false, -1, -1, null, 7, false, new HashMap());
    }

    /**
     * Accuracy test of <code>setPositionId(long positionId)</code> method.
     * <p>
     * Set with a zero positionId.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSetPositionIdAccuracy2() throws Exception {
        position.setPositionId(0);
        TestHelper.assertTeamPosition(position, null, false, -1, -1, null, 0, false, new HashMap());
    }

    /**
     * Failure test of <code>setPositionId(long positionId)</code> method.
     * <p>
     * Set with negative id.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSetPositionIdFailure() throws Exception {
        try {
            position.setPositionId(-1);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>setPublished(boolean published)</code> method.
     * <p>
     * Set true.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSetPublishedAccuracy1() throws Exception {
        position.setPublished(true);
        TestHelper.assertTeamPosition(position, null, false, -1, -1, null, -1, true, new HashMap());
    }

    /**
     * Accuracy test of <code>setPublished(boolean published)</code> method.
     * <p>
     * Set false.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSetPublishedAccuracy2() throws Exception {
        position.setPublished(true);
        position.setPublished(false);
        TestHelper.assertTeamPosition(position, null, false, -1, -1, null, -1, false, new HashMap());
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
        position.setProperty("key1", "value1");
        position.setProperty("key1", "value2");
        position.setProperty("key2", "value3");
        map.put("key1", "value2");
        map.put("key2", "value3");
        TestHelper.assertTeamPosition(position, null, false, -1, -1, null, -1, false, map);
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
        position.setProperty("key1", "value1");
        position.setProperty("key1", "value2");
        position.setProperty("key2", "value3");
        position.setProperty("key2", null);
        map.put("key1", "value2");
        TestHelper.assertTeamPosition(position, null, false, -1, -1, null, -1, false, map);
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
            position.setProperty(null, "value");
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
            position.setProperty(" ", "value");
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
            position.setProperty(TestHelper.generateString(256), "value");
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
        position.setProperty("key1", "value1");
        position.setProperty("key1", "value2");
        assertEquals("property mismatch", "value2", position.getProperty("key1"));
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
        assertEquals("property mismatch", null, position.getProperty("key1"));
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
            position.getProperty(null);
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
            position.getProperty(" ");
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
            position.getProperty(TestHelper.generateString(256));
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

}
