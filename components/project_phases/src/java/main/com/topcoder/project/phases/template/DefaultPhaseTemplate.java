/*
 * Copyright (C) 2006-2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.phases.template;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import com.topcoder.date.workdays.DefaultWorkdays;
import com.topcoder.date.workdays.Workdays;
import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.Project;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.Property;
import com.topcoder.util.config.UnknownNamespaceException;
import com.topcoder.util.objectfactory.InvalidClassSpecificationException;
import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.objectfactory.impl.ConfigManagerSpecificationFactory;
import com.topcoder.util.objectfactory.impl.IllegalReferenceException;
import com.topcoder.util.objectfactory.impl.SpecificationConfigurationException;

/**
 * <p>
 * <code>DefaultPhaseTemplate</code> is a default implementation of
 * <code>{@link PhaseTemplate}</code> interface.
 * </p>
 * <p>
 * It manages two underlying variables - persistence(of type
 * <code>{@link PhaseTemplatePersistence}</code>) which is used as the actual template storage
 * logic; and startDateGenerator(of type <code>{@link StartDateGenerator}</code>) which is used
 * as the default project start date generation logic if there's no specific start date provided
 * during the phase generation. By this, we can easily change the persistence and start date
 * generator implementations so that the client is able to swap out for different storage and start
 * date generation strategies without code changes - all we need to do is adding new
 * <code>{@link PhaseTemplatePersistence}</code> and/or <code>{@link StartDateGenerator}</code>
 * implementations.
 * </p>
 * <p>
 * <code>DefaultPhaseTemplate</code> can be created from configuration, please consult the
 * component specification and sample configuration file(docs/Default_Phase_Template.xml) for
 * configuration details. It can also be created programatically.
 * </p>
 * <p>
 * Note that the phase start dates, phase statuses will NOT be included in the template, so these
 * information will NOT be populated to the phases, they should be set by the calling application.
 * </p>
 *
 * <p>
 * <strong>Thread-safety:</strong> There are mutable inner variables in this class, but the access to
 * these variables are properly synchronized, so this class is thread-safe.
 * </p>
 *
 * <p>
 * Change for version 1.1: In the version 1.1 new methods were added. They allow the user to retrieve
 * additional information about each template: category, description and creation date, retrieve all template
 * names and default one for each category. Also methods for applying templates with intermediate phase start
 * time specified were added.
 * </p>
 * <p>
 * Change for version 1.2: Added applyTemplate() overloads that allow to left out some phases from the
 * template being applied. Fixed type of varPhaseId and fixedPhaseId parameters.
 * </p>
 * <p>
 * Usage: Please see CS usage notes
 * </p>
 *
 * @author albertwang, TCSDEVELOPER
 * @author flying2hk, TCSDEVELOPER
 * @author saarixx, TCSDEVELOPER
 * @version 1.2
 * @since 1.0
 */
public class DefaultPhaseTemplate implements PhaseTemplate {
    /**
     * <p>
     * Represents the key of property "persistence".
     * </p>
     */
    private static final String KEY_PERSISTENCE = "persistence";

    /**
     * <p>
     * Represents the key of property "start_date_generator".
     * </p>
     */
    private static final String KEY_START_DATE_GENERATOR = "start_date_generator";

    /**
     * <p>
     * Represents the key of sub-property "class".
     * </p>
     */
    private static final String KEY_CLASS = "class";

    /**
     * <p>
     * Represents the key of sub-property "namespace".
     * </p>
     */
    private static final String KEY_NAMESPACE = "namespace";

    /**
     * <p>
     * Represents the key of property "workdays".
     * </p>
     */
    private static final String KEY_WORKDAYS = "workdays";

    /**
     * <p>
     * Represents the key of sub-property "object_specification_namespace".
     * </p>
     */
    private static final String KEY_OBJECT_SPEC_NAMESPACE = "object_specification_namespace";

    /**
     * <p>
     * Represents the key of sub-property "object_key".
     * </p>
     */
    private static final String KEY_OBJECT_KEY = "object_key";

    /**
     * <p>
     * Represents the key of sub-property "object_identifier".
     * </p>
     */
    private static final String KEY_OBJECT_IDENTIFIER = "object_identifier";

    /**
     * <p>
     * Represents the number of milliseconds in a minute.
     * </p>
     */
    private static final int MILLISECONDS_A_MINUTE = 60000;

