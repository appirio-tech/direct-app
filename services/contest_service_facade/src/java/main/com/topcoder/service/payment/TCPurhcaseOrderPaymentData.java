/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.payment;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * This class captures the Purchase Order payment data. Purchase order in the
 * current implementation is recognized by po-number only.
 *
 * <p>
 * Updated for: Cockpit Release Assembly for Receipts Added 4 new properties to
 * hold client and project details for the given po.
 * </p>
 *
 * @author snow01
 * @version 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tcPurhcaseOrderPaymentData", propOrder =  {
    "poNumber", "clientId", "clientName", "projectId", "projectName"}
)
public class TCPurhcaseOrderPaymentData extends PaymentData {
    /**
     * Default serial version uid.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Purchase order number of the payment.
     */
    private String poNumber;

    /**
     * Client id of the payment.
     *
     * @since Cockpit Release Assembly for Receipts.
     */
    private long clientId;

    /**
     * Client name of the payment.
     *
     * @since Cockpit Release Assembly for Receipts
     */
    private String clientName;

    /**
     * Project id of the payment
     *
     * @since Cockpit Release Assembly for Receipts
     */
    private long projectId;

    /**
     * Project name of the payment.
     *
     * @since Cockpit Release Assembly for Receipts
     */
    private String projectName;

    /**
     * A do nothing default constructor.
     */
    public TCPurhcaseOrderPaymentData() {
    }

    /**
     * <p>
     * Initializes this instance with given <code>PaymentType</code> and PO
     * Number.
     * </p>
     *
     * @param type
     *            a <code>PaymentType</code> a payment type.
     * @param poNumber
     *            a <code>String</code> purchase order number.
     */
    public TCPurhcaseOrderPaymentData(PaymentType type, String poNumber) {
        super(type);
        this.poNumber = poNumber;
    }

    /**
     * <p>
     * Gets the purchase order number of the payment
     * </p>
     *
     * @return a <code>String</code> the purchase order number of the payment.
     */
    public String getPoNumber() {
        return poNumber;
    }

    /**
     * <p>
     * Sets the purchase order number of the payment
     * </p>
     *
     * @param poNumber
     *            a <code>String</code> the purchase order number for the
     *            payment to be processed.
     */
    public void setPoNumber(String poNumber) {
        this.poNumber = poNumber;
    }

    /**
     * Gets the client id of the payment.
     *
     * @return the client id of the payment.
     *
     * @since Cockpit Release Assembly for Receipts
     */
    public long getClientId() {
        return this.clientId;
    }

    /**
     * Sets the client id of the payment.
     *
     * @param clientId
     *            the client id of the payment.
     *
     * @since Cockpit Release Assembly for Receipts
     */
    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    /**
     * Gets the client name of the payment.
     *
     * @return the client name of the payment.
     *
     * @since Cockpit Release Assembly for Receipts
     */
    public String getClientName() {
        return this.clientName;
    }

    /**
     * Sets the client name of the payment.
     *
     * @param clientName
     *            the client name of the payment.
     *
     * @since Cockpit Release Assembly for Receipts
     */
    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    /**
     * Gets the project id of the payment.
     *
     * @return the project id of the payment.
     *
     * @since Cockpit Release Assembly for Receipts
     */
    public long getProjectId() {
        return this.projectId;
    }

    /**
     * Sets the project id of the payment.
     *
     * @param projectId
     *            the project id of the payment.
     *
     * @since Cockpit Release Assembly for Receipts
     */
    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    /**
     * Gets the project name of the payment.
     *
     * @return the project name of the payment.
     *
     * @since Cockpit Release Assembly for Receipts
     */
    public String getProjectName() {
        return this.projectName;
    }

    /**
     * Sets the project name of the payment.
     *
     * @param projectName
     *            the project name of the payment.
     *
     * @since Cockpit Release Assembly for Receipts
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}
