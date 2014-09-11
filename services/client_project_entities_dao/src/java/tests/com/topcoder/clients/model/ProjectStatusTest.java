/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.model;

import junit.framework.TestCase;

/**
 * <p>
 * Test class: <code>ProjectStatus</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ProjectStatusTest extends TestCase {
    /**
     * <p>
     * An instance of <code>ProjectStatus</code> which is tested.
     * </p>
     */
    private ProjectStatus target = null;

    /**
     * <p>
     * setUp() routine.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        target = new ProjectStatus();
    }

    /**
     * <p>
     * Inheritance test.
     * </p>
     * <p>
     * Verifies <code>ProjectStatus</code> subclasses <code>AuditableEntity</code>.
     * </p>
     */
    public void testInheritance() {
        assertTrue("ProjectStatus does not subclasses AuditableEntity.", target instanceof AuditableEntity);
    }

    /**
     * <p>
     * Tests the <code>com.topcoder.clients.model.ProjectStatus()</code> for proper behavior. Verifies that Verifies
     * that instance should be created.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConstructor() throws Exception {
        assertNotNull("Instance should be created.", target);
    }

    /**
     * <p>
     * Tests the <code>setDescription(String)</code> for proper behavior. Verifies that the property should be
     * correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testMethodSetDescription_String() throws Exception {
        target.setDescription("name1");
        assertEquals("getDescription", "name1", target.getDescription());
    }

    /**
     * <p>
     * Tests the <code>getDescription()</code> for proper behavior. Verifies that the property should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testMethodGetDescription() throws Exception {
        target.setDescription("name1");
        assertEquals("getDescription", "name1", target.getDescription());
    }

}
