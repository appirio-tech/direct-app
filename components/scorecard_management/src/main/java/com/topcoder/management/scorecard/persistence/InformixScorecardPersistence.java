/*
 * Copyright (C) 2006-2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.scorecard.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.topcoder.db.connectionfactory.DBConnectionException;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.db.connectionfactory.UnknownConnectionException;
import com.topcoder.management.scorecard.ConfigurationException;
import com.topcoder.management.scorecard.PersistenceException;
import com.topcoder.management.scorecard.ScorecardIDInfo;
import com.topcoder.management.scorecard.ScorecardPersistence;
import com.topcoder.management.scorecard.data.Group;
import com.topcoder.management.scorecard.data.NamedScorecardStructure;
import com.topcoder.management.scorecard.data.QuestionType;
import com.topcoder.management.scorecard.data.Scorecard;
import com.topcoder.management.scorecard.data.ScorecardStatus;
import com.topcoder.management.scorecard.data.ScorecardType;
import com.topcoder.management.scorecard.persistence.logging.LogMessage;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.ConfigManagerException;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;
import com.topcoder.util.sql.databaseabstraction.CustomResultSet;
import com.topcoder.util.sql.databaseabstraction.InvalidCursorStateException;
import com.topcoder.util.sql.databaseabstraction.NullColumnValueException;


/**
 * This class contains operations to create and update scorecard instances into the Informix database. It
 * implements the ScorecardPersistence interface to provide a plug-in persistence for Informix database. It is used
 * by the ScorecardManagerImpl class. The constructor takes a namespace parameter to initialize database
 * connection. Note that in this class, delete operation is not supported. To delete the scorecard, its status is
 * set to 'Disabled'. The create and update operation here work on the scorecard and including its sub items as
 * well. It means creating/updating a scorecard would involve creating/updating its sub groups/sections and
 * questions. The get scorecard operation including a 'complete' parameter, so the scorecard can be retrieve with
 * or without its sub items. Thread Safety: The implementation is not thread safe in that two threads running the
 * same method will use the same statement and could overwrite each other's work.
 *
 * <p>
 * Changes in v1.0.3 (Cockpit Spec Review Backend Service Update v1.0):
 * - added flag so that container transaction demarcation can be used.
 * - LogManager is used instead of LogFactory.
 * </p>
 *
 * @author tuenm, kr00tki, George1, Angen, pulky
 *
 * @version 1.0.3
 */
public class InformixScorecardPersistence implements ScorecardPersistence {
    /** Logger instance using the class name as category */
    private static final Log logger = LogManager.getLog(InformixScorecardPersistence.class.getName());

    /**
     * Selects the scorecards ids that are in use.
     */
    private static final String SELECT_IN_USE_IDS = "SELECT pc.parameter FROM phase_criteria pc " +
        "JOIN phase_criteria_type_lu pct ON pc.phase_criteria_type_id = pct.phase_criteria_type_id " +
        "WHERE pct.name='Scorecard ID' AND pc.parameter IN ";

    /**
     * Selects the scorecards by ids.
     */
    private static final String SELECT_SCORECARD = "SELECT sc.scorecard_id, status.scorecard_status_id, " +
        "type.scorecard_type_id, sc.project_category_id, sc.name, sc.version, sc.min_score, " +
        "sc.max_score, sc.create_user, sc.create_date, sc.modify_user, sc.modify_date, " +
        "status.name AS StatusName, type.name AS TypeName FROM scorecard AS sc JOIN scorecard_type_lu AS type " +
        "ON sc.scorecard_type_id=type.scorecard_type_id JOIN scorecard_status_lu AS status ON " +
        "sc.scorecard_status_id=status.scorecard_status_id WHERE sc.scorecard_id IN ";

    /**
     * Selects the scorecards statuses.
     */
    private static final String SELECT_SCORECARD_STATUS = "SELECT scorecard_status_id, name FROM scorecard_status_lu";

    /**
     * Selects the question types.
     */
    private static final String SELECT_SCORECARD_QUESTION_TYPE = "SELECT scorecard_question_type_id, name " +
        "FROM scorecard_question_type_lu";

    /**
     * Selects the scorecards types.
     */
    private static final String SELECT_SCORECARD_TYPE = "SELECT scorecard_type_id, name FROM scorecard_type_lu";

    /**
     * Selects the default scorecards.
     */
    private static final String SELECT_DEFAULT_SCORECARDS_ID_INFO = "SELECT scorecard_type_id, scorecard_id " +
        "FROM default_scorecard WHERE project_category_id = ";