    /**
     * <p>
     * Represents the number of minutes in a week.
     * </p>
     */
    private static final int MINUTES_A_WEEK = 7 * 24 * 60;

    /**
     * <p>
     * Represents the underlying template persistence from which the project phases will be
     * generated.
     * </p>
     * <p>
     * It is initialized in the constructor, and can be modified by the setter.It can never be null.
     * </p>
     */
    private PhaseTemplatePersistence persistence;

    /**
     * <p>
     * Represents the generator to generate the project start date.
     * </p>
     * <p>
     * It is initialized in the constructor, and can be modified by the setter.It can never be null.
     * </p>
     */
    private StartDateGenerator startDateGenerator;

    /**
     * <p>
     * Represents the Workdays object which the generated Project will be based on.
     * </p>
     * <p>
     * It is initialized in the constructor, and can be modified by the setter.It can never be null.
     * </p>
     */
    private DefaultWorkdays workdays;

    /**
     * <p>
     * Create a <code>DefaultPhaseTemplate</code> from the given configuration namespace.
     * </p>
     *
     * @param namespace the configuration namespace
     * @throws IllegalArgumentException if the namespace is null or empty string
     * @throws ConfigurationException if the namespace is unknown or there's any errors in the
     *         configuration namespace
     */
    public DefaultPhaseTemplate(String namespace) throws ConfigurationException {
        if (namespace == null) {
            throw new IllegalArgumentException("namespace can not be null!");
        }
        if (namespace.trim().length() == 0) {
            throw new IllegalArgumentException("namespace can not be empty string!");
        }

        try {
            ConfigManager cm = ConfigManager.getInstance();
            // retrieve "persistence" property
            Property property = cm.getPropertyObject(namespace, KEY_PERSISTENCE);
            if (property == null) {
                throw new ConfigurationException("Property " + KEY_PERSISTENCE + " is required!");
            }
            // create persistence from the property
            this.persistence = (PhaseTemplatePersistence) instantiateObjectFromProperty(property);

            // retrieve "start_date_generator" property
            property = cm.getPropertyObject(namespace, KEY_START_DATE_GENERATOR);
            if (property == null) {
                throw new ConfigurationException("Property " + KEY_START_DATE_GENERATOR + " is required!");
            }
            // create startDateGenerator from the property
            this.startDateGenerator = (StartDateGenerator) instantiateObjectFromProperty(property);

            // retrieve "workdays" property
            property = cm.getPropertyObject(namespace, KEY_WORKDAYS);
            if (property == null) {
                throw new ConfigurationException("Property " + KEY_WORKDAYS + " is required!");
            }
            // retrieve "object_specification_namespace", "object_key" and "object_identifier"
            String objectSpecNamespace = property.getValue(KEY_OBJECT_SPEC_NAMESPACE);
            if (objectSpecNamespace == null) {
                throw new ConfigurationException("Property " + KEY_OBJECT_SPEC_NAMESPACE + " is required!");
            }
            String objectKey = property.getValue(KEY_OBJECT_KEY);
            if (objectKey == null) {
                throw new ConfigurationException("Property " + KEY_OBJECT_KEY + " is required!");
            }
            String objectIdentifier = property.getValue(KEY_OBJECT_IDENTIFIER);
            // create Object Factory instance
            ObjectFactory objectFactory = new ObjectFactory(new ConfigManagerSpecificationFactory(objectSpecNamespace));
            // create workdays from Object Factory
            if (objectIdentifier == null) {
                this.workdays = (DefaultWorkdays) objectFactory.createObject(objectKey);
            } else {
                this.workdays = (DefaultWorkdays) objectFactory.createObject(objectKey, objectIdentifier);
            }
        } catch (InvalidClassSpecificationException e) {
            throw new ConfigurationException("Object factory error occured while initializing the workdays.", e);
        } catch (SpecificationConfigurationException e) {
            throw new ConfigurationException("Object factory error occured while initializing the workdays.", e);
        } catch (IllegalReferenceException e) {
            throw new ConfigurationException("Object factory error occured while initializing the workdays.", e);
        } catch (UnknownNamespaceException e) {
            throw new ConfigurationException("The given namespace is unknown.", e);
        } catch (NoSuchMethodException e) {
            throw new ConfigurationException("Reflection error occured while loading the configuration.", e);
        } catch (ClassNotFoundException e) {
            throw new ConfigurationException("Reflection error occured while loading the configuration.", e);
        } catch (InstantiationException e) {
            throw new ConfigurationException("Reflection error occured while loading the configuration." + e, e);
        } catch (IllegalAccessException e) {
            throw new ConfigurationException("Reflection error occured while loading the configuration.", e);
        } catch (InvocationTargetException e) {
            throw new ConfigurationException("Reflection error occured while loading the configuration.", e);
        } catch (ConfigurationException e) {
            throw e;
        }
    }

