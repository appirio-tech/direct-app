/*
 * Copyright (C) 2006-2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.phases.template.persistence;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import com.topcoder.project.phases.Dependency;
import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.PhaseType;
import com.topcoder.project.phases.Project;
import com.topcoder.project.phases.template.ConfigurationException;
import com.topcoder.project.phases.template.PersistenceException;
import com.topcoder.project.phases.template.PhaseGenerationException;
import com.topcoder.project.phases.template.PhaseTemplatePersistence;
import com.topcoder.project.phases.template.Util;
import com.topcoder.util.config.ConfigManager;

/**
 * <p>
 * <code>XmlPhaseTemplatePersistence</code> is an XML based persistence implementation of
 * <code>{@link PhaseTemplatePersistence}</code>.
 * </p>
 * <p>
 * Phase templates are stored in XML files, each XML file defines one template, the XML schema is
 * defined in docs/xml_phase_templte.xsd, and also a sample template is provided in docs directory.
 * At the instantiation time, the template XML files will be validated against the schema, the
 * schema file should be named as "xml_phase_template.xsd" and placed in the root path of
 * CLASSPATH(the first choice is to include it in root path of the package JAR), or placed in
 * current file path.
 * </p>
 * <p>
 * Each template is assigned a template name. Inside the <code>XmlPhaseTemplatePersistence</code>,
 * templates are stored in a <code>Map</code> with the template name as the key, and with
 * <code>org.w3c.dom.Document</code> objects parsed from the XML document as the value.
 * </p>
 * <p>
 * The persistence can be created from configurations with the configured XML template file paths,
 * instance can also be created programatically. Please consult the component specification and
 * sample configuration file (docs/Xml_Phase_Template_Persistence.xml) for configuration details.
 * </p>
 * <p>
 * Note that the phase start dates, phase statuses will NOT be included in the template, so these
 * information will NOT be populated to the phases, they should be set by the calling application.
 * </p>
 *
 * <p>
 * Change for version 1.1: In the version 1.1 new methods were added. They allow to retrieve
 * additional information about each template: category, description and creation date, retrive all
 * template names and default one for each category. generatePhases(String, Project) method was
 * modified so that it now also reads phaseId attribute.
 * </p>
 * <p>
 * Change for version 1.2: Updated generatePhases() method to support skipping specified phases from the
 * template.
 * </p>
 * <p>
 * This class is thread safe, as it is immutable and the templates are readonly.
 * </p>
 *
 * @author albertwang, TCSDEVELOPER
 * @author flying2hk, TCSDEVELOPER
 * @author saarixx, TCSDEVELOPER
 * @version 1.2
 * @since 1.0
 */
public class XmlPhaseTemplatePersistence implements PhaseTemplatePersistence {
    /**
     * <p>
     * Represents the property key of "template_files".
     * </p>
     */
    private static final String KEY_TEMPLATE_FILES = "template_files";

    /**
     * <p>
     * XML Schema file(XSD schema) for the validation of persistence file. This implementation will
     * try to find the schema file in the classpath, if it is not found in classpath, then try to
     * find in the relative file path.
     * </p>
     * <p>
     * The schema file should be placed in the root path of CLASSPATH(the first choice is to include
     * it in root path of the package JAR), or placed in current file path.
     * </p>
     */
    private static final String SCHEMA_FILE = "xml_phase_template.xsd";

    /**
     * <p>
     * Represent an instance of DateFormat which is used to parse the date.
     * </p>
     */
    private static final DateFormat DATE_FORMATE = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * <p>
     * Represents the mapping from template names to the templates.
     * </p>
     * <p>
     * The keys are strings, the values are <code>org.w3c.dom.Document</code> objects parsed from the XML
     * based template files.
     * </p>
     * <p>
     * It is initialized in place, and can not be modified afterwards, it can never be null, can be
     * empty Map if there's no template defined.
     * </p>
     */
    private final Map<String, Document> templates = new HashMap<String, Document>();

