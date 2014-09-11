/*
 * Copyright (C) 2007 - 2011 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.management.phase.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.topcoder.date.workdays.DefaultWorkdaysFactory;
import com.topcoder.date.workdays.DefaultWorkdays;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.management.phase.AbstractDbPhasePersistence;
import com.topcoder.management.phase.ConfigurationException;
import com.topcoder.management.phase.PhasePersistenceException;
import com.topcoder.project.phases.Dependency;
import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.PhaseStatus;
import com.topcoder.project.phases.PhaseType;
import com.topcoder.project.phases.Project;
import com.topcoder.util.idgenerator.IDGenerationException;
import com.topcoder.util.idgenerator.IDGenerator;

/**
 * <p>
 * This is an abstract class specifically implementing the Informix based SQL to
 * achieve the persistence API implementation.
 * </p>
 * <p>
 * The specifics of <code>startTrasnaction(Map)</code>,
 * <code>commitTransaction(Map)</code>, <code>rollbackTransaction(Map)</code>,
 * and <code>disposeConnection(Connection)</code> will be dealt with by
 * classes that will implement this particular class, which might be implemented
 * in different ways depending on different strategies.
 * </p>
 * <p>
 * Currently, the <code>InformixPhasePersistence</code> descendant class
 * implements these methods to do self-manager transactional control, but the
 * <code>UnmanagedTrasnactionsInformixPhasePersistence</code> is designed to
 * not manager any transactional aspects of the persistence actions.
 * </p>
 * <p>
 * This is an actual implementation of database persistence that is geared for
 * Informix database.
 * </p>
 * <p>
 * <strong>Transactional Issues and Connection Handling:</strong>
 * </p>
 * <p>
 * In the transaction related APIs like createPhase, the database operations are
 * done as below to make the specific extending class decides how to handle the
 * transaction.
 * <ol>
 * <li>obtain their connections through the getConnection() method.</li>
 * <li>start the transaction through startTransaction(Map) method to start the
 * transaction defined by the extending class.</li>
 * <li>do the action</li>
 * <li>If there is no problem, call the commitTransaction(Map) method to do the
 * 'commit' operation defined by extending class.</li>
 * <li>If there is any problem, call the rollbackTransaction(Map) method to do
 * the 'rollback' operation defined by extending class.</li>
 * <li>Dispose of the connection by calling the disposeConnection(Connection)
 * method.</li>
 * </ol>
 * </p>
 * <p>
 * Auditing Operator audit is based on simply filling in the create_user,
 * create_date, modify_user, and modify_date field for each create and update
 * operation on any of the provided tables. When creating an entry the above
 * columns will be fill out with actual data. When updating, only the update
 * user and update timestamp will be set.
 * </p>
 * <p>
 * <b>Thread Safety:</b> <code>AbstractInformixPhasePersistence</code> acts
 * like a stateless bean with utility-like functionality where function calls
 * retain no state from one call to the next. Separate connections are created
 * each time a call is made and thus (assuming the connections are different)
 * there is no contention for a connection from competing threads. This
 * implementation is thread-safe. Any descendants should try to ensure
 * thread-safety.
 * </p>
 * 
 * <p>
 * Version 1.1.3 (TC Direct Replatforming Release 2) Change notes:
 * <ul>
 * <li>Added {@link #DELETE_PHASE_AUDIT_FOR_PHASES} field to delete records in project_phase_audit of
 * a specific phase.</li>
 * <li>Update {@link #deletePhases(Phase[])} to delete the phase audit when deleting a phase.
 * If we don't delete the phase audit, we can't delete the phase because the foreign key.</li>
 * <li>Update {@link #deletePhases(Phase[])} to don't insert a record to project_phase_audit because
 * the foreign key.</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 1.2 (First2Finish - TC Cockpit Auto Assign Reviewer Update)
 * <ul>
 *     <li>Updated {@link #updatePhases(com.topcoder.project.phases.Phase[], String)} to remove the comment of
 *     statement 'updatePhaseCriteria(conn, phases[i], operator, lookUps);' </li>
 * </ul>
 * </p>
 * 
 * @author AleaActaEst, kr00tki, GreatKevin
 * @version 1.2
 */
