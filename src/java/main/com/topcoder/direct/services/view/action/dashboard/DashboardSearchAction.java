/*
 * Copyright (C) 2010 - 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.dashboard;

import com.topcoder.direct.services.project.metadata.entities.dao.DirectProjectMetadata;
import com.topcoder.direct.services.project.metadata.entities.dao.DirectProjectMetadataKey;
import com.topcoder.direct.services.view.action.FormAction;
import com.topcoder.direct.services.view.action.ViewAction;
import com.topcoder.direct.services.view.action.contest.launch.BaseDirectStrutsAction;
import com.topcoder.direct.services.view.dto.dashboard.DashboardProjectSearchResultDTO;
import com.topcoder.direct.services.view.dto.dashboard.DashboardSearchCriteriaType;
import com.topcoder.direct.services.view.dto.dashboard.DashboardSearchResultsDTO;
import com.topcoder.direct.services.view.form.DashboardSearchForm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * A <code>Struts</code> action to be used for handling requests for viewing the <code>Dashboard</code> page.
 * </p>
 * <p>
 * Version 2.0 - add result for excel download.
 * </p>
 * <p/>
 * <p>
 * Version 2.0.1 (Direct Release 6 Assembly 1.0) Change notes:
 * <ol>
 * <li>Excluded <code>Members/Admin</code> option from dashboard search criteria types.</li>
 * </ol>
 * </p>
 *
 * <p>
 *  Version 2.1 (Release Assembly - TC Cockpit All Projects Management Page Update) change notes:
 *  <ol>
 *  <li>
 *      Change the parent class from AbstractAction to BaseDirectStrutsAction so metadata service can be utilized.
 *  </li>
 *  <li>
 *      Add method {@link #executeAction} to get project metadata for each project and set into the view.
 *  </li>
 *  </ol>
 * </p>
 * 
 * <p>
 *  Version 2.2 (Module Assembly - TC Cockpit Operations Dashboard For PMs) change notes:
 *   <ol>
 *     <li>
 *         Updated method {@link #executeAction} to get the project metadata of which group flag is true for 
 *         operationsDashboardEnterprise page.
 *     </li>
 *   </ol>
 * </p>
 *
 * @author isv, BeBetter, bugbuka
 * @version 2.2
 */
public class DashboardSearchAction extends BaseDirectStrutsAction implements ViewAction<DashboardSearchResultsDTO>,
        FormAction<DashboardSearchForm> {

    /**
     * <p>
     * A static <code>Map</code> mapping the existing search criteria types to their textual presentations.
     * </p>
     */
    private static final Map<DashboardSearchCriteriaType, String> SEARCH_CRITERIA_TYPES;

    /**
     * <p>
     * The key value of project manager field in project meta data.
     * </p>
     */
    private static final Long PROJECT_MANAGER_KEY = 2L;

    /**
     * <p>
     * This static initializer initializes the <code>SEARCH_CRITERIA_TYPES</code> map.
     * </p>
     */
    static {
        SEARCH_CRITERIA_TYPES = new LinkedHashMap<DashboardSearchCriteriaType, String>();
        SEARCH_CRITERIA_TYPES.put(DashboardSearchCriteriaType.PROJECTS, "Project");
        SEARCH_CRITERIA_TYPES.put(DashboardSearchCriteriaType.CONTESTS, "Contest");
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
     * Gets the project metadata of which group flag is true for search projects, all projects page, and 
     * operationsDashboardEnterprise page.
     *
     * @throws Exception if error.
     * @since 2.1
     */
    @Override
    protected void executeAction() throws Exception {
        // get the metadata for the projects if is the request is project search or all projects page
        if (DashboardSearchCriteriaType.PROJECTS == getFormData().getSearchIn()
                || getRequestData().getRequest().getRequestURI().endsWith("allProjects")
                || getRequestData().getRequest().getRequestURI().endsWith("allProjects.action")
                || getRequestData().getRequest().getRequestURI().endsWith("operationsDashboardEnterprise")
                || getRequestData().getRequest().getRequestURI().endsWith("operationsDashboardEnterprise.action")) {

            // projects should be in view data now (set by the action pre processor)
            final List<DashboardProjectSearchResultDTO> projects = viewData.getProjects();

            // Helper map to store the mapping of project id to DashboardProjectSearchResultDTO
            if (projects.size() > 0) {
                Map<Long, DashboardProjectSearchResultDTO> helperMap = new HashMap<Long, DashboardProjectSearchResultDTO>();

                List<Long> allProjectIds = new ArrayList<Long>();

                for (DashboardProjectSearchResultDTO item : projects) {
                    allProjectIds.add(item.getData().getProjectId());
                    helperMap.put(item.getData().getProjectId(), item);
                }

                if (this.getMetadataKeyService() == null) {
                    throw new IllegalStateException("The direct project metadata service is not initialized.");
                }

                // Gets all project metadata for the projects in list
                final List<DirectProjectMetadata> projectMetadataByProjects = this.getMetadataService().getProjectMetadataByProjects(allProjectIds);

                for (DirectProjectMetadata metadata : projectMetadataByProjects) {

                    // only add metadata used for grouping (grouping = true)
                    // or if it's project manager
                    if ((metadata.getProjectMetadataKey().getGrouping() != null && metadata.getProjectMetadataKey().getGrouping())
                            || (metadata.getProjectMetadataKey().getId() == PROJECT_MANAGER_KEY)) {
                        long projectId = metadata.getTcDirectProjectId();
                        Map<DirectProjectMetadataKey, List<DirectProjectMetadata>> data
                                = helperMap.get(projectId).getProjectsMetadataMap();

                        if (data == null) {
                            data = new HashMap<DirectProjectMetadataKey, List<DirectProjectMetadata>>();
                            helperMap.get(projectId).setProjectsMetadataMap(data);
                        }

                        if (!data.containsKey(metadata.getProjectMetadataKey())) {
                            data.put(metadata.getProjectMetadataKey(), new ArrayList<DirectProjectMetadata>());
                        }

                        data.get(metadata.getProjectMetadataKey()).add(metadata);
                    }
                }
            }
        }
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
