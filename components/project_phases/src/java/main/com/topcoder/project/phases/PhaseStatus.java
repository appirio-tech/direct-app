/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.phases;

import java.io.Serializable;

/**
 * <p>
 * This class represents the phase status. A phase status consists of a numeric identifier and a name. This class is
 * serializable.
 * </p>
 * <p>
 * This class defines three build in phase types - SCHEDULED, OPEN and CLOSED.
 * </p>
 * <p>
 * Thread Safety: This class is mutable. so it's not thread safe.
 * </p>
 *
 * @author oldbig, littlebull
 * @version 2.0
 */
public class PhaseStatus implements Serializable {
    /**
     * Represents the scheduled status id.
     */
    private static final int SCHEDULED_ID = 1;

    /**
     * Represents the scheduled status name.
     */
    private static final String SCHEDULED_NAME = "Scheduled";

    /**
     * Represents the open status id.
     */
    private static final int OPEN_ID = 2;

    /**
     * Represents the open status name.
     */
    private static final String OPEN_NAME = "Open";

    /**
     * Represents the closed status id.
     */
    private static final int CLOSED_ID = 3;

    /**
     * Represents the closed status name.
     */
    private static final String CLOSED_NAME = "Closed";

    /**
     * A build in phase status that represents the phase is scheduled.
     */
    public static final PhaseStatus SCHEDULED = new PhaseStatus(SCHEDULED_ID, SCHEDULED_NAME);

    /**
     * A build in phase status that represents the phase is open.
     */
    public static final PhaseStatus OPEN = new PhaseStatus(OPEN_ID, OPEN_NAME);

    /**
     * A build in phase status that represents the phase is closed.
     */
    public static final PhaseStatus CLOSED = new PhaseStatus(CLOSED_ID, CLOSED_NAME);

    /**
     * Represents the phase status id. Initialized in the constructor and could be accessed via getter and setter
     * method. The value could not be negative .
     */
    private long id;

    /**
     * Represents the phase status name. Initialized in the constructor and could be accessed via getter and setter
     * method. The value could not be null .
     */
    private String name;

    /**
     * The constructor with the phase status id and name.
     *
     * @param id
     *            the phase status id
     * @param name
     *            the phase status name
     * @throws IllegalArgumentException
     *             if <code>id</code> is negative or <code>name</code> is null
     */
    public PhaseStatus(long id, String name) {
        setId(id);
        setName(name);
    }

	
	/**
     * The constructor with the phase status id and name.
     *
     * @param id
     *            the phase status id
     * @param name
     *            the phase status name
     * @throws IllegalArgumentException
     *             if <code>id</code> is negative or <code>name</code> is null
     */
    public PhaseStatus() {

    }

    /**
     * Gets the phase status id.
     *
     * @return the phase status id
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the phase status id.
     *
     * @param id
     *            the phase status id
     * @throws IllegalArgumentException
     *             if <code>id</code> is negative
     */
    public void setId(long id) {
        ProjectPhaseHelper.checkLongNotNegative(id, "id");

        this.id = id;
    }

    /**
     * Gets the phase status name.
     *
     * @return the phase status name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the phase status name.
     *
     * @param name
     *            the phase status name
     * @throws IllegalArgumentException
     *             if <code>name</code> is null
     */
    public void setName(String name) {
        ProjectPhaseHelper.checkObjectNotNull(name, "name");

        this.name = name;
    }
}
