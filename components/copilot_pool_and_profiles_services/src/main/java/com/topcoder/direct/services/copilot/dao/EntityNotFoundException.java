/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.copilot.dao;

import com.topcoder.util.errorhandling.ExceptionData;

/**
 * <p>This exception is thrown by implementations of UtilityDAO and GenericDAO when entity with the given ID doesn't
 * exist in the persistence.</p>
 *
 * <p><strong>Thread safety:</strong> This class is not thread safe because its base class is not thread safe.</p>
 *
 * @author saarixx, TCSDEVELOPER
 * @version 1.0
 */
public class EntityNotFoundException extends CopilotDAOException {

    /**
     * <p>The type name of the entity that cannot be found. Is initialized in the constructor and never changed after
     * that. Can be any value. Has a getter.</p>
     */
    private final String entityTypeName;

    /**
     * <p>The ID of the entity that cannot be found. Is initialized in the constructor and never changed after that. Can
     * be any value. Has a getter.</p>
     */
    private final long entityId;

    /**
     * <p>Creates new instance of <code>{@link EntityNotFoundException}</code> class with given error message, entity
     * type name and entity ID.</p>
     *
     * @param message        the detailed error message of this exception
     * @param entityTypeName the type name of the entity that cannot be found
     * @param entityId       the ID of the entity that cannot be found
     */
    public EntityNotFoundException(String message, String entityTypeName, long entityId) {
        super(message);
        this.entityTypeName = entityTypeName;
        this.entityId = entityId;
    }

    /**
     * <p>Creates new instance of <code>{@link EntityNotFoundException}</code> class with error message, inner cause,
     * entity type name and entity ID.</p>
     *
     * @param message        the detailed error message of this exception
     * @param entityTypeName the type name of the entity that cannot be found
     * @param cause          the inner cause of this exception
     * @param entityId       the ID of the entity that cannot be found
     */
    public EntityNotFoundException(String message, Throwable cause, String entityTypeName, long entityId) {
        super(message, cause);

        this.entityTypeName = entityTypeName;
        this.entityId = entityId;
    }

    /**
     * <p>Creates new instance of <code>{@link EntityNotFoundException}</code> class with given error message, exception
     * data, entity type name and entity ID.</p>
     *
     * @param message        the detailed error message of this exception
     * @param entityTypeName the type name of the entity that cannot be found
     * @param entityId       the ID of the entity that cannot be found
     * @param data           the exception data
     */
    public EntityNotFoundException(String message, ExceptionData data, String entityTypeName, long entityId) {
        super(message, data);

        this.entityTypeName = entityTypeName;
        this.entityId = entityId;
    }

    /**
     * <p>Creates new instance of <code>{@link EntityNotFoundException}</code> class with given error message, inner
     * cause, exception data, entity type name and entity ID.</p>
     *
     * @param message        the detailed error message of this exception
     * @param entityTypeName the type name of the entity that cannot be found
     * @param cause          the inner cause of this exception
     * @param entityId       the ID of the entity that cannot be found
     * @param data           the exception data
     */
    public EntityNotFoundException(String message, Throwable cause, ExceptionData data, String entityTypeName,
                                   long entityId) {
        super(message, cause, data);

        this.entityTypeName = entityTypeName;
        this.entityId = entityId;
    }

    /**
     * <p>Retrieves the type name of the entity that cannot be found.</p>
     *
     * @return the type name of the entity that cannot be found
     */
    public String getEntityTypeName() {
        return entityTypeName;
    }

    /**
     * <p>Retrieves the ID of the entity that cannot be found.</p>
     *
     * @return the ID of the entity that cannot be found
     */
    public long getEntityId() {
        return entityId;
    }
}

