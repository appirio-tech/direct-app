/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.asset.entities;

/**
 * <p>
 * This class is a container for category search criteria. It defines search and filter criteria for categories. It is
 * a simple JavaBean (POJO) that provides getters and setters for all private attributes and performs no argument
 * validation in the setters.
 * </p>
 *
 * <p>
 * Any field of CategorySearchCriteria is optional, null means a condition is ignored. All fields of
 * CategorySearchCriteria are exact match. If criteria.page <= 0, all matched result should be returned.
 * </p>
 *
 * <p>
 * <strong>Thread safety:</strong> This class is mutable and not thread safe.
 * </p>
 *
 * @author LOY, sparemax
 * @version 1.0
 */
public class CategorySearchCriteria extends BaseSearchCriteria {
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
    private Long containerId;

    /**
     * Creates an instance of CategorySearchCriteria.
     */
    public CategorySearchCriteria() {
        // Empty
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
    public Long getContainerId() {
        return containerId;
    }

    /**
     * Sets the container id of asset category.
     *
     * @param containerId
     *            the container id of asset category.
     */
    public void setContainerId(Long containerId) {
        this.containerId = containerId;
    }
}
