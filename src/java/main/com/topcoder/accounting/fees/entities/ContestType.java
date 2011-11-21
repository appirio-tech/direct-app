/*
 * Copyright (C) 2010 - 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.accounting.fees.entities;

/**
 * Represents the contest type entity. It's a DTO object.
 * 
 * Thread safety: The class is mutable and not thread safe.
 * 
 * @author winstips, TCSDEVELOPER
 * @version 1.0
 */
public class ContestType {
    /**
     * Represents the type id. It is managed with a getter and setter. It may have any value. It is fully mutable.
     */
    private int typeId;
    /**
     * Represents the description. It is managed with a getter and setter. It may have any value. It is fully mutable.
     */
    private String description;
    /**
     * Represents the studio flag. It is managed with a getter and setter. It may have any value. It is fully mutable.
     */
    private boolean isStudio;
    /**
     * Represents existing flag. It is managed with a getter and setter. It may have any value. It is fully mutable.
     */
    private boolean isExisting;

    /**
     * Default Constructor.
     */
    public ContestType() {
    }

    /**
     * Returns the typeId field value.
     * 
     * @return typeId field value.
     */
    public int getTypeId() {
        return typeId;
    }

    /**
     * Sets the given value to typeId field.
     * 
     * @param typeId
     *            - the given value to set.
     */
    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    /**
     * Returns the description field value.
     * 
     * @return description field value.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the given value to description field.
     * 
     * @param description
     *            - the given value to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the isStudio field value.
     * 
     * @return isStudio field value.
     */
    public boolean isStudio() {
        return isStudio;
    }

    /**
     * Sets the given value to isStudio field.
     * 
     * @param isStudio
     *            - the given value to set.
     */
    public void setStudio(boolean isStudio) {
        this.isStudio = isStudio;
    }

    /**
     * Returns the isExisting field value.
     * 
     * @return isExisting field value.
     */
    public boolean isExisting() {
        return isExisting;
    }

    /**
     * Sets the given value to isExisting field.
     * 
     * @param isExisting
     *            - the given value to set.
     */
    public void setExisting(boolean isExisting) {
        this.isExisting = isExisting;
    }
}
