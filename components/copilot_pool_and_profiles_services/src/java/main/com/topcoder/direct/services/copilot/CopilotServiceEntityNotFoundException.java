/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.copilot;

import com.topcoder.util.errorhandling.ExceptionData;

/**
 * <p>
 * This exception is thrown by implementations of GenericService when entity with the given ID doesn't exist in the
 * persistence.
 * </p>
 * <p>
 * <b>Thread Safety:</b> This class is not thread safe because its base class is not thread safe.
 * </p>
 *
 * @author saarixx, TCSDEVELOPER
 * @version 1.0
 */
public class CopilotServiceEntityNotFoundException extends CopilotServiceException {
    /**
     * The type name of the entity that cannot be found. Is initialized in the constructor and never changed after
     * that. Can be any value. Has a getter.
     */
    private final String entityTypeName;

    /**
     * The ID of the entity that cannot be found. Is initialized in the constructor and never changed after that. Can
     * be any value. Has a getter.
     */
    private final long entityId;

    /**
     * Creates a new instance of this exception with the given message, entity type and entity ID.
     *
     * @param message the detailed error message of this exception
     * @param entityId the ID of the entity that cannot be found
     * @param entityTypeName the type name of the entity that cannot be found
     */
    public CopilotServiceEntityNotFoundException(String message, String entityTypeName, long entityId) {
        super(message);
        this.entityTypeName = entityTypeName;
        this.entityId = entityId;
    }

    /**
     * Creates a new instance of this exception with the given message, cause, entity type and entity ID.
     *
     * @param message the detailed error message of this exception
     * @param cause the inner cause of this exception
     * @param entityId the ID of the entity that cannot be found
     * @param entityTypeName the type name of the entity that cannot be found
     */
    public CopilotServiceEntityNotFoundException(String message, Throwable cause, String entityTypeName,
        long entityId) {
        super(message, cause);
        this.entityTypeName = entityTypeName;
        this.entityId = entityId;
    }

    /**
     * Creates a new instance of this exception with the given message, exception data, entity type and entity ID.
     *
     * @param message the detailed error message of this exception
     * @param entityId the ID of the entity that cannot be found
     * @param data the exception data
     * @param entityTypeName the type name of the entity that cannot be found
     */
    public CopilotServiceEntityNotFoundException(String message, ExceptionData data, String entityTypeName,
        long entityId) {
        super(message, data);
        this.entityTypeName = entityTypeName;
        this.entityId = entityId;
    }

    /**
     * Creates a new instance of this exception with the given message, cause, exception data, entity type and entity
     * ID.
     *
     * @param message the detailed error message of this exception
     * @param cause the inner cause of this exception
     * @param entityId the ID of the entity that cannot be found
     * @param data the exception data
     * @param entityTypeName the type name of the entity that cannot be found
     */
    public CopilotServiceEntityNotFoundException(String message, Throwable cause, ExceptionData data,
        String entityTypeName, long entityId) {
        super(message, cause, data);
        this.entityTypeName = entityTypeName;
        this.entityId = entityId;
    }

    /**
     * Retrieves the type name of the entity that cannot be found.
     *
     * @return the type name of the entity that cannot be found
     */
    public String getEntityTypeName() {
        return entityTypeName;
    }

    /**
     * Retrieves the ID of the entity that cannot be found.
     *
     * @return the ID of the entity that cannot be found
     */
    public long getEntityId() {
        return entityId;
    }
}