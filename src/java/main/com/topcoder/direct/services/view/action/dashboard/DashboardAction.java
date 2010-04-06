/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.dashboard;

import com.topcoder.direct.services.view.action.AbstractAction;
import com.topcoder.direct.services.view.action.ViewAction;
import com.topcoder.direct.services.view.dto.dashboard.DashboardDTO;

/**
 * <p>A <code>Struts</code> action to be used for handling requests for viewing the <code>Dashboard</code> page.</p>
 * 
 * @author isv
 * @version 1.0
 */
public class DashboardAction extends AbstractAction implements ViewAction<DashboardDTO> {

    /**
     * <p>A <code>DashboardDTO</code> providing the viewData for displaying by <code>Dashboard</code> view.</p>
     */
    private DashboardDTO viewData = new DashboardDTO();

    /**
     * <p>Constructs new <code>DashboardAction</code> instance. This implementation does nothing.</p>
     */
    public DashboardAction() {
    }

    /**
     * <p>Gets the viewData to be displayed by <code>Dashboard</code> view.</p>
     *
     * @return a <code>DashboardDTO</code> providing the viewData for displaying by <code>Dashboard</code> view.
     */
    public DashboardDTO getViewData() {
        return this.viewData;
    }
}
