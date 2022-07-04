/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.form;

import com.topcoder.direct.services.view.dto.contest.ContestRoundType;
import com.topcoder.direct.services.view.dto.contest.SubmissionViewerType;

/**
 * <p>A form bean for requests for viewing the list of submissions for desired <code>Studio</code> contest.</p>
 *
 * @author isv
 * @version 1.0 (Submission Viewer Release 1 assembly)
 */
public class StudioContestSubmissionsForm extends ContestIdForm {

    /**
     * <p>A <code>SubmissionViewerType</code> providing the type of submissions view.</p>
     */
    private SubmissionViewerType viewType;

    /**
     * <p>A <code>ContestRoundType</code> providing the contest round type.</p>
     */
    private ContestRoundType roundType = ContestRoundType.FINAL;

    /**
     * <p>Constructs new <code>StudioContestSubmissionsForm</code> instance. This implementation does nothing.</p>
     */
    public StudioContestSubmissionsForm() {
    }

    /**
     * <p>Constructs new <code>StudioContestSubmissionsForm</code> instance to be used for notification of specified
     * party on parameters of this form.</p>
     *
     * @param aware an <code>Aware</code> referencing the object to be notified on parameters of this form.
     */
    public StudioContestSubmissionsForm(Aware aware) {
        super(aware);
    }

    /**
     * <p>Gets the type of submissions view.</p>
     *
     * @return a <code>SubmissionViewerType</code> providing the type of submissions view.
     */
    public SubmissionViewerType getViewType() {
        return this.viewType;
    }

    /**
     * <p>Sets the type of submissions view.</p>
     *
     * @param viewType a <code>SubmissionViewerType</code> providing the type of submissions view.
     */
    public void setViewType(SubmissionViewerType viewType) {
        this.viewType = viewType;
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
