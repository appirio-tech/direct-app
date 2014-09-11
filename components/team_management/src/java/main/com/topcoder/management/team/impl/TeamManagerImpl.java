/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.team.impl;

import com.topcoder.management.team.Helper;
import com.topcoder.management.team.InvalidPositionException;
import com.topcoder.management.team.InvalidTeamException;
import com.topcoder.management.team.PositionRemovalException;
import com.topcoder.management.team.Team;
import com.topcoder.management.team.TeamConfigurationException;
import com.topcoder.management.team.TeamHeader;
import com.topcoder.management.team.TeamManager;
import com.topcoder.management.team.TeamPersistence;
import com.topcoder.management.team.TeamPersistenceException;
import com.topcoder.management.team.TeamPosition;
import com.topcoder.management.team.UnknownEntityException;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.UnknownNamespaceException;
import com.topcoder.util.idgenerator.IDGenerationException;
import com.topcoder.util.idgenerator.IDGenerator;
import com.topcoder.util.idgenerator.IDGeneratorFactory;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;
import com.topcoder.util.objectfactory.InvalidClassSpecificationException;
import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.objectfactory.impl.ConfigManagerSpecificationFactory;
import com.topcoder.util.objectfactory.impl.IllegalReferenceException;
import com.topcoder.util.objectfactory.impl.SpecificationConfigurationException;

