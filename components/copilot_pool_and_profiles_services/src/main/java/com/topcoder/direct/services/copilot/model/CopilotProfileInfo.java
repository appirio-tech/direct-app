/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.copilot.model;

/**
 * <p>This class is a container for a single copilot profile detail. It is a simple JavaBean (POJO) that provides
 * getters and setters for all private attributes and performs no argument validation in the setters.</p>
 *
 * <p><strong>Thread safety:</strong> This class is mutable and not thread safe.</p>
 *
 * @author saarixx, TCSDEVELOPER
 * @version 1.0
 */
public class CopilotProfileInfo extends IdentifiableEntity {

    /**
     * <p>The copilot profile info type. Can be any value. Has getter and setter.</p>
     */
    private CopilotProfileInfoType infoType;

    /**
     * <p>The ID of the copilot profile. Can be any value. Has getter and setter.</p>
     */
    private long copilotProfileId;

    /**
     * <p>The value for the profile info. Can be any value. Has getter and setter.</p>
     */
    private String value;

    /**
     * <p>Creates new instance of <code>{@link CopilotProfileInfo}</code> class.</p>
     */
    public CopilotProfileInfo() {
        // empty constructor
    }

    /**
     * <p>Retrieves the copilot profile info type.</p>
     *
     * @return the copilot profile info type
     */
    public CopilotProfileInfoType getInfoType() {
        return infoType;
    }

    /**
     * <p>Sets the copilot profile info type.</p>
     *
     * @param infoType the copilot profile info type
     */
    public void setInfoType(CopilotProfileInfoType infoType) {
        this.infoType = infoType;
    }

    /**
     * <p>Retrieves the ID of the copilot profile.</p>
     *
     * @return the ID of the copilot profile
     */
    public long getCopilotProfileId() {
        return copilotProfileId;
    }

    /**
     * <p>Sets the ID of the copilot profile.</p>
     *
     * @param copilotProfileId the ID of the copilot profile
     */
    public void setCopilotProfileId(long copilotProfileId) {
        this.copilotProfileId = copilotProfileId;
    }

    /**
     * <p>Retrieves the value for the profile info.</p>
     *
     * @return the value for the profile info
     */
    public String getValue() {
        return value;
    }

    /**
     * <p>Sets the value for the profile info.</p>
     *
     * @param value the value for the profile info
     */
    public void setValue(String value) {
        this.value = value;
    }
}

