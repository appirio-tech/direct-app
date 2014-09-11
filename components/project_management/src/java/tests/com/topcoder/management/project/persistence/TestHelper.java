/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project.persistence;

import java.sql.Connection;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;

import com.topcoder.management.project.FileType;
import com.topcoder.management.project.Prize;
import com.topcoder.management.project.PrizeType;
import com.topcoder.management.project.Project;
import com.topcoder.management.project.ProjectCategory;
import com.topcoder.management.project.ProjectStatus;
import com.topcoder.management.project.ProjectStudioSpecification;
import com.topcoder.management.project.ProjectType;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.ConfigManagerException;

/**
 * <p>
 * This is the test help class used to aggregate some common utilities methods.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.2
 */
public final class TestHelper {
    /**
     * Private constructor.
     */
    private TestHelper() {
    }

    /**
     * Creates file type instance according to the given fields values.
     *
     * @param description
     *            the description field value to set
     * @param extension
     *            the extension field value to set
     * @param imageFile
     *            the imageFile field value to set
     * @param bundledFile
     *            the bundledFile field value to set
     * @param sort
     *            the sort field value to set
     * @return the created instance
     */
    static FileType createFileType(String description, String extension, boolean imageFile, boolean bundledFile,
        int sort) {
        FileType fileType = new FileType();

        fileType.setBundledFile(true);
        fileType.setCreationTimestamp(new Date());
        fileType.setCreationUser("user");
        fileType.setDescription(description);
        fileType.setExtension(extension);
        fileType.setImageFile(true);
        fileType.setModificationTimestamp(new Date());
        fileType.setModificationUser("root");
        fileType.setSort(sort);

        return fileType;
    }

    /**
     * Creates prize instance according to the given fields values.
     *
     * @param place
     *            the place field value to set
     * @param prizeAmount
     *            the prizeAmount field value to set
     * @param numberOfSubmissions
     *            the numberOfSubmissions field value to set
     * @return the created instance
     */
    static Prize createPrize(int place, double prizeAmount, int numberOfSubmissions) {
        Prize prize = new Prize();
        prize.setPlace(place);
        prize.setPrizeAmount(prizeAmount);
        prize.setNumberOfSubmissions(numberOfSubmissions);
        PrizeType prizeType = new PrizeType();
        prizeType.setId(1);
        prizeType.setDescription("Component Design Prize");
        prize.setPrizeType(prizeType);
        return prize;
    }

    /**
     * Creates project studio specification instance according to the given fields values.
     *
     * @param goals
     *            the goals field value to set
     * @param targetAudience
     *            the targetAudience field value to set
     * @param roundOneIntroduction
     *            the roundOneIntroduction field value to set
     * @return the created instance
     */
    static ProjectStudioSpecification createProjectStudioSpecification(String goals, String targetAudience,
        String roundOneIntroduction) {
        ProjectStudioSpecification spec = new ProjectStudioSpecification();
        spec.setBrandingGuidelines("brandingGuidelines");
        spec.setColors("colors");
        spec.setDislikedDesignWebSites("dislikedDesignWebSites");
        spec.setFonts("fonts");
        spec.setLayoutAndSize("layoutAndSize");
        spec.setOtherInstructions("otherInstructions");
        spec.setRoundOneIntroduction(roundOneIntroduction);
        spec.setRoundTwoIntroduction("roundTwoIntroduction");
        spec.setSubmittersLockedBetweenRounds(true);
        spec.setTargetAudience(targetAudience);
        spec.setWinningCriteria("winningCriteria");
        spec.setGoals(goals);
        return spec;
    }

    /**
     * Get a sample Project object to test, with project id = 0, project category = .Net, project type = Topcoder,
     * project status = Active.
     *
     * @return a sample Project object
     */
    static Project getSampleProject1() {
        // create a ProjectStatus object
        ProjectStatus status = new ProjectStatus(1, "Active");

        // create a ProjectType object
        ProjectType type = new ProjectType(1, "Topcoder");

        // create a ProjectCategory object
        ProjectCategory category = new ProjectCategory(1, ".Net", type);

        // create the sample project object
        Project project = new Project(category, status);

        // set the properties
        project.setProperty("property 1", "value 1");
        project.setProperty("property 2", "value 2");

        Prize prize = createPrize(1, 600.00, 5);
        project.setPrizes(Arrays.asList(new Prize[]{prize}));

        FileType fileType = createFileType("description 1", "extension 1", true, false, 1);

        project.setProjectFileTypes(Arrays.asList(new FileType[]{fileType}));
        project.setProjectStudioSpecification(createProjectStudioSpecification("goal1", "targetAudience",
            "roundOneIntroduction1"));

        project.setTcDirectProjectId(1);

        return project;
    }