    /**
     * <p>
     * Create a <code>DefaultPhaseTemplate</code> with the given persistence , startDateGenerator
     * and workdays.
     * </p>
     *
     * @param persistence the PhaseTemplatePersistence instance to store the templates
     * @param startDateGenerator the StartDateGenerator instance to generate the project start date
     * @param workdays the Workdays instance used to create the project
     * @throws IllegalArgumentException if persistence or startDateGenerator or workdays is null
     */
    public DefaultPhaseTemplate(PhaseTemplatePersistence persistence, StartDateGenerator startDateGenerator,
        DefaultWorkdays workdays) {
        this.setPersistence(persistence);
        this.setStartDateGenerator(startDateGenerator);
        this.setWorkdays(workdays);
    }

    /**
     * <p>
     * Return the names of all the templates available to use.
     * </p>
     *
     * @return the names of all the templates available to use
     */
    public String[] getAllTemplateNames() {
        synchronized (this.persistence) {
            return this.persistence.getAllTemplateNames();
        }
    }

    /**
     * <p>
     * Generate a set of project phases and compose a <code>Project</code> object with those phases by
     * applying the template with the given name.The start date of the project will be set to the startDate
     * provided by the caller.
     * </p>
     * <p>
     * Change in 1.2: Passing null as leftOutPhaseIds parameter to persistence.generatePhases().
     * </p>
     *
     * @param templateName the template name
     * @param startDate the start date of the project
     * @return a Project with the phases generated from the given template
     * @throws IllegalArgumentException
     *             if templateName or startDate is null, or templateName is an
     *             empty string, or there's no template with the given name
     * @throws PhaseTemplateException
     *             if any error occurred during the generation so that we can not
     *             apply the template successfully
     */
    public Project applyTemplate(String templateName, Date startDate) throws PhaseTemplateException {
        if (startDate == null) {
            throw new IllegalArgumentException("startDate can not be null!");
        }
        // create a new Project object
        Project project = new Project(startDate, this.workdays);
        // access the persistence to generate the phases
        synchronized (this.persistence) {
            // Since 1.2 start
            this.persistence.generatePhases(templateName, project, null);
            // Since 1.2 end
        }
        // return the project
        return project;
    }

    /**
     * <p>
     * Generate a set of project phases and compose a <code>Project</code> object with those phases by
     * applying the template with the given name.The start date of the project will be generated automatically
     * by the underlying startDateGenerator.
     * </p>
     * <p>
     * Change in 1.2: Passing null as leftOutPhaseIds parameter to persistence.generatePhases().
     * </p>
     *
     * @param templateName the template name
     * @return a Project with the phases generated from the given template
     * @throws IllegalArgumentException
     *             if templateName is null or an empty string, or there's no
     *             template with the given name
     * @throws PhaseTemplateException
     *             if any error occurred during the generation so that we can not
     *             apply the template successfully
     */
    public Project applyTemplate(String templateName) throws PhaseTemplateException {
        // generate the default start date using startDateGenerator
        Date startDate = null;
        synchronized (this.startDateGenerator) {
            startDate = this.startDateGenerator.generateStartDate();
        }
        // delegate to applyTemplate(String templateName, Date startDate)
        return this.applyTemplate(templateName, startDate);
    }

    /**
     * <p>
     * Return the persistence layer of this class.
     * </p>
     *
     * @return the PhaseTemplatePersistence of this PhaseTemplate
     */
    public PhaseTemplatePersistence getPersistence() {
        return this.persistence;
    }

    /**
     * <p>
     * Set the <code>{@link PhaseTemplatePersistence}</code> of this <code>PhaseTemplate</code>.
     * </p>
     *
     * @param persistence
     *            the PhaseTemplatePersistence instance
     * @throws IllegalArgumentException
     *             if the argument is null
     */
    public void setPersistence(PhaseTemplatePersistence persistence) {
        if (persistence == null) {
            throw new IllegalArgumentException("persistence can not be null!");
        }
        synchronized (this) {
            this.persistence = persistence;
        }
    }

