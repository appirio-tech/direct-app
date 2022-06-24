/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.asset;

/**
 * <p>
 * The asset container type enum. There are 3 types now:
 * <ul>
 *     <li>GLOBAL</li>
 *     <li>PROJECT</li>
 *     <li>CLIENT</li>
 * </ul>
 * </p>
 *
 * @author TCSASSEMBLER
 * @version 1.0 (TopCoder Cockpit Asset View And Basic Upload)
 */
public enum AssetContainerType {
    /**
     * The global container type.
     */
    GLOBAL("GLOBAL"),

    /**
     * The project container type.
     */
    PROJECT("PROJECT"),

    /**
     * The client container type.
     */
    CLIENT("CLIENT");

    /**
     * The asset container type name.
     */
    private final String name;

    /**
     * The private constructor for the <code>AssetContainerType</code> class.
     * @param name the type name.
     */
    private AssetContainerType(String name) {
        this.name = name;
    }

    /**
     * Checks if the name is equal.
     *
     * @param otherName
     * @return
     */
    public boolean equalsName(String otherName) {
        return (otherName == null) ? false : name.equalsIgnoreCase(otherName);
    }

    /**
     * Returns the string representation of the AssetContainerType
     *
     * @return the string representation of the AssetContainerType
     */
    public String toString() {
        return name;
    }
}
