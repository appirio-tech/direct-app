/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.form;

import com.topcoder.direct.services.view.dto.contest.ContestRoundType;
import com.topcoder.direct.services.view.dto.contest.SubmissionViewerType;

/**
 * <p>A form bean for requests comprising <code>Can't Choose a Winner</code> flow for desired <code>Studio</code>
 * contest.</p>
 *
 * @author TCSDEVELOPER
 * @version 1.0 (Submission Viewer Release 4 assembly)
 */
public class StudioNoWinnerForm extends ContestIdForm {

    /**
     * <p>A <code>ContestRoundType</code> providing the contest round type.</p>
     */
    private ContestRoundType roundType = ContestRoundType.FINAL;

    /**
     * <p>A <code>String</code> providing the feedback from the client on reasons for choosing no winners for
     * contest.</p>
     */
    private String feedback;

    /**
     * <p>Constructs new <code>StudioNoWinnerForm</code> instance. This implementation does nothing.</p>
     */
    public StudioNoWinnerForm() {
    }

    /**
     * <p>Constructs new <code>StudioNoWinnerForm</code> instance to be used for notification of specified
     * party on parameters of this form.</p>
     *
     * @param aware an <code>Aware</code> referencing the object to be notified on parameters of this form.
     */
    public StudioNoWinnerForm(Aware aware) {
        super(aware);
    }

    /**
     * <p>Gets the type of submissions view.</p>
     *
     * @return a <code>SubmissionViewerType</code> providing the type of submissions view.
     */
    public SubmissionViewerType getViewType() {
        return SubmissionViewerType.NOWINNER;
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

    /**
     * <p>Gets the feedback from the client on reasons for choosing no winners for contest.</p>
     *
     * @return a <code>String</code> providing the feedback from the client on reasons for choosing no winners for
     *         contest.
     */
    public String getFeedback() {
        return this.feedback;
    }

    /**
     * <p>Sets the feedback from the client on reasons for choosing no winners for contests.</p>
     *
     * @param feedback a <code>String</code> providing the feedback from the client on reasons for choosing no winners
     *        for contest.
     */
    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    /**
     * <p>Checks if the current round for contest is <code>Checkpoint</code> round or not.</p>
     *
     * @return <code>true</code> if current round type is <code>Checkpoint</code> ; <code>false</code> otherwise.
     */
    public boolean getIsCheckpointRound() {
        return getRoundType() == ContestRoundType.CHECKPOINT;
    }
}
