/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * This class represents the ProjectStatus java bean. An ProjectStatus can
 * contain a description.
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
@XmlType(name = "clientProjectStatus", propOrder = { "description" })
@Entity
@Table(name = "project_status")
@javax.persistence.AttributeOverride(name = "id", column = @Column(name = "project_status_id"))
public class ProjectStatus extends AuditableEntity {
    /**
     * The serial version uid of this class.
     */
    private static final long serialVersionUID = 1985538062214695270L;

    /**
     * <p>
     * This field represents the 'description' property of the ProjectStatus.
     * </p>
     * <p>
     * It is default to the default value of this data type when it is not
     * assigned.
     * </p>
     * <p>
     * Through corresponding getter/setter methods. It is retrieved from
     * ProjectStatus.description [ProjectStatus.getDescription()] and in table
     * project_status.description.
     * </p>
     * <p>
     * There are no restrictions at this moment. It can take any value.
     * OPTIONAL.
     * </p>
     */
    @Column(name = "description")
    private String description;

    /**
     * Default no-arg constructor. Constructs a new 'ProjectStatus' instance.
     */
    public ProjectStatus() {
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
}