    /**
     * Prepares the test data.
     *
     * @param persistence
     *            the AbstractInformixProjectPersistence instance to use
     * @throws Exception
     *             to JUnit
     */
    static void prepareData(AbstractInformixProjectPersistence persistence) throws Exception {
        // create the connection
        Connection conn = persistence.openConnection();
        conn.setAutoCommit(false);

        // insert data into project_status_lu table
        Helper.doDMLQuery(conn, "INSERT INTO project_status_lu (project_status_id, name, description, "
            + "create_user, create_date, modify_user, modify_date) "
            + "VALUES (1, 'Active', 'Active', 'System', CURRENT, 'System', CURRENT)", new Object[]{});
        Helper.doDMLQuery(conn, "INSERT INTO project_status_lu(project_status_id, name, description, "
            + "create_user, create_date, modify_user, modify_date) "
            + "VALUES (2, 'Inactive', 'Inactive', 'System', CURRENT, 'System', CURRENT)", new Object[]{});
        Helper.doDMLQuery(conn, "INSERT INTO project_status_lu(project_status_id, name, description, "
            + "create_user, create_date, modify_user, modify_date) "
            + "VALUES (3, 'Deleted', 'Deleted', 'System', CURRENT, 'System', CURRENT)", new Object[]{});
        Helper.doDMLQuery(conn, "INSERT INTO project_status_lu(project_status_id, name, description, "
            + "create_user, create_date, modify_user, modify_date) "
            + "VALUES (4, 'Cancelled - Failed Review', 'Cancelled - Failed Review', "
            + "'System', CURRENT, 'System', CURRENT)", new Object[]{});
        Helper.doDMLQuery(conn, "INSERT INTO project_status_lu(project_status_id, name, description, "
            + "create_user, create_date, modify_user, modify_date) "
            + "VALUES (5, 'Cancelled - Failed Screening', 'Cancelled - Failed Screening', "
            + "'System', CURRENT, 'System', CURRENT)", new Object[]{});
        Helper.doDMLQuery(conn, "INSERT INTO project_status_lu(project_status_id, name, description, "
            + "create_user, create_date, modify_user, modify_date)"
            + "VALUES (6, 'Cancelled - Zero Submissions', 'Cancelled - Zero Submissions', "
            + "'System', CURRENT, 'System', CURRENT)", new Object[]{});
        Helper.doDMLQuery(conn, "INSERT INTO project_status_lu(project_status_id, name, description, "
            + "create_user, create_date, modify_user, modify_date) "
            + "VALUES (7, 'Completed', 'Completed', 'System', CURRENT, 'System', CURRENT)", new Object[]{});

        // insert data into project_type_lu table
        Helper.doDMLQuery(conn, "INSERT INTO project_type_lu " + "(project_type_id, name, description, "
            + "create_user, create_date, modify_user, modify_date, is_generic) "
            + "VALUES (1, 'Topcoder', 'Topcoder Component', " + "'topcoder', CURRENT, 'topcoder', CURRENT, 1)",
            new Object[]{});
        Helper.doDMLQuery(conn, "INSERT INTO project_type_lu " + "(project_type_id, name, description, "
            + "create_user, create_date, modify_user, modify_date, is_generic) "
            + "VALUES (2, 'Customer', 'Customer Component', " + "'topcoder', CURRENT, 'topcoder', CURRENT, 1)",
            new Object[]{});

        // insert data into project_category_lu table
        Helper.doDMLQuery(conn, "INSERT INTO project_category_lu "
            + "(project_category_id, project_type_id, name, description, "
            + "create_user, create_date, modify_user, modify_date) " + "VALUES (1, 1, '.Net', '.NET Component', "
            + "'topcoder', CURRENT, 'topcoder', CURRENT)", new Object[]{});
        Helper.doDMLQuery(conn, "INSERT INTO project_category_lu "
            + "(project_category_id, project_type_id, name, description, "
            + "create_user, create_date, modify_user, modify_date) " + "VALUES (2, 1, 'Java', 'JAVA Component', "
            + "'topcoder', CURRENT, 'topcoder', CURRENT)", new Object[]{});
        Helper.doDMLQuery(conn, "INSERT INTO project_category_lu "
            + "(project_category_id, project_type_id, name, description, "
            + "create_user, create_date, modify_user, modify_date) "
            + "VALUES (3, 2, 'Customer .Net', 'Customer .NET Component', "
            + "'topcoder', CURRENT, 'topcoder', CURRENT)", new Object[]{});
        Helper.doDMLQuery(conn, "INSERT INTO project_category_lu "
            + "(project_category_id, project_type_id, name, description, "
            + "create_user, create_date, modify_user, modify_date) "
            + "VALUES (4, 2, 'Customer Java', 'Customer JAVA Component', "
            + "'topcoder', CURRENT, 'topcoder', CURRENT)", new Object[]{});

        // insert data into project_info_type_lu table
        Helper.doDMLQuery(conn, "INSERT INTO project_info_type_lu " + "(project_info_type_id, name, description, "
            + "create_user, create_date, modify_user, modify_date) "
            + "VALUES (1, 'property 1', 'project property 1', " + "'topcoder', CURRENT, 'topcoder', CURRENT)",
            new Object[]{});
        Helper.doDMLQuery(conn, "INSERT INTO project_info_type_lu " + "(project_info_type_id, name, description, "
            + "create_user, create_date, modify_user, modify_date) "
            + "VALUES (2, 'property 2', 'project property 2', " + "'topcoder', CURRENT, 'topcoder', CURRENT)",
            new Object[]{});
        Helper.doDMLQuery(conn, "INSERT INTO project_info_type_lu " + "(project_info_type_id, name, description, "
            + "create_user, create_date, modify_user, modify_date) "
            + "VALUES (3, 'property 3', 'project property 3', " + "'topcoder', CURRENT, 'topcoder', CURRENT)",
            new Object[]{});
        Helper.doDMLQuery(conn, "INSERT INTO project_info_type_lu " + "(project_info_type_id, name, description, "
            + "create_user, create_date, modify_user, modify_date) "
            + "VALUES (4, 'property 4', 'project property 4', " + "'topcoder', CURRENT, 'topcoder', CURRENT)",
            new Object[]{});

        // insert data into audit_action_type_id table
        Helper.doDMLQuery(conn, "INSERT INTO audit_action_type_lu " + "(audit_action_type_id, name, description, "
            + "create_user, create_date, modify_user, modify_date) " + "VALUES (1, 'audit 1', 'audit 1', "
            + "'topcoder', CURRENT, 'topcoder', CURRENT)", new Object[]{});
        Helper.doDMLQuery(conn, "INSERT INTO audit_action_type_lu " + "(audit_action_type_id, name, description, "
            + "create_user, create_date, modify_user, modify_date) " + "VALUES (2, 'audit 2', 'audit 2', "
            + "'topcoder', CURRENT, 'topcoder', CURRENT)", new Object[]{});

        // insert data into prize_type_lu table
        Helper.doDMLQuery(conn, "INSERT INTO prize_type_lu " + "(prize_type_id, description) "
            + "VALUES (1, 'Component Design Prize')", new Object[]{});
        Helper.doDMLQuery(conn, "INSERT INTO prize_type_lu " + "(prize_type_id, description) "
            + "VALUES (2, 'Component Development Prize')", new Object[]{});
        Helper.doDMLQuery(conn, "INSERT INTO prize_type_lu " + "(prize_type_id, description) "
            + "VALUES (3, 'Assembly Prize')", new Object[]{});

        conn.commit();
        conn.close();
    }

