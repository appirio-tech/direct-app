/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * This class represents the Client java bean. An Client can contain a company,
 * paymentTermsId, clientStatus, salesTax, startDate, endDate, codeName. See
 * base class for other available properties.
 * </p>
 * <p>
 * This is a simple java bean (with a default no-arg constructor and for each
 * property, a corresponding getter/setter method).
 * </p>
 * <p>
 * Any attribute in this bean is OPTIONAL so NO VALIDATION IS PERFORMED here.
 * </p>
 * <p>
 * This class is Serializable (base class is Serializable).
 * </p>
 * <p>
 * <strong>THREAD SAFETY:</strong> This class contains only mutable fields so
 * therefore it is not thread safe.
 * </p>
 *
 * @author Mafy, TCSDEVELOPER
 * @version 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "client", propOrder = { "company", "paymentTermsId",
        "clientStatus", "salesTax", "startDate", "endDate", "codeName" })
@Entity
@Table(name = "client")
@javax.persistence.AttributeOverride(name = "id", column = @Column(name = "client_id"))
public class Client extends AuditableEntity {
    /**
     * The serial version uid of this class.
     */
    private static final long serialVersionUID = -4879296417145627222L;

    /**
     * <p>
     * This field represents the 'company' property of the Client.
     * </p>
     * <p>
     * It is default to the default value of this data type when it is not
     * assigned.
     * </p>
     * <p>
     * Through corresponding getter/setter methods. It is retrieved from
     * Client.company [Client.getCompany()] and in table client.company_id.
     * </p>
     * <p>
     * There are no restrictions at this moment. It can take any value.
     * OPTIONAL.
     * </p>
     */
    @OneToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "company_id", updatable = false)
    private Company company;

    /**
     * <p>
     * This field represents the 'paymentTermsId' property of the Client.
     * </p>
     * <p>
     * It is default to the default value of this data type when it is not
     * assigned.
     * </p>
     * <p>
     * Through corresponding getter/setter methods. It is retrieved from
     * Client.paymentTermsId [Client.getPaymentTermsId()] and in table
     * client.payment_term_id.
     * </p>
     * <p>
     * There are no restrictions at this moment. It can take any value.
     * OPTIONAL.
     * </p>
     */
    @Column(name = "payment_term_id")
    private long paymentTermsId;

    /**
     * <p>
     * This field represents the 'enableEffortHours' property of the Client.
     * </p>
     * <p>
     * It is default to the default value of this data type when it is not
     * assigned.
     * </p>
     * <p>
     * Through corresponding getter/setter methods. It is retrieved from
     * Client.enableEffortHours [Client.isEffortHoursEnabled()] and in table
     * client.enable_effort_hours.
     * </p>
     * <p>
     * There are no restrictions at this moment. It can take any value.
     * OPTIONAL.
     * </p>
     */
    @Column(name = "enable_effort_hours")
    private boolean enableEffortHours;

    /**
     * <p>
     * This field represents the 'clientStatus' property of the Client.
     * </p>
     * <p>
     * It is default to the default value of this data type when it is not
     * assigned.
     * </p>
     * <p>
     * Through corresponding getter/setter methods. It is retrieved from
     * Client.clientStatus [Client.getClientStatus()] and in table
     * client.client_status_id.
     * </p>
     * <p>
     * There are no restrictions at this moment. It can take any value.
     * OPTIONAL.
     * </p>
     */
    @OneToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "client_status_id", updatable = false)
    private ClientStatus clientStatus;

    /**
     * <p>
     * This field represents the 'salesTax' property of the Client.
     * </p>
     * <p>
     * It is default to the default value of this data type when it is not
     * assigned.
     * </p>
     * <p>
     * Through corresponding getter/setter methods. It is retrieved from
     * Client.salesTax [Client.getSalesTax()] and in table client.salestax.
     * </p>
     * <p>
     * There are no restrictions at this moment. It can take any value.
     * OPTIONAL.
     * </p>
     */
    @Column(name = "salestax")
    private double salesTax;

    /**
     * <p>
     * This field represents the 'startDate' property of the Client.
     * </p>
     * <p>
     * It is default to the default value of this data type when it is not
     * assigned.
     * </p>
     * <p>
     * Through corresponding getter/setter methods. It is retrieved from
     * Client.startDate [Client.getStartDate()] and in table client.start_date.
     * </p>
     * <p>
     * There are no restrictions at this moment. It can take any value.
     * OPTIONAL.
     * </p>
     */
    @Column(name = "start_date")
    private Date startDate;

    /**
     * <p>
     * This field represents the 'endDate' property of the Client.
     * </p>
     * <p>
     * It is default to the default value of this data type when it is not
     * assigned.
     * </p>
     * <p>
     * Through corresponding getter/setter methods. It is retrieved from
     * Client.endDate [Client.getEndDate()] and in table client.end_date.
     * </p>
     * <p>
     * There are no restrictions at this moment. It can take any value.
     * OPTIONAL.
     * </p>
     */
    @Column(name = "end_date")
    private Date endDate;

    /**
     * <p>
     * This field represents the 'codeName' property of the Client.
     * </p>
     * <p>
     * It is default to the default value of this data type when it is not
     * assigned.
     * </p>
     * <p>
     * Through corresponding getter/setter methods. It is retrieved from
     * Client.codeName [Client.getCodeName()] and in table client.code_name.
     * </p>
     * <p>
     * There are no restrictions at this moment. It can take any value.
     * OPTIONAL.
     * </p>
     */
    @Column(name = "code_name")
    private String codeName;

    /**
     * Default no-arg constructor. Constructs a new 'Client' instance.
     */
    public Client() {
    }

    /**
     * Getter for 'clientStatus' property. Please refer to the related
     * 'clientStatus' field for more information.
     *
     * @return the value of the 'clientStatus' property. It can be any value.
     */
    public ClientStatus getClientStatus() {
        return this.clientStatus;
    }

    /**
     * Setter for 'clientStatus' property. Please refer to the related
     * 'clientStatus' field for more information.
     *
     * @param clientStatus
     *                the new clientStatus to be used for 'clientStatus'
     *                property. It can be any value.
     */
    public void setClientStatus(ClientStatus clientStatus) {
        this.clientStatus = clientStatus;
    }

    /**
     * Getter for 'codeName' property. Please refer to the related 'codeName'
     * field for more information.
     *
     * @return the value of the 'codeName' property. It can be any value.
     */
    public String getCodeName() {
        return this.codeName;
    }

    /**
     * Setter for 'codeName' property. Please refer to the related 'codeName'
     * field for more information.
     *
     * @param codeName
     *                the new codeName to be used for 'codeName' property. It
     *                can be any value.
     */
    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    /**
     * Getter for 'company' property. Please refer to the related 'company'
     * field for more information.
     *
     * @return the value of the 'company' property. It can be any value.
     */
    public Company getCompany() {
        return this.company;
    }

    /**
     * Setter for 'company' property. Please refer to the related 'company'
     * field for more information.
     *
     * @param company
     *                the new company to be used for 'company' property. It can
     *                be any value.
     */
    public void setCompany(Company company) {
        this.company = company;
    }

    /**
     * Getter for 'endDate' property. Please refer to the related 'endDate'
     * field for more information.
     *
     * @return the value of the 'endDate' property. It can be any value.
     */
    public Date getEndDate() {
        return this.endDate;
    }

    /**
     * Setter for 'endDate' property. Please refer to the related 'endDate'
     * field for more information.
     *
     * @param endDate
     *                the new endDate to be used for 'endDate' property. It can
     *                be any value.
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * Getter for 'paymentTermsId' property. Please refer to the related
     * 'paymentTermsId' field for more information.
     *
     * @return the value of the 'paymentTermsId' property. It can be any value.
     */
    public long getPaymentTermsId() {
        return this.paymentTermsId;
    }

    /**
     * Setter for 'paymentTermsId' property. Please refer to the related
     * 'paymentTermsId' field for more information.
     *
     * @param paymentTermsId
     *                the new paymentTermsId to be used for 'paymentTermsId'
     *                property. It can be any value.
     */
    public void setPaymentTermsId(long paymentTermsId) {
        this.paymentTermsId = paymentTermsId;
    }

    /**
     * Getter for 'salesTax' property. Please refer to the related 'salesTax'
     * field for more information.
     *
     * @return the value of the 'salesTax' property. It can be any value.
     */
    public double getSalesTax() {
        return this.salesTax;
    }

    /**
     * Setter for 'salesTax' property. Please refer to the related 'salesTax'
     * field for more information.
     *
     * @param salesTax
     *                the new salesTax to be used for 'salesTax' property. It
     *                can be any value.
     */
    public void setSalesTax(double salesTax) {
        this.salesTax = salesTax;
    }

    /**
     * Getter for 'startDate' property. Please refer to the related 'startDate'
     * field for more information.
     *
     * @return the value of the 'startDate' property. It can be any value.
     */
    public Date getStartDate() {
        return this.startDate;
    }

    /**
     * Setter for 'startDate' property. Please refer to the related 'startDate'
     * field for more information.
     *
     * @param startDate
     *                the new startDate to be used for 'startDate' property. It
     *                can be any value.
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * Gets whether this client enabled effort hours.
     *
     * @return whether this client enabled effort hours.
     */
    public boolean isEffortHoursEnabled() {
        return this.enableEffortHours;
    }

    /**
     * Sets whether this client enabled effort hours.
     *
     * @param enableEffortHours
     *            whether this client enabled effort hours.
     */
    public void setEnableEffortHours(boolean enableEffortHours) {
        this.enableEffortHours = enableEffortHours;
    }
}
