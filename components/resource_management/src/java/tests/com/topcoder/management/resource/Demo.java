/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import junit.framework.TestCase;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.management.resource.persistence.MockResourcePersistenceImpl;
import com.topcoder.management.resource.persistence.PersistenceResourceManager;
import com.topcoder.management.resource.persistence.ResourcePersistence;
import com.topcoder.management.resource.search.NotificationFilterBuilder;
import com.topcoder.management.resource.search.NotificationTypeFilterBuilder;
import com.topcoder.management.resource.search.ResourceFilterBuilder;
import com.topcoder.management.resource.search.ResourceRoleFilterBuilder;
import com.topcoder.search.builder.SearchBundle;
import com.topcoder.search.builder.SearchBundleManager;
import com.topcoder.search.builder.filter.AndFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.datavalidator.LongValidator;
import com.topcoder.util.datavalidator.StringValidator;
import com.topcoder.util.idgenerator.IDGenerator;
import com.topcoder.util.idgenerator.IDGeneratorFactory;

/**
 * Demo for this component.
 *
 * In this demo, it provides the usage of using the persistence resource manager
 *
 * to create Resource and ResourceRole, create the persistence and manager, to
 *
 * update the resource and resource roles, update all resources for a project,
 *
 * retrieve and search resources, add and remove notifications and retrieve notifications, etc.
 *
 * @author kinfkong, Rica, Xuchen
 * @version 1.1
 * @since 1.0
 */
public class Demo extends TestCase {

    /**
     * The config file path for the DBConnectionFactory.
     */
    private static final String DB_CONNECTION_CONFIG = "test_files" + File.separator + "ConnectionFactory.xml";

    /**
     * The config file path for the SearchBundleManager.
     */
    private static final String SEARCH_BUNDLE_MANAGER = "test_files" + File.separator + "SearchBundleManager.xml";

    /**
     * An instance of ResourcePersistence for tests.
     */
    private ResourcePersistence persistence;

    /**
     * An instance of SearchBundle for tests.
     */
    private SearchBundle resourceSearchBundle;

    /**
     * An instance of SearchBundle for tests.
     */
    private SearchBundle resourceRoleSearchBundle;

    /**
     * An instance of SearchBundle for tests.
     */
    private SearchBundle notificationSearchBundle;

    /**
     * An instance of SearchBundle for tests.
     */
    private SearchBundle notificationTypeSearchBundle;

    /**
     * An instance of IDGenrator for tests.
     */
    private IDGenerator resourceIdGenerator;

    /**
     * An instance of IDGenrator for tests.
     */
    private IDGenerator resourceRoleIdGenerator;

    /**
     * An instance of IDGenrator for tests.
     */
    private IDGenerator notificationTypeIdGenerator;

    /**
     * An instance of ConfigManager for tests.
     */
    private ConfigManager cm = ConfigManager.getInstance();

    /**
     * An instance of PersistenceResourceManager for tests.
     */
    private PersistenceResourceManager manager = null;

    /**
     * Shows how to create the resource and resource role.
     */
    public void testDemo_CreateResourceAndResourceRole() {
        ResourceRole resourceRole = new ResourceRole();
        resourceRole.setName("Some Resource Role");
        resourceRole.setDescription("This role plays some purpose");

        // Note that it is not necessary to set any other field of the
        // resource because they are all optional
        Resource resource = new Resource();
        resource.setResourceRole(resourceRole);

        // The creation of notification types is entirely similar
        // to the calls above.
    }

