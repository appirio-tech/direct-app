/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.configs;

import javax.xml.bind.annotation.*;

/**
 * <p>
 * Configuration for the Cloud VM Service Access Error Message.
 * </p>
 *
 * @author jiajizhou86
 * @version 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "CloudVMServiceAccessErrorConfig")
public class CloudVMServiceAccessErrorConfig {

    /**
     * The general error message to display to frontend for access cloud vm service.
     */
    @XmlElement
    private String generalErrorMessage;

    /**
     * Gets the general error message to display to frontend for access cloud vm service.
     *
     * @return the general error message to display to frontend for access cloud vm service.
     */
    public String getGeneralErrorMessage() {
        return generalErrorMessage;
    }

    /**
     * Sets the general error message to display to frontend for access cloud vm service.
     *
     * @param generalErrorMessage the general error message to display to frontend for access cloud vm service.
     */
    public void setGeneralErrorMessage(String generalErrorMessage) {
        this.generalErrorMessage = generalErrorMessage;
    }
}
