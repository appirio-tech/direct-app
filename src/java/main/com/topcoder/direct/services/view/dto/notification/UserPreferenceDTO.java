/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.notification;

import java.io.Serializable;

/**
 * <p>
 * A DTO used to represent user preference.
 * </p>
 * 
 * @author tangzx
 * @version 1.0
 * @since BUGR-4597
 */
public class UserPreferenceDTO implements Serializable {
    /**
     * Generated serial version UID.
     */
    private static final long serialVersionUID = 3292512325470324208L;
    
    /**
     * The preference id.
     */
    private int preferenceId;
    
    /**
     * The name.
     */
    private String name;
    
    /**
     * The description.
     */
    private String desc;
    
    /**
     * The value.
     */
    private String value;

    /**
     * Get the preferenceId field.
     *
     * @return the preferenceId
     */
    public int getPreferenceId() {
        return preferenceId;
    }

    /**
     * Set the preferenceId field.
     *
     * @param preferenceId the preferenceId to set
     */
    public void setPreferenceId(int preferenceId) {
        this.preferenceId = preferenceId;
    }

    /**
     * Get the name field.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name field.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the desc field.
     *
     * @return the desc
     */
    public String getDesc() {
        return desc;
    }

    /**
     * Set the desc field.
     *
     * @param desc the desc to set
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * Get the value field.
     *
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * Set the value field.
     *
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }
    
    
}
