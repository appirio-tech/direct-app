/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.analytics.longcontest;

import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.direct.services.view.action.AbstractAction;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.security.TCSubject;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;

/**
 * This class will handle the set/update round id request.
 *
 * @author Ghost_141
 * @version 1.0
 * @since 1.0 (BUGR - 9796)
 */
public class MarathonMatchRoundIdAction extends AbstractAction {

    /**
     * The class name used in log.
     */
    private static final String CLASS_NAME = MarathonMatchRoundIdAction.class.getName();

    /**
     * The logger used to log.
     */
    private Log logger = LogManager.getLog(CLASS_NAME);

    /**
     * The round id.
     */
    private Long roundId;

    /**
     * The project id.
     */
    private Long projectId;

    /**
     * Instantiates a new Marathon match round id action.
     */
    public MarathonMatchRoundIdAction() {
        // Empty
    }

    /**
     * This method will handle set round id request.
     *
     * @return the execution result.
     * @throws Exception if any error occurred.
     */
    public String mmSetRoundId() throws Exception {
        final String signature = CLASS_NAME + "#mmSetRoundId()";
        try {
            LoggingWrapperUtility.logEntrance(logger, signature, null, null);

            validateArgument(roundId, "roundId");
            validateArgument(projectId, "projectId");

            TCSubject currentUser = DirectUtils.getTCSubjectFromSession();

            DirectUtils.setRoundId(roundId, projectId, currentUser.getUserId());

            LoggingWrapperUtility.logExit(logger, signature, new Object[] {"success"});

            return SUCCESS;
        } catch (Exception e) {
            LoggingWrapperUtility.logException(logger, signature, e);
            setResult(e);
            throw new Exception("Error when executing action : " + getAction() + " : " + e.getMessage());
        }
    }

    /**
     * This method will handle update round id request.
     *
     * @return the execution result.
     * @throws Exception if any error occurred.
     */
    public String mmUpdateRoundId() throws Exception {
        final String signature = CLASS_NAME + "#mmUpdateRoundId()";
        try {
            LoggingWrapperUtility.logEntrance(logger, signature, null, null);

            validateArgument(roundId, "roundId");
            validateArgument(projectId, "projectId");

            TCSubject currentUser = DirectUtils.getTCSubjectFromSession();

            DirectUtils.updateRoundId(roundId, projectId, currentUser.getUserId());

            LoggingWrapperUtility.logExit(logger, signature, new Object[]{"success"});
            return SUCCESS;
        } catch (Exception e) {
            LoggingWrapperUtility.logException(logger, signature, e);
            setResult(e);
            throw new Exception("Error when executing action : " + getAction() + " : " + e.getMessage());
        }
    }

    /**
     * This method will validate the argument.
     *
     * @param value the argument value.
     * @param name the argument name.
     */
    private void validateArgument(Long value, String name) {
        if (value == null) {
            throw new IllegalArgumentException("The " + name + " should not be null.");
        }
        if (value <= 0) {
            throw new IllegalArgumentException("The " + name + " should be positive.");
        }
    }

    /**
     * Gets round id.
     *
     * @return the round id
     */
    public Long getRoundId() {
        return roundId;
    }

    /**
     * Sets round id.
     *
     * @param roundId the round id
     */
    public void setRoundId(Long roundId) {
        this.roundId = roundId;
    }

    /**
     * Gets project id.
     *
     * @return the project id
     */
    public Long getProjectId() {
        return projectId;
    }

    /**
     * Sets project id.
     *
     * @param projectId the project id
     */
    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }
}
