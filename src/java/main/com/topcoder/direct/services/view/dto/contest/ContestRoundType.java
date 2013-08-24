/*
 * Copyright (C) 2010-2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.contest;

/**
 * <p>An enumeration over contest round types (checkpoint, final).</p>
 *
 * <p>
 * Version 1.1 (Module Assembly - TC Cockpit - Studio - Final Fixes Integration Part One Assembly) Change notes:
 *   <ol>
 *     <li>Added {@link #STUDIO_FINAL_FIX_SUBMISSION} item.</li>
 *     <li>Added {@link #ContestRoundType(int)} constructor.</li>
 *     <li>Added {@link #submissionTypeId} property with respective accessor method.</li>
 *   </ol>
 * </p>
 *
 * @author isv
 * @version 1.1
 */
public enum ContestRoundType {

    /**
     * <p>A <code>ContestRoundType</code> corresponding to <code>Checkpoint</code> contest round type.</p>
     */
    CHECKPOINT(3),

    /**
     * <p>A <code>ContestRoundType</code> corresponding to <code>Final</code> contest round type.</p>
     */
    FINAL(1),

    /**
     * <p>A <code>ContestRoundType</code> corresponding to <code>Studio Final Fix</code> contest round type.</p>
     * 
     * @since 1.1
     */
    STUDIO_FINAL_FIX_SUBMISSION(4);

    /**
     * <p>A <code>int</code> providing the ID for the type of submissions relevant to this contest round type.</p>
     * 
     * @since 1.1
     */
    private int submissionTypeId;

    /**
     * <p>Constructs new <code>ContestRoundType</code> instance mapped to specified submission type.</p>
     * 
     * @param submissionTypeId an <code>int</code> providing the ID for the type of submissions relevant to this contest
     *        round type.
     * @since 1.1
     */
    private ContestRoundType(int submissionTypeId) {
        this.submissionTypeId = submissionTypeId;
}

    /**
     * <p>Gets the ID for the type of submissions relevant to this contest round type.</p>
     *
     * @return a <code>int</code> providing the ID for the type of submissions relevant to this contest round type.
     * @since 1.1
     */
    public int getSubmissionTypeId() {
        return this.submissionTypeId;
    }

}
