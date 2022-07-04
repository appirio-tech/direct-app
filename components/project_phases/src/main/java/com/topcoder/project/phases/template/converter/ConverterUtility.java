/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.phases.template.converter;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.topcoder.db.connectionfactory.DBConnectionException;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.project.phases.template.ConfigurationException;
import com.topcoder.project.phases.template.Util;
import com.topcoder.util.commandline.ArgumentValidationException;
import com.topcoder.util.commandline.CommandLineUtility;
import com.topcoder.util.commandline.IllegalSwitchException;
import com.topcoder.util.commandline.Switch;
import com.topcoder.util.commandline.UsageException;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.ConfigManagerException;
import com.topcoder.util.config.UnknownNamespaceException;

/**
 * <p>
 * This class is a persistance migrating utility for XML phase templates files. It can be used as a main class
 * in a standalone utility which reads XML files with templates and outputs to console SQL INSERT queries
 * those must be executed by the user to configure template phases database.
 * <p>
 * <p>
 * This utility uses Configuration Manager component to retrieve all input data. Namespaces of
 * XmlPhaseTemplatePersistence and optionally DBPhaseTemplatePersistence configurations must be specified as
 * command line arguments. Namespaces can be preloaded using ConfigManager.properties file. The users can also
 * provide them using these optional switch "-ifile", "-ofile".
 * </p>
 * <p>
 * If the user passes "auto" as the command line argument, the utility automatically executes all generated
 * SQL queries.
 * </p>
 *
 * @author saarixx, TCSDEVELOPER
 * @version 1.1
 */
public class ConverterUtility {

    /**
     * <p>
     * Represent the name of the "auto" switch.
     * </p>
     */
    private static final String AUTO = "auto";

    /**
     * <p>
     * Represent the name of the "inamespace" switch.
     * </p>
     */
    private static final String I_NAMESPACE = "inamespace";

    /**
     * <p>
     * Represent the name of the "onamespace" switch.
     * </p>
     */
    private static final String O_NAMESPACE = "onamespace";

    /**
     * <p>
     * Represent the name of the "ifile" switch.
     * </p>
     */
    private static final String I_FILE = "ifile";

    /**
     * <p>
     * Represent the name of the "ofile" switch.
     * </p>
     */
    private static final String O_FILE = "ofile";

    /**
     * <p>
     * Represent the string of "-".
     * </p>
     */
    private static final String DASH = "-";

    /**
     * <p>
     * Represent an message to show the sample usage of this component.
     * </p>
     */
    private static final String HELP_MSG = "Sample usage: java jarname"
        + " Cinamespace Xml_Persistence_NamSpace Conamespace DB_Persistence_Namespace -auto"
        + "(-onamespace and -auto is optional)";

    /**
     * <p>
     * Represent a string that used to generate the insert command for table "template".
     * </p>
     */
    private static final String TEMPLATE_INSERT_STRING = "INSERT INTO template (id, name, category,"
        + " creation_date, description) VALUES ({0}, {1}, {2}, {3}, {4})";

    /**
     * <p>
     * Represent a string that used to generate the insert command for table "default_template".
     * </p>
     */
    private static final String DEFAULTTEMPLATE_INSERT_STRING = "INSERT INTO default_template "
        + "(template_id, category) VALUES ({0}, {1})";

    /**
     * <p>
     * Represent a string that used to generate the insert command for table "phase_type".
     * </p>
     */
    private static final String PHASETYPE_INSERT_STRING = "INSERT INTO phase_type (id, template_id, type_id, name)"
        + " VALUES ({0}, {1}, {2}, {3})";

    /**
     * <p>
     * Represent a string that used to generate the insert command for table "phase".
     * </p>
     */
    private static final String PHASE_INSERT_STRING = "INSERT INTO phase (id, template_id, phase_type_id, phase_id,"
        + " time_length) VALUES ({0}, {1}, {2}, {3}, {4})";

    /**
     * <p>
     * Represent a string that used to generate the insert command for table "dependency".
     * </p>
     */
    private static final String DEPENDENCY_INSERT_STRING = "INSERT INTO dependency (id, dependent_id, dependency_id,"
        + " dependent_start, dependency_start, " + "lag_time) VALUES ({0}, {1}, {2}, {3}, {4}, {5})";

    /**
     * <p>
     * The id of the last record in template table. Can be initialized in main() method. Is used in
     * convert(String[]) method.
     * </p>
     *
     */
    private int templateId = 0;

