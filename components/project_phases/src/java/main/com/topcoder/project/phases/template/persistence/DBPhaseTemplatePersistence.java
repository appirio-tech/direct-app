/*
 * Copyright (C) 2007-2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.phases.template.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.topcoder.db.connectionfactory.DBConnectionException;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.project.phases.CyclicDependencyException;
import com.topcoder.project.phases.Dependency;
import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.PhaseType;
import com.topcoder.project.phases.Project;
import com.topcoder.project.phases.template.ConfigurationException;
import com.topcoder.project.phases.template.PersistenceException;
import com.topcoder.project.phases.template.PersistenceRuntimeException;
import com.topcoder.project.phases.template.PhaseGenerationException;
import com.topcoder.project.phases.template.PhaseTemplatePersistence;
import com.topcoder.project.phases.template.Util;

/**
 * <p>
 * This is a database based phase templates persistence implementation. This class must work at least with
 * Informix 10 DBMS.<br/>
 * It has method <code>removeTemplate(String)</code> which is not part <code>PhaseTemplatePersistence</code>.
 * This method can be used directly by the user to remove a template with specific name from the database.
 * </p>
 *
 * <p>
 * It uses <code>DBConnectionFactory</code> to establish a connection with DBMS.
 * </p>
 *
 * <p>
 * Change for version 1.2: Updated generatePhases() method to support skipping specified phases from the
 * template.
 * </p>
 * <p>
 * <strong>Thread-safety:</strong> This class is immutable. Its attributes are frozen after initialization in
 * the constructor. Thus this class is thread safe.
 * </p>
 *
 * @author saarixx, TCSDEVELOPER
 * @version 1.1
 */
public class DBPhaseTemplatePersistence implements PhaseTemplatePersistence {

    /**
     * <p>
     * Represent an array of String that holds the sql script to execute when getting the template
     * names.
     * </p>
     *
     * <p>
     * GETNAMES_SQL_STATEMENT[0] is used in getAllTemplateNames() and GETNAMES_SQL_STATEMENT[1] is
     * used in getAllTemplateNames(int), and GETNAMES_SQL_STATEMENT[2] is used in
     * getDefaultTemplateName(int)
     * </p>
     */
    private static final String[] GETNAMES_SQL_STATEMENT = {
        "SELECT name FROM template",
        "SELECT name FROM template WHERE category=?",
        "SELECT name FROM default_template JOIN"
            + " template ON default_template.template_id=template.id WHERE default_template.category=?"};

    /**
     * <p>
     * Represent an array of String that holds the sql script to delete the entry from database.
     * </p>
     *
     * <p>
     * The script are listed as below in order: DELETE FROM default_template WHERE template_id=?
     * DELETE FROM phase WHERE template_id=? DELETE FROM phase_type WHERE template_id=? ELETE FROM
     * template WHERE id=?
     * </p>
     */
    private static final String[] DELETE_SQL_STATEMENT = {"DELETE FROM default_template WHERE template_id=?",
        "DELETE FROM phase WHERE template_id=?", "DELETE FROM phase_type WHERE template_id=?",
        "DELETE FROM template WHERE id=?"};

    /**
     * <p>
     * Represent the name of "id" field in "template" table.
     * </p>
     */
    private static final String ID_FIELD = "id";

    /**
     * <p>
     * Represent the name of "description" field in "template" table.
     * </p>
     */
    private static final String DESCRIPTION_FIELD = "description";

    /**
     * <p>
     * Represent the name of "category" field in "template" table.
     * </p>
     */
    private static final String CATEGORY_FIELD = "category";

    /**
     * <p>
     * Represent the name of "creation_date" field in "template" table.
     * </p>
     */
    private static final String CREATION_DATE_FIELD = "creation_date";

    /**
     * <p>
     * This represents the connection factory from which we will obtain a pre-configured connection
     * for our data base access.
     * </p>
     *
     * <p>
     * It is initialized in one of the constructors and once initialized cannot be changed. Can not
     * be null. Accessed in all methods of the class when creating a database connection.
     * </p>
     *
     */
    private final DBConnectionFactory factory;

    /**
     * <p>
     * This represents a connection name (an alias) that is used to fetch a connection instance from
     * the connection factory.
     * </p>
     *
     * <p>
     * This is initialized in one of the constructors and once initialized cannot be changed. Can be
     * null or an empty string, upon which it will try to use the default connection. Is accessed in
     * all methods of the class when creating a database connection.
     * </p>
     *
     */
    private final String connectionName;


