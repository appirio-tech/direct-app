/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.dashboard;

/**
 * <p>
 * A enum used to represent color of each status.
 * </p>
 * 
 * @author TCSASSEMBLER
 * @version 1.0.0 (From Direct - Project Dashboard Assembly)
 */
public enum DashboardStatusColor {
    /**
     * The green color.
     */
    GREEN("green"),

    /**
     * The red color.
     */
    RED("red"),

    /**
     * The orange color.
     */
    ORANGE("orange");

    /**
     * <p>
     * A <code>String</code> providing the color name. Such a name serves as a
     * textual presentation of the status color.
     * </p>
     */
    private String name;

    /**
     * <p>
     * Constructs new <code>DashboardStatusColor</code> instance with specified
     * parameter.
     * </p>
     * 
     * @param name
     *            a <code>String</code> providing the status color name.
     */
    private DashboardStatusColor(String n) {
        name = n;
    }

    /**
     * Retrieves the name field.
     * 
     * @return the name
     */
    public String getName() {
        return name;
    }
}