    /**
     * <p>
     * Return the StartDateGenerator used to generate the start date in this class.
     * </p>
     *
     * @return the StartDateGenerator used to generate the start date
     */
    public StartDateGenerator getStartDateGenerator() {
        return this.startDateGenerator;
    }

    /**
     * <p>
     * Set the <code>{@link StartDateGenerator}</code> of this <code>PhaseTemplate</code>.
     * </p>
     *
     * @param startDateGenerator
     *            the StartDateGenerator instance
     * @throws IllegalArgumentException
     *             if the argument is null
     */
    public void setStartDateGenerator(StartDateGenerator startDateGenerator) {
        if (startDateGenerator == null) {
            throw new IllegalArgumentException("startDateGenerator can not be null!");
        }
        synchronized (this) {
            this.startDateGenerator = startDateGenerator;
        }
    }

    /**
     * <p>
     * Return the Workdays used to generate the Project in this class.
     * </p>
     *
     * @return the workdays variable
     */
    public DefaultWorkdays getWorkdays() {
        return this.workdays;
    }

    /**
     * <p>
     * Set the <code>Workdays</code> used to create projects.
     * </p>
     *
     * @param workdays
     *            the Workdays object
     * @throws IllegalArgumentException
     *             if workdays is null
     */
    public void setWorkdays(DefaultWorkdays workdays) {
        if (workdays == null) {
            throw new IllegalArgumentException("workdays can not be null!");
        }
        synchronized (this) {
            this.workdays = workdays;
        }
    }

    /**
     * <p>
     * Returns the list of names of templates those belong to the specified category. Never returns null.
     * </p>
     *
     * @param category
     *            the category of templates
     * @return the list of names of templates those belong to the specified
     *         category
     * @throws PersistenceException
     *             if any error occurs in the persistence
     * @since 1.1
     */
    public String[] getAllTemplateNames(int category) throws PersistenceException {
        synchronized (this) {
            return persistence.getAllTemplateNames(category);
        }
    }

    /**
     * <p>
     * Returns the category of template with the given name.
     * </p>
     *
     * @param templateName
     *            the name of template, should not be null or empty
     * @return category of the template
     * @throws PersistenceException
     *             if any error occurs in the persistence
     * @throws IllegalArgumentException
     *             if templateName is null or empty, or the template with the
     *             given name could not be found.
     * @since 1.1
     */
    public int getTemplateCategory(String templateName) throws PersistenceException {

        synchronized (this) {
            return persistence.getTemplateCategory(templateName);
        }
    }

    /**
     * <p>
     * Returns the name of template that is default for the specified category. It would return null if
     * default template is not specified for the given category.
     * </p>
     *
     * @param category
     *            the category of templates
     * @return the name of the default template for the given category, could be
     *         null
     * @throws PersistenceException
     *             if any error occurs in the persistence
     * @since 1.1
     */
    public String getDefaultTemplateName(int category) throws PersistenceException {

        synchronized (this) {
            return persistence.getDefaultTemplateName(category);
        }
    }

    /**
     * <p>
     * Returns the description of template with the given name. Can return null if description was not
     * specified.
     * </p>
     *
     * @param templateName
     *            the name of template, should not be null or empty
     * @return the description of the template with the given name, could be
     *         null
     * @throws PersistenceException
     *             if any error occurs in the persistence
     * @throws IllegalArgumentException
     *             if templateName is null or empty, or the template with the
     *             given name could not be found.
     * @since 1.1
     */
    public String getTemplateDescription(String templateName) throws PersistenceException {

        synchronized (this) {
            return persistence.getTemplateDescription(templateName);
        }
    }

    /**
     * <p>
     * Returns the creation date of template with the given name. Can return null if creation date was not
     * specified.
     * </p>
     *
     * @param templateName
     *            the name of template, should not be null or empty
     * @return the creation date of the template with the given name, could be
     *         null
     * @throws PersistenceException
     *             if any error occurs in the persistence
     * @throws IllegalArgumentException
     *             if templateName is null or empty, or the template with the
     *             given name could not be found.
     * @since 1.1
     */
    public Date getTemplateCreationDate(String templateName) throws PersistenceException {

        synchronized (this) {
            return persistence.getTemplateCreationDate(templateName);
        }
    }

