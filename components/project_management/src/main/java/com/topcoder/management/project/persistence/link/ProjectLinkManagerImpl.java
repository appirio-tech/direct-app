/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project.persistence.link;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.management.project.ConfigurationException;
import com.topcoder.management.project.PersistenceException;
import com.topcoder.management.project.Project;
import com.topcoder.management.project.ProjectManager;
import com.topcoder.management.project.persistence.link.Helper.DataType;
import com.topcoder.management.project.persistence.logging.LogMessage;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;
import com.topcoder.management.project.link.ProjectLinkManager;
import com.topcoder.management.project.link.ProjectLink;
import com.topcoder.management.project.link.ProjectLinkType;
import com.topcoder.management.project.link.ProjectLinkCycleException;


/**
 * <p>
 * Project link manager. It handles persistence operations for project link as well as project link type. It currently
 * relies on the project manager to get <code>Project</code> entities.
 * </p>
 * <p>
 * It is created for "OR Project Linking" assembly.
 * </p>
 * <p>
 * Changes in v1.1 (Cockpit Spec Review Backend Service Update v1.0):
 * - added flag so that container transaction demarcation can be used.
 * Change log for version 1.1: Updated the code for initializing the retrieved project link types with value of
 * allow_overlap flag; update {@link #updateProjectLinks(long, long[], long[])} method to validate the project links
 * against cycle.
 * </p>
 *
 * @author BeBetter, pulky
 * @version 1.1
 */
public class ProjectLinkManagerImpl implements ProjectLinkManager {
    /**
     * <p>
     * Logger instance using the class name as category.
     * </p>
     */
    private static final Log LOGGER = LogManager.getLog(ProjectLinkManagerImpl.class.getName());

    /**
     * <p>
     * Represents the name of connection name parameter in configuration.
     * </p>
     */
    private static final String CONNECTION_NAME_PARAMETER = "ConnectionName";

    /**
     * <p>
     * Represents the name of connection factory namespace parameter in configuration.
     * </p>
     */
    private static final String CONNECTION_FACTORY_NAMESPACE_PARAMETER = "ConnectionFactoryNS";

    /**
     * <p>
     * The SQL to get all project link types.
     * </p>
     */
    private static final String QUERY_ALL_PROJECT_LINK_TYPES_SQL = "SELECT link_type_id, link_type_name, allow_overlap "
        + " FROM link_type_lu";

    /**
     * <p>
     * Column types for return row of above SQL.
     * </p>
     */
    private static final DataType[] QUERY_ALL_PROJECT_LINK_TYPES_COLUMN_TYPES = new DataType[] {Helper.LONG_TYPE,
        Helper.STRING_TYPE, Helper.STRING_TYPE};

    /**
     * <p>
     * The SQL to get all destination project links given source project id.
     * </p>
     */
    private static final String QUERY_DEST_PROJECT_LINK_SQL = "SELECT dest_project_id, link_type_id "
        + " FROM linked_project_xref WHERE source_project_id = ?";

    /**
     * <p>
     * Column types for return row of above SQL.
     * </p>
     */
    private static final DataType[] QUERY_DEST_PROJECT_LINK_COLUMN_TYPES = new DataType[] {Helper.LONG_TYPE,
        Helper.LONG_TYPE};

    /**
     * <p>
     * The SQL to get all source project links for the given destination project id.
     * </p>
     */
    private static final String QUERY_SOURCE_PROJECT_LINK_SQL = "SELECT source_project_id, link_type_id "
        + " FROM linked_project_xref WHERE dest_project_id = ?";

    /**
     * <p>
     * Column types for return row of above SQL.
     * </p>
     */
    private static final DataType[] QUERY_SOURCE_PROJECT_LINK_COLUMN_TYPES = new DataType[] {Helper.LONG_TYPE,
        Helper.LONG_TYPE};

    /**
     * <p>
     * The SQL to delete project links by source project id.
     * </p>
     */
    private static final String DELETE_PROJECT_LINK_BY_SOURCE_RPOJECT_ID = "DELETE FROM linked_project_xref "
        + "WHERE source_project_id = ?";

    /**
     * <p>
     * The SQL to insert project link.
     * </p>
     */
    private static final String INSERT_PROJECT_LINK = "INSERT INTO linked_project_xref"
        + "(source_project_id,dest_project_id,link_type_id) VALUES(?,?,?)";

