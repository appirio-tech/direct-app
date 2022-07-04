/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.team;

import com.topcoder.management.team.impl.TeamImpl;
import com.topcoder.management.team.impl.TeamManagerImpl;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.util.idgenerator.IDGeneratorFactory;
import com.topcoder.util.log.LogManager;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Demo Test for com.topcoder.management.team package.
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DemoUnitTest extends TestCase {
    /** Provide a mock TeamPersistence for testing. */
    private MockTeamPersistence persistence;

    /** Provide a TeamManagerImpl for testing. */
    private TeamManagerImpl manager;

    /** Provide a TeamHeader for testing. */
    private TeamHeader header = new TeamHeader("header_name", false, 21, 1, 1, 10, "header_des");

    /** Provide positions for testing. */
    private TeamPosition position = new TeamPosition("position", false, 22, 10, "position_name", 11, false);

    /** Provide a team for testing. */
    private Team defaultTeam = new TeamImpl(header, new TeamPosition[] {position});

    /**
     * <p>
     * Aggregates all tests in this class.
     * </p>
     * @return Test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(DemoUnitTest.class);
    }

    /**
     * <p>
     * Sets up the environment for the TestCase.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    protected void setUp() throws Exception {
        TestHelper.addConfig();

        // In this unit test we have only a mock persistence, so we have to force this persistence to return some
        // non-null values to make sure the demo works
        persistence = new MockTeamPersistence();
        persistence.setTeam(defaultTeam);
        persistence.setPosition(position);
        // Create the manager
        manager = new TeamManagerImpl(persistence, IDGeneratorFactory.getIDGenerator(TestHelper.IDGENERATOR_NAME),
            LogManager.getLog(TestHelper.LOG_NAME));
    }

    /**
     * <p>
     * Tears down the environment for the TestCase.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    protected void tearDown() throws Exception {
        TestHelper.clearConfig();
    }

    /**
     * The demo for the usage of TeamHeader and TeamPosition instances.
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testDemoHeaderAndPosition() throws Exception {
        // Two constructors for TeamHeader
        TeamHeader teamHeader = new TeamHeader();
        teamHeader = new TeamHeader("Team TAG", false, 1, 1, 1, 50, "This is team TAG. Deal with it.");

        // Setter and getter
        teamHeader.setTeamId(7);
        assertEquals(50, teamHeader.getCaptainPaymentPercentage());

        // Custom Properties
        teamHeader.setProperty("expediteStatus", "expedite");
        assertEquals("expedite", teamHeader.getProperty("expediteStatus"));
        assertEquals("expedite", teamHeader.getAllProperties().get("expediteStatus"));

        // Two constructors for TeamPosition
        TeamPosition teamPosition = new TeamPosition();
        teamPosition = new TeamPosition("This is a designer position", false, 2, 50, "Designer", 2, false);

        // Setter and getter
        teamPosition.setPositionId(7);
        assertEquals(50, teamPosition.getPaymentPercentage());

        // Custom Properties
        teamPosition.setProperty("expediteStatus", "expedite");
        assertEquals("expedite", teamPosition.getProperty("expediteStatus"));
        assertEquals("expedite", teamPosition.getAllProperties().get("expediteStatus"));
    }

    /**
     * The demo for the usage of constructors of TeamManagerImpl.
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testDemoConstructor() throws Exception {
        // Create manager from the default configuration
        manager = new TeamManagerImpl();
        // Create manager from a specified namespace
        manager = new TeamManagerImpl(TeamManagerImpl.DEFAULT_NAMESPACE);
        // Create manager using own parameters
        manager = new TeamManagerImpl(persistence, IDGeneratorFactory.getIDGenerator(TestHelper.IDGENERATOR_NAME),
            LogManager.getLog(TestHelper.LOG_NAME));
    }

    /**
     * This demo has provided a typical scenario for adding/removing/updating items in the manager.
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testDemoProcess() throws Exception {
        // commence by creating a TeamHeader in the manager
        TeamHeader teamHeader = new TeamHeader();
        teamHeader.setName("Team TAG");
        teamHeader.setDescription("This is team TAG. Deal with it.");
        teamHeader.setFinalized(false);
        teamHeader.setProjectId(1);
        teamHeader.setCaptainResourceId(1);
        teamHeader.setCaptainPaymentPercentage(50);
        teamHeader.setProperty("expediteStatus", "expedite");

        manager.createTeam(teamHeader, 1);
        // team header is added: assume it has a teamId of 10

        // add a position to the above team header
        TeamPosition teamPosition2 = new TeamPosition();
        teamPosition2.setName("Designer");
        teamPosition2.setDescription("This is a designer position");
        teamPosition2.setFilled(false);
        teamPosition2.setPublished(false);
        teamPosition2.setMemberResourceId(2);
        teamPosition2.setPaymentPercentage(50);
        teamPosition2.setProperty("country", "Poland");

        manager.addPosition(10, teamPosition2, 1);
        // position is added to the above team header: assume it has a positionId of 12

        // The above position may not work for us, so it would be eliminated and split into two, with new resources.
        // The position is eliminated and replaced with two separate positions. Note that percentages still add up to
        // 100.
        manager.removePosition(12, 1);

        TeamPosition teamPosition3 = new TeamPosition();
        teamPosition3.setName("Component Designer");
        teamPosition3.setDescription("This is a component designer position");
        teamPosition3.setFilled(true);
        teamPosition3.setPublished(true);
        teamPosition3.setMemberResourceId(3);
        teamPosition3.setPaymentPercentage(25);
        teamPosition3.setProperty("country", "Poland");

        TeamPosition teamPosition4 = new TeamPosition();
        teamPosition4.setName("Specification Writer");
        teamPosition4.setDescription("This is a specification writer position");
        teamPosition4.setFilled(true);
        teamPosition4.setPublished(true);
        teamPosition4.setMemberResourceId(4);
        teamPosition4.setPaymentPercentage(25);
        teamPosition2.setProperty("country", "France");

        manager.addPosition(10, teamPosition3, 1);
        manager.addPosition(10, teamPosition4, 1);
        // positions are added to the above team header: assume they have a positionId of 13 and 14, respectively

        // we may now want to update the captain in the header, as the primary resource is gone.
        teamHeader.setName("Team Europe");
        teamHeader.setDescription("This is team Europe. We are neural.");
        teamHeader.setProperty("expediteStatus", "normal");
        teamHeader.setCaptainResourceId(10);
        teamHeader.setFinalized(true);

        manager.updatePosition(teamPosition4, 1);
        manager.updateTeam(teamHeader, 1);
        // The team is now finalized and locked. It cannot be removed. But suppose there was another team (id 9), that
        // was previously present, and we wanted to removed it.
        manager.removeTeam(9, 1);
    }

    /**
     * This demo has provided a typical scenario for queries in the manager.
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testDemoQuery() throws Exception {
        // commence by creating a TeamHeader in the manager
        // Now we can search for the team. Suppose we want to know which teams are in projectId=1.
        TeamHeader[] teams = manager.findTeams(1);
        // The result would be one team (id=10)

        // we can retrieve the team by its ID. Suppose we want to get the team we just created and finalized (ID = 10)
        Team team = manager.getTeam(10);
        // This would get us the team (id=10), and all its positions

        // we can also retrieve the team a position is in. Suppose we want to know which team position 14 is in
        team = manager.getTeamFromPosition(14);
        // This would also get us the team (id=10), and all its positions

        // we can also retrieve the position. Suppose we want to get the Position with id 13
        TeamPosition retrievedPosition = manager.getPosition(13);
        // This would get us the position (id=13)

        // Finally, we can perform searches, and use the filter factories for this purpose. We can create a filter
        // that finds all teams with projectId=1 that have an "expediteStatus" custom property.
        Filter projectIdFilter = TeamFilterFactory.createProjectIdFilter(1);
        Filter customFilter = TeamFilterFactory.createCustomPropertyNameFilter("expediteStatus");
        Filter combinedFilter = UtilityFilterFactory.createAndFilter(projectIdFilter, customFilter);

        teams = manager.findTeams(combinedFilter);
        // This would get us the team (id=10)

        // We can perform filter searches for positions as well. We can look for all positions in team with ID = 10
        // that are published
        Filter teamIdFilter = PositionFilterFactory.createTeamIdFilter(1);
        Filter publishedFilter = PositionFilterFactory.createPublishedFilter(true);
        Filter combinedPositionFilter = UtilityFilterFactory.createAndFilter(teamIdFilter, publishedFilter);

        TeamPosition[] positions = manager.findPositions(combinedPositionFilter);
        // This would get us the two positions (position ID 13, 14)
    }
}
