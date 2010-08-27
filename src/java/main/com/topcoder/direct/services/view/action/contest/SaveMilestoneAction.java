/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.contest;

import com.topcoder.direct.services.view.action.contest.launch.DirectStrutsActionsHelper;
import com.topcoder.direct.services.view.action.contest.launch.StudioOrSoftwareContestAction;
import com.topcoder.security.TCSubject;
import com.topcoder.service.facade.contest.ContestServiceFacade;

/**
 * <p>
 * A <code>Struts</code> action to be used for handling requests for save rank of milestone submissions for
 * <code>Studio</code> contest.
 * </p>
 * 
 * @author flexme
 * @since Submission Viewer Release 3 assembly
 * @version 1.0
 */
public class SaveMilestoneAction extends StudioOrSoftwareContestAction {
    /**
     * <p>
     * Represents the serial version unique id.
     * </p>
     */
    private static final long serialVersionUID = 4334966071352160715L;

    /**
     * <p>
     * A <code>String</code> providing the ranking of the milestone submissions.
     * </p>
     */
    private String ranks;

    /**
     * <p>
     * Constructs new <code>SaveMilestoneAction</code> instance. This implementation does nothing.
     * </p>
     */
    public SaveMilestoneAction() {
    }

    /**
     * <p>
     * Gets the ranking of the milestone submissions.
     * </p>
     * 
     * @return A <code>String</code> providing the ranking of the milestone submissions.
     */
    public String getRanks() {
        return ranks;
    }

    /**
     * <p>
     * Sets the ranking of the milestone submissions.
     * </p>
     * 
     * @param ranks
     *            A <code>String</code> providing the ranking of the milestone submissions.
     */
    public void setRanks(String ranks) {
        this.ranks = ranks;
    }

    /**
     * <p>
     * Handles the incoming request. Save the ranking of the milestone submissions.
     * </p>
     * 
     * @throws Exception
     *             if an unexpected error occurs.
     */
    @Override
    public void executeAction() throws Exception {
        if (!isStudioCompetition()) {
            return;
        }
        if (ranks.length() > 0) {
            String[] rankingSubmissions = ranks.split(",");
            ContestServiceFacade contestServiceFacade = getContestServiceFacade();
            TCSubject currentUser = DirectStrutsActionsHelper.getTCSubjectFromSession();

            for (String submissionId : rankingSubmissions) {
                contestServiceFacade.updateSubmissionUserRank(currentUser, Long.parseLong(submissionId), 1, true);
            }
        }
    }
}
