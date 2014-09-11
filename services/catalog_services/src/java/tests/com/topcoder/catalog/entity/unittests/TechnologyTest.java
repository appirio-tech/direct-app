/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.catalog.entity.unittests;

import com.topcoder.catalog.entity.Status;
import com.topcoder.catalog.entity.Technology;
import junit.framework.TestCase;

/**
 * <p>Unit test case for {@link Technology}.</p>
 *
 * @author Retunsky
 * @version 1.0
 */
public class TechnologyTest extends TestCase {
    /**
     * <p>Represents Technology instance for testing.</p>
     */
    private Technology technology;

    /**
     * <p>Creates a new instance of Technology.</p>
     *
     * @throws Exception to jUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();
        technology = new Technology();
    }

    /**
     * <p>Tears down the test environment.</p>
     *
     * @throws Exception to jUnit.
     */
    protected void tearDown() throws Exception {
        technology = null;
        super.tearDown();
    }

    /**
     * <p>Tests <code>Technology()</code> constructor.</p>
     */
    public void testTechnology() {
        assertNotNull("Unable to instantiate Technology", technology);
    }

    /**
     * <p>Tests <code>getId()</code> and
     * <code>getId()</code> methods for accuracy.</p>
     */
    public void testGetSetId() {
        assertNull("Incorrect default id value", technology.getId());
        Long id = 1L;
        // set a id
        technology.setId(id);
        assertEquals("Incorrect id value after setting a new one",
            id, technology.getId());
    }

    /**
     * <p>Tests <code>setId(null)</code>.</p>
     */
    public void testSetIdAllowsNull() {
        // set null
        try {
            technology.setId(null);
            assertNull("Field 'id' should contain 'null' value",
                technology.getId());
        } catch (IllegalArgumentException e) {
            fail("Field 'id' should allow null values");
        }
    }


    /**
     * <p>Tests <code>getDescription()</code> and
     * <code>setDescription()</code> methods for accuracy.</p>
     */
    public void testGetSetDescription() {
        assertNull("Incorrect default description value", technology.getDescription());
        String value = "46";
        // set a description
        technology.setDescription(value);
        assertEquals("Incorrect description value after setting a new one",
            value, technology.getDescription());
    }

    /**
     * <p>Tests <code>setDescription(null)</code>.</p>
     */
    public void testDescriptionAllowsNull() {
        // set a description
        // set null
        try {
            technology.setDescription(null);
            assertNull("Field 'description' should contain 'null' value",
                technology.getDescription());
        } catch (IllegalArgumentException e) {
            fail("Field 'description' should allow null values");
        }
    }

    /**
     * <p>Tests <code>getName()</code> and
     * <code>setName()</code> methods for accuracy.</p>
     */
    public void testGetSetName() {
        assertNull("Incorrect default name value", technology.getName());
        String value = "47";
        // set a name
        technology.setName(value);
        assertEquals("Incorrect name value after setting a new one",
            value, technology.getName());
    }

    /**
     * <p>Tests <code>setName(null)</code>.</p>
     */
    public void testNameAllowsNull() {
        // set a name
        // set null
        try {
            technology.setName(null);
            assertNull("Field 'name' should contain 'null' value",
                technology.getName());
        } catch (IllegalArgumentException e) {
            fail("Field 'name' should allow null values");
        }
    }

    /**
     * <p>Tests <code>getStatus()</code> and
     * <code>setStatus()</code> methods for accuracy.</p>
     */
    public void testGetSetStatus() {
        assertNull("Incorrect default status value", technology.getStatus());
        Status value = Status.REPOST;
        // set a status
        technology.setStatus(value);
        assertEquals("Incorrect status value after setting a new one",
            value, technology.getStatus());
    }

    /**
     * <p>Tests <code>setStatus(null)</code>.</p>
     */
    public void testStatusAllowsNull() {
        // set a status
        // set null
        try {
            technology.setStatus(null);
            assertNull("Field 'status' should contain 'null' value",
                technology.getStatus());
        } catch (IllegalArgumentException e) {
            fail("Field 'status' should allow null values");
        }
    }


}