    /**
     * Updates the scorecard.
     */
    private static final String UPDATE_SCORECARD = "UPDATE scorecard SET scorecard_status_id = ?, " +
        "scorecard_type_id = ?, project_category_id = ?, name = ?, version = ?, min_score = ?, " +
        "max_score = ?, modify_user = ?, modify_date = ? WHERE scorecard_id = ?";

    /**
     * Insert the scorecard data.
     */
    private static final String INSERT_SCORECARD = "INSERT INTO scorecard(scorecard_id, scorecard_status_id, " +
        "scorecard_type_id, project_category_id, name, version, min_score, max_score, create_user, " +
        "create_date, modify_user, modify_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    /**
     * The factory instance that is used to create connection to the database. The Create, update, get scorecard
     * methods use this instance to create connection. This variable is initialized in the constructor, immutable
     * and can never be null.
     *
     */
    private final DBConnectionFactory factory;

    /**
     * <p>
     * This field will be used by create, update, get methods to retrieve database connection. It is initialized in
     * the constructor, immutable and can be either null or non-null. If is is null, default connection will be
     * created.
     * </p>
     *
     */
    private final String connectionName;

    /**
     * <p>
     * Represents whether this component should use manual commit or not.
     * </p>
     *
     * @since 1.0.3
     */
    private final Boolean useManualCommit = false;

    /**
     * Create a new instance of InformixScorecardPersistence. The passing namespace parameter will be used to get
     * the namespace to initialize DBConnectionFactory component. ConfigurationException will be thrown if the
     * 'ConnectionFactoryNS' property is missing in the given namespace.
     *
     * @param namespace The namespace to load connection setting.
     * @throws IllegalArgumentException if the input is null or empty string.
     * @throws ConfigurationException if error occurrs while loading configuration settings, or required
     *         configuration parameter is missing.
     * @throws PersistenceException if cannot initialize the connection to the database.
     */
    public InformixScorecardPersistence(String namespace)
        throws ConfigurationException, PersistenceException {
        if (namespace == null) {
            throw new IllegalArgumentException("namespace cannot be null.");
        }

        if (namespace.trim().length() == 0) {
            throw new IllegalArgumentException(
                "namespace cannot be empty String.");
        }

        try {
            ConfigManager cm = ConfigManager.getInstance();
            String factoryNs = cm.getString(namespace, "ConnectionFactoryNS");
            logger.log(Level.INFO,
                "read property ConnectionFactoryNS[" + factoryNs +
                "] from the namespace :" + namespace);
            factory = new DBConnectionFactoryImpl(factoryNs);
            connectionName = cm.getString(namespace, "ConnectionName");
            logger.log(Level.INFO,
                "read property ConnectionName[" + connectionName +
                "] from the namespace :" + namespace);
            IdGeneratorUtility.loadIdGenerators(namespace);
        } catch (ConfigManagerException ex) {
            logger.log(Level.FATAL,
                "Fails to create InformixScorecardPersistence.\n" +
                LogMessage.getExceptionStackTrace(ex));
            throw new ConfigurationException("Error occur while reading the configuration.",
                ex);
        } catch (com.topcoder.db.connectionfactory.ConfigurationException ex) {
            logger.log(Level.FATAL,
                "Fails to create InformixScorecardPersistence.\n" +
                LogMessage.getExceptionStackTrace(ex));
            throw new ConfigurationException("Error occur while creating the DbConnectionFactory.",
                ex);
        } catch (UnknownConnectionException ex) {
            logger.log(Level.FATAL,
                "Fails to create InformixScorecardPersistence.\n" +
                LogMessage.getExceptionStackTrace(ex));
            throw new PersistenceException("Error occur while creating the DbConnectionFactory.",
                ex);
        }
    }

    /**
     * <p>
     * Creates the database connection. If the <code>connectionName</code> is <code>null</code>, the default
     * will be created. The autoCommit flag will be set to <code>false</code>.
     * </p>
     *
     * @return the database connection.
     * @throws PersistenceException if connection couldn't be created.
     */
    private Connection createConnection() throws PersistenceException {
        try {
            Connection conn = (connectionName == null)
                ? factory.createConnection()
                : factory.createConnection(connectionName);

            if (connectionName == null) {
                logger.log(Level.INFO,
                    "create db connection with default connect name");
            } else {
                logger.log(Level.INFO,
                    "create db connection with connect name:" + connectionName);
            }

            if(useManualCommit) {
                conn.setAutoCommit(false);
            }

            return conn;
        } catch (DBConnectionException ex) {
            logger.log(Level.ERROR,
                "fail to create db connection.\n" +
                LogMessage.getExceptionStackTrace(ex));
            throw new PersistenceException("Error occur while creating database connection.",
                ex);
        } catch (SQLException ex) {
            logger.log(Level.ERROR,
                "fail to create db connection.\n" +
                LogMessage.getExceptionStackTrace(ex));
            throw new PersistenceException("Error occur while creating database connection.",
                ex);
        }
    }

