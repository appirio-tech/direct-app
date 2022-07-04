/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.management.scorecard.data;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;

/**
 * <p>
 * Allows a scorecard to be tagged as being of a certain type, and is mainly used to support the {@link Scorecard}
 * class.
 * </p>
 *
 * <p>
 * <b>Thread Safety</b>: This class is NOT thread safe.
 * </p>
 *
 * @author      aubergineanode, UFP2161
 * @copyright   Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 * @version     1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "scorecardType")
public class ScorecardType extends NamedScorecardStructure {

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Constructors

    /**
     * Creates a new ScorecardType using the default values.
     */
    public ScorecardType() {
        super();
    }

    /**
     * Creates a new ScorecardType using the specified identifier and a default name.
     *
     * @param   id
     *          The scorecard structure's identifier to initialize with.
     *
     * @throws  IllegalArgumentException
     *          The id is less than or equal to zero.
     */
    public ScorecardType(long id) {
        super(id);
    }

    /**
     * Creates a new ScorecardType using the specified identifier and name.
     *
     * @param   id
     *          The scorecard structure's identifier to initialize with.
     * @param   name
     *          The scorecard structure's name to initialize with.
     *
     * @throws  IllegalArgumentException
     *          The id is less than or equal to zero, or the name is null.
     */
    public ScorecardType(long id, String name) {
        super(id, name);
    }
}