    /**
     * <p>
     * Create an <code>XmlPhaseTemplatePersistence</code> from the given configuration namespace.
     * </p>
     * <p>
     * The templates will be loaded from the configured persistence files, note that the persistence
     * XML files will be validated against the XSD schema.
     * </p>
     *
     * @param namespace
     *            the configuration namespace
     * @throws IllegalArgumentException
     *             if the namespace is null or empty string
     * @throws ConfigurationException
     *             if any error occurs(e.g. the required configuration properties
     *             is missing, the configured xml file is malformed, etc) so that the persistence can
     *             not be initialized successfully
     */
    public XmlPhaseTemplatePersistence(String namespace) throws ConfigurationException {
        if (namespace == null) {
            throw new IllegalArgumentException("namespace can not be null!");
        }
        if (namespace.trim().length() == 0) {
            throw new IllegalArgumentException("namespace can not be empty string!");
        }

        try {
            // retrieve template_files from configuration
            ConfigManager cm = ConfigManager.getInstance();
            String[] templateFiles = cm.getStringArray(namespace, KEY_TEMPLATE_FILES);  

            // initialize the templates
            this.initialzeTemplates(templateFiles);
        } catch (PersistenceException e) {
            throw new ConfigurationException("Persistence error occured while initializing the persistence.", e);
        } catch (ParserConfigurationException e) {
            throw new ConfigurationException("Error occured while parsing the XML persistence file.", e);
        } catch (SAXException e) {
            throw new ConfigurationException("Error occured while parsing the XML persistence file.", e);
        } catch (IOException e) {
            throw new ConfigurationException("Error occured while accessing the XML persistence file.", e);
        } catch (IllegalArgumentException e) {
            throw new ConfigurationException("Error occured while initializing the persistence.", e);
        }
    }

    /**
     * <p>
     * Create an XmlPhaseTemplatePersistence with the given xml template file names.
     * </p>
     * <p>
     * The templates will be loaded from the given persistence files, note that the persistence XML files will
     * be validated against the XSD schema.
     * </p>
     *
     * @param fileNames
     *            the XML phase template file names
     * @throws IllegalArgumentException
     *             if the fileNames is null, or any of the items is null or
     *             empty string, or schema validation can not be performed
     * @throws PersistenceException
     *             if any error occurs(e.g. any of the file is malformed XML
     *             document) while parsing the files
     */
    public XmlPhaseTemplatePersistence(String[] fileNames) throws PersistenceException {
        try {
            // init templates
            this.initialzeTemplates(fileNames);
        } catch (IllegalArgumentException iae) {
            throw iae;
        } catch (Exception ex) {
            // any exception is thrown, wrap to PersistenceException
            throw new PersistenceException("Error occurs while accessing the persistence.", ex);
        }
    }

