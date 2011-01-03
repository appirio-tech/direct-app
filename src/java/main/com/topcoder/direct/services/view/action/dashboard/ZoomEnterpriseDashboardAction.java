/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.dashboard;

import com.topcoder.direct.services.view.dto.dashboard.EnterpriseDashboardStatPeriodType;
import com.topcoder.direct.services.view.util.DirectUtils;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * <p>A <code>Struts</code> action to be used for handling requests for viewing the <code>Enterprise Dashboard</code> 
 * data at desired zoom level passed as AJAX call.</p>
 * 
 * @author isv
 * @version 1.0 (Cockpit - Enterprise Dashboard 2 assembly)
 */
public class ZoomEnterpriseDashboardAction extends EnterpriseDashboardAction {

    /**
     * <p>Constructs new <code>ZoomEnterpriseDashboardAction</code> instance. This implementation does nothing.</p>
     */
    public ZoomEnterpriseDashboardAction() {
    }

    /**
     * <p>Handles the incoming request.</p>
     * 
     * @throws Exception if an unexpected error occurs.
     */
    @Override
    protected void executeAction() throws Exception {
        // Get the desired zoom level from request 
        HttpServletRequest request = DirectUtils.getServletRequest();
        String zoomType = request.getParameter("zoomType");
        EnterpriseDashboardStatPeriodType periodType = EnterpriseDashboardStatPeriodType.valueOf(zoomType);
        
        // Evaluate the period ending with current time based on desired zoom level
        Calendar now = Calendar.getInstance();
        Calendar start = Calendar.getInstance();
        start.setTime(now.getTime());
        start.set(Calendar.HOUR, 0);
        start.set(Calendar.HOUR_OF_DAY, 0);
        start.set(Calendar.MINUTE, 0);
        start.set(Calendar.SECOND, 0);
        start.set(Calendar.MILLISECOND, 0);
        
        if (periodType == EnterpriseDashboardStatPeriodType.WEEK) {
            start.add(Calendar.DATE, -6);
        } else if (periodType == EnterpriseDashboardStatPeriodType.MONTH) {
            start.add(Calendar.MONTH, -1);
        } else if (periodType == EnterpriseDashboardStatPeriodType.QUARTER) {
            start.add(Calendar.MONTH, -3);
        } else if (periodType == EnterpriseDashboardStatPeriodType.HALF_OF_THE_YEAR) {
            start.add(Calendar.MONTH, -6);
        } else if (periodType == EnterpriseDashboardStatPeriodType.YEAR) {
            start.set(Calendar.MONTH, 0);
            start.set(Calendar.DATE, 1);
        }
        
        // Set form start/end date to match the desired zoom level period
        SimpleDateFormat dateFormat = new SimpleDateFormat(DirectUtils.DATE_FORMAT);
        getFormData().setStartDate(dateFormat.format(start.getTime()));
        getFormData().setEndDate(dateFormat.format(now.getTime()));

        // Execute action and retrieve desired data
        super.executeAction();
    }
}
