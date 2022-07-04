/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project;

import java.io.Serializable;

/**
 * <p>
 * This class represents a copilot posting contest extra info type. The data is stored in
 * "copilot_contest_extra_info_type" table. This class implements Serializable interface to support serialization.
 * </p>
 * @author duxiaoyang
 * @version 1.0
 */
@SuppressWarnings("serial")
public class CopilotContestExtraInfoType implements Serializable {

    /**
     * Represents the extra info for budget.
     */
    public static final CopilotContestExtraInfoType BUDGET = new CopilotContestExtraInfoType(1, "Budget", "Budget");

    /**
     * Represents the extra info for other managing experience.
     */
    public static final CopilotContestExtraInfoType OTHER_MANAGING_EXPERIENCE = new CopilotContestExtraInfoType(2,
            "Other Managing Experience", "Other Managing Experience");

    /**
     * Represents the id of the copilot contest extra info type. It is initialized in the constructor and can be
     * accessed through corresponding getter/setter methods. It can be any value.
     */
    private long id;

    /**
     * Represents the name of the copilot contest extra info type. It is initialized in the constructor and can be
     * accessed through corresponding getter/setter methods. It can be any value.
     */
    private String name;

    /**
     * Represents the description of the copilot contest extra info type. It is initialized in the constructor and can
     * be accessed through corresponding getter/setter methods. It can be any value.
     */
    private String description;

    /**
     * <p>
     * Creates an instance of this class. It does nothing.
     * </p>
     */
    public CopilotContestExtraInfoType() {
    }

    /**
     * <p>
     * Creates an instance of this class using the given properties.
     * </p>
     * @param id
     *            the id of the copilot contest extra info type.
     * @param name
     *            the name of the copilot contest extra info type.
     * @param description
     *            the description of the copilot contest extra info type.
     */
    public CopilotContestExtraInfoType(long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    /**
     * <p>
     * Gets the id of the copilot contest extra info type.
     * </p>
     * @return the id of the copilot contest extra info type.
     */
    public long getId() {
        return id;
    }

    /**
     * <p>
     * Sets the id of the copilot contest extra info type.
     * </p>
     * @param id
     *            the id to set.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * <p>
     * Gets the name of the copilot contest extra info type.
     * </p>
     * @return the name of the copilot contest extra info type.
     */
    public String getName() {
        return name;
    }

    /**
     * <p>
     * Sets the name of the copilot contest extra info type.
     * </p>
     * @param name
     *            the name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * <p>
     * Gets the description of the copilot contest extra info type.
     * </p>
     * @return the description of the copilot contest extra info type.
     */
    public String getDescription() {
        return description;
    }

    /**
     * <p>
     * Sets the description of the copilot contest extra info type.
     * </p>
     * @param description
     *            the name to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
