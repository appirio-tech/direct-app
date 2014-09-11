/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.cloudvm;

/**
 * This class represents the VM user data.
 *
 * Thread-safety: Mutable and not thread-safe.
 *
 * @author Standlove, TCSDEVELOPER
 * @version 1.0
 */
public class VMUserData extends AbstractIdEntity {
    /**
     * The serial version uid.
     */
    private static final long serialVersionUID = 8734108154023844L;

    /**
     * Represents the user data key. It has getter & setter. It can be any value.
     */
    private String key;

    /**
     * Represents the user data value. It has getter & setter. It can be any value.
     */
    private String value;

    /**
     * Represents the user data value is encrypted or not. It has getter & setter. It can be any value.
     */
    private boolean encrypted;

    /**
     * Empty constructor.
     */
    public VMUserData() {
    }

    /**
     * Getter for the namesake instance variable. Simply return the namesake instance variable.
     *
     * @return field value
     */
    public String getKey() {
        return key;
    }

    /**
     * Setter for the namesake instance variable. Simply set the value to the namesake instance variable.
     *
     * @param key value to set
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * Getter for the namesake instance variable. Simply return the namesake instance variable.
     *
     * @return field value
     */
    public String getValue() {
        return value;
    }

    /**
     * Setter for the namesake instance variable. Simply set the value to the namesake instance variable.
     *
     * @param value value to set
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Getter for the namesake instance variable. Simply return the namesake instance variable.
     *
     * @return field value
     */
    public boolean isEncrypted() {
        return encrypted;
    }

    /**
     * Setter for the namesake instance variable. Simply set the value to the namesake instance variable.
     *
     * @param encrypted value to set
     */
    public void setEncrypted(boolean encrypted) {
        this.encrypted = encrypted;
    }
}

