/*
 * Copyright (C) 2006 -2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for ProjectPropertyType class.
 *
 * @author iamajia, TCSDEVELOPER
 * @version 1.2
 * @since 1.0
 */
public class ProjectPropertyTypeTest extends TestCase {
    /**
     * This projectPropertyType is used in test.
     */
    private ProjectPropertyType projectPropertyType = new ProjectPropertyType(1, "test", "des");

    /**
     * Aggregates all tests in this class.
     *
     * @return Test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(ProjectPropertyTypeTest.class);
    }

    /**
     * Accuracy test of <code>ProjectPropertyType(long id, String name)</code> constructor.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testProjectPropertyTypeAccuracy1() throws Exception {
        ProjectPropertyType newProjectPropertyType = new ProjectPropertyType(1, "test");
        assertEquals("id is incorrect.", 1, newProjectPropertyType.getId());
        assertEquals("name is incorrect.", "test", newProjectPropertyType.getName());
    }

    /**
     * Failure test of <code>ProjectPropertyType(long id, String name)</code> constructor.
     * <p>
     * id = 0
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testProjectPropertyTypeFailure1() throws Exception {
        try {
            new ProjectPropertyType(0, "test");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>ProjectPropertyType(long id, String name)</code> constructor.
     * <p>
     * id = -1
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testProjectPropertyTypeFailure8() throws Exception {
        try {
            new ProjectPropertyType(-1, "test");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>ProjectPropertyType(long id, String name)</code> constructor.
     * <p>
     * name is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testProjectPropertyTypeFailure2() throws Exception {
        try {
            new ProjectPropertyType(1, null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>ProjectPropertyType(long id, String name)</code> constructor.
     * <p>
     * name is empty.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testProjectPropertyTypeFailure3() throws Exception {
        try {
            new ProjectPropertyType(1, "  ");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>ProjectPropertyType(long id, String name, String description)</code> constructor.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testProjectPropertyTypeAccuracy2() throws Exception {
        ProjectPropertyType newProjectPropertyType = new ProjectPropertyType(1, "test", "");
        assertEquals("id is incorrect.", 1, newProjectPropertyType.getId());
        assertEquals("name is incorrect.", "test", newProjectPropertyType.getName());
        assertEquals("description is incorrect.", "", newProjectPropertyType.getDescription());
    }

    /**
     * Failure test of <code>ProjectPropertyType(long id, String name, String description)</code> constructor.
     * <p>
     * id = 0
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testProjectPropertyTypeFailure4() throws Exception {
        try {
            new ProjectPropertyType(0, "test", "");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>ProjectPropertyType(long id, String name, String description)</code> constructor.
     * <p>
     * id = -1
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testProjectPropertyTypeFailure9() throws Exception {
        try {
            new ProjectPropertyType(-1, "test", "");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>ProjectPropertyType(long id, String name, String description)</code> constructor.
     * <p>
     * name is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testProjectPropertyTypeFailure5() throws Exception {
        try {
            new ProjectPropertyType(1, null, "");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>ProjectPropertyType(long id, String name, String description)</code> constructor.
     * <p>
     * name is empty.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testProjectPropertyTypeFailure6() throws Exception {
        try {
            new ProjectPropertyType(1, "   ", "");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>ProjectPropertyType(long id, String name, String description)</code> constructor.
     * <p>
     * description is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testProjectPropertyTypeFailure7() throws Exception {
        try {
            new ProjectPropertyType(1, "test", null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>setId(long id)</code> method.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSetIdAccuracy() throws Exception {
        projectPropertyType.setId(2);
        assertEquals("id is incorrect.", 2, projectPropertyType.getId());
    }

    /**
     * Failure test of <code>setId(long id)</code> method.
     * <p>
     * id = 0
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSetIdFailure1() throws Exception {
        try {
            projectPropertyType.setId(0);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>setId(long id)</code> method.
     * <p>
     * id = -1
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSetIdFailure2() throws Exception {
        try {
            projectPropertyType.setId(-1);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>getId()</code> method.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testGetIdAccuracy() throws Exception {
        projectPropertyType.setId(2);
        assertEquals("id is incorrect.", 2, projectPropertyType.getId());
    }

    /**
     * Accuracy test of <code>setName(String name)</code> method.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSetNameAccuracy() throws Exception {
        projectPropertyType.setName("a");
        assertEquals("name is incorrect.", "a", projectPropertyType.getName());
    }

    /**
     * Failure test of <code>setName(String name)</code> method.
     * <p>
     * name is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSetNameFailure1() throws Exception {
        try {
            projectPropertyType.setName(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>setName(String name)</code> method.
     * <p>
     * name is empty.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSetNameFailure2() throws Exception {
        try {
            projectPropertyType.setName("  ");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>getName()</code> method.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testGetNameAccuracy() throws Exception {
        projectPropertyType.setName("a");
        assertEquals("name is incorrect.", "a", projectPropertyType.getName());
    }

    /**
     * Accuracy test of <code>setDescription(String description)</code> method.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSetDescriptionAccuracy() throws Exception {
        projectPropertyType.setDescription("");
        assertEquals("description is incorrect.", "", projectPropertyType.getDescription());
    }

    /**
     * Failure test of <code>setDescription(String description)</code> method.
     * <p>
     * description is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSetDescriptionFailure() throws Exception {
        try {
            projectPropertyType.setDescription(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>getDescription()</code> method.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testGetDescriptionAccuracy() throws Exception {
        projectPropertyType.setDescription("");
        assertEquals("description is incorrect.", "", projectPropertyType.getDescription());
    }

    /**
     * Accuracy test of the new added MAXIMUM_SUBMISSIONS_KEY constant.
     *
     * @since 1.2
     */
    public void testMaximumSubmissionsKey() {
        assertEquals("Should be the same.", "Maximum Submissions", ProjectPropertyType.MAXIMUM_SUBMISSIONS_KEY);
    }
}
