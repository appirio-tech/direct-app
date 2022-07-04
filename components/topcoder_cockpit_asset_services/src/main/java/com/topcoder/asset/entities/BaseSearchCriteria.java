/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.asset.entities;

/**
 * <p>
 * This class is a container for base search criteria. It defines common fields for search criteria. It is a simple
 * JavaBean (POJO) that provides getters and setters for all private attributes and performs no argument validation in
 * the setters.
 * </p>
 *
 * <p>
 * page is 1-based, if it is 0, then all records are searched.
 * </p>
 *
 * <p>
 * <strong>Thread safety:</strong> This class is mutable and not thread safe.
 * </p>
 *
 * @author LOY, sparemax
 * @version 1.0
 */
public abstract class BaseSearchCriteria {
    /**
     * The page. Can be any value. Has getter and setter.
     */
    private int page;

    /**
     * The page size. Can be any value. Has getter and setter.
     */
    private int pageSize;

    /**
     * The sorting column. Can be any value. Has getter and setter.
     */
    private String sortingColumn;

    /**
     * The flag indicates whether the sort order is ascending. Can be any value. Has getter and setter.
     */
    private boolean ascending;

    /**
     * Creates an instance of this class.
     */
    protected BaseSearchCriteria() {
    }

    /**
     * Retrieves the page.
     *
     * @return the page.
     */
    public int getPage() {
        return page;
    }

    /**
     * Sets the page.
     *
     * @param page
     *            the page.
     */
    public void setPage(int page) {
        this.page = page;
    }

    /**
     * Retrieves the page size.
     *
     * @return the page size.
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * Sets the page size.
     *
     * @param pageSize
     *            the page size.
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * Retrieves the sorting column.
     *
     * @return the sorting column.
     */
    public String getSortingColumn() {
        return sortingColumn;
    }

    /**
     * Sets the sorting column.
     *
     * @param sortingColumn
     *            the sorting column.
     */
    public void setSortingColumn(String sortingColumn) {
        this.sortingColumn = sortingColumn;
    }

    /**
     * Retrieves the flag indicates whether the sort order is ascending.
     *
     * @return the flag indicates whether the sort order is ascending.
     */
    public boolean getAscending() {
        return ascending;
    }

    /**
     * Sets the flag indicates whether the sort order is ascending.
     *
     * @param ascending
     *            the flag indicates whether the sort order is ascending.
     */
    public void setAscending(boolean ascending) {
        this.ascending = ascending;
    }
}
