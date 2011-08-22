/*
 * Copyright (C) 2010 - 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.contest.launch;

/**
 * <p>
 * This is a base class only to hold the contest service facade shared among several actions.
 * </p>
 * <p>
 * <p>
 * <b>Thread Safety</b>: In <b>Struts 2</b> framework, the action is constructed for every request so the thread safety
 * is not required (instead in Struts 1 the thread safety is required because the action instances are reused). This
 * class is mutable and stateful: it's not thread safe.
 * </p>
 * <p>
 * Change Note for 1.1 : Direct - View Edit Activate Studio Contests Assembly
 * <ul>
 * <li>Adds getStudioContestTypes method.</li>
 * </ul>
 * </p>
 * <p>
 * Change Note for 1.2 : TC Direct Replatforming Release 1 Assembly
 * <ul>
 * <li>Remove getStudioContestTypes method.</li>
 * </ul>
 * </p>
 * 
 * @author fabrizyo, FireIce, TCSDEVELOPER
 * @version 1.2
 */
public abstract class ContestAction extends BaseDirectStrutsAction {
    /**
     * <p>A <code>long</code> providing the id of the project.</p>
     */
    private long projectId;

    /**
     * <p>
     * Represents the unique serial version id.
     * </p>
     */
    private static final long serialVersionUID = -329554689416913910L;

    /**
     * <p>
     * Creates a <code>ContestAction</code> instance.
     * </p>
     */
    protected ContestAction() {
    }

    /**
     * Gets the project id.
     * 
     * @return the project id.
     */
    public long getProjectId() {
        return projectId;
    }

    /**
     * Sets the project id.
     * 
     * @param projectId the project id.
     */
    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }
}
