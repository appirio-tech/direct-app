/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.cloudvm;

/**
 * This class represents the VM instance type.
 *
 * Thread-safety: Mutable and not thread-safe.
 *
 * Version 1.1 : Module Assembly - Cloud VM Service Notus Cloud Integration version 1.0
 * Added  {@link #toString()} implementation
 * 
 * @author Standlove, kanakarajank
 * @version 1.0
 */
public class VMInstanceType extends AbstractIdEntity {
    /**
     * The serial version uid.
     */
    private static final long serialVersionUID = 4320570143750879L;

    /**
     * Represents the instance type name. It has getter & setter. It can be any value.
     */
    private String name;

    /**
     * Empty constructor.
     */
    public VMInstanceType() {
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "VMInstanceType [name=" + name + "]";
	}
}

