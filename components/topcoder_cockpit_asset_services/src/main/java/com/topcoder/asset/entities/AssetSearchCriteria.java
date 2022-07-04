/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.asset.entities;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * This class is a container for asset search criteria. It defines search and filter criteria for assets. It is a
 * simple JavaBean (POJO) that provides getters and setters for all private attributes and performs no argument
 * validation in the setters.
 * </p>
 *
 * <p>
 * Any field of AssetSearchCriteria is optional, null means a condition is ignored.
 * <ul>
 * <li>The "user" (user name) condition is met if the user can access the asset, i.e. the user is uploader of the
 * current asset version or has permission (determined via AssetPermission) to access the asset. </li>
 * <li>The conditions follow "AND" logic.</li>
 * <li>The "categories" condition is met if asset.categories has a category whose name matches one of the categories
 * condition.</li>
 * <li>The "uploaders" condition is met if asset.currentVersion.uploader matches one of the uploaders condition.</li>
 * <li>This criteria matches data in Asset and Asset.currentVersion:AssetVersion.</li>
 * <li>AssetSearchCriteria.name is partial matching condition, it is matched if asset name contains the given name.
 * Other string condition matching is exact match.</li>
 * </ul>
 * </p>
 *
 * <p>
 * <strong>Thread safety:</strong> This class is mutable and not thread safe.
 * </p>
 *
 * @author LOY, sparemax
 * @version 1.0
 */
public class AssetSearchCriteria extends BaseSearchCriteria {
    /**
     * The name. Can be any value. Has getter and setter.
     */
    private String name;

    /**
     * The version. Can be any value. Has getter and setter.
     */
    private String version;

    /**
     * The file name. Can be any value. Has getter and setter.
     */
    private String fileName;

    /**
     * The categories. Can be any value. Has getter and setter.
     */
    private List<String> categories;

    /**
     * The uploaders. Can be any value. Has getter and setter.
     */
    private List<String> uploaders;

    /**
     * The user. Can be any value. Has getter and setter.
     */
    private String user;

    /**
     * The container type. Can be any value. Has getter and setter.
     */
    private String containerType;

    /**
     * The container id. Can be any value. Has getter and setter.
     */
    private Long containerId;

    /**
     * The start time. Can be any value. Has getter and setter.
     */
    private Date startTime;

    /**
     * The end time. Can be any value. Has getter and setter.
     */
    private Date endTime;

    /**
     * Creates an instance of this class.
     */
    public AssetSearchCriteria() {
        // Empty
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
     * Retrieves the version.
     *
     * @return the version.
     */
    public String getVersion() {
        return version;
    }

    /**
     * Sets the version.
     *
     * @param version
     *            the version.
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * Retrieves the file name.
     *
     * @return the file name.
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Sets the file name.
     *
     * @param fileName
     *            the file name.
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Retrieves the categories.
     *
     * @return the categories.
     */
    public List<String> getCategories() {
        return categories;
    }

    /**
     * Sets the categories.
     *
     * @param categories
     *            the categories.
     */
    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    /**
     * Retrieves the uploaders.
     *
     * @return the uploaders.
     */
    public List<String> getUploaders() {
        return uploaders;
    }

    /**
     * Sets the uploaders.
     *
     * @param uploaders
     *            the uploaders.
     */
    public void setUploaders(List<String> uploaders) {
        this.uploaders = uploaders;
    }

    /**
     * Retrieves the user.
     *
     * @return the user.
     */
    public String getUser() {
        return user;
    }

    /**
     * Sets the user.
     *
     * @param user
     *            the user.
     */
    public void setUser(String user) {
        this.user = user;
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
    public Long getContainerId() {
        return containerId;
    }

    /**
     * Sets the container id.
     *
     * @param containerId
     *            the container id.
     */
    public void setContainerId(Long containerId) {
        this.containerId = containerId;
    }

    /**
     * Retrieves the start time.
     *
     * @return the start time.
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * Sets the start time.
     *
     * @param startTime
     *            the start time.
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * Retrieves the end time.
     *
     * @return the end time.
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * Sets the end time.
     *
     * @param endTime
     *            the end time.
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
