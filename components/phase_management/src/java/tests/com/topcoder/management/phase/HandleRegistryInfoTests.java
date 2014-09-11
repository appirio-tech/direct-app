/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.phase;

/**
 * Test suite for HandleRegistryInfoTests.
 *
 * @author RachaelLCook
 * @version 1.0
 */

public class HandleRegistryInfoTests extends PhaseManagementTestCase {
    /**
     * A test registry entry.
     */
    private final HandlerRegistryInfo oneStart = new HandlerRegistryInfo(PHASE_TYPE_ONE, PhaseOperationEnum.START);

    /**
     * A test registry entry.
     */
    private final HandlerRegistryInfo twoStart = new HandlerRegistryInfo(PHASE_TYPE_TWO, PhaseOperationEnum.START);

    /**
     * A test registry entry.
     */
    private final HandlerRegistryInfo oneEnd = new HandlerRegistryInfo(PHASE_TYPE_ONE, PhaseOperationEnum.END);

    /**
     * A test registry entry.
     */
    private final HandlerRegistryInfo oneStart2 = new HandlerRegistryInfo(PHASE_TYPE_ONE, PhaseOperationEnum.START);

    /**
     * Tests invalid constructor args.
     */
    public void testconstructor() {

        try {
            new HandlerRegistryInfo(PHASE_TYPE_ONE, null);
            fail("construct should throw IllegalArgumentException for null argument");
        } catch (IllegalArgumentException ex) {
            // pass
        }

        try {
            new HandlerRegistryInfo(null, PhaseOperationEnum.START);
            fail("construct should throw IllegalArgumentException for null argument");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests the getType method.
     */
    public void testgetType() {
        assertEquals("getType should return ONE", oneStart.getType(), PHASE_TYPE_ONE);
    }

    /**
     * Tests the getOperation method.
     */
    public void testgetOperation() {
        assertEquals("getOperation should return START", oneStart.getOperation(), PhaseOperationEnum.START);
    }

    /**
     * Tests the equals method.
     */
    public void testequals() {
        assertTrue("oneStart should equal oneStart2", oneStart.equals(oneStart2));
        assertFalse("oneStart should not equal twoStart", oneStart.equals(twoStart));
        assertFalse("oneStart should not equal oneEnd", oneStart.equals(oneEnd));
    }

    /**
     * Tests the hashCode method.
     */
    public void testhashCode() {
        assertEquals("hash code for HandlerRegistryInfo should equal XOR of component hash codes",
                     oneStart.hashCode(),
                     new Long(PHASE_TYPE_ONE.getId()).hashCode() ^ PhaseOperationEnum.START.getName().hashCode());
    }
}