    /**
     * <p>
     * Generates a set of project phases and composes a Project object with those phases by applying the
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
     *            the name of the template to be used, should not be null or
     *            empty
     * @param varPhaseId
     *            the id of the phase, length of which must be changed
     * @param fixedPhaseId
     *            the id of the phase, start date of which must be adjusted
     * @param fixedPhaseStartDate
     *            the desired start date of the intermediate phase, should not
     *            be null
     * @param startDate
     *            the start date of the project, should can not be null, should
     *            not be later than fixedPhaseStartDate
     * @return a Project with the phases generated from the given template
     * @throws IllegalArgumentException
     *             if templateName is null or empty or there's no template with
     *             the given name, if fixedPhaseStartDate or startDate is null
     *             or startDate > fixedPhaseStartDate, or if phases with the
     *             given ids can not be found
     * @throws PhaseTemplateException
     *             if any error occurred during the generation so that we can not
     *             apply the template successfully (incl. fixed phase start date
     *             can not be achieved)
     * @throws PersistenceException
     *             if error occurs while accessing the persistence layer
     * @since 1.1
     */
    public Project applyTemplate(String templateName, long varPhaseId, long fixedPhaseId,
        Date fixedPhaseStartDate, Date startDate) throws PhaseTemplateException {

        // check the argument fixedPhaseStartDate and startDate
        Util.checkNull(startDate, "startDate");
        Util.checkNull(fixedPhaseStartDate, "fixedPhaseStartDate");
        if (startDate.after(fixedPhaseStartDate)) {
            throw new IllegalArgumentException("startDate should not >(after) fixedPhaseStartDate");
        }

        // apply the given template to construct a project
        Project project = applyTemplate(templateName, startDate);
        // adjust the phases
        adjustPhases(project, varPhaseId, fixedPhaseId, fixedPhaseStartDate);
        return project;
    }

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
     *            the name of the template to be used, should not be null or
     *            empty
     * @param varPhaseId
     *            the id of the phase, length of which must be changed
     * @param fixedPhaseId
     *            the id of the phase, start date of which must be adjusted
     * @param fixedPhaseStartDate
     *            the desired start date of the intermediate phase, should not
     *            be null
     * @return a Project with the phases generated from the given template
     * @throws IllegalArgumentException
     *             if templateName is null or empty or there's no template with
     *             the given name, if fixedPhaseStartDate is null or if phases
     *             with the given ids can not be found
     * @throws PhaseTemplateException
     *             if any error occurred during the generation so that we can not
     *             apply the template successfully (incl. fixed phase start date
     *             can not be achieved)
     * @throws PersistenceException
     *             if error occurs while accessing the persistence layer
     * @since 1.1
     */
    public Project applyTemplate(String templateName, long varPhaseId, long fixedPhaseId,
        Date fixedPhaseStartDate) throws PhaseTemplateException {

        // apply the given template to construct a project
        Project project = applyTemplate(templateName);
        // adjust the phases
        adjustPhases(project, varPhaseId, fixedPhaseId, fixedPhaseStartDate);
        return project;
    }

    /**
     * <p>
     * Adjusts project phases so that the phase with id equal to fixedPhaseId must start at
     * fixedPhaseStartDate. Adjustment is made by changing the length of the phase with id equal to
     * varPhaseId.
     * </p>
     * <p>
     * Change in 1.2: Type of varPhaseId and fixedPhaseId parameters was changed from "int" to "long".
     * </p>
     *
     * @param project
     *            the project phases of which must be adjusted, should not be
     *            null
     * @param varPhaseId
     *            the id of the phase length of which must be changed
     * @param fixedPhaseId
     *            the id of the phase start date of which must be adjusted
     * @param fixedPhaseStartDate
     *            the desired start date of the intermediate phase, should not
     *            be null
     * @throws IllegalArgumentException
     *             if project or fixedPhaseStartDate is null or if phases with
     *             the given ids can not be found
     * @throws PhaseGenerationException
     *             if fixed phase start date can not be achieved
     * @since 1.1
     */
    private void adjustPhases(Project project, long varPhaseId, long fixedPhaseId, Date fixedPhaseStartDate)
        throws PhaseGenerationException {

        Phase fixedPhase = null, varPhase = null;

        Util.checkNull(fixedPhaseStartDate, "fixedPhaseStartDate");

        // get all the phases from project, and found the fixedPhase and
        // varPhase
        Phase[] phases = project.getAllPhases();
        for (int i = 0; i < phases.length; ++i) {
            if (phases[i].getId() == varPhaseId) {
                varPhase = phases[i];
            }
            if (phases[i].getId() == fixedPhaseId) {
                fixedPhase = phases[i];
            }
        }

        // if the phase with the given id can not be found, throw
        // IllegalArgumentException
        if (fixedPhase == null) {
            throw new IllegalArgumentException("The phase with id " + fixedPhaseId + " can not be found.");
        }
        if (varPhase == null) {
            throw new IllegalArgumentException("The phase with id " + varPhaseId + " can not be found.");
        }

       // Get difference between current fixed phase start date and desired one
        // (in minutes)
        long diff = (fixedPhaseStartDate.getTime() - fixedPhase.calcStartDate().getTime());
     
        project.setStartDate(new Date(project.getStartDate().getTime() + diff));

        fixedPhase.calcStartDate();
    }


   


