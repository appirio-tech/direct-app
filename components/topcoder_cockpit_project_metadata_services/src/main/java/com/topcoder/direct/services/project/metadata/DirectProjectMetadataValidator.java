/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.metadata;

import com.topcoder.direct.services.project.metadata.entities.dao.DirectProjectMetadata;

/**
 * <p>
 * This interface is responsible for validating the project metadata before creating or updating. If any problem with
 * validation occurs, ValidationException is thrown.
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> The implementations are required to be thread safe.
 * </p>
 *
 * @author faeton, sparemax
 * @version 1.0
 */
public interface DirectProjectMetadataValidator {
    /**
     * Validates the project metadata. If metadata fails validation, ValidationException is thrown. If metadata passes
     * validation, no exception is thrown.
     *
     * @param projectMetadata
     *            the project metadata to validate.
     *
     * @throws IllegalArgumentException
     *             if projectMetadata is null.
     * @throws ValidationException
     *             if metadata fails validation.
     */
    public void validate(DirectProjectMetadata projectMetadata) throws ValidationException;
}