    /**
     * <p>
     * Generates an array of <code>Phase</code>s from the template with the given name and add them into the
     * given <code>Project</code> object.The dependency hierarchy will be populated too.
     * </p>
     * <p>
     * Change for version 1.1: It also reads a "phaseId" attribute and sets its value to a newly created
     * <code>Phase</code> object if this attribute is present.
     * </p>
     * <p>
     * Change in 1.2: Added leftOutPhaseIds parameter. Added a support for skipping phases to be left out.
     * </p>
     *
     * @param templateName
     *            the template name
     * @param project
     *            the project which the phases will be added to
     * @param leftOutPhaseIds
     *            the IDs of phases to be left out (null or empty if no phases should be left out)
     * @throws IllegalArgumentException
     *             if the templateName is null or empty string, or the project
     *             is null, or the there's no template with the given templateName
     * @throws PhaseGenerationException
     *             if any error occurred in the phase generation(e.g. cyclic
     *             dependency, etc.) so that the generation process can not complete successfully
     * @throws PersistenceException
     *             if error occurs while accessing the persistence layer
     */
    public void generatePhases(String templateName, Project project, long[] leftOutPhaseIds)
        throws PhaseGenerationException, PersistenceException {

        if (templateName == null) {
            throw new IllegalArgumentException("templateName can not be null!");
        }
        if (project == null) {
            throw new IllegalArgumentException("project can not be null!");
        }
        if (templateName.trim().length() == 0) {
            throw new IllegalArgumentException("templateName can not be empty string!");
        }
        // verify if the template exsits
        if (!this.templates.containsKey(templateName)) {
            throw new IllegalArgumentException("There's no such template with name " + templateName);
        }
        // Since 1.2 start
        // check array duplicate value.
        Set<Long> leftOutPhaseIdsSet = Util.checkArrayDuplicateValue(leftOutPhaseIds);

        Set<String> leftOutPhaseNames = new HashSet<String>();
        Map<Long, List<Dependency>> dependenciesToLeftOutPhases = new HashMap<Long, List<Dependency>>();
        // except dependency information will be populated
        // Map phases = new HashMap();
        Map<String, Phase> phases = new HashMap<String, Phase>();
        // Since 1.2 end
        NodeList list = null;
        try {
            // retrieve the DOM Document representing the template
            Document doc = (Document) this.templates.get(templateName);

            // generating phase types
            Map<String, PhaseType> phaseTypes = new HashMap<String, PhaseType>();
            list = doc.getDocumentElement().getElementsByTagName(Util.TAG_PHASE_TYPE);
            for (int i = 0; i < list.getLength(); i++) {
                Element typeElement = (Element) list.item(i);
                // retrieve typeId and typeName, create the PhaseType with them
                long typeId = Long.parseLong(typeElement.getAttribute(Util.ATTRIBUTE_TYPE_ID));
                String typeName = typeElement.getAttribute(Util.ATTRIBUTE_TYPE_NAME);
                PhaseType phaseType = new PhaseType(typeId, typeName);
                // cache the phaseType
                phaseTypes.put(typeElement.getAttribute(Util.ATTRIBUTE_ID), phaseType);
            }

            // 1st pass to scan the "Phase" elements, in this pass, all phase related information

            list = doc.getElementsByTagName(Util.TAG_PHASE);
            long listLength = list.getLength();
            for (int i = 0; i < listLength; i++) {
                Element phaseElement = (Element) list.item(i);
                String id = phaseElement.getAttribute(Util.ATTRIBUTE_ID);
                // retrieve length and create the Phase with project and length
                long length = Long.parseLong(phaseElement.getAttribute(Util.ATTRIBUTE_LENGTH));
                Phase phase = new Phase(project, length);

                // set the phase id if "phaseId" attribute is present
                String phaseId = phaseElement.getAttribute(Util.ATTRIBUTE_PHASEID);
                if (phaseId.trim().length() != 0) {
                    // Since 1.2 start
                    long phaseIdLong = Long.parseLong(phaseId);
                    if (leftOutPhaseIdsSet.contains(phaseIdLong)) {
                        leftOutPhaseNames.add(id);
                    }
                    // Since 1.2 end
                    phase.setId(phaseIdLong);
                }

                // retrieve the cached phase type and set it to the phase
                PhaseType type = (PhaseType) phaseTypes.get(phaseElement.getAttribute(Util.ATTRIBUTE_TYPE));
                if (type == null) {
                    throw new PhaseGenerationException("The requested phase type does not exist.");
                }
                phase.setPhaseType(type);
                // cache the phase
                phases.put(id, phase);
            }
        } catch (Exception ex) {
            // if any exception occurs, wrap it to a PhaseGenerationException
            throw new PhaseGenerationException("Error occurs while generating the phases.", ex);
        }
        // Since 1.2 start
        // check phase id is valid in leftOutPhaseIds
        // Util.checkArraySize(leftOutPhaseNames.size(), leftOutPhaseIdsSet.size());
        // Since 1.2 end
        try {
            // 2nd pass to scan the "Phase" elements, in this pass, dependency information will
            // be populated
            for (int i = 0, length = list.getLength(); i < length; i++) {
                Element phaseElement = (Element) list.item(i);
                // "Dependency" elements
                NodeList dependencyElements = phaseElement.getElementsByTagName(Util.TAG_DEPENDENCY);
                // Current Phase
                Phase phase = (Phase) phases.get(phaseElement.getAttribute(Util.ATTRIBUTE_ID));
                // for each "Dependency"
                for (int j = 0; j < dependencyElements.getLength(); j++) {
                    // retrieve the cached phase as the dependency
                    Element dependencyElement = (Element) dependencyElements.item(j);
                    String dependencyId = dependencyElement.getAttribute(Util.ATTRIBUTE_ID);
                    Phase dependencyPhase = (Phase) phases.get(dependencyId);
                    // isDependencyStart flag, optional attribute, default to false if missing.
                    boolean isDependencyStart = false;
                    // isDependentStart flag, optional attribute, default to true if missing
                    boolean isDependentStart = true;
                    // lagTime between the dependent and the dependency, optional attribute, default
                    // to 0 if missing
                    int lagTime = 0;
                    // temp variable to cache attribute value
                    String tmp = dependencyElement.getAttribute(Util.ATTRIBUTE_IS_DEPENDENCY_START);
                    // retrieve isDependencyStart
                    if (tmp.trim().length() != 0) {
                        isDependencyStart = parseBoolean(tmp);
                    }
                    // retrieve isDependentStart
                    tmp = dependencyElement.getAttribute(Util.ATTRIBUTE_IS_DEPENDENT_START);
                    if (tmp.trim().length() != 0) {
                        isDependentStart = parseBoolean(tmp);
                    }
                    // retrieve lagTime
                    tmp = dependencyElement.getAttribute(Util.ATTRIBUTE_LAG_TIME);
                    if (tmp.trim().length() != 0) {
                        lagTime = Integer.parseInt(tmp);
                    }
                    // create Dependency object with the retrieved information
                    Dependency dependency = new Dependency(dependencyPhase, phase, isDependencyStart,
                        isDependentStart, lagTime);

                    // Since 1.2 start
                    if (leftOutPhaseNames.contains(dependencyId)) {
                        long depPhaseId = dependencyPhase.getId();
                        List<Dependency> dependenciesToLeftOutPhase = dependenciesToLeftOutPhases
                            .get(depPhaseId);
                        if (dependenciesToLeftOutPhase == null) {
                            dependenciesToLeftOutPhase = new ArrayList<Dependency>();
                            dependenciesToLeftOutPhases.put(depPhaseId, dependenciesToLeftOutPhase);
                        }
                        dependenciesToLeftOutPhase.add(dependency);
                    }
                    // Since 1.2 end

                    // Since 1.2 start
                    // add the dependency to Current Phase
                    if (leftOutPhaseIdsSet.contains(phase.getId())
                        || !leftOutPhaseNames.contains(dependencyId)) {
                        phase.addDependency(dependency);
                    }
                    // Since 1.2 end
                }
            }

            // Since 1.2 start
            // process all dependencies for left out phases
            // this algorithm replaces each dependency from not left out phase to left out phase
            // with dependencies from the original not left out phase to not left out phases that
            // are dependencies of the original left out phase
            // Note that since dependency phases of left out phases can be also left out,
            // the breadth-first search approach is used in this algorithm to locate all new dependencies
            // to be used instead of the old dependency
            Util.processDependencies(leftOutPhaseIdsSet, dependenciesToLeftOutPhases);

            // add the generated phases to the project
            removeLeftOutPhase(project, leftOutPhaseNames, phases);
            // Since 1.2 end
        } catch (Exception ex) {
            // if any exception occurs, wrap it to a PhaseGenerationException
            throw new PhaseGenerationException("Error occurs while generating the phases.", ex);
        }
    }