    /**
     * <p>
     * The SQL to delete project link
     * </p>
     */
    private static final String DELETE_PROJECT_LINK = "DELETE FROM linked_project_xref "
        + "WHERE source_project_id = ? and dest_project_id = ? and link_type_id = ? ";

    /**
     * <p>
     * The factory instance used to create connection to the database. It is initialized in the constructor using
     * DBConnectionFactory component and never changed after that. It will be used in various persistence methods of
     * this project.
     * </p>
     */
    private final DBConnectionFactory factory;

    /**
     * <p>
     * Represents the database connection name that will be used by DBConnectionFactory. This variable is initialized
     * in the constructor and never changed after that. It will be used in create/update/retrieve method to create
     * connection. This variable can be null, which mean connection name is not defined in the configuration namespace
     * and default connection will be created.
     * </p>
     */
    private final String connectionName;

    /**
     * <p>
     * Represents whether this component should use manual commit or not.
     * </p>
     *
     * @since 1.1
     */
    private final Boolean useManualCommit = false;

    /**
     * <p>
     * The project manager. It will delegate some project related queries into project manager.
     * </p>
     */
    private final ProjectManager projectManager;

    /**
     * <p>
     * The default constructor for project link manager.
     * </p>
     *
     * @param projectManager the project manager
     * @throws IllegalArgumentException if the project manager is null
     * @throws ConfigurationException if error occurs while loading configuration settings, or required configuration
     *             parameter is missing.
     */
    public ProjectLinkManagerImpl(ProjectManager projectManager) throws ConfigurationException {
        this(ProjectLinkManagerImpl.class.getName(), projectManager);
    }

    /**
     * <p>
     * The constructor for project link manager.
     * </p>
     *
     * @param namespace the name space value. It should not be null or empty
     * @param projectManager the project manager
     * @throws IllegalArgumentException if the namespace is null or empty string or the project manager is null
     * @throws ConfigurationException if error occurs while loading configuration settings, or required configuration
     *             parameter is missing.
     */
    public ProjectLinkManagerImpl(String namespace, ProjectManager projectManager) throws ConfigurationException {
        Helper.assertStringNotNullNorEmpty(namespace, "namespace");
        Helper.assertObjectNotNull(projectManager, "projectManager");

        this.projectManager = projectManager;

        // get the instance of ConfigManager
        ConfigManager cm = ConfigManager.getInstance();

        // get db connection factory namespace
        String factoryNamespace = Helper.getConfigurationParameterValue(cm, namespace,
            CONNECTION_FACTORY_NAMESPACE_PARAMETER, true, LOGGER);

        // try to create a DBConnectionFactoryImpl instance from
        // factoryNamespace
        try {
            factory = new DBConnectionFactoryImpl(factoryNamespace);
        } catch (Exception e) {
            throw new ConfigurationException("Unable to create a new instance of DBConnectionFactoryImpl class"
                + " from namespace [" + factoryNamespace + "].", e);
        }

        // get the connection name
        connectionName = Helper.getConfigurationParameterValue(cm, namespace, CONNECTION_NAME_PARAMETER, false,
            LOGGER);

    }

    /**
     * <p>
     * Gets all project link types.
     * </p>
     *
     * @return all project link types
     * @throws PersistenceException if any persistence error occurs
     */
    public ProjectLinkType[] getAllProjectLinkTypes() throws PersistenceException {
        Connection conn = null;

        LOGGER.log(Level.INFO, new LogMessage(null, null, "Enter getAllProjectLinkTypes method."));
        try {
            // create the connection
            conn = openConnection();

            // get all the project link types
            ProjectLinkType[] projectLinkTypes = getAllProjectLinkTypes(conn);
            closeConnection(conn);
            return projectLinkTypes;
        } catch (PersistenceException e) {
            LOGGER.log(Level.ERROR, new LogMessage(null, null, "Fail to getAllProjectLinkTypes.", e));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw e;
        }
    }