    /**
     * Create the scorecard in the database using the given scorecard instance. The scorecard instance can include
     * sub items such as groups, sections and questions. Those sub items will be persisted as well. The operator
     * parameter is used as the creation/modification user of the scorecard and its subitems. The creation date and
     * modification date will be the current date time when the scorecard is created.
     *
     * @param scorecard The scorecard instance to be created in the database.
     * @param operator The creation user of this scorecard.
     * @throws IllegalArgumentException if any input is null or the operator is empty string.
     * @throws PersistenceException if error occurred while accessing the database.
     */
    public void createScorecard(Scorecard scorecard, String operator)
        throws PersistenceException {
        if (scorecard == null) {
            throw new IllegalArgumentException("scorecard cannot be null.");
        }

        if (operator == null) {
            throw new IllegalArgumentException("operator cannot be null.");
        }

        if (operator.trim().length() == 0) {
            throw new IllegalArgumentException(
                "operator cannot be empty String.");
        }

        logger.log(Level.INFO,
            new LogMessage("Scorecard", null, operator, "Create new Scorecard."));

        Connection conn = createConnection();

        // generate the id first
        long scorecardId = DBUtils.nextId(IdGeneratorUtility.getScorecardIdGenerator());

        try {
            // get the current time
            Timestamp time = new Timestamp(System.currentTimeMillis());
            // create scorecard
            createScorecard(conn, scorecard, scorecardId, operator, time);

            // create groups
            InformixGroupPersistence groupPersistence = new InformixGroupPersistence(conn);
            groupPersistence.createGroups(scorecard.getAllGroups(), operator,
                scorecardId);

            // set the audit data
            scorecard.setId(scorecardId);
            scorecard.setModificationTimestamp(time);
            scorecard.setCreationTimestamp(time);
            scorecard.setCreationUser(operator);
            scorecard.setModificationUser(operator);

            if(useManualCommit) {
                logger.log(Level.INFO, "commit the transaction.");
                conn.commit();
            }
        } catch (SQLException ex) {
            DBUtils.rollback(conn, useManualCommit);
            logger.log(Level.ERROR,
                new LogMessage("Scorecard", null, operator,
                    "Fail to create Scorecard.", ex));
            throw new PersistenceException("Error occur during create operation.",
                ex);
        } catch (PersistenceException ex) {
            DBUtils.rollback(conn, useManualCommit);
            logger.log(Level.ERROR,
                new LogMessage("Scorecard", null, operator,
                    "Fail to create Scorecard."));
            throw ex;
        } finally {
            DBUtils.close(conn);
        }
    }

    /**
     * Creates the scorecard in the database. Inserts the values into the scorecard table.
     *
     * @param conn the connection to be used.
     * @param scorecard the scorecard to be stored.
     * @param id the id of the scorecard.
     * @param operator the creation operator.
     * @param time the creation time.
     * @throws SQLException if database error occurs.
     */
    private static void createScorecard(Connection conn, Scorecard scorecard,
        long id, String operator, Timestamp time) throws SQLException {
        logger.log(Level.INFO, "insert record into scorecard with id:" + id);

        PreparedStatement pstmt = null;

        try {
            pstmt = conn.prepareStatement(INSERT_SCORECARD);

            pstmt.setLong(1, id);
            pstmt.setLong(2, scorecard.getScorecardStatus().getId());
            pstmt.setLong(3, scorecard.getScorecardType().getId());
            pstmt.setLong(4, scorecard.getCategory());
            pstmt.setString(5, scorecard.getName());
            pstmt.setString(6, scorecard.getVersion());
            pstmt.setFloat(7, scorecard.getMinScore());
            pstmt.setFloat(8, scorecard.getMaxScore());

            // set user and date
            pstmt.setString(9, operator);
            pstmt.setTimestamp(10, time);
            pstmt.setString(11, operator);
            pstmt.setTimestamp(12, time);

            pstmt.execute();
        } finally {
            DBUtils.close(pstmt);
        }
    }

