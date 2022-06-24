/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.facade.user;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * This class represents the "user_service_fault" element in WSDL.
 * </p>
 * 
 * <p>
 * This class is not thread safe because it's highly mutable
 * </p>
 * 
 * @author snow01
 * 
 * @since Jira & Confluence User Service
 * @version 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "faultMessage" })
@XmlRootElement(name = "user_service_facade_fault")
public class UserServiceFacadeFault implements Serializable {

    /**
     * Default serial version id.
     */
    private static final long serialVersionUID = 1L;

    /**
     * <p>
     * Represents the fault message
     * </p>
     */
    @XmlElement(name = "faultString", required = true)
    private String faultMessage;
    
    /**
     * <p>
     * This is the default constructor. It does nothing.
     * </p>
     */
    public UserServiceFacadeFault() {
    }

    /**
     * <p>
     * Gets the value of the faultMessage property.
     * </p>
     * 
     * @return the value of the faultMessage property.
     */
    public String getFaultMessage() {
        return faultMessage;
    }

    /**
     * <p>
     * Sets the value of the faultMessage property.
     * </p>
     * 
     * @param faultMessage
     *            the value of the faultMessage property to set, can be null, can be empty
     */
    public void setFaultMessage(String faultMessage) {
        this.faultMessage = faultMessage;
    }
}
