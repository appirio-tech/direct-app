/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.contest;

/**
 * <p>An enumeration over the status of reviewers sign-up for the project.</p>
 *
 * @author isv
 * @version 1.0 (Direct Contest Dashboard assembly)
 */
public enum ReviewersSignupStatus {

    /**
     * <p>A <code>ReviewersSignupStatus</code> item corresponding to case when all review positions for project are
     * taken by reviewers.</p>
     */
    ALL_REVIEW_POSITIONS_FILLED,

    /**
     * <p>A <code>ReviewersSignupStatus</code> item corresponding to case when not all review positions for project are
     * taken by reviewers while there are more than 24 hours left before Screening phase.</p>
     */
    REVIEW_POSITIONS_NON_FILLED_WARNING,

    /**
     * <p>A <code>ReviewersSignupStatus</code> item corresponding to case when not all review positions for project are
     * taken by reviewers while there are less than 24 hours left before Screening phase.</p>
     */
    REVIEW_POSITIONS_NON_FILLED_DANGER
}
