/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.milestone.model;

/**
 * <p>
 * This is the base class for all lookup entities. It extends IdentifiableEntity, and additionally provides the
 * name of this entity.
 * </p>
 * <p>
 * <b>Thread Safety:</b> This class is mutable and not thread safe.
 * </p>
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
public abstract class NamedEntity extends IdentifiableEntity {
    /**
     * Represents the name of the entity. It is managed with a getter and setter. It may have any value. It is
     * fully mutable.
     */
    private String name;

    /**
     * Empty constructor.
     */
    protected NamedEntity() {
        // Empty
    }

    /**
     * <p>
     * Getter method for name, simply return the name of the entity.
     * </p>
     *
     * @return the value of name
     */
    public String getName() {
        return name;
    }

    /**
     * <p>
     * Setter method for name, simply set the name of the entity.
     * </p>
     *
     * @param name
     *            the value of name to set
     */
    public void setName(String name) {
        this.name = name;
    }

}
