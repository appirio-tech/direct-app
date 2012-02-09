/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.project;

import com.topcoder.direct.services.view.action.contest.launch.BaseDirectStrutsAction;

/**
 * <p>
 * This action updates forum watch.
 * </p>
 *
 * @author BeBetter
 * @version 1.0
 */
public class UpdateProjectForumWatchAction extends BaseDirectStrutsAction {

    /**
     * <p>
     * Serial version UID.
     * </p>
     */
    private static final long serialVersionUID = -8716080359967114673L;

    /**
     * <p>
     * project Id.
     * </p>
     */
    private long tcDirectProjectId;

    /**
     * <p>
     * If the forum needs to be watched or not.
     * </p>
     */
    private boolean watch;

    public UpdateProjectForumWatchAction() {

    }

    /**
     * <p>
     * Handles the incoming request. Updates the project forum watch.
     * </p>
     *
     * @throws Exception if an unexpected error occurs.
     */
    @Override
    protected void executeAction() throws Exception {
        this.getProjectServiceFacade().updateProjectForumWatch(getCurrentUser(), tcDirectProjectId, watch);
    }

    /**
     * <p>
     * Gets the ID of TC Direct project to get the forum status for.
     * </p>
     *
     * @return a <code>long</code> providing the ID of TC Direct project to get the forum status for.
     */
    public long getTcDirectProjectId() {
        return this.tcDirectProjectId;
    }

    /**
     * <p>
     * Sets the ID of TC Direct project to get the forum status for.
     * </p>
     *
     * @param tcDirectProjectId a <code>long</code> providing the ID of TC Direct project to get the forum status for.
     */
    public void setTcDirectProjectId(long tcDirectProjectId) {
        this.tcDirectProjectId = tcDirectProjectId;
    }

    /**
     * <p>
     *  Returns the watch.
     *  </p>
     *
     * @return the watch
     */
    public boolean isWatch() {
        return watch;
    }

    /**
     * <p>
     * Sets the watch.
     * </p>
     *
     * @param watch the watch to set
     */
    public void setWatch(boolean watch) {
        this.watch = watch;
    }

}
