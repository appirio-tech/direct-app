/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project;

/**
 * <p>
 * This class represents an extra info associated with a copilot posting contest. The data is stored in
 * "copilot_contest_extra_info" table. This class implements Serializable interface to support serialization.
 * </p>
 * @author duxiaoyang
 * @version 1.0
 */
@SuppressWarnings("serial")
public class CopilotContestExtraInfo extends AuditableObject {

    /**
     * Represents the type of the copilot contest extra info. It is initialized to null and can be accessed through
     * corresponding getter/setter methods. It can be any value.
     */
    private CopilotContestExtraInfoType type;

    /**
     * Represents the value of the copilot contest extra info. It is initialized to null and can be accessed through
     * corresponding getter/setter methods. It can be any value.
     */
    private String value;

    /**
     * <p>
     * Creates an instance of this class. It does nothing.
     * </p>
     */
    public CopilotContestExtraInfo() {
    }

    /**
     * <p>
     * Gets the type of the copilot contest extra info.
     * </p>
     * @return the type of the copilot contest extra info.
     */
    public CopilotContestExtraInfoType getType() {
        return type;
    }

    /**
     * <p>
     * Sets the type of the copilot contest extra info.
     * </p>
     * @param type
     *            the type to set.
     */
    public void setType(CopilotContestExtraInfoType type) {
        this.type = type;
    }

    /**
     * <p>
     * Gets the value of the copilot contest extra info.
     * </p>
     * @return the value of the copilot contest extra info.
     */
    public String getValue() {
        return value;
    }

    /**
     * <p>
     * Sets the value of the copilot contest extra info.
     * </p>
     * @param value
     *            the value to set.
     */
    public void setValue(String value) {
        this.value = value;
    }
}