    /**
     * Shows how to create the persistence and the persistence resource manager.
     *
     * @throws Exception to JUnit
     */
    public void testDemo_CreatePersistenceAndManager() throws Exception {
        // set the persistence
        persistence = new MockResourcePersistenceImpl();

        // get the id generators
        resourceIdGenerator = IDGeneratorFactory.getIDGenerator(
                PersistenceResourceManager.RESOURCE_ID_GENERATOR_NAME);
        resourceRoleIdGenerator = IDGeneratorFactory.getIDGenerator(
                PersistenceResourceManager.RESOURCE_ROLE_ID_GENERATOR_NAME);
        notificationTypeIdGenerator = IDGeneratorFactory.getIDGenerator(
                PersistenceResourceManager.NOTIFICATION_TYPE_ID_GENERATOR_NAME);

        // get the search bundles
        SearchBundleManager searchBundleManager = new SearchBundleManager(
                "com.topcoder.search.builder.SearchBundleManager");


        resourceSearchBundle = searchBundleManager.getSearchBundle(
                PersistenceResourceManager.RESOURCE_SEARCH_BUNDLE_NAME);
        // set it searchable
        setAllFieldsSearchable(resourceSearchBundle);

        resourceRoleSearchBundle = searchBundleManager.getSearchBundle(
                PersistenceResourceManager.RESOURCE_ROLE_SEARCH_BUNDLE_NAME);
        // set it searchable
        setAllFieldsSearchable(resourceRoleSearchBundle);

        notificationSearchBundle = searchBundleManager.getSearchBundle(
                PersistenceResourceManager.NOTIFICATION_SEARCH_BUNDLE_NAME);
        // set it searchable
        setAllFieldsSearchable(notificationSearchBundle);

        notificationTypeSearchBundle = searchBundleManager.getSearchBundle(
                PersistenceResourceManager.NOTIFICATION_TYPE_SEARCH_BUNDLE_NAME);
        // set it searchable
        setAllFieldsSearchable(notificationTypeSearchBundle);

        // initialize the PersistenceResourceManager
        manager = new PersistenceResourceManager(persistence, searchBundleManager);

        // or pass the arguments to the constructor directly
        manager = new PersistenceResourceManager(persistence, resourceSearchBundle,
                resourceRoleSearchBundle, notificationSearchBundle,
                notificationTypeSearchBundle, resourceIdGenerator,
                resourceRoleIdGenerator, notificationTypeIdGenerator);
    }

    /**
     * Shows how to put the resource and resource role to persistence.
     *
     * @throws Exception to JUnit
     */
    public void testDemo_createResourceOrResourceRoleToPersistence() throws Exception {
        ResourceRole resourceRole = new ResourceRole();
        resourceRole.setName("Some Resource Role");
        resourceRole.setDescription("This role plays some purpose");
        Resource resource = new Resource();
        resource.setResourceRole(resourceRole);

        // Note that this will assign an id to the resource and
        // resource role
        manager.updateResourceRole(resourceRole, "Operator #1");
        manager.updateResource(resource, "Operator #1");
        // The updating of notification types is entirely similar
        // to the calls above

        // The data can then be changed and the changes
        //  persisted
        resourceRole.setName("Changed name");
        manager.updateResourceRole(resourceRole, "Operator #1");

    }

    /**
     * Shows how to update all resources for a project.
     *
     * @throws Exception to JUnit
     */
    public void testDemo_UpdateAllResourcesForAProject() throws Exception {
        ResourceRole resourceRole = new ResourceRole();
        resourceRole.setName("Some Resource Role");
        resourceRole.setDescription("This role plays some purpose");
        Resource resource = new Resource();
        resource.setResourceRole(resourceRole);

        long projectId = 1205;
        resource.setProject(new Long(projectId));

        // Removes any resources for the project not in the array
        // and updates/adds those in the array to the persistence
        manager.updateResources(new Resource[] {resource}, projectId, "Operator #1");
    }

    /**
     * Shows how to retrieve and search the resources.
     *
     * @throws Exception to JUnit
     */
    public void testDemo_RetrieveAndSearchResources() throws Exception {
        // Get a resource for a given id
        manager.getResource(14402);
        // The properties of the resource can then be queried
        // and used by the client of this component
        // Search for resources
        // Build the filters ¨C this example shows searching for
        // all resources related to a given project and of a
        // given type and having an extension property of a given name
        Filter projectFilter =  ResourceFilterBuilder.createProjectIdFilter(953);
        Filter resourceTypeFilter =  ResourceFilterBuilder.createResourceRoleIdFilter(1223);
        Filter extensionNameFilter =  ResourceFilterBuilder.createExtensionPropertyNameFilter("Extension Prop Name");
        Filter fullFilter =  new AndFilter(new AndFilter(projectFilter, resourceTypeFilter), extensionNameFilter);

        // Search for the Resources
        manager.searchResources(fullFilter);

        // ResourceRoles, NotificationTypes, and Notifications can be
        // searched similarly by using the other FilterBuilder classes
        // and the corresponding ResourceManager methods.  They can
        // also be retrieved through the getAll methods
        manager.getAllResourceRoles();

        manager.getAllNotificationTypes();

    }

