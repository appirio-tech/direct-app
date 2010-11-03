/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.util;

import com.topcoder.direct.services.view.dto.contest.ContestDashboardDTO;
import com.topcoder.direct.services.view.dto.contest.DependenciesStatus;
import com.topcoder.direct.services.view.dto.contest.RegistrationStatus;
import com.topcoder.direct.services.view.dto.contest.ReviewersSignupStatus;
import com.topcoder.direct.services.view.dto.contest.RunningPhaseStatus;
import com.topcoder.direct.services.view.dto.dashboard.DashboardStatusColor;
import com.topcoder.direct.services.view.dto.dashboard.EnterpriseDashboardProjectStatDTO;

/**
 * <p>
 * A utility class used to provide some help methods.
 * </p>
 * 
 * @author TCSASSEMBLER
 * @version 1.0.0 (From Direct - Project Dashboard Assembly)
 */
public class DashboardHelper {
    /**
     * Represents the running status.
     */
    public static final String RUNNING = "running";

    /**
     * Represents the scheduled status.
     */
    public static final String SCHEDULED = "scheduled";

    /**
     * Private constructor.
     */
    private DashboardHelper() {
    }

    /**
     * Set color for each status and contest.
     * 
     * @param contest
     *            the contest
     */
    public static void setContestStatusColor(ContestDashboardDTO contest) {
        // set phase status color
        if (contest.getCurrentPhaseStatus() == RunningPhaseStatus.RUNNING) {
            contest.setPhaseStatusColor(DashboardStatusColor.GREEN);
        } else if (contest.getCurrentPhaseStatus() == RunningPhaseStatus.CLOSING) {
            contest.setPhaseStatusColor(DashboardStatusColor.ORANGE);
        } else if (contest.getCurrentPhaseStatus() == RunningPhaseStatus.LATE) {
            contest.setPhaseStatusColor(DashboardStatusColor.RED);
        } else {
            contest.setPhaseStatusColor(DashboardStatusColor.GREEN);
        }

        // set registration status color
        if (contest.getRegistrationStatus() == RegistrationStatus.REGISTRATION_LESS_IDEAL_ACTIVE) {
            contest.setRegStatusColor(DashboardStatusColor.ORANGE);
        } else if (contest.getRegistrationStatus() == RegistrationStatus.REGISTRATION_LESS_IDEAL_CLOSED) {
            contest.setRegStatusColor(DashboardStatusColor.ORANGE);
        } else if (contest.getRegistrationStatus() == RegistrationStatus.REGISTRATION_POOR) {
            contest.setRegStatusColor(DashboardStatusColor.RED);
        } else {
            contest.setRegStatusColor(DashboardStatusColor.GREEN);
        }

        // set forum activity status color
        if (contest.getUnansweredForumPostsNumber() > 0) {
            contest.setForumActivityStatusColor(DashboardStatusColor.RED);
        } else {
            contest.setForumActivityStatusColor(DashboardStatusColor.GREEN);
        }

        // set review sign up status color
        if (contest.getReviewersSignupStatus() == ReviewersSignupStatus.ALL_REVIEW_POSITIONS_FILLED) {
            contest.setReviewersSignupStatusColor(DashboardStatusColor.GREEN);
        } else if (contest.getReviewersSignupStatus() == ReviewersSignupStatus.REVIEW_POSITIONS_NON_FILLED_WARNING) {
            contest.setReviewersSignupStatusColor(DashboardStatusColor.ORANGE);
        } else if (contest.getReviewersSignupStatus() == ReviewersSignupStatus.REVIEW_POSITIONS_NON_FILLED_DANGER) {
            contest.setReviewersSignupStatusColor(DashboardStatusColor.RED);
        } else {
            contest.setReviewersSignupStatusColor(DashboardStatusColor.GREEN);
        }

        // set dependencies status color
        if (contest.getDependenciesStatus() == DependenciesStatus.DEPENDENCIES_NON_SATISFIED) {
            contest.setDependenciesStatusColor(DashboardStatusColor.RED);
        } else {
            contest.setDependenciesStatusColor(DashboardStatusColor.GREEN);
        }

        // set contest status color
        if (hasSpecifiedColor(contest, DashboardStatusColor.RED)) {
            contest.setContestStatusColor(DashboardStatusColor.RED);
        } else if (hasSpecifiedColor(contest, DashboardStatusColor.ORANGE)) {
            contest.setContestStatusColor(DashboardStatusColor.ORANGE);
        } else {
            contest.setContestStatusColor(DashboardStatusColor.GREEN);
        }
    }

    /**
     * Judge whether the contest has specified color.
     * 
     * @param contest
     *            the contest to judge
     * @param color
     *            the color to judge
     * @return whether the contest contains this color
     */
    private static boolean hasSpecifiedColor(ContestDashboardDTO contest,
            DashboardStatusColor color) {
        if (contest.getPhaseStatusColor() == color
                || contest.getRegStatusColor() == color
                || contest.getForumActivityStatusColor() == color
                || contest.getReviewersSignupStatusColor() == color
                || contest.getDependenciesStatusColor() == color) {
            return true;
        }
        return false;
    }

    /**
     * Set average contest duration text.
     * 
     * @param project the project to set
     */
    public static void setAverageConestDurationText(
            EnterpriseDashboardProjectStatDTO project) {
        StringBuilder stringBuilder = new StringBuilder();
        double time = project.getAverageContestDuration();
        time /= 24;
        
        if (time >= 1) {
            if (time >= 2) {
                stringBuilder.append((int) time + " days ");
            } else {
                stringBuilder.append((int) time + " day ");
            }
        }
        time -= (int) time;
        time *= 24;
        stringBuilder.append((int) time + ":");
        time -= (int) time;
        time *= 60;
        stringBuilder.append((int) time + ":");
        time -= (int) time;
        time *= 60;
        stringBuilder.append((int) time);

        project.setAverageContestDurationText(stringBuilder.toString());
    }
}
