/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.catalog.entity.unittests;

import com.topcoder.catalog.entity.Phase;
import junit.framework.TestCase;

/**
 * <p>Unit test case for {@link Phase}.</p>
 *
 * @author Retunsky
 * @version 1.0
 */
public class PhaseTest extends TestCase {
    /**
     * <p>Represents Phase instance for testing.</p>
     */
    private Phase phase;

    /**
     * <p>Creates a new instance of Phase.</p>
     *
     * @throws Exception to jUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();
        phase = new Phase();
    }

    /**
     * <p>Tears down the test environment.</p>
     *
     * @throws Exception to jUnit.
     */
    protected void tearDown() throws Exception {
        phase = null;
        super.tearDown();
    }

    /**
     * <p>Tests <code>Phase()</code> constructor.</p>
     */
    public void testPhase() {
        assertNotNull("Unable to instantiate Phase", phase);
    }

    /**
     * <p>Tests <code>getId()</code> and
     * <code>getId()</code> methods for accuracy.</p>
     */
    public void testGetSetId() {
        assertNull("Incorrect default id value", phase.getId());
        Long id = 1L;
        // set a id
        phase.setId(id);
        assertEquals("Incorrect id value after setting a new one",
            id, phase.getId());
    }

    /**
     * <p>Tests <code>setId(null)</code>.</p>
     */
    public void testSetIdAllowsNull() {
        // set null
        try {
            phase.setId(null);
            assertNull("Field 'id' should contain 'null' value",
                phase.getId());
        } catch (IllegalArgumentException e) {
            fail("Field 'id' should allow null values");
        }
    }


    /**
     * <p>Tests <code>getDescription()</code> and
     * <code>setDescription()</code> methods for accuracy.</p>
     */
    public void testGetSetDescription() {
        assertNull("Incorrect default description value", phase.getDescription());
        String value = "45";
        // set a description
        phase.setDescription(value);
        assertEquals("Incorrect description value after setting a new one",
            value, phase.getDescription());
    }

    /**
     * <p>Tests <code>setDescription(null)</code>.</p>
     */
    public void testDescriptionAllowsNull() {
        // set a description
        // set null
        try {
            phase.setDescription(null);
            assertNull("Field 'description' should contain 'null' value",
                phase.getDescription());
        } catch (IllegalArgumentException e) {
            fail("Field 'description' should allow null values");
        }
    }


}
