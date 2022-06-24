/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.catalog.entity;

import java.io.Serializable;

/**
 * <p>This class stores the forum associated with the component compVersion.</p>
 * <p>Validation of parameters is not performed in this class. It's supposed to be a caller's responsibility.</p>
 * <p><strong>Thread safety: </strong></p> <p>This class is mutable and not thread safe.</p>
 *
 * @author caru, Retunsky
 * @version 1.0
 */
public class CompForum implements Serializable {
    /**
     * <p>Represents the jive category id of the forum.</p>
     * <p>The initial value is <tt>null</tt>. Access is performed via its getter and setter.</p>
     * <p>The acceptance region: any <code>Long</code> value or <code>null</code>.</p>
     */
    private Long jiveCategoryId;
    /**
     * <p>This field represents the component compVersion which forum is associated with.</p>
     * <p>The initial value is <tt>null</tt>. Access is performed via its getter and setter.</p>
     * <p>The acceptance region: any <code>CompVersion</code> value or <code>null</code>.</p>
     */
    private CompVersion compVersion;

    /**
     * <p>Default constructor.</p> <p><em>Does nothing.</em></p>
     */
    public CompForum() {
    }

    /**
     * <p>Sets a value to the {@link #jiveCategoryId} field.</p>
     * <p>The acceptance region: any <code>Long</code> value or <code>null</code>.</p>
     * @param jiveCategoryId category id of the forum.
     */
    public void setJiveCategoryId(Long jiveCategoryId) {
        this.jiveCategoryId = jiveCategoryId;
    }

    /**
     * <p>Retrieves category id of the forum.</p>
     *
     * @return {@link #jiveCategoryId} property's value.
     */
    public Long getJiveCategoryId() {
        return jiveCategoryId;
    }

    /**
     * <p>Retrieves the component compVersion which forum is associated with.</p>
     *
     * @return {@link #compVersion} property's value.
     */
    public CompVersion getCompVersion() {
        return compVersion;
    }

    /**
     * <p>Sets a value to the {@link #compVersion} field.</p>
     * <p>The acceptance region: any <code>CompVersion</code> value or <code>null</code>.</p>
     * @param compVersion the component compVersion which forum is associated with
     */
    public void setCompVersion(CompVersion compVersion) {
        this.compVersion = compVersion;
    }
}

