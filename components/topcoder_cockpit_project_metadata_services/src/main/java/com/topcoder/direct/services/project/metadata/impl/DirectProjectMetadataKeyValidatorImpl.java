/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.metadata.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.commons.utils.ParameterCheckUtility;
import com.topcoder.commons.utils.ValidationUtility;
import com.topcoder.direct.services.project.metadata.DirectProjectMetadataKeyValidator;
import com.topcoder.direct.services.project.metadata.ValidationException;
import com.topcoder.direct.services.project.metadata.entities.dao.DirectProjectMetadataKey;
import com.topcoder.direct.services.project.metadata.entities.dao.DirectProjectMetadataPredefinedValue;
import com.topcoder.util.log.Log;

/**
 * <p>
 * This class is the default implementation of DirectProjectMetadataKeyValidator. It extends
 * AbstractDirectProjectMetadataValidator to use entityManager and log.
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> The only non-thread safe part of class is configuration, but it is done in thread
 * safe manner by Spring IoC framework. The class is thread safe under these conditions.
 * </p>
 *
 * @author faeton, sparemax
 * @version 1.0
 */
public class DirectProjectMetadataKeyValidatorImpl extends AbstractDirectProjectMetadataValidator implements
    DirectProjectMetadataKeyValidator {
    /**
     * <p>
     * Represents the class name.
     * </p>
     */
    private static final String CLASS_NAME = DirectProjectMetadataKeyValidatorImpl.class.getName();

    /**
     * <p>
     * Represents the maximum length of name of direct project metadata key.
     * </p>
     */
    private static final int MAX_DIRECT_PROJECT_METADATA_KEY_NAME_LEN = 20;

    /**
     * <p>
     * Represents the SQL string to query the existence of direct project metadata key.
     * </p>
     */
    private static final String QUERY_DIRECT_PROJECT_METADATA_KEY =
        "SELECT 1 FROM direct_project_metadata_key WHERE name=:name";

    /**
     * Creates an instance of DirectProjectMetadataKeyValidatorImpl.
     */
    public DirectProjectMetadataKeyValidatorImpl() {
        // Empty
    }

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
    public void validate(DirectProjectMetadataKey projectMetadataKey) throws ValidationException {
        String signature = CLASS_NAME + ".validate(DirectProjectMetadataKey projectMetadataKey)";
        Log logger = getLogger();

        LoggingWrapperUtility.logEntrance(logger, signature,
            new String[] {"projectMetadataKey"},
            new Object[] {Helper.toString(projectMetadataKey)});

        try {
            ParameterCheckUtility.checkNotNull(projectMetadataKey, "projectMetadataKey");

            String name = projectMetadataKey.getName();
            ValidationUtility.checkNotNullNorEmptyAfterTrimming(name, "projectMetadataKey.getName()",
                ValidationException.class);

            if (projectMetadataKey.getClientId() == null) {
                if (projectMetadataKey.getGrouping() != null) {
                    throw new ValidationException("'projectMetadataKey.getGrouping()' should be null.");
                }
            } else {
                ValidationUtility.checkLessThan(name.length(), MAX_DIRECT_PROJECT_METADATA_KEY_NAME_LEN, true,
                    "projectMetadataKey.getName().length()", ValidationException.class);

                ValidationUtility.checkNotNull(projectMetadataKey.getGrouping(), "projectMetadataKey.getGrouping()",
                    ValidationException.class);

                List<DirectProjectMetadataPredefinedValue> predefinedValues =
                    projectMetadataKey.getPredefinedValues();
                if ((predefinedValues != null) && (!predefinedValues.isEmpty())) {
                    throw new ValidationException(
                        "'projectMetadataKey.getPredefinedValues()' should be null or empty.");
                }
            }


            // Check if project metadata key with the name exists
            EntityManager entityManager = getEntityManager();
            Query query = entityManager.createNativeQuery(QUERY_DIRECT_PROJECT_METADATA_KEY);
            query.setParameter("name", name);

            if (projectMetadataKey.getId() <= 0) {
                if (query.getResultList().size() > 0) {
                    throw new ValidationException("A key with the name already exists.");
                }
            }

            LoggingWrapperUtility.logExit(logger, signature, null);
        } catch (IllegalArgumentException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(logger, signature, e);
        } catch (ValidationException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(logger, signature, e);
        }
    }
}
