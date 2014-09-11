/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable;

/**
 * <p>
 * This class represents the type of submission external content. It is a simple JavaBean that provides getters and
 * setters for all private attributes.
 * </p>
 * <p>
 * Thread Safety: This class is mutable and not thread safe.
 * </p>
 * 
 * @author TCSASSEMBER
 * @version 1.0 (TC Direct Replatforming Release 5)
 */
public class SubmissionExternalContentType extends IdentifiableDeliverableStructure {
    /**
     * Represents the serial version unique id.
     */
    private static final long serialVersionUID = 5596606347577404351L;

    /**
     * Represents the type name.
     */
    private String name;

    /**
     * Empty constructor.
     */
    public SubmissionExternalContentType() {
        super();
    }

    /**
     * Gets the type name.
     * 
     * @return the type name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the type name.
     * 
     * @param name
     *            the type name.
     */
    public void setName(String name) {
        this.name = name;
    }
}