    /**
     * <p>
     * Instantiate an object from a <code>Property</code> object which contains the class name and
     * configuration namespace. If namespace is not null, it will create the object by invoking constructor
     * with namespace argument. If namespace is null or error occurs while invoking constructor with namespace
     * argument, it will try to create the object by invoking default constructor without argument.
     * </p>
     * <p>
     * It will be used in the constructor with configuration namespace to initialize
     * <code>startDateGenerator</code> and <code>persistence</code>
     * </p>
     *
     * @param property
     *            the property from which the object will be created
     * @return the created object
     * @throws SecurityException
     *             thrown from relection API
     * @throws NoSuchMethodException
     *             thrown from relection API
     * @throws ClassNotFoundException
     *             thrown from relection API
     * @throws IllegalArgumentException
     *             thrown from relection API
     * @throws InstantiationException
     *             thrown from relection API
     * @throws IllegalAccessException
     *             thrown from relection API
     * @throws InvocationTargetException
     *             thrown from relection API
     * @throws ConfigurationException
     *             if "namespace" or "class" property is missing
     */
    private static Object instantiateObjectFromProperty(Property property) throws NoSuchMethodException,
        ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException,
        ConfigurationException {

        String className = property.getValue(KEY_CLASS);
        if (className == null) {
            throw new ConfigurationException("Property " + KEY_CLASS + " is required!");
        }
        String namespace = property.getValue(KEY_NAMESPACE);
        if (namespace != null) {   
            try {
                // namespace is present, use ctor with string argument
                Constructor ctor = Class.forName(className).getConstructor(new Class[]{String.class});
                return ctor.newInstance(new Object[]{namespace});
            } catch (Exception ex) {   
                // error occurs, use ctor with no argument
                return Class.forName(className).newInstance();
            }
        } else {
            // namespace is not present, use ctor with no argument
            return Class.forName(className).newInstance();
        }
    }

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
     * @throws PersistenceException
     *             if error occurs while accessing the persistence layer
     * @throws StartDateGenerationException
     *             if any error occurs so that the start date can not be generated
     * @throws PhaseTemplateException
     *             if any error occurred during the generation so that we can not apply the template
     *             successfully
     * @since 1.2
     */
    public Project applyTemplate(String templateName, long[] leftOutPhaseIds) throws PhaseTemplateException {

        // generate the default start date using startDateGenerator
        Date startDate = null;
        synchronized (this.startDateGenerator) {
            startDate = this.startDateGenerator.generateStartDate();
        }
        // delegate to applyTemplate(String templateName, Date startDate)
        return this.applyTemplate(templateName, leftOutPhaseIds, startDate);
    }

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
     * @throws PersistenceException
     *             if error occurs while accessing the persistence layer
     * @throws StartDateGenerationException
     *             if any error occurs so that the start date can not be generated
     * @throws PhaseTemplateException
     *             if any error occurred during the generation so that we can not apply the template
     *             successfully
     * @since 1.2
     */
    public Project applyTemplate(String templateName, long[] leftOutPhaseIds, Date startDate)
        throws PhaseTemplateException {

        if (startDate == null) {
            throw new IllegalArgumentException("startDate can not be null!");
        }
        // create a new Project object
        Project project = new Project(startDate, (DefaultWorkdays) this.workdays);
        // access the persistence to generate the phases
        synchronized (this.persistence) {
            this.persistence.generatePhases(templateName, project, leftOutPhaseIds);
        }
        // return the project
        return project;
    }

