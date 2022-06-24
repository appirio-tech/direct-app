/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.scorecard.data.accuracytests;

import com.topcoder.management.scorecard.data.ScorecardType;

import junit.framework.TestCase;


/**
 * Tests for ScorecardType class.
 *
 * @author crackme
 * @version 1.0
 */
public class TestScorecardType extends TestCase {
    /** the id of this instance. */
    private final long id = new Long(1234567).longValue();

    /**
     * Tests ScorecardType() method with accuracy state.
     */
    public void testScorecardTypeAccuracy1() {
        ScorecardType type = new ScorecardType();
        assertNull("constructor is wrong.", type.getName());
        assertEquals("constructor is wrong.", -1, type.getId());
    }

    /**
     * Tests ScorecardType(long id) method with accuracy state.
     */
    public void testScorecardTypeAccuracy2() {
        ScorecardType type = new ScorecardType(id);
        assertNull("constructor is wrong.", type.getName());
        assertEquals("constructor is wrong.", id, type.getId());
    }

    /**
     * Tests ScorecardType(long id, String name) method with accuracy state.
     */
    public void testScorecardTypeAccuracy3() {
        ScorecardType type = new ScorecardType(id, "new name");
        assertEquals("constructor is wrong.", "new name", type.getName());
        assertEquals("constructor is wrong.", id, type.getId());
    }
}
