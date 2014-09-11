/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.cloudvm;

/**
 * This class represents the VM account user for the Amazon EC2. It contains the user id.
 *
 * Thread-safety: Mutable and not thread-safe.
 *
 * Version 1.1 : Module Assembly - Cloud VM Service Notus Cloud Integration version 1.0
 * Added  {@link #toString()} implementation
 *  
 * @author kanakarajank
 * 
 * @version 1.1
 */
public class VMAccountUser extends AbstractIdEntity {
    /**
     * The serial version uid.
     */
    private static final long serialVersionUID = 2742320281043253L;

    /**
     * Represents the VM account id.
     */
    private long vmAccountId;

    /**
     * User id.
     */
    private long userId;

    /**
     * Empty constructor.
     */
    public VMAccountUser() {
    }

    /**
     * Gets the VM Account id.
     * @return  the VM Account id.
     */
    public long getVmAccountId() {
        return vmAccountId;
    }

    /**
     * Sets the VM Account id.
     * @param vmAccountId  the VM Account id.
     */
    public void setVmAccountId(long vmAccountId) {
        this.vmAccountId = vmAccountId;
    }


    /**
     * Getter for the namesake instance variable. Simply return the namesake instance variable.
     *
     * @return the user id.
     */
    public long getUserId() {
        return userId;
    }

    /**
     * Setter for the namesake instance variable. Simply set the value to the namesake instance variable.
     *
     * @param userId the user id.
     */
    public void setUserId(long userId) {
        this.userId = userId;
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "VMAccountUser [vmAccountId=" + vmAccountId + ", userId=" + userId + "]";
	}
}