    /**
     * Removes the left out phase from the project.
     *
     * @param project
     *            the project
     * @param leftOutPhaseNames
     *            the left out phase names
     * @param phases
     *            the phases
     * @since 1.2
     */
    private void removeLeftOutPhase(Project project, Set<String> leftOutPhaseNames, Map<String, Phase> phases) {

        for (Map.Entry<String, Phase> entry : phases.entrySet()) {
            Phase phase = entry.getValue();
            if (!leftOutPhaseNames.contains(entry.getKey())) {
                // method Project.addPhase(Phase phase) doesn't detect the cyclic dependency,
                // so here we need call method calcStartDate() to detect the cyclic dependency
                phase.calcStartDate();

            } else {
                // remove the left out phase
                for (Phase phase1 : project.getAllPhases()) {
                    if (phase1.getId() == phase.getId()) {
                        project.removePhase(phase);
                    }
                }

            }
        }
    }

    /**
     * <p>
     * Return the names of all templates defined in the persistence layer.
     * </p>
     *
     * @return the names of all templates defined in the persistence layer.
     */
    public String[] getAllTemplateNames() {

        String[] names = new String[this.templates.size()];
        this.templates.keySet().toArray(names);
        return names;
    }

    /**
     * <p>
     * Returns the list of names of templates those belong to the specified category. Never returns null.
     * </p>
     *
     * @param category
     *            the category of templates
     * @return an array of template name, could not be null
     * @throws PersistenceException
     *             if any error occurs in this method
     * @since 1.1
     */
    public String[] getAllTemplateNames(int category) throws PersistenceException {

        // the list to hold the result
        List<String> list = new ArrayList<String>();

        // iterator all the entries in the templates map
        for (Iterator it = templates.entrySet().iterator(); it.hasNext();) {
            // get the Document value
            Entry pair = (Entry) it.next();
            Document doc = (Document) pair.getValue();

            // get the "category" attribute, default is 0
            int templateCategory = getCategory(doc.getDocumentElement());

            // add the template name into list if the category equals
            if (templateCategory == category) {
                list.add((String) pair.getKey());
            }
        }
        return (String[]) list.toArray(new String[list.size()]);
    }

