/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.cloudvm;

import java.io.Serializable;
import java.util.Date;

/**
 * Represents the base entity for the other entities. It contains id, create date and modify date.
 *
 * Thread-safety: Mutable and not thread-safe.
 * 
 * Version 1.1 : Module Assembly - Cloud VM Service Notus Cloud Integration version 1.0
 * Added  {@link #toString()} implementation 
 * 
 * @author Standlove, TCSASSEMBLER
 * @version 1.1
 */
public abstract class AbstractIdEntity implements Serializable {
    /**
     * The serial version uid.
     */
    private static final long serialVersionUID = 3307994843080778739L;

    /**
     * Represents the entity id. It has getter & setter. It can be any value.
     */
    private long id;

    /**
     * Represents the creation date It has getter & setter. It can be any value.
     */
    private Date createDate = new Date();

    /**
     * Represents the modification date. It has getter & setter. It can be any value.
     */
    private Date modifyDate = new Date();

    /**
     * Empty constructor.
     */
    protected AbstractIdEntity() {
    }

    /**
     * Getter for the namesake instance variable. Simply return the namesake instance variable.
     *
     * @return field value
     */
    public long getId() {
        return id;
    }

    /**
     * Setter for the namesake instance variable. Simply set the value to the namesake instance variable.
     *
     * @param id value to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Getter for the namesake instance variable. Simply return the namesake instance variable.
     *
     * @return field value
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * Setter for the namesake instance variable. Simply set the value to the namesake instance variable.
     *
     * @param createDate value to set
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * Getter for the namesake instance variable. Simply return the namesake instance variable.
     *
     * @return field value
     */
    public Date getModifyDate() {
        return modifyDate;
    }

    /**
     * Setter for the namesake instance variable. Simply set the value to the namesake instance variable.
     *
     * @param modifyDate value to set
     */
    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AbstractIdEntity [id=" + id + ", createDate=" + createDate + ", modifyDate="
				+ modifyDate + "]";
	}
}

