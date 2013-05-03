/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.payments.entities;

import java.util.Date;

/**
 * <p>
 * This entity is a container for range of dates. It contains start and end
 * dates.
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
public class DateRange {

    /**
     * Represents the start date.
     */
    private Date fromDate;

    /**
     * Represents the end date.
     */
    private Date toDate;

    /**
     * The default empty ctor.
     */
    public DateRange() {
        // do nothing
    }

    /**
     * <p>
     * Getter of fromDate field.
     * </p>
     * 
     * @return the fromDate
     */
    public Date getFromDate() {
        return fromDate;
    }

    /**
     * <p>
     * Setter of fromDate field.
     * </p>
     * 
     * @param fromDate
     *            the fromDate to set
     */
    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    /**
     * <p>
     * Getter of toDate field.
     * </p>
     * 
     * @return the toDate
     */
    public Date getToDate() {
        return toDate;
    }

    /**
     * <p>
     * Setter of toDate field.
     * </p>
     * 
     * @param toDate
     *            the toDate to set
     */
    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

}