    /**
     * <p>
     * Returns the category of template with the given name.
     * </p>
     *
     * @param templateName
     *            the name of template, should not be null or empty
     * @return the category of the template with the given name
     * @throws IllegalArgumentException
     *             if templateName is null or empty, or a template with the
     *             given name can not be found
     * @throws PersistenceException
     *             if any error occurs in this method
     * @since 1.1
     */
    public int getTemplateCategory(String templateName) throws PersistenceException {
        // get the document and then read the attribute
        Document doc = getTemplateByName(templateName);
        return getCategory(doc.getDocumentElement());
    }

    /**
     * <p>
     * Returns the name of template that is default for the specified category. It would return null if
     * default template is not specified for the given category.
     * </p>
     *
     * @param category
     *            the category of templates
     * @return the name of the default template for the specified category
     * @throws PersistenceException
     *             if any error occurs in this method, or more than one default
     *             template is found.
     * @since 1.1
     */
    public String getDefaultTemplateName(int category) throws PersistenceException {

        // iterator all the entries in the templates map
        String defaultName = null;
        for (Iterator it = templates.entrySet().iterator(); it.hasNext();) {
            // get the Document value
            Entry pair = (Entry) it.next();
            Document doc = (Document) pair.getValue();

            // get the "category" attribute, default is 0
            Element docElement = doc.getDocumentElement();
            int templateCategory = getCategory(docElement);
            if (templateCategory != category) {
                continue;
            }

            // read the "isDefault" attribute
            boolean isDefault = false;
            String attribute = docElement.getAttribute(Util.ATTRIBUTE_ISDEFAULT);
            if (attribute.trim().length() != 0) {
                if (attribute.equals("true")) {
                    isDefault = true;
                }
                // in other cases, isDefault would be false. since the schema will
                // validate the attribute's value, so we need not check the value here
            }

            // work with the default template
            if (isDefault) {
                if (defaultName == null) {
                    defaultName = (String) pair.getKey();
                } else {
                    // more than one template is found, throw exception
                    throw new PersistenceException("More than one default template for " + category
                        + " is found.");
                }
            }
        }
        return defaultName;
    }

