/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.team;

import java.util.Iterator;
import java.util.Map;

import junit.framework.Assert;

import com.topcoder.util.config.ConfigManager;

/**
 * <p>
 * A helper class to perform those common operations such as clearing the configuration which are helpful for the
 * test.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class TestHelper {

    /** The default namespace for team manager. */
    public static final String DEFAULT_NAMESPACE = "com.topcoder.management.team.impl.TeamManagerImpl";

    /** The log name for logger. */
    public static final String LOG_NAME = "com.topcoder.util.log";

    /** The IDGenerator name. */
    public static final String IDGENERATOR_NAME = "team_manager_id_generator";

    /**
     * <p>
     * The config file for the project.
     * </p>
     */
    public static final String CONFIG = "config.xml";

    /**
     * <p>
     * The config file for db connection factory.
     * </p>
     */
    public static final String CONFIG_DBCONNECTION = "DBConnectionFactory.xml";

    /**
     * <p>
     * Creates a new instance of <code>TestHelper</code> class. The private constructor prevents the creation of a
     * new instance.
     * </p>
     */
    private TestHelper() {
    }

    /**
     * <p>
     * Add the valid config files for testing.
     * </p>
     * @throws Exception
     *             unexpected exception.
     */
    public static void addConfig() throws Exception {
        clearConfig();

        ConfigManager configManager = ConfigManager.getInstance();
        configManager.add(CONFIG);
        configManager.add(CONFIG_DBCONNECTION);
    }

    /**
     * <p>
     * Clear the config.
     * </p>
     * @throws Exception
     *             unexpected exception.
     */
    public static void clearConfig() throws Exception {
        ConfigManager configManager = ConfigManager.getInstance();

        for (Iterator iter = configManager.getAllNamespaces(); iter.hasNext();) {
            configManager.removeNamespace((String) iter.next());
        }
    }

    /**
     * <p>
     * Assert if header matches the given inner fields.
     * </p>
     * @param header
     *            the received header
     * @param name
     *            name field
     * @param finalized
     *            finalized field
     * @param projectId
     *            projectId field
     * @param teamId
     *            teamId field
     * @param captainResourceId
     *            captainResourceId field
     * @param captainPaymentPercentage
     *            captainPaymentPercentage field
     * @param description
     *            description field
     * @param customProperties
     *            customProperties field
     */
    public static void assertTeamHeader(TeamHeader header, String name, boolean finalized, long projectId,
        long teamId, long captainResourceId, int captainPaymentPercentage, String description, Map customProperties) {
        Assert.assertEquals("name mismatch", name, header.getName());
        Assert.assertEquals("finalized mismatch", finalized, header.getFinalized());
        Assert.assertEquals("projectId mismatch", projectId, header.getProjectId());
        Assert.assertEquals("teamId mismatch", teamId, header.getTeamId());
        Assert.assertEquals("captainResourceId mismatch", captainResourceId, header.getCaptainResourceId());
        Assert.assertEquals("captainPaymentPercentage mismatch", captainPaymentPercentage, header
            .getCaptainPaymentPercentage());
        Assert.assertEquals("description mismatch", description, header.getDescription());
        Assert.assertEquals("customProperties mismatch", customProperties, header.getAllProperties());
    }

    /**
     * <p>
     * Assert if position matches the given inner fields.
     * </p>
     * @param position
     *            the received position
     * @param description
     *            description field
     * @param filled
     *            filled field
     * @param memberResourceId
     *            memberResourceId field
     * @param paymentPercentage
     *            paymentPercentage field
     * @param name
     *            name field
     * @param positionId
     *            positionId field
     * @param published
     *            published field
     * @param customProperties
     *            customProperties field
     */
    public static void assertTeamPosition(TeamPosition position, String description, boolean filled,
        long memberResourceId, int paymentPercentage, String name, long positionId, boolean published,
        Map customProperties) {
        Assert.assertEquals("description mismatch", description, position.getDescription());
        Assert.assertEquals("filled mismatch", filled, position.getFilled());
        Assert.assertEquals("captainResourceId mismatch", memberResourceId, position.getMemberResourceId());
        Assert.assertEquals("captainPaymentPercentage mismatch", paymentPercentage, position.getPaymentPercentage());
        Assert.assertEquals("name mismatch", name, position.getName());
        Assert.assertEquals("positionId mismatch", positionId, position.getPositionId());
        Assert.assertEquals("published mismatch", published, position.getPublished());
        Assert.assertEquals("customProperties mismatch", customProperties, position.getAllProperties());
    }

    /**
     * <p>
     * A helper method to new a TeamHeader instance. Here parameters might be invalid, for example -1.
     * </p>
     * @param name
     *            name field
     * @param finalized
     *            finalized field
     * @param projectId
     *            projectId field
     * @param teamId
     *            teamId field
     * @param captainResourceId
     *            captainResourceId field
     * @param captainPaymentPercentage
     *            captainPaymentPercentage field
     * @param description
     *            description field
     * @return the created TeamHeader
     */
    public static TeamHeader myNewTeamHeader(String name, boolean finalized, long projectId, long teamId,
        long captainResourceId, int captainPaymentPercentage, String description) {
        TeamHeader header = new TeamHeader();
        // Create a new TeamHeader, and set the inner fields only when they are valid.
        header.setName(name);
        header.setFinalized(finalized);
        if (projectId >= 0) {
            header.setProjectId(projectId);
        }
        if (teamId >= 0) {
            header.setTeamId(teamId);
        }
        if (captainResourceId >= 0) {
            header.setCaptainResourceId(captainResourceId);
        }
        if (captainPaymentPercentage >= 0) {
            header.setCaptainPaymentPercentage(captainPaymentPercentage);
        }
        header.setDescription(description);
        return header;
    }

    /**
     * <p>
     * A helper method to new a TeamPosition instance. Here parameters might be invalid, for example -1.
     * </p>
     * @param description
     *            the description field
     * @param filled
     *            the filled field
     * @param memberResourceId
     *            the memberResourceId field
     * @param paymentPercentage
     *            the paymentPercentage field
     * @param name
     *            the name field
     * @param positionId
     *            the positionId field
     * @param published
     *            the published field
     * @return the created TeamPosition
     */
    public static TeamPosition myNewTeamPosition(String description, boolean filled, long memberResourceId,
        int paymentPercentage, String name, long positionId, boolean published) {
        TeamPosition position = new TeamPosition();
        // Create a new TeamPosition, and set the inner fields only when they are valid.
        position.setDescription(description);
        position.setFilled(filled);
        if (memberResourceId >= 0) {
            position.setMemberResourceId(memberResourceId);
        }
        if (paymentPercentage >= 0) {
            position.setPaymentPercentage(paymentPercentage);
        }
        position.setName(name);
        if (positionId >= 0) {
            position.setPositionId(positionId);
        }
        position.setPublished(published);
        return position;
    }

    /**
     * <p>
     * A helper method to generate a string with 'length' as length.
     * </p>
     * @param length
     *            the given length of the string to be generated
     * @return the generated string.
     */
    public static String generateString(int length) {
        StringBuffer sb = new StringBuffer(" ");
        for (int i = 1; i < length; i++) {
            sb.append('a');
        }
        return sb.toString();
    }
}
