/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.metadata.entities.dao;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.annotations.Entity;

import com.topcoder.direct.services.project.metadata.entities.HelperEntities;
import com.topcoder.json.object.JSONObject;

/**
 * <p>
 *  This POJO class contains information for TC Direct Project.
 *  There is no any argument validation in setter method.
 * </p>
 *
 * <p>
 *  Thread Safety: This class is mutable and not thread safe.
 * </p>
 *
 * @author Standlove, CaDenza
 * @version 1.0
 */
@Entity
@SuppressWarnings("serial")
public class TcDirectProject implements Serializable {
    /**
     * The id of project.
     */
    private long projectId;

    /**
     * The project name.
     */
    private String name;

    /**
     * The project description.
     */
    private String description;

    /**
     * The project status id.
     */
    private int projectStatusId;

    /**
     * The id of user.
     */
    private int userId;

    /**
     * The project creation date.
     */
    private Date createDate;

    /**
     * The latest modification date.
     */
    private Date modifyDate;

    /**
     * Default constructor.
     */
    public TcDirectProject() {
        // Do nothing.
    }

    /**
     * Retrieve project id.
     *
     * @return id of project.
     */
    public long getProjectId() {
        return projectId;
    }

    /**
     * Setter project id.
     *
     * @param projectId the id of project.
     */
    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    /**
     * Retrieves the project name.
     *
     * @return the project name.
     */
    public String getName() {
        return name;
    }

    /**
     * Setter project name.
     *
     * @param name the name of project.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retrieves the project description.
     *
     * @return the project description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter pf project description.
     *
     * @param description the project description.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Retrieves the id of project status.
     *
     * @return id of project status.
     */
    public int getProjectStatusId() {
        return projectStatusId;
    }

    /**
     * Setter for id of project status.
     *
     * @param projectStatusId the id of project status.
     */
    public void setProjectStatusId(int projectStatusId) {
        this.projectStatusId = projectStatusId;
    }

    /**
     * Retrieves the user id.
     *
     * @return id of user.
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Setter for user id.
     *
     * @param userId the id of user.
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Retrieves the project creation date.
     *
     * @return the creation date.
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * Setter project creation date.
     *
     * @param createDate the project creation date.
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * Retrieves project modify date.
     *
     * @return the project modify date.
     */
    public Date getModifyDate() {
        return modifyDate;
    }

    /**
     * Setter for project modify date.
     *
     * @param modifyDate the project modify date.
     */
    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    /**
     * Converts the entity to a JSON string that can be used for logging.
     * @return the JSON string with entity data (not null)
     */
    public String toJSONString() {
        JSONObject jsonObject = new JSONObject();

        jsonObject.setLong("projectId", projectId);
        HelperEntities.setString(jsonObject, "name", name);
        HelperEntities.setString(jsonObject, "description", description);
        jsonObject.setInt("projectStatusId", projectStatusId);
        jsonObject.setInt("userId", userId);
        HelperEntities.setDate(jsonObject, "createDate", createDate);
        HelperEntities.setDate(jsonObject, "modifyDate", modifyDate);

        return jsonObject.toJSONString();
    }
}

