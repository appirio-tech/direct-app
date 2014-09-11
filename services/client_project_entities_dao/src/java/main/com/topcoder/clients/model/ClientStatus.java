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
 * This class represents the ClientStatus java bean. An ClientStatus can contain
 * a description. See base class for other available properties.
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
@XmlType(name = "clientStatus", propOrder = { "description" })
@Entity
@Table(name = "client_status")
@javax.persistence.AttributeOverride(name = "id", column = @Column(name = "client_status_id"))
public class ClientStatus extends AuditableEntity {
    /**
     * The serial version uid of this class.
     */
    private static final long serialVersionUID = -1339856229050220456L;

    /**
     * <p>
     * This field represents the 'description' property of the ClientStatus.
     * </p>
     * <p>
     * It is default to the default value of this data type when it is not
     * assigned.
     * </p>
     * <p>
     * Through corresponding getter/setter methods. It is retrieved from
     * ClientStatus.description [ClientStatus.getDescription()] and in table
     * client_status.description.
     * </p>
     * <p>
     * There are no restrictions at this moment. It can take any value.
     * OPTIONAL.
     * </p>
     */
    @Column(name = "description")
    private String description;

    /**
     * Default no-arg constructor. Constructs a new 'ClientStatus' instance.
     */
    public ClientStatus() {
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
