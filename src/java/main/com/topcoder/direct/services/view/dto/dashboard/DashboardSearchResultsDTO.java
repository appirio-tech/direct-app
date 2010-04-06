/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.dashboard;

import com.topcoder.direct.services.view.dto.CommonDTO;

import java.io.Serializable;
import java.util.List;

/**
 * <p>A DTO providing the search results for dashboard.</p>
 *
 * @author isv
 * @version 1.0
 */
public class DashboardSearchResultsDTO extends CommonDTO implements Serializable {

    /**
     * <p>A <code>List</code> listing the details for found projects.</p>
     */
    private List<DashboardProjectSearchResultDTO> projects;

    /**
     * <p>A <code>List</code> listing the details for found contests.</p>
     */
    private List<DashboardContestSearchResultDTO> contests;

    /**
     * <p>A <code>List</code> listing the details for found members.</p>
     */
    private List<DashboardMemberSearchResultDTO> members;

    /**
     * <p>A <code>DashboardSearchCriteriaType</code> referencing the type of the results.</p>
     */
    private DashboardSearchCriteriaType resultType;

    /**
     * <p>Constructs new <code>DashboardSearchResultsDTO</code> instance. This implementation does nothing.</p>
     */
    public DashboardSearchResultsDTO() {
    }

    /**
     * <p>Gets the list of found projects.</p>
     *
     * @return a <code>List</code> listing the details for found projects.
     */
    public List<DashboardProjectSearchResultDTO> getProjects() {
        return projects;
    }

    /**
     * <p>Sets the list of found projects.</p>
     *
     * @param projects a <code>List</code> listing the details for found projects.
     */
    public void setProjects(List<DashboardProjectSearchResultDTO> projects) {
        this.projects = projects;
    }

    /**
     * <p>Gets the list of found contests.</p>
     *
     * @return a <code>List</code> listing the details for found contests.
     */
    public List<DashboardContestSearchResultDTO> getContests() {
        return contests;
    }

    /**
     * <p>Sets the list of found contests.</p>
     *
     * @param contests a <code>List</code> listing the details for found contests.
     */
    public void setContests(List<DashboardContestSearchResultDTO> contests) {
        this.contests = contests;
    }

    /**
     * <p>Gets the list of found members.</p>
     *
     * @return a <code>List</code> listing the details for found members.
     */
    public List<DashboardMemberSearchResultDTO> getMembers() {
        return members;
    }

    /**
     * <p>Sets the list of found members.</p>
     *
     * @param members a <code>List</code> listing the details for found members.
     */
    public void setMembers(List<DashboardMemberSearchResultDTO> members) {
        this.members = members;
    }

    /**
     * <p>Gets the results type.</p>
     *
     * @return a <code>DashboardSearchCriteriaType</code> referencing the type of the results.
     */
    public DashboardSearchCriteriaType getResultType() {
        return resultType;
    }

    /**
     * <p>Sets the results type.</p>
     *
     * @param resultType a <code>DashboardSearchCriteriaType</code> referencing the type of the results.
     */
    public void setResultType(DashboardSearchCriteriaType resultType) {
        this.resultType = resultType;
    }
}
