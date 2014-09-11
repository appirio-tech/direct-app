/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.asset.entities;

/**
 * <p>
 * This class is a container for asset category. It is a simple JavaBean (POJO) that provides getters and setters for
 * all private attributes and performs no argument validation in the setters.
 * </p>
 *
 * <p>
 * Category.containerType indicates what type is category associated to, it may be "project", "client", "global", or
 * other container types. The containerId means id of container object. At present, only project level categories are
 * supported. So containerType is "project", containerId is project id.
 * </p>
 *
 * <p>
 * <strong>Thread safety:</strong> This class is mutable and not thread safe.
 * </p>
 *
 * @author LOY, sparemax
 * @version 1.0
 */
public class Category {
    /**
     * The id of asset category. Can be any value. Has getter and setter.
     */
    private long id;

    /**
     * The name of asset category. Can be any value. Has getter and setter.
     */
    private String name;

    /**
     * The container type of asset category. Can be any value. Has getter and setter.
     */
    private String containerType;

    /**
     * The container id of asset category. Can be any value. Has getter and setter.
     */
    private long containerId;

    /**
     * Creates an instance of Category.
     */
    public Category() {
        // Empty
    }

    /**
     * Retrieves the id of asset category.
     *
     * @return the id of asset category.
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the id of asset category.
     *
     * @param id
     *            the id of asset category.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Retrieves the name of asset category.
     *
     * @return the name of asset category.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of asset category.
     *
     * @param name
     *            the name of asset category.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retrieves the container type of asset category.
     *
     * @return the container type of asset category.
     */
    public String getContainerType() {
        return containerType;
    }

    /**
     * Sets the container type of asset category.
     *
     * @param containerType
     *            the container type of asset category.
     */
    public void setContainerType(String containerType) {
        this.containerType = containerType;
    }

    /**
     * Retrieves the container id of asset category.
     *
     * @return the container id of asset category.
     */
    public long getContainerId() {
        return containerId;
    }

    /**
     * Sets the container id of asset category.
     *
     * @param containerId
     *            the container id of asset category.
     */
    public void setContainerId(long containerId) {
        this.containerId = containerId;
    }
}