    /**
     * <p>
     * Gets all project link types.
     * </p>
     *
     * @param conn the db connection
     * @return all project link types
     * @throws PersistenceException if any persistence error occurs
     */
    private ProjectLinkType[] getAllProjectLinkTypes(Connection conn) throws PersistenceException {
        // find all project types in the table.
        Object[][] rows = Helper.doQuery(conn, QUERY_ALL_PROJECT_LINK_TYPES_SQL, new Object[] {},
            QUERY_ALL_PROJECT_LINK_TYPES_COLUMN_TYPES);

        // create the ProjectLinkType array.
        ProjectLinkType[] projectLinkTypes = new ProjectLinkType[rows.length];

        for (int i = 0; i < rows.length; ++i) {
            Object[] row = rows[i];

            // create a new instance of ProjectLinkType class
            projectLinkTypes[i] = new ProjectLinkType(((Long) row[0]).longValue(), (String) row[1], 
                                                      Boolean.valueOf("1".equals((String) row[2])));
        }

        return projectLinkTypes;
    }

    /**
     * <p>
     * Gets all project links based on source project id.
     * </p>
     *
     * @param sourceProjectId source project id
     * @return all project link types
     * @throws PersistenceException if any persistence error occurs
     */
    public ProjectLink[] getDestProjectLinks(long sourceProjectId) throws PersistenceException {
        Connection conn = null;

        LOGGER.log(Level.INFO, new LogMessage(null, null, "Enter getDestProjectLinks method."));
        try {
            // create the connection
            conn = openConnection();

            // get all the project link types
            ProjectLink[] projectLinks = getDestProjectLinks(sourceProjectId, conn);
            closeConnection(conn);
            return projectLinks;
        } catch (PersistenceException e) {
            LOGGER.log(Level.ERROR, new LogMessage(null, null, "Fail to getDestProjectLinks.", e));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw e;
        }
    }

    /**
     * <p>
     * It is internal method and it gets all project links based on source project id.
     * </p>
     *
     * @param sourceProjectId source project id
     * @param conn the db connection
     * @return all project link types
     * @throws PersistenceException if any persistence error occurs
     */
    private ProjectLink[] getDestProjectLinks(long sourceProjectId, Connection conn) throws PersistenceException {
        Object[][] rows = Helper.doQuery(conn, QUERY_DEST_PROJECT_LINK_SQL, new Object[] {new Long(sourceProjectId)},
            QUERY_DEST_PROJECT_LINK_COLUMN_TYPES);

        ProjectLink[] projectLinks = new ProjectLink[rows.length];

        long[] ids = new long[rows.length + 1];
        ids[0] = sourceProjectId;
        for (int i = 0; i < rows.length; ++i) {
            Object[] row = rows[i];

            // create a new instance of ProjectLink class
            projectLinks[i] = new ProjectLink();
            // we will only provide link type id
            projectLinks[i].setType(new ProjectLinkType());
            projectLinks[i].getType().setId((Long) row[1]);
            ids[i + 1] = (Long) row[0];
        }

        // get Project objects
        Project[] projects = projectManager.getProjects(ids);
        Project sourceProject = projects[0];
        for (int i = 0; i < projectLinks.length; i++) {
            projectLinks[i].setSourceProject(sourceProject);
            projectLinks[i].setDestProject(projects[i + 1]);
        }

        fillLinkTypes(projectLinks, conn);

        return projectLinks;
    }

    /**
     * <p>
     * Gets all project links based on destination project id.
     * </p>
     *
     * @param destProjectId destination project id
     * @return all project link types
     * @throws PersistenceException if any persistence error occurs
     */
    public ProjectLink[] getSourceProjectLinks(long destProjectId) throws PersistenceException {
        Connection conn = null;

        LOGGER.log(Level.INFO, new LogMessage(null, null, "Enter getSourceProjectLinks method."));
        try {
            // create the connection
            conn = openConnection();

            // get all the project link types
            ProjectLink[] projectLinks = getSourceProjectLinks(destProjectId, conn);
            closeConnection(conn);
            return projectLinks;
        } catch (PersistenceException e) {
            LOGGER.log(Level.ERROR, new LogMessage(null, null, "Fail to getSourceProjectLinks.", e));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw e;
        }
    }

