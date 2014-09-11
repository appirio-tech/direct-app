/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.phases.template;

import java.util.Date;

import com.topcoder.date.workdays.DefaultWorkdays;
import com.topcoder.project.phases.Project;

/**
 * <p>
 * A phase template is a set of predefined project phases and their dependencies, the phases will not have
 * start date defined in the templates.
 * </p>
 * <p>
 * <code>PhaseTemplate</code> interface defines the contract to access the phase templates, generally the
 * implementations will manage several different templates which are used to generate different project phase
 * hierarchies. It provides the API to generate a set of project phases from a given predefined template, and
 * compose a <code>Project</code> object with those phases, the project start date will be generated
 * automatically if there's no start date specified, it also provides the API to retrieve names of all
 * templates available to use.
 * </p>
 * <p>
 * Note that the phase start dates, phase statuses will NOT be included in the template, so these information
 * will NOT be populated to the phases, they should be set by the calling application.
 * </p>
 * <p>
 * The implementations should be thread-safe.
 * </p>
 * <p>
 * Change for version 1.1: In version 1.1 new methods were added. They allow the user to retrieve additional
 * information about each template: category, description and creation date, retrieve all template names and
 * default one for each category. Also methods for applying templates with intermediate phase start time
 * specified were added.
 * </p>
 * <p>
 * Change for version 1.2: Added applyTemplate() overloads that allow to left out some phases from the
 * template being applied. Fixed type of varPhaseId and fixedPhaseId parameters.
 * </p>
 *
 * @author albertwang, TCSDEVELOPER
 * @author flying2hk, TCSDEVELOPER
 * @author saarixx, TCSDEVELOPER
 * @version 1.2
 * @since 1.0
 */
public interface PhaseTemplate {

    /**
     * <p>
     * Return the names of all the templates available to use.
     * </p>
     *
     * @return the names of all the templates available to use
     */
    public String[] getAllTemplateNames();

    /**
     * <p>
     * Returns the list of names of templates those belong to the specified category. Never returns null.
     * </p>
     *
     * @param category
     *            the category of templates
     * @return the list of names of templates those belong to the specified category
     * @throws PhaseTemplateException
     *             if any error occurs in this method
     * @since 1.1
     */
    public String[] getAllTemplateNames(int category) throws PhaseTemplateException;

    /**
     * <p>
     * Returns the category of template with the given name.
     * </p>
     *
     * @param templateName
     *            the name of template, should not be null or empty
     * @return category of the template
     * @throws PhaseTemplateException
     *             if any error occurs in this method
     * @throws IllegalArgumentException
     *             if templateName is null or empty, or the template with the
     *             given name could not be found.
     * @since 1.1
     */
    public int getTemplateCategory(String templateName) throws PhaseTemplateException;

    /**
     * <p>
     * Returns the name of template that is default for the specified category. It would return null if
     * default template is not specified for the given category.
     * </p>
     *
     * @param category
     *            the category of templates
     * @return the name of the default template for the given category, could be null
     * @throws PhaseTemplateException
     *             if any error occurs in this method
     * @since 1.1
     */
    public String getDefaultTemplateName(int category) throws PhaseTemplateException;

    /**
     * <p>
     * Returns the description of template with the given name. Can return null if description was not
     * specified.
     * </p>
     *
     * @param templateName
     *            the name of template, should not be null or empty
     * @return the description of the template with the given name, could be null
     * @throws PhaseTemplateException
     *             if any error occurs in this method
     * @throws IllegalArgumentException
     *             if templateName is null or empty, or the template with the
     *             given name could not be found.
     * @since 1.1
     */
    public String getTemplateDescription(String templateName) throws PhaseTemplateException;

    /**
     * <p>
     * Returns the creation date of template with the given name. Can return null if creation date was not
     * specified.
     * </p>
     *
     * @param templateName
     *            the name of template, should not be null or empty
     * @return the creation date of the template with the given name, could be null
     * @throws PhaseTemplateException
     *             if any error occurs in this method
     * @throws IllegalArgumentException
     *             if templateName is null or empty, or the template with the
     *             given name could not be found.
     * @since 1.1
     */
    public Date getTemplateCreationDate(String templateName) throws PhaseTemplateException;

