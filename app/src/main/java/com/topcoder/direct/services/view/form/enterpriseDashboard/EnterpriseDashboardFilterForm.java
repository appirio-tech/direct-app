/*
 * Copyright (C) 2012 - 2014 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.form.enterpriseDashboard;

import java.io.Serializable;

/**
 * <p>
 * The form for all the enterprise dashboard requests.
 * </p>
 *
 *
 * <p>
 * Version 1.1 (Module Assembly - TC Direct Struts 2 Upgrade)
 * <ol>
 *     <li>Added {@link #zoom} and corresponding getter and setter method, in order to bypass the struts 2 ONGL valiation</li>
 * </ol>
 * </p>
 *
 * <p>
 * Version 1.2 (TopCoder Direct - New User Issue with Enterprise Dashboard)
 * <ul>
 *     <li>Change the setter of clientId, directProjectId, projectStatusId and projectFilterId to use
 *     string. Change the getter to parse the long ID from string, if invalid, use negative value</li>
 * </ul>
 * </p>
 *
 * @author Veve
 * @version 1.2
 */
public class EnterpriseDashboardFilterForm implements Serializable {

    /**
     * The client id.
     */
    private String clientId;

    /**
     * The direct project id.
     */
    private String directProjectId;

    /**
     * The project status id.
     */
    private String projectStatusId;

    /**
     * The project filter id.
     */
    private String projectFilterId;

    /**
     * The project filter value.
     */
    private String projectFilterValue;

    /**
     * The start filter month.
     */
    private String startMonth;

    /**
     * The end filter month.
     */
    private String endMonth;

    /**
     * The page size.
     */
    private int pageSize;

    /**
     * The page number.
     */
    private int pageNumber;


    /**
     * the zoom option, not used in backend, but useful to maintain for struts 2 validation.
     */
    private String zoom;


    /**
     * Gets the long id from the string value. If string value is invalid, return -1 (negative value)
     *
     * @param str the str value to check
     * @return the parsed long id.
     * @since 1.2
     */
    private static long getIdFromString(String str) {
        if(str == null || str.trim().length() == 0) {
            return -1;
        } else {
            try {
                return Long.parseLong(str);
            } catch (NumberFormatException e) {
                return -1;
            }
        }
    }

    /**
     * Gets the id of the client.
     *
     * @return the id of the client.
     */
    public long getClientId() {
        return getIdFromString(this.clientId);
    }

    /**
     * Sets the id of the client.
     *
     * @param clientId the id of the client.
     */
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    /**
     * Gets the direct project id.
     *
     * @return the direct project id.
     */
    public long getDirectProjectId() {
        return getIdFromString(this.directProjectId);
    }

    /**
     * Sets the direct project id.
     *
     * @param directProjectId the direct project id.
     */
    public void setDirectProjectId(String directProjectId) {
        this.directProjectId = directProjectId;
    }

    /**
     * Gets the project status id.
     *
     * @return the project status id.
     */
    public long getProjectStatusId() {
        return getIdFromString(this.projectStatusId);
    }

    /**
     * Sets the project status id.
     *
     * @param projectStatusId the project status id.
     */
    public void setProjectStatusId(String projectStatusId) {
        this.projectStatusId = projectStatusId;
    }

    /**
     * Gets the project filter metadata key id.
     *
     * @return the project filter metadata key id.
     */
    public long getProjectFilterId() {
        return getIdFromString(this.projectFilterId);
    }

    /**
     * Sets the project filter metadata key id.
     *
     * @param projectFilterId the project filter metadata key id.
     */
    public void setProjectFilterId(String projectFilterId) {
        this.projectFilterId = projectFilterId;
    }

    /**
     * Gets the project filter value.
     *
     * @return the project filter value.
     */
    public String getProjectFilterValue() {
        return projectFilterValue;
    }

    /**
     * Sets the project filter value.
     *
     * @param projectFilterValue the project filter value.
     */
    public void setProjectFilterValue(String projectFilterValue) {
        this.projectFilterValue = projectFilterValue;
    }

    /**
     * Gets the start filter month.
     *
     * @return the start filter month.
     */
    public String getStartMonth() {
        return startMonth;
    }

    /**
     * Sets the start filter month.
     *
     * @param startMonth the start filter month.
     */
    public void setStartMonth(String startMonth) {
        this.startMonth = startMonth;
    }

    /**
     * Gets the end filter month.
     *
     * @return the end filter month.
     */
    public String getEndMonth() {
        return endMonth;
    }

    /**
     * Sets the end filter month.
     *
     * @param endMonth the end filter month.
     */
    public void setEndMonth(String endMonth) {
        this.endMonth = endMonth;
    }

    /**
     * Gets the page size.
     *
     * @return the page size.
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * Sets the page size.
     *
     * @param pageSize the page size.
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * Gets the page number.
     *
     * @return the page number.
     */
    public int getPageNumber() {
        return pageNumber;
    }

    /**
     * Sets the page number.
     *
     * @param pageNumber the page number.
     */
    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    /**
     * Gets the zoom.
     *
     * @return the zomm.
     */
    public String getZoom() {
        return zoom;
    }

    /**
     * Sets the zoom.
     *
     * @param zoom zoom.
     */
    public void setZoom(String zoom) {
        this.zoom = zoom;
    }
}
