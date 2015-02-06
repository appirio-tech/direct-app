/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.cloudvm;

/**
 * This class represents the VM usage.
 *
 * Thread-safety: Mutable and not thread-safe.
 *
 * @author TCSASSEMBLIER
 * @version 1.0
 */
public class VMUsage extends AbstractIdEntity {
    /**
     * The serial version uid.
     */
    private static final long serialVersionUID = 7823130752304832L;

    /**
     * Represents the content type name. It has getter & setter. It can be any value.
     */
    private String name;

    /**
     * Empty constructor.
     */
    public VMUsage() {
    }

    /**
     * Getter for the namesake instance variable. Simply return the namesake instance variable.
     *
     * @return field value
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for the namesake instance variable. Simply set the value to the namesake instance variable.
     *
     * @param name value to set
     */
    public void setName(String name) {
        this.name = name;
    }
}

