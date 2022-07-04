/*
 * Copyright (C) 2010 - 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.accounting;

import com.topcoder.clients.model.BillingAccount;
import com.topcoder.clients.model.SearchResult;

/**
 * This struts action is used to display all billing accounts for all projects. By default it will retrieve
 * latest(active) project billings which have contest fees set. Account team can select any billing account view its
 * contest fees(ContestFeeDetailsAction struts action display contest fee details of the billing account).
 * 
 * <p>
 * Version 1.1 (Release Assembly - Project Contest Fees Management Update 1 Assembly) Change notes:
 *   <ol>
 *     <li>Updated {@link #execute()} method to retrieve the list of billing accounts having contest fees set only.</li>
 *   </ol>
 * </p>
 * 
 * Thread safety: The class is mutable and not thread safe. But it'll not caused thread safety issue if used under
 * Spring container.
 * 
 * @author winstips, isv
 * @version 1.1
 */
public class ContestFeesHomeAction extends BaseContestFeeAction {
    /**
     * Denotes instance of SearchResult<BillingAccount>. It is managed with a getter and setter. It may have any value.
     * It is fully mutable.
     */
    private SearchResult<BillingAccount> result;
    /**
     * Represents the number of items to display per page. It is managed with a getter and setter. It may have any
     * value. It is fully mutable.
     */
    private int pageSize = Integer.MAX_VALUE;
    /**
     * Represents the page number in search results. It is managed with a getter and setter. It may have any value. It
     * is fully mutable.
     */
    private int pageNumber = 1;
    /**
     * Denotes the field of billing account entity on which to sort the results It is managed with a getter and setter.
     * It may have any value. It is fully mutable.
     */
    private String sortColumn = "start_date";
    /**
     * Denotes sorting order. It is managed with a getter and setter. It may have any value. It is fully mutable.
     */
    private String sortOrder = "DESC";

    /**
     * Empty constructor.
     */
    public ContestFeesHomeAction() {
    }

    /**
     * Responsible for displaying billing accounts for all active projects.
     * 
     * @throws Exception if an unexpected error occurs.
     */
    public void executeAction() throws Exception {
        setResult(getContestFeeService().search(true, getPageSize(), getPageNumber(), getSortColumn(), getSortOrder()));
    }

    /**
     * Returns the result field value.
     * 
     * @return result field value.
     */
    public SearchResult<BillingAccount> getResult() {
        return this.result;
    }

    /**
     * Sets the given value to result field.
     * 
     * @param result
     *            - the given value to set.
     */
    public void setResult(SearchResult<BillingAccount> result) {
        this.result = result;
    }

    /**
     * Returns the pageSize field value.
     * 
     * @return pageSize field value.
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * Sets the given value to pageSize field.
     * 
     * @param pageSize
     *            - the given value to set.
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * Returns the pageNumber field value.
     * 
     * @return pageNumber field value.
     */
    public int getPageNumber() {
        return this.pageNumber;
    }

    /**
     * Sets the given value to pageNumber field.
     * 
     * @param pageNumber
     *            - the given value to set.
     */
    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    /**
     * Returns the sortColumn field value.
     * 
     * @return sortColumn field value.
     */
    public String getSortColumn() {
        return this.sortColumn;
    }

    /**
     * Sets the given value to sortColumn field.
     * 
     * @param sortColumn
     *            - the given value to set.
     */
    public void setSortColumn(String sortColumn) {
        this.sortColumn = sortColumn;
    }

    /**
     * Returns the sortOrder field value.
     * 
     * @return sortOrder field value.
     */
    public String getSortOrder() {
        return this.sortOrder;
    }

    /**
     * Sets the given value to sortOrder field.
     * 
     * @param sortOrder
     *            - the given value to set.
     */
    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    /**
     * Returns the projectId field value.
     * 
     * @return projectId field value.
     */
    public SearchResult<BillingAccount> getViewData() {
        return getResult();
    }
}
