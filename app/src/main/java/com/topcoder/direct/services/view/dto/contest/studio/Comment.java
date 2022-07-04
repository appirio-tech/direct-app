/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.contest.studio;

import java.io.Serializable;

/**
 * <p>A DTO representing a single requirement for the Final Fix as specified by the client. Corresponds to a single
 * review item in Approval review.</p>
 * 
 * @author isv
 * @version 1.0 (Module Assembly - TC Cockpit - Studio - Final Fixes Integration Part One)
 */
public class Comment implements Serializable {

    /**
     * <p>A <code>boolean</code> providing the flag indicating whether the Final Fix requirement has been fixed or
     * not.</p>
     */
    private Boolean fixed;

    /**
     * <p>A <code>String</code> providing the text of the Final Fix requirement.</p>
     */
    private String comment;

    /**
     * <p>Constructs new <code>Comment</code> instance. This implementation does nothing.</p>
     */
    public Comment() {
    }

    /**
     * <p>Gets the text of the Final Fix requirement.</p>
     *
     * @return a <code>String</code> providing the text of the Final Fix requirement.
     */
    public String getComment() {
        return this.comment;
    }

    /**
     * <p>Sets the text of the Final Fix requirement.</p>
     *
     * @param comment a <code>String</code> providing the text of the Final Fix requirement.
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * <p>Gets the flag indicating whether the Final Fix requirement has been fixed or not.</p>
     *
     * @return a <code>Boolean</code> providing the flag indicating whether the Final Fix requirement has been fixed or
     *         not.
     */
    public Boolean getFixed() {
        return this.fixed;
    }

    /**
     * <p>Sets the flag indicating whether the Final Fix requirement has been fixed or not.</p>
     *
     * @param fixed a <code>Boolean</code> providing the flag indicating whether the Final Fix requirement has been
     * fixed or not.
     */
    public void setFixed(Boolean fixed) {
        this.fixed = fixed;
    }
}
