/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project.failuretests;

import java.util.HashMap;
import java.util.Map;

import com.topcoder.management.project.Project;
import com.topcoder.management.project.ProjectCategory;
import com.topcoder.management.project.ProjectStatus;
import com.topcoder.management.project.ProjectType;

import junit.framework.TestCase;

/**
 * Failure tests for <code>Project</code> class.
 *
 * @author vividmxx
 * @version 1.0
 */
public class ProjectFailureTests extends TestCase {

    /**
     * The properties map used for test.
     */
    private Map properties = null;

    /**
     * The <code>Project</code> instance used to test against.
     */
    private Project project = null;

    /**
     * The <code>ProjectStatus</code> instance used for test.
     */
    private ProjectStatus status = new ProjectStatus(200601, "status");

    /**
     * The <code>ProjectCategory</code> instance used for test.
     */
    private ProjectCategory category = new ProjectCategory(200602, "category", new ProjectType(2006, "type"));

    /**
     * Set up the test environment.
     */
    protected void setUp() {
        properties = new HashMap();
        properties.put("an Integer", new Integer(1));
        project = new Project(2006, category, status);
    }

    /**
     * Test the constructor <code>Project(ProjectCategory, ProjectStatus)</code> with null projectCategory.
     * IllegalArgumentException should be thrown.
     */
    public void testConstructorWithNullProjectCategory1() {
        try {
            new Project(null, status);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the constructor <code>Project(ProjectCategory, ProjectStatus)</code> with null projectStatus.
     * IllegalArgumentException should be thrown.
     */
    public void testConstructorWithNullProjectStatus1() {
        try {
            new Project(category, null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the constructor <code>Project(long, ProjectCategory, ProjectStatus)</code> with null projectCategory.
     * IllegalArgumentException should be thrown.
     */
    public void testConstructorWithNullProjectCategory2() {
        try {
            new Project(1, null, status);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the constructor <code>Project(long, ProjectCategory, ProjectStatus)</code> with null projectStatus.
     * IllegalArgumentException should be thrown.
     */
    public void testConstructorWithNullProjectStatus2() {
        try {
            new Project(1, category, null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the constructor <code>Project(long, ProjectCategory, ProjectStatus)</code> with negative id.
     * IllegalArgumentException should be thrown.
     */
    public void testConstructorWithNegativeId1() {
        try {
            new Project(-1, category, status);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the constructor <code>Project(long, ProjectCategory, ProjectStatus, Map)</code> with null projectCategory.
     * IllegalArgumentException should be thrown.
     */
    public void testConstructorWithNullProjectCategory3() {
        try {
            new Project(1, null, status, properties);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the constructor <code>Project(long, ProjectCategory, ProjectStatus, Map)</code> with null projectStatus.
     * IllegalArgumentException should be thrown.
     */
    public void testConstructorWithNullProjectStatus3() {
        try {
            new Project(1, category, null, properties);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the constructor <code>Project(long, ProjectCategory, ProjectStatus, Map)</code> with negative id.
     * IllegalArgumentException should be thrown.
     */
    public void testConstructorWithNegativeId2() {
        try {
            new Project(-1, category, status, properties);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the constructor <code>Project(long, ProjectCategory, ProjectStatus, Map)</code> with null properties.
     * IllegalArgumentException should be thrown.
     */
    public void testConstructorWithNullProperties() {
        try {
            new Project(1, category, status, null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the constructor <code>Project(long, ProjectCategory, ProjectStatus, Map)</code> with invalid properties
     * which contains non-String key. IllegalArgumentException should be thrown.
     */
    public void testConstructorWithNonStringKeyProperties() {
        try {
            Map map = new HashMap();
            map.put(new Integer(1), new Integer(1));
            new Project(1, category, status, map);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the constructor <code>Project(long, ProjectCategory, ProjectStatus, Map)</code> with invalid properties
     * which contains non-Object value. IllegalArgumentException should be thrown.
     */
    public void testConstructorWithNonObjectValueProperties() {
        try {
            Map map = new HashMap();
            map.put("I am a String", null);
            new Project(1, category, status, map);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>setId(long)</code> with negative id. IllegalArgumentException should be thrown.
     */
    public void testSetIdWithNegativeId() {
        try {
            project.setId(-1);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>setId(long)</code> with zero id. IllegalArgumentException should be thrown.
     */
    public void testSetIdWithZeroId() {
        try {
            project.setId(0);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>setProjectStatus(ProjectStatus)</code> with null projectStatus. IllegalArgumentException
     * should be thrown.
     */
    public void testSetProjectStatusWithNullProjectStatus() {
        try {
            project.setProjectStatus(null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>setProjectCategory(ProjectCategory)</code> with null projectCategory.
     * IllegalArgumentException should be thrown.
     */
    public void testSetProjectCategoryWithNullProjectCategory() {
        try {
            project.setProjectCategory(null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>setProperty(String, Object)</code> with null name. IllegalArgumentException should be
     * thrown.
     */
    public void testSetPropertyWithNullName() {
        try {
            project.setProperty(null, new Object());
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>setProperty(String, Object)</code> with empty name. IllegalArgumentException should be
     * thrown.
     */
    public void testSetPropertyWithEmptyName() {
        try {
            project.setProperty(" ", new Object());
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>getProperty(String)</code> with null name. IllegalArgumentException should be thrown.
     */
    public void testGetPropertyWithNullName() {
        try {
            project.getProperty(null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * Test the method <code>getProperty(String)</code> with empty name. IllegalArgumentException should be thrown.
     */
    public void testGetPropertyWithEmptyName() {
        try {
            project.getProperty(" ");
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // success
        }
    }
}