    /**
     * <p>
     * Generates a set of project phases and compose a Project object with those phases by applying the
     * template with the given name.
     * </p>
     * <p>
     * The start date of the project is set to the startDate. Start date of phase with id equal to
     * fixedPhaseId is adjusted to fixedPhaseStartDate by changing length of phase with id equal to
     * varPhaseId. If such adjustment is impossible then PhaseTemplateException would be thrown.
     * </p>
     * <p>
     * Change in 1.2: Type of varPhaseId and fixedPhaseId parameters was changed from "int" to "long".
     * </p>
     *
     * @param templateName
     *            the name of the template to be used, should not be null or empty
     * @param varPhaseId
     *            the id of the phase, length of which must be changed
     * @param fixedPhaseId
     *            the id of the phase, start date of which must be adjusted
     * @param fixedPhaseStartDate
     *            the desired start date of the intermediate phase, should not be
     *            null
     * @param startDate
     *            the start date of the project, should can not be null, should not be later than
     *            fixedPhaseStartDate
     * @return a Project with the phases generated from the given template
     * @throws IllegalArgumentException
     *             if templateName is null or empty or there's no template with
     *             the given name, if fixedPhaseStartDate or startDate is null or startDate >
     *             fixedPhaseStartDate, or if phases with the given ids can not be found
     * @throws PhaseTemplateException
     *             if any error occurred during the generation so that we can not
     *             apply the template successfully (incl. fixed phase start date can not be achieved)
     * @since 1.1
     */
    public Project applyTemplate(String templateName, long varPhaseId, long fixedPhaseId, Date fixedPhaseStartDate,
        Date startDate) throws PhaseTemplateException;

    /**
     * <p>
     * Generates a set of project phases and compose a Project object with those phases by applying the
     * template with the given name.
     * </p>
     * <p>
     * The start date of the project is set to a default value which depends on the specific implementations.
     * Start date of phase with id equal to fixedPhaseId is adjusted to fixedPhaseStartDate by changing length
     * of phase with id equal to varPhaseId. If such adjustment is impossible then PhaseTemplateException
     * would be thrown.
     * </p>
     * <p>
     * Change in 1.2: Type of varPhaseId and fixedPhaseId parameters was changed from "int" to "long".
     * </p>
     *
     * @param templateName
     *            the name of the template to be used, should not be null or empty
     * @param varPhaseId
     *            the id of the phase, length of which must be changed
     * @param fixedPhaseId
     *            the id of the phase, start date of which must be adjusted
     * @param fixedPhaseStartDate
     *            the desired start date of the intermediate phase, should not be
     *            null
     * @return a Project with the phases generated from the given template
     * @throws IllegalArgumentException
     *             if templateName is null or empty or there's no template with
     *             the given name, if fixedPhaseStartDate is null
     * @throws PhaseTemplateException
     *             if any error occurred during the generation so that we can not
     *             apply the template successfully (incl. fixed phase start date can not be achieved)
     * @since 1.1
     */
    public Project applyTemplate(String templateName, long varPhaseId, long fixedPhaseId, Date fixedPhaseStartDate)
        throws PhaseTemplateException;

    /**
     * <p>
     * Generate a set of project phases and compose a <code>Project</code> object with those phases by
     * applying the template with the given name.The start date of the project will be set to the startDate
     * provided by the caller.
     * </p>
     *
     * @param templateName
     *            the template name
     * @param startDate
     *            the start date of the project
     * @return a Project with the phases generated from the given template
     * @throws PhaseTemplateException
     *             if any error occurred during the generation so that we can not
     *             apply the template successfully
     * @throws IllegalArgumentException
     *             if templateName or startDate is null, or templateName is an
     *             empty string, or there's no template with the given name
     */
    public Project applyTemplate(String templateName, Date startDate) throws PhaseTemplateException;

    /**
     * <p>
     * Generate a set of project phases and compose a <code>Project</code> object with those phases by
     * applying the template with the given name.The start date of the project will be generated automatically
     * according to a specific generation logic.
     * </p>
     *
     * @param templateName
     *            the template name
     * @return a Project with the phases generated from the given template
     * @throws PhaseTemplateException
     *             if any error occurred during the generation so that we can not
     *             apply the template successfully
     * @throws IllegalArgumentException
     *             if templateName is null or an empty string, or there's no
     *             template with the given name
     */
    public Project applyTemplate(String templateName) throws PhaseTemplateException;

    /**
     * <p>
     * Generates a set of project phases and compose a Project object with those phases by applying the
     * template with the given name. The start date of the project is set to a default value which depends on
     * the specific implementations. This method additionally removes the specified phases when applying the
     * template.
     * </p>
     *
     * @param templateName
     *            the template name
     * @param leftOutPhaseIds
     *            the IDs of phases to be left out (null or empty if no phases should be left out)
     * @return a project with the phases generated from the given template
     * @throws IllegalArgumentException
     *             if templateName is null or an empty string, or there's no template with the given name, or
     *             if leftOutPhaseIds contains unknown/duplicate phase ID
     * @throws PhaseTemplateException
     *             if any error occurred during the generation so that we can not apply the template
     *             successfully
     * @since 1.2
     */
    public Project applyTemplate(String templateName, long[] leftOutPhaseIds) throws PhaseTemplateException;

