/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.dashboard;

import com.topcoder.direct.services.view.action.AbstractAction;
import com.topcoder.direct.services.view.action.FormAction;
import com.topcoder.direct.services.view.action.ViewAction;
import com.topcoder.direct.services.view.dto.dashboard.DashboardSearchCriteriaType;
import com.topcoder.direct.services.view.dto.dashboard.DashboardSearchResultsDTO;
import com.topcoder.direct.services.view.form.DashboardSearchForm;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <p>
 * A <code>Struts</code> action to be used for handling requests for viewing the <code>Dashboard</code> page.
 * </p>
 * <p>
 * Version 2.0 - add result for excel download.
 * </p>
 *
 * @author isv, BeBetter
 * @version 2.0
 */
public class DashboardSearchAction extends AbstractAction implements ViewAction<DashboardSearchResultsDTO>,
    FormAction<DashboardSearchForm> {

    /**
     * <p>
     * A static <code>Map</code> mapping the existing search criteria types to their textual presentations.
     * </p>
     */
    private static final Map<DashboardSearchCriteriaType, String> SEARCH_CRITERIA_TYPES;

    /**
     * <p>
     * This static initializer initializes the <code>SEARCH_CRITERIA_TYPES</code> map.
     * </p>
     */
    static {
        SEARCH_CRITERIA_TYPES = new LinkedHashMap<DashboardSearchCriteriaType, String>();
        SEARCH_CRITERIA_TYPES.put(DashboardSearchCriteriaType.PROJECTS, "Project");
        SEARCH_CRITERIA_TYPES.put(DashboardSearchCriteriaType.CONTESTS, "Contest");
        SEARCH_CRITERIA_TYPES.put(DashboardSearchCriteriaType.MEMBERS, "Member/Admin");
    }

    /**
     * <p>
     * A <DashboardSearchResultsDTO>DashboardDTO</code> providing the viewData for displaying by
     * <code>Dashboard Search Results</code> view.
     * </p>
     */
    private DashboardSearchResultsDTO viewData = new DashboardSearchResultsDTO();

    /**
     * <p>
     * A <code>DashboardSearchForm</code> providing the input parameters submitted by user.
     * </p>
     */
    private DashboardSearchForm formData = new DashboardSearchForm();

    /**
     * <p>
     * Constructs new <code>DashboardSearchAction</code> instance. This implementation does nothing.
     * </p>
     */
    public DashboardSearchAction() {
    }

    /**
     * <p>
     * Prepares this action for execution. This implementation sets the session and request contexts for this action.
     * </p>
     *
     * @throws Exception if an unexpected error occurs.
     */
    @Override
    public void prepare() throws Exception {
        super.prepare();
        getRequestData().setDashboardSearchTypes(SEARCH_CRITERIA_TYPES);
    }

    /**
     * <p>
     * Gets the data to be displayed by view mapped to this action.
     * </p>
     *
     * @return an <code>Object</code> providing the collector for data to be rendered by the view mapped to this
     *         action.
     */
    public DashboardSearchResultsDTO getViewData() {
        return this.viewData;
    }

    /**
     * <p>
     * Gets the form data.
     * </p>
     *
     * @return an <code>Object</code> providing the data for form submitted by user..
     */
    public DashboardSearchForm getFormData() {
        return this.formData;
    }

    /**
     * <p>
     * In the case of excel download, forward to download result.
     * </p>
     *
     * @return the result name
     */
    @Override
    public String execute() throws Exception {
        String result = super.execute();
        if (SUCCESS.equals(result)) {
            if (getFormData().isExcel()) {
                return "download";
            } else {
                return SUCCESS;
            }
        } else {
            return result;
        }
    }
}
