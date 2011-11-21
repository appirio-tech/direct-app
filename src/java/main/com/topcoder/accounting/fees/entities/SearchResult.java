/*
 * Copyright (C) 2010 - 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.accounting.fees.entities;

import java.util.List;

/**
 * Represents container for search results.
 * 
 * Thread safety: The class is mutable and not thread safe.
 * 
 * @author winstips, TCSDEVELOPER
 * @version 1.0
 */
public class SearchResult<T> {
    /**
     * Represents the number of items to display per page. It is managed with a getter and setter. It may have any value.
     * It is fully mutable.
     */
    private int pageSize;
    /**
     * Represents the page number in search results. It is managed with a getter and setter. It may have any value. It is
     * fully mutable.
     */
    private int pageNumber;
    /**
     * Represents the total number of items in search result. It is managed with a getter and setter. It may have any
     * value. It is fully mutable.
     */
    private int total;
    /**
     * Represents the total number of pages resulted from the search result. It is managed with a getter and setter. It
     * may have any value. It is fully mutable.
     */
    private int totalPageCount;
    /**
     * Represents the list of resultant items. items must of type of BillingAccount. It is managed with a getter and
     * setter. It may have any value. It is fully mutable.
     */
    private List<T> items;

    /**
     * Default Constructor.
     */
    public SearchResult() {
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
        return pageNumber;
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
     * Returns the total field value.
     * 
     * @return total field value.
     */
    public int getTotal() {
        return total;
    }

    /**
     * Sets the given value to total field.
     * 
     * @param total
     *            - the given value to set.
     */
    public void setTotal(int total) {
        this.total = total;
    }

    /**
     * Returns the totalPageCount field value.
     * 
     * @return totalPageCount field value.
     */
    public int getTotalPageCount() {
        return totalPageCount;
    }

    /**
     * Sets the given value to totalPageCount field.
     * 
     * @param totalPageCount
     *            - the given value to set.
     */
    public void setTotalPageCount(int totalPageCount) {
        this.totalPageCount = totalPageCount;
    }

    /**
     * Returns the items field value.
     * 
     * @return items field value.
     */
    public List<T> getItems() {
        return items;
    }

    /**
     * Sets the given value to items field.
     * 
     * @param items
     *            - the given value to set.
     */
    public void setItems(List<T> items) {
        this.items = items;
    }

}