    /**
     * <p>
     * Generates a set of project phases and compose a Project object with those phases by applying the
     * template with the given name. The start date of the project is set to the startDate. This method
     * additionally removes the specified phases when applying the template.
     * </p>
     *
     * @param templateName
     *            the template name
     * @param leftOutPhaseIds
     *            the IDs of phases to be left out (null or empty if no phases should be left out)
     * @param startDate
     *            the start date of the project
     * @return a project with the phases generated from the given template
     * @throws IllegalArgumentException
     *             if templateName or startDate is null, or templateName is an empty string, or there's no
     *             template with the given name, or if leftOutPhaseIds contains unknown/duplicate phase ID
     * @throws PhaseTemplateException
     *             if any error occurred during the generation so that we can not apply the template
     *             successfully
     * @since 1.2
     */
    public Project applyTemplate(String templateName, long[] leftOutPhaseIds, Date startDate)
        throws PhaseTemplateException;

    /**
     * Generates a set of project phases and compose a Project object with those phases by applying the
     * template with the given name. The start date of the project is set to a default value which depends on
     * the specific implementations. Start date of phase with id equal to fixedPhaseId is adjusted to
     * fixedPhaseStartDate by changing length of phase with id equal to varPhaseId. If such adjustment is
     * impossible then throw PhaseTemplateException. This method additionally removes the specified phases
     * when applying the template.
     *
     * @param templateName
     *            the name of the template to be used (can not be null or empty)
     * @param leftOutPhaseIds
     *            the IDs of phases to be left out (null or empty if no phases should be left out)
     * @param varPhaseId
     *            the ID of the phase length of which must be changed
     * @param fixedPhaseId
     *            the ID of the phase start date of which must be adjusted
     * @param fixedPhaseStartDate
     *            the desired start date of the intermediate phase (can not be null)
     * @return the project with the phases generated from the given template
     * @throws IllegalArgumentException
     *             if templateName is null or empty or there's no template with the given name, if
     *             fixedPhaseStartDate is null, or if leftOutPhaseIds contains unknown/duplicate phase ID, or
     *             leftOutPhaseIds contains varPhaseId/fixedPhaseId
     * @throws PhaseTemplateException
     *             if any error occurred during the generation so that we can not apply the template
     *             successfully (incl. fixed phase start date can not be achieved).
     * @since 1.2
     */
    public Project applyTemplate(String templateName, long[] leftOutPhaseIds, long varPhaseId, long fixedPhaseId,
        Date fixedPhaseStartDate) throws PhaseTemplateException;

    /**
     * Generates a set of project phases and compose a Project object with those phases by applying the
     * template with the given name. The start date of the project is set to the startDate. Start date of
     * phase with id equal to fixedPhaseId is adjusted to fixedPhaseStartDate by changing length of phase with
     * id equal to varPhaseId. If such adjustment is impossible then throw PhaseTemplateException. This method
     * additionally removes the specified phases when applying the template.
     *
     * @param templateName
     *            the name of the template to be used (can not be null or empty)
     * @param leftOutPhaseIds
     *            the IDs of phases to be left out (null or empty if no phases should be left out)
     * @param varPhaseId
     *            the ID of the phase length of which must be changed
     * @param fixedPhaseId
     *            the ID of the phase start date of which must be adjusted
     * @param fixedPhaseStartDate
     *            the desired start date of the intermediate phase (can not be null)
     * @param startDate
     *            the start date of the project (can not be null, must be earlier than fixedPhaseStartDate).
     * @return the project with the phases generated from the given template
     * @throws IllegalArgumentException
     *             if templateName is null or empty or there's no template with the given name, if
     *             fixedPhaseStartDate or startDate is null or startDate > fixedPhaseStartDate, or if
     *             leftOutPhaseIds contains unknown/duplicate phase ID, or leftOutPhaseIds contains
     *             varPhaseId/fixedPhaseId
     * @throws PhaseTemplateException
     *             if any error occurred during the generation so that we can not apply the template
     *             successfully (incl. fixed phase start date can not be achieved).
     * @since 1.2
     */
    public Project applyTemplate(String templateName, long[] leftOutPhaseIds, long varPhaseId, long fixedPhaseId,
        Date fixedPhaseStartDate, Date startDate) throws PhaseTemplateException;


    /**
     * <p>
     * get the workdays used
     * </p>
     *
     */
    public DefaultWorkdays getWorkdays();
}
