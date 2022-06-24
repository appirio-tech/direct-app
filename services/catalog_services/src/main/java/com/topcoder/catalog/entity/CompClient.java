/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.catalog.entity;

import java.io.Serializable;
import java.util.Set;

/**
 * <p>This class represents an association between a component and a client.</p>
 * <p>This entity also includes a list of the users for the client.</p>
 * <p>Validation of parameters is not performed in this class. It's supposed to be a caller's responsibility.</p>
 * <p><strong>Thread safety: </strong></p> <p>This class is mutable and not thread safe.</p>
 *
 * @author caru, Retunsky
 * @version 1.0
 */
public class CompClient implements Serializable {
    /**
     * <p>Represents the id of the client.</p>
     * <p>The initial value is <tt>null</tt>. Access is performed via its getter and setter.</p>
     * <p>The acceptance region: any <code>Long</code> value or <code>null</code>.</p>
     */
    private Long clientId;
    /**
     * <p>This field represents the set of ids of users of the client.</p>
     * <p>The initial value is <tt>null</tt>. Access is performed via its getter and setter.</p>
     * <p>The acceptable region: any set including <code>null</code> and empty one, a non-empty set
     * containing <code>null</code> is legal as well.</p>
     */
    private Set<Long> users;
    /**
     * <p>This field represents component for this client-component association.</p>
     * <p>The initial value is <tt>null</tt>. Access is performed via its getter and setter.</p>
     * <p>The acceptance region: any <code>Component</code> value or <code>null</code>.</p>
     */
    private Component component;

    /**
     * <p>Default constructor.</p> <p><em>Does nothing.</em></p>
     */
    public CompClient() {
    }

    /**
     * <p>Sets a value to the {@link #clientId} field.</p>
     * <p>The acceptance region: any <code>Long</code> value or <code>null</code>.</p>
     * @param clientId the client.
     */
    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    /**
     * <p>Retrieves the client.</p>
     *
     * @return {@link #clientId} property's value.
     */
    public Long getClientId() {
        return clientId;
    }

    /**
     * <p>Sets a value to the {@link #component} field.</p>
     * <p>The acceptance region: any <code>Component</code> value or <code>null</code>.</p>
     * @param component for the client-component association.
     */
    public void setComponent(Component component) {
        this.component = component;
    }

    /**
     * <p>Retrieves the component for this client-component association.</p>
     *
     * @return {@link #component} property's value.
     */
    public Component getComponent() {
        return component;
    }

    /**
     * <p>Sets a value to the {@link #users} field.</p>
     * <p>The acceptable region: any set including <code>null</code> and empty one, a non-empty set
     * containing <code>null</code> is legal as well.</p>
     * @param users the list of ids of users of the client.
     */
    public void setUsers(Set<Long> users) {
        this.users = users;
    }

    /**
     * <p>Retrieves the list of ids of users of the client.</p>
     *
     * @return {@link #users} property's value.
     */
    public Set<Long> getUsers() {
        return users;
    }

}

