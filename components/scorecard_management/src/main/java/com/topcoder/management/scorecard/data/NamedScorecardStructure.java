/*
 * Copyright (C) 2006-2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.scorecard.data;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Base class from which other Scorecard Structure classes inherit from to reduce redundancy of the manipulation of
 * identifier and name attributes.
 * </p>
 *
 * <p>
 * Changes in v1.1 (Cockpit Spec Review Backend Service Update v1.0):
 * - added @XmlSeeAlso for Scorecard, QuestionType, WeightedScorecardStructure, ScorecardStatus and ScorecardType
 * classes
 * </p>
 *
 * <p>
 * It is expected that in each implementation of this class, all instances will have unique identifiers.
 * </p>
 *
 * <p>
 * <b>Thread Safety</b>: This class is NOT thread safe.
 * </p>
 *
 * @author      aubergineanode, UFP2161, pulky
 * @copyright   Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 * @version     1.1
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "namedScorecardStructure", propOrder = {"id", "name"})
@XmlSeeAlso ({Scorecard.class, QuestionType.class, WeightedScorecardStructure.class, ScorecardStatus.class,
    ScorecardType.class})
public abstract class NamedScorecardStructure implements Serializable {

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Static Fields

    /**
     * The sentinel identifier, used to indicate that the identifier has not yet been set through the constructor or
     * the setId method (or has been reset by the resetId method).
     */
    public static final long SENTINEL_ID = -1;

    /**
     * The sentinel name, used to indicate that the name has not yet been set through the constructor or the
     * setName method (or has been reset by the resetName method).
     */
    private static final String SENTINEL_NAME = null;

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Member Fields

    /**
     * <p>
     * The unique identifier of the scorecard structure.
     * </p>
     *
     * <p>
     * A value of SENTINEL_ID indicates that this field has not yet been initialized. A positive value
     * indicates that the client has assigned an identifier. A negative value (other than the sentinel value) is
     * disallowed.
     * </p>
     */
    private long id;

    /**
     * <p>
     * The name of the scorecard structure.
     * </p>
     *
     * <p>
     * A value of SENTINEL_NAME indicates that this field has not yet been initialized. There are no
     * restrictions of what this field can be set to (it may be the empty string, a blank string, etc.).
     * </p>
     */
    private String name;

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Constructors

    /**
     * Creates a new NamedScorecardStructure using the default values.
     */
    protected NamedScorecardStructure() {
        this.id = SENTINEL_ID;
        this.name = SENTINEL_NAME;
    }

    /**
     * Creates a new NamedScorecardStructure using the specified identifier and a default name.
     *
     * @param   id
     *          The scorecard structure's identifier to initialize with.
     *
     * @throws  IllegalArgumentException
     *          The id is less than or equal to zero.
     */
    protected NamedScorecardStructure(long id) {
        setId(id);
        this.name = SENTINEL_NAME;
    }

    /**
     * Creates a new NamedScorecardStructure using the specified identifier and name.
     *
     * @param   id
     *          The scorecard structure's identifier to initialize with.
     * @param   name
     *          The scorecard structure's name to initialize with.
     *
     * @throws  IllegalArgumentException
     *          The id is less than or equal to zero, or the name is null.
     */
    protected NamedScorecardStructure(long id, String name) {
        setId(id);
        Util.checkNotNull(name, "name");
        this.name = name;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Member Methods - id Accessors and Mutators

    /**
     * Sets the scorecard structure's unique identifier.
     *
     * @param   id
     *          The scorecard structure's new identifier.
     *
     * @throws  IllegalArgumentException
     *          The id is less than or equal to zero.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Resets the scorecard structure's unique identifier to an "undefined" value (which is -1).
     */
    public void resetId() {
        id = SENTINEL_ID;
    }

    /**
     * Gets the scorecard structure's unique identifier. If it hasn't been set yet, the
     * {@link #SENTINEL_ID SENTINEL_ID} will be returned.
     *
     * @return  The scorecard structure's unique identifier.
     */
    public long getId() {
        return id;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Member Methods - name Accessors and Mutators

    /**
     * Sets the scorecard structure's name.
     *
     * @param   name
     *          The scorecard structure's new name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Resets the scorecard structure's name to an "undefined" value (which is null).
     */
    public void resetName() {
        setName(SENTINEL_NAME);
    }

    /**
     * Gets the scorecard structure's name.
     *
     * @return  The scorecard structure's name.
     */
    public String getName() {
        return name;
    }
}
