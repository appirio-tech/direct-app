/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.payments.entities;

import java.util.Date;

/**
 * <p>
 * This entity is a container for member payment history search criteria.
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
public class PaymentHistoryCriteria {

    /**
     * Denotes the start date of payment history.
     */
    private Date startDate;

    /**
     * Denotes the end date of payment history.
     */
    private Date endDate;

    /**
     * The default empty ctor.
     */
    public PaymentHistoryCriteria() {
        // do nothing
    }
    
    /**
     * <p>
     * Getter of startDate field.
     * </p>
     * 
     * @return the startDate
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * <p>
     * Setter of startDate field.
     * </p>
     * 
     * @param startDate
     *            the startDate to set
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * <p>
     * Getter of endDate field.
     * </p>
     * 
     * @return the endDate
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * <p>
     * Setter of endDate field.
     * </p>
     * 
     * @param endDate
     *            the endDate to set
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

}
