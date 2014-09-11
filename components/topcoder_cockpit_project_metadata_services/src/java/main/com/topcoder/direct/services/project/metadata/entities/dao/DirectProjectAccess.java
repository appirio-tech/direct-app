/*
 * Copyright (C) 2010 - 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.metadata.entities.dao;

import java.io.Serializable;
import java.util.Date;

/**
 * This DTO represents an user access to direct project or direct project contest.
 *
 * @author TCSASSEMBLER
 * @version 1.0 (Release Assembly - TopCoder Cockpit Navigation Update)
 */
public class DirectProjectAccess implements Serializable {
    /**
     * The id.
     */
    private long id;

    /**
     * The user id.
     */
    private long userId;

    /**
     * The access item id.
     */
    private long accessItemId;

    /**
     * The access type id.
     */
    private long accessTypeId;

    /**
     * The access time.
     */
    private Date accessTime;

    /**
     * The item name.
     */
    private String itemName;

    /**
     * Gets the id.
     *
     * @return the id.
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id the id.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets the user id.
     *
     * @return the user id.
     */
    public long getUserId() {
        return userId;
    }

    /**
     * Sets the user id.
     *
     * @param userId the user id.
     */
    public void setUserId(long userId) {
        this.userId = userId;
    }

    /**
     * Gets the access item id.
     *
     * @return the access item id.
     */
    public long getAccessItemId() {
        return accessItemId;
    }

    /**
     * Sets the access item id.
     *
     * @param accessItemId the access item id.
     */
    public void setAccessItemId(long accessItemId) {
        this.accessItemId = accessItemId;
    }

    /**
     * Gets the access time.
     *
     * @return the access time.
     */
    public Date getAccessTime() {
        return accessTime;
    }

    /**
     * Sets the access time.
     *
     * @param accessTime the access time.
     */
    public void setAccessTime(Date accessTime) {
        this.accessTime = accessTime;
    }

    /**
     * Gets the access type id.
     *
     * @return the access type id.
     */
    public long getAccessTypeId() {
        return accessTypeId;
    }

    /**
     * Sets the access type id.
     *
     * @param accessTypeId the access type id.
     */
    public void setAccessTypeId(long accessTypeId) {
        this.accessTypeId = accessTypeId;
    }

    /**
     * Gets the item name.
     *
     * @return the item name.
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * Sets the item name.
     *
     * @param itemName the item name.
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}
