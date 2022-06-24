/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project.failuretests;

import com.topcoder.management.project.ProjectCategory;
import com.topcoder.management.project.ProjectType;

import junit.framework.TestCase;

/**
 * Failure tests for <code>ProjectCategory</code> class.
 *
 * @author vividmxx
 * @version 1.0
 */
public class ProjectCategoryFailureTests extends TestCase {

    /**
     * The <code>ProjectType</code> instance used for test.
     */
    private ProjectType type = new ProjectType(2006, "type");

    /**
     * The <code>ProjectCategory</code> instance used to test against.
     */
    private ProjectCategory pc = new ProjectCategory(2006001, "name", new ProjectType(2006, "type"));

    /**
     * Test the constructor <code>ProjectCategory(long, String, ProjectType)</code> with negative id,
     * IllegalArgumentException should be thrown.
     */
    public void testConstructorWithNegativeId1() {
        try {
            new ProjectCategory(-1, "name", type);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the constructor <code>ProjectCategory(long, String, ProjectType)</code> with zero id,
     * IllegalArgumentException should be thrown.
     */
    public void testConstructorWithZeroId1() {
        try {
            new ProjectCategory(0, "name", type);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the constructor <code>ProjectCategory(long, String, ProjectType)</code> with null name,
     * IllegalArgumentException should be thrown.
     */
    public void testConstructorWithNullName1() {
        try {
            new ProjectCategory(1, null, type);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the constructor <code>ProjectCategory(long, String, ProjectType)</code> with empty name,
     * IllegalArgumentException should be thrown.
     */
    public void testConstructorWithEmptyName1() {
        try {
            new ProjectCategory(1, " ", type);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the constructor <code>ProjectCategory(long, String, ProjectType)</code> with null projectType,
     * IllegalArgumentException should be thrown.
     */
    public void testConstructorWithNullProjectType1() {
        try {
            new ProjectCategory(1, "desc", null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the constructor <code>ProjectCategory(long, String, String, ProjectType)</code> with negative id,
     * IllegalArgumentException should be thrown.
     */
    public void testConstructorWithNegativeId2() {
        try {
            new ProjectCategory(-1, "name", "desc", type);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the constructor <code>ProjectCategory(long, String, String, ProjectType)</code> with zero id,
     * IllegalArgumentException should be thrown.
     */
    public void testConstructorWithZeroId2() {
        try {
            new ProjectCategory(0, "name", "desc", type);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the constructor <code>ProjectCategory(long, String, String, ProjectType)</code> with null name,
     * IllegalArgumentException should be thrown.
     */
    public void testConstructorWithNullName2() {
        try {
            new ProjectCategory(1, null, "desc", type);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the constructor <code>ProjectCategory(long, String, String, ProjectType)</code> with empty name,
     * IllegalArgumentException should be thrown.
     */
    public void testConstructorWithEmptyName2() {
        try {
            new ProjectCategory(1, " ", "desc", type);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the constructor <code>ProjectCategory(long, String, String, ProjectType)</code> with null description,
     * IllegalArgumentException should be thrown.
     */
    public void testConstructorWithNullDescription() {
        try {
            new ProjectCategory(1, "name", null, type);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the constructor <code>ProjectCategory(long, String, String, ProjectType)</code> with null projectType,
     * IllegalArgumentException should be thrown.
     */
    public void testConstructorWithNullProjectType() {
        try {
            new ProjectCategory(1, "name", "desc", null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>setId(long)</code> with negative id, IllegalArgumentException should be thrown.
     */
    public void testSetIdWithNegativeId() {
        try {
            pc.setId(-1);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>setId(long)</code> with zero id, IllegalArgumentException should be thrown.
     */
    public void testSetIdWithZeroId() {
        try {
            pc.setId(0);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>setName(String)</code> with null name, IllegalArgumentException should be thrown.
     */
    public void testSetNameWithNullName() {
        try {
            pc.setName(null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>setName(String)</code> with empty name, IllegalArgumentException should be thrown.
     */
    public void testSetNameWithEmptyName() {
        try {
            pc.setName(" ");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>setDescription(String)</code> with null description, IllegalArgumentException should be
     * thrown.
     */
    public void testSetDescriptionWithNullName() {
        try {
            pc.setDescription(null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>setProjectType(ProjectType)</code> with null projectType, IllegalArgumentException should
     * be thrown.
     */
    public void testSetProjectTypeWithNullProjectType() {
        try {
            pc.setProjectType(null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }
}
