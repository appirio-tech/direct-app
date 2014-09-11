/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.scorecard;


/**
 * This class contains the scorecard id information, the scorecard type id and the scorecard id,
 * which will be used by the method getDefaultScorecardsIDInfo of the interface ScorecardPersistence.
 *
 * @author Angen
 * @version 1.0
 */
public class ScorecardIDInfo {
    /** The id of scorecard type. */
    private long scorecardTypeId;

    /** The id of scorecard. */
    private long scorecardId;

    /**
     * Creates a new ScorecardInfo instance with the input scorecard type id and the scorecard id.
     *
     * @param scorecardTypeId the id of the scorecard type.
     * @param scorecardId the id of the scorecard.
     */
    public ScorecardIDInfo(long scorecardTypeId, long scorecardId) {
        this.scorecardTypeId = scorecardTypeId;
        this.scorecardId = scorecardId;
    }

    /**
     * Returns the scorecard type id.
     *
     * @return the id of the scorecard type.
     */
    public long getScorecardTypeId() {
        return scorecardTypeId;
    }

    /**
     * Assigns the scorecard type id.
     *
     * @param scorecardTypeId the id of the scorecard type to assign.
     */
    public void setScorecardTypeId(long scorecardTypeId) {
        this.scorecardTypeId = scorecardTypeId;
    }

    /**
     * Returns the scorecard id.
     *
     * @return the id of the scorecard.
     */
    public long getScorecardId() {
        return scorecardId;
    }

    /**
     * Assigns the scorecard id.
     *
     * @param scorecardId the id of the scorecard to assign.
     */
    public void setScorecardId(long scorecardId) {
        this.scorecardId = scorecardId;
    }
}
