/*
 * Copyright (C) 2017 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project;

import java.io.Serializable;
import java.util.Date;
/**
 * <p>
 * This is a MMRoundSegment entity which represents the Marathon Match Round Segment entry.
 * </p>
 *
 * @author TCSCODER
 * @version 1.0
 */
public class MMRoundSegment implements Serializable {
    /**
     * Segment Id
     */
    private long id;

    /**
     * Segment start_date
     */
    private Date startDate;

    /**
     * Segment end_date
     */
    private Date endDate;

    /**
     * Segment id for registration
     */
    public static final int REGISTRATION_SEGMENT_ID = 1;

    /**
     * Segment id for coding
     */
    public static final int CODING_SEGMENT_ID = 2;

    /**
     * Segment id for intermission
     */
    public static final int INTERMISSION_SEGMENT_ID = 3;

    /**
     * Segment id for challenge
     */
    public static final int CHALLENGE_SEGMENT_ID = 4;

    /**
     * Segment id for system test
     */
    public static final int SYSTEM_TEST_SEGMENT_ID = 5;

    /**
     * Segment id for room assigment
     */
    public static final int ROOM_ASSIGNMENT_SEGMENT_ID = 7;

    /**
     * List used segments by MM
     */
    public static final int[] SEGMENT_IDS = new int[]{REGISTRATION_SEGMENT_ID, CODING_SEGMENT_ID,
            INTERMISSION_SEGMENT_ID, CHALLENGE_SEGMENT_ID, SYSTEM_TEST_SEGMENT_ID, ROOM_ASSIGNMENT_SEGMENT_ID};

    public MMRoundSegment() {
    }

    /**
     * Getter for segment id
     * @return segment id
     */
    public long getId() {
        return id;
    }

    /**
     * Setter for segment id
     * @param id segment id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Getter segment start_date
     * @return start_date
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Setter for segment start_date
     * @param startDate start_date
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * Getter for segment end_date
     * @return end_date
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * Setter for segment end_date
     * @param endDate end_date
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
