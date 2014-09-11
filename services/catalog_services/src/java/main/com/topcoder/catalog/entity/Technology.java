/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.catalog.entity;

import java.io.Serializable;

/**
 * <p>This class represents a technology that a component version uses, like "XML", "EJB", "Spring" and so on.</p>
 * <p>The status is used to indicate whether the technology is active or it was logically deleted.</p>
 * <p>Validation of parameters is not performed in this class. It's supposed to be a caller's responsibility.</p>
 * <p><strong>Thread safety: </strong></p> <p>This class is mutable and not thread safe.</p>
 *
 * @author caru, Retunsky
 * @version 1.0
 */
public class Technology implements Serializable {
    /**
     * <p>This field represents the id of the entity.</p>
     * <p>The initial value is <tt>null</tt>. Access is performed via its getter and setter.</p>
     * <p>The acceptance region: any <code>Long</code> value or <code>null</code>.</p>
     */
    private Long id;
    /**
     * <p>This field represents the name of the technology.</p>
     * <p>The initial value is <tt>null</tt>. Access is performed via its getter and setter.</p>
     * <p>The acceptance region: any <code>String</code> value including empty ones or <code>null</code>.</p>
     */
    private String name;
    /**
     * <p>This field represents the description of the technology.</p>
     * <p>The initial value is <tt>null</tt>. Access is performed via its getter and setter.</p>
     * <p>The acceptance region: any <code>String</code> value including empty ones or <code>null</code>.</p>
     */
    private String description;
    /**
     * <p>This field represents the status of the technology.</p>
     * <p>The initial value is <tt>null</tt>. Access is performed via its getter and setter.</p>
     * <p>The acceptance region: any <code>Status</code> instance or <tt>null</tt>.</p>
     */
    private Status status;


    /**
     * <p>Default constructor.</p> <p><em>Does nothing.</em></p>
     */
    public Technology() {
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
     * <p>Sets a value to the {@link #name} field.</p>
     * <p>The acceptance region: any <code>String</code> value including empty ones or <code>null</code>.</p>
     * @param name the name of the technology.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * <p>Retrieves the name of the technology.</p>
     *
     * @return {@link #name} property's value.
     */
    public String getName() {
        return name;
    }

    /**
     * <p>Sets a value to the {@link #description} field.</p>
     * <p>The acceptance region: any <code>String</code> value including empty ones or <code>null</code>.</p>
     * @param description the description of the technology.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * <p>Retrieves the description of the technology.</p>
     *
     * @return {@link #description} property's value.
     */
    public String getDescription() {
        return description;
    }

    /**
     * <p>Sets a value to the {@link #status} field.</p>
     * <p>The acceptance region: any <code>Status</code> instance or <tt>null</tt>.</p>
     * @param status the status of the technology.
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * <p>Retrieves the status of the technology.</p>
     *
     * @return {@link #status} property's value.
     */
    public Status getStatus() {
        return status;
    }

}

