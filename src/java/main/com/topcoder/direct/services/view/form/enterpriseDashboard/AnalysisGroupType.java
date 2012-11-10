/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.form.enterpriseDashboard;

/**
 * <p>
 * The time group type of the new Enterprise Dashboard Analysis page.
 * </p>
 *
 * @author TCSASSEMBLER
 * @version 1.0 (Module Assembly - TC Cockpit Enterprise Dashboard Analysis 1)
 */
public enum AnalysisGroupType {
    /**
     * The week time dimension.
     */
    WEEK("Week"),

    /**
     * The month time dimension.
     */
    MONTH("Month"),

    /**
     * The quarter time dimension.
     */
    QUARTER("Quarter"),

    /**
     * The year time dimension.
     */
    YEAR("Year");

    /**
     * The label name of the AnalysisGroupType
     */
    private String label;

    /**
     * Creates the AnalysisGroupType by label name
     *
     * @param label the label name.
     */
    private AnalysisGroupType(String label) {
        this.label = label;
    }

    /**
     * Gets the string representation of AnalysisGroupType.
     *
     * @return the string representation of AnalysisGroupType.
     */
    @Override
    public String toString() {
        return this.label;
    }
}

