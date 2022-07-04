/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.dashboard;

/**
 * <p>An enumeration over the possible types of criteria to search from the dashboard.</p>
 *
 * <p>
 *  Version 1.1 (Module Assembly - TC Cockpit Operations Dashboard For PMs) change notes:
 *   <ol>
 *       <li>Added enum item {@link #PM_PROJECTS}.</li>
 *   </ol>
 * </p>
 * 
 * @author isv, bugbuka
 * @version 1.1
 */
public enum DashboardSearchCriteriaType {

    /**
     * <p>An <code>DashboardSearchCriteriaType</code> corresponding to search for Platform Managers' projects.</p>
     */
    PM_PROJECTS,
    
    /**
     * <p>An <code>DashboardSearchCriteriaType</code> corresponding to search for projects.</p>
     */
    PROJECTS,

    /**
     * <p>An <code>DashboardSearchCriteriaType</code> corresponding to search for contests.</p>
     */
    CONTESTS,

    /**
     * <p>An <code>DashboardSearchCriteriaType</code> corresponding to search for members or administrators.</p>
     */
    MEMBERS
}
