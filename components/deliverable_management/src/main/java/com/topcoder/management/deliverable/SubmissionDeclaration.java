/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable;

import java.util.List;

/**
 * <p>
 * This class represents an submission declaration associated with a submission. It is a simple JavaBean that provides
 * getters and setters for all private attributes.
 * </p>
 * <p>
 * Thread Safety: This class is mutable and not thread safe.
 * </p>
 * 
 * @author TCSASSEMBER
 * @version 1.0 (TC Direct Replatforming Release 5)
 */
public class SubmissionDeclaration extends IdentifiableDeliverableStructure {
    /**
     * Represents the serial version unique id.
     */
    private static final long serialVersionUID = -5263905028897382885L;

    /**
     * Represents the user comment.
     */
    private String comment;

    /**
     * A flag represents whether the submission declaration contains external contents.
     */
    private boolean hasExternalContent;

    /**
     * A <code>List</code> providing the <code>SubmissionExternalContent</code> of the submission declaration.
     */
    private List<SubmissionExternalContent> externalContents;

    /**
     * Empty constructor.
     */
    public SubmissionDeclaration() {
        super();
    }

    /**
     * Gets the user comment.
     * 
     * @return the user comment.
     */
    public String getComment() {
        return comment;
    }

    /**
     * Sets the user comment.
     * 
     * @param comment
     *            the user comment.
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * Gets the flag represents whether the submission declaration contains external contents.
     * 
     * @return true if the submission declaration contains external contents, false otherwise.
     */
    public boolean hasExternalContent() {
        return hasExternalContent;
    }

    /**
     * Sets the flag represents whether the submission declaration contains external contents.
     * 
     * @param hasExternalContent
     *            true if the submission declaration contains external contents, false otherwise.
     */
    public void setHasExternalContent(boolean hasExternalContent) {
        this.hasExternalContent = hasExternalContent;
    }

    /**
     * Gets the <code>SubmissionExternalContent</code> of the submission declaration.
     * 
     * @return A <code>List</code> providing the <code>SubmissionExternalContent</code> of the submission declaration.
     */
    public List<SubmissionExternalContent> getExternalContents() {
        return externalContents;
    }

    /**
     * Sets the <code>SubmissionExternalContent</code> of the submission declaration.
     * 
     * @param externalContents
     *            A <code>List</code> providing the <code>SubmissionExternalContent</code> of the submission
     *            declaration.
     */
    public void setExternalContents(List<SubmissionExternalContent> externalContents) {
        this.externalContents = externalContents;
    }
}
