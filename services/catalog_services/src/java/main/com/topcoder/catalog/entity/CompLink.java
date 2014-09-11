/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.catalog.entity;

import java.io.Serializable;

/**
 * <p>This class stores a link for the component compVersion; e.g. SVN link for the component files.</p>
 * <p>Validation of parameters is not performed in this class. It's supposed to be a caller's responsibility.</p>
 * <p><strong>Thread safety: </strong></p> <p>This class is mutable and not thread safe.</p>
 *
 * @author caru, Retunsky
 * @version 1.0
 */
public class CompLink implements Serializable {
    /**
     * <p>This field represents the id of the entity.</p>
     * <p>The initial value is <tt>null</tt>. Access is performed via its getter and setter.</p>
     * <p>The acceptance region: any <code>Long</code> value or <code>null</code>.</p>
     */
    private Long id;
    /**
     * <p>This field represents the link for the component files (e.g. SVN link).</p>
     * <p>The initial value is <tt>null</tt>. Access is performed via its getter and setter.</p>
     * <p>The acceptance region: any <code>String</code> value including empty ones or <code>null</code>.</p>
     */
    private String link;
    /**
     * <p>This field represents the component compVersion which link is associated with.</p>
     * <p>The initial value is <tt>null</tt>. Access is performed via its getter and setter.</p>
     * <p>The acceptance region: any <code>CompVersion</code> value or <code>null</code>.</p>
     */
    private CompVersion compVersion;
    /**
     * <p>Default constructor.</p> <p><em>Does nothing.</em></p>
     */
    public CompLink() {
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
     * <p>Sets a value to the {@link #link} field.</p>
     * <p>The acceptance region: any <code>String</code> value including empty ones or <code>null</code>.</p>
     * @param link the SVN link for the component files.
     */
    public void setLink(String link) {
        this.link = link;
    }

    /**
     * <p>Retrieves the SVN link for the component files.</p>
     *
     * @return {@link #link} property's value.
     */
    public String getLink() {
        return link;
    }

    /**
     * <p>Retrieves the component compVersion which link is associated with.</p>
     *
     * @return {@link #compVersion} property's value.
     */
    public CompVersion getCompVersion() {
        return compVersion;
    }

    /**
     * <p>Sets a value to the {@link #compVersion} field.</p>
     * <p>The acceptance region: any <code>CompVersion</code> value or <code>null</code>.</p>
     * @param compVersion the component compVersion which link is associated with
     */
    public void setCompVersion(CompVersion compVersion) {
        this.compVersion = compVersion;
    }
}