    /**
     * Clears the test data.
     *
     * @param persistence
     *            the AbstractInformixProjectPersistence instance to use
     * @throws Exception
     *             to JUnit
     */
    static void clearData(AbstractInformixProjectPersistence persistence) throws Exception {
        // create the connection
        Connection conn = persistence.openConnection();
        conn.setAutoCommit(false);

        // clear the tables
        Helper.doDMLQuery(conn, "DELETE FROM project_info_audit", new Object[]{});
        Helper.doDMLQuery(conn, "DELETE FROM audit_action_type_lu", new Object[]{});
        Helper.doDMLQuery(conn, "DELETE FROM project_audit", new Object[]{});
        Helper.doDMLQuery(conn, "DELETE FROM project_info", new Object[]{});
        Helper.doDMLQuery(conn, "DELETE FROM project_info_type_lu", new Object[]{});

        Helper.doDMLQuery(conn, "DELETE FROM project_file_type_xref", new Object[]{});
        Helper.doDMLQuery(conn, "DELETE FROM project_prize_xref", new Object[]{});
        Helper.doDMLQuery(conn, "DELETE FROM prize", new Object[]{});
        Helper.doDMLQuery(conn, "DELETE FROM prize_type_lu", new Object[]{});
        Helper.doDMLQuery(conn, "DELETE FROM file_type_lu", new Object[]{});

        Helper.doDMLQuery(conn, "DELETE FROM project", new Object[]{});
        Helper.doDMLQuery(conn, "DELETE FROM project_status_lu", new Object[]{});
        Helper.doDMLQuery(conn, "DELETE FROM project_category_lu", new Object[]{});
        Helper.doDMLQuery(conn, "DELETE FROM project_type_lu", new Object[]{});

        conn.commit();
        conn.close();
    }

    /**
     * Prepares the configurations.
     *
     * @throws ConfigManagerException
     *             to JUnit
     */
    static void prepareConfig() throws ConfigManagerException {
        ConfigManager cm = ConfigManager.getInstance();

        // load the configurations for db connection factory
        cm.add("dbfactory.xml");

        // load the configurations for InformixProjectPersistence
        cm.add("informix_persistence.xml");
    }

    /**
     * Clears the configurations.
     *
     * @throws ConfigManagerException
     *             to JUnit
     */
    @SuppressWarnings("unchecked")
    static void clearConfig() throws ConfigManagerException {
        ConfigManager cm = ConfigManager.getInstance();

        Iterator it = cm.getAllNamespaces();
        while (it.hasNext()) {
            cm.removeNamespace((String) it.next());
        }
    }
}