    /**
     * <p>
     * It is internal method and it gets all project links based on destination project id.
     * </p>
     *
     * @param destProjectId destination project id
     * @param conn the db connection
     * @return all project link types
     * @throws PersistenceException if any persistence error occurs
     */
    private ProjectLink[] getSourceProjectLinks(long destProjectId, Connection conn) throws PersistenceException {
        Object[][] rows = Helper.doQuery(conn, QUERY_SOURCE_PROJECT_LINK_SQL, new Object[] {new Long(destProjectId)},
            QUERY_SOURCE_PROJECT_LINK_COLUMN_TYPES);

        ProjectLink[] projectLinks = new ProjectLink[rows.length];

        long[] ids = new long[rows.length + 1];
        ids[0] = destProjectId;
        for (int i = 0; i < rows.length; ++i) {
            Object[] row = rows[i];

            // create a new instance of ProjectLink class
            projectLinks[i] = new ProjectLink();
            // we will only provide link type id
            projectLinks[i].setType(new ProjectLinkType());
            projectLinks[i].getType().setId((Long) row[1]);
            ids[i + 1] = (Long) row[0];
        }

        // get Project objects
        Project[] projects = projectManager.getProjects(ids);
        Project destProject = projects[0];
        for (int i = 0; i < projectLinks.length; i++) {
            projectLinks[i].setSourceProject(projects[i + 1]);
            projectLinks[i].setDestProject(destProject);
        }

        fillLinkTypes(projectLinks, conn);

        return projectLinks;
    }

    /**
     * <p>
     * Fills full blown link type information.
     * </p>
     *
     * @param projectLinks project links to be filled
     * @param conn db the connection
     * @throws PersistenceException if any persistence error occurs
     */
    private void fillLinkTypes(ProjectLink[] projectLinks, Connection conn) throws PersistenceException {
        ProjectLinkType[] allTypes = getAllProjectLinkTypes(conn);

        Map<Long, ProjectLinkType> typeMap = new HashMap<Long, ProjectLinkType>();
        for (ProjectLinkType type : allTypes) {
            typeMap.put(type.getId(), type);
        }

        for (ProjectLink link : projectLinks) {
            link.setType(typeMap.get(link.getType().getId()));
        }
    }

    /**
     * <p>
     * Updates project links for given source project id. It will delete all old links and use passed in project
     * links. There are 2 arrays passed in, one is for destination project ids and other for link type ids. The id at
     * the same position in each array represents a project link information.
     * </p>
     *
     * @param sourceProjectId the source project id
     * @param destProjectIds the destination project ids
     * @param linkTypeIds the type ids
     * @throws IllegalArgumentException if any array is null or it is not equal in length for dest project id array
     *             and link type array
     * @throws PersistenceException if any persistence error occurs
     * @throws ProjectLinkCycleException if there is a cycle detected in the project links.
     */
    public void updateProjectLinks(long sourceProjectId, long[] destProjectIds, long[] linkTypeIds)
        throws PersistenceException {
        LOGGER.log(Level.INFO, new LogMessage(null, null, "Enter updateProjectLinks method."));
        Helper.assertObjectNotNull(destProjectIds, "destProjectIds");
        Helper.assertObjectNotNull(linkTypeIds, "linkTypeIds");
        if (destProjectIds.length != linkTypeIds.length) {
            throw new IllegalArgumentException("destProjectIds must have same length as linkTypeIds");
        }

        checkForCycle(sourceProjectId, destProjectIds, linkTypeIds);

        // Update the links
        Connection conn = null;

        try {
            // create the connection
            conn = openConnection();

            // refresh links
            updateProjectLinks(sourceProjectId, destProjectIds, linkTypeIds, conn);
            closeConnection(conn);
        } catch (PersistenceException e) {
            LOGGER.log(Level.ERROR, new LogMessage(null, null, "Fail to updateProjectLinks.", e));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw e;
        }
    }

    /**
     * <p>Checks if specified project is a part of cycle in project dependencies.</p>
     *
     * @param sourceProjectId a <code>long</code> providing the ID of a project.
     * @throws PersistenceException if any persistence error occurs.
     * @throws ProjectLinkCycleException if there is a cycle detected in the project links.
     */
    public void checkForCycle(long sourceProjectId) throws PersistenceException {
        ProjectLink[] destProjectLinks = getDestProjectLinks(sourceProjectId);
        long[] destProjectIds = new long[destProjectLinks.length];
        long[] linkTypeIds = new long[destProjectLinks.length];

        for (int i = 0; i < destProjectLinks.length; i++) {
            ProjectLink link = destProjectLinks[i];
            destProjectIds[i] = link.getDestProject().getId();
            linkTypeIds[i] = link.getType().getId(); 
        }
        
        checkForCycle(sourceProjectId, destProjectIds, linkTypeIds);
    }

