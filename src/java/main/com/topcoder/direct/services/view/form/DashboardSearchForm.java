/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.form;

import com.topcoder.direct.services.view.dto.dashboard.DashboardSearchCriteriaType;

import java.io.Serializable;

/**
 * <p>
 * A form bean providing the data submitted by user for searching the dashboard projects/contests/members.
 * </p>
 * <p>
 * Version 1.1 - Direct Search Assembly - add search fields: startDate,endDate,excel.
 * </p>
 *
 * @author isv, BeBetter
 * @version 1.1
 */
public class DashboardSearchForm implements Serializable {

    /**
     * <p>
     * A <code>String</code> providing the text for search criteria.
     * </p>
     */
    private String searchFor;

    /**
     * <p>
     * A <code>DashboardSearchCriteriaType</code> referencing the type of search criteria.
     * </p>
     */
    private DashboardSearchCriteriaType searchIn;

    /**
     * <p>
     * The startDate field.
     * </p>
     */
    private String startDate;

    /**
     * <p>
     * The endDate field.
     * </p>
     */
    private String endDate;

    /**
     * <p>
     * The excel field.
     * </p>
     */
    private boolean excel;

    /**
     * <p>
     * Constructs new <code>DashboardSearchForm</code> instance. This implementation does nothing.
     * </p>
     */
    public DashboardSearchForm() {
    }

    /**
     * <p>
     * Gets the text for search criteria.
     * </p>
     *
     * @return a <code>String</code> providing the text for search criteria.</p>
     */
    public String getSearchFor() {
        return searchFor;
    }

    /**
     * <p>
     * Sets the text for search criteria.
     * </p>
     *
     * @param searchFor a <code>String</code> providing the text for search criteria.</p>
     */
    public void setSearchFor(String searchFor) {
        this.searchFor = searchFor;
    }

    /**
     * <p>
     * Gets the type of search criteria.
     * </p>
     *
     * @return a <code>DashboardSearchCriteriaType</code> referencing the type of search criteria.
     */
    public DashboardSearchCriteriaType getSearchIn() {
        return searchIn;
    }

    /**
     * <p>
     * Sets the type of search criteria.
     * </p>
     *
     * @param searchIn a <code>DashboardSearchCriteriaType</code> referencing the type of search criteria.
     */
    public void setSearchIn(DashboardSearchCriteriaType searchIn) {
        this.searchIn = searchIn;
    }

    /**
     * <p>
     * Sets the <code>startDate</code> field value.
     * </p>
     *
     * @param startDate the value to set
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     * <p>
     * Gets the <code>startDate</code> field value.
     * </p>
     *
     * @return the <code>startDate</code> field value
     */
    public String getStartDate() {
        return this.startDate;
    }

    /**
     * <p>
     * Sets the <code>endDate</code> field value.
     * </p>
     *
     * @param endDate the value to set
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    /**
     * <p>
     * Gets the <code>endDate</code> field value.
     * </p>
     *
     * @return the <code>endDate</code> field value
     */
    public String getEndDate() {
        return this.endDate;
    }

    /**
     * <p>
     * Sets the <code>excel</code> field value.
     * </p>
     *
     * @param excel the value to set
     */
    public void setExcel(boolean excel) {
        this.excel = excel;
    }

    /**
     * <p>
     * Gets the <code>excel</code> field value.
     * </p>
     *
     * @return the <code>excel</code> field value
     */
    public boolean isExcel() {
        return this.excel;
    }
}