public abstract class AbstractInformixPhasePersistence extends
        AbstractDbPhasePersistence {

    /**
     * Checks if the dependency exists in the database.
     */
    private static final String CHECK_DEPENDENCY = "SELECT 1 FROM phase_dependency WHERE dependency_phase_id = ? "
            + "AND dependent_phase_id = ?";

    /**
     * The second part of the delete query for the dependencies.
     */
    private static final String DELETE_FROM_PHASE_DEPENDENCY_OR = " OR dependent_phase_id IN ";

    /**
     * Delete the dependencies for particular phase.
     */
    private static final String DELETE_FROM_PHASE_DEPENDENCY = "DELETE FROM phase_dependency WHERE "
            + "dependency_phase_id IN ";

    /**
     * Delete the phase criteria for the given phases.
     */
    private static final String DELETE_PHASE_CRITERIA_FOR_PHASES = "DELETE FROM phase_criteria "
            + "WHERE project_phase_id IN ";

    /**
     * Delete the phase audit for the given phases.
     *
     * @since 1.1.3
     */
    private static final String DELETE_PHASE_AUDIT_FOR_PHASES = "DELETE FROM project_phase_audit "
            + "WHERE project_phase_id IN ";

    /**
     * Delete the project phases given by id list.
     */
    private static final String DELETE_PROJECT_PHASE = "DELETE FROM project_phase WHERE project_phase_id IN ";

    /**
     * Select the complete phase criteria for a phases.
     */
    private static final String SELECT_PHASE_CRITERIA_FOR_PHASE = "SELECT phase_criteria.phase_criteria_type_id, "
            + "name, parameter FROM phase_criteria JOIN phase_criteria_type_lu "
            + "ON phase_criteria_type_lu.phase_criteria_type_id = phase_criteria.phase_criteria_type_id "
            + "WHERE project_phase_id = ?";

    /**
     * Update the phase criteria.
     */
    private static final String UPDATE_PHASE_CRITERIA = "UPDATE phase_criteria SET parameter = ?, "
            + "modify_user = ?, modify_date = ? WHERE project_phase_id = ? AND phase_criteria_type_id = ?";

    /**
     * Delete the criteria for phase.
     */
    private static final String DELETE_PHASE_CRITERIA = "DELETE FROM phase_criteria "
            + "WHERE project_phase_id = ? AND phase_criteria_type_id IN ";

    /**
     * Deletes the concrete dependecies for a phase.
     */
    private static final String DELETE_PHASE_DEPENDENCY = "DELETE FROM phase_dependency "
            + "WHERE dependent_phase_id = ? AND dependency_phase_id IN ";

    /**
     * Updates the phases dependencies.
     */
    private static final String UPDATE_PHASE_DEPENDENCY = "UPDATE phase_dependency "
            + "SET dependency_start = ?, dependent_start = ?, lag_time = ?, modify_user = ?, modify_date = ? "
            + "WHERE dependency_phase_id = ? AND dependent_phase_id = ?";

    /**
     * Selects the phase criteria for phases.
     */
    private static final String SELECT_PHASE_CRITERIA_FOR_PROJECTS = "SELECT phase_criteria.project_phase_id, name, "
            + "parameter FROM phase_criteria JOIN phase_criteria_type_lu "
            + "ON phase_criteria_type_lu.phase_criteria_type_id = phase_criteria.phase_criteria_type_id "
            + "JOIN project_phase ON phase_criteria.project_phase_id = project_phase.project_phase_id "
            + "WHERE project_id IN ";

    /**
     * Select the project id - phase id mappings.
     */
    private static final String SELECT_PROJECT_PHASE_ID = "SELECT project_phase_id, project_id FROM project_phase "
            + "WHERE project_phase_id IN ";

    /**
     * Select all the criteria id and name.
     */
    private static final String SELECT_PHASE_CRITERIA = "SELECT phase_criteria_type_id, name "
            + "FROM phase_criteria_type_lu";

    /**
     * Inserts the phase criteria into table.
     */
    private static final String INSERT_PHASE_CRITERIA = "INSERT INTO phase_criteria(project_phase_id, "
            + "phase_criteria_type_id, parameter, create_user, create_date, modify_user, modify_date) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?)";

    /**
     * Updates the phase table.
     */
    private static final String UPDATE_PHASE = "UPDATE project_phase SET project_id = ?, phase_type_id = ?, "
            + "phase_status_id = ?, fixed_start_time = ?, scheduled_start_time = ?, scheduled_end_time = ?, "
            + "actual_start_time = ?, actual_end_time = ?, duration = ?, modify_user = ?, modify_date = ? "
            + "WHERE project_phase_id = ?";

    /**
     * Selects all dependencies for phase.
     */
    private static final String SELECT_DEPENDENCY = "SELECT dependency_phase_id, dependent_phase_id, "
            + "dependency_start, dependent_start, lag_time FROM phase_dependency WHERE dependent_phase_id = ?";

    /**
     * Selects all dependencies for projects.
     */
    private static final String SELECT_DEPENDENCY_FOR_PROJECTS = "SELECT dependency_phase_id, dependent_phase_id, "
            + "dependency_start, dependent_start, lag_time FROM phase_dependency "
            + "JOIN project_phase ON dependent_phase_id = project_phase_id "
            + "WHERE project_id IN ";

    /**
     * Selects phase data.
     */
    private static final String SELECT_PHASE_FOR_PROJECTS = "SELECT project_phase_id, project_id, fixed_start_time, "
            + "scheduled_start_time, scheduled_end_time, actual_start_time, actual_end_time, duration, "
            + "phase_type_lu.phase_type_id, phase_type_lu.name phase_type_name, "
            + "phase_status_lu.phase_status_id, phase_status_lu.name phase_status_name "
            + "FROM project_phase JOIN phase_type_lu ON phase_type_lu.phase_type_id = project_phase.phase_type_id "
            + "JOIN phase_status_lu ON phase_status_lu.phase_status_id = "
            + "project_phase.phase_status_id WHERE project_id IN ";

    /**
     * Inserts data into phase dependencies.
     */
    private static final String INSERT_PHASE_DEPENDENCY = "INSERT INTO phase_dependency "
            + "(dependency_phase_id, dependent_phase_id, dependency_start, dependent_start, "
            + "lag_time, create_user, create_date, modify_user, modify_date) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) ";

    /**
     * Inserts the phase.
     */
    private static final String INSERT_PHASE = "INSERT INTO project_phase (project_phase_id, project_id, "
            + "phase_type_id, "
            + "phase_status_id, fixed_start_time, scheduled_start_time, scheduled_end_time, "
            + "actual_start_time, actual_end_time, duration, create_user, create_date, modify_user, modify_date) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    /**
     * Selects all phase statuses.
     */
    private static final String SELECT_PHASE_STATUS_IDS = "SELECT phase_status_id, name phase_status_name "
            + "FROM phase_status_lu";

    /**
     * Selects all phases types.
     */
    private static final String SELECT_PHASE_TYPES = "SELECT phase_type_id, name phase_type_name FROM phase_type_lu";

    /**
     * Selects all projects - checks if all exists in database.
     */
    private static final String SELECT_PROJECT_IDS = "SELECT project_id FROM project WHERE project_id IN ";

   
    /**
     * <p>
     * Represents the audit creation type.
     * </p>
     *
     * @since 1.0.2
     */
    private static final int AUDIT_CREATE_TYPE = 1;

    /**
     * <p>
     * Represents the audit deletion type.
     * </p>
     *
     * @since 1.0.2
     */
    private static final int AUDIT_DELETE_TYPE = 2;
    
    /**
     * <p>
     * Represents the audit update type.
     * </p>
     *
     * @since 1.0.2
     */
    private static final int AUDIT_UPDATE_TYPE = 3;
    
    /**
     * Represents the SQL statement to audit project info.
     * 
     * @since 1.0.2
     */
    private static final String PROJECT_PHASE_AUDIT_INSERT_SQL = "INSERT INTO project_phase_audit "
    	+ "(project_phase_id, scheduled_start_time, scheduled_end_time, audit_action_type_id, action_date, action_user_id) "
    	+ "VALUES (?, ?, ?, ?, ?, ?)";


    /**
     * <p>
     * An simple constructor which will populate the connectionFactory and
     * connectionName information from configuration. It will also read the
     * IDGenerator sequence name and implementation class (optional).
     * </p>
     * <p>
     * The configurations are:
     * <ol>
     * <li>connectionName - The connection name used to get connection from
     * connection factory, if it is not configured, the default connection will
     * be used, optional.</li>
     * <li>connectionFactoryClassName - The class name of connection factory,
     * required.</li>
     * <li>connectionFactoryNamespace - The namespace used to create the
     * connection factory, required.</li>
     * <li>Idgenerator.sequenceName - Used to retrieve an IDGenerator that can
     * service it, required. </li>
     * <li>Idgenerator.className - Name of the IDGenerator class that services
     * the named sequence, optional.</li>
     * </ol>
     * </p>
     * @param namespace the namespace to get the configuration.
     * @throws IllegalArgumentException if namespace is an empty string or a
     *             null.
     * @throws ConfigurationException if any of the required configuration
     *             parameters are missing or are incorrect.
     */
    protected AbstractInformixPhasePersistence(String namespace)
        throws ConfigurationException {
        super(namespace);
    }

    /**
     * <p>
     * A simple constructor which will populate the id generator, connection
     * factory and connection name information from input parameters.
     * </p>
     * @param connectionFactory connection factory instance
     * @param connectionName connection name.
     * @param idGen the IdGenerator that will be used to create the new ids for
     *            entities.
     * @throws IllegalArgumentException if the connectionFactory is
     *             <code>null</code>, idGen is <code>null</code> or the connectionName
     *             is empty String.
     */
    protected AbstractInformixPhasePersistence(
            DBConnectionFactory connectionFactory, String connectionName,
            IDGenerator idGen) {
        super(connectionFactory, connectionName, idGen);
    }

    /**
     * <p>
     * returns project instance for the given id. If the project can not be
     * found then a null is returned. The project will have all the dependencies
     * filled out. If the project exists in the database, but has no phases, the
     * empty Project instance will be returned.
     * </p>
     * @param projectId project id to find by.
     * @return Project for this id.
     * @throws PhasePersistenceException if any database error happen.
     */
    public Project getProjectPhases(long projectId)
        throws PhasePersistenceException {
        return getProjectPhases(new long[] {projectId})[0];
    }

    /**
     * <p>
     * Will return an array of project instances for the given input array of
     * project ids. If the project can not be found then a null is returned in
     * the return array. Returns are positional thus id at index 2 of input is
     * represented at index 2 of output. The project will have all the
     * dependencies filled out. If the project exists in the database, but has
     * no phases, the empty Project instance will be returned.
     * </p>
     * @param projectIds an array of project ids
     * @return and array of Projects for the ids
     * @throws IllegalArgumentException if the input array is null.
     * @throws PhasePersistenceException if any database error occurs.
     */
    public Project[] getProjectPhases(long[] projectIds)
        throws PhasePersistenceException {
        if (projectIds == null) {
            throw new IllegalArgumentException("projectIds cannot be null.");
        }

        // empty array empty result
        if (projectIds.length == 0) {
            return new Project[0];
        }

        Connection conn = getConnection();

        try {
            Project[] projects = getProjectPhasesImpl(conn, projectIds);
            return projects;
        } catch (SQLException ex) {
            throw new PhasePersistenceException(
                    "Error occurs while retrieving the projects.", ex);
        } finally {
            disposeConnection(conn);
        }
    }

    /**
     * <p>
     * Returns all the PhaseTypes from the database. The returned array might be
     * empty if no types exists, but it won't be null.
     * </p>
     * @return all available phase types stored in the database.
     * @throws PhasePersistenceException if any error occurs while accessing the
     *             database or creating PhaseTypes.
     */
    public PhaseType[] getAllPhaseTypes() throws PhasePersistenceException {
        Connection conn = getConnection();
        Statement stmt = null;
        ResultSet rs = null;

        try {

            // create statement
            stmt = conn.createStatement();
            // select all phase types
            rs = stmt.executeQuery(SELECT_PHASE_TYPES);

            // create the types
            List result = new ArrayList();
            while (rs.next()) {
                result.add(populatePhaseType(rs));
            }

            // convert list to array and return
            return (PhaseType[]) result.toArray(new PhaseType[result.size()]);
        } catch (SQLException ex) {

            throw new PhasePersistenceException(
                    "Error occurs while retrieving phase types.", ex);
        } finally {
            close(rs);
            close(stmt);
            disposeConnection(conn);
        }
    }

    /**
     * <p>
     * Returns all the PhaseStatuses from the database. The returned array might
     * be empty if no statuses exists, but it won't be null.
     * </p>
     * @return all available phase statuses stored in the database.
     * @throws PhasePersistenceException if any error occurs while accessing the
     *             database or creating PhaseStatus.
     */
    public PhaseStatus[] getAllPhaseStatuses() throws PhasePersistenceException {
        Connection conn = getConnection();
        Statement stmt = null;
        ResultSet rs = null;

        try {

            // create the statement
            stmt = conn.createStatement();
            // select all the statuses
            rs = stmt.executeQuery(SELECT_PHASE_STATUS_IDS);

            // create the statuses
            List result = new ArrayList();
            while (rs.next()) {
                result.add(populatePhaseStatus(rs));
            }

            // convert result to array and return
            return (PhaseStatus[]) result
                    .toArray(new PhaseStatus[result.size()]);
        } catch (SQLException ex) {
            throw new PhasePersistenceException(
                    "Error occurs while database operation.", ex);
        } finally {
            close(rs);
            close(stmt);
            disposeConnection(conn);
        }
    }

    /**
     * <p>
     * Creates the provided phase in persistence. All the phase dependencies
     * will be also created. The phase should have the unique id already
     * generated and set.
     * </p>
     * @param phase phase to be created.
     * @param operator the creation operator for audit proposes.
     * @throws IllegalArgumentException if phase or operator is null or if
     *             operator is am empty string.
     * @throws PhasePersistenceException if database relates error occurs.
     */
    public void createPhase(Phase phase, String operator)
        throws PhasePersistenceException {
        if (phase == null) {
            throw new IllegalArgumentException("phase cannot be null.");
        }

        createPhases(new Phase[] {phase}, operator);
    }

    /**
     * <p>
     * Creates the provided phase in persistence. All the phases dependencies
     * will be also created. Each phase should have the unique id already
     * generated and set.
     * </p>
     * @param phases an array of phases to create in persistence
     * @param operator the creation operator for audit proposes.
     * @throws IllegalArgumentException if phase or operator is null or if
     *             operator is an empty string or the array contains null value.
     * @throws PhasePersistenceException if database relates error occurs.
     */
    public void createPhases(Phase[] phases, String operator)
        throws PhasePersistenceException {
        checkPhases(phases);
        if (operator == null) {
            throw new IllegalArgumentException("operator cannot be null.");
        }

        if (operator.trim().length() == 0) {
            throw new IllegalArgumentException(
                    "operator cannot be empty String.");
        }

        if (phases.length == 0) {
            return;
        }

        PreparedStatement pstmt = null;

        // get the connection.
        Connection conn = getConnection();

        // create the context map
        Map context = createContextMap(conn);

        try {
            // start the transaction
            startTransaction(context);

            Map lookUp = getCriteriaTypes(conn);
            createPhasesImpl(conn, phases, operator, lookUp);
            // if all OK - commit transaction
            commitTransaction(context);
        } catch (SQLException ex) {
            // roll back the transaction
            rollbackTransaction(context);
            throw new PhasePersistenceException(
                    "Error occurs while creating the phases.", ex);
        } catch (PhasePersistenceException ppe) {
            // roll back the transaction
            rollbackTransaction(context);
            throw ppe;
        } finally {
            close(pstmt);
            disposeConnection(conn);
        }
    }

    /**
     * <p>
     * Reads a specific phase from the data store. If the phase with given id
     * doesn't exist, <code>null</code> value will be returned. The returned
     * phase will have all its dependencies set.
     * </p>
     * @param phaseId id of phase to fetch
     * @return the Phase object with given id, or <code>null</code> if not
     *         found.
     * @throws PhasePersistenceException if database related error occurs.
     */
    public Phase getPhase(long phaseId) throws PhasePersistenceException {
        return getPhases(new long[] {phaseId})[0];
    }

    /**
     * <p>
     * Batch version of the {@link #getPhase(long) getPhase} method. For each
     * entry in the input array at index i of the Phase is not found then we
     * return a null in the corresponding index in the output array. All the
     * phases dependencies will be satisfied.
     * </p>
     * @param phaseIds an array of phase ids to fetch phases with
     * @return a non-null array of Phases.
     * @throws PhasePersistenceException if any database related error occurs.
     * @throws IllegalArgumentException if phaseIds array is null.
     */
    public Phase[] getPhases(long[] phaseIds) throws PhasePersistenceException {
        if (phaseIds == null) {
            throw new IllegalArgumentException("phaseIds cannot be null.");
        }

        if (phaseIds.length == 0) {
            return new Phase[0];
        }
        Connection conn = getConnection();

        try {
            return getPhasesImpl(conn, phaseIds);
        } catch (SQLException ex) {
            throw new PhasePersistenceException(
                    "Error occurs while retrieving phases.", ex);
        } finally {
            disposeConnection(conn);
        }
    }

    /**
     * <p>
     * Update the provided phase in persistence. All the phases dependencies
     * will be updated as well.
     * </p>
     * @param phase update the phase in persistence
     * @param operator operator.
     * @throws PhasePersistenceException if any database related error occurs.
     * @throws IllegalArgumentException if phase or operator is null or the the
     *             operator is empty string.
     */
    public void updatePhase(Phase phase, String operator)
        throws PhasePersistenceException {
        if (phase == null) {
            throw new IllegalArgumentException("phase cannot be null.");
        }

        updatePhases(new Phase[] {phase}, operator);
    }

    /**
     * <p>
     * Update the provided phases in persistence. All the phases dependencies
     * will be updated as well. If any of the phases is not in the database, it
     * will be create with the new id.
     * </p>
     * @param phases an array of phases to update.
     * @param operator audit operator.
     * @throws PhasePersistenceException if any database related error occurs.
     * @throws IllegalArgumentException if phaseIds array is null or operator is
     *             null or the the operator is empty string.
     */
    public void updatePhases(Phase[] phases, String operator)
        throws PhasePersistenceException {
        checkPhases(phases);
        if (operator == null) {
            throw new IllegalArgumentException("operator cannot be null.");
        }

        if (operator.trim().length() == 0) {
            throw new IllegalArgumentException(
                    "operator cannot be empty String.");
        }

        // if not phases to update - just return
        if (phases.length == 0) {
            return;
        }

        // Retrieve the original phases to check for modifications.
        long[] phaseIds = new long[phases.length];
        for (int i = 0; i < phases.length; ++i) {
        	phaseIds[i] = phases[i].getId();
        }
        Phase[] oldPhases = getPhases(phaseIds);
        Map<Long, Phase> oldPhasesMap = new HashMap<Long, Phase>();
        for (Phase p : oldPhases) {
        	oldPhasesMap.put(p.getId(), p);
        }

		  // get the connection
        Connection conn = getConnection();

		long projectId = phases[0].getProject().getId();

		

        // create the context
        Map context = createContextMap(conn);

        PreparedStatement pstmt = null;
        // it will contain the new phases that should be created
        List toCreate = new ArrayList();

        try {

            // start the transaction
            startTransaction(context);

            // get the phases criteria lookups
            Map lookUps = getCriteriaTypes(conn);
            // create the statement
            pstmt = conn.prepareStatement(UPDATE_PHASE);

            // set audit values
            pstmt.setString(10, operator);
            Timestamp updateTime = new Timestamp(System.currentTimeMillis());
            pstmt.setTimestamp(11, updateTime);

            // iterate over all phases
            for (int i = 0; i < phases.length; i++) {
                // check if is a new phase - if so add it to list
                if (isNewPhase(phases[i])) {
                    toCreate.add(phases[i]);
                } else {
                    // set the update values
                    pstmt.setLong(1, phases[i].getProject().getId());
                    pstmt.setLong(2, phases[i].getPhaseType().getId());
                    pstmt.setLong(3, phases[i].getPhaseStatus().getId());
                    insertValueOrNull(pstmt, 4, phases[i].getFixedStartDate());
                    Timestamp scheduledStartTime = new Timestamp(phases[i].getScheduledStartDate().getTime());
                    pstmt.setTimestamp(5, scheduledStartTime);
                    Timestamp scheduledEndTime = new Timestamp(phases[i].getScheduledEndDate().getTime());
                    pstmt.setTimestamp(6, scheduledEndTime);

                    insertValueOrNull(pstmt, 7, phases[i].getActualStartDate());
                    insertValueOrNull(pstmt, 8, phases[i].getActualEndDate());
                    pstmt.setLong(9, phases[i].getLength());
                    pstmt.setLong(12, phases[i].getId());

                    // if phase not exists add it to the new list
                    if (pstmt.executeUpdate() == 0) {
                        toCreate.add(phases[i]);
                    } else {
						// TODO, for cockpit we dont update criteria and dependencies, so not need to update
			            // problem is we remove dependencies for cycle issue for web services, and criteria 
						// dont seem returned in get phase, so update here will delete all data
                        updatePhaseCriteria(conn, phases[i], operator, lookUps);
                        //updateDependencies(conn, phases[i], operator);

                        Phase oldPhase = oldPhasesMap.get(phases[i].getId());
                        Timestamp auditScheduledStartTime =
                        	oldPhase.getScheduledStartDate().equals(scheduledStartTime) ? null : scheduledStartTime;
                        Timestamp auditScheduledEndTime =
                        	oldPhase.getScheduledEndDate().equals(scheduledEndTime) ? null : scheduledEndTime;
                        
                        // only audit if one of the scheduled times changed
                        if (auditScheduledStartTime != null || auditScheduledEndTime != null) {
                        	auditProjectPhase(conn, phases[i], AUDIT_UPDATE_TYPE, auditScheduledStartTime,
                        			auditScheduledEndTime, Long.parseLong(operator), updateTime);
                        } 
                    }
                }
            }

            // create the new phases
            createPhasesImpl(conn, (Phase[]) toCreate
                    .toArray(new Phase[toCreate.size()]), operator, lookUps);

            // everything is OK, commit the transaction.
            commitTransaction(context);
        } catch (SQLException ex) {
            rollbackTransaction(context);
            throw new PhasePersistenceException(
                    "Error occurs while updating phases.", ex);
        }
		catch (Exception ex) {
            rollbackTransaction(context);
            throw new PhasePersistenceException(
                    "Error occurs while updating phases.", ex);
        }finally {
            close(pstmt);
            disposeConnection(conn);
        }
    }

    /**
     * <p>
     * Deletes the provided phase from the persistence. All phase dependencies
     * will be removed as well. If phase is not in the persistence, nothing will
     * happen.
     * </p>
     * @param phase phase to delete.
     * @throws PhasePersistenceException if database error occurs.
     * @throws IllegalArgumentException if phase is null.
     */
    public void deletePhase(Phase phase) throws PhasePersistenceException {
        if (phase == null) {
            throw new IllegalArgumentException("phase cannot be null.");
        }

        deletePhases(new Phase[] {phase});
    }

    /**
     * <p>
     * Deletes the provided phases from the persistence. All phases dependencies
     * will be removed as well. If any phase is not in the persistence, nothing
     * will happen.
     * </p>
     * @param phases an array of phases to delete
     * @throws PhasePersistenceException if database error occurs.
     * @throws IllegalArgumentException if array is null or contains null.
     */
    public void deletePhases(Phase[] phases) throws PhasePersistenceException {
        checkPhases(phases);
        // if no phases to delete - just return
        if (phases.length == 0) {
            return;
        }

        String inSet = createQuestionMarks(phases.length);
        // get the connection.
        Connection conn = getConnection();
        PreparedStatement pstmt = null;
        PreparedStatement pstmt2 = null;
        PreparedStatement pstmt3 = null;
        PreparedStatement pstmt4 = null;

        // create the context.
        Map context = createContextMap(conn);

        try {
            // start the transaction.
            startTransaction(context);
            // create the statements for all 4 tables (phase, dependencies and
            // criteria and audit)
            pstmt = conn.prepareStatement(DELETE_FROM_PHASE_DEPENDENCY + inSet
                    + DELETE_FROM_PHASE_DEPENDENCY_OR + inSet);
            pstmt2 = conn.prepareStatement(DELETE_PROJECT_PHASE + inSet);
            pstmt3 = conn.prepareStatement(DELETE_PHASE_CRITERIA_FOR_PHASES
                    + inSet);
            pstmt4 = conn.prepareStatement(DELETE_PHASE_AUDIT_FOR_PHASES + inSet);

            // set the id values
            for (int i = 0; i < phases.length; i++) {
                pstmt.setLong(i + 1, phases[i].getId());
                pstmt.setLong(i + 1 + phases.length, phases[i].getId());
                pstmt2.setLong(i + 1, phases[i].getId());
                pstmt3.setLong(i + 1, phases[i].getId());
                pstmt4.setLong(i + 1, phases[i].getId());
            }

            // delete dependencies
            pstmt.executeUpdate();
            // delete criteria
            pstmt3.executeUpdate();
            // delete audit
            pstmt4.executeUpdate();
            // delete phases
            pstmt2.executeUpdate();

            /*
             for (int i = 0; i < phases.length; i++) {
            	auditProjectPhase(conn, phases[i], AUDIT_DELETE_TYPE, null, null, 0L,
            			new Timestamp(System.currentTimeMillis()));
            }
            */

            // everything is OK, commit the transaction.
            commitTransaction(context);
        } catch (SQLException ex) {
            rollbackTransaction(context);
            throw new PhasePersistenceException(
                    "Error occurs while deleting phases.", ex);
        } finally {
            close(pstmt);
            close(pstmt2);
            close(pstmt3);
            close(pstmt4);
            disposeConnection(conn);
        }
    }

    /**
     * <p>
     * Tests if the input phase is a new phase (i.e. needs its id to be set).
     * </p>
     * @param phase Phase object to tests if it is new
     * @return true if the phase is new; false otherwise.
     * @throws IllegalArgumentException if dependency is null.
     */
    public boolean isNewPhase(Phase phase) {
        if (phase == null) {
            throw new IllegalArgumentException("phase cannot be null.");
        }

        return phase.getId() == 0;
    }

    /**
     * <p>
     * Tests if the input dependency is a new dependency (i.e. needs its id to
     * be set) .
     * </p>
     * <p>
     * Implementation details As per PM comments in the forums. This logic is
     * not currently known and will be supplied later.
     * </p>
     * @param dependency Dependency to check for being new.
     * @return true if the dependency is new; false otherwise.
     * @throws IllegalArgumentException if dependency is null.
     */
    public boolean isNewDependency(Dependency dependency) {
        if (dependency == null) {
            throw new IllegalArgumentException("dependency cannot be null.");
        }

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
        } catch (PhasePersistenceException e) {
            return false;
        }

        try {

            // create the statement
            pstmt = conn.prepareStatement(CHECK_DEPENDENCY);
            pstmt.setLong(1, dependency.getDependency().getId());
            pstmt.setLong(2, dependency.getDependent().getId());
            // execute query
            rs = pstmt.executeQuery();

            // if has any result - phase is not new
            return !rs.next();
        } catch (SQLException ex) {
            return false;
        } finally {
            close(rs);
            close(pstmt);
            try {
                disposeConnection(conn);
            } catch (PhasePersistenceException e) {
                // ignore
            }
        }
    }


    /**
     * This method selects all the dependencies for phases.
     * @param conn the database connection.
     * @param phases the map of already retrieved phases.
     * @param projectIds all the project ids.
     * @throws PhasePersistenceException if the phase dependencies cannot be
     *             filled.
     */
    public void fillDependencies(Map phases, long[] projectIds)
        throws PhasePersistenceException {

         Connection conn = getConnection();

        try {
            fillDependencies(conn, phases, projectIds);
        } catch (SQLException ex) {
            throw new PhasePersistenceException(
                    "Error occurs while fill Dependencies.", ex);
        } finally {
            disposeConnection(conn);
        }

    }

    /**
     * Creates a context map with the connection filled with key "connection".
     * @param conn the connection which should be in the context map.
     * @return a context map containing the connection with key "connection".
     */
    private Map createContextMap(Connection conn) {
        Map context = new HashMap();
        context.put(AbstractDbPhasePersistence.CONNECTION_CONTEXT_KEY, conn);
        return context;
    }

    /**
     * This the current implementation of the {@link #getProjectPhases(long[])}
     * method. It will first check which project for the given ids exists and
     * which have no phases. The it selects all the phases for the projects,
     * create them and return. If any project from the given list not exists,
     * null will be returned in that place.
     * @param conn the database connection to use.
     * @param projectIds the ids of the projects to retrieve.
     * @return the array of projects with one to one mapping between project and
     *         it id.
     * @throws SQLException if any database error occurs.
     * @throws PhasePersistenceException if other error happen.
     */
    private Project[] getProjectPhasesImpl(Connection conn, long[] projectIds)
        throws SQLException, PhasePersistenceException {
        // if no ids - return empty array
        if (projectIds.length == 0) {
            return new Project[0];
        }

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        // create workdays to be used to create the project
        DefaultWorkdays workdays = (DefaultWorkdays)new DefaultWorkdaysFactory()
                .createWorkdaysInstance();

        try {
            pstmt = conn.prepareStatement(SELECT_PROJECT_IDS
                    + createQuestionMarks(projectIds.length));
            for (int i = 0; i < projectIds.length; i++) {
                pstmt.setLong(i + 1, projectIds[i]);
            }

            rs = pstmt.executeQuery();

            Map projectsMap = new HashMap();

            // create all the projects that exists and store them in helper map
            while (rs.next()) {
                long projectId = rs.getLong(1);
                Project project = new Project(new Date(Long.MAX_VALUE),
                        workdays);
                project.setId(projectId);
                projectsMap.put(new Long(projectId), project);
            }

            // closes resources
            close(rs);
            close(pstmt);

            Map phasesMap = new HashMap();

            // prepare the query to retrieve the phases .
            pstmt = conn.prepareStatement(SELECT_PHASE_FOR_PROJECTS
                    + createQuestionMarks(projectIds.length));
            for (int i = 0; i < projectIds.length; i++) {
                pstmt.setLong(i + 1, projectIds[i]);
            }

            rs = pstmt.executeQuery();

            // for each phase in the response create the Phase object and add it
            // to the internal list
            while (rs.next()) {
                long projectId = rs.getLong("project_id");

                Project project = (Project) projectsMap
                        .get(new Long(projectId));

                Phase phase = populatePhase(rs, project);
                phasesMap.put(new Long(phase.getId()), phase);
            }

            // fill the phases dependencies and criteria for them
            if (phasesMap.size() > 0) {
                fillDependencies(conn, phasesMap, projectIds);
                fillCriteria(conn, phasesMap, projectIds);
            }

            // this comparator is used to get the lowest start date
            Comparator phasesComparator = new PhaseStartDateComparator();
            // set the correct date for each project
            for (Iterator it = projectsMap.values().iterator(); it.hasNext();) {
                Project project = (Project) it.next();
                Phase[] phases = project.getAllPhases(phasesComparator);
                // if project has any phases - get the first one
                if (phases.length > 0) {
                    project.setStartDate(phases[0].getScheduledStartDate());
                }
            }

            // create the result array
            Project[] result = new Project[projectIds.length];
            for (int i = 0; i < projectIds.length; i++) {
                result[i] = (Project) projectsMap.get(new Long(projectIds[i]));
            }

            return result;
        } finally {
            close(rs);
            close(pstmt);
        }
    }

    /**
     * Closes the statement.
     * @param stmt the statement.
     */
    private static void close(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                // ignore
            }
        }
    }

    /**
     * Closes the result set.
     * @param rs the result set.
     */
    private static void close(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                // ignore
            }
        }
    }

    /**
     * Creates the string in the pattern (?,+) where count is the number of
     * question marks. It is used th build prepared statements with IN
     * condition.
     * @param count number of question marks.
     * @return the string of question marks.
     */
    private static String createQuestionMarks(int count) {
        StringBuffer buff = new StringBuffer();
        buff.append("(?");
        for (int i = 1; i < count; i++) {
            buff.append(", ?");
        }

        buff.append(")");
        return buff.toString();
    }

    /**
     * Creates the PhaseType instance using the data from the ResultSet.
     * @param rs the source ResultSet for PhaseType object.
     * @return the PhaseType instance created from the ResultSet row.
     * @throws SQLException if errors occurs while accessing the ResultSet.
     */
    private static PhaseType populatePhaseType(ResultSet rs)
        throws SQLException {
        long id = rs.getLong("phase_type_id");
        String name = rs.getString("phase_type_name");
        return new PhaseType(id, name);
    }

    /**
     * This method selects all the dependencies for phases.
     * @param conn the database connection.
     * @param phases the map of already retrieved phases.
     * @param projectIds all the project ids.
     * @throws SQLException if database error occurs.
     * @throws PhasePersistenceException if the phase dependencies cannot be
     *             filled.
     */
    private void fillDependencies(Connection conn, Map phases, long[] projectIds)
        throws SQLException, PhasePersistenceException {
        // get the phase
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // create the statement
            pstmt = conn.prepareStatement(SELECT_DEPENDENCY_FOR_PROJECTS
                    + createQuestionMarks(projectIds.length));

            // set the id to the statement
            for (int i = 0; i < projectIds.length; ++i) {
                pstmt.setLong(i + 1, projectIds[i]);
            }

            // execute the query
            rs = pstmt.executeQuery();

            while (rs.next()) {
                // get the dependency
                Long dependentId = new Long(rs.getLong("dependent_phase_id"));
                Long dependencyId = new Long(rs.getLong("dependency_phase_id"));
                // if the phase exists - create dependency
                if (phases.containsKey(dependentId)
                        && phases.containsKey(dependencyId)) {
                    Phase phase = (Phase) phases.get(dependentId);
                    Dependency dependency = createDependency(rs, phases, phase);
                    phase.addDependency(dependency);
                } else {
                    // because we have retrieved all the phases for project
                    // before, this should never happen
                    throw new PhasePersistenceException("Missing dependency: "
                            + dependencyId + " for phase: " + dependentId);
                }
            }

        } finally {
            close(rs);
            close(pstmt);
        }
    }

    /**
     * Create the Dependency instance from given result set.
     * @param rs the source result set.
     * @param phases the retrieved phases.
     * @param dependantPhase the dependant phase.
     * @return the Dependency instance.
     * @throws SQLException if database error occurs.
     */
    private static Dependency createDependency(ResultSet rs, Map phases,
            Phase dependantPhase) throws SQLException {
        Phase dependencyPhase = (Phase) phases.get(new Long(rs
                .getLong("dependency_phase_id")));
        long lagTime = rs.getLong("lag_time");

        return new Dependency(dependencyPhase, dependantPhase, rs
                .getBoolean("dependency_start"), rs
                .getBoolean("dependent_start"), lagTime);
    }

    /**
     * This method set the phase criteria into the phases from the given map.
     * @param conn the database connection to be used.
     * @param phases the Phases to which criteria will be add. Key should be
     *            Long phase id, value - Phase object.
     * @param projectIds all the project ids.
     * @throws SQLException if any database error occurs.
     */
    private void fillCriteria(Connection conn, Map phases, long[] projectIds)
        throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // create the statement
            pstmt = conn.prepareStatement(SELECT_PHASE_CRITERIA_FOR_PROJECTS
                    + createQuestionMarks(projectIds.length));

            // set the id to the statement
            for (int i = 0; i < projectIds.length; ++i) {
                pstmt.setLong(i + 1, projectIds[i]);
            }

            // execute query
            rs = pstmt.executeQuery();

            // create the phase criteria
            while (rs.next()) {
                // get the phase id
                Long id = new Long(rs.getLong("project_phase_id"));
                // get criteria name and parameter
                String name = rs.getString("name");
                String parameter = rs.getString("parameter");

                // get the phase and add criteria
                Phase phase = (Phase) phases.get(id);
                phase.setAttribute(name, parameter);
            }
        } finally {
            close(rs);
            close(pstmt);
        }
    }

    /**
     * Creates the Phase instance from the given ResultSet.
     * @param rs the source result set.
     * @param project the project for phase.
     * @return the Phase instance.
     * @throws SQLException if database error occurs.
     */
    private static Phase populatePhase(ResultSet rs, Project project)
        throws SQLException {
        long duration = rs.getLong("duration");
        Phase phase = new Phase(project, duration);
        phase.setActualEndDate(rs.getTimestamp("actual_end_time"));
        phase.setActualStartDate(rs.getTimestamp("actual_start_time"));
        phase.setFixedStartDate(rs.getTimestamp("fixed_start_time"));
        phase.setId(rs.getLong("project_phase_id"));

        phase.setPhaseStatus(populatePhaseStatus(rs));
        phase.setPhaseType(populatePhaseType(rs));
        phase.setScheduledEndDate(rs.getTimestamp("scheduled_end_time"));
        phase.setScheduledStartDate(rs.getTimestamp("scheduled_start_time"));

        return phase;
    }

    /**
     * Creates the PhaseStatus instance using the values from ResultSet.
     * @param rs the source result set for the PhaseStatus object.
     * @return the PhaseStatus created from the ResultSet.
     * @throws SQLException if error occurs while accessing the ResultSet.
     */
    private static PhaseStatus populatePhaseStatus(ResultSet rs)
        throws SQLException {
        long id = rs.getLong("phase_status_id");
        String name = rs.getString("phase_status_name");
        return new PhaseStatus(id, name);
    }

    /**
     * Checks the phases array.
     * @param phases the phases array.
     * @throws IllegalArgumentException if phases is null or contains null.
     */
    private static void checkPhases(Phase[] phases) {
        if (phases == null) {
            throw new IllegalArgumentException("phases cannot be null.");
        }

        for (int i = 0; i < phases.length; i++) {
            if (phases[i] == null) {
                throw new IllegalArgumentException(
                        "The phases array contains null element at index: " + i);
            }
        }
    }

    /**
     * Returns all the criteria types for lookup proposes. This will speed up
     * the creation process.
     * @param conn the connection to use.
     * @return a map where the key is the criteria name and the value is the
     *         criteria type id.
     * @throws SQLException if any database error occurs.
     */
    private static Map getCriteriaTypes(Connection conn) throws SQLException {
        Map result = new HashMap();
        Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(SELECT_PHASE_CRITERIA);
            while (rs.next()) {
                result.put(rs.getString("name"), new Long(rs
                        .getLong("phase_criteria_type_id")));
            }

        } finally {
            close(rs);
            close(stmt);
        }

        return result;
    }

    /**
     * This is the internal implementation of the
     * {@link #createPhase(Phase, String)} method. It creates the given array of
     * the Phases in the persistence.
     * @param conn the connection to be used.
     * @param phases the phases array to be created.
     * @param operator the creation audit operator.
     * @param lookUp the lookup map for the criteria name - id.
     * @throws SQLException if any database error occurs.
     * @throws PhasePersistenceException if other error occurs.
     */
    private void createPhasesImpl(Connection conn, Phase[] phases,
            String operator, Map lookUp) throws SQLException,
            PhasePersistenceException {
        // the list of phases dependencies
        List dependencies = new ArrayList();
        PreparedStatement pstmt = null;

        try {

            // create insert statement
            pstmt = conn.prepareStatement(INSERT_PHASE);
            Timestamp now = new Timestamp(System.currentTimeMillis());

            // set the date and operator - this values are constant for all
            // phases
            pstmt.setString(11, operator);
            pstmt.setTimestamp(12, now);
            pstmt.setString(13, operator);
            pstmt.setTimestamp(14, now);

            // iterate over the phases array
            for (int i = 0; i < phases.length; i++) {
                // generate the new id for phase
                phases[i].setId(nextId());
                // add all phase dependencies to the list
                dependencies.addAll(Arrays.asList(phases[i]
                        .getAllDependencies()));

                // set the phase data to statement
                pstmt.setLong(1, phases[i].getId());
                pstmt.setLong(2, phases[i].getProject().getId());
                pstmt.setLong(3, phases[i].getPhaseType().getId());
                pstmt.setLong(4, phases[i].getPhaseStatus().getId());
                insertValueOrNull(pstmt, 5, phases[i].getFixedStartDate());
                Timestamp scheduledStartTime = new Timestamp(phases[i].getScheduledStartDate().getTime());
                pstmt.setTimestamp(6, scheduledStartTime);
                Timestamp scheduledEndTime = new Timestamp(phases[i].getScheduledEndDate().getTime());
                pstmt.setTimestamp(7, scheduledEndTime);
                insertValueOrNull(pstmt, 8, phases[i].getActualStartDate());
                insertValueOrNull(pstmt, 9, phases[i].getActualEndDate());
                pstmt.setLong(10, phases[i].getLength());

                // insert the phase
                pstmt.executeUpdate();
                
                auditProjectPhase(conn, phases[i], AUDIT_CREATE_TYPE, scheduledStartTime, scheduledEndTime,
                		Long.parseLong(operator), now);
                
                // create the criteria for phase
                createPhaseCriteria(conn, phases[i], filterAttributes(phases[i]
                        .getAttributes()), operator, lookUp);
            }

            // create the dependencies for phases
            createDependency(conn, dependencies, operator);
        } finally {
            close(pstmt);
        }
    }

    /**
     * Creates the given list of dependencies in the database. All the phases
     * must already be persisted.
     * @param conn the database connection to be used.
     * @param dependencies the phases dependencies to.
     * @param operator the creation operator for audit proposes.
     * @throws SQLException if any database error happens.
     */
    private void createDependency(Connection conn, Collection dependencies,
            String operator) throws SQLException {
        PreparedStatement pstmt = null;

        try {
            // create the statement
            pstmt = conn.prepareStatement(INSERT_PHASE_DEPENDENCY);
            Timestamp time = new Timestamp(System.currentTimeMillis());
            // set the operator and timestamps
            pstmt.setString(6, operator);
            pstmt.setTimestamp(7, time);
            pstmt.setString(8, operator);
            pstmt.setTimestamp(9, time);

            // iterate over the list of dependencies
            for (Iterator iter = dependencies.iterator(); iter.hasNext();) {
                Dependency dependency = (Dependency) iter.next();

                // set the dependencies values.
                pstmt.setLong(1, dependency.getDependency().getId());
                pstmt.setLong(2, dependency.getDependent().getId());
                pstmt.setBoolean(3, dependency.isDependencyStart());
                pstmt.setBoolean(4, dependency.isDependentStart());
                pstmt.setLong(5, dependency.getLagTime());

                // create the dependency
                pstmt.executeUpdate();
            }
        } finally {
            close(pstmt);
        }

    }

    /**
     * Inserts date into PreparedStatement. If the date is null, null will be
     * set according to JDBC specification.
     * @param pstmt the statement where value will be set.
     * @param idx the position is statement where values need to be set.
     * @param date the value to be set.
     * @throws SQLException if error occurs while setting the date.
     */
    private static void insertValueOrNull(PreparedStatement pstmt, int idx,
            Date date) throws SQLException {
        if (date == null) {
            pstmt.setNull(idx, Types.DATE);
        } else {
            pstmt.setTimestamp(idx, new Timestamp(date.getTime()));
        }
    }

    /**
     * This helper method removes all attributes that have non-String hey.
     * @param attributes the attributes to be filtered.
     * @return the filtered attributes map.
     */
    private static Map filterAttributes(Map attributes) {
        // make the copy because the input attributes are not modifiable
        Map attribs = new HashMap(attributes);
        for (Iterator it = attribs.entrySet().iterator(); it.hasNext();) {
            Map.Entry el = (Map.Entry) it.next();
            if (!(el.getKey() instanceof String)) {
                it.remove();
            }
        }
        return attribs;
    }

    /**
     * Creates the phase criteria in the database. It uses helper lookup map to
     * reduce database call for all the criteria types.
     * @param conn the database connection to use.
     * @param phase the phase to which the criteria belongs.
     * @param attribs the phases criteria and parameters. The key is the name of
     *            criteria, the vale is the parameter.
     * @param operator the operator for audit proposes.
     * @param lookUp the lookup values for the criteria types.
     * @throws SQLException thrown if any database error occurs.
     */
    private void createPhaseCriteria(Connection conn, Phase phase, Map attribs,
            String operator, Map lookUp) throws SQLException {

        // no work to do, return
        if (attribs.size() == 0) {
            return;
        }

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // create the query
            pstmt = conn.prepareStatement(INSERT_PHASE_CRITERIA);
            Timestamp time = new Timestamp(System.currentTimeMillis());

            // set the value that do not change
            pstmt.setLong(1, phase.getId());
            pstmt.setString(4, operator);
            pstmt.setTimestamp(5, time);
            pstmt.setString(6, operator);
            pstmt.setTimestamp(7, time);

            // iterate over all attributes and persist only those who are the
            // criteria
            for (Iterator it = attribs.entrySet().iterator(); it.hasNext();) {
                Map.Entry entry = (Map.Entry) it.next();
                Long id = (Long) lookUp.get(entry.getKey());
                // if such criteria exists - add it
                if (id != null) {
                    pstmt.setLong(2, id.longValue());
                    pstmt.setString(3, (String) entry.getValue());
                    pstmt.executeUpdate();
                }
            }

        } finally {
            close(pstmt);
            close(rs);
        }

    }

    /**
     * Returns the new unique ID.
     * @return the unique ID.
     * @throws PhasePersistenceException if error occurs while generating the
     *             id.
     */
    private long nextId() throws PhasePersistenceException {
        try {
            return super.getIDGenerator().getNextID();
        } catch (IDGenerationException ex) {
            throw new PhasePersistenceException(
                    "Error occurs while generating new ids.", ex);
        }
    }

    /**
     * This is the internal version of the {@link #checkPhases(Phase[])} method.
     * It retrieves the project ids for those phases and that delegates to the
     * {@link #getProjectPhasesImpl(Connection, long[])} method. Than it gets
     * the phases from appropriate projects and returns them.
     * @param conn the database connection.
     * @param phaseIds the phases ids that need to be retrieved.
     * @return the Phase array.
     * @throws SQLException if any database error occurs.
     * @throws PhasePersistenceException if the phase dependencies cannot be
     *             met.
     */
    private Phase[] getPhasesImpl(Connection conn, long[] phaseIds)
        throws SQLException, PhasePersistenceException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        // this map will hold current phases. the key is phase id, value - phase
        // instance
        Map phases = new HashMap();

        try {
            // get all the phase - project mapping from the database - just in
            // case the actual value were outdated
            pstmt = conn.prepareStatement(SELECT_PROJECT_PHASE_ID
                    + createQuestionMarks(phaseIds.length));
            for (int i = 0; i < phaseIds.length; i++) {
                pstmt.setLong(i + 1, phaseIds[i]);
            }

            // execute the query
            rs = pstmt.executeQuery();
            List projectIds = new ArrayList();

            // create the phase - project mapping
            while (rs.next()) {
                Long projectId = new Long(rs.getLong("project_id"));
                Long phaseId = new Long(rs.getLong("project_phase_id"));

                phases.put(phaseId, projectId);
                if (!projectIds.contains(projectId)) {
                    projectIds.add(projectId);
                }
            }

            // get the projects
            Project[] projects = getProjectPhasesImpl(conn,
                    listToArray(projectIds));
            // for each phase in the phases map
            for (Iterator it = new HashSet(phases.entrySet()).iterator(); it
                    .hasNext();) {
                Map.Entry entry = (Map.Entry) it.next();
                // get the phase id
                long phaseId = ((Long) entry.getKey()).longValue();

                // get the phases from project for that phase id.
                Phase[] phasesTemp = projects[projectIds.indexOf(entry
                        .getValue())].getAllPhases();
                // find the phase object
                for (int i = 0; i < phasesTemp.length; i++) {
                    if (phasesTemp[i].getId() == phaseId) {
                        phases.put(entry.getKey(), phasesTemp[i]);
                        break;
                    }
                }
            }

        } finally {
            close(rs);
            close(pstmt);
        }

        // prepare the result array - if any phase not exists - set the result
        // for it to null
        Phase[] result = new Phase[phaseIds.length];
        for (int i = 0; i < phaseIds.length; i++) {
            result[i] = (Phase) phases.get(new Long(phaseIds[i]));
        }

        return result;

    }

    /**
     * Converts the give list of Long objects to array.
     * @param ids the ids list.
     * @return the ids array.
     */
    private static long[] listToArray(List ids) {
        long[] result = new long[ids.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = ((Long) ids.get(i)).longValue();
        }

        return result;
    }

    /**
     * Updates the phase criteria for the given phase. It will create new ones,
     * modify existing and delete the old.
     * @param conn the database connection to be used.
     * @param phase the phase to update.
     * @param operator the modification operator for audit proposes.
     * @param lookUp the lookup value for the criteria type ids.
     * @throws SQLException if any database error occurs.
     */
    private void updatePhaseCriteria(Connection conn, Phase phase,
            String operator, Map lookUp) throws SQLException {

        PreparedStatement selectStatement = null;
        PreparedStatement updateStatement = null;
        PreparedStatement deleteStatement = null;
        ResultSet rs = null;

        // get all the properties that only string key - they are potential
        // criteria
        Map newCriteria = filterAttributes(phase.getAttributes());
        // the map for the old criteria
        Map oldCriteria = new HashMap();

        try {
            // current update time
            Timestamp now = new Timestamp(System.currentTimeMillis());

            // get all criteria for phase from the persistence
            selectStatement = conn
                    .prepareStatement(SELECT_PHASE_CRITERIA_FOR_PHASE);
            selectStatement.setLong(1, phase.getId());

            rs = selectStatement.executeQuery();

            // put the old criteria to map
            while (rs.next()) {
                String name = rs.getString("name");
                String value = rs.getString("parameter");

                oldCriteria.put(name, value);
            }

            // create the update statement for all criteria
            updateStatement = conn.prepareStatement(UPDATE_PHASE_CRITERIA);
            updateStatement.setString(2, operator);
            updateStatement.setTimestamp(3, now);
            updateStatement.setLong(4, phase.getId());

            // iterate over the new attributes taken from the updated phase
            for (Iterator it = newCriteria.entrySet().iterator(); it.hasNext();) {
                Map.Entry entry = (Map.Entry) it.next();
                // check if the value from new set is in the one from
                // persistence
                String oldValue = (String) oldCriteria.remove(entry.getKey());
                if ((oldValue != null) && !oldValue.equals(entry.getValue())) {
                    // update if the values are different - update the criteria
                    updateStatement.setString(1, (String) entry.getValue());
                    updateStatement.setLong(5, ((Long) lookUp.get(entry
                                .getKey())).longValue());
                    updateStatement.executeUpdate();
                }
                if (oldValue != null) {
                    // remove the criteria from list
                    it.remove();
                }
            }

            // if left any new values - create them
            if (newCriteria.size() > 0) {
                createPhaseCriteria(conn, phase, newCriteria, operator, lookUp);
            }

            // if any value left in old criteria - they need to be removed.
            if (oldCriteria.size() > 0) {
                deleteStatement = conn.prepareStatement(DELETE_PHASE_CRITERIA
                        + createQuestionMarks(oldCriteria.size()));
                deleteStatement.setLong(1, phase.getId());

                int i = 1;
                for (Iterator it = oldCriteria.keySet().iterator(); it
                        .hasNext();) {
                    String name = (String) it.next();
                    deleteStatement.setLong(++i, ((Long) lookUp.get(name))
                            .longValue());
                }

                deleteStatement.executeUpdate();
            }

        } finally {
            close(rs);
            close(selectStatement);
            close(deleteStatement);
            close(updateStatement);
        }

    }

    /**
     * This method will update the phase dependencies in the persistence.
     * Because the table has new audit columns, we cannot just remove
     * dependencies and create the again.
     * @param conn the database connection.
     * @param phase the phase with dependencies to update.
     * @param operator the update operator for audit proposes.
     * @throws SQLException if any database error occurs.
     */
    private void updateDependencies(Connection conn, Phase phase,
            String operator) throws SQLException {
        // put all the dependencies to the map: key: dependency id, value:
        // Dependency object.
        Map dependencies = new HashMap();
        Dependency[] deps = phase.getAllDependencies();
        for (int i = 0; i < deps.length; i++) {
            dependencies
                    .put(new Long(deps[i].getDependency().getId()), deps[i]);
        }

        PreparedStatement selectStatement = null;
        ResultSet rs = null;

        // this list will keep dependencies to remove and to update
        List depsToRemove = new ArrayList();
        List depsToUpdate = new ArrayList();
        try {
            // select dependencies for the phase
            selectStatement = conn.prepareStatement(SELECT_DEPENDENCY);
            selectStatement.setLong(1, phase.getId());

            rs = selectStatement.executeQuery();
            // for each row
            while (rs.next()) {
                // get the dependency phase id
                Long dependencyId = new Long(rs.getLong("dependency_phase_id"));
                // check if such dependency exists
                if (dependencies.containsKey(dependencyId)) {
                    // if yes
                    // check to update (we don't need another db call of the row
                    // not need to be updated.
                    Dependency dep = (Dependency) dependencies
                            .get(dependencyId);
                    // check the value
                    if ((dep.isDependencyStart() != rs
                            .getBoolean("dependency_start"))
                            || (dep.isDependentStart() != rs
                                    .getBoolean("dependent_start"))
                            || (dep.getLagTime() != rs.getLong("lag_time"))) {

                        // if any is different - update the dependency
                        depsToUpdate.add(dep);
                    }
                    // remove the dependency from map
                    dependencies.remove(dependencyId);
                } else {
                    // if not exists, delete the dependency from database
                    depsToRemove.add(dependencyId);
                }
            }

            // if still some dependencies left - this means they are new -
            // create
            // them
            if (dependencies.size() > 0) {
                // create new dependency
                createDependency(conn, dependencies.values(), operator);
            }

            // if there is something to remove
            if (depsToRemove.size() > 0) {
                deleteDependencies(conn, depsToRemove, phase.getId());
            }

            // if there is something to update
            if (depsToUpdate.size() > 0) {
                updateDependencies(conn, depsToUpdate, operator);
            }

        } finally {
            close(rs);
            close(selectStatement);
        }
    }

    /**
     * Deletes the dependencies from database.
     * @param conn the connection to use.
     * @param ids the ids of the dependencies.
     * @param dependantId the dependant phase id.
     * @throws SQLException if any database error occurs.
     */
    private static void deleteDependencies(Connection conn, List ids,
            long dependantId) throws SQLException {
        PreparedStatement pstmt = null;
        try {
            // create the statement
            pstmt = conn.prepareStatement(DELETE_PHASE_DEPENDENCY
                    + createQuestionMarks(ids.size()));
            pstmt.setLong(1, dependantId);
            for (int i = 0; i < ids.size(); i++) {
                pstmt.setLong(i + 2, ((Long) ids.get(i)).longValue());
            }

            // execute update
            pstmt.executeUpdate();

        } finally {
            close(pstmt);
        }
    }

    /**
     * Updates the dependencies in the database.
     * @param conn the database connection to use.
     * @param deps the dependencies list.
     * @param operator the update operator for audit proposes.
     * @throws SQLException if any database error occurs.
     */
    private static void updateDependencies(Connection conn, List deps,
            String operator) throws SQLException {
        PreparedStatement pstmt = null;

        try {
            // create the statement
            pstmt = conn.prepareStatement(UPDATE_PHASE_DEPENDENCY);

            pstmt.setString(4, operator);
            pstmt.setTimestamp(5, new Timestamp(System.currentTimeMillis()));

            // for each dependency - update it in the database
            for (Iterator it = deps.iterator(); it.hasNext();) {
                Dependency dep = (Dependency) it.next();

                pstmt.setBoolean(1, dep.isDependencyStart());
                pstmt.setBoolean(2, dep.isDependentStart());
                pstmt.setLong(3, dep.getLagTime());
                pstmt.setLong(6, dep.getDependency().getId());
                pstmt.setLong(7, dep.getDependent().getId());

                pstmt.executeUpdate();
            }

        } finally {
            close(pstmt);
        }
    }

    /**
     * The private comparator of phases. It compares the phases scheduled start
     * dates.
     * @author kr00tki
     * @version 1.0
     */
    private class PhaseStartDateComparator implements Comparator {

        /**
         * Compares its two arguments for order. Returns a negative integer,
         * zero, or a positive integer as the first start date is less than,
         * equal to, or greater than the second.
         * @param o1 the first phase.
         * @param o2 the second phase.
         * @return a negative integer, zero, or a positive integer as the first
         *         argument is less than, equal to, or greater than the second.
         */
        public int compare(Object o1, Object o2) {
            return ((Phase) o1).getScheduledStartDate().compareTo(
                    ((Phase) o2).getScheduledStartDate());
        }
    }

     /**
     * This method will a project phase's scheduled start and end time when they are inserted, deleted, or edited.
     *
     * @param connection the connection to database
     * @param phase the phase being audited
     * @param scheduledStartTime the new scheduled start time for the phase
     * @param scheduledEndTime the new scheduled end time for the phase
     * @param auditType the audit type. Can be AUDIT_CREATE_TYPE, AUDIT_DELETE_TYPE, or AUDIT_UPDATE_TYPE
     * @param auditUser the user initiating the change
     * @param auditTime the timestamp for the audit event
     *
     * @throws PhasePersistenceException if validation error occurs or any error occurs in the underlying layer
     *
     * @since 1.0.2
     */
    private void auditProjectPhase(Connection connection, Phase phase, int auditType, Timestamp scheduledStartTime,
    		Timestamp scheduledEndTime, Long auditUser, Timestamp auditTime) throws PhasePersistenceException {

        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(PROJECT_PHASE_AUDIT_INSERT_SQL);

            int index = 1;
            statement.setLong(index++, phase.getId());
            statement.setTimestamp(index++, scheduledStartTime);
            statement.setTimestamp(index++, scheduledEndTime);
            statement.setInt(index++, auditType);
            statement.setTimestamp(index++, auditTime);
            statement.setLong(index++, auditUser);

            if (statement.executeUpdate() != 1) {
                throw new PhasePersistenceException("Audit information was not successfully saved.");
            }
        } catch (SQLException e) {
            throw new PhasePersistenceException("Unable to insert project_info_audit record.", e);
        } finally {
            close(statement);
        }
    }

}