    /**
     * <p>
     * Add a new project link
     * </p>
     *
     * @param sourceProjectId the source project id
     * @param destProjectId the destination project id
     * @param linkTypeId the type id
     * @throws IllegalArgumentException if any array is null or it is not equal in length for dest project id array
     *             and link type array
     * @throws PersistenceException if any persistence error occurs
     */
    public void addProjectLink(long sourceProjectId, long destProjectId, long linkTypeId)
        throws PersistenceException {
        LOGGER.log(Level.INFO, new LogMessage(null, null, "Enter updateProjectLinks method."));

        Connection conn = null;

        try {
            // create the connection
            conn = openConnection();

            // add link
            addProjectLink(sourceProjectId, destProjectId, linkTypeId, conn);
            closeConnection(conn);
        } catch (PersistenceException e) {
            LOGGER.log(Level.ERROR, new LogMessage(null, null, "Fail to updateProjectLinks.", e));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw e;
        }
    }


    /**
     * <p>
     * Remvoe a new project link
     * </p>
     *
     * @param sourceProjectId the source project id
     * @param destProjectId the destination project id
     * @param linkTypeId the type id
     * @throws IllegalArgumentException if any array is null or it is not equal in length for dest project id array
     *             and link type array
     * @throws PersistenceException if any persistence error occurs
     */
    public void removeProjectLink(long sourceProjectId, long destProjectId, long linkTypeId)
        throws PersistenceException {
        LOGGER.log(Level.INFO, new LogMessage(null, null, "Enter updateProjectLinks method."));

        Connection conn = null;

        try {
            // create the connection
            conn = openConnection();

            // add link
            removeProjectLink(sourceProjectId, destProjectId, linkTypeId, conn);
            closeConnection(conn);
        } catch (PersistenceException e) {
            LOGGER.log(Level.ERROR, new LogMessage(null, null, "Fail to updateProjectLinks.", e));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw e;
        }
    }

    
    /**
     * <p>Checks if specified project referring to specified projects is a part of a cycle.</p>
     *
     * @param sourceProjectId the source project id.
     * @param destProjectIds the destination project ids.
     * @param linkTypeIds the type ids.
     * @throws PersistenceException if any persistence error occurs.
     * @throws ProjectLinkCycleException if there is a cycle detected in the project links.
     */
    private void checkForCycle(long sourceProjectId, long[] destProjectIds, long[] linkTypeIds)
        throws PersistenceException {

        // Build project link types map
        Map<Long, ProjectLinkType> typesMap = new HashMap<Long, ProjectLinkType>();
        ProjectLinkType[] types = getAllProjectLinkTypes();
        for (int i = 0; i < linkTypeIds.length; i++) {
            long linkTypeId = linkTypeIds[i];
            for (int j = 0; j < types.length; j++) {
                ProjectLinkType type = types[j];
                if (type.getId() == linkTypeId) {
                    typesMap.put(linkTypeId, type);
                    break;
                }
            }
        }

        // Validate for cycles using BFS algorithm

        // Current candidates for cycle
        Project currentProject = this.projectManager.getProject(sourceProjectId);
        Map<Long, LinkedList<Project>> cycles = new HashMap<Long, LinkedList<Project>>();

        // A list of projects to be visited
        LinkedHashMap<Long, Project> open = new LinkedHashMap<Long, Project>();
        for (int i = 0; i < destProjectIds.length; i++) {
            long linkTypeId = linkTypeIds[i];
            ProjectLinkType type = typesMap.get(linkTypeId);
            if (type != null) {
                if (!type.isAllowOverlap()) {
                    open.put(destProjectIds[i], this.projectManager.getProject(destProjectIds[i]));
                    cycles.put(destProjectIds[i],
                               new LinkedList(Arrays.asList(currentProject)));
                }
            }
        }

        // A list of projects already visited
        Map<Long, Project> closed = new HashMap<Long, Project>();
        closed.put(sourceProjectId, currentProject);

        // Do BFS
        while (!open.isEmpty()) {
            Iterator<Map.Entry<Long,Project>> iterator = open.entrySet().iterator();
            Project project = iterator.next().getValue();
            iterator.remove();
            
            Long projectId = project.getId();

            if (!closed.containsKey(projectId)) {
                closed.put(projectId, project);
                ProjectLink[] links = getDestProjectLinks(projectId);
                for (int i = 0; i < links.length; i++) {
                    ProjectLink link = links[i];
                    long nextProjectId = link.getDestProject().getId();
                    if (!link.getType().isAllowOverlap()) {
                        if (!closed.containsKey(nextProjectId)) {
                            if (!open.containsKey(nextProjectId)) {
                                open.put(nextProjectId, link.getDestProject());
                                if (cycles.containsKey(nextProjectId)) {
                                    cycles.get(nextProjectId).add(project);
                                } else {
                                    LinkedList<Project> currentCycle = cycles.get(projectId);
                                    LinkedList newCycle = new LinkedList(currentCycle);
                                    newCycle.add(project);
                                    cycles.put(nextProjectId, newCycle);
                                }
                            }
                        } else {
                            LinkedList<Project> cycle = cycles.get(projectId);
                            cycle.addLast(project);
                            throw new ProjectLinkCycleException(cycle);
                        }
                    }
                }
            } else {
                throw new ProjectLinkCycleException(cycles.get(projectId));
            }

            closed.put(project.getId(), project);
        }
    }

