/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.review.application.accuracytests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.junit.After;
import org.junit.Before;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.persistence.XMLFilePersistence;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.management.review.application.BaseLookupEntity;
import com.topcoder.management.review.application.ReviewApplication;
import com.topcoder.management.review.application.ReviewApplicationResourceRole;
import com.topcoder.management.review.application.ReviewApplicationRole;
import com.topcoder.management.review.application.ReviewApplicationStatus;
import com.topcoder.management.review.application.ReviewAuction;
import com.topcoder.management.review.application.ReviewAuctionCategory;
import com.topcoder.management.review.application.ReviewAuctionType;
import com.topcoder.management.review.application.impl.ReviewApplicationManagerImpl;
import com.topcoder.util.config.ConfigManager;

/**
 * <p>
 * Base class for accuracy test classes.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public abstract class BaseAccuracyTests {
    /**
     * <p>
     * Represents the path for accuracy test files.
     * </p>
     */
    public static final String ACCURACY_TEST_FILES = "test_files" + File.separator + "accuracy" + File.separator;
    /**
     * <p>
     * Represents the path for failure test files.
     * </p>
     */
    public static final String FAILURE_TEST_FILES = "test_files" + File.separator + "failure" + File.separator;

    /**
     * <p>
     * Represents the configuration file used in tests.
     * </p>
     */
    private static final String CONFIG_FILE = ACCURACY_TEST_FILES + "ReviewApplicationManagement.xml";

    /**
     * <p>
     * Represents the ReviewApplicationStatus list in database.
     * </p>
     */
    protected static final List<ReviewApplicationStatus> STATUSES = Arrays.asList(new ReviewApplicationStatus[] {
        new ReviewApplicationStatus(1, "Pending"), new ReviewApplicationStatus(2, "Cancelled"),
        new ReviewApplicationStatus(3, "Approved"), new ReviewApplicationStatus(4, "Rejected")});

    /**
     * <p>
     * Represents the ReviewAuctionCategory list in database.
     * </p>
     */
    protected static final List<ReviewAuctionCategory> AUCTION_CATEGORY = Arrays.asList(new ReviewAuctionCategory[] {
        new ReviewAuctionCategory(1, "Contest Review"), new ReviewAuctionCategory(2, "Specification Review")});

    /**
     * <p>
     * Represents the ReviewAuctionType list in database.
     * </p>
     */
    protected static final List<ReviewAuctionType> AUCTION_TYPES = Arrays.asList(new ReviewAuctionType[] {
        new ReviewAuctionType(1L, "Regular Contest Review", AUCTION_CATEGORY.get(0), Arrays
            .asList(new ReviewApplicationRole[] {
                new ReviewApplicationRole(1, "Primary Reviewer", 1, Arrays.asList(new ReviewApplicationResourceRole[] {
                    new ReviewApplicationResourceRole(2, true), new ReviewApplicationResourceRole(4, false),
                    new ReviewApplicationResourceRole(8, true), new ReviewApplicationResourceRole(9, true)})),
                new ReviewApplicationRole(2, "Secondary Reviewer", 3, Arrays
                    .asList(new ReviewApplicationResourceRole[] {new ReviewApplicationResourceRole(4, false)}))})),
        new ReviewAuctionType(2L, "Component Development Review", AUCTION_CATEGORY.get(0), Arrays
            .asList(new ReviewApplicationRole[] {
                new ReviewApplicationRole(3, "Primary Failure Reviewer", 1, Arrays
                    .asList(new ReviewApplicationResourceRole[] {new ReviewApplicationResourceRole(2, true),
                        new ReviewApplicationResourceRole(6, true), new ReviewApplicationResourceRole(8, true),
                        new ReviewApplicationResourceRole(9, true)})),
                new ReviewApplicationRole(4, "Accuracy Reviewer", 1, Arrays
                    .asList(new ReviewApplicationResourceRole[] {new ReviewApplicationResourceRole(5, true)})),
                new ReviewApplicationRole(5, "Stress Reviewer", 1, Arrays
                    .asList(new ReviewApplicationResourceRole[] {new ReviewApplicationResourceRole(7, true)})),
                new ReviewApplicationRole(6, "Failure Reviewer", 1, Arrays
                    .asList(new ReviewApplicationResourceRole[] {new ReviewApplicationResourceRole(6, true)}))})),
        new ReviewAuctionType(3L, "Specification Review", AUCTION_CATEGORY.get(1), Arrays
            .asList(new ReviewApplicationRole[] {new ReviewApplicationRole(7, "Specification Reviewer", 1, Arrays
                .asList(new ReviewApplicationResourceRole[] {new ReviewApplicationResourceRole(18, true)}))}))});
    /**
     * Represents the connection instance.
     */
    private Connection connection;

    /**
     * <p>
     * Sets up for each test.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Before
    public void setUp() throws Exception {
        removeAllNamespaces();
        ConfigManager.getInstance().add("SearchBundleManager.xml");

        connection = createConnection();
        clearData();
        prepareData();
    }

    /**
     * <p>
     * Tear down for each test.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @After
    public void tearDown() throws Exception {
        if (connection == null) {
            connection = createConnection();
        }
        clearData();
        closeConnection();
        removeAllNamespaces();
    }

    /**
     * <p>
     * Gets instance of ReviewAuction.
     * </p>
     *
     * @param seed
     *            the seed.
     *
     * @return the instance of ReviewAuction.
     */
    protected static ReviewAuction getReviewAuction(int seed) {
        ReviewAuction entity = new ReviewAuction();
        entity.setAssignmentDate(new Date());
        entity.setAuctionType(AUCTION_TYPES.get(seed % AUCTION_TYPES.size()));
        entity.setOpen(true);
        entity.setOpenPositions(Arrays.asList(0L, 0L));
        entity.setProjectId(seed);
        return entity;
    }

    /**
     * <p>
     * Gets an instance of ReviewApplication.
     * </p>
     *
     * @param seed
     *            the seed.
     * @return the instance of ReviewApplication.
     */
    protected ReviewApplication getReviewApplication(int seed) {
        ReviewApplication application = new ReviewApplication();
        application.setUserId(100);
        application.setAuctionId(seed);
        application.setApplicationRoleId(3);
        application.setStatus(STATUSES.get(seed % STATUSES.size()));
        application.setCreateDate(new Date());
        return application;
    }

    /**
     * Asserts the 2 entities are equal to.
     *
     * @param <T>
     *            the entity type.
     * @param list1
     *            the first list.
     * @param list2
     *            the second list.
     */
    protected static <T extends BaseLookupEntity> void assertEntities(List<T> list1, List<T> list2) {
        assertEquals("This count is diferent.", list1.size(), list2.size());
        for (int i = 0; i < list1.size(); i++) {
            assertEntity(list1.get(i), list2.get(i));
        }
    }

    /**
     * Asserts the 2 entities are equal to.
     *
     * @param list1
     *            the first list.
     * @param list2
     *            the second list.
     * @param hasResource
     *            test resourceRoles or not.
     */
    protected static void assertReviewAuctionTypes(List<ReviewAuctionType> list1, List<ReviewAuctionType> list2,
        boolean hasResource) {
        assertEquals("This count is diferent.", list1.size(), list2.size());
        for (int i = 0; i < list1.size(); i++) {
            assertEntity(list1.get(i), list2.get(i), hasResource);
        }
    }

    /**
     * Asserts the 2 entities are equal to.
     *
     * @param entity1
     *            the first entity
     * @param entity2
     *            the second entity
     * @param hasResource
     *            test resourceRoles or not.
     */
    protected static void assertEntity(ReviewAuctionType entity1, ReviewAuctionType entity2, boolean hasResource) {
        System.out.println("ReviewAuctionType : " + entity1.getId() + entity1.getName());
        assertEquals("The id of ReviewAuctionType is incorrect.", entity1.getId(), entity2.getId());
        assertEquals("The name of ReviewAuctionType is incorrect.", entity1.getName(), entity2.getName());

        assertEntity(entity1.getAuctionCategory(), entity2.getAuctionCategory());
        assertEquals("The size of applicationRoles is incorrect.", entity1.getApplicationRoles().size(), entity2
            .getApplicationRoles().size());
        for (int i = 0; i < entity1.getApplicationRoles().size(); i++) {
            ReviewApplicationRole role1 = entity1.getApplicationRoles().get(i);
            ReviewApplicationRole role2 = entity2.getApplicationRoles().get(i);

            assertEntity(role1, role2, hasResource);
        }
    }

    /**
     * Asserts the 2 entities are equal to.
     *
     * @param entity1
     *            the first entity
     * @param entity2
     *            the second entity
     * @param hasResource
     *            test resourceRoles or not.
     */
    protected static void assertEntity(ReviewApplicationRole entity1, ReviewApplicationRole entity2, boolean hasResource) {
        System.out.println("ReviewApplicationRole : " + entity1.getId() + entity1.getName());
        assertEquals("The id of ReviewApplicationRole is incorrect.", entity1.getId(), entity2.getId());
        assertEquals("The name of ReviewApplicationRole is incorrect.", entity1.getName(), entity2.getName());
        assertEquals("The positions of ReviewApplicationRole is incorrect.", entity1.getPositions(), entity2
            .getPositions());

        if (hasResource) {
            assertEquals("The resourceRoles of ReviewApplicationRole is incorrect.", entity1.getResourceRoles().size(),
                entity2.getResourceRoles().size());
            for (int i = 0; i < entity1.getResourceRoles().size(); i++) {
                System.out.println(entity1.getResourceRoles().get(i));
                assertEquals("The resourceRoles of ReviewApplicationRole is incorrect.", entity1.getResourceRoles()
                    .get(i).getResourceRoleId(), entity2.getResourceRoles().get(i).getResourceRoleId());
                assertEquals("The resourceRoles of ReviewApplicationRole is incorrect.", entity1.getResourceRoles()
                    .get(i).isUniqueRole(), entity2.getResourceRoles().get(i).isUniqueRole());
            }
        } else {
            assertNull("The resourceRoles should be null.", entity2.getResourceRoles());
        }
    }

    /**
     * Asserts the 2 entities are equal to.
     *
     * @param entity1
     *            the first entity
     * @param entity2
     *            the second entity
     */
    protected static void assertEntity(BaseLookupEntity entity1, BaseLookupEntity entity2) {
        assertEquals("The id of BaseLookupEntity is incorrect.", entity1.getId(), entity2.getId());
        assertEquals("The name of BaseLookupEntity is incorrect.", entity1.getName(), entity2.getName());
    }

    /**
     * Asserts the 2 entities are equal to.
     *
     * @param entity1
     *            the first entity
     * @param entity2
     *            the second entity
     */
    protected static void assertEntity(ReviewApplication entity1, ReviewApplication entity2) {
        assertEquals("The applicationRoleId of ReviewApplication is incorrect.", entity1.getApplicationRoleId(),
            entity2.getApplicationRoleId());
        assertEquals("The auctionId of ReviewApplication is incorrect.", entity1.getAuctionId(), entity2.getAuctionId());
        assertEquals("The cetCreateDate of ReviewApplication is incorrect.", entity1.getCreateDate(), entity2
            .getCreateDate());
        assertEquals("The id of ReviewApplication is incorrect.", entity1.getId(), entity2.getId());
        assertEquals("The name of ReviewApplication is incorrect.", entity1.getStatus(), entity2.getStatus());
        assertEquals("The userId of ReviewApplication is incorrect.", entity1.getUserId(), entity2.getUserId());
    }

    /**
     * Asserts the 2 entities are equal to.
     *
     * @param entity1
     *            the first entity
     * @param entity2
     *            the second entity
     */
    protected static void assertEntity(ReviewAuction entity1, ReviewAuction entity2) {
        assertEquals("The assignmentDate of ReviewAuction is incorrect.", entity1.getAssignmentDate(), entity2
            .getAssignmentDate());
        assertEquals("The auctionType of ReviewAuction is incorrect.", entity1.getAuctionType(), entity2
            .getAuctionType());
        assertEquals("The cetCreateDate of ReviewAuction is incorrect.", entity1.getId(), entity2.getId());
        assertEquals("The name of ReviewAuction is incorrect.", entity1.getOpenPositions().size(), entity2
            .getOpenPositions().size());
        for (int i = 0; i < entity1.getOpenPositions().size(); i++) {
            assertEquals("The name of ReviewAuction is incorrect.", entity1.getOpenPositions().get(i), entity2
                .getOpenPositions().get(i));
        }
        assertEquals("The userId of ReviewAuction is incorrect.", entity1.getProjectId(), entity2.getProjectId());
    }

    /**
     * <p>
     * Returns the record count by executing the sql.
     * </p>
     *
     * @param connection
     *            the connection
     * @param sql
     *            the SQL string
     * @param params
     *            the parameters
     *
     * @return the record count
     *
     * @throws Exception
     *             to JUnit.
     */
    public static int count(Connection connection, String sql, Object... params) throws Exception {
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        try {
            // Set parameters
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }

            // Execute
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
            return -1;
        } finally {
            preparedStatement.close();
        }
    }

    /**
     * <p>
     * Gets the configuration object for persistence class.
     * </p>
     *
     * @param namespace
     *            the namespace.
     *
     * @return the configuration object for persistence class.
     *
     * @throws Exception
     *             to JUnit.
     */
    public static ConfigurationObject getPersistenceConfig(String namespace) throws Exception {
        XMLFilePersistence persistence = new XMLFilePersistence();
        ConfigurationObject config = persistence.loadFile(namespace, new File(CONFIG_FILE)).getChild(namespace);

        return config.getChild("persistenceConfig");
    }

    /**
     * <p>
     * Gets the configuration object used for tests.
     * </p>
     *
     * @param namespace
     *            the namespace.
     *
     * @return the configuration object.
     *
     * @throws Exception
     *             to JUnit.
     */
    public static ConfigurationObject getConfig(String namespace) throws Exception {
        XMLFilePersistence persistence = new XMLFilePersistence();

        // Get configuration
        ConfigurationObject obj = persistence.loadFile(namespace, new File(CONFIG_FILE));

        return obj.getChild(namespace);
    }

    /**
     * <p>
     * Closes the connection.
     * </p>
     */
    private void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException ex) {
                // ignore
            }
        }
        connection = null;
    }

    /**
     * <p>
     * Gets a connection.
     * </p>
     *
     * @return the connection.
     *
     * @throws Exception
     *             to JUnit.
     */
    private static Connection createConnection() throws Exception {
        DBConnectionFactoryImpl dbConnectionFactory = new DBConnectionFactoryImpl(getPersistenceConfig(
            ReviewApplicationManagerImpl.DEFAULT_CONFIG_NAMESPACE).getChild("dbConnectionFactoryConfig"));
        return dbConnectionFactory.createConnection();
    }

    /**
     * <p>
     * Gets the connection.
     * </p>
     *
     * @return the connection.
     *
     * @throws Exception
     *             to JUnit.
     */
    protected Connection getConnection() throws Exception {
        return connection;
    }

    /**
     * <p>
     * Executes the SQL statements in the file.
     * </p>
     *
     * @param connection
     *            the connection.
     * @param file
     *            the file containing SQL statement.
     *
     * @throws Exception
     *             to JUnit.
     */
    private static void execute(Connection connection, String file) throws Exception {
        Statement stmt = null;
        try {
            stmt = connection.createStatement();

            String[] values = readFile(file).split(";");
            for (int i = 0; i < values.length; i++) {
                String sql = values[i].trim();
                if (sql.length() > 0) {
                    stmt.executeUpdate(sql);
                }
            }
        } finally {
            stmt.close();
        }
    }

    /**
     * <p>
     * Reads the file content.
     * </p>
     *
     * @param fileName
     *            the name of file to read.
     *
     * @return the file content.
     *
     * @throws IOException
     *             to JUnit.
     */
    private static String readFile(String fileName) throws IOException {
        Reader reader = null;
        try {
            reader = new FileReader(fileName);
            StringBuilder sb = new StringBuilder();
            char[] buf = new char[1024];
            int i = 0;
            while ((i = reader.read(buf)) != -1) {
                sb.append(buf, 0, i);
            }
            return sb.toString();
        } finally {
            reader.close();
        }
    }

    /**
     * <p>
     * Removes all namespaces.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    private static void removeAllNamespaces() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();

        for (Iterator<?> itor = cm.getAllNamespaces(); itor.hasNext();) {
            String ns = itor.next().toString();
            if (!"com.topcoder.util.log".equals(ns)) {
                cm.removeNamespace(ns);
            }
        }
    }

    /**
     * <p>
     * Clears the test data.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    protected void clearData() throws Exception {
        execute(connection, ACCURACY_TEST_FILES + "clear.sql");
    }

    /**
     * <p>
     * Prepare the test data.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    private void prepareData() throws Exception {
        execute(connection, ACCURACY_TEST_FILES + "data.sql");
    }

    /**
     * <p>
     * Returns the record count by executing the sql.
     * </p>
     *
     * @param connection
     *            the connection
     * @param sql
     *            the SQL string
     *
     *
     * @throws Exception
     *             to JUnit.
     */
    protected void execute(String sql) throws Exception {
        Statement stmt = connection.createStatement();
        try {
            stmt.execute(sql);
        } finally {
            stmt.close();
        }
    }
}