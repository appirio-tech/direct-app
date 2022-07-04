/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.asset.entities;

import java.util.List;

/**
 * <p>
 * This class is a container for an asset. Asset may have multiple versions, represented as AssetVersion. The latest
 * or current version of an Asset is Asset.currentVersion. It is a simple JavaBean (POJO) that provides getters and
 * setters for all private attributes and performs no argument validation in the setters.
 * </p>
 *
 * <p>
 * Asset.containerType indicates what type is asset associated to, it may be "project", "client", "global", or other
 * container types. The containerId means id of container object. At present, only project level assets are supported.
 * So containerType is "project", containerId is project id.
 * </p>
 *
 * <p>
 * <strong>Thread safety:</strong> This class is mutable and not thread safe.
 * </p>
 *
 * @author LOY, sparemax
 * @version 1.0
 */
public class Asset {
    /**
     * The id. Can be any value. Has getter and setter.
     */
    private long id;

    /**
     * The name. Can be any value. Has getter and setter.
     */
    private String name;

    /**
     * The current version. Can be any value. Has getter and setter.
     */
    private AssetVersion currentVersion;

    /**
     * The container type. Can be any value. Has getter and setter.
     */
    private String containerType;

    /**
     * The container id. Can be any value. Has getter and setter.
     */
    private long containerId;

    /**
     * The flag indicates whether it's public. Can be any value. Has getter and setter.
     */
    private boolean isPublic;

    /**
     * The categories. Can be any value. Has getter and setter.
     */
    private List<Category> categories;

    /**
     * Creates an instance of this class.
     */
    public Asset() {
        // Empty
    }

    /**
     * Retrieves the id.
     *
     * @return the id.
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id
     *            the id.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Retrieves the name.
     *
     * @return the name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     *
     * @param name
     *            the name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retrieves the current version.
     *
     * @return the current version.
     */
    public AssetVersion getCurrentVersion() {
        return currentVersion;
    }

    /**
     * Sets the current version.
     *
     * @param currentVersion
     *            the current version.
     */
    public void setCurrentVersion(AssetVersion currentVersion) {
        this.currentVersion = currentVersion;
    }

    /**
     * Retrieves the container type.
     *
     * @return the container type.
     */
    public String getContainerType() {
        return containerType;
    }

    /**
     * Sets the container type.
     *
     * @param containerType
     *            the container type.
     */
    public void setContainerType(String containerType) {
        this.containerType = containerType;
    }

    /**
     * Retrieves the container id.
     *
     * @return the container id.
     */
    public long getContainerId() {
        return containerId;
    }

    /**
     * Sets the container id.
     *
     * @param containerId
     *            the container id.
     */
    public void setContainerId(long containerId) {
        this.containerId = containerId;
    }

    /**
     * Retrieves the flag indicates whether it's public.
     *
     * @return the flag indicates whether it's public.
     */
    public boolean isPublic() {
        return isPublic;
    }

    /**
     * Sets the flag indicates whether it's public.
     *
     * @param isPublic
     *            the flag indicates whether it's public.
     */
    public void setPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    /**
     * Retrieves the categories.
     *
     * @return the categories.
     */
    public List<Category> getCategories() {
        return categories;
    }

    /**
     * Sets the categories.
     *
     * @param categories
     *            the categories.
     */
    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
