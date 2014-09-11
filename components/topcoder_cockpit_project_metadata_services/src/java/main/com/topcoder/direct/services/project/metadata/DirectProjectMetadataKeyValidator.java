/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.metadata;

import com.topcoder.direct.services.project.metadata.entities.dao.DirectProjectMetadataKey;

/**
 * <p>
 * This interface is responsible for validating the project metadata keys before creating or updating records in the
 * database. If any problem with validation occurs, ValidationException is thrown.
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> The implementations are required to be thread safe.
 * </p>
 *
 * @author faeton, sparemax
 * @version 1.0
 */
public interface DirectProjectMetadataKeyValidator {
    /**
     * Validates the project metadata key. If metadata key fails validation, ValidationException is thrown. If
     * metadata key passes validation, no exception is thrown.
     *
     * @param projectMetadataKey
     *            the project metadata key to validate.
     *
     * @throws IllegalArgumentException
     *             if projectMetadataKey is null.
     * @throws ValidationException
     *             if metadata key fails validation.
     */
    public void validate(DirectProjectMetadataKey projectMetadataKey) throws ValidationException;
}
