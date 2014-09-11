/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable;

/**
 * <p>
 * This class represents a property associated with a submission external content. It is a simple JavaBean that provides getters and
 * setters for all private attributes.
 * </p>
 * <p>
 * Thread Safety: This class is mutable and not thread safe.
 * </p>
 * 
 * @author TCSASSEMBER
 * @version 1.0 (TC Direct Replatforming Release 5)
 */
public class SubmissionExternalContentProperty extends IdentifiableDeliverableStructure {
    /**
     * Represents the serial version unique id.
     */
    private static final long serialVersionUID = -6941676485234290086L;

    /**
     * Represents the property name.
     */
    private String name;
    
    /**
     * Represents the property value.
     */
    private String value;
    
    /**
     * Empty constructor.
     */
    public SubmissionExternalContentProperty() {
        super();
    }

    /**
     * Gets the property name.
     * 
     * @return the property name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the property name.
     * 
     * @param name the property name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the property value.
     * 
     * @return the property value.
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the property value.
     * 
     * @param value the property value.
     */
    public void setValue(String value) {
        this.value = value;
    }
}
