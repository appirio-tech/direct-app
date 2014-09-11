/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.team.failuretests;

import java.util.Arrays;
import java.util.Iterator;

import com.topcoder.management.team.InvalidPositionException;
import com.topcoder.management.team.InvalidTeamException;
import com.topcoder.management.team.PositionFilterFactory;
import com.topcoder.management.team.PositionRemovalException;
import com.topcoder.management.team.TeamConfigurationException;
import com.topcoder.management.team.TeamFilterFactory;
import com.topcoder.management.team.TeamHeader;
import com.topcoder.management.team.TeamManager;
import com.topcoder.management.team.TeamPersistenceException;
import com.topcoder.management.team.TeamPosition;
import com.topcoder.management.team.UnknownEntityException;
import com.topcoder.management.team.impl.TeamManagerImpl;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.idgenerator.IDGenerator;
import com.topcoder.util.idgenerator.IDGeneratorFactory;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Failure test for TeamManagerImpl class.
 *
 * @author King_Bette
 * @version 1.0
 */
public class TeamManagerImplFailureTest extends TestCase {
    /**
     * Represents a TeamManagerImpl instance that is used in the test.
     */
    private TeamManager teamManager;

    /**
     * Represents a TeamHeader instance that is used in the test.
     * <p>
     * <strong>Business rules for creating team</strong>
     * </p>
     * <ul>
     * <li>team name is not null/empty with length 5-31 characters</li>
     * <li>team description is either null/empty or no longer than 255
     * characters</li>
     * <li>projectId must be filled (non-negative)</li>
     * <li>captainResourceId must be filled (non-negative)</li>
     * <li>captainPaymentPercentage must be positive number that is not greater
     * than 100</li>
     * <li>finalized flag must be false</li>
     * </ul>
     */
    private TeamHeader teamHeader;

    /**
     * Represents a TeamPosition instance that is used in the test.
     */
    private TeamPosition position;

