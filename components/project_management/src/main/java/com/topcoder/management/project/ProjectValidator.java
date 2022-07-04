/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project;


/**
 * This interface defines the contract that project validators should implement.
 * The implementation classes will be used by ProjectManagerImpl to perform
 * project validation. ProjectManagerImpl loads the validation implementation
 * from the configuration settings, which allows further validator to plug-in.
 * The implementation classes should have a constructor that receives a
 * namespace string parameter so that they're exchangeable with each other by
 * changing configuration settings in the manager. The set of rules is the logic
 * of implementation classes.
 * <p>
 * Thread safety: The implementations of this interface do not have to be thread
 * safe.
 * </p>
 *
 * @author tuenm, iamajia
 * @version 1.0
 */
public interface ProjectValidator {
    /**
     * Validate the given project base on some specific rules. This method will
     * throw ValidationException on the first problem it found. The exception
     * should contains meaningful error message about the validation problem.
     *
     * @param project
     *            The project to validate.
     * @throws ValidationException
     *             if validation fails.
     * @throws IllegalArgumentException
     *             if project is null.
     */
    public void validateProject(Project project) throws ValidationException;
}