    /**
     * Shows how to add and remove notifications.
     *
     * @throws Exception to JUnit
     */
    public void testDemo_AddAndRemoveNotificaions() throws Exception {
        // Note that it is up to the application to decide what the
        // user/external ids represent in their system
        manager.addNotifications(new long[] {1, 2, 3}, 953, 192, "Operator #1");
        manager.removeNotifications(new long[] {1, 2, 3}, 953, 192,  "Operator #1");
    }

    /**
     * Shows how to retrieve notifications.
     *
     * @throws Exception to JUnit
     */
    public void testDemo_RetrieveNotifications() throws Exception {
        manager.getNotifications(953, 192);
        // This might, for example, represent the users to which an email
        // needs to be sent.  The client could then lookup the user
        // information for each id and send the email.

        // Searches for notifications can also be made using an API
        // that precisely parallels that shown for Resources in 4.3.5.
        // When searching is used, full-fledges Notification instances
        // are returned.

    }

    /**
     * Shows how to manipulate resource submissions.
     *
     * @throws Exception to JUnit
     * @since 1.1
     */
    public void testDemo_Manipulate_Resource_Submissions() throws Exception {
        // Create a Resource
        Resource resource = new Resource();

        // Suppose we have some submissions that need to be associated with the Resource created.
        Long[] submissions = new Long[] {new Long(1), new Long(2), new Long(3)};

        // Add the submissions to the Resource
        resource.setSubmissions(submissions);

        // Suppose a new submission is received
        Long newSubmission = new Long(4);
        resource.addSubmission(newSubmission);

        // See whether a resource contains a submission
        Long checkSubmission = new Long(3);
        resource.containsSubmission(checkSubmission);

        // See how many submissions are associated with the Resource
        int submissionsNumber = resource.countSubmissions();

        // Clear the associated submissions
        resource.clearSubmissions();

        // Create Resource filter which is used to retrieve the resources which are associated with given submission id.
        Filter resourceFilter = ResourceFilterBuilder.createSubmissionIdFilter(2);

        // Create Resource filter which is used to retrieve the resources which are associated with any one of given
        // submission ids.
        resourceFilter = ResourceFilterBuilder.createAnySubmissionIdFilter(new long[]{1, 4});
    }

    /**
     * Sets the searchable fields to the search bundle.
     *
     * @param searchBundle the search bundle to set
     */
    private void setAllFieldsSearchable(SearchBundle searchBundle) {
        Map fields = new HashMap();

        // set the resource filter fields
        fields.put(ResourceFilterBuilder.RESOURCE_ID_FIELD_NAME, LongValidator.isPositive());
        fields.put(ResourceFilterBuilder.PHASE_ID_FIELD_NAME, LongValidator.isPositive());
        fields.put(ResourceFilterBuilder.PROJECT_ID_FIELD_NAME, LongValidator.isPositive());
        fields.put(ResourceFilterBuilder.SUBMISSION_ID_FIELD_NAME, LongValidator.isPositive());
        fields.put(ResourceFilterBuilder.RESOURCE_ROLE_ID_FIELD_NAME, LongValidator.isPositive());
        fields.put(ResourceFilterBuilder.EXTENSION_PROPERTY_NAME_FIELD_NAME, StringValidator.startsWith(""));
        fields.put(ResourceFilterBuilder.EXTENSION_PROPERTY_VALUE_FIELD_NAME, StringValidator.startsWith(""));

        // set the resource role filter fields
        fields.put(ResourceRoleFilterBuilder.NAME_FIELD_NAME, StringValidator.startsWith(""));
        fields.put(ResourceRoleFilterBuilder.PHASE_TYPE_ID_FIELD_NAME, LongValidator.isPositive());
        fields.put(ResourceRoleFilterBuilder.RESOURCE_ROLE_ID_FIELD_NAME, LongValidator.isPositive());

        // set the notification filter fields
        fields.put(NotificationFilterBuilder.EXTERNAL_REF_ID_FIELD_NAME, LongValidator.isPositive());
        fields.put(NotificationFilterBuilder.NOTIFICATION_TYPE_ID_FIELD_NAME, LongValidator.isPositive());
        fields.put(NotificationFilterBuilder.PROJECT_ID_FIELD_NAME, LongValidator.isPositive());

        // set the notification type filter fields
        fields.put(NotificationTypeFilterBuilder.NOTIFICATION_TYPE_ID_FIELD_NAME, LongValidator.isPositive());
        fields.put(NotificationTypeFilterBuilder.NAME_FIELD_NAME, StringValidator.startsWith(""));

        searchBundle.setSearchableFields(fields);
    }

