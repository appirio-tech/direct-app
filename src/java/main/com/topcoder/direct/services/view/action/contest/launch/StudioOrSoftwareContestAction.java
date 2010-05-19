/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.direct.services.view.action.contest.launch;

import com.opensymphony.xwork2.validator.annotations.FieldExpressionValidator;

/**
 * <p>
 * This is the base class for the actions which must decide if a studio or a software contest is managed based
 * on the project or contest ID. It contains the common logic. It extends <code>ContestAction</code>.
 * </p>
 *
 * <p>
 * <b>Thread safety:</b> In Struts 2 framework the action is constructed for every request so the thread
 * safety is not required (instead in Struts 1 the thread safety is required because the action instances are
 * reused). This class is mutable and stateful, so it's not thread safe.
 * </p>
 *
 * @author fabrizyo, TCSDEVELOPER
 * @version 1.0
 */
public abstract class StudioOrSoftwareContestAction extends ContestAction {

    /**
     * Represents the prefix to use for the validation error keys.
     */
    private static final String KEY_PREFIX = "i18n.studioOrSoftwareContestAction.";

    /**
     * <p>
     * Represents the ID of project.
     * </p>
     *
     * <p>
     * It's used to retrieve the software competition. It can be 0 (means not present) or greater than 0 (if it's
     * present). It's changed by the setter and returned by the getter.
     * </p>
     */
    private long projectId;

    /**
     * <p>
     * Represents the ID of contest.
     * </p>
     *
     * <p>
     * It's used to retrieve the studio competition. It can be 0 (means not present) or greater than 0 (if it's
     * present). It's changed by the setter and returned by the getter.
     * </p>
     */
    private long contestId;

    /**
     * Default constructor, creates new instance.
     */
    protected StudioOrSoftwareContestAction() {
    }

    /**
     * This method does nothing in this implementation, but is present to allow for validations. Struts 2 validation
     * is used to ensure that either project ID or contest ID are set, but not both.
     *
     * @throws Exception if some error occurs during method execution
     */
    @FieldExpressionValidator(key = KEY_PREFIX + "contestIdProjectIdNotBothSet",
        fieldName = "contestIdProjectId", expression = "((projectId > 0) ^ (contestId > 0)) == 1",
        message = "Either projectId or contestId must be > 0")
    public void executeAction() throws Exception {
    }

    /**
     * Returns true if the competition is a studio competition, false otherwise.
     *
     * @return true if the competition is a studio competition, false otherwise
     */
    protected boolean isStudioCompetition() {
        return contestId > 0;
    }

    /**
     * Getter for the project ID.
     *
     * @return the project ID
     */
    public long getProjectId() {
        return projectId;
    }

    /**
     * Setter for the project ID. Struts 2 validation is used to check that the argument is greater than or
     * equal to 0.
     *
     * @param projectId the project ID
     */
    @FieldExpressionValidator(key = KEY_PREFIX + "projectIdNotNegative",
        fieldName = "projectId", expression = "projectId >= 0",
        message = ActionHelper.GREATER_THAN_OR_EQUAL_TO_ZERO)
    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    /**
     * Getter for the contest ID.
     *
     * @return the contest ID
     */
    public long getContestId() {
        return contestId;
    }

    /**
     * Setter for the contest ID. Struts 2 validation is used to check that the argument is greater than or
     * equal to 0.
     *
     * @param contestId the contest ID
     */
    @FieldExpressionValidator(key = KEY_PREFIX + "contestIdNotNegative",
        fieldName = "contestId", expression = "contestId >= 0",
        message = ActionHelper.GREATER_THAN_OR_EQUAL_TO_ZERO)
    public void setContestId(long contestId) {
        this.contestId = contestId;
    }
}
