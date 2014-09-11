/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.invoices.model;

/**
 * <p>This is a base class for all lookup entities. It's assumed that each lookup entity has a name field. This class is
 * a simple JavaBean (POJO) that provides getters and setters for all private attributes and performs no argument
 * validation in the setters.</p>
 *
 * <p><strong>Thread safety:</strong> This class is mutable and not thread safe.</p>
 *
 * @author flexme
 * @version 1.0
 */
public abstract class LookupEntity extends IdentifiableEntity {
    /**
     * <p>The name associated with the lookup entity. Can be any value. Has getter and setter.</p>
     */
    private String name;

    /**
     * <p>Creates new instance of <code>{@link LookupEntity}</code> class.</p>
     */
    protected LookupEntity() {
        // empty constructor
    }

    /**
     * <p>Retrieves the name associated with the lookup entity.</p>
     *
     * @return the name associated with the lookup entity
     */
    public String getName() {
        return name;
    }

    /**
     * <p>Sets the name associated with the lookup entity.</p>
     *
     * @param name the name associated with the lookup entity
     */
    public void setName(String name) {
        this.name = name;
    }
}
