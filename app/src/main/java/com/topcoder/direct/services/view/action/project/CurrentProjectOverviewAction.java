/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.project;

/**
 * <p>A <code>Struts</code> action to be used for handling requests for viewing the <code>Project Overview</code> page
 * for currently selected project for current user.</p>
 *
 * @author isv
 * @version 1.0
 */
public class CurrentProjectOverviewAction extends ProjectOverviewAction {

    /**
     * <p>Constructs new <code>CurrentProjectOverviewAction</code> instance. This implementation does nothing.</p>
     */
    public CurrentProjectOverviewAction() {
    }

    /**
     * <p>Prepares this action for execution. This implementation sets the form data with ID for current project.</p>
     *
     * @throws Exception if an unexpected error occurs.
     */
    @Override
    public void prepare() throws Exception {
        super.prepare();
        getFormData().setProjectId(getSessionData().getCurrentProjectContext().getId());
    }
}
