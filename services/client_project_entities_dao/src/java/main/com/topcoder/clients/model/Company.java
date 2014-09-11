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
 * This class represents the Company java bean. An Company can contain a
 * passcode.
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
@XmlType(name = "company", propOrder = { "passcode" })
@Entity
@Table(name = "company")
@javax.persistence.AttributeOverride(name = "id", column = @Column(name = "company_id"))
public class Company extends AuditableEntity {
    /**
     * The serial version uid of this class.
     */
    private static final long serialVersionUID = 1924422078753827945L;

    /**
     * <p>
     * This field represents the 'passcode' property of the Company.
     * </p>
     * <p>
     * It is default to the default value of this data type when it is not
     * assigned.
     * </p>
     * <p>
     * Through corresponding getter/setter methods. It is retrieved from
     * Company.passcode [Company.getPasscode()] and in table company.passcode.
     * </p>
     * <p>
     * There are no restrictions at this moment. It can take any value.
     * OPTIONAL.
     * </p>
     */
    @Column(name = "passcode")
    private String passcode;

    /**
     * Default no-arg constructor. Constructs a new 'Company' instance.
     */
    public Company() {
    }

    /**
     * Getter for 'passcode' property. Please refer to the related 'passcode'
     * field for more information.
     *
     * @return the value of the 'passcode' property. It can be any value.
     */
    public String getPasscode() {
        return this.passcode;
    }

    /**
     * Setter for 'passcode' property. Please refer to the related 'passcode'
     * field for more information.
     *
     * @param passcode
     *                the new passcode to be used for 'passcode' property. It
     *                can be any value.
     */
    public void setPasscode(String passcode) {
        this.passcode = passcode;
    }
}
