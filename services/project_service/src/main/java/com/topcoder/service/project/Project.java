/*
 * Copyright (C) 2008 - 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.project;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlType;

import com.topcoder.service.project.entities.ProjectAnswer;

/**
 * <p>
 * This data object extends the <code>{@link ProjectData}</code> class and adds additional properties, including a set
 * of associated competitions.
 * </p>
 * <p>
 * We simply define 4 new properties and getter/setters for these properties. Note that this is a dumb DTO - no
 * validation is done.
 * </p>
 *
 * <p>Version 1.1 - Release Assembly - TopCoder Cockpit Project Status Management changes
 * <li>Refactor the projectStatusId property from this class to the parent class {@link ProjectData}</li>
 * </p>
 *
 * <p>
 * <b>Thread Safety</b>: This class is not thread safe as it has mutable state. Another reason is that its base class
 * is also not thread safe.
 * </p>
 *
 * @author humblefool, FireIce, GreatKevin
 * @version 1.1
 */
@XmlType(name = "project", namespace = "com.topcoder.service.project")
@Entity
public class Project extends ProjectData {
    /**
     * <p>
     * Represents the serial version unique id.
     * </p>
     */
    private static final long serialVersionUID = 2387108503992430447L;

    /**
     * <p>
     * Represents the ID of the user who created this project.
     * </p>
     * <p>
     * This variable is mutable and is retrieved by the {@link #getUserId()} method and set by the
     * {@link #setUserId(long)} method. It is initialized to zero, and may be set to ANY value.
     * </p>
     */
    private long userId;

    /**
     * <p>
     * Represents the date of creation of this project.
     * </p>
     * <p>
     * This variable is mutable and is retrieved by the {@link #getCreateDate()} method and set by the
     * {@link #setCreateDate(Date)} method. It is initialized to null, and may be set to ANY value.
     * </p>
     */
    private Date createDate;

    /**
     * <p>
     * Represents the date of last modification of this project.
     * </p>
     * <p>
     * This variable is mutable and is retrieved by the {@link #getModifyDate()} method and set by the
     * {@link #setModifyDate(Date)} method. It is initialized to null, and may be set to ANY value.
     * </p>
     */
    private Date modifyDate;


    /**
     * <p>
     * Represents the competitions associated with this project.
     * </p>
     * <p>
     * This variable is mutable and is retrieved by the {@link #getCompetitions()} method and set by the
     * {@link #setCompetitions(Set)} method. It is initialized to null, and may be set to ANY value. The members of the
     * collection may also have ANY value.
     * </p>
     */
    private Set < Competition > competitions;

    /**
     * <p>
     * Constructs a <code>Project</code> instance.
     * </p>
     */
    public Project() {
    }

    /**
     * <p>
     * Gets the user ID of the project.
     * </p>
     *
     * @return The user ID of the project.
     */
    public long getUserId() {
        return userId;
    }

    /**
     * <p>
     * Sets the ID of user who created this project.
     * </p>
     *
     * @param userId
     *            The desired ID of the user who created this project. ANY value.
     */
    public void setUserId(long userId) {
        this.userId = userId;
    }

    /**
     * <p>
     * Gets the date of creation of the project.
     * </p>
     *
     * @return The date of creation of the project.
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * <p>
     * Sets the date of creation of this project.
     * </p>
     *
     * @param createDate
     *            The desired date of creation of this project. ANY value.
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * <p>
     * Gets the date of modification of the project.
     * </p>
     *
     * @return The date of modification of the project.
     */
    public Date getModifyDate() {
        return modifyDate;
    }

    /**
     * <p>
     * Sets the date of modification of this project.
     * </p>
     *
     * @param modifyDate
     *            The desired date of modification of this project. ANY value.
     */
    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    /**
     * <p>
     * Gets the competitions associated with the project.
     * </p>
     *
     * @return The competitions associated with the project.
     */
    public Set < Competition > getCompetitions() {
        return competitions;
    }

    /**
     * <p>
     * Sets the competitions associated with this project.
     * </p>
     *
     * @param competitions
     *            The competitions associated with this project. ANY value.
     */
    public void setCompetitions(Set < Competition > competitions) {
        this.competitions = competitions;
    }

}