    /**
     * Sets up the environment, mainly initialize the private fields.
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        loadNamespaces();

        // insert records
        insertRecords();

        // set the persistence
        persistence = new MockResourcePersistenceImpl();

        // get the id generators
        resourceIdGenerator = IDGeneratorFactory.getIDGenerator(
                PersistenceResourceManager.RESOURCE_ID_GENERATOR_NAME);
        resourceRoleIdGenerator = IDGeneratorFactory.getIDGenerator(
                PersistenceResourceManager.RESOURCE_ROLE_ID_GENERATOR_NAME);
        notificationTypeIdGenerator = IDGeneratorFactory.getIDGenerator(
                PersistenceResourceManager.NOTIFICATION_TYPE_ID_GENERATOR_NAME);

        // get the search bundles
        SearchBundleManager searchBundleManager = new SearchBundleManager(
                "com.topcoder.search.builder.SearchBundleManager");


        resourceSearchBundle = searchBundleManager.getSearchBundle(
                PersistenceResourceManager.RESOURCE_SEARCH_BUNDLE_NAME);
        // set it searchable
        setAllFieldsSearchable(resourceSearchBundle);

        resourceRoleSearchBundle = searchBundleManager.getSearchBundle(
                PersistenceResourceManager.RESOURCE_ROLE_SEARCH_BUNDLE_NAME);
        // set it searchable
        setAllFieldsSearchable(resourceRoleSearchBundle);

        notificationSearchBundle = searchBundleManager.getSearchBundle(
                PersistenceResourceManager.NOTIFICATION_SEARCH_BUNDLE_NAME);
        // set it searchable
        setAllFieldsSearchable(notificationSearchBundle);

        notificationTypeSearchBundle = searchBundleManager.getSearchBundle(
                PersistenceResourceManager.NOTIFICATION_TYPE_SEARCH_BUNDLE_NAME);
        // set it searchable
        setAllFieldsSearchable(notificationTypeSearchBundle);

        // initialize the PersistenceResourceManager
        manager = new PersistenceResourceManager(persistence, searchBundleManager);
    }

    /**
     * Clears the test environments.
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        removeTables();
        clearNamespaces();
    }

    /**
     * Loads the specific namespaces to the ConfigManager.
     *
     * @throws Exception to JUnit.
     */
    private void loadNamespaces() throws Exception {
        String fileNames[] = {DB_CONNECTION_CONFIG, SEARCH_BUNDLE_MANAGER};
        for (int i = 0; i < fileNames.length; i++) {
            File file = new File(fileNames[i]);
            cm.add(file.getCanonicalPath());
        }
    }

    /**
     * Removes all the namespaces from the ConfigManager.
     *
     * @throws Exception to JUnit
     */
    private void clearNamespaces() throws Exception {
        for (Iterator it = cm.getAllNamespaces(); it.hasNext();) {
            String namespace = (String) it.next();
            if (cm.existsNamespace(namespace)) {
                cm.removeNamespace(namespace);
            }
        }
    }

    /**
     * Clears all the tables in the database.
     *
     * @throws Exception to JUnit
     */
    private void removeTables() throws Exception {
        String[] tables = new String[] {"notification", "notification_type_lu", "resource_submission",
            "resource_info", "resource_info_type_lu", "resource", "resource_role_lu", "submission",
            "project_phase", "phase_type_lu", "project"};
        Connection con = getConnection();
        for (int i = 0; i < tables.length; i++) {
            doSQLUpdate(con, "DELETE FROM " + tables[i], new Object[] {});
        }
        con.commit();
        closeConnection(con);
    }

