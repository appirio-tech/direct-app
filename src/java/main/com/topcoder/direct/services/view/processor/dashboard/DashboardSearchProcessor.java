/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.processor.dashboard;

import com.topcoder.direct.services.view.action.dashboard.DashboardSearchAction;
import com.topcoder.direct.services.view.dto.dashboard.DashboardSearchCriteriaType;
import com.topcoder.direct.services.view.dto.dashboard.DashboardSearchResultsDTO;
import com.topcoder.direct.services.view.form.DashboardSearchForm;
import com.topcoder.direct.services.view.processor.RequestProcessor;
import com.topcoder.direct.services.view.util.DataProvider;
import com.topcoder.shared.util.logging.Logger;

/**
 * <p>A request processor for handling the requests for performing search for the projects, contests, members from the
 * dashboard.</p>
 *
 * @author isv
 * @version 1.0
 */
public class DashboardSearchProcessor implements RequestProcessor<DashboardSearchAction> {

    /**
     * <p>A <code>Logger</code> to be used for logging the events encountered while processing the requests.</p>
     */
    private static final Logger log = Logger.getLogger(DashboardSearchProcessor.class);

    /**
     * <p>Constructs new <code>DashboardSearchProcessor</code> instance. This implementation does nothing.</p>
     */
    public DashboardSearchProcessor() {
    }

    /**
     * <p>Processes the incoming request which has been mapped to specified action.</p>
     *
     * @param action an <code>Object</code> representing the current action mapped to incoming request.
     */
    public void processRequest(DashboardSearchAction action) {
        try {
            long currentUserId = action.getSessionData().getCurrentUserId();
            DashboardSearchResultsDTO viewData = action.getViewData();
            DashboardSearchForm form = action.getFormData();
            String searchFor = form.getSearchFor();
            DashboardSearchCriteriaType criteriaType = form.getSearchIn();
            if (DashboardSearchCriteriaType.PROJECTS == criteriaType) {
                viewData.setProjects(DataProvider.searchUserProjects(currentUserId, searchFor));
                viewData.setResultType(DashboardSearchCriteriaType.PROJECTS);
            } else if (DashboardSearchCriteriaType.CONTESTS == criteriaType) {
                viewData.setContests(DataProvider.searchUserContests(currentUserId, searchFor));
                viewData.setResultType(DashboardSearchCriteriaType.CONTESTS);
            } else if (DashboardSearchCriteriaType.MEMBERS == criteriaType) {
                viewData.setMembers(DataProvider.searchUserProjectMembers(currentUserId, searchFor));
                viewData.setResultType(DashboardSearchCriteriaType.MEMBERS);
            }
        } catch (Exception e) {
            log.debug("Failed to perform dashboard search due to unexpected error", e);
            action.setResultCode(DashboardSearchAction.RC_UNEXPECTED_ERROR);
            action.setErrorMessage(e.getMessage());
            action.setError(e);
        }
    }
}