    /**
     * Update the given scorecard instance into the database. The scorecard instance can include sub items such as
     * groups, sections and questions. Those sub items will be updated as well. If sub items are removed from the
     * scorecard, they will be deleted from the persistence. Likewise, if new sub items are added, they will be
     * created in the persistence. The operator parameter is used as the modification user of the scorecard and its
     * subitems. The modification date will be the current date time when the scorecard is updated.
     *
     *
     * @param scorecard The scorecard instance to be updated into the database.
     * @param operator The modification user of this scorecard.
     * @throws IllegalArgumentException if any input is null or the operator is empty string.
     * @throws PersistenceException if error occurred while accessing the database.
     */
    public void updateScorecard(Scorecard scorecard, String operator)
        throws PersistenceException {
        if (scorecard == null) {
            throw new IllegalArgumentException("scorecard cannot be null.");
        }

        if (operator == null) {
            throw new IllegalArgumentException("operator cannot be null.");
        }

        if (operator.trim().length() == 0) {
            throw new IllegalArgumentException(
                "operator cannot be empty String.");
        }

        logger.log(Level.INFO,
            new LogMessage("Scorecard", new Long(scorecard.getId()), operator,
                "update Scorecard."));

        Connection conn = createConnection();

        try {
            // get the old scorcard
            Scorecard oldScorecard = getScorecard(scorecard.getId(), true);

            if (oldScorecard.isInUse()) {
                logger.log(Level.ERROR,
                    new LogMessage("Scorecard", null, operator,
                        "The scorecard is in use."));
                throw new PersistenceException("The scorecard is in use. Id: " +
                    scorecard.getId());
            }

            // increment the version number
            String version = incrementVersion(oldScorecard.getVersion());

            // get old group id
            Set oldGroupsIds = getGroupsIds(oldScorecard);

            // create the group persistence
            InformixGroupPersistence groupPersistence = new InformixGroupPersistence(conn);

            // create update time
            Timestamp time = new Timestamp(System.currentTimeMillis());
            // update the scorecard data
            updateScorecard(conn, scorecard, operator, time, version);

            // get the new groups
            Group[] newGroups = scorecard.getAllGroups();
            List deletedSectionIds = new ArrayList();
            List deletedQuestionsIds = new ArrayList();

            // add all old groups to the delete list
            List deletedGroupsIds = new ArrayList(oldGroupsIds);

            for (int i = 0; i < newGroups.length; i++) {
                Long longId = new Long(newGroups[i].getId());

                // if is a new group - create it
                if (newGroups[i].getId() == NamedScorecardStructure.SENTINEL_ID) {
                    groupPersistence.createGroup(newGroups[i], i, operator,
                        scorecard.getId());
                } else if (oldGroupsIds.contains(longId)) {
                    // if this group exists - update it and remove from the delete list
                    groupPersistence.updateGroup(newGroups[i], i, operator,
                        scorecard.getId(), oldScorecard, deletedSectionIds,
                        deletedQuestionsIds);
                    deletedGroupsIds.remove(longId);
                }
            }

            // delete the groups
            if (deletedGroupsIds.size() > 0) {
                groupPersistence.deleteGroups(DBUtils.listToArray(
                        deletedGroupsIds));
            }

            // delete the sections
            if (deletedSectionIds.size() > 0) {
                new InformixSectionPersistence(conn).deleteSections(DBUtils.listToArray(
                        deletedSectionIds));
            }

            // delete the question
            if (deletedQuestionsIds.size() > 0) {
                new InformixQuestionPersistence(conn).deleteQuestions(DBUtils.listToArray(
                        deletedQuestionsIds));
            }

            if(useManualCommit) {
                logger.log(Level.INFO, "commit the transaction.");
                // commit transaction and set the modification user and date
                conn.commit();
            }
            scorecard.setVersion(version);
            scorecard.setModificationTimestamp(time);
            scorecard.setModificationUser(operator);
        } catch (SQLException ex) {
            DBUtils.rollback(conn, useManualCommit);

            String errMsg = scorecard + " op:" + operator;

            try {
                errMsg = "Scorecard Status: " +
                    scorecard.getScorecardStatus().getId() +
                    " - Scorecard Type: " +
                    scorecard.getScorecardType().getId() +
                    " - Scorecard Category: " + scorecard.getCategory() +
                    " - Scorecard Name: " + scorecard.getName() +
                    " - Scorecard Version: " + scorecard.getVersion() +
                    " - Scorecard Min Score: " + scorecard.getMinScore() +
                    " - Scorecard Max Score: " + scorecard.getMaxScore() +
                    " - Operator: " + operator + " - Scorecard ID: " +
                    scorecard.getId();
            } catch (Exception e) {
                logger.log(Level.ERROR,
                    new LogMessage("Scorecard", new Long(scorecard.getId()),
                        operator, "Failed to update Scorecard." + errMsg, ex));
                throw new PersistenceException("Couldn't format error output:" +
                    errMsg);
            }

            logger.log(Level.ERROR,
                new LogMessage("Scorecard", new Long(scorecard.getId()),
                    operator, "Failed to update Scorecard." + errMsg, ex));
            throw new PersistenceException(
                "Error occurs while deleting the scorecard: " + errMsg, ex);
        } catch (PersistenceException ex) {
            DBUtils.rollback(conn, useManualCommit);
            logger.log(Level.ERROR,
                new LogMessage("Scorecard", new Long(scorecard.getId()),
                    operator, "Fail to update Scorecard."));
            throw ex;
        } finally {
            DBUtils.close(conn);
        }
    }

