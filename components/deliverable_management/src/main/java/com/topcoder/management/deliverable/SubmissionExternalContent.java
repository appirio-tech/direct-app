/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable;

import java.util.List;

/**
 * <p>
 * This class represents an submission external content associated with a submission declaration. It is a simple
 * JavaBean that provides getters and setters for all private attributes.
 * </p>
 * <p>
 * Thread Safety: This class is mutable and not thread safe.
 * </p>
 * 
 * @author TCSASSEMBER
 * @version 1.0 (TC Direct Replatforming Release 5)
 */
public class SubmissionExternalContent extends IdentifiableDeliverableStructure {
    /**
     * Represents the serial version unique id.
     */
    private static final long serialVersionUID = -6345874691693793426L;

    /**
     * Represents the display position.
     */
    private int displayPosition;

    /**
     * Represents the type of the this <code>SubmissionExternalContent</code>.
     */
    private SubmissionExternalContentType externalContentType;

    /**
     * A <code>List</code> providing the <code>SubmissionExternalContentProperty</code> associated with this
     * <code>SubmissionExternalContent</code>.
     */
    private List<SubmissionExternalContentProperty> externalContentProperties;

    /**
     * Empty constructor.
     */
    public SubmissionExternalContent() {
        super();
    }

    /**
     * Gets the display position.
     * 
     * @return the display position.
     */
    public int getDisplayPosition() {
        return displayPosition;
    }

    /**
     * Sets the display position.
     * 
     * @param displayPosition
     *            the display position.
     */
    public void setDisplayPosition(int displayPosition) {
        this.displayPosition = displayPosition;
    }

    /**
     * Gets the type of the this <code>SubmissionExternalContent</code>.
     * 
     * @return the type of the this <code>SubmissionExternalContent</code>.
     */
    public SubmissionExternalContentType getExternalContentType() {
        return externalContentType;
    }

    /**
     * Sets the type of the this <code>SubmissionExternalContent</code>.
     * 
     * @param externalContentType
     *            the type of the this <code>SubmissionExternalContent</code>.
     */
    public void setExternalContentType(SubmissionExternalContentType externalContentType) {
        this.externalContentType = externalContentType;
    }

    /**
     * Gets the <code>SubmissionExternalContentProperty</code> associated with this
     * <code>SubmissionExternalContent</code>.
     * 
     * @return the <code>SubmissionExternalContentProperty</code> associated with this
     *         <code>SubmissionExternalContent</code>.
     */
    public List<SubmissionExternalContentProperty> getExternalContentProperties() {
        return externalContentProperties;
    }

    /**
     * Sets the <code>SubmissionExternalContentProperty</code> associated with this
     * <code>SubmissionExternalContent</code>.
     * 
     * @param externalContentProperties
     *            the <code>SubmissionExternalContentProperty</code> associated with this
     *            <code>SubmissionExternalContent</code>.
     */
    public void setExternalContentProperties(List<SubmissionExternalContentProperty> externalContentProperties) {
        this.externalContentProperties = externalContentProperties;
    }
}
