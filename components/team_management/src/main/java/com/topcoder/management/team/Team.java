/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.team;

import java.io.Serializable;

/**
 * <p>
 * The interface that holds the complete team information, which includes the team information and its currently
 * configured positions. It is serializable so it can be used in a remote environment.
 * </p>
 * <p>
 * This interface follows java bean conventions for defining setters and getters for these properties.
 * </p>
 * <p>
 * It has one implementation: TeamImpl.
 * </p>
 * <p>
 * Thread Safety: There are no restrictions on thread safety in implementations.
 * </p>
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
public interface Team extends Serializable {
    /**
     * Gets the teamHeader field value.
     * @return The teamHeader field value
     */
    public TeamHeader getTeamHeader();

    /**
     * Sets the teamHeader field value.
     * @param teamHeader
     *            The teamHeader field value
     * @throws IllegalArgumentException
     *             If teamHeader is null
     */
    public void setTeamHeader(TeamHeader teamHeader);

    /**
     * Gets the positions field value.
     * @return The positions field value
     */
    public TeamPosition[] getPositions();

    /**
     * Sets the positions field value.
     * @param positions
     *            The positions field value
     * @throws IllegalArgumentException
     *             If positions contains null elements
     */
    public void setPositions(TeamPosition[] positions);
}
