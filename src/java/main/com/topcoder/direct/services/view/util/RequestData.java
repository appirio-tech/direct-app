/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.util;

import com.topcoder.direct.services.view.dto.dashboard.DashboardSearchCriteriaType;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>A helper class to be used for wrapping the current HTTP request. This class provides the helpful methods for
 * managing the request attributes so that front-end actions, views as well as request processors do not have to deal
 * with concrete request attribute names and thus are provided with consistent API for managing the request state.</p>
 *
 * <p>Sub-sequent assemblies may expand this class with new methods as necessary.</p>
 *
 * @author isv
 * @version 1.0
 */
public class RequestData {

    /**
     * <p>An <code>HttpServletRequest</code> managed by this helper.</p>
     */
    private HttpServletRequest request;

    /**
     * <p>Constructs new <code>RequestData</code> instance wrapping the specified HTTP request.</p>
     *
     * @param request an <code>HttpServletRequest</code> providing the incoming request.
     * @throws IllegalArgumentException if specified <code>session</code> is <code>null</code>.
     */
    public RequestData(HttpServletRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("The parameter [request] is NULL");
        }
        this.request = request;
    }

    /**
     * <p>Gets the incoming request from the client.</p>
     *
     * @return an <code>HttpServletRequest</code> representing the incoming request from the client.
     */
    public HttpServletRequest getRequest() {
        return request;
    }

    /**
     * <p>Gets the search criteria types for dashboard search.</p>
     *
     * @return a <code>Map</code> mapping the criteria types to their textual presentations.
     */
    @SuppressWarnings("unchecked")
    public Map<DashboardSearchCriteriaType, String> getDashboardSearchTypes() {
        return (Map<DashboardSearchCriteriaType, String>) this.request.getAttribute("dashboardSearchTypes");
    }

    /**
     * <p>Sets the search criteria types for dashboard search.</p>
     *
     * @param types a <code>Map</code> mapping the criteria types to their textual presentations.
     */
    public void setDashboardSearchTypes(Map<DashboardSearchCriteriaType, String> types) {
        this.request.setAttribute("dashboardSearchTypes", types);
    }
}
