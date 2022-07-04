/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.phase.stresstests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.management.phase.HandlerRegistryInfo;
import com.topcoder.management.phase.PhaseOperationEnum;
import com.topcoder.project.phases.PhaseType;

/**
 * <p>
 * Stress tests for HandlerRegistryInfo.
 * </p>
 *
 * @author still
 * @version 1.0
 */
public class HandlerRegistryInfoStressTest extends TestCase {
    /** The number of times each method will be run. */
    public static final int RUN_TIMES = 100000;
    /** The HandlerRegistryInfo instance used in this test. */
    private HandlerRegistryInfo info;

    /**
     * Test suite of HandlerRegistryInfoStressTest.
     *
     * @return Test suite of HandlerRegistryInfoStressTest.
     */
    public static Test suite() {
        return new TestSuite(HandlerRegistryInfoStressTest.class);
    }

    /**
     * Initialization for all tests here.
     * @throws Exception to Junit.
     */
    protected void setUp() throws Exception {
        PhaseType phaseType = new PhaseType(100, "PhaseType1");
        info = new HandlerRegistryInfo(phaseType, PhaseOperationEnum.START);
    }

    /**
     * <p>Stress test for HandlerRegistryInfo#HandlerRegistryInfo(PhaseType, PhaseOperationEnum).</p>
     *
     */
    public void testCtor() {
        PhaseType phaseType = new PhaseType(100, "PhaseType1");
        long start = System.currentTimeMillis();
        for (int i = 0; i < RUN_TIMES; i++) {
            assertNotNull("Failed to create HandlerRegistryInfo.",
                    new HandlerRegistryInfo(phaseType, PhaseOperationEnum.START));
        }
        long end = System.currentTimeMillis();
        System.out.println("Testing HandlerRegistryInfo(PhaseType, PhaseOperationEnum) for " + RUN_TIMES
            + " times costs " + (end - start) + "ms");
    }

    /**
     * <p>Stress test for HandlerRegistryInfo#equals(Object).</p>
     */
    public void testEquals() {
        PhaseType phaseType1 = new PhaseType(100, "PhaseType1");
        PhaseType phaseType2 = new PhaseType(100, "PhaseType2");
        PhaseType phaseType3 = new PhaseType(101, "PhaseType2");

        long start = System.currentTimeMillis();
        for (int i = 0; i < RUN_TIMES; i++) {
            assertTrue("Should return true.", info.equals(new HandlerRegistryInfo(phaseType1,
                PhaseOperationEnum.START)));
            assertTrue("Should return true.", info.equals(new HandlerRegistryInfo(phaseType2,
                PhaseOperationEnum.START)));
            assertFalse("Should return false.", info.equals(new HandlerRegistryInfo(phaseType1,
                PhaseOperationEnum.END)));
            assertFalse("Should return false.", info.equals(new HandlerRegistryInfo(phaseType3,
                PhaseOperationEnum.START)));
        }
        long end = System.currentTimeMillis();
        System.out.println("Testing equals(Object) for " + (RUN_TIMES * 4) + " times costs "
                + (end - start) + "ms");
    }

    /**
     * <p>Stress test for HandlerRegistryInfo#getOperation().</p>
     */
    public void testGetOperation() {
        PhaseType phaseType = new PhaseType(100, "PhaseType1");

        long start = System.currentTimeMillis();

        for (int i = 0; i < RUN_TIMES; i++) {
            assertEquals("Should be equal.", info.getOperation(), PhaseOperationEnum.START);
        }
        long end = System.currentTimeMillis();
        System.out.println("Testing getOperation() for " + RUN_TIMES + " times costs "
                + (end - start) + "ms");
    }

    /**
     * <p>Stress test for HandlerRegistryInfo#getType().</p>
     */
    public void testGetType() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < RUN_TIMES; i++) {
            PhaseType type = info.getType();
            assertEquals("Should be 100.", type.getId(), 100);
            assertEquals("Should be 'PhaseType1'", type.getName(), "PhaseType1");
        }
        long end = System.currentTimeMillis();
        System.out.println("Testing getType() for " + RUN_TIMES + " times costs "
                + (end - start) + "ms");
    }

    /**
     * <p>Stress test for HandlerRegistryInfo#hashCode().</p>
     */
    public void testHashCode() {
        long start = System.currentTimeMillis();

        for (int i = 0; i < RUN_TIMES; i++) {
            info.hashCode();
        }
        long end = System.currentTimeMillis();
        System.out.println("Testing hashCode() for " + RUN_TIMES + " times costs "
                + (end - start) + "ms");
    }
}
