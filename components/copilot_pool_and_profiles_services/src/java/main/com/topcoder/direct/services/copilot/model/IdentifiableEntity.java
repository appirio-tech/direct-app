/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.copilot.model;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>This is a base class for all entities that have ID and auditing fields. It is a simple JavaBean (POJO) that
 * provides getters and setters for all private attributes and performs no argument validation in the setters.</p>
 *
 * <p><strong>Thread safety:</strong> This class is mutable and not thread safe.</p>
 *
 * @author saarixx, TCSDEVELOPER
 * @version 1.0
 */
public abstract class IdentifiableEntity implements Serializable {

    /**
     * <p>The ID of the entity. Can be any value. Has getter and setter.</p>
     */
    private long id;

    /**
     * <p>The user that created the entity. Can be any value. Has getter and setter.</p>
     */
    private String createUser;

    /**
     * <p>The creation date of the entity. Can be any value. Has getter and setter.</p>
     */
    private Date createDate;

    /**
     * <p>The last user that modified the entity. Can be any value. Has getter and setter.</p>
     */
    private String modifyUser;

    /**
     * <p>The last modification date of the entity. Can be any value. Has getter and setter.</p>
     */
    private Date modifyDate;

    /**
     * <p>Creates new instance of <code>{@link IdentifiableEntity}</code> class.</p>
     */
    protected IdentifiableEntity() {
        // empty constructor
    }

    /**
     * <p>Retrieves the ID of the entity.</p>
     *
     * @return the ID of the entity
     */
    public long getId() {
        return id;
    }

    /**
     * <p>Sets the ID of the entity.</p>
     *
     * @param id the ID of the entity
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * <p>Retrieves the user that created the entity.</p>
     *
     * @return the user that created the entity
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * <p>Sets the user that created the entity.</p>
     *
     * @param createUser the user that created the entity
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * <p>Retrieves the creation date of the entity.</p>
     *
     * @return the creation date of the entity
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * <p>Sets the creation date of the entity.</p>
     *
     * @param createDate the creation date of the entity
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * <p>Retrieves the last user that modified the entity.</p>
     *
     * @return the last user that modified the entity
     */
    public String getModifyUser() {
        return modifyUser;
    }

    /**
     * <p>Sets the last user that modified the entity.</p>
     *
     * @param modifyUser the last user that modified the entity
     */
    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }

    /**
     * <p>Retrieves the last modification date of the entity.</p>
     *
     * @return the last modification date of the entity
     */
    public Date getModifyDate() {
        return modifyDate;
    }

    /**
     * <p>Sets the last modification date of the entity.</p>
     *
     * @param modifyDate the last modification date of the entity
     */
    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }
}
