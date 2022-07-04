/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto;

/**
 * <p>An enumeration over the possible types of permissions to be granted to users on accessing contest details.</p>
 *
 * @author isv
 * @version 1.0
 */
public enum PermissionType {

    /**
     * <p>A <code>PermissionType</code> corresponding to <code>Read</code> permission.</p>
     */
    READ("Read: Allows member to read contest data"),

    /**
     * <p>A <code>PermissionType</code> corresponding to <code>Write</code> permission.</p>
     */
    WRITE("Write: Allows member to read contest data and modify it"),

    /**
     * <p>A <code>PermissionType</code> corresponding to <code>Full</code> permission.</p>
     */
    FULL("Full Access: Allows member to read and modify contest data");

    /**
     * <p>A <code>String</code> providing the textual description of the permission.</p>
     */
    private String description;

    /**
     * <p>Constructs new <code>PermissionType</code> instance with specified properties.</p>
     *
     * @param description a  <code>String</code> providing the textual description of the permission.</p>
     */
    private PermissionType(String description) {
        this.description = description;
    }

    /**
     * <p>Gets the permission description.</p>
     *
     * @return a <code>String</code> providing the textual description of the permission.
     */
    public String getDescription() {
        return description;
    }
}