    /**
     * <p>
     * The id of the last record in phase_type table. Can be initialized in main() method. Is used in
     * convert(String[]) method.
     * </p>
     */
    private int phaseTypeId = 0;

    /**
     * <p>
     * The id of the last record in phase table. Can be initialized in main() method. Is used in
     * convert(String[]) method.
     * </p>
     */
    private int phaseId = 0;

    /**
     * <p>
     * The id of the last record in dependency table. Can be initialized in main() method. Is used in
     * convert(String[]) method.
     * </p>
     *
     */
    private int dependencyId = 0;

    /**
     * <p>
     * The database connection which must be used to execute the generated SQL queries. Can be initialized in
     * main(). Is used in convert(String[]). Can be null if queries doesn't have to be executed.
     * </p>
     */
    private Connection connection = null;

    /**
     * <p>
     * Empty private constructor of the utility to prevents to create a new instance.
     * </p>
     * <p>
     */
    private ConverterUtility() {

    }

    /**
     * <p>
     * This method is executed when converter utility is executed from command line.
     * </p>
     * <p>
     * Accepted command line arguments (strict order): "-i" flag followed by
     * XmlPhaseTemplatePersistence configuration namespace, "-o" flag followed by
     * DBPhaseTemplatePersistence configuration namespace and optional "auto" flag. The method
     * utilizes Command Line Utility component to facilitate parameters parsing and handling.
     * </p>
     *
     * @param args
     *            the list of command line arguments
     */
    public static void main(String args[]) {

        // create a command line utility
        CommandLineUtility utility = initializeCommandUtility(args);
        if (utility == null) {
            // fail to initialize the utility, return
            return;
        }

        Connection connection = null;
        try {
            // Create a ConverterUtility instance
            ConverterUtility converter = new ConverterUtility();
            // Get the config manager
            ConfigManager cfg = ConfigManager.getInstance();

            // get the name space for xml and db persistence, dbNamespace could be null
            String xmlNamespace = utility.getSwitch(I_NAMESPACE).getValue();
            String dbNamespace = utility.getSwitch(O_NAMESPACE).getValue();

            // get the additional configuration file name for xml and db persistence
            String[] configFiles = new String[2];
            configFiles[0] = utility.getSwitch(I_FILE).getValue();
            configFiles[1] = utility.getSwitch(O_FILE).getValue();

            // load the additional config file into configuration manager
            for (int i = 0; i < configFiles.length; ++i) {
                if (configFiles[i] != null) {
                    cfg.add(new File(configFiles[i]).getAbsolutePath());
                }
            }

            // get the templateFiles
            String[] templateFiles = ConfigManager.getInstance().getStringArray(xmlNamespace, "template_files");

            // look for the "auto" switch
            boolean auto = false;
            for (int i = 0; i < args.length; ++i) {
                if (args[i].equals(DASH + AUTO)) {
                    auto = true;
                }
            }

            // query the database for id only if "-onamespace" switch is on
            if (dbNamespace != null) {
                // get the db factory and connection name
                Object[] result = Util.createDBConnectionFactoryAndConnectionName(dbNamespace);
                String connectionName = (String) result[0];
                DBConnectionFactory factory = (DBConnectionFactory) result[1];

                // connect to the database
                connection = Util.createConnection(factory, connectionName);

                // the name of table needs to retrieve the last id from database
                String tableNames[] = {"template", "phase_type", "dependency", "phase"};
                // array that hold the last id
                int[] lastId = new int[tableNames.length];

                // query the database to get the last id for each table
                for (int i = 0; i < tableNames.length; ++i) {
                    PreparedStatement statement = null;
                    ResultSet resultSet = null;
                    try {
                        // execute the statement "SELECT FIRST 1 id FROM <tablename> ORDER BY id DESC"
                        statement = connection.prepareStatement("SELECT FIRST 1 id FROM " + tableNames[i]
                            + " ORDER BY id DESC");
                        resultSet = statement.executeQuery();

                        // get the last id
                        if (resultSet.next()) {
                            lastId[i] = resultSet.getInt(1);
                        } else {
                            // set to 0 if not found
                            lastId[i] = 0;
                        }
                    } finally {
                        // close in finally clause
                        Util.closeResultAndStatement(statement, resultSet);
                    }
                }

                // set the converter's last id field
                converter.templateId = lastId[0];
                converter.phaseTypeId = lastId[1];
                converter.dependencyId = lastId[2];
                converter.phaseId = lastId[3];
            }

            // set the connection if auto is enabled
            if (auto) {
                converter.connection = connection;
            }

            // Convert XML files
            converter.convert(templateFiles);

        } catch (UnknownNamespaceException e) {
            System.out.println("Error: The given xml persistence namespace is unknown.");
        } catch (ConfigurationException e) {
            System.out.println("Error: The given db persistence namespace is unknown, or the configuration"
                + " of the ConfigManager is incorrect.");
        } catch (DBConnectionException e) {
            System.out.println("Error: Fail to connect to the database.");
        } catch (SQLException e) {
            System.out.println("Error: Operation on the database failed.");
            System.out.println("Cause: " + e.getMessage());
        } catch (ConfigManagerException e) {
            System.out.println("Error: Fail to load the given file to the configuration manager.");
            System.out.println("Cause: " + e.getMessage());
        } catch (SecurityException e) {
            System.out.println("Error: Fail to access the given config file.");
        } finally {
            // close the connection in finally clause
            Util.closeConnection(connection);
        }
    }

