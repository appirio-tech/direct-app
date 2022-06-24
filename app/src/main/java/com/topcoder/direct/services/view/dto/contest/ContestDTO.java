/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.contest;

/**
 * <p>A DTO providing the complete details on a single contest.</p>
 *
 * @author isv
 * @version 1.0
 */
public class ContestDTO extends TypedContestBriefDTO {

    /**
     * <p>An interface to be implemented by the parties interested in getting the details on desired contest.</p>
     */
    public static interface Aware {

        /**
         * <p>Gets the ID of contest to get details for.</p>
         *
         * @return a <code>long</code> providing the ID of contest to get details for.
         */
        long getContestId();

        /**
         * <p>Sets the details on requested contest.</p>
         *
         * @param contest a <code>ContestDTO</code> providing the details for requested contest.
         */
        void setContest(ContestDTO contest);

    }

    /**
     * <p>Constructs new <code>ContestDTO</code> instance. This implementation does nothing.</p>
     */
    public ContestDTO() {
    }
}
