/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.payments.entities;

/**
 * <p>
 * This entity is a container for top Member payment criteria information.
 * </p>
 * 
 * <p>
 * <strong>Thread Safety: </strong> This class is mutable and not thread safe.
 * </p>
 * 
 * @author TCSASSEMBLER
 * @version 1.0 (Module Assembly - TopCoder Direct Member Payments Dashboard
 *          Backend Assembly)
 * @since 1.0
 */
public class TopMemberPaymentCriteria {

    /**
     * Stands for the sorting column.
     */
    private String sortColumn;

    /**
     * Stands for the sort order.
     */
    private String sortOrder;

    /**
     * Stands for the number of records to retrieve.
     */
    private int resultLimit;

    /**
     * The default empty ctor.
     */
    public TopMemberPaymentCriteria() {
        // do nothing
    }

    /**
     * <p>
     * Getter of sortColumn field.
     * </p>
     * 
     * @return the sortColumn
     */
    public String getSortColumn() {
        return sortColumn;
    }

    /**
     * <p>
     * Setter of sortColumn field.
     * </p>
     * 
     * @param sortColumn
     *            the sortColumn to set
     */
    public void setSortColumn(String sortColumn) {
        this.sortColumn = sortColumn;
    }

    /**
     * <p>
     * Getter of sortOrder field.
     * </p>
     * 
     * @return the sortOrder
     */
    public String getSortOrder() {
        return sortOrder;
    }

    /**
     * <p>
     * Setter of sortOrder field.
     * </p>
     * 
     * @param sortOrder
     *            the sortOrder to set
     */
    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    /**
     * <p>
     * Getter of resultLimit field.
     * </p>
     * 
     * @return the resultLimit
     */
    public int getResultLimit() {
        return resultLimit;
    }

    /**
     * <p>
     * Setter of resultLimit field.
     * </p>
     * 
     * @param resultLimit
     *            the resultLimit to set
     */
    public void setResultLimit(int resultLimit) {
        this.resultLimit = resultLimit;
    }

}