    /**
     * <p>
     * Initialize a CommandLineUtility with the given command arguments.
     * </p>
     *
     * @param args
     *            the command argument
     * @return the initialized CommandLineUtility. If any error occurs, it would return null.
     */
    private static CommandLineUtility initializeCommandUtility(String args[]) {
        // create a CommandLineUtility
        CommandLineUtility utility = new CommandLineUtility();

        try {
            // create the switches and add them to add the command line utility
            utility.addSwitch(new Switch(I_NAMESPACE, true, 1, 1, null));
            utility.addSwitch(new Switch(O_NAMESPACE, false, 1, 1, null));
            utility.addSwitch(new Switch(I_FILE, false, 1, 1, null));
            utility.addSwitch(new Switch(O_FILE, false, 1, 1, null));
            utility.addSwitch(new Switch(AUTO, false, 0, 0, null));

            // parse the argument
            utility.parse(args);
        } catch (IllegalSwitchException e) {
            System.out.println("Fail to initialize the command line utility.");
            return null;
        } catch (ArgumentValidationException e) {
            // would never occur
            return null;
        } catch (UsageException e) {
            System.out.println("Command not correct.");
            System.out.println(HELP_MSG);
            return null;
        }
        return utility;
    }

    /**
     * <p>
     * Reads the provided phase templates XML files and outputs corresponding SQL INSERT commands to standart
     * output. It also executes the generated queries if connection is not null.
     * </p>
     *
     * @param templateFileNames
     *            the list of files to be read
     * @throws SQLException
     *             to main method to handle if it fails to enable the transaction
     */
    private void convert(String[] templateFileNames) throws SQLException {
        if (connection != null) {
            // enable the transaction
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
        }

        String fileName = null;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();

            for (int i = 0; i < templateFileNames.length; ++i) {
                templateId++;
                // parse the xml document
                fileName = templateFileNames[i];
                Document doc = builder.parse(fileName);
                Element documentElement = doc.getDocumentElement();

                // get the template name
                String templateName = transferString(documentElement.getAttribute(Util.ATTRIBUTE_NAME));
                if (templateName == null) {
                    System.out.println("The name of the template " + fileName + " is empty!");
                    Util.rollback(connection);
                    return;
                }

                // get the template category
                String templateCategory = documentElement.getAttribute(Util.ATTRIBUTE_CATEGORY);
                if (templateCategory.trim().length() == 0) {
                    templateCategory = "0";
                }

                // get the template creation date and description
                String creationDate = transferString(documentElement.getAttribute(Util.ATTRIBUTE_CREATIONDATE));
                String description = transferString(documentElement.getAttribute(Util.ATTRIBUTE_DESCRIPTION));

                // set the parameters
                Object[] params = {new Long(templateId), templateName, templateCategory, creationDate, description};
                // generate and output the insert command for template
                outPutSQLInsertCommand(TEMPLATE_INSERT_STRING, params);

                // get the isDefault attribute
                boolean isDefault = false;
                String isDefaultString = documentElement.getAttribute(Util.ATTRIBUTE_ISDEFAULT);
                if (isDefaultString.trim().length() != 0) {
                    if (isDefaultString.equals("true")) {
                        isDefault = true;
                    }
                    // in other cases, isDefault would be false. since the schema will
                    // validate the attribute's value, so we need not check the value here
                }

                if (isDefault) {
                    // generate and output the insert command for default_template
                    params = new Object[]{new Long(templateId), templateCategory};
                    outPutSQLInsertCommand(DEFAULTTEMPLATE_INSERT_STRING, params);
                }

                // generate and output the insert command for phast type
                Map<String, Long> phaseTypes = generatePhaseTypeSQL(documentElement);
                // generate and output the insert command for phast
                Map<String, Long> phases = generatePhaseSQL(documentElement, phaseTypes);

                // For each phase, generate and output the insert command for its dependencies
                NodeList list = documentElement.getElementsByTagName(Util.TAG_PHASE);
                for (int index = 0; index < list.getLength(); index++) {
                    Element phaseElement = (Element) list.item(index);
                    generateDependencySQL(phaseElement, phases);
                }

            }

            // commit the transaction
            if (connection != null) {
                connection.commit();
            }

        } catch (ParserConfigurationException e) {
            System.out.println("Error: fail to parse the xml file \"" + fileName + "\".");
            Util.rollback(connection);
        } catch (SAXException e) {
            System.out.println("Error: fail to parse the xml file \"" + fileName + "\".");
            Util.rollback(connection);
        } catch (IOException e) {
            System.out.println("Error: fail to read the xml file \"" + fileName + "\".");
            Util.rollback(connection);
        } catch (SQLException e) {
            System.out.println("Error: Operation on the database failed.");
            System.out.println("Cause: " + e.getMessage());
            Util.rollback(connection);
        } catch (NumberFormatException e) {
            System.out.println("Error: fail to parse the number value.");
            Util.rollback(connection);
        }
    }

    /**
     * <p>
     * Generate sql insert command for dependencies of the given phase element.
     * </p>
     *
     * @param phaseElement
     *            the phase element to work with
     * @param phases
     *            the map used to retrieve the dependency phase id
     * @throws SQLException
     *             if fail to execute the statement
     */
    private void generateDependencySQL(Element phaseElement, Map<String, Long> phases) throws SQLException {

        // get the dependent phase id
        Object dependentId = phases.get(phaseElement.getAttribute(Util.ATTRIBUTE_ID));
        // Get a list of dependency nodes
        NodeList dependencyElements = phaseElement.getElementsByTagName(Util.TAG_DEPENDENCY);

        for (int i = 0; i < dependencyElements.getLength(); i++) {
            dependencyId++;
            Element dependencyElement = (Element) dependencyElements.item(i);

            // get the dependency phase id
            String id = dependencyElement.getAttribute(Util.ATTRIBUTE_ID);
            Object dependencyPhaseId = phases.get(id);

            // Set String isDependencyStart to "f" if corresponding attribute is absent or equal to
            // "false", else set it to "t"
            String isDependencyStart = "t";
            String booleanAttribute = dependencyElement.getAttribute(Util.ATTRIBUTE_IS_DEPENDENCY_START);
            if (booleanAttribute.trim().length() == 0 || booleanAttribute.equals("false")) {
                isDependencyStart = "f";
            }
            isDependencyStart = transferString(isDependencyStart);

            // Set String isDependentStart to "t" if corresponding attribute is absent or equal to
            // "true", else set it to "f"
            String isDependentStart = "f";
            booleanAttribute = dependencyElement.getAttribute(Util.ATTRIBUTE_IS_DEPENDENT_START);
            if (booleanAttribute.trim().length() == 0 || booleanAttribute.equals("true")) {
                isDependentStart = "t";
            }
            isDependentStart = transferString(isDependentStart);

            // Get "lagTime" attribute of dependencyElement and set it to String lagTime.
            String lagTime = dependencyElement.getAttribute(Util.ATTRIBUTE_LAG_TIME);
            if (lagTime.trim().length() == 0) {
                lagTime = null;
            }

            // set the parameters
            Object[] params = new Object[]{new Long(dependencyId), dependentId, dependencyPhaseId, isDependentStart,
                isDependencyStart, lagTime};
            // generate and output the insert command for dependency
            outPutSQLInsertCommand(DEPENDENCY_INSERT_STRING, params);
        }
    }

    /**
     * <p>
     * Generate sql insert command for phase of the given root document element.
     * </p>
     *
     * @param documentElement
     *            the root document element to work with
     * @param phaseTypes
     *            the map used to retrieve the phase type id
     * @return Create a map of phases (key is an internal id in XML file, value - id in a database table).
     *         This is used to generate sql insert command for dependency.
     * @throws SQLException
     *             if fail to execute the statement
     */
    private Map<String, Long> generatePhaseSQL(Element documentElement, Map<String, Long> phaseTypes)
        throws SQLException {

        // Create a map of phases (key is an internal id in XML file, value - id in a database table)
        Map<String, Long> phases = new HashMap<String, Long>();
        // curPhaseId used if "phaseId" attribute is absent
        int curPhaseId = 0;
        // Get a list of phase nodes
        NodeList list = documentElement.getElementsByTagName(Util.TAG_PHASE);

        for (int i = 0; i < list.getLength(); i++) {
            Element phaseElement = (Element) list.item(i);
            phaseId++;
            curPhaseId++;

            // get "id", "phaseId", "length" and "type" attributes
            String id = phaseElement.getAttribute(Util.ATTRIBUTE_ID);
            String length = phaseElement.getAttribute(Util.ATTRIBUTE_LENGTH);
            String type = phaseElement.getAttribute(Util.ATTRIBUTE_TYPE);
            String xmlPhaseId = phaseElement.getAttribute(Util.ATTRIBUTE_PHASEID);

            // if "phaseId" attribute is absent, set xmlPhaseId to curPhaseId
            if (xmlPhaseId.trim().length() == 0) {
                xmlPhaseId = new Long(curPhaseId).toString();
            }

            // Add an item to phases
            phases.put(id, new Long(phaseId));

            // Get typeId for type. Since the MessageFormat will deal with parameters,
            // we do not need to know the concret class of typeId
            Object typeId = phaseTypes.get(type);

            // set the parameters
            Object[] params = {new Long(phaseId), new Long(templateId), typeId, xmlPhaseId, length};
            // generate and output the insert command for phase
            outPutSQLInsertCommand(PHASE_INSERT_STRING, params);
        }

        return phases;
    }

    /**
     * <p>
     * Generate sql insert command for phase types of the given root document element.
     * </p>
     *
     * @param documentElement
     *            the root document element to work with
     * @return a map of phase types (key will be an internal id in XML file, value - id in a database table).
     *         This is used to generate sql insert command for phase.
     * @throws SQLException
     *             if fail to execute the statement
     */
    private Map<String, Long> generatePhaseTypeSQL(Element documentElement) throws SQLException {

        // Create a map of phase types (key will be an internal id in XML file, value - id in a database
        // table)
        Map<String, Long> phaseTypes = new HashMap<String, Long>();
        // Get a list of phase type nodes
        NodeList list = documentElement.getElementsByTagName(Util.TAG_PHASE_TYPE);

        // iterate all the element in the list
        for (int i = 0; i < list.getLength(); i++) {
            Element typeElement = (Element) list.item(i);
            phaseTypeId++;

            // retrieve "typeId" , "id" and "typeName" attribute
            long typeId = Long.parseLong(typeElement.getAttribute(Util.ATTRIBUTE_TYPE_ID));
            String id = typeElement.getAttribute(Util.ATTRIBUTE_ID);
            String typeName = transferString(typeElement.getAttribute(Util.ATTRIBUTE_TYPE_NAME));

            // add an item to phaseTypes
            phaseTypes.put(id, new Long(phaseTypeId));

            // set the parameters
            Object[] params = {new Long(phaseTypeId), new Long(templateId), new Long(typeId), typeName};
            // generate and output the insert command for phase type
            outPutSQLInsertCommand(PHASETYPE_INSERT_STRING, params);
        }
        return phaseTypes;
    }

    /**
     * <p>
     * Transfer the string attribute in xml to a informix recognized string format.
     * </p>
     *
     * @param arg
     *            the string to transfer
     * @return the transfered string
     */
    private static String transferString(String arg) {

        if (arg.trim().length() == 0) {
            return null;
        } else {
            // only add "'" if arg is not null
            return "'" + arg + "'";
        }
    }

    /**
     * <p>
     * Format the passed in string with the params to generate the sql insert command and output the command
     * to standard output. Execute the command if connection if not null.
     * <p>
     *
     * @param script
     *            the String to formate
     * @param params
     *            the parameters to format
     * @throws SQLException
     *             if it fail to execute the sql statement
     */
    private void outPutSQLInsertCommand(String script, Object[] params) throws SQLException {
        // format the script with the given params
        String sqlCmmand = new MessageFormat(script).format(params);
        System.out.println(sqlCmmand + ";");

        // execute the command if connection is not null
        PreparedStatement statement = null;
        if (connection != null) {
            try {
                statement = connection.prepareStatement(sqlCmmand);
                statement.executeUpdate();
            } finally {
                Util.closeStatement(statement);
            }
        }
    }
}
