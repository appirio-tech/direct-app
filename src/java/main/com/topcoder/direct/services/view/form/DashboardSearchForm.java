/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.form;

import com.topcoder.direct.services.view.dto.dashboard.DashboardSearchCriteriaType;

import java.io.Serializable;

/**
 * <p>A form bean providing the data submitted by user for searching the dashboard projects/contests/members.</p>
 *
 * @author isv
 * @version 1.0
 */
public class DashboardSearchForm implements Serializable {

    /**
     * <p>A <code>String</code> providing the text for search criteria.</p>
     */
    private String searchFor;

    /**
     * <p>A <code>DashboardSearchCriteriaType</code> referencing the type of search criteria.</p>
     */
    private DashboardSearchCriteriaType searchIn;

    /**
     * <p>Constructs new <code>DashboardSearchForm</code> instance. This implementation does nothing.</p>
     */
    public DashboardSearchForm() {
    }

    /**
     * <p>Gets the text for search criteria.</p>
     *
     * @return a <code>String</code> providing the text for search criteria.</p>
     */
    public String getSearchFor() {
        return searchFor;
    }

    /**
     * <p>Sets the text for search criteria.</p>
     *
     * @param searchFor a <code>String</code> providing the text for search criteria.</p>
     */
    public void setSearchFor(String searchFor) {
        this.searchFor = searchFor;
    }

    /**
     * <p>Gets the type of search criteria.</p>
     *
     * @return a <code>DashboardSearchCriteriaType</code> referencing the type of search criteria.
     */
    public DashboardSearchCriteriaType getSearchIn() {
        return searchIn;
    }

    /**
     * <p>Sets the type of search criteria.</p>
     *
     * @param searchIn a <code>DashboardSearchCriteriaType</code> referencing the type of search criteria.
     */
    public void setSearchIn(DashboardSearchCriteriaType searchIn) {
        this.searchIn = searchIn;
    }
}
