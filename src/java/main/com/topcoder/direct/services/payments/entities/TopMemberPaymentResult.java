/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.payments.entities;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * This entity is a container for Top Member Payment information.
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
public class TopMemberPaymentResult {

    /**
     * Stands for the start date of payment creating.
     */
    private Date creationStartDate;

    /**
     * Stands for the end date of payment creating.
     */
    private Date creationEndDate;

    /**
     * Stands for the list of {@link TopMemberPayment} instances.
     */
    private List<TopMemberPayment> memberPayments;

    /**
     * The default empty ctor.
     */
    public TopMemberPaymentResult() {
        // do nothing
    }

    /**
     * <p>
     * Getter of creationStartDate field.
     * </p>
     * 
     * @return the creationStartDate
     */
    public Date getCreationStartDate() {
        return creationStartDate;
    }

    /**
     * <p>
     * Setter of creationStartDate field.
     * </p>
     * 
     * @param creationStartDate
     *            the creationStartDate to set
     */
    public void setCreationStartDate(Date creationStartDate) {
        this.creationStartDate = creationStartDate;
    }

    /**
     * <p>
     * Getter of creationEndDate field.
     * </p>
     * 
     * @return the creationEndDate
     */
    public Date getCreationEndDate() {
        return creationEndDate;
    }

    /**
     * <p>
     * Setter of creationEndDate field.
     * </p>
     * 
     * @param creationEndDate
     *            the creationEndDate to set
     */
    public void setCreationEndDate(Date creationEndDate) {
        this.creationEndDate = creationEndDate;
    }

    /**
     * <p>
     * Getter of memberPayments field.
     * </p>
     * 
     * @return the memberPayments
     */
    public List<TopMemberPayment> getMemberPayments() {
        return memberPayments;
    }

    /**
     * <p>
     * Setter of memberPayments field.
     * </p>
     * 
     * @param memberPayments
     *            the memberPayments to set
     */
    public void setMemberPayments(List<TopMemberPayment> memberPayments) {
        this.memberPayments = memberPayments;
    }

}
