/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.form;

import java.io.Serializable;

/**
 * <p>A form bean providing the ID for the contest requested for some processing.</p>
 *
 * @author isv
 * @version 1.0
 */
public class ContestIdForm implements Serializable {

    /**
     * <p>An interface to be implemented by the parties interested in getting the values on parameters set to this form.
     * </p>
     */
    public static interface Aware {

        /**
         * <p>Sets the value of contest ID parameter set to this form.</p>
         *
         * @param contestId a <code>long</code> providing the value of contest ID parameter set to this form.
         */
        public void setContestId(long contestId);
    }

    /**
     * <p>An <code>Aware</code> referencing the object to be notified on parameters of this form.</p>
     */
    private Aware aware;

    /**
     * <p>A <code>long</code> providing the ID of a requested contest.</p>
     */
    private long contestId;

    /**
     * <p>Constructs new <code>ContestIdForm</code> instance. This implementation does nothing.</p>
     */
    public ContestIdForm() {
    }

    /**
     * <p>Constructs new <code>ContestIdForm</code> instance to be used for notification of specified party on
     * parameters of this form.</p>
     *
     * @param aware an <code>Aware</code> referencing the object to be notified on parameters of this form.
     */
    public ContestIdForm(Aware aware) {
        this.aware = aware;
    }

    /**
     * <p>Gets the ID for the requested contest.</p>
     *
     * @return a <code>long</code> providing the ID of a requested contest.
     */
    public long getContestId() {
        return contestId;
    }

    /**
     * <p>Sets the ID for the requested contest.</p>
     *
     * @param contestId a <code>long</code> providing the ID of a requested contest.
     */
    public void setContestId(long contestId) {
        this.contestId = contestId;
        if (this.aware != null) {
            this.aware.setContestId(contestId);
        }
    }
}
