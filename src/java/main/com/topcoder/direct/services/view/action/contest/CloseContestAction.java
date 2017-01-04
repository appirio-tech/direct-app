/*
 * Copyright (C) 2016 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.contest;

import com.topcoder.direct.services.view.action.contest.launch.ContestAction;
import com.topcoder.direct.services.view.action.contest.launch.DirectStrutsActionsHelper;
import com.topcoder.direct.services.view.util.AuthorizationProvider;
import com.topcoder.management.project.Project;
import com.topcoder.management.project.ProjectPropertyType;
import com.topcoder.security.TCSubject;
import com.topcoder.service.facade.contest.ContestServiceFacade;

/**
 * <p>A <code>Struts</code> action to be used for handling requests for force close private project
 *
 * @author deedee
 * @version 1.0
 */
public class CloseContestAction extends ContestAction {

    /**
     * User id of registrant that is been choose as thw winner
     */
    private long winner;

    /**
     * <p>Execute the action</p>
     *
     * @throws Exception if an unexpected error occurs while processing the request.
     */
    @Override
    protected void executeAction() throws Exception {
        ContestServiceFacade contestServiceFacade = getContestServiceFacade();
        TCSubject currentUser = DirectStrutsActionsHelper.getTCSubjectFromSession();
        Project contest = getProjectServices().getProject(getProjectId());

        if (!AuthorizationProvider.isUserGrantedWriteAccessToProject(currentUser, contest.getTcDirectProjectId())) {
            throw new Exception("You don't have access to this resource");
        }

        if (contest.getProperty(ProjectPropertyType.PRIVATE_PROJECT) != null &&
                contest.getProperty(ProjectPropertyType.PRIVATE_PROJECT).equals("1")) {
            contestServiceFacade.closeSoftwareContest(currentUser, getProjectId(), winner);
        } else {
            throw new Exception("Only for private challenge");
        }
    }

    /**
     * Getter for {@link #winner}
     *
     * @return winner
     */
    public long getWinner() {
        return winner;
    }

    /**
     * Setter for {@link #winner}
     *
     * @param winner
     */
    public void setWinner(long winner) {
        this.winner = winner;
    }


}