    /**
     * <p>
     * It is internal method and it updates the project links for given source project id.
     * </p>
     *
     * @param sourceProjectId the source project id
     * @param destProjectIds the destination project ids
     * @param linkTypeIds the type ids
     * @param conn the db connection
     * @throws PersistenceException if any persistence error occurs
     */
    private void updateProjectLinks(long sourceProjectId, long[] destProjectIds, long[] linkTypeIds, Connection conn)
        throws PersistenceException {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(DELETE_PROJECT_LINK_BY_SOURCE_RPOJECT_ID);
            ps.setLong(1, sourceProjectId);
            ps.executeUpdate();
            Helper.closeStatement(ps);

            ps = conn.prepareStatement(INSERT_PROJECT_LINK);
            for (int i = 0; i < destProjectIds.length; i++) {
                int idx = 1;
                ps.setLong(idx++, sourceProjectId);
                ps.setLong(idx++, destProjectIds[i]);
                ps.setLong(idx++, linkTypeIds[i]);
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) {
            throw new PersistenceException("Error occurs while executing queries for update project links. ", e);
        } finally {
            Helper.closeStatement(ps);
        }
    }


    /**
     * <p>
     * It is internal method and it add the project link for given source project id.
     * </p>
     *
     * @param sourceProjectId the source project id
     * @param destProjectId the destination project id
     * @param linkTypeId the type id
     * @param conn the db connection
     * @throws PersistenceException if any persistence error occurs
     */
    private void addProjectLink(long sourceProjectId, long destProjectId, long linkTypeId, Connection conn)
        throws PersistenceException {
        PreparedStatement ps = null;
        try {

            ps = conn.prepareStatement(INSERT_PROJECT_LINK);

            int idx = 1;
            ps.setLong(idx++, sourceProjectId);
            ps.setLong(idx++, destProjectId);
            ps.setLong(idx++, linkTypeId);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new PersistenceException("Error occurs while executing queries for adding project link. ", e);
        } finally {
            Helper.closeStatement(ps);
        }
    }


    /**
     * <p>
     * It is internal method and it remove the project link for given source project id.
     * </p>
     *
     * @param sourceProjectId the source project id
     * @param destProjectId the destination project id
     * @param linkTypeId the type id
     * @param conn the db connection
     * @throws PersistenceException if any persistence error occurs
     */
    private void removeProjectLink(long sourceProjectId, long destProjectId, long linkTypeId, Connection conn)
        throws PersistenceException {
        PreparedStatement ps = null;
        try {

            ps = conn.prepareStatement(DELETE_PROJECT_LINK);

            int idx = 1;
            ps.setLong(idx++, sourceProjectId);
            ps.setLong(idx++, destProjectId);
            ps.setLong(idx++, linkTypeId);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new PersistenceException("Error occurs while executing queries for removing project link. ", e);
        } finally {
            Helper.closeStatement(ps);
        }
    }

