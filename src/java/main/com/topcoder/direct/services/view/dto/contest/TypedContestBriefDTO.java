/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.contest;

/**
 * <p>A DTO providing the brief details for a single contest including the specification of contest type and status.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class TypedContestBriefDTO extends ContestBriefDTO {

    /**
     * <p>A <code>ContestType</code> referencing the content type.</p>
     */
    private ContestType contestType;

    /**
     * <p>A <code>ContestStatus</code> providing the status of this contest.</p>
     */
    private ContestStatus status;

    /**
     * <p>Constructs new <code>TypedContestBriefDTO</code> instance. This implementation does nothing.</p>
     */
    public TypedContestBriefDTO() {
    }

    /**
     * <p>Gets the contest type.</p>
     *
     * @return a <code>ContestType</code> referencing the content type.
     */
    public ContestType getContestType() {
        return contestType;
    }

    /**
     * <p>Sets the contest type.</p>
     *
     * @param contestType a <code>ContestType</code> referencing the content type.
     */
    public void setContestType(ContestType contestType) {
        this.contestType = contestType;
    }

    /**
     * <p>Gets the status of this contest.</p>
     *
     * @return a <code>ContestStatus</code> providing the status of this contest.
     */
    public ContestStatus getStatus() {
        return this.status;
    }

    /**
     * <p>Sets the status of this contest.</p>
     *
     * @param status a <code>ContestStatus</code> providing the status of this contest.
     */
    public void setStatus(ContestStatus status) {
        this.status = status;
    }
}
