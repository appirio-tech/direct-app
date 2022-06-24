/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.catalog.entity;

import java.io.Serializable;

/**
 * <p>This class represents a component phase, like <tt>Collaboration</tt>, <tt>Design</tt>, <tt>Development</tt>
 * or <tt>Completed</tt>.</p>
 * <p>Validation of parameters is not performed in this class. It's supposed to be a caller's responsibility.</p>
 * <p><strong>Thread safety: </strong></p> <p>This class is mutable and not thread safe.</p>
 *
 * @author caru, Retunsky
 * @version 1.0
 */
public class Phase implements Serializable {

    public static final long COLLABORATION_PHASE_ID = 111L;

    public static final long DEVELOPMENT_PHASE_ID = 113L;

    public static final long DESIGN_PHASE_ID = 112L;

    public static final long COMPLETED_PHASE_ID = 114L;

    /**
     * <p>This field represents the id of the entity.</p>
     * <p>The initial value is <tt>null</tt>. Access is performed via its getter and setter.</p>
     * <p>The acceptance region: any <code>Long</code> value or <code>null</code>.</p>
     */
    private Long id;
    /**
     * <p>This field represents the description of the phase.</p>
     * <p>The initial value is <tt>null</tt>. Access is performed via its getter and setter.</p>
     * <p>The acceptance region: any <code>String</code> value including empty ones or <code>null</code>.</p>
     */
    private String description;


    /**
     * <p>Default constructor.</p> <p><em>Does nothing.</em></p>
     */
    public Phase() {
    }

    /**
     * <p>Sets a value to the {@link #id} field.</p>
     * <p>The acceptance region: any <code>Long</code> value or <code>null</code>.</p>
     * @param id the id of the entity.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * <p>Retrieves the id of the entity.</p>
     *
     * @return {@link #id} property's value.
     */
    public Long getId() {
        return id;
    }

    /**
     * <p>Sets a value to the {@link #description} field.</p>
     * <p>The acceptance region: any <code>String</code> value including empty ones or <code>null</code>.</p>
     * @param description the description of the phase.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * <p>Retrieves the description of the phase.</p>
     *
     * @return {@link #description} property's value.
     */
    public String getDescription() {
        return description;
    }

}

