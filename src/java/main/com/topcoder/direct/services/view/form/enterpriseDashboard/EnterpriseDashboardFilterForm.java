/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.form.enterpriseDashboard;

import java.io.Serializable;

/**
 * <p>
 * The form for all the enterprise dashboard requests.
 * </p>
 *
 * @author TCSASSEMBLER
 * @version 1.0
 */
public class EnterpriseDashboardFilterForm implements Serializable {

    /**
     * The client id.
     */
    private long clientId;

    /**
     * The direct project id.
     */
    private long directProjectId;

    /**
     * The project status id.
     */
    private long projectStatusId;

    /**
     * The project filter id.
     */
    private long projectFilterId;

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
     * Gets the id of the client.
     *
     * @return the id of the client.
     */
    public long getClientId() {
        return clientId;
    }

    /**
     * Sets the id of the client.
     *
     * @param clientId the id of the client.
     */
    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    /**
     * Gets the direct project id.
     *
     * @return the direct project id.
     */
    public long getDirectProjectId() {
        return directProjectId;
    }

    /**
     * Sets the direct project id.
     *
     * @param directProjectId the direct project id.
     */
    public void setDirectProjectId(long directProjectId) {
        this.directProjectId = directProjectId;
    }

    /**
     * Gets the project status id.
     *
     * @return the project status id.
     */
    public long getProjectStatusId() {
        return projectStatusId;
    }

    /**
     * Sets the project status id.
     *
     * @param projectStatusId the project status id.
     */
    public void setProjectStatusId(long projectStatusId) {
        this.projectStatusId = projectStatusId;
    }

    /**
     * Gets the project filter metadata key id.
     *
     * @return the project filter metadata key id.
     */
    public long getProjectFilterId() {
        return projectFilterId;
    }

    /**
     * Sets the project filter metadata key id.
     *
     * @param projectFilterId the project filter metadata key id.
     */
    public void setProjectFilterId(long projectFilterId) {
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
}
