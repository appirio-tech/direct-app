/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto;

import java.io.Serializable;

/**
 * <p>A <code>DTO</code> class providing the data for single user.</p>
 *
 * @author TCSDEVELOPER
 * @version 1.0 (Direct Software Submission Viewer assembly)
 */
public class UserDTO implements Serializable {

    /**
     * <p>A <code>long</code> providing the user ID.</p>
     */
    private long id;

    /**
     * <p>A <code>String</code> providing the user handle.</p>
     */
    private String handle;

    /**
     * <p>Constructs new <code>UserDTO</code> instance. This implementation does nothing.</p>
     */
    public UserDTO() {
    }

    /**
     * <p>Gets the user handle.</p>
     *
     * @return a <code>String</code> providing the user handle.
     */
    public String getHandle() {
        return this.handle;
    }

    /**
     * <p>Sets the user handle.</p>
     *
     * @param handle a <code>String</code> providing the user handle.
     */
    public void setHandle(String handle) {
        this.handle = handle;
    }

    /**
     * <p>Gets the user ID.</p>
     *
     * @return a <code>long</code> providing the user ID.
     */
    public long getId() {
        return this.id;
    }

    /**
     * <p>Sets the user ID.</p>
     *
     * @param id a <code>long</code> providing the user ID.
     */
    public void setId(long id) {
        this.id = id;
    }

    public String toString() {
        return "UserDTO{" +
               "id=" + id +
               ", handle='" + handle + '\'' +
               '}';
    }
}