    /**
     * Does the update operation to the database.
     *
     * @param con the connection
     * @param sql the sql statement to execute
     * @param args the arguments to be set into the database
     */
    private static void doSQLUpdate(Connection con, String sql, Object[] args) {
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                setElement(ps, i + 1, args[i]);
            }
            ps.executeUpdate();
        } catch (SQLException e) {
            // ignore
            e.printStackTrace();
        } finally {
            closeStatement(ps);
        }
    }

    /**
     * Sets the element to the prepared statement with given index.
     *
     * @param ps the prepared statement
     * @param index the index to set
     * @param obj the value of the argument
     *
     * @throws SQLException if any error occurs
     */
    private static void setElement(PreparedStatement ps, int index, Object obj) throws SQLException {
        if (obj instanceof String) {
            ps.setString(index, (String) obj);
        } else if (obj instanceof Integer) {
            ps.setInt(index, ((Integer) obj).intValue());
        } else if (obj instanceof Long) {
            ps.setLong(index, ((Long) obj).longValue());
        } else if (obj instanceof Timestamp) {
            ps.setTimestamp(index, (Timestamp) obj);
        } else if (obj instanceof Date) {
            ps.setDate(index, new java.sql.Date(((Date) obj).getTime()));
        } else {
            throw new IllegalArgumentException("The element type is not supported yet.");
        }
    }

    /**
     * Closes a prepared statement silently.
     *
     * @param ps the prepared statement to close
     */
    private static void closeStatement(PreparedStatement ps) {
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                // ignore
            }
        }
    }

    /**
     * Closes a connection silently.
     *
     * @param con the connection to close
     */
    private static void closeConnection(Connection con) {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                // ignore
            }
        }
    }

    /**
     * Returns a connection.
     *
     * @return the connection
     *
     * @throws Exception to JUnit
     */
    private static Connection getConnection() throws Exception {
        String dbNamespace = "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl";
        DBConnectionFactory factory = new DBConnectionFactoryImpl(dbNamespace);
        Connection con = factory.createConnection();
        con.setAutoCommit(false);
        return con;
    }

    /**
     * Inserts the test records to database.
     *
     * @throws Exception to JUnit
     */
    private static void insertRecords() throws Exception {
        Connection con = getConnection();

        String sql = null;

        // insert records to the table: project
        sql = "INSERT INTO project(project_id) VALUES(?)";

        doSQLUpdate(con, sql, new Object[] {new Long(1)});

        doSQLUpdate(con, sql, new Object[] {new Long(2)});

        doSQLUpdate(con, sql, new Object[] {new Long(3)});

        // insert records to the table: phase_type_lu
        sql = "INSERT INTO phase_type_lu(phase_type_id) VALUES(?)";

        doSQLUpdate(con, sql, new Object[] {new Long(1)});

        doSQLUpdate(con, sql, new Object[] {new Long(2)});

        doSQLUpdate(con, sql, new Object[] {new Long(3)});

        // insert records to the table: phase

        sql = "INSERT INTO project_phase(project_phase_id, project_id, phase_type_id) VALUES(?, ?, ?)";

        doSQLUpdate(con, sql, new Object[] {new Long(1), new Long(1), new Long(1)});

        doSQLUpdate(con, sql, new Object[] {new Long(2), new Long(1), new Long(2)});

        doSQLUpdate(con, sql, new Object[] {new Long(3), new Long(1), new Long(3)});

        doSQLUpdate(con, sql, new Object[] {new Long(4), new Long(2), new Long(1)});

        doSQLUpdate(con, sql, new Object[] {new Long(5), new Long(2), new Long(2)});

        doSQLUpdate(con, sql, new Object[] {new Long(6), new Long(2), new Long(3)});

        doSQLUpdate(con, sql, new Object[] {new Long(7), new Long(3), new Long(1)});

        doSQLUpdate(con, sql, new Object[] {new Long(8), new Long(3), new Long(2)});

        doSQLUpdate(con, sql, new Object[] {new Long(9), new Long(3), new Long(3)});

        // insert records to the table: submission

        sql = "INSERT INTO submission(submission_id) VALUES(?)";

        doSQLUpdate(con, sql, new Object[] {new Long(1)});

        doSQLUpdate(con, sql, new Object[] {new Long(2)});

        doSQLUpdate(con, sql, new Object[] {new Long(3)});

        // insert records to the table: resource_role_lu

        sql = "INSERT INTO resource_role_lu(resource_role_id, phase_type_id, name,"
            + " description, create_user, create_date, modify_user, modify_date)"
            + " VALUES(?, ?, ?, 'test', 'developer', CURRENT, 'developer', CURRENT)";

        doSQLUpdate(con, sql, new Object[] {new Long(1), new Long(1), "role 1"});

        doSQLUpdate(con, sql, new Object[] {new Long(2), new Long(2), "role 2"});

        doSQLUpdate(con, sql, new Object[] {new Long(3), new Long(3), "role 3"});

        // insert records to the table: resource.

        sql = "INSERT INTO resource(resource_id, resource_role_id, project_id, project_phase_id, "
            + "create_user, create_date, modify_user, modify_date)"
            + " VALUES (?, ?, ?, ?, 'developer', CURRENT, 'developer', CURRENT)";

        doSQLUpdate(con, sql, new Object[] {new Long(1),
            new Long(1), new Long(1), new Long(1)});

        doSQLUpdate(con, sql, new Object[] {new Long(2),
            new Long(1), new Long(2), new Long(1)});

        doSQLUpdate(con, sql, new Object[] {new Long(3),
            new Long(1), new Long(3), new Long(1)});

        doSQLUpdate(con, sql, new Object[] {new Long(4),
            new Long(2), new Long(1), new Long(1)});

        doSQLUpdate(con, sql, new Object[] {new Long(5),
            new Long(2), new Long(2), new Long(1)});

        doSQLUpdate(con, sql, new Object[] {new Long(6),
            new Long(2), new Long(3), new Long(1)});

        doSQLUpdate(con, sql, new Object[] {new Long(7),
            new Long(3), new Long(1), new Long(1)});

        doSQLUpdate(con, sql, new Object[] {new Long(8),
            new Long(3), new Long(2), new Long(1)});

        doSQLUpdate(con, sql, new Object[] {new Long(9),
            new Long(3), new Long(3), new Long(1)});

        // insert records to the table: resource_info_type_lu

        sql = "INSERT INTO resource_info_type_lu"
            + " (resource_info_type_id, name, description, create_user, create_date, modify_user, modify_date)"
            + " VALUES(?, ?, 'test', 'developer', CURRENT, 'developer', CURRENT)";

        doSQLUpdate(con, sql, new Object[] {new Long(1), "resource info type 1"});

        doSQLUpdate(con, sql, new Object[] {new Long(2), "resource info type 2"});

        doSQLUpdate(con, sql, new Object[] {new Long(3), "resource info type 3"});

        // insert records to the table: resource_info
        sql = "INSERT INTO resource_info"
            + " (resource_id, resource_info_type_id, value, create_user, create_date, modify_user, modify_date)"
            + " VALUES(?, ?, ?, 'developer', CURRENT, 'developer', CURRENT)";

        doSQLUpdate(con, sql, new Object[] {new Long(1), new Long(1), "value 1"});

        doSQLUpdate(con, sql, new Object[] {new Long(2), new Long(1), "value 2"});

        doSQLUpdate(con, sql, new Object[] {new Long(3), new Long(1), "value 3"});

        doSQLUpdate(con, sql, new Object[] {new Long(1), new Long(2), "value 4"});

        doSQLUpdate(con, sql, new Object[] {new Long(2), new Long(2), "value 5"});

        doSQLUpdate(con, sql, new Object[] {new Long(3), new Long(2), "value 6"});

        doSQLUpdate(con, sql, new Object[] {new Long(1), new Long(3), "value 7"});

        doSQLUpdate(con, sql, new Object[] {new Long(2), new Long(3), "value 8"});

        doSQLUpdate(con, sql, new Object[] {new Long(3), new Long(3), "value 9"});

        // insert records to the table: resource_submission
        sql = "INSERT INTO resource_submission"
            + " (resource_id, submission_id, create_user, create_date, modify_user, modify_date)"
            + " VALUES(?, ?, 'developer', CURRENT, 'developer', CURRENT)";

        doSQLUpdate(con, sql, new Object[] {new Long(1), new Long(1)});

        doSQLUpdate(con, sql, new Object[] {new Long(1), new Long(2)});

        doSQLUpdate(con, sql, new Object[] {new Long(1), new Long(3)});

        doSQLUpdate(con, sql, new Object[] {new Long(2), new Long(1)});

        doSQLUpdate(con, sql, new Object[] {new Long(2), new Long(2)});

        doSQLUpdate(con, sql, new Object[] {new Long(2), new Long(3)});

        doSQLUpdate(con, sql, new Object[] {new Long(3), new Long(1)});

        doSQLUpdate(con, sql, new Object[] {new Long(3), new Long(2)});

        doSQLUpdate(con, sql, new Object[] {new Long(3), new Long(3)});

        // insert records to the table: notification_type_lu
        sql = "INSERT INTO notification_type_lu"
            + " (notification_type_id, name, description, create_user, create_date, modify_user, modify_date)"
            + " VALUES(?, ?, 'test', 'developer', CURRENT, 'developer', CURRENT)";

        doSQLUpdate(con, sql, new Object[] {new Long(1), "notification type 1"});

        doSQLUpdate(con, sql, new Object[] {new Long(2), "notification type 2"});

        doSQLUpdate(con, sql, new Object[] {new Long(3), "notification type 3"});

        // insert records to the table: notification
        sql = "INSERT INTO notification ("
            + "project_id, external_ref_id, notification_type_id, create_user, create_date, modify_user, modify_date)"
            + " VALUES(?, ?, ?, 'developer', CURRENT, 'developer', CURRENT)";

        doSQLUpdate(con, sql, new Object[] {new Long(1), new Long(1), new Long(1)});

        doSQLUpdate(con, sql, new Object[] {new Long(1), new Long(1), new Long(2)});

        doSQLUpdate(con, sql, new Object[] {new Long(1), new Long(1), new Long(3)});

        doSQLUpdate(con, sql, new Object[] {new Long(1), new Long(2), new Long(1)});

        doSQLUpdate(con, sql, new Object[] {new Long(1), new Long(2), new Long(2)});

        doSQLUpdate(con, sql, new Object[] {new Long(1), new Long(2), new Long(3)});

        doSQLUpdate(con, sql, new Object[] {new Long(1), new Long(3), new Long(1)});

        doSQLUpdate(con, sql, new Object[] {new Long(1), new Long(3), new Long(2)});

        doSQLUpdate(con, sql, new Object[] {new Long(1), new Long(3), new Long(3)});

        doSQLUpdate(con, sql, new Object[] {new Long(2), new Long(1), new Long(1)});

        doSQLUpdate(con, sql, new Object[] {new Long(2), new Long(1), new Long(2)});

        doSQLUpdate(con, sql, new Object[] {new Long(2), new Long(1), new Long(3)});

        doSQLUpdate(con, sql, new Object[] {new Long(2), new Long(2), new Long(1)});

        doSQLUpdate(con, sql, new Object[] {new Long(2), new Long(2), new Long(2)});

        doSQLUpdate(con, sql, new Object[] {new Long(2), new Long(2), new Long(3)});

        doSQLUpdate(con, sql, new Object[] {new Long(2), new Long(3), new Long(1)});

        doSQLUpdate(con, sql, new Object[] {new Long(2), new Long(3), new Long(2)});

        doSQLUpdate(con, sql, new Object[] {new Long(2), new Long(3), new Long(3)});

        doSQLUpdate(con, sql, new Object[] {new Long(3), new Long(1), new Long(1)});

        doSQLUpdate(con, sql, new Object[] {new Long(3), new Long(1), new Long(2)});

        doSQLUpdate(con, sql, new Object[] {new Long(3), new Long(1), new Long(3)});

        doSQLUpdate(con, sql, new Object[] {new Long(3), new Long(2), new Long(1)});

        doSQLUpdate(con, sql, new Object[] {new Long(3), new Long(2), new Long(2)});

        doSQLUpdate(con, sql, new Object[] {new Long(3), new Long(2), new Long(3)});

        doSQLUpdate(con, sql, new Object[] {new Long(3), new Long(3), new Long(1)});

        doSQLUpdate(con, sql, new Object[] {new Long(3), new Long(3), new Long(2)});

        doSQLUpdate(con, sql, new Object[] {new Long(3), new Long(3), new Long(3)});

        con.commit();
        closeConnection(con);
    }

}
