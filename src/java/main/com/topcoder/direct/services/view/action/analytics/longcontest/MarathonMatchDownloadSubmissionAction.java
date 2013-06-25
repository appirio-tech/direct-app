/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.analytics.longcontest;

import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.direct.services.view.action.AbstractAction;
import com.topcoder.direct.services.view.action.ViewAction;
import com.topcoder.direct.services.view.util.DataProvider;
import com.topcoder.marathonmatch.service.dto.MMDownloadSubmissionDTO;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;

/**
 * This action is used to download Marathon Match submission.
 * <p>
 * <strong>Thread Safety:</strong> This class is only used in thread-safe manner by Struts2 framework.
 * </p>
 * 
 * @author sampath01, zhu_tao
 * @version 1.0
 * @since 1.0
 */
public class MarathonMatchDownloadSubmissionAction extends AbstractAction implements
        ViewAction<MMDownloadSubmissionDTO> {

    /**
     * This field represents the qualified name of this class.
     */
    private static final String CLASS_NAME = MarathonMatchDownloadSubmissionAction.class.getName();

    /**
     * Log instance.
     */
    private Log logger = LogManager.getLog(CLASS_NAME);

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = 3782008133569745879L;

    /**
     * This field variable holds data for front-end to access.
     */
    private MMDownloadSubmissionDTO viewData;

    /**
     * This method will be invoked by presentation tier.
     * 
     * @return Instance of {@link MMDownloadSubmissionDTO}.
     */
    public MMDownloadSubmissionDTO getViewData() {
        return viewData;
    }

    /**
     * Used to store user input.
     */
    private long roundId;

    /**
     * Used to store user input.
     */
    private long problemId;

    /**
     * Used to store user input.
     */
    private long coderId;

    /**
     * Used to store user input.
     */
    private long submissionNum;

    /**
     * This method collects user input data and then invoke the
     * {@link com.topcoder.direct.services.view.util.DataProvider#getMMSubmission(long, long, long, long)} to retrieve data from DB.
     * 
     * @return The execution result.
     * @throws Exception
     *             If there is any error.
     */
    @Override
    public String execute() throws Exception {
        final String signature = CLASS_NAME + "#execute()";
        LoggingWrapperUtility.logEntrance(logger, signature, null, null);
        try {
            viewData = DataProvider.getMMSubmission(roundId, problemId, coderId, submissionNum);
            LoggingWrapperUtility.logExit(logger, signature, new String[] { "download" });
            return "download";
        } catch (Exception e) {
            LoggingWrapperUtility.logException(logger, signature, e);
            throw new Exception("Error when executing action : " + getAction() + " : " + e.getMessage());
        }
    }

    /**
     * Getter of the name-sake field.
     * 
     * @return the value of name-sake field.
     */
    public long getRoundId() {
        return roundId;
    }

    /**
     * Setter of the name-sake field.
     * 
     * @param roundId
     *            the name-sake field to set
     */
    public void setRoundId(long roundId) {
        this.roundId = roundId;
    }

    /**
     * Getter of the name-sake field.
     * 
     * @return the value of name-sake field.
     */
    public long getProblemId() {
        return problemId;
    }

    /**
     * Setter of the name-sake field.
     * 
     * @param problemId
     *            the name-sake field to set
     */
    public void setProblemId(long problemId) {
        this.problemId = problemId;
    }

    /**
     * Getter of the name-sake field.
     * 
     * @return the value of name-sake field.
     */
    public long getCoderId() {
        return coderId;
    }

    /**
     * Setter of the name-sake field.
     * 
     * @param coderId
     *            the name-sake field to set
     */
    public void setCoderId(long coderId) {
        this.coderId = coderId;
    }

    /**
     * Getter of the name-sake field.
     * 
     * @return the value of name-sake field.
     */
    public long getSubmissionNum() {
        return submissionNum;
    }

    /**
     * Setter of the name-sake field.
     * 
     * @param submissionNum
     *            the name-sake field to set
     */
    public void setSubmissionNum(long submissionNum) {
        this.submissionNum = submissionNum;
    }

}
