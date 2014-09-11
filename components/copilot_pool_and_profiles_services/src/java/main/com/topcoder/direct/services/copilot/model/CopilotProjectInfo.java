/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.copilot.model;

/**
 * <p>This class is a container for a single copilot project detail. It is a simple JavaBean (POJO) that provides
 * getters and setters for all private attributes and performs no argument validation in the setters.</p>
 *
 * <p><strong>Thread safety:</strong> This class is mutable and not thread safe.</p>
 *
 * @author saarixx, TCSDEVELOPER
 * @version 1.0
 */
public class CopilotProjectInfo extends IdentifiableEntity {

    /**
     * <p>The copilot project info type. Can be any value. Has getter and setter.</p>
     */
    private CopilotProjectInfoType infoType;

    /**
     * <p>The ID of the copilot project. Can be any value. Has getter and setter.</p>
     */
    private long copilotProjectId;

    /**
     * <p>The value for the project info. Can be any value. Has getter and setter.</p>
     */
    private String value;

    /**
     * <p>Creates new instance of <code>{@link CopilotProjectInfo}</code> class.</p>
     */
    public CopilotProjectInfo() {
        // empty constructor
    }

    /**
     * <p>Retrieves the copilot project info type.</p>
     *
     * @return the copilot project info type
     */
    public CopilotProjectInfoType getInfoType() {
        return infoType;
    }

    /**
     * <p>Sets the copilot project info type.</p>
     *
     * @param infoType the copilot project info type
     */
    public void setInfoType(CopilotProjectInfoType infoType) {
        this.infoType = infoType;
    }

    /**
     * <p>Retrieves the ID of the copilot project.</p>
     *
     * @return the ID of the copilot project
     */
    public long getCopilotProjectId() {
        return copilotProjectId;
    }

    /**
     * <p>Sets the ID of the copilot project.</p>
     *
     * @param copilotProjectId the ID of the copilot project
     */
    public void setCopilotProjectId(long copilotProjectId) {
        this.copilotProjectId = copilotProjectId;
    }

    /**
     * <p>Retrieves the value for the project info.</p>
     *
     * @return the value for the project info
     */
    public String getValue() {
        return value;
    }

    /**
     * <p>Sets the value for the project info.</p>
     *
     * @param value the value for the project info
     */
    public void setValue(String value) {
        this.value = value;
    }
}

