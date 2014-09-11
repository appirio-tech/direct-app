/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.cloudvm;

/**
 * This class represents the VM security group.
 *
 * Thread-safety: Mutable and not thread-safe.
 *
 * @author Standlove, TCSDEVELOPER
 * @version 1.0
 */
public class VMSecurityGroup extends AbstractIdEntity {
    /**
     * The serial version uid.
     */
    private static final long serialVersionUID = 234098954052583408L;

    /**
     * Represents the security group name. It has getter & setter. It can be any value.
     */
    private String name;

    /**
     * Empty constructor.
     */
    public VMSecurityGroup() {
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

