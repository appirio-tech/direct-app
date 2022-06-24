/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.asset.entities;

/**
 * <p>
 * This class is a container for asset permission. It represents a permission that a user can access an asset.
 * Permissions are applicable for private assets, public assets can be accessed by anyone. It is a simple JavaBean
 * (POJO) that provides getters and setters for all private attributes and performs no argument validation in the
 * setters.
 * </p>
 *
 * <p>
 * <strong>Thread safety:</strong> This class is mutable and not thread safe.
 * </p>
 *
 * @author LOY, sparemax
 * @version 1.0
 */
public class AssetPermission {
    /**
     * The id of asset permission. Can be any value. Has getter and setter.
     */
    private long id;

    /**
     * The asset id. Can be any value. Has getter and setter.
     */
    private long assetId;

    /**
     * The user that holds the asset permission. Can be any value. Has getter and setter.
     */
    private User user;

    /**
     * Creates an instance of AssetPermission.
     */
    public AssetPermission() {
        // Empty
    }

    /**
     * Retrieves the id of asset permission.
     *
     * @return the id of asset permission.
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the id of asset permission.
     *
     * @param id
     *            the id of asset permission.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Retrieves the asset id.
     *
     * @return the asset id.
     */
    public long getAssetId() {
        return assetId;
    }

    /**
     * Sets the asset id.
     *
     * @param assetId
     *            the asset id.
     */
    public void setAssetId(long assetId) {
        this.assetId = assetId;
    }

    /**
     * Retrieves the user that holds the asset permission.
     *
     * @return the user that holds the asset permission.
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the user that holds the asset permission.
     *
     * @param user
     *            the user that holds the asset permission.
     */
    public void setUser(User user) {
        this.user = user;
    }
}
