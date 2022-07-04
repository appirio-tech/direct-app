/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.phase;

import com.topcoder.util.collection.typesafeenum.Enum;

/**
 * <p>A convenient enumeration of phase operations as defined by the Phase Manager API which currently allows
 * operations of "start", "end", and "cancel". This is provided as a utility to the user when they need to identify
 * the phase operation when registering a phase handler with a manager. This is used when creating
 * HandlerRegistryInfo instances for handler registrations (used as keys to id handlers), which need to know what
 * operation the handler will handle (as well as which phase status it will deal with - which is covered by
 * PhaseStatusEnum).</p>
 *
 * <p>PhaseOperation is an Enum and thus poses no thread-safety issues.</p>
 *
 * @author AleaActaEst, RachaelLCook
 * @version 1.0
 */

public class PhaseOperationEnum extends Enum {

    /**
     * Represents the start operation for a phase. This is used to map PhaseHandlers to specific operations.
     */
    public static final PhaseOperationEnum START = new PhaseOperationEnum("start");

    /**
     * Represents the end operation for a phase. This is used to map PhaseHandlers to specific operations.
     */
    public static final PhaseOperationEnum END = new PhaseOperationEnum("end");

    /**
     * Represents the cancel operation for a phase. This is used to map PhaseHandlers to specific operations.
     */
    public static final PhaseOperationEnum CANCEL = new PhaseOperationEnum("cancel");

    /**
     * Represents the name of the operation for this instance. It will contain "start", "end", or "cancel" only.
     */
    private final String name;

    /**
     * Private constructor to prevent instantiation 'from the outside'. When called will assign the
     * <code>name</code> to <code>this.name</code>.
     *
     * @throws IllegalArgumentException if name is null or empty (shoudl never happen though)
     *
     * @param name the name of the operation
     */
    private  PhaseOperationEnum(String name) {
        if (name == null) {
            throw new IllegalArgumentException("name must a be non-null string");
        }

        if (name.length() == 0) {
            throw new IllegalArgumentException("name must a be non-empty string");
        }

        this.name = name;
    }

    /**
     * Accessor for the name of this operation.
     *
     * @return the name of this operation
     */
    public String getName() {
        return this.name;
    }
}