    /**
     * <p>
     * It is internal method and it updates the project links for given source project id.
     * </p>
     *
     * @param sourceProjectId the source project id
     * @param destProjectIds the destination project ids
     * @param linkTypeIds the type ids
     * @param conn the db connection
     * @throws PersistenceException if any persistence error occurs
     */
    private void addProjectLinks(long sourceProjectId, long[] destProjectIds, long[] linkTypeIds, Connection conn)
        throws PersistenceException {
        PreparedStatement ps = null;
        try {

            ps = conn.prepareStatement(INSERT_PROJECT_LINK);
            for (int i = 0; i < destProjectIds.length; i++) {
                int idx = 1;
                ps.setLong(idx++, sourceProjectId);
                ps.setLong(idx++, destProjectIds[i]);
                ps.setLong(idx++, linkTypeIds[i]);
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) {
            throw new PersistenceException("Error occurs while executing queries for add project links. ", e);
        } finally {
            Helper.closeStatement(ps);
        }
    }

    /**
     * Returns the database connection name that will be used by DB Connection Factory.
     *
     * @return a possibly <code>null</code> string representing the connection name used in DB Connection Factory.
     */
    public String getConnectionName() {
        return connectionName;
    }

    /**
     * Returns the <code>DBConnectionFactory</code> instance used to create connection to the database.
     *
     * @return the <code>DBConnectionFactory</code> instance used to create connection to the database.
     */
    public DBConnectionFactory getConnectionFactory() {
        return factory;
    }

    /**
     * <p>
     * It uses the DB connection factory to create the connection to underlying database. If the connection is not
     * configured, the default connection from DB connection factory will be created, otherwise, the connection with
     * the specified name in DB connection factory will be created.
     * </p>
     * <p>
     * Once the connection is retrieved, the auto commit property will be set false to manage the transaction itself.
     * </p>
     *
     * @return an open Connection to underlying database.
     * @throws PersistenceException if there's a problem getting the Connection
     */
    private Connection openConnection() throws PersistenceException {
        if (connectionName == null) {
            LOGGER
                .log(Level.INFO, new LogMessage(null, null, "creating db connection using default connection"));
        } else {
            LOGGER.log(Level.INFO,
                new LogMessage(null, null, "creating db connection using connection name: " + connectionName));
        }
        Connection conn = Helper.createConnection(getConnectionFactory(), connectionName);
        try {
            if(useManualCommit) {
                conn.setAutoCommit(false);
            }
            return conn;
        } catch (SQLException e) {
            throw new PersistenceException("Error occurs when setting "
                + (connectionName == null ? "the default connection" : ("the connection '" + connectionName + "'"))
                + " to support transaction.", e);
        }

    }

    /**
     * <p>
     * It is used to commit the transaction and close the connection after an operation successfully completes.
     * </p>
     *
     * @param connection a Connection to close
     * @throws PersistenceException if any problem occurs trying to close the connection
     * @throws IllegalArgumentException if the argument is null
     */
    protected void closeConnection(Connection connection) throws PersistenceException {
        Helper.assertObjectNotNull(connection, "connection");
        try {
            if(useManualCommit) {
                LOGGER.log(Level.INFO, "committing transaction");
                Helper.commitTransaction(connection);
            }
        } finally {
            Helper.closeConnection(connection);
        }

    }

    /**
     * <p>
     * It is used to rollback the transaction and close the connection after an operation fails to complete.
     * </p>
     *
     * @param connection a connection to close
     * @throws IllegalArgumentException if the argument is null
     * @throws PersistenceException if any problem occurs trying to close the connection
     */
    protected void closeConnectionOnError(Connection connection) throws PersistenceException {
        Helper.assertObjectNotNull(connection, "connection");
        try {
            if(useManualCommit) {
                LOGGER.log(Level.INFO, "rollback transaction");
                Helper.rollBackTransaction(connection);
            }
        } finally {
            Helper.closeConnection(connection);
        }
    }
}
