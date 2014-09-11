/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * This class represents customer platform fee.
 * </p>
 * <p>
 * <strong>THREAD SAFETY:</strong> This class contains only mutable fields so therefore it is not thread safe.
 * </p>
 * 
 * @author minhu
 * @version 1.0 (Module Assembly - Add Monthly Platform Fee Feature to Admin Page)
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "customerPlatformFee")
@Entity
@Table(name = "customer_platform_fee")
public class CustomerPlatformFee implements Serializable {
    /**
     * <p>
     * Generated serial id.
     * </p>
     */
    private static final long serialVersionUID = 1603004801986230919L;

    /**
     * <p>
     * The id.
     * </p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_platform_fee_seq")
    @SequenceGenerator(name = "customer_platform_fee_seq", sequenceName = "customer_platform_fee_seq", allocationSize = 5)
    @Column(name = "customer_platform_fee_id")
    private long id;

    /**
     * <p>
     * The client id.
     * </p>
     */
    @Column(name = "client_id")
    private long clientId;

    /**
     * <p>
     * The amount.
     * </p>
     */
    @Column(name = "amount")
    private double amount;

    /**
     * <p>
     * The payment date.
     * </p>
     */
    @Column(name = "payment_date")
    private Date paymentDate;

    /**
     * <p>
     * The create user name.
     * </p>
     */
    @Column(name = "creation_user")
    private String createUser;

    /**
     * <p>
     * The create date.
     * </p>
     */
    @Column(name = "creation_date")
    private Date createDate;

    /**
     * <p>
     * The modify user name.
     * </p>
     */
    @Column(name = "modification_user")
    private String modifyUser;

    /**
     * <p>
     * The modify date.
     * </p>
     */
    @Column(name = "modification_date")
    private Date modifyDate;

    /**
     * <p>
     * Sets the id.
     * </p>
     * 
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * <p>
     * Gets the id.
     * </p>
     * 
     * @return the id
     */
    public long getId() {
        return this.id;
    }

    /**
     * <p>
     * Sets the client id.
     * </p>
     * 
     * @param clientId
     *        the client id to set
     */
    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    /**
     * <p>
     * Gets the client id.
     * </p>
     * 
     * @return the client id
     */
    public long getClientId() {
        return this.clientId;
    }

    /**
     * <p>
     * Sets the amount.
     * </p>
     * 
     * @param amount
     *        the amount to set
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * <p>
     * Gets the amount.
     * </p>
     * 
     * @return the amount
     */
    public double getAmount() {
        return this.amount;
    }

    /**
     * <p>
     * Gets the payment date.
     * </p>
     * 
     * @return the payment date value
     */
    public Date getPaymentDate() {
        return paymentDate;
    }

    /**
     * <p>
     * Sets the payment date.
     * </p>
     *
     * @param paymentDate the payment date value to set
     */
    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    /**
     * <p>
     * Gets the create user name.
     * </p>
     * 
     * @return the createUser
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * <p>
     * Sets the create user name.
     * </p>
     * 
     * @param createUser the createUser to set
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * <p>
     * Gets the create date.
     * </p>
     * 
     * @return the createDate
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * <p>
     * Sets the create date.
     * </p>
     * 
     * @param createDate the createDate to set
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * <p>
     * Gets the modify user name.
     * </p>
     * 
     * @return the modifyUser
     */
    public String getModifyUser() {
        return modifyUser;
    }

    /**
     * <p>
     * Sets the modify user name.
     * </p>
     * 
     * @param modifyUser the modifyUser to set
     */
    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }

    /**
     * <p>
     * Gets the modify date.
     * </p>
     * 
     * @return the modifyDate
     */
    public Date getModifyDate() {
        return modifyDate;
    }

    /**
     * <p>
     * Sets the modify date.
     * </p>
     * 
     * @param modifyDate the modifyDate to set
     */
    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }
}
