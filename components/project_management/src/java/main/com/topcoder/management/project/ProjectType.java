/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project;

import java.io.Serializable;

/**
 * <p>
 * This class represents a project type from the persistence. Each project
 * category must belong to a project type. Project type are stored in
 * 'project_type_lu' table, project category in 'project_category_lu'. A project
 * type instance contains id, name and description. This class is used in
 * ProjectCategory class to specify the project type of the project category.
 * This class implements Serializable interface to support serialization.
 * </p>
 * <p>
 * Thread safety: This class is not thread safe.
 * </p>
 *
 * @author tuenm, iamajia
 * @version 1.0
 */
public class ProjectType implements Serializable {
    /**
     * Represents Studio project type.
     *
     * @since 1.2
     */
    public static final ProjectType STUDIO = new ProjectType(3, "Studio", "Studio");

    public static final ProjectType COMPONENT  = new ProjectType(1, "Component", "Component");

    public static final ProjectType APPLICATION  = new ProjectType(2, "Application", "Application");

    public static final ProjectType GENERIC  = new ProjectType(4, "Generic", "Generic (can not have projects created of that type)");



    /**
     * Represents the id of this instance. Only values greater than zero is
     * allowed. This variable is initialized in the constructor and can be
     * accessed in the corresponding getter/setter method.
     */
    private long id = 0;

    /**
     * Represents the name of this instance. Null or empty values are not
     * allowed. This variable is initialized in the constructor and can be
     * accessed in the corresponding getter/setter method.
     */
    private String name = null;

    /**
     * Represents the description of this instance. Null value is not allowed.
     * This variable is initialized in the constructor and can be accessed in
     * the corresponding getter/setter method.
     */
    private String description = null;
    
	/**
     * Create a new ProjectType instance with the given id and name. The two
     * fields are required for a this instance to be persisted.
     *
     * @param id
     *            The project type id.
     * @param name
     *            The project type name.
     * @throws IllegalArgumentException
     *             If id is less than or equals to zero, or name is null or
     *             empty string.
     */
    public ProjectType(long id, String name) {
        this(id, name, "");
    }

    /**
     * Create a new ProjectType instance with the given id, name and description. The two first fields are required for
     * a this instance to be persisted.
     *
     * @param id
     *            The project type id.
     * @param name
     *            The project type name.
     * @param description
     *            The project type description.
     * @throws IllegalArgumentException
     *             If id is less than or equals to zero, or name is null or empty string, or description is null.
     */
    public ProjectType(long id, String name, String description) {
        setId(id);
        setName(name);
        setDescription(description);
    }
	
	/**
     * Create a new ProjectType instance with the given id and name. The two
     * fields are required for a this instance to be persisted.
     *
     */
    public ProjectType() {
        
    }

    /**
     * Sets the id for this project type instance. Only positive values are
     * allowed.
     *
     * @param id
     *            The id of this project type instance.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets the id of this project type instance.
     *
     * @return the id of this project type instance.
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the name for this project type instance. Null or empty values are
     * not allowed.
     *
     * @param name
     *            The name of this project type instance.
     * @throws IllegalArgumentException
     *             If project type name is null or empty string.
     */
    public void setName(String name) {
        Helper.checkStringNotNullOrEmpty(name, "name");
        this.name = name;
    }

    /**
     * Gets the name of this project type instance.
     *
     * @return the name of this project type instance.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the description for this project type instance. Null value are not
     * allowed.
     *
     * @param description
     *            The description of this project type instance.
     * @throws IllegalArgumentException
     *             If project type description is null.
     */
    public void setDescription(String description) {
        Helper.checkObjectNotNull(description, "description");
        this.description = description;
    }

    /**
     * Gets the description of this project type instance.
     *
     * @return the description of this project type instance.
     */
    public String getDescription() {
        return description;
    }

}
