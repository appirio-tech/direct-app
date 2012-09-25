/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.dashboard.participationreport;

/**
 * The enum represents the view type of aggregation participation metrics report.
 *
 * @author bugbuka
 * @version  1.0 (TC Cockpit - Member Participation Metrics Report Upgrade)
 */
public enum ParticipationViewType {
    
    /**
     * The aggregation cost report type : direct project.
     */
    PROJECT("project","Project"),

    /**
     * The aggregation cost report type : billing account.
     */
    BILLING("billing", "Billing Account"),
    
    /**
     * The aggregation cost report type : contest type.
     */
    CONTEST_TYPE("type","Contest Type"),

    /**
     * The aggregation cost report type : status.
     */
    CONTEST_STATUS("status", "Contest Status"),

    /**
     * The aggregation cost report type : contest.
     */
    CONTEST("contest", "Contest Name");
    
    /**
     * The key of the view type of aggregation participation metrics report.
     */
    private final String key;

    /**
     * The text presentation of the view type of aggregation participation metrics report.
     */
    private final String text;

    /**
     * Instantiates a new participation view type.
     *
     * @param key the key
     * @param text the text
     */
    private ParticipationViewType(String key, String text) {
        this.key = key;
        this.text = text;
    }

    /**
     * Gets the key.
     *
     * @return the key
     */
    public String getKey() {
        return key;
    }

    /**
     * Gets the text.
     *
     * @return the text
     */
    public String getText() {
        return text;
    }
    
    
}
