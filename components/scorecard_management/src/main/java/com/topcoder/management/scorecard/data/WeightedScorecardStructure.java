/*
 * Copyright (C) 2006-2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.scorecard.data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Base class from which other Scorecard Structure classes inherit from to reduce redundancy of the manipulation of
 * a weight attribute.
 * </p>
 *
 * <p>
 * Changes in v1.1 (Cockpit Spec Review Backend Service Update v1.0):
 * - added @XmlSeeAlso for Group, Question and Section classes
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
@XmlType(name = "weightedScorecardStructure", propOrder = {"weight"})
@XmlSeeAlso ({Group.class, Question.class, Section.class })
public abstract class WeightedScorecardStructure extends NamedScorecardStructure {

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Static Fields

    /**
     * The sentinel weight, used to indicate that the weight has not yet been set through the constructor or the
     * setWeight method (or has been reset by the resetWeight method).
     */
    private static final float SENTINEL_WEIGHT = 0.0f;

    /**
     * The maximum allowable weight.
     */
    private static final float MAX_WEIGHT = 100.0f;

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Member Fields

    /**
     * <p>
     * The weight of the scorecard structure.
     * </p>
     *
     * <p>
     * A value of SENTINEL_NAME indicates that this field has not yet been initialized (and hence no weight
     * is given to this structure). When set, this field must be between 0 and 100, inclusive.
     * </p>
     */
    private float weight;

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Constructors

    /**
     * Creates a new WeightedScorecardStructure using the default values.
     */
    protected WeightedScorecardStructure() {
        super();
        setWeight(SENTINEL_WEIGHT);
    }

    /**
     * Creates a new WeightedScorecardStructure using the specified identifier, a default name, and a
     * default weight.
     *
     * @param   id
     *          The scorecard structure's identifier to initialize with.
     *
     * @throws  IllegalArgumentException
     *          The id is less than or equal to zero.
     */
    protected WeightedScorecardStructure(long id) {
        super(id);
        setWeight(SENTINEL_WEIGHT);
    }

    /**
     * Creates a new WeightedScorecardStructure using the specified identifier and name, and a default
     * weight.
     *
     * @param   id
     *          The scorecard structure's identifier to initialize with.
     * @param   name
     *          The scorecard structure's name to initialize with.
     *
     * @throws  IllegalArgumentException
     *          The id is less than or equal to zero, or the name is null.
     */
    protected WeightedScorecardStructure(long id, String name) {
        super(id, name);
        setWeight(SENTINEL_WEIGHT);
    }

    /**
     * Creates a new WeightedScorecardStructure using the specified identifier and weight, and a default
     * name.
     *
     * @param   id
     *          The scorecard structure's identifier to initialize with.
     * @param   weight
     *          The scorecard structure's weight to initialize with.
     *
     * @throws  IllegalArgumentException
     *          The id is less than or equal to zero, or the weight is less than 0, greater than 100,
     *          or equal to Float.NaN.
     */
    protected WeightedScorecardStructure(long id, float weight) {
        super(id);
        setWeight(weight);
    }

    /**
     * Creates a new WeightedScorecardStructure using the specified identifier, weight, and name.
     *
     * @param   id
     *          The scorecard structure's identifier to initialize with.
     * @param   name
     *          The scorecard structure's name to initialize with.
     * @param   weight
     *          The scorecard structure's weight to initialize with.
     *
     * @throws  IllegalArgumentException
     *          The id is less than or equal to zero, or the weight is less than 0, greater than 100,
     *          or equal to Float.NaN, or the name is null.
     */
    protected WeightedScorecardStructure(long id, String name, float weight) {
        super(id, name);
        setWeight(weight);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Member Methods - weight Accessors and Mutators

    /**
     * Sets the scorecard structure's weight.
     *
     * @param   weight
     *          The scorecard structure's new weight.
     *
     * @throws  IllegalArgumentException
     *          The weight is less than 0, greater than 100, or equal to Float.NaN.
     */
    public void setWeight(float weight) {
        this.weight = weight;
    }

    /**
     * Resets the scorecard structure's weight to an "undefined" value (which is 0).
     */
    public void resetWeight() {
        weight = SENTINEL_WEIGHT;
    }

    /**
     * Gets the scorecard structure's weight.
     *
     * @return  The scorecard structure's weight.
     */
    public float getWeight() {
        return weight;
    }
}
