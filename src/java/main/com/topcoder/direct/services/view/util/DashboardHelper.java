/*
 * Copyright (C) 2010-2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.util;

import com.topcoder.direct.services.configs.ConfigUtils;
import com.topcoder.direct.services.view.dto.contest.ContestHealthDTO;
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
 * <p>
 * Version 1.1 (Cockpit Performance Improvement Project Overview and Manage Copilot Posting Assembly 1.0) Change notes:
 *   <ol>
 *     <li>Updated {@link #setContestStatusColor(ContestHealthDTO)} and
 *     {@link #hasSpecifiedColor(ContestHealthDTO, DashboardStatusColor)} methods to accept parameters of
 *     {@link ContestHealthDTO} type instead of <code>ContestDashboardDTO</code> type.</li>
 *   </ol>
 * </p>
 *
 * <p>
 * Version 1.2 (TC Cockpit Bug Tracking R1 Cockpit Project Tracking version 1.0) Change notes:
 *   <ol>
 *     <li>Add logic of calculating health for issue tracking of contest.</li>
 *   </ol>
 * </p>
 *
 * <p>
 * Version 1.2.1 (TC Cockpit Contest Duration Calculation Updates Assembly 1.0) Change notes:
 *   <ol>
 *     <li>Fixed typo in name of {@link #setContestStatusColor(ContestHealthDTO)} method.</li>
 *   </ol>
 * </p>
 *
 * @author Veve, isv
 * @version 1.2.1
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
     * <p>Set color for each status and contest.</p>
     *
     * <p>Version 1.2 changes: add logic of setting contest issue tracking health color.</p>
     *
     * @param contest
     *            the contest
     */
    public static void setContestStatusColor(ContestHealthDTO contest) {
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

        // set the contest issues color
        if (contest.getUnresolvedIssuesNumber() >= ConfigUtils.getIssueTrackingConfig().getBadContestHealthIssuesNumber()) {
            contest.setContestIssuesColor(DashboardStatusColor.RED);
        } else if (contest.getUnresolvedIssuesNumber() > 0) {
            contest.setContestIssuesColor(DashboardStatusColor.ORANGE);
        } else {
            contest.setContestIssuesColor(DashboardStatusColor.GREEN);
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
     * <p>Judge whether the contest has specified color.</p>
     *
     * <p>Version 1.2 changes: add contest issue tracking color.</p>
     *
     * @param contest
     *            the contest to judge
     * @param color
     *            the color to judge
     * @return whether the contest contains this color
     */
    private static boolean hasSpecifiedColor(ContestHealthDTO contest,
            DashboardStatusColor color) {
        if (contest.getPhaseStatusColor() == color
                || contest.getRegStatusColor() == color
                || contest.getForumActivityStatusColor() == color
                || contest.getReviewersSignupStatusColor() == color
                || contest.getDependenciesStatusColor() == color || contest.getContestIssuesColor() == color) {
            return true;
        }
        return false;
    }

    /**
     * Set average contest duration text.
     *
     * @param project the project to set
     */
    public static void setAverageContestDurationText(
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