/**
 * <p>
 * Full implementation of the TeamManager interface. This implementation makes use of some TopCoder components to
 * perform configuration and its business methods. It makes use of the Config Manager and Object Factory components to
 * hold properties and obtain class instances, respectively. One such class instance is the TeamPersistence, which is
 * used for all actual persistence operations. The ID Generator component is used to generate IDs for new teams and
 * positions, and the Logging Wrapper is used to log most operations in a method, such as entry and exit, calls to
 * other components, etc.
 * </p>
 * <p>
 * All methods are implemented and supported. Also provided are configuration-backed and programmatic constructors.
 * This allows the user to either create all internal supporting objects from configuration, or to simply pass the
 * instance directly.
 * </p>
 * <p>
 * All teams and positions are validated when they are created and updated. The specific rules are available in the
 * affected methods
 * </p>
 * <p>
 * Below is a sample configuration file for this class.
 * <pre>
 *  &lt;CMConfig&gt;
 *      &lt;!-- Namespace for TeamManagerImpl class --&gt;
 *      &lt;Config name=&quot;com.topcoder.management.team.impl.TeamManagerImpl&quot;&gt;
 *          &lt;Property name=&quot;specNamespace&quot;&gt;
 *              &lt;Value&gt;of.persistence&lt;/Value&gt;
 *          &lt;/Property&gt;
 *          &lt;Property name=&quot;persistenceKey&quot;&gt;
 *              &lt;Value&gt;MockTeamPersistence&lt;/Value&gt;
 *          &lt;/Property&gt;
 *          &lt;Property name=&quot;loggerName&quot;&gt;
 *              &lt;Value&gt;com.topcoder.util.log&lt;/Value&gt;
 *          &lt;/Property&gt;
 *          &lt;Property name=&quot;idGenerator&quot;&gt;
 *              &lt;Value&gt;team_manager_id_generator&lt;/Value&gt;
 *          &lt;/Property&gt;
 *      &lt;/Config&gt;
 *
 *      &lt;!-- Namespace for ObjectFactory component --&gt;
 *      &lt;Config name=&quot;of.persistence&quot;&gt;
 *          &lt;Property name=&quot;MockTeamPersistence&quot;&gt;
 *              &lt;Property name=&quot;type&quot;&gt;
 *                  &lt;Value&gt;com.topcoder.management.team.MockTeamPersistence&lt;/Value&gt;
 *              &lt;/Property&gt;
 *              &lt;Property name=&quot;params&quot;&gt;
 *              &lt;/Property&gt;
 *          &lt;/Property&gt;
 *      &lt;/Config&gt;
 *
 *    &lt;Config name=&quot;com.topcoder.db.connectionfactory.DBConnectionFactoryImpl&quot;&gt;
 *      &lt;Property name=&quot;connections&quot;&gt;
 *          &lt;Property name=&quot;default&quot;&gt;
 *              &lt;Value&gt;IDGeneratorImpl&lt;/Value&gt;
 *          &lt;/Property&gt;
 *
 *          &lt;Property name=&quot;IDGeneratorImpl&quot;&gt;
 *              &lt;Property name=&quot;producer&quot;&gt;
 *                  &lt;Value&gt;com.topcoder.db.connectionfactory.producers.JDBCConnectionProducer&lt;/Value&gt;
 *              &lt;/Property&gt;
 *              &lt;Property name=&quot;parameters&quot;&gt;
 *                  &lt;Property name=&quot;jdbc_driver&quot;&gt;
 *                      &lt;Value&gt;com.informix.jdbc.IfxDriver&lt;/Value&gt;
 *                  &lt;/Property&gt;
 *                  &lt;Property name=&quot;jdbc_url&quot;&gt;
 *                      &lt;Value&gt;jdbc:informix-sqli://mycomputer:1526/team:INFORMIXSERVER=ol_team&lt;/Value&gt;
 *                  &lt;/Property&gt;
 *                  &lt;Property name=&quot;user&quot;&gt;
 *                      &lt;Value&gt;informix&lt;/Value&gt;
 *                  &lt;/Property&gt;
 *                  &lt;Property name=&quot;password&quot;&gt;
 *                      &lt;Value&gt;password&lt;/Value&gt;
 *                  &lt;/Property&gt;
 *              &lt;/Property&gt;
 *          &lt;/Property&gt;
 *      &lt;/Property&gt;
 *    &lt;/Config&gt;
 *  &lt;/CMConfig&gt;
 * </pre>
 *
 * </p>
 * <p>
 * Thread Safety: This class is immutable but operates on non thread safe objects, thus making it potentially non
 * thread safe.
 * </p>
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
public class TeamManagerImpl implements TeamManager {
    /**
     * Represents the default namespace used by the default constructor to access configuration info in the
     * construction.
     */
    public static final String DEFAULT_NAMESPACE = "com.topcoder.management.team.impl.TeamManagerImpl";

    /** Name for specNamespace string in the config. */
    private static final String SPEC_NAMESPACE = "specNamespace";

    /** Name for persistenceKey string in the config. */
    private static final String PERSISTENCE_KEY = "persistenceKey";

    /** Name for idGenerator string in the config. */
    private static final String ID_GENERATOR = "idGenerator";

    /** Name for loggerName string in the config. */
    private static final String LOGGER_NAME = "loggerName";

    /** Min length for team/position names. */
    private static final int NAME_MINLEN = 5;

    /** Max length for team/position names. */
    private static final int NAME_MAXLEN = 31;

    /** Max length for team/position descriptions. */
    private static final int DESCRIPTION_MAXLEN = 255;

    /**
     * <p>
     * Represents the ID generator for teams and positions. It will be used in the createTeam and addPosition methods.
     * </p>
     * <p>
     * It will be set in the constructor to a non-null value, and will not change.
     * </p>
     */
    private final IDGenerator idGenerator;

    /**
     * <p>
     * Represents the TeamPersistence that will perform all actual persistence of teams and their positions in all
     * methods.
     * </p>
     * <p>
     * It will be set in the constructor to a non-null value, and will not change.
     * </p>
     */
    private final TeamPersistence persistence;

    /**
     * <p>
     * Used extensively in this class to log information. This will include logging method entry and exit, errors,
     * debug information for calls to other components, etc. A more detailed explanation is provided in the CS section
     * 1.4.1.
     * </p>
     * <p>
     * Note that logging is optional and can be null, in which case, no logging will take place. It will be set in the
     * constructor and will not change.
     * </p>
     */
    private final Log logger;

    /**
     * Default constructor.
     * @throws TeamConfigurationException
     *             If any configuration error occurs, such as unknown namespace, or missing required values, or errors
     *             while constructing the persistence or logger.
     */
    public TeamManagerImpl() {
        this(DEFAULT_NAMESPACE);
    }

    /**
     * <p>
     * Namespace constructor. Initializes this instance from configuration info from the Config Manager. It will use
     * the Object Factory to create instance of TeamPersistence, the IDGeneratorFactory to get the IDGenerator, and
     * LogManager to get the Log instance. More details can be found in CS 3.2.1.
     * </p>
     * @param namespace
     *            The configuration namespace
     * @throws IllegalArgumentException
     *             If namespace is null/empty
     * @throws TeamConfigurationException
     *             If any configuration error occurs, such as unknown namespace, or missing required values, or errors
     *             while constructing the persistence or logger.
     */
    public TeamManagerImpl(String namespace) throws TeamConfigurationException {
        Helper.assertStringNotNullOrEmpty(namespace, "namespace");
        // Get the instance of ConfigManager
        ConfigManager cm = ConfigManager.getInstance();
        try {
            // Create an object factory
            ObjectFactory of = new ObjectFactory(new ConfigManagerSpecificationFactory(myCMGetString(cm, namespace,
                SPEC_NAMESPACE)));

            // Create the persistence
            persistence = (TeamPersistence) of.createObject(myCMGetString(cm, namespace, PERSISTENCE_KEY));

            // Prepare the logger
            String loggerName = cm.getString(namespace, LOGGER_NAME);
            if (loggerName == null || loggerName.trim().length() == 0) {
                logger = null;
            } else {
                logger = LogManager.getLog(loggerName);
            }

            // Prepare the idGenerator
            idGenerator = IDGeneratorFactory.getIDGenerator(myCMGetString(cm, namespace, ID_GENERATOR));
        } catch (ClassCastException cce) {
            throw new TeamConfigurationException("Error while class casting", cce);
        } catch (SpecificationConfigurationException sce) {
            throw new TeamConfigurationException("Error while specifying factory", sce);
        } catch (IllegalReferenceException ire) {
            throw new TeamConfigurationException("Error while referring", ire);
        } catch (UnknownNamespaceException une) {
            throw new TeamConfigurationException("Unknown namespace", une);
        } catch (InvalidClassSpecificationException icse) {
            throw new TeamConfigurationException("Invalid class specified", icse);
        } catch (IDGenerationException idge) {
            throw new TeamConfigurationException("Error while creating idGenerator", idge);
        }
    }

    /**
     * <p>
     * Parameter constructor. logger can be null.
     * </p>
     * @param persistence
     *            the TeamPersistence that will perform all actual persistence of teams and their positions in all
     *            methods
     * @param idGenerator
     *            the ID generator for teams and positions
     * @param logger
     *            used to log information
     * @throws IllegalArgumentException
     *             if persistence or idGenerator is null
     */
    public TeamManagerImpl(TeamPersistence persistence, IDGenerator idGenerator, Log logger) {
        Helper.assertObjectNotNull(persistence, "persistence");
        Helper.assertObjectNotNull(idGenerator, "idGenerator");
        this.persistence = persistence;
        this.idGenerator = idGenerator;
        this.logger = logger;
    }

    /**
     * <p>
     * A private method for getting string from config manager. This method will check if the return value is empty
     * and throw ChatContactConfigurationException. UnknownNamespaceException will be simply rethrown to the upper
     * method.
     * </p>
     * @param cm
     *            the given config manager
     * @param namespace
     *            the given namespace
     * @param name
     *            the property name
     * @return the property value retrieved
     * @throws TeamConfigurationException
     *             if the retrieved property value is empty, or if getString throws UnknownNamespaceException
     */
    private static String myCMGetString(ConfigManager cm, String namespace, String name)
        throws TeamConfigurationException {
        try {
            // Get the property string from the manager, and check invalid
            String res = cm.getString(namespace, name);
            if (res == null || res.trim().length() == 0) {
                throw new TeamConfigurationException("The \"" + name + "\" property value must not be empty");
            }
            return res;
        } catch (UnknownNamespaceException nae) {
            throw new TeamConfigurationException("Namespace not found", nae);
        }
    }

    /**
     * <p>
     * A helper method to log the message only when logger is not null. Class name here will be appended before the
     * message. If e is not null, logger.log(level, e, message) will be used instead of simply log(level, message).
     * </p>
     * @param level
     *            the level of this log
     * @param e
     *            an exception to pass to the logger, if null, logger.log(level, message) will be used without e
     * @param message
     *            the message to be logged
     */
    private void log(Level level, Throwable e, String message) {
        if (logger != null) {
            if (e == null) {
                logger.log(level, level.toString() + "-" + TeamManagerImpl.class.getName() + "#" + message);
            } else {
                logger.log(level, e, level.toString() + "-" + TeamManagerImpl.class.getName() + "#" + message);
            }
        }
    }

    /**
     * Log an entry event message.
     * @param method
     *            the method who invokes this log method
     */
    private void logEntry(String method) {
        log(Level.INFO, null, method + ": enter");
    }

    /**
     * Log an exit event message.
     * @param method
     *            the method who invokes this log method
     */
    private void logExit(String method) {
        log(Level.INFO, null, method + ": exit");
    }

    /**
     * Log an entry event of an invoking.
     * @param method
     *            the method who invokes this log method
     * @param subMethod
     *            the method to be logged who is invoked
     */
    private void logInvokeEntry(String method, String subMethod) {
        log(Level.DEBUG, null, method + ": start invoking " + subMethod);
    }

    /**
     * Log an exit event of an invoking.
     * @param method
     *            the method who invokes this log method
     * @param subMethod
     *            the method to be logged who is invoked
     */
    private void logInvokeExit(String method, String subMethod) {
        log(Level.DEBUG, null, method + ": finish invoking " + subMethod);
    }

    /**
     * <p>
     * Throw an <code>InvalidTeamException</code> and log this exception at ERROR level.
     * </p>
     * @param method
     *            the method who invokes this log method
     * @param msg
     *            the error message
     * @throws InvalidTeamException
     *             to upper method
     */
    private void throwInvalidTeamException(String method, String msg) throws InvalidTeamException {
        InvalidTeamException e = new InvalidTeamException(msg);
        log(Level.ERROR, e, method + ": InvalidTeamException(" + msg + ")");
        throw e;
    }

    /**
     * <p>
     * Throw an <code>InvalidPositionException</code> and log this exception at ERROR level.
     * </p>
     * @param method
     *            the method who invokes this log method
     * @param msg
     *            the error message
     * @throws InvalidPositionException
     *             to upper method
     */
    private void throwInvalidPositionException(String method, String msg) throws InvalidPositionException {
        InvalidPositionException e = new InvalidPositionException(msg);
        log(Level.ERROR, e, method + ": InvalidPositionException(" + msg + ")");
        throw e;
    }

    /**
     * <p>
     * Throw an <code>UnknownEntityException</code> and log this exception at ERROR level.
     * </p>
     * @param method
     *            the method who invokes this log method
     * @param msg
     *            the error message
     * @throws UnknownEntityException
     *             to upper method
     */
    private void throwUnknownEntityException(String method, String msg) throws UnknownEntityException {
        UnknownEntityException e = new UnknownEntityException(msg);
        log(Level.ERROR, e, method + ": UnknownEntityException(" + msg + ")");
        throw e;
    }

    /**
     * <p>
     * Throw a <code>PositionRemovalException</code> and log this exception at ERROR level.
     * </p>
     * @param method
     *            the method who invokes this log method
     * @param msg
     *            the error message
     * @throws PositionRemovalException
     *             to upper method
     */
    private void throwPositionRemovalException(String method, String msg) throws PositionRemovalException {
        PositionRemovalException e = new PositionRemovalException(msg);
        log(Level.ERROR, e, method + ": PositionRemovalException(" + msg + ")");
        throw e;
    }

    /**
     * <p>
     * Throw an <code>IllegalArgumentException</code> and log this exception at ERROR level.
     * </p>
     * @param method
     *            the method who invokes this log method
     * @param msg
     *            the error message
     * @throws IllegalArgumentException
     *             to upper method
     */
    private void throwIllegalArgumentException(String method, String msg) {
        IllegalArgumentException e = new IllegalArgumentException(msg);
        log(Level.ERROR, e, method + ": IllegalArgumentException(" + msg + ")");
        throw e;
    }

    /**
     * <p>
     * Assert the given number is not negative.
     * </p>
     * @param number
     *            the number to be checked
     * @param name
     *            the name for this number
     * @param method
     *            the method that invokes this method
     * @throws IllegalArgumentException
     *             if number is negative.
     */
    private void assertNotNegative(long number, String name, String method) {
        if (number < 0) {
            throwIllegalArgumentException(method, name + " should be non-negative. (" + name + "=" + number + ")");
        }
    }

    /**
     * <p>
     * A helper method to generate an id from idGenerator. Entry and exit to idGenerator#getNextId will be logged
     * here, and IDGenerationException will be wrapped to TeamPersistenceException and logged also.
     * </p>
     * @param method
     *            the method who invokes this generateId method
     * @return the generated id
     * @throws TeamPersistenceException
     *             if error occurs while generating the id
     */
    private long generateId(String method) throws TeamPersistenceException {
        // Log the invoke of getNextId method at DEBUG level
        logInvokeEntry(method, "idGenerator.getNextId()");
        try {
            return idGenerator.getNextID();
        } catch (IDGenerationException idge) {
            // Log the potential id generation exception
            TeamPersistenceException e = new TeamPersistenceException("error while generating id", idge);
            log(Level.ERROR, idge, method + ": " + e.getMessage());
            throw e;
        } finally {
            logInvokeExit(method, "idGenerator.getNextId()");
        }
    }

    /**
     * <p>
     * Delegate the parameters to the persistence. Besides which, method invoking will be logged, and potential
     * TeamPersistenceException will be logged and re-thrown.
     * </p>
     * @return Team with the given ID, or null if none found
     * @param method
     *            the method who invokes the current method (to be used in log text)
     * @param teamId
     *            The ID of the team to be retrieved
     * @throws IllegalArgumentException
     *             If teamId is negative
     * @throws TeamPersistenceException
     *             If any system error, such as connection trouble, occurs
     */
    private Team myGetTeam(String method, long teamId) throws TeamPersistenceException {
        logInvokeEntry(method, "persistence.getTeam");
        try {
            // Delegate it to the persistence
            return persistence.getTeam(teamId);
        } catch (TeamPersistenceException e) {
            log(Level.ERROR, e, "error while getting team");
            throw e;
        } finally {
            logInvokeExit(method, "persistence.getTeam");
        }
    }

    /**
     * <p>
     * Delegate the parameters to the persistence. Besides which, method invoking will be logged, and potential
     * TeamPersistenceException will be logged and re-thrown.
     * </p>
     * @return Team that contains the position with the given ID
     * @param method
     *            the method who invokes the current method (to be used in log text)
     * @param positionId
     *            The ID of the position whose team is to be retrieved
     * @throws IllegalArgumentException
     *             If positionId is negative
     * @throws TeamPersistenceException
     *             If any system error, such as connection trouble, occurs
     */
    private Team myGetTeamFromPosition(String method, long positionId) throws TeamPersistenceException {
        logInvokeEntry(method, "persistence.getTeamFromPosition");
        try {
            // Delegate it to the persistence
            return persistence.getTeamFromPosition(positionId);
        } catch (TeamPersistenceException e) {
            log(Level.ERROR, e, "error while getting team from position");
            throw e;
        } finally {
            logInvokeExit(method, "persistence.getTeamFromPosition");
        }
    }

    /**
     * <p>
     * Delegate the parameters to the persistence. Besides which, method invoking will be logged, and potential
     * TeamPersistenceException will be logged and re-thrown.
     * </p>
     * @return TeamPosition with the given ID, or null if none found
     * @param method
     *            the method who invokes the current method (to be used in log text)
     * @param positionId
     *            The ID of the position to get
     * @throws IllegalArgumentException
     *             If positionId is negative
     * @throws TeamPersistenceException
     *             If any system error, such as connection trouble, occurs
     */
    private TeamPosition myGetPosition(String method, long positionId) throws TeamPersistenceException {
        logInvokeEntry(method, "persistence.getTeam");
        try {
            // Delegate it to the persistence
            return persistence.getPosition(positionId);
        } catch (TeamPersistenceException e) {
            log(Level.ERROR, e, "error while getting team");
            throw e;
        } finally {
            logInvokeExit(method, "persistence.getTeam");
        }
    }

    /**
     * Validate team. Any failure will result in InvalidTeamException.
     * <ul type="disc">
     * <li>team name not null/empty and 5-31 chars</li>
     * <li>team description can be null/empty, or no longer than 255 chars</li>
     * <li>captainResourceId and projectId must be filled (non-negative)</li>
     * <li>captainPaymentPercentage must be positive number that is not greater than 100</li>
     * </ul>
     * @param team
     *            the team to be validated
     * @throws IllegalArgumentException
     *             If team is null
     * @throws InvalidTeamException
     *             If the team contains invalid data
     */
    private void validateTeam(TeamHeader team) throws InvalidTeamException {
        logEntry("validateTeam");
        try {
            // Validate the team as what is mentioned in the method doc.
            if (team == null) {
                throwIllegalArgumentException("validateTeam", "team must not be null");
            }
            if (team.getName() == null || team.getName().trim().length() == 0) {
                throwInvalidTeamException("validateTeam", "team name must not be null or empty");
            }
            if (team.getName().length() < NAME_MINLEN || team.getName().length() > NAME_MAXLEN) {
                throwInvalidTeamException("validateTeam", "team name must contain " + NAME_MINLEN + "-" + NAME_MAXLEN
                    + " chars");
            }
            if (team.getDescription() != null && team.getDescription().length() > DESCRIPTION_MAXLEN) {
                throwInvalidTeamException("validateTeam", "team description must be no longer than "
                    + DESCRIPTION_MAXLEN + " chars");
            }
            if (team.getProjectId() < 0) {
                throwInvalidTeamException("validateTeam", "projectId must be filled (non-negative)");
            }
            if (team.getCaptainResourceId() < 0) {
                throwInvalidTeamException("validateTeam", "captainResourceId must be filled (non-negative)");
            }
            if (team.getCaptainPaymentPercentage() <= 0) {
                // No need to check if percentage is greater than 100, since setter has already checked it.
                throwInvalidTeamException("validateTeam", "captainPaymentPercentage must be positive number");
            }
        } finally {
            logExit("validateTeam");
        }
    }

    /**
     * <p>
     * Creates the team. A new team id will be appended to this team. Here are the restrictions on team:
     * <ul type="disc">
     * <li>team name not null/empty and 5-31 chars</li>
     * <li>team description can be null/empty, or no longer than 255 chars</li>
     * <li>captainResourceId and projectId must be filled (non-negative)</li>
     * <li>captainPaymentPercentage must be positive number that is not greater than 100</li>
     * <li>finalized flag must be false</li>
     * </ul>
     * </p>
     * @param team
     *            The team to be created
     * @param userId
     *            The user Id for logging and auditing purposes
     * @throws IllegalArgumentException
     *             If team is null or userId is negative
     * @throws InvalidTeamException
     *             If the team contains invalid data
     * @throws TeamPersistenceException
     *             If any system error, such as an inability to access the persistence properly.
     */
    public void createTeam(TeamHeader team, long userId) throws InvalidTeamException, TeamPersistenceException {
        logEntry("createTeam");
        try {
            assertNotNegative(userId, "userId", "createTeam");
            validateTeam(team);
            // Team must not be finalized
            if (team.getFinalized()) {
                throwInvalidTeamException("createTeam", "finalized flag must be false");
            }
            // Generate a team id
            team.setTeamId(generateId("createTeam"));

            logInvokeEntry("createTeam", "persistence.createTeam");
            try {
                // Delegate it to the persistence
                persistence.createTeam(team, userId);
            } catch (TeamPersistenceException e) {
                log(Level.ERROR, e, "error while creating team");
                throw e;
            } finally {
                logInvokeExit("createTeam", "persistence.createTeam");
            }
        } finally {
            logExit("creatTeam");
        }
    }

    /**
     * <p>
     * Updates the team. The team id must be present before this update. Here are the restrictions on team:
     * <ul type="disc">
     * <li>team name not null/empty and 5-31 chars</li>
     * <li>team description can be null/empty, or no longer than 255 chars</li>
     * <li>captainResourceId and projectId must be filled (non-negative)</li>
     * <li>captainPaymentPercentage must be positive number that is not greater than 100</li>
     * <li>captainResourceId must not be the same as any position's memberresourceId</li>
     * <li>sum of captainPaymentPercentage and all positions' paymentPercentagemust not be greater than 100</li>
     * </ul>
     * </p>
     * @param team
     *            The team to be updated
     * @param userId
     *            The user Id for logging and auditing purposes
     * @throws IllegalArgumentException
     *             If team is null or userId is negative, or if the team's teamId is negative
     * @throws InvalidTeamException
     *             If the team contains invalid data
     * @throws UnknownEntityException
     *             If the team id is not present in the database
     * @throws TeamPersistenceException
     *             If any system error, such as an inability to access the persistence properly.
     */
    public void updateTeam(TeamHeader team, long userId) throws InvalidTeamException, UnknownEntityException,
        TeamPersistenceException {
        logEntry("updateTeam");
        try {
            // Check IAEs
            assertNotNegative(userId, "userId", "updateTeam");
            if (team == null) {
                throwIllegalArgumentException("updateTeam", "team must not be null");
            }

            // Check if the team id is valid
            if (team.getTeamId() < 0) {
                throwIllegalArgumentException("updateTeam", "given team's id is invalid");
            }
            Team oldTeam = myGetTeam("updateTeam", team.getTeamId());
            if (oldTeam == null) {
                throwUnknownEntityException("updateTeam", "given team's id is not present");
            }

            validateTeam(team);

            // Get the positions for this team
            TeamPosition[] positions = oldTeam.getPositions();
            int sum = 0;
            for (int i = 0; i < positions.length; i++) {
                // The resource id must be unique in this team
                if (team.getCaptainResourceId() == positions[i].getMemberResourceId()) {
                    throwInvalidTeamException("updateTeam", "team's captain resource id must be unique in the team");
                }
                sum += positions[i].getPaymentPercentage();
            }
            // The total percentage must not be larger than 100
            if (team.getCaptainPaymentPercentage() + sum > Helper.HUNDRED) {
                throwInvalidTeamException("updateTeam",
                    "the sum of captain and positions' payment percentage must not be greater than 100");
            }

            // Delegate to the persistence
            logInvokeEntry("updateTeam", "persistence.updateTeam");
            try {
                // Delegate it to the persistence
                persistence.updateTeam(team, userId);
            } catch (TeamPersistenceException e) {
                log(Level.ERROR, e, "error while updating team");
                throw e;
            } finally {
                logInvokeExit("updateTeam", "persistence.updateTeam");
            }
        } finally {
            logExit("updateTeam");
        }
    }

    /**
     * <p>
     * Removes the team and all positions associated with it. UnknownEntityException will be thrown if teamId is
     * unknown.
     * </p>
     * @param teamId
     *            The ID of the team to be removed
     * @param userId
     *            The user Id for logging and auditing purposes
     * @throws IllegalArgumentException
     *             If teamId or userId is negative
     * @throws UnknownEntityException
     *             If teamId is unknown
     * @throws TeamPersistenceException
     *             If any system error, such as an inability to access the persistence properly.
     */
    public void removeTeam(long teamId, long userId) throws UnknownEntityException, TeamPersistenceException {
        logEntry("removeTeam");
        try {
            assertNotNegative(userId, "userId", "removeTeam");

            // Check if the teamId is unknown
            if (myGetTeam("removeTeam", teamId) != null) {
                // Delegate to the persistence
                logInvokeEntry("removeTeam", "persistence.removeTeam");
                try {
                    // Delegate it to the persistence
                    persistence.removeTeam(teamId, userId);
                } catch (TeamPersistenceException e) {
                    log(Level.ERROR, e, "error while removing team");
                    throw e;
                } finally {
                    logInvokeExit("removeTeam", "persistence.removeTeam");
                }
            } else {
                throwUnknownEntityException("removeTeam", "teamId " + teamId + " is unknown");
            }
        } finally {
            logExit("removeTeam");
        }
    }

    /**
     * <p>
     * Retrieves the team and all associated positions. Returns null if it does not exist.
     * </p>
     * @return Team with the given ID, or null if none found
     * @param teamId
     *            The ID of the team to be retrieved
     * @throws IllegalArgumentException
     *             If teamId is negative
     * @throws TeamPersistenceException
     *             If any system error, such as an inability to access the persistence properly.
     */
    public Team getTeam(long teamId) throws TeamPersistenceException {
        logEntry("getTeam");
        try {
            return myGetTeam("getTeam", teamId);
        } finally {
            logExit("getTeam");
        }
    }

    /**
     * <p>
     * Finds all teams associated with the project with the given ID. Returns empty array if none found.
     * </p>
     * @return An array of matching TeamHeader, or empty if no matches found
     * @param projectId
     *            The ID of the project whose teams are to be retrieved
     * @throws IllegalArgumentException
     *             If projectId is negative
     * @throws TeamPersistenceException
     *             If any system error, such as an inability to access the persistence properly.
     */
    public TeamHeader[] findTeams(long projectId) throws TeamPersistenceException {
        logEntry("findTeams(long projectId)");
        logInvokeEntry("findTeams", "persistence.findTeams(long)");
        try {
            // Delegate it to the persistence
            return persistence.findTeams(projectId);
        } catch (TeamPersistenceException e) {
            log(Level.ERROR, e, "error while finding team");
            throw e;
        } finally {
            logInvokeExit("findTeams", "persistence.findTeams(long)");
            logExit("findTeams(long projectId)");
        }
    }

    /**
     * <p>
     * Finds all teams associated with the projects with the given IDs. Returns empty array if none found.
     * </p>
     * @return An array of matching TeamHeader, or empty if no matches found
     * @param projectIds
     *            The IDs of the projects whose teams are to be retrieved
     * @throws IllegalArgumentException
     *             If projectIds is null or contains any negative IDs
     * @throws TeamPersistenceException
     *             If any system error, such as an inability to access the persistence properly.
     */
    public TeamHeader[] findTeams(long[] projectIds) throws TeamPersistenceException {
        logEntry("findTeams(long[] projectIds)");
        logInvokeEntry("findTeams", "persistence.findTeams(long[])");
        try {
            // Delegate it to the persistence
            return persistence.findTeams(projectIds);
        } catch (TeamPersistenceException e) {
            log(Level.ERROR, e, "error while finding team");
            throw e;
        } finally {
            logInvokeExit("findTeams", "persistence.findTeams(long[])");
            logExit("findTeams(long[] projectIds)");
        }
    }

    /**
     * <p>
     * Finds all teams that match the criteria in the passed filter. Returns empty array if none found.
     * </p>
     * @return An array of matching TeamHeader, or empty if no matches found
     * @param filter
     *            The filter criteria to match teams
     * @throws IllegalArgumentException
     *             If filter is null
     * @throws TeamPersistenceException
     *             If any system error, such as an inability to access the persistence properly.
     */
    public TeamHeader[] findTeams(Filter filter) throws TeamPersistenceException {
        logEntry("findTeams(Filter filter)");
        logInvokeEntry("findTeams", "persistence.findTeams(Filter)");
        try {
            // Delegate it to the persistence
            return persistence.findTeams(filter);
        } catch (TeamPersistenceException e) {
            log(Level.ERROR, e, "error while finding team");
            throw e;
        } finally {
            logInvokeExit("findTeams", "persistence.findTeams(Filter)");
            logExit("findTeams(Filter filter)");
        }
    }

    /**
     * <p>
     * Gets the team, and all its positions, that the position with the given ID belongs to. Returns null if it does
     * not exist.
     * </p>
     * @return Team that contains the position with the given ID
     * @param positionId
     *            The ID of the position whose team is to be retrieved
     * @throws IllegalArgumentException
     *             If positionId is negative
     * @throws TeamPersistenceException
     *             If any system error, such as an inability to access the persistence properly.
     */
    public Team getTeamFromPosition(long positionId) throws TeamPersistenceException {
        logEntry("getTeamFromPosition");
        try {
            return myGetTeamFromPosition("getTeamFromPosition", positionId);
        } finally {
            logExit("getTeamFromPosition");
        }
    }

    /**
     * <p>
     * Validate position. Any failure will result in InvalidPositionException
     * <ul type="disc">
     * <li>name must not be null/empty, and must be 5-31 characters</li>
     * <li>description may be null/empty, and must have no more than 255 characters</li>
     * <li>team must not be finalized</li>
     * <li>memberResourceId must be non-negative when position is filled.</li>
     * <li>paymentPercentage must be larger than zero</li>
     * <li>position's memberResourceId must not be the same as the captain's.</li>
     * </ul>
     * </p>
     * @param position
     *            the position to be validated
     * @param team
     *            the team to validate the position
     * @throws InvalidPositionException
     *             If the position contains invalid data
     */
    private void validatePosition(TeamPosition position, Team team) throws InvalidPositionException {
        logEntry("validatePosition");
        try {
            // Validate the position as mentioned in the method doc
            if (position.getName() == null || position.getName().trim().length() == 0) {
                throwInvalidPositionException("validatePosition", "position name must not be null or empty");
            }
            if (position.getName().length() < NAME_MINLEN || position.getName().length() > NAME_MAXLEN) {
                throwInvalidPositionException("validatePosition", "position name must contain " + NAME_MINLEN + "-"
                    + NAME_MAXLEN + " chars");
            }
            if (position.getDescription() != null && position.getDescription().length() > DESCRIPTION_MAXLEN) {
                throwInvalidPositionException("validatePosition", "position description must be no longer than "
                    + DESCRIPTION_MAXLEN + " chars");
            }
            if (position.getFilled() && position.getMemberResourceId() < 0) {
                throwInvalidPositionException("validatePosition",
                    "memberResourceId must be non-negative when position is filled.");
            }
            if (position.getPaymentPercentage() <= 0) {
                throwInvalidPositionException("validatePosition", "paymentPercentage must be larger than zero");
            }
            // Team must not be finalized
            if (team.getTeamHeader().getFinalized()) {
                throwInvalidPositionException("updatePosition", "team must not be finalized");
            }
            // The position's resource id must be unique
            if (position.getMemberResourceId() == team.getTeamHeader().getCaptainResourceId()) {
                throwInvalidPositionException("updatePosition",
                    "position's member resource id must be unique in the team");
            }
        } finally {
            logExit("validatePosition");
        }
    }

    /**
     * <p>
     * Adds a position to the existing team with the given teamID. A new position id will be appended to this
     * position. Here are the restrictions on position:
     * <ul type="disc">
     * <li>name must not be null/empty, and must be 5-31 characters</li>
     * <li>description may be null/empty, and must have no more than 255 characters</li>
     * <li>teamId must be known, and team must not be finalized</li>
     * <li>memberResourceId must be non-negative when position is filled.</li>
     * <li>paymentPercentage must be larger than zero</li>
     * <li>memberResourceId/name must be unique in this team, including the captain</li>
     * <li>Add all position payment percentages in position plus new position plus the captainPercentagePayment. If
     * 100 or less, it is fine.</li>
     * </ul>
     * </p>
     * @param teamId
     *            The ID of the team to which the position is to be added
     * @param position
     *            TeamPosition to add to the team with the given teamId
     * @param userId
     *            The user Id for logging and auditing purposes
     * @throws IllegalArgumentException
     *             If position is null, or teamId or userId is negative
     * @throws InvalidPositionException
     *             If the position contains invalid data
     * @throws UnknownEntityException
     *             If position's teamId is unknown
     * @throws TeamPersistenceException
     *             If any system error, such as an inability to access the persistence properly.
     */
    public void addPosition(long teamId, TeamPosition position, long userId) throws InvalidPositionException,
        TeamPersistenceException, UnknownEntityException {
        logEntry("addPosition");
        try {
            // Check IAEs
            assertNotNegative(teamId, "teamId", "addPosition");
            assertNotNegative(userId, "userId", "addPosition");
            if (position == null) {
                throwIllegalArgumentException("addPosition", "position must not be null");
            }

            // Check if the teamId is present
            Team team = myGetTeam("addPosition", teamId);
            if (team == null) {
                throwUnknownEntityException("addPosition", "teamId " + teamId + " not existed");
            }

            validatePosition(position, team);

            TeamPosition[] positions = team.getPositions();
            int sum = 0;
            for (int i = 0; i < positions.length; i++) {
                // position's resource id must be unique
                if (position.getMemberResourceId() != -1
                    && position.getMemberResourceId() == positions[i].getMemberResourceId()) {
                    // Only non-negative resource ids will be compared.
                    throwInvalidPositionException("addPosition",
                        "position's member resource id must be unique in the team");
                }
                // position's name must be unique
                if (position.getName().equals(positions[i].getName())) {
                    throwInvalidPositionException("addPosition", "position's name must be unique in the team");
                }
                sum += positions[i].getPaymentPercentage();
            }
            // Total percentage must not exceed 100
            if (position.getPaymentPercentage() + team.getTeamHeader().getCaptainPaymentPercentage() + sum > Helper.HUNDRED) {
                throwInvalidPositionException("addPosition",
                    "the sum of captain and positions' payment percentage must not be greater than 100");
            }
            // Generate a position id for it
            position.setPositionId(generateId("addPosition"));

            logInvokeEntry("addPosition", "persistence.addPosition");
            try {
                // Delegate it to the persistence
                persistence.addPosition(teamId, position, userId);
            } catch (TeamPersistenceException e) {
                log(Level.ERROR, e, "error while adding position");
                throw e;
            } finally {
                logInvokeExit("addPosition", "persistence.addPosition");
            }
        } finally {
            logExit("addPosition");
        }
    }

    /**
     * <p>
     * Updates a position. The position id must be present before this update. Here are the restrictions on position:
     * <ul type="disc">
     * <li>name must not be null/empty, and must be 5-31 characters</li>
     * <li>description may be null/empty, and must have no more than 255 characters</li>
     * <li>position's id must be known, team must not be finalized</li>
     * <li>memberResourceId must be non-negative when position is filled.</li>
     * <li>paymentPercentage must be larger than zero</li>
     * <li>position's name and memberResourceId must not be the same as the captain's.</li>
     * <li>Add all position percentages in position( minus position being updated) plus updated position plus the
     * captainPercentagePayment. If 100 or less, it is fine.</li>
     * </ul>
     * </p>
     * @param position
     *            TeamPosition to update
     * @param userId
     *            The user Id for logging and auditing purposes
     * @throws IllegalArgumentException
     *             If position is null, userId is negative, or if position's positionId is negative
     * @throws InvalidPositionException
     *             If the position contains invalid data
     * @throws UnknownEntityException
     *             If the position's id is not present in the database
     * @throws TeamPersistenceException
     *             If any system error, such as an inability to access the persistence properly.
     */
    public void updatePosition(TeamPosition position, long userId) throws InvalidPositionException,
        UnknownEntityException, TeamPersistenceException {
        logEntry("updatePosition");
        try {
            // check IAEs
            assertNotNegative(userId, "userId", "updatePosition");
            if (position == null) {
                throwIllegalArgumentException("updatePosition", "position must not be null");
            }

            // Check if the position id is valid
            Team team = myGetTeamFromPosition("updatePosition", position.getPositionId());
            if (team == null) {
                throwUnknownEntityException("updatePosition", "team with positionId " + position.getPositionId()
                    + " not existed");
            }
            validatePosition(position, team);

            TeamPosition[] positions = team.getPositions();
            int sum = 0;
            for (int i = 0; i < positions.length; i++) {
                // Check other positions
                if (position.getPositionId() != positions[i].getPositionId()) {
                    // The position's resource id must be unique
                    if (position.getMemberResourceId() != -1
                        && position.getMemberResourceId() == positions[i].getMemberResourceId()) {
                        // Only non-negative resource ids will be compared.
                        throwInvalidPositionException("addPosition",
                            "position's member resource id must be unique in the team");
                    }
                    // The position's name must be unique
                    if (position.getName().equals(positions[i].getName())) {
                        throwInvalidPositionException("updatePosition", "position's name must be unique in the team");
                    }
                    sum += positions[i].getPaymentPercentage();
                }
            }
            // The total percentage must not exceed 100
            if (position.getPaymentPercentage() + team.getTeamHeader().getCaptainPaymentPercentage() + sum > Helper.HUNDRED) {
                throwInvalidPositionException("updatePosition",
                    "the sum of captain and positions' payment percentage must not be greater than 100");
            }

            logInvokeEntry("updatePosition", "persistence.updatePosition");
            try {
                // Delegate it to the persistence
                persistence.updatePosition(position, userId);
            } catch (TeamPersistenceException e) {
                log(Level.ERROR, e, "error while updating position");
                throw e;
            } finally {
                logInvokeExit("updatePosition", "persistence.updatePosition");
            }
        } finally {
            logExit("updatePosition");
        }
    }

    /**
     * <p>
     * Removes a position. The position must be present, not published and not filled before the remove. And the team
     * must not be finalized. Here are the restrictions for the remove:
     * <ul type="disc">
     * <li>position must not be published, and not be filled.</li>
     * <li>team must not be finalized.</li>
     * </ul>
     * </p>
     * @param positionId
     *            The ID of the position to remove
     * @param userId
     *            The user Id for logging and auditing purposes
     * @throws IllegalArgumentException
     *             If positionId or userId is negative
     * @throws PositionRemovalException
     *             if the position is already published or filled, or the team is finalized.
     * @throws UnknownEntityException
     *             If positionID is unknown
     * @throws TeamPersistenceException
     *             If any system error, such as an inability to access the persistence properly.
     */
    public void removePosition(long positionId, long userId) throws PositionRemovalException, UnknownEntityException,
        TeamPersistenceException {
        logEntry("removePosition");
        try {
            // Check IAEs
            assertNotNegative(userId, "userId", "removePosition");
            TeamPosition position = myGetPosition("removePosition", positionId);
            if (position == null) {
                throwUnknownEntityException("removePosition", "positionId is unknown");
            }

            // Verify the team before remove
            Team team = myGetTeamFromPosition("removePosition", positionId);
            // Position must not be published, and not be filled.
            if (position.getPublished() || position.getFilled()) {
                throwPositionRemovalException("removePosition", "position must have published=false and filled=false");
            }
            // Team must not be finalized.
            if (team.getTeamHeader().getFinalized()) {
                throwPositionRemovalException("removePosition", "team must not be finalized");
            }

            logInvokeEntry("removePosition", "persistence.removePosition");
            try {
                // Delegate it to the persistence
                persistence.removePosition(positionId, userId);
            } catch (TeamPersistenceException e) {
                log(Level.ERROR, e, "error while updating position");
                throw e;
            } finally {
                logInvokeExit("removePosition", "persistence.removePosition");
            }
        } finally {
            logExit("removePosition");
        }
    }

    /**
     * <p>
     * Retrieves the position with the given ID. Returns null if it does not exist.
     * </p>
     * @return TeamPosition with the given ID, or null if none found
     * @param positionId
     *            The ID of the position to get
     * @throws IllegalArgumentException
     *             If positionId is negative
     * @throws TeamPersistenceException
     *             If any system error, such as an inability to access the persistence properly.
     */
    public TeamPosition getPosition(long positionId) throws TeamPersistenceException {
        logEntry("getPosition");
        try {
            return myGetPosition("getPosition", positionId);
        } finally {
            logExit("getPosition");
        }
    }

    /**
     * <p>
     * Finds all positions that match the criteria in the passed filter. Returns empty array if none found.
     * </p>
     * @return An array of matching TeamPosition, or empty if no matches found
     * @param filter
     *            The filter criteria to match positions
     * @throws IllegalArgumentException
     *             If filter is null
     * @throws TeamPersistenceException
     *             If any system error, such as an inability to access the persistence properly.
     */
    public TeamPosition[] findPositions(Filter filter) throws TeamPersistenceException {
        logEntry("findPositions");
        logInvokeEntry("findPositions", "persistence.findPositions(Filter)");
        try {
            // Delegate it to the persistence
            return persistence.findPositions(filter);
        } catch (TeamPersistenceException e) {
            log(Level.ERROR, e, "error while finding position");
            throw e;
        } finally {
            logInvokeExit("findPositions", "persistence.findPositions(Filter)");
            logExit("findPositions");
        }
    }
}
