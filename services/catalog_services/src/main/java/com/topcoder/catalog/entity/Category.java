/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.catalog.entity;

import java.io.Serializable;

/**
 * <p>Categories are used to classify components. They can be related to other categories through
 * with parent-child relationships, creating a hierarchy.</p>
 * <p>On the top level, there are categories like "Java", "C++", ".Net", etc. (they don't have parent categories).
 * Then, other categories, like "JSF", "Swing", "Communication" are child of "Java".
 * At the moment when this code were written there was about 60 categories.</p>
 * <p>Field status indicates whether the category is active or deleted.</p>
 * <p>Field viewable indicates whether the category should be displayed to users in order to be
 * selected or not.</p>
 * <p>Root categories can be associated with <code>Catalogs</code>.
 * The name of the catalog can be retrieved from <code>catalogName</code> attribute.</p>
 *
 * <p>Validation of parameters is not performed in this class. It's supposed to be a caller's responsibility.</p>
 * <p><strong>NOTE: </strong></p> <p>This entity is <code>READ-ONLY</code>. Managing this entity
 * in the persistence is out of scope of this component.</p>
 * <p><strong>Thread safety: </strong></p> <p>This class is mutable and not thread safe.</p>
 *
 * @author caru, Retunsky
 * @version 1.0
 */
public class Category implements Serializable {
    /**
     * <p>This field represents the id of the entity.</p>
     * <p>The initial value is <tt>null</tt>. Access is performed via its getter and setter.</p>
     * <p>The acceptance region: any <code>Long</code> value or <code>null</code>.</p>
     */
    private Long id;
    /**
     * <p>This field represents the parent category of the category.</p>
     * <p>The initial value is <tt>null</tt>. Access is performed via its getter and setter.</p>
     * <p>The acceptance region: any <code>Category</code> instance or <tt>null</tt>.</p>
     */
    private Category parentCategory;
    /**
     * <p>This field represents the name of the category.</p>
     * <p>The initial value is <tt>null</tt>. Access is performed via its getter and setter.</p>
     * <p>The acceptance region: any <code>String</code> value including empty ones or <code>null</code>.</p>
     */
    private String name;
    /**
     * <p>This field represents the description of the category.</p>
     * <p>The initial value is <tt>null</tt>. Access is performed via its getter and setter.</p>
     * <p>The acceptance region: any <code>String</code> value including empty ones or <code>null</code>.</p>
     */
    private String description;
    /**
     * <p>This field represents the status of the category.</p>
     * <p>The initial value is <tt>null</tt>. Access is performed via its getter and setter.</p>
     * <p>The acceptance region: any <code>Status</code> instance or <tt>null</tt>.</p>
     */
    private Status status;
    /**
     * <p>This field indicates the visibility of the category.</p>
     * <p>The initial value is <code>false</code>.</p>
     * <p>The acceptable region: <code>true</code> or <code>false</code>.</p>
     */
    private boolean viewable;
    /**
     * <p>This field represents the name of the catalog this category belongs to.</p>
     * <p>The initial value is <tt>null</tt>. Access is performed via its getter and setter.</p>
     * <p>The acceptance region: any <code>String</code> value including empty ones or <code>null</code>.</p>
     */
    private String catalogName;


    /**
     * <p>Default constructor.</p> <p><em>Does nothing.</em></p>
     */
    public Category() {
    }

    /**
     * <p>Sets a value to the {@link #id} field.</p>
     * <p>The acceptance region: any <code>Long</code> value or <code>null</code>.</p>
     *
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
     * <p>Sets a value to the {@link #parentCategory} field.</p>
     * <p>The acceptance region: any <code>Category</code> instance or <tt>null</tt>.</p>
     *
     * @param parentCategory the parent category of the category.
     */
    public void setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
    }

    /**
     * <p>Retrieves the parent category of the category.</p>
     *
     * @return {@link #parentCategory} property's value.
     */
    public Category getParentCategory() {
        return parentCategory;
    }

    /**
     * <p>Sets a value to the {@link #name} field.</p>
     * <p>The acceptance region: any <code>String</code> value including empty ones or <code>null</code>.</p>
     *
     * @param name the name of the category.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * <p>Retrieves the name of the category.</p>
     *
     * @return {@link #name} property's value.
     */
    public String getName() {
        return name;
    }

    /**
     * <p>Sets a value to the {@link #description} field.</p>
     * <p>The acceptance region: any <code>String</code> value including empty ones or <code>null</code>.</p>
     *
     * @param description the description of the category.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * <p>Retrieves the description of the category.</p>
     *
     * @return {@link #description} property's value.
     */
    public String getDescription() {
        return description;
    }

    /**
     * <p>Sets a value to the {@link #status} field.</p>
     * <p>The acceptance region: any <code>Status</code> instance or <tt>null</tt>.</p>
     *
     * @param status the status of the category.
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * <p>Retrieves the status of the category.</p>
     *
     * @return {@link #status} property's value.
     */
    public Status getStatus() {
        return status;
    }

    /**
     * <p>Sets a value to the {@link #viewable} field.</p>
     * <p>The acceptable region: <code>true</code> or <code>false</code>.</p>
     *
     * @param viewable the visibility of the category.
     */
    public void setViewable(boolean viewable) {
        this.viewable = viewable;
    }

    /**
     * <p>Retrieves the visibility of the category.</p>
     *
     * @return {@link #viewable} property's value.
     */
    public boolean isViewable() {
        return viewable;
    }

    /**
     * <p>Sets a value to the {@link #catalogName} field.</p>
     * <p>The acceptance region: any <code>String</code> value including empty ones or <code>null</code>.</p>
     *
     * @param catalogName the name of the catalog this category belongs to.
     */
    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    /**
     * <p>Retrieves the name of the catalog this category belongs to.</p>
     *
     * @return {@link #catalogName} property's value.
     */
    public String getCatalogName() {
        return catalogName;
    }

}

