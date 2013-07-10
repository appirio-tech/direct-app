/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.asset.project;

import com.topcoder.asset.entities.Asset;
import com.topcoder.asset.entities.Category;
import com.topcoder.asset.entities.User;
import com.topcoder.direct.services.view.dto.CommonDTO;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * The view data for the <code>ProjectAssetsAction</code>
 * </p>
 * <p/>
 * <p>
 * Version 1.1 (Release Assembly - TopCoder Cockpit Asset View And File Version)
 * <ul>
 * <li>Adds {@link #myDateGroupedAssets} and its getter and setter</li>
 * <li>Adds {@link #myCategoryGroupedAssets} and its getter and setter</li>
 * <li>Adds {@link #assetCategories} and its getter and setter</li>
 * <li>Adds {@link #availableCategories} and its getter and setter</li>
 * <li>Adds {@link #availableUploaders} and its getter and setter</li>
 * <li>Adds {@link #myAvailableCategories} and its getter and setter</li>
 * <li>Adds {@link #currentUploader} and its getter and setter</li>
 * <li>Adds {@link #clientManagers} and its getter and setter</li>
 * <li>Adds {@link #topcoderManagers} and its getter and setter</li>
 * <li>Adds {@link #projectCopilots} and its getter and setter</li>
 * </ul>
 * </p>
 *
 * @author GreatKevin
 * @version 1.0 (Release Assembly - TopCoder Cockpit Asset View And Basic Upload version 1.0)
 */
public class ProjectAssetsViewDTO extends CommonDTO {
    /**
     * The assets grouped by date.
     */
    private Map<Date, List<Asset>> dateGroupedAssets;

    /**
     * The assets grouped by asset category.
     */
    private Map<String, List<Asset>> categoryGroupedAssets;

    /**
     * The assets grouped by date.
     *
     * @since 1.1
     */
    private Map<Date, List<Asset>> myDateGroupedAssets;

    /**
     * The assets grouped by asset category.
     *
     * @since 1.1
     */
    private Map<String, List<Asset>> myCategoryGroupedAssets;

    /**
     * All the categories of an asset.
     *
     * @since 1.1
     */
    private List<Category> assetCategories;

    /**
     * The existing categories to list in asset filter panel.
     *
     * @since 1.1
     */
    private Set<String> availableCategories;

    /**
     * The existing uploaders to list in asset filter panel.
     *
     * @since 1.1
     */
    private Set<String> availableUploaders;

    /**
     * The existing categories to list in asset filter panel - my assets view.
     *
     * @since 1.1
     */
    private Set<String> myAvailableCategories;

    /**
     * The name of the current uploader
     */
    private String currentUploader;

    /**
     * The client managers for the asset permission chosen
     *
     * @since 1.1
     */
    private List<User> clientManagers;

    /**
     * The TopCoder manager for the asset permission chosen.
     *
     * @since 1.1
     */
    private List<User> topcoderManagers;

    /**
     * The project copilots for the asset permission chosen.
     *
     * @since 1.1
     */
    private List<User> projectCopilots;

    /**
     * Gets the assets grouped by date.
     *
     * @return the assets grouped by date.
     */
    public Map<Date, List<Asset>> getDateGroupedAssets() {
        return dateGroupedAssets;
    }

    /**
     * Sets the assets grouped by date.
     *
     * @param dateGroupedAssets the assets grouped by date.
     */
    public void setDateGroupedAssets(Map<Date, List<Asset>> dateGroupedAssets) {
        this.dateGroupedAssets = dateGroupedAssets;
    }

    /**
     * Gets the assets grouped by category.
     *
     * @return the assets grouped by category.
     */
    public Map<String, List<Asset>> getCategoryGroupedAssets() {
        return categoryGroupedAssets;
    }

    /**
     * Sets the assets grouped by category.
     *
     * @param categoryGroupedAssets the assets grouped by category.
     */
    public void setCategoryGroupedAssets(Map<String, List<Asset>> categoryGroupedAssets) {
        this.categoryGroupedAssets = categoryGroupedAssets;
    }

    /**
     * Gets the available categories.
     *
     * @return the available categories.
     * @since 1.1
     */
    public Set<String> getAvailableCategories() {
        return availableCategories;
    }

    /**
     * Sets the available categories.
     *
     * @param availableCategories the available categories.
     * @since 1.1
     */
    public void setAvailableCategories(Set<String> availableCategories) {
        this.availableCategories = availableCategories;
    }

    /**
     * Gets available uploaders.
     *
     * @return the available uploaders.
     * @since 1.1
     */
    public Set<String> getAvailableUploaders() {
        return availableUploaders;
    }

    /**
     * Sets the avaiable uploaders
     *
     * @param availableUploaders the avaiable uploaders.
     * @since 1.1
     */
    public void setAvailableUploaders(Set<String> availableUploaders) {
        this.availableUploaders = availableUploaders;
    }

    /**
     * Gets the current uploader.
     *
     * @return current uploader.
     * @since 1.1
     */
    public String getCurrentUploader() {
        return currentUploader;
    }

    /**
     * Sets the current uploader.
     *
     * @param currentUploader the current uploader.
     * @since 1.1
     */
    public void setCurrentUploader(String currentUploader) {
        this.currentUploader = currentUploader;
    }

    /**
     * Gets my date grouped assets.
     *
     * @return my date grouped assets.
     * @since 1.1
     */
    public Map<Date, List<Asset>> getMyDateGroupedAssets() {
        return myDateGroupedAssets;
    }

    /**
     * Sets my date grouped assets.
     *
     * @param myDateGroupedAssets my date grouped assets.
     * @since 1.1
     */
    public void setMyDateGroupedAssets(Map<Date, List<Asset>> myDateGroupedAssets) {
        this.myDateGroupedAssets = myDateGroupedAssets;
    }

    /**
     * Gets my category grouped assets.
     *
     * @return my categories grouped assets.
     * @since 1.1
     */
    public Map<String, List<Asset>> getMyCategoryGroupedAssets() {
        return myCategoryGroupedAssets;
    }

    /**
     * Sets my categories grouped assets.
     *
     * @param myCategoryGroupedAssets my category grouped assets.
     * @since 1.1
     */
    public void setMyCategoryGroupedAssets(Map<String, List<Asset>> myCategoryGroupedAssets) {
        this.myCategoryGroupedAssets = myCategoryGroupedAssets;
    }

    /**
     * Gets my available categories.
     *
     * @return my available categories.
     * @since 1.1
     */
    public Set<String> getMyAvailableCategories() {
        return myAvailableCategories;
    }

    /**
     * Sets my available categories.
     *
     * @param myAvailableCategories my available categories.
     * @since 1.1
     */
    public void setMyAvailableCategories(Set<String> myAvailableCategories) {
        this.myAvailableCategories = myAvailableCategories;
    }

    /**
     * Gets asset categories.
     *
     * @return asset categories.
     * @since 1.1
     */
    public List<Category> getAssetCategories() {
        return assetCategories;
    }

    /**
     * Sets asset categories
     *
     * @param assetCategories the asset categories.
     * @since 1.1
     */
    public void setAssetCategories(List<Category> assetCategories) {
        this.assetCategories = assetCategories;
    }

    /**
     * Gets client managers.
     *
     * @return the client managers.
     * @since 1.1
     */
    public List<User> getClientManagers() {
        return clientManagers;
    }

    /**
     * Sets client managers
     *
     * @param clientManagers the client managers.
     * @since 1.1
     */
    public void setClientManagers(List<User> clientManagers) {
        this.clientManagers = clientManagers;
    }

    /**
     * Gets TopCoder managers.
     *
     * @return TopCoder managers.
     * @since 1.1
     */
    public List<User> getTopcoderManagers() {
        return topcoderManagers;
    }

    /**
     * Sets TopCoder managers.
     *
     * @param topcoderManagers TopCoder managers.
     * @since 1.1
     */
    public void setTopcoderManagers(List<User> topcoderManagers) {
        this.topcoderManagers = topcoderManagers;
    }

    /**
     * Gets project copilots.
     *
     * @return project copilots.
     * @since 1.1
     */
    public List<User> getProjectCopilots() {
        return projectCopilots;
    }

    /**
     * Set project copilots.
     *
     * @param projectCopilots project copilots.
     * @since 1.1
     */
    public void setProjectCopilots(List<User> projectCopilots) {
        this.projectCopilots = projectCopilots;
    }
}
