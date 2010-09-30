/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.contest;

/**
 * <p>An enumeration over submission viewer types (grid, list, single).</p>
 *
 * <p>
 * Version 1.1 (Direct Submission Viewer Release 3) change notes:
 * <ul>
 * <li>Added CHECKOUT type.</li>
 * </ul>
 * </p>
 *
 * <p>
 *   Version 1.2 (Direct Submission Viewer Release 4) change notes:
 *   <ul>
 *     <li>Added {@link #NOWINNER} type.</li>
 *   </ul>
 * </p>
 *
 * @author isv, flexme, TCSDEVELOPER
 * @version 1.2
 */
public enum SubmissionViewerType {

    /**
     * <p>A <code>SubmissionViewerType</code> corresponding to <code>Grid</code> view of submissions.</p>
     */
    GRID,

    /**
     * <p>A <code>SubmissionViewerType</code> corresponding to <code>List</code> view of submissions.</p>
     */
    LIST,

    /**
     * <p>A <code>SubmissionViewerType</code> corresponding to <code>Single</code> view of submissions.</p>
     */
    SINGLE,
    
    /**
     * <p>A <code>SubmissionViewerType</code> corresponding to <code>checkout</code> view of submissions.</p>
     * @since 1.1
     */
    CHECKOUT,

    /**
     * <p>A <code>SubmissionViewerType</code> corresponding to <code>Can't Choose a Winner</code> views of submissions.
     * </p>
     *
     * @since 1.2
     */
    NOWINNER
}