    /**
     * <p>
     * The constructor which will populate the connection factory and connection name information
     * from configuration.
     * </p>
     *
     *
     * @param namespace the configuration namespace, can not be null or empty.
     * @throws ConfigurationException if any of the required configuration parameters is missing or
     *         incorrect, or any other error occurs.
     * @throws IllegalArgumentException if namespace is an empty string or null.
     */
    public DBPhaseTemplatePersistence(String namespace) throws ConfigurationException {
        Object[] result = {null, null};
        try {
            result = Util.createDBConnectionFactoryAndConnectionName(namespace);
        } finally {
            // the final member must be initialized
            connectionName = (String) result[0];
            factory = (DBConnectionFactory) result[1];
        }
    }

    /**
     * <p>
     * A simple constructor which will populate the connection factory and connection name
     * information from input parameters.
     * </p>
     *
     *
     * @param factory the DB connection factory instance, should not be null.
     * @param connectionName the DB connection name (can be null or empty). If connectionName is
     *        null, it would use a default connection.
     * @throws IllegalArgumentException if factory is null
     */
    public DBPhaseTemplatePersistence(DBConnectionFactory factory, String connectionName) {
        Util.checkNull(factory, "factory");
        this.connectionName = connectionName;
        this.factory = factory;
    }

    /**
     * <p>
     * Generates an array of Phases from the template with the given name. Stores it in the provided Project
     * instance.
     * </p>
     * <p>
     * Change in 1.2: Added leftOutPhaseIds parameter. Added a support for skipping phases to be left out.
     * </p>
     *
     * @param templateName
     *            the template name, should not be null or empty
     * @param project
     *            the project object, should not be null
     * @param leftOutPhaseIds
     *            the IDs of phases to be left out (null or empty if no phases
     *            should be left out)
     * @throws PersistenceException
     *             if any error occurs while accessing the database
     * @throws PhaseGenerationException
     *             if any error occurs in the phase generation (e.g. cyclic
     *             dependency, etc.) so that the generation process can not
     *             complete successfully.
     * @throws IllegalArgumentException
     *             if templateName is null or empty, project is null, or a
     *             template with this name can not be found in the database, or
     *             if leftOutPhaseIds contains unknown/duplicate phase ID
     */
    public void generatePhases(String templateName, Project project, long[] leftOutPhaseIds)
        throws PersistenceException, PhaseGenerationException {

        // JDBC interface
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;

        // check the argument
        Util.checkString(templateName, "templateName");
        Util.checkNull(project, "project");

        // Since 1.2 start
        // check array duplicate value.
        Set<Long> leftOutPhaseIdsSet = Util.checkArrayDuplicateValue(leftOutPhaseIds);
        // Since 1.2 end

        try {
            // Create a database connection with use of DBConnectionFactory.
            connection = Util.createConnection(factory, connectionName);

            // get the template id with the given name
            long templateId = ((Long) getTemplateField(connection, templateName, ID_FIELD)).longValue();

            // get all the phase types of the template
            Map<Long, PhaseType> types = new HashMap<Long, PhaseType>();
            try {
                // query the phase_type table for the types with the given
                // template_id
                statement = connection
                    .prepareStatement("SELECT id, type_id, name FROM phase_type WHERE template_id=?");
                statement.setLong(1, templateId);
                result = statement.executeQuery();

                // Extract the results. For each result, create a PhaseType, and
                // put it in types
                while (result.next()) {
                    // get "id" column
                    long id = result.getLong(1);
                    // get "type_id" column
                    long typeId = result.getLong(2);
                    // get "name" column
                    String name = result.getString(2 + 1);

                    types.put(id, new PhaseType(typeId, name));
                }
            } finally {
                // close the result set and statement in finally clause
                Util.closeResultAndStatement(statement, result);
            }

            // Since 1.2 start
            int skippedPhasesNum = 0;
            // keys in both maps are Longs for dependent phase IDs
            // values in both maps are Lists of Dependency instances
            Map<Long, List<Dependency>> dependenciesToLeftOutPhases = new HashMap<Long, List<Dependency>>();
            // Since 1.2 end

            // get all the phases of the template
            Map<Long, Phase> phases = new HashMap<Long, Phase>();
            try {
                // query the phase_type table for the phases with the given
                // template_id
                statement = connection
                    .prepareStatement("SELECT id, phase_type_id, phase_id, time_length FROM phase WHERE template_id=?");
                statement.setLong(1, templateId);
                result = statement.executeQuery();

                // Extract the results. For each result, create a Phase, and put
                // it in phases
                while (result.next()) {

                    // get the "id" column
                    long id = result.getLong(1);
                    // get the "phase_type_id" column
                    long phaseTypeId = result.getLong(2);
                    // get the "phase_id" column
                    long phaseId = result.getLong(2 + 1);
                    // get the "time_length" column
                    long timeLength = result.getLong(2 + 2);

                    // Since 1.2 start
                    if (leftOutPhaseIdsSet.contains(id)) {
                        skippedPhasesNum++;
                    }
                    // Since 1.2 end

                    // create a phase and set the type, id
                    Phase phase = new Phase(project, timeLength);
                    PhaseType type = (PhaseType) types.get(phaseTypeId);

                    // if the type can not be found in the map, then the type is
                    // not contained in the template throw exception is this case
                    if (type == null) {
                        throw new PhaseGenerationException("The phase type with id " + phaseTypeId
                            + " is not the type in template \"" + templateName + "\".");
                    }

                    phase.setPhaseType(type);
                    phase.setId(phaseId);

                    // put it in phases
                    phases.put(id, phase);
                }

            } finally {
                // close the result set and statement in finally clause
                Util.closeResultAndStatement(statement, result);
            }

            // Since 1.2 start
            // check phase id is valid in leftOutPhaseIds
            Util.checkArraySize(skippedPhasesNum, leftOutPhaseIdsSet.size());

            // build the dependency for each phase
            for (Entry<Long, Phase> phase : phases.entrySet()) {
                addDependency(connection, phase.getKey().longValue(), phase.getValue(), phases,
                    leftOutPhaseIdsSet, dependenciesToLeftOutPhases, leftOutPhaseIdsSet);
            }

            // process all dependencies for left out phases
            // this algorithm replaces each dependency from not left out phase
            // to left out phase
            // with dependencies from the original not left out phase to not
            // left out phases that
            // are dependencies of the original left out phase
            // Note that since dependency phases of left out phases can be also
            // left out,
            // the breadth-first search approach is used in this algorithm to
            // locate all new dependencies
            // to be used instead of the old dependency
            Util.processDependencies(leftOutPhaseIdsSet, dependenciesToLeftOutPhases);

            for (Map.Entry<Long, Phase> entry : phases.entrySet()) {
                Phase phase = entry.getValue();
                if (!leftOutPhaseIdsSet.contains(entry.getKey())) {
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
            // Since 1.2 end
        } catch (DBConnectionException e) {
            throw new PersistenceException("Error occurs during generating the phases.", e);
        } catch (SQLException e) {
            throw new PersistenceException("Error occurs during generating the phases.", e);
        } catch (CyclicDependencyException e) {
            throw new PhaseGenerationException("Error occurs during generating the phases.", e);
        } finally {
            // the connection could be closed now
            Util.closeConnection(connection);
        }
    }

    /**
     * <p>
     * Returns the names of all templates defined in the database. Never returns null.
     * </p>
     *
     * @throws PersistenceRuntimeException if error occurs while accessing the database.
     *
     * @return the names of the templates, never returns null
     */
    public String[] getAllTemplateNames() {
        try {
            return getTemplateNames(0, 0);
        } catch (DBConnectionException e) {
            throw new PersistenceRuntimeException("Error occurs during getting the template names.", e);
        } catch (SQLException e) {
            throw new PersistenceRuntimeException("Error occurs during getting the template names.", e);
        }
    }

    /**
     * <p>
     * Returns the list of names of templates those belong to the specified category. Never returns
     * null.
     * </p>
     *
     * @param category
     *            the category of templates
     * @return the list of names of templates those belong to the specified
     *         category
     * @throws PersistenceException
     *             if error occurs while accessing the database
     */
    public String[] getAllTemplateNames(int category) throws PersistenceException {
        try {
            return getTemplateNames(category, 1);
        } catch (DBConnectionException e) {
            throw new PersistenceException("Error occurs during getting the template names.", e);
        } catch (SQLException e) {
            throw new PersistenceException("Error occurs during getting the template names.", e);
        }
    }

    /**
     * <p>
     * Returns the category of template with the given name.
     * </p>
     *
     * @param templateName the name of template, should not be null or empty
     * @return the category of the template with the given name
     * @throws PersistenceException
     *             if any error occurs while accessing the database
     * @throws IllegalArgumentException
     *             if templateName is null or empty or a template with this name
     *             can not be found in the database
     */
    public int getTemplateCategory(String templateName) throws PersistenceException {
        return ((Integer) getTemplateField(templateName, CATEGORY_FIELD)).intValue();
    }

    /**
     * <p>
     * Returns the name of template that is default for the specified category. Returns null if
     * default template is not specified for the given category.
     * </p>
     *
     * @param category
     *            the category of templates
     * @return the name of the default template for the the category of
     *         templates
     * @throws PersistenceException
     *             if any error occurs while accessing the database or more than
     *             one default template for category is found.
     */
    public String getDefaultTemplateName(int category) throws PersistenceException {
        try {
            // get the names that default for the category
            String[] names = getTemplateNames(category, 2);

            if (names.length == 0) {
                return null;
            } else if (names.length == 1) {
                return names[0];
            } else {
                // more than one template name is found, throw exception
                throw new PersistenceException("More than one default templates for category \"" + category
                    + "\" is found.");
            }

        } catch (DBConnectionException e) {
            throw new PersistenceException("Error occurs during getting the template names.", e);
        } catch (SQLException e) {
            throw new PersistenceException("Error occurs during getting the template names.", e);
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
     *             if any error occurs while accessing the database
     * @throws IllegalArgumentException
     *             if templateName is null or empty or a template with this name
     *             can not be found in the database
     */
    public String getTemplateDescription(String templateName) throws PersistenceException {
        return (String) getTemplateField(templateName, DESCRIPTION_FIELD);
    }

    /**
     * <p>
     * Returns the creation date of template with the given name. Can return null if creation date was not
     * specified.
     * </p>
     * <p>
     *
     * @param templateName
     *            the name of template, should not be null or empty
     * @return the creation date of the template with the given name, could be
     *         null
     * @throws PersistenceException
     *             if any error occurs while accessing the database
     * @throws IllegalArgumentException
     *             if templateName is null or empty or a template with this name
     *             can not be found in the database
     */
    public Date getTemplateCreationDate(String templateName) throws PersistenceException {
        return (Date) getTemplateField(templateName, CREATION_DATE_FIELD);
    }

    /**
     * <p>
     * Removes the template with the given name and its related entries(phases, phase types, etc..)
     * from the database.
     * </p>
     *
     * @param templateName
     *            the name of template, should not be null or empty
     * @throws PersistenceException
     *             if error occurs while accessing the database
     * @throws IllegalArgumentException
     *             if templateName is null or empty, or a template with this
     *             name can not be found in the database
     */
    public void removeTemplate(String templateName) throws PersistenceException {
        // JDBC interface
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;

        // check the argument
        Util.checkString(templateName, "templateName");

        try {
            // Create a database connection with use of DBConnectionFactory.
            connection = Util.createConnection(factory, connectionName);

            // enable the transaction
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);

            // get the template id with the given name
            long templateId = ((Long) getTemplateField(connection, templateName, ID_FIELD)).longValue();

            // get all the phase ids of the template
            List<Long> phaseIds = new ArrayList<Long>();
            try {
                // query the phase table for the phases with the given
                // template_id
                statement = connection.prepareStatement("SELECT id FROM phase WHERE template_id=?");
                statement.setLong(1, templateId);
                result = statement.executeQuery();

                // Extract the results, put the id into the list
                while (result.next()) {
                    phaseIds.add(result.getLong("id"));
                }
            } finally {
                // close the result set and statement in finally clause
                Util.closeResultAndStatement(statement, result);
            }

            // delete the entries in dependency
            for (int i = 0; i < phaseIds.size(); ++i) {
                long id = ((Long) phaseIds.get(i)).longValue();
                deleteEntry(connection, id, "DELETE FROM dependency WHERE dependent_id=?");
            }

            // delete other entries in the database, in the following order:
            // entries in default_template, phase, phase_type, template. phase
            // should be deleted
            // before
            // phase_type, and template should be delete last.
            for (int i = 0; i < DELETE_SQL_STATEMENT.length; ++i) {
                deleteEntry(connection, templateId, DELETE_SQL_STATEMENT[i]);
            }

            // commit the transcation
            connection.commit();

        } catch (DBConnectionException e) {
            // rollback if exception occurs
            Util.rollback(connection);
            throw new PersistenceException("Error occurs during generating the phases.", e);
        } catch (SQLException e) {
            // rollback if exception occurs
            Util.rollback(connection);
            throw new PersistenceException("Error occurs during generating the phases.", e);
        } finally {
            // the connection could be closed now
            Util.closeConnection(connection);
        }
    }

    /**
     * <p>
     * Get the specific field value of the template with the given name. This method would create a Connection
     * and then delegate to getTemplateField(Connection, String, String).
     * </p>
     *
     * @param templateName
     *            the name of the template
     * @param fieldName
     *            the name of the field, can only be the following value:
     *            ID_FIELD, CREATION_DATE_FIELD, CATEGORY_FIELD,
     *            DESCRIPTION_FIELD
     * @return the field value of the template. The concert value type depends
     *         on the fieldName value.
     * @throws PersistenceException
     *             if error occurs while accessing the database, or more than
     *             one record with the given name were found
     * @throws IllegalArgumentException
     *             if the template is null or empty, the template with the name
     *             can not be found
     */
    private Object getTemplateField(String templateName, String fieldName) throws PersistenceException {
        Util.checkString(templateName, "templateName");
        Connection connection = null;

        try {
            // create a connection and delegate to getTemplateField(Connection,
            // String, String)
            connection = Util.createConnection(factory, connectionName);
            return getTemplateField(connection, templateName, fieldName);

        } catch (DBConnectionException e) {
            throw new PersistenceException("Error occurs during generating the phases.", e);
        } catch (SQLException e) {
            throw new PersistenceException("Error occurs during generating the phases.", e);
        } finally {
            // close in finally clause
            Util.closeConnection(connection);
        }
    }

    /**
     * <p>
     * Get the specific field value of the template with the given name, using the provided Connection.
     * </p>
     *
     * @param connection
     *            the Connection instance to work with database
     * @param templateName
     *            the name of the template
     * @param fieldName
     *            the name of the field, can only be the following value:
     *            ID_FIELD, CREATION_DATE_FIELD, CATEGORY_FIELD,
     *            DESCRIPTION_FIELD
     * @return the field value of the template. The concert value type depends
     *         on the fieldName value.
     * @throws SQLException
     *             if any error occurs during executing the statement
     * @throws PersistenceException
     *             if more than one record with the given name were found
     * @throws IllegalArgumentException
     *             if the template with the name can not be found
     */
    private Object getTemplateField(Connection connection, String templateName, String fieldName)
        throws SQLException, PersistenceException {

        // JDBC interface
        PreparedStatement statement = null;
        ResultSet result = null;
        Object value = null;

        try {
            // initialize and execute the query statement
            statement = connection.prepareStatement("SELECT " + fieldName + " FROM template WHERE name=?");
            statement.setString(1, templateName);
            result = statement.executeQuery();

            // check result
            if (result.next()) {
                // get the field value. construct different java class according to the field name
                if (fieldName == ID_FIELD) {
                    // template's id field
                    value = new Long(result.getLong(1));
                } else if (fieldName == CREATION_DATE_FIELD) {
                    // template's creation_date field
                    value = result.getDate(1);
                } else if (fieldName == CATEGORY_FIELD) {
                    // template's category field
                    value = new Integer(result.getInt(1));
                } else {
                    // template's description field
                    value = result.getString(1);
                }

            } else {
                // the template with the given name could not be found
                throw new IllegalArgumentException("The template with the name \"" + templateName
                    + "\" can not be found.");
            }

            if (result.next()) {
                // more than one templates were found
                throw new PersistenceException("More than one templates with the given name were found.");
            }
            return value;

        } finally {
            // close the result set and statement in finally clause
            Util.closeResultAndStatement(statement, result);
        }

    }

    /**
     * <p>
     * Sub program to retrieve a phase's dependency from the database, and add them to the phase.
     * </p>
     *
     * @param connection
     *            the Connection instance to connect the database
     * @param id
     *            the id field of the given phase, also the dependent id
     * @param phase
     *            the phase to add the dependency with
     * @param phases
     *            the Map to retrieve the phase
     * @param leftOutPhaseIds
     *            the IDs of phases to be left out (null or empty if no phases
     *            should be left out)
     * @param dependenciesToLeftOutPhases
     *            the dependencies to left out phases map
     * @param leftOutPhaseIdsSet
     *            The left out phase array
     * @throws SQLException
     *             if error occurs during working with the statement or result
     *             set
     * @throws PhaseGenerationException
     *             if fail to add the dependency to the phase
     */
    private void addDependency(Connection connection, long id, Phase phase, Map<Long, Phase> phases,
        Set<Long> leftOutPhaseIds, Map<Long, List<Dependency>> dependenciesToLeftOutPhases,
        Set<Long> leftOutPhaseIdsSet) throws SQLException, PhaseGenerationException {

        // JDBC interface
        PreparedStatement statement = null;
        ResultSet result = null;

        try {
            // execute the query to get all the phase's dependency
            statement = connection
                .prepareStatement("SELECT dependency_id, dependent_start, dependency_start,"
                    + " lag_time FROM dependency WHERE dependent_id=?");
            statement.setLong(1, id);
            result = statement.executeQuery();

            while (result.next()) {
                // get the fields for each result
                long dependencyId = result.getLong("dependency_id");
                boolean dependentStart = result.getBoolean("dependent_start");
                boolean dependencyStart = result.getBoolean("dependency_start");
                long lagTime = result.getLong("lag_time");

                // get the dependency phase
                Phase dependencyPhase = (Phase) phases.get(dependencyId);

                // if the phase can not be found in the map, then the phase is
                // not contained in the
                // template
                // throw exception is this case
                if (dependencyPhase == null) {
                    throw new PhaseGenerationException("The phase with id " + dependencyId
                        + " is not the phase in the template.");
                }

                Dependency dependency = new Dependency(dependencyPhase, phase, dependencyStart,
                    dependentStart, lagTime);

                // Since 1.2 start
                if (leftOutPhaseIds.contains(dependencyId)) {
                    List<Dependency> dependenciesToLeftOutPhase = dependenciesToLeftOutPhases
                        .get(dependencyId);
                    if (dependenciesToLeftOutPhase == null) {
                        dependenciesToLeftOutPhase = new ArrayList<Dependency>();
                        dependenciesToLeftOutPhases.put(dependencyId, dependenciesToLeftOutPhase);
                    }
                    dependenciesToLeftOutPhase.add(dependency);
                }

                // add the dependency to Current Phase
                if (leftOutPhaseIdsSet.contains(phase.getId()) || !leftOutPhaseIdsSet.contains(dependencyId)) {
                    phase.addDependency(dependency);
                }
                // Since 1.2 end
            }
        } finally {
            // close the result set and statement in finally clause
            Util.closeResultAndStatement(statement, result);
        }
    }

    /**
     * <p>
     * Get the names of the template, or the names of the template belongs to the given category in the
     * database.
     * </p>
     *
     * @param category
     *            the category of the templates. It is used to fill the
     *            parameter in the statement if (index >= 1)
     * @param index
     *            the index of sql statement array GETNAMES_SQL_STATEMENT that
     *            to used to execute
     * @return the names of the template
     * @throws DBConnectionException
     *             if any error occurs when connecting the database
     * @throws SQLException
     *             if any error occurs when working with the statement
     */
    private String[] getTemplateNames(int category, int index) throws DBConnectionException, SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        // Create a database connection with use of DBConnectionFactory.
        connection = Util.createConnection(factory, connectionName);

        try {
            // execute the query
            statement = connection.prepareStatement(GETNAMES_SQL_STATEMENT[index]);
            // fill the parameters
            if (index >= 1) {
                statement.setInt(1, category);
            }
            resultSet = statement.executeQuery();

            // Extract the results. Add the names into the result
            List<String> result = new ArrayList<String>();
            while (resultSet.next()) {
                // get the "name" column
                result.add(resultSet.getString(1));
            }

            // return the result
            return result.toArray(new String[result.size()]);

        } finally {
            // close the result set and statement in finally clause
            closeAll(connection, statement, resultSet);
        }
    }

    /**
     * <p>
     * Delete an entry with the given id from the database.
     * </p>
     *
     * @param connection
     *            the jdbc connection to work with
     * @param id
     *            the id of the entry to be deleted
     * @param script
     *            the sql statement to use to execute
     * @throws SQLException
     *             if it fail to execute the statement
     */
    private static void deleteEntry(Connection connection, long id, String script) throws SQLException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(script);
            statement.setLong(1, id);
            statement.executeUpdate();
        } finally {
            Util.closeStatement(statement);
        }
    }

    /**
     * <p>
     * Helper method to close the JDBC interface(result set, statement, connection).
     * </p>
     *
     * @param connection
     *            the connection to be closed, could be null
     * @param statement
     *            the statement to be closed, could be null
     * @param resultSet
     *            the result set to be closed, could be null
     * @throws SQLException
     *             if any error occurs in this method
     */
    private static void closeAll(Connection connection, Statement statement, ResultSet resultSet) throws SQLException {
        try {
            Util.closeResultAndStatement(statement, resultSet);
        } finally {
            Util.closeConnection(connection);
        }
    }
}
