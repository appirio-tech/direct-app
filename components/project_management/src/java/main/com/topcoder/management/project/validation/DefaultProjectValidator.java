/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project.validation;

import java.util.Iterator;
import java.util.Map.Entry;

import com.topcoder.management.project.Project;
import com.topcoder.management.project.ProjectValidator;
import com.topcoder.management.project.ValidationException;

/**
 * This is the default implementation of the ProjectValidator interface to
 * provide project validation functions.
 * <p>
 * It validates the project base on the following rules:
 * </p>
 * <ul>
 * <li>Project type/status/category name: Length must be less than or equal to
 * 64</li>
 * <li>Project type/status/category description: Length must be less than or
 * equal to 256</li>
 * <li>Project property key: Length must be less than or equal to 64</li>
 * <li>Project property value: Length must be less than or equal to 4096</li>
 * </ul>
 * <p>
 * Thread safety: This class is immutable and thread safe.
 * </p>
 *
 * @author tuenm, iamajia
 * @version 1.0
 */
public class DefaultProjectValidator implements ProjectValidator {
    /**
     * represents the max length of name.
     */
    private static final int MAX_LENGTH_OF_NAME = 64;

    /**
     * represents the max length of property key.
     */
    private static final int MAX_LENGTH_OF_PROPERTY_KEY = 64;

    /**
     * represents the max length of description.
     */
    private static final int MAX_LENGTH_OF_DESCRIPTION = 256;

    /**
     * represents the max length of property value.
     */
    private static final int MAX_LENGTH_OF_PROPERTY_VALUE = 4096;

    /**
     * Create a new instance of DefaultProjectValidator. This class does not
     * have any configuration settings. But the namespace parameter is provided
     * to comply with the contract defined in ScorecardValidator interface.
     *
     * @param namespace
     *            The namespace to load configuration settings.
     */
    public DefaultProjectValidator(String namespace) {
    }

    /**
     * <p>
     * It validates the project base on the following rules:
     * </p>
     * <ul>
     * <li>Project type/status/category name: Length must be less than 64</li>
     * <li>Project type/status/category description: Length must be less than
     * 256</li>
     * <li>Project property key: Length must be less than 64</li>
     * <li>Project property value: Length must be less than 4096</li>
     * </ul>
     * This method will throw ValidationException on the first problem it found.
     * The exception should contains meaningful error message about the
     * validation problem.
     *
     * @param project
     *            The project to validate.
     * @throws ValidationException
     *             if validation fails.
     * @throws IllegalArgumentException
     *             if project is null.
     */
    public void validateProject(Project project) throws ValidationException {
        if (project == null) {
            throw new IllegalArgumentException("project can not be null.");
        }
        validateStringLength(project.getProjectStatus().getName(), "project status's name", MAX_LENGTH_OF_NAME);
        validateStringLength(project.getProjectCategory().getName(), "project category's name", MAX_LENGTH_OF_NAME);
        validateStringLength(project.getProjectCategory().getProjectType().getName(), "project type's name",
            MAX_LENGTH_OF_NAME);
        validateStringLength(project.getProjectStatus().getDescription(), "project status's description",
            MAX_LENGTH_OF_DESCRIPTION);
        validateStringLength(project.getProjectCategory().getDescription(), "project category's description",
            MAX_LENGTH_OF_DESCRIPTION);
        validateStringLength(project.getProjectCategory().getProjectType().getDescription(),
            "project type's description", MAX_LENGTH_OF_DESCRIPTION);
        // validate each property.
        for (Iterator iter = project.getAllProperties().entrySet().iterator(); iter.hasNext();) {
            Entry entry = (Entry) iter.next();
            validateStringLength((String) entry.getKey(), "property key", MAX_LENGTH_OF_PROPERTY_KEY);
            validateStringLength(entry.getValue().toString(), "property value", MAX_LENGTH_OF_PROPERTY_VALUE);
        }
    }

    /**
     * This private method is used to validate the given string.<br>
     * check if given string length is less than or equal to given length.
     *
     * @param str
     *            the string to validate
     * @param name
     *            the name of given string
     * @param length
     *            the max length of given string
     * @throws ValidationException
     *             if name or key length greater than given length
     */
    private static void validateStringLength(String str, String name, int length) throws ValidationException {
        if (str.length() > length) {
            throw new ValidationException(name + "length must be less than or equal to " + length);
        }
    }
}
