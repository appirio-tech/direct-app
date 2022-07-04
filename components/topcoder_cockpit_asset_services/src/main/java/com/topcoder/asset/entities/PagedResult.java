/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.asset.entities;

import java.util.List;

/**
 * <p>
 * This class is a container for a page of search/filter result. It is a simple JavaBean (POJO) that provides getters
 * and setters for all private attributes and performs no argument validation in the setters.
 * </p>
 *
 * <p>
 * <strong>Thread safety:</strong> This class is mutable and not thread safe.
 * </p>
 *
 * @author LOY, sparemax
 * @version 1.0
 *
 * @param <T>
 *            the data type.
 */
public class PagedResult<T> {
    /**
     * The page. Can be any value. Has getter and setter.
     */
    private int page;

    /**
     * The page size. Can be any value. Has getter and setter.
     */
    private int pageSize;

    /**
     * The total pages. Can be any value. Has getter and setter.
     */
    private int totalPages;

    /**
     * The records. Can be any value. Has getter and setter.
     */
    private List<T> records;

    /**
     * Creates an instance of this class.
     */
    public PagedResult() {
        // Empty
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
     * Retrieves the total pages.
     *
     * @return the total pages.
     */
    public int getTotalPages() {
        return totalPages;
    }

    /**
     * Sets the total pages.
     *
     * @param totalPages
     *            the total pages.
     */
    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    /**
     * Retrieves the records.
     *
     * @return the records.
     */
    public List<T> getRecords() {
        return records;
    }

    /**
     * Sets the records.
     *
     * @param records
     *            the records.
     */
    public void setRecords(List<T> records) {
        this.records = records;
    }
}
