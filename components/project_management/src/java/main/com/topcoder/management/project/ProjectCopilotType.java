/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project;

import java.io.Serializable;

/**
 * <p>
 * This class represents a project copilot type associated with a copilot posting contest. The data is stored in "project_copilot_type_lu" table. This class implements Serializable interface to support serialization.
 * </p>
 * @author duxiaoyang
 * @version 1.0
 */
@SuppressWarnings ("serial")
public class ProjectCopilotType implements Serializable {
	
	/**
	 * Represents the id of the project copilot type. It is initialized in the constructor and can be accessed through corresponding getter/setter methods. It can be any value.
	 */
	private long id;
	
	/**
	 * Represents the name of the project copilot type. It is initialized in the constructor and can be accessed through corresponding getter/setter methods. It can be any value.
	 */
	private String name;
	
	/**
	 * Represents the description of the project copilot type. It is initialized in the constructor and can be accessed through corresponding getter/setter methods. It can be any value.
	 */
	private String description;

	/**
	 * <p>
	 * Creates an instance of this class. It does nothing.
	 * </p>
	 */
	public ProjectCopilotType() {
	}
	
	/**
	 * <p>
	 * Creates an instance of this class using the given properties.
	 * </p>
	 * @param id the id of the project copilot type.
	 * @param name the name of the project copilot type.
	 * @param description the description of the project copilot type.
	 */
	public ProjectCopilotType(long id, String name, String description) {
		this.id = id;
		this.name = name;
		this.description = description;
	}
	
	/**
	 * <p>
	 * Gets the id of the project copilot type.
	 * </p>
	 * @return the id of the project copilot type.
	 */
	public long getId() {
		return id;
	}

	/**
	 * <p>
	 * Sets the id of the project copilot type.
	 * </p>
	 * @param id the id to set.
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * <p>
	 * Gets the name of the project copilot type.
	 * </p>
	 * @return the name of the project copilot type.
	 */
	public String getName() {
		return name;
	}

	/**
	 * <p>
	 * Sets the name of the project copilot type.
	 * </p>
	 * @param name the name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * <p>
	 * Gets the description of the project copilot type.
	 * </p>
	 * @return the description of the project copilot type.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * <p>
	 * Sets the description of the project copilot type.
	 * </p>
	 * @param description the name to set.
	 */
	public void setDescription(String description) {
		this.description = description;
	}
}