    /**
     * Generates a set of project phases and compose a Project object with those phases by applying the
     * template with the given name. The start date of the project is set to a default value which depends on
     * the specific implementations. Start date of phase with id equal to fixedPhaseId is adjusted to
     * fixedPhaseStartDate by changing length of phase with id equal to varPhaseId. If such adjustment is
     * impossible then throw PhaseTemplateException.
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
     * @throws PersistenceException
     *             if error occurs while accessing the persistence layer
     * @throws StartDateGenerationException
     *             if any error occurs so that the start date can not be generated
     * @throws PhaseTemplateException
     *             if any error occurred during the generation so that we can not apply the template
     *             successfully (incl. fixed phase start date can not be achieved).
     * @since 1.2
     */
    public Project applyTemplate(String templateName, long[] leftOutPhaseIds, long varPhaseId,
        long fixedPhaseId, Date fixedPhaseStartDate) throws PhaseTemplateException {

        // generate the default start date using startDateGenerator
        Date startDate = null;
        synchronized (this.startDateGenerator) {
            startDate = this.startDateGenerator.generateStartDate();
        }

        return applyTemplate(templateName, leftOutPhaseIds, varPhaseId, fixedPhaseId, fixedPhaseStartDate,
            startDate);
    }

    /**
     * Generates a set of project phases and composes a Project object with those phases by applying the
     * template with the given name. The start date of the project is set to the startDate. Start date of
     * phase with id equal to fixedPhaseId is adjusted to fixedPhaseStartDate by changing length of phase with
     * id equal to varPhaseId. If such adjustment is impossible then throw PhaseTemplateException.
     * Note that the implementation should lock on this to ensure the thread safety.
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
     *            the start date of the project (can not be null, must be earlier than fixedPhaseStartDate)
     * @return the project with the phases generated from the given template
     * @throws IllegalArgumentException
     *             if templateName is null or empty or there's no template with the given name, if
     *             fixedPhaseStartDate or startDate is null or startDate > fixedPhaseStartDate, if phases with
     *             the given IDs can not be found, or if leftOutPhaseIds contains unknown/duplicate phase ID,
     *             or leftOutPhaseIds contains varPhaseId/fixedPhaseId
     * @throws PersistenceException
     *             if error occurs while accessing the persistence layer
     * @throws StartDateGenerationException
     *             if any error occurs so that the start date can not be generated
     * @throws PhaseTemplateException
     *             if any error occurred during the generation so that we can not apply the template
     *             successfully (incl. fixed phase start date can not be achieved).
     * @since 1.2
     */
    public Project applyTemplate(String templateName, long[] leftOutPhaseIds, long varPhaseId,
        long fixedPhaseId, Date fixedPhaseStartDate, Date startDate) throws PhaseTemplateException {

        // check the argument fixedPhaseStartDate and startDate
        Util.checkNull(startDate, "startDate");
        Util.checkNull(fixedPhaseStartDate, "fixedPhaseStartDate");
        if (startDate.after(fixedPhaseStartDate)) {
            throw new IllegalArgumentException("startDate should not >(after) fixedPhaseStartDate");
        }
        if (leftOutPhaseIds != null) {
            for (int i = 0; i < leftOutPhaseIds.length; i++) {
                checkId(leftOutPhaseIds[i], varPhaseId, "varPhaseId");
                checkId(leftOutPhaseIds[i], fixedPhaseId, "fixedPhaseId");
            }
        }
        // delegate to applyTemplate(String templateName, Date startDate)
        Project project = applyTemplate(templateName, leftOutPhaseIds, startDate);

        adjustPhases(project, varPhaseId, fixedPhaseId, fixedPhaseStartDate);
        return project;
    }

    /**
     * Check leftOutPhaseIds array with varPhaseId/fixedPhaseId.
     *
     * @param id
     *            the id
     * @param checkedId
     *            the checked id
     * @param name
     *            the name
     * @throws IllegalArgumentException
     *             if leftOutPhaseIds contains varPhaseId/fixedPhaseId
     * @since 1.2
     */
    private void checkId(long id, long checkedId, String name) {

        if (id == checkedId) {
            throw new IllegalArgumentException("The leftOutPhaseIds array must not contain " + name + "("
                + checkedId + ").");
        }
    }
}
