/*
 * Copyright (C) 2008-2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * This class represents the Project java bean. An Project can contain a
 * company, active, salesTax, pOBoxNumber, paymentTermsId, description,
 * projectStatus, client, childProjects, parentProjectId, manualPrizeSetting.
 * </p>
 * <p>
 * See base class for other available properties.
 * </p>
 * <p>
 * This is a simple java bean (with a default no-arg constructor and for each
 * property, a corresponding getter/setter method).
 * </p>
 * <p>
 * Any attribute in this bean is OPTIONAL so NO VALIDATION IS PERFORMED here.
 * This class is Serializable (base class is Serializable).
 * </p>
 *
 * <p>
 * Version 1.0.1 (Cockpit Release Assembly 7 v1.0) Change Notes:
 *  - Added manual prize setting property.
 * </p>
 *
 * <p>
 * <strong>THREAD SAFETY:</strong> This class contains only mutable fields so
 * therefore it is not thread safe.
 * </p>
 *
 * @author Mafy, TCSDEVELOPER, TCSASSEMBLER
 * @version 1.0.1
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "clientProject", propOrder = { "company", "active", "salesTax",
        "pOBoxNumber", "paymentTermsId", "description", "projectStatus",
        "client", "manualPrizeSetting", "childProjects", "parentProjectId" })
@Entity
@Table(name = "project")
@javax.persistence.AttributeOverride(name = "id", column = @Column(name = "project_id"))
public class Project extends AuditableEntity {
    /**
     * The serial version uid of this class.
     */
    private static final long serialVersionUID = -3195035153126023749L;

    /**
     * <p>
     * This field represents the 'company' property of the Project.
     * </p>
     * <p>
     * It is default to the default value of this data type when it is not
     * assigned.
     * </p>
     * <p>
     * Through corresponding getter/setter methods. It is retrieved from
     * Project.company [Project.getCompany()] and in table project.company_id.
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
     * This field represents the 'active' property of the Project.
     * </p>
     * <p>
     * It is default to the default value of this data type when it is not
     * assigned.
     * </p>
     * <p>
     * Through corresponding getter/setter methods. It is retrieved from
     * Project.active [Project.isActive()] and in table project.active.
     * </p>
     * <p>
     * There are no restrictions at this moment. It can take any value.
     * OPTIONAL.
     * </p>
     */
    @Column(name = "active")
    private boolean active;

    /**
     * <p>
     * This field represents the 'salesTax' property of the Project.
     * </p>
     * <p>
     * It is default to the default value of this data type when it is not
     * assigned.
     * </p>
     * <p>
     * Through corresponding getter/setter methods. It is retrieved from
     * Project.salesTax [Project.getSalesTax()] and in table project.sales_tax.
     * </p>
     * <p>
     * There are no restrictions at this moment. It can take any value.
     * OPTIONAL.
     * </p>
     */
    @Column(name = "sales_tax")
    private double salesTax;

    /**
     * <p>
     * This field represents the 'pOBoxNumber' property of the Project.
     * </p>
     * <p>
     * It is default to the default value of this data type when it is not
     * assigned.
     * </p>
     * <p>
     * Through corresponding getter/setter methods. It is retrieved from
     * Project.pOBoxNumber [Project.getPOBoxNumber()] and in table
     * project.po_box_number.
     * </p>
     * <p>
     * There are no restrictions at this moment. It can take any value.
     * OPTIONAL.
     * </p>
     */
    @Column(name = "po_box_number")
    private String pOBoxNumber;

    /**
     * <p>
     * This field represents the 'paymentTermsId' property of the Project.
     * </p>
     * <p>
     * It is default to the default value of this data type when it is not
     * assigned.
     * </p>
     * <p>
     * Through corresponding getter/setter methods. It is retrieved from
     * Project.paymentTermsId [Project.getPaymentTermsId()] and in table
     * project.payment_terms_id.
     * </p>
     * <p>
     * There are no restrictions at this moment. It can take any value.
     * OPTIONAL.
     * </p>
     */
    @Column(name = "payment_terms_id")
    private long paymentTermsId;

    /**
     * <p>
     * This field represents the 'description' property of the Project.
     * </p>
     * <p>
     * It is default to the default value of this data type when it is not
     * assigned.
     * </p>
     * <p>
     * Through corresponding getter/setter methods. It is retrieved from
     * Project.description [Project.getDescription()] and in table
     * project.description.
     * </p>
     * <p>
     * There are no restrictions at this moment. It can take any value.
     * OPTIONAL.
     * </p>
     */
    @Column(name = "description")
    private String description;

    /**
     * <p>
     * This field represents the 'projectStatus' property of the Project.
     * </p>
     * <p>
     * It is default to the default value of this data type when it is not
     * assigned.
     * </p>
     * <p>
     * Through corresponding getter/setter methods. It is retrieved from
     * Project.projectStatus [Project.getProjectStatus()] and in table
     * project.project_status_id.
     * </p>
     * <p>
     * There are no restrictions at this moment. It can take any value.
     * OPTIONAL.
     * </p>
     */
    @OneToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "project_status_id", updatable = false)
    private ProjectStatus projectStatus;

    /**
     * <p>
     * This field represents the 'client' property of the Project.
     * </p>
     * <p>
     * It is default to the default value of this data type when it is not
     * assigned.
     * </p>
     * <p>
     * Through corresponding getter/setter methods. It is retrieved from
     * Project.client [Project.getClient()] and in table project.client_id.
     * </p>
     * <p>
     * There are no restrictions at this moment. It can take any value.
     * OPTIONAL.
     * </p>
     */
    @OneToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "client_id", updatable = false)
    private Client client;

    /**
     * <p>
     * This field represents the 'childProjects' property of the Project.
     * </p>
     * <p>
     * It is default to the default value of this data type when it is not
     * assigned.
     * </p>
     * <p>
     * Through corresponding getter/setter methods. It is retrieved from
     * Project.childProjects [Project.getChildProjects()] and in table
     * project.project_id.
     * </p>
     * <p>
     * There are no restrictions at this moment. It can take any value.
     * OPTIONAL.
     * </p>
     */
    @OneToMany(cascade = CascadeType.REFRESH, fetch=FetchType.EAGER)
    @JoinColumn(name = "parent_project_id", referencedColumnName = "project_id", updatable = false)
    private List<Project> childProjects = new ArrayList<Project>();

    /**
     * <p>
     * This field represents the 'parentProjectId' property of the Project.
     * </p>
     * <p>
     * It is default to the default value of this data type when it is not
     * assigned.
     * </p>
     * <p>
     * Through corresponding getter/setter methods. It is retrieved from
     * Project.parentProjectId [Project.getParentProjectId()] and in table
     * project.parent_project_id.
     * </p>
     * <p>
     * There are no restrictions at this moment. It can take any value.
     * OPTIONAL.
     * </p>
     */
    @Column(name = "parent_project_id")
    private Long parentProjectId;

    /**
     * <p>
     * Represents whether this project allows/requires manual prize setting.
     * </p>
     *
     * @since 1.0.1
     */
    @Column(name = "is_manual_prize_setting")
    private boolean manualPrizeSetting;

    /**
     * Default no-arg constructor. Constructs a new 'Project' instance.
     */
    public Project() {
    }

    /**
     * Getter for 'active' property. Please refer to the related 'active' field
     * for more information.
     *
     * @return the value of the 'active' property. It can be any value.
     */
    public boolean isActive() {
        return this.active;
    }

    /**
     * Setter for 'active' property. Please refer to the related 'active' field
     * for more information.
     *
     * @param active
     *                the new active to be used for 'active' property. It can be
     *                any value.
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Getter for 'childProjects' property. Please refer to the related
     * 'childProjects' field for more information.
     *
     * @return the value of the 'childProjects' property. It can be any value.
     */
    public List<Project> getChildProjects() {
        return this.childProjects;
    }

    /**
     * Setter for 'childProjects' property. Please refer to the related
     * 'childProjects' field for more information.
     *
     * @param childProjects
     *                the new childProjects to be used for 'childProjects'
     *                property. It can be any value.
     */
    public void setChildProjects(List<Project> childProjects) {
        this.childProjects = childProjects;
    }

    /**
     * Getter for 'client' property. Please refer to the related 'client' field
     * for more information.
     *
     * @return the value of the 'client' property. It can be any value.
     */
    public Client getClient() {
        return this.client;
    }

    /**
     * Setter for 'client' property. Please refer to the related 'client' field
     * for more information.
     *
     * @param client
     *                the new client to be used for 'client' property. It can be
     *                any value.
     */
    public void setClient(Client client) {
        this.client = client;
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
     * Getter for 'description' property. Please refer to the related
     * 'description' field for more information.
     *
     * @return the value of the 'description' property. It can be any value.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Setter for 'description' property. Please refer to the related
     * 'description' field for more information.
     *
     * @param description
     *                the new description to be used for 'description' property.
     *                It can be any value.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Getter for 'parentProjectId' property. Please refer to the related
     * 'parentProjectId' field for more information.
     *
     * @return the value of the 'parentProjectId' property. It can be any value.
     */
    public long getParentProjectId() {
        return this.parentProjectId;
    }

    /**
     * Setter for 'parentProjectId' property. Please refer to the related
     * 'parentProjectId' field for more information.
     *
     * @param parentProjectId
     *                the new parentProjectId to be used for 'parentProjectId'
     *                property. It can be any value.
     */
    public void setParentProjectId(long parentProjectId) {
        this.parentProjectId = parentProjectId;
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
     * Getter for 'POBoxNumber' property. Please refer to the related
     * 'POBoxNumber' field for more information.
     *
     * @return the value of the 'POBoxNumber' property. It can be any value.
     */
    public String getPOBoxNumber() {
        return this.pOBoxNumber;
    }

    /**
     * Setter for 'POBoxNumber' property. Please refer to the related
     * 'POBoxNumber' field for more information.
     *
     * @param pOBoxNumber
     *                the new pOBoxNumber to be used for 'POBoxNumber' property.
     *                It can be any value.
     */
    public void setPOBoxNumber(String pOBoxNumber) {
        this.pOBoxNumber = pOBoxNumber;
    }

    /**
     * Getter for 'projectStatus' property. Please refer to the related
     * 'projectStatus' field for more information.
     *
     * @return the value of the 'projectStatus' property. It can be any value.
     */
    public ProjectStatus getProjectStatus() {
        return this.projectStatus;
    }

    /**
     * Setter for 'projectStatus' property. Please refer to the related
     * 'projectStatus' field for more information.
     *
     * @param projectStatus
     *                the new projectStatus to be used for 'projectStatus'
     *                property. It can be any value.
     */
    public void setProjectStatus(ProjectStatus projectStatus) {
        this.projectStatus = projectStatus;
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
     * Gets whether this project supports/requires manual prize setting.
     *
     * @return whether this project supports/requires manual prize setting.
     * @since 1.0.1
     */
    public boolean isManualPrizeSetting() {
        return this.manualPrizeSetting;
    }

    /**
     * Sets whether this project supports/requires manual prize setting.
     *
     * @param manualPrizeSetting
     *            whether this project supports/requires manual prize setting.
     * @since 1.0.1
     */
    public void setManualPrizeSetting(boolean manualPrizeSetting) {
        this.manualPrizeSetting = manualPrizeSetting;
    }
}
