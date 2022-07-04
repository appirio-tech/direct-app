/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.metadata.impl;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.commons.utils.ParameterCheckUtility;
import com.topcoder.commons.utils.ValidationUtility;
import com.topcoder.direct.services.project.metadata.ConfigurationException;
import com.topcoder.direct.services.project.metadata.DirectProjectMetadataValidator;
import com.topcoder.direct.services.project.metadata.ValidationException;
import com.topcoder.direct.services.project.metadata.entities.dao.DirectProjectMetadata;
import com.topcoder.direct.services.project.metadata.entities.dao.DirectProjectMetadataKey;
import com.topcoder.direct.services.project.metadata.entities.dao.DirectProjectMetadataPredefinedValue;
import com.topcoder.util.log.Log;

/**
 * <p>
 * This class is the default implementation of DirectProjectMetadataValidator. It extends
 * AbstractDirectProjectMetadataValidator to use entityManager and log. It is injected with validator mappings to
 * validate metadata.
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
public class DirectProjectMetadataValidatorImpl extends AbstractDirectProjectMetadataValidator implements
    DirectProjectMetadataValidator {
    /**
     * <p>
     * Represents the class name.
     * </p>
     */
    private static final String CLASS_NAME = DirectProjectMetadataValidatorImpl.class.getName();

    /**
     * <p>
     * Represents the SQL string to query the existence of direct project metadata.
     * </p>
     */
    private static final String QUERY_DIRECT_PROJECT_METADATA =
        "SELECT project_metadata_id FROM direct_project_metadata WHERE tc_direct_project_id=:tc_direct_project_id"
        + " AND project_metadata_key_id=:project_metadata_key_id";

    /**
     * <p>
     * The mapping for the validation of keys. The key is the id of the metadata key, the value is the regular
     * expression to validate value.
     * </p>
     *
     * <p>
     * The default value is null. It can not be null after configuration, contain null keys, null/empty or invalid
     * regular expression values. It is modified by setter. Its' legality is checked in checkInitialization method.
     * </p>
     */
    private Map<Long, String> validatorMapping;

    /**
     * <p>
     * The mapping for predefined keys. The key is the id of the metadata key, the value is true if the key value
     * should be selected from predefined list, false otherwise.
     * </p>
     *
     * <p>
     * The default value is null. It can not be null after configuration, contain null keys, null values. It is
     * modified by setter. Its' legality is checked in checkInitialization method.
     * </p>
     */
    private Map<Long, Boolean> predefinedKeys;

    /**
     * Creates an instance of DirectProjectMetadataValidatorImpl.
     */
    public DirectProjectMetadataValidatorImpl() {
        // Empty
    }

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
    @SuppressWarnings("unchecked")
    public void validate(DirectProjectMetadata projectMetadata) throws ValidationException {
        String signature = CLASS_NAME + ".validate(DirectProjectMetadata projectMetadata)";
        Log logger = getLogger();

        LoggingWrapperUtility.logEntrance(logger, signature,
            new String[] {"projectMetadata"},
            new Object[] {Helper.toString(projectMetadata)});

        try {
            ParameterCheckUtility.checkNotNull(projectMetadata, "projectMetadata");

            DirectProjectMetadataKey key = projectMetadata.getProjectMetadataKey();
            ValidationUtility.checkNotNull(key, "projectMetadata.getProjectMetadataKey()",
                ValidationException.class);

            String metadataValue = projectMetadata.getMetadataValue();
            ValidationUtility.checkNotNull(metadataValue, "projectMetadata.getMetadataValue()",
                ValidationException.class);

            boolean singleKey = key.isSingle();
            if (singleKey) {
                EntityManager entityManager = getEntityManager();
                Query query = entityManager.createNativeQuery(QUERY_DIRECT_PROJECT_METADATA);
                query.setParameter("tc_direct_project_id", projectMetadata.getTcDirectProjectId());
                query.setParameter("project_metadata_key_id", key.getId());

                List<Integer> resultList = query.getResultList();
                int resultNum = resultList.size();
                if (resultNum > 1) {
                    throw new ValidationException("The key is not single.");
                }
                if ((resultNum == 1) && ! ((long) resultList.get(0) == projectMetadata.getId())) {
                    throw new ValidationException("A record with the project id and the key id already exists.");
                }
            }
            if (key.getClientId() == null) {

                String regexp = validatorMapping.get(key.getId());
                if ((regexp != null) && (!Pattern.matches(regexp, metadataValue))) {
                    throw new ValidationException("The metadata value doesn't match the pattern: " + regexp);
                }

                Boolean predefined = predefinedKeys.get(key.getId());
                if ((predefined != null) && predefined) {
                    // Check predefined values
                    checkMetadataValue(metadataValue, key.getPredefinedValues());
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

    /**
     * Sets the mapping for the validation of keys. The key is the id of the metadata key, the value is the instance
     * regular expression to validate.
     *
     * @param validatorMapping
     *            the mapping for the validation of keys. The key is the id of the metadata key, the value is the
     *            instance regular expression to validate.
     */
    public void setValidatorMapping(Map<Long, String> validatorMapping) {
        this.validatorMapping = validatorMapping;
    }

    /**
     * Sets the mapping for the validation of keys. The key is the id of the metadata key, the value is true if the
     * key value should be selected from predefined list, false otherwise.
     *
     * @param predefinedKeys
     *            the mapping for the validation of keys. The key is the id of the metadata key, the value is true if
     *            the key value should be selected from predefined list, false otherwise.
     */
    public void setPredefinedKeys(Map<Long, Boolean> predefinedKeys) {
        this.predefinedKeys = predefinedKeys;
    }

    /**
     * This method is used to check, whether the dependencies were properly initialized for the class.
     *
     * @throws ConfigurationException
     *             if entityManager is <code>null</code>; validatorMapping is <code>null</code>, contains
     *             <code>null</code> keys or <code>null</code>/empty or invalid regular expression values;
     *             predefinedKeys is <code>null</code>, contains <code>null</code> keys or <code>null</code>
     *             values.
     */
    @Override
    @PostConstruct
    protected void checkInitialization() {
        super.checkInitialization();

        Helper.checkMap(validatorMapping, "validatorMapping");
        for (String value : validatorMapping.values()) {
            try {
                Pattern.compile(value);
            } catch (PatternSyntaxException e) {
                throw new ConfigurationException("The regular expression is invalid: " + value, e);
            }
        }
        Helper.checkMap(predefinedKeys, "predefinedKeys");
    }

    /**
     * Checks the metadata value.
     *
     * @param metadataValue
     *            the metadata value.
     * @param predefinedValues
     *            the predefined value.
     *
     * @throws ValidationException
     *             if the metadata value doesn't match any of the predefined values
     */
    private void checkMetadataValue(String metadataValue, List<DirectProjectMetadataPredefinedValue> predefinedValues)
        throws ValidationException {
        boolean matchPredefined = false;
        if (predefinedValues != null) {
            for (DirectProjectMetadataPredefinedValue predefinedValue : predefinedValues) {
                if (metadataValue.equals(predefinedValue.getPredefinedMetadataValue())) {
                    matchPredefined = true;
                    break;
                }
            }
        }
        if (!matchPredefined) {
            throw new ValidationException("The metadata value doesn't match any of the predefined values.");
        }
    }
}
