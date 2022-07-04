/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.project.edit;

import java.io.Serializable;

/**
 * This class is used to represent a metadata operation from the edit project page.
 *
 * @author TCSASSEMBLER
 * @version 1.0 (Module Assembly - TopCoder Cockpit Project Dashboard Edit Project version 1.0)
 */
public class ProjectMetadataOperation implements Serializable {

    /**
     * The remove operation.
     */
    public static final String REMOVE = "remove";

    /**
     * The update operation.
     */
    public static final String UPDATE = "update";

    /**
     * The add operation.
     */
    public static final String ADD = "add";

    /**
     * The id of the project metadata.
     */
    private long id;

    /**
     * The id of the project metadata key.
     */
    private long keyId;

    /**
     * The value of the project metadata.
     */
    private String value;

    /**
     * The operation name.
     */
    private String operation;

    /**
     * Gets the id of the project metadata.
     *
     * @return the id of the project metadata.
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the id of the project metadata.
     *
     * @param id the id of the project metadata.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets the key id of the project metadata.
     *
     * @return the key id of the project metadata.
     */
    public long getKeyId() {
        return keyId;
    }

    /**
     * Sets the key id of the project metadata.
     *
     * @param keyId the key id of the project metadata.
     */
    public void setKeyId(long keyId) {
        this.keyId = keyId;
    }

    /**
     * Gets the value of the project metadata.
     *
     * @return the value of the project metadata.
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value of the project metadata.
     *
     * @param value
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Gets the operation name.
     *
     * @return the operation name.
     */
    public String getOperation() {
        return operation;
    }

    /**
     * Sets the operation name.
     *
     * @param operation the name of the operation.
     */
    public void setOperation(String operation) {
        this.operation = operation;
    }
}
