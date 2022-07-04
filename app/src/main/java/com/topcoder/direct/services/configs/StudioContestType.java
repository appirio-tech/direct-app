/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.configs;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * The studio contest type class for xml handling.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "type")
public class StudioContestType {
    @XmlAttribute(required = true)
    private long contestTypeId;

    @XmlAttribute(required = true)
    private String description;

    public long getContestTypeId() {
        return contestTypeId;
    }

    public void setContestTypeId(long contestTypeId) {
        this.contestTypeId = contestTypeId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
