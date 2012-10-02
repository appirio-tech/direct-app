/*
 * Copyright (C) 2010-2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.processor.dashboard;

import java.util.Date;

import com.topcoder.direct.services.view.action.dashboard.DashboardSearchAction;
import com.topcoder.direct.services.view.dto.dashboard.DashboardSearchCriteriaType;
import com.topcoder.direct.services.view.dto.dashboard.DashboardSearchResultsDTO;
import com.topcoder.direct.services.view.form.DashboardSearchForm;
import com.topcoder.direct.services.view.processor.RequestProcessor;
import com.topcoder.direct.services.view.util.DataProvider;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.security.TCSubject;
import com.topcoder.service.permission.PermissionServiceException;
import com.topcoder.shared.util.logging.Logger;

/**
 * <p>
 * A request processor for handling the requests for performing search for the projects, contests, members from the
 * dashboard.
 * </p>
 * <p>
 * Version 1.1 - Direct Search Assembly
 * - add search functions for project/contest type
 * </p>
 * 
 * <p>
 * Version 1.2 - Module Assembly - TC Cockpit Operations Dashboard For PMs
 * - add search functions for Platform Managers' projects type
 * </p>
 *
 * @author isv, BeBetter, bugbuka
 * @version 1.2
 */
public class DashboardSearchProcessor implements RequestProcessor<DashboardSearchAction> {

    /**
     * <p>
     * A <code>Logger</code> to be used for logging the events encountered while processing the requests.
     * </p>
     */
    private static final Logger log = Logger.getLogger(DashboardSearchProcessor.class);

    /**
     * <p>
     * Constructs new <code>DashboardSearchProcessor</code> instance. This implementation does nothing.
     * </p>
     */
    public DashboardSearchProcessor() {
    }

    /**
     * <p>
     * Processes the incoming request which has been mapped to specified action.
     * </p>
     * <p>
     * Update for Direct Search Assembly: add search function for project/contest
     * </p>
     *
     * @param action an <code>Object</code> representing the current action mapped to incoming request.
     */
    public void processRequest(DashboardSearchAction action) {
        try {
            // it is protected area and user should not be null
            TCSubject tcSubject = action.getSessionData().getCurrentUser();
            long currentUserId = tcSubject.getUserId();

            // request
            DashboardSearchForm form = action.getFormData();
            DashboardSearchCriteriaType criteriaType = form.getSearchIn();
            String searchFor = form.getSearchFor();
            Date start = DirectUtils.getDate(form.getStartDate());
            if (start == null) {
                form.setStartDate("");
            }
            Date end = DirectUtils.getDate(form.getEndDate());
            if (end == null) {
                form.setEndDate("");
            }

            // response
            DashboardSearchResultsDTO viewData = action.getViewData();

            if (DashboardSearchCriteriaType.PROJECTS == criteriaType) {
                viewData.setProjects(DataProvider.searchUserProjects(tcSubject, searchFor));
                viewData.setResultType(DashboardSearchCriteriaType.PROJECTS);
                viewData.setIsAllProjectsPage(false);
            } else if (DashboardSearchCriteriaType.CONTESTS == criteriaType) {
                viewData.setContests(DataProvider.searchUserContests(tcSubject, searchFor, start, end));
                viewData.setResultType(DashboardSearchCriteriaType.CONTESTS);
                viewData.setIsAllProjectsPage(false);
            } else if (DashboardSearchCriteriaType.MEMBERS == criteriaType) {
                viewData.setMembers(DataProvider.searchUserProjectMembers(currentUserId, searchFor));
                viewData.setResultType(DashboardSearchCriteriaType.MEMBERS);
                viewData.setIsAllProjectsPage(false);
            } else if (DashboardSearchCriteriaType.PM_PROJECTS == criteriaType
                || action.getRequestData().getRequest().getRequestURI().endsWith("operationsDashboardEnterprise")
                || action.getRequestData().getRequest().getRequestURI().endsWith("operationsDashboardEnterprise.action")) {
                if(!DirectUtils.isTcStaff(DirectUtils.getTCSubjectFromSession())) {
                    throw new PermissionServiceException("You don't have permission to manage copilot feedback.");
                }
                viewData.setProjects(DataProvider.searchPMUserProjects(tcSubject, ""));
                viewData.setResultType(DashboardSearchCriteriaType.PM_PROJECTS);
                viewData.setIsAllProjectsPage(false);
            } else if (action.getRequestData().getRequest().getRequestURI().endsWith("allProjects")
                || action.getRequestData().getRequest().getRequestURI().endsWith("allProjects.action")) {
                viewData.setProjects(DataProvider.searchUserProjects(tcSubject, ""));
                viewData.setResultType(DashboardSearchCriteriaType.PROJECTS);
                viewData.setIsAllProjectsPage(true);
            }
        } catch (Exception e) {
            log.error("Failed to perform dashboard search due to unexpected error", e);
            action.setResultCode(DashboardSearchAction.RC_UNEXPECTED_ERROR);
            action.setErrorMessage(e.getMessage());
            action.setError(e);
        }
    }
}