    /**
     * Represents the persistence used in the TeamManager.
     */
    private MockTeamPersistence teamPersistence = new MockTeamPersistence();
    /**
     * Represents the IDGenerator instance used in the test.
     */
    private IDGenerator idGenerator;
    /**
     * Aggregates all tests in this class.
     *
     * @return Test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(TeamManagerImplFailureTest.class);
    }

    /**
     * Sets up the environment for the TestCase.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    protected void setUp() throws Exception {
        ConfigManager.getInstance().add("failuretests/config.xml");
        teamHeader = new TeamHeader();
        teamHeader.setName("team1");
        teamHeader.setProjectId(0);
        teamHeader.setCaptainResourceId(0);
        teamHeader.setCaptainPaymentPercentage(50);
        teamHeader.setFinalized(false);

        position = new TeamPosition();
        position.setName("position1");
        position.setFilled(true);
        position.setMemberResourceId(1);
        position.setPaymentPercentage(10);
        idGenerator = IDGeneratorFactory.getIDGenerator("failuretests");
        teamManager = new TeamManagerImpl(teamPersistence, idGenerator, null);
    }

    /**
     * Tears down the environment for the TestCase.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    protected void tearDown() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        for (Iterator iter = cm.getAllNamespaces(); iter.hasNext();) {
            cm.removeNamespace((String) iter.next());
        }
    }

    /**
     * Failure test of <code>TeamManagerImpl(String namespace)</code>
     * constructor.
     * <p>
     * namespace is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testTeamManagerImpl_failure_null_namespace() throws Exception {
        try {
            new TeamManagerImpl(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>TeamManagerImpl(String namespace)</code>
     * constructor.
     * <p>
     * namespace is empty.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testTeamManagerImpl_failure_empty_namespace() throws Exception {
        try {
            new TeamManagerImpl("   ");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>TeamManagerImpl(String namespace)</code>
     * constructor.
     * <p>
     * specNamespace property is not present.
     * </p>
     * <p>
     * Expect TeamConfigurationException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testTeamManagerImpl_failure_non_persent_specNamespace_property() throws Exception {
        try {
            new TeamManagerImpl("invalid.specNamespacePropertyNotPresent");
            fail("Expect TeamConfigurationException.");
        } catch (TeamConfigurationException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>TeamManagerImpl(String namespace)</code>
     * constructor.
     * <p>
     * specNamespace property is incorrect.
     * </p>
     * <p>
     * Expect TeamConfigurationException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testTeamManagerImpl_failure_incorrect_specNamespace_property() throws Exception {
        try {
            new TeamManagerImpl("invalid.specNamespacePropertyIncorrect");
            fail("Expect TeamConfigurationException.");
        } catch (TeamConfigurationException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>TeamManagerImpl(String namespace)</code>
     * constructor.
     * <p>
     * persistenceKey property is not present.
     * </p>
     * <p>
     * Expect TeamConfigurationException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testTeamManagerImpl_failure_non_present_persistenceKey_property() throws Exception {
        try {
            new TeamManagerImpl("invalid.persistenceKeyPropertyNotPresent");
            fail("Expect TeamConfigurationException.");
        } catch (TeamConfigurationException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>TeamManagerImpl(String namespace)</code>
     * constructor.
     * <p>
     * persistenceKey is incorrect.
     * </p>
     * <p>
     * Expect TeamConfigurationException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testTeamManagerImpl_failure_incorrect_persistenceKey_property() throws Exception {
        try {
            new TeamManagerImpl("invalid.persistenceKeyPropertyIncorrect");
            fail("Expect TeamConfigurationException.");
        } catch (TeamConfigurationException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>TeamManagerImpl(String namespace)</code>
     * constructor.
     * <p>
     * persistenceKey is a String type.
     * </p>
     * <p>
     * Expect TeamConfigurationException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testTeamManagerImpl_failure_string_type_persistenceKey_property() throws Exception {
        try {
            new TeamManagerImpl("invalid.persistenceKeyPropertyStringType");
            fail("Expect TeamConfigurationException.");
        } catch (TeamConfigurationException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>TeamManagerImpl(String namespace)</code>
     * constructor.
     * <p>
     * idGenerator Property is Not Present
     * </p>
     * <p>
     * Expect TeamConfigurationException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testTeamManagerImpl_failure_not_present_idGenerator_property() throws Exception {
        try {
            new TeamManagerImpl("invalid.idGeneratorPropertyNotPresent");
            fail("Expect TeamConfigurationException.");
        } catch (TeamConfigurationException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>TeamManagerImpl(String namespace)</code>
     * constructor.
     * <p>
     * idGenerator Property is incorrect.
     * </p>
     * <p>
     * Expect TeamConfigurationException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testTeamManagerImpl_failure_incorrect_idGenerator_property() throws Exception {
        try {
            new TeamManagerImpl("invalid.idGeneratorPropertyIncorrect");
            fail("Expect TeamConfigurationException.");
        } catch (TeamConfigurationException e) {
            // expect
        }
    }

    /**
     * Failure test of
     * <code>TeamManagerImpl(TeamPersistence persistence, IDGenerator idGenerator, Log logger)</code>
     * constructor.
     * <p>
     * persistence is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testTeamManagerImpl3_failure_null_persistence() throws Exception {
        try {
            new TeamManagerImpl(null, idGenerator, null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }
    /**
     * Failure test of
     * <code>TeamManagerImpl(TeamPersistence persistence, IDGenerator idGenerator, Log logger)</code>
     * constructor.
     * <p>
     * idGenerator is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testTeamManagerImpl3_failure_null_idGenerator() throws Exception {
        try {
            new TeamManagerImpl(teamPersistence, null, null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }
    /**
     * Failure test of <code>createTeam(TeamHeader team, long userId)</code>
     * method.
     * <p>
     * teamHeader is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testCreateTeam_failure_null_teamHeader() throws Exception {
        try {
            teamManager.createTeam(null, 0);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>createTeam(TeamHeader team, long userId)</code>
     * method.
     * <p>
     * userId is negatvie.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testCreateTeam_failure_negative_userId() throws Exception {
        try {
            teamManager.createTeam(teamHeader, -1);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>createTeam(TeamHeader team, long userId)</code>
     * method.
     * <p>
     * team name is null.
     * </p>
     * <p>
     * Expect InvalidTeamException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testCreateTeam_failure_null_team_name() throws Exception {
        teamHeader.setName(null);
        try {
            teamManager.createTeam(teamHeader, 0);
            fail("Expect InvalidTeamException.");
        } catch (InvalidTeamException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>createTeam(TeamHeader team, long userId)</code>
     * method.
     * <p>
     * team name is empty.
     * </p>
     * <p>
     * Expect InvalidTeamException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testCreateTeam_failure_empty_team_name() throws Exception {
        teamHeader.setName("      ");
        try {
            teamManager.createTeam(teamHeader, 0);
            fail("Expect InvalidTeamException.");
        } catch (InvalidTeamException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>createTeam(TeamHeader team, long userId)</code>
     * method.
     * <p>
     * teamHeader name shorter than 5.
     * </p>
     * <p>
     * Expect InvalidTeamException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testCreateTeam_failure_teamHeader_name_shorter_than_5() throws Exception {
        teamHeader.setName("abcd");
        try {
            teamManager.createTeam(teamHeader, 0);
            fail("Expect InvalidTeamException.");
        } catch (InvalidTeamException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>createTeam(TeamHeader team, long userId)</code>
     * method.
     * <p>
     * teamHeader name longer than 31.
     * </p>
     * <p>
     * Expect InvalidTeamException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testCreateTeam_failure_teamHeader_name_longer_than_31() throws Exception {
        char[] name = new char[32];
        Arrays.fill(name, 'a');
        teamHeader.setName(new String(name));
        try {
            teamManager.createTeam(teamHeader, 0);
            fail("Expect InvalidTeamException.");
        } catch (InvalidTeamException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>createTeam(TeamHeader team, long userId)</code>
     * method.
     * <p>
     * teamHeader description longer than 255.
     * </p>
     * <p>
     * Expect InvalidTeamException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testCreateTeam_failure_teamHeader_description_longer_than_255() throws Exception {
        char[] description = new char[256];
        Arrays.fill(description, 'a');
        teamHeader.setDescription(new String(description));
        try {
            teamManager.createTeam(teamHeader, 0);
            fail("Expect InvalidTeamException.");
        } catch (InvalidTeamException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>createTeam(TeamHeader team, long userId)</code>
     * method.
     * <p>
     * projectId is not set.
     * </p>
     * <p>
     * Expect InvalidTeamException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testCreateTeam_failure_non_set_projectId() throws Exception {
        teamHeader = new TeamHeader();
        teamHeader.setName("team1");
        teamHeader.setCaptainResourceId(0);
        teamHeader.setCaptainPaymentPercentage(50);
        teamHeader.setFinalized(false);
        try {
            teamManager.createTeam(teamHeader, 0);
            fail("Expect InvalidTeamException.");
        } catch (InvalidTeamException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>createTeam(TeamHeader team, long userId)</code>
     * method.
     * <p>
     * captainResourceId is not set.
     * </p>
     * <p>
     * Expect InvalidTeamException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testCreateTeam_failure_non_set_captainResourceId() throws Exception {
        teamHeader = new TeamHeader();
        teamHeader.setName("team1");
        teamHeader.setProjectId(0);
        teamHeader.setCaptainPaymentPercentage(50);
        teamHeader.setFinalized(false);
        try {
            teamManager.createTeam(teamHeader, 0);
            fail("Expect InvalidTeamException.");
        } catch (InvalidTeamException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>createTeam(TeamHeader team, long userId)</code>
     * method.
     * <p>
     * captainPaymentPercentage is zero.
     * </p>
     * <p>
     * Expect InvalidTeamException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testCreateTeam_failure_zero_captainPaymentPercentage() throws Exception {
        teamHeader.setCaptainPaymentPercentage(0);
        try {
            teamManager.createTeam(teamHeader, 0);
            fail("Expect InvalidTeamException.");
        } catch (InvalidTeamException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>createTeam(TeamHeader team, long userId)</code>
     * method.
     * <p>
     * finalized flag is true.
     * </p>
     * <p>
     * Expect InvalidTeamException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testCreateTeam_failure_true_finalized_flag() throws Exception {
        teamHeader.setFinalized(true);
        try {
            teamManager.createTeam(teamHeader, 0);
            fail("Expect InvalidTeamException.");
        } catch (InvalidTeamException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>createTeam(TeamHeader team, long userId)</code>
     * method.
     * <p>
     * persistence error occurs.
     * </p>
     * <p>
     * Expect TeamPersistenceException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testCreateTeam_failure_persistence_error_occurs() throws Exception {
        teamPersistence.setThrowException(true);
        try {
            teamManager.createTeam(teamHeader, 0);
            fail("Expect TeamPersistenceException.");
        } catch (TeamPersistenceException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>updateTeam(TeamHeader team, long userId)</code>
     * method.
     * <p>
     * teamHeader is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testUpdateTeam_failure_null_teamHeader() throws Exception {
        try {
            teamManager.updateTeam(null, 0);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>updateTeam(TeamHeader team, long userId)</code>
     * method.
     * <p>
     * userId is negatvie.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testUpdateTeam_failure_negative_userId() throws Exception {
        try {
            teamManager.updateTeam(teamHeader, -1);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>updateTeam(TeamHeader team, long userId)</code>
     * method.
     * <p>
     * team id is not set.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testUpdateTeam_failure_non_set_team_id() throws Exception {
        try {
            teamManager.updateTeam(teamHeader, 0);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>updateTeam(TeamHeader team, long userId)</code>
     * method.
     * <p>
     * team id is not present.
     * </p>
     * <p>
     * Expect UnknownEntityException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testUpdateTeam_failure_non_present_team_id() throws Exception {
        teamHeader.setTeamId(2);
        try {
            teamManager.updateTeam(teamHeader, 0);
            fail("Expect UnknownEntityException.");
        } catch (UnknownEntityException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>updateTeam(TeamHeader team, long userId)</code>
     * method.
     * <p>
     * team name is null.
     * </p>
     * <p>
     * Expect InvalidTeamException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testUpdateTeam_failure_null_team_name() throws Exception {
        teamManager.createTeam(teamHeader, 0);
        teamHeader.setName(null);
        try {
            teamManager.updateTeam(teamHeader, 0);
            fail("Expect InvalidTeamException.");
        } catch (InvalidTeamException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>updateTeam(TeamHeader team, long userId)</code>
     * method.
     * <p>
     * team name is empty.
     * </p>
     * <p>
     * Expect InvalidTeamException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testUpdateTeam_failure_empty_team_name() throws Exception {
        teamManager.createTeam(teamHeader, 0);
        teamHeader.setName("      ");
        try {
            teamManager.updateTeam(teamHeader, 0);
            fail("Expect InvalidTeamException.");
        } catch (InvalidTeamException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>updateTeam(TeamHeader team, long userId)</code>
     * method.
     * <p>
     * teamHeader name shorter than 5.
     * </p>
     * <p>
     * Expect InvalidTeamException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testUpdateTeam_failure_teamHeader_name_shorter_than_5() throws Exception {
        teamManager.createTeam(teamHeader, 0);
        teamHeader.setName("abcd");
        try {
            teamManager.updateTeam(teamHeader, 0);
            fail("Expect InvalidTeamException.");
        } catch (InvalidTeamException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>updateTeam(TeamHeader team, long userId)</code>
     * method.
     * <p>
     * teamHeader name longer than 31.
     * </p>
     * <p>
     * Expect InvalidTeamException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testUpdateTeam_failure_teamHeader_name_longer_than_31() throws Exception {
        teamManager.createTeam(teamHeader, 0);
        char[] name = new char[32];
        Arrays.fill(name, 'a');
        teamHeader.setName(new String(name));
        try {
            teamManager.updateTeam(teamHeader, 0);
            fail("Expect InvalidTeamException.");
        } catch (InvalidTeamException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>updateTeam(TeamHeader team, long userId)</code>
     * method.
     * <p>
     * teamHeader description longer than 255.
     * </p>
     * <p>
     * Expect InvalidTeamException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testUpdateTeam_failure_teamHeader_description_longer_than_255() throws Exception {
        teamManager.createTeam(teamHeader, 0);
        char[] description = new char[256];
        Arrays.fill(description, 'a');
        teamHeader.setDescription(new String(description));
        try {
            teamManager.updateTeam(teamHeader, 0);
            fail("Expect InvalidTeamException.");
        } catch (InvalidTeamException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>updateTeam(TeamHeader team, long userId)</code>
     * method.
     * <p>
     * projectId is not set.
     * </p>
     * <p>
     * Expect InvalidTeamException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testUpdateTeam_failure_non_set_projectId() throws Exception {
        teamManager.createTeam(teamHeader, 0);
        long teamId = teamHeader.getTeamId();
        teamHeader = new TeamHeader();
        teamHeader.setName("team1");
        teamHeader.setCaptainResourceId(0);
        teamHeader.setCaptainPaymentPercentage(50);
        teamHeader.setFinalized(false);
        teamHeader.setTeamId(teamId);
        try {
            teamManager.updateTeam(teamHeader, 0);
            fail("Expect InvalidTeamException.");
        } catch (InvalidTeamException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>updateTeam(TeamHeader team, long userId)</code>
     * method.
     * <p>
     * captainResourceId is not set.
     * </p>
     * <p>
     * Expect InvalidTeamException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testUpdateTeam_failure_non_set_captainResourceId() throws Exception {
        teamManager.createTeam(teamHeader, 0);
        long teamId = teamHeader.getTeamId();
        teamHeader = new TeamHeader();
        teamHeader.setTeamId(teamId);
        teamHeader.setName("team1");
        teamHeader.setProjectId(0);
        teamHeader.setCaptainPaymentPercentage(50);
        teamHeader.setFinalized(false);
        try {
            teamManager.updateTeam(teamHeader, 0);
            fail("Expect InvalidTeamException.");
        } catch (InvalidTeamException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>updateTeam(TeamHeader team, long userId)</code>
     * method.
     * <p>
     * captainPaymentPercentage is zero.
     * </p>
     * <p>
     * Expect InvalidTeamException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testUpdateTeam_failure_zero_captainPaymentPercentage() throws Exception {
        teamManager.createTeam(teamHeader, 0);
        teamHeader.setCaptainPaymentPercentage(0);
        try {
            teamManager.updateTeam(teamHeader, 0);
            fail("Expect InvalidTeamException.");
        } catch (InvalidTeamException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>updateTeam(TeamHeader team, long userId)</code>
     * method.
     * <p>
     * team captainResourceId is the same as one position memberresourceId.
     * </p>
     * <p>
     * Expect InvalidTeamException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testUpdateTeam_failure_team_captainResourceId_equalTo_memberresourceId() throws Exception {
        teamManager.createTeam(teamHeader, 0);
        teamManager.addPosition(teamHeader.getTeamId(), position, 0);
        teamHeader.setCaptainResourceId(1);
        try {
            teamManager.updateTeam(teamHeader, 0);
            fail("Expect InvalidTeamException.");
        } catch (InvalidTeamException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>updateTeam(TeamHeader team, long userId)</code>
     * method.
     * <p>
     * sum of payment percentage is greater than 100.
     * </p>
     * <p>
     * Expect InvalidTeamException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testUpdateTeam_failure_sum_of_paymentPercentage_greater_than_100() throws Exception {
        teamManager.createTeam(teamHeader, 0);
        teamManager.addPosition(teamHeader.getTeamId(), position, 0);
        teamHeader.setCaptainPaymentPercentage(91);
        try {
            teamManager.updateTeam(teamHeader, 0);
            fail("Expect InvalidTeamException.");
        } catch (InvalidTeamException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>updateTeam(TeamHeader team, long userId)</code>
     * method.
     * <p>
     * persistence error occurs.
     * </p>
     * <p>
     * Expect TeamPersistenceException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testUpdateTeam_failure_persistence_error_occurs() throws Exception {
        teamManager.createTeam(teamHeader, 0);
        teamPersistence.setThrowException(true);
        try {
            teamManager.updateTeam(teamHeader, 0);
            fail("Expect TeamPersistenceException.");
        } catch (TeamPersistenceException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>removeTeam(long teamId, long userId)</code>
     * method.
     * <p>
     * teamId is negative.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testRemoveTeam_failure_negative_teamId() throws Exception {
        try {
            teamManager.removeTeam(-1, 0);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>removeTeam(long teamId, long userId)</code>
     * method.
     * <p>
     * userId is negative.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testRemoveTeam_failure_negative_userId() throws Exception {
        teamManager.createTeam(teamHeader, 0);
        try {
            teamManager.removeTeam(teamHeader.getTeamId(), -1);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>removeTeam(long teamId, long userId)</code>
     * method.
     * <p>
     * teamId is not present.
     * </p>
     * <p>
     * Expect UnknownEntityException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testRemoveTeam_failure_non_present_team_id() throws Exception {
        try {
            teamManager.removeTeam(0, 0);
            fail("Expect UnknownEntityException.");
        } catch (UnknownEntityException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>removeTeam(long teamId, long userId)</code>
     * method.
     * <p>
     * persisence error occurs.
     * </p>
     * <p>
     * Expect TeamPersistenceException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testRemoveTeam_failure_persistence_error_occurs() throws Exception {
        teamManager.createTeam(teamHeader, 0);
        teamPersistence.setThrowException(true);
        try {
            teamManager.removeTeam(teamHeader.getTeamId(), 0);
            fail("Expect TeamPersistenceException.");
        } catch (TeamPersistenceException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>getTeam(long teamId)</code> method.
     * <p>
     * teamId is negative.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testGetTeam_failure_negative_teamId() throws Exception {
        try {
            teamManager.getTeam(-1);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>getTeam(long teamId)</code> method.
     * <p>
     * persistence error occurs.
     * </p>
     * <p>
     * Expect TeamPersistenceException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testGetTeam_failure_persistence_error_occurs() throws Exception {
        teamPersistence.setThrowException(true);
        try {
            teamManager.getTeam(0);
            fail("Expect TeamPersistenceException.");
        } catch (TeamPersistenceException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>findTeams(long projectId)</code> method.
     * <p>
     * projectId is negative.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testFindTeams1_failure_negative_projectId() throws Exception {
        try {
            teamManager.findTeams(-1);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>findTeams(long projectId)</code> method.
     * <p>
     * persistence error occurs.
     * </p>
     * <p>
     * Expect TeamPersistenceException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testFindTeams1_failure_persistence_error_occurs() throws Exception {
        teamPersistence.setThrowException(true);
        try {
            teamManager.findTeams(0);
            fail("Expect TeamPersistenceException.");
        } catch (TeamPersistenceException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>findTeams(long[] projectIds)</code> method.
     * <p>
     * projectIds is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testFindTeams2_failure_null_projectIds() throws Exception {
        try {
            teamManager.findTeams((long[]) null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>findTeams(long[] projectIds)</code> method.
     * <p>
     * projectIds contains null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testFindTeams2_failure_projectIds_contains_null() throws Exception {
        try {
            teamManager.findTeams(new long[]{1, -1 });
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>findTeams(long[] projectIds)</code> method.
     * <p>
     * persistence error occurs.
     * </p>
     * <p>
     * Expect TeamPersistenceException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testFindTeams2_failure_persistence_error_occurs() throws Exception {
        teamPersistence.setThrowException(true);
        try {
            teamManager.findTeams(new long[]{1 });
            fail("Expect TeamPersistenceException.");
        } catch (TeamPersistenceException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>findTeams(Filter filter)</code> method.
     * <p>
     * filter is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testFindTeams3_failure_null_filter() throws Exception {
        try {
            teamManager.findTeams((Filter) null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>findTeams(Filter filter)</code> method.
     * <p>
     * persistence error occrs.
     * </p>
     * <p>
     * Expect TeamPersistenceException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testFindTeams3_failure_persistence_error_occurs() throws Exception {
        teamPersistence.setThrowException(true);
        try {
            teamManager.findTeams(TeamFilterFactory.createProjectIdFilter(1));
            fail("Expect TeamPersistenceException.");
        } catch (TeamPersistenceException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>getTeamFromPosition(long positionId)</code>
     * method.
     * <p>
     * positionId is negative.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testGetTeamFromPosition_failure_negative_positionId() throws Exception {
        try {
            teamManager.getTeamFromPosition(-1);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>getTeamFromPosition(long positionId)</code>
     * method.
     * <p>
     * persistence error occurs.
     * </p>
     * <p>
     * Expect TeamPersistenceException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testGetTeamFromPosition_failure_persistence_error_occurs() throws Exception {
        teamPersistence.setThrowException(true);
        try {
            teamManager.getTeamFromPosition(0);
            fail("Expect TeamPersistenceException.");
        } catch (TeamPersistenceException e) {
            // expect
        }
    }

    /**
     * Failure test of
     * <code>addPosition(long teamId, TeamPosition position, long userId)</code>
     * method.
     * <p>
     * position is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testAddPosition_failure_null_position() throws Exception {
        teamManager.createTeam(teamHeader, 0);
        try {
            teamManager.addPosition(teamHeader.getTeamId(), null, 0);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of
     * <code>addPosition(long teamId, TeamPosition position, long userId)</code>
     * method.
     * <p>
     * teamId is negative.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testAddPosition_failure_negative_teamId() throws Exception {
        try {
            teamManager.addPosition(-1, position, 0);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of
     * <code>addPosition(long teamId, TeamPosition position, long userId)</code>
     * method.
     * <p>
     * userId is negative.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testAddPosition_failure_negative_userId() throws Exception {
        teamManager.createTeam(teamHeader, 0);
        try {
            teamManager.addPosition(teamHeader.getTeamId(), position, -1);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of
     * <code>addPosition(long teamId, TeamPosition position, long userId)</code>
     * method.
     * <p>
     * position name is null.
     * </p>
     * <p>
     * Expect InvalidPositionException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testAddPosition_failure_null_position_name() throws Exception {
        teamManager.createTeam(teamHeader, 0);
        position.setName(null);
        try {
            teamManager.addPosition(teamHeader.getTeamId(), position, 0);
            fail("Expect InvalidPositionException.");
        } catch (InvalidPositionException e) {
            // expect
        }
    }

    /**
     * Failure test of
     * <code>addPosition(long teamId, TeamPosition position, long userId)</code>
     * method.
     * <p>
     * position name is empty.
     * </p>
     * <p>
     * Expect InvalidPositionException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testAddPosition_failure_empty_position_name() throws Exception {
        teamManager.createTeam(teamHeader, 0);
        position.setName("      ");
        try {
            teamManager.addPosition(teamHeader.getTeamId(), position, 0);
            fail("Expect InvalidPositionException.");
        } catch (InvalidPositionException e) {
            // expect
        }
    }

    /**
     * Failure test of
     * <code>addPosition(long teamId, TeamPosition position, long userId)</code>
     * method.
     * <p>
     * position name is shorter than 5.
     * </p>
     * <p>
     * Expect InvalidPositionException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testAddPosition_failure_position_name_shorter_than_5() throws Exception {
        teamManager.createTeam(teamHeader, 0);
        position.setName("abcd");
        try {
            teamManager.addPosition(teamHeader.getTeamId(), position, 0);
            fail("Expect InvalidPositionException.");
        } catch (InvalidPositionException e) {
            // expect
        }
    }

    /**
     * Failure test of
     * <code>addPosition(long teamId, TeamPosition position, long userId)</code>
     * method.
     * <p>
     * position name is longer than 31
     * </p>
     * <p>
     * Expect InvalidPositionException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testAddPosition_failure_position_name_longer_than_31() throws Exception {
        teamManager.createTeam(teamHeader, 0);
        char[] name = new char[32];
        Arrays.fill(name, 'a');
        position.setName(new String(name));
        try {
            teamManager.addPosition(teamHeader.getTeamId(), position, 0);
            fail("Expect InvalidPositionException.");
        } catch (InvalidPositionException e) {
            // expect
        }
    }

    /**
     * Failure test of
     * <code>addPosition(long teamId, TeamPosition position, long userId)</code>
     * method.
     * <p>
     * position description is longer than 255.
     * </p>
     * <p>
     * Expect InvalidPositionException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testAddPosition_failure_position_description_longer_than_255() throws Exception {
        teamManager.createTeam(teamHeader, 0);
        char[] description = new char[256];
        Arrays.fill(description, 'a');
        position.setDescription(new String(description));
        try {
            teamManager.addPosition(teamHeader.getTeamId(), position, 0);
            fail("Expect InvalidPositionException.");
        } catch (InvalidPositionException e) {
            // expect
        }
    }

    /**
     * Failure test of
     * <code>addPosition(long teamId, TeamPosition position, long userId)</code>
     * method.
     * <p>
     * position payment percentage is zero.
     * </p>
     * <p>
     * Expect InvalidPositionException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testAddPosition_failure_zero_position_payment_percentage() throws Exception {
        teamManager.createTeam(teamHeader, 0);
        position.setPaymentPercentage(0);
        try {
            teamManager.addPosition(teamHeader.getTeamId(), position, 0);
            fail("Expect InvalidPositionException.");
        } catch (InvalidPositionException e) {
            // expect
        }
    }

    /**
     * Failure test of
     * <code>addPosition(long teamId, TeamPosition position, long userId)</code>
     * method.
     * <p>
     * member resource id is negative.
     * </p>
     * <p>
     * Expect InvalidPositionException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testAddPosition_failure_negative_member_resource_id_when_position_filled() throws Exception {
        teamManager.createTeam(teamHeader, 0);
        position.setMemberResourceId(-1);
        try {
            teamManager.addPosition(teamHeader.getTeamId(), position, 0);
            fail("Expect InvalidPositionException.");
        } catch (InvalidPositionException e) {
            // expect
        }
    }

    /**
     * Failure test of
     * <code>addPosition(long teamId, TeamPosition position, long userId)</code>
     * method.
     * <p>
     * team is finalized.
     * </p>
     * <p>
     * Expect InvalidPositionException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testAddPosition_failure_finalized_team() throws Exception {
        teamManager.createTeam(teamHeader, 0);
        teamHeader.setFinalized(true);
        teamManager.updateTeam(teamHeader, 0);
        try {
            teamManager.addPosition(teamHeader.getTeamId(), position, 0);
            fail("Expect InvalidPositionException.");
        } catch (InvalidPositionException e) {
            // expect
        }
    }

    /**
     * Failure test of
     * <code>addPosition(long teamId, TeamPosition position, long userId)</code>
     * method.
     * <p>
     * member resource id is not unique.
     * </p>
     * <p>
     * Expect InvalidPositionException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testAddPosition_failure_non_unique_member_resource_id() throws Exception {
        teamManager.createTeam(teamHeader, 0);
        position.setMemberResourceId(0);
        try {
            teamManager.addPosition(teamHeader.getTeamId(), position, 0);
            fail("Expect InvalidPositionException.");
        } catch (InvalidPositionException e) {
            // expect
        }
    }

    /**
     * Failure test of
     * <code>addPosition(long teamId, TeamPosition position, long userId)</code>
     * method.
     * <p>
     * position name is not unique.
     * </p>
     * <p>
     * Expect InvalidPositionException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testAddPosition_failure_non_unique_name() throws Exception {
        teamManager.createTeam(teamHeader, 0);
        teamManager.addPosition(teamHeader.getTeamId(), position, 0);
        position = new TeamPosition();
        position.setName("position1");
        position.setFilled(true);
        position.setMemberResourceId(2);
        position.setPaymentPercentage(10);
        teamManager = new TeamManagerImpl(teamPersistence, idGenerator, null);
        try {
            teamManager.addPosition(teamHeader.getTeamId(), position, 0);
            fail("Expect InvalidPositionException.");
        } catch (InvalidPositionException e) {
            // expect
        }
    }

    /**
     * Failure test of
     * <code>addPosition(long teamId, TeamPosition position, long userId)</code>
     * method.
     * <p>
     * sum of payment percentage is greater than 100.
     * </p>
     * <p>
     * Expect InvalidPositionException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testAddPosition_failure_sum_of_payment_percentage_greater_than_100() throws Exception {
        teamManager.createTeam(teamHeader, 0);
        position.setPaymentPercentage(51);
        try {
            teamManager.addPosition(teamHeader.getTeamId(), position, 0);
            fail("Expect InvalidPositionException.");
        } catch (InvalidPositionException e) {
            // expect
        }
    }

    /**
     * Failure test of
     * <code>addPosition(long teamId, TeamPosition position, long userId)</code>
     * method.
     * <p>
     * teamId is not present.
     * </p>
     * <p>
     * Expect UnknownEntityException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testAddPosition_failure_non_present_teamId() throws Exception {
        try {
            teamManager.addPosition(0, position, 0);
            fail("Expect UnknownEntityException.");
        } catch (UnknownEntityException e) {
            // expect
        }
    }

    /**
     * Failure test of
     * <code>addPosition(long teamId, TeamPosition position, long userId)</code>
     * method.
     * <p>
     * persistence error occurs.
     * </p>
     * <p>
     * Expect TeamPersistenceException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testAddPosition_failure_persistence_error_occurs() throws Exception {
        teamManager.createTeam(teamHeader, 0);
        teamPersistence.setThrowException(true);
        try {
            teamManager.addPosition(teamHeader.getTeamId(), position, 0);
            fail("Expect TeamPersistenceException.");
        } catch (TeamPersistenceException e) {
            // expect
        }
    }

    /**
     * Failure test of
     * <code>updatePosition(TeamPosition position, long userId)</code> method.
     * <p>
     * position is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testUpdatePosition_failure_null_position() throws Exception {
        try {
            teamManager.updatePosition(null, 0);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of
     * <code>updatePosition(TeamPosition position, long userId)</code> method.
     * <p>
     * positionId is negative.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testUpdatePosition_failure_negative_positionId() throws Exception {
        try {
            teamManager.updatePosition(position, -1);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of
     * <code>updatePosition(TeamPosition position, long userId)</code> method.
     * <p>
     * userId is negative.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testUpdatePosition_failure_negative_userId() throws Exception {
        teamManager.createTeam(teamHeader, 0);
        teamManager.addPosition(teamHeader.getTeamId(), position, 0);
        try {
            teamManager.updatePosition(position, -1);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of
     * <code>updatePosition(TeamPosition position, long userId)</code> method.
     * <p>
     * position name is null.
     * </p>
     * <p>
     * Expect InvalidPositionException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testUpdatePosition_failure_null_position_name() throws Exception {
        teamManager.createTeam(teamHeader, 0);
        teamManager.addPosition(teamHeader.getTeamId(), position, 0);
        position.setName(null);
        try {
            teamManager.updatePosition(position, 0);
            fail("Expect InvalidPositionException.");
        } catch (InvalidPositionException e) {
            // expect
        }
    }

    /**
     * Failure test of
     * <code>updatePosition(TeamPosition position, long userId)</code> method.
     * <p>
     * position name is empty.
     * </p>
     * <p>
     * Expect InvalidPositionException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testUpdatePosition_failure_empty_position_name() throws Exception {
        teamManager.createTeam(teamHeader, 0);
        teamManager.addPosition(teamHeader.getTeamId(), position, 0);
        position.setName("      ");
        try {
            teamManager.updatePosition(position, 0);
            fail("Expect InvalidPositionException.");
        } catch (InvalidPositionException e) {
            // expect
        }
    }

    /**
     * Failure test of
     * <code>updatePosition(TeamPosition position, long userId)</code> method.
     * <p>
     * position name is shorter than 5.
     * </p>
     * <p>
     * Expect InvalidPositionException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testUpdatePosition_failure_position_name_shorter_than_5() throws Exception {
        teamManager.createTeam(teamHeader, 0);
        teamManager.addPosition(teamHeader.getTeamId(), position, 0);
        position.setName("abcd");
        try {
            teamManager.updatePosition(position, 0);
            fail("Expect InvalidPositionException.");
        } catch (InvalidPositionException e) {
            // expect
        }
    }

    /**
     * Failure test of
     * <code>updatePosition(TeamPosition position, long userId)</code> method.
     * <p>
     * position name is longer than 31
     * </p>
     * <p>
     * Expect InvalidPositionException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testUpdatePosition_failure_position_name_longer_than_31() throws Exception {
        teamManager.createTeam(teamHeader, 0);
        teamManager.addPosition(teamHeader.getTeamId(), position, 0);
        char[] name = new char[32];
        Arrays.fill(name, 'a');
        position.setName(new String(name));
        try {
            teamManager.updatePosition(position, 0);
            fail("Expect InvalidPositionException.");
        } catch (InvalidPositionException e) {
            // expect
        }
    }

    /**
     * Failure test of
     * <code>updatePosition(TeamPosition position, long userId)</code> method.
     * <p>
     * position description is longer than 255.
     * </p>
     * <p>
     * Expect InvalidPositionException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testUpdatePosition_failure_position_description_longer_than_255() throws Exception {
        teamManager.createTeam(teamHeader, 0);
        teamManager.addPosition(teamHeader.getTeamId(), position, 0);
        char[] description = new char[256];
        Arrays.fill(description, 'a');
        position.setDescription(new String(description));
        try {
            teamManager.updatePosition(position, 0);
            fail("Expect InvalidPositionException.");
        } catch (InvalidPositionException e) {
            // expect
        }
    }

    /**
     * Failure test of
     * <code>updatePosition(TeamPosition position, long userId)</code> method.
     * <p>
     * position payment percentage is zero.
     * </p>
     * <p>
     * Expect InvalidPositionException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testUpdatePosition_failure_zero_position_payment_percentage() throws Exception {
        teamManager.createTeam(teamHeader, 0);
        teamManager.addPosition(teamHeader.getTeamId(), position, 0);
        position.setPaymentPercentage(0);
        try {
            teamManager.updatePosition(position, 0);
            fail("Expect InvalidPositionException.");
        } catch (InvalidPositionException e) {
            // expect
        }
    }

    /**
     * Failure test of
     * <code>updatePosition(TeamPosition position, long userId)</code> method.
     * <p>
     * member resource id is negative.
     * </p>
     * <p>
     * Expect InvalidPositionException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testUpdatePosition_failure_negative_member_resource_id_when_position_filled() throws Exception {
        teamManager.createTeam(teamHeader, 0);
        teamManager.addPosition(teamHeader.getTeamId(), position, 0);
        position.setMemberResourceId(-1);
        try {
            teamManager.updatePosition(position, 0);
            fail("Expect InvalidPositionException.");
        } catch (InvalidPositionException e) {
            // expect
        }
    }

    /**
     * Failure test of
     * <code>updatePosition(TeamPosition position, long userId)</code> method.
     * <p>
     * team is finalized.
     * </p>
     * <p>
     * Expect InvalidPositionException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testUpdatePosition_failure_finalized_team() throws Exception {
        teamManager.createTeam(teamHeader, 0);
        teamManager.addPosition(teamHeader.getTeamId(), position, 0);
        teamHeader.setFinalized(true);
        teamManager.updateTeam(teamHeader, 0);
        try {
            teamManager.updatePosition(position, 0);
            fail("Expect InvalidPositionException.");
        } catch (InvalidPositionException e) {
            // expect
        }
    }

    /**
     * Failure test of
     * <code>updatePosition(TeamPosition position, long userId)</code> method.
     * <p>
     * member resource id is not unique.
     * </p>
     * <p>
     * Expect InvalidPositionException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testUpdatePosition_failure_non_unique_member_resource_id() throws Exception {
        teamManager.createTeam(teamHeader, 0);
        teamManager.addPosition(teamHeader.getTeamId(), position, 0);
        position.setMemberResourceId(0);
        try {
            teamManager.updatePosition(position, 0);
            fail("Expect InvalidPositionException.");
        } catch (InvalidPositionException e) {
            // expect
        }
    }

    /**
     * Failure test of
     * <code>updatePosition(TeamPosition position, long userId)</code> method.
     * <p>
     * position name is not unique.
     * </p>
     * <p>
     * Expect InvalidPositionException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testUpdatePosition_failure_non_unique_name() throws Exception {
        teamManager.createTeam(teamHeader, 0);
        teamManager.addPosition(teamHeader.getTeamId(), position, 0);
        position = new TeamPosition();
        position.setName("position2");
        position.setFilled(true);
        position.setMemberResourceId(2);
        position.setPaymentPercentage(10);
        teamManager.addPosition(teamHeader.getTeamId(), position, 0);
        position.setName("position1");
        try {
            teamManager.updatePosition(position, 0);
            fail("Expect InvalidPositionException.");
        } catch (InvalidPositionException e) {
            // expect
        }
    }

    /**
     * Failure test of
     * <code>updatePosition(TeamPosition position, long userId)</code> method.
     * <p>
     * sum of payment percentage is greater than 100.
     * </p>
     * <p>
     * Expect InvalidPositionException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testUpdatePosition_failure_sum_of_payment_percentage_greater_than_100() throws Exception {
        teamManager.createTeam(teamHeader, 0);
        teamManager.addPosition(teamHeader.getTeamId(), position, 0);
        position.setPaymentPercentage(51);
        try {
            teamManager.updatePosition(position, 0);
            fail("Expect InvalidPositionException.");
        } catch (InvalidPositionException e) {
            // expect
        }
    }

    /**
     * Failure test of
     * <code>updatePosition(TeamPosition position, long userId)</code> method.
     * <p>
     * positionId is not present.
     * </p>
     * <p>
     * Expect UnknownEntityException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testUpdatePosition_failure_non_present_positionId() throws Exception {
        position.setPositionId(0);
        try {
            teamManager.updatePosition(position, 0);
            fail("Expect UnknownEntityException.");
        } catch (UnknownEntityException e) {
            // expect
        }
    }

    /**
     * Failure test of
     * <code>updatePosition(TeamPosition position, long userId)</code> method.
     * <p>
     * persistence error occurs.
     * </p>
     * <p>
     * Expect TeamPersistenceException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testUpdatePosition_failure_persistence_error_occurs() throws Exception {
        teamManager.createTeam(teamHeader, 0);
        teamManager.addPosition(teamHeader.getTeamId(), position, 0);
        teamPersistence.setThrowException(true);
        try {
            teamManager.updatePosition(position, 0);
            fail("Expect TeamPersistenceException.");
        } catch (TeamPersistenceException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>removePosition(long positionId, long userId)</code>
     * method.
     * <p>
     * positionId is negative.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testRemovePosition_failure_negative_posistionId() throws Exception {
        try {
            teamManager.removePosition(-1, 0);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>removePosition(long positionId, long userId)</code>
     * method.
     * <p>
     * userId is negative.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testRemovePosition_failure_negative_userId() throws Exception {
        teamManager.createTeam(teamHeader, 0);
        position.setFilled(false);
        position.setMemberResourceId(-1);
        teamManager.addPosition(teamHeader.getTeamId(), position, 0);
        try {
            teamManager.removePosition(position.getPositionId(), -1);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>removePosition(long positionId, long userId)</code>
     * method.
     * <p>
     * position is filled.
     * </p>
     * <p>
     * Expect PositionRemovalException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testRemovePosition_failure_filled_position() throws Exception {
        teamManager.createTeam(teamHeader, 0);
        teamManager.addPosition(teamHeader.getTeamId(), position, 0);
        try {
            teamManager.removePosition(position.getPositionId(), 0);
            fail("Expect PositionRemovalException.");
        } catch (PositionRemovalException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>removePosition(long positionId, long userId)</code>
     * method.
     * <p>
     * position is published.
     * </p>
     * <p>
     * Expect PositionRemovalException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testRemovePosition_failure_published_position() throws Exception {
        teamManager.createTeam(teamHeader, 0);
        position.setFilled(false);
        position.setMemberResourceId(-1);
        position.setPublished(true);
        teamManager.addPosition(teamHeader.getTeamId(), position, 0);
        try {
            teamManager.removePosition(position.getPositionId(), 0);
            fail("Expect PositionRemovalException.");
        } catch (PositionRemovalException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>removePosition(long positionId, long userId)</code>
     * method.
     * <p>
     * team is finalized.
     * </p>
     * <p>
     * Expect PositionRemovalException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testRemovePosition_failure_finalized_team() throws Exception {
        teamManager.createTeam(teamHeader, 0);
        position.setFilled(false);
        position.setMemberResourceId(-1);
        teamManager.addPosition(teamHeader.getTeamId(), position, 0);
        teamHeader.setFinalized(true);
        teamManager.updateTeam(teamHeader, 0);
        try {
            teamManager.removePosition(position.getPositionId(), 0);
            fail("Expect PositionRemovalException.");
        } catch (PositionRemovalException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>removePosition(long positionId, long userId)</code>
     * method.
     * <p>
     * positionId is not present.
     * </p>
     * <p>
     * Expect UnknownEntityException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testRemovePosition_failure_non_present_positionId() throws Exception {
        try {
            teamManager.removePosition(2, 0);
            fail("Expect UnknownEntityException.");
        } catch (UnknownEntityException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>removePosition(long positionId, long userId)</code>
     * method.
     * <p>
     * persistence error occurs.
     * </p>
     * <p>
     * Expect TeamPersistenceException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testRemovePosition_failure_persistence_error_occurs() throws Exception {
        teamManager.createTeam(teamHeader, 0);
        position.setFilled(false);
        position.setMemberResourceId(-1);
        teamManager.addPosition(teamHeader.getTeamId(), position, 0);
        teamPersistence.setThrowException(true);
        try {
            teamManager.removePosition(position.getPositionId(), 0);
            fail("Expect TeamPersistenceException.");
        } catch (TeamPersistenceException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>getPosition(long positionId)</code> method.
     * <p>
     * positionId is negative.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testGetPosition_failure_negative_positionId() throws Exception {
        try {
            teamManager.getPosition(-1);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>getPosition(long positionId)</code> method.
     * <p>
     * persistence error occurs.
     * </p>
     * <p>
     * Expect TeamPersistenceException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testGetPosition_failure_persistence_error_occurs() throws Exception {
        teamPersistence.setThrowException(true);
        try {
            teamManager.getPosition(0);
            fail("Expect TeamPersistenceException.");
        } catch (TeamPersistenceException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>findPositions(Filter filter)</code> method.
     * <p>
     * filter is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testFindPositions_failure_null_filter() throws Exception {
        try {
            teamManager.findPositions(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>findPositions(Filter filter)</code> method.
     * <p>
     * persistence error occurs.
     * </p>
     * <p>
     * Expect TeamPersistenceException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testFindPositions_failure_persistence_error_occurs() throws Exception {
        teamPersistence.setThrowException(true);
        try {
            teamManager.findPositions(PositionFilterFactory.createProjectIdFilter(0));
            fail("Expect TeamPersistenceException.");
        } catch (TeamPersistenceException e) {
            // expect
        }
    }
}
