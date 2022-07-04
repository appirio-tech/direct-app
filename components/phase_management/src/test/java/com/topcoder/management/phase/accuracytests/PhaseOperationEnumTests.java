/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.phase.accuracytests;

import junit.framework.TestCase;

import com.topcoder.management.phase.PhaseOperationEnum;

/**
 * <p>
 * Accuracy test fixture for PhaseOperationEnum class.
 * </p>
 * @author Thinfox
 * @version 1.0
 */
public class PhaseOperationEnumTests extends TestCase {
    /**
     * <p>
     * Tests the public static field CANCEL.
     * </p>
     */
    public void testScheduled() {
        assertEquals("Incorrect name.", "cancel", PhaseOperationEnum.CANCEL.getName());
    }

    /**
     * <p>
     * Tests the public static field END.
     * </p>
     */
    public void testEnd() {
        assertEquals("Incorrect name.", "end", PhaseOperationEnum.END.getName());
    }

    /**
     * <p>
     * Tests the public static field START.
     * </p>
     */
    public void testStart() {
        assertEquals("Incorrect name.", "end", PhaseOperationEnum.END.getName());
    }

    /**
     * Tests the getName() method.
     */
    public void testGetName() {
        assertEquals("Incorrect name.", "cancel", PhaseOperationEnum.CANCEL.getName());
        assertEquals("Incorrect name.", "end", PhaseOperationEnum.END.getName());
        assertEquals("Incorrect name.", "end", PhaseOperationEnum.END.getName());
    }
}