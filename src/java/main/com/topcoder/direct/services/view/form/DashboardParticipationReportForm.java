/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.form;

/**
 * <p>A form bean providing the form data submitted by user for getting the participation metrics report.</p>
 *
 * @author bugbuka
 * @version 1.0 (TC Cockpit - Member Participation Metrics Report Upgrade)
 */
public class DashboardParticipationReportForm extends DashboardReportForm {

    /**
     * Represents the serial version unique id.
     */
    private static final long serialVersionUID = 8123239262266251226L;

    /** The view type. */
    private String viewType;

    /**
     * Gets the view type.
     *
     * @return the view type
     */
    public String getViewType() {
        return viewType;
    }

    /**
     * Sets the view type.
     *
     * @param viewType the new view type
     */
    public void setViewType(String viewType) {
        this.viewType = viewType;
    }  
}
