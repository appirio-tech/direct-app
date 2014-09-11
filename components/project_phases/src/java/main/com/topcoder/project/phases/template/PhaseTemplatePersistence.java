/*
 * Copyright (C) 2006-2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.phases.template;

import java.util.Date;

import com.topcoder.project.phases.Project;

/**
 * <p>
 * <code>PhaseTemplatePersistence</code> interface acts as the persistence
 * layer of the phase templates, so that the persistence is pluggable and can be
 * added without code changes. It manages a set of templates, provides the API
 * to generate an array of Phases from a template it manages and add the phases
 * to a given <code>Project</code>.
 * </p>
 * <p>
 * Note that this interface generates only phases, the <code>Project</code> generation
 * is out of the scope of this interface.
 * </p>
 * <p>
 * Note that the phase start dates, phase statuses will NOT be included in the template,
 * so these information will NOT be populated to the phases, they should be set by the
 * calling application.
 * </p>
 * <p>
 * In this initial release, the persistence is readonly, all templates are not
 * modifiable with this component.The template authoring functionalities may be
 * added in the future versions.
 * </p>
 * <p>
 * If a concrete implementation need configuration, it must define a constructor
 * with a string namespace argument, otherwise a default constructor without any
 * argument must be provided.<code>{@link DefaultPhaseTemplate}</code> will
 * try these two constructors.
 * </p>
 * <p>
 * Implementations should be thread safe, and it is easy to achieve in the
 * initial release as the templates are readonly.
 * </p>
 *
 * <p>
 * Change for version 1.1: In version 1.1 new methods were added. They allow to retrieve additional
 * information about each template: category, description and creation date, retrieve all template names and
 * default one for each category.
 * </p>
 * <p>
 * Change for version 1.2: Added leftOutPhaseIds parameter to generatePhases() method.
 * </p>
 *
 * @author albertwang, TCSDEVELOPER
 * @author flying2hk, TCSDEVELOPER
 * @author saarixx, TCSDEVELOPER
 * @version 1.2
 * @since 1.0
 */
public interface PhaseTemplatePersistence {

    /**
     * <p>
     * Generates an array of <code>Phase</code>s from the template with the given name and add them into the
     * given <code>Project</code> object.The dependency hierarchy will be populated too.
     * </p>
     * <p>
     * Change in 1.2: Added leftOutPhaseIds parameter.
     * </p>
     *
     * @param templateName
     *            the template name
     * @param project
     *            the project which the phases will be added to
     * @param leftOutPhaseIds
     *            the IDs of phases to be left out (null or empty if no phases should be left out)
     * @throws IllegalArgumentException
     *             if the templateName is null or empty string, or the project is null, or if leftOutPhaseIds
     *             contains unknown/duplicate phase ID
     * @throws PhaseGenerationException
     *             if any error occurred in the phase generation(e.g. cyclic dependency, etc.) so that the
     *             generation process can not complete successfully
     * @throws PersistenceException
     *             if error occurs while accessing the persistence layer
     */
    public void generatePhases(String templateName, Project project, long[] leftOutPhaseIds)
        throws PhaseGenerationException, PersistenceException;

    /**
     * <p>
     * Return the names of all templates defined in the persistence layer.
     * </p>
     *
     * @return the names of all templates defined in the persistence layer.
     */
    public String[] getAllTemplateNames();

    /**
     * <p>
     * Returns the list of names of templates those belong to the specified category. Never returns null.
     * </p>
     *
     * @param category
     *            the category of templates
     * @return an array of template name, could not be null
     * @throws PersistenceException
     *             if any error occurs in the method
     * @since 1.1
     */
    public String[] getAllTemplateNames(int category) throws PersistenceException;

    /**
     * <p>
     * Returns the category of template with the given name.
     * </p>
     *
     * @param templateName
     *            the name of template, should not be null or empty
     * @return the category of the template with the given name
     * @throws IllegalArgumentException
     *             if <code>templateName</code> is null or empty,
     *             or the template with the given name could not be found.
     * @throws PersistenceException
     *             if any error occurs in the method
     * @since 1.1
     */
    public int getTemplateCategory(String templateName) throws PersistenceException;

    /**
     * <p>
     * Returns the name of template that is default for the specified category. Returns null if default
     * template is not specified for the given category.
     * </p>
     *
     * @param category
     *            the category of templates
     * @return the name of the default template for the specified category
     * @throws PersistenceException
     *             if any error occurs in the method
     * @since 1.1
     */
    public String getDefaultTemplateName(int category) throws PersistenceException;

    /**
     * <p>
     * Returns the description of template with the given name. Can return null if description was not
     * specified.
     * </p>
     *
     * @param templateName
     *            the name of template, should not be null or empty
     * @return the given template's description, could be null
     * @throws IllegalArgumentException
     *             if templateName is null or empty, or the template with the
     *             given name could not be found.
     * @throws PersistenceException
     *             if any error occurs in the method
     * @since 1.1
     */
    public String getTemplateDescription(String templateName) throws PersistenceException;

    /**
     * <p>
     * Returns the creation date of template with the given name. Can return null if creation date was not
     * specified.
     * </p>
     *
     * @param templateName
     *            the name of template, should not be null or empty
     * @return the given template's date, could be null
     * @throws IllegalArgumentException
     *             if templateName is null or empty, or the template with the
     *             given name could not be found.
     * @throws PersistenceException
     *             if any error occurs in the method
     * @since 1.1
     */
    public Date getTemplateCreationDate(String templateName) throws PersistenceException;
}
