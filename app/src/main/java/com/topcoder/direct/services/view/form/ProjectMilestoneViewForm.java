/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.form;

/**
 * <p>
 * The request form for all the project milestone operations.
 * </p>
 *
 * @author TCSASSEMBLER
 * @version 1.0 (Module Assembly - TC Cockpit Project Milestones Management Front End)
 */
public class ProjectMilestoneViewForm extends ProjectIdForm {

    /**
     * Represents the list view type.
     */
    public static final String LIST_VIEW = "list";

    /**
     * Represents the calendar view type.
     */
    public static final String CALENDAR_VIEW = "calendar";

    /**
     * Represents the view of multiple milestone creation.
     */
    public static final String MULTIPLE_CREATION_VIEW = "multiple";

    /**
     * The view type.
     */
    private String viewType;

    /**
     * Gets the view type.
     *
     * @return the view type.
     */
    public String getViewType() {
        return viewType;
    }

    /**
     * Sets the view type.
     *
     * @param viewType the view type.
     */
    public void setViewType(String viewType) {
        this.viewType = viewType;
    }
}
