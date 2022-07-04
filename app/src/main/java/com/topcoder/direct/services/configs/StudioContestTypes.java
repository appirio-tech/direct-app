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
 * The studio contest types class for xml handling.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "types")
public class StudioContestTypes {
    @XmlElement(name="type")
    private List<StudioContestType> contestTypes;

    public List<StudioContestType> getContestTypes() {
        if (contestTypes == null) {
            contestTypes = new ArrayList<StudioContestType>();
        }

        return contestTypes;
    }

    public void setContestTypes(List<StudioContestType> contestTypes) {
        this.contestTypes = contestTypes;
    }


}
