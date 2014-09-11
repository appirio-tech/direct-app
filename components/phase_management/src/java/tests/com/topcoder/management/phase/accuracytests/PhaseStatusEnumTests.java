/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.phase.accuracytests;

import junit.framework.TestCase;

import com.topcoder.management.phase.PhaseStatusEnum;

/**
 * <p>
 * Accuracy test fixture for PhaseStatusEnum class.
 * </p>
 * @author Thinfox
 * @version 1.0
 */
public class PhaseStatusEnumTests extends TestCase {
    /**
     * <p>
     * Tests the public static field SCHEDULED.
     * </p>
     */
    public void testScheduled() {
        assertEquals("Incorrect id.", 1, PhaseStatusEnum.SCHEDULED.getId());
        assertEquals("Incorrect name.", "Scheduled", PhaseStatusEnum.SCHEDULED.getName());
    }

    /**
     * <p>
     * Tests the public static field OPEN.
     * </p>
     */
    public void testOpen() {
        assertEquals("Incorrect id.", 2, PhaseStatusEnum.OPEN.getId());
        assertEquals("Incorrect name.", "Open", PhaseStatusEnum.OPEN.getName());
    }

    /**
     * <p>
     * Tests the public static field CLOSED.
     * </p>
     */
    public void testClosed() {
        assertEquals("Incorrect id.", 3, PhaseStatusEnum.CLOSED.getId());
        assertEquals("Incorrect name.", "Closed", PhaseStatusEnum.CLOSED.getName());
    }

    /**
     * Tests the getId() method.
     */
    public void testGetId() {
        assertEquals("Incorrect id.", 1, PhaseStatusEnum.SCHEDULED.getId());
        assertEquals("Incorrect id.", 2, PhaseStatusEnum.OPEN.getId());
        assertEquals("Incorrect id.", 3, PhaseStatusEnum.CLOSED.getId());
    }

    /**
     * Tests the getName() method.
     */
    public void testGetName() {
        assertEquals("Incorrect name.", "Closed", PhaseStatusEnum.CLOSED.getName());
        assertEquals("Incorrect name.", "Open", PhaseStatusEnum.OPEN.getName());
        assertEquals("Incorrect name.", "Scheduled", PhaseStatusEnum.SCHEDULED.getName());
    }
}