/*
 * Copyright (C) 2010 - 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.dashboard;

import com.topcoder.direct.services.view.action.AbstractAction;
import com.topcoder.direct.services.view.action.ViewAction;
import com.topcoder.direct.services.view.dto.dashboard.DashboardDTO;

import java.util.Date;

/**
 * <p>A <code>Struts</code> action to be used for handling requests for viewing the <code>Calendar</code> page.</p>
 * <p/>
 * <p>
 * Version 1.1 Release Assembly - Direct Improvements Assembly Release 3 change note:
 * <ul>
 * <li>add method getCalendarToday to return the today date on the server.</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 1.2 (Release Assembly - TC Cockpit Enterprise Calendar Revamp) change note:
 * <ul><li>
 * Remove the unneeded form data and getter, setter from the action.
 * </li></ul>
 * </p>
 *
 * @author isv, GreatKevin
 * @version 1.2
 */
public class CalendarAction extends AbstractAction implements ViewAction<DashboardDTO> {

    /**
     * <p>A <code>DashboardDTO</code> providing the viewData for displaying by <code>Dashboard</code> view.</p>
     */
    private DashboardDTO viewData = new DashboardDTO();

    /**
     * <p>Constructs new <code>CalendarAction</code> instance. This implementation does nothing.</p>
     */
    public CalendarAction() {
    }

    /**
     * <p>Gets the viewData to be displayed by <code>Dashboard</code> view.</p>
     *
     * @return a <code>DashboardDTO</code> providing the viewData for displaying by <code>Dashboard</code> view.
     */
    public DashboardDTO getViewData() {
        return this.viewData;
    }

    /**
     * <p>Gets the current date on server as today for calendar</p>
     *
     * @return the current date on server.
     * @since 1.1
     */
    public Date getCalendarToday() {
        return new Date();
    }
}
