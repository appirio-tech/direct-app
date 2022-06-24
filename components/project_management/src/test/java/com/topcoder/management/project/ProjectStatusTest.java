/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for ProjectStatus class.
 *
 * @author iamajia
 * @version 1.0
 */
public class ProjectStatusTest extends TestCase {
    /**
     * This projectStatus is used in test.
     */
    private ProjectStatus projectStatus = new ProjectStatus(1, "test", "des");

    /**
     * Aggregates all tests in this class.
     *
     * @return Test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(ProjectStatusTest.class);
    }

    /**
     * Accuracy test of <code>ProjectStatus(long id, String name)</code> constructor.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testProjectStatusAccuracy1() throws Exception {
        ProjectStatus newProjectStatus = new ProjectStatus(1, "test");
        assertEquals("id is incorrect.", 1, newProjectStatus.getId());
        assertEquals("name is incorrect.", "test", newProjectStatus.getName());
    }

    /**
     * Failure test of <code>ProjectStatus(long id, String name)</code> constructor.
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
    public void testProjectStatusFailure1() throws Exception {
        try {
            new ProjectStatus(0, "test");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>ProjectStatus(long id, String name)</code> constructor.
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
    public void testProjectStatusFailure8() throws Exception {
        try {
            new ProjectStatus(-1, "test");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>ProjectStatus(long id, String name)</code> constructor.
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
    public void testProjectStatusFailure2() throws Exception {
        try {
            new ProjectStatus(1, null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>ProjectStatus(long id, String name)</code> constructor.
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
    public void testProjectStatusFailure3() throws Exception {
        try {
            new ProjectStatus(1, "  ");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>ProjectStatus(long id, String name, String description)</code> constructor.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testProjectStatusAccuracy2() throws Exception {
        ProjectStatus newProjectStatus = new ProjectStatus(1, "test", "");
        assertEquals("id is incorrect.", 1, newProjectStatus.getId());
        assertEquals("name is incorrect.", "test", newProjectStatus.getName());
        assertEquals("description is incorrect.", "", newProjectStatus.getDescription());
    }

    /**
     * Failure test of <code>ProjectStatus(long id, String name, String description)</code> constructor.
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
    public void testProjectStatusFailure4() throws Exception {
        try {
            new ProjectStatus(0, "test", "");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>ProjectStatus(long id, String name, String description)</code> constructor.
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
    public void testProjectStatusFailure9() throws Exception {
        try {
            new ProjectStatus(-1, "test", "");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>ProjectStatus(long id, String name, String description)</code> constructor.
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
    public void testProjectStatusFailure5() throws Exception {
        try {
            new ProjectStatus(1, null, "");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>ProjectStatus(long id, String name, String description)</code> constructor.
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
    public void testProjectStatusFailure6() throws Exception {
        try {
            new ProjectStatus(1, "   ", "");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>ProjectStatus(long id, String name, String description)</code> constructor.
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
    public void testProjectStatusFailure7() throws Exception {
        try {
            new ProjectStatus(1, "test", null);
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
        projectStatus.setId(2);
        assertEquals("id is incorrect.", 2, projectStatus.getId());
    }

    /**
     * Failure test of <code>setId(long id)</code> method.
     * <p>
     * id <= 0
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSetIdFailure() throws Exception {
        try {
            projectStatus.setId(0);
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
        projectStatus.setId(2);
        assertEquals("id is incorrect.", 2, projectStatus.getId());
    }

    /**
     * Accuracy test of <code>setName(String name)</code> method.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSetNameAccuracy() throws Exception {
        projectStatus.setName("a");
        assertEquals("name is incorrect.", "a", projectStatus.getName());
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
            projectStatus.setName(null);
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
            projectStatus.setName("  ");
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
        projectStatus.setName("a");
        assertEquals("name is incorrect.", "a", projectStatus.getName());
    }

    /**
     * Accuracy test of <code>setDescription(String description)</code> method.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSetDescriptionAccuracy() throws Exception {
        projectStatus.setDescription("");
        assertEquals("description is incorrect.", "", projectStatus.getDescription());
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
            projectStatus.setDescription(null);
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
        projectStatus.setDescription("");
        assertEquals("description is incorrect.", "", projectStatus.getDescription());
    }
}