    /**
     * Returns the set of gourp ids for given scorecard.
     *
     * @param oldScorecard the cource scorcard.
     * @return the set of groups ids.
     */
    private static Set getGroupsIds(Scorecard oldScorecard) {
        Set ids = new HashSet();
        Group[] groups = oldScorecard.getAllGroups();

        for (int i = 0; i < groups.length; i++) {
            ids.add(new Long(groups[i].getId()));
        }

        return ids;
    }

    /**
     * Updates the Scorecard in the database.
     *
     * @param conn the connection to use.
     * @param scorecard the scorecard to be updated.
     * @param operator the update operator.
     * @param time the operation timestamp.
     * @param version the scorecard version.
     * @throws SQLException if any database error occurs.
     */
    private static void updateScorecard(Connection conn, Scorecard scorecard,
        String operator, Timestamp time, String version)
        throws SQLException {
        logger.log(Level.INFO, "update scorecard with id : " +
            scorecard.getId());

        PreparedStatement pstmt = null;

        try {
            pstmt = conn.prepareStatement(UPDATE_SCORECARD);

            pstmt.setLong(1, scorecard.getScorecardStatus().getId());
            pstmt.setLong(2, scorecard.getScorecardType().getId());
            pstmt.setLong(3, scorecard.getCategory());
            pstmt.setString(4, scorecard.getName());
            pstmt.setString(5, version);
            pstmt.setFloat(6, scorecard.getMinScore());
            pstmt.setFloat(7, scorecard.getMaxScore());

            pstmt.setString(8, operator);
            pstmt.setTimestamp(9, time);
            pstmt.setLong(10, scorecard.getId());
            pstmt.executeUpdate();
        } finally {
            DBUtils.close(pstmt);
        }
    }

    /**
     * Increments the version minor number. If the version contains dot, the value after the last will be
     * incremented. In not, the ".1" will be added at the end.
     *
     * @param version the version to increment.
     * @return the incremented version.
     */
    private static String incrementVersion(String version) {
        int idx = version.lastIndexOf('.');

        if (idx == -1) {
            return version + ".1";
        }

        int minor = Integer.parseInt(version.substring(idx + 1)) + 1;

        return version.substring(0, idx) + "." + minor;
    }

    /**
     * Retrieves the scorecard instance from the persistence given its id. The scorecard instance can be retrieved
     * with or without its sub items, depends on the 'complete' parameter.
     *
     * @return The scorecard instance.
     * @param id The id of the scorecard to be retrieved.
     * @param complete Indicates whether to retrieve the scorecard including its sub items.
     * @throws IllegalArgumentException if the input id is less than or equal to zero.
     * @throws PersistenceException if error occurred while accessing the database.
     */
    public Scorecard getScorecard(long id, boolean complete)
        throws PersistenceException {
        Scorecard[] result = getScorecards(new long[] { id }, complete);

        if (result.length > 0) {
            return result[0];
        }

        return null;
    }

    /**
     * Retrieves all the scorecard types from the persistence.
     *
     * @return An array of scorecard type instances.
     * @throws PersistenceException if error occurred while accessing the database.
     */
    public ScorecardType[] getAllScorecardTypes() throws PersistenceException {
        logger.log(Level.INFO, "get all scorecard types.");

        Connection conn = createConnection();
        Statement stmt = null;
        ResultSet rs = null;

        try {
            // create statement and execute select query
            stmt = conn.createStatement();
            rs = stmt.executeQuery(SELECT_SCORECARD_TYPE);

            List result = new ArrayList();

            // for each row in the result set create new ScorecardType instance.
            while (rs.next()) {
                long id = rs.getLong("scorecard_type_id");
                String name = rs.getString("name");
                result.add(new ScorecardType(id, name));
            }

            // convert the list to array
            return (ScorecardType[]) result.toArray(new ScorecardType[result.size()]);
        } catch (SQLException ex) {
            logger.log(Level.ERROR,
                "Failed to get all scorecard types. \n" +
                LogMessage.getExceptionStackTrace(ex));
            throw new PersistenceException("Error occur during database operation.",
                ex);
        } finally {
            DBUtils.close(rs);
            DBUtils.close(stmt);
            DBUtils.close(conn);
        }
    }

