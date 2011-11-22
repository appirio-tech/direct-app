/*
 * Copyright (C) 2010 - 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.accounting;

import com.topcoder.accounting.fees.entities.BillingAccount;
import com.topcoder.accounting.fees.entities.SearchResult;
import com.topcoder.accounting.fees.services.ContestFeeConfigurationException;
import com.topcoder.accounting.fees.services.ContestFeeService;
import com.topcoder.direct.services.view.action.AbstractAction;
import com.topcoder.direct.services.view.action.ViewAction;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;

/**
 * This struts action is used to display all billing accounts for all projects. By default it will retrieve
 * lastest(active) project billings. Account team can select any billing account view its contest
 * fees(ContestFeeDetailsAction struts action display contest fee details of the billing account).
 * 
 * Thread safety: The class is mutable and not thread safe. But it'll not caused thread safety issue if used under
 * Spring container.
 * 
 * @author winstips, TCSDEVELOPER
 * @version 1.0
 */
public class ContestFeesHomeAction extends AbstractAction implements ViewAction<SearchResult<BillingAccount>> {
    /**
     * Instance of ContestFeeService used to perform persistence operations. It is managed with a getter and setter. It
     * may have any value. It is fully mutable.
     */
    private ContestFeeService contestFeeService;
    /**
     * Instance of Logger used to perform logging operations. It is managed with a getter and setter. It may have any
     * value. It is fully mutable.
     */
    private Log logger;
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
     * @return success flag.
     */
    public String execute() {
        setResult(contestFeeService.search(pageSize, pageNumber, sortColumn, sortOrder));
        return SUCCESS;
    }

    /**
     * Returns the contestFeeService field value.
     * 
     * @return contestFeeService field value.
     */
    public ContestFeeService getContestFeeService() {
        return this.contestFeeService;
    }

    /**
     * Sets the given value to contestFeeService field.
     * 
     * @param contestFeeService
     *            - the given value to set.
     */
    public void setContestFeeService(ContestFeeService contestFeeService) {
        this.contestFeeService = contestFeeService;
    }

    /**
     * Returns the logger field value.
     * 
     * @return logger field value.
     */
    public Log getLogger() {
        return this.logger;
    }

    /**
     * Sets the corresponding member field
     * 
     * @param loggerName
     *            - the given name to set.
     */
    public void setLoggerName(String loggerName) {
        if (loggerName == null) {
            this.logger = null;
        } else {
            this.logger = LogManager.getLog(loggerName);
        }
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
     * Check parameters.
     * 
     * @throws ContestFeeConfigurationException
     *             if this.contestFeeService or this.logger is null
     */
    public void postConstruct() {
        if (contestFeeService == null || logger == null) {
            throw new ContestFeeConfigurationException("The contestFeeService and logger should not be null.");
        }
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
