/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.catalog.entity;

import java.io.Serializable;

/**
 * <p>This class represents an association between a component and a user, meaning that the user can view
 * and change component data.</p>
 * <p>Validation of parameters is not performed in this class. It's supposed to be a caller's responsibility.</p>
 * <p><strong>Thread safety: </strong></p> <p>This class is mutable and not thread safe.</p>
 *
 * @author caru, Retunsky
 * @version 1.0
 */
public class CompUser implements Serializable {
    /**
     * <p>This field represents the id of the user.</p>
     * <p>The initial value is <tt>null</tt>. Access is performed via its getter and setter.</p>
     * <p>The acceptance region: any <code>Long</code> value or <code>null</code>.</p>
     */
    private Long userId;

    /**
     * <p>This field represents component for this user-component association.</p>
     * <p>The initial value is <tt>null</tt>. Access is performed via its getter and setter.</p>
     * <p>The acceptance region: any <code>Component</code> value or <code>null</code>.</p>
     */
    private Component component;

    /**
     * <p>Default constructor.</p> <p><em>Does nothing.</em></p>
     */
    public CompUser() {
    }

    /**
     * <p>Sets a value to the {@link #userId} field.</p>
     * <p>The acceptance region: any <code>Long</code> value or <code>null</code>.</p>
     * @param userId the id of the user.
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * <p>Retrieves the id of the user.</p>
     *
     * @return {@link #userId} property's value.
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * <p>Sets a value to the {@link #component} field.</p>
     * <p>The acceptance region: any <code>Component</code> value or <code>null</code>.</p>
     * @param component for the user-component association.
     */
    public void setComponent(Component component) {
        this.component = component;
    }

    /**
     * <p>Retrieves the component for this user-component association.</p>
     *
     * @return {@link #component} property's value.
     */
    public Component getComponent() {
        return component;
    }
}