    /**
     * Retrieves all the question types from the persistence.
     *
     * @return An array of question type instances.
     * @throws PersistenceException if error occurred while accessing the database.
     */
    public QuestionType[] getAllQuestionTypes() throws PersistenceException {
        logger.log(Level.INFO, "get all question types.");

        Connection conn = createConnection();
        Statement stmt = null;
        ResultSet rs = null;

        try {
            // create statement and execute select query
            stmt = conn.createStatement();
            rs = stmt.executeQuery(SELECT_SCORECARD_QUESTION_TYPE);

            List result = new ArrayList();

            // for each row in the result set create new QuestionType instance.
            while (rs.next()) {
                long id = rs.getLong("scorecard_question_type_id");
                String name = rs.getString("name");
                result.add(new QuestionType(id, name));
            }

            // convert the list to array
            return (QuestionType[]) result.toArray(new QuestionType[result.size()]);
        } catch (SQLException ex) {
            logger.log(Level.ERROR,
                "Failed to get all question types. \n" +
                LogMessage.getExceptionStackTrace(ex));
            throw new PersistenceException("Error occur during database operation.",
                ex);
        } finally {
            DBUtils.close(rs);
            DBUtils.close(stmt);
            DBUtils.close(conn);
        }
    }

    /**
     * Retrieves all the scorecard statuses from the persistence.
     *
     * @return An array of scorecard status instances.
     * @throws PersistenceException if error occurred while accessing the database.
     */
    public ScorecardStatus[] getAllScorecardStatuses()
        throws PersistenceException {
        logger.log(Level.INFO, "get all scorecard status.");

        Connection conn = createConnection();
        Statement stmt = null;
        ResultSet rs = null;

        try {
            // create statement and execute select query
            stmt = conn.createStatement();
            rs = stmt.executeQuery(SELECT_SCORECARD_STATUS);

            List result = new ArrayList();

            // for each row in the result set create new QuestionType instance.
            while (rs.next()) {
                long id = rs.getLong("scorecard_status_id");
                String name = rs.getString("name");
                result.add(new ScorecardStatus(id, name));
            }

            // convert the list to array
            return (ScorecardStatus[]) result.toArray(new ScorecardStatus[result.size()]);
        } catch (SQLException ex) {
            logger.log(Level.ERROR,
                "Failed to get all scorecard status. \n" +
                LogMessage.getExceptionStackTrace(ex));
            throw new PersistenceException("Error occur during database operation.",
                ex);
        } finally {
            DBUtils.close(rs);
            DBUtils.close(stmt);
            DBUtils.close(conn);
        }
    }

    /**
     * <p>
     * Retrieves an array of the scorecard instances from the persistence given their ids. The scorecard instances
     * can be retrieved with or without its sub items, depends on the 'complete' parameter.
     * </p>
     *
     * @param ids The array of ids of the scorecards to be retrieved.
     * @param complete Indicates whether to retrieve the scorecard including its sub items.
     * @return An array of scorecard instances.
     * @throws IllegalArgumentException if the ids is less than or equal to zero. Or the input array is null or
     *         empty.
     * @throws PersistenceException if error occurred while accessing the persistence.
     */
    public Scorecard[] getScorecards(long[] ids, boolean complete)
        throws PersistenceException {
        DBUtils.checkIdsArray(ids, "ids");

        logger.log(Level.INFO,
            new LogMessage("Scorecard", null, null,
                "retrieve scorecard with ids:" +
                InformixPersistenceHelper.generateIdString(ids) + ", and is " +
                (complete ? "complete" : "incomplete")));

        Connection conn = createConnection();

        try {
            Set inUseIds = selectInUse(ids, conn);
            List scorecards = getScorecards(conn, ids, inUseIds);

            if (complete) {
                InformixGroupPersistence groupPersistence = new InformixGroupPersistence(conn);

                for (Iterator it = scorecards.iterator(); it.hasNext();) {
                    Scorecard card = (Scorecard) it.next();
                    card.addGroups(groupPersistence.getGroups(card.getId()));
                }
            }

            return (Scorecard[]) scorecards.toArray(new Scorecard[scorecards.size()]);
        } finally {
            DBUtils.close(conn);
        }
    }