    /**
     * <p>
     * Returns the description of template with the given name. Can return null if description was not
     * specified.
     * </p>
     *
     * @param templateName
     *            templateName the name of template, should not be null or empty
     * @return the given template's description, could be null
     * @throws IllegalArgumentException
     *             if templateName is null or empty, or a template with the
     *             given name can not be found.
     * @since 1.1
     */
    public String getTemplateDescription(String templateName) {

        // get the template and then read the "description" attribute
        Document doc = getTemplateByName(templateName);
        String description = doc.getDocumentElement().getAttribute(Util.ATTRIBUTE_DESCRIPTION);
        if (description.trim().length() == 0) {
            return null;
        }
        return description;
    }

    /**
     * <p>
     * Returns the creation date of template with the given name. Can return null if creation date was not
     * specified.
     * </p>
     *
     * @param templateName
     *            templateName the name of template, should not be null or empty
     * @return the given template's date, could be null
     * @throws IllegalArgumentException
     *             if templateName is null or empty, or a template with the
     *             given name can not be found.
     * @throws PersistenceException
     *             if any error occur when get the creation date
     * @since 1.1
     */
    public Date getTemplateCreationDate(String templateName) throws PersistenceException {
        // get the template and then read the "creationDate" attribute
        Document doc = getTemplateByName(templateName);
        String date = doc.getDocumentElement().getAttribute(Util.ATTRIBUTE_CREATIONDATE);
        if (date.trim().length() == 0) {
            return null;
        }

        // parse the date
        try {
            return DATE_FORMATE.parse(date);
        } catch (ParseException e) {
            // wrap it in PersistenceException and rethrow
            throw new PersistenceException("Error occurs when parsing the date.", e);
        }
    }

    /**
     * <p>
     * Initialize the <code>templates</code> map with the templateFiles array. It will pase DOM Documents from
     * the files and put them in the templates map.
     * </p>
     *
     * @param templateFiles
     *            the template files
     * @throws ParserConfigurationException
     *             thrown from SAX API
     * @throws SAXException
     *             thrown from SAX API
     * @throws IOException
     *             if any I/O error occurs
     * @throws PersistenceException
     *             if the template name is empty string
     * @throws IllegalArgumentException
     *             if the parser is not JAXP 1.2 compatible.
     */
    private void initialzeTemplates(String[] templateFiles) throws ParserConfigurationException,
        SAXException, IOException, PersistenceException {

        if (templateFiles == null) {
            throw new IllegalArgumentException("templateFiles can not be null!");
        }
        if (templateFiles.length < 1) {
            throw new IllegalArgumentException("There should be at least one template in templateFiles!");
        }
        // check the templateFile for null or empty string
        for (int i = 0; i < templateFiles.length; i++) {
            if (templateFiles[i] == null) {
                throw new IllegalArgumentException("Element in the templateFiles can not be null!");
            }
            if (templateFiles[i].trim().length() == 0) {
                throw new IllegalArgumentException("Element in the templateFiles can not be empty!");
            }
        }
        // for each template file, try to parse the xml file to Document object and cache it to the
        // map
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        // set it to validating
        factory.setValidating(true);

        // set the XSD schema
        // If the parser is not JAXP 1.2 compatible, these may throw IllegalArgumentException.
        factory.setAttribute("http://java.sun.com/xml/jaxp/properties/schemaLanguage",
            "http://www.w3.org/2001/XMLSchema");
        factory.setAttribute("http://java.sun.com/xml/jaxp/properties/schemaSource", getSchemaFile());

        DocumentBuilder builder = factory.newDocumentBuilder();
        // create and set the error handler
        ErrorHandler errorHandler = new ErrorHandler() {

            /**
             * <p>
             * Just rethrow the SAXParseException.
             * </p>
             *
             * @param exception
             *            the SAXParseException that describes the parsing error that occured.
             * @throws SAXException
             *             when error happens in parsing.
             */
            public void error(SAXParseException exception) throws SAXException {   
                throw exception;
            }

            /**
             * <p>
             * Just rethrow the SAXParseException.
             * </p>
             *
             * @param exception
             *            the SAXParseException that describes the parsing error that occured.
             * @throws SAXException
             *             when fatal error happens in parsing.
             */
            public void fatalError(SAXParseException exception) throws SAXException {
                throw exception;
            }

            /**
             * <p>
             * Just rethrow the SAXParseException.
             * </p>
             *
             * @param exception
             *            the SAXParseException that describes the parsing error that occured.
             * @throws SAXException
             *             when warning happens in parsing.
             */
            public void warning(SAXParseException exception) throws SAXException {
                throw exception;
            }
        };

        builder.setErrorHandler(errorHandler);
        for (int i = 0; i < templateFiles.length; i++) {
			builder = factory.newDocumentBuilder();
            Document doc = builder.parse(XmlPhaseTemplatePersistence.class.getClassLoader().getResourceAsStream(templateFiles[i]));
            String name = doc.getDocumentElement().getAttribute(Util.ATTRIBUTE_NAME);   
            // the template name should be non-empty
            if (name.trim().length() == 0) {
                throw new PersistenceException("The name of a template is empty!");
            }    
            this.templates.put(name, doc);
        }
    }

