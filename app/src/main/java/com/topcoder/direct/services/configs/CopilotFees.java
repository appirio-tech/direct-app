/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.configs;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * <p>
 * The contest fees class for xml handling.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 * @since TC Direct - Software Contest Creation Update Assembly
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "contestTypes")
public class CopilotFees {
    /**
     * List to store copilot fee of different software contest types.
     */
    @XmlElement(name = "contestType")
    private List<CopilotFee> copilotFees;

    /**
     * Gets the list of copilot fee.
     *
     * @return the list of copilot fee.
     */
    public List<CopilotFee> getCopilotFees() {
        if(copilotFees == null) {
            copilotFees = new ArrayList<CopilotFee>();
        }
        return copilotFees;
    }

    /**
     * Sets the copilot fees.
     *
     * @param copilotFees a list of copilot fee.
     */
    public void setCopilotFees(List<CopilotFee> copilotFees) {
        this.copilotFees = copilotFees;
    }
}
