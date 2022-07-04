/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.phase;

import com.topcoder.project.phases.PhaseType;

/**
 * <p>A simple data structure that encapsulates type/operation pairs
 * for use as keys in the handler registry maintained by
 * <code>PhaseManager</code>. As such its only methods are accessors
 * and methods necessary to make it usable as a key in a
 * <code>HashMap</code>.</p>
 *
 * <p>HandlerRegistryInfo is immutable and therefore thread safe.</p>
 *
 * @author AleaActaEst, RachaelLCook
 * @version 1.0
 * @see PhaseManager#registerHandler
 */

public class HandlerRegistryInfo {
    /**
     * The number of bits in a word.
     */
    private static final int ONE_WORD = 32;

    /**
     * The (non-null) type associated with the registry entry.
     */
    private final PhaseType type;

    /**
     * The (non-null) operation associated with the registry entry.
     */
    private final PhaseOperationEnum operation;

    /**
     * Constructs a new instance based on the specified type and operation.
     *
     * @throws IllegalArgumentException if either argument is null.
     *
     * @param type the phase type
     * @param operation the phase operation
     */
    public  HandlerRegistryInfo(PhaseType type, PhaseOperationEnum operation) {
        if (type == null) {
            throw new IllegalArgumentException("type must be non-null");
        }

        if (operation == null) {
            throw new IllegalArgumentException("operation must be non-null");
        }

        this.type = type;
        this.operation = operation;
    }

    /**
     * Returns the phase type associated with the registry entry. Note that this method returns a reference to the
     * actual type object in the registry. The caller should take care not to modify the returned object. Doing so
     * might cause the registry to become inconsistent.
     *
     * @return the phase type associated with the registry entry
     */
    public PhaseType getType() {
        return this.type;
    }

    /**
     * Returns the phase operation associated with the registry entry.
     *
     * @return the phase operation associated with the registry entry
     */
    public PhaseOperationEnum getOperation() {
        return this.operation;
    }

    /**
     * Returns true if <code>obj</code> is a <code>HandlerRegistryInfo</code> instance and has the same type and
     * operation.
     *
     * @param obj object to test for equality
     * @return true if the two are the same; false otherwise.
     */
    public boolean equals(Object obj) {
        if (!(obj instanceof HandlerRegistryInfo)) {
            return false;
        }

        HandlerRegistryInfo hri = (HandlerRegistryInfo) obj;
        return (hri.getType().getId() == getType().getId())
            && (hri.getOperation().getName().equals(getOperation().getName()));
    }

    /**
     * Returns the hash code for this object. The hash code is formed by taking the exclusive or of the hash codes
     * of the type and the operation name.
     *
     * @return the hash code for this object
     */
    public int hashCode() {
        long id = type.getId();
        return (int) (id ^ (id >>> ONE_WORD)) ^ operation.getName().hashCode();
    }
}
