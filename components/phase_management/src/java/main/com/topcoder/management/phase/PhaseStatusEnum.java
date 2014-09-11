/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.phase;

import com.topcoder.util.collection.typesafeenum.Enum;

/**
 * <p>A enumeration of currently supported phase statuses.</p>
 *
 * <p>PhaseStatus is an Enum and thus poses no thread-safety issues.</p>
 *
 * @author AleaActaEst, RachaelLCook
 * @version 1.0
 */

public class PhaseStatusEnum extends Enum {
    /**
     * Represents the scheduled status for a phase as mapped to the data base (uses id of 1).
     */
    public static final PhaseStatusEnum SCHEDULED = new PhaseStatusEnum("Scheduled", 1);

    /**
     * Represents the open status for a phase as mapped to the data base (uses id of 2).
     */
    public static final PhaseStatusEnum OPEN = new PhaseStatusEnum("Open", 2);

    /**
     * Represents the closed status for a phase as mapped to the data base (uses id of 3).
     */
    public static final PhaseStatusEnum CLOSED = new PhaseStatusEnum("Closed", 3);

    /**
     * Represents the name of the phase status for this instance. It will currently contain "scheduled", "open", or
     * "closed" only.
     */
    private String name;

    /**
     * Represents the id of the record in the data store for the specific phase. It will currently contain 1, 2, or 3.
     */
    private int id;

    /**
     * Private constructor to prevent instantiation 'from the outside'. When called, it will assign
     * <code>name</code> to * <code>this.name</code> and <code>id</code> to <code>this.id</code>.
     *
     * @throws IllegalArgumentException if name is <code>null</code> or empty
     *
     * @param name the name of the phase status
     * @param id the store id of the phase
     */
    private  PhaseStatusEnum(String name, int id) {
        if (name == null) {
            throw new IllegalArgumentException("name must be a non-null string");
        }

        this.name = name;
        this.id = id;
    }

    /**
     * Accessor for the name of this phase status.
     *
     * @return the name of this phase status
     */
    public String getName() {
        return this.name;
    }

    /**
     * Accessor for the ID of this phase status.
     *
     * @return the ID of this phase status
     */
    public int getId() {
        return this.id;
    }
}
