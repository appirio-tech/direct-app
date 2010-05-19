/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.configs;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * The contest fee class for xml handling.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "contestType")
public class ContestFee {
    @XmlAttribute
    private String id;

    @XmlAttribute(required = true)
    private long contestTypeId;

    @XmlElement(name = "subType")
    private List<StudioSubtypeContestFee> studioSubtypeContestFees;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getContestTypeId() {
        return contestTypeId;
    }

    public void setContestTypeId(long contestTypeId) {
        this.contestTypeId = contestTypeId;
    }

    public List<StudioSubtypeContestFee> getStudioSubtypeContestFees() {
        if (studioSubtypeContestFees == null) {
            studioSubtypeContestFees = new ArrayList<StudioSubtypeContestFee>();
        }
        return studioSubtypeContestFees;
    }

    public void setStudioSubtypeContestFees(List<StudioSubtypeContestFee> studioSubtypeContestFees) {
        this.studioSubtypeContestFees = studioSubtypeContestFees;
    }
}
