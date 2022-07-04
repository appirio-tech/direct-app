/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.asset.project;

import com.topcoder.asset.entities.Asset;
import com.topcoder.asset.entities.AssetVersion;
import com.topcoder.asset.entities.Category;
import com.topcoder.asset.entities.User;
import com.topcoder.direct.services.view.dto.CommonDTO;

import java.util.List;

/**
 * <p>
 * The View DTO for the asset versions view.
 * </p>
 *
 * @author GreatKevin
 * @version 1.1 (Release Assembly - TopCoder Cockpit Asset View And File Version)
 */
public class ProjectAssetVersionsDTO extends CommonDTO {

    /**
     * The asset.
     */
    private Asset asset;

    /**
     * All the versions of the asset.
     */
    private List<AssetVersion> assetVersions;

    /**
     * All the categories of an asset.
     */
    private List<Category> assetCategories;

    /**
     * The client managers for the asset permission chosen
     */
    private List<User> clientManagers;

    /**
     * The TopCoder manager for the asset permission chosen.
     */
    private List<User> topcoderManagers;

    /**
     * The project copilots for the asset permission chosen.
     */
    private List<User> projectCopilots;

    /**
     * Gets the asset.
     *
     * @return the asset.
     */
    public Asset getAsset() {
        return asset;
    }

    /**
     * Sets the asset.
     *
     * @param asset the asset.
     */
    public void setAsset(Asset asset) {
        this.asset = asset;
    }

    /**
     * Gets the asset versions.
     *
     * @return the asset versions.
     */
    public List<AssetVersion> getAssetVersions() {
        return assetVersions;
    }

    /**
     * Sets the asset versions.
     *
     * @param assetVersions the asset versions.
     */
    public void setAssetVersions(List<AssetVersion> assetVersions) {
        this.assetVersions = assetVersions;
    }

    /**
     * Gets the asset categories.
     *
     * @return the asset categories.
     */
    public List<Category> getAssetCategories() {
        return assetCategories;
    }

    /**
     * Sets the asset categories.
     *
     * @param assetCategories the asset categories.
     */
    public void setAssetCategories(List<Category> assetCategories) {
        this.assetCategories = assetCategories;
    }

    /**
     * Gets the client managers.
     *
     * @return the client managers.
     */
    public List<User> getClientManagers() {
        return clientManagers;
    }

    /**
     * Sets the client managers
     *
     * @param clientManagers the client managers.
     */
    public void setClientManagers(List<User> clientManagers) {
        this.clientManagers = clientManagers;
    }

    /**
     * Gets the TopCoder managers.
     *
     * @return the TopCoder managers.
     */
    public List<User> getTopcoderManagers() {
        return topcoderManagers;
    }

    /**
     * Sets the TopCoder Managers.
     *
     * @param topcoderManagers the TopCoder managers.
     */
    public void setTopcoderManagers(List<User> topcoderManagers) {
        this.topcoderManagers = topcoderManagers;
    }

    /**
     * Gets the project copilots.
     *
     * @return the project copilots.
     */
    public List<User> getProjectCopilots() {
        return projectCopilots;
    }

    /**
     * Sets the project copilots.
     *
     * @param projectCopilots the project copilots.
     */
    public void setProjectCopilots(List<User> projectCopilots) {
        this.projectCopilots = projectCopilots;
    }
}
