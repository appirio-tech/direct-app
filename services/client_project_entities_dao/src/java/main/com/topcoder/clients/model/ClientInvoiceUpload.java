/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.model;

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
import java.io.Serializable;
import java.util.Date;

/**
 * This bean class represents the the upload invoice record for the client. It maps to the table
 * time_oltp:invoice_upload.
 *
 * @author TCSASSEMBLER
 * @version 1.0 (Release Assembly - TopCoder Cockpit - Billing Management)
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "clientInvoiceUpload")
@Entity
@Table(name = "invoice_upload")
public class ClientInvoiceUpload implements Serializable {

    /**
     * The id of the <code>ClientInvoiceUpload</code>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "invoice_upload_seq")
    @SequenceGenerator(name = "invoice_upload_seq", sequenceName = "invoice_upload_seq", allocationSize = 5)
    @Column(name = "invoice_upload_id")
    private long id;

    /**
     * The client id.
     */
    @Column(name = "client_id")
    private long clientId;

    /**
     * The description of the uploaded invoice.
     */
    @Column(name = "description")
    private String description;

    /**
     * The saved file name of the uploaded invoice.
     */
    @Column(name = "file_name")
    private String fileName;

    /**
     * The invoice date of the uploaded invoice.
     */
    @Column(name = "invoice_upload_date")
    private Date invoiceUploadDate;

    /**
     * The create user.
     */
    @Column(name = "creation_user")
    private String createUser;

    /**
     * The modification user.
     */
    @Column(name = "modification_user")
    private String modifyUser;

    /**
     * The creation date.
     */
    @Column(name = "creation_date")
    private Date createDate;

    /**
     * The modification date.
     */
    @Column(name = "modification_date")
    private Date modifyDate;

    /**
     * Gets the id.
     *
     * @return the id.
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id the id.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets the client id.
     *
     * @return the client id.
     */
    public long getClientId() {
        return clientId;
    }

    /**
     * Sets the client id.
     *
     * @param clientId the client id.
     */
    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    /**
     * Gets the description of the uploaded invoice.
     *
     * @return the description of the uploaded invoice.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the uploaded invoice.
     *
     * @param description the description of the uploaded invoice
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the invoice date.
     *
     * @return the invoice date.
     */
    public Date getInvoiceUploadDate() {
        return invoiceUploadDate;
    }

    /**
     * Sets the invoice date.
     *
     * @param invoiceUploadDate the invoice date.
     */
    public void setInvoiceUploadDate(Date invoiceUploadDate) {
        this.invoiceUploadDate = invoiceUploadDate;
    }

    /**
     * Gets the creation user.
     *
     * @return the creation user.
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * Sets the creation user.
     *
     * @param createUser the creation user.
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * Gets the modification user.
     *
     * @return the modification user.
     */
    public String getModifyUser() {
        return modifyUser;
    }

    /**
     * Sets the modification user.
     *
     * @param modifyUser the modification user.
     */
    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }

    /**
     * Gets the creation date.
     *
     * @return the creation date.
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * Sets the creation date.
     *
     * @param createDate the creation date.
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * Gets the modification date.
     *
     * @return the modification date.
     */
    public Date getModifyDate() {
        return modifyDate;
    }

    /**
     * Sets the modification date.
     *
     * @param modifyDate the modification date.
     */
    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    /**
     * Gets the file name.
     *
     * @return the file name.
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Sets the file name.
     *
     * @param fileName the file name.
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
