/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.scorecard.data.accuracytests;

import com.topcoder.management.scorecard.data.ScorecardStatus;

import junit.framework.TestCase;


/**
 * Tests for ScorecardStatus class.
 *
 * @author crackme
 * @version 1.0
 */
public class TestScorecardStatus extends TestCase {
    /** the id of this instance. */
    private final long id = new Long(1234567).longValue();

    /**
     * Tests ScorecardStatus() method with accuracy state.
     */
    public void testScorecardStatusAccuracy1() {
        ScorecardStatus status = new ScorecardStatus();
        assertNull("constructor is wrong.", status.getName());
        assertEquals("constructor is wrong.", -1, status.getId());
    }

    /**
     * Tests ScorecardStatus(long id) method with accuracy state.
     */
    public void testScorecardStatusAccuracy2() {
        ScorecardStatus status = new ScorecardStatus(id);
        assertNull("constructor is wrong.", status.getName());
        assertEquals("constructor is wrong.", id, status.getId());
    }

    /**
     * Tests ScorecardStatus(long id, String name) method with accuracy state.
     */
    public void testScorecardStatusAccuracy3() {
        ScorecardStatus status = new ScorecardStatus(id, "new name");
        assertEquals("constructor is wrong.", "new name", status.getName());
        assertEquals("constructor is wrong.", id, status.getId());
    }
}