    /**
     * <p>
     * Gets the schema file from the CLASSPATH, if it is not found in classpath, then try to find in the
     * relative file path.
     * </p>
     *
     * @return a File instance of schema file.
     */
    private static File getSchemaFile() {

        // get the schema file from classpath
        URL schemaURL = XmlPhaseTemplatePersistence.class.getClassLoader().getResource(SCHEMA_FILE);

        // when schema file is not found in classpath, try to find the input schema file in the
        // relative file path.
        if (schemaURL == null) {
            return new File(XmlPhaseTemplatePersistence.SCHEMA_FILE);
        } else {
            File schemaFile = new File(schemaURL.getFile());
            return schemaFile;
        }
    }

    /**
     * <p>
     * Parse a string value to boolean value.
     * </p>
     * <p>
     * If string value equals to "true"(case-sensitive), then <code>true</code> will be returned; If string
     * value equals to "false"(case-sensitive), then <code>false</code> will be returned. Otherwise,
     * <code>IllegalArgumentException</code> will be thrown.
     * </p>
     *
     * @param value
     *            the string value
     * @return parsed boolean value
     * @throws IllegalArgumentException
     *             if the value can not be parsed to boolean value
     */
    private static boolean parseBoolean(String value) {

        if (value.equals("true")) {
            return true;
        } else if (value.equals("false")) {
            return false;
        } else {
            throw new IllegalArgumentException("value is not either true or false.");
        }
    }

    /**
     * <p>
     * Get the template document with the given name from templates.
     * </p>
     *
     * @param templateName
     *            the name of the template, should not be empty or null
     * @return the template document with the given name
     * @throws IllegalArgumentException
     *             if templateName is null or empty, or a template with the
     *             given name can not be found.
     * @since 1.1
     */
    private Document getTemplateByName(String templateName) {

        Util.checkString(templateName, "templateName");
        Document doc = (Document) templates.get(templateName);
        if (doc == null) {
            throw new IllegalArgumentException("The template with the name \"" + templateName
                + "\" can not be found.");
        }
        return doc;
    }

    /**
     * <p>
     * Get the category of the given template document.
     * </p>
     *
     * @param docElement
     *            the template document to get the category from
     * @return the category of the given template document, 0 if not present
     * @throws PersistenceException
     *             if it fail to parse the numeric value
     * @since 1.1
     */
    private int getCategory(Element docElement) throws PersistenceException {

        // get the attribute "category"
        String value = docElement.getAttribute(Util.ATTRIBUTE_CATEGORY);

        // check the attribute
        if (value.trim().length() != 0) {
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                throw new PersistenceException("Error occurs when parse the int value.", e);
            }
        } else {
            return 0;
        }
    }
}