    /**
     * <p>
     * Retrieves an array of the scorecard instances from the persistence given their ids. The scorecard instances
     * can be retrieved with or without its sub items, depends on the 'complete' parameter.
     * </p>
     *
     * @param resultSet The result of the SELECT operation.
     * @param complete Indicates whether to retrieve the scorecard including its sub items.
     * @return An array of scorecard instances.
     * @throws IllegalArgumentException if the ids is less than or equal to zero. Or the input array is null or
     *         empty.
     * @throws PersistenceException if error occurred while accessing the persistence.
     */
    public Scorecard[] getScorecards(CustomResultSet resultSet, boolean complete)
        throws PersistenceException {
        if (resultSet == null) {
            throw new IllegalArgumentException("resultSet cannot be null.");
        }

        Connection conn = createConnection();
        InformixGroupPersistence groupPersistence = null;
        List scorecards = new ArrayList();

        if (complete) {
            groupPersistence = new InformixGroupPersistence(conn);
        }

        try {
            while (resultSet.next()) {
                Scorecard scorecard = populateScorecard(resultSet);

                if (groupPersistence != null) {
                    scorecard.addGroups(groupPersistence.getGroups(
                            scorecard.getId()));
                }

                scorecards.add(scorecard);
            }

            return (Scorecard[]) scorecards.toArray(new Scorecard[scorecards.size()]);
        } catch (InvalidCursorStateException icse) {
            throw new PersistenceException("Error occured fetching scorecards from the database.",
                icse);
        } catch (NullColumnValueException ne) {
            throw new PersistenceException("Error occured fetching scorecards from the database.",
                ne);
        }  finally {
            DBUtils.close(conn);
        }
    }

