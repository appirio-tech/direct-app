/*
 * Copyright (C) 2010-2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.dashboard;

import com.topcoder.direct.services.view.action.AbstractAction;
import com.topcoder.direct.services.view.action.FormAction;
import com.topcoder.direct.services.view.action.ViewAction;
import com.topcoder.direct.services.view.dto.dashboard.DashboardDTO;
import com.topcoder.direct.services.view.form.CalendarForm;

import java.util.Date;

/**
 * <p>A <code>Struts</code> action to be used for handling requests for viewing the <code>Calendar</code> page.</p>
 *
 * <p>
 *     Version 1.1 Release Assembly - Direct Improvements Assembly Release 3 change note:
 *     - add method getCalendarToday to return the today date on the server.
 * </p>
 *
 * @author isv, TCSDEVELOPER
 * @version 1.1
 */
public class CalendarAction extends AbstractAction implements ViewAction<DashboardDTO>, FormAction<CalendarForm> {

    /**
     * <p>A <code>DashboardDTO</code> providing the viewData for displaying by <code>Dashboard</code> view.</p>
     */
    private DashboardDTO viewData = new DashboardDTO();

    /**
     * <p>A <code>CalendarForm</code> providing the parameters for viewing calendar events.</p>
     */
    private CalendarForm formData = new CalendarForm();

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
     * <p>Gets the form data.</p>
     *
     * @return an <code>Object</code> providing the data for form submitted by user..
     */
    public CalendarForm getFormData() {
        return this.formData;
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
