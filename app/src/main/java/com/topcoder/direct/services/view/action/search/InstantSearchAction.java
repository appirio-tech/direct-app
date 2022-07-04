/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.search;

import com.topcoder.direct.services.view.action.BaseDirectStrutsAction;
import com.topcoder.direct.services.view.dto.search.ContestSearchResult;
import com.topcoder.direct.services.view.dto.search.ProjectSearchResult;
import com.topcoder.direct.services.view.util.DataProvider;
import com.topcoder.direct.services.view.util.DirectUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * This struts action class handles the instant search and search all requests.
 * </p>
 *
 * @author TCSASSEMBLER
 * @version 1.0 (Module Assembly - TopCoder Cockpit Instant Search)
 */
public class InstantSearchAction extends BaseDirectStrutsAction {

    /**
     * The number of search results returned by instant search.
     */
    private static final int INSTANT_SEARCH_RESULT_NUMBER = 5;

    /**
     * The number of search results returned by search all. It's set to a very large value so it returns all
     * the search result.
     */
    private static final int SEARCH_ALL_RESULT_NUMBER = 100000;

    /**
     * The string keyword used for the search.
     */
    private String searchKey;

    /**
     * The contest search results.
     */
    private List<ContestSearchResult> contestsSearchResult;

    /**
     * The project search result.
     */
    private List<ProjectSearchResult> projectsSearchResult;

    /**
     * Gets the search keyword.
     *
     * @return the search keyword.
     */
    public String getSearchKey() {
        return searchKey;
    }

    /**
     * Sets the search keyword.
     *
     * @param searchKey the search keyword.
     */
    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

    /**
     * Gets the contests search result.
     *
     * @return the contest search result.
     */
    public List<ContestSearchResult> getContestsSearchResult() {
        return contestsSearchResult;
    }

    /**
     * Gets the projects search result.
     *
     * @return the projects search result.
     */
    public List<ProjectSearchResult> getProjectsSearchResult() {
        return projectsSearchResult;
    }

    /**
     * Gets whether current user is TopCoder Admin.
     *
     * @return whether current user is TopCoder Admin.
     */
    public boolean isTCAdmin() {
        return DirectUtils.isTcStaff(DirectUtils.getTCSubjectFromSession());
    }

    /**
     * The action handler to handle the search all request.
     *
     * @throws Exception if there is any error
     */
    @Override
    protected void executeAction() throws Exception {
        // gets the result from query
        final Map<String,List> instantSearchResult = DataProvider.getInstantSearchResultV2(getSearchKey(), SEARCH_ALL_RESULT_NUMBER);

        // sets the contest result
        contestsSearchResult = instantSearchResult.get("contests");
        // sets the project result
        projectsSearchResult = instantSearchResult.get("projects");

    }

    /**
     * Handles the ajax request for the instant search.
     *
     * @return the action result code.
     */
    public String instantSearch() {
        try {
            // gets result from query
            final Map<String,List> instantSearchResult = DataProvider.getInstantSearchResultV2(getSearchKey(), INSTANT_SEARCH_RESULT_NUMBER);

            List<ContestSearchResult> contestResults = (List<ContestSearchResult>) instantSearchResult.get("contests");
            List<ProjectSearchResult> projectResults = (List<ProjectSearchResult>) instantSearchResult.get("projects");

            // build contests result
            Map<String, Object> result = new HashMap<String, Object>();
            List<Map<String, String>> contests = new ArrayList<Map<String, String>>();
            for(ContestSearchResult contest : contestResults) {
                Map<String, String> cm = new HashMap<String, String>();
                cm.put("contestId", String.valueOf(contest.getContestId()));
                cm.put("contestName", contest.getContestName());
                cm.put("contestType", contest.getContestTypeName());
                cm.put("projectId", String.valueOf(contest.getProjectId()));
                cm.put("projectName", contest.getProjectName());
                contests.add(cm);
            }

            // build projects result
            List<Map<String, String>> projects = new ArrayList<Map<String, String>>();
            for (ProjectSearchResult project : projectResults) {
                Map<String, String> pm = new HashMap<String, String>();
                pm.put("projectId", String.valueOf(project.getProjectId()));
                pm.put("projectName", project.getProjectName());
                pm.put("projectType", project.getProjectTypeLabel());
                if (project.getClientName() != null) {
                    pm.put("clientName", project.getClientName());
                }

                projects.add(pm);
            }

            result.put("Contests", contests);
            result.put("Projects", projects);
            // put isAdmin to the return result
            result.put("isAdmin", String.valueOf(DirectUtils.isTcStaff(DirectUtils.getTCSubjectFromSession())));

            setResult(result);

        } catch (Throwable e) {
            if (getModel() != null) {
                setResult(e);
            }
        }

        return SUCCESS;
    }

}
