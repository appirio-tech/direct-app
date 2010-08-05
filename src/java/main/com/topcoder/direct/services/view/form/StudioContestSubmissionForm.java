/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.form;

import com.topcoder.direct.services.view.dto.contest.ContestRoundType;
import com.topcoder.direct.services.view.dto.contest.SubmissionViewerType;

/**
 * <p>A form bean for requests for viewing the details for requested submission for desired <code>Studio</code> contest.
 * </p>
 *
 * @author isv
 * @version 1.0 (Submission Viewer Release 1 assembly)
 */
public class StudioContestSubmissionForm extends ContestIdForm {

    /**
     * <p>A <code>long</code> providing the submission ID.</p>
     */
    private long submissionId;

    /**
     * <p>A <code>ContestRoundType</code> providing the contest round type.</p>
     */
    private ContestRoundType roundType = ContestRoundType.FINAL;

    /**
     * <p>Constructs new <code>StudioContestSubmissionForm</code> instance. This implementation does nothing.</p>
     */
    public StudioContestSubmissionForm() {
    }

    /**
     * <p>Constructs new <code>StudioContestSubmissionForm</code> instance to be used for notification of specified
     * party on parameters of this form.</p>
     *
     * @param aware an <code>Aware</code> referencing the object to be notified on parameters of this form.
     */
    public StudioContestSubmissionForm(Aware aware) {
        super(aware);
    }

    /**
     * <p>Gets the submission ID.</p>
     *
     * @return a <code>long</code> providing the submission ID.
     */
    public long getSubmissionId() {
        return this.submissionId;
    }

    /**
     * <p>Sets the submission ID.</p>
     *
     * @param submissionId a <code>long</code> providing the submission ID.
     */
    public void setSubmissionId(long submissionId) {
        this.submissionId = submissionId;
    }

    /**
     * <p>Gets the type of submissions view.</p>
     *
     * @return a <code>SubmissionViewerType</code> providing the type of submissions view.
     */
    public SubmissionViewerType getViewType() {
        return SubmissionViewerType.SINGLE;
    }

    /**
     * <p>Gets the contest round type.</p>
     *
     * @return a <code>ContestRoundType</code> providing the contest round type.
     */
    public ContestRoundType getRoundType() {
        return this.roundType;
    }

    /**
     * <p>Sets the contest round type.</p>
     *
     * @param roundType a <code>ContestRoundType</code> providing the contest round type.
     */
    public void setRoundType(ContestRoundType roundType) {
        this.roundType = roundType;
    }
}