    /**
     * Retrieves the scorecard type ids and scorecard ids for a specific category
     * from default scorecards table.
     * This method takes as a parameter projectCategoryId, gets the
     * id information of scorecards for it, and returns an array of ScorecardIDInfo instances, each one
     * containing the scorecard type id and the scorecard id.
     *
     * @param projectCategoryId the id of the project category.
     *
     * @return the ScorecardIDInfo instances array, each one containing the scorecard type id and the scorecard id.
     *
     * @throws PersistenceException if error occurred while accessing the persistence.
     * @throws IllegalArgumentException if the projectCategoryId is less than or equal to zero.
     */
    public ScorecardIDInfo[] getDefaultScorecardsIDInfo(long projectCategoryId)
        throws PersistenceException {
        if (projectCategoryId <= 0) {
            throw new IllegalArgumentException(
                "The projectCategoryId should be positive.");
        }

        logger.log(Level.INFO,
            new LogMessage("Scorecard", null, null,
                "retrieve scorecards id info with projectCategoryId:" +
                projectCategoryId));

        Connection conn = createConnection();

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        List scorecardsInfo = new ArrayList();

        try {
            pstmt = conn.prepareStatement(SELECT_DEFAULT_SCORECARDS_ID_INFO +
                    projectCategoryId);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                scorecardsInfo.add(new ScorecardIDInfo(rs.getLong(1),
                        rs.getLong(2)));
            }

            return (ScorecardIDInfo[]) scorecardsInfo.toArray(new ScorecardIDInfo[scorecardsInfo.size()]);
        } catch (SQLException ex) {
            logger.log(Level.ERROR,
                new LogMessage("ScoreCard", null, null,
                    "Fails to retrieve scorecards id info with projectCategoryId:" +
                    projectCategoryId));
            throw new PersistenceException("Error occurs during database operation.",
                ex);
        } finally {
            DBUtils.close(rs);
            DBUtils.close(pstmt);
            DBUtils.close(conn);
        }
    }

    /**
     * Create the Scorecard instance using the data from the ResultSet object.
     *
     * @param rs the source result set.
     * @return the Scorecard instance.
     * @throws InvalidCursorStateException if any database error occurs.
     */
    private static Scorecard populateScorecard(CustomResultSet rs)
        throws InvalidCursorStateException, NullColumnValueException {
        Scorecard card = new Scorecard(rs.getLong("scorecard_id"));
        card.setCategory(rs.getLong("project_category_id"));
        card.setVersion(rs.getString("version"));
        card.setMinScore(rs.getFloat("min_score"));
        card.setMaxScore(rs.getFloat("max_score"));
        card.setName(rs.getString("scorecard_name"));

        ScorecardStatus status = new ScorecardStatus();
        status.setId(rs.getLong("status_id"));
        status.setName(rs.getString("status_name"));
        card.setScorecardStatus(status);

        card.setScorecardType(new ScorecardType(rs.getLong("type_id"),
                rs.getString("type_name")));

        card.setModificationUser(rs.getString("modify_user"));
        card.setCreationUser(rs.getString("create_user"));
        card.setCreationTimestamp(rs.getTimestamp("create_date"));
        card.setModificationTimestamp(rs.getTimestamp("modify_date"));

        return card;
    }

    /**
     * Returns the list of all scorecards with the id from the ids array.
     *
     * @param conn the database connection to use.
     * @param ids the scorecards ids.
     * @param inUseIds the set of scorecards in use ids.
     * @return the list of Scorecards.
     * @throws PersistenceException if any error occurs.
     */
    private static List getScorecards(Connection conn, long[] ids, Set inUseIds)
        throws PersistenceException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = conn.prepareStatement(SELECT_SCORECARD +
                    DBUtils.createQuestionMarks(ids.length));

            for (int i = 0; i < ids.length; i++) {
                pstmt.setLong(i + 1, ids[i]);
            }

            rs = pstmt.executeQuery();

            List result = new ArrayList();

            while (rs.next()) {
                Scorecard card = populateScorecard(rs);

                if (inUseIds.contains(new Long(card.getId()))) {
                    card.setInUse(true);
                }

                result.add(card);
            }

            return result;
        } catch (SQLException ex) {
            logger.log(Level.ERROR,
                new LogMessage("ScoreCard", null, null,
                    "Fails to retrieve scorecards:" +
                    InformixPersistenceHelper.generateIdString(ids) + ".", ex));
            throw new PersistenceException("Error occurs while retrieving scorecards.",
                ex);
        } finally {
            DBUtils.close(rs);
            DBUtils.close(pstmt);
        }
    }

    /**
     * Create the Scorecard instance using the data from the ResultSet object.
     *
     * @param rs the source result set.
     * @return the Scorecard instance.
     * @throws SQLException if any database error occurs.
     */
    private static Scorecard populateScorecard(ResultSet rs)
        throws SQLException {
        Scorecard card = new Scorecard(rs.getLong("scorecard_id"));
        card.setCategory(rs.getLong("project_category_id"));
        card.setVersion(rs.getString("version"));
        card.setMinScore(rs.getFloat("min_score"));
        card.setMaxScore(rs.getFloat("max_score"));
        card.setName(rs.getString("name"));

        ScorecardStatus status = new ScorecardStatus();
        status.setId(rs.getLong("scorecard_status_id"));
        status.setName(rs.getString("StatusName"));
        card.setScorecardStatus(status);

        card.setScorecardType(new ScorecardType(rs.getLong("scorecard_type_id"),
                rs.getString("TypeName")));

        card.setModificationUser(rs.getString("modify_user"));
        card.setCreationUser(rs.getString("create_user"));
        card.setCreationTimestamp(rs.getTimestamp("create_date"));
        card.setModificationTimestamp(rs.getTimestamp("modify_date"));

        return card;
    }

    /**
     * Checks if the scorecard is in use or not. It returns the set of ids that are in use.
     *
     * @param ids the scorecards ids to check.
     * @param conn the database connection to be used.
     * @return true if the scorecard is in use.
     * @throws PersistenceException if database error occurs.
     */
    private static Set selectInUse(long[] ids, Connection conn)
        throws PersistenceException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Set result = new HashSet();

        try {
            pstmt = conn.prepareStatement(SELECT_IN_USE_IDS +
                    DBUtils.createQuestionMarks(ids.length));

            for (int i = 0; i < ids.length; i++) {
                pstmt.setString(i + 1, String.valueOf(ids[i]));

                //pstmt.setLong(i + 1, ids[i]);
            }

            rs = pstmt.executeQuery();

            while (rs.next()) {
                result.add(new Long(rs.getString(1)));
            }

            return result;
        } catch (SQLException ex) {
            logger.log(Level.ERROR,
                new LogMessage("ScoreCard", null, null,
                    "Fails to check if the scorecards:" +
                    InformixPersistenceHelper.generateIdString(ids) +
                    " in use or not.", ex));
            throw new PersistenceException("Error occurs during database operation.",
                ex);
        } finally {
            DBUtils.close(rs);
            DBUtils.close(pstmt);
        }
    }
}
