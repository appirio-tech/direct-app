/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.phases.template.persistence;

import java.util.Date;

import com.topcoder.project.phases.Project;
import com.topcoder.project.phases.template.PersistenceException;
import com.topcoder.project.phases.template.PhaseGenerationException;
import com.topcoder.project.phases.template.PhaseTemplatePersistence;
/**
 * <p>
 * Dummy implementation of <code>PhaseTemplatePersistence</code> for test.
 * </p>
 *
 * <p>
 * Change for version 1.1: add implemented methods.
 * </p>
 *
 * @author flying2hk
 * @author TCSDEVELOPER
 * @version 1.1
 * @since 1.0
 */
public class DummyPhaseTemplatePersistence implements PhaseTemplatePersistence {
    /**
     * <p>
     * Generates an array of Phases from the template with the given name and
     * add them into the given Project object.The dependency hierarchy will be
     * generated too.
     * </p>
     * @param templateName the template name
     * @param project the project which the phases will be added to
     * @throws IllegalArgumentException if the templateName is null or empty
     *             string, or the project is null
     * @throws PhaseGenerationException if any error occured in the phase
     *             generation(e.g. cyclic dependency, etc.) so that the
     *             generation process can not complete successfully
     * @throws PersistenceException if error occurs while accessing the
     *             persistence layer
     */
    public void generatePhases(String templateName, Project project) throws PhaseGenerationException,
            PersistenceException {
    }
    /**
     * <p>
     * Return the names of all templates defined in the persistence layer.
     * </p>
     * @return the names of all templates defined in the persistence layer.
     */
    public String[] getAllTemplateNames() {
        return null;
    }

    /**
     * <p>
     * Returns the list of names of templates those belong to the specified category. Never returns null.
     * </p>
     *
     * @param category the category of templates
     * @return an array of template name, could not be null
     * @since 1.1
     */
    public String[] getAllTemplateNames(int category) {
        return null;
    }

    /**
     * <p>
     * Returns the category of template with the given name.
     * </p>
     *
     * @param templateName the name of template, should not be null or empty
     * @return the category of the template with the given name
     * @throws IllegalArgumentException if <code>templateName</code> is null or empty,
     *         or the template with the given name could not be found.
     * @since 1.1
     */
    public int getTemplateCategory(String templateName) {
        return 0;
    }

    /**
     * <p>
     * Returns the name of template that is default for the specified category.
     * Returns null if default template is not specified for the given category.
     * </p>
     *
     * @param category the category of templates
     * @return the name of the default template for the specified category
     * @since 1.1
     */
    public String getDefaultTemplateName(int category) {
        return null;
    }

    /**
     * <p>
     * Returns the description of template with the given name. Can return null if description was not
     * specified.
     * </p>
     *
     * @param templateName the name of template, should not be null or empty
     * @return the given template's description, could be null
     * @throws IllegalArgumentException if templateName is null or empty, or the template with the
     *         given name could not be found.
     * @since 1.1
     */
    public String getTemplateDescription(String templateName) {
        return null;
    }

    /**
     * <p>
     * Returns the creation date of template with the given name. Can return null if creation date
     * was not specified.
     * </p>
     *
     * @param templateName the name of template, should not be null or empty
     * @return the given template's date, could be null
     * @throws IllegalArgumentException if templateName is null or empty, or the template with the
     * @since 1.1
     */
    public Date getTemplateCreationDate(String templateName) {
        return null;
    }

}
