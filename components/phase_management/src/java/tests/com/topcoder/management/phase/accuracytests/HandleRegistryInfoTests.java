/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.phase.accuracytests;

import junit.framework.TestCase;

import com.topcoder.management.phase.HandlerRegistryInfo;
import com.topcoder.management.phase.PhaseOperationEnum;
import com.topcoder.project.phases.PhaseType;

/**
 * <p>
 * Accuracy test fixture for HandlerRegistryIndo class.
 * </p>
 * @author Thinfox
 * @version 1.0
 */
public class HandleRegistryInfoTests extends TestCase {
    /**
     * The default HandlerRegistryInfo instance on which to perform tests.
     */
    private HandlerRegistryInfo info = null;

    /**
     * The default PhaseType instance used for tests.
     */
    private PhaseType phaseType = null;

    /**
     * The default PhaseOperationEnum instance used for tests.
     */
    private PhaseOperationEnum operation = null;

    /**
     * Creates the default instances for tests.
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        phaseType = new PhaseType(1, "type1");
        operation = PhaseOperationEnum.START;
        info = new HandlerRegistryInfo(phaseType, operation);
    }

    /**
     * Tests the constructor.
     */
    public void testCtor() {
        info = new HandlerRegistryInfo(phaseType, operation);
        assertNotNull("Creation failed.", info);
        assertEquals("Incorrect phase type.", phaseType, info.getType());
        assertEquals("Incorrect operation.", operation, info.getOperation());
    }

    /**
     * Tests the getType() method.
     */
    public void testGetType() {
        assertEquals("Incorrect phase type.", phaseType, info.getType());
    }

    /**
     * Tests the getOperation() method.
     */
    public void testGetOperation() {
        assertEquals("Incorrect operation.", operation, info.getOperation());
    }

    /**
     * Tests the equals() method.
     */
    public void testEquals() {
        assertTrue("Identical infos should be equal to each other.", info.equals(info));
        assertTrue("Infos with same types and operations should be equal to each other.", info
            .equals(new HandlerRegistryInfo(phaseType, operation)));

        PhaseType type2 = new PhaseType(2, "type2");
        assertFalse("infos with different types should not be equal.", info.equals(new HandlerRegistryInfo(
            type2, operation)));
        assertFalse("infos with different operations should not be equal.", info
            .equals(new HandlerRegistryInfo(phaseType, PhaseOperationEnum.END)));
    }

    /**
     * Tests the hashCode() method.
     */
    public void testHashCode() {
        assertEquals("Infos with same types and operations should have the same hash code.", info.hashCode(),
            new HandlerRegistryInfo(phaseType, operation).hashCode());
        PhaseType type2 = new PhaseType(2, "type2");
        assertFalse("infos with different types should have different hash codes.",
            info.hashCode() == new HandlerRegistryInfo(type2, operation).hashCode());
        assertFalse("infos with different operations should have different hash codes.",
            info.hashCode() == new HandlerRegistryInfo(phaseType, PhaseOperationEnum.END).hashCode());
    }
}

