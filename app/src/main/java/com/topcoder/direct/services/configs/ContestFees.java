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
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "contestTypes")
public class ContestFees {
    @XmlElement(name = "contestType")
    private List<ContestFee> contestFees;

    public List<ContestFee> getContestFees() {
        if(contestFees == null) {
            contestFees = new ArrayList<ContestFee>();
        }
        return contestFees;
    }

    public void setContestFees(List<ContestFee> contestFees) {
        this.contestFees = contestFees;
    }
}
